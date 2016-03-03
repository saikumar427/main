

 var final_discount="";
 var timestamp=new Date();
	
function deleteGroupTicket(func,event,jsonObj){
	var groupticketid = jsonObj.groupticketid;
	var eid=jsonObj.eid;
	var agree;
	agree=confirm("Do you want to  delete GroupTicket");	
	if (agree){	
		var url='ManageGroupDealTickets!delete?eid='+eid+'&groupticketid='+groupticketid;
		callURLandReload(url);
    }
}


function showtickets(eid){
	var url='GroupTicketsShowHide?eid='+eid;
	loadURLBasedWindow(url, displayGroupTicketsShowHideSuccess);	
}
var displayGroupTicketsShowHideSuccess = function(responseObj){
	showPopUpDialogWindow(responseObj, "GroupTickets", submitGroupTicketsShowHideForm, handleCancel);	
}
var submitGroupTicketsShowHideForm=function(){
	submitFormAndReload('groupticketshowhideform', '');		
}

function checkAll(field){
	for (i = 0; i < field.length; i++)
		field[i].checked = true ;
	}
	function uncheckAll(field){
	for (i = 0; i < field.length; i++)
		field[i].checked = false ;
	}
	
	function ConfirmationPage(eid){
	     //  alert("in confirmationpage::"+eid);
	       var url="GroupTicketConfirmationPageSettings?eid="+eid;
	       YAHOO.ebee.popup.wait.show();
	       var dynatimestamp = new Date();
	       url += '&dynatimestamp='+dynatimestamp.getTime();
	   	   var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getConfirmationPageSuccess,failure:getFailure});
	   	
	}
	var getConfirmationPageSuccess = function(responseObj){
	     
		showPopUpDialogWindow(responseObj, "Confirmation Page", saveconfirmationpage, handleCancel);
}
	 var getFailure = function(){
		alert("error");
	}
	 var saveconfirmationpage=function(){
			
			submitFormAndReload('confirmationpage', 'confirmationpageerrormessages');	
		}
		 
	 function OnPurchase(eid){
	      
	       var url="GroupTicketConfirmationEmail!onPurchase?eid="+eid;
	       YAHOO.ebee.popup.wait.show();
	       var dynatimestamp = new Date();
	       url += '&dynatimestamp='+dynatimestamp.getTime();
	   	   var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getOnPurchaseSuccess,failure:getFailure});
	   	
	}
	var getOnPurchaseSuccess = function(responseObj){
	     
		showPopUpDialogWindow(responseObj, "On Purchase", savepurchase, handleCancel);
}
	 var savepurchase=function(){
			
			submitFormAndReload('onpurchaseemailform', '');	
		}

	 function OnTriggerReach(eid){
	     
	       var url="GroupTicketConfirmationEmail!onTriggerReach?eid="+eid;
	       YAHOO.ebee.popup.wait.show();
	       var dynatimestamp = new Date();
	       url += '&dynatimestamp='+dynatimestamp.getTime();
	   	   var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getOnTriggerReachSuccess,failure:getFailure});
	   	
	}
	var getOnTriggerReachSuccess = function(responseObj){
	     
		showPopUpDialogWindow(responseObj, "On TriggerReach", savetriggerreach, handleCancel);
}
	 var savetriggerreach=function(){
			
			submitFormAndReload('ontriggerreachemailform', '');	
		}

	 function OnTriggerFail(eid){
	     
	       var url="GroupTicketConfirmationEmail!onTriggerFail?eid="+eid;
	       YAHOO.ebee.popup.wait.show();
	       var dynatimestamp = new Date();
	       url += '&dynatimestamp='+dynatimestamp.getTime();
	   	   var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getOnTriggerFailSuccess,failure:getFailure});
	   	
	}
	var getOnTriggerFailSuccess = function(responseObj){
	     
		showPopUpDialogWindow(responseObj, "On TriggerFail", savetriggerfail, handleCancel);
}
	
	 var savetriggerfail=function(){
			
			submitFormAndReload('ontriggerfailemailform', '');	
		}

/*------------------------------------------------------------------------------------------------------------*/
	
	
	
	
	 	function addGrpTicket(event,jsonObj){
	 		var eid = jsonObj.eid;
	 		var url='GroupTicketDetails?eid='+eid;
	 		loadURLBasedWindow(url, displayTicketScreen);	
	 	}
	 	function editGroupTicket(func,event,jsonObj){
	 		var groupticketid = jsonObj.groupticketid;
	 		var eid=jsonObj.eid;
	 		var url='GroupTicketDetails!edit?eid='+eid+'&ticketId='+groupticketid;
	 		loadURLBasedWindow(url, displayTicketScreen);
	 	}
	 	var displayTicketScreen = function(responseObj) {	
	 		showPopUpDialogWindow(responseObj, "Group Ticket Details", submitTicketingForm, handleCancel);
	 	}
	 	var submitTicketingForm = function(){
	 		submitTicketFormAndReload('grptcktform', 'errormessages');	
	 	}
	function submitTicketFormAndReload(formId, msgDivid){
	YAHOO.ebee.popup.wait.show();
	$(formId).request({
	        onFailure: function() {YAHOO.ebee.popup.wait.hide(); $(msgDivid).show();$(msgDivid).update('Failed to get results'); },
	        onSuccess: function(t) {
	        	
		        var result=t.responseText;
		        var jsondata=eval('('+result+')');
		        if(jsondata.errorexist==1)
		        {
		        	YAHOO.ebee.popup.wait.hide();
			        $(msgDivid).show();
			        prepareErrorMessage(msgDivid,jsondata);
		        }
		        else{
		        YAHOO.ebee.popup.dialog.cancel();
		        YAHOO.ebee.popup.wait.hide();
		        window.location.reload();
		        }
		        }
	    });
	}
	 	function prepareErrorMessage(msgDivid,jsondata){
	 	var errorMsg="";
	 	if(jsondata.name=="empty")
	 		errorMsg=errorMsg+"<li>Group Ticket Name is empty";
	 	if(jsondata.desc=="empty")
	 		errorMsg=errorMsg+"<li>Group Ticket Description is empty";
	 	if(jsondata.price=="empty")
	 		errorMsg=errorMsg+"<li>Group Ticket Price is empty";
	 	else if(jsondata.price=="numeric")
	 		errorMsg=errorMsg+"<li>Group Ticket Price should be numeric";
	 	else if(jsondata.price=="negative")
	 		errorMsg=errorMsg+"<li>Group Ticket Price should be minimum 1";
	 	if(jsondata.discount=="empty")
	 		errorMsg=errorMsg+"<li>Group Discount is empty";
	 	else if(jsondata.discount=="numeric")
	 		errorMsg=errorMsg+"<li>Group Discount should be numeric";
	 	else if(jsondata.discount=="mindisc")
	 		errorMsg=errorMsg+"<li>Group Discount should not be less than 25%";
	 	else if(jsondata.discount=="maxdisc")
	 		errorMsg=errorMsg+"<li>Group Discount should not be more than 100%";
	 		
	 	if(jsondata.triggerqty=="empty")
	 		errorMsg=errorMsg+"<li>Trigger Quantity is empty";
	 	else if(jsondata.triggerqty=="numeric")
	 		errorMsg=errorMsg+"<li>Trigger Quantity should be numeric";
	 	else if(jsondata.triggerqty=="negative")
	 		errorMsg=errorMsg+"<li>Trigger Quantity should be minimum 1";

	 	if(jsondata.minqty=="empty")
	 		errorMsg=errorMsg+"<li>Minimum Purchase Quantity Per Registration is empty";
	 	else if(jsondata.minqty=="numeric")
	 		errorMsg=errorMsg+"<li>Minimum Purchase Quantity Per Registration should be numeric";
	 	else if(jsondata.minqty=="negative")
	 		errorMsg=errorMsg+"<li>Minimum Purchase Quantity Per Registration should be minimum 1";

	 	if(jsondata.maxqty=="empty")
	 		errorMsg=errorMsg+"<li>Maximum Purchase Quantity Per Registration is empty";
	 	else if(jsondata.maxqty=="numeric")
	 		errorMsg=errorMsg+"<li>Maximum Purchase Quantity Per Registration should be numeric";
	 	else if(jsondata.maxqty=="negative")
	 		errorMsg=errorMsg+"<li>Maximum Purchase Quantity Per Registration should be minimum 1";

	 	if(jsondata.minmax=="greater")
	 		errorMsg=errorMsg+"<li>Minimum Purchase Quantity is Greater than Maximum Purchase Quantity";
	 		
	 	if(jsondata.posttrigtype==2){
	 		if(jsondata.upperlimit=="empty")
	 		errorMsg=errorMsg+"<li>Ticket Sales Count is empty";
	 	else if(jsondata.upperlimit=="numeric")
	 		errorMsg=errorMsg+"<li>Ticket Sales Count should be numeric";
	 	else if(jsondata.upperlimit=="negative")
	 		errorMsg=errorMsg+"<li>Ticket Sales Count should be minimum 1";	
	 	}else if(jsondata.posttrigtype==3){
	 			if(jsondata.cycleslimit=="empty")
	 		errorMsg=errorMsg+"<li>Repeat Count is empty";
	 	else if(jsondata.cycleslimit=="numeric")
	 		errorMsg=errorMsg+"<li>Repeat Count should be numeric";
	 	else if(jsondata.cycleslimit=="negative")
	 		errorMsg=errorMsg+"<li>Repeat Count should be minimum 1";	
	 	
	 	}
	 	if(jsondata.startdate=="isbefore")
	 		errorMsg=errorMsg+"<li>Start Date should be later than Current Date";
	 	if(jsondata.failtype==1){
	 			if(jsondata.faildiscount=="empty")
	 		errorMsg=errorMsg+"<li>Trigger Fail Discount  is empty";
	 	else if(jsondata.faildiscount=="numeric")
	 		errorMsg=errorMsg+"<li>Trigger Fail Discount should be numeric";
	 	else if(jsondata.faildiscount=="negative")
	 		errorMsg=errorMsg+"<li>Trigger Fail Discount should be minimum 1";	

	 	}
	 		document.getElementById(msgDivid).innerHTML=errorMsg;
	 	}
	 	function changeType(){
	 	/*var selectedtype=document.getElementById("absoluteradio").checked;
	 	if(document.getElementById("absoluteradio").checked){
	 	document.getElementById('discountprefixlabel').style.display='inline';
	 	document.getElementById('discountsufixlabel').style.display='none';
	 	}else{*/
	 	document.getElementById('discountprefixlabel').style.display='none';
	 	document.getElementById('discountsufixlabel').style.display='inline';
	 	//}
	 	}
	 	function extractNumber(obj, decimalPlaces, allowNegative)
	 	{
	 		var temp = obj.value;
	 		
	 		// avoid changing things if already formatted correctly
	 		var reg0Str = '[0-9]*';
	 		if (decimalPlaces > 0) {
	 			reg0Str += '\\.?[0-9]{0,' + decimalPlaces + '}';
	 		} else if (decimalPlaces < 0) {
	 			reg0Str += '\\.?[0-9]*';
	 		}
	 		reg0Str = allowNegative ? '^-?' + reg0Str : '^' + reg0Str;
	 		reg0Str = reg0Str + '$';
	 		var reg0 = new RegExp(reg0Str);
	 		if (reg0.test(temp)) return true;

	 		// first replace all non numbers
	 		var reg1Str = '[^0-9' + (decimalPlaces != 0 ? '.' : '') + (allowNegative ? '-' : '') + ']';
	 		var reg1 = new RegExp(reg1Str, 'g');
	 		temp = temp.replace(reg1, '');

	 		if (allowNegative) {
	 			// replace extra negative
	 			var hasNegative = temp.length > 0 && temp.charAt(0) == '-';
	 			var reg2 = /-/g;
	 			temp = temp.replace(reg2, '');
	 			if (hasNegative) temp = '-' + temp;
	 		}
	 		
	 		if (decimalPlaces != 0) {
	 			var reg3 = /\./g;
	 			var reg3Array = reg3.exec(temp);
	 			if (reg3Array != null) {
	 				// keep only first occurrence of .
	 				//  and the number of places specified by decimalPlaces or the entire string if decimalPlaces < 0
	 				var reg3Right = temp.substring(reg3Array.index + reg3Array[0].length);
	 				reg3Right = reg3Right.replace(reg3, '');
	 				reg3Right = decimalPlaces > 0 ? reg3Right.substring(0, decimalPlaces) : reg3Right;
	 				temp = temp.substring(0,reg3Array.index) + '.' + reg3Right;
	 			}
	 		}
	 		
	 		obj.value = temp;
	 	}
	 	function blockNonNumbers(obj, e, allowDecimal, allowNegative)
	 	{
	 		var key;
	 		var isCtrl = false;
	 		var keychar;
	 		var reg;
	 			
	 		if(window.event) {
	 			key = e.keyCode;
	 			isCtrl = window.event.ctrlKey
	 		}
	 		else if(e.which) {
	 			key = e.which;
	 			isCtrl = e.ctrlKey;
	 		}
	 		
	 		if (isNaN(key)) return true;
	 		
	 		keychar = String.fromCharCode(key);
	 		
	 		// check for backspace or delete, or if Ctrl was pressed
	 		if (key == 8 || isCtrl)
	 		{
	 			return true;
	 		}

	 		reg = /\d/;
	 		var isFirstN = allowNegative ? keychar == '-' && obj.value.indexOf('-') == -1 : false;
	 		var isFirstD = allowDecimal ? keychar == '.' && obj.value.indexOf('.') == -1 : false;
	 		
	 		return isFirstN || isFirstD || reg.test(keychar);
	 	}
	 function calculatePrice(){
	 	var price=document.getElementById("price").value;
	 	var discount=document.getElementById("discount").value;
	 	if(price.length>0 && discount.length>0)
	 	{
	 		if(discount>=25 && discount<=100){
	 			calculate(price,discount);
	 		}	
	 	}
	 }
	 function calculate(price,discount){
	 	final_discount=discount;
	 	var eid=document.getElementById("eid").value;
	 	var url='GroupTicketDetails!calculatePrice?eid='+eid+'&price='+price+'&discount='+discount+'&t='+timestamp.getTime();
	  new Ajax.Request(url,{
	 method: 'get',
	 onSuccess: Calculateresponse,
	  onFailure:  failureJsonResponse
	 		 });
	 }
	 function Calculateresponse(response){
	 	var dprice=response.responseText;
	 	$("discountprice").update(final_discount+"% discount price $"+dprice);
	 }
	 function failureJsonResponse(){
	 }
	 function callDiscountHelp(){
	 	y=YAHOO.ebee.popup.dialog.cfg.getProperty("y")+30;
	 	x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	 	showPopupInPopup(gtdiscount_helptitle, getHelpContent('gtdiscount_helpcontent'),200,300,x,y);
	 }
	 function callTriggerQtyHelp(){
	 	y=YAHOO.ebee.popup.dialog.cfg.getProperty("y")+30;
	 	x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	 	showPopupInPopup(gttriggerqty_helptitle, getHelpContent('gttriggerqty_helpcontent'),200,300,x,y);
	 }