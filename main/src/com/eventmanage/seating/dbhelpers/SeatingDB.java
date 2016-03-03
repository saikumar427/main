package com.eventmanage.seating.dbhelpers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.event.dbhelpers.EventDB;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EventbeeLogger;
import com.eventbee.general.StatusObj;
import com.eventmanage.seating.beans.Seat;
import com.eventmanage.seating.beans.SeatColor;
import com.eventmanage.seating.beans.Section;
import com.eventmanage.seating.beans.Venue;

public class SeatingDB {
	public static final ArrayList<String> basecolors=new ArrayList<String>();
	public static final HashMap<String,String> directionsuffix=new HashMap<String, String>();
	static{
		basecolors.add("darkblue");
		basecolors.add("darkgreen");
		basecolors.add("darkorange");
		basecolors.add("darkpink");
		basecolors.add("lightblue");
		basecolors.add("lightgreen");
		basecolors.add("lightorange");
		basecolors.add("lightpink");
		directionsuffix.put("north", "n");
		directionsuffix.put("south", "s");
		directionsuffix.put("east", "e");
		directionsuffix.put("west", "w");
		directionsuffix.put("north_east", "ne");
		directionsuffix.put("north_west", "nw");
		directionsuffix.put("south_east", "se");
		directionsuffix.put("south_west", "sw");
		directionsuffix.put("no_direction", "blank");
	}
	private static final Logger log = Logger.getLogger(SeatingDB.class);
	public static ArrayList<Entity> getVenueList(){
		ArrayList<Entity> venueList=new ArrayList<Entity>();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery("select venue_id,venuename from seating_venues where venuename!=''",null);
		for(int i=0;i<sb.getCount();i++){
			venueList.add( new Entity(db.getValue(i, "venue_id", ""),db.getValue(i, "venuename", "")));
		}
		return venueList;
	}
	public static ArrayList<Entity> GetVenueSections(String venueid){
		ArrayList<Entity> sections=new ArrayList<Entity>();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery("select section_id,sectionname from venue_sections where venue_id=to_number(?,'9999999999')", new String[]{venueid});
		for(int i=0;i<sb.getCount();i++){
			sections.add( new Entity(db.getValue(i, "section_id", ""),db.getValue(i, "sectionname", "")));
		}
		return sections;
	}
	public static Section getSection(String sectionid){
		Section section=new Section();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery("select * from venue_sections where section_id=to_number(?,'9999999999')", new String[]{sectionid});
		section.setSectionid(db.getValue(0, "section_id", ""));
		section.setSectionname(db.getValue(0,"sectionname", ""));
		section.setNo_of_rows(db.getValue(0, "noofrows", ""));
		section.setNo_of_cols(db.getValue(0,"noofcols", ""));
		
		section.setBackground_image(db.getValue(0,"background_image", ""));
		section.setSeat_image_width(db.getValue(0,"seat_image_width", ""));
		section.setSeat_image_height(db.getValue(0,"seat_image_height", ""));
		section.setLayout_css(db.getValue(0,"layout_css", ""));
		section.setSeats(getSectionSeats(sectionid));
		return section;
	}
	public static ArrayList<Seat> getSectionSeats(String sectionid){
		ArrayList<Seat> seats=new ArrayList<Seat>();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery("select row_id,col_id,seatindex,isseat,seatcode from venue_seats where section_id=to_number(?,'9999999999') and isseat='Y'", new String[]{sectionid});
		for(int i=0;i<sb.getCount();i++){
			Seat seat=new Seat();
			seat.setRowid(db.getValue(i, "row_id",""));
			seat.setColid(db.getValue(i, "col_id",""));
			seat.setSeatIndex(db.getValue(i, "seatindex",""));
			seat.setIsSeat(db.getValue(i, "isseat",""));
			seat.setSeatCode(db.getValue(i, "seatcode",""));
			seats.add(seat);
		}
		return seats;
	}
	public static String getVenue(String eid){
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid});
		log.info("configid "+configid);
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN, EventbeeLogger.INFO, "SeatingDB", "configid "+configid, "", null);
		String venueid=DbUtil.getVal("select value from config where config_id=CAST(? as integer) and name='event.seating.venueid'", new String[]{configid});
		log.info("Venue id "+venueid);
		return venueid;
	}
	public static void addVenue(String eid,String venueid){
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid});
		removeEventLevelCapacity(configid, eid);
		String current_venueid=DbUtil.getVal("select value from config where config_id=CAST(? as integer) and name='event.seating.venueid'", new String[]{configid});
		if(current_venueid==null) current_venueid="";
		if("".equals(venueid)) venueid="NAN";
		if(!current_venueid.equals(venueid)){
		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? as integer) and name='event.seating.venueid'", new String[]{configid});
		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? as integer) and name='event.seating.enabled'", new String[]{configid});
		DbUtil.executeUpdateQuery("delete from event_seating where eventid=CAST(? AS BIGINT)",new String[]{eid});
		DbUtil.executeUpdateQuery("delete from seat_groups where eventid=CAST(? AS BIGINT)",new String[]{eid});
		DbUtil.executeUpdateQuery("delete from seat_tickets where eventid=CAST(? AS BIGINT)",new String[]{eid});
		DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(to_number(?,'9999999999'),'event.seating.venueid',?)",new String[]{configid,venueid});
		DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(to_number(?,'9999999999'),'event.seating.enabled','YES')",new String[]{configid});
		}
		
		//for Page Design ticket widget moving to top(wide_widgets TO single_widgets)
		DBManager dbm = new DBManager();
		StatusObj statobj;
		String selectEvent_layout = "select * from event_layout where eventid=?::bigint and stage='final'";
		statobj = dbm.executeSelectQuery(selectEvent_layout, new String[]{eid});
		if(statobj.getStatus() && statobj.getCount()>0){
			String singleWidgets=dbm.getValue(0,"single_widgets","");
			String wideWidgets=dbm.getValue(0,"wide_widgets","");
			if(singleWidgets!=null && singleWidgets.indexOf("tickets")>-1){
				
			}else if(wideWidgets!=null && wideWidgets.indexOf("tickets")>-1){
				EventDB.removeTicketsFromWideWidgets(singleWidgets,wideWidgets,eid);
			}
			String themeData=DbUtil.getVal("select 'yes' from event_layout where eventid=?::bigint and stage='draft'", new String[]{eid});
			if(themeData==null || "".equals(themeData)){
				String query = "insert into event_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode) (select  ?::bigint,'draft','no',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode from event_layout where eventid='1'::bigint )";
				DbUtil.executeUpdateQuery(query, new String[]{eid});
			}
			DbUtil.executeUpdateQuery("update event_layout set single_widgets=(select single_widgets from event_layout where eventid=?::bigint and stage='final') where eventid=?::bigint and stage='draft'", new String[]{eid,eid});
			DbUtil.executeUpdateQuery("update event_layout set wide_widgets=(select wide_widgets from event_layout where eventid=?::bigint and stage='final') where eventid=?::bigint and stage='draft'", new String[]{eid,eid});
		}
	}
	
	
	
	
	public static Venue getVenueDetails(String venueid){
		Venue venue=new Venue();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery("select venuename,address,city,state,country from seating_venues where venue_id=to_number(?,'9999999999')", new String[]{venueid});
		venue.setVenueid(venueid);
		venue.setVenuename(db.getValue(0, "venuename", ""));
		venue.setAddress(db.getValue(0,"address", ""));
		venue.setCity(db.getValue(0,"city", ""));
		venue.setState(db.getValue(0,"state", ""));
		venue.setCountry(db.getValue(0,"country", ""));
		return venue;
	}
	public static void addSeat(ArrayList tickets,String color,String eid,String venueid){
		String seat_grpid=DbUtil.getVal("select nextval('seat_groupid_seq')",null);
		DbUtil.executeUpdateQuery("insert into seat_groups(eventid,venue_id,seat_groupid,color) values(CAST(? AS BIGINT),to_number(?,'9999999999'),to_number(?,'999999999'),?)", new String[]{eid,venueid,seat_grpid,color});
		if(tickets.size()==0)
		{
			DbUtil.executeUpdateQuery("insert into seat_tickets(eventid,venue_id,seat_groupid,ticketid)" +
					" values(CAST(? AS BIGINT),to_number(?,'99999999999'),to_number(?,'99999999999'),-100)", new String[]{eid,venueid,seat_grpid});
			return;
		}
		for(int i=0;i<tickets.size();i++)
		{
			DbUtil.executeUpdateQuery("insert into seat_tickets(eventid,venue_id,seat_groupid,ticketid) values(CAST(? AS BIGINT),to_number(?,'9999999999'),to_number(?,'999999999'),to_number(?,'999999999'))", new String[]{eid,venueid,seat_grpid,(String)tickets.get(i)});
		}
	}
	public static void deleteSeat(String color,String eid,String venueid){
		DbUtil.executeUpdateQuery("delete from seat_tickets where seat_groupid in" +
				"(select seat_groupid from seat_groups where eventid=CAST(? AS BIGINT) and venue_id=to_number(?,'999999999999999') and color=?)", new String[]{eid,venueid,color});
			DbUtil.executeUpdateQuery("delete from seat_groups where eventid=CAST(? AS BIGINT) and venue_id=to_number(?,'999999999999') and color=?", new String[]{eid,venueid,color});
	}
	public static void addRSVPSeat(String color,String eid,String venueid,String sectionid){
		String seat_grpid=DbUtil.getVal("select nextval('seat_groupid_seq')",null);
		DbUtil.executeUpdateQuery("insert into seat_groups(eventid,venue_id,section_id,seat_groupid,color) values(CAST(? AS BIGINT),to_number(?,'9999999999'),to_number(?,'9999999999'),to_number(?,'999999999'),?)", new String[]{eid,venueid,sectionid,seat_grpid,color});
		DbUtil.executeUpdateQuery("insert into seat_tickets(eventid,venue_id,section_id,seat_groupid,ticketid) values(CAST(? AS BIGINT),to_number(?,'9999999999'),to_number(?,'9999999999'),to_number(?,'999999999'),101)", new String[]{eid,venueid,sectionid,seat_grpid});
	}
	public static ArrayList<SeatColor> getSeatColors(String eid){
		DBManager dbmanager=new DBManager();
		ArrayList<SeatColor> seatcolors=new ArrayList<SeatColor>();
		HashMap<String,ArrayList<String>> colors=new HashMap<String,ArrayList<String>>();
		StatusObj sb=dbmanager.executeSelectQuery("select a.color as color,(select c.ticket_name from price c where price_id=b.ticketid) as ticketname " +
				"from seat_groups a,seat_tickets b where a.seat_groupid=b.seat_groupid and a.eventid=CAST(? AS BIGINT) order by color",new String[]{eid});
		SeatColor seatcolor=new SeatColor();
		ArrayList<String> tickets;
		for(int i=0;i<sb.getCount();i++)
		{
			String color=dbmanager.getValue(i, "color", "");
			if(colors.containsKey(color)){
				tickets=(ArrayList<String>)colors.get(color);
				tickets.add(dbmanager.getValue(i, "ticketname", ""));
				seatcolor.setTickets(tickets);
			}else{
				tickets=new ArrayList<String>();
				seatcolor=new SeatColor();
				seatcolor.setColor(dbmanager.getValue(i, "color", ""));
				tickets.add(dbmanager.getValue(i, "ticketname", ""));
				seatcolor.setTickets(tickets);
				colors.put(color, tickets);
				seatcolors.add(seatcolor);
			}
		}
		return seatcolors;
	}
	public static ArrayList<SeatColor> getRSVPSeatColors(String eid){
		ArrayList<SeatColor> rsvpseatcolors=new ArrayList<SeatColor>();
		DBManager dbmanager=new DBManager();
		StatusObj statusobj=dbmanager.executeSelectQuery("select distinct color from seat_groups where eventid=CAST(? AS BIGINT)", new String[]{eid});
		for(int i=0;i<statusobj.getCount();i++){
			SeatColor seatcolor=new SeatColor();
			seatcolor.setColor(dbmanager.getValue(i, "color", ""));
			System.out.println(seatcolor.getColor());
			rsvpseatcolors.add(seatcolor);
		}
		return rsvpseatcolors;
	}
	public static HashMap<String, String> getSeatGroups(String eid){
		HashMap<String, String> seatgroups=new HashMap<String, String>();
		DBManager dbmanager=new DBManager();
		StatusObj sb=dbmanager.executeSelectQuery("select color,seat_groupid from seat_groups where eventid=CAST(? AS BIGINT)", new String[]{eid});
		for(int i=0;i<sb.getCount();i++){
		seatgroups.put(dbmanager.getValue(i, "color", ""), dbmanager.getValue(i, "seat_groupid", ""));
		}
		return seatgroups;
	}
	public static void saveSeats(String eid,String venueid,String sectionid,JSONArray seatsJson){
		int l=seatsJson.length();
		HashMap<String, String> seatGroups=getSeatGroups(eid);
		try{
			for(int i=0;i<l;i++){
				JSONObject seat=seatsJson.getJSONObject(i);
				String sid=(String)seat.get("sid");
				String color=(String)seat.get("color");
				DbUtil.executeUpdateQuery("delete from event_seating where eventid=CAST(? AS BIGINT) and venue_id=to_number(?,'99999999999') and section_id=to_number(?,'99999999999') and seatindex=?", new String[]{eid,venueid,sectionid,sid});
				if(color.equals("lightgray_blank")){
					DbUtil.executeUpdateQuery("insert into event_seating(eventid,venue_id,section_id,seatindex,seat_groupid) values(CAST(? AS BIGINT),to_number(?,'9999999999'),to_number(?,'9999999999'),?,-1)", new String[]{eid,venueid,sectionid,sid});
				}
				else if(color.equals("lightgray_question")){
				}
				else{
						String seat_groupid=seatGroups.get(color);
						DbUtil.executeUpdateQuery("insert into event_seating(eventid,venue_id,section_id,seatindex,seat_groupid) values(CAST(? AS BIGINT),to_number(?,'9999999999'),to_number(?,'9999999999'),?,to_number(?,'9999999999'))", new String[]{eid,venueid,sectionid,sid,seat_groupid});
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public static HashMap<String, String> getAllotedSeats(String eid,String sectionid){
		HashMap<String, String> seatIndeces=new HashMap<String, String>();
		DBManager dbmanager=new DBManager();
		StatusObj sb=dbmanager.executeSelectQuery("select seatindex,seat_groupid from event_seating where eventid=CAST(? AS BIGINT) and section_id=to_number(?,'9999999999')", new String[]{eid,sectionid});
		for(int i=0;i<sb.getCount();i++){
			seatIndeces.put(dbmanager.getValue(i, "seatindex", ""), dbmanager.getValue(i, "seat_groupid", ""));
		}
		return seatIndeces;
	}
	public static HashMap<String, String> getAllotedSeatColors(String eid){
		HashMap<String, String> seatgroups=new HashMap<String, String>();
		DBManager dbmanager=new DBManager();
		StatusObj sb=dbmanager.executeSelectQuery("select color,seat_groupid from seat_groups where eventid=CAST(? AS BIGINT)", new String[]{eid});
		seatgroups.put("-1", "lightgray_blank");
		for(int i=0;i<sb.getCount();i++){
		seatgroups.put(dbmanager.getValue(i, "seat_groupid", ""), dbmanager.getValue(i, "color", ""));
		}
		return seatgroups;
	}
	public static List getSoldOutSeats(String eid,String sectionid,String eventdate){
		List seats;
		if("".equals(eventdate))
		seats=DbUtil.getValues("select seatindex from seat_booking_status where eventid=CAST(? AS BIGINT) and section_id=to_number(?,'9999999999') and status is null", new String[]{eid,sectionid});
		else
		seats=DbUtil.getValues("select seatindex from seat_booking_status where eventid=CAST(? AS BIGINT) and section_id=to_number(?,'9999999999') and eventdate=? and status is null", new String[]{eid,sectionid,eventdate});
		return seats;
	}
	public static List getBlockedSeats(String eid,String sectionid,String eventdate){
		List seats;
		seats=DbUtil.getValues("select seatindex from seat_booking_status where eventid=CAST(? AS BIGINT) and section_id=to_number(?,'9999999999') and eventdate=? and status='blocked'", new String[]{eid,sectionid,eventdate});
		return seats;
	}
	public static List<String> getColors(String eid,String sectionid){
		List colors=DbUtil.getValues("select color from supported_colors where color not in (select color from seat_groups where eventid=CAST(? AS BIGINT) and section_id=to_number(?,'999999999'))",new String[]{eid,sectionid});
		return colors;
	}
	public static LinkedHashMap<String, ArrayList<String>> getSupportedSeatColors(String eid,String sectionid){ 
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		LinkedHashMap<String,ArrayList<String>> directions=new LinkedHashMap<String,ArrayList<String>>();
		ArrayList<String> supportedcolors;
		DBManager dbmanager=new DBManager();
		StatusObj stobj=dbmanager.executeSelectQuery("select color,direction,added,category_position,color_position from ("+
				"(select color,category as direction,'NO' as added,category_position,color_position  from available_seatcolors where color not in (select color from seat_groups where eventid=?::BIGINT) and category!='' and color not like 'lightgray%' order by category_position desc)"+ 
				"UNION     (select color,category as direction,'YES' as added,category_position,color_position  from available_seatcolors where color in (select color from seat_groups where eventid=?::BIGINT) and category!='' and color not like 'lightgray%' order by category_position desc)"+
				") as available_colors order by category_position,color_position ", new String[]{eid,eid});
		for(int i=0;i<stobj.getCount();i++){
			
			String seatdirection=dbmanager.getValue(i, "direction", "");
			String temp = seatdirection.replaceAll(" ", "");
			String direction=resourceBundle.getString("sea.direction."+temp);
			
			if(directions.containsKey(direction)){
				supportedcolors=directions.get(direction);
				supportedcolors.add("NO".equals(dbmanager.getValue(i, "added", ""))?dbmanager.getValue(i, "color", ""):"blank");
			}else{
				supportedcolors=new ArrayList<String>();
				supportedcolors.add("NO".equals(dbmanager.getValue(i, "added", ""))?dbmanager.getValue(i, "color", ""):"blank");
				directions.put(direction, supportedcolors);
			}
		}
		return directions;
	}
	public static ArrayList getselectedTickets(String eid,String color){
		String seatgroup=DbUtil.getVal("select seat_groupid from seat_groups where eventid=CAST(? AS BIGINT)" +
				" and color=?", new String[]{eid,color});
		ArrayList seltickets=new ArrayList();
		DBManager dbmanager=new DBManager();
		StatusObj sb=dbmanager.executeSelectQuery("select ticketid from seat_tickets where " +
				"eventid=CAST(? AS BIGINT) and seat_groupid=to_number(?,'9999999999')", new String[]{eid,seatgroup});
		for(int i=0;i<sb.getCount();i++){
			seltickets.add(dbmanager.getValue(i, "ticketid",""));
		}
		return seltickets;
	}
	public static void updateSeatTickets(ArrayList seltickets,String eid,String venueid,String color){
		String seatgroup=DbUtil.getVal("select seat_groupid from seat_groups where eventid=CAST(? AS BIGINT)" +
				" and color=?", new String[]{eid,color});
		StatusObj sb=DbUtil.executeUpdateQuery("delete from seat_tickets where eventid=CAST(? AS BIGINT)" +
				" and seat_groupid=to_number(?,'9999999999')", new String[]{eid,seatgroup});
		if(sb.getStatus()){
		if(seltickets.size()==0)
		{
			DbUtil.executeUpdateQuery("insert into seat_tickets(eventid,venue_id,seat_groupid,ticketid)" +
					" values(CAST(? AS BIGINT),to_number(?,'99999999999'),to_number(?,'99999999999'),-100)", new String[]{eid,venueid,seatgroup});
		}else{
		for(int i=0;i<seltickets.size();i++){
			DbUtil.executeUpdateQuery("insert into seat_tickets(eventid,venue_id,seat_groupid,ticketid)" +
					" values(CAST(? AS BIGINT),to_number(?,'99999999999'),to_number(?,'99999999999'),to_number(?,'99999999999'))", new String[]{eid,venueid,seatgroup,(String)seltickets.get(i)});
		}
		}
		}
		
	}
	public static void saveAddManual(String eid,String venueid,String sectionid,JSONArray seatsJson,String eventdate){
		int l=seatsJson.length();
		HashMap<String, String> seatGroups=getSeatGroups(eid);
		List soldList=DbUtil.getValues("select seatindex from seat_booking_status where" +
				" eventid=CAST(? AS BIGINT) and venue_id=to_number(?,'99999999999')" +
				" and section_id=to_number(?,'99999999999') and eventdate=? and status is null", new String[]{eid,venueid,sectionid,eventdate});
		List holdList=DbUtil.getValues("select seatindex from event_reg_block_seats_temp where eventid=? and eventdate=?",new String[]{eid,eventdate});
		try{
			for(int i=0;i<l;i++){
				JSONObject seat=seatsJson.getJSONObject(i);
				String sid=(String)seat.get("sid");
				String action=(String)seat.get("action");
				if(soldList.contains(sid) || holdList.contains(sid)){
				}
				else{
					if("block".equals(action)){
						DbUtil.executeUpdateQuery("insert into seat_booking_status(eventid,venue_id,section_id,seatindex,eventdate,status) values(CAST(? AS BIGINT),to_number(?,'9999999999'),to_number(?,'9999999999'),?,?,'blocked')", new String[]{eid,venueid,sectionid,sid,eventdate});
					}
					else if("unblock".equals(action)){
							DbUtil.executeUpdateQuery("delete from seat_booking_status where eventid=CAST(? AS BIGINT) and venue_id=to_number(?,'9999999999999') and section_id=to_number(?,'999999999999') and seatindex=? and eventdate=?", new String[]{eid,venueid,sectionid,sid,eventdate});
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public static ArrayList getEventTickets(String eid){
		String ticketSelQuery="select pr.price_id,pr.ticket_name as ticketname,etg.groupname  as groupname from price pr," +
		"event_ticket_groups etg,group_tickets gt where gt.ticket_groupid=etg.ticket_groupid and pr." +
		"price_id=to_number(gt.price_id,'9999999999') and  etg.eventid=? and " +
		"pr.evt_id=CAST(? AS BIGINT) and pr.isdonation='No' order by etg.position,gt.position";
		return EventDB.getTicketsData(ticketSelQuery,eid);
	}
	/*public static String USER_VENUES_QUERY="select venue_id,venuename from seating_venues where venue_id in" +
	"(select to_number(value,'99999999999') from config where config_id in " +
	"(select config_id from eventinfo where mgr_id=to_number(?,'9999999999999')) and name='event.seating.venueid')";*/
	public static String USER_VENUES_QUERY="select venue_id,venuename from seating_venues where venue_id in" +
			"(select distinct venue_id from manager_venues_list where  mgr_id=CAST(? as INTEGER))";
	public static ArrayList<Entity> getUserVenues(String mgrId){
		ArrayList<Entity> venueList=new ArrayList<Entity>();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(USER_VENUES_QUERY,new String[]{mgrId});
		for(int i=0;i<sb.getCount();i++){
			venueList.add( new Entity(db.getValue(i, "venue_id", ""),db.getValue(i, "venuename", "")));
		}
		return venueList;
	}
	public static void showVenue(String eid,boolean showvenue,String venueid){
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid});
		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? as integer) and name='event.seating.enabled'", new String[]{configid});
		String showstatus="NO";
		if(showvenue){
			showstatus="YES";
			removeEventLevelCapacity(configid, eid);
		}else
			showstatus="NO";
		DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(to_number(?,'9999999999999'),'event.seating.enabled',?)", new String[]{configid,showstatus});
		//for Page Design 
		if("YES".equals(showstatus)){
			DBManager dbm = new DBManager();
			StatusObj statobj;
			String selectEvent_layout = "select * from event_layout where eventid=?::bigint and stage='final'";
			statobj = dbm.executeSelectQuery(selectEvent_layout, new String[]{eid});
			if(statobj.getStatus() && statobj.getCount()>0){
				String singleWidgets=dbm.getValue(0,"single_widgets","");
				String wideWidgets=dbm.getValue(0,"wide_widgets","");
				if(singleWidgets!=null && singleWidgets.indexOf("tickets")>-1){
					
				}else if(wideWidgets!=null && wideWidgets.indexOf("tickets")>-1){
					System.out.println("Checking... ININ");
					EventDB.removeTicketsFromWideWidgets(singleWidgets,wideWidgets,eid);
				}
				String themeData=DbUtil.getVal("select 'yes' from event_layout where eventid=?::bigint and stage='draft'", new String[]{eid});
				if(themeData==null || "".equals(themeData)){
					String query = "insert into event_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode) (select  ?::bigint,'draft','no',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode from event_layout where eventid='1'::bigint )";
					DbUtil.executeUpdateQuery(query, new String[]{eid});
				}
				DbUtil.executeUpdateQuery("update event_layout set single_widgets=(select single_widgets from event_layout where eventid=?::bigint and stage='final') where eventid=?::bigint and stage='draft'", new String[]{eid,eid});
				DbUtil.executeUpdateQuery("update event_layout set wide_widgets=(select wide_widgets from event_layout where eventid=?::bigint and stage='final') where eventid=?::bigint and stage='draft'", new String[]{eid,eid});
			}
		}
		
	}
	public static void removeEventLevelCapacity(String configId, String eventid){
		String eventlevelcheck = DbUtil.getVal("select 'YES' from config where config_id=CAST(? as integer) and name='event.reg.eventlevelcheck.count'", new String[]{configId});
		if("YES".equals(eventlevelcheck)){
			DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? as integer) and name='event.reg.eventlevelcheck.count'", new String[]{configId});
			System.out.println("Deleting event level capacity while enabling venu seating : EventID : "+eventid);
		}
	}
	public static ArrayList<String> getAllColors(){
		ArrayList<String> allcolors=new ArrayList<String>();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery("select color from supported_seatcolors",null);
		for(int i=0;i<sb.getCount();i++){
			allcolors.add(db.getValue(i, "color", ""));
		}
		return allcolors;
	}
	public static boolean getShowVenue(String eid){
		String show=DbUtil.getVal("select value from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.seating.enabled'", new String[]{eid});
		if(show==null) show="";
		System.out.println("show: "+show);
		if(show.equals("YES")) return true;
		else return false;
	}
	public static int getRegCount(String eid){
		String count=DbUtil.getVal("select count(*) from seat_booking_status where eventid=CAST(? AS BIGINT)", new String[]{eid});
		if(count==null) count="0" ;
		return Integer.parseInt(count);
	}
	public static ArrayList getLockedSeatsData(String eid){
		ArrayList seatingList=new ArrayList();
		HashMap timeMap=new HashMap();
		HashMap recurdateMap=new HashMap();
		String query="select transactionid,seatindex,floor(EXTRACT(EPOCH FROM  (now()-blocked_at))/60) as blocked_at,eventdate from event_reg_block_seats_temp where"+
		" eventid=? group by transactionid,seatindex,blocked_at,eventdate order by blocked_at desc ";
		DBManager dbm=new DBManager();
		StatusObj sbj=dbm.executeSelectQuery(query,new String[]{eid});
		HashMap allseatcodes =getAllSeatCodes(eid);
		ArrayList<String> tempList=new ArrayList<String>();
		ArrayList tidList=new ArrayList();
		HashMap<String,ArrayList<String>> tempMap=new HashMap<String,ArrayList<String>>();
		if(sbj.getStatus() && sbj.getCount()>0){
			for(int i=0;i<sbj.getCount();i++){
				String tid=dbm.getValue(i,"transactionid","");
				String sindex=dbm.getValue(i,"seatindex","");
				String blocktime=dbm.getValue(i,"blocked_at","");
				String eventdate=dbm.getValue(i,"eventdate","");
				if(sindex==null)sindex="";
			if(!tempMap.containsKey(tid)){
				tempList=new ArrayList<String>();
				tempList.add((String)allseatcodes.get(sindex));
				tempMap.put(tid,tempList);
				tidList.add(tid);
				timeMap.put(tid, blocktime);
				recurdateMap.put(tid,eventdate);
			}else{
				tempList=new ArrayList<String>();
				tempList=tempMap.get(tid);
				tempList.add((String)allseatcodes.get(sindex));
				tempMap.put(tid,tempList);
				}
			}
			HashMap statusMap=getTransactionTimeAndStatus(eid,tidList);
			for(int i=0;i<tidList.size();i++){
				String tid=(String)tidList.get(i);
				//String sindex=dbm.getValue(i,"seatindex","");
				HashMap transMap=(HashMap)statusMap.get(tid);
				if(transMap==null)transMap=new HashMap();
				HashMap seatingMap=new HashMap();
				seatingMap.put("tid", tid);
				seatingMap.put("seatcodes",tempMap.get(tid));
				seatingMap.put("curraction",transMap.get("caction"));
				//String timediff=getTimeinMinutes((String)timeMap.get(tid));
				seatingMap.put("tdate",(String)timeMap.get(tid));
				seatingMap.put("recurdate",(String)recurdateMap.get(tid));
				seatingList.add(seatingMap);
			}
		}
return seatingList; 
	}
	
	public static HashMap getAllSeatCodes(String eid){
		HashMap hmap=new HashMap();
		String venueid=EventDB.getConfigVal(eid,"event.seating.venueid","");
		if(venueid==null)venueid="";
	if(!"".equals(venueid)){
		String seatcodes="select seatcode,seatindex from venue_seats where venue_id=cast(? as integer)";
		DBManager dbm=new DBManager();
		StatusObj sbj=dbm.executeSelectQuery(seatcodes, new String[]{venueid});
		if(sbj.getStatus() && sbj.getCount()>0){
			for(int i=0;i<sbj.getCount();i++){
			hmap.put(dbm.getValue(i,"seatindex",""),dbm.getValue(i,"seatcode",""));
			}
		}
	}   
	return hmap;
	}
	
	public static HashMap getTransactionTimeAndStatus(String eid,ArrayList tidlist){
		String csv = tidlist.toString().replace("[", "").replace("]", "")
	            .replace(", ", "','");
		csv="'"+csv+"'";
		HashMap transMap=new HashMap();
		String query="select (now()-transactiondate) as transactiondate,current_action,tid from event_reg_details_temp where eventid=? and"+
	" tid in("+csv+")";
		DBManager dbm=new DBManager();
		StatusObj sbj=dbm.executeSelectQuery(query,new String[]{eid});
		if(sbj.getStatus() && sbj.getCount()>0){
			for(int i=0;i<sbj.getCount();i++){
			HashMap hmap=new HashMap();
			if(tidlist.contains(dbm.getValue(i,"tid",""))){
				hmap.put("tdate", dbm.getValue(i,"transactiondate",""));
				hmap.put("caction", dbm.getValue(i,"current_action",""));
			}
			transMap.put(dbm.getValue(i,"tid",""), hmap);
				
			}
		}
	return transMap;
	}
	
	public static void realeseSeats(String eid,String tid){
	String query="insert into released_seats_track(eventid,seatindex,transactionid,released_at,ticketid,eventdate) select "+
	              "eventid,seatindex,transactionid,now(),ticketid,eventdate from event_reg_block_seats_temp where  transactionid=? and eventid=?";
	DbUtil.executeUpdateQuery(query, new String[]{tid,eid});	
	DbUtil.executeUpdateQuery("delete from event_reg_block_seats_temp where  transactionid=? and eventid=?",new String[]{tid,eid});
	DbUtil.executeUpdateQuery("delete from event_reg_locked_tickets  where  tid=? and eventid=?",new String[]{tid,eid});
	
	
	}
	
	
	public static List getBLockedSeatsData(String eid,String sectionid,String eventdate){
		List list=new ArrayList();
		String query="select seatindex from event_reg_block_seats_temp where eventid=?";
		list=DbUtil.getValues(query, new String[]{eid});
		System.out.println("the list is::"+list);
	return list;
	}
	
	public static List getBLockedSeatsEventData(String eid,String eventdate){
		List list=new ArrayList();
		String query="select seatindex from event_reg_block_seats_temp where eventid=? and eventdate=?";
		list=DbUtil.getValues(query, new String[]{eid,eventdate});
		System.out.println("the list is::"+list);
	return list;
	}
	
	
	public static  String getAllSeatColors(String eid)
	{	
		JSONArray resultarray=new JSONArray();
		String query="select  seatindex, color  from event_seating a,seat_groups b where a.eventid=b.eventid and a.seat_groupid=b.seat_groupid and a.eventid=?";
		DBManager dbm=new DBManager();
		StatusObj sbj=dbm.executeSelectQuery(query,new String[]{eid});
		for(int i=0;i<sbj.getCount();i++){
			JSONObject eachObject=new JSONObject();
			try{
			eachObject.put(dbm.getValue(i,"seatindex",""), dbm.getValue(i,"color",""));
			}catch(Exception e){}
			resultarray.put(eachObject);
		}
		
		return resultarray.toString();
	}
	
	
	
}