<style>
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

<div style="width: 100%; background-color: #F3F6FA" class="container">
	<div class="container" style="padding-bottom: 50px; padding-top: 50px;">
		<div class="row">
			<h1 class="col-md-12 section_header">Free Event Ticketing Software</h1>
		</div>
		<br/>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">Eventbee gives complete control and flexibility of how you manage your event. You can even make it free event ticketing software with the options that we provide to you:</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Understanding Event Ticketing Fees</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
			When you use online event ticketing software or service, there are two types of fees that you will be paying:<br><br>
				
			<i>Service Fees</i>
			<p>Our service fee is $1 per ticket sold for basic ticketing going up to $2 for reserved seating. Regardless of the ticket price, our service fee stays the same – there are no additional percentage fees like other companies. For complete details, visit    
			<a href="http://www.eventbee.com/main/pricing" target="_blank">http://www.eventbee.com/main/pricing</a></p>
						<i>Credit Card Processing Fees</i> 						
						<p>Depending on the payment processing provider you would like to use, you will pay a credit card processing fee to them. We support credit card processing from PayPal, Stripe, Braintree, and Authorize.net. These providers charge around 2.9% + 30c per transaction.</P>
																
					<p>You also have the option to use Eventbee credit card processing. However, our transaction fee is 4.95% + 50c. If you choose this option, there is no need to sign up for the other credit card processing services.</p>
			</div>
			</div>
			<br>
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Free Event Ticketing Software by Collecting Fees from the Attendees</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				<div calss="features-ticketing">
					<p>You have complete control over all the fees that you collect from attendees. </p>
					<i>Collecting Service Fee from the attendee:</i>
					<p>When creating tickets, select  &quot;Collect from Attendee&quot; option.</p>
					<div class="col-md-12"style="margin:20px 0px!important;"><img class="img-responsive" src="/main/images/en-us/Ticket_settings.jpg" alt="Free event ticketing software - Ticket settings"></div>
					<i>Collecting Credit Card Processing Fees from the attendee:</i>
					<p>Under Payment Settings, select your payment processing provider, and for credit card processing fees select &quot;Collect from Attendee &quot; option.</p>
					<div class="col-md-12"style="margin:20px 0px!important;"><img class="img-responsive" src="/main/images/en-us/Payment_settings.jpg" alt="Free event ticketing software - Payment settings"></div>
															
						<p>Please note – Eventbee does not charge any hidden fees, and there are no contracts. We will not charge any setup fees or monthly fees. With our flat fee model, you not only save on ticketing costs, but you can also make our ticketing system as free event ticketing software by collecting both the service fees and the credit card processing fees from your attendees.  </p>
				</div>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Free Event Ticketing Software + Collecting Additional Fees</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				<div calss="features-ticketing">
					<p>
You can also collect any additional fees from your attendees towards the event at ticket level and transaction level.  
</p>
					
					<p>To collect more fees at ticket level, set higher fees than Eventbee service fee in &quot;ticket settings&quot;. The additional fees collected goes to you.</p>
					<p>To collect more fees at transaction level, set higher fees than what you pay to your credit card processing provider in &quot;Payment Settings&quot;. </p>
						
				</div>
			</div>
		</div>
		</div>
</div>
<!-- <script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
<script src="/main/js/bootstrap.min.js"></script>
<script>
	$(function() {
		$('#emailus').click(
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
		$('#myModal')
				.on(
						'hide.bs.modal',
						function() {
							$('iframe#popup').attr("src", '');
							$('#myModal .modal-body')
									.html(
											'<iframe id="popup" src="" width="100%" style="height:440px" frameborder="0"></iframe>');
						});
	});
</script>