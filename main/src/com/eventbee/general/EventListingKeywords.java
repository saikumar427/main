package com.eventbee.general;

import java.util.ArrayList;


import com.event.dbhelpers.AddEventDB;


public class EventListingKeywords {
	
	
	//public static HashMap<String,String> keywords = new HashMap<String,String>();
	//public static ArrayList<HashMap<String,String>> keywords = new ArrayList<HashMap<String,String>>();
	public static ArrayList<String> keywords = new ArrayList<String>();
	
	
	static {		
		keywords = AddEventDB.getKeywords();		
	}

}
