package com.eventmanage.groupticketing.dbhelpers;

import java.util.ArrayList;

import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class GroupTicketsShowHideDB {
	public static ArrayList getSelectedTickets(String eid) {
		
		// System.out.println("we r in getselected tickets");
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList selt = new ArrayList();
		String QUERY = "select ticketid from groupdeal_tickets where eventid=to_number(?,'999999999') and show_status is null or show_status='Y'";
		statobj=dbmanager.executeSelectQuery(QUERY,new String []{eid});
		// System.out.println(statobj.getStatus());
		if(statobj.getStatus()){
			for (int i = 0; i < statobj.getCount(); i++) {				
				selt.add(dbmanager.getValue(i,"ticketid",""));
			}
		}
		return selt;
	}

	public static void update(ArrayList selgrouptickets, String eid){
		StatusObj statobj=null;
		
		statobj=DbUtil.executeUpdateQuery("update groupdeal_tickets set show_status='N' where eventid=to_number(?,'999999999')",new String[]{eid});
		// System.out.println("status of first update in groupticketsshowhidedb"+statobj.getStatus());
		
		for (int i = 0; i < selgrouptickets.size(); i++) {
		String ticketId = (String) selgrouptickets.get(i);
		statobj=DbUtil.executeUpdateQuery("update groupdeal_tickets set show_status='Y'" +
				" where eventid=to_number(?,'999999999') and ticketid=to_number(?,'999999999')",
				new String[]{eid,ticketId});
		// System.out.println("status of second update in groupticketsshowhidedb"+statobj.getStatus());
		}
		
	}
	
		public static ArrayList getEventTickets(String eid){
			
			
		/*	String ticketSelQuery="select pr.price_id,pr.ticket_name as ticketname,etg.groupname  as groupname from price pr," +
			"event_ticket_groups etg,group_tickets gt where gt.ticket_groupid=etg.ticket_groupid and pr." +
			"price_id=to_number(gt.price_id,'9999999999') and  etg.eventid=? and " +
			"pr.evt_id=to_number(?,'99999999999') order by etg.position,gt.position";*/
			
			String groupticketSelQuery="select ticketid,name from groupdeal_tickets where eventid=to_number(?,'999999999')" ;
			
			return getGroupTicketsData(groupticketSelQuery,eid);
		}
		
		public static ArrayList getGroupTicketsData(String query,String eid){
			DBManager dbmanager=new DBManager();
			StatusObj statobj=null;
			ArrayList alltickets=new ArrayList();
			try{
				statobj=dbmanager.executeSelectQuery(query,new String []{eid});
				int count1=statobj.getCount();
				if(statobj.getStatus()&&count1>0){
					for(int k=0;k<count1;k++){
						String ticketId=dbmanager.getValue(k,"ticketid","");
						String ticketName=dbmanager.getValue(k,"name","");
						alltickets.add(new Entity(ticketId,ticketName));
					}
				}
			}//End of try block
			catch(Exception e){
				System.out.println("There is an Exception while executing the ticket selection query" +
						"for the eventid"+eid+"in Disccounts Management"+e.getMessage());
			}
			return alltickets;
		}

}
