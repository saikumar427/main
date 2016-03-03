
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ManagerLoginForm</title>
<script language="javascript">
function checkloginname(){
	var loginname=document.getElementById("loginname").value;
	if(loginname==null || loginname==""){
		alert("Please Enter Loginname");
		 return false;
		 
	}
	else return true;
	
}
</script>
</head>
<body>
<%
String loginname_exist=request.getParameter("loginname_exist");
String memberadded=request.getParameter("memberadded");
%>
<form name="managerloginform" id="managerloginform" onsubmit="return checkloginname()" action="leadmailinfo.jsp">


<table align="center">

<tr><td align="center"><b>Manager Login Form</b></td></tr>
<%if("false".equals(loginname_exist)){ %>
<tr><td>Please enter a valid loginname.</td></tr>
<%} %>
<%if("true".equals(memberadded)){ %>
<tr><td>Member added successfully.</td></tr>
<%} %>
<tr>
<td>Loginname</td>
<td colspan="3"><input type="text" name="loginname" id="loginname" value=""></td>
</tr>
<tr>
<td align="center"><input type="submit" value="submit"></td>
</tr>
</table>
</form>
</body>
</html>