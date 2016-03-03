package com.myevents.helpers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.event.beans.EventData;
import com.event.helpers.I18n;

import org.apache.log4j.Logger;
import org.json.*;
public class EventsJsonBuilder {
	private static final Logger log = Logger.getLogger(EventsJsonBuilder.class);
	private static JSONArray getEventsJson(ArrayList eventsList){
		JSONArray events=new JSONArray();
		try{
		for(int i=0;i<eventsList.size();i++){
			JSONObject evtObject=new JSONObject();
			EventData edata=(EventData)eventsList.get(i);
			evtObject.put("title", edata.getEventName());
			evtObject.put("id", edata.getEventId());
			evtObject.put("mgrid", edata.getMgrId());
			//edata.getFormattedStartDate().getYear()
			evtObject.put("startdate", edata.getStart_date());
			evtObject.put("enddate", edata.getEnd_date());
			evtObject.put("start_time", edata.getStarttime());
			evtObject.put("end_time", edata.getEndtime());
			evtObject.put("listeddate", edata.getListed_date());
			evtObject.put("eventtype",edata.getPowertype());
			evtObject.put("action", "Manage");
			events.put(evtObject);
		}
		}catch(Exception e){}
		return events;
	}
	public static JSONObject getEventsPageURL(String URL){
		JSONArray eventsPageurl=new JSONArray();
		//JSONArray BoxOfficePageurl=new JSONArray();
		JSONObject allEvtsPageObject=new JSONObject();
		
		JSONObject URLObj=new JSONObject();
		//JSONObject BOURLObj=new JSONObject();
		try{
			URLObj.put("eventsPageURL", URL);
			URLObj.put("action", "Manage");
			//String BOURL=URL.replace("/events", "");
			//BOURLObj.put("boxofficePageURL", BOURL);
			//BOURLObj.put("action", "Manage");
			eventsPageurl.put(URLObj);
			//BoxOfficePageurl.put(BOURLObj);
			allEvtsPageObject.put("eventsPageURL", eventsPageurl);
			//allEvtsPageObject.put("boxofficePageURL", BoxOfficePageurl);
		}catch(Exception e){}
			return allEvtsPageObject;
	}
	public static JSONObject getEventsJson(ArrayList activeEventsList, ArrayList closedEventsList, ArrayList suspendedEventsList){
		JSONObject allEvtsObject=new JSONObject();
		try{
		allEvtsObject.put("active", getEventsJson(activeEventsList));
		allEvtsObject.put("closed", getEventsJson(closedEventsList));
		allEvtsObject.put("suspended", getEventsJson(suspendedEventsList));
		}catch(Exception e){}
		return allEvtsObject;
	}
	
	
	public static JSONObject getEventGroupsJson(List<HashMap<String,String>> gList){
		JSONArray gevents=new JSONArray();
		JSONObject allEGrpEvtsObject=new JSONObject();
		try{
		for(int i=0;i<gList.size();i++){
			JSONObject evtObject=new JSONObject();
			HashMap<String,String> gdata=gList.get(i);
			evtObject.put("title", gdata.get("group_title"));
			evtObject.put("id", gdata.get("event_groupid"));
			evtObject.put("count", gdata.get("eventscount"));
			evtObject.put("action", "Manage");
			gevents.put(evtObject);
		}
		allEGrpEvtsObject.put("groups", gevents);
		}catch(Exception e){}
	
		return allEGrpEvtsObject;
	}
	public static JSONObject getEventThemesJson(List<HashMap<String,String>> gList){
		JSONArray eventThemes=new JSONArray();
		JSONObject allEGrpEvtsObject=new JSONObject();
		try{
		for(int i=0;i<gList.size();i++){
			JSONObject evtObject=new JSONObject();
			HashMap<String,String> themesdata=gList.get(i);
			evtObject.put("themename", themesdata.get("themename"));
			evtObject.put("themeid", themesdata.get("themeid"));			
			evtObject.put("htmlcss", "HTML & CSS");
			evtObject.put("look&feel", "Look & Feel");
			eventThemes.put(evtObject);
		}
		allEGrpEvtsObject.put("themes", eventThemes);
		}catch(Exception e){}
	
		return allEGrpEvtsObject;
	}
	public static JSONObject getGroupEventsJson(Vector<HashMap<String,String>> grpEventsVec){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArrObj = new JSONArray();
		try {
			for (HashMap<String, String> hashMap : grpEventsVec) {
				JSONObject jsonObj_evtInfo = new JSONObject();
				jsonObj_evtInfo.put("name", hashMap.get("eventname"));
				jsonObj_evtInfo.put("id", hashMap.get("eventid"));
				jsonObj_evtInfo.put("mgrid", hashMap.get("mgrid"));
				jsonObj_evtInfo.put("action", "Manage");
				jsonArrObj.put(jsonObj_evtInfo);
			}
			jsonObj.put("events", jsonArrObj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jsonObj;
	}
	public static JSONArray getEventTicketsStausInfoJson(List<HashMap<String, String>> list){
		JSONArray jsnArObjMain = new JSONArray();
		try {			
			for (HashMap<String, String> hashMap : list) {
				JSONArray jsnArObj = new JSONArray();
				JSONObject jsnObj =new JSONObject();
				jsnObj.put("v",Integer.parseInt(hashMap.get("sold_qty")));
				jsnObj.put("f","Sold Count - "+ hashMap.get("sold_qty"));
				String tName = hashMap.get("ticketname");
				if(hashMap.get("groupname")!=null && !"".equals(hashMap.get("groupname"))){
					tName = hashMap.get("ticketname") + "("+ hashMap.get("groupname") + ")";
				}
				jsnArObj.put(tName);
				jsnArObj.put(jsnObj);
				jsnArObjMain.put(jsnArObj);				
			}
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return jsnArObjMain;
	}
	
	public static JSONArray getRsvpEventStatusInfo(List<EventData> list){
		JSONArray jsnArObjMain = new JSONArray();
		try {
			for (EventData eventData : list) {
				JSONArray jsnArObj = new JSONArray();
				JSONObject jsnObj =new JSONObject();
				jsnObj.put("v",Integer.parseInt(eventData.getRsvpcount()));
				jsnObj.put("f","Response Count - "+ eventData.getRsvpcount());
				String status_name = eventData.getAttendingevent();				
				jsnArObj.put(status_name);
				jsnArObj.put(jsnObj);
				jsnArObjMain.put(jsnArObj);				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jsnArObjMain;
	}
	
	public static JSONObject getRSVPResponseJson(HashMap<String,String> hmap){
		JSONObject json=new JSONObject();
		try{
			
			if(hmap.containsKey("Attending"))
				json.put(I18n.getString("sp.attending.lbl"),hmap.get("Attending"));
			if(hmap.containsKey("NotSure"))
				json.put(I18n.getString("sp.notsure.lbl"), hmap.get("NotSure"));
			if(hmap.containsKey("NotAttending"))
			    json.put(I18n.getString("sp.notattending.lbl"),hmap.get("NotAttending"));
			
		}catch(Exception e){
			System.out.println("Exception in EventsJsonBuilder getRSVPResponseJson() ERROR:: "+e.getMessage());
		}
		return json;
	}

}
