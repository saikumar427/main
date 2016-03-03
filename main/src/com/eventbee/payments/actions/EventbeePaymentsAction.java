package com.eventbee.payments.actions;


import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.user.beans.UserData;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.payments.dbhelpers.CardProcessorDB;
public class EventbeePaymentsAction {



	private String paytoken="";
	private String currency="";
	private String paymode="";
	private String amount="";
	private String title = "";
	private String refId="";
	private String lang="";
	private String merchantid="";
	private String callbackurl="";
	private String message = "";
	private String json="";
	private String purpose="";
	private String softdis="";
	private String vendor="";
	private String Internalref="";
	private String serveraddress="";
	private String sslserveraddress="";
	private String month="";
	
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getPaykey(){
		Map session = ActionContext.getContext().getSession();
		JSONObject jsonobj= new JSONObject();
		String userid="";
		if(session.get("SESSION_USER")!=null){
			UserData user=(UserData)session.get("SESSION_USER");
			userid=user.getUid();
		}
		 HashMap<String,String> enchm=getsubmitedData();
		 HashMap<String,String> dechm=getDescodeSubmitedData(enchm);
		 String mgrid=dechm.get("refid");
		 System.out.println("dechm::"+dechm);
		 if(("".equals(userid) || !mgrid.equals(userid)) && !"addattendeeccpayment".equalsIgnoreCase(dechm.get("purpose"))){
			 try{
				 System.out.println("**** userid and map userid are not same case ****");
				 jsonobj.put("paytoken","");
			     jsonobj.put("sslserv",getSslserveraddress());
			     jsonobj.put("month", dechm.get("month"));
			     json=jsonobj.toString();
			 }catch(Exception e){System.out.println("error in geting paykey:"+e.getMessage());}
			 return "json";
		 }
		 if(!"addattendeeccpayment".equalsIgnoreCase(dechm.get("purpose"))){
		 String cardcount=DbUtil.getVal("select count(*) from manager_card_authentication where mgr_id=? and to_char(setupdate,'YYYY-MM-DD')=to_char(now(),'YYYY-MM-DD')", new String[]{mgrid});
		 if(cardcount==null)cardcount="0";
		 System.out.println("month in dechm::"+dechm.get("month"));
		 if(Integer.parseInt(cardcount)>=3){
			 try{
			 System.out.println("In getPaykey cardcount is:"+cardcount);	 
			 jsonobj.put("paytoken","");
		     jsonobj.put("sslserv",getSslserveraddress());
		     jsonobj.put("month", dechm.get("month"));
		     json=jsonobj.toString();
			 }catch(Exception e){System.out.println("error in geting paykey:"+e.getMessage());}
		 System.out.println(json);
		 return "json";
		 }
		 }
		 paytoken=CardProcessorDB.insertPayData(dechm);	
		 gethtppsserveraddress();
		  
		  try{ jsonobj.put("paytoken",paytoken);
		       jsonobj.put("sslserv",getSslserveraddress());
		       jsonobj.put("month", dechm.get("month"));
		       json=jsonobj.toString();
		      
		    }
		  catch(Exception e)
		  { System.out.println("error in geting paykey:"+e.getMessage());}
		  
		    System.out.println(json);
		    return "json";
		}
   
	public  HashMap<String,String> getsubmitedData(){
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("currency",currency);
		hm.put("paymode",paymode);
		hm.put("lang",lang);
		hm.put("merchantid",merchantid);
		hm.put("message",message);
		hm.put("softdis",softdis);
		hm.put("purpose", purpose);
		hm.put("callbackurl",callbackurl);
		hm.put("vendor", vendor);
		hm.put("amount",amount);
		hm.put("refid",refId);
		hm.put("Internalref",Internalref);
		//hm.put("month", month);
	    return hm;
	}
	public  HashMap<String,String>getDescodeSubmitedData(HashMap<String,String> hm)
	{  System.out.println("yes comming");
		HashMap<String,String> dechm =new HashMap<String,String>();
		for (Map.Entry<String, String> entry : hm.entrySet()) {
		String key="";
		String value="";
		key= entry.getKey();
		value=entry.getValue();
	    value=bas64Decode(value);
	    dechm.put(key, value);	       
	}
		
		 dechm.put("title", title);
		 dechm.put("month", month);
		 return dechm;
	}

	
	
	public String bas64Decode(String  s)
	{	//By google
		try{
		if("".equals(s)){return s;}	
			
		    String base64chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		         // remove/ignore any characters not in the base64 characters list
		        // or the pad character -- particularly newlines
		        s = s.replaceAll("[^" + base64chars + "=]", "");		 
		        // replace any incoming padding with a zero pad (the 'A' character is
		        // zero)
		        //System.out.println("yyy"+s+s.length());
		        String p = (s.charAt(s.length() - 1) == '=' ? 
		                (s.charAt(s.length() - 2) == '=' ? "AA" : "A") : "");
		        String r = "";
		        s = s.substring(0, s.length() - p.length()) + p;		 
		        // increment over the length of this encrypted string, four characters
		        // at a time		        
		       // System.out.println("yyy");		        
		        for (int c = 0; c < s.length(); c += 4) {
		           // System.out.println("yyy"+s.length()+c);
		            // each of these four characters represents a 6-bit index in the
		            // base64 characters list which, when concatenated, will give the
		            // 24-bit number for the original 3 characters
		            int n = (base64chars.indexOf(s.charAt(c)) << 18)
		                    + (base64chars.indexOf(s.charAt(c + 1)) << 12)
		                    + (base64chars.indexOf(s.charAt(c + 2)) << 6)
		                    + base64chars.indexOf(s.charAt(c + 3));		 
		            // split the 24-bit number into the original three 8-bit (ASCII)
		            // characters
		            r += "" + (char) ((n >>> 16) & 0xFF) + (char) ((n >>> 8) & 0xFF)
		                    + (char) (n & 0xFF);
		           // System.out.println("ZZZZ"+r);		           
		        }		 
		        // remove any zero pad that was added to make this a multiple of 24 bits
		      //  System.out.println("kkk"+r);		        
		        String str=r.substring(0, r.length() - p.length());   	        
		        return r.substring(0, r.length() - p.length());
		}catch(Exception e){e.printStackTrace();return "";}
		        
		        
  }
	
	public void gethtppsserveraddress()
	{  setSslserveraddress(EbeeConstantsF.get("SSLProtocol","http")+"://"+EbeeConstantsF.get("sslserveraddress","www.eventbee.com"));
	   setServeraddress("http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com"));
	  //HashMap<String,String> hm=new  HashMap<String,String>();
	  
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

	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	public String getCallbackurl() {
		return callbackurl;
	}

	public void setCallbackurl(String callbackurl) {
		this.callbackurl = callbackurl;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getSoftdis() {
		return softdis;
	}

	public void setSoftdis(String softdis) {
		this.softdis = softdis;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
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

	public String getInternalref() {
		return Internalref;
	}

	public void setInternalref(String internalref) {
		Internalref = internalref;
	}

}
