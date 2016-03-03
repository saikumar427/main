var hidelink=false;
function PrcocesConfirmationResponse(response){
clickcount=0;

//if(document.getElementById('imageLoad'))
//document.getElementById('imageLoad').style.display='none';
document.getElementById('registration').style.display='block';
document.getElementById('profile').style.display='none';

if(document.getElementById('paymentsection'))
document.getElementById('paymentsection').style.display='none';
if(document.getElementById('pageheader')){
document.getElementById('pageheader').style.display='block';
document.getElementById('pageheader').innerHTML='<a name="ticketing"></a>'+headers.confirmationpage
}

document.getElementById('registration').innerHTML=response.responseText;
hideTicLoadingImage();
showSharingOptions();
if(!fbavailable){
if($('subbtn'))
$('subbtn').update('');
}
location.href='#ticketing';
//updatecurrentaction("confirmation page");
if($('fbsharepopup').value!='N'){
	//getconfirmationfbsharepopup();
	}
}
function gotoattendeehome(){
	fbtype='fbattendee';
	fbcommon();
}
        
  