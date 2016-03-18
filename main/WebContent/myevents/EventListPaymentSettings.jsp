<%@taglib uri="/struts-tags" prefix="s"%>
<head>
<style>
.drop-image{
  display:none;
}

.options{
margin-left: 1px !important;

}


</style>
<script src="/main/js/currencycodes.js"></script>

<%-- <script type="text/javascript" language="JavaScript" src="../js/eventmanage/paymentsettings.js"></script> --%>
<script>

var CurrencyCode={ "AUD":props.currency_aus_lbl,
		  "BRL":props.currency_brazil_lbl,
		  "BGN":props.currency_bulgrain_lbl,
		  "CAD":props.currency_canada_lbl,
		  "CLP":props.currency_chilea_lbl,
		  "COP":props.currency_colombia_lbl, 
		  "CRC":props.currency_costa_lbl, 
		  "CZK":props.currency_czeh_lbl, 
		  "DKK":props.currency_danish_lbl,
		  "EUR":props.currency_euro_lbl,
		  "HKD":props.currency_hongkong_lbl,
		  "HUF":props.currency_hungarian_lbl,
		  "INR":props.currency_india_lbl,
		  "IDR":props.currency_indonesia_lbl,
		  "ILS":props.currency_isreal_lbl,
		  "LVL":props.currency_latvian_lbl, 
		  "LTL":props.currency_lithun_lbl,
		  "MYR":props.currency_malaysia_lbl,
		  "MVR":props.currency_maldivian_lbl,
		  "MXN":props.currency_mexicon_lbl, 
		  "NZD":props.currency_newzea_lbl,
		  "NOK":props.currency_norwegian_lbl,
		  "PEN":props.currency_peruvian_lbl,
		  "PHP":props.currency_philipin_lbl,
		  "PLN":props.currency_polish_lbl,
		  "GBP":props.currency_pounds_lbl,
		  "SAR":props.currency_saudi_lbl,
		  "SGD":props.currency_singapore_lbl,
		  "ZAR":props.currency_africa_lbl,
		  "SEK":props.currency_swedish_lbl,
		  "CHF":props.currency_swiss_lbl,
		  "TWD":props.currency_taiwan_lbl,
		  "THB":props.currency_bath_lbl,
		  "TRY":props.currency_turkish_lbl,
		  "USD":props.currency_us_lbl,
		  "UAH":props.currency_ukrain_lbl,
		  "AED":props.currency_uae_lbl,
		  "JPY":props.currency_yen_lbl}; 

 var CurrencySupports={
		"USD":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE","EVENTBEE"],
		"EUR":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"GBP":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"JPY":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"AUD":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"CAD":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"DKK":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"HKD":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"HUF":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"NZD":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"SGD":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"SEK":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"CHF":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"MXN":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE","PAYU"],
		"ILS":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"NOK":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"TWD":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"CZK":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"PHP":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"MYR":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"BRL":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"PLN":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"THB":["PAYPAL","BRAINTREE","STRIPE","AUTHORIZE"],
		"ZAR":["BRAINTREE","STRIPE"],
		"AED":["STRIPE"],
		"BGN":["STRIPE"],
		"CLP":["STRIPE"],
		"COP":["STRIPE","PAYU"],
		"CRC":["STRIPE"],
		"IDR":["STRIPE"],
		"INR":["STRIPE"],
		"LTL":["STRIPE"],
		"LVL":["STRIPE"],
		"MVR":["STRIPE"],
		"PEN":["STRIPE"],
		"SAR":["STRIPE"],
		"TRY":["STRIPE"],
		"UAH":["STRIPE"]
}; 
</script>
</head>

<body>
<div class="white-box row">
<form id="paymentsettingsform" name="paymentsettingsform"
		action="PaymentSettings!Save" method="post">
		<s:hidden name="eid" id="eventid"></s:hidden>
		<s:hidden name="mgrid"></s:hidden>
		<s:hidden name="purpose"></s:hidden>
		<s:hidden name="specialfee" id="specialfee"></s:hidden>
		<s:hidden name="evtlevel" id="evtlevel"></s:hidden>
		<s:hidden name="paymentData.eventbee_payment_by"></s:hidden>
		<s:hidden id="taxval" value="%{paymentData.tax}"></s:hidden>
		<s:hidden id="ccfeeval" value="%{paymentData.ccfee}"></s:hidden>
		<s:hidden name="paymentData.paypal_other_ccfee"></s:hidden>
		<s:hidden name="paymentData.ebeecredits_other_ccfee"></s:hidden>
		<s:hidden name="paymentData.eventbee_other_ccfee"></s:hidden>
		<s:hidden name="paymentData.other_other_ccfee"></s:hidden>
		<s:hidden name="paymentData.braintreeArroved"></s:hidden>
		
		<s:hidden name="paymentData.paymentApprovalType" id="paymentapproval"></s:hidden>
		
		<s:hidden value="%{currencySymbol}" id="csymbol"/>
		<s:hidden name="paymentData.ccprocessingfee" id="cctypes"/>
		                             
		
		<!-- <div class="alert alert-info">
			<i class="fa fa-info-circle"></i>&nbsp; 
			You have immediate access to funds with PayPal, Stripe, Braintree and Authorize.net payment methods. Registrations processed through Eventbee credit card processing are mailed out via check the first week of the month for all the closed events in prior month.
		</div> -->
		<div id="paymentStatusMsg" style="display: none;"></div>
		<br />
		
		
		
		<div class="row">
			<div class="col-md-3 col-sm-6 col-xs-6" style="padding-top:5px;"><s:text name="ps.payment.currency.lbl"/><span class="required">*</span></div>
			<div class="col-md-3 col-sm-6 col-xs-6" style="padding: 0px 30px;">
				<s:set name="currency" value="paymentData.currencytype"></s:set>
				<s:hidden value="%{paymentData.currencytype}" id="currencyVal"></s:hidden>
				<select name="paymentData.currencytype" id="currency" onchange="changepaymentsettings();" class="form-control"> </select>
			</div>
			
		</div>
		<div style="clear:both;"></div>
		<hr>
		
		
		
		
			<div class="row">
				<div class="col-md-4 col-sm-6 col-xs-12">
					<s:text name="ps.payment.mode.lbl"/>
				</div>
			</div>
			<div class="bottom-gap" style="clear:both;"></div>
			
			<!-- credit card start -->
			<div class="row">
			<div class="col-md-5" id="cccheckblock">
			<s:set name="eventbeeckeck" value="paymentData.eventbee_payment_status"></s:set>
			
			
				<input type="checkbox" class="creditcardicheck" id="creditcardid" name="paymentData.eventbeechecked" 
			<s:if test="%{#eventbeeckeck == 'Enabled' || #eventbeeckeck == 'Need Approve'}">checked='checked'</s:if> />
			
			<span id="ccbold" style="cursor: pointer;" class="checkbox-space">   <s:text name="ps.listing.c.processing.lbl"/></span><span id="ccpindb"></span>    
			<i id="ccfonticon"  class="glyphicon glyphicon-menu-right drop-image"  style="cursor: pointer;"></i>
			</div>   <%-- <s:property value="%{#eventbeeckeck}"/> --%>
			</div>
			
			<div class="options background-options  xsm-font" style='display:none;'  id="ccdatablock">
			
			
			
				<s:set name="eventbeevendor"  value="paymentData.eventbeeVendor"></s:set>
				<div class="row bottom-gap">
				<div class="col-md-3">
						<select name="paymentData.eventbeeVendor" class="form-control" onchange="changecc();payment();" id="ccdropdown">
							<!-- <option value="" disabled selected>Please select a name...</option>   -->
							<!-- <option  value="">Select CC</option> --> 
							<option value="paypal_pro"   id="paypal_pro" <s:if test='%{#eventbeevendor=="paypal_pro"}'>selected='selected'</s:if>   >   <s:text name="ps.eventbee.lbl"/>  </option>
							<option value="authorize.net" id="authorize.net" <s:if test="%{#eventbeevendor=='authorize.net'}">selected='selected'</s:if>>   <s:text name="ps.authorize.net.lbl"/> </option>
							<option value="braintree_manager" id="braintree_manager" <s:if test="%{#eventbeevendor=='braintree_manager'}">selected='selected'</s:if>   > <s:text name="ps.braintree.lbl"/>   </option>
							<option value="stripe" id="stripe"  <s:if test="%{#eventbeevendor=='stripe'}">selected='selected'</s:if>   > <s:text name="ps.stripe.lbl"/>   </option>
						    <option value="payulatam" id="payulatam"  <s:if test="%{#eventbeevendor=='payulatam'}">selected='selected'</s:if>><s:text name="ps.payu.lbl"/><s:text name="ps.payulatam.lbl"/></option>
						</select>
				</div>   <%-- ptype:<s:property value="%{#eventbeevendor}"/>  --%>
			    </div>
			
			
			<!-- ccontent -->
			<div class="row" id="ccdescdiv">
			<div class="col-md-8" id="authorize" style="<s:if test="%{#eventbeevendor == 'authorize.net'}">display:block</s:if><s:else>display:none</s:else>; ">
				<div class="col-md-12" style="padding-top:10px;">
					
					<div class="col-md-6 col-sm-6 col-xs-12 sm-font">
						<s:text name="ps.authorize.net.api.login.id.lbl"/>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:textfield name="paymentData.apiLoginId" theme="simple" cssClass="form-control" id="apiloginid"></s:textfield><br>
					</div>
					
					
					<div class="col-md-6 col-sm-6 col-xs-12 sm-font">
						<s:text name="ps.api.transaction.key"/>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:textfield name="paymentData.apiTransactionId" cssClass="form-control" theme="simple" id="apitransactionid"></s:textfield>
					</div>
					
					
				</div>
			</div>
			
			<div class="col-md-8"  id="stripe" style="<s:if test="%{#eventbeevendor == 'stripe'}">display:block</s:if><s:else>display:none</s:else>; ">
				<div class="col-md-12"  style="padding-top:10px;">
					
					<div class="col-md-6 col-sm-6 col-xs-12 sm-font">
						<s:text name="ps.stripe.merchant.api.key"/>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:textfield name="paymentData.stripekey" theme="simple" cssClass="form-control" id="stripekey"></s:textfield><br>
					</div>
					<div class="col-md-12 xsm-font">
						<a onclick="getStripeInst();" style="cursor: pointer;"><s:text name="ps.stripe.help.msg1"/></a> <s:text name="ps.stripe.help.msg2"/>
					</div>
				</div>
			</div>
			
			
				<div class="col-md-8" id="payulatam"   style="display:none;">
				<div id="payuError1" style="display:none; font-size:12px; color:red; padding-left:30px;" class="col-md-12 col-sm-12"></div>
				<div id="payuError2" style="display:none; font-size:12px; color:red; padding-left:30px;" class="col-md-12 col-sm-12"></div>
			 <div class="col-md-12" style="padding-top:10px;">
					
					<div class="col-md-6 col-sm-6 col-xs-12 sm-font">
						<s:text name="ps.payu.api.key" />
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:textfield name="paymentData.payuApiKey"  theme="simple" cssClass="form-control" id="payyouapikey"></s:textfield><br>
					</div>
					
					
					<div class="col-md-6 col-sm-6 col-xs-12 sm-font">
						<s:text name="ps.payu.api.login"/>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:textfield name="paymentData.payuApiLogin"  cssClass="form-control" theme="simple" id="payyouapilogin"></s:textfield><br>
					</div>
					
					
					<div class="col-md-6 col-sm-6 col-xs-12 sm-font">
					<s:text name="ps.payu.api.accountid"/>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:textfield name="paymentData.payuAccountId"  theme="simple" cssClass="form-control" id="payyouaccountid"></s:textfield><br>
					</div>
					
					
					<div class="col-md-6 col-sm-6 col-xs-12 sm-font">
						<s:text name="ps.payu.api.marchantid"/>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:textfield name="paymentData.payuMarchantId"   cssClass="form-control" theme="simple" id="payyoumarchantid"></s:textfield>
					</div>
					
					
				</div>
			</div>
			
			
			<div class="col-md-8"  id="braintree" style="<s:if test="%{#eventbeevendor == 'braintree_manager'}">display:block</s:if><s:else>display:none</s:else>;   ">
				<div class="col-md-12" style="padding-top:10px;">
					
					<div class="col-md-6 col-sm-6 col-xs-12 sm-font">
						<s:text name="ps.braintree.merchant.id.lbl"/>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:textfield name="paymentData.braintreekey" theme="simple" cssClass="form-control" id="braintreekey"></s:textfield><br>
					</div> <%-- <s:property value="%{paymentData.braintreekey}"/>tt --%>
					
					
					<div class="col-md-6 col-sm-6 col-xs-12 sm-font">
						<s:text name="ps.public.key"/>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:textfield name="paymentData.brainPubKey" id="braintreepublickey" cssClass="form-control" theme="simple" size="35"></s:textfield><br>
					</div>
					
					
					<div class="col-md-6 col-sm-6 col-xs-12">
						<h4> <small class="titleColor"><s:text name="ps.private.key"/></small> </h4>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:textfield name="paymentData.brainPriKey" id="braintreeprivatekey" cssClass="form-control" theme="simple" size="35"></s:textfield><br>
					</div>
					
					
					<s:if test="%{paymentData.braintreeArroved!='Enabled'}">
						<div class="col-md-12 xsm-font"  style="clear: both;"><a onclick="brainTree();" style="cursor: pointer; "><s:text name="ps.braintree.help.msg1"/></a> <s:text name="ps.braintree.help.msg2"/></div>
					</s:if>
				</div>
			</div>	
			
			<div class="col-md-6"></div>
			
			</div>
			</div>
			<!-- paypal start -->
			
			
			<div class="row"   style="display:block;">
			<s:set name="paypalcheck" value="paymentData.paypal_merchant_status"></s:set>
			<div class="col-md-2" id="paypalcheckblock">
				
					 <input type="checkbox" class="paypalicheck" id="paypal" name="paymentData.paypalchecked" <s:if test="%{#paypalcheck == 'Enabled'}">checked='checked'</s:if> />
				<span id="paypalbold" style="cursor: pointer;" class="checkbox-space"><s:text name="ps.paypal.payments.lbl"/></span>
				<i id="paypalfonticon"  class="glyphicon glyphicon-menu-right drop-image"  style="cursor: pointer"></i>
			</div>
			</div>
			
			
			<%-- <s:property value="%{#paypalcheck}"/> --%>
			
			
			<div class="row options background-options  xsm-font" id='papaldatablock' style='<s:if test="%{#paypalcheck == 'Enabled'}">display:block;</s:if><s:else>display:none;</s:else>'>
			
			
					<div class="row">
					
					
							<div class="col-md-3 sm-font">
								<s:text name="ps.paypal.email.lbl"/>
							</div>
							<div class="col-md-4">
								<s:textfield name="paymentData.paypal_merchant_accmail" id="paypalemailid" theme="simple" cssClass="form-control"></s:textfield><br>
							</div>
							
							</div>
					
					
					
					<s:set name="paypalLanguage" value="paymentData.paypalSupportedLanguage"></s:set>
					<div class="row bottom-gap">
					<div class="col-md-12 col-sm-12 col-xs-12 sm-font" >
						<s:text name="ps.payment.language.lbl"/>
					</div>
					
					<div class="col-md-4">
						<select name="paymentData.paypalSupportedLanguage" class="form-control">
							<option value="EN" <s:if test="%{#paypalLanguage == 'EN'}">selected='selected'</s:if>> <s:text name="ps.cc_lang_en"/> </option>
							<option value="AU" <s:if test="%{#paypalLanguage == 'AU'}">selected='selected'</s:if>> <s:text name="ps.cc.lang_aus"/>  </option>
							<option value="CN" <s:if test="%{#paypalLanguage == 'CN'}">selected='selected'</s:if>> <s:text name="ps.cc.lang.chines"/> </option>
							<option value="FR" <s:if test="%{#paypalLanguage == 'FR'}">selected='selected'</s:if>> <s:text name="ps.cc.lang.fr"/>  </option>
							<option value="DE" <s:if test="%{#paypalLanguage == 'DE'}">selected='selected'</s:if>>  <s:text name="ps.cc.lang.ger"/> </option>
							<option value="IT" <s:if test="%{#paypalLanguage == 'IT'}">selected='selected'</s:if>> <s:text name="ps.cc.lang.ital"/>  </option>
							<option value="JP" <s:if test="%{#paypalLanguage == 'JP'}">selected='selected'</s:if>>  <s:text name="ps.cc.lang.jap"/>  </option>
							<option value="ES" <s:if test="%{#paypalLanguage == 'ES'}">selected='selected'</s:if>> <s:text name="ps.cc.lang.span"/> </option>
							<option value="GB" <s:if test="%{#paypalLanguage == 'GB'}">selected='selected'</s:if>> <s:text name="ps.cc.lang.uk"/> </option>
						</select>
					</div>   <%-- <div> <s:property value="%{#paypalLanguage}"/></div> --%>
					</div>
					
				<div class="row">	
					<div class="col-md-12 xsm-font">
						<s:text name="ps.paypal.help.msg1"/> 
						<a target="_blank" href="https://www.paypal.com/us/mrb/pal=X7MC9MT27JZ3L"><s:text name="ps.paypal.help.msg2"/></a>
					</div>
					</div>
				</div>
			
			
			
			
			<!-- paypal ends -->
			
			<!-- others started -->
			
			<div class="row">
			<s:set name="othercheck"  value="paymentData.other_payment_status"></s:set>
			<div class="col-md-2">
					
						
					<input type="checkbox" class="othericheck" id="otherid" onclick="call('otherdisableid','others');payment();"  name="paymentData.otherchecked" <s:if test="%{#othercheck=='Enabled'}">checked='checked'</s:if> />
						<span id="otherbold" style="cursor: pointer;" class="checkbox-space"><s:text name="ps.other.paymentmethods.lbl"/></span> 
					
						<i id="otherfonticon" class="glyphicon glyphicon-menu-right drop-image" style="cursor: pointer"></i>
					
			</div>
			</div> 
			
			
			
			<div class="row options background-options  xsm-font"  id="othersoptonboxid" style='<s:if test="%{#othercheck == 'Enabled'}">display:block;</s:if><s:else>display:none;</s:else>'>
			
			<div  class="col-md-5" ><br>
				<div  id='otherblock' style='display: block'>
					<s:textarea cssClass="taskcontent" theme="simple" name="paymentData.other_payment_desc" rows="4" cols="67"
								onfocus="this.value=(this.value==' ')?'':this.value" cssClass="form-control">
					</s:textarea><br>
					 <label > <input type="checkbox"  id="otherpaymentmode" name="payments"
							<s:if test="%{paymentData.paymentApprovalType == 'manual'}">checked='checked'</s:if> />
					<span id="paymentapprovalinfo" class="sm-font"><s:text name="ps.payment.approval.method"/></span>    </label>
					<%-- <s:property value="%{paymentData.paymentApprovalType}"/> --%>
				</div>
				<div class="col-md-7" ></div>
			</div>
			
			</div>
			
			<!-- others ended -->
		 
				
		<div id="style" style="display:none;">
		
		<div style="clear:both;"></div>
				<hr>
		
		<div class="form-group">
			<div class="row">
				
				<div class="col-md-12 col-sm-12 col-xs-12 bottom-gap" >
					<s:text name="ps.cc.fee.section.header"/>&nbsp;<i class="fa fa-info-circle info" style="cursor: pointer" id="creditcardinfo"></i>
				</div>
			
				<div class="col-md-3 col-sm-4 col-xs-12 bottom-gap" >
					<label class="checkbox-inline"> 
					<input type="checkbox" id="ccfeemode"   <s:if test="%{paymentData.ccprocessingfee == 'yes'}">checked='checked'</s:if>  />&nbsp;&nbsp;&nbsp;<s:text name="ps.colletct.from.attendee.lbl"/>
					</label>   
				</div>    
				 
				
				<div class="col-md-6 col-sm-4 col-xs-12" id="processingtax">
					<table>
						<tr>
							<td>
							<s:hidden name="paymentData.tax" id="tax"></s:hidden>
							
								<%-- <s:textfield style="width:60px;" name="paymentData.tax" id="tax"   theme="simple" cssClass="form-control" onfocus="getProcessingFeeAgreeDiv('tax');" onchange="getProcessingFeeAgreeDiv('tax');"></s:textfield> --%>
							</td>
							<td style="width: 65px;">&nbsp;%&nbsp;+&nbsp;<s:label name="currencySymbol" id="currencysymbol"></s:label></td>
							<td>
							
							<s:hidden name="paymentData.ccfee" id="ccfee"></s:hidden>
							
								<%-- <s:textfield  style="width:60px;" name="paymentData.ccfee" id="ccfee" theme="simple" cssClass="form-control" onfocus="getProcessingFeeAgreeDiv('ccfee');" onchange="getProcessingFeeAgreeDiv('ccfee');"></s:textfield> --%>
							</td>
						</tr>
					</table>
				</div>       
				
				
				</div>
				
				<div class="row">
				<div class="col-md-4"></div>
				
				<div class="col-md-6 col-sm-12" id="processingfeeagree"  style="display: none; margin-top: 10px;">
					<div class="panel panel-default">
					  <div class="panel-body">
						  <div class="text-center">
						  		<b><s:text name="ps.upgrade.header.lbl"/></b><br><br>
						  		
						  		<span id="currsymbl">${currencySymbol}</span>${specialfee} <s:text name="ps.upgradepopup.msg1.lbl"/><br><s:text name="ps.upgradepopup.msg2.lbl"/><br><br>
						  		<input type="button" class="submitbtn-style btn btn-primary btn-sm" value='<s:text name="up.upgrade.btn.lbl"/>' id="agreebtn" onclick="responseStatus('agreebtn');"/>
						  		<input type="button" class="submitbtn-style btn btn-active btn-sm" value='<s:text name="up.nothanks.btn.lbl"/>' id="cancelbtn" onclick="responseStatus('cancelbtn');"/>
						  </div>
					  </div>
					</div>
				</div>
				
				</div>
			
		</div>
		<!-- Credit Card Processing Fee/Tax end -->
		
		<div style="clear:both;"></div>
				<hr>
		
		<!-- Refund Policy/Terms & Conditions start -->
		
			<div class=" row">
				
				<div class="col-md-3 col-sm-4 col-xs-12" ><br>
					<s:text name="ps.refund.policy.lbl"/><span class="required">*</span>
				</div>
				
				<div class="col-md-7 col-sm-6 col-xs-12" style="padding-left:30px;">
					<s:textarea cssClass="taskcontent" name="paymentData.refundpolicy"
						rows="4" cols="67"
						onfocus="this.value=(this.value==' ')?'':this.value" theme="simple"
						cssClass="form-control">
					</s:textarea>
				</div>
				
				<div class="col-md-3"></div>
			</div>
		
		<!-- Refund Policy/Terms & Conditions end -->
		
		</div>
				
	</form>
	
	<div class="form-group">
			<s:set name="poweredbyEB" value="poweredbyEB"></s:set>
			<s:set name="eventbeevendor" value="eventbeevendor"></s:set>
			<%-- <s:property value="%{poweredbyEB}" />khk --%>
			
			<s:if test="%{poweredbyEB=='yes' || eventbeevendor == 'braintree_manager'}">
				<div class="col-md-12 text-center" style="margin-top:10px;">
					<button id="pSettSubmitBtn" class="btn btn-primary" onclick="updatePaymentSettings('');"><s:text name="%{getText('global.submit.btn.lbl')}" /></button>
				</div>
			</s:if>
			<s:else>
				<div class="col-md-12 text-center" style="margin-top:10px;">
					<button id="pSettSubmitBtn" class="btn btn-primary" onclick="updatePaymentSettings('continuepay');"><s:text name="%{getText('global.continue.btn.lbl')}" /></button>
				</div>
			</s:else>
		
			<div id="processing" class="col-md-12 text-center" style="margin-top:10px;display:none;">
				<button  class="btn  btn-primary"><s:text name="%{getText('global.processing.btn.lbl')}" /></button>
			</div>
		</div>
		
		
	
	
	
	</div>
</body>
<script>

var currencyvalue =['AED','BGN','CLP','COP','CRC','IDR','INR','LTL','LVL','MVR','PEN','SAR','TRY','UAH','USD','EUR','GBP','JPY','AUD','CAD','DKK','HKD','HUF','NZD','SGD','SEK','CHF','MXN','ILS','NOK','TWD','CZK','PHP','MYR','BRL','PLN','THB','ZAR'];
var countrycode = '${msg}';




$(document).ready(function()
{
	$('#ccpindb').html="";
	var selectedcc='${eventbeevendor}';
});
		
		
function updateSelectedCC()
{                       
	if(document.getElementById('creditcardid').checked){
		$('#ccpindb').html("");
		   var selectedcc=$("#ccdropdown").val();
		if("paypal_pro"==selectedcc)
			$('#ccpindb').html(": "+props.ps_eventbee_lbl);     
		else if("braintree_manager"==selectedcc)
			$('#ccpindb').html(": "+props.ps_braintree_lbl);
		else if("authorize.net"==selectedcc)
			$('#ccpindb').html(": "+props.ps_authorize_lbl);
		else if("stripe"==selectedcc)	
			$('#ccpindb').html(": "+props.ps_stripe_lbl);
		else if("payulatam"==selectedcc)	
			$('#ccpindb').html(": "+props.ps_payu_lbl);
		}
	else
		$('#ccpindb').html("");
				
}


var _purpose='PaymentSettings';
var ccdivs=['authorize','stripe','braintree','payulatam'];
var cccards={"braintree_manager":"BRAINTREE","stripe":"STRIPE","paypal_pro":"EVENTBEE","authorize.net":"AUTHORIZE"};
var ccdivObj={"braintree_manager":"braintree","stripe":"stripe","authorize.net":"authorize"};
var ccNameValues={"paypal_pro":"Eventbee","braintree_manager":"Braintree","authorize.net":"Authorize.Net","stripe":"Stripe","PAYYOU":"Payulatam"};

var j=0;

function updateFees(tax,ccfee){
	 $('#tax').val(tax);
	 $('#ccfee').val(ccfee);
}


function updatePaymentSettings(val){
	++j;
   if(j==1){
	   
	   /* var myspecialfee=document.getElementById('specialfee').value;	   
	   if(myspecialfee!='')
	   {
		   if(document.getElementById('processingfeeagree'))
			   $('html, body').animate({ scrollTop: $('#processingfeeagree').offset().top }, 'slow');
		  
	       var checkfee=getMaximumFee();
		   var csymbol=document.getElementById('csymbol').value;
		   if(checkfee=='4.95' && (Number(document.getElementById('tax').value)>4.95 || Number(document.getElementById('ccfee').value)>0.5)){
			   j=0;
			   //alert("CCProcessing fee should be 4.95%+"+csymbol+"0.5. This change needs upgrade to pro feature");
			   bootbox.confirm("CCProcessing fee should be 4.95%+"+csymbol+"0.5. This change needs upgrade to pro feature", function (result) {
				   if(result)
					   updateFees('4.95','0.5');
				   	   }); 
			   return;
		   }else if(checkfee=='2.9' && (Number(document.getElementById('tax').value)>2.9 || Number(document.getElementById('ccfee').value)>0.3)){
			   j=0;
			   bootbox.confirm("CCProcessing fee should be 2.9%+"+csymbol+"0.30. This change needs upgrade to pro feature", function (result) {	
				   if(result)
					   updateFees('2.9','0.30');
			   }); 	
		      return;
		   }else if(checkfee=='0' && (Number(document.getElementById('tax').value)>0 || Number(document.getElementById('ccfee').value)>0)){
			   j=0;
			   bootbox.confirm("CCProcessing fee should be 0%+"+csymbol+"0. This change needs upgrade to pro feature", function (result) {
				   if(result)
					   updateFees('0','0');
			   }); 	
		      return;
		   }   
	   } */
   
   //document.getElementById('pSettSubmitBtn').style.display="none";
   //document.getElementById('processing').style.display="block";
   var paypalemail=document.getElementById('paypalemailid').value;
   paypalemail=paypalemail.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   document.getElementById('paypalemailid').value=paypalemail;
   var authlogin=document.getElementById('apiloginid').value;
   document.getElementById('apiloginid').value=authlogin.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   var authkey=document.getElementById('apitransactionid').value;
   document.getElementById('apitransactionid').value=authkey.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   var braintreekey=document.getElementById('braintreekey').value;
   document.getElementById('braintreekey').value=braintreekey.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   var braintreepublickey=document.getElementById('braintreepublickey').value;
   document.getElementById('braintreepublickey').value=braintreepublickey.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   var braintreeprivatekey=document.getElementById('braintreeprivatekey').value;
   document.getElementById('braintreeprivatekey').value=braintreeprivatekey.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   var stripekey=document.getElementById('stripekey').value;
   document.getElementById('stripekey').value=stripekey.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');

   var payyouapikey=document.getElementById('payyouapikey').value;
   document.getElementById('payyouapikey').value=payyouapikey.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   var payyouapilogin=document.getElementById('payyouapilogin').value;
   document.getElementById('payyouapilogin').value=payyouapilogin.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   var payyouaccountid=document.getElementById('payyouaccountid').value;
   document.getElementById('payyouaccountid').value=payyouaccountid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   var payyoumarchantid=document.getElementById('payyoumarchantid').value;
   document.getElementById('payyoumarchantid').value=payyoumarchantid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
   
	loadingPopup();
   if($('#otherpaymentmode').is(':checked'))
	   $('#paymentapproval').val('manual');
   else
	   $('#paymentapproval').val('auto');

	$.ajax({
		url : 'PaymentSettings!Save',
		type: 'POST',
		data : $('#paymentsettingsform').serialize(),
		success : function(t){
			   if(!isValidActionMessage(t))
				   return;
			  	      var msg=t;				  	 
			  	        
			  	      if(msg.indexOf("You have no cards")>-1)
			  	      {  
				  	      hidePopup();
			  	    	var url='/main/payments!getPaymentScreen?processType=vault&amount=1&refId='+${mgrid}+'&purpose=invoice_hold_paymentsettings';
                        var dynatimestamp = new Date();
                        url += "&dynatimestamp="+dynatimestamp.getTime();
                        var mgrid='${mgrid}';
                        $.ajax({
                            url : url,
                            type : 'get',
                            success : function(t)
                                   {
                                      getcc(mgrid,'invoice_hold_paymentsettings');
                                   }
                              });	        
			             }
			  	      else if(msg.indexOf("Payment Settings updated")>-1) 
			             {
			  	    	//hidePopup();
			  	    	 window.location.href='ManageTickets?eid=${eid}';
			            	 /* if(val=='continuepay')
			            	       {window.location.href='ManageTickets?eid=${eid}';}
			            	 else{
			            	 $('#pSettSubmitBtn').show();
					  	     $('#processing').hide();
					  	     $('#paymentStatusMsg').css('height','50px');					  	  
			            	 $('#paymentStatusMsg').show();
			            	 $('#paymentStatusMsg').html(msg);
			            	 $('#paymentStatusMsg').removeClass('alert alert-danger');			            	 
			            	 $('#paymentStatusMsg').addClass('alert alert-success');
			            	 
			            	 updateSelectedCC();	
			            	 
			            	 $('html, body').animate({ scrollTop: $("#paymentStatusMsg").height()}, 1000);
			            	 } */
			             }else{
				             hidePopup();
			            	 $('#pSettSubmitBtn').show();
					  	     $('#processing').hide();					  	     
			            	 $('#paymentStatusMsg').show();
			            	 $('#paymentStatusMsg').html(msg);
			            	 $('#paymentStatusMsg').removeClass('alert alert-success');
			            	 $('#paymentStatusMsg').addClass('alert alert-danger');			            	 
			            	 $('html, body').animate({ scrollTop: $("#paymentStatusMsg").height()}, 1000);
				             }
			  	    			j=0;
			  	      }
			});   /* ajax close */
  }
}



function getProcessingFeeAgreeDiv(id){

	//alert(document.getElementById('ccfeemode').checked);
	
	// commmented beacause of event listing payment settings...
	
	/* if(document.getElementById('ccfeemode').checked)
		getdiv(id); */
}


function getdiv(id){
	var specialfee=document.getElementById('specialfee').value;
	var checkfee=getMaximumFee();
	var evttax='0',evtccfee='0';
	if(checkfee=='4.95'){
		evttax='4.95';
		evtccfee='0.5';
	}else if(checkfee=='2.9'){
		evttax='2.9';
		evtccfee='0.30';
	}else if(checkfee=='0'){
		evttax='0';
		evtccfee='0';
	}
	if(specialfee!=''){	
	   if((checkfee=='4.95' || checkfee=='2.9' || checkfee=='0') && (Number(document.getElementById('tax').value)>Number(evttax) || Number(document.getElementById('ccfee').value)>Number(evtccfee)))
	   {
       var currsymbl=document.getElementById('csymbol').value;
       document.getElementById('currsymbl').innerHTML=currsymbl;
	   document.getElementById('processingfeeagree').style.display='block';
       document.getElementById(id).setAttribute("oncopy","return false");
       document.getElementById(id).setAttribute("oncut","return false");
	   document.getElementById(id).setAttribute("onpaste","return false");
	   }else{document.getElementById('processingfeeagree').style.display='none';}
	}else{
		document.getElementById('processingfeeagree').style.display='none';	   
	}
}

function getMaximumFee(){
		
	var maxfee='0.0';
    if(document.getElementById('creditcardid').checked && $("#ccdropdown").val()=="paypal_pro")
		return maxfee='4.95';
	else{
		if(document.getElementById('paypal').checked)
			 return maxfee='2.9';
		else if(document.getElementById('creditcardid').checked && $("#ccdropdown").val()=="payulatam")
			return maxfee='4.95';
		else if(document.getElementById('creditcardid').checked && $("#ccdropdown").val()=="stripe")
			 return maxfee='2.9';
		else if(document.getElementById('creditcardid').checked && ($("#ccdropdown").val()=="authorize.net" || $("#ccdropdown").val()=="braintree_manager"))
			return maxfee='0';
	}
}


function responseStatus(id){
	var status=document.getElementById(id).value;
	if(id=='agreebtn'){
		var eid=document.getElementById("eventid").value;
		var evtlevel="200";
		var splfee=document.getElementById("specialfee").value;
		var url="PaymentSettings!agreeServiceFee?eid="+eid+"&evtlevel="+evtlevel+"&specialfee="+splfee;
		
		$.ajax({
		      url:url,
		      method:'POST',
		      success:function(msg){
		    	  if(msg.indexOf('success')>-1){
						j=0;
						document.getElementById('evtlevel').value=evtlevel;
						document.getElementById('specialfee').value='';
						document.getElementById('tax').removeAttribute('oncopy');
						document.getElementById('tax').removeAttribute('onpaste');
						document.getElementById('tax').removeAttribute('oncut');
						document.getElementById('ccfee').removeAttribute('oncopy');
						document.getElementById('ccfee').removeAttribute('onpaste');
						document.getElementById('ccfee').removeAttribute('oncut');
						}
		      }
		  		});		
		document.getElementById('processingfeeagree').style.display='none';
		
	}else if(id=='cancelbtn'){
		document.getElementById('processingfeeagree').style.display='none';
		document.getElementById('tax').removeAttribute('oncopy');
		document.getElementById('tax').removeAttribute('onpaste');
		document.getElementById('tax').removeAttribute('oncut');
		document.getElementById('ccfee').removeAttribute('oncopy');
		document.getElementById('ccfee').removeAttribute('onpaste');
		document.getElementById('ccfee').removeAttribute('oncut');
		var checkfee=getMaximumFee();
		if(checkfee=='4.95'){
			document.getElementById('tax').value='4.95';
			document.getElementById('ccfee').value='0.5';
		}else if(checkfee=='2.9'){
			document.getElementById('tax').value='2.9';
			document.getElementById('ccfee').value='0.30';
		}else if(checkfee=='0'){
			document.getElementById('tax').value='0';
			document.getElementById('ccfee').value='0';
		}		
		}	
}

function getcc(rfid,purp)
{
  var Msg=getPopMsg();
  var setkey= new callPaykey();
  setkey.setCallbackurl("/main/myaccount/home!insertManagerccData?refId="+rfid);
  setkey.setPurpose(purp);
  setkey.setPaymode("vault");
  setkey.setMessage(Msg);
  setkey.setAmount("1.00");
  setkey.setCurrency("USD");
  setkey.setMerchantid("");
  setkey.setLang("En");
  setkey.setTitle(props.myevts_popup_hdr);
  setkey.setSoftdis("");
  setkey.setRefid(rfid);
  setkey.setVendor("Braintree");
  setkey.setAppendDiv('maincontent');
  setkey.setInternalref('111');
  var paykey=setkey.getPaykey();
     if(paykey!="")
	 {
    	 setkey.ccpopup(paykey);
	 }
	 else
	 {
	    alert(props.myevents_cc_fail_error);
	 }
}

for (key in CurrencyCode) {
	   var daySelect = document.getElementById('currency');
	  var myOption = document.createElement("option");
	    myOption.text = CurrencyCode[key];
	    myOption.value = key;
	    if(document.getElementById('currencyVal').value==key)
	    	myOption.selected="selected";
	    daySelect.appendChild(myOption);
	}

//alert(countrycode);

if(currencyObj[countrycode]!=undefined && $.inArray(currencyObj[countrycode],currencyvalue)!=-1) {                          	
	document.getElementById('currency').value=currencyObj[countrycode];
}
else {                        	
	document.getElementById('currency').value='USD';                        	
}  

function showccdescdiv(cctypeselected, currency){
var currency=document.getElementById("currency").value;
var SupportedPayments=CurrencySupports[currency];
	var toshow;
	if("braintree_manager"==cctypeselected)
		toshow="BRAINTREE";
	if("stripe"==cctypeselected)
		toshow="STRIPE";
	if("paypal_pro"==cctypeselected)
		toshow="EVENTBEE";
	if("authorize.net"==cctypeselected)
		toshow="AUTHORIZE";
	if(SupportedPayments.indexOf(toshow)>-1){
		$('#ccdescdiv').show();
	}
}

var ccvendor='${eventbeevendor}';
changepaymentsettings(); 

function generateCCDropDown(SupportedPayments){

	if(SupportedPayments.indexOf("PAYPAL")>-1){
		$('#paypalcheckblock').show();
	}else{
            $('.paypalicheck').iCheck('uncheck');
			$('#paypalcheckblock').hide();
			$('#papaldatablock').hide();
		}

	if(SupportedPayments.indexOf("EVENTBEE")>-1)
		$('#ccdropdown').append('<option value="paypal_pro">'+props.ps_eventbee_lbl+'</option>');

	if(SupportedPayments.indexOf("BRAINTREE")>-1)
		$('#ccdropdown').append('<option value="braintree_manager">'+props.ps_braintree_lbl+'</option>');

	if(SupportedPayments.indexOf("AUTHORIZE")>-1)
		$('#ccdropdown').append('<option value="authorize.net">'+props.ps_authorize_lbl+'</option>');

	if(SupportedPayments.indexOf("STRIPE")>-1)
		$('#ccdropdown').append('<option value="stripe">'+props.ps_stripe_lbl+'</option>');

	if(SupportedPayments.indexOf("PAYU")>-1)
		$('#ccdropdown').append('<option value="payulatam">'+props.ps_payu_lbl+'</option>');
}



function showCorrespondingPayments(SupportedPayments){
	
 $.each(ccdivs,function(key,value){
	$('#'+value).hide();
});

	 
	$('#ccdescdiv').show();
 
	if($('#creditcardid').is(':checked'))
	$('#ccdatablock').show();
	else
	$('#ccdatablock').hide();

    var selectedcc=cccards[ccvendor];

        if(selectedcc!=undefined){

        	if(SupportedPayments.indexOf(selectedcc)>-1){
        		$('#ccdropdown').val(ccvendor);
        		$('#'+ccdivObj[ccvendor]).show();
        		$('#ccpindb').html(': '+ccNameValues[ccvendor]);
            	}else{
                	selectOtherCCVal(SupportedPayments);
                	}
            }else{
            	selectOtherCCVal(SupportedPayments);
                }
        
if(!$('#creditcardid').is(':checked'))
	$('#ccpindb').html(' ');
        
}



function selectOtherCCVal(SupportedPayments){
	
	if(SupportedPayments.indexOf("EVENTBEE")>-1){
		$('#ccdropdown').val("paypal_pro");
		$('#ccpindb').html(': Eventbee');
		return;
		}

	if(SupportedPayments.indexOf("BRAINTREE")>-1){
		$('#ccdropdown').val("braintree_manager");
		$('#braintree').show();
		$('#ccpindb').html(': Braintree');
		return;
		}

	if(SupportedPayments.indexOf("AUTHORIZE")>-1){
		$('#ccdropdown').val("authorize.net");
		$('#authorize').show();
		$('#ccpindb').html(': Authorize.Net');
		return;
		}

	if(SupportedPayments.indexOf("STRIPE")>-1){
		$('#ccdropdown').val("stripe");
		$('#stripe').show();
		$('#ccpindb').html(': Stripe');
		return;
		} 
	if(SupportedPayments.indexOf("PAYU")>-1){
		$('#ccdropdown').val("payulatam");
		$('#stripe').show();
		$('#ccpindb').html(': PayU');
		return;
		} 
}

          
          
	function changepaymentsettings() {
		var cctypeselected=$('#ccdropdown').val();
		var currency=document.getElementById("currency").value;
		var SupportedPayments=CurrencySupports[currency];
		$('#ccdescdiv').hide();
		$('#ccpindb').html("");
		 showccdescdiv(cctypeselected, currency); 
		$('#ccdropdown').html('');
		 generateCCDropDown(SupportedPayments);
		 showCorrespondingPayments(SupportedPayments);
	
	    var eventid=document.getElementById('eventid').value;
		  var url="PaymentSettings!getCurrencySymbolOfCurrencyCode?currencycode="+currency+"&eid="+eventid;
		  $.ajax({
			   	url : url,
			   	type : 'post',
			   	success : function(t){ 
			   		//var json=eval('('+t.responseText+')');
			   		var json=JSON.parse(t);
		            var currencysymbol=json.currencysymbol;
		            document.getElementById('currencysymbol').innerHTML=currencysymbol;
		            document.getElementById("csymbol").value=currencysymbol;
		          },onFailure:function(){
		          alert("failure");
			   	}
		   });
	}   /* close of function changepaymentsettings */
	
	function payment(){

		var taxval=document.getElementById('taxval').value;
		var ccfeeval=document.getElementById('ccfeeval').value;
		var currtaxval=document.getElementById('tax').value;
		var currccfeeval=document.getElementById('ccfee').value;
		var paypal=document.getElementById('paypal').checked;
		var ebee=document.getElementById('creditcardid').checked; 


		var other=document.getElementById('otherid').checked; 
		
		if(document.getElementById('creditcardid').checked==false){
			
			  if(paypal==true){
			   if(Number(taxval)<2.9 && Number(currtaxval)<2.9){
				document.getElementById('tax').value=2.9;
				document.getElementById('ccfee').value=0.3+'0';
			   }}else{
				  document.getElementById('tax').value=0;
				  document.getElementById('ccfee').value=0; 
			   }
			}
		if(ebee){
			
			
		 if($("#ccdropdown").val()=='authorize.net')
		     $('#authorize').show();

		if($("#ccdropdown").val()=='braintree_manager')
			 $('#braintree').show(); 


		
      	 if($("#ccdropdown").val()=='paypal_pro'){

			if(Number(taxval)<4.95 && Number(currtaxval)<4.95){
			document.getElementById('tax').value=4.95;
			document.getElementById('ccfee').value=0.5;
			}
			}

			
			else if($("#ccdropdown").val()=='stripe')
			{
				$('#stripe').show(); 
					if(Number(taxval)<2.9 && Number(currtaxval)<2.9){
					document.getElementById('tax').value=2.9;
					document.getElementById('ccfee').value=0.3+'0';
					}
			}else{
				 if(paypal==true){
					 if(Number(taxval)<2.9 && Number(currtaxval)<2.9){
					document.getElementById('tax').value=2.9;
					document.getElementById('ccfee').value=0.3+'0';
				   }	    
				 }else{
					 document.getElementById('tax').value=taxval;
					document.getElementById('ccfee').value=ccfeeval;
				}	    
			}
		    return ;
		}else if(paypal==true){
		    if(Number(taxval)<2.9 && Number(currtaxval)<2.9){
			document.getElementById('tax').value=2.9;
			document.getElementById('ccfee').value=0.3+'0';
			}
		    return;  
		}else{
			document.getElementById('tax').value=taxval;
			document.getElementById('ccfee').value=ccfeeval;
			return; 
		}
		}   /* payment() close */
	
/* <s:set name="msgKey" var="msgKey" value="%{#parameters.msgKey}"/>
<s:if test="%{#msgKey[0] == 'welcomeToPaymentSettings'}">
getStatusMsg();
</s:if>

function getStatusMsg(){
	$.ajax({
		url : 'ajaxstatusmsg.jsp',
		type : 'get',
		data : {msgType:'welcomeToPaymentSettings'},
		success : function(){
			showStatusMsg();
		}
		
	});
}
function showStatusMsg(response) {
  document.getElementById("paymentStatusMsg").focus();		
		
}

function openpopup(){
 var w = window.open('/home/links/eventpayhelp.html','', 'width=650,height=500,resizeable,scrollbars,top=350,left=350');
	    w.document.close(); 
}
	
function showEventPayHelp() {
openpopup();
}

function checkboxFocus(){
     if(document.getElementById('googledisabledid').checked && document.getElementById('otherdisableid').checked)
       $('googledisabledid').scrollTo();
    else if(document.getElementById('googledisabledid').checked || document.getElementById('otherdisableid').checked)
       $('googledisabledid').scrollTo(); 
}

function showAdvancedOptions(){
    if(document.getElementById('otherdisableid').checked || document.getElementById('googledisabledid').checked){
      document.getElementById('advanced').style.display='block';
    }else{
      document.getElementById('advanced').style.display='none';
     }
} */

var paymentclass='';
$(document).ready(function()
		{

	$('#creditcardinfo').attr('title',props.ps_cc_fee_help_msg);
	
	 $('.paypalicheck').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
	 
	 $('input.othericheck').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
	 
	 $('#otherpaymentmode').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
	 
	  $('input.creditcardicheck').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
	  
	  $('#ccfeemode').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
	  
	  $('#ccfeemode').on('ifUnchecked', function(event){    
		  $('#cctypes').val("no");
		  $('#processingfeeagree').hide();
	   });
	 
	  $('#ccfeemode').on('ifChecked', function(event){ 
		  $('#cctypes').val("yes");
		  //getDiv('getProcessingFeeAgreeDiv('ccfee')');
		  getProcessingFeeAgreeDiv('ccfee');
	    }); 
	 
	   
	    
	    
	   
	  
	    
	    /* others started */
	    
	    $('input.othericheck').on('ifUnchecked', function(event){  
	    	
		       $('#othersoptonboxid').slideUp("slow");    
		       $('#otherfonticon').addClass('original').removeClass('down');
		      /*  $('#otherbold').css("font-weight", "normal"); */
		       payment();
		       
		   });
	    
	    $('#otherfonticon').click(function() {
	    	
	    	$('#paypalfonticon').addClass('original').removeClass('down');
	    	$('#ccfonticon').addClass('original').removeClass('down');
	    	
	    	$('#ccdatablock').hide();
	    	$('#papaldatablock').hide();
	    	
	    	if($('#othersoptonboxid').is(':visible')) {
	    		$('#othersoptonboxid').slideUp("slow");
	    		$('#otherfonticon').addClass('original').removeClass('down');
	    		/* $('#otherbold').css("font-weight", "normal"); */
	    	}
	    	else{
	    		 $('#othersoptonboxid').slideDown("slow"); 
	    		 $('#otherfonticon').addClass('down').removeClass('original');
	    		/*  $('#otherbold').css("font-weight", "bold");
	    		 $('#ccbold').css("font-weight", "normal");
	    		 $('#ccpindb').css("font-weight", "normal");
	    		 $('#paypalbold').css("font-weight", "normal"); */
	    	}
	    	$('input.othericheck').iCheck('check');
	    });
	    
	    
	    
	    $('.othericheck').on('ifChecked', function(event){ 
	    	/* $('#otherbold').css("font-weight", "bold");
	    	$('#ccbold').css("font-weight", "normal");
	    	$('#ccpindb').css("font-weight", "normal");
	    	$('#paypalbold').css("font-weight", "normal"); */
	    	/* $('#ccdatablock').hide();
	    	$('#papaldatablock').hide(); */
	    	$('#othersoptonboxid').slideDown("slow");
	    	$('#otherfonticon').addClass('down').removeClass('original');
	    	$('#paypalfonticon').addClass('original').removeClass('down');
	    	$('#ccfonticon').addClass('original').removeClass('down');
	    	payment();
	    }); 
	    
	    
	    /* others ended */
	    
	    /* paypal started */
	    $('#paypalfonticon').click(function() {
	    
	    	$('#otherfonticon').addClass('original').removeClass('down');
	    	$('#ccfonticon').addClass('original').removeClass('down');
	    	
	    	$('#ccdatablock').hide();
	    	$('#othersoptonboxid').hide(); 
	    	
	    	if($('#papaldatablock').is(':visible')) {
	    		$('#papaldatablock').slideUp("slow");
	    		$('#paypalfonticon').addClass('original').removeClass('down');
	    		/* $('#paypalbold').css("font-weight", "normal"); */
	    	}
	    	else
	    	{ 
	    		$('#paypalfonticon').addClass('down').removeClass('original');
	    		$('#papaldatablock').slideDown("slow");
	    		/* $('#paypalbold').css("font-weight", "bold");
	    		$('#ccbold').css("font-weight", "normal");
	    		$('#ccpindb').css("font-weight", "normal");
	    		$('#otherbold').css("font-weight", "normal"); */
	    	}
	    	$('input.paypalicheck').iCheck('check'); 
	    	});
	    
	    $('input.paypalicheck').on('ifChecked', function(event){    
	    /* 	$('#paypalbold').css("font-weight", "bold");
	    	$('#ccbold').css("font-weight", "normal");
	    	$('#ccpindb').css("font-weight", "normal");
	    	$('#otherbold').css("font-weight", "normal"); */
			/* $('#ccdatablock').hide();
	    	$('#othersoptonboxid').hide(); */
			 $('#otherfonticon').addClass('original').removeClass('down');
		    	$('#ccfonticon').addClass('original').removeClass('down');
		     $('#papaldatablock').slideDown("slow");
		     $('#paypalfonticon').addClass('down').removeClass('original');

		     payment();
		   }); 
	    
	    $('input.paypalicheck').on('ifUnchecked', function(event){    
		       $('#papaldatablock').slideUp("slow"); 
		       $('#paypalfonticon').addClass('original').removeClass('down');
		      /*  $('#paypalbold').css("font-weight", "normal"); */
		       payment();
		   });
	    /* paypal ended */
	    
	    
	   
	    /* cc starts */
	    $('#ccfonticon').click(function() {
	    	$('#otherfonticon').addClass('original').removeClass('down');
	    	$('#paypalfonticon').addClass('original').removeClass('down');
	    	
	    	$('#papaldatablock').hide();
	    	$('#othersoptonboxid').hide();
	    	
	    	if($('#ccdatablock').is(':visible')) {
	    		$('#ccdatablock').slideUp("slow");
	    		$('#ccfonticon').addClass('original').removeClass('down');
	    		/* $('#ccbold').css("font-weight", "normal");
	    		$('#ccpindb').css("font-weight", "normal"); */
	    	}
	    	else{
	    		$('#ccfonticon').addClass('down').removeClass('original');
	    		 $('#ccdatablock').slideDown("slow"); 
	    		/*  $('#ccbold').css("font-weight", "bold");
	    		 $('#ccpindb').css("font-weight", "bold");
	    		 $('#paypalbold').css("font-weight", "normal");
	    		 $('#otherbold').css("font-weight", "normal"); */
	    	}
	    	$('input.creditcardicheck').iCheck('check'); 
	    	});
	    
	    $('.creditcardicheck').on('ifChecked', function(event){   
	    	/* $('#ccbold').css("font-weight", "bold");
	    	$('#ccpindb').css("font-weight", "bold");
	    	$('#paypalbold').css("font-weight", "normal");
	    	$('#otherbold').css("font-weight", "normal"); */
	    	
	    	$('#otherfonticon').addClass('original').removeClass('down');
	    	$('#paypalfonticon').addClass('original').removeClass('down');
	    	
	    	/* $('#papaldatablock').hide();
	    	$('#othersoptonboxid').hide(); */
	    	$('#ccdatablock').slideDown("slow");
	    	 $('#ccfonticon').addClass('down').removeClass('original');

	    	 $('#ccpindb').html(': '+ccNameValues[$('#ccdropdown').val()]);
	    	 
	    	 payment();
	    	 
	    }); 
	    
	    $('input.creditcardicheck').on('ifUnchecked', function(event){      
		       $('#ccdatablock').slideUp("slow");         
		       $('#ccfonticon').addClass('original').removeClass('down');
		      /*  $('#ccbold').css("font-weight", "normal");
		       $('#ccpindb').css("font-weight", "normal"); */
		       $('#ccpindb').html("");
		       payment();
		   });
	    
	    /* cc ends */
	    
	    payment();
	    var value = props.ps_cc_info_msg1+'<br>'+ props.ps_cc_info_msg2 +' <br>'+props.ps_cc_info_msg3;
	   $('#creditcardinfo').tooltipster({
		    fixedWidth:'100px',
		   content: $('<span>'+value+'</span>')
		    });
	  // $('#creditcardinfo').attr('title',props.ps_cc_fee_help_msg);
	   
		$('#paymentapprovalinfo').tooltipster({
			contentAsHTML:'true',
		    position: 'right',
		    content: $('<span>'+props.ps_other_payment_msg1+' <br/> '+props.ps_other_payment_msg2+'<br/> '+props.ps_other_payment_msg3+' <br/> '+props.ps_other_payment_msg4+'</span>'),
			});
	});	
	
function changecc()  
{ 
	$('#ccdescdiv').show();
	var cctypeselected=$('#ccdropdown').val();
	authorizeBlock(cctypeselected);
}

function authorizeBlock(val){	
  if(val=='paypal_pro'){
	document.getElementById('authorize').style.display='none';
	document.getElementById('braintree').style.display='none';
	document.getElementById('stripe').style.display='none';
	document.getElementById('payulatam').style.display='none';
	$('#ccpindb').html(": Eventbee");
}else if(val=='authorize.net'){
	document.getElementById('payulatam').style.display='none';
	document.getElementById('braintree').style.display='none';
	document.getElementById('stripe').style.display='none';
	document.getElementById('authorize').style.display='block';
	$('#ccpindb').html(": Authorize.Net");
}else if(val=='braintree_manager'){
	document.getElementById('payulatam').style.display='none';
	document.getElementById('authorize').style.display='none';
	document.getElementById('stripe').style.display='none';
	document.getElementById('braintree').style.display='block';
	$('#ccpindb').html(": Braintree");
}else if(val=='payulatam'){
	document.getElementById('payulatam').style.display='block';
	document.getElementById('authorize').style.display='none';
	document.getElementById('stripe').style.display='none';
	document.getElementById('braintree').style.display='none';
	$('#ccpindb').html(": payulatam");
}else if(val=='stripe'){
	document.getElementById('payulatam').style.display='none';
	document.getElementById('authorize').style.display='none';
	document.getElementById('braintree').style.display='none';
	document.getElementById('stripe').style.display='block';
	$('#ccpindb').html(": Stripe");
}
}
	
function getStripeInst(){
	parent.loadingPopup();
	var eid=document.getElementById('eventid').value;
	var url="PaymentSettings!getStripeScreen?eid="+eid;     
     $('#myModal').on('show.bs.modal', function () {				  	
   	  $('#myModal .modal-title').html('<h3><span style="padding-left:10px">'+props.ps_stripe_ac_settings_popup_title+'</span></h3>');	
   	  $('iframe#popup').attr("src",url);
   	      });		        	     
   	   $('#myModal').modal('show');
   	   $('#myModal').on('hide.bs.modal', function () { 
   	        $('iframe#popup').attr("src",'');			        
   	        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="modalOnLoad()" frameborder="0"></iframe>');
   });
}
	
function brainTree(){
	parent.loadingPopup();
	var eid=document.getElementById('eventid').value;
	var url ="PaymentSettings!getBrinbtreeScreen?eid="+eid;
	
$('#myModal').on('show.bs.modal', function () {				  	
  $('#myModal .modal-title').html('<h3><span style="padding-left:10px">'+props.ps_braintree_ac_settings_popup_title+'</span></h3>');	
  $('iframe#popup').attr("src",url);  
      });		          
   $('#myModal').modal('show');
   $('#myModal').on('hide.bs.modal', function () { 
        $('iframe#popup').attr("src",'');			        
        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="modalOnLoad()" frameborder="0"></iframe>');
});
}
</script>
