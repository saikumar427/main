package com.event.beans;

import java.util.ArrayList;

import com.eventbee.beans.DateTimeBean;
import com.eventbee.beans.Entity;

public class EmailAttendeesData {
	private ArrayList<Entity> contentlist=new ArrayList<Entity>();
	private ArrayList<Entity> recurringlist=new ArrayList<Entity>();
	private String descriptiontype="wysiwyg";
	private String description="";
	private String email="";
	private String count="";
	private String listTo="";
	private String hours="";
	private String months="";
	private String dates="";
	private String years="";
	private String mailtotest="";
	private String subject="";
	private String listid="";
	private String listName="";
	private String day="";
	private String month="";
	private String year="";
	private String now="";
	private String time="";
	private String sendAt="now";
	private String blastid="";
	private String schTime="";
	private String buyer="Y";
	private String attendee="";
	private String content="";
	private String notify="Y";
	private String hour="";
	private String minutes="";
	private String radio="sendnow";
	private DateTimeBean dateTimeBeanObj;
	private String seldate="";
	private String selectAttendeeType="";
	private String startDate="";
	private String tidList="";
	private String selectedTickets="";
	private String sendTo="ALL";
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getSeldate() {
		return seldate;
	}

	public void setSeldate(String seldate) {
		this.seldate = seldate;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getAttendee() {
		return attendee;
	}

	public void setAttendee(String attendee) {
		this.attendee = attendee;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;    
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	
	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getListid() {
		return listid;
	}

	public void setListid(String listid) {
		this.listid = listid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMailtotest() {
		return mailtotest;
	}

	public void setMailtotest(String mailtotest) {
		this.mailtotest = mailtotest;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getListTo() {
		return listTo;
	}

	public void setListTo(String listTo) {
		this.listTo = listTo;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getBlastid() {
		return blastid;
	}

	public void setBlastid(String blastid) {
		this.blastid = blastid;
	}

	public String getSchTime() {
		return schTime;
	}

	public void setSchTime(String schTime) {
		this.schTime = schTime;
	}

	public String getSendAt() {
		return sendAt;
	}

	public void setSendAt(String sendAt) {
		this.sendAt = sendAt;
	}

	public String getNow() {
		return now;
	}

	public void setNow(String now) {
		this.now = now;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ArrayList<Entity> getContentlist() {
		return contentlist;
	}

	public void setContentlist(ArrayList<Entity> contentlist) {
		this.contentlist = contentlist;
	}

	

	public DateTimeBean getDateTimeBeanObj() {
		return dateTimeBeanObj;
	}

	public void setDateTimeBeanObj(DateTimeBean dateTimeBeanObj) {
		this.dateTimeBeanObj = dateTimeBeanObj;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public ArrayList<Entity> getRecurringlist() {
		return recurringlist;
	}

	public void setRecurringlist(ArrayList<Entity> recurringlist) {
		this.recurringlist = recurringlist;
	}

	public String getTidList() {
		return tidList;
	}

	public void setTidList(String tidList) {
		this.tidList = tidList;
	}

	public String getSelectedTickets() {
		return selectedTickets;
	}

	public void setSelectedTickets(String selectedTickets) {
		this.selectedTickets = selectedTickets;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getSelectAttendeeType() {
		return selectAttendeeType;
	}

	public void setSelectAttendeeType(String selectAttendeeType) {
		this.selectAttendeeType = selectAttendeeType;
	}
	
}
