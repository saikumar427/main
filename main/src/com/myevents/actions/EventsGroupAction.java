package com.myevents.actions;

import java.util.List;
import com.event.dbhelpers.EventsGroupDB;
import com.eventbee.beans.Entity;
import com.myevents.beans.EventsGroup;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class EventsGroupAction extends MyEventsActionWrapper implements
		Preparable, ValidationAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EventsGroup grpevent;
	private String msgKey = "";
	private String eventgroupid = "";
	private String purpose="";
	private List<Entity> optionsList;
	@Override
	public void prepare() throws Exception {
		super.prepare();
	}
	
	public String addEventGroup(){
		grpevent = new EventsGroup();		
		EventsGroupDB.getActiveEvents(purpose,mgrId,grpevent);		
		return "add";
	}
	public String editEventGroup(){
		grpevent = new EventsGroup();
		grpevent.setEventgroupid(eventgroupid);
		purpose = "edit";
		EventsGroupDB.getActiveEvents(purpose, mgrId, grpevent);		
		EventsGroupDB.getGroupEvents(grpevent);
		return "edit";
	}
	public String deleteEventGroup(){
		grpevent = new EventsGroup();
		grpevent.setEventgroupid(eventgroupid);		
		EventsGroupDB.deleteEventGroup(eventgroupid);		
		return "deleted";
	}
	public String saveEventGroup(){
		grpevent.setOptionsList(optionsList);
		EventsGroupDB.saveEventsGroup(mgrId, purpose, grpevent);		
		msgKey = "success";
		return "ajaxmsg";
	}
	
	public List<Entity> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(List<Entity> optionsList) {
		this.optionsList = optionsList;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	

	public EventsGroup getGrpevent() {
		return grpevent;
	}

	public void setGrpevent(EventsGroup grpevent) {
		this.grpevent = grpevent;
	}

	public String getEventgroupid() {
		return eventgroupid;
	}

	public void setEventgroupid(String eventgroupid) {
		this.eventgroupid = eventgroupid;
	}
}
