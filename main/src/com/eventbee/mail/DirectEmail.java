package com.eventbee.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;

import com.eventbee.filters.SMTPHostProps;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.general.EventbeeLogger;
import com.eventbee.general.SendMailStatus;
import com.eventbee.interfaces.MailerInterface;

public class DirectEmail {
	
	public static int sendHtmlMailPlain(EmailObj emailobj){
		MailerInterface mi=getMailerInterface(emailobj);
		if(mi!=null)
			return mi.processHtmlMail(emailobj);
		
		return 0;
	}
	
	public static MailerInterface getMailerInterface(EmailObj emailobj){
		MailerInterface mi=null;		
		try{
			String smtphost=SMTPHostProps.get("DEFAULT","AmzonPhpMail");
			if(emailobj.getSendMailStatus()!=null)
				smtphost = SMTPHostProps.get(emailobj.getSendMailStatus().getPurpose(),"").trim();
			if("".equals(smtphost)) smtphost=SMTPHostProps.get("DEFAULT","AmzonPhpMail");
			System.out.println("smtphost: "+smtphost);
			try{
				mi = (MailerInterface)Class.forName("com.eventbee.mail."+smtphost).newInstance();
			}catch(Exception e){
				System.out.println("Exception in getMailerInterface inner try: "+e.getMessage());
				mi = (MailerInterface)Class.forName("com.eventbee.mail."+smtphost).newInstance();
			}
		}catch(Exception e){
			System.out.println("Exception in getMailerInterface: "+e.getMessage());
		}
		return mi;
	}
	
	public static int mailHtmlData(EmailObj emailobj,Session mailSession){
		int status=0;
		try{
		MimeMessage msg = new MimeMessage( mailSession );
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeMail.java","in sendHtmlMailPlain mail session is-------->:"+msg,"",null);
		if(msg!=null){
			msg.setFrom( new InternetAddress( emailobj.getFrom() ) );
			InternetAddress[]inarr= InternetAddress.parse(emailobj.getTo() , false );
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeMail.java","in sendHtmlMailPlain after creating internetaddress object-------->:","",null);

			msg.setRecipients( javax.mail.Message.RecipientType.TO, inarr );
			setOtherRecepients(msg,emailobj);// cc and bcc
			msg.setSubject( emailobj.getSubject(),"UTF-8" );
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeMail.java","in sendHtmlMailPlain after setting email object to mail session-------->:","",null);
			MimeBodyPart textpart=new MimeBodyPart();
			textpart.setText(emailobj.getTextMessage(),"UTF-8");
			textpart.addHeaderLine("Content-Type: text/plain; charset=\"UTF-8\"");
			textpart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");

			MimeBodyPart htmlpart=new MimeBodyPart();
			htmlpart.setText(emailobj.getHtmlMessage(),"UTF-8");
			htmlpart.addHeaderLine("Content-Type: text/html; charset=\"UTF-8\"");
			htmlpart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeMail.java","in sendHtmlMailPlain after setting email object to MimeBodyPart object-------->:","",null);
			Multipart mp2=new MimeMultipart("alternative");
			mp2.addBodyPart(textpart);
			mp2.addBodyPart(htmlpart);
			/*if("Y".equals(emailobj.getPdfTicket())){
				MimeBodyPart filepart=new MimeBodyPart();
				String filepath=EbeeConstantsF.get("temp.upload.filepath","C:\\uploads\\");
				FileDataSource fds = new FileDataSource(filepath+emailobj.getFileName());
				filepart.setDataHandler(new DataHandler(fds));
				filepart.setFileName(fds.getName());
				mp2.addBodyPart(filepart);
			}*/
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeMail.java","in sendHtmlMailPlain after setting MimeBodyPart object to Multipart object-------->:","",null);
			msg.setContent(mp2);
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeMail.java","in sendHtmlMailPlain after setting Multipart object to mailsession object-------->:","",null);
			String replyTo=emailobj.getReplyTo();
			if(replyTo==null)replyTo=emailobj.getFrom();
			msg.setReplyTo(new Address[]{new InternetAddress( replyTo ) });
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeMail.java","in sendHtmlMailPlain after setting mailto object to mailsession object-------->:","",null);
			msg.setSentDate( new java.util.Date() );
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeMail.java","in sendHtmlMailPlain Before Transaport.send-------->:","",null);
			Transport.send( msg );
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeMail.java","in sendHtmlMailPlain After Transaport.send-------->:","",null);
			SendMailStatus smstat=emailobj.getSendMailStatus();
			status=1;
			//if(smstat != null)smstat.insertStatus();
		}//end of msg null
		
		}catch(Exception ex){
			status=0;
			System.out.println("Exception from DirectEmail.java: sendHtmlMailPlain() message ="+ex.getMessage());
		}
		return status;
	}
	
	public static void sendTextMailPlain(EmailObj emailobj){
		
		MailerInterface mi=getMailerInterface(emailobj);
		if(mi!=null)
			mi.processTextMail(emailobj);
		
	}
	
	public static void mailPlainData(EmailObj emailobj,Session mailSession){
		try{
			
			//javax.mail.Session mailSession = getMailSession();
			//javax.mail.Session mailSession=null;
			String smtphost = SMTPHostProps.get(emailobj.getSendMailStatus().getPurpose(),"").trim();
			MailerInterface mi = (MailerInterface)Class.forName(smtphost).newInstance();
			mailSession=mi.getMailSession();
			MimeMessage msg = new MimeMessage( mailSession );
			if(msg!=null){
				msg.setFrom( new InternetAddress( emailobj.getFrom() ) );
				InternetAddress[]inarr= InternetAddress.parse(emailobj.getTo() , false );
				msg.setRecipients( javax.mail.Message.RecipientType.TO, inarr );
				setOtherRecepients(msg,emailobj);// cc and bcc
				msg.setSubject( emailobj.getSubject(),"UTF-8" );
	
				msg.setText(emailobj.getTextMessage(),"UTF-8");
				String replyTo=emailobj.getReplyTo();
				if(replyTo==null)replyTo=emailobj.getFrom();
				msg.setReplyTo(new Address[]{new InternetAddress( replyTo ) });
				msg.setSentDate( new java.util.Date() );
				//msg.setHeader( "Event-Mailer", "eventbeeMailer" );
				Transport.send( msg );
				SendMailStatus smstat=emailobj.getSendMailStatus();
				if(smstat != null)smstat.insertStatus();
			}//end of msg null
		}catch(Exception ex){
			System.out.println("Exception from DirectEmail.java SendTextMailPlain():="+ex.getMessage());
		}
	}

	public static void sendHtmlMailCampaign(EmailObj emailobj){

		try{

			javax.mail.Session   mailSession=(javax.mail.Session) new InitialContext().lookup( "java:/Mail" );
			javax.mail.Message  msg=null;
	
			Properties prop=mailSession.getProperties() ;
	
			String emailto=emailobj.getCampId()+"-"+emailobj.getMemId();
			emailto="return-list-"+emailto+"@"+EbeeConstantsF.get("emailserver.name" ,"eventbee.com");
			prop.put("mail.smtp.from", emailto);
	
			msg = new MimeMessage( mailSession );
	
			if(msg!=null){
				msg.setFrom( new InternetAddress( emailobj.getFrom() ) );
				InternetAddress[]inarr= InternetAddress.parse(emailobj.getTo() , false );
				msg.setRecipients( javax.mail.Message.RecipientType.TO, inarr );
				setOtherRecepients(msg,emailobj);// cc and bcc
		
				msg.setSubject( emailobj.getSubject() );
		
		
				MimeBodyPart textpart=new MimeBodyPart();
		
				textpart.setText(emailobj.getTextMessage());
				textpart.addHeaderLine("Content-Type: text/plain; charset=\"UTF-8\"");
				textpart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");
		
				MimeBodyPart htmlpart=new MimeBodyPart();
				htmlpart.setText(emailobj.getHtmlMessage());
				htmlpart.addHeaderLine("Content-Type: text/html; charset=\"UTF-8\"");
				htmlpart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");
		
				Multipart mp2=new MimeMultipart("alternative");
				mp2.addBodyPart(textpart);
				mp2.addBodyPart(htmlpart);
				msg.setContent(mp2);
				String replyTo=emailobj.getReplyTo();
				if(replyTo==null)replyTo=emailobj.getFrom();
				msg.setReplyTo(new Address[]{new InternetAddress( replyTo ) });
				msg.setSentDate( new java.util.Date() );
				//msg.setHeader( "Event-Mailer", "eventbeeMailer" );
		
				Transport.send( msg );
				SendMailStatus smstat=emailobj.getSendMailStatus();
				if(smstat != null)smstat.insertStatus();
	
			}//end of msg null
				prop.put("mail.smtp.from", "");
		}catch(Exception ex){

			System.out.println("Exception from DirectEmail.java: html message ="+ex.getMessage());
		}
	}
	
	
	public static void sendTextMailCampaign(EmailObj emailobj){
		try{
			javax.mail.Session   mailSession=(javax.mail.Session) new InitialContext().lookup( "java:/Mail" );
			javax.mail.Message  msg=null;

			Properties prop=mailSession.getProperties() ;

			String emailto=emailobj.getCampId()+"-"+emailobj.getMemId();

			emailto="return-list-"+emailto+"@"+EbeeConstantsF.get("emailserver.name" ,"eventbee.com");
			prop.put("mail.smtp.from", emailto);

			msg = new MimeMessage( mailSession );

			if(msg!=null){
				msg.setFrom( new InternetAddress( emailobj.getFrom() ) );
				InternetAddress[]inarr= InternetAddress.parse(emailobj.getTo() , false );
				msg.setRecipients( javax.mail.Message.RecipientType.TO, inarr );
				setOtherRecepients(msg,emailobj);// cc and bcc
				msg.setSubject( emailobj.getSubject() );
	
				msg.setText(emailobj.getTextMessage());
				String replyTo=emailobj.getReplyTo();
				if(replyTo==null)replyTo=emailobj.getFrom();
				msg.setReplyTo(new Address[]{new InternetAddress( replyTo ) });
				msg.setSentDate( new java.util.Date() );
	
				//msg.setHeader( "Return-Path", "bounce-list-narayan=eventbee.com@eventbee.com" );
				//Return-Path
				Transport.send( msg );
	
				SendMailStatus smstat=emailobj.getSendMailStatus();
				if(smstat != null)smstat.insertStatus();
			}//end of msg null
			prop.put("mail.smtp.from", "");
		}catch(Exception ex){
			System.out.println("Exception from DirectEmail.java sendTextMailCampaign:="+ex.getMessage());
		}

	}//end of text mail


	private static void setOtherRecepients(javax.mail.Message  msg,EmailObj emailobj   ){
		if(msg!=null && emailobj !=null){
			try{
				if(emailobj.getCc() !=null && !"".equals(emailobj.getCc().trim() ) ){
					emailobj.setCc(emailobj.getCc().replaceAll(";", ","));
					InternetAddress[]inarrcc= InternetAddress.parse(emailobj.getCc() , false );
					msg.setRecipients( javax.mail.Message.RecipientType.CC, inarrcc );
				}

			}catch(Exception ade){
				System.out.println("DirectEmail setOtherRecepients1 Exception: "+ade.getMessage());
			}
			try{
				if(emailobj.getBcc() !=null && !"".equals(emailobj.getBcc().trim() ) ){
					emailobj.setBcc(emailobj.getBcc().replaceAll(";", ","));
					InternetAddress[]inarrbcc= InternetAddress.parse(emailobj.getBcc() , false );
					msg.setRecipients( javax.mail.Message.RecipientType.BCC, inarrbcc );
				}
				}catch(Exception ade){
					System.out.println("DirectEmail setOtherRecepients2 Exception: "+ade.getMessage());
				}
			}
		}
	
/*	private static javax.mail.Session getMailSession(){
		javax.mail.Session  mailSession=null;
		try{
			
			String query="select mail_host from admin_scheduler_mailserver where mail_type='DirectMail'";
			
			String smtpsource=DbUtil.getVal(query,null);
			
			if(smtpsource == null)
				smtpsource="";
			
			System.out.println("mail sending using smtphost: "+smtpsource);
			
			
			Properties props=null;
			if("smail".equals(smtpsource)){
				props = new Properties();
				props.put("mail.transport.protocol", "smtp");
				props.put("mail.smtp.host", "smail.eventbee.com");
				props.put("mail.smtp.auth", "false");
				mailSession = Session.getInstance(props);
			}else{	
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
			System.out.println("Exception from DirectEmail.java getMailSession: "+e.getMessage());
		}
		return mailSession;
	}*/

}

/*class SMTPAuthenticator extends javax.mail.Authenticator {
	private String uid="";
	private String password="";
	public SMTPAuthenticator(String uid, String pwd){
		this.uid=uid;
		this.password=pwd;
	}
	public PasswordAuthentication getPasswordAuthentication() {
   		return new PasswordAuthentication(uid, password);
	}
}*/
