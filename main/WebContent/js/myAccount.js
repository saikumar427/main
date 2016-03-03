$(document).ready(function(){
	$('.tab1').show();
});



function payType(){
	if($("#showMenu").is(':hidden'))
	 	 $("#showMenu").show();
	  else
		  $("#showMenu").hide();
}
/*$(document).click(function(){
	console.log("cllick");
	setTimeout(function(){
		if(!$('#showMenu').is(':hidden')){
			console.log("hiding");
			$("#showMenu").hide();
		}
	},200);		
});*/

$(document).on('mouseover','tr',function() {
	$(this).find('.hideShow').show();
	});
	$(document).on('mouseout','tr',function() {
	$(this).find('.hideShow').hide();
	});

$('#tabOne').click(function(){
	$('.tab2').hide();
	$('.tab1').show();
	$('.hideButton').show();
	if(!$(this).hasClass("btn-default")){
		$('#tabOne').addClass("btn-default").removeClass("btn-active");
		$('#tabTwo').removeClass("btn-default").addClass("btn-active");
	}
});
$('#tabTwo').click(function(){
	$('.tab1').hide();
	$('.tab2').show();
	$('.hideButton').hide();
	if(!$(this).hasClass("btn-default")){
		$('#tabTwo').addClass("btn-default").removeClass("btn-active");
		$('#tabOne').removeClass("btn-default").addClass("btn-active");
	}
});
//accountInfo Edit-Button
//changePwd	Password-Button
//accountInfoData	Account-data
//editAccountInfo	Edit-Account
//editPassword	Edit-Passeord
//accountMessage Status-message
$('#accountInfo').click(function(){
	$('#changePwd').prop("disabled",true);
	if($('#accountInfoData').is(':visible')){
		$('#accountInfoData').slideUp();
		$('#editAccountInfo').slideDown();
	}else{
		$('#accountInfoData').slideDown();
		$('#editAccountInfo').slideUp();
		$('#changePwd').prop("disabled",false);
	}
	$('#changeproErrorMsg').slideUp();
});
$('#changePwd').click(function(){
	$('#accountInfo').prop("disabled",true);
	if($('#editPassword').is(':visible')){
		$('#accountInfoData').slideDown();
		$('#editPassword').slideUp();
		$('#accountInfo').prop("disabled",false);
	}else{
		$('#accountInfoData').slideUp();
		$('#editPassword').slideDown();
	}
	 $('#changepwdErrorMsg').slideUp();
});
scrollTo = scrollTo+40;
function updatePassword(){
	$('#accountMessage').html('');
$.ajax( {
   url: 'home!savepassword',
   data:$('#passwordForm').serialize(),
   success: function( t ) {
	  var result = t;
	  if(!isValidActionMessage(t)) return;
	  if(result.indexOf("errorMessage")>-1){
		  if(document.getElementById('changepwdErrorMsg')){
			  $('#changepwdErrorMsg').show();
			  $('#changeproStatusMsg').hide();
			  $('#changeproErrorMsg').hide();
			  $('#changepwdStatusMsg').hide();
         }  
		 $('#changepwdErrorMsg').html(t);			  	     	  		
	  	}else{
	  		if(document.getElementById('changepwdStatusMsg')){
	  			$('#changepwdStatusMsg').show();
	  			$('#changeproStatusMsg').hide();
	  			$('#changepwdErrorMsg').hide();
	  		}  
  		$('#editPassword').slideUp();
  		$('#accountInfoData').slideDown();
  	//	statusMessage('accountMessage','Password updated','success');
                notification(props.ai_pwd_updated_successfully,'success');
  		$('#accountInfo').prop("disabled",false);
  		$('html, body').animate({ scrollTop: $("#accountMessage").offset().top-scrollTo}, 1000);
    	//$('#changepwdStatusMsg').html(t);			  	     		
  		}
   }
   });
}
var accountInfoObject = {};
function updateAccInfo(){ 	
	$('#accountMessage').html('');
	 $.ajax( {
	   url: 'home!save',
	   data:$('#accInfoForm').serialize(),
	   success: function( t ) {
 		var result = t;
 		if(!isValidActionMessage(t)) return;
 		if(result.indexOf("errorMessage")>-1){
 			if(document.getElementById('changeproErrorMsg')){
	          $('#changeproErrorMsg').show();
	          $('#changeproStatusMsg').hide();
	          $('#changepwdStatusMsg').hide();
	          $('#changepwdErrorMsg').hide();
	         }  
	    	 $('#changeproErrorMsg').html(t);
	    	$('html, body').animate({scrollTop:$('#changeproErrorMsg').position().top}, 'slow');	 	    	     		
 		}else{
	  		if(document.getElementById('changeproStatusMsg')){
	          $('#changeproStatusMsg').show();  
	          $('#changeproErrorMsg').hide();
	          $('#changepwdStatusMsg').hide();
	         }
	  		$('#editAccountInfo').slideUp();
	  		$('#accountInfoData').slideDown();
//	  		statusMessage('accountMessage','Account updated','success');
                        notification(props.ai_account_updated_msg,'success');
	  		$('#changePwd').prop("disabled",false);
	  		$('html, body').animate({ scrollTop: $("#accountMessage").offset().top-scrollTo}, 1000);
	  		accountInfoObject =  $('#accInfoForm').serializeObject();
	  		updateAccountInformation(accountInfoObject);
	    	// $('#changeproStatusMsg').html(t);	  	    		
	  		}
 		}
	});
}
function updateAccountInformation(obj){ 
	var html="";
	for(key in obj){	
		if(key!='state' && obj[key]!='' && obj[key]!=1)
			html+='<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label>'+labels[key]+'&nbsp;:</label></div>'+
			'<div class="col-md-10 col-sm-8 col-xs-6 aFname">'+obj[key]+' </div><div style="clear:both;"></div>';
	}
	$("#accountInfoData").html(html);
}

