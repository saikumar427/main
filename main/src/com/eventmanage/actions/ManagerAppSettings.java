package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Locale;

import com.event.beans.ManagerAppData;
import com.event.dbhelpers.ManagerAppsDB;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class ManagerAppSettings extends ActionSupport implements Preparable,ValidationAware{
	
	private String eid="";
	public List<Entity> getAllTickets() {
		return allTickets;
	}

	public void setAllTickets(List<Entity> allTickets) {
		this.allTickets = allTickets;
	}

	public List<Entity> getSelTickets() {
		return selTickets;
	}

	public void setSelTickets(List<Entity> selTickets) {
		this.selTickets = selTickets;
	}

	private ManagerAppData appsData=new ManagerAppData();
	private String msgType="boxofficeapps";
	private List<Entity> allTickets=new ArrayList<Entity>();
	private List<Entity> selTickets=new ArrayList<Entity>();
	private String ticketstoshow;
	private String ticketsnottoshow;

	
	public String getTicketstoshow() {
		return ticketstoshow;
	}

	public void setTicketstoshow(String ticketstoshow) {
		this.ticketstoshow = ticketstoshow;
	}

	public String getTicketsnottoshow() {
		return ticketsnottoshow;
	}

	public void setTicketsnottoshow(String ticketsnottoshow) {
		this.ticketsnottoshow = ticketsnottoshow;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String execute(){
		System.out.println("event id is::"+eid);
		allTickets = ManagerAppsDB.getAllTickets(eid);
		selTickets = ManagerAppsDB.getSelTickets(eid);
		appsData=ManagerAppsDB.getManagerAppData(eid);
		return "success";
	}

	public String saveAppsSeetings(){
		boolean flag=validateData();
		if(flag){
			ManagerAppsDB.saveTicketsDisplay(ticketstoshow,ticketsnottoshow,eid);
			ManagerAppsDB.saveAppsData(eid,appsData);
		}
		else
		return "inputerror";	
		return "updated";
	}
	
	public boolean validateData(){
		ResourceBundle resourceBundle =I18n.getResourceBundle();
		String paymenttype=appsData.getEventbeeVendor();
		System.out.println("the payment type:::"+paymenttype);
		if("paypal_pro".equals(paymenttype)){
			
		}else if("authorize.net".equals(paymenttype)){
			if("".equals(appsData.getApiLoginId().trim()))
				addFieldError("appsData.getApiLoginId()",resourceBundle.getString("mg.api.lgn.id.emty.lbl"));
			if("".equals(appsData.getApiTransactionId().trim()))
				addFieldError("appsData.getApiTransactionId()",resourceBundle.getString("mg.api.trans.ky.emty.lbl"));
			
		}else if("braintree_manager".equals(paymenttype)){
			if("".equals(appsData.getBraintreekey().trim()))
				addFieldError("appsData.getBraintreekey()",resourceBundle.getString("mg.brntree.id.emty.lbl"));
			
			if("".equals(appsData.getBrainPubKey().trim()))
				addFieldError("appsData.getBrainPubKey()",resourceBundle.getString("mg.brntree.pub.ky.emty.lbl"));
			
			if("".equals(appsData.getBrainPriKey().trim()))
				addFieldError("appsData.getBrainPriKey()",resourceBundle.getString("mg.brntree.prv.ky.emty.lbl"));
			
		}else if("stripe".equals(paymenttype)){
			if("".equals(appsData.getStripekey().trim()))
				addFieldError("appsData.getStripekey()",resourceBundle.getString("mg.strp.mchnt.Api.Ky.emty.lbl"));
		}else{
			addFieldError("PaymentType",resourceBundle.getString("mg.slct.paymnt.type.mthd.lbl"));
		}
		if(!getFieldErrors().isEmpty()){
			return false;
		}
	return true;
	}
	
	
	public ManagerAppData getAppsData() {
		return appsData;
	}

	public void setAppsData(ManagerAppData appsData) {
		this.appsData = appsData;
	}
		
	
	
}
