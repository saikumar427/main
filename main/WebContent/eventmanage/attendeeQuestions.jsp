<%@taglib uri="/struts-tags" prefix="s" %>
<s:form name="attendeeQuestionsform" action="Reports!updateattendeeQuestionsFilter" id="attendeeQuestionsform" theme="simple" method="post">
<s:hidden name="eid"></s:hidden>
<div id="boder">
<div style="overflow:auto;height:300px;width:500px;border:1px solid #336699;padding-left:5px">

<table border="0" cellpadding="3" cellspacing="0" width="100%">
<s:set value="%{customattributes.size()}" name="size"></s:set>
<s:if test="%{#size==0}">
Currently there are no questions added to the event.
</s:if>
<s:else>
<tr><td align="right">
<a href="#" name ="CheckAll" onClick="checkAll(document.attendeeQuestionsform.attQuestionFields)">Select All</a> | <a href="#" name ="UnCheckAll" onClick="uncheckAll(document.attendeeQuestionsform.attQuestionFields)">Clear All</a>
</td>
</tr>
<tr>
<td colspan="2"><s:iterator value="%{customattributes}" var="attributes" status="stat">
<s:set name="mapkey" value="%{key}"/>
<s:hidden name="%{'attributeFilterMap['+#mapkey+']'}" value="%{value}" />
<s:checkbox name="attQuestionFields"  fieldValue="%{key}" value="%{attQuestionFields.contains(key)}"  theme="simple"/>${value}<br/>
</s:iterator>
</td>
</tr>
</s:else>
</table>
</div>
</div>
</s:form>
