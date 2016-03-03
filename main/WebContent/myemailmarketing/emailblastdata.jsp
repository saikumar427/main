<script>
YAHOO.namespace('emailblastsdata');
</script>
<style>
.yui-dt table{
width:100%;
}
</style>
<!-- <script type="text/javascript" src="../js/emailmarketing/emailblastslist.js"></script> -->
<script type="text/javascript" src="campaignlistmanage!populateListData?campId=${campId}"></script>
<script type="text/javascript" src="../js/emailmarketing/emailblastssummaryloader.js"></script>
<div id="emailblastsdata_tab"></div>
<script>
var dataarray = new Array("Scheduled ("+YAHOO.emailblastsdata.Data.SCHEDULED.length+")","Completed ("+YAHOO.emailblastsdata.Data.COMPLETED.length+")");
createEmailBlastsSummaryTabs(dataarray);	
</script>