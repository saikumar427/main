package com.mymarketing.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mymarketing.beans.TemplateData;

public class JsonBuilderHelper {
	
	public static JSONObject getTemplateListJson(ArrayList<TemplateData> templateList){
		JSONObject allTemplatesObject=new JSONObject();
		JSONArray templateListobj=new JSONArray();
		int tempIndex=0;
		int tempTotal = templateList.size();
		try{
			
			for (TemplateData templateData : templateList) {

				String isFirst =(tempIndex==0)?"Y": "N";
				String isLast =(tempIndex == tempTotal-1)?"Y": "N";
				JSONObject jsonObj_tempDetails = new JSONObject();
				jsonObj_tempDetails.put("isFirst", isFirst);
				jsonObj_tempDetails.put("isLast", isLast);
				jsonObj_tempDetails.put("tempid", templateData.getTemplateId());
				jsonObj_tempDetails.put("name", templateData.getName());
				jsonObj_tempDetails.put("subject", templateData.getSubject());
				jsonObj_tempDetails.put("from", templateData.getFrom());
				//jsonObj_tempDetails.put("content", templateData.getContent());
				jsonObj_tempDetails.put("notes", templateData.getNotes());
				jsonObj_tempDetails.put("createdat", templateData.getCreatedAt());
				templateListobj.put(jsonObj_tempDetails);
				tempIndex++;
			}
			
			allTemplatesObject.put("tempaltes",templateListobj);
		}
		catch(Exception e){
			System.out.println("There is an error in processing the JSON Data for mail lists");
		}
	return allTemplatesObject;		
	}
	
	public static JSONObject getSchedulesJson(ArrayList<HashMap<String,String>> scheduledList,
			ArrayList<HashMap<String,String>> completedList,ArrayList<HashMap<String,String>> runningList){
		JSONObject allListsObject=new JSONObject();
		try{
			allListsObject.put("COMPLETED",getScheduleJson(completedList));
			allListsObject.put("SCHEDULED",getScheduleJson(scheduledList));
			allListsObject.put("RUNNING",getScheduleJson(runningList));
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return allListsObject;
	}
	
	private static JSONArray getScheduleJson(ArrayList<HashMap<String,String>> scheduleList){
		JSONArray schedules=new JSONArray();
		try{
		for(int i=0;i<scheduleList.size();i++){
			JSONObject schObject=new JSONObject();
			HashMap<String,String> dataObj=scheduleList.get(i);
			schObject.put("schid",dataObj.get("schid"));
			schObject.put("schname", dataObj.get("schname"));
			schObject.put("schdate", dataObj.get("schdate"));
			schObject.put("sent_count", dataObj.get("sent_count"));
			schObject.put("totalcount", dataObj.get("totalcount"));
			schObject.put("starttime", dataObj.get("starttime"));
			schObject.put("lastsenttime", dataObj.get("lastsenttime"));
			schObject.put("maillistid", dataObj.get("maillistid"));
			schedules.put(schObject);
		}
		}catch(Exception e){}
		return schedules;
	}
	
	
	
	public static JSONObject getMailingListJson(ArrayList<HashMap<String,String>>  maillist){
		JSONObject mailListObj=new JSONObject();
		JSONArray jsonarrObj = new JSONArray();
		try {
			for (int i=0;i<maillist.size();i++) {
				JSONObject jsonObj_mailDetails = new JSONObject();
				HashMap<String,String> dataObj=maillist.get(i);
				String name= dataObj.get("name");
				String description = dataObj.get("description");
				String listid=dataObj.get("list_id");
				jsonObj_mailDetails.put("name", name);
				jsonObj_mailDetails.put("description",description);
				jsonObj_mailDetails.put("listid", listid);
				jsonarrObj.put(jsonObj_mailDetails);
			}
			mailListObj.put("list", jsonarrObj);
		} catch (Exception ex) {
			System.out.println("In getMailingListJson: "+ex.getMessage());
		}
		return mailListObj;
	}

}
