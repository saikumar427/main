package com.eventregister.beans;

import java.util.HashMap;

public class TicketGroup {
	private String m_GroupType="Public";
	private String m_Ticket_GroupId="";
	private String m_GroupName="";
	private String m_Groupdescription="";
	private boolean TicketsExist=true;
	private EventTicket GroupTickets[]=null;
	private boolean m_isAutoGroup=true;
	private HashMap ticketGroupHashMap=null;
	public String getTicketGroupName(){
	 return m_GroupName;
	}

	public void setTicketGroupName(String p_GroupName){
	 m_GroupName=p_GroupName;
	}

	public String getTicketGroupId(){
	 return m_Ticket_GroupId;
	}

	public void setTicketGroupId(String p_Ticket_GroupId){
	 m_Ticket_GroupId=p_Ticket_GroupId;
	}

	public String getTicketGroupDescription(){
	 return m_Groupdescription;
	}


	public void setTicketGroupDescription(String p_GroupName){
	 m_Groupdescription=m_Groupdescription;
	}


	public EventTicket[] getGroupTicketsArray(){
	 return GroupTickets;
	}

	public void setGroupTicketsArray(EventTicket[] Tickets){
	 GroupTickets=Tickets;
	}


	public void setGroupContainsTickets(boolean p_TicketsExist){
	 TicketsExist=p_TicketsExist;
	}

	public boolean isGroupContainsTickets(){
	 return TicketsExist;
	}

	public void setAutoGroup(boolean p_isAutoGroup){
	 m_isAutoGroup=p_isAutoGroup;
	}

	public boolean isAutoGroup(){
	 return m_isAutoGroup;
	}


	public HashMap getTicketGroupHashMap(){
	 return ticketGroupHashMap;
	}

	public void setTicketGroupHashMap(HashMap hm){
	 ticketGroupHashMap=hm;
	}

	public Object getGenericKeyValue(String key){
	 return ticketGroupHashMap.get(key);
	}

	public void setGenericKeyValue(String key,Object value){
	 ticketGroupHashMap.put(key,value);
	}
}
