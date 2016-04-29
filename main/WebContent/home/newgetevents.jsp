<%@ page import="java.util.*" %>
<%@ page import="com.eventbee.general.*,com.event.beans.*" %>
<%@ page import="com.event.beans.HomePageEventData,org.json.JSONObject" %>
<%@include file="../getresourcespath.jsp" %>
<%!
    static String GET_EVENTS_BY_LOCATION="SELECT eventname,eventid,trim(to_char(start_date,'Dy, Mon DD, YYYY' )) as start_date1,starttime,"+
	               " config_id,premiumlevel,address1,address2,city,state,country  from  eventinfo where (premiumlevel not in('EVENT_FEATURED_LISTING','EVENT_CUSTOM_LISTING') or premiumlevel is null )"+
	               " and listType='PBL' and showinhomepage='Y' and status='ACTIVE' and end_date>now() and eventposition is not null order by eventposition limit to_number(?,'999')";	
	               
	static String GET_FEATURED_EVENTS_BY_LOCATION="SELECT eventname,eventid,trim(to_char(start_date,'Day, Mon DD, YYYY' )) as start_date1,starttime,"
			   +" config_id,premiumlevel,address1,address2,city,state,country from  eventinfo where premiumlevel in('EVENT_FEATURED_LISTING','EVENT_CUSTOM_LISTING') "
			   +"  and status='ACTIVE' and listType='PBL' and  end_date>now() order by created_at desc ";
    
    static ArrayList<String> eventsarr=new ArrayList<String>();
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
				  eventsarr.add(dbmanager.getValue(i,"eventid",""));
				  hpData.setConfigid(dbmanager.getValue(i,"config_id",""));
				  hpData.setEventid(dbmanager.getValue(i,"eventid",""));
				  hpData.setEventname(dbmanager.getValue(i,"eventname",""));
				  hpData.setStartdate(dbmanager.getValue(i,"start_date1",""));
				  hpData.setStarttime(DateConverter.getTimeAM(dbmanager.getValue(i,"starttime","")));
				  hpData.setAddress1(dbmanager.getValue(i,"address1",""));
				  hpData.setAddress2(dbmanager.getValue(i,"address2",""));
				  hpData.setCity(dbmanager.getValue(i,"city",""));
				  hpData.setState(dbmanager.getValue(i,"state",""));
				  hpData.setCountry(dbmanager.getValue(i,"country",""));
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
	 featured_vec=getUnique(featured_vec,-1);
	 
		if(featured_vec!=null)
	 	{	
			if(featured_vec.size()>=12){
	 			result_vec=getEvents(1,new String [] {"20"});
	 			result_vec=getUnique(result_vec,2);
	 			result_vec.addAll(featured_vec); 
	 		}
	 		else {  
	 			int count=12-featured_vec.size();
	 			if(count<2)
	 			count=2;
	 		 	result_vec=getEvents(1,new String [] {"30"});
				result_vec=getUnique(result_vec,count);
				result_vec.addAll(featured_vec); 
	 		}
			
	 	}
		return result_vec;
	}

  public static String getAddressLine(String address1,String address2,String city,String state,String country){
	  String addressline="";
	  /* if(!"".equals(address1))
		  addressline=address1;
	  if(!"".equals(addressline) && !"".equals(address2))
		  addressline=addressline+", "+address2;
	  if(!"".equals(addressline) && !"".equals(city))
		  addressline=addressline+", "+city;
	  if(!"".equals(addressline) && !"".equals(state))
		  addressline=addressline+", "+state;
	  if(!"".equals(addressline) && !"".equals(country))
		  addressline=addressline+", "+country; */
		  
		  if(!"".equals(address1))
			  addressline=address1+", ";
		  if(!"".equals(address2))
			  addressline+=address2+", ";
		  if(!"".equals(city))
			  addressline+=city+", ";
		  if(!"".equals(state))
			  addressline+=state+", ";
		  if(!"".equals(country))
			  addressline+=country;
		  
		  addressline = addressline.replaceAll(", $", "");
		  
	  return addressline;
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
	 
 
public static String TruncateData(String basedata, int tsize){
	if(basedata==null) return "";
	if(basedata.length()<=tsize) return basedata;
	return basedata.substring(0,tsize-1 )+"...";
}

public static String getFEventsJsonData(ArrayList result_vec,String type,ArrayList<String> eventsarr){
	String feventsdata="",link="";
	HomePageEventData hm=null;
	JSONObject json=new JSONObject();
	String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/";
	ArrayList<HashMap<String,String>> feventsarr=new ArrayList<HashMap<String,String>>();
	HashMap<String,String> logomap=new HashMap<String,String>();
	logomap=getLogoMap(eventsarr);
	 if(result_vec!=null&&result_vec.size()>0){
	  String eTypeLabel="event".equals(type)?"Events":"Classes";
		for(int i=0;i<result_vec.size();i++){
			    hm=new HomePageEventData();
				HashMap<String,String> hmap=new HashMap<String,String>();
				hm=(HomePageEventData)result_vec.get(i);
				String addressline=getAddressLine(hm.getAddress1(),hm.getAddress2(),hm.getCity(),hm.getState(),hm.getCountry());
				String datetime=hm.getStartdate()+", "+hm.getStarttime();
				link=serveraddress+"event?eid="+hm.getEventid();
				String logo=logomap.get(hm.getEventid());
				if(logo==null)logo="/main/images/nopic.gif";
				hmap.put("logo",logo);
				hmap.put("en",GenUtil.textToHtml(GenUtil.TruncateData(hm.getEventname(),65),true));
				hmap.put("link",link);
				hmap.put("dt",datetime);
				hmap.put("adrs",addressline);
				feventsarr.add(hmap);}
		}
	try{
	json.put("fevents",feventsarr);
	}catch(Exception e){System.out.println("Exception occured while preparing is:"+e.getMessage());}
	feventsdata=json.toString();
	return feventsdata;
}

public static HashMap<String,String> getLogoMap(ArrayList<String> eventsarr){
	 String eventids="";
	 HashMap<String,String> logomap=new HashMap<String,String>();
	 try{
	 if(eventsarr.size()>0){
		 for(int i=0;i<eventsarr.size();i++){
			 if(i==0)
				 eventids=eventsarr.get(i);
			 else
				 eventids=eventids+","+eventsarr.get(i);
		 }
	 }
	 
	 DBManager dbmanager=new DBManager();
	 String query="select eventid,path from featured_events_images where eventid in("+eventids+")";
	 StatusObj statobj=null;
	 if(!"".equals(eventids))
	 statobj=dbmanager.executeSelectQuery(query, null);
	 if(statobj.getStatus() && statobj.getCount()>0){
		 for(int j=0;j<statobj.getCount();j++)
		 logomap.put(dbmanager.getValue(j,"eventid",""),dbmanager.getValue(j,"path",""));
	 }
	 }catch(Exception e){
		 System.out.println("Exception occured in getLogoMap :: "+e.getMessage());
	 }
	 return logomap;
}
%>
<%
ArrayList result_vec=new ArrayList();
String feventsdata="";
String type=request.getParameter("type"); 
boolean isInCache=EbeeCachingManager.checkCache("displayEvents",7200000);
if(!isInCache){
	 result_vec=getDisplayEvents();
	 feventsdata=getFEventsJsonData(result_vec,type,eventsarr);
	 if(feventsdata!=null && !"".equals(feventsdata))
	 EbeeCachingManager.put("displayEvents",feventsdata);
 }
feventsdata=EbeeCachingManager.get("displayEvents")+"";

out.println(feventsdata);

%>
