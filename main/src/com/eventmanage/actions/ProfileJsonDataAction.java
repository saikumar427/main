package com.eventmanage.actions;


import com.eventregister.dbhelper.ProfilePageDisplay;
import com.opensymphony.xwork2.ActionSupport;

public class ProfileJsonDataAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String eid="";
	String tid="";
	String profileJsonObj=null;
	ProfilePageDisplay profiledata=null;
	public String getEid() {
		return eid;
	}
	public String getProfileJsonObj() {
		return profileJsonObj;
	}
	public void setProfileJsonObj(String profileJsonObj) {
		this.profileJsonObj = profileJsonObj;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	
	
public	String execute(){
	   getJSONData();	
		return "Success";
	}
	
void getJSONData(){
	
	profiledata=new ProfilePageDisplay();
	profileJsonObj=profiledata.getProfilesJson(tid,eid);
   	}
	

}
