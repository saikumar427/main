
<%@taglib uri="/struts-tags" prefix="s"%>
<script language="javascript">
function onCancel() {
	//window.location.href = "AddCommunityMember?groupId=${groupId}";
	history.back();
}
function addactivemem(){
	document.getElementById('activememberform').submit();
}

function changePassword(usrId,groupId){
	var url='AddCommunityMember!settings?pwdPopup=Yes&usrId='+usrId+'&groupId='+groupId;
	loadURLBasedWindow(url, changePasswordScreen);	
}
var changePasswordScreen = function (responseObj){
	showPopUpDialogWindow(responseObj, "Change Password",submitChangePassword, handleCancel);
}
var submitChangePassword = function(){
	submitFormAndCloseWindow('chPwd','displayFormerrormessages');
}

</script>

<s:if test="%{errorsExists==true}">
	<div class="error"><s:fielderror theme="simple" /></div><br>
</s:if>


<s:form name="activememberform" id="activememberform"
	action="AddCommunityMember!saveProfile" method="post" theme="simple">
	<s:hidden name="groupId"></s:hidden>
	<s:hidden name="usrId"></s:hidden>
	<s:hidden name="memberdata.discountCodeExist"></s:hidden>
<div class="taskbox"><span class="taskheader">Profile
Details</span>
<table width='100%' cellpadding="3" cellspacing="0" border="0">
	
	
	<tr>
		<td width="30%">Login ID *<br/><span class="smallestfont">4-20 alphanumeric characters</span> </td>
		<td><s:textfield name="memberdata.loginId"/><s:if test="%{usrId != null && usrId != ''}">&nbsp;[<a href="#" onClick="changePassword('${usrId}','${groupId} }')" style="a:{text-decoration:underline}">Change Password</a>]</s:if></td>
	</tr>
	<s:if test="%{usrId == null || usrId == ''}">
	<tr>
		<td width="30%">Password *<br/><span class="smallestfont">4-20 alphanumeric characters</span> </td>
		<td><s:password name="memberdata.password"/></td>
	</tr>
	</s:if>
	<tr>
		<td width="30%">First Name *</td>
		<td><s:textfield name="memberdata.firstName" /></td>
	</tr>
	<tr>
		<td width="30%">Last Name *</td>
		<td><s:textfield name="memberdata.lastName" /></td>
	</tr>
	<tr>
		<td width="30%">Email *</td>
		<td><s:textfield name="memberdata.email" /></td>
	</tr>
	<tr>
		<td width="30%">Gender *</td>
		<td><s:iterator value="%{genderType}" var="type">
			<s:radio name="memberdata.gender" list="#{key: value}"
				value="%{memberdata.gender}" />&nbsp;
					</s:iterator></td>
	</tr>
	<tr>
		<td width="30%">Phone *</td>
		<td><s:textfield name="memberdata.phone" /></td>
	</tr>
	<tr>
		<td width="30%">Street</td>
		<td><s:textfield name="memberdata.street" /></td>
	</tr>
	<tr>
		<td width="30%">City</td>
		<td><s:textfield name="memberdata.city" /></td>
	</tr>
	<tr>
		<td width="30%">Zip</td>
		<td><s:textfield name="memberdata.zip" /></td>
	</tr>
	<tr>
		<td width="30%">Membership Type *</td>
		<td><s:select label="Select" name="memberdata.membershiptype"
			id='membershipType' headerKey="" headerValue="-- Select --"
			list="membershipType" /></td>
	</tr>
	<s:if test="%{memberdata.discountCodeExist=='Yes'}">
	<tr>
		<td width="30%">Discount Code</td>
		<td><s:textfield name="memberdata.discountcode" /></td>
	</tr>
	</s:if>
	<tr>
		<td width="30%">Memeber ID</td>
		<td><s:textfield name="memberdata.internalId" /></td>
	</tr>
	<tr>
		<td width="30%">Start Date&nbsp;(yyyy-mm-dd) *</td>
		<td><s:textfield name="memberdata.startDate" /></td>
	</tr>
	<tr>
		<td width="30%">Pay Due Date&nbsp;(yyyy-mm-dd)</td>
		<td><s:textfield name="memberdata.payDueDate" /></td>
	</tr>
	<s:if test="%{usrId != null && usrId != ''}">
	<tr>
		<td width="30%">Status</td>
		<td><s:select label="Select" name="memberdata.status" list="statusTypes" listKey="key" listValue="value"/></td>
	</tr>
	</s:if>
	
	<s:iterator value="%{custAttList}" var="item" status="stat">
		<s:hidden name="%{'custAttList['+#stat.index+'].name'}" />
		<s:hidden name="%{'custAttList['+#stat.index+'].qtype'}" />
		<s:hidden name="%{'custAttList['+#stat.index+'].isRequired'}" />
		<s:hidden name="%{'custAttList['+#stat.index+'].attribid'}" />
		<s:hidden name="%{'custAttList['+#stat.index+'].attribsetid'}" />
		<tr>
			
			
				<s:if test="%{#item.qtype=='text'}">
					<td width="30%" valign="top"><s:property value="#item.name" /><s:if test="%{#item.isRequired=='Required'}">&nbsp*</s:if></td>
					<td><s:textfield name="%{'custAttList['+#stat.index+'].propValue'}"  size="%{#item.size}"/></td>
				</s:if>
				<s:if test="%{#item.qtype=='textarea'}">
					<td width="30%" valign="top"><s:property value="#item.name" /><s:if test="%{#item.isRequired=='Required'}">&nbsp*</s:if></td>
					<td><s:textarea name="%{'custAttList['+#stat.index+'].propValue'}" rows="%{#item.rows}" cols="%{#item.columns}"/></td>
				</s:if>
				<s:if test="%{#item.qtype=='radio'}">
					<td width="30%" valign="top"><s:property value="#item.name" /><s:if test="%{#item.isRequired=='Required'}">&nbsp*</s:if></td>
					
					<td><s:iterator value="%{#item.optionsList}" status="radstat">
						<s:hidden name="%{'custAttList['+#stat.index+'].optionsList['+#radstat.index+'].key'}"/>
						<s:hidden name="%{'custAttList['+#stat.index+'].optionsList['+#radstat.index+'].value'}"/>
						<s:radio name="%{'custAttList['+#stat.index+'].propValue'}" list="#{value: value}" value="custAttList[#stat.index].propValue" /></br>
					</s:iterator></td>
				</s:if>
				<s:if test="%{#item.qtype=='checkbox'}">
					<td width="30%" valign="top"><s:property value="#item.name" /><s:if test="%{#item.isRequired=='Required'}">&nbsp*</s:if></td>
					
					<td><s:iterator value="%{#item.optionsList}" status="chkstat">
						<s:hidden name="%{'custAttList['+#stat.index+'].optionsList['+#chkstat.index+'].key'}"/>
						<s:hidden name="%{'custAttList['+#stat.index+'].optionsList['+#chkstat.index+'].value'}"/>
						<s:checkbox name="%{'custAttList['+#stat.index+'].chkList'}" fieldValue="%{value}" value="%{custAttList[#stat.index].chkList.contains(value)}" />${value}</br>
					</s:iterator></td>
				</s:if>
				
				<s:if test="%{#item.qtype=='dropdown'}">
					<td width="30%"><s:property value="#item.name" /><s:if test="%{#item.isRequired=='Required'}">&nbsp*</s:if></td>
					
					<td><s:iterator value="%{#item.optionsList}" status="dropstat">
						<s:hidden name="%{'custAttList['+#stat.index+'].optionsList['+#dropstat.index+'].key'}" />
						<s:hidden name="%{'custAttList['+#stat.index+'].optionsList['+#dropstat.index+'].value'}" />
					</s:iterator>
					<s:select label="Select" name="%{'custAttList['+#stat.index+'].propValue'}" headerKey="" headerValue="-- Select --" list="optionsList" listKey="value" listValue="value" value="%{custAttList[#stat.index].propValue}" theme="simple"/>
					</td>
				</s:if>
			
		</tr>
	</s:iterator>
	<tr>
	<td align="center" colspan="2">
     <s:if test="%{usrId == null || usrId == ''}"><input type="button" id="submitbtn" value=" Add  " /></s:if>
     <s:if test="%{usrId != null && usrId != ''}"><input type="button" id="updatebtn" value=" Update  " /></s:if> 
     <input type="Button" id="cancelbtn" value=" Cancel " />
    </td>
	</tr>
</table>

</div>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>

<script language='javascript'>
	var submit_btn = new YAHOO.widget.Button("submitbtn", {
		onclick : {fn :addactivemem}
	});
	var submit_btn = new YAHOO.widget.Button("updatebtn", {
		onclick : {fn :addactivemem}
	});
	var cancel_btn = new YAHOO.widget.Button("cancelbtn", {
		onclick : {fn :onCancel}
	});
	
		
</script>
</s:form>