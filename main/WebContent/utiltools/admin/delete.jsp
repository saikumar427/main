<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete User Account</title>
<script type="text/javascript">
	function checkValidity() {

		if (!(document.getElementById("login1").checked || document
				.getElementById("login2").checked)) {
			alert("Please Select atleast one value");
			return false;
		} else
			return true;

	}
	function checktransactionid(){
		var transactionid=document.getElementById("transactionid").value;
		if(transactionid==null || transactionid==""){
			alert("Transactionid is empty");
			return false;
			
		}else return true;
	}
</script>
</head>
<body>
<%

String tidexist="";
String userexist=request.getParameter("userexist");
tidexist=request.getParameter("tidexist");
String deletetransaction=request.getParameter("deletetransaction");
String deleteaccount=request.getParameter("deleteaccount");
%>
<table align="left" border="0" width="840px">
	<tr>
		<td valign='top'  width="50%" style="border: 1px solid">
		<form name="deleteuseraccountform" id="deleteuseraccountform"
			action="deleteaccount.jsp" onsubmit=" return checkValidity();">

		
        
		<table align="left" cellpadding="5" cellspacing="5" >
            <tr> <td><b>Delete Account</b></td></tr>
         <%if("false".equals(userexist)){ %> 
             <tr><td><center>Invalid user.</center></td></tr>
         <%}if("true".equals(deleteaccount)){ %>  <tr><td><center>Deleted Successfully.</center></td></tr>
         <%} %> 
			<tr>

				<td><input type="radio" name="logintype" id="login1"
					value="loginname">Login Name</td>
				<td colspan="3"><input type="text" name="loginname"
					id="loginname" value=""></td>
			</tr>
			<tr>
				<td>Or</td>
			</tr>
			<tr>

				<td><input type="radio" name="logintype" id="login2"
					value="userid">User Id</td>
				<td><input type="text" name="userid" id="userid" value="">
				</td>

			</tr>
			<tr>
				<td align="center"><input type="submit" value="Delete" /></td>
			</tr>
		</table>

	</form>

	</td>
 <td></td>

	<td valign='top' width="50%" style="border: 1px solid">

	<form name="deletetransactionform" id="deletetransactionform"
		action="deletetransaction.jsp" onsubmit="return checktransactionid()">
	
	<table align="left" border="0" cellpadding="5" cellspacing="5">
        <tr> <td><b>Delete Transaction</b></td></tr>
        <%if("false".equals(tidexist)){ %><tr><td>Invalid TransactionId.</td></tr>
         <%}if("true".equals(deletetransaction)){%><tr><td>Deleted Successfully.</td></tr>
            <%} %>
		<tr>

			<td>Transaction Id</td>
			<td colspan="3"><input type="text" name="transactionid"
				id="transactionid" value=""></td>
		</tr>

		<tr>
			<td align="center"><input type="submit" value="Delete" /></td>
		</tr>
	</table>
	</form>
	</td>
	</tr>
</table>
</body>
</html>