<%@taglib uri="/struts-tags" prefix="s" %>
<script>
</script>
<div id="displayFormerrormessages"></div>
<form action="AddCommunityMember!savePassword" name="chPwd" id="chPwd" method="post">
<s:hidden name="usrId"></s:hidden>
<table>
<tr><td>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr><td>Choose a new password *<br/><span class="smallestfont">4-20 alphanumeric characters</span></td><td><s:password name="password" size="20"></s:password></td></tr>
<tr><td>Re-enter new password *</td><td><s:password name="rePassword" size="20"></s:password></td></tr>
</table>
</td>
</tr>
</table>
</form>