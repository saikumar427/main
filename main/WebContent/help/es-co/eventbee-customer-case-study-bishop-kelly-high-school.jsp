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
						<span style="color: #999999;font-size: 42px;">BISHOP KELLY HIGH SCHOOL &#45; CASE STUDY</span>
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
									Before Eventbee, it was difficult for the school to manage the number of students that could attend events, and they had no solution for reserved seating. Because most of their events are held in an auditorium with a set number of seats, it was important to be able to limit the number of tickets sold, and make sure that each attendee had a place to sit. They also wanted to be able to manage the ticket sales for specific areas of their events. Bishop Kelley High School needed a solution that would allow them to sell multiple ticket types, and create a community around their school's events.
									</div>
								</div><br>
								<div class="row">
									<h4 class="list-group-item-heading caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Solution</h4><br>
										<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">Bishop Kelley High School decided to take advantage of Eventbee's many features in order to bring control and efficiency to their events. 
									</div>
								</div><br>
								<div class="row">
										<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">The first thing they did was to use Eventbee's reserved seating feature, digitize the auditorium seating, and integrate it into their event pages. This lets students and parents reserve the seats they want! Now the school could sell the perfect number of tickets in no time, and make sure that everyone had a seat. They stated that <font face="arial">&ldquo;</font>Our Annual Trivia Night sells out in a matter of hours and is much easier to manage using Eventbee.<font face="arial">&rdquo;</font> Reserved seating turned out to be the perfect solution for the school.
									</div>
								</div><br>
								<div class="row">
										<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">They also enjoy the ability to sell multiple ticket types for the school's events. For events like the All&#45;School Musical, they can sell specific seats at certain prices. If they want to reserve a section of seats at a higher or lower price, they can do that in just a few clicks. They took advantage of a few other features, as well. When an attendee chooses a certain ticket type, being able to ask questions geared to that specific ticket level is a huge help. Eventbee is perfect for Bishop Kelly High School because they value the options that they are able to provide for the students. 
									</div>
								</div><br>
								<div class="row">
									<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">
									<h4 class="list-group-item-heading caption_header_blue" style=" margin-left: 0px !important;color: #428bca;font-size: 32px;">Features Used</h4><br>
									<ul class="normal_desc_grey_ans" style="padding-left: 20px !important;margin-bottom:0px !important;">	
										<li style="color:#333333;"><span class="normal_desc_grey_ans">Reserved Seating</span></li>
										<li style="color:#333333;"><span class="normal_desc_grey_ans">Different Ticket Types</span></li>												
										<li style="color:#333333;"><span class="normal_desc_grey_ans">Real Time Reports</span></li>												
										<li style="color:#333333;"><span class="normal_desc_grey_ans">Ticket Level Questions</span></li>												
										<li style="color:#333333;"><span class="normal_desc_grey_ans">Attendee Emails</span></li>												
									</ul><br>
									</div>
								</div>
					</div>
					<div class="col-md-4" style="padding: 48px;background-color:#fff;">
						<div class="row">
							<div class="col-md-12" align="center" style="padding-left:0px !important;padding-right:0px !important;" >
							<div align="center"> <img class="img-responsive" width="300px" height="200px" border="0" src="bootstrap/images/bishop.jpg" alt="BISHOP SCHOOL" > </div>
							</div>
						</div><br>

						<div class="row">
							<div class="col-md-12 normal_desc_grey_ans" align="center">Bishop Kelly High School
							</div></div>
						
						<div class="row">
							<div class="col-md-12 normal_desc_grey_ans" align="center">3905 South Hudson Avenue</div>
						</div>
						<div class="row">
							<div class="col-md-12 normal_desc_grey_ans" align="center">Tulsa, OK</div>
						</div><br>
						<div class="row">
							
									<h4 class="list-group-item-heading medium_desc_grey" align="center" style="margin-left: 0px !important;">About Bishop Kelly High School</h4><br>
										<div class="col-md-12 normal_desc_grey_ans" style="padding-left:0px;">Bishop Kelley is a high school in Tulsa Oklahoma. Like many schools across the United States, they hold events, shows, and contests every single year. Some of their events include the Annual Golf tournament, the All&#45;School Musical, Trivia Night, and even the All Night Graduation Party at the end of the year. It was important to Bishop Kelly that they were taking advantage of tools that would help their events become more efficient, and it turn, more fun.</div>
								</div><br>
								
							<div class="row">
							      <div class="col-md-4" align="center">
									   <div id='fb-root'></div>
									   <fb:like href='https:<%=serveraddress%>main/eventbee-customer-case-study-bishop-kelly-high-school' send='false' layout='box_count' width='60' show_faces='true'></fb:like>
									</div>
									<div class="col-md-4" align="center">
									  <a href="http://twitter.com/share" class="twitter-share-button" data-url="http://bit.ly/1pmy6OF" data-text="Bishop Kelly High School uses Eventbee to bring control and efficiency to their events" data-count="vertical" data-via="Eventbee" rel="external">Tweet</a>
									</div>
									<div class="col-md-4" align="center">
									  <g:plusone size="tall" href="http:<%=serveraddress%>main/eventbee-customer-case-study-bishop-kelly-high-school"></g:plusone>
									</div>
								</div>
					</div>
					
				
		</div>
			</div>
</div></div>			
		<div class="container" style="width:100% !important;background-color:#fff !important">
		<div class="container" style="padding-bottom: 50px; padding-top: 50px;">	
			<div class="row">
					<div class="col-md-12 medium_desc_grey" align="center" style="padding-left:0px;"><span><font face="arial">&ldquo;</font>Our Annual Trivia Night sells out in a matter of hours and is much easier to manage using Eventbee.<font face="arial">&rdquo;</font></span></div>
					<div class="col-md-12 normal_desc_grey_ans" align="center" style="padding-left:0px;">Margaret Jones, Bishop Kelley High School</div>
					

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