package com.eventbee.mail;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.interfaces.MailerInterface;

public class AmazonSes implements MailerInterface{
	
	final static String SMTP_USERNAME = EbeeConstantsF.get("aws.username" ,"AKIAJZMJ6OULMP4SNCHQ");
	final static String SMTP_PASSWORD = EbeeConstantsF.get("aws.pwd" ,"Ajwti6UG1dl7sHrVo33md/JxdHzWB3JNJbkKsIPBIh1z");

	public Session getMailSession(){
		System.out.println("AmazonSes getMailSession");
		Session session=null;
		try{
			Properties props = new Properties();
			 props.put("mail.transport.protocol", "smtp");
		     props.put("mail.smtp.port", "25");
		     //props.put("mail.debug", "true")    ;
		     props.put("mail.smtp.host", "email-smtp.us-east-1.amazonaws.com");
		     props.put("mail.smtp.auth", "true");
		     props.put("mail.smtp.auth", "true");
		     props.put("mail.smtp.starttls.enable", "true");
			
		     session = Session.getInstance (props,
	                   new javax.mail.Authenticator (){  
	                protected PasswordAuthentication getPasswordAuthentication (){
	                    return new PasswordAuthentication (SMTP_USERNAME, SMTP_PASSWORD);
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
