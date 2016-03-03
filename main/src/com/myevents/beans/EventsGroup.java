package com.myevents.beans;

import java.util.ArrayList;
import java.util.List;

import com.eventbee.beans.Entity;

public class EventsGroup {
	private String eventgroupid = "";
	private List<Entity> optionsList;
	private String title = "";
	private String userName="";
	private String customURL="";
	private String eventURL="";
	private String mgrId="";
	
	private List<Entity> events;
	private List<Entity> groupEvents;
	public EventsGroup(){
		events = new ArrayList<Entity>();
		groupEvents = new ArrayList<Entity>();
		optionsList = new ArrayList<Entity>();
	}
	public String getMgrId() {
		return mgrId;
	}
	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}
	public String getEventgroupid() {
		return eventgroupid;
	}
	public void setEventgroupid(String eventgroupid) {
		this.eventgroupid = eventgroupid;
	}
	public List<Entity> getOptionsList() {
		return optionsList;
	}
	public void setOptionsList(List<Entity> optionsList) {
		this.optionsList = optionsList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Entity> getEvents() {
		return events;
	}
	public void setEvents(List<Entity> events) {
		this.events = events;
	}
	public List<Entity> getGroupEvents() {
		return groupEvents;
	}
	public void setGroupEvents(List<Entity> groupEvents) {
		this.groupEvents = groupEvents;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustomURL() {
		return customURL;
	}
	public void setCustomURL(String customURL) {
		this.customURL = customURL;
	}
	public String getEventURL() {
		return eventURL;
	}
	public void setEventURL(String eventURL) {
		this.eventURL = eventURL;
	}
}
