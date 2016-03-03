<%@taglib uri="/struts-tags" prefix="s" %>
<form action="maillistmanage!saveList" name="mailListForm" id="mailListForm" method="post">


<div id="maillisterrormessages">

</div>

<s:hidden name="mailList.listId"/>
<table>
<tr>
<td width="200">List Name *</td>
<td><s:textfield name="mailList.listName" theme="simple" size="40"></s:textfield></td>
</tr>
<tr>
<td valign="top" width="200">Description</td>
<td><s:textarea rows="4" cols="40" name="mailList.descritption" theme="simple"></s:textarea>
</td>
</tr>
<tr>
<td valign="top" width="200">Unsubscribe Message *<br/><span class="smallestfont">Displayed to the user when he/she unsubscribes from the Mailing List</span></td>
<td><s:textarea rows="4" cols="40" name="mailList.unsubmsg" theme="simple"></s:textarea></td>
</tr>

</table>
</form>

