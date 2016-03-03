<%@page import="com.eventbee.general.DbUtil" %>
<%
	String zone=request.getParameter("zone");
	String position=request.getParameter("pos");
	String customtype=request.getParameter("customtype");
	DbUtil.executeUpdateQuery("delete from zone_event_topbanners where position=to_number(?,'99999999') and region=?",new String[]{position,zone});
	DbUtil.executeUpdateQuery("update zone_event_topbanners set position=position-1 where position>to_number(?,'99999999') and region=?",new String[]{position,zone} );
	String url="partyzonemanage.jsp?"+"zone="+zone+"&customtype="+customtype;
	response.sendRedirect(url);
%>