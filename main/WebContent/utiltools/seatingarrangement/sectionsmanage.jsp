<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.general.DBManager"%>
<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DbUtil"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
function addSection(venue_id,venuename)
{
	window.location="/main/utiltools/seatingarrangement/venuesections.jsp?venue_id="+venue_id+"&venuename="+venuename+"&add=yes";
}
function deleteSection(venueid,sectionid,venuename){
	var agree=true;
	agree=confirm("Do you really want to delete?");
	if(agree){
		window.location="/main/utiltools/seatingarrangement/deletesection.jsp?venueid="+venueid+"&sectionid="+sectionid+"&venuename="+venuename;
	}
	else {
		
	}
	}

function exportfile(venueid,sectionid,venuename){
	
   // alert("in export file function");
	
	window.location.href="/main/seatingarrangement/seatingfileupload!exportfile?venue_id="+venueid+"&section_id="+sectionid+"&venuename="+venuename;
}

</script>
<%!


ArrayList<String> getSectionsDetails(String venue_id){
	
	ArrayList sections=new ArrayList();
    DBManager db=new DBManager();
    
	String query="select * from venue_sections where venue_id=to_number(?,'999999999')";
	StatusObj statobj=db.executeSelectQuery(query,new String[]{venue_id});
	// System.out.println("status in get section details"+statobj.getStatus());
	
	 if(statobj.getStatus()){
	
		for(int i=0;i<statobj.getCount();i++){
		
	    HashMap sectionMap=new HashMap();
		sectionMap.put("venue_id",db.getValue(i,"venue_id",""));
		sectionMap.put("section_id",db.getValue(i,"section_id",""));
		sectionMap.put("noofrows",db.getValue(i,"noofrows",""));
		sectionMap.put("noofcols",db.getValue(i,"noofcols",""));
		sectionMap.put("sectionname",db.getValue(i,"sectionname",""));
		query="select count(*) as noofseats from venue_seats where venue_id=to_number(?,'999999999') and section_id=to_number(?,'999999999') and seatcode!='' and seatcode!='EMPTY' ";
	    String noofseats=DbUtil.getVal(query,new String[]{venue_id,db.getValue(i,"section_id","")});
	    if(noofseats==null) noofseats="0";
	    sectionMap.put("noofseats",noofseats);
		sections.add(sectionMap);
		
	}
	 }
	return sections;
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
<%
String venuename=request.getParameter("venuename");
String venue_id=request.getParameter("venue_id");
String url="/main/utiltools/seatingarrangement/venuemanage.jsp";
%>
  
<form  name="sectionmanage" action="">
<h4 align="center">Venuename:&nbsp;<%=venuename %><br/></h4>
<table align="center">

<tr><td>
<input type="button" name="addsection" value="Add Section" onClick="addSection('<%=venue_id%>','<%=venuename%>');"/></td>
<td><a href="<%=url%>"> All Venues </a></td>
</tr>
</table>
</form>

<br>

<%

     
	ArrayList sectionsList=new ArrayList();
	sectionsList=getSectionsDetails(venue_id);
	 if(sectionsList.size()>0){
		 
	%>	 
		 <table align="center" border="1" cellpadding="5" cellspacing="5"
				style="border-collapse: collapse; border-style: solid"
				bordercolor="black">
				<tr class="head">
					<th>Venue Id</th>
					<th>Section Id</th>
					<th>Section Name</th>
			        <th> Rows </th>
			        <th> Cols </th>
                    <th> No of Seats </th>
					<th> Manage</th>
				</tr>
		
<%    
                      String cls="";
               for(int i=0;i<sectionsList.size();i++){
	             if(i%2==0){
		              cls="even";
	                 }else{
	                    	cls="odd";
	                     }
	                HashMap sectionDetails= (HashMap)sectionsList.get(i);
		    	    String sectionname = (String) sectionDetails.get("sectionname");
					String sectionid= (String) sectionDetails.get("section_id");
				    venue_id = (String) sectionDetails.get("venue_id");
				    String  noofrows = (String) sectionDetails.get("noofrows");
				    String  noofcols = (String) sectionDetails.get("noofcols");
				    String  noofseats=(String)sectionDetails.get("noofseats");
				    int seats=Integer.parseInt(noofseats.trim());
				 //   System.out.println("sectiondetails"+sectionname+sectionid+venue_id+noofrows+noofcols);
	%>

 	<tr class="<%=cls%>">
		<td><%=venue_id%></td>
		<td><%=sectionid%></td>
		<td><%=sectionname%></td>
        <td><%=noofrows%></td>
        <td><%=noofcols%></td>
        <td><%=noofseats%></td>
	    <td align="center"><a
			href="/main/utiltools/seatingarrangement/venuesections.jsp?section_id=<%=sectionid%>&venue_id=<%=venue_id%>&venuename=<%=venuename%>&add=no">Edit</a>
		| <a href="#"
			onclick="deleteSection('<%=venue_id%>','<%=sectionid%>','<%=venuename%>');">Delete</a>
        |<a href="/main/utiltools/seatingarrangement/fileupload.jsp?section_id=<%=sectionid%>&venue_id=<%=venue_id%>&venuename=<%=venuename%>">Import</a>
       <% if(seats!=0){ %>
			 |<a href="#" onclick="exportfile('<%=venue_id%>','<%=sectionid%>','<%=venuename%>');">Export</a>
       <%} %> 
      
</td>
	</tr>
<%  } %>
</table>
<% 	    
	}
	
%> 


</body>
</html>