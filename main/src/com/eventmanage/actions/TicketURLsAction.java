package com.eventmanage.actions;

import java.util.ArrayList;

import org.json.JSONObject;

import com.event.beans.TicketURLsData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.TicketURLsDB;
import com.eventbee.general.DbUtil;
import com.eventbee.general.ProfileValidator;
import com.eventmanage.helpers.TicketURLJSONBuilderHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class TicketURLsAction extends ActionSupport implements Preparable,ValidationAware{

	private static final long serialVersionUID = -3773514102198232135L;
	private TicketURLsData ticketURLsData=new TicketURLsData();
	private String eid="";
	private ArrayList alltickets=new ArrayList();
	private ArrayList seltickets=new ArrayList();
	private String name="";
	private String msg="";
	private String code="";
	private ArrayList<TicketURLsData> ticketURLList=new ArrayList<TicketURLsData>();
	private String jsonData="";
	private String ticketsCount=""; 	
	public String getTicketsCount() {
		return ticketsCount;
	}

	public void setTicketsCount(String ticketsCount) {
		this.ticketsCount = ticketsCount;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ArrayList<TicketURLsData> getTicketURLList() {
		return ticketURLList;
	}

	public void setTicketURLList(ArrayList<TicketURLsData> ticketURLList) {
		this.ticketURLList = ticketURLList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList getSeltickets() {
		return seltickets;
	}

	public void setSeltickets(ArrayList seltickets) {
		this.seltickets = seltickets;
	}

	public ArrayList getAlltickets() {
		return alltickets;
	}

	public void setAlltickets(ArrayList alltickets) {
		this.alltickets = alltickets;
	}

	public TicketURLsData getTicketURLsData() {
		return ticketURLsData;
	}

	public void setTicketURLsData(TicketURLsData ticketURLsData) {
		this.ticketURLsData = ticketURLsData;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
	public void populateEventURL(){
		ticketURLsData.setEventURL(TicketURLsDB.getEventURL(eid));
	}
	public void populateEventTickets(){
		alltickets=EventDB.getEventTickets(eid);				
	}
	public String execute(){
		Boolean redirect = SpecialFeeDB.checking(eid,"TicketURLs","Ticketing","300");
		if(redirect)
			return "pageRedirect";
		else{
			populateTicketurlsList();
			return "success";
		}
		/*populateEventURL();	
		populateEventTickets();
		ticketsCount=TicketURLsDB.getTicketsCount(eid);
		ticketURLsData.setTicketscount(ticketsCount);
		ticketURLList=TicketURLsDB.getTicketURLdetails(eid);*/
		
	}
	public String insertTicketURLs(){
		ProfileValidator pv=new ProfileValidator();
		if(pv.checkNameValidity(name,true)){
			String namecheck=name.toLowerCase();
			String alreadyexists=DbUtil.getVal("select 'yes' from custom_ticket_urls where lower(code)=? and eventid=CAST(? AS BIGINT)",new String[]{namecheck,eid});
			if(!"yes".equals(alreadyexists)){
				
				// adding for special fee start
				String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
				if("Yes".equalsIgnoreCase(SpecialFeeDB.checkUpgradeStatus(eid, "TicketURLs", "Ticketing", "300")))
					SpecialFeeDB.chekingSpecialFee(eid,mgrId,"300","Private Tickets URLs");
				// special fee end.
				
				TicketURLsDB.insertTicketURLs(seltickets, eid, name);
			}else	msg="Name Exists";
		}else{
		msg="spacesInUrl";
		}
		return "ajaxmsg";
	}
	public String getTickets(){
		populateEventTickets();
		seltickets=TicketURLsDB.getSelectedTickets(eid,code);
		ticketURLsData.setSeltickets(seltickets);
		ticketURLsData.setCode(code);
		return "tickets";
	}
	public void updateTicketURLs(){
		TicketURLsDB.updateTicketURLs(seltickets, eid, ticketURLsData.getCode());
		
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String populateTicketurlsList(){
		populateEventURL();	
		populateEventTickets();
		ticketURLList=TicketURLsDB.getTicketURLdetails(eid);
		JSONObject js=TicketURLJSONBuilderHelper.getTicketURLsListJson(eid,ticketURLList,ticketURLsData,alltickets);
		jsonData=js.toString();
		return "jsondata";
	}
	
	
	public String reloadTicketurlsList(){
		populateTicketurlsList();
		msg=jsonData;
		return "reloadedData";
	}
	
	
	public String createTicketingURL(){
		populateEventURL();	
		populateEventTickets();
		return "createticketsURL";
	}
	public String deleteTicketURL(){
		boolean hasSales=false;
		hasSales=TicketURLsDB.deleteTicketURL(eid, code);
		if(hasSales) msg="fail";
		else	msg="success";
		return "ajaxmsg";
	}

}
