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
      	     }catch(Exception e){System.out.println("error in inserting values at2 Braintreepayment.jsp:"+e.getMessage());}
       
         
         System.out.println("ipaddress"+ipaddress+"ref_id:"+ref_id+"purpose:"+purpose);
    
         String merchant_id="",env="",public_key="",private_key="";
         try{    
        env=EbeeConstantsF.get("BRAINTREE_ENVIRONMENT","SANDBOX");
        env=env.trim();
        System.out.println("paypro is::"+paypro);
      
        if("braintree_mobile_app".equals(paypro))
        {  System.out.println("refid::"+ref_id+":: app :: "+paypro);
		    DBManager dm=new DBManager();
           StatusObj sob=dm.executeSelectQuery("select * from eventbee_manager_sellticket_settings where vendor_type='braintree_manager' and eventid=?::bigint",new String[]{ref_id});
       	if(sob.getStatus() && sob.getCount()>0)
           { 
		    System.out.println("getting details from eventbee_manager_sellticket_settings for event :: "+ref_id+"   :: app  :: "+paypro);
		   merchant_id=dm.getValue(0,"attrib_1","hb6kgw82pwjq7vn4").trim();
             public_key=dm.getValue(0,"attrib_2","jvczgyymx42k3sbf").trim();
             private_key=dm.getValue(0,"attrib_3","0f7261f4cc998e149ad879481b4e2b81").trim();
			  System.out.println(merchant_id);
			  System.out.println(public_key);
			  System.out.println(private_key);
           }else{
		    System.out.println("getting details from payment_types for event :: "+ref_id+"   :: app  :: "+paypro);
		    sob=dm.executeSelectQuery("select * from payment_types where attrib_5='braintree_manager' and refid=?",new String[]{ref_id});
        	if(sob.getStatus())
            { merchant_id=dm.getValue(0,"attrib_2","hb6kgw82pwjq7vn4");
              public_key=dm.getValue(0,"attrib_3","jvczgyymx42k3sbf");
              private_key=dm.getValue(0,"attrib_4","0f7261f4cc998e149ad879481b4e2b81");
			  System.out.println(merchant_id);
			  System.out.println(public_key);
			  System.out.println(private_key);
            }
		   }
        }else if("braintree_manager".equals(paypro))
         {  System.out.println("refid::"+ref_id+":: app :: "+paypro);
		    DBManager dm=new DBManager();
            StatusObj sob=dm.executeSelectQuery("select * from payment_types where attrib_5='braintree_manager' and refid=?",new String[]{ref_id});
        	if(sob.getStatus())
            { merchant_id=dm.getValue(0,"attrib_2","hb6kgw82pwjq7vn4");
              public_key=dm.getValue(0,"attrib_3","jvczgyymx42k3sbf");
              private_key=dm.getValue(0,"attrib_4","0f7261f4cc998e149ad879481b4e2b81");
			  System.out.println(merchant_id);
			  System.out.println(public_key);
			  System.out.println(private_key);
            }
         }
	  else{
        merchant_id=EbeeConstantsF.get("BRAINTREE_"+env+"_MERCHANT_ID","");
        public_key=EbeeConstantsF.get("BRAINTREE_"+env+"_PUBLIC_KEY","");
        private_key=EbeeConstantsF.get("BRAINTREE_"+env+"_PRIVATE_KEY","");
       }
	     datahm.put("payprocess",paypro);
	     datahm.put("merchant_id",merchant_id.trim());datahm.put("public_key",public_key.trim());datahm.put("private_key",private_key.trim());datahm.put("env",env.trim());
	     datahm.put("record_id",record_id); }catch(Exception e){System.out.println("");}
	     if("direct".equals(paytype) || "auth".equals(paytype)||"vault".equals(paytype)||"valid".equals(paytype) || "directvault".equals(paytype))
	     { 	  
	     String CreditCardNumber=paydet.getParameter("CreditCardNumber");if(CreditCardNumber==null){CreditCardNumber="";}
	     String CreditCardType=paydet.getParameter("CreditCardType");if(CreditCardType==null){CreditCardType="";}
	     String CVV2=paydet.getParameter("CVVCode");if(CVV2==null){CVV2="";}
	     String ExpMonth=paydet.getParameter("ExpMonth");if(ExpMonth==null){ExpMonth="0";}
	     String ExpYear=paydet.getParameter("ExpYear");if(ExpYear==null){ExpYear="0";}
	     String PostalCode=paydet.getParameter("PostalCode");if(PostalCode==null){PostalCode="";}
	     String Street1=paydet.getParameter("Street1");if(Street1==null){Street1="";}
	     String CountryName=paydet.getParameter("CountryName");if(CountryName==null){CountryName="";}
	     String  CityName=paydet.getParameter("CityName");if(CityName==null){CityName="";}
	     String PayerFirstName=paydet.getParameter("PayerFirstName");if(PayerFirstName==null){PayerFirstName="";}
	     String  PayerLastName=paydet.getParameter("PayerLastName");if(CreditCardNumber==null){CreditCardNumber="";}
	     String PayerEmail=paydet.getParameter("PayerEmail");if(PayerEmail==null){PayerEmail="";}
	     String  amt=paydet.getParameter("GrandTotal");if(amt==null){amt="0.00";}
	     amt=CurrencyFormat.getCurrencyFormat("",amt,false);
	     String orderid=paydet.getParameter("orderid");if(orderid==null){orderid="";}
	     String expirationDate=ExpMonth+"/"+ExpYear;
	     
	     datahm. put("vaultflag","false");
	     datahm.put("CreditCardNumber",CreditCardNumber);
	     datahm.put("CreditCardType",CreditCardType);
	     datahm.put("CVV2",CVV2); 	
	     datahm.put("PostalCode",PostalCode);
	     datahm.put("Street1",Street1);
	     datahm.put("CountryName",CountryName);
	     datahm.put("PayerFirstName",PayerFirstName);	    
	     datahm.put("PayerLastName",PayerLastName);
	     datahm.put("PayerEmail",PayerEmail);datahm.put("amt",amt);datahm.put("orderid",orderid);datahm.put("expirationDate",expirationDate);
	    // System.out.println("datahm"+datahm);
	     
	     CreditCardNumber=CreditCardNumber.replace(CreditCardNumber.subSequence(0,CreditCardNumber.length()-4),"xxxxxxxxxxx");
	   //System.out.println("CreditCardNumber"+CreditCardNumber);
	     
	     
	   try{  
		  StatusObj sb=DbUtil.executeUpdateQuery(insrtquery,new String[]{record_id,ref_id,transactionid,cardtype,expirationDate,CreditCardNumber,amt,paytype,"","",merchant_id,ipaddress,"",purpose,"","","",public_key,private_key});
	   }catch(Exception e){System.out.println("error in inserting values at2 Braintreepayment.jsp:"+e.getMessage());}
	     return datahm;
	     
	     }
	     else{
	    	
	    	 String coustmerid=paydet.getParameter("coustmerid");if(coustmerid==null){coustmerid="";}
	    	 String tokenid=paydet.getParameter("tokenid");if(tokenid==null){tokenid="";}
	    	 String tid=paydet.getParameter("recall_id");if(tid==null){tid="";}
	    	 String amt=paydet.getParameter("GrandTotal");if(amt==null){amt="0.00";}
	         datahm.put("tid",tid);
	         datahm.put("amt",amt);
	         datahm.put("coustmerid",coustmerid);
	         datahm.put("tokenid",tokenid);
	        // System.out.println("datahm"+datahm);
	         try{  
	   StatusObj sb=DbUtil.executeUpdateQuery(insrtquery,new String[]{record_id,ref_id,transactionid,"","","",amt,paytype,"","",merchant_id,ipaddress,tid,purpose,"","","",public_key,private_key});
	         
	         
	         }catch(Exception e){System.out.println("error in updating values at2 Braintreepayment.jsp:"+e.getMessage());}
	         return datahm;
	         
	     }
	
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
	valerror.put("CREDIT_CARD_NUMBER_IS_INVALID","Credit card number is invalid");
	try{  
	    	if (result.isSuccess()) {
	    	
	       
	    		
	    		
	        Transaction transaction = result.getTarget();
	        String tokken=transaction.getId();
	        System.out.println("Success!: " + tokken);
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
	        System.out.println("Error processing transaction:");
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
			
			    	
	 }catch(Exception e){System.out.println("Error in processing result:"+e.getMessage());}
return jsonObj;
}

public HashMap<Object,Object> processVaultResult(Result<Customer> result,String paytype,String recid){
	System.out.println("processVaultResult");
	valerror.put("2001","Card declined due to Insufficient Funds");
	valerror.put("2002","Card declined");
	valerror.put("2004","Card declined as Expired");
	valerror.put("2051","Card declined for this payment");
	valerror.put("2059","Address Verification Failed");
	valerror.put("2010","Card declined. Invalid CVV");
	valerror.put("CREDIT_CARD_NUMBER_IS_INVALID","Credit card number is invalid");
	valerror.put("2000","Do Not Honor.");

	HashMap<Object,Object> jsonObj=new HashMap<Object,Object>();
	ArrayList<String> valerrorcodelist=new ArrayList<String>();
try{
	if(result.isSuccess()){	
		Customer customer = result.getTarget();
		    jsonObj.put("status","success");
	        jsonObj.put("vaultid",customer.getId());
	    	jsonObj.put("valuttoken",customer.getCreditCards().get(0).getToken());
	    	

}else
{
	try{
	CreditCardVerification verification = result.getCreditCardVerification();
	jsonObj.put("verifystatus",verification.getStatus());
	jsonObj.put("rescode:",verification.getProcessorResponseCode());
	jsonObj.put("restext:",verification.getProcessorResponseText());
	}catch(Exception e){System.out.println("no verification"+e.getMessage());}
	
	
	
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
	        	
	        	jsonObj.put("VEL",valerrorcodelist);
	        System.out.println("Attribute: " + error.getAttribute()+"\t Code: " + error.getCode()+"\t Message: " + error.getMessage());
	        }
	        jsonObj.put("validerrormsglst",errormsglst);
	        jsonObj.put("validerrorcodelst",errorcodelst);
	           
}
	try{ 
		String response_id="",vaultid="",valuttoken="";
		if(jsonObj.get("tokken")!=null)response_id=jsonObj.get("tokken")+"";
		if(jsonObj.get("vaultid")!=null)vaultid=jsonObj.get("vaultid")+"";
		if(jsonObj.get("valuttoken")!=null)valuttoken=jsonObj.get("valuttoken")+"";
		
		String ack=jsonObj.get("status")+"";
		String res=jsonObj.toString();
		String updatequery="update  braintree_payments_requests set response_id=?,status=?,vaultid=?,valuttoken=?,responsetime=now(),response=? where record_id=?";
	   StatusObj sb=DbUtil.executeUpdateQuery(updatequery,new String[]{response_id,ack,vaultid,valuttoken,res,recid});
	}catch(Exception e1){System.out.println("error in updating values at end Braintreepayment.jsp:"+e1.getMessage());}
	
	return jsonObj;
}
catch(Exception e){System.out.println("error procesvaultresult"+e.getMessage());return null;}
}




%>


<% 

try{
	
String paytype=request.getParameter("paytype");
String recid="";
//String ccprocesstype=request.getParameter("ccprocesstype");
//	     if("authorize".equals(ccprocesstype)){ paytype="auth"; }else if("capture".equals(ccprocesstype)){
//	 		paytype="settlement";}else if("void".equals(ccprocesstype)){paytype="void";}
//	 		else if("refund".equals(ccprocesstype)){paytype="refund";}
//	 		else if("report".equals(ccprocesstype)){paytype="report";}
//	 		else{paytype="direct";}
System.out.println("paytype:"+paytype);
if(paytype==null||"".equals(paytype))paytype="direct";

Result<Transaction> result=null;

EventBrainPayments btp=new EventBrainPayments();
HashMap<Object,Object> jsonObj=new HashMap<Object,Object>();
jsonObj.put("status","payment type not availabe");
if("direct".equals(paytype))
	{
	 HashMap<String,String> hm= filldata(request,paytype);
	 recid=hm.get("record_id");
	 result= btp.makeADirectPayment(hm);
	 if(result!=null)
		 jsonObj=processResult(result,paytype,recid);
	      else
	      jsonObj.put("status","conerror"); 
   }

if("authvault".equals(paytype))
{result=null;
HashMap<String,String> hm= filldata(request,"auth");
hm.put("vaultflag","true");
recid=hm.get("record_id");
result= btp.makeAAuthorizePayment(hm);
     if(result!=null)
   	  jsonObj=processResult(result,paytype,recid);
     else
     jsonObj.put("status","conerror"); 
	  
}
 
 if("auth".equals(paytype))
 {result=null;
 HashMap<String,String> hm= filldata(request,paytype);
 recid=hm.get("record_id");
 result= btp.makeAAuthorizePayment(hm);
      if(result!=null)
    	  jsonObj=processResult(result,paytype,recid);
      else
      jsonObj.put("status","conerror"); 
	  
 }
 if("settlement".equals(paytype))
 {     result=null;   
	    HashMap<String,String> hm= filldata(request,paytype);
	    recid=hm.get("record_id");
	    result= btp.makeASettlementPayment(hm);
	    if(result!=null)
	    	jsonObj=processResult(result,paytype,recid);
	        else
	    jsonObj.put("status","conerror"); 
 }
if("void".equals(paytype))
{   result=null;
	HashMap<String,String> hm= filldata(request,paytype);
	 recid=hm.get("record_id");
	result=btp.makeAVoidPayment(hm);
	if(result!=null)
		jsonObj=processResult(result,paytype,recid);
        else
    jsonObj.put("status","conerror"); 
}
 if("refund".equals(paytype))
 { result=null;
     HashMap<String,String> hm= filldata(request,paytype);
     recid=hm.get("record_id");
	 result=btp.makeARefundPayment(hm);
	 if(result!=null)
	 {	 
	jsonObj=processResult(result,paytype,recid);					
	 }else
    jsonObj.put("status","conerror"); 	 
 }
 if("report".equals(paytype))
 {
	 HashMap<String,String> hm= filldata(request,paytype);
	 recid=hm.get("record_id");
	 Transaction trans=btp.getReport(hm);
	 if(trans!=null)
	 {jsonObj.put("reportobj",trans);
      List al=new ArrayList();
	 for(int i=0;i<trans.getStatusHistory().size();i++)
		 al.add(trans.getStatusHistory().get(i));
	 jsonObj.put("reportobjhistory",al); 	 
	 }else
	  jsonObj.put("reportobj",""); 	
 }
 
 if("vault".equals(paytype))
	{
	 Result<Customer> cresult=null;
	 HashMap<String,String> hm= filldata(request,paytype);
	 hm.put("vaultflag","true");
	 recid=hm.get("record_id");
	  cresult= btp.validatecreditcard(hm);
	      if(cresult!=null)
	    	  jsonObj=processVaultResult(cresult,paytype,recid);
	      else
	      jsonObj.put("status","conerror"); 
}

 
 if("vaultdirect".equals(paytype))
	{
	 HashMap<String,String> hm= filldata(request,paytype);
	 recid=hm.get("record_id");
	 result= btp.valutTransaciton(hm);
	 if(result!=null)
	jsonObj=processResult(result,paytype,recid);
	      else
   jsonObj.put("status","conerror"); 
}
 
 
 if("valid".equals(paytype))
	{
	 HashMap<String,String> hm= filldata(request,paytype);
	 recid=hm.get("record_id");
	 System.out.println("result"+ btp.validatecreditcard(hm));
	// if(result!=null)
	//jsonObj=processResult(result,paytype,recid);
	  //    else
//jsonObj.put("status","error");  
	}
 
 if("directvault".equals(paytype))
{ 
        System.out.println("paytype is:"+paytype);
        HashMap<String,String> hm= filldata(request,"direct");
        //Result<Customer> cresult=null;
        recid=hm.get("record_id");
        //hm.put("vaultflag","true");
        //cresult= btp.validatecreditcard(hm);
		result= btp.makeADirectPayment(hm);
    if(result!=null)
    {  jsonObj=processResult(result,"direct",recid);//processVaultResult(cresult,paytype,recid);   
             Result<Customer> cresult=null;
			 if(jsonObj.toString().indexOf("success")>-1)
             {  hm.clear();
            	hm= filldata(request,paytype);
                recid=hm.get("record_id");
				hm.put("vaultflag","true");
                cresult= btp.validatecreditcard(hm);
                    //jsonObj=processResult(result,paytype,recid);
            //result= btp.makeADirectPayment(hm);
             }
              if(cresult!=null)
              {  HashMap jsonObj2=processVaultResult(cresult,paytype,recid);//processResult(result,paytype,recid);
                 //jsonObj.put("tokken",jsonObj2.get("tokken"));
                 if(jsonObj2.toString().indexOf("success")>-1){
            		jsonObj.put("vaultid",jsonObj2.get("vaultid"));
            		jsonObj.put("valuttoken",jsonObj2.get("valuttoken"));
            	}else{
            		jsonObj.put("vaultid","");
            		jsonObj.put("valuttoken","");
            	}
              }
           else
        jsonObj.put("status","conerror"); 
    }     
    else
    jsonObj.put("status","conerror"); 
}

System.out.println("jsonObj:"+jsonObj);
//out.println(jsonObj.toString());

String ack="";

%>

<%@page import="com.eventbee.general.formatting.CurrencyFormat;"%>
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