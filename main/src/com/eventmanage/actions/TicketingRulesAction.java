package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;

import com.event.beans.ticketing.GroupData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.TicketsDB;
import com.eventmanage.helpers.JsonBuilderHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TicketingRulesAction extends ActionSupport{
	
	private String eid = "";
	private String existingRules="{}";
	private ArrayList<GroupData> groupsList = new ArrayList<GroupData>();
	private String eventDate="";
	private String jsonData = "";
	
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getExistingRules() {
		return existingRules;
	}

	public void setExistingRules(String existingRules) {
		this.existingRules = existingRules;
	}

	public ArrayList<GroupData> getGroupsList() {
		return groupsList;
	}

	public void setGroupsList(ArrayList<GroupData> groupsList) {
		this.groupsList = groupsList;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String execute() {
		Boolean redirect = SpecialFeeDB.checking(eid,"TicketingRules","Ticketing","400");
		if(redirect)
			return "pageRedirect";
		else{
			existingRules=TicketsDB.getExistingRules(eid).toString();
			String userTimeZone = EventDB.getEventTimeZone(eid);
			groupsList = TicketsDB.getEventTicketsList(eid, userTimeZone,eventDate);
			HashMap tktStatusMap=TicketsDB.getTicketsStatus(eid,eventDate);
			jsonData = JsonBuilderHelper.getTicketsJson(groupsList,tktStatusMap).toString();
			return "success";
		}
	}

}
