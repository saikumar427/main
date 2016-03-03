<%@ page import="java.net.*" %>
<%@ page import="java.io.*,java.text.*" %>
<%@ page import="java.util.*,org.w3c.dom.*" %>
<%@ page import="com.eventbee.general.*,com.eventbee.util.*,com.eventregister.*"%>
<%
Enumeration en = request.getParameterNames();
String str = "cmd=_notify-validate";
while(en.hasMoreElements()){
String paramName = (String)en.nextElement();
String paramValue = request.getParameter(paramName);
str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue);
}
System.out.println("str::"+str);
EventbeeLogger.log("com.eventbee.main",EventbeeLogger.ERROR,"paypalnotification.jsp", "response from paypal in manager", " ****#####Values from paypal---- "+str,null);
String paypal_url=EbeeConstantsF.get("paypal.form.url","https://www.sandbox.paypal.com/cgi-bin/webscr");
URL u = new URL(paypal_url);
URLConnection uc = u.openConnection();
uc.setDoOutput(true);
uc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
PrintWriter pw = new PrintWriter(uc.getOutputStream());
pw.println(str);
pw.close();
BufferedReader in = new BufferedReader(
new InputStreamReader(uc.getInputStream()));
String res = in.readLine();
in.close();
System.out.println("res in mgrpaypalnotification::"+res);
String itemName = request.getParameter("item_name");
String itemNumber = request.getParameter("item_number");
String paymentStatus = request.getParameter("payment_status");
String paymentAmount = request.getParameter("mc_gross");
String paymentCurrency = request.getParameter("mc_currency");
String txnId = request.getParameter("txn_id");
String receiverEmail = request.getParameter("receiver_email");
String payerEmail = request.getParameter("payer_email");
String quantity =  request.getParameter("quantity");
String pending_reason =  request.getParameter("pending_reason");
if(!"".equals(itemNumber) && itemNumber!=null){
String responseqry="insert into mgr_paypal_payment_response_data(tid,response_data,status,created_at,ext_pay_id,payeremail,recieveremail) values(?,?,?,now(),?,?,?)";
DbUtil.executeUpdateQuery(responseqry, new String[]{itemNumber,str,paymentStatus,txnId,payerEmail,receiverEmail});

EventbeeLogger.log("com.eventbee.main",EventbeeLogger.ERROR,"mgrpaypalnotification.jsp", "response from paypal", " ****#####Values from paypal to new flow---- "+itemNumber,null);

if("Completed".equals(paymentStatus)){
try{
	DbUtil.executeUpdateQuery("update mgr_paypal_payment_data set status=?,updated_at=now() where tid=?", new String[]{paymentStatus,itemNumber});
	DBManager dbm=new DBManager();
	StatusObj statobj=null;
	String month="",purpose="",mgrid="",months="";
	statobj=dbm.executeSelectQuery("select invoice_month,purpose,refid from mgr_paypal_payment_data where tid=?", new String[]{itemNumber});
	if(statobj.getStatus() && statobj.getCount()>0){
		month=dbm.getValue(0,"invoice_month", "");
		purpose=dbm.getValue(0,"purpose", "");
		mgrid=dbm.getValue(0,"refid", "");
	}
	
	if("invoice_paypal".equals(purpose)){
		 if("total".equals(month)){
			 List invoicemonths=new ArrayList();
			 if(!"".equals(mgrid)) 
			    invoicemonths=DbUtil.getValues("select monthofinvoice from mgr_active_monthly_invoice where mgrid=cast(? as integer) and showstatus='Y'",new String[]{mgrid}); 
			  
			  for(int i=0;i<invoicemonths.size();i++){
				  if(i==0)
					  months="'"+invoicemonths.get(i)+"'";
				  else
					  months=months+","+"'"+invoicemonths.get(i)+"'";
			  }
			  }else
				  months="'"+month+"'";
		if("total".equals(month))
			DbUtil.executeUpdateQuery("update mgr_active_monthly_invoice set status='Charged',updated_at=now() where mgrid=cast(? as integer) and showstatus='Y' and status='Pending'",new String[]{mgrid});
		else
			DbUtil.executeUpdateQuery("update mgr_active_monthly_invoice set status='Charged',updated_at=now() where mgrid=cast(? as integer) and monthofinvoice=? and status='Pending'",new String[]{mgrid,month});
		
		 DbUtil.executeUpdateQuery("update eventlevel_active_invoice set status='Charged',paidby='manager',updated_at=now() where mgrid=cast(? as integer) and monthofinvoice in("+months+") and status='Pending'", new String[]{mgrid});
		
		String chkstatus=DbUtil.getVal("select 'Y' from mgr_config where name='require_charge' and value='Y' and userid=CAST(? as INTEGER)", new String[]{mgrid});
		  if("Y".equals(chkstatus)){
		    if("total".equals(month))
			  DbUtil.executeUpdateQuery("update mgr_config set value='N' where name='require_charge' and value='Y' and userid=CAST(? as INTEGER)", new String[]{mgrid});
		    else{
		    	String pendingstatus=DbUtil.getVal("select 'Y' from mgr_active_monthly_invoice where status='Pending' and mgrid=CAST(? as INTEGER)", new String[]{mgrid});
		    	if(pendingstatus==null)pendingstatus="N";
		    	if("N".equalsIgnoreCase(pendingstatus))
		    		DbUtil.executeUpdateQuery("update mgr_config set value='N' where name='require_charge' and value='Y' and userid=CAST(? as INTEGER)", new String[]{mgrid});
		    }
		  }	  
	}
}catch(Exception e){
EventbeeLogger.logException("com.eventbee.exception",EventbeeLogger.INFO,"paypalnotification.jsp","paypal notification","ERROR in process db",e);

} 
}
}
%>





