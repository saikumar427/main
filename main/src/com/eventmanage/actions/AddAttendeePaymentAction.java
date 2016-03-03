package com.eventmanage.actions;

import java.util.HashMap;

import org.json.JSONObject;

import com.eventbee.general.DbUtil;
import com.eventregister.dbhelper.RegistrationTiketingManager;
import com.opensymphony.xwork2.ActionSupport;

public class AddAttendeePaymentAction extends ActionSupport{
	private String eid="";
	private String tid="";
	private String pctype="";
	private String pctypeobj="";
	private String collecteTktQty="";
	private String currencyCode="";
	RegistrationTiketingManager regTktMgr=new RegistrationTiketingManager();
	
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	
	public String getPctype() {
		return pctype;
	}
	public void setPctype(String pctype) {
		this.pctype = pctype;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getPctypeobj() {
		return pctypeobj;
	}
	public void setPctypeobj(String pctypeobj) {
		this.pctypeobj = pctypeobj;
	}
	
	public String execute(){
		
		collecteTktQty=DbUtil.getVal("select collected_ticketqty from event_reg_details_temp where tid=?",new String[]{tid});
		currencyCode=DbUtil.getVal("select currency_code from event_currency where eventid=?",new String[]{eid});
		System.out.println("AddAttendeePaymentAction eid: "+eid+" tid: "+tid+" collecteTktQty: "+collecteTktQty+" currencyCode: "+currencyCode);
		return "success";
	}
	
	public String submitPayment(){
		System.out.println("submitPayment eid: "+eid+" tid: "+tid+" pctype: "+pctype);
		try{
			String status = DbUtil.getVal("select 'yes' from event_reg_transactions where eventid=? and tid=? ", new String[]{eid,tid});
			if(!"yes".equals(status)) status="no";
			JSONObject obj=new JSONObject();
			if(!"yes".equals(status) && "cc".equals(pctype)){
				HashMap<String,String> hm = regTktMgr.getRegTotalAmounts(tid);
				String vendor = DbUtil.getVal("select value from config where config_id='0' and name='eventbee.cc.vendor'", null);
				if(vendor==null || "".equals(vendor)) vendor="Braintree";
				
				obj.put("vendor",vendor);
				obj.put("grandtotamount",hm.get("grandtotamount"));
				
				String ccvendor="";
				if("Braintree".equalsIgnoreCase(vendor)) ccvendor="braintree_eventbee";
				else ccvendor=vendor;
				
				regTktMgr.updatePaytype(tid, "eventbee",ccvendor);
			}
			//else regTktMgr.updatePaytype(tid, "confirmation page");
			System.out.println("##### AddAttendeePaymentAction submitPayment() tid: "+tid+" alreadydone: "+status);
			obj.put("alreadydone",status);
			obj.put("pctype",pctype);
			pctypeobj=obj.toString();
		}catch(Exception e){
			System.out.println("exception in AddAttendeePaymentAction.java updatePayment() error: "+e.getMessage());
		}
		return "update";
	}
	public String getCollecteTktQty() {
		return collecteTktQty;
	}
	public void setCollecteTktQty(String collecteTktQty) {
		this.collecteTktQty = collecteTktQty;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
