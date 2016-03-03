var backtoboxoffice="";
function getTicketsJson(eid){
eventid=eid;
seating_enabled_tkt_wedget=document.getElementById('isseatingevent').value;
venueid=document.getElementById('venueid').value;
if($('nts_enable'))
	ntsdata["nts_enable"]=$('nts_enable').value;
if($('nts_commission'))	
	ntsdata["nts_commission"]=$('nts_commission').value;
if($('login-popup'))
	ntsdata["login-popup"]=$('login-popup').value;
if($('referral_ntscode'))
	ntsdata["referral_ntscode"]=$('referral_ntscode').value;
if($('fbappid'))
	ntsdata["fbappid"]=$('fbappid').value;

if(document.getElementById('eventdate')){
	insertOptionBefore('tickets');
	$('registration').innerHTML='';
	$('profile').innerHTML="";
	var index=document.getElementById('eventdate').selectedIndex;
	evtdate=document.getElementById('eventdate').options[index].value;
	if(!$('backtoboxoffice')){	
		backtoboxoffice="";
	if($('username').value!=""){
		backtoboxoffice="<a class='button backtoboxoffice' id='backtoboxoffice' style='max-width:115px; float:right;'>Back To Box Office</a><br><br>";	
	}
	jQuery("#mobile").prepend(backtoboxoffice);
	if($('backtoboxoffice')){
		jQuery("#backtoboxoffice").click(function(){
			jQuery("#registration").removeClass("current")
												 .attr("class","slide out");
		window.location.href="/view/"+$('username').value;
		});
	}
	}
	if(evtdate=='Select Date'){
		evtdate='';
		return;
	}	
	else{
		showTicLoadingImage("Loading...");
		document.getElementById('eventdate').disabled=true;
		}
		
}
else{
showTicLoadingImage("Loading...");
}
if(document.getElementById('ticketurlcode'))
ticketurl=document.getElementById('ticketurlcode').value;

timetktjsonajax=new Date();
new Ajax.Request('/reg_widget/ticketsjson.jsp?timestamp='+(new Date()).getTime(), {
  method: 'get',
  parameters:{eid:eid,ticketurl:ticketurl,evtdate:evtdate,seating_enable:seating_enabled_tkt_wedget},
  onSuccess: ticketJsonResponse,
  onFailure:  failureJsonResponse
  });
 }
 
 function  getTicketsBlock(eventid){
 if(document.getElementById('eventdate')){
var index=document.getElementById('eventdate').selectedIndex;
evtdate=document.getElementById('eventdate').options[index].value;
 }
 if(evtdate=='Select Date')
 evtdate='';
 if(document.getElementById('trackcode'))
 track=document.getElementById('trackcode').value;
if(document.getElementById('discountcode'))
 discountcode=document.getElementById('discountcode').value;
 if(document.getElementById('ticketurlcode'))
 ticketurl=document.getElementById('ticketurlcode').value;
 if(document.getElementById('context'))
 context=document.getElementById('context').value;
if(document.getElementById('oid'))
oid=document.getElementById('oid').value;
if(document.getElementById('domain'))
 domain=document.getElementById('domain').value;
var ticketlabel="Ticket Name",pricelabel="Price",feelabel="Fee",qtylabel="Qty",orderbutton="Buy Tickets";
if(tktsData.RegFlowWordings["event.reg.ticket.name.label"]!=undefined)
	ticketlabel=tktsData.RegFlowWordings["event.reg.ticket.name.label"];
if(tktsData.RegFlowWordings["event.reg.ticket.price.label"]!=undefined)
	pricelabel=tktsData.RegFlowWordings["event.reg.ticket.price.label"];
if(tktsData.RegFlowWordings["event.reg.processfee.label"]!=undefined)
	feelabel=tktsData.RegFlowWordings["event.reg.processfee.label"];
if(tktsData.RegFlowWordings["event.reg.ticket.qty.label"]!=undefined)
	qtylabel=tktsData.RegFlowWordings["event.reg.ticket.qty.label"];
if(tktsData.RegFlowWordings["event.reg.orderbutton.label"]!=undefined)	
	orderbutton=tktsData.RegFlowWordings["event.reg.orderbutton.label"];
var htmldata='<form id="regform" name="regform" action="/embedded_reg/regformaction.jsp" method="post"><input type="hidden" value="" id="tid" name="tid"><input type="hidden" name="clubuserid" id="clubuserid" value="" /> <input type="hidden" name="track" id="track" value="'+track+'"><input type="hidden" name="ticketurlcode" id="ticketurlcode" value="'+ticketurl+'"><input type="hidden" name="discountcode" id="discountcode" value="'+discountcode+'"><table width="100%" cellpadding="0" cellspacing="0" valign="top" class="tableborder"><tr>'+
'<td width="100%"  cellpadding="0" cellspacing="0">'+
'<table width="100%" cellpadding="0" cellspacing="0" class="ticketlableheader"><tr>'+
'<td width="55%"><b>'+ticketlabel+'</b></td>'+
'<td width="15%" align="right"><b>'+pricelabel+' ('+tktsData.currencyformat+')</b></td>'+
'<td width="15%" align="right" id="feelabel" ><b>'+feelabel+' ('+tktsData.currencyformat+')</b></td>'+
'<td width="20%" align="right"><b>'+qtylabel+'</b></td></tr></table></td></tr><tr><td><hr class="hrline" /></td></tr>';

for(i=0;i<tktsData.groups.length;i++){
		var grpdata=tktsData.groups[i];
		if(grpdata.grpname!=""){
			htmldata+="<tr class='groupdesc'><td><div id='"+grpdata.grpid+"' class='tckgrpname'>"+grpdata.grpname+"</div>";
			if(grpdata.grpdesc!=""){
				htmldata+="<span id='desc_"+grpdata.grpid+"'>"+grpdata.grpdesc+"</span>";
			}
			htmldata+="</td></tr>";
		}
		for(j=0;j<grpdata.tickets.length;j++){
			htmldata+="<tr><td width='100%'><div id='"+grpdata.tickets[j]+"' class='tktWedgetClass'></div></td></tr><tr><td><hr class='hrline' /></td></tr>";
		}
	}
	
	if(tktsData.isDiscountExists){
		discountboxlabel="event.reg.discount.box.label","Have a discount code, enter it here";
		if(tktsData.RegFlowWordings["event.reg.discount.box.label"]!=undefined)
			discountboxlabel=tktsData.RegFlowWordings["event.reg.discount.box.label"];
		var discountcode="";
		if($('discountcode'))
			discountcode=$('discountcode').value;
		var discountApplyLabel="Apply";
		if(tktsData.RegFlowWordings["discount.code.applybutton.label"]!=undefined)
			discountApplyLabel=tktsData.RegFlowWordings["discount.code.applybutton.label"];
		var  discountbox="<input type='text' name='couponcode' id='couponcode' size='10' value='"+discountcode+"' onkeypress='return ignorekeypress(event)'/>";			
		var applybutton="<input type='button' name='submit' id ='discountsbtn' value='"+discountApplyLabel+"'  onClick='getDiscountAmounts();'  />";

		htmldata+='<tr><td valign="top" width="100%"><table width="100%" cellpadding="0" cellspacing="0">'
						  +'<tr ><td class="discount" width="100%" cellpadding="0" cellspacing="0" class="yui-skin-sam">'
						  +discountboxlabel+'&nbsp;'+discountbox+'&nbsp;'+applybutton
						  +'<span id="discountmsg"></span><span id="invaliddiscount"></span>'
						  +'</td></tr></table></td></tr>';
	}
	if(seating_enabled_tkt_wedget=="YES"){
		htmldata+='<tr><td height="10px"></td></tr><tr><td width="100%" id="seatingsection"> </td></tr>';
	}
	
	htmldata+='<tr><td width="100%"><table width="100%" cellpadding="0" cellspacing="0"><tr><td align="left" width="100%" id="registerBttn" class="yui-skin-sam"><center><input type="hidden" id="actiontype" name="actiontype" value="" /><li style="list-style: none;"><a><input type="button" class="buybutton" name="submit"  id="orderbutton" value="'+orderbutton+'" onClick="validateTickets();"  /></a></li></center></td></tr>';
	/*
	<input type="hidden" id="actiontype" name="actiontype" value="" /><input type="button" name="submit"  id="orderbutton" value="'+orderbutton+'"  onClick="validateTickets();"  />
	
	<center><input type='hidden' id='actiontype' name='actiontype' value='' /><li style='list-style: none;'><a><input type='button' class='buybutton' name='submit'  id='orderbutton' value='"+orderbutton+"' onClick='validateTickets();'  /></a></li></center>
	*/
	htmldata+="</table></form>";
	//alert(seating_enabled_tkt_wedget+"--"+venueid);

	//$('registration').hide();
	document.getElementById("registration").innerHTML="";
	//backtoboxoffice="";
	 if(document.getElementById('eventdate')){
		backtoboxoffice="<a class='back' id='backtorecurringdateselection' style='max-width:115px; float:right;'>Back</a><br><br>";
	}
	else{
		if($('username').value!=""){
			backtoboxoffice="<a class='button backtoboxoffice' id='backtoboxoffice' style='max-width:115px; float:right;'>Back To Box Office</a><br><br>";	
		}
	}
	document.getElementById("registration").innerHTML=backtoboxoffice+htmldata;
	 hideTicLoadingImage();
	if(document.getElementById("eventdate"))
			document.getElementById("eventdate").disabled=false;
	ticketsArray=new Array();
	Initialize("registration");
	if(seating_enabled_tkt_wedget=="YES"){
		//getseatingsection();
		if(document.getElementById("eventdate"))
			document.getElementById("eventdate").disabled=true;
				$('orderbutton').disabled=true;
	}
	if(discountcode!=null&&discountcode!=''&&discountcode!='null')
		getDiscountAmounts();
		if($('eventstatus')){
			if( $('eventstatus').value=='CLOSED')
				$('orderbutton').style.display='none';
		}
		jQuery("#mobile").addClass("slide in reverse");
		jQuery("#mobile").removeClass("current");
		jQuery('registration').addClass('current slide in');
		$('registration').className='current slide in';
		//$('registration').show();
		//$('mobile').hide();
	if($('backtorecurringdateselection')){
		jQuery("#backtorecurringdateselection").click(function(){
			jQuery("#registration,#profile,#paymentsection").removeClass("current");
			jQuery("#registration").attr("class","slide in reverse");
			jQuery("#mobile").attr("class","slide in reverse current");
			$('registration').hide();
			$('mobile').show();
		});
  }  
  else if($('backtoboxoffice')){
 jQuery("#backtoboxoffice").click(function(){
	jQuery("#registration").removeClass("current")
	.attr("class","slide in reverse");
	window.location.href="/view/"+$('username').value;
	});
}

}

function submitTickets(){
$('registration').className='';
var fbuserid=0;
if(fblogindata["first_name"]!=undefined){
	fname=fblogindata["first_name"];
	lname=fblogindata["last_name"];
	email=fblogindata["email"];
}
if(fblogindata.id!=undefined){
	fbuserid=fblogindata.id;
	becamepartner=true;
}	
if($('pageheader')){ 
var x=jQuery('#pageheader').position();
window.scrollTo("0",""+Number(x.top)+"");
}
var jsonFormat=getJsonFormat();
var seating_enable="NO";
if($('seatingsection')){
seating_enable="YES";
}
var ticketids=ticketsArray.join(",");
document.getElementById('actiontype').value='Order Now';
var registrationsource="regular";
if($('registrationsource')){
	registrationsource=$('registrationsource').value;
}
$('regform').action='/embedded_reg/regformaction.jsp?timestamp='+(new Date()).getTime(),
$('regform').request({
parameters:{evtdate:evtdate,code:code,context:context,sectionid:sectionid,selected_ticket:jsonFormat,seating_enabled:seating_enable,ticketids:ticketids,eid:eventid,registrationsource:registrationsource,fbuserid:fbuserid,nts_enable:ntsdata["nts_enable"],nts_commission:ntsdata["nts_commission"],referral_ntscode:ntsdata["referral_ntscode"],fname:fname,lname:lname,email:email},
onComplete:submitTicketrepone
});
}