<link rel="stylesheet" href="/main/slider/dist/powerange.css" />
  <link rel="stylesheet" href="/main/slider/style.css" />
 
<style>
.fixed{
  top:0;
 position:fixed;
  display:none;
  border:none;
  z-index:999999999;
  background-color:#EEEEEE;
}

.section_header{
	font-size: 42px;
	font-weight:500;
	text-align: center;
	color: #999999;
}


.main_header_orange{
	font-size: 42px;
	font-weight:800;
	text-align: center;
	color: #F27D2F;
}

.caption_header_blue{
	font-size:32px;
	#font-weight: normal;
	text-align: center;
	color: #428BCA;
}

.caption_header_blue_faq{
	font-size: 32px;
	#font-weight: normal;
	#text-align: center;
	color:#428BCA;
}


.medium_desc_grey{
	color: #999999;
    font-size: 20px;
    text-align: center;
}

.normal_desc_grey{
	 color: #333333;
    font-size: 14px;
    text-align:center;
}

.normal_desc_grey_ans{
	 color: #333333;
    font-size: 14px;
}


.dropdown{
	background-color: white;
    border: 1px solid white;
    border-radius: 11px 11px 11px 11px;
    height: 182px;
    margin: 26px;
    width: 212px;
}

.subevent{
	border: 1px solid #F3F6FA;
	background-color: #F3F6FA;
    border-radius: 27px 27px 27px 27px;
    cursor: pointer;
    height: 45px;
    margin: 7px;
    padding: 5px;
    width: 315px;
    color:#ffffff;
}

.textbox{
    margin: 10px;
    padding-left: 30px;    
}

.input-field{
	background-color: #FFFFFF;
    border: medium none #FFFFFF;
    height: 30px;
    width: 50px;
}



.avgtooltip{
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


.range-max{
	font-size:20px;
}

.range-min{
	font-size:20px;
}

li{
	list-type:desc;
}


</style>

<div class="container"
	style="height: 250px; width: 100%; background-image: url('/main/bootstrap/images/EB_bee_pattern_1600x250.jpg');">
	<div class="container" style="padding-top: 55px;">
		<div class="row">			
			<div class="col-md-12 main_header_orange">
				<span style="color: white">FLAT FEE PRICING</span>
			</div>
		
		</div>
		<div class="row">			
			<div class="col-md-12 caption_header_blue">
				<span style="color: white;font-size:30px">Forget about percentage fees. Same
					fee regardless of ticket price!</span>
			</div>			
		</div>
	</div>
</div>

<div style="width: 100%;min-height: 400px"
	class="container">
	<div class="container" style="padding-bottom: 50px; padding-top: 50px;">
		<div class="row">
			<div class="col-md-4">
				<div id="basic">
				<div class="caption_header_blue">Basic</div>
				<div></div>
				</div>
				<div class="main_header_orange">$1</div>
				<div class="normal_desc_grey">/
					TICKET</div>
			</div>
			<div class="col-md-4">
			    <div id="pro">
				<div class="caption_header_blue">Pro</div>
				<div></div>
				</div>
				<div class="main_header_orange">$1.50</div>
				<div class="normal_desc_grey">/
					TICKET</div>
			</div>
			<div class="col-md-4">
				<div id="advanced">
				<div class="caption_header_blue">Advanced</div>
				<div class="normal_desc_grey" style="line-height:0.55">with
					seating</div>
				</div>
				<div class="main_header_orange">$2</div>
				<div class="normal_desc_grey">/
					TICKET</div>
			</div>
		</div>
		<br />
		<div class="row" style="padding-top:20px">
			<div class="col-md-4 medium_desc_grey">
					No fee on free tickets! 
			</div>
			<div class="col-md-4"></div>
			<div class="col-md-4 medium_desc_grey">
					<a id="compareFeature"
					style="cursor: pointer; text-decoration: none;color:#999999">+ COMPARE
					FEATURES</a>
			</div>
		</div>		
		<div style="display: none" id="compareFeatureData">
		<br>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-10">
					<div id="ticketingfeatures"></div>
				</div>
				<div class="col-md-1"></div>
			</div>
			<div class="row">
				<div class="col-md-12 normal_desc_grey_ans">
					Seating Digitization* - A one-time fee of $199.95 is applied for seating digitization. No additional fee if the same seating is used for future events!
					<div class="col-md-1"></div>
				</div>
			</div>
			<br>
		</div>		
	</div>
</div>

<div style="width: 100%; background-color: #FFFFFF;min-height:400px" class="container">
<div class="container" style="padding-bottom: 50px; padding-top: 50px;">
<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8 section_header" style="padding-bottom:20px">
				START SAVING
			</div>
			<div class="col-md-2"></div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="caption_header_blue" id="currentticket">Current
					Provider</div>
				<div></div>
				<div class="dropdown">
					<div class="subevent" id="eventbrite">				
	
						<span class="medium_desc_grey" style="padding: 12px;color:white">Eventbrite</span>
					</div>
					<div id="others" style="display: block">
						<div class="subevent" id="ticketleap">
							<span class="medium_desc_grey" style="padding: 12px;color:grey">Ticketleap</span>
						</div>
						<div class="subevent" id="ticketmaster">
							<span  class="medium_desc_grey" style="padding: 12px;color:grey">Ticketmaster</span>
						</div>
						<div class="subevent" id="other">
						<span class="medium_desc_grey" style="padding: 12px;color:grey;">Other&nbsp;&nbsp;
						<input type="text" size="2" class="input-field" id="pf" style="color:#000"
							>&nbsp;%&nbsp;&nbsp;+&nbsp;$&nbsp;<input type="text" size="2"
							class="input-field" style="color:#000" id="ff"></span>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="caption_header_blue" id="avgticketsprice">Ticket Price</div>
				<div></div>

				<div id="ticketsprice">
					<div class="row">
						<div class="col-md-4"></div>
						<div class="slider-wrapper vertical-wrapper col-md-4">
							<input type="text" class="js-vertical" />
						</div>
						<div class="avgtooltip"></div>
						<div class="col-md-4"></div>
					</div>
				</div>

			</div>
			<div class="col-md-4">
				<div class="caption_header_blue" id="ticketssold">Ticket Quantity</div>
				<div id="ticketscount">
					<div class="row">
						<div class="col-md-4"></div>
						<div class="slider-wrapper vertical-wrapper col-md-4">
							<input type="text" class="js-vertical-slider" />
						</div>
						<div class="avgtooltip"></div>
						<div class="col-md-4"></div>
					</div>
				</div>
			</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 medium_desc_grey">
					You save <span id="savedamount"></span> by switching to Eventbee
					Basic Ticketing
			</div>			
		</div>
		<br>
</div>
</div>
<div style="width: 100%;min-height: 400px;"
	class="container" id="payments">
	<div class="container" style="padding-bottom: 50px; padding-top: 50px;">
		<div class="row">
			<div class="col-md-12 section_header" style="padding-bottom:20px">
				CREDIT CARD PROCESSING
			</div>
		</div>
		<div style="min-height:300px;background-color:#ffffff;border-radius: 40px 40px 0 0;" >		
					<div class="row" style="padding-top:30px">
						<div class="col-md-12 caption_header_blue">							
								<span>Immediate access to
									funds with</span>							
						</div>
							</div>
							<div class="row"  style="padding-top:30px">
                    <div class="col-md-3"></div>

				<div class="col-md-6">
					<center>
						<span><a
							href="https://www.paypal.com/us/mrb/pal=X7MC9MT27JZ3L"
							target="_blank"><img src="/main/images/paypal_logo.jpg"></a></span> <span><a
							href="https://stripe.com/gb/help/pricing" target="_blank"><img
								src="/main/images/stripe_logo.jpg"></a></span> <br /> <span><a
							href="https://www.braintreepayments.com/faq" target="_blank"><img
								src="/main/images/braintree_logo.jpg"></a></span> <span><a
							href="http://www.authorize.net/solutions/merchantsolutions/pricing/"
							target="_blank"><img src="/main/images/authorizenet_logo.jpg"
								style="padding-top: 13px"></a></span>
					</center>
				</div>

				<div class="col-md-3"></div>
                    </div>					
					<div class="row"  style="padding-top:30px;padding-bottom:30px">
					
					<div class="col-md-12 medium_desc_grey">
						These providers charge around 2.9% + 30c per transaction
					</div>
				</div>
		</div>
		<br>
		<div style="min-height: 200px; background-color: #ffffff;border-radius: 0px 0px 40px 40px">
			<div class="row" style="padding-top:30px">
					<div class="col-md-12 caption_header_blue">
							<span>Simple credit card
								processing with</span>
					</div>
				</div>
				<div class="row" style="padding-top:20px">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<center><img height="50" src="/main/images/logo.png" alt="Eventbee"></center>
				</div>
				<div class="col-md-3"></div>
			</div>
				<div class="row" style="padding-bottom:30px">
				<br>
					<div class="col-md-12 medium_desc_grey">
						The transaction fee is 4.95% + 50c
					</div>
				</div>
		</div>
		<br>
	</div>
</div>



<div style="width: 100%; min-height: 400px; background-color: #FFFFFF"
	class="container">
	<div class="container" style="padding-bottom: 50px; padding-top: 50px;">
		<div class="row">
			<div class="col-md-12 section_header" style="padding-bottom: 20px">
				INTERNATIONAL EVENTS</div>
		</div>
		<div class="row">
			<div class="col-md-12 caption_header_blue">
				<span>Customize your event to your
					local language and currency!</span>
			</div>
		</div>
		<div class="row" style="padding-top:50px">
			<div class="col-md-12 medium_desc_grey">
				<a id="pricingFeature"
					style="cursor: pointer; text-decoration: none;color:#999999">+ INTERNATIONAL
					PRICING</a>
			</div>
		</div>
		<div id="internationalPricingData" style="display: none">
			<div class="row">
				<div class="col-md-12"  style="padding-top: 30px;">
					<div id="internationalPricing" ></div>
				</div>
			</div>
		</div>
	</div>
</div>

<div style="width: 100%;"  class="container">
	<div class="container" style="padding-bottom: 50px; padding-top: 50px;">
		<div class="row">
			<div class="col-md-12 section_header" style="padding-bottom:20px">
				FAQ
			</div>
		</div>		
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">We are hosting a non-profit event. Do you have special pricing?</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				Yes! We offer a 25% discount on our service fee to non-profit events. For complete details, visit <a href="http://www.eventbee.com/main/good" target="_blank">http://www.eventbee.com/main/good</a>
			</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">When do I receive ticket sale proceeds?</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				If you are using PayPal, Stripe, Braintree or Authorize.net for credit card processing, money instantly goes to your account.  <br> <br>
				For Eventbee processed payments, 90% of proceeds are mailed out via check the first week of the month for all closed events in the prior month, and the remaining 10% is mailed out in 60 days.
				The minimum balance required in your account to process check is $100.
			</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Can I collect fees from the attendee?</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
				You have complete control over which fees you collect from the attendee. Following are the three options:<br><br>
             </div>             
             <div class="col-md-10 normal_desc_grey_ans" style="padding-left:31px">
				<li>Collect the service fee and the credit card processing fee from your attendees</li>
				<li>Split the fees with your attendees</li>
				<li>Pay the service fee and the credit card processing fee yourself</li><br>
			 </div>	
			 <div class="col-md-12 normal_desc_grey_ans">
			 	You can also collect any additional fees from your attendees towards the event.
			 </div>
		<br>
	</div>
	<br>
	<div class="row">
			<div class="col-md-12 caption_header_blue_faq">Are there any hidden fees or contracts?</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12 normal_desc_grey_ans">
			Eventbee does not charge any hidden fees, and there are no contracts. We will not charge any setup fees or monthly fees. Enjoy ticketing with our simple flat fee pricing model.</div>
		</div>
</div>
</div>

<!-- --------------------------------------------------------------------- -->
		


<!-- padding opx -->
 <!-- <script	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
	<script src="/main/js/bootstrap.min.js"></script>
	  <script src="/main/slider/dist/powerange.js"></script>
	  
	<script>
	    var fixedfee;
        var pf;
        var ticketingdata = [{"name":"Hosted Event Page","b":true,"p":true,"a":true},{"name":"Registration Form","b":true,"p":true,"a":true},{"name":"Multiple Ticket Types","b":true,"p":true,"a":true},{"name":"Donations","b":true,"p":true,"a":true}, {"name":"Look & Feel Settings","b":true,"p":true,"a":true},{"name":"Event Page Themes","b":true,"p":true,"a":true},{"name":"Confirmation Email","b":true,"p":true,"a":true},{"name":"E-Tickets With QR Code","b":true,"p":true,"a":true},
                             {"name":"Event Page Content Control","b":true,"p":true,"a":true},{"name":"Attendee Reports","b":true,"p":true,"a":true},{"name":"Sales Reports","b":true,"p":true,"a":true}, {"name":"Export Reports (Excel, PDF)","b":true,"p":true,"a":true},{"name":"Attendee Search","b":true,"p":true,"a":true},
                             {"name":"Manual Registrations","b":true,"p":true,"a":true}, {"name":"Facebook Commenting","b":true,"p":true,"a":true},{"name":"Facebook/Twitter/Google+ Sharing","b":true,"p":true,"a":true},{"name":"Facebook RSVP Box","b":true,"p":true,"a":true},{"name":"Facebook Fan Page Ticketing","b":true,"p":true,"a":true},
                             {"name":"Network Ticket Selling","b":true,"p":true,"a":true}, {"name":"Custom Event Page URL","b":false,"p":true,"a":true}, {"name":"Ticket Display Options","b":false,"p":true,"a":true},{"name":"Custom Wording","b":false,"p":true,"a":true},  {"name":"Discount Codes","b":false,"p":true,"a":true},
                             {"name":"Event Page Password Protection","b":false,"p":true,"a":true}, {"name":"Tracking URLs & Reports","b":false,"p":true,"a":true},{"name":"Tickets Box Widget","b":false,"p":true,"a":true},{"name":"Private Ticketing","b":false,"p":true,"a":true},{"name":"Email Attendees","b":false,"p":false,"a":true},
                             {"name":"Attendee Badges","b":false,"p":false,"a":true},  {"name":"Sub Managers","b":false,"p":false,"a":true},  {"name":"Custom Registration Form","b":false,"p":false,"a":true},{"name":"Ticket Level Questions","b":false,"p":false,"a":true},{"name":"Event Page HTML & CSS Access","b":false,"p":false,"a":true},
                             {"name":"Custom Confirmation Email","b":false,"p":false,"a":true},{"name":"Custom Confirmation Page","b":false,"p":false,"a":true}, {"name":"Scan IDs","b":false,"p":false,"a":true},{"name":"Reserved Seating*","b":false,"p":false,"a":true}];
                             
        var data='';
        data+= '<table class="table table-bordered" style="border-top: none !important;padding-top:20px">'+
	 	'<thead><tr><th class="section_header" style="text-align:left;font-size:24px">FEATURES</th>'
	 	+'<th class="caption_header_blue" style="font-weight:normal;font-size:24px">Basic</th>'+
	 	'<th class="caption_header_blue" style="font-weight:normal;font-size:24px">Pro</th>'
	 	+'<th class="caption_header_blue" style="font-weight:normal;font-size:24px">Advanced</th>'
	 	+'</tr></thead><tbody>';
        $.each(ticketingdata,function(index,value){
        	data+= '<tr><td class="normal_desc_grey_ans">'+value.name+'</td><td style="text-align:center">'+((value.b)?'<img src="/main/images/Check_Mark_Symbol-011.png" width="20" height="20"/>':'<img src="/main/images/Check_Mark_Symbol-022.png" width="20" height="20"/>')+'</td><td style="text-align:center">'+((value.p)?'<img src="/main/images/Check_Mark_Symbol-011.png" width="20" height="20"/>':'<img src="/main/images/Check_Mark_Symbol-022.png" width="20" height="20"/>')+'</td><td style="text-align:center">'+ ((value.a)?'<img src="/main/images/Check_Mark_Symbol-011.png" width="20" height="20"/>':'<img src="/main/images/Check_Mark_Symbol-022.png" width="20" height="20"/>')+'</td></tr>';
        });
        $('#ticketingfeatures').append(data);
        var pricingData,countryimage='';
         $.ajax({
        	url : '/main/help/newinternationalpricing.jsp',
        	type : 'POST',
        	success : function(t){
        		var res = eval('('+t+')');
        		pricingData = res;
    			 var html='<table class="table table-bordered" style="border-top: none !important;padding-top:20px">'+
    			 	'<thead><tr><th  class="section_header" style="text-align:left;font-size:24px">CURRENCY</th>'
    			 	+'<th class="caption_header_blue" style="font-weight:normal;font-size:24px">Basic</th><th  class="caption_header_blue" style="font-weight:normal;font-size:24px">Pro</th>'
    			 	+'<th  class="caption_header_blue" style="font-weight:normal;font-size:24px">Advanced</th><th  class="caption_header_blue" style="font-weight:normal;font-size:24px">Payment Processors Supported</th>'
    			 	+'</tr></thead><tbody>';
    			 	
        		  $.each(pricingData,function(index,value){
        			  if(value.currencycode=='AUD'){
        				  countryimage='Australia.png';        				  
        			  }if(value.currencycode=='BRL'){
        				  countryimage='Brazil.png';
        			  }if(value.currencycode=='CAD'){
        				  countryimage='Canada.png';
        			  }if(value.currencycode=='CZK'){
        				  countryimage='Czech.png';
        			  }
        			  if(value.currencycode=='DKK'){
        				  countryimage='danish.png'; 
        			  }if(value.currencycode=='EUR'){
        				  countryimage='Euro.png';
        			  }if(value.currencycode=='HKD'){
        				  countryimage='HongKong.png';
        			  }
        			  if(value.currencycode=='HUF'){
        				  countryimage='Hungary.png';
        			  }
        			  if(value.currencycode=='ILS'){
        				  countryimage='Israel.png';
        			  }
        			  if(value.currencycode=='MYR'){
        				  countryimage='Malaysia.png';
        			  }
        			  if(value.currencycode=='MVR')
        				  countryimage='MALDIVES.GIF';
    				  
        			  if(value.currencycode=='MXN'){
        				  countryimage='Mexico_(reverse).png';
        			  }
        			  if(value.currencycode=='NZD'){
        				  countryimage='NewZealand.png';
        			  }
        			  if(value.currencycode=='NOK'){
        				  countryimage='Norway.png';
        			  }
        			  if(value.currencycode=='PHP'){
        				  countryimage='philippines.png';
        			  }
        			  if(value.currencycode=='PLN'){
        				  countryimage='Poland.png';
        			  }
        			  if(value.currencycode=='GBP'){
        				  countryimage='United Kingdom.png';
        			  }
        			  if(value.currencycode=='SGD'){
        				  countryimage='Singapore_flag.png';
        			  }
        			  if(value.currencycode=='SEK'){
        				  countryimage='Sweden-flag.png';
        			  }
        			  if(value.currencycode=='CHF'){
        				  countryimage='swiss.png';
        			  }
        			  if(value.currencycode=='TWD'){
        				  countryimage='taiwan.png';
        			  }
        			  if(value.currencycode=='THB'){
        				  countryimage='Thailand.png';
        			  }
        			  if(value.currencycode=='USD'){
        				  countryimage='United_States.png';
        			  } if(value.currencycode=='JPY'){
        				  countryimage='Yemen.png';
        			  }if(value.currencycode=='ZAR'){
        				  countryimage='SouthAfrica.png';
            			  }

        			  if(value.currencycode=='BGN'){
        				  countryimage='BULGARIA.GIF';
            			  }
        		  if(value.currencycode=='CLP'){
        				  countryimage='CHILE.GIF';
            			  }
        			  if(value.currencycode=='COP'){
        				  countryimage='COLOMBIA.GIF';
            			  }
        			  if(value.currencycode=='CRC'){
        				  countryimage='COSTARICA.GIF';
            			  }
        			  if(value.currencycode=='CZK'){
        				  countryimage='SouthAfrica.png';
            			  }
        			  if(value.currencycode=='INR'){
        				  countryimage='INDIA.GIF';
            			  }
        			  if(value.currencycode=='IDR'){
        				  countryimage='INDONESIA.GIF';
            			  }
        			  if(value.currencycode=='LVL'){
        				  countryimage='LATVIA.GIF';
            			  }
        			  if(value.currencycode=='LTL'){
        				  countryimage='LITHUANIA.GIF';
            			  } 
        			  if(value.currencycode=='PEN'){
        				  countryimage='PERU.GIF';
            			  } 
        			  if(value.currencycode=='SAR'){
        				  countryimage='SAUDI ARABIA.GIF';
            			  } 
        			  if(value.currencycode=='KRW'){
        				  countryimage='SOUTHKOREA.GIF';
            			  } 
        			  if(value.currencycode=='TRY'){
        				  countryimage='TURKEY.GIF';
            			  } 
        			  if(value.currencycode=='UAH'){
        				  countryimage='UKRAINE.GIF';
            			  } 

        			  if(value.currencycode=='AED'){
        				  countryimage='UAE.GIF';
            			  } 

        			  if(value.currencycode=='VND'){
        				  countryimage='VIETNAM.GIF';
            			  } 
        			  
		    			 	//html+= '<tr><td class="normal_desc_grey_ans"><img src="/main/images/flags/'+countryimage+'" height="25" width="27">&nbsp;'+value.currency+'&nbsp;'+value.currencysymbol+'</td><td class="normal_desc_grey_ans" style="text-align:center">'+value.currencysymbol+getRoundedValue(value.basicfee)+'</td><td  class="normal_desc_grey_ans" style="text-align:center">'+value.currencysymbol+getRoundedValue(value.profee)+'</td><td  class="normal_desc_grey_ans" style="text-align:center">'+value.currencysymbol+getRoundedValue(value.advancedfee)+'</td><td  class="normal_desc_grey_ans" style="text-align:center">'+value.paymentprocessor+'</td></tr>';
        			  html+= '<tr><td class="normal_desc_grey_ans"><img src="/main/images/flags/'+countryimage+'" height="25" width="27">&nbsp;'+value.currency+'</td><td class="normal_desc_grey_ans" style="text-align:center">'+value.currencysymbol+getRoundedValue(value.basicfee)+'</td><td  class="normal_desc_grey_ans" style="text-align:center">'+value.currencysymbol+getRoundedValue(value.profee)+'</td><td  class="normal_desc_grey_ans" style="text-align:center">'+value.currencysymbol+getRoundedValue(value.advancedfee)+'</td><td  class="normal_desc_grey_ans" style="text-align:center">'+value.paymentprocessor+'</td></tr>';        		 });  
        		  html+= '</tbody></table>';
        		  $('#internationalPricing').append(html);
        	}        	
        }); 
         
        
         $('#compareFeature').click(function(){        	  
        	 $('#compareFeatureData').slideToggle('fast',function(){
        		 if( $('#compareFeature').text().substr(0, 1)=='+')
             		$('#compareFeature').text('- COMPARE FEATURES');
             	else 
             		$('#compareFeature').text('+ COMPARE FEATURES');
        	 });
        	
         });
         
         $('#pricingFeature').click(function(){
        	 $('#internationalPricingData').slideToggle('fast',function(){
        		 if( $('#pricingFeature').text().substr(0, 1)=='+')
              		$('#pricingFeature').text('- INTERNATIONAL PRICING');
              	else 
              		$('#pricingFeature').text('+ INTERNATIONAL PRICING');
         	 });
         });
        
         
         function showEventsList(){
        	 $('#others').toggle();
         }
         
      
         var vert;
         var initVert1;
         var elem;
         var initVert2;
		 var domain;
         $(document).ready(function() {  		     
        	   var url=$(location).attr('href');
		       var arr=url.split('#');
               if(arr[1]=='payments')
		       $('html, body').animate({
               scrollTop: $('#payments').offset().top-90}, 700);
        	    var avg;var num;
				 domain='eventbrite';
        	    vert = document.querySelector('.js-vertical');
        	    initVert1 = new Powerange(vert, { min: 0, max: 1000, start: 100, vertical: true , callback : displayAvgTckt, dollar:true });
        	    avg=vert.value;
        	    var elem = document.querySelector('.js-vertical-slider');
        	    initVert2 = new Powerange(elem, {  min: 0,  max: 10000, start: 1000, vertical: true , callback : displayNumOfTckt });        	   
        	    num=elem.value;
        	    function displayAvgTckt() {
              	  avg=vert.value;
              	  saveamount(avg,num);   
              	$('#ticketsprice').find('.avgtooltip').css('bottom',parseInt($('#ticketsprice').find('.range-handle').css('bottom'),10)+18);
              	$('#ticketsprice').find('.avgtooltip').html('$'+avg);
              	}
               function displayNumOfTckt() {
             	  num=elem.value;
             	  saveamount(avg,num);
             	 $('#ticketscount').find('.avgtooltip').css('bottom',parseInt($('#ticketscount').find('.range-handle').css('bottom'),10)+18);
             	$('#ticketscount').find('.avgtooltip').html(num);
             	}
               
              
               $('#eventbrite').css('background-color','#F27A28');
               fixedfee='2.5';
         	    pf='0.99';
         	   saveamount(avg,num);

               $('#eventbrite').click(function(){
			   domain='eventbrite';
              	 $('#ticketmaster').css('background-color','#F3F6FA');
              	$('#ticketmaster').find('span').css('color','grey');
              	 $('#ticketleap').css('background-color','#F3F6FA');
              	$('#ticketleap').find('span').css('color','grey');
              	 $('#other').css('background-color','#F3F6FA');
              	$('#other').find('span').css('color','grey');
              	 $('#eventbrite').css('background-color','#F27A28');
              	$('#eventbrite').find('span').css('color','white');
                  fixedfee='2.5';
            	  pf='0.99';
            	  $(vert).next("span").remove();            	            	   
              	 initVert1 = new Powerange(vert, { min: 0, max: 1000, start: 100, vertical: true , callback : displayAvgTckt, dollar:true, disable : false});
              	 $(elem).next("span").remove();            	            	   
         	     initVert2 = new Powerange(elem, { min: 0, max: 10000, start: 1000, vertical: true , callback : displayNumOfTckt, disable : false});
                   saveamount(avg,num);
                   $('.avgtooltip').show();
               });
               
               $('#ticketmaster').click(function(){
			    domain='ticketmaster';
              	 $('#eventbrite').css('background-color','#F3F6FA');
              	$('#eventbrite').find('span').css('color','grey');
              	 $('#ticketleap').css('background-color','#F3F6FA');
              	$('#ticketleap').find('span').css('color','grey');
              	 $('#other').css('background-color','#F3F6FA');
              	$('#other').find('span').css('color','grey');
              	 $('#ticketmaster').css('background-color','#F27A28');
              	$('#ticketmaster').find('span').css('color','white');          
                   document.getElementById('savedamount').innerHTML='<font color="#F27A28">$ZILLION</font>'; 
              		$(vert).next("span").remove();            	            	   
            	    initVert1 = new Powerange(vert, { min: 0, max: 1000, start: 100, vertical: true , callback : displayAvgTckt, dollar:true, disable : true});
            	    $(elem).next("span").remove();            	            	   
            	    initVert2 = new Powerange(elem, { min: 0, max: 10000, start: 1000, vertical: true , callback : displayNumOfTckt, disable : true});
            	  document.getElementById('savedamount').innerHTML='<font color="#F27A28">$ZILLION</font>'; 
            	  $('.avgtooltip').hide();
               });
               
               $('#ticketleap').click(function(){
			   domain='ticketleap';
              	 $('#ticketmaster').css('background-color','#F3F6FA');
              	$('#ticketmaster').find('span').css('color','grey');
              	 $('#eventbrite').css('background-color','#F3F6FA');
              	$('#eventbrite').find('span').css('color','grey');
              	 $('#other').css('background-color','#F3F6FA');
              	$('#other').find('span').css('color','grey');
              	 $('#ticketleap').css('background-color','#F27A28');
              	$('#ticketleap').find('span').css('color','white');
                  fixedfee='1';
            	  pf='2';
            	  $(vert).next("span").remove();            	            	   
             	 initVert1 = new Powerange(vert, { min: 0, max: 1000, start: 100, vertical: true , callback : displayAvgTckt, dollar:true, disable : false});
             	 $(elem).next("span").remove();            	            	   
         	    initVert2 = new Powerange(elem, { min: 0, max: 10000, start: 1000, vertical: true , callback : displayNumOfTckt, disable : false});
                   saveamount(avg,num);
                   $('.avgtooltip').show();
               });
               
               $('#other').click(function(){
			   domain='other';
              	 $('#ticketmaster').css('background-color','#F3F6FA');
              	$('#ticketmaster').find('span').css('color','grey');
              	 $('#ticketleap').css('background-color','#F3F6FA');
              	$('#ticketleap').find('span').css('color','grey');
              	 $('#eventbrite').css('background-color','#F3F6FA');
              	$('#eventbrite').find('span').css('color','grey');
              	 $('#other').css('background-color','#F27A28');
              	$('#other').find('span').css('color','white');
              	  fixedfee=document.getElementById("ff").value;                  
				  pf=document.getElementById("pf").value;
                  $(vert).next("span").remove();            	            	   
              	 initVert1 = new Powerange(vert, { min: 0, max: 1000, start: 100, vertical: true , callback : displayAvgTckt, dollar:true, disable : false});
              	 $(elem).next("span").remove();            	            	   
         	    initVert2 = new Powerange(elem, { min: 0, max: 10000, start: 1000, vertical: true , callback : displayNumOfTckt, disable : false});
                   saveamount(avg,num);
                   $('.avgtooltip').show();
               });
               
            
               
              $('#pf , #ff').keypress(function(evt){  
            	     if(isNumberKey(evt)){						
	            	  	  setTimeout(function(){            		  
	            		  fixedfee=document.getElementById("pf").value;
	                      pf=document.getElementById("ff").value;            		  
	            		  saveamount(avg,num);},10);
            	     }
            	     return isNumberKey(evt);
               });
              
              function isNumberKey(evt)
              {
            	  var charCode = (evt.which) ? evt.which : evt.keyCode;
                  if (charCode != 46 && charCode>31  && (charCode<48 || charCode>57))
                     return false;

                 return true;
              }
       });  
         
		 		  function addCommas(nStr)
   {
       nStr += '';
       x = nStr.split('.');
       x1 = x[0];
       x2 = x.length > 1 ? '.' + x[1] : '';
       var rgx = /(\d+)(\d{3})/;
       while (rgx.test(x1)) {
           x1 = x1.replace(rgx, '$1' + ',' + '$2');
       }
       return x1 + x2;
   }
		 
         
         $('#avgticketsprice').height( $('#ticketssold').height());
         $('#currentticket').height($('#ticketssold').height());         
         $('#basic').css('padding-bottom','15px');
         $('#pro').css('padding-bottom','15px');
         $('#advanced').css('padding-bottom','15px');
         $('#basic').height($('#advanced').height());
         $('#pro').height($('#advanced').height());
		function saveamount(avg,num){
             var tktprice=avg;
            var tktcnt=num;           
            var savedamt;   
            var eventbeeamt;
            var amount=(tktcnt*tktprice*(pf/100))+(tktcnt*fixedfee);
			console.log("tktprice::"+tktprice);
			if(tktprice==0){
				eventbeeamt=0;
				if(domain=='eventbrite' || domain=='ticketleap'){
					amount=0;
				}		
			}
            else eventbeeamt=tktcnt*1;
           if(fixedfee=='' && pf=='' ){
        	   savedamt=0.00;
           }else{savedamt=amount-eventbeeamt;}
     	   document.getElementById('savedamount').innerHTML='<font color="#F27A28">$'+addCommas(savedamt.toFixed(0))+'</font>'; 
        }
        	  
         
         
        </script>
        
        <script>
   ;(function($) {
	   $.fn.fixMe = function() {
	      return this.each(function() {
	         var $this = $(this),
	            $t_fixed;
	         function init() {
	            $this.wrap('<div />');
	            $t_fixed = $this.clone();
	           $t_fixed.find("tbody").remove().end().addClass("fixed").insertBefore($this);
	            $('.fixed').css('top',$('.navbar-fixed-top').height()+'px');
	           
	            resizeFixed();
	         }
	         function resizeFixed() {
	        	 $t_fixed.find("th").each(function(index) {
		               $(this).css("width",$this.find("th").eq(index).outerWidth()+"px");
		            });
	         }
	         function scrollFixed() {
	            var offset = $(this).scrollTop(),
	            tableOffsetTop = $this.offset().top,
	            tableOffsetBottom = tableOffsetTop + $this.height() - $this.find("thead").height();
	           if(offset+$('.navbar-fixed-top').height() < tableOffsetTop || offset > tableOffsetBottom-$('.navbar-fixed-top').height()){
	            	
	               $t_fixed.hide();
	            }
	            else  if(offset+$('.navbar-fixed-top').height() >= tableOffsetTop && offset <= tableOffsetBottom){
	            	
	            	$t_fixed.show();
	            	
	         }
	         }
	         $(window).resize(resizeFixed);
	         $(window).scroll(scrollFixed);
	         init();
	      });
	   };
	})(jQuery);
  function getRoundedValue(value){
	  var temp = parseFloat(value+"").toFixed(2);
		var parts = temp.toString().split(".");
		parts[0] = parts[0].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		if(parseInt(parts[1])==0)
			return parts[0];
		else
			return parts.join(".");  
	  
  }
	</script>
