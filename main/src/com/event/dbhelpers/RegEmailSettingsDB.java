package com.event.dbhelpers;

import java.util.HashMap;

import com.event.beans.RegEmailSettingsData;
import com.event.i18n.dbmanager.ConfirmationEmailDAO;
import com.eventbee.general.DbUtil;

public class RegEmailSettingsDB {
	public static void updateRegEmailSettings(String eid,RegEmailSettingsData regEmailSettings, String templateType){
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name='event.sendmailtoattendee'",new String [] {configid});
		if("on".equals(regEmailSettings.getSendToAttendee()))
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.sendmailtoattendee','yes')",new String [] {configid});
		else
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.sendmailtoattendee','no')",new String [] {configid});

		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name='event.sendmailtomgr'",new String [] {configid});
		if("on".equals(regEmailSettings.getSendToManager()))
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.sendmailtomgr','yes')",new String [] {configid});
		else
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.sendmailtomgr','no')",new String [] {configid});

			DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name='registration.email.cc.list'",new String [] {configid});
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? AS INTEGER),'registration.email.cc.list',?)",new String [] {configid,regEmailSettings.getCc()});
			//System.out.println("power:::::"+regEmailSettings.getPowerType());
		String purpose="";
		if("RSVP".equals(regEmailSettings.getPowerType())){
			purpose="RSVP_CONFIRMATION";
			String DELETE_TEMPLATE="delete from custom_email_templates where purpose=? and groupid=?";
			String INSERT_TEMPLATE="insert into custom_email_templates(groupid,subjectformat,htmlformat,textformat,replytoemail,fromemail,purpose) values (?,?,?,?,?,?,?)";
			DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{purpose,eid});
			DbUtil.executeUpdateQuery(INSERT_TEMPLATE,new String[]{eid,regEmailSettings.getSubject(),regEmailSettings.getTempalteContent(),null,"$mgrEmail", "$mgrEmail",purpose});
		}else{
			if("COMPLETED".equals(templateType)){
				purpose="EVENT_REGISTARTION_CONFIRMATION_COMPLETED";
				String DELETE_TEMPLATE="delete from custom_email_templates where  purpose=? and groupid=?";
				String INSERT_TEMPLATE="insert into custom_email_templates(groupid,subjectformat,htmlformat,textformat,replytoemail,fromemail,purpose) values (?,?,?,?,?,?,?)";
				DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{purpose,eid});
				DbUtil.executeUpdateQuery(INSERT_TEMPLATE,new String[]{eid,regEmailSettings.getSubject(),regEmailSettings.getTempalteContent(),null,"$mgrEmail", "$mgrEmail",purpose});
			}else{
				purpose="EVENT_REGISTARTION_CONFIRMATION_PENDING";
				String DELETE_TEMPLATE="delete from custom_email_templates where purpose=? and groupid=?";
				String INSERT_TEMPLATE="insert into custom_email_templates(groupid,subjectformat,htmlformat,textformat,replytoemail,fromemail,purpose) values (?,?,?,?,?,?,?)";
				DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{purpose,eid});
				DbUtil.executeUpdateQuery(INSERT_TEMPLATE,new String[]{eid,regEmailSettings.getSubject(),regEmailSettings.getTempalteContent(),null,"$mgrEmail", "$mgrEmail",purpose});
			}
		}
	}
	
	public static void resetRegEmailSettings(String eid, String powerType, String templateType){
		String purpose="";
		if("RSVP".equals(powerType)){
			purpose="RSVP_CONFIRMATION";
			String DELETE_TEMPLATE="delete from custom_email_templates where  purpose=? and groupid=?";
			DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{purpose,eid});
		}else{			
			if("COMPLETED".equals(templateType)){
				purpose="EVENT_REGISTARTION_CONFIRMATION_COMPLETED";
				DbUtil.executeUpdateQuery("delete from custom_email_templates where  purpose=? and groupid=?",new String[]{purpose,eid});
			}else{
				purpose="EVENT_REGISTARTION_CONFIRMATION_PENDING";
				DbUtil.executeUpdateQuery("delete from custom_email_templates where  purpose=? and groupid=?",new String[]{purpose,eid});
			}
		}	
	}
	
	public static HashMap<String, String> getEmailTemplate(HashMap<String, String> hm, String lang, String eid){
		ConfirmationEmailDAO emailDao=new ConfirmationEmailDAO();
		return (HashMap<String, String>)emailDao.getData(hm, lang, eid).get("emailTemplate");
	}
	
}
