<%@page import="com.eventbee.general.DbUtil" %>
<%
	String position=request.getParameter("position");
	String eventid=request.getParameter("eventid");
	String bannerurl=request.getParameter("url");
	String buttonlabel=request.getParameter("buttonlabel");
	String zone=request.getParameter("zone");
	String customtype=request.getParameter("customtype");
	DbUtil.executeUpdateQuery("update zone_event_topbanners set position=position+1 where region=? and position>=to_number(?,'999999999')",new String[]{zone,position});
	DbUtil.executeUpdateQuery("insert into zone_event_topbanners(region,image_src,eventid,button_label,position) values(?,?,to_number(?,'99999999999'),?,to_number(?,'99999999'))",new String[]{zone,bannerurl,eventid,buttonlabel,position});
	String url="partyzonemanage.jsp?"+"zone="+zone+"&customtype="+customtype;
	response.sendRedirect(url);
%>
