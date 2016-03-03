<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.eventbee.general.DBManager"%>
<%@page import="com.eventbee.general.StatusObj"%>
<%@taglib uri="/struts-tags" prefix="s" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Venue Sections</title>
<script>
function cancel(venue_id,venuename){
	
	window.location="sectionsmanage.jsp?venuename="+venuename+"&venue_id="+venue_id;
	
}
</script>
</head>
<body>

<%
      String sectionid=request.getParameter("section_id");
     if(" ".equals(sectionid)){
    	System.out.println("sectionid:"+sectionid);%>
     <h3 align="center">Add Section</h3>
      <%}else{ %>
      <h3 align="center">Edit Section</h3>
      <%} 

  
    DBManager db=new DBManager();
    StatusObj statobj=null;
	String venueid=request.getParameter("venue_id");
	String venuename=request.getParameter("venuename");
    String add=request.getParameter("add");
	String noofrows="";
	String noofcols="";
	String sectionname="";
	String row_header="";
	String col_header="";
	if("no".equals(add)){
	
	String Query="select * from venue_sections where venue_id=to_number(?,'999999999') and section_id=to_number(?,'999999999')";
	statobj=db.executeSelectQuery(Query,new String[]{venueid,sectionid});
	venueid=db.getValue(0,"venue_id",""); if(venueid==null) venueid="";
	sectionid=db.getValue(0,"section_id",""); if(sectionid==null) sectionid="";
	sectionname=db.getValue(0,"sectionname","");if(sectionname==null)sectionname="";
	noofrows=db.getValue(0,"noofrows","");if(noofrows==null)noofrows="";
	noofcols=db.getValue(0,"noofcols","");if(noofcols==null)noofcols="";
	row_header=db.getValue(0,"row_header","");if(row_header==null)row_header="";
	col_header=db.getValue(0,"col_header","");if(col_header==null)col_header="";
	
	}
	
	
	String url="sectionsmanage.jsp?venuename="+venuename+"&venue_id="+venueid;
	
%>
<!-- <a href="<%=url%>"> Back</a> -->
<form name="venuesections" action="venuesectionsave.jsp">
<table align="center">
<tr><td> Venue Name:&nbsp;<%=venuename%></td></tr>
<tr>
		<td>
		     Section Name
		</td>
		<td colspan="3">
			<input type="text" name="sectionname" id="sectionname" value="<%=sectionname %>">
		</td>
	</tr>
	<tr>
		<td>
			No of Rows
		</td>
		<td colspan="3">
			<input type="text" name="noofrows" id="noofrows" value="<%=noofrows%>">
		</td>
	</tr>
	<tr>
		<td>
			No of Columns
		</td>
		<td colspan="3">
			<input type="text" name="noofcols" id="noofcols" value="<%=noofcols %>">
		</td>
	</tr>
      	<tr>
		<td>
			Row_header
		</td>
		<td colspan="3">
			<input type="text" name="row_header" id="row_header" value="<%=row_header%>">
		</td>
	</tr>
    <tr>
		<td>
			Col_header
		</td>
		<td colspan="3">
			<input type="text" name="col_header" id="col_header" value="<%=col_header%>">
		</td>
	</tr>

   <tr>
		<td colspan="3" align="center"> <input type="submit" value="Submit">&nbsp;<input type="button" value="Cancel" onclick="cancel('<%=venueid%>','<%=venuename%>');"> </td>
	</tr>

	
</table>

<input type="hidden" name="venue_id" value="<%=venueid%>">
<input type="hidden" name="section_id" value="<%=sectionid%>">
<input type="hidden" name="add" value="<%=add%>">
<input type="hidden" name="venuename" value="<%=venuename%>">
</form>
</body>
</html>