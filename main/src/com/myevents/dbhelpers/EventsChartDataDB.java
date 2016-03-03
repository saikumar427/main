package com.myevents.dbhelpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import com.event.beans.EventData;
import com.event.dbhelpers.EventDB;
import com.eventbee.general.DBManager;
import com.eventbee.general.StatusObj;

public class EventsChartDataDB {
	private static final Logger log = Logger.getLogger(EventsChartDataDB.class);
	private static final String EVENT_TICKETS_INFO = "select sum(case when (UPPER(coalesce(paymentstatus,"
			+ "'COMPLETED')) in ('COMPLETED','CHARGED','PENDING')) then ticketqty else 0 end) "
			+ "as sold,groupname,ticketname from transaction_tickets a, event_reg_transactions b "
			+ "where a.eventid=? and a.tid=b.tid group by groupname, ticketname having sum(case "
			+ "when (UPPER(coalesce(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING')) then "
			+ "ticketqty else 0 end)>0 order by groupname, ticketname";
	private static final String EVENT_TICKETS_INFO_BYDATE = "select sum(case when (UPPER(coalesce " +
			"(paymentstatus,'COMPLETED')) in ('COMPLETED','CHARGED','PENDING')) then ticketqty else 0 end) " +
			"as sold,groupname,ticketname from transaction_tickets a, event_reg_transactions b, event_dates " +
			"c where a.eventid=? and CAST(a.eventid AS BIGINT) = c.eventid and " +
			"b.eventdate = ? and a.tid=b.tid group by groupname, ticketname " +
			"having sum(case when (UPPER(coalesce(paymentstatus,'COMPLETED')) in " +
			"('COMPLETED','CHARGED','PENDING')) then ticketqty else 0 end)>0 order by groupname, ticketname";

	public static List<HashMap<String, String>> getEventTicketsStatusInfo(
			String eid) {		
		return getTicetsStatusInfo(EVENT_TICKETS_INFO,new String[]{eid});		
	}
	
	public static List<HashMap<String, String>> getEventTicketsStatusInfo(
			String eid,String eventdate) {
		/*String strdate = "";
		try{
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
			Date seldate = df.parse(eventdate);
			strdate = sdf.format(seldate);
			System.out.println("strdate: "+strdate);
		}catch (Exception e) {
		}*/
		return getTicetsStatusInfo(EVENT_TICKETS_INFO_BYDATE,new String[]{eid,eventdate});
	}
	
	public static List<HashMap<String, String>> getTicetsStatusInfo(String query,String[] inParams){
		List<HashMap<String, String>> eTicketsInfoList = new ArrayList<HashMap<String, String>>();
		try {			
			DBManager dbm = new DBManager();
			StatusObj stobj = dbm.executeSelectQuery(query,inParams);			
			int count = stobj.getCount();			
			for (int i = 0; i < count; i++) {
				HashMap<String, String> hmap = new HashMap<String, String>();
				hmap.put("sold_qty", dbm.getValue(i, "sold", ""));
				hmap.put("groupname", dbm.getValue(i, "groupname", ""));
				hmap.put("ticketname", dbm.getValue(i, "ticketname", ""));
				eTicketsInfoList.add(hmap);
			}
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return eTicketsInfoList;
	}
	
	public static List<EventData> getRSVPEventStatusInfo(String eid){
		List<EventData> RSVPCount = new ArrayList<EventData>(); 
		try {			
				RSVPCount=EventDB.getRSVPCounts(eid,"");
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return RSVPCount;
	}
	public static List<EventData> getRSVPEventStatusInfo(String eid,String eventDate){
		List<EventData> RSVPCount = new ArrayList<EventData>(); 
		try {			
				RSVPCount=EventDB.getRSVPCounts(eid,eventDate);
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return RSVPCount;
	}
	public static List<HashMap<String,String>> getEventTicketsInfo(Vector tktdata)
	{
	List<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
	for(int i=0;i<tktdata.size();i++)
	{
		HashMap hmap=(HashMap)tktdata.get(i);
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("ticketname",(String)hmap.get("ticket_name"));
		map.put("groupname",(String)hmap.get("groupname"));
		map.put("sold_qty",(String)hmap.get("sold_qty"));
		list.add(map);
	}
	return list;
	}
	
	
	
}
