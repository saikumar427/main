
<%@taglib uri="/struts-tags" prefix="s"%>

<s:form id="onpurchaseemailform" name="onpurchaseemailform" action="GroupTicketConfirmationEmail!save" method="post" theme="simple">
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="emailpurpose" id="emailpurpose" value="onpurchase"></s:hidden>
<s:set name="sendtoattendee" value="regEmailSettingsData.sendToAttendee"></s:set>
<s:set name="sendtomanager" value="regEmailSettingsData.sendToManager"></s:set>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
		<tr ><td colspan='2'></td></tr>
<tr><td>Send To </td>
<td>
<input type="checkbox" name="regEmailSettingsData.sendToAttendee" <s:if test="%{#sendtoattendee == 'yes'}">checked='checked'</s:if> />
<s:if test="%{#powerType == 'RSVP'}">Attendee</s:if>
<s:else>Buyer</s:else>
<input type="checkbox" name="regEmailSettingsData.sendToManager" <s:if test="%{#sendtomanager == 'yes'}">checked='checked'</s:if> />
Event Manager</td>
</tr>
<tr>
<td>Subject </td><td><s:textfield name="regEmailSettingsData.subject" theme="simple" size="50"/></td>
</tr>
<tr>
<td>Bcc To </td><td><s:textfield name="regEmailSettingsData.cc" theme="simple" size="50"/></td>
</tr>
<tr><td colspan='2'>Email Content</td></tr>
<tr><td align="left" colspan='2'>				
	<s:textarea name="regEmailSettingsData.tempalteContent" rows="15" cols="60"  theme="simple"></s:textarea>
</td>
 </tr>
</table>
<p/>
</s:form>




