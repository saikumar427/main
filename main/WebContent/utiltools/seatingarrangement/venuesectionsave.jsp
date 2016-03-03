<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DbUtil" %>
<%@page import="com.eventbee.general.DBManager" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Venue Section Save Page</title>
</head>
<body>
<%
    DBManager db=new DBManager();
    StatusObj statobj=null;
    String sectionname=request.getParameter("sectionname");
    String noofrows=request.getParameter("noofrows");
    String noofcols=request.getParameter("noofcols");
    String venue_id=request.getParameter("venue_id");
    String add=request.getParameter("add");
    String section_id=request.getParameter("section_id");
    String venuename=request.getParameter("venuename");
    String col_header=request.getParameter("col_header");
    String row_header=request.getParameter("row_header");
   
    if("yes".equals(add)){
        section_id=DbUtil.getVal("select nextval('venue_section_seq')",null);
    	//System.out.println(sectionname+noofrows+noofcols+venue_id+section_id);
    	DbUtil.executeUpdateQuery("insert into venue_sections(venue_id,section_id,sectionname,noofrows,noofcols,row_header,col_header) values(to_number(?,'999999999'),to_number(?,'999999999'),?,to_number(?,'999999999'),to_number(?,'999999999'),?,?)",new String[]{venue_id,section_id,sectionname,noofrows,noofcols,row_header,col_header});
    	//String url="fileupload.jsp?"+"venue_id="+venue_id+"&section_id="+section_id+"&noofrows="+noofrows+"&noofcols="+noofcols+"&venuename="+venuename;
    	String url="sectionsmanage.jsp?venuename="+venuename+"&venue_id="+venue_id;
        response.sendRedirect(url);
        
    }else{
    	
    	String query="";
    	query="update venue_sections set sectionname=?, noofrows=to_number(?,'9999999999'), noofcols=to_number(?,'9999999999'),row_header=?,col_header=? where venue_id=to_number(?,'999999999') and section_id=to_number(?,'999999999')";
    	statobj=DbUtil.executeUpdateQuery(query,new String[]{sectionname,noofrows,noofcols,row_header,col_header,venue_id,section_id});
    	System.out.println("status in update venue_sections"+statobj.getStatus());
    	String url="sectionsmanage.jsp?venuename="+venuename+"&venue_id="+venue_id;
    	response.sendRedirect(url);
    }


	
	
 %>

</body>
</html>