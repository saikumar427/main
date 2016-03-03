<%@taglib uri="/struts-tags" prefix="s"%>
<script>
YAHOO.namespace('noticeboarditems');
</script>
<style>
.yui-dt table{
width:100%;
}
</style>
<s:hidden name="eid"></s:hidden>
<script type="text/javascript" src="NoticeBoard!getNoticeItems?eid=${eid}"></script> 
<script type="text/javascript" src="../js/noticeboard/noticeboarditemslistloader.js"></script>
<script type="text/javascript" src="../js/noticeboard/noticeboard.js"></script>
<div class="box" align="left">
<div class="boxheader">Notice Board&nbsp;<span class="helpboxlink" id="noticeboardhelp"><img src="../images/questionMark.gif" /></span></div>
<div id="noticeboardhelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(content_noticeboard_helptitle)</script></div>
<div class="bd"><script>setHelpContent("content_noticeboard_helpcontent")</script></div>
</div>
<div class="boxcontent">
<table width="100%">
<tr><td>
<div id="noticeboarditemslisttable"></div>
</td></tr>
</table>
</div>
<div class="boxfooter">
<input type="button"  id="addNoticeBtn" value="Post Notice"/> 
</div>
</div>
<script>
loadHelpPanel("noticeboardhelppanel", "noticeboardhelp", "500px");
var currenteid=${eid};
createNoticeItemslistTable();	
var addNoticeBtn = new YAHOO.widget.Button("addNoticeBtn", { onclick: { fn: submitAddNotice } });
</script>