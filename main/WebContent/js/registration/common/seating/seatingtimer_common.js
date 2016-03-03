function showmemberseats(){

for(i=0;i<memberseatticketids.length;i++){

	var cur_id=memberseatticketids[i];
	var dropdown_id='qty_'+cur_id;
	var dropdown_id1='show_'+cur_id;
	var td_id="td_ticketid_"+cur_id;
	$(td_id).innerHTML="";
	$(td_id).innerHTML="<center title='Select Seats below'><span id='"+dropdown_id1+"' style='font-size:14px;margin-left:40px' >0</span></center><input value='0' type='hidden' name='"+dropdown_id+"' id='"+dropdown_id+"'>Select seats";
	
}
enablememberseats();
}


function enablememberseats(){
	for (var i = 0; i<memberseatticketids.length; i++) {
		var arrlen = notavailableticketids.length;
		for (var j = 0; j<arrlen; j++) {
			if (memberseatticketids[i] == notavailableticketids[j]) {
				notavailableticketids = notavailableticketids.slice(0, j).concat(notavailableticketids.slice(j+1, arrlen));
			}
		}
	}
	getsection();

}

function getsection(){
var sec_id=document.getElementById("section").value;
sectionid=document.getElementById("section").value;
generateSeating(seatingsectionresponsedata.allsections[sec_id]);
getsectionlevelselectedseats();
}


function getsectionlevelselectedseats(){
	var temp=new Array();
	temp=section_sel_seats[sectionid];
	if(temp!=undefined){
		for(i=0;i<temp.length;i++){
			//jQuery("#"+temp[i]+" img").attr("style","border:1px solid black;");
			jQuery("#"+temp[i]+" img").attr("style","border:1px solid red;");
			sel_select[temp[i]]=true;
			
			
		}
	}
	
}


function fifteenMinutes(){

   s_left--;
if(s_left<0) {
   s_left="59";
   mins_left--;
}

if(mins_left==-1) {
	clearTimeout(reg_timeout);
	getconfirmationpopup();
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
	
	
  reg_timeout=setTimeout('fifteenMinutes()',1000);
}
   }
  
  
function seatingtryagain(){
	$('ticketpoup_div').hide();
	document.getElementById('ticketpoup_div').innerHTML='';
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='none';
	}
	del_seat_temp();
	getTicketsPage();
} 

function del_seat_temp(){
	
	new Ajax.Request('/embedded_reg/seating/delete_tempseats.jsp?timestamp='+(new Date()).getTime(), {
	method: 'post',
	parameters:{eid:eventid,tid:tranid}
	});
}

function seatingcancel(){
	del_seat_temp_cancel();
	//var t=setTimeout('window.location.reload()',2000);
} 


function delete_locked_temp(tid){
	new Ajax.Request('/embedded_reg/seating/delete_temp_locked_tickets.jsp?timestamp='+(new Date()).getTime(), {
	method: 'post',
	parameters:{eid:eventid,tid:tid},
	onComplete:hidetimer
	});
}
function hidetimer(){
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
}


function del_seat_temp_cancel(){
	
	new Ajax.Request('/embedded_reg/seating/delete_tempseats.jsp?timestamp='+(new Date()).getTime(), {
	method: 'post',
	parameters:{eid:eventid,tid:tranid},
	onComplete:window.location.reload()
	});
}

function hidetimerpopup(){
	if($('ticketpoup_div')){
		var div=$('ticketpoup_div');
		div.className='ticketpoup_div1';
	}
}

function displaydivpopuptimeup(){
	if($('ticketpoup_div')){
		if(mins_left==-1){
			var div=$('ticketpoup_div');
			div.className='ticketpoup_div';
			if(document.getElementById("backgroundPopup")){
				document.getElementById("backgroundPopup").style.display='block';
			}
		}
	}
}
