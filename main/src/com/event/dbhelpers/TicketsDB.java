package com.event.dbhelpers;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.select.Evaluator.IsRoot;

import com.event.beans.ticketing.*;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.beans.Entity;
import com.eventbee.general.*;
import com.eventbee.general.formatting.CurrencyFormat;
import com.eventbee.namedquery.NQDbHelper;
import com.eventbee.namedquery.NQDbUtil;
@SuppressWarnings("unchecked")
public class TicketsDB {
	static ArrayList groupsList=new ArrayList();
	public static ArrayList<Entity> getHours(){
		ArrayList<Entity> hrs=new ArrayList<Entity>();
		for( int i=0;i<13;i++){
			String val=Integer.toString(i);
			if(val.length()<2)
				val="0"+val;
			hrs.add(new Entity(val,val));
		}
		return hrs;
	}
	public static ArrayList<Entity> getMinutes(){      
		ArrayList<Entity> mins=new ArrayList<Entity>();
		for( int i=0;i<60;i++){
			String val=Integer.toString(i);
			if(val.length()<2)
				val="0"+val;
			mins.add(new Entity(val,val));
		}
		return mins;
	}
	public static ArrayList<Entity> getuaQuestions(String eid,String ticketId){
		HashMap inParams = new HashMap();
		inParams.put("groupid", eid);
		inParams.put("subgroupid",ticketId);
		
		NQDbHelper dbhelper = new NQDbHelper();
		ArrayList<Entity> uaQuestions=new ArrayList<Entity>();
		List uaQuestionsList = dbhelper.getDataList("selectuaquestions", inParams);
		if (uaQuestionsList != null) {
			for (int i = 0; i < uaQuestionsList.size(); i++) {
				Entity entityObj=new Entity();
				HashMap hmap = (HashMap) uaQuestionsList.get(i);
				entityObj=new Entity(hmap.get("attribid").toString(),hmap.get("question").toString());
				uaQuestions.add(entityObj);
			}
		}
		for(Entity ua: uaQuestions){
			//System.out.println("key"+ua.getKey());
			//System.out.println("value"+ua.getValue());
		}
		
		return uaQuestions;
	}
	public static ArrayList<Entity> getAnsweredQuestions(String eid,String ticketId){
		ArrayList<Entity> answeredQuestions=new ArrayList<Entity>();
		HashMap inParams = new HashMap();
		inParams.put("groupid", eid);
		inParams.put("subgroupid",ticketId);
		NQDbHelper dbhelper = new NQDbHelper();
		List aQuestionsList = dbhelper.getDataList("selectasnweredquestions", inParams);
		if (aQuestionsList != null) {
			for (int i = 0; i < aQuestionsList.size(); i++) {
				Entity entityObj=new Entity();
				HashMap hmap = (HashMap) aQuestionsList.get(i);
				entityObj=new Entity(hmap.get("attribid").toString(),hmap.get("question").toString());
				answeredQuestions.add(entityObj);
			}
		}
		return answeredQuestions;

	}
	public static boolean swapTicket(String eid,String groupId,String t1,String p1,String t2,String p2){
		String updateQuery="update group_tickets set position=CAST(? AS INTEGER) where ticket_groupid=? and price_id=?";
		DbUtil.executeUpdateQuery(updateQuery, new String []{p1,groupId,t1});
		DbUtil.executeUpdateQuery(updateQuery, new String []{p2,groupId,t2});
		return true;
	}
	public static boolean swapGroup(String eid,String groupId,String t1,String p1,String t2,String p2){
		String updateQuery="update event_ticket_groups set position=CAST(? AS INTEGER) where ticket_groupid=? ";
		DbUtil.executeUpdateQuery(updateQuery, new String []{p1,t1});
		DbUtil.executeUpdateQuery(updateQuery, new String []{p2,t2});
		return true;
	}
	public static boolean deleteGroup(String eid,String groupId){
		String etgDeleteQuery="delete from event_ticket_groups where ticket_groupid=? and eventid=?";
		String priceDeleteQurey="delete from price where price_id::TEXT in (select price_id from group_tickets where ticket_groupid =?) and evt_id=CAST(? AS BIGINT)";
		String gtDeleteQuery="delete from group_tickets where ticket_groupid=?";
		DbUtil.executeUpdateQuery(etgDeleteQuery,new String[]{groupId,eid});
		DbUtil.executeUpdateQuery(priceDeleteQurey,new String[]{groupId,eid});
		DbUtil.executeUpdateQuery(gtDeleteQuery,new String[]{groupId});

		return true;
	}
	public static boolean deleteTicket(String eid,String ticketId){
		String etgDeleteQuery="delete from event_ticket_groups where " +
			"ticket_groupid in(select ticket_groupid from group_tickets where price_id=?) and eventid=?";
	    String priceDeleteQurey="delete from price where price_id=CAST(? AS INTEGER)"; 
		String gtDeleteQuery="delete from group_tickets where price_id=?";
		DbUtil.executeUpdateQuery(priceDeleteQurey,new String[]{ticketId});
		DbUtil.executeUpdateQuery("delete from event_communities where price_id=?",new String[]{ticketId});
				String grpname=DbUtil.getVal("select groupname from event_ticket_groups where ticket_groupid " +
				"in (select ticket_groupid from group_tickets where price_id=?)",new String[]{ticketId});
		if("".equals(grpname)||grpname==null){
			DbUtil.executeUpdateQuery(etgDeleteQuery,new String[]{ticketId,eid});

		}
		DbUtil.executeUpdateQuery(gtDeleteQuery,new String[]{ticketId});
		DbUtil.executeUpdateQuery("delete from base_profile_questions where contextid=CAST(? as INTEGER) and groupid=CAST(? AS BIGINT)",new String[]{ticketId,eid});

		return true;
	}
	public static ArrayList getEventGroups(String eid){
		ArrayList groups=new ArrayList();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String ticketSelQuery="select ticket_groupid,groupname from event_ticket_groups  where eventid=? and length(groupname)>0 order by position";
		
		statobj=dbmanager.executeSelectQuery(ticketSelQuery,new String []{eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
			    String ticket_groupid=dbmanager.getValue(k,"ticket_groupid","");
				String groupname=dbmanager.getValue(k,"groupname","");
				groups.add(new Entity(ticket_groupid,groupname));

			}
		}
		return groups;
	}
	public static ArrayList getEventTicketsList(String eid,String userTimeZone,String eventDate) {
		//GroupData tempGroupDataobj=new GroupData();
		ArrayList groupsList=new ArrayList();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		/*String getGroupsListQuery="select distinct a.ticket_groupid,groupname,grouptype,a.position from " +
				"event_ticket_groups a,group_tickets b  where eventid=? and a.ticket_groupid=b.ticket_groupid" +
				" order by a.position";*/
		String getGroupsListQuery="select distinct a.ticket_groupid,groupname,grouptype,a.position from " +
				"event_ticket_groups a,group_tickets b  where eventid=? and " +
				"a.ticket_groupid=b.ticket_groupid	union select distinct a.ticket_groupid,groupname,grouptype," +
				"a.position from	event_ticket_groups a where eventid=? and length(a.groupname)!=0 " +
				"order by position";
		
		String ticketsalesquery="";
		
		String ticketsales[]=new String[2];
		
		//get ticket soldqty query	  
		if("".equals(eventDate.trim())){    
		ticketsalesquery="select sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in "
			+" ('COMPLETED','CHARGED','PENDING')) and UPPER(bookingsource)='MANAGER' "
			+" then ticketqty else 0 end) as manual, sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) "
			+" in ('COMPLETED','CHARGED','PENDING')) and  UPPER(bookingsource)='ONLINE' "
			+" then ticketqty else 0 end) as online, sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING'))  then ticketqty else 0 end  ) as total, "
			+" a.ticketid from transaction_tickets a, event_reg_transactions b where a.eventid=? "
			+" and a.eventid=b.eventid and a.tid=b.tid group by a.ticketid";
			ticketsales=new String[]{eid};
		}else{
			ticketsalesquery="select sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in "
					+" ('COMPLETED','CHARGED','PENDING')) and UPPER(bookingsource)='MANAGER' "
					+" then ticketqty else 0 end) as manual, sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) "
					+" in ('COMPLETED','CHARGED','PENDING')) and  UPPER(bookingsource)='ONLINE' "
					+" then ticketqty else 0 end) as online, sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING'))  then ticketqty else 0 end  ) as total, "
					+" a.ticketid from transaction_tickets a, event_reg_transactions b where a.eventid=? and b.eventdate=? "
					+" and a.eventid=b.eventid and a.tid=b.tid group by a.ticketid";
			ticketsales=new String[]{eid,eventDate};
		}
		//get ticket soldqty logic
		statobj=dbmanager.executeSelectQuery(ticketsalesquery,ticketsales);
		HashMap<String,String> ticketsalesMap=new HashMap<String,String>();
		if(statobj.getStatus() && statobj.getCount()>0){
		  for(int i=0;i<statobj.getCount();i++){
			  ticketsalesMap.put(dbmanager.getValue(i,"ticketid",""),dbmanager.getValue(i,"total","0"));
		  }
		}
		
		HashMap<String,String> showHideMap = new HashMap<String,String>();
		
		if(!"".equals(eventDate.trim()))
			showHideMap=getShowHideMapForRecurring(eid,eventDate);
		
		
		statobj=dbmanager.executeSelectQuery(getGroupsListQuery,new String []{eid,eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
			    String ticket_groupid=dbmanager.getValue(k,"ticket_groupid","");
				String groupname=dbmanager.getValue(k,"groupname","");
				String grouptype=dbmanager.getValue(k,"grouptype","");
				String position=dbmanager.getValue(k,"position","");
				GroupData tempGroupDataobj=new GroupData();
				tempGroupDataobj.setGroupId(ticket_groupid);
				tempGroupDataobj.setGroupName(groupname);
				tempGroupDataobj.setGroupType(grouptype);
				tempGroupDataobj.setPosition(position);
				ArrayList ticketData=new ArrayList();
				ticketData=getTicketsforGroup(ticket_groupid,eid,userTimeZone,ticketsalesMap,showHideMap,eventDate);
				tempGroupDataobj.setGroupTickets(ticketData);
				groupsList.add(tempGroupDataobj);

			}
		}
		return groupsList;
	}
	
	public static HashMap getTicketsStatus(String eid,String eventDate){
		HashMap statusMap=new HashMap();
		String query="";
		String[] params=new String[4];
		if("".equals(eventDate)){
			query="select * from tickets_info where eventid=?";	
			params=new String[]{eid};
		}else{
			query="select price_id,startstatus,endstatus,soldstatus from recurring_event_tickets_info where evt_id=?::bigint and eventdate=?";
			params=new String[]{eid,eventDate};
		}
		
		DBManager dbm=new DBManager();
		StatusObj sbj=dbm.executeSelectQuery(query,params);
		if(sbj.getStatus() && sbj.getCount()>0){
			for(int i=0;i<sbj.getCount();i++){
				String finalstatus="";
				String startstatus=dbm.getValue(i,"startstatus","");
				String soldstatus=dbm.getValue(i,"soldstatus","");
				String endstatus=dbm.getValue(i,"endstatus","");
				String tktid=dbm.getValue(i,"price_id","");
				
				
				if(("NOT_SOLD".equals(soldstatus))&&("STARTED".equals(startstatus))&&("NOT_CLOSED".equals(endstatus)))
						finalstatus="Active";
					else if("SOLD_OUT".equals(soldstatus))
						finalstatus="Sold Out";
					else if(("STARTED".equals(startstatus))&&("CLOSED".equals(endstatus)))
						finalstatus="Closed";
					else if("NOT_STARTED".equals(startstatus))
						finalstatus="NA";
				statusMap.put(tktid, finalstatus);
			}
		}
	return statusMap;
	}
	
	
	public static ArrayList getTicketsforGroup(String groupId,String eid,String userTimeZone,HashMap<String,String> ticketsalesMap,HashMap<String,String> showhideMap,String eventDate){
		ArrayList tDataList=new ArrayList();
		//select query to fetch the ticketData objects for this group and event ids
		String selectTicketsQry="select  pr.max_ticket,pr.price_id,pr.showyn,pr.ticket_name,pr.process_fee,pr.ticket_price," +
				"gt.position,pr.isdonation,pr.isattendee, pr.max_qty, pr.min_qty, to_char(pr.start_date,'yyyy') as start_yy," +
				"to_char(pr.start_date,'mm') as start_mm,to_char(pr.start_date,'dd') as start_dd," +
				"to_char(pr.end_date,'yyyy') as end_yy,to_char(pr.end_date,'mm') as end_mm," +
				"to_char(pr.end_date,'dd') as end_dd,pr.starttime,pr.endtime,start_date,end_date,start_before,end_before, " +
				"pr.networkcommission from price pr," +
				"event_ticket_groups etg,group_tickets gt where CAST(etg.eventid AS BIGINT)=pr.evt_id" +
				" and pr.price_id=CAST(gt.price_id AS INTEGER) and gt.ticket_groupid=etg.ticket_groupid " +
				"and etg.eventid=? and etg.ticket_groupid=? order by etg.position,gt.position";
		
        
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		
		statobj=dbmanager.executeSelectQuery(selectTicketsQry,new String []{eid,groupId});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			HashMap<String,String> currencydetails=TicketsDB.getCurrencyDetails(eid);
			String currency=currencydetails.get("currencysymbol");
			for(int k=0;k<count1;k++){
				TicketData tData=new TicketData();
				String ticketname=dbmanager.getValue(k,"ticket_name","");
				String maxtktqty=dbmanager.getValue(k,"max_ticket","");
				String ticketPrice=dbmanager.getValue(k,"ticket_price","");
				String ticketid=dbmanager.getValue(k,"price_id","");
				String position=dbmanager.getValue(k,"position","");
				String isAttendee=dbmanager.getValue(k,"isattendee","");
				String isDonation=dbmanager.getValue(k,"isdonation","");
				String start_dd=dbmanager.getValue(k,"start_dd","");
				String start_mm=dbmanager.getValue(k,"start_mm","");
				String start_yy=dbmanager.getValue(k,"start_yy","");
				String end_dd=dbmanager.getValue(k,"end_dd","");
				String end_mm=dbmanager.getValue(k,"end_mm","");
				String end_yy=dbmanager.getValue(k,"end_yy","");
				String start_time=dbmanager.getValue(k,"starttime","");
				String end_time=dbmanager.getValue(k,"endtime","");
				String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
				String start_date=dbmanager.getValue(k,"start_date","");
				String end_date=dbmanager.getValue(k,"end_date","");
				String start_before = dbmanager.getValue(k,"start_before","");
				String end_before = dbmanager.getValue(k,"end_before","");
				String networkcomm = dbmanager.getValue(k,"networkcommission","");
				//String showyn=dbmanager.getValue(k,"showyn","");
				String showyn="";
				String min_qty=dbmanager.getValue(k,"min_qty","");
				String max_qty=dbmanager.getValue(k,"max_qty","");
				
				if("".equals(eventDate))
					showyn=dbmanager.getValue(k,"showyn","");
				else
					showyn=showhideMap.get(ticketid);
				
				if(showyn==null || "".equals(showyn))
					showyn="Y";
				String soldqty=ticketsalesMap.get(ticketid);
				
				
				//System.out.println("soldqty is:"+soldqty);
				if(soldqty==null)soldqty="0";
				DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
				boolean statusdc=dc.convertDateTime(start_date,start_time,userTimeZone);
				DateTimeBean stdtb=dc.getDateTimeBeanObj();
				tData.setStdateTimeBeanObj(stdtb);
				statusdc=dc.convertDateTime(end_date, end_time,userTimeZone);
				DateTimeBean enddtb=dc.getDateTimeBeanObj();
				tData.setEnddateTimeBeanObj(enddtb);
				tData.setPosition(position);
				tData.setTicketName(ticketname);
				ticketPrice=CurrencyFormat.getCurrencyFormat("",ticketPrice,false);
				tData.setTicketPrice(ticketPrice);
				tData.setTicketId(ticketid);
				tData.setIsAttendee(isAttendee);
				tData.setIsDonation(isDonation);
				tData.setStartBefore(end_before);
				tData.setEndBefore(start_before);
				tData.setShowyn(showyn);
				tData.setSoldqty(soldqty);
				tData.setTotalTicketCount(maxtktqty);
				tData.setMinQty(min_qty);
				tData.setMaxQty(max_qty);				
				String processfee=dbmanager.getValue(k,"process_fee","");
				processfee=CurrencyFormat.getCurrencyFormat("",processfee,false);
				tData.setProcessingFee(processfee);
				tData.setCurrency(currency);
				networkcomm=CurrencyFormat.getCurrencyFormat("",networkcomm,false);
				tData.setNetWorkCommission(networkcomm);
				tDataList.add(tData);
			}
		}
		
		
		
		return tDataList;
	}

	public static TicketData saveTicketData(TicketData ticketData, String eid,String userTimeZone,boolean isrecurr){
		String ticketName=ticketData.getTicketName();
		String ticketDescription=ticketData.getTicketDescription();
		String minQty=ticketData.getMinQty();
		String maxQty=ticketData.getMaxQty();
		String ticketPrice=ticketData.getTicketPrice();
		String totalTickets=ticketData.getTotalTicketCount();
		String ticketType=ticketData.getTicketType();
		String start_before = ticketData.getStartBefore();
		String end_before = ticketData.getEndBefore();
		String scancoderequired=ticketData.getIsScanCode();
		String toTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		DateConverter dc=new DateConverter(userTimeZone,toTimeZone);
		String oldtktid=ticketData.getOldTicket();
		if("1".equals(oldtktid))oldtktid="";
		
		
		if(!"".equals(ticketData.getTicketId().trim()) && isrecurr==false)
		ticketData=TicketsDB.setStartEndDates(ticketData);
		
		DateTimeBean dtb=ticketData.getStdateTimeBeanObj();
		boolean statusdc=dc.convertDateTime(dtb);
		String startDate=dc.getDatePart();
		String startTime=dc.getTimePart();
		DateTimeBean edtb=ticketData.getEnddateTimeBeanObj();
		statusdc=dc.convertDateTime(edtb);
		String endDate=dc.getDatePart();
		String endTime=dc.getTimePart();
		
		String ticketCategory="Optional";
		String ticketoptorreq="Optional";
		String isDonation="No";
		String soldQty="0";
		String process_fee=ticketData.getProcessingFee();
	    String startHoursBefore=ticketData.getStartHoursBefore();
		String startMinutesBefore=ticketData.getStartMinutesBefore();
		String endHoursBefore=ticketData.getEndHoursBefore();
		String endMinutesBefore=ticketData.getEndMinutesBefore();
		if("processFeeAttendee".equalsIgnoreCase(ticketData.getProcessFeePaidBy())){
			//if(ticketData.getProcessingFee().indexOf("0.00")>-1)
			if("0".equals(ticketData.getProcessingFee()))
				process_fee="0.00";
			else	
			  process_fee=ticketData.getProcessingFee();
		}
		else{
			process_fee="0.00";
		}
		if("".equals(process_fee)||process_fee==null){
			process_fee="0.00";	
		}
		String status="A";
		String partnerLimit="0";
		String priceId=ticketData.getTicketId();
		String attendeeType=ticketData.getIsAttendee();
		String isAttendee="Yes";
		if("ATTENDEE".equalsIgnoreCase(attendeeType))
		{
			isAttendee="Yes";
		}
		else{
			isAttendee="No";
		}
		
		String networkComission="0";
		if(!ticketType.equalsIgnoreCase("Donation"))
			networkComission=EventDB.getNetWorkCommission(eid);
		
		if("Donation".equalsIgnoreCase(ticketType)){
			
			minQty="0";
			maxQty="1";
			ticketPrice="0.00";
			totalTickets="135790";
			isDonation="Yes";
		}
		String waitListType=ticketData.getWaitListType();
		String waitListLimit=ticketData.getWaitListLimit();
		if("NOLIMIT".equals(waitListType)) waitListLimit="100000";
		if("NO".equals(waitListType)) waitListLimit="0";
		String isPostSelling=ticketData.getIsPostSelling();
		if("".equals(ticketData.getTicketId())){
			//save method
		String query="select nextval('seq_priceid') as priceid";
		priceId=DbUtil.getVal(query, null);
		if("0".equals(ticketData.getTicketGroupId())){
			//It means it is an individual ticket  add a new group
			
			GroupData grpObject=new GroupData();
			grpObject.setGroupDescription("");
			grpObject.setGroupName("");
			grpObject.setGroupId("");
			StatusObj statObjResult=saveGroupData(grpObject, eid);
			if(statObjResult.getStatus()){
				String groupId=statObjResult.getData().toString();
				ticketData.setTicketGroupId(groupId);
			}
			
		}
		String groupId=ticketData.getTicketGroupId();
		HashMap inParams = new HashMap();
		inParams.put("evt_id",eid);
		inParams.put("price_id",priceId);
		inParams.put("ticket_name",ticketName);
		inParams.put("description",ticketDescription);
		inParams.put("ticket_price",ticketPrice);
		inParams.put("max_ticket",totalTickets);
		inParams.put("start_before",start_before);
		inParams.put("end_before",end_before);
		inParams.put("start_date",startDate);
		inParams.put("end_date",endDate);
		inParams.put("min_qty",minQty);
		inParams.put("max_qty",maxQty);
		inParams.put("starttime",startTime);
		inParams.put("endtime",endTime);
		inParams.put("process_fee",process_fee);
		inParams.put("isattendee",isAttendee);
		inParams.put("isdonation",isDonation);
		inParams.put("ticket_category",ticketCategory);
		inParams.put("sold_qty",soldQty);
		inParams.put("ticket_type",ticketoptorreq);
		inParams.put("status",status);
		inParams.put("networkcommission",networkComission);
		
		inParams.put("partnerlimit",partnerLimit);
		
		inParams.put("start_hours_before",startHoursBefore);
		inParams.put("start_minutes_before",startMinutesBefore);
		inParams.put("end_hours_before",endHoursBefore);
		inParams.put("end_minutes_before",endMinutesBefore);
		inParams.put("scan_code_required",scancoderequired);
		
		NQDbUtil.executeQuery("ticketinsert", inParams);
		
		String count=DbUtil.getVal("select max(position) from group_tickets where ticket_groupid=?",new String[]{groupId });
		if(count==null)
		count="0";
		int pos=Integer.parseInt(count);
		pos++;
		String insertGroupTicketsQry="insert into group_tickets(ticket_groupid,price_id,position)" +
				"values(?,?,CAST(? AS INTEGER))";
		DbUtil.executeUpdateQuery(insertGroupTicketsQry,new String[]{groupId,priceId,pos+""});
		
		
		if("Yes".equalsIgnoreCase(ticketData.getMemberTicket())){
			String clubId=ticketData.getHubId();
			
		String insertCommunityQuery="insert into event_communities(price_id,eventid,clubid) values(?,?,?)";
	    DbUtil.executeUpdateQuery(insertCommunityQuery,new String[]{priceId,eid,clubId});
		}
		String ticketaddtype="";
		if("".equals(oldtktid)){
			ticketaddtype="Add Ticket";
			if("Yes".equalsIgnoreCase(isAttendee)){
				CustomAttributesDB.saveTicketBaseProfileSettings(eid,"fname",priceId,"Y");
				CustomAttributesDB.saveTicketBaseProfileSettings(eid,"lname",priceId,"Y");
			}
		}else{
			ticketaddtype="Copy From - "+oldtktid;
			CopyTicketLevelQuestions(oldtktid,priceId,eid);
			if("Yes".equalsIgnoreCase(isAttendee)){
				CustomAttributesDB.saveTicketBaseProfileSettings(eid,"fname",priceId,"Y");
				CustomAttributesDB.saveTicketBaseProfileSettings(eid,"lname",priceId,"Y");
			}else
			    CustomAttributesDB.deleteTicketBaseProfileSettings(eid,priceId);
		}	
		DbUtil.executeUpdateQuery("update price set created_by=?, wait_list_type=?, wait_list_max_qty=CAST(? as INTEGER) where price_id=CAST(? as INTEGER) and evt_id=CAST(? AS BIGINT)", new String[]{ticketaddtype,waitListType,waitListLimit,priceId,eid});
		
	  }else{
			
			//update the ticket details method
			
			String ticketId=ticketData.getTicketId();
			String grpId=ticketData.getTicketGroupId();
			if("ATTENDEE".equalsIgnoreCase(attendeeType))
			{
				isAttendee="Yes";
			}
			else{
				isAttendee="No";
			}
			if("Donation".equalsIgnoreCase(ticketType)){
				isDonation="Yes";
			}
			
			String oldtktName=DbUtil.getVal("select ticket_name from price where price_id=CAST(? as integer)", new String[]{ticketId});
			HashMap inParams = new HashMap();
			inParams.put("evt_id",eid);
			inParams.put("price_id",ticketId);
			inParams.put("ticket_name",ticketName);
			inParams.put("description",ticketDescription);
			inParams.put("ticket_price",ticketPrice);
			inParams.put("max_ticket",totalTickets);
			inParams.put("start_before",start_before);
			inParams.put("end_before",end_before);
			inParams.put("start_date",startDate);
			inParams.put("end_date",endDate);
			inParams.put("min_qty",minQty);
			inParams.put("max_qty",maxQty);
			inParams.put("starttime",startTime);
			inParams.put("endtime",endTime);
			inParams.put("process_fee",process_fee);
			inParams.put("isattendee",isAttendee);
			inParams.put("isdonation",isDonation);
			inParams.put("partnerlimit",partnerLimit);
			inParams.put("start_hours_before",startHoursBefore);
			inParams.put("end_hours_before",endHoursBefore);
			inParams.put("start_minutes_before",startMinutesBefore);
			inParams.put("end_minutes_before",endMinutesBefore);
			inParams.put("scan_code_required",scancoderequired);
			inParams.put("wait_list_type",waitListType);
			inParams.put("wait_list_max_qty",waitListLimit);
			inParams.put("is_post_selling", isPostSelling);
			String updatedat=DateUtil.getCurrDBFormatDate();
			String edittktquery="insert into editticket_track(eventid,ticketid,ticketname,price,fee,min_qty,max_qty,max_ticket,start_date,start_time,end_date,end_time,updated_at,wait_list_type,wait_list_max_qty)"
			                   +" select evt_id,price_id,ticket_name,ticket_price,process_fee,min_qty,max_qty,max_ticket,start_date,CAST(starttime as time without time zone),end_date,"
			                   +" CAST(endtime as time without time zone),to_timestamp(?,'yyyy-MM-dd HH24:MI:ss:S'),wait_list_type,wait_list_max_qty from price where price_id=CAST(? as integer)";
			
			DbUtil.executeUpdateQuery(edittktquery,new String[]{updatedat,ticketId});
			NQDbUtil.executeQuery("updateprice", inParams);
			if(!oldtktName.equals(ticketName))
				DbUtil.executeUpdateQuery("update transaction_tickets set ticketname=? where ticketid=CAST(? as integer)",new String[]{ticketName,ticketId});
			
			AttachTicketToGroup(ticketId,grpId,eid);
			DbUtil.executeUpdateQuery("delete from event_communities where price_id=?",new String[]{ticketId});
			if("Yes".equalsIgnoreCase(ticketData.getMemberTicket())){
				String clubId=ticketData.getHubId();
				//DbUtil.executeUpdateQuery("delete from event_communities where price_id=?",new String[]{ticketId});
				String insertCommunityQuery="insert into event_communities(price_id,eventid,clubid) values(?,?,?)";
				DbUtil.executeUpdateQuery(insertCommunityQuery,new String[]{ticketId,eid,clubId});
			}
			if("Yes".equalsIgnoreCase(isAttendee)){
				CustomAttributesDB.saveTicketBaseProfileSettings(eid,"fname",ticketId,"Y");
				CustomAttributesDB.saveTicketBaseProfileSettings(eid,"lname",ticketId,"Y");
			}else{
				CustomAttributesDB.deleteTicketBaseProfileSettings(eid,ticketId);
			}
		}
		ticketData.setTicketId(priceId);
		return ticketData;
	}
	public static String createNewEmptyGroup(String eid){
		GroupData grpObject=new GroupData();
		String grpId="";
		grpObject.setGroupDescription("");
		grpObject.setGroupName("");
		grpObject.setGroupId("");
		StatusObj statObjResult=saveGroupData(grpObject, eid);
		if(statObjResult.getStatus()){
			grpId=statObjResult.getData().toString();					
		}
		return grpId;
		
	}
	private static void AttachTicketToGroup(String ticketId,String grpId,String eid){
		//
		/*method is invoked for edit event only
		 * get old gid from group_tickets based on ticket id
		 * get old gid type from event_ticket_groups
		 * 
		 * select from length(groupname), a.ticket_groupid from event_ticket_groups a, group_tickets b
		 * where a.ticket_groupid=b.ticket_groupid
		 * and b.ticketid=?
		cases:
		old group is loose, new group is loose (grpId=0)
		----do nothing
		old group loose, new group is an existing gid
		--delete from event_ticket_groups where ticket_groupid =  ?(oldgrpid)
		--update group_tickets set ticket_groupid = grpId where ticketid=?
		old existing gid, new loose
		--create new loose group 
		--get NewCreatedGID
		--update group_tickets set ticket_groupid = NewCreatedGID where ticketid=?
		old existing gid, new same gid
		----do nothing
		old existing gid, new different gid
		--update group_tickets set ticket_groupid = grpId where ticketid=?
		*/
		String oldGrpId="";
		String length="";
		String updateGT_query="update group_tickets set ticket_groupid=? where price_id=?";
		String getGrpId_qry="select length(groupname) as length, a.ticket_groupid as grpid from " +
				"event_ticket_groups a,group_tickets b  where a.ticket_groupid=b.ticket_groupid and b.price_id=?";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(getGrpId_qry,new String []{ticketId});
		if(statobj.getStatus()){
			oldGrpId=dbmanager.getValue(0, "grpid", "");
			length=dbmanager.getValue(0, "length", "");
		}
		int grpNamesize=Integer.parseInt(length);
		if(grpNamesize==0 && grpId.equals("0")){
			//old group is loose, new group is loose
		}
		
		if(grpNamesize!=0 && "0".equals(grpId)){//old existing gid, new loose
			
			String newGrpId=createNewEmptyGroup(eid);
			DbUtil.executeUpdateQuery(updateGT_query,new String[]{newGrpId,ticketId});
		}
		if(oldGrpId.equals(grpId)){//old existing gid, new same gid;
			// System.out.println("old existing gid, new same gid");
		}
		else if(grpNamesize==0 && !grpId.equals("0")){//old group loose, new group is an existing gid
			// System.out.println("old group loose, new group is an existing gid");
			String deleteQry="delete from event_ticket_groups where ticket_groupid=?";
			DbUtil.executeUpdateQuery(deleteQry,new String[]{oldGrpId});
			String count=DbUtil.getVal("select max(position) from group_tickets where ticket_groupid=?",new String[]{grpId });
			if(count==null)	count="0";
			int pos=Integer.parseInt(count);
			pos++;
			updateGT_query="update group_tickets set ticket_groupid=?,position=CAST(? AS INTEGER)" +
					" where price_id=?";
			DbUtil.executeUpdateQuery(updateGT_query,new String[]{grpId,pos+"",ticketId});
		}
		else if(grpNamesize!=0 && !"0".equals(grpId)){
			//old existing gid, new different gid
			// System.out.println("old existing gid, new different gid");
			String count=DbUtil.getVal("select max(position) from group_tickets where ticket_groupid=?",new String[]{grpId });
			if(count==null)	count="0";
			int pos=Integer.parseInt(count);
			pos++;
			updateGT_query="update group_tickets set ticket_groupid=?,position=CAST(? AS INTEGER)" +
					" where price_id=?";
			DbUtil.executeUpdateQuery(updateGT_query,new String[]{grpId,pos+"",ticketId});
		}
		
	}
	public static StatusObj saveGroupData(GroupData groupData,String eid){
		//System.out.println("in saveGroupData method TicketsDB");
		String groupName=groupData.getGroupName();
		String groupDescription=groupData.getGroupDescription();
		String groupId=groupData.getGroupId();
		String ticketType="Optional";
		String groupType="Optional";
		String showname_yn="Y";
		String showdesc_yn="Y";
		StatusObj statusObj=new StatusObj(true,"",null);
		if("".equals(groupData.getGroupId())){
			
			//System.out.println("saving groupdata");
			String query="select nextval('Ticket_Groupid_Seq') as groupid";
			groupId=DbUtil.getVal(query, null);
			//System.out.println("groupid"+groupId);
			//System.out.println("eid"+eid);
			String count=DbUtil.getVal("select max(position) from event_ticket_groups where eventid=?",new String[]{eid });
			if(count==null)
			count="0";
			int pos=Integer.parseInt(count);
            pos++;
            String groupPos=pos+"";
 			String insertQuery="insert into event_ticket_groups(eventid ,ticket_groupid ,groupname ,position," +
					"date ,description ,tickettype,grouptype ,showname_yn ,showdesc_yn )values(?,?,?,CAST(? AS INTEGER)," +
					"now(),?,?,?,?,?)";
			DbUtil.executeUpdateQuery(insertQuery,new String [] {eid,groupId,groupName,groupPos,
					groupDescription,ticketType,groupType,showname_yn,showdesc_yn});
			statusObj.setData(groupId);
		}
		else{
			//update method
			//System.out.println("TicketDetails Group Update Method");
			//System.out.println("groupid"+groupId);
			String updateGroupQuery="update event_ticket_groups set groupname=?,description=? where ticket_groupid=?" +
					"and eventid=?";
			DbUtil.executeUpdateQuery(updateGroupQuery,new String [] {groupName,groupDescription,groupId,eid});
			
		}
		return statusObj;
	}

	public static TicketData getTicketData(String eid, String ticketId,String userTimeZone,String conversionfactor){
		TicketData ticketDataObj=new TicketData();
		HashMap inParams = new HashMap();
		inParams.put("evt_id", eid);
		inParams.put("price_id",ticketId);
		NQDbHelper dbhelper = new NQDbHelper();
		HashMap ticketDetatailsHash=dbhelper.getDataHash("selectticketdetails", inParams);
		
		ticketDataObj.setTicketName((String) ticketDetatailsHash.get("ticket_name"));
		ticketDataObj.setTicketDescription((String) ticketDetatailsHash.get("description"));
		ticketDataObj.setMinQty((String) ticketDetatailsHash.get("min_qty"));
		ticketDataObj.setMaxQty((String) ticketDetatailsHash.get("max_qty"));
		String ticketprice=(String) ticketDetatailsHash.get("ticket_price");
		ticketprice=CurrencyFormat.getCurrencyFormat("",ticketprice,false);
		ticketDataObj.setTicketPrice(ticketprice);
		ticketDataObj.setTotalTicketCount((String) ticketDetatailsHash.get("max_ticket"));
		ticketDataObj.setIsAttendee((String) ticketDetatailsHash.get("isattendee"));
		ticketDataObj.setIsDonation((String) ticketDetatailsHash.get("isdonation"));
		ticketDataObj.setPartnerLimit((String) ticketDetatailsHash.get("partnerlimit"));
		//ticketDataObj.setProcessingFee((String) ticketDetatailsHash.get("process_fee"));
		ticketDataObj.setStartBefore((String) ticketDetatailsHash.get("start_before"));
		ticketDataObj.setEndBefore((String) ticketDetatailsHash.get("end_before"));
		ticketDataObj.setStartHoursBefore((String) ticketDetatailsHash.get("start_hours_before"));
		ticketDataObj.setStartMinutesBefore((String) ticketDetatailsHash.get("start_minutes_before"));
		ticketDataObj.setEndHoursBefore((String) ticketDetatailsHash.get("end_hours_before"));
		ticketDataObj.setEndMinutesBefore((String) ticketDetatailsHash.get("end_minutes_before"));
		String waitListType=(String) ticketDetatailsHash.get("wait_list_type");
		ticketDataObj.setWaitListType(waitListType);
		String waitListLimit=(String) ticketDetatailsHash.get("wait_list_max_qty");
		if("NO".equals(waitListType)||"NOLIMIT".equals(waitListType)) waitListLimit="";
		ticketDataObj.setWaitListLimit(waitListLimit);
		String process_fee=(String) ticketDetatailsHash.get("process_fee");
		process_fee=CurrencyFormat.getCurrencyFormat("",process_fee,false);
		String scancoderequired=(String) ticketDetatailsHash.get("scan_code_required");
		if(scancoderequired==null || "".equals(scancoderequired) || "null".equals(scancoderequired))scancoderequired="Yes";
		ticketDataObj.setIsScanCode(scancoderequired);
		ticketDataObj.setProcessingFee(process_fee);
		if(!"0.00".equals(process_fee)){
			ticketDataObj.setProcessFeePaidBy("processFeeAttendee");
		}
		else{
			ticketDataObj.setProcessFeePaidBy("processFeeMgr");
		}
		ticketDataObj.setIsPostSelling((String) ticketDetatailsHash.get("is_post_selling"));
		String start_dd=(String)ticketDetatailsHash.get("start_dd");
		String start_mm=(String)ticketDetatailsHash.get("start_mm");
		String start_yy=(String)ticketDetatailsHash.get("start_yy");
		String end_dd=(String)ticketDetatailsHash.get("end_dd");
		String end_mm=(String)ticketDetatailsHash.get("end_mm");
		String end_yy=(String)ticketDetatailsHash.get("end_yy");
		String start_time=(String)ticketDetatailsHash.get("starttime");
		String end_time=(String)ticketDetatailsHash.get("endtime");
		
		
		/*String start_date=start_mm+"-"+start_dd+"-"+start_yy;
		String end_date=end_mm+"-"+end_dd+"-"+end_yy;*/
		String start_date=(String)ticketDetatailsHash.get("start_date");
		String end_date=(String)ticketDetatailsHash.get("end_date");
		
		ticketDataObj.setTicketId(ticketId);
		String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
		boolean statusdc=dc.convertDateTime(start_date,start_time,userTimeZone);
		DateTimeBean stdtb=dc.getDateTimeBeanObj();
		ticketDataObj.setStdateTimeBeanObj(stdtb);
		statusdc=dc.convertDateTime(end_date, end_time,userTimeZone);
		DateTimeBean enddtb=dc.getDateTimeBeanObj();
		ticketDataObj.setEnddateTimeBeanObj(enddtb);
		String ismember=DbUtil.getVal("select 'Yes' from event_communities where price_id=?",new String[]{ticketId}); 
        if(ismember==null)
        ismember="No";
        ticketDataObj.setMemberTicket(ismember);
		return ticketDataObj;
		
	}
	public static String getGroupIdforTicket(String ticketId){
		String tempGroupId="";
		String selectGroupIdQuery="select ticket_groupid from group_tickets where price_id=?";
		tempGroupId=DbUtil.getVal(selectGroupIdQuery,new String[]{ticketId});
		return tempGroupId; 
	}
	public static GroupData getGroupInfo(String groupId,String eid){
		String selectGroupInfoQuery="select groupname,description from event_ticket_groups" +
				" where ticket_groupid=? and eventid=?";
		DBManager dbmanager=new DBManager();
		GroupData groupDataObj=new GroupData();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(selectGroupInfoQuery,new String []{groupId,eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			
			    String groupName=dbmanager.getValue(0,"groupname","");
				String description=dbmanager.getValue(0,"description","");
				groupDataObj.setGroupDescription(description);
				groupDataObj.setGroupName(groupName);
				groupDataObj.setGroupId(groupId);

			}
		return groupDataObj;	
	}
	public static void getEndDate(TicketData ticketData,String eid,String userTimeZone){
		String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		String selectEventDates="select end_date,endtime from eventinfo where eventid=CAST(? AS BIGINT)";
		DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(selectEventDates,new String []{eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			
			    String endDate=dbmanager.getValue(0,"end_date","");
				String endTime=dbmanager.getValue(0,"endtime","");
				boolean statusdc=dc.convertDateTime(endDate,endTime,userTimeZone);

			}
		
		DateTimeBean enddtb=dc.getDateTimeBeanObj();
		ticketData.setEnddateTimeBeanObj(enddtb);
	}
	
	public static HashMap<String,String> getTicketServiceFee(String eventid){
		HashMap<String,String> tktservicefeemap=new HashMap<String,String>();
		try{
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
	    statobj=dbm.executeSelectQuery("select current_fee,current_level from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eventid});
		if(statobj.getStatus() && statobj.getCount()>0){
			tktservicefeemap.put("currentfee", dbm.getValue(0,"current_fee",""));
			tktservicefeemap.put("currentlevel", dbm.getValue(0,"current_level",""));
		 }else{
			 tktservicefeemap.put("currentfee", "1");
			 tktservicefeemap.put("currentlevel","100");
		 }
		}catch(Exception e){
		  System.out.println("Exception occured in getTicketServiceFee for event :: "+eventid+" :: "+e.getMessage());
		  tktservicefeemap.put("currentfee", "1");
		  tktservicefeemap.put("currentlevel","100");
		}
		return tktservicefeemap;
	}
	
	public static HashMap<String,String> getCurrencyDetails(String eventid){
		HashMap<String,String> hmap=new HashMap<String,String>();
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		String currencyqry="select currency_symbol,conversion_factor,ec.currency_code  from currency_symbols cs,event_currency ec where " +
		                    "cs.currency_code=ec.currency_code and eventid=?";
		statobj=dbm.executeSelectQuery(currencyqry,new String[]{eventid});
		if(statobj.getStatus()&& statobj.getCount()>0){
			hmap.put("currencysymbol",dbm.getValue(0,"currency_symbol",""));
			hmap.put("conversionfactor", dbm.getValue(0,"conversion_factor",""));
			hmap.put("currencycode",dbm.getValue(0,"currency_code",""));
		}else{
			hmap.put("currencysymbol","$");
			hmap.put("conversionfactor","1");
			hmap.put("currencycode","USD");
		}
		//System.out.println("hmap is:"+hmap); 
		return hmap;
	}
		public static void saveTicketCount(String eid,String tktcount,String tkttype){
		String deletetktcount="delete from config where name='event.reg.eventlevelcheck.count' and config_id=CAST(? AS INTEGER) ";
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid});
		DbUtil.executeUpdateQuery(deletetktcount,new String[]{configid});
		if(!"0".equals(tktcount) && "limited".equals(tkttype))
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) values (CAST(? AS INTEGER),'event.reg.eventlevelcheck.count',?)",new String[]{configid,tktcount});
		
	}
		
	public static ArrayList<Entity> getEventTicketsList(String eventid){
		ArrayList<Entity> ticketslist=new ArrayList<Entity>();
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		statobj=dbm.executeSelectQuery("select price_id,ticket_name from  price where evt_id=CAST(? AS BIGINT) and isdonation='No' and ticket_name is not null", new String[]{eventid});
		if(statobj.getStatus() && statobj.getCount()>0){
			for(int i=0;i<statobj.getCount();i++){
				ticketslist.add(new Entity(dbm.getValue(i,"price_id",""),dbm.getValue(i,"ticket_name","")));
			}
		}
		return  ticketslist;
	}
	
	public static ArrayList<Entity> getTemplateList(String eid){
		ArrayList<Entity> templateList=new ArrayList<Entity>();
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		statobj=dbm.executeSelectQuery("select templateid,templatename from preprinted_tickets_templates where eid=CAST(? AS BIGINT)", new String[]{"0"});
		if(statobj.getStatus() && statobj.getCount()>0){
			for(int i=0;i<statobj.getCount();i++){
				templateList.add(new Entity(dbm.getValue(i,"templateid",""),dbm.getValue(i,"templatename","")));
			}
		}
		
		return templateList;
	}
	
	
	
	public static void CopyTicketLevelQuestions(String oldtktid,String newtktid,String eid){
		copyTicketBaseProfileQuestions(oldtktid,newtktid,eid);
		copyTicketSubGroupAttribs(oldtktid,newtktid,eid);
	}
	
	public static void copyTicketBaseProfileQuestions(String oldtktid,String newtktid,String eid){
	    DBManager dbm=new DBManager();
	    StatusObj statobj=null;
	    statobj=dbm.executeSelectQuery("select * from base_profile_questions where contextid=CAST(? as INTEGER) and groupid=CAST(? AS BIGINT)", new String[]{oldtktid,eid});
	    String baseprofileqry="insert into base_profile_questions(attribid,isrequired,groupid,contextid) select "+
        " attribid,isrequired,groupid,CAST(? as INTEGER) from base_profile_questions where contextid=CAST(? as INTEGER) and groupid=CAST(? AS BIGINT)";
	    if(statobj.getStatus() && statobj.getCount()>0){ 
	     DbUtil.executeUpdateQuery(baseprofileqry, new String[]{newtktid,oldtktid,eid});
	    }
	} 
  
	
	public static void copyTicketSubGroupAttribs(String oldtktid,String newtktid,String eid){
		DBManager dbm=new DBManager();
	    StatusObj statobj=null;
	    statobj=dbm.executeSelectQuery("select * from subgroupattribs where subgroupid=CAST(? as INTEGER) and groupid=CAST(? AS BIGINT)",new String[]{oldtktid,eid});
	    String subgrpattribqry="insert into subgroupattribs(position,attribid,attribsetid,subgroupid,groupid) select "+
	    " position,attribid,attribsetid,CAST(? as INTEGER),groupid from subgroupattribs where subgroupid=CAST(? as INTEGER) and groupid=CAST(? AS BIGINT)";
	    if(statobj.getStatus() && statobj.getCount()>0){
	    	DbUtil.executeUpdateQuery(subgrpattribqry, new String[]{newtktid,oldtktid,eid});	
	    }
	}
	
	public static void getTicketDefaultStartDate(TicketData ticketData,String eid,String userTimeZone){
		String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		
		DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
		
		java.util.Date now=new java.util.Date();
		System.out.println("**** SystemV/EST5 now: "+now);
        Format date=new SimpleDateFormat("yyyy-MM-dd");
        String stdate=date.format(now);
        Format time=new SimpleDateFormat("HH:mm");
        String sttime=time.format(now);
		boolean statusdc=dc.convertDateTime(stdate,sttime,userTimeZone);
		DateTimeBean startdtb=dc.getDateTimeBeanObj();
		ticketData.setStdateTimeBeanObj(startdtb);
		System.out.println("**** UserTimeZone now: "+startdtb.getYyPart()+"-"+startdtb.getMonthPart()+"-"+startdtb.getDdPart()+" "+startdtb.getHhPart()+":"+startdtb.getMmPart()+" "+startdtb.getAmpm());
	}
	
	public static void getTicketDefaultEndDate(TicketData ticketData,String eid){
		
		String selectTicketEndDate="select to_char(date,'yyyy') as teyr," +
				"to_char(date,'mm') as temm," +
				"to_char(date,'dd') as tedd," +
				"to_char(date,'hh') as tehh," +
				"to_char(date,'mi') as temi," +
				"to_char(date,'am') as teampm " +
				"from (select (end_date + COALESCE(endtime, '00:00')::text::time without time zone-interval '1 hour' ) as date " +
				"from eventinfo where eventid=CAST(? AS BIGINT)) ticket_def_enddate";
		DateTimeBean dtbObj=new DateTimeBean();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(selectTicketEndDate,new String []{eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			dtbObj.setYyPart(dbmanager.getValue(0,"teyr",""));
			dtbObj.setMonthPart(dbmanager.getValue(0,"temm",""));
			dtbObj.setDdPart(dbmanager.getValue(0,"tedd",""));
			dtbObj.setHhPart(dbmanager.getValue(0,"tehh",""));
			dtbObj.setMmPart(dbmanager.getValue(0,"temi",""));
			dtbObj.setAmpm(dbmanager.getValue(0,"teampm","").toUpperCase());
		}
		ticketData.setEnddateTimeBeanObj(dtbObj);
	}
	
	
	public static HashMap<String,String> getShowHideMapForRecurring(String eid,String eventDate){
		DBManager dbm=new DBManager();
		StatusObj sbj=null;
		HashMap<String,String>showHideMap=new HashMap<String,String>();
		String query="select ticketid,displayshowyn from reccurringevent_ticketdetails where eventid=?::bigint and eventdate=?";
		sbj=dbm.executeSelectQuery(query,new String[]{eid,eventDate});
		if(sbj.getStatus() && sbj.getCount()>0)
			for(int i=0;i<sbj.getCount();i++)
				showHideMap.put(dbm.getValue(i,"ticketid",""),dbm.getValue(i,"displayshowyn","Y"));
		return showHideMap;
	}
	
	
	
	
	public static TicketData getStartEndDate(TicketData ticketData){
		DateTimeBean startDate=ticketData.getStdateTimeBeanObj();
		DateTimeBean endDate=ticketData.getEnddateTimeBeanObj();
		
		String stdateval=startDate.getMonthPart()+"/"+startDate.getDdPart()+"/"+startDate.getYyPart()+" "+startDate.getHhPart()+":"+startDate.getMmPart()+" "+startDate.getAmpm();
		String enddateval=endDate.getMonthPart()+"/"+endDate.getDdPart()+"/"+endDate.getYyPart()+" "+endDate.getHhPart()+":"+endDate.getMmPart()+" "+endDate.getAmpm();
		ticketData.setNewStartDate(stdateval);
		ticketData.setNewEndDate(enddateval);
		
		return ticketData;
	}
	
	public static TicketData setStartEndDates(TicketData ticketData){
		String startDate=ticketData.getNewStartDate();   
		String endDate=ticketData.getNewEndDate();
		
		String stdatetimearr[]=new String[3];
		String stdatePartArr[]=new String[3];
		String stTimePartArr[]=new String[3];
		String stAmpm="";
		String endatetimearr[]=new String[3];
		String enddatePartArr[]=new String[3];
		String endtimepartArr[]=new String[3];
		String endAmpm="";
		try{
			stdatetimearr=startDate.split(" ");
			stdatePartArr=stdatetimearr[0].split("/");
			stTimePartArr=stdatetimearr[1].split(":");
			stAmpm=stdatetimearr[2];
			endatetimearr=endDate.split(" ");
			enddatePartArr=endatetimearr[0].split("/");
			endtimepartArr=endatetimearr[1].split(":");
			endAmpm=endatetimearr[2];
			}catch(Exception e){
				
			}
		
		DateTimeBean stdatetime=new DateTimeBean();
		System.out.println("the da:"+stdatePartArr[0]+":"+stdatePartArr[1]+":"+stdatePartArr[2]+":"+stTimePartArr[0]+":"+stTimePartArr[1]+"ampm::"+stAmpm);
		stdatetime.setMonthPart(stdatePartArr[0]);
		stdatetime.setDdPart(stdatePartArr[1]);
		stdatetime.setYyPart(stdatePartArr[2]);
		stdatetime.setHhPart(stTimePartArr[0]);
		stdatetime.setMmPart(stTimePartArr[1]);
		stdatetime.setAmpm(stAmpm);
		ticketData.setStdateTimeBeanObj(stdatetime);
		
		DateTimeBean enddatetime=new DateTimeBean();
		System.out.println("the da:"+enddatePartArr[0]+":"+enddatePartArr[1]+":"+enddatePartArr[2]+":"+endtimepartArr[0]+":"+endtimepartArr[1]+"ampm::"+endAmpm);
		enddatetime.setMonthPart(enddatePartArr[0]);
		enddatetime.setDdPart(enddatePartArr[1]);
		enddatetime.setYyPart(enddatePartArr[2]);
		enddatetime.setHhPart(endtimepartArr[0]);
		enddatetime.setMmPart(endtimepartArr[1]);
		enddatetime.setAmpm(endAmpm);
		ticketData.setEnddateTimeBeanObj(enddatetime);
	return ticketData;
	}
	
	public static JSONObject saveConditionalTicketingRules(String eid, String rules,String ruleType){		
		
		JSONObject status=new JSONObject();		
		try{
			status.put("status", "fail");
			if(ruleType.equals("blockRules"))
				ruleType="Block";

			JSONArray rulesArray=new JSONArray();
			try{
				JSONArray jObject=null;
				try{
					jObject = new JSONArray(rules.trim());
				}catch(Exception e){
					jObject=new JSONArray();
				}			
				for(int i=0;i<((JSONArray)jObject).length();i++){
					rulesArray.put(((JSONArray)jObject).getJSONObject(i));
				}
			}catch(Exception e){
				System.out.println("Exception in parsing request param json:: "+eid);
			}	
			String selectQuery="select * from  conditional_ticketing_rules  where eventid=CAST(? AS BIGINT)";
			DBManager dbManager=new DBManager();		
			StatusObj statobj=dbManager.executeSelectQuery(selectQuery, new String []{eid});
			if(statobj.getStatus()==true && statobj.getCount()>0){
				JSONArray dbRules=null;
				try{
					try{
						dbRules=new JSONArray(dbManager.getValue(0, "rules", "[]"));
					}catch(Exception e){
						dbRules=new JSONArray();
					}
					for(int i=0;i<dbRules.length();i++){					
						if(ruleType.equalsIgnoreCase("mustRules")){ //removing all must rules and adding all other rules
							if(!"mustByOR".equalsIgnoreCase(dbRules.getJSONObject(i).getString("condition")) && !"mustByAND".equalsIgnoreCase(dbRules.getJSONObject(i).getString("condition"))){
								rulesArray.put(dbRules.getJSONObject(i));
							}
						}
						else if(ruleType.equalsIgnoreCase("requireRules")){//removing all require rules and adding all other rules
							if(!"requireOR".equalsIgnoreCase(dbRules.getJSONObject(i).getString("condition")) && !"requireAND".equalsIgnoreCase(dbRules.getJSONObject(i).getString("condition"))){
								rulesArray.put(dbRules.getJSONObject(i));
							}
						}
						else if(!ruleType.equalsIgnoreCase(dbRules.getJSONObject(i).getString("condition"))){//removing all block rules and adding all other rules
							rulesArray.put(dbRules.getJSONObject(i));
						}
					}
				}catch(Exception e){
					System.out.println("Exception in parsing db rules:: "+eid);
				}
				String updateQuery="update conditional_ticketing_rules set rules=?, updated_at=now() where eventid=CAST(? AS BIGINT)";
				DbUtil.executeUpdateQuery(updateQuery, new String[]{rulesArray.toString(),eid});
			}
			else
				DbUtil.executeUpdateQuery("insert into conditional_ticketing_rules(eventid, rules, created_at, updated_at) values (CAST(? AS BIGINT),?,now(),now())",new String[]{eid,rulesArray.toString()});
			status.put("status", "success");
		}
		catch(Exception e){

		}
		return status;
	
	}
	public static JSONObject getExistingRules(String eventid){
		JSONObject rules=new JSONObject();
			try{
				DBManager dbMnager = new DBManager();
				StatusObj statusOb = dbMnager
						.executeSelectQuery(
								"select rules from conditional_ticketing_rules where eventid=cast(? as BIGINT)",
								new String[] { eventid });
				
				if (statusOb.getStatus() && statusOb.getCount() > 0) {
					System.out.println("inside rules"+eventid);
					JSONArray dbRuleObject = new JSONArray(dbMnager.getValue(0, "rules", "[]"));
					for(int i=0;i<dbRuleObject.length();i++){
												
						if("custom".equalsIgnoreCase(dbRuleObject.getJSONObject(i).getString("condition")))
							rules.put("customRules",dbRuleObject.getJSONObject(i));
						if("requireOR".equalsIgnoreCase(dbRuleObject.getJSONObject(i).getString("condition"))||"requireAND".equalsIgnoreCase(dbRuleObject.getJSONObject(i).getString("condition"))){
							if(rules.has("requireRules")){
								rules.getJSONArray("requireRules").put(dbRuleObject.getJSONObject(i));
							}else{
								rules.put("requireRules", new JSONArray().put(dbRuleObject.getJSONObject(i)));						
							}
						 }						
						if("MustByOR".equalsIgnoreCase(dbRuleObject.getJSONObject(i).getString("condition"))||"MustByAND".equalsIgnoreCase(dbRuleObject.getJSONObject(i).getString("condition"))){
							if(rules.has("mustRules")){
								rules.getJSONArray("mustRules").put(dbRuleObject.getJSONObject(i));
							}else{
								rules.put("mustRules", new JSONArray().put(dbRuleObject.getJSONObject(i)));						
							}
						 }	
						if("Block".equalsIgnoreCase(dbRuleObject.getJSONObject(i).getString("condition"))){
							if(rules.has("blockRules")){
								rules.getJSONArray("blockRules").put(dbRuleObject.getJSONObject(i));
							}else{
								rules.put("blockRules", new JSONArray().put(dbRuleObject.getJSONObject(i)));						
							}
						 }	
				     }
			      }
			  }
			catch(Exception e){
				System.out.println("exception in getting rules");
			}			
			return rules;
	}

}
