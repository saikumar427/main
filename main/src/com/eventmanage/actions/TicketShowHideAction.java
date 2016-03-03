package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.event.beans.TicketShowHideData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.EventHelperDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.TicketShowHideDB;
import com.eventbee.beans.Entity;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class TicketShowHideAction  extends ActionSupport implements Preparable,ValidationAware{

	private static final long serialVersionUID = -3773514102198232135L;
	private TicketShowHideData ticketShowHideData=new TicketShowHideData();
	private String eid="";
	private ArrayList alltickets=new ArrayList();
	private ArrayList seltickets=new ArrayList();
	private ArrayList<Entity> eventdates=new ArrayList<Entity>();
	private String eventdate="";
	private String show="";
	private String ticketid="";
	private ArrayList<Entity> voltickets=new ArrayList<Entity>();
	private ArrayList selvoltickets=new ArrayList();
	private List<Entity> optionsList=new ArrayList<Entity>();
	private String msg="";
	public List<Entity> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(List<Entity> optionsList) {
		this.optionsList = optionsList;
	}

	public String getTicketid() {
		return ticketid;
	}

	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getEventdate() {
		return eventdate;
	}

	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}

	public ArrayList getSeltickets() {
		return seltickets;
	}

	public void setSeltickets(ArrayList seltickets) {
		this.seltickets = seltickets;
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

	public ArrayList getAlltickets() {
		return alltickets;
	}

	public void setAlltickets(ArrayList alltickets) {
		this.alltickets = alltickets;
	}
	public void populateEventTickets(){
		alltickets=EventDB.getEventTickets(eid);
		seltickets=TicketShowHideDB.getSelectedTickets(eid);
	}
	public void populateEventDatesAndTickets(){
		alltickets=EventDB.getEventTickets(eid);
		eventdates=TicketShowHideDB.getEventDates(eid);
		seltickets=TicketShowHideDB.getSelectedTicketsForDates(eid,eventdate);
		System.out.println("tickets size:"+seltickets.size());
	}
	public String execute(){
		if(EventDB.isRecurringEvent(eid))
		{	
			System.out.println("eventdate: "+eventdate);
			populateEventDatesAndTickets();
		//populateEventTickets();
		System.out.println("in recurring");
		return "recurringtickets";
		}
		populateEventTickets();
		return "tickets";
	}
	public String update(){
		TicketShowHideDB.update(show,seltickets, eid);
		String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
		SpecialFeeDB.chekingSpecialFee(eid,mgrId,"200","showhidetickets");
		System.out.println("removeGLobalEventCache: while ticket hide/show");
		EventHelperDB.removeGLobalEventCache(eid, "remove", "ticketsinfo");
		return "success";
	}
	public String updateRecurring(){
		System.out.println("eventdate: "+eventdate);
		System.out.println("ticketid: "+ticketid);
		System.out.println("show: "+show);
		TicketShowHideDB.updateForRecurring(eid, eventdate, ticketid, show);
		String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
		SpecialFeeDB.chekingSpecialFee(eid,mgrId,"200","showhidetickets");
		return "success";
	}
	
	public String volumeTickets(){
		HashMap<String, ArrayList<Entity>> allvolumetickets = TicketShowHideDB.getVolumeTickets(eid);
		voltickets=allvolumetickets.get("voltickets");
		selvoltickets=allvolumetickets.get("selvoltickets");
		return "volumetickets";
	}
	
	public String savevolumeTickets(){
		TicketShowHideDB.saveVolumeTickets(eid, selvoltickets);
		msg="success";
		return "ajaxmsg";
	}
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Entity> getEventdates() {
		return eventdates;
	}

	public void setEventdates(ArrayList<Entity> eventdates) {
		this.eventdates = eventdates;
	}

	public ArrayList<Entity> getVoltickets() {
		return voltickets;
	}

	public void setVoltickets(ArrayList<Entity> voltickets) {
		this.voltickets = voltickets;
	}

	public ArrayList getSelvoltickets() {
		return selvoltickets;
	}

	public void setSelvoltickets(ArrayList selvoltickets) {
		this.selvoltickets = selvoltickets;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
