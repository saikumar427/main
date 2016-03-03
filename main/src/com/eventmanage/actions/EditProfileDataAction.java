package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eventbee.general.DbUtil;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventregister.customquestions.beans.CustomAttribSet;
import com.eventregister.customquestions.beans.CustomAttribute;
import com.eventregister.customquestions.dbhelper.CustomAttribsDB;
import com.eventregister.customquestions.dbhelper.CustomAttribsDBDup;
import com.eventregister.dbhelper.ProfileActionDB;
import com.event.dbhelpers.ProfileResponseDB;
import com.opensymphony.xwork2.ActionSupport;

public class EditProfileDataAction extends ActionSupport {

	String eid="";
	String pid="";
	String profileresponsejson="";
	ProfileActionDB profiledb=new ProfileActionDB();
	CustomAttribsDB ticketcustomattribs=new CustomAttribsDB();
	String buyerupdate="";
	String tid="";
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getBuyerupdate() {
		return buyerupdate;
	}
	public void setBuyerupdate(String buyerupdate) {
		this.buyerupdate = buyerupdate;
	}
	public String getProfileresponsejson() {
		return profileresponsejson;
	}
	public void setProfileresponsejson(String profileresponsejson) {
		this.profileresponsejson = profileresponsejson;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
	public String execute(){
	UpdateAProfiledata();
	return "success";
	
	}
	
	void UpdateAProfiledata(){
		ArrayList<String> basicques=new ArrayList();
		basicques.add("fname");
		basicques.add("lname");
		basicques.add("email");
		basicques.add("phone");
		HashMap basicProfileMap=null;
		//CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribs.getCustomAttribSet(eid,"EVENT" );
		CustomAttribsDBDup ticketcustomattribsdup=new CustomAttribsDBDup();
		CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribsdup.getCustomAttribSet(eid,"EVENT",pid);
		CustomAttribute[]  attributeSet=customattribs.getAttributes();
			try{
		if(profileresponsejson!=null){
			JSONArray objarray=new JSONArray(profileresponsejson);
			for(int index=0;index<objarray.length();index++)	
			{
			JSONObject obj=objarray.getJSONObject(index);
			
			
		String pkey=obj.getString("pid");
		if("yes".equals(buyerupdate)){
			basicProfileMap=ProfileResponseDB.getBuyerResponses(pkey);
		}else{
		basicProfileMap=ProfileResponseDB.getResponses(pkey);
		}
			JSONArray responses=obj.getJSONArray("questions");
		for(int p=0;p<responses.length();p++){
			JSONObject profileobj=responses.getJSONObject(p);	
			String qid=(String)profileobj.get("qid");
			
			if(basicques.contains(qid)){
				basicProfileMap.put(qid,(String)profileobj.get("response"));	
			}
			else{					
				for(int j=0;j<attributeSet.length;j++){
					CustomAttribute cb=(CustomAttribute)attributeSet[j];
					String questionid=cb.getAttribId();
					String bigresponse=null;
					String shortresponse=null;
					if(qid.equals(cb.getAttribId())){
					String question1=cb.getAttributeName();
					String type=cb.getAttributeType();
					System.out.println("type:::"+type);
					if("checkbox".equals(type)||"radio".equals(type)||"select".equals(type)){
					ArrayList options=cb.getAttriboptions();
					JSONArray responsesarray=(JSONArray)profileobj.get("response");
					String response[]=new String[responsesarray.length()];
					for(int q=0;q<responsesarray.length();q++){
						response[q]=(String)responsesarray.get(q);	
					}
					System.out.println("response:::::"+response);
					String responsesVal[]=profiledb.getOptionVal(options,response);
					shortresponse=GenUtil.stringArrayToStr(response,",");
					bigresponse=GenUtil.stringArrayToStr(responsesVal,",");
					}
					else{
						shortresponse=(String)profileobj.get("response");
						bigresponse=(String)profileobj.get("response");
						}
					
						HashMap profileResponseMap=new HashMap();
						String responseid =ProfileResponseDB.getProfileResponseId(pkey);
						
						if("".equals(responseid) || responseid==null ) {
							responseid=DbUtil.getVal("select nextval('attributes_survey_responseid')",null);											
							profiledb.responseMasterInsert(basicProfileMap,eid,responseid);
						}
						boolean status=profiledb.clearResponse(responseid,qid);
						profileResponseMap.put("question",question1);
						profileResponseMap.put("questionid",questionid);
						profileResponseMap.put("shortresponse",shortresponse);
						profileResponseMap.put("bigresponse",bigresponse);
						profileResponseMap.put("responseid", responseid);
						profiledb.insertResponse(profileResponseMap);
				       }	
					}
								
			}
			
		}
		
		if(basicProfileMap!=null){
			boolean status=false;
			if("yes".equals(buyerupdate)){
				status=profiledb.deleteBuyerProfileData(pkey);
			}else{
				status=profiledb.deleteProfileData(pkey);
			}
			
			if(status){
				if("yes".equals(buyerupdate)){
					profiledb.updateBuyerBaseProfile(basicProfileMap);
					profiledb.updateTransactionProfile(basicProfileMap);
				}else{
				 profiledb.updateBaseProfile(basicProfileMap);
				}
			}
		}
			}
		}
		}
		catch(Exception e){
			System.out.println("exception in Editing profiledata"+e.getMessage());
		}
		
		
	}
	
	
	
}
