package com.user.dbhelpers;

import java.util.HashMap;
import java.util.Map;

import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.TransactionDB;
import com.event.helpers.I18n;
import com.event.i18n.dbmanager.ConfirmationEmailDAO;
import com.eventbee.beans.TransactionData;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.general.EventbeeMail;
import com.eventbee.general.SendMailStatus;
import com.eventbee.general.StatusObj;
import com.eventbee.general.TemplateConverter;
public class MyTicketsActionDB {
	 
	
	public static HashMap<String,String> getEmailTransactions(String emailid){
		System.out.println("\n In MyTicketsActionDB getEmailTransactions: "+emailid);
		HashMap<String,String> hmap=new HashMap<String,String>();
		String status="0";
		try{
			DBManager dbm =new DBManager();
			StatusObj statobj=null;
			String hlink=null;
			String hrline="<hr>";
			String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/";
			String transactionid="",eventid="",eventname="",textmessage="",transactiondate="";
			String query="select et.tid,e.eventname,et.transaction_date from eventinfo e,event_reg_transactions et where e.eventid=CAST(et.eventid AS BIGINT)  and et.paymentstatus in('Completed','Authorized','Cancelled') and et.email=?";
			statobj=dbm.executeSelectQuery(query,new String[]{emailid});
			HashMap emailcontent=new HashMap();
			StringBuffer sb=new StringBuffer();
			if(statobj.getStatus() && statobj.getCount()==1){
				transactionid=dbm.getValue(0,"tid","");
				status="3";
				hmap.put("status",status);
				hmap.put("tid", transactionid);
			}else if(statobj.getStatus() && statobj.getCount()>1){
				for(int i=0;i<statobj.getCount();i++){
					transactionid=dbm.getValue(i,"tid","");
					eventname=dbm.getValue(i,"eventname","");
					transactiondate=dbm.getValue(i,"transaction_date","");
					hlink="<a href='"+serveraddress+"main/user/mytickets?tid="+transactionid+"'>"+I18n.getString("global.get.tickets.click.here.lbl")+"</a>";
					sb.append("<p>"+I18n.getString("global.event.lbl")+": "+eventname);
					sb.append("<br>"+I18n.getString("global.transaction.id.lbl")+": "+transactionid);
					sb.append("<br>"+I18n.getString("global.transaction.date.lbl")+": "+transactiondate);
					sb.append("<P>"+hlink);
					sb.append(hrline);
	     	   	}
				emailcontent.put("Transaction",sb.toString());
				status = sendEmail(emailcontent,"13579",emailid);
				hmap.put("status",status);
			}else {
				status = "2";
				hmap.put("status", status);
			}	
			 
		}catch(Exception e){
			status="0";
			hmap.put("status", status);
			System.out.println("\n Exception In MyTicketsActionDB getEmailTransactions: "+e.getMessage());
		}
		return hmap;
	}
    
	/*public static void getTicketsInformation(String transactionid){
		DBManager dbm =new DBManager();
    	StatusObj statobj=null;
    	String eid="",tid="",powertype="";
    	//System.out.println("transaction id recieved:"+transactionid);
    	String query="select eventid from event_reg_transactions where tid=?";
    	statobj=dbm.executeSelectQuery(query,new String[]{transactionid});
    	for(int i=0;i<statobj.getCount();i++)
    	{
    	 eid=dbm.getValue(i,"eventid","");
    	}
    	powertype=EventDB.getPowerType(eid);
    	System.out.println("we are in getTicketsInformation method");
    	TransactionDB.resendingMail(eid,transactionid,powertype);
	}*/
	public static String sendEmail(HashMap emailcontent,String unitid,String emailid){
		System.out.println("\n In MyTicketsActionDB sendEmail: "+emailid);
		String status = "0";
		try{
			String purpose="GET_MY_TICKETS";
			/*EmailTemplate emailtemplate=new EmailTemplate(unitid,purpose);*/
			String lang=I18n.getLanguageFromSession();
			HashMap<String, String> purposeHm= new HashMap<String,String>();
			purposeHm.put("purpose", purpose);
			purposeHm.put("isCustomFeature", "N");
			HashMap<String, String> emailTemplate=new HashMap<String, String>();
			ConfirmationEmailDAO emailDao=new ConfirmationEmailDAO();
			emailTemplate=(HashMap<String, String>)emailDao.getData(purposeHm, lang, "").get("emailTemplate");
			EmailObj emailobj=EventbeeMail.getEmailObj();
			emailobj.setFrom(emailTemplate.get("fromMail"));
			String replyto=emailTemplate.get("replyToMail");//emailtemplate.getReplyToMail();
			if(replyto==null||"".equals(replyto.trim()))
				replyto=emailTemplate.get("fromMail");//emailtemplate.getFromMail();
			emailobj.setReplyTo(replyto);
			emailobj.setTo(emailid);
			Map message=fillData(emailcontent);
			//System.out.println(message);
			emailobj.setSubject(TemplateConverter.getMessage(message,emailTemplate.get("subjectFormat")));
			emailobj.setHtmlMessage(TemplateConverter.getMessage(message,emailTemplate.get("htmlFormat")));
			emailobj.setSendMailStatus(new SendMailStatus("13579",purpose,"0","1"));
			EventbeeMail.sendHtmlMailPlain(emailobj);
			status = "1";
		}catch(Exception e){
			status = "0";
			System.out.println("Exception In MyTicketsActionDB sendEmail method:"+ e.getMessage());
		}
		return status;
	}
	public static Map fillData(HashMap emailcontent){
		Map mp=new HashMap();
		mp.put("#**transactionid**#",(String)emailcontent.get("Transaction"));
		return mp;
	}  
	public static int resendDirectEmail(String tid){
		System.out.println("\n In MyTicketsActionDB resendDirectEmail: "+tid);
		int status = 0;
		try{	   
			
			String powertype="";
			String query="select eventid from event_reg_transactions where tid=?";
			String eid=DbUtil.getVal(query,new String[]{tid});
			if(eid != null){	 
				powertype=EventDB.getPowerType(eid);
				status = TransactionDB.resendingDirectMail(eid,tid,powertype);
			}else
				status = 2;
				
			/*DbUtil.executeUpdateQuery("update stray_email set mail_to=? where html_message like '%"+tid+"%'",new String[]{emid});
		    	System.out.println("updated stray_email");*/
		      	
			/*if(status == 1){
				DbUtil.executeUpdateQuery("insert into mytickets_track(tid,emailid,eventname,date)values(?,?,?,now())",new String[]{tid,email,eventname});
				System.out.println("data inserted in mytickets_track");
			}*/
	    }catch(Exception e){
	    	System.out.println("\n Exception in MyTicketsActionDB resendDirectEmail: "+e.getMessage());
	    }
	    return status;
	 }	
	
	
	public static TransactionData getRegistrationDetails(String tid){
		TransactionData transactionData = new TransactionData();
		String query = "select e.email as mgremail,e.eventid,e.eventname,mgr_id,first_name||' '||last_name as mgrname," +
				"to_char(ert.transaction_date, 'DD Mon YYYY') as tDate from eventinfo e,event_reg_transactions ert, user_profile uf where " +
				"CAST(ert.eventid AS BIGINT)=e.eventid and to_number(uf.user_id,'999999999999')=e.mgr_id " +
				"and tid=?";
		DBManager dbmanager = new DBManager();
		StatusObj statobj=null;
		statobj = dbmanager.executeSelectQuery(query, new String[]{tid});
		if(statobj.getStatus() && statobj.getCount()>0){
			
			transactionData.setEname(dbmanager.getValue(0, "eventname", ""));
			transactionData.setTid(tid);
			transactionData.setTdate(dbmanager.getValue(0, "tDate", ""));
			transactionData.setMgrEmail(dbmanager.getValue(0, "mgremail", ""));
		}
		return transactionData;
	}
	       

}