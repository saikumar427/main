

function activatePlaceholders() 
{
	var detect = navigator.userAgent.toLowerCase();
	if (detect.indexOf("")> 0) 
	return false;
	var inputs = document.getElementsByTagName("input");
	for (var i=0;i<inputs.length;i++) 
	{
	  if (inputs[i].getAttribute("type") == "text") 
	  {
		if (inputs[i].getAttribute("placeholder") && inputs[i].getAttribute("placeholder").length > 0) 
		{
			inputs[i].value = inputs[i].getAttribute("placeholder");
			inputs[i].onfocus = function() 
			{
				 if (this.value == this.getAttribute("placeholder")) 
				 {
					  this.value = "";
					  this.style.color ="black";
				 }
				 return false;
			}
			inputs[i].onblur = function() 
			{
				 if (this.value.length < 1) 
				 {
					  this.value = this.getAttribute("placeholder");
					  this.style.color ="darkgray";
				 }
			}
		}
	  }
	}
}


window.onload=function() {
activatePlaceholders();
activateTextArea();
}

function activateTextArea(){
     var textarea=document.getElementById("contentMsg");
     textarea.onfocus = function () {
        if (this.value == "<Click here> and type your Message - Required") {
            this.value = "";
            this.style.color ="black";
        } 
       return false; 
     };
     textarea.onblur = function () {
        if (this.value == "") {
            this.value = "<Click here> and type your Message - Required";
            this.style.color ="darkgray";
        } 
    };
}


function validateForm(){
	jQuery('#showMsg').css('display','none');
	jQuery('#subForm').prop("disabled",true);
   var username=document.getElementById('username').value;
   var email=document.getElementById('email').value;
   var phone=document.getElementById('phone').value;
   var eventurl=document.getElementById('eventurl').value;
   var message=document.getElementById('contentMsg').value;
   var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-Z0-9]{2,4}$/;
   username=username.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   email=email.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   phone=phone.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   eventurl=jQuery.trim(eventurl);
   message=jQuery.trim(message);
  // eventurl=eventurl.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   //message=message.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   document.getElementById('username').value=username;
   document.getElementById('email').value=email;
   document.getElementById('phone').value=phone;
   document.getElementById('eventurl').value=eventurl;
   document.getElementById('message').value=message;
   if(username=='' || username=='<Click here> and type your Name - Required'){
	   //bootbox.confirm("Name is required", function (result) {	});
     alert("Name is required");
     jQuery('#subForm').prop("disabled",false);
     return false;
   }
   if(email=='' || email=='<Click here> and type your Email - Required'){
	   //bootbox.confirm("Email is empty", function (result) {	});
     alert("Email is empty");
     jQuery('#subForm').prop("disabled",false);
     return false;
   }else{
     if(!email.match(emailExp)){
    	 //bootbox.confirm("Enter Valid Email", function (result) {	});
	   alert("Enter Valid Email");
	  jQuery('#subForm').prop("disabled",false);
	   return false;
	 }
   }
   if(message=='' || message=='<Click here> and type your Message - Required'){
	  // bootbox.confirm("Message is required", function (result) {	});
     alert("Message is required");
     jQuery('#subForm').prop("disabled",false);
     return false;
   }
   //document.getElementById("subForm").disabled = true;
   supportcontactinfo();
  // document.getElementById('buttons').style.display='none';
   //document.getElementById('processing').style.display='block';
    return true;
}


function supportcontactinfo(){
	jQuery('#subForm').val('Sending...');
	jQuery.ajax({
		url:'/main/user/supportmailprocessing.jsp',
		data:jQuery('#supportemail').serialize(),
		success:function(result){
			var result = JSON.parse(result);
			if('success'==result['result']){
				jQuery("[type=reset]").trigger("click");
				jQuery('#supportcontact').css('display','none');
				jQuery('#subForm').val('Submit');
				jQuery('#subForm').prop("disabled",false);
				jQuery('#showMsg').css('display','block');
			}
			else{ }
				
			
		}
	});
	
	
/*$('supportemail').request({
       onFailure: function() {
    	   document.getElementById('processing').style.display='none';
    	   document.getElementById('buttons').style.display='block'; 
    	   alert('error');
           },
        onSuccess: function(t) {
        var json=eval('('+t.responseText+')');
        var result=json.result;
        if(result=="success")
        var msg='<table align="center" style="padding-left:10px;"><tr><td style="font-family:Lucida Grande,Lucida Sans Unicode,sans-serif;font-size: 12px;">Thanks for contacting us! We will get in touch with you shortly.<td></tr>'+
        '<br/><tr><td>If you would like, please visit our <a href="#" onclick="faqpage();">FAQ</a> page!</td></tr></table>';
        document.getElementById('supportcontact').innerHTML=msg;
        }
   });*/
}
jQuery('#faqPage').click(function(){
	parent.window.location.href='http://www.eventbee.com/main/faq';
});
/*function faqpage(){
   parent.window.location.href='/main/faq';
}*/

function processEmail()
{
	document.getElementById("backgroundPopup").style.display="block";
    var msg1='<ul class="errorMessage"><li align="left"><font color="red" >Please enter your Email</font></li></ul>';
	var msg2='<ul class="errorMessage"><li align="left"><font color="red">Please enter valid Email</font></li></ul>';
	var emailid=document.getElementById('email_id').value;
	var x=document.getElementById('email_id').value;
	var url="";
	var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-Z0-9]{2,4}$/;
	if(x!="")
	{
	document.getElementById("backgroundPopup").style.display="block";
	emailid=emailid.replace(/[ \t\r]+/g,"");
	emailid=emailid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	document.getElementById('email_id').value=emailid;
	emailid=emailid.toLowerCase();
	if(emailid.match(emailExp))
	{
	  url="/main/user/mytickets!getEmailId?email_id="+emailid;
	new Ajax.Request(url, {
  	       method: 'post',
  	       onSuccess: function(t){
			   var msg3=t.responseText;
			   
			   if(msg3.indexOf("success")>-1)
			   {
			   
			   document.getElementById('statusmsg1').style.display='none';
			   document.getElementById('statusmsg').style.display='none';
			   document.getElementById("backgroundPopup").style.display="none";
			   document.getElementById('statusmsg').show();
			   document.getElementById('statusmsg').update(t.responseText);
			   }
			   else
			   {
			    t.responseText='<ul class=errorMessage><li>'+t.responseText+'</li></ul>';
			    document.getElementById('statusmsg').style.display='none';
				document.getElementById('statusmsg1').style.display='none';
		        document.getElementById("backgroundPopup").style.display="none";
				document.getElementById('statusmsg1').show();
				document.getElementById('statusmsg1').update(t.responseText);
			   }
           }
  });
	document.getElementById('transaction_id').value="";
	 }
	else
	    {
		 document.getElementById('statusmsg').style.display='none';
		 document.getElementById('statusmsg1').style.display='none';
	     document.getElementById("backgroundPopup").style.display="none";
	     document.getElementById('statusmsg1').show();
		 document.getElementById('statusmsg1').innerHTML=msg2;
		 document.getElementById('transaction_id').value="";
	    }
	}
	 else
	{
       document.getElementById('statusmsg').style.display='none';
	   document.getElementById('statusmsg1').style.display='none';
	   document.getElementById("backgroundPopup").style.display="none";
	   document.getElementById('statusmsg1').show();
       document.getElementById('statusmsg1').innerHTML=msg1;
	   document.getElementById('transaction_id').value="";
	}  
}

function processTransaction()
{
	
	document.getElementById("backgroundPopup").style.display="block";
	var msg1='<ul class="errorMessage"><li align="left"><font color="red">Please enter your Transaction ID</font></li></ul>';
	var transactionid=document.getElementById('transaction_id').value;
	var y=document.getElementById('transaction_id').value;
	var url="";
	if(y!="")
	{
	transactionid=transactionid.replace(/[ \t\r]+/g,"");
	transactionid=transactionid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,''); 
	document.getElementById('transaction_id').value=transactionid;
    url="/main/user/mytickets!getTransactionId?transaction_id="+transactionid;
    new Ajax.Request(url, {
	    	       method: 'post',
	    	       onSuccess: function(t){
				var msg=t.responseText;
				if(msg.indexOf("success")>-1)
				{
				 document.getElementById('statusmsg1').style.display='none';
				 document.getElementById('statusmsg').style.display='none';
				 document.getElementById("backgroundPopup").style.display="none";
				 document.getElementById('statusmsg').show();
				 document.getElementById('statusmsg').update(t.responseText); 
              }
             else
			   {
			    t.responseText='<ul class=errorMessage><li>'+t.responseText+'</li></ul>';
				document.getElementById('statusmsg').style.display='none';
				document.getElementById('statusmsg1').style.display='none';
	            document.getElementById("backgroundPopup").style.display="none";
				document.getElementById('statusmsg1').show();
				document.getElementById('statusmsg1').update(t.responseText); 
			   }
			 },onFailure:function(){
				 alert("Fail");
			 }
	      }); 
    document.getElementById('email_id').value="";	
	}
	else
	{
	 document.getElementById('statusmsg').style.display='none';
	 document.getElementById('statusmsg1').style.display='none';
	 document.getElementById("backgroundPopup").style.display="none";
	 document.getElementById('statusmsg1').show();
	 document.getElementById('statusmsg1').innerHTML=msg1;
	 document.getElementById('email_id').value="";
	 }
	
}

function ShowAttendeeZone(id){
	if(document.getElementById('statusmsg'))
	 document.getElementById('statusmsg').style.display='none';
	if(document.getElementById('statusmsg1'))
	 document.getElementById('statusmsg1').style.display='none';
	
	if(document.getElementById(id).style.display=='block')
	   document.getElementById(id).style.display='none';
	else
	   document.getElementById(id).style.display='block';
}

function CancelAttendeeZone(id){
	if(document.getElementById(id).style.display=='block')
	  document.getElementById(id).style.display='none';
	
}