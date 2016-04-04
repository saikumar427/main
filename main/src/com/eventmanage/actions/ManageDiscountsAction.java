package com.eventmanage.actions;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.event.beans.DiscountData;
import com.event.dbhelpers.DiscountsDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.EventHelperDB;
import com.eventmanage.helpers.JsonBuilderHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class ManageDiscountsAction extends ActionSupport implements Preparable,ValidationAware{
	
	/**
	 * 
	 */
	private static Logger log = Logger.getLogger(ManageDiscountsAction.class);
	private String id ="";	
	private String eid="";
	private String msgKey="";
	private ArrayList<DiscountData> discountsList=new ArrayList<DiscountData>();
	private static final long serialVersionUID = -89331838884426105L;
	private String jsonData = "";
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public void prepare(){
		msgKey="";		
	}
	public String execute()
	{
		String curLvl=ActionContext.getContext().getParameters().get("curLvl").toString();
		String pwrType=ActionContext.getContext().getParameters().get("pwrType").toString();
		int ManageDiscounts = 200;
		System.out.println("Current Level : "+curLvl+" , Power Type : "+pwrType+" & EventId : "+eid);
		if(Integer.parseInt(curLvl)>=ManageDiscounts)
			return "success";
		else
			return "pageRedirect";
				
	}
	public String populateDiscountsDetails(){
		String eventURL=DiscountsDB.populateEventURL(eid);
		jsonData=DiscountsDB.getEventDiscountsList(eid,eventURL).toString();
		//int tktcount=DiscountsDB.getCount(eid);
	//	jsonData = JsonBuilderHelper.getDiscountsJson(discountsList,eventURL,eid,tktcount).toString();
		return "jsondata";
	}
	
	public String getReloadedDiscountData(){
		populateDiscountsDetails();
	return "reloadeddiscount";
	}
	
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public ArrayList<DiscountData> getDiscountsList() {
		return discountsList;
	}
	public void setDiscountsList(ArrayList<DiscountData> discountsList) {
		this.discountsList = discountsList;
	}
	public String delete(){
		log.info("deleting... discounts");
		DiscountsDB.deleteDiscountData(eid,id);		
		EventHelperDB.removeGLobalEventCache(eid, "remove", "ticketsettings");
		msgKey="discount.deleted";
		return "deleted";		
	}
	

}
