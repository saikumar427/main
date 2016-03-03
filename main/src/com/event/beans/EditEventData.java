package com.event.beans;

import java.util.ArrayList;
import java.util.List;

import com.eventbee.beans.Entity;

public class EditEventData  {
	private String textcontent=""; 
	private String eventTitle="";
	private String venue="";
	private String tags="";
	private String city="";
	private String email="";
	private String listtype="";
	private String listingprivacy="";
	private String address1="";
	private String address2="";
	private String country="";
	private String state="";
	private String region="";
	private String category="";
	private String subcategory="";
	private String description="";
	private String descriptiontype="";
	private String startyear="";
	private String startmonth="";
	private String startday="";
	private String endyear="";
	private String endmonth="";
	private String endday="";
	private String name="";
	private String timezones="";
	private List<Entity> recurringDates;
	private String startHour="";
	private String startminute="";
	private String hours="";
	private String endhours="";
	private String startampm="AM";
	private String endampm="AM";
	private String endHour="";
	private String endminute="";
	private String mgrId="";
	private String recurringcheck="";
	private String latitude="";
	private String longitude="";
    private String i18nLang="en_US";
	
	public String getI18nLang() {
		return i18nLang;
	}
	public void setI18nLang(String i18nLang) {
		this.i18nLang = i18nLang;
	}
	public String getMgrId() {
		return mgrId;
	}
	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}
	public String getEndminute() {
		return endminute;
	}
	public void setEndminute(String endminute) {
		this.endminute = endminute;
	}
	public String getEndhours() {
		return endhours;
	}
	public void setEndhours(String endhours) {
		this.endhours = endhours;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public String getEndampm() {
		return endampm;
	}
	public void setEndampm(String endampm) {
		this.endampm = endampm;
	}
	public String getStartampm() {
		return startampm;
	}
	public void setStartampm(String startampm) {
		this.startampm = startampm;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getStartminute() {
		return startminute;
	}
	public void setStartminute(String startminute) {
		this.startminute = startminute;
	}
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public EditEventData(){
		recurringDates = new ArrayList<Entity>(); 
	}
	public List<Entity> getRecurringDates() {
		return recurringDates;
	}
	public void setRecurringDates(List<Entity> recurringDates) {
		this.recurringDates = recurringDates;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimezones() {
		return timezones;
	}
	public void setTimezones(String timezones) {
		this.timezones = timezones;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getListtype() {
		return listtype;
	}
	public void setListtype(String listtype) {
		this.listtype = listtype;
	}
	public String getListingprivacy() {
		return listingprivacy;
	}
	public void setListingprivacy(String listingprivacy) {
		this.listingprivacy = listingprivacy;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescriptiontype() {
		return descriptiontype;
	}
	public void setDescriptiontype(String descriptiontype) {
		this.descriptiontype = descriptiontype;
	}
	public String getStartyear() {
		return startyear;
	}
	public void setStartyear(String startyear) {
		this.startyear = startyear;
	}
	public String getStartmonth() {
		return startmonth;
	}
	public void setStartmonth(String startmonth) {
		this.startmonth = startmonth;
	}
	public String getStartday() {
		return startday;
	}
	public void setStartday(String startday) {
		this.startday = startday;
	}
	public String getEndyear() {
		return endyear;
	}
	public void setEndyear(String endyear) {
		this.endyear = endyear;
	}
	public String getEndmonth() {
		return endmonth;
	}
	public void setEndmonth(String endmonth) {
		this.endmonth = endmonth;
	}
	public String getEndday() {
		return endday;
	}
	public void setEndday(String endday) {
		this.endday = endday;
	}
	
	public String getTextcontent() {
		return textcontent;
	}
	public void setTextcontent(String textcontent) {
		this.textcontent = textcontent;
	}
	public String getRecurringcheck() {
		return recurringcheck;
	}
	public void setRecurringcheck(String recurringcheck) {
		this.recurringcheck = recurringcheck;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
	
	
	

