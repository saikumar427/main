package com.eventmanage.actions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.event.beans.EventData;

import com.event.dbhelpers.BadgesDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.ReportsDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.TicketSalesDB;
import com.event.dbhelpers.TicketsDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.eventmanage.helpers.JsonBuilderHelper;
import com.myevents.dbhelpers.EventsChartDataDB;
import com.myevents.helpers.EventsJsonBuilder;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
public class SnapshotAction extends ActionSupport implements Preparable,ValidationAware{
private static Logger log=Logger.getLogger(SnapshotAction.class);
private static final long serialVersionUID = 3638845334032069827L;
private String eid="";
private EventData eventData=null;
private String powertype="";
private Vector ticketstatusvec=new Vector();
private HashMap totalSales=new HashMap();
private ArrayList<EventData> RSVPCount=new ArrayList<EventData>();
private String mgrId="";
private String userurl="";
private String msg="";
private String newurl="";
private HashMap<String,String> rsvpMap=new HashMap<String,String>();
private String purpose="";
private ArrayList<HashMap<String,String>> promotedMembers=new ArrayList<HashMap<String,String>>();
private String promotionsJson="";
private String jsondata = "[]";
private String chartJsondata = "[]";
private String eventDate = "";
private boolean ntsgraphEnable=false;
private boolean isrecurring=false;
private String rsvpjsondata="[]";
private ArrayList<Entity> recurDates;
private String count="";
private String recTicketDetails="{}";

private String visitcount="";


public String getVisitcount() {
	return visitcount;
}

public void setVisitcount(String visitcount) {
	this.visitcount = visitcount;
}

public boolean isNtsgraphEnable() {
	return ntsgraphEnable;
}

public void setNtsgraphEnable(boolean ntsgraphEnable) {
	this.ntsgraphEnable = ntsgraphEnable;
}

public String getCount() {
	return count;
}

public void setCount(String count) {
	this.count = count;
}

	public String getEventDate() {
	return eventDate;
}

public void setEventDate(String eventDate) {
	this.eventDate = eventDate;
}

	public String getJsondata() {
		return jsondata;
	}

	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}

public String getPurpose() {
	return purpose;
}

public void setPurpose(String purpose) {
	this.purpose = purpose;
}

public ArrayList<EventData> getRSVPCount() {
	return RSVPCount;
}

public void setRSVPCount(ArrayList<EventData> count) {
	RSVPCount = count;
}

public HashMap<String, String> getRsvpMap() {
	return rsvpMap;
}

public void setRsvpMap(HashMap<String, String> rsvpMap) {
	this.rsvpMap = rsvpMap;
}

public String getNewurl() {
	return newurl;
}

public void setNewurl(String newurl) {
	this.newurl = newurl;
}

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}

public String getUserurl() {
	return userurl;
}

public void setUserurl(String userurl) {
	this.userurl = userurl;
}

public String getMgrId() {
	return mgrId;
}

public void setMgrId(String mgrId) {
	this.mgrId = mgrId;
}

public HashMap getTotalSales() {
	return totalSales;
}

public void setTotalSales(HashMap totalSales) {
	this.totalSales = totalSales;
}

public Vector getTicketstatusvec() {
	return ticketstatusvec;
}

public void setTicketstatusvec(Vector ticketstatusvec) {
	this.ticketstatusvec = ticketstatusvec;
}

public String getPowertype() {
	return powertype;
}

public void setPowertype(String powertype) {
	this.powertype = powertype;
}



public String execute(){
	/*List<HashMap<String, String>> ticketing_list=null;
	List<EventData> rsvp_list=null;*/
	eventData= EventDB.getEventData(eid);
	String eventPageViews=EventDB.getEventPageViews(eid);
	eventData.setEventPageViews(eventPageViews);
	isrecurring = EventDB.isRecurringEvent(eid);
	
	if("RSVP".equalsIgnoreCase(eventData.getPowertype())){
		if("all".equals(eventDate))eventDate="";
		//RSVPCount=EventDB.getRSVPCounts(eid,eventDate);
		HashMap<String,String> hmap=EventDB.getRSVPChartCounts(eid,eventDate);
		JSONObject json = EventsJsonBuilder.getRSVPResponseJson(hmap);
		rsvpjsondata=json.toString();
		
		// commented while doing bootstrap changes on May 11th, 2015
		
		/*count=EventDB.getRSVPResponsesCount(eid); 
		if("".equals(eventDate) || "all".equals(eventDate))
			rsvp_list = EventsChartDataDB.getRSVPEventStatusInfo(eid);
		else
			rsvp_list=EventsChartDataDB.getRSVPEventStatusInfo(eid, eventDate);
		if(rsvp_list.size()>0){
			chartJsondata = EventsJsonBuilder.getRsvpEventStatusInfo(rsvp_list).toString();
		}*/	
		
	}else{
		System.out.println(" snapshot action start eid: "+ eid+" eventDate: "+eventDate);
		/*if(eventDate==null || "all".equals(eventDate) || "".equals(eventDate))
			eventDate = EventDB.getRecurringDate(eid);*/



		/*if(isrecurring){
			ticketSourceMap.put("recurringstatus","recurr");
		}*/

		if(isrecurring){
			ArrayList<String> eventDates=new ArrayList<String>();
			try{
			jsondata=new JSONObject(TicketSalesDB.getTicketSourceMapForAllDates(eid, eventDate,totalSales,eventDates)).toString();
			recTicketDetails=new JSONObject(TicketSalesDB.getTicketDetails(eid)).put("dates", eventDates).toString();
			}catch(Exception e){
				System.out.println("Snapshot.java TicketSalesDB.getTicketSourceMapForAllDates ERROR: "+e.getMessage());
			}
		}else{
			HashMap ticketSourceMap=new HashMap();
			ticketSourceMap=TicketSalesDB.getTicketSourceMap(eid,eventDate);
			
			//HashMap deletedticketSourceMap=TicketSalesDB.getDeletedTicketSourceMap(eid,eventDate);
			ticketstatusvec=TicketSalesDB.getTicketStatusInfo(eid, ticketstatusvec, ticketSourceMap, eventDate);
			//ticketstatusvec=TicketSalesDB.getDeletedTicketsInfo(eid, ticketstatusvec, deletedticketSourceMap, eventDate);
			/*if("".equals(eventDate))
				ticketstatusvec=TicketSalesDB.getVolumeTicketStatusInfo(eid, ticketstatusvec);*/
			jsondata = JsonBuilderHelper.getTicketsStatusJson(ticketstatusvec).toString();
			totalSales=TicketSalesDB.getTotalsalesByDates(ticketstatusvec);//added on 29th may 2013
			//totalSales=TicketSalesDB.getTotalsales(eid, eventDate);//commented on 29th may 2013
			
			//commented while doing bootstrap changes on May 11th, 2015
			/*if("".equals(eventDate)){
				ticketing_list = EventsChartDataDB.getEventTicketsStatusInfo(eid);
			}else{
				ticketing_list=EventsChartDataDB.getEventTicketsInfo(ticketstatusvec);
				//totalSales=TicketSalesDB.getTotalsalesByDates(ticketstatusvec);//commented on 29th may 2013
			}
			if(ticketing_list.size()>0){
				chartJsondata = EventsJsonBuilder.getEventTicketsStausInfoJson(ticketing_list).toString();
			}*/
		}



		promotedMembers=EventDB.getPormotedMembers(eid);
		JSONObject jobj=new JSONObject();
		try{jobj.put("promotions", promotedMembers);}catch (Exception e) {}
		promotionsJson=jobj.toString();
		ntsgraphEnable=EventDB.isNtsGraphEnable(eid);
	}
	
	if(isrecurring){
		//recurDates = EditEventDB.getSummaryRecurringEventDates(eid);
		recurDates = ReportsDB.getRecurringDates(eid,eventData.getPowertype());
	}
	
	eventData.setEventURL(EventDB.getEventURL(eid,eventData.getUsername()));
	eventData.setUserurl(EventDB.getUserURL(eid,eventData.getUsername()));
	eventData.setTotal(TicketSalesDB.getTransactionsCount(eid));
	return "success";
}

public String getPromotedMembersList(){
	promotedMembers=EventDB.getPormotedMembers(eid);
	JSONObject jobj=new JSONObject();
	try{jobj.put("promotions", promotedMembers);}catch (Exception e) {}
	promotionsJson=jobj.toString();
	return "promotions";
}


	
	public String setCustomURL(){
		System.out.println("powertype: "+powertype);
		if(powertype.equals("RSVP"))
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP CustomURL");
		else
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"200","eventcustomurl");

		msg=EventDB.setCustomURL(eid,mgrId,userurl,newurl);
		
		if("inserted".equals(msg) || "".equals(msg)){
			String val=DbUtil.getVal("select getMemberPref(mgr_id||'','pref:myurl','') as username from eventinfo a where eventid=CAST(? AS BIGINT)",new String[]{eid});
			JSONObject jsonObj=new JSONObject();
			try{
			jsonObj.put("url",EventDB.getEventURL(eid,val));
			jsonObj.put("newurl",newurl);
			msg=jsonObj.toString();
			}catch(Exception e){
				System.out.println("the exception is::"+e.getMessage());
			}
			return "ajaxjson";
		}
		else return "ajaxmsg";
	}
	public String chanageEventStatus(){
		EventDB.updateEventStatus(eid,purpose);
		return "ajaxmsg";
	}
	
	public String RSVPtoTicketing(){
		log.info("RSVPtoTicketing eid: "+eid);
		EventDB.updateRSVPtoTicketing(eid);
		//String ticketingtype = CopyEventDB.getTicketingType(eid,"RSVP");
		mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
		SpecialFeeDB.chekingSpecialFee(eid, mgrId,"100","RSVP To Ticketing");
		return "ajaxmsg";
	}
	public EventData getEventData() {
		return eventData;
	}
public String getTicketingChartData(){
		System.out.println("getTicketingChartData eid: "+eid+" eventDate: "+eventDate+" isrecurring: "+isrecurring);

		//isrecurring=EventDB.isRecurringEvent(eid);
		/*if(eventDate==null || "all".equals(eventDate) || "".equals(eventDate))
			eventDate = EventDB.getRecurringDate(eid);*/

		HashMap<String, String> deletedTickets=new HashMap<String, String>();
		if(isrecurring && "all".equals(eventDate)){
			ArrayList<String> eventDates=new ArrayList<String>();
			try{
				jsondata=new JSONObject(TicketSalesDB.getTicketSourceMapForAllDates(eid, eventDate,totalSales,eventDates)).toString();
				recTicketDetails=new JSONObject(TicketSalesDB.getTicketDetails(eid)).put("dates", eventDates).toString();
			}catch(Exception e){
				System.out.println("Snapshot.java TicketSalesDB.getTicketSourceMapForAllDates ERROR: "+e.getMessage());
			}

		}else{
			
			HashMap ticketSourceMap=TicketSalesDB.getTicketSourceMap(eid,eventDate);
			if(isrecurring)ticketSourceMap.put("recurringstatus","recurr");
			//HashMap deletedticketSourceMap=TicketSalesDB.getDeletedTicketSourceMap(eid,eventDate);
			ticketstatusvec=TicketSalesDB.getTicketStatusInfo(eid, ticketstatusvec, ticketSourceMap, eventDate);
			//ticketstatusvec=TicketSalesDB.getDeletedTicketsInfo(eid, ticketstatusvec, deletedticketSourceMap, eventDate);
			/*if("".equals(eventDate))
				ticketstatusvec=TicketSalesDB.getVolumeTicketStatusInfo(eid, ticketstatusvec);*/
			deletedTickets=TicketSalesDB.getDeletedTicketsMap(eid,false);
			jsondata = JsonBuilderHelper.getTicketsStatusJson(ticketstatusvec).toString();
			totalSales=TicketSalesDB.getTotalsalesByDates(ticketstatusvec);//added on 29th may 2013
			//totalSales=TicketSalesDB.getTotalsales(eid, eventDate);//commented on 29th may 2013
			
			//commented while doing bootstrap changes on May 11th, 2015
			/*List<HashMap<String, String>> ticketing_list=null;
			if("".equals(eventDate)){
				ticketing_list = EventsChartDataDB.getEventTicketsStatusInfo(eid);
			}else{
				ticketing_list=EventsChartDataDB.getEventTicketsInfo(ticketstatusvec);
				//totalSales=TicketSalesDB.getTotalsalesByDates(ticketstatusvec);//commented on 29th may 2013
			}
					
			if(ticketing_list.size()>0)
				chartJsondata = EventsJsonBuilder.getEventTicketsStausInfoJson(ticketing_list).toString();*/
		}

		JSONObject json=new JSONObject();
		try{
			json.put("isrecur",isrecurring);
			json.put("ticketsummary",new JSONObject(jsondata));
			json.put("chartdata",new JSONArray(chartJsondata));
			json.put("recTicketDetails",new JSONObject(recTicketDetails));
			json.put("sales",totalSales);
			json.put("deletedTickets",new JSONObject(deletedTickets));
			msg=json.toString();
			System.out.println("for getTicketingChartData msg: "+msg);
		}catch(Exception e){
			System.out.println("Exception getTicketingChartData: ERROR:: "+e.getMessage());
		}
		return "ajaxjson";
	}
	
	public String getRSVPChartData(){
		//List<EventData> rsvp_list=null;
		if("all".equals(eventDate))eventDate="";
		HashMap<String,String> hmap=EventDB.getRSVPChartCounts(eid,eventDate);
		
		//commented while doing bootstrap changes on May 11th, 2015
		
		/*count=EventDB.getRSVPResponsesCount(eid); 
		if("".equals(eventDate) || "all".equals(eventDate))
			rsvp_list = EventsChartDataDB.getRSVPEventStatusInfo(eid);
		else
			rsvp_list=EventsChartDataDB.getRSVPEventStatusInfo(eid, eventDate);
		if(rsvp_list.size()>0){
			chartJsondata = EventsJsonBuilder.getRsvpEventStatusInfo(rsvp_list).toString();
		}*/ 
		JSONObject json = EventsJsonBuilder.getRSVPResponseJson(hmap);
		msg=json.toString();
		
	return "ajaxjson";	
	}
	
	
	public String eventDetails(){
		visitcount=EventDB.getEventPageViews(eid);
		
		eventData= EventDB.getEventData(eid);
		eventData.setEventURL(EventDB.getEventURL(eid,eventData.getUsername()));
		eventData.setUserurl(EventDB.getUserURL(eid,eventData.getUsername()));
		boolean isrecurr = EventDB.isRecurringEvent(eid);
		
		if("RSVP".equalsIgnoreCase(eventData.getPowertype())){
			HashMap<String,String> hmap=EventDB.getRSVPChartCounts(eid,eventDate);
			JSONObject json = EventsJsonBuilder.getRSVPResponseJson(hmap);
			rsvpjsondata=json.toString();
		}else{
			String userTimeZone = EventDB.getEventTimeZone(eid);
			ArrayList groupsList = TicketsDB.getEventTicketsList(eid, userTimeZone,eventDate);
			HashMap tktStatusMap=TicketsDB.getTicketsStatus(eid,eventDate);
			jsondata = JsonBuilderHelper.getTicketsJson(groupsList,tktStatusMap).toString();
		}
		JSONObject js=EventDB.getRecentTransactions(eid);
		ArrayList<String> timeList=new ArrayList<String>();
		
		if(isrecurr){
			if("RSVP".equalsIgnoreCase(eventData.getPowertype()))
			timeList=EventDB.getRSVPRecurringDates(eid);
			else
			timeList=EventDB.getRecurringDates(eid);
		}try{
		js.put("dateList",timeList);
		}catch(Exception e){
		}
		msg=js.toString();
	return "success";
	}
	
	
	public String populateTicketJson(){
		String userTimeZone = EventDB.getEventTimeZone(eid);
		ArrayList groupsList = TicketsDB.getEventTicketsList(eid, userTimeZone,eventDate);
		HashMap tktStatusMap=TicketsDB.getTicketsStatus(eid,eventDate);
		msg = JsonBuilderHelper.getTicketsJson(groupsList,tktStatusMap).toString();
		return "refresh";
	}
	
	
	
	
	
	
	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}

	public String getEid(){
		return eid;
	}
	public void setEid(String eid){
		this.eid=eid;
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	public String getChartJsondata() {
		return chartJsondata;
	}

	public void setChartJsondata(String chartJsondata) {
		this.chartJsondata = chartJsondata;
	}

	public ArrayList<Entity> getRecurDates() {
		return recurDates;
	}

	public void setRecurDates(ArrayList<Entity> recurDates) {
		this.recurDates = recurDates;
	}

	public ArrayList<HashMap<String, String>> getPromotedMembers() {
		return promotedMembers;
	}

	public void setPromotedMembers(
			ArrayList<HashMap<String, String>> promotedMembers) {
		this.promotedMembers = promotedMembers;
	}

	public String getPromotionsJson() {
		return promotionsJson;
	}       

	public void setPromotionsJson(String promotionsJson) {
		this.promotionsJson = promotionsJson;
	}

	public boolean isIsrecurring() {
		return isrecurring;
	}

	public void setIsrecurring(boolean isrecurring) {
		this.isrecurring = isrecurring;
	}

	public String getRsvpjsondata() {
		return rsvpjsondata;
	}

	public void setRsvpjsondata(String rsvpjsondata) {
		this.rsvpjsondata = rsvpjsondata;
	}
	

	public String getRecTicketDetails() {
		return recTicketDetails;
	}

	public void setRecTicketDetails(String recTicketDetails) {
		this.recTicketDetails = recTicketDetails;
	}
}
