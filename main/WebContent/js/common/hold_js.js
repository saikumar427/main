/*******profile levele ticket status*********/
var transid="";
function checkticketsstatusprofilelevel(tid)
{
if(discountfalgpro=='false')
if(checkdisc()){return;}

transid=tid
var allticketids="";
var seltktids="";
var jsonFormat="{";
	for(i=0;i<ticketsArray.length;i++){
		if(allticketids!="")
			allticketids=allticketids+"_"+ticketsArray[i];
		else
			allticketids=ticketsArray[i];
		var curid="qty_"+ticketsArray[i];
		var qty=document.getElementById(curid).value;
		if(Number(qty)>0){
	          if(seltktids!="")
			seltktids=seltktids+"_"+ticketsArray[i];
		else
			seltktids=ticketsArray[i];
			  
			jsonFormat=jsonFormat+'"'+ticketsArray[i]+'":'+qty;
			if(i!=ticketsArray.length-1){
				jsonFormat=jsonFormat+",";
			}
		}
	}
	jsonFormat=jsonFormat+"}";
	new Ajax.Request('/embedded_reg/profileticketstatus.jsp?timestamp='+(new Date()).getTime(),{
	method: 'get',
	parameters:{eid:eventid,tid:tid,eventdate:evtdate,selected_tickets:jsonFormat,all_ticket_ids:allticketids,seltktids:seltktids},
	onSuccess: proticketsavailable,
	onFailure: seatunavailable
	});


}
function proticketsavailable(response){
var ticdata=response.responseText;
var availabletktdjson=eval('(' + response.responseText + ')');
//sssalert("tidstatus "+availabletktdjson.responsemap.status);
var status=availabletktdjson.responsemap.status;

if(status=="success")
{
      if($('seatingsection'))
        {
			checkseatsavailibilityatprofilesubmit(transid);
		}
  else{
        submitProfiles(transid);
      }
}
else{
	var jobj=availabletktdjson.responsemap;
	ticketsunavailable(jobj);
}


		




}


/*****timme out******/
function gettkttimer(){
	var divpopup=document.createElement("div");
	divpopup.setAttribute('id','ticketpoup_div');
	divpopup.className='ticketpoup_div';
	var cell=$('container');
	//var cell=document.body;
	var div=document.createElement("div");
	div.setAttribute('id','ticket_timer');
	div.className='ticket_timer';
	//div.className='initial_timer';
	div.setAttribute('style','display:block;width:100px;');
	cell.appendChild(divpopup);
	cell.appendChild(div);
	
	$('ticket_timer').innerHTML='Time left to buy<br>';
	//window.scrollTo("0","25");
	var divcell=$('ticket_timer');
	var span=document.createElement("div");
	span.setAttribute('id','time_left_tobuy');
	span.setAttribute('class','spannormal');
	divcell.appendChild(span);
	mins_left = 14;
	//mins_left=0;
    s_left = 59;
	//s_left = 10;
	mins_remain=14;
	secs_remain=59;
	
	fifteenMinutestkt();
}
function fifteenMinutestkt(){
   s_left--;
if(s_left<0) {
   s_left="59";
   mins_left--;
}

if(mins_left==-1) {
	clearTimeout(reg_timeout);
	
	document.getElementById('time_left_tobuy').innerHTML='<center>Time Out</center>';
	var t=document.getElementById('tid').value;
	


if(clickcount!=undefined){//clickcount=0;
}
   gettimeoutpopup("timeout");
   
}
else{
if(mins_left<10) {
   mins_remain='0'+mins_left;
}
else {
   mins_remain=mins_left;
}
if(s_left<10) {
   secs_remain='0'+s_left;
}
else {
   secs_remain=s_left;
}
   document.getElementById('time_left_tobuy').innerHTML='<center>'+mins_remain+':'+secs_remain+'</center>';
   reg_timeout=setTimeout('fifteenMinutestkt()',1000);
}
   }




function gettimeoutpopup(msg){

	var divpopupcontent="Sorry, timed out!<br><input type='button' value='Try Again' onclick='timeouttkt()'><a href=# onclick=timeouttkt()><img src='/home/images/images/close.png' class='divimage'></a>";
	
	//$("").attr("tabindex", "-1");;;
	
	if($('registration')){

		jQuery("#registration input").attr("tabindex", "-1");
		jQuery("#registration a").attr("tabindex", "-1");
		jQuery("#registration select").attr("tabindex", "-1");
	}
	if($('profile')){

		jQuery("#profile input").attr("tabindex", "-1");
		jQuery("#profile a").attr("tabindex", "-1");
	}
	if($('paymentsection')){

		jQuery("#paymentsection input").attr("tabindex", "-1");
		jQuery("#paymentsection a").attr("tabindex", "-1");
	}
	
	
	
	
	
	if($('ebeecreditpopup')){
	closeebeecreditspopup();}
	
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='block';
	}
	
	//$('#ticketpoup_div').bgiframe();
	document.getElementById('ticketpoup_div').innerHTML=divpopupcontent;
	document.getElementById('ticketpoup_div').style.display="block";
	var pos=jQuery(document).scrollTop()+130;
		document.getElementById('ticketpoup_div').style.top=pos+'px';	
	//document.getElementById('ticketpoup_div').style.top='50%';
	document.getElementById('ticketpoup_div').style.left='26%';
	//window.scrollTo("0","150");
}


function timeouttkt(){
	$('ticketpoup_div').hide();
	document.getElementById('ticketpoup_div').innerHTML='';
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='none';
	}
		if($('registration')){

		jQuery("#registration input").attr("tabindex", "-1");
		jQuery("#registration a").attr("tabindex", "-1");
		jQuery("#registration select").attr("tabindex", "-1");
	}
	if($('profile')){

		jQuery("#profile input").attr("tabindex", "-1");
		jQuery("#profile a").attr("tabindex", "-1");
	}
	if($('paymentsection')){

		jQuery("#paymentsection input").attr("tabindex", "-1");
		jQuery("#paymentsection a").attr("tabindex", "-1");
	}
	if($('registration')){
$('registration').hide();
		
		
	}
	if($('profile')){
$('profile').hide();
		
	}
	if($('paymentsection')){

$('paymentsection').hide();
	}
	
	showTicLoadingImage('loading');
	timeout_delete_locked_temp('wait');
	
	
} 

function timeout_delete_locked_temp(pur){
//var evid=document.getElementById('eid').innerHTML.value;
 
var t=document.getElementById('tid').value;
	new Ajax.Request('/embedded_reg/seating/delete_temp_locked_tickets.jsp?timestamp='+(new Date()).getTime(), {
	method: 'post',
	parameters:{eid:eventid,tid:t},
	onComplete:function(){if($('paymentsection')){
		$('paymentsection').hide();
		$('paymentsection').innerHTML='';
		if(pur!='normal')
		window.location.reload(true);
		
	}
	if($('ticket_div')){
		$('ticket_div').hide();
	}
	if($('ticket_timer')){
		$('ticket_timer').remove();
		clearTimeout(reg_timeout);
	
	}}
	});
  
   }
   
function hidetimerpopupfortkt(){
	if($('ticketpoup_div')){
		var div=$('ticketpoup_div');
		div.className='ticketpoup_div1';
	}
}



function displaydivpopuptimeupfortkt(){
	if($('ticketpoup_div')){
		//if(mins_left==-1)
		
			var div=$('ticketpoup_div');
			div.className='ticketpoup_div';
			if(mins_left==-1){
			if(document.getElementById("backgroundPopup")){
				document.getElementById("backgroundPopup").style.display='block';
			}
			}
		
	}
}

function checkdisc()
{
if(discountfalgpro=='false')
{
var dismsg="Applied DiscountCode Is Unavailable<br/><br/><br/>";

if($('ticketunavailablepopup_div')){}else{
	var divpopup=document.createElement("div");
	divpopup.setAttribute('id','ticketunavailablepopup_div');
	divpopup.className='ticketunavailablepopup_div';
	var cell=$('container');
	cell.appendChild(divpopup);
	}


getunavailablepopup(dismsg);
document.getElementById('couponcode').value="";
document.getElementById('invaliddiscount').innerHTML='';
getDiscountAmounts();
if($('paymentsection')){
		$('paymentsection').hide();
		}
 return  true;
	 

}

}


