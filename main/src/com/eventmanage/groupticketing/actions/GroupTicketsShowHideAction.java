package com.eventmanage.groupticketing.actions;

import java.util.ArrayList;

import com.event.beans.TicketShowHideData;
import com.eventmanage.groupticketing.dbhelpers.GroupTicketsShowHideDB;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class GroupTicketsShowHideAction  extends ActionSupport implements Preparable,ValidationAware{
      
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";
	private TicketShowHideData ticketShowHideData=new TicketShowHideData();
	private ArrayList allGrouptickets=new ArrayList();
	private ArrayList selGrouptickets=new ArrayList();

	public ArrayList getAllGrouptickets() {
		return allGrouptickets;
	}

	public void setAllGrouptickets(ArrayList allGrouptickets) {
		this.allGrouptickets = allGrouptickets;
	}

	public ArrayList getSelGrouptickets() {
		return selGrouptickets;
	}

	public void setSelGrouptickets(ArrayList selGrouptickets) {
		this.selGrouptickets = selGrouptickets;
	}
	
	
	public TicketShowHideData getTicketShowHideData() {
		return ticketShowHideData;
	}

	public void setTicketShowHideData(TicketShowHideData ticketShowHideData) {
		this.ticketShowHideData = ticketShowHideData;
	}


	
		public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public void populateEventTickets(){
		allGrouptickets=GroupTicketsShowHideDB.getEventTickets(eid);
		selGrouptickets=GroupTicketsShowHideDB.getSelectedTickets(eid);
	}
	public String execute(){
		populateEventTickets();
		//System.out.println("we r in groupticket show hide action");
		return "tickets";
	}
	public String update(){
		GroupTicketsShowHideDB.update(selGrouptickets, eid);	
		return "success";
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}


}
