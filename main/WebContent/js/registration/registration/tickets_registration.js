function getTicketsJson(eid){
eventid=eid;
if(document.getElementById('waitlistId'))
	waitlistId=document.getElementById('waitlistId').value;
seating_enabled_tkt_wedget=$('isseatingevent').value;
venueid=$('venueid').value;
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
if(document.getElementById('eventdate')){//if eventdate id exists it is recurring event.
	insertOptionBefore('tickets');
	$('registration').innerHTML='';
	$('profile').innerHTML="";
	var index=document.getElementById('eventdate').selectedIndex;
	evtdate=document.getElementById('eventdate').options[index].value;
	if(evtdate=='Select Date'){
		evtdate='';
		return;
	}	
	else{
		showTicLoadingImage(props.loading);
		document.getElementById('eventdate').disabled=true;
		}
}
else{
showTicLoadingImage(props.loading);
}
if(document.getElementById('ticketurlcode'))
ticketurl=document.getElementById('ticketurlcode').value;

timetktjsonajax=new Date();
new Ajax.Request('/reg_widget/ticketsjson.jsp?timestamp='+(new Date()).getTime(), {
  method: 'get',
  parameters:{eid:eid,ticketurl:ticketurl,evtdate:evtdate,seating_enable:seating_enabled_tkt_wedget,wid:waitlistId,priregtoken:priRegToken,priregtype:priRegType,prilistid:priListId},
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
 //alert("discountcode"+discountcode);
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
'<td width="15%" align="right" style="padding: 4px;"><b>'+pricelabel+' ('+tktsData.currencyformat+')</b></td>'+
'<td width="15%" align="right" id="feelabel" style="padding: 4px;"><b>'+feelabel+' ('+tktsData.currencyformat+')</b></td>'+
'<td width="20%" align="right" style="padding: 4px;"><b>'+qtylabel+'</b></td></tr></table></td></tr><tr><td><hr class="hrline" /></td></tr>';

for(i=0;i<tktsData.groups.length;i++){
		var grpdata=tktsData.groups[i];
		if(grpdata.grpname!=""){
			htmldata+="<tr class='groupdesc'><td><div id='grp_"+grpdata.grpid+"' class='tckgrpname'>"+grpdata.grpname+"</div>";
			if(grpdata.grpdesc!=""){
				htmldata+="<span id='desc_grp_"+grpdata.grpid+"'>"+grpdata.grpdesc+"</span>";
			}
			htmldata+="</td></tr>";
		}
		for(j=0;j<grpdata.tickets.length;j++){
			htmldata+="<tr><td width='100%'><div id='"+grpdata.tickets[j]+"' class='tktWedgetClass'></div></td></tr><tr><td><hr class='hrline' /></td></tr>";
		}
	}
	var discnbuy='dn';
	var discsec='';
	var domain="/";
	if(tktsData.i18nActualLang!=undefined && tktsData.i18nActualLang=='es-co') domain="http://www.eventbee.co";
	else if(tktsData.i18nActualLang!=undefined && tktsData.i18nActualLang=='es-mx') domain="http://www.eventbee.mx";
	
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

		discsec='<tr><td valign="top" width="100%"><table width="100%" cellpadding="0" cellspacing="0">'
						  +'<tr ><td class="discount" cellpadding="0" cellspacing="0" class="yui-skin-sam" align="right" width="78%">'
						  +discountboxlabel+'&nbsp;'+discountbox+'&nbsp;'+applybutton
						  +'<span id="discountmsg"></span><span id="invaliddiscount"></span>'
						  +'</td><td align="right" id="registerBttn" class="yui-skin-sam"><div class="buyticketssubmit"><input type="hidden" id="actiontype" name="actiontype" value="" /><input type="button" name="submit"  id="orderbutton" value="'+orderbutton+'"  onClick="priorityTimeCheck();"  class="buyticketsbutton" onmouseover="javascript:this.className=\'buyticketsbuttonhover\';" onmouseout="javascript:this.className=\'buyticketsbutton\';" /></div></td></tr></table></td></tr><tr><td height="10px"></td></tr>';
	
		discsec+='<tr><td width="100%"><table width="100%" cellpadding="0" cellspacing="0"><tr><td align="left"><a href="'+domain+'" target="_blank"><img src="/main/images/home/poweredbyeventbee.jpg" alt="Powered by Eventbee Online Registration & Ticketing" title="Powered by Eventbee" border="0" style="width:78px !important;height:42px !important"> </a> </td><td align="right" width="100%"></td></tr>';
	}else{
	discsec+='<tr><td width="100%"><table width="100%" cellpadding="0" cellspacing="0"><tr><td align="left"><a href="'+domain+'" target="_blank"><img src="/main/images/home/poweredbyeventbee.jpg" alt="Powered by Eventbee Online Registration & Ticketing" title="Powered by Eventbee" border="0" style="width:78px !important;height:42px !important"> </a> </td><td align="right" width="100%" id="registerBttn" class="yui-skin-sam"><div class="buyticketssubmit"><input type="hidden" id="actiontype" name="actiontype" value="" /><input type="button" name="submit"  id="orderbutton" value="'+orderbutton+'"  onClick="priorityTimeCheck();"  class="buyticketsbutton" onmouseover="javascript:this.className=\'buyticketsbuttonhover\';" onmouseout="javascript:this.className=\'buyticketsbutton\';" /></div></td></tr>';
	}
	
	
	
	if(seating_enabled_tkt_wedget=="YES" ){
		discnbuy=tktsData.discnbuy;
		if(discnbuy=='up')
		{htmldata+=discsec;
		 discsec='';
		}			
		htmldata+='<tr><td height="10px"></td></tr><tr><td width="100%" id="seatingsection"> </td></tr><tr><td height="20px"></td></tr>';
	}
	
	htmldata+=discsec;
	
	htmldata+="</table></form>";
	//alert(seating_enabled_tkt_wedget+"--"+venueid);

	//$('registration').hide();
	document.getElementById("registration").innerHTML="";
	document.getElementById("registration").innerHTML=htmldata;
	 hideTicLoadingImage();
	if(document.getElementById("eventdate")){
		if(isPriority)
			document.getElementById('eventdate').disabled=true;
		else
			document.getElementById("eventdate").disabled=false;
	}
	ticketsArray=new Array();
	if(seating_enabled_tkt_wedget=="YES"){
		//getseatingsection();
		if(document.getElementById("eventdate"))
			document.getElementById("eventdate").disabled=true;
		$('orderbutton').disabled=true;
	}
	Initialize("registration");
	
	if(discountcode!=null&&discountcode!=''&&discountcode!='null')
		getDiscountAmounts();
		
	if($('eventstatus')){
		if( $('eventstatus').value=='CLOSED')
			$('orderbutton').style.display='none';
		}
	if(tktsData.isDiscountExists && tktsData.buybuttonbeside!=undefined && tktsData.buybuttonbeside=="Y"){
		jQuery('#registerBttn').insertAfter(jQuery('#discountsection'));
		jQuery('#discountsection').attr('width',"50%");
		jQuery('#registerBttn').attr('align',"left");
		
	}
}
 
 
 function waitList(tktId,waitListCount){
	var tktName='';
	 
	 if(document.getElementById('waittkt_'+tktId))
	 tktName=document.getElementById('waittkt_'+tktId).getAttribute('data-tktname');
	 
	 if(!$('attendeeloginpopup')){
			var div=document.createElement("div");
			div.setAttribute('id','attendeeloginpopup');
			div.className='ticketunavailablepopup_div';
			jQuery('body').append(div);
		}
	 document.getElementById('attendeeloginpopup').style.display='block';
	 if(document.getElementById("backgroundPopup"))
			document.getElementById("backgroundPopup").style.display='block';
		
			var pos=jQuery(document).scrollTop()+130;
			document.getElementById('attendeeloginpopup').style.top=pos+'px';	
			document.getElementById('attendeeloginpopup').style.display="block";
			document.getElementById('attendeeloginpopup').style.left='26%';	
			document.getElementById('attendeeloginpopup').style.width='450px';	
	 
			 new Ajax.Request('/waitlist/waitlistcontent.jsp?timestamp='+(new Date()).getTime(), {
					method: 'post',
					parameters:{eid:eventid,tktid:tktId,tktname:tktName,tktcount:waitListCount},
					onSuccess:waitListResponse
					});
 }
 
 
 function waitListResponse(response){
	 document.getElementById('attendeeloginpopup').innerHTML=response.responseText;
 }
 
 
 function waitClose(){
		document.getElementById('attendeeloginpopup').innerHTML='';
    	document.getElementById('attendeeloginpopup').style.display="none";
    	document.getElementById("backgroundPopup").style.display="none";
 }
 
 
 function waitSubmit(){
	 var nameflag=true,emailflag=true;
	 var tktId=document.getElementById('waittktid').value;
	 var name=document.getElementById('waitname').value;
	 name=name.replace(/^\s+|\s+$/gm,'');
	 document.getElementById('waitname').value=name;
	 if(name==''){
		 document.getElementById('waitnameerror').style.display='block';   
		 nameflag=false;
	 }else{
		 nameflag=true;
		 document.getElementById('waitnameerror').style.display='none';
	 }
	 var email=document.getElementById('waitemail').value;
	 email=email.replace(/^\s+|\s+$/gm,'');
	 document.getElementById('waitemail').value=email;
	 if(email==''){
		 document.getElementById('waitemailerror').style.display='block';
		 emailflag=false;
	 }else{
		 var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		 if(!re.test(email)){
		  document.getElementById('waitemailerror').innerHTML='Invalid email';
		  document.getElementById('waitemailerror').style.display='block';
		  emailflag=false;
	  }else{
		  document.getElementById('waitemailerror').style.display='none';
		  emailflag=true
	  }
	 }
	 
	 if(emailflag && nameflag){
	 var phone=document.getElementById('waitphone').value;
	 var notes=document.getElementById('waitnotes').value;
	 var userDetailsObj={name:name,email:email,phone:phone};
	 var tktDetails='[{';
		 tktDetails=tktDetails+'"ticket_id":"'+tktId+'",';
		 tktDetails=tktDetails+'"ticket_name":"'+document.getElementById('waittktname').value+'",';
	 	 tktDetails=tktDetails+'"qty":"'+document.getElementById('waitqty').value+'"';
		 tktDetails=tktDetails+'}]';

		 document.getElementById('waitsubmitdiv').style.display='none';
		 document.getElementById('waitloadingdiv').style.display='block';
		 new Ajax.Request('/waitlist/createwaitlist.jsp?timestamp='+(new Date()).getTime(), {
				method: 'post',
				parameters:{event_id:eventid,user_details:JSON.stringify(userDetailsObj),tickets_info:tktDetails,notes:notes,event_date:evtdate},
				onSuccess:waitListSaveResponse
				});
	 }
 }
 
 function waitListSaveResponse(result){
	 var response=eval('('+result.responseText+')');
	 document.getElementById('waitsubmitdiv').style.display='block';
	 document.getElementById('waitloadingdiv').style.display='none';
	 if(response.status=='success'){
		 document.getElementById('waitsuccess').style.display='block';
		 document.getElementById("submitbtn").disabled = true; 
		 getTicketsJson(eventid);
		 document.getElementById('notifymsg').innerHTML='&nbsp;'+props.wl_confirm_msg;
		 /*setTimeout(function(){
			 waitClose();
		 },1000);*/
	 }
 }
 
 
 
 
 
 

function submitTickets(){

	if($('discountcode'))
			discountcode=$('discountcode').value;
			
			//alert("submit"+code);
			
var fbuserid=0;
if(fblogindata["first_name"]!=undefined){
	fname=fblogindata["first_name"];
	lname=fblogindata["last_name"];
	if(fblogindata["email"]==undefined)
		fblogindata["email"]='';
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
parameters:{evtdate:evtdate,code:code,context:context,sectionid:sectionid,selected_ticket:jsonFormat,seating_enabled:seating_enable,ticketids:ticketids,eid:eventid,registrationsource:registrationsource,fbuserid:fbuserid,nts_enable:ntsdata["nts_enable"],nts_commission:ntsdata["nts_commission"],referral_ntscode:ntsdata["referral_ntscode"],fname:fname,lname:lname,email:email,wid:waitlistId,priregtoken:priRegToken,prilistid:priListId},
onComplete:submitTicketrepone
});
}


/* profile_common code copied */
function getTicketsAvailabilityMsg(){
	if(tktsData.availibilitymsg==undefined)
		return;
	var data=tktsData.availibilitymsg;
	for(i=0;i<ticketsArray.length;i++){
		var tktid=ticketsArray[i];
		if(data[tktid]!=undefined){
			var str=data[tktid];
			var capacity=data["capacity_"+tktid];
			var soldqty=data["soldqty_"+tktid];
			var holdqty=data["hold_"+tktid];
			var remainingqty=Number(data["remaining_"+tktid])-Number(holdqty);
			if(remainingqty<0)remainingqty=0;
			str = str.replace(/\$capacity/g,capacity);
			str=str.replace(/\$soldOutQty/g,soldqty);
			str=str.replace(/\$remainingQty/g,remainingqty);
			str=str.replace(/\$onHoldQty/g,holdqty);
			str="<pre  style='font-family: verdana; font-size:10px; line-height:100%; padding:0px; margin:0px; white-space: pre-wrap; white-space: -moz-pre-wrap !important; white-space: -pre-wrap; white-space: -o-pre-wrap; word-wrap: break-word;color:#666666;'>"+str+"</pre>";
			//str="<span class='small' >"+str+"</span>";
			if($(tktid)){
				var availmsg="availmsg_"+tktid;
				var cellid="divmsg_"+tktid;
				var cell=$(cellid);
				var div=document.createElement("div");	
				div.setAttribute("id",availmsg);
				div.setAttribute("class","small");
				div.setAttribute("width","55%");
				cell.appendChild(div);
				
				if($(availmsg))
					$(availmsg).innerHTML=str;
			}
		}	
	}

}
/* profile_common code copied end*/

/* tickets_common.js code start*/

function log(message){
	try{
	if(window.console)
		console.log(message);
	}catch(e){}
}
function ticketJsonResponse(response){
 var jsonTicketData=response.responseText;
  tktsData=eval('(' + jsonTicketData + ')');
  document.getElementById('registration').style.display='block';
  if(tktsData.status!=undefined && tktsData.status=='blockedbytraffic'){
	  hideTicLoadingImage();
	  document.getElementById('registration').innerHTML='<center>'+tktsData.msg+'</center>';
	  if($('pageheader')){
			document.getElementById('pageheader').style.display='block';
			document.getElementById('pageheader').innerHTML='Tickets';
		}
	  setBlockedInterval(tktsData.remaintime);
	  return ;
  }
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
		document.getElementById('registration').innerHTML='<center><b>'+props.tkts_not_available+'</b></center>';
		hideTicLoadingImage();
		if($('eventdate')){
			if(isPriority)
				document.getElementById('eventdate').disabled=true;
			else
				document.getElementById('eventdate').disabled=false;
		}
		if($('ticketrecurring'))
			document.getElementById('ticketrecurring').innerHTML='';
	}
	else{
		
	getHeader(eventid);
	}
   
   $$('.leftboxcontent')[1].select('img').each(function(element) {
	    element.width=element.width>620?620:element.width;
	});
   
 
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
 var  donatObject=new Object();
 
 function priorityTimeCheck(){
	if(priRegToken!='' && priListId!='' && priLimitType!='UNLIMIT'){
		new Ajax.Request('/embedded_reg/PriorityTimeCheck.jsp?timestamp='+(new Date()).getTime(), {
			  method: 'get',
			  parameters:{eid:eventid,listId:priListId,priToken:priRegToken},
			  onComplete:function(response){
				  var priExpiryResponse=eval('(' + response.responseText + ')');
				  if(priExpiryResponse.expired){
					  priorityTimeoutPopup();
				  }else{
					  validateTickets();
				  }
			  },
			  onFailure: failureJsonResponse
		});
	}else{
		validateTickets();
	}
 }
 
 function validateTickets(){
	ticketpageclickcount++;
	if(ticketpageclickcount>1){
		return;
		}	
		
		for(key in donatObject)
		{ if(!donatObject[key])
		  { alert("Enter valid amount for \""+tktsData[key].Name+"\"");
		    ticketpageclickcount=0;
		    return;
		  }
		}
		
		
	
	if($('registration'))
		$('registration').hide();
	showTicLoadingImage(props.loading);
	if(window.custom_conditional_rules && window.matchCustomConditionalRules){//checking conditional complex rule for particular manager. 
		var isRulesMatched=matchCustomConditionalRules();
		if(!isRulesMatched){
			hideTicLoadingImage();
			if($('registration')) $('registration').show();
			ticketpageclickcount=0;
			return;
		}
	}
	
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
							alert(props.sea_for_lbl+' "'+ticketnameids[ticketd]+'" '+props.sea_min_qty_msg+' '+min_ticketid[ticketd]+', '+props.sea_sel_only_msg+' '+qty+' '+props.sea_seat_msg);
							min_sel_qty=false;
							ticketpageclickcount=0;
							break;
						}
						else if(Number(qty)>max_ticketid[ticketd]){
							totalqty=totalqty+Number(qty)
							//totalqty=0;
							allowsel=false;
							alert(props.sea_for_lbl+' "'+ticketnameids[ticketd]+'" '+ props.sea_max_qty_msg+' '+max_ticketid[ticketd]+', '+props.sea_selected_msg+' '+qty+' '+props.sea_seats_msg);
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
mins_left=responsejsondata.timeout;
//console.log("timeout::"+mins_left);
var status=responsejsondata.status;
ntscode=responsejsondata.ntscode;
display_ntscode=responsejsondata["display_ntscode"];
if(status=='success'){
document.getElementById('tid').value=responsejsondata.tid;
tranid==responsejsondata.tid;
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
new Ajax.Request('/embedded_reg/getdiscounts.jsp?timestamp='+(new Date()).getTime(), {
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
		   ele.innerHTML = ele.innerHTML + ' <img style="cursor:pointer; width:13px !important" id="imgShowHidegrp_'+eleid+'" src="/home/images/expand.gif" onClick=showTcktGrpDescr("'+eleid+'");>';
	   }
}


function showTcktGrpDescr(eleid){
		if(document.getElementById("desc_"+eleid)){
	   var a=document.getElementById("desc_"+eleid).style.display;
	   if(a=="none"){
			   document.getElementById("imgShowHidegrp_"+eleid).src="/home/images/collapse.gif";
			   document.getElementById("desc_"+eleid).style.display="block";
	   }else{
			   document.getElementById("imgShowHidegrp_"+eleid).src="/home/images/expand.gif";
			   document.getElementById("desc_"+eleid).style.display="none";
	   }
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

windowOpener('/portal/embedded_reg/memberlogingblock.jsp?eid='+eid,'memberLogin','WIDTH=400,HEIGHT=300,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');


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
    elOptNew.text = props.rec_select_date_lbl;
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
  if(elOptOld.value!='Select Date'){
     
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
log("newTrnId: "+newTrnId);
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
	/*if($('registration')){ 
		var x=jQuery('#registration').position();
		window.scrollTo("0",""+Number(x)+"");
	}
	else{
		window.scrollTo("0","0");
	}*/
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='block';
	}
		var pos=jQuery(document).scrollTop()+130;
		document.getElementById('attendeeloginpopup').style.top=pos+'px';	
		document.getElementById('attendeeloginpopup').style.display="block";
		//document.getElementById('attendeeloginpopup').style.top='50%';
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
	if(isPriority){ 
		if(document.getElementById('prioerror'))
			document.getElementById('prioerror').innerHTML='';
		return;
	}
if(document.getElementById('tid')){
var checktid=document.getElementById('tid').value;

if(checktid!=undefined && checktid!=null && checktid!="")
{     timeout_delete_locked_temp('normal');
	if($('seatingsection'))
	{
	 	 new Ajax.Request('/embedded_reg/seating/delete_tempseats.jsp?timestamp='+(new Date()).getTime(), {
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

var stopInterval;
function setBlockedInterval(timeremain){
	if(timeremain==undefined)timeremain='30';
		var time=Number(timeremain);
	stopInterval=setInterval(
			function(){
				if(time==0){
					document.getElementById('registration').style.display='none';
					clearInterval(stopInterval);
					getTicketsJson(eventid);
				}
				time--;
				}
		,1000);
}

/* tickets_common.js code end*/
