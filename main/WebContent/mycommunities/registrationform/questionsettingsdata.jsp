<%@taglib uri="/struts-tags" prefix="s" %>
<s:set value="%{alltickets.size()}" name="size"></s:set>
<div style="border-bottom:#999999 solid 1px;border-left:#999999 solid 1px;border-right:#999999 solid 1px;">
<table style="border:solid #336699;border-width:0px;" width="300" border="0" cellpadding="0" cellspacing="0">
<tr><td colspan="2" class="boxheader">Display Question Under </td></tr>
<s:if test="%{powertype!='RSVP'}">
<tr><td colspan="2" class="tblright">
<s:checkbox name="collectbuyer" fieldValue="true" theme="simple"/>
Buyer Profile
</td></tr>
<s:if test="%{#size!=0}">
<tr><td valign="top" class="tblright">Tickets Profile</td>
<td align="right" class="tblright">
[<a href="#" onclick="SetAllCheckBoxes('addquestion','seltickets','true')">Select All</a>&nbsp;|&nbsp;
<a href="#" onclick="SetAllCheckBoxes('addquestion','seltickets','')">None</a>]
</td></tr>
</s:if>
<tr><td colspan="2" id="selectedtickets">
<div style="overflow:auto;height:100px;border:0px solid #336699;padding-left:5px">
<s:iterator value="%{alltickets}" var="ticket">
<s:checkbox name="seltickets" fieldValue="%{key}" value="%{seltickets.contains(key)}" theme="simple"/>${value}<br/>
</s:iterator>
</div>
</td></tr>
</s:if>
<s:else>
<tr><td class="tableline">
Display this question for the following response type[s].
</td></tr>
<tr height="10"><td></td></tr>
<s:iterator value="%{rsvpStatusOptionsList}" var="rsvptype">
<tr><td>
<s:checkbox name="selRsvpStatusOptions" fieldValue="%{key}" value="%{selRsvpStatusOptions.contains(key)}" theme="simple"/>${value}
</td></tr>
</s:iterator>
</s:else>
</table>
</div>