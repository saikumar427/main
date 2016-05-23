package com.event.dbhelpers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Vector;
import java.util.ResourceBundle;

import com.event.beans.EmailAttendeesData;
import com.event.helpers.I18n;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateConverter;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.general.EventbeeLogger;
import com.eventbee.general.EventbeeMail;
import com.eventbee.general.GenUtil;
import com.eventbee.general.SendMailStatus;
import com.eventbee.general.StatusObj;
import com.eventbee.util.RandomStringUUID;
import com.eventregister.dbhelper.RegistrationTiketingManager; 

public class EmailAttendeesDB {
	static String[] monthvals=new String[]{"01","02","03","04","05","06","07","08","08","10","11","12"};
	public static HashMap<String,String> getListdetails(String eid){
		DBManager dbmanager=new DBManager();
		HashMap<String,String> hm=new HashMap<String,String>();
		StatusObj sb=dbmanager.executeSelectQuery("select a.list_name,a.list_id from mail_list a, eventinfo b, config c where b.config_id=c.config_id and c.name='event.maillist.id' and CAST(c.value AS INTEGER)=a.list_id  and b.eventid=CAST(? AS BIGINT)",new String[]{eid});
		if(sb.getStatus()){
		hm.put("listname",dbmanager.getValue(0,"list_name",""));
		hm.put("list_id",dbmanager.getValue(0,"list_id",""));
		}
		return hm;
	}
	public static String getMgrEmail(String eid){
		//String mgr_email=DbUtil.getVal("select email from user_profile where to_number(user_id,'999999999999')=(select mgr_id from eventinfo where eventid=to_number(?,'99999999999999999'))",new String[]{eid});
		String mgr_email=DbUtil.getVal("select email from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		return mgr_email;
	}
	public static String getCount(String eid,HashMap<String,String> Attendeelist){
	String count=DbUtil.getVal("select count(*) from mail_list_members where list_id=CAST(? AS INTEGER) and status='available'",new String[]{(String)Attendeelist.get("list_id")});
	return count;
	}
	public static String sendTestMail(String eid,EmailAttendeesData emailAttendeesData,String fckdesc){
		String msg="";
		String description=emailAttendeesData.getDescription();
		String descriptiontype=emailAttendeesData.getDescriptiontype();
		//if("wysiwyg".equals(descriptiontype)) description=fckdesc;
		String mgrId=DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[] {eid});
		String server_address="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		String messagefrommgr="";
		String senderfulladdress="";
		ArrayList addresslist=null;
		EmailObj emailobj=null;
		String query="select getMemberName(user_id||'') as name,street,city,state,country,zip,email from user_profile where user_id=? ";
		
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String [] {mgrId});
		if(statobj.getStatus()){
		addresslist=new ArrayList();
		addresslist.add(dbmanager.getValue(0,"name",""));
		addresslist.add(dbmanager.getValue(0,"street",""));
		addresslist.add(dbmanager.getValue(0,"city",""));
		addresslist.add(dbmanager.getValue(0,"state",""));
		addresslist.add(dbmanager.getValue(0,"country",""));
		addresslist.add(dbmanager.getValue(0,"zip",""));
		}
		if(addresslist!=null)
			senderfulladdress=GenUtil.getCSVData((String [])addresslist.toArray(new String[addresslist.size()]));
			 messagefrommgr=I18n.getString("ea.email.sent.by.lbl");
			messagefrommgr=messagefrommgr+senderfulladdress;
			String imgpattern=server_address+"/home/images/poweredby.jpg";
			description="<pre style='font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12px; line-height: 140%; padding-left: 2px; margin: 0px; white-space: pre-wrap; word-wrap: break-word;'>"+description+"</pre>";
		
			String Htmlmailcont=I18n.getString("em.testmail.msg1")+"<br/><br/>"+I18n.getString("del.nte.lbl")+"<br/>"+""
				+I18n.getString("em.testmail.msg2")+""
				+"<br/>---------------------------"+I18n.getString("em.testmail.msg3")+"------------------------<br/><br/>"
				+description+"<br/>"
				+"<br/><div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>"+messagefrommgr+"</font></div><br/><div align='center'><a href='"+server_address+"'><img src='"+imgpattern+"'   border='0' /></a></div>"
				+" <br/> ";
			
			//System.out.println("Htmlmailcont:::::::::::"+Htmlmailcont);
			try{				
				emailobj=new EmailObj();				
				emailobj.setTo(emailAttendeesData.getMailtotest().trim());
				String frommail=EbeeConstantsF.get("MESSAGES_FROM_EMAIL","messages@eventbee.com");
				emailobj.setFrom(frommail);				
				emailobj.setHtmlMessage(Htmlmailcont);
				String mgremail=EmailAttendeesDB.getMgrEmail(eid);
				emailobj.setReplyTo(mgremail);
				emailobj.setSendMailStatus(new SendMailStatus("13579", "Attendees_Test_Mail", "","1"));
				if(description!=null&&!"".equals(description.trim())){
				emailobj.setSubject(emailAttendeesData.getSubject()+" (Test mail)");
				EventbeeMail.sendHtmlMailPlain(emailobj);
				EmailAttendeesDB.insertIntoStrayEMail(eid,emailAttendeesData,Htmlmailcont);
				}
			
				return "Success";	
			}catch(Exception exp){				
				EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "ecmailprocess .jsp in sending test mail from emial attendee", "jsp service", exp.getMessage(), exp ) ;
			}
			return msg;
	}
	
	public static void sendMail(String eid,EmailAttendeesData emailAttendeesData,String fckdesc){
		/*String description=emailAttendeesData.getDescription();
		String descriptiontype=emailAttendeesData.getDescriptiontype();
		if("wysiwyg".equals(descriptiontype)) description=fckdesc;
		String mgrId=DbUtil.getVal("select mgr_id from eventinfo where eventid=to_number(?,'9999999999999999')", new String[] {eid});
		String server_address="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		String messagefrommgr="";
		String senderfulladdress="";
		ArrayList addresslist=null;
		EmailObj emailobj=null;		
		String campid=DbUtil.getVal("select  nextval('seq_campaign') as campid", new String[] {});
		String image_name="poweredby.jpg";
		String imagetest=server_address+"/campmailsopen?eid=#*EID*#";
		java.sql.Timestamp tsp=null;
		String query="select getMemberName(user_id||'') as name,street,city,state,country,zip,email from user_profile where user_id=? ";
		DBManager dbmanager=new DBManager();
		StatusObj statobj1=dbmanager.executeSelectQuery(query,new String [] {mgrId});
		if(statobj1.getStatus()){
		addresslist=new ArrayList();
		addresslist.add(dbmanager.getValue(0,"name",""));
		addresslist.add(dbmanager.getValue(0,"street",""));
		addresslist.add(dbmanager.getValue(0,"city",""));
		addresslist.add(dbmanager.getValue(0,"state",""));
		addresslist.add(dbmanager.getValue(0,"country",""));
		addresslist.add(dbmanager.getValue(0,"zip",""));
		}		
		if(addresslist!=null)
		senderfulladdress=GenUtil.getCSVData((String [])addresslist.toArray(new String[addresslist.size()]));
		 messagefrommgr="This email was sent by ";
		messagefrommgr=messagefrommgr+senderfulladdress;
		String imgpattern=server_address+"/home/images/spacer.gif";
		if("text".equals(descriptiontype)){
			description="<pre style='font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12px; line-height: 140%; padding-left: 2px; margin: 0px; white-space: pre-wrap; word-wrap: break-word;'>"+description+"</pre>";
		}
		if(description!=null&&!"".equals(description.trim()))
			description=description+"<br/><a href='"+server_address+"'><img src='"+imgpattern+"'   border='0' /></a><br/><br/><div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>"+messagefrommgr+"</font></div><br/><div align='center' border='0'><a href='"+server_address+"' target='_blank'><img src='"+server_address+"/home/images/"+image_name+"' border='0'/></a></div>";
		description=description+"<div ><img src='"+imagetest+"'   border='0' /></div>";
		
		if("now".equals(emailAttendeesData.getNow())){
			java.util.Date d=new java.util.Date();
			tsp=new java.sql.Timestamp(Calendar.getInstance().get(Calendar.YEAR)-1900,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH),Calendar.getInstance().get(Calendar.HOUR_OF_DAY),0,0,0);
		}else if("date".equals(emailAttendeesData.getNow())){				 
			tsp=new java.sql.Timestamp(Integer.parseInt(emailAttendeesData.getYear())-1900,Integer.parseInt(emailAttendeesData.getMonth())-1,Integer.parseInt(emailAttendeesData.getDay()),Integer.parseInt(emailAttendeesData.getTime()),0,0,0);	
		}
		String query1="insert into email_campaign (manager_id,camp_id,camp_name,camp_groupid,"
			+" camp_grouptype,camp_html,camp_subject,camp_to,camp_from,camp_replyto,camp_scheddate,"
			+" camp_status,camp_timezone,created_by,created_at,updated_by,updated_at,unitid,role)"
			+" values (to_number(?,'9999999999999999'),to_number(?,'9999999999999999'),?,to_number(?,'9999999999999999')," +
			"?,?,?,'',?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS')," +
			"'mailscheduler',now(),'mailscheduler',now(),to_number(?,'99999999999999'),?)";
		
		StatusObj sb=DbUtil.executeUpdateQuery(query1,new String[]{mgrId,campid,emailAttendeesData.getListName(),"13579","Event",description,emailAttendeesData.getSubject(),emailAttendeesData.getEmail(),emailAttendeesData.getEmail(),tsp.toString(),"P",tsp.toString(),"13579","MEM"});
		StatusObj sb1=DbUtil.executeUpdateQuery("insert into email_recepients(camp_id, recepient_id, created_by,created_at,updated_by,updated_at)values(to_number(?,'99999999999999'),to_number(?,'99999999999999'),'mailscheduler',now(),'mailschedule',now())",new String[]{campid,emailAttendeesData.getListid()});
	*/	
	
		String description=emailAttendeesData.getDescription();
		String descriptiontype=emailAttendeesData.getDescriptiontype();
		if("wysiwyg".equals(descriptiontype)) description=fckdesc;
		String mgrId=DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[] {eid});
		String server_address="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		String messagefrommgr="";
		String senderfulladdress="";
		ArrayList addresslist=null;
		EmailObj emailobj=null;		
		String campid=DbUtil.getVal("select  nextval('seq_campaign') as campid", new String[] {});
		String image_name="poweredby.jpg";
		String imagetest=server_address+"/campmailsopen?eid=#*EID*#";
		java.sql.Timestamp tsp=null;
		String query="select getMemberName(user_id||'') as name,street,city,state,country,zip,email from user_profile where user_id=? ";
		DBManager dbmanager=new DBManager();
		StatusObj statobj1=dbmanager.executeSelectQuery(query,new String [] {mgrId});
		if(statobj1.getStatus()){
		addresslist=new ArrayList();
		addresslist.add(dbmanager.getValue(0,"name",""));
		addresslist.add(dbmanager.getValue(0,"street",""));
		addresslist.add(dbmanager.getValue(0,"city",""));
		addresslist.add(dbmanager.getValue(0,"state",""));
		addresslist.add(dbmanager.getValue(0,"country",""));
		addresslist.add(dbmanager.getValue(0,"zip",""));
		}		
		if(addresslist!=null)
		senderfulladdress=GenUtil.getCSVData((String [])addresslist.toArray(new String[addresslist.size()]));
		 messagefrommgr="This email was sent by ";
		messagefrommgr=messagefrommgr+senderfulladdress;
		String imgpattern=server_address+"/home/images/spacer.gif";
		if("text".equals(descriptiontype)){
			description="<pre style='font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12px; line-height: 140%; padding-left: 2px; margin: 0px; white-space: pre-wrap; word-wrap: break-word;'>"+description+"</pre>";
		}
		if(description!=null&&!"".equals(description.trim()))
			description=description+"<br/><a href='"+server_address+"'><img src='"+imgpattern+"'   border='0' /></a><br/><br/><div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>"+messagefrommgr+"</font></div><br/><div align='center' border='0'><a href='"+server_address+"' target='_blank'><img src='"+server_address+"/home/images/"+image_name+"' border='0'/></a></div>";
		description=description+"<div ><img src='"+imagetest+"'   border='0' /></div>";
		
		String schTime="";
		String schESTTime="";
		String status="P";
		String buyer="Y";
		if("now".equals(emailAttendeesData.getNow())){
			java.util.Date d=new java.util.Date();
			tsp=new java.sql.Timestamp(Calendar.getInstance().get(Calendar.YEAR)-1900,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH),Calendar.getInstance().get(Calendar.HOUR_OF_DAY),0,0,0);
			schTime=tsp.toString();
		}else if("date".equals(emailAttendeesData.getNow())){				 
			tsp=new java.sql.Timestamp(Integer.parseInt(emailAttendeesData.getYear())-1900,Integer.parseInt(emailAttendeesData.getMonth())-1,Integer.parseInt(emailAttendeesData.getDay()),Integer.parseInt(emailAttendeesData.getTime()),0,0,0);	
			schTime=tsp.toString();
		}
		String insertquery="insert into attendee_blasts(status,sendtobuyer,attendee_blast_id,fromemail,subject," +
		"contenttype,content,sch_time,created_at,sch_est_time,eventid) " +
		"values (?,?,?,?,?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS')," +
		"to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),?)";

		StatusObj sb=DbUtil.executeUpdateQuery(insertquery,new String[]{status,buyer,campid,emailAttendeesData.getEmail(),emailAttendeesData.getSubject(),descriptiontype,description,schTime,DateUtil.getCurrDBFormatDate(),schTime,eid});

	}
	
	public static EmailAttendeesData getEditDetails(String blastid,String eid){
		EmailAttendeesData emailAttendeesData =new EmailAttendeesData();
		DBManager dbmanager=new DBManager();
		//emailAttendeesData.setOptions("edit");
		String query = "select event_date,fromemail,sentcontent,sendtobuyer,sendtoattendee,contenttype,subject," +
			"to_char(sch_time,'MM') AS month,to_char(sch_time,'DD') AS day ," +
			"EXTRACT(YEAR FROM sch_time) AS year ,EXTRACT(HOUR FROM sch_time) AS hour," +
			"EXTRACT(MINUTE FROM sch_time) AS minute, sch_time,select_attendees_type,selected_tkts " +
			"from attendee_blasts where attendee_blast_id=? and eventid=?";
		
		StatusObj sb=dbmanager.executeSelectQuery(query,new String[]{blastid,eid});
		
        if(sb.getStatus()){
        	boolean isrecur=EventDB.isRecurringEvent(eid);
        	if(isrecur){
        	    String powertype=EventDB.getPowerType(eid);
        		emailAttendeesData.setRecurringlist(EmailAttendeesDB.getRecurringList(eid,powertype));
        	}
        	emailAttendeesData.setSeldate(dbmanager.getValue(0,"event_date",""));
			emailAttendeesData.setEmail(dbmanager.getValue(0,"fromemail",""));
			emailAttendeesData.setSubject(dbmanager.getValue(0,"subject",""));
			emailAttendeesData.setContent(dbmanager.getValue(0,"sentcontent",""));	
			emailAttendeesData.setBuyer(dbmanager.getValue(0,"sendtobuyer",""));
			emailAttendeesData.setAttendee(dbmanager.getValue(0,"sendtoattendee",""));
			emailAttendeesData.setDescriptiontype(dbmanager.getValue(0,"contenttype",""));
			emailAttendeesData.setDescription(dbmanager.getValue(0,"sentcontent",""));
			String selectedAttType=dbmanager.getValue(0,"select_attendees_type","");
			if("".equals(selectedAttType) && !"".equals(dbmanager.getValue(0,"selected_tkts","")))
				selectedAttType="TICKET";
			if("".equals(selectedAttType) && "".equals(dbmanager.getValue(0,"selected_tkts","")))
		          selectedAttType="ALL";
				
			System.out.println("!!! selectedAttType: "+selectedAttType);
			emailAttendeesData.setSendTo(selectedAttType);
			emailAttendeesData.setSelectAttendeeType(selectedAttType);
			if("TID".equalsIgnoreCase(selectedAttType)) 
				emailAttendeesData.setTidList(dbmanager.getValue(0,"selected_tkts",""));
			if("TICKET".equalsIgnoreCase(selectedAttType)) 
				emailAttendeesData.setSelectedTickets(dbmanager.getValue(0,"selected_tkts",""));
			
			String schTime = dbmanager.getValue(0,"sch_time","");
			
			
			if("".equals(schTime)){
				emailAttendeesData.setSendAt("later");
				try{
					//emailAttendeesData.setContentlist(getCopyFromDetails(eid));
					//emailAttendeesData.setEmail(getMgrEmail(eid));
					
					/*Calendar calendar=Calendar.getInstance();
					int hour=calendar.get(Calendar.HOUR_OF_DAY);
					emailAttendeesData.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
					emailAttendeesData.setMonth(String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1));
					emailAttendeesData.setDay(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
					emailAttendeesData.setTime(String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
					emailAttendeesData.setHour(EventbeeDates.getHoursHtml(hour,"emailAttendeesData.time"));
					String userTimeZone = EventDB.getEventTimeZone(eid);
				    String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
				    DateConverter dateconvert=new DateConverter(fromTimeZone,userTimeZone);
				    DateConverter dc = getESTTime(eid,emailAttendeesData,dateconvert);
				    String year=dc.getDatePart().substring(0,4);
				    String month=dc.getDatePart().substring(5,7);
				    String day=dc.getDatePart().substring(8,10);
				    int time=Integer.parseInt(dc.getTimePart().substring(0,2));
				    emailAttendeesData.setMonth(month);
					emailAttendeesData.setDay(day);
					emailAttendeesData.setYear(year);
					emailAttendeesData.setHour(EventbeeDates.getHoursHtml(time,"emailAttendeesData.time"));*/
					getCreateAttendeeDetails(eid,emailAttendeesData);
				
				}catch(Exception e){
					System.out.println("Exception in getCreateAttendeeDetails EmailAttendeesDB.java Error: "+e.getMessage());
				}	
			
			}else{
				emailAttendeesData.setSendAt("date");
				//emailAttendeesData.setMonth(dbmanager.getValue(0,"month",""));
				//emailAttendeesData.setDay(dbmanager.getValue(0,"day",""));
				//emailAttendeesData.setYear(dbmanager.getValue(0,"year",""));
				//emailAttendeesData.setHour(EventbeeDates.getHoursHtml(Integer.parseInt(dbmanager.getValue(0,"hour","")),"emailAttendeesData.time"));
				
				DateTimeBean dtbObj=new DateTimeBean();
				dtbObj.setYyPart(dbmanager.getValue(0,"year",""));
				dtbObj.setDdPart(dbmanager.getValue(0,"day",""));
				dtbObj.setMonthPart(dbmanager.getValue(0,"month",""));
				String minutes  = dbmanager.getValue(0,"minute","");
				if(minutes.length()<2)
					minutes="0"+minutes;
				dtbObj.setMmPart(minutes);
				int hour = Integer.parseInt(dbmanager.getValue(0,"hour",""));
				if(hour==12){
					dtbObj.setHhPart("12");
					dtbObj.setAmpm("PM");
		        }else if(hour>12){
		            hour = hour-12;
		            dtbObj.setAmpm("PM");
		        }else{
		        	dtbObj.setAmpm("AM");
		        }
				
				String hh = String.valueOf(hour);
	            if(hh.length()<2){
					hh="0"+hh;
	            	dtbObj.setHhPart(hh);
	            }
	            dtbObj.setHhPart(hh);
				emailAttendeesData.setDateTimeBeanObj(dtbObj);
			}
			
        
        }
		return emailAttendeesData;
	}
	
	public static EmailAttendeesData showContent(String blastid, String eid){
		EmailAttendeesData emailAttendeesData =new EmailAttendeesData();
		DBManager dbmanager=new DBManager();
		StatusObj sb=dbmanager.executeSelectQuery("select content from attendee_blasts where attendee_blast_id=? and eventid=?",new String[]{blastid,eid});
		if(sb.getStatus()){
			emailAttendeesData.setContent(dbmanager.getValue(0,"content",""));
		}
		return emailAttendeesData;
	}
	
	public static ArrayList getScheduledList(String eid){
		
		ArrayList scheduledList = new ArrayList();
		HashMap<String,String> hm=new HashMap<String,String>();
		String query = "select attendee_blast_id,subject,to_char(sch_time, 'Dy, Mon DD, YYYY, HH:MI PM') as sch_time " +
				"from attendee_blasts where status='P' and eventid=? and sch_time is not null order by created_at desc";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{eid});
		int count=statobj.getCount();
		
		if(statobj.getStatus()&&count>0){
			for(int k=0;k<count;k++){
				EmailAttendeesData emailEmailAttendeesData = new EmailAttendeesData();
				emailEmailAttendeesData.setBlastid(dbmanager.getValue(k,"attendee_blast_id",""));
				emailEmailAttendeesData.setSubject(dbmanager.getValue(k,"subject",""));
				emailEmailAttendeesData.setSchTime(dbmanager.getValue(k,"sch_time",""));
				scheduledList.add(emailEmailAttendeesData);
			}
		}

		return scheduledList;
	}
	public static ArrayList getDraftedList(String eid){
		ArrayList draftedlist = new ArrayList();
		
		String query = "select attendee_blast_id,subject,to_char(created_at,'Dy, Mon DD, YYYY, HH:MI PM') as created_at " +
				"from attendee_blasts where status='P' and eventid=? and sch_time is null order by created_at desc";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{eid});
		int count=statobj.getCount();
		
		if(statobj.getStatus()&&count>0){
			for(int k=0;k<count;k++){
				EmailAttendeesData emailEmailAttendeesData = new EmailAttendeesData();
				emailEmailAttendeesData.setBlastid(dbmanager.getValue(k,"attendee_blast_id",""));
				emailEmailAttendeesData.setSubject(dbmanager.getValue(k,"subject",""));
				emailEmailAttendeesData.setSchTime(dbmanager.getValue(k,"created_at",""));
				draftedlist.add(emailEmailAttendeesData);
			}
		}
		return draftedlist;
	}	
	public static ArrayList getSentList(String eid){
		ArrayList sentlist = new ArrayList();
		String query = "select attendee_blast_id,subject,to_char(sch_time, 'Dy, Mon DD, YYYY, HH:MI PM') as sch_time " +
				"from attendee_blasts where status='S' and eventid=? order by created_at desc";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{eid});
		int count=statobj.getCount();
		
		if(statobj.getStatus()&&count>0){
			for(int k=0;k<count;k++){
				EmailAttendeesData emailEmailAttendeesData = new EmailAttendeesData();
				emailEmailAttendeesData.setBlastid(dbmanager.getValue(k,"attendee_blast_id",""));
				emailEmailAttendeesData.setSubject(dbmanager.getValue(k,"subject",""));
				emailEmailAttendeesData.setSchTime(dbmanager.getValue(k,"sch_time",""));
				
				sentlist.add(emailEmailAttendeesData);
			}
		}
		return sentlist;	
	}
	/*public static void deleteRecord(String blastid, String eid){
		DBManager dbmanager=new DBManager();
		String query="delete from attendee_blasts where attendee_blast_id=? and eventid=?";
		StatusObj sb=DbUtil.executeUpdateQuery(query,new String[]{blastid,eid});
	}*/
	public static boolean deleteMail(String blastid, String eid){
		//String deletemail="delete from attendee_blasts where attendee_blast_id=? and eventid=?";
		//DbUtil.executeUpdateQuery(deletemail,new String[]{blastid,eid});
		String updateQuery="update attendee_blasts set status='D' where attendee_blast_id=? and eventid=?";
		DbUtil.executeUpdateQuery(updateQuery,new String[]{blastid,eid});
		
		return true;	
}
	
	
	public static void saveMail( String blast,String eid, EmailAttendeesData emailAttendeesData,ArrayList tktselectedlist,String powertype){
		String content=emailAttendeesData.getContent();
		String showcontent=emailAttendeesData.getContent();
		String contenttype=emailAttendeesData.getDescriptiontype();
		if("wysiwyg".equals(contenttype)){
			content=emailAttendeesData.getDescription();
			showcontent=emailAttendeesData.getDescription();
		}
		String buyer=emailAttendeesData.getBuyer();
		String attendee=emailAttendeesData.getAttendee();
		
        String selectedtkts="";
		
		String fromemail=emailAttendeesData.getEmail().trim().toLowerCase();
		fromemail=EbeeConstantsF.get("MESSAGES_FROM_EMAIL", "messages@eventbee.com");
		String subject=emailAttendeesData.getSubject();
		//String contenttype=emailAttendeesData.getDescriptiontype();
		//String content=emailAttendeesData.getContent();
		String blastid=RandomStringUUID.getUUID();
	
		//String showcontent=emailAttendeesData.getContent();
		String schTime="";
		String schESTTime="";
		String selecteddate=emailAttendeesData.getSeldate();
		if(!EventDB.isRecurringEvent(eid))
			selecteddate="alldates";
		String mgrId=DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[] {eid});
		String server_address="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		String messagefrommgr="";
		String senderfulladdress="";
		ArrayList addresslist=null;
		EmailObj emailobj=null;		
		//String campid=DbUtil.getVal("select  nextval('seq_campaign') as campid", new String[] {});
		String image_name="poweredby.jpg";
	//commented on 21st feb	String imagetest=server_address+"/campmailsopen?eid=#*EID*#";
		java.sql.Timestamp tsp=null;
		String query="select getMemberName(user_id||'') as name,street,city,state,country,zip,email from user_profile where user_id=? ";
		DBManager dbmanager=new DBManager();
		StatusObj statobj1=dbmanager.executeSelectQuery(query,new String [] {mgrId});
		if(statobj1.getStatus()){
		addresslist=new ArrayList();
		addresslist.add(dbmanager.getValue(0,"name",""));
		addresslist.add(dbmanager.getValue(0,"street",""));
		addresslist.add(dbmanager.getValue(0,"city",""));
		addresslist.add(dbmanager.getValue(0,"state",""));
		addresslist.add(dbmanager.getValue(0,"country",""));
		addresslist.add(dbmanager.getValue(0,"zip",""));
		}		
		if(addresslist!=null)
		senderfulladdress=GenUtil.getCSVData((String [])addresslist.toArray(new String[addresslist.size()]));
		 messagefrommgr=I18n.getString("ea.email.sent.by.lbl");
		messagefrommgr=messagefrommgr+senderfulladdress;
		String imgpattern=server_address+"/home/images/spacer.gif";
		if("text".equals(contenttype)){
			content="<pre style='font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12px; line-height: 140%; padding-left: 2px; margin: 0px; white-space: pre-wrap; word-wrap: break-word;'>"+content+"</pre>";
		}
		if(content!=null&&!"".equals(content.trim()))
			content=content+"<br/><br/><div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>"+messagefrommgr+"</font></div><br/><div align='center' border='0'><a href='"+server_address+"' target='_blank'><img src='"+server_address+"/home/images/"+image_name+"' border='0'/></a></div>";
		//commented on 21st feb	content=content+"<div ><img src='"+imagetest+"'   border='0' /></div>";
		//<a href='"+server_address+"'><img src='"+imgpattern+"'   border='0' /></a><br/>

		if("on".equals(buyer))buyer="Y";         
		else buyer="N";
		if("on".equals(attendee))attendee="Y";
		else attendee="N";
		String selectedAttType="";
		System.out.println("SelectAttendeeType: "+emailAttendeesData.getSelectAttendeeType()+" SendTo: "+emailAttendeesData.getSendTo());
		if("Ticketing".equals(powertype) && "Y".equals(attendee) && !"ALL".equals(emailAttendeesData.getSendTo())){
			
        		if("TICKET".equals(emailAttendeesData.getSelectAttendeeType())){
        			selectedtkts=tktselectedlist.toString();
        			selectedtkts = tktselectedlist.toString().replace("[", "").replace("]", "").replace(", ", ",");
        			selectedAttType="TICKET";
        		}
        		
        		if("TID".equals(emailAttendeesData.getSelectAttendeeType())){
        			selectedtkts=emailAttendeesData.getTidList().trim();
        			selectedAttType="TID";
        		}
        		
        }
		if("Ticketing".equals(powertype) && "Y".equals(attendee) && "".equals(selectedAttType)) selectedAttType="ALL";
		
        System.out.println("finally selected tickets in string format:: "+selectedtkts);
		
		String status="P";
		if("now".equals(emailAttendeesData.getSendAt())){
			java.util.Date d=new java.util.Date();
			System.out.println("HOUR_OF_DAY: "+Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
			System.out.println("HOUR: "+Calendar.getInstance().get(Calendar.HOUR));
			System.out.println("MINUTE: "+Calendar.getInstance().get(Calendar.MINUTE));
			tsp=new java.sql.Timestamp(Calendar.getInstance().get(Calendar.YEAR)-1900,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH),Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE),0,0);
			schESTTime=tsp.toString();
			emailAttendeesData.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            emailAttendeesData.setMonth(String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1));
            emailAttendeesData.setDay(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
            emailAttendeesData.setHour(String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
            emailAttendeesData.setMinutes(String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)));
            String userTimeZone = EventDB.getEventTimeZone(eid);
            String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
            DateConverter dateconvert=new DateConverter(fromTimeZone,userTimeZone);
            DateConverter dc = getESTTime(eid,emailAttendeesData,dateconvert);
            String startDate=dc.getDatePart();
            String startTime=dc.getTimePart();
            schTime = startDate+" "+startTime;         
            }
		else if("date".equals(emailAttendeesData.getSendAt())){	
			
			int hour=Integer.parseInt(emailAttendeesData.getDateTimeBeanObj().getHhPart());
			String min=emailAttendeesData.getDateTimeBeanObj().getMmPart();
			String ampm=emailAttendeesData.getDateTimeBeanObj().getAmpm();
			if("PM".equals(ampm) && hour!=12){
				hour=hour+12;
	        }else if("AM".equals(ampm) && hour==12){
	        	hour=0;
	        }
			tsp=new java.sql.Timestamp(Integer.parseInt(emailAttendeesData.getDateTimeBeanObj().getYyPart())-1900,Integer.parseInt(emailAttendeesData.getDateTimeBeanObj().getMonthPart())-1,Integer.parseInt(emailAttendeesData.getDateTimeBeanObj().getDdPart()),hour,Integer.parseInt(emailAttendeesData.getDateTimeBeanObj().getMmPart()),0,0);	
			schTime=tsp.toString();
			
			String userTimeZone = EventDB.getEventTimeZone(eid);
			String toTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
			DateConverter dateconvert=new DateConverter(userTimeZone,toTimeZone);
			DateTimeBean dtb=emailAttendeesData.getDateTimeBeanObj();
			boolean statusdc=dateconvert.convertDateTime(dtb);
			String startDate=dateconvert.getDatePart();
			String startTime=dateconvert.getTimePart();
		    schESTTime = startDate+" "+startTime; 
           
           }else{				 
        	   schTime=null;
        	   schESTTime=null;
           }
		if("".equals(blast)){
			String que="insert into attendee_blasts(sentcontent,status,sendtobuyer,sendtoattendee,attendee_blast_id,fromemail,subject," +
					"contenttype,content,sch_time,created_at,sch_est_time,eventid,event_date,selected_tkts,select_attendees_type) " +
					"values (?,?,?,?,?,?,?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS')," +
					"to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),?,?,?,?)";
			StatusObj sb=DbUtil.executeUpdateQuery(que,new String[]{showcontent,status,buyer,attendee,blastid,fromemail,subject,contenttype,content,schTime,DateUtil.getCurrDBFormatDate(),schESTTime,eid,selecteddate,selectedtkts,selectedAttType});
		}else{
			String query2="update attendee_blasts set selected_tkts=?,event_date=?,sentcontent=?,sendtobuyer=?,sendtoattendee=?,fromemail=?,subject=?,contenttype=?," +
					"content=?,sch_time=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS')," +
					"sch_est_time=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),created_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),select_attendees_type=? where attendee_blast_id=?";
			StatusObj sb2=DbUtil.executeUpdateQuery(query2,new String[]{selectedtkts,selecteddate,showcontent,buyer,attendee,fromemail,subject,contenttype,content,schTime,schESTTime,DateUtil.getCurrDBFormatDate(),selectedAttType,blast});
	    }
}
	public static DateConverter getESTTime(String eid, EmailAttendeesData edata,DateConverter dateconvert){
	       
        DateTimeBean dtb=new DateTimeBean();
       
        dtb.setYyPart(edata.getYear());
        dtb.setMonthPart(edata.getMonth());
        dtb.setDdPart(edata.getDay());
        dtb.setMmPart(edata.getMinutes());
       
        int hour = Integer.parseInt(edata.getHour());
       
        if(hour==12){
            dtb.setHhPart("12");
            dtb.setAmpm("PM");
        }else if(hour>12){
            hour = hour-12;
            dtb.setHhPart(String.valueOf(hour));
            dtb.setAmpm("PM");
        }else{
            dtb.setHhPart(String.valueOf(hour));
            dtb.setAmpm("AM");
        }
       
        boolean statusdc=dateconvert.convertDateTime(dtb);
           
        String startDate=dateconvert.getDatePart();
        String startTime=dateconvert.getTimePart();
       
      
        return dateconvert;
}
public static void getDetailsForMailSend(String eid,String blastid,EmailAttendeesData emailAttendeesData){
	DBManager dbmanager=new DBManager();
	String query="select fromemail,subject,sentcontent from attendee_blasts where attendee_blast_id=?";
	StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{blastid});
	int count=statobj.getCount();
	if(statobj.getStatus()&&count>0){
		for(int k=0;k<count;k++){
			emailAttendeesData.setEmail(dbmanager.getValue(k,"fromemail",""));
			emailAttendeesData.setSubject(dbmanager.getValue(k,"subject",""));
            emailAttendeesData.setDescription(dbmanager.getValue(k,"sentcontent",""));

	}	
}

}

public static int getEmailCount(String eid)
{
	int count=0;
	try
	{
		count=Integer.parseInt(DbUtil.getVal("select count(*) from attendee_blasts where eventid=? and status <> 'D' ",new String[]{eid}));
	}
	catch(Exception e)
	{
		count=0;
	}
	System.out.println(count);
	return count;
}


public static ArrayList<Entity> getHours(){
	ArrayList<Entity> hrs=new ArrayList<Entity>();
	for( int i=0;i<13;i++){
		String val=Integer.toString(i);
		if(val.length()<2)
			val="0"+val;
		hrs.add(new Entity(val,val));
	}
	return hrs;
}
public static ArrayList<Entity> getMinutes(){
	ArrayList<Entity> mins=new ArrayList<Entity>();
	for( int i=0;i<60;i++){
		String val=Integer.toString(i);
		if(val.length()<2)
			val="0"+val;
		mins.add(new Entity(val,val));
	}
	return mins;
}





public static HashMap<String,String> getBlastDetails(String blastId,String eid){
	HashMap<String,String> blastMap=new HashMap<String,String>();
	if("".equals(blastId)) {
		blastMap.put("fromemail","");
		blastMap.put("subject","");
		blastMap.put("content","");
		blastMap.put("contenttype","");
		blastMap.put("buyer","Y");
	}else{
		String query="select event_date,sendtobuyer,sendtoattendee,fromemail,subject,contenttype,sentcontent,select_attendees_type,selected_tkts from attendee_blasts where attendee_blast_id=? and eventid=?";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{blastId,eid});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
			EmailAttendeesData emailAttendeesData=new EmailAttendeesData();
			emailAttendeesData.setBuyer(dbmanager.getValue(0,"sendtobuyer",""));
			emailAttendeesData.setAttendee(dbmanager.getValue(0,"sendtoattendee",""));
			//emailAttendeesData.setContent(dbmanager.getValue(0,"sentcontent",""));
			blastMap.put("fromemail",dbmanager.getValue(0,"fromemail",""));
			blastMap.put("subject",dbmanager.getValue(0,"subject",""));
			blastMap.put("content",dbmanager.getValue(0,"sentcontent",""));
			blastMap.put("contenttype",dbmanager.getValue(0,"contenttype",""));
		    blastMap.put("buyer",dbmanager.getValue(0,"sendtobuyer",""));
		    blastMap.put("attendee",dbmanager.getValue(0,"sendtoattendee",""));
		    blastMap.put("evt_date",dbmanager.getValue(0,"event_date",""));
		    blastMap.put("attendeestype", dbmanager.getValue(0,"select_attendees_type",""));
		    blastMap.put("selectedTkts", dbmanager.getValue(0,"selected_tkts",""));
		    
		}
	}return blastMap;
}


public static ArrayList<Entity> getCopyFromDetails(String eid){
	ResourceBundle resourceBundle=I18n.getResourceBundle();
	DBManager dbmanager=new DBManager();
	ArrayList<Entity> contentlist=new ArrayList<Entity>();
	String dropdown="";
	String subject="";
	String query="select sch_time,to_char(sch_time, 'Dy, Mon DD, YYYY, HH:00 PM') as time,subject,attendee_blast_id from attendee_blasts where eventid=? order by sch_time desc";
	StatusObj stat=dbmanager.executeSelectQuery(query,new String[]{eid});
	if(stat.getStatus()){
	for (int k=0;k<stat.getCount();k++){
		String time=dbmanager.getValue(k,"time", "");
		if("".equals(dbmanager.getValue(k,"sch_time", ""))){
			 subject=truncateData(dbmanager.getValue(k,"subject", ""),20);
		   dropdown= resourceBundle.getString("ea.drafts.tab.lbl")+" - "+subject+"";
		}else{
			dropdown=time+ " - " +truncateData(dbmanager.getValue(k,"subject",""),20)+"";
		}
		contentlist.add(new Entity(dbmanager.getValue(k,"attendee_blast_id",""),dropdown));
	}
	}
return contentlist;
}

public static void getCreateAttendeeDetails(String eid, EmailAttendeesData emailAttendeesData){
	
	
		//emailAttendeesData.setContentlist(getCopyFromDetails(eid));
		//emailAttendeesData.setEmail(getMgrEmail(eid));
		String userTimeZone = EventDB.getEventTimeZone(eid);
	    	
		Calendar currentdate=Calendar.getInstance();
		currentdate.setTimeZone(TimeZone.getTimeZone(userTimeZone));
		//currentdate.add(Calendar.HOUR,-4);
		String currentmonth=monthvals[currentdate.get(Calendar.MONTH)];
		int date = currentdate.get(Calendar.DATE);
		int hh=currentdate.get(Calendar.HOUR);
		int mm=currentdate.get(Calendar.MINUTE);
		int ampm=currentdate.get(Calendar.AM_PM);
		if(ampm==1 && hh==0)
			hh=hh+12;
		String hours=""+hh;
		String minutes=""+mm;
		if(hours.length()<2)
			hours="0"+hours;
		if(minutes.length()<2)
			minutes="0"+minutes;
		String presentdate=(date<10)?("0"+date):(date+"");
		String dateformate=currentdate.get(Calendar.YEAR)+"-"+currentmonth+"-"+presentdate;
		
		
		try{
			DateTimeBean dtbObj=new DateTimeBean();
			dtbObj.setYyPart(currentdate.get(Calendar.YEAR)+"");
			dtbObj.setDdPart(presentdate);
			dtbObj.setMonthPart(currentmonth);
			dtbObj.setHhPart(hours);
			dtbObj.setMmPart(minutes);
			if(ampm==1)
				dtbObj.setAmpm("PM");
			else
				dtbObj.setAmpm("AM");
			emailAttendeesData.setDateTimeBeanObj(dtbObj);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		}	


public static String truncateData(String basedata, int tsize){
    if(basedata==null) return "";
    if(basedata.length()<=tsize) return basedata;
    return basedata.substring(0,tsize-1 )+"...";
}

public static ArrayList<Entity> getRecurringList(String eid,String powertype){
	ResourceBundle resourceBundle=I18n.getResourceBundle();
ArrayList<Entity> arrlist=new ArrayList<Entity>();	
RegistrationTiketingManager rtm=new RegistrationTiketingManager();
Vector vec=new Vector();
if("RSVP".equalsIgnoreCase(powertype))
vec=getRSVPRecDates(eid);
else
vec=rtm.getRecurringEventDates(eid);
arrlist.add(new Entity("alldates",resourceBundle.getString("ea.all.recc.dates.lbl")));
for(int i=0;i<vec.size();i++)
	arrlist.add(new Entity((String)vec.get(i),(String)vec.get(i)));
return arrlist;
}

public static boolean getTransactionExists(String eid,String dateval){
	String query="select 'yes' from event_reg_transactions where eventid=? and paymentstatus='Completed'";
	String[] arrlist=new String[]{eid};
	if(!"".equals(dateval) && !"alldates".equals(dateval)){
	query=query+" and eventdate=?";
	arrlist=new String[]{eid,dateval};
	}
	String val=DbUtil.getVal(query,arrlist);
	if("".equals(val)||val==null)
		return false;
	else
		return true;	
}
public static Vector getRSVPRecDates(String eid){
Vector vec=new Vector();
String query="select date_display as evt_start_date from event_dates where eventid=CAST(? AS BIGINT)";
DBManager db=new DBManager();  
  String str=null;
  StatusObj stob=db.executeSelectQuery(query,new String[]{eid} );
  if(stob.getStatus()){
  for(int i=0;i<stob.getCount();i++){
  vec.add(db.getValue(i,"evt_start_date",""));
  }
  }
return vec;
}

public static void insertIntoStrayEMail(String eid,EmailAttendeesData edata,String content){
	String query="insert into stray_email( unit_id,purpose,ref_id,mail_count,mail_to,mail_from,mail_subject, "
			+" mail_replyto,text_message,html_message,sch_time,emailtype,mail_cc,mail_bcc,status) "
			+" values (to_number(?,'99999999999999'),?,?, to_number(?,'99999999999999'),?,?,?,?, ?, ?, now(),?,?,?,?)";
		DbUtil.executeUpdateQuery(query, new String[]{"13579","Email_Ateendee_TestMail",eid,"1",
				edata.getMailtotest(),edata.getEmail(),edata.getSubject(),edata.getEmail(),"",content,
				"html","","","S"});
}
			
public static ArrayList<Entity> getTicketsList(String eid){
	
	DBManager dbm=new DBManager();
	//String tktquery="select price_id,ticket_name from price where evt_id=cast(? as bigint)";
	
	String tktquery="select distinct ticketid,ticketname from transaction_tickets t,event_reg_transactions e"+
	" where t.eventid=e.eventid and e.paymentstatus='Completed' and e.eventid=?";
	StatusObj stb=null;
	ArrayList<Entity> tktlist=new ArrayList<Entity>();
	stb=dbm.executeSelectQuery(tktquery, new String[]{eid});
	if(stb.getStatus() && stb.getCount()>0){
		for(int i=0;i<stb.getCount();i++){
               tktlist.add(new Entity(dbm.getValue(i,"ticketid",""),dbm.getValue(i,"ticketname","")));	
		}
	}
	System.out.println("the ticket list is::"+tktlist);
	return tktlist;
}

public static ArrayList getSelectedTktsList(String selectedTkts){
	/*String query="select selected_tkts from attendee_blasts where attendee_blast_id=? and eventid=?";
	String selectedtkts=DbUtil.getVal(query,new String[]{blastid,eid});
	if(selectedtkts==null || "null".equals(selectedtkts) || "".equals(selectedtkts)){
		selectedtkts="";edata.setAllattendee("all");
	}else
		edata.setAllattendee("TICKET");*/
	String strarr[]=GenUtil.strToArrayStr(selectedTkts,",");
	ArrayList arrlist=new ArrayList(Arrays.asList(strarr));
return arrlist;
}




}
