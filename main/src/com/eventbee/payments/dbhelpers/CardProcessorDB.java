package com.eventbee.payments.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.StoredProcedureHelper;

public class CardProcessorDB {
	/*String  CountryNames[]=new String[]{"USA","Albania","Algeria","Andorra","Angola","Anguilla","Antigua and Barbuda","Armenia","Aruba",
			"Argentina","Australia","Austria","Azerbaijan Republic","Bahamas","Bahrain","Barbados","Belgium",
			"Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia and Herzegovina","Botswana","Brazil","British Virgin Islands",
			"Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Canada","Cape Verde","Cayman Islands","Chad",
			"China","Chile","Colombia","Comoros","Costa Rica","Cook Islands","Croatia","Cyprus","Czech Republic",
			"Denmark","Democratic Republic of the Congo","Djibouti","Dominica","Dominican Republic","Ecuador",
			"El Salvador","Eritrea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Federated States of Micronesia",
			"Fiji","Finland","France","French Guiana","French Polynesia","Gabon Republic","Gambia","Germany",
			"Gibraltar","Greenland","Greece","Grenada","Guadeloupe","Guatemala","Guinea","Guinea Bissau","Guyana",
			"Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Ireland","Israel","Italy","Japan",
			"Jamaica","Jordan","Kazakhstan","Kenya","Kiribati","Kuwait","Kyrgyzstan","Laos","Latvia","Lesotho",
			"Liechtenstein","Lithuania","Luxembourg","Madagascar","Malaysia","Malawi","Maldives","Mali","Malta",
			"Marshall Islands","Martinique","Mauritania","Mauritius","Mayotte","Mexico","Mongolia","Montserrat",
			"Morocco","Mozambique","Namibia","Nauru","Nepal","Netherlands","Netherlands Antilles","New Zealand",
			"New Caledonia","Nicaragua","Niger","Niue","Norfolk Island","Norway","Oman","Palau","Panama","Papua New Guinea",
			"Peru","Philippines","Pitcairn Islands","Poland","Portugal","Qatar","Republic of the Congo","Reunion",
			"Romania","Russia","Rwanda","Saint Vincent and the Grenadines","San Marino","Samoa","S�o Tom� and Pr�ncipe",
			"Saudi Arabia","Senegal","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands",
			"Somalia","South Africa","South Korea","Spain","Sri Lanka","St. Helena","St. Kitts and Nevis",
			"St. Lucia","St. Pierre and Miquelon","Suriname","Svalbard and Jan Mayen Islands","Swaziland","Sweden",
			"Switzerland","Taiwan","Tajikistan","Tanzania","Thailand","Togo","Tonga","Trinidad and Tobago","Tunisia",
			"Turkey","Turkmenistan","Turks and Caicos Islands","Tuvalu","Uganda","Ukraine","United Arab Emirates",
			"United Kingdom","Uruguay","Vanuatu","Vatican City State","Venezuela","Vietnam","Wallis and Futuna Islands",
			"Yemen","Zambia"};

			String CountryCodes[]=new String[]{"US","AL","DZ","AD","AO","AI","AG","AM","AW","AR","AU","AT","AZ","BS","BH",
			"BB","BE","BZ","BJ","BM","BT","BO","BA","BW","BR","VG","BN","BG","BF","BI","KH",
			"CA","CV","KY","TD","C2","CL","CO","KM","CR","CK","HR","CY","CZ","DK",
			"CD","DJ","DM","DO","EC","SV","ER","EE","ET","FK","FO","FM","FJ","FI","FR","GF","PF",
			"GA","GM","DE","GI","GL","GR","GD","GP","GT","GN","GW","GY","HN","HK","HU","IS","IN","ID",
			"IE","IL","IT","JP","JM","JO","KZ","KE","KI","KW","KG","LA","LV","LS","LI","LT",
			"LU","MG","MY","MW","MV","ML","MT","MH","MQ","MR","MU","YT","MX","MN","MS","MA","MZ","NA","NR",
			"NP","NL","AN","NZ","NC","NI","NE","NU","NF","NO","OM","PW","PA","PG","PE","PH","PN","PL","PT","QA",
			"CG","RE","RO","RU","RW","VC","SM","WS","ST","SA","SN","SC","SL","SG","SK","SI","SB","SO","ZA","KR",
			"ES","LK","SH","KN","LC","PM","SR","SJ","SZ","SE","CH","TW","TJ","TZ","TH","TG","TO","TT","TN",
			"TR","TM","TC","TV","UG","UA","AE","GB","UY","VU","VA","VE","VN","WF","YE","ZM"};*/

	String  CountryNames[]=new String[]{"USA","Canada"};
	String CountryCodes[]=new String[]{"US","CA"};
	
	String 	BrainTreeCountryNames[]=new String[]{"United States of America","Afghanistan","American Samoa","Andorra","Australia",
			"Austria","Bahrain","Belgium","Brazil","British Indian Ocean Territory","Brunei Darussalam","Canada","China","Christmas Island",
			"Cocos (Keeling) Islands","Congo","Cook Islands","Costa Rica","Cyprus","Czech Republic","Denmark","Dominican Republic","Ecuador",
			"El Salvador","Estonia","Faroe Islands","Finland","France","French Guiana","French Southern Lands","Germany","Greece","Greenland",
			"Guadeloupe","Guam","Guatemala","Heard and McDonald Islands","Hong Kong","Hungary","India","Indonesia","Iran","Ireland","Israel",
			"Italy","Jamaica","Japan","Kiribati","Luxembourg","Malaysia","Malta","Martinique","Mayotte","Mexico","Micronesia","Monaco","Montenegro",
			"Morocco","Nauru","Netherlands","New Zealand","Niue","Norfolk Island","Norway","Pakistan","Peru","Philippines","Pitcairn","Portugal",
			"Poland","Puerto Rico","Reunion","Saint Kitts and Nevis","Saint Pierre and Miquelon","San Marino","Singapore","Slovakia","Slovenia",
			"South Africa","Spain","Swaziland","Sweden","Switzerland","Taiwan","Thailand","Tokelau","Turkey","Turks and Caicos Islands","Tuvalu",
			"United Arab Emirates","United Kingdom","United States Minor Outlying Islands","Vatican City","Zimbabwe"};	


   
    public static String getSeqID(){
		String seqID=DbUtil.getVal("select nextval('CCProcessing_Sequence')",new String[]{});
		return seqID;
	}
	public static String insertInitialData(HashMap<String,String> hmap){
		String statustime= DateUtil.getCurrDBFormatDate();
		String seqIDExists=DbUtil.getVal("select 'Y' from ccdata where seq_id=?",new String[]{hmap.get("SEQID")});
		String insertqry="insert into ccdata(status_time,seq_id,purpose,processtype,amount,ref_id,paytoken) values(to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'),?,?,?,CAST(? as numeric),?,?)";
		if(!"Y".equalsIgnoreCase(seqIDExists))
		    DbUtil.executeUpdateQuery(insertqry, new String[]{statustime,hmap.get("SEQID"),hmap.get("PURPOSE"),hmap.get("PROCESSTYPE"),hmap.get("AMOUNT"),
				                                          hmap.get("REFID"),hmap.get("PAYTOKEN")});
		return "success";
	}
	public static String updateCCInitialData(HashMap<String,String> hmap,String seqId){
		String statustime= DateUtil.getCurrDBFormatDate();
		String updateqry="update ccdata set status_time=to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'),cc_number=?,card_type=?,expiremonth=?,expireyear=?,firstname=?,lastname=?," 
				+"address=?,city=?,country=?,state=?,postalcode=? where seq_id=?";
		String cardnumber=hmap.get("CARDNUMBER");
		cardnumber=cardnumber.substring(cardnumber.length()-4);
		DbUtil.executeUpdateQuery(updateqry, new String[]{statustime,cardnumber,hmap.get("CREDITCARDTYPE"),hmap.get("EXPIREMONTH"),
				                                          hmap.get("EXPIREYEAR"),hmap.get("FIRSTNAME"),hmap.get("LASTNAME"),hmap.get("ADDRESS"),
				                                          hmap.get("CITY"),hmap.get("COUNTRY"),hmap.get("STATE"),hmap.get("ZIP"),seqId});
		
		return "success";
	}
	public static String updateCCStatusData(String seqId,Map resMap){
		String updateqry="update ccdata set status=?,transaction_id=?,vault_id=?,vault_token=? where seq_id=?";
		DbUtil.executeUpdateQuery(updateqry, new String[]{(String)resMap.get("Status"),(String)resMap.get("TransactionID"),(String)resMap.get("VaultId"),(String)resMap.get("VaultToken"),seqId});
		return "success";
	}
	public static HashMap<String,String> getDefaultData(String seqId){
		HashMap<String,String> hm=new HashMap<String,String>(); 
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		String defaultdataqry="select round(amount::numeric,2) as amt,ref_id,processtype from ccdata where seq_id=?";
		statobj=dbm.executeSelectQuery(defaultdataqry, new String[]{seqId});
		if(statobj.getStatus()){
			hm.put("AMOUNT",dbm.getValue(0,"amt",""));
			hm.put("REFID",dbm.getValue(0,"ref_id",""));
			hm.put("PROCESSTYPE",dbm.getValue(0,"processtype",""));
		}
		return hm;
	}
	public static void updateCCStatusFailure(String seqId){
		DbUtil.executeUpdateQuery("update ccdata set status='Failure' where seq_id=?", new String[]{seqId});
	}
	public static String getManagerCCStatus(String mgrId){
		try{
		String expiremnthyear=DbUtil.getVal("select expmnthyear from manager_card_authentication where mgr_id=? and status='Active'",new String[]{mgrId});
		if(expiremnthyear==null)expiremnthyear="";
		if(!"".equals(expiremnthyear)){
		 String currentdate=DateUtil.getCurrDBFormatDate();
		 String[] currentdatearr=GenUtil.strToArrayStr(currentdate,"-"); 
		 String currentyear=currentdatearr[0];
		 String currentmonth=currentdatearr[1];
  	     String[] expiremnthyeararr=GenUtil.strToArrayStr(expiremnthyear,"/"); 
  	     String expiremonth=expiremnthyeararr[0];
  	     String expireyear=expiremnthyeararr[1];
  	     if(Integer.parseInt(expireyear)<=Integer.parseInt(currentyear)){
  	    	 if(Integer.parseInt(expiremonth)<Integer.parseInt(currentmonth))
  	    		 DbUtil.executeUpdateQuery("update manager_card_authentication set status='Inactive' where mgr_id=? and status='Active'", new String[]{mgrId});
  	     }
		}
		}catch(Exception e){
			System.out.println("Exception occured in getManagerCCStatus :"+ mgrId +e.getMessage());
		}
		String cardstatus=DbUtil.getVal("select 'Y' from manager_card_authentication where mgr_id=? and status='Active'",new String[]{mgrId});
		return cardstatus;
	}
	
	public static void updateUpgradePurpose(String seqid,String refid)
	{     	DbUtil.executeUpdateQuery("update CCProcessing_details set status='Success', created_at='now()'" +
					"  where regid=to_number(?,'99999999999999')",new String[]{seqid});
			String premiumlisttype=DbUtil.getVal("select purpose from CCProcessing_details where  regid=to_number(?,'99999999999999')",new String[]{seqid});
			String eid=DbUtil.getVal("select refid  from CCProcessing_details where  regid=to_number(?,'99999999999999')",new String[]{seqid});
			DbUtil.executeUpdateQuery("update eventinfo set premiumlevel=?  where eventid=to_number(?,'9999999999999')",new String[]{premiumlisttype, eid});
			System.out.println("upgrade success");
			
	}
	
	
	
	public static void  updateBuyEmailPurpose(String seqid,String refid,String m_cardamount)
	{
		String qty="0";
		if("25".equals(m_cardamount)){
			qty="1000";
		}else if("40".equals(m_cardamount)){
			qty="2500";
		}else if("60".equals(m_cardamount)){
			qty="5000";
		}else{
			qty="10000";
		}
		
		
		String transactionid=DbUtil.getVal("select nextval('seq_transactionid') as transactionid", new String[] {});
		transactionid=EncodeNum.encodeNum(transactionid);
		StoredProcedureHelper.creditmailquota("13579",qty,refid,"mail.quota.buy","MEM","C",transactionid);
		System.out.println("seqid"+seqid+"refid"+refid+"m_cardamount"+m_cardamount);
		DbUtil.executeUpdateQuery("update CCProcessing_details set status='Success', amount=?, created_at='now()' where refid=cast(? as numeric) and regid=cast(? as numeric)",new String[]{m_cardamount,refid,seqid});
		}
	
	public static String insertPayData(HashMap<String,String> hmap){
		String time= DateUtil.getCurrDBFormatDate();
		String payregid=DbUtil.getVal("select nextval('eventbee_payseqid')",new String[]{});
		String paytoken="";
		paytoken="EBP"+EncodeNum.encodeNum(payregid).toUpperCase();
		String insertqry="insert into  eventbee_payments_submits (payregid ,paytoken ,currency ,amount,purpose,paymode ,title ,message,vendor,callbackurl ,refid ,lang ,merchantid ,softdis,Internalref,status,submited_at)" +
		"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
	try{
		if(!"".equals(paytoken))
		DbUtil.executeUpdateQuery(insertqry, new String[]{payregid,paytoken,hmap.get("currency"),hmap.get("amount"),hmap.get("purpose"),hmap.get("paymode"),hmap.get("title"),hmap.get("message"),hmap.get("vendor"),hmap.get("callbackurl"),hmap.get("refid"),hmap.get("lang"),hmap.get("merchantid"),hmap.get("softdis"),hmap.get("Internalref"),"pending",time});
        
	    }
		catch(Exception e){System.out.println("problem in inserting payke details:"+e.getMessage());}
		System.out.println("in CardProcessorDB.java generated paytoken : "+paytoken);
		return paytoken;
	}
	
	
	public static HashMap<String,String>  getCardDisplaydata(String paytoken){
		HashMap<String,String> hmap =new HashMap<String,String>();
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		String disdataqry="select * from eventbee_payments_submits where paytoken=?";
		statobj=dbm.executeSelectQuery(disdataqry, new String[]{paytoken});
		if(statobj.getStatus()){
			hmap.put("amount",dbm.getValue(0,"amount","1.00"));
			hmap.put("refid",dbm.getValue(0,"refid",""));			
			hmap.put("currency",dbm.getValue(0,"currency","USD"));
			hmap.put("paymode",dbm.getValue(0,"paymode","direct"));
			hmap.put("lang",dbm.getValue(0,"lang","En"));
			hmap.put("merchantid",dbm.getValue(0,"merchantid",""));
			hmap.put("message",dbm.getValue(0,"message",""));
			hmap.put("title",dbm.getValue(0,"title","Credit Card Details"));
			hmap.put("callbackurl",dbm.getValue(0,"callbackurl",""));
			hmap.put("vendor",dbm.getValue(0,"vendor","Braintree"));
			hmap.put("softdis",dbm.getValue(0,"softdis","Eventbee"));
			hmap.put("purpose",dbm.getValue(0,"purpose",""));
			hmap.put("Internalref",dbm.getValue(0,"internalref",""));
		}
		System.out.println("hmap"+hmap);	
		return hmap;

	}
	public static void updateTokentrans(String paytoken)
	{
		String updatequery="update eventbee_payments_submits set status='success' where paytoken=?";
		DbUtil.executeUpdateQuery(updatequery, new String[]{paytoken,});
	}
	public  List<Entity>  getcountryNames()
	{  
	List<Entity> countrylist=new ArrayList<Entity>();
	try{		
			for(int k=0;k<CountryNames.length;k++){
			countrylist.add(new Entity(CountryCodes[k],CountryNames[k]));
		     }
	    }catch(Exception e){
		System.out.println("There is an Exception "+e.getMessage());
	  }
	return countrylist;
	
		
	}
	
	public List<Entity> getBrainTreeCountryNames(){
		List<Entity> countrylist=new ArrayList<Entity>();	
		try{		
			for(int k=0;k<BrainTreeCountryNames.length;k++){
			countrylist.add(new Entity(BrainTreeCountryNames[k],BrainTreeCountryNames[k]));
		     }
	    }catch(Exception e){
		System.out.println("There is an Exception "+e.getMessage());
	  }
	return countrylist;
		
	}
	
	public static String getsuccessurl(String paytoken)
	{
		String successurl=DbUtil.getVal("select callbackurl from eventbee_payments_submits where paytoken=?",new String[]{paytoken});
		return successurl;
		
	}
}
