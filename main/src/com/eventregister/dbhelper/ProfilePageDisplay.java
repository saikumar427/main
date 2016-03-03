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

import freemarker.ext.beans.HashAdapter;

public class ProfilePageDisplay {
	HashMap<String, String> regFlowWordings=new HashMap<String, String>();
	public  String getProfilesJson(String tid,String eid){
		System.out.println("in Profile JSON ");
		//ArrayList ticketlevelbaseProfiles=null;
		ArrayList buyerAttribs=null;
		ArrayList al=null;
        ProfilePageVm pvm=new ProfilePageVm();
		HashMap ticketspecificAttributeIds=null;
		RegistrationTiketingManager regTktMgr=new RegistrationTiketingManager();
		CustomAttribsDB ticketcustomattribs=new CustomAttribsDB();
		ArrayList selectedTickets=regTktMgr.getSelectedTickets(eid,tid);
		System.out.println("selectedTickets"+selectedTickets);
		selectedTickets=regTktMgr.getSelectedTickets(eid,tid);
		JSONArray ticketsArray=new JSONArray();
		JSONObject profilesetObject=new JSONObject();
		JSONObject profileCount=new JSONObject();
		JSONObject questionObj=new JSONObject();
		try{
			HashMap<String, String> hm= new HashMap<String,String>();
			hm.put("module", "RegFlowWordings");
			regFlowWordings=DisplayAttribsDB.getDisplayAttribs(hm, I18NCacheData.getI18NLanguage(eid), eid);
			System.out.println("reg flow wordingsssssss::"+regFlowWordings);
		CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribs.getCustomAttribSet(eid,"EVENT" );
		CustomAttribute[]  attributeSet=customattribs.getAttributes();
		for(int i=0;i<selectedTickets.size();i++){
		JSONArray questionArray=new JSONArray();
		HashMap profileMap=(HashMap)selectedTickets.get(i);
		String selecedticket=(String)profileMap.get("selectedTicket");
		//ticketlevelbaseProfiles=regTktMgr.getTicketIdsForBaseProfiles(eid);
     	//if(ticketlevelbaseProfiles!=null&&ticketlevelbaseProfiles.contains(selecedticket)){
		HashMap hmap=pvm.getAttribsForTickets(selecedticket,eid);
		if(hmap!=null&&hmap.size()>0){
		String fisrequired=(String)hmap.get("fname");
		if(fisrequired!=null){
		questionArray.put("fname");
		profilesetObject.put(selecedticket+"_fname",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.fname.label","First Name"),"fname",fisrequired));
		}
		String lisrequired=(String)hmap.get("lname");
		if(lisrequired!=null){
		profilesetObject.put(selecedticket+"_lname",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.lname.label","Last Name"),"lname",lisrequired));
		questionArray.put("lname");
		}
		String emailisrequired=(String)hmap.get("email");
		if(emailisrequired!=null){
		profilesetObject.put(selecedticket+"_email",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.email.label","Email"),"email",emailisrequired));
		questionArray.put("email");
		}
		String phoneisrequired=(String)hmap.get("phone");
		if(phoneisrequired!=null){
		profilesetObject.put(selecedticket+"_phone",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.phone.label","Phone"),"phone",phoneisrequired));
		questionArray.put("phone");
		}
		}		
		//}
		ticketspecificAttributeIds=ticketcustomattribs.getTicketLevelAttributes(eid);


		if(ticketspecificAttributeIds!=null&&ticketspecificAttributeIds.containsKey(selecedticket))
		{
		al=(ArrayList)ticketspecificAttributeIds.get(selecedticket);

		if(attributeSet!=null&&attributeSet.length>0){
		for(int k=0;k<attributeSet.length;k++){
		HashMap customMap=new HashMap();
		JSONObject attributesObj=new JSONObject();
		CustomAttribute cb=(CustomAttribute)attributeSet[k];
		customMap.put("qType",cb.getAttributeType());
		customMap.put("qId",cb.getAttribId());
		attributesObj.put("lblText",cb.getAttributeName());
		attributesObj.put("type",cb.getAttributeType());
		attributesObj.put("Required","Required".equals(cb.getIsRequired())?"Y":"N");
		attributesObj.put("ErrorMsg",GenUtil.getHMvalue(regFlowWordings,"event.reg.requiredprofile.empty.msg","Required"));
		attributesObj.put("StarColor","red");
		attributesObj.put("Id",cb.getAttribId());
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
		if((al!=null&&al.contains(cb.getAttribId()))){

		profilesetObject.put(selecedticket+"_"+cb.getAttribId(),attributesObj);
		questionArray.put(cb.getAttribId());
		}
		}
		}
		}
		profileCount.put(selecedticket,profileMap.get("qty"));
		ticketsArray.put(selecedticket);
		questionObj.put(selecedticket,questionArray);
		}
		profilesetObject.put("profilecount",profileCount);
		profilesetObject.put("questions",questionObj);
		profilesetObject.put("tickets",ticketsArray);
		profilesetObject.put("buyer_fname",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.fname.label","First Name"),"fname","Y"));
		profilesetObject.put("buyer_lname",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.lname.label","Last Name"),"lname","Y"));
		profilesetObject.put("buyer_email",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.email.label","Email"),"email","Y"));
		HashMap attribMap=pvm.getAttribsForTickets("0",eid);
		String isRequired=(String)attribMap.get("phone");
		profilesetObject.put("buyer_phone",getAttendeeObject(GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.phone.label","Phone"),"phone",isRequired));
		JSONArray buyerarray=new JSONArray();
		buyerarray.put("fname");
		buyerarray.put("lname");
		buyerarray.put("email");
		if(attribMap.containsKey("phone"))
		buyerarray.put("phone");
		buyerAttribs=regTktMgr.getBuyerSpecificAttribs(eid);
		if(attributeSet!=null&&attributeSet.length>0){
		for(int k=0;k<attributeSet.length;k++){
		HashMap customMap=new HashMap();
		JSONObject attributesObj=new JSONObject();
		CustomAttribute cb=(CustomAttribute)attributeSet[k];

		customMap.put("qType",cb.getAttributeType());
		customMap.put("qId",cb.getAttribId());
		attributesObj.put("lblText",cb.getAttributeName());
		attributesObj.put("type",cb.getAttributeType());
		attributesObj.put("Required","Required".equals(cb.getIsRequired())?"Y":"N");
		attributesObj.put("Id",cb.getAttribId());
		attributesObj.put("isactive",cb.getIsActive());
		attributesObj.put("attrib_shortForm",cb.getAttributeName_shortForm());
		attributesObj.put("ErrorMsg",GenUtil.getHMvalue(regFlowWordings,"event.reg.requiredprofile.empty.msg","Required"));
		attributesObj.put("StarColor","red");
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
		if(buyerAttribs!=null&&buyerAttribs.contains(cb.getAttribId())){
		profilesetObject.put("buyer_"+cb.getAttribId(),attributesObj);
		buyerarray.put(cb.getAttribId());
		}
		}
		}
		profilesetObject.put("buyerquest",buyerarray);
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
		obj.put("ErrorMsg",GenUtil.getHMvalue(regFlowWordings,"event.reg.requiredprofile.empty.msg","Required"));
		obj.put("StarColor","red");
		}
		catch(Exception e){}
		return obj;
		}

}
