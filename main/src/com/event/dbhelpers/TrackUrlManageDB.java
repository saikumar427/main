package com.event.dbhelpers;

import java.util.HashMap;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONObject;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.StatusObj;

public  class TrackUrlManageDB {

	public static HashMap<String,String> getTrackUrlName(String eid,String trackcode){
		HashMap<String,String> reportMap=new HashMap<String,String>();
		String evtname=DbUtil.getVal("select eventname from eventinfo where eventid=cast(? as bigint)",new String[]{eid});
		if(evtname==null)evtname="";
		reportMap.put("evtname",evtname);
		reportMap.put("trackcode",trackcode);
		return reportMap;
	}
	
	public static HashMap<String,String> getTrackUrlDetails(String eid,String tcode,String scode){
		HashMap<String,String> hmap=new HashMap<String,String>();
	    String query="select message,photo,trackingid  from trackurls where eventid=? and trackingcode=? and secretcode=?";
	    DBManager dbm=new DBManager();
	    StatusObj sbj=dbm.executeSelectQuery(query,new String[]{eid,tcode,scode});
	    if(sbj.getStatus() && sbj.getCount()>0){
	    	hmap.put("message",dbm.getValue(0,"message",""));
	    	hmap.put("photo", dbm.getValue(0,"photo",""));
	        hmap.put("trackid",dbm.getValue(0,"trackingid",""));
	    }
		return hmap;
	}
	
	public static HashMap<String,String> getAccTrackUrlDetails(String userid,String tcode,String scode){
		HashMap<String,String> hmap=new HashMap<String,String>();
		String query="select message,photourl from accountlevel_track_partners where userid=cast(? as bigint) and trackname=? and scode=?";
		DBManager dbm=new DBManager();
		StatusObj sbj=dbm.executeSelectQuery(query,new String[]{userid,tcode,scode});
		if(sbj.getStatus() && sbj.getCount()>0){
		    	hmap.put("message",dbm.getValue(0,"message",""));
		    	hmap.put("photo", dbm.getValue(0,"photourl",""));
		}
		return hmap;
	}
	

	public static String getTrackCodePassword(String eid,String tcode,String scode){
		String password=DbUtil.getVal("select password from trackurls where eventid=? and trackingcode=? and secretcode=?", new String[]{eid,tcode,scode});
		if(password==null)password="";
		return password;
	}
	
	public static String getAcclevelTrackCodePassword(String tcode,String scode,String userid){		
		String acclevelpassword=DbUtil.getVal("select password from accountlevel_track_partners where userid=cast(? as bigint) and trackname=? and scode=?",new String[]{userid,tcode,scode});
		if(acclevelpassword==null)acclevelpassword=""; 
		return acclevelpassword;
	}
	
	public static void changePassword(String password,String eid,String trackcode,String scode){
		DbUtil.executeUpdateQuery("update trackURLs set password=? where eventid=? and trackingcode =? and secretcode=?",new String [] {password,eid,trackcode,scode});
	}
	
	public static void changeAcclevelPassword(String password,String userid,String tcode,String scode){
		DbUtil.executeUpdateQuery("update accountlevel_track_partners set password=? where userid=cast(? as bigint) and trackname=? and scode=? ",new String [] {password,userid,tcode,scode});
	}
	
	public static String trackPhoto(String eid,String tcode,String scode){
		String photo=DbUtil.getVal("select photo from trackURLs where eventid=? and trackingcode=? and secretcode=?", new String[]{eid,tcode,scode});
		if(photo==null)photo="";
		return photo;
	}
	
	public static String getAcclevelTrackPhoto(String userid,String tcode,String scode){
		String accleveltrackphoto=DbUtil.getVal("select photourl from accountlevel_track_partners where trackname=? and scode=? and userid=cast(? as bigint)",new String [] {tcode,scode,userid});
		if(accleveltrackphoto==null)accleveltrackphoto="";
		return accleveltrackphoto;
	}
	
	public static void changePhotoUrl(String photo,String eid,String trackcode,String scode){
		DbUtil.executeUpdateQuery("update trackURLs set photo=? where eventid=? and trackingcode=? and secretcode=?", new String[]{photo,eid,trackcode,scode});
	}
	
	public static void changeAcclevelPhotoUrl(String photo,String userid,String tcode,String scode){
		DbUtil.getKeyValues("update accountlevel_track_partners set photourl=? where trackname=? and scode=? and userid=cast(? as bigint)",new String[]{photo,tcode,scode,userid} );
	}
	
	public static String trackMessage(String eid,String tcode,String scode){
		String message=DbUtil.getVal("select message from trackURLs where eventid=? and trackingcode=? and secretcode=?", new String[]{eid,tcode,scode});
		if(message==null)message="";
		return message;
	}
	
	public static String getAcclevelTrackMessage(String userid,String tcode,String scode){
		String accleveltrackmessage=DbUtil.getVal("select message from accountlevel_track_partners where trackname=? and scode=? and userid=cast(? as bigint)", new String[] {tcode,scode,userid});
		if(accleveltrackmessage==null)accleveltrackmessage="";
		return accleveltrackmessage;
	}
	
	public static void changeMessage(String message,String eid,String trackcode,String scode){
		DbUtil.executeUpdateQuery("update trackURLs set message=? where eventid=? and trackingcode=? and secretcode=?", new String[]{message,eid,trackcode,scode});
	}
	
	public static void changeAcclevelMessage(String message,String userid,String tcode,String scode){
		DbUtil.getVal("update accountlevel_track_partners set message=? where trackname=? and scode=? and userid=cast(? as bigint)",new String[]{message,tcode,scode,userid});		
	}
	
	public static String getBeeid(String beeid){
		String loginname=DbUtil.getVal("select user_id from authentication where login_name=?", new String[]{beeid});
		if(loginname==null)loginname="";
		return loginname;
	}
	
	public static HashMap<String,String> getEventRegistrations(String trackcode){
		DBManager dbmanager=new DBManager();
		HashMap<String,String> hm=new HashMap<String,String>();
		StatusObj stobj=null;
		stobj=dbmanager.executeSelectQuery("select eventid,count(*) from event_reg_transactions where trackpartner=? group by eventid", new String[]{trackcode});
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){				
                hm.put(dbmanager.getValue(i,"eventid","0"),dbmanager.getValue(i,"count","0"));	
			}
		}
		return hm;
	}
	
	public static Vector<HashMap<String,String>> getTrackDetailsInfo(String trackcode, String secretcode) {
		Vector<HashMap<String,String>> partnereventsvector = new Vector<HashMap<String,String>>();
		DBManager dbmanager = new DBManager();
		HashMap<String,String> eventregistration = getEventRegistrations(trackcode.toLowerCase());
		System.out.println("the track code is::"+trackcode);
		System.out.println("the secoret code is::"+secretcode);
		
		StatusObj statobj = dbmanager.executeSelectQuery("select a.eventname, b.eventid, b.status, b.count from trackurls b,eventinfo a where b.trackingcode=? and b.secretcode=? and a.eventid=CAST(b.eventid as int)",
						new String[] { trackcode.trim(), secretcode.trim() });
		if (statobj.getStatus()) {
			for (int k = 0; k < statobj.getCount(); k++) {
				HashMap<String,String> eventhashmap = new HashMap<String,String>();	
				eventhashmap.put("eventname", dbmanager.getValue(k, "eventname", ""));
				eventhashmap.put("status", dbmanager.getValue(k, "status", "Approved"));
				eventhashmap.put("eventid", dbmanager.getValue(k, "eventid", ""));				
				eventhashmap.put("count", dbmanager.getValue(k, "count", "0"));
				if(eventregistration.containsKey(dbmanager.getValue(k, "eventid", "")))
				eventhashmap.put("registrations",eventregistration.get(dbmanager.getValue(k, "eventid", "")));
				else
				eventhashmap.put("registrations","0");
				partnereventsvector.add(eventhashmap);
			}
		}
		return partnereventsvector;
	}
	
	public static JSONObject getTrackStatusJson(Vector <HashMap<String,String>>ticStatusVec){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonarrObj = new JSONArray();
		try {
			for (Object object : ticStatusVec) {
				JSONObject jsonObj_ticstatus = new JSONObject();
				HashMap<String,String> hMap = (HashMap)object;				
				jsonObj_ticstatus.put("name", hMap.get("eventname"));
				jsonObj_ticstatus.put("status", hMap.get("status"));
				jsonObj_ticstatus.put("visits", hMap.get("count"));
				jsonObj_ticstatus.put("registrations",hMap.get("registrations"));
				jsonObj_ticstatus.put("eventid", hMap.get("eventid"));
				jsonarrObj.put(jsonObj_ticstatus);
			}
						
			JSONObject keyObj=new JSONObject();
			JSONArray keyarr=new JSONArray();
			keyObj.put("name","Name");
			keyarr.put(keyObj);
						
			jsonObj.put("tracksummary", jsonarrObj);
			jsonObj.put("keysummary",keyarr);
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());			
		}
		return jsonObj;		
	}
	
	public static HashMap<String,String> getGlobalTrackDetails(String mgrName,String tcode){
		HashMap<String,String> hmap=new HashMap<String,String>();
		String mgrId = DbUtil.getVal("select user_id from authentication where lower(login_name)=?",new String[] { mgrName });
		if(mgrId==null)return hmap;
		tcode = tcode.toLowerCase();
		String secretcode = DbUtil.getVal("select scode from accountlevel_track_partners where userid=CAST(? as BIGINT) and lower(trackname)=?",new String[] { mgrId, tcode });
		String dbpwd = DbUtil.getVal("select password from accountlevel_track_partners where userid=CAST(? as BIGINT) and lower(trackname)=?",	new String[] { mgrId, tcode });
		hmap.put("userid",mgrId);
		hmap.put("scode",secretcode);
		hmap.put("pwd",dbpwd);
		return hmap;
	}
	public static String getTrackSecretcode(String eid,String tcode){
		
		String scode=DbUtil.getVal("select secretcode from trackurls where eventid=? and trackingcode=?",
				new String[]{eid,tcode});
		if(scode==null)scode="";
		return scode;
	}
	
	
	public static String generateEventURL(String eid,String tcode){
		String eventURL=DbUtil.getVal("select url from event_custom_urls where eventid=?",new String[]{eid});
		if(eventURL!=null)
			eventURL=eventURL+"/t/"+tcode+"/manage";
		else
			eventURL="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/event?eid="+eid+"&track="+tcode+"&manage=manage";

		return eventURL;
	}
	
	}

