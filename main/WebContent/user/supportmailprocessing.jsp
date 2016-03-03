<%@ page import="com.eventbee.general.DbUtil,com.eventbee.general.EventbeeMail,org.json.JSONObject"%>
<%@ page import="com.eventbee.general.EmailObj,com.eventbee.general.DateUtil,com.eventbee.general.SendMailStatus"%>
<%@ page import="com.event.helpers.I18n"%>
<%
  String username=request.getParameter("username");
  String useremail=request.getParameter("useremail");
  String phone=request.getParameter("phone");
  String eventurl=request.getParameter("eventurl");
  String message=request.getParameter("message");
  String token=request.getParameter("token");
  if("<Click here> and type your Name - Required".equals(username.trim()))username="";
  if("<Click here> and type your Email - Required".equals(useremail.trim()))useremail="";
  if("<Click here> and type your Phone".equals(phone.trim()))phone="";
  if("<Click here> and type your Event URL".equals(eventurl.trim()))eventurl="";
  if("<Click here> and type your Message - Required".equals(message.trim()))message="";
  try{
  if(!"".equals(useremail)){  
  EmailObj emailobj=EventbeeMail.getEmailObj();
  StringBuffer sb=new StringBuffer();
  emailobj.setFrom("messages@eventbee.com");
	if("1234".equals(token))
	emailobj.setTo("support@eventbee.com");
	else
	emailobj.setTo("support@volumebee.com");	
	emailobj.setSubject("Eventbee Support Email");
	String mailmessage="";
	sb.append("<p>"+I18n.getString("myo.name.header.lbl")+": "+username);          
	if(!"".equals(phone))
	sb.append("<br>"+I18n.getString("rep.disflds.phone.lbl")+": "+phone);     
	if(!"".equals(eventurl))
	sb.append("<br>"+I18n.getString("em.actions.eventurl.lbl")+": "+eventurl);
	sb.append("<p>");
	sb.append("<pre style='font-family: verdana; font-size: 12px; line-height: 140%; padding-left: 2px; margin: 0px; white-space: pre-wrap; word-wrap: break-word;'>"+message+"</pre>");
	mailmessage=sb.toString();
	emailobj.setHtmlMessage(mailmessage);
	emailobj.setReplyTo(useremail);
	emailobj.setSendMailStatus(new SendMailStatus("13579","Eventbee_Support_Mail","","1"));
	EventbeeMail.sendHtmlMailPlain(emailobj);
	String currentdate=DateUtil.getCurrDBFormatDate();
	String straymailinsertqry="insert into stray_email(mail_count,status,sch_time,unit_id,purpose,html_message,mail_to,emailtype,"+
	" mail_from,mail_subject,mail_replyto) values(to_number('1','99999999'),'S',to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'),CAST('13579' as integer),'Eventbee_Support_Mail',"+
	" ?,'support@eventbee.com','html',?,'"+I18n.getString("hpsm.contact.support.subject")+"',?)";
	DbUtil.executeUpdateQuery(straymailinsertqry, new String[]{currentdate,mailmessage,useremail,useremail});
    JSONObject json=new JSONObject();
    json.put("result","success");
    out.println(json.toString());  
  }  
  }catch(Exception e){
	  System.out.println("Exception occured in sending supportmail:"+e.getMessage());
  }
%>