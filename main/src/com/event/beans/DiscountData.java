package com.event.beans;

import java.util.ArrayList;

import com.eventbee.beans.DateTimeBean;

public class DiscountData {
private String id="";
private String name="";
private String description="";
private String discountVal="";
private String discountCodescsv="";
private String discountType="ABSOLUTE";
private String limitType="1";
private String allLimitValue="";
private String eachLimitValue="";
private String discountCode="";
private ArrayList seltickets=new ArrayList();
private ArrayList Codes=new ArrayList();
private String currency="";
private DateTimeBean expDateTimeBeanObj;
private String expType="N";
public String getDiscountCode() {
	return discountCode;
}
public void setDiscountCode(String discountCode) {
	this.discountCode = discountCode;
}
public String getCurrency() {
	return currency;
}
public void setCurrency(String currency) {
	this.currency = currency;
}
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
public ArrayList getSeltickets() {
	return seltickets;
}
public void setSeltickets(ArrayList seltickets) {
	this.seltickets = seltickets;
}
public ArrayList getCodes() {
	return Codes;
}
public void setCodes(ArrayList codes) {
	Codes = codes;
}
public String getDiscountCodescsv() {
	return discountCodescsv;
}
public String getExpType() {
	return expType;
}
public void setExpType(String expType) {
	this.expType = expType;
}
public void setDiscountCodescsv(String discountCodescsv) {
	this.discountCodescsv = discountCodescsv;
}
public String getAllLimitValue() {
	return allLimitValue;
}
public void setAllLimitValue(String allLimitValue) {
	this.allLimitValue = allLimitValue;
}
public String getEachLimitValue() {
	return eachLimitValue;
}
public void setEachLimitValue(String eachLimitValue) {
	this.eachLimitValue = eachLimitValue;
}
public DateTimeBean getExpDateTimeBeanObj() {
	return expDateTimeBeanObj;
}
public void setExpDateTimeBeanObj(DateTimeBean expDateTimeBeanObj) {
	this.expDateTimeBeanObj = expDateTimeBeanObj;
}


}
