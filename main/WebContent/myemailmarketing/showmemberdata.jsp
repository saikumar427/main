<%@taglib uri="/struts-tags" prefix="s"%>
<form id="memberdata" name="memberdata" action="maillistmanage!showValidationData">
<div id="optlist">
</div>
<!-- action="maillistmanage!saveFileData"> -->
<div id="memberdataerors"></div>
<table width="300" cellpadding="0" cellspacing="0" >
<s:hidden name="listId"/>
<tr><td>
<s:set name="count" value="%{fileheadings.size}"></s:set>
<s:hidden id="mattribcount" value="%{#count}"></s:hidden>
</td></tr>
<tr>
<td ><b>Map Attributes</b></td>
</tr>
<tr>
<td height="10"></td>
</tr>
<s:iterator value="fileheadings" var="data" status="rowStatus" id="file">
<tr>
<td align="justify"><b>
<s:property value="fileheadings[#rowStatus.index]"/>
</b>
</td>
<td>
<select name="mattrib<s:property value='#rowStatus.index' />" id="mattrib<s:property value='#rowStatus.index' />">
<s:iterator value="%{memberAttribs}" var="type">
<s:set name="cval" value="key"/>
<option value='<s:property value="key"/>'>
<s:property value="value"/></option>
</s:iterator>
</select></td>
</tr>
<br/>
</s:iterator>
</table>
</form>
