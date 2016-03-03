<%@page import="com.eventbee.general.DbUtil" %>
<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DBManager" %>
<%
    DBManager db=new DBManager();
    
    String venueid=request.getParameter("venueid");
    String sectionid=request.getParameter("sectionid");
    String venuename=request.getParameter("venuename");
	// System.out.println("sectionid"+sectionid+","+venueid);
   
	StatusObj statobj=DbUtil.executeUpdateQuery("delete from venue_sections where venue_id=to_number(?,'999999999') and section_id=to_number(?,'999999999')",new String[]{venueid,sectionid});
	// System.out.println("status in delete venue_sections"+statobj.getStatus());
	 statobj=DbUtil.executeUpdateQuery("delete from venue_seats where venue_id=to_number(?,'999999999') and section_id=to_number(?,'999999999')",new String[]{venueid,sectionid});
	// System.out.println("status in delete venue_seats"+statobj.getStatus());
	String url="sectionsmanage.jsp?"+"venuename="+venuename+"&venue_id="+venueid;
	response.sendRedirect(url);
%>
