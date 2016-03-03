function getConfirmation(tid,eid){
if(document.getElementById("backgroundPopup"))
	document.getElementById("backgroundPopup").style.display='none';
if($('ticket_div')){
		$('ticket_div').hide();
	}
if($('ticketpoup_div')){
		$('ticketpoup_div').hide();
	}	
	if($('ticket_timer')){
		$('ticket_timer').remove();
		clearTimeout(reg_timeout);
	
	}
var venueid=$('venueid').value;
new Ajax.Request('/volume/embedded_reg/done.jsp?timestamp='+(new Date()).getTime(),{
method: 'get',
parameters:{eid:eid,tid:tid,eventdate:evtdate,seatingenabled:seating_enabled_tkt_wedget,venueid:venueid,pagein:pagein},
onSuccess: PrcocesConfirmationResponse,
onFailure:  failureJsonResponse
});
}