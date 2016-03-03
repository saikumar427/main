package com.event.dbhelpers;

import java.util.HashMap;
import com.eventbee.beans.DateTime;
import com.eventbee.general.DBManager;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;

public class EventInfoDB {
	 final static String FILE_NAME="EventTicketDB";
	 final String EVENT_INFO_QUERY="select getMemberPref(mgr_id||'','pref:myurl','') as username,venue,starttime,endtime,eventname,status,email,mgr_id,evt_level,phone, to_char(start_date,'mm/dd/yyyy')  as startdate, to_char(end_date,'mm/dd/yyyy') as enddate,trim(to_char(start_date,'Day')) ||', '|| to_char(start_date,'Month DD, YYYY') as start_date,trim(to_char(end_date,'Day')) ||', '|| to_char(end_date,'Month DD, YYYY') as end_date,city,state,address1,address2,country "
	   +" from eventinfo  where   eventid=CAST(? AS BIGINT)";
	 final String MGR_INFO_QUERY="select first_name,last_name,phone,mobile from user_profile "
         +" where user_id=? ";

	 public StatusObj getEventInfo(String EventId,HashMap evtInfo){
	       boolean status=fillEventInfo(evtInfo, EventId);
	      	       return new StatusObj(status, "", "");
	 }

	 public boolean fillEventInfo(HashMap evtInfo,String eventid){
		 System.out.println("fillEventInfo eventid: "+eventid);
		    boolean status=false;
	        String userid="";
		//Connection con=null;
	        //java.sql.PreparedStatement pstmt=null;
	        //java.sql.PreparedStatement pstmtX=null;
		try{
	                //con=EventbeeConnection.getReadConnection("event");
	                //pstmt=con.prepareStatement(EVENT_INFO_QUERY);
	                //pstmt.setInt(1,Integer.parseInt(eventid));
	                //ResultSet rs=pstmt.executeQuery();
			DBManager dbmanager = new DBManager();
			StatusObj statobj=null;
			statobj=dbmanager.executeSelectQuery(EVENT_INFO_QUERY,new String []{eventid});
			int count=statobj.getCount();
	                if(statobj.getStatus()&&count>0){
	                        
				            evtInfo.put("EVENTNAME", dbmanager.getValue(0,"eventname",""));
	                        evtInfo.put("STATUS", dbmanager.getValue(0,"status",""));
	                        evtInfo.put("EMAIL",dbmanager.getValue(0,"email",""));
				            evtInfo.put("STARTDATE",dbmanager.getValue(0,"startdate",""));
				            evtInfo.put("ENDDATE",dbmanager.getValue(0,"enddate",""));
				            evtInfo.put("StartDate_Day",dbmanager.getValue(0,"start_date",""));
				            evtInfo.put("EndDate_Day",dbmanager.getValue(0,"end_date",""));
				            evtInfo.put("STARTTIME", DateTime.getTimeAM(dbmanager.getValue(0,"starttime","")));
				            evtInfo.put("ENDTIME", DateTime.getTimeAM(dbmanager.getValue(0,"endtime","")));
	                        userid=dbmanager.getValue(0,"mgr_id","");
				            evtInfo.put("LOCATION",GenUtil.getCSVData(new String[]{dbmanager.getValue(0,"city",""),dbmanager.getValue(0,"state",""),dbmanager.getValue(0,"country","")}));
				            evtInfo.put("VENUE",GenUtil.getCSVData(new String[]{dbmanager.getValue(0,"venue",""),dbmanager.getValue(0,"address1",""),dbmanager.getValue(0,"address2","")}));
	                        evtInfo.put("FULLADDRESS",GenUtil.getCSVData(new String[]{dbmanager.getValue(0,"venue",""),dbmanager.getValue(0,"address1",""),dbmanager.getValue(0,"address2",""),dbmanager.getValue(0,"city",""),dbmanager.getValue(0,"state",""),dbmanager.getValue(0,"country","")}));
	                        evtInfo.put("ADDRESS",GenUtil.getCSVData(new String[]{dbmanager.getValue(0,"address1",""),dbmanager.getValue(0,"address2","")}));
	                        evtInfo.put("EVENTLEVEL",dbmanager.getValue(0,"evt_level",""));
			       }
			//rs.close();
			//pstmt.close();
	        //pstmt=con.prepareStatement(MGR_INFO_QUERY);
	        //pstmt.setString(1,userid);
	       //rs=pstmt.executeQuery();
	        statobj=dbmanager.executeSelectQuery(MGR_INFO_QUERY,new String []{userid});
	        int count1=statobj.getCount();
	        if(statobj.getStatus()&&count1>0){
				            evtInfo.put("FIRSTNAME",dbmanager.getValue(0,"first_name",""));
	                        evtInfo.put("LASTNAME",dbmanager.getValue(0,"last_name",""));
	                        evtInfo.put("PHONE",dbmanager.getValue(0,"phone",""));
	                        evtInfo.put("MOBILE",dbmanager.getValue(0,"mobile",""));
			}
			//rs.close();
			//pstmt.close();
	         
	               	//pstmt=null;
	                status=true;
		}catch(Exception e){
	               System.out.println("Error in Fill EventInfo"+e.getMessage());
			status=false;
		}
		finally{
			try{
				//if (pstmt!=null) pstmt.close();
	                        //if(con!=null) con.close();
			}catch(Exception e){}
		}
		return status;
	    }
	  
}
