function setRoundCorners(){
if(!NiftyCheck())
    return;
Rounded("div#lastlistedeventpanel");

}
function drawPieCharts() {
    drawChart(lasteventchartdata, 'chart_latestevent');
    drawChart(recentclosedeventchartdata, 'chart_recentevent');
    
    }
      
var createYUIComponents=function(){
    var customizeStreamerlink = new YAHOO.widget.Button("customizeStreamerlink", { type:"link",href:"ManageStreamer" });
	var createEventGrpBtn = new YAHOO.widget.Button("createEventGrpBtn", { onclick: { fn: createEventGroup } });
	//var createEventBtn = new YAHOO.widget.Button("createEventBtn", { onclick: { fn: createEvent } });
	//var copyEventBtn = new YAHOO.widget.Button("copyEventBtn", { onclick: { fn: copyEvent } });
	var myeventdetailsthemeBtn = new YAHOO.widget.Button("myeventdetailsthemeBtn", { onclick: { fn: createTheme } });
	var myeventspageBtn = new YAHOO.widget.Button("myeventspageBtn", { onclick: { fn: customizeEventsPage } });
	var myeventspagecontentBtn = new YAHOO.widget.Button("myeventspagecontentBtn", { onclick: { fn: customizeEventsPageContent } });
	//var createTrackingPartnerBtn = new YAHOO.widget.Button("createTrackingPartnerBtn", { onclick: { fn: createTrackingPartner } });
}

var tabView;
var handleEGroupSuccess = function(o){
	tabView.getTab(1).set("content",o.responseText+"<div id='basic'></div>");
}
var handleEGroupFailure = function(o){
	alert("error"+o.responseText);
}
function createEventsSummaryTabs(arrayObj){
	tabView = new YAHOO.widget.TabView();
	
	tabView.addTab( new YAHOO.widget.Tab({
        label: arrayObj[0],
        content:"<div align='right'>Search By Event Name <input type='text' id='activefilter' value=''></div><div id='dtcontainer1'></div>",     
        active:true
    	}));
    	  	 
    tabView.addTab( new YAHOO.widget.Tab({
        label: arrayObj[1],
         content:"<div align='right'>Search By Event Name <input type='text' id='closedfilter' value=''></div><div id='dtcontainer2'></div>"
    	}));
    	  	
    tabView.addTab( new YAHOO.widget.Tab({
        label: arrayObj[2],
        content:"<div align='right'>Search By Event Name <input type='text' id='suspendedfilter' value=''></div><div id='dtcontainer3'></div>"
    	}));   
      
    tabView.appendTo('tabs_myevents');
    loadEventSummaryTables();
    
}
var loadEventGroupsTable=function(){
 var myEvtGrpDataTable = instantiateEventGroupSummaryTable(YAHOO.eventgroups.Data.groups,"evtgrpcontainer");
}


function instantiateEventGroupSummaryTable(ds, cname){

 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: eventfields
        }
        
    });
    
    var myColumnDefs = [
            {key:"title", sortable:true, label:"Name", width:120},
            {key:"count", label:"Events Count", sortable:true, width:65},
            {key:"action", sortable:false, label:"", width:40}
        ];
    var myDataTable = 
                new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource, {MSG_EMPTY:"&nbsp;"});
                myDataTable.subscribe("rowMouseoverEvent", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("rowMouseoutEvent", myDataTable.onEventUnhighlightRow);
                myDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			window.location.href="ManageGroup?gid="+record.getData('id');
});  
                return myDataTable;
}
var loadTrackingPartnerTable=function(){
	
	 var trackingPartnerTable = instantiateTrackingPartnerTable(YAHOO.trackingpartner.Data.TrackingPartnerDetails,"trackingpartnercontainer");
	}


	function instantiateTrackingPartnerTable(ds, cname){
           	 var myDataSource = new YAHOO.util.DataSource(ds,{
	        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
	        responseSchema : {
	            fields:["Name","TrackId","EventsCount"]
	        }
	   });
       var mypaginator = new YAHOO.widget.Paginator({
     	        rowsPerPage   : 5,
     	        pageLinks     : 3
     	    });
      var conf = {
     		        paginator : mypaginator,
     		        MSG_EMPTY:"&nbsp;"
     		        //,sortedBy: {key:'title', dir:YAHOO.widget.DataTable.CLASS_ASC}
     		    };
      var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
             	
                  elLiner.innerHTML = "Manage";                   
             };
             YAHOO.widget.DataTable.Formatter.mydisplay = myCustomFormatter;
           	  
	    var myColumnDefs = [
	            {key:"Name", sortable:true, label:"Name",sortable:true},	            
	             //{key:"EventsCount", sortable:false, label:"Events Count"},
	              {key:"manage", sortable:false, label:"",formatter:"mydisplay"}
	              ];
	        
	    var myDataTable = 
	                new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource, conf,{MSG_EMPTY:"&nbsp;"});
	                myDataTable.subscribe("rowMouseoverEvent", myDataTable.onEventHighlightRow); 
	               myDataTable.subscribe("rowMouseoutEvent", myDataTable.onEventUnhighlightRow);
	               myDataTable.on('cellClickEvent',function (oArgs) {
	           		var target = oArgs.target;
	       			var record = this.getRecord(target);
	       			
	       			window.location.href="ManageTrackingPartner?trackid="+record.getData('TrackId');
	            });  
	                return myDataTable;
	}
	
	
var loadEventsPageTable=function(){	
	 var myEvtsPageDataTable = instantiateEventsPageTable(YAHOO.eventspage.Data.eventsPageURL,"myeventspagecontainer");
}
function instantiateEventsPageTable(ds, cname){	
	 var myDataSource = new YAHOO.util.DataSource(ds,{
	        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
	        responseSchema : {
		 fields: ["eventsPageURL","action"]
	        }	        
	    });	 
	 var conf = {		       
		        MSG_EMPTY:"&nbsp;"		       
		    };
	 var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) { 
	    	var themeid=oRecord.getData('themeid');	    	
	        elLiner.innerHTML = '<a href="CustomizeEventsPageTheme" >Settings</a>';
	    };
	    YAHOO.widget.DataTable.Formatter.myActions = myCustomFormatter;
	  var myColumnDefs = [
	      	            {key:"eventsPageURL", sortable:true, label:"URL",sortable:true},
	      	          {key:"action", sortable:false, label:"", formatter:"myActions"}];
   
	    var myDataTable = 
	                new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource, conf);
	    myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
           myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);
	                myDataTable.on('cellClickEvent',function (oArgs) {
	        		var target = oArgs.target;
	    			var record = this.getRecord(target);
	    			//window.location.href="CustomizeEventsPageTheme";
	});  
	                return myDataTable;
	}
var loadThemesTable=function(){	
	 var myEvtThemesDataTable = instantiateEventThemesTable(YAHOO.eventthemes.Data.themes,"myeventdetailsthemecontainer");
	}


	function instantiateEventThemesTable(ds, cname){		
	 var myDataSource = new YAHOO.util.DataSource(ds,{
	        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
	        responseSchema : {
		 fields: ["themename","htmlcss","look&feel","themeid"]
	        }
	        
	    });
	 var mypaginator = new YAHOO.widget.Paginator({
	        rowsPerPage   : 5,
	        pageLinks     : 3
	    });
	 var conf = {
		        paginator : mypaginator,
		        MSG_EMPTY:"&nbsp;"
		        //,sortedBy: {key:'title', dir:YAHOO.widget.DataTable.CLASS_ASC}
		    };
	 var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) { 
		 var themeid=oRecord.getData('themeid');	
	        elLiner.innerHTML = '&nbsp;<a href="eventslist!themeTemplates?themeid='+themeid+'" >HTML & CSS</a>';
	    };
	    var myCustomFormatter1 = function(elLiner, oRecord, oColumn, oData) { 
	    	var themeid=oRecord.getData('themeid');	    	
	        elLiner.innerHTML = '&nbsp;<a href="eventslist!editUserTheme?action=edit&themeid='+themeid+'" >Look & Feel</a>';
	    };
	    YAHOO.widget.DataTable.Formatter.myHtmlActions = myCustomFormatter;
	    YAHOO.widget.DataTable.Formatter.myActions1 = myCustomFormatter1;
	    var myColumnDefs = [
	            {key:"themename", sortable:true, label:"Name",sortable:true},	            
	            {key:"htmlcss", sortable:false, label:"", formatter:"myHtmlActions"},
	            {key:"look&feel", sortable:false, label:"", formatter:"myActions1"}];
	            conf.sortedBy= {key:'themename', dir:YAHOO.widget.DataTable.CLASS_DESC};
	    var myDataTable = 
	                new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource, conf);
	                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
	                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);
	                myDataTable.on('cellClickEvent',function (oArgs) {
	        		var target = oArgs.target;
	    			var record = this.getRecord(target);
	    			
	});  
	                return myDataTable;
	}

function instantiateEventSummaryTable(ds, cname, filterElmName,filterTimeout, tabtype){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["title","startdate","enddate","listeddate","id","mgrid","action"]
        },
        doBeforeCallback : function (req,raw,res,cb) {
            // This is the filter function
            var data     = res.results || [],
                filtered = [],
                i,l;

            if (req) {
                req = req.toLowerCase();
                for (i = 0, l = data.length; i < l; ++i) {                	
                    if (data[i].title.toLowerCase().indexOf(req)>-1) {
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
        pageLinks     : 5
        //template: YAHOO.widget.Paginator.TEMPLATE_ROWS_PER_PAGE,
        //rowsPerPageOptions: [5,10,20,40,100]
    });
    var conf = {
        paginator : mypaginator,
        MSG_EMPTY:"&nbsp;",
        sortedBy: {key:'title', dir:YAHOO.widget.DataTable.CLASS_ASC}
    };
    
    var myCustomDateFormatter = function(elLiner, oRecord, oColumn, oData) {
    var d=oData;
    elLiner.innerHTML =d.substring(5,7)+'/'+d.substring(8)+'/'+d.substring(0,4);   
    
            };
    YAHOO.widget.DataTable.Formatter.myDateFormat = myCustomDateFormatter;
    var myColumnDefs = [{key:"title", sortable:true, label:"Name"}];
    if(tabtype=='A'){
    myColumnDefs.push({key:"startdate", label:"Start Date", formatter:"myDateFormat", sortable:true, sortOptions:{defaultDir:YAHOO.widget.DataTable.CLASS_DESC}});
    myColumnDefs.push({key:"listeddate", label:"List Date", formatter:"myDateFormat", sortable:true, sortOptions:{defaultDir:YAHOO.widget.DataTable.CLASS_DESC}});
    conf.sortedBy= {key:'listeddate', dir:YAHOO.widget.DataTable.CLASS_DESC};
    }
    if(tabtype=='C'){
    	 myColumnDefs.push({key:"enddate", label:"End Date", formatter:"myDateFormat", sortable:true, sortOptions:{defaultDir:YAHOO.widget.DataTable.CLASS_DESC}});
    	 myColumnDefs.push({key:"listeddate", label:"List Date", formatter:"myDateFormat", sortable:true, sortOptions:{defaultDir:YAHOO.widget.DataTable.CLASS_DESC}});
    	 conf.sortedBy= {key:'enddate', dir:YAHOO.widget.DataTable.CLASS_DESC};
        }
    if(tabtype=='S'){
        myColumnDefs.push({key:"startdate", label:"Start Date", formatter:"myDateFormat", sortable:true, sortOptions:{defaultDir:YAHOO.widget.DataTable.CLASS_DESC}});
        myColumnDefs.push({key:"listeddate", label:"List Date", formatter:"myDateFormat", sortable:true, sortOptions:{defaultDir:YAHOO.widget.DataTable.CLASS_DESC}});
        conf.sortedBy= {key:'listeddate', dir:YAHOO.widget.DataTable.CLASS_DESC};
        }
    myColumnDefs.push( {key:"action", sortable:false, label:""});
 
    var myDataTable = 
                new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,conf);
                myDataTable.subscribe("rowMouseoverEvent", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("rowMouseoutEvent", myDataTable.onEventUnhighlightRow);
                myDataTable.on('cellClickEvent',function (oArgs) {
        		var target = oArgs.target;
    			var record = this.getRecord(target);
    			eventid=record.getData('id');
    			_purpose='myeventspage';
    			var url='../eventmanage/SpecialFee!getMgrCCStatus?mgrid='+record.getData('mgrid')+'&eid='+eventid;
    			new Ajax.Request(url, {
	    	       method: 'post',
	    	       onSuccess: function(t){
	    	       if(!isValidActionMessage(t.responseText)) return;
			       var data=t.responseText;
			       var ccjson=eval('('+data+')');
		           var cardstatus=ccjson.cardstatus;
		           var userid=ccjson.userid;
		           if(cardstatus=='popup1'){
						getDuePopup(record.getData('mgrid'),eventid);
		           }else if(cardstatus=='charge'){
		           		getChargePopup(record.getData('mgrid'),eventid)
		           }else if(cardstatus=='popup2' || cardstatus=='authorize'){
		           		getcc(userid,'myeventspage_'+cardstatus);
		           }else{
		           		window.location.href="../eventmanage/Snapshot?eid="+eventid;
		           }
	                  
		          },onFailure:function(){alert("Failure");}
	    	    });   
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
var loadEventSummaryTables = function() {	
        var filterTimeout1 = null;
         var filterTimeout2 = null;
          var filterTimeout3 = null;
        var myDataTable1 = instantiateEventSummaryTable(YAHOO.events.Data.active,"dtcontainer1","activefilter",filterTimeout1,'A');
        var myDataTable2 = instantiateEventSummaryTable(YAHOO.events.Data.closed,"dtcontainer2","closedfilter",filterTimeout2,'C');
        var myDataTable3 = instantiateEventSummaryTable(YAHOO.events.Data.suspended,"dtcontainer3","suspendedfilter",filterTimeout3,'S');
               
        tabView.getTab(0).addListener("click", function() {myDataTable1.onShow()});        
		tabView.getTab(1).addListener("click", function() {myDataTable2.onShow()});
		tabView.getTab(2).addListener("click", function() {myDataTable3.onShow()});
    
}

function getDuePopup(mgrid,eid){
var url='../eventmanage/SpecialFee!getDuePopup?eid='+eid+'&mgrid='+mgrid;
  loadURLBasedWindow(url,duepopupscreen);
}

function getChargePopup(mgrid,eid){
var url='../eventmanage/SpecialFee!getChargePopup?eid='+eid+'&mgrid='+mgrid;
loadURLBasedWindow(url,chargepopupscreen);
}


var duepopupscreen = function (responseObj){
	if(!isValidActionMessage(responseObj.responseText)) return;
	var myButtons = [
	    				{ text: "Proceed", handler: proceedDueForm }
	             	];
	YAHOO.ebee.popup.dialog.setHeader("Update your account");
    YAHOO.ebee.popup.dialog.setBody(responseObj.responseText);
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", myButtons);
	YAHOO.ebee.popup.dialog.hideEvent.subscribe(function(){		
		var eid=document.getElementById("eventid").value;
		window.location.href="../eventmanage/Snapshot?eid="+eid;
	});
	adjustYUIpopup();
}

var chargepopupscreen = function (responseObj){
		if(!isValidActionMessage(responseObj.responseText)) return;
	var myButtons = [
	    				{ text: "Proceed", handler: proceedDueForm }
	             	];
	YAHOO.ebee.popup.dialog.setHeader("Update your account");
    YAHOO.ebee.popup.dialog.setBody(responseObj.responseText);
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", myButtons);
	YAHOO.ebee.popup.dialog.hideEvent.subscribe(function(){		
		window.location.href="../myaccount/home";
	});
	adjustYUIpopup();
}

var proceedDueForm = function(){
	window.location.href="../myaccount/home";
	
}




