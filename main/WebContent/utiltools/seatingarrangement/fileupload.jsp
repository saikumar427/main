
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.eventbee.general.DBManager"%>
<%@page import="com.eventbee.general.StatusObj"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>


<html>
<head>

<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="/main/css/displaytag.css">

<script type="text/javascript">
	function cancel(section_id, venue_id, venuename) {
		window.location = "/main/utiltools/seatingarrangement/sectionsmanage.jsp?section_id="
				+ section_id
				+ "&venue_id="
				+ venue_id
				+ "&venuename="
				+ venuename;
	}
</script>
</head>
<body>

<%
	DBManager db = new DBManager();
	String venue_id = request.getParameter("venue_id");
	String venuename = request.getParameter("venuename");
	String section_id = request.getParameter("section_id");
	String sectionname = "";
	String noofrows = "";
	String noofcols = "";
	String query = "select * from venue_sections where venue_id=to_number(?,'999999999') and section_id=to_number(?,'999999999')";
	StatusObj statobj = db.executeSelectQuery(query, new String[] {
			venue_id, section_id });
	// System.out.println("status of venuesections"+statobj.getStatus());
	if (statobj.getStatus()) {
		for (int i = 0; i < statobj.getCount(); i++) {
			sectionname = db.getValue(i, "sectionname", "");
			noofrows = db.getValue(i, "noofrows", "");
			noofcols = db.getValue(i, "noofcols", "");
		}
	}
	String url = "sectionsmanage.jsp?venuename=" + venuename
			+ "&venue_id=" + venue_id;
%>
<!-- <a href="<%=url%>"> Back </a> -->

<form id="fileForm"
	action="/main/seatingarrangement/seatingfileupload!save" method="post"
	enctype="multipart/form-data">

<table align="center">
	<tr>
		<td>
		<h3>File Upload Page</h3>
		</td>
	</tr>
	<tr>
		<td colspan="2"><s:actionerror theme="simple" /></td>
	</tr>
	<tr>
		<td>Venue Name: &nbsp;<%=venuename%></td>
	</tr>
	<tr>
		<td>Section Name: &nbsp;<%=sectionname%></td>
	</tr>
	<tr>
		<td>Rows: &nbsp;<%=noofrows%></td>
	</tr>
	<tr>
		<td>Cols: &nbsp;<%=noofcols%></td>
	</tr>
	<tr>
		<td>Select file to upload: <s:file name="upload" label="File"
			id="upload" /></td>
	</tr>
	
	<tr>
		<td><input type="submit" value="Upload" />&nbsp; <input
			type="button" value="Cancel"
			onclick="cancel('<%=section_id%>','<%=venue_id%>','<%=venuename%>')"></td>
	</tr>


         
</table>
<input type="hidden" name="venue_id" id="venue_id"
	value="<%=venue_id%>" /> <input type="hidden" name="section_id"
	id="section_id" value="<%=section_id%>" /> <input type="hidden"
	name="venuename" id="venuename" value="<%=venuename%>" /> <input
	type="hidden" name="noofrows" id="noofrows" value="<%=noofrows%>" /> 
  <input type="hidden" name="noofcols" id="noofcols" value="<%=noofcols%>" />
  

</form>

</body>
</html>