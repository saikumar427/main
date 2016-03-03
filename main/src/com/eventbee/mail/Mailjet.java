package com.eventbee.mail;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.interfaces.MailerInterface;
 
public class Mailjet implements MailerInterface{
	
	final static String APIKey = EbeeConstantsF.get("mailjet.apikey" ,"1302318b05317bf616854aa173243cd4");
	final static String SecretKey = EbeeConstantsF.get("mailjet.secretkey" ,"5afbc4097072a19c5122450a513e7fc3");
	
	public Session getMailSession(){

		System.out.println("Mailjet getMailSession");
		Session session=null;
		try{
			Properties props = new Properties ();
	 
			props.put ("mail.smtp.host", "in.mailjet.com");
			//props.put ("mail.smtp.socketFactory.port", "425");
			props.put ("mail.smtp.socketFactory.port", "465");
			props.put ("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put ("mail.smtp.auth", "true");
			//props.put ("mail.smtp.port", "587");
			props.put ("mail.smtp.port", "465");
	 
			session = Session.getInstance (props,
				new javax.mail.Authenticator (){
					protected PasswordAuthentication getPasswordAuthentication (){
						return new PasswordAuthentication (APIKey, SecretKey);
					}
				});
		}catch(Exception e){
			System.out.println("Exception from MailjetSession.java getMailjetSession: "+e.getMessage());
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
