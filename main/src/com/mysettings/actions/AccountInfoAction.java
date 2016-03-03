package com.mysettings.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.membertasks.dbhelpers.AccountInfoDB;
import com.event.dbhelpers.EventDB;
import com.event.helpers.I18n;
import com.event.helpers.InvoiceExportHelper;
import com.eventbee.beans.CreditCardData;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.EventBeeValidations;
import com.eventbee.general.ShortUrlPattern;
import com.eventbee.general.formatting.CurrencyFormat;
import com.eventbee.payments.dbhelpers.CardProcessorDB;
import com.membertasks.actions.MemberTasksWrapper;
import com.mysettings.beans.AccountInfo;
import com.opensymphony.xwork2.ActionContext;

public class AccountInfoAction extends MemberTasksWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4302857469952702122L;
	private AccountInfo accountInfo;
	private List<Entity> countrylist = new ArrayList<Entity>();
    private String accountInfoErrorsExists = "";
	private String msgType="";
	private String state="";
	private String seqId="";
	private String accounttype="";
	private String msg="";
	private CreditCardData creditCardData=new CreditCardData();
	private HashMap<String,String> cardinfo=new HashMap<String,String>();
	private String payamount="";
	private String paytoken="";
	private String totalbeecredits="";
	private String availablebeecredits="";
	private HashMap<String,String> displaybcredits=new HashMap<String,String>();
	private String jsondata="";
	private ArrayList Fields=new ArrayList();
	private ArrayList<HashMap<String,String>> mapList=new ArrayList<HashMap<String,String>>();
	private HashMap<String,String> invoicesummary=new HashMap<String,String>();
	private boolean export=false;
	private String excel="";
	private String exporttype="";
	private String sortDirection="";
	private String sortField="";
	private HashMap induemap=new HashMap();
	private HashMap clearedmap=new HashMap();
	private String invoicemnth="";
	private String amount="";
	private String rtype="";
	private int size=0;
	private String purpose="";
	private HashMap<String,String> paypalMap=new HashMap<String,String>();
	private String url="";
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HashMap<String, String> getPaypalMap() {
		return paypalMap;
	}

	public void setPaypalMap(HashMap<String, String> paypalMap) {
		this.paypalMap = paypalMap;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getRtype() {
		return rtype;
	}

	public void setRtype(String rtype) {
		this.rtype = rtype;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public HashMap<String, String> getDisplaybcredits() {
		return displaybcredits;
	}

	public void setDisplaybcredits(HashMap<String, String> displaybcredits) {
		this.displaybcredits = displaybcredits;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public void prepare() throws Exception {
		try {
			super.prepare();
			populateCountrylist();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public List<Entity> getCountrylist() {
		return countrylist;
	}

	public void setCountrylist(List<Entity> countrylist) {
		this.countrylist = countrylist;
	}

	/**
	 * @param accountInfo
	 *            the accountInfo to set
	 */
	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}

	/**
	 * @return the accountInfo
	 */
	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public void populateCountrylist() {
		countrylist = EventDB.getCountries();
	}

    public String execute() {
    	if("".equals(userId) || userId==null){
			System.out.println("--- mgrId EMPTY or NULL case --- in MyEventsSummaryAction execute mgrId: "+userId);
			return "error";
		}
    	System.out.println("AccountInfo Action execute method user id value is"
				+ userId+","+accountInfoErrorsExists);
		accountInfo = AccountInfoDB.getUserInfo(userId);
		cardinfo=AccountInfoDB.getCardInfo(userId);
		state=accountInfo.getState();
		accounttype=AccountInfoDB.getAccounttype(userId);
		displaybcredits=AccountInfoDB.getAvailableBeeCredits(userId);
		totalbeecredits=displaybcredits.get("totalcredits");
		availablebeecredits=displaybcredits.get("availablebcredits");
		String userName=DbUtil.getVal("select getMemberPref(?||'','pref:myurl','') as username",new String[]{userId});
		String url=ShortUrlPattern.get(userName)+"/boxoffice";
		if(totalbeecredits==null)totalbeecredits="0.00";
		if(availablebeecredits==null)availablebeecredits="0.00";
		populateCountrylist();
		
		induemap =AccountInfoDB.getInDueDetails("indue",userId);
		clearedmap= AccountInfoDB.getInDueDetails("clear",userId);
		amount= AccountInfoDB.getInvoiceSum(userId);
		if(amount==null)amount="0.00";
		System.out.println("amount in execute method:"+amount);
		size=((ArrayList)induemap.get("details")).size();
		invoicesummary.put("invoiceindue",((ArrayList)induemap.get("details")).size()+"");
		invoicesummary.put("invoicepaid",((ArrayList)clearedmap.get("details")).size()+"");
		JSONObject jsonobj =new JSONObject();		
		try{
		jsonobj.put("induedetail",induemap);
		jsonobj.put("cleardetail",clearedmap);
		jsonobj.put("boxURL",url);
		}catch(Exception e){}
		jsondata=jsonobj.toString();
		return INPUT;
	}

	public String save() {
		System.out.println("enter in to save method");
		try {
			boolean status = validateData();
			if (status) {
				System.out.println("AccountInfo Action save method");
				AccountInfoDB.UpdateAccountInfo(userId, accountInfo);
				accountInfoErrorsExists = "false";
			} else {
				populateCountrylist();
				return "validationerrors";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		msgType="contactinfo";
		return SUCCESS;
	}
	
	public String savepassword(){
		System.out.println("savepassword method");
		try{
			boolean passwordstatus=validatePassword();
			if(passwordstatus){
				System.out.println("In savepassword method");
				AccountInfoDB.UpdatePassword(userId, accountInfo);
				accountInfoErrorsExists = "false";	
			}else
				return "validationerrors";
		}catch(Exception e){
			System.out.println("Exception occured in savepassword:"+e.getMessage());
		}
		msgType="passwordinfo";
		return SUCCESS;
	}
	
	public String checkPaypalStatus(){
		System.out.println("userId in checkPaypalStatus"+userId);
		System.out.println("seqId in checkPaypalStatus"+seqId);
		String paypalstatus=AccountInfoDB.getPaypalStatus(userId,seqId);
		System.out.println("paypalstatus in checkPaypalStatus is:"+paypalstatus);
		if("Completed".equals(paypalstatus)){
			msgType="invoicemessage";
		    return SUCCESS;
		}else if("Pending".equalsIgnoreCase(paypalstatus)){
			msgType="invoicemessage";
			addFieldError("accountInfo.paypalinfo",
		  			"Your PayPal payment is in process. Please revisit after some time.");
			if (!getFieldErrors().isEmpty())			
				accountInfoErrorsExists = "true";
			return "validationerrors";
		}else
			return "validationerrors";
	}
	
	
	public String closePaypalScreen(){
		System.out.println("in closePaypalScreen userId is:"+userId);
		System.out.println("in closePaypalScreen seqId is:"+seqId);
		return "closepaypalscreen";
	}
	
	public void insertManagerccData(){
	    AccountInfoDB.insertCCResponseData(seqId,userId);
	}
	
	public void updateBeeCredits(){
		AccountInfoDB.insertBeeCredits(seqId,userId);
		 //AccountInfoDB.insertCCResponseData(seqId,userId);
	}
	
	public void updateInvoiceCredits(){
		AccountInfoDB.insertCCResponseData(seqId,userId);
		AccountInfoDB.updateInvoiceAmount(userId,invoicemnth,amount);
	}
	
	public String getBeeCreditsHistory(){
		msg=AccountInfoDB.getBCreditsHistoryJson(userId);
		return "beecreditshistory";
	}	
	
	public void updateAmount(){
	    System.out.println("seqId is:"+seqId);
	    System.out.println("paytoken is:"+paytoken);
	    System.out.println("payamount is:"+payamount);
	    AccountInfoDB.updatePaymentAmount(seqId,paytoken,payamount);
	}
	
	public String insertPaypalData(){
		System.out.println("In insertPaypalData method for userId:"+userId+ " :: amount :: "+amount+" :: month ::"+invoicemnth);
		String sid=AccountInfoDB.getPaypalSeqID();
		System.out.println("SeqId in insertPaypalData for userId ::"+userId+" :: purpose ::"+purpose +" :: seqid ::"+sid);
		seqId=EncodeNum.encodeNum(sid);
		msg=AccountInfoDB.insertPaypalDataToDB(amount,invoicemnth,purpose,userId,seqId);
		return "beecreditshistory";
		
	}
	
	public String getPaypalPayment(){
		System.out.println("In getPaypalPayment method for userId:"+userId+ " :: seqId :: "+seqId);
	    paypalMap=AccountInfoDB.getPaypalDataMap(seqId,userId);
		System.out.println("paypalmap is:"+paypalMap);
		return "paypalscreen";
	}
	
	public boolean validateData() {
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		try {
			isValidEmail(accountInfo.getEmail());
			if ("".equals(accountInfo.getFirstName())) {
				addFieldError("accountInfo.newPassword", resourceBundle.getString("ai.first.name.rqd.msg"));
			}
			if ("".equals(accountInfo.getLastName())) {
				addFieldError("accountInfo.newPassword", resourceBundle.getString("ai.last.name.rqd.msg"));
			}
			/*if ("".equals(accountInfo.getGender())) {
				addFieldError("accountInfo.newPassword", "Gender is required");
			}*/
			if (!getFieldErrors().isEmpty()) {				
				msgType="contactinfo";
				accountInfoErrorsExists = "true";
				return false;
			}

		} catch (Exception e) {
			addFieldError("accountInfo.errorsExists", resourceBundle.getString("ai.some.errors.msg"));
			return false;
		}
		return true;
	}
     public boolean validatePassword() {
    	 ResourceBundle resourceBundle=I18n.getResourceBundle();
    	 String password=accountInfo.getNewPassword();
    	 if("".equals(password) || password.length()==0 || password==null)
			addFieldError("accountInfo.newPassword", resourceBundle.getString("ai.pwd.not.empty.msg"));
		 
    	 if(password.length()>0 && password.length()<4)
    		 addFieldError("accountInfo.newPassword", resourceBundle.getString("ai.pwd.mustbe.grt.msg"));
    
    	 if(password.length()>20)
    		 addFieldError("accountInfo.newPassword", resourceBundle.getString("ai.pwd.mustbe.less.msg"));
    	 
    	 if (!"".equals(password)) {
			String newPassword = password;
			String rePassword = accountInfo.getRePassword();
			System.out.println("newPassword" + newPassword + "         "
					+ "rePassword" + rePassword);
			if (!newPassword.equals(rePassword)) {
				addFieldError("accountInfo.newPassword", resourceBundle.getString("ai.pwd.match.msg"));
			}
		}
		if (!getFieldErrors().isEmpty()) {				
			msgType="passwordinfo";
			accountInfoErrorsExists = "true";
			return false;
		}
		return true;
	}
	public void isValidEmail(String email) {
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		try {
			if (email == null || "".equals(email.trim())) {
				addFieldError("accountInfo.email", resourceBundle.getString("global.email.is.empty.errmsg"));

			} else {
				  boolean flag=EventBeeValidations.isValidFromEmail(email);
					if(!flag)
					addFieldError("accountInfo.email", resourceBundle.getString("ai.invalid.email.msg"));
				}
		}// End of try
		catch (Exception e) {

		}
	}
	
	
   public String getInDuePopupDetails(){		
	  try{
		  System.out.println("get indue popup details: userid:"+userId+"invoice month:"+invoicemnth+"rtype:"+rtype);
		  HashMap hashmap=new HashMap();
		  if(export)
			  hashmap=AccountInfoDB.getExportInvoiceDetailsJson(userId,invoicemnth,rtype); 
		  else
		      hashmap=AccountInfoDB.getInvoiceDetailsJson(userId,invoicemnth,rtype);
		JSONObject jsonobj =new JSONObject();		
		try{
		jsonobj.put("monthdetails",hashmap);
		}catch(Exception e){}
		msg=jsonobj.toString();		
		mapList=(ArrayList<HashMap<String,String>>)hashmap.get("monthlyinvdetails");
		Fields=(ArrayList<HashMap<String,String>>)hashmap.get("fields");		
		}catch(Exception e){}			
		if(export){
		excel=InvoiceExportHelper.exportToExcel(mapList, Fields,exporttype);
		if(exporttype.equals("excel"))
			return "exporttoexcel";
		else if(exporttype.equals("csv"))
			return "exporttocsv";		
	}
		return "beecreditshistory";
	}
   
   public String getCardStatus(){
	   System.out.println("the user id from jsp ia::"+userId);
	   String cctype=AccountInfoDB.getCCType(userId);
	   System.out.println("the cc type is::"+cctype);
	   if("".equals(cctype.trim()))cctype="nocard";
	   JSONObject js=new JSONObject();
	   try{
	   js.put("cctype",cctype);
	   }catch(Exception e){
		   System.out.println("Exception Occured in getCardStatus Method");
	   }
	   msg=js.toString();
   return "beecreditshistory";
   } 
   
	
	
	public String getExcel() {
		return excel;
		
	}

	public void setExcel(String excel) {
		this.excel = excel;
	}

	public String getExporttype() {
		return exporttype;
	}

	public void setExporttype(String exporttype) {
		this.exporttype = exporttype;
	}

	public ArrayList getFields() {
		return Fields;
	}

	public void setFields(ArrayList fields) {
		Fields = fields;
	}

	public ArrayList<HashMap<String, String>> getMapList() {
		return mapList;
	}

	public void setMapList(ArrayList<HashMap<String, String>> mapList) {
		this.mapList = mapList;
	}

	/*public String getInvoiceDetails(){
		
		return "input";
	}*/
	 
	public String getAccountInfoErrorsExists() {
		return accountInfoErrorsExists;
	}

	public void setAccountInfoErrorsExists(String accountInfoErrorsExists) {
		this.accountInfoErrorsExists = accountInfoErrorsExists;
	}

	public HashMap<String, String> getCardinfo() {
		return cardinfo;
	}

	public void setCardinfo(HashMap<String, String> cardinfo) {
		this.cardinfo = cardinfo;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
    public CreditCardData getCreditCardData() {
		return creditCardData;
	}

	public void setCreditCardData(CreditCardData creditCardData) {
		this.creditCardData = creditCardData;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPayamount() {
		return payamount;
	}

	public void setPayamount(String payamount) {
		this.payamount = payamount;
	}

	public String getPaytoken() {
		return paytoken;
	}

	public void setPaytoken(String paytoken) {
		this.paytoken = paytoken;
	}

	public String getTotalbeecredits() {
		return totalbeecredits;
	}

	public void setTotalbeecredits(String totalbeecredits) {
		this.totalbeecredits = totalbeecredits;
	}

	public String getAvailablebeecredits() {
		return availablebeecredits;
	}

	public void setAvailablebeecredits(String availablebeecredits) {
		this.availablebeecredits = availablebeecredits;
	}

	public String getJsondata() {
		return jsondata;
	}

	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public HashMap getInduemap() {
		return induemap;
	}

	public void setInduemap(HashMap induemap) {
		this.induemap = induemap;
	}

	public HashMap getClearedmap() {
		return clearedmap;
	}

	public void setClearedmap(HashMap clearedmap) {
		this.clearedmap = clearedmap;
	}

	public HashMap<String, String> getInvoicesummary() {
		return invoicesummary;
	}

	public void setInvoicesummary(HashMap<String, String> invoicesummary) {
		this.invoicesummary = invoicesummary;
	}

	public String getInvoicemnth() {
		return invoicemnth;
	}

	public void setInvoicemnth(String invoicemnth) {
		this.invoicemnth = invoicemnth;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	

	
}
