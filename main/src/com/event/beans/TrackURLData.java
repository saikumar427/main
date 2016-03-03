package com.event.beans;

import java.util.ArrayList;
import java.util.HashMap;

public class TrackURLData {
	private String eventURL="";
	private String manageEventURL="";	
	private String eventid="";
	private String trackingcode="";
	private String trackingid="";
	private String count="";
	private String secretcode="";	
	private String password="";
	private String message="";
	private String photo="";
	private String status="";
	private String individulasecretcode="";
	private String transactionid="";
	private String trandate="";
	private String firstname="";
	private String lastname="";
	private String totalamount="";
	private String grandtotal="";
	private String ebeefee="";
	private String cardfee="";
	private String discount="";
	private String attname="";
	private String ticketname="";
	private String ticketQty="";
	private String ticketPrice="";
	private String tickets="";
	private String alreadyexists="";
	private ArrayList ticketsData;
	private String date="";
	private String dir="asc";
	private String sortedkey="";
	
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAlreadyexists() {
		return alreadyexists;
	}
	public void setAlreadyexists(String alreadyexists) {
		this.alreadyexists = alreadyexists;
	}
	public String getTickets() {
		return tickets;
	}
	public void setTickets(String tickets) {
		this.tickets = tickets;
	}
	public String getTicketQty() {
		return ticketQty;
	}
	public void setTicketQty(String ticketQty) {
		this.ticketQty = ticketQty;
	}
	public String getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public String getTicketname() {
		return ticketname;
	}
	public void setTicketname(String ticketname) {
		this.ticketname = ticketname;
	}

	HashMap ticketInfo=new HashMap();
	
	public HashMap getTicketInfo() {
		return ticketInfo;
	}
	public void setTicketInfo(HashMap ticketInfo) {
		this.ticketInfo = ticketInfo;
	}
	public String getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
	public String getTrandate() {
		return trandate;
	}
	public void setTrandate(String trandate) {
		this.trandate = trandate;
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
	public String getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}
	public String getGrandtotal() {
		return grandtotal;
	}
	public void setGrandtotal(String grandtotal) {
		this.grandtotal = grandtotal;
	}
	public String getEbeefee() {
		return ebeefee;
	}
	public void setEbeefee(String ebeefee) {
		this.ebeefee = ebeefee;
	}
	public String getCardfee() {
		return cardfee;
	}
	public void setCardfee(String cardfee) {
		this.cardfee = cardfee;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getAttname() {
		return attname;
	}
	public void setAttname(String attname) {
		this.attname = attname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIndividulasecretcode() {
		return individulasecretcode;
	}

	public void setIndividulasecretcode(String individulasecretcode) {
		this.individulasecretcode = individulasecretcode;
	}
	
	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getTrackingcode() {
		return trackingcode;
	}

	public void setTrackingcode(String trackingcode) {
		this.trackingcode = trackingcode;
	}

	public String getTrackingid() {
		return trackingid;
	}

	public void setTrackingid(String trackingid) {
		this.trackingid = trackingid;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSecretcode() {
		return secretcode;
	}

	public void setSecretcode(String secretcode) {
		this.secretcode = secretcode;
	}

	public String getEventURL() {
		return eventURL;
	}

	public void setEventURL(String eventURL) {
		this.eventURL = eventURL;
	}
	public String getManageEventURL() {
		return manageEventURL;
	}

	public void setManageEventURL(String manageEventURL) {
		this.manageEventURL = manageEventURL;
	}
	public ArrayList getTicketsData() {
		return ticketsData;
	}
	public void setTicketsData(ArrayList ticketsData) {
		this.ticketsData = ticketsData;
	}

}
