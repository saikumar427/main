<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.general.DBManager"%>
<%@page import="com.eventbee.general.StatusObj"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Venue Manage Page</title>
<script>


function addVenue(){
	window.location="addvenue.jsp?add=yes";
}
function deleteVenue(venueid,venuename){
	var agree=true;
	agree=confirm("Do you really want to delete?");
	if(agree){
		window.location="deletevenue.jsp?venueid="+venueid+"&venuename="+venuename;
	}
	else {
		
	}
	}

</script>
<%!

ArrayList<String> getVenueDetails(){
	
	 ArrayList venues=new ArrayList();
    DBManager db=new DBManager();
	 String query="select * from seating_venues ";
	StatusObj statobj=db.executeSelectQuery(query,new String[]{});
	if(statobj.getStatus()){
		
	   for(int i=0;i<statobj.getCount();i++){
		      HashMap venueMap=new HashMap();
		      venueMap.put("venuename",db.getValue(i,"venuename",""));
		      venueMap.put("venue_id",db.getValue(i,"venue_id",""));
		      venueMap.put("address",db.getValue(i,"address",""));
		      venueMap.put("city",db.getValue(i,"city",""));
		      venues.add(venueMap);  
	  }
	}
	
	return venues;
    }


%>


<style type="text/css">
.odd{ 
background-color:#E4FAE6; 
} 
.even{
background-color:#F1FCF2;
}
.head{ 
background-color:silver; 
} 

</style>

</head>
<body>

<form  name="venuemanage" action="">
<h3 align="center">Venue Manage Page</h3>
<table align="center">
<tr><td>
<input type="button" name="addvenue" value="Add Venue" onClick="addVenue();"/></td></tr>
</table>
</form>
<br>
<%
	
			ArrayList venueList = getVenueDetails();
			if (venueList.size() > 0) {
%>
<table align="center" border="1" cellpadding="5" cellspacing="5"
	style="border-collapse: collapse; border-style: solid"
	bordercolor="black">
	<tr class="head">
		<th>Venue Id</th>
		<th>Venue Name</th>
		<th> Address </th>
        <th> City </th>
		<th> Manage </th>
	</tr>

<%
          String cls="";
		for (int i = 0; i < venueList.size(); i++) {
			              if(i%2==0){
			            	  cls="even";
			            	  }
			              else{
			            	  cls="odd";
			              }
						HashMap venueDetails= (HashMap)venueList.get(i);
						String venuename = (String) venueDetails.get("venuename");
						String address = (String) venueDetails.get("address");
						String venue_id = (String) venueDetails.get("venue_id");
						String city=(String) venueDetails.get("city");
						
	%>
	<tr class="<%=cls%>">
		<td><%=venue_id%></td>
		<td><%=venuename%></td>
		<td><%=address%></td>
        <td><%=city %></td>
	    <td align="center"><a
			href="addvenue.jsp?venuename=<%=venuename%>&venue_id=<%=venue_id%>&add=no">Edit</a>
		| <a href="#"
			onclick="deleteVenue('<%=venue_id%>','<%=venuename%>');">Delete</a>
        | <a href="sectionsmanage.jsp?venuename=<%=venuename%>&venue_id=<%=venue_id%>"
			>Sections</a>
</td>
	</tr>

	<%
		}
	%>
</table>
<%
	}
	
%>
</body>
</html>