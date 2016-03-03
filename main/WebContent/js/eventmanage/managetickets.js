var j=0;
function submitAddDonation(eid){
	var url='TicketDetails?eid='+eid+'&ticketType=Donation';
	loadURLBasedWindow(url, displayDonationScreen);	
}

function getEventCapacity(eid){
var url='TicketDetails!getEventCapacity?eid='+eid;
loadURLBasedWindow(url, eventCapacityScreen);	
}

var eventCapacityScreen = function (responseObj){
showPopUpDialogWindow(responseObj, "Event Capacity", submitEventCapacityForm, handleCancel);
}
var submitEventCapacityForm = function(){
	submitForm('EventCapacityForm', 'capacityerrormessages',++j);	
}




function editDonation(eid,ticketId){
	var url='TicketDetails!edit?eid='+eid+'&ticketId='+ticketId;
	loadURLBasedWindow(url, displayDonationScreen);	
}
var displayDonationScreen = function (responseObj){
	showPopUpDialogWindow(responseObj, "Donation Details", submitDonationForm, handleCancel);
}
var submitDonationForm = function(){
	submitForm('DonationAddForm', 'donationaddtformerrormessages',++j);	
}


function submitAddGroup(eid){
	var url='groupadd.jsp?eid='+eid;
	loadURLBasedWindow(url, displayTicketGroupScreen);	
}
var displayTicketGroupScreen = function(responseObj){
	showPopUpDialogWindow(responseObj, "Group Details", submitGroupAddForm, handleCancel);	
}
var submitGroupAddForm = function(){
YAHOO.ebee.popup.wait.show();
	var groupName=document.getElementById("groupName").value;
	if(trim(groupName)==""){
	YAHOO.ebee.popup.wait.hide();
		document.getElementById("groupaddtformerrormessages").style.display="block";
		document.getElementById("groupaddtformerrormessages").innerHTML='Please Enter Group Name';
		return false;
	}
	submitForm('GroupAddForm', 'groupaddtformerrormessages',++j);
	return true;
}
function showtickets(eid,isrecurring){
	if(isrecurring=="true"){
		recurringshowtickets(eid);
	}
	else{
	var url='TicketShowHide?eid='+eid;
	loadURLBasedWindow(url, displayTicketsShowHideSuccess);
	}	
}
var displayTicketsShowHideSuccess = function(responseObj){
	showPopUpDialogWindow(responseObj, "Tickets", submitTicketsShowHideForm, handleCancel);	
}
var submitTicketsShowHideForm=function(){
YAHOO.ebee.popup.wait.show();
submitFormAndReload('ticketshowhideform', '');		
}
function checkAll(field){
if(field.length==undefined)
	field.checked=true;
	else{
for (i = 0; i < field.length; i++)
	field[i].checked = true ;
	}
}
function uncheckAll(field){
if(field.length==undefined)
	field.checked=false;
	else{
for (i = 0; i < field.length; i++)
	field[i].checked = false ;
	}
}
function deleteTicket(func,event,jsonObj){
	var eid = jsonObj.eid;
	var ticketId = jsonObj.ticketid;
	var agree;
	agree=confirm("Ticket will be deleted from the list for ever. This operation cannot be revert back.");
	if (agree){	
	    YAHOO.ebee.popup.wait.show();
		var url='TicketDetails!deleteTicket?eid='+eid+'&ticketId='+ticketId;		
		callURLandReload(url);			
	}
}
function deleteGroup(func,event,jsonObj){
	var groupId = jsonObj.groupid;
	var eid=jsonObj.eid;
	var agree;
	agree=confirm("Group will be deleted from the list along with the tickets in the group.");	
	if (agree){	
	    YAHOO.ebee.popup.wait.show();
		var url='TicketDetails!deleteGroup?eid='+eid+'&groupId='+groupId;		
		callURLandReload(url);		
	    }
}
function moveGroupDown(groupId,eid)
{
	YAHOO.ebee.popup.wait.show();
	var groupsArray=groupPositions;
	var t1="";
	var p1="";
	var t2="";
	var p2="";
	for(i=0;i<groupsArray.length;i++){
		tktid=groupsArray[i][0];
		if(tktid==groupId){
		if(i==groupsArray.length-1) { return}
		t1=tktid;
		p1=groupsArray[i+1][1];
		t2=groupsArray[i+1][0];
		p2=groupsArray[i][1];
		}
	}
	var url='TicketDetails!swapGroup?eid='+eid+'&groupId='+groupId+'&t1='+t1+'&p1='+p1+'&t2='+t2+'&p2='+p2;
	callURLandReload(url);
}
function moveGroupUp(groupId,eid)
{
	YAHOO.ebee.popup.wait.show();
	var groupsArray=groupPositions;
	var t1="";
	var p1="";
	var t2="";
	var p2="";
	for(i=0;i<groupsArray.length;i++){
		tktid=groupsArray[i][0];
		if(tktid==groupId){
		if(i==0) {return false;}
		t1=tktid;
		p1=groupsArray[i-1][1];
		t2=groupsArray[i-1][0];
		p2=groupsArray[i][1];
		}
	}
	var url='TicketDetails!swapGroup?eid='+eid+'&groupId='+groupId+'&t1='+t1+'&p1='+p1+'&t2='+t2+'&p2='+p2;
	callURLandReload(url);
}
function moveTicketUp(reqtktid,groupId,eid){
	var ticketsarray=groupIds[groupId];
	var t1="";
	var p1="";
	var t2="";
	var p2="";
	for(i=0;i<ticketsarray.length;i++){
	tktid=ticketsarray[i][0];
	if(tktid==reqtktid){
	if(i==0) {return}
	t1=tktid;
	p1=ticketsarray[i-1][1];
	t2=ticketsarray[i-1][0];
	p2=ticketsarray[i][1];
	}
	}
	var url='TicketDetails!swapTicket?eid='+eid+'&ticketId='+reqtktid+'&groupId='+groupId+'&t1='+t1+'&p1='+p1+'&t2='+t2+'&p2='+p2;
	callURLandReload(url);
}
function moveTicketDown(reqtktid,groupId,eid){
	var ticketsarray=groupIds[groupId];
	var t1="";
	var p1="";
	var t2="";
	var p2="";
	for(i=0;i<ticketsarray.length;i++){
	tktid=ticketsarray[i][0];
	if(tktid==reqtktid){
		if(i==ticketsarray.length-1) { return; }	
	t1=tktid;
	p1=ticketsarray[i+1][1];
	t2=ticketsarray[i+1][0];
	p2=ticketsarray[i][1];
	}
	}
	var url='TicketDetails!swapTicket?eid='+eid+'&ticketId='+reqtktid+'&groupId='+groupId+'&t1='+t1+'&p1='+p1+'&t2='+t2+'&p2='+p2;
	callURLandReload(url);
}
function editTicket(func,event,jsonObj){	
	var eid = jsonObj.eid;
	var ticketId = jsonObj.ticketid;
	if(jsonObj.ttype=='Donation'){
		editDonation(eid,ticketId);
		return;
	}	
	var url='TicketDetails!edit?eid='+eid+'&ticketId='+ticketId+'&purpose=edittkt';
	loadURLBasedWindow(url, displayTicketScreen);	
}
function editGroup(funcevent,event,jsonObj){
	var groupId=jsonObj.groupid;
	var eid=jsonObj.eid;
	var url='GroupDetails!edit?eid='+eid+'&groupId='+groupId;
	loadURLBasedWindow(url, displayTicketGroupScreen);
}
function createticketingYUIButtons(eid){
	var addTicketBtn = new YAHOO.widget.Button("addTicket", { onclick: { fn: addTicket,obj:{"eid":eid} } });
}
function addTicket(event,jsonObj){
	var eid = jsonObj.eid;
	var url='TicketDetails?eid='+eid;
	loadURLBasedWindow(url, displayTicketScreen);	
}

function selectTicket(){
   var eid=document.getElementById('eid').value;
   var oldtktid=document.getElementById('oldticket').value;
   var url='';
   if(oldtktid=='1' || oldtktid=='')
     url='TicketDetails?eid='+eid;
   else
     url='TicketDetails!selectTktDetails?eid='+eid+'&ticketId='+oldtktid+'&purpose=selecttkt';  
   loadURLBasedWindow(url, displayTicketScreen);
}

var displayTicketScreen = function(responseObj) {	
	showPopUpDialogWindow(responseObj, "Ticket Details", submitTicketingForm, handleCancel);
	document.getElementById('popupdialog_c').style.top='100px';
	window.scrollTo(0,0);
	$j("#showhide").click(function(){
    $j("#moreblock").slideToggle(
    function(){
    	var a=document.getElementById(this.id).style.display;
        if(a=="none")
        $j("#showhide").attr("src","../images/dn.gif")
        else
        $j("#showhide").attr("src","../images/up.gif")
    }
    );    
	});
}

var submitTicketingForm = function(){
if(document.getElementById('ticketprice').value==0)
	document.getElementById('processingfee').value=0;
	submitForm('ticketingForm', 'ticketformerrormessages',++j);	
}

function submitForm(formId, errMsgDivid,j){
	if(j==1)
	submitFormAndHandle(formId, errMsgDivid, true);
}

function submitFormAndHandle(formId, errMsgDivid, reloadYN){
YAHOO.ebee.popup.wait.show();
$(formId).request({
        onFailure: function() { 
        YAHOO.ebee.popup.wait.hide();
        $(errMsgDivid).update('Failed to get results');
        },
        onSuccess: function(t) {
	         var result=t.responseText;
	        if(!isValidActionMessage(result)) return;
	       if(result.indexOf("success")>-1){
	        	YAHOO.ebee.popup.wait.hide();
	        	YAHOO.ebee.popup.dialog.cancel();
	        	if(reloadYN)
	        		window.location.reload( true );	
	        } 
	         else {
	         	YAHOO.ebee.popup.wait.hide();
            	$(errMsgDivid).update(result);
            	j=0;
	        }
        }
    });
}



function callTicketTypeHelp(){
	//y=YAHOO.ebee.popup.dialog.cfg.getProperty("y")+80;
	var top=document.getElementById('popupdialog_c').style.top;
	y=Number(top.replace("px",""))+240;
	x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	showPopupInPopup(tickets_tickettypes_helptitle, getHelpContent('tickets_tickettypes_helpcontent'),200,500,x,y);
}
function callTicketGroupHelp(){
	var top=document.getElementById('popupdialog_c').style.top;
	y=Number(top.replace("px",""))+50;
	//y=YAHOO.ebee.popup.dialog.cfg.getProperty("y")+10;
	x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	showPopupInPopup(tickets_ticketgroup_helptitle, getHelpContent('tickets_ticketgroup_helpcontent'),200,300,x,y);
}
function callTicketFeeHelp(){
	//y=YAHOO.ebee.popup.dialog.cfg.getProperty("y")+180;
	var top=document.getElementById('popupdialog_c').style.top;
	y=Number(top.replace("px",""))+200;
	x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	showPopupInPopup(tickets_ticketfee_helptitle, getHelpContent('tickets_ticketfee_helpcontent'),200,400,x,y);
}
function instantiateTicketsTable(ds, cname,eid, currencySymbol,isrecurring){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["isLast","position","ttype","isFirst","type","fee","price",
            "startdate","isdonation","name","gid","tid","enddate","soldqty"]            
        }
        
    });
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    var cid=oColumn.getIndex();
    var qid = oRecord.getData("attrib_id");
    if(cid==0){
    var UD='';
    if(oRecord.getData("type")=='group' && oRecord.getData("isFirst")=='N'){
    UD='<img src="../images/up.gif" onClick="javascript:moveGroupUp('+oRecord.getData("gid")+','+eid+');"/>';
    }else if(oRecord.getData("type")=='group' && oRecord.getData("isFirst")=='Y'){
    UD='<img src="../images/spacer.gif" width="16" height="16" align="left"/>';
    }else if(oRecord.getData("type")=='looseticket' && oRecord.getData("isFirst")=='Y'){
    UD='<img src="../images/spacer.gif" width="16" height="16" align="left"/>';
    }else if(oRecord.getData("type")=='looseticket' && oRecord.getData("isFirst")=='N'){
    UD='<img src="../images/up.gif" onClick="javascript:moveGroupUp('+oRecord.getData("gid")+','+eid+');"/>';
    }else{
    UD=' ';
    }
    if(oRecord.getData("type")=='group' && oRecord.getData("isLast")=='N'){
    UD+='<img src="../images/dn.gif" onClick="javascript:moveGroupDown('+oRecord.getData("gid")+','+eid+');"/>';
    }else if(oRecord.getData("type")=='group' && oRecord.getData("isLast")=='Y'){
    UD+='<img src="../images/spacer.gif" width="16" height="16" align="left"/>';
    }else if(oRecord.getData("type")=='looseticket' && oRecord.getData("isLast")=='Y'){
    UD+='<img src="../images/spacer.gif" width="16" height="16" align="left"/>';
    }else if(oRecord.getData("type")=='looseticket' && oRecord.getData("isLast")=='N'){
    UD+='<img src="../images/dn.gif" onClick="javascript:moveGroupDown('+oRecord.getData("gid")+','+eid+');"/>';
    }else{
    UD+=' ';
    }
    elLiner.innerHTML = UD;
    }else if(cid==1){
    var namepart='';
    if(oRecord.getData("type")=='grpticket')
    	namepart = "&nbsp;&nbsp;"+oData;
    else
        namepart = oData;
    if(oRecord.getData("type")!='group' && oRecord.getData("isdonation")!='Y'){
    	if(isrecurring=="false"){
       if(oRecord.getData("type")=='grpticket')
    	namepart += "<br/>&nbsp;&nbsp;<span class='smallestfont'>Available: "+oRecord.getData("startdate")+" - "+ oRecord.getData("enddate")+"</span>";
    	else
    	namepart += "<br/><span class='smallestfont'>Available: "+oRecord.getData("startdate")+" - "+ oRecord.getData("enddate")+"</span>";
    	}}
    elLiner.innerHTML = namepart;
    }else if(cid==2){
    var namepart='';
    if(oRecord.getData("type")!='group' && oRecord.getData("isdonation")!='Y')
    	namepart += "<s:text name='ticketData.currency'></s:text>"+oRecord.getData("price")+"<br/><span class='smallestfont'>Fee: <s:text name='ticketData.currency'></s:text>"+oRecord.getData("fee")+"</span>";
    elLiner.innerHTML = namepart;
    }else if(cid==3){
       var namepart='';
       if(oRecord.getData("type")!='group')
       namepart += "<s:text name='ticketData.soldqty'></s:text>"+oRecord.getData("soldqty");
    elLiner.innerHTML = namepart; 
    }else if(cid==4){
    if(oRecord.getData("type")=='group')
    	elLiner.innerHTML = "<div id='manageGrp"+ oRecord.getData("gid") +"'></div>";
    else
        elLiner.innerHTML = "<div id='manageTkt"+ oRecord.getData("tid") +"'></div>";
    }else if(cid==5){
   var UD='';
   var UD=( oRecord.getData("isFirst")=='N' )?'<img src="../images/up.gif" onClick="javascript:moveTicketUp('+oRecord.getData("tid")+','+oRecord.getData("gid")+','+eid+');"/>':'<img src="../images/spacer.gif" width="16" height="16" align="left"/>';
    UD+=( oRecord.getData("isLast")=='N' )?'<img src="../images/dn.gif" onClick="javascript:moveTicketDown('+oRecord.getData("tid")+','+oRecord.getData("gid")+','+eid+');"/>':'<img src="../images/spacer.gif" width="16" height="16" align="left"/>';
    UD=( oRecord.getData("type")=='grpticket')?UD:'';
    	elLiner.innerHTML = UD;
    }
                  
    };
    YAHOO.widget.DataTable.Formatter.mydisplay = myCustomFormatter;
    var myColumnDefs = [
            {key:"grpmv", label:"",formatter:"mydisplay"},
            {key:"name", label:"Name",formatter:"mydisplay"},
            {key:"price", label:"Price ("+currencySymbol+")",formatter:"mydisplay"} ,
            {key:"soldqty", label:"Total Sold",formatter:"mydisplay"} ,
            //{key:"ttype", label:"Type"},
            {key:"action", label:"",formatter:"mydisplay"},
            {key:"tktmv", label:"",formatter:"mydisplay"}          
        ];
    var myDataTable =   new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,{MSG_EMPTY:"&nbsp;"});
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);   
    return myDataTable;
}
function loadControlsUsingTicketsData(eid){
for(var i=0;i<YAHOO.ebee.event.data.TicketDetails.length;i++){
	var jsonobj = YAHOO.ebee.event.data.TicketDetails[i];
	var ticketIds;
	if(jsonobj.type=='looseticket'){
		ticketIds=new Array();
		ticketIds.push([jsonobj.tid,"1"]);
		groupIds[jsonobj.gid]=ticketIds;
		groupPositions.push([jsonobj.gid,jsonobj.grpPos]);	   	
	}
	else if(jsonobj.type=='group'){
	   ticketIds=new Array();
	   groupPositions.push([jsonobj.gid,jsonobj.grpPos]);
	}
	else if(jsonobj.type=='grpticket'){
	   ticketIds.push([jsonobj.tid,jsonobj.position]);
	   groupIds[jsonobj.gid]=ticketIds;
	}
	if(jsonobj.type=='group'){
	var divid = "manageGrp"+jsonobj.gid;
	var Grp_ManageMenu = [			
			{ text: "Edit", value: 1, onclick: { fn: editGroup ,obj: {"eid":eid,"groupid":jsonobj.gid } } },
			{ text: "Delete", value: 2, onclick: { fn: deleteGroup ,obj: {"eid":eid,"groupid":jsonobj.gid} } }
		
		];
		
		var splitBtn = new YAHOO.widget.Button({ type: "menu",  label: "Manage", name: "Manage", menu: Grp_ManageMenu, container: divid });
	}else{
	var divid = "manageTkt"+jsonobj.tid;
	var tkt_ManageMenu = [			
			{ text: "Edit", value: 1, onclick: { fn: editTicket ,obj: {"eid":eid,"ticketid":jsonobj.tid,"ttype":jsonobj.ttype} } },
			{ text: "Delete", value: 2, onclick: { fn: deleteTicket ,obj: {"eid":eid,"ticketid":jsonobj.tid} } }
		];
		var splitBtn = new YAHOO.widget.Button({ type: "menu",  label: "Manage", name: "Manage", menu: tkt_ManageMenu, container: divid });
	}
}
}
function checkRecurring(){
	if(document.getElementById("recurringdate")){
		if(document.getElementById("recurringdate").selectedIndex==0){
		document.getElementById("heading").innerHTML="";
		document.getElementById("tickets").innerHTML="";	
		}
	}
}
function getRecurringDateTickets(){
	if(document.getElementById("recurringdate")){
		var eventid=document.getElementById('eid').value;
		recurringshowtickets(eventid);
	}
}
function recurringshowtickets(eid){
	var url='TicketShowHide?eid='+eid;
	if(document.getElementById("recurringdate"))
		url=url+"&eventdate="+document.getElementById("recurringdate").value;
	loadURLBasedWindow(url, showticketsscreen);
}
var showticketsscreen=function(responseObj){
	YAHOO.ebee.popup.wait.hide();
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons","");
	if(!isValidActionMessage(responseObj.responseText)){
       	return;
    }
    YAHOO.ebee.popup.dialog.setHeader("Tickets");
    YAHOO.ebee.popup.dialog.setBody(responseObj.responseText);
	adjustYUIpopup();
	checkRecurring();
}
function updaterecurrshowhide(checkbox){
	var eid=document.getElementById("eid").value;
	var edate=document.getElementById("recurringdate").value;
	var ticketid=checkbox.value;
	var url='TicketShowHide!updateRecurring?eid='+eid+"&eventdate="+edate;
	if(checkbox.checked)
		url=url+"&ticketid="+ticketid+"&show=Y";
	else
		url=url+"&ticketid="+ticketid+"&show=N";
	YAHOO.ebee.popup.wait.show();
	new Ajax.Request(url, {
		  method: 'get',
		  onSuccess: function(transport) {
			var result=transport.responseText;
			YAHOO.ebee.popup.wait.hide();						
		}
	});
}

function setTicketMaxQty(){
var ticketid=document.getElementById('ticketid').value;
if(ticketid==''){
		var ticketavail=document.getElementById('tktavailid').value;
		ticketavail=trim(ticketavail);
		var ticketmax=document.getElementById('tktmaxid').value;
		if(isNaN(ticketavail)){
			return false;
		}else{
			if(ticketavail!='' && ticketavail <= 5){
				document.getElementById('tktmaxid').value=ticketavail;
			}
			if(ticketavail!='' && ticketavail >5){
				document.getElementById('tktmaxid').value=5;
			}
		}
	}
}