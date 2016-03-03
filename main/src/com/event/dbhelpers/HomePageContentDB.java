package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.event.beans.HomePageEventData;
import com.eventbee.general.*;

public class HomePageContentDB {
	static String GET_EVENTS_BY_LOCATION="SELECT eventname,eventid,photourl,trim(to_char(e.start_date,'DD Mon')) as start_date1,"
		+" e.created_at as created, premiumlevel,count(price.price_id) as tcount"
		+"  from  eventinfo e left outer join price on price.evt_id=e.eventid"
		+" where (premiumlevel not in('EVENT_FEATURED_LISTING','EVENT_CUSTOM_LISTING')or premiumlevel is null ) and "
		+"  type=?   and listType='PBL' and showinhomepage='Y' and e.status='ACTIVE' and to_char(e.start_date,'yyyy-MM-dd')>=to_char(now(),'yyyy-MM-dd')"
		+" and price.evt_id=e.eventid group by eventname,eventid,photourl,start_date1,premiumlevel, created " +
				"order by e.created_at desc limit to_number(?,'999')";
		//
  static String GET_FEATURED_EVENTS_BY_LOCATION="SELECT eventname,eventid,photourl,trim(to_char(e.start_date,'DD Mon')) as start_date1,"
				   +" e.created_at as created, premiumlevel,count(price.price_id) as tcount"
					   +" from  eventinfo e left outer join price on price.evt_id=e.eventid"
					   +" where premiumlevel in('EVENT_FEATURED_LISTING','EVENT_CUSTOM_LISTING') "
				   +"  and type=?   and e.status='ACTIVE' and listType='PBL' and to_char(e.start_date,'yyyy-MM-dd')>=to_char(now(),'yyyy-MM-dd') "
				   +" group by eventname,eventid,photourl,start_date1,premiumlevel, created " +
				   		"order by e.created_at desc ";
		//
  public static ArrayList getEvents(int index,String []params){
System.out.println("HomePageContentDB getEvents Method");
	  ArrayList vec=new ArrayList();
	  DBManager dbmanager=new DBManager();
	  String query="";
	  if(index==1){
		  query=GET_EVENTS_BY_LOCATION;
	  }
	  else{
		  query=GET_FEATURED_EVENTS_BY_LOCATION;
	  }
	  StatusObj statobj=dbmanager.executeSelectQuery(query,params);
	  int recordcount=statobj.getCount();
	  if(recordcount>0){
		  for(int i=0;i<recordcount;i++){
			  HomePageEventData hpData=new HomePageEventData();
			  hpData.setEventid(dbmanager.getValue(i,"eventid",""));
			  hpData.setEventname(dbmanager.getValue(i,"eventname",""));
			  hpData.setStartdate(dbmanager.getValue(i,"start_date1",""));
			  String premiumLevel=dbmanager.getValue(i,"premiumlevel","");
			  hpData.setPremiumlevel("No");
			  if(!"no".equals(premiumLevel)&&premiumLevel!=null&&!"".equals(premiumLevel)){
				  hpData.setPremiumlevel("Yes");
			  }
			  hpData.setPhotourl(dbmanager.getValue(i,"photourl",null));
			  hpData.setTcount(dbmanager.getValue(i,"tcount",""));
			  vec.add(hpData);

}
}
return vec;
}
public static ArrayList  getUnique(ArrayList v,int max){
System.out.println("HomePageContentDB getUnique Method");
HomePageEventData hpData1;
HomePageEventData hpData2;

for(int i=v.size()-1;i>0;i--)
{
	hpData1=(HomePageEventData)v.get(i);
	hpData2=(HomePageEventData)v.get(i-1);
if(hpData1.getEventname().equals(hpData2.getEventname()))
v.remove(hpData2);
}
if(max>0&&v.size()>max){
for(int i=v.size()-1;i>=max;i--)
{
v.remove(i);

} 
}
return v;
}

public static ArrayList getEventGroups(){
	System.out.println("HomePageContentDB getEventGroups Method");
ArrayList eventgroupsvector= new ArrayList();
DBManager dbm =new DBManager();
HomePageEventData hpData=new HomePageEventData();	
//select trim(to_char(b.start_date,'DD Mon')) as start_date from group_events a, eventinfo b where
//a.event_groupid=? and a.eventid=b.eventid order by start_date asc"
//,new String[]{(String)hm.get("event_groupid")});
String Query="select trim(to_char(b.start_date,'DD Mon')) as start_date,a.event_groupid," +
		"group_title from user_groupevents c,group_events a, eventinfo b where c.showinhomepage='Y'" +
		" and b.eventid=CAST(a.eventid AS BIGINT)  and c.event_groupid=a.event_groupid order by " +
		"start_date asc"; 
StatusObj statobj=dbm.executeSelectQuery(Query,new String[] {});
HashMap groupIds=new HashMap();
if(statobj.getStatus())	{
for(int k=0;k<statobj.getCount();k++){
	hpData=new HomePageEventData();
	String groupId=dbm.getValue(k,"event_groupid","");
	if(!groupIds.containsKey(groupId)){
	groupIds.put(groupId,"");
	hpData.setEventid(groupId);
	hpData.setEventname(dbm.getValue(k,"group_title",""));
	hpData.setStartdate(dbm.getValue(k,"start_date",""));
	eventgroupsvector.add(hpData);
	}
}
}
else 
System.out.println("statusobject status is:::::::;"+statobj.getStatus());
return eventgroupsvector;
}
public static ArrayList getDisplayEvents(String type,int count){
	ArrayList featured_vec=new ArrayList();
	
	ArrayList result_vec=new ArrayList();
	 featured_vec=getEvents(2,new String [] {type});
	 featured_vec=getUnique(featured_vec,-1);
	 
		if(featured_vec!=null)
	 	{
	 		if(featured_vec.size()>=5){
	 			result_vec=getEvents(1,new String [] {type,"20"});
	 			result_vec=getUnique(result_vec,2);
	 			result_vec.addAll(featured_vec); 
	 		}
	 		else {  
	 			count=5-featured_vec.size();
	 			if(count<2)
	 			count=2;
	 		 	result_vec=getEvents(1,new String [] {type,"30"});
				result_vec=getUnique(result_vec,count);
				result_vec.addAll(featured_vec); 
	 		}
	 	}
		result_vec=fillGroupEvents(result_vec);
		return result_vec;
	}
	public static ArrayList fillGroupEvents(ArrayList result_vec){
		ArrayList groupsvector=getEventGroups(); 
		if(groupsvector!=null&&groupsvector.size()>0){
			result_vec.addAll(groupsvector); 
			}	
		return result_vec;
	}
}