<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subscribe</title>
</head>
<body>
<script type="text/javascript">
function activatePlaceholders() 
{
	var detect = navigator.userAgent.toLowerCase();
	if (detect.indexOf("") > 0) 
	return false;
	var inputs = document.getElementsByTagName("input");
	for (var i=0;i<inputs.length;i++) 
	{
	  if (inputs[i].getAttribute("type") == "text") 
	  {
		if (inputs[i].getAttribute("placeholder") && inputs[i].getAttribute("placeholder").length > 0) 
		{
			inputs[i].value = inputs[i].getAttribute("placeholder");
			inputs[i].onfocus = function() 
			{
				 if (this.value == this.getAttribute("placeholder")) 
				 {
					  this.value = "";
					  this.style.color ="black";
				 }
				 return false;
			}
			inputs[i].onblur = function() 
			{
				 if (this.value.length < 1) 
				 {
					  this.value = this.getAttribute("placeholder");
					  this.style.color ="darkgray";
				 }
			}
		}
	  }
	}
}
window.onload=function() {
activatePlaceholders();
}
function emailValidation(){
	if(document.subscribeform.email.value == "" || document.subscribeform.email.value == "Enter Email Address")
	if(document.subscribeform.fname.value == "" || document.subscribeform.fname.value == "Enter First Name")
	if(document.subscribeform.lname.value == "" || document.subscribeform.lname.value == "Enter Last Name")
	return false;
}

function closediv(){
parent.document.getElementById('maillistwidget').style.display="none";
parent.openpopupclick=false;
parent.document.getElementById("backgroundPopup").style.display='none';	

}
</script>
<%
String listId=request.getParameter("listId");
String userId=request.getParameter("userId");
String firstname=request.getParameter("firstname");
%>
<form action="subscribecommit" name="subscribeform" method="post" onsubmit="return emailValidation();">
<%if(!"yes".equals(firstname)){ %>
<table width="70%">
<tr><td>
<input type="text" name="email" id="email" placeholder="Enter Email Address" style="color:darkgray;" >
<input type="submit" value="Subscribe"></td></tr></table>
<%}else{ %>
<table width="100%" align="center">
<tr><td width="100%" align="center">
<table width="100%" align="center">
<tr><td align="left">Email *</td><td align="left"><input type="text" name="email" id="email" placeholder="Enter Email Address" style="color:darkgray;"></td></tr>
<tr><td align="left">First Name</td><td align="left"><input type="text" name="fname" id="fname" placeholder="Enter First Name" style="color:darkgray;"></td></tr>
<tr><td align="left">Last Name</td><td align="left"><input type="text" name="lname" id="lname" placeholder="Enter Last Name" style="color:darkgray;"></td></tr>
<tr><td  align="left">Gender </td><td  align="left"><input type="radio" name="gender" value="male">Male<input type="radio" name="gender" value="female">Female</td></tr>
</table>
</td></tr>
<tr><td align="center" width="100%">
 <table align="center" width="100%">
 <tr><td align="center">
 <input type="submit" value="Submit"><input type="button" value="Cancel" id="canbtn" onclick="closediv()"></td></tr>
 </table>
</td></tr>
 </table>
<%} %>
<s:hidden name="listId"></s:hidden>
<s:hidden name="userId"></s:hidden>
</form>
</body>
<script>
</script>
</html>