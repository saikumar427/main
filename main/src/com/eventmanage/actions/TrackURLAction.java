package com.eventmanage.actions;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.event.beans.EventData;
import com.event.beans.ImportTrackUrlData;
import com.event.beans.TrackURLData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.ReportsDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.TrackURLDB;
import com.eventbee.general.DbUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.eventbee.general.*;
import com.eventbee.namedquery.NQDbUtil;
import com.eventmanage.helpers.TrackURLExportHelper;
import com.eventmanage.helpers.TrackURLJSONBuilderHelper;

public class TrackURLAction extends ActionSupport implements Preparable,ValidationAware{

	private static final long serialVersionUID = -3773514102198232135L;
	private TrackURLData trackURLData;
	private String eid="";
	private String msg="";
	private String eventURL="";
	private String manageEventURL="";
	private String name="";
	private String trackcode="";
	private String visits="";
	private String scode="";
	private String status="";
	private String password="";
	private String photourl="";	
	private String message="";	
	private String alreadyexists="";
	private ArrayList globalUrls=new ArrayList();
	private ArrayList<ImportTrackUrlData> globalTrackUrlList=new ArrayList<ImportTrackUrlData>();//Rahul
	private ArrayList trackURLList=new ArrayList();
	private ArrayList indivualTrackcodeList=new ArrayList();
	private ArrayList indivualTrackreportList=new ArrayList();
	private ArrayList<HashMap<String,String>> getTrackCodeReport=new ArrayList<HashMap<String,String>>();
	private ArrayList Fields=new ArrayList();
	private ArrayList<TrackURLData> trackURLDetailsList=new ArrayList<TrackURLData>();
	private ArrayList<TrackURLData> trackURLSummaryDetailsList=new ArrayList<TrackURLData>();
	private ArrayList dates=new ArrayList();
	private String powertype="";
	private boolean export=false;
	private String excel="";
	private String exporttype="";
	private String sortDirection="";
	private String sortField="";
	private String isrecurring="";
	EventData eventData=new EventData();
	private JSONObject trackCodeData = new JSONObject();
	
	public JSONObject getTrackCodeData() {
		return trackCodeData;
	}
	public void setTrackCodeData(JSONObject trackCodeData) {
		this.trackCodeData = trackCodeData;
	}
	public EventData getEventData() {
		return eventData;
	}
	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}
	public String getPowertype() {
		return powertype;
	}
	public void setPowertype(String powertype) {
		this.powertype = powertype;
	}
	public ArrayList getDates() {
		return dates;
	}
	public void setDates(ArrayList dates) {
		this.dates = dates;
	}
	public String getAlreadyexists() {
		return alreadyexists;
	}
	public void setAlreadyexists(String alreadyexists) {
		this.alreadyexists = alreadyexists;
	}
	public ArrayList<TrackURLData> getTrackURLSummaryDetailsList() {
		return trackURLSummaryDetailsList;
	}
	public void setTrackURLSummaryDetailsList(
			ArrayList<TrackURLData> trackURLSummaryDetailsList) {
		this.trackURLSummaryDetailsList = trackURLSummaryDetailsList;
	}
	private String jsonData="";
	
	public ArrayList<TrackURLData> getTrackURLDetailsList() {
		return trackURLDetailsList;
	}
	public void setTrackURLDetailsList(ArrayList<TrackURLData> trackURLDetailsList) {
		this.trackURLDetailsList = trackURLDetailsList;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public ArrayList getFields() {
		return Fields;
	}
	public void setFields(ArrayList fields) {
		Fields = fields;
	}
	public String getEventURL() {
		return eventURL;
	}
	public void setEventURL(String eventURL) {
		this.eventURL = eventURL;
	}
	public String getManageEventURL() {
		return manageEventURL;
	}
	public void setManageEventURL(String manageEventURL) {
		this.manageEventURL = manageEventURL;
	}
	public ArrayList<HashMap<String, String>> getGetTrackCodeReport() {
		return getTrackCodeReport;
	}
	public void setGetTrackCodeReport(
			ArrayList<HashMap<String, String>> getTrackCodeReport) {
		this.getTrackCodeReport = getTrackCodeReport;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ArrayList getIndivualTrackreportList() {
		return indivualTrackreportList;
	}
	public void setIndivualTrackreportList(ArrayList indivualTrackreportList) {
		this.indivualTrackreportList = indivualTrackreportList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getPhotourl() {
		return photourl;
	}
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTrackcode() {
		return trackcode;
	}
	public void setTrackcode(String trackcode) {
		this.trackcode = trackcode;
	}
	public String getVisits() {
		return visits;
	}
	public void setVisits(String visits) {
		this.visits = visits;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	
	public ArrayList getIndivualTrackcodeList() {		
		return indivualTrackcodeList;
	}
	public void setIndivualTrackcodeList(ArrayList indivualTrackcodeList) {
		this.indivualTrackcodeList = indivualTrackcodeList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList getTrackURLList() {
		return trackURLList;
	}
	public void setTrackURLList(ArrayList trackURLList) {
		this.trackURLList = trackURLList;
	}
	public String getMsg() {
		return msg;
	}
	public TrackURLData getTrackURLData() {
		return trackURLData;
	}
	public void setTrackURLData(TrackURLData trackURLData) {
		this.trackURLData = trackURLData;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public void prepare(){	
		trackURLData=new TrackURLData();
	}
	/*public String execute(){
		//alreadyexists=DbUtil.getVal("select 'yes' from event_custom_urls where eventid=?",new String[]{eid});
		//trackURLData.setAlreadyexists(alreadyexists);
		TrackURLDB.populateEventURL(eid,trackURLData);
	//trackURLList=TrackURLDB.getTrackingURLdetails(eid);
	return "success";
	}*/
	public String execute(){
		TrackURLDB.populateEventURL(eid,trackURLData);
		//trackURLDetailsList=TrackURLDB.getTrackURLdetails(eid);
		trackURLDetailsList=TrackURLDB.getTrackURLSummarydetails(eid);
		JSONObject js=TrackURLJSONBuilderHelper.getTrackURLsListJson(trackURLDetailsList,trackURLData);
		try{
			js.put("codeData",EventDB.serverAdderess());
		}catch(Exception e){
			System.out.println("Exception at widget code.");
		}
		
		
		jsonData=js.toString();		
		return "success";
	}
	public String trackURLexist(){		
		alreadyexists=DbUtil.getVal("select 'yes' from event_custom_urls where eventid=?",new String[]{eid});
		if("yes".equals(alreadyexists))	msg="Success";			
		else msg="not exist";		
		return "ajaxmsg";
	}
	public String importURL(){
		globalTrackUrlList=TrackURLDB.importTrackURL(eid);
		return "import";
	}
	public String insertImportTrackURLs(){
		if(globalUrls.size() > 0){
			// adding for special fee start
			String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
			EventData edata=EventDB.getEventData(eid);
			System.out.println("\n insertImportTrackURLs Powertype: "+edata.getPowertype());
			if(edata.getPowertype().equals("RSVP"))
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","Tracking URLs");
			else{
				if("Yes".equalsIgnoreCase(SpecialFeeDB.checkUpgradeStatus(eid, "TrackURL", "Ticketing", "300")))
					SpecialFeeDB.chekingSpecialFee(eid,mgrId,"300","Tracking URLs");
			}
				
			// special fee end.
		}
		for(int i=0;i<globalUrls.size();i++)
		{
			ImportTrackUrlData imptd=new ImportTrackUrlData();
			imptd=TrackURLDB.getGlobalTrackPartner(globalUrls.get(i).toString());
			TrackURLDB.insertGlobalTrackUnderEvent(imptd,eid);
		}
		msg="";
		return "ajaxmsg";
	}
	public String insertTrackURL(){
		ProfileValidator pv=new ProfileValidator();
		if(pv.checkNameValidity(name,true)){
			String namecheck=name.toLowerCase();
			String alreadyexists=DbUtil.getVal("select 'yes' from trackurls where lower(trackingcode)=? and eventid=?",new String[]{namecheck,eid});
			if(!"yes".equals(alreadyexists)){
				
				// adding for special fee start
				String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
				EventData edata=EventDB.getEventData(eid);
				System.out.println("insertTrackURL Powertype: "+edata.getPowertype());
				if(edata.getPowertype().equals("RSVP"))
					SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP Tracking URLs");
				else{
					if("Yes".equalsIgnoreCase(SpecialFeeDB.checkUpgradeStatus(eid, "TrackURL", "Ticketing", "300")))
						SpecialFeeDB.chekingSpecialFee(eid,mgrId,"300","Tracking URLs");
				}
					
				// special fee end.
				
			String trackingid=DbUtil.getVal("select nextval('trackingid')",new String[]{});
			String secretcode=EncodeNum.encodeNum(trackingid);
			HashMap inParams = new HashMap();
			inParams.put("eventid",eid);
			inParams.put("trackingcode",name);
			inParams.put("password",name);
			inParams.put("trackingid",trackingid);
			inParams.put("secretcode",secretcode);
			inParams.put("message","");
			inParams.put("photourl","");
			NQDbUtil.executeQuery("TrackingDetails", inParams);			
			}else	msg="Name Exists";
		}else{
		msg="spacesInUrl";
		}
		return "ajaxmsg";
	}
	public String manageTrackURL(){
		TrackURLDB.populateManageEventURL(eid,trackURLData,trackcode);
		String trackcodetolower=trackcode.toLowerCase();
		indivualTrackcodeList=TrackURLDB.getIndividualTrackCodeDetails(eid,trackcodetolower,scode);
		return "manage";
	}
	
	public String checkTrackCodeReport(){
		String trackcodereportexists=TrackURLDB.getTrackReportExists(eid,trackcode);	
	    if("yes".equalsIgnoreCase(trackcodereportexists))
	    	msg="trackcodereportexists";
	    else
	    	msg="notrackreport";
		return "ajaxmsg";
	}
	
	public String deleteTrackURL(){
		TrackURLDB.deleteTrackURLFromReport(eid,trackcode);
		msg="delete";
		return "ajaxmsg";
	}
	public String changeTrackURLStatus(){		
		TrackURLDB.changeTrackURLStatus(status,eid,trackcode);
	return "ajaxmsg";
	}
	public String changeTrackURLPwd(){
		TrackURLDB.changeTrackURLPwd(password,eid,trackcode);
	return "ajaxmsg";
	}
	public String changeTrackURLPhoto(){
		TrackURLDB.changeTrackURLPhoto(photourl,eid,trackcode);
	return "ajaxmsg";
	}
	public String changeTrackURLMessage(){
		TrackURLDB.changeTrackURLMessage(message,eid,trackcode);
	return "ajaxmsg";
	}
	public String trackURLReport(){
		String eventtype="Ticketing";
		
		String powertype=DbUtil.getVal("select value from config where config_id " +
				" in(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.rsvp.enabled'",new  String[]{eid});
		if("yes".equals(powertype)){
			Fields=TrackURLDB.populateRSVPFields();
			eventtype="RSVP";
		}else{
			Fields=TrackURLDB.populateFields(eid);
		}
		dates=ReportsDB.getRecurringEvents(eid,trackcode,eventtype);
		TrackURLDB.populateEventURL(eid,trackURLData);
		String trackcodetolower=trackcode.toLowerCase();
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
	public String TrackURLsReportsSummary(){
		TrackURLDB.populateEventURL(eid,trackURLData);
		trackURLList=TrackURLDB.getTrackingURLdetails(eid);
		return "trackreportsummary";
	}
	public String updateTrackUrlData(){
		TrackURLDB.updateTrackUrlData(status, password, photourl, message, trackcode, eid);
		msg="success";
		return "ajaxmsg";
	}
	
	public String populateTrackurlsList(){
		TrackURLDB.populateEventURL(eid,trackURLData);
		//trackURLDetailsList=TrackURLDB.getTrackURLdetails(eid);
		trackURLDetailsList=TrackURLDB.getTrackURLSummarydetails(eid);
		JSONObject js=TrackURLJSONBuilderHelper.getTrackURLsListJson(trackURLDetailsList,trackURLData);
		try{
			js.put("codeData",EventDB.serverAdderess());
		}catch(Exception e){
			System.out.println("Exception at widget code.");
		}
		jsonData=js.toString();		
		return "jsondata";
	}
	public String getReloadedTrackURLData(){
		populateTrackurlsList();
		return "reloadTreckData";
	}
	public String editTrackURL(){
		TrackURLDB.populateManageEventURL(eid,trackURLData,trackcode);
		String trackcodetolower=trackcode.toLowerCase();
		trackCodeData = TrackURLDB.getTrackCodes(eid,trackcodetolower,scode);
		return "editTrack";
	}
	public String populateTrackurlsSummaryList(){
		TrackURLDB.populateEventURL(eid,trackURLData);
		trackURLSummaryDetailsList=TrackURLDB.getTrackURLSummarydetails(eid);
		JSONObject js=TrackURLJSONBuilderHelper.getTrackURLsListJson(trackURLSummaryDetailsList,trackURLData);
		jsonData=js.toString();		
		return "jsonsummarydata";
	}
	public String getTrackPassword(){
		String trackcodetolower=trackcode.toLowerCase();
		indivualTrackcodeList=TrackURLDB.getIndividualTrackCodeDetails(eid,trackcodetolower,scode);
		return "trackmanagepwd";
	}
	public String getTrackPhoto(){
		String trackcodetolower=trackcode.toLowerCase();
		indivualTrackcodeList=TrackURLDB.getIndividualTrackCodeDetails(eid,trackcodetolower,scode);
		return "trackmanagephoto";
	}
	public String getTrackMessage(){
		String trackcodetolower=trackcode.toLowerCase();
		indivualTrackcodeList=TrackURLDB.getIndividualTrackCodeDetails(eid,trackcodetolower,scode);
		return "trackmanagemessage";
	}
	public String createTrackingURL(){
		TrackURLDB.populateEventURL(eid,trackURLData);
		
		return "createtrackURL";
	}
	public ArrayList<ImportTrackUrlData> getGlobalTrackUrlList() {
		return globalTrackUrlList;
	}
	public void setGlobalTrackUrlList(
			ArrayList<ImportTrackUrlData> globalTrackUrlList) {
		this.globalTrackUrlList = globalTrackUrlList;
	}
	public ArrayList getGlobalUrls() {
		return globalUrls;
	}
	public void setGlobalUrls(ArrayList globalUrls) {
		this.globalUrls = globalUrls;
	}
	
	public String addWidget(){
		System.out.println("trackcode"+trackcode+"--"+scode);
		eventData.setServerAddress(EventDB.serverAdderess());
		return "widget";
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
	public String getIsrecurring() {
		return isrecurring;
	}
	public void setIsrecurring(String isrecurring) {
		this.isrecurring = isrecurring;
	}
	
}

