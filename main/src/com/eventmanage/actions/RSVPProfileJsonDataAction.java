package com.eventmanage.actions;

import com.eventregister.dbhelper.RSVPProfilePageDisplay;
import com.opensymphony.xwork2.ActionSupport;

public class RSVPProfileJsonDataAction {
	private static final long serialVersionUID = 1L;
	String eid="";
	String sure="";
	String profileJsonObj=null;
	RSVPProfilePageDisplay profiledata=null;
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getsure() {
		return sure;
	}
	public void setSure(String sure) {
		this.sure = sure;
	}
	public String getProfileJsonObj() {
		return profileJsonObj;
	}
	public void setProfileJsonObj(String profileJsonObj) {
		this.profileJsonObj = profileJsonObj;
	}
	
	public	String execute(){
		   getRSVPJSONData();	
			return "Success";
		}
		
	void getRSVPJSONData(){
		
		profiledata=new RSVPProfilePageDisplay();
		profileJsonObj=profiledata.getRSVPProfilesJson(eid,sure);
	   	}

}
