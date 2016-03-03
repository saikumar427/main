package com.mycommunities.actions;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.event.dbhelpers.DiscountsDB;
import com.event.dbhelpers.EventDB;
import com.eventbee.beans.Entity;
import com.mycommunities.beans.DiscountData;
import com.mycommunities.dbhelpers.CommunityDiscountDB;

public class CommunityDiscountDetailsAction extends MyCommunitiesWrapper{
	/**
	 * 
	 */
	private static Logger log = Logger.getLogger(CommunityDiscountDetailsAction.class);
	private static final long serialVersionUID = -8226266159829166013L;
	private String msgKey="";
	private String id="";
	private ArrayList<Entity> allItmes=new ArrayList<Entity>();
	private ArrayList<Entity> selItems=new ArrayList<Entity>();
	private ArrayList<Entity> typelist=new ArrayList<Entity>();
	private ArrayList<Entity> limitTypelist=new ArrayList<Entity>();
	private DiscountData discountData;
	private String currency="";
	
	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Entity> getAllItmes() {
		return allItmes;
	}

	public void setAllItmes(ArrayList<Entity> allItmes) {
		this.allItmes = allItmes;
	}

	public ArrayList<Entity> getSelItems() {
		return selItems;
	}

	public void setSelItems(ArrayList<Entity> selItems) {
		this.selItems = selItems;
	}

	public ArrayList<Entity> getTypelist() {
		return typelist;
	}

	public void setTypelist(ArrayList<Entity> typelist) {
		this.typelist = typelist;
	}

	public ArrayList<Entity> getLimitTypelist() {
		return limitTypelist;
	}

	public void setLimitTypelist(ArrayList<Entity> limitTypelist) {
		this.limitTypelist = limitTypelist;
	}

	public DiscountData getDiscountData() {
		return discountData;
	}

	public void setDiscountData(DiscountData discountData) {
		this.discountData = discountData;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		try {
			super.prepare();
			clearFieldErrors();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public String execute(){
		currency=EventDB.getCurrencySymbol(groupId);
		populateRefData();
		return "input";
	}
	
	public void populateRefData(){
		populateEventTickets();
		populateDiscountTypes();
		populateDiscountLimitTypes();
		
	}
	public void populateEventTickets(){
		if(groupId==null) groupId="";
		if(!"".equals(groupId))
		allItmes=CommunityDiscountDB.getMemberShips(groupId);				
	}
	public void populateDiscountTypes(){
		typelist.clear();
		typelist.add(new Entity("ABSOLUTE","Absolute"));
		typelist.add(new Entity("PERCENTAGE","Percentage"));				
	}
	public void populateDiscountLimitTypes(){
		limitTypelist.clear();
		limitTypelist.add(new Entity("1","No Limit"));
		limitTypelist.add(new Entity("2","Max Limit"));				
	}
	public boolean validateInputData(){
		log.info("discountData.getName: "+discountData.getName());
		log.info("discountData.getDiscountType: "+discountData.getDiscountType());
		log.info("discountData.getDiscountVal: "+discountData.getDiscountVal());
		log.info("discountData.getDiscountCodescsv: "+discountData.getDiscountCodescsv());
		log.info("discountData.getSeltickets: "+discountData.getSelItems().size());
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
		if(selItems.size()==0){
			addFieldError("discountData.selectedTickets", "Select Tickets");
		}
		if(!getFieldErrors().isEmpty()){
			//addFieldError("discountData.errorsExists", "There are some errors");
			return false;
		}
		return true;
	}
	public String save(){
		boolean status=validateInputData();	
		if(status){
			if("".equals(discountData.getId()))
					msgKey="discounts.added";
			else
				msgKey="discounts.updated";
			System.out.println("In save method"+status);
			discountData.setSelItems(selItems);
			CommunityDiscountDB.saveDiscountData(discountData, groupId);
			return "success";
		}
		else{
			populateRefData();
			return "input";
		}
	}
	public String edit(){
		currency=EventDB.getCurrencySymbol(groupId);
		discountData=CommunityDiscountDB.getDiscountData(groupId,id);
		if(discountData==null){
			msgKey="discounts.idnotfound";
			return "success";
		}
		selItems=discountData.getSelItems();
		populateRefData();
		return "input";		
	}
	
}

