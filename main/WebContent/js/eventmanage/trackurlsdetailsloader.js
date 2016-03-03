function instantiateTrackURLsDetailsTable(ds, cname, filterElmName,filterTimeout){

 var TRackURLDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["trackingcode","url","count","id","tickets","action"]
        }
    });
    var mypaginator = new YAHOO.widget.Paginator({
        rowsPerPage   : 5,
        pageLinks     : 3
    });
    var conf = {
        paginator : mypaginator,
        MSG_EMPTY:"&nbsp;"
        
    };
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {    	
   // elLiner.innerHTML = '&nbsp;[<a href="#" onclick=TrackManagePage('+currentEid+',"'+oRecord.getData("trackingcode")+'","'+oRecord.getData("count")+'","'+oRecord.getData("id")+'")>Manage</a>&nbsp;|&nbsp;<a href="#" onclick=Reports('+currentEid+',"'+oRecord.getData("trackingcode")+'","'+oRecord.getData("id")+'")>Report</a>]';
    	var tcode = oRecord.getData("trackingcode");
    	var displaycontent="<span>"+oRecord.getData("trackingcode");
    	displaycontent+="<div align='left'>";
    	displaycontent+='[<a href="#" onclick=TrackManagePage('+currentEid+',"'+oRecord.getData("trackingcode")+'","'+oRecord.getData("count")+'","'+oRecord.getData("id")+'")>Manage</a>&nbsp;|&nbsp;<a href="#" onclick=Reports('+currentEid+',"'+tcode+'","'+oRecord.getData("id")+'")>Report</a>&nbsp;|&nbsp;<a href="#" onclick=Widget('+currentEid+',"'+tcode+'","'+oRecord.getData("id")+'")>Widget Code</a>]';
    	displaycontent+="</div></span>";
         elLiner.innerHTML = displaycontent; 
    };
     var CustomTicketsDisplayFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var ticketsdisplaycontent="<span>";
    	var tickets = oRecord.getData("tickets");
    	for(var i=0;i<tickets.length;i++){
        	ticketsdisplaycontent+="<li> "+tickets[i].tname+" - " +tickets[i].tqty+"</li>";
    	}
    	
    	ticketsdisplaycontent+="</span>";
         elLiner.innerHTML = ticketsdisplaycontent;                   
    };
    YAHOO.widget.DataTable.Formatter.myActions = myCustomFormatter;
    YAHOO.widget.DataTable.Formatter.ticketsDisplay = CustomTicketsDisplayFormatter;
    var myColumnDefs = [
            {key:"trackingcode", sortable:true, label:"Name", formatter:"myActions"},
            {key:"url", sortable:true, label:"URL"},
            {key:"tickets", sortable:false, label:"Registrations", formatter:"ticketsDisplay"},
            {key:"count", sortable:true, label:"Visits"}];
   var TrackURLDataTable =new YAHOO.widget.DataTable(cname, myColumnDefs, TRackURLDataSource,conf);
   TrackURLDataTable.subscribe("", TrackURLDataTable.onEventHighlightRow); 
   TrackURLDataTable.subscribe("", TrackURLDataTable.onEventUnhighlightRow);   
   TrackURLDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			var column = TrackURLDataTable.getColumn(target);
				var colindex = column.getIndex();
				if(colindex==1) {
					return;
				}
     			
});


    var updateFilter  = function () {
        // Reset timeout
        filterTimeout = null;
        // Get filtered data
        TRackURLDataSource.sendRequest(YAHOO.util.Dom.get(filterElmName).value,{
            success : TrackURLDataTable.onDataReturnInitializeTable,
            failure : TrackURLDataTable.onDataReturnInitializeTable,
            scope   : TrackURLDataTable,
            argument: "title"
        });
    };
    
    YAHOO.util.Event.on(filterElmName,'keyup',function (e) {
        clearTimeout(filterTimeout);
        setTimeout(updateFilter,600);
    });       
}
var createTrackURLsDetailsTable= function() {	
        var TrackURLsfilterTimeout = null;
        instantiateTrackURLsDetailsTable(YAHOO.trackurls.Data.citems,"trackurlsdetails","trackurlsfilter",TrackURLsfilterTimeout);
}

