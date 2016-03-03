package com.eventmanage.rsvp.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONObject;

public class RSVPReportsJsonHelper {

	public static HashMap getRSVPResponseJson(ArrayList resFields,ArrayList rsvpResponseMap,ArrayList responseQuestionFields, HashMap<String, String> customAttribsMap){
		
		String msg="";
		JSONObject json=new JSONObject();
		ArrayList finalrsvpresponses=new ArrayList();
		if(resFields.contains("resFieldsFilter")){
			resFields.remove("resFieldsFilter");
			for(int m=0;m<responseQuestionFields.size();m++){
				//customAttribsMap.get(responseQuestionFields.get(m))
				resFields.add(responseQuestionFields.get(m));
			}
		}
		for(int i=0;i<rsvpResponseMap.size();i++){
			HashMap hmap=new HashMap();
			for(int j=0;j<resFields.size();j++){
				hmap.put(resFields.get(j),((HashMap)rsvpResponseMap.get(i)).get(resFields.get(j)));
			}
		finalrsvpresponses.add(hmap);
	  }
	 try{
		 	
		    HashMap hm= comperssMap(finalrsvpresponses,"just",resFields, customAttribsMap);
		 //   System.out.println("HashMap:::"+hm);			
			json.put("rsvpResponseMap",hm.get("resmap"));
			json.put("mapkeyfiled",hm.get("reskeymap"));
			json.put("filedlen",resFields.size());
		    msg=json.toString();
		}catch(Exception e){
			System.out.println("Exception occured at prepare json in getRSVPResponseJson"+e.getMessage());
		}
		
		HashMap result=new HashMap();
		result.put("msg",msg);
		result.put("resFields",resFields);
	   
		
		//try{comperssMap(finalrsvpresponses,"just");}catch(Exception e){System.out.println("exeception:: rsvpjson helper"+e.getMessage());e.printStackTrace();}
		return result;
	}
	
	public static String getRSVPCheckedJson(ArrayList checkinFields,ArrayList rsvpcheckedinReportsMap){
		String msg="";
		JSONObject json=new JSONObject();
		ArrayList finalrsvpcheckinlist=new ArrayList();
		for(int i=0;i<rsvpcheckedinReportsMap.size();i++){
			HashMap hmap=new HashMap();
			for(int j=0;j<checkinFields.size();j++){
				hmap.put(checkinFields.get(j),((HashMap)rsvpcheckedinReportsMap.get(i)).get(checkinFields.get(j)));
			}
			finalrsvpcheckinlist.add(hmap);
	  }
	   try{
		   
		    HashMap hm= comperssMap(rsvpcheckedinReportsMap,"just",checkinFields,null);
		   // System.out.println("HashMap:::"+hm);			
			json.put("rsvpResponseMap",hm.get("resmap"));
			json.put("mapkeyfiled",hm.get("reskeymap"));
			json.put("filedlen",checkinFields.size());
		     msg=json.toString();
		}catch(Exception e){
			System.out.println("Exception occured at prepare json in getRSVPResponseJson"+e.getMessage());
		}	
        return  msg;
	}
	
	
	
	
	public static HashMap comperssMap(ArrayList attendeeMap,String purpose,ArrayList resFields,HashMap<String, String> customAttribsMap){
		long starttime=System.currentTimeMillis();
		  System.out.println("starttime:::::"+new java.util.Date().getTime());
	      HashMap glbreshmap= new HashMap();
	      ArrayList attendeeMaptemp=new ArrayList();
	      HashMap glbtemphmap= new HashMap();
	      int id=0;
	      Set set = new HashSet();
	      //System.out.println("attendeeMap:::"+attendeeMap);
	      for(int i=0;i<attendeeMap.size();i++){
	      HashMap deattendeeMap= (HashMap)attendeeMap.get(i);
	      HashMap nel=new HashMap();
	      Iterator entries = deattendeeMap.entrySet().iterator();
	      while (entries.hasNext()) {
	        Entry thisEntry = (Entry) entries.next();
	        String key =(String)thisEntry.getKey();
	        String value = (String)thisEntry.getValue();
	        if("".equals(value))
	                   continue;              
	        else
	        {  if(set.add(value)){}
	            else
	            {   if(value!=null && value.length()>6 && !glbtemphmap.containsKey(value))
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
	 // System.out.println("endtime:::11::"+new java.util.Date().getTime());   
	   attendeeMap=null;
	   attendeeMap=new ArrayList();
	   set.clear();
	  	    for(int i=0;i< resFields.size();i++){
	  	    	glbtemphmap.put(resFields.get(i),"q_"+i);
	  	    	if(customAttribsMap != null && customAttribsMap.containsKey(resFields.get(i)))
	  	    		glbreshmap.put("q_"+i,customAttribsMap.get(resFields.get(i)));
	  	    	else
	  	    		glbreshmap.put("q_"+i,resFields.get(i));
	  	    }
	   
	    for(int i=0;i<attendeeMaptemp.size();i++){
	          HashMap deattendeeMap= (HashMap)attendeeMaptemp.get(i);
	          HashMap nel=new HashMap();
	          Iterator entries = deattendeeMap.entrySet().iterator();
	          while (entries.hasNext()) {	             
	            Entry thisEntry = (Entry) entries.next();
	            String key =(String) thisEntry.getKey();
	            key=glbtemphmap.get(key)+"";
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
	//result.put("reslist",tempal);
	System.out.println("endtime:::::"+new java.util.Date().getTime());
	long totaltime=(System.currentTimeMillis())-starttime;
	System.out.println("Reports_UUID: Total time taken to compress "+purpose+": "+totaltime+" MS");
	//*/HashMap result=new HashMap();
	      return result;

	}
	
	
	
	
}
