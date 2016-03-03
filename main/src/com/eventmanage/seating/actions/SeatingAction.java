package com.eventmanage.seating.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.TicketShowHideDB;
import com.eventbee.beans.Entity;
import com.eventmanage.seating.beans.Section;
import com.eventmanage.seating.beans.Venue;
import com.eventmanage.seating.dbhelpers.SeatingDB;
import com.eventmanage.seating.helpers.SeatingJsonHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SeatingAction extends ActionSupport{
/**
	 * 
	 */
	private static final long serialVersionUID = 2406028177432273164L;
	//------------------------------------------Properties-----------------------
	private String eid;
	private String venueid;
	private String sectionid;
	private ArrayList<Entity> venueList=new ArrayList<Entity>();
	private ArrayList<Entity> sectionsList;
	private JSONObject messageJson=new JSONObject();
	private ArrayList<Entity> eventdates=new ArrayList<Entity>();
	private String eventdate="";
	private boolean hasvenue;
	private String msg="";
	private String colorCode="";
	private String tckts="";
	private Venue venue=new Venue();
	private ArrayList alltickets=new ArrayList();
	private ArrayList seltickets=new ArrayList();
	private LinkedHashMap<String, ArrayList<String>> supportedcolors=new LinkedHashMap<String, ArrayList<String>>();
	private String powerType="";
	private String isrecurring="";
	private int regcount=0;
	private boolean showvenue;
	private String lockedData="";
	private String seatColorData="";
	public String getSeatColorData() {
		return seatColorData;
	}
	public void setSeatColorData(String seatColorData) {
		this.seatColorData = seatColorData;
	}
	private String tid="";
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getLockedData() {
		return lockedData;
	}
	public void setLockedData(String lockedData) {
		this.lockedData = lockedData;
	}
	//------------------------------------------Setters and Getters-------------
	public String getEventdate() {
		return eventdate;
	}
	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getVenueid() {
		return venueid;
	}
	public void setVenueid(String venueid) {
		this.venueid = venueid;
	}
	public String getSectionid() {
		return sectionid;
	}
	public void setSectionid(String sectionid) {
		this.sectionid = sectionid;
	}
	public ArrayList<Entity> getVenueList() {
		return venueList;
	}
	public void setVenueList(ArrayList<Entity> venueList) {
		this.venueList = venueList;
	}
	public ArrayList<Entity> getSectionsList() {
		return sectionsList;
	}
	public void setSectionsList(ArrayList<Entity> sectionsList) {
		this.sectionsList = sectionsList;
	}
	public JSONObject getMessageJson() {
		return messageJson;
	}
	public void setMessageJson(JSONObject messageJson) {
		this.messageJson = messageJson;
	}
	public boolean isHasvenue() {
		return hasvenue;
	}
	public void setHasvenue(boolean hasvenue) {
		this.hasvenue = hasvenue;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getTckts() {
		return tckts;
	}
	public void setTckts(String tckts) {
		this.tckts = tckts;
	}
	public Venue getVenue() {
		return venue;
	}
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	public ArrayList getAlltickets() {
		return alltickets;
	}
	public void setAlltickets(ArrayList alltickets) {
		this.alltickets = alltickets;
	}
	public ArrayList getSeltickets() {
		return seltickets;
	}
	public void setSeltickets(ArrayList seltickets) {
		this.seltickets = seltickets;
	}
	public String getPowerType() {
		return powerType;
	}
	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}
	public LinkedHashMap<String, ArrayList<String>> getSupportedcolors() {
		return supportedcolors;
	}
	public void setSupportedcolors(
			LinkedHashMap<String, ArrayList<String>> supportedcolors) {
		this.supportedcolors = supportedcolors;
	}
	public String getIsrecurring() {
		return isrecurring;
	}
	public void setIsrecurring(String isrecurring) {
		this.isrecurring = isrecurring;
	}
	public ArrayList<Entity> getEventdates() {
		return eventdates;
	}
	public void setEventdates(ArrayList<Entity> eventdates) {
		this.eventdates = eventdates;
	}
	public boolean isShowvenue() {
		return showvenue;
	}
	public void setShowvenue(boolean showvenue) {
		this.showvenue = showvenue;
	}
	public int getRegcount() {
		return regcount;
	}
	public void setRegcount(int regcount) {
		this.regcount = regcount;
	}

//------------------------------------------Action Methods-----------------
	public String execute(){
		populateData();
			return "success";
	}
	void populateData(){
		hasvenue=false;
		eventdates=TicketShowHideDB.getEventDates(eid);
		venueid=SeatingDB.getVenue(eid);
		if("".equals(venueid) || venueid==null)	hasvenue=false;
		else hasvenue=true;
		if(hasvenue) populateSections();
		regcount=SeatingDB.getRegCount(eid);
		venueList=SeatingDB.getUserVenues(EventDB.getUserId(eid));
		ArrayList lockedList=SeatingDB.getLockedSeatsData(eid);
		lockedData=SeatingJsonHelper.getLockedJSONData(lockedList);
		seatColorData=SeatingDB.getAllSeatColors(eid);
		
		//else populateVenues();
	}
	
	public String releaseSeats(){
		System.out.println("the eid is::"+eid);   
		System.out.println("the tid is::"+tid);
		SeatingDB.realeseSeats(eid,tid);
	return "ajaxjson";
	}
	
	
	public String getSections(){
		sectionsList=SeatingDB.GetVenueSections(venueid);
		return "sections";
	}
	public void populateVenues(){
		venueList=SeatingDB.getVenueList();
		//venueList=SeatingDB.getUserVenues(EventDB.getUserId(eid));
	}
	public void populateSections(){
		showvenue=SeatingDB.getShowVenue(eid);
		venue=SeatingDB.getVenueDetails(venueid);
		sectionsList=SeatingDB.GetVenueSections(venueid);
	}
	public String getSectionDetails(){
		messageJson=SeatingJsonHelper.getSectionSeatingDetails(sectionid, eid,eventdate,isrecurring);
		msg=messageJson.toString();
		return "ajaxjson";
	}
	public String addVenue(){    
		// adding for special fee start
		String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
		SpecialFeeDB.chekingSpecialFee(eid,mgrId,"300","Venue Seating");
		// special fee end.
		SeatingDB.addVenue(eid, venueid);
		return execute();
	}
	public String getTicketsandColors(){
		supportedcolors=SeatingDB.getSupportedSeatColors(eid, sectionid);
		if(supportedcolors.size()<1){
			msg=supportedcolors.size()+"";
			return "ajaxjson";
		}
		else{
			alltickets=EventDB.getEventTickets(eid);
			return "addseat";
		}
	}
	public String addSeat(){
		SeatingDB.addSeat(alltickets, colorCode, eid, venueid);
		populateData();
		return "success";
	}
	public String deleteSeat(){
		SeatingDB.deleteSeat(colorCode, eid, venueid);
		populateData();
		return "success";
	}
	public String getSeatColors(){
		JSONObject seattypes=SeatingJsonHelper.getSeatColorsJson(eid,powerType);
		msg=seattypes.toString();
		return "ajaxjson";
	}
	public String saveSeats(){
		try{
		JSONObject obj=new JSONObject();
		obj=(JSONObject)new JSONTokener(msg).nextValue();
		JSONArray ja=obj.getJSONArray("seating");
		SeatingDB.saveSeats(eid, venueid, sectionid, ja);
		}catch (Exception e) {
		}
		msg="success";
		return "ajaxjson";
	}
	public String getTicketsForColor(){
		alltickets=SeatingDB.getEventTickets(eid);
		if(alltickets.size()<1)
		{
			msg="notickets";
			return "ajaxjson";
		}else{
		seltickets=SeatingDB.getselectedTickets(eid, colorCode);
		return "showtickets";
		}
	}
	public String updateSeatTickets(){
		SeatingDB.updateSeatTickets(seltickets, eid, venueid, colorCode);
		populateData();
		return "success";
	}
	public String addManual(){
		populateData();
		return "addmanual";
	}
	public String saveManual(){
		try{
			JSONObject obj=new JSONObject();
			obj=(JSONObject)new JSONTokener(msg).nextValue();
			JSONArray ja=obj.getJSONArray("seating");
			SeatingDB.saveAddManual(eid, venueid, sectionid, ja,eventdate);
			}catch (Exception e) {
			}
			msg="success";
			return "ajaxjson";
	}
	public String showvenue(){
		SeatingDB.showVenue(eid, showvenue, venueid);
		msg="success";
		return "ajaxjson";
	}
}