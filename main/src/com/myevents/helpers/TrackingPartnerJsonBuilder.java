package com.myevents.helpers;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.myevents.beans.TrackingPartnerData;

public class TrackingPartnerJsonBuilder {
	private static final Logger log = Logger.getLogger(TrackingPartnerJsonBuilder.class);

	public static JSONObject getTrackingPartnerJson(List<TrackingPartnerData> trackingPartnerList,String mgrid){
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarrObj = new JSONArray();
	
		try {
			for (TrackingPartnerData data : trackingPartnerList) {
				JSONObject jsonData = new JSONObject();
				jsonData.put("Name",data.getTrackname());
				jsonData.put("EventsCount",data.getEventscount());
				jsonData.put("Status",data.getStatus());
				jsonData.put("TrackId",data.getTrackid());
				jsonarrObj.put(jsonData);
			}
			jsonobj.put("TrackingPartnerDetails", jsonarrObj);
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return jsonobj;
}
	public static JSONObject getTrackURLJson(List<TrackingPartnerData> trackURLList,String trackid){
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarrObj = new JSONArray();
		try {
			for (TrackingPartnerData data : trackURLList) {
				JSONObject jsonData = new JSONObject();
				jsonData.put("eventname",data.getEventName());
				jsonData.put("eventid",data.getEid());
				jsonData.put("status",data.getStatus());
				jsonarrObj.put(jsonData);
			}
			jsonobj.put("TrackURLDetails", jsonarrObj);
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return jsonobj;
	}
	

}
