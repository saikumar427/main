package com.myevents.actions;

import java.util.ArrayList;

import com.event.beans.ImportTrackUrlData;
import com.event.dbhelpers.TrackURLDB;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.GenUtil;
import com.myevents.beans.TrackingPartnerData;
import com.myevents.dbhelpers.TrackingPartnerDB;
import com.myevents.helpers.TrackingPartnerJsonBuilder;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class ManageTrackingPartnerAction extends MyEventsActionWrapper implements Preparable,
ValidationAware  {
	TrackingPartnerData tpd=new TrackingPartnerData();
	private String trackname="";
	private String password="";
	private String message="";
	private String photoURL="";
	private String status="";
	private String trackid="";
	private String userName="";
	private String jsonData="";
	private boolean checked=true;
	private String msg="";
	private String manageURL="";
	private ArrayList myOtherEventsList=new ArrayList();
	private ArrayList<TrackingPartnerData> indivualTrackingpartnerList=new ArrayList<TrackingPartnerData>();
	private ArrayList<TrackingPartnerData> trackURLList=new ArrayList<TrackingPartnerData>();
	private ArrayList<TrackingPartnerData> eventsList=new ArrayList<TrackingPartnerData>();
	
	public ArrayList getMyOtherEventsList() {
		return myOtherEventsList;
	}
	public void setMyOtherEventsList(ArrayList myOtherEventsList) {
		this.myOtherEventsList = myOtherEventsList;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public ArrayList<TrackingPartnerData> getEventsList() {
		return eventsList;
	}
	public void setEventsList(ArrayList<TrackingPartnerData> eventsList) {
		this.eventsList = eventsList;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	public ArrayList<TrackingPartnerData> getTrackURLList() {
		return trackURLList;
	}
	public void setTrackURLList(ArrayList<TrackingPartnerData> trackURLList) {
		this.trackURLList = trackURLList;
	}
	public String execute(){
		
		indivualTrackingpartnerList=TrackingPartnerDB.getIndividualTrackingPartnerData(mgrId, trackid);
		userName=DbUtil.getVal("select getMemberPref(?||'','pref:myurl','') as username",new String[]{mgrId});
		String serveraddress=EbeeConstantsF.get("serveraddress","www.eventbee.com");
		String[] saddressarr=GenUtil.strToArrayStr(serveraddress,"."); 
		manageURL="http://"+userName+"."+saddressarr[1]+"."+saddressarr[2]+"/tu/";
		return "managetrackingpartner";
	}
	public String getMyOtherEvents(){
		eventsList=TrackingPartnerDB.importMyOtherEventsData(mgrId,trackid);
		return "myotherevents";
	}
	public String insertMyOtherEvents(){
		   TrackingPartnerData partnerData=TrackingPartnerDB.getTrackingPartnerData(trackid);
		   for(int i=0;i<myOtherEventsList.size();i++)
			 {
				TrackingPartnerDB.insertEventUnderPartner(myOtherEventsList.get(i).toString(),partnerData,trackid);
			 }
		   ArrayList<TrackingPartnerData> trackURLList=TrackingPartnerDB.getTrackURLList(trackid,mgrId);
		   jsonData = TrackingPartnerJsonBuilder.getTrackURLJson(trackURLList,trackid).toString();
		   return "TrackEventsJsonData";
	}
	
	public String deleteTrackPartner(){
		return "deletetrackpartner";
	}
	public void deleteTrackPartnerSuccess(){
		TrackingPartnerDB.deleteTrackingPartnerData(mgrId, trackid, checked);
		}
	
	public String getTrackPartnerMessage(){
		indivualTrackingpartnerList=TrackingPartnerDB.getIndividualTrackingPartnerData(mgrId, trackid);
		message=indivualTrackingpartnerList.get(0).getMessage();
		return "trackmanagemessage";
	}
public String getTrackPartnerStatus(){
		indivualTrackingpartnerList=TrackingPartnerDB.getIndividualTrackingPartnerData(mgrId, trackid);
		status=indivualTrackingpartnerList.get(0).getStatus();
		return "trackmanagestatus";
	}
  public String getTrackPartnerPassword(){
		
		indivualTrackingpartnerList=TrackingPartnerDB.getIndividualTrackingPartnerData(mgrId, trackid);
		password=indivualTrackingpartnerList.get(0).getPassword();
		return "trackmanagepassword";
	}
   public String getTrackPartnerPhotoURL(){
		indivualTrackingpartnerList=TrackingPartnerDB.getIndividualTrackingPartnerData(mgrId, trackid);
		photoURL=indivualTrackingpartnerList.get(0).getPhotoURL();
		return "trackmanagephotourl";
	}
	
	public String changeTrackPartnerMessage(){
		TrackingPartnerDB.changeTrackingPartnerMessage(message,mgrId,trackid,checked);
		return "ajaxmsg";
	}
	public String changeTrackPartnerPassword(){
		TrackingPartnerDB.changeTrackingPartnerPassword(password,mgrId,trackid,checked);
		return "ajaxmsg";
	}
	public String changeTrackPartnerStatus(){
		TrackingPartnerDB.changeTrackingPartnerStatus(status,mgrId,trackid,checked);
		return "ajaxmsg";
	}
	public String changeTrackPartnerPhotoURL(){
		TrackingPartnerDB.changeTrackingPartnerPhotoURL(photoURL,mgrId,trackid,checked);
	    return "ajaxmsg";
	}
	public TrackingPartnerData getTpd() {
		return tpd;
	}

	public void setTpd(TrackingPartnerData tpd) {
		this.tpd = tpd;
	}

	public String getTrackname() {
		return trackname;
	}

	public void setTrackname(String trackname) {
		this.trackname = trackname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrackid() {
		return trackid;
	}

	public void setTrackid(String trackid) {
		this.trackid = trackid;
	}

	
	public ArrayList<TrackingPartnerData> getIndivualTrackingpartnerList() {
		return indivualTrackingpartnerList;
	}
	public void setIndivualTrackingpartnerList(
			ArrayList<TrackingPartnerData> indivualTrackingpartnerList) {
		this.indivualTrackingpartnerList = indivualTrackingpartnerList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String populateTrackURLList(){
		ArrayList<TrackingPartnerData> trackURLList=TrackingPartnerDB.getTrackURLList(trackid,mgrId);
	    jsonData = TrackingPartnerJsonBuilder.getTrackURLJson(trackURLList,trackid).toString();
		return "jsondata";
		
	}
	public String getManageURL() {
		return manageURL;
	}
	public void setManageURL(String manageURL) {
		this.manageURL = manageURL;
	}
}
