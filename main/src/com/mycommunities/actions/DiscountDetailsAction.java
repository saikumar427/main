package com.mycommunities.actions;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.event.dbhelpers.DiscountsDB;
import com.event.dbhelpers.EventDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.ProfileValidator;
import com.mycommunities.beans.DiscountData;
import com.mycommunities.dbhelpers.CommunityDiscountDB;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class DiscountDetailsAction extends MyCommunitiesWrapper{
	/**
	 * 
	 */
	private static Logger log = Logger.getLogger(DiscountDetailsAction.class);
	private static final long serialVersionUID = -3773514102198232135L;

	private String msgKey="";
	private String id="";
	private ArrayList allitems=new ArrayList();
	private ArrayList selitems=new ArrayList();
	private ArrayList typelist=new ArrayList();
	private ArrayList limitTypelist=new ArrayList();
	private DiscountData discountData;
	private String currency="";
	
	public ArrayList getAllitems() {
		return allitems;
	}
	public void setAllitems(ArrayList allitems) {
		this.allitems = allitems;
	}
	public ArrayList getSelitems() {
		return selitems;
	}
	public void setSelitems(ArrayList selitems) {
		this.selitems = selitems;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public void setTypelist(ArrayList typelist) {
		this.typelist = typelist;
	}
	public void setLimitTypelist(ArrayList limitTypelist) {
		this.limitTypelist = limitTypelist;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
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
		currency=EventDB.getCurrencySymbol(groupId);
		populateRefData();
		return "input";
	}
	
	
	
	public void populateEventTickets(){
		if(groupId==null) groupId="";
		if(!"".equals(groupId))
		allitems=CommunityDiscountDB.getMemberShips(groupId);		
	}
	public void populateDiscountTypes(){
		typelist.clear();
		typelist.add(new Entity("ABSOLUTE","Amount Discount"));
		typelist.add(new Entity("PERCENTAGE","Percentage Discount"));				
	}
	public void populateDiscountLimitTypes(){
		limitTypelist.clear();
		limitTypelist.add(new Entity("1","No Limit"));
		limitTypelist.add(new Entity("2","Max Limit"));				
	}
	public void populateRefData(){
		populateEventTickets();
		populateDiscountTypes();
		populateDiscountLimitTypes();
		
	}
	public boolean validateInputData(){
		log.info("discountData.getName: "+discountData.getName());
		log.info("discountData.getDiscountType: "+discountData.getDiscountType());
		log.info("discountData.getDiscountVal: "+discountData.getDiscountVal());
		log.info("discountData.getDiscountCodescsv: "+discountData.getDiscountCodescsv());
		if("".equals(discountData.getName())){
			addFieldError("discountData.name", "Discount Name is required");
			
		}
		if("".equals(discountData.getDiscountType())){
			addFieldError("discountData.discountType", "Select Discount Type");
			
		}		
		if("".equals(discountData.getDiscountVal())){
			addFieldError("discountData.discountVal", "Enter a value for Discount");
		}else{
			try{
				Double val = Double.parseDouble(discountData.getDiscountVal());
				if(discountData.getDiscountType().equalsIgnoreCase("PERCENTAGE")){
					if(val>100){
						addFieldError("discountData.discountVal", "Enter correct value for Discount");
					}
				}
			}catch(Exception ex){
				addFieldError("discountData.discountVal", "Enter correct value for Discount");
			}
		}
		if("".equals(discountData.getLimitType())){
			addFieldError("discountData.discountLimitVal", "Select Discount Limit");
		}else{
			if(!"1".equals(discountData.getLimitType())){
				if("".equals(discountData.getLimitValue())){
					addFieldError("discountData.discountLimitVal", "Enter a value for Discount Limit");
				}else{
					try{
						int val = Integer.parseInt(discountData.getLimitValue());						
					}catch(Exception ex){
						addFieldError("discountData.discountLimitVal", "Enter correct value for Discount Limit");
					}
				}
			}
		}
		if("".equals(discountData.getDiscountCodescsv())){
			addFieldError("discountData.discountCodesList", "Enter Codes List");
		}else if(!checkInvalidCodes(discountData.getDiscountCodescsv())){
			addFieldError("discountData.discountCodesList", "Enter valid Discount Code(s). Only alphanumerics are allowed");
		}else{
			String codeslist = "";
			List list = DiscountsDB.validateCodescsv(groupId, discountData.getId(), discountData.getDiscountCodescsv());			
			for(int i=0;i<list.size();i++){
				if(i==0)
					codeslist = list.get(i).toString();
				else
					codeslist += ","+list.get(i).toString();
			}
			if(!"".equals(codeslist)){
				addFieldError("discountData.discountCodesList", "Already these codes are used: "+codeslist);
			}
		}
		if(selitems.size()==0){
			addFieldError("discountData.selectedTickets", "Select Memberships");
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
			if(!pv.checkNameValidity(codes[i])){
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
			discountData.setSelItems(selitems);
			CommunityDiscountDB.saveDiscountData(discountData,groupId);
			return "success";
		}
		else{
			System.out.println("entered in else");
			populateRefData();
			return "inputerror";
		}
	}
	public String edit(){
		currency=EventDB.getCurrencySymbol(groupId);
		discountData=CommunityDiscountDB.getDiscountData(groupId, id);
		if(discountData==null){
			msgKey="discounts.idnotfound";
			return "success";
		}
		selitems=discountData.getSelItems();
		populateRefData();
		return "input";		
	}
	
	public ArrayList getTypelist() {
		return typelist;
	}
	public ArrayList getLimitTypelist() {
		return limitTypelist;
	}
	
}
