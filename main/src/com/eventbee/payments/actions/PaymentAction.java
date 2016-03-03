package com.eventbee.payments.actions;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.json.JSONObject;
import org.w3c.dom.Document;

import com.event.dbhelpers.EventDB;
import com.event.helpers.I18n;
import com.eventbee.beans.CreditCardData;
import com.eventbee.beans.Entity;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeCachingManager;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EventbeeLogger;
import com.eventbee.general.GenUtil;
import com.eventbee.general.formatting.CurrencyFormat;
import com.eventbee.payments.beans.CCData;
import com.eventbee.payments.dbhelpers.CardProcessorDB;
import com.eventbee.util.CoreConnector;
import com.eventbee.util.ProcessXMLData;
import com.membertasks.dbhelpers.AccountInfoDB;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.user.beans.UserData;



public  class PaymentAction extends ActionSupport implements ValidationAware{
	private CreditCardData creditCardData=new CreditCardData();
	private CCData carddata=new CCData();
	private String refId="";
	private String purpose="";
	private String seqId="";
	private String processType="";
	private String amount="";
	private String ccpageErrorsExists = "";
	private String msgType="ccpaymentpage";
	private List<Entity> cardtype = new ArrayList<Entity>();
	private List<Entity> months=new ArrayList<Entity>();
	private List<Entity> years=new ArrayList<Entity>();
	private List<Entity> countrylist=new ArrayList<Entity>();
	private String serveraddress="";
	private String sslserveraddress="";
	private String successurl="";
	private String json="";
	private String paytoken="";
	private String currency="";
	private String title="";
	private String lang="";
	private String message="";
	private String merchantid="";
	private String vendor="";
	private String softdis="";
	private String Internalref="";
	private String errorcode="";
	private String month="";
	
	

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public void populatecardType() {
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		cardtype.clear();
		cardtype.add(new Entity("visa", resourceBundle.getString("cc.visa.lbl")));
		cardtype.add(new Entity("mastercard", resourceBundle.getString("cc.mastercard.lbl")));
		cardtype.add(new Entity("amex", resourceBundle.getString("cc.amex.lbl")));
		cardtype.add(new Entity("discover", resourceBundle.getString("cc.discover.lbl")));
	}
	
	public void populateMonths(){
		months.clear();
		months.add(new Entity("01", "Jan"));
		months.add(new Entity("02", "Feb"));
		months.add(new Entity("03", "Mar"));
		months.add(new Entity("04", "Apr"));
		months.add(new Entity("05", "May"));
		months.add(new Entity("06", "Jun"));
		months.add(new Entity("07", "Jul"));
		months.add(new Entity("08", "Aug"));
		months.add(new Entity("09", "Sep"));
		months.add(new Entity("10", "Oct"));
		months.add(new Entity("11", "Nov"));
		months.add(new Entity("12", "Dec"));
	}
	
	public void populateYears(){
		years.clear();
		
		years.add(new Entity("2015", "2015"));
		years.add(new Entity("2016", "2016"));
		years.add(new Entity("2017", "2017"));
		years.add(new Entity("2018", "2018"));
		years.add(new Entity("2019", "2019"));
		years.add(new Entity("2020", "2020"));
		years.add(new Entity("2021", "2021"));
		years.add(new Entity("2022", "2022"));
		years.add(new Entity("2023", "2023"));
		years.add(new Entity("2024", "2024"));
		years.add(new Entity("2025", "2025"));
		years.add(new Entity("2026", "2026"));
		years.add(new Entity("2027", "2027"));
		years.add(new Entity("2028", "2028"));
		years.add(new Entity("2029", "2029"));

	}
	public void populateCountrylist(String vend){
		
		System.out.println("vend  list "+vend);
		if("Paypal".equalsIgnoreCase(vend))
		  	countrylist=new CardProcessorDB().getcountryNames();
		else
			countrylist=new CardProcessorDB().getBrainTreeCountryNames();
	}
	
	public void poplateDisplaydata(HashMap<String,String> discc)
	{
		setAmount(discc.get("amount"));
		setRefId(discc.get("refid"));
		setCurrency(discc.get("currency"));
		setProcessType(discc.get("paymode"));
		setTitle(discc.get("title"));
		setLang(discc.get("lang"));
		setSuccessurl(discc.get("callbackurl"));
		setMerchantid(discc.get("merchantid"));
		setMessage(discc.get("message"));
		setVendor(discc.get("vendor"));
		setPurpose(discc.get("purpose"));
		setSoftdis(discc.get("softdis"));
		setInternalref(discc.get("Internalref"));
			
	}
	
	public void populateData(){
		populatecardType();
		populateMonths();
		populateYears();
		//vendor="Paypal";
		populateCountrylist(vendor);
		populateserveraddress();
	}
	public String execute() {
		return "success";
	}
	public String getPaymentScreen(){		
		populateData();
		CurrencyFormat cf=new CurrencyFormat();
		amount=cf.getCurrency2decimal(amount);
		creditCardData.setAmount(amount);
		seqId=CardProcessorDB.getSeqID();
		creditCardData.setSeqId(seqId);
		HashMap<String,String> hm=getInitialData();
		CardProcessorDB.insertInitialData(hm);
		return "paymentScreen";
	}
	
	public boolean validateData(){
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		try{
	String numbervalidatestatus="";
	String currentdate=DateUtil.getCurrDBFormatDate();
	String[] currentdatearr=GenUtil.strToArrayStr(currentdate,"-"); 
	String currentyear=currentdatearr[0];
	String currentmonth=currentdatearr[1];
	if("1".equals(carddata.getCreditcardtype()))
		addFieldError("carddata.creditcardtype", resourceBundle.getString("cc.select.card.type"));
    if("".equals(carddata.getCardnumber().trim()) && getFieldErrors().isEmpty())
    	addFieldError("carddata.cardnumber", resourceBundle.getString("cc.card.empty.msg"));
    else if(getFieldErrors().isEmpty()){
    	numbervalidatestatus=validateNumber(carddata.getCardnumber().trim());
        if("notvalidnumber".equals(numbervalidatestatus))
    		addFieldError("carddata.cardnumber", resourceBundle.getString("cc.cc.no.num"));	
    }
    if(carddata.getCvvcode().trim().length()==0 && getFieldErrors().isEmpty())
    	addFieldError("carddata.cvvcode", resourceBundle.getString("cc.cvv.empty.msg"));
    else if(getFieldErrors().isEmpty()){
    	numbervalidatestatus=validateNumber(carddata.getCvvcode().trim());
    	if("notvalidnumber".equals(numbervalidatestatus))
    	addFieldError("carddata.cvvcode", resourceBundle.getString("cc.cvv.numeric.msg"));
    	else if(carddata.getCvvcode().trim().length()<3 || carddata.getCvvcode().trim().length()>4)
    	addFieldError("carddata.cvvcode", resourceBundle.getString("cc.cvv.min.digits"));	
    }
   if(Integer.parseInt(carddata.getExpireyear())<=Integer.parseInt(currentyear) && getFieldErrors().isEmpty()){
    	if(Integer.parseInt(carddata.getExpiremonth())<Integer.parseInt(currentmonth))
    		addFieldError("carddata.expiremonth", resourceBundle.getString("cc.invalid.expiration"));
    }
    if(!"".equals(carddata.getFirstname()) && getFieldErrors().isEmpty()){
    	if("".equals(carddata.getLastname()))
    		addFieldError("carddata.lastname", resourceBundle.getString("cc.lname.empty.msg"));
    }else if(getFieldErrors().isEmpty())
    	addFieldError("carddata.firstname", resourceBundle.getString("cc.fname.empty.msg"));
    
	if("".equals(carddata.getAddress().trim()) && getFieldErrors().isEmpty())
		addFieldError("carddata.address", resourceBundle.getString("cc.street.empty"));
	if("".equals(carddata.getCity().trim()) && getFieldErrors().isEmpty())
		addFieldError("carddata.city", resourceBundle.getString("cc.city.empty.msg"));
	if("1".equals(carddata.getCountry()) && getFieldErrors().isEmpty())
		addFieldError("carddata.country", resourceBundle.getString("cc.select.country"));
	if("Paypal".equalsIgnoreCase(vendor) && getFieldErrors().isEmpty()){
		if("1".equals(carddata.getState()) || "".equals(carddata.getState()))
			addFieldError("carddata.state", resourceBundle.getString("cc.select.state"));
	}
	if("".equals(carddata.getZip().trim()) && getFieldErrors().isEmpty())
		addFieldError("carddata.zip", resourceBundle.getString("cc.zip.empty.msg"));
	
	if(!getFieldErrors().isEmpty()){
		ccpageErrorsExists = "true";
		return false;
	}

	return true;
		}catch(Exception e){
			//System.out.println("In card validateData"+e.getMessage());
			return false;
		}
	}
	
	public String validateNumber(String number){
		if (number==null || number.length()==0)return "notvalidnumber";
        for (int i = 0; i < number.length(); i++) {
          if (!Character.isDigit(number.charAt(i)))
          return "notvalidnumber";
        }
		return "validnumber";
	}
	
	public String processPayment(){
		Map session = ActionContext.getContext().getSession();
		String userid="";
		if(session.get("SESSION_USER")!=null){
			UserData user=(UserData)session.get("SESSION_USER");
			userid=user.getUid();
		}
		HashMap<String,String> discc=new HashMap<String,String>();
		boolean status=validateData();
		String cardcount="0";
		if(status){
		 if(EbeeCachingManager.ebeeCache.get("braintree_overhits_"+paytoken)==null){
			discc=CardProcessorDB.getCardDisplaydata(paytoken);
			if(discc.get("refid")==null || "".equals(discc.get("refid"))) return "success";
			if(("".equals(userid) || !discc.get("refid").equals(userid)) && !"addattendeeccpayment".equalsIgnoreCase(discc.get("purpose"))) return "success";
			if(!"addattendeeccpayment".equalsIgnoreCase(discc.get("purpose"))){
			cardcount=DbUtil.getVal("select count(*) from manager_card_authentication where mgr_id=? and to_char(setupdate,'YYYY-MM-DD')=to_char(now(),'YYYY-MM-DD')", new String[]{discc.get("refid")});
			 if(cardcount==null)cardcount="0";
			 if(Integer.parseInt(cardcount)>=3){
				 EbeeCachingManager.ebeeCache.put("braintree_overhits_"+paytoken, "true");
				 System.out.println("cardcount in processPayment section is:"+cardcount);
				 return "success";
			 }
			}
			}else{
				return "success";
			}
		
		//discc=CardProcessorDB.getCardDisplaydata(paytoken);
		poplateDisplaydata(discc);		
		CoreConnector cc1=null;
		HashMap<String,String> hm=getCardData();
		CardProcessorDB.updateCCInitialData(hm,seqId);
		//String vendor=EbeeConstantsF.get(purpose+".PAYMENT_PROCESS_TYPE","braintree");
		Map resMap=null;
		String data="";
		//vendor = "braintree";
		String serverAddress = "http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		if(vendor.equalsIgnoreCase("paypal")){
			try{
				 cc1=new CoreConnector(EbeeConstantsF.get("CONNECTING_PAYPAL_URL",serverAddress+"/paypal/paypal.jsp"));
				 cc1.setArguments(fillParamMap(hm,seqId));
				 cc1.setTimeout(30000);
				 data=cc1.MGet();
				 String [] xmltags={"Ack","AVSCode","CVV2Code","TransactionID"};
				 Document doc=ProcessXMLData.getDocument(new StringBufferInputStream(data));
				 doc.getDocumentElement ().normalize ();
				 resMap=ProcessXMLData.getProcessedXMLData(doc,"DoDirectPaymentResponse",xmltags);
				 if("Success".equalsIgnoreCase(resMap.get("Ack")+"")){
					 resMap.put("Status", "success");
				 }
			}catch(Exception e){
				System.out.println("Exception in PaymentAction.java paypal: "+e.getMessage());
				errorcode="505";
				return "error";
			 }
		}else{
			try{
				cc1=new CoreConnector(EbeeConstantsF.get("CONNECTING_BRAINTREE_URL",serverAddress+"/main/payments/braintree/BraintreePayment.jsp"));
				cc1.setArguments(fillParamMap(hm,seqId));
		       	cc1.setTimeout(80000);
			    data=cc1.MGet();
		        String [] xmltags={"Status","TransactionID","VaultId","VaultToken"};
		        Document doc=ProcessXMLData.getDocument(new StringBufferInputStream(data));
			    doc.getDocumentElement ().normalize ();
		        resMap=ProcessXMLData.getProcessedXMLData(doc,"Response",xmltags);
			}catch(Exception e){
				System.out.println("Exception in PaymentAction.java Braintree: "+e.getMessage());
				errorcode="505";
				return "error";
			 }
		}
		
		System.out.println("resMap: "+resMap);
		
		if("success".equalsIgnoreCase((String)resMap.get("Status"))){
			CardProcessorDB.updateCCStatusData(seqId,resMap);
			CardProcessorDB.updateTokentrans(paytoken);
			System.out.println("month in success of paymentaction:"+month);
			System.out.println("purpose in success of paymentaction:"+discc.get("purpose"));
			System.out.println("refid in success of paymentaction:"+discc.get("refid"));
			if("payinvoice".equals(discc.get("purpose"))){
				AccountInfoDB.insertCCResponseData(seqId,discc.get("refid"));
				AccountInfoDB.updateInvoiceAmount(discc.get("refid"),month,discc.get("amount"));
			}else if("addattendeeccpayment".equals(discc.get("purpose")))
				AccountInfoDB.insertAddAttendeeData(paytoken);
			else if("beecreditsquota".equals(discc.get("purpose")))
				AccountInfoDB.insertBeeCredits(seqId, discc.get("refid"));
			else
				AccountInfoDB.insertCCResponseData(seqId,discc.get("refid"));
			
        }else{
        	String[] errorxmltags={"ErrorCode"};
        	Document doc=ProcessXMLData.getDocument(new StringBufferInputStream(data));
		    doc.getDocumentElement ().normalize ();
	        resMap=ProcessXMLData.getProcessedXMLData(doc,"Response",errorxmltags);
        	System.out.println("resMap is: "+resMap);
	        errorcode=(String)resMap.get("ErrorCode");
            System.out.println("errorcode is: "+errorcode);
        	return "error";
        }
        setSeqId(seqId);
		populateData();
		return "success";
		}else{
			System.out.println("failure case");
			return "fail";
		}
			
	}
	public  HashMap<String,String> getInitialData(){
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("AMOUNT",amount);
		hm.put("REFID",refId);
		hm.put("SEQID",seqId);
		hm.put("PURPOSE",purpose);
		hm.put("PROCESSTYPE",processType);
		hm.put("PAYTOKEN",paytoken);
	    return hm;
	}
	public  HashMap<String,String> getCardData(){
		HashMap<String,String> hm=new HashMap<String,String>();
	    hm.put("CREDITCARDTYPE",carddata.getCreditcardtype());
		hm.put("CARDNUMBER",carddata.getCardnumber());
		hm.put("CVVCODE",carddata.getCvvcode());
		hm.put("EXPIREMONTH",carddata.getExpiremonth());
		hm.put("EXPIREYEAR",carddata.getExpireyear());
		hm.put("FIRSTNAME",carddata.getFirstname());
		hm.put("LASTNAME",carddata.getLastname());
		hm.put("ADDRESS",carddata.getAddress());
		hm.put("CITY",carddata.getCity());
		hm.put("COUNTRY",carddata.getCountry());
		hm.put("STATE",carddata.getState());
		hm.put("ZIP",carddata.getZip());
		return hm;
	}
	private Map fillParamMap(HashMap<String,String> hm,String seqId){
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"PaypalProcessing.java","fillParamMap()","parammap",null);
		Map paramMap=new HashMap();
		CurrencyFormat cf=new CurrencyFormat();
		HashMap<String,String> hmap=CardProcessorDB.getDefaultData(seqId); 
		paramMap.put("GrandTotal",cf.getCurrency2decimal(hmap.get("AMOUNT")));
		paramMap.put("ref_id",hmap.get("REFID"));
	    paramMap.put("paytype",hmap.get("PROCESSTYPE"));   
	    paramMap.put("internalrefid",Internalref);
	    paramMap.put("CreditCardNumber",hm.get("CARDNUMBER"));
		paramMap.put("CVVCode",hm.get("CVVCODE"));
		paramMap.put("CreditCardType",hm.get("CREDITCARDTYPE"));
		paramMap.put("ExpMonth",hm.get("EXPIREMONTH"));
		paramMap.put("ExpYear",hm.get("EXPIREYEAR"));
		paramMap.put("PayerFirstName",hm.get("FIRSTNAME"));
		paramMap.put("PayerLastName",hm.get("LASTNAME"));
		paramMap.put("Street1",hm.get("ADDRESS"));
		paramMap.put("CityName",hm.get("CITY"));
		paramMap.put("CountryName",hm.get("COUNTRY"));
		paramMap.put("StateOrProvince",hm.get("STATE"));
		paramMap.put("PostalCode",hm.get("ZIP"));
		if(currency==null||"".equals(currency))
			currency="USD";
        paramMap.put("CurrencyCode",currency);
		String environment=EbeeConstantsF.get("EBPAY_ENVIRONMENT","sandbox");
		paramMap.put("Environment",environment);
        paramMap.put("softdescriptor", softdis);
        paramMap.put("InternalRef",Internalref);
        paramMap.put("purpose",purpose);
        System.out.println("** BRAINTREE_PAYMENT_SUBMIT_DATA FOR SEQID: "+seqId+" DATA: "+paramMap.toString());
		return paramMap;
	}
	
	public void populateserveraddress()
	{setSslserveraddress(EbeeConstantsF.get("sslserveraddress","https://www.eventbee.com"));
	  setServeraddress("http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com"));
	}
	public void updateUpgrade()
	{	
		CardProcessorDB.updateUpgradePurpose(seqId,refId);
		
	}	
	public void updateBuyEmail()
	{
		CardProcessorDB.updateBuyEmailPurpose(seqId,refId,amount);
	}
	public String onSuccess()
	{     		
		successurl=CardProcessorDB.getsuccessurl(paytoken);
		if(successurl.indexOf("seqId")==-1)
		{  if(successurl.indexOf("?")!=-1)  
			successurl=successurl+"&seqId="+seqId;
		    else
		    successurl=successurl+"?seqId="+seqId;
		}
		successurl=successurl+"&paytoken="+paytoken;
		System.out.println("sucessurl:"+successurl);
		return "onsuccess";
	}		
	public String getccPaymentScreen(){	
		Map session = ActionContext.getContext().getSession();
		String userid="";
		if(session.get("SESSION_USER")!=null){
			UserData user=(UserData)session.get("SESSION_USER");
			userid=user.getUid();
			System.out.println("userid in getccPaymentScreen ::"+userid);
		}
		HashMap<String,String> discc=CardProcessorDB.getCardDisplaydata(paytoken);
		if(discc.get("refid")==null || "".equals(discc.get("refid"))) return "success";
		if(("".equals(userid) || !discc.get("refid").equals(userid)) && !"addattendeeccpayment".equalsIgnoreCase(discc.get("purpose"))) return "success";
		if(!"addattendeeccpayment".equalsIgnoreCase(discc.get("purpose"))){
		String cardcount=DbUtil.getVal("select count(*) from manager_card_authentication where mgr_id=? and to_char(setupdate,'YYYY-MM-DD')=to_char(now(),'YYYY-MM-DD')", new String[]{discc.get("refid")});
		 if(cardcount==null)cardcount="0";
	     if(Integer.parseInt(cardcount)>=3){
			 System.out.println("In getccPaymentScreen cardcount is:"+cardcount);
			 return "success";
		 }
		}
		poplateDisplaydata(discc);
		populateData();
		CurrencyFormat cf=new CurrencyFormat();
		amount=cf.getCurrency2decimal(amount);
		month=month;
		creditCardData.setAmount(amount);
		if(seqId==null ||"".equals(seqId))
		seqId=CardProcessorDB.getSeqID();
		creditCardData.setSeqId(seqId);
		System.out.println("seqId: "+seqId+" and amount: "+amount);
		HashMap<String,String> hm=getInitialData();
        CardProcessorDB.insertInitialData(hm);
        
		return "ccpaymentScreen";
	}
    
      
	public String  gethtppsserveraddress()
	{  setSslserveraddress(EbeeConstantsF.get("sslserveraddress","https://www.eventbee.com"));
	   setServeraddress("http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com"));
	  // HashMap<String,String> hm=new  HashMap<String,String>();
	   JSONObject hm= new JSONObject();
	  try{ hm.put("sslserv",getSslserveraddress());
	    json=hm.toString();
	  }
	  catch(Exception e)
	  { System.out.println("error in geting server address:"+e.getMessage());}
	  
	    System.out.println(json);
	    return "json";
	}
	
	
	
	public List<Entity> getCardtype() {
		return cardtype;
	}
	public void setCardtype(List<Entity> cardtype) {
		this.cardtype = cardtype;
	}
	public List<Entity> getMonths() {
		return months;
	}
	public void setMonths(List<Entity> months) {
		this.months = months;
	}
	public List<Entity> getYears() {
		return years;
	}
	public void setYears(List<Entity> years) {
		this.years = years;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public CCData getCarddata() {
		return carddata;
	}
	public void setCarddata(CCData carddata) {
		this.carddata = carddata;
	}
	public String getCcpageErrorsExists() {
		return ccpageErrorsExists;
	}
	public void setCcpageErrorsExists(String ccpageErrorsExists) {
		this.ccpageErrorsExists = ccpageErrorsExists;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public String getServeraddress() {
		return serveraddress;
	}
	public void setServeraddress(String serveraddress) {
		this.serveraddress = serveraddress;
	}
	public String getSslserveraddress() {
		return sslserveraddress;
	}
	public void setSslserveraddress(String sslserveraddress) {
		this.sslserveraddress = sslserveraddress;
	}
	public String getSuccessurl() {
		return successurl;
	}
	public void setSuccessurl(String successurl) {
		this.successurl = successurl;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	public List<Entity> getCountrylist() {
		return countrylist;
	}
	public void setCountrylist(List<Entity> countrylist) {
		this.countrylist = countrylist;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public CreditCardData getCreditCardData() {
		return creditCardData;
	}
	public void setCreditCardData(CreditCardData creditCardData) {
		this.creditCardData = creditCardData;
	}
	
	public String getPaytoken() {
		return paytoken;
	}
	public void setPaytoken(String paytoken) {
		this.paytoken = paytoken;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	public String getSoftdis() {
		return softdis;
	}
	public void setSoftdis(String softdis) {
		this.softdis = softdis;
	}
	public String getInternalref() {
		return Internalref;
	}

	public void setInternalref(String internalref) {
		Internalref = internalref;
	}

	
	
}
