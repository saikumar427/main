function getPopMsg(){
      var title="";
      if(_purpose=='PaymentSettings')
      title='Please fill out your credit card information to activate your selected payment methods.';
      else if(_purpose=='AddAttendee' && _powertype=='Ticketing')
      title='Please fill out your credit card information to use this feature.';
      else if(_powertype=='RSVP')
      title='Please fill out your credit card information to use Pro RSVP features.';
      else{title="";}
      return title;
    }

function responseDatacc(){
       	        if(_purpose=='integrationwidget')
				window.location.href='/main/eventmanage/'+'IntegrationLinks?eid='+_eventid+'&purpose=widget';
				else if(_purpose=='integrationeventurl')
					window.location.href='/main/eventmanage/'+'IntegrationLinks?eid='+_eventid+'&purpose=eventurl';
				else if(_purpose=='AddAttendee' && _powertype=='Ticketing')
			    	window.location.href='/main/eventmanage/'+'AddAttendee?eid='+_eventid+'&purpose=manageradd';
			    else if(_purpose=='AddAttendee' && _powertype=='RSVP')
			    	window.location.href='/main/eventmanage/'+'AddAttendee?eid='+_eventid+'&purpose=manageradd&type=rsvp';
				else if(_purpose=='TransactionDetails' && _powertype=='RSVP')
					window.location.href='/main/eventmanage/'+'TransactionDetails?eid='+_eventid+'&type=rsvp';
				else if(_purpose=='Badges' && _powertype=='RSVP')
					window.location.href='/main/eventmanage/'+'Badges?eid='+_eventid+'&purpose=rsvpbadges';
				else if(_purpose=='ConfirmationPageSettings' && _powertype=='RSVP')
					window.location.href='/main/eventmanage/'+'ConfirmationPageSettings?eid='+_eventid+'&type=rsvp';
    	        else if(_purpose=='PaymentSettings')
    	        	updatePaymentSettings();
    	        else if(_purpose=='EventPageContentSocial')
    	        	updateSocialnw();
    	        else if(_purpose=='BoxOffice Settings')
    	            updateCustomization(); 	
    	        else if(_purpose=='CopyEvent')
    	            copyEventSnapshot('true',_eventid); 
    	        else if(_purpose=='CopyExistingEvent')
    	            copyEvent1();
    	        else if(_purpose=='ListEvent')   
    	           listEvent(_type,_isrecurring,_selectedtype);
    	        else if(_purpose=='EditEvent')
    	            listEvent(_type,_isrecurring,_selectedtype);
    	        else if(_purpose=='LooknFeel_HtmlCssLoad'){
    	        	// in looknfeel.js submit the form for htmlcustomization
				}
				else if(_purpose=='LooknFeel_HtmlCssSubmit'){
    	        	submitHtmlnCss(); // in looknfeel.js submit the form for htmlcustomization
				}
    	        else if(_purpose!='')
    	        	window.location.href='/main/eventmanage/'+_purpose+'?eid='+_eventid;
    	         else
    	        	window.location.reload();   
}


var handlesuccessccform=function(responseObj){
     showPopUpDialogWindow(responseObj, "Your Credit Card Information", submitCCForm, CancelPopUp);
     if(_purpose=='PaymentSettings')
       document.getElementById('headermsg').innerHTML='Please fill out your credit card information to activate your selected payment methods.';
     else if(_purpose=='AddAttendee' && _powertype=='Ticketing')
       document.getElementById('headermsg').innerHTML='Please fill out your credit card information to use this feature.';
     else if(_powertype=='RSVP')
     	document.getElementById('headermsg').innerHTML='Please fill out your credit card information to use Pro RSVP features.';
     else{}
    }

var submitCCForm=function(){
     //YAHOO.ebee.popup.wait.show();
     //YAHOO.ebee.popup.dialog.hideEvent.subscribe(function(){
		    // YAHOO.ebee.popup.wait.hide();
      //});
     var seqID=document.getElementById('seqId').value;
     var cardstatus=CCStatus();     
     //if(cardstatus){
     $('ccpaymentscreen').request({
        onFailure: function() { $('ccpaymenterror').update('Failed to get results') },
        onSuccess: function(t) {
           var msg=t.responseText;
           if(msg.indexOf("success")>-1){
            responseData(seqID); 
            }else if(msg=='error'){
           //YAHOO.ebee.popup.wait.hide();
		   $('ccpaymenterror').scrollTo();
		   $('ccpaymenterror').update('<font color="red">Card authentication failed. Invalid card or billing address.</font>');
           }else{
          //YAHOO.ebee.popup.wait.hide();
		  $('ccpaymenterror').scrollTo();
		  $('ccpaymenterror').update(t.responseText); 
          //$('ccpaymenterror').update('<font color="red">Card Authentication Failed. Invalid Card or Billing Address</font>');
	    }
	    }
    });
    //}
}
function responseData(seqID){
      url="/main/myaccount/home!insertManagerccData?seqId="+seqID;
      $.ajax({
    	  url : url,
    	  type : 'post',
    	  success : function(t){
    		  if(_purpose=='integrationwidget')
					window.location.href='IntegrationLinks?eid='+_eventid+'&purpose=widget';
				else if(_purpose=='integrationeventurl')
					window.location.href='IntegrationLinks?eid='+_eventid+'&purpose=eventurl';
				else if(_purpose=='AddAttendee' && _powertype=='Ticketing')
			    	window.location.href='AddAttendee?eid='+_eventid+'&purpose=manageradd';
			    else if(_purpose=='AddAttendee' && _powertype=='RSVP')
			    	window.location.href='AddAttendee?eid='+_eventid+'&purpose=manageradd&type=rsvp';
				else if(_purpose=='TransactionDetails' && _powertype=='RSVP')
					window.location.href='TransactionDetails?eid='+_eventid+'&type=rsvp';
				else if(_purpose=='Badges' && _powertype=='RSVP')
					window.location.href='Badges?eid='+_eventid+'&purpose=rsvpbadges';
				else if(_purpose=='ConfirmationPageSettings' && _powertype=='RSVP')
					window.location.href='ConfirmationPageSettings?eid='+_eventid+'&type=rsvp';
  	        else if(_purpose=='PaymentSettings')
  	        		updatePaymentSettings();
  	        	//window.location.href='ConfirmationPageSettings?eid='+_eventid+'&type=rsvp';
  	        else if(_purpose=='EventPageContentSocial')
  	                updateSocialnw();
  	        else if(_purpose!='')
  	        	window.location.href=_purpose+'?eid='+_eventid;
  	         else
  	        	window.location.reload();
    	  }
      });     
      
     
} 
 var CancelPopUp = function(){
 	//YAHOO.ebee.popup.wait.hide();
	//YAHOO.ebee.popup.dialog.cancel();
	if(_purpose=='PaymentSettings'){
	 checkboxFocus();
	 }else{
	  //window.location.reload();
	 }
}
function CloseAction()
{
 if(_purpose=='ListEvent'){
  document.getElementById('submitBtn2').style.display='none';
  document.getElementById('submitBtn').style.display='block';
 }
}

