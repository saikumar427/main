
function instantiateMailListTable(ds, cname, filterElmName,filterTimeout){

 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["name","id","count","action"]
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
        rowsPerPage   : 50,
        pageLinks     : 3
    });
    var conf = {
        paginator : mypaginator,
        MSG_EMPTY:"&nbsp;"
        
    };
    var myColumnDefs = [
            {key:"name", sortable:true, label:"Name"},
            {key:"count", sortable:true, label:"Members"},
            {key:"action", sortable:false, label:""}
        ];
    var myDataTable = 
                new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,conf);
                myDataTable.subscribe("rowMouseoverEvent", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("rowMouseoutEvent", myDataTable.onEventUnhighlightRow);
                myDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			window.location.href="../myemailmarketing/maillistmanage!listInfo?listId="+record.getData('id');
    			
});

	
    var updateFilter  = function () {
        // Reset timeout
        filterTimeout = null;
        // Get filtered data
        myDataSource.sendRequest(YAHOO.util.Dom.get(filterElmName).value,{
            success : myDataTable.onDataReturnInitializeTable,
            failure : myDataTable.onDataReturnInitializeTable,
            scope   : myDataTable,
            argument: "title"
        });
    };
    
    YAHOO.util.Event.on(filterElmName,'keyup',function (e) {
        clearTimeout(filterTimeout);
        setTimeout(updateFilter,600);
    });       
                return myDataTable;
}
var createMaillistTable = function() {		
        var mailfilterTimeout = null;
         
        var myDataTable = instantiateMailListTable(YAHOO.maillist.Data.items,"maillisttable","listfilter",mailfilterTimeout);
            
       
    
}




