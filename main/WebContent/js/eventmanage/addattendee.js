var ticketsArray=[];
var tktsData='';
var eventid='';
var tranid='';
var profileJsondata;
var CtrlWidgets=[];
var responsesdata=[];
var tktarray=[];
var selectedtktarray=[];
var qtyarray=[];
var quesarray='';
var jsonprofile='';
var timestamp=new Date();
var eventdate='';
var sectionid="";
var max_ticketid=new Object();
var min_ticketid=new Object();
var ticket_ids_seats=[];
var seating_enabled_tkt_wedget='NO';
var allsectionid=[];
var allsectionname=[];
var seatingsectionresponsedata=new Object();
var ticketseatcolor=new Object();
var mins_left = 14;
var s_left = 60;
var mins_remain=14;
var secs_remain=60;
var ticket_groupnames=new Object();
var notavailableticketids=new Array();
var memberseatticketids=new Array();
var seatingticketidresponse="";
var pctype='';
var paytoken='';
var selectedpctype='';
var m=0,n=0;
var msbt=0; //for handle multiple sumbmits
var alreadydone='';
function AssignTicketsJsonData(eid,jsondata){
	eventid=eid;
   tktsData=jsondata;
   var flagshowtickets=true;
   if(document.getElementById('event_date')){
   var index=document.getElementById('event_date').selectedIndex;
  eventdate=document.getElementById('event_date').options[index].value;
  if(eventdate!=''){
    getTicketsPageforselectedDates();
  }
  else
  $('#registration').html('');
   }
 else{
 showLoading(props.aa_loading_tks_lbl);
 getTicketsBlock(eid);
 }
  }
function getTicketsBlock(eid){
var url='AddAttendeeVm?eid='+eid+'&purpose=manageradd&t='+(new Date()).getTime();

$.ajax({
    url: url,
    type: 'POST',
    data: { eventdate:eventdate} ,
    success: function (response) {
    	//alert("response::"+response);
    	TicketsBlockresponse(response);
    },
    error: function () {
        alert("error");
    }
}); 
}	

function TicketsBlockresponse(response){
	// alert(response);
	hideLoading();
	ticketsArray=[];
	$('#registration').html(response);
		if(document.getElementById('seatingsection'))
		{
			seatingticketidlabel();
	 		seating_enabled_tkt_wedget='YES';
		}
		else{
		seating_enabled_tkt_wedget='NO';
		Initialize("registration");
	 }	
	 if(document.getElementById('seatingsection')){
		// alert("befo");
	getseatingsection();
	//alert("after");
 }
	 getdiscountblock();
	 loadticketssavebtn();
	 if(eventdate!=''){
	document.addattendeetickets.eventdate.value=eventdate;	 
	 }
	 hideLoading();
}
function failureJsonResponse(){
	
}

function getdiscountblock(){
if(tktsData.isDiscountExists){
		var discountboxlabel=props.aa_disc_code_lbl;
		//if(tktsData.RegFlowWordings["event.reg.discount.box.label"]!=undefined)
			//discountboxlabel=tktsData.RegFlowWordings["event.reg.discount.box.label"];
		var discountcode="";
		if(document.getElementById('discountcode'))
			discountcode=$('#discountcode').val();
		var discountApplyLabel=props.aa_apply_disc_code_btn_lbl;
		//if(tktsData.RegFlowWordings["discount.code.applybutton.label"]!=undefined)
			//discountApplyLabel=tktsData.RegFlowWordings["discount.code.applybutton.label"];
		var  discountbox="<input type='text' name='couponcode' id='couponcode' size='10' value='"+discountcode+"' class='form-control'/>";			
		var applybutton="<span style='vertical-align: middle; display: inline-block;  margin-top: 6px;'>"+"<input type='button' style='margin-top:-3px;' class='btn btn-primary btn-sm' name='submit' id ='discountsbtn' value='"+discountApplyLabel+"'  onClick='getDiscountAmounts();'  /></span>";

		/*var htmldata='<table width="100%" cellpadding="0" cellspacing="0">'
						  +'<tr ><td class="discount" width="100%" cellpadding="0" cellspacing="0" class="yui-skin-sam">'
						  +discountboxlabel+'&nbsp;'+discountbox+'&nbsp;'+applybutton
						  +'<span id="discountmsg"></span><span id="invaliddiscount"></span>'
						  +'</td></tr></table>';*/
		var htmldata='<div class="row"><div class="col-md-4" style="width:31.333%; padding-top: 8px;">'+discountboxlabel+'</div>'+
		              '<div class="col-md-2">'+discountbox+'</div>'+
		              '<div class="col-md-2" style="width: 11%;">'+applybutton+'</div>'+
		              '<div class="col-md-3"><div id="discountmsg" style="padding-top: 6px;"></div><div id="invaliddiscount" ></div></div>';
		
		$("#discountblock").html(htmldata);				  
	}
}
//------------------------------------------------------------------
function getDiscountAmounts(){
code=document.getElementById('couponcode').value;
/*new Ajax.Request('AddAttendeeDiscounts?timestamp='+(new Date()).getTime(), {
method: 'get',
parameters:{eid:eventid,code:code},
onSuccess: PrcocesDiscountsResponse,
onFailure:  failureJsonResponse
});*/
//alert(code+":"+eventid);
$.ajax({
	type:'get',
	data:'',
	url:'AddAttendeeDiscounts?timestamp='+(new Date()).getTime(),
	data:{eid:eventid,code:code},
	success:function(result){
		PrcocesDiscountsResponse(result);
	},
	error:function(){failureJsonResponse();}
})

}

function PrcocesDiscountsResponse(response){
var data=response;
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
$('#discountcode').val(discountsjson.discountcode);
}
else
{
$('#discountcode').val('');
$('#invaliddiscount').html(discountsjson.discountmsg);
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
if(document.getElementById('qty_'+ticketid)){
ticketWidgets[ticketid].SetChargingFee(0.00);
}
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
//------------------------------------------------------------------

function addAttendeeTicketssave(){
	n++;
	tktarray=[];
	var myJSONText = JSON.stringify(tktarray);
	for(var i=0;i<ticketsArray.length;i++){
		if(parseFloat($('#qty_'+ticketsArray[i]).val())>0){
	    var qty=$('#qty_'+ticketsArray[i]).val();
	    var fprice=$('#finalprice_'+ticketsArray[i]).val();
		var oprice=$('#originalprice_'+ticketsArray[i]).val();
		var ofee=$('#processfee_'+ticketsArray[i]).val();
		var ffee=$('#finalprocessfee_'+ticketsArray[i]).val();
		var ticketgroupid=$('#finalprocessfee_'+ticketsArray[i]).val();
		var obj={'finalprice':fprice};
		obj.originalprice=oprice;
		obj.originalfee=ofee;
		obj.finalfee=ofee;
		obj.ticketid=ticketsArray[i];
		obj.qty=qty;
		obj.ticketGroupId=$('#ticketGroup_'+ticketsArray[i]).val();
		obj.ticketType=$('#ticketType_'+ticketsArray[i]).val();
		tktarray.push(obj);
		}
		myJSONText = JSON.stringify(tktarray);
		var myObject = JSON.parse(myJSONText);
		$('#jsondata').val(JSON.stringify(myObject));
		}	
	$('#tid').val(tranid);
	var jsonFormat=getJsonFormat();
	$('#selseatsjson').val(jsonFormat);
	var url='AddAttendeeTickets?eid='+eventid+'&t='+(new Date()).getTime();
	if(n==1){
	/*$('addattendeetickets').request({
	   onComplete:addAttendeeTicketrespone
			});	*/
		$.ajax({
			type:'POST',
			url:url,
			data:$('#addattendeetickets').serialize(),
			success:function(result){
				if(!isValidActionMessage(result)) return;
				addAttendeeTicketrespone(result);
			}
		});
	}	
}

function addAttendeeTicketrespone(response){
	var data=response;
	var jsondata=eval('('+data+')');
	tranid=jsondata.tid;
	$('#addattendeetickets').val(tranid);
	getProfileJsonData(eventid,tranid);
		
}
function getProfileJsonData(eid,tid){
	var url='ProfileJsonData?t='+(new Date()).getTime();
	/* new Ajax.Request(url,{
	method: 'get',
	parameters:{eid:eid,tid:tid},
	onSuccess: ProfileJsonResponse,
	onFailure:  failureJsonResponse
	});*/
	
	$.ajax({
		type:'get',
		url:url,
		data:{eid:eid,tid:tid},
		success:function(result){
			ProfileJsonResponse(result);
		}
	});
}

function  ProfileJsonResponse(response){
	var data=response;
	jsonprofile=data;
	profileJsondata=eval('('+data+')');
	getProfilesBlock(eventid,tranid);
}
function getProfilesBlock(eid,tid){
	var url='ProfileVmData?t='+(new Date()).getTime();
	 /*new Ajax.Request(url,{
	method: 'get',
	parameters:{eid:eid,tid:tid},
	onSuccess: ProfilesBlockResponse,
	onFailure: failureJsonResponse
	});*/
	
	$.ajax({
		type:'get',
		url:url,
		data:{eid:eid,tid:tid},
		success:function(result){
			ProfilesBlockResponse(result);
		}
	});
	
	
}
function ProfilesBlockResponse(response){
	ctrlwidget=[];
	loadbuttons();	
	var data=response;
	var responsejsondata=profileJsondata;
	hideLoading();
	document.getElementById('registration').style.display='none';
	document.getElementById('addattendeeStatus').style.display='none';
	document.getElementById('profile').style.display='block';
	if(document.getElementById('titleDiv'))
	document.getElementById('titleDiv').innerHTML=props.aa_attendee_info_section_header;
	if(document.getElementById('recurringblock'))
	document.getElementById('recurringblock').style.display='none';
	document.getElementById('profile').innerHTML=response;
	var profilecount=responsejsondata.profilecount;
	var tickets=responsejsondata.tickets;
	var questionsobj=responsejsondata.questions;
	var questions='';
	var buyerinfo=responsejsondata.buyerquest;
	for(i=0;i<buyerinfo.length;i++){
		var bqid=buyerinfo[i];
		putWidgetIdeal('buyer', bqid, '1');
	 }
	 for(var index=0;index<tickets.length;index++){
	 ticketid=tickets[index];
	 var count=profilecount[ticketid];
	 questions=questionsobj[ticketid];
	 for(var p=1;p<=count;p++){
	    for(i=0;i<questions.length;i++){
	    var qid=questions[i];
	   putWidgetIdeal(ticketid, qid,p);
	 }
   }
}
document.getElementById('q_buyer_fname_1').focus();	
if(document.getElementById('seatingsection')){
	document.getElementById('container').style.display='block';
	getseatingtimer();
}
}
	
var ctrlwidget=[];

function putWidgetIdeal(ticketid,qid, profileid){

var elmid=ticketid+'_'+qid+'_'+profileid;
var jsondata=profileJsondata;
var questionjson=jsondata[ticketid+'_'+qid];
var  objWidget = new InitControlWidget(questionjson, elmid);
$('input.radiochk').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'});
ctrlwidget.push(elmid);
CtrlWidgets[elmid]= objWidget;
//objWidget.get
}

function SubmitProfileData(){
var responsejsondata=profileJsondata;
var profilecount=responsejsondata.profilecount;
var tickets=responsejsondata.tickets;
var questionsobj=responsejsondata.questions;
var questions='';
var buyerinfo=responsejsondata.buyerquest;
var barray=[];
var parray=[];
var tickettype='';

for(i=0;i<buyerinfo.length;i++){
	var bqid=buyerinfo[i];
	var bquestionjson=responsejsondata['buyer_'+bqid];
	var qtype=bquestionjson.type;
	var bansarray=[];
	var bobj={qid:bqid};
	var belementid='q_buyer_'+bqid+'_1';
	if(qtype=='checkbox'||qtype=='radio'){
		var options=document.addattendeeprofiles.elements[belementid];
		if(options.length){
				for(var j=0;j<options.length;j++){
					if(options[j].checked==true){
						bansarray.push(options[j].value);
					}
				}
		}else{
			if(options.checked==true){
				bansarray.push(options.value);
		}	
	}		
	bobj.response=bansarray;
	}
	else if(qtype=='select'){
	var index=document.getElementById(belementid).selectedIndex;
	ans=document.getElementById(belementid).options[index].value;
	bansarray.push(ans);
	 bobj.response=bansarray;
	}
		
	else{
	 ans=document.getElementById(belementid).value;
	 bobj.response=ans;
	}
	barray.push(bobj);
	}
var mybJSONText=JSON.stringify(barray);
var bjsonObj=JSON.parse(mybJSONText);
$('#buyerjsondata').val(JSON.stringify(bjsonObj));



for(var index=0;index<tickets.length;index++){
ticketid=tickets[index];
var count=profilecount[ticketid];
questions=questionsobj[ticketid];
for(var p=1;p<=count;p++){
var qarray=[];
for(i=0;i<questions.length;i++){
var qid=questions[i];
var questionjson=responsejsondata[ticketid+'_'+qid];
var qobj={qid:qid};
var qtype=questionjson.type;
var elementid='q_'+ticketid+'_'+qid+'_'+p;
var ansarray=[];
if(qtype=='checkbox'||qtype=='radio'||qtype=='select'){
	if(qtype=='select'){
		
		var selectedindex=document.getElementById(elementid).selectedIndex;
		ansarray.push(document.getElementById(elementid).options[selectedindex].value);
	}
	else{
		
		
		var options12 = document.addattendeeprofiles.elements[elementid];

if(options12.length){
	
for(var k=0;k<options12.length;k++){
	
if(options12[k].checked==true){

ansarray.push(options12[k].value);
}
}
}
else
{
	if(options12.checked==true){

		ansarray.push(options12.value);
		}	
}
}

qobj.response=ansarray;
}
else{
qobj.response=document.getElementById(elementid).value;
}
qarray.push(qobj);
}
if(document.getElementById(ticketid+'_type'))
tickettype=document.getElementById(ticketid+'_type').value;
var obj={questions:qarray};
obj.ticketid=ticketid;
obj.tickettype=tickettype;
obj.pindex=p;
parray.push(obj);
}
}
var myJSONText = JSON.stringify(parray);
var jsonObj=JSON.parse(myJSONText);
$('#profilejsondata').val(JSON.stringify(jsonObj));
$('#seatingenabled').val(seating_enabled_tkt_wedget);
$('#edate').val(eventdate);
$('#ticketids').val(ticketsArray);
//$('#addattendeeprofiles').action='SaveProfileDetails';
/*$('#addattendeeprofiles').request({
	parameter:{paytype:'Bulk'},	
//onComplete:SubmitProfilerespone
onComplete:getPaymentBlock
});*/

$.ajax({
	type:'POST',
	url:'SaveProfileDetails?paytype=Bulk',
	data:$('#addattendeeprofiles').serialize(),
	success:function(result){
		//SubmitProfilerespone(result);
		if(!isValidActionMessage(result)) return;
		getPaymentBlock(result);
	}
});


}

// paymentscreen
function getPaymentBlock(response){
hideLoading();

document.getElementById('registration').style.display='none';
document.getElementById('addattendeeStatus').style.display='none';
document.getElementById('profile').style.display='none';
if(document.getElementById('titleDiv'))
document.getElementById('titleDiv').innerHTML=props.aa_payment_section_header;
if(document.getElementById('recurringblock'))
document.getElementById('recurringblock').style.display='none';
document.getElementById('payment').innerHTML=response;
document.getElementById('payment').style.display='block';
loadpaymentbtn();
msbt=0;
if(selectedpctype!=''){
	if(document.getElementById('collecteTktQty').value != 0){
		//document.getElementById(selectedpctype).checked=true;
		rmCheck();
		//$('#'+selectedpctype).parent().addClass('checked');
		$("#"+selectedpctype).iCheck('check');
		
	}
}
//window.scrollTo(0,0);
}	

function rmCheck(){
	/*$('#paidpayment').parent().removeClass('checked');
	$('#ccpayment').parent().removeClass('checked');
	$('#nopayment').parent().removeClass('checked');*/
	
	$('#paidpayment').iCheck('uncheck');
	$('#ccpayment').iCheck('uncheck');
	$('#nopayment').iCheck('uncheck');
}

function submitPayment(){
var count=0;
var options=document.addattendeepayment.pctype;	 

//alert(JSON.stringify(JSON.stringify(options)));

for(i=0;i<options.length;i++){
	if(options[i].checked){
		count++;
	}
}
if(count==0){
	//alert('Please select payment collection');
	bootbox.confirm(props.aa_pls_sel_payment_collection_errmsg,function(result){});
	return false;
}
submitPayment1(++msbt);
}

function submitPayment1(msbt){
	//alert("the msbt is::"+msbt);
//if(msbt==1)
submitPayment2();
}

function submitPayment2(){
document.getElementById('tid').value=tranid;
document.getElementById('eid').value=eventid;
if(!document.getElementById('ccpayment') || document.getElementById('ccpayment').checked==false){
/*document.getElementById("paymentcontinue").innerHTML="";
document.getElementById('paymentbutton').innerHTML="<center>Processing...<br><img src='../images/loading.gif'></center>";*/
document.getElementById('paymentprocessimg').innerHTML="<center><img src='../images/ajax-loader.gif'></center>";
document.getElementById('paymentbutton').style.display='none';
document.getElementById('paymentprocessimg').style.display='block';
}	
/*$('addattendeepayment').action='AddAttendeePayment!submitPayment';
$('addattendeepayment').request({
onComplete:paymentBlockResponse
});*/

$.ajax({
	type:'POST',
	url:'AddAttendeePayment!submitPayment',
	data:$('#addattendeepayment').serialize(),
	success:function(result){
		if(!isValidActionMessage(result)) return;
		paymentBlockResponse(result);
	}
});

}

function paymentBlockResponse(response){
var data=response;
var jsondata=eval('('+data+')');
pctype=jsondata.pctype;
alreadydone=jsondata.alreadydone;
if(pctype=='cc' && alreadydone!='yes'){
var grandtotamount=jsondata.grandtotamount;
var vendor=jsondata.vendor;
getCCToCollectPayment(eventid,tranid,grandtotamount,vendor,'addattendeeccpayment');
}else{
/*if(pctype != 'paid')
	pctype='nopayment';*/
if(document.getElementById('ticket_timer')){
		$('#ticket_timer').remove();
		clearTimeout(reg_timeout);
}
FinalUpdate();
}
}

function FinalUpdate(){
var url='UpdateRegDb?t='+(new Date()).getTime();
$.ajax({
	type:'get',
	url:url,
	data:{eid:eventid,tid:tranid,eventdate:eventdate,seatingenabled:seating_enabled_tkt_wedget,pctype:pctype,paytoken:paytoken,alreadydone:alreadydone},
	success:function(result){
		SubmitPaymentBlockResponse(result);
	}
});

}


function getCCToCollectPayment(eid,tid,grandtotamount,vendor,purp){
  var Msg='<span style="font-weight: normal;font-size: 12px"><b>'+props.cc_amount_lbl+': $'+grandtotamount+'</b></span><br/>'+props.global_eventbee_cc_processing_note;
  //var Msg=getPopMsg();
  var setkey= new callPaykey();
  //setkey.setCallbackurl("/main/payments/finalupdate.jsp");
  setkey.setPurpose(purp);
  setkey.setPaymode("direct");
  setkey.setMessage(Msg);
  setkey.setAmount(grandtotamount);
  setkey.setCurrency("USD");
  setkey.setMerchantid("");
  setkey.setLang("En");
  setkey.setTitle(props.myevts_popup_hdr);
  setkey.setSoftdis("");
  setkey.setRefid(eid);
  setkey.setVendor(vendor); //default "Braintree"
  setkey.setAppendDiv('maincontent');
  setkey.setInternalref(tid);
  var paykey=setkey.getPaykey();
     if(paykey!=""){
	 	paytoken=paykey;
	 	setkey.ccpopup(paykey);
	 }
	 else{
	 //alert("unable to process Not valid paykey");
	 bootbox.confirm(props.myevents_cc_fail_error,function(result){});
	 }

}
function onsucessclose(){
/*document.getElementById("paymentcontinue").innerHTML="";
document.getElementById('paymentbutton').innerHTML="<center>Processing...<br><img src='../images/loading.gif'></center>";*/
if(document.getElementById('paymentprocessimg'))
document.getElementById('paymentprocessimg').innerHTML="<center><img src='../images/ajax-loader.gif'></center>";
if(document.getElementById('paymentbutton'))
document.getElementById('paymentbutton').style.display='none';
if(document.getElementById('paymentprocessimg'))
document.getElementById('paymentprocessimg').style.display='block';
if(document.getElementById('ticket_timer')){
		$('#ticket_timer').remove();
		clearTimeout(reg_timeout);
}
FinalUpdate();
//responseDatacc();
msbt=0;
closeEBpopup();
}

function CloseAction(){
msbt=0;
}

function getProfilePage(){
m=0;	
document.getElementById('processimg').style.display="none";
document.getElementById('profilebuttons').style.display="block";
document.getElementById('payment').style.display='none';
document.getElementById('registration').style.display='none';
document.getElementById('addattendeeStatus').style.display='none';
if(document.getElementById('profileerr'))
document.getElementById('profileerr').style.display='none';
document.getElementById('profile').style.display='block';
if($(document).find('.breadcrumb').length>0)
	$('html, body').animate({ scrollTop: $(".breadcrumb").height()}, 1000);
if(document.getElementById('titleDiv'))
document.getElementById('titleDiv').innerHTML='Attendee Information';
if(document.getElementById('recurringblock'))
document.getElementById('recurringblock').style.display='none';
}

// paymentscreen 


function SubmitPaymentBlockResponse(response){
	//console.log(response);
	$('#paymentprocessimg').html('');		
	$('#addattendeepayment').html('');		
	//notification(response,'success');
	$('#addattendeeStatus').addClass("alert");		
	$('#addattendeeStatus').addClass("alert-success");		
	$('#addattendeeStatus').css("height","50px");		
	$('#addattendeeStatus').html(response);		
	$('#addattendeeStatus').show();	
	$('html, body').animate({ scrollTop: 0 }, 'slow');		
			
	setTimeout(function(){		
		location.reload();		
	}, 1000);
	/*for dynamic start*/
	
/*if(document.getElementById('ticket_timer')){
		$('#ticket_timer').remove();
		clearTimeout(reg_timeout);
}
var statusMsg=response;
$("#tid").val('');
responsesdata=[];
$('#registration').html('');
$('#profile').html('');
$('#payment').html('');
if(document.getElementById('seatingsection')){
	$('seatingsection').html('');
	if(document.getElementById('ticket_timer')){
		$('#ticket_timer').remove();
		clearTimeout(reg_timeout);
	}
}
resetData();
AssignTicketsJsonData(eventid,tktsData);
$('#addattendeeStatus').addClass("alert");
$('#addattendeeStatus').addClass("alert-success");
$('#addattendeeStatus').css("height","50px");
$('#addattendeeStatus').html(statusMsg);
$('#addattendeeStatus').show();
$('html, body').animate({ scrollTop: 0 }, 'slow');*/
	
	/*for dynamic end*/
}

function validateTickets(){
	$('html, body').animate({ scrollTop: 0 }, 'slow');
	showLoading(props.aa_loading_profiles_lbl);
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
	for(var i=0;i<ticketsArray.length;i++){
		var ticketid=ticketsArray[i];
	if(ticketWidgets[ticketid].ticketIsAvailable=='Y'&&ticketWidgets[ticketid].ticketStatusMsg!='NA'){
	var ttype=ticketWidgets[ticketid].GetTicketType();
	if(ttype=='donationType')
	qty=ticketWidgets[ticketid].GetDonationTicketQty();
	else{
	qty=document.getElementById("qty_"+ticketid).selectedIndex;
	if(qty==undefined)
		qty=document.getElementById("qty_"+ticketid).value;
	}
	}
	if(document.getElementById('seatingsection')){
		 	for(var j=0;j<ticket_ids_seats.length;j++){
				if(ticketid==ticket_ids_seats[j]){
					if(qty == 0){
						//allowsel=false;
					}
					else{ 
					
					if(Number(qty)>0){
					
						if(Number(qty)<min_ticketid[ticketid]){
							totalqty=totalqty+Number(qty);
							//totalqty=0;
							allowsel=false;
							hideLoading();
							//alert("for \""+ticketnameids[ticketid]+"\" the minimum seats quantity is "+min_ticketid[ticketid]+", you selected only "+qty+" seats");
							bootbox.confirm(props.aa_for_lbl+" \""+ticketnameids[ticketid]+" "+props.sea_min_qty_selected_msg1+" "+min_ticketid[ticketid]+", "+props.sea_min_qty_selected_msg2+" "+qty+" "+props.sea_min_qty_selected_msg3,function(result){});
							min_sel_qty=false;
							break;
						}
						else if(Number(qty)>max_ticketid[ticketid]){
							totalqty=totalqty+Number(qty);
							//totalqty=0;
							allowsel=false;
							hideLoading();
							//alert("for \""+ticketnameids[ticketid]+"\" the maximum seats quantity is "+max_ticketid[ticketid]+", you selected "+qty+" seats");
							bootbox.confirm(props.aa_for_lbl+" \""+ticketnameids[ticketid]+" "+props.sea_min_qty_selected_msg1+" "+max_ticketid[ticketid]+", "+props.sea_min_qty_selected_msg2+" "+qty+" "+props.sea_min_qty_selected_msg3,function(result){});
							max_sel_qty=false;
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
							}
						}
					}
					}

				}
				else{
				     for(var k=0;k<NOSEAT_tic_id.length;k++){
						if(ticketid==NOSEAT_tic_id[k]){
							noseatqty=noseatqty+Number(qty);
							noseatallowsel=true;
						}
					}
				}
			}
		}
	else if(parseInt(qty) > 0){
		break;
	
	}
	}
	if(document.getElementById('seatingsection')){
		if(parseFloat(totalqty)==0 && parseFloat(noseatqty)==0){
		
			var qty1=0;
			for(var t=0;t<ticketsArray.length;t++){
				qty1=qty1+Number($("#qty_"+ticketsArray[t]).val());
			}
			if(qty1==0){
				hideLoading();
				n=0;
				//alert("Please select ticket(s) Quantity");
				bootbox.confirm(props.aa_no_tkt_select_err_msg,function(result){});
				}
		}
		else if(parseFloat(totalqty)==0 && parseFloat(noseatqty)>0){
			addAttendeeTicketssave();
		}
		else if (!allowsel){
		}
		else{
				checkseatavailibility();
		}
	}
	else{	
		if(parseFloat(qty)==0){
			hideLoading();
			n=0;
			//alert("Please select ticket(s) Quantity");
			bootbox.confirm(props.aa_no_tkt_select_err_msg,function(result){});
		}
		else{
			    addAttendeeTicketssave();
				return;
			}
		}
	}

function validateCheckDate(){
	if(document.getElementById('event_date')){
	if(eventdate==''){
	//alert("select date");
	bootbox.confirm(props.aa_select_date_msg,function(result){});
	return false;
	}
	else
		return true;
	}
	else 
		return false;
	}


	
function getTicketsPage(){
	$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
	n=0;
	if(document.getElementById('ticket_timer')){
		$('#ticket_timer').remove();
		clearTimeout(reg_timeout);
	}
	//del_seat_temp();
	getresponses();
	document.getElementById('registration').style.display='block';
	if($(document).find('.breadcrumb').length>0)
		$('html, body').animate({ scrollTop: $(".breadcrumb").height()}, 1000);
	document.getElementById('profile').style.display='none';
	if(document.getElementById('payment'))
		document.getElementById('payment').style.display='none';
	if(document.getElementById('paymentbutton'))
		document.getElementById('paymentbutton').style.display='none';
	if(document.getElementById('titleDiv'))
		document.getElementById('titleDiv').innerHTML=props.aa_sel_tkts_section_header;
	if(document.getElementById('recurringblock'))
		document.getElementById('recurringblock').style.display='block';
}

	function getresponses(){
	for (var p=0;p<ctrlwidget.length;p++){
	var id=ctrlwidget[p];
	responsesdata[id]=CtrlWidgets[id].GetValue();
	}
  }
	
	
	function getTotals(){
		tktarray=[];
			for(var i=0;i<ticketsArray.length;i++){
			if(parseFloat($('qty_'+ticketsArray[i]).value)>0){
		    var qty=$('qty_'+ticketsArray[i]).value;
		    var fprice=$('finalprice_'+ticketsArray[i]).value;
			var oprice=$('originalprice_'+ticketsArray[i]).value;
			var ofee=$('processfee_'+ticketsArray[i]).value;
			var ffee=$('finalprocessfee_'+ticketsArray[i]).value;
			var ticketgroupid=$('finalprocessfee_'+ticketsArray[i]).value;
			var obj={'finalprice':fprice}
			obj.originalprice=oprice;
			obj.originalfee=ofee;
			obj.finalfee=ffee;
			obj.ticketid=ticketsArray[i];
			obj.qty=qty;
			obj.ticketGroupId=$('ticketGroup_'+ticketsArray[i]).value;
			obj.ticketType=$('ticketType_'+ticketsArray[i]).value;
			tktarray.push(obj);
			}
			}
			var myJSONText = JSON.stringify(tktarray);
			var myObject = JSON.parse(myJSONText);
			$('jsondata').value='';
			$('jsondata').value=myObject.toString();
		$('addattendeetickets').action='AddAttendeeTickets!getTotals?eid='+eventid+'&timestamp='+(new Date()).getTime();
		$('addattendeetickets').request({
		onComplete:getTotalrespone
				});		
		
	}
	
	function getTotalrespone(response){
	var amountsJson=eval('('+response.responseText+')');
	var amonuts=amountsJson.amounts;
	tranid=amountsJson.amounts.tid;
	document.getElementById('tid').value=tranid;
	document.getElementById('totalamounts').style.display='block';
	document.getElementById('total').innerHTML=amountsJson.amounts.totamount;
	if(parseFloat(amountsJson.amounts.disamount)>0){
		document.getElementById('discount').innerHTML=amountsJson.amounts.disamount;
		document.getElementById('netamount').innerHTML=amountsJson.amounts.netamount;
	}
	else{
	document.getElementById('discount').innerHTML='';
	document.getElementById('netamount').innerHTML='';
	document.getElementById('discountsblock').style.display='none';
	document.getElementById('netamountblock').style.display='none';
	}
	
	if(parseFloat(amountsJson.amounts.tax)>0){
	document.getElementById('tax').innerHTML=amountsJson.amounts.tax;
	document.getElementById('grandtotal').innerHTML=amountsJson.amounts.grandtotamount;
	}
	else{
		document.getElementById('taxblock').style.display='none';
		document.getElementById('gtotalblock').style.display='none';
	}
	}
	
	
	
	
	
	function validateProfiles(){
		$('html, body').animate({ scrollTop: 0 }, 'slow');
		//alert(document.getElementById('email_err_msg'));
	if(document.getElementById('email_err_msg'))
		document.getElementById('email_err_msg').innerHTML='';
		var count=0;
	    m++;
		for (var p=0;p<ctrlwidget.length;p++){
		id=ctrlwidget[p];
		if(CtrlWidgets[id].Validate())
		{
			if(id=='buyer_email_1'){
				var bemail=CtrlWidgets[id].GetValue();
				var value=emailchecking(bemail);
				if(value){
					getemailmessagevalue(true);
				}
				else{
					getemailmessagevalue(false);
					count++;
					}
			}
		}
		else{
		
		count++;
		}
		}
		if(count==0){				
			//document.getElementById("profilecontinue").innerHTML="";
			//document.getElementById("profilepageBack").innerHTML="";
			document.getElementById('processimg').innerHTML="<center><img src='../images/ajax-loader.gif'></center>";
			document.getElementById('profilebuttons').style.display='none';
			document.getElementById('processimg').style.display='block';
			if(m==1)
			SubmitProfileData();
		}
		else{
			m=0;
			if(parseInt(count)>1){
				document.getElementById('profileerr').innerHTML="<font color='red'>"+props.aa_there_are_errmsg1+" "+count+" "+props.aa_errors_errmsg2+"</font>";
				$('html, body').animate({ scrollTop: $("#profileerr").height()}, 1000);
				//document.getElementById('profileerr').scrollTo();
			}else{
				document.getElementById('profileerr').innerHTML="<font color='red'>"+props.aa_there_is_an_error_errmsg+"</font>";
				$('html, body').animate({ scrollTop: $("#profileerr").height()}, 1000);
				//document.getElementById('profileerr').scrollTo();
			}
		}
	}

function getTicketsPageforselectedDates(){
	if(tranid!=''){
		del_seat_temp(tranid);
		tranid='';
	}
	if(document.getElementById('addattendeeStatus'))
		document.getElementById('addattendeeStatus').innerHTML='';
	var index=document.getElementById('event_date').selectedIndex;
	eventdate=document.getElementById('event_date').options[index].value;
	if(eventdate!=''){
	showLoading(props.aa_loading_tks_lbl);
	url="AddAttendee!getTicketsforselecteddate?eid="+eventid+"&purpose=manageradd&timestamp="+(new Date()).getTime();;
	/*new Ajax.Request(url,{
			 method: 'get',
			 parameters:{eventdate:eventdate},
			 onSuccess: getTicketsPageforselectedDatesresponse,
			 onFailure:  failureJsonResponse
			  
		 });*/
	
	$.ajax({
		type:'get',
		data:{eventdate:eventdate},
		url:url,
		success:function(result){
			getTicketsPageforselectedDatesresponse(result);
		},
		error:function(){
			failureJsonResponse();
		}
	});

	}
		 else{
		 $('#registration').html('');
		 }
	 }	
		
	
	function  getTicketsPageforselectedDatesresponse(response){
		tktsData=eval('('+response+')'); 
		getTicketsBlock(eventid);	 
		 }
	
	
	function copyByuerData(ticketid){
		var phone='';
		var fname=document.getElementById('q_buyer_fname_1').value;
		var lname=document.getElementById('q_buyer_lname_1').value;
		var email=document.getElementById('q_buyer_email_1').value;
		if(document.getElementById('q_buyer_phone_1'))
		phone=document.getElementById('q_buyer_phone_1').value;
		document.getElementById('q_'+ticketid+'_fname_1').value=fname;
		document.getElementById('q_'+ticketid+'_lname_1').value=lname;
		if(document.getElementById('q_'+ticketid+'_email_1'))
		document.getElementById('q_'+ticketid+'_email_1').value=email;
		if(document.getElementById('q_'+ticketid+'_phone_1'))
		document.getElementById('q_'+ticketid+'_phone_1').value=phone;
		
		}
	 
	
	function loadticketssavebtn(){
	/*YAHOO.util.Event.onContentReady("addattendeeticketsave", function () {
		var oSubmitButton2 = new YAHOO.widget.Button("addattendeeticketsave",{onclick:{fn:validateTickets}});
		});
	YAHOO.util.Event.onContentReady("discountsbtn", function () {
		var oDiscountButton = new YAHOO.widget.Button("discountsbtn",{onclick:{fn:getDiscountAmounts}});
		});*/
	}
	function loadbuttons(){
	/*YAHOO.util.Event.onContentReady("profilecontinue", function () {
		var oSubmitButton2 = new YAHOO.widget.Button("profilecontinue",{onclick:{fn:validateProfiles}});
		});
	YAHOO.util.Event.onContentReady("profilepageBack", function () {
		var oSubmitButton2 = new YAHOO.widget.Button("profilepageBack",{onclick:{fn:getTicketsPage}});
		});*/
	}
	
	// paymentscreen 
	function loadpaymentbtn(){
	/*YAHOO.util.Event.onContentReady("paymentcontinue", function () {
		var oSubmitButton3 = new YAHOO.widget.Button("paymentcontinue",{onclick:{fn:submitPayment}});
		});
	YAHOO.util.Event.onContentReady("paymentback", function () {
		var oSubmitButton2 = new YAHOO.widget.Button("paymentback",{onclick:{fn:getProfilePage}});
		});	*/
		
		
		/*$('input.payment').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});*/
		
		
		$('input#paidpayment').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});
		$('input#ccpayment').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});
		$('input#nopayment').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});
		
		$('input#paidpayment').on('ifChecked', function(event){
			selectedpctype=$(this).attr('id');
		});
		$('input#ccpayment').on('ifChecked', function(event){
			selectedpctype=$(this).attr('id');
		});
		$('input#nopayment').on('ifChecked', function(event){
			selectedpctype=$(this).attr('id');
		});
		
		
		/*$('input.payment').on('ifChecked', function(event){
			selectedpctype=$(this).attr('id');
		});
	*/
	}
	
	function seatingticketidlabel(){
     /*new Ajax.Request('SeatingAddManual!getAllTickets?timestamp='+(new Date()).getTime(), {
	 method: 'get',
	 parameters:{eid:eventid},
	 onSuccess: seatingticketresponseassign,
	 onFailure:  failureJsonResponse
	 });*/
		
		$.ajax({
	        url: 'SeatingAddManual!getAllTickets?timestamp='+(new Date()).getTime(),
	        type: 'get',
	        data: {eid:eventid},
	        success: function (response) {
	        	seatingticketresponseassign(response);
	        },
	        error: function () {
	            alert("error");
	        }
	    }); 
		
		
}

function seatingticketresponseassign(response){
var seatingticket=response;
seatingticketidresponse=eval('(' + seatingticket + ')');
Initialize("registration");
}

function seatingticketresponse(){
notavailableticketids=[];
var cur_ids=seatingticketidresponse.seatticketid+"";

var sel_tic_id=new Array();
ticket_ids_seats=cur_ids.split(",");
sel_tic_id=cur_ids.split(",");
for(i=0;i<sel_tic_id.length;i++){
	var cur_id=sel_tic_id[i];
	var dropdown_id='qty_'+cur_id;
	var dropdown_id1='show_'+cur_id;
	if(document.getElementById(dropdown_id) && $(dropdown_id).disabled==true){
		notavailableticketids[notavailableticketids.length]=cur_id;
		memberseatticketids[memberseatticketids.length]=cur_id;
		var tic_dropdown=document.getElementById(dropdown_id);
		var drop_down_length=tic_dropdown.length;
		min_ticketid[cur_id]=tic_dropdown[1].value;
		max_ticketid[cur_id]=tic_dropdown[Number(drop_down_length)-1].value;
	}
	else if(document.getElementById(dropdown_id) && $(dropdown_id).type!='hidden'){
		var td_id="td_ticketid_"+cur_id;
		var tic_dropdown=document.getElementById(dropdown_id);
		var drop_down_length=tic_dropdown.length;
		min_ticketid[cur_id]=tic_dropdown[1].value;
		max_ticketid[cur_id]=tic_dropdown[Number(drop_down_length)-1].value;
		document.getElementById(td_id).innerHTML="";
		document.getElementById(td_id).innerHTML="<center title='Select Seats below'><span id='"+dropdown_id1+"' style='font-size:14px;margin-left:40px' >0</span></center><input value='0' type='hidden' name='"+dropdown_id+"' id='"+dropdown_id+"'>"+props.aa_sel_seats_lbl;
		
	}
	else{
		notavailableticketids[notavailableticketids.length]=cur_id;
	}
	
}
}

function getseatingsection(){
     document.getElementById('seatingsection').innerHTML="<center><img src='../images/ajax-loader.gif'></center>";
		/*new Ajax.Request('SeatingAddManual!getSeatingSection?timestamp='+(new Date()).getTime(), {
	 method: 'get',
	 parameters:{eid:eventid,eventdate:eventdate,tid:tranid},
	 onSuccess: seatingsectionresponse,
	 onFailure:  failureJsonResponse
	 });*/
     
     $.ajax({
    	type:'get',
    	data:{eid:eventid,eventdate:eventdate,tid:tranid},
    	url:'SeatingAddManual!getSeatingSection?timestamp='+(new Date()).getTime(),
    	success:function(result){
    		seatingsectionresponse(result);
    	},
    	error:function(){failureJsonResponse();}
     });
     
}

function seatingsectionresponse(response){

var data=response;
var responsedata=eval('(' + data + ')');
seatingsectionresponsedata=responsedata;
allsectionid=responsedata.allsectionid;
//alert(allsectionid.length);
mins_left=responsedata.timeout;
resetobjectdata();
allsectionname=responsedata.allsectionname;
ticketseatcolor=responsedata.ticketseatcolor;
ticket_groupnames=responsedata.ticketgroups;
var layoutdisplay=responsedata.venuelayout;
var venuelayoutlink="";
if(layoutdisplay=="URL"){
	venuepath="\""+responsedata.venuepath+"\"";
	venuelayoutlink="<a href=#layout onclick='getvenuelayout("+venuepath+")' style='float:right;'>"+responsedata.venuelinklabel+"</a>";
}
if(allsectionid.length==1){
	sectionid=allsectionid[0];
	$('#seatingsection').html('');
	$('#seatingsection').html("<html><head>"+
	"</head><body><input type='hidden' name='section' id='section' value='"+sectionid+"'>"+venuelayoutlink+"<div id='seatcell' style=' width: 100%; height:100%; border: 0px solid rgb(51, 102, 153); padding: 10px;'>"+
	"</div></body></html>");
	getticketseatsdisplay();
	generateSeating(responsedata.allsections[sectionid]);
	
}
else{
	sectionid=allsectionid[0];
	$('#seatingsection').html('');
	var sectionlistdropdown=generate_Sectiondropdown(allsectionid,allsectionname);
	$('#seatingsection').html("<html><head>"+
	"</head><body><div style='float:left;'>Select Section: "+sectionlistdropdown+"</div> &nbsp;&nbsp;"+venuelayoutlink+"<br><div id='seatcell' style=' width: 100%; height: 100%; border: 0px solid rgb(51, 102, 153); padding: 10px;'>"+
	"</div></body></html>");
	getticketseatsdisplay();
	generateSeating(responsedata.allsections[sectionid]);
}
if(document.getElementById('eventdate'))
 document.getElementById('eventdate').disabled=false;
}

function getJsonFormat(){
	var jsonFormat="{";
	if(document.getElementById('seatingsection')){
		for(i=0;i<ticketsArray.length;i++){
			if(sel_ticket[ticketsArray[i]]!=undefined){
				jsonFormat=jsonFormat+'"'+ticketsArray[i]+'":['+sel_ticket[ticketsArray[i]]+']';
				if(i!=ticketsArray.length-1){
				jsonFormat=jsonFormat+",";
				}
				}
		}
	}
	jsonFormat=jsonFormat+"}";
	return jsonFormat;
}
function checkseatavailibility(){
	var jsonFormat="{";
	var sec_ids="";
	for(i=0;i<allsectionid.length;i++){
		if(sec_ids==""){
			sec_ids=allsectionid[i];
		}
		else{
			sec_ids=sec_ids+"_"+allsectionid[i];
		}
		if(section_sel_seats[allsectionid[i]]!=undefined){
			jsonFormat=jsonFormat+'"'+allsectionid[i]+'":['+section_sel_seats[allsectionid[i]]+']';
				if(i!=allsectionid.length-1){
				jsonFormat=jsonFormat+",";
				}
		}
	}
	
	jsonFormat=jsonFormat+"}";
	/*new Ajax.Request('SeatingAddManual!checkSeatStatus?timestamp='+(new Date()).getTime(),{
	method: 'get',
	parameters:{eid:eventid,tid:tranid,eventdate:eventdate,selectedseats:jsonFormat,allsectionids:sec_ids},
	onSuccess: seatavailable,
	onFailure: seatunavailable
	});*/
	
	$.ajax({
		type:'get',
		data:{eid:eventid,tid:tranid,eventdate:eventdate,selectedseats:jsonFormat,allsectionids:sec_ids},
		url:'SeatingAddManual!checkSeatStatus?timestamp='+(new Date()).getTime(),	
		success:function(result){
			seatavailable(result);
		},
		error:function(){seatunavailable();}
	});
	
	
		
}

function seatavailable(response){
var data=response;
var availablejson=eval('('+data+')');
if(availablejson.status == "success"){
	isavailableseat="yes";
	addAttendeeTicketssave();
}
else{
	isavailableseat="no";
	for(i=0;i<ticketsArray.length;i++){
		var ticketd=ticketsArray[i];
		if(document.getElementById("qty_"+ticketd).type=='hidden'){
			document.getElementById("qty_"+ticketd).value=0;
			if(document.getElementById("show_"+ticketd))
			document.getElementById("show_"+ticketd).innerHTML=0;
		}
	}
	//alert("Few of the seats you are trying to book are currently on hold or already soldout");
	bootbox.confirm(props.sea_few_seats_onhold,function(result){});
	document.getElementById('imageLoad').style.display="none";
	document.getElementById('imageLoad').innerHTML="";
	if(document.getElementById('registration')){
		$('#registration').show();
	}
	if(document.getElementById('recurringblock')){
		$('#recurringblock').show();
	}
	if(document.getElementById('seatingsection')){
	//hideTicLoadingImage();
		$('#seatingsection').html('');
		getseatingsection();
	}
	
}
}

function seatunavailable(){
//alert("Error processing. Try back in some time");
bootbox.confirm(props.global_error_in_processing,function(result){});
}

function getTicketsAvailabilityMsg(){
	var data=tktsData.availibilitymsg;
	for(i=0;i<ticketsArray.length;i++){
		var tktid=ticketsArray[i];
		str=props.global_avail_lbl+": "+data["remaining_"+tktid];
		str=str+" "+props.aa_sold_ticket_lbl+": "+data["soldqty_"+tktid];
		str=str+" "+props.global_total_lbl+": "+data["capacity_"+tktid];
			if(document.getElementById(tktid)){
				var availmsg="availmsg_"+tktid;
				var cellid="divmsg_"+tktid;
				var cell=document.getElementById(cellid);
				var div=document.createElement("div");	
				div.setAttribute("id",availmsg);
				div.setAttribute("class","smallestfont");
				div.setAttribute("width","55%");
				cell.appendChild(div);
				
				if(document.getElementById(availmsg))
					document.getElementById(availmsg).innerHTML=str;
			}
		}	
	}	
	
function showLoading(message){
	//window.scrollTo(0,0);
	hideblocks();
	document.getElementById('imageLoad').style.display="block";
	document.getElementById('imageLoad').innerHTML="<center>"+message+"<br/><br/><img src='../images/ajax-loader.gif'></center>";	
	//document.getElementById('imageLoad').innerHTML="<center>"+message+"...<br><br/><img src='../images/ajax-spinner.gif'></center>";	
}
function hideLoading(){
	document.getElementById('imageLoad').style.display="none";
	document.getElementById('imageLoad').innerHTML="";
		showall();	
}
function hideblocks(){
	document.getElementById('registration').style.display='none';
	document.getElementById('addattendeeStatus').style.display='none';
	if(document.getElementById('recurringblock'))
		document.getElementById('recurringblock').style.display='none';

}
function showall(){
document.getElementById('registration').style.display='block';
	document.getElementById('addattendeeStatus').style.display='block';
	if(document.getElementById('recurringblock'))
		document.getElementById('recurringblock').style.display='block';
}

function emailchecking(str) {     
		/*var at="@"
		var dot="."
		var lat=str.indexOf(at)
		var lstr=str.length
		var ldot=str.indexOf(dot)

		if (str.indexOf(at)==-1){
		   return false
		}

		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   return false
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		    return false
		}

		 if (str.indexOf(at,(lat+1))!=-1){
		    return false
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    return false
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
		    return false
		 }
		
		 if (str.indexOf(" ")!=-1){
		    return false
		 }

 		 return true	*/			
	var emailExp ="^[a-z0-9!#$%&*+/=?^_`:()'{|}~-]+(?:\\.[a-z0-9!#$%&*+/=?^_`:()'{|}~-]+)*@(?:[a-z0-9!#$%&*+/=?^_`:()'{|}~-](?:[_a-z0-9-!#$%&*+/=?^_`:()'{|}~-]*[_a-z0-9-!#$%&*+/=?^_`:(){|}'~-])?\\.)+[_a-z0-9-!#$%&*+/=?^_`:()'{|}~-](?:[_a-z0-9-!#$%&*+/=?^_`:()'{|}~-]*[_a-z0-9-!#$%&*+/=?^_`:()'{|}~-])?$";
 	//var patt=new RegExp(emailExp,"i");
 	str=str.toLowerCase();
	var result=str.match(emailExp);
	if(result==null)
	return false;
	else
	return true; 
	}
function getemailmessagevalue(type){
	if(type){
		if(document.getElementById('email_err_msg')){
			document.getElementById('email_err_msg').innerHTML='';
			document.getElementById('email_err_msg').style.display='none';
		}
	}
	else{
		if(document.getElementById('email_err_msg')){
		}
		else{
			var cell=document.getElementById('buyer_email_1');
			var div=document.createElement('div');
			div.setAttribute('id','email_err_msg');
			div.setAttribute("style","color: red;");
			cell.appendChild(div);
		}
		document.getElementById('profileerr').style.display='block';
		document.getElementById('email_err_msg').innerHTML=props.la_supp_popup_invalid_mail;
		document.getElementById('email_err_msg').style.display='block';
		$('html, body').animate({ scrollTop: $("#email_err_msg").height()}, 1000);
		
	}
}

function resetData(){
	tranid='';
	profileJsondata='';
	CtrlWidgets=[];
	responsesdata=[];
	tktarray=[];
	selectedtktarray=[];
	qtyarray=[];
	quesarray='';
	jsonprofile='';
	max_ticketid=new Object();
	min_ticketid=new Object();
	seatingticketidresponse="";
	sel_ticket=new Object();
	sel_ticket=new Object();
	ticket_count=new Object();
	seatcode_ticid=new Object();
	sel_select=new Object();
	pctype='';
	paytoken='';
	selectedpctype='';
	m=0;
	n=0;
}

function checkPCType(type){
	selectedpctype=type;
}






