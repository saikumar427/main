package com.eventbee.layout;

import java.util.HashMap;

public class EventGlobalTemplates {
		
	public static HashMap<String, String> globalTemplates = new HashMap<String, String>();

	public static HashMap<String, String> getGlobalTemplates() {
		return globalTemplates;
	}

	public static void setGlobalTemplates(String key, String template) {
		globalTemplates.put(key, template);
	}
	
	public static String get(String name,String def){

		return ( globalTemplates.get(name)==null   )?def:(String)globalTemplates.get(name);

	}
	
	static{
		System.out.println("::::::: in EventGlobalTemplates static block :::::::");
		globalTemplates.putAll(DBHelper.getGlobalTemplates());
	}

}
