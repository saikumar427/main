var tabView;
function createMemberSummaryTabs(arrayObj){
	tabView = new YAHOO.widget.TabView();
	
	tabView.addTab( new YAHOO.widget.Tab({
        label: arrayObj[0],
        content:"<div align='right'>Search By Name <input type='text' id='activefilter' value=''></div><div id='dtcontainer1'></div>",     
        active:true
    	}));
    	  	 
    tabView.addTab( new YAHOO.widget.Tab({
        label: arrayObj[1],
         content:"<div align='right'>Search By Name <input type='text' id='inactivefilter' value=''></div><div id='dtcontainer2'></div>"
    	}));
    	  	
    tabView.addTab( new YAHOO.widget.Tab({
        label: arrayObj[2],
        content:"<div align='right'>Search By  Name <input type='text' id='bouncedfilter' value=''></div><div id='dtcontainer3'></div>"
    	}));
    	
    tabView.addTab( new YAHOO.widget.Tab({
        label: arrayObj[3],
        content:"<div align='right'>Search By  Name <input type='text' id='unsubscribedfilter' value=''></div><div id='dtcontainer4'></div>"
    	}));      
      
    tabView.appendTo('members_tab');
    loadMemberSummaryTables();
    
}
function instantiateMemberSummaryTable(ds, cname, filterElmName,filterTimeout){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["name","email","id","action"]
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
        //,sortedBy: {key:'title', dir:YAHOO.widget.DataTable.CLASS_ASC}
    };
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    	var temp=oRecord.getData("id");
    	var temp1='&nbsp;<a href="#" onclick=editmember('+temp+'); >';
    	var temp2='&nbsp;<a href="#" onclick=deletemember('+temp+','+_listId+'); >';
        elLiner.innerHTML = temp1+'Manage</a>'+' &nbsp;|&nbsp;'+temp2+
        						'Delete</a>';
    };
    YAHOO.widget.DataTable.Formatter.myActions = myCustomFormatter;
    var myColumnDefs = [
            {key:"name", sortable:true, label:"Name",width:100},
            {key:"email", label:"Email", sortable:true},
            {key:"action", sortable:false, label:"", formatter:"myActions"}
        ];
    var myDataTable = 
                new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,conf);
                myDataTable.subscribe("rowMouseoverEvent", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("rowMouseoutEvent", myDataTable.onEventUnhighlightRow);
               // myDataTable.on('cellClickEvent',function (oArgs) {
        		//var target = oArgs.target;
    			//var record = this.getRecord(target);
    			//alert(record.getData('id'));
    			//editmember(record.getData('id'));

				//});

	
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
var loadMemberSummaryTables = function() {		
        var filterTimeout1 = null;
         var filterTimeout2 = null;
          var filterTimeout3 = null;
           var filterTimeout4 = null;
        var myDataTable1 = instantiateMemberSummaryTable(YAHOO.members.Data.active,"dtcontainer1","activefilter",filterTimeout1);
        var myDataTable2 = instantiateMemberSummaryTable(YAHOO.members.Data.inactive,"dtcontainer2","inactivefilter",filterTimeout2);
        var myDataTable3 = instantiateMemberSummaryTable(YAHOO.members.Data.bounced,"dtcontainer3","bouncedfilter",filterTimeout3);
        var myDataTable4 = instantiateMemberSummaryTable(YAHOO.members.Data.unsubscribed,"dtcontainer4","unsubscribedfilter",filterTimeout4);
               
        tabView.getTab(0).addListener("click", function() {myDataTable1.onShow()});        
		tabView.getTab(1).addListener("click", function() {myDataTable2.onShow()});
		tabView.getTab(2).addListener("click", function() {myDataTable3.onShow()});
		tabView.getTab(3).addListener("click", function() {myDataTable4.onShow()});
    
}




