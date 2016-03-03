<%@taglib uri="/struts-tags" prefix="s"%>
<jsp:include page="/help/eventmanage_messages.jsp"></jsp:include>
<div class="box" align="left">
<div class="boxheader">Event URL</div>
<div class="boxcontent">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
		<td colspan="2"><script>setHelpContent("Integrationlinks_eventurl_helpcontent")</script></td>
	</tr>
	<tr><td height="8"></td></tr>
	<tr>
		<td colspan="2"><s:textfield name="eventData.eventURL" size="60" onclick="this.select()"></s:textfield>
</td>
	</tr>
	<tr><td height="8"></td></tr>
</table>

</div>

<div class="boxfooter"><a id="previewlink"
	href="/event?eid=${eid}" target="_blank">Preview</a> &nbsp; <s:if test="%{subMgr==null || submgr_permissions['IntegrationLinks']=='yes'}"><input
	type="button" id="customurlbtn" name="customurlbutton"
	value="Set Custom URL" /></s:if></div>

</div>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
		var customurlBtn = new YAHOO.widget.Button("customurlbtn", { onclick: { fn: getCustomUrlBlock ,obj:{"eid":"${eid}","powertype":"${powertype}","curlevel":"${currentLevel}","curfee":"${currentFee}"}} });
		var previewlink = new YAHOO.widget.Button("previewlink", { type:"link",href:"/event?eid=${eid}" });
</script>
