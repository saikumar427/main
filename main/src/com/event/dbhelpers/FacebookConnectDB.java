package com.event.dbhelpers;

import com.eventbee.beans.FacebookConnectData;
import com.eventbee.general.DbUtil;

public class FacebookConnectDB {
	public static String getFBApiKey(String eid){
		String apikey=DbUtil.getVal("select value from config where name='ebee.fbconnect.api' and config_id=to_number(?,'999999999999999')",new String[]{"0"});
		return apikey;
	}
	public static String getFBAppId(String eid){
		String apikey=DbUtil.getVal("select value from config where name='ebee.fbconnect.appid' and config_id=to_number(?,'999999999999999')",new String[]{"0"});
		return apikey;
	}
	public static String getFBEventId(String eid){
		String fbeventid=DbUtil.getVal("select fbeventid from ebee_fb_publishevent where eventid=?",new String[]{eid});
		return fbeventid;
	}
	public static void saveFBId(String eid,FacebookConnectData fbData){
		DbUtil.executeUpdateQuery("insert into ebee_fb_publishevent(eventid,fbuid,fbeventid,created_at) values(?,?,?,now())", new String[]{eid,fbData.getFbuserid(),fbData.getFbeventid()});
	}
	public static String getEventDescription(String eid){
		String textdesc=DbUtil.getVal("select description  from eventinfo where descriptiontype='text' and eventid=CAST(? AS BIGINT)",new String[]{eid});
		return textdesc;
	}
	

}
