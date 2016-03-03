package com.event.beans;

public class PaymentData {
	private String google_merchant_id="";
	private String google_merchant_key="";
	private String google_merchant_status="";
	private String paypal_merchant_accmail="";
	private String other_payment_status="";
	private String other_payment_desc="";
	private String eventbee_payment_status="";
	private String paypal_merchant_status=""; 
	private String eventbee_payment_by="manager";
	private String currencytype="";
	private String paypalchecked="";
	private String googlechecked=""; 
	private String eventbeechecked="";
	private String otherchecked="";
	private String ebeecreditschecked="";
	private String ebeecredits_payment_status="";
	private String ebeecredits_payment_desc="";
	private String refundpolicy="";
	private String tax="0";
	private String paypalSupportedLanguage="";
	private String paymentApprovalType="";
	private String email="";
	private String status="";
	private String cardstatus="InActive";
	private String ccprocessingfee="yes";
	private String ccfee="";
	private String apiLoginId="";
	private String apiTransactionId="";
	private String eventbeeVendor="";
	private String google_other_ccfee="";
	private String paypal_other_ccfee="";
	private String ebeecredits_other_ccfee="";
	private String eventbee_other_ccfee="";
	private String other_other_ccfee="";
	private String braintreekey="";
	private String braintreeArroved="";
	private String existedBrainKey="";
	private String stripekey="";
	private String brainPriKey="";
	private String brainPubKey="";
	private String payuApiKey="";
	private String payuApiLogin="";
	private String payuAccountId="";
	private String  payuMarchantId="";
	
	public String getPayuApiKey() {
		return payuApiKey;
	}
	public void setPayuApiKey(String payuApiKey) {
		this.payuApiKey = payuApiKey;
	}
	public String getPayuApiLogin() {
		return payuApiLogin;
	}
	public void setPayuApiLogin(String payuApiLogin) {
		this.payuApiLogin = payuApiLogin;
	}
	public String getPayuAccountId() {
		return payuAccountId;
	}
	public void setPayuAccountId(String payuAccountId) {
		this.payuAccountId = payuAccountId;
	}
	public String getPayuMarchantId() {
		return payuMarchantId;
	}
	public void setPayuMarchantId(String payuMarchantId) {
		this.payuMarchantId = payuMarchantId;
	}
	public String getBrainPriKey() {
		return brainPriKey;
	}
	public void setBrainPriKey(String brainPriKey) {
		this.brainPriKey = brainPriKey;
	}
	public String getBrainPubKey() {
		return brainPubKey;
	}
	public void setBrainPubKey(String brainPubKey) {
		this.brainPubKey = brainPubKey;
	}
	public String getStripekey() {
		return stripekey;
	}
	public void setStripekey(String stripekey) {
		this.stripekey = stripekey;
	}
	public String getExistedBrainKey() {
		return existedBrainKey;
	}
	public void setExistedBrainKey(String existedBrainKey) {
		this.existedBrainKey = existedBrainKey;
	}
	public String getBraintreeArroved() {
		return braintreeArroved;
	}
	public void setBraintreeArroved(String braintreeArroved) {
		this.braintreeArroved = braintreeArroved;
	}
	public String getBraintreekey() {
		return braintreekey;
	}
	public void setBraintreekey(String braintreekey) {
		this.braintreekey = braintreekey;
	}
	public String getEventbeeVendor() {
		return eventbeeVendor;
	}
	public void setEventbeeVendor(String eventbeeVendor) {
		this.eventbeeVendor = eventbeeVendor;
	}
	public String getCcfee() {
		return ccfee;
	}
	public void setCcfee(String ccfee) {
		this.ccfee = ccfee;
	}
	public String getCcprocessingfee() {
		return ccprocessingfee;
	}
	public void setCcprocessingfee(String ccprocessingfee) {
		this.ccprocessingfee = ccprocessingfee;
	}
	public String getCardstatus() {
		return cardstatus;
	}
	public void setCardstatus(String cardstatus) {
		this.cardstatus = cardstatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEbeecreditschecked() {
		return ebeecreditschecked;
	}
	public void setEbeecreditschecked(String ebeecreditschecked) {
		this.ebeecreditschecked = ebeecreditschecked;
	}
	public String getEbeecredits_payment_status() {
		return ebeecredits_payment_status;
	}
	public void setEbeecredits_payment_status(String ebeecredits_payment_status) {
		this.ebeecredits_payment_status = ebeecredits_payment_status;
	}
	public String getEbeecredits_payment_desc() {
		return ebeecredits_payment_desc;
	}
	public void setEbeecredits_payment_desc(String ebeecredits_payment_desc) {
		this.ebeecredits_payment_desc = ebeecredits_payment_desc;
	}
	public String getPaypalSupportedLanguage() {
		return paypalSupportedLanguage;
	}
	public void setPaypalSupportedLanguage(String paypalSupportedLanguage) {
		this.paypalSupportedLanguage = paypalSupportedLanguage;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getRefundpolicy() {
		return refundpolicy;
	}
	public void setRefundpolicy(String refundpolicy) {
		this.refundpolicy = refundpolicy;
	}
	public String getGooglechecked() {
		return googlechecked;
	}
	public void setGooglechecked(String googlechecked) {
		this.googlechecked = googlechecked;
	}
	public String getEventbeechecked() {
		return eventbeechecked;
	}
	public void setEventbeechecked(String eventbeechecked) {
		this.eventbeechecked = eventbeechecked;
	}
	public String getOtherchecked() {
		return otherchecked;
	}
	public void setOtherchecked(String otherchecked) {
		this.otherchecked = otherchecked;
	}
	public String getPaypalchecked() {
		return paypalchecked;
	}
	public void setPaypalchecked(String paypalchecked) {
		this.paypalchecked = paypalchecked;
	}
	public String getCurrencytype() {
		return currencytype;
	}
	public void setCurrencytype(String currencytype) {
		this.currencytype = currencytype;
	}
	public String getGoogle_merchant_id() {
		return google_merchant_id;
	}
	public void setGoogle_merchant_id(String google_merchant_id) {
		this.google_merchant_id = google_merchant_id;
	}
	public String getGoogle_merchant_key() {
		return google_merchant_key;
	}
	public void setGoogle_merchant_key(String google_merchant_key) {
		this.google_merchant_key = google_merchant_key;
	}
	public String getGoogle_merchant_status() {
		return google_merchant_status;
	}
	public void setGoogle_merchant_status(String google_merchant_status) {
		this.google_merchant_status = google_merchant_status;
	}
	public String getPaypal_merchant_accmail() {
		return paypal_merchant_accmail;
	}
	public void setPaypal_merchant_accmail(String paypal_merchant_accmail) {
		this.paypal_merchant_accmail = paypal_merchant_accmail;
	}
	public String getOther_payment_status() {
		return other_payment_status;
	}
	public void setOther_payment_status(String other_payment_status) {
		this.other_payment_status = other_payment_status;
	}
	public String getOther_payment_desc() {
		return other_payment_desc;
	}
	public void setOther_payment_desc(String other_payment_desc) {
		this.other_payment_desc = other_payment_desc;
	}
	public String getEventbee_payment_status() {
		return eventbee_payment_status;
	}
	public void setEventbee_payment_status(String eventbee_payment_status) {
		this.eventbee_payment_status = eventbee_payment_status;
	}
	public String getPaypal_merchant_status() {
		return paypal_merchant_status;
	}
	public void setPaypal_merchant_status(String paypal_merchant_status) {
		this.paypal_merchant_status = paypal_merchant_status;
	}
	public String getEventbee_payment_by() {
		return eventbee_payment_by;
	}
	public void setEventbee_payment_by(String eventbee_payment_by) {
		this.eventbee_payment_by = eventbee_payment_by;
	}
	public String getPaymentApprovalType() {
		return paymentApprovalType;
	}
	public void setPaymentApprovalType(String paymentApprovalType) {
		this.paymentApprovalType = paymentApprovalType;
	}
	public String getApiTransactionId() {
		return apiTransactionId;
	}
	public void setApiTransactionId(String apiTransactionId) {
		this.apiTransactionId = apiTransactionId;
	}
	public String getApiLoginId() {
		return apiLoginId;
	}
	public void setApiLoginId(String apiLoginId) {
		this.apiLoginId = apiLoginId;
	}
	public String getGoogle_other_ccfee() {
		return google_other_ccfee;
	}
	public void setGoogle_other_ccfee(String google_other_ccfee) {
		this.google_other_ccfee = google_other_ccfee;
	}
	public String getPaypal_other_ccfee() {
		return paypal_other_ccfee;
	}
	public void setPaypal_other_ccfee(String paypal_other_ccfee) {
		this.paypal_other_ccfee = paypal_other_ccfee;
	}
	public String getEbeecredits_other_ccfee() {
		return ebeecredits_other_ccfee;
	}
	public void setEbeecredits_other_ccfee(String ebeecredits_other_ccfee) {
		this.ebeecredits_other_ccfee = ebeecredits_other_ccfee;
	}
	public String getEventbee_other_ccfee() {
		return eventbee_other_ccfee;
	}
	public void setEventbee_other_ccfee(String eventbee_other_ccfee) {
		this.eventbee_other_ccfee = eventbee_other_ccfee;
	}
	public String getOther_other_ccfee() {
		return other_other_ccfee;
	}
	public void setOther_other_ccfee(String other_other_ccfee) {
		this.other_other_ccfee = other_other_ccfee;
	}
	
}
