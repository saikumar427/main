<%@taglib uri="/struts-tags" prefix="s"%>
<div style="overflow:auto;height:470px;width:600px;border:0px solid #336699;padding-left:5px">
<form action="VolumeTicketDetails!saveConfirmationPage" name="conPageForm" id="conPageForm" method="post">
<s:hidden name="eid" ></s:hidden>
<s:hidden name="purpose" ></s:hidden>
<table  border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
<tr><td height="2"></td></tr>
	<tr>
		<td></td>
	  <td align="left"></td>
	</tr>
	<tr><td height="5"></td></tr>
<tr><td height="5"></td></tr>
	<tr>
	<td></td>
	 <td align="left">
	   <s:textarea  name="confirmationPageSettingsData.content" id="content" rows="30" cols="90" theme="simple">
	   </s:textarea>
	 </td>
	</tr>
</table>
</form>
</div>