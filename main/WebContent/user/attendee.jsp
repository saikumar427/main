<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.eventbee.general.EbeeConstantsF"%>
<div id="fb-root"></div>
<script type="text/javascript">

window.fbAsyncInit = function() {
	FB.init({ 
    appId:'${FBAppId}', cookie:true, status:true, xfbml:true 
    });
    
    FB.getLoginStatus(function(response) {
		if (response.authResponse) 
        	login();
	},{scope:'email'});
};
(function() {
	var e = document.createElement('script');
    e.type = 'text/javascript';
    e.src = document.location.protocol +'//connect.facebook.net/en_US/all.js';
    e.async = true;
    document.getElementById('fb-root').appendChild(e);
}());

var fblogin=function(){
	FB.login(function(response) {
  		if (response.authResponse) {
    		//if (response.scope) {
    			FB.api('/me', function(response1) {
  					var url='/attendee/authtoken?fbuid='+response.authResponse.userID+'&fname='+response1.first_name+'&lname='+response1.last_name+'&email='+response1.email+'&gender='+response1.gender;
             		loginwithtoken(url);
				});
			//} 
		} 
	}, {scope:'email'});
	
}

function fblogout(){
	FB.logout(function(response) {
		window.location.reload();
	});
}

function login(){
	FB.api('/me', function(response) {
		var fbuserid = response.id;
        var s="<div class='row'><div class='col-sm-3'><span><img src='https://graph.facebook.com/"+response.id+"/picture' border='0' alt=''></span></div><div class='col-sm-9'><span style='vertical-align:top'><font style='padding:5px;' >"+props.global_logged_in_lbl+" <b>"+response.name + "</b></font></span></div></div>";
			s+="<br><div class='row'><div class='col-sm-4'><button class='btn btn-primary' id='conitnue'>"+props.global_continue_lbl+"</button></div>&nbsp;&nbsp;<div class='col-sm-4'><button class='btn btn-primary' id='nybtn'>Not you?</button></div></div>";
		document.getElementById('fbcontent').innerHTML=s;
		$('#conitnue').click(function(){gotoattendeehome(fbuserid);});
		$('#nybtn').click(function(){fblogout();});
	});
}


function gotoattendeehome(fbuid){ 	
	FB.api('/me', function(response1) {
		var url='/attendee/authtoken?fbuid='+fbuid+'&fname='+response1.first_name+'&lname='+response1.last_name+'&email='+response1.email+'&gender='+response1.gender;
		loginwithtoken(url);
	});
}

function gotoattendee(response){
     window.location.href="/attendee/mypurchases/home?aid="+response; 
}
function loginwithtoken(lurl){
	$.ajax({
url: lurl,
}).done(gotoattendee);
}


</script>
<%
String tid=request.getParameter("tid");
String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/"; 
if(tid == null)tid="";
%>

<div class="row" style="padding-top:20px;margin-left:-30px;">
<div class="col-md-12">
<s:text name="global.already.purchase.tickts"/></div>
</div>
<br/>
<div class="row" style="margin-left:-30px;">
<div class="col-md-12">
	  	<s:text name="global.option.login.fb.lbl"/>   
	  </div></div>
	 <div class="row" style="margin-left:-30px;">
       <div class="col-md-7">
        <div class="control">
        <div style="font-size:85%;text-align:right" class="pull-left">
        <div id="fbcontent">
         <a id="btn-fblogin" class="btn btn-primary">
            <i class="fa fa-facebook"></i>&nbsp;<s:text name="global.connect.fb.lbl"/></a></div>
         </div>
         </div></div></div><br/>
			
     <div class="row" style="margin-left:-30px;">
       <div class="col-md-12">
   <s:text name="global.option2.retrive.tickets.lbl"/>
	 </div></div>
     <div class="row" style="margin-left:-30px;">
       <div class="col-md-7">
        <div class="control">
	  <form name="getticketspdf" id="getticketspdf" action="" target="">
       <input type="hidden" name="tid" id="tid" value="<%=tid%>"><button class="btn btn-primary" id="clickhereBtn"><s:text name="global.get.tickets.lbl"/></button></form>
	</div></div>
	
<script>
   //var clickBtn=new YAHOO.widget.Button("clickhereBtn",{onclick: { fn:getPDFTicket  } });
   
  $('#clickhereBtn').click(function(){getPDFTicket();});
  $('#btn-fblogin').click(function(){fblogin();});
 function getPDFTicket(){
  var serveraddress='<%=serveraddress%>';
  document.getticketspdf.action=serveraddress+"gettickets";
  document.getticketspdf.target="_blank";
  document.getticketspdf.submit();
  }

</script>
