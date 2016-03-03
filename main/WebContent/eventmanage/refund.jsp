<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
<!-- <head>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
</head> -->
<body>
<s:form name="refundform" id="refundform" action="PaymentsRefund!submitRefund" method="post">





<div class="row">
<div class="col-md-1 col-xs-1 col-sm-1"></div>
<div class="col-md-6 col-xs-6 col-sm-6">
<div id="refundErrorMsg" class="alert alert-danger" style="display:none;margin-left: 28px;margin-bottom:6px!important;"></div></div>
<div class="col-xs-4 col-md-4 col-sm-4"></div>
</div>

<div class="container bottom-gap">
<div class="row" id="paypalhelplink" style="display:none;">
<!-- <table><tr>
<td class="smallestfont"><a href="#" onclick="getHelpPopup()" style="cursor: pointer;">Click here for instructions</a> to Grant API Third Party Permissions.
</td></tr></table> -->
<div class="col-md-1 col-xs-1 col-sm-1"></div>
<div class="col-md-10 sub-text">
<a href="javascript:;" onclick="getHelpPopup()" style="cursor: pointer;"><s:text name="rep.clck.instrctns.lbl"/></a><s:text name="rep.to.grnd.api.prty.lbl"/> 

</div>

</div>
</div>



<!-- <div class="row">
<div class="col-md-1 col-xs-1 col-sm-1"></div>
<div class="col-md-6 col-xs-6 col-sm-6">
<div id="refundStatusMsg" class="alert alert-success" style="display:none;margin-left: 28px;"></div>
</div>
<div class="col-xs-4 col-md-4 col-sm-4"></div>
</div> -->



<s:hidden name="eid"></s:hidden>
<s:hidden name="tid" id="transactionid"></s:hidden>
<s:hidden name="refundMap['actualAmount']"></s:hidden>
<s:hidden name="refundMap['ccvendor']"></s:hidden>
<s:hidden name="refundMap['paymenttype']"></s:hidden>
<s:hidden name="refundMap['extpayid']"></s:hidden>
<s:hidden name="refundMap['currencycode']"></s:hidden>
<s:hidden name="refundMap['ebeefee']"></s:hidden>
<s:hidden name="refundMap['collectedby']"></s:hidden>
<s:hidden name="refundMap['refundedAmount']"></s:hidden>
<div>
<s:fielderror cssErrorClass="errorMessage" theme="simple">
</s:fielderror>
</div>

<div class="container">
<div class="row">

<div class="col-md-1 col-xs-1 col-sm-1"></div>
<div class="col-md-2 col-xs-2"><s:text name="rep.actal.lbl"/>&nbsp;<s:text name="rep.amnt.lbl"/></div>
<div class="col-xs-9"><span style="display:inline">${currency}</span><s:property value="%{actualAmount}"></s:property></div>



<%-- <div class="col-xs-3"></div>
<div class="col-xs-8"><span style="display:inline">${currency}</span><s:property value="%{actualAmount}"></s:property></div> --%>
</div>
<br/>

<div class="row">
<%-- <div class="col-xs-3" style="padding-top: 5px;">Refund&nbsp;Amount<span class="required">*</span></div>
<div class="col-xs-3">
<div class="input-group" id="amountfield">
  <span class="input-group-addon">${currency}</span>
  <s:textfield name="refundAmount" size="8" cssClass="form-control"></s:textfield>
  </div>
</div> --%>



<div class="col-md-1 col-xs-1 col-sm-1"></div>
<div class="col-md-2 col-xs-2 col-sm-2"><s:text name="rep.refund.lbl"/>&nbsp;<s:text name="rep.amnt.lbl"/><span class="required">*</span></div>
<div class="col-xs-4 col-md-4 col-sm-4">

<div class="input-group" id="amountfield">
  <span class="input-group-addon">${currency}</span>
  <s:textfield name="refundAmount" size="8" cssClass="form-control"></s:textfield>
  </div>


</div>



</div>
</div>


<%-- <table>
<tr>
<td width="64%">Actual&nbsp;Amount</td>
<td><span style="display:inline">${currency}</span><s:property value="%{actualAmount}"></s:property>
</td>
 </tr>
<tr>
<td width="64%" valign="top">Refund&nbsp;Amount&nbsp;*</td>
<td>
<span style="display:inline">${currency}</span>
<s:textfield name="refundAmount" size="8"></s:textfield>
</td>
</tr>

</table> --%>
<!-- 
<s:if test="%{refundMap['paymenttype']=='paypal' && refundMap['collectedby']!='paypalx'}">
<table><tr>

<td colspan="2"><span class="smallestfont">
Please&nbsp;<a href='https://www.paypal.com/cgi-bin/webscr?cmd=_login-run' target='_blank'>login</a>&nbsp;to your PayPal account, to give <a href="https://www.sandbox.paypal.com/us/cgi-bin/?cmd=_profile-api-add-authorization&apiusername=ebpayx-facilitator_api1.gmail.com" target='_blank'>Third Party Permissions</a> and <a href='#'>go for instructions</a>.</span>
 </td>
      
</tr></table>
</s:if>
 -->
</s:form>
<br/>

<div class="form-group" id="refunddiv">
 <div class="col-md-3 col-sm-3"></div>
                        <div class="col-sm-6 col-md-6" style="margin-left:10px;">
                             <button type="submit" class="btn btn-primary" id="refundsubmit">
                               <s:text name="global.submit.btn.lbl"></s:text></button>
                            <button class="btn btn-cancel" id="cancel">
                               <i class="fa fa-times"></i></button>
                        </div>
 </div>



<!-- <div class="form-group" id="refunddiv">
                        <div class="col-sm-offset-2 col-sm-6 pull-right" >
                            <button type="submit" class="btn btn-primary" id="refundsubmit">
                               Continue</button>
                            <button class="btn btn-cancel" id="cancel">
                               <i class="fa fa-times"></i></button>
                        </div>
                    </div> -->
                    
                    
                    <div class="form-group" id="refundloading" style="display: none;">
                    <div class="col-sm-3"></div>
                          <div class="col-sm-6">
                            <img src="../images/ajax-loader.gif"></img>
                        </div>
                    </div>
                    

</body></html>
<%-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> --%>
 <script>
 $(document).ready(function(){
	 $('#refundsubmit').click(function(){
		$('#refundloading').show();
		$('#refunddiv').hide();
$.ajax({
	type:'get',
	data:$('#refundform').serialize(),
	url:'PaymentsRefund!submitRefund',
	success:function(result){
		var tid=$('#transactionid').val();
		if(!parent.isValidActionMessage(result)) return;
		if(result.indexOf('errorMessage')>-1){
			
		//$('#refundStatusMsg').hide();
		$('#refundErrorMsg').show();
		$('#refundErrorMsg').html(result);
		$('#refundloading').hide();
		$('#refunddiv').show();
		if(result.indexOf("third-party access")>-1 || result.indexOf("You do not have permissions to make this API call")>-1)
        	$('#paypalhelplink').show();
		
			}else{
		$('#refundErrorMsg').hide();
		//$('#refundStatusMsg').show();
		//$('#refundStatusMsg').html(result);
		notification(props.trans_refund_success_msg,'success');
		$('#refundloading').hide();
		//$('#refunddiv').hide();
		$('#refunddiv').show();
		//closeTransactionTickets();
		
		//parent.showDetails(tid);
		//parent.$('#myModal').modal('hide');
	}
		}
});
	 });

	 $('#cancel').click(function(){
		 closeTransactionTickets();
		});

     $(document).on('click','',function(){
         });
		
	 
	 });

function getHelpPopup(){
	$('#myModal .modal-title').html(props.refund_paypal_api_per_lbl);
	$('iframe#popup').attr("src",'/main/help/<%=I18n.getLanguageFromSession()%>/paypalrefundapipermission.html');
	$('#myModal').modal('show');
	
	//showYUIinIframe('Paypal API Third Party Permissions','/main/help/paypalrefundapipermission.html',775,500);
}
 
 </script>

