function PrcocesConfirmationResponse(response){
clickcount=0;

//if(document.getElementById('imageLoad'))
//document.getElementById('imageLoad').style.display='none';
document.getElementById('registration').style.display='block';
document.getElementById('profile').style.display='none';
$('registration').className='current slide in ';
$('profile').className='';
if(document.getElementById('paymentsection')){
document.getElementById('paymentsection').style.display='none';
$('paymentsection').className='';
}
if(document.getElementById('pageheader')){
document.getElementById('pageheader').style.display='block';
document.getElementById('pageheader').innerHTML='<a name="ticketing"></a>'+headers.confirmationpage
}
backtoboxoffice="";
var backtoeventlabel="Back To Event Page";
backtoboxoffice='<a href="#" id="reflink" onclick="refreshPage()" class="button backtoboxoffice">'+backtoeventlabel+'</a>';
if($('username').value!=""){
	backtoboxoffice+="<a class='button backtoboxoffice' id='confbacktoboxoffice' style='max-width:115px; float:right;'>Back To Box Office</a><br><br>";	
	
}
else
	backtoboxoffice+="<br><br>";

document.getElementById('registration').innerHTML=backtoboxoffice+response.responseText;
if($('confbacktoboxoffice')){
 jQuery("#confbacktoboxoffice").click(function(){
	jQuery("#registration").removeClass("current")
	.attr("class","slide in reverse");
	window.location.href="/view/"+$('username').value;
	});
	if($('reflink'))
		jQuery("#reflink").attr("style","right:160px;");
}
hideTicLoadingImage();
jQuery("#registration .tableborder").attr("style","padding:10px;");
jQuery("#registration .tableborder tr:first-child a,#registration .tableborder tr:last-child a").attr("class","whiteButtonlink");
jQuery("#registration .tableborder tr:first-child a,#registration .tableborder tr:last-child a").attr("style","display:none;");

//updatecurrentaction("confirmation page");
if(fbsharepopup!='N'){
	getconfirmationfbsharepopup();
	}
}