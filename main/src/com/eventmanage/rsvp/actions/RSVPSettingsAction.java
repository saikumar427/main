package com.eventmanage.rsvp.actions;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.event.beans.EventData;
import com.event.dbhelpers.EventDB;
import com.event.helpers.I18n;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class RSVPSettingsAction extends ActionSupport implements Preparable,ValidationAware{

	private static final long serialVersionUID = 2215838549244896041L;
	//------------------------------------------Properties--------------------------------------------------------
	String eid="";
	private EventData eventData=null;
	private boolean errorExists;
	private String msgType = "rsvpsettings";
	//------------------------------------------Setters and Getters----------------------------------------------- 
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public EventData getEventData() {
		return eventData;
	}
	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}
	public boolean isErrorExists() {
		return errorExists;
	}
	public void setErrorExists(boolean errorExists) {
		this.errorExists = errorExists;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	//-------------------------------------------Action Methods---------------------------------------------------
	public String execute(){
		eventData=EventDB.getRSVPSettings(eid);
		String rsvplimit=eventData.getRsvplimit();
		if("".equals(rsvplimit) || rsvplimit==null)
			eventData.setRsvplimit("1");
		return "success";
	}
	public boolean validateData(){
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		int rsvplimit=0,rsvpattlimt=0,rsvpnotsurelimit=0;
		try{
			if(!("".equals(eventData.getRsvpattendeelimit()))){
			rsvpattlimt=Integer.parseInt(eventData.getRsvpattendeelimit().trim());
			if(rsvpattlimt<1){
				
				addFieldError("eventData.rsvpattendeelimit",resourceBundle.getString("rsvps.max.att.minimum.one"));
			}
			}
			else{
				addFieldError("eventData.rsvpattendeelimit",resourceBundle.getString("rsvps.max.att.rsvp.empty.msg"));
			}
		}
		catch (Exception e) {
			addFieldError("eventData.rsvpattendeelimit",resourceBundle.getString("rsvps.max.count.rsvp.num.msg"));
		}
		if("notsure".equalsIgnoreCase(eventData.getNotsuretype())){
		try{
			if(!("".equals(eventData.getRsvpnotsurelimit()))){
				rsvpnotsurelimit=Integer.parseInt(eventData.getRsvpnotsurelimit().trim());
				if(rsvpnotsurelimit<1)
					addFieldError("eventData.rsvpnotsurelimit",resourceBundle.getString("rsvps.max.not.sure.min.one"));
			}
			else
				addFieldError("eventData.rsvpnotsurelimit",resourceBundle.getString("rsvps.max.not.sure.empty.msg"));
		}
		catch(Exception e){
			addFieldError("eventData.rsvpnotsurelimit",resourceBundle.getString("rsvps.max.not.sure.rsvp.num.error.msg"));
		}
		}
		System.out.println(eventData.getRsvptype());
		if(eventData.getRsvptype().equals("1")){
			try{
				if(!("".equals(eventData.getRsvplimit()))){
				rsvplimit=Integer.parseInt(eventData.getRsvplimit().trim());
				if(rsvplimit<1)
					addFieldError("eventData.rsvplimit",resourceBundle.getString("rsvps.total.atte.count.limit.num"));
				}else{
					addFieldError("eventData.rsvplimit",resourceBundle.getString("rsvps.total.rsvp.count.empty"));
				}
			}
			catch (Exception e) {
				addFieldError("eventData.rsvplimit",resourceBundle.getString("rsvps.total.rsvp.att.count.num"));
			}
			
		}
		if(!getFieldErrors().isEmpty()){
			errorExists=true;
			return false;
		}	 
		return true;
	}
	public String updateRsvp(){
		boolean status=validateData();
		if(status){
			EventDB.updateRSVPSettings(eventData,eid);
			return "updated";
		}
		return "fail";
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		try{
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
