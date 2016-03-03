
<%@taglib uri="/struts-tags" prefix="s" %>
<s:form name="groupticketshowhideform" action="GroupTicketsShowHide!update" id="groupticketshowhideform" theme="simple" method="post">
<s:hidden name="eid"></s:hidden>
<div id="border">
<div style="overflow:auto;height:300px;width:500px;border:1px solid #336699;padding-left:5px">

<table border="0" cellpadding="3" cellspacing="0" width="100%">
<s:set value="%{allGrouptickets.size()}" name="size"></s:set>
<s:if test="%{#size==0}">
Currently there are no GroupTickets added to the event.
</s:if>
<s:else>
<tr>
<td >Select GroupTickets to show (Unselect to hide)  </td>
</tr>
<tr><td align="right">
<a href="#" name ="CheckAll" onClick="checkAll(document.groupticketshowhideform.selGrouptickets)">Select All</a> | <a href="#" name ="UnCheckAll" onClick="uncheckAll(document.groupticketshowhideform.selGrouptickets)">Clear All</a>
</td>
</tr>
<tr>
<td>
<s:iterator value="%{allGrouptickets}" var="ticket" >
<s:checkbox name="selGrouptickets" id="selGrouptickets" fieldValue="%{key}" value="%{selGrouptickets.contains(key)}" />${value}<br/>
</s:iterator>

</td>
</tr>
</s:else>
</table>
</div>
</div>
</s:form>
 