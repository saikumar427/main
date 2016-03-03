YAHOO.namespace("ebee.popup");
YAHOO.namespace("helppanel.container");
YAHOO.namespace("helppanel.container.manager");
function log(message){
	try{
		if(window.console)
			console.log(message);
	}
	catch(e){}
}
function showPopUpDialogWithCustomButtons(responseObj, windowtitle, myButtons){
	YAHOO.ebee.popup.wait.hide();
	if(!isValidActionMessage(responseObj.responseText)){
       	return;
    }
    YAHOO.ebee.popup.dialog.setHeader(windowtitle);
    YAHOO.ebee.popup.dialog.setBody(responseObj.responseText);
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", myButtons);
	adjustYUIpopup();
}
function showPopUpDialogWindow(responseObj, windowtitle, submitBtnHandler, cancelBtnHandler){
	var myButtons = [
	                 { text: "Submit", handler: submitBtnHandler },
	                 { text: "Cancel", handler: cancelBtnHandler }
	             ];
	showPopUpDialogWithCustomButtons(responseObj, windowtitle, myButtons);	
} 
var handleDataFailure = function (o){
	YAHOO.ebee.popup.wait.hide();	
	alert("Unable to process the action. Please try later");
}
var handleCancel = function(){
	YAHOO.ebee.popup.dialog.cancel();
	if(navigator.appName == "Microsoft Internet Explorer")
		window.location.reload( true );	
}
function callURLandReload(url){
	var dynatimestamp=new Date();
	url+='&dynatimestamp='+dynatimestamp.getTime();
	var ajax= new Ajax.Request(url, { method:'get',
      onSuccess: function(){window.location.reload( true ); }
  });
}
function loadURLBasedWindow(url, onSuccessAction){
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += "&dynatimestamp="+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:onSuccessAction,failure:handleDataFailure});
}
function submitFormAndCloseWindow(formId, errMsgDivid){
	submitFormAndHandleNext(formId, errMsgDivid, false);
}
function submitFormAndReload(formId, errMsgDivid){
	submitFormAndHandleNext(formId, errMsgDivid, true);
}
function submitFormAndHandleNext(formId, errMsgDivid, reloadYN){
$(formId).request({
        onFailure: function() { $(errMsgDivid).update('Failed to get results') },
        onSuccess: function(t) {
            
	        var result=t.responseText;
	        if(!isValidActionMessage(result)) return;
	        if(result.indexOf("success")>-1){
	        	YAHOO.ebee.popup.dialog.cancel();
	        	if(reloadYN)
	        		window.location.reload( true );	
	        } 
	         else {
            	$(errMsgDivid).update(result);
	        }
        }
    });
}
function setHelpTitle(title){
	document.write(title);
}
function getHelpContent(divname){
var cnt='oops! Help Content is missing';
if(document.getElementById(divname))	
		cnt='<div>'+document.getElementById(divname).innerHTML+'</div>';
return cnt;
}
function setHelpContent(divname){
	document.write(getHelpContent(divname));

}
var isValidActionMessage=function(messageText){
if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
    	alert('You need to login to perform this action');
    	window.location.href="../user/login";
    	return false;
    }
 else if(messageText.indexOf('Something went wrong')>-1){
 		alert('Sorry! You do not have permission to perform this action');
 		YAHOO.ebee.popup.wait.hide();
 		return false;
 	} 
    return true;	
}
var NS4 = (navigator.appName == "Netscape" && parseInt(navigator.appVersion) < 5);
function loadHelpPanel(panelDiv, containerDiv, W){

YAHOO.helppanel.container.panel = new YAHOO.widget.Panel(panelDiv, { visible:false,	width: W} );
	YAHOO.helppanel.container.panel.render();
	YAHOO.helppanel.container.manager = new YAHOO.widget.OverlayManager();
	YAHOO.helppanel.container.manager.register([YAHOO.helppanel.container.panel]);
	YAHOO.util.Event.addListener(containerDiv, "click", YAHOO.helppanel.container.panel.show, YAHOO.helppanel.container.panel, true);	
}
YAHOO.util.Event.onDOMReady( function() {
	// Define various event handlers for Dialog

		var handleSuccess = function(o) {
			var response = o.responseText;
		};
		var handleFailure = function(o) {
			alert("Submission failed: " + o.status);
		};

		// Remove progressively enhanced content class, just before creating the
		// module
		YAHOO.util.Dom.removeClass("popupdialog", "yui-pe-content");

		// Instantiate the Dialog
		
		YAHOO.ebee.popup.dialog = new YAHOO.widget.Dialog("popupdialog", {
			modal :true,
			zindex:1000,
			visible :false,
			fixedcenter:true,
			constraintoviewport :true			
		});
		// Wire up the success and failure handlers
		YAHOO.ebee.popup.dialog.callback = {
			success :handleSuccess,
			failure :handleFailure
		};
		// Render the Dialog
		YAHOO.ebee.popup.dialog.render();		
		
		YAHOO.util.Dom.removeClass("iframepopupdialog", "yui-pe-content");
		YAHOO.ebee.popup.dialog1 = new YAHOO.widget.Dialog("iframepopupdialog", {
			modal :true,
			zindex:20000,
			visible :false,
			fixedcenter:true,
			constraintoviewport :true
			
		});
		
		// Render the Dialog
		YAHOO.ebee.popup.dialog1.render();	
		
		YAHOO.util.Dom.removeClass("popinpopupdialog", "yui-pe-content");
		YAHOO.ebee.popup.dialog2 = new YAHOO.widget.Dialog("popinpopupdialog", {
			modal :true,
			zindex:10000,
			visible :false,
			fixedcenter:false,
			constraintoviewport :true
			
		});
		if (!YAHOO.ebee.popup.wait) {

            // Initialize the temporary Panel to display while waiting for external content to load

            YAHOO.ebee.popup.wait = 
                    new YAHOO.widget.Panel("wait",  
                                                    { width: "240px", 
                                                      fixedcenter: true, 
                                                      close: false, 
                                                      draggable: false, 
                                                      zindex:500,
                                                      modal: true,
                                                      visible: false
                                                    } 
                                                );
    
            YAHOO.ebee.popup.wait.setHeader("Processing...");
            YAHOO.ebee.popup.wait.setBody("<img src=\"/main/images/loading.gif\"/>");
            YAHOO.ebee.popup.wait.render(document.body);

        }
		
	});

function drawChart(chartdata, divid) {
	if(document.getElementById(divid)){
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'a');
        data.addColumn('number', 'b');
        data.addRows(chartdata);
		var options = {
          is3D: 'true',
		  legend: {
		  position: 'right',
		  textStyle:{
		  fontName: 'verdana,arial,helvetica,clean,sans-serif',
          fontSize: 12
		  }
        }
		};
        new google.visualization.PieChart(document.getElementById(divid)).draw(data, options);
        }
}

function addOption(theSel, theText, theValue)
{
  var newOpt = new Option(theText, theValue);
  var selLength = theSel.length;
  theSel.options[selLength] = newOpt;
}

function deleteOption(theSel, theIndex)
{ 
  var selLength = theSel.length;
  if(selLength>0)
  {
    theSel.options[theIndex] = null;
  }
}

function moveOptions(theSelFrom, theSelTo)
{
  
  var selLength = theSelFrom.length;
  var selectedText = new Array();
  var selectedValues = new Array();
  var selectedCount = 0;
  
  var i;
  
  // Find the selected Options in reverse order
  // and delete them from the 'from' Select.
  for(i=selLength-1; i>=0; i--)
  {
    if(theSelFrom.options[i].selected)
    {
      if(theSelFrom.options[i].text=="Attendee Name"){
    	  return;
      }
      selectedText[selectedCount] = theSelFrom.options[i].text;
      selectedValues[selectedCount] = theSelFrom.options[i].value;
      deleteOption(theSelFrom, i);
      selectedCount++;
    }
  }
  
  // Add the selected text/values in reverse order.
  // This will add the Options to the 'to' Select
  // in the same order as they were in the 'from' Select.
  for(i=selectedCount-1; i>=0; i--)
  {
    addOption(theSelTo, selectedText[i], selectedValues[i]);
  }
  
  if(NS4) history.go(0);
}
function ManageQuestion_mvUpOption(elem) {

	selindex = elem.selectedIndex;
	if (selindex <= 0) {
		return;
	}
	temp = elem.options[selindex].text;
	val = elem.options[selindex].value;
	elem.options[selindex].text = elem.options[selindex - 1].text;
	elem.options[selindex].value = elem.options[selindex - 1].value;
	
	elem.options[selindex - 1].text = temp;
	elem.options[selindex - 1].value = val;
	elem.selectedIndex = selindex - 1;
}

function ManageQuestion_mvDnOption(elem) {

	selindex = elem.selectedIndex;
	if (selindex == elem.length - 1) {
		return;
	}
	temp = elem.options[selindex].text;
	val = elem.options[selindex].value;
	elem.options[selindex].text = elem.options[selindex + 1].text;
	elem.options[selindex].value = elem.options[selindex + 1].value;
	
	elem.options[selindex + 1].text = temp;
	elem.options[selindex + 1].value = val;
	elem.selectedIndex = selindex + 1;
}
function createHiddenTextElement(index,etype,val){
	var pname=document.createElement("input");
	pname.type="hidden";
	pname.name="optionsList["+index+"]."+etype;	
	pname.value=val;
	return pname;
}
function trim(s1){
		s1=ltrim(s1);
		s1=rtrim(s1);
		return s1;
 	}
function ltrim(s1){
	for(i=0;i<s1.length;i++){
		if(s1.charAt(i)==' ' || s1.charAt(i)=='\t' || s1.charAt(i)=='\n'){

		}
		else{
			s1=s1.substr(i)
			break;
		}
		if(i==s1.length-1)
		s1="";
	}

	return s1;
 }
function rtrim(s1){
	for(i=s1.length-1;i>=0;i--){
		if(s1.charAt(i)==' ' || s1.charAt(i)=='\t' || s1.charAt(i)=='\n'){
		}
		else{
			s1=s1.substring(0,i+1);
			break;
		}
		if(i==0)
		s1="";
	}
	return s1;
 }
 function submitForm(event,jsonobj){
 	
 	var formname = jsonobj.formName;
 	var divname = jsonobj.divName;
	$(formname).request({
  		onComplete: function(o){
  		if(!isValidActionMessage(o.responseText)) return;
  			$(divname).update(o.responseText);
  			$('break').show();
	  		$(divname).scrollTo();	  		
  		}
	});
}
function hidestatusmessage(divname){
if(!divname) divname='statusMessageBox';
 $(divname).innerHTML="";
 $(divname).hide();
 //$(divname).parent().hide();
 
}
var showPopupInPopup = function (title,cnt,w,h, x, y){
	YAHOO.ebee.popup.wait.hide();
	YAHOO.ebee.popup.dialog2.setHeader(title);
	//var cnt="<iframe src='"+url+"' width='"+w+"' height='"+h+"'></iframe>";
	YAHOO.ebee.popup.dialog2.setBody(cnt);
	YAHOO.ebee.popup.dialog2.cfg.setProperty("fixedcenter", false);
	YAHOO.ebee.popup.dialog2.cfg.setProperty("x", x);
	YAHOO.ebee.popup.dialog2.cfg.setProperty("y", y);
	YAHOO.ebee.popup.dialog2.render();
	YAHOO.ebee.popup.dialog2.show();
}
var showYUIinIframe = function (title,url,w,h){
	YAHOO.ebee.popup.wait.hide();
	YAHOO.ebee.popup.dialog1.setHeader(title);
	var cnt="<iframe src='"+url+"' width='"+w+"' height='"+h+"' frameborder='0'></iframe>";
	YAHOO.ebee.popup.dialog1.setBody(cnt);
	YAHOO.ebee.popup.dialog1.cfg.setProperty("fixedcenter", true);	
	YAHOO.ebee.popup.dialog1.cfg.setProperty("y",YAHOO.ebee.popup.dialog1.cfg.getProperty("y"));
	YAHOO.ebee.popup.dialog1.cfg.setProperty("fixedcenter", false);
	YAHOO.ebee.popup.dialog1.render();
	YAHOO.ebee.popup.dialog1.show();
	//adjustYUIpopup(YAHOO.ebee.popup.dialog1);	
}
var adjustYUIpopup=function (dialogwindowhandle){
if(!dialogwindowhandle) dialogwindowhandle= YAHOO.ebee.popup.dialog;
	dialogwindowhandle.cfg.setProperty("fixedcenter", true);	
	dialogwindowhandle.cfg.setProperty("y",dialogwindowhandle.cfg.getProperty("y"));
	dialogwindowhandle.cfg.setProperty("fixedcenter", false);
	dialogwindowhandle.render(document.body);
	dialogwindowhandle.show();
}
function testtrim(str){
	var temp='';
	temp=new String(str);
	temp=temp.replace(/[^a-zA-Z 0-9]+/g,'');
	return temp;
}