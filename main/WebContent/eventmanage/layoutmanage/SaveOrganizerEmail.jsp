<%@page import="com.eventbee.layout.DBHelper"%>
<%@page import="com.eventbee.layout.EventDBHelper"%>
<%
System.out.println("!!! SaveOrganizerEmail.jsp");
String msg="Email sent to event manager";
String purpose = request.getParameter("purpose");
String eventid = request.getParameter("id");
String fromemail=request.getParameter("from_email");
String fromname=request.getParameter("from_name");
String subject=request.getParameter("subject");
String message=request.getParameter("note");
String captchatxt=request.getParameter("captchamgr");
String text="";
String formname=request.getParameter("formnamemgr");
String captchasession=(String)session.getAttribute("captcha_"+formname);
System.out.println("Email to Manager captchasession: "+captchasession);
if(captchatxt!=null)
	text=captchatxt.trim();
else{text="";}
if(!text.equalsIgnoreCase(captchasession)){
	out.print("<data>Error</data>");
}else{
	EventDBHelper.insertOrganizerEmail(purpose, eventid, fromname, fromemail, subject, message);
	session.removeAttribute("captcha_"+formname);   
	out.print("<data>"+msg+"</data>");
}
%>