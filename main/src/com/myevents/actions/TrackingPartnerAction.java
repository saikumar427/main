package com.myevents.actions;

import java.util.*;

import org.apache.log4j.Logger;





import com.eventbee.general.DbUtil;
import com.eventbee.general.ProfileValidator;
import com.myevents.beans.TrackingPartnerData;
import com.myevents.dbhelpers.TrackingPartnerDB;
import com.myevents.helpers.TrackingPartnerJsonBuilder;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;




public class TrackingPartnerAction extends MyEventsActionWrapper implements Preparable,
           ValidationAware {
	
	private static Logger log = Logger.getLogger(TrackingPartnerAction.class);
	private static final long serialVersionUID = -3773514102198232135L;
	private TrackingPartnerData trackingPartnerData;
	private String msg="";
	private String msgKey="";
	private String jsonData="";
	private ArrayList<TrackingPartnerData> trackingPartnersList=new ArrayList<TrackingPartnerData>();
	private String trackid="";
	public String getTrackid() {
		return trackid;
	}
	public void setTrackid(String trackid) {
		this.trackid = trackid;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	public ArrayList<TrackingPartnerData> getTrackingPartnersList() {
		return trackingPartnersList;
	}
	public void setTrackingPartnersList(
			ArrayList<TrackingPartnerData> trackingPartnersList) {
		this.trackingPartnersList = trackingPartnersList;
	}
	
	public TrackingPartnerData getTrackingPartnerData() {
		return trackingPartnerData;
	}
	public void setTrackingPartnerData(TrackingPartnerData trackingPartnerData) {
		this.trackingPartnerData = trackingPartnerData;
	}

	
	
	public String execute(){
		System.out.println("in execute method")	;
		return "success";
		}
	public String save(){
		    System.out.println("we are in save"+trackingPartnerData.getTrackname());
		    ProfileValidator pv=new ProfileValidator();
		    String tracknameCheck=trackingPartnerData.getTrackname();
		    trackingPartnerData.setPassword(tracknameCheck);
		    if("".equals(tracknameCheck))
		    {
		    	msg="Enter Name"; 	
		    	return "ajaxmsg";
		    }
		    
		    if(pv.checkNameValidity(tracknameCheck,true)){
		    	
		    String alreadyexist=DbUtil.getVal("select 'yes' from accountlevel_track_partners where lower(trackname)=? and userid=to_number(?,'9999999999')",new String[]{tracknameCheck.toLowerCase(),mgrId});
		    if(!"yes".equals(alreadyexist)){
			String trackid=TrackingPartnerDB.saveTrackingPartnerData(trackingPartnerData, mgrId);
		               msg="success_"+trackid;
		    }
		    else	msg=" You have entered already existed Name";
		    	//addFieldError("trackingPartnerData.trackname","Tracking url already exist");
		    }
		    else
		       msg="Use only alphanumeric characters in Name";
     	   	    return "ajaxmsg";
			
		}	
	/*public String edit(){
		trackingPartnerData=TrackingPartnerDB.getTrackingPartnerData(mgrId,trackid);
		if(trackingPartnerData==null){
			msgKey="trackingPartner.idnotfound";
			return "success";
		}
		return "success";		
	}*/
	public String populateTrackingPartnersList(){
		trackingPartnersList=TrackingPartnerDB.getTrackingPartnersList(mgrId);
		jsonData = TrackingPartnerJsonBuilder.getTrackingPartnerJson(trackingPartnersList,mgrId).toString();
		System.out.println("after jsondata");
		return "jsondata";
		
	}
	public String delete(){
	    log.info("deleting... TrackingPartner");
		//TrackingPartnerDB.deleteTrackingPartnerData(mgrId,trackid);		
	    msgKey="trackingpartner.deleted";
		return "deleted";		
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
