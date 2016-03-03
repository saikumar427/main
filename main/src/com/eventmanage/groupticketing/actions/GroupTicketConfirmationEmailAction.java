package com.eventmanage.groupticketing.actions;



import com.event.beans.RegEmailSettingsData;
import com.event.dbhelpers.EventDB;
import com.eventmanage.groupticketing.dbhelpers.GroupTicketConfirmationEmailDB;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EmailTemplate;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class GroupTicketConfirmationEmailAction extends ActionSupport implements Preparable,ValidationAware{
	private static final long serialVersionUID = 3638845334032069827L;
	private String eid="";
	private EmailTemplate emailtemplate=null;
	private RegEmailSettingsData regEmailSettingsData=new RegEmailSettingsData();
	private String msgType ="";
	private String powerType="";
	private String emailpurpose="";

	public String getEmailpurpose() {
		return emailpurpose;
	}


	public void setEmailpurpose(String emailpurpose) {
		this.emailpurpose = emailpurpose;
	}


	public String getEid() {
		return eid;
	}


	public void setEid(String eid) {
		this.eid = eid;
	}


	public EmailTemplate getEmailtemplate() {
		return emailtemplate;
	}


	public void setEmailtemplate(EmailTemplate emailtemplate) {
		this.emailtemplate = emailtemplate;
	}


	public RegEmailSettingsData getRegEmailSettingsData() {
		return regEmailSettingsData;
	}


	public void setRegEmailSettingsData(RegEmailSettingsData regEmailSettingsData) {
		this.regEmailSettingsData = regEmailSettingsData;
	}


	public String getMsgType() {
		return msgType;
	}


	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}


	public String getPowerType() {
		return powerType;
	}


	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}


	public String execute(){
		return "success";
	}
	
	
	 public String onPurchase(){
		 System.out.println("we r in onpurchase"+eid);
		 String isformatexists=DbUtil.getVal("select 'yes' from email_templates where purpose='GROUPTICKET_CONFIRMATION_ONPURCHASE' and groupid=?", new String []{eid});
			if("yes".equals(isformatexists)){
				emailtemplate=new EmailTemplate("13579","GROUPTICKET_CONFIRMATION_ONPURCHASE",eid);
			}else{
				emailtemplate=new EmailTemplate("500","GROUPTICKET_CONFIRMATION_ONPURCHASE");
			}
			
			regEmailSettingsData.setTempalteContent(emailtemplate.getHtmlFormat());
			regEmailSettingsData.setSubject(emailtemplate.getSubjectFormat());
			String sendtoattendee=EventDB.getConfigVal(eid, "event.sendmailtoattendee", "yes");
			String sendtomgr=EventDB.getConfigVal(eid, "event.sendmailtomgr", "yes");		
			regEmailSettingsData.setSendToAttendee(sendtoattendee);
			regEmailSettingsData.setSendToManager(sendtomgr);
			regEmailSettingsData.setCc(EventDB.getConfigVal(eid, "registration.email.cc.list", ""));
		
		 return "onpurchase";
	 }
	 public String onTriggerReach(){
		
		 String isformatexists=DbUtil.getVal("select 'yes' from email_templates where purpose='GROUPTICKET_CONFIRMATION_ONTRIGGERREACH' and groupid=?", new String []{eid});
			if("yes".equals(isformatexists)){
				
				emailtemplate=new EmailTemplate("13579","GROUPTICKET_CONFIRMATION_ONTRIGGERREACH",eid);
			}else{
				
				emailtemplate=new EmailTemplate("500","GROUPTICKET_CONFIRMATION_ONTRIGGERREACH");
			}
			
			regEmailSettingsData.setTempalteContent(emailtemplate.getHtmlFormat());
			regEmailSettingsData.setSubject(emailtemplate.getSubjectFormat());
			String sendtoattendee=EventDB.getConfigVal(eid, "event.sendmailtoattendee", "yes");
			String sendtomgr=EventDB.getConfigVal(eid, "event.sendmailtomgr", "yes");		
			regEmailSettingsData.setSendToAttendee(sendtoattendee);
			regEmailSettingsData.setSendToManager(sendtomgr);
			regEmailSettingsData.setCc(EventDB.getConfigVal(eid, "registration.email.cc.list", ""));
		
		 return "ontriggerreach";
	 }
	 public String onTriggerFail(){
		 
		 String isformatexists=DbUtil.getVal("select 'yes' from email_templates where purpose='GROUPTICKET_CONFIRMATION_ONTRIGGERFAIL' and groupid=?", new String []{eid});
			if("yes".equals(isformatexists)){
				
				emailtemplate=new EmailTemplate("13579","GROUPTICKET_CONFIRMATION_ONTRIGGERFAIL",eid);
			}else{
				
				emailtemplate=new EmailTemplate("500","GROUPTICKET_CONFIRMATION_ONTRIGGERFAIL");
			}
			
			regEmailSettingsData.setTempalteContent(emailtemplate.getHtmlFormat());
			regEmailSettingsData.setSubject(emailtemplate.getSubjectFormat());
			String sendtoattendee=EventDB.getConfigVal(eid, "event.sendmailtoattendee", "yes");
			String sendtomgr=EventDB.getConfigVal(eid, "event.sendmailtomgr", "yes");		
			regEmailSettingsData.setSendToAttendee(sendtoattendee);
			regEmailSettingsData.setSendToManager(sendtomgr);
			regEmailSettingsData.setCc(EventDB.getConfigVal(eid, "registration.email.cc.list", ""));
		
		 return "ontriggerfail";
	 }
	 public String save(){
		
			GroupTicketConfirmationEmailDB.updateRegEmailSettings(eid, regEmailSettingsData,emailpurpose);
			return "save";
		}
		
	 public void prepare() throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	 
	}
