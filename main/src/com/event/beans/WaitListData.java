package com.event.beans;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventbee.beans.DateTimeBean;

public class WaitListData implements Cloneable{
	private String tdate="";
	private String wid="";
	private String name="";
	private String email="";
	private String tickets="";
	private String phone="";
	private String status="";
	private String activationLink="";
	private DateTimeBean expDateTimeBeanObj;
	private String eventid="";
	private String notes="";
	private String ticketId="";
	private String ticketCount="";
	private String expDate="";
	private String expTime="";
	private String ticketQty="0";
	private ArrayList<HashMap<String,String>> ticketList=new ArrayList<HashMap<String,String>>();
	private String isbuyer="";
	private String isattendee="";
	private String bcc="";

	private String tomanager="";
	private String toattendee="";
	private String emailcontent="";
	private String subject="";
	private String completed="0";
	private String expired="0";
	private String waiting="0";
	private String inProcess="0";
	private ArrayList ticketsList=new ArrayList();
	private String allStatus="0";
	private ArrayList eventDates=new ArrayList();
	private String eventDate="";
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTickets() {
		return tickets;
	}
	public void setTickets(String tickets) {
		this.tickets = tickets;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActivationLink() {
		return activationLink;
	}
	public void setActivationLink(String activationLink) {
		this.activationLink = activationLink;
	}
	public DateTimeBean getExpDateTimeBeanObj() {
		return expDateTimeBeanObj;
	}
	public void setExpDateTimeBeanObj(DateTimeBean expDateTimeBeanObj) {
		this.expDateTimeBeanObj = expDateTimeBeanObj;
	}

	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getExpTime() {
		return expTime;
	}
	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}
	
	public String getTicketQty() {
		return ticketQty;
	}
	public void setTicketQty(String ticketQty) {
		this.ticketQty = ticketQty;
	}
	public ArrayList<HashMap<String, String>> getTicketList() {
		return ticketList;
	}
	public void setTicketList(ArrayList<HashMap<String, String>> ticketList) {
		this.ticketList = ticketList;
	}
	public String getIsbuyer() {
		return isbuyer;
	}
	public void setIsbuyer(String isbuyer) {
		this.isbuyer = isbuyer;
	}
	public String getIsattendee() {
		return isattendee;
	}
	public void setIsattendee(String isattendee) {
		this.isattendee = isattendee;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getTomanager() {
		return tomanager;
	}
	public void setTomanager(String tomanager) {
		this.tomanager = tomanager;
	}
	public String getToattendee() {
		return toattendee;
	}
	public void setToattendee(String toattendee) {
		this.toattendee = toattendee;
	}
	public String getEmailcontent() {
		return emailcontent;
	}
	public void setEmailcontent(String emailcontent) {
		this.emailcontent = emailcontent;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getWaiting() {
		return waiting;
	}
	public void setWaiting(String waiting) {
		this.waiting = waiting;
	}
	public String getInProcess() {
		return inProcess;
	}
	public void setInProcess(String inProcess) {
		this.inProcess = inProcess;
	}
	public ArrayList getTicketsList() {
		return ticketsList;
	}
	public void setTicketsList(ArrayList ticketsList) {
		this.ticketsList = ticketsList;
	}
	public String getAllStatus() {
		return allStatus;
	}
	public void setAllStatus(String allStatus) {
		this.allStatus = allStatus;
	}
	public ArrayList getEventDates() {
		return eventDates;
	}
	public void setEventDates(ArrayList eventDates) {
		this.eventDates = eventDates;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	
	public Object clone() {
        try {
            return (Object)super.clone();
        }
        catch (CloneNotSupportedException e) {
            // This should never happen
        	return new Object();
        }
    }
	
}
