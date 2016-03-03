package com.eventmanage.actions;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.event.beans.EventData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.UpgradeEventDB;
import com.eventbee.general.DbUtil;
import com.eventbee.payments.dbhelpers.CardProcessorDB;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;


public class SpecialFeeAction extends ActionSupport implements Preparable,ValidationAware{

	private String eid="";
	private String ticketingtype;
	private String servicefee="";
	private String mgrid="";
	private String ccstatus="";
	private String source="";
	private String currencySymbol="";
	private String powertype="";
	private HashMap<String, String> specialFeeMap=new HashMap<String, String>();
	
    public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCcstatus() {
		return ccstatus;
	}

	public void setCcstatus(String ccstatus) {
		this.ccstatus = ccstatus;
	}

	public String getMgrid() {
		return mgrid;
	}

	public void setMgrid(String mgrid) {
		this.mgrid = mgrid;
	}

	public String getServicefee() {
		return servicefee;
	}

	public void setServicefee(String servicefee) {
		this.servicefee = servicefee;
	}

	public String getTicketingtype() {
		return ticketingtype;
	}

	public void setTicketingtype(String ticketingtype) {
		this.ticketingtype = ticketingtype;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public HashMap<String, String> getSpecialFeeMap() {
		return specialFeeMap;
	}

	public void setSpecialFeeMap(HashMap<String, String> specialFeeMap) {
		this.specialFeeMap = specialFeeMap;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String execute(){
		EventData edata=EventDB.getEventData(eid);
		mgrid=edata.getMgrId();
		HashMap<String,String> currencyDetailsMap=new HashMap<String,String>();
		currencyDetailsMap=SpecialFeeDB.getEventCurrencyMap(eid);
		currencySymbol=currencyDetailsMap.get("currencysymbol");
		if(currencySymbol==null || "".equals(currencySymbol))currencySymbol="$";
		System.out.println("ticketingtype: "+ticketingtype+" source: "+source+" powertype: "+powertype);
		if("No".equalsIgnoreCase(SpecialFeeDB.checkUpgradeStatus(eid, source, powertype, ticketingtype)))
			return "blank";
		specialFeeMap=SpecialFeeDB.getNewSpecialFee(eid,mgrid,ticketingtype,currencyDetailsMap.get("currencycode"));
		
		System.out.println("servicefee for event is :: "+eid+" servicefee :: "+servicefee);
		if(specialFeeMap.isEmpty())
			return "blank";
		else 
			return "specialfee";
	    	
	}		
	
	public String upgradeEvent(){
		System.out.println("ticketingtype: "+ticketingtype+" source: "+source+" powertype: "+powertype);
		EventData edata=EventDB.getEventData(eid);
		mgrid=edata.getMgrId();
	return "upgradeevent";
	}
	
	public String saveUpgradeEvent(){
		HashMap<String,String> currencyDetailsMap=new HashMap<String,String>();
		currencyDetailsMap=SpecialFeeDB.getEventCurrencyMap(eid);
		specialFeeMap=SpecialFeeDB.getNewSpecialFee(eid,mgrid,ticketingtype,currencyDetailsMap.get("currencycode"));
		System.out.println("eid: "+eid+" mgrid: "+mgrid+" ticketingtype: "+ticketingtype+"::servicefee::"+servicefee+":source::"+source);
		SpecialFeeDB.insertSpecialFee(eid,ticketingtype,specialFeeMap.get(ticketingtype),source);
		return "errorsuccess";
	}
	
	
	public String specialFee(){
		System.out.println("in specialFee source: "+source);
		SpecialFeeDB.insertSpecialFee(eid,ticketingtype,servicefee,source);
		return "done";
	}
	
	public String getCCStatus(){
		System.out.println("in SpecialFeeAction getCCStatus");
		String cardstatus="Y";
		String mgrid=ActionContext.getContext().getParameters().get("mgrId").toString();
		if(!"Y".equals(cardstatus)){/*we hardcode this card status='Y' for disable the logic.If in case it is required then we remove this
              if condition and put cardstatus empty then this logic enabled.*/
		String accounttype=UpgradeEventDB.getAccountType(mgrid);
		if("basic".equalsIgnoreCase(accounttype)){
			cardstatus=CardProcessorDB.getManagerCCStatus(mgrid);
		    if(cardstatus==null){
		    	String cardRequired = DbUtil.getVal("select value from mgr_config where name='card.popup.required' and userid=CAST(? AS INTEGER)", new String[]{mgrid});
		    	if(cardRequired != null && cardRequired.equals("N"))
					cardstatus="Y";
		    	else
		    		cardstatus=""; 
		    }
		}else
			cardstatus="Y";
		}
	    JSONObject ccjson=new JSONObject();
		try{
			ccjson.put("cardstatus",cardstatus);
			ccjson.put("userid",mgrid);
		}catch(Exception e){
			System.out.println("exception in getCCStatus in specialfee action:"+eid); 
		}
		ccstatus=ccjson.toString();
		System.out.println("ccstatus is:"+ccstatus);
		return "ccjson";
	}
	
	public String getMgrCCStatus(){
		System.out.println("in SpecialFeeAction getMgrCCStatus mgrid: "+mgrid);
		Map session = ActionContext.getContext().getSession();
		String cardstatus="",cardRequired="",cardType="popup1";
		
		if(session.get("SESSION_USER")!=null && !"".equals(mgrid) && mgrid!=null && !"null".equals(mgrid))
			cardRequired=DbUtil.getVal("select name from mgr_config where name in('mgr.card.popup1.required','mgr.card.popup2.required','authorize','require_charge') and value='Y' and userid=CAST(? AS INTEGER)", new String[]{mgrid});
			
			
		if("require_charge".equals(cardRequired)){
				cardType="charge";
				cardstatus=cardType;
			
		}else if(cardRequired != null && !"".equals(cardRequired)){
			cardstatus=CardProcessorDB.getManagerCCStatus(mgrid);
			System.out.println("card status:"+cardstatus);
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
			ccjson.put("userid",mgrid);
		}catch(Exception e){
			System.out.println("exception in getCCStatus in specialfee action:"+eid); 
		}
		ccstatus=ccjson.toString();
		System.out.println("ccstatus is:"+ccstatus);
		return "ccjson";
	}
	
	public String getDuePopup(){
		return "duepopup";
	}
	
	public String getChargePopup(){
		System.out.println("get charge popup");
		return "chargepopup";
	}

	public String getPowertype() {
		return powertype;
	}

	public void setPowertype(String powertype) {
		this.powertype = powertype;
	}

	
	/*public String getInterceptMgrCCStatus(String userid){
		String cardstatus="";
		String cardRequired = DbUtil.getVal("select value from mgr_config where name='card.popup.required' and userid=CAST(? AS INTEGER)", new String[]{userid});
		if(cardRequired==null || "N".equalsIgnoreCase(cardRequired))
			cardstatus="don't askcard";
		else{
			cardstatus=CardProcessorDB.getManagerCCStatus(userid);
		if(cardstatus==null || "".equals(cardstatus))
			cardstatus="askcard";
		else
			cardstatus="don't askcard";
	  }
	return 	cardstatus;

   }*/

}