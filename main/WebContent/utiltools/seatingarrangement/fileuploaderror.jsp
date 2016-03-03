
<html>
<head>
</head>
<body>
<%
String venue_id=request.getParameter("venue_id");
String section_id=request.getParameter("section_id");
String venuename=request.getParameter("venuename");
String url="/main/utiltools/seatingarrangement/sectionsmanage.jsp?section_id="+ section_id+"&venue_id="+ venue_id+ "&venuename="+ venuename;
%>
<h3 align="center">
<a href="<%=url%>">Back</a></br>
There is an error in File Upload
</h3>
</body>
</html>

