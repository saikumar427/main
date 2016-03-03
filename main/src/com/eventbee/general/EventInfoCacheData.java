package com.eventbee.general;

import com.eventbee.general.helpers.EventInfoCache;

public class EventInfoCacheData extends EventInfoCache {
	
	public static String getI18NLanguage(String eventID){
		String lang=getData(eventID+"_db_i18n_lang");
		if(lang==null) lang=setI18NLanguage(eventID);
		return lang;
	}
	
	public static void setI18NLanguage(String key,String value){
		setData(key,value);
	}
	
	public static String setI18NLanguage(String eventID){
		String langQry="select value from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.i18n.lang'";
		String lanag=DbUtil.getVal(langQry, new String[]{eventID});
		if(lanag==null || "".equals(lanag)) lanag="en_US";
		setData(eventID+"_db_i18n_lang",lanag);
		return lanag;
	}
	
}
