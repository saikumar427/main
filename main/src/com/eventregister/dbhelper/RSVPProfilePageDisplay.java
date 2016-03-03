package com.eventregister.dbhelper;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.event.dbhelpers.DisplayAttribsDB;
import com.eventbee.general.GenUtil;
import com.eventbee.general.I18NCacheData;
import com.eventregister.customquestions.beans.AttribOption;
import com.eventregister.customquestions.beans.CustomAttribSet;
import com.eventregister.customquestions.beans.CustomAttribute;
import com.eventregister.customquestions.dbhelper.CustomAttribsDB;

public class RSVPProfilePageDisplay {
	
	HashMap<String, String> regFlowWordings=new HashMap<String, String>();
	public  String getRSVPProfilesJson(String eid,String sure){
		HashMap<String, String> hm= new HashMap<String,String>();
		hm.put("module", "RSVPFlowWordings");
		regFlowWordings=DisplayAttribsDB.getDisplayAttribs(hm, I18NCacheData.getI18NLanguage(eid), eid);
		ArrayList transactionAttribs=null;
		ArrayList al=null;
		System.out.println("in Profile JSON ");
		ProfilePageVm pvm=new ProfilePageVm();
				
		RegistrationRSVPManager regRSVPMgr=new RegistrationRSVPManager();
		CustomAttribsDB ticketcustomattribs=new CustomAttribsDB();
		JSONObject profilesetObject=new JSONObject();
		JSONArray questionObj=new JSONArray();
		try{
			System.out.println("eventid"+eid);
		CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribs.getCustomAttribSet(eid,"EVENT" );
		CustomAttribute[]  attributeSet=customattribs.getAttributes();
	
		profilesetObject.put("profilecount",sure);
		profilesetObject.put("questions",questionObj);
	
		profilesetObject.put("p_fname",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.fname.label","First Name"),"fname","Y"));
		profilesetObject.put("p_lname",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.lname.label","Last Name"),"lname","Y"));
		profilesetObject.put("p_email",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.email.label","Email"),"email","Y"));
		
		
		HashMap attribMap=pvm.getAttribsForTickets("0",eid);
		String isRequired=(String)attribMap.get("phone");
		profilesetObject.put("phone",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.phone.label","Phone"),"phone",isRequired));
		JSONArray buyerarray=new JSONArray();
		buyerarray.put("fname");
		buyerarray.put("lname");
		buyerarray.put("email");
		if(attribMap.containsKey("phone"))
		buyerarray.put("phone");
		transactionAttribs=regRSVPMgr.getQuestionsFortransactionlevel(eid);
		regRSVPMgr.setEid(eid);
		regRSVPMgr.fillCustomQuestions(attributeSet,transactionAttribs,buyerarray,profilesetObject,"p_");
		System.out.println(questionObj);
		profilesetObject.put("transactionquestions",buyerarray);
		JSONArray surequestionsarray=new JSONArray();
		surequestionsarray.put("fname");
		surequestionsarray.put("lname");
		surequestionsarray.put("email");

		profilesetObject.put("s_fname",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.fname.label","First Name"),"fname","Y"));
		profilesetObject.put("s_lname",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.lname.label","Last Name"),"lname","Y"));
		profilesetObject.put("s_email",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.email.label","Email"),"email","Y"));
		transactionAttribs=regRSVPMgr.getQuestionsFortheSelectedOption("101",eid);
		regRSVPMgr.fillCustomQuestions(attributeSet,transactionAttribs,surequestionsarray,profilesetObject,"s_");
		System.out.println("sure"+questionObj);
		profilesetObject.put("responsequestions",surequestionsarray);
		
		}
		catch(Exception e)
		{
		}
		
		return profilesetObject.toString();
		}
	JSONObject getAttendeeObject(String question,String id,String isRequired){
		JSONObject obj=new JSONObject();
		try{
		obj.put("Id",id);
		obj.put("textboxsize","30");
		obj.put("lblText",question);
		obj.put("txtValue","");
		obj.put("type","text");
		obj.put("Required",("Y".equals(isRequired)?"Y":"N"));
		obj.put("ErrorMsg",GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.error.message","Required"));
		obj.put("StarColor","red");
		}
		catch(Exception e){}
		return obj;
		}
}
