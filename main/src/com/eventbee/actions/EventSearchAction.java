package com.eventbee.actions;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateConverter;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.opensymphony.xwork2.ActionSupport;

public class EventSearchAction extends ActionSupport {
	private String searchcontent="";
    private HashMap configMap=new HashMap();
    private ArrayList eventlist=new ArrayList(); 
    private ArrayList<String> configlist=new ArrayList<String>();
    private HashMap hosetedMap=new HashMap();
    private String sortedtype="asc";
    private String count="";
    private ArrayList<Entity> sortlist=new ArrayList<Entity>();
    String pattern="default";
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSortedtype() {
		return sortedtype;
	}

	public void setSortedtype(String sortedtype) {
		this.sortedtype = sortedtype;
	}

	public String getSearchcontent() {
		return searchcontent;
	}

	public void setSearchcontent(String searchcontent) {
		this.searchcontent = searchcontent;
	}

	public String getEvent(){
		return "success";
	}
	
	public void getSortList(){
		sortlist.add(new Entity("asc","A-Z"));
		sortlist.add(new Entity("desc","Z-A"));
		sortlist.add(new Entity("date","Date"));
	}
	
	public String execute(){
		getSortList();
		if("".equals(searchcontent.trim()))
			return "success";
		searchcontent=searchcontent.trim();	
		String searchquery="select  country,config_id,to_char(start_date,'DD, Month YYYY') as stdate,"+
						   "to_char(start_date,'Day') as stfuldate,to_char(start_date,'Month DD, YYYY') as stfuldate2,starttime,eventid,"+
						   "eventname,address1,address2,city,venue from eventinfo where status='ACTIVE' and "+
						   "(lower(eventname) like lower(?) or lower(venue) like lower(?))";
		if("asc".equalsIgnoreCase(sortedtype))
			searchquery=searchquery+" order by eventname "+sortedtype;
		else if("desc".equalsIgnoreCase(sortedtype))
			searchquery=searchquery+" order by eventname "+sortedtype;
		else if("date".equalsIgnoreCase(sortedtype))
			searchquery=searchquery+" order by start_date";
						   
		String[] inputparams= {"%"+searchcontent+"%","%"+searchcontent+"%"};
		DBManager dbm=new DBManager();
    	StatusObj statobj=null;
    	statobj=dbm.executeSelectQuery(searchquery,inputparams);
    	System.out.println("the count for records::"+statobj.getCount());
    	if(statobj.getStatus() && statobj.getCount()>0){
    		String seradd=EbeeConstantsF.get("serveraddress","www.eventbee.com");
    		for(int i=0;i<statobj.getCount();i++){
    			HashMap<String,String> hmap=new HashMap<String,String>();
    			hmap.put("stdate",dbm.getValue(i,"stdate",""));
    			hmap.put("eventname",dbm.getValue(i,"eventname",""));
    			hmap.put("eventid","http://"+seradd+"/event?eid="+dbm.getValue(i,"eventid", ""));
    			hmap.put("address1",dbm.getValue(i,"address1", ""));
    			hmap.put("address2",dbm.getValue(i,"address2", ""));
    			hmap.put("city",dbm.getValue(i,"city", ""));
    			hmap.put("venue", dbm.getValue(i,"venue",""));
    			String fuldate=dbm.getValue(i,"stfuldate","").trim()+", "+dbm.getValue(i,"stfuldate2","")+", "+DateConverter.getTimeAM(dbm.getValue(i,"starttime",""));
    			hmap.put("stfuldate",fuldate);
    			hmap.put("country",dbm.getValue(i,"country",""));
    			hmap.put("configid",dbm.getValue(i,"config_id", ""));
    			eventlist.add(hmap);
    			configlist.add(dbm.getValue(i,"config_id", ""));
    		}
    	}
    	count=eventlist.size()+"".trim();
    	
    	String configids="";
    	for(int i=0;i<configlist.size();i++){
    		if(i==0)
    			configids=configlist.get(i);
    		else
    			configids=configids+","+configlist.get(i);
    		
    	}
    	if(!"".equals(configids.trim())){
    	String configquery1="select * from config where name in('event.hostname','event.eventphotoURL') and config_id in ("+configids+")";
		statobj=dbm.executeSelectQuery(configquery1,null);
		if(statobj.getStatus()&&statobj.getCount()>0){
			for(int i=0;i<statobj.getCount();i++){
				HashMap configmap=new HashMap();
				if("event.eventphotoURL".equals(dbm.getValue(i,"name",""))){
				configmap.put(dbm.getValue(i,"name",""),dbm.getValue(i,"value",""));
				configMap.put(dbm.getValue(i,"config_id",""), configmap);
				}else {
					configmap.put(dbm.getValue(i,"name",""),dbm.getValue(i,"value",""));
					hosetedMap.put(dbm.getValue(i,"config_id",""), configmap);
				}
			}
			
		}
    	}
		 return "success";
	}
	public String searchResult(){
		getSortList();
		System.out.println("seearch term:"+searchcontent);
		if("".equals(searchcontent.trim()))
			return "success";
		searchcontent=searchcontent.trim();	
		String searchquery="select  country,config_id,to_char(start_date,'DD, Month YYYY') as stdate,"+
						   "to_char(start_date,'Day') as stfuldate,to_char(start_date,'Month DD, YYYY') as stfuldate2,starttime,eventid,"+
						   "eventname,address1,address2,city,venue from eventinfo where status='ACTIVE' and "+
						   "(lower(eventname) like lower(?) or lower(venue) like lower(?))";
		
		if("asc".equalsIgnoreCase(sortedtype))
			searchquery=searchquery+" order by eventname "+sortedtype;
		else if("desc".equalsIgnoreCase(sortedtype))
			searchquery=searchquery+" order by eventname "+sortedtype;
		else if("date".equalsIgnoreCase(sortedtype))
			searchquery=searchquery+" order by start_date";
						   
		String[] inputparams= {"%"+searchcontent+"%","%"+searchcontent+"%"};
		DBManager dbm=new DBManager();
    	StatusObj statobj=null;
    	statobj=dbm.executeSelectQuery(searchquery,inputparams);
    	System.out.println("the count for records::"+statobj.getCount());
    	if(statobj.getStatus() && statobj.getCount()>0){
    		String seradd=EbeeConstantsF.get("serveraddress","www.eventbee.com");
    		for(int i=0;i<statobj.getCount();i++){
    			HashMap<String,String> hmap=new HashMap<String,String>();
    			hmap.put("stdate",dbm.getValue(i,"stdate",""));
    			hmap.put("eventname",dbm.getValue(i,"eventname",""));
    			hmap.put("eventid","http://"+seradd+"/event?eid="+dbm.getValue(i,"eventid", ""));
    			hmap.put("address1",dbm.getValue(i,"address1", ""));
    			hmap.put("address2",dbm.getValue(i,"address2", ""));
    			hmap.put("city",dbm.getValue(i,"city", ""));
    			hmap.put("venue", dbm.getValue(i,"venue",""));
    			String fuldate=dbm.getValue(i,"stfuldate","").trim()+", "+dbm.getValue(i,"stfuldate2","")+", "+DateConverter.getTimeAM(dbm.getValue(i,"starttime",""));
    			hmap.put("stfuldate",fuldate);
    			hmap.put("country",dbm.getValue(i,"country",""));
    			hmap.put("configid",dbm.getValue(i,"config_id", ""));
    			eventlist.add(hmap);
    			configlist.add(dbm.getValue(i,"config_id", ""));
    		}
    	}
    	count=eventlist.size()+"".trim();
    	
    	String configids="";
    	for(int i=0;i<configlist.size();i++){
    		if(i==0)
    			configids=configlist.get(i);
    		else
    			configids=configids+","+configlist.get(i);
    		
    	}
    	if(!"".equals(configids.trim())){
    	String configquery1="select * from config where name in('event.hostname','event.eventphotoURL') and config_id in ("+configids+")";
		statobj=dbm.executeSelectQuery(configquery1,null);
		if(statobj.getStatus()&&statobj.getCount()>0){
			for(int i=0;i<statobj.getCount();i++){
				HashMap configmap=new HashMap();
				if("event.eventphotoURL".equals(dbm.getValue(i,"name",""))){
				configmap.put(dbm.getValue(i,"name",""),dbm.getValue(i,"value",""));
				configMap.put(dbm.getValue(i,"config_id",""), configmap);
				}else {
					configmap.put(dbm.getValue(i,"name",""),dbm.getValue(i,"value",""));
					hosetedMap.put(dbm.getValue(i,"config_id",""), configmap);
				}
			}
			
		}
    	}
		 return "result";
	}

	public ArrayList getEventlist() {
		return eventlist;
	}

	public void setEventlist(ArrayList eventlist) {
		this.eventlist = eventlist;
	}

	public ArrayList<String> getConfiglist() {
		return configlist;
	}

	public void setConfiglist(ArrayList<String> configlist) {
		this.configlist = configlist;
	}

	public HashMap getConfigMap() {
		return configMap;
	}

	public void setConfigMap(HashMap configMap) {
		this.configMap = configMap;
	}

	public HashMap getHosetedMap() {
		return hosetedMap;
	}

	public void setHosetedMap(HashMap hosetedMap) {
		this.hosetedMap = hosetedMap;
	}

	public ArrayList<Entity> getSortlist() {
		return sortlist;
	}

	public void setSortlist(ArrayList<Entity> sortlist) {
		this.sortlist = sortlist;
	}

}
