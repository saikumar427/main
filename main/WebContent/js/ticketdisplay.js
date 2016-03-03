var loadDisplayFormatTable=function(){	
	 var myDisplayFormatTable = instantiateDisplayFormatTable(YAHOO.ticketdisplayformat.Data.formats,"displayFormatTable");
}
function instantiateDisplayFormatTable(ds, cname){
	var myDataSource = new YAHOO.util.DataSource(ds,{
	        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
	        responseSchema : {
		 	fields: ["fm","fmId","action"]
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
    	var temp=oRecord.getData("fmId");
    	var temp1='&nbsp;<a href="#" onclick=creatDisplay('+temp+','+_eid+'); >';
    	var temp2='&nbsp;<a href="#" onclick=deleteDisplayFormat("delete",'+temp+','+_eid+'); >';
        elLiner.innerHTML = temp1+'Edit</a>'+' &nbsp;|&nbsp;'+temp2+
        						'Delete</a>';
    };
    YAHOO.widget.DataTable.Formatter.myActions = myCustomFormatter;
	    
	var myColumnDefs = [
	            {key:"fm", sortable:true, label:"Display Format"},
	            {key:"action", sortable:false, label:"Manage", formatter:"myActions"}];
	            
	var myDataTable = new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource, conf);
					  myDataTable.subscribe("rowMouseoverEvent", myDataTable.onEventHighlightRow); 
	               	  myDataTable.subscribe("rowMouseoutEvent", myDataTable.onEventUnhighlightRow);
	                
	return myDataTable;
}

function selectAll(){
for(var i=0;i<document.disForm.elements.length;i++)
    if(document.disForm.elements[i].name == 'formatTickets')
    	document.disForm.elements[i].checked=true;
}

function clearAll(){
for(var i=0;i<document.disForm.elements.length;i++)
    if(document.disForm.elements[i].name == 'formatTickets')
    	document.disForm.elements[i].checked=false;
}

function showDiv(id){
	if(document.getElementById(id).style.display=='none') {
		document.getElementById(id).style.display='block';
	}
}
function hideDiv(id){
	if(document.getElementById(id).style.display=='block') {
		document.getElementById(id).style.display='none';
	}
}

function creatDisplay(fmtId,eid){
	var url='DisplayTickets?eid='+eid+'&dispFrmt=Display'+'&formatId='+fmtId;
	loadURLBasedWindow(url, displayFormatScreen);	
}
var displayFormatScreen = function (responseObj){
	showPopUpDialogWindow(responseObj, "Tickets Availability Display Format",submitDisplayForm, handleCancel);
}
var submitDisplayForm = function(){
	submitFormAndReload('disForm','displayFormerrormessages');
}

function deleteDisplayFormat(purpose,fmtId,eid){
	var agree;
	var dynatimestamp = new Date();
	var url="DisplayTickets!deleteDisplayFormat?eid="+eid+'&formatId='+fmtId; 
	url += '&dynatimestamp='+dynatimestamp.getTime();
	if(purpose=='delete'){
		agree=confirm("Your Display Format will be deleted");		
	}
	if (agree){
		new Ajax.Request(url, {
			  method: 'get',
			  onSuccess: function(transport) {
				var result=transport.responseText;
				if(purpose=='delete'){
					window.location.href="../eventmanage/DisplayTickets?eid="+eid;
				}
		      }
		});	
	}
	else
		return false;
}

