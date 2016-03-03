package com.eventbee.general.helpers;

import java.util.HashMap;

public class EventInfoCache {
	
	static HashMap<String, String> evetnInfoData=new HashMap<String, String>();
	
	protected static String getData(String key){
		return evetnInfoData.get(key);
	}
	
	protected static void setData(String key, String value){
		evetnInfoData.put(key,value);
	}
	
	public static void clearAllCache(){
		evetnInfoData.clear();
	}
	
	public static void clearCache(String key){
		evetnInfoData.remove(key);
	}

}
