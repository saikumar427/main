function instantiateNoticeItemsListTable(ds, cname, filterElmName,filterTimeout){

 var noticeitemsListDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["noticetype","id","action","notice","postedat"]
        },
        doBeforeCallback : function (req,raw,res,cb) {
            // This is the filter function
            var data     = res.results || [],
                filtered = [],
                i,l;

            if (req) {
                req = req.toLowerCase();
                for (i = 0, l = data.length; i < l; ++i) {                	
                    if (data[i].notice.toLowerCase().indexOf(req)>-1) {
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
    	//submiteditNotice(currenteid,oRecord.getData("id"));
    	var temp=oRecord.getData("id");
    	var temp1='&nbsp;<a  onclick=submiteditNotice('+temp+')>';
    	var temp2='<a href="#" onclick=deleteNotice('+temp+')>';
        elLiner.innerHTML = temp1+'Edit</a>&nbsp;|&nbsp;'+temp2+'Delete</a>';
    };
    YAHOO.widget.DataTable.Formatter.myActions = myCustomFormatter;
    var myColumnDefs = [
            {key:"noticetype", sortable:true, label:"Notice Type"},
            {key:"notice", sortable:true, label:"Notice", width:278},
            {key:"postedat", sortable:true, label:"Posting Time"},
            {key:"action", sortable:false, label:"", formatter:"myActions"}];
   var noticeItemsDataTable =new YAHOO.widget.DataTable(cname, myColumnDefs, noticeitemsListDataSource,conf);
   noticeItemsDataTable.subscribe("rowMouseoverEvent", noticeItemsDataTable.onEventHighlightRow); 
   noticeItemsDataTable.subscribe("rowMouseoutEvent", noticeItemsDataTable.onEventUnhighlightRow);   
   noticeItemsDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			var column = noticeItemsDataTable.getColumn(target);
				var colindex = column.getIndex();
				if(colindex==3) {
					return;
				}
  // window.location.href="NoticeBoard!editNoticeData?eid='+currenteid+'&noticeId='+oRecord.getData('id')";
				submiteditNotice(record.getData('id'));
    			
});
    var updateFilter  = function () {
        // Reset timeout
        filterTimeout = null;
        // Get filtered data
        noticeitemsListDataSource.sendRequest(YAHOO.util.Dom.get(filterElmName).value,{
            success : noticeItemsDataTable.onDataReturnInitializeTable,
            failure : noticeItemsDataTable.onDataReturnInitializeTable,
            scope   : noticeItemsDataTable,
            argument: "notice"
        });
    };
    
    YAHOO.util.Event.on(filterElmName,'keyup',function (e) {
        clearTimeout(filterTimeout);
        setTimeout(updateFilter,600);
    });       
}
var createNoticeItemslistTable= function() {		
        var noticefilterTimeout = null;
        instantiateNoticeItemsListTable(YAHOO.noticeboarditems.Data.citems,"noticeboarditemslisttable","noticeitemfilter",noticefilterTimeout);
}




