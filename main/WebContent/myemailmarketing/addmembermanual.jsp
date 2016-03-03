<%@taglib uri="/struts-tags" prefix="s"%>
<form id="membermanualadd" name="membermanualadd" action="maillistmanage!saveMemberManual">
<s:hidden name="memberData.memberId"/>
<div id="membermanualadderrors"></div>
<div id="manual">
<table width="100%" align="center" > 
<s:hidden name="listId"/>
<s:hidden name="memberData.mem_userId"/>
<tr>
<td >Email * </td>
<td><s:textfield name="memberData.email" theme="simple" size="30" id="email"></s:textfield></td>
<td >Phone</td><td><s:textfield name="memberData.phone" theme="simple"></s:textfield></td>
</tr>
<tr>
<td>First Name </td> <td><s:textfield name="memberData.firstName" theme="simple" size="30"></s:textfield></td> 
<td>Last Name </td> <td><s:textfield name="memberData.lastName" theme="simple"></s:textfield></td>
</tr>
<tr>
<td>Age  </td><td><s:textfield name="memberData.age" theme="simple" size="3"></s:textfield></td>
<td>Gender</td><td>
<s:set name="genderType" value="memberData.gender"/>
<select name="memberData.gender" >
<s:iterator value="%{genderTypelist}" var="type">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#genderType==#cval}">selected='selected'</s:if> >
<s:property value="value"/></option>
</s:iterator>
</select></td>
</tr>
<tr>
<td>Title</td><td><s:textfield name="memberData.title" theme="simple" size="30" ></s:textfield></td>
<td>Place</td><td><s:textfield name="memberData.place" theme="simple"></s:textfield></td>
</tr>
<tr>
<td>Company</td><td><s:textfield name="memberData.company" theme="simple" size="30"></s:textfield></td>
<td>Email Preference </td> 
<td>
<s:set name="emailPrefType" value="memberData.emailPref"/>
<select name="memberData.emailPref" >
<s:iterator value="%{emailPreflist}" var="type">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#emailPrefType==#cval}">selected='selected'</s:if> >
<s:property value="value"/></option>
</s:iterator>
</select>
</td> 
</tr>
<s:set name="id" value="memberData.memberId"/>
<s:if  test="#id!=''">
<tr>
<td>status</td>
<td>
<s:set name="statusType" value="memberData.status"/>
<select name="memberData.status" >
<s:iterator value="%{statusList}" var="type">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#statusType==#cval}">selected='selected'</s:if> >
<s:property value="value"/></option>
</s:iterator>
</select>
</td>
<td></td>
</tr>
</s:if>
</table>

</div>
</form>