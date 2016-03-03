var _purpose='';
var _powertype='';
function updateSettingsFunction() {
	var l = document.getElementById('selevents');
	var upCEvent=document.getElementsByName("userThemes.upcomingEvents");
	for (var i = 0; i < upCEvent.length; i++) {
		if(upCEvent[i].value=='Selected' && upCEvent[i].checked){
			if (l.length==0) {
				alert("Please Select Atleast One Event");
				return false;
			}		
		}
	}
	addselEventstoForm();
	
	YAHOO.ebee.popup.wait.show();
	$('EventsPageContent').request({
        onFailure: function() { YAHOO.ebee.popup.wait.hide(); alert("error"); },
        onSuccess: function(t) {
	        var result=t.responseText;
	        if(!isValidActionMessage(result)) return;
	        if(result.indexOf("success")>-1){
	        	YAHOO.ebee.popup.wait.hide();
	        	$("updatedEventsMsg").update('Updated');
	        } 
        }
    });
	
	
}
var addselEventstoForm = function(){
	var parent = document.getElementById("finalselevents");
	parent.innerHTML  = "";
	var selectBox = document.getElementById("selevents");
	for (var i = 0; i < selectBox.options.length; i++) {
		var obj1 = createHiddenTextElement(i,"value",selectBox.options[i].text);			
		var obj2 = createHiddenTextElement(i,"key",selectBox.options[i].value);
		parent.appendChild(obj1);
		parent.appendChild(obj2);
	}
}
function createHiddenTextElement(index,etype,val){
	var pname=document.createElement("input");
	pname.type="hidden";
	pname.name="selEvents["+index+"]."+etype;	
	pname.value=val;
	return pname;
}

function updateCustomization() {
	var title = document.forms['EventsPageCustomize'].elements['boxofftitle'].value;
	title = trim(title);
	if (title == "") {
		alert("Please enter Title");
		return false;
	}
	
	        
	        $('EventsPageCustomize').request({
               onFailure: function() { YAHOO.ebee.popup.wait.hide(); alert("error"); },
               onSuccess: function(t) {
	              var result=t.responseText;
	              if(!isValidActionMessage(result)) return;
	              if(result.indexOf("don't askcard")>-1){
	              YAHOO.ebee.popup.wait.hide();
	              $("updatedCustomMsg").update('Updated');
	              }else{
	              var mgrid=document.getElementById('managerid').value;
 	              _purpose='BoxOffice Settings';
 	              _powertype='';
 	               getcc(mgrid,'boxoffice settings');
	             }
        }
    });
}
function getcc(rfid,purp)
{
  var Msg="Please fill out your credit card information to activate your account, this is required to prevent hosting fake events using our system!";
  var setkey= new callPaykey();
  setkey.setCallbackurl("/main/myaccount/home!insertManagerccData?refId="+rfid);
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

function onsucessclose()
	{
	closeEBpopup();	
	responseDatacc();
	}
