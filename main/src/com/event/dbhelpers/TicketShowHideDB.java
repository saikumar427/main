package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class TicketShowHideDB {
	public static void update(String type,ArrayList seltickets, String eid){
		String ticketId = (String) seltickets.get(0);
		if("Y".equals(type))
			DbUtil.executeUpdateQuery("update price set showyn='Y' where evt_id=CAST(? AS BIGINT) and price_id=to_number(?,'9999999999999')",new String[]{eid,ticketId});
		else
			DbUtil.executeUpdateQuery("update price set showyn='N' where evt_id=CAST(? AS BIGINT) and price_id=to_number(?,'9999999999999')",new String[]{eid,ticketId});
		
		/*DbUtil.executeUpdateQuery("update price set showyn='N' where evt_id=CAST(? AS BIGINT)",new String[]{eid});
		for (int i = 0; i < seltickets.size(); i++) {
		String ticketId = (String) seltickets.get(i);
		DbUtil.executeUpdateQuery("update price set showyn='Y' where evt_id=CAST(? AS BIGINT) and price_id=to_number(?,'9999999999999')",new String[]{eid,ticketId});
		}	*/	
	}
	public static ArrayList getSelectedTickets(String eid) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList selt = new ArrayList();
		String QUERY = "select price_id from price  where evt_id=CAST(? AS BIGINT) and showyn is null or showyn='Y'";
		statobj=dbmanager.executeSelectQuery(QUERY,new String []{eid});
		if(statobj.getStatus()){
			for (int i = 0; i < statobj.getCount(); i++) {				
				selt.add(dbmanager.getValue(i,"price_id",""));
			}
		}
		return selt;
	}
	public static ArrayList<String> getSelectedTicketsForDates(String eid,String eventdate){
		ArrayList<String> tickets=new ArrayList<String>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String QUERY = "select a.price_id from price a where a.evt_id=CAST(? AS BIGINT) and" +
				" a.price_id not in(select ticketid from reccurringevent_ticketdetails" +
				" where eventid=CAST(? AS BIGINT) and eventdate=? and displayshowyn='N')";
		statobj=dbmanager.executeSelectQuery(QUERY,new String []{eid,eid,eventdate});
		if(statobj.getStatus()){
			for (int i = 0; i < statobj.getCount(); i++) {				
				tickets.add(dbmanager.getValue(i,"price_id",""));
			}
		}
		return tickets;
	}
	public static ArrayList<Entity> getEventDates(String eid){
		ArrayList<Entity> eventdates=new ArrayList<Entity>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		/*String QUERY = "select date_display from event_dates where eventid=to_number(?,'999999999999999') and" +
				" (zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS') as text) as time ))>=current_date " +
				"order by (zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS') as text) as time ))";*/
		String QUERY= " select to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00')," +
        " 'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY HH12:MI AM') as date_display " +
        " from event_dates where eventid=CAST(? AS BIGINT) order by date_key ";

		statobj=dbmanager.executeSelectQuery(QUERY, new String[]{eid});
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
				eventdates.add(new Entity(dbmanager.getValue(i,"date_display", ""),dbmanager.getValue(i, "date_display", "")));
			}
			
		}
		return eventdates;
	}
	public static void updateForRecurring(String eid,String eventdate,String ticketid,String show){
		DBManager dbmanger=new DBManager();
		StatusObj statobj=dbmanger.executeSelectQuery("select ticketid from reccurringevent_ticketdetails where " +
				"eventid=CAST(? AS BIGINT) and ticketid=to_number(?,'99999999999') and eventdate=?", new String[]{eid,ticketid,eventdate});
		if(statobj.getStatus()){
			System.out.println("record exists");
			DbUtil.executeUpdateQuery("update reccurringevent_ticketdetails set displayshowyn=?" +
					" where eventid=CAST(? AS BIGINT) and ticketid=to_number(?,'99999999999') and eventdate=?", new String[]{show,eid,ticketid,eventdate});
		}
		else{
			System.out.println("record not exists");
			DbUtil.executeUpdateQuery("insert into reccurringevent_ticketdetails(eventid,ticketid,eventdate,displayshowyn,soldqty)" +
					" values(CAST(? AS BIGINT),to_number(?,'999999999999'),?,?,0)", new String[]{eid,ticketid,eventdate,show});
		}
	}
	
	public static HashMap<String, ArrayList<Entity>> getVolumeTickets(String eid){
		ArrayList<Entity> voltickets=new ArrayList<Entity>();
		ArrayList<String> selvoltickets=new ArrayList<String>();
		HashMap volumetickets = new HashMap();
		String query="select ticketid,ticket_name,showhide from groupdeal_tickets where eventid=? order by ticketid";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query, new String[]{eid});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
				if(db.getValue(i, "showhide","").equals("Y"))
					selvoltickets.add(db.getValue(i, "ticketid",""));
				//selvoltickets.add(new Entity(db.getValue(i, "ticketid",""),db.getValue(i,"ticket_name","")));
				//else
					voltickets.add(new Entity(db.getValue(i, "ticketid",""),db.getValue(i,"ticket_name","")));
			}
		}
		volumetickets.put("voltickets", voltickets);
		volumetickets.put("selvoltickets", selvoltickets);
		return volumetickets;
	  }
	
	  public static void saveVolumeTickets(String eid,List selvoltickets){
		  DbUtil.executeUpdateQuery("update groupdeal_tickets set showhide='N' where eventid=?",new String[]{eid});
		  for (int i = 0; i < selvoltickets.size(); i++) {
			  String ticketId = (String) selvoltickets.get(i);
			  DbUtil.executeUpdateQuery("update groupdeal_tickets set showhide='Y' where ticketid=CAST(? AS INTEGER) and eventid=?",new String[]{ticketId,eid});
		  }
		  
		  /*try {
				if(voltickets!=null){
					StringBuffer sb = new StringBuffer();
					for(int i=0;i<voltickets.size() ;i++){
						if(i==voltickets.size()-1)
							sb.append(voltickets.get(i).getKey());
						else
							sb.append(voltickets.get(i).getKey()+",");
					}
				
					String queryY = "update groupdeal_tickets set showhide='Y' where ticketid not in("+sb+") and eventid=?";
					DbUtil.executeUpdateQuery(queryY,new String[]{eid});
					String queryN = "update groupdeal_tickets set showhide='N' where ticketid in("+sb+") and eventid=?";
					DbUtil.executeUpdateQuery(queryN,new String[]{eid});
				}else DbUtil.executeUpdateQuery("update groupdeal_tickets set showhide='Y' where eventid=?",new String[]{eid});
			} catch (Exception ex) {
				// TODO: handle exception
				System.out.println("Exception In Edit Volume Tickets In EventManage Content ERROR: "+ex.getMessage());
			}*/
		}
}
