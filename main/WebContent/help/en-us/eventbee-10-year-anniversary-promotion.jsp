<script src='http://platform.twitter.com/anywhere.js?id=X1eHtpybTHdDmEagOhWbgA&v=1.2' type='text/javascript'></script>
<script src='http://www.eventbee.com/home/js/jQuery.js' type='text/javascript'></script>
<script src='http://www.eventbee.com/home/js/ebeepopup.js' type='text/javascript'></script>
<link rel='stylesheet' type='text/css' href='http://www.eventbee.com/home/css/popupcss.css' />
<script type="text/javascript" src="http://eventbee.com/home/js/customfonts/cufon-yui.js"></script>
<!-- <script src="http://eventbee.com/home/js/customfonts/Droid_Sans_Mono_400.font.js" type="text/javascript"></script>
 <script>Cufon.replace('.headerfont');</script> -->
<script src="http://eventbee.com/home/js/customfonts/Myriad_Pro_400.font.js" type="text/javascript"></script>
<script>Cufon.replace('.roundedboxheader,.rectboxheader,.roundedboxheader h2,.rectboxheader h1,.rectboxheader h2,.logo h1');</script>
<script>
var tconnect=false;
var twitter;
var tweeting=false;
var twtcontent="I just applied for Eventbee's One Million Dollar Giveaway! Join the promotion at http://bit.ly/Zeo6sc #Eventbee #MillionBeeCredits";
var twtcontent1="Join Eventbee One Million Dollar Giveaway promotion! Enter my name to receive $100 credit http://bit.ly/Zeo6sc #Eventbee #MillionBeeCredits";
var fbimgsrc='http://www.eventbee.com/home/images/logo_big.jpg';
var fbimgsrc1='http://www.eventbee.com/home/images/logo_big.jpg';
var twtclick='';
 jQuery(document.body).ready(function () {
	  window.ebeepopup=new ebeepopupwindow("ebeecustpopup","ebeepopup");
	  ebeepopup.init();
	  jQuery('#fbconfshare,#fbconfshare1').click(function(){
			postToFeed(this.id);
			return false;
		}).attr("style","cursor:pointer;");
	  jQuery('#conftweet,#conftweet1').click(function(){
		twtclick=this.id;
	  	if(tconnect) publishToTwitter();
		else twitter.signIn();
	  });
	  document.getElementById("reflink").value='http://www.eventbee.com/main/eventbee-10-year-anniversary-promotion';
	  document.getElementById("reflink1").value='http://www.eventbee.com/main/eventbee-10-year-anniversary-promotion';
	});
twttr.anywhere.config({ callbackURL: 'http://www.eventbee.com/socialnetworking/twittercallback.jsp'});
twttr.anywhere(function (T) {
		twitter=T;
		T.bind("authComplete", function (e, user) {
			tconnect=true;
			if(!tweeting)
			publishToTwitter();
		});
 });
function publishToTwitter(){
	var defcont='';
	if(twtclick=='conftweet') defcont=twtcontent;
	if(twtclick=='conftweet1') defcont=twtcontent1;
	ebeepopup.setContent("<div id='tbox'></div>");
	ebeepopup.show();
	twttr.anywhere(function (T) {
		T("#tbox").tweetBox({
			label:"",
			defaultContent: defcont,
			onTweet:function(a,b){
			ebeepopup.hide();
				//alert("tweet successfull");
			}
		});
	});
}
function postToFeed(btnid) {
	var linkname=document.title;
	var url='http://www.eventbee.com/main/eventbee-10-year-anniversary-promotion';
	var pic='';
	if(btnid=='fbconfshare') pic=fbimgsrc;
	if(btnid=='fbconfshare1') pic=fbimgsrc1;
       FB.ui({
   		method: 'feed',
    	name: linkname,
    	link: url,
    	picture: pic,
    	caption:'',
    	description: jQuery('meta[name=description]').attr("content")
  		}, function(response) {}
		);
  }
</script>

<style>
#header {
z-index:9999;
}
.milliondollorpromotion ol {
	list-style-position:outside;
	list-style-type:decimal;
	padding-left:25px;
}
.milliondollorpromotion ol li {
    list-style-type:decimal;
	font-size: 12px;
    line-height: 25px;
	color: #333333;	
}
.social-share {
background: rgb(255,255,255); /* Old browsers */
background: -moz-linear-gradient(top,  rgba(255,255,255,1) 0%, rgba(237,237,237,1) 28%); /* FF3.6+ */
background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(255,255,255,1)), color-stop(28%,rgba(237,237,237,1))); /* Chrome,Safari4+ */
background: -webkit-linear-gradient(top,  rgba(255,255,255,1) 0%,rgba(237,237,237,1) 28%); /* Chrome10+,Safari5.1+ */
background: -o-linear-gradient(top,  rgba(255,255,255,1) 0%,rgba(237,237,237,1) 28%); /* Opera 11.10+ */
background: -ms-linear-gradient(top,  rgba(255,255,255,1) 0%,rgba(237,237,237,1) 28%); /* IE10+ */
background: linear-gradient(to bottom,  rgba(255,255,255,1) 0%,rgba(237,237,237,1) 28%); /* W3C */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff', endColorstr='#ededed',GradientType=0); /* IE6-9 */
cursor:pointer;
border:1px solid rgba(229,229,229,1);
border-radius:3px;
color: #666;
padding: 13px 35px 15px;
margin-right:15px;
margin-left:0px;
margin-top: 15px;
margin-bottom:15px;
}

.sharing-section
{
margin-top: 10px;
margin-bottom: 10px;
}

.sharing-section a span{
text-decoration:none;
color: #666;
cursor:pointer;
}
.fbsharebtn{
border: medium none;
background: none repeat scroll 0 0 transparent;
width: 65px;
cursor:pointer;
}
.serialnumber {
color: #DDDDDD;
font-family: Arial,Helvetica,sans-serif;
font-size: 23px;
font-weight: bold;
line-height: 22px;
width:40px;
padding-bottom:15px;
}
#container a {
 color: #0C50A1;
 }
</style>
<div id='fb-root'></div>
<script>var  fbavailable=false;window.fbAsyncInit=function(){fbavailable=true;FB.init({appId:'48654146645',status:true,cookie:true,xfbml:true})};(function(a){var b,c='facebook-jssdk';if(a.getElementById(c)){return}b=a.createElement('script');b.id=c;b.async=true;b.src='//connect.facebook.net/en_US/all.js';a.getElementsByTagName('head')[0].appendChild(b)})(document)</script>
<table  cellpadding="0" cellspacing="0" align="center"  valign="top" id="container" ><tr><td>
<div style="margin:0px;padding:0px;" class="milliondollorpromotion">

		<div style="background:#258DC8;font-size:45px; color:#FDF598;text-align:center; line-height:53px;padding:88px 0px 0px;margin-left:-30px;margin-top:-30px;position:relative;height:188px;width:101.70%;">
			Eventbee Celebrates 10 Years!<br/>
<span style="font-size:31px; color:#fff">One Million Dollar Giveaway in Service Fees</span>
			<div style="position:absolute;right:-71px;top:-52px;margin:0px;padding:0px;"><img src="/main/images/home/ribbon.png"></div>
		</div>
		</td></tr>
		<tr><td>
		<div style="padding-top:10px;">
		
			
			
			
			<table cellpadding="0" cellspacing="0" align="left" valign="top" width="100%">
			<tr><td>
			<p>In celebrating the 10 year anniversary, we are giving away ONE MILLION dollars in service fees to our customers.</p>
			</td></tr>
			<tr><td align="left">
			 <p><span class="rectboxheader">New customers</span></p>
			 <p>Follow these simple steps to claim your reward:</p>
			</td></tr>
			<tr><td width="100%" align="left">
			 <table cellpadding="0" cellspacing="0" align="left" valign="top" width="100%">
			 <tr><td align="left" class="serialnumber" valign="top">1 </td>
			 <td align="left" valign="top" style="padding-bottom:15px;">Sign up your account at <a href="http://www.eventbee.com" target="_blank">http://www.eventbee.com</a>. Fan us on <a href="http://facebook.com/eventbeeinc" target="_blank">Facebook</a>, and follow us on <a href="http://twitter.com/eventbee" target="_blank">Twitter</a>!

			 </td></tr>
			 <tr><td height="5px"></td></tr>
			 <tr>
			 <td class="serialnumber" valign="top" style="padding-bottom:15px;">2 </td>
			 <td align="left" valign="top" >Click on the "Get $100 Bee Credits" button below to apply for the promotion.<br />
				<div id='widget'> 
				<script type='text/javascript' language='JavaScript' src='http://www.volumebee.com/js/widget/eventregistration.js'></script>
				<iframe  id='_EbeeIFrame_itemwidget_920171423' name='_EbeeIFrame_itemwidget_920171423'  src='http://www.volumebee.com/reg_widgetHandler?vid=920171423&vtype=promotions&viewType=iframe;resizeIFrame=true&context=web'  height='0' width='718'></iframe>
				</div> 
			 </td>
			 </tr>
			 
			
			<tr><td height="5px"></td></tr>
			<tr>
			<td valign="top" class="serialnumber">3 </td>
			<td valign="top" align="left" style="padding-bottom:15px;">
			 Share this promotion with your friends and followers!<br />
			 <div class="sharing-section">
		
			  <a class="social-share" id="fbconfshare">
			  <span style="background-image: url(/main/images/fbsmall.jpg);background-repeat: no-repeat;padding-left: 19px;">Share</span> </a>
		
	
			  <a class="social-share" id="conftweet"> 
			  <span style="background-image: url(/main/images/tweet.png);background-repeat: no-repeat;padding-left: 19px;width:15px;height:15px;">Tweet</span> 
			  </a>
		      </div>
			  <div style="padding-top:10px;">
		       Your referral link 
			   <input type="text" id="reflink" name="reflink" readonly="readonly" value="" size="75"></div>
			 </td>
			  </tr>
			  </table>
			 </td></tr>
			  </table>
			
			
			
			<table cellpadding="0" cellspacing="0" align="left" valign="top" width="100%"><tr>
			<td align="left" valign="top" style="padding-bottom:15px;padding-left:5px;">
			<p><span class="rectboxheader">Existing customers </span></p>
			<p>We will reward $100 Bee Credits for each customer you refer to Eventbee, and the new customer will also receive $100 Bee Credits.</p>
			<p>Refer this promotion to your friends and followers!</p>
			<p><div class="sharing-section">
		
			<a class="social-share" id="fbconfshare1">
			 <span style="background-image: url(/main/images/fbsmall.jpg);background-repeat: no-repeat;padding-left: 19px;">Share</span> </a>
		
	
			<a class="social-share" id="conftweet1"> 
			<span style="background-image: url(/main/images/tweet.png);background-repeat: no-repeat;padding-left: 19px;width:15px;height:15px;">Tweet</span> 
			</a>
		</div><div style="padding-top:10px;">
		Your referral link <input type="text" id="reflink1" name="reflink1" readonly="readonly" value="" size="75"></div>
		</td></tr></table>
		
		
		 <table cellpadding="0" cellspacing="0" align="left" width="100%">
		 <tr><td height="15px"></td></tr>
		 <tr><td align="left" style="padding-left:0px;"><span class="rectboxheader" style="padding-bottom:15px;">
					Here are 10 interesting facts about Eventbee 
				</span></td></tr>
				<tr><td height="15px"></td></tr>
		 </table>
		
			
			<table cellpadding="0" cellspacing="0" align="left" width="100%">
			
			<tr><td class="serialnumber" valign="top" align="left">1 </td>
			 <td valign="top" align="left" style="padding-bottom:15px;">Eventbee started as an attendee networking site, way before the social networking phenomenon started.</td></tr>
			 <tr><td height="5px"></td></tr>
			 <tr><td class="serialnumber" valign="top" align="left">2 </td>
			 <td align="left" valign="top" style="padding-bottom:15px;">We are the first company to bring a flat fee pricing model to the events industry, charging only $1 per ticket sold.
			</td></tr>
			<tr><td height="5px"></td></tr>
			<tr><td class="serialnumber" valign="top" align="left">3 </td>
			 <td valign="top" align="left" style="padding-bottom:15px;">Eventbee supports credit card payment processing for over 20 different currencies.</td></tr>
			 <tr><td height="5px"></td></tr>
			<tr><td class="serialnumber" valign="top" align="left">4 </td>
			 <td valign="top" align="left" style="padding-bottom:15px;">Eventbee is used in 84 countries (and counting!) around the world.</td></tr>
			 <tr><td height="5px"></td></tr>
			<tr><td class="serialnumber" valign="top" align="left">5 </td>
			 <td valign="top" align="left" style="padding-bottom:15px;">We are the only ticketing company that gives a 25% discount in service fees to non-profits.
			</td></tr>
			<tr><td height="5px"></td></tr>
			<tr><td class="serialnumber" valign="top" align="left">6 </td>
			 <td valign="top" align="left" style="padding-bottom:15px;">Our customers have saved millions of dollars with our flat fee pricing model, compared to the competition.</td></tr>
			 <tr><td height="5px"></td></tr>
			<tr><td class="serialnumber" valign="top" align="left">7 </td>
			 <td valign="top" align="left" style="padding-bottom:15px;">We don't implement customers contracts since we don't believe in binding our clients.</td></tr>
			 <tr><td height="5px"></td></tr>
			<tr><td class="serialnumber" valign="top" align="left">8 </td>
			 <td valign="top" align="left" style="padding-bottom:15px;">We are the first online ticketing company to reward attendees for their event promotions.</td></tr>
			 <tr><td height="5px"></td></tr>
			<tr><td class="serialnumber" valign="top" align="left">9 </td>
			 <td valign="top" align="left" style="padding-bottom:15px;">Eventbee operates 24/7 in offices located in the US and India  to assist customers and improve services.</td></tr>
			 <tr><td height="5px"></td></tr>
			<tr><td class="serialnumber" valign="top" align="left">10 </td>
			 <td valign="top" align="left" style="padding-bottom:15px;">Volumebee (<a href="http://www.volumebee.com">http://www.volumebee.com</a>), the newest member of the Eventbee family, is announced as part of its 10 year anniversary.</td>
			</tr>
			</table>
			
		</div>

</td></tr></table>