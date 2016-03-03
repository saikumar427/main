<%@taglib uri="/struts-tags" prefix="s"%>

<s:form name="addattendeepayment"  id='addattendeepayment'  action='' method="post" theme="simple">
<input type="hidden"  id='tid'  name='tid' value="${tid}"/>
<input type="hidden"  id='eid'  name='eid' value="${eid}"/>
<input type="hidden"  id='collecteTktQty'  name='collecteTktQty' value="${collecteTktQty}"/>
<%-- <table width="100%" cellpadding="0" cellspacing="0">
<tr>
 <td><input type="radio" id="paidpayment" name="pctype" value="paid" onclick="checkPCType('paidpayment');" <s:if test="%{collecteTktQty != 0}">checked="checked"</s:if>/>Already collected payment</td>
 </tr>
 <s:if test="%{currencyCode=='USD' && collecteTktQty > 0}">
 <tr>
 <td><input type="radio" id="ccpayment" name="pctype" value="cc" onclick="checkPCType('ccpayment');"/>Process credit card to collect payment</td>
 </tr>
 </s:if>
 <tr>
 <td><input type="radio" id="nopayment" name="pctype" value="nopayment" onclick="checkPCType('nopayment');" <s:if test="%{collecteTktQty == 0}">checked="checked"</s:if>/>Complimentary registration</td>
 </tr>
</table> --%>
<br/>

 <div class="row">
   <div class="col-md-1 col-sm-2 col-xs-3"  style="width:2%"><input type="radio" id="paidpayment" name="pctype" value="paid" class="payment" onclick="checkPCType('paidpayment');" <s:if test="%{collecteTktQty != 0}">checked="checked"</s:if>/></div>
    <div class="col-md-11 col-sm-10 col-xs-9" style="margin-top: 5px;"><s:text name="aa.payment.already.collected.lbl"/></div>
 </div>
 
<div style="height:5px"></div>

 <s:if test="%{currencyCode=='USD' && collecteTktQty > 0}">
   <div class="row">
      <div class="col-md-1 col-sm-2 col-xs-3"  style="width:2%"><input type="radio" id="ccpayment" name="pctype" value="cc" class="payment" onclick="checkPCType('ccpayment');"/></div>
      <div class="col-md-11 col-sm-10 col-xs-9" style="margin-top: 5px;"><s:text name="aa.payment.process.cc.lbl"/></div>
  </div>
  <div style="height:5px"></div>
  </s:if>
  
   <div class="row">
       <div class="col-md-1 col-sm-2 col-xs-3"  style="width:2%"><input type="radio" id="nopayment" name="pctype" value="nopayment" class="payment" onclick="checkPCType('nopayment');" <s:if test="%{collecteTktQty == 0}">checked="checked"</s:if>/></div>
       <div class="col-md-11 col-sm-10 col-xs-9" style="margin-top: 5px;"><s:text name="aa.payment.complimentary.lbl"/></div>
   </div>

 <div class="col-xs-12 text-center">
	 <div id="paymentbutton"><span>
	<input type="button"  id='paymentcontinue' class="btn btn-primary" name='paymentcontinue' value="<s:text name="global.continue.btn.lbl"/>" onclick="submitPayment();" /></span>
	<span>
	<input type="button"  id='paymentback' class="btn btn-primary" name='paymentback' value="<s:text name="global.back.btn.lbl"/>" onclick="getProfilePage();"/>
</span>
</div>
<div >
<span id="paymentprocessimg" ></span>
</div>
</div> 


 
<%--

<table border="0">
 <tr>
 <td>
 <div id="paymentbutton" style="position:relative;">
 <span style="position:absolute;top:40px;left:300px;">
	<input type="button"  id='paymentcontinue' class="btn btn-primary" name='paymentcontinue' value="Continue" onclick="submitPayment();" />
 </span>
 <span style="position:absolute;top:40px; left:390px;">
	<input type="button"  id='paymentback' class="btn btn-primary" name='paymentback' value="Back" onclick="getProfilePage();"/>
 </span>
</div>
<div style="position:relative;">
<span id="paymentprocessimg"style="position:absolute;top:40px;left:300px;" ></span>
</div>
</td></tr>
 </table> --%>
</s:form>
