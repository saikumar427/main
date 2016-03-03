<%@taglib uri="/struts-tags" prefix="s"%>
<div id="error" class="errorMessage" style="display:none"></div>
<table>
	<tr>
		<td>Event Name: </td>
		<td><s:textfield name="edata.eventName" id="ename" size="60"/></td>
	</tr>
	<tr>
		<td>Host: </td>
		<td><s:textfield name="edata.hostName" id="host" size="60"/></td>
	</tr>
	<tr>
		<td>Location: </td>
		<td><s:textfield name="edata.location" id="location" size="60"/></td>
	</tr>
	<tr>
		<td>City: </td>
		<td><s:textfield name="edata.city" id="city" size="60"/></td>
	</tr>
	<tr>
		<td>Start Time: </td>
		<td><s:hidden name="edata.start_date" id="sdate"/>${edata.start_date}</td>
	</tr>
	<tr>
		<td>End Time: </td>
		<td><s:hidden name="edata.end_date" id="edate"/>${edata.end_date}</td>
	</tr>
	<tr>
		<td>Description: </td>
		<td><s:textarea rows="5" cols="60" id="description" name="edata.description"></s:textarea> </td>
	</tr>
</table>
