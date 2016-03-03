<%@taglib uri="/struts-tags" prefix="s" %>
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
<table  border="0" cellpadding="0" cellspacing="0">
<tr><td >Question:</td>
<td><s:textarea id="question" rows="1"  cols="70" name="customattribute.name" theme="simple"></s:textarea>
</td></tr>
<tr><td height="5" colspan=2></td></tr>
<tr><td >
Question Type:
</td>
<td>
<s:iterator value="%{requiredlist}" var="type">
<s:radio name="customattribute.isRequired" list="#{key: value}" 
value="%{customattribute.isRequired}" theme="simple"/>
</s:iterator>
</td></tr>
<tr><td height="5" colspan=2></td></tr>
<tr><td >
Answer Type:
</td>
<td>
<s:iterator value="%{typeList}" var="type">
<s:radio name="type" list="#{key: value}" 
value="%{type}" onclick="ManageQuestion_ChangeUI('%{key}')" theme="simple"/> &nbsp;
</s:iterator>
</td>
</tr>
<s:iterator value="%{typeList}" var="type">
<tr>
<td width="40%" ></td>
<s:if test="%{key=='text'}">
<td align="left" valign="top" ><span id="smalltextarea" style='<s:property value="%{#textstyle}" />'>Size <s:textfield name="customattribute.size" theme="simple" size="3"/></span></td>
</s:if>
<s:elseif test="%{key=='textarea'}">

<td align="left"  valign="top">
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
<tr><td></td><td  id="selectionarea" style='<s:property value="%{#selectionastyle}" />'>
<fieldset>
<legend>Answer Choices</legend>
<table >
<tr>
<td colspan="2">
Display Type <s:select name="customattribute.qtype" list="selectionList" listKey="key" listValue="value" theme="simple"></s:select>
</td>
</tr>

<tr>
<td colspan=2 id="qdatabtn"></td>
</tr>
<tr>
<td colspan=2 >
<table id="updatearea" >
<tr>
<td>
Answer Choice &nbsp;<s:textfield id="coption" theme="simple"></s:textfield> &nbsp;<input type=button id="gobtn" onclick="ManageQuestion_submitOption()" value="Add" /></td>
<td id="cancelButtd" style="display:none"><input type=button id="cancelbtn" onclick="ManageQuestion_cancelOption()" value=Cancel /></td>
</tr>
<tr>
<td>
<s:select list="customattribute.optionsList" id="optionslist" size="5" style="width:300px;"
 listKey="key" listValue="value" headerKey="default" headerValue="--Answer Choices--"theme="simple" onclick="ManageQuestion_showEditOption()">
</s:select>
</td>
<td height="100%">
<table height="100%"><tr>
<td valign="top">
<img src="../images/up.gif" title="Move Up" onclick="ManageQuestion_mvUpOption()">
<br/>
<img src="../images/dn.gif" title="Move Down" onclick="ManageQuestion_mvDnOption()">
</td>
</tr>
<tr><td valign="bottom">
<input type="button" id="delbtn" onclick="ManageQuestion_deleteOption()" value="Remove"/>
</td>
</tr>
</table>
</td>
</tr>
</table>
</td></tr>
</table>
</fieldset>
</td></tr>
</table>
