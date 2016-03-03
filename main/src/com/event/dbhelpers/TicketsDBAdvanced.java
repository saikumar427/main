package com.event.dbhelpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eventbee.general.DBQueryObj;
import com.eventbee.general.DbUtil;
import com.eventbee.general.DbUtilExtesion;
import com.eventbee.general.StatusObj;

public class TicketsDBAdvanced {
	
	
	public static JSONObject shiftGroupPosition(JSONObject data,String type,String eid){
		JSONObject result=new JSONObject();
		try {
			String id=data.getString("itemid");
			String grpid=data.getString("descid");
			JSONArray grps=data.getJSONArray("grps");
              String srcid=data.getString("srcid");
			
		if("gl".equals(type)){grpid=TicketsDB.createNewEmptyGroup(eid);
		String update="update  group_tickets set ticket_groupid=?::text where price_id=?::text and ticket_groupid=?::text";
		DbUtil.executeUpdateQuery(update,new String[]{grpid,id,srcid});
		}
		
		String val="";
		int pos=0;
		val=grps.length()>0?
				DbUtil.getVal("Select min(position)  from event_ticket_groups where eventid=?::text and ticket_groupid in(select id ::text from (select UNNEST(ARRAY"+grps.toString()+") as id) a)", new String[]{eid})
				:
				DbUtil.getVal("Select max(position)+1 from event_ticket_groups where eventid=?::text ", new String[]{eid});
				pos=Integer.parseInt(val.trim());
						
			
			JSONArray grpsn=new JSONArray();                                
            grpsn.put(0, grpid);
            for(int i=0;i<grps.length();i++)
            	grpsn.put(grps.get(i)+"");
            
            System.out.println("groups sn is::"+grpsn);
			
			DBQueryObj []dbqueries=new DBQueryObj[grpsn.length()] ;
			for(int i=0;i<grpsn.length();i++)
			dbqueries[i] = new DBQueryObj("update event_ticket_groups set position=cast(? as integer)  where ticket_groupid=?::text and eventid=?::text ",new String[]{(pos++)+"",grpsn.getString(i),eid});
			
			StatusObj s = DbUtilExtesion.executeUpdateBatchQueries(dbqueries);	
			if(s.getStatus()&& s.getCount()!=0 && s.getCount()<=grpsn.length())
				result.put("status", "success");
			     result.put("gid", grpid);	
		} catch (Exception e) {
			System.out.println("error"+e.getMessage());e.printStackTrace();
			
		}
		return result;
	}
	public static JSONObject shiftTicketPosition(JSONObject data,String type,String eid){
		JSONObject result=new JSONObject();
		try {
			String id=data.getString("itemid");
			String srcgrpid=data.getString("srcid");
			String descgrpid=data.getString("descid");
			JSONArray tkts=data.getJSONArray("tkts");
			
		if("lg".equals(type)){
		String deleteq="delete group_tickets from where ticket_groupid =? and price_id=? ";
		DbUtil.executeUpdateQuery(deleteq,new String[]{srcgrpid,id});
		deleteq="delete event_ticket_groups from where ticket_groupid =? and eventid=? ";
		DbUtil.executeUpdateQuery(deleteq,new String[]{srcgrpid,eid});
		}
		
	  else if("ogg".equals(type)){
			String update="update  group_tickets set ticket_groupid =? where  price_id=? ";
			DbUtil.executeUpdateQuery(update,new String[]{descgrpid,id});			
		}
		
		String val="";
		int pos=0;
		val=tkts.length()>0?
				DbUtil.getVal("Select min(position) from group_tickets where ticket_groupid=? and price_id in (select id ::text from (select UNNEST(ARRAY"+tkts.toString()+") as id) a) ", new String[]{descgrpid})
				:
				DbUtil.getVal("Select max(position)+1 from group_tickets where ticket_groupid=? ", new String[]{descgrpid});
			System.out.println("valu"+val);
				pos=Integer.parseInt(val.trim());
						
						
			JSONArray tktsn=new JSONArray();                                
           tktsn.put(id);
            for(int i=0;i<tkts.length();i++)
            	tktsn.put(tkts.get(i)+"");
            
        System.out.println("tktsn is:::"+tktsn);
        DBQueryObj []dbqueries=new DBQueryObj[tktsn.length()] ;
        for(int i=0;i<tktsn.length();i++)
        dbqueries[i] = new DBQueryObj("update group_tickets set position=cast(? as integer) where  price_id =?::varchar and ticket_groupid=?::varchar ",new String[]{(pos++)+"",tktsn.getString(i),descgrpid});
			
			
			StatusObj s = DbUtilExtesion.executeUpdateBatchQueries(dbqueries);
			System.out.println("update count"+s.getCount());
			if(s.getStatus() && s.getCount()!=0 && s.getCount()<=tktsn.length())
				result.put("status", "success");
			     result.put("gid", descgrpid);	
			     
		} catch (Exception e) {
			System.out.println("error"+e.getMessage());
			e.printStackTrace();
		}
		return result;
		
	}

}
