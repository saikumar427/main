<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/main/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="/main/bootstrap/css/main.css" rel="stylesheet">
<link href="/main/bootstrap/css/bootswatch.min.css" rel="stylesheet">
<script src="/main/bootstrap/js/jquery-1.10.2.min.js"></script>
</head>
<style>
.navbar-nav {
	margin: 18px -8px;
}

.signup {
	margin-top: -8px
}
.white {
	color: white;
}

.btn-lg {
	font-size: 38px;
	line-height: 1.33;
	border-radius: 6px;
}

.box>.icon {
	text-align: center;
	position: relative;
}

.box>.icon>.image {
	position: relative;
	z-index: 2;
	margin: auto;
	width: 88px;
	height: 88px;
	border: 7px solid white;
	line-height: 88px;
	border-radius: 50%;
	background: #63B76C;
	vertical-align: middle;
}

.box>.icon:hover>.image {
	border: 4px solid #428bd3;
}

.box>.icon>.image>i {
	font-size: 40px !important;
	color: #fff !important;
}

.box>.icon:hover>.image>i {
	color: white !important;
}

.box>.icon>.info {
	margin-top: -24px;
	background: rgba(0, 0, 0, 0.04);
	border: 1px solid #e0e0e0;
	padding: 15px 0 10px 0;
}

.box>.icon>.info>h3.title {
	color: #428bd3;
	font-size: 32px;
	font-weight: 500;
}

.box>.icon>.info>p {
	color: #666;
	line-height: 1.5em;
	margin: 20px;
}

.box>.icon:hover>.info>h3.title,.box>.icon:hover>.info>p,.box>.icon:hover>.info>.more>a
	{
	color: #428bd3;
}

.box>.icon>.info>.more a {
	color: #222;
	line-height: 12px;
	text-transform: uppercase;
	text-decoration: none;
}

.box>.icon:hover>.info>.more>a {
	color: #000;
	padding: 6px 8px;
	border-bottom: 4px solid black;
}

.box .space {
	height: 30px;
}

.shape {
	border-style: solid;
	border-width: 0 80px 60px 0;
	float: right;
	height: 0px;
	width: 0px;
	-ms-transform: rotate(360deg); /* IE 9 */
	-o-transform: rotate(360deg); /* Opera 10.5 */
	-webkit-transform: rotate(360deg); /* Safari and Chrome */
	transform: rotate(360deg);
}

.listing {
	background: #fff;
	border: 1px solid #ddd;
	box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
	margin: 15px 0;
	overflow: hidden;
}

.listing:hover {
	-webkit-transform: scale(1.1);
	-moz-transform: scale(1.1);
	-ms-transform: scale(1.1);
	-o-transform: scale(1.1);
	transform: rotate scale(1.1);
	-webkit-transition: all 0.4s ease-in-out;
	-moz-transition: all 0.4s ease-in-out;
	-o-transition: all 0.4s ease-in-out;
	transition: all 0.4s ease-in-out;
}

.shape {
	border-color: rgba(255, 255, 255, 0) #d9534f rgba(255, 255, 255, 0)
		rgba(255, 255, 255, 0);
}

.listing-radius {
	border-radius: 7px;
}

.listing-danger {
	border-color: #d9534f;
}

.listing-danger .shape {
	border-color: transparent #d9533f transparent transparent;
}

.listing-success {
	border-color: #5cb85c;
}

.listing-success .shape {
	border-color: transparent #5cb75c transparent transparent;
}

.listing-default {
	border-color: #999999;
}

.listing-default .shape {
	border-color: transparent #999999 transparent transparent;
}

.listing-primary {
	border-color: #428bca;
}

.listing-primary .shape {
	border-color: transparent #318bca transparent transparent;
}

.listing-info {
	border-color: #5bc0de;
}

.listing-info .shape {
	border-color: transparent #5bc0de transparent transparent;
}

.listing-warning {
	border-color: #f0ad4e;
}

.listing-warning .shape {
	border-color: transparent #f0ad4e transparent transparent;
}

.shape-text {
	color: #fff;
	font-size: 12px;
	font-weight: bold;
	position: relative;
	right: -34px;
	top: 2px;
	white-space: nowrap;
	-ms-transform: rotate(30deg); /* IE 9 */
	-o-transform: rotate(360deg); /* Opera 10.5 */
	-webkit-transform: rotate(30deg); /* Safari and Chrome */
	transform: rotate(30deg);
}

.listing-content {
	padding: 0 20px 10px;
}

.details {
	min-height: 355px;
	display: inline-block;
}

.blogicon {
	font-size: 217px;
	color: #5CB85C;
}

.height {
	min-height: 200px;
}

.icon {
	font-size: 47px;
	color: #5CB85C;
	cursor: pointer;
}

.iconbig {
	font-size: 77px;
	color: #5CB85C;
}

.table>tbody>tr>.emptyrow {
	border-top: none;
}

.table>thead>tr>.emptyrow {
	border-bottom: none;
}

.table>tbody>tr>.highrow {
	border-top: 3px solid;
}

.panel:hover {
	background-color: none;
}

.clearfix {
	clear: both;
}

.rowcolor {
	background-color: #CCCCCC;
}

.blogicon {
	font-size: 217px;
	color: #5CB85C;
}

.ratetext {
	font-size: 37px;
	text-decoration: underline;
	padding-bottom: 10px;
}

.votes {
	font-size: 47px;
	padding-right: 20px;
	color: #197BB5;
}

a.list-group-item {
	height: auto;
	min-height: 250px;
}

a.list-group-item:hover,a.list-group-item:focus {
	border-left: 10px solid #5CB85C;
	border-right: 10px solid #5CB85C;
}

a.list-group-item:hover,a.list-group-item:focus {
	background-color: #edf5fc !important;
}

a.list-group-item {
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

#create {
	cursor: pointer;
}

#promote {
	cursor: pointer;
}

#manage {
	cursor: pointer;
}

.blogicon2 {
	font-size: 128px;
}

.blogicon3 {
	font-size: 60px;
}

.btn-lg {
	font-size: 38px;
	line-height: 1.33;
	border-radius: 6px;
}

.box>.icon {
	text-align: center;
	position: relative;
}

.box>.icon>.image {
	position: relative;
	z-index: 2;
	margin: auto;
	width: 88px;
	height: 88px;
	border: 7px solid white;
	line-height: 88px;
	border-radius: 50%;
	background: #63B76C;
	vertical-align: middle;
}

.box .space {
	height: 30px;
}

.listing-content {
	padding: 0 20px 10px;
}

.blogicon {
	font-size: 217px;
	color: #5CB85C;
}

.icon {
	font-size: 47px;
	color: #5CB85C;
}

.iconbig {
	font-size: 77px;
	color: #5CB85C;
}

.fixed {
	top: 0;
	position: fixed;
	display: none;
	border: none;
	z-index: 999999999;
	background-color: #EEEEEE;
}

.section_header {
	font-size: 42px;
	font-weight: 500;
	text-align: center;
	color: #999999;
}

.main_header_orange {
	font-size: 42px;
	font-weight: 800;
	text-align: center;
	color: #F27D2F;
}

.caption_header_blue {
	font-size: 32px; #
	font-weight: normal;
	text-align: center;
	color: #428BCA;
}

.caption_header_blue_faq {
	font-size: 32px; #
	font-weight: normal; #
	text-align: center;
	color: #428BCA;
}

.medium_desc_grey {
	color: #999999;
	font-size: 20px;
	text-align: center;
}

.normal_desc_grey {
	color: #333333;
	font-size: 14px;
	text-align: center;
}

.normal_desc_grey_ans {
	color: #333333;
	font-size: 14px;
}

.dropdown {
	background-color: white;
	border: 1px solid white;
	border-radius: 11px 11px 11px 11px;
	height: 182px;
	margin: 26px;
	width: 212px;
}

.subevent {
	border: 1px solid #F3F6FA;
	background-color: #F3F6FA;
	border-radius: 27px 27px 27px 27px;
	cursor: pointer;
	height: 45px;
	margin: 7px;
	padding: 5px;
	width: 315px;
	color: #ffffff;
}

.textbox {
	margin: 10px;
	padding-left: 30px;
}

.input-field {
	background-color: #FFFFFF;
	border: medium none #FFFFFF;
	height: 30px;
	width: 50px;
}

.avgtooltip {
	background-color: #F27A28;
	bottom: 18px;
	box-shadow: 0 0 1px 1px #DDDDDD;
	color: #FFFFFF;
	left: 218px;
	padding: 17px 0 5px;
	position: absolute;
	text-align: center;
	width: 50px;
}

.range-max {
	font-size: 20px;
}

.range-min {
	font-size: 20px;
}

li {
	list-type: desc;
}

.serialnumber { #
	color: #c6c6c6;
	font-family: Arial, Helvetica, sans-serif; #
	font-size: 23px;
	font-weight: bold;
	line-height: 22px;
	padding-bottom: 15px;
	width: 40px;
}

ol.u {
	list-style-type: none;
}

a {
	outline: none !important;
}

.responsive-video {
	position: relative;
	padding-bottom: 200px;
	padding-top: 150px;
	overflow: hidden;
}

.responsive-video iframe,.responsive-video object,.responsive-video embed
	{
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
}
</style>
<body>
	<div style="width: 100%; background-color: #FFFFFF" class="container">
		<div class="container"
			style="padding-bottom: 50px; padding-top: 50px;">
			<div class="row">
				<div class="col-md-12 section_header" style="padding-bottom: 20px">
					FREQUENTLY ASKED QUESTIONS</div>
			</div>
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">1. Who is
					Eventbee?</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Founded in 2003,
					Eventbee provides ticketing services to over 40,000 event managers
					worldwide. We operate with a single mission: Maximizing ticket
					sales while providing the best technology for event managers around
					the world. We disrupted the industry by introducing our simple flat
					fee pricing model. Regardless of price or currency, our prices stay
					the same!</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Check out our
					brief introduction video!</div>
			</div>
			<br />
			<div class="row" style="margin-left: 5px !important;">
				<div class="col-md-7 normal_desc_grey_ans responsive-video">
					<iframe width="640" height="360"
						src="https://www.youtube.com/embed/EI1_xDgGvb0?rel=0"
						frameborder="0" allowfullscreen></iframe>
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">2. How does
					Eventbee work?</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">
					Eventbee is user friendly, and setting up an event is very simple.
					Just follow these three steps:<br>
					<br>
				</div>
				<div class="col-md-10 normal_desc_grey_ans">
					<ol class="pricing_desc_grey u"
						style="padding-left: 4px !important; margin-bottom: 0px !important;">
						<li><span class="">1)</span> Sign up for your free account at
							<a href="/main/user/signup" target="_blank">http://www.eventbee.com/main/user/signup</a></li>
						<li><span class="">2)</span> Create your event and ticket
							information</li>
						<li><span class="">3)</span> Start selling tickets on your
							own website, facebook fan page, and many other media tools!</li>
					</ol>
					<br>
				</div>
				<div class="col-md-12 normal_desc_grey_ans">
					When someone buys a ticket to your event, they will receive an
					email confirmation with a QR code and any information that is
					needed for easy check in. You can monitor ticket sales in real-time
					from your account, and generate attendee reports at any time. <br>
					<br>For a more in-depth description about how Eventbee works,
					please visit <a href="/main/how-it-works"
						target="_blank">http://www.eventbee.com/main/how-it-works</a>
				</div>
				<br>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">3. What event
					management features do you have?</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">We have used our
					10+ years of industry experience to implement great technology so
					that our users can easily manage their events from start to finish.
					Please visit our features page to learn more:
					<a href="/main/features" target="_blank">http://www.eventbee.com/main/features</a></div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">4. What event
					promotion features do you have?</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Eventbee has great
					social sharing capabilities integrated throughout our site:</div>
			</div>
			<br>
			<ul style="padding-left: 20px !important;">
				<li>Social media sharing tools on event page</li>
				<li>Facebook commenting integrated into event page and user
					profile</li>
				<li>Facebook RSVP list that directly displays attendees and
					drives traffic to your event page</li>
				<li>Network ticket selling: Tap your attendees to sell tickets
					for you</li>
			</ul>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Check out our Network Selling video here!</div>
			</div>
			<br>
			<div class="row" style="margin-left: 5px !important;">
				<div class="col-md-7 normal_desc_grey_ans responsive-video">
					<iframe width="640" height="360"
						src="https://www.youtube.com/embed/sz9gL5Iz5Gc?rel=0"
						frameborder="0" allowfullscreen></iframe>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">5. What are the
					fees using Eventbee service?</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">There are two fees
					that you will be paying with online ticketing:</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">
					<b>Flat Service Fee</b><br> For simple ticketing, our fee is one dollar
					per ticket sold, regardless of ticket price. No additional
					percentage fees like other companies. For complete details, please
					visit <a href="/main/pricing" target="_blank">http://www.eventbee.com/main/pricing</a>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">
					<b>Credit Card Processing Fees</b> <br>Depending on which processing
					service you decide to use, you will pay a credit card processing
					fee to them. We support Paypal, Stripe, Braintree and Authorize.net
					processing. These providers charge around 2.9% + 30c per
					transaction.
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">You also have the
					option of using Eventbee credit card processing, the fee is 4.95% +
					50c. With this option, there is no need to sign up for other credit
					card processing services.</div>
			</div>
			<br>
			<div class="row">

				<div class="col-md-12 normal_desc_grey_ans">You have complete
					control over which fees you collect from the attendee. Following
					are the three options:</div>
			</div>
			<br>
			<ul>
				<li>Collect the service fee and the credit card processing fee
					from your attendees</li>
				<li>Split the fees with your attendees</li>
				<li>Pay the service fee and the credit card processing fee
					yourself</li>
			</ul>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">You can also
					collect any additional fees from your attendees towards the event.
				</div>
			</div>
			<br>
			
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">6. When do I receive ticket sale proceeds?</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">If you are using PayPal, Stripe, Braintree or Authorize.net for credit card processing, money instantly goes to your account.</div>
					</div>
				<br>
            <div class="row">
				<div class="col-md-12 normal_desc_grey_ans">For Eventbee processed payments, 90% of proceeds are mailed out via check the first week of the month for all closed events in the prior month, and the remaining 10% is mailed out in 60 days. The minimum balance required in your account to process check is $100.</div>
					</div>
				<br>
			<div class="row">
				<div class="col-md-12 caption_header_blue_faq">7. How much do
					I save by switching to Eventbee?</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">Most customers
					save a huge amount of money by switching to Eventbee. The fact that
					we do not charge any percentage fees means that you save more money
					as your ticket prices go up. We encourage you to check our custom
					savings calculator at <a href="/main/pricing" target="_blank">http://www.eventbee.com/main/pricing</a> to see
					how much you will save by switching to Eventbee!</div>
					</div>
				<br>
				<div class="row">
					<div class="col-md-12 caption_header_blue_faq">8. We are a
						non-profit, do you have special pricing?</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						Yes! We offer a 25% discount on our Advanced and Pro ticketing for
						non-profit events. For complete details, visit <a
							href="/main/good" target="_blank">http://www.eventbee.com/main/good</a>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 caption_header_blue_faq">9. Can I see a
						sample event page?</div>
				</div>
				<br>
				<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">We have many types
					of events to show you. Here are a few:</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						<b>Simple</b> event: <a
							href="http://www.eventbee.com/event?eid=860191324"
							target="_blank">Click here</a> to view an event that uses basic
						ticketing features
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						<b>Recurring</b> event: <a
							href="http://www.eventbee.com/event?eid=810793324"
							target="_blank">Click here</a> to view the event ticketing for a
						show that is recurring everyday
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						<b>Venue seating</b> event: <a
							href="http://www.eventbee.com/event?eid=850399221"
							target="_blank">Click here</a> to view the digitized venue and
						selling tickets using reserved seating features.
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 caption_header_blue_faq">10. I am not
						in the USA, can I still use Eventbee?</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">Yes! We support 130 international currencies. We also allow you to
						customize every word in registration to support your local
						language.</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						Example event in <b>UK</b>: <a
							href="http://www.eventbee.com/event?eid=880294224"
							target="_blank">Windrush Aquathlon</a>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						Example event localized to <b>French language</b>: <a
							href="http://www.eventbee.com/event?eid=890697221"
							target="_blank">Gala Phenicia 2011 - CCGQ </a>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">
						Example event in <b>Hungary</b>: <a
							href="http://www.eventbee.com/event?eid=890396224"
							target="_blank">Culinary Walking Tour</a>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 caption_header_blue_faq">11. Do I have
						to sign a contract with Eventbee to get started?</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-12 normal_desc_grey_ans">No. We value your
						flexibility, and we are confident that once you begin using
						Eventbee, you won’t want to switch! We don’t lock our customers
						into contracts.</div>
						</div>
					<br>
					<div class="row">
						<div class="col-md-12 caption_header_blue_faq">12. I have
							more questions, who do I contact?</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-12 normal_desc_grey_ans">
							<a href="javascript:;" id="emailus">Click here</a> to contact our support, we
							have 24x7 email customer support.
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-12 normal_desc_grey_ans">
							<b>Like us, or follow us for special promotions and rewards!</b>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-12 normal_desc_grey_ans">
							Facebook: <a href="http://facebook.com/eventbeeinc"
								target="_blank">http://facebook.com/eventbeeinc</a>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-12 normal_desc_grey_ans">
							Twitter: <a href="http://twitter.com/eventbee" target="_blank">http://twitter.com/eventbee</a>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-12 normal_desc_grey_ans">
							YouTube: <a href="http://www.youtube.com/user/eventbee/videos"
								target="_blank">http://www.youtube.com/user/eventbee/videos</a>
						</div>
					</div>

				</div>
			</div>
</body>
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<script src="/main/bootstrap/js/bootswatch.js"></script>
<script>
$(function(){
$('#emailus').click(function() {
$('.modal-title').html('Contact Eventbee');
$('#myModal').on('show.bs.modal', function () {
$('iframe#popup').attr("src",'/main/user/homepagesupportemail.jsp');
$('iframe#popup').css("height","440px"); 
});
$('#myModal').modal('show');
});	
$('#myModal').on('hide.bs.modal', function () {
$('iframe#popup').attr("src",'');
$('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" style="height:440px" frameborder="0"></iframe>');
});
});	
</script>
</html>