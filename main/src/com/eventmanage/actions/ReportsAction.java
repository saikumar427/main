package com.eventmanage.actions;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONObject;


import com.event.beans.ReportsData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.ReportsDB;
import com.eventbee.beans.Entity;
import com.eventbee.util.RandomStringUUID;
import com.eventmanage.helpers.ExportToExcelHelper;
import com.eventmanage.helpers.ReportsJsonHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class ReportsAction extends ActionSupport implements Preparable,ValidationAware{
	private static final long serialVersionUID = -3773514102198232135L;
	public static final HashMap<String, String> reportsColumnMap=new HashMap<String, String>();
	public static final HashMap<String, String> ebeeI18nMapping=new HashMap<String, String>();
	private String eid="";
	private String setid="";
	private ArrayList Fields=new ArrayList();
	private ArrayList attFields=new ArrayList();
	private ArrayList checkinFields=new ArrayList();
	private ArrayList customattributes=new ArrayList();
	private ArrayList tickets=new ArrayList();
	private ArrayList bookingSource=new ArrayList();	
	private ArrayList paymentTypes=new ArrayList();
	private ReportsData reportsData=new ReportsData();
	private ArrayList<HashMap<String,String>> attendeeMap=new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String,String>> transactionMap=new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String,String>> checkedinReportsMap=new ArrayList<HashMap<String,String>>();
	private String selindex="";
	private String attselindex="";
	private String typeindex="";
	private String transactiontypeindex="";
	private String checkinreportsindex="";
	private String reportsradio="";
	private String sYear="";
	private String sMonth="";
	private String sDay="";
	private String eYear="";
	private String eMonth="";
	private String eDay="";	
	private String paymentstaus="";
	private ArrayList<String> buyerAttribList=null;
	private ArrayList attQuestionFields=new ArrayList();
	private ArrayList buyerQuestionFields=new ArrayList();
	private String attAttributeCount="";
	private String buyerAttributeCount="";
	private String msgKey="''";
	private String configValue;
	private String msg="";
	private boolean export;
	private String sortDirection="";
	private String sortField="";
	ArrayList fieldNames=new ArrayList();
	private String excel="";
	private String exporttype="";
	private String paymentApprovalType="";
	private int pendingApproval=0;
	private String pendingApprovalJson="''";
	private JSONObject jsonObj;
	private int recPendingAppSize=0;
	private String currencySymbol="";
	private HashMap<String,String> customAttribsMap=new HashMap<String, String>();
	private HashMap<String,String> attributeFilterMap=new HashMap<String, String>();
	private String otherCCPFee="";
	private ArrayList dates=new ArrayList();
	private ArrayList attndtickets=new ArrayList();
	private String totalAttendees="";
	private String totalSales="";
	private String totalCheckIns="";
	private HashMap<String,String> optionLabelMap=new HashMap<String, String>();
	private HashMap<String,ArrayList<Entity>> subQuestionsMap=new HashMap<String, ArrayList<Entity>>();
	private HashMap<String,ArrayList> attribOptionMap=new HashMap<String,ArrayList>();
	private HashMap<String,String> buyerOptionLabelMap=new HashMap<String, String>();
	private HashMap<String,ArrayList<Entity>> buyerSubQuestionsMap=new HashMap<String, ArrayList<Entity>>();
	private HashMap<String,ArrayList> buyerAttribOptionMap=new HashMap<String,ArrayList>();
	private String purpose="";
	
	
	public ArrayList getPaymentTypes() {
		return paymentTypes;
	}
	public void setPaymentTypes(ArrayList paymentTypes) {
		this.paymentTypes = paymentTypes;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public HashMap<String, String> getOptionLabelMap() {
		return optionLabelMap;
	}
	public void setOptionLabelMap(HashMap<String, String> optionLabelMap) {
		this.optionLabelMap = optionLabelMap;
	}
	public HashMap<String, ArrayList<Entity>> getSubQuestionsMap() {
		return subQuestionsMap;
	}
	public void setSubQuestionsMap(
			HashMap<String, ArrayList<Entity>> subQuestionsMap) {
		this.subQuestionsMap = subQuestionsMap;
	}
	public HashMap<String, ArrayList> getAttribOptionMap() {
		return attribOptionMap;
	}
	public void setAttribOptionMap(HashMap<String, ArrayList> attribOptionMap) {
		this.attribOptionMap = attribOptionMap;
	}
	public HashMap<String, String> getBuyerOptionLabelMap() {
		return buyerOptionLabelMap;
	}
	public void setBuyerOptionLabelMap(HashMap<String, String> buyerOptionLabelMap) {
		this.buyerOptionLabelMap = buyerOptionLabelMap;
	}
	public HashMap<String, ArrayList<Entity>> getBuyerSubQuestionsMap() {
		return buyerSubQuestionsMap;
	}
	public void setBuyerSubQuestionsMap(
			HashMap<String, ArrayList<Entity>> buyerSubQuestionsMap) {
		this.buyerSubQuestionsMap = buyerSubQuestionsMap;
	}
	public HashMap<String, ArrayList> getBuyerAttribOptionMap() {
		return buyerAttribOptionMap;
	}
	public void setBuyerAttribOptionMap(
			HashMap<String, ArrayList> buyerAttribOptionMap) {
		this.buyerAttribOptionMap = buyerAttribOptionMap;
	}
	public String getTotalAttendees() {
		return totalAttendees;
	}
	public void setTotalAttendees(String totalAttendees) {
		this.totalAttendees = totalAttendees;
	}
	public String getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(String totalSales) {
		this.totalSales = totalSales;
	}
	
	public String getTotalCheckIns() {
		return totalCheckIns;
	}
	public void setTotalCheckIns(String totalCheckIns) {
		this.totalCheckIns = totalCheckIns;
	}
	public ArrayList getAttndtickets() {
		return attndtickets;
	}
	public void setAttndtickets(ArrayList attndtickets) {
		this.attndtickets = attndtickets;
	}
	
	public String getExporttype() {
		return exporttype;
	}
	public void setExporttype(String exporttype) {
		this.exporttype = exporttype;
	}
	public String getExcel() {
		return excel;
	}
	public void setExcel(String excel) {
		this.excel = excel;
	}
	public ArrayList getFieldNames() {
		return fieldNames;
	}
	public void setFieldNames(ArrayList fieldNames) {
		this.fieldNames = fieldNames;
	}
	public String getTransactiontypeindex() {
		return transactiontypeindex;
	}
	public void setTransactiontypeindex(String transactiontypeindex) {
		this.transactiontypeindex = transactiontypeindex;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getAttAttributeCount() {
		return attAttributeCount;
	}
	public void setAttAttributeCount(String attAttributeCount) {
		this.attAttributeCount = attAttributeCount;
	}
	public String getBuyerAttributeCount() {
		return buyerAttributeCount;
	}
	public void setBuyerAttributeCount(String buyerAttributeCount) {
		this.buyerAttributeCount = buyerAttributeCount;
	}
	public ArrayList getBuyerQuestionFields() {
		return buyerQuestionFields;
	}
	public void setBuyerQuestionFields(ArrayList buyerQuestionFields) {
		this.buyerQuestionFields = buyerQuestionFields;
	}
	public ArrayList getAttQuestionFields() {
		return attQuestionFields;
	}
	public void setAttQuestionFields(ArrayList attQuestionFields) {
		this.attQuestionFields = attQuestionFields;
	}
	public String getTypeindex() {
		return typeindex;
	}
	public void setTypeindex(String typeindex) {
		this.typeindex = typeindex;
	}
	public ArrayList<HashMap<String, String>> getCheckedinReportsMap() {
		return checkedinReportsMap;
	}
	public void setCheckedinReportsMap(
			ArrayList<HashMap<String, String>> checkedinReportsMap) {
		this.checkedinReportsMap = checkedinReportsMap;
	}
	public ArrayList getCheckinFields() {
		return checkinFields;
	}
	public void setCheckinFields(ArrayList checkinFields) {
		this.checkinFields = checkinFields;
	}
	public String getCheckinreportsindex() {
		return checkinreportsindex;
	}
	public void setCheckinreportsindex(String checkinreportsindex) {
		this.checkinreportsindex = checkinreportsindex;
	}
	public ArrayList<String> getBuyerAttribList() {
		return buyerAttribList;
	}
	public void setBuyerAttribList(ArrayList<String> buyerAttribList) {
		this.buyerAttribList = buyerAttribList;
	}
	
	public ArrayList getDates() {
		return dates;
	}
	public void setDates(ArrayList dates) {
		this.dates = dates;
	}
	public String getSYear() {
		return sYear;
	}
	public void setSYear(String year) {
		sYear = year;
	}
	public String getSMonth() {
		return sMonth;
	}
	public void setSMonth(String month) {
		sMonth = month;
	}
	public String getSDay() {
		return sDay;
	}
	public void setSDay(String day) {
		sDay = day;
	}
	public String getEYear() {
		return eYear;
	}
	public void setEYear(String year) {
		eYear = year;
	}
	public String getEMonth() {
		return eMonth;
	}
	public void setEMonth(String month) {
		eMonth = month;
	}
	public String getEDay() {
		return eDay;
	}
	public void setEDay(String day) {
		eDay = day;
	}
	public String getPaymentstaus() {
		return paymentstaus;
	}
	public void setPaymentstaus(String paymentstaus) {
		this.paymentstaus = paymentstaus;
	}
	
	public String getReportsradio() {
		return reportsradio;
	}
	public void setReportsradio(String reportsradio) {
		this.reportsradio = reportsradio;
	}
	
	public ArrayList<HashMap<String, String>> getTransactionMap() {
		return transactionMap;
	}
	public void setTransactionMap(ArrayList<HashMap<String, String>> transactionMap) {
		this.transactionMap = transactionMap;
		
	}
	public String getAttselindex() {
		return attselindex;
	}
	public void setAttselindex(String attselindex) {
		this.attselindex = attselindex;
	}
	
	public ArrayList<HashMap<String, String>> getAttendeeMap() {
		return attendeeMap;
	}
	public void setAttendeeMap(ArrayList<HashMap<String, String>> attendeeMap) {
		this.attendeeMap = attendeeMap;
	}
	public String getSelindex() {
		return selindex;
	}
	public void setSelindex(String selindex) {
		this.selindex = selindex;
	}
	
	public ArrayList getAttFields() {
		return attFields;
	}
	public void setAttFields(ArrayList attFields) {
		this.attFields = attFields;
	}
	public ArrayList getBookingSource() {
		return bookingSource;
	}
	public void setBookingSource(ArrayList bookingSource) {
		this.bookingSource = bookingSource;
	}
	public ReportsData getReportsData() {
		return reportsData;
	}
	public void setReportsData(ReportsData reportsData) {
		this.reportsData = reportsData;
	}
	public ArrayList getTickets() {
		return tickets;
	}
	public void setTickets(ArrayList tickets) {
		this.tickets = tickets;
	}
	public String getSetid() {
		return setid;
	}
	public void setSetid(String setid) {
		this.setid = setid;
	}
	
	public ArrayList getFields() {
		return Fields;
	}
	public void setFields(ArrayList fields) {
		Fields = fields;
	}	
	
	public ArrayList getCustomattributes() {
		return customattributes;
	}
	public void setCustomattributes(ArrayList customattributes) {
		this.customattributes = customattributes;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isExport() {
		return export;
	}
	public void setExport(boolean export) {
		this.export = export;
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

	public String getPaymentApprovalType() {
		return paymentApprovalType;
	}
	public void setPaymentApprovalType(String paymentApprovalType) {
		this.paymentApprovalType = paymentApprovalType;
	}
	public int getPendingApproval() {
		return pendingApproval;
	}
	public void setPendingApproval(int pendingApproval) {
		this.pendingApproval = pendingApproval;
	}
	public String getPendingApprovalJson() {
		return pendingApprovalJson;
	}
	public void setPendingApprovalJson(String pendingApprovalJson) {
		this.pendingApprovalJson = pendingApprovalJson;
	}
	public int getRecPendingAppSize() {
		return recPendingAppSize;
	}
	public void setRecPendingAppSize(int recPendingAppSize) {
		this.recPendingAppSize = recPendingAppSize;
	}
	public void populateReportsInfo(){
		System.out.println("populateReportsInfo(ReportsAction.java) started(eid: "+eid+"),free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		configValue= ReportsDB.getConfigValue(eid);
		tickets=ReportsDB.getTicketDetails(eid);
		attndtickets=ReportsDB.getAttendeeTickets(tickets);
		bookingSource=ReportsDB.getBookingSourceDetails(eid);
		paymentTypes=ReportsDB.getPaymetTypes();
		if(EventDB.isRecurringEvent(eid))
			dates=ReportsDB.getRecurringDates(eid,"Ticketing");
		paymentApprovalType=ReportsDB.getPaymentApprovalType(eid);
		if(dates.size() > 0){
			jsonObj= ReportsDB.getRecurringPendingApprovalCount(eid);
			try{
				recPendingAppSize = Integer.parseInt(jsonObj.get("recPendingAppSize").toString());
				totalAttendees=jsonObj.get("totalAttendees").toString();
				totalSales=jsonObj.get("totalSales").toString();
				totalCheckIns=jsonObj.get("totalCheckIns").toString();
			}catch(Exception e){
				recPendingAppSize=0;totalAttendees="0";totalSales="0";totalCheckIns="0";
			}
			pendingApprovalJson=jsonObj.toString();
		}		
		else{
			pendingApproval=Integer.parseInt(ReportsDB.getPendingApprovalCount(eid));
			totalAttendees=ReportsDB.getTotalAttendees(eid);
			totalSales=ReportsDB.getTotalSales(eid);
			totalCheckIns=ReportsDB.getTotalCheckIns(eid);
		}
		otherCCPFee=ReportsDB.checkOtherCCPFee(eid);
		if(otherCCPFee != null) otherCCPFee="Yes";
	    java.util.Date today=new java.util.Date();
        Format fm=new SimpleDateFormat("MM/dd/yyyy");
        String timenow=fm.format(today);
        reportsData.setEventStartDate(timenow);
        reportsData.setEventEndDate(timenow);
        reportsData.setEventTranStartDate(timenow);
        reportsData.setEventTranEndDate(timenow);
        reportsData.setEventTranEndDate(timenow);
        reportsData.setChkInStartDate(timenow);
        reportsData.setChkInEndDate(timenow);
		reportsData.setIsVolumeSale(ReportsDB.isVolumeSale(eid));
		attAttributeCount=ReportsDB.attendeeAttribsCount(eid); 
		buyerAttributeCount=ReportsDB.buyerAttribsCount(eid); 
		System.out.println("populateReportsInfo(ReportsAction.java) ended(eid: "+eid+"),free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
	}
	public void populateFields(){
		attFields.add("FN");
		attFields.add("TN");
		attFields.add("LN");
		attFields.add("TID");
		attFields.add("TD");
		
		Fields.add("TID");
		Fields.add("TD");
		Fields.add("FN");
		Fields.add("LN");
		/*Fields.add("TN");
		Fields.add("SF");
		Fields.add("CCPF");
		Fields.add("TTC");*/
		Fields.add("TNet");		
		/*Fields.add("TP");
		Fields.add("TC");
		Fields.add("PT");
		Fields.add("Di");
		Fields.add("ESF");
		Fields.add("ECCF");
		Fields.add("NTSC");
		Fields.add("CSF");*/
		Fields.add("Bal");
		/*Fields.add("OCCF");
		Fields.add("RA");*/
		
		checkinFields.add("TID");		
		checkinFields.add("FN");
		checkinFields.add("LN");
		checkinFields.add("CIS");
		checkinFields.add("AK");
	}
	public String execute(){
		populateReportsInfo();
		populateFields();
		currencySymbol=EventDB.getCurrencySymbol(eid);
		//customattributes=ReportsDB.getCustomAttributes(eid);
		//buyerAttribList=ReportsDB.getBuyerAttributes(eid);
		attQuestionFields=ReportsDB.getSelectedAttendeeQuestions(eid);
		buyerQuestionFields=ReportsDB.getSelectedBuyerQuestions(eid);
		try{
			HashMap allmap=ReportsDB.getAllCustomAttributesMap(eid);
			customattributes=(ArrayList)allmap.get("attriblist");
			subQuestionsMap=(HashMap<String,ArrayList<Entity>>)allmap.get("subquestionofmap");
			optionLabelMap=(HashMap)allmap.get("optionlabelmap");
			attribOptionMap=(HashMap<String,ArrayList>)allmap.get("attriboptionmap");
			
			HashMap allbuyermap =ReportsDB.getAllBuyerAttributesMap(eid);
			buyerAttribList=(ArrayList)allbuyermap.get("buyerattriblist");
			buyerSubQuestionsMap=(HashMap<String,ArrayList<Entity>>)allbuyermap.get("buyersubquestionofmap");
			buyerOptionLabelMap=(HashMap)allbuyermap.get("buyeroptionlabelmap");
			buyerAttribOptionMap=(HashMap<String,ArrayList>)allbuyermap.get("buyerattriboptionmap");
			
		}catch(Exception e){
			System.out.println("ReportsAction execute: "+e.getMessage());
		}
		return "success";
	}
	
	public String getAttendeeInfo(){
		
		String uuid = RandomStringUUID.getUUID();
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" current JVM Total memory(ReportsAction.java): "+(Runtime.getRuntime().totalMemory()/(1024*1024)));
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" started, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		ArrayList <String>deflist=new ArrayList<String>(
				Arrays.asList("TD","TID","FN","LN","TN","TP","BE","Em","Ph","ON","AK","BN","SC","BP","ED"));   
		long starttime=System.currentTimeMillis();		
		
		if("2".equals(reportsData.getAttDispFields())){
			for(int i=0;i<attFields.size();i++){
				if(!deflist.contains(attFields.get(i)))
					attQuestionFields.add(attFields.get(i));      
			}
			customAttribsMap=ReportsDB.getCustomAttributesMap(eid,export);
		   	for(int i=0;i<attQuestionFields.size();i++){
		   		String key=(String)attQuestionFields.get(i);
		   		if(!customAttribsMap.containsKey(key)){
		   			attFields.remove(key);
		   			attQuestionFields.remove(key);
		   			i=-1;
		   		}
		   	}
		}
		
		reportsData.setReportsradio(reportsradio);
		reportsData.setAttselindex(attselindex);
		reportsData.setTypeindex(typeindex);
		reportsData.setTransactiontypeindex(transactiontypeindex);
		reportsData.setReportType("attendee");
		reportsData.setSelindex(selindex);     
		fieldNames.addAll(attFields);
		System.out.println("attQuestionFields.size: "+attQuestionFields.size()+" fieldNames: "+fieldNames);
		HashMap attribresponse=null;
			System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" calling ReportsDB.getSelectedAttendeeQuestions, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");	
			long questionfieldssttime=System.currentTimeMillis();
			long questionfieldstotaltime=(System.currentTimeMillis())-questionfieldssttime;
			System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" after ReportsDB.getSelectedAttendeeQuestions, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
			System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken to getSelectedAttendeeQuestions: "+questionfieldstotaltime+" MS");
			if(!export)
				ReportsDB.insertIntoCustomQuestions(customAttribsMap,attQuestionFields,eid,"attendee");
			if(attQuestionFields.size()>0){
				System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" calling ReportsDB.customAttribsMap, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");	
				long customAttribsMapsttime=System.currentTimeMillis();
			//	customAttribsMap=ReportsDB.getCustomAttributesMap(eid);
				long customAttribsMaptotaltime=(System.currentTimeMillis())-customAttribsMapsttime;
				System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" after ReportsDB.customAttribsMap, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
				System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken to customAttribsMap: "+customAttribsMaptotaltime+" MS");
				System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" before calling ReportsDB.getResponses, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
				long attribresponsesttime=System.currentTimeMillis();
				attribresponse = ReportsDB.getResponses(eid,attQuestionFields,"attendee");
				long attribresponsetotaltime=(System.currentTimeMillis())-attribresponsesttime;
				System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" after ReportsDB.getResponses,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
				System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken to getResponses: "+attribresponsetotaltime+" MS");
		}
		if("".equals(sortField)) sortField=(String)attFields.get(0);
			
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" before calling ReportsDB.getTicketInfo,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long ticketsinfosttime=System.currentTimeMillis();
		HashMap ticketsinfo = ReportsDB.getTicketInfo(eid);
		long ticketsinfototaltime=(System.currentTimeMillis())-ticketsinfosttime;
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" after ReportsDB.getTicketInfo,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken to getTicketInfo: "+ticketsinfototaltime+" MS");
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" before calling ReportsDB.getAttendeeListInfo,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long attendeeMapstarttime=System.currentTimeMillis();
		attendeeMap=ReportsDB.getAttendeeListInfo(eid,attribresponse,attQuestionFields,attselindex, reportsData,ticketsinfo,typeindex,sortField,sortDirection,export);
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" after ReportsDB.getAttendeeListInfo,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long attendeeMaptotaltime=(System.currentTimeMillis())-attendeeMapstarttime;
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken to getAttendeeListInfo: "+attendeeMaptotaltime+" MS");
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" before calling ReportsJsonHelper.getAttReportsJson,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long jsonstarttime=System.currentTimeMillis();
		msg=ReportsJsonHelper.getAttReportsJson(attendeeMap, fieldNames,customAttribsMap,currencySymbol,reportsColumnMap);
		long jsontotaltime=(System.currentTimeMillis())-jsonstarttime;
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" after calling ReportsJsonHelper.getAttReportsJson (eid: "+eid+"),free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken to getAttReportsJson: "+jsontotaltime+" MS");
		long totaltime=(System.currentTimeMillis())-starttime;
		System.out.println("Attendee_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken to get ** Attendee Reports ** (size :"+attendeeMap.size()+"): "+totaltime+" MS");
		if(export){
			excel = ExportToExcelHelper.exportToExcel(attendeeMap,fieldNames,"attendee",exporttype,customAttribsMap,reportsColumnMap,ebeeI18nMapping);
			if(exporttype.equals("excel"))
				return "exporttoexcel";
			else if(exporttype.equals("csv"))
				return "exporttocsv";
		}
		return "reportsjson";
	}
	public String getTransactionInfo(){
		String uuid = RandomStringUUID.getUUID();
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" current JVM Total memory(ReportsAction.java): "+(Runtime.getRuntime().totalMemory()/(1024*1024)));
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" started, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long starttime=System.currentTimeMillis();

		HashMap buyerResponses=null;
			System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" before calling ReportsDB.getSelectedBuyerQuestions,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
			long buyerQuestionstarttime=System.currentTimeMillis();
			long buyerQuestiontotaltime=(System.currentTimeMillis())-buyerQuestionstarttime;
			System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" after ReportsDB.getSelectedBuyerQuestions,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
			System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken execute getSelectedBuyerQuestions: "+buyerQuestiontotaltime+" MS");
			customAttribsMap=ReportsDB.getBuyerAttributesMap(eid,export);
			ArrayList <String>deflist=new ArrayList<String>(
			Arrays.asList("TD","TID","FN","LN","TN","TP","TC","PT","CCPF","SF","TTC","TNet","ON","St","Em","Ph","TU","So","Di","DC","EPID","CSF","No","NTSC","PID","VTSC","ESF","ECCF","Bal","OCCF","RA","ED"));
			if("2".equals(reportsData.getSalesDispFields())){
				for(int i=0;i<Fields.size();i++){
					if(!deflist.contains(Fields.get(i)))
						buyerQuestionFields.add(Fields.get(i));
				}
				
				for(int i=0;i<buyerQuestionFields.size();i++)
				{
					String key=(String)buyerQuestionFields.get(i);
					if(!customAttribsMap.containsKey(key))
					{
						buyerQuestionFields.remove(key);
						Fields.remove(key);
						i=-1;
					}
				}
			}
			reportsData.setReportsradio(reportsradio);
			reportsData.setSelindex(selindex);
			reportsData.setTypeindex(typeindex);
			reportsData.setTransactiontypeindex(transactiontypeindex);
			reportsData.setReportType("registration");
			reportsData.setAttselindex(attselindex);		
			reportsData.setPaymentstaus(paymentstaus);
			fieldNames.addAll(Fields);
			System.out.println("buyerQuestionFields.size: "+buyerQuestionFields.size()+" fieldNames: "+fieldNames);
			if(!export)
				ReportsDB.insertIntoCustomQuestions(customAttribsMap,buyerQuestionFields,eid,"buyer");
			if(buyerQuestionFields.size()>0){
				System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" before calling ReportsDB.getBuyerAttributesMap,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
				long CustomAttributesMapstarttime=System.currentTimeMillis();
			//	customAttribsMap=ReportsDB.getBuyerAttributesMap(eid);
				long CustomAttributesMaptotaltime=(System.currentTimeMillis())-CustomAttributesMapstarttime;
				System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" after ReportsDB.getBuyerAttributesMap,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
				System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" eid: "+eid+": Total time taken execute getBuyerAttributesMap: "+CustomAttributesMaptotaltime+" MS");
				System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" before calling ReportsDB.getbuyerAttribresponses,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
				long byerResponsesstarttime=System.currentTimeMillis();
				buyerResponses=ReportsDB.getbuyerAttribresponses(eid,buyerQuestionFields);
				long byerResponsestotaltime=(System.currentTimeMillis())-byerResponsesstarttime;
				System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" after ReportsDB.getbuyerAttribresponses,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
				System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken execute getbuyerAttribresponses: "+byerResponsestotaltime+" MS");
			}
			
		if("".equals(sortField)) sortField=(String)Fields.get(0);
		
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" before calling ReportsDB.getTicketInfo,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long ticketsinfostarttime=System.currentTimeMillis();
		HashMap ticketsinfo = ReportsDB.getTicketInfo(eid);
		long ticketsinfototaltime=(System.currentTimeMillis())-ticketsinfostarttime;
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" after ReportsDB.getTicketInfo,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken execute getTicketInfo: "+ticketsinfototaltime+" MS");
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" before calling ReportsDB.getAllReports");
		long transactionMapstarttime=System.currentTimeMillis();
		
		transactionMap=ReportsDB.getAllReports(eid,selindex,buyerResponses,buyerQuestionFields,ticketsinfo,reportsData,transactiontypeindex,sortField,sortDirection,fieldNames,export);
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" after ReportsDB.getAllReports,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long transactionMaptotaltime=(System.currentTimeMillis())-transactionMapstarttime;
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken to execute getAllReports: "+transactionMaptotaltime+" MS");
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" before calling ReportsJsonHelper.getTransactionReportsJson,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long jsonstarttime=System.currentTimeMillis();
		msg=ReportsJsonHelper.getTransactionReportsJson(transactionMap, fieldNames, customAttribsMap,currencySymbol,reportsColumnMap);
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" after calling ReportsJsonHelper.getTransactionReportsJson,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long jsontotaltime=(System.currentTimeMillis())-jsonstarttime;
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken to get TransactionReportsJson: "+jsontotaltime+" MS");
		long totaltime=(System.currentTimeMillis())-starttime;
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: "+uuid+" Total time taken to get ** Transaction Reports ** (size: "+transactionMap.size()+"): "+totaltime+" MS");
		if(export){
			excel = ExportToExcelHelper.exportToExcel(transactionMap,fieldNames,"transaction",exporttype,customAttribsMap,reportsColumnMap,ebeeI18nMapping);
			if(exporttype.equals("excel"))
				return "exporttoexcel";
			else if(exporttype.equals("csv"))
				return "exporttocsv";
		}
		return "reportsjson";
	}
	public String getCheckedInReportsInfo(){
		
		reportsData.setReportsradio(reportsradio);
		reportsData.setCheckinreportsindex(checkinreportsindex);
		reportsData.setReportType("checkin");
		reportsData.setSelindex(selindex);
		reportsData.setTypeindex(typeindex);
		reportsData.setTransactiontypeindex(transactiontypeindex);
		reportsData.setAttselindex(attselindex);
		//populateReportsInfo();
		if("".equals(sortField)) sortField=(String)checkinFields.get(0);
		checkedinReportsMap=ReportsDB.getCheckedInReports(eid,checkinreportsindex,reportsData,sortField,sortDirection,export);
		msg=ReportsJsonHelper.getCheckInReportsJson(checkedinReportsMap, checkinFields, reportsColumnMap);
		if(export){
			excel = ExportToExcelHelper.exportToExcel(checkedinReportsMap,checkinFields,"checkin",exporttype,customAttribsMap,reportsColumnMap,ebeeI18nMapping);
			if(exporttype.equals("excel"))
				return "exporttoexcel";
			else if(exporttype.equals("csv"))
				return "exporttocsv";
		}
		return "reportsjson";
	}
	public String attendeeQuestions(){
		System.out.println("into attendee questions ");
		customattributes=ReportsDB.getCustomAttributes(eid);
		attQuestionFields=ReportsDB.getSelectedAttendeeQuestions(eid);
		return "attendeeQuestions";
	}
	public String updateattendeeQuestionsFilter(){
		ReportsDB.attendeeQuestionsFilter(attQuestionFields, eid,attributeFilterMap);
		attAttributeCount=ReportsDB.attendeeAttribsCount(eid); 
		msgKey=attAttributeCount;
		return "count";
	}
	public String buyerQuestions(){
		System.out.println("into buyer questions");
		buyerQuestionFields=ReportsDB.getSelectedBuyerQuestions(eid);
		buyerAttribList=ReportsDB.getBuyerAttributes(eid);
		return "buyerQuestions";
	}
	public String updateBuyerQuestionsFilter(){
		ReportsDB.buyerQuestionsFilter(buyerQuestionFields, eid,attributeFilterMap);
		attAttributeCount=ReportsDB.buyerAttribsCount(eid); 
		msgKey=attAttributeCount;
		return "count";
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String getCurrencySymbol() {
		return currencySymbol;
	}
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}
	
	public HashMap<String, String> getCustomAttribsMap() {
		return customAttribsMap;
	}
	public void setCustomAttribsMap(HashMap<String, String> customAttribsMap) {
		this.customAttribsMap = customAttribsMap;
	}
	
	static{
		reportsColumnMap.put("TID", "rep.disflds.trans.id.lbl");
		reportsColumnMap.put("TD", "rep.disflds.trans.date.lbl");
		reportsColumnMap.put("FN", "rep.disflds.first.name.lbl");
		reportsColumnMap.put("LN", "rep.disflds.last.name.lbl");
		reportsColumnMap.put("Em", "rep.disflds.email.lbl");
		reportsColumnMap.put("Ph", "rep.disflds.phone.lbl");
		reportsColumnMap.put("TN", "rep.tckt.name.lbl");
		reportsColumnMap.put("TP", "rep.disflds.ticket.price.lbl");
		reportsColumnMap.put("ON", "rep.disflds.order.number.lbl");
		reportsColumnMap.put("AK", "rep.disflds.attendee.key.lbl");
		reportsColumnMap.put("BN", "rep.disflds.buyer.name.lbl");
		reportsColumnMap.put("BE", "rep.disflds.buyer.email.lbl");
		reportsColumnMap.put("BP", "rep.disflds.buyer.phone.lbl");
		reportsColumnMap.put("SC", "rep.disflds.seat.code.lbl");
		
		reportsColumnMap.put("St", "rep.disflds.status.lbl");
		reportsColumnMap.put("TU", "rep.disflds.tracking.url.lbl");
		reportsColumnMap.put("So", "rep.disflds.source.lbl");
		reportsColumnMap.put("Di", "rep.dscnt.lbl");
		reportsColumnMap.put("DC", "rep.disflds.discount.code.lbl");
		reportsColumnMap.put("PT", "rep.disflds.payment.type.lbl");
		reportsColumnMap.put("CCPF", "rep.disflds.cc.pro.fee.lbl");
		reportsColumnMap.put("EPID", "rep.disflds.ext.pay.id.lbl");
		reportsColumnMap.put("SF", "rep.disflds.service.fee.lbl");
		reportsColumnMap.put("CSF", "rep.disflds.collected.service.fee.lbl");
		reportsColumnMap.put("TTC", "rep.disflds.total.tkt.price.lbl");
		reportsColumnMap.put("PID", "rep.disflds.nts.partner.id.lbl");
		reportsColumnMap.put("NTSC", "rep.disflds.nts.comm.lbl");
		reportsColumnMap.put("No", "rep.disflds.notes.lbl");
		reportsColumnMap.put("TC", "rep.disflds.tickets.count.lbl");
		reportsColumnMap.put("TNet", "rep.disflds.net.tkt.price.lbl");
		
		reportsColumnMap.put("CIS", "rep.filter.checkedin.lbl");
		reportsColumnMap.put("CIT", "rep.disflds.checkin.time.lbl");
		reportsColumnMap.put("SID", "rep.disflds.scan.id.lbl");
		reportsColumnMap.put("TT", "rep.disflds.ticket.type.lbl");
		//reportsColumnMap.put("VTSC", "Volume Ticket Selling Commission");
		
		reportsColumnMap.put("ESF", "rep.disflds.ebee.service.fee.lbl");
		reportsColumnMap.put("ECCF", "rep.disflds.ebee.cc.pro.fee.lbl");
		reportsColumnMap.put("Bal", "rep.disflds.balance.lbl");
		reportsColumnMap.put("OCCF", "rep.disflds.other.cc.pro.fee.lbl");
		reportsColumnMap.put("RA", "rep.disflds.refunded.amt.lbl");
		reportsColumnMap.put("ED", "rep.disflds.event.date.lbl");
		
		ebeeI18nMapping.put("Completed","rep.trans.completed.status.lbl");
		ebeeI18nMapping.put("Pending Approval","rep.trans.pending.approval.lbl");
		ebeeI18nMapping.put("Refunded","rep.refunded.status.lbl");
		ebeeI18nMapping.put("Chargeback","rep.chargeback.status.lbl");
		ebeeI18nMapping.put("Deleted","rep.deleted.status.lbl");
		ebeeI18nMapping.put("Main URL","rep.main.url.lbl");
		ebeeI18nMapping.put("Other","rep.other.status.lbl");
		ebeeI18nMapping.put("Other - Pending Approval","rep.other.pending.paymentmethod.lbl");
		ebeeI18nMapping.put("No Payment","rep.no.payment.lbl");
		ebeeI18nMapping.put("Other - Approved","rep.other.approved.paymentmethod.lbl");
		ebeeI18nMapping.put("Track URL","rep.track.url.lbl2");
	}

	public HashMap<String, String> getAttributeFilterMap() {
		return attributeFilterMap;
	}
	public void setAttributeFilterMap(HashMap<String, String> attributeFilterMap) {
		this.attributeFilterMap = attributeFilterMap;
	}
	public String getOtherCCPFee() {
		return otherCCPFee;
	}
	public void setOtherCCPFee(String otherCCPFee) {
		this.otherCCPFee = otherCCPFee;
	}
}
