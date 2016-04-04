package com.eventmanage.actions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import org.json.JSONObject;

import com.event.beans.EventData;
import com.event.beans.PaymentData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.EventHelperDB;
import com.event.dbhelpers.PaymentSettingsDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.TicketsDB;
import com.event.dbhelpers.UpgradeEventDB;
import com.event.helpers.DataPopulator;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.GenUtil;
import com.eventbee.util.CoreConnector;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class PaymentSettingsAction extends ActionSupport implements Preparable,ValidationAware{
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";
	private EventData eventData=null;
	private PaymentData paymentData=new PaymentData();
	private Vector payvector=new Vector();
	private String paymentSettingsErrorsExists = "";
	private String msgType = "paymentsettings";
	private String currency="";
	private String mgrid="";
	private String currencycode="";
	private String msg="";
	private String currencySymbol="";
	private String purpose="";
	private String mgrType="";
	private String paytypekey="";
	private String specialfee="";
	private String evtlevel="";
	private String payUapikey="";
	private String payUapilogin="";
	
	public String getPayUapikey() {
		return payUapikey;
	}
	public void setPayUapikey(String payUapikey) {
		this.payUapikey = payUapikey;
	}
	public String getPayUapilogin() {
		return payUapilogin;
	}
	public void setPayUapilogin(String payUapilogin) {
		this.payUapilogin = payUapilogin;
	}
	private HashMap<String, String> specialFeeMap=new HashMap<String, String>();
	
	public HashMap<String, String> getSpecialFeeMap() {
		return specialFeeMap;
	}
	public void setSpecialFeeMap(HashMap<String, String> specialFeeMap) {
		this.specialFeeMap = specialFeeMap;
	}
	public String getEvtlevel() {
		return evtlevel;
	}
	public void setEvtlevel(String evtlevel) {
		this.evtlevel = evtlevel;
	}
	public String getSpecialfee() {
		return specialfee;
	}
	public void setSpecialfee(String specialfee) {
		this.specialfee = specialfee;
	}
	public String getPaytypekey() {
		return paytypekey;
	}
	public void setPaytypekey(String paytypekey) {
		this.paytypekey = paytypekey;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getCurrencySymbol() {
		return currencySymbol;
	}
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCurrencycode() {
		return currencycode;
	}
	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}
	public String getMgrid() {
		return mgrid;
	}
	public void setMgrid(String mgrid) {
		this.mgrid = mgrid;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getPaymentSettingsErrorsExists() {
		return paymentSettingsErrorsExists;
	}
	public void setPaymentSettingsErrorsExists(String paymentSettingsErrorsExists) {
		this.paymentSettingsErrorsExists = paymentSettingsErrorsExists;
	}
	public PaymentData getPaymentData() {
		return paymentData;
	}
	public void setPaymentData(PaymentData paymentData) {
		this.paymentData = paymentData;
	}
	public Vector getPayvector() {
		return payvector;
	}
	public void setPayvector(Vector payvector) {
		this.payvector = payvector;
	}

	public EventData getEventData() {
		return eventData;
	}
	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}
	private ArrayList<Entity> currencytype=new ArrayList<Entity>();

	public ArrayList<Entity> getCurrencytype() {
		return currencytype;
	}
	public void setCurrencytype(ArrayList<Entity> currencytype) {
		this.currencytype = currencytype;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public void prepare(){
		paymentData=new PaymentData();
	}
	public void populateCurrencyTypes(){
		currencytype=DataPopulator.populateCurrencyTypes();
	}
	public void populatePaymentTypes(String eventid){
		payvector=PaymentSettingsDB.getAllPaymentTypes(eventid,"Event");
		System.out.println(eventid+" payvector : "+payvector);
		Boolean data =  payvector.isEmpty();
		if(data){
			try{
				payvector=PaymentSettingsDB.getMgrAllPaymentTypes(mgrid);
				populatePaymentVector(payvector);
			}catch(Exception e){
				System.out.println("Exception while getting payment types"+e);
			}
		}else{
			populatePaymentVector(payvector);
		}
	}
	
	public String populateMgrPaymentTypes(String mgrid){
		String eventid="";
		try{
		payvector=PaymentSettingsDB.getMgrAllPaymentTypes(mgrid);
		populatePaymentVector(payvector);
		if(payvector.size()>0){
			HashMap paytypesmap=(HashMap)payvector.get(0);
			eventid=(String)GenUtil.getHMvalue(paytypesmap,"refid","");
		}
		}catch(Exception e){
			System.out.println("Exception occured in populateMgrPaymentTypes method for manager :: "+mgrid+" :: "+e.getMessage());
		}
		return eventid;
	}
	
	public void populatePaymentVector(Vector payvector){
	System.out.println("mgrid in populatePaymentVector is:"+mgrid); 
	try{
	HashMap<String,HashMap<String,String>> gatewaysmap=PaymentSettingsDB.getPaymentKeyValues(mgrid);
	paymentData.setStripekey(gatewaysmap.get("stripe").get("stripekey"));
	paymentData.setApiLoginId(gatewaysmap.get("authorize").get("authapiid"));
	paymentData.setApiTransactionId(gatewaysmap.get("authorize").get("authapikey"));
	paymentData.setBraintreekey(gatewaysmap.get("braintree").get("brainmid"));
	paymentData.setBrainPubKey(gatewaysmap.get("braintree").get("brainpublickey"));
	paymentData.setBrainPriKey(gatewaysmap.get("braintree").get("brainprivatekey"));
	paymentData.setPayuApiKey(gatewaysmap.get("payulatam").get("api_key"));
	paymentData.setPayuApiLogin(gatewaysmap.get("payulatam").get("api_login"));
	paymentData.setPayuAccountId(gatewaysmap.get("payulatam").get("account_id"));
	paymentData.setPayuMarchantId(gatewaysmap.get("payulatam").get("marchant_id"));
	if(payvector!=null){
		System.out.println("in if case"+payvector.size()); 
		if(payvector.size()>0){
	    	for(int i=0;i<payvector.size();i++){
	    		HashMap paytypesmap=(HashMap)payvector.get(i);
	    		if("google".equals((String)GenUtil.getHMvalue(paytypesmap,"paytype","paypal"))){
	    			paymentData.setGoogle_merchant_id(GenUtil.getHMvalue(paytypesmap,"attrib_1",""));
	    			paymentData.setGoogle_merchant_key(GenUtil.getHMvalue(paytypesmap,"attrib_2",""));
	    			paymentData.setGoogle_merchant_status(GenUtil.getHMvalue(paytypesmap,"status","Enabled"));
	    			paymentData.setGoogle_other_ccfee(GenUtil.getHMvalue(paytypesmap,"other_ccfee",""));
	    		}
	    		if("paypal".equals((String)GenUtil.getHMvalue(paytypesmap,"paytype","paypal"))){
	    			paymentData.setPaypal_merchant_accmail(GenUtil.getHMvalue(paytypesmap,"attrib_1",""));
	    			paymentData.setPaypal_merchant_status(GenUtil.getHMvalue(paytypesmap,"status","Enabled"));
	    			paymentData.setPaypalSupportedLanguage(GenUtil.getHMvalue(paytypesmap,"attrib_3","English"));
	    			paymentData.setPaypal_other_ccfee(GenUtil.getHMvalue(paytypesmap,"other_ccfee",""));
	    		}
	    		if("ebeecredits".equals((String)GenUtil.getHMvalue(paytypesmap,"paytype","paypal"))){
	    			paymentData.setEbeecredits_payment_desc(GenUtil.getHMvalue(paytypesmap,"attrib_1",""));
	    			paymentData.setEbeecredits_payment_status(GenUtil.getHMvalue(paytypesmap,"status","Enabled"));
	    			paymentData.setEbeecredits_other_ccfee(GenUtil.getHMvalue(paytypesmap,"other_ccfee",""));
	    		}
	    		if("other".equals((String)GenUtil.getHMvalue(paytypesmap,"paytype","paypal"))){
	    			String lang = I18n.getActualLangFromSession();
	    			String otherDes = PaymentSettingsDB.otherDesc(mgrid,lang,eid);
	    			paymentData.setOther_payment_desc(otherDes);
	    			paymentData.setOther_payment_status(GenUtil.getHMvalue(paytypesmap,"status","Enabled"));
	    			paymentData.setPaymentApprovalType(GenUtil.getHMvalue(paytypesmap,"attrib_4","auto"));
	    			paymentData.setOther_other_ccfee(GenUtil.getHMvalue(paytypesmap,"other_ccfee",""));
	    		}
	    		if("eventbee".equals((String)GenUtil.getHMvalue(paytypesmap,"paytype","paypal"))){
	    			paymentData.setEventbee_payment_by(GenUtil.getHMvalue(paytypesmap,"attrib_1",""));
	    			paymentData.setEventbeeVendor(GenUtil.getHMvalue(paytypesmap,"attrib_5",""));
	    			paymentData.setEventbee_payment_status(GenUtil.getHMvalue(paytypesmap,"status","Enabled"));
	    			paymentData.setEventbee_other_ccfee(GenUtil.getHMvalue(paytypesmap,"other_ccfee",""));
	    			
	    			if("braintree_manager".equalsIgnoreCase(paymentData.getEventbeeVendor())){
	    				System.out.println("mgr_id:::::in paymentsaction:::"+mgrid);
	    				
	    			paymentData.setBraintreekey(GenUtil.getHMvalue(paytypesmap,"attrib_2",""));
	    			paymentData.setBrainPubKey(GenUtil.getHMvalue(paytypesmap,"attrib_3",""));
	    			paymentData.setBrainPriKey(GenUtil.getHMvalue(paytypesmap,"attrib_4",""));
	    			}else if("stripe".equalsIgnoreCase(paymentData.getEventbeeVendor())){
	    				paymentData.setStripekey(GenUtil.getHMvalue(paytypesmap,"attrib_2",""));
	    			}else if("authorize.net".equalsIgnoreCase(paymentData.getEventbeeVendor())){
	    				paymentData.setApiLoginId(GenUtil.getHMvalue(paytypesmap,"attrib_2",""));
	    				paymentData.setApiTransactionId(GenUtil.getHMvalue(paytypesmap,"attrib_3",""));
	    			}else if("payulatam".equalsIgnoreCase(paymentData.getEventbeeVendor())){
	    				paymentData.setPayuApiKey(GenUtil.getHMvalue(paytypesmap,"attrib_2",""));
	    				paymentData.setPayuApiLogin(GenUtil.getHMvalue(paytypesmap,"attrib_3",""));
	    				try{
	    					JSONObject json= new JSONObject(paytypesmap.get("attrib_4").toString());
		    				String marchantId=json.getString("marchant_id");
		    				String accountId=json.getString("account_id");
		    				paymentData.setPayuAccountId(accountId);
		    				paymentData.setPayuMarchantId(marchantId);
	    				}catch(Exception e){
	    					System.out.println("EXCEPTION ERROR: "+e.getMessage());
	    				}
	    			}
	    			
	    		}
	    	}
	    }
	  }
	}catch(Exception e){
		System.out.println("Exception occured in populatePaymentVector method for manager :: "+mgrid+ e.getMessage());
	}
	} 
	
	public void populatePaymentDataData(String eventid){
	try{
		populateCurrencyTypes();
		String refundpolicy=EventDB.getConfigVal(eventid,"event.ticketpage.refundpolicy.statement","");
		if("".equals(refundpolicy) || "No refunds".equals(refundpolicy) || refundpolicy==null)
			paymentData.setRefundpolicy(I18n.getString("ps.refund_policy"));
		else
			paymentData.setRefundpolicy(refundpolicy);
		
		paymentData.setCurrencytype(EventDB.getcurrency(eventid));
	    String taxtype=PaymentSettingsDB.getTaxType(eventid);
		if("byattendee".equals(taxtype)){
	    String tax=EventDB.getConfigVal(eventid,"event.tax.amount","");
	    String ccprocessingfee="";
		if("".equals(tax)){
			HashMap<String,String> taxccprocessmap=new HashMap<String,String>();
			taxccprocessmap=PaymentSettingsDB.getTaxAndCCProcessFee(eventid);
			if(taxccprocessmap.size()>0){
			   tax=taxccprocessmap.get("tax");
			   ccprocessingfee=taxccprocessmap.get("ccprocessingfee");
			}else{
				tax="0";
				ccprocessingfee="0";
			}	
		}else{
			try{
			String[] uriarr=GenUtil.strToArrayStr(tax, "+");
		  	tax=uriarr[0];
		  	ccprocessingfee=uriarr[1];
			}catch(Exception e){
				ccprocessingfee="0";	
			}
		  	if("".equals(ccprocessingfee)) ccprocessingfee="0";
		}
		if("".equals(tax) && !"eventlist".equals(purpose))
			paymentData.setCcprocessingfee("no");
		else paymentData.setCcprocessingfee("yes");
		paymentData.setTax(tax);
		paymentData.setCcfee(ccprocessingfee);
		}else
			paymentData.setCcprocessingfee("no");	
	
	}catch(Exception e){
		System.out.println("Exception occured in populatePaymentDataData");
	}
	}
	public String execute(){
		String eventid="";
		EventData edata=EventDB.getEventData(eid);
		mgrid=edata.getMgrId();
		currencySymbol=EventDB.getCurrencySymbol(eid);
		if("eventlist".equals(purpose)){
			eventid=populateMgrPaymentTypes(mgrid);
			if(!"".equals(eventid))
				populatePaymentDataData(eventid);
			else
				populatePaymentDataData(eid);
			/*eventid=EventDB.getManagerLatestEvent(mgrid);
			if(!"".equals(eventid)){
				populateMgrPaymentTypes(mgrid);
				populatePaymentDataData(eventid);
			}else{
				populatePaymentTypes(eid);
				populatePaymentDataData(eid);
			}	*/	
		}else{
			populatePaymentTypes(eid);
			populatePaymentDataData(eid);
			HashMap<String,String> currencydetails=TicketsDB.getCurrencyDetails(eid);
			evtlevel="200";
			if("Yes".equalsIgnoreCase(SpecialFeeDB.checkUpgradeStatus(eid, "CCProcessingFee", "Ticketing", evtlevel))){
			//specialfee=SpecialFeeDB.getNewSpecialFee(eid,mgrid,evtlevel,currencydetails.get("currencycode"));
				specialFeeMap=SpecialFeeDB.getNewSpecialFee(eid,mgrid,evtlevel,currencydetails.get("currencycode"));
				if(!specialFeeMap.isEmpty() && specialFeeMap.get(evtlevel) !=null)
					specialfee=specialFeeMap.get(evtlevel);
			}
		}
		Map session = ActionContext.getContext().getSession();
	   	if(session.get("SESSION_USER") == null)
	   		setMgrType("submgr");
	   	else
	   		setMgrType("mgr");
		return "success";
	}
	//This is USed to validate Paypal Email Address
	
	private String callPaypalTest(String email,String paypalValidationURL){
		String res="success";
		String option="Chained";
		CoreConnector cc=new CoreConnector(paypalValidationURL);
		HashMap <String,String>map=new HashMap<String,String>();
		map.put("email",email);
		map.put("currency", paymentData.getCurrencytype());
		map.put("payoption",option);
		cc.setArguments(map);
		res=cc.MGet();
		System.out.println("the response from paypal is"+res.trim());
		if(res==null)	
			res="success";
		return res.trim();
	}
	
	private String validatePaypalEmail(String email,String accounttype){
		ResourceBundle resourceBundle=I18n.getResourceBundle();
	 paymentData=PaymentSettingsDB.checkEmail(email,paymentData,mgrid);
	 String res="";
	// String paypalValidationURL=EbeeConstantsF.get("paypalValidationURL908","http://www.citypartytix.com/portal/utworks/paypalxtest.jsp");
	 String ser=EbeeConstantsF.get("serveraddress", "www.eventbee.com");
	 //String ser="www.citypartytix.com";
	 ser="http://"+ser+"/portal/utworks/paypalxtest.jsp";
	 String paypalValidationURL=ser;
	//#paypalValidationURL=http://184.72.247.248:8080/portal/utiltools/paypalxtest.jsp
	 if("".equals(paymentData.getEmail())){
			if(!"".equals(paypalValidationURL)){
				try{
				res=callPaypalTest(email,paypalValidationURL);
				System.out.println("paypalTest case: 1 response: "+res+" email: "+email);
				if("success".equalsIgnoreCase(res.trim()))
					PaymentSettingsDB.insertRecord(email,eid,"success","success",paymentData,mgrid);
				else{
					/*if("basic".equalsIgnoreCase(accounttype)) 
						paymentData=PaymentSettingsDB.checkManager(mgrid,paymentData,res.trim(),eid,email);
					else*/
						 PaymentSettingsDB.insertNoCard(paymentData,eid,res.trim(),mgrid);
				}
			 }catch(Exception e){
					  addFieldError("paymentData.paypal_merchant_accmail", resourceBundle.getString("ps.paypal.errmsg"));
				  }
			}
	}else{   
		 if("fail".equalsIgnoreCase(paymentData.getStatus())){
			      if(!"".equals(paypalValidationURL)){
			    	 try{					
			    	 res=callPaypalTest(email,paypalValidationURL);
					 System.out.println("paypalTest case: 2 response: "+res+" email: "+email);
					if("success".equalsIgnoreCase(res.trim()))
						PaymentSettingsDB.insertRecord(email,eid,"success","success",paymentData,mgrid);	
					else
					{
						/*if("basic".equalsIgnoreCase(accounttype))
							paymentData=PaymentSettingsDB.checkManager(mgrid,paymentData,res.trim(),eid,email);
						else*/
							 PaymentSettingsDB.insertNoCard(paymentData,eid,res.trim(),mgrid);
					}
			      }catch(Exception e){
						 addFieldError("paymentData.paypal_merchant_accmail",  resourceBundle.getString("ps.paypal.errmsg"));
					 } 
			      }
			     }
		 else{
			 try{
			 res=callPaypalTest(email,paypalValidationURL);
			 System.out.println("paypalTest case: 3 response: "+res+" email: "+email);
			 if("success".equalsIgnoreCase(res.trim()))
					PaymentSettingsDB.insertRecord(email,eid,"success","success",paymentData,mgrid);
			 else
				{
					/*if("basic".equalsIgnoreCase(accounttype))
						paymentData=PaymentSettingsDB.checkManager(mgrid,paymentData,res.trim(),eid,email);
					else*/
						PaymentSettingsDB.insertNoCard(paymentData,eid,res.trim(),mgrid);
					}
			 }catch(Exception e){
				 addFieldError("paymentData.paypal_merchant_accmail",  resourceBundle.getString("ps.paypal.errmsg"));
			 }
		 }
	 }
	 return res.trim();
	 }
	
	// end of paypal email validation
	
	public boolean validateData(){
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		int i=0;
		String accounttype=UpgradeEventDB.getAccountType(mgrid);
		String cardrequired="";
		if("1".equals(paymentData.getCurrencytype())){
			addFieldError("paymentData.currencytype", resourceBundle.getString("ps.payment.currency.is.not.selected.errmsg"));
		}
		if("on".equals(paymentData.getPaypalchecked())){
			paymentData.setPaypal_merchant_status("Enabled");
			String paypalEmail=paymentData.getPaypal_merchant_accmail().trim();
		if("".equals(paypalEmail)){
			addFieldError("paymentData.paypal_merchant_accmail", resourceBundle.getString("ps.paypal.email.is.empty.errmsg"));
			}
			if((!"".equals(paypalEmail))){
				 String res=validatePaypalEmail(paypalEmail,accounttype);
				if(!"success".equalsIgnoreCase(res.trim())){
					  /*if("basic".equalsIgnoreCase(accounttype) && !"eventlist".equals(purpose)){
						 if(!"Active".equalsIgnoreCase(paymentData.getCardstatus())){
							 cardrequired = DbUtil.getVal("select value from mgr_config where name='card.popup.required' and userid=CAST(? AS INTEGER)", new String[]{mgrid});
							 if(cardrequired==null)cardrequired="";
							 if(!"".equals(cardrequired) && !cardrequired.equals("Y"))
					 	      PaymentSettingsDB.insertNoCard(paymentData,eid,res.trim(),mgrid);
				            if(("".equals(cardrequired) || cardrequired.equals("Y")) && "mgr".equals(mgrType))
					 	     addFieldError("", "You have no cards");
						 }
					  }*/
					PaymentSettingsDB.insertPaypalXEvents(eid);
				}
			}
		}
		
		/*if("on".equals(paymentData.getGooglechecked())){
			paymentData.setGoogle_merchant_status("Enabled");
			//String accounttype=UpgradeEventDB.getAccountType(mgrid);
			if("basic".equalsIgnoreCase(accounttype) && !"eventlist".equals(purpose)){
				String cardstatus=CardProcessorDB.getManagerCCStatus(mgrid);
				if(!"Y".equalsIgnoreCase(cardstatus)){
				    	cardrequired = DbUtil.getVal("select value from mgr_config where name='card.popup.required' and userid=CAST(? AS INTEGER)", new String[]{mgrid});
					if((cardrequired==null || cardrequired.equals("Y")) && "mgr".equals(mgrType))
						addFieldError("","You have no cards");
				}
			}
			if("".equals(paymentData.getGoogle_merchant_id())){
			addFieldError("paymentData.google_merchant_id", "Google Merchant ID is empty");
			}
			if("".equals(paymentData.getGoogle_merchant_key())){
				addFieldError("paymentData.google_merchant_key", "Google Merchant Key is empty");
			}
		}*/
		if("on".equals(paymentData.getEbeecreditschecked())){
			paymentData.setEbeecredits_payment_status("Enabled");
			if("".equals(paymentData.getEbeecredits_payment_desc().trim())){
			addFieldError("paymentData.ebeecredits_payment_desc", resourceBundle.getString("ps.ebee.credits.payment.instructions.is.empt.errmsg"));
			}
		}
		if("on".equals(paymentData.getOtherchecked())){
			paymentData.setOther_payment_status("Enabled");
			//String accounttype=UpgradeEventDB.getAccountType(mgrid);
			/*if("basic".equalsIgnoreCase(accounttype) && !"eventlist".equals(purpose)){
                String cardstatus=CardProcessorDB.getManagerCCStatus(mgrid);
                if(!"Y".equalsIgnoreCase(cardstatus)){
				    	cardrequired = DbUtil.getVal("select value from mgr_config where name='card.popup.required' and userid=CAST(? AS INTEGER)", new String[]{mgrid});
					if((cardrequired==null || cardrequired.equals("Y")) && "mgr".equals(mgrType))
						addFieldError("","You have no cards");
				}
				}*/
			if("".equals(paymentData.getOther_payment_desc().trim())){
			addFieldError("paymentData.other_payment_desc", resourceBundle.getString("ps.other.payment.instructions.are.empty.errmsg"));
			}
		}
		if("on".equals(paymentData.getEventbeechecked())){
			paymentData.setEventbee_payment_status("Enabled"); 
			if("".equals(paymentData.getEventbeeVendor().trim())){
				addFieldError("paymentData.eventbeeVendor", resourceBundle.getString("ps.select.cc.processing.method.errmsg"));
			}
			if("authorize.net".equals(paymentData.getEventbeeVendor())){
			if("".equals(paymentData.getApiLoginId().trim())){
				addFieldError("paymentData.apiLoginId", resourceBundle.getString("ps.api.login.id.is.empty.errmsg"));
			}
			if("".equals(paymentData.getApiTransactionId().trim())){
				addFieldError("paymentData.apiTransactionId", resourceBundle.getString("ps.api.transaction.key.is.empty.errmsg"));
			}
		  }else if("braintree_manager".equalsIgnoreCase(paymentData.getEventbeeVendor())){
			  if("".equals(paymentData.getBraintreekey().trim()))
				  addFieldError("paymentData.braintreekey", resourceBundle.getString("ps.braintree.merchant.id.is.empty.errmsg"));
			  if("".equalsIgnoreCase(paymentData.getBrainPubKey().trim()))
				  addFieldError("paymentData.brainPubKey", resourceBundle.getString("ps.braintree.public.key.is.empty.errmsg"));
			  if("".equalsIgnoreCase(paymentData.getBrainPriKey().trim()))
				  addFieldError("paymentData.brainPriKey", resourceBundle.getString("ps.braintree.private.key.is.empty.errmsg"));
		  }else if("payulatam".equalsIgnoreCase(paymentData.getEventbeeVendor())){
			  if("".equals(paymentData.getPayuApiKey().trim()))
				  addFieldError("paymentData.payyouapikey", resourceBundle.getString("ps.payu.apikey.id.is.empty.errmsg"));
			  if("".equalsIgnoreCase(paymentData.getPayuApiLogin().trim()))
				  addFieldError("paymentData.payyouapilogin", resourceBundle.getString("ps.payu.apilogin.id.is.empty.errmsg"));
			  if("".equalsIgnoreCase(paymentData.getPayuAccountId().trim()))
				  addFieldError("paymentData.payyouaccountid", resourceBundle.getString("ps.payu.accountid.id.is.empty.errmsg"));
			  if("".equalsIgnoreCase(paymentData.getPayuMarchantId().trim()))
				  addFieldError("paymentData.payyoumarchantid", resourceBundle.getString("ps.payu.marchantid.id.is.empty.errmsg"));
			  String payuApiKey = paymentData.getPayuApiKey().trim();
			  String PayuApiLogin=paymentData.getPayuApiLogin().trim();
			  String payuAccountId =paymentData.getPayuAccountId().trim();
			  String payuMarchantId = paymentData.getPayuMarchantId().trim();
			  if(!"".equals(payuApiKey) && !"".equals(PayuApiLogin) && !"".equals(payuAccountId) && !"".equals(payuMarchantId)){
				  System.out.println("checking payulatam details");
				  String payUdetailsResult = PaymentSettingsDB.payUChecking(payuApiKey,PayuApiLogin,eid);
				  System.out.println("payUdetailsResult : "+payUdetailsResult);
				  System.out.println("payUdetailsResult : "+payUdetailsResult.trim());
				  if(!"success".equals(payUdetailsResult.trim()))
						addFieldError("paymentData.payyoumarchantid", resourceBundle.getString("ps.payu.invalid.credentials"));
				}
		  }else if("stripe".equals(paymentData.getEventbeeVendor())){
			  if("".equals(paymentData.getStripekey().trim()))
				  addFieldError("paymentData.stripekey",resourceBundle.getString("ps.stripe.merchant.api.key.is.empty.errmsg"));
		  }
		}
		if("".equals(paymentData.getPaypalchecked()) && "".equals(paymentData.getGooglechecked()) && "".equals(paymentData.getOtherchecked()) && "".equals(paymentData.getEventbeechecked()) && "".equals(paymentData.getEbeecreditschecked())){
			addFieldError("paymentData.error", resourceBundle.getString("ps.select.payment.method.errmsg"));
		}
		if("".equals(paymentData.getRefundpolicy().trim())){
			addFieldError("paymentData.refundpolicy", resourceBundle.getString("ps.refund.policy.conditions.is.empty.errmsg"));
		}
		if("yes".equalsIgnoreCase(paymentData.getCcprocessingfee()))
		{
		if("".equals(paymentData.getTax().trim())){
			paymentData.setTax("0");
		}else{
			try{
			Double tax=Double.parseDouble(paymentData.getTax().trim());
			}catch(Exception e){
				i++;
				System.out.println("1 the i value is : "+i);
			}
			}
		if("".equals(paymentData.getCcfee().trim())){
			paymentData.setCcfee("0");
		}else{
			try{
			Double ccfee=Double.parseDouble(paymentData.getCcfee().trim());
			}catch(Exception e){
					i++;
					System.out.println("2 the i value is : "+i);

			}
			}
		if(paymentData.getTax().contains("-"))
				i++;
		if(paymentData.getCcfee().contains("-"))
	            i++;		 
		if(i>0)	
			addFieldError("paymentData.error", resourceBundle.getString("ps.invalid.cc.processing.fee.errmsg"));
		}
			if(!getFieldErrors().isEmpty()){
			paymentSettingsErrorsExists = "true";
			return false;
		}

	return true;
	}
	
	public String Save(){
		boolean status=validateData();
		if(status){
				PaymentSettingsDB.Save(paymentData,eid,purpose);
				paymentSettingsErrorsExists = "false";
				PaymentSettingsDB.deleteduplicates(eid,paymentData);
				PaymentSettingsDB.updateCurrencyFee(eid,mgrid,paymentData.getCurrencytype());
				EventHelperDB.removeGLobalEventCache(eid, "remove", "ticketsettings");
				return "updated";
			}
			else{
				populateCurrencyTypes();
				return "fail";
			}
	}
	
	
	public String getCurrencySymbolOfCurrencyCode(){
		String currencysymbol=PaymentSettingsDB.getCurrencySymbolOfCCode(currencycode);
		JSONObject json=new JSONObject();
		try{
			json.put("currencysymbol", currencysymbol);
		}catch(Exception e){
			System.out.println("Exception occured in preparing json in getCurrencySymbolOfCurrencyCode");
		}
		msg=json.toString();
		return "jsondata";
	}
	
	public String agreeServiceFee(){
		SpecialFeeDB.insertSpecialFee(eid,evtlevel,specialfee,"CCProcessingFee");
		msg="success";
		System.out.println("in agreeServiceFee :: "+eid);
		return "jsondata";
	}
	
	
	public String getMgrType() {
		return mgrType;
	}
	public void setMgrType(String mgrType) {
		this.mgrType = mgrType;
	}
	public String getBrinbtreeScreen(){
		return "brinbtreeScreen";
	}
	public String getStripeScreen(){
		return "stripeScreen";
	}
}
