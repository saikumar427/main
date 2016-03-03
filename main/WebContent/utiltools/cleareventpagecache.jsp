<%@page import="com.event.dbhelpers.EventPageContentDB"%>
<%
try{
String eid=request.getParameter("eid");
if(eid==null)eid="";
if(!"".equals(eid)){
EventPageContentDB.removeGLobalEventCache(eid);
System.out.println("eventpagecache cleared successfully:"+eid);
}
}catch(Exception e){
	System.out.println("Exception occured in clear eventpagecache:"+e.getMessage());
}
%>