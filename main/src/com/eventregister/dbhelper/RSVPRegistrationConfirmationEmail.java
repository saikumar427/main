package com.eventregister.dbhelper;

import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.general.EmailTemplate;
import com.eventbee.general.EventbeeMail;
import com.eventbee.general.GenUtil;

import com.eventbee.general.SendMailStatus;
import com.eventregister.TicketsDB;

public class RSVPRegistrationConfirmationEmail {
	public static int sendRsvpEmail(HashMap hmap,VelocityContext context){
		return sendRsvpEmail(hmap,context,"bcc",null);
	}
	public static int sendRsvpEmail(HashMap hmap,VelocityContext context,String bcc,String mailto){
	    String eventid=(String) hmap.get("GROUPID");
	    TicketsDB tktdb=new TicketsDB();
	    HashMap configMap = tktdb.getConfigValuesFromDb(eventid);
	    int status=0;
		try{
			EmailTemplate emailtemplate=null;
			String isformatexists=DbUtil.getVal("select 'yes' from email_templates where purpose='RSVP_CONFIRMATION' and groupid=?", new String []{eventid});
		
			if("yes".equals(isformatexists)){
				emailtemplate=new EmailTemplate("13579","RSVP_CONFIRMATION",eventid);
			}else{
				emailtemplate=new EmailTemplate("100","RSVP_CONFIRMATION");
			}
			
	       	EmailObj obj=EventbeeMail.getEmailObj();
			String mailbcc=EbeeConstantsF.get("ADMIN_EMAIL","sridevi@beeport.com") ;
			//String tomail=(String)hmap.get("emailid");
			if(mailto == null){
				if(!"no".equals(GenUtil.getHMvalue(configMap,"event.sendmailtoattendee","yes"))){
					obj.setTo((String)hmap.get("emailid"));
				}
				else{
					obj.setTo(EbeeConstantsF.get("ADMIN_EMAIL","sridevi@beeport.com"));
					mailbcc="";
				}
			}else
				obj.setTo(mailto);
			//obj.setTo(tomail);
			String[] formatmessage=RSVPProfileActionDB.fillData(hmap,eventid,emailtemplate,context);
			
			String emailtype=formatmessage[3];
			if(bcc.equals("bcc")){
				if(!"no".equals(GenUtil.getHMvalue(configMap,"event.sendmailtomgr","yes"))){
					if(!"".equals(mailbcc))
						mailbcc+=","+formatmessage[1];
					else	
						mailbcc+=formatmessage[1];
				}
				if(!"".equals(GenUtil.getHMvalue(configMap,"registration.email.cc.list",""))){
					if(!"".equals(mailbcc))
						mailbcc+=","+(String)GenUtil.getHMvalue(configMap,"registration.email.cc.list","");
					else	
						mailbcc+=(String)GenUtil.getHMvalue(configMap,"registration.email.cc.list","");
				}	
				obj.setBcc(mailbcc);
			}
			//obj.setBcc(formatmessage[1]+","+EbeeConstantsF.get("ADMIN_EMAIL","sridevi@beeport.com") );
			//obj.setFrom(formatmessage[1]);
			obj.setReplyTo(formatmessage[1]);
			String fromemail=DbUtil.getVal("select email from from_email_permissions where email=?", new String[]{formatmessage[1]});
			if(fromemail==null || "".equals(fromemail))
				fromemail=EbeeConstantsF.get("RSVP_FROM_EMAIL","registrations@eventbee.com");
			obj.setFrom(fromemail);
			obj.setSubject(formatmessage[2]);
			obj.setSendMailStatus(new SendMailStatus("13579","RSVP_CONFIRMATION",eventid,"1"));
			//if("Html".equals(emailtype)){
				obj.setHtmlMessage(formatmessage[0]);
				//EventbeeMail.sendHtmlMail(obj);
				EventbeeMail.sendHtmlMailPlain(obj);
				EventbeeMail.insertStrayEmail(obj, "html","S");
			/*}
			else if("Text".equals(emailtype)){
				obj.setTextMessage(formatmessage[0]);
				EventbeeMail.sendTextMail(obj);
			}*/
			status=1;
		}
		catch(Exception e){
			status=0;
		}
		return status;

	}
	
}
