<%@taglib uri="/struts-tags" prefix="s"%>
<s:hidden name="groupId"/>
<a href="CommunityManage!showTermCond?groupId=${groupId}" id="termcondlnk" >Terms &amp; Conditions</a>
<script>
var termcondlnk = new YAHOO.widget.Button("termcondlnk",  {type: "link"});
</script>

