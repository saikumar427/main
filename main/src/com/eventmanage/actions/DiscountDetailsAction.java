package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.event.beans.DiscountData;
import com.event.dbhelpers.DiscountsDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.EventHelperDB;
import com.event.dbhelpers.TicketsDB;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.ProfileValidator;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class DiscountDetailsAction extends ActionSupport implements Preparable,ValidationAware{
	/**
	 * 
	 */
	private static Logger log = Logger.getLogger(DiscountDetailsAction.class);
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";
	private String msgKey="";
	private String id="";
	private ArrayList alltickets=new ArrayList();
	private ArrayList seltickets=new ArrayList();
	private ArrayList typelist=new ArrayList();
	private ArrayList limitTypelist=new ArrayList();
	private DiscountData discountData;
	private String currency="";
	private ArrayList<Entity> hours = new ArrayList<Entity>();
	private ArrayList<Entity> minutes = new ArrayList<Entity>();
	private String jsonData="";
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DiscountData getDiscountData(){
		return discountData;
	}
	public void setDiscountData(DiscountData discountData){
		this.discountData=discountData;
	}
	
	public void prepare(){
		System.out.println("preparing discountDetails object");
		discountData=new DiscountData();
		clearFieldErrors();		
	}
	public String execute(){
		currency=EventDB.getCurrencySymbol(eid);
		populateRefData();
		DiscountsDB.getDiscountDefaultExpDate(discountData,eid);
		return "input";
	}
	
	public void setSeltickets(ArrayList seltickets){
		this.seltickets=seltickets;
	}
	
	public ArrayList getSeltickets(){
		return seltickets;
	}
	
	public void populateEventTickets(){
		alltickets=EventDB.getEventTicketsForDiscounts(eid);				
	}
	public void populateDiscountTypes(){
		typelist.clear();
		ResourceBundle resourceBundle =I18n.getResourceBundle();
		typelist.add(new Entity("ABSOLUTE",resourceBundle.getString("disc.amount.disc.lbl")));
		typelist.add(new Entity("PERCENTAGE",resourceBundle.getString("disc.percentage.dis.lbl")));				
	}
	public void populateDiscountLimitTypes(){
		limitTypelist.clear();
		limitTypelist.add(new Entity("1","No Limit"));
		limitTypelist.add(new Entity("2","Max Limit"));				
	}
	public void populateTime() {
		hours = TicketsDB.getHours();
		minutes = TicketsDB.getMinutes();
	}
	public void populateRefData(){
		populateEventTickets();
		populateDiscountTypes();
		populateDiscountLimitTypes();
		populateTime();
	}
	public boolean validateInputData(){
		/*log.info("discountData.getName: "+discountData.getName());
		log.info("discountData.getDiscountType: "+discountData.getDiscountType());
		log.info("discountData.getDiscountVal: "+discountData.getDiscountVal());
		log.info("discountData.getDiscountCodescsv: "+discountData.getDiscountCodescsv());
		log.info("discountData.getSeltickets: "+discountData.getSeltickets().size());*/
		ResourceBundle resourceBundle =I18n.getResourceBundle();
		if("".equals(discountData.getName().trim())){
			addFieldError("discountData.name", resourceBundle.getString("disc.enter.disc.name.errmsg"));
		}
		
		try{
		System.out.println("the discountData.getDiscountCodescsv()::"+discountData.getDiscountCodescsv());
			
		if("".equals(discountData.getDiscountCodescsv())){
			addFieldError("discountData.discountCodesList", resourceBundle.getString("disc.enter.disc.codes.errmsg"));
		}else if(!checkInvalidCodes(discountData.getDiscountCodescsv())){
			addFieldError("discountData.discountCodesList", resourceBundle.getString("disc.enter.valid.disc.codes.errmsg"));
		}
		else
		{
			String codeslist = "";
			List list = DiscountsDB.validateCodescsv(eid, discountData.getId(), discountData.getDiscountCodescsv());			
			for(int i=0;i<list.size();i++){
				if(i==0)
					codeslist = list.get(i).toString();
				else
					codeslist += ","+list.get(i).toString();
			}
			if(!"".equals(codeslist)){
				addFieldError("discountData.discountCodesList", resourceBundle.getString("disc.already.these.codes.are.used.errmsg")+": "+codeslist);
			}
		}
		}catch(Exception e){
			System.out.println("Exception occure in resourceBundleget String.. ");
			addFieldError("discountData.discountCodesList", resourceBundle.getString("disc.enter.valid.disc.codes.errmsg"));
		}
		
		System.out.println("after excprion is::");
		
		if("".equals(discountData.getDiscountType())){
			addFieldError("discountData.discountType", resourceBundle.getString("disc.select.disc.type.errmsg"));
		}		
		if("".equals(discountData.getDiscountVal())){
			addFieldError("discountData.discountVal", resourceBundle.getString("disc.enter.value.for.disc.errmsg"));
		}else{
			try{
				Double val = Double.parseDouble(discountData.getDiscountVal());
				
				if(Double.parseDouble(discountData.getDiscountVal())<0)
					addFieldError("discountData.discountVal", resourceBundle.getString("disc.enter.correct.value.for.disc.errmsg"));
				
				if(discountData.getDiscountType().equals("PERCENTAGE")){
					if(val>100){
						addFieldError("discountData.discountVal", resourceBundle.getString("disc.enter.correct.value.for.disc.errmsg"));
					}
				}
			}catch(Exception ex){
				addFieldError("discountData.discountVal", resourceBundle.getString("disc.enter.correct.value.for.disc.errmsg"));
			}
		}
		if("".equals(discountData.getLimitType())){
			addFieldError("discountData.discountLimitVal", resourceBundle.getString("disc.select.disc.limit.errmsg"));
		}else{
			if("2".equals(discountData.getLimitType())){
				if("".equals(discountData.getAllLimitValue().trim())){
					addFieldError("discountData.allLimitVal", resourceBundle.getString("disc.enter.value.for.limit.count.for.all.disc.codes.errmsg"));
				}else{
					try{
						int val = Integer.parseInt(discountData.getAllLimitValue().trim());						
					}catch(Exception ex){
						addFieldError("discountData.allLimitVal", resourceBundle.getString("disc.enter.correct.value.for.limit.count.for.all.disc.codes.errmsg"));
					}
				}
			}else if("3".equals(discountData.getLimitType())){
				if("".equals(discountData.getEachLimitValue().trim())){
					addFieldError("discountData.eachLimitVal", resourceBundle.getString("disc.enter.value.for.limit.count.per.disc.code.errmsg"));
				}else{
					try{
						int val = Integer.parseInt(discountData.getEachLimitValue().trim());						
					}catch(Exception ex){
						addFieldError("discountData.allLimitVal", resourceBundle.getString("disc.enter.correct.value.for.limit.count.per.disc.code.errmsg"));
					}
				}
			}
		}
		if(seltickets.size()==0){
			addFieldError("discountData.selectedTickets", resourceBundle.getString("disc.select.applicable.tickets.errmsg"));
		}
		if(!getFieldErrors().isEmpty()){
			//addFieldError("discountData.errorsExists", "There are some errors");
			return false;
		}
		return true;
	}
	
	public boolean checkInvalidCodes(String discountcodes){
		String[] codes=discountcodes.split(",");
		ProfileValidator pv=new ProfileValidator();
		boolean flag=true;
		for(int i=0;i<codes.length;i++)
		{
			if(!pv.checkNameValidity(codes[i].trim())){
				flag=false;
				break;
			}
		}
		return flag;
	}
	
	public String save(){
		boolean status=validateInputData();	
		if(status){
			if("".equals(discountData.getId()))
					msgKey="discounts.added";
			else
				msgKey="discounts.updated";
			System.out.println("In save method"+status);
			discountData.setSeltickets(seltickets);
			DiscountsDB.saveDiscountData(discountData, eid);
			EventHelperDB.removeGLobalEventCache(eid, "remove", "ticketsettings");
			return "success";
		}
		else{
			populateRefData();
			return "inputerror";
		}
	}
	public String edit(){
		currency=EventDB.getCurrencySymbol(eid);
		discountData=DiscountsDB.getDiscountData(eid,id);
		if(discountData==null){
			msgKey="discounts.idnotfound";
			return "success";
		}
		seltickets=discountData.getSeltickets();
		populateRefData();
		return "input";		
	}
	public ArrayList getAlltickets() {
		return alltickets;
	}
	public ArrayList getTypelist() {
		return typelist;
	}
	public ArrayList getLimitTypelist() {
		return limitTypelist;
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
	
	
}
