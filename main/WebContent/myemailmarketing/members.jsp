
<script>
YAHOO.namespace('members');
</script>

<!-- <script type="text/javascript" src="../js/emailmarketing/memberlist.js"></script> -->
<script type="text/javascript" src="maillistmanage!populateMembers?listId=${listId}"></script>
<script type="text/javascript" src="../js/emailmarketing/memberlistloader.js"></script>
<div id="members_tab"></div>
<script>
_listId=${listId};
var dataarray = new Array("Active (${memberSummaryCount['ACTIVE_COUNT']})","Inactive (${memberSummaryCount['INACTIVE_COUNT']})","Bounced (${memberSummaryCount['BOUNCED_COUNT']})","Unsubscribed (${memberSummaryCount['UNSUB_COUNT']})");
createMemberSummaryTabs(dataarray);	
</script>



