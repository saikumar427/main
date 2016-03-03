package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.velocity.VelocityContext;

import com.event.dbhelpers.DisplayAttribsDB;
import com.eventregister.customquestions.beans.CustomAttribSet;
import com.eventregister.customquestions.beans.CustomAttribute;
import com.eventregister.customquestions.dbhelper.CustomAttribsDB;
import com.eventregister.dbhelper.RSVPProfilePageVm;
import com.eventregister.dbhelper.RegistrationRSVPManager;

public class RSVPProfileVmDataAction {
	String eid="";
	String sure="";
	String eventdate="";
	Vector profileMap=null;
	HashMap attribMap=new HashMap();
	HashMap sureattribMap=new HashMap();
	HashMap statmap=new HashMap();
	public String getEid() {
		return eid;
	}
	public HashMap getSureattribMap() {
		return sureattribMap;
	}
	public void setSureattribMap(HashMap sureattribMap) {
		this.sureattribMap = sureattribMap;
	}
	public HashMap getStatmap() {
		return statmap;
	}
	public void setStatmap(HashMap statmap) {
		this.statmap = statmap;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public HashMap getAttribMap() {
		return attribMap;
	}
	public void setAttribMap(HashMap attribMap) {
		this.attribMap = attribMap;
	}
	public String getEventdate() {
		return eventdate;
	}
	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}
	public String getSure() {
		return sure;
	}
	public int getSureQty(){
		try{
		return Integer.parseInt(sure);
		}
		catch(Exception e){
			return 1;
		}
	}
	public void setSureQty(int qty){
		this.sure=""+qty;
	}
	public void setSure(String sure) {
		this.sure = sure;
	}
	public String execute(){
		System.out.println("in execute");
	getRSVPProfilePageVm();		
	return "Success";	
	}
	
	public Vector getProfileMap() {
		return profileMap;
	}
	public void setProfileMap(Vector profileMap) {
		this.profileMap = profileMap;
	}
	void getRSVPProfilePageVm(){
		String arribsetid=null;
		CustomAttribsDB ticketcustomattribs=new CustomAttribsDB();
		HashMap profilePageLabels=DisplayAttribsDB.getAttribValues(eid,"RSVPFlowWordings");
		VelocityContext context = new VelocityContext();
		CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribs.getCustomAttribSet(eid,"EVENT" );
		CustomAttribute[]  attributeSet=customattribs.getAttributes();
		arribsetid=customattribs.getAttribSetid();
		RSVPProfilePageVm profilevm=new RSVPProfilePageVm();
		profileMap=profilevm.gettransactionprofile(eid,attributeSet);
		attribMap.put("customProfile",profileMap);
		Vector surequestions=profilevm.getattendprofile(eid,"yes",attributeSet,context,profilePageLabels);
		sureattribMap.put("SurecustomProfile",surequestions);
		ArrayList al = new ArrayList();
		al=RegistrationRSVPManager.getQuestionsFortransactionlevel(eid);
		System.out.println(al.toString());
		if(al.isEmpty()){
			statmap.put("translevelquestions", "NO");
		}
		else{
			statmap.put("translevelquestions", "YES");
		}
		System.out.println("statusmap-->"+statmap.toString());
	}
	
	public void setBaseProfileTickets(HashMap attribMap) {
		this.attribMap = attribMap;
	}
}
