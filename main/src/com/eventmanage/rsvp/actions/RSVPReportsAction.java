package com.eventmanage.rsvp.actions;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONObject;

import com.event.dbhelpers.ReportsDB;
import com.event.rsvp.beans.RSVPReportsData;
import com.eventmanage.rsvp.helpers.RsvpExcelExport;
import com.eventmanage.rsvp.dbhelpers.RSVPReportsDB;
import com.eventmanage.rsvp.helpers.RSVPReportsJsonHelper;
import com.opensymphony.xwork2.ActionSupport;

public class RSVPReportsAction extends ActionSupport{

	//-----------------------------------------Properties------------------------------------
	private static final long serialVersionUID = 4164501940663717172L;
	public static final HashMap<String,String> rsvpReportsColumnMap=new HashMap<String, String>();
	public static final HashMap<String, String> rsvpEbeeI18nMapping=new HashMap<String, String>();
	private String eid="";
	private String setid="";
	private ArrayList resFields=new ArrayList();
	private ArrayList transFields=new ArrayList();
	private ArrayList checkinFields=new ArrayList();
	private ArrayList<HashMap<String,String>> rsvpResponseMap=new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String,String>> rsvpcheckedinReportsMap=new ArrayList<HashMap<String,String>>();
	private ArrayList attributes=new ArrayList();
	private ArrayList responseQuestionFields=new ArrayList();
	private ArrayList respattributes=new ArrayList();
	private RSVPReportsData rsvpReportsData=new RSVPReportsData();
	private ArrayList responseType=new ArrayList();
	private ArrayList recurringDates=new ArrayList();
	private ArrayList trackCodes=new ArrayList();
	private String rsvpReportType="";
	private String resattribcount="";
	private String msgKey="";
	private String msg="";
	private boolean export=false;
	private String excel="";
	private String exporttype="";
	private String sortDirection="";
	private String sortField="";
	private HashMap<String,String> attributeFilterMap=new HashMap<String, String>();
	private HashMap<String,String> customAttribsMap=new HashMap<String, String>();
	private String recurringAttendeeCountJson="''";
	private JSONObject jsonObj;
	private String totalSure="";
	private String totalNotSure="";
	private String totalCheckIn="";
	
	public String getTotalCheckIn() {
		return totalCheckIn;
	}
	public void setTotalCheckIn(String totalCheckIn) {
		this.totalCheckIn = totalCheckIn;
	}
	public String getTotalSure() {
		return totalSure;
	}
	public void setTotalSure(String totalSure) {
		this.totalSure = totalSure;
	}
	public String getTotalNotSure() {
		return totalNotSure;
	}
	public void setTotalNotSure(String totalNotSure) {
		this.totalNotSure = totalNotSure;
	}
	public String getRecurringAttendeeCountJson() {
		return recurringAttendeeCountJson;
	}
	public void setRecurringAttendeeCountJson(String recurringAttendeeCountJson) {
		this.recurringAttendeeCountJson = recurringAttendeeCountJson;
	}
	public JSONObject getJsonObj() {
		return jsonObj;
	}
	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}
	
	public HashMap<String, String> getCustomAttribsMap() {
		return customAttribsMap;
	}
	public void setCustomAttribsMap(HashMap<String, String> customAttribsMap) {
		this.customAttribsMap = customAttribsMap;
	}
	//---------------------------------------Setters and Getters--------------------------------
	public HashMap<String, String> getAttributeFilterMap() {
		return attributeFilterMap;
	}
	public void setAttributeFilterMap(HashMap<String, String> attributeFilterMap) {
		this.attributeFilterMap = attributeFilterMap;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getSetid() {
		return setid;
	}
	public void setSetid(String setid) {
		this.setid = setid;
	}
	public ArrayList getResFields() {
		return resFields;
	}
	public void setResFields(ArrayList resFields) {
		this.resFields = resFields;
	}
	public ArrayList getTransFields() {
		return transFields;
	}
	public void setTransFields(ArrayList transFields) {
		this.transFields = transFields;
	}
	public ArrayList<HashMap<String, String>> getRsvpResponseMap() {
		return rsvpResponseMap;
	}
	public void setRsvpResponseMap(
			ArrayList<HashMap<String, String>> rsvpResponseMap) {
		this.rsvpResponseMap = rsvpResponseMap;
	}
	public ArrayList getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList attributes) {
		this.attributes = attributes;
	}
	public RSVPReportsData getRsvpReportsData() {
		return rsvpReportsData;
	}
	public void setRsvpReportsData(RSVPReportsData rsvpReportsData) {
		this.rsvpReportsData = rsvpReportsData;
	}
	public ArrayList getResponseType() {
		return responseType;
	}
	public void setResponseType(ArrayList responseType) {
		this.responseType = responseType;
	}
	public ArrayList getRespattributes() {
		return respattributes;
	}
	public void setRespattributes(ArrayList respattributes) {
		this.respattributes = respattributes;
	}
	public ArrayList getRecurringDates() {
		return recurringDates;
	}
	public void setRecurringDates(ArrayList recurringDates) {
		this.recurringDates = recurringDates;
	}
	public String getRsvpReportType() {
		return rsvpReportType;
	}
	public void setRsvpReportType(String rsvpReportType) {
		this.rsvpReportType = rsvpReportType;
	}
	public ArrayList getResponseQuestionFields() {
		return responseQuestionFields;
	}
	public void setResponseQuestionFields(ArrayList responseQuestionFields) {
		this.responseQuestionFields = responseQuestionFields;
	}
	public String getResattribcount() {
		return resattribcount;
	}
	public void setResattribcount(String resattribcount) {
		this.resattribcount = resattribcount;
	}
	public ArrayList getTrackCodes() {
		return trackCodes;
	}
	public void setTrackCodes(ArrayList trackCodes) {
		this.trackCodes = trackCodes;
	}
	public ArrayList<HashMap<String, String>> getRsvpcheckedinReportsMap() {
		return rsvpcheckedinReportsMap;
	}
	public void setRsvpcheckedinReportsMap(
			ArrayList<HashMap<String, String>> rsvpcheckedinReportsMap) {
		this.rsvpcheckedinReportsMap = rsvpcheckedinReportsMap;
	}
	public ArrayList getCheckinFields() {
		return checkinFields;
	}
	public void setCheckinFields(ArrayList checkinFields) {
		this.checkinFields = checkinFields;
	}
	//-----------------------------Action Methods--------------------------
	public void populateFields(){
		resFields.add("Transaction ID");
		resFields.add("First Name");
		resFields.add("Last Name");
		resFields.add("Transaction Date");
		transFields.add("Transaction ID");
		transFields.add("First Name");
		transFields.add("Last Name");
		transFields.add("Transaction Date");
		checkinFields.add("Transaction ID");		
		checkinFields.add("First Name");
		checkinFields.add("Last Name");
		checkinFields.add("Checked In");
		rsvpReportsData.getBookingSourceType().add("Online");
		rsvpReportsData.getBookingSourceType().add("Manager");
		rsvpReportsData.getBookingSourceType().add("trackurls");
		rsvpReportsData.getAttendeeType().add("sure");
		rsvpReportsData.getAttendeeType().add("notsure");
	}
	public void populateInfo(){
		//setid=ReportsDB.getAttribSetID(eid,"EVENT");
		//respattributes=ReportsDB.getAttributes(setid);
		respattributes=ReportsDB.getCustomAttributes(eid);
		responseQuestionFields=RSVPReportsDB.getSelectedResponseQuestions(eid);
		recurringDates=ReportsDB.getRecurringDates(eid,"RSVP");
		if(recurringDates.size()>0){
			jsonObj= RSVPReportsDB.getRecurringAttendeeCount(eid);
			recurringAttendeeCountJson=jsonObj.toString();
			try{
				totalSure=jsonObj.get("totalSure").toString();
				totalNotSure=jsonObj.get("totalNotSure").toString();
				totalCheckIn=jsonObj.get("totalCheckIn").toString();
			}catch(Exception e){
				totalSure="0";
				totalNotSure="0";
				totalCheckIn="0";
			}
		}else{
			HashMap<String,String> hm=RSVPReportsDB.getTotalAttendees(eid);
			totalSure=hm.get("sure");
			totalNotSure=hm.get("notsure");
			totalCheckIn=RSVPReportsDB.getTotalCheckIns(eid);
		}
	    java.util.Date today=new java.util.Date();
        Format fm=new SimpleDateFormat("MM/dd/yyyy");
        String timenow=fm.format(today);
        rsvpReportsData.setEventStartDate(timenow);
        rsvpReportsData.setEventEndDate(timenow);
        rsvpReportsData.setCheckInStartDate(timenow);
        rsvpReportsData.setCheckInEndDate(timenow);
        trackCodes=RSVPReportsDB.getTrackCodes(eid);
	    getResponseAttributeCount();
	}
	@Override
	public String execute(){
		populateFields();
		populateInfo();
		return "success";
	}
	public String getRSVPResponseInfo(){
		//populateInfo();
		rsvpReportType="response";
		HashMap attribresponse=null;
		
		ArrayList <String>deflist=new ArrayList<String>(
				Arrays.asList("Transaction ID","Transaction Date","First Name","Last Name",
						"Email","Response","Notes","Transaction Source","Tracking URL","Event Date")); 
		
		responseQuestionFields.clear();
		for(int i=0;i<resFields.size();i++){
			if(!deflist.contains(resFields.get(i)))
				responseQuestionFields.add(resFields.get(i));      
		}
		
		
	//	if(resFields.contains("resFieldsFilter")){
		//	responseQuestionFields=RSVPReportsDB.getSelectedResponseQuestions(eid);
			//resFields.remove("resFieldsFilter");
			if(responseQuestionFields.size()>0){
				customAttribsMap=ReportsDB.getCustomAttributesMap(eid);
				if(!export)
					RSVPReportsDB.responseQuestionsFilter(responseQuestionFields, eid, customAttribsMap);
				attribresponse=ReportsDB.getResponses(eid,responseQuestionFields,"rsvpresponse");
				
				
			}
		
		if(export){
			rsvpReportsData.setDir(sortDirection);
			rsvpReportsData.setSortedkey(sortField);
		}else{
			rsvpReportsData.setDir("");
			rsvpReportsData.setSortedkey("");
		}
		rsvpResponseMap=RSVPReportsDB.getRSVPAttendeeReports(eid, attribresponse,responseQuestionFields,rsvpReportsData,export);
		resFields.add("SUR");
		resFields.add("RST");
		resFields.add("BKS");// for filtering json object in javascript
		HashMap h=RSVPReportsJsonHelper.getRSVPResponseJson(resFields, rsvpResponseMap, responseQuestionFields, customAttribsMap);
		
		msg=h.get("msg")+"";
	    //ArrayList chngresFields=(ArrayList)h.get("resFields"); //commented on 19th Nov 2015 while i18n
      if(export){
    	  resFields.remove("SUR");
    	  resFields.remove("RST");
    	  resFields.remove("BKS");
			//excel = RsvpExcelExport.exportToExcel(rsvpResponseMap,chngresFields,"rsvp",exporttype,customAttribsMap,rsvpReportsColumnMap); //commented on 19th Nov 2015 while i18n
    	  excel = RsvpExcelExport.exportToExcel(rsvpResponseMap,resFields,"rsvp",exporttype,customAttribsMap,rsvpReportsColumnMap,rsvpEbeeI18nMapping);
			if(exporttype.equals("excel"))
				return "exporttoexcel";
			else if(exporttype.equals("csv"))
				return "exporttocsv";
		}
		
		return "json";
	}
	public String getRSVPCheckedInReportsInfo(){
		System.out.println("Rsv checkidin");
		//populateInfo();
		rsvpReportType="checkedin";
		rsvpReportsData.setRsvpreportsradio("checkinreports");
		System.out.println("sortDirection::c:"+sortDirection);
		System.out.println("sortField::c:"+sortField);
		if(export)
		{rsvpReportsData.setDir(sortDirection);
		rsvpReportsData.setSortedkey(sortField);
		}else{rsvpReportsData.setDir("");
		rsvpReportsData.setSortedkey("");}
		rsvpcheckedinReportsMap=RSVPReportsDB.getRSVPCheckedInReports(eid,rsvpReportsData);
		//System.out.println("rsvpcheckedinReportsMap:::::::"+rsvpcheckedinReportsMap);
		  HashMap h=new HashMap();
		  checkinFields.add("AK");
	      msg=RSVPReportsJsonHelper.getRSVPCheckedJson(checkinFields,rsvpcheckedinReportsMap); 
			if(export){
				checkinFields.remove("AK");
				excel = RsvpExcelExport.exportToExcel(rsvpcheckedinReportsMap,checkinFields,"rsvpcheck",exporttype,customAttribsMap,rsvpReportsColumnMap,rsvpEbeeI18nMapping);
				if(exporttype.equals("excel"))
					return "exporttoexcel";
				else if(exporttype.equals("csv"))
					return "exporttocsv";
			}
			return "json";
		
		
	}
	public String getResponseQuestions(){
		populateInfo();
		responseQuestionFields=RSVPReportsDB.getSelectedResponseQuestions(eid);
		return "responsequestions";
	}
	public String updateresponseQuestionsFilter(){
		RSVPReportsDB.responseQuestionsFilter(responseQuestionFields, eid, attributeFilterMap);
		return "ajaxmsg";
	}
	public String getResponseAttributeCount(){
		resattribcount=RSVPReportsDB.getResponseAttribsCount(eid); 
		msgKey=resattribcount;
		return "count";
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
	
	static{
		rsvpReportsColumnMap.put("Transaction ID", "rep.disflds.trans.id.lbl");
		rsvpReportsColumnMap.put("Transaction Date", "rep.disflds.trans.date.lbl");
		rsvpReportsColumnMap.put("First Name", "rep.disflds.first.name.lbl");
		rsvpReportsColumnMap.put("Last Name", "rep.disflds.last.name.lbl");
		rsvpReportsColumnMap.put("Email", "rep.disflds.email.lbl");
		rsvpReportsColumnMap.put("Response", "rep.disflds.response.lbl");
		rsvpReportsColumnMap.put("Notes", "rep.disflds.notes.lbl");
		rsvpReportsColumnMap.put("Transaction Source", "rep.disflds.trans.source.lbl");
		rsvpReportsColumnMap.put("Tracking URL", "rep.disflds.tracking.url.lbl");
		rsvpReportsColumnMap.put("Event Date", "rep.disflds.event.date.lbl");
		rsvpReportsColumnMap.put("Checked In", "rep.disflds.checked.in.lbl");
		rsvpReportsColumnMap.put("Check In Time", "rep.disflds.checkin.time.lbl");
		rsvpReportsColumnMap.put("Scan ID", "rep.disflds.scan.id.lbl");
		rsvpReportsColumnMap.put("Attendee Key", "rep.disflds.attendee.key.lbl");
		
		rsvpEbeeI18nMapping.put("Attending", "rsvps.attendeing.lbl");
		rsvpEbeeI18nMapping.put("Online", "rep.disflds.online.lbl");
		rsvpEbeeI18nMapping.put("Manager", "rep.disflds.manager.lbl");
		rsvpEbeeI18nMapping.put("Not Sure", "rsvps.not.sure.lbl");
		rsvpEbeeI18nMapping.put("Not Attending", "rsvps.not.attending.lbl");
	}
}