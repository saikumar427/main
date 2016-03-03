<%@taglib uri="/struts-tags" prefix="s" %>
<div id="boder">
<s:form method="post" action="RSVPReports!update" name="rsvpeditform" id="rsvpeditform" theme="simple">
<s:hidden name="eid"></s:hidden>
<s:hidden name="rsvpData.attendeeid"></s:hidden>
<table  border="0" cellpadding="3" cellspacing="0" width="100%"   class="taskcontent">
<tr><td>First Name</td><td><s:textfield name="rsvpData.firstName"></s:textfield></td></tr>
<tr><td>Last Name</td><td><s:textfield name="rsvpData.lastName"></s:textfield></td></tr>
<tr><td>Email</td><td><s:textfield name="rsvpData.email"></s:textfield></td></tr>
<tr><td>Phone</td><td><s:textfield name="rsvpData.phone"></s:textfield></td></tr>
<tr><td >Attending</td>
    <td><s:iterator value="%{responseType}" var="type">
		<s:radio name="rsvpData.attendingevent" list="#{key: value}" value="%{rsvpData.attendingevent}"   />&nbsp;
		</s:iterator>
	</td>
</tr>
</table>
</s:form>
</div>