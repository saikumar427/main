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
			<h1 class="col-md-12 section_header">Sell Event Tickets with Paypal, Braintree, Authorize.net and Stripe Credit Card Processing</h1>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">Here at Eventbee, we believe in giving you complete control of how you process ticket sale payments and managing your money. We support a wide array of credit card processing companies so that you can use the one that works best for you. Moreover, you have immediate access to ticket sale proceeds which you can use for managing your event.</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">PayPal</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				PayPal is one of the most widely used payment processing systems in the world and because of that, millions of users and event managers have set up accounts with PayPal to sell event tickets. With PayPal, buyers don’t need a PayPal account, they only need to use a credit card to make ticket purchases. Plus, there are no setup charges or monthly fees and it’s easy to integrate it with your event on Eventbee.  <br><br>
				
				All you have to do is enter your PayPal email in your Payment Settings on Eventbee. When the buyer makes the ticket purchase, the money goes instantly to your PayPal account.
				<div class="col-md-12"style="margin:20px 0px!important;"><img class="img-responsive" src="/main/images/Paypal.png" alt="Sell event tickets with PayPal"></div>
				<p>PayPal charges 2.9% + 30c for payment processing, which you have the option to collect from the buyer using Eventbee.<br>
					</p>
					 
						<p>To sign up with PayPal, visit <a href="http://www.paypal.com" target="_blank"> http://www.paypal.com</a></p> 
						
						<p>Currencies we support for Paypal payments: USD, EUR, GBP, JPY, AUD, CAD, DKK, HKD, HUF, NZD, SGD, SEK, CHF, MXN, ILS, NOK, TWD, CZK, PHP, MYR, BRL, PLN, THB  </p>
			</div>
		</div>
		<br>


		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Stripe</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				<p>Stripe is a set of web and mobile payments that is built for developers and allows businesses to accept and manage their online payments. With Stripe, there are no monthly fees and you only get charged when you earn money from your events. There are also no refund costs, so when you refund a transaction, Stripe will also refund the entire fee. Best of all, there are no hidden fees, which means no setup fees, no monthly fees, and no card storage fees.</P>
				<p>To enable Stripe on your event, just select Stripe in Payment Settings, and enter your Stripe Merchant API Secret Key. When the buyer makes the ticket purchase, the money goes instantly to your Stripe account.</p>
				
				<div class="col-md-12"style="margin:20px 0px!important;"><img class="img-responsive" src="/main/images/Stripe.png" alt="Sell event tickets with Stripe"></div>
					<p>Stripe charges 2.9% + 30c for payment processing, which you have the option to collect from the buyer using Eventbee.<br>
					</p>
						<p>To sign up with Stripe, visit  <a href="http://www.stripe.com" target="_blank">http://www.stripe.com</a></p> 
						
						<p>Currencies we support for Stripe payments:  AUD, BRL, BGN, CAD, CLP, COP, CRC, CZK, DKK, EUR, HKD, HUF, INR, IDR, ILS, LVL, LTL, MVR, MYR, MXN, NZD, NOK, PEN, PHP, PLN, GBP, SAR, SGD, ZAR, SEK, CHF, TWD, THB, TRY, USD, UAH, AED, JPY   </p>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Braintree</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				<div calss="features-ticketing">
					<p>Braintree is a payment platform that makes it simple to accept payments in the app or on your website. Plus, Braintree can accept most credit and debit cards, and has more experience working with new businesses than any other payments provider.</p>
					
					<p>To enable Braintree on your event, just select Braintree in Payment Settings, and enter your Braintree Merchant ID, Public Key and Private key. When the buyer makes the ticket purchase, the money goes instantly to your Braintree account.</p>
					<div class="col-md-12"style="margin:20px 0px!important;"><img class="img-responsive" src="/main/images/Braintree.png" alt="Sell event tickets with Braintree"></div>
					<p>Braintree has no processing fees for your first $50,000. Afterwards, Braintree charges 2.9% + 30c, which you have the option to collect from the buyer using Eventbee. For more details, 
					visit <a href="https://www.braintreepayments.com/pricing" target="_blank">https://www.braintreepayments.com/pricing</a></p>
						<p>To sign up with Braintree, visit <a
						href="http://www.braintreepayments.com" target="_blank">http://www.braintreepayments.com</a></p> 
						
						<p>Currencies we support for Braintree payments:  AUD, BRL, CAD, CZK, DKK, EUR, HKD, HUF, ILS, MYR, MXN, NZD, NOK, PHP, PLN, GBP, SGD, ZAR, SEK, CHF, TWD, THB, USD, JPY  </p>
				</div>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Authorize.net</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				<div calss="features-ticketing">
					<p>Authorize.net gives you the ability to sell more tickets, whether it is online, on a mobile app, or at a store. They allow you to save time and money, working on a full-time clock to counter data fraud and store data securely. They also have a number of services that assist managers in managing businesses and protecting themselves from fraud.</p>
					
					<p>If you already have a merchant account or want the flexibility to choose your own provider, Authorize.net also offer a Payment Gateway Only plan. For more details, visit  <a
						href="https://www.authorize.net/solutions/merchantsolutions/pricing/?p=gwo  " target="_blank">https://www.authorize.net/solutions/merchantsolutions/pricing/?p=gwo</a></p>
						<p>To enable Authorize.net on your event, just select Authorize.net in Payment Settings, and enter your Authorize.net API Login ID, API Transaction Key. When the buyer makes the ticket purchase, the money goes instantly to your Authorize.net account.</p>
						<div class="col-md-12"style="margin:20px 0px!important;"><img class="img-responsive" src="/main/images/Authorize.Net.png" alt="Sell event tickets with Authroize.net"></div>
						<p>Authorize.net has a processing fee of 2.9% + 30c, which you have the option to collect from the buyer using Eventbee.
						</p>
						<p>To sign up with Authorize.net, visit <a
						href="http://www.authorize.net" target="_blank">http://www.authorize.net</a></p>
						<p>Currencies we support for Authorize.net payments: AUD, BRL, CAD, CZK, DKK, EUR, HKD, HUF, ILS, MYR, MXN, NZD, NOK, PHP, PLN, GBP, SGD, SEK, CHF, TWD, THB, USD, JPY </p>
						
				</div>
			</div>
		</div>
		<div class="row">
			<div class="features-ticketing col-md-12" style="float:left;"><i><b>Please note:</b></i></div>
		</div>
		
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				<div calss="features-ticketing">
					<p>If you don’t want to use any of these outside payment processing providers, you can still use Eventbee credit card processing.  To use Eventbee credit card processing on your event, all you have to do is select it in your Payment Settings.</p>
					
					<p>Eventbee credit card processing is supported only in United States, and the fee is 4.95% + 50c, which you have the option to collect from the buyer.</p>
						
				</div>
			</div>
		</div>
		



	</div>
</div>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
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