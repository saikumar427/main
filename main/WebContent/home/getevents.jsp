<%@ page import="java.util.*" %>
<%@ page import="com.eventbee.general.*,com.event.beans.*" %>
<%@ page import="com.event.dbhelpers.HomePageContentDB" %>
<%@ page import="com.event.beans.HomePageEventData" %>
<%!
static String GET_EVENTS_BY_LOCATION="SELECT eventname,eventid,trim(to_char(start_date,'DD Mon')) as start_date1"+
	 ", premiumlevel  from  eventinfo where (premiumlevel not in('EVENT_FEATURED_LISTING','EVENT_CUSTOM_LISTING')or premiumlevel is null ) and"+ 
	  " listType='PBL' and showinhomepage='Y' and status='ACTIVE' and end_date>now() and eventposition is not null"+
	 			" order by eventposition limit to_number(?,'999')";
	//
static String GET_FEATURED_EVENTS_BY_LOCATION="SELECT eventname,eventid,trim(to_char(start_date,'DD Mon')) as start_date1,"
			   +"premiumlevel from  eventinfo where premiumlevel in('EVENT_FEATURED_LISTING','EVENT_CUSTOM_LISTING') "
			   +"  and status='ACTIVE' and listType='PBL' and  end_date>now() order by created_at desc ";
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
String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/";
HomePageEventData hm=null;
 ArrayList result_vec=new ArrayList();
 int count=10;
 String link="";
 String type=request.getParameter("type"); 
 String base="oddbase"; 
 //result_vec=getDisplayEvents();
 boolean isInCache=EbeeCachingManager.checkCache("displayEvents",7200000);
 if(!isInCache){
	 result_vec=getDisplayEvents();
	 if(result_vec!=null && result_vec.size()>0)
	EbeeCachingManager.put("displayEvents",result_vec);
 }
 result_vec=(ArrayList)EbeeCachingManager.get("displayEvents");	
 %>
 
  <%
if(result_vec!=null&&result_vec.size()>0){
	
	String eTypeLabel="event".equals(type)?"Events":"Classes";
	
	for(int i=0;i<result_vec.size();i++){
		
			hm=new HomePageEventData();
			if(i%2==0){
				base="onebase";
			}else{
				base="onebase";
			}
			hm=(HomePageEventData)result_vec.get(i);
			link=serveraddress+"event?eid="+hm.getEventid();
	%>		<table border="0" width="100%" align="center" cellspacing="0" cellpadding="0">
	
			<tr class="<%=base%>"><td class="<%=base%>" width="10%" ><%=hm.getStartdate()%></td><td align='left' class="<%=base%>"  >
			
			<%if("Yes".equalsIgnoreCase(hm.getPremiumlevel())){%>
				 <a target="_blank" HREF="<%=link%>"><b><%=GenUtil.textToHtml(GenUtil.TruncateData(hm.getEventname(),60),true)%></b></a>
				 <%}else{%>
				 <a target="_blank" HREF="<%=link%>"><%=GenUtil.textToHtml(GenUtil.TruncateData(hm.getEventname(),65),true)%></a>
			<%}%>
			</td></tr><tr><td height="5"></td></tr>
			</table>
		<%}

}
%>
