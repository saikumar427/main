<%@taglib uri="/struts-tags" prefix="s" %>
<script>


</script>
<div id="displayFormerrormessages">
</div>
<form action="DisplayTickets!saveformat" name="disForm" id="disForm" method="post">
<s:hidden name="eid"></s:hidden>
<s:hidden name="formatId" ></s:hidden>
<s:hidden name="dispFrmt" value="Display"></s:hidden>
<table>
<tr><td>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr><td></td><td>$soldOutQty&nbsp;&nbsp;$capacity&nbsp;&nbsp;$remainingQty&nbsp;&nbsp;$onHoldQty
</td>
</tr>
<tr><td>Display Format:</td><td><s:textarea name="displayFormat" rows="3" cols="80"></s:textarea></td></tr>
<tr>
<td>Tickets:</td>
<td>
<div style="overflow:auto;height:200px;width:400px;border:1px solid #336699;padding-left:5px">
<a href="#" name ="SelectAll" onClick="selectAll();">Select All</a> | <a href="#" name ="ClearAll" onClick="clearAll();">Clear All</a>
<br/>
<s:iterator value="%{alltickets}" var="ticket">
<input type="checkbox" name="formatTickets" value="${key}" <s:if test="%{formatTickets.contains(key)}">checked='checked'</s:if>/>${value}<br/>
</s:iterator>
</div>
</td>
</tr>
</table>
</td>
</tr>
</table>
</form>