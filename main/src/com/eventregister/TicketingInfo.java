package com.eventregister;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventbee.general.DBManager;
import com.eventbee.general.StatusObj;
import com.eventregister.beans.EventTicket;
import com.eventregister.beans.TicketGroup;

public class TicketingInfo {
	private String eventid=null;
	public ArrayList  requiredTicketGroups=null;
	public ArrayList  optionalTicketGroups=null;
	public String currencySymbol=null;
	public HashMap configMap=null;
	public boolean isDiscountExists=true;
	public boolean isMemberTicketsExists=true;
	public HashMap memberTicketsMap=null;
	private HashMap groupTickets=new HashMap();
	private String evtdate=null;
	private HashMap detailsMap=null;
	TicketsDB ticketdb=new TicketsDB();
	public void intialize(String evtid,String ticketurlcode,String evtdate,String purpose){
	evtdate=evtdate;
	eventid=evtid;
	memberTicketsMap=ticketdb.getMemberTicketsMap(evtid);
	configMap=ticketdb.getConfigValuesFromDb(evtid);
	groupTickets=getGroupsAndTickets(eventid,ticketurlcode,evtdate,purpose);
	requiredTicketGroups=(ArrayList)groupTickets.get("Tickets");
	isDiscountExists=ticketdb.isCouponcodeExists(eventid);
	}

	public HashMap getGroupsAndTickets(String eventid,String ticketurlcode,String evtdate,String purpose){
	ticketTimeZoneConversion  ticketTimezones=new ticketTimeZoneConversion();
	ArrayList ParamList=new ArrayList();
	ParamList.add(eventid);

	String query="select * from tickets_info where eventid=? ";
	if(ticketurlcode!=null&&!"".equals(ticketurlcode)&&!"null".equals(ticketurlcode)){
	query="select * from ticketurl_tickets_info where eventid=? and code=?";
	ParamList.add(ticketurlcode);
	}
	
	if("manageradd".equals(purpose))
	query="select * from manager_tickets_info where eventid=?";
	
	if(evtdate!=null&&!"".equals(evtdate)){
	detailsMap=ticketdb.getrecurringEventdetails(eventid,evtdate);
	}

	DBManager db=new DBManager();
	ArrayList requiredGroupTickets=new ArrayList();

	HashMap hm=new HashMap();
	TicketGroup tktGrp=null;
	ArrayList groupTicketsArray=null;
	int groupIndex=0;
	StatusObj sb=db.executeSelectQuery(query,(String[])ParamList.toArray(new String[ParamList.size()]));
	try{
	if(sb.getStatus()){
	for(int i=0;i<sb.getCount();i++){

	if(hm.get(db.getValue(i,"ticket_groupid",""))==null){
	groupIndex++;
	tktGrp=new TicketGroup();
	groupTicketsArray=new ArrayList();
	tktGrp.setTicketGroupName(db.getValue(i,"groupname",""));
	if(!"".equals(db.getValue(i,"groupname","")))
	tktGrp.setAutoGroup(false);
	tktGrp.setTicketGroupId(db.getValue(i,"ticket_groupid",""));
	}
	EventTicket eventTicketObj=new EventTicket();
	if("Yes".equals(db.getValue(i,"isdonation","")))
	eventTicketObj.setTicketType("donationType");
	else if("Yes".equalsIgnoreCase(db.getValue(i,"isattendee","")))
	eventTicketObj.setTicketType("attendeeType");
	else
	eventTicketObj.setTicketType("nonAttendee");	
	//eventTicketObj.setTicketType(db.getValue(i,"tickettype",""));
	eventTicketObj.setTicketName(db.getValue(i,"ticket_name",""));
	eventTicketObj.setTicketPrice(Double.parseDouble(db.getValue(i,"ticket_price","0")));
	eventTicketObj.setTicketProcessFee(Double.parseDouble(db.getValue(i,"process_fee","0")));
	eventTicketObj.setTicketDescription(db.getValue(i,"description",""));
	eventTicketObj.setTicketId(db.getValue(i,"price_id",""));
	eventTicketObj.setTicketCapacity(Integer.parseInt(db.getValue(i,"max_ticket","0")));
	eventTicketObj.setTicketSoldQty(Integer.parseInt(db.getValue(i,"sold_qty","0")));
	eventTicketObj.setTicketMaxQty(Integer.parseInt(db.getValue(i,"max_qty","0")));
	eventTicketObj.setTicketMinQty(Integer.parseInt(db.getValue(i,"min_qty","0")));
	eventTicketObj.setTicketMinQty(Integer.parseInt(db.getValue(i,"min_qty","0")));
	HashMap timeHm=new HashMap();

	if(evtdate!=null&&!"".equals(evtdate)){
	timeHm=(HashMap)detailsMap.get(db.getValue(i,"price_id",""));
	eventTicketObj.setSoldStatus((String)timeHm.get("soldstatus"));
	eventTicketObj.setStartStatus((String)timeHm.get("startstatus"));
	eventTicketObj.setEndStatus((String)timeHm.get("endstatus"));
	}
	else{
	timeHm.put("startYear",db.getValue(i,"start_yy",""));
	timeHm.put("startMonth",db.getValue(i,"start_mm",""));
	timeHm.put("startYear",db.getValue(i,"start_yy",""));
	timeHm.put("startDay",db.getValue(i,"start_dd",""));
	timeHm.put("endYear",db.getValue(i,"end_yy",""));
	timeHm.put("endMonth",db.getValue(i,"end_mm",""));
	timeHm.put("endDay",db.getValue(i,"end_dd",""));
	timeHm.put("starttime",(db.getValue(i,"starttime","")==null|| "".equals(db.getValue(i,"starttime","")) )?"01:00":db.getValue(i,"starttime","")   );
	timeHm.put("endtime",(db.getValue(i,"endtime","")==null|| "".equals(db.getValue(i,"endtime","")) )?"01:00":db.getValue(i,"endtime","")   );
	eventTicketObj.setSoldStatus(db.getValue(i,"soldstatus",""));
	eventTicketObj.setStartStatus(db.getValue(i,"startstatus",""));
	eventTicketObj.setEndStatus(db.getValue(i,"endstatus",""));
	timeHm.put("soldstatus",db.getValue(i,"soldstatus",""));
	timeHm.put("startstatus",db.getValue(i,"startstatus",""));
	timeHm.put("endstatus",db.getValue(i,"endstatus",""));

	}

	ticketTimezones.getTimezones(timeHm,eventid);
	eventTicketObj.setTicketStartDate((String)timeHm.get("start_date"));
	eventTicketObj.setTicketEndDate((String)timeHm.get("end_date"));
	eventTicketObj.setTicketStartTime((String)timeHm.get("starttime"));
	eventTicketObj.setTicketEndTime((String)timeHm.get("endtime"));
	/*if(("NOT_SOLD".equals((String)timeHm.get("soldstatus")))&&("STARTED".equals(eventTicketObj.getStartStatus()))&&("NOT_CLOSED".equals((String)timeHm.get("endstatus"))))
	eventTicketObj.setTicketStatus("Active");
	else if("SOLD_OUT".equals((String)timeHm.get("soldstatus")))
	eventTicketObj.setTicketStatus("Sold Out");
	else if(("STARTED".equals((String)timeHm.get("startstatus")))&&("CLOSED".equals((String)timeHm.get("endstatus"))))
	eventTicketObj.setTicketStatus("Closed");
	else if("NOT_STARTED".equals((String)timeHm.get("startstatus")))
	eventTicketObj.setTicketStatus("NOT_STARTED");*/
	eventTicketObj.setTicketStatus("Active");
	if(memberTicketsMap.containsKey(db.getValue(i,"price_id","")))
	eventTicketObj.setMemberTicketFlag(true);
	else
	eventTicketObj.setMemberTicketFlag(false);
	groupTicketsArray.add(eventTicketObj);
	if(groupTicketsArray!=null){
	EventTicket eventTicketsArray[]=new EventTicket[groupTicketsArray.size()];
	for(int k=0;k<groupTicketsArray.size();k++){
	eventTicketsArray[k]=(EventTicket)groupTicketsArray.get(k);
	}
	tktGrp.setGroupTicketsArray(eventTicketsArray);
	}
	if(hm.get(db.getValue(i,"ticket_groupid",""))==null){
	requiredGroupTickets.add(tktGrp);
	hm.put(db.getValue(i,"ticket_groupid",""),"Y");
	}
	else{
	requiredGroupTickets.set(groupIndex-1,tktGrp);
	}
	}
	groupTickets.put("Tickets",requiredGroupTickets);
	}
	}
	catch(Exception e){
	System.out.println("exeption in get Ticket Groups method in TicketInfo");
	}

	return groupTickets;
	}

	
	public HashMap getTicketMessage(String eventid,String eventdate){
		if(eventdate==null)
			eventdate="";
		HashMap hm=new HashMap();
		HashMap rec_hm=new HashMap();
		DBManager db=new DBManager();
		StatusObj sb=null;
		String hold_qty="select sum(locked_qty) as holdqty,ticketid  from event_reg_locked_tickets where eventid=? group by ticketid";
		try{
		if(!"".equals(eventdate)){
		String rec_qry="select ticketid,soldqty from reccurringevent_ticketdetails where eventid=CAST(? AS BIGINT) and eventdate=?";
		sb=db.executeSelectQuery(rec_qry,new String[]{eventid,eventdate});
		if(sb.getStatus()){
		for(int i=0;i<sb.getCount();i++){
		rec_hm.put(db.getValue(i,"ticketid",""),db.getValue(i,"soldqty",""));
		}
		}
		hold_qty="select sum(locked_qty) as holdqty,ticketid  from event_reg_locked_tickets where eventid=? and eventdate=? group by ticketid";
		sb=db.executeSelectQuery(hold_qty,new String[]{eventid,eventdate});
		}
		else{
		sb=db.executeSelectQuery(hold_qty,new String[]{eventid});
		}
		if(sb.getStatus()){
		for(int i=0;i<sb.getCount();i++){
		hm.put("hold_"+db.getValue(i, "ticketid", ""),db.getValue(i, "holdqty", ""));
		}
		}
		String query="select a.price_id as ticketid,a.max_ticket as capacity,a.sold_qty as soldoutqty,(a.max_ticket-a.sold_qty) as remainingqty from price a  where  a.evt_id=CAST(? AS BIGINT)";
		sb=db.executeSelectQuery(query,new String[]{eventid});
		if(sb.getStatus()){
		for(int i=0;i<sb.getCount();i++){
		String tktid=db.getValue(i,"ticketid","");
		//String format=db.getValue(i,"format","");
		//hm.put(tktid,format);
		hm.put("capacity_"+tktid,db.getValue(i,"capacity",""));
		if(!"".equals(eventdate)){
		if( rec_hm.containsKey(tktid)){
		hm.put("soldqty_"+tktid,rec_hm.get(tktid));
		int remqty=Integer.parseInt(db.getValue(i,"capacity",""))-Integer.parseInt((String) rec_hm.get(tktid));
		hm.put("remaining_"+tktid,remqty);
		}else{
		hm.put("remaining_"+tktid,db.getValue(i,"capacity",""));
		hm.put("soldqty_"+tktid,"0");
		}
		}else{
		hm.put("remaining_"+tktid,db.getValue(i,"remainingqty",""));
		hm.put("soldqty_"+tktid,db.getValue(i,"soldoutqty",""));
		}
		if(!hm.containsKey("hold_"+tktid)){
		hm.put("hold_"+tktid, "0");
		}
		}
		}
		}catch(Exception e){
			System.out.println(e);
		}
		return hm;
		}
		
}
