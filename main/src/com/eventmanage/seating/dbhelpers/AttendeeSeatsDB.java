package com.eventmanage.seating.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class AttendeeSeatsDB {
	/*
	 * gets records of attendees for a transaction which is identified by transationid
	 * including seatcode and seatindex
	 */
	public static ArrayList<HashMap<String,String>> getAttendeeList(String tid){
		ArrayList<HashMap<String,String>> attendeeList=new ArrayList<HashMap<String,String>>();
		String query="select a.profilekey,a.fname,a.lname,a.seatcode,a.seatindex,b.section_id from profile_base_info a ,venue_seats b where a.seatindex=b.seatindex and a.transactionid=?";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query, new String[]{tid});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
				HashMap<String, String> attendeeMap=new HashMap<String, String>();
				attendeeMap.put("profilekey", db.getValue(i, "profilekey", ""));
				attendeeMap.put("fname", db.getValue(i, "fname", ""));
				attendeeMap.put("lname", db.getValue(i, "lname", ""));
				attendeeMap.put("seatcode", db.getValue(i, "seatcode", ""));
				attendeeMap.put("sectionid", db.getValue(i, "section_id", ""));
				attendeeMap.put("seatindex", db.getValue(i, "seatindex", ""));
				attendeeList.add(attendeeMap);
			}
		}
		return attendeeList;
	}
	
	public static boolean saveAttendeeSeats(ArrayList<String> seatcodes,ArrayList<String> sections,ArrayList<String> profilekeys,String tid,String eid,ArrayList<String> changeSeats,ArrayList<String> seatindeces,String edate){
		System.out.println("in save db");
		String update_transaction_query="update event_reg_transactions set eventdate=? where eventid=? and tid=?";
		String update_edate_bookingstatus="update seat_booking_status set eventdate=? where eventid=CAST(? AS BIGINT) and tid=?";
		String profile_update_query="update profile_base_info set seatcode=?,seatindex=? where eventid=CAST(? AS BIGINT) and profilekey=? and transactionid=?";
		String booking_status_query="update seat_booking_status set seatindex=?,section_id=to_number(?,'9999999999999'),eventdate=?,updated_at=now() where eventid=CAST(? AS BIGINT) and profilekey=? and tid=?";
		
		DbUtil.executeUpdateQuery(update_transaction_query, new String[]{edate,eid,tid});
		
		DbUtil.executeUpdateQuery(update_edate_bookingstatus, new String[]{edate,eid,tid});
		
		for(int i=0;i<profilekeys.size();i++){
			String changeseat=changeSeats.get(i);
		
			if(changeseat.equals("yes")){
		        DbUtil.executeUpdateQuery(profile_update_query, new String[]{seatcodes.get(i),seatindeces.get(i),eid,profilekeys.get(i),tid});
		        DbUtil.executeUpdateQuery(booking_status_query, new String[]{seatindeces.get(i),sections.get(i),edate,eid,profilekeys.get(i),tid});
		       
		        }
		}
		return false;
	}
	public static ArrayList getEventDates(String eid){
		DBManager db=new DBManager();
		ArrayList eventDatesList=new ArrayList();
		StatusObj statobj=null;
		 String query="select  to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY HH12:MI AM') as date_display"+
              " from event_dates where eventid=CAST(? AS BIGINT) order by (zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS') as text) as time ))";
		
		statobj=db.executeSelectQuery(query,new String[]{eid});
		int count=statobj.getCount();
		if(statobj.getStatus() && count>0){
          for(int i=0;i<count;i++){
        	  eventDatesList.add(new Entity(db.getValue(i,"date_display",""),db.getValue(i,"date_display","")));
  			 }
		}
		
		return eventDatesList;
	}
	public static HashMap<String, String> getProfile(String eid,String profilekey,String venue){
		DBManager db=new DBManager();
		HashMap<String, String> profileMap=new HashMap<String, String>();
		String query="select seatcode,seatindex from profile_base_info where eventid=CAST(? AS BIGINT) and profilekey=?";
		String section_query="select section_id from venue_seats where seatindex=?";
		StatusObj sob=db.executeSelectQuery(query, new String[]{eid,profilekey});
		if(sob.getStatus()){
			profileMap.put("seatcode", db.getValue(0, "seatcode", ""));
			profileMap.put("seatindex", db.getValue(0, "seatindex", ""));
		}
		String sindex=profileMap.get("seatindex");
		System.out.println("sindex: "+sindex);
		if(sindex==null) sindex="";
		if(!"".equals(sindex)){
			sob=db.executeSelectQuery(section_query, new String[]{sindex});
			if(sob.getStatus())
				profileMap.put("sectionid", db.getValue(0, "section_id", ""));
		}
		profileMap.put("venueid", venue);
		return profileMap;
	}
	public static void saveAttendeeSeat(HashMap<String,String> hm){
		try{
		String currentdate=DateUtil.getCurrDBFormatDate();
		if("changeseat".equals(hm.get("seataction"))){
		System.out.println("In changeseatcase:"+hm.get("tid")+":"+hm.get("eid"));
		HashMap<String,String> hmap=new HashMap<String,String>();
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		statobj=dbm.executeSelectQuery("select fname,lname,email,seatindex,seatcode,profileid,ticketid  from profile_base_info where eventid=CAST(? AS BIGINT) and profilekey=? and transactionid=?", new String[]{hm.get("eid"),hm.get("profilekey"),hm.get("tid")});
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
				hmap.put("fname",dbm.getValue(i,"fname",""));
				hmap.put("lname",dbm.getValue(i,"lname",""));
				hmap.put("email",dbm.getValue(i,"email",""));
				hmap.put("seatindex",dbm.getValue(i,"seatindex",""));
				hmap.put("seatcode",dbm.getValue(i,"seatcode",""));
				hmap.put("profileid",dbm.getValue(i,"profileid",""));
				hmap.put("ticketid",dbm.getValue(i,"ticketid",""));
			}
		}
		String eventdate=DbUtil.getVal("select eventdate from event_reg_transactions where eventid=? and tid=?", new String[]{hm.get("eid"),hm.get("tid")});
		String profile_update_query="update profile_base_info set seatcode=?,seatindex=? where eventid=CAST(? AS BIGINT) and profilekey=? and transactionid=?";
		String booking_status_query="update seat_booking_status set seatindex=?,section_id=to_number(?,'9999999999999'),updated_at=now() where eventid=CAST(? AS BIGINT) and profilekey=? and tid=?";
		String trackinsertquery="insert into editmanage_attendeetrack(fname,lname,originalseatindex,originalseatcode,eventid,profileid,profilekey,email,"
            + "tid,ticketid,created_at,currentseatindex,currentseatcode,actiontype,eventdate) values(?,?,?,?,?,?,?,?,?,?,to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.ms'),?,?,?,?)";
		DbUtil.executeUpdateQuery(profile_update_query, new String[]{hm.get("seatcode"),hm.get("seatindex"),hm.get("eid"),hm.get("profilekey"),hm.get("tid")});
		DbUtil.executeUpdateQuery(booking_status_query, new String[]{hm.get("seatindex"),hm.get("sectionid"),hm.get("eid"),hm.get("profilekey"),hm.get("tid")});
		DbUtil.executeUpdateQuery(trackinsertquery,new String[]{hmap.get("fname"),hmap.get("lname"),hmap.get("seatindex"),hmap.get("seatcode"),hm.get("eid"),hmap.get("profileid"),
				hm.get("profilekey"),hmap.get("email"),hm.get("tid"),hmap.get("ticketid"),currentdate,hm.get("seatindex"),hm.get("seatcode"),hm.get("seataction"),eventdate});
		}else if("addseat".equals(hm.get("seataction"))){
			System.out.println("In addseatcase:"+hm.get("tid")+":"+hm.get("eid"));
			String ticketid=DbUtil.getVal("select ticketid from profile_base_info where profilekey=? and transactionid=?", new String[]{hm.get("profilekey"),hm.get("tid")});
			String eventdate=DbUtil.getVal("select eventdate from event_reg_transactions where eventid=? and tid=?", new String[]{hm.get("eid"),hm.get("tid")});
			if(ticketid==null)ticketid="";
			String seatbooking_insert_query="insert into seat_booking_status(profilekey,bookingtime,"
	        +"venue_id,section_id,tid,ticketid,eventid,seatindex,eventdate) values(?,to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'),"
	        +"to_number(?,'999999999'),to_number(?,'999999999'),?,to_number(?,'999999999'),CAST(? AS BIGINT),?,?)";
			String trackinsertquery="insert into editmanage_attendeetrack(fname,lname,eventid,profileid,profilekey,email,"
	            + "tid,ticketid,actiontype) select fname,lname,eventid,profileid,profilekey,email,transactionid,ticketid,? from profile_base_info where profilekey=? and"
	            + " transactionid=? and eventid=CAST(? AS BIGINT)";
			DbUtil.executeUpdateQuery(trackinsertquery,new String[]{hm.get("seataction"),hm.get("profilekey"),hm.get("tid"),hm.get("eid")});
			if(!"".equals(ticketid))
			DbUtil.executeUpdateQuery(seatbooking_insert_query, new String[]{hm.get("profilekey"),currentdate,hm.get("venueid"),hm.get("sectionid"),hm.get("tid"),ticketid,hm.get("eid"),hm.get("seatindex"),eventdate});
			
			DbUtil.executeUpdateQuery("update profile_base_info set seatcode=?,seatindex=?,created_at=to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S') where eventid=CAST(? AS BIGINT) and profilekey=? and transactionid=?", new String[]{hm.get("seatcode"),hm.get("seatindex"),currentdate,hm.get("eid"),hm.get("profilekey"),hm.get("tid")});
			DbUtil.executeUpdateQuery("update editmanage_attendeetrack set originalseatcode=?,originalseatindex=?,created_at=to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'),eventdate=? where eventid=? and profilekey=? and tid=? and actiontype=?", new String[]{hm.get("seatcode"),hm.get("seatindex"),currentdate,eventdate,hm.get("eid"),hm.get("profilekey"),hm.get("tid"),hm.get("seataction")});	
		}
		
	}catch(Exception e){
		System.out.println("Exception occured in saveAttendeeSeat:"+hm.get("tid")+":"+hm.get("eid"));
	}
}
	 
	public static String getSeatStatus(String selectedseat,String eventid){
		String seatstatus="";
		String holdstatus=DbUtil.getVal("select 'Y' from event_reg_block_seats_temp where eventid=? and seatindex=?", new String[]{eventid,selectedseat});
		if(holdstatus==null || "".equals(holdstatus))holdstatus="N";
		if("Y".equals(holdstatus))
			seatstatus="hold";
		if("".equals(seatstatus)){
		String soldstatus=DbUtil.getVal("select 'Y' from seat_booking_status where eventid=CAST(? AS BIGINT) and seatindex=?", new String[]{eventid,selectedseat});
		if(soldstatus==null || "".equals(soldstatus))soldstatus="N";
		if("Y".equals(soldstatus))
			seatstatus="sold";
		}
		return seatstatus;
	}
}	
	
