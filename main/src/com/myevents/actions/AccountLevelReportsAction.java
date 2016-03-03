package com.myevents.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;





import com.myevents.beans.AccountLevelReportsData;
import com.myevents.dbhelpers.AccountLevelReportsDB;
import com.event.dbhelpers.EventDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.eventbee.general.GenUtil;
import com.eventbee.payments.dbhelpers.CardProcessorDB;
import com.eventbee.util.RandomStringUUID;
import com.myevents.helpers.ExportToExcelHelper;
import com.myevents.helpers.AccountLevelReportsJsonHelper;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;


public class AccountLevelReportsAction extends MyEventsActionWrapper implements Preparable,ValidationAware{
	private static final long serialVersionUID = -3773514102198232135L;
	public static final HashMap<String,String> reportsColumnMap=new HashMap<String, String>();
	private String eid="";
	private String setid="";
	private boolean errorsExists;
	private ArrayList Fields=new ArrayList();
	private ArrayList attFields=new ArrayList();
	private ArrayList tickets=new ArrayList();
	private ArrayList bookingSource=new ArrayList();	
	private AccountLevelReportsData reportsData=new AccountLevelReportsData();
	private ArrayList<HashMap<String,String>> attendeeMap=new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String,String>> transactionMap=new ArrayList<HashMap<String,String>>();
	private String selindex="";
	private String attselindex="";
	private String typeindex="";
	private String transactiontypeindex="";
    private String chkditem="";
	private String reportsradio="";
	private String paymentstaus="";
    private boolean export;
	private String sortDirection="";
	private String sortField="";
	ArrayList fieldNames=new ArrayList();
	private String excel="";
	private String exporttype="";
	private String paymentApprovalType="";
	private int pendingApproval=0;
	private String pendingApprovalJson="''";
	private int recPendingAppSize=0;
	private String currencySymbol="";
	private HashMap<String,String> attributeFilterMap=new HashMap<String, String>();
	private String otherCCPFee="";
	private String msgType = "accounlevelreports";
	private String isEntryExists="yes";
	private HashMap<String,String> mgrSelectedDates=new HashMap<String,String>();
   /* private ArrayList<Entity> eventList=new ArrayList<Entity>(); */
	private JSONArray eventList;
    private HashMap<String,String> customAttribsMap=new HashMap<String,String>();
    private String eventlist="";
    private String eventstartdate="";
    private String eventenddate="";
    private String rtype="";
    private String msg="";
    private String ccstatus="";
	public String getCcstatus() {
		return ccstatus;
	}
	public void setCcstatus(String ccstatus) {
		this.ccstatus = ccstatus;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getEventstartdate() {
		return eventstartdate;
	}
	public void setEventstartdate(String eventstartdate) {
		this.eventstartdate = eventstartdate;
	}
	public String getEventenddate() {
		return eventenddate;
	}
	public void setEventenddate(String eventenddate) {
		this.eventenddate = eventenddate;
	}
	public String getEventlist() {
		return eventlist;
	}
	public void setEventlist(String eventlist) {
		this.eventlist = eventlist;
	}
	public String getIsEntryExists() {
		return isEntryExists;
	}
	public void setIsEntryExists(String isEntryExists) {
		this.isEntryExists = isEntryExists;
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
	
	public String getTypeindex() {
		return typeindex;
	}
	public void setTypeindex(String typeindex) {
		this.typeindex = typeindex;
	}
	
	private ArrayList dates=new ArrayList();
	public ArrayList getDates() {
		return dates;
	}
	public void setDates(ArrayList dates) {
		this.dates = dates;
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
	public AccountLevelReportsData getReportsData() {
		return reportsData;
	}
	public void setReportsData(AccountLevelReportsData reportsData) {
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
	
	
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
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
		/*System.out.println("populateReportsInfo(ReportsAction.java) started(eid: "+eid+"),free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		bookingSource=AccountLevelReportsDB.getBookingSourceDetails(eid);
		dates=AccountLevelReportsDB.getRecurringEvents(eid);
		paymentApprovalType=AccountLevelReportsDB.getPaymentApprovalType(eid);
		if(dates.size() > 0){
			jsonObj= AccountLevelReportsDB.getRecurringPendingApprovalCount(eid);
			recPendingAppSize = jsonObj.length();
			pendingApprovalJson=jsonObj.toString();
		}
		
		else
			pendingApproval=Integer.parseInt(AccountLevelReportsDB.getPendingApprovalCount(eid));
		otherCCPFee=AccountLevelReportsDB.checkOtherCCPFee(eid);
		if(otherCCPFee != null) otherCCPFee="Yes";*/
		//String eventlist="";
		//eventlist=AccountLevelReportsDB.getManagerEvents(mgrId);
		bookingSource=AccountLevelReportsDB.getBookingSourceDetails(mgrId);
		int year=0;
		java.util.Date d=new java.util.Date();
	    year=d.getYear()+1900;
	    String syear=reportsData.getSYear();
	    String attsyear=reportsData.getAttsYear();
	    if("".equals(syear)) syear="1";
	    if("".equals(attsyear)) attsyear="1";
	    String eyear=reportsData.getEYear();
	    String atteyear=reportsData.getAtteYear();
	    if("".equals(eyear)) eyear="1";
	    if("".equals(atteyear)) atteyear="1";
	    int displayStartYear=Integer.parseInt(syear);
	    int displayEndYear=Integer.parseInt(eyear);
	    int attdisplayStartYear=Integer.parseInt(attsyear);
	    int attdisplayEndYear=Integer.parseInt(atteyear);
	    System.out.println("populateReportsInfo(ReportsAction.java) ended(eid: "+eid+"),free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
	}
	public void populateFields(){
		attFields.add("FN");
		attFields.add("TN");
		attFields.add("LN");
		attFields.add("TID");
		attFields.add("TD");
		attFields.add("EN");
		
		
		Fields.add("TID");
		Fields.add("TD");
		Fields.add("FN");
		Fields.add("LN");
		Fields.add("TN");
		Fields.add("SF");
		Fields.add("CCPF");
		Fields.add("TTC");
		Fields.add("TNet");		
		Fields.add("TP");
		Fields.add("TC");
		Fields.add("PT");
		Fields.add("Di");
		Fields.add("ESF");
		Fields.add("ECCF");
		Fields.add("NTSC");
		Fields.add("CSF");
		Fields.add("Bal");
		Fields.add("OCCF");
		Fields.add("EN");
		
		}
	public String getEvents(){
		
		populateReportsInfo();
		populateFields();
		if("dates".equals(chkditem)){
			
		  boolean val=GetEventDatesValidate();
		  	mgrSelectedDates.put("startdate", reportsData.getStartdate().trim());
			mgrSelectedDates.put("enddate", reportsData.getEnddate().trim());
			eventList=AccountLevelReportsDB.getAllEvents(mgrId,mgrSelectedDates);
			
		  if(!val)
			return "alleventsjson";
		  
		    if(eventList.length()<=0)
		       	addFieldError("reportsData.size","No events found");
		}else if("all".equals(chkditem)){
			
			mgrSelectedDates.put("startdate","");
			mgrSelectedDates.put("enddate","");
	        eventList=AccountLevelReportsDB.getAllEvents(mgrId,mgrSelectedDates);
	        
	        if(eventList.length()<=0)
			   addFieldError("reportsData.size","No events found");
		}	   
		return "alleventsjson";
	}
	
	public String execute(){
		
		System.out.println("in AccountLevelReportsAction execute mgrId: "+mgrId);
		String chargeStatus=EventDB.getChargeStatus(mgrId);
		if("Y".equalsIgnoreCase(chargeStatus)) 
			return "error";
		/*String mgrccstatus="";
		mgrccstatus=AccountLevelReportsDB.getMgrAccountCCStatus(mgrId);
		if("donot_askcard".equals(mgrccstatus)){*/
			eventList=AccountLevelReportsDB.getAllEvents(mgrId,mgrSelectedDates);
			populateReportsInfo();
			populateFields();
			return "success";
		/*}else{
			addFieldError("cardstatus",mgrccstatus);
			return "fail";
		}*/
	}
	
	public String getAllEventReports(){
		dontAskCard();
		return "success";
	}
	
	public void dontAskCard(){
		eventList=AccountLevelReportsDB.getAllEvents(mgrId,mgrSelectedDates);
		populateReportsInfo();
		populateFields();
	}
	
	public String getMgrCCStatus(){
		System.out.println("in AccountLevelReportsAction getMgrCCStatus mgrId: "+mgrId);
		Map session = ActionContext.getContext().getSession();
		String cardstatus="",cardRequired="",cardType="popup1";
		
		if(session.get("SESSION_USER")!=null && !"".equals(mgrId) && mgrId!=null && !"null".equals(mgrId))
			cardRequired=DbUtil.getVal("select name from mgr_config where name in('mgr.card.popup1.required','mgr.card.popup2.required','authorize','require_charge') and value='Y' and userid=CAST(? AS INTEGER)", new String[]{mgrId});
		
		if("require_charge".equals(cardRequired)){
			cardType="charge";
			cardstatus=cardType;
	   }else if(cardRequired != null && !"".equals(cardRequired)){
			cardstatus=CardProcessorDB.getManagerCCStatus(mgrId);
			if(cardstatus==null || "".equals(cardstatus))
				cardstatus="askcard";
			else
				cardstatus="donot_askcard";
		}else{
				cardstatus="donot_askcard";
		}
		
		JSONObject ccjson=new JSONObject();
		try{
			if("askcard".equals(cardstatus)){
				
				if("mgr.card.popup2.required".equals(cardRequired))
					cardType="popup2";
				
				if("authorize".equals(cardRequired))
					cardType="authorize";			
							
				cardstatus=cardType;
			}
			
			ccjson.put("cardstatus",cardstatus);
			ccjson.put("userid",mgrId);
		}catch(Exception e){
			System.out.println("exception in getCCStatus in AccountLevelReportsAction"); 
		}
		ccstatus=ccjson.toString();
		System.out.println("in AccountLevelReportsAction getMgrCCStatus ccstatus is:"+ccstatus);
		return "ccjson";
	}
	
	
	public String getChargePopup(){
		System.out.println("get charge popup");
		return "chargepopup";
	}
	
	public String validDateFormat(String inputdate){
		if(inputdate.indexOf("/")>-1){
		String[] datearr=GenUtil.strToArrayStr(inputdate, "/");
		if(datearr.length<3 || datearr.length>3)return "invaliddate";
		else{
			if(datearr[0].length()>2)return "invaliddate";
			else if(datearr[1].length()>2)return "invaliddate";
			else if(datearr[2].length()<4 || datearr[2].length()>4)return "invaliddate";
		 }
		}else
			return "invaliddate";
		return "validdate";
	}
	
	
	
	public boolean GetEventDatesValidate(){
		if("".equals(reportsData.getStartdate())){
			addFieldError("reportsData.startdate", "Event Start Date Range is empty ");
		}else{
			String chkdateformat=validDateFormat(reportsData.getStartdate().trim());
			if("invaliddate".equals(chkdateformat))
				addFieldError("reportsData.startdate", "Event Start Date Range is invalid ");
		}
		if("".equals(reportsData.getEnddate().trim()))
			addFieldError("reportsData.enddate", "Event End Date Range is empty ");
		else{
			String chkdateformat=validDateFormat(reportsData.getEnddate().trim());
			if("invaliddate".equals(chkdateformat))
				addFieldError("reportsData.enddate", "Event End Date Range is invalid ");
		}
		if(!getFieldErrors().isEmpty()){
			return false;
		}
		return true;
	}
	
     public boolean validateData(){
		if("2".equals(reportsData.getAttdatetype()) && "attendee".equals(rtype)){
		if(reportsData.getEventAttStartDate().indexOf(",")>-1)reportsData.setEventAttStartDate(reportsData.getEventAttStartDate().replace(",",""));
		if(reportsData.getEventAttEndDate().indexOf(",")>-1)reportsData.setEventAttEndDate(reportsData.getEventAttEndDate().replace(",",""));
		if("".equals(reportsData.getEventAttStartDate().trim()))
			addFieldError("reportsData.eventAttStartDate", "Start Date is empty");
		else{
			String chkdateformat=validDateFormat(reportsData.getEventAttStartDate().trim());
			if("invaliddate".equals(chkdateformat))
				addFieldError("reportsData.eventAttStartDate", "Start Date is invalid date format");
		}
		if("".equals(reportsData.getEventAttEndDate().trim()))
			addFieldError("reportsData.eventAttEndDate", "End Date is empty");
		else{
			String chkdateformat=validDateFormat(reportsData.getEventAttEndDate().trim());
			if("invaliddate".equals(chkdateformat))
				addFieldError("reportsData.eventAttEndDate", "End Date is invalid date format");
		}
		}if("2".equals(selindex) && "transaction".equals(rtype)){
			if(reportsData.getEventTranStartDate().indexOf(",")>-1)reportsData.setEventTranStartDate(reportsData.getEventTranStartDate().replace(",",""));
			if(reportsData.getEventTranEndDate().indexOf(",")>-1)reportsData.setEventTranEndDate(reportsData.getEventTranEndDate().replace(",",""));
			if("".equals(reportsData.getEventTranStartDate().trim()))
				addFieldError("reportsData.eventTranStartDate", "Start Date is empty");
			else{
				String chkdateformat=validDateFormat(reportsData.getEventTranStartDate().trim());
				if("invaliddate".equals(chkdateformat))
					addFieldError("reportsData.eventTranStartDate", "Start Date is invalid date format");
			}
			if("".equals(reportsData.getEventTranEndDate().trim()))
				addFieldError("reportsData.eventTranEndDate", "End Date is empty");
			else{
				String chkdateformat=validDateFormat(reportsData.getEventTranEndDate().trim());
				if("invaliddate".equals(chkdateformat))
					addFieldError("reportsData.eventTranEndDate", "End Date is invalid date format");
			}
		}
		if(!getFieldErrors().isEmpty()){
			return false;
		}
		return true;
	}
	
	public String getAttendeeInfo(){  
        boolean status=validateData();
		if(status){
		String uuid = RandomStringUUID.getUUID();
		ArrayList <String>deflist=new ArrayList<String>(
				Arrays.asList("TD","TID","FN","LN","TN","TP","BE","Em","Ph","ON","AK","BN","SC","BP","EN"));   
		long starttime=System.currentTimeMillis();		
		reportsData.setReportsradio(reportsradio);
		reportsData.setAttselindex(attselindex);
		reportsData.setTypeindex(typeindex);
		reportsData.setTransactiontypeindex(transactiontypeindex);
		reportsData.setReportType("attendee");
		reportsData.setSelindex(selindex);     
		fieldNames.addAll(attFields);
		fieldNames.add("EID");
		
		if("".equals(sortField)) sortField=(String)attFields.get(0);
		long ticketsinfosttime=System.currentTimeMillis();
		HashMap<String,String> currencymap=new HashMap<String,String>();
		reportsData.setStartdate(eventstartdate);
		reportsData.setEnddate(eventenddate);
		AccountLevelReportsDB.saveSelectedEvents(eventlist,mgrId,reportsData);
		currencymap=AccountLevelReportsDB.getEventsCurrencyMap(eventlist);
		long ticketsinfototaltime=(System.currentTimeMillis())-ticketsinfosttime;
		long attendeeMapstarttime=System.currentTimeMillis();
		attendeeMap=AccountLevelReportsDB.getAttendeeListInfo(eventlist,attselindex,reportsData,typeindex,sortField,sortDirection,currencymap);
		long attendeeMaptotaltime=(System.currentTimeMillis())-attendeeMapstarttime;
		long jsonstarttime=System.currentTimeMillis();
		msg=AccountLevelReportsJsonHelper.getAttReportsJson(attendeeMap, fieldNames,customAttribsMap,currencySymbol,reportsColumnMap);
		long jsontotaltime=(System.currentTimeMillis())-jsonstarttime;
		long totaltime=(System.currentTimeMillis())-starttime;
		if(export){
			fieldNames.remove("EID");
			excel = ExportToExcelHelper.exportToExcel(attendeeMap,fieldNames,"attendee",exporttype,customAttribsMap,reportsColumnMap);
			if(exporttype.equals("excel"))
				return "exporttoexcel";
			else if(exporttype.equals("csv"))
				return "exporttocsv";
		}
		return "reportsjson";
         }else
			return "fail";
	}
	public String getTransactionInfo(){
		System.out.println("in getTransaction method");
		boolean status=validateData();
		if(status){
		long starttime=System.currentTimeMillis();
		ArrayList <String>deflist=new ArrayList<String>(
		Arrays.asList("TD","TID","FN","LN","TN","TP","TC","PT","CCPF","SF","TTC","TNet","ON","St","Em","Ph","TU","So","Di","DC","EPID","CSF","No","NTSC","PID","VTSC","ESF","ECCF","Bal","OCCF","EN"));
		reportsData.setReportsradio(reportsradio);
		reportsData.setSelindex(selindex);
		reportsData.setTypeindex(typeindex);
		reportsData.setTransactiontypeindex(transactiontypeindex);
		reportsData.setReportType("registration");
		reportsData.setAttselindex(attselindex);		
		reportsData.setPaymentstaus(paymentstaus);
		fieldNames.addAll(Fields);
		fieldNames.add("EID");
		if("".equals(sortField)) sortField=(String)Fields.get(0);
		reportsData.setStartdate(eventstartdate);
		reportsData.setEnddate(eventenddate);
		AccountLevelReportsDB.saveSelectedEvents(eventlist,mgrId,reportsData);
		HashMap<String,String> currencymap=new HashMap<String,String>();
		currencymap=AccountLevelReportsDB.getEventsCurrencyMap(eventlist);
		HashMap ticketsinfo = AccountLevelReportsDB.getTicketInfo(eventlist,currencymap);
		transactionMap=AccountLevelReportsDB.getAllReports(eventlist,selindex,ticketsinfo,reportsData,transactiontypeindex,sortField,sortDirection,fieldNames,currencymap);
		msg=AccountLevelReportsJsonHelper.getTransactionReportsJson(transactionMap, fieldNames, customAttribsMap,currencySymbol,reportsColumnMap);
		if(export){
			fieldNames.remove("EID");
			excel = ExportToExcelHelper.exportToExcel(transactionMap,fieldNames,"transaction",exporttype,customAttribsMap,reportsColumnMap);
			if(exporttype.equals("excel"))
				return "exporttoexcel";
			else if(exporttype.equals("csv"))
				return "exporttocsv";
		}
		return "reportsjson";
		}else
		  return "fail";
	}
	
	public String getDuePopup(){
		return "duepopup";
	}
	
	@Override
	public void prepare() throws Exception {
			System.out.println("Mgr ID before super prepare:"+mgrId);
			try {
				super.prepare();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			System.out.println("Mgr ID after super prepare:"+mgrId);
			//eventData=new EventData();
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
		reportsColumnMap.put("TID", "Transaction ID");
		reportsColumnMap.put("TD", "Transaction Date");
		reportsColumnMap.put("FN", "First Name");
		reportsColumnMap.put("LN", "Last Name");
		reportsColumnMap.put("Em", "Email");
		reportsColumnMap.put("Ph", "Phone");
		reportsColumnMap.put("TN", "Ticket Name");
		reportsColumnMap.put("TP", "Ticket Price");
		reportsColumnMap.put("ON", "Order Number");
		reportsColumnMap.put("AK", "Attendee Key");
		reportsColumnMap.put("BN", "Buyer Name");
		reportsColumnMap.put("BE", "Buyer Email");
		reportsColumnMap.put("BP", "Buyer Phone");
		reportsColumnMap.put("SC", "Seat Code");
		
		reportsColumnMap.put("St", "Status");
		reportsColumnMap.put("TU", "Tracking URL");
		reportsColumnMap.put("So", "Source");
		reportsColumnMap.put("Di", "Discount");
		reportsColumnMap.put("DC", "Discount Code");
		reportsColumnMap.put("PT", "Payment Type");
		reportsColumnMap.put("CCPF", "CC Processing Fee");
		reportsColumnMap.put("EPID", "External Pay ID");
		reportsColumnMap.put("SF", "Service Fee");
		reportsColumnMap.put("CSF", "Collected Service Fee");
		reportsColumnMap.put("TTC", "Total Ticket Price");
		reportsColumnMap.put("PID", "Network Ticket Selling Partner ID");
		reportsColumnMap.put("NTSC", "Network Ticket Selling Commission");
		reportsColumnMap.put("No", "Notes");
		reportsColumnMap.put("TC", "Tickets Count");
		reportsColumnMap.put("TNet", "Net Ticket Price");
		
		reportsColumnMap.put("CIS", "Check In Status");
		reportsColumnMap.put("CIT", "Check In Time");
		reportsColumnMap.put("SID", "Scan ID");
		reportsColumnMap.put("TT", "Ticket Type");
		reportsColumnMap.put("VTSC", "Volume Ticket Selling Commission");
		
		reportsColumnMap.put("ESF", "Eventbee Service Fee");
		reportsColumnMap.put("ECCF", "Eventbee CC Processing Fee");
		reportsColumnMap.put("Bal", "Balance");
		reportsColumnMap.put("OCCF", "Other CC Processing Fee");
		reportsColumnMap.put("EN","Event Name");
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
	
	public String getChkditem() {
		return chkditem;
	}
	public void setChkditem(String chkditem) {
		this.chkditem = chkditem;
	}
	public HashMap<String, String> getMgrSelectedDates() {
		return mgrSelectedDates;
	}
	public void setMgrSelectedDates(HashMap<String, String> mgrSelectedDates) {
		this.mgrSelectedDates = mgrSelectedDates;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;

	}
    public JSONArray getEventList() {
		return eventList;
	}
	public void setEventList(JSONArray eventList) {
		this.eventList = eventList;
	}
	public String getRtype() {
		return rtype;
	}
	public void setRtype(String rtype) {
		this.rtype = rtype;
	}
	public boolean isErrorsExists() {
		return errorsExists;
	}
	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}
}
