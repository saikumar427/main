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
<%@page import="com.event.helpers.I18n"%>
<%@include file="../getresourcespath.jsp"%>
<script src="<%=resourceaddress%>/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
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
 document.getElementById('headermsg').innerHTML='<span style="font-weight: normal;font-size: 12px;"><b>'+props.cc_amount_lbl+': $'+beecreditsamount+'</b></span><br/>'+props.global_eventbee_cc_processing_note;
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

document.getElementById('processblock').innerHTML=props.global_processing_lbl+"<img src='/main/images/ajax-loader.gif'/>";

$.ajax({
	url : 'payments!processPayment',
	type : 'post',
	data : $('#ccpaymentscreen').serialize(),
	success : function(response){
		var data = response;
		//alert(data);
		if(data.indexOf("success")>-1){
	          //  window.location.reload();        
	         // alert('success');
	            var seqid='${seqId}';
	                     
	            
	          window.location.href=seradd+'/main/payments!onSuccess?paytoken='+paytoken+'&seqId='+seqid;
	            
	            }else if(data.indexOf("error_")>-1){
	            	var splitdata=data.split('_');
	             var errorcode=splitdata[1];
	        //   parent.window.scrollTop();
	            document.getElementById('ccpaymenterror').style.display="block";
			   //$('ccpaymenterror').update('<font color="red">Card authentication failed. Invalid card or billing address.</font>');
	            //$('ccpaymenterror').update('<font color="red">Card authentication failed. Invalid card details (Card Number/expiration Date/CVV)</font>');
	           if(errorcode=='404' || errorcode=='505')
	        	  $('#ccpaymenterror').html(props.cc_card_not_processed);
	           else if(errorcode=='2001')
	             $('#ccpaymenterror').html(props.cc_insufficient_funds);
	           else if(errorcode=='2002')
	             $('#ccpaymenterror').html(props.cc_card_declined_msg);
	           else if(errorcode=='2004')
	             $('#ccpaymenterror').html(props.cc_card_experied);
	           else if(errorcode=='2051')
	             $('#ccpaymenterror').html(props.cc_card_declined_payment);
	           else if(errorcode=='2059')
	             $('#ccpaymenterror').html(props.cc_address_failed); 
	           else if(errorcode=='2010')
	             $('#ccpaymenterror').html(props.cc_invalid_cc);  
	           else if(errorcode=='81715')
	             $('#ccpaymenterror').html(props.cc_ccno_invalid);    
	          else if(errorcode=='2000')
	             $('#ccpaymenterror').html(props.cc_donot_honor);    
	         
	           else{
	        	   $('#ccpaymenterror').html('<font color="red">'+props.cc_declined_invalid_detail_msg+'</font>');
	           }
	                
	           document.getElementById('submitblock').style.visibility="visible";
	           document.getElementById('processblock').style.visibility="hidden";
	           parent.resizeIframe();
	           }else{
	         //$('#ccpaymenterror').scrollTo();
	         /*  */
			   $('#ccpaymenterror').html(response); 
			   document.getElementById('ccpaymenterror').style.display="block";		
	         document.getElementById('submitblock').style.visibility="visible";
	         document.getElementById('processblock').style.visibility="hidden";
	         parent.resizeIframe();
		     }
	},
	error : function (){
		//alert("Please try again"); 
		bootbox.alert(props.cc_plz_try_again_msg);
	}
});

}

</script>

</head>
<body>
<div id="beecreditsamount" style="<s:if test="%{purpose=='beecreditsquota'}">display:block;</s:if><s:else>display:none;</s:else>">
<div class="col-xs-12 col-sm-9">        
	<div class="container">
            <div class="row">
            	<div class="col-xs-12 col-sm-12">
            		<label id="radbtn1"><input type="radio" name="radbtn" checked='checked' onclick="setAmount('100.00','100.00');" class="100byebee"/>&nbsp;100 <s:text name="cc.beecredits.lbl"/> ($B) <s:text name="cc.for.lbl"/> $100</label> 
            	</div>
            	 </div>
            	<br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-12">
            		<label id="radbtn2"><input type="radio" name="radbtn"  onclick="setAmount('475.00','475.00');" class="500byebee"/>&nbsp;500 <s:text name="cc.beecredits.lbl"/> ($B) <s:text name="cc.for.lbl"/> $475 (5% <s:text name="cc.beecredits.discount"/>)</label>
            	</div></div><br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-12">
            		<label id="radbtn3"><input type="radio" name="radbtn"  onclick="setAmount('900.00','900.00');" class="1000byebee"/>&nbsp;1,000 <s:text name="cc.beecredits.lbl"/> ($B) <s:text name="cc.for.lbl"/> $900 (10% <s:text name="cc.beecredits.discount"/>)</label>
            	</div></div><br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-12">
            		<label id="radbtn4"><input type="radio" name="radbtn"  onclick="setAmount('4,250.00','4250.00');" class="5000byebee"/>&nbsp;5,000 <s:text name="cc.beecredits.lbl"/> ($B) <s:text name="cc.for.lbl"/> $4,250 (15% <s:text name="cc.beecredits.discount"/>)</label>
            	</div></div><br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-12">
            		<label id="radbtn5"><input type="radio" name="radbtn"  onclick="setAmount('8,000.00','8000.00');" class="10000byebee"/>&nbsp;10,000 <s:text name="cc.beecredits.lbl"/> ($B) <s:text name="cc.for.lbl"/> $8,000 (20% <s:text name="cc.beecredits.discount"/>)</label>
            	</div></div><br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-12">
            		<label id="radbtn6"><input type="radio" name="radbtn"  onclick="setAmount('37,500.00','37500.00');" class="50000byebee"/>&nbsp;50,000 <s:text name="cc.beecredits.lbl"/> ($B) <s:text name="cc.for.lbl"/> $37,500 (25% <s:text name="cc.beecredits.discount"/>)</label>
            	</div></div><br>
            	 <div class="row">
            	<div class="col-xs-12 col-sm-12">
            		<label id="radbtn7"><input type="radio" name="radbtn"  onclick="setAmount('70,000.00','70000.00');" class="100000byebee"/>&nbsp;100,000 <s:text name="cc.beecredits.lbl"/> ($B) <s:text name="cc.for.lbl"/> $70,000 (30% <s:text name="cc.beecredits.discount"/>)</label>
            	</div></div><br>
            	<div class="row">            	 
            	<div class="col-xs-12 col-sm-12">
            		<div class="text-center"><button class="btn btn-primary" id="continuebtn" onclick="getCardScreen();"> <s:text name="global.continue.btn.lbl"/> </button></div>
            	</div></div>
           
           </div>
</div>
</div>
<div id="creditcardscreen" style="<s:if test="%{purpose!='beecreditsquota'}">display:block;</s:if><s:else>display:none;</s:else>">
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
                            <div for="name" class="col-md-3 col-xs-3 control-div"><s:text name="cc.popup.header.lbl"/><span class="required">*</span></div>
                            <div class="col-md-5 col-xs-5">
                               <s:select name="carddata.creditcardtype" id="cardtype"
									headerKey="1" headerValue="%{getText('cc.select.card.type')}"
									 list="cardtype"  listKey="key" listValue="value" cssClass="form-control"/>
                            </div>
                            <div class="col-xs-4 col-sm-4">
                            <img src="/main/images/amex.png" border="0" width="35px"> <img src="/main/images/mastercard.png" border="0" width="35px" > 
                             <img src="/main/images/discover.png" border="0" width="35px" > <img src="/main/images/visa.png" border="0" width="35px" >
                            
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <div for="email" class="col-md-3 col-xs-3 control-div"> <s:text name="cc.card.number.lbl"/>   <span class="required">*</span></div>
                            <div class="col-md-6 col-xs-6">
                               <s:textfield name="carddata.cardnumber" id="cardnumber" theme="simple" size="25" cssClass="form-control"/>
                            </div> 
                            <div class="col-xs-3 co-sm-3">
                            <span id="siteseal" style="margin-left: -15px;"><script type="text/javascript"
								src="https://seal.godaddy.com/getSeal?sealID=NVWu6PFkDsxAjkyLnVuI60pWgpqh4SRo3mlfoHSPjcEHprez8Nf5vp"></script></span>
                            </div>                       
                        </div>
                        
                       
                        
                        <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div"><s:text name="cc.cvvcode.lbl"/>   <span class="required">*</span></div>                        
                            <div class="col-md-6 col-xs-6">
                              <s:textfield name="carddata.cvvcode" id="cvvcode" theme="simple" size="25" cssClass="form-control"/>
                            </div>  <div class="col-xs-3 col-sm-3"></div>                          
                        </div>
                        
                        <div class="form-group">
                          <div  class="col-md-3 col-xs-3 control-div"><s:text name="cc.experation.year.lbl"/>  </div>
                           
                            <div class="col-md-3 col-xs-3">                                       
                                    <s:select name="carddata.expiremonth" id='expiremonth'
											headerKey="0"  list="months"
											listKey="key" listValue="value"  cssClass="form-control"/>
						        </div>
						        <div class="col-md-3 col-xs-3">      
						              <s:select name="carddata.expireyear" id='expireyear'
										headerKey="0"  list="years"
										listKey="key" listValue="value" cssClass="form-control"/>													
               		   </div>				   
					 </div>  
					 
					 <div class="form-group">
					 <h1 style="padding-left: 15px;"><small> <s:text name="cc.billing.address"/>  </small></h1>
					 </div>
					
					  <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div"> <s:text name="cc.holder.name"/>  <span class="required">*</span></div>                        
                            <div class="col-md-3 col-xs-4">
                               <s:textfield name="carddata.firstname" id="firstname" theme="simple" size="25" placeholder="%{getText('cc.first.name.ph')}"
 style="color:darkgray;" cssClass="form-control"/>
                            </div>
                            <div class="col-md-3 col-xs-4">
                               <s:textfield name="carddata.lastname" id="lastname" theme="simple" size="25" placeholder="%{getText('cc.last.name.ph')}"
                                style="color:darkgray;" cssClass="form-control"/>
                            </div><div class="col-xs-1 col-sm-1"></div>
                        </div>
                        
                         <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div"> <s:text name="cc.street.lbl"/>  <span class="required">*</span></div>                        
                            <div class="col-md-8 col-xs-8">
                               <s:textfield name="carddata.address" id="address" theme="simple" size="25" cssClass="form-control"/>
                            </div><div class="col-xs-1 col-sm-1"></div>
                        </div>
                        
                         <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div"><s:text name="cc.apt.suite"/>  </div>                        
                            <div class="col-md-8 col-xs-8">
                               <s:textfield name="carddata.aptsuite" id="aptsuite" theme="simple" size="25" cssClass="form-control"/>
                            </div><div class="col-xs-1 col-sm-1"></div>
                        </div>
                        
                         <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div"> <s:text name="cc.city.lbl"/>  <span class="required">*</span></div>                        
                            <div class="col-md-8 col-xs-8">
                               <s:textfield name="carddata.city" id="city" theme="simple" size="25" cssClass="form-control"/>
                            </div><div class="col-xs-1 col-sm-1"></div>
                        </div>
                        
                         <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div"> <s:text name="cc.country.lbl"/>  <span class="required">*</span></div>                        
                            <div class="col-md-8 col-xs-8">
                                <s:select name="carddata.country" id='phcountry' headerKey="1"
									headerValue="%{getText('cc.select.country')}"
 list="countrylist"
									listKey="key" listValue="value" onchange="PCallStates();" cssClass="form-control"/>
                            </div><div class="col-xs-1 col-sm-1"></div>
                        </div>                
                        
                         <s:if test="%{vendor=='paypal'}"> 
                          <div class="form-group">                                     
			               <div for="message" class="col-md-3 col-xs-3 control-div"> <s:text name="cc.state.lbl"/>  <span class="required">*</span></div>
			                <div class="col-md-8 col-xs-8">
			                <select  name="carddata.state" id="state_p">
			                   <option value="1"> <s:text name="cc.select.state"/>  </option>
			                </select>
			                </div>	<div class="col-xs-1 col-sm-1"></div>			                       
                        </div>
                        </s:if>	 
                        
                         <div class="form-group">
                        <div for="message" class="col-md-3 col-xs-3 control-div"> <s:text name="cc.Zip.Postal.Code"/> <span class="required">*</span></div>                        
                            <div class="col-md-8 col-xs-8">
                               <s:textfield name="carddata.zip" id="zip" theme="simple" size="10" cssClass="form-control"/>
                            </div><div class="col-xs-1 col-sm-1"></div>
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
                 		<div for="message" class="col-md-12 col-xs-12 control-div"></div>
                 		<div class="col-md-12 col-xs-12 col-sm-12 text-center">
                 		<div  id="submitblock">
                 				<input type="button" value="<s:text name="global.submit.btn.lbl"/>" class="btn btn-primary"   onClick="AjaxSubmit('submit');"/>
                 				<input type="button" value="<s:text name="global.cancel.btn.lbl"/>" class="btn btn-active"   onClick="AjaxSubmit('cancel');"/>
                 			
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
 <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />
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