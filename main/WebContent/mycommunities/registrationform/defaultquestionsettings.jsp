<%@taglib uri="/struts-tags" prefix="s" %>
<form name="dqsettings" action="ManageRegistrationForm!saveBaseQSettings" method="post">
<s:set value="%{alltickets.size()}" name="size"></s:set>
<s:hidden name="eid"></s:hidden>
<table style="border:solid;border-width:0px;" width="300">
<tr><td colspan="2">Name, Phone & Email will be collected from buyer by default. You may select the tickets for which Name, Email to be collected</td></tr>
<tr><td colspan="2" height="5"></td></tr>
<s:if test="%{#size!=0}">
<tr><td valign="top">Select Tickets</td>
<td align="right">
[<a href="#" onclick="SetAllCheckBoxes('dqsettings','seltickets','true')">Select All</a>&nbsp;|&nbsp;
<a href="#" onclick="SetAllCheckBoxes('dqsettings','seltickets','')">None</a>]
</td></tr>
<tr><td colspan="2" id="selectedtickets">
<div style="overflow:auto;height:200px;border:1px solid #336699;padding-left:5px">
<s:iterator value="%{alltickets}" var="ticket">
<s:checkbox name="seltickets" fieldValue="%{key}" value="%{seltickets.contains(key)}" theme="simple"/>${value}<br/>
</s:iterator>
</div>
</td></tr>
</s:if>
<s:else>
<tr><td valign="top"> Currently, there are no tickets for this event</td></tr>
</s:else>
</table>
</form>
