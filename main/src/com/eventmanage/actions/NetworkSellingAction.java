package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Locale;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.event.beans.EventData;
import com.event.beans.ticketing.GroupData;
import com.event.beans.ticketing.TicketData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.NetworkSellingDB;
import com.event.helpers.I18n;
import com.eventmanage.helpers.NTSJSONHelper;
import com.opensymphony.xwork2.ActionSupport;

public class NetworkSellingAction extends ActionSupport{
	private static Logger log=Logger.getLogger(NetworkSellingAction.class);
	private String eid="";
	private String currenySymbol="";
	TicketData ticketData=new TicketData();
	private ArrayList<GroupData> groupsList = new ArrayList<GroupData>();
	private String jsonData = "";
	private String tid = "";
	private String ntsStatus;
	private String status;
	EventData eventData = new EventData();
	private String nts_commission;
	private String msgType = "";
	private ArrayList<HashMap<String,String>> promotedMembers=new ArrayList<HashMap<String,String>>();
	private String promotionsJson="";
	private String type="";
	private String videourl="";
	

	public String getVideourl() {
		return videourl;
	}

	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<HashMap<String, String>> getPromotedMembers() {
		return promotedMembers;
	}

	public void setPromotedMembers(
			ArrayList<HashMap<String, String>> promotedMembers) {
		this.promotedMembers = promotedMembers;
	}

	public String getPromotionsJson() {
		return promotionsJson;
	}

	public void setPromotionsJson(String promotionsJson) {
		this.promotionsJson = promotionsJson;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getNts_commission() {
		return nts_commission;
	}

	public void setNts_commission(String nts_commission) {
		this.nts_commission = nts_commission;
	}

	public EventData getEventData() {
		return eventData;
	}

	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNtsStatus() {
		return ntsStatus;
	}

	public void setNtsStatus(String ntsStatus) {
		this.ntsStatus = ntsStatus;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
	
	public String getCurrenySymbol() {
		return currenySymbol;
	}

	public void setCurrenySymbol(String currenySymbol) {
		this.currenySymbol = currenySymbol;
	}

	public TicketData getTicketData() {
		return ticketData;
	}

	public void setTicketData(TicketData ticketData) {
		this.ticketData = ticketData;
	}

	public ArrayList<GroupData> getGroupsList() {
		return groupsList;
	}

	public void setGroupsList(ArrayList<GroupData> groupsList) {
		this.groupsList = groupsList;
	}
	
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String execute(){
		currenySymbol=EventDB.getCurrencySymbol(eid);
        if(currenySymbol==null)
        	currenySymbol="$";
        ticketData.setCurrency(currenySymbol);
		String userTimeZone = EventDB.getEventTimeZone(eid);
		eventData = EventDB.getEventData(eid);
		groupsList = NetworkSellingDB.getEventTicketsList(eid, userTimeZone);
		jsonData = NTSJSONHelper.getNTSTicketsJson(groupsList).toString();
		promotedMembers=EventDB.getPormotedMembers(eid);
		JSONObject jobj=new JSONObject();
		try{jobj.put("promotions", promotedMembers);}catch (Exception e) {}
		promotionsJson=jobj.toString();
		return "input";
	}

	public String getNTSChargeScreen(){
		return "ntscharges";
	}
	
	public String updateNTSChargeEnableStatus(){
		NetworkSellingDB.updateNTSEnableChargeStatus(eid);
		return "success";
	} 
	
	public String editCommission(){
		ticketData = NetworkSellingDB.getTicketCommissionData(eid, tid);
		return "commission";
	}
	
	public String save(){
		log.info("In save ticket commission: "+ticketData.getNetWorkCommission()+" tickeid: "+tid);
		String commission = ticketData.getNetWorkCommission().trim();
		boolean status = validateInputData(commission,"ticket");
		if(status){
			NetworkSellingDB.updateCommission(commission, tid, eid);
			return "success";
		}else 
			return "inputerror";
	}
	
	public String updateNTSStatus(){
		EventDB.updateNTSStatus(eid,status);
		/*if(status.equals("Y"))
			msgType="Y";
		else
			msgType="N";*/
		return "success";
	}
	
	public String disableNTS(){
		EventDB.updateNTSStatus(eid,"N");
		return "success";
	}
	
	public String updateCommission(){
		String commission = eventData.getNtsCommission().trim();
		boolean status = validateInputData(commission,"all");
		if(status){
			log.info("Updateting Same Commission for all tickets commission: "+commission);
			NetworkSellingDB.updateAllTicketsCommission(commission, eid);
			return "success";
		}else 
			return "inputerror";
	}
	
	public boolean validateInputData(String commission,String type) {
		ResourceBundle resourceBundle =I18n.getResourceBundle();
		try {
			if("".equals(commission)){
				addFieldError("",resourceBundle.getString("nt.tkt.selling.cmsn.emty.lbl"));			
			}
			if(!commission.equals("")){
				Double comm = Double.parseDouble(commission);
				if (comm < 0 || comm > 100) {
					addFieldError("",resourceBundle.getString("nt.inv.nwk.tkt.slng.cmsn.lbl"));
				}else if (type.equals("all") && comm < 5 ) {        
					addFieldError("",resourceBundle.getString("nt.tkt.slng.cmsn.grt.eql.fve.lbl"));
				}else if(type.equals("ticket") && comm > 0 && comm < 5){
					addFieldError("",resourceBundle.getString("nt.tkt.slng.cmsn.grt.or.eql.fve.lbl"));
				}
			}
		} catch (NumberFormatException e) {
			addFieldError("",resourceBundle.getString("nt.tkt.slng.cmsn.num.lbl"));

		} catch (Exception e) {
			addFieldError("",resourceBundle.getString("nt.tkt.slng.cmsn.num.lbl"));
		}
		
		if (!getFieldErrors().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public String loadNtsVideo(){
		System.out.println("eid is:"+eid);
		return "loadntsvideo";
	}
	public String loadVideo()
	{
		return "videos";
	}

	
	
}
