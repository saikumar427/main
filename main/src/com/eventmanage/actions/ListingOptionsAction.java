package com.eventmanage.actions;

import com.event.beans.AddEventData;
import com.event.beans.ListingOptionsData;
import com.event.dbhelpers.UpgradeEventDB;
import com.eventbee.general.EbeeConstantsF;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.myevents.actions.MyEventsActionWrapper;

public class ListingOptionsAction extends MyEventsActionWrapper implements Preparable,ValidationAware{
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";
	private AddEventData addEventData=new AddEventData();
	private ListingOptionsData listingOptionsData=new ListingOptionsData();
	private String accounttype=null;
	private String premiumlevel="";
	private String msgKey = "";
	private String totalAmount="";
	private String upgradeEventSeqId="";
	private String selectedoption="";
	private String BASE_REF="";
	private String premiumType="";
	
	public String getPremiumType() {
		return premiumType;
	}
	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}
	public String getBASE_REF() {
		return BASE_REF;
	}
	public void setBASE_REF(String base_ref) {
		BASE_REF = base_ref;
	}
	public String getSelectedoption() {
		return selectedoption;
	}
	public void setSelectedoption(String selectedoption) {
		this.selectedoption = selectedoption;
	}
	public String getUpgradeEventSeqId() {
		return upgradeEventSeqId;
	}
	public void setUpgradeEventSeqId(String upgradeEventSeqId) {
		this.upgradeEventSeqId = upgradeEventSeqId;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getPremiumlevel() {
		return premiumlevel;
	}
	public void setPremiumlevel(String premiumlevel) {
		this.premiumlevel = premiumlevel;
	}
	public ListingOptionsData getListingOptionsData() {
		return listingOptionsData;
	}
	public void setListingOptionsData(ListingOptionsData listingOptionsData) {
		this.listingOptionsData = listingOptionsData;
	}
	public String getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	
	public AddEventData getAddEventData() {
		return addEventData;
	}
	public void setAddEventData(AddEventData addEventData) {
		this.addEventData = addEventData;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	
	public void prepare(){	
		System.out.println("Mgr ID before super prepare:"+mgrId);
		try {
			super.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		System.out.println("Mgr ID after super prepare:"+mgrId);
		//eventData=new EventData();
	}
	public void updateEventInfo(){
		UpgradeEventDB.updateEventInfo(eid,listingOptionsData);
	}
	public String success(){
		//updateEventInfo();
		return "updated";
	}
	public String execute(){
		premiumlevel=UpgradeEventDB.getPremiumLevel(eid);
		if("".equals(premiumlevel)) premiumlevel="Free";
		listingOptionsData.setPremiumlisttype(premiumlevel);
		accounttype=UpgradeEventDB.getAccountType(mgrId);
		if("Gold".equals(accounttype)){
			listingOptionsData.setPremiumprice("Free");
			listingOptionsData.setFeaturedprice("$"+EbeeConstantsF.get("accounts.event."+accounttype.toLowerCase()+".featured","29.95"));
			listingOptionsData.setCustomprice(EbeeConstantsF.get("accounts.event."+accounttype.toLowerCase()+".custom1","$99.95"));
		}else if("Platinum".equals(accounttype)){
			listingOptionsData.setPremiumprice("Free");
			listingOptionsData.setFeaturedprice("Free");	
			listingOptionsData.setCustomprice(EbeeConstantsF.get("accounts.event."+accounttype.toLowerCase()+".custom1","$99.95"));		
		}else{
			listingOptionsData.setPremiumprice("$9.95");
			listingOptionsData.setFeaturedprice("$29.95");
			listingOptionsData.setCustomprice("$99.95");
		}
		upgradeEventSeqId=UpgradeEventDB.insertUpgradeData(eid,premiumlevel);
		listingOptionsData.setSeqId(upgradeEventSeqId);
		return "success";
	}
	public String insertUpgradeData(){	
		UpgradeEventDB.update(eid,selectedoption,totalAmount,upgradeEventSeqId);
		//msgKey = upgradeEventSeqId;		
		return "ajaxmsg";
	}
	public String getPaymentScreen(){
		String totalAmount=UpgradeEventDB.getTotalAmount(eid,upgradeEventSeqId);		
		listingOptionsData.setTotalAmount(totalAmount);
		String creditCardScreenData=UpgradeEventDB.creditCardScreenData(eid,upgradeEventSeqId,totalAmount);		
		listingOptionsData.setCreditCardScreenData(creditCardScreenData);
		listingOptionsData.setSeqId(upgradeEventSeqId);
		return "paymentScreen";
	}
	public String ccValidateAction(){
	
		String result=UpgradeEventDB.validatePayment(listingOptionsData, eid,BASE_REF);
		return "success";
	}
	public String updateMessage(){
		return "updatedMsg";
	}
	public String updatePremium(){		
		UpgradeEventDB.updatePremiumLevel(eid,premiumType);
		return "success";
	}
}
