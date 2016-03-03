function instantiateTicketURLsReportTable(ds, cname, filterElmName,filterTimeout){

 var TicketURLDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["code","url","count","id","ticketname","action","ticketnames"]
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
    var CustomTicketsDisplayFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var ticketsdisplaycontent="<span>";
    	var tickets = oRecord.getData("ticketnames");
    	for(var i=0;i<tickets.length;i++){
    	if(tickets[i].name)
        	ticketsdisplaycontent+="<li> "+tickets[i].name+"</li>";
    	}
    	
    	ticketsdisplaycontent+="</span>";
         elLiner.innerHTML = ticketsdisplaycontent;                   
    };
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
     var actionLinks= '&nbsp;<a href="#" onclick=getTickets('+currentEid+',"'+oRecord.getData("id")+'")>Edit</a>';
    actionLinks=actionLinks+'&nbsp;|&nbsp;<a href="#" onclick=deleteTicketingURL('+currentEid+',"'+oRecord.getData("id")+'")>Delete</a>';
    elLiner.innerHTML =actionLinks;
   
   // elLiner.innerHTML = '&nbsp;<a href="#" onclick=getTickets('+currentEid+',"'+oRecord.getData("id")+'")>View/Edit</a>';
    };
    YAHOO.widget.DataTable.Formatter.myActions = myCustomFormatter;
    YAHOO.widget.DataTable.Formatter.ticketsdisplay = CustomTicketsDisplayFormatter;
    var myColumnDefs = [
            {key:"code", sortable:true, label:"Name"},
            {key:"url", sortable:true, label:"URL", width:390},
            {key:"ticketname", sortable:true,formatter:"ticketsdisplay", label:"Tickets"},
            {key:"action", sortable:false, label:"", formatter:"myActions"}];
   var TicketURLDataTable =new YAHOO.widget.DataTable(cname, myColumnDefs, TicketURLDataSource,conf);
   TicketURLDataTable.subscribe("", TicketURLDataTable.onEventHighlightRow); 
   TicketURLDataTable.subscribe("", TicketURLDataTable.onEventUnhighlightRow);   
   TicketURLDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			var column = TicketURLDataTable.getColumn(target);
				var colindex = column.getIndex();
				if(colindex==1) {
					return;
				}
     			
});


    var updateFilter  = function () {
        // Reset timeout
        filterTimeout = null;
        // Get filtered data
        TicketURLDataSource.sendRequest(YAHOO.util.Dom.get(filterElmName).value,{
            success : TicketURLDataTable.onDataReturnInitializeTable,
            failure : TicketURLDataTable.onDataReturnInitializeTable,
            scope   : TicketURLDataTable,
            argument: "title"
        });
    };
    
    YAHOO.util.Event.on(filterElmName,'keyup',function (e) {
        clearTimeout(filterTimeout);
        setTimeout(updateFilter,600);
    });       
}
var createTicketURLsReportTable= function() {	
        var TicketURLsfilterTimeout = null;
        instantiateTicketURLsReportTable(YAHOO.ticketurls.Data.citems,"ticketurlstable","ticketurlsfilter",TicketURLsfilterTimeout);
}




