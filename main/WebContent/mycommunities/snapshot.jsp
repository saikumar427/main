<%@taglib uri="/struts-tags" prefix="s"%>
<script>
var getSuccess = function(responseObj){
	showPopUpDialogWindow(responseObj, "Edit Community", save, handleCancel);	
}
var getFailure = function(){
	alert("error");
}
var save=function(){	
	submitFormAndReload('communitycreate', 'communityerrormessages');	
}
</script>
<s:hidden name="groupId"/>
<div class="box" align="left">
  <div class="boxheader">Summary</div>
  <div class="boxcontent">
  <table border="0" cellpadding="3" cellspacing="0" width="100%"	>
	<tr><td width="50%">
		<table width="100%" cellspacing="0" cellpadding="0" align="center" border="0" valign="top">
			<tbody valign="top">
				<tr>
					<td>Created On:</td>
					<td>${summaryData.created_at}</td>
				</tr>
				<tr>
					<td>Moderator:</td>
					<td>${summaryData.moderator}</td>
				</tr>
				<tr>
					<td>Members:</td>
					<td>${summaryData.members}</td>
				</tr>
				<!--tr>
					<td>Active Members:</td>
					<td>${summaryData.acount}</td>
				</tr-->
				<!--tr>
					<td>Passive Members:</td>
					<td>${summaryData.pcount}</td>
				</tr-->
				<tr>
					<td>City:</td>
					<td>${summaryData.city}</td>
				</tr>
				
			</tbody>
		</table>
		</td>
		<td valign="top">
			<table width="100%" cellspacing="0" cellpadding="0" align="center" border="0" valign="top">
			<tbody valign="top">
				<tr>
					<td>Membership Type:</td>
					<td>${summaryData["club.memberapproval.type"]}</td>
				</tr>
				<!--<tr>
					<td>Membership:</td>
					<td></td>
				</tr>
				-->
				
				<tr>
					<td>Category:</td>
					<td>${summaryData.category}</td>
				</tr>
				<tr>
					<td>Country:</td>
					<td>${summaryData.country}</td>
				</tr>
		</table>

</table>
  </div>
  <div class="boxfooter">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td width="50%"><input type="button" id="editcommbtn" value="Edit Community" />&nbsp;&nbsp;
</tr>
</table>
</div>
</div>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
var editCommBtn = new YAHOO.widget.Button("editcommbtn", { onclick: { fn: editCommunity,obj: {"groupId":"${groupId}"} } });
function editCommunity(event,jsonObj){	
	var jsondata = eval(jsonObj);
	var url='CommunitesAction!createCommunity?purpose=edit&groupId='+jsondata.groupId;	
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();	
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getSuccess,failure:getFailure});
}
</script>