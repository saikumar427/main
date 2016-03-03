function init(){
	//alert("init");
//var rsvpsettingsupdatebtn = new YAHOO.widget.Button("rsvpsettingsupdatebtn",{onclick:{fn:updateRSVPSettings}});
document.getElementById('atttype').disabled=true;
if(document.getElementById('notsurelimit').value==0){
	document.getElementById('notsurelimit').value=1;
}
checkRadio();
checknotsure();
}


function updateRSVPSettings(){
//YAHOO.ebee.popup.wait.show();
parent.loadingPopup();
$.ajax({
	url : 'RSVPSettings!updateRsvp',
	type : 'POST',
	data: $('#rsvpsettingsform').serialize(),
	success : function(t){
		if(!isValidActionMessage(t)){
   			return;
    	}
		//alert(t);		
			parent.hidePopup();
  			$('#rsvpStatusMsg').html(t);
			
  		
  			if(t.indexOf('statusMessageBox')>-1){
  				notification(t,'success');
  				$('#rsvpStatusMsg').hide();
  				/*$('#rsvpStatusMsg').removeClass('alert alert-danger');
  			   	$('#rsvpStatusMsg').addClass('alert alert-success');*/
  			}else{
  					
  				/*$('#rsvpStatusMsg').removeClass('alert alert-success');*/ 
  				/*$('#rsvpStatusMsg').addClass('alert alert-danger');*/
  				$('#rsvpStatusMsg').css('height','');
  				$('#rsvpStatusMsg').show();
  				//$('#rsvpStatusMsg').append("<button type='button' style='margin-top: -20px;' class='close close-notification'><span aria-hidden='true'>x</span><span class='sr-only'>Close</span></button>");
  			}  			
  			
  			
  			 $('html, body').animate({ scrollTop: $("#rsvpStatusMsg").height()}, 1000);
	}
	
});
}		
function checkRadio(){
	if(document.getElementById('radio1').checked)
		document.getElementById('rsvplimit').disabled=true;
	else
		document.getElementById('rsvplimit').disabled=false;
}
function checknotsure(){
	if(document.getElementById('notsure').checked)
		document.getElementById('notsurelimit').disabled=false;
	else
		document.getElementById('notsurelimit').disabled=true;
}
