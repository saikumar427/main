package com.eventmanage.groupticketing.actions;

import java.util.ArrayList;
import com.event.dbhelpers.EventDB;
import com.eventmanage.groupticketing.beans.GroupDealTicketsData;
import com.eventmanage.groupticketing.helpers.GroupTicketJsonBuilderHelper;
import com.eventmanage.groupticketing.dbhelpers.GroupDealTicketsDB;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;



public class ManageGroupDealTicketsAction extends ActionSupport implements Preparable,ValidationAware {
	
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";

	private String msgKey = "";
	private String jsonData = "";
	GroupDealTicketsData ticketData=new GroupDealTicketsData();
	private String groupticketid="";
	private ArrayList<GroupDealTicketsData> groupticketsList=new ArrayList<GroupDealTicketsData>();
	private String percentageSymbol="";
	private String currency="";
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPercentageSymbol() {
		return percentageSymbol;
	}

	public void setPercentageSymbol(String percentageSymbol) {
		this.percentageSymbol = percentageSymbol;
	}

	public String getGroupticketid() {
		return groupticketid;
	}

	public void setGroupticketid(String groupticketid) {
		this.groupticketid = groupticketid;
	}

	public ArrayList<GroupDealTicketsData> getGroupticketsList() {
		return groupticketsList;
	}

	public void setGroupticketsList(ArrayList<GroupDealTicketsData> groupticketsList) {
		this.groupticketsList = groupticketsList;
	}

	
	
	
	public String execute() {
		//System.out.println("eid"+eid);
		//System.out.println("we r in Managegroupdeal ticketsAction");
		currency=EventDB.getCurrencySymbol(eid);
        if(currency==null)
        	currency="$";
            percentageSymbol="%";
        ticketData.setCurrency(currency);
        ticketData.setPercentageSymbol(percentageSymbol);
        String userTimeZone = EventDB.getEventTimeZone(eid);
        
    	return "success";
	}
	
	 public String populateGroupTicketJson(){
	  //  System.out.println("we r in populategrouptikcetjson"+eid);
        String userTimeZone = EventDB.getEventTimeZone(eid);  
        groupticketsList=GroupDealTicketsDB.getGroupTicketsList(eid,userTimeZone);
		jsonData = GroupTicketJsonBuilderHelper.getGroupTicketJson(groupticketsList,eid).toString();
	//	System.out.println("after jsondata"+jsonData);
		return "jsondata";
		
			}
	 
    public String delete(){
    	
    //System.out.println("we r in delete");
    GroupDealTicketsDB.deleteGroupTicketsData(eid,groupticketid);		
	msgKey="groupticket.deleted";
	return "deleted";	
    
    }

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}


	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	
	public GroupDealTicketsData getTicketData() {
		return ticketData;
	}

	public void setTicketData(GroupDealTicketsData ticketData) {
		this.ticketData = ticketData;
	}
	public void prepare() throws Exception {
		// TODO Auto-generated method stub	
	}

}
