<%@taglib uri="/struts-tags" prefix="s" %>
<s:url id="backURL" action="membershiptypes">
<s:param name="groupId" value="%{groupId}"></s:param>
</s:url>
<script>
function backToList(){
	window.location.href='${backURL}';
}
</script>
<s:form name="addmembershiptypeform" id="addmembershiptypeform" action="CommunityManage!saveMembershipType" method="post" theme="simple">
<div id="membershiperrormessages"></div>
<s:hidden name="groupId"></s:hidden>
<s:hidden name="membershipData.memberShipId"></s:hidden>
<div>
<s:fielderror cssErrorClass="errorMessage" theme="simple">
</s:fielderror>
</div>
<table width="100%" >
<tr>
<td >Membership Name *</td>
<td><s:textfield name="membershipData.memberShipName" theme="simple" size="43"></s:textfield></td>
</tr> 
<tr>
<td >Description</td>
<td>
<s:textarea name="membershipData.description" rows="5" cols="80"></s:textarea>
</td>
</tr> 
<tr>
<td colspan="2">Fee (during signup, initial fee + one term fee will be charged)</td>
</tr>
<tr>
<td >Initial Fee * </td>
<td>
<s:textfield name="membershipData.price" theme="simple" size="5"></s:textfield>
</td>
</tr> 
<tr>
<td >Term Fee * </td>
<td>
<s:textfield name="membershipData.termPrice" theme="simple" size="5"></s:textfield>
</td>
</tr> 
<tr>
<td >Term</td>
<td>
<s:set name="PrefType" value="membershipData.mshipTerm"/>
<select name="membershipData.mshipTerm" >
<s:iterator value="%{termTypes}" var="type">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#PrefType==#cval}">selected='selected'</s:if> >
<s:property value="value"/></option>
</s:iterator>
</select>
</td> 
</tr> 
<!--
<tr>
<td colspan="2" align="center">
<s:submit theme="simple" id="submitBtn"></s:submit>
<input type="button" value="Cancel" id="cancelBtn" onclick="backToList();"  />
</td>
</tr>
-->
</table>
</s:form>
<script>
var btn = new YAHOO.widget.Button("submitBtn", { onclick: { } });
var btn1 = new YAHOO.widget.Button("cancelBtn", { onclick: {fn: cancelThisEvent } });
function cancelThisEvent(){
	backToList();
}
</script>