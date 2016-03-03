<%@taglib uri="/struts-tags" prefix="s" %>
<s:form name="responseQuestionsform" action="RSVPReports!updateresponseQuestionsFilter" id="responseQuestionsform" theme="simple" method="post">
<s:hidden name="eid"></s:hidden>
<div id="boder">
<div style="overflow:auto;height:300px;width:500px;border:1px solid #336699;padding-left:5px">

<table border="0" cellpadding="3" cellspacing="0" width="100%">
<s:set value="%{respattributes.size()}" name="size"></s:set>
<s:if test="%{#size==0}">
Currently there are no questions added to the event.
</s:if>
<s:else>
<tr><td align="right">
<a href="#" name ="CheckAll" onClick="checkAll(document.responseQuestionsform.responseQuestionFields)">Select All</a> | <a href="#" name ="UnCheckAll" onClick="uncheckAll(document.responseQuestionsform.responseQuestionFields)">Clear All</a>
</td>
</tr>
<tr>
<td colspan="2"><s:iterator value="%{respattributes}" var="attributes">
<s:set name="mapkey" value="%{key}"/>
<s:hidden name="%{'attributeFilterMap['+#mapkey+']'}" value="%{value}" />
<s:checkbox name="responseQuestionFields"  fieldValue="%{key}" value="%{responseQuestionFields.contains(key)}"  theme="simple"/>${value}<br/>
</s:iterator>
</td>
</tr>
</s:else>
</table>
</div>
</div>
</s:form>
