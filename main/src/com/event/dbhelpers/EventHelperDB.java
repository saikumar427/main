package com.event.dbhelpers;

import java.util.HashMap;

import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.StatusObj;
import com.eventbee.util.CoreConnector;

public class EventHelperDB {
	public static void setUserTheme(String eid,String mgrId,String registrationtype){
		System.out.println("15)inserting user theme");
		if("ticketing".equals(registrationtype)){
		StatusObj obj=DbUtil.executeUpdateQuery("insert into user_roller_themes(userid,module,themecode,cssurl,refid,themetype) values(to_number(?,'99999999999999999'),'event','basic','basic.css',?,'DEFAULT')", new String[]{mgrId,eid});
		}else{
			DbUtil.executeUpdateQuery("insert into user_roller_themes(userid,module,themecode,cssurl,refid,themetype) values(to_number(?,'99999999999999999'),'event','basic','basic.css',?,'DEFAULT')", new String[]{mgrId,eid});
		}
		System.out.println("15)inserted usertheme");
	}
	
	public static void removeGLobalEventCache(String eid, String mode, String cacheKey){
		String s1=EbeeConstantsF.get("s1","www.eventbee.com");
		String s2=EbeeConstantsF.get("s2","www.eventbee.com");
		System.out.println("in EventHelperDB.java removeGLobalEventCache::"+eid);
		System.out.println("s1: server address :"+s1+":s2: server address:"+s2);
		HashMap<String,String> inputparams=new HashMap<String,String>();
		
		if("remove".equals(mode)) 
			inputparams.put("rmkey",eid+"_"+cacheKey);
		else 
			inputparams.put("rmkey",eid);
		
		inputparams.put("mode",mode);
		inputparams.put("action","allow");
		String data="";
		try{
			CoreConnector cc1=new CoreConnector("http://"+s1+"/utworks/eventglobaldata.jsp");
			cc1.setArguments(inputparams);
			cc1.setTimeout(50000);
			data=cc1.MGet();
			CoreConnector cc2=new CoreConnector("http://"+s2+"/utworks/eventglobaldata.jsp");
			cc2.setArguments(inputparams);
			cc2.setTimeout(50000);
			data=cc2.MGet();
			}catch(Exception e){
			System.out.println("Error While Processing Request::"+e.getMessage());		
			}	
	}

}
