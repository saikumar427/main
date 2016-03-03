<%@taglib uri="/struts-tags" prefix="s"%>
<form id="memberdatavalidated" name="memberdatavalidated" action="maillistmanage!saveFileData">
<div id="optlist">
</div>
<div id="memberdatavalidatederrors"></div>
<s:hidden name="listId"/>
<s:set name="ext" value="ext"></s:set>
<s:hidden id="ext" name="ext" value="%{#ext}"></s:hidden>
<table width="300" height="100" cellpadding="0" cellspacing="0">
<s:set name="count" value="fileData.size"></s:set>
<s:hidden id="mattribcount" value="%{#count}"></s:hidden>
<tr>
<td align="center">
Valid Members: ${validcount } </br>
&nbsp &nbsp &nbsp  Duplicate Members: ${duplicateemailcount }</br> 
&nbspAdded Members: ${addedmembers }
</td>
</tr>
<s:iterator value="optionsList" var="data" status="rowStatus">
<tr>
<td>
<input type="hidden" id="mattrib" name='optionsList[<s:property value="#rowStatus.index" />]' value='<s:property value="data" />' />
</td>
</tr>
</s:iterator>
</table> 
</form>

