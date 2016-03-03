<%@ page import="com.eventbee.general.MailUtil,com.eventbee.general.DBManager,com.eventbee.general.StatusObj"%>
<%@ page import="com.eventbee.general.DbUtil,javax.mail.*,javax.mail.internet.*,java.text.*,java.util.HashMap" %>
<%
  String refid=request.getParameter("refid");
  DBManager dbm=new DBManager();
  StatusObj statobj=null;
  String content="",fromemail="",subject="",replyto="";
  String invoicemanageqry="select html_message,mail_from,mail_subject,mail_to from stray_email where status='R' and purpose='MONTHLY_INVOICES' and ref_id=?";
  String smtphost="sendgrid";
  statobj=dbm.executeSelectQuery(invoicemanageqry,new String[]{refid});
  HashMap hmap=null; 
  try{
  if(statobj.getStatus() && statobj.getCount()>0){
    hmap=new HashMap();
	hmap.put("content",dbm.getValue(0,"html_message",""));
    hmap.put("fromemail",dbm.getValue(0,"mail_from",""));
	hmap.put("subject",dbm.getValue(0,"mail_subject",""));
	hmap.put("tomail",dbm.getValue(0,"mail_to",""));
	DbUtil.executeUpdateQuery("update stray_email set status='K' where purpose='MONTHLY_INVOICES' and ref_id=?",new String[]{refid});
    }
  MimeMessage msg = MailUtil.getMsgObject(smtphost,hmap);
  if(msg!=null){
   msg.setSentDate( new java.util.Date());
   InternetAddress[]inarr=InternetAddress.parse((String)hmap.get("tomail"), false);
   msg.setRecipients( javax.mail.Message.RecipientType.TO, inarr );
   Transport.send(msg);
   out.println("mail sent succesfully to : "+(String)hmap.get("tomail"));
   DbUtil.executeUpdateQuery("update stray_email set status='S' where purpose='MONTHLY_INVOICES' and ref_id=?",new String[]{refid});
   } 	 
  }catch(Exception e){
     System.out.println("Exception occured in invoiceemail");
   }	 
%>