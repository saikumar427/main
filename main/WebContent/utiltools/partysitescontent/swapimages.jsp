<%@page import="com.eventbee.general.DbUtil" %>
<%
String from=request.getParameter("fromposition");
String to=request.getParameter("toposition");
String zone=request.getParameter("zone");
String customtype=request.getParameter("customtype");
DbUtil.executeUpdateQuery("update zone_event_images set position=99999999 where position=to_number(?,'99999999') and region=?",new String[]{from,zone});
DbUtil.executeUpdateQuery("update zone_event_images set position=to_number(?,'99999999') where position=to_number(?,'99999999') and region=?",new String[]{from,to,zone});
DbUtil.executeUpdateQuery("update zone_event_images set position=to_number(?,'99999999') where position=99999999 and region=?",new String[]{to,zone});
String url="partyzonemanage.jsp?"+"zone="+zone+"&customtype="+customtype;
response.sendRedirect(url);
%>
