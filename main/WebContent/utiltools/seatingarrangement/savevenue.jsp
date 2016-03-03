<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DbUtil" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Save Venue Page</title>
</head>
<body>
<%
    StatusObj statobj=null;
    String venuename=request.getParameter("venuename");
    String venue_id=request.getParameter("venue_id");
    String address=request.getParameter("address");
	String city=request.getParameter("city");
	String state=request.getParameter("state");
	String country=request.getParameter("country");
	String add=request.getParameter("add");
	String layout_display=request.getParameter("layout_display");
	String layout_display_path=request.getParameter("layout_display_path");
	String layout_display_link=request.getParameter("layout_display_link");
	String url="";
	
	if("yes".equals(add)){
		   venue_id=DbUtil.getVal("select nextval('venue_seq')",null);
		  DbUtil.executeUpdateQuery("insert into seating_venues(venue_id,venuename,address,city,state,country,"+
				 " layout_display,layout_display_path,layout_display_link ) "+
				  " values(to_number(?,'999999999'),?,?,?,?,?,?,?,?)",new String[]{venue_id,venuename,address,city,state,country,layout_display,layout_display_path,layout_display_link});
		  url="venuemanage.jsp";
			
	}
	else{
		 
		  String query="update seating_venues  set venuename=?,address=?,city=?,state=?,country=?,"+
				 " layout_display=?,layout_display_path=?,layout_display_link=? where venue_id=to_number(?,'999999999')";
		  statobj=DbUtil.executeUpdateQuery(query,new String[]{venuename,address,city,state,country,layout_display,layout_display_path,layout_display_link,venue_id});
		 // System.out.println("uppdate details"+venue_id+","+venuename+","+address+","+city+","+state+","+country);
		 //System.out.println("status of update seating_venues"+statobj.getStatus());
		  url="venuemanage.jsp";
	}


	 
    response.sendRedirect(url);
 %>

</body>
</html>