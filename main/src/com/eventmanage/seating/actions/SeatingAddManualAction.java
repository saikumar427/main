package com.eventmanage.seating.actions;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.eventmanage.seating.dbhelpers.SeatingAddManualDB;
import com.eventmanage.seating.dbhelpers.SeatingDB;
import com.eventmanage.seating.helpers.SeatingAddManualHelper;


public class SeatingAddManualAction {
	private String eid="";
	private String tid="";
	private String msg="";
	private String eventdate="";
	private String selectedseats="";
	private String allsectionids="";
	public String getAllsectionids() {
		return allsectionids;
	}
	public void setAllsectionids(String allsectionids) {
		this.allsectionids = allsectionids;
	}
	public String getSelectedseats() {
		return selectedseats;
	}
	public void setSelectedseats(String selectedseats) {
		this.selectedseats = selectedseats;
	}
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String execute(){
		return "success";
	}
	public String getAllTickets(){
		try{
		msg=new JSONObject().put("seatticketid",SeatingAddManualDB.getAllticketid(eid)).toString();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return "ajaxjson";
	}
	public String getSeatingSection(){

		SeatingAddManualDB.deleteTempBlockSeats(eid);
		String venueid=SeatingDB.getVenue(eid);
		HashMap layoutdetails=SeatingAddManualDB.GetVenueLayout(venueid);
		HashMap allticket_id_name=SeatingAddManualDB.getAllTicketNameIds(eid);
		HashMap hm=SeatingAddManualDB.getAllcotedTicketidForSeatgroupid(eid,venueid);
		HashMap seatingsectionhm=SeatingAddManualDB.getSection(venueid);
		HashMap allotedseatshm=SeatingAddManualDB.getAllotedSeats(eid,"");
		HashMap hmap=new HashMap();
		hmap.put("allticket_id_name",allticket_id_name);
		hmap.put("tktid_seat_grpid",hm);
		hmap.put("venueid",venueid);
		hmap.put("allotedseats",allotedseatshm);
		hmap.put("getAllotedSeatColors",SeatingAddManualDB.getAllotedSeatColors(eid,""));
		hmap.put("getSoldOutSeats",SeatingAddManualDB.getSoldOutSeats(eid,"",eventdate));
		if("".equals(tid))
			hmap.put("getOnHoldSeats",SeatingAddManualDB.getOnHoldSeats(eid,eventdate));
		else
			hmap.put("getOnHoldSeats",SeatingAddManualDB.getOnHoldSeatsTid(eid,eventdate,tid));
		hmap.put("eid",eid);
		hmap.put("eventdate",eventdate);
		HashMap sectiondetails=new HashMap();
		String sectionid="";
		ArrayList sectionids=(ArrayList)seatingsectionhm.get("sectionid");
		ArrayList sectionnames=(ArrayList)seatingsectionhm.get("sectionname");
			for(int i=0;i<sectionids.size();i++){	
				sectionid=sectionids.get(i).toString();
				hmap.put("sectionid",sectionid);
				hmap.put("sectionheader",seatingsectionhm.get("header_"+sectionid));
				hmap.put("getsectiondetails",seatingsectionhm.get(sectionid));
				sectiondetails.put(sectionid,SeatingAddManualHelper.getSectionSeatingDetails(hmap));

			}
		HashMap ticketseat_colors=SeatingAddManualDB.getTicketSeatColors(eid);
		HashMap ticketgroupdetails=SeatingAddManualDB.getTicketGroupdetails(eid);
		JSONObject seatingdetailobj=new JSONObject();
		try{
		if(!layoutdetails.isEmpty()){
			seatingdetailobj.put("venuelayout",(String)layoutdetails.get("layout"));
			seatingdetailobj.put("venuepath",(String)layoutdetails.get("path"));
			seatingdetailobj.put("venuelinklabel",(String)layoutdetails.get("link"));
		}

		seatingdetailobj.put("ticketseatcolor",ticketseat_colors);
		seatingdetailobj.put("allsections",sectiondetails);
		seatingdetailobj.put("allsectionid",sectionids);
		seatingdetailobj.put("allsectionname",sectionnames);
		seatingdetailobj.put("ticketgroups",ticketgroupdetails);
		String timeout=SeatingAddManualDB.getTimeOut(eid);
		seatingdetailobj.put("timeout",timeout);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		msg=seatingdetailobj.toString();
		return "ajaxjson";
	}
	public String checkSeatStatus(){
		String allseats="";
		String status="";
		String[] sectionids=allsectionids.split("_");
		JSONObject obj=new JSONObject();
		try{
		obj=(JSONObject)new JSONTokener(selectedseats).nextValue();
		for(int i=0;i<sectionids.length;i++){
			JSONArray seatindexarray=new JSONArray();
			try{
				seatindexarray=obj.getJSONArray(sectionids[i]);
				allseats=allseats+seatindexarray.join(",");
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			}	
		allseats=allseats.replace('"','\'');
		if(!"".equals(allseats)){
			status=SeatingAddManualDB.getStatus(eid, tid, eventdate, allseats);
		}
		else
			status="success";
		JSONObject jobj=new JSONObject();
		jobj.put("status", status);
		jobj.put("tid", tid);
		msg=jobj.toString();
		}
		catch(Exception e){
			System.out.println(e);
			
		}
		return "ajaxjson";
	}
}
