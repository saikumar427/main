<script>
YAHOO.namespace('scanids');
</script>
<style>
.yui-dt table{
width:100%;
}
</style>
<script type="text/javascript" src="ScanIDs!populateScanIdsList?eid=${eid}"></script> 
<!-- <script type="text/javascript" src="../js/eventmanage/scanids.js"></script> -->
<script type="text/javascript" src="../js/eventmanage/scaniddetailsloader.js"></script>

<div id="scaniddetails"></div>
<script>
var currentEid=${eid};
createScanIdDetailsTable();	
</script>