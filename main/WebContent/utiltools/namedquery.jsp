<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DbQuery</title>
<link href="../css/namedquery.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="JavaScript" src="../js/ajaxjson.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/namedquery.js"></script>
</head>
<body bgcolor="#666666">
<form id="dynaquery" method="post">
<br>
<div class="head" align="center">Dynamic Quries</div>
<div id="errors" class="error"></div>
<div id="success" class="final"></div>
<div class="tabledata">
	<table>
		<tr>
			<td colspan="2" align="center"><input type="radio" name="qtype" value="select" checked="checked" onblur="showBoth()">Select&nbsp;&nbsp;&nbsp;<input type="radio" name="qtype" value="update" onblur="showInp()">Update</td>
		</tr>
		<tr>
			<td>Query Name</td>
			<td><input type="text" name="queryname" title="Enter query name here"></td>
		</tr>
		<tr>
			<td>Module</td>
			<td><input type="text" name="modulename" title="Enter module name here"></td>
		</tr>
		<tr>
			<td>Query</td>
			<td><textarea name="query" rows="6" cols="80" title="Enter query here"></textarea></td>
		</tr>
		<tr>
			<td>DSN</td>
			<td><input type="text" name="dsn" title="Enter data source name here"></td>
		</tr>
		<tr>
			<td>No of InPutParams</td>
			<td><input type="text" name="inpnumber" id="inpnumber" value="0"></td>
		</tr>
		<tr>
			<td>No of OutPutParams</td>
			<td><input type="text" name="outpnumber" id="outpnumber" value="0"></td>
		</tr>
		<tr>		
			<td colspan="2" align="center"><input type="button" value="AutoProcess" onclick="automate()"></td>
		</tr>

	</table>
</div>
<br>
<div id="inp" style="display: block">
<div class="head" align="center">InPut Parameters</div>
<div id="inparams" class="tabledata"></div>
</div>
<br>
<div id="outp" style="display: block">
<div class="head" align="center">OutPut Parameters</div>
<div id="outparams" class="tabledata"></div>
</div>
<center><input type="button" value="Submit" onclick="dbProcess()"></center>
</form>
<form id="reloadqueries" method="post">
<div class="head" align="center">Reload NamedQuries</div>
<div id="reloadstatus" class="tabledata"></div>
<input type="button" value="Reload" onclick="reload()">
</form>
</body>
</html>