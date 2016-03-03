package com.eventmanage.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.event.helpers.I18n;


public class ReportsJsonHelper {
	

	public static String getAttReportsJson(ArrayList<HashMap<String,String>> attendeeMap,ArrayList attFields,
			HashMap<String,String> custattribsmap,String currencySymbol,HashMap<String,String> reportsmap){
		String msg="";
		 JSONObject js=new JSONObject();
		 try {
			 
			 ArrayList <String>noSortable=new ArrayList<String>(Arrays.asList("TP","ED"));
			 ArrayList<HashMap<String, String>> coldefs=new ArrayList<HashMap<String, String>>();
			 //attFields.remove("attFieldsFilter");
			 
			 for(int i=0;i<attFields.size();i++){
				 HashMap<String,String> hm=new HashMap<String, String>();
				 String key=(String)attFields.get(i);
				 String label="";
				 if(reportsmap.containsKey(key))
					 label=I18n.getString(reportsmap.get(key));
				 else if(custattribsmap.containsKey(key))
					 label=custattribsmap.get(key);
				 else label=key;
				 hm.put("key",key);
				 hm.put("label",label);
				 if(noSortable.contains(key)){
					 
				 }else if(!custattribsmap.containsKey(key))
					 hm.put("sortable","true");
				 coldefs.add(hm);
			 }
			 
			 ArrayList attendeeRepMap=new ArrayList();
             for(int i=0;i<attendeeMap.size();i++){
             HashMap deattendeeMap= (HashMap)attendeeMap.get(i);
             HashMap hmap=new HashMap();
             Iterator entries = deattendeeMap.entrySet().iterator();
             while (entries.hasNext()) {
                
               Entry thisEntry = (Entry) entries.next();
               String key =(String) thisEntry.getKey();
               String value = (String)thisEntry.getValue();
               if("".equals(value))
                {
                   continue;                     
                }
               hmap.put(key,value);
              }
             attendeeRepMap.add(hmap);
           }
			 
            HashMap reponse=comperssMap(attendeeMap,"attendeereports"); 
            js.put("attreports", reponse.get("resmap"));
            js.put("glbres", reponse.get("reskeymap"));
			//js.put("attreports", attendeeRepMap);
			js.put("fields", attFields);
			js.put("coldefs",coldefs);
			js.put("currencySymbol",currencySymbol);
			msg=js.toString();
	}
		 catch (Exception e) {
			// TODO: handle exception
		}
		 return msg;
	}
	public static String getTransactionReportsJson(ArrayList<HashMap<String,String>> transactionMap,ArrayList Fields
			,HashMap<String, String> custattribsmap,String currencySymbol,HashMap<String, String> reportsmap){
		String msg="";
		JSONObject js=new JSONObject();
		ArrayList<HashMap<String, String>> coldefs=new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> fields=new ArrayList<HashMap<String, String>>();
		try {
			ArrayList <String>noSortable=new ArrayList<String>(
					Arrays.asList("TP","TC","CCPF","SF","TTC","TNet","Di","CSF","NTSC","VTSC","ESF","ECCF","Bal","OCCF","RA","ED"));

			//Fields.remove("FieldsFilter");
			for(int i=0;i<Fields.size();i++){
				HashMap<String, String> hm=new HashMap<String, String>();
				String field=(String)Fields.get(i);
				hm.put("key", field);
				fields.add(hm);
			}
			
			for(int i=0;i<Fields.size();i++){
				 HashMap<String,String> hm=new HashMap<String, String>();
				 String key=(String)Fields.get(i);
				 String label="";
				 if(reportsmap.containsKey(key))
					 label=I18n.getString(reportsmap.get(key));
				 else if(custattribsmap.containsKey(key))
					 label=custattribsmap.get(key);
				 else label=key;
				 
				 hm.put("key",key);
				 hm.put("label",label);
				 /*if(key.equals("So") || key.equals("TN") || key.equals("TP") || key.equals("No") ){
					 
				 }else if(key.equals("TC") || key.equals("TTC") || key.equals("TNet") || key.equals("CSF") || key.equals("TTC") || key.equals("TNet") ){
				 }*/
				 if(noSortable.contains(key)){
					 
				 }else if(!custattribsmap.containsKey(key)){
					 hm.put("sortable","true");
				 }
				 coldefs.add(hm);
			 }
			
			ArrayList transactionRepMap=new ArrayList();
            for(int i=0;i<transactionMap.size();i++){
            HashMap deattendeeMap= (HashMap)transactionMap.get(i);
            HashMap hmap=new HashMap();
            Iterator entries = deattendeeMap.entrySet().iterator();
            while (entries.hasNext()) {
               
              Entry thisEntry = (Entry) entries.next();
              String key =(String) thisEntry.getKey();
              String value = (String)thisEntry.getValue();
              if("".equals(value))
               {
                  continue;                     
               }
              hmap.put(key,value);
             }
            transactionRepMap.add(hmap);
          }
            HashMap reponse=comperssMap(transactionMap,"transactionreports");
            js.put("attreports", reponse.get("resmap"));
            js.put("glbres", reponse.get("reskeymap"));
			//js.put("attreports", transactionRepMap);
			js.put("fields", fields);
			js.put("coldefs",coldefs);
			js.put("currencySymbol",currencySymbol);
			msg=js.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return msg;
	}
public static String getCheckInReportsJson(ArrayList<HashMap<String,String>> checkedinReportsMap,ArrayList checkinFields,HashMap<String, String> reportsmap){
	String msg="";
	JSONObject js=new JSONObject();
	ArrayList<HashMap<String, String>> coldefs=new ArrayList<HashMap<String, String>>();
	try {
		for(int i=0;i<checkinFields.size();i++){
			 HashMap<String,String> hm=new HashMap<String, String>();
			 String key=(String)checkinFields.get(i);
			 String label="";
			 if(reportsmap.containsKey(key))
				 label=I18n.getString(reportsmap.get(key));
			 else label=key;
			 hm.put("key",key);
			 hm.put("label",label);
			 if(key.equals("CIS") || key.equals("CIT") || key.equals("SID"))
			 {}
			 else
			 hm.put("sortable","true");
			 coldefs.add(hm);
		 }
		HashMap reponse=comperssMap(checkedinReportsMap,"checkedinreports");
		js.put("attreports", reponse.get("resmap"));
        js.put("glbres", reponse.get("reskeymap"));
		//js.put("attreports", checkedinReportsMap);
		js.put("fields", checkinFields);
		js.put("coldefs",coldefs);
		msg=js.toString();

	} catch (Exception e) {
		// TODO: handle exception
	}
	return msg;
}

public static HashMap comperssMap(ArrayList attendeeMap,String purpose){
	long starttime=System.currentTimeMillis();
   HashMap glbreshmap= new HashMap();
      ArrayList attendeeMaptemp=new ArrayList();
      ArrayList keysnotcompress=new ArrayList();
      keysnotcompress.add("TKTID");
      keysnotcompress.add("PT");
      keysnotcompress.add("St");
      keysnotcompress.add("BKS");
      HashMap glbtemphmap= new HashMap();
   int id=0;
      Set set = new HashSet();
      for(int i=0;i<attendeeMap.size();i++){
      HashMap deattendeeMap= (HashMap)attendeeMap.get(i);
      HashMap nel=new HashMap();
      Iterator entries = deattendeeMap.entrySet().iterator();
      while (entries.hasNext()) {
        Entry thisEntry = (Entry) entries.next();
        String key =(String) thisEntry.getKey();
        String value = (String)thisEntry.getValue();
        if("".equals(value))
                   continue;              
        else
        {
            if(set.add(value)){}
            else
            {
                if(value.length()>6 && !glbtemphmap.containsKey(value) && !keysnotcompress.contains(key))
                {
                 glbtemphmap.put(value,"g_"+id);
                 glbreshmap.put("g_"+id,value);
                 id++;
                }
            }
        }
         nel.put(key,value);
       }
      attendeeMaptemp.add(nel);
    }
         
   attendeeMap=null;
   attendeeMap=new ArrayList();
   set.clear();
    for(int i=0;i<attendeeMaptemp.size();i++){
          HashMap deattendeeMap= (HashMap)attendeeMaptemp.get(i);
          HashMap nel=new HashMap();
          Iterator entries = deattendeeMap.entrySet().iterator();
          while (entries.hasNext()) {
             
            Entry thisEntry = (Entry) entries.next();
            String key =(String) thisEntry.getKey();
            String value = (String)thisEntry.getValue();
            if(glbtemphmap.containsKey(value))
             {
                nel.put(key,glbtemphmap.get(value));
               
           }
             else
            nel.put(key,value);   
                       
          }
            attendeeMap.add(nel);
    }
    glbtemphmap.clear();
    attendeeMaptemp.clear();
HashMap result=new HashMap();
result.put("resmap",attendeeMap);
result.put("reskeymap",glbreshmap);

long totaltime=(System.currentTimeMillis())-starttime;
System.out.println("Reports_UUID: Total time taken to compress "+purpose+": "+totaltime+" MS");
return result;

}

}
