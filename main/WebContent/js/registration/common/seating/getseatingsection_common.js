function seatingticketresponse(){
notavailableticketids=[];
//var cur_ids=tktsData.seatticketid+"";
ticket_ids_seats=tktsData.seatticketid;
var sel_tic_id=tktsData.seatticketid;
for(i=0;i<tktsData.seatticketid.length;i++){
	var cur_id=tktsData.seatticketid[i];
	var dropdown_id='qty_'+cur_id;
	var dropdown_id1='show_'+cur_id;
	if($(dropdown_id) && $(dropdown_id).disabled==true){
		notavailableticketids[notavailableticketids.length]=cur_id;
		memberseatticketids[memberseatticketids.length]=cur_id;
		var tic_dropdown=document.getElementById(dropdown_id);
		var drop_down_length=tic_dropdown.length;
		min_ticketid[cur_id]=tic_dropdown[1].value;
		max_ticketid[cur_id]=tic_dropdown[Number(drop_down_length)-1].value;
		
		if(document.getElementById('waitlistId') && (cur_id==tktsData.waitList.tktid)){
			min_ticketid[cur_id]=tktsData.waitList.tktqty;
			max_ticketid[cur_id]=tktsData.waitList.tktqty;
			var td_id="td_ticketid_"+cur_id;
			$(td_id).innerHTML="";
			$(td_id).innerHTML="<center title='Select Seats below'><span id='"+dropdown_id1+"' style='font-size:14px;margin-left:40px' >0</span></center><input value='0' type='hidden' name='"+dropdown_id+"' id='"+dropdown_id+"'><span onClick='scrollToSeats();' style='cursor:pointer;color:blue;'>"+props.sea_choose_seats_lbl+"</span><br> Reserverd Quantity : "+tktsData.waitList.tktqty;
		    var index = notavailableticketids.indexOf(cur_id);
		    if (index > -1) 
			notavailableticketids.splice(index, 1);
		}
			
	}
	else if($(dropdown_id) && $(dropdown_id).type!='hidden'){
		var td_id="td_ticketid_"+cur_id;
		var tic_dropdown=document.getElementById(dropdown_id);
		var drop_down_length=tic_dropdown.length;
		min_ticketid[cur_id]=tic_dropdown[1].value;
		max_ticketid[cur_id]=tic_dropdown[Number(drop_down_length)-1].value;
		$(td_id).innerHTML="";
		$(td_id).innerHTML="<center title='Select Seats below'><span id='"+dropdown_id1+"' style='font-size:14px;margin-left:40px' ></span></center><input value='0' type='hidden' name='"+dropdown_id+"' id='"+dropdown_id+"'><span onClick='scrollToSeats();' style='cursor:pointer;color:blue;'>"+props.sea_choose_seats_lbl+"</span>";
		
	}
	else{
		notavailableticketids[notavailableticketids.length]=cur_id;
	}
	
}
}

function getseatingsection(){
	$('seatingsection').innerHTML="<center><span id='seating_image'>"+props.sea_load_seats_msg+"<br><img src='/home/images/ajax-loader.gif'></span><center>";
     new Ajax.Request('/embedded_reg/seating/seatingsection.jsp?timestamp='+(new Date()).getTime(), {
	 method: 'get',
	 parameters:{eid:eventid,evtdate:evtdate,tid:tranid,venueid:venueid},
	 onSuccess: seatingsectionresponse,
	 onFailure:  failureJsonResponse
	 });
}


function generate_Sectiondropdown(allsectionid,allsectionname){
var select="<select name='section' id='section' onchange='getsection()' >"
for(i=0;i<allsectionid.length;i++){
select=select+"<option value="+allsectionid[i]+">"+allsectionname[i]+"</option>";
}
select=select+"</select>";
return select;
}

function checkticketsstatus(){
var allticketids="";

var jsonFormat="{";
	for(i=0;i<ticketsArray.length;i++){
		if(allticketids!="")
			allticketids=allticketids+"_"+ticketsArray[i];
		else
			allticketids=ticketsArray[i];
			
		var curid="qty_"+ticketsArray[i];
		var qty=document.getElementById(curid).value;
		if(document.getElementById('ticketType_'+ticketsArray[i]))
		  if(document.getElementById('ticketType_'+ticketsArray[i]).value=='donationType')
		     qty=0;
		if(Number(qty)>0){
			jsonFormat=jsonFormat+'"'+ticketsArray[i]+'":'+qty;
			if(i!=ticketsArray.length-1){
				jsonFormat=jsonFormat+",";
			}
		}
	}
	jsonFormat=jsonFormat+"}";
	new Ajax.Request('/embedded_reg/checkticketsstatus.jsp?timestamp='+(new Date()).getTime(),{
	method: 'get',
	parameters:{eid:eventid,tid:tranid,eventdate:evtdate,selected_tickets:jsonFormat,all_ticket_ids:allticketids,wid:waitlistId},
	onSuccess: ticketsavailable,
	onFailure: seatunavailable
	});

}

function ticketsavailable(response){
	var ticdata=response.responseText;
	var availablejson=eval('(' + response.responseText + ')');
	
	if(availablejson.responsemap.status=="success"){
		if(seating_enabled_tkt_wedget=='YES')
			checkseatavailibility();
		else{
			//submitTickets();
			getAttendeeLoginPopup();
			}
	}
	else{
		var jobj=availablejson.responsemap;
	    if(jobj.reason=="conditional_ticketing")
			conditionalTicketingRulesErrors(jobj.errors);
		else 
		ticketsunavailable(jobj);
	}
}

function conditionalTicketingRulesErrors(errors){
	var message="<ul>";
		for(i=0;i<errors.length;i++)	
			message=message+"<li>"+errors[i]+"</li>";		
	
		message=message+"</ul>";
		if($('ticketunavailablepopup_div')){}else{
		var divpopup=document.createElement("div");
		divpopup.setAttribute('id','ticketunavailablepopup_div');
		divpopup.className='ticketunavailablepopup_div';
		var cell=$('container');
		cell.appendChild(divpopup);
		}
		getConditionalRulesPopup(message);
	if($('registration')){
		$('registration').show();
	}
	if($('imageLoad'))
	$('imageLoad').hide();
	window.scrollTO(0,0);
}

function getConditionalRulesPopup(message){
	message=message+"<br><input type='button' value='OK' onclick='closeunavailablepopup()'><a href=# onclick=closeunavailablepopup()><img src='/home/images/images/close.png' class='divimage1'></a>";
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='block';
	}
	document.getElementById('ticketunavailablepopup_div').innerHTML=message;
	document.getElementById('ticketunavailablepopup_div').style.display="block";
	document.getElementById('ticketunavailablepopup_div').style.top='50%';
	document.getElementById('ticketunavailablepopup_div').style.left='26%';
	 
}





function ticketsunavailable(jobj){
var message="<ul>";
for(i=0;i<ticketsArray.length;i++){
	var tktid=ticketsArray[i];
	if(jobj["sel_"+tktid]!=undefined){
		var tktobj=	tktsData[tktid];
		var avail_qty=Number(jobj['remaining_'+tktid]);
		if(Number(avail_qty)<0)
			avail_qty=0;
		message=message+"<li>"+props.sea_for_lbl2+" \""+tktobj['Name']+"\" "+props.sea_sel_qty_is_lbl+" "+jobj['sel_'+tktid]+" "+props.sea_cur_avail_qty_is_lbl+" "+avail_qty+"</li>";
	
		if(jobj["hold_"+tktid]!=undefined && tktsData.availibilitymsg!=undefined){			
			tktsData.availibilitymsg['hold_'+tktid]=Number(jobj["hold_"+tktid]);
           tktsData.availibilitymsg['remaining_'+tktid]=avail_qty+tktsData.availibilitymsg['hold_'+tktid];
		}
			
	}
}
if(jobj["sel_"+eventid]!=undefined){
		var avail_qty=Number(jobj['remaining_'+eventid]);
		if(Number(avail_qty)<0)
			avail_qty=0;
				message=message+"<li>"+props.sea_for_lbl2+" \""+jobj['Name']+"\" "+props.sea_sel_qty_is_lbl+" "+jobj['sel_'+eventid]+" "+props.sea_cur_avail_qty_is_lbl+" "+avail_qty+"</li>";
	}
    getTicketsAvailabilityMsg();
	message=message+"</ul>";
	if($('ticketunavailablepopup_div')){}else{
	var divpopup=document.createElement("div");
	divpopup.setAttribute('id','ticketunavailablepopup_div');
	divpopup.className='ticketunavailablepopup_div';
	var cell=$('container');
	cell.appendChild(divpopup);
	}
	getunavailablepopup(message);
if($('registration')){
	$('registration').show();
}
if($('imageLoad'))
$('imageLoad').hide();
window.scrollTO(0,0);
}

function getunavailablepopup(message){
	message=message+"<br><input type='button' value='OK' onclick='closeunavailablepopup()'><a href=# onclick=closeunavailablepopup()><img src='/home/images/images/close.png' class='divimage1'></a>";
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='block';
	}
	document.getElementById('ticketunavailablepopup_div').innerHTML=message;
	document.getElementById('ticketunavailablepopup_div').style.display="block";
	document.getElementById('ticketunavailablepopup_div').style.top='50%';
	document.getElementById('ticketunavailablepopup_div').style.left='26%';
	
	clearallselections();
	 
}

function closeunavailablepopup(){
if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='none';
	}
	document.getElementById('ticketunavailablepopup_div').style.display="none";
	ticketpageclickcount=0;
	profilepageclickcount=0;
	getTicketsPage();
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
	new Ajax.Request('/embedded_reg/seating/checkseatstatus.jsp?timestamp='+(new Date()).getTime(),{
	method: 'get',
	parameters:{eid:eventid,tid:tranid,eventdate:evtdate,selected_seats:jsonFormat,all_section_ids:sec_ids},
	onSuccess: seatavailable,
	onFailure: seatunavailable
	});
		
}

function seatavailable(response){
	
	var waitId='';
	if(tktsData.waitList!=undefined){
		if(tktsData.waitList.tktid!=undefined)
			waitId=tktsData.waitList.tktid;
	}
var data=response.responseText;
var availablejson=eval('('+data+')');
if(availablejson.status == "success"){
	isavailableseat="yes";
	//submitTickets();
	getAttendeeLoginPopup();
}
else{
	isavailableseat="no";
	for(i=0;i<ticketsArray.length;i++){
		var ticketd=ticketsArray[i];
		if(ticketd!=waitId){
		if(document.getElementById("qty_"+ticketd).type=='hidden'){
			document.getElementById("qty_"+ticketd).value=0;
			/*if(document.getElementById("show_"+ticketd))
			document.getElementById("show_"+ticketd).innerHTML=0;*/
			if(document.getElementById("waitlist_"+ticketd))
				document.getElementById("waitlist_"+ticketd).value=0;
		}
		}
	}

	alert(props.sea_few_seats_hold_or_sold_msg);
	if($('registration')){
		$('registration').show();
	}
	if($('eventdate')){
		$('eventdate').disabled=false;
	}
	if($('seatingsection')){
	hideTicLoadingImage();
		$('seatingsection').innerHTML='';
		getseatingsection();
	}
		ticketpageclickcount=0;

}
}

function seatunavailable(){
alert(props.sea_error_try_back_msg);
}

function resetobjectdata(){
var a=[];
sel_seatcodes=[];

var waitId='';
if(tktsData.waitList!=undefined){
	if(tktsData.waitList.tktid!=undefined)
		waitId=tktsData.waitList.tktid;
}


for(i=0;i<ticketsArray.length;i++){
		var ticketd=ticketsArray[i];
		
		if(tktsData[ticketd].DonateType)
		  { if(tktsData[ticketd].DonateType=="Y"){}}
		    
		
		else{
			if(ticketd!=waitId){
			if(document.getElementById("qty_"+ticketd)){
				document.getElementById("qty_"+ticketd).value=0;
				/*if(document.getElementById("show_"+ticketd))
					document.getElementById("show_"+ticketd).innerHTML=0;*/
				if(document.getElementById("waitlist_"+ticketd))
					document.getElementById("waitlist_"+ticketd).value=0;
			  }
			}
		}
	}




   sel_seatcodes=new Array();
   sel_ticket=new Object();
   ticket_count=new Object();
	
for(i=0;i<=allsectionid.length;i++){
	if(section_sel_seats[allsectionid[i]]!=undefined){
		section_sel_seats[allsectionid[i]]=a;
	}
}
for(i=0;i<ticketsArray.length;i++){
	var drop_down_id="qty_"+ticketsArray[i];
		if(sel_ticket[ticketsArray[i]]!=undefined){
             sel_ticket[ticketsArray[i]]=a;
      	}
	if(ticket_count[drop_down_id]!=undefined){
		ticket_count[drop_down_id]=a;
	}
}
	
}

function closepopuplayout(){
	/*if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='none';
	}*/
	if($('layoutpopup')){
		$('layoutpopup').style.display='none';
	}
}

function getticketseatsdisplay(){
for(i=0;i<tktsData.seatticketid.length;i++){
	var tktID=tktsData.seatticketid[i];
	
	if($('qty_'+tktID)){
	if(ticketseatcolor[tktID]!=undefined){
		var seatingimg='seatingimg_'+tktID;
		var cell=document.getElementById(tktID);
		var div=document.createElement("div");
		div.setAttribute('id',seatingimg);
		div.className='widgetcontainer';
		cell.appendChild(div);
		
		var allimgs="";
		var groupid=$('ticketGroup_'+tktID).value;
		if(ticket_groupnames[groupid]!=undefined){
			allimgs="<table><tr><td style='width: 26px;' rowspan='3'></td><td>";
		}
		var ticketcolor=ticketseatcolor[tktID];
		for(j=0;j<ticketcolor.length;j++){
			allimgs=allimgs+"<img src='/main/images/seatingimages/"+ticketcolor[j]+".png' style='padding:5px; border:0px;'> ";
				
		}
		if(ticket_groupnames[groupid]!=undefined){
			allimgs=allimgs+"</td></tr></table>";
		}
		
		if(document.getElementById(seatingimg))
		document.getElementById(seatingimg).innerHTML=allimgs;
	}

	}
	}
	
}

function getavailableticketid(ticketid){
for (var i = 0; i<notavailableticketids.length; i++) {
		var arrlen = ticketid.length;
		for (var j = 0; j<arrlen; j++) {
			if (notavailableticketids[i] == ticketid[j]) {
				ticketid = ticketid.slice(0, j).concat(ticketid.slice(j+1, arrlen));
			}
		}
	}
return ticketid;
}

function addselectiontotooltip(seatid,ticketid){
var tooltip=seatinfo[seatid]+"";
var newtip=tooltip.split("<u>");
tooltip=newtip[0]+"<u><b>"+props.sea_cur_sel_tooltip+":</b></u><br><ul><li>"+ticketnameids[ticketid]+"</li></ul><br><u>"+newtip[1];
seatinfo[seatid]=tooltip;

}


function restoreoldtooltip(seatid){
var tooltip=seatinfo[seatid]+"";
var newtip=tooltip.split("<u>");
tooltip=newtip[0]+"<u>"+newtip[2];
seatinfo[seatid]=tooltip;
}


function clearallselections(){


    if($('seatingsection')){
		getseatingsection();
	}
	else
	{
	resetobjectdata();
	}
}

function scrollToSeats(){
	try{
	jQuery('html, body').animate({
	        scrollTop: jQuery('#seatingsection').offset().top
	    }, 700);
	}catch(err){
		
	}
}

