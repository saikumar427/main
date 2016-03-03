<%@taglib uri="/struts-tags" prefix="s"%>
<form id="mergelists" name="mergelists" action="maillistmanage!saveMergeListData">
<div id="mergelistserrors"></div>
<div STYLE="height : 250px; width: 400px; font-size: 12px; overflow: auto;">
<table>
<s:hidden name="listId"/>
<tr>
<td colspan="2"></td>
</tr>
<tr><td colspan="2">
Select one or more list(s)
</td></tr>
<s:iterator value="allMailLists" var="mailListData">
<tr>
<td>
<input type='checkbox' value='<s:property value="listId"/>' name='liststobeMerged'/>
</td>
<td>
<s:property value="listName"/>
</td>
</tr>
</s:iterator>
</table>
</div>
</form>