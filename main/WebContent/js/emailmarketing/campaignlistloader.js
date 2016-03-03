function test(tid){
alert(tid);
}
function instantiateCampaignListTable(ds, cname, filterElmName,filterTimeout){

 var campDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["name","id","action"]
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
        rowsPerPage   : 5,
        pageLinks     : 3
    });
    var conf = {
        paginator : mypaginator,
        MSG_EMPTY:"&nbsp;"
        
    };
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    elLiner.innerHTML = '&nbsp;<a href="../myemailmarketing/campaignlistmanage!campaignInfo?campId='+oRecord.getData("id")+'">Manage</a>';
    };
    YAHOO.widget.DataTable.Formatter.myActions = myCustomFormatter;
    var myColumnDefs = [
            {key:"name", sortable:true, label:"Name", width:278},
            {key:"action", sortable:false, label:"", formatter:"myActions"}];
   var campDataTable =new YAHOO.widget.DataTable(cname, myColumnDefs, campDataSource,conf);
   campDataTable.subscribe("rowMouseoverEvent", campDataTable.onEventHighlightRow); 
   campDataTable.subscribe("rowMouseoutEvent", campDataTable.onEventUnhighlightRow);   
   campDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			var column = campDataTable.getColumn(target);
				var colindex = column.getIndex();
				if(colindex==1) {
					return;
				}
   window.location.href="../myemailmarketing/campaignlistmanage!campaignInfo?campId="+record.getData('id');
    			
});


    var updateFilter  = function () {
        // Reset timeout
        filterTimeout = null;
        // Get filtered data
        campDataSource.sendRequest(YAHOO.util.Dom.get(filterElmName).value,{
            success : campDataTable.onDataReturnInitializeTable,
            failure : campDataTable.onDataReturnInitializeTable,
            scope   : campDataTable,
            argument: "title"
        });
    };
    
    YAHOO.util.Event.on(filterElmName,'keyup',function (e) {
        clearTimeout(filterTimeout);
        setTimeout(updateFilter,600);
    });       
}
var createCampaignlistTable= function() {		
        var campfilterTimeout = null;
        instantiateCampaignListTable(YAHOO.campaign.Data.citems,"campaignlisttable","campaignfilter",campfilterTimeout);
}




