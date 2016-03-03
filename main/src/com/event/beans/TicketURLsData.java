package com.event.beans;

import java.util.ArrayList;

public class TicketURLsData {
	private String eventURL="";
	private ArrayList seltickets=new ArrayList();
	private String code="";
	private String count="";
	private String ticketid="";
	private String ticketname="";
	private String ticketscount="";
	
	public String getTicketscount() {
		return ticketscount;
	}

	public void setTicketscount(String ticketscount) {
		this.ticketscount = ticketscount;
	}

	public String getTicketname() {
		return ticketname;
	}

	public void setTicketname(String ticketname) {
		this.ticketname = ticketname;
	}

	public String getTicketid() {
		return ticketid;
	}

	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public ArrayList getSeltickets() {
		return seltickets;
	}

	public void setSeltickets(ArrayList seltickets) {
		this.seltickets = seltickets;
	}

	public String getEventURL() {
		return eventURL;
	}

	public void setEventURL(String eventURL) {
		this.eventURL = eventURL;
	}

}
