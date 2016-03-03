<%@page import="com.eventbee.general.DbUtil" %>
<%
	String zone=request.getParameter("zone");
	String zonename=request.getParameter("zonename");
	String zonecode=request.getParameter("zonecode");
	String zonedomain=request.getParameter("zonedomain");
	String zonefb=request.getParameter("zonefb");
	String zonetwitter=request.getParameter("zonetwitter");
	String zonelistingurl=request.getParameter("zonelistingurl");
	String listId=request.getParameter("listId");
	String listUserId=request.getParameter("listUserId");
	String logo=request.getParameter("logo");
	String zoneshortname=request.getParameter("zoneshortname");
	String customtype=request.getParameter("customtype");
	DbUtil.executeUpdateQuery("delete from zone_customise where region=?",new String[]{zonecode});
	DbUtil.executeUpdateQuery("insert into zone_customise(region,key_name,key_value) values(?,'zone_name',?) ",new String[]{zonecode,zonename});
	//DbUtil.executeUpdateQuery("insert into zone_customise(region,key_name,key_value) values(?,'zone_code',?) ",new String[]{zonecode,zonecode});
	DbUtil.executeUpdateQuery("insert into zone_customise(region,key_name,key_value) values(?,'zone_domain',?) ",new String[]{zonecode,zonedomain});
	DbUtil.executeUpdateQuery("insert into zone_customise(region,key_name,key_value) values(?,'zone_fb',?) ",new String[]{zonecode,zonefb});
	DbUtil.executeUpdateQuery("insert into zone_customise(region,key_name,key_value) values(?,'zone_twitter',?) ",new String[]{zonecode,zonetwitter});
	DbUtil.executeUpdateQuery("insert into zone_customise(region,key_name,key_value) values(?,'listId',?) ",new String[]{zonecode,listId});
	DbUtil.executeUpdateQuery("insert into zone_customise(region,key_name,key_value) values(?,'zonelistingurl',?) ",new String[]{zonecode,zonelistingurl});
	DbUtil.executeUpdateQuery("insert into zone_customise(region,key_name,key_value) values(?,'zone_shortname',?) ",new String[]{zonecode,zoneshortname});
	DbUtil.executeUpdateQuery("insert into zone_customise(region,key_name,key_value) values(?,'listUserId',?) ",new String[]{zonecode,listUserId});
	DbUtil.executeUpdateQuery("insert into zone_customise(region,key_name,key_value) values(?,'zone_logo_src',?) ",new String[]{zonecode,logo});
	String url="partyzonemanage.jsp?"+"zone="+zonecode+"&customtype="+customtype;
	response.sendRedirect(url);
%>