<%@ page import="java.util.*" %>
<%@ page import="com.eventbee.general.*,com.event.beans.*" %>
<%@ page import="com.event.dbhelpers.HomePageContentDB" %>
<%@ page import="com.event.beans.HomePageEventData" %>
<%!
static String GET_EVENTS_BY_LOCATION="SELECT eventname,eventid,trim(to_char(e.start_date,'DD Mon')) as start_date1,"
	+" e.created_at as created, premiumlevel,count(price.price_id) as tcount"
	+"  from  eventinfo e left outer join price on price.evt_id=e.eventid"
	+" where (premiumlevel not in('EVENT_FEATURED_LISTING','EVENT_CUSTOM_LISTING')or premiumlevel is null ) and "
	+"   listType='PBL' and showinhomepage='Y' and e.status='ACTIVE' "
	+" and price.evt_id=e.eventid group by eventname,eventid,photourl,start_date1,premiumlevel, created " +
			"order by e.created_at desc limit to_number(?,'999')";
	//
static String GET_FEATURED_EVENTS_BY_LOCATION="SELECT eventname,eventid,trim(to_char(e.start_date,'DD Mon')) as start_date1,"
			   +" e.created_at as created, premiumlevel,count(price.price_id) as tcount"
				   +" from  eventinfo e left outer join price on price.evt_id=e.eventid"
				   +" where premiumlevel in('EVENT_FEATURED_LISTING','EVENT_CUSTOM_LISTING') "
			   +"  and e.status='ACTIVE' and listType='PBL' "
			   +" group by eventname,eventid,photourl,start_date1,premiumlevel, created " +
			   		"order by e.created_at desc ";
public static ArrayList getEvents(int index,String []params){
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
public static ArrayList getDisplayEvents(){
	//System.out.println("getting features events from DB");
	ArrayList featured_vec=new ArrayList();
	
	ArrayList result_vec=new ArrayList();
	 featured_vec=getEvents(2,new String [] {});
	 featured_vec=HomePageContentDB.getUnique(featured_vec,-1);
	 
		if(featured_vec!=null)
	 	{	
			if(featured_vec.size()>=12){
	 			result_vec=getEvents(1,new String [] {"20"});
	 			result_vec=HomePageContentDB.getUnique(result_vec,2);
	 			result_vec.addAll(featured_vec); 
	 		}
	 		else {  
	 			int count=12-featured_vec.size();
	 			if(count<2)
	 			count=2;
	 		 	result_vec=getEvents(1,new String [] {"30"});
				result_vec=HomePageContentDB.getUnique(result_vec,count);
				result_vec.addAll(featured_vec); 
	 		}
			
	 	}
		
		
		return result_vec;
	}

%>
<%
HomePageEventData hm=null;
 ArrayList result_vec=new ArrayList();
 boolean isInCache=EbeeCachingManager.checkCache("TestFeatureEvents",120000);
 out.println("<b>");
 if(!isInCache){
	EbeeCachingManager.put("TestFeatureEvents",getDisplayEvents());
	out.println("<br>Getting from DB");
 }else{
	 out.println("Getting from Cache");
 }
 out.println("</b>");
 result_vec=(ArrayList)EbeeCachingManager.get("TestFeatureEvents");
 out.println("<br>Data: "+result_vec);
 %>
 