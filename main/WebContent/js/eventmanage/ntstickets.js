function editTCommission(tid,eid,nts_commission){	
	var url='NetworkSelling!editCommission?eid='+eid+'&tid='+tid+'&nts_commission='+nts_commission;
	loadURLBasedWindow(url, displayTCommissionScreen);	
}

var displayTCommissionScreen = function(responseObj) {	
	showPopUpDialogWindow(responseObj, "Commission", submitTCommissionForm, handleCancel);
}

var submitTCommissionForm = function(){
	submitFormAndReload('tCommissionForm','commissionerrormessages');
}

function instantiateNTSTicketsTable(ds, cname,eid, currencySymbol){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["isLast","position","ttype","isFirst","type","fee","price",
            "startdate","isdonation","name","gid","tid","enddate","commission","credits"]            
        }
        
    });
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    var cid=oColumn.getIndex();
    var qid = oRecord.getData("attrib_id");
   if(cid==0){
    var namepart='';
    if(oRecord.getData("type")=='grpticket')
    	namepart = "&nbsp;&nbsp;"+oData;
    else
        namepart = oData;
        
    if(oRecord.getData("type")!='group')
       if(oRecord.getData("type")=='grpticket')
    	namepart += "<br/>&nbsp;&nbsp;<span class='smallestfont'>Available: "+oRecord.getData("startdate")+" - "+ oRecord.getData("enddate")+"</span>";
    	else
    	namepart += "<br/><span class='smallestfont'>Available: "+oRecord.getData("startdate")+" - "+ oRecord.getData("enddate")+"</span>";
    	
    elLiner.innerHTML = namepart;
    }else if(cid==1){
    var namepart='';
    
    if(oRecord.getData("type")!='group' && oRecord.getData("isdonation")!='Y')
    	namepart += "<s:text name='ticketData.currency'></s:text>"+oRecord.getData("price")+"<br/><span class='smallestfont'>Fee: <s:text name='ticketData.currency'></s:text>"+oRecord.getData("fee")+"</span>";
    elLiner.innerHTML = namepart;
    }else if(cid==4){
    if(oRecord.getData("type")=='group')
    	elLiner.innerHTML = '';
    else
        elLiner.innerHTML = '<a href="#" onclick=editTCommission('+oRecord.getData("tid")+','+eid+','+nts_commission+');>Edit</a>'
    }
                  
    };
    YAHOO.widget.DataTable.Formatter.mydisplay = myCustomFormatter;
    var myColumnDefs = [
            {key:"name", label:"Name",formatter:"mydisplay"},
            {key:"price", label:"Price ("+currencySymbol+")",formatter:"mydisplay"} ,
            {key:"commission", label:"Commission (%)"},
            {key:"credits", label:"Bee Credits (B$)"+'&nbsp;<span class="helpboxlink" id="bcredits"><img src="../images/questionMark.gif" title="Bee Credits are like cash, attendee can use them to purchase tickets on Eventbee, or redeem for gift certificates etc."/></span>'},
            {key:"action", label:"",formatter:"mydisplay"}
        ];
    var oConfigs = {
        			MSG_EMPTY:"&nbsp;", 
	                paginator: new YAHOO.widget.Paginator({ 
	                    rowsPerPage: 5 
	                }) 
       }; 
    var myDataTable =   new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,oConfigs);
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);   
    return myDataTable;
}

function updateNTSStatus(event,jsonObj){
var jsonData = eval(jsonObj);
var eid = jsonData.eid;
var status = jsonData.status;
if(status=='N'){
	status='Y';
}else if(status=='Y'){
	status='N';
}
var url="NetworkSelling!updateNTSStatus?eid="+eid+"&status="+status;
var dynatimestamp=new Date();
url+='&dynatimestamp='+dynatimestamp.getTime();
document.getElementById('commissionerrormsg').style.display="none";
YAHOO.ebee.popup.wait.show();
new Ajax.Request(url, {
		  method: 'get',
		  onSuccess: function(transport) {
			var result=transport.responseText;
			if(!isValidActionMessage(result)) return;
			if(result.indexOf("success")>-1){
        	window.location.reload(true);
        }else{}
		}
	});	
}


function updateCommission(){
var chk = document.getElementById('chksamecom').checked;
if(chk){
YAHOO.ebee.popup.wait.show();
$("ntsform").request({
		onFailure: function() {alert("error");},
        onSuccess: function(t) {
        var result=t.responseText;
        if(!isValidActionMessage(result)) return;
        if(result.indexOf("success")>-1){
        	window.location.reload(true);
        }else {
        		YAHOO.ebee.popup.wait.hide();
        		document.getElementById('statusMessage').style.display="none";
        		document.getElementById('commissionerrormsg').style.display="block";
            	$('commissionerrormsg').update(result);
	        }
        }
	});
}else{
	alert("Please Check Set same commisson on all ticket types");
}
}