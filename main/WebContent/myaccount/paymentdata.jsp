<%@taglib uri="/struts-tags" prefix="s"%>
<form name='paypalform' id='paypalform' action="<s:property value="paypalMap['paypal_form_url']"/>" method="post" target="_top">
   <input type="hidden" name="cmd" value="_xclick">
   <input type="hidden" name="business" value="<s:property value="paypalMap['paypalmerchantid']"/>">
   <input type="hidden" name="item_name"   value="<s:property value="paypalMap['itemname']"/>">
   <input type="hidden" name="item_number"   value="<s:property value="paypalMap['itemnumber']"/>">
   <input type="hidden" name="amount" value="<s:property value="paypalMap['paypalamount']"/>">
   <input type="hidden" name="no_shipping" value="1">
   <input type="hidden" name="no_note" value="1">
   <input type="hidden" name="currency_code" value="<s:property value="paypalMap['currencycode']"/>">
   <input type="hidden" name="tax" value="<s:property value="paypalMap['paypaltax']"/>">
   <input type="hidden" name="bn" value="IC_Sample">
   <input type="hidden" name="notify_url" value="<s:property value="paypalMap['paypal_notify_url']"/>">
   <input type="hidden" name="lc" value="<s:property value="paypalMap['paypallang']"/>">
   <input type="hidden" name="return" value="<s:property value="paypalMap['serveraddress']"/>/main/myaccount/home!closePaypalScreen?seqId=<s:property value="paypalMap['itemnumber']"/>&source=paypal&userId=<s:property value="paypalMap['refid']"/>">
   <input type="hidden" name="cancel_return" value="<s:property value="paypalMap['serveraddress']"/>/main/myaccount/home!closePaypalScreen?seqId=<s:property value="paypalMap['itemnumber']"/>&source=Paypal&userId=<s:property value="paypalMap['refid']"/>">
 </form>
<script>
document.getElementById('paypalform').submit();	
</script>
</body>