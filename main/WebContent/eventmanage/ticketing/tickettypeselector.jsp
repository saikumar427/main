<%@taglib uri="/struts-tags" prefix="s" %>
<div>
<form name="newTicket" id="newTicket">
<!-- <input type="hidden" name="eid" id="eid" value="<%=request.getParameter("eid")%>"></input>  -->
<s:hidden name="eid"></s:hidden>
	<table>
	<tr>
	<td>Ticket Type</td>
	<td><input type="radio" name="ticketType" id="aticketType" value="ATTENDEE" checked="checked">Attendee Ticket<br/>
	<input type="radio" name="ticketType" id="nticketType" value="NONATTENDEE">Non-Ateendee Ticket<br/>
	<input type="radio" name="ticketType" id="donation" value="DONATION">Donation</td>
	</tr>
	<tr>
	<td>Add As</td>
	<td><input type="radio" name="groupId" id="igroupId" value="0" checked="checked">Individual Ticket<br/>
	<input type="radio" name="groupId" id="ggroupId" >Add to Group
	</td>
	</tr>
	<tr>
	<td></td>
	<td>
	<select name="groupName" id="groupName" >
	<s:iterator value="%{allgroups}" var="type">
	<option value="<s:property value="key"/>" >${value}</option>
	</s:iterator>
	</select></td>
	</tr>
	<tr>
	<td><input type="button" value="Cancel" onClick="closeTicketingForm();" align="right"> </td>
	<td><input type="button" value="Continue" onclick="submitFunc();"> </td></tr>
	</table>
	
</form>
</div>