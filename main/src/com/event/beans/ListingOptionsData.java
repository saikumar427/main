package com.event.beans;

public class ListingOptionsData {
	private String customprice="";
	private String premiumprice="";
	private String featuredprice="";
	private String premiumlisttype="";
	private String seqId="";
	private String selectedoption="";
	private String totalAmount="";
	private String creditCardScreenData="";
	public String getCreditCardScreenData() {
		return creditCardScreenData;
	}
	public void setCreditCardScreenData(String creditCardScreenData) {
		this.creditCardScreenData = creditCardScreenData;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getSelectedoption() {
		return selectedoption;
	}
	public void setSelectedoption(String selectedoption) {
		this.selectedoption = selectedoption;
	}
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public String getPremiumlisttype() {
		return premiumlisttype;
	}
	public void setPremiumlisttype(String premiumlisttype) {
		this.premiumlisttype = premiumlisttype;
	}
	public String getCustomprice() {
		return customprice;
	}
	public void setCustomprice(String customprice) {
		this.customprice = customprice;
	}
	public String getPremiumprice() {
		return premiumprice;
	}
	public void setPremiumprice(String premiumprice) {
		this.premiumprice = premiumprice;
	}
	public String getFeaturedprice() {
		return featuredprice;
	}
	public void setFeaturedprice(String featuredprice) {
		this.featuredprice = featuredprice;
	}
	
}
