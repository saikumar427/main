package com.eventbee.mail;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.interfaces.MailerInterface;

public class Sendgrid implements MailerInterface {
	
	final static String uid = EbeeConstantsF.get("sendgrid.uid" ,"musrifbala@gmail.com");
	final static String pwd = EbeeConstantsF.get("sendgrid.pwd" ,"eventbee2112");
	
	public Session getMailSession(){
		System.out.println("Sendgrid getMailSession");
		Session session=null;
		try{
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", "smtp.sendgrid.net");
			props.put("mail.smtp.auth", "true");
			
			session = Session.getInstance (props,
					new javax.mail.Authenticator (){
						protected PasswordAuthentication getPasswordAuthentication (){
							return new PasswordAuthentication (uid, pwd);
						}
					});
		}catch(Exception e){
			System.out.println("Exception from SendgridSession.java getSendgridSession: "+e.getMessage());
		}
		return session;
	}
	
	public int processHtmlMail(EmailObj emailobj){	
		Session session=getMailSession();
		return DirectEmail.mailHtmlData(emailobj,session);
	}
	
	public void processTextMail(EmailObj emailobj){	
		Session session=getMailSession();
		DirectEmail.mailPlainData(emailobj,session);
	}

}
