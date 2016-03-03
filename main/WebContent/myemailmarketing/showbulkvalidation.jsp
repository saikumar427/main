<%@taglib uri="/struts-tags" prefix="s"%>
<form id="bulkdatavalidated" name="bulkdatavalidated" action="maillistmanage!saveBulkData">
<div id="optlist">
</div>
<div id="bulkdatavalidatederrors"></div>
<s:set name="bulkmails" value="bulkmails"></s:set>
<s:hidden id="bulkmails" name="bulkmails" value="%{#bulkmails}"></s:hidden>
<s:set name="userId" value="userId"></s:set>
<s:hidden id="userId" name="userId" value="%{#userId}"></s:hidden>
<s:set name="listId" value="listId"></s:set>
<s:hidden id="listId" name="listId" value="%{#listId}"></s:hidden>
<table width="300" height="100" cellpadding="0" cellspacing="0">
<tr>
<td align="center">
Valid Members: ${bulkvalidlines } </br>
&nbsp &nbsp &nbsp  Duplicate Members: ${bulkduplicates }</br> 
&nbspAdded Members: ${bulkadded }
</td>
</tr>
</table>
</form>
