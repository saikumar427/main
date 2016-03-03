package com.myevents.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.ShortUrlPattern;
import com.eventbee.general.StatusObj;
import com.myevents.beans.BoxOfficeData;

public class BoxOfficeDB {
	
	/*public static final String ALL_EVENTS="select eventid,eventname from eventinfo where mgr_id=to_number(?,'9999999999') and status='ACTIVE' and" +
	" end_date>=now() and eventid not in(select to_number(eventid,'99999999999999') from box_office_events where boxoffice_id=" +
	"(select boxoffice_id  from box_office_master where userid=?)) order by start_date";*/
	
	public static final String NON_GROUP_EVENTS="select eventid,eventname from eventinfo where mgr_id=to_number(?,'9999999999') and status='ACTIVE' " +
	"and end_date>=now() and listtype='PBL' and eventid not in(select CAST(eventid AS BIGINT) from box_office_events where " +
	"boxoffice_id=(select boxoffice_id  from box_office_master where userid=?))and eventid not in(select CAST(eventid AS BIGINT) " +
	"from group_events where event_groupid in(select event_groupid from user_groupevents where userid=?))order by start_date";
	
	public static final String GROUP_EVENTS="select distinct a.event_groupid as eventgroupid,a.group_title as grouptitle from " +
	"user_groupevents a,group_events b,eventinfo c where a.event_groupid=b.event_groupid and CAST(b.eventid AS BIGINT)=c.eventid " +
	"and to_number(a.userid,'99999999999999')=c.mgr_id and c.status='ACTIVE' and c.end_date>=now() and c.listtype='PBL' and a.userid=? and " +
	"a.event_groupid not in(select eventid from box_office_events where boxoffice_id=(select boxoffice_id  from box_office_master where userid=?))";
	
	public static final String BOX_OFFICE_DETAILS="select boxoffice_id,description,header from box_office_master where userid=?";
	public static final String INSERT_BOX_OFFICE_DETAILS="insert into box_office_master(boxoffice_id,userid,description,header,photo_url,status," +
	"display_order,events_display_type) values(?,?,?,?,?,?,?,?)";
	public static final String UPDATE_BOX_OFFICE_DETAILS="update box_office_master set description=?,header=?,photo_url=?,status=?," +
	"display_order=?,events_display_type=? where boxoffice_id=?";
	
	public static final String SEL_EVENTS="select a.eventid,a.eventname,b.position from eventinfo a,box_office_events b where " +
	"a.eventid=CAST(b.eventid AS BIGINT) and b.boxoffice_id=(select boxoffice_id from " +
	"box_office_master where userid=?) order by b.position";
	
	public static final String SEL_GROUP_EVENTS="select distinct a.event_groupid as groupeventid,a.group_title as grouptitle, c.position from user_groupevents a, " +
	"group_events b, box_office_events c where a.userid=? and a.event_groupid=b.event_groupid and c.eventid=b.event_groupid order by c.position";
	
	public static final String DELETE_EVENTS="delete from box_office_events where boxoffice_id=?";
	public static final String INSERT_EVENTS="insert into box_office_events(boxoffice_id,eventid,position) values(?,?,to_number(?,'999999999999'))";
	
	public static ArrayList<Entity>  getAllEvents(String mgrId){
		ArrayList<Entity> allEvents=new ArrayList<Entity>();
		DBManager db=new DBManager();
		StatusObj stobj=db.executeSelectQuery(NON_GROUP_EVENTS, new String[]{mgrId,mgrId,mgrId});
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++)
			{
				allEvents.add(new Entity(db.getValue(i, "eventid", ""),db.getValue(i, "eventname", "")));
			}
		}
		DBManager db2=new DBManager();
		StatusObj stobj2=db2.executeSelectQuery(GROUP_EVENTS, new String[]{mgrId,mgrId});
		if(stobj2.getStatus()){
			for(int i=0;i<stobj2.getCount();i++)
			{
				allEvents.add(new Entity(db2.getValue(i, "eventgroupid", ""),db2.getValue(i, "grouptitle", "")));
			}
		}
		return allEvents;
	}
	public static BoxOfficeData getBoxOfficeDetails(String mgrId){
		BoxOfficeData boxoffice=new BoxOfficeData();
		DBManager db=new DBManager();
		StatusObj stobj=db.executeSelectQuery(BOX_OFFICE_DETAILS, new String[]{mgrId});
		if(stobj.getStatus()){
			boxoffice.setBoxoffice_id(db.getValue(0, "boxoffice_id", ""));
			boxoffice.setDescription(db.getValue(0, "description", ""));
			boxoffice.setTitle(db.getValue(0, "header", ""));
			/*String status=db.getValue(0, "status", "");
			if(status==null || "N".equals(status) || "".equals(status))
				boxoffice.setEnabled(false);
			else if("Y".equals(status))
				boxoffice.setEnabled(true);
			boxoffice.setPhotourl(db.getValue(0, "photo_url", ""));
			String display_type=db.getValue(0,"events_display_type", "");
			if(display_type==null || "".equals(display_type))
				display_type="ALL";
			boxoffice.setEventsdisplaytype(display_type);
			boxoffice.setDisplayorder(db.getValue(0, "display_order", ""));*/
		}
		return boxoffice;
	}
	public static void saveBoxOfficeDetails(BoxOfficeData boxoffice,ArrayList<Entity> selectedEvents,String mgrId){
		String boxoffice_id=DbUtil.getVal("select boxoffice_id from box_office_master where userid=?", new String[]{mgrId});
		if(boxoffice_id==null || "".equals(boxoffice_id)){
			boxoffice_id=DbUtil.getVal("select nextval('seq_boxoffice_id')",null);
			String status=(boxoffice.isEnabled())?"Y":"N";
			DbUtil.executeUpdateQuery(INSERT_BOX_OFFICE_DETAILS, new String[]{boxoffice_id,mgrId,boxoffice.getDescription(),boxoffice.getTitle(),boxoffice.getPhotourl(),status,boxoffice.getDisplayorder(),boxoffice.getEventsdisplaytype()});
			if(boxoffice.getEventsdisplaytype().equals("SELECTED") && boxoffice.isEnabled())
			updateBoxOfficeEvents(boxoffice_id,selectedEvents);
		}else{
			String status=(boxoffice.isEnabled())?"Y":"N";
			DbUtil.executeUpdateQuery(UPDATE_BOX_OFFICE_DETAILS, new String[]{boxoffice.getDescription(),boxoffice.getTitle(),boxoffice.getPhotourl(),status,boxoffice.getDisplayorder(),boxoffice.getEventsdisplaytype(),boxoffice_id});
			if(boxoffice.getEventsdisplaytype().equals("SELECTED") && boxoffice.isEnabled())
			updateBoxOfficeEvents(boxoffice_id, selectedEvents);
		}
		
	}
	public static void updateBoxOfficeEvents(String boxoffice_id,ArrayList<Entity> selectedEvents){
		if(selectedEvents.size()>0){
			DbUtil.executeUpdateQuery(DELETE_EVENTS, new String[]{boxoffice_id});
			for(int i=0;i<selectedEvents.size();i++){
				if(!"".equals(selectedEvents.get(i).getKey()))
				DbUtil.executeUpdateQuery(INSERT_EVENTS, new String[]{boxoffice_id,selectedEvents.get(i).getKey(),""+(i+1)});
			}
		}
	}
	public static ArrayList<Entity> getSelEvents(String mgrId){
		HashMap<String, Entity> hsmap = new HashMap<String, Entity>();
		ArrayList<Entity> selEvents=new ArrayList<Entity>();
		DBManager db=new DBManager();
		StatusObj stobj=db.executeSelectQuery(SEL_EVENTS, new String[]{mgrId});
		if(stobj.getStatus())
			for(int i=0;i<stobj.getCount();i++)
				hsmap.put(db.getValue(i, "position", ""), new Entity(db.getValue(i, "eventid", ""),db.getValue(i, "eventname", "")));
		
		
		DBManager db2=new DBManager();
		StatusObj stobj2=db2.executeSelectQuery(SEL_GROUP_EVENTS, new String[]{mgrId});
		if(stobj2.getStatus())
			for(int i=0;i<stobj2.getCount();i++)
				hsmap.put(db2.getValue(i, "position", ""), new Entity(db2.getValue(i, "groupeventid", ""),db2.getValue(i, "grouptitle", "")));
		
		if(hsmap.size()>0){
			for(int position=1;position<=hsmap.size();position++){
				Entity entity =  new Entity();
				entity = hsmap.get(""+position);
				selEvents.add(entity);
			}
		}
		return selEvents;
	}
	public static String getShortURL(String mgrId){
		String userName=DbUtil.getVal("select getMemberPref(?||'','pref:myurl','') as username",new String[]{mgrId});
		String url=ShortUrlPattern.get(userName);
		return url;
	}
	
	public static void saveBoxOfficeCustomization(BoxOfficeData boxoffice, String mgrId){
		String boxoffice_id=DbUtil.getVal("select boxoffice_id from box_office_master where userid=?", new String[]{mgrId});
		if(boxoffice_id==null || "".equals(boxoffice_id)){
			boxoffice_id=DbUtil.getVal("select nextval('seq_boxoffice_id')",null);
			String INSERT_BOX_OFFICE_DESCRIPTION="insert into box_office_master(boxoffice_id,userid,description,header) values(?,?,?,?)";
			String DELETE_DUPLICATE_BOX_OFFICE="delete from box_office_master where userid=?";
			DbUtil.executeUpdateQuery(DELETE_DUPLICATE_BOX_OFFICE, new String[]{mgrId});
			DbUtil.executeUpdateQuery(INSERT_BOX_OFFICE_DESCRIPTION, new String[]{boxoffice_id,mgrId,boxoffice.getDescription(),boxoffice.getTitle()});
			
		}else{
			String UPDATE_BOX_OFFICE_DESCRIPTION="update box_office_master set description=?,header=? where boxoffice_id=?";
			DbUtil.executeUpdateQuery(UPDATE_BOX_OFFICE_DESCRIPTION, new String[]{boxoffice.getDescription(),boxoffice.getTitle(),boxoffice_id});
			
		}
	}
	
	public static boolean checkFBInstalled(String mgrid){
	String isexists=DbUtil.getVal("select 'yes' from fb_page_owners where mgr_id=?::bigint", new String[]{mgrid});	
	if("yes".equals(isexists))
		return true;
	else
		return false;
	}
	
	public static BoxOfficeData getFBDetails(String mgrid,BoxOfficeData bdata){
		String query="select * from fb_page_owners where mgr_id=?::bigint limit 1;";
		DBManager dbm=new DBManager();
		StatusObj sbj;
		sbj=dbm.executeSelectQuery(query,new String[]{mgrid});
		if(sbj.getStatus() && sbj.getCount()>0){
			bdata.setFbbuybutton(dbm.getValue(0,"buyticketbutton","Buy Tickets"));
			bdata.setFbdescription(dbm.getValue(0,"fbdescription",""));
			bdata.setFbnoevtsmsg(dbm.getValue(0,"message","No upcoming events available!"));
			bdata.setFbpagination(dbm.getValue(0,"paginationrequired","yes"));
			bdata.setFbpaginationsize(dbm.getValue(0,"totalpages","3"));
			bdata.setFbtitle(dbm.getValue(0,"title","Upcoming Events"));
		}
	return bdata;
	}
	
	
	public static void saveFBSettings(String mgrId,BoxOfficeData bdata){
		String count="3";
		if("yes".equals(bdata.getFbpagination().trim()))
			count=bdata.getFbpaginationsize();
		
		String query="update fb_page_owners set title=?,fbdescription=?,paginationrequired=?,totalpages=?,buyticketbutton=?,message=? where mgr_id=?::bigint";
		DbUtil.executeUpdateQuery(query, new String[]{
				bdata.getFbtitle(),bdata.getFbdescription(),bdata.getFbpagination(),bdata.getFbpaginationsize(),bdata.getFbbuybutton(),bdata.getFbnoevtsmsg(),mgrId
		});
		
	}
	
}
