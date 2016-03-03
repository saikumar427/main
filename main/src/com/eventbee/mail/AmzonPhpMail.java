package com.eventbee.mail;
import java.util.HashMap;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;

import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.interfaces.MailerInterface;
import com.eventbee.util.CoreConnector;
public class AmzonPhpMail implements MailerInterface{
	
	public Session getMailSession(){
		return null;
	}
	
	public int processHtmlMail(EmailObj emailobj){	
		return sendMail(emailobj,"html");
	}
	
	public void processTextMail(EmailObj emailobj){	
		 sendMail(emailobj,"text");
	}
	
	public static int sendMail(EmailObj emailobj,String mailtype){
		HashMap<String,String> map=new HashMap<String,String>();
		String replyTo=emailobj.getReplyTo();
		if(replyTo==null)replyTo=emailobj.getFrom();
		/*if("Y".equals(emailobj.getPdfTicket())){
			String filepath=EbeeConstantsF.get("temp.upload.filepath","C:\\uploads\\");
			filepath=filepath+emailobj.getFileName();
			map.put("filepath",filepath);
		}*/
		CoreConnector cc=new CoreConnector("http://nightwithfriends.com:8080/beeadmin/pajgrid/sendmail");
        
        map.put("from",emailobj.getFrom());
        map.put("to",emailobj.getTo());
        map.put("replyto",replyTo);
        map.put("subject",emailobj.getSubject());
        map.put("message",emailobj.getHtmlMessage());
        map.put("cc",emailobj.getCc());
        map.put("bcc",emailobj.getBcc());
        map.put("mailtype",mailtype);
        cc.setArguments(map);
        cc.setTimeout(60000);
        String res=cc.MPost();
        if("success".equalsIgnoreCase(res)){
        	return 1;
        }else{
        	return 0;
        }

    }

}
