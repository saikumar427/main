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
    String transactionid=paydet.getParameter("internalrefid");if(transactionid==null){transactionid="";}
    String cardtype=paydet.getParameter("CreditCardType");if(cardtype==null){cardtype="";}
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
  
        if("custom".equals(paypro)&& transactionid!=null && !"".equals(transactionid)){
        	System.out.println(" Start Custom Process for Transaction::::"+transactionid);
        	DBManager dm=new DBManager();
            StatusObj sob=dm.executeSelectQuery("select merchant_id,public_key,private_key from braintree_payments_requests where transactionid=? and transaction_type='direct' and status='success'",new String[]{transactionid});
        	if(sob.getStatus()){ 
				merchant_id=dm.getValue(0,"merchant_id","hb6kgw82pwjq7vn4");
              	public_key=dm.getValue(0,"public_key","jvczgyymx42k3sbf");
              	private_key=dm.getValue(0,"private_key","0f7261f4cc998e149ad879481b4e2b81");
            }
         }
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
	     System.out.println("in braintreerefund.jsp datahm: "+datahm);
	     try{  
	       	 StatusObj sb=DbUtil.executeUpdateQuery(insrtquery,new String[]{record_id,ref_id,transactionid,cardtype,"","",amt,paytype,"","",merchant_id,ipaddress,"",purpose,"","","",public_key,private_key});
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
	        //jsonObj.put("Avs",transaction.getAvsErrorResponseCode());
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
	  	          {
	  	        	  valerrorcodelist.add(error.getCode()+"");
	  	          }
	        	
	        	
	        	
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
			    		String updatequery="update  braintree_payments_requests set response_id=?,status=?,vaultid=?,valuttoken=?,responsetime=now(),response=? where record_id=?";
			    	   StatusObj sb=DbUtil.executeUpdateQuery(updatequery,new String[]{response_id,ack,vaultid,valuttoken,res,recid});
			    	}catch(Exception e){System.out.println("error in updating values at end Braintreepayment.jsp:"+e.getMessage());}
			
			    	
	 }catch(Exception e){System.out.println("Error in processing result braintreerefund.jsp::"+e.getMessage());}
return jsonObj;
}

%>

<% 
try{
	String paytype=request.getParameter("paytype");
	String recid="";
	String tid=request.getParameter("internalrefid");
	System.out.println("in braintreerefund.jsp tid: "+tid+" paytype:"+paytype);
	String eid=request.getParameter("eid");
	String amount=request.getParameter("GrandTotal");
	String payment_type=request.getParameter("payment_type");
	if(paytype==null||"".equals(paytype))paytype="refund";
	Result<Transaction> result=null;
	EventBrainPayments btp=new EventBrainPayments();
	HashMap<Object,Object> jsonObj=new HashMap<Object,Object>();
	jsonObj.put("status","payment type not availabe");

	if("refund".equals(paytype)){ 
		result=null;
	    HashMap<String,String> hm= filldata(request,paytype);
	    recid=hm.get("record_id");
	    try{
			result=btp.makeARefundPayment(hm);
	    }catch(Exception e){
	    	System.out.println("Exception in braintreerefund.jsp tid: "+tid+" ERROR: "+e.getMessage());
	    }
		if(result!=null){	 
			jsonObj=processResult(result,paytype,recid);					
		}else jsonObj.put("status","conerror"); 	 
	}
	String insertquery="insert into refund_track(eventid,tid,amount,payment_type,response,refunded_at,reference_id,status) values(?,?,?,?,?,now(),?,?)";
	StatusObj sb=DbUtil.executeUpdateQuery(insertquery,new String[]{eid,tid,amount,payment_type,jsonObj.toString(),jsonObj.get("tokken").toString(),jsonObj.get("status").toString()});

System.out.println("in braintreerefund.jsp tid: "+tid+" jsonObj: "+jsonObj);
String ack="";

%>
<Response>
	<%			
	if(!"success".equals(jsonObj.get("status"))){
	%>
	<% 
		ArrayList<String> VEL=(ArrayList<String>) jsonObj.get("VEL");
		System.out.println("VEL:::"+VEL);
		if(VEL!=null && VEL.size()>0){
			for(int i=0;i<VEL.size();i++){
	%>
			 	<TransactionID><%=request.getParameter("recall_id")%></TransactionID>
			 	<Status>Fail</Status>
			 	<ErrorCode><%=!"TRANSACTION_CANNOT_REFUND_UNLESS_SETTLED".equals(VEL.get(i))? VEL.get(i):"91506"%></ErrorCode>
	         	<ErrorMsg><%=valerror.get(VEL.get(i))%></ErrorMsg>
	<% 		
			} 
		}else{
	        if("conerror".equals(jsonObj.get("status"))){
	%>		
				<TransactionID><%=request.getParameter("recall_id")%></TransactionID>
				<Status>Fail</Status>			
				<ErrorCode>505</ErrorCode>
				<ErrorMsg>Refund could not be processed at this time.</ErrorMsg>
	
    <%
    		}else{
    %>
                <TransactionID><%=request.getParameter("recall_id")%></TransactionID>
                <Status>Fail</Status>	
                <ErrorCode>1</ErrorCode>
				<ErrorMsg>Refund could not be processed for this payment. Make sure all details are valid.</ErrorMsg>
    <% 		
    		}
	    }
	}	
    if("success".equals(jsonObj.get("status"))){
    	ack="Success";
	%>
		<Status><%=ack%></Status>
		<TransactionID><%=jsonObj.get("tokken")%></TransactionID>
		<response><%=jsonObj.toString()%></response>
	<%
	}
	%>

</Response>
<% 
}
catch(Exception e){
System.out.println("Error while processing...in braintreerefund.jsp:: "+e.getMessage());
%>	
	<Response>
	<Status>Fail</Status>
	<ErrorCode>404</ErrorCode>
	<TransactionID><%=request.getParameter("recall_id")%></TransactionID>
	<ErrorMsg>Card could not be processed at this time.</ErrorMsg>				
	<response>Error while processing</response>
	</Response>
<%
}
%>