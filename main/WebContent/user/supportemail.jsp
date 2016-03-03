<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<script type="text/javascript" src="http://www.eventbee.com/home/js/customfonts/cufon-yui.js"></script>
<script src="http://www.eventbee.com/home/js/customfonts/Myriad_Pro_400.font.js" type="text/javascript"></script>
<script>Cufon.replace('.featuredeventsheader h2');</script> 
<link href="/main/css/newhomepage.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/main/js/common/common.js"></script>
<script type="text/javascript" src="/main/js/prototype.js"></script>
<script type="text/javascript">
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
     var textarea=document.getElementById("message");
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
   var username=document.getElementById('username').value;
   var email=document.getElementById('email').value;
   var phone=document.getElementById('phone').value;
   var eventurl=document.getElementById('eventurl').value;
   var message=document.getElementById('message').value;
   var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-Z0-9]{2,4}$/;
   username=username.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   email=email.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   phone=phone.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   eventurl=eventurl.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   message=message.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   document.getElementById('username').value=username;
   document.getElementById('email').value=email;
   document.getElementById('phone').value=phone;
   document.getElementById('eventurl').value=eventurl;
   document.getElementById('message').value=message;
   if(username=='' || username=='<Click here> and type your Name - Required'){
     alert("Name is required");
     return false;
   }
   if(email=='' || email=='<Click here> and type your Email - Required'){
     alert("Email is empty");
     return false;
   }else{
     if(!email.match(emailExp)){
	   alert("Enter Valid Email");
	   return false;
	 }
   }
   if(message=='' || message=='<Click here> and type your Message - Required'){
     alert("Message is required");
     return false;
   }
   supportcontactinfo();
   document.getElementById('buttons').style.display='none';
   document.getElementById('processing').style.display='block';
    return true;
}

function supportcontactinfo(){
$('supportemail').request({
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
   });
}

function faqpage(){
   parent.window.location.href='/main/faq';
}

function closediv(){
  parent.closepopuplayout();
}
</script>
<style>
 .submitbtn-style {
    height:21px;
	margin: 5px;
    background: transparent url(../images/sprite.png);
	background-repeat:repeat-x;
	border:#808080 1px solid;
    padding-left: 10px;
	padding-right: 10px;
	padding-bottom:3px;
	font: 12px/1.4 verdana,arial,helvetica,clean,sans-serif;
	valign: middle;
}

.submitbtn-style:hover {
	border-color:#7D98B8;
	background-position:0 -1300px;
	text-decoration: none;
	color: #000;
}
input[type="text"]{
    font-family: Lucida Grande,Lucida Sans Unicode,sans-serif;
    font-size: 12px;
    line-height: 20px;
    margin: 0;
    padding: 5px;
    width:338px;
}
textarea{
    font-family: Lucida Grande,Lucida Sans Unicode,sans-serif;
    font-size: 12px;
    margin: 0;
    padding-left: 5px;
    width:338px;
} 
 body {
  background: #F5F5F5;
  }
</style>
</head>
<body>
<div id="supportcontact">
<s:form name="supportemail" id="supportemail" action="/user/supportmailprocessing.jsp">
<table width="100%" align="center" style="padding-left:0px;padding-top:10px;">
<tr><td class="featuredeventsheader"><h2>Contact Support</h2></td></tr>
<tr><td><input type="hidden" name="token" id="token" value="1234"/></td></tr>
<tr><td height="20"><input type="text" name="username" id="username" placeholder="&lt;Click here&gt; and type your Name - Required" size="50" style="color:darkgray;"></td></tr>
<tr><td height="20"><input type="text" name="useremail" id="email" placeholder="&lt;Click here&gt; and type your Email - Required" size="50" style="color:darkgray;"></td></tr>
<tr><td height="20"><input type="text" name="phone" id="phone" placeholder="&lt;Click here&gt; and type your Phone" size="50"  style="color:darkgray;"></td></tr>
<tr><td height="20"><input type="text" name="eventurl" id="eventurl" placeholder="&lt;Click here&gt; and type your Event URL" size="50" style="color:darkgray;"></td></tr>
<tr><td><textarea name="message" id="message" cols="53" rows="5" height="10" placeholder="&lt;Click here&gt; and type your Message - Required" style="color:darkgray;">&lt;Click here&gt; and type your Message - Required</textarea></td></tr>
</table>
<table  align="center">
 <tr id="processing" style="display:none;"><td>Processing...</td></tr>
 <tr id="buttons"><td><input type="button" id="sendbtn" value="Send" class="submitbtn-style" onclick="validateForm();"/></td><td><input type="button" class="submitbtn-style" id="cancelbtn" value="Cancel" onclick="closediv();"/></td></tr>
</table>
</s:form>
</div>
</body>
</html>
