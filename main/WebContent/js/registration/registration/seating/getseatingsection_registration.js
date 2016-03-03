
function seatingsectionresponse(response){
var data=response.responseText;
var responsedata=eval('(' + data + ')');
seatingsectionresponsedata=responsedata;
if(seatingsectionresponsedata['seatticketgroups']!=undefined)
	setSeatGroupsData(seatingsectionresponsedata['seatticketgroups']);
allsectionid=responsedata.allsectionid;
resetobjectdata();
allsectionname=responsedata.allsectionname;
ticketseatcolor=responsedata.ticketseatcolor;
ticket_groupnames=responsedata.ticketgroups;
var layoutdisplay=responsedata.venuelayout;
var venuelayoutlink="";
if(layoutdisplay=="URL"){
	venuepath="\""+responsedata.venuepath+"\"";
	venuelayoutlink="<a id='venuelayoutlinkclick'  style='float:right;cursor:pointer;'>"+responsedata.venuelinklabel+"</a>";
}
if(allsectionid.length==1){
	sectionid=allsectionid[0];
	$('seatingsection').innerHTML=="";
	$('seatingsection').innerHTML="<html><head>"+
	"</head><body><input type='hidden' name='section' id='section' value='"+sectionid+"'><span id='seating_image'>"+props.sea_load_seats_msg+"<br><img src='/home/images/ajax-loader.gif'></span>"+venuelayoutlink+"<div id='sectionexpand'></div><div id='seatcell' style='overflow: auto; width: 600px; height: 300px; border: 0px solid rgb(51, 102, 153); padding: 10px; background:#fff;'>"+
	"</div></body></html>";
	}
else{
	sectionid=allsectionid[0];
	$('seatingsection').innerHTML=="";
	var sectionlistdropdown=generate_Sectiondropdown(allsectionid,allsectionname);
	$('seatingsection').innerHTML="<html><head>"+
	"</head><body><br><div style='float:left;'>"+props.sea_sel_section_lbl+": "+sectionlistdropdown+"</div> &nbsp;&nbsp;"+venuelayoutlink+"<p id='seating_image' align='center'>Loading Seats...<br><img src='/home/images/ajax-loader.gif'></p><div id='sectionexpand' align='left' style='padding:10px 0px 0px 0px; _padding:10px 0px 0px 0px;'></div><div id='seatcell' style='overflow: auto; width: 600px; height: 300px; border: 0px solid rgb(51, 102, 153); padding: 10px;background:#fff;'>"+
	"</div></body></html>";
	//padding-left:0px;padding-top:10px;$('seatingsection').innerHTML="<table width='100%'><td align='left'><div style='float:left;'>Select Section: "+sectionlistdropdown+"</div></td><td align='right'>"+venuelayoutlink+"</td></table><span id='seating_image'>Loading Seats...<br><img src='/home/images/ajax-loader.gif'></span><br><div id='sectionexpand'></div><div id='seatcell' style='overflow: auto; width: 600px; height: 300px; border: 0px solid rgb(51, 102, 153); padding: 10px;background:#fff;'></div>";
}
if($('venuelayoutlinkclick')){
	jQuery("#venuelayoutlinkclick").click(function(){
		getvenuelayout(venuepath);
	});
}	
getticketseatsdisplay();
generateSeating(responsedata.allsections[sectionid]);
$('seating_image').hide();
if(document.getElementById('eventdate'))
 document.getElementById('eventdate').disabled=false;
 getBuyButtonStatus();
 /*
   jQuery("#seatcell").mouseenter(function() {
	//alert("mouse entered");
	document.getElementById("seatcell").style.height='100%';
	document.getElementById("seatcell").style.width='100%';
	document.getElementById("seatcell").style.position='relative';
  }).mouseleave(function() {
	//alert("mouse left");
	//width: 600px; height: 300px;
	document.getElementById("seatcell").style.height='300px';
	document.getElementById("seatcell").style.width='600px';
	document.getElementById("seatcell").style.position='';
  });
*/
}


function getvenuelayout(url){
if($('layoutpopup')){
}
else{
var cell=$('seatcell');
	var div=document.createElement("div");
	div.setAttribute('id','layoutpopup');
	div.className='layoutwidget';
	cell.appendChild(div);
	document.getElementById('layoutpopup').style.display='none';
	jQuery( "#layoutpopup" ).draggable();
}

var layout="<a href='javascript:closepopuplayout();'><img src='/home/images/close.png' class='imgclose'></a><iframe width='100%' height='100%' src="+url+" resizeIFrame=true frameborder='0' allowfullscreen></iframe>";
document.getElementById('layoutpopup').innerHTML=layout;
document.getElementById('layoutpopup').style.display='block';
document.getElementById('layoutpopup').style.top='35%';
	document.getElementById('layoutpopup').style.left='15%';
	//if(document.getElementById("backgroundPopup"))
	//	document.getElementById("backgroundPopup").style.display='block';
	window.scrollTo("0","150");
	jQuery( "#layoutpopup" ).resizable();
}


function setSeatGroupsData(groupSeatObj){
	if(Object.keys(groupSeatObj).length>0){
		seattickets=groupSeatObj['seattickets'];
		seatingeid=groupSeatObj['seatingeid'];
		for(var eachArray in groupSeatObj['seatgrouptable']){
			seattables.push(eachArray);
			window[eachArray]=groupSeatObj['seatgrouptable'][eachArray];
			GroupSeats.push.apply(GroupSeats, window[eachArray]);
		}
		}
	//alert(seattickets.length);
}


