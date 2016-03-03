package com.event.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONObject;
import org.json.JSONArray;

import com.event.dbhelpers.ProfileResponseDB;
import com.eventregister.customquestions.beans.CustomAttribute;
import com.eventregister.customquestions.beans.AttribOption;
import com.eventregister.dbhelper.ProfilePageVm;

public class ProfilePageHelper{
	
	
@SuppressWarnings("unchecked")
public static JSONObject getProfileJson(ArrayList attribids,CustomAttribute[] attributeSet,String ticketid,String eid){

	JSONObject 	profileObj=new JSONObject();
	JSONArray questionArray=new JSONArray();	
	ProfilePageVm pvm=new ProfilePageVm();
	
	try{
	//if(ticketlevelbaseProfiles!=null&&ticketlevelbaseProfiles.contains(ticketid)){
		HashMap hmap=pvm.getAttribsForTickets(ticketid,eid);
		
		if(hmap!=null){
		String fisrequired=(String)hmap.get("fname");
		if(fisrequired==null){
			fisrequired="N";
		}
		if(fisrequired!=null){
		questionArray.put("fname");
		profileObj.put("fname",getAttendeeObject(I18n.getString("global.first.name.lbl"),"fname",fisrequired));
		}
		String lisrequired=(String)hmap.get("lname");
		if(lisrequired==null)
			lisrequired="N";
		if(lisrequired!=null){
			profileObj.put("lname",getAttendeeObject(I18n.getString("global.last.name.lbl"),"lname",lisrequired));
		questionArray.put("lname");
		}
		String emailisrequired=(String)hmap.get("email");
		if(emailisrequired!=null){
			profileObj.put("email",getAttendeeObject(I18n.getString("global.email.lbl"),"email",emailisrequired));
		questionArray.put("email");
		}
		String phoneisrequired=(String)hmap.get("phone");
		if(phoneisrequired!=null){
			profileObj.put("phone",getAttendeeObject(I18n.getString("global.phone.lbl"),"phone",phoneisrequired));
		questionArray.put("phone");
		}
		}		
		//}
		if(attributeSet!=null&&attributeSet.length>0){
		for(int k=0;k<attributeSet.length;k++){
		JSONObject attributesObj=new JSONObject();
		CustomAttribute cb=(CustomAttribute)attributeSet[k];
		if(attribids!=null&&attribids.contains(cb.getAttribId())){
		questionArray.put(cb.getAttribId());
		attributesObj.put("lblText",cb.getAttributeName());
		attributesObj.put("type",cb.getAttributeType());
		attributesObj.put("Required","Required".equals(cb.getIsRequired())?"Y":"N");
		attributesObj.put("ErrorMsg","Required");
		attributesObj.put("StarColor","red");
		attributesObj.put("Id",cb.getAttribId());
		String attrib_setid=cb.getAttribSetid();
			if("text".equals(cb.getAttributeType()))
			attributesObj.put("textboxsize",cb.getTextboxSize());
			else if("textarea".equals(cb.getAttributeType())){
			attributesObj.put("rows",cb.getRows());
			attributesObj.put("cols",cb.getCols());
			}
			else{
			ArrayList  customattribOptions=cb.getAttriboptions();
			if(customattribOptions!=null&&customattribOptions.size()>0){
			JSONArray optionsArrayObj=new JSONArray();
			for(int p=0;p<customattribOptions.size();p++){
			AttribOption option=(AttribOption)customattribOptions.get(p);
			JSONObject optionObj=new JSONObject();
			optionObj.put("Display",option.getOptionValue());
			optionObj.put("Value",option.getOptionid());
			optionsArrayObj.put(optionObj);
			}
			if("select".equals(cb.getAttributeType()))
			attributesObj.put("Validate","N");

			attributesObj.put("Options",optionsArrayObj);
			}
			
			}
			
			}
		profileObj.put(cb.getAttribId(),attributesObj);
		}
	}
		
		profileObj.put("questions",questionArray);
	}
	catch(Exception e)
	{
	System.out.println("Exception in getProfileJson for search Attendee method"+e.getMessage());	
	}
	return profileObj;
}
static JSONObject  getAttendeeObject(String question,String id,String isRequired){
	JSONObject obj=new JSONObject();
	try{
	obj.put("Id",id);
	obj.put("textboxsize","30");
	obj.put("lblText",question);
	obj.put("txtValue","");
	obj.put("type","text");
	obj.put("Required",("Y".equals(isRequired)?"Y":"N"));
	obj.put("ErrorMsg","Required");
	obj.put("StarColor","red");
	}
	catch(Exception e){}
	return obj;
	}

public static JSONObject getResponesObj(String pid,String type){
JSONObject responeobj=new JSONObject();	
 JSONArray basicresponseObj=new JSONArray();
 JSONArray customresarray=new JSONArray();
 HashMap responses=new HashMap();
 if("buyer".equals(type)){
	responses=ProfileResponseDB.getBuyerResponses(pid);
 }else{
 responses=ProfileResponseDB.getResponses(pid);
 }
 ArrayList customrespones=ProfileResponseDB.getCustomReponses(pid);
 try{
 if(responses!=null&&responses.size()>0){
	 Set keys=responses.keySet();  
	 
	 Iterator keyit=keys.iterator();
	while(keyit.hasNext()){
	String key=(String)keyit.next();	 
	JSONObject jobj=new JSONObject();
	jobj.put("qid", key);
	jobj.put("response", responses.get(key));
	basicresponseObj.put(jobj);
	 }
	 	
		
	
 }
 
if(customrespones!=null&&customrespones.size()>0){
	for(int i=0;i<customrespones.size();i++){
		HashMap hm=(HashMap)customrespones.get(i);
		JSONObject obj=new JSONObject();
		obj.put("qid",(String)hm.get("qid"));
		obj.put("response",(String)hm.get("response"));
		customresarray.put(obj);
	}
}
	

responeobj.put("basicres", basicresponseObj);
responeobj.put("customres",customresarray);
 }
catch(Exception e){
	System.out.println("Exception Occured is "+e.getMessage());
}
return responeobj;
}

public static JSONObject getBuyerProfileJson(ArrayList attribids,CustomAttribute[] attributeSet,String eid){

	JSONObject 	profileObj=new JSONObject();
	JSONArray questionArray=new JSONArray();	
	
	try{	
		questionArray.put("fname");
		profileObj.put("fname",getAttendeeObject(I18n.getString("global.first.name.lbl"),"fname","Y"));
		profileObj.put("lname",getAttendeeObject(I18n.getString("global.last.name.lbl"),"lname","Y"));
		questionArray.put("lname");
		profileObj.put("email",getAttendeeObject(I18n.getString("global.email.lbl"),"email","Y"));
		questionArray.put("email");
		profileObj.put("phone",getAttendeeObject(I18n.getString("global.phone.lbl"),"phone","N"));
		questionArray.put("phone");
		//}
		if(attributeSet!=null&&attributeSet.length>0){
		for(int k=0;k<attributeSet.length;k++){
		JSONObject attributesObj=new JSONObject();
		CustomAttribute cb=(CustomAttribute)attributeSet[k];
		if(attribids!=null&&attribids.contains(cb.getAttribId())){
		questionArray.put(cb.getAttribId());
		attributesObj.put("lblText",cb.getAttributeName());
		attributesObj.put("type",cb.getAttributeType());
		attributesObj.put("Required","Required".equals(cb.getIsRequired())?"Y":"N");
		attributesObj.put("ErrorMsg","Required");
		attributesObj.put("StarColor","red");
		attributesObj.put("Id",cb.getAttribId());
		String attrib_setid=cb.getAttribSetid();
			if("text".equals(cb.getAttributeType()))
			attributesObj.put("textboxsize",cb.getTextboxSize());
			else if("textarea".equals(cb.getAttributeType())){
			attributesObj.put("rows",cb.getRows());
			attributesObj.put("cols",cb.getCols());
			}
			else{
			ArrayList  customattribOptions=cb.getAttriboptions();
			if(customattribOptions!=null&&customattribOptions.size()>0){
			JSONArray optionsArrayObj=new JSONArray();
			for(int p=0;p<customattribOptions.size();p++){
			AttribOption option=(AttribOption)customattribOptions.get(p);
			JSONObject optionObj=new JSONObject();
			optionObj.put("Display",option.getOptionValue());
			optionObj.put("Value",option.getOptionid());
			optionsArrayObj.put(optionObj);
			}
			if("select".equals(cb.getAttributeType()))
			attributesObj.put("Validate","N");

			attributesObj.put("Options",optionsArrayObj);
			}
			
			}
			
			}
		profileObj.put(cb.getAttribId(),attributesObj);
		}
	}
		
		profileObj.put("questions",questionArray);
	}
	catch(Exception e)
	{
	System.out.println("Exception in getProfileJson for search Attendee method"+e.getMessage());	
	}
	return profileObj;
}

}