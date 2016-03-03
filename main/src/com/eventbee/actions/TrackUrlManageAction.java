package com.eventbee.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.ReportsDB;
import com.event.dbhelpers.TrackURLDB;
import com.event.dbhelpers.TrackUrlManageDB;

import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventmanage.helpers.TrackURLExportHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.event.beans.TrackURLData;
import com.eventbee.general.GenUtil;

public class TrackUrlManageAction extends ActionSupport {

	public String eid="";
	public String secretcode="";
	public String trackcode="";
	private ArrayList dates=new ArrayList();
	private ArrayList Fields=new ArrayList();
	private ArrayList indivualtrackcode=new ArrayList();
	private TrackURLData trackURLData=new TrackURLData();
	private Vector<HashMap<String,String>> trackdetailsvec=new Vector<HashMap<String,String>>();
	private boolean export=false;
	private String excel="";
	private String exporttype="";
	private String sortDirection="";
	private String sortField="";
	private ArrayList<HashMap<String,String>> getTrackCodeReport=new ArrayList<HashMap<String,String>>();
	private String msg="";
	private String isrecurring="";
	private String powertype="";
	private String visitcount="";
    private String password="";
	private HashMap<String,String>reportMap=new HashMap<String,String>();
    private String errormsg="";
	private String serveraddress="";
	private String photo="";
	private String message="";
	private String userid="";
    private String beeid="";
    private String jsondata = "[]";
    private String eventURL="";
    private String trackid="";
	

	public String getTrackid() {
		return trackid;
	}


	public void setTrackid(String trackid) {
		this.trackid = trackid;
	}


	public String getEventURL() {
		return eventURL;
	}


	public void setEventURL(String eventURL) {
		this.eventURL = eventURL;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public HashMap<String, String> getReportMap() {
		return reportMap;
	}


	public void setReportMap(HashMap<String, String> reportMap) {
		this.reportMap = reportMap;
	}


	public String getPowertype() {
		return powertype;
	}


	public void setPowertype(String powertype) {
		this.powertype = powertype;
	}


	public String getIsrecurring() {
		return isrecurring;
	}


	public void setIsrecurring(String isrecurring) {
		this.isrecurring = isrecurring;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public ArrayList<HashMap<String, String>> getGetTrackCodeReport() {
		return getTrackCodeReport;
	}


	public void setGetTrackCodeReport(
			ArrayList<HashMap<String, String>> getTrackCodeReport) {
		this.getTrackCodeReport = getTrackCodeReport;
	}


	public boolean isExport() {
		return export;
	}


	public void setExport(boolean export) {
		this.export = export;
	}


	public String getExcel() {
		return excel;
	}


	public void setExcel(String excel) {
		this.excel = excel;
	}


	public String getExporttype() {
		return exporttype;
	}


	public void setExporttype(String exporttype) {
		this.exporttype = exporttype;
	}


	public String getSortDirection() {
		return sortDirection;
	}


	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}


	public String getSortField() {
		return sortField;
	}


	public void setSortField(String sortField) {
		this.sortField = sortField;
	}


	public TrackURLData getTrackURLData() {
		return trackURLData;
	}


	public void setTrackURLData(TrackURLData trackURLData) {
		this.trackURLData = trackURLData;
	}


	public ArrayList getFields() {
		return Fields;
	}


	public void setFields(ArrayList fields) {
		Fields = fields;
	}


	public ArrayList getDates() {
		return dates;
	}


	public void setDates(ArrayList dates) {
		this.dates = dates;
	}


	public String getEid() {
		return eid;
	}


	public void setEid(String eid) {
		this.eid = eid;
	}


	public String getSecretcode() {
		return secretcode;
	}


	public void setSecretcode(String secretcode) {
		this.secretcode = secretcode;
	}


	public String getTrackcode() {
		return trackcode;
	}


	public void setTrackcode(String trackcode) {
		this.trackcode = trackcode;
	}



	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public  String execute(){
	return "success";
	}
	public String  validatePassword(){
		if("".equals(password)){
			errormsg="error";return "success";
		}	
		secretcode=TrackUrlManageDB.getTrackSecretcode(eid,trackcode);
		String pwd=TrackUrlManageDB.getTrackCodePassword(eid,trackcode,secretcode);		
		if(password.equals(pwd)){
		Map session=ActionContext.getContext().getSession();
		session.put("loggedin","true");
		serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		return "logedin";	
		}
		errormsg="error";
		return "success";
	}	
	
	
	
	public String  validateGlobalPassword(){	
		 if("".equals(password)){
			errormsg="error";return "success";
			}
		 HashMap<String,String> hmap=TrackUrlManageDB.getGlobalTrackDetails(beeid,trackcode);
		  if(hmap.size()==0){errormsg="error";return "success";
	    }
		  userid=hmap.get("userid");secretcode=hmap.get("scode");String pwd=hmap.get("pwd");
		if(password.equals(pwd)){
		Map session=ActionContext.getContext().getSession();
		session.put("loggedin","true");
	/*	message=TrackUrlManageDB.getAcclevelTrackMessage(userid,trackcode,secretcode);	
	  	photo=TrackUrlManageDB.getAcclevelTrackPhoto(userid,trackcode,secretcode);		
		trackdetailsvec=TrackUrlManageDB.getTrackDetailsInfo(trackcode,secretcode);
		jsondata=TrackUrlManageDB.getTrackStatusJson(trackdetailsvec).toString();*/		
		return "logedin";
		}else{
			errormsg="error";
			return "success";
		}		
		}
	
/*	public String eventLevelManage(){
	   serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		message=TrackUrlManageDB.trackMessage(eid,trackcode,secretcode);
		photo=TrackUrlManageDB.trackPhoto(eid,trackcode,secretcode);
		return "manageurl";
	}*/
	
	
	public String getManagePage(){
		boolean chk=checkinSession();
		if(!chk)return "success";
		serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		eventURL=TrackUrlManageDB.generateEventURL(eid,trackcode);
		HashMap<String,String> hmap=new HashMap<String,String>();
		hmap=TrackUrlManageDB.getTrackUrlDetails(eid,trackcode,secretcode);
		if(hmap.size()==0)return "commonerror";
		photo=hmap.get("photo");
		message=hmap.get("message");
		trackid=hmap.get("trackid");
		if(!"".equals(userid.trim())){			
			password=TrackUrlManageDB.getAcclevelTrackCodePassword(trackcode,secretcode,userid);		
			}else{		
		    password=TrackUrlManageDB.getTrackCodePassword(eid,trackcode.toLowerCase(),secretcode);		   
		}
		return "managepage";
   }
	
   public String getGlobalTrackePage(){
	boolean chk=checkinSession();
	if(!chk)return "success";  	
	serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
	String userName=DbUtil.getVal("select getMemberPref(?||'','pref:myurl','') as username",new String[]{userid});
	String[] saddressarr=GenUtil.strToArrayStr(serveraddress,"."); 
	eventURL="http://"+userName+"."+saddressarr[1]+"."+saddressarr[2]+"/tu/"+trackcode;
	HashMap<String,String> hmap=new HashMap<String,String>();
	hmap=TrackUrlManageDB.getAccTrackUrlDetails(userid,trackcode,secretcode);
	if(hmap.size()==0)return "commonerror";
	photo=hmap.get("photo");
	message=hmap.get("message");
	trackdetailsvec=TrackUrlManageDB.getTrackDetailsInfo(trackcode,secretcode);
	jsondata=TrackUrlManageDB.getTrackStatusJson(trackdetailsvec).toString();
	//System.out.println("the final json data is::"+jsondata);
	return "globalpartner";
   }
	
	
	public String getTrackPassword(){	
		boolean chk=checkinSession();
		if(!chk)return "success";		
		if(!"".equals(userid.trim())){			
		password=TrackUrlManageDB.getAcclevelTrackCodePassword(trackcode,secretcode,userid);		
		}else{		
	    password=TrackUrlManageDB.getTrackCodePassword(eid,trackcode.toLowerCase(),secretcode);		   
		}
		return "pwdscreen";
	}
	
	public String getTrackPhoto(){
		boolean chk=checkinSession();
		if(!chk)return "success";
		if(!"".equals(userid.trim())){			
		photo=TrackUrlManageDB.getAcclevelTrackPhoto(userid,trackcode,secretcode);
		}else{			
		photo=TrackUrlManageDB.trackPhoto(eid,trackcode.toLowerCase(),secretcode);
		}
		return "photoscreen";
		
	}
	
	public String getTrackMessage(){
		boolean chk=checkinSession();
		if(!chk)return "success";
		if(!"".equals(userid.trim())){			
		message=TrackUrlManageDB.getAcclevelTrackMessage(userid,trackcode,secretcode);	
		}else{			
	    message=TrackUrlManageDB.trackMessage(eid,trackcode.toLowerCase(),secretcode);
		}
		return "msgscreen";
		}	
	
	
	public String savePassword(){
		boolean chk=checkinSession();
		if(!chk)return "success";
		if(!"".equals(userid))
		TrackUrlManageDB.changeAcclevelPassword(password,userid,trackcode,secretcode);
		else
		TrackUrlManageDB.changePassword(password,eid,trackcode,secretcode);
		return "manageurl";
	}	
	
	public String savePhotoUrl(){
		boolean chk=checkinSession();
		if(!chk)return "success";
		if(!"".equals(userid))
		TrackUrlManageDB.changeAcclevelPhotoUrl(photo,userid,trackcode,secretcode);
		else
		TrackUrlManageDB.changePhotoUrl(photo,eid,trackcode,secretcode);
		return "manageurl";
	}
	
	public String saveMessage(){
		boolean chk=checkinSession();
		if(!chk)return "success";
		if(!"".equals(userid))
		TrackUrlManageDB.changeAcclevelMessage(message,userid,trackcode,secretcode);		
		else
		TrackUrlManageDB.changeMessage(message,eid,trackcode,secretcode);
		return "manageurl";
	}
	
	/*public String goBack(){
		if(eid==null || "".equals(eid.trim()))
		return "commonerror";
		else{
		boolean chk=checkinSession();
		if(!chk)return "success";
		serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		HashMap<String,String> hmap=new HashMap<String,String>();
		hmap=TrackUrlManageDB.getTrackUrlDetails(eid,trackcode,secretcode);
		photo=hmap.get("photo");
		message=hmap.get("message");
		return "manageurl";
		}
	}*/
	
	public String getTrackUrlReports(){
		String eventtype="Ticketing";
		boolean chk=checkinSession();
		if(!chk)return "success";
		reportMap=TrackUrlManageDB.getTrackUrlName(eid,trackcode);
		if("".equals(reportMap.get("evtname")))
			return "commonerror";
		boolean isrecur = EventDB.isRecurringEvent(eid);
		if(isrecur)
		isrecurring="true";
		powertype=DbUtil.getVal("select value from config where config_id " +
				" in(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.rsvp.enabled'",new  String[]{eid});
		if("yes".equals(powertype)){
		 Fields=TrackURLDB.populateRSVPFields();
		 eventtype="RSVP";
		}else
		 Fields=TrackURLDB.populateFields(eid);
		dates=ReportsDB.getRecurringEvents(eid,trackcode,eventtype);
		String trackcodetolower=trackcode.toLowerCase();
		visitcount=DbUtil.getVal("select count from trackURLs where eventid=? and lower(trackingcode)=? and"+"" +
				" secretcode=?",new String[]{eid,trackcodetolower,secretcode});				
		if(visitcount==null || "".equals(visitcount))visitcount="0";
		TrackURLDB.populateEventURL(eid,trackURLData);
		
		if(export)
		{
		  trackURLData.setDir(sortDirection);
		  trackURLData.setSortedkey(sortField);
		}else{
		  trackURLData.setDir("");
		  trackURLData.setSortedkey("");
		}
		getTrackCodeReport=TrackURLDB.getTrackCodeReport(eid, trackcodetolower, TrackURLDB.getTicketInfo(eid),trackURLData,powertype);
		msg=TrackURLDB.getTrackCodeJsonData(getTrackCodeReport,Fields);
		if(export){
			excel = TrackURLExportHelper.exportToExcel(getTrackCodeReport,Fields,"trackurl",exporttype);
			if(exporttype.equals("excel"))
				return "exporttoexcel";
			else if(exporttype.equals("csv"))
				return "exporttocsv";
		}

		return "report";
	}
	
	
	public boolean checkinSession(){
    	Map session = ActionContext.getContext().getSession();
    if(session.size()==0)return false;
    else 
    	return true;
    }
	
	public String getVisitcount() {
		return visitcount;
	}
	public void setVisitcount(String visitcount) {
		this.visitcount = visitcount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getServeraddress() {
		return serveraddress;
	}
	public void setServeraddress(String serveraddress) {
		this.serveraddress = serveraddress;
	}
	public String getBeeid() {
		return beeid;
	}
	public void setBeeid(String beeid) {
		this.beeid = beeid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getJsondata() {
		return jsondata;
	}
	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
	public Vector<HashMap<String, String>> getTrackdetailsvec() {
		return trackdetailsvec;
	}
	public void setTrackdetailsvec(Vector<HashMap<String, String>> trackdetailsvec) {
		this.trackdetailsvec = trackdetailsvec;
	}
}
