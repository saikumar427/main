<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css" href="../css/backgroundDisablePopup.css" />
<script src="../js/emailmarketing/previewdata.js" language="JavaScript"	type="text/javascript"></script>
<script>
YAHOO.namespace('emailblastsdata');
var currentCampId=${campId};

</script>
<script src="../js/emailmarketing/campaignmanage.js" language="JavaScript"	type="text/javascript"></script>
<script type="text/javascript" src="../js/popup.js">
function popupdummy(){}
</script>
<div id="backgroundPopup" ></div>
<input type="hidden" id="campId" value="${campId}"/>
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<td width="60%" valign="top" colspan="2">

<jsp:include page="emailblastdata.jsp" ></jsp:include>
</td>
</tr>
<tr>
<td class="boxfooter" colspan="2">
<a id="scheduleBlastlink"	href="../myemailmarketing/campaignlistmanage!scheduleblast?campId=${campId}" >Schedule Blast</a>&nbsp;
<input id="editCampaignBtn" type="button"	value="Edit Campaign" />&nbsp;
<input id="previewBtn" type="button"  value="Preview"/>
<s:if test="%{count<1}"><input id="deleteBtn" type="button"  value="Delete Campaign" /></s:if>
</td>
</tr>
</table>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<div id="iframepopupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
function editThisCampaign(event, jsonobj){
editCampaign(jsonobj.campId);
}
function init() {
var schedulecount=YAHOO.emailblastsdata.Data.SCHEDULED.length;
var editCampaignBtn = new YAHOO.widget.Button("editCampaignBtn", { onclick: { fn: editThisCampaign,obj:{"campId":"${campId}"} } });
var scheduleBlastlink = new YAHOO.widget.Button("scheduleBlastlink", { type:"link",href:"../myemailmarketing/campaignlistmanage!scheduleblast?campId=${campId}" });
var previewBtn = new YAHOO.widget.Button("previewBtn", { onclick: { fn: getPreview,obj:{"campId":"${campId}"}} });
var deleteBtn = new YAHOO.widget.Button("deleteBtn", { onclick: { fn: deleteCampaign,obj:{"campId":"${campId}","count":schedulecount}}});
}
YAHOO.util.Event.onDOMReady(init);
</script>
