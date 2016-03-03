package com.event.beans;

import java.util.Date;

public class EventData {
	private String eventName="";
	private String eventId="";
	private String venue="";
	private String startDate="";
	private String mgrId="";
	private String email="";
	private String address1="";
	private String address2="";
	private String city="";
	private String state="";
	private String country="";
	private String status="";
	private String start_date="";
	private String starttime="";
	private String endtime="";
	private String end_date="";
	private String phone="";
	private String evt_end_date="";
	private String evt_start_date="";
	private String premiumlevel="";
	private String location="";
	private String powertype="";
	private String currencytype="";
	private String poweredbyEB="";
	private String total="";
	private String username="";
	private String eventURL="";
	private String userurl="";
	private String serverAddress="";
	private String faceBookTicketingPageURL="";
	private Date formattedStartDate;
	private Date formattedEndDate;
	private Date formattedListDate;
	private String rsvplimit="1";
	private String rsvptype="0";
	private String unitId="13579";
	private String managerId="";
	private String listName="";
	private String listDesc="";
	private String groupId="";
	private String listId="";
	private String authRole="MGR";
	private String unSubMessage="";
	private String attendingevent="";
	private String rsvpcount="";
	private String eventPageViews="";
	private String evt_listed_date="";
	private String listed_date="";
	private String hostName="";
	private String hostEmail="";
	private String rsvpattendeelimit="1";
	private String rsvpnotsurelimit="0";
	private String notatttype="";
	private String notsuretype="";
	private String facebookeventid="";
	private String description="";
	private String ntsCommission="";
	private String ntsStatus="";
	private String currentLevel="";
	private String currentFee="";
	private String isNonProfit="N";
	
	public String getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}
	public String getFacebookeventid() {
		return facebookeventid;
	}
	public void setFacebookeventid(String facebookeventid) {
		this.facebookeventid = facebookeventid;
	}
	public String getNotsuretype() {
		return notsuretype;
	}
	public void setNotsuretype(String notsuretype) {
		this.notsuretype = notsuretype;
	}
	public String getNotatttype() {
		return notatttype;
	}
	public void setNotatttype(String notatttype) {
		this.notatttype = notatttype;
	}
	public String getRsvpattendeelimit() {
		return rsvpattendeelimit;
	}
	public void setRsvpattendeelimit(String rsvpattendielimit) {
		this.rsvpattendeelimit = rsvpattendielimit;
	}
	public String getRsvpnotsurelimit() {
		return rsvpnotsurelimit;
	}
	public void setRsvpnotsurelimit(String rsvpnotsurelimit) {
		this.rsvpnotsurelimit = rsvpnotsurelimit;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHostEmail() {
		return hostEmail;
	}
	public void setHostEmail(String hostEmail) {
		this.hostEmail = hostEmail;
	}
	public String getEvt_listed_date() {
		return evt_listed_date;
	}
	public void setEvt_listed_date(String evt_listed_date) {
		this.evt_listed_date = evt_listed_date;
	}
	public String getListed_date() {
		return listed_date;
	}
	public void setListed_date(String listed_date) {
		this.listed_date = listed_date;
	}
	public String getEventPageViews() {
		return eventPageViews;
	}
	public void setEventPageViews(String eventPageViews) {
		this.eventPageViews = eventPageViews;
	}
	public String getAttendingevent() {
		return attendingevent;
	}
	public void setAttendingevent(String attendingevent) {
		this.attendingevent = attendingevent;
	}
	public String getRsvpcount() {
		return rsvpcount;
	}
	public void setRsvpcount(String rsvpcount) {
		this.rsvpcount = rsvpcount;
	}
	public String getUnSubMessage() {
		return unSubMessage;
	}
	public void setUnSubMessage(String unSubMessage) {
		this.unSubMessage = unSubMessage;
	}
	public String getAuthRole() {
		return authRole;
	}
	public void setAuthRole(String authRole) {
		this.authRole = authRole;
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public Date getFormattedListDate() {
		return formattedListDate;
	}
	public void setFormattedListDate(Date formattedListDate) {
		this.formattedListDate = formattedListDate;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getListDesc() {
		return listDesc;
	}
	public void setListDesc(String listDesc) {
		this.listDesc = listDesc;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getRsvplimit() {
		return rsvplimit;
	}
	public void setRsvplimit(String rsvplimit) {
		this.rsvplimit = rsvplimit;
	}
	public String getRsvptype() {
		return rsvptype;
	}
	public void setRsvptype(String rsvptype) {
		this.rsvptype = rsvptype;
	}
	public String getFaceBookTicketingPageURL() {
		return faceBookTicketingPageURL;
	}
	public void setFaceBookTicketingPageURL(String faceBookTicketingPageURL) {
		this.faceBookTicketingPageURL = faceBookTicketingPageURL;
	}
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public String getUserurl() {
		return userurl;
	}
	public void setUserurl(String userurl) {
		this.userurl = userurl;
	}
	public String getEventURL() {
		return eventURL;
	}
	public void setEventURL(String eventURL) {
		this.eventURL = eventURL;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPoweredbyEB() {
		return poweredbyEB;
	}
	public void setPoweredbyEB(String poweredbyEB) {
		this.poweredbyEB = poweredbyEB;
	}
	public String getEventId() {
			return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getCurrencytype() {
		return currencytype;
	}
	public void setCurrencytype(String currencytype) {
		this.currencytype = currencytype;
	}
	public String getPowertype() {
		return powertype;
	}
	public void setPowertype(String powertype) {
		this.powertype = powertype;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPremiumlevel() {
		return premiumlevel;
	}
	public void setPremiumlevel(String premiumlevel) {
		this.premiumlevel = premiumlevel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEvt_end_date() {
		return evt_end_date;
	}
	public void setEvt_end_date(String evt_end_date) {
		this.evt_end_date = evt_end_date;
	}
	public String getEvt_start_date() {
		return evt_start_date;
	}
	public void setEvt_start_date(String evt_start_date) {
		this.evt_start_date = evt_start_date;
	}
	public String getMgrId() {
		return mgrId;
	}
	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @param formattedStartDate the formattedStartDate to set
	 */
	public void setFormattedStartDate(Date formattedStartDate) {
		this.formattedStartDate = formattedStartDate;
	}
	/**
	 * @return the formattedStartDate
	 */
	public Date getFormattedStartDate() {
		return formattedStartDate;
	}
	/**
	 * @param formattedEndDate the formattedEndDate to set
	 */
	public void setFormattedEndDate(Date formattedEndDate) {
		this.formattedEndDate = formattedEndDate;
	}
	/**
	 * @return the formattedEndDate
	 */
	public Date getFormattedEndDate() {
		return formattedEndDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNtsCommission() {
		return ntsCommission;
	}
	public void setNtsCommission(String ntsCommission) {
		this.ntsCommission = ntsCommission;
	}
	public String getNtsStatus() {
		return ntsStatus;
	}
	public void setNtsStatus(String ntsStatus) {
		this.ntsStatus = ntsStatus;
	}
	public String getCurrentFee() {
		return currentFee;
	}
	public void setCurrentFee(String currentFee) {
		this.currentFee = currentFee;
	}
	public String getIsNonProfit() {
		return isNonProfit;
	}
	public void setIsNonProfit(String isNonProfit) {
		this.isNonProfit = isNonProfit;
	}

}
