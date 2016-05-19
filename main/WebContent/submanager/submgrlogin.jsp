<%@page import="com.event.beans.SubManagerData"%>
<%@page import="com.user.beans.UserData"%>
<%@taglib uri="/struts-tags" prefix="s"%><div id="fb-root">
       <script src="https://connect.facebook.net/en_US/all.js"></script></div>
        <script>
      window.fbAsyncInit = function() {
         FB.init({ 
            appId:'${FBAppId}', cookie:true, 
            status:true, xfbml:true 
         });
         /*FB.Event.subscribe('auth.login', function(response) {
         	//alert(response.session.uid);
             //login();
             
             FB.api('/me', function(response1) {

  				var url='/attendee/authtoken?fbuid='+response.session.uid+'&fname='+response1.first_name+'&lname='+response1.last_name+'&email='+response1.email+'&gender='+response1.gender;

             	loginwithtoken(url);

			});
         });*/
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
        /*  (function() {
                var e = document.createElement('script');
                e.type = 'text/javascript';
                e.src = document.location.protocol +
                    '//connect.facebook.net/en_US/all.js';
                e.async = true;
                document.getElementById('fb-root').appendChild(e);
            }()); */
            
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

				var s="<span class=\"pull-left\">"+
				"<img border=\"0\" src=\"https://graph.facebook.com/"+response.id+"/picture\" alt=\"\" height=\"60px\"></span>"+
				"<span style=\"vertical-align:top\">"+
				"<span class=\"pull-left\">"+
				"<font style=\"padding:5px;\">Logged in as <b>"+response.name+"</b></font><br/>"+
				"&nbsp;&nbsp;<button id=\"fbcontinue\" class=\"btn btn-primary\" onclick=\"gotoattendeehome("+fbuserid+");\">Continue</button>&nbsp;<button id=\"fbnotyou\" class=\"btn btn-primary\" onclick=\"fblogout();\">Not you?</button>"+
				"</span></span></span>";
				      
						//document.getElementById('fbcontent').innerHTML=s;
					//$('#fbcontent').parent().css('margin-left','-85px');                   
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
  				
  				//var url='/attendee/authtoken?fbuid='+obj.fbuid;
  				//loginwithtoken(url);
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
    <h1><span class=" text-muted"><s:text name="la.submanager.login.link.lbl" /></span></h1>
     </div>
     </div>
     </div>
     <%if(session.getAttribute("SESSION_USER")!=null || session.getAttribute("SESSION_SUBMGR")!=null) {
	String continueURL="",logoutURL="";
	String username="";
		if(session.getAttribute("SESSION_USER")!=null){
			UserData user=(UserData)session.getAttribute("SESSION_USER");
			username=user.getBeeId();%>
			<div class="row" style="background-color: white;">	
					<div  style="text-align: center">	<span><s:text name="la.you.logged.lbl"/> <%=username%>. <s:text name="la.please.lbl"/> <a href="javascript:mgrlogout();"><s:text name="la.logout.lbl"/></a> <s:text name="la.try.again.lbl"/></span></div>
					<br/>
			</div>
		<%}
		if(session.getAttribute("SESSION_SUBMGR")!=null){
			SubManagerData smd=(SubManagerData)session.getAttribute("SESSION_SUBMGR");
			username=smd.getEmail();%>
		
			<div class="row" style="background-color: white;">	
				<div  style="text-align: center"><span><s:text name="la.you.logged.lbl" /><%=username%>. <br><a href="/main/submanager/events"><s:text name="la.contine.lbl"/></a> | <a href="/main/submanager/logout"><s:text name="la.not.you.lbl"/></a></span></div>
			    <br/>
			</div>
			
			<%
		}
}else{ %>
 <div class="row" style="background-color: white;">
    
     
     <div class="col-md-2"></div>
     <div class="col-md-8"><div class="col-md-3"></div>
     <div class="col-md-6">
     <form  class="form-horizontal" role="form" id="login" name="login" method="POST" action="login!authenticate"  >
  <div id="errorMsg" style="text-align: center"><div style="height: 10px"></div><s:if test="%{message!=''}"><s:text name="la.invalid.error.lbl"/><div style="height: 20px"></div> </s:if></div>   
                            <div class="input-group">
                                        <span class="input-group-addon">
                                            <i class="fa fa-envelope-o"></i>
                                        </span>
                                        <!-- <input id="uname" type="text" class="form-control" name="uname" value="" placeholder="Bee ID"> -->
                                     <s:textfield placeholder="Email" id="email" key="email" name="email" cssClass="form-control" autofocus="autofocus"></s:textfield>
                                    </div>
                                    <br/>
                                     <div  class="input-group">
                                        <span class="input-group-addon">
                                            <i class="fa fa-lock"></i>
                                        </span>
                                        <input id="password" type="password" class="form-control" name="password" placeholder="<s:text name="epc.password.lbl"/>">
                                    </div>

                                    <br/>
                                     
                            
                         
                                <button type="submit" id="btn-login" class="btn btn-block btn-primary pull-right" style="height:36px;"><s:text name="global.login.link.lbl"/></button>
                             
                            
  </form><div style="height:50px"></div>
  
  <div style="font-size:85%">
  <a href="/main/user/login"><s:text name="la.sub.mgr.login.lbl"/></a>
  <span class="pull-right">
   <a id="forgotpwd" style="cursor: pointer"><s:text name="la.forgot.password.link.lbl"/></a>&nbsp;
   <!--  <a id="changepwd" style="cursor: pointer">Change password</a> -->
    </span>
                                              <%-- <span class="pull-right">
                                          	 <a href="/main/user/login">
                                                   Manager Login 
                                                </a>&nbsp;
                                                <span id="fbcontent" >
                                                <a id="subbtn-fblogin" style="cursor: pointer">
                                                   Attendee Login</a>
                                            </span>
                                            </span> --%></div>
                                            
                                            <div style="font-size:85%;padding-top:10px"  >
                                             
                                               
                                                <span id="fbcontent" >
                                                <!-- <a id="subbtn-fblogin" style="cursor: pointer">
                                                   Attendee Login</a> -->
                                           		 </span>
                                            </div>
                                            </div>
                                            </div>
                                            </div>
                                            
                <%} %>       
     <div style="height: 20px"></div>
     
    <script>
            $(document).ready(function(){
            	if(navigator.userAgent.indexOf('MSIE')>-1)
             	   $("input.form-control").ezpz_hint();
            	$('#subbtn-fblogin').click(function(){
             	   fblogin();
                    });
            	 $('#forgotpwd').click(function() {
              	   $('.modal-title').html(props.la_login_help);
              	   $('#myModal').on('show.bs.modal', function () {
              	   $('iframe#popup').attr("src",'/main/user/loginproblem?message=a&role=submgr');
              	   $('iframe#popup').css("height","170px"); 
              	   });
              	   $('#myModal').modal('show');
              	   });
/* $('#changepwd').click(function(){
	 $('.modal-title').html('Change Password');
	   $('#myModal').on('show.bs.modal', function () {
	   $('iframe#popup').attr("src",'changepassword!subMgrChangepwd?t=a');
	   $('iframe#popup').css("height","300px"); 
	   });
	   $('#myModal').modal('show');
}); */
            });
            function mgrlogout(){
            	$.ajax( {
               	   type: "POST",
               	   url: '/main/user/logout',
               	   success: function( result ) {
               		 window.location.href="/main/submanager/login";
               	   }
            	   });
            	}
        </script>