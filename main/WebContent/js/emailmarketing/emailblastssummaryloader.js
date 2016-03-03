var tabView;
function createEmailBlastsSummaryTabs(arrayObj){
	tabView = new YAHOO.widget.TabView();
	
	tabView.addTab( new YAHOO.widget.Tab({
        label: arrayObj[0],
        content:"<div id='dtcontainer2'></div>",     
        active:true
    	}));
    	  	 
    tabView.addTab( new YAHOO.widget.Tab({
        label: arrayObj[1],
         content:"<div id='dtcontainer1'></div>"
    	}));
    	  	
        
    tabView.appendTo('emailblastsdata_tab');
    loadEmailBlastSummarySummaryTables();
}

function instantiateCompletedBlastSummaryTable(ds, cname){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {  fields: ["camp_scheddate","subject","id"] }
        });
    var mypaginator = new YAHOO.widget.Paginator({  
    	rowsPerPage   : 5,      pageLinks     : 3   });
    var conf = {   paginator : mypaginator ,
        MSG_EMPTY:"&nbsp;"};
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var temp=oRecord.getData("id");
    	var temp1='&nbsp;<a href="#" onclick=blastPreview('+temp+') >';
    	var temp2='<a href="#" onclick=blastReport('+temp+')>';
        elLiner.innerHTML = temp1+'Preview</a>&nbsp;|&nbsp;'+temp2+'Reports</a>';
     };
    YAHOO.widget.DataTable.Formatter.CompletedBlastActions = myCustomFormatter;
    var myColumnDefs = [
            {key:"camp_scheddate", sortable:true, label:"Date"},
            {key:"subject", label:"Subject", sortable:true},
            {key:"action", sortable:false, label:"", formatter:"CompletedBlastActions"}
        ];
    
    var myDataTable =    new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,conf);
                myDataTable.subscribe("rowMouseoverEvent", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("rowMouseoutEvent", myDataTable.onEventUnhighlightRow);
                myDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			var column = myDataTable.getColumn(target);
				var colindex = column.getIndex();
				//if(colindex==2) {return;}
    			//javascript:alert(record.getData('id'));
    			//window.location.href="../myemailmarketing/campaignlistmanage!campaignInfo?campId="+record.getData('id');
				//blastReport(record.getData('id'));
});
            return myDataTable;
}

function instantiateScheduledBlastSummaryTable(ds, cname){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {  fields: ["camp_scheddate","subject","id"] }
        });
    var mypaginator = new YAHOO.widget.Paginator({     
    	rowsPerPage   : 5,      pageLinks     : 3   });
    var conf = {   paginator : mypaginator,
        MSG_EMPTY:"&nbsp;" };
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var temp=oRecord.getData("id");
    	var temp1='&nbsp;<a href="#" onclick=blastPreview('+temp+'); >';
    	var temp2='<a href="#" onclick=editEmailBlast('+temp+')>';
    	var temp3='<a href="#" onclick=deleteEmailBlast('+temp+')>';
       elLiner.innerHTML = temp1+'Preview</a>&nbsp;|&nbsp;'+temp2+'Manage</a>'+
        		'&nbsp;|&nbsp;'+temp3+'Delete</a>';
    };
    YAHOO.widget.DataTable.Formatter.ScheduledBlastActions = myCustomFormatter;
    var myColumnDefs = [
            {key:"camp_scheddate", sortable:true, label:"Date"},
            {key:"subject", label:"Subject", sortable:true},
            {key:"action", sortable:false, label:"", formatter:"ScheduledBlastActions"}
        ];
    
    var myDataTable =    new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,conf);
                myDataTable.subscribe("rowMouseoverEvent", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("rowMouseoutEvent", myDataTable.onEventUnhighlightRow);
                myDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			var column = myDataTable.getColumn(target);
				var colindex = column.getIndex();
				//if(colindex==3) {return;}
    			//javascript:alert(record.getData('id'));
				//editEmailBlast(record.getData('id'));
});
            return myDataTable;
}


var loadEmailBlastSummarySummaryTables = function() {		
       
        var myDataTable1 = instantiateCompletedBlastSummaryTable(YAHOO.emailblastsdata.Data.COMPLETED,"dtcontainer1");
        var myDataTable2 = instantiateScheduledBlastSummaryTable(YAHOO.emailblastsdata.Data.SCHEDULED,"dtcontainer2");
                 
        tabView.getTab(0).addListener("click", function() {myDataTable1.onShow()});        
		tabView.getTab(1).addListener("click", function() {myDataTable2.onShow()});
}




