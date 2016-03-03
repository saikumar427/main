<%@page import="com.braintreegateway.*" %>
<%@page import="java.util.*" %>
<%@ page import="org.json.JSONObject, com.eventbee.general.*"%>
<%@ page import="com.eventbee.general.formatting.CurrencyFormat"%>
<%! 
private  BraintreeGateway intialize(HashMap<String,String> paydet)
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

public HashMap<Object,Object> processVaultResult(Result<Customer> result){
	System.out.println("processVaultResult");
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
	jsonObj.put("rescode",verification.getProcessorResponseCode());
	jsonObj.put("restext",verification.getProcessorResponseText());
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
	  	        valerrorcodelist.add(error.getCode()+"");
	  	        
	        	
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
		}catch(Exception e1){System.out.println("error in updating values at end Braintreepayment.jsp:"+e1.getMessage());}
	
	return jsonObj;
}
catch(Exception e){System.out.println("error procesvaultresult"+e.getMessage());return null;}
}



%>

<%
String vaultid=request.getParameter("coustmerid");
String new_token=request.getParameter("ntoken");
String checktype=request.getParameter("checktype");
System.out.println("checking type::::"+checktype);
if(checktype==null)checktype="verify";


     
     String ipAddress  = request.getHeader("X-FORWARDED-FOR");  
     if(ipAddress == null)  
     ipAddress = request.getRemoteAddr();  
              
     String purpose=request.getParameter("purpose");if(purpose==null){purpose="";}
     String ref_id=request.getParameter("ref_id");if(ref_id==null){ref_id="";}
       
     String record_id="";
     try{ record_id=DbUtil.getVal("select nextval('braintree_record_id')",new String[]{});
  	  }catch(Exception e){System.out.println("error in geting recordid values  BrainVAultVerification.jsp:"+e.getMessage());}
   
     




String merchant_id="",env="",public_key="",private_key="";
System.out.println("vaultid::"+vaultid +"new_token::"+new_token);
HashMap<String,String> valerror= new HashMap<String,String>(); 
valerror.put("2001","Card declined due to Insufficient Funds");
valerror.put("2002","Card declined");
valerror.put("2004","Card declined as Expired");
valerror.put("2051","Card declined for this payment");
valerror.put("2059","Address Verification Failed");
valerror.put("2010","Card declined. Invalid CVV");
valerror.put("2000","Do Not Honor.");
valerror.put("2005","Credit card number is invalid");
valerror.put("CREDIT_CARD_NUMBER_IS_INVALID","Credit card number is invalid");
HashMap resultprocess =new HashMap();
try{    
env=EbeeConstantsF.get("BRAINTREE_ENVIRONMENT","SANDBOX");//PRODUCTION
env=env.trim();
merchant_id=EbeeConstantsF.get("BRAINTREE_"+env+"_MERCHANT_ID","sg48xmv2772wb49y");
public_key=EbeeConstantsF.get("BRAINTREE_"+env+"_PUBLIC_KEY","jwgbsv6yftbcgjk9");
private_key=EbeeConstantsF.get("BRAINTREE_"+env+"_PRIVATE_KEY","xggt2m47bfrd7jnm");
HashMap<String,String> payhm=new HashMap<String,String>();
payhm.put("env",env.trim());
payhm.put("merchant_id",merchant_id);
payhm.put("public_key",public_key);
payhm.put("private_key",private_key);
System.out.println("payhm::"+payhm);

String insrtquery="insert into braintree_payments_requests( "
    +"record_id,ref_id,transaction_date," +
    "transaction_type,status,merchant_id,ipaddresss,purpose,vaultid,valuttoken" +
    ",responsetime,response) values (?,?,now(), ?,?,?,?,?,?,?,now(),?) ";
  try{  
	  StatusObj sb=DbUtil.executeUpdateQuery(insrtquery,new String[]{record_id,ref_id,checktype,"pending",merchant_id,ipAddress,purpose,vaultid,new_token,""});
 }catch(Exception e){System.out.println("error in inserting values at2 BrainVAultVerification.jsp:"+e.getMessage());}
 


BraintreeGateway gateway = intialize(payhm);
if(gateway!=null)
{if(vaultid!=null )
 {	if("verify".equals(checktype))  
    {
	 try{
      CustomerRequest crequest = new CustomerRequest()
			.creditCard()
			.options()
			.updateExistingToken(new_token)
			.verifyCard(true)
			.done()
			.done();
      	  Result<Customer> updateResult = gateway.customer().update(vaultid,crequest);	
      	  System.out.println("Resulted Updated:::"+updateResult);
		  resultprocess=processVaultResult(updateResult);
 		  
	 }catch(Exception e){System.out.println("Exeception while verifing card: BrainVAultVerification.jsp:::"+e.getMessage());
	    resultprocess.put("status","conerror");
	 }
			 
    }
   else if("delete".equals(checktype))
    {
	try{  Result<Customer> deletedres=gateway.customer().delete(vaultid);
	    
 	      resultprocess.put("status",deletedres.isSuccess()==true?"success":"error");
	   	   }catch(Exception e){System.out.println("Exeception while deleteing card:BrainVAultVerification.jsp:::"+e.getMessage());
  	     resultprocess.put("status","conerror");
  	   }
    } 
 }
  else
	  resultprocess.put("status","conerror");
     
}
else
	  resultprocess.put("status","conerror");

try{
String updatequery="update  braintree_payments_requests set status=? ,responsetime=now(),response=? where record_id=?";
StatusObj sb=DbUtil.executeUpdateQuery(updatequery,new String[]{resultprocess.get("status")+"",resultprocess+"",record_id});
}catch(Exception e){System.out.println("error in updating values at end BrainVAultVerification.jsp:::"+e.getMessage());}


String ack="";
%><Response>
<%			if(!"success".equals(resultprocess.get("status")))
		{
%>


<% 
ArrayList<String> VEL=(ArrayList<String>) resultprocess.get("VEL");
if(VEL!=null && VEL.size()>0)
{for(int i=0;i<VEL.size();i++)
{
%>
		 <ErrorCode><%=!"CREDIT_CARD_NUMBER_IS_INVALID".equals(VEL.get(i))? VEL.get(i):"81715"%></ErrorCode>
         <ShortMsg><%=valerror.get(VEL.get(i))%></ShortMsg>

<% } }

else if(VEL==null &&  resultprocess.get("rescode")!=null){
	  %>					
		<ErrorCode><%=resultprocess.get("rescode")%></ErrorCode>
		<ShortMsg><%=resultprocess.get("restext")%></ShortMsg>
	

       <%}
	


else{

        if("conerror".equals(resultprocess.get("status"))){
    %>					
		<ErrorCode>505</ErrorCode>
		<ShortMsg>Request could not be processed at this time.</ShortMsg>
	

         <%}%><%else{ %>
         
            <ErrorCode>1</ErrorCode>
			<ShortMsg>Card declined Check all details are valid.</ShortMsg>
         <% }}


}	
     if("success".equals(resultprocess.get("status")))
       {      ack="Success";
			
			%>
		<Status><%=ack%></Status>
		<CVV2Code><%=resultprocess.get("CVV2")%></CVV2Code>
		<TransactionID><%=resultprocess.get("tokken")%></TransactionID>
		<VaultId><%=resultprocess.get("vaultid")%></VaultId>
		<VaultToken><%=resultprocess.get("valuttoken")%></VaultToken>
		<response><%=resultprocess.toString()%></response>
		
		

	
<%
}
%>







</Response>
<% 



}catch(Exception e){
	e.printStackTrace();
	%>	
	<Response>
	<Status>Fail</Status>
	<ErrorCode>404</ErrorCode>
	<ShortMsg>Request could not be processed at this time.</ShortMsg>				
	<response>Error while processing</response>
	</Response>
<%
System.out.println("::Exception in Braintree Verification:: "+e.getMessage());}
System.out.println(resultprocess);


%>