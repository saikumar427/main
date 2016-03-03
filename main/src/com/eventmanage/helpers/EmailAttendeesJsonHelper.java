package com.eventmanage.helpers;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.event.beans.EmailAttendeesData;

public class EmailAttendeesJsonHelper {
	

	private static final Logger log = Logger.getLogger(EmailAttendeesJsonHelper.class);
	private static JSONArray getEmailAttendeesJson(ArrayList<EmailAttendeesData> emailAttendeesList){
		JSONArray jsonarrObj = new JSONArray();
		int ntsIndex=0;
		int ntsTotal = emailAttendeesList.size();
		try {
			for (EmailAttendeesData emailAttendeesData : emailAttendeesList) {
				String isFirst =(ntsIndex==0)?"Y": "N";
				String isLast =(ntsIndex == ntsTotal-1)?"Y": "N";
				JSONObject jsonObj_emailAttendeesDetails = new JSONObject();
				String blastid = emailAttendeesData.getBlastid();
				String schTime = emailAttendeesData.getSchTime();
				String subject = TruncateData(emailAttendeesData.getSubject(),30);
				jsonObj_emailAttendeesDetails.put("isFirst", isFirst);
				jsonObj_emailAttendeesDetails.put("isLast", isLast);
				jsonObj_emailAttendeesDetails.put("blastid", blastid);
				jsonObj_emailAttendeesDetails.put("date", schTime);
				jsonObj_emailAttendeesDetails.put("subject", subject);
				jsonObj_emailAttendeesDetails.put("slno", ntsIndex+1);
				jsonObj_emailAttendeesDetails.put("action", "");
				jsonarrObj.put(jsonObj_emailAttendeesDetails);
				ntsIndex++;
			}
		} catch (Exception ex) {
			System.out.println("\n In getPromotedEventsJson: "+ex.getMessage());
		}
		return jsonarrObj;
	}
	
	public static JSONObject getEmailAttendeesJson(ArrayList scheduledList, ArrayList draftsList, ArrayList sentList){
		JSONObject allEmailAttendeesObj=new JSONObject();
		try{
			allEmailAttendeesObj.put("scheduled", getEmailAttendeesJson(scheduledList));
			allEmailAttendeesObj.put("drafts", getEmailAttendeesJson(draftsList));
			allEmailAttendeesObj.put("sent", getEmailAttendeesJson(sentList));
		}catch(Exception e){}
		return allEmailAttendeesObj;
	}
	
	public static String TruncateData(String basedata, int tsize){
	    if(basedata==null) return "";
	    if(basedata.length()<=tsize) return basedata;
	    return basedata.substring(0,tsize-1 )+"....";
	}
	
	
}
