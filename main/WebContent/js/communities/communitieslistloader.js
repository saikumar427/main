function instantiateCommunitiesListTable(ds, cname, filterElmName,filterTimeout){

 var communitiesListDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["name","id","action","createdon","members","status"]
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
    	
    elLiner.innerHTML = '&nbsp;<a href="CommunityManage?groupId='+oRecord.getData("id")+'">Manage</a>';
    };
    YAHOO.widget.DataTable.Formatter.myActions = myCustomFormatter;
    var myColumnDefs = [
            {key:"name", sortable:true, label:"Name", width:278},
            //{key:"place", label:"Place"},
            {key:"createdon", sortable:true, label:"Creation Date"},
            {key:"status", sortable:true, label:"Status"},
            {key:"action", sortable:false, label:"", formatter:"myActions"}];
   var communityDataTable =new YAHOO.widget.DataTable(cname, myColumnDefs, communitiesListDataSource,conf);
   communityDataTable.subscribe("rowMouseoverEvent", communityDataTable.onEventHighlightRow); 
   communityDataTable.subscribe("rowMouseoutEvent", communityDataTable.onEventUnhighlightRow);   
   communityDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			//var column = communityDataTable.getColumn(target);
				//var colindex = column.getIndex();
				/*if(colindex==1) {
					return;
				}*/
   window.location.href="CommunityManage?groupId="+record.getData('id');
    			
});
    var updateFilter  = function () {
        // Reset timeout
        filterTimeout = null;
        // Get filtered data
        communitiesListDataSource.sendRequest(YAHOO.util.Dom.get(filterElmName).value,{
            success : communityDataTable.onDataReturnInitializeTable,
            failure : communityDataTable.onDataReturnInitializeTable,
            scope   : communityDataTable,
            argument: "title"
        });
    };
    
    YAHOO.util.Event.on(filterElmName,'keyup',function (e) {
        clearTimeout(filterTimeout);
        setTimeout(updateFilter,600);
    });       
}
var createCommunitieslistTable= function() {		
        var filterTimeout = null;
        instantiateCommunitiesListTable(YAHOO.communitiesList.Data.citems,"communitieslisttable","communityfilter",filterTimeout);
}


