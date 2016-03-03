<%@page import="com.eventmanage.seating.helpers.SeatingJsonHelper"%>
<% 
String sectionid=request.getParameter("sectionid");
String eid=request.getParameter("eid");
String isrecurring=request.getParameter("isrecurring");
String seatjson=SeatingJsonHelper.getSectionSeatingDetails(sectionid, eid,"",isrecurring).toString();
out.println("seatdata='"+seatjson+"';");
%>