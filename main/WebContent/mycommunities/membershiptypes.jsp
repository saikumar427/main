<%@taglib uri="/struts-tags" prefix="s"%>
<script>
YAHOO.namespace('membershiptypes');
</script>
<style>
.yui-dt table{
width:100%;
}
</style>

<s:hidden name="groupId"/>
<script type="text/javascript" src="../js/communities/communitymanage.js"></script>
<!--<script type="text/javascript" src="../js/communities/membershiptypeslist.js"></script>-->
<script type="text/javascript" src="CommunityManage!showMembershipTypes?groupId=${groupId}"></script> 
<script type="text/javascript" src="../js/communities/membershiptypeslistloader.js"></script>
<div class="box" align="left">
<div class="boxheader">Membership Types</div>
<div class="boxcontent">

<table width="100%">
<tr><td>
<!--
<div align='right'>Search By Membership Name <input type='text' id='membershiptypefilter' value=''></div>
  -->
<div id="membershiptypeslisttable"></div>
</td></tr>
</table>
</div>
<div class="boxfooter">
<input type="button" value="Create Membership Type" id="addMembershipTypebtn">
</div>
</div>
<script>
var currentGroupId=${groupId};
var addDiscountlnk = new YAHOO.widget.Button("addMembershipTypelnk",  {type: "link"});
var btn = new YAHOO.widget.Button("addMembershipTypebtn", { onclick: {  fn: addMembership,obj: {"groupId":"${groupId}"}} });
createMembershipTypeslistTable();	

</script>
<div id="popupdialog">
<div id="hd"></div>
<div id="bd"></div>
</div>