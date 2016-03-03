function instantiateScanIdsDetailsTable(ds, cname, filterElmName,filterTimeout){

 var ScanIdsDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["scanid","id","action"]
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
        rowsPerPage   : 08,
        pageLinks     : 3
    });
    var conf = {
        paginator : mypaginator,
        MSG_EMPTY:"&nbsp;"
        
    };
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {   
    var scanid=oRecord.getData("scanid");
    var eventid=oRecord.getData("id");
   elLiner.innerHTML = '&nbsp;<a href="#" onclick=deleteScan(\''+scanid+'\','+eventid+')>Delete</a>';
    //elLiner.innerHTML = 'Delete';
    };
    YAHOO.widget.DataTable.Formatter.myActions = myCustomFormatter;
    var myColumnDefs = [
            {key:"scanid", sortable:true, label:"Name"},           
            {key:"action", sortable:false, label:"", formatter:"myActions"}]; 
   var ScanIdsDataTable =new YAHOO.widget.DataTable(cname, myColumnDefs, ScanIdsDataSource,conf);
   ScanIdsDataTable.subscribe("", ScanIdsDataTable.onEventHighlightRow); 
   ScanIdsDataTable.subscribe("", ScanIdsDataTable.onEventUnhighlightRow);   
   ScanIdsDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			var column = ScanIdsDataTable.getColumn(target);
				var colindex = column.getIndex();
				if(colindex==1) {
					return;
				}
     			
});


    var updateFilter  = function () {
        // Reset timeout
        filterTimeout = null;
        // Get filtered data
        ScanIdsDataSource.sendRequest(YAHOO.util.Dom.get(filterElmName).value,{
            success : ScanIdsDataTable.onDataReturnInitializeTable,
            failure : ScanIdsDataTable.onDataReturnInitializeTable,
            scope   : ScanIdsDataTable,
            argument: "title"
        });
    };
    
    YAHOO.util.Event.on(filterElmName,'keyup',function (e) {
        clearTimeout(filterTimeout);
        setTimeout(updateFilter,600);
    });       
}
var createScanIdDetailsTable= function() {	
        var ScanIdsfilterTimeout = null;
        instantiateScanIdsDetailsTable(YAHOO.scanids.citems,"scaniddetails","scanidsfilter",ScanIdsfilterTimeout);
}

function deleteScan(scanid,eventid)
{

var agree;
	agree=confirm("ScanId will be deleted from this list for ever. This operation cannot be revert back.");
	if (agree){	
        var url='ScanIDs!deleteScan?scanid='+scanid+'&eid='+eventid;
		callURLandReload(url);
	}
}



