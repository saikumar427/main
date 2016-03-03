<%@ page import="com.eventbee.general.EmailObj,com.eventbee.general.EbeeConstantsF,com.eventbee.general.EventbeeMail,com.eventbee.general.SendMailStatus" %>
<%!
public void sendTestMail(String to,String from,String bcc,String content,String subject){
try{
	System.out.println("sendTestMail");
	EmailObj emailobj=new EmailObj();				
	emailobj.setTo(to);
	emailobj.setFrom(from);
	emailobj.setBcc(bcc);
	emailobj.setHtmlMessage(content);
	emailobj.setReplyTo(to);
	emailobj.setSubject(subject);
	emailobj.setSendMailStatus(new SendMailStatus("13579", "Manual_Test_Mail", "","1"));
	EventbeeMail.sendHtmlMailPlain(emailobj);
		
}catch(Exception exp){	
	System.out.println("Exception in TestMail.jsp: "+exp);
}
}
%>
<%
String to=request.getParameter("to");
String from=request.getParameter("from");
String bcc=request.getParameter("bcc");
String content=request.getParameter("content");
String subject=request.getParameter("subject");
if(to==null) to="";if(from==null) from="";if(bcc==null) bcc="";if(content==null) content="";if(subject==null) subject="";
if(!"".equals(to.trim()) && !"".equals(from.trim()))
	sendTestMail(to.trim().toLowerCase(),from.trim().toLowerCase(),bcc.trim(),content,subject.trim());
%>
<html>
<head>
<title>TestMail</title>
</head>
<body>
<form name="testmail" id="testmail" action="" method="post">
<table  align="center">
<tr><td></td><td>Test Mail</td></tr>
<tr height="10px;"></tr>
<tr>
<td>To&nbsp;</td>
<td><input type="text" size="40" name="to" placeholder=""></td>
</tr>
<tr height="10px;"></tr>
<tr><td>Bcc&nbsp;</td>
<td>
<input type="text" size="40" name="bcc" placeholder="">
</td>
</tr>
<tr height="10px;"></tr>
<tr><td>From&nbsp;</td>
<td>
<input type="text" size="40" name="from" placeholder="">
</td>
</tr>
<tr height="10px;"></tr>
<tr><td>Subject&nbsp;</td>
<td>
<input type="text" size="40" name="subject" placeholder="">
</td>
</tr>
<tr height="10px;"></tr>
<tr><td>Content&nbsp;</td>
<td>
<textarea rows="10" cols="40" name="content" placeholder=""></textarea>
</td>
</tr>
</table>
<table align="center">
<tr>
<td id="submitbtn" align="center"><input type="submit" name="submit" value="Submit" /></td>
</tr>
</table>
</form>
</body>
</html>