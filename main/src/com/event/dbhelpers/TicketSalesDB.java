package com.event.dbhelpers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.eventbee.general.DBManager;

import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class TicketSalesDB {
	public static String deleteedticketsquery="select sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING')) "
		+" then ticketqty else 0 end) as sold,ticketid, groupname,  ticketname "
		+" from transaction_tickets a, event_reg_transactions b where a.eventid=? "
		+" and a.eventid=b.eventid and a.tid=b.tid and b.bookingdomain not like '%VBEE%'" 
		+" and ticketid not in "
		+" (select price_id from price where evt_id=CAST(? AS BIGINT)) group by ticketid, "
		+" ticketname, groupname having sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in "
		+" ('COMPLETED','CHARGED','PENDING')) then ticketqty else 0 end)>0 "
		+" order by groupname, ticketname";
	public static String deleteedticketsByDatequery="select sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING')) "
		+" then ticketqty else 0 end) as sold,ticketid, groupname,  ticketname "
		+" from transaction_tickets a, event_reg_transactions b where a.eventid=? "
		+" and a.eventid=b.eventid and a.tid=b.tid and b.eventdate = ? " 
		+" and ticketid not in "
		+" (select price_id from price where evt_id=CAST(? AS BIGINT)) group by ticketid, "
		+" ticketname, groupname having sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in "
		+" ('COMPLETED','CHARGED','PENDING')) then ticketqty else 0 end)>0 "
		+" order by groupname, ticketname";
	public static String ticketsourcequery="select sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING')) "
		+" then ticketqty else 0 end) as sold,ticketid,b.bookingsource "
		+" from transaction_tickets a, event_reg_transactions b, price c "
		+" where a.eventid=? and a.eventid=b.eventid and a.tid=b.tid and "
		+" a.ticketid=c.price_id group by ticketid, bookingsource "
		+" having sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in "
		+" ('COMPLETED','CHARGED','PENDING')) then ticketqty else 0 end)>0";
	/*public static String ticketsourceByDatequery="select sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING')) "
		+" then ticketqty else 0 end) as sold,ticketid,b.bookingsource "
		+" from transaction_tickets a, event_reg_transactions b, price c "
		+" where a.eventid=? and a.eventid=b.eventid and a.tid=b.tid  and b.eventdate = ? " 
		+" and a.ticketid=c.price_id group by ticketid, bookingsource "
		+" having sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in "
		+" ('COMPLETED','CHARGED','PENDING')) then ticketqty else 0 end)>0";*/
	public static String ticketsourceByDatequery="select sum(case when (UPPER(coalesce(paymentstatus,'')) in ('COMPLETED','CHARGED','PENDING')) " +
		" then ticketqty else 0 end) as sold,ticketid,b.bookingsource  " +
		" from transaction_tickets a, event_reg_transactions b " +
		" where b.eventid||'_'||b.eventdate=? " +
		" and a.eventid=b.eventid and a.tid=b.tid group by ticketid,b.bookingsource ";
	public static String ticketsourceForAllDates="select sum(case when (UPPER(coalesce(paymentstatus,'')) in ('COMPLETED','CHARGED','PENDING')) " +
			" then ticketqty else 0 end) as sold,ticketid,b.bookingsource,b.eventdate  " +
			" from transaction_tickets a, event_reg_transactions b " +
			" where b.eventid=? " +
			" and a.eventid=b.eventid and a.tid=b.tid group by ticketid,b.bookingsource,b.eventdate ";
	public static String deletedticketsourcequery="select sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING')) "
		+" then ticketqty else 0 end) as sold,ticketid,ticketname, "
		+" b.bookingsource from transaction_tickets a, event_reg_transactions b "
		+" where a.eventid=? and a.eventid=b.eventid and a.tid=b.tid and b.bookingdomain not like '%VBEE%' and "
		+" a.ticketid not in (select price_id from price where evt_id=CAST(? AS BIGINT)) "
		+" group by ticketid, bookingsource, ticketname "
		+" having sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in "
		+" ('COMPLETED','CHARGED','PENDING')) then ticketqty else 0 end)>0";	
	public static String deletedticketsourceByDatequery="select sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING')) "
		+" then ticketqty else 0 end) as sold,ticketid,ticketname, "
		+" b.bookingsource from transaction_tickets a, event_reg_transactions b "
		+" where a.eventid=? and a.eventid=b.eventid and a.tid=b.tid and b.eventdate = ? "
		+" and a.ticketid not in (select price_id from price where evt_id=CAST(? AS BIGINT)) "
		+" group by ticketid, bookingsource, ticketname "
		+" having sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in "
		+" ('COMPLETED','CHARGED','PENDING')) then ticketqty else 0 end)>0";	
	public static String totalsalesquery="select sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in "
		+" ('COMPLETED','CHARGED','PENDING')) and UPPER(bookingsource)='MANAGER' "
		+" then ticketqty else 0 end) as manual, sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) "
		+" in ('COMPLETED','CHARGED','PENDING')) and  UPPER(bookingsource)='ONLINE' "
		+" then ticketqty else 0 end) as online, sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING'))  then ticketqty else 0 end  ) as total "
		+" from transaction_tickets a, event_reg_transactions b where a.eventid=? "
		+" and a.eventid=b.eventid and a.tid=b.tid";
	/*public static String totalsalesquerybyDate="select sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in "
		+" ('COMPLETED','CHARGED','PENDING')) and UPPER(bookingsource)='MANAGER' "
		+" then ticketqty else 0 end) as manual, sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) "
		+" in ('COMPLETED','CHARGED','PENDING')) and  UPPER(bookingsource)='ONLINE' "
		+" then ticketqty else 0 end) as online, sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING'))  then ticketqty else 0 end  ) as total "
		+" from transaction_tickets a, event_reg_transactions b where a.eventid=? "
		+" and a.eventid=b.eventid and a.tid=b.tid and b.eventdate =?";*/
	public static String totalsalesquerybyDate="select sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in "
		+" ('COMPLETED','CHARGED','PENDING')) and UPPER(bookingsource)='MANAGER' "
		+" then ticketqty else 0 end) as manual, sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) "
		+" in ('COMPLETED','CHARGED','PENDING')) and  UPPER(bookingsource)='ONLINE' "
		+" then ticketqty else 0 end) as online, sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING'))  then ticketqty else 0 end  ) as total "
		+" from transaction_tickets a, event_reg_transactions b where a.eventid=? "
		+" and a.eventid=b.eventid and a.tid=b.tid and b.eventdate =?";
	public static String ticketstatusquery="select distinct a.price_id, groupname, ticket_name, max_ticket ,sold_qty, "
		+" case when start_date+cast(cast(starttime as text) as time )>now()  "
		+" then 'NOT_STARTED' else 'STARTED' end as startstatus,"
		+" case when end_date+cast(cast(endtime as text) as time )<now()  "
		+" then 'CLOSED' else 'NOT_CLOSED' end as endstatus,"
		+" case when sold_qty<max_ticket then 'AVAILABLE' else 'SOLD_OUT' end as soldstatus "
		+" from price a, event_ticket_groups b, group_tickets c where evt_id=CAST(? AS BIGINT) and sold_qty>0 "
		+" and a.price_id=CAST(c.price_id AS INTEGER) and c.ticket_groupid=b.ticket_groupid "
		+" order by groupname, ticket_name";
/*public static String ticketstatusByDatequery="select distinct a.price_id, groupname, ticket_name, max_ticket," +
			"sold_qty,case when to_timestamp(?,'yyyy-mm-dd')-cast(start_before ||'days'  as interval)" +
			">now()	then 'NOT_STARTED' else 'STARTED' end as startstatus," +
			"case when to_timestamp(?,'yyyy-mm-dd')-cast(end_before ||'days'  as interval)<now()	then " +
			"'CLOSED' else 'NOT_CLOSED' end as endstatus from price a, event_ticket_groups b, group_tickets c where " +
			"evt_id=to_number(?,'99999999999999') and sold_qty>0 and" +
			" a.price_id=to_number(c.price_id,'999999999999') and c.ticket_groupid=b.ticket_groupid " +
			"order by groupname, ticket_name";
	*/
	public static String ticketstatusByDatequery="select distinct a.price_id, groupname, ticket_name, max_ticket," +
	"sold_qty, 'recurr' as startstatus," +
	"'recurr' as endstatus from price a, event_ticket_groups b, group_tickets c where " +
	"evt_id=CAST(? AS BIGINT) and sold_qty>0 and" +
	" a.price_id=CAST(c.price_id AS INTEGER) and c.ticket_groupid=b.ticket_groupid " +
	"order by groupname, ticket_name";
	
	public static String volumeticketstatusquery="select a.ticketid,a.ticketname,sum(a.ticketqty) as soldqty," +
	"trigger_qty,post_trigger_type,upper_limit,cycles_limit,show_status,sale_status, " +
	"case when act_start_date+cast(cast(to_timestamp(COALESCE(act_starttime,'00'),'HH24:MI:SS') as text) as time )" +
	"<=current_timestamp then 'STARTED' else 'NOT STARTED' end as starttimestatus," +
	"case when end_date+cast(cast(to_timestamp(COALESCE(end_time,'00'),'HH24:MI:SS') as text) as time )" +
	"<=current_timestamp then 'CLOSED' else 'NOT CLOSED' end as endtimestatus " +
	"from transaction_tickets a, event_reg_transactions b,groupdeal_tickets c where a.eventid=b.eventid " +
	"and a.tid=b.tid and a.eventid=? and a.eventid=c.eventid and a.ticketid=c.ticketid and " +
	"b.paymentstatus='Completed' and b.bookingdomain like '%VBEE%' group by a.ticketid,ticketname," +
	"trigger_qty,post_trigger_type,upper_limit,cycles_limit,show_status,sale_status,starttimestatus,endtimestatus";
	
	public static String deletedTicketsQry="select ticketname, groupname,sum(ticketqty) as sold_qty from transaction_tickets " +
			"where ticketid in (select distinct ticketid from transaction_tickets where eventid=? " +
			"except select price_id from price where evt_id=CAST(? AS BIGINT)) and eventid=? group by ticketname, groupname";

	public static Vector getTicketStatusInfo(String groupid, Vector ticketsVector, HashMap ticketSourceMap, String eventDate){
		DBManager dbmanager=new DBManager();
		StatusObj stobj;
		if("".equals(eventDate)){
			stobj=dbmanager.executeSelectQuery(ticketstatusquery,new String[]{groupid});
		}
		else{
			
			//String edate=getDateAsDBString(eventDate);
			stobj=dbmanager.executeSelectQuery(ticketstatusByDatequery,new String[]{groupid});
			
		}
		
		if(stobj.getStatus()){
			
			for(int i=0;i<stobj.getCount();i++){
				HashMap<String,String> hm=new HashMap<String,String>();
				String ticketid=dbmanager.getValue(i,"price_id","");
				String ticketname=dbmanager.getValue(i,"ticket_name","");
				String groupname=dbmanager.getValue(i,"groupname","");
				//String source=dbmanager.getValue(i,"bookingsource","");

                String key="online"+"_"+ticketid;
				String onlinesales=(String)ticketSourceMap.get(key);
				if(onlinesales==null) onlinesales="0";
				key="Manager"+"_"+ticketid;
				String offlinesales=(String)ticketSourceMap.get(key);
				if(offlinesales==null) offlinesales="0";
				hm.put("offlinesales",offlinesales);
				hm.put("onlinesales",onlinesales);
				hm.put("ticket_name",ticketname);
				hm.put("groupname",groupname);
				int soldqty=Integer.parseInt(offlinesales)+Integer.parseInt(onlinesales);
				int actualsold=Integer.parseInt(dbmanager.getValue(i,"sold_qty","0"));
				hm.put("sold_qty",""+soldqty);
				//sold_qty<max_ticket
				int max_ticket=Integer.parseInt(dbmanager.getValue(i,"max_ticket","0"));
				String status="Available";
				if(actualsold>=max_ticket){
					status="Sold Out";
				}
				hm.put("total_qty",dbmanager.getValue(i,"max_ticket",""));
				String startstatus=dbmanager.getValue(i,"startstatus","");
				String endstatus=dbmanager.getValue(i,"endstatus","");
				//String soldstatus=dbmanager.getValue(i,"soldstatus","");
				if("recurr".equals((String)ticketSourceMap.get("recurringstatus")))startstatus="recurr";
				if("CLOSED".equals(endstatus)){
					status="Ended";
				}else if("NOT_STARTED".equals(startstatus)){
					status="On Hold";
				}
				else if("recurr".equals(startstatus)){
					status="";
				}
				hm.put("status",status);
				if("recurr".equals(startstatus) && "alldates".equals(eventDate))
					hm.put("alldates","yes"); 
				else hm.put("alldates","no"); //for recurring alldates
				ticketsVector.add(hm);
			}
		}
		return ticketsVector;
	}
	public static Vector getDeletedTicketsInfo(String groupid, Vector ticketsVector, HashMap ticketSourceMap, String eventDate){
		DBManager dbmanager=new DBManager();
		StatusObj stobj;
		if("".equals(eventDate)){
			stobj=dbmanager.executeSelectQuery(deleteedticketsquery,new String[]{groupid,groupid});
		}
		else{
			String edate=getDateAsDBString(eventDate);
			stobj=dbmanager.executeSelectQuery(deleteedticketsByDatequery,new String[]{groupid,edate,groupid});
		}
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				HashMap hm=new HashMap();
				String ticketid=dbmanager.getValue(i,"ticketid","");
				String ticketname=dbmanager.getValue(i,"ticketname","");
				String groupname=dbmanager.getValue(i,"groupname","");
				String source=dbmanager.getValue(i,"bookingsource","");

				String key="online"+"_"+ticketid+"_"+ticketname;
				String onlinesales=(String)ticketSourceMap.get(key);
				if(onlinesales==null) onlinesales="0";
				key="Manager"+"_"+ticketid+"_"+ticketname;
				String offlinesales=(String)ticketSourceMap.get(key);
				if(offlinesales==null) offlinesales="0";
				hm.put("offlinesales",offlinesales);
				hm.put("onlinesales",onlinesales);
				hm.put("ticket_name",ticketname);
				hm.put("groupname",groupname);
				hm.put("sold_qty",dbmanager.getValue(i,"sold",""));

				hm.put("status","Deleted");
				ticketsVector.add(hm);
			}
		}
		return ticketsVector;
	}
	public static HashMap getTicketSourceMap(String groupid, String eventDate){

		DBManager dbmanager=new DBManager();
		StatusObj stobj;
		if("".equals(eventDate)){
			stobj=dbmanager.executeSelectQuery(ticketsourcequery,new String[]{groupid});
		}
		else{
			String edate=getDateAsDBString(eventDate);
			String eventIdAndDate=groupid+"_"+edate;
			stobj=dbmanager.executeSelectQuery(ticketsourceByDatequery,new String[]{eventIdAndDate});
		}
		HashMap hm=new HashMap();
		
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				//String ticketname=dbmanager.getValue(i,"ticketname","");

				String ticketid=dbmanager.getValue(i,"ticketid","");
				String source=dbmanager.getValue(i,"bookingsource","");
				String key=source+"_"+ticketid;
				hm.put(key,dbmanager.getValue(i,"sold",""));
			}
		}
		return hm;
	}
	
	public static HashMap<String,HashMap<String,HashMap<String,String>>> getTicketSourceMapForAllDates(String groupid, String eventDate,HashMap totalSales,ArrayList<String> eventDates){

		DBManager dbmanager=new DBManager();
		StatusObj stobj;
		stobj=dbmanager.executeSelectQuery(ticketsourceForAllDates,new String[]{groupid});
		HashMap<String,HashMap<String,HashMap<String,String>>> recuringTicketsSoldMap=new HashMap<String,HashMap<String,HashMap<String,String>>>();
		int totalManual=0,totalOnline=0,totalQty=0;
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				String ticketid=dbmanager.getValue(i,"ticketid","");
				String source=dbmanager.getValue(i,"bookingsource","");
				String eventdate=dbmanager.getValue(i, "eventdate", "");
				if(!eventDates.contains(eventdate))
					eventDates.add(eventdate);
				DateUtil.sortCustomDate(eventDates,"EEE, MMM dd, yyyy hh:mm a",false);
				String sold=dbmanager.getValue(i,"sold","");
				if(recuringTicketsSoldMap.containsKey(ticketid)){
					if(recuringTicketsSoldMap.get(ticketid).containsKey(eventdate)){
						HashMap<String,String> salesStatus=recuringTicketsSoldMap.get(ticketid).get(eventdate);
						salesStatus.put(source, sold);
						String manual="0",online="0";
						manual=salesStatus.get("Manager")==null?"0":salesStatus.get("Manager");
						online=salesStatus.get("online")==null?"0":salesStatus.get("online");
						String soldqty=String.valueOf(Integer.parseInt(manual)+Integer.parseInt(online));
						salesStatus.put("soldqty", soldqty);
						if("Manager".equals(source)) totalManual+=Integer.parseInt(sold);
						if("online".equals(source)) totalOnline+=Integer.parseInt(sold);
						totalQty+=Integer.parseInt(sold);
						//recuringTicketsSoldMap.get(ticketid).put(eventdate,salesStatus);
					}else{
						HashMap<String,String> salesStatus = new HashMap<String,String>();
						salesStatus.put(source, sold);
						if("Manager".equals(source)) totalManual+=Integer.parseInt(sold);
						if("online".equals(source)) totalOnline+=Integer.parseInt(sold);
						totalQty+=Integer.parseInt(sold);
						salesStatus.put("soldqty", sold);
						recuringTicketsSoldMap.get(ticketid).put(eventdate, salesStatus);
					}
				}else{
					HashMap<String,HashMap<String,String>> dateMap=new HashMap<String,HashMap<String,String>>();
					HashMap<String,String> salesStatus = new HashMap<String,String>();
					salesStatus.put(source, sold);
					if("Manager".equals(source)) totalManual+=Integer.parseInt(sold);
					if("online".equals(source)) totalOnline+=Integer.parseInt(sold);
					totalQty+=Integer.parseInt(sold);
					salesStatus.put("soldqty", sold);
					dateMap.put(eventdate, salesStatus);
					recuringTicketsSoldMap.put(ticketid, dateMap);
				}
			}
		}
		
		totalSales.put("manual", totalManual);
		totalSales.put("online", totalOnline);
		totalSales.put("total", totalQty);
		
		return recuringTicketsSoldMap;
	}
	
	public static HashMap<String, HashMap<String,String>> getTicketDetails(String groupid){
		DBManager dbmanager=new DBManager();
		StatusObj stobj;
		stobj=dbmanager.executeSelectQuery(ticketstatusByDatequery,new String[]{groupid});
		HashMap<String, HashMap<String,String>> ticketDetails=new HashMap<String, HashMap<String,String>>();
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				HashMap<String,String> hm=new HashMap<String,String>();
				String ticketid=dbmanager.getValue(i,"price_id","");
				String ticketname=dbmanager.getValue(i,"ticket_name","");
				String groupname=dbmanager.getValue(i,"groupname","");
				hm.put("ticket_name",ticketname);
				hm.put("groupname",groupname);
				hm.put("total_qty",dbmanager.getValue(i,"max_ticket",""));
				ticketDetails.put(ticketid, hm);
			}
		}
		return ticketDetails;
	}
	public static HashMap getDeletedTicketSourceMap(String groupid, String eventDate){
		DBManager dbmanager=new DBManager();
		StatusObj stobj;
		if("".equals(eventDate)){
			stobj=dbmanager.executeSelectQuery(deletedticketsourcequery,new String[]{groupid, groupid});
		}
		else{
			String edate=getDateAsDBString(eventDate);
			stobj=dbmanager.executeSelectQuery(deletedticketsourceByDatequery,new String[]{groupid,edate, groupid});
			
		}
		HashMap hm=new HashMap();
		
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				String ticketname=dbmanager.getValue(i,"ticketname","");
				String ticketid=dbmanager.getValue(i,"ticketid","");
				String source=dbmanager.getValue(i,"bookingsource","");
				String key=source+"_"+ticketid+"_"+ticketname;
				hm.put(key,dbmanager.getValue(i,"sold",""));
			}
		}
		return hm;
	}
	
	public static HashMap<String, String> getDeletedTicketsMap(String groupid, boolean isRecurring){
		HashMap delteteTicketsMap=new HashMap();
		DBManager dbmanager=new DBManager();
		StatusObj stobj;
		stobj=dbmanager.executeSelectQuery(deletedTicketsQry,new String[]{groupid, groupid, groupid});
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				String ticketname=dbmanager.getValue(i,"ticketname","");
				String groupname=dbmanager.getValue(i,"groupname","");
				String sold_qty=dbmanager.getValue(i,"sold_qty","");
				if(!"".equals(groupname)) ticketname=ticketname+" ("+groupname+")";
				delteteTicketsMap.put(ticketname,sold_qty);
			}
		}
		return delteteTicketsMap;
		
	}
	public static HashMap getTotalsales(String groupid, String eventDate){
		DBManager dbmanager=new DBManager();
		HashMap hm=new HashMap();
		hm.put("total","0");
		hm.put("manual","0");
		hm.put("online","0");
		StatusObj stobj=null;
		if("".equals(eventDate)){
		stobj=dbmanager.executeSelectQuery(totalsalesquery,new String[]{groupid});
		}
		else{
			
			String edate=getDateAsDBString(eventDate);
			stobj=dbmanager.executeSelectQuery(totalsalesquerybyDate,new String[]{groupid,edate});
		}
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				String total=dbmanager.getValue(i,"total","0");
				String manual=dbmanager.getValue(i,"manual","0");
				String online=dbmanager.getValue(i,"online","0");
                hm.put("total",total);
				hm.put("manual",manual);
				hm.put("online",online);

			}
		}
		
		return hm;
	}
	public static String getDateAsDBString(String eventdate) {
		/*
		String strdate = "";
		try{
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
			Date seldate = df.parse(eventdate);
			strdate = sdf.format(seldate);
		}catch (Exception e) {
		}
		return strdate;
		*/
		return eventdate;
	}
	
	public static Vector getVolumeTicketStatusInfo(String eid, Vector ticketsVector){
		DBManager dbmanager=new DBManager();
		StatusObj stobj;
		stobj=dbmanager.executeSelectQuery(volumeticketstatusquery,new String[]{eid});
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				HashMap<String,String> hm=new HashMap<String,String>();
				String ticketid=dbmanager.getValue(i,"ticketid","");
				String ticketname=dbmanager.getValue(i,"ticketname","");
				String soldqty=dbmanager.getValue(i,"soldqty","0");
				String posttriggertype=dbmanager.getValue(i,"post_trigger_type","0");
				String triggerqty=dbmanager.getValue(i,"trigger_qty","0");
				String upperlimit=dbmanager.getValue(i,"upper_limit","0");
				String cycleslimit=dbmanager.getValue(i,"cycles_limit","0");
				String showstatus=dbmanager.getValue(i,"show_status","");
				String starttimestatus=dbmanager.getValue(i,"starttimestatus","");
				String endtimestatus=dbmanager.getValue(i,"endtimestatus","");
				hm.put("offlinesales","0");
				hm.put("onlinesales",soldqty);
				hm.put("ticket_name",ticketname);
				hm.put("sold_qty",""+soldqty);
				int max_ticket=0;
				if("3".equals(posttriggertype))
					max_ticket = Integer.parseInt(triggerqty) * Integer.parseInt(cycleslimit);
				else if("2".equals(posttriggertype))
					max_ticket = Integer.parseInt(upperlimit);
				else
					max_ticket = Integer.parseInt(triggerqty);

				String salestatus=dbmanager.getValue(i,"sale_status","Available");
				String status="Available";
				if("Closed".equals(salestatus)){
					status="Ended";
				}else if("NOT_STARTED".equals(starttimestatus) && "Available".equals(salestatus)){
					status="On Hold";
				}else if(Integer.parseInt(soldqty)>=max_ticket && "Available".equals(salestatus)  )
					status="Sold Out";
				else status=salestatus;
				
				hm.put("total_qty",String.valueOf(max_ticket));
				hm.put("status",status);
				ticketsVector.add(hm);
			}
		}
		return ticketsVector;
	}
	
	public static HashMap<String,String> getTotalsalesByDates(Vector vec){
		int manual=0;
		int online=0;
		int total=0;
		HashMap<String,String> map=new HashMap<String,String>();
		for(int i=0;i<vec.size();i++)
		{
		HashMap m=(HashMap)vec.get(i);
		int man=Integer.parseInt((String)m.get("offlinesales"));
		int o=Integer.parseInt((String)m.get("onlinesales"));
		int tot=Integer.parseInt((String)m.get("sold_qty"));
		manual=manual+man;
		online=online+o;
		total=total+tot;
		}
		map.put("manual",Integer.toString(manual));
		map.put("online",Integer.toString(online));
		map.put("total",Integer.toString(total));
		return map;
	}
	
	public static String getTransactionsCount(String eventid){
		return DbUtil.getVal("select count(*) from event_reg_transactions where eventid=?", new String[]{eventid});
	}
	
	
}
