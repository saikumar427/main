var eventid='';
var _purpose='';
function createTrackingPartner(event,obj) {
	var url = 'CreateTrackingPartner?mgrid=' + obj.mid;
	loadURLBasedWindow(url, handleTrackingPartnerSuccess);
}
function gettrackname(){
	document.getElementById('url').innerHTML=document.getElementById('trackname').value;
}
/*function editTrackingPartner(event,obj) {
	var url = 'CreateTrackingPartner!edit?mgrid=' + obj.mgrid+'trackid'+obj.trackid;
	loadURLBasedWindow(url, editGroupThemeSuccess);
}*/
var handleTrackingPartnerSuccess = function(responseObj) {
	showPopUpDialogWindow(responseObj, "Tracking Partner",saveTrackingPartner, handleCancel);
}
var saveTrackingPartner = function() {

	submitFormAndReload('createtrackingpartnerform', 'errormsg');
	//this.cancel();
}

function createEventGroup() {
	var url = 'CreateEventGroup!addEventGroup?a=1';
	loadURLBasedWindow(url, handleEventGroupSuccess);
}
function editEventGroup(egroupid) {
	var url = 'CreateEventGroup!editEventGroup?eventgroupid=' + egroupid;
	loadURLBasedWindow(url, handleEventGroupSuccess);
}
function deleteEventGroup(egroupid) {
	var agree;
	agree = confirm("Deleting Event Group doesn't delete individual events in the group. Click on Confirm button to delete Event Group");
	var url = 'CreateEventGroup!deleteEventGroup?eventgroupid=' + egroupid;
	if (agree) {
		callURLandReload(url);
	} else
		return false;
}
var handleEventGroupSuccess = function(responseObj) {
	showPopUpDialogWindow(responseObj, "Event Group Details", saveEventGroup,
			handleCancel);
}
var saveEventGroup = function() {
	var title = document.forms['createeventsgroup'].elements['grpevent.title'].value;
	title = trim(title);
	if (title == "") {
		alert("Enter text for the name.");
		return false;
	}
	var parent = document.getElementById("optlist");
	parent.innerHTML = "";
	var selectBox = document.getElementById("selgroupevents");
	for ( var i = 0; i < selectBox.options.length; i++) {
		var obj1 = createHiddenTextElement(i, "value",
				selectBox.options[i].text);
		var obj2 = createHiddenTextElement(i, "key", selectBox.options[i].value);
		parent.appendChild(obj1);
		parent.appendChild(obj2);
	}
	submitFormAndReload('createeventsgroup', '');
	this.cancel();
}
function createTheme() {
	var url = 'eventslist!addUserTheme';
	window.location.href = url;
}
function editGroupTheme(gId) {
	var url = 'ManageGroup!editGroupTheme?gid=' + gId;
	loadURLBasedWindow(url, editGroupThemeSuccess);
}
var editGroupThemeSuccess = function(responseObj) {
	var myButtons = [ {
		text :"Submit",
		handler :saveGroupTheme
	}, {
		text :"Cancel",
		handler :handleCancel
	}/*, {
		text :"Preview",
		handler :getContentPreview
	}*/ ];
	showPopUpDialogWithCustomButtons(responseObj, "Customize Event Group Page",
			myButtons);
}
var saveGroupTheme = function() {
	submitFormAndReload('GroupTheme', '');
}
var EventsPageThemeSuccess = function(responseObj) {
	showPopUpDialogWindow(responseObj, "Customize My Box Office Page", saveTheme,
			handleCancel);
}
function updateFunction() {
	submitFormAndCloseWindow("EventsPageContent", '');
	document.getElementById('updatedMsg').innerHTML = 'Updated Successfully';
}
function customizeEventsPage(id) {
	if(document.getElementById(id).checked){
		var url = 'CustomizeEventsPageTheme!edit?purpose=eventspage';
		loadURLBasedWindow(url, EventsPageThemeSuccess);
	}else {
	    	alert('Please check Custom HTML & CSS');
	    }  
}
var saveTheme = function() {
	YAHOO.ebee.popup.wait.show();
	submitFormAndReload('EventsPageTheme', '');
}
function customizeEventsPageContent() {
	var url = 'CustomizeEventsPageTheme!customizeContent?purpose=eventspagecontent';
	loadURLBasedWindow(url, EventsPageThemeContentSuccess);
}
var EventsPageThemeContentSuccess = function(responseObj) {
	showPopUpDialogWindow(responseObj, "My Events Preferences", save,
			handleCancel);
}
var save = function() {
	submitFormAndReload('EventsPageContent', '');
}
function getImageURL(divId) {
	currentDivId = divId;
	url = '../membertasks/ImageUpload';
	showYUIinIframe("Photo Upload", url, 400, 200);
	// popupwindow('../membertasks/ImageUpload','Map','300','300');
}
function closeFileUploadWindow() {
	YAHOO.ebee.popup.dialog1.cancel();
}
function setWebPath(fullPath, uploadurl, photoname) {
	document.getElementById(currentDivId).value = fullPath;
	YAHOO.ebee.popup.dialog1.cancel();
	if (document.getElementById("uploadurl"))
		if (document.getElementById("uploadurl")) {
			document.getElementById('uploadurl').value = uploadurl;
		}

	if (document.getElementById("photoname"))
		if (document.getElementById("photoname")) {
			document.getElementById('photoname').value = photoname.substring(0,
					photoname.lastIndexOf("."));
		}
	inserteventsPublicPagephotoURL();
}
function inserteventsPublicPagephotoURL() {
	var eventphotoURL = document.getElementById('Image').value;
	var uploadurl = document.getElementById('uploadurl').value;
	var photoname = document.getElementById('photoname').value;
	var url = "CustomizeEventsPageTheme!updateeventPublicPagePhotoURL?purpose=update";
	var params = {
		eventphotoURL :eventphotoURL,
		uploadurl :uploadurl,
		photoname :photoname
	};
	submitContentFormAndReload(url, params);
}
function submitContentFormAndReload(url, params) {
	var dynatimestamp = new Date();
	url += '&dynatimestamp=' + dynatimestamp.getTime();
	new Ajax.Request(url, {
		method :'post',
		parameters :params,
		onSuccess : function(transport) {
			var result = transport.responseText;
			window.location.reload(true);
		}
	});
}

function listNewEvent()
{
YAHOO.ebee.popup.wait.show();
var url='/main/myevents/createevent!getEventsCount';
new Ajax.Request(url, {
	    	       method: 'post',
	    	       onSuccess: function(t){
	    	        if(!isValidActionMessage(t.responseText)) return;
	    	        var data=t.responseText;
			       var json=eval('('+data+')');
			       var listingStatus = json.listingStatus;
			       var mgrid = json.mgrid;		
			       if(listingStatus.indexOf('donot_allow')>-1)
			       	 getListingPopup(mgrid,listingStatus);			      
	    	       else 
	    	       window.location.href='/main/myevents/createevent';
		          },onFailure:function(){alert("Failure");}
	    	    });   

//window.location.href='/main/myevents/createevent';
}

function getListingPopup(mgrid,listingStatus){
var url='/main/myevents/createevent!getListingPopup?mgrId='+mgrid+"&msgType="+listingStatus;
loadURLBasedWindow(url,listpopupscreen);

}



var listpopupscreen = function (responseObj){
	if(!isValidActionMessage(responseObj.responseText)) return;	
	YAHOO.ebee.popup.dialog.setHeader("Message");
    YAHOO.ebee.popup.dialog.setBody(responseObj.responseText);	
	YAHOO.ebee.popup.dialog.hideEvent.subscribe(function(){		
		//window.location.href='/main/myevents/home';
		YAHOO.ebee.popup.wait.hide();
	});
	adjustYUIpopup();
}





var j=0;
function copyEvent1()
{
/**/
if(document.getElementById('selectedEvent'))
	var eventid=document.getElementById('selectedEvent').value;	
	url='/main/myevents/CopyEvent!getMgrCardStatus';
       new Ajax.Request(url, {
	    	       method: 'post',
	    	       onSuccess: function(t){
	    	       var jsondata=eval('('+t.responseText+')');
	    	       var checkcardstatus=jsondata.cardstatus;
	    	       var mgrid=jsondata.mgrid;
	    	       listingevent(checkcardstatus,eventid);
	    	      /* if(checkcardstatus=="nocard"){
	    	       var url = '/main/myevents/CopyEvent?a=1&eid='+eventid;
	               loadURLBasedWindow(url, handleCopyEventSuccess1);
	    	       }else{
	    	       _purpose='CopyExistingEvent';
	    	       _powertype='';
	    	       getcc(mgrid,'copyexistingevent');
	    	       }*/
	    	       },onFailure:function(t){alert("Fail");}
	    });
}


function listingevent(checkcardstatus,eventid){
YAHOO.ebee.popup.wait.show();
	url = '/main/myevents/createevent!getEventsCount';
		new Ajax.Request(url, {
					method: 'post',
	    	        onSuccess: function(t){
	    	        if(!isValidActionMessage(t.responseText)) return;
	    	        var data=t.responseText;
				       var json=eval('('+data+')');
				       var listingStatus = json.listingStatus;
				       if(listingStatus=='donot_allow')
		    	       getListingPopup();
		    	       else 
		    	       {
			    	       if(checkcardstatus=="nocard"){
			    	       var url = '/main/myevents/CopyEvent?a=1&eid='+eventid;
			               loadURLBasedWindow(url, handleCopyEventSuccess1);
			    	       }else{
			    	       _purpose='CopyExistingEvent';
			    	       _powertype='';
			    	       getcc(mgrid,'copyexistingevent');
		    	           }		    	       
		    	       }		    	      
			          },onFailure:function(){alert("Failure");}

});
}


var handleCopyEventSuccess1 = function(responseObj){
      showPopUpDialogWindow(responseObj, "Copy Existing Event", copyEvent, CancelCopyExistingEvent);
      YAHOO.ebee.popup.dialog.hideEvent.subscribe(function(){
		if(document.getElementById('selectedEvent')){
				document.getElementById('selectedEvent').selectedIndex=0;
				CancelCopyExistingEvent();
				}		
	});
      var eventid=document.getElementById('selectedEvent').value;
      if(eventid!=''){
      if(document.getElementById("recurringdates")) document.getElementById("recurringdates").style.display='block';
      else if(document.getElementById("normaldates")) document.getElementById("normaldates").style.display='block';
      }else{
      if(document.getElementById("recurringdates")) document.getElementById("recurringdates").innerHTML='';
      if(document.getElementById("normaldates")) document.getElementById("normaldates").innerHTML='';
      }
      	
 }
 
 var copyEvent=function()
 {
 saveEvent(++j);
 }
 var saveEvent=function(j)
 {
 if(j==1)
 saveCopyEvent();
 }
 
 var saveCopyEvent = function(){
	var title = document.forms['copyevent'].elements['newEventName'].value;
	var index = document.forms['copyevent'].elements['selectedEvent'].selectedIndex;
	if(index==0){
		    alert("Select Existing Event to Copy");
		    j=0;
			return false;
	}
	title = trim(title);
	/*if (title == "") {
			alert("Enter text for the event title.");
			return false;
	}*/
	
	YAHOO.ebee.popup.wait.show();
	document.getElementById('copyevent').request({
        method: 'get',
        onFailure: function() { alert("error"); },
        onSuccess: function(t) {
	      if(!isValidActionMessage(t.responseText)){
    			return;
    		}
	      var result=t.responseText;
	      if(result.indexOf("success")>-1){
	      YAHOO.ebee.popup.dialog.cancel();
	      //checklisting('copy');
	      window.location.href="../myevents/home";
	      }else{
	      YAHOO.ebee.popup.wait.hide();
	      document.getElementById('copyeventerrormessage').style.display='block';
	      document.getElementById('copyeventerrormessage').innerHTML=t.responseText; 
	      j=0;
	       //window.location.reload();
	       //window.location.href="../myevents/home";
        }
       }
    });
	//submitFormAndReload('copyevent', '');	
}

/*function checklisting(type){
var url='/main/myevents/createevent!getEventsCount';
new Ajax.Request(url, {
	    	       method: 'post',
	    	       onSuccess: function(t){
	    	        if(!isValidActionMessage(t.responseText)) return;
	    	        var data=t.responseText;
			       var json=eval('('+data+')');
			       var listingStatus = json.listingStatus;
			       var mgrid = json.mgrid;			      
			       if(listingStatus=='donot_allow')
	    	       getListingPopup(mgrid);
	    	       else {
	    	       if(type=='copy')
	    	       window.location.href="../myevents/home";
	    	       else if(type=='list')
	    	       window.location.href='/main/myevents/createevent';
	    	       }	
		          },onFailure:function(){alert("Failure");}
	    	    });  
	    	    
}*/	    	    
 
var CancelCopyExistingEvent=function(){
    YAHOO.ebee.popup.dialog.cancel();
   // YAHOO.ebee.popup.wait.show();
	// window.location.reload( true );	 
} 

function getcc(rfid,purp)
{
  var Msg='To manage your events, please provide your credit card information. This will be stored in a secured way and your invoice amounts will be charged to this card.';
  var setkey= new callPaykey();
  //setkey.setCallbackurl("/main/myaccount/home!insertManagerccData");
  setkey.setPurpose(purp);
  if(purp.indexOf("authorize")>-1){
  	setkey.setPaymode("authvault");
  	setkey.setAmount("50.00");
  }else{
  	setkey.setPaymode("vault");
  	setkey.setAmount("1.00");
  }
  setkey.setMessage(Msg);
  setkey.setCurrency("USD");
  setkey.setMerchantid("");
  setkey.setLang("En");
  setkey.setTitle("Credit Card Details");
  setkey.setSoftdis("");
  setkey.setRefid(rfid);
  setkey.setVendor("Braintree");
  setkey.setAppendDiv('maincontent');
  setkey.setInternalref('111');
  var paykey=setkey.getPaykey();
     if(paykey!="")
	 {setkey.ccpopup(paykey);
	 }
	 else{
	 alert("unable to process Not valid paykey");
	 }

}

function onsucessclose()
	{
	closeEBpopup();	
	if(_purpose=='myeventspage' || _purpose=='groupeventspage')
		window.location.href="/main/eventmanage/Snapshot?eid="+eventid;
	else if(_purpose=='accountlevelreports')
		window.location.href="/main/myevents/accountreports";
	else
		responseDatacc();
		
	}
function CloseAction(){
	
}


