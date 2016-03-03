<html>
<head>
<script type="text/javascript" src="http://www.eventbee.com/home/js/jQuery.js"></script>
<script type="text/javascript" src="http://www.eventbee.com/home/js/prototype.js"></script>

<script type="text/javascript">
function contactme(){
	   var name=document.getElementById('profile_name').value;
	   var email=document.getElementById('profile_email').value;
	   var phone=document.getElementById('profile_phone').value;
	   var company=document.getElementById('profile_company').value;
	   email=email.toLowerCase();
	   email=email.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	   var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-Z0-9]{2,4}$/;
	   var flag=true;
	    name=name.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	   if(name==""){
	   alert("Enter Name");
	   flag=false;
       return false;  }
       if(email!=""){
	   if(!email.match(emailExp)){
	   alert("Enter Valid Email");
	   flag=false;
	    return false;
	   }}else{
	   alert("Enter Email");
	   flag=false;
	    return false;
	   }
	   if(isNaN(phone)){
	   alert("Enter Valid Phone Number");
	   flag=false;
	    return false;
	   }
	     if(flag){
		sendajaxreq();
		}
	    }
function sendajaxreq(){
		 $('profile_frm').request({
         onSuccess: function(t) {
		    var jsondata=eval('('+t.responseText+')');
			var result=jsondata.result;
            var msg="<span class='bioformcontent'>Thank you for your interest in Volumebee. We will contact you soon!<span>";
			if(result=="success")
			 document.getElementById('contactinfo').innerHTML=msg;
           },onFailure: function() { alert("fail");}
        });
		}
		
		
		
		
		
</script>
 
<style>

.bioformheader  {
margin-top:1px;
margin-bottom:5px;
font-family: League Gothic,Helvetica,Arial,sans-serif;
font-size: 150%;
line-height: 20%;
color: #ccc;
text-shadow: -2px -1px 1px rgba(0, 0, 0, 0.3);
text-align:left;
}

.biouformsubmit {
background: url("/main/images/home/vb_dealbuybutton_bg.png") repeat  transparent;
border: 0 none;
border-radius: 6px;
display: inline-block;
overflow: visible;
padding: 5px;
box-shadow: 0 0 3px rgba(0, 0, 0, 0.3) inset;
color: #fff;
text-shadow: 1px 1px 0 rgba(255, 255, 255, 0.7);
vertical-align:middle;
height: 32px;
}

.bioformbutton {
background: url("/main/images/home/vb_dealbuybutton_in_bg.png") repeat-x scroll 50% 0 transparent;
border: 0 none;
border-radius: 4px 4px 4px 4px;
text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.3);
-moz-box-shadow: 0 0 3px rgba(0, 0, 0, 0.3) inset;
-webkit-box-shadow: 0 0 3px rgba(0, 0, 0, 0.3) inset;
display: block;
font-family: League Gothic,Helvetica,Arial,sans-serif;
font-size: 16px;
font-weight: 800;
height: 32px;
line-height: 32px;
text-transform: uppercase;
white-space: nowrap;
cursor: pointer;
margin: 0;
padding: 0 8px;
position: relative;
color: #fff;
}


.bioformbuttonhover {
background: #333333;
border: 0 none;
border-radius: 4px 4px 4px 4px;
text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.3);
-moz-box-shadow: 0 0 3px rgba(0, 0, 0, 0.3) inset;
-webkit-box-shadow: 0 0 3px rgba(0, 0, 0, 0.3) inset;
display: block;
font-family: League Gothic,Helvetica,Arial,sans-serif;
font-size: 16px;
font-weight: 800;
height: 32px;
line-height: 32px;
text-transform: uppercase;
white-space: nowrap;
cursor: pointer;
margin: 0;
padding: 0 8px;
position: relative;
color: #fff;
}
.bioformcontent, .bioformcontent td{
color: #fff;
font-family: Lucida Grande,Lucida Sans Unicode,sans-serif;
font-size: 12px;
line-height: 20px;
margin: 0;
padding: 0;
}
</style>
</head>
<body>
<div id="contactinfo">
<form action="/main/vb_contact/contactinfo.jsp" name="profile_frm" id="profile_frm" method="post">
	 <table align="center" cellspacing="0" cellpadding="0" valign="middle">
	 <tbody><tr><td><span class="bioformheader">Interested in using Volumebee?</span></td></tr>
	 <tr><td height="15"></td></tr>
	 <tr><td align="center">
	     <table align="center" width="100%" class="bioformcontent">
		 <tbody><tr>
		 <td align="center" valign="top">
			<table align="left" width="100%"><tbody>
			
			<tr><td align="left" width="40%" valign="top"><span>Name</span><span> *</span></td><td align="left" width="60%" valign="top"><input type="text" size="30" id="profile_name" name="profile_name"></td></tr></tbody></table>
		</td>
		 </tr>
		 <tr>
		 <td align="center" valign="top">
			<table align="left" width="100%"><tbody><tr><td align="left" width="40%" valign="top"><span>Email</span><span> *</span></td><td align="left" width="60%" valign="top"><input type="text" size="30" id="profile_email" name="profile_email"></td></tr></tbody></table>
		</td>
		 </tr>
		 <tr>
		 <td align="center" valign="top">
			<table align="left" width="100%"><tbody><tr><td align="left" width="40%" valign="top"><span>Phone</span><span></span></td><td align="left" width="60%" valign="top"><input type="text" size="30" id="profile_phone" name="profile_phone"></td></tr></tbody></table>
		</td>
		 </tr>
		 <tr>
		 <td align="center" valign="top">
			<table align="left" width="100%"><tbody><tr><td align="left" width="40%" valign="top"><span>Company</span><span> </span></td><td align="left" width="60%" valign="top"><input type="text" size="30" id="profile_company" name="profile_company"></td></tr></tbody></table>
		</td>
		 </tr>
		 <tr><td height="5"></td></tr>
		 <tr><td align="center" valign="top" cellspacing="0" cellpadding="0">
			<table align="center" width="100%" cellspacing="0" cellpadding="0"><tbody><tr>
		  <td align="center" cellspacing="0" cellpadding="0"><div class="biouformsubmit">
		  <input type="button" name="Contact Me" value="Contact Me" onclick="contactme();" onmouseover="this.className ='bioformbuttonhover'" onmouseout="this.className ='bioformbutton'" class="bioformbutton"> </div>
		  </td>
		  </tr>
		  </tbody></table>
	  </td></tr>
	  </tbody></table>
	 </td></tr>
	 </tbody></table>
	 
	 </form>
	 </div>
	 </body>
	 </html>
