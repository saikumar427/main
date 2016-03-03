<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.eventbee.general.DBManager" %>
<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="java.util.Date;"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add/Edit Zone Event</title>
<script type="text/javascript">
function validate(){
var eid=String.trim(document.getElementById("eid").value);
var position=String.trim(document.getElementById("position").value);
if(eid.length<1){
alert("Event Id is empty");
return false;
}else{
if(!(IsNumeric(eid))){
	alert("Event Id should be numeric");
	return false;
}
}
if(position.length<1){
alert("Postion is empty");
return false;
}
else{
if(!(IsNumeric(position))){
	alert("position should be numeric");
	return false;
}
}
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
</script>
</head>
<body>
<%
	String zone=request.getParameter("zone");
	String eid=request.getParameter("eid");
	Date cdate=new Date();
	String eventdate=request.getParameter("eventdate");
	String add=request.getParameter("add");
	String eventid="";
	String eventname="";
	String address="";
	String button_label="Buy Tickets";
	String day=""+cdate.getDate();
	String month=""+(cdate.getMonth()+1);
	String year=""+(cdate.getYear()+1900);
	String position="";
	String seqid="";
	String external_url="";
	String url="partyzonemanage.jsp?"+"zone="+zone+"&eventdate="+eventdate;
	if("no".equals(add)){
	DBManager db=new DBManager();
	String req_seqid=request.getParameter("seqid");
	String Query="select seq_id,eventid,eventname,address,button_label,to_char(eventdate,'DD') as day,to_char(eventdate,'MM') as month,to_char(eventdate,'YYYY') as year,external_url,position from party_events where seq_id=to_number(?,'99999999')";
	StatusObj staobj=db.executeSelectQuery(Query,new String[]{req_seqid});
	eventid=db.getValue(0,"eventid",""); if(eventid==null) eventid="";
	eventname=db.getValue(0,"eventname",""); if(eventname==null) eventname="";
	address=db.getValue(0,"address",""); if(address==null) address="";
	button_label=db.getValue(0,"button_label",""); if(button_label==null) button_label="";
	day=db.getValue(0,"day",""); if(day==null) day=""+cdate.getDate();;
	month=db.getValue(0,"month",""); if(month==null) month=""+cdate.getMonth();
	year=db.getValue(0,"year",""); if(year==null) year=""+(cdate.getYear()+1900);
	position=db.getValue(0,"position",""); if(position==null) position="";
	seqid=db.getValue(0,"seq_id",""); if(seqid==null) seqid="";
	external_url=db.getValue(0,"external_url",""); if(external_url==null) external_url="";
	}
%>
<a href="<%=url%>"> Back</a>
<center><h3>Event Details</h3></center>
<form action="saveevent.jsp" onsubmit="return validate();">
<table align="center">
	<tr>
		<td></td>
		<td>MM</td>
		<td>DD</td>
		<td>YYYY</td>
	</tr>
	<tr>
		<td>Date :</td>
		<td><select name="month"> 
					<%for(int i=1;i<=12;i++){ 
					String m=i+"";
					if(i<10) m="0"+m;
					%>
					<option value="<%=m%>" <%if(month.equals(m)) {%> selected="selected" <%} %>><%=m%>
					<%} %>
		</select>
		<td><select name="day"> 
					<%for(int k=1;k<=31;k++){ 
					String d=k+"";
					if(k<10) d="0"+d;
					%>
					<option value="<%=d%>" <%if(day.equals(d)) {%> selected="selected" <%} %>><%=d%>
					<%} %>
		</select></td>
		<td><select name="year">
				<option value="2009" <%if(year.equals("2009")) {%> selected="selected" <%} %> >2009
				<option value="2010" <%if(year.equals("2010")) {%> selected="selected" <%} %> >2010
				<option value="2011" <%if(year.equals("2011")) {%> selected="selected" <%} %> >2011
				<option value="2012" <%if(year.equals("2012")) {%> selected="selected" <%} %> >2012
				<option value="2013" <%if(year.equals("2013")) {%> selected="selected" <%} %> >2013
			</select>
	</tr>
	<tr>
		<td>
			Event Id:
		</td>
		<td colspan="3">
			<input type="text" name="eid" id="eid" value="<%=eventid%>">
		</td>
	</tr>
	<tr>
		<td>
			Event Name:
		</td>
		<td colspan="3">
			<input type="text" name="ename" id="ename" value="<%=eventname%>" title="fdfgfg" >
		</td>
	</tr>
	<tr>
		<td>
			Address:
		</td>
		<td colspan="3">
			<input type="text" name="address" id="address" value="<%=address%>">
		</td>
	</tr>
	<tr>
		<td>
			Button Name:
		</td>
		<td colspan="3">
			<input type="text" name="bname" id="bname" value="<%=button_label%>">
		</td>
	</tr>
	<tr>
		<td>
			External URL:
		</td>
		<td colspan="3">
			<input type="text" name="exturl" id="exturl" value="<%=external_url%>">
		</td>
	</tr>
	<tr>
		<td>
			Position:
		</td>
		<td colspan="3">
			<input type="text" name="position" id="position" value="<%=position%>">
		</td>
	</tr>
	<tr>
		<td colspan="3" align="center"> <input type="submit" value="Submit">&nbsp;<input type="button" value="Cancel" onclick="javascript:window.location='<%=url%>'"> </td>
	</tr>
</table>
<input type="hidden" name="eventdate" value="<%=eventdate%>">
<input type="hidden" name="zone" value="<%=zone%>">
<input type="hidden" name="oldeid" value="<%=eid%>">
<input type="hidden" name="add" value="<%=add%>">
<input type="hidden" name="seqid" value="<%=seqid%>">
</form>
</body>
</html>