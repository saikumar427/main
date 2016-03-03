<%@taglib uri="/struts-tags" prefix="s"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head profile="http://gmpg.org/xfn/11">
<meta name="verify-v1" content="/2obWcBcvVqISVfmAFOvfTgJIfdxFfmMOe+TE8pbDJg=" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META Http-Equiv="Cache-Control" Content="no-cache">
<META Http-Equiv="Pragma" Content="no-cache">
<META Http-Equiv="Expires" Content="0">
<!--<link rel="stylesheet" type="text/css" href="/main/css/creditpopupcs.css" />
-->

<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<script src="/main/js/ajaxjson.js" language="JavaScript" type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="/main/js/popup.js"></script>
<script type="text/javascript" language="JavaScript" src="/main/js/paypalstates.js"></script>
<script>

  function activatePlaceholders(){
	var detect = navigator.userAgent.toLowerCase();
	if (detect.indexOf("")> 0) 
	return false;
	var inputs = document.getElementsByTagName("input");
	for (var i=0;i<inputs.length;i++) 
	{
	  if (inputs[i].getAttribute("type") == "text") 
	  {
		if (inputs[i].getAttribute("placeholder") && inputs[i].getAttribute("placeholder").length > 0) 
		{
			inputs[i].value = inputs[i].getAttribute("placeholder");
			inputs[i].onfocus = function() 
			{
				 if (this.value == this.getAttribute("placeholder")) 
				 {
					  this.value = "";
					  this.style.color ="black";
				 }
				 return false;
			}
			inputs[i].onblur = function() 
			{
				 if (this.value.length < 1) 
				 {
					  this.value = this.getAttribute("placeholder");
					  this.style.color ="darkgray";
				 }
			}
		}
	  }
	}
}



window.onload=function() {
activatePlaceholders();
}

var beecreditsamount='';
var creditvalue='';
function setAmount(bcamount,creditamount){
      beecreditsamount=bcamount;
      creditvalue=creditamount;
      //beecreditsamount=parseFloat(beecreditsamount).toFixed(2);
}

var beepurpose='';
var saddress='';
function onsucessload(){
     var amount='${amount}';
     var message='${message}'; 
   	 beepurpose='${purpose}';   	
   	 saddress=document.getElementById('serveraddress').value;
   	 document.getElementById('headermsg').innerHTML=message;
     if(beepurpose=='beecreditsquota')
   	 {
    	 document.getElementById('ccpaymenterror').style.display="none";
   	  document.getElementById('beecreditsamount').style.display='block';
   	  document.getElementById('creditcardscreen').style.display='none';
   	 }
}

function getCardScreen()
{	
 if(beecreditsamount==''){
     beecreditsamount='100.00';
     creditvalue='100.00';
 }    
 document.getElementById('beecreditsamount').style.display='none';
 document.getElementById('creditcardscreen').style.display='block';
 document.getElementById('headermsg').innerHTML='<span style="font-weight: normal;font-size: 12px;"><b>Amount: $'+beecreditsamount+'</b></span><br/>NOTE: CC processing is done by Eventbee. Eventbee appears on your credit card statement.';
 var seqid='${seqId}';
 var ptoken='${paytoken}';
 var arr=saddress.split('//');
 url="//"+arr[1]+"/main/myaccount/home!updateAmount?seqId="+seqid+"&paytoken="+ptoken+"&payamount="+creditvalue;
 /*  new Ajax.Request(url, {
       method: 'post',
       onSuccess: function(t){
       },onFailure:function(){alert("failure");}
    }); */
    $.ajax({
    	url : url,
    	type : 'post',
    	
    	success : function(t){   		
    	},
    	error : function(){
    		//alert("failure");
    	}
    });
    parent.resizeIframe();
}

function AjaxSubmit(action){
var ssladd=document.getElementById('sslserveraddress').value;
var seradd=document.getElementById('serveraddress').value;
var paytoken=document.getElementById("paytoken").value;
document.getElementById('ccpaymenterror').innerHTML="";
document.getElementById('ccpaymenterror').style.display="none";	
if(action=='cancel'){	
    if(beepurpose=='beecreditsquota'){   
    document.getElementById('beecreditsamount').style.display='block';
   	 document.getElementById('creditcardscreen').style.display='none';     	 
    }else
   // window.location=seradd+'/main/payments/closepopup.jsp';
    parent.closeEBpopup();
    return;
}

document.getElementById('submitblock').style.visibility = "hidden";
document.getElementById('processblock').style.visibility = "visible";

document.getElementById('processblock').innerHTML="Processing...<img src='/main/images/ajax-loader.gif'/>";

$.ajax({
	url : 'payments!processPayment',
	type : 'post',
	data : $('#ccpaymentscreen').serialize(),
	success : function(response){
		var data = response;
		//alert(data);
		if(data.indexOf("success")>-1){
	          //  window.location.reload();        
	          
	            var seqid='${seqId}';
	                     
	            
	          window.location.href=seradd+'/main/payments!onSuccess?paytoken='+paytoken+'&seqId='+seqid;
	            
	            }else if(data.indexOf("error_")>-1){
	             var splitdata=data.split('_');
	             var errorcode=splitdata[1];
	             //alert(splitdata);
	           parent.window.scrollTop();
	            document.getElementById('ccpaymenterror').style.display="block";
			   //$('ccpaymenterror').update('<font color="red">Card authentication failed. Invalid card or billing address.</font>');
	            //$('ccpaymenterror').update('<font color="red">Card authentication failed. Invalid card details (Card Number/expiration Date/CVV)</font>');
	           if(errorcode=='404' || errorcode=='505')
	        	  $('#ccpaymenterror').html('Card could not be processed at this time.');
	           else if(errorcode=='2001')
	             $('#ccpaymenterror').html('Card declined due to Insufficient Funds.');
	           else if(errorcode=='2002')
	             $('#ccpaymenterror').html('Card declined.');
	           else if(errorcode=='2004')
	             $('#ccpaymenterror').html('Card declined as Expired.');
	           else if(errorcode=='2051')
	             $('#ccpaymenterror').html('Card declined for this payment.');
	           else if(errorcode=='2059')
	             $('#ccpaymenterror').html('Address Verification Failed.'); 
	           else if(errorcode=='2010')
	             $('#ccpaymenterror').html('Card declined. Invalid CVV.');  
	           else if(errorcode=='81715')
	             $('#ccpaymenterror').html('Credit card number is invalid.');    
	          else if(errorcode=='2000')
	             $('#ccpaymenterror').html('Do Not Honor.');    
	         
	           else
	             $('#ccpaymenterror').html('<font color="red">Card declined for this payment. Make sure all details are valid.</font>');   
	           document.getElementById('submitblock').style.visibility="visible";
	           document.getElementById('processblock').style.visibility="hidden";
	           parent.resizeIframe();
	           }else{
	        
	         //$('#ccpaymenterror').scrollTo();
	         
			   $('#ccpaymenterror').html(response); 
			   document.getElementById('ccpaymenterror').style.display="block";		
	         document.getElementById('submitblock').style.visibility="visible";
	         document.getElementById('processblock').style.visibility="hidden";
	         parent.resizeIframe();
		     }
	},
	error : function (){
		alert("Please try again"); 
	}
});

}

</script>
<style>
.submitbtn-style {
    height:25px;
	margin: 5px;
    cursor: pointer;
	background: transparent url(/main/images/sprite.png);
	background-repeat:repeat-x;
	border:#808080 1px solid;
   	padding-left: 10px;
	padding-right: 10px;
	font: 12px/1.4 verdana,arial,helvetica,clean,sans-serif;
	valign: bottom;
}
</style>
</head>
<body>
<div id="beecreditsamount" style="display:none;height:px;">
<div class="col-xs-12 col-sm-9">        
	<div class="container">
            <div class="row">
            	<div class="col-xs-12 col-sm-9">
            		<input type="radio" name="radbtn" id="radbtn1" checked='checked' onclick="setAmount('100.00','100.00'); " class="100byebee"/>&nbsp;100 Bee Credits ($B) for $100
            	</div>
            	 </div>
            	<br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-9">
            		<input type="radio" name="radbtn" id="radbtn2" onclick="setAmount('475.00','475.00');" class="500byebee"/>&nbsp;500 Bee Credits ($B) for $475 (5% discount)
            	</div></div><br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-9">
            		<input type="radio" name="radbtn" id="radbtn3" onclick="setAmount('900.00','900.00');" class="1000byebee"/>&nbsp;1,000 Bee Credits ($B) for $900 (10% discount)
            	</div></div><br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-9">
            		<input type="radio" name="radbtn" id="radbtn4" onclick="setAmount('4,250.00','4250.00');" class="5000byebee"/>&nbsp;5,000 Bee Credits ($B) for $4,250 (15% discount)
            	</div></div><br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-9">
            		<input type="radio" name="radbtn" id="radbtn5" onclick="setAmount('8,000.00','8000.00');" class="100000byebee"/>&nbsp;10,000 Bee Credits ($B) for $8,000 (20% discount)
            	</div></div><br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-9">
            		<input type="radio" name="radbtn" id="radbtn6" onclick="setAmount('37,500.00','37500.00');" class="50000byebee"/>&nbsp;50,000 Bee Credits ($B) for $37,500 (25% discount)
            	</div></div><br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-9">
            		<input type="radio" name="radbtn" id="radbtn7" onclick="setAmount('70,000.00','70000.00');" class="100000byebee"/>&nbsp;100,000 Bee Credits ($B) for $70,000 (30% discount)
            	</div></div><br>
            	<div class="row">            	 
            	<div class="col-xs-offset-5">
            		<button class="btn btn-primary" id="continuebtn" onclick="getCardScreen();">Continue</button>
            	</div></div>
           
           </div>
</div>
</div>
<div id="creditcardscreen" style="height:px;">
<div id='ccpaymenterror' style="display:none" class="alert alert-danger"></div>

<div id='ccpaymentstatus'>


     
<div class="container">
            <div class="row">
                <div class="col-xs-12 col-sm-9">                    
                    <form class="form-horizontal" name="ccpaymentscreen" id="ccpaymentscreen" method="post" action="payments!processPayment" >              
                          <s:hidden name="seqId" id="seqId"/>
							<s:hidden name="state" id="bhstate"></s:hidden>
							<s:hidden name="sslserveraddress" id="sslserveraddress"></s:hidden>
							<s:hidden name="serveraddress" id="serveraddress"></s:hidden>
							<s:hidden name="paytoken" id="paytoken"></s:hidden>
							<s:hidden name="vendor"></s:hidden>
							<s:hidden name="month" id="month"></s:hidden>
							<s:hidden id="pstate"></s:hidden>
                         <div id="headermsg"></div>
                    	<br/>
                        <div class="form-group">
                            <div for="name" class="col-md-3 col-xs-3 control-div">Card Type*</div>
                            <div class="col-md-6 col-xs-7">
                               <s:select name="carddata.creditcardtype" id="cardtype"
									headerKey="1" headerValue="-- Select Card Type --" list="cardtype"  listKey="key" listValue="value" cssClass="form-control"/>
                            </div>
                            <div>
                            <img src="/main/images/amex.png" border="0" width="40px"> <img src="/main/images/mastercard.png" border="0" width="40px" > 
                             <img src="/main/images/discover.png" border="0" width="40px" > <img src="/main/images/visa.png" border="0" width="40px" >
                            
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <div for="email" class="col-md-3 col-xs-3 control-div">Card Number*</div>
                            <div class="col-md-6 col-xs-8">
                               <s:textfield name="carddata.cardnumber" id="cardnumber" theme="simple" size="25" cssClass="form-control"/>
                            </div> 
                            <div>
                            	 <span id="siteseal"><script type="text/javascript"
								src="https://seal.godaddy.com/getSeal?sealID=NVWu6PFkDsxAjkyLnVuI60pWgpqh4SRo3mlfoHSPjcEHprez8Nf5vp"></script></span>
                            </div>                         
                        </div>
                        
                       
                        
                        <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div">CVV Code*</div>                        
                            <div class="col-md-6 col-xs-6">
                              <s:textfield name="carddata.cvvcode" id="cvvcode" theme="simple" size="25" cssClass="form-control"/>
                            </div>                            
                        </div>
                        
                        <div class="form-group">
                          <div  class="col-md-3 col-xs-3 control-div">Expiration Month/Year</div>
                           
                            <div class="col-md-3 col-xs-4">                                       
                                    <s:select name="carddata.expiremonth" id='expiremonth'
											headerKey="0"  list="months"
											listKey="key" listValue="value"  cssClass="form-control"/>
						        </div>
						        <div class="col-md-3 col-xs-4">      
						              <s:select name="carddata.expireyear" id='expireyear'
										headerKey="0"  list="years"
										listKey="key" listValue="value" cssClass="form-control"/>													
               		   </div>				   
					 </div>  
					 
					 <div class="form-group">
					 <h1 style="padding-left: 15px;"><small>Credit Card Billing Address</small></h1>
					 </div>
					
					  <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div">Card Holder*</div>                        
                            <div class="col-md-3 col-xs-4">
                               <s:textfield name="carddata.firstname" id="firstname" theme="simple" size="25" placeholder="First Name" style="color:darkgray;" cssClass="form-control"/>
                            </div>
                            <div class="col-md-3 col-xs-4">
                               <s:textfield name="carddata.lastname" id="lastname" theme="simple" size="25" placeholder="Last Name" style="color:darkgray;" cssClass="form-control"/>
                            </div>
                        </div>
                        
                         <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div">Street*</div>                        
                            <div class="col-md-6 col-xs-8">
                               <s:textfield name="carddata.address" id="address" theme="simple" size="25" cssClass="form-control"/>
                            </div>
                        </div>
                        
                         <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div">Apt/Suite</div>                        
                            <div class="col-md-6 col-xs-8">
                               <s:textfield name="carddata.aptsuite" id="aptsuite" theme="simple" size="25" cssClass="form-control"/>
                            </div>
                        </div>
                        
                         <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div">City*</div>                        
                            <div class="col-md-6 col-xs-8">
                               <s:textfield name="carddata.city" id="city" theme="simple" size="25" cssClass="form-control"/>
                            </div>
                        </div>
                        
                         <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div">Country*</div>                        
                            <div class="col-md-6 col-xs-8">
                                <s:select name="carddata.country" id='phcountry' headerKey="1"
									headerValue="--  Select Country  --" list="countrylist"
									listKey="key" listValue="value" onchange="PCallStates();" cssClass="form-control"/>
                            </div>
                        </div>                
                        
                         <s:if test="%{vendor=='paypal'}"> 
                          <div class="form-group">                                     
			               <div for="message" class="col-md-3 col-xs-3 control-div">State*</div>
			                <div class="col-md-6 col-xs-8">
			                <select  name="carddata.state" id="state_p">
			                   <option value="1">--  Select State  --</option>
			                </select>
			                </div>				                       
                        </div>
                        </s:if>	 
                        
                         <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div">Zip/Postal Code*</div>                        
                            <div class="col-md-6 col-xs-8">
                               <s:textfield name="carddata.zip" id="zip" theme="simple" size="10" cssClass="form-control"/>
                            </div>
                        </div>       
                        
                        <div class="form-group control-div">
                        	<div class="col-md-12 text-center"><div id="processblock"></div></div>
                        </div>
                    <!--     <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div"></div>                        
                            <div class="col-md-6 col-xs-8">
                              <div id="processblock"></div>
                            </div>
                        </div>  -->                   
                 			
                 		
                 		<div class="form-group">
                 		<div for="message" class="col-md-8 col-xs-8 control-div"></div>
                 		<div class="col-md-4 col-xs-4">
                 		<div  id="submitblock">
                 				<input type="button" value="Submit" class="btn btn-primary"   onClick="AjaxSubmit('submit');"/>
                 				<input type="button" value="Cancel" class="btn btn-active"   onClick="AjaxSubmit('cancel');"/>
                 			
                 		</div>
                 		</div>
                 		</div>
                    </form>
                    
                 </div>
            </div>
        </div>


 </div>
</div> 
 <script>
  onsucessload();</script>
  </body>
 </html>
 <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/blue.css" />
<script src="/main/bootstrap/js/icheck.min.js"></script>
 <script>
 $(document).ready(function() {
	 $('input.100byebee').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 $('input.500byebee').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 $('input.1000byebee').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 $('input.5000byebee').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 $('input.10000byebee').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 $('input.50000byebee').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});	
	 $('input.100000byebee').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});

	 $('input.100byebee').on('ifChecked', function(event){
		 setAmount('100.00','100.00');
 });
	 $('input.500byebee').on('ifChecked', function(event){
		 setAmount('475.00','475.00');
   				 
 });
	 $('input.1000byebee').on('ifChecked', function(event){
		 setAmount('900.00','900.00');
 });
	 $('input.5000byebee').on('ifChecked', function(event){
		 setAmount('4,250.00','4250.00');
 });
	 $('input.10000byebee').on('ifChecked', function(event){
		 setAmount('8,000.00','8000.00');
 });
	 $('input.50000byebee').on('ifChecked', function(event){
		 setAmount('37,500.00','37500.00');
 });
	 $('input.100000byebee').on('ifChecked', function(event){
		 setAmount('70,000.00','70000.00');
 });
 });
 </script>