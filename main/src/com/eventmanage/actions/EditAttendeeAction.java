package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;
import com.event.helpers.ProfilePageHelper;
import com.event.dbhelpers.TransactionDB;
import com.eventbee.general.DbUtil;
import com.eventregister.customquestions.beans.CustomAttribSet;
import com.eventregister.customquestions.beans.CustomAttribute;
import com.eventregister.customquestions.dbhelper.CustomAttribsDB;
import com.eventregister.customquestions.dbhelper.CustomAttribsDBDup;
import com.eventregister.dbhelper.RegistrationTiketingManager;
import com.opensymphony.xwork2.ActionSupport;

public class EditAttendeeAction extends ActionSupport{
String pid="";
String eid="";
String ticketid="";
String tid="";
String profilejson="";
String profileresponse="";
private String deleteattendeenotes="";
private String profilecount="";
HashMap profileMap=new HashMap();
public HashMap getProfileMap() {
	return profileMap;
}
public void setProfileMap(HashMap profileMap) {
	this.profileMap = profileMap;
}
public String getTid() {
	return tid;
}
public void setTid(String tid) {
	this.tid = tid;
}

public String getProfileresponse() {
	return profileresponse;
}
public void setProfileresponse(String profileresponse) {
	this.profileresponse = profileresponse;
}
public String getPid() {
	return pid;
}
public void setPid(String pid) {
	this.pid = pid;
}
public String getEid() {
	return eid;
}
public void setEid(String eid) {
	this.eid = eid;
}
public String getTicketid() {
	return ticketid;
}
public void setTicketid(String ticketid) {
	this.ticketid = ticketid;
}

public String execute(){
	getAttendeeprofileData();
	return "success";
}


void getAttendeeprofileData(){
	CustomAttribsDB ticketcustomattribs=new CustomAttribsDB();
	CustomAttribsDBDup ticketcustomattribsdup=new CustomAttribsDBDup();
	//CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribs.getCustomAttribSet(eid,"EVENT" );
	CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribsdup.getCustomAttribSet(eid,"EVENT",pid);
	CustomAttribute[]  attributeSet=customattribs.getAttributes();
	HashMap ticketspecificAttributeIds=ticketcustomattribs.getTicketLevelAttributes(eid);
	ArrayList attribids=(ArrayList)ticketspecificAttributeIds.get(ticketid);	
	JSONObject profilejsondata=ProfilePageHelper.getProfileJson(attribids,attributeSet,ticketid,eid);
	profilejson=profilejsondata.toString();	
	JSONObject obj=ProfilePageHelper.getResponesObj(pid,"");
	profileresponse=obj.toString();

}
public String editBuyerInfo(){
	RegistrationTiketingManager regTktMgr=new RegistrationTiketingManager();
	ArrayList buyerAttribs=regTktMgr.getBuyerSpecificAttribs(eid);	
	CustomAttribsDB ticketcustomattribs=new CustomAttribsDB();
	CustomAttribsDBDup ticketcustomattribsdup=new CustomAttribsDBDup();
	//CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribs.getCustomAttribSet(eid,"EVENT");
	CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribsdup.getCustomAttribSet(eid,"EVENT",pid);
	CustomAttribute[]  attributeSet=customattribs.getAttributes();
	JSONObject profilejsondata=ProfilePageHelper.getBuyerProfileJson(buyerAttribs,attributeSet,eid);
	profilejson=profilejsondata.toString();
	JSONObject obj=ProfilePageHelper.getResponesObj(pid,"buyer");
	profileresponse=obj.toString();	
	return "editbuyerinfo";
}

public String changeToNotsure(){
	TransactionDB.convetingToNotsure(eid,tid,pid,ticketid);
	return "transactiondetailspage";
}
public String changeToSure(){
	TransactionDB.convetingToSure(eid,tid,pid,ticketid);
	return "transactiondetailspage";
}
public String changeToNotattending(){
	TransactionDB.convetingToNotAttending(eid,tid,pid,ticketid);
	return "transactiondetailspage";
}
public String deleteAttendee(){
	String msg="",profilekeystatus="",paymentstatus="";
    String eventtype=TransactionDB.getEventType(eid);
	try{
    if(Integer.parseInt(profilecount)==1){
   	 if("Ticketing".equals(eventtype)){
	 String changestatus="Cancelled";
	 paymentstatus=DbUtil.getVal("select 'Y' from event_reg_transactions where eventid=? and tid=? and paymentstatus='Cancelled'", new String[]{eid,tid});
	 if(!"Y".equals(paymentstatus)){
	 TransactionDB.insertAttendeeTrack(tid, pid, eid,ticketid,deleteattendeenotes);
	 TransactionDB.completedToOtherStatus(eid,tid,changestatus);
	 }
   	 msg="reportspage";
   	 }else{
   		profilekeystatus=TransactionDB.getProfileKeyStatus(pid,eid,ticketid,tid);
   	    if("Y".equals(profilekeystatus)){
   	    TransactionDB.insertAttendeeTrack(tid, pid, eid,ticketid,deleteattendeenotes);
   	    TransactionDB.deleteRSVPTransaction(eid, tid); 
   	    }
    	msg="rsvpreportspage";
   	 }	
   	 }else{
	 if("Ticketing".equals(eventtype)){
	 profilekeystatus=TransactionDB.getProfileKeyStatus(pid,eid,ticketid,tid); 
	 if("Y".equals(profilekeystatus)){
	 TransactionDB.deleteAttendeeInfo(pid,eid,ticketid,tid,deleteattendeenotes);
   	 //TransactionDB.insertAttendeeTrack(tid, pid, eid,ticketid,deleteattendeenotes);
   	 }
	 msg="transactiondetailspage";
	 }else{
	 System.out.println("enter into RSVP case");
	 profilekeystatus=TransactionDB.getProfileKeyStatus(pid,eid,ticketid,tid);
	 if("Y".equals(profilekeystatus)){
	 TransactionDB.insertAttendeeTrack(tid, pid, eid,ticketid,deleteattendeenotes);
	 TransactionDB.deleteRSVPAttendeeInfo(pid,eid,ticketid,tid);
	 }
	 msg="transactiondetailspage";
	 }
	 }
	}catch(Exception e){
		System.out.println("Exception occured in deleteAttendee method:"+tid+""+eid+e.getMessage());
		msg="transactiondetailspage";
	}
    return msg;
}

public String deleteattendeeNotes(){
	return "deleteattendeenotes";
}

public String getProfilejson() {
	return profilejson;
}
public void setProfilejson(String profilejson) {
	this.profilejson = profilejson;
}
public String getDeleteattendeenotes() {
	return deleteattendeenotes;
}
public void setDeleteattendeenotes(String deleteattendeenotes) {
	this.deleteattendeenotes = deleteattendeenotes;
}
public String getProfilecount() {
	return profilecount;
}
public void setProfilecount(String profilecount) {
	this.profilecount = profilecount;
}

}
