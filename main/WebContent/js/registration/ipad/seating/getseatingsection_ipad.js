
function seatingsectionresponse(response){

var data=response.responseText;
var responsedata=eval('(' + data + ')');
seatingsectionresponsedata=responsedata;
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
	"</head><body><input type='hidden' name='section' id='section' value='"+sectionid+"'><span id='seating_image'>Loading Seats...<br><img src='/home/images/ajax-loader.gif'></span>"+venuelayoutlink+"<div id='sectionexpand' style='float:left;'></div><div id='seatcell' style='overflow: auto; width: auto; height: auto; border: 0px solid rgb(51, 102, 153); padding: 0px;'>"+
	"</div></body></html>";
	}
else{
	sectionid=allsectionid[0];
	$('seatingsection').innerHTML=="";
	var sectionlistdropdown=generate_Sectiondropdown(allsectionid,allsectionname);
	$('seatingsection').innerHTML="<html><head>"+
	"</head><body><div style='float:left;'>Select Section: "+sectionlistdropdown+"</div> &nbsp;&nbsp;"+venuelayoutlink+"<br><span id='seating_image'>Loading Seats...<br><img src='/home/images/ajax-loader.gif'></span><br><center><div id='seatcell' style='overflow: auto; width: auto; height: auto; border: 0px solid rgb(51, 102, 153); padding: 10px;'>"+
	"</div></center></body></html>";
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
}


function getvenuelayout(url){
var layout="<ul><li><a href='javascript:closepopuplayout();'><img src='/home/images/close.png' class='imgcancel'></a><iframe width='100%' height='100%' src='"+url+"' resizeIFrame=true frameborder='0' allowfullscreen></iframe><a style='margin:0 10px;color:rgba(0,0,0,.9)' onClick='javascript:closepopuplayout();'  class='whiteButton '>Close</a></li></ul>";

document.getElementById('layoutpopup').innerHTML=layout;
document.getElementById('layoutpopup').style.display='block';
	window.scrollTo("0","0");

	jQuery( "#layoutpopup" ).resizable();
}

