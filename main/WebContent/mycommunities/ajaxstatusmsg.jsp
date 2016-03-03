<%@taglib uri="/struts-tags" prefix="s"%>
<div id="statusMessageBox" class="statusMessageBox">
<div style="float: left;valign: middle;">
<s:if test="%{msgType=='termcondsaved'}">
Updated Terms & Conditions
</s:if>

</div>
<div style="float: right;padding:5px;"><a href="javascript:hidestatusmessage()"><img src="../images/deletebutton.JPG" border="0"></a></div>
</div>

