package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Locale;

import org.json.JSONObject;

import com.event.beans.TransactionNotesData;
import com.event.beans.ReportsData;
import com.event.dbhelpers.DiscountsDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.ReportsDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.TransactionDB;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.EventbeeStrings;
import com.eventregister.dbhelper.RegistrationTiketingManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.user.dbhelpers.UserDB;


public class TransactionDetailsAction extends ActionSupport {	
private static final long serialVersionUID = 3352061092593412030L;
String eid="";
String tid="";
String notes_id="";
String powerType="";
private String powertype="";
HashMap TransInfo=new HashMap();
ArrayList ticketsList=new ArrayList(); 
HashMap ProfilesList=new HashMap();
ArrayList selectedTicketsDetails=new ArrayList();
HashMap attribMap=new HashMap();
HashMap buyerInfoMap=new HashMap();
private String currencySymbol="";
private String paymentStatus="";
private TransactionNotesData transactionNotesData;
private String checkInStatus="";

public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
private String msg="";
private ReportsData reportsData=new ReportsData();
private ArrayList dates=new ArrayList();
private String currentTransactionDate="";	
private String date="";
private String currentStatus="";
private ArrayList<Entity> statusType=new ArrayList<Entity>();
private String payType="";
private String profilecount="";
String paymentApprovalType="";
private String seatingevent="";
private String ticketseatstaus="";
private String ordernumber="";
private String attendeename="";
private String attendeelastname="";
private String traninfoindex="";
private String purpose="";
private String emailid="";
private String startDate="";
private String endDate="";

private String profilekey="";
public String getProfilekey() {
	return profilekey;
}
public void setProfilekey(String profilekey) {
	this.profilekey = profilekey;
}
public String getEmailid() {
	return emailid;
}
public void setEmailid(String emailid) {
	this.emailid = emailid;
}
public String getPurpose() {
	return purpose;
}
public void setPurpose(String purpose) {
	this.purpose = purpose;
}
public String getTraninfoindex() {
	return traninfoindex;
}
public void setTraninfoindex(String traninfoindex) {
	this.traninfoindex = traninfoindex;
}
public String getOrdernumber() {
	return ordernumber;
}
public void setOrdernumber(String ordernumber) {
	this.ordernumber = ordernumber;
}
public String getAttendeename() {
	return attendeename;
}
public void setAttendeename(String attendeename) {
	this.attendeename = attendeename;
}
public String getTicketseatstaus() {
	return ticketseatstaus;
}
public void setTicketseatstaus(String ticketseatstaus) {
	this.ticketseatstaus = ticketseatstaus;
}
public String getSeatingevent() {
	return seatingevent;
}
public void setSeatingevent(String seatingevent) {
	this.seatingevent = seatingevent;
}
public String getCurrentStatus() {
	return currentStatus;
}
public void setCurrentStatus(String currentStatus) {
	this.currentStatus = currentStatus;
}

public ArrayList<Entity> getStatusType() {
	return statusType;
}
public void setStatusType(ArrayList<Entity> statusType) {
	this.statusType = statusType;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public ArrayList getDates() {
	return dates;
}
public void setDates(ArrayList dates) {
	this.dates = dates;
}
public String getCurrentTransactionDate() {
	return currentTransactionDate;
}
public void setCurrentTransactionDate(String currentTransactionDate) {
	this.currentTransactionDate = currentTransactionDate;
}
public ReportsData getReportsData() {
	return reportsData;
}
public void setReportsData(ReportsData reportsData) {
	this.reportsData = reportsData;
}

public void setNotes_id(String notes_id) {
	this.notes_id = notes_id;
}

public TransactionNotesData getTransactionNotesData() {
	return transactionNotesData;
}
public void setTransactionNotesData(TransactionNotesData transactionNotesData) {
	this.transactionNotesData = transactionNotesData;
}
public HashMap getBuyerInfoMap() {
	return buyerInfoMap;
}
public void setBuyerInfoMap(HashMap buyerInfoMap) {
	this.buyerInfoMap = buyerInfoMap;
}
public String getPaymentStatus() {
	return paymentStatus;
}
public void setPaymentStatus(String paymentStatus) {
	this.paymentStatus = paymentStatus;
}
public String getCurrencySymbol() {
	return currencySymbol;
}
public void setCurrencySymbol(String currencySymbol) {
	this.currencySymbol = currencySymbol;
}
public HashMap getAttribMap() {
	return attribMap;
}
public void setAttribMap(HashMap attribMap) {
	this.attribMap = attribMap;
}
RegistrationTiketingManager rgmgr=new RegistrationTiketingManager();

public String getEid() {
	return eid;
}
public void setEid(String eid) {
	this.eid = eid;
}
public String getTid() {
	return tid;
}
public void setTid(String tid) {
	this.tid = tid;
}

public void populatedatefields(){
	int year=0;
	java.util.Date d=new java.util.Date();
    year=d.getYear()+1900;
    String attsyear=reportsData.getAttsYear();
    if("".equals(attsyear)) attsyear="1";
    String atteyear=reportsData.getAtteYear();
    if("".equals(atteyear)) atteyear="1";
    int attdisplayStartYear=Integer.parseInt(attsyear);
    int attdisplayEndYear=Integer.parseInt(atteyear);
	reportsData.setAttstartMonth(EventbeeStrings.getMonthHtml(reportsData.getAttsMonth(),"reportsData.attsMonth"));
	reportsData.setAttstartDay(EventbeeStrings.getDayHtml(reportsData.getAttsDay(),"reportsData.attsDay"));
	reportsData.setAttstartYear(EventbeeStrings.getYearHtml(year-1,3,attdisplayStartYear,"reportsData.attsYear"));
	reportsData.setAttendDay(EventbeeStrings.getDayHtml(reportsData.getAtteDay(),"reportsData.atteDay"));
	reportsData.setAttendYear(EventbeeStrings.getYearHtml(year-1,3,attdisplayEndYear,"reportsData.atteYear"));
	reportsData.setAttendMonth(EventbeeStrings.getMonthHtml(reportsData.getAtteMonth(),"reportsData.atteMonth"));
	
}


public String execute(){
	setTraninfoindex(traninfoindex);
	populatedatefields();
	return "success";	
}
public String addNotes(){
	transactionNotesData=TransactionDB.getNotesData(tid);
	return "notes";
}


 public String saveNotes(){
	TransactionDB.saveNotesData(transactionNotesData,tid);
	return "checkinstatus";
		
	}

public String getTransactionInfo(){
	// adding for rsvp special fee pro feature
	boolean isrecur=EventDB.isRecurringEvent(eid);
	try{
	System.out.println("getTransactionInfo powertype123: "+powertype);
	String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
	setTraninfoindex("1");
	if(powertype.equals("RSVP"))
		SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP Search Attendee");
	    currencySymbol = EventDB.getCurrencySymbol(eid);
		seatingevent=TransactionDB.getSeatingStatus(eid);
	    getDetailTransactionReport(eid,tid.trim());
		populatedatefields();
	}catch(Exception e){
		System.out.println("Exception occured in getTransactionInfo for tid:"+tid);
	}
	JSONObject jsonObj=new JSONObject();
	try{
	jsonObj.put("transdetails",TransInfo);
	jsonObj.put("tktdetails",ticketsList);
	jsonObj.put("buyerdetails",buyerInfoMap);
	jsonObj.put("attendeedetails",ProfilesList);
	jsonObj.put("attribmap",attribMap);
	jsonObj.put("seatingevt",seatingevent);
	jsonObj.put("currencySymbol",currencySymbol);
	jsonObj.put("currentTransactionDate",currentTransactionDate);
	jsonObj.put("profilecount",profilecount);
	jsonObj.put("response1",reportsData.getResponsetype());
	jsonObj.put("recurdates",dates);
	jsonObj.put("notes",transactionNotesData.getNotes());
	jsonObj.put("powertype",powertype);
	jsonObj.put("isrecurring",isrecur);
	
	}catch(Exception e){}
	msg=jsonObj.toString();
	if(TransInfo.size()>0)
	return "attendeetraninfojson";
	else{
		 msg="Not a valid Transaction ID";
		 return "attendeetraninfojson";
	 }
}


public String getTransactionIdInfo(){
	
	if("".equals(tid.trim())){
		 msg="Not a valid Transaction ID";
	 }else{
    ArrayList<HashMap<String,String>> attendeestransactions=new ArrayList<HashMap<String,String>>();
	 attendeestransactions=TransactionDB.getAttendeeTranInfoByTid(eid,tid.trim());
	 ArrayList<String> Fields=new ArrayList<String>();
	 Fields=getFields();
	 JSONObject json=new JSONObject();
	 if(attendeestransactions.size()==0)
		 msg="None Found";
	 else
	 msg=TransactionDB.getAttendeeTranJsonData(attendeestransactions,Fields);
	 }
	 return "attendeetraninfojson";
}



 public String getOrderNumTransactionInfo(){
	 System.out.println("In getOrderNumTransactionInfo is:"+eid);
	 String transactionid=TransactionDB.getOrderNumTransactionID(eid,ordernumber.trim());
	 ArrayList<HashMap<String,String>> attendeestransactions=new ArrayList<HashMap<String,String>>();
	 attendeestransactions=TransactionDB.getAttendeeTranInfoByTid(eid,transactionid);
	 ArrayList<String> Fields=new ArrayList<String>();
	 Fields=getFields();
	 JSONObject json=new JSONObject();
	 if(attendeestransactions.size()==0)
		 msg="None Found";
	 else
	 msg=TransactionDB.getAttendeeTranJsonData(attendeestransactions,Fields);
		 return "attendeetraninfojson";
 }
 
 public ArrayList<String> getFields(){
	 ArrayList<String> Fields=new ArrayList<String>();
	 Fields.add("Transaction Date");
	 Fields.add("Transaction ID");
	 Fields.add("Attendee Name");
     return Fields;
 }
 
 
 public String getTranInfoByAttendeeName(){
	 System.out.println("In getTranInfoByAttendeeName is:"+eid); 
	 if("First Name".equals(attendeename))attendeename="";
     if("Last Name".equals(attendeelastname))attendeelastname="";
	 String attname=attendeename.trim()+attendeelastname.trim();
	 if("".equals(attname.trim())){
		 msg="Not a valid Attendee Name";
	 }else{
     ArrayList<HashMap<String,String>> attendeestransactions=new ArrayList<HashMap<String,String>>();
	 attendeestransactions=TransactionDB.getAttendeeTranInfo(eid,attname.trim());
	 ArrayList<String> Fields=new ArrayList<String>();
	 Fields=getFields();
	 JSONObject json=new JSONObject();
	 if(attendeestransactions.size()==0)
		 msg="Not a valid Attendee Name";
	 else
	 msg=TransactionDB.getAttendeeTranJsonData(attendeestransactions,Fields);
	 }
	 return "attendeetraninfojson";
	}
 
 public String getTranInfoByEmail(){
	 System.out.println("In getTranInfoByEmail is:"+eid); 
	 ArrayList<HashMap<String,String>> traninfobyemaillist=new ArrayList<HashMap<String,String>>();
	 if(!"".equals(emailid.trim().toLowerCase()))
	 {
		 traninfobyemaillist=TransactionDB.getTransactionInfoByEmail(eid,emailid.trim().toLowerCase());
		 ArrayList<String> Fields=new ArrayList<String>();
		 Fields=getFields();
		 if(traninfobyemaillist.size()==0)
			 msg="Not a valid Email";
		 else
		 msg=TransactionDB.getAttendeeTranJsonData(traninfobyemaillist,Fields);
     }
	 else if("".equals(emailid.trim()))
		 msg="Not a valid Email"; 
	 return "attendeetraninfojson";
	 
 }
 
 public String getTranInfoByDates(){        
	 System.out.println("In getTranInfoByDates is:"+eid); 
	 ArrayList<HashMap<String,String>> traninfobydateslist=new ArrayList<HashMap<String,String>>();
	 String[] sdate=startDate.split("/"); 
	 String[] edate=endDate.split("/");
	 
	 
	 
	 String startdate=reportsData.getAttsYear()+"-"+reportsData.getAttsMonth()+"-"+reportsData.getAttsDay();
	 String enddate=reportsData.getAtteYear()+"-"+reportsData.getAtteMonth()+"-"+reportsData.getAtteDay();
	 
	 startdate=sdate[2]+"-"+sdate[0]+"-"+sdate[1];
	 enddate=edate[2]+"-"+edate[0]+"-"+edate[1];
	 System.out.println("2nd sta::"+startdate+"::"+startdate);
	 
	 ArrayList<String> Fields=new ArrayList<String>();
	 Fields=getFields();
	 traninfobydateslist=TransactionDB.getTransactionInfoByDates(startdate,enddate,eid);
	 if(traninfobydateslist.size()>0)
	  msg=TransactionDB.getAttendeeTranJsonData(traninfobydateslist,Fields);
	 else
	  msg="No Records";	 
	 return "attendeetraninfojson";
	}
 
 public void getDetailTransactionReport(String eventid,String transactionid){
	 try{
	 TransInfo = TransactionDB.getRegTransactionInfo(eventid, transactionid.trim());
	 if(TransInfo.size()>0){
	 currentTransactionDate = ReportsDB.getCurrentTransctionDate(eventid, transactionid);
		System.out.println("we r in transactiondetails action"
				+ currentTransactionDate);
	  //dates = ReportsDB.getEventDates(eventid, transactionid);
	  dates = TransactionDB.getEventDates(eventid,transactionid);
      reportsData = TransactionDB.getResponseInfo(eventid, transactionid);
      paymentStatus=TransInfo.get("paymentstatus").toString();
	  ticketsList = TransactionDB.getRegTicketsInfo(eventid, transactionid.trim());
	  ProfilesList = TransactionDB.getProfileData(eventid, transactionid.trim());
	  buyerInfoMap = TransactionDB.getBuyerData(eventid, transactionid.trim());
	  // System.out.println("buyerInfoMap::::::;"+buyerInfoMap);
	  selectedTicketsDetails = rgmgr.getSelectedTickets(eventid, transactionid.trim());
	  attribMap = TransactionDB.getTransactionAttributes(eventid, transactionid.trim());
	  transactionNotesData = TransactionDB.getNotesData(transactionid);
	  profilecount=TransactionDB.getProfileCount(eventid, transactionid.trim());
	 }
	 }catch(Exception e){
		 System.out.println("Exception occured in getDetailTransactionReport");
	 }
 }
 
 public String changeDate(){
	 date=reportsData.getDate();
	 currentTransactionDate=ReportsDB.getCurrentTransctionDate(eid, tid);
	 if(!"".equals(date)&& date!=null){
    	 TransactionDB.updateRSVPEventDate(eid,tid,date,currentTransactionDate);
	 }
	 return "checkinstatus";
}
public String deleteTransaction(){	
	TransactionDB.deleteTransaction(eid,tid);
	return "deleted";
}
public String deleteRSVPTransaction(){	
	TransactionDB.deleteRSVPTransaction(eid,tid);
	return "checkinstatus";
}
public String resendMail(){
	
	TransactionDB.resendingMail(eid,tid,powerType);
	return "sendingmail";
}
public String changeStatus(){
	//Need Approval = Pending Approval
	currentStatus=TransactionDB.getCurrentStatus(eid,tid);
	if("Need Approval".equalsIgnoreCase(currentStatus)) currentStatus="Pending Approval";
	if("Cancelled".equalsIgnoreCase(currentStatus)) currentStatus="Deleted";
	if(payType.equalsIgnoreCase("Other"))
		paymentApprovalType=ReportsDB.getPaymentApprovalType(eid);
	
	if(paymentApprovalType != null && paymentApprovalType.equals("manual"))
		statusType=TransactionDB.populateOtherStatusTypeList();
	else
		statusType=TransactionDB.populateStatusTypeList();
	
	transactionNotesData=TransactionDB.getNotesData(tid);
	return "changestatus";
}
public boolean validateStatus(){
	ResourceBundle resourceBundle =I18n.getResourceBundle();
	if ("".equals(reportsData.getChangeStatus().trim())) {
		addFieldError("reportsData.changeStatus",resourceBundle.getString("tr.status.emty.lbl"));
	}
	if (!getFieldErrors().isEmpty()) {
		// addFieldError("discountData.errorsExists",
		// "There are some errors");

		return false;
	}
	return true;
}

public String saveStatus(){
	    TransactionDB.insertStatus(eid,tid,reportsData);
	    String currentstatus=TransactionDB.getCurrentStatus(eid,tid);
	    System.out.println("present status after changed:"+tid+":"+currentstatus);
	    TransactionDB.saveNotesData(transactionNotesData,tid);
		return "success";	
}
public String undeleteTransaction(){
	System.out.println("\n undeleteTransaction Completed status");
	String changestatus="";
	if(payType.equalsIgnoreCase("Other"))
		paymentApprovalType=ReportsDB.getPaymentApprovalType(eid);
	
	if(paymentApprovalType != null && paymentApprovalType.equals("manual"))
		changestatus="Need Approval";
	else
		changestatus="Completed";
	TransactionDB.undeleteTransaction(eid,tid,changestatus);
	return "deleted";
}

public String changeCheckInStatus(){
	System.out.println("profile key:"+profilekey+" checkInStatus: "+checkInStatus);
	TransactionDB.changeCheckInStatus(eid,tid,profilekey,checkInStatus);
return "checkinstatus";	
}



public ArrayList getSelectedTicketsDetails() {
	return selectedTicketsDetails;
}
public void setSelectedTicketsDetails(ArrayList selectedTicketsDetails) {
	this.selectedTicketsDetails = selectedTicketsDetails;
}
public void setProfilesList(HashMap profilesList) {
	ProfilesList = profilesList;
}

public HashMap getProfilesList(){
	return ProfilesList;
}
public ArrayList getTicketsList() {
	return ticketsList;
}
public void setTicketsList(ArrayList ticketsList) {
	this.ticketsList = ticketsList;
}
public HashMap getTransInfo() {
	return TransInfo;
}
public void setTransInfo(HashMap transInfo) {
	TransInfo = transInfo;
}
public String getPowerType() {
	return powerType;
}
public void setPowerType(String powerType) {
	this.powerType = powerType;
}
public String getPayType() {
	return payType;
}
public void setPayType(String payType) {
	this.payType = payType;
}
public String getPowertype() {
	return powertype;
}
public void setPowertype(String powertype) {
	this.powertype = powertype;
}
public void setProfilecount(String profilecount) {
	this.profilecount = profilecount;
}
public String getProfilecount() {
	return profilecount;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public String getAttendeelastname() {
	return attendeelastname;
}
public void setAttendeelastname(String attendeelastname) {
	this.attendeelastname = attendeelastname;
}
public String getCheckInStatus() {
	return checkInStatus;
}
public void setCheckInStatus(String checkInStatus) {
	this.checkInStatus = checkInStatus;
}
}
