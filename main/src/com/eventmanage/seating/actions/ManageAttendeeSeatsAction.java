package com.eventmanage.seating.actions;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;


import com.eventbee.beans.Entity;
import com.eventmanage.seating.dbhelpers.AttendeeSeatsDB;
import com.eventmanage.seating.dbhelpers.SeatingDB;


public class ManageAttendeeSeatsAction {
	
	//------------------------------------------Properties-----------------------
	String eid="";
	String tid="";
	String eventdate="";
	boolean isrecurring;
	String msg="";
	private String profilekey="";
	private String sectionid="";
	private String seatcode="";
	private String seatindex="";
	private String seataction="";
	private String venueid="";
	private String selectedseat="";
	private String seatstatus="";
	ArrayList<Entity> venueSections=new ArrayList<Entity>();
	ArrayList<HashMap<String,String>> attendeeList= new ArrayList<HashMap<String,String>>();
	//------------------------------------------Setters and Getters-------------
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getEventdate() {
		return eventdate;
	}
	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}
	public boolean isIsrecurring() {
		return isrecurring;
	}
	public void setIsrecurring(boolean isrecurring) {
		this.isrecurring = isrecurring;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ArrayList<Entity> getVenueSections() {
		return venueSections;
	}
	public void setVenueSections(ArrayList<Entity> venueSections) {
		this.venueSections = venueSections;
	}
	public ArrayList<HashMap<String, String>> getAttendeeList() {
		return attendeeList;
	}
	public void setAttendeeList(ArrayList<HashMap<String, String>> attendeeList) {
		this.attendeeList = attendeeList;
	}
	public String getProfilekey() {
		return profilekey;
	}
	public void setProfilekey(String profilekey) {
		this.profilekey = profilekey;
	}
	public String getSectionid() {
		return sectionid;
	}
	public void setSectionid(String sectionid) {
		this.sectionid = sectionid;
	}
	public String getSeatcode() {
		return seatcode;
	}
	public void setSeatcode(String seatcode) {
		this.seatcode = seatcode;
	}
	public String getSeatindex() {
		return seatindex;
	}
	public void setSeatindex(String seatindex) {
		this.seatindex = seatindex;
	}

	//------------------------------------------Action Methods-----------------
	public String execute(){
		populateData();
		return "success";
	}
	public void populateData(){
		String venue=SeatingDB.getVenue(eid);
		venueSections=SeatingDB.GetVenueSections(venue);
	}
	
	public String saveAttendeeSeat(){
		HashMap<String,String>hmap=new HashMap<String,String>();
		hmap.put("seatcode",seatcode);
		hmap.put("seatindex",seatindex);
		hmap.put("sectionid",sectionid);
		hmap.put("profilekey",profilekey);
		hmap.put("tid",tid.trim());
		hmap.put("eid",eid.trim());
		hmap.put("venueid",venueid);
		hmap.put("seataction",seataction);
		AttendeeSeatsDB.saveAttendeeSeat(hmap);
		msg="success";
		return "ajaxjson";
	}
	public String getSeatingChart(){
		String venue=SeatingDB.getVenue(eid);
		venueSections=SeatingDB.GetVenueSections(venue);
		HashMap<String, String> profileMap=AttendeeSeatsDB.getProfile(eid,profilekey,venue);
		sectionid=profileMap.get("sectionid");
		seatcode=profileMap.get("seatcode");
		seatindex=profileMap.get("seatindex");
		venueid=profileMap.get("venueid");
		return "seatingchart";
	}
	
	public String checkSeatStatus(){
		System.out.println("Eneter in to checkSeatStatus:"+eid+":"+selectedseat);
		String checkseatstatus=AttendeeSeatsDB.getSeatStatus(selectedseat,eid);
		JSONObject seatstatusjson=new JSONObject();
		try{
			seatstatusjson.put("seatstatus",checkseatstatus);
		}catch(Exception e){
			System.out.println("exception in getCCStatus in specialfee action:"+eid); 
		}
		seatstatus=seatstatusjson.toString();
		return "seatjson";
	}
	public String getSeataction() {
		return seataction;
	}
	public void setSeataction(String seataction) {
		this.seataction = seataction;
	}
	public String getVenueid() {
		return venueid;
	}
	public void setVenueid(String venueid) {
		this.venueid = venueid;
	}
	public String getSelectedseat() {
		return selectedseat;
	}
	public void setSelectedseat(String selectedseat) {
		this.selectedseat = selectedseat;
	}
	public String getSeatstatus() {
		return seatstatus;
	}
	public void setSeatstatus(String seatstatus) {
		this.seatstatus = seatstatus;
	}
}
