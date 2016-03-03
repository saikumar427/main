package com.event.rsvp.beans;

import java.util.ArrayList;

public class RSVPReportsData {
	private String firstName="";
	private String lastName="";
	private String phone="";
	private String email="";
	private String notes="";
	private String responseType="";
	private String bookingSource="";
	private String tid="";
	private String scanId="";
	private String trackCode="";
	private String date="";
	private String rsvpreportsradio="response";
	private String attselindex="1";
	private String respstartMonth="";
	private String respstartDay="";
	private String respstartYear="";
	private String respendMonth="";
	private String respendDay="";
	private String respendYear="";
	private String respsMonth="";
	private String respsDay="";
	private String respsYear="";
	private String respeMonth="";
	private String respeDay="";
	private String respeYear="";
	private String checkinreportsindex="1";
	private String tdateradio="1";
	private String resptdateradio="1";
	private String checkDateRadio="1";
	private String dir="asc";
	private String sortedkey="";
	private String eventStartDate="";
	private String eventEndDate="";
	private String searchOn="";
	private String searchContent="";
	private String checkInStartDate="";
	private String checkInEndDate="";
	private boolean isSearch=false;
	private ArrayList<String> bookingSourceType = new ArrayList<String>();//new ArrayList<String>(){{add("Online");add("Manual");add("trackurls");}};
	private ArrayList<String> attendeeType = new ArrayList<String>();
	
	public boolean getIsSearch() {
		return isSearch;
	}
	public void setIsSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}
	public String getCheckInStartDate() {
		return checkInStartDate;
	}
	public void setCheckInStartDate(String checkInStartDate) {
		this.checkInStartDate = checkInStartDate;
	}
	public String getCheckInEndDate() {
		return checkInEndDate;
	}
	public void setCheckInEndDate(String checkInEndDate) {
		this.checkInEndDate = checkInEndDate;
	}
	public String getSearchOn() {
		return searchOn;
	}
	public void setSearchOn(String searchOn) {
		this.searchOn = searchOn;
	}
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	public String getEventStartDate() {
		return eventStartDate;
	}
	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	public String getEventEndDate() {
		return eventEndDate;
	}
	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	
	public String getTdateradio() {
		return tdateradio;
	}
	public void setTdateradio(String tdateradio) {
		this.tdateradio = tdateradio;
	}
	public String getAttselindex() {
		return attselindex;
	}
	public void setAttselindex(String attselindex) {
		this.attselindex = attselindex;
	}
	public String getRsvpreportsradio() {
		return rsvpreportsradio;
	}
	public void setRsvpreportsradio(String rsvpreportsradio) {
		this.rsvpreportsradio = rsvpreportsradio;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getBookingSource() {
		return bookingSource;
	}
	public void setBookingSource(String bookingSource) {
		this.bookingSource = bookingSource;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getTrackCode() {
		return trackCode;
	}
	public void setTrackCode(String trackCode) {
		this.trackCode = trackCode;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<String> getBookingSourceType() {
		return bookingSourceType;
	}
	public void setBookingSourceType(ArrayList<String> bookingSourceType) {
		this.bookingSourceType = bookingSourceType;
	}
	public String getRespstartMonth() {
		return respstartMonth;
	}
	public void setRespstartMonth(String respstartMonth) {
		this.respstartMonth = respstartMonth;
	}
	public String getRespstartDay() {
		return respstartDay;
	}
	public void setRespstartDay(String respstartDay) {
		this.respstartDay = respstartDay;
	}
	public String getRespstartYear() {
		return respstartYear;
	}
	public void setRespstartYear(String respstartYear) {
		this.respstartYear = respstartYear;
	}
	public String getRespendMonth() {
		return respendMonth;
	}
	public void setRespendMonth(String respendMonth) {
		this.respendMonth = respendMonth;
	}
	public String getRespendDay() {
		return respendDay;
	}
	public void setRespendDay(String respendDay) {
		this.respendDay = respendDay;
	}
	public String getRespendYear() {
		return respendYear;
	}
	public void setRespendYear(String respendYear) {
		this.respendYear = respendYear;
	}
	public String getRespsMonth() {
		return respsMonth;
	}
	public void setRespsMonth(String respsMonth) {
		this.respsMonth = respsMonth;
	}
	public String getRespsDay() {
		return respsDay;
	}
	public void setRespsDay(String respsDay) {
		this.respsDay = respsDay;
	}
	public String getRespsYear() {
		return respsYear;
	}
	public void setRespsYear(String respsYear) {
		this.respsYear = respsYear;
	}
	public String getRespeMonth() {
		return respeMonth;
	}
	public void setRespeMonth(String respeMonth) {
		this.respeMonth = respeMonth;
	}
	public String getRespeDay() {
		return respeDay;
	}
	public void setRespeDay(String respeDay) {
		this.respeDay = respeDay;
	}
	public String getRespeYear() {
		return respeYear;
	}
	public void setRespeYear(String respeYear) {
		this.respeYear = respeYear;
	}
	public String getResptdateradio() {
		return resptdateradio;
	}
	public void setResptdateradio(String resptdateradio) {
		this.resptdateradio = resptdateradio;
	}
	public String getCheckinreportsindex() {
		return checkinreportsindex;
	}
	public void setCheckinreportsindex(String checkinreportsindex) {
		this.checkinreportsindex = checkinreportsindex;
	}
	public ArrayList<String> getAttendeeType() {
		return attendeeType;
	}
	public void setAttendeeType(ArrayList<String> attendeeType) {
		this.attendeeType = attendeeType;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getSortedkey() {
		return sortedkey;
	}
	public void setSortedkey(String sortedkey) {
		this.sortedkey = sortedkey;
	}
	public String getCheckDateRadio() {
		return checkDateRadio;
	}
	public void setCheckDateRadio(String checkDateRadio) {
		this.checkDateRadio = checkDateRadio;
	}
	
}