package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.event.beans.TicketURLsData;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.namedquery.NQDbHelper;

public class TicketURLsDB {
	public static ArrayList<TicketURLsData> getTicketURLdetails(String eid) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList<TicketURLsData> list = new ArrayList<TicketURLsData>();

    	String QUERY = "select code,count(*) from"
    				+"  custom_ticket_urls  where  eventid=CAST(? AS BIGINT) group by code";
    	statobj=dbmanager.executeSelectQuery(QUERY,new String []{eid});
			if(statobj.getStatus()){
				String tempCode="";
				for(int k=0;k<statobj.getCount();k++){	
					TicketURLsData Obj=new TicketURLsData();
					Obj.setCount(dbmanager.getValue(k,"count",""));
					Obj.setCode(dbmanager.getValue(k,"code",""));	
					String code=dbmanager.getValue(k,"code","");
					String ticketname=getTicketNamesforCode(eid,code);
					Obj.setTicketname(ticketname);
					list.add(Obj);
					
			}					
		}	    	
		
		return list;
	}
	public static String getEventURL(String eid){
		String ticketingURL="";
		String eventURL=DbUtil.getVal("select url from event_custom_urls where eventid=?",new String[]{eid});
		ticketingURL=eventURL+"/ticket?tc=";
		if(eventURL==null){
			String username=DbUtil.getVal("select login_name from authentication where user_id= cast((select mgr_id from eventinfo where "+
		" eventid=CAST(? AS BIGINT)) as varchar)",new String[]{eid});
			eventURL=GenUtil.get(username)+"/event?eid="+eid;	
			ticketingURL=eventURL+"&tc=";
		}
		return 	ticketingURL;
	}
	public static void insertTicketURLs(ArrayList seltickets, String eid, String code){
			for (int i = 0; i < seltickets.size(); i++) {
			String ticketId = (String) seltickets.get(i);
			DbUtil.executeUpdateQuery("insert into custom_ticket_urls(eventid, ticketid,code) values(CAST(? AS BIGINT),to_number(?,'9999999999999'),?)",new String[]{eid,ticketId,code});
			}		
	}
	
	public static void updateTicketURLs(ArrayList seltickets, String eid, String code){
		DbUtil.executeUpdateQuery("delete from custom_ticket_urls where eventid=CAST(? AS BIGINT) and code=?",new String[]{eid,code});
		for (int i = 0; i < seltickets.size(); i++) {
		String ticketId = (String) seltickets.get(i);
		DbUtil.executeUpdateQuery("insert into custom_ticket_urls(eventid, ticketid,code) values(CAST(? AS BIGINT),to_number(?,'9999999999999'),?)",new String[]{eid,ticketId,code});
		}		
	}
	public static ArrayList getSelectedTickets(String eid, String code) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList selt = new ArrayList();
		String QUERY = "select ticketid from"
			+"  custom_ticket_urls  where code=? and eventid=CAST(? AS BIGINT)";
		statobj=dbmanager.executeSelectQuery(QUERY,new String []{code,eid});
		if(statobj.getStatus()){
			for (int i = 0; i < statobj.getCount(); i++) {				
				selt.add(dbmanager.getValue(i,"ticketid",""));
			}
		}
		return selt;
	}
	
	public static String getTicketNamesforCode(String eid,String code){
		DBManager dbmanager=new DBManager();
		StringBuffer ticketname=new StringBuffer();
		String query="select code,ticketid,ticket_name from custom_ticket_urls ,price  where " +
				" eventid=CAST(? AS BIGINT) and price.price_id=ticketid " +
				"and eventid=evt_id  and code=?";
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String []{eid,code});
		if(statobj.getStatus()){
			ticketname.append(dbmanager.getValue(0,"ticket_name",""));
			for (int i = 1; i < statobj.getCount(); i++) {				
				ticketname.append(","+dbmanager.getValue(i,"ticket_name",""));
			}
		}
		
		return ticketname.toString();
	}
	public static List<String> getTicketidsforCode(String eid, String code){
		List<String> list=new ArrayList<String>();			
		try {
			String query="select distinct ticketid from custom_ticket_urls where " +
					"eventid=CAST(? AS BIGINT) and code=?";
			DBManager dbmanager=new DBManager();
			StatusObj statobj=dbmanager.executeSelectQuery(query,new String []{eid,code});
			if(statobj.getStatus()){
				for (int i = 0; i < statobj.getCount(); i++) {
					list.add(dbmanager.getValue(i,"ticketid",""));
				}
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public static String getTicketsCount(String eid){
		String count=DbUtil.getVal("select count(*) from price where evt_id=CAST(? AS BIGINT)", new String[]{eid});
		return count;
	}
	public static boolean deleteTicketURL(String eid,String code){
		boolean status;
		String salesQuery="select count(*) from event_reg_transactions where eventid=? and ticketurlcode=?";
		String deleteQuery="delete from custom_ticket_urls where eventid=CAST(? AS BIGINT) and code=?";
		String count=DbUtil.getVal(salesQuery, new String[]{eid,code});
		if(count==null || "".equals(count)) count="0";
		if(Integer.parseInt(count)<1){
			status=false;
			DbUtil.executeUpdateQuery(deleteQuery, new String[]{eid,code});
		}else 
			status=true;
		return status;
	}
}
