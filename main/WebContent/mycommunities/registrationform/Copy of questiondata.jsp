<%@taglib uri="/struts-tags" prefix="s" %>

<table width="300" border="0">
<tr><td colspan=2>Question</td></tr>
<tr><td colspan=2>
<s:textarea id="question" rows="1"  cols="70" name="customattribute.name" theme="simple"></s:textarea>
</td></tr>
<tr><td colspan=2>
Question Type
<s:iterator value="%{requiredlist}" var="type">
<s:radio name="customattribute.isRequired" list="#{key: value}" 
value="%{customattribute.isRequired}" theme="simple"/>
</s:iterator>
</td></tr>
<tr><td colspan=2>
Answer Type
</td></tr>

<s:set name="qtype" value="type"/>
<s:set name="textstyle" value="%{'display:none;'}"/>
<s:set name="textareastyle" value="%{'display:none;'}"/>
<s:set name="selectionastyle" value="%{'display:none;'}"/>
<s:if test="%{#qtype=='text'}">
<s:set name="textstyle" value="%{'display:block'}"/>
</s:if>
<s:elseif test="%{#qtype=='textarea'}">
<s:set name="textareastyle" value="%{'display:block'}"/>
</s:elseif>
<s:else>
<s:set name="selectionastyle" value="%{'display:block'}"/>
</s:else>

<s:iterator value="%{typeList}" var="type">
<tr>
<td>
<s:radio name="type" list="#{key: value}" 
value="%{type}" onclick="ManageQuestion_ChangeUI('%{key}')" theme="simple"/>
</td>
<s:if test="%{key=='text'}">
<td align="right"><span id="smalltextarea" style='<s:property value="%{#textstyle}" />'>Size <s:textfield name="customattribute.size" theme="simple" size="3"/></span></td>
</s:if>
<s:elseif test="%{key=='textarea'}">

<td  align="right"  >
<span id="multilinearea" style='<s:property value="%{#textareastyle}" />' >Lines <s:textfield name="customattribute.rows" theme="simple" size="3"/>&nbsp;&nbsp;Chars/Line <s:textfield name="customattribute.columns" theme="simple" size="3"/></span>
</td>
</s:elseif>
<s:else>
<td>
</td>
<td></td>
</s:else>
</tr>
</s:iterator>
<tr><td colspan=2 id="selectionarea" style='<s:property value="%{#selectionastyle}" />'>
<fieldset>
<legend>Answer Choices</legend>
<table >
<tr>
<td colspan="2">
Display Type: <s:select name="customattribute.qtype" list="selectionList" listKey="key" listValue="value" theme="simple"></s:select>
</td>
</tr>
<tr>
<td>
<s:select list="customattribute.optionsList" id="optionslist" size="5" style="width:300px;"
 listKey="key" listValue="value" headerKey="default" headerValue="--Add Answer Choices--"theme="simple" onclick="ManageQuestion_showEditOption()">
</s:select>
</td>
<td height="100%">
<table height="100%"><tr>
<td valign="top">
<img src="../images/up.gif" title="Move Up" onclick="ManageQuestion_mvUpOption()"><img src="../images/spacer.gif" width="10" height="16" /><img src="../images/dn.gif" title="Move Down" onclick="ManageQuestion_mvDnOption()">
</td>
</tr>
<tr><td valign="bottom">
<input type="button" id="delbtn" onclick="ManageQuestion_deleteOption()" value="Remove"/>
</td>
</tr>
</table>
</td>
</tr>
<tr>
<td colspan=2 id="qdatabtn">
</td>
</tr>
<tr>
<td colspan=2 >
<table id="updatearea" >
<tr>
<td >
Answer Choice </td>
<td ><s:textfield id="coption" theme="simple"></s:textfield></td>
<td><input type=button id="gobtn" onclick="ManageQuestion_submitOption()" value="Add" /></td>
<td id="cancelButtd" style="display:none"><input type=button id="cancelbtn" onclick="ManageQuestion_cancelOption()" value=Cancel /></td>
</tr>
</table>
</td></tr>
</table>
</fieldset>
</td></tr>
</table>
