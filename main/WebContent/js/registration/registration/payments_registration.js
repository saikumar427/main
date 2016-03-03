

function PrcocesPaymentSectionResponse(response){
document.getElementById('registration').style.display='none';
document.getElementById('profile').style.display='none';
document.getElementById('paymentsection').style.display='block';
hideTicLoadingImage();
if(document.getElementById("eventdate"))
document.getElementById("eventdate").disabled=true;

if(document.getElementById('pageheader')){
document.getElementById('pageheader').style.display='block';
document.getElementById('pageheader').innerHTML='<a name="ticketing"></a>'+headers.paymentpage;

}

document.getElementById('paymentsection').innerHTML=response.responseText;
location.href='#ticketing';
profilepageclickcount=0;
$$('table.leftboxcontent pre').invoke('setStyle', {backgroundColor: 'transparent',border:'none'});
//updatecurrentaction("payment section");
}

function getProfilePage(){
if(document.getElementById("imageLoad")){
Element.hide('imageLoad');
loaded = true;
}
	  
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