<%@page import="com.eventbee.general.formatting.CurrencyFormat,com.eventbee.general.SendMailStatus"%>
<%@page import="com.eventbee.general.DbUtil,com.eventbee.general.DBManager,com.eventbee.general.StatusObj" %>
<%@page import="com.eventbee.general.EmailObj,com.eventbee.general.EventbeeMail,com.eventbee.general.formatting.CurrencyFormat" %>
<%@page import="java.util.HashMap,com.event.helpers.I18n,com.event.i18n.dbmanager.ConfirmationEmailDAO"%>
<%
  String userid=request.getParameter("userid");
  if(userid==null)userid="";
  //System.out.println("userid is:"+userid);
  String mgrfname="",mgrlname="",loginname="",mgrid="",paidamount="",beecredits="",fromemail="",subject="",replyemail="",content="",mgremail="";
  String query="select first_name,last_name,a.user_id,login_name,email from user_profile u,authentication a where a.user_id=u.user_id and (u.user_id=? or login_name=?)";
  DBManager dbm=new DBManager();
  StatusObj statobj=null;
  if(!"".equalsIgnoreCase(userid)){
  try{
  statobj=dbm.executeSelectQuery(query, new String[]{userid,userid});
  //System.out.println("useraccount count is:"+statobj.getCount()+" :: "+userid);
  if(statobj.getStatus() && statobj.getCount()>0){
	  mgrfname=dbm.getValue(0,"first_name", "");
	  mgrlname=dbm.getValue(0,"last_name", "");
	  loginname=dbm.getValue(0,"login_name", "");
	  mgrid=dbm.getValue(0,"user_id", "");
	  mgremail=dbm.getValue(0,"email", "");
	  paidamount=DbUtil.getVal("select amount from  ccdata where purpose='beecreditsquota' and ref_id=? and status='Success' order by status_time desc limit 1", new String[]{mgrid});
	  paidamount=CurrencyFormat.getCurrencyFormat("", paidamount,true);
	  //System.out.println("paidamount  is:"+paidamount+" :: "+userid);
	  beecredits=DbUtil.getVal("select purchased_bee_credits from mgr_accured_credits_details where mgr_id=CAST(? as BIGINT) order by purchased_at desc limit 1", new String[]{mgrid});
	  //beecredits=CurrencyFormat.getCurrencyFormat("", beecredits,true);
	  beecredits=beecredits.replace(".00", "");
	  //System.out.println("beecredits  is:"+beecredits+" :: "+userid);
	  /* statobj=dbm.executeSelectQuery("select * from email_templates where purpose='BEECREDITS_CONFIRMATION' and groupid='0'", null);
	  if(statobj.getStatus() && statobj.getCount()>0){
		  subject=dbm.getValue(0,"subjectformat","");
		  fromemail=dbm.getValue(0,"fromemail","");
		  replyemail=dbm.getValue(0,"replytoemail","");
		  content=dbm.getValue(0,"htmlformat","");
	  } */
	  String lang=I18n.getLanguageFromSession();
		HashMap<String, String> purposeHm= new HashMap<String,String>();
		purposeHm.put("purpose", "BEECREDITS_CONFIRMATION");
		purposeHm.put("isCustomFeature", "N");
		HashMap<String, String> emailTemplate=new HashMap<String, String>();
		ConfirmationEmailDAO emailDao=new ConfirmationEmailDAO();
		emailTemplate=(HashMap<String, String>)emailDao.getData(purposeHm, lang, "").get("emailTemplate");
		subject=emailTemplate.get("subjectFormat");
		fromemail=emailTemplate.get("fromMail");
		replyemail=emailTemplate.get("replyToMail");
		content=emailTemplate.get("htmlFormat");
	  //System.out.println("subject  is: "+subject);
	  //System.out.println("fromemail  is: "+fromemail);
	  //System.out.println("after getting content  is: "+userid);
	  content=content.replace("$firstName", mgrfname);
	  content=content.replace("$lastName", mgrlname);
	  content=content.replace("$paidAmount", paidamount);
	  content=content.replace("$beeid", loginname);
	  content=content.replaceAll("\\$beeCredits", beecredits);
	  //System.out.println("content  is: "+content);
	  EmailObj emailobj=EventbeeMail.getEmailObj();
	  emailobj.setSubject(subject);
	  emailobj.setFrom(fromemail);
	  emailobj.setHtmlMessage(content);
	  emailobj.setReplyTo(replyemail);
	  emailobj.setTo(mgremail);
	  emailobj.setSendMailStatus(new SendMailStatus("13579","BEECREDITS_CONFIRMATION","","1"));
	  //System.out.println("before sending mail "+content);
	  EventbeeMail.sendHtmlMailPlain(emailobj);
	  EventbeeMail.insertStrayEmail(emailobj,"html","S");
	  out.println("Mail Sent Successfully");
  }
  else
	  out.println("Invalid Bee ID (or) User ID");
}catch(Exception e){
	  System.out.println("Exception Occured in while sending beecreditsemail :: "+e.getMessage()); 
  }
  }
  %>
<html>
<head>
</head>
<body>
<form name="bcmail" id="bcmail" method="post" action="beecreditsmail.jsp">
<table>
<tr><td>Bee ID (or) User ID </td><td><input type="text" name="userid" id="userid"/></td><td><input type="submit" name="submit"  value="Send Email"/></td>
</table>
</form>
</body>
</html>