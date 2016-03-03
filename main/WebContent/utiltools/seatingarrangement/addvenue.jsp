
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.eventbee.general.DBManager"%>
<%@page import="com.eventbee.general.StatusObj"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Venue</title>

</head>

<body>
<%
	String venuename=request.getParameter("venuename");
	String venueid=request.getParameter("venue_id");
	String add=request.getParameter("add");
	String address="";
	String country="";
	String state="";
	String city="";
	String layout_display= "";
	String layout_display_path="";
	String layout_display_link="";
	
	String url="venuemanage.jsp";
	
	if(venuename==null)
	venuename="";
	if("no".equals(add)){
	DBManager db=new DBManager();
	String Query="select * from seating_venues where venue_id=to_number(?,'99999999')";
	StatusObj staobj=db.executeSelectQuery(Query,new String[]{venueid});
	venueid=db.getValue(0,"venue_id",""); if(venueid==null) venueid="";
	venuename=db.getValue(0,"venuename",""); if(venuename==null) venuename="";
	address=db.getValue(0,"address",""); if(address==null) address="";
	city=db.getValue(0,"city",""); if(city==null) city="";
	state=db.getValue(0,"state",""); if(state==null)state="";
	country=db.getValue(0,"country",""); if(country==null)country="";
	layout_display=db.getValue(0,"layout_display","");if(layout_display==null)layout_display="";
	layout_display_path=db.getValue(0,"layout_display_path","");
	if(layout_display_path==null)layout_display_path="";
	layout_display_link=db.getValue(0,"layout_display_link","");
	if(layout_display_link==null)layout_display_link="";
	}
%>
<!-- <a href="<%=url%>"> Back</a> -->
<h3 align="center">Venue Details</h3>
<form name="addvenue" action="savevenue.jsp">
<table align="center">
	<tr>
		<td>
		     Venue Name
		</td>
		<td colspan="3">
			<input type="text" name="venuename" id="venuename" value="<%=venuename%>">
		</td>
	</tr>
	<tr>
		<td>
			Address
		</td>
		<td colspan="3">
			<input type="text" name="address" id="address" value="<%=address%>">
		</td>
	</tr>
	<tr>
		<td>
			City
		</td>
		<td colspan="3">
			<input type="text" name="city" id="city" value="<%=city%>">
		</td>
	</tr>
	<tr>
		<td>
			State:
		</td>
		<td colspan="3">
			<input type="text" name="state" id="state" value="<%=state%>">
		</td>
	</tr>
<tr>
		<td>
			Country
		</td>
		<td colspan="3">
			<input type="text" name="country" id="country" value="<%=country%>">
		</td>
	</tr>

<tr>
		<td>
			layout_display
		</td>
      <td><select id="layout_display" name="layout_display">
                 <option value="None" <%if("None".equals(layout_display)){ %> selected="selected" <%} %>> None</option>
                <option value="URL" <%if("URL".equals(layout_display)){ %> selected="selected" <%} %>>URL</option>
       </select></td>
			</tr>
  
<tr>
		<td>
			layout_display_path
		</td>
		<td colspan="3">
			<input type="text" name="layout_display_path" id="layout_display_path" value="<%=layout_display_path%>">
		</td>
      
	</tr>

<tr>
		<td>
			layout_display_link
		</td>
		<td colspan="3">
			<input type="text" name="layout_display_link" id="layout_display_link" value="<%=layout_display_link%>">
		</td>
	</tr>


	<tr>
		<td colspan="3" align="center"> <input type="submit" value="Submit">&nbsp;<input type="button" value="Cancel" onclick="javascript:window.location='venuemanage.jsp'"> </td>
	</tr>

</table>


<input type="hidden" name="venue_id" value="<%=venueid%>">
<input type="hidden" name="add" value="<%=add%>">

</form>
</body>
</html>