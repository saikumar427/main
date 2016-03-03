package com.membertasks.dbhelpers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.event.beans.TrackURLData;
import com.event.dbhelpers.EventDB;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.StringEncrypter;
import com.eventbee.general.StringEncrypter.EncryptionException;
import com.eventbee.general.formatting.CurrencyFormat;
import com.mysettings.beans.AccountInfo;
import com.event.dbhelpers.TransactionDB;
import com.event.helpers.I18n;
import com.eventregister.dbhelper.*;

public class AccountInfoDB {

	private static HashMap<String,String> attColumnMap=new HashMap<String,String>();
	static{
		attColumnMap.put("USED_CREDITS","usedcredits");
		
	}	
	
	
	
	public static HashMap getInDueDetails(String val,String mgrid)
	
	{	
		ArrayList<String> fields = new ArrayList<String>();
		HashMap hmp = new HashMap();
		String purpose="",query="";
		ArrayList<HashMap<String, String>> invoice = new ArrayList<HashMap<String,String>>();   
		try{			
		DBManager db=new DBManager();
		StatusObj statobj=null;
		purpose=val;
		if("indue".equals(val))
			//query="select to_date(monthofinvoice,'Mon-YYYY') as invoicedate,* from mgr_monthly_invoice where mgrid=cast(? as integer) and status!='Charged' and showstatus='Y' and invoiced_amount>0.00  order by invoicedate desc";
			//query="select to_date(monthofinvoice,'Mon-YYYY') as invoicedate,abs(invoiced_amount) as invoiced_amount,monthofinvoice from mgr_active_monthly_invoice where mgrid=cast(? as integer) and status='Pending' and showstatus='Y' order by invoicedate desc";
			query="select abs(invoiced_amount_usd) as invoiced_amount,monthofinvoice,title,header_title  from mgr_active_monthly_invoice where mgrid=cast(? as integer) and status='Pending' and showstatus='Y' order by created_at desc";	
			
		else 
			//query="select to_date(monthofinvoice,'Mon-YYYY') as invoicedate,* from mgr_monthly_invoice where mgrid=cast(? as integer) and status='Charged' and showstatus='Y' order by invoicedate desc";
			//query="select to_date(monthofinvoice,'Mon-YYYY') as invoicedate,abs(invoiced_amount) as invoiced_amount,monthofinvoice from mgr_active_monthly_invoice where mgrid=cast(? as integer) and status='Charged' and showstatus='Y' order by invoicedate desc";
			query="select abs(invoiced_amount_usd) as invoiced_amount,monthofinvoice,title,header_title from mgr_active_monthly_invoice where mgrid=cast(? as integer) and status='Charged' and showstatus='Y' order by created_at desc";	
		statobj=db.executeSelectQuery(query,new String[]{mgrid});
		fields=getinvoiceFields(val,statobj);
		if(statobj.getStatus() && statobj.getCount()>0){
        for(int j=0;j<statobj.getCount();j++){
        	HashMap<String,String> invoiceDetails = new HashMap<String, String>();
        	invoiceDetails.put("invoicedmonth",db.getValue(j,"monthofinvoice",""));
        	invoiceDetails.put("amount",db.getValue(j,"invoiced_amount",""));
        	invoiceDetails.put("title",db.getValue(j,"title",""));
        	String headertitle = db.getValue(j,"header_title","");
        	headertitle = headertitle.replace("Invoice", "");
        	headertitle = headertitle+""+I18n.getString("acc.invoice.normal.lbl");
        	invoiceDetails.put("headertitle",headertitle);
        	invoice.add(invoiceDetails);
        }}}
        catch(Exception e){			
		}
		
        hmp.put("Fields",fields);
        hmp.put("details",invoice);
        hmp.put("purpose",purpose);      
		return hmp;
	} 
	
	public static ArrayList<String> getinvoiceFields(String value,StatusObj statobj){
		ArrayList<String> invoicefields = new ArrayList<String>();
		invoicefields.add("invoicedmonth");
		invoicefields.add("amount");
		invoicefields.add("title");
		invoicefields.add("headertitle");
		if("indue".equals(value) && statobj.getCount()>0)		 
		  invoicefields.add("bdetails");
		return invoicefields;
	}
	
	public static ArrayList<String> populateFields(List<String> currencylist){		
        ArrayList<String> Fields=new ArrayList<String>();
		Fields.add("Event_Name");
        Fields.add("Service_Fee");
        Fields.add("Ticket_Count");
        if(currencylist.size()>1){
        	Fields.add("Total_Service_Fee_Local_Currency");
            Fields.add("Total_Service_Fee_Usd");
        }else if(currencylist.size()==1 && !"$".equals(currencylist.get(0))){
        	Fields.add("Total_Service_Fee_Local_Currency");
        	Fields.add("Total_Service_Fee_Usd");	
        }else
        	Fields.add("Total_Service_Fee_Usd");
        
        return Fields;
	}
	
	
	public static HashMap getInvoiceDetailsJson(String userid,String invoicemnth,String rtype){		
		System.out.println("get invoice detials json: userid:"+userid+"invoice month:"+invoicemnth+"rtype:"+rtype);
		JSONObject monthlyinvoicedetails=new JSONObject();
		ArrayList<String> fields = new ArrayList<String>();
		HashMap hmp = new HashMap();
		ArrayList<HashMap<String, String>> monthlyinvoicelist = new ArrayList<HashMap<String,String>>();
		List<String> currencylist=new ArrayList<String>();
		
		String monthlyinvoiceqry="",msg="";
		try{
			
			DBManager db=new DBManager();
			StatusObj statobj=null;
			String status="";
			if("clear".equals(rtype))
				status="Charged";
			else
				status="Pending";
			
			
			/*monthlyinvoiceqry="select ei.eventid,sum(abs(total_amount_owe)) as total_amount_owe,eventname from eventlevel_active_invoice ei,eventinfo e where "+
			                  " CAST(ei.eventid as BIGINT)=e.eventid and mgrid=cast(? as integer) and  monthofinvoice=? and ei.status=? group by ei.eventid,eventname";
			monthlyinvoiceqry="select ei.eventid,ticketsqty,total_amount_owe,unit_service_fee,eventname from eventlevel_active_invoice ei,eventinfo e where "+
			                  " CAST(ei.eventid as BIGINT)=e.eventid and mgrid=cast(? as integer) and  monthofinvoice=? and ei.status=? and unit_service_fee is not null order by eventname,unit_service_fee";
			monthlyinvoiceqry=" select ei.eventid,substring(array_agg(ticketsqty ):: text from '([^{^}]+)' ) as ticketsqty,substring(array_agg(usdtotal ):: text from '([^{^}]+)' ) as usdtotal,"+
			                  " substring(array_agg(usf_dollars ):: text from '([^{^}]+)' )  as usf_dollars,eventname from eventlevel_active_invoice ei,eventinfo e where CAST(ei.eventid as BIGINT)=e.eventid "+
					          " and mgrid=cast(? as integer) and  monthofinvoice=? and ei.status=? group by ei.eventid,eventname ";*/
			monthlyinvoiceqry="select ei.eventid,ticketsqty,total_amount_owe_local_currency as total_amount_owe_local,unit_service_fee,title,currency_symbol,total_amount_owe_dollars as total_amount_owe_dollars from eventlevel_active_invoice ei where "+
	                  " mgrid=cast(? as integer) and  monthofinvoice=? and ei.status=? and unit_service_fee is not null order by title,unit_service_fee";
			statobj=db.executeSelectQuery(monthlyinvoiceqry,new String[]{userid,invoicemnth,status});
			currencylist=DbUtil.getValues("select distinct currency_symbol from eventlevel_active_invoice where mgrid=cast(? as integer) and  monthofinvoice=? and status=?",new String[]{userid,invoicemnth,status});
			
			if(currencylist.get(0)==null || "null".equalsIgnoreCase(currencylist.get(0)) || currencylist.size()==0){currencylist.clear();currencylist.add("$");}
			System.out.println("currencylist is:"+currencylist);
			fields=populateFields(currencylist);
			
			if(statobj.getStatus() && statobj.getCount()>0){
		        for(int j=0;j<statobj.getCount();j++){
		        	HashMap<String,String> monthlyinvoice = new HashMap<String, String>();
		        	monthlyinvoice.put("Total_Service_Fee_Local_Currency",db.getValue(j,"currency_symbol","$")+CurrencyFormat.getCurrencyFormat("",db.getValue(j,"total_amount_owe_local",""),false));
		        	monthlyinvoice.put("Total_Service_Fee_Usd",CurrencyFormat.getCurrencyFormat("",db.getValue(j,"total_amount_owe_dollars",""),false));
		        	monthlyinvoice.put("Ticket_Count",db.getValue(j,"ticketsqty",""));
		        	monthlyinvoice.put("Event_Name",db.getValue(j,"title",""));
		        	monthlyinvoice.put("Service_Fee",db.getValue(j,"currency_symbol","$")+CurrencyFormat.getCurrencyFormat("",db.getValue(j,"unit_service_fee",""),false));
		        	monthlyinvoicelist.add(monthlyinvoice);
		        }}		
			
			monthlyinvoicedetails.put("fields",fields);
			monthlyinvoicedetails.put("monthlyinvdetails",monthlyinvoicelist);
			hmp.put("monthlyinvdetails", monthlyinvoicelist);
			hmp.put("fields",fields);
		 }
		
		catch(Exception e){}	
		
		return hmp;
	}
	
	
public static boolean UpdateAccountInfo(String mgrId,AccountInfo accInfo){
	try {
		String fName = accInfo.getFirstName().trim();
		String lName = accInfo.getLastName().trim();
		String email = accInfo.getEmail().trim();
		String phone = accInfo.getPhone().trim();
		String gender = accInfo.getGender().trim();
		String city = accInfo.getCity().trim();
		String state = accInfo.getState().trim();
		String country = accInfo.getCountry().trim();
		String zip = accInfo.getZip().trim();
		String title = accInfo.getTitle().trim();
		String company = accInfo.getCompany().trim();
		String industry = accInfo.getIndustry().trim();
		String address=accInfo.getAddress().trim();
		String USR_PROFILE_UPDATE = "update user_profile set first_name=?,last_name=?,"
				+ "email=?,phone=?,mobile=?,gender=?,city=?,state=?,country=?,zip=?,"
				+ " title=?,company=?,industry=?,updated_by=?,updated_at=now(),street=? where user_id=?";
		DbUtil.executeUpdateQuery(USR_PROFILE_UPDATE, new String []{fName,lName,email,phone,phone,gender,city,state,
				country,zip,title,company,industry,"",address,mgrId});
	} catch (Exception e) {
		// TODO: handle exception
	}
		
	return true;
}
public static boolean UpdatePassword(String mgrId,AccountInfo accInfo) throws EncryptionException{
	String newPassword=accInfo.getNewPassword();
	String encPword=(new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME)).encrypt(newPassword);
	String AUTHENTICATION_UPDATE="update authentication set "
		   +"password=?,updated_by=?,updated_at=now() where user_id=?";
	DbUtil.executeUpdateQuery(AUTHENTICATION_UPDATE, new String []{encPword,"",mgrId});
	return true;
}
public static AccountInfo getUserInfo(String mgrId){
	AccountInfo acInfo=new AccountInfo();
	String selQuery="select * from user_profile where user_id=?";
	DBManager dbmanager=new DBManager();
	StatusObj statobj=null;
	statobj=dbmanager.executeSelectQuery(selQuery,new String []{mgrId});
	if(statobj.getStatus()){
		 acInfo.setFirstName(dbmanager.getValue(0,"first_name","").trim());
		 acInfo.setCity(dbmanager.getValue(0,"city","").trim());
		 acInfo.setCompany(dbmanager.getValue(0,"company","").trim());
		 acInfo.setCountry(dbmanager.getValue(0,"country","").trim());
		 acInfo.setEmail(dbmanager.getValue(0,"email","").trim());
		 acInfo.setGender(dbmanager.getValue(0,"gender","").trim());
		 acInfo.setIndustry(dbmanager.getValue(0,"industry","").trim());
		 acInfo.setLastName(dbmanager.getValue(0,"last_name","").trim());
		 acInfo.setPhone(dbmanager.getValue(0,"phone","").trim());
		 acInfo.setState(dbmanager.getValue(0,"state","").trim());
		 acInfo.setTitle(dbmanager.getValue(0,"title","").trim());
		 acInfo.setZip(dbmanager.getValue(0,"zip","").trim());
		 acInfo.setAddress(dbmanager.getValue(0,"street","").trim());
	}
	return acInfo;
}
   public static HashMap<String,String> getCardInfo(String mgrId){
	   HashMap<String,String> hm=null;
	   String cardStatus="";
	   try{
	   String carddetailquery="select cardlast_4digits,expmnthyear,status from manager_card_authentication where mgr_id=? and status='Active'";
	   DBManager dbm=new DBManager();
	   StatusObj statobj=null;
	   String cardlabel="";
	   statobj=dbm.executeSelectQuery(carddetailquery,new String []{mgrId});
	   
	   
	   if(statobj.getStatus()){
		   for(int i=0;i<statobj.getCount();i++){
		    hm=new HashMap<String,String>();
	        String currentdate=DateUtil.getCurrDBFormatDate();
			String[] currentdatearr=GenUtil.strToArrayStr(currentdate,"-"); 
			String currentyear=currentdatearr[0];
			String currentmonth=currentdatearr[1];
	  	    String[] expiremnthyeararr=GenUtil.strToArrayStr(dbm.getValue(i,"expmnthyear",""),"/"); 
	  	    String expiremonth=expiremnthyeararr[0];
	  	    String expireyear=expiremnthyeararr[1];
	  	    if(Integer.parseInt(expireyear)<=Integer.parseInt(currentyear)){
	  	    	 if(Integer.parseInt(expiremonth)<Integer.parseInt(currentmonth)){
	  	    		 DbUtil.executeUpdateQuery("update manager_card_authentication set status='Inactive' where mgr_id=? and status='Active'", new String[]{mgrId});
	  	    		 cardlabel=I18n.getString("acc.add.card.btn.lbl");
	  	    	 }else{
	  	    		hm.put("card4digits",dbm.getValue(i,"cardlast_4digits",""));
		 	        hm.put("expmnthyear",dbm.getValue(i,"expmnthyear",""));
		 	       cardStatus=dbm.getValue(i,"status","");
		 		   if("Active".equals(cardStatus)){
		 	        	cardStatus=I18n.getString("global.status.active.lbl");
		 	        }else if("Inactive".equals(cardStatus)){
		 	        	cardStatus=I18n.getString("global.status.inactive.lbl");
		 	        }else{
		 	        	cardStatus=I18n.getString("global.status.failed.lbl");
		 	        }
		 	        hm.put("status",cardStatus);
		            cardlabel=I18n.getString("acc.change.button.lbl");
		           }
	  	    	hm.put("cardlabel",cardlabel);
	  	     }else{
	            hm.put("card4digits",dbm.getValue(i,"cardlast_4digits",""));
	 	        hm.put("expmnthyear",dbm.getValue(i,"expmnthyear",""));
	 	       cardStatus=dbm.getValue(i,"status","");
	 		   if("Active".equals(cardStatus)){
	 	        	cardStatus=I18n.getString("global.status.active.lbl");
	 	        }else if("Inactive".equals(cardStatus)){
	 	        	cardStatus=I18n.getString("global.status.inactive.lbl");
	 	        }else{
	 	        	cardStatus=I18n.getString("global.status.failed.lbl");
	 	        }
	 	        hm.put("status",cardStatus);
	            cardlabel=I18n.getString("acc.change.button.lbl");
	            hm.put("cardlabel",cardlabel); 
	            
	       }
	   }
	   }  }catch(Exception e){
		   System.out.println("Exception occured in getCardInfo :"+ mgrId +e.getMessage());
	   }
	   return hm; 
   }
   public static String getAccounttype(String mgrId){
	   String accounttype="";
	   accounttype=DbUtil.getVal("select accounttype from authentication where user_id=?", new String[]{mgrId});
	   return accounttype;
   }
   public static void insertCCResponseData(String seqId,String mgrId){
	   String updatestatus="",expmnthyear="",setupdate="",ccnumber="",vendor="";
	   String statuscheckqry="select expiremonth,expireyear,status,cc_number from ccdata where seq_id=?";
	   String managerccdata="insert into manager_card_authentication(mgr_id,setupdate,amount,cardlast_4digits,"
		                    +"expmnthyear,vendor,vendor_token,vault_id,status) select ref_id,to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'),amount,?,"
		                    +"?,?,transaction_id,vault_id,? from ccdata where seq_id=?"; 
	   DBManager dbm=new DBManager();
	   StatusObj statobj=null;
	   try{
	   statobj=dbm.executeSelectQuery(statuscheckqry, new String[]{seqId});
	   if(statobj.getStatus()){
		   String expmnth=dbm.getValue(0,"expiremonth","");
		   String expyear=dbm.getValue(0,"expireyear","");
		   String status=dbm.getValue(0,"status","");
		   String cardnumber=dbm.getValue(0,"cc_number","");
		   expmnthyear=expmnth+"/"+expyear;
		   setupdate= DateUtil.getCurrDBFormatDate();
		   vendor="Braintree";
		   if("Success".equalsIgnoreCase(status)){
			   updatestatus="Active";
	           String checkstatus=DbUtil.getVal("select 'Y' from manager_card_authentication where mgr_id=? and status='Active'",new String[]{mgrId});
			   if("Y".equalsIgnoreCase(checkstatus))
				  DbUtil.executeUpdateQuery("update manager_card_authentication set status='Inactive' where mgr_id=? and status='Active'", new String[]{mgrId});
				   
			  DbUtil.executeUpdateQuery(managerccdata, new String[]{setupdate,cardnumber,expmnthyear,vendor,updatestatus,seqId});
		   }
		}
	   }catch(Exception e){
		   System.out.println("Exception occured in insertCCResponseData :"+seqId);
	   }
	   
   }
   
   public static HashMap<String,String> getAvailableBeeCredits(String mgrId){
	   HashMap<String,String> availbcredits=new HashMap<String,String>();
	   DBManager dbm=new DBManager();
       StatusObj statobj=null;
       statobj=dbm.executeSelectQuery("select available_credits,total_credits from mgr_available_credits where mgr_id=CAST(? as bigint)",new String[]{mgrId});
	   if(statobj.getStatus() && statobj.getCount()>0){
		   availbcredits.put("totalcredits",CurrencyFormat.getCurrencyFormat("",dbm.getValue(0, "total_credits","0.00"),false));	  
		   availbcredits.put("availablebcredits",CurrencyFormat.getCurrencyFormat("",dbm.getValue(0, "available_credits","0.00"),false));
	   }
	   return availbcredits;
   }
   
   public static void insertBeeCredits(String seqId,String mgrId){
	  String transactionid="",updatetime=""; 
	  String amount="0.00";
	  DBManager dbm=new DBManager();
	  StatusObj statobj=null;
	  String insertqry="insert into mgr_accured_credits_details(mgr_id,purchased_bee_credits,purchased_at,ref_id)"
		                +" values(CAST(? as bigint),CAST(? as numeric),to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'),?)";
	  String creditsinsertqry="insert into mgr_available_credits(mgr_id,total_credits,used_credits,available_credits,last_updated_at,updated_by)"+
		                      " values(CAST(? as bigint),CAST(? as numeric),CAST(? as numeric),CAST(? as numeric),to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'),?)";
	  String creditsupdateqry="update mgr_available_credits set total_credits=total_credits+CAST(? as numeric),available_credits=available_credits+CAST(? as numeric),"+
	                          " last_updated_at=to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'),updated_by=? where mgr_id=CAST(? as bigint)";
	  String query="select transaction_id,amount from ccdata where ref_id=? and seq_id=?";
	  statobj=dbm.executeSelectQuery(query,new String[]{mgrId,seqId});
	  if(statobj.getStatus() && statobj.getCount()>0){
		  transactionid=dbm.getValue(0,"transaction_id","");
		  if(!"".equals(dbm.getValue(0,"amount","")) && !"null".equals(dbm.getValue(0,"amount","")) && dbm.getValue(0,"amount","")!=null){
		   if("100".equals(dbm.getValue(0,"amount","")) || "100.00".equals(dbm.getValue(0,"amount","")))
			   amount="100.00";
		   else if("475".equals(dbm.getValue(0,"amount","")) || "475.00".equals(dbm.getValue(0,"amount","")))
			   amount="500.00";
		   else if("900".equals(dbm.getValue(0,"amount","")) || "900.00".equals(dbm.getValue(0,"amount","")))
			   amount="1000.00";
		   else if("4250".equals(dbm.getValue(0,"amount","")) || "4250.00".equals(dbm.getValue(0,"amount","")))
			   amount="5000.00";
		   else if("8000".equals(dbm.getValue(0,"amount","")) || "8000.00".equals(dbm.getValue(0,"amount","")))
			   amount="10000.00";
		   else if("37500".equals(dbm.getValue(0,"amount","")) || "37500.00".equals(dbm.getValue(0,"amount","")))
			   amount="50000.00";
		   else if("70000".equals(dbm.getValue(0,"amount","")) || "70000.00".equals(dbm.getValue(0,"amount","")))
			   amount="100000.00";
		  }  
	  }
	  updatetime=DateUtil.getCurrDBFormatDate();
	  DbUtil.executeUpdateQuery(insertqry,new String[]{mgrId,amount,updatetime,transactionid});
	  String chkentry=DbUtil.getVal("select 'Y' from mgr_available_credits where mgr_id=CAST(? as integer)",new String[]{mgrId});
	  if("Y".equals(chkentry))
		  DbUtil.executeUpdateQuery(creditsupdateqry,new String[]{amount,amount,updatetime,"Manager",mgrId});
	  else
		  DbUtil.executeUpdateQuery(creditsinsertqry,new String[]{mgrId,amount,"0.00",amount,updatetime,"Manager"});
   }
   
   
   public static String getBCreditsHistoryJson(String mgrId){
	   String msg="",tktqty="";
	   JSONObject json=new JSONObject();
	   HashMap<String,String> eventnamemap=new HashMap<String,String>();
	   HashMap<String,String> creditsinfo=new HashMap<String,String>();
	   ArrayList<HashMap<String,String>> histarr=new ArrayList<HashMap<String,String>>(); 
	   ArrayList<String> Fields=new ArrayList<String> ();
	   ArrayList<HashMap<String,String>> resfields=new ArrayList<HashMap<String,String>>();
	   HashMap<String,String> ticketqtymap=new HashMap<String,String>();
	   DBManager dbm=new DBManager();
	   StatusObj statobj=null;
	   String eventnamequery="select eventname,eventid from eventinfo where eventid in(select distinct(used_for_eventid) from mgr_credits_usage_history where mgr_id=CAST(? as bigint))";
	   String historyquery="select sum(used_credits) as totalcredits,count(tid) as transactions,used_for_eventid  from mgr_credits_usage_history where mgr_id=CAST(? as bigint) group by "
		                   +" used_for_eventid order by totalcredits desc";
	   
	   String ticketqtyqry="select sum(collected_ticketqty) as tktqty,eventid from event_reg_transactions where collected_by='beecredits' "+
	                       " and CAST(eventid as BIGINT) in(select distinct(used_for_eventid) from mgr_credits_usage_history where mgr_id=CAST(? as BIGINT)) group by eventid";
	   try{
		Fields.add("Event_Name");
		Fields.add("Ticket_Count");
		Fields.add("Used_Credits&nbsp;(B$)");
		for(int i=0;i<Fields.size();i++){
			HashMap<String, String> hm=new HashMap<String, String>();
			String keyvalue=(String)Fields.get(i);
			hm.put("key", keyvalue);
			resfields.add(hm);
		}
	  
	   statobj=dbm.executeSelectQuery(eventnamequery, new String[]{mgrId});
	   if(statobj.getStatus() && statobj.getCount()>0){
		   for(int i=0;i<statobj.getCount();i++){
		   eventnamemap.put(dbm.getValue(i,"eventid",""),dbm.getValue(i,"eventname",""));
		   }   
	   }
	   
	   statobj=dbm.executeSelectQuery("select * from mgr_available_credits where mgr_id=CAST(? as bigint)",new String[]{mgrId});
	   if(statobj.getStatus() && statobj.getCount()>0){
		  for(int i=0;i<statobj.getCount();i++){
			  creditsinfo.put("totalcredits",CurrencyFormat.getCurrencyFormat("",dbm.getValue(i,"total_credits","0"),false));
			  creditsinfo.put("availablecredits",CurrencyFormat.getCurrencyFormat("",dbm.getValue(i,"available_credits","0"),false));
			  creditsinfo.put("usedcredits",CurrencyFormat.getCurrencyFormat("",dbm.getValue(i,"used_credits","0"),false));
		  }
	   }
	   
	   statobj=dbm.executeSelectQuery(ticketqtyqry, new String[]{mgrId});
	   if(statobj.getStatus() && statobj.getCount()>0){
	     for(int i=0;i<statobj.getCount();i++){
	    	 ticketqtymap.put(dbm.getValue(i,"eventid",""),dbm.getValue(i,"tktqty",""));
	     }
	   }
	   statobj=dbm.executeSelectQuery(historyquery, new String[]{mgrId});
	   if(statobj.getStatus() && statobj.getCount()>0){
		   for(int i=0;i<statobj.getCount();i++){
			   HashMap<String,String> creditshist=new HashMap<String,String>();
			   creditshist.put("Event_Name",eventnamemap.get(dbm.getValue(i,"used_for_eventid","")));
			   tktqty=ticketqtymap.get(dbm.getValue(i,"used_for_eventid",""));
			   if(tktqty==null)tktqty="0";
			   creditshist.put("Ticket_Count",tktqty);
			   creditshist.put("Used_Credits&nbsp;(B$)",dbm.getValue(i,"totalcredits",""));
			   histarr.add(creditshist);
		   }
		   json.put("HistoryDetails",histarr);
		   json.put("fields",resfields);
		   json.put("creditsdata",creditsinfo);
	   }
	   }catch(Exception e){
		   System.out.println("Exception Occured in getBCreditsHistoryJson");
	   }
	   msg=json.toString();
	   return msg;
   }
   
   public static void updatePaymentAmount(String seqId,String paytoken,String amount){
	   DbUtil.executeUpdateQuery("update eventbee_payments_submits set amount=? where paytoken=?",new String[]{amount,paytoken});
	   DbUtil.executeUpdateQuery("update ccdata set amount=CAST(? as numeric) where seq_id=?",new String[]{amount,seqId});
	  }
  
   public static void updateInvoiceAmount(String userid,String month,String amount){
	  System.out.println("userid in updateInvoiceAmount method :: userid: "+userid+":: month: "+month+" :: amount: "+amount);
	  String months="";
	  try{
	  if("total".equals(month)){
	  List invoicemonths=DbUtil.getValues("select monthofinvoice from mgr_active_monthly_invoice where mgrid=cast(? as integer) and showstatus='Y'",new String[]{userid}); 
	  
	  for(int i=0;i<invoicemonths.size();i++){
		  if(i==0)
			  months="'"+invoicemonths.get(i)+"'";
		  else
			  months=months+","+"'"+invoicemonths.get(i)+"'";
	  }
	  }else
		  months="'"+month+"'";
	  if("total".equals(month))
		  DbUtil.executeUpdateQuery("update mgr_active_monthly_invoice set status='Charged',updated_at=now() where mgrid=cast(? as integer) and showstatus='Y' and status='Pending'",new String[]{userid});
	  else
		  DbUtil.executeUpdateQuery("update mgr_active_monthly_invoice set status='Charged',updated_at=now() where mgrid=cast(? as integer) and monthofinvoice=? and status='Pending'",new String[]{userid,month});
	  
	  DbUtil.executeUpdateQuery("update eventlevel_active_invoice set status='Charged',paidby='manager',updated_at=now() where mgrid=cast(? as integer) and monthofinvoice in("+months+") and status='Pending'", new String[]{userid});
	  String chkstatus=DbUtil.getVal("select 'Y' from mgr_config where name='require_charge' and value='Y' and userid=CAST(? as INTEGER)", new String[]{userid});
	  if("Y".equals(chkstatus)){
		    if("total".equals(month))
			  DbUtil.executeUpdateQuery("update mgr_config set value='N' where name='require_charge' and value='Y' and userid=CAST(? as INTEGER)", new String[]{userid});
		    else{
		    	String pendingstatus=DbUtil.getVal("select 'Y' from mgr_active_monthly_invoice where status='Pending' and mgrid=CAST(? as INTEGER)", new String[]{userid});
		    	if(pendingstatus==null)pendingstatus="N";
		    	if("N".equalsIgnoreCase(pendingstatus))
		    		DbUtil.executeUpdateQuery("update mgr_config set value='N' where name='require_charge' and value='Y' and userid=CAST(? as INTEGER)", new String[]{userid});
		    }
			  
	 }	
	  }catch(Exception e){
		  System.out.println("Exception occured in updateInvoiceAmount method:"+userid+" :: "+e.getMessage());
	  }
	}
   
   public static String getInvoiceSum(String mgrid){
	   String invoicesum="0.00";
	   try{
	      invoicesum=DbUtil.getVal("select sum(invoiced_amount_usd) as amount from mgr_active_monthly_invoice where status!='Charged' and mgrid=cast(? as integer) and showstatus='Y'",new String[]{mgrid});
	   }catch(Exception e){
		   System.out.println("Exception occured in getInvoiceSum for manager :: "+mgrid);
	   }
	   return invoicesum;
   }
   
   
   public static String getCCType(String mgrid){
	   String query="select name from mgr_config where userid=cast(? as integer) and name in('mgr.card.popup1.required','mgr.card.popup2.required','authorize') and value='Y'";
	   String cctype=DbUtil.getVal(query, new String[]{mgrid});
	   if(cctype==null)cctype="";
   return cctype;
   }
   
   public static void insertAddAttendeeData(String paytoken){
	   DBManager dbmanager=new DBManager();
	   StatusObj statobj=null;
	   String query = "select internalref,refid from eventbee_payments_submits where status='success' and paytoken=? ";
	   statobj=dbmanager.executeSelectQuery(query,new String []{paytoken});
	   String tid="";String eid="";
	   if(statobj.getStatus()){
	   	tid = dbmanager.getValue(0,"internalref","");
	   	eid = dbmanager.getValue(0,"refid","");
	   }
	   if(tid!=null && eid!=null){
	   	String status = DbUtil.getVal("select 'yes' from event_reg_transactions where eventid=? and tid=? ", new String[]{eid,tid});
	   	if(!"yes".equals(status)){
	   		updateRegDb(tid, paytoken, eid);
	   	}
	   }
   }
   
   public static void updateRegDb(String tid, String paytoken, String eid){
		System.out.println("##### updateRegDb() in finalupdate.jsp tid: "+tid);
		try{
			RegistrationConfirmationEmail regmail=new RegistrationConfirmationEmail();
			RegistrationTiketingManager regmgr=new RegistrationTiketingManager();
			RegistrationProcessDB regdb=new RegistrationProcessDB();
			
			regmgr.updatePaytype(tid, "confirmation page");
				
			regmgr.updatePaymentType(tid);
				
			regdb.InsertRegistrationDb(tid,eid);
			
			regdb.updateExtPayId(tid,paytoken);
			
			boolean seatingenabled=regmgr.getSeatingEnabled(eid);
			System.out.println("In finalupdate.jsp seatingenabled: "+seatingenabled);
			if(seatingenabled){
				String eventdate = DbUtil.getVal("select  eventdate from event_reg_transactions where eventid=? and tid=? ", new String[]{eid,tid});
				if(eventdate==null || "null".equals(eventdate)) eventdate="";
				regmgr.fillseatingstatus(tid, eid, eventdate);
			}
			
			TransactionDB.resendingMail(eid,tid,"Ticketing");
			regmgr.deleteTempDetails(tid);
			regmgr.updateProfileStatus(tid);
		}catch(Exception e){
			System.out.println("Exception in finalupdate.jsp Error:: "+e.getMessage());
		}
		
	}
   
   public static String insertPaypalDataToDB(String amount,String month,String purpose,String mgrid,String tid){
	   System.out.println("In insertPaypalDataToDB method for mgrid :: "+mgrid+" :: seqId :: "+tid);
	   String msg="",serveraddress="";
	   JSONObject json=new JSONObject();
	   serveraddress="http://"+EbeeConstantsF.get("serveraddress", "www.eventbee.com");
	   String paypaldataqry="insert into mgr_paypal_payment_data(amount,invoice_month,purpose,refid,tid,submitted_at) values(CAST(? as numeric),?,?,CAST(? as BIGINT),?,now())";
	   try{
	   DbUtil.executeUpdateQuery(paypaldataqry, new String[]{amount,month,purpose,mgrid,tid});
	   System.out.println("data submited successfully in mgr_paypal_payment_data");
	   json.put("mgrid", mgrid);
	   json.put("seqid", tid);
	   json.put("serveraddress", serveraddress);
	   }catch(Exception e){
		   System.out.println("Exception occured in insertPaypalDataToDB while preparing json data for mgrid :: "+mgrid+" :: seqid ::"+tid);
	   }
	   msg=json.toString();
	  return msg;
   }
   
   public static HashMap<String,String> getPaypalDataMap(String tid,String refid){
	   System.out.println("In getPaypalDataMap method for mgrid :: "+refid+" :: seqId :: "+tid);
	   HashMap<String,String> paypalMap=new HashMap<String,String>();
	   String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
	   String paypal_form_url=EbeeConstantsF.get("paypal.form.url","https://www.paypal.com/cgi-bin/webscr");
	   String paypal_notify_url=EbeeConstantsF.get("paypal.mgr.notify.url","http://www.eventbee.com/main/myaccount/mgrpaypalnotification.jsp");
	   String paypalmanageremail=DbUtil.getVal("select value from config where config_id='0' and name='main.paypal.account'", null);
	   DBManager dbm=new DBManager();
	   StatusObj statobj=null;
	   String amount="",month="",purpose="",userid="";
	  
	   try{
	   statobj=dbm.executeSelectQuery("select invoice_month,amount,tid,purpose,refid from mgr_paypal_payment_data where refid=CAST(? as INTEGER) and tid=?", new String[]{refid,tid});
	   if(statobj.getStatus() && statobj.getCount()>0){
		   paypalMap.put("paypalamount",dbm.getValue(0, "amount", "0.00"));
		   paypalMap.put("invoicemonth",dbm.getValue(0, "invoice_month", ""));
		   paypalMap.put("itemnumber",dbm.getValue(0, "tid", ""));
		   paypalMap.put("purpose",dbm.getValue(0,"purpose", ""));
		   paypalMap.put("refid",dbm.getValue(0,"refid", ""));
	   }
	   paypalMap.put("paypal_form_url",paypal_form_url);
	   paypalMap.put("paypalmerchantid",paypalmanageremail);
	   paypalMap.put("itemname","Invoice Charge");
	   paypalMap.put("currencycode","USD");
	   paypalMap.put("paypaltax","0.00");
	   paypalMap.put("paypallang","US");
	   paypalMap.put("paypal_notify_url",paypal_notify_url);
	   paypalMap.put("serveraddress",serveraddress);
	   }catch(Exception e){
		   System.out.println("Exception occured in paymentdata.jsp for mgrid ::"+refid+" seqid :: "+tid);
	   }

	   return paypalMap;
   }
   
   public static String getPaypalStatus(String refid,String tid){
	  System.out.println("In getPaypalStatus method for mgrid :: "+refid+" :: seqId :: "+tid);
      String paypalstatus="";
      paypalstatus=DbUtil.getVal("select status from mgr_paypal_payment_response_data where tid=?", new String[]{tid});
      System.out.println("paypalstatus in getPaypalStatus method for mgrid :: "+refid+" :: seqId :: "+tid +" :: paypalstatus ::"+paypalstatus);
      if(paypalstatus==null)paypalstatus="";
      if("Completed".equals(paypalstatus)){
    	  try{
    	  	DbUtil.executeUpdateQuery("update mgr_paypal_payment_data set status=?,updated_at=now() where tid=?", new String[]{paypalstatus,tid});
    	  	DBManager dbm=new DBManager();
    	  	StatusObj statobj=null;
    	  	String month="",purpose="",mgrid="",months="";
    	  	statobj=dbm.executeSelectQuery("select invoice_month,purpose,refid from mgr_paypal_payment_data where tid=?", new String[]{tid});
    	  	if(statobj.getStatus() && statobj.getCount()>0){
    	  		month=dbm.getValue(0,"invoice_month", "");
    	  		purpose=dbm.getValue(0,"purpose", "");
    	  		mgrid=dbm.getValue(0,"refid", "");
    	  	}
    	  	
    	  	if("invoice_paypal".equals(purpose)){
    	  		 if("total".equals(month)){
    	  			 List invoicemonths=new ArrayList();
    	  			 if(!"".equals(mgrid)) 
    	  			    invoicemonths=DbUtil.getValues("select monthofinvoice from mgr_active_monthly_invoice where mgrid=cast(? as integer) and showstatus='Y'",new String[]{mgrid}); 
    	  			  
    	  			  for(int i=0;i<invoicemonths.size();i++){
    	  				  if(i==0)
    	  					  months="'"+invoicemonths.get(i)+"'";
    	  				  else
    	  					  months=months+","+"'"+invoicemonths.get(i)+"'";
    	  			  }
    	  			  }else
    	  				  months="'"+month+"'";
    	  		if("total".equals(month))
    	  			DbUtil.executeUpdateQuery("update mgr_active_monthly_invoice set status='Charged',updated_at=now() where mgrid=cast(? as integer) and showstatus='Y' and status='Pending'",new String[]{mgrid});
    	  		else
    	  			DbUtil.executeUpdateQuery("update mgr_active_monthly_invoice set status='Charged',updated_at=now() where mgrid=cast(? as integer) and monthofinvoice=? and status='Pending'",new String[]{mgrid,month});
    	  		
    	  		 DbUtil.executeUpdateQuery("update eventlevel_active_invoice set status='Charged',paidby='manager',updated_at=now() where mgrid=cast(? as integer) and monthofinvoice in("+months+") and status='Pending'", new String[]{mgrid});
    	  		
    	  		String chkstatus=DbUtil.getVal("select 'Y' from mgr_config where name='require_charge' and value='Y' and userid=CAST(? as INTEGER)", new String[]{mgrid});
    	  		if("Y".equals(chkstatus)){
    	  			if("total".equals(month))
    	  			  DbUtil.executeUpdateQuery("update mgr_config set value='N' where name='require_charge' and value='Y' and userid=CAST(? as INTEGER)", new String[]{mgrid});
    	  		    else{
    	  		    	String pendingstatus=DbUtil.getVal("select 'Y' from mgr_active_monthly_invoice where status='Pending' and mgrid=CAST(? as INTEGER)", new String[]{mgrid});
    	  		    	if(pendingstatus==null)pendingstatus="N";
    	  		    	if("N".equalsIgnoreCase(pendingstatus))
    	  		    		DbUtil.executeUpdateQuery("update mgr_config set value='N' where name='require_charge' and value='Y' and userid=CAST(? as INTEGER)", new String[]{mgrid});
    	  		    }

    	  		  }
    	  	}
    	  }catch(Exception e){
    	    System.out.println("Exception occured in getPaypalStatus for manager :: "+refid+" :: seqId ::"+tid);
    	  } 
    	  }
      return paypalstatus;
   }
   
   
   public static String getPaypalSeqID(){
	   String seqID=DbUtil.getVal("select nextval('mgr_paypal_tid')",new String[]{});
		return seqID;
   }
   
   
   public static HashMap getExportInvoiceDetailsJson(String userid,String invoicemnth,String rtype){		
		System.out.println("get export invoice detials json: userid:"+userid+"invoice month:"+invoicemnth+"rtype:"+rtype);
		JSONObject monthlyinvoicedetails=new JSONObject();
		HashMap<String,String> currencycodemap=new HashMap<String,String>();
		ArrayList<String> fields = new ArrayList<String>();
		HashMap hmp = new HashMap();
		DBManager db=new DBManager();
		StatusObj statobj=null;
		ArrayList<HashMap<String, String>> monthlyinvoicelist = new ArrayList<HashMap<String,String>>();
		List<String> currencylist=new ArrayList<String>();
		String monthlyinvoiceqry="",msg="";
		statobj=db.executeSelectQuery("select html_symbol,currency_code  from currency_symbols", null);
		if(statobj.getStatus() && statobj.getCount()>0){
			for(int i=0;i<statobj.getCount();i++)
				currencycodemap.put(db.getValue(i,"html_symbol",""), db.getValue(i,"currency_code",""));
		}
		try{
			String status="";
			if("clear".equals(rtype))
				status="Charged";
			else
				status="Pending";
			
			monthlyinvoiceqry="select ei.eventid,ticketsqty,total_amount_owe_local_currency as total_amount_owe_local,unit_service_fee,title,currency_symbol,total_amount_owe_dollars as total_amount_owe_dollars from eventlevel_active_invoice ei where "+
	                  " mgrid=cast(? as integer) and  monthofinvoice=? and ei.status=? and unit_service_fee is not null order by title,unit_service_fee";
			statobj=db.executeSelectQuery(monthlyinvoiceqry,new String[]{userid,invoicemnth,status});
			currencylist=DbUtil.getValues("select distinct currency_symbol from eventlevel_active_invoice where mgrid=cast(? as integer) and  monthofinvoice=? and status=?",new String[]{userid,invoicemnth,status});
			
			if(currencylist.get(0)==null || "null".equalsIgnoreCase(currencylist.get(0)) || currencylist.size()==0){currencylist.clear();currencylist.add("$");}
			System.out.println("currencylist is:"+currencylist);
			fields=populateFields(currencylist);
			
			if(statobj.getStatus() && statobj.getCount()>0){
		        for(int j=0;j<statobj.getCount();j++){
		        	HashMap<String,String> monthlyinvoice = new HashMap<String, String>();
		        	monthlyinvoice.put("Total_Service_Fee_Local_Currency",CurrencyFormat.getCurrencyFormat("",db.getValue(j,"total_amount_owe_local","")+" "+currencycodemap.get(db.getValue(j, "currency_symbol", "$")),false));
		        	monthlyinvoice.put("Total_Service_Fee_Usd",CurrencyFormat.getCurrencyFormat("",db.getValue(j,"total_amount_owe_dollars",""),false));
		        	monthlyinvoice.put("Ticket_Count",db.getValue(j,"ticketsqty",""));
		        	monthlyinvoice.put("Event_Name",db.getValue(j,"title",""));
		        	monthlyinvoice.put("Service_Fee",CurrencyFormat.getCurrencyFormat("",db.getValue(j,"unit_service_fee","")+" "+currencycodemap.get(db.getValue(j, "currency_symbol", "$")),false));
		        	monthlyinvoicelist.add(monthlyinvoice);
		        }}		
			
			monthlyinvoicedetails.put("fields",fields);
			monthlyinvoicedetails.put("monthlyinvdetails",monthlyinvoicelist);
			hmp.put("monthlyinvdetails", monthlyinvoicelist);
			hmp.put("fields",fields);
		 }
		
		catch(Exception e){}	
		
		return hmp;
	}
   
   
 }
