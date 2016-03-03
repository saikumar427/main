

<%@taglib uri="/struts-tags" prefix="s"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<s:if test="%{addmember==true}">
<div id="statusMessageBox" class="statusMessageBox">
<div style="float: left;valign: middle;"><img src="../images/check.png"/>
Member added successfully. Add one more member.
</div>
<div><a class="closebtn" href="javascript:hidestatusmessage()"></a></div>
</div>
<div id="break" style="display: none;"><br/></div><br>
</s:if>
 <s:set value="%{membertype}" name="memtype"></s:set>
<div class="taskbox"><span class="taskheader">Add Member Settings</span>
     <form name="addunitmember" id="addunitmember" action="AddCommunityMember!settings" method="post">
     <s:hidden name="memberdata.usernamereset" value="No"></s:hidden>
     <s:hidden name="memtype"></s:hidden>
     <s:hidden name="groupId"></s:hidden>
     <div id="paymentStatusMsg" style="display:none;"></div>
	<table width='100%' cellpadding="2" cellspacing="2" border="0">

		<s:set value="%{mshipidlist.size()}" name="mshipidsize"></s:set>
		<s:if test="%{#mshipidsize!=0}">
        
        <!--<s:if test="%{#memtype!='EXCLUSIVE'}">
              <tr><td>Add as Active Members (membership is created automatically)</td></tr>
           </s:if>-->

			<tr>
				<td class="inputlabel">Force user to update Password during
				the first login?</td>
				<td class="inputvalue"><input type="radio"
					name="memberdata.pwdresetradio" value="Yes" />Yes <input type="radio"
					name="memberdata.pwdresetradio" value="No" checked="checked" />No</td>
			</tr>
			<tr>
				<td class="inputlabel">Email login information to user after
				adding?</td>
				<td class="inputvalue"><input type="radio"
					name="memberdata.emailradio" value="Yes" />Yes <input type="radio"
					name="memberdata.emailradio" value="No" checked="checked" />No</td>
			</tr>
			<tr>
				<td align="center" colspan="2"><input type="submit"
					name="addunitmemsubmitbtn" id="addunitmemsubmitbtn" value="Continue" /></td>
			</tr>

             
               <!--<s:if test="%{#memtype!='EXCLUSIVE'}">
               
               <tr><td class="subheader">Add as Passive Members (membership is not created) </td></tr>
	          <tr><td align="center" colspan="2" ><input type="submit" name="passivemembtn" id="passivemembtn"  value="Continue"/></td></tr>
	
             </s:if>-->
</s:if>

		
		<s:else>
			<tr>
				<td>You have no Membershiptype.</td>
			</tr>
		</s:else> 
	
</table>
	</form>
</div>
</body>
</html>
<script>
var  unitmembersubmit=new YAHOO.widget.Button("addunitmemsubmitbtn",{onclick:{fn:addActiveMember}});
var  passivemem_btn=new YAHOO.widget.Button("passivemembtn",{onclick:{fn:addPassiveMember }});

function addPassiveMember(){
	window.location.href='AddCommunityMember!addPassiveMember?groupId=${groupId}';
}
function addActiveMember(){
	document.getElementById('addunitmember').submit;
}


</script>
