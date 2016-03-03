//var _eventid=''; var _purpose=''; var windowtype='';
function addVolumeTicket(event,jsonObj){
	var eid = jsonObj.eid;
	var vbenabled=jsonObj.vbenabled;
	if(vbenabled=='Y'){
		var url='VolumeTicketDetails!addVolumeTicket?eid='+eid;
		loadURLBasedWindow(url, displayVolTicketScreen);
	}else{
		var url='VolumeTicketDetails!VolumeTicketingEnable?eid='+eid;
		loadURLBasedWindow(url, displayVolTicketEnable);	
	}
			
}
function editVolumeTicket(func,event,jsonObj){	
	var eid = jsonObj.eid;
	var ticketId = jsonObj.ticketid;
	var url='VolumeTicketDetails!editVolumeTicket?eid='+eid+'&ticketId='+ticketId;
	loadURLBasedWindow(url, displayVolTicketScreen);	
}

var displayVolTicketScreen = function(responseObj) {	
	showPopUpDialogWindow(responseObj, "Volume Ticket Details", submitVolTicketingForm, handleCancel);
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
var submitVolTicketingForm = function(){
submitForm('volTicketingForm', 'ticketformerrormessages',++j);
	//submitFormAndReload('volTicketingForm', 'ticketformerrormessages');	
}

var displayVolTicketEnable=function(responseObj){
	showPopUpDialogWithCustomButtons(responseObj, "Volume Ticket Selling", "");
}
function instantiateVolumeTicketsTable(ds, cname,eid, currencySymbol){
 var myDataSource = new YAHOO.util.DataSource(ds,{
        responseType : YAHOO.util.DataSource.TYPE_JSARRAY,
        responseSchema : {
            fields: ["isLast","isFirst","volprice","price","trigQty","fee","name","tid","status","appstatus","simulate","showhide"]            
        }
    });
    
    
    var myCustomFormatter = function(elLiner, oRecord, oColumn, oData) {
    var cid=oColumn.getIndex();
    /*if(cid==0){
    	var namepart='';
    	if(oRecord.getData("showhide")=='Y')
    		namepart += "<img src='../images/check.png'>";
    	else
    		namepart += "<img src='../images/close.png' width='12' height='12'>";
    	elLiner.innerHTML = namepart;
    }else*/ if(cid==0){
    	var namepart='';
    	namepart += "<br/><span class='smallestfont'>Available: "+oRecord.getData("startdate")+" - "+ oRecord.getData("enddate")+"</span>";
    	elLiner.innerHTML = namepart;
    }else if(cid==1){
    	var namepart='';
    	namepart += "<s:text name='ticketData.currency'></s:text>"+oRecord.getData("price")+"<br/><span class='smallestfont'>Fee: <s:text name='ticketData.currency'></s:text>"+oRecord.getData("fee")+"</span>";
   	 	elLiner.innerHTML = namepart;
    }else if(cid==2){
    	var namepart='';
    	namepart += "<s:text name='ticketData.currency'></s:text>"+oRecord.getData("volprice")+"<br/><span class='smallestfont'>Fee: <s:text name='ticketData.currency'></s:text>"+oRecord.getData("fee")+"</span>";
   	 	elLiner.innerHTML = namepart;
    }else if(cid==5){
        elLiner.innerHTML = "<div id='manageVolTkt"+ oRecord.getData("tid") +"'></div>";
    }
                  
    };
    
    YAHOO.widget.DataTable.Formatter.mydisplay = myCustomFormatter;
    
    var myColumnDefs = [
    		//{key:"showhide", label:"",formatter:"mydisplay"},
            {key:"name", label:"Name"},
            {key:"price", label:"Regular Price ("+currencySymbol+")",formatter:"mydisplay"} ,
            {key:"volprice", label:"Volume Price ("+currencySymbol+")",formatter:"mydisplay"} ,
            {key:"trigQty", label:"Trigger Count"} ,
            {key:"status", label:"Status"},
            {key:"action", label:"",formatter:"mydisplay"}
        ];
    var myDataTable =   new YAHOO.widget.DataTable(cname, myColumnDefs, myDataSource,{MSG_EMPTY:"&nbsp;"});
                myDataTable.subscribe("", myDataTable.onEventHighlightRow); 
                myDataTable.subscribe("", myDataTable.onEventUnhighlightRow);   
    return myDataTable;
}
function loadControlsUsingVolumeTicketsData(eid){
for(var i=0;i<YAHOO.vtickets.data.VolumeTicketDetails.length;i++){
	var volJsonobj = YAHOO.vtickets.data.VolumeTicketDetails[i];
	var volTicketIds;
	var voldivid = "manageVolTkt"+volJsonobj.tid;
	var voltkt_ManageMenu = [			
			{ text: "Edit", value: 1, onclick: { fn: editVolumeTicket ,obj: {"eid":eid,"ticketid":volJsonobj.tid} } },
			//{ text: "Delete", value: 2, onclick: { fn: deleteVolumeTicket ,obj: {"eid":eid,"ticketid":volJsonobj.tid} } },
			{ text: "Widget Code", value: 2, onclick:{ fn: widgetCode ,obj: {"eid":eid,"ticketid":volJsonobj.tid} } },
			//{ text: "Simulate", value: 4, onclick:{ fn: simulate ,obj: {"eid":eid,"ticketid":volJsonobj.tid} } },
		];
		
	var status = volJsonobj.status;
	var simulate=volJsonobj.simulate;
	if(status=='Pending' || status=='Approved'){
		
    	voltkt_ManageMenu.push({ text: "Delete", value: 3, onclick: { fn: deleteVolumeTicket ,obj: {"eid":eid,"ticketid":volJsonobj.tid} } });
    }	
    
    if(status!='Cancelled' && status!='Closed'){
    	voltkt_ManageMenu.push({ text: "Cancel", value: 4, onclick:{ fn: cancelVolumeTicket ,obj: {"eid":eid,"ticketid":volJsonobj.tid} } });
    }
    
	/*if(status=='Approved' || status=='Active' || status=='Suspended'){
		if(simulate=='N'){
			startstop = 'Start';
			simulate='Y'
		}else{
		 	startstop = 'Suspend';
		 	simulate='N'
		 }
    	voltkt_ManageMenu.push({ text: startstop, value: 4, onclick:{ fn: simulating ,obj: {"eid":eid,"ticketid":volJsonobj.tid,"simulate":simulate} } });
    }*/
	var splitBtn = new YAHOO.widget.Button({ type: "menu",  label: "Manage", name: "Manage", menu: voltkt_ManageMenu, container: voldivid });
}
}
function changeType(selectedtype){
if(selectedtype=='Absolute' || selectedtype=='ABSOLUTE'){
document.getElementById('discountprefixlabel').style.display='inline';
document.getElementById('discountsufixlabel').style.display='none';
//document.getElementById('discountprefixlabel2').style.display='inline';
//document.getElementById('discountsufixlabel2').style.display='none';
}else{
document.getElementById('discountprefixlabel').style.display='none';
document.getElementById('discountsufixlabel').style.display='inline';
//document.getElementById('discountprefixlabel2').style.display='none';
//document.getElementById('discountsufixlabel2').style.display='inline';
}
}

/*function widgetPopUp(func,event,jsonObj){
var eid = jsonObj.eid;
var url = "VolumeTicketDetails!widgetCode?eid="+eid;
loadURLBasedWindow(url, displayWidgetPopUp);
}

var displayWidgetPopUp = function(responseObj){
showPopUpDialogWithCustomButtons(responseObj, "Volume Tickets Widget Code", "");
}*/

/*function confirmationPagePopUp(func,event,jsonObj){
var eid = jsonObj.eid;
var purpose = jsonObj.purpose;
var url = "VolumeTicketDetails!confirmationPage?eid="+eid+"&purpose="+purpose;
loadURLBasedWindow(url, displayConfirmationPagePopUp);
}*/

function customConfirmationPage(eid,purpose){
var url = "VolumeTicketDetails!confirmationPage?eid="+eid+"&purpose="+purpose;
loadURLBasedWindow(url, displayConfirmationPagePopUp);
}

var displayConfirmationPagePopUp = function(responseObj){
var myButtons = [
	                 { text: "Submit", handler: submitConfirmationPageForm},
	                 { text: "Reset", handler: resetConfirmationPageForm},
	                 { text: "Preview", handler: previewConfirmationPageForm}
	             ];
showPopUpDialogWithCustomButtons(responseObj, "Customize Confirmation Page", myButtons);	
}
var submitConfirmationPageForm=function(){
	document.conPageForm.action = 'VolumeTicketDetails!saveConfirmationPage';
	submitFormAndReload('conPageForm', '');		
}
var resetConfirmationPageForm=function(){
	agree=confirm("By Resetting your Customization will be lossed");
	if(agree){
		document.conPageForm.action = 'VolumeTicketDetails!resetConfirmationPage';
		submitFormAndReload('conPageForm', '');
	}	
}
var previewConfirmationPageForm=function(){
	document.conPageForm.action = 'VolumeTicketDetails!previewConfirmationPage';
	$('conPageForm').request({
	   onSuccess:function(t){
	   y=YAHOO.ebee.popup.dialog.cfg.getProperty("y")+30;
	   x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	   var pgpreviewcnt="<iframe src = 'data:text/html;charset=utf-8,"+ escape(t.responseText)+"' width='600' height='600'></iframe>";
	   showPopupInPopup("Confirmation Page Preview",pgpreviewcnt,200,300,x,y);
	   },onFailure:function(){
	         alert("Failure");
	  }
	 });
}

function confirmationEmailPopUp(func,event,jsonObj){
var eid = jsonObj.eid;
var purpose = jsonObj.purpose;
//_eventid=eid;_purpose=purpose;windowtype='confirmationemailpopup';
var url = "VolumeTicketDetails!confirmationEmail?eid="+eid+"&purpose="+purpose;
loadURLBasedWindow(url, displayConfirmationEmailPopUp);
}

var displayConfirmationEmailPopUp = function(responseObj){
var myButtons = [
	                 { text: "Submit", handler: submitConfirmationEmailForm},
	                 { text: "Reset", handler: resetConfirmationEmailForm},
	                 { text: "Preview", handler: previewConfirmationEmailForm}
	             ];
showPopUpDialogWithCustomButtons(responseObj, "Customize Confirmation Email", myButtons);	
}
var submitConfirmationEmailForm=function(){
	document.conEmailForm.action = 'VolumeTicketDetails!saveConfirmationEmail';
	submitFormAndReload('conEmailForm', '');		
}
var resetConfirmationEmailForm=function(){
	agree=confirm("By Resetting your Customization will be lossed");
	if(agree){
		document.conEmailForm.action = 'VolumeTicketDetails!resetConfirmationEmail';
		submitFormAndReload('conEmailForm', '');
	}		
}
var _frameContent='Loading...';
function reloadIFrame(){
document.getElementById('proiframe').contentWindow.document.body.innerHTML=_frameContent;
var h=document.getElementById('proiframe').contentWindow.document.body.scrollHeight;
document.getElementById('proiframe').style.height=h+20+"px";
var w=document.getElementById('proiframe').contentWindow.document.body.scrollWidth;
document.getElementById('proiframe').style.width=w+20+"px";
}

var previewConfirmationEmailForm=function(){
YAHOO.ebee.popup.wait.show();
	document.conEmailForm.action = 'VolumeTicketDetails!previewConfirmationEmail';
	$('conEmailForm').request({
	   onSuccess:function(t){
	   y=YAHOO.ebee.popup.dialog.cfg.getProperty("y")+30;
	   x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	   //var previewcnt="<iframe id='proiframe' src = 'data:text/html;charset=utf-8,"+ escape(t.responseText)+"'></iframe>";
	  var previewcnt="<iframe id='proiframe' src = '/main/eventmanage/ticketing/myframe.html' frameBorder='0'></iframe>";
	  _frameContent=t.responseText;
	  showPopupInPopup("Confirmation Email Preview",previewcnt,200,300,x,y);
	  YAHOO.ebee.popup.wait.hide();
	  setTimeout('reloadIFrame()',1000);
	  },onFailure:function(){
	       YAHOO.ebee.popup.dialog.cancel();
	     	alert("Failure");
	  }
	 });
	
	 /*document.getElementById('previewdata').value='yes';
	 $('conEmailForm').request({
	   onSuccess:function(t){
	    YAHOO.ebee.popup.dialog.cancel();
	     showPopUpDialogWithCustomButtons(t, "Confirmation Email Preview", "");
	     windowtype='confirmationpreview';
	     YAHOO.ebee.popup.dialog.hideEvent.subscribe(function(){
		 if(windowtype=='confirmationpreview'){
		  var url = "VolumeTicketDetails!confirmationEmail?eid="+_eventid+"&purpose="+_purpose;
          windowtype='confirmationemailpopup';
          loadURLBasedWindow(url, displayConfirmationEmailPopUp);
         }else{
            YAHOO.ebee.popup.dialog.cancel();
            window.location.reload();
            } 
        });    	
	     },onFailure:function(){
	         alert("Failure");
	  }
	 });*/ 
	 
            
	//submitFormAndReload('', '');		
}


function loadConfirmationYUIMenus(eid){
/*YAHOO.util.Event.onAvailable("conpagetarget", function () {
var conPageMenu = new YAHOO.widget.Menu("conpagebasicmenu");
	conPageMenu.addItems([ 
	 
	        { text: "Confirmation Page", onclick: { fn: confirmationPagePopUp ,obj: {"eid":eid,"purpose":"vb_trigger_confirmation"} } }, 
	        //{ text: "Trigger failed and voided", onclick: { fn: confirmationPagePopUp ,obj: {"eid":eid,"purpose":"vb_trigger_confirmation"} } }, 
	        //{ text: "Trigger failed but charged", onclick: { fn: confirmationPagePopUp ,obj: {"eid":eid,"purpose":"vb_trigger_confirmation"} } } 
	    	]);
	
	YAHOO.util.Event.on("conpagemenutoggle", "mouseover", function () {
			document.getElementById('conpagebasicmenu').style.left="0px";
			document.getElementById('conpagebasicmenu').style.top="0px";
          conPageMenu.show();
    });
    YAHOO.util.Event.on("conpagemenutoggle", "mouseout", function () {
   		  conPageMenu.hide();
    });
	//YAHOO.util.Event.addListener("conpagemenutoggle", "mouseover", conPageMenu.show, null, conPageMenu);
    
    conPageMenu.render("conpagetarget");
    
    });*/
    
YAHOO.util.Event.onAvailable("conemailtarget", function () {    
var conEmailMenu = new YAHOO.widget.Menu("conemailbasicmenu");
	conEmailMenu.addItems([ 
	 		{ text: "Trigger success", onclick: { fn: confirmationEmailPopUp, obj: {"eid":eid,"purpose":"VB_TRIGGERCAPTURE_CONFIRMATION"} } }, 
	        { text: "Trigger fail", onclick: { fn: confirmationEmailPopUp, obj: {"eid":eid,"purpose":"VB_VOID_CONFIRMATION"} } } 
	    	]);
	
	YAHOO.util.Event.on("conemailmenutoggle", "mouseover", function () {
			document.getElementById('conemailbasicmenu').style.left="0px";
			document.getElementById('conemailbasicmenu').style.top="0px";
          	conEmailMenu.show();
    });
    YAHOO.util.Event.on("conemailmenutoggle", "mouseout", function () {
   		  conEmailMenu.hide();
    });   
    conEmailMenu.render("conemailtarget");
    
    });
    
}

function widgetCode(func,event,jsonObj){
var eid = jsonObj.eid;
var ticketId = jsonObj.ticketid;
var url = "VolumeTicketDetails!widgetCode?eid="+eid+"&ticketId="+ticketId;
showYUIinIframe("Widget Code",url,500,200);
}

function closeWidgetCodePopUp(){
	YAHOO.ebee.popup.dialog1.hide();
}

function deleteVolumeTicket(func,event,jsonObj){
var eid = jsonObj.eid;
var ticketId = jsonObj.ticketid;
var agree=confirm("Volume Ticket will be deleted from the list for ever. This operation cannot be revert back.");
if (agree){	
	var url='VolumeTicketDetails!deleteVolumeTicket?eid='+eid+'&ticketId='+ticketId;
	callURLandReload(url);
}
}

function cancelVolumeTicket(func,event,jsonObj){
var eid = jsonObj.eid;
var ticketId = jsonObj.ticketid;

var url='VolumeTicketDetails!confirmCancelVolumeTicket?eid='+eid+'&ticketId='+ticketId;
new Ajax.Request(url, {
		  method: 'get',
		  onSuccess: function(transport) {
			var result=transport.responseText;
			if(result.indexOf("exist")>-1){
				var agree=confirm("Some pending registrations are there for this ticket. Cancel the ticket all pending registrations will be voided. Do you want to cancel?");
				if(agree){
					YAHOO.ebee.popup.wait.show();
					var url='VolumeTicketDetails!cancelVolumeTicket?eid='+eid+'&ticketId='+ticketId;
					callURLandReload(url);
				}
			}else{	
				var agree=confirm("Do yout want to cancel?");
				if(agree){			
					YAHOO.ebee.popup.wait.show();
					var url='VolumeTicketDetails!cancelVolumeTicket?eid='+eid+'&ticketId='+ticketId;
					callURLandReload(url);
				}
			}
	      }
	});
}


function simulating(func,event,jsonObj){
var eid = jsonObj.eid;
var ticketId = jsonObj.ticketid;
var simulate = jsonObj.simulate;
var url='VolumeTicketDetails!simulating?eid='+eid+'&ticketId='+ticketId+'&showStatus='+simulate;
//loadURLBasedWindow(url, displaySimulateScreen);
var confirmtext='';
if(simulate=='N')
	confirmtext='Volume Sale will suspend.';
else confirmtext='Volume Sale will start.';
var agree=confirm(confirmtext);
	if (agree){	
		callURLandReload(url);
	}	
}

var displaySimulateScreen = function (responseObj){
	showPopUpDialogWindow(responseObj, "Simulator", submitSimulateForm, handleCancel);
}
var submitSimulateForm = function(){
	submitFormAndReload('simulateForm', '');	
}

function callTriggerQtyHelp(){
	//y=YAHOO.ebee.popup.dialog.cfg.getProperty("y");
	var top=document.getElementById('popupdialog_c').style.top;
	y=Number(top.replace("px",""))+150;
	x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	showPopupInPopup(trigger_quantity_helptitle, getHelpContent('trigger_quantity_helpcontent'),200,100,x,y);
}

function callTriggerSuccessHelp(){
	//y=YAHOO.ebee.popup.dialog.cfg.getProperty("y");
	var top=document.getElementById('popupdialog_c').style.top;
	y=Number(top.replace("px",""))+250;
	x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	showPopupInPopup(trigger_success_helptitle, getHelpContent('trigger_success_helpcontent'),200,300,x,y);
}

function callTriggerFailHelp(){
	//y=YAHOO.ebee.popup.dialog.cfg.getProperty("y");
	var top=document.getElementById('popupdialog_c').style.top;
	y=Number(top.replace("px",""))+300;
	x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	showPopupInPopup(trigger_fail_helptitle, getHelpContent('trigger_fail_helpcontent'),200,300,x,y);
}

function callServiceFeeHelp(){
	//y=YAHOO.ebee.popup.dialog.cfg.getProperty("y")+180;
	var top=document.getElementById('popupdialog_c').style.top;
	y=Number(top.replace("px",""))+350;
	x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	showPopupInPopup(tickets_ticketfee_helptitle, getHelpContent('tickets_ticketfee_helpcontent'),200,400,x,y);
}

function showhidevolumetickets(eid){
	var url = 'TicketShowHide!volumeTickets?eid='+eid;
	loadURLBasedWindow(url, handleDisplayVolumeTickets);
}

var handleDisplayVolumeTickets = function(responseObj){
	showPopUpDialogWindow(responseObj, "Volume Tickets", saveShowHideVolTickets, handleCancel);		
}

var saveShowHideVolTickets = function(){
	/*var parent = document.getElementById("volticketslist");
	parent.innerHTML  = "";
	var selectBox = document.getElementById("voltickets");
	for (var i = 0; i < selectBox.options.length; i++) {
		var obj1 = createHiddenTextElement(i,"value",selectBox.options[i].text);			
		var obj2 = createHiddenTextElement(i,"key",selectBox.options[i].value);
		parent.appendChild(obj1);
		parent.appendChild(obj2);
	}
	document.getElementById("selvoltickets").selectedIndex = -1
	document.getElementById("voltickets").selectedIndex = -1*/
	submitFormAndCloseWindow('volticketshowhideform', '');
	//submitFormAndReload('volticketshowhideform', '');
}


