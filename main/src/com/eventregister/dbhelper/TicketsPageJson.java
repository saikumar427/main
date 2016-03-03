package com.eventregister.dbhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import org.json.JSONObject;
import com.event.dbhelpers.DisplayAttribsDB;
import com.eventbee.general.I18NCacheData;
import com.eventbee.general.GenUtil;
import com.eventbee.general.formatting.CurrencyFormat;
import com.eventregister.TicketingInfo;
import com.eventregister.beans.EventTicket;
import com.eventregister.beans.TicketGroup;

public class TicketsPageJson {

	TicketingInfo ticketInfo=new TicketingInfo();

	public  JSONObject getJsonTicketsData(String eid,String evtdate,String ticketurlcode,String purpose){



	JSONObject tObject=new JSONObject();
	boolean isLooseTicket=true;
	try{
	ticketInfo.intialize(eid,ticketurlcode,evtdate,purpose);
	tObject.put("isDiscountExists",ticketInfo.isDiscountExists);
	HashMap<String, String> hm= new HashMap<String,String>();
	hm.put("module", "TicketDisplayOptions");
	HashMap <String,String>configMap=DisplayAttribsDB.getDisplayAttribs(hm, I18NCacheData.getI18NLanguage(eid), eid);//DisplayAttribsDB.getAttribValues(eid,"TicketDisplayOptions");
	ArrayList ticketsArray=ticketInfo.requiredTicketGroups;

	for(int i=0;i<ticketsArray.size();i++){
	TicketGroup evt_TicketGroups=(TicketGroup)ticketsArray.get(i);
	isLooseTicket=evt_TicketGroups.isAutoGroup();

	EventTicket eventTicketsArray[]=(EventTicket[])evt_TicketGroups.getGroupTicketsArray();
	for(int k=0;k<eventTicketsArray.length;k++){
	try{
	JSONObject ticketObject=new JSONObject();

	ticketObject.put("Id",eventTicketsArray[k].getTicketId());
	ticketObject.put("eventId",eid);

	if(isLooseTicket)
	ticketObject.put("isLooseTicket","Y");
	else
	ticketObject.put("isLooseTicket","N");

	ticketObject.put("Name",eventTicketsArray[k].getTicketName());
	ticketObject.put("ActualPrice",CurrencyFormat.getCurrencyFormat("",eventTicketsArray[k].getTicketPrice()+"",true));
	ticketObject.put("ActualFee",CurrencyFormat.getCurrencyFormat("",eventTicketsArray[k].getTicketProcessFee()+"",true));
	ticketObject.put("ChargingPrice",CurrencyFormat.getCurrencyFormat("",eventTicketsArray[k].getTicketPrice()+"",true));
	ticketObject.put("ChargingFee",CurrencyFormat.getCurrencyFormat("",eventTicketsArray[k].getTicketProcessFee()+"",true));

	if("Active".equals(eventTicketsArray[k].getTicketStatus())){
	ticketObject.put("Available","Y");
	}
	else
	ticketObject.put("Available","N");
	ticketObject.put("IsEnable","Y");
	if("donationType".equals(eventTicketsArray[k].getTicketType()))
	ticketObject.put("DonateType","Y");
	String shortmsg=eventTicketsArray[k].getTicketStartDate()+" "+eventTicketsArray[k].getTicketStartTime()+" - "+eventTicketsArray[k].getTicketEndDate()+" "+eventTicketsArray[k].getTicketEndTime();
	ticketObject.put("smallDesc",shortmsg);
	ticketObject.put("Min",eventTicketsArray[k].getTicketMinQty()+"");
	ticketObject.put("Max",eventTicketsArray[k].getTicketMaxQty()+"");
	ticketObject.put("ticketType",eventTicketsArray[k].getTicketType());
	if("donationType".equals(eventTicketsArray[k].getTicketType()))
	ticketObject.put("DonateType","Y");
	ticketObject.put("GroupId",evt_TicketGroups.getTicketGroupId());
	if(!"".equals(eventTicketsArray[k].getTicketDescription()))
	ticketObject.put("Desc",eventTicketsArray[k].getTicketDescription());

	if(eventTicketsArray[k].isMemberTicket()&&!"manageradd".equals(purpose))
	ticketObject.put("IsMemberTicket","Y");
	ticketObject.put("tktSelected","0");

	if("Closed".equals(eventTicketsArray[k].getTicketStatus())&&"yes".equalsIgnoreCase(GenUtil.getHMvalue(configMap,"event.closedtickets.status","yes"))){
	ticketObject.put("Msg",GenUtil.getHMvalue(configMap,"event.closedtickets.statusmessage","Closed"));
	tObject.put(eventTicketsArray[k].getTicketId(),ticketObject);
	ticketObject.put("Available","N");

	}
	else if("Sold Out".equals(eventTicketsArray[k].getTicketStatus())&&"yes".equalsIgnoreCase(GenUtil.getHMvalue(configMap,"event.soldouttickets.status","yes"))){
	ticketObject.put("Msg",GenUtil.getHMvalue(configMap,"event.soldouttickets.statusmessage","SOLD OUT"));
	tObject.put(eventTicketsArray[k].getTicketId(),ticketObject);
	ticketObject.put("Available","N");

	}
	else if("NOT_STARTED".equals(eventTicketsArray[k].getTicketStatus())&&"yes".equalsIgnoreCase(GenUtil.getHMvalue(configMap,"event.notyetstartedtickets.status","no"))){
	ticketObject.put("Msg","NA");
	tObject.put(eventTicketsArray[k].getTicketId(),ticketObject);
	ticketObject.put("Available","Y");
	}
	else if("Active".equals(eventTicketsArray[k].getTicketStatus())){
	tObject.put(eventTicketsArray[k].getTicketId(),ticketObject);
	}

	}
	catch(Exception e)
	{
	System.out.println("Exception is"+e.getMessage());
	}
	}
	}
	tObject.put("availibilitymsg",ticketInfo.getTicketMessage(eid,evtdate));
	} catch(Exception e)
	  {
	  System.out.println("Exception occured is"+e.getMessage());
	  }
	return tObject;

	}




	public  HashMap getGroupTicketsVec(String eid,String ticketurlcode,String evtdate,String purpose){
	HashMap hm=new HashMap();
	Vector ticketGroupsVector=new Vector();
	String ticketslist[]={};
	try{
	TicketingInfo ticketInfo=new TicketingInfo();
	ArrayList  ticketidsArray=new ArrayList();
	HashMap configMap=DisplayAttribsDB.getAttribValues(eid,"TicketDisplayOptions");
	boolean isLooseTicket=true;
	ticketInfo.intialize(eid,ticketurlcode,evtdate,purpose);
	ArrayList ticketsArray=ticketInfo.requiredTicketGroups;
	HashMap configEntries=ticketInfo.configMap;

	for(int i=0;i<ticketsArray.size();i++){
	Vector <HashMap>ticketsVector=new Vector();
	HashMap TicketGroupMap=new HashMap();
	TicketGroup evt_TicketGroups=(TicketGroup)ticketsArray.get(i);
	EventTicket eventTicketsArray[]=(EventTicket[])evt_TicketGroups.getGroupTicketsArray();
	isLooseTicket=evt_TicketGroups.isAutoGroup();
	if(!isLooseTicket){
	TicketGroupMap.put("ticketGroupName",evt_TicketGroups.getTicketGroupName());
	TicketGroupMap.put("ticketGroupDecription",evt_TicketGroups.getTicketGroupDescription());
	}
	for(int k=0;k<eventTicketsArray.length;k++){
	HashMap ticketMap=new HashMap();
	if(isLooseTicket)
	ticketMap.put("isLooseTicket","Yes");
	String tid=eventTicketsArray[k].getTicketName();
	ticketMap.put("ticketid",eventTicketsArray[k].getTicketId());
	ticketMap.put("ticketName",eventTicketsArray[k].getTicketName());
	ticketMap.put("ticketPrice",eventTicketsArray[k].getTicketPrice()+"");
	ticketMap.put("processFee",eventTicketsArray[k].getTicketProcessFee()+"");
	ticketMap.put("ticketStatus",eventTicketsArray[k].getTicketStatus());
	ticketMap.put("minQty",eventTicketsArray[k].getTicketMinQty()+"");
	ticketMap.put("maxQty",eventTicketsArray[k].getTicketMaxQty()+"");
	ticketMap.put("ticketType",eventTicketsArray[k].getTicketType());
	ticketMap.put("ticketType",eventTicketsArray[k].getTicketType());
	ticketMap.put("ticketGroupId",evt_TicketGroups.getTicketGroupId());
	if(!"".equals(eventTicketsArray[k].getTicketDescription()))
	ticketMap.put("ticketDescription",eventTicketsArray[k].getTicketDescription());
	ticketMap.put("isMemberTicket",eventTicketsArray[k].isMemberTicket()+"");
	String ticketdiv="<div id='"+eventTicketsArray[k].getTicketId()+"' class='tktWedgetClass'></div>";
	System.out.println("eventTicketsArray[k].getTicketStatus()--"+eventTicketsArray[k].getTicketStatus());
	ticketMap.put("ticketWidget",ticketdiv);
	if("Closed".equals(eventTicketsArray[k].getTicketStatus())&&"yes".equalsIgnoreCase(GenUtil.getHMvalue(configMap,"event.closedtickets.status","yes"))){
	ticketsVector.add(ticketMap);
	ticketidsArray.add(eventTicketsArray[k].getTicketId());
	}
	else if("Sold Out".equals(eventTicketsArray[k].getTicketStatus())&&"yes".equalsIgnoreCase(GenUtil.getHMvalue(configMap,"event.soldouttickets.status","yes"))){
	ticketsVector.add(ticketMap);
	ticketidsArray.add(eventTicketsArray[k].getTicketId());
	}
	else if("Active".equals(eventTicketsArray[k].getTicketStatus())){
	ticketsVector.add(ticketMap);
	ticketidsArray.add(eventTicketsArray[k].getTicketId());
	}
	else if("NOT_STARTED".equals(eventTicketsArray[k].getTicketStatus())&&"yes".equalsIgnoreCase(GenUtil.getHMvalue(configMap,"event.notyetstartedtickets.status","no"))){
	ticketsVector.add(ticketMap);
	ticketidsArray.add(eventTicketsArray[k].getTicketId());
	}
	}
	TicketGroupMap.put("tickets",ticketsVector);
	if(ticketsVector.size()>0)
	ticketGroupsVector.add(TicketGroupMap);
	}

	if(ticketidsArray!=null&&ticketidsArray.size()>0){
	ticketslist=(String[])ticketidsArray.toArray(new String[ticketidsArray.size()]);
	}
	hm.put("ticketVec",ticketGroupsVector);
	hm.put("ticketsarray",ticketslist);
	hm.put("configEntries",configEntries);
	}
	catch(Exception e){
	System.out.println("exception occured in get groupTickets is--"+e.getMessage());
	}
	return hm;
	}

	}