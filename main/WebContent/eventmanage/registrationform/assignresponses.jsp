<%@taglib uri="/struts-tags" prefix="s" %>
<s:form name="addquestion" action="ManageRegistrationForm!saveQSettings" method="post" id="addqform">
<s:hidden name="eid"></s:hidden>
<s:hidden name="purpose"></s:hidden>
<s:hidden name="attributeid"></s:hidden>
<s:hidden name="powertype"></s:hidden>
<s:hidden name="qlevel"></s:hidden>
<s:hidden name="selqs"></s:hidden>
<div id="optlist"></div>
<input type="hidden" name="ctype" id="ctype" value="add"/>
<input type="hidden" name="cpos" id="cpos" value="0"/>
<table  border="0">
<tr>
<td valign="top" ><jsp:include page="questionsettingsdata.jsp" /></td>
</tr>
</table>
</s:form>