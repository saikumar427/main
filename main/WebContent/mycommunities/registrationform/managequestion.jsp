<%@taglib uri="/struts-tags" prefix="s" %>
<s:form name="addquestion" action="ManageRegistrationForm!saveQuestion" method="post" id="addqform">
<s:hidden name="groupId"></s:hidden>
<s:hidden name="purpose"></s:hidden>
<s:hidden name="attributeid"></s:hidden>
<div id="optlist"></div>
<input type="hidden" name="ctype" id="ctype" value="add"/>
<input type="hidden" name="cpos" id="cpos" value="0"/>
<table width="650" border="0">
<tr>
<td valign="top" ><jsp:include page="questiondata.jsp" /></td>

</tr>
</table>
</s:form>