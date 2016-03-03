<%@taglib uri="/struts-tags" prefix="s"%>

<div class="box" align="left">
<div class="boxheader">Integration URLs</div>
<div class="boxcontent">
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td class="taskcontent" colspan="2">Use the following Eventbee URL links to integrate your website with Eventbee backend</td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr>
<td>Community URL</td>
<td><s:textfield name="clubInfo.communityURL" size="80" onclick='this.select()'></s:textfield>
</td>
</tr>
<tr><td colspan="2"></td></tr>
<tr>
<td>Login URL</td>
<td><s:textfield name="clubInfo.loginURL" size="80" onclick='this.select()'></s:textfield>
</td>
</tr>
<tr><td colspan="2"></td></tr>
<tr>
<td>Signup URL</td>
<td><s:textfield name="clubInfo.signupURL" size="80" onclick='this.select()'></s:textfield>
</td>
</tr>
<tr><td colspan="2"></td></tr>
<tr>
<td>Renew URL</td>
<td><s:textfield name="clubInfo.renewURL" size="80" onclick='this.select()'></s:textfield>
</td>
</tr>
</table>
</div>
</div>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>