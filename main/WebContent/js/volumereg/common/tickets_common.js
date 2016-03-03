function ticketJsonResponse(response){
 var jsonTicketData=response.responseText;
  tktsData=eval('(' + jsonTicketData + ')');
  if(tktsData.RegFlowWordings["event.reg.selectTicket.msg"]!=undefined && tktsData.RegFlowWordings["event.reg.selectTicket.msg"]!="")
	selectTktMsg=tktsData.RegFlowWordings["event.reg.selectTicket.msg"];
	if($('pageheader')){
		var ticketheader="Tickets",profileheader="Order",paymentheader="Payment",confirmationheader="Confirmation";
		if(tktsData.RegFlowWordings["event.reg.ticket.page.Header"]!=undefined)
			ticketheader=tktsData.RegFlowWordings["event.reg.ticket.page.Header"];
		if(tktsData.RegFlowWordings["event.reg.profile.page.Header"]!=undefined)
			profileheader=tktsData.RegFlowWordings["event.reg.profile.page.Header"];
		if(tktsData.RegFlowWordings["event.reg.payments.page.Header"]!=undefined)
			paymentheader=tktsData.RegFlowWordings["event.reg.payments.page.Header"];
		if(tktsData.RegFlowWordings["event.reg.confirmation.page.Header"]!=undefined)
			confirmationheader=tktsData.RegFlowWordings["event.reg.confirmation.page.Header"];
			headers["ticketspage"]=ticketheader;
			headers["profilepage"]=profileheader;
			headers["paymentpage"]=paymentheader;
			headers["confirmationpage"]=confirmationheader;		
	}
	var eventstatus='ACTIVE';
	if($('eventstatus'))
		eventstatus=$('eventstatus').value;
   if(tktsData.ticketstatus == 'no' || tktsData.groups==undefined || eventstatus=="PENDING" || tktsData.groups==""){
	    if($('pageheader')){
			document.getElementById('pageheader').style.display='block';
			document.getElementById('pageheader').innerHTML=headers["ticketspage"];
		}
		document.getElementById('registration').innerHTML='<center><b>Tickets are currently unavailable</b></center>';
		hideTicLoadingImage();
		if($('eventdate')){
			document.getElementById('eventdate').disabled=false;
		}
		if($('ticketrecurring'))
			document.getElementById('ticketrecurring').innerHTML='';
	}
	else{
		
	getHeader(eventid);
	}
 
  }
  
   function getHeader(eventid){

 if(document.getElementById('pageheader')){
 document.getElementById('pageheader').style.display='block';
 
 document.getElementById('pageheader').innerHTML=headers.ticketspage;

  }
 if(evtdate==''&&document.getElementById('eventdate')){
 $('registration').innerHTML='';
 }
 else
 {
  getTicketsBlock(eventid);
  }
 }
 
 var ticketpageclickcount=0;
 function validateTickets(){
	ticketpageclickcount++;
	if(ticketpageclickcount>1){
		return;
		}	
	
	if($('registration'))
		$('registration').hide();
	showTicLoadingImage("Loading...");
	if(document.getElementById("eventdate"))
		document.getElementById("eventdate").disabled=true;
	var qty=0;
	var totalqty=0;
	var noseatqty=0;
	var flag=true;
	var allowsel=false;
	var noseatallowsel=false;
	var min_sel_qty=true;
	var max_sel_qty=true;
	var NOSEAT_tic_id=[];
	NOSEAT_tic_id=ticketsArray;
	/*for tickets with out seats section start*/
	for (var i = 0; i<ticket_ids_seats.length; i++) {
		var arrlen = NOSEAT_tic_id.length;
		for (var j = 0; j<arrlen; j++) {
			if (ticket_ids_seats[i] == NOSEAT_tic_id[j]) {
				NOSEAT_tic_id = NOSEAT_tic_id.slice(0, j).concat(NOSEAT_tic_id.slice(j+1, arrlen));
			}
		}
	}
	/*tickets without seats end*/
	
	for(var t=0;t<ticketsArray.length;t++){
		ticketd=ticketsArray[t];
		var ttype=ticketWidgets[ticketd].GetTicketType();
		if(ticketWidgets[ticketd].ticketIsAvailable=='Y'&&ticketWidgets[ticketd].ticketStatusMsg!='NA'){
			if(ttype=='donationType')
				qty=ticketWidgets[ticketd].GetDonationTicketQty();
			else{
				
				if(document.getElementById("qty_"+ticketd).type=='hidden'){
					qty=document.getElementById("qty_"+ticketd).value;
				}
				else{
					var x=document.getElementById("qty_"+ticketd).selectedIndex;
					qty=document.getElementById("qty_"+ticketd).options[x].value;
				}
			}
		}
		if($('seatingsection')){
			for(i=0;i<ticket_ids_seats.length;i++){
				if(ticketd==ticket_ids_seats[i]){
					if(qty == 0){
						//allowsel=false;
						ticketpageclickcount=0;
					}
					else{ 
					
					if(Number(qty)>0){
					
						if(Number(qty)<min_ticketid[ticketd]){
							totalqty=totalqty+Number(qty)
							//totalqty=0;
							allowsel=false;
							alert("for \""+ticketnameids[ticketd]+"\" the minimum seats quantity is "+min_ticketid[ticketd]+", you selected only "+qty+" seats");
							min_sel_qty=false;
							ticketpageclickcount=0;
							break;
						}
						else if(Number(qty)>max_ticketid[ticketd]){
							totalqty=totalqty+Number(qty)
							//totalqty=0;
							allowsel=false;
							alert("for \""+ticketnameids[ticketd]+"\" the maximum seats quantity is "+max_ticketid[ticketd]+", you selected "+qty+" seats");
							max_sel_qty=false;
							ticketpageclickcount=0;
							break;
						}
						else{
							if(min_sel_qty && max_sel_qty){
								totalqty=totalqty+Number(qty);
								allowsel=true;
							}
							else{
								totalqty=0;
								allowsel=false;
								ticketpageclickcount=0;
							}
						}
					}
					}

				}
				else{
					for(j=0;j<NOSEAT_tic_id.length;j++){
						if(ticketd==NOSEAT_tic_id[j]){
							noseatqty=noseatqty+Number(qty);
							noseatallowsel=true;
						}
					}
				}
			}
			if(ticket_ids_seats.length==0){
				for(j=0;j<NOSEAT_tic_id.length;j++){
						if(ticketd==NOSEAT_tic_id[j]){
							noseatqty=noseatqty+Number(qty);
							noseatallowsel=true;
						}
					}
			}
		}
		else{	
			if(qty>0){
				break;
			}
		}
	}
	if($('seatingsection')){
		if(parseFloat(totalqty)==0 && parseFloat(noseatqty)==0){
		
		if(document.getElementById("eventdate"))
			document.getElementById("eventdate").disabled=false;
		hideTicLoadingImage();
		if($('registration'))
			$('registration').show();
		
			var qty1=0;
			for(var t=0;t<ticketsArray.length;t++){
				qty1=qty1+Number($("qty_"+ticketsArray[t]).value);
			}
			if(qty1==0){
				alert(selectTktMsg);
				ticketpageclickcount=0;
				}
		}
		else if(parseFloat(totalqty)==0 && parseFloat(noseatqty)>0){
			DisabledonationAmount();
			if(document.getElementById('eventdate')){
				flag=validateCheckDate();
			}
			if(flag){
				
				checkticketsstatus();
				
				//checkseatavailibility();
				return;
			}
		}
		else if (!allowsel){
			
		if(document.getElementById("eventdate"))
			document.getElementById("eventdate").disabled=false;
		hideTicLoadingImage();
		if($('registration'))
			$('registration').show();

		}
		else{
			DisabledonationAmount();
			if(document.getElementById('eventdate')){
				flag=validateCheckDate();
			}
			if(flag){
				checkticketsstatus();
				//checkseatavailibility();
				return;
			}
		}
	}
	else{	
		if(parseFloat(qty)==0){
			if(document.getElementById("eventdate"))
				document.getElementById("eventdate").disabled=false;
			hideTicLoadingImage();
			if($('registration'))
				$('registration').show();
			alert(selectTktMsg);
			ticketpageclickcount=0;
		}
		else{
			DisabledonationAmount();
			if(document.getElementById('eventdate')){
				flag=validateCheckDate();
			}
			if(flag){
				 checkticketsstatus();
				//submitTickets();
				//getAttendeeLoginPopup();
				return;
			}
		}
	}
}
var discountfalgpro="";
function submitTicketrepone(response){
var data=response.responseText;
var responsejsondata=eval('(' + data + ')');
discountfalgpro=responsejsondata.discountfalg;
var status=responsejsondata.status;
ntscode=responsejsondata.ntscode;
display_ntscode=responsejsondata["display_ntscode"];
if(status=='success'){
document.getElementById('tid').value=responsejsondata.tid;
tranid=responsejsondata.tid;
if(responsejsondata.paymentmode);
paymentmode=responsejsondata.paymentmode;
getProfileJson(responsejsondata.tid,responsejsondata.eid);
}
else
showErrorMessage();




}

function validateCheckDate(){
if(document.getElementById('eventdate')){
if(evtdate==''){
alert("select date");
return false;
}
else
return true;
}
}

function showErrorMessage(){
hideTicLoadingImage();
document.getElementById('registration').innerHTML="<table class='boxcontent' width='100%'><tr height='100'><td align='center'>Sorry Registration can not be processed now, Please click here to <a href='#' onClick='refreshPage()'>retry.</a></td></tr></table>";
}
 
 function getJsonFormat(){
	var jsonFormat="{";
	if($('seatingsection')){
		var tic_id=ticketsArray;
		//var tic_id=tick_id.split(",");
		for(i=0;i<tic_id.length;i++){
			if(sel_ticket[tic_id[i]]!=undefined){
				if(jsonFormat!="{"){
					jsonFormat=jsonFormat+",";
				}
				jsonFormat=jsonFormat+'"'+tic_id[i]+'":['+sel_ticket[tic_id[i]]+']';
			}
		}
	}
	jsonFormat=jsonFormat+"}";
	return jsonFormat;
}
 
 
 function ignorekeypress(e){
var keycode;
if (e) keycode = e.which;
if(keycode==13)
return false;
else 
return true;

}

function getDiscountAmounts(){
code=document.getElementById('couponcode').value;
new Ajax.Request('/volume/embedded_reg/getdiscounts.jsp?timestamp='+(new Date()).getTime(), {
method: 'get',
parameters:{eid:eventid,code:code},
onSuccess: PrcocesDiscountsResponse,
onFailure:  failureJsonResponse
});
}

function PrcocesDiscountsResponse(response){
var data=response.responseText;
var responsejsondata=eval('(' + data + ')');
discountsjson=responsejsondata.discounts;
discountedprices=responsejsondata.discountedprices;
if(document.getElementById('totalamount'))
document.getElementById('totalamount').style.display='none';
fillDiscountBox();
resetPrices();
updatePrices(discountedprices);
}

function fillDiscountBox(){
if(discountsjson && discountsjson.IsCouponsExists && discountsjson.IsCouponsExists=='Y'){
if(discountsjson.discountapplied && discountsjson.discountapplied=='Y'){
if(discountsjson.validdiscount=='Y'){
document.getElementById('discountmsg').innerHTML=discountsjson.discountmsg;
document.getElementById('invaliddiscount').innerHTML='';
}
else
{
code='';
$('invaliddiscount').update(discountsjson.discountmsg);
document.getElementById('discountmsg').innerHTML='';
}
document.getElementById('couponcode').value=discountsjson.discountcode;
}
}
}

function updatePrices(discountedprices){

if(discountedprices){
for(var index=0;index<discountedprices.length;index++){
var ticketid=discountedprices[index].ticketid;
var finalprice=discountedprices[index].final_price;
if(discountedprices[index].isdonation!='Yes'){
if(document.getElementById('qty_'+ticketid))
ticketWidgets[ticketid].SetChargingPrice(discountedprices[index].final_price);
}
if(parseFloat(finalprice)==0){
if(discountedprices[index].haveDiscount=='yes'){
if(document.getElementById('qty_'+ticketid))
ticketWidgets[ticketid].SetChargingFee(0.00);
}
}
}
}
}

function resetPrices(){
for(var q=0;q<ticketsArray.length;q++){
tktid=ticketsArray[q];
var actualprice=ticketWidgets[tktid].GetTicketPrice();
var actualfee=ticketWidgets[tktid].GetTicketActualFee();
var tickettype=ticketWidgets[tktid].GetTicketType();
if(tickettype!='donationType'){
ticketWidgets[tktid].SetChargingPrice(actualprice);
ticketWidgets[tktid].SetChargingFee(actualfee);
}
}
}

function DisabledonationAmount(){
	for(var t=0;t<ticketsArray.length;t++){
		ticketd=ticketsArray[t];
		var ttype=ticketWidgets[ticketd].GetTicketType();
		if(ticketWidgets[ticketd].ticketStatusMsg=='NA'){
			if(ttype=='donationType')
				ticketWidgets[ticketd].setDonationTicketQty(0);
		}
	}
}

 function setTicketGroupDescription(eleid){
	   var ele=document.getElementById(eleid);
	   if(document.getElementById("desc_"+eleid)){
		   document.getElementById("desc_"+eleid).style.display="none";
		   ele.innerHTML = ele.innerHTML + ' <img style="cursor:pointer;" id="imgShowHidegrp_'+eleid+'" src="/home/images/expand.gif" onClick=showTcktGrpDescr('+eleid+');>';
	   }
}


function showTcktGrpDescr(eleid){
	   var a=document.getElementById("desc_"+eleid).style.display;
	   if(a=="none"){
			   document.getElementById("imgShowHidegrp_"+eleid).src="/home/images/collapse.gif";
			   document.getElementById("desc_"+eleid).style.display="block";
	   }else{
			   document.getElementById("imgShowHidegrp_"+eleid).src="/home/images/expand.gif";
			   document.getElementById("desc_"+eleid).style.display="none";
	   }
	   
}
function EnableMemberTickets(){

for(var i=0;i<ticketsArray.length;i++){
ticketd=ticketsArray[i];		
ticketWidgets[ticketd].ClearMemberTicketLogin();
		}
		
if($('seatingsection')){
	showmemberseats();
}
}
 
 function getMemeberLoginPopUp(eid){
if(document.getElementById("backgroundPopup"))
document.getElementById("backgroundPopup").style.display="block";

windowOpener('/volume/embedded_reg/memberlogingblock.jsp?eid='+eid,'memberLogin','WIDTH=400,HEIGHT=300,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');


}

 
 function failureJsonResponse(response){
	clickcount=0;
	alert("Sorry this request cannot be processed at this time");
}

function insertOptionBefore(purpose){ 
  var elSel;
if(purpose=='tickets')
  elSel = document.getElementById('eventdate');
  else
    elSel = document.getElementById('event_date');
	
	var elOptNew = document.createElement('option');
    elOptNew.text = '--Select Date--';
    elOptNew.value = 'Select Date';
	if(elSel.options.length==0){
		try{
			elSel.add(elOptNew,elSel.options[0]);
		}
		catch(ex){
			elSel.add(elOptNew,0);
		}	
	}
  if(elSel.options.length>0){
  var elOptOld = elSel.options[0]; 
  if(elOptOld.text!='--Select Date--'){
     
    try {
      elSel.add(elOptNew, elOptOld); // standards compliant; doesn't work in IE
    }
    catch(ex) {
      elSel.add(elOptNew, 0); // IE only
    }
  
  elSel.selectedIndex=0;
}
}
}

function showTicLoadingImage(msg) {
	loaded = false;
	var el = document.getElementById("imageLoad");
	if (el && !loaded) {
		el.innerHTML='';
		el.innerHTML = msg+'<br/><img src="/home/images/ajax-loader.gif">';
		Element.show('imageLoad');
	}
}

function hideTicLoadingImage(){
if(document.getElementById("imageLoad")){

Element.hide('imageLoad');
loaded = true;
}
}

function setNewTransactionId(newTrnId){
$('regform').tid.value=newTrnId;
tranid=newTrnId;
}

function getBuyButtonStatus(){
var butdisable=true;
for(i=0;i<ticketsArray.length;i++){
	var ticketd=ticketsArray[i];
	if(ticketWidgets[ticketd].ticketStatusMsg==undefined){
		butdisable=false;
		break;
	}
}
	if(butdisable){
		$('orderbutton').disabled=true;
	}
}

function getAttendeeLoginPopup(){
if(!fbavailable){
if($('nts_enable'))	$('nts_enable').value='N';
	ntsdata["nts_enable"]='N';
if($('login-popup')) $('login-popup').value='N';
	ntsdata["login-popup"]='N';
if($('fbsharepopup')) $('fbsharepopup').value='N';
	}
if((ntsdata.nts_enable=='N' && ntsdata["login-popup"]=='N') || becamepartner==true){

	submitTickets();
	return;
}
	if(!$('fb-root')){

			var divroot=document.createElement("div");
			divroot.setAttribute('id','fb-root');
			jQuery('body').append(divroot);
		}
	if($('registrationsource').value=='widget'){
		jQuery('#leftList').append("<div id='forntspopup'><iframe style='border: 0; margin: 0; padding: 0; height: 350px; width: 0;' id='ntsframe' name='ntsframe'></iframe></div>");
	}
	if(!$('attendeeloginpopup')){
		var div=document.createElement("div");
		div.setAttribute('id','attendeeloginpopup');
		div.className='ticketunavailablepopup_div';
		jQuery('body').append(div);
		
	}
/*
	jQuery.getScript('/home/js/fbevent/fbntslogin.js');
	jQuery('#attendeeloginpopup').load('/portal/socialnetworking/fbntslogin.jsp?eid='+eventid+'',function(response,status){
		if(document.getElementById("backgroundPopup")){
				document.getElementById("backgroundPopup").style.display='block';
		}
		document.getElementById('attendeeloginpopup').style.display="block";
		document.getElementById('attendeeloginpopup').style.top='50%';
		document.getElementById('attendeeloginpopup').style.left='26%';	
	});
	*/
	document.getElementById("attendeeloginpopup").innerHTML='';

	FB.getLoginStatus(function(response) {
		if (response.authResponse && response.status=='connected') {
			FB.api('/me', function(response) {
			if(response.email==undefined)
				response.email='';
				populatefblogindata(response);
			});	
		}
		else if(response.session){
					FBLoginDetails();
		}
		else{
			fillfblogindefaultcontent();
		}
	}, {scope:'publish_stream,email'});
	if($('registration')){ 
		var x=jQuery('#registration').position();
		window.scrollTo("0",""+Number(x)+"");
	}
	else{
		window.scrollTo("0","0");
	}
	if(document.getElementById("backgroundPopup")){
				document.getElementById("backgroundPopup").style.display='block';
		}
		document.getElementById('attendeeloginpopup').style.display="block";
		document.getElementById('attendeeloginpopup').style.top='30%';
		document.getElementById('attendeeloginpopup').style.left='26%';	


	fbpopupwait();
}
function getdisplaymessage(){
	var msg="<p>Keep track of your purchases with your Facebook login.</p>";
	if(ntsdata["nts_enable"]=='Y'){
		msg="<p>This is an NTS event, u can earn credits when transactions happen's with your shared link. You can also keep a track of all your transactions. </p>";
	}
	return msg;
}
function initfbcheck(){
/*var b=document.getElementById('proiframe').contentWindow.document.body.scrollHeight;
if(!document.getElementById('proiframe').contentWindow.document.getElementById('eventprotab'))
	document.getElementById('proiframe').style.height="30px";
else
document.getElementById('proiframe').style.height=b+"px";*/
if(!fbavailable){
if($('fblike')){
var a=$('fblike').parentNode;
a.innerHTML='';
a.setAttribute('width','0px');
}
try{
var a=document.getElementsByTagName('fb:send')[0].parentNode;
if(a)
a.parentNode.removeChild(a);
}
catch(e){}
try{
var b=document.getElementsByTagName('fb:comments')[0].parentNode;
while(b.nodeName!="DIV") b=b.parentNode;
b.innerHTML='';
}
catch(e){}
}
}
/********/
function getTicketsJsonBefore(eid)
{
if(document.getElementById('tid')){
var checktid=document.getElementById('tid').value;

if(checktid!=undefined && checktid!=null && checktid!="")
{     timeout_delete_locked_temp('normal');
	if($('seatingsection'))
	{
	 	 new Ajax.Request('/volume/embedded_reg/seating/delete_tempseats.jsp?timestamp='+(new Date()).getTime(), {
	method: 'post',
	parameters:{eid:eid,tid:checktid}
	,onComplete:getTicketsJson(eid)
	
	});
	}else{getTicketsJson(eid);}

 }else
 getTicketsJson(eid);
}
else
getTicketsJson(eid);
}