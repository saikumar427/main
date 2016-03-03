
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Venue Seats</title>
</head>
<body>
<form name="fileuploadform" action="filesave.jsp">
<h3 align="center">File Upload</h3>
<table align="center">
<tr>
		<td>
		     Section Name:
		</td>
		<td colspan="3">
			<input type="text" name="sectionname" id="sectionname" value="">
		</td>
	</tr>
	<tr>
		<td>
			No of Rows:
		</td>
		<td colspan="3">
			<input type="text" name="noofrows" id="noofrows" value="">
		</td>
	</tr>
	<tr>
		<td>
			No of Columns:
		</td>
		<td colspan="3">
			<input type="text" name="noofcols" id="noofcols" value="">
		</td>
	</tr>
   <tr>
		<td colspan="3" align="center"> <input type="submit" value="Submit">&nbsp;<input type="button" value="Cancel" onclick="javascript:window.location='venuemanage.jsp'"> </td>
	</tr>

	
</table>

<input type="hidden" name="venue_id" value="<%=request.getParameter("venue_id")%>">
</form>
</body>
</html>

