<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" language="JavaScript">
</script>
<jsp:include page="../help/eventmanage_messages.jsp" />
<div id="summary"></div>
<div id="textareadiv" style="padding:5px">
<form action="maillistmanage!SaveBulk" name="bulkuplaodform" id="bulkuplaodform">
<s:hidden name="listId"/>
	Enter Email, First Name and Last Name. Separate them with comma (,) in each line. <a class="helpboxlink" onClick="javascript:bulkUploadHelp()"><img src="../images/questionMark.gif" /></a><br>
	<s:textarea rows="20" cols="85" name="bulkmails" id="bulktextarea" wrap="off"/><br>
	<s:checkbox name="replaceDuplicates"></s:checkbox>Replace if duplicate email addresses are found.
</form>
</div>


