<%@page import="com.eventbee.general.EbeeConstantsF,com.eventbee.general.DbUtil" %>
<%
   String serveraddress="//"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/";
   String appid=DbUtil.getVal("select value from config where name='ebee.fbconnect.appid' and config_id=0", null);
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="bootstrap/css/bootstrap-social.css" rel="stylesheet">
		<script type="text/javascript" src="http://apis.google.com/js/plusone.js"></script>
		 <script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>
         <script>var servadd='<%=serveraddress%>';
                 var  fbavailable=false;window.fbAsyncInit=function(){fbavailable=true;FB.init({appId:<%=appid%>,status:true,cookie:true,xfbml:true})};(function(a){var b,c='facebook-jssdk';if(a.getElementById(c)){return}b=a.createElement('script');b.id=c;b.async=true;b.src='//connect.facebook.net/en_US/all.js';a.getElementsByTagName('head')[0].appendChild(b)})(document)</script>
	</head>
	<style>
	.caption_header_blue{
		font-size:32px;
		color: #428BCA;
	}
	p{
		line-height:24px;
	}
	.pricing_desc_grey{
		color: #999999;
		font-size: 20px;
	}
	.main_header_orange {
		color: #f27d2f;
		font-size: 42px;
		font-weight: 800;
		text-align: center;
	}
	.medium_desc_grey {
		color: #999999;
		font-size: 20px;	  
	}
	.arrow_box { 
		background: #fff; border: 1px solid #bbb; 
		position: relative;border-radius: 3px;
		padding: 8px 5px; width: 60px;margin-bottom:10px;
	}
	.arrow_box:after, .arrow_box:before { 
		top: 100%; left: 50%; border: solid transparent; content: " "; height: 0; width: 0; position: absolute; pointer-events: none;
	}
	.arrow_box:after {
		border-color: #fff rgba(255, 255, 255, 0) rgba(255, 255, 255, 0);
    border-width: 6px; margin-left: -6px;
	}
	.arrow_box:before {
		 border-color: #bbb rgba(187, 187, 187, 0) rgba(187, 187, 187, 0);
   border-width: 7px; margin-left: -7px;
	}
	.arrow_box > span{
		cursor:pointer;
	}
	.arrow_box span:hover{
		cursor:pointer;
		text-decoration:underline;
	}
	a{
		outline:none !important;
	}
	.btn{
		outline:none !important;
	}
	.btn:hover, .btn:focus{
		color:#fff !important;
	}
	</style>
	<body>
		<div class="container" style="width:100% !important;padding-left:0 !important;background-color:#fff !important">
			<div class="row" style="padding-top:50px !important;padding-bottom:25px !important;">
				<div class="col-xs-12">
					<div class="panel-heading" style="padding-top:0px !important; padding-bottom:0px !important; border:none !important;color: #58585a;background-color:#fff !important; text-align:center;">
						<span style="color: #999999;font-size: 42px;">DEMOLAY INTERNATIONAL &#45; CASE STUDY</span>
					</div>
				</div>
			</div>
		</div>
		<div class="container" style="width:100% !important;padding-left:0 !important;padding-right:0 !important;background-color:#f1f5f7 !important;">
			<div class="container" style="padding-bottom: 50px; padding-top: 50px;">			
			<div class="row">
				<div class="col-md-12 col-lg-12" style="background-color:#f1f5f7 !important;">
					<div class="col-md-8">
						<div class="row">
									<h4 class="list-group-item-heading caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Challenge</h4><br>
									<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">
									As a non&#45;profit, DeMolay International was having trouble finding an online platform that would allow for them to design a detailed registration form at a very low price. They needed their form to be dynamic and customizable, and they needed to ask attendees questions based on which tickets they were choosing. DeMolay also wanted to use their current Authorize.net merchant account to accept credit card payments, and they wanted everything to be very user friendly and easy. 
									</div>
								</div><br>
								<div class="row">
									<h4 class="list-group-item-heading caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Solution</h4><br>
										<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">DeMolay International decided to take advantage of Eventbee's custom events pages. They needed to offer a wide variety of options to attendees, and Eventbee was the perfect fit. They could set up an event page for people to register for the event, reserve hotel rooms, pay for meals, and answer questions related to the purchase. Eventbee made it possible to handle all of these tasks in a simple and efficient manner.  
									</div>
								</div><br>
								<div class="row">
										<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">They also took advantage of Eventbee's flexible credit card processing capabilities. Now, DeMolay International could use their existing Authorize.net account to process payments for their event tickets. Eventbee also supports by Paypal, Braintree, and Stripe credit card processing.</div>
								</div><br>
								<div class="row">
										<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">Eventbee's discount for non-profit organizations was also a huge help to the company. DeMolay International was able to take advantage of the <font face="arial">&ldquo;</font>Eventbee for Common Good<font face="arial">&rdquo;</font> program, which aims to help non-profit organizations save money on online ticketing. Eventbee offers a 25% discount for 501c3 non&#45;profit organizations. This was perfect for DeMolay International.</div>
								</div><br>
								<div class="row">
										<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">The company also enjoys the fact that Eventbee has a great customer service staff that can always help. They stated that <font face="arial">&ldquo;</font>The customer service staff is excellent and they were always able to answer my questions or provide a solution when asked.<font face="arial">&rdquo;</font> Eventbee is perfect for DeMolay International because they value the fact that they can give unlimited options, while keeping their costs low.</div>
								</div><br>
								<div class="row">
									<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">
									<h4 class="list-group-item-heading caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Features Used</h4><br>
									<ul class="normal_desc_grey_ans" style="padding-left: 20px !important;margin-bottom:0px !important;">	
										<li style="color:#333333;"><span class="normal_desc_grey_ans">Event Page Customization</span></li>
										<li style="color:#333333;"><span class="normal_desc_grey_ans">Flexible Credit Card Processing</span></li>												
										<li style="color:#333333;"><span class="normal_desc_grey_ans">Different Ticket Types</span></li>												
										<li style="color:#333333;"><span class="normal_desc_grey_ans">Ticket Level Questions</span></li>												
										<li style="color:#333333;"><span class="normal_desc_grey_ans">Custom Registration Form</span></li>												
									</ul><br>
									</div>
								</div>
					</div>
					<div class="col-md-4" style="padding: 48px;background-color:#fff;">
						<div class="row">
							<div class="col-md-12" align="center" style="padding-left:0px !important;padding-right:0px !important;" >
							<div align="center"> <img class="img-responsive" width="300px" height="200px" border="0" src="bootstrap/images/demolay_international.jpg" alt="BISHOP SCHOOL" > </div>
							</div>
						</div><br>

						<div class="row">
							<div class="col-md-12 normal_desc_grey_ans" align="center">DeMolay International</div></div>
						
						<div class="row">
							<div class="col-md-12 normal_desc_grey_ans" align="center">10200 NW Ambassador Drive</div>
						</div>
						<div class="row">
							<div class="col-md-12 normal_desc_grey_ans" align="center">Kansas City, MO</div>
						</div><br>
						<div class="row">
							
									<h4 class="list-group-item-heading medium_desc_grey" align="center" style="margin-left: 0px !important;">About DeMolay International</h4><br>
										<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">DeMolay International is a non&#45;profit organization that holds many types of events all over the country. People come from all over the world to attend the Demolay International Annual Convention, which is an event that aims to prepare young men for happy and successful lives through character building and leadership training over the course of a weekend. From registering for the convention, to paying for meals and hotel rooms, there is a lot of thought and planning that goes into this event.</div>
								</div><br>
								
							<div class="row">
							     <div class="col-md-4" align="center">
									   <div id='fb-root'></div>
									   <fb:like href='https:<%=serveraddress%>main/eventbee-customer-case-study-demolay-international' send='false' layout='box_count' width='60' show_faces='true'></fb:like>
									</div>
									<div class="col-md-4" align="center">
									  <a href="http://twitter.com/share" class="twitter-share-button" data-url="http://bit.ly/1tAUnqo" data-text="DeMolay International uses Eventbee's flexible credit card processing capabilities" data-count="vertical" data-via="Eventbee" rel="external">Tweet</a>
									</div>
									<div class="col-md-4" align="center">
									  <g:plusone size="tall" href="http:<%=serveraddress%>main/eventbee-customer-case-study-demolay-international"></g:plusone>
									</div>
								</div>
					</div>
					
				
		</div>
			</div>
</div></div>			
		<div class="container" style="width:100% !important;background-color:#fff !important">
		<div class="container" style="padding-bottom: 50px; padding-top: 50px;">	
			<div class="row">
					<div class="col-md-12 medium_desc_grey" align="center" style="padding-left:0px;"><span><font face="arial">&ldquo;</font>The customer service staff is excellent and they were always able to answer my questions or provide a solution when asked.<font face="arial">&rdquo;</font></span></div>
					<div class="col-md-12 normal_desc_grey_ans" align="center" style="padding-left:0px;">Gary Denklau, DeMolay International</div>
					

</div></div>
					
				
			</div>
			</div>
		</div>
		<div class="container" style="width:100% !important;padding-left:0 !important;background-color:#f1f5f7 !important;">
			<div class="container" style="padding-bottom: 50px; padding-top: 50px;">	
			<div class="row">
				
					
					<div class="col-md-12 medium_desc_grey" style="padding-left:0px;">
						<div class="main_header_orange">Do you host events?</div>
					
					</div>
					<div class="col-md-12 medium_desc_grey" style="padding-left:0px;padding-top:10px;" align="center">
						<a href="/main/how-it-works" class="btn btn-primary" style="border-radius:4px;">Learn How It Works</a>    
					
					</div>
					
				
			</div>
			</div>
		</div>			
	</body>
</html>