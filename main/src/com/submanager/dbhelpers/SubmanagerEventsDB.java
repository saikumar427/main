package com.submanager.dbhelpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.event.beans.EventData;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.ShortUrlPattern;
import com.eventbee.general.StatusObj;

public class SubmanagerEventsDB {
	private static final Logger log = Logger.getLogger(SubmanagerEventsDB.class);
	
	public static ArrayList populateEventsForQuery(String query, String mgrId, int limitCount){
		ArrayList eventsList=new ArrayList();
try{
	
	DBManager dbmanager=new DBManager();
	if(limitCount>0)
		query+=" limit "+limitCount;
	StatusObj statobj=null;
			statobj=dbmanager.executeSelectQuery(query,new String []{mgrId});
			int count1=statobj.getCount();
			if(statobj.getStatus()&&count1>0){
				for(int k=0;k<count1;k++){
					EventData edata=new EventData();
					String currentlevel=(String)dbmanager.getValue(k,"current_level","");
					if("100".equals(currentlevel) || "200".equals(currentlevel) || "300".equals(currentlevel) || "400".equals(currentlevel) )
						edata.setPowertype("Ticketing");
					else
						edata.setPowertype("RSVP");
					
					
					edata.setEventName(dbmanager.getValue(k,"eventname",""));
					edata.setEventId(dbmanager.getValue(k,"eventid",""));
					String status=dbmanager.getValue(k,"status","");
					if("ACTIVE".equals(status))	status="Active";
					if("PENDING".equals(status)) status="Suspended";
					if("CLOSED".equals(status)) status="Closed";
					edata.setStatus(status);
					edata.setStarttime(dbmanager.getValue(k,"starttime",""));
					edata.setEndtime(dbmanager.getValue(k,"endtime",""));	
					edata.setMgrId(dbmanager.getValue(k,"mgr_id",""));
					edata.setStart_date(dbmanager.getValue(k,"start_date_formatted",""));
					edata.setEnd_date(dbmanager.getValue(k,"end_date_formatted",""));
					edata.setListed_date(dbmanager.getValue(k,"listed_date_formatted",""));
					DateFormat formatter ; 
			        Date date ; 
			        formatter = new SimpleDateFormat("yyyy-MM-dd");
			        date = (Date)formatter.parse(dbmanager.getValue(k, "start_date", ""));
			        edata.setFormattedStartDate(date);
			        date = (Date)formatter.parse(dbmanager.getValue(k, "end_date", ""));
			        edata.setFormattedEndDate(date);
			        date = (Date)formatter.parse(dbmanager.getValue(k, "created_at", ""));
			        edata.setFormattedListDate(date);
			        eventsList.add(edata);
				}
			}
			}//End of try block
			catch(Exception e){
				System.out.println("There is an Exception while executing activeevents query" +
						"for the mgr "+mgrId +" "+e.getMessage());
			}
		
		return eventsList;
	}
	public static ArrayList populateAllEvents(String mgrId, int limitCount){
		/*String query="select eventname, eventid,start_date, end_date,created_at,status," +
				"to_char(start_date, 'YYYY/MM/DD') as start_date_formatted," +
				"to_char(end_date, 'YYYY/MM/DD') as end_date_formatted," +
				"to_char(created_at, 'YYYY/MM/DD') as listed_date_formatted" +
				" from eventinfo where mgr_id in (select mgr_id from event_submanager where sub_mgr_id=?::BIGINT) " +
				" and status in ('ACTIVE','CLOSED','PENDING') order by created_at desc ";  */
		String query="select eventname, e.eventid, start_date, end_date, e.created_at, e.status, e.mgr_id," +
		"to_char(start_date, 'YYYY/MM/DD') as start_date_formatted," +
		"to_char(end_date, 'YYYY/MM/DD') as end_date_formatted," +
		"to_char(e.created_at, 'YYYY/MM/DD') as listed_date_formatted" +
		" from eventinfo  e,event_submanager es where es.sub_mgr_id=?::BIGINT and es.mgr_id=e.mgr_id and es.eventid=e.eventid" +
		" and e.status in ('ACTIVE','CLOSED','PENDING')  and es.status='Active' order by e.created_at desc"; 
        return populateEventsForQuery(query, mgrId,limitCount);		
	}
	public static ArrayList populateUpComingEvents(String mgrId, int limitCount){
		String query="select eventname, e.current_level, e.eventid, start_date,starttime, endtime,end_date, e.created_at, e.status, e.mgr_id," +
				"trim(to_char(start_date, 'Dy')) ||', '|| to_char(start_date, 'Mon DD, YYYY') as start_date_formatted," +
				"trim(to_char(end_date, 'Dy')) ||', '|| to_char(end_date, 'Mon DD, YYYY') as end_date_formatted," +
				"to_char(e.created_at, 'YYYY/MM/DD') as listed_date_formatted" +
				" from eventinfo  e,event_submanager es where es.sub_mgr_id=?::BIGINT and es.mgr_id=e.mgr_id and es.eventid=e.eventid" +
				" and e.status='ACTIVE' and es.status='Active' order by e.created_at desc";        
        return populateEventsForQuery(query, mgrId,limitCount);		
	}
	public static ArrayList populateRecentClosedEvents(String mgrId, int limitCount){
		String query="select eventname, e.current_level, e.eventid, start_date,starttime,endtime,end_date, e.created_at, e.status, e.mgr_id," +
		"trim(to_char(start_date, 'Dy')) ||', '|| to_char(start_date, 'Mon DD, YYYY') as start_date_formatted," +
		"trim(to_char(end_date, 'Dy')) ||', '|| to_char(end_date, 'Mon DD, YYYY') as end_date_formatted," +
		"to_char(e.created_at, 'YYYY/MM/DD') as listed_date_formatted" +
		" from eventinfo  e,event_submanager es where es.sub_mgr_id=?::BIGINT and es.mgr_id=e.mgr_id and es.eventid=e.eventid" +
		" and e.status='CLOSED'  and es.status='Active' order by end_date desc ";
		return populateEventsForQuery(query, mgrId,limitCount);		
	}
	public static ArrayList populateSuspendedEvents(String mgrId, int limitCount){
		String query="select eventname, e.current_level, e.eventid, start_date,starttime, endtime, end_date, e.created_at, e.status, e.mgr_id," +
		"trim(to_char(start_date, 'Dy')) ||', '|| to_char(start_date, 'Mon DD, YYYY') as start_date_formatted," +
		"trim(to_char(end_date, 'Dy')) ||', '|| to_char(end_date, 'Mon DD, YYYY') as end_date_formatted," +
		"to_char(e.created_at, 'YYYY/MM/DD') as listed_date_formatted" +
		" from eventinfo  e,event_submanager es where es.sub_mgr_id=?::BIGINT and es.mgr_id=e.mgr_id and es.eventid=e.eventid" +
		" and e.status='PENDING'  and es.status='Active' order by e.created_at desc ";
		return populateEventsForQuery(query, mgrId,limitCount);		
	}
	
	
	public static EventData getLastListedEvent(String mgrId){
		
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		EventData edata=null;
		String query="select eventname, e.eventid from eventinfo  e,event_submanager es where es.sub_mgr_id=?::BIGINT and es.mgr_id=e.mgr_id and es.eventid=e.eventid  and es.status='Active' order by e.created_at desc limit 1";
		try{			
			statobj=dbmanager.executeSelectQuery(query,new String []{mgrId});
			int count1=statobj.getCount();
			if(statobj.getStatus()&&count1>0){				
					edata=new EventData();
					edata.setEventName(dbmanager.getValue(0,"eventname",""));
					edata.setEventId(dbmanager.getValue(0,"eventid",""));		
			}
			}//End of try block
			catch(Exception e){
				System.out.println("There is an Exception while executing latest event query" +
						"for the mgr "+mgrId +" "+e.getMessage());
			}
		
		return edata;
	}
	
	
	public static ArrayList getGroupEvents(String mgrId){
		ArrayList groupEvents=new ArrayList();
		return groupEvents;
	}
	public static void putEventsCount(String mgrId, HashMap eventsSummary){
		eventsSummary.put("ACTIVE_EVENTS_COUNT", "0");
		eventsSummary.put("CLOSED_EVENTS_COUNT", "0");
		eventsSummary.put("SUSPENDED_EVENTS_COUNT", "0");
		eventsSummary.put("ALL_EVENTS_COUNT", "0");
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String query="select e.status, count(*) as statuscount from eventinfo  e,event_submanager es where es.sub_mgr_id=?::BIGINT and es.mgr_id=e.mgr_id and es.eventid=e.eventid  and es.status='Active' group by e.status";
		try{
			
			
			statobj=dbmanager.executeSelectQuery(query,new String []{mgrId});
			int count1=statobj.getCount();
			if(statobj.getStatus()&&count1>0){
				for(int k=0;k<count1;k++){
					if("ACTIVE".equalsIgnoreCase(dbmanager.getValue(k,"status","")))
						eventsSummary.put("ACTIVE_EVENTS_COUNT", dbmanager.getValue(k,"statuscount",""));
					if("CLOSED".equalsIgnoreCase(dbmanager.getValue(k,"status","")))
						eventsSummary.put("CLOSED_EVENTS_COUNT", dbmanager.getValue(k,"statuscount",""));
					if("PENDING".equalsIgnoreCase(dbmanager.getValue(k,"status","")))
						eventsSummary.put("SUSPENDED_EVENTS_COUNT", dbmanager.getValue(k,"statuscount",""));
				}
			}
			int active=Integer.parseInt(eventsSummary.get("ACTIVE_EVENTS_COUNT").toString());
			int closed=Integer.parseInt(eventsSummary.get("CLOSED_EVENTS_COUNT").toString());
			int suspended=Integer.parseInt(eventsSummary.get("SUSPENDED_EVENTS_COUNT").toString());
			eventsSummary.put("ALL_EVENTS_COUNT", ""+(active+closed+suspended));
			}//End of try block
			catch(Exception e){
				System.out.println("There is an Exception while executing activeevents query" +
						"for the mgr "+mgrId +" "+e.getMessage());
			}
		
		
	}
	public static HashMap<String, String> populateMyEventsSummary(String mgrId){
		/*
		select status, count(*) from eventinfo where mgr_id=?;
		*/
		HashMap<String, String> eventsSummary=new HashMap<String, String>();
		putEventsCount(mgrId, eventsSummary);
		EventData edata=getLastListedEvent(mgrId);
		if(edata!=null){
		eventsSummary.put("LAST_EVENT_EXISTS", "Y");		
		eventsSummary.put("LAST_EVENT_NAME", edata.getEventName());
		eventsSummary.put("LAST_EVENT_ID", edata.getEventId());
		}else{
			eventsSummary.put("LAST_EVENT_EXISTS", "N");
		}
		return eventsSummary;
	}
	public static List<Entity> getAllEvents(String mgrId){
		List<Entity> list = new ArrayList<Entity>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String Query = "select eventname,eventid from eventinfo where mgr_id = to_number(?,'9999999999') and status!='CANCEL' order by created_at desc";			
		try{
				statobj=dbmanager.executeSelectQuery(Query,new String []{mgrId});
				int count=statobj.getCount();
				if(statobj.getStatus()&&count>0){
					for(int k=0;k<count;k++){						
						list.add(new Entity(dbmanager.getValue(k,"eventid",""),dbmanager.getValue(k,"eventname","")));						
					}
				}
		}//End of try block
		catch(Exception ex){
			log.error("Exception: "+ex);
		}	
		return list;
	}
	
	public static String getEventsPageURL(String mgrId){
		String userName=DbUtil.getVal("select getMemberPref(?||'','pref:myurl','') as username",new String[]{mgrId});
		String url=ShortUrlPattern.get(userName)+"/boxoffice";
		return url;

	}
}


