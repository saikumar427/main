package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.event.beans.WaitListData;
import com.event.dbhelpers.TicketsDB;
import com.event.dbhelpers.WaitListDB;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.EbeeConstantsF;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WaitListAction extends ActionSupport{
	
	private String eid = "";
	private String jsonData = "[]";
	private String wid="";
	private WaitListData waitListData= new WaitListData();
	private ArrayList<Entity> hours = new ArrayList<Entity>();
	private ArrayList<Entity> minutes = new ArrayList<Entity>();
	private String msgType = "waitlistactivationlink";
	private String ticketid="";
	private String ticketQty="0";
	private String widAry="";
	private String activationType="";
	HashMap<String,HashMap<String,String>> multipleTicketReqMap=new HashMap<String,HashMap<String,String>>();
	private String multiTktMap="";
	private JSONObject multipleTicketJson=new JSONObject();
	private String purpose="confirmed";
	private String previewContent="";
	private String msg="";
	private String emailTemplateJson="";
	private String eventname="";
	private String mgrName="";
	private String serverAddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
	
	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	public JSONObject getMultipleTicketJson() {
		return multipleTicketJson;
	}

	public void setMultipleTicketJson(JSONObject multipleTicketJson) {
		this.multipleTicketJson = multipleTicketJson;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public WaitListData getWaitListData() {
		return waitListData;
	}

	public void setWaitListData(WaitListData waitListData) {
		this.waitListData = waitListData;
	}
	
	public ArrayList<Entity> getHours() {
		return hours;
	}

	public void setHours(ArrayList<Entity> hours) {
		this.hours = hours;
	}

	public ArrayList<Entity> getMinutes() {
		return minutes;
	}

	public void setMinutes(ArrayList<Entity> minutes) {
		this.minutes = minutes;
	}
	
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getTicketid() {
		return ticketid;
	}

	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}
	
	public String getTicketQty() {
		return ticketQty;
	}

	public void setTicketQty(String ticketQty) {
		this.ticketQty = ticketQty;
	}
	
	public String getWidAry() {
		return widAry;
	}

	public void setWidAry(String widAry) {
		this.widAry = widAry;
	}
	
	public String getActivationType() {
		return activationType;
	}

	public void setActivationType(String activationType) {
		this.activationType = activationType;
	}
	

	public HashMap<String, HashMap<String, String>> getMultipleTicketReqMap() {
		return multipleTicketReqMap;
	}

	public void setMultipleTicketReqMap(
			HashMap<String, HashMap<String, String>> multipleTicketReqMap) {
		this.multipleTicketReqMap = multipleTicketReqMap;
	}
	

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPreviewContent() {
		return previewContent;
	}

	public void setPreviewContent(String previewContent) {
		this.previewContent = previewContent;
	}

	public void populateTime() {
		hours = TicketsDB.getHours();
		minutes = TicketsDB.getMinutes();
	}
	
	public String getMultiTktMap() {
		return multiTktMap;
	}

	public void setMultiTktMap(String multiTktMap) {
		this.multiTktMap = multiTktMap;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getEmailTemplateJson() {
		return emailTemplateJson;
	}

	public void setEmailTemplateJson(String emailTemplateJson) {
		this.emailTemplateJson = emailTemplateJson;
	}
	
	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public String execute() {
		String curLvl=ActionContext.getContext().getParameters().get("curLvl").toString();
		String pwrType=ActionContext.getContext().getParameters().get("pwrType").toString();
		int WaitList = 300;
		System.out.println("Current Level : "+curLvl+" , Power Type : "+pwrType+" & EventId : "+eid);
		if(Integer.parseInt(curLvl)>=WaitList){
			jsonData = WaitListDB.getWaitListSummary(eid,waitListData).toString();
			return "waitlistsummary";
		}else
			return "pageRedirect";
		//emailTemplate();
		
	}
	
	public String waitListSummary(){
		jsonData = WaitListDB.getWaitListSummary(eid,waitListData).toString();
		return "jsondata";
	}
	
	public String waitEmailTemplate() {
		emailTemplate();
		return "waitlistemail";
	}
	
	public String emailTemplate(){
		emailTemplateJson=WaitListDB.getTemplate(eid, purpose).toString();
		try{
			JSONObject jsonObj = new JSONObject(emailTemplateJson);
			setMgrName(jsonObj.get("mgrName").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "emailTemplate";
	}
	
	public String getWLTransactionInfo(){
		populateTime();
		waitListData=WaitListDB.getWLTransactionData(wid,eid);
		return "waitlistdetails";
	}
	
	public String deleteWLTransaction(){
		WaitListDB.deleteWLTransaction(eid, wid);
		return "success";
	}
	
	public String validateExpiration(){
		System.out.println("validateExpiration eid: "+eid+" wid: "+waitListData.getWid());
		String status=WaitListDB.validateTimeCompare(eid, waitListData);
		System.out.println("validateExpiration status: "+status);
		if("success".equals(status)){
			 JSONObject datejson=new JSONObject();
				try{
					datejson.put("expDate",waitListData.getExpDate());
					datejson.put("expTime",waitListData.getExpTime());
				}catch(Exception e){
					System.out.println("exception in getCCStatus in specialfee action:"+eid); 
				}
				msg=datejson.toString();
			return "datejson";
		}else{
			addFieldError("selecteddate","Please select valid expiration date");
			return "expirationerror";
		}
	}
	
	public String sendActivationLink(){
		
			System.out.println("sendActivationLink eid: "+eid+" wid: "+wid);
			WaitListDB.sendActivationLink(eid, wid, waitListData,WaitListDB.getEventData(eid));
			return "activationlink";
	}
	
	public String sendMultipleActivation(){
		String status=WaitListDB.validateTimeCompare(eid, waitListData);
		if("success".equals(status)){
			WaitListDB.sendMultipleActivation(eid, widAry, waitListData);
		}else{
			addFieldError("selecteddate","Please select valid expiration date");
		return "expirationerror";
		}
		return "success";
	}
	
	public String checkSoldStatus(){
		boolean flag=false;
		HashMap<String,String> soldstatusMap=WaitListDB.getTicketSoldStatusMap(eid, ticketid, wid);
		if(soldstatusMap!=null){
			String soldQty=soldstatusMap.get("sold_qty");
			String maxTicket=soldstatusMap.get("max_ticket");
			String inprocessTktQty=soldstatusMap.get("inprocess_tkt_qty");
			if((Integer.parseInt(ticketQty)+Integer.parseInt(soldQty)+Integer.parseInt(inprocessTktQty))>Integer.parseInt(maxTicket))
				flag=true;
		}
		if(flag)
			return "soldstatus";
		else 
			return "blank";
	}
	
	public String checkMultipleSoldStatus(){
		populateTime();
		activationType="Multiple";
		boolean flag=false;
		multipleTicketReqMap=WaitListDB.getMultipleTicketSoldStatusMap(eid, widAry.replace(",", "','"));
		multipleTicketJson=new JSONObject(multipleTicketReqMap);
		WaitListDB.getWaitListDefaultExpDate(waitListData,eid);
		/*if(multipleTicketReqMap!=null && multipleTicketReqMap.size()>0){
			flag=true;
		}
		if(flag)*/
			return "soldstatus";
		/*else 
			return "blank";*/
		
	}
	
	public String updateTicketQty(){
		String status="success";
		if("Multiple".equals(activationType)){
			status=WaitListDB.updateMultipleTicketQty(eid, multiTktMap, waitListData);
			if("errors".equals(status)){
				addFieldError("selecteddate","Please select valid expiration date");
			return "expirationerror";
			}
		}else{
			
			WaitListDB.updateTicketQty(eid, ticketid, ticketQty);
		}
		return "success";
	}
	
	public String checkSoldQtyAndUpdate(){
		//System.out.println("checkSoldQtyAndUpdate ticketid: "+ticketid+" ticketQty: "+ticketQty+" wid: "+wid);
		HashMap<String,String> soldstatusMap=WaitListDB.getTicketSoldStatusMap(eid, ticketid, wid);
		if(soldstatusMap!=null){
			System.out.println("checkSoldQtyAndUpdate soldstatusMap: "+soldstatusMap+" wid: "+wid+" eid: "+eid+" ReqticketQty: "+ticketQty);
			String soldQty=soldstatusMap.get("sold_qty");
			String maxTicket=soldstatusMap.get("max_ticket");
			String inprocessTktQty=soldstatusMap.get("inprocess_tkt_qty");
			try{
				if(Integer.parseInt(inprocessTktQty)==0 && (Integer.parseInt(ticketQty)+Integer.parseInt(soldQty)-Integer.parseInt(maxTicket)>0))
					WaitListDB.updateTicketQty(eid, ticketid, String.valueOf(Integer.parseInt(ticketQty)+Integer.parseInt(soldQty)-Integer.parseInt(maxTicket)));
				else if((Integer.parseInt(ticketQty)+Integer.parseInt(soldQty)+Integer.parseInt(inprocessTktQty))==Integer.parseInt(maxTicket)){
					//WaitListDB.updateTicketQty(eid, ticketid, ticketQty);
				}else if((Integer.parseInt(ticketQty)+Integer.parseInt(soldQty)+Integer.parseInt(inprocessTktQty))>Integer.parseInt(maxTicket)){
					WaitListDB.updateTicketQty(eid, ticketid, String.valueOf((Integer.parseInt(ticketQty)+Integer.parseInt(soldQty)+Integer.parseInt(inprocessTktQty))-Integer.parseInt(maxTicket)));
				}
			}catch(Exception e){
				System.out.println("WaitListAction.java checkSoldQtyAndUpdate eid:: "+eid+" wid:: "+wid+" ERROR: "+e.getMessage());
			}
		}
		return "success";
	}
	
	public String save(){
		//System.out.println("th e save");
		WaitListDB.updateWaitListEmailSettings(eid,purpose,waitListData);
		//msgType="waitlistsave";
		msg=I18n.getString("global.update.scsfly.lbl");
	return "ajaxmsg";
	}
	
	public String resetWaitListEmailSettings(){
		WaitListDB.resetWaitListEmailSettings(eid,purpose);
		return emailTemplate();
	}
	
	public  String preview(){	
		previewContent=waitListData.getEmailcontent();
		previewContent=previewContent.replace("$eventName", eventname);
		previewContent=previewContent.replace("$buyerName", "xxx");
		previewContent=previewContent.replace("$mgrFirstName", mgrName);
		previewContent=previewContent.replace("$mgrLastName", "");
		return "preview";
	}
}
