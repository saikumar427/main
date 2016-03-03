<div style="overflow:auto;height:500px;width:600px;border:0px solid #336699;padding-left:5px">
<%@taglib uri="/struts-tags" prefix="s" %>
<form action="VolumeTicketDetails!saveConfirmationEmail" name="conEmailForm" id="conEmailForm" method="post">
<s:hidden name="eid" ></s:hidden>
<s:hidden name="purpose" ></s:hidden>
<s:hidden name="preview" id="previewdata"></s:hidden>
<s:set name="sendtoattendee" value="regEmailSettingsData.sendToAttendee"></s:set>
<s:set name="sendtomanager" value="regEmailSettingsData.sendToManager"></s:set>
<table  border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
<tr><td height="2"></td></tr>
<tr><td></td><td align="left"></td></tr>
<tr><td height="2"></td></tr>
<!--tr><td>Send To 
<input type="checkbox" name="regEmailSettingsData.sendToAttendee" <s:if test="%{#sendtoattendee == 'yes'}">checked='checked'</s:if>/>
Buyer
<input type="checkbox" name="regEmailSettingsData.sendToManager" <s:if test="%{#sendtomanager == 'yes'}">checked='checked'</s:if> />
Event Manager</td>
</tr-->
<tr><td height="2"></td></tr>
<tr>
<td>Subject <s:textfield name="regEmailSettingsData.subject" size="60"/></td>
</tr>
<!--tr>
<td>Bcc To&nbsp;&nbsp;<s:textfield name="regEmailSettingsData.cc" size="60"/></td>
</tr-->
<tr><td height="5"></td></tr>
<tr>
<td>
<s:if test="%{purpose=='VB_TRIGGERCAPTURE_CONFIRMATION'}">Email content for Trigger success</s:if>
<s:elseif test="%{purpose=='VB_NOTRIGGERCAPTURE_CONFIRMATION'}">Email content for Trigger failed but charged</s:elseif>
<s:elseif test="%{purpose=='VB_VOID_CONFIRMATION'}">Email content for Trigger failed and voided</s:elseif>
<s:else>Email content for Trigger not yet reached</s:else>
</td>
</tr>
<tr><td height="5"></td></tr>
	<tr>
	 <td>
	   <s:textarea name="regEmailSettingsData.tempalteContent" rows="30" cols="90"  theme="simple"></s:textarea>
	 </td>
	</tr>
</table>
</form>
</div>