<%@page import="com.eventbee.general.DbUtil" %>
<%
	String position=request.getParameter("position");
	String imagehref=request.getParameter("href");
	String imageurl=request.getParameter("url");
	String zone=request.getParameter("zone");
	String customtype=request.getParameter("customtype");
	DbUtil.executeUpdateQuery("update zone_event_images set position=position+1 where region=? and position>=to_number(?,'999999999')",new String[]{zone,position});
	DbUtil.executeUpdateQuery("insert into zone_event_images(region,image_src,image_href,position) values(?,?,?,to_number(?,'99999999'))",new String[]{zone,imageurl,imagehref,position});
	String url="partyzonemanage.jsp?"+"zone="+zone+"&customtype="+customtype;
	response.sendRedirect(url);
%>
