package com.eventmanage.groupticketing.dbhelpers;
import com.event.beans.RegEmailSettingsData;
import com.eventbee.general.DbUtil;
public class GroupTicketConfirmationEmailDB {
	
	public static void updateRegEmailSettings(String eid, RegEmailSettingsData regEmailSettingsData,String emailpurpose){
		
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		
		
		DbUtil.executeUpdateQuery("delete from config where config_id=to_number(?,'99999999999999') and name='event.sendmailtoattendee'",new String [] {configid});
		if("on".equals(regEmailSettingsData.getSendToAttendee()))
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'event.sendmailtoattendee','yes')",new String [] {configid});
		else
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'event.sendmailtoattendee','no')",new String [] {configid});

		
		
		DbUtil.executeUpdateQuery("delete from config where config_id=to_number(?,'99999999999999') and name='event.sendmailtomgr'",new String [] {configid});
		if("on".equals(regEmailSettingsData.getSendToManager()))
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'event.sendmailtomgr','yes')",new String [] {configid});
		else
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'event.sendmailtomgr','no')",new String [] {configid});

			DbUtil.executeUpdateQuery("delete from config where config_id=to_number(?,'99999999999999') and name='registration.email.cc.list'",new String [] {configid});
			
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'registration.email.cc.list',?)",new String [] {configid,regEmailSettingsData.getCc()});
			
			
			
		    if(emailpurpose.equals("onpurchase")){
		    	
			String DELETE_TEMPLATE="delete from email_templates where  purpose='GROUPTICKET_CONFIRMATION_ONPURCHASE' and groupid=?";
			String INSERT_TEMPLATE="insert into email_templates(groupid,subjectformat,htmlformat,textformat, replytoemail,fromemail,purpose,unitid) values (?,?,?,?,?,?,?,?)";
			DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{eid});
			DbUtil.executeUpdateQuery(INSERT_TEMPLATE,new String[]{eid,regEmailSettingsData.getSubject(),regEmailSettingsData.getTempalteContent(),null,"$mgrEmail", "$mgrEmail","GROUPTICKET_CONFIRMATION_ONPURCHASE","13579"});
		
		    }
		    if(emailpurpose.equals("ontriggerreach")){
		    	
		    	String DELETE_TEMPLATE="delete from email_templates where  purpose='GROUPTICKET_CONFIRMATION_ONTRIGGERREACH' and groupid=?";
				String INSERT_TEMPLATE="insert into email_templates(groupid,subjectformat,htmlformat,textformat, replytoemail,fromemail,purpose,unitid) values (?,?,?,?,?,?,?,?)";
				DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{eid});
				DbUtil.executeUpdateQuery(INSERT_TEMPLATE,new String[]{eid,regEmailSettingsData.getSubject(),regEmailSettingsData.getTempalteContent(),null,"$mgrEmail", "$mgrEmail","GROUPTICKET_CONFIRMATION_ONTRIGGERREACH","13579"});
			
		    	
		    }
		    if(emailpurpose.equals("ontriggerfail")){
		    	
		    	String DELETE_TEMPLATE="delete from email_templates where  purpose='GROUPTICKET_CONFIRMATION_ONTRIGGERFAIL' and groupid=?";
				String INSERT_TEMPLATE="insert into email_templates(groupid,subjectformat,htmlformat,textformat, replytoemail,fromemail,purpose,unitid) values (?,?,?,?,?,?,?,?)";
				DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{eid});
				DbUtil.executeUpdateQuery(INSERT_TEMPLATE,new String[]{eid,regEmailSettingsData.getSubject(),regEmailSettingsData.getTempalteContent(),null,"$mgrEmail", "$mgrEmail","GROUPTICKET_CONFIRMATION_ONTRIGGERFAIL","13579"});
			
		    }
		
	}

}
