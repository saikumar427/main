<%-- <%@taglib uri="/struts-tags" prefix="s" %>
<div id="groupaddtformerrormessages" class="errorMessage" style="display:none"></div>

<form action="GroupDetails!save" name="GroupAddForm" id="GroupAddForm" method="post">
<input type="hidden" name="eid" value="<%=request.getParameter("eid")%>"></input>
<s:hidden name="groupData.groupId"></s:hidden>
<table>
<tr>
<td>Group Name *</td>
<td><s:textfield name="groupData.groupName" theme="simple" size="50" id="groupName"></s:textfield></td>
</tr>
<tr>
<td>Group Description</td>
<td><s:textarea rows="5" cols="47" name="groupData.groupDescription" theme="simple"></s:textarea></td>
</tr>
</table>
</form> --%>

<%@taglib uri="/struts-tags" prefix="s" %>
 [{"eid":"<s:property value="eid"/>"
,"groupData.groupName":"<s:property value="groupData.groupName"/>"
,"groupData.groupDescription":"<s:property value="groupData.groupDescription"/>"
}]

