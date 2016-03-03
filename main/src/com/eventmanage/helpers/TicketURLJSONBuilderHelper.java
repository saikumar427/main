package com.eventmanage.helpers;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import com.event.beans.TicketURLsData;
import com.event.dbhelpers.CustomAttributesDB;
import com.event.dbhelpers.TicketURLsDB;
import com.eventbee.beans.Entity;


public class TicketURLJSONBuilderHelper {

	public static JSONObject getTicketURLsListJson(String eid,ArrayList<TicketURLsData> ticketURLList,TicketURLsData ticketURLsData,List alltickets){
		JSONObject allticketURLsObject=new JSONObject();
		JSONArray ticketURLobj=new JSONArray();
		try{
			for(int i=0;i<ticketURLList.size();i++){
				JSONObject cObject=new JSONObject();
				TicketURLsData cListObj= ticketURLList.get(i);
				cObject.put("code",cListObj.getCode());
				cObject.put("id",cListObj.getCode());
				cObject.put("url",ticketURLsData.getEventURL()+cListObj.getCode());
				cObject.put("count",cListObj.getCount());
				cObject.put("ticketname",cListObj.getTicketname());
				String code=cListObj.getCode();
				cObject.put("ticketnames", getTicketNamesforCode(code,eid,alltickets));
				cObject.put("action", "Manage");
				ticketURLobj.put(cObject);
			}
			allticketURLsObject.put("citems",ticketURLobj);
		}
		catch(Exception e){
			System.out.println("There is an error in processing the JSON Data for ticket urls");
		}
	return allticketURLsObject;
	}
private static JSONArray getTicketNamesforCode(String code, String eid, List alltickets) throws Exception{
		
		JSONArray tickets_jsonarray=new JSONArray();
		CustomAttributesDB cadb = new CustomAttributesDB();
		//List seltickets = cadb.getCustomQuestionTickets(eid,qid);
		List<String> seltickets=TicketURLsDB.getTicketidsforCode(eid,code);
		for(int i=0;i<seltickets.size();i++){
			String tid=seltickets.get(i);
			String tname= getTicketName(tid, alltickets);
			if(tname!=null){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", tname);
			jsonObj.put("tid", tid);
			tickets_jsonarray.put(jsonObj);
			}
		}		
		return tickets_jsonarray;
	}
	private static String getTicketName(String tid, List alltickets){
	for(int i=0;i<alltickets.size();i++){
		Entity tdata=(Entity)alltickets.get(i);
		String tktidFromMap=tdata.getKey();
		if(tktidFromMap.equals(tid))
			return tdata.getValue();
	}
	return null;
}
}
