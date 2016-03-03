function instantiateTrackURLsSummaryTable(ds, cname, filterElmName,filterTimeout){

 var TRackURLSummaryDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["trackingcode","url","count","id","tickets","action"]
        },
        doBeforeCallback : function (req,raw,res,cb) {
            // This is the filter function
            var data     = res.results || [],
                filtered = [],
                i,l;

            if (req) {
                req = req.toLowerCase();
                for (i = 0, l = data.length; i < l; ++i) {                	
                    if (data[i].name.toLowerCase().indexOf(req)>-1) {
                        filtered.push(data[i]);
                    }
                }
                res.results = filtered;
            }

            return res;
        }
    });
    var mypaginator = new YAHOO.widget.Paginator({
        rowsPerPage   : 10,
        pageLinks     : 3
    });
    var conf = {
        paginator : mypaginator,
        MSG_EMPTY:"&nbsp;"
        
    };
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {   
        elLiner.innerHTML = '&nbsp;<a href="#" onclick=Reports('+currentEid+',"'+oRecord.getData("trackingcode")+'","'+oRecord.getData("id")+'")>Report</a>';
    };
    YAHOO.widget.DataTable.Formatter.myActions = myCustomFormatter;
    var myColumnDefs = [
            {key:"trackingcode", sortable:true, label:"Name"},
            {key:"url", sortable:true, label:"URL", width:390},
            {key:"count", sortable:true, label:"Visits"},
            {key:"tickets", sortable:true, label:"Tickets"},
            {key:"action", sortable:false, label:"", formatter:"myActions"}];
   var TrackURLSummaryDataTable =new YAHOO.widget.DataTable(cname, myColumnDefs, TRackURLSummaryDataSource,conf);
   TrackURLSummaryDataTable.subscribe("", TrackURLSummaryDataTable.onEventHighlightRow); 
   TrackURLSummaryDataTable.subscribe("", TrackURLSummaryDataTable.onEventUnhighlightRow);   
   TrackURLSummaryDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			var column = TrackURLSummaryDataTable.getColumn(target);
				var colindex = column.getIndex();
				if(colindex==1) {
					return;
				}
     			
});


    var updateFilter  = function () {
        // Reset timeout
        filterTimeout = null;
        // Get filtered data
        TRackURLSummaryDataSource.sendRequest(YAHOO.util.Dom.get(filterElmName).value,{
            success : TrackURLSummaryDataTable.onDataReturnInitializeTable,
            failure : TrackURLSummaryDataTable.onDataReturnInitializeTable,
            scope   : TrackURLSummaryDataTable,
            argument: "title"
        });
    };
    
    YAHOO.util.Event.on(filterElmName,'keyup',function (e) {
        clearTimeout(filterTimeout);
        setTimeout(updateFilter,600);
    });       
}
var createTrackURLsSummaryTable= function() {	
        var TrackURLssummaryfilterTimeout = null;
        instantiateTrackURLsSummaryTable(YAHOO.trackurlssummary.Data.citems,"trackurlssummarydetails","trackurlssummaryfilter",TrackURLssummaryfilterTimeout);
}




