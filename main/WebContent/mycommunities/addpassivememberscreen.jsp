
<%@taglib uri="/struts-tags" prefix="s"%>
<script language='javascript' >

function onProfileSubmit(){	

document.getElementById('passivememform').submit();
}
function onCancel(){
	window.location.href="AddCommunityMember?groupId=${groupId}";
}


</script>

<s:if test="%{errorsExists==true}">
	<div class="error"><s:fielderror theme="simple" /></div><br>
</s:if>


<s:form name="passivememform" id="passivememform" 
action="PassiveMemberCreatManual!saveMemberProfile" method="post" theme="simple">
<s:hidden name="groupId"></s:hidden>
<div class="taskbox">
<span class="taskheader">Profile Details</span></br>
<table width='100%' cellpadding="3" cellspacing="0" border="0">

<tr><td width="30%">First Name *</td><td><s:textfield name="memberdata.firstName"/></td>
</tr>
<tr><td width="30%">Last Name *</td><td><s:textfield name="memberdata.lastName"/></td>
</tr>
<tr><td width="30%">Email *</td><td><s:textfield name="memberdata.email"/></td>
</tr>
<tr><td width="30%">Gender *</td>
<td><s:iterator value="%{genderType}" var="type">
					<s:radio name="memberdata.gender" list="#{key: value}" value="%{memberdata.gender}" />&nbsp;
					</s:iterator>
				</td>
</tr>
<tr><td width="30%">Phone *</td><td><s:textfield name="memberdata.phone"/></td>
</tr>
<tr><td width="30%">Street</td><td><s:textfield name="memberdata.street"/></td>
</tr>
<tr><td width="30%">City</td><td><s:textfield name="memberdata.city"/></td>
</tr>
<tr><td width="30%">Zip</td><td><s:textfield name="memberdata.zip"/></td>
</tr>
<tr><td width="30%">
Membership Type</td><td><s:select label="Select" name="memberdata.membershiptype" id='membershipType'
    				headerKey="" headerValue="-- Select --" list="membershipType"/></td>
</tr>
<tr><td width="30%">Memeber Id</td><td><s:textfield name="memberdata.internalId"/></td>
</tr>
<tr><td width="30%">Start Date&nbsp;(yyyy-mm-dd) *</td><td><s:textfield name="memberdata.startDate"/></td>
</tr>
<tr><td width="30%">Pay Due Date&nbsp;(yyyy-mm-dd) </td><td><s:textfield name="memberdata.payDueDate"/></td>
</tr>
<tr>
<td align="center" colspan="6">
<input type="button"  id="submitbtn"  value=" Add  " />
<input type="Button"  id="cancelbtn"  value=" Cancel "/></td>
</tr>
</table>
</div>
<script language='javascript'>
var submit_btn=new YAHOO.widget.Button("submitbtn",{onclick:{ fn: onProfileSubmit}});
var cancel_btn=new YAHOO.widget.Button("cancelbtn",{ onclick: { fn:onCancel } });
</script>
</s:form>
