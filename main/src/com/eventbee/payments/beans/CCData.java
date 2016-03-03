package com.eventbee.payments.beans;

public class CCData {
	private String cardnumber="";
	private String cvvcode="";
	private String creditcardtype="";
	private String expiremonth="";
	private String expireyear="";
	private String firstname="";
	private String lastname="";
    private String address="";
    private String aptsuite="";
    private String city="";
	private String country="";
	private String zip="";
	private String state="";
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getCvvcode() {
		return cvvcode;
	}
	public void setCvvcode(String cvvcode) {
		this.cvvcode = cvvcode;
	}
	public String getCreditcardtype() {
		return creditcardtype;
	}
	public void setCreditcardtype(String creditcardtype) {
		this.creditcardtype = creditcardtype;
	}
	public String getExpiremonth() {
		return expiremonth;
	}
	public void setExpiremonth(String expiremonth) {
		this.expiremonth = expiremonth;
	}
	public String getExpireyear() {
		return expireyear;
	}
	public void setExpireyear(String expireyear) {
		this.expireyear = expireyear;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAptsuite() {
		return aptsuite;
	}
	public void setAptsuite(String aptsuite) {
		this.aptsuite = aptsuite;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
