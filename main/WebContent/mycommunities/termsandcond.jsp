<%@taglib uri="/struts-tags" prefix="s" %>
<s:url id="backURL" action="ManageRegistrationForm">
<s:param name="groupId" value="%{groupId}"></s:param>
</s:url>
<script>
function backToManage(){
	window.location.href='${backURL}';
}
</script>
<s:form name="termsandcondform" id="termsandcondform" action="CommunityManage!saveTermsandCond" method="post" theme="simple">
<s:hidden name="groupId"></s:hidden>
<s:hidden name="clubInfo.clubId"></s:hidden>

<div id="termsstatusmsg"></div>
<jsp:include page="/help/mycommunities_messages.jsp"></jsp:include>

<div style="height:3px;"></div>
<div class="taskbox">
<span class="taskheader">Membership Terms &amp; Conditions</span><br/>
<div>
<s:fielderror cssErrorClass="errorMessage" theme="simple">
</s:fielderror>
</div>
<table width="100%" >
<tr>
<td ></td>
<td>
<s:textarea name="clubInfo.termsCond" rows="20" cols="115"></s:textarea>
</td>
</tr>

</table>
</div>
<p/>
<table align="center" width="100%">
<tr>
<td colspan="2" align="center">
<input type="button" value="Submit" id="submitBtn" onClick="updateTermsnConditions()">
<input type="button" value="Cancel" id="cancelBtn" onclick="backToManage();"  />
</td>
</tr>
</table>
</s:form>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
function updateTermsnConditions(){

$('termsandcondform').request({
  		onComplete: function(o){
  			$('termsstatusmsg').update(o.responseText);
  			$('termsstatusmsg').show();
  			$('termsstatusmsg').scrollTo();	
	  			
  		}
	})
}
function getStatusMsg(){
		new Ajax.Request('ajaxstatusmsg.jsp', {
		method: 'get',
		parameters:{msgType:'termcondsaved'},
		onSuccess: showStatusMsg
		});
}
function showStatusMsg(response) {
  document.getElementById("termsstatusmsg").focus();		
		document.getElementById("termsstatusmsg").innerHTML=response.responseText;
}
</script>
<script>
var btn = new YAHOO.widget.Button("submitBtn", { onclick: {fn: updateTermsnConditions } });
var btn1 = new YAHOO.widget.Button("cancelBtn", { onclick: {fn: cancelThisEvent } });
function cancelThisEvent(){
	backToManage();
}
</script>
