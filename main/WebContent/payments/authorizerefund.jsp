<%@page import="java.util.Hashtable"%>
<%@page import="com.eventbee.general.DbUtil"%>
<%@page import="com.eventbee.general.DateUtil"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.DataOutputStream"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.eventbee.general.DBManager"%>
<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="java.net.URL"%>
<%@page import="com.eventbee.general.EbeeConstantsF"%>
<%@page import="com.eventbee.creditcard.CreditCardModel"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%!
   public static String[] responseFields={"ResponseCode","ResponseSubcode","ResponseReasonCode","ResponseReasonText","AuthorizationCode",
		"TransactionID","Amount","Method","TransactionType","ReferenceCode","CardNumber","CardType"};
   public static HashMap<String,String> PaymentRefund(HashMap<String,String> carddetails,String amount){
	// for real accounts: https://secure.authorize.net/gateway/transact.dll
	String gatewayURL=EbeeConstantsF.get("AUTHORIZE.NET.GATEWAY.URL","https://test.authorize.net/gateway/transact.dll");
	HashMap<String,String> responseMap=new HashMap<String,String>();
	try{
	URL post_url = new URL(gatewayURL);
	Hashtable<String,String> post_values = new Hashtable<String,String>();

	//Acquiring Card Transaction Details from DB
	//Acquiring API Details from DB

	post_values.put ("x_login", carddetails.get("loginId"));
	post_values.put ("x_tran_key",carddetails.get("transactionKey"));
	post_values.put ("x_version", "3.1");
	post_values.put ("x_delim_data", "TRUE");
	post_values.put ("x_delim_char", "|");
	post_values.put ("x_relay_response", "FALSE");
	post_values.put ("x_type", "CREDIT");
	post_values.put ("x_trans_id", carddetails.get("responseid"));
	post_values.put ("x_card_num", carddetails.get("cardnumber"));
	post_values.put ("x_amount", amount);
	StringBuffer post_string = new StringBuffer();
	Enumeration<String> keys = post_values.keys();
	while( keys.hasMoreElements() ) {
	  String key = URLEncoder.encode(keys.nextElement(),"UTF-8");
	  String value = URLEncoder.encode(post_values.get(key),"UTF-8");
	  post_string.append(key + "=" + value + "&");
	}	
	URLConnection connection = post_url.openConnection();
	connection.setDoOutput(true);
	connection.setUseCaches(false);

	// this line is not necessarily required but fixes a bug with some servers
	connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

	// submit the post_string and close the connection
	DataOutputStream requestObject = new DataOutputStream( connection.getOutputStream() );
	requestObject.write(post_string.toString().getBytes());
	requestObject.flush();
	requestObject.close();

	// process and read the gateway response
	BufferedReader rawResponse = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	String responseData = rawResponse.readLine();
	System.out.println("Actual Response: "+responseData);
	rawResponse.close();// no more data
	
	// split the response into an array
	ArrayList<String> responsesList=new ArrayList<String>();
	String [] responses = responseData.split("\\|");
	String responsetext="";
	for(int i=0;i<responses.length;i++){
		if(responses[i]!=null && !"".equals(responses[i].trim()))
	    	responsesList.add(responses[i]);
	}

	for(int i=0;i<responsesList.size();i++){
		if("ResponseReasonText".equals(responseFields[i])){
			if(responsesList.get(i).indexOf(".")>-1)
				responsetext=responsesList.get(i).replace(".","").trim();
			responseMap.put(responseFields[i],responsetext);
		}else
			responseMap.put(responseFields[i],responsesList.get(i));
	}
}catch(Exception e){
		System.out.println("Exception in authorizerefund.jsp:: "+e.getMessage());
		e.printStackTrace();
		return null;
	}
    System.out.println("responseMap is:"+responseMap);
	return responseMap;
}

%>
<%

try{
	String eid=request.getParameter("eid");
	String tid=request.getParameter("tid");
	System.out.println("in authorizerefund.jsp tid: "+tid);
	String amount=request.getParameter("amount");
	String payment_type=request.getParameter("payment_type");
	HashMap<String,String>  carddetails=new HashMap<String,String> ();
	carddetails.put("loginId",request.getParameter("loginId"));
	carddetails.put("transactionKey",request.getParameter("transactionKey"));
	carddetails.put("responseid",request.getParameter("responseid"));
	carddetails.put("cardnumber",request.getParameter("cardnumber"));
	StringBuffer sbf=new StringBuffer();
	sbf.append("<Response>\n");
	HashMap<String,String> responseMap=PaymentRefund(carddetails,amount);
    String status="success";
	if("This transaction has been approved".equals(responseMap.get("ResponseReasonText")))
		sbf.append("<Status>Success</Status>\n<TransactionID>"+tid+"</TransactionID>\n<internalrefid>"+responseMap.get("TransactionID")+"</internalrefid>\n");
	else{
		sbf.append("<Status>Fail</Status>\n<TransactionID>"+tid+"</TransactionID>\n<ErrorMsg>"+responseMap.get("ResponseReasonText")+"</ErrorMsg>\n"); 
	status="fail";
	}
	sbf.append("</Response>");
	System.out.println("response is: "+sbf.toString());
	String reference_id="";
	if(responseMap.get("TransactionID")!=null)
		reference_id=responseMap.get("TransactionID");
	String insertquery="insert into refund_track(eventid,tid,amount,payment_type,response,refunded_at,reference_id,status) values(?,?,?,?,?,now(),?,?)";
	StatusObj sb=DbUtil.executeUpdateQuery(insertquery,new String[]{eid,tid,amount,payment_type,sbf.toString(),reference_id,status});
	out.println(sbf.toString());
}catch(Exception e){
	System.out.println("Exception in authorizerefund.jsp: "+e.getMessage());
}
%>