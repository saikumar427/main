var trnsid="";
var boxcheck="false";
function getPaymentSection(tid,eid){
trnsid=tid;
var regtype=$('registrationsource').value;
var fbuid='';
if(regtype=='iPad')
	regtype='mobile';
if(fbavailable){
	FB.getLoginStatus(function(response) {
		if(response.authResponse){
			fbuid=response.authResponse.userID;
		}
	});}
	
	
new Ajax.Request('/volume/embedded_reg/paymentsection.jsp?timestamp='+(new Date()).getTime(),{
method: 'get',
parameters:{eid:eid,tid:tid,regtype:regtype,ntsenable:ntsdata["nts_enable"],referral_ntscode:ntsdata['referral_ntscode'],fbuid:fbuid,paymentmode:paymentmode,ticketid:ticketid,boxcheck:boxcheck},
onSuccess: PrcocesPaymentSectionResponse,
onFailure:  failureJsonResponse
});
}

function insertstatus(status)
{//alert("functioncomin"+trnsid);
		new Ajax.Request('/volume/embedded_reg/volume_statusupdated.jsp?timestamp='+(new Date()).getTime(),{
		method: 'get',
		parameters:{tid:trnsid,status:status}});
}

var clickcount=0;
function selectcheck(){
if($('vbmsg').checked==true)
{ boxcheck="true"; $('paymentblock').style.display="none";clickcount=0;}else{boxcheck="false";$('paymentblock').style.display="block";clickcount=2; }
 

}


function SubmitForm(tid,type,serveraddress){
var checkstatus="N";
if($('vbmsg'))
		{		if($('vbmsg').checked==true)
				checkstatus='Y';
				else
				{
				checkstatus='N';
				}
		}
		if($('stsvoid'))
		{	
		checkstatus='N';
		}
		 insertstatus(checkstatus);
//updatecurrentaction(type);

if($('paymentsection'))
		$('paymentsection').hide();
showTicLoadingImage("Loading...");

paymenttype=type;
if($('seatingsection')){
hidetimerpopup();
}
else
{

hidetimerpopupfortkt();}
if(clickcount>1)
{
hideimage_showpaysection();
return;
}
tid=document.getElementById('tid').value;
tranid=tid;
serveradd=serveraddress;
var eid=eventid;
if(paymenttype=='eventbee') {
/* parent.window.scrollTo(0,0);
document.getElementById('imageLoad').focus(); */
showTicLoadingImage("Loading...");
getEventbeecreditcardScreen(tid,eid);
}
else if(paymenttype=='paypal'){
if(paymentmode=='paypalx')
getPaypalXPaymentsPopUp(tid,eid,paymenttype);
else
getPaypalPaymentsPopUp(tid,eid,paymenttype);
hideimage_showpaysection();
}
else if(paymenttype=='google'){
getGooglePaymentsPopUp(tid,eid,paymenttype);
hideimage_showpaysection();
}
if(paymenttype=='ebeecredits'){
if(fbavailable)
getEventbeeCreditsPopUp(tid,eid,paymenttype);
else{
	alert("Facebook is not available. Please try another payment type");
	hideimage_showpaysection();
	clickcount=0;
	}
//hideimage_showpaysection();
}
else if(paymenttype=='other'||paymenttype=='nopayment'){
processRegistration(tranid,eventid,paymenttype);
}

}

function hideimage_showpaysection(){

hideTicLoadingImage();
if($('paymentsection'))
$('paymentsection').show();

}

function getEventbeecreditcardScreen(tid,eid){

/* if(document.getElementById("backgroundPopup"))
document.getElementById("backgroundPopup").style.display="block";
 */{
$('paymentsection').hide();
}
 
 //windowOpener(serveradd+'/embedded_reg/payment.jsp?tid='+tid+'&eid='+eid,'Payment_'+tid,'WIDTH=740,HEIGHT=600,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');
//windowOpener(serveradd+'/volume/embedded_reg/checkavailability.jsp?tid='+tid+'&eid='+eid+'&paytype=CC','Payment_'+tid,'WIDTH=740,HEIGHT=600,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');
var ifurl=serveradd+'/volume/embedded_reg/checkavailability.jsp?tid='+tid+'&eid='+eid+'&paytype=CC';
PrcocesCCSreenResponse(ifurl);
gPopupIsShown = true;
disableTabIndexes();
}
function successload()
{
   hideTicLoadingImage();
	if($('cciframe'))
     $('cciframe').show();
 document.getElementById('ccsection').style.display='block';
}

function PrcocesCCSreenResponse(ifurl){
insidepay=true;
/* if(!$('attendeeloginpopup')){
		var div=document.createElement("div");
		div.setAttribute('id','attendeeloginpopup');
		div.className='ticketunavailablepopup_div';
		jQuery('body').append(div);
	} */
	if($('ticket_timer'))
     $('ticket_timer').hide();
	document.getElementById('ccsection').style.display='none';
	var htmldata="<img src='/home/images/images/close.png' id='ebeecreditsclose' class='imgclose'  >";

	/*	var fileref=document.createElement('script');
		fileref.setAttribute("type","text/javascript");
		fileref.setAttribute("src", "http://www.localebee.com/home/js/widget/eventregistration.js");
		if (typeof fileref!="undefined")
			document.getElementsByTagName("head")[0].appendChild(fileref)*/
	 htmldata+="<iframe src='"+ifurl+"&resizeIFrame=true' id='cciframe' display='none' scrolling='no' style='width:650px;height:525px;' frameborder='no' onload='successload();'></iframe";
 
	document.getElementById('ccsection').innerHTML=htmldata;//+t.responseText;
	//window.scrollTo(0,0);
	/* document.getElementById('attendeeloginpopup').style.width='auto';
	document.getElementById('attendeeloginpopup').style.height='auto';
	document.getElementById('attendeeloginpopup').style.top='15%';
	document.getElementById('attendeeloginpopup').style.left='15%';	 */
/* 	if($('registrationsource').value=='widget'){
	jQuery('#paymentsection').append("<div id='hhelper' style='height:300px;'></div>");
	document.getElementById('attendeeloginpopup').style.left='2%';	
	} */
	/* document.getElementById('ebeecreditsclose').style.marginTop='-15px';
	document.getElementById('ebeecreditsclose').style.marginRight='-16px';
	document.getElementById('ebeecreditsclose').style.cursor='pointer';
	document.getElementById('attendeeloginpopup').style.padding=0;
	document.getElementById('attendeeloginpopup').style.backgroundColor='#FFFFFF';
	document.getElementById('ebeecreditsclose').onclick=ccscreenclose;
	document.getElementById('attendeeloginpopup').style.display="block"; */
}

var ccscreenclose=function(){
showTicLoadingImage("Loading...");
insidepay=false;


	//if($('hhelper')) $('hhelper').remove();
	tid=tranid;
	eid=eventid;
	eventbeepaymentclickcount=0;
	clickcount=0;
	/* document.getElementById('attendeeloginpopup').removeAttribute("style");
	document.getElementById('attendeeloginpopup').innerHTML='';
	document.getElementById('attendeeloginpopup').style.display="none";
	document.getElementById("backgroundPopup").style.display="none"; */
	
	document.getElementById('ccsection').innerHTML="";
	getStatus();
	
	
	//window.close();
}
function non_sucess()
{   
    if($('seatingsection'))
	displaydivpopuptimeup();
	else
	displaydivpopuptimeupfortkt();	
	if(hidetimeup){gettimeoutpopup("");}
	 if($('ticket_timer'))
     $('ticket_timer').show();
	hideimage_showpaysection();

}
function getPaypalPaymentsPopUp(tid,eid,paytype){
if(document.getElementById("backgroundPopup"))
document.getElementById("backgroundPopup").style.display="block";

//windowOpener('/portal/embedded_reg/paymentdata.jsp?tid='+tid+'&eid='+eid+'&paytype='+paytype,'Payment_'+tid,'WIDTH=950,HEIGHT=700,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');
windowOpener(serveradd+'/embedded_reg/checkavailability.jsp?tid='+tid+'&eid='+eid+'&paytype=paypal','Payment_'+tid,'WIDTH=740,HEIGHT=600,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');
gPopupIsShown = true;
disableTabIndexes();
}

function getPaypalXPaymentsPopUp(tid,eid,paytype){
if(document.getElementById("backgroundPopup"))
document.getElementById("backgroundPopup").style.display="block";
//windowOpener('/portal/embedded_reg/paypalxpaymentdata.jsp?tid='+tid+'&eid='+eid+'&paytype='+paytype,'Payment_'+tid,'WIDTH=950,HEIGHT=700,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');
windowOpener(serveradd+'/volume/embedded_reg/checkavailability.jsp?tid='+tid+'&eid='+eid+'&paytype=paypalx','Payment_'+tid,'WIDTH=740,HEIGHT=600,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');
gPopupIsShown = true;
disableTabIndexes();
}

function getGooglePaymentsPopUp(tid,eid,paytype){
if(document.getElementById("backgroundPopup"))
document.getElementById("backgroundPopup").style.display="block";

//windowOpener('/portal/embedded_reg/googlepaymentdata.jsp?tid='+tid+'&eid='+eid+'&paytype='+paytype,'Payment_'+tid,'WIDTH=740,HEIGHT=500,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');
windowOpener(serveradd+'/volume/embedded_reg/checkavailability.jsp?tid='+tid+'&eid='+eid+'&paytype=google','Payment_'+tid,'WIDTH=740,HEIGHT=600,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');
gPopupIsShown = true;
disableTabIndexes();
}



var popupWin="";
var modelwin;
var val='';
function windowOpener(url, name, args) 
{
val='';
popupWin="";

if (typeof(popupWin) != "object")
{
popupWin = window.open(url,name,args);

if (popupWin && popupWin.top) {

} else {
alert("Pop-up blocker seem to have been enabled in your browser.\nFor completing registration, please change your Pop-up settings.");
if(document.getElementById("backgroundPopup"))
document.getElementById("backgroundPopup").style.display="none";
clickcount=0;
return;
} 

if(name=='memberLogin'){

val='member';
}
else{
val='register';
}
closeIt();

} 
else 
{
if (!popupWin.closed)
{
popupWin.location.href = url;
}
else 
{
popupWin = window.open(url, name,args);
closeIt();
}
}
popupWin.focus();
}



function closeIt()
{

if(val=='register'){
tid=tranid;
eid=eventid;
}
if (!popupWin.closed)
{
setTimeout("closeIt()",1)//adjust timing
try
{
		
}
catch (err)
{
}
}
else
{
if(document.getElementById("backgroundPopup"))
document.getElementById("backgroundPopup").style.display="none";
if(val=='register'){
if(paymenttype=='paypal'&&paymentmode=='paypalx')
getPaypalxStatus();
else
getStatus();
}
else{
if(document.getElementById('clubuserid').value!='')
EnableMemberTickets();
}
if($('seatingsection'))
	displaydivpopuptimeup();
	else
	displaydivpopuptimeupfortkt();
}
}


var eventbeepaymentclickcount=0;

function AjaxSubmit(action){
eventbeepaymentclickcount++;
if(eventbeepaymentclickcount>1)
return;
if(document.getElementById('ebeepay'))
document.getElementById('ebeepay').style.display='none';
if(document.getElementById('paystatus'))
document.getElementById('paystatus').innerHTML="<img src='/home/images/ajax-loader.gif'><span>Processing...</span>";

if(document.getElementById('paymenterror'))
document.getElementById('paymenterror').innerHTML='';
if(action=='cancel'){
eventbeepaymentclickcount=0;
//window.close();
ccscreenclose();

return;
}
$('form-register-event').request({
onComplete:EbeepaymentResponse
});
}

function EbeepaymentResponse(response){
data=response.responseText;
var statusJson=eval('('+data+')');
var status=statusJson.status;

tranid=statusJson.tid;
eventid=statusJson.eid;

if(status=='success'){
cancelRedirect();
//$("cardScreenContent").update("<table ><tr><td height='175'></td></tr></table><center>Your event ticket purchase is completed successfully<br/><a href='#' onClick='cancelRedirect();return false;'>Click here to reach confirmation page. Please print confirmation page and bring it to the venue</a> </center>");
eventbeepaymentclickcount=0;
}
else if(status=='alreadyCompleted'){
cancelRedirect();
//$("cardScreenContent").update("<table ><tr><td height='175'></td></tr></table><center>Your event registration is already completed. Your card is not charged this time.<br/><a href='#' onClick='cancelRedirect();return false;'>Click here to reach confirmation page</a> </center>");
eventbeepaymentclickcount=0;
}
else if(status=='timeout')
{document.getElementById('cardScreenContent').innerHTML="";
document.getElementById('cardScreenContent').innerHTML="<table width='100%'><tr><td height='200px'></td></tr><tr><td align='center'><b>You have exceeded the time limit and your reservation has been released.</b><br/>* The purpose of this time limit is to ensure that registration is available to as many people as possible.<br/>We apologize for the inconvenience.</p><br/> <b><a href='#' onclick='cancelRedirect()'>Click Here Go To Tickets Page</a></b></td></tr></table>";

}
else
{
if(document.getElementById('ebeepay'))
document.getElementById('ebeepay').style.display='block';

if(document.getElementById('paystatus'))
document.getElementById('paystatus').innerHTML="";
eventbeepaymentclickcount=0;
var info ="<table class='error'>";
if(statusJson.errors.length==1)
info+="There is an Error";
else
info+="There are "+statusJson.errors.length+" Error[s]";
for(var i=0;i<statusJson.errors.length;i++){
if(statusJson.errors[i].indexOf("please try back later")>-1)
{
		document.getElementById('cardScreenContent').style.display='none';
		$("errormsg").innerHTML="<table width='90%' align='center' valign='middle'><tr><td height='175'></td></tr><tr><td align='center' valign='top'>We haven't received payment confirmation from our credit card processing company, your registration is still pending, we will email you status within 24 hours! </td></tr></table>"
		document.getElementById('errormsg').style.display='block';
	break;
}
info += "<tr><td >"+statusJson.errors[i]+"</td></tr>";
}
info +="</table>";
$('paymenterror').update(info);
}
}

function getStatus(){
tid=document.getElementById('tid').value;
new Ajax.Request('/volume/embedded_reg/checkstatus.jsp?timestamp='+(new Date()).getTime(), {
method: 'get',
parameters:{tid:tid},
onSuccess: PrcocesgetStatusResponse,
onFailure:  failureJsonResponse
});
}

function PrcocesgetStatusResponse(response){
data=response.responseText;
	var statusJson=eval('('+data+')');
	var status=statusJson.status;
	var hstatus=statusJson.hstatus;
	setNewTransactionId(statusJson.tid);
	tid=statusJson.tid;	
	if(status=='Completed'){
	getConfirmation(tid,eid);
	}
	else if(status=='Processing'){
	//for google transaction
	getConfirmation(tid,eid);
	}
	else if(status=='waiting'){
	//paypal not yet completed payment
	getConfirmation(tid,eid);
	}
	else if(status=='ccfatalerror'){
		//Eventbee CC Fatal Error
		refreshPage();
	}
	else if(paymenttype=='paypal'){
	clickcount=0;
	showContinueOptions(tid,eid);
	}
	else if(hstatus=='TimeOut'){
		
		refreshPage();
	}
	else if(hstatus=='Exceeded '){
		
		refreshPage();
	}
	else{
 	clickcount=0;
	non_sucess();
	}
	
	
}


function getPaypalxStatus(){
new Ajax.Request('/volume/embedded_reg/papalxstatus.jsp?timestamp='+(new Date()).getTime(), {
method: 'get',
parameters:{tid:tranid,eid:eid},
onCreate : startLoading("Loading"),
onSuccess: PrcocesPaypalxResponse,
onFailure:  failureJsonResponse
});

}

function PrcocesPaypalxResponse(response){

data=response.responseText;
	var statusJson=eval('('+data+')');
	var status=statusJson.status;
	setNewTransactionId(statusJson.tid);
	tid=statusJson.tid;
	if(status=='Completed')
	getConfirmation(tid,eid);
	
    else if(status=='INCOMPLETE'||status=='PROCESSING'||status=='EXPIRED'){
	if(document.getElementById('imageLoad'))
document.getElementById('imageLoad').style.display='none';

document.getElementById('profile').style.display='none';

if(document.getElementById('profile'))
document.getElementById('paymentsection').style.display='none';

if(document.getElementById('pageheader')){
document.getElementById('pageheader').style.display='none';
}

document.getElementById('registration').style.display='block';
document.getElementById('registration').innerHTML=statusJson.msg;	
}
else if(status=='CREATED'||status=='INVALID'){
	clickcount=0;
	  Element.hide('imageLoad');
	  loaded = true;
	  document.getElementById('paymentsection').style.display='block';
 	}
 	else
 	{
 	clickcount=0;
		  Element.hide('imageLoad');
		  loaded = true;
	  document.getElementById('paymentsection').style.display='block';
 	
 	}
}

function showContinueOptions(tid,eid) {
new Ajax.Request('/volume/embedded_reg/continueoptions.jsp?timestamp='+(new Date()).getTime(),{
method: 'get',
parameters:{eid:eid,tid:tid},
onSuccess: showOptions,
onFailure:  failureJsonResponse
});
}


function showOptions(response){
var el = document.getElementById("imageLoad");
if (el) {
document.getElementById("profile").style.display='none'
document.getElementById("paymentsection").style.display='none'
el.innerHTML =response.responseText;
document.getElementById("imageLoad").style.display='block';
}
}

function continueRegistration(){
document.getElementById("paymentsection").style.display='block';
document.getElementById("imageLoad").style.display='none';

}

function processRegistration(tid,eid,paytype){
if($('paymentsection'))
$('paymentsection').hide();
showTicLoadingImage("Loading...");
new Ajax.Request('/volume/embedded_reg/checkavailability.jsp?timestamp='+(new Date()).getTime(), {
method: 'get',
parameters:{eid:eid,tid:tid,paytype:paytype},
onSuccess: PrcocesRegResponse,
onFailure: failureJsonResponse
});
}

function PrcocesRegResponse(response){

var data=response.responseText;

var statusJson=eval('('+data+')');
var status=statusJson.status;
if(status=='Success')
{	getConfirmation(tranid,eventid); }
else if (status=='Fail' && statusJson.paymenttype=='ebeecredits'){
FillebeecreditDetails(statusJson.fbuserid);
}else if (status=='Fail' && statusJson.exceeded=='true'){
alert(statusJson.msg);
hideimage_showpaysection();
clickcount=0;
}
else
showErrorMessage();
}

//***********************PAYPAL PAMENT SCREEN************************

function closePaypalPopUp(){
window.close();
}
//***********************PAYPAL PAMENT SCREEN ************************

//***********************GOOGLE PAMENT SCREEN************************

function closeGooglePopUp(){
window.close();
}

//***********************GOOGLE PAMENT SCREEN************************

function refreshPage(){
window.location.reload(true);
}


var loaded = false;
var paymentstatusmsg='';
function startLoading(msg){
document.getElementById("paymentsection").style.display='none';
paymentstatusmsg=msg;
 loaded = false;
 showLoadingImage();
 	}

function showLoadingImage() {

var el = document.getElementById("imageLoad");
if (el && !loaded) {
el.innerHTML = paymentstatusmsg+' ......<br/><br/><br/><img src="/home/images/ajax-loader.gif">';
new Effect.Appear('imageLoad');
}
}

function getEventbeeCreditsPopUp(tid,eid,paymenttype){
if(document.getElementById("backgroundPopup"))
	document.getElementById("backgroundPopup").style.display="block";
if(!$('ebeecreditpopup')){
		var div=document.createElement("div");
		div.setAttribute('id','ebeecreditpopup');
		div.className='ticketunavailablepopup_div';
		jQuery('body').append(div);
	}
	$('ebeecreditpopup').innerHTML="<center>Loading...<br><img src='/home/images/ajax-loader.gif'></center>";
	$('ebeecreditpopup').style.display='block';
	$('ebeecreditpopup').style.top='50%';
	$('ebeecreditpopup').style.left='26%';
	$('ebeecreditpopup').style.width='350px';
	fbcreditspopupwait();
	disableTabIndexes();
	FB.getLoginStatus(function(response) {
		if (response.authResponse && response.status=='connected') {
			FB.api('/me', function(response) {
				FillebeecreditDetails(response);
			});	
		}
		else{
			getFBLoginebeecreditsContent();
		}
	}, {scope:'publish_stream,email'});
if($('registrationsource').value=='widget'){
		jQuery('#leftList').append("<div id='forntspopup'><iframe style='border: 0; margin: 0; padding: 0; height: 350px; width: 0;' id='ntsframe' name='ntsframe'></iframe></div>");
	}
}
var creditbuttonclick=0;
function getFBLoginebeecreditsContent(){
var htmldata="<img src='/home/images/images/close.png' id='ebeecreditsclose' class='imgclose' ><center><img src='/main/images/login-button.png' class='ebeecreditsloginbutton' border='0'><br><span ><a id='cancelstep'>Cancel.</a></span></center>";
document.getElementById("ebeecreditpopup").innerHTML=htmldata;
jQuery("#cancelstep,.ebeecreditsloginbutton").attr("style","cursor:pointer;color:blue");
jQuery("#ebeecreditsclose").attr('style','margin-top:-35px;cursor:pointer;');
jQuery("#ebeecreditsclose,#cancelstep").click(function(){
if(creditbuttonclick==0)
	closeebeecreditspopup();
});
jQuery(".ebeecreditsloginbutton").click(function(){
if(creditbuttonclick==0)
	getFBLoginForebeecredits();
	creditbuttonclick++;
});
if($('registrationsource') && $('registrationsource').value=='iPad'){
	jQuery("#ebeecreditsclose").attr("style"," margin-top: -15px;margin-right: -15px;cursor:pointer;color:white;");
	jQuery("#cancelstep").attr("style","cursor:pointer;color:white;");
}
}

function getFBLoginForebeecredits(){
	FB.login(function(response){
	FB.api('/me', function(response) {
		if(response.error){
		creditbuttonclick=0;
		}
		else{
			FillebeecreditDetails(response);
		}       
	});
	}, {scope:'publish_stream,email'});
}

function FillebeecreditDetails(response){
var fbuserid=response.id
if(fbuserid==undefined){
getFBLoginForebeecredits();
return;
}
$('ebeecreditpopup').innerHTML="<center>Loading...<br><img src='/home/images/ajax-loader.gif'></center>";
//new Ajax.Request('/embedded_reg/getebeecreditdetails.jsp?timestamp='+(new Date()).getTime(),{
new Ajax.Request('/volume/embedded_reg/checkavailability.jsp?timestamp='+(new Date()).getTime(),{
method:'get',
parameters:{eid:eventid,ntsenable:ntsdata["nts_enable"],fbuserid:fbuserid,tid:tranid,fname:response["first_name"],lname:response["last_name"],email:response["email"],paytype:'ebeecredits'},
onSuccess: ebeecreditDetails,
onFailure:  failureJsonResponse
});
}
function ebeecreditDetails(response){
	var data=response.responseText;
	responsedata=eval('('+data+')');
	var fbuserid="";
	var fbname="";
	ntscode=responsedata.ntscode;
	display_ntscode=responsedata["display_ntscode"];
	FB.getLoginStatus(function(response) {
		if (response.authResponse && response.status=='connected') {
			FB.api('/me', function(response) {
				fbuserid=response.id;
				fbname=response.name;
				ebeecreditdetailsdata(responsedata,fbuserid,fbname);
			});	
		}
		else{
			getFBLoginebeecreditsContent();
		}
	}, {scope:'publish_stream,email'});
	
}

function ebeecreditdetailsdata(responsedata,fbuserid,fbname){
var status='';
if(responsedata.status){
	status=responsedata.status;
}
var htmldata="<img src='/home/images/images/close.png' id='ebeecreditsclose' class='imgclose'>";
if(status!='Fail'){
	var availablecredits = responsedata.availablecredits;
	//var availablecredits=(Number(responsedata.availablecredits)-Number(responsedata.usedcredits)-Number(responsedata.reservedcredits))*Number(responsedata.currencyconverter);
	if(availablecredits<0)
	{
		availablecredits='0.00';
	}
	var amountdetails="Total Amount:&nbsp;"+tktsData.currencyformat+responsedata.totalamount+"<br>("+responsedata.totalamtcredits+" Credits Required)<br> Available Credits:&nbsp;"+availablecredits;
	if(fbuserid!=""){
		imgtag="<img src='http://graph.facebook.com/"+fbuserid+"/picture'>";
		htmldata+="<table><tr><td width='150'>Logged in as:<br>"+imgtag+"<br>"+fbname+"</td><td>"+amountdetails+"</td></tr></table>";
	}
	else{
		htmldata+=amountdetails;
	}
	var clickfunction="paywithebeecredits("+fbuserid+")";
	var buttonvalue='Confirm';
	var message="Confim Your payment with Eventbee Credits.";
	var cancellink="<span style='float:right;'><a id='cancelstep'>Cancel.</a></span>";
	var btnstyle="float:left";
	if(Number(availablecredits)<Number(responsedata.totalamtcredits)){
		clickfunction="closeebeecreditspopup()";
		buttonvalue="OK";
		cancellink='';
		message="You don't have sufficient credits to complete this transaction, please select different Payment Method";
		btnstyle="margin-left:40%";
	}
	htmldata+="<br>"+message+"<br><input type='button' value='"+buttonvalue+"' style='"+btnstyle+"' onclick='"+clickfunction+"'>"+cancellink;
}else{	
if(responsedata.msg){
htmldata+=responsedata.msg;
}
}
	$('ebeecreditpopup').innerHTML=htmldata;
	jQuery("#cancelstep").attr("style","cursor:pointer;color:blue");
jQuery("#ebeecreditsclose").attr('style','margin-top:-35px;cursor:pointer;');
jQuery("#ebeecreditsclose,#cancelstep").click(function(){
	closeebeecreditspopup();
});
$('ebeecreditpopup').style.display='block';
if($(backgroundPopup)){
	$('backgroundPopup').style.display='block';
}
if($('registrationsource') && $('registrationsource').value=='iPad'){
	jQuery("#ebeecreditsclose").attr("style"," margin-top: -15px;margin-right: -15px;cursor:pointer;color:white;");
	jQuery("#cancelstep").attr("style","cursor:pointer;color:white;");
}
}

function closeebeecreditspopup() {
creditbuttonclick=0;
if($('backgroundPopup'))
		$('backgroundPopup').hide();
$('ebeecreditpopup').hide();	
hideimage_showpaysection();
clickcount=0;
if($('forntspopup')){
			jQuery('#forntspopup').remove();
		}
displaydivpopuptimeupfortkt();
		
}
function paywithebeecredits(fbuserid){
if($('backgroundPopup'))
		$('backgroundPopup').hide();
$('ebeecreditpopup').hide();	
if($('forntspopup')){
			jQuery('#forntspopup').remove();
		}
new Ajax.Request('/volume/embedded_reg/ebeecreditspayment.jsp?timestamp='+(new Date()).getTime(),{
method:'get',
parameters:{eid:eventid,tid:tranid,ntscode:ntscode,fbuserid:fbuserid},
onSuccess: PrcocesRegResponse,
onFailure:  creditfailureresponse
});
}

function creditfailureresponse(){
}

