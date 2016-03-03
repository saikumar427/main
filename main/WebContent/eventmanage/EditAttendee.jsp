
<script type="text/javascript" language="JavaScript"
	src="../js/common/json.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../js/controls/ticketwidget.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../js/controls/textboxWidget.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../js/controls/textareaWidget.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../js/controls/selectWidget.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../js/controls/radioWidget.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../js/controls/checkboxWidget.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../js/controls/buildcontrol.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../js/eventmanage/editattendee.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>


<div class="row" id="buyerstatus" style="display: none;"></div>


<div class='taskbox'><span class="taskheader" id="profile_header" style="display:none;"></span>
<div id='profile'></div>
</div>
<script>
getProfileBlock(${eid},${profilejson},${ticketid},${profileresponse},'${tid}','${pid}');
</script>