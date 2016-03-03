package com.mycommunities.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class DiscountData implements Serializable{

	private static final long serialVersionUID = 4908041474852290721L;
	private String id="";
	private String name="";
	private String description="";
	private String discountVal="";
	private String discountCodescsv="";
	private String discountType="ABSOLUTE";
	private String limitType="1";
	private String limitValue="";
	private ArrayList selItems=new ArrayList();
	private ArrayList Codes=new ArrayList();
	private String currency="";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDiscountVal() {
		return discountVal;
	}
	public void setDiscountVal(String discountVal) {
		this.discountVal = discountVal;
	}
	public String getDiscountCodescsv() {
		return discountCodescsv;
	}
	public void setDiscountCodescsv(String discountCodescsv) {
		this.discountCodescsv = discountCodescsv;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getLimitType() {
		return limitType;
	}
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
	public String getLimitValue() {
		return limitValue;
	}
	public void setLimitValue(String limitValue) {
		this.limitValue = limitValue;
	}
	public ArrayList getSelItems() {
		return selItems;
	}
	public void setSelItems(ArrayList selItems) {
		this.selItems = selItems;
	}
	public ArrayList getCodes() {
		return Codes;
	}
	public void setCodes(ArrayList codes) {
		Codes = codes;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
