<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.general.DBManager"%>
<%@page import="com.eventbee.general.StatusObj"%>

<%!
ArrayList getEvents(String zone,String date){
	ArrayList events=new ArrayList();
	HashMap eventMap=new HashMap();
	DBManager db=new DBManager();
	String Query="select seq_id,eventid,eventname,address,button_label,to_char(eventdate,'DD Mon, YYYY') as date,external_url,position from party_events where region=? and eventdate=to_date(?,'YYYY-MM-DD') order by position";
	StatusObj staobj=db.executeSelectQuery(Query,new String[]{zone,date});
	for(int i=0;i<staobj.getCount();i++)
	{
		eventMap=new HashMap();
	eventMap.put("eid",db.getValue(i,"eventid",""));
	eventMap.put("name",db.getValue(i,"eventname",""));
	eventMap.put("address",db.getValue(i,"address",""));
	eventMap.put("date",db.getValue(i,"date",""));
	eventMap.put("button_label",db.getValue(i,"button_label",""));
	eventMap.put("position",db.getValue(i,"position",""));
	eventMap.put("seqid",db.getValue(i,"seq_id",""));
	eventMap.put("exturl",db.getValue(i,"external_url",""));
	events.add(eventMap);
	}
	return events;
}
ArrayList<String> getDatesforZone(String zone){
	ArrayList<String> dates=new ArrayList<String>();
	DBManager db=new DBManager();
	String Query="select distinct eventdate as date from party_events where region=? order by eventdate";
	StatusObj staobj=db.executeSelectQuery(Query,new String[]{zone});
	for(int i=0;i<staobj.getCount();i++)
	{
		dates.add(db.getValue(i,"date",""));
	}
	return dates;
}
ArrayList<String> getZones(){
	ArrayList<String> zones=new ArrayList<String>();
	DBManager db=new DBManager();
	String Query="select distinct region from zone_customise where region!=''";
	StatusObj staobj=db.executeSelectQuery(Query,null);
	for(int i=0;i<staobj.getCount();i++)
	{
		zones.add(db.getValue(i,"region",""));
	}
	return zones;
}
HashMap getZoneWordings(String zone){
	HashMap zonewords=new HashMap();
	DBManager db=new DBManager();
	String Query="select key_name,key_value from zone_customise where region=?";
	StatusObj staobj=db.executeSelectQuery(Query,new String[]{zone});
	for(int i=0;i<staobj.getCount();i++)
	{
		
		zonewords.put(db.getValue(i,"key_name",""),db.getValue(i,"key_value",""));
	}

	
	return zonewords;
}
ArrayList getImages(String zone){
	ArrayList images=new ArrayList();
	HashMap imageMap=new HashMap();
	DBManager db=new DBManager();
	String Query="select image_href,image_src,position from zone_event_images where region=? order by position";
	StatusObj staobj=db.executeSelectQuery(Query,new String[]{zone});
	for(int i=0;i<staobj.getCount();i++)
	{
		imageMap=new HashMap();
		imageMap.put("href",db.getValue(i,"image_href",""));
		imageMap.put("src",db.getValue(i,"image_src",""));
		imageMap.put("position",db.getValue(i,"poistion",""));
	
	images.add(imageMap);
	}
	
	return images;
}
ArrayList getBanners(String zone){
	ArrayList banners=new ArrayList();
	HashMap bannerMap=new HashMap();
	DBManager db=new DBManager();
	String Query="select image_src,position,eventid,button_label from zone_event_topbanners where region=? order by position";
	StatusObj staobj=db.executeSelectQuery(Query,new String[]{zone});
	for(int i=0;i<staobj.getCount();i++)
	{
		bannerMap=new HashMap();
		bannerMap.put("src",db.getValue(i,"image_src",""));
		bannerMap.put("eventid",db.getValue(i,"eventid",""));
		bannerMap.put("buttonlabel",db.getValue(i,"button_label",""));
		bannerMap.put("position",db.getValue(i,"poistion",""));
	
	banners.add(bannerMap);
	}
	
	return banners;
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Party Zone Manage</title>
<script type="text/javascript">
function submitform(){
	var date=document.getElementById("eventdate").value;
	if(date==""){}
	else
		document.dateforeventzone.submit();
}
function addEvent(zone,eventdate){
	window.location="editzoneevent.jsp?zone="+zone+"&eventdate="+eventdate+"&add=yes";
}
function deleteEvent(zone,eid,eventdate,seqid){
var agree=true;
agree=confirm("Do you really want to delete?");
if(agree){
	window.location="deleteevent.jsp?zone="+zone+"&eid="+eid+"&eventdate="+eventdate+"&seqid="+seqid;
}
else {
	
}
}
function deleteImage(zone,position,customtype){
var agree=true;
agree=confirm("Do you really want to delete?");
if(agree){
	window.location="deleteimage.jsp?zone="+zone+"&pos="+position+"&customtype="+customtype;
}
else {
	
}
}
function deleteBanner(zone,position,customtype){
var agree=true;
agree=confirm("Do you really want to delete?");
if(agree){
	window.location="deletebanner.jsp?zone="+zone+"&pos="+position+"&customtype="+customtype;
}
else {
	
}
}
function showAddImage(){
	document.getElementById('addbtn').style.display='none';
	document.getElementById('addimage').style.display='block';
}
function hideAddImage(){
	document.getElementById('addbtn').style.display='block';
	document.getElementById('addimage').style.display='none';
}
function showAddBanner(){
	document.getElementById('addbannerbtn').style.display='none';
	document.getElementById('addbanner').style.display='block';
}
function hideAddBanner(){
	document.getElementById('addbannerbtn').style.display='block';
	document.getElementById('addbanner').style.display='none';
}
function validateWordingFrom(){
	var zonecode=String.trim(document.getElementById("zonecode").value);
	if(zonecode.length<1){
		alert("Zone Code is empty");
		return false;
	}
}
function swapPosition(zone,fromposition,customtype){
	var selid="moveto"+fromposition;
	var toposition=document.getElementById(selid).value;
	if(toposition==0){
	}
	else
	window.location="swapimages.jsp?zone="+zone+"&fromposition="+fromposition+"&toposition="+toposition+"&customtype="+customtype;
}
function swapBannerPosition(zone,fromposition,customtype){
	var selid="moveto"+fromposition;
	var toposition=document.getElementById(selid).value;
	if(toposition==0){
	}
	else
	window.location="swapbanners.jsp?zone="+zone+"&fromposition="+fromposition+"&toposition="+toposition+"&customtype="+customtype;
}
function IsNumeric(sText)
{
   var ValidChars = "0123456789";
   var IsNumber=true;
   var Char;
   for (i = 0; i < sText.length && IsNumber == true; i++) 
      { 
      Char = sText.charAt(i); 
      if (ValidChars.indexOf(Char) == -1) 
         {
         IsNumber = false;
         }
      }
   return IsNumber;
}
function validateBannerForm(){
var eid=String.trim(document.getElementById("eid").value);
if(eid.length<1){
alert("Event Id is empty");
return false;
}else{
if(!(IsNumeric(eid))){
	alert("Event Id should be numeric");
	return false;
}
}
}
</script>
</head>
<body>
<%
	String zone = request.getParameter("zone");
	String customtype = request.getParameter("customtype");
	ArrayList<String> eventdates = new ArrayList<String>();
	ArrayList<String> zones = getZones();
	if (zone == null || "".equals(zone))
		zone = "sf";
	if (customtype == null || "".equals(customtype))
		customtype = "events";
	if (customtype.equals("events")) {
		eventdates = getDatesforZone(zone);
	}
	String eventdate = request.getParameter("eventdate");
	if (eventdate == null || "".equals(eventdate))
		eventdate = "";
%>

<form action="">
<table align="center">
	<tr>
		<td>Zone:</td>
		<td><select name="zone" id="zone">
			<%
				for (int i = 0; i < zones.size(); i++) {
			%>
			<option value="<%=zones.get(i)%>" <%if(zone.equals(zones.get(i))) {%>
				selected="selected" <%} %>><%=zones.get(i)%> <%
 	}
 %>
			
		</select></td>
		<td><select name="customtype">
			<option value="events" <%if(customtype.equals("events")) {%>
				selected="selected" <%} %>>Events
			<option value="wording" <%if(customtype.equals("wording")) {%>
				selected="selected" <%} %>>Customization
			<option value="images" <%if(customtype.equals("images")) {%>
				selected="selected" <%} %>>Images
			<option value="banners" <%if(customtype.equals("banners")) {%>
				selected="selected" <%} %>>Top Banners
		</select></td>
		<td><input type="submit" value="Get"></td>
	</tr>
</table>
</form>

<%
	if (customtype.equals("events")) {
%>
<%
	// if(eventdates.size()>0){
%>
<form name="dateforeventzone" action="">
<table align="center">
	<tr>
		<td>Date</td>
		<td><select name="eventdate" onchange="submitform()"
			id="eventdate">
			<option value="">---Select Date--- <%
				for (int i = 0; i < eventdates.size(); i++) {
			%>
			
			<option value="<%= eventdates.get(i)%>"
				<%if(eventdate.equals(eventdates.get(i))) {%> selected="selected"
				<%} %>><%=eventdates.get(i)%> <%
 	}
 %>
			
		</select> <input type="hidden" name="zone" value="<%=zone%>"></td>
		<td><input type="button" value="Add Event"
			onclick="addEvent('<%=zone %>','<%=eventdate %>')"></td>
	</tr>
</table>
</form>
<!-- <center><input type="button" value="Add Event" onclick="addEvent('<%=zone %>','<%=eventdate %>')"></center> -->
<br>
<%
	if (!(eventdate == null || "".equals(eventdate))) {
			ArrayList events = getEvents(zone, eventdate);
			if (events.size() > 0) {
%>
<table align="center" border="1"
	style="border-collapse: collapse; border-style: solid"
	bordercolor="black">
	<tr>
		<th>Event Id</th>
		<th>Event Name</th>
		<th>Address</th>
		<th>Button Label</th>
		<th>External URL</th>
		<th>Position</th>
		<th>Manage</th>
	</tr>
	<%
		for (int i = 0; i < events.size(); i++) {
						HashMap event = (HashMap) events.get(i);
						String name = (String) event.get("name");
						String eid = (String) event.get("eid");
						String address = (String) event.get("address");
						String button_name = (String) event
								.get("button_label");
						String position = (String) event.get("position");
						String seqid=(String)event.get("seqid");
						String exturl=(String)event.get("exturl");
	%>
	<tr>
		<td><%=eid%></td>
		<td><%=name%></td>
		<td><%=address%></td>
		<td><%=button_name%></td>
		<td><%=exturl%></td>
		<td align="center"><%=position%></td>
		<td align="center"><a
			href="editzoneevent.jsp?zone=<%=zone%>&eid=<%=eid%>&eventdate=<%=eventdate%>&add=no&seqid=<%=seqid%>">Edit</a>
		| <a href="#"
			onclick="deleteEvent('<%=zone%>','<%=eid%>','<%=eventdate%>','<%=seqid%>');">Delete</a></td>
	</tr>

	<%
		}
	%>
</table>
<%
	}
		}
%>

<%
	} else if (customtype.equals("wording")) {
		HashMap zonewordings = getZoneWordings(zone);
		String zonecode = (String) zonewordings.get("zone_code");
		if (zonecode == null || "".equals(zonecode))
			zonecode = "";
		String zonename = (String) zonewordings.get("zone_name");
		if (zonename == null || "".equals(zonename))
			zonename = "";
		String zonedomain = (String) zonewordings.get("zone_domain");
		if (zonedomain == null || "".equals(zonedomain))
			zonedomain = "";
		String listId = (String) zonewordings.get("listId");
		if (listId == null || "".equals(listId))
			listId = "";
		String listUserId = (String) zonewordings.get("listUserId");
		if (listUserId == null || "".equals(listUserId))
			listUserId = "";
		String zonefb = (String) zonewordings.get("zone_fb");
		if (zonefb == null || "".equals(zonefb))
			zonefb = "";
		String zonetwitter = (String) zonewordings.get("zone_twitter");
		if (zonetwitter == null || "".equals(zonetwitter))
			zonetwitter = "";
		String zonelistingurl = (String) zonewordings.get("zonelistingurl");
		if (zonelistingurl == null || "".equals(zonelistingurl))
			zonelistingurl = "";
		String zoneshortname = (String) zonewordings.get("zone_shortname");
		if (zoneshortname == null || "".equals(zoneshortname))
			zoneshortname = "";
		String logo = (String) zonewordings.get("zone_logo_src");
		if (logo == null || "".equals(logo))
			logo = "";
%>
<form name="wordingform" action="updatewordings.jsp" onsubmit="return validateWordingFrom();">
<table align="center">
	<tr>
		<td>Zone Code:</td>
		<td><input type="text" name="zonecode" id="zonecode" value="<%=zone%>">
		</td>
	</tr>
	<tr>
		<td>Zone Name:</td>
		<td><input type="text" name="zonename" value="<%=zonename %>">
		</td>
	</tr>
	<tr>
		<td>Zone Short Name:</td>
		<td><input type="text" name="zoneshortname" value="<%=zoneshortname%>">
		</td>
	</tr>
	<tr>
		<td>Domain:</td>
		<td><input type="text" name="zonedomain" value="<%=zonedomain %>">
		</td>
	</tr>
	<tr>
		<td>ListId:</td>
		<td><input type="text" name="listId" value="<%=listId %>">
		</td>
	</tr>
	<tr>
		<td>List UserId:</td>
		<td><input type="text" name="listUserId" value="<%=listUserId %>">
		</td>
	</tr>
	<tr>
		<td>Face Book Url:</td>
		<td><input type="text" name="zonefb" value="<%=zonefb %>">
		</td>
	</tr>
	<tr>
		<td>Twitter Url:</td>
		<td><input type="text" name="zonetwitter"
			value="<%=zonetwitter %>"></td>
	</tr>
	<tr>
		<td>Listing Url:</td>
		<td><input type="text" name="zonelistingurl"
			value="<%=zonelistingurl %>"></td>
	</tr>
	<tr>
		<td>Logo Image Url:</td>
		<td><input type="text" name="logo"
			value="<%=logo%>"></td>
	</tr>
	<tr>
		<td></td>
		<td><img src="<%=logo%>" width="300"	height="250" border="0"></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="submit"
			value="Update"></td>
	</tr>
</table>
<input type="hidden" name="customtype" value="<%=customtype %>">
<input type="hidden" name="zone" value="<%=zone %>">
</form>
<%
	} else if (customtype.equals("images")) {
		ArrayList images = getImages(zone);
		int totalimages = images.size();
		//if(images.size()>0){
%>
  <center><input type="button" value="Add Image" id="addbtn" onclick="showAddImage();">  </center>
 <div id="addimage" style="display: none">
 <form action="saveimage.jsp">
<table align="center">
<tr>
	<td>Postion :</td>
	<td><select name="position">
		<%
			for(int i=1;i<=totalimages+1;i++)
			{
		%>
			<option value="<%=i %>" <%if(i==totalimages+1){ %> selected="selected" <%} %>><%=i %>
			<%} %>
		</select>
	</td>
</tr>
<tr>
	<td>Href:</td>
	<td> <input type="text" name="href"> </td>
</tr>
<tr>
	<td>URL:</td>
	<td> <input type="text" name="url"> </td>
</tr>
<tr>
	<td align="center" colspan="2"><input type="submit" value="Add"> <input type="button" value="Cancel" onclick="hideAddImage();">  </td>
</tr>
</table>
<input type="hidden" name="zone" value="<%=zone%>">
<input type="hidden" name="customtype" value="<%=customtype%>">
</form>
  </div>
<form action="updatezoneimages.jsp">
<table align="center" border="0" >
	<%
			for (int i = 0; i < images.size(); i++) {
				HashMap image1 = (HashMap) images.get(i);
				String href = (String) image1.get("href");
				if (href == null || "".equals(href))
					href = "";
				String src = (String) image1.get("src");
				if (src == null || "".equals(src))
					src = "";
	%>
	<tr>
		<td>Position <%=i + 1%>:</td>
		<td>Href: <input type="text" name="href<%=i+1%>"
			value="<%=href %>" size="50">&nbsp; <a href="#" onclick="deleteImage('<%=zone%>','<%=i+1%>','<%=customtype%>')">Delete</a></td>
		<td>Move to position: <select name="moveto<%=i+1%>" id="moveto<%=i+1%>" onchange="swapPosition('<%=zone %>','<%=i+1 %>','<%=customtype%>');">
		<option value="0">Select
		<%for(int j=1;j<=totalimages;j++){
			if((i+1)==j) continue;
			%>
		
		<option value="<%=j%>"><%=j%>
		<%} %>
		</select>
		 </td>
	</tr>
	<tr>
		<td></td>
		<td colspan="2">URL: <input type="text" name="url<%=i+1%>" value="<%=src%>"
			size="50"></td>
	</tr>
	<tr>
		<td></td>
		<td colspan="2"><a href="<%=href%>"><img src="<%=src%>" width="300"
			height="250" border="0"></a></td>
	</tr>
	<%
		}
	%>
	<%if(totalimages>0){ %>
	<tr>
		<td colspan="2" align="center">
		<input type="submit" value="Update">
		</td>
	</tr>
	<%} %>
</table>
<input type="hidden" name="totalimages" value="<%=totalimages%>">
<input type="hidden" name="zone" value="<%=zone %>"> <input
	type="hidden" name="customtype" value="<%=customtype%>"></form>
<%
	}
%>
<%
	  if (customtype.equals("banners")) {
		ArrayList banners = getBanners(zone);
		int totalbanners = banners.size();
		//if(images.size()>0){
%>
  <center><input type="button" value="Add Top Banner" id="addbannerbtn" onclick="showAddBanner();">  </center>
  <div id="addbanner" style="display: none">
  <form action="savebanner.jsp" onsubmit="return validateBannerForm();">
<table align="center">
<tr>
	<td>Position :</td>
	<td><select name="position">
		<%
			for(int i=1;i<=totalbanners+1;i++)
			{
		%>
			<option value="<%=i %>" <%if(i==totalbanners+1){ %> selected="selected" <%} %>><%=i %>
			<%} %>
		</select>
	</td>
</tr>
<tr>
	<td>URL:</td>
	<td> <input type="text" name="url"> </td>
</tr>
<tr>
	<td>Event Id:</td>
	<td> <input type="text" name="eventid" id="eid"> </td>
</tr>
<tr>
	<td>Button Label:</td>
	<td> <input type="text" name="buttonlabel"> </td>
</tr>

<tr>
	<td align="center" colspan="2"><input type="submit" value="Add"> <input type="button" value="Cancel" onclick="hideAddBanner();">  </td>
</tr>
</table>
<input type="hidden" name="zone" value="<%=zone%>">
<input type="hidden" name="customtype" value="<%=customtype%>">
</form>
  </div>
<form action="updatebanners.jsp">
<table align="center" border="0" >
	<%
			for (int i = 0; i < banners.size(); i++) {
				HashMap banner = (HashMap) banners.get(i);
				String src = (String) banner.get("src");
				if (src == null || "".equals(src))
					src = "";
				String eventid = (String) banner.get("eventid");
				if (eventid == null || "".equals(eventid))
					eventid = "";
				String buttonlabel = (String) banner.get("buttonlabel");
				if (buttonlabel == null || "".equals(buttonlabel))
					buttonlabel = "";
				String position = (String) banner.get("position");
				if (position == null || "".equals(position))
					position = "";
	%>
	<tr>
		<td>Position <%=i+1%>:</td>
		<td>URL:</td><td> <input type="text" name="url<%=i+1%>" value="<%=src%>"
			size="50">&nbsp; <a href="#" onclick="deleteBanner('<%=zone%>','<%=i+1%>','<%=customtype%>')">Delete</a></td>
		<td>Move to position: <select name="moveto<%=i+1%>" id="moveto<%=i+1%>" onchange="swapBannerPosition('<%=zone %>','<%=i+1 %>','<%=customtype%>');">
		<option value="0">Select
		<%for(int j=1;j<=totalbanners;j++){
			if((i+1)==j) continue;
			%>
		
		<option value="<%=j%>"><%=j%>
		<%} %>
		</select>
		 </td>
	</tr>
	<tr>
		<td></td>
		<td >Event Id:</td><td colspan="2"> <input type="text" name="eventid<%=i+1%>" value="<%=eventid%>"
			size="50"></td>
	</tr>
	<tr>
		<td></td>
		<td>Buttton Label: </td><td colspan="2"> <input type="text" name="buttonlabel<%=i+1%>" value="<%=buttonlabel%>"
			size="50"></td>
	</tr>
	<tr>
		<td></td>
		<td colspan="3"><img src="<%=src%>" width="300"	height="250" border="0"></td>
	</tr>
	<%
		}
	%>
	<%if(totalbanners>0){ %>
	<tr>
		<td colspan="4" align="center">
		<input type="submit" value="Update">
		</td>
	</tr>
	<%} %>
</table>
<input type="hidden" name="totalbanners" value="<%=totalbanners%>">
<input type="hidden" name="zone" value="<%=zone %>"> <input
	type="hidden" name="customtype" value="<%=customtype%>">
</form>
<%
	}
%>
</body>
</html>