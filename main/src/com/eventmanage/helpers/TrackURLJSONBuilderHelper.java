package com.eventmanage.helpers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.event.beans.TicketURLsData;
import com.event.beans.TrackURLData;

public class TrackURLJSONBuilderHelper {
	public static JSONObject getTrackURLsListJson(ArrayList<TrackURLData> trackURLDetailsList,TrackURLData trackURLData){
		JSONObject allticketURLsObject=new JSONObject();
		JSONArray trackURLobj=new JSONArray();
		try{
			for(int i=0;i<trackURLDetailsList.size();i++){
				JSONObject cObject=new JSONObject();
				TrackURLData cListObj= trackURLDetailsList.get(i);
				cObject.put("trackingcode",cListObj.getTrackingcode());
				cObject.put("id",cListObj.getSecretcode());
				cObject.put("url",trackURLData.getEventURL()+cListObj.getTrackingcode());
				cObject.put("count",cListObj.getCount());
				cObject.put("tickets",fillTickets(cListObj.getTicketsData()));	
				cObject.put("action", "Manage");
				cObject.put("status", cListObj.getStatus());
				trackURLobj.put(cObject);
			}
			allticketURLsObject.put("citems",trackURLobj);
		}
		catch(Exception e){
			System.out.println("There is an error in processing the JSON Data for track urls");
		}
	return allticketURLsObject;
	}
	private static JSONArray fillTickets(ArrayList ticketsData){
		JSONArray trackURLTickets=new JSONArray();
		try{
		for(int i=0;i<ticketsData.size();i++){
			TrackURLData tempObj=(TrackURLData)ticketsData.get(i);
			JSONObject cObject=new JSONObject();
			cObject.put("tname",tempObj.getTicketname().toString());
			cObject.put("tqty",tempObj.getTicketQty().toString());
			trackURLTickets.put(cObject);				
		}
		}catch(Exception ex){}
		return trackURLTickets;
	}
	
	
}
