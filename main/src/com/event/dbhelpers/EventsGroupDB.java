package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.beans.Entity;
import com.myevents.beans.EventsGroup;

public class EventsGroupDB {
	public static final Logger log = Logger.getLogger(EventsGroupDB.class);
	
	public static List<HashMap<String,String>> getEventGroups(String userid){
		List<HashMap<String,String>> eventgroupsvector= new ArrayList<HashMap<String,String>>();
		DBManager dbm =new DBManager();
		HashMap<String,String> groupdetails= null;
		HashMap hmap = getEventsCountOfGroups(userid);
		String Query="select group_title,event_groupid from user_groupevents where userid=? order by created_at desc";
		StatusObj statobj=dbm.executeSelectQuery(Query,new String[] {userid});
		if(statobj.getStatus())	{
			for(int k=0;k<statobj.getCount();k++){				
				groupdetails =new HashMap<String,String>();			
				groupdetails.put("group_title",dbm.getValue(k,"group_title",""));
				String grpid = dbm.getValue(k,"event_groupid","");
				groupdetails.put("event_groupid",grpid);
				String count = (String) hmap.get(grpid);
				groupdetails.put("eventscount",count!=null ? count : "0");
				eventgroupsvector.add(groupdetails);
			}

		}
		
		return eventgroupsvector;
	}
	public static List<HashMap<String,String>> getEventThemes(String userid){
		
		List<HashMap<String,String>> eventthemesvector= new ArrayList<HashMap<String,String>>();
		DBManager dbm =new DBManager();

		
		String Query="select themeid,themename from user_customized_themes  where userid=? and module='event' order by created_at desc";
		StatusObj statobj=dbm.executeSelectQuery(Query,new String[] {userid});		
		if(statobj.getStatus())	{
			String [] columnnames=dbm.getColumnNames();			
			
			try {				
				for (int k = 0; k < statobj.getCount(); k++) {
					HashMap<String,String> themedetails= new HashMap<String, String>();					
					for (int j = 0; j < columnnames.length; j++) {
						themedetails.put(columnnames[j], dbm.getValue(k,columnnames[j], ""));
					}					
					eventthemesvector.add(themedetails);
				}
			} catch (Exception e) {
				System.out.println("exception occured::::::::" + e.getMessage());
			}

		}
		
		return eventthemesvector;
	}

	private static HashMap getEventsCountOfGroups(String uid){
		HashMap groupdetails = new HashMap();
		String Query = "select event_groupid,count(*) as eventcount from group_events where event_groupid " +
				"in (select event_groupid from user_groupevents where userid=?) group by event_groupid";
		DBManager dbm =new DBManager();
		try {
			StatusObj statobj=dbm.executeSelectQuery(Query,new String[] {uid});
			if(statobj.getStatus())	{
				for(int k=0;k<statobj.getCount();k++){		
					groupdetails.put(dbm.getValue(k,"event_groupid",""),dbm.getValue(k,"eventcount",""));
				}

			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return groupdetails;
	}
	
	private static Vector<HashMap<String,String>> getDBActiveEvents(String Query,String[] params){
		Vector<HashMap<String,String>> activeeventsvector= new Vector<HashMap<String,String>>();
		DBManager dbm =new DBManager();
		HashMap<String,String> activeevents= null;	 
		StatusObj statobj=dbm.executeSelectQuery(Query,params);
		if(statobj.getStatus())	{
			for(int k=0;k<statobj.getCount();k++){
				activeevents =new HashMap<String,String>();
				activeevents.put("eventname",dbm.getValue(k,"eventname",""));
				activeevents.put("status",dbm.getValue(k,"status",""));
				activeevents.put("eventid",dbm.getValue(k,"eventid",""));
				activeeventsvector.add(activeevents);
			}

		}
		else 
			System.out.println("statusobject status is:"+statobj.getStatus());
		return activeeventsvector;
	}
	public static void deleteEventGroup(String eventGroupId){
		String DELETE_USERGROUP="delete from user_groupevents where event_groupid=? ";
		String DELETEQUERY_USERGROUPEVENTS="delete from group_events where event_groupid=? ";
		DbUtil.executeUpdateQuery(DELETE_USERGROUP,new String[]{eventGroupId});
		DbUtil.executeUpdateQuery(DELETEQUERY_USERGROUPEVENTS,new String[]{eventGroupId});
	}
	public static Vector<HashMap<String,String>> getDBGroupEvents(String event_groupid){
		Vector<HashMap<String,String>> groupeventsvector= new Vector<HashMap<String,String>>();
		DBManager dbm =new DBManager();
		HashMap<String,String> eventdetails= null;		
		String Query="select a.eventid,b.eventname,b.mgr_id from group_events a, eventinfo b "+
"where a.event_groupid=? and b.eventid=CAST(a.eventid AS BIGINT) order by position";
		StatusObj statobj=dbm.executeSelectQuery(Query,new String[] {event_groupid});
		if(statobj.getStatus())	{
			for(int k=0;k<statobj.getCount();k++){
				eventdetails =new HashMap<String,String>();			
				eventdetails.put("eventid",dbm.getValue(k,"eventid",""));
				eventdetails.put("eventname",dbm.getValue(k,"eventname",""));
				eventdetails.put("mgrid",dbm.getValue(k,"mgr_id",""));
				groupeventsvector.add(eventdetails);
			}
		}
		else 
			System.out.println("statusobject status isssss:"+statobj.getStatus());
		return groupeventsvector;
	}
	
	public static List<Entity> getActiveEvents(String purpose,String uid,EventsGroup grpevents){
		List<Entity> list = new ArrayList<Entity>();
		try {
			String Query="";
			String[] params;
			if(purpose.equals("edit")){
				String title=DbUtil.getVal("select group_title from user_groupevents where event_groupid=?",new String[]{grpevents.getEventgroupid()});
				grpevents.setTitle(title);
				Query="select eventname,status,eventid from  eventinfo where mgr_id=to_number(?,'9999999999') and eventid not in "+
				"(select CAST(eventid AS BIGINT) from group_events where event_groupid=?) and upper(status)='ACTIVE' order by created_at desc";
				params = new String[]{uid,grpevents.getEventgroupid()};
			}else{
				Query="select eventname,status," +
				"eventid from  eventinfo where mgr_id=to_number(?,'9999999999999') and " +
				"upper(status)='ACTIVE' order by created_at desc";
				params = new String[]{uid};
			}
			
			Vector<HashMap<String,String>> actevents = getDBActiveEvents(Query,params);
			for(int i=0;i<actevents.size();i++){
				list.add(new Entity(actevents.get(i).get("eventid"),actevents.get(i).get("eventname")));
			}
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception:"+ex);
		}
		grpevents.setEvents(list);
		return list;
	}
	public static EventsGroup getEventsGroupData(String gid, String mgrId){
		String title=DbUtil.getVal("select group_title from user_groupevents where event_groupid=?",new String[]{gid});
		String userName=DbUtil.getVal("select getMemberPref(?||'','pref:myurl','') as username",new String[]{mgrId});
		
		EventsGroup eventGroupData=new EventsGroup();
		eventGroupData.setTitle(title);
		eventGroupData.setUserName(userName);
		return eventGroupData;
	}
	public static List<Entity> getGroupEvents(EventsGroup grouppevents){
		List<Entity> list = new ArrayList<Entity>();
		try {
			Vector<HashMap<String,String>> grpevents = getDBGroupEvents(grouppevents.getEventgroupid());
			for(int i=0;i<grpevents.size();i++){
				list.add(new Entity(grpevents.get(i).get("eventid"),grpevents.get(i).get("eventname")));
			}
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception:"+ex);
		}
		grouppevents.setGroupEvents(list);
		return list;
	}
	/*public List<Entity> getMyEventGroups(String uid){
		List<Entity> list = new ArrayList<Entity>();
		try {
			Vector<HashMap<String,String>> eventsgroups = getEventGroups(uid);
			for(int i=0;i<eventsgroups.size();i++){
				list.add(new Entity(eventsgroups.get(i).get("event_groupid"),eventsgroups.get(i).get("group_title")));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}*/
	public static void saveEventsGroup(String uid,String purpose,EventsGroup grouppevents){
		try {	
			String neweventgroupid = "select nextval('seq_groupid') as newgrpid";
			String DELETEQUERY="delete from group_events where event_groupid=? ";
			String UPDATEQUERY="update user_groupevents set group_title=?, created_at = now() where event_groupid=?";
			
			String INSERTGEVENTS = "insert into user_groupevents(userid,event_groupid, group_title,created_at) values(?,?,?,now())";
			String INSERTQ="insert into group_events(event_groupid,eventid, position)values(?,?,?)";
			System.out.print("purpose:"+purpose);
			System.out.print("uid:"+uid);
			System.out.print("title:"+grouppevents.getTitle());
			System.out.print("groupEvents:"+grouppevents.getGroupEvents().size());
			System.out.print("event_groupid:"+grouppevents.getEventgroupid());
			
			if(purpose.equals("edit")){
				DbUtil.executeUpdateQuery(DELETEQUERY,new String[]{grouppevents.getEventgroupid()});
				DbUtil.executeUpdateQuery(UPDATEQUERY,new String[]{grouppevents.getTitle(),grouppevents.getEventgroupid()});
			}else{
				grouppevents.setEventgroupid(DbUtil.getVal(neweventgroupid, null));
				DbUtil.executeUpdateQuery(INSERTGEVENTS,new String[]{uid,grouppevents.getEventgroupid(),grouppevents.getTitle()});				
			}
			if(grouppevents.getOptionsList()!=null){
				for(int i=0;i<grouppevents.getOptionsList().size();i++){
					int position = i+1;
					DbUtil.executeUpdateQuery(INSERTQ,new String[]{grouppevents.getEventgroupid(),grouppevents.getOptionsList().get(i).getKey(),""+position});
				}
			}
			
			String DELETE_BOXOFFICE_EVENTS = "delete from box_office_events where eventid in(select eventid from group_events where event_groupid=?) and " +
					"boxoffice_id=(select boxoffice_id from box_office_master where userid=?)";
			DbUtil.executeUpdateQuery(DELETE_BOXOFFICE_EVENTS,new String[]{grouppevents.getEventgroupid(),uid});
			
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception:"+ex);
		}
	}
	
}
