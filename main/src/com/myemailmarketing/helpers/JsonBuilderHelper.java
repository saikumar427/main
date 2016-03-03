package com.myemailmarketing.helpers;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import com.myemailmarketing.beans.CampaignDetails;
import com.myemailmarketing.beans.MailListDetails;
import com.myemailmarketing.beans.MemberData;

public class JsonBuilderHelper {
	private static JSONArray getMembersJson(ArrayList membersList){
		JSONArray events=new JSONArray();
		try{
		for(int i=0;i<membersList.size();i++){
			JSONObject evtObject=new JSONObject();
			MemberData mData=(MemberData)membersList.get(i);
			evtObject.put("name", mData.getFirstName()+" "+mData.getLastName());
			evtObject.put("id", mData.getMemberId());
			//edata.getFormattedStartDate().getYear()
			evtObject.put("email", mData.getEmail());
			evtObject.put("action", "Manage");
			events.put(evtObject);
		}
		}catch(Exception e){}
		return events;
	}
	
	public static JSONObject getMembersJson(ArrayList activeMembersList, ArrayList inactiveMembersList,ArrayList bouncedMemberList,ArrayList unsubscribdeMemberList){
		JSONObject allMembersObject=new JSONObject();
		try{
			allMembersObject.put("active", getMembersJson(activeMembersList));
			allMembersObject.put("inactive", getMembersJson(inactiveMembersList));
			allMembersObject.put("bounced", getMembersJson(bouncedMemberList));
			allMembersObject.put("unsubscribed", getMembersJson(unsubscribdeMemberList));
		}catch(Exception e){}
		return allMembersObject;
	}
	public static JSONObject getEmailBlastsJson(ArrayList<HashMap<String,String>> scheduledBlasts,ArrayList<HashMap<String,String>> completedBlasts){
		JSONObject allListsObject=new JSONObject();
		try{
			allListsObject.put("COMPLETED",getEmailBlastsJson(completedBlasts));
			allListsObject.put("SCHEDULED",getEmailBlastsJson(scheduledBlasts));
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return allListsObject;
	}
	private static JSONArray getEmailBlastsJson(ArrayList<HashMap<String,String>> blastssList){
		JSONArray events=new JSONArray();
		try{
		for(int i=0;i<blastssList.size();i++){
			JSONObject evtObject=new JSONObject();
			HashMap<String,String> dataObj=blastssList.get(i);
			evtObject.put("camp_scheddate",dataObj.get("camp_scheddate"));
			evtObject.put("id", dataObj.get("camp_id"));
			evtObject.put("subject", dataObj.get("camp_subject"));
			evtObject.put("camp_name", dataObj.get("camp_name"));
			events.put(evtObject);
		}
		}catch(Exception e){}
		return events;
	}
	public static JSONObject getMailListJson(ArrayList<MailListDetails> mailingList){
		JSONObject allMailListsObject=new JSONObject();
		JSONArray mailList=new JSONArray();
		try{
			for(int i=0;i<mailingList.size();i++){
				JSONObject mObject=new JSONObject();
				MailListDetails mListObj= mailingList.get(i);
				mObject.put("name",mListObj.getListName());
				mObject.put("count", mListObj.getMemberCount());
				mObject.put("id",mListObj.getListId());
				mObject.put("action", "Manage");
				mailList.put(mObject);
			}
		
		allMailListsObject.put("items",mailList);
		}
		catch(Exception e){
			System.out.println("There is an error in processing the JSON Data for mail lists");
		}
	return allMailListsObject;		
	}
	public static JSONObject getCampListJson(ArrayList<CampaignDetails> campList){
		JSONObject allCampListsObject=new JSONObject();
		JSONArray campListobj=new JSONArray();
		try{
			for(int i=0;i<campList.size();i++){
				JSONObject cObject=new JSONObject();
				CampaignDetails cListObj= campList.get(i);
				cObject.put("name",cListObj.getCampName());
				cObject.put("id",cListObj.getCampId());
				cObject.put("action", "Manage");
				campListobj.put(cObject);
			}
		allCampListsObject.put("citems",campListobj);
		}
		catch(Exception e){
			System.out.println("There is an error in processing the JSON Data for mail lists");
		}
	return allCampListsObject;		
	}
}
