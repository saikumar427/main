<script>
YAHOO.namespace('ticketurls');
</script>
<style>
.yui-dt table{
width:100%;
}
</style>
<!--  <script type="text/javascript" src="../js/eventmanage/ticketurlslist.js"></script>--> 
<script type="text/javascript" src="TicketURLs!populateTicketurlsList?eid=${eid}"></script> 
<script type="text/javascript" src="../js/eventmanage/ticketurlsreportloader.js"></script>
<div id="ticketurlstable"></div>
<script>
var currentEid=${eid};
createTicketURLsReportTable();	

</script>