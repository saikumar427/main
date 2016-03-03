<html>
<form  action="/gettickets" method="get" name="getticketsform">
<% 
String tid="";
String result="";
tid=request.getParameter("tid");
if(tid==null)tid="";
result=request.getParameter("error");
if("invalid".equals(result))
{%>
<div>Invalid Transaction ID, there is no record with this Transaction ID, please provide valid one.</div>
<%}
if("exception".equals(result))
{%>
<div>Some internal problem occured, we are encountering problem in generating tickets, please try later.</div>
<%}%>
<table width="30%" cellpadding="0" cellspacing="0">
<tr height="10"></tr>
<tr><td>Transaction ID</td>
<td><input type="text" name="tid" id="tid" size="20" value="<%=tid%>"/></td></tr>
<tr height="5"></tr>
<tr><td></td><td><input  type="submit" value="Submit" id="submitBtn"/></td>
</tr>
</table>
</form>
</html>
<script>
var subBtn = new YAHOO.widget.Button("submitBtn");
</script>