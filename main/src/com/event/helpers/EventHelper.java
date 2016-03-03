package com.event.helpers;

import java.util.HashMap;
import java.util.Map;

import com.event.dbhelpers.EventDB;
import com.event.i18n.dbmanager.ConfirmationEmailDAO;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.general.I18NCacheData;
import com.eventbee.general.EventbeeMail;
import com.eventbee.general.SendMailStatus;
import com.eventbee.general.StatusObj;
import com.eventbee.general.TemplateConverter;

public class EventHelper {
	public static void sendListingConfirmationEmail(String mgrid,String eid){
		
		try{
			String server_addrs="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
			String firstname=DbUtil.getVal("select first_name from user_profile where user_id=?",new String[]{mgrid});

			DBManager dbmanager=new DBManager();
					String query=" select eventname,email from eventinfo where eventid=CAST(? AS BIGINT)";
					StatusObj statobject=dbmanager.executeSelectQuery(query,new String[]{eid});
					String eventname="",email="";
					if(statobject.getStatus()){
					int recordcount=statobject.getCount();
					for(int i=0;i<recordcount;i++){
					 eventname=dbmanager.getValue(i,"eventname","");
					 email=dbmanager.getValue(i,"email","");			
					 }
				}
			Map messageMap=new HashMap();
			String msg= " <table cellpadding='5' bgcolor='#ffcc33'><tr><td><p>";

			if(("Yes".equalsIgnoreCase(EventDB.getConfigVal(eid,"event.poweredbyEB",""))))
		{

		        msg=msg+"Visit your <a href='"+server_addrs+"/portal/auth/listauth.jsp?groupid="+eid+"&purpose=eventmanage'>Event Manage</a> page to get code to display Register button on your website.<br>";
		                
		          
		}
		else{

		      msg=msg+"Online ticketing is not yet enabled on your event,"
		              +"  <a href='"+server_addrs+"/portal/auth/listauth.jsp?groupid="+eid+"&purpose=eventmanage'>addtickets</a> to your event today, and take advantage of Eventbee's "
			      +"  flat $1 fee per ticket pricing model. <a href='"+server_addrs+"/helplinks/onlineregistration.jsp'>Click here</a> to learn more.";


		}
		msg=msg+"</p></td></tr></table>";

			messageMap.put("#**TO_FIRST_NAME**#",firstname);
			messageMap.put("#**EVENT_NAME**#",eventname);
			messageMap.put("#**TO_FIRST_NAME**#",firstname);
			messageMap.put("#**EVENT_MSG**#",msg);
			
			messageMap.put("#**EVENT_URL**#",server_addrs+"/event?eid="+eid);
			//SendEmail se=new SendEmail("13579","EVENT_LISTING_CONFIRMATION_MAIL",email,messageMap);		
			//EmailTemplate emailtemplate=new EmailTemplate("13579","EVENT_LISTING_CONFIRMATION_MAIL");
			
			String dbi18nLang=I18NCacheData.getI18NLanguage(eid);
			HashMap<String, String> purposeHm= new HashMap<String,String>();
			purposeHm.put("purpose", "EVENT_LISTING_CONFIRMATION_MAIL");
			purposeHm.put("isCustomFeature", "N");
			HashMap<String, String> emailTemplate=new HashMap<String, String>();
			ConfirmationEmailDAO emailDao=new ConfirmationEmailDAO();
			emailTemplate=(HashMap<String, String>)emailDao.getData(purposeHm, dbi18nLang, "").get("emailTemplate");
			
			EmailObj obj=EventbeeMail.getEmailObj();
			 obj=EventbeeMail.getEmailObj();
			 obj.setTo(email);
			 obj.setFrom(emailTemplate.get("fromMail"));
			 obj.setReplyTo(emailTemplate.get("replyToMail"));
			 obj.setSubject(TemplateConverter.getMessage(messageMap,emailTemplate.get("subjectFormat")));
			 obj.setTextMessage(TemplateConverter.getMessage(messageMap,emailTemplate.get("textFormat")));
			 obj.setHtmlMessage(TemplateConverter.getMessage(messageMap,emailTemplate.get("htmlFormat")));
			 obj.setSendMailStatus(new SendMailStatus("0","EVENT_LISTING_CONFIRMATION_MAIL","13579","1"));
			 EventbeeMail.sendHtmlMailPlain(obj);
			 EventbeeMail.insertStrayEmail(obj,"html","S");
		}catch(Exception send){System.out.println(" Exception in sendEventEmail: "+send);}

		}
	public static void SendEmail(String eventid,String unitid){
		try{
		DBManager dbmanager=new DBManager();
		String query=" select eventname,e.email,e.phone,u.phone as userphone,getMemberPref(mgr_id||'','pref:myurl','') as username from eventinfo e,user_profile u where to_number(u.user_id,'999999999999999')=e.mgr_id and eventid=CAST(? AS BIGINT)";
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{eventid});
		String evtname="",email="",phone="",u_phone="",username="";
		if(statobj.getStatus()){
		int recordcount=statobj.getCount();
		for(int i=0;i<recordcount;i++){
		 evtname=dbmanager.getValue(i,"eventname","");
		 email=dbmanager.getValue(i,"email","");
		 phone=dbmanager.getValue(i,"phone","");
		 u_phone=dbmanager.getValue(i,"userphone","");
		 username=dbmanager.getValue(i,"username","");
		 }
		}
		String servername=EbeeConstantsF.get("serveraddress","www.eventbee.com");
		String url=servername+"/event?eid="+eventid;
		String subject="Online Registration Alert";
		String message="Event: "+evtname+"\n"+" http://"+url;
		message+="\n Powered With Online Registration";
		message+="\n User Phone no: "+u_phone;
		message+="\n Event Phone no: "+phone;
		message+="\n email: "+email;
		EmailObj obj=EventbeeMail.getEmailObj();
		/*String frommail=EbeeConstantsF.get("online.registration.alertfrom.mail","bala@beeport.com");
		String tomail=EbeeConstantsF.get("online.registration.alertto.mail","bala@beeport.com");*/
		String frommail=EbeeConstantsF.get("online.registration.alertfrom.mail","bala@eventbee.org");
		String tomail=EbeeConstantsF.get("online.registration.alertto.mail","bala@eventbee.org");
		obj.setTo(tomail);
		obj.setFrom(frommail);
		obj.setSubject(subject);
		obj.setTextMessage(message);
		EventbeeMail.sendTextMailPlain(obj);
	}catch(Exception e){
		 System.out.println(" There is an error in powered by send mail:"+ e.getMessage());
	   }
	}
	

}
