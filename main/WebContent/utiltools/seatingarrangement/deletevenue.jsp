<%@page import="com.eventbee.general.DbUtil" %>
<%@page import="com.eventbee.general.StatusObj"%>
<%
    String venueid=request.getParameter("venueid");
    String venuename=request.getParameter("venuename");
	
   
	StatusObj statobj=DbUtil.executeUpdateQuery("delete from seating_venues where venuename=? and venue_id=to_number(?,'999999999')",new String[]{venuename,venueid});
	//System.out.println("status in delete seating_venues"+statobj.getStatus());
	 statobj=DbUtil.executeUpdateQuery("delete from venue_sections where venue_id=to_number(?,'999999999')",new String[]{venueid});
   // System.out.println("status in delete venue_sections"+statobj.getStatus());
	 statobj=DbUtil.executeUpdateQuery("delete from venue_seats where venue_id=to_number(?,'999999999')",new String[]{venueid});
	 // System.out.println("status in delete venue_seats"+statobj.getStatus());
	String url="venuemanage.jsp";
	response.sendRedirect(url);
%>
