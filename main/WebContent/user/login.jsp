	<%@page import="com.event.beans.SubManagerData"%>
<%@page import="com.user.beans.UserData"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<title><s:text name="global.login.link.lbl"></s:text></title>



<style>
.form-control {
	height: 40px;
}
</style>
<div id="fb-root">
	<script src="https://connect.facebook.net/en_US/all.js"></script>
</div>
<script>
      window.fbAsyncInit = function() {
         FB.init({ 
            appId:'${FBAppId}', cookie:true, 
            status:true, xfbml:true 
         });
         FB.Event.subscribe('auth.logout', function(response) {
            // logout();
         });
         FB.getLoginStatus(function(response) {
         	var src='${source}';
             	if(src=='regmail'){
                	window.location.href="/main/user/attendee";
                    return;
                }
                if (response.authResponse)
                	login();
         	},{scope:'email'});
         };
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
 		  };
            
          function login(){
                FB.api('/me', function(response) {
                	var fbuserid = response.id;
	  var s="<span class=\"pull-left\" style=\"padding-top:5px;\">"+
      "<span class=\"pull-left\">"+
      "<img border=\"0\" src=\"https://graph.facebook.com/"+response.id+"/picture\" alt=\"\" height=\"60px\">"+
      "</span>"+
      "<span style=\"vertical-align:top\">"+
      "<font style=\"padding:5px;\">Logged in as <b>"+response.name+"</b></font>"+
      "<span class=\"pull-left\" style=\"padding-left:5px\">"+
      "<button id=\"fbcontinue\" class=\"btn btn-primary\" onclick=\"gotoattendeehome("+fbuserid+");\">Continue</button>&nbsp;<button id=\"fbnotyou\" class=\"btn btn-primary\" onclick=\"fblogout();\">Not you?</button>"+
      "</span></span></span>";
		             
                });
            }
            function fblogout(){
            FB.logout(function(response) {
				window.location.reload();
			});
            }
            
            function gotoattendeehome(fbuid){
  				//window.location.href="/attendee/mypurchases/home?fbuid="+obj.fbuid;
  				FB.api('/me', function(response1) {

  				var url='/attendee/authtoken?fbuid='+fbuid+'&fname='+response1.first_name+'&lname='+response1.last_name+'&email='+response1.email+'&gender='+response1.gender;

             	loginwithtoken(url);

			});
  				
            }
            
            function loginwithtoken(url){
             	 $.ajax( {
                	   type: "get",
                	   url: url,
                	   success: function( t ) {
                		window.location.href="/attendee/mypurchases/home?aid="+t; 
                	   }
             	   });
            
            }
          </script>


<div class="row" style="background-color: white;">
	<div class="col-md-2"></div>
	<div class="col-md-8">

		<div style="text-align: center">
			<h1>
				<span class=" text-muted"><s:text name="global.login.link.lbl"></s:text> </span>
			</h1>
			<div style="font-size: 85%">
				<s:text name="la.new.to.eventbee.lbl"/> <a href="/main/user/signup"> <s:text name="la.signup.link.lbl"/> </a>
			</div>
		</div>
	</div>
</div>

<%if(session.getAttribute("SESSION_USER")!=null || session.getAttribute("SESSION_SUBMGR")!=null) {
	String continueURL="",logoutURL="";
	String username="";
		if(session.getAttribute("SESSION_USER")!=null){
			UserData user=(UserData)session.getAttribute("SESSION_USER");
			username=user.getBeeId();%>
<div class="row" style="background-color: white;padding-bottom:20px">
	<div class="col-md-12">
		<div style="text-align: center">
			<span><s:text name="la.you.logged.lbl"/> <%=username%>. <br>
			<a href="/main/myevents/home"><s:text name="la.contine.lbl"/></a> | <a
				href="/main/user/logout"><s:text name="la.not.you.lbl"/></a></span>
		</div>
	</div>
</div>
<%}
		if(session.getAttribute("SESSION_SUBMGR")!=null){
			SubManagerData smd=(SubManagerData)session.getAttribute("SESSION_SUBMGR");
			username=smd.getEmail();%>
<div class="row" style="background-color: white;;padding-bottom:20px">
	<div class="col-md-12">
		<div style="text-align: center">
			<span><s:text name="la.you.logged.lbl"/> <%=username%>. <s:text name="la.please.lbl"/> <a
				href="javascript:submgrlogout();"><s:text name="la.logout.lbl"/></a> <s:text name="la.try.again.lbl"/>
			</span>
		</div>
	</div>
</div>
<%
		}
%>
<%}else{ %>

<div class="row" style="background-color: white;">


	<div class="col-md-2"></div>
	<div class="col-md-8">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<form class="form-horizontal" role="form" id="login" name="login"
				method="POST" action="/main/user/login!authenticate">

				<div id="errorMsg" style="text-align: center">
					<s:if test="%{message!=''}">
						<div style="height: 12px"></div>
						<s:text name="message" />
					</s:if>
				</div>
				<br />
				<div class="input-group ">
					<span class="input-group-addon"> <i class="fa fa-user"></i>
					</span>
					<!-- <input id="uname" type="text" class="form-control" name="uname" value="" placeholder="Bee ID"> -->
					<s:textfield name="uname" id="uname" key="uname"
						cssClass="form-control" placeholder="%{getText('global.beeid.ph')}" autofocus="autofocus"></s:textfield>
				</div>
				<br />
				<div class="input-group ">
					<span class="input-group-addon"> <i class="fa fa-lock"></i>
					</span> <input id="password" type="password" class="form-control"
						name="password" placeholder="<s:text name="global.pwd.ph"></s:text>">
				</div>
				<br />


				<button type="submit" id="btn-login"
					class="btn btn-block btn-primary pull-right" style="height: 36px;"><s:text name="global.login.link.lbl"></s:text></button>

			</form>
			<div style="height: 50px"></div>
			<div style="font-size: 85%">

				<span class="pull-left"> <a href="/main/submanager/login">
						<s:text name="la.submanager.login.link.lbl"></s:text> </a>&nbsp;
				</span> <span class="pull-right"> <a id="forgotpwd"
					style="cursor: pointer"><s:text name="la.forgot.password.link.lbl"></s:text></a></span>
			</div>
			<br />
			<div style="font-size: 85%">
				<span class="pull-left" style="padding-top: 5px;"> <span
					id="fbcontent" class="pull-left"> <!--  <a id="btn-fblogin" style="cursor: pointer">
                                                   Attendee Login</a>  -->
				</span>
				</span>
			</div>
		</div>
	</div>


</div>
<%} %>
<div style="height: 20px"></div>
<script>

function closediv(){}


            $(document).ready(function(){
            	if(navigator.userAgent.indexOf('MSIE')>-1)
            	   $("input.form-control").ezpz_hint();	
         	   
               $('#btn-fblogin').click(function(){
            	   fblogin();
                   });


               $('#forgotpwd').click(function() {
            	   $('.modal-title').html(props.la_login_help);
            	   $('#myModal').on('show.bs.modal', function () {
            	   $('iframe#popup').attr("src",'loginproblem?message=a');
            	   $('iframe#popup').css("height","180px"); 
            	   });
            	   $('#myModal').modal('show');
            	   });
               
                
            });

            function submgrlogout(){
                $.ajax( {
              	   type: "POST",
              	   url: '/main/submanager/logout',
              	   success: function( id ) {
              		 window.location.href="/main/user/login";
              	   }
           	   });
            	}
            window.fbAsyncInit();
        </script>