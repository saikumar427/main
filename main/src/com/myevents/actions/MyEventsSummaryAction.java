package com.myevents.actions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.event.beans.EventData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.MyEventsDB;
import com.event.dbhelpers.UpgradeEventDB;
import com.event.helpers.DisplayAttribsHelper;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.payments.dbhelpers.CardProcessorDB;
import com.eventbee.util.RandomStringUUID;
import com.myevents.dbhelpers.EventsChartDataDB;
import com.myevents.helpers.EventsJsonBuilder;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.user.dbhelpers.UserDisplayAttribsDB;
public class MyEventsSummaryAction extends MyEventsActionWrapper  implements Preparable,ValidationAware{
	private static final Logger log = Logger.getLogger(MyEventsSummaryAction.class);
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
	public String getMgrid() {
		return mgrid;
	}
	public void setMgrid(String mgrid) {
		this.mgrid = mgrid;
	}
	public String getCcstatus() {
		return ccstatus;
	}
	public void setCcstatus(String ccstatus) {
		this.ccstatus = ccstatus;
	}
	private int eventscount=0;
	private String messageJson="";
	private String mgrid="";
	private String ccstatus="";
	public String getMessageJson() {
		return messageJson;
	}
	public void setMessageJson(String messageJson) {
		this.messageJson = messageJson;
	}
	public int getEventscount() {
		return eventscount;
	}
	public void setEventscount(int eventscount) {
		this.eventscount = eventscount;
	}
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
	public String getChartJson(String eventid){
		String jsondata ="[]";
		List<HashMap<String, String>> ticketing_list=null;
		List<EventData> rsvp_list=null;
		try {
			String eventtype = EventDB.getPowerType(eventid);			
			
			if("RSVP".equals(eventtype)){
				 rsvp_list = EventsChartDataDB.getRSVPEventStatusInfo(eventid);	
				if(rsvp_list.size()>0){
					jsondata = EventsJsonBuilder.getRsvpEventStatusInfo(rsvp_list).toString();
				}				
			}else if("Ticketing".equals(eventtype)){
				ticketing_list = EventsChartDataDB.getEventTicketsStatusInfo(eventid);
				if(ticketing_list.size()>0){
					jsondata = EventsJsonBuilder.getEventTicketsStausInfoJson(ticketing_list).toString();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jsondata;
	}
	
	
	
	public String execute(){
		
		if("".equals(mgrId) || mgrId==null){
			System.out.println("--- mgrId EMPTY or NULL case --- in MyEventsSummaryAction execute mgrId: "+mgrId);
			return "error";
		}
		String accounttype=UpgradeEventDB.getAccountType(mgrId);
		if("blocked".equalsIgnoreCase(accounttype))return "error";
		recentClosedEvents=MyEventsDB.populateRecentClosedEvents(mgrId, 1);		
		eventsSummary=MyEventsDB.populateMyEventsSummary(mgrId);		
		
		/*
		 * Last listed event
		 */
		if("Y".equals(eventsSummary.get("LAST_EVENT_EXISTS"))){
			String eventid=eventsSummary.get("LAST_EVENT_ID");			
			lastListedEvtJsonData = getChartJson(eventid);
			if(lastListedEvtJsonData.length() > 2){
				eventsSummary.put("LAST_EVENT_CHART_EXISTS", "Y");
			}else{
				eventsSummary.put("LAST_EVENT_CHART_EXISTS", "N");
			}
			
		}
		/*
		 * Recent closed event
		 */
		if(recentClosedEvents.size()>0){			
			eventsSummary.put("RECENT_CLOSED_EVENT_EXISTS", "Y");
			EventData edata=(EventData)recentClosedEvents.get(0);
			String eventid = edata.getEventId();
			recentClosedEvtJsonData = getChartJson(eventid);
			
			if(recentClosedEvtJsonData.length() > 2){
				eventsSummary.put("RECENT_CLOSED_EVENT_CHART_EXISTS", "Y");
			}else{
				eventsSummary.put("RECENT_CLOSED_EVENT_CHART_EXISTS", "N");
			}
		}else{
			eventsSummary.put("RECENT_CLOSED_EVENT_EXISTS", "N");
		}
		
		userName=DbUtil.getVal("select getMemberPref(?||'','pref:myurl','') as username",new String[]{mgrId});
		serveraddress=EbeeConstantsF.get("serveraddress","www.eventbee.com");
		if(serveraddress!=null){
			if(!serveraddress.startsWith("http://")){
				serveraddress="http://"+serveraddress;
			}
			}
		HashMap<String,String> streamerData=UserDisplayAttribsDB.getAttribValues(mgrId, "maineventstreamer");
		streamerCodeString=DisplayAttribsHelper.getStreamerCodeString(mgrId,streamerData);
		int active=Integer.parseInt(eventsSummary.get("ACTIVE_EVENTS_COUNT"));
		int closed=Integer.parseInt(eventsSummary.get("CLOSED_EVENTS_COUNT"));
		int suspended=Integer.parseInt(eventsSummary.get("SUSPENDED_EVENTS_COUNT"));
		eventscount=active+closed+suspended;
		return "success";		
	}
	public void prepare() throws Exception {
		try {
			super.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public String generateToken(){
		System.out.println("in generate token method");
		HttpServletRequest request = ServletActionContext.getRequest();
		messageJson=request.getSession().getId()+RandomStringUUID.getUUID();
		MyEventsDB.insertTokenInDB(messageJson,mgrId);
		return "dummy";
	}
	
	public String generateClassicToken(){
		System.out.println("in generateClassicToken Method");
		String tokenId=EventDB.randomAlphaNumeric(15);
    	EventDB.getTogglingToken(mgrId,tokenId,"","myevents");
    	messageJson=tokenId;
		
		return "classictoken";
	}
	
	
	
	public String getMgrCCStatus(){
		System.out.println("in MyEventsSummaryAction getMgrCCStatus mgrid: "+mgrid);
		Map session = ActionContext.getContext().getSession();
		String cardstatus="",cardRequired="",cardType="popup1";
		
		if(session.get("SESSION_USER")!=null && !"".equals(mgrid) && mgrid!=null && !"null".equals(mgrid))
			cardRequired=DbUtil.getVal("select name from mgr_config where name in('mgr.card.popup1.required','mgr.card.popup2.required','authorize','require_charge') and value='Y' and userid=CAST(? AS INTEGER)", new String[]{mgrid});
			
			
		if("require_charge".equals(cardRequired)){
				cardType="charge";
				cardstatus=cardType;
			
		}else if(cardRequired != null && !"".equals(cardRequired)){
			cardstatus=CardProcessorDB.getManagerCCStatus(mgrid);
			System.out.println("card status:"+cardstatus);
			if(cardstatus==null || "".equals(cardstatus))
				cardstatus="askcard";
			else
				cardstatus="donot_askcard";
		}else{
				cardstatus="donot_askcard";
		}
		JSONObject ccjson=new JSONObject();
		try{
			if("askcard".equals(cardstatus)){
				
				if("mgr.card.popup2.required".equals(cardRequired))
					cardType="popup2";
				
				if("authorize".equals(cardRequired))
					cardType="authorize";
				
				
				cardstatus=cardType;
				
			}
			
			ccjson.put("cardstatus",cardstatus);
			ccjson.put("userid",mgrid);
		}catch(Exception e){
			System.out.println("exception in getCCStatus in MyEventsSummaryAction:"+mgrid); 
		}
		ccstatus=ccjson.toString();
		System.out.println("ccstatus is:"+ccstatus);
		return "ccjson";
	}
	
	public String getDuePopup(){
		System.out.println("getDuePopup in MyEventsSummaryAction");
		return "duepopup";
	}
	
	public String getChargePopup(){
		System.out.println("getChargePopup in MyEventsSummaryAction");
		return "chargepopup";
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
	
	
	
	
	
}
