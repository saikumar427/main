package com.event.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.event.beans.CustomAttribute;
import com.event.dbhelpers.CustomAttributesDB;
import com.eventbee.beans.Entity;

public class CustomAttribsJSONBuilderHelper {
	
	private static JSONObject fillTicketlevelBaseQSettings(String attribName, String attribid, HashMap basicProfileSettingsMap
			, String eid, List alltickets, String attribType){
		JSONObject question_jsonObj = new JSONObject();
		try{
		question_jsonObj.put("attribname", attribName);
		question_jsonObj.put("attrib_id", attribid);
		if((basicProfileSettingsMap==null)||(basicProfileSettingsMap.get(attribType)==null)){
			question_jsonObj.put("tickets", new JSONArray());
		}else{
			ArrayList profileQTickets=(ArrayList)basicProfileSettingsMap.get(attribType);
			JSONArray tickets_jsonarray=new JSONArray();
			for(int i=0;i<profileQTickets.size();i++){
				HashMap ticketData=(HashMap)profileQTickets.get(i);
				JSONObject jsonObj = new JSONObject();
				String tid= (String)ticketData.get("tid");
				jsonObj.put("name", getTicketName(tid, alltickets));
				jsonObj.put("tid", tid);
				tickets_jsonarray.put(jsonObj);
			}
			question_jsonObj.put("tickets", tickets_jsonarray);
		}
			
		
		}catch(Exception e){}
		return question_jsonObj;
	}
	private static JSONObject fillTransactionlevelBaseQSettings(String attribName, String attribid, String isrequired){
		JSONObject question_jsonObj = new JSONObject();
		try{
		question_jsonObj.put("attribname", attribName);
		question_jsonObj.put("attrib_id", attribid);
		question_jsonObj.put("attribtype", "Text");
		question_jsonObj.put("isrequired", isrequired);
		}catch(Exception e){}
		return question_jsonObj;
	}
	public static JSONObject getTransactionLevelJsonObj(List<CustomAttribute> caArray, HashMap basicProfileSettingsMap,String powerType){
		JSONObject jsonObj = new JSONObject();
		JSONArray questions_jsonarray=new JSONArray();
		try {
			String phoneQType=(String)basicProfileSettingsMap.get("phone");
			if(phoneQType==null) phoneQType="";
			if("Y".equals(phoneQType)) phoneQType="Required";
			if("N".equals(phoneQType)) phoneQType="Optional";
			if(powerType.equals("RSVP")){}
			else{
			/*questions_jsonarray.put(fillTransactionlevelBaseQSettings("First Name", "-1", "Required"));
			questions_jsonarray.put(fillTransactionlevelBaseQSettings("Last Name", "-2", "Required"));
			questions_jsonarray.put(fillTransactionlevelBaseQSettings("Email", "-3", "Required"));
			questions_jsonarray.put(fillTransactionlevelBaseQSettings("Phone", "-4", phoneQType));*/
			}
			for(int i=0;i<caArray.size();i++){
				CustomAttribute obj=caArray.get(i);
				JSONObject question_jsonObj = new JSONObject();
				question_jsonObj.put("attribname", obj.getName());
				question_jsonObj.put("attrib_id", obj.getAttribid());
				question_jsonObj.put("isrequired", obj.getIsRequired());
				question_jsonObj.put("attribtype",obj.getQtype());
				question_jsonObj.put("subQnsCount",obj.getSubQnsCount());
				if(i==0){
					question_jsonObj.put("moveup_attrib", "-4");
				}else{
					question_jsonObj.put("moveup_attrib", caArray.get(i-1).getAttribid());
				}
				if(i==caArray.size()-1){
					question_jsonObj.put("movedn_attrib", "0");
				}else{
					question_jsonObj.put("movedn_attrib", caArray.get(i+1).getAttribid());
				}
				questions_jsonarray.put(question_jsonObj);		
			}
			jsonObj.put("questions",questions_jsonarray);
			//System.out.println(questions_jsonarray);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return jsonObj;
	}
	private static JSONArray getTicketsOfQuestion(String qid, String eid, List alltickets) throws Exception{
		
		JSONArray tickets_jsonarray=new JSONArray();
		CustomAttributesDB cadb = new CustomAttributesDB();
		List seltickets = cadb.getCustomQuestionTickets(eid,qid);
		for(int i=0;i<seltickets.size();i++){
			String tid=(String)seltickets.get(i);
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
	public static JSONObject getTicketLevelJsonObj(List<CustomAttribute> caArray, HashMap basicProfileSettingsMap, String eid, List alltickets){
		JSONObject jsonObj = new JSONObject();
		JSONArray questions_jsonarray=new JSONArray();
		try {
			questions_jsonarray.put(fillTicketlevelBaseQSettings("First Name", "-5", basicProfileSettingsMap, eid, alltickets,"fname"));
			questions_jsonarray.put(fillTicketlevelBaseQSettings("Last Name", "-6", basicProfileSettingsMap, eid, alltickets,"lname"));
			questions_jsonarray.put(fillTicketlevelBaseQSettings("Email", "-7", basicProfileSettingsMap, eid, alltickets,"email"));
			questions_jsonarray.put(fillTicketlevelBaseQSettings("Phone", "-8", basicProfileSettingsMap, eid, alltickets,"phone"));
			for(int i=0;i<caArray.size();i++){
				CustomAttribute obj=caArray.get(i);
				JSONObject question_jsonObj = new JSONObject();
				question_jsonObj.put("attribname", obj.getName());
				question_jsonObj.put("attrib_id", obj.getAttribid());
				question_jsonObj.put("tickets", getTicketsOfQuestion(obj.getAttribid(), eid,alltickets));
				if(i==0){
					question_jsonObj.put("moveup_attrib", "-8");
				}else{
					question_jsonObj.put("moveup_attrib", caArray.get(i-1).getAttribid());
				}
				if(i==caArray.size()-1){
					question_jsonObj.put("movedn_attrib", "0");
				}else{
					question_jsonObj.put("movedn_attrib", caArray.get(i+1).getAttribid());
				}
				questions_jsonarray.put(question_jsonObj);		
			}
			jsonObj.put("questions",questions_jsonarray);
		} catch (Exception e) {
			System.out.println("Error in json helper: "+e.getMessage());
		}
		
		return jsonObj;
	}

private static JSONArray getResponsesOfQuestion(String qid, String eid) throws Exception{
		
		JSONArray responses_jsonarray=new JSONArray();
		CustomAttributesDB cadb = new CustomAttributesDB();
		List selresponses = cadb.getRsvpQuestionsettings(eid,qid);
		for(int i=0;i<selresponses.size();i++){
			String rid=(String)selresponses.get(i);
			String rname= "Not Sure";
			if("yes".equals(rid)) rname="Attending";
			if("no".equals(rid)) rname="Not Attending";
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", rname);
			jsonObj.put("tid", rid);
			responses_jsonarray.put(jsonObj);			
		}		
		return responses_jsonarray;
	}
private static JSONObject fillResponselevelBaseQSettings(String attribName, String attribid, String isrequired ,String eid){
	JSONObject question_jsonObj = new JSONObject();
	try{
	question_jsonObj.put("attribname", attribName);
	question_jsonObj.put("attrib_id", attribid);
	question_jsonObj.put("attribtype", "Text");
	//if(attribid.equals("-12") || attribid.equals("-11"))
	//	question_jsonObj.put("responses", getResponsesOfQuestion(attribid, eid));
	question_jsonObj.put("isrequired", isrequired);
	}catch(Exception e){}
	return question_jsonObj;
}
	public static JSONObject getResponseLevelJsonObj(List<CustomAttribute> caArray,  String eid){
		JSONObject jsonObj = new JSONObject();
		JSONArray questions_jsonarray=new JSONArray();
		

		try {
			/*questions_jsonarray.put(fillResponselevelBaseQSettings("First Name", "-9", "Required",eid));
			questions_jsonarray.put(fillResponselevelBaseQSettings("Last Name", "-10", "Required",eid));
			questions_jsonarray.put(fillResponselevelBaseQSettings("Email", "-11", "Required",eid));
			*///questions_jsonarray.put(fillResponselevelBaseQSettings("Phone", "-12", "Required",eid));
			for(int i=0;i<caArray.size();i++){
				CustomAttribute obj=caArray.get(i);
				JSONObject question_jsonObj = new JSONObject();
				question_jsonObj.put("attribname", obj.getName());
				question_jsonObj.put("attrib_id", obj.getAttribid());
				question_jsonObj.put("isrequired", obj.getIsRequired());
				question_jsonObj.put("attribtype",obj.getQtype());
				question_jsonObj.put("subQnsCount",obj.getSubQnsCount());
				/*question_jsonObj.put("responses", getResponsesOfQuestion(obj.getAttribid(), eid));
				if(i==0){
					question_jsonObj.put("moveup_attrib", "-13");
				}else{
					question_jsonObj.put("moveup_attrib", caArray.get(i-1).getAttribid());
				}
				if(i==caArray.size()-1){
					question_jsonObj.put("movedn_attrib", "0");
				}else{
					question_jsonObj.put("movedn_attrib", caArray.get(i+1).getAttribid());
				}*/
				questions_jsonarray.put(question_jsonObj);		
			}
			jsonObj.put("questions",questions_jsonarray);
		} catch (Exception e) {
			System.out.println("Error in json helper: "+e.getMessage());
		}
		
		return jsonObj;
	}

}
