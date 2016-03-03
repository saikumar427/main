package com.myevents.dbhelpers;

import java.util.*;

import org.apache.log4j.Logger;
import com.myevents.beans.TrackingPartnerData;
import com.event.beans.ImportTrackUrlData;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.StatusObj;
import com.eventbee.namedquery.NQDbHelper;
import com.eventbee.namedquery.NQDbUtil;

public class TrackingPartnerDB {
	private static Logger log = Logger.getLogger(TrackingPartnerDB.class);

	public static ArrayList<TrackingPartnerData> getTrackingPartnersList(
			String mgrid) {
		ArrayList<TrackingPartnerData> trackingPartnersList = new ArrayList<TrackingPartnerData>();
		DBManager db = new DBManager();
		String query = "select trackname,eventscount,status,trackid from accountlevel_track_partners where userid=to_number(?,'99999999999') order by trackid desc";
		StatusObj statobj = db
				.executeSelectQuery(query, new String[] { mgrid });
	  int count = statobj.getCount();
		if (statobj.getStatus() && count > 0) {
			for (int k = 0; k < count; k++) {
				TrackingPartnerData obj = new TrackingPartnerData();
				obj.setTrackname(db.getValue(k, "trackname", ""));
				obj.setEventscount(db.getValue(k, "eventscount", ""));
				obj.setTrackid(db.getValue(k, "trackid", ""));
				trackingPartnersList.add(obj);
			}
		}
		return trackingPartnersList;
	}

	public static String saveTrackingPartnerData(
			TrackingPartnerData trackingPartnerData, String mgrid) {

		String trackname = trackingPartnerData.getTrackname();
		String trackid = DbUtil.getVal("select nextval('trackingid')",
				new String[] {});
		String scode = EncodeNum.encodeNum(trackid);

		log.info("Inserting... tracking partner data");
		String query = "insert into accountlevel_track_partners"
				+ " (userid,trackid,trackname,password,photourl,eventscount,message,status,scode) "
				+ " values(to_number(?,'9999999999'),to_number(?,'9999999999'),?,?,?,0,?,'Approved',?) ";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[] {
				mgrid, trackid, trackname, trackname, "", "", scode });
		log.info("Account level track partner Insert Status: "
				+ statobj.getStatus());
		return trackid;
	}

	public static ArrayList<TrackingPartnerData> getIndividualTrackingPartnerData(
			String mgrid, String trackid) {
		ArrayList indivualTrackingpartnerList = new ArrayList();
		DBManager db = new DBManager();
		StatusObj statobj = null;
		TrackingPartnerData tempTrackingPartnerData = new TrackingPartnerData();
		String query = "select * from accountlevel_track_partners where userid=to_number(?,'9999999999')"
				+ " and trackid=to_number(?,'9999999999')";
		statobj = db.executeSelectQuery(query, new String[] { mgrid, trackid });
		int count = statobj.getCount();
	    if (statobj.getStatus() && count > 0) {
			for (int k = 0; k < count; k++) {

				tempTrackingPartnerData.setMgrId(db.getValue(k, "userid", ""));
				tempTrackingPartnerData.setTrackid(db
						.getValue(k, "trackid", ""));
				tempTrackingPartnerData.setEventscount(db.getValue(k,
						"eventscount", ""));
				tempTrackingPartnerData.setStatus(db.getValue(k, "status", ""));
				tempTrackingPartnerData.setTrackname(db.getValue(k,
						"trackName", ""));
				tempTrackingPartnerData.setPassword(db.getValue(k, "password",
						""));
				tempTrackingPartnerData.setMessage(db
						.getValue(k, "message", ""));
				tempTrackingPartnerData.setPhotoURL(db.getValue(k, "photourl",
						""));
				indivualTrackingpartnerList.add(tempTrackingPartnerData);

			}

		}
		return indivualTrackingpartnerList;
	}

	public static ArrayList<TrackingPartnerData> importMyOtherEventsData(
			String mgrid, String trackid) {
		ArrayList eventsList = new ArrayList();
		DBManager db = new DBManager();
		StatusObj statobj = null;
		String trackcode=DbUtil.getVal("select trackname from accountlevel_track_partners where trackid=CAST(? as integer)", new String[]{trackid});
		String query = "select eventname,eventid from eventinfo where mgr_id=to_number(?,'99999999')"
				+ " and status='ACTIVE'  and  eventid not in(select CAST(eventid AS BIGINT)"
				+ " from trackurls where trackingcode=?)" ;
				//+ " and eventid in(select CAST(eventid as int) from event_custom_urls )";
		statobj = db.executeSelectQuery(query, new String[] {mgrid,trackcode});
		log.info("AccountlevelTrackPartner Other Events Status: "
				+ statobj.getStatus());
		int count = statobj.getCount();
		if (statobj.getStatus() && count > 0) {
			for (int k = 0; k < count; k++) {
				TrackingPartnerData tempEventsData = new TrackingPartnerData();
				tempEventsData.setEventName(db.getValue(k, "eventname", ""));
				tempEventsData.setEid(db.getValue(k, "eventid", ""));
				eventsList.add(tempEventsData);

			}

		}
		return eventsList;

	}

	public static void deleteTrackingPartnerData(String mgrid, String trackid,
			boolean checked) {
		StatusObj statobj = null;
		String query = " delete from accountlevel_track_partners where userid=to_number(?,'99999999999') "
				+ "and trackid=to_number(?,'99999999999')";
		statobj = DbUtil.executeUpdateQuery(query, new String[] { mgrid,
				trackid });
		
		if (checked) {

			DbUtil.executeUpdateQuery(
					"update trackurls set status='Suspended' where "
							+ " trackingid=to_number(?,'999999999999')",
					new String[] { trackid });
		}

	}

	public static void changeTrackingPartnerMessage(String message,
			String mgrId, String trackid, boolean checked) {
		DbUtil
				.executeUpdateQuery(
						"update accountlevel_track_partners set message=? where  userid=to_number(?,'99999999999') "
								+ " and trackid=to_number(?,'999999999999')",
						new String[] { message, mgrId, trackid });
		if (checked) {
			DbUtil.executeUpdateQuery("update trackurls set message=? where "
					+ " trackingid=to_number(?,'999999999999')", new String[] {
					message, trackid });
		}
	}

	public static void changeTrackingPartnerStatus(String status, String mgrId,
			String trackid, boolean checked) {
		DbUtil
				.executeUpdateQuery(
						"update accountlevel_track_partners set status=? where  userid=to_number(?,'99999999999') "
								+ " and trackid=to_number(?,'999999999999')",
						new String[] { status, mgrId, trackid });
		if (checked) {
			DbUtil.executeUpdateQuery("update trackurls set status=? where "
					+ " trackingid=to_number(?,'999999999999')", new String[] {
					status, trackid });
		}
	}

	public static void changeTrackingPartnerPhotoURL(String photoURL,
			String mgrId, String trackid, boolean checked) {
		DbUtil
				.executeUpdateQuery(
						"update accountlevel_track_partners set photourl=? where  userid=to_number(?,'99999999999') "
								+ " and trackid=to_number(?,'999999999999')",
						new String[] { photoURL, mgrId, trackid });
		if (checked) {
			DbUtil.executeUpdateQuery("update trackurls set photo=? where "
					+ " trackingid=to_number(?,'999999999999')", new String[] {
					photoURL, trackid });
		}
	}

	public static void changeTrackingPartnerPassword(String password,
			String mgrId, String trackid, boolean checked) {
		DbUtil
				.executeUpdateQuery(
						"update accountlevel_track_partners set password=? where  userid=to_number(?,'99999999999') "
								+ " and trackid=to_number(?,'999999999999')",
						new String[] { password, mgrId, trackid });
		if (checked) {
			DbUtil.executeUpdateQuery("update trackurls set password=? where "
					+ " trackingid=to_number(?,'999999999999')", new String[] {
					password, trackid });
		}
	}
	public static ArrayList<TrackingPartnerData> getTrackURLList(String trackid){
		return getTrackURLList(trackid,"");
	}
		

	public static ArrayList<TrackingPartnerData> getTrackURLList(String trackid,String mgrId){
		ArrayList<TrackingPartnerData> list = new ArrayList<TrackingPartnerData>();
		DBManager db = new DBManager();
		String trackcode=DbUtil.getVal("select trackname from accountlevel_track_partners where trackid=CAST(? as integer)", new String[]{trackid});
		String query = "select  a.eventname,b.status,b.eventid from eventinfo a,trackurls b "
				+ " where a.eventid=CAST(b.eventid AS BIGINT)"
				+ " and b.trackingcode=? and a.mgr_id=cast(? as integer) order by eventname";
		StatusObj statobj = db.executeSelectQuery(query,
				new String[] { trackcode,mgrId });
				int count = statobj.getCount();
		if (statobj.getStatus() && count > 0) {
			for (int k = 0; k < count; k++) {
				TrackingPartnerData trackPartnerDataObj = new TrackingPartnerData();
				trackPartnerDataObj.setEventName(db
						.getValue(k, "eventname", ""));
				trackPartnerDataObj.setStatus(db.getValue(k, "status", ""));
				trackPartnerDataObj.setEid(db.getValue(k, "eventid", ""));
				list.add(trackPartnerDataObj);
			}
		}
		return list;
	}
	
	public static TrackingPartnerData getTrackingPartnerData(String  trackid){
		 
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		String scode = EncodeNum.encodeNum(trackid);
		String Query= "select trackname,password,message,photourl,status,scode from accountlevel_track_partners " +
		       " where  trackid=to_number(?,'9999999999')";
		statobj=dbmanager.executeSelectQuery(Query,new String []{trackid});
		// log.info("Inserting... tracking partner data"+statobj.getStatus());
		
		TrackingPartnerData tpd=new TrackingPartnerData();
			 
    		 tpd.setTrackname(dbmanager.getValue(0, "trackname",""));
    	     tpd.setPassword(dbmanager.getValue(0, "password",""));
    		 tpd.setMessage(dbmanager.getValue(0,"message", ""));
    		 tpd.setPhotoURL(dbmanager.getValue(0,"photourl", ""));
    		 tpd.setStatus(dbmanager.getValue(0,"status", ""));
    		 tpd.setScode(dbmanager.getValue(0,"scode", ""));
    	
    	return tpd;
	}
	
	public static void insertEventUnderPartner(String eventid,TrackingPartnerData tpd,String trackid){
	    String trackingid=trackid;
	    String trackname=tpd.getTrackname();
		String password=tpd.getPassword();
		String message=tpd.getMessage();
		String photo=tpd.getPhotoURL();
		String status=tpd.getStatus();
		String scode=tpd.getScode();
		//String scode = EncodeNum.encodeNum(trackingid);
        String query="insert into trackurls(eventid,trackingid,trackingcode,password,message,photo,status,secretcode)" +
		              " values(?,to_number(?,'99999999'),?,?,?,?,?,?)";
		// eventid,trackingid are of type varchar,bigint in trackurls table              
		DbUtil.executeUpdateQuery(query, new String[] {
				eventid, trackingid,trackname,password, message,photo,status, scode });
		 
		// log.info("insertEventsUnderPartner Status: "+ statobj.getStatus());
           
		
}
 
}
