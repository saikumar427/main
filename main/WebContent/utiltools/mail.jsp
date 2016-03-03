<%@ page import="java.util.*,java.io.*,javax.mail.*,javax.mail.internet.*" %>
<%@ page import="com.eventbee.general.EbeeConstantsF,com.eventbee.general.DbUtil,com.eventbee.general.DBManager,com.eventbee.general.StatusObj" %>

<%!
public static class SMTPAuthenticator extends javax.mail.Authenticator {
	private String uid="";
	private String password="";
	public SMTPAuthenticator(String uid, String pwd){
		this.uid=uid;
		this.password=pwd;
	}
	public PasswordAuthentication getPasswordAuthentication() {
   		return new PasswordAuthentication(uid, password);
	}
}
 public static void sendHtmlMailPlain(String smtphost, String content, String subject,String fromemail,List emails){
		
}
private static javax.mail.Session getMailSession(String smtphost){
		javax.mail.Session  mailSession=null;
		try{
			
			System.out.println("\n mail sending using smtphost: "+smtphost);
			Properties props=null;
			if("smail".equals(smtphost)){
				props = new Properties();
				props.put("mail.transport.protocol", "smtp");
				props.put("mail.smtp.host", "smail.eventbee.com");
				props.put("mail.smtp.auth", "false");
				mailSession = Session.getInstance(props);
			}else if("bmail".equals(smtphost)){
				props = new Properties();
				props.put("mail.transport.protocol", "smtp");
				props.put("mail.smtp.host", "bmail.eventbee.com");
				props.put("mail.smtp.auth", "false");
				mailSession = Session.getInstance(props);
			}
			else{	
				props = new Properties();
				props.put("mail.transport.protocol", "smtp");
				props.put("mail.smtp.host", "smtp.sendgrid.net");
				props.put("mail.smtp.auth", "true");
				String uid = EbeeConstantsF.get("sendgrid.uid" ,"musrifbala@gmail.com");
				String pwd = EbeeConstantsF.get("sendgrid.pwd" ,"eventbee2112");
				Authenticator auth = new SMTPAuthenticator(uid,pwd);
				mailSession = Session.getInstance(props, auth);	
			}
			
		}catch(Exception e){
			System.out.println("EventbeeMail.java getMailSession: "+e.getMessage());
		}
		return mailSession;
	}
%>	

<%
String smtphost=request.getParameter("host");
String cid=request.getParameter("cid");
String lid=request.getParameter("lid");
String content="",subject="",fromemail="";
List receivers=DbUtil.getValues("select emailid from email_list_members where listid=?",new String[]{lid});
if(receivers.size()==0){
	out.println("<br>no records for lid= "+lid);
	return;
}
DBManager dbm=new DBManager();
StatusObj statobj=null;
String query="select content,fromemail,subject from email_blast_content where contentid=?";
statobj=dbm.executeSelectQuery(query,new String[]{cid});
if(!statobj.getStatus()){
	out.println("<br>Unable to get content for cid= "+cid);
	return;
}
 if(statobj.getCount()==0){
 	out.println("<br>no record for cid= "+cid);
	return;
 }
 if(statobj.getCount()>1){
 	out.println("<br>"+statobj.getCount()+" records for cid= "+cid);
	return;
 }
 content=dbm.getValue(0,"content","");
 if("".equals(content.trim())){
 	out.println("<br>no content for cid= "+cid);
	return;
 }
   
 subject=dbm.getValue(0,"subject","");
 fromemail=dbm.getValue(0,"fromemail","");
 out.println("<br>subject: "+subject);
 out.println("<br>from: "+fromemail);
 out.println("<br>receivers count: "+receivers.size());
 out.flush();
 try{
               javax.mail.Session mailSession = getMailSession(smtphost);
 		  	 MimeMessage msg = new MimeMessage( mailSession );
 			if(msg!=null){
 			msg.setFrom( new InternetAddress(fromemail));
 			msg.setSubject(subject);
 			MimeBodyPart textpart=new MimeBodyPart();
 			    textpart.setText("");
 					textpart.addHeaderLine("Content-Type: text/plain; charset=\"iso-8859-1\"");
 					textpart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");
 			    MimeBodyPart htmlpart=new MimeBodyPart();
 					htmlpart.setText(content);
 					htmlpart.addHeaderLine("Content-Type: text/html; charset=\"iso-8859-1\"");
 					htmlpart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");
 			    Multipart mp2=new MimeMultipart("alternative");
 					mp2.addBodyPart(textpart);
 					mp2.addBodyPart(htmlpart);
 			    msg.setContent(mp2);
 	            msg.setReplyTo(new Address[]{new InternetAddress(fromemail)});
 				msg.setSentDate( new java.util.Date() );
             for(int i=0;i<receivers.size();i++){
 			try{
 			InternetAddress[]inarr=InternetAddress.parse((String)receivers.get(i), false );
 			 msg.setRecipients( javax.mail.Message.RecipientType.TO, inarr );
 			 Transport.send(msg);
 			//setOtherRecepients(msg,emailobj);// cc and bcc
 			   out.println("<br>sent to msg: "+ (i+1) +" "+receivers.get(i));
             		}catch(Exception e){
             			out.println("<br>failed to send: "+ (i+1) +" "+receivers.get(i) +" error: "+e.getMessage());
             		}       
 			out.flush();
 			}
 			}
 			else{
 			out.println("<br>no mail object");
 			}
 		}catch(Exception ex){
 		out.println("<br>Exception from EventbeeMail message ="+ex.getMessage());
		}
   
   out.println("<br>Blast complete");
%>