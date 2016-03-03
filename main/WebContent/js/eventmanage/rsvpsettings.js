function init(){
var rsvpsettingsupdatebtn = new YAHOO.widget.Button("rsvpsettingsupdatebtn",{onclick:{fn:updateRSVPSettings}});
loadHelpPanel("countperrsvphelppanel", "countperrsvphelp", "500px");
loadHelpPanel("rsvpoptionshelppanel", "rsvpoptionshelp", "500px");
loadHelpPanel("rsvpattcounthelppanel", "rsvpattcounthelp", "500px");
loadHelpPanel("notsurecounthelppanel", "notsurecounthelp", "500px");
document.getElementById('atttype').disabled=true;
if(document.getElementById('notsurelimit').value==0){
	document.getElementById('notsurelimit').value=1;
}
checkRadio();
checknotsure();
}
function updateRSVPSettings(){
YAHOO.ebee.popup.wait.show();
$('rsvpsettingsform').request({
  		onComplete: function(o){
  		if(!isValidActionMessage(o.responseText)){
   			return;
    	}
    		YAHOO.ebee.popup.wait.hide();
  			$('rsvpStatusMsg').update(o.responseText);
  			$('rsvpStatusMsg').show();
	  		  $('rsvpStatusMsg').scrollTo();
	 }
	})
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
