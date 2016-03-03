<%@taglib uri="/struts-tags" prefix="s"%>
<s:set name="nexturl" value="%{'../eventmanage/Snapshot'}" />
<s:if test="%{addEventData.powerWithType=='ticketing'}">
<s:set name="nexturl" value="%{'../eventmanage/PaymentSettings'}" />
</s:if>
<script language="Javascript">
var URL = '<s:text name="nexturl"/>?eid=${eid}';
var speed = 4000;
function reload()
{
location = URL
}
setTimeout("reload()", speed);
</script>

<table align='center'>
<tr height='30'><td></td></tr>
<tr><td>
Congrats, your event is listed now. Taking you to Event Manage page in a moment....

</td></tr>
<tr><td class='smallfont'>If this page is not redirected within 5 seconds, please <a href='<s:text name="nexturl"/>?eid=${eid}'>click here</a></td></tr>
<tr height='10'><td></td></tr>

</table>