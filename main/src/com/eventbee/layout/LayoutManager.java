package com.eventbee.layout;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;


public class LayoutManager { 
	
	
	
	public static JSONArray concatArray(JSONArray...arrs) throws Exception {
		
		
		JSONArray result = new JSONArray();
		for (JSONArray arr : arrs){
			//System.out.println("arrssss:::"+arr);
			for (int i = 0; i < arr.length(); i++)
				result.put(arr.get(i));
		}
		return result;
	}
	
	
public static JSONObject concatArrays(JSONArray...arrs) throws Exception {
		
		
	JSONObject object = new JSONObject();
		for (JSONArray arr : arrs){
			//System.out.println("arrssss:::"+arr);
			for (int i = 0; i < arr.length(); i++){
				JSONObject posts = (JSONObject) arr.get(i);
				
				Iterator keysToCopyIterator = posts.keys();
				ArrayList<String> keys = new ArrayList<String>();
				while(keysToCopyIterator.hasNext()) {
				    String key = (String) keysToCopyIterator.next();
				    keys.add(key);
				}
				
				for(int j=0;j<keys.size();j++){
					object.put(keys.get(j),posts.get(keys.get(j)));
				}
				
			}
		}
		//System.out.println("the obecyts::"+object);
		return object;
	}
	
	
}
