

function PrcocesPaymentSectionResponse(response){
//document.getElementById('registration').style.display='none';
//document.getElementById('profile').style.display='none';
//document.getElementById('paymentsection').style.display='block';
$('paymentsection').className='current slide in';
$('profile').className='previous';
hideTicLoadingImage();
if(document.getElementById("eventdate"))
document.getElementById("eventdate").disabled=true;

if(document.getElementById('pageheader')){
document.getElementById('pageheader').style.display='block';
document.getElementById('pageheader').innerHTML='<a name="ticketing"></a>'+headers.paymentpage;

}
var backtoprofilelabel="Back To Profile Page";
if(tktsData.RegFlowWordings["event.reg.backtoprofile.label"]!=undefined)
	backtoprofilelabel=tktsData.RegFlowWordings["event.reg.backtoprofile.label"];
if(backtoprofilelabel=="")
	backtoprofilelabel="Back To Profile Page";
var backtoprofilebutton="<a href='#profile' class='slide button backtoboxoffice'   onClick=getProfilePage(); >"+backtoprofilelabel+"</a><br><br>";
document.getElementById('paymentsection').innerHTML=backtoprofilebutton+response.responseText;
if($("showotherpaymentdesc")){
jQuery("#showotherpaymentdesc").click(function(){
	jQuery("#otherpaymentdiv").slideToggle("slow",function(){
	if($('otherpaymentdiv').style.display=="block"){
		jQuery("#showotherimg").attr("src","/home/images/collapse.gif");
	}
	else{
		jQuery("#showotherimg").attr("src","/home/images/expand.gif");
	}
	});
});
}
profilepageclickcount=0;
//updatecurrentaction("payment section");
}

function getProfilePage(){
if(document.getElementById("imageLoad")){
Element.hide('imageLoad');
loaded = true;
}
$('paymentsection').className='';
$('profile').className='current slide in reverse'; 
document.getElementById('paymentsection').style.display='none';
if(document.getElementById('profilesubmitbtn'))
document.getElementById('profilesubmitbtn').style.display='block';
document.getElementById('profile').style.display='block';
if(document.getElementById('pageheader')){
if(headers.profilepage!=''){
document.getElementById('pageheader').style.display='block';
document.getElementById('pageheader').innerHTML=headers.profilepage;
}
else
document.getElementById('pageheader').style.display='none';
}
profilepageclickcount=0;
//updatecurrentaction("profile page");
}
