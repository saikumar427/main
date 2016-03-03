<script>
YAHOO.namespace('trackurls');
</script>
<style>
.yui-dt table{
width:100%;
}
</style>
<script type="text/javascript" src="TrackURL!populateTrackurlsList?eid=${eid}"></script> 
<script type="text/javascript" src="../js/eventmanage/trackurlsdetailsloader.js"></script>

<div id="trackurlsdetails"></div>
<script>
var currentEid=${eid};

createTrackURLsDetailsTable();	
filltracksaleschartdata();

function filltracksaleschartdata(){
tracksaleschartdata = [];
var totalSold=0;
citems=YAHOO.trackurls.Data.citems;
for(var i=0;i<citems.length;i++){
ticketsold=citems[i].tickets;
var qty=0;
for(var j=0;j<ticketsold.length;j++){
qty+=Number(ticketsold[j].tqty);
totalSold+=qty;
}
trackData=[citems[i].trackingcode, {v:qty, f:'Total Tickets Sold - '+qty}]
tracksaleschartdata.push(trackData);
}
if(totalSold==0) tracksaleschartdata = [];
}
</script>


