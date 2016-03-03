<%@ page import="com.google.code.facebookapi.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.eventbee.general.*" %>
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
String fbeventid=DbUtil.getVal("select fbeventid from ebee_fb_publishevent where eventid=?",new String[]{groupid});
long eveid = Long.parseLong(fbeventid);
Long eid = new Long(eveid);
String fbsessionkey= (String)session.getAttribute("FB_SESSION_KEY");
String fbconnapi=(String)session.getAttribute("FBCONNECTAPIKEY");
if(fbconnapi==null){
	fbconnapi=DbUtil.getVal("select value from config where name='ebee.fbconnect.api' and config_id=to_number(?,'9999999999999999')",new String[]{"0"});
	session.setAttribute("FBCONNECTAPIKEY", fbconnapi);
}
String fbconnsecretkey=(String)session.getAttribute("FBCONNECTSECRETKEY");
if(fbconnsecretkey==null){
	fbconnsecretkey=DbUtil.getVal("select value from config where name='ebee.fbconnect.secretkey' and config_id=to_number(?,'9999999999999999')",new String[]{"0"});
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
	eventinfo.put("privacy_type","OPEN");
	boolean ret=client.events_edit(eid,eventinfo);
	out.print("<status>published: "+eid+"</status>");
}catch(Exception fbEx){
	out.println(fbEx.getMessage());	
}

System.out.println(fbeventid);
%>