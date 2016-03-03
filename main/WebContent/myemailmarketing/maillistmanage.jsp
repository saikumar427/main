<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css" href="../build/menu/assets/skins/sam/menu.css" />
<script type="text/javascript" src="../build/container/container_core-min.js"></script>
<script type="text/javascript" src="../build/menu/menu-min.js"></script>

<script src="../js/emailmarketing/emailmarketing.js" language="JavaScript"	type="text/javascript"></script>
<script type="text/javascript" src="../js/popup.js">
function popupdummy(){}
</script>
<script type="text/javascript">
YAHOO.namespace("ebee.maillistmanageForm");
</script>
<jsp:include page="../help/eventmanage_messages.jsp" />
<table width="100%">
<tr>
<td width="60%" valign="top">
<jsp:include page="members.jsp" ></jsp:include>
</td>
<td width="5"></td>
<td valign="top">
<div class="box" align="left">
<div class="boxheader">List Info</div>
<div class="boxcontent"> 
<table  border="0" cellpadding="0" cellspacing="2" width="100%" >
<tr>
<td width="30%">Members:</td><td>${memberSummaryCount.ALL_COUNT}</td>
</tr>
<tr>
<td>Description:</td><td><s:property value="mailList.descritption"/></td>
</tr>
</table> 
</div>
<div class="boxfooter">
<span id="listManage"></span><span id="memberAdd"></span>
</div>
</div>
<div><table height="10"><tr><td></td></tr></table></div>
<div class="box" align="left">
<div class="boxheader">Email Subscription Widget</div>
<div class="boxcontent">
<p>Copy and paste the following code into your website, blog or forums. </p>
<textarea cols="53" rows="9" onClick="this.select()"><iframe  id='_EbeeIFrame' name='_EbeeIFrame' frameborder="0" scrolling="no"  src="<s:text name='eventData.serverAddress'></s:text>/main/widget/emailsubscribe?listId=${listId}&userId=${userId}"  height='400' width='600'></iframe>
	   </textarea> 
</div>
</div>
<!--  
<div class="box" align="left">
<div class="boxheader">Add Members</div>
<div class="boxcontent"> 
Can add Members through below options
</div>
<div class="boxfooter">[<a href="#" onClick="addManual(${listId},${userId});">Add Manually</a>&nbsp;|&nbsp;
<a href="#" onClick="mergeLists(${listId},${userId});">Merge List</a>&nbsp;|&nbsp;
<a href="#" onClick="fileUpload(${listId},${userId});">File Upload</a>]
</div>
</div>
-->

</td>
</tr>
</table>
<form name="mailistManagaeForm" id="mailistManagaeForm" action="maillistmanage!showMemberData">
<input type="hidden" name="filePath" id="filepath">
</form>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<div id="popinpopupdialog"><div id="hd"></div><div id="bd"></div></div>
<div id="iframepopupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
var editBtn = new YAHOO.widget.Button("editBtn", { onclick: { fn: editMailListDetails,obj:{"listId":"${listId}", "userId":"${userId}"} } });
var deleteBtn = new YAHOO.widget.Button("deleteBtn", { onclick: { fn: deleteMailListDetails, obj: {"listId":"${listId}","userId":"${userId}"} }});
var subscribe = new YAHOO.widget.Button("subscribe",{ onclick: { fn: subscribeWidget, obj: {"listId":"${listId}","userId":"${userId}"} }});
</script>
<script>
YAHOO.ebee.maillistmanageForm.init = function () {
	//var qdata = eval(YAHOO.ebee.event.data.questions);
	var divid = "memberAdd";
		//YAHOO.util.Event.onContentReady(divid,function(){
			var MemberAdd_ManageMenu = [			
				{ text: "Add Manually", value: 1, onclick: { fn: addManualJS ,obj: {"listId":"${listId}","userId":"${userId}" } }},
				//{ text: "Merge List", value: 2, onclick: { fn: mergeListJS ,obj: {"listId":"${listId}","userId":"${userId}" } }},
				{ text: "File Upload", value: 3, onclick: { fn: fileUploadJS ,obj: {"listId":"${listId}","userId":"${userId}" } }}
				,{ text: "Bulk Upload", value: 4, onclick: { fn: bulkUploadJS ,obj: {"listId":"${listId}","userId":"${userId}" } }}	
			];
			var splitBtn = new YAHOO.widget.Button({ type: "menu",  label: "Add Members", name: "Manage", menu: MemberAdd_ManageMenu, container: divid });
		//});
			var divid1 = "listManage";
		var List_ManageMenu = [			
				{ text: "Edit", value: 1, onclick: { fn: editMailListDetails ,obj: {"listId":"${listId}","userId":"${userId}" } }},
				{ text: "Delete", value: 2, onclick: { fn: deleteMailListDetails ,obj: {"listId":"${listId}","userId":"${userId}" } }}
					
			];
			var splitBtn = new YAHOO.widget.Button({ type: "menu",  label: "Manage", name: "Manage", menu: List_ManageMenu, container: divid1 });
	}();
	/*end*/
</script>