package com.eventmanage.actions.ticketing;

import com.event.beans.ticketing.GroupData;
import com.event.beans.ticketing.TicketData;
import com.event.dbhelpers.BadgesDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.TicketsDB;
import com.event.dbhelpers.VolumeTicketsDB;
import com.eventmanage.helpers.JsonBuilderHelper;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class ManageTicketsAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2857352425026605243L;
	private String eid = "";
	private String msgKey = "";
	private ArrayList<GroupData> groupsList = new ArrayList<GroupData>();
	private String jsonData = "";
	private String currenySymbol="";
	TicketData ticketData=new TicketData();
	private String vtjsonData="''";
	private String vbEnabled="";
	private ArrayList volumeTicketList = new ArrayList();
	private String showvb="";
	private String recurringVB="";
	private String ticketcount="";
	private String msgType="ticketcount";
	private String eventleveltktcount="0";
	private String venueenable="YES";
	private ArrayList dates=new ArrayList();
	private String eventDate="";
	private String existingRules="{}";	
	private String rules="";
	private String ruleType="";
	
	
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	
	public String getVenueenable() {
		return venueenable;
	}

	public void setVenueenable(String venueenable) {
		this.venueenable = venueenable;
	}
	
	public String getEventleveltktcount() {
		return eventleveltktcount;
	}

	public void setEventleveltktcount(String eventleveltktcount) {
		this.eventleveltktcount = eventleveltktcount;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getTicketcount() {
		return ticketcount;
	}

	public void setTicketcount(String ticketcount) {
		this.ticketcount = ticketcount;
	}

	public String getRecurringVB() {
		return recurringVB;
	}

	public void setRecurringVB(String recurringVB) {
		this.recurringVB = recurringVB;
	}

	public String getShowvb() {
		return showvb;
	}

	public void setShowvb(String showvb) {
		this.showvb = showvb;
	}

	public ArrayList getVolumeTicketList() {
		return volumeTicketList;
	}

	public void setVolumeTicketList(ArrayList volumeTicketList) {
		this.volumeTicketList = volumeTicketList;
	}

	public String getVtjsonData() {
		return vtjsonData;
	}

	public void setVtjsonData(String vtjsonData) {
		this.vtjsonData = vtjsonData;
	}

	public String getVbEnabled() {
		return vbEnabled;
	}

	public void setVbEnabled(String vbEnabled) {
		this.vbEnabled = vbEnabled;
	}

	public TicketData getTicketData() {
		return ticketData;
	}

	public void setTicketData(TicketData ticketData) {
		this.ticketData = ticketData;
	}

	public String getCurrenySymbol() {
		return currenySymbol;
	}

	public void setCurrenySymbol(String currenySymbol) {
		this.currenySymbol = currenySymbol;
	}

	public String getExistingRules() {
		return existingRules;
	}
	public void setExistingRules(String existingRules) {
		this.existingRules = existingRules;
	}
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String execute() {
		System.out.println("inside excute manage tickets");
		currenySymbol=EventDB.getCurrencySymbol(eid);
        if(currenySymbol==null)
        	currenySymbol="$";
        ticketData.setCurrency(currenySymbol);
		String userTimeZone = EventDB.getEventTimeZone(eid);
		
		
		boolean isrecurr = EventDB.isRecurringEvent(eid);
		if(isrecurr)
		dates=BadgesDB.getEventDates(eid);
		groupsList = TicketsDB.getEventTicketsList(eid, userTimeZone,eventDate);
		HashMap tktStatusMap=TicketsDB.getTicketsStatus(eid,eventDate);
		jsonData = JsonBuilderHelper.getTicketsJson(groupsList,tktStatusMap).toString();
		recurringVB=EventDB.getConfigVal(eid,"event.recurring.volumeticketing.enabled","N");
		venueenable=EventDB.getConfigVal(eid,"event.seating.enabled","NO");
		eventleveltktcount=EventDB.getConfigVal(eid,"event.reg.eventlevelcheck.count","");
		showvb = VolumeTicketsDB.showVolumeTicketing(eid);
		if("Y".equals(showvb)){
			vbEnabled = VolumeTicketsDB.isVolumeTicketingEnabled(eid,"mgr.volumeticketing.enabled");
			if("Y".equals(vbEnabled)){
				volumeTicketList=VolumeTicketsDB.getVolumeTicketsList(eid);
				vtjsonData = JsonBuilderHelper.getVolumeTicketsJson(volumeTicketList).toString();
			}
		}
		
		existingRules=TicketsDB.getExistingRules(eid).toString();
		
		return "success";
	}
	
	public ArrayList getDates() {
		return dates;
	}
	public void setDates(ArrayList dates) {
		this.dates = dates;
	}
	public String populateTicketJson(){
		String userTimeZone = EventDB.getEventTimeZone(eid);
		groupsList = TicketsDB.getEventTicketsList(eid, userTimeZone,eventDate);
		HashMap tktStatusMap=TicketsDB.getTicketsStatus(eid,eventDate);
		jsonData = JsonBuilderHelper.getTicketsJson(groupsList,tktStatusMap).toString();
		return "refresh";
	}
	
	public String getModifiedData(){
		execute();
		return "refresh";
	}
	
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public ArrayList<GroupData> getGroupsList() {
		return groupsList;
	}

	public void setGroupsList(ArrayList<GroupData> groupsList) {
		this.groupsList = groupsList;
	}

	public void prepare() {
		msgKey = "";
	}
	
	public String saveConditionalTicketingRules(){
		System.out.println("while saving ticketing rules::"+eid);
		jsonData=TicketsDB.saveConditionalTicketingRules(eid, rules,ruleType).toString();
		return "statusjson";
	}

}
