package com.eventmanage.actions;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import com.event.beans.PrintedData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.PrePrintedTicketsDB;
import com.event.dbhelpers.TicketsDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.EbeeConstantsF;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class PrintedTicketsAction extends ActionSupport implements Preparable,ValidationAware{

	private String eid="";
	private ArrayList<Entity> ticketsList=new ArrayList<Entity>();
	private PrintedData printedData = new PrintedData();
	private String isErrorExist="";
	private String jsonData="";	
	private String attendees="";
	private String notes="";
	private String transaction="";
	private InputStream inputStream;
	private String result="";
	private String selecteddate="";
	private String msgType="preprintedtickets";
	private String download="no";
	private boolean isrecur;
	private String listid="";
	private ArrayList<Entity> templateList=new ArrayList<Entity>();
	private ArrayList<Entity> datesList=new ArrayList<Entity>();
	
	
	public String getListid() {
		return listid;
	}
	public void setListid(String listid) {
		this.listid = listid;
	}
	
	public String getSelecteddate() {
		return selecteddate;
	}
	public void setSelecteddate(String selecteddate) {
		this.selecteddate = selecteddate;
	}
	public ArrayList<Entity> getDatesList() {
		return datesList;
	}
	public void setDatesList(ArrayList<Entity> datesList) {
		this.datesList = datesList;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public ArrayList<Entity> getTemplateList() {
		return templateList;
	}
	public void setTemplateList(ArrayList<Entity> templateList) {
		this.templateList = templateList;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTransaction() {
		return transaction;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getAttendees() {
		return attendees;
	}
	public void setAttendees(String attendees) {
		this.attendees = attendees;
	}
	public String getIsErrorExist() {
		return isErrorExist;
	}
	public String getJsonData() {
		return jsonData;   
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public void setIsErrorExist(String isErrorExist) {
		this.isErrorExist = isErrorExist;
	}
	public String getEid() {
		return eid;
	}
	public ArrayList<Entity> getTicketsList() {
		return ticketsList;
	}
	public void setTicketsList(ArrayList<Entity> ticketsList) {
		this.ticketsList = ticketsList;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public PrintedData getPrintedData() {
		return printedData;
	}
	public void setPrintedData(PrintedData printedData) {
		this.printedData = printedData;
	}
	public boolean isIsrecur() {
		return isrecur;
	}
	public void setIsrecur(boolean isrecur) {
		this.isrecur = isrecur;
	}
	public String generateTickets(){
		return "generate";
	}
	public void prepare(){
	}
	
	public String execute(){
		isrecur=EventDB.isRecurringEvent(eid);
		JSONObject printedList=new JSONObject();
		if(isrecur)
			datesList=PrePrintedTicketsDB.getDatesList(eid);
		
		if(!isrecur)
			printedList=generateNormalEventPDFTickets();
		jsonData=printedList.toString();
		return "success";
	}
	
	
	public JSONObject generateNormalEventPDFTickets(){
		JSONObject js=new JSONObject();
		ArrayList<HashMap<String,String>> printedTickets=PrePrintedTicketsDB.getPrintedTicketDetails(eid,"");
		js=PrePrintedTicketsDB.getPrePrintedJson(eid,printedTickets);
		return js;
	}
	
	
	public String generateRecurringEventPDFTickets(){
		JSONObject js=new JSONObject();
		ArrayList<HashMap<String,String>> printedTickets=PrePrintedTicketsDB.getPrintedTicketDetails(eid,selecteddate);
		js=PrePrintedTicketsDB.getPrePrintedJson(eid,printedTickets);
		
		jsonData=js.toString();
	return "savedstatus";
	}
	

	public String getPrintedTickets(){
		isrecur=EventDB.isRecurringEvent(eid);
		if(isrecur)
			datesList=PrePrintedTicketsDB.getDatesList(eid);
		ticketsList=TicketsDB.getEventTicketsList(eid);
		templateList=TicketsDB.getTemplateList(eid);
	return "createprintedticket";
	}
	
	public String saveTicketStatus(){
		PrePrintedTicketsDB.saveChangeStatus(eid,attendees,notes,transaction);
		return "savedstatus";
	}
	
	public String changeToAssigned(){
	return "changetoassign";
	}
	
	
	public String deleteAttendees(){
		PrePrintedTicketsDB.deleteAttendees(eid,attendees);
	return "savedstatus";
	}
	
	
	public String getRecurringTickets(){
		return "savedstatus";
	}
	
	
	public String downloadPdf(){
		try{
				String pdfLocation=EbeeConstantsF.get("temp.upload.filepath","C:\\uploads\\")+eid+"_"+listid+".pdf";
				System.out.println("the pdf location::"+pdfLocation);
	            inputStream =new FileInputStream(pdfLocation); 
	            
		}catch(Exception e){
			System.out.println("Exception Occured while Dowloading preprinted tickets:"+e.getMessage());
			execute();
			return "success";
		}
	return "download";
	}
	
	
	public String savePrePrintedTicket(){
		boolean flag=validateData(); 
		if(flag){
			isErrorExist="no";
		String listId=PrePrintedTicketsDB.savePrintedTickets(eid,printedData,selecteddate);
		if(isrecur)
		generateRecurringEventPDFTickets();
		else
		execute();
		try{
		JSONObject json=new JSONObject(jsonData);
		json.put("listid",listId);
		jsonData=json.toString();
		System.out.println("final json is:::"+jsonData);
		}catch(Exception e){
			
		}
		
		}else{
			isErrorExist="yes";
			return "inputerror";
		}
		return "savedstatus";
	}
	
	public String getPrintedListDetails(){
		JSONObject js=new JSONObject();
		ArrayList notAssignedList=PrePrintedTicketsDB.getNotAssignedPrintedTicketsList(eid,selecteddate,listid);
		ArrayList AssignedList=PrePrintedTicketsDB.getAssignedPrintedTicketsList(eid,selecteddate,listid);
		js=PrePrintedTicketsDB.getAllPrePrintedJson(eid,notAssignedList,AssignedList);
		jsonData=js.toString();
		
		
	return "printedlist";
	}
	
	
	
	public boolean validateData(){
		if("0".equals(selecteddate))
			addFieldError("selecteddate","Please select date");
		
		if("1".equals(printedData.getSelectedtkt()))
			addFieldError("printedData.selectedtkt","Please select ticket");
		
		if("".equals(printedData.getFromfix()))
			addFieldError("printedData.fromfix","Series from number is empty");
		else{
			try{
				Integer.parseInt(printedData.getFromfix());
			}catch(Exception e){
				addFieldError("printedData.fromfix","Series from number should be integer");
			}
		}
		if("".equals(printedData.getTofix()))
			addFieldError("printedData.fromfix","Series to number is empty");
		else{
			try{
				Integer.parseInt(printedData.getTofix());
			}catch(Exception e){
				addFieldError("printedData.fromfix","Series to number should be integer");
			}
		}
		if (!getFieldErrors().isEmpty()) {
			return false;
		}
		return true;
	}
}
