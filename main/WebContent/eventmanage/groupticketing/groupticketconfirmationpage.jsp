<%@taglib uri="/struts-tags" prefix="s"%>

<s:form name="confirmationpage"  action="GroupTicketConfirmationPageSettings!save" id="confirmationpage"  theme="simple" method="post">
<div id="confirmationpageerrormessages"></div>
<s:hidden name="eid" id="eid"></s:hidden>
<div>
<s:fielderror cssErrorClass="errorMessage" theme="simple">
</s:fielderror>
</div>


<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr><td align="left" colspan='2'>				
	<s:textarea name="confirmationPageSettingsData.content" rows="15" cols="60"  theme="simple"></s:textarea>
</td>
 </tr>
</table>

</s:form>
