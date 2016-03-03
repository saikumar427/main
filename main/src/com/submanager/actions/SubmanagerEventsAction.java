package com.submanager.actions;

import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONObject;

import com.event.dbhelpers.MyEventsDB;
import com.myevents.helpers.EventsJsonBuilder;
import com.submanager.dbhelpers.SubmanagerEventsDB;

public class SubmanagerEventsAction extends SubmangerAuthWrapper{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1880443441180248908L;
	private ArrayList recentClosedEvents=new ArrayList();
	private HashMap<String, String> eventsSummary=new HashMap<String, String>();
	private String streamerCodeString="";
	private String lastListedEvtJsonData = "[]";
	private String recentClosedEvtJsonData = "[]";
	private String userName="";
	protected String serveraddress="";
	private String jsonData;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLastListedEvtJsonData() {
		return lastListedEvtJsonData;
	}
	public void setLastListedEvtJsonData(String lastListedEvtJsonData) {
		this.lastListedEvtJsonData = lastListedEvtJsonData;
	}
	public String getRecentClosedEvtJsonData() {
		return recentClosedEvtJsonData;
	}
	public void setRecentClosedEvtJsonData(String recentClosedEvtJsonData) {
		this.recentClosedEvtJsonData = recentClosedEvtJsonData;
	}
	public String getStreamerCodeString() {
		return streamerCodeString;
	}
	public void setStreamerCodeString(String streamerCodeString) {
		this.streamerCodeString = streamerCodeString;
	}

	public String execute(){
		if(submgrId==null || "".equals(submgrId)) return "error";
		eventsSummary=SubmanagerEventsDB.populateMyEventsSummary(submgrId);	
		return "success";	
	}
	public String populateEventsList()
	{
		System.out.println(eventsSummary);
		ArrayList activeEventsList=SubmanagerEventsDB.populateUpComingEvents(submgrId,0);
		ArrayList closedEventsList=SubmanagerEventsDB.populateRecentClosedEvents(submgrId,0);
		ArrayList suspendedEventsList=SubmanagerEventsDB.populateSuspendedEvents(submgrId,0);
		JSONObject js= EventsJsonBuilder.getEventsJson(activeEventsList, closedEventsList, suspendedEventsList);
		//System.out.println("jsonData: "+js);
		jsonData=js.toString();
		return "eventsjsondata";		
	}
	public ArrayList getRecentClosedEvents() {
		return recentClosedEvents;
	}
	public void setRecentClosedEvents(ArrayList recentClosedEvents) {
		this.recentClosedEvents = recentClosedEvents;
	}
	public HashMap getEventsSummary() {
		return eventsSummary;
	}
	public void setEventsSummary(HashMap eventsSummary) {
		this.eventsSummary = eventsSummary;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	
	
	
	
}
