function getProfileData(tid,eid){
CtrlWidgets=[];
ctrlwidget=[];
new Ajax.Request('/embedded_reg/getprofilesblock.jsp?timestamp='+(new Date()).getTime(), {
method: 'get',
parameters:{eid:eid,tid:tid},
onSuccess: ProfileblockResponse,
onFailure:  failureJsonResponse
});
}

function ProfileblockResponse(response){
var data=response.responseText;
var responsejsondata=eval('(' + profileJsondata + ')');
document.getElementById('registration').style.display='none';
document.getElementById('profile').style.display='block';

if(document.getElementById('paymentsection'))
document.getElementById('paymentsection').style.display='none';
hideTicLoadingImage();
document.getElementById('profile').innerHTML=response.responseText;
if(document.getElementById('pageheader')){
if(headers.profilepage!=''){
document.getElementById('pageheader').style.display='block';
document.getElementById('pageheader').innerHTML='<a name="ticketing"></a>'+headers.profilepage;
}
else{
document.getElementById('pageheader').style.display='none';
}

}
ticketpageclickcount=0;
var profilecount=responsejsondata.profilecount;
var tickets=responsejsondata.tickets;
var questionsobj=responsejsondata.questions;
var profilecount=responsejsondata.profilecount;
var questions='';
var buyerinfo=responsejsondata.buyerquest;
for(var index=0;index<tickets.length;index++){
ticketid=tickets[index];
var count=profilecount[ticketid];
questions=questionsobj[ticketid];

for(var p=1;p<=count;p++){
for(i=0;i<questions.length;i++){
var qid=questions[i];
putWidgetIdeal(ticketid, qid, p);
}
}
}
for(i=0;i<buyerinfo.length;i++){
var qid=buyerinfo[i];
putWidgetIdeal('buyer', qid, '1');
}
if(document.getElementById('enablepromotion')){

document.getElementById('enablepromotion').checked=promotionenable;
if(promotionenable){
document.getElementById('enablepromotion').value="yes";
}

}
if(!$('seatingsection')){gettkttimer();}
if($('seatingsection')){
	getseatingtimer();
}

if(document.getElementById('q_buyer_fname_1')){
document.getElementById('q_buyer_fname_1').focus();
if(CtrlWidgets["buyer_fname_1"].GetValue()=="")
document.getElementById('q_buyer_fname_1').value=fname;
}
if(document.getElementById('q_buyer_lname_1')){
if(CtrlWidgets["buyer_lname_1"].GetValue()=="")
document.getElementById('q_buyer_lname_1').value=lname;
}
if(document.getElementById('q_buyer_email_1')){
if(CtrlWidgets["buyer_email_1"].GetValue()=="")
document.getElementById('q_buyer_email_1').value=email;
}
//updatecurrentaction("profile page");
if(eventid=='125114992')
limitTextLength();
}
function limitTextLength(){	jQuery('textarea[name^="q_"]').each(function(k,v){ 
	var rows=jQuery(this).attr('rows');	var cols=jQuery(this).attr('cols');		
	jQuery(this).attr('maxlength',(Number(cols)*Number(rows)));	
	});
jQuery('textarea[name^="q_"]').unbind('keypress');
jQuery('textarea[name^="q_"]').bind('keypress',function(e){ 
	var rows=jQuery(this).attr('rows');	var cols=jQuery(this).attr('cols');	
	var text=jQuery(this).val();
	try{
		if((Number(cols)*Number(rows))<=text.length && e.which!=8){
	alert("Limit characters to "+ Number(cols)*Number(rows));	return false;
		}}catch(err){}});	
}


function getTicketsPage(){
if(document.getElementById('enablepromotion')){

promotionenable=document.getElementById('enablepromotion').checked;
if(document.getElementById('enablepromotion').checked){
document.getElementById('enablepromotion').value="yes";
}

else{
document.getElementById('enablepromotion').checked=promotionenable;
document.getElementById('enablepromotion').value='';
}
}
getresponses();
document.getElementById('registration').style.display='block';
hideTicLoadingImage();
if(document.getElementById("eventdate")){
	if(isPriority)
		document.getElementById('eventdate').disabled=true;
	else
		document.getElementById("eventdate").disabled=false;
}
document.getElementById('profile').style.display='none';
if(document.getElementById('pageheader')){
document.getElementById('pageheader').style.display='block';
document.getElementById('pageheader').innerHTML=headers.ticketspage;
}
if($('seatingsection')){
	if($('paymentsection')){
		$('paymentsection').hide();
		$('paymentsection').innerHTML='';
	}
	if($('ticket_div')){
		$('ticket_div').hide();
	}
	if($('ticket_timer')){
		$('ticket_timer').remove();
		clearTimeout(reg_timeout);
	
	}
	ticketpageclickcount=0;
}
if($('ticket_timer')){
		$('ticket_timer').remove();
		clearTimeout(reg_timeout);
	
	}
updatecurrentaction("tickets page");
}