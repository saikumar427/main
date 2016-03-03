package com.eventmanage.seating.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class SeatingAddManualDB {
	public static List getOnHoldSeatsTid(String eid,String eventdate,String tid){
		String query="";
		DBManager Db=new DBManager();
		List onholdseats=new ArrayList();
		query="select distinct seatindex from event_reg_block_seats_temp where eventid=? and transactionid!=? and seatindex NOT IN (select seatindex from seat_booking_status where eventid=CAST(? AS BIGINT))";
		if(!"".equals(eventdate)){
			query="select distinct seatindex from event_reg_block_seats_temp where eventid=? and transactionid!=? and eventdate=? and seatindex NOT IN (select seatindex from seat_booking_status where eventid=CAST(? AS BIGINT) and eventdate=?)";
			onholdseats=DbUtil.getValues(query,new String[]{eid,tid,eventdate,eid,eventdate});
		}
		else
			onholdseats=DbUtil.getValues(query,new String[]{eid,tid,eid});
			return onholdseats; 
	}
	
	public static HashMap GetVenueSections(String venueid){
		HashMap sectiondetails=new HashMap();
		
		ArrayList sectionids=new ArrayList();
		ArrayList sectionnames=new ArrayList();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery("select section_id,sectionname,row_header,col_header from venue_sections where venue_id=to_number(?,'9999999999')", new String[]{venueid});
		for(int i=0;i<sb.getCount();i++){
			HashMap Sectionheader=new HashMap();
			sectionids.add(i,db.getValue(i, "section_id", ""));
			sectionnames.add(i,db.getValue(i, "sectionname", ""));
			Sectionheader.put("rowheader",db.getValue(i,"row_header",""));
			Sectionheader.put("columnheader",db.getValue(i,"col_header",""));
			sectiondetails.put("header_"+db.getValue(i, "section_id", ""), Sectionheader);
		}
		sectiondetails.put("sectionid",sectionids);
		sectiondetails.put("sectionname",sectionnames);
		return sectiondetails;
	}
	
	public static HashMap getsectionheader(String venueid,String sectionid){
		HashMap Sectionheader=new HashMap();
		DBManager db=new DBManager();
		StatusObj headersb=db.executeSelectQuery("select row_header,col_header from venue_sections where venue_id=to_number(?,'99999999') and section_id=to_number(?,'99999999')",new String[]{venueid,sectionid});
		if(headersb.getStatus()){
			Sectionheader.put("rowheader",db.getValue(0,"row_header",""));
			Sectionheader.put("columnheader",db.getValue(0,"col_header",""));
		}
		
		return Sectionheader;
	}
	
	
	public static HashMap GetVenueLayout(String venueid){
		HashMap venuelayout=new HashMap();
		DBManager db=new DBManager();
		StatusObj layoutsb=db.executeSelectQuery("select layout_display,layout_display_path,layout_display_link from seating_venues where venue_id=to_number(?,'999999999')",new String[]{venueid});
		if(layoutsb.getStatus()){
			venuelayout.put("layout",db.getValue(0,"layout_display",""));
			venuelayout.put("path",db.getValue(0,"layout_display_path",""));
			venuelayout.put("link",db.getValue(0,"layout_display_link",""));
		}
		
		return venuelayout;
	}
	
	public static ArrayList getAllotedticketnames(String eid,String section_id,String color){
	//String query="select ticket_name from price where evt_id=to_number(?,'999999999') and price_id in (select ticketid from seat_tickets where eventid=? and section_id=to_number(?,'999999999') and seat_groupid in (select seat_groupid from seat_groups where color=? and eventid=? and section_id=to_number(?,'999999999')))";
		String query="select ticket_name from price where evt_id=CAST(? AS BIGINT) and price_id in (select ticketid from seat_tickets where eventid=CAST(? AS BIGINT)  and seat_groupid in (select seat_groupid from seat_groups where color=? and eventid=CAST(? AS BIGINT)))";	
	ArrayList ticketname=new ArrayList();
	DBManager db=new DBManager();
		//StatusObj sb=db.executeSelectQuery(query,new String[]{eid,eid,section_id,color,eid,section_id});
	StatusObj sb=db.executeSelectQuery(query,new String[]{eid,eid,color,eid});
		if(sb.getStatus()){
			
			for(int i=0;i<sb.getCount();i++){
				ticketname.add(i,db.getValue(i,"ticket_name",""));
			}
		}
		return ticketname;
	}
	
	public static ArrayList getAllotedticketid(String eid,String section_id,String color){
	//String query="select ticketid from seat_tickets where eventid=? and section_id=to_number(?,'999999999') and seat_groupid in (select seat_groupid from seat_groups where color=? and eventid=? and section_id=to_number(?,'999999999'))";
		String query="select ticketid from seat_tickets where eventid=CAST(? AS BIGINT) and  seat_groupid in (select seat_groupid from seat_groups where color=? and eventid=CAST(? AS BIGINT))";	
	ArrayList ticketid=new ArrayList();
	DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query,new String[]{eid,color,eid});
		if(sb.getStatus()){
			
			for(int i=0;i<sb.getCount();i++){
				ticketid.add(i,db.getValue(i,"ticketid",""));
			}
		}
		return ticketid;
	}
	public static HashMap getAllTicketNameIds(String eid){
		HashMap ticketname=new HashMap();
		String query="select ticket_name,price_id from price where evt_id=CAST(? AS BIGINT)";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query,new String[]{eid});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
				ticketname.put(db.getValue(i,"price_id",""),db.getValue(i,"ticket_name",""));
			}
		}
		return ticketname;
	}
	
	public static HashMap getAllcotedTicketidForSeatgroupid(String eventid,String venueid){
		String query="select seat_groupid,ticketid from seat_tickets where eventid=CAST(? AS BIGINT) and venue_id=to_number(?,'999999999') order by seat_groupid";
		HashMap hm=new HashMap();
		DBManager db= new DBManager();
		StatusObj sb=db.executeSelectQuery(query,new String[]{eventid,venueid});
		if(sb.getStatus()&&sb.getCount()>0){
			for(int i=0;i<sb.getCount();i++){
				String seat_grpid=db.getValue(i, "seat_groupid", "");
				ArrayList ticketid=new ArrayList();
				ticketid.add(db.getValue(i, "ticketid", ""));
				try{
					if(hm.containsKey(seat_grpid)){
						ArrayList tktid=(ArrayList) hm.get(seat_grpid);
						tktid.add(db.getValue(i, "ticketid", ""));
						hm.put(seat_grpid, tktid);
					}else{
						hm.put(seat_grpid, ticketid);
						
					}
					
				}catch(Exception e){System.out.println("exception in ticketids for each seat groupid is"+e.getMessage());}
				
			}
			
		}
		return hm;
	}
	
	
	public static HashMap<String, String> getAllotedSeats(String eid,String sectionid){
		HashMap<String, String> seatIndeces=new HashMap<String, String>();
		DBManager dbmanager=new DBManager();
		StatusObj sb=dbmanager.executeSelectQuery("select seatindex,seat_groupid from event_seating where eventid=CAST(? AS BIGINT)", new String[]{eid});
		for(int i=0;i<sb.getCount();i++){
			seatIndeces.put(dbmanager.getValue(i, "seatindex", ""), dbmanager.getValue(i, "seat_groupid", ""));
		}
		return seatIndeces;
	}
	public static HashMap<String, String> getAllotedSeatColors(String eid,String sectionid){
		HashMap<String, String> seatgroups=new HashMap<String, String>();
		DBManager dbmanager=new DBManager();
		StatusObj sb=dbmanager.executeSelectQuery("select color,seat_groupid from seat_groups where eventid=CAST(? AS BIGINT)", new String[]{eid});
		seatgroups.put("-1", "white_NA");
		for(int i=0;i<sb.getCount();i++){
		seatgroups.put(dbmanager.getValue(i, "seat_groupid", ""), dbmanager.getValue(i, "color", ""));
		}
		return seatgroups;
	}
	
	public static List getSoldOutSeats(String eid,String sectionid,String eventdate){
		String query="";
		List seats=new ArrayList();
		//query="select seatindex from seat_booking_status where eventid=to_number(?,'99999999999') and section_id=to_number(?,'9999999999')";
		query="select seatindex from seat_booking_status where eventid=CAST(? AS BIGINT)";
		if(!"".equals(eventdate)){
			query="select seatindex from seat_booking_status where eventid=CAST(? AS BIGINT) and eventdate=?";
			//seats=DbUtil.getValues(query, new String[]{eid,eventdate,sectionid});
			seats=DbUtil.getValues(query, new String[]{eid,eventdate});
		}
		else
			//seats=DbUtil.getValues(query, new String[]{eid,sectionid});
			seats=DbUtil.getValues(query, new String[]{eid});
		return seats;
	}
	public static List getOnHoldSeats(String eid,String eventdate){
		String query="";
		DBManager Db=new DBManager();
		List onholdseats=new ArrayList();
		query="select distinct seatindex from event_reg_block_seats_temp where eventid=? and seatindex NOT IN (select seatindex from seat_booking_status where eventid=CAST(? AS BIGINT))";
		if(!"".equals(eventdate)){
			query="select distinct seatindex from event_reg_block_seats_temp where eventid=? and eventdate=? and seatindex NOT IN (select seatindex from seat_booking_status where eventid=CAST(? AS BIGINT) and eventdate=?)";
			onholdseats=DbUtil.getValues(query,new String[]{eid,eventdate,eid,eventdate});
		}
		else
			onholdseats=DbUtil.getValues(query,new String[]{eid,eid});
			return onholdseats; 
	}
	
	public static HashMap getSection(String venueid){
		HashMap allsections=new HashMap();
		ArrayList sectionids=new ArrayList();
		ArrayList sectionnames=new ArrayList();
		HashMap sectionseats=getSectionSeats(venueid);
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery("select * from venue_sections where venue_id=to_number(?,'9999999999')", new String[]{venueid});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
				HashMap section=new HashMap();
				section.put("Sectionid",db.getValue(i, "section_id", ""));
				section.put("sectionname",db.getValue(i,"sectionname", ""));
				section.put("No_of_rows",db.getValue(i, "noofrows", ""));
				section.put("No_of_cols",db.getValue(i,"noofcols", ""));
				
				
				section.put("background_image",db.getValue(i,"background_image", ""));
				section.put("seat_image_width",db.getValue(i,"seat_image_width", ""));
				section.put("seat_image_height",db.getValue(i,"seat_image_height", ""));
				section.put("layout_css",db.getValue(i,"layout_css", ""));
				
				HashMap Sectionheader=new HashMap();
				sectionids.add(i,db.getValue(i, "section_id", ""));
				sectionnames.add(i,db.getValue(i, "sectionname", ""));
				Sectionheader.put("rowheader",db.getValue(i,"row_header",""));
				Sectionheader.put("columnheader",db.getValue(i,"col_header",""));
				section.put("Seats",sectionseats.get(db.getValue(i, "section_id", "")));
				allsections.put(db.getValue(i, "section_id", ""),section);
				allsections.put("header_"+db.getValue(i, "section_id", ""), Sectionheader);
				
			}
			allsections.put("sectionid",sectionids);
			allsections.put("sectionname",sectionnames);
		}
		
		return allsections;
	}
	public static HashMap getSectionSeats(String venueid){
		HashMap sectionseat=new HashMap();
		DBManager db=new DBManager();
		String query="select row_id,col_id,seatindex,isseat,seatcode,section_id from venue_seats where venue_id=to_number(?,'9999999999') and isseat='Y'";
		StatusObj sb=db.executeSelectQuery(query, new String[]{venueid});
		if(sb.getStatus()){
		for(int i=0;i<sb.getCount();i++){
			String sectionid=db.getValue(i, "section_id", "");
		  if(sectionseat.containsKey(sectionid)){
				ArrayList seats=(ArrayList) sectionseat.get(sectionid);
				HashMap seat=new HashMap();
				seat.put("row_id",db.getValue(i, "row_id",""));
				seat.put("col_id",db.getValue(i, "col_id",""));
				seat.put("seatindex",db.getValue(i, "seatindex",""));
				seat.put("isseat",db.getValue(i, "isseat",""));
				seat.put("seatcode",db.getValue(i, "seatcode",""));
				seats.add(seat);
			}else{
			ArrayList seats=new ArrayList();
			HashMap seat=new HashMap();
			seat.put("row_id",db.getValue(i, "row_id",""));
			seat.put("col_id",db.getValue(i, "col_id",""));
			seat.put("seatindex",db.getValue(i, "seatindex",""));
			seat.put("isseat",db.getValue(i, "isseat",""));
			seat.put("seatcode",db.getValue(i, "seatcode",""));
			seats.add(seat);
			sectionseat.put(sectionid,seats);
		   }
		}
	}	
		return sectionseat;
	} 
	
	public static HashMap getTicketGroupdetails(String eid){
		DBManager dbmanager=new DBManager();
		HashMap groupmap=new HashMap();
		
		StatusObj sb=dbmanager.executeSelectQuery("select ticket_groupid,groupname from event_ticket_groups where eventid=? and groupname!=?",new String[]{eid,""});
		if(sb.getStatus()){
				for(int i=0;i<sb.getCount();i++){
					groupmap.put(dbmanager.getValue(i,"ticket_groupid",""),dbmanager.getValue(i,"groupname",""));
				}
		}
		//System.out.println(groupmap.toString());
		return groupmap;
	}
	
	public static HashMap getTicketSeatColors(String eid){
		DBManager dbmanager=new DBManager();
		ArrayList seatcolors=new ArrayList();
		HashMap<String,ArrayList<String>> tickets=new HashMap<String,ArrayList<String>>();
		StatusObj sb=dbmanager.executeSelectQuery("select distinct a.color as color,(select c.price_id from price c where c.price_id=b.ticketid) as ticketid " +
				"from seat_groups a,seat_tickets b where a.seat_groupid=b.seat_groupid and a.eventid=CAST(? AS BIGINT)  order by color",new String[]{eid});
		//SeatColor seatcolor=new SeatColor();
		ArrayList<String> colors;
		for(int i=0;i<sb.getCount();i++)
		{
			String ticket=dbmanager.getValue(i, "ticketid", "");
			if(tickets.containsKey(ticket)){
				colors=(ArrayList<String>)tickets.get(ticket);
				colors.add(dbmanager.getValue(i, "color", ""));
			}else{
				colors=new ArrayList<String>();
				colors.add(dbmanager.getValue(i, "color", ""));
				tickets.put(ticket, colors);
			}
		}
		/*JSONObject json=new JSONObject();
		try{
		json.put("hmap", tickets);
		}
		catch (Exception e) {
		}*/
		//System.out.println(tickets.toString());
		return tickets;
	}
	

	public static ArrayList getAllticketid(String eid){
		String query="select distinct ticketid from seat_tickets where eventid=CAST(? AS BIGINT) and seat_groupid in (select seat_groupid from seat_groups where eventid=CAST(? AS BIGINT) )";
		ArrayList ticketid=new ArrayList();
		DBManager db=new DBManager();
			StatusObj sb=db.executeSelectQuery(query,new String[]{eid,eid});
			if(sb.getStatus()){
				
				for(int i=0;i<sb.getCount();i++){
					ticketid.add(i,db.getValue(i,"ticketid",""));
				}
			}
			
			return ticketid;
		}
	public static String getStatus(String eid,String tid,String eventdate,String allseats){
		String seating_temp="select * from event_reg_block_seats_temp where eventid=? and seatindex in ("+allseats+")";
		String booked_seats="select * from seat_booking_status where eventid=CAST(? AS BIGINT) and seatindex in ("+allseats+")";
		 StatusObj booked_status;
		 StatusObj temp_status;
		DBManager db=new DBManager();
		if(!"".equals(eventdate)||!" ".equals(eventdate)){
			if(!"".equals(tid)){
				seating_temp=seating_temp+" and transactionid!=? and eventdate=?";
				booked_seats=booked_seats+" and tid!=? and eventdate=?";
				booked_status=db.executeSelectQuery(booked_seats,new String[]{eid,tid,eventdate});
				temp_status=db.executeSelectQuery(seating_temp,new String[]{eid,tid,eventdate});
			}
			else{
				seating_temp=seating_temp+" and eventdate=?";
				booked_seats=booked_seats+" and eventdate=?";
					booked_status=db.executeSelectQuery(booked_seats,new String[]{eid,eventdate});
					temp_status=db.executeSelectQuery(seating_temp,new String[]{eid,eventdate});
			}
		}
		else{
		if(!"".equals(tid)){
			seating_temp=seating_temp+" and transactionid!=?";
			booked_seats=booked_seats+" and tid!=?";
			booked_status=db.executeSelectQuery(booked_seats,new String[]{eid,tid});
			 temp_status=db.executeSelectQuery(seating_temp,new String[]{eid,tid});
		 }
		 else{
			booked_status=db.executeSelectQuery(booked_seats,new String[]{eid});
		 temp_status=db.executeSelectQuery(seating_temp,new String[]{eid});

		 }
		 }
		 

		if(temp_status.getStatus() || booked_status.getStatus()){
		return "Failed";
		}
		else{
		return "success";
		}
		}
	public static void deleteTempBlockSeats(String eid){
		/*DbUtil.executeUpdateQuery("delete from event_reg_block_seats_temp where transactionid in (select tid from event_reg_details_temp where eventid=? and transactiondate < (select now()- interval '20 minutes') and lower(current_action) in ('profile page','payment section') );",new String[]{eid});
		DbUtil.executeUpdateQuery("delete from event_reg_block_seats_temp where transactionid in (select tid from event_reg_details_temp where eventid=? and transactiondate < (select now()- interval '60 minutes') and lower(current_action)='eventbee' );",new String[]{eid});
*/	
		String timeout=DbUtil.getVal("select value from config where config_id =(select config_id from eventinfo where eventid=?::bigint) and name='timeout' ",new String[]{eid});
    	timeout=timeout==null||"".equals(timeout) ?"14":timeout;
    	try{timeout=1+Integer.parseInt(timeout)+"";}catch(Exception e){timeout="15";}
  
		DbUtil.executeUpdateQuery("delete from event_reg_block_seats_temp where blocked_at  <(select now()- interval ' "+timeout+" minutes') and eventid=?;",new String[]{eid});
	    }
	
	
	public static String getTimeOut(String eid){
		String timeout=DbUtil.getVal("select value from config where config_id =(select config_id from eventinfo where eventid=?::bigint) and name='timeout' ",new String[]{eid});
		timeout=timeout==null||"".equals(timeout) ?"14":timeout;
		try{Integer.parseInt(timeout);}catch(Exception e){timeout="14";};
		return timeout;
	}
}
