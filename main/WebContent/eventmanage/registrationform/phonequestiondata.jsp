<%@taglib uri="/struts-tags" prefix="s" %>
<s:set name="dtype" value="displayPhoneYN"/>
<table  border="0" cellpadding="0" cellspacing="0">
<tr><td >Question </td><td> Phone</td></tr>
<tr><td height="5" colspan=2></td></tr>
<tr><td >Show/Hide</td>
<td>
<input type="radio" name="displayPhoneYN" value="Y" <s:if test='%{#dtype=="Y"}'>checked=true</s:if>>Show</input>
&nbsp;
<input type="radio" name="displayPhoneYN" value="N" <s:if test='%{#dtype=="N"}'>checked=true</s:if>>Hide</input>
</td></tr>
<tr><td height="5" colspan=2></td></tr>
<tr>
<td>Question Type</td>
<td>
<s:iterator value="%{requiredlist}" var="type">
<s:radio name="customattribute.isRequired" list="#{key: value}" 
value="%{customattribute.isRequired}" theme="simple"/>
</s:iterator>
</td>
</tr>
<tr><td height="5" colspan=2></td></tr>
</table>
