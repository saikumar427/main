
function getseatingtimer(){
	if(!$('ticket_timer')){
	
	var divpopup=document.createElement("div");
	divpopup.setAttribute('id','ticketpoup_div');
	divpopup.className='ticketpoup_div current';
	var cell=$('container');
	//var cell=$('profile');
	var div=document.createElement("div");
	div.setAttribute('id','ticket_timer');
	div.className='ticket_timer layoutfloat';
	//div.className='initial_timer';
	div.setAttribute('style','display:block;');
	cell.appendChild(div);
	cell.appendChild(divpopup);
	}
	//$('ticket_timer').style.position="absolute";
	document.getElementById('ticket_timer').style.display="block";
	$('ticket_timer').innerHTML='Time left to buy<br>';
	//window.scrollTo("0","25");
	var divcell=$('ticket_timer');
	var span=document.createElement("div");
	span.setAttribute('id','time_left_tobuy');
	span.setAttribute('class','spannormal');
	divcell.appendChild(span);
	mins_left = 14;
	//mins_left=0;
    s_left = 60;
	//s_left = 10;
	mins_remain=14;
	secs_remain=60;
	/*
	jQuery('#ticket_timer').animate({
	 jQuery("#ticket_timer").animate({"left": "+=50px"}, "slow");
	 
	 });

	jQuery('#ticket_timer').animate({"left": "+=600px"}, 2000, function() {
		jQuery('#ticket_timer').animate({"top": "+=450px"}, 2000, function() {
	  		//var div=$('ticket_timer');
		   // div.className='ticket_timer';
	    });
	});
	*/
	fifteenMinutes();
}

   
 function getconfirmationpopup(){
	var divpopupcontent="<br><ul><li>Sorry, timed out!</li></ul><center><input type='button' value='Try Again' onclick='seatingtryagain()'><center><a href=# onclick=seatingcancel()><img src='/home/images/close.png' class='divimage'></a>";
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
	closeebeecreditspopup();
	displaydivpopuptimeup();
	}
	if(document.getElementById("backgroundPopup")){
		document.getElementById("backgroundPopup").style.display='block';
	}
	
	//jQuery('#ticketpoup_div').bgiframe();
	document.getElementById('ticketpoup_div').innerHTML=divpopupcontent;
	document.getElementById('ticketpoup_div').style.width="80%";
	document.getElementById('ticketpoup_div').style.display="block";
	document.getElementById('ticketpoup_div').style.top='25%';
	document.getElementById('ticketpoup_div').style.left='10%';
	window.scrollTo("0","0");
}

