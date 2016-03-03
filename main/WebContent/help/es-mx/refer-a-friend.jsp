<%@page import="com.eventbee.general.DbUtil"%>
<%@page import="com.user.beans.UserData"%>
<%
   String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/";
   String appid=DbUtil.getVal("select value from config where name='ebee.fbconnect.appid' and config_id=0", null);
%>
<%@include file="/getresourcespath.jsp"%>
<!-- Bootstrap styling for Typeahead -->
<link href="dist/css/tokenfield-typeahead.css" type="text/css"
	rel="stylesheet">
<!-- Tokenfield CSS -->
<link href="dist/css/bootstrap-tokenfield.css" type="text/css"
	rel="stylesheet">
<link href="social/socialnetworks.css" type="text/css" rel="stylesheet">

<%@page import="com.eventbee.general.DBManager"%>
<html>
	<head>
	<script>
		  window.fbAsyncInit = function() {
           FB.init({
               appId: '<%=appid%>',
               status: true,
               cookie: true,
               xfbml: true
           });
       };
       
       (function(d) {
           var js,
               id = 'facebook-jssdk',
               ref = d.getElementsByTagName('script')[0];
           if (d.getElementById(id))
               return;
           js = d.createElement('script');
           js.id = id;
           js.async = true;
           js.src = "//connect.facebook.net/en_US/all.js";
           ref.parentNode.insertBefore(js, ref);
       }(document));  
       </script>  
   </head>

<style>
.normal_desc_grey_ans {
	color: #333333;
	font-size: 14px;
}

.caption_header_blue_faq {
	color: #428bca;
	font-size: 32px;
}

.normal_desc_grey {
	color: #999999;
	font-size: 20px;
}

.caption_header_white {
	font-size: 30px;
	text-align: center;
	color: white;
}

#global-referral-link{
	font-size:14px !important;
	
}

#notify-wrapper {
height: 0px;
 left: 43%;
top: 100px;
text-align: center;
position: fixed;
z-index: 1001;
}
.server-success {
background-color: #e8f7ed;
border: 1px solid #30b661;
color: #1BA84E;
}

#notify {
-moz-border-radius: 3px;
-webkit-border-radius: 3px;
border-radius: 3px;
margin: 9px;
padding: 8px 18px;
font-size: 12px;
display: inline-block;
}
</style>
<body>
<div class="container"
	style="height: 250px; width: 100%; background-image: url('bootstrap/images/EB_bee_pattern_1600x250.jpg');">
	<div class="container" style="padding-top: 60px;">
		<div class="row">
			<div class="col-md-12 caption_header_white">For every friend
				that you refer to Eventbee that sells 100 tickets, we will send you
				a free Kindle Fire. Your friend gets one too!</div>
		</div>
	</div>
</div>
<div id="notify-wrapper" style="display: none"> <span id="notify" class="server-success" > <span id="notify-msg"></span> </span> </div>

<div class="container">
	<div class="container">
		<!-- <div class="row" style="padding-top: 50px;">
			<div class="col-md-12 normal_desc_grey_ans">For every friend
				that you refer to Eventbee that sells 100 tickets, we will send you
				a free Kindle Fire. Your friend gets one too!</div>
		</div> -->
		<div class="row" style="padding-bottom: 50px; padding-top: 50px;">
			<div class="col-md-12" align="center">
				<img src="images/EB_Infographic.png" class="img-responsive" id="abc" />
			</div>
		</div>
		<div class="row" style="padding-bottom:20px">
			<h1 style="text-align: center">
				<span class="text-muted">INVITE YOUR FRIENDS!</span>
			</h1>
			<!-- 
			<div class="col-md-12 normal_desc_grey" style="text-align:center">Invite your
				friends!</div> -->
		</div>
		<%
			if (session.getAttribute("SESSION_USER") == null) {
				
		%>
		<div class="row" style="padding-bottom: 50px;">
			<div class="col-md-3 col-xs-0"></div>
			<div class="col-md-6 col-xs-12">
				<button class="btn btn-block btn-lg btn-primary" id="loginEventbee">Login
					to Eventbee</button>
			</div>
			<div class="col-md-3 col-xs-0"></div>
		</div>
		<%
			} else {
				
				
					String query = "select email,first_name,last_name from user_profile where user_id=?";
					UserData user= (UserData)session.getAttribute("SESSION_USER");
					DBManager dbm = new DBManager();
					dbm.executeSelectQuery(query, new String[]{user.getUid()});
					String fname = dbm.getValue(0,"first_name","");
					String lname = dbm.getValue(0,"last_name","");
					user.setFirstName(fname);
					user.setLastName(lname);
					session.setAttribute("SESSION_USER", user);
					

		%>
		
		<!--<div class="row" style="padding-bottom: 5px">
			<div class="col-md-3 col-xs-0"></div>
			<div class="col-md-6 col-xs-12">	
			<div id="emailvalidationmsg" style="display:none;color:red;padding-bottom:2px;">			
			email is not valid</div>
			</div>
			<div class="col-md-3 col-xs-0"></div>
		</div>
		
		
		--><div class="row" style="padding-bottom: 5px">
			<div class="col-md-3 col-xs-0"></div>
			<div class="col-md-6 col-xs-12">
				<!--<input type="text" class="form-control" id="tokenfield">-->
				<textarea rows="20" cols="60" id="tokenfield"
					placeholder="Enter emails, separate with commas"></textarea>
			</div>
			<div class="col-md-3 col-xs-0"></div>
		</div>



		<div class="row" style="padding-bottom:50px">
			<div class="col-md-3 col-xs-0"></div>
			<div class="col-md-6 col-xs-12">
				<button class="btn btn-block btn-lg btn-primary" id="sendEmail">Send Email</button>
			</div>
			<div class="col-md-3 col-xs-0"></div>
		</div>
		
		<div class="row" style="padding-bottom:50px">
			<div id="referrals-social" align="center" class="normal_desc_grey">
				<p style="padding-bottom:20px">More ways to invite your friends</p>
				<div id="referrals-social-bar">
					<button class="freshbutton" id="faceshare" onclick="fbLogin();"
						style="padding-bottom: 9px; padding-top: 9px; margin: 0 5px; background: none repeat scroll 0 0 #eceef5; border: 1px solid #cad4e7; color: #3b5998;">
						<img style="position: relative; top: -2px;"
							class=" sprite sprite_web s_web_facebook"
							src="images/icon_spacer-vflN3BYt2.gif">Share on Facebook
					</button>
					
					<a class="freshbutton" id="twittershare" onclick="conftweet();"
						style="padding-bottom: 9px; padding-top: 9px; margin: 0 5px;">
						<img style="position: relative; top: -2px;"
						class=" sprite sprite_web s_web_referral_twitter"
						src="images/icon_spacer-vflN3BYt2.gif">Tweet on Twitter
					</a> <a class="freshbutton" id="copyLink"
						style="padding-bottom: 9px; padding-top: 9px;"> 
						<img
						style="position: relative; top: -2px;"
						class=" sprite sprite_web s_web_s_link"
						src="images/icon_spacer-vflN3BYt2.gif">Copy link
					</a> <input type="text" id="global-referral-link" class="textinput" value="<%=serveraddress%>signup/<%=((UserData)session.getAttribute("SESSION_USER"))==null?"":((UserData)session.getAttribute("SESSION_USER")).getBeeId()%>" onclick="this.select();" readonly>
				</div>
			</div>
		</div>
		
		<%
			}
		%>
		<div class="row">
			<div class="col-md-12">
				If you have any questions regarding this promotion, please <a
					href="javascript:;" id="promotion">click here</a> to contact us.
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Kindle Fire with
				Eventbee Manager app highlights:</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-10 normal_desc_grey_ans">
				<ul
					style="padding-left: 18px !important; margin-bottom: 0px !important;">
					<li style="color: #333333;"><span class="normal_desc_grey_ans">QR
							code and barcode ticket scanning</span></li>
					<li style="color: #333333;"><span class="normal_desc_grey_ans">Sell
							tickets and process credit cards at the door</span></li>
					<li style="color: #333333;"><span class="normal_desc_grey_ans">Monitor
							attendance and ticket sales in real time</span></li>
					<li style="color: #333333;"><span class="normal_desc_grey_ans">Simultaneous
							manager and sub-manager login</span></li>
				</ul>
				<br>
			</div>

		</div>
		<br>
		<div class="row" style="padding-bottom:50px">
			<div class="col-md-12">
				For more information on Eventbee Manager app, please visit <a
					href="/main/eventbee-manager-app">http://www.eventbee.com/main/eventbee-manager-app</a>
			</div>
		</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript" src="dist/bootstrap-tokenfield.js"
	charset="UTF-8"></script>
	<script src="/main/dist/jquery.zclip.js"></script>
	<script src="<%=resourceaddress%>/main/bootstrap/js/bootbox.min.js"></script>
<script><!--
var emails = [];
	$('#sendEmail')
			.click(
					function() {					
						//var emails = "[srinvas@eventbee.com,giri@eventbee.com,venkat@eventbee.com]";
						if(document.getElementById("tokenfield").value==''){
							bootbox.alert("Enter atleast one email to send");
						    return false;
						}
						emails.push(document.getElementById('tokenfield').value.replace(/\s/g, ''));
						document.getElementById('sendEmail').innerHTML='<i class="fa fa-spinner fa-spin"></i>&nbsp;Sending...';
						url = '/main/user/referfriend?email=' + emails;
						$.ajax({
							url : url,
							method : 'post',
							success : function(t) {
								console.log("response friend::" + t);
								$('#tokenfield').tokenfield('setTokens',' ');
								document.getElementById("tokenfield").value='';
								document.getElementById('sendEmail').innerHTML='Send Email';
								 document.getElementById('notify-msg').innerHTML='Email sent successfully';                             
                                  $('#notify-wrapper').fadeIn(1000,function(){
                            		 setTimeout(function(){$('#notify-wrapper').fadeOut(1000);},4000);			    	       
			    	       									  });								
								     emails = [];
							}

						});
					});

	$('#tokenfield')

	.on('tokenfield:createtoken', function(e) {	
	
	/*	var temp=new Array();
         label:for(i=0;i<this.length;i++){
        for(var j=0; j<temp.length;j++ ){//check duplicates
            if(temp[j]==this[i])//skip if already present 
               continue label;      
        }
        temp[temp.length] = this[i];
        }*/
      /*  var tem = new Array($('#tokenfield').tokenfield('getTokens'));
        console.log('temp::'+tem);*/

		var data = e.attrs.value.split('|')
		e.attrs.value = data[1] || data[0]
		e.attrs.label = data[1] ? data[0] + ' (' + data[1] + ')' : data[0]
		
	})

	.on('tokenfield:createdtoken', function(e) {
		// Über-simplistic e-mail validation
		var re = /\S+@\S+\.\S+/
		var valid = re.test(e.attrs.value)
		if (!valid) {
			$(e.relatedTarget).addClass('invalid')
		}
	})

	.on('tokenfield:edittoken', function(e) {
		if (e.attrs.label !== e.attrs.value) {
			var label = e.attrs.label.split(' (')
			e.attrs.value = label[0] + '|' + e.attrs.value
		}
	})

	.on('tokenfield:removedtoken', function(e) {
		//alert('Token removed! Token value was: ' + e.attrs.value)
		$('#emailvalidationmsg').hide();
	})
	
	/*.on('tokenfield:keypressevent',function(e){
		 $('#emailvalidationmsg').show();
		var re = /\S+@\S+\.\S+/
		var valid = re.test(e.attrs)
		if (!valid) {
			$('#emailvalidationmsg').show();
			//console.log("not valid");
		}else{
			$('#emailvalidationmsg').hide();
			//console.log("valid");
		}
		
		
	})*/

	.tokenfield();
	
	
	


	$(function() {  
	
		$("a#copyLink").zclip({
		      path:"/main/dist/ZeroClipboard.swf",
		      copy:function(){
		      return $("input#global-referral-link").val();
		      }
		   });
			
		 $('#copyLink').click(function(){
        	document.getElementById('global-referral-link').select();
        });   
        
        	
		$('#promotion').click(
				function() {
					$('.modal-title').html('Contact Eventbee');
					$('#myModal').on(
							'show.bs.modal',
							function() {
								$('iframe#popup').attr("src",
										'/main/user/homepagesupportemail.jsp');
								$('iframe#popup').css("height", "440px");
							});
					$('#myModal').modal('show');
				});

		$('#loginEventbee').click(function() {
			$('.modal-title').html('Login');
			$('.modal-body').attr('align', 'center');
			$('#myModal').on('show.bs.modal', function() {
				$('iframe#popup').attr("src", '/main/user/friendlogin.jsp');
				$('iframe#popup').css("height", "250px");
				$('iframe#popup').css("width", "65%");
			});
			$('#myModal').modal('show');
		});
	});

	function closePopup() {
		$('#myModal').modal('hide');
		window.location.href = window.location.href;
	}
	
	function fbFeed(){
                               FB.ui({                       
                   					   method: 'feed',
                                       name: 'Get a free Kindle Fire with Eventbee ticketing!',
                                       link: '<%=serveraddress%>signup/<%=((UserData)session.getAttribute("SESSION_USER"))==null?"":((UserData)session.getAttribute("SESSION_USER")).getBeeId()%>',
                                       picture: 'http://www.eventbee.com/home/images/social_fb.png',
                                        caption: '<%=((UserData)session.getAttribute("SESSION_USER"))==null?"":((UserData)session.getAttribute("SESSION_USER")).getFirstName()+" "+((UserData)session.getAttribute("SESSION_USER")).getLastName()%> is using Eventbee.',
                                       description: 'Spending too much on ticketing fees? Save BIG with our $1 flat fee per ticket pricing. Sign up today and receive a free Kindle Fire!'
                                        
                                     },
                                     function(response) {
                                       if (response && response.post_id) {
                                        // alert('Post was published.');                                           
                                       } else {
                                         //alert('Post was not published.');
                                       }
                                     }
                                   );
	}
	
	function fbLogin(){
			
		  FB.login(function(response1){
                     FB.api('/me', function(response) {
                                        if(response.id!=undefined){
                                          fbFeed();
                                        }
                                    });
                                    });
	}
	
	function conftweet(){	     
           var url="//images.eventbee.com/tweet_city?bee_id=<%=((UserData)session.getAttribute("SESSION_USER"))==null?"":((UserData)session.getAttribute("SESSION_USER")).getBeeId()%>";
                           window.open(url,'','height=600,width=650');  
           }
           
       
</script>