function eventlogo(){	
	document.getElementById('logomsg').style.display="block";
}
function hidelogo(){
	document.getElementById('logomsg').style.display="none";
}
function pwdprotect(){
	document.getElementById('passwordprotect').style.display="block";	
}
function closebtn(){
	document.getElementById('passwordprotect').style.display="none";        
}
function submitContentFormAndReload(url, params){
	var dynatimestamp=new Date();
	url+='&dynatimestamp='+dynatimestamp.getTime();	
	YAHOO.displayinfo.wait.setHeader("Updating...");
	YAHOO.displayinfo.wait.show();
	new Ajax.Request(url, {
		  method: 'post',
		  parameters: params,
		  onSuccess: function(transport) {
			var result=transport.responseText;
			YAHOO.displayinfo.wait.hide();
			YAHOO.ebee.popup.dialog.cancel();
			window.location.reload(true);			
	}
	});
}
function insertlogoandmessage(){
	var image=document.getElementById('image').value;
	var message=document.getElementById('message').value;
	var eid=document.getElementById('eid').value;
	var url="EventPageContent!insertlogoandmessage?eid="+eid;
	var params={ message:message,image:image};
	submitContentFormAndReload(url, params);	
}
function inserteventphotoURL(eid){
	var eventphotoURL=document.getElementById('eventphotoURL').value;	
	var uploadurl=document.getElementById('uploadurl').value;
	var photoname=document.getElementById('photoname').value;		
	var url="EventPageContent!updateeventphotoURL?eid="+eid;
	var params={eventphotoURL:eventphotoURL,uploadurl:uploadurl,photoname:photoname};
	submitContentFormAndReload(url, params);	
}
function insertpwd(){
	var password=document.getElementById('password').value;
	var powertype=document.getElementById('powertype').value;
	var eid=document.getElementById('eid').value;
	var url="EventPageContent!insertEventPassword?eid="+eid;
	var params={password:password,powertype:powertype};
	submitContentFormAndReload(url, params);	
}
function configureEventView(eid,val,updateto){
	var url="";
	var date = new Date();
	var resultdiv='';
	$('googleSettingUpdateMessage').hide();
	$('showattendeesSettingUpdateMessage').hide();
	if(updateto=='googlemap'){
	resultdiv='googleSettingUpdateMessage';
		url="EventPageContent!updateshowgooglemap?eid="+eid+"&val="+val+"&datetimestamp="+date.getTime();		
	}else if(updateto=='promotions'){
		resultdiv='promotionsUpdateMessage';
		url="EventPageContent!updateshowpromotions?eid="+eid+"&val="+val+"&datetimestamp="+date.getTime();
	}else if(updateto=='questions'){
		resultdiv="questionsSettingUpdateMessage";
		url="EventPageContent!updateshowquestions?eid="+eid+"&val="+val+"&datetimestamp="+date.getTime();
	}else if(updateto=='socialpromotions'){
	   resultdiv="socialPromotionsUpdateMessage";
	   url="EventPageContent!updateshowsocialpromotions?eid="+eid+"&val="+val+"&datetimestamp="+date.getTime(); 
	}else if(updateto=='voltickets'){
	   resultdiv="volticketsUpdateMessage";
	   url="EventPageContent!updateshowvolumetickets?eid="+eid+"&val="+val+"&datetimestamp="+date.getTime(); 
	}else{
	resultdiv='showattendeesSettingUpdateMessage';
	url="EventPageContent!updateshowattendee?eid="+eid+"&val="+val+"&datetimestamp="+date.getTime();
	}
	var dynatimestamp=new Date();
	url+='&dynatimestamp='+dynatimestamp.getTime();
	YAHOO.displayinfo.wait.setHeader("Updating...");
	YAHOO.displayinfo.wait.show();
	new Ajax.Request(url, {
		  method: 'get',
		  onSuccess: function(transport) {
			var result=transport.responseText;
			$(resultdiv).update("Updated");
			$(resultdiv).show();
			YAHOO.displayinfo.wait.hide();						
		}
	});	
}
function displayInfo(eid){
    var url = 'AttendeeListDisplayFields!displayInformation?eid='+eid;
	loadURLBasedWindow(url, handleDisplayInfoSuccess);	
}
var handleDisplayInfoSuccess = function(responseObj){
	showPopUpDialogWindow(responseObj, "Display Information", saveDisplayInfo, handleCancel);	
}
var saveDisplayInfo = function(){
	var parent = document.getElementById("attendeeqlist");
	parent.innerHTML  = "";
	var selectBox = document.getElementById("selattendeeattribs");	
	for (var i = 0; i < selectBox.options.length; i++) {
		var obj1 = createHiddenTextElement(i,"value",selectBox.options[i].text);			
		var obj2 = createHiddenTextElement(i,"key",selectBox.options[i].value);
		parent.appendChild(obj1);
		parent.appendChild(obj2);
	}
	submitFormAndCloseWindow('attendeelist', '');	
}
function createHiddenTextElement(index,etype,val){
	var pname=document.createElement("input");
	pname.type="hidden";
	pname.name="optionsList["+index+"]."+etype;	
	pname.value=val;
	return pname;
}

YAHOO.namespace("displayinfo");
YAHOO.util.Event.onDOMReady( function() {
		// Remove progressively enhanced content class, just before creating the
		// module
		YAHOO.util.Dom.removeClass("displayinfo", "yui-pe-content");
		// Instantiate the Dialog
		if (!YAHOO.displayinfo.wait) {
        // Initialize the temporary Panel to display while waiting for external content to load
            YAHOO.displayinfo.wait = 
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
            YAHOO.displayinfo.wait.setHeader("Processing...");
            YAHOO.displayinfo.wait.setBody("<img src=\"../images/loading.gif\"/>");
            YAHOO.displayinfo.wait.render(document.body);
        }			
	});
var NS4 = (navigator.appName == "Netscape" && parseInt(navigator.appVersion) < 5);
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

var currentDivId="";
var currentEid="";
function setWebPath(fullPath,uploadurl,photoname){
	if(document.getElementById(currentDivId))
	document.getElementById(currentDivId).value=fullPath;
	YAHOO.ebee.popup.dialog1.cancel();
	if(document.getElementById("uploadurl"))
	if(document.getElementById("uploadurl")){
	document.getElementById('uploadurl').value=uploadurl;
	}
	
	if(document.getElementById("photoname"))
	if(document.getElementById("photoname")){
	document.getElementById('photoname').value=photoname.substring(0,photoname.lastIndexOf("."));
	}
	
	if(document.getElementById("imgdisplay"))
	document.getElementById("imgdisplay").innerHTML="<img src="+fullPath+"></img>";
	inserteventphotoURL(currentEid);
}

function closeFileUploadWindow(){
	YAHOO.ebee.popup.dialog1.cancel();
}
function getImageURL(divId,eid){
    YAHOO.ebee.popup.wait.show();
	currentDivId=divId;
	currentEid=eid;
	url='../membertasks/ImageUpload';
	//YAHOO.ebee.popup.wait.hide();
	showYUIinIframe("Upload Image",url,400,200);
	//popupwindow('../membertasks/ImageUpload','Map','300','300');
}
function deletePhotoURL(event,jsonObj){	
	var jsonData = eval(jsonObj);
	var eid = jsonData.eid;		
	var url="EventPageContent!deletePhotoURL?eid="+eid;
	var params={};
	submitContentFormAndReload(url, params);
	
}
function getLogoAndMessage(event,jsonObj){	
	var jsonData = eval(jsonObj);
	var eid = jsonData.eid;	
	var url="EventPageContent!getlogoandmessage?eid="+eid;
	loadURLBasedWindow(url, handleSuccess);
}
var handleSuccess = function(responseObj) {	
	showPopUpDialogWindow(responseObj, "Logo & Message", insertlogoandmessage, handleCancel);	
}
function getEventPassword(event,jsonObj){	
	var jsonData = eval(jsonObj);
	var eid = jsonData.eid;	
	_powertype=jsonData.powertype;
	var curlevel=jsonData.curlevel;
	var curfee=jsonData.curfee;
	
	var cardrequired='';
	if(_powertype=='RSVP' && curlevel == 150 && curfee == 0)
	  cardrequired='no';
	
	var url='SpecialFee!getCCStatus?eid='+eid;
	if(_powertype=='RSVP' && cardrequired!='no'){
      new Ajax.Request(url, {
    	       method: 'post',
    	        onSuccess: function(t){
			    data=t.responseText;
			    var ccjson=eval('('+data+')');
		        var cardstatus=ccjson.cardstatus;
		        var userid=ccjson.userid;
		        if(cardstatus=='Y'){
		        	var url="EventPageContent!geteventpassword?eid="+eid;
					loadURLBasedWindow(url, handlePasswordSuccess);
		        }else{
		          _eventid=eid;	//global variable declared in speciafee.js
		          _purpose='EventPageContent';//global variable declared in speciafee.js
		          var url='/main/payments!getPaymentScreen?processType=vault&amount=1&refId='+userid+'&purpose=invoice_hold_eventpassword';
                 // loadURLBasedWindow(url,handlesuccessccform);
                 

	             getccContent(userid,'invoice_hold_eventpassword'); 
	 
			    }
			 } 
    	});
	}else{
		var url="EventPageContent!geteventpassword?eid="+eid;
		loadURLBasedWindow(url, handlePasswordSuccess);
	}
	
	
	
	}
var handlePasswordSuccess = function(responseObj) {
	showPopUpDialogWindow(responseObj, "Password Protection", insertpwd, handleCancel);		
}
function getExternalPhotoURL(event,jsonObj){
    YAHOO.ebee.popup.wait.show();	
	var jsonData = eval(jsonObj);
	var eid = jsonData.eid;
	var url="EventPageContent!getexternalphotourl?eid="+eid;
	YAHOO.ebee.popup.wait.hide();
	loadURLBasedWindow(url, handleExternalURLSuccess);
	}
var handleExternalURLSuccess = function(responseObj) {	
	showPopUpDialogWindow(responseObj, "External Photo URL", handleExternalURL, handleCancel);	
}
function handleExternalURL(){
	document.getElementById('errors').innerHTML="";
	$("externalphoto").request({
		onSuccess:function(t){
			 var result=t.responseText;
			    if(result.indexOf("errorMessage")>-1)
			    document.getElementById('errors').innerHTML=result;
			    else
			    window.location.reload(true);	
		},
		onFailure:function(){
				alert("fail");
		}
	});
}

function updateSocialnw(){
	var ptype=document.getElementById("ptype").value;
	var clevel=document.getElementById("clevel").value;
	var eventid=document.getElementById("eid").value;
	
	if(ptype=='RSVP' && clevel=='90'){
	  if(document.getElementById("twitter").checked || document.getElementById("fblike").checked || document.getElementById("googleplusone").checked || document.getElementById("fbcomment").checked || document.getElementById("fbattending").checked)
		{
		  specialFee(eventid,'150','SocialNetworking','RSVP');
		}
	  else
		  submitSocialNetworking(); 
		
	}else
		submitSocialNetworking();
	
	}

function changeCurrentLevel(){
	document.getElementById('clevel').value='150';
}

function submitSocialNetworking(){
    	var x=processFacebookId($('facebookeventid').value);
    	if(x){
    	$("socialnwForm").request({
    		
    		onFailure: function() { YAHOO.displayinfo.wait.hide(); alert("error");},
            onSuccess: function(t) {
            	if(!isValidActionMessage(t.responseText)){return;}
            	var msg=t.responseText;
      	      	if(msg.indexOf("You have no cards")>-1){
      	      		 _powertype='RSVP'//global variable declared in speciafee.js
      	      		 _purpose='EventPageContentSocial';//global variable declared in speciafee.js
    	  	         var url='/main/payments!getPaymentScreen?processType=vault&amount=1&refId='+_mgrId+'&purpose=invoice_hold_socialnetworking';
    	           //  loadURLBasedWindow(url,handlesuccessccform);
    	       
    	        getccContent(_mgrId,'invoice_hold_socialnetworking');      
    	             
                 }else{
    				var fbeid=$('facebookeventid').value;
    				fbeid=fbeid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
    				$('facebookeventid').value=fbeid;
    			    $("fbeventidvalidationmessage").hide();
    				$("UpdateMessage").hide();
    	        	$("socialNetworkUpdateMessage").update(t.responseText);
            	}
            	YAHOO.displayinfo.wait.hide();
            }
    	});
    	}
    }

function editquestionfinal(eid){
	var url = 'AttendeeListDisplayFields!customAttribs?eid='+eid;
	loadURLBasedWindow(url, handleDisplayAttribsSuccess);
}
function processFacebookId(facebookeventid) {
	 var msg = "Please enter  valid Facebook event id only.";
	 //var msg1="There are some errors";
	 var fbeid=$('facebookeventid').value;
	 var inpVal = Number($('facebookeventid').value);
	  if($('fbattending').checked){
	  if(fbeid=='')
	  {
		  $("fbeventidvalidationmessage").show();
	        $("fbeventidvalidationmessage").update(msg);
	        $("UpdateMessage").innerHTML="";
	        $("UpdateMessage").show();
	        //$("UpdateMessage").update(msg1);
	        $("socialNetworkUpdateMessage").innerHTML="";
	        return false;
	   }
	  }
	   if(isNaN(inpVal)) {
		  try{
	            var err = new Error(msg);
	            if (!err.message) {
	                err.message = msg;
	            }
	            throw err;
	        }
	       catch (e) {
	    	 $("fbeventidvalidationmessage").show();
	         $("fbeventidvalidationmessage").update(e.message);
	         $("UpdateMessage").innerHTML="";
	         $("UpdateMessage").show();
	         //$("UpdateMessage").update(msg1);
	         $("socialNetworkUpdateMessage").innerHTML="";
	         
	    	facebookeventid.focus();
	        facebookeventid.select();
	    }
	   }
	   else
		   return true;
}
	/*   function processFanpageId(fanpageid)
	   { 
		   var fpeid=$('fanpageid').value;
		   var msg="please enter valid Facebook Fan page id only.";
		   var msg1="There are some errors";
		   var inpVal=Number($('fanpageid').value);
		   if($('fbfan').checked){
	     if(fpeid=='')
		  {
			    $("fanpageidvalidationmessage").show();
		        $("fanpageidvalidationmessage").update(msg);
		        $("UpdateMessage").innerHTML="";
		        $("UpdateMessage").show();
		        $("UpdateMessage").update(msg1);
		        $("socialNetworkUpdateMessage").innerHTML="";
		        return false;
		   }
		  }
          if(isNaN(inpVal)) {
			  try{
		            var err = new Error(msg);
		            if (!err.message) {
		                err.message = msg;
		            }
		            throw err;
		        }
		       catch (e) {
		    	 $("fanpageidvalidationmessage").show();
		         $("fanpageidvalidationmessage").update(e.message);
		         $("UpdateMessage").innerHTML="";
		         $("UpdateMessage").show();
			     $("UpdateMessage").update(msg1);
		         $("socialNetworkUpdateMessage").innerHTML="";
		         fanpageid.focus();
		         fanpageid.select();
		    }
		   }
	   
	  else
		   return true;

	}*/
	

var handleDisplayAttribsSuccess = function(responseObj){
	showPopUpDialogWindow(responseObj, "Display Information", saveAttribs, handleCancel);		
	}
var saveAttribs = function(){
	var parent = document.getElementById("attendeeqlist");
	parent.innerHTML  = "";
	var selectBox = document.getElementById("selattribs");	
	for (var i = 0; i < selectBox.options.length; i++) {
		var obj1 = createHiddenTextElement(i,"value",selectBox.options[i].text);			
		var obj2 = createHiddenTextElement(i,"key",selectBox.options[i].value);
		parent.appendChild(obj1);
		parent.appendChild(obj2);
	}
	document.getElementById("selattribs").selectedIndex = -1
	document.getElementById("attribs").selectedIndex = -1
	submitFormAndCloseWindow('attribslistform', '');
}
function getccContent(rfid,purp)
{
  var Msg=getPopMsg();
  var setkey= new callPaykey();
  setkey.setCallbackurl("/main/myaccount/home!insertManagerccData");
  setkey.setPurpose(purp);
  setkey.setPaymode("vault");
  setkey.setMessage(Msg);
  setkey.setAmount("1.00");
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
function call(elem)
{
var sel = elem.selectedIndex;
var ind=elem.options[sel].index;
var len=elem.length;
if(ind==0)
document.getElementById('up').style.opacity='0.4';
else
document.getElementById('up').style.opacity='1.0';
if(ind==len-1)
document.getElementById('down').style.opacity='0.4';
else
document.getElementById('down').style.opacity='1.0';
}

function enablePasswordProtection(event,jsonObj){
	var jsonData = eval(jsonObj);
	var eventid = jsonData.eid; 
	var pwtype=jsonData.powertype;
	if(pwtype=='RSVP')
	specialFee(eventid,'150','PasswordProtection','RSVP');
	else
	specialFee(eventid,'200','PasswordProtection','Ticketing');	
}
/*function editvolumetickets(eid){
	var url = 'AttendeeListDisplayFields!volumeTickets?eid='+eid;
	loadURLBasedWindow(url, handleDisplayVolumeTickets);
}

var handleDisplayVolumeTickets = function(responseObj){
	showPopUpDialogWindow(responseObj, "Select Tickets", saveVolTickets, handleCancel);		
}

var saveVolTickets = function(){
	var parent = document.getElementById("volticketslist");
	parent.innerHTML  = "";
	var selectBox = document.getElementById("voltickets");
	for (var i = 0; i < selectBox.options.length; i++) {
		var obj1 = createHiddenTextElement(i,"value",selectBox.options[i].text);			
		var obj2 = createHiddenTextElement(i,"key",selectBox.options[i].value);
		parent.appendChild(obj1);
		parent.appendChild(obj2);
	}
	document.getElementById("selvoltickets").selectedIndex = -1
	document.getElementById("voltickets").selectedIndex = -1
	submitFormAndCloseWindow('volticketsform', '');
}*/
