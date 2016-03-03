<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.eventbee.general.*" %>

<s:form name="photos1" method="post" action="imageselection.jsp" onsubmit="return BuildURL()" >
<table width='100%'  cellspacing="0" cellpadding="0" align='center'>
<tr><td><table align='center' width='100%'>
<tr><td class="inputlabel"  colspan='2' >
Select image size and click on the Add button to add image: </td></tr>

<tr><td height='5'></td></tr>

<tr>
<td class="inputlabel" width='20%'>Size</td>
<td>
<input type='radio' name="photos.radioBox" >Small
<input type='radio' name="photos.radioBox" >Medium
<input type='radio' name="photos.radioBox" checked='checked'>Large
</td></tr>
</table></td></tr>
<tr><td height='10'></td></tr>

<tr><td><table align='center' width='100%'>

<s:iterator value="%{photos.photoMap}" var="type">
<tr>

</s:iterator>
</table></td></tr>
</table>
</s:form>