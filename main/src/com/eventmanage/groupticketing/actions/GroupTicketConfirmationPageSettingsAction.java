package com.eventmanage.groupticketing.actions;

import com.event.beans.ConfirmationPageSettingsData;
import com.eventmanage.groupticketing.dbhelpers.GroupTicketConfirmationPageSettigsDB;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class GroupTicketConfirmationPageSettingsAction extends ActionSupport implements Preparable,ValidationAware {
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";
	private String msgType ="groupticketconfirmationpagesettings";
	private ConfirmationPageSettingsData confirmationPageSettingsData=new ConfirmationPageSettingsData();
	private String type="";
	private String content="";
	
	
	public String execute(){
		
		System.out.println("we are in groupticket confirmationpagesettingsaction"+ eid);
		String content=GroupTicketConfirmationPageSettigsDB.getContent(eid,"groupticket_confirmationpage");
		 confirmationPageSettingsData.setContent(content);
	      return "success";
	      
	}
	public String save(){
		//  System.out.println("we are in gtcp save");
		  content=confirmationPageSettingsData.getContent();
		  GroupTicketConfirmationPageSettigsDB.updateContent(eid,content);
		  return "onsubmit";
	      
	}
	
	public String getEid() {
		return eid;
	}


	public void setEid(String eid) {
		this.eid = eid;
	}


	public String getMsgType() {
		return msgType;
	}


	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}


	public ConfirmationPageSettingsData getConfirmationPageSettingsData() {
		return confirmationPageSettingsData;
	}


	public void setConfirmationPageSettingsData(
			ConfirmationPageSettingsData confirmationPageSettingsData) {
		this.confirmationPageSettingsData = confirmationPageSettingsData;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
