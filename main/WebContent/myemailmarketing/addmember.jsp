<%@taglib uri="/struts-tags" prefix="s"%>
<div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>		
		<td class="headline">&nbsp;<strong>Add Member</strong></td>		
	</tr>
	</table>
</div>
<div id="boder">
		<table  border="0" cellpadding="3" cellspacing="0" width="100%" class="taskcontent">
 		<tr>
		<td ><a href="#" onClick="addManual(${listId},${userId});">Add Manually</a></td>
		<td ><a href="#" onClick="mergeLists(${listId},${userId});">Merge List</a></td>
		<td ><a href="#">File Upload</a></td>
		</tr>
      </table> 
</div>