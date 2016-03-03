<%@page import="java.math.BigDecimal"%>
<%@page import="com.eventbee.payments.braintree.EventBrainPayments" %>
<%@page import="com.braintreegateway.*" %>

<%@page import="java.util.*" %>
<%@ page import="org.json.JSONObject, com.eventbee.general.*"%>
<%@ page import=" com.eventbee.general.formatting.CurrencyFormat"%>
<%!
HashMap<String,String> valerror= new HashMap<String,String>(); 


public HashMap<String,String> filldata(HttpServletRequest paydet,String paytype){
	HashMap<String,String> datahm=new HashMap<String,String>();
		
	String insrtquery="insert into braintree_payments_requests( "
        +"record_id,ref_id,transactionid,transaction_date," +
        "cardtype,exp_date,cardnum,amount," +
        "transaction_type,response_id,status,merchant_id,ipaddresss,recallid,purpose,vaultid,valuttoken" +
        ",responsetime,response,public_key,private_key) "
         +" values (?,?,?,now(),?,?,?," +
         "?,?,?,?,?,?,?,?,?,?,now(),?,?,?) ";
         
    String ipaddress=paydet.getLocalAddr();
    String purpose=paydet.getParameter("purpose");if(purpose==null){purpose="";}
    String ref_id=paydet.getParameter("ref_id");if(ref_id==null){ref_id="";}
    String paypro=paydet.getParameter("paypro");if(paypro==null)paypro=""; 
    String record_id="";
    try{  
    	record_id=DbUtil.getVal("select nextval('braintree_record_id')",new String[]{});
     }catch(Exception e){
    	 System.out.println("error in inserting values at2 Braintreepayment.jsp:"+e.getMessage());
    	}
         
    System.out.println("ipaddress: "+ipaddress+" ref_id: "+ref_id+" purpose: "+purpose);
    
    String merchant_id="",env="",public_key="",private_key="";
    try{    
    	env=EbeeConstantsF.get("BRAINTREE_ENVIRONMENT","SANDBOX");
        env=env.trim();
        merchant_id=EbeeConstantsF.get("BRAINTREE_"+env+"_MERCHANT_ID","3ynh6mq9z8cktggg");
        public_key=EbeeConstantsF.get("BRAINTREE_"+env+"_PUBLIC_KEY","js6ywhpnmxvcrxdt");
        private_key=EbeeConstantsF.get("BRAINTREE_"+env+"_PRIVATE_KEY","9kfgngv725mgwjzz");
  
  
         datahm.put("paypro",paypro);	     
	     datahm.put("merchant_id",merchant_id.trim());
	     datahm.put("public_key",public_key.trim());
	     datahm.put("private_key",private_key.trim());
	     datahm.put("env",env.trim());
	     datahm.put("record_id",record_id); }catch(Exception e){System.out.println("");}
	    	
	     String coustmerid=paydet.getParameter("coustmerid");if(coustmerid==null){coustmerid="";}
	     String tokenid=paydet.getParameter("tokenid");if(tokenid==null){tokenid="";}
	     String tid=paydet.getParameter("recall_id");if(tid==null){tid="";}
	     String amt=paydet.getParameter("GrandTotal");if(amt==null){amt="0.00";}
	     datahm.put("tid",tid);
	     datahm.put("amt",amt);
	     datahm.put("coustmerid",coustmerid);
	     datahm.put("tokenid",tokenid);
	     System.out.println("in braintreeauth.jsp datahm: "+datahm);
	     try{  
	       	 StatusObj sb=DbUtil.executeUpdateQuery(insrtquery,new String[]{record_id,ref_id,"","","","",amt,paytype,"","",merchant_id,ipaddress,"",purpose,"","","",public_key,private_key});
	     }catch(Exception e){System.out.println("error in updating values at2 Braintreepayment.jsp:"+e.getMessage());}
	     return datahm;
	
}
public HashMap<Object,Object> processResult(Result<Transaction> result,String paytype,String recid){
	HashMap<Object,Object> jsonObj=new HashMap<Object,Object>();
	ArrayList<String> valerrorcodelist=new ArrayList<String>();
	
	valerror.put("2001","Card declined due to Insufficient Funds");
	valerror.put("2002","Card declined");
	valerror.put("2004","Card declined as Expired");
	valerror.put("2051","Card declined for this payment");
	valerror.put("2059","Address Verification Failed");
	valerror.put("2010","Card declined. Invalid CVV");
	valerror.put("2000","Do Not Honor.");
	valerror.put("TRANSACTION_CANNOT_REFUND_UNLESS_SETTLED","Cannot refund a transaction unless it is settled.");
	try{  
	    	if (result.isSuccess()) {
	    	
	        Transaction transaction = result.getTarget();
	        String tokken=transaction.getId();
	        System.out.println("Success!: tokken: " + tokken);
	        jsonObj.put("status","success");
	        jsonObj.put("tokken",tokken);
	        jsonObj.put("CVV2",transaction.getCvvResponseCode());
	        jsonObj.put("postal_res",transaction.getAvsPostalCodeResponseCode());
	        jsonObj.put("addres_res",transaction.getAvsStreetAddressResponseCode());
	        jsonObj.put("response",transaction.toString());
	      
	        jsonObj.put("cardtype",transaction.getType());
	    	jsonObj.put("paytype",paytype);	
	    	jsonObj.put("vaultid",transaction.getCustomer().getId());
	    	jsonObj.put("valuttoken",transaction.getCreditCard().getToken());
		
				    	
	       } 
		  else if (result.getTransaction()!= null) {
			 
	        System.out.println("Message: " + result.getMessage());
	        jsonObj.put("tokken","");
	        jsonObj.put("vaultid","");
	    	jsonObj.put("valuttoken","");
	        Transaction transaction = result.getTransaction();
	        jsonObj.put("status",transaction.getStatus());
	        if(transaction.getProcessorResponseCode()!=null)
	          if(valerror.containsKey(transaction.getProcessorResponseCode()+""))
	          {
	        	  valerrorcodelist.add(transaction.getProcessorResponseCode()+"");
	          }
	        jsonObj.put("errorCode",transaction.getProcessorResponseCode());
	        jsonObj.put("errortext",transaction.getProcessorResponseText());
	        System.out.println("  Status: " + transaction.getStatus()+"\t Code: " + transaction.getProcessorResponseCode()+"\t Text: " + transaction.getProcessorResponseText());
	       } 
		  else {
	        System.out.println("Message: " + result.getMessage());
	        jsonObj.put("tokken","");jsonObj.put("vaultid","");
	    	jsonObj.put("valuttoken","");
	        jsonObj.put("status","validationerror");
	        jsonObj.put("validationerrorlist",result.getErrors().getAllDeepValidationErrors());
	        ArrayList<String> errormsglst=new ArrayList<String>();
	        ArrayList<String> errorcodelst=new ArrayList<String>();
	        for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
	        	errormsglst.add(error.getMessage());
	        	errorcodelst.add( error.getCode()+"");
	        	
	        	if(error.getCode()!=null)
	  	          if(valerror.containsKey( error.getCode()+""))
	  	          {valerrorcodelist.add(error.getCode()+"");}    	
		  System.out.println("Attribute: " + error.getAttribute()+"\t Code: " + error.getCode()+"\t Message: " + error.getMessage());
	        }
	        jsonObj.put("validerrormsglst",errormsglst);
	        jsonObj.put("validerrorcodelst",errorcodelst);
	      }
	    
	    	jsonObj.put("VEL",valerrorcodelist);
    	try{ 
    		String response_id="",vaultid="",valuttoken="";
    		if(jsonObj.get("tokken")!=null)response_id=jsonObj.get("tokken")+"";
    		if(jsonObj.get("vaultid")!=null)vaultid=jsonObj.get("vaultid")+"";
    		if(jsonObj.get("valuttoken")!=null)valuttoken=jsonObj.get("valuttoken")+"";
    		
    		String ack=jsonObj.get("status")+"";
    		String res=jsonObj.toString();
    		System.out.println("recid:"+recid);
    		String updatequery="update  braintree_payments_requests set response_id=?,status=?,vaultid=?,valuttoken=?,responsetime=now(),response=? where record_id=?";
    	   StatusObj sb=DbUtil.executeUpdateQuery(updatequery,new String[]{response_id,ack,vaultid,valuttoken,res,recid});
    	}catch(Exception e){System.out.println("error in updating values at end Braintreeauth.jsp:"+e.getMessage());}

			    	
	 }catch(Exception e){System.out.println("Error in processing result braintreerefund.jsp::"+e.getMessage());}
return jsonObj;
}

public Result<Transaction> makeAutherize(HashMap<String,String> paydet) 
{ System.out.println(" Start processing autherization");
	    Result<Transaction> result=null;	  
	    BraintreeGateway gateway =  intialize (paydet);			     
	    TransactionRequest request = new TransactionRequest()
	    		.customerId(paydet.get("coustmerid")).
			     amount(new BigDecimal(paydet.get("amt"))).			            
			        options().   done();

			     try{
			    	 if(gateway!=null)
			         result = gateway.transaction().sale(request);
			    	 else
			    	System.out.println("unable to create gateway for this authpayment");
			    		 
			        }catch(Exception e)
			        { System.out.println("Exception in makeutherization:"+e.getMessage());}
			        
			return result;
}
public BraintreeGateway intialize(HashMap<String,String> paydet)
	{
	System.out.println("Intializing..");
	Environment environment=Environment.SANDBOX;
	String mid="";
	String mpubkey="";
   String mprikey="";
   String env="";
   if(paydet!=null)
   {
   	try{
   		mid=(String) paydet.get("merchant_id");
   		mpubkey=(String) paydet.get("public_key");
   		mprikey=(String) paydet.get("private_key");
   		env=(String) paydet.get("env");
   		if(!"SANDBOX".equals(env))
   		environment=Environment.PRODUCTION;
   		
   	return new BraintreeGateway(environment,mid,mpubkey,mprikey);
   	}catch(Exception e)
       { System.out.println("Exception in intialize:"+e.getMessage());}
   }
   System.out.println("In sufficent values to create gateway");
   	return null;
}

%>

<% 
try{
	String paytype=request.getParameter("paytype");
	String recid="";
	System.out.println("in braintreerauth.jsp  paytype:"+paytype);
	String payment_type=request.getParameter("payment_type");
	if(paytype==null||"".equals(paytype))paytype="authmgr";
	Result<Transaction> result=null;
	HashMap<Object,Object> jsonObj=new HashMap<Object,Object>();
	jsonObj.put("status","payment type not availabe");

	if("authmgr".equals(paytype)){ 
		result=null;
	    HashMap<String,String> hm= filldata(request,paytype);
	    recid=hm.get("record_id");
		result=makeAutherize(hm);
		if(result!=null){	 
			jsonObj=processResult(result,paytype,recid);					
		}else jsonObj.put("status","conerror"); 	 
	}
	
System.out.println("in braintreerauth.jsp jsonObj: "+jsonObj);
String ack="";

%>
<Response>
	<%			if(!"success".equals(jsonObj.get("status")))
			{
	%>
	
	
	<% 
	ArrayList<String> VEL=(ArrayList<String>) jsonObj.get("VEL");
	if(VEL!=null && VEL.size()>0)
	{for(int i=0;i<VEL.size();i++)
	{
	%>
			 <ErrorCode><%=!"CREDIT_CARD_NUMBER_IS_INVALID".equals(VEL.get(i))? VEL.get(i):"81715"%></ErrorCode>
	         <ShortMsg><%=valerror.get(VEL.get(i))%></ShortMsg>
	
	<% } } else{
	
	        if("conerror".equals(jsonObj.get("status"))){
	    %>					
			<ErrorCode>505</ErrorCode>
			<ShortMsg>Card could not be processed at this time.</ShortMsg>
		
	
             <%}%><%else{ %>
             
                <ErrorCode>1</ErrorCode>
				<ShortMsg>Card declined for this payment. Make sure all details are valid.</ShortMsg>
             <% }}


}	
         if("success".equals(jsonObj.get("status")))
           {      ack="Success";
				
				%>
			<Status><%=ack%></Status>
			<CVV2Code><%=jsonObj.get("CVV2")%></CVV2Code>
			<TransactionID><%=jsonObj.get("tokken")%></TransactionID>
			<VaultId><%=jsonObj.get("vaultid")%></VaultId>
			<VaultToken><%=jsonObj.get("valuttoken")%></VaultToken>
			<response><%=jsonObj.toString()%></response>
			
			
	
		
	<%
	}
%>


</Response>
<% 

}
catch(Exception e){
System.out.println("Error while processing...in Braintreepayment.jsp"+e.getMessage());
%>	
	<Response>
	<Status>Fail</Status>
	<ErrorCode>404</ErrorCode>
	<ShortMsg>Card could not be processed at this time.</ShortMsg>				
	<response>Error while processing</response>
	</Response>
<%
}

%>