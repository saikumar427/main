<%@ page import="com.google.code.facebookapi.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.*,com.eventbee.general.*" %>
<% 
String name=request.getParameter("eventname");

String category=request.getParameter("category");

String subcategory=request.getParameter("subcategory");

String host=request.getParameter("host");

String location=request.getParameter("location");

String city=request.getParameter("city");

String description=request.getParameter("description");

String start_time=request.getParameter("startdate");

String end_time=request.getParameter("enddate");

String stdate=request.getParameter("StartDate");
String enddate=request.getParameter("endDate");

String fbuid=request.getParameter("fbuid");
String groupid=request.getParameter("groupid");
String fbsessionkey= (String)session.getAttribute("FB_SESSION_KEY");
String fbconnapi=(String)session.getAttribute("FBCONNECTAPIKEY");
if(fbconnapi==null){
	fbconnapi=DbUtil.getVal("select value from config where name='ebee.fbconnect.api' and config_id=?",new String[]{"0"});
	session.setAttribute("FBCONNECTAPIKEY", fbconnapi);
}
String fbconnsecretkey=(String)session.getAttribute("FBCONNECTSECRETKEY");
if(fbconnsecretkey==null){
	fbconnsecretkey=DbUtil.getVal("select value from config where name='ebee.fbconnect.secretkey' and config_id=?",new String[]{"0"});
	session.setAttribute("FBCONNECTSECRETKEY", fbconnsecretkey);
}
try{
       
	FacebookJaxbRestClient client= new FacebookJaxbRestClient(fbconnapi,fbconnsecretkey,fbsessionkey);
	HashMap eventinfo=new HashMap();
	eventinfo.put("name",name);
	eventinfo.put("category",category);
	eventinfo.put("subcategory",subcategory);
	eventinfo.put("host",host);
	eventinfo.put("location",location);
	eventinfo.put("city",city);
	eventinfo.put("description",description);
	eventinfo.put("start_time",stdate);
	eventinfo.put("end_time",enddate);
	Long ret=client.events_create(eventinfo);	
	Long fbeid = ret;	
	String fbeventid = String.valueOf(fbeid); 	
	DbUtil.executeUpdateQuery("insert into  ebbe_fb_publishevent(fbuid,fbeventid ,eventid,created_at) values(?,?,?,now())",new String[]{fbuid,fbeventid,groupid});
	out.print("published");
}catch(Exception fbEx){
	out.println(fbEx.getMessage());	
}


%>