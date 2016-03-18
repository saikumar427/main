package com.event.dbhelpers;

import com.event.beans.PaymentData;
import com.event.helpers.I18n;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;
import com.eventbee.util.CoreConnector;

import java.util.*;

import org.apache.log4j.Logger;
import org.apache.velocity.runtime.directive.Break;
import org.json.JSONObject;

public class PaymentSettingsDB {    
	private static final Logger log = Logger.getLogger(PaymentSettingsDB.class);

	public static Vector getAllPaymentTypes(String refid, String Purpose) {
		Vector paytypevector = new Vector();
		String GET_PAYMENT_DETAILS = "select * from payment_types where refid=? and purpose=?";
		DBManager dbmanager = new DBManager();
		HashMap hm = null;
		StatusObj stobj = dbmanager.executeSelectQuery(GET_PAYMENT_DETAILS,
				new String[] { refid, Purpose });
		if (stobj.getStatus()) {
			String[] columnnames = dbmanager.getColumnNames();
			for (int i = 0; i < stobj.getCount(); i++) {
				hm = new HashMap();
				for (int j = 0; j < columnnames.length; j++) {
					hm.put(columnnames[j],
							dbmanager.getValue(i, columnnames[j], ""));
				}
				paytypevector.add(hm);
			}

		}
		return paytypevector;
	}

	public static HashMap getPaymentTypeInfo(String refid, String Purpose,
			String Paytype) {
		String GET_PAYMENT_TYPE_INFO = "select * from payment_types where refid=? and purpose=? and paytype=?";
		DBManager dbmanager = new DBManager();
		HashMap hm = new HashMap();
		StatusObj stobj = dbmanager.executeSelectQuery(GET_PAYMENT_TYPE_INFO,
				new String[] { refid, Purpose, Paytype });
		if (stobj.getStatus()) {
			String[] columnnames = dbmanager.getColumnNames();
			for (int i = 0; i < stobj.getCount(); i++) {
				for (int j = 0; j < columnnames.length; j++) {
					hm.put(columnnames[j],
							dbmanager.getValue(i, columnnames[j], ""));
				}
			}
		}
		return hm;
	}

	public static HashMap getPaymentTypesStatus(String refid, String Purpose) {
		String GET_PAYMENT_TYPE_STATUS = "select status,paytype from payment_types where refid=? and purpose=?";
		DBManager dbmanager = new DBManager();
		StatusObj stobj = dbmanager.executeSelectQuery(GET_PAYMENT_TYPE_STATUS,
				new String[] { refid, Purpose });
		HashMap hm = new HashMap();
		Vector v = new Vector();
		if (stobj.getStatus()) {
			for (int i = 0; i < stobj.getCount(); i++) {
				hm.put(dbmanager.getValue(i, "paytype", ""),
						dbmanager.getValue(i, "status", ""));
			}
		}
		return hm;
	}

	public static int UpdatePaymentData(String refid, String Purpose,
			String Paytype, String attrib_1, String attrib_2, String attrib_4,
			String otherccfee) {
		return UpdatePaymentData(refid, Purpose, Paytype, attrib_1, attrib_2,
				attrib_4, null, null, otherccfee);
	}
	 

	public static int UpdatePaymentData(String refid, String Purpose,
			String Paytype, String attrib_1, String attrib_2, String attrib_4,
			String attrib_3, String attrib_5, String otherccfee) {
		
		StatusObj statobj = null;
		String[] insertparams = null;
		String DELETE_PAYMENT_DATA = "delete from payment_types where refid=? and purpose=? and paytype=?";
		StatusObj stb=DbUtil.executeUpdateQuery(DELETE_PAYMENT_DATA, new String[] { refid,
				Purpose, Paytype });
		insertparams = new String[10];
		insertparams[0] = refid;
		insertparams[1] = Purpose;
		insertparams[2] = Paytype;
		insertparams[3] = "Enabled";
		insertparams[4] = attrib_1;
		insertparams[5] = attrib_2;
		insertparams[6] = attrib_4;
		insertparams[7] = attrib_3;
		insertparams[8] = attrib_5;
		insertparams[9] = otherccfee;
		int refval = InsertPaymentData(insertparams);
		return refval;
	}

	public static int UpdatePaymentStatus(String refid, String purpose,
			String paytype, String Status) {
		int refval = 0;
		String UPDATE_PAYMENT_STATUS = "update payment_types set status=? where refid=? and purpose=? and paytype=?";
		StatusObj statobj = DbUtil.executeUpdateQuery(UPDATE_PAYMENT_STATUS,
				new String[] { Status, refid, purpose, paytype });
		if (statobj.getStatus()) {
			refval = 1;
		}
		return refval;
	}

	public static int InsertPaymentData(String[] params) {
		int refval = 0;
		String INSERT_PAYMENT_DATA = "insert into payment_types(refid,purpose,paytype,status,attrib_1,attrib_2,attrib_4,attrib_3,attrib_5,other_ccfee) values(?,?,?,?,?,?,?,?,?,?)";
		StatusObj statobj = DbUtil.executeUpdateQuery(INSERT_PAYMENT_DATA,
				params);
		if (statobj.getStatus()) {
			refval = 1;
		}
		return refval;
	}

	public static String getPaymentConfigStatus(String eid) {
		String yescount = DbUtil
				.getVal("select count(*) as yescount from payment_types where refid=?  and purpose='Event' and status in ('Enabled','Need Approve')",
						new String[] { eid });
		if ("0".equals(yescount))
			return "no";
		return "yes";

	}

	public static void Save(PaymentData paymentData, String eid,String purpose) {
		String configid = DbUtil
				.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",
						new String[] { eid });
		DbUtil.executeUpdateQuery(
				"delete from config where config_id=to_number(?,'999999999999999') and name='event.poweredbyEB'",
				new String[] { configid });
		DbUtil.executeUpdateQuery(
				"insert into config(name,value,config_id) values('event.poweredbyEB','yes',to_number(?,'999999999999999'))",
				new String[] { configid });

		DbUtil.executeUpdateQuery(
				"delete from config where config_id=to_number(?,'999999999999999') and name='event.ticketpage.refundpolicy.statement'",
				new String[] { configid });
		DbUtil.executeUpdateQuery(
				"insert into config(name,value,config_id) values('event.ticketpage.refundpolicy.statement',?,to_number(?,'999999999999999'))",
				new String[] { paymentData.getRefundpolicy(), configid });
		String taxtype = DbUtil
				.getVal("select value from config where name='event.tax.type' and config_id=to_number(?,'999999999999999')",
						new String[] { configid });
		if (taxtype == null)
			taxtype = "";
		if ("yes".equalsIgnoreCase(paymentData.getCcprocessingfee())) {
			System.out.println("enter into yes case");
			DbUtil.executeUpdateQuery(
					"delete from config where config_id=to_number(?,'999999999999999') and name='event.tax.amount'",
					new String[] { configid });
			DbUtil.executeUpdateQuery(
					"insert into config(name,value,config_id) values('event.tax.amount',?,to_number(?,'999999999999999'))",
					new String[] {
							paymentData.getTax() + "+" + paymentData.getCcfee(),
							configid });
			if ("".equals(taxtype))
				DbUtil.executeUpdateQuery(
						"insert into config(name,value,config_id) values('event.tax.type',?,to_number(?,'999999999999999'))",
						new String[] { "byattendee", configid });
			else
				DbUtil.executeUpdateQuery(
						"update config set value=? where name='event.tax.type' and config_id=to_number(?,'999999999999999')",
						new String[] { "byattendee", configid });
		} else if ("no".equalsIgnoreCase(paymentData.getCcprocessingfee())) {
			System.out.println("enter into no case");
			DbUtil.executeUpdateQuery(
					"delete from config where config_id=to_number(?,'999999999999999') and name='event.tax.amount'",
					new String[] { configid });
			if ("".equals(taxtype))
				DbUtil.executeUpdateQuery(
						"insert into config(name,value,config_id) values('event.tax.type',?,to_number(?,'999999999999999'))",
						new String[] { "bymanager", configid });
			else
				DbUtil.executeUpdateQuery(
						"update config set value=? where name='event.tax.type' and config_id=to_number(?,'999999999999999')",
						new String[] { "bymanager", configid });
		}

		DbUtil.executeUpdateQuery(
				"delete from event_currency where eventid=? ",
				new String[] { eid });
		DbUtil.executeUpdateQuery(
				"insert into event_currency(eventid,currency_code) values(?,?)",
				new String[] { eid, paymentData.getCurrencytype() });

		String paypalchecked = paymentData.getPaypalchecked();
		String googlechecked = paymentData.getGooglechecked();
		String eventbeechecked = paymentData.getEventbeechecked();
		String otherchecked = paymentData.getOtherchecked();
		String ebeecreditschecked = paymentData.getEbeecreditschecked();

		if ("on".equals(paypalchecked)) {
			PaymentSettingsDB.UpdatePaymentData(eid, "Event", "paypal",
					paymentData.getPaypal_merchant_accmail().trim(), null, null,
					paymentData.getPaypal_other_ccfee());
			String UPDATE_PAYMENT_LANGUAGE = "update payment_types set attrib_3=? where refid=? and purpose=? and paytype=?";
			StatusObj statobj = DbUtil.executeUpdateQuery(
					UPDATE_PAYMENT_LANGUAGE,
					new String[] { paymentData.getPaypalSupportedLanguage(),
							eid, "Event", "paypal" });

		} else {
			PaymentSettingsDB.UpdatePaymentStatus(eid, "Event", "paypal",
					"disable");
		}
		if ("on".equals(googlechecked)) {
			PaymentSettingsDB.UpdatePaymentData(eid, "Event", "google",
					paymentData.getGoogle_merchant_id(),
					paymentData.getGoogle_merchant_key(), null,
					paymentData.getGoogle_other_ccfee());

		} else {
			PaymentSettingsDB.UpdatePaymentStatus(eid, "Event", "google",
					"disable");
		}
		if ("on".equals(ebeecreditschecked)) {

			PaymentSettingsDB.UpdatePaymentData(eid, "Event", "ebeecredits",
					paymentData.getEbeecredits_payment_desc(), null, null,
					paymentData.getEbeecredits_other_ccfee());

		} else {
			PaymentSettingsDB.UpdatePaymentStatus(eid, "Event", "ebeecredits",
					"disable");
		}
		if ("on".equals(otherchecked)) {
			PaymentSettingsDB.UpdatePaymentData(eid, "Event", "other",
					paymentData.getOther_payment_desc(), null,
					paymentData.getPaymentApprovalType(),
					paymentData.getOther_other_ccfee());

		} else {
			PaymentSettingsDB.UpdatePaymentStatus(eid, "Event", "other",
					"disable");
		}
		if ("on".equals(eventbeechecked)) {
			
			
			if("braintree_manager".equalsIgnoreCase(paymentData.getEventbeeVendor().trim())){
				System.out.println("in barintree case");
				 DbUtil.executeUpdateQuery("insert into paymentsettings_track(eid,vendor,paymenttype,merchantid,publickey,privatekey,created_at)"+
				                           "select refid::bigint,attrib_5,paytype,attrib_2,attrib_3,attrib_4,now() from payment_types where refid=? and paytype='eventbee' and attrib_5='braintree_manager'" , new String[]{eid});
				 PaymentSettingsDB.UpdatePaymentData(eid,"Event","eventbee","manager",
			    		  paymentData.getBraintreekey().trim(),paymentData.getBrainPriKey().trim(),paymentData.getBrainPubKey().trim(),paymentData.getEventbeeVendor().trim(),null);
				 
			}else if("stripe".equals(paymentData.getEventbeeVendor())){
			      PaymentSettingsDB.UpdatePaymentData(eid,"Event","eventbee","manager",
			    		  paymentData.getStripekey().trim(),null,null,paymentData.getEventbeeVendor().trim(),
			    		  null);
			
			}else if("authorize.net".equals(paymentData.getEventbeeVendor())){
			      PaymentSettingsDB.UpdatePaymentData(eid,"Event","eventbee","manager",
			    		  paymentData.getApiLoginId().trim(),null,paymentData.getApiTransactionId().trim(),paymentData.getEventbeeVendor().trim(),
			    		  null);
			}else if("payulatam".equals(paymentData.getEventbeeVendor())){
				JSONObject obj=new JSONObject();
				try{
					obj.put("marchant_id",paymentData.getPayuMarchantId());
					obj.put("account_id", paymentData.getPayuAccountId());
				}catch(Exception e){
					System.out.println("Exception raised in saving payment::"+e);
				}
				String marchantdetails=obj.toString();
			    PaymentSettingsDB.UpdatePaymentData( eid,"Event","eventbee","manager",  paymentData.getPayuApiKey().trim(),marchantdetails,paymentData.getPayuApiLogin().trim(),paymentData.getEventbeeVendor().trim(),null);
			}else{
					PaymentSettingsDB.UpdatePaymentData(eid, "Event", "eventbee",
					paymentData.getEventbee_payment_by(),
					null, null,null,paymentData.getEventbeeVendor().trim(),paymentData.getEventbee_other_ccfee());
			}
		} else {
			PaymentSettingsDB.UpdatePaymentStatus(eid, "Event", "eventbee",
					"disable");
		}
	}

	public static void deleteduplicates(String eid, PaymentData paymentData) {

		StatusObj statobj = null;
		ArrayList currencylist = new ArrayList();
		DBManager dbmanager = new DBManager();
		String currencycode = "";

		String eventcurrency = "select currency_code from event_currency where eventid=?";
		statobj = dbmanager.executeSelectQuery(eventcurrency,
				new String[] { eid });
		for (int i = 0; i < statobj.getCount(); i++) {
			currencycode = dbmanager.getValue(i, "currency_code", "");
			currencylist.add(currencycode);
		}

		if (currencylist.size() > 1) {
			log.info("Duplicate entries found in event_currency for eventid:  "
					+ eid);
			log.info("Deleting duplicates..");

			for (int j = 1; j < currencylist.size(); j++) {
				currencycode = (String) currencylist.get(j);
				statobj = DbUtil.executeUpdateQuery(
						"delete from event_currency where eventid=?",
						new String[] { eid });
				System.out.println("status of remove duplicates"
						+ statobj.getStatus());

			}

			String insert_eventcurrency = "insert into event_currency(eventid,currency_code) values(?,?)";
			statobj = DbUtil.executeUpdateQuery(insert_eventcurrency,
					new String[] { eid, paymentData.getCurrencytype() });
		}

	}

	// written for Paypal Payment Settings

	public static PaymentData checkEmail(String mail, PaymentData paymentData,
			String mgrid) {
		StatusObj sb = null;
		DBManager dbmanager = new DBManager();
		String query = "select status,emailid from paypalx_chained_managers where emailid=? and mgr_id=? order by checked_at desc limit 1";
		sb = dbmanager.executeSelectQuery(query, new String[] { mail, mgrid });
		if (sb.getStatus()) {
			paymentData.setEmail(dbmanager.getValue(0, "emailid", ""));
			paymentData.setStatus(dbmanager.getValue(0, "status", ""));
		} else
			paymentData.setEmail("");
		return paymentData;
	}

	public static void insertRecord(String email, String eid, String status,
			String error, PaymentData paymentData, String mgrid) {
		if (!"".equals(eid) && eid != null)
			DbUtil.executeUpdateQuery(
					"delete from paypalx_events where eventid=CAST(? AS BIGINT)",
					new String[] { eid });

		String insertquery = "insert into paypalx_chained_managers(emailid,checked_at,status,eventid,error_desc,mgr_id,cardcollected)values(?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),?,?,?,?,?)";
		StatusObj sb = DbUtil.executeUpdateQuery(insertquery, new String[] {
				email, DateUtil.getCurrDBFormatDate(), status, eid, error,
				mgrid, null });
	}

	public static PaymentData checkManager(String mgrid,
			PaymentData paymentData, String res, String eid, String email) {
		StatusObj sb = null;
		DBManager dbmanager = new DBManager();
		try {
			String expiremnthyear = DbUtil
					.getVal("select expmnthyear from manager_card_authentication where mgr_id=? and status='Active'",
							new String[] { mgrid });
			if (expiremnthyear == null)
				expiremnthyear = "";
			if (!"".equals(expiremnthyear)) {
				String currentdate = DateUtil.getCurrDBFormatDate();
				String[] currentdatearr = GenUtil.strToArrayStr(currentdate,
						"-");
				String currentyear = currentdatearr[0];
				String currentmonth = currentdatearr[1];
				String[] expiremnthyeararr = GenUtil.strToArrayStr(
						expiremnthyear, "/");
				String expiremonth = expiremnthyeararr[0];
				String expireyear = expiremnthyeararr[1];
				if (Integer.parseInt(expireyear) <= Integer
						.parseInt(currentyear)) {
					if (Integer.parseInt(expiremonth) < Integer
							.parseInt(currentmonth))
						DbUtil.executeUpdateQuery(
								"update manager_card_authentication set status='Inactive' where mgr_id=? and status='Active'",
								new String[] { mgrid });
				}
			}
		} catch (Exception e) {
			System.out.println("Exception occured in checkManager :" + mgrid
					+ e.getMessage());
		}
		String query = "select status from manager_card_authentication where mgr_id=? order by setupdate desc limit 1";
		sb = dbmanager.executeSelectQuery(query, new String[] { mgrid });
		if (sb.getStatus())
			paymentData.setCardstatus(dbmanager.getValue(0, "status", ""));
		if ("Active".equals(paymentData.getCardstatus()))
			insertRecordIntoPaypal(email, eid, "fail", "Y", res, paymentData,
					mgrid);
		return paymentData;
	}

	public static void insertRecordIntoPaypal(String email, String eid,
			String status, String cardcollected, String error,
			PaymentData paymentData, String mgrid) {
		String insertquery = "insert into paypalx_chained_managers(mgr_id,emailid"
				+ ",checked_at,status,eventid,cardcollected,error_desc)"
				+ "values(?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),?,?,?,?)";
		DbUtil.executeUpdateQuery(insertquery, new String[] { mgrid, email,
				DateUtil.getCurrDBFormatDate(), status, eid, cardcollected,
				error });
		insertPaypalXEvents(eid);
	}

	public static void insertNoCard(PaymentData paymentData, String eid,
			String error_msg, String mgrid) {
		String insertquery = "insert into paypalx_chained_managers(mgr_id,emailid"
				+ ",checked_at,status,eventid,cardcollected,error_desc)"
				+ "values(?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),?,?,?,?)";
		DbUtil.executeUpdateQuery(insertquery,
				new String[] { mgrid, paymentData.getPaypal_merchant_accmail(),
						DateUtil.getCurrDBFormatDate(), "fail", eid, "N",
						error_msg });
		insertPaypalXEvents(eid);
	}

	// End for Paypal Payment Settings

	public static void insertPaypalXEvents(String eventid) {
		String paypal = "insert into paypalx_events(servicefee,paymentoption,eventid)"
				+ "values(to_number(?,'99999999999'),?,CAST(? AS BIGINT))";
		DbUtil.executeUpdateQuery("delete from paypalx_events where eventid=CAST(? AS BIGINT)",
				new String[] { eventid });
		DbUtil.executeUpdateQuery(paypal, new String[] { "0.00", "paypal",
				eventid });
	}

	public static String getCurrencySymbolOfCCode(String currencycode) {
		System.out.println("currencycode is:" + currencycode);
		String currencysymbol = "";
		try {
			String query = "select currency_symbol from currency_symbols where currency_code=?";
			currencysymbol = DbUtil
					.getVal(query, new String[] { currencycode });
			if (currencysymbol == null)
				currencysymbol = "$";
		} catch (Exception e) {
			System.out
					.println("Exception occured in getCurrencySymbolOfCCode in PaymentSettingsDB");
		}
		System.out.println("currencysymbol is:" + currencysymbol);
		return currencysymbol;
	}

	public static String getTaxType(String eventid) {
		String taxtype = DbUtil
				.getVal("select value from config where name='event.tax.type' and config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT))",
						new String[] { eventid });
		if (taxtype == null)
			taxtype = "byattendee";
		return taxtype;
	}

	public static HashMap<String, String> getTaxAndCCProcessFee(String eventid) {
		HashMap<String, String> hmap = new HashMap<String, String>();
		String paytype = DbUtil
				.getVal("select paytype from payment_types where status='Enabled' and refid=? and paytype in('eventbee','paypal','google') order by paytype limit 1",
						new String[] { eventid });
		if ("eventbee".equals(paytype)) {
			hmap.put("tax", "4.95");
			hmap.put("ccprocessingfee", "0.5");
			return hmap;
		} else if ("paypal".equals(paytype) || "google".equals(paytype)) {
			hmap.put("tax", "2.95");
			hmap.put("ccprocessingfee", "0.3");
		} else {
			hmap.put("tax", "0");
			hmap.put("ccprocessingfee", "0");
		}
		System.out.println("hmap is:" + hmap);
		return hmap;
	}
	
	public static Vector getMgrAllPaymentTypes(String mgrid) {
		Vector paytypevector = new Vector();
		try{
			
		String GET_PAYMENT_DETAILS = "select * from payment_types where refid::int in(select eventid from eventinfo a,config b where mgr_id=CAST(? as INTEGER)  and b.value=? and a.config_id=b.config_id "+
		 " and status!='CANCEL' and current_level in('100','200','300','400') order by created_at desc limit 2)";
		DBManager dbmanager = new DBManager();
		HashMap hm = null;
		StatusObj stobj = dbmanager.executeSelectQuery(GET_PAYMENT_DETAILS,new String[]{mgrid,I18n.getActualLangFromSession()});
		if (stobj.getStatus()) {
			String[] columnnames = dbmanager.getColumnNames();
			for (int i = 0; i < stobj.getCount(); i++) {
				hm = new HashMap();
				for (int j = 0; j < columnnames.length; j++) {
					hm.put(columnnames[j],dbmanager.getValue(i, columnnames[j], ""));
				}
				
				paytypevector.add(hm);
			}

		}
		}catch(Exception e){
			System.out.println("Exception occured in getMgrAllPaymentTypes method fro manager :: "+mgrid+" :: "+e.getMessage());
		}
		return paytypevector;
	}
	
	public static HashMap<String,String> getBrainTreeApproved(String eid){
		DBManager dbm=new DBManager();
		HashMap<String,String> hmap=new HashMap<String,String>();
		StatusObj sb=dbm.executeSelectQuery("select status,attrib_2 as key,attrib_3,attrib_4  from payment_types where refid=? and paytype='eventbee' and  attrib_5='braintree_manager' limit 1",new String[]{eid});
		if(sb.getStatus() && sb.getCount()>0){
			hmap.put("status",dbm.getValue(0,"status",""));
		    hmap.put("key",dbm.getValue(0,"key",""));
		    hmap.put("attrib3",dbm.getValue(0,"attrib_3",""));
		    hmap.put("attrib4",dbm.getValue(0,"attrib_4",""));
		}
				return hmap;
	}
	
	public static void updatePubPriKeys(PaymentData pdata,String eid){
		String query="update payment_types set attrib_3=?,attrib_4=? where paytype='eventbee' and refid=?";
		DbUtil.executeUpdateQuery(query,new String[]{pdata.getBrainPubKey(),pdata.getBrainPriKey(),eid});
	}
	
	public static void checkBraintreeEnable(String eid,PaymentData paydata){
		String mgr_id=EventDB.getUserId(eid);
		System.out.println("the manger id is::"+mgr_id);
	        String query="select private_key,public_key from braintree_enabled_users where mgr_id=cast(? as bigint) "+
                    		"and merchant_id=? and status='Enabled'";
	        DBManager dbm=new DBManager();
	        StatusObj sbj=dbm.executeSelectQuery(query,new String[]{mgr_id,paydata.getBraintreekey()});
	         if(sbj.getStatus() && sbj.getCount()>0){
	        	 	String updatequery="update payment_types set attrib_3=?,attrib_4=?,status='Enabled' where paytype='eventbee' and refid=?";
	     			DbUtil.executeUpdateQuery(updatequery,new String[]{dbm.getValue(0,"public_key",""),dbm.getValue(0,"private_key",""),eid});
	         }else{
	        	 	String insertqry="insert into braintree_enabled_users(mgr_id,eventid,created_at,merchant_id,status) values(cast(? as bigint),cast(? as bigint),now(),?,?) ";
	        		DbUtil.executeUpdateQuery(insertqry,new String[]{mgr_id,eid,paydata.getBraintreekey(),"Disabled"});
	         }
	}
	
	
	
	public static HashMap<String,HashMap<String,String>> getPaymentKeyValues(String mgrid){
		System.out.println("in getPaymentKeyValues :"+mgrid);
		HashMap<String,HashMap<String,String>> gatewaysmap=new HashMap<String,HashMap<String,String>>();
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		String query="";
		try{
			/*authorize.net*/
		query="select attrib_1,attrib_2,attrib_3,attrib_4,attrib_5 from payment_types a,eventinfo b "
				+"where a.refid::int=b.eventid and current_level in('100','200','300','400') and b.status!='CANCEL' "
				+"and mgr_id=CAST(? as INTEGER) and paytype='eventbee' and attrib_5='authorize.net' "
				+"order by created_at desc limit 1";
		statobj=dbm.executeSelectQuery(query, new String[]{mgrid});
		if(statobj.getStatus() && statobj.getCount()>0){
			if("authorize.net".equalsIgnoreCase(dbm.getValue(0,"attrib_5",""))){
				HashMap<String,String> authmap=new HashMap<String,String>();
				authmap.put("authapiid", dbm.getValue(0,"attrib_2",""));
				authmap.put("authapikey",dbm.getValue(0,"attrib_3",""));
				gatewaysmap.put("authorize", authmap);
			}
		}else{
			    HashMap<String,String> authmap=new HashMap<String,String>();
				authmap.put("authapiid","");
				authmap.put("authapikey","");
				gatewaysmap.put("authorize", authmap);
			
		}
		
		/*stripe*/
		query="select attrib_2,attrib_5 from payment_types a,eventinfo b "
				+"where a.refid::int=b.eventid and current_level in('100','200','300','400') and b.status!='CANCEL' "
				+"and mgr_id=CAST(? as INTEGER) and paytype='eventbee' and attrib_5='stripe' "
				+"order by created_at desc limit 1";
		statobj=dbm.executeSelectQuery(query, new String[]{mgrid});
		if(statobj.getStatus() && statobj.getCount()>0){
		 if("stripe".equalsIgnoreCase(dbm.getValue(0,"attrib_5", ""))){
			HashMap<String,String> stripemap=new HashMap<String,String>();
			stripemap.put("stripekey", dbm.getValue(0,"attrib_2",""));
			gatewaysmap.put("stripe", stripemap);
		 }
		}else{
			    HashMap<String,String> stripemap=new HashMap<String,String>();
				stripemap.put("stripekey", "");
				gatewaysmap.put("stripe", stripemap);
			 
		}
		
		/*braintree_manager*/
		query= "select attrib_2,attrib_3,attrib_4,attrib_5 from payment_types a,eventinfo b "
				+"where a.refid::int=b.eventid and current_level in('100','200','300','400') and b.status!='CANCEL' "
				+"and mgr_id=CAST(? as INTEGER) and paytype='eventbee' and attrib_5='braintree_manager' "
				+"order by created_at desc limit 1";
		statobj=dbm.executeSelectQuery(query, new String[]{mgrid});
		if(statobj.getStatus() && statobj.getCount()>0){
			if("braintree_manager".equalsIgnoreCase(dbm.getValue(0,"attrib_5",""))){
				HashMap<String,String> braintreemap=new HashMap<String,String>();
				braintreemap.put("brainmid", dbm.getValue(0,"attrib_2", ""));
				braintreemap.put("brainpublickey", dbm.getValue(0,"attrib_3",""));
				braintreemap.put("brainprivatekey",dbm.getValue(0,"attrib_4",""));
				gatewaysmap.put("braintree", braintreemap);
			}
		}else{
			    HashMap<String,String> braintreemap=new HashMap<String,String>();
				braintreemap.put("brainmid", "");
				braintreemap.put("brainpublickey","");
				braintreemap.put("brainprivatekey","");
				gatewaysmap.put("braintree", braintreemap);
			
		}
		
		/*payulatam*/
		query= "select attrib_2,attrib_3,attrib_4,attrib_5 from payment_types a,eventinfo b "
				+"where a.refid::int=b.eventid and current_level in('100','200','300','400') and b.status!='CANCEL' "
				+"and mgr_id=CAST(? as INTEGER) and paytype='eventbee' and attrib_5='payulatam' "
				+"order by created_at desc limit 1";
		statobj=dbm.executeSelectQuery(query, new String[]{mgrid});
		if(statobj.getStatus() && statobj.getCount()>0){
			if("payulatam".equalsIgnoreCase(dbm.getValue(0, "attrib_5", ""))){
				HashMap<String,String> payulatammap=new HashMap<String,String>();
				payulatammap.put("api_key", dbm.getValue(0,"attrib_2", ""));
				payulatammap.put("api_login", dbm.getValue(0,"attrib_3", ""));
				String accoutDetails =  dbm.getValue(0,"attrib_4","");
				try{
					JSONObject details = new JSONObject(accoutDetails);
					payulatammap.put("account_id",details.getString("account_id"));
					payulatammap.put("marchant_id",details.getString("marchant_id"));
				}catch(Exception e){
				}
				gatewaysmap.put("payulatam", payulatammap);
			}
		}else{
			HashMap<String,String> payulatammap=new HashMap<String,String>();
			payulatammap.put("api_key", "");
			payulatammap.put("api_login", "");
			payulatammap.put("account_id","");
			payulatammap.put("marchant_id","");
			gatewaysmap.put("payulatam", payulatammap);
		}
		}catch(Exception e){
			System.out.println("Exception occured in getPaymentKeyValues while getting gateways map dor manager :: "+mgrid);
		}
		return gatewaysmap;
	}
	public static String otherDesc(String mgrid, String lang, String eid){
		String desData = DbUtil.getVal("select attrib_1 from payment_types where refid=? and paytype='other'", new String[]{eid});
		System.out.println("desData : "+desData);
		if("".equals(desData) || desData==null){
			String eventId = DbUtil.getVal("select eventid from eventinfo a, config b, payment_types c where  a.config_id=b.config_id and a.mgr_id=CAST(? as INTEGER) and "+
					" a.eventid!=CAST(? as BIGINT) and b.value=? and current_level in('100','200','300','400') and a.status!='CANCEL' and c.attrib_1 is not null and c.attrib_1!=''"+
					" order by a.created_at desc limit 1", new String []{mgrid,eid,lang});
			String description = DbUtil.getVal("select attrib_1 from payment_types where refid=? and paytype='other'",new String[]{eventId});
			if("".equals(description)||description==null)
				return "";
			else
				return description;
		}else
			return desData;
			
	}
	
	public static String payUChecking(String payuApiKey, String PayuApiLogin, String eid){
		 HashMap<String,String> inputparams=new HashMap<String,String>();
			String data="";
			String s1=EbeeConstantsF.get("s1","www.eventbee.com");
			String s2=EbeeConstantsF.get("s2","www.eventbee.com");
			inputparams.put("payUapikey", payuApiKey);
			inputparams.put("payUapilogin", PayuApiLogin);
			inputparams.put("eid", eid);
			try{
				CoreConnector cc1=new CoreConnector("http://"+s1+"/embedded_reg/payuvalidate.jsp");
				cc1.setArguments(inputparams);
				cc1.setTimeout(50000);
				data=cc1.MGet();
				CoreConnector cc2=new CoreConnector("http://"+s2+"/embedded_reg/payuvalidate.jsp");
				cc2.setArguments(inputparams);
				cc2.setTimeout(50000);
				data=cc2.MGet();
			}catch(Exception e){
				System.out.println("Error While Processing Request::"+e.getMessage());		
			}
			return data.trim();
	}
	
	public static void updateCurrencyFee(String eid,String mgrid,String currency){
		System.out.println("In updateCurrencyFee method for manager :: "+mgrid+" :: eventid ::"+eid+" :: currency ::"+currency);
		try{
		String accounttype=DbUtil.getVal("select accounttype from authentication where user_id=?", new String[]{mgrid});
		if(!"Gold".equalsIgnoreCase(accounttype) && !"Platinum".equalsIgnoreCase(accounttype)){
		String currentlevel="",currentfee="1",conversionfactor="";
		String nonprofitstatus=DbUtil.getVal("select 'Y' from ebee_special_fee where eventid=?", new String[]{eid});
		if(nonprofitstatus==null)nonprofitstatus=DbUtil.getVal("select 'Y' from ebee_special_fee where userid=?", new String[]{mgrid});
		if(nonprofitstatus==null)nonprofitstatus="N";
		HashMap<String,String> evtmap=new HashMap<String,String>();
		evtmap=SpecialFeeDB.getEventMap(eid);
		currentlevel=evtmap.get("currentlevel")+"";
		if("Y".equalsIgnoreCase(nonprofitstatus))currentlevel="ln"+currentlevel;
		else currentlevel="l"+currentlevel;
		currentfee=DbUtil.getVal("select "+currentlevel+" from international_pricing where currency_code=?", new String[]{currency});
		conversionfactor=evtmap.get("conversionfactor")+"";
		currentfee=Double.toString(Double.parseDouble(currentfee)/Double.parseDouble(conversionfactor));
		DbUtil.executeUpdateQuery("update eventinfo set current_fee=CAST(? as NUMERIC),currency_update_date=now() where eventid=CAST(? as INTEGER)", new String[]{currentfee+"",eid});
		}
		}catch(Exception e){
		  System.out.println("Exception occured in updateCurrencyFee method for manager :: "+mgrid+" :: event :: "+eid+" :: currency "+currency);	
		}
	}	
	
	public static String refundpolicyListing(String eid){
		String refPolicy="";
		String mgr_id= DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)", new String []{eid});
		String lang = I18n.getActualLangFromSession();
		refPolicy = DbUtil.getVal("select value from config where name='event.ticketpage.refundpolicy.statement' and "+
				"config_id=(select a.config_id from (select config_id from config where name='event.ticketpage.refundpolicy.statement' " +
				"and value<>'')a, config b,eventinfo c where a.config_id=b.config_id and  b.value=? and b.name='event.i18n.actual.lang' "+
				"and b.config_id=c.config_id and c.config_id=a.config_id and c.mgr_id=CAST(? as integer)  order by created_at desc limit 1);", new String []{lang,mgr_id});
		return refPolicy;
		
	}
	public static String refundpolicy(String eid){
		String refPolicy="";
		String configId= DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)", new String []{eid});
		refPolicy = DbUtil.getVal("select value from config where name='event.ticketpage.refundpolicy.statement' and config_id=CAST(? AS INTEGER)", new String []{configId});
		return refPolicy;
	}
	
}
