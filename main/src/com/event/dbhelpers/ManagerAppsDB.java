package com.event.dbhelpers;

import java.util.ArrayList;

import com.event.beans.ManagerAppData;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;
import com.sun.org.apache.xpath.internal.operations.Equals;

public class ManagerAppsDB {
	public static ManagerAppData getManagerAppData(String eid){
		ManagerAppData appdata=new ManagerAppData();
		
		String showAttendee="n";
		
		String checkBaseQuestionsforTickets="select count(*) from base_profile_questions where groupid=?::bigint and contextid<>0";
		String val=DbUtil.getVal(checkBaseQuestionsforTickets, new String[]{eid});
		if(val==null)val="";
		if(!"".equals(val) && !"0".equals(val))
			showAttendee="y";
		if("n".equals(showAttendee)){
			String checkCustomQuestionsforTickets="select count(*) from subgroupattribs where groupid=?::bigint"; 
			String count=DbUtil.getVal(checkCustomQuestionsforTickets, new String[]{eid});
			if(!"".equals(count) && !"0".equals(count))
				showAttendee="y";
		}
		
		System.out.println("the show attedee::"+showAttendee);
		
		
		appdata.setShowAttendee(showAttendee);
		String appquery="select * from eventbee_manager_sellticket_settings where eventid=?::bigint";
		DBManager dbm=new DBManager();
		StatusObj stb=dbm.executeSelectQuery(appquery, new String[]{eid});
		if(stb.getStatus() && stb.getCount()>0){
			String paytype=dbm.getValue(0,"vendor_type","");
			System.out.println("in entry exists case:::"+paytype);
			appdata.setEventbeeVendor(paytype);
			appdata.setCollectattendee(dbm.getValue(0,"collect_ap",""));
			appdata.setCollectbuyer(dbm.getValue(0,"collect_bp",""));
			if("authorize.net".equals(paytype)){
				appdata.setApiLoginId(dbm.getValue(0,"attrib_1",""));
				appdata.setApiTransactionId(dbm.getValue(0,"attrib_2",""));
			}else if("braintree_manager".equals(paytype)){
				appdata.setBraintreekey(dbm.getValue(0,"attrib_1",""));
				appdata.setBrainPubKey(dbm.getValue(0,"attrib_2",""));
				appdata.setBrainPriKey(dbm.getValue(0,"attrib_3",""));
			}else if("stripe".equals(paytype)){
				appdata.setStripekey(dbm.getValue(0,"attrib_1",""));
			}
		}else{
			appdata.setCollectbuyer("y");
			String paymenttypes="select * from payment_types where refid=? and paytype='eventbee' and status='Enabled'";
			DBManager paymentdb=new DBManager();
			StatusObj paymentsbj=paymentdb.executeSelectQuery(paymenttypes, new String[]{eid});
			if(paymentsbj.getStatus() && paymentsbj.getCount()>0){
				String paytype=paymentdb.getValue(0,"attrib_5","");
				appdata.setEventbeeVendor(paytype);
				if("authorize.net".equals(paytype)){
					appdata.setApiLoginId(paymentdb.getValue(0,"attrib_2",""));
					appdata.setApiTransactionId(paymentdb.getValue(0,"attrib_3",""));
				}else if("braintree_manager".equals(paytype)){
					appdata.setBraintreekey(paymentdb.getValue(0,"attrib_2",""));
					appdata.setBrainPubKey(paymentdb.getValue(0,"attrib_3",""));
					appdata.setBrainPriKey(paymentdb.getValue(0,"attrib_4",""));
				}else if("stripe".equals(paytype)){
					appdata.setStripekey(paymentdb.getValue(0,"attrib_2",""));
				}
			}else{
				appdata.setEventbeeVendor("paypal_pro");
			}
		}
		
		
	return appdata;
	}
	
	
	public static void saveAppsData(String eid,ManagerAppData appsData){
		
		String mgrid=EventDB.getUserId(eid);
		
		String vendor=appsData.getEventbeeVendor();
		String DELETE_PAYMENT_DATA ="delete from eventbee_manager_sellticket_settings where eventid=?::bigint";
		String INSERT_PAYMENT_DATA ="insert into eventbee_manager_sellticket_settings(mgr_id,eventid,vendor_type,collect_bp,collect_ap,attrib_1,attrib_2,attrib_3) values(?,?::bigint,?,?,?,?,?,?)";
		String INSERT_CCPAYMENT_DATA="insert into ccpayment_setting_level_fields(mgr_id,merchant_key,vendor_type,card_holder_name,cvv,street,city,state,country,zip_code) values(?,?,?,?,?,?,?,?,?,?)";
		
		
		
		String collectattendee="n",collectbuyer="n";
		if("on".equals(appsData.getCollectattendee().trim()))
			collectattendee="y";
		if("on".equals(appsData.getCollectbuyer().trim()))
			collectbuyer="y";
		
		DbUtil.executeUpdateQuery(DELETE_PAYMENT_DATA, new String[]{eid});
		if("paypal_pro".equals(vendor)){
				DbUtil.executeUpdateQuery(INSERT_PAYMENT_DATA, new String[]{mgrid,eid,"paypal_pro",collectbuyer,collectattendee,"","",""});
		}else if("authorize.net".equals(vendor)){
			DbUtil.executeUpdateQuery(INSERT_PAYMENT_DATA, new String[]{mgrid,eid,"authorize.net",collectbuyer,collectattendee,appsData.getApiLoginId().trim(),appsData.getApiTransactionId().trim(),""});
			String isexists=DbUtil.getVal("select 'y' from ccpayment_setting_level_fields where vendor_type=? and mgr_id=? and merchant_key=?",new String[]{"authorize.net",mgrid,appsData.getApiLoginId().trim()});
			if(isexists==null)isexists="";
			if(!"y".equals(isexists))
				DbUtil.executeUpdateQuery(INSERT_CCPAYMENT_DATA, new String[]{mgrid,appsData.getApiLoginId(),"authorize.net","y","y","n","y","y","y","n"});
		
		}else if("braintree_manager".equals(vendor)){
			DbUtil.executeUpdateQuery(INSERT_PAYMENT_DATA, new String[]{mgrid,eid,"braintree_manager",collectbuyer,collectattendee,appsData.getBraintreekey().trim(),appsData.getBrainPubKey().trim(),appsData.getBrainPriKey().trim()});
			
			String isexists=DbUtil.getVal("select 'y' from ccpayment_setting_level_fields where vendor_type=? and mgr_id=? and merchant_key=?",new String[]{"braintree_manager",mgrid,appsData.getBraintreekey().trim()});
			if(isexists==null)isexists="";
			if(!"y".equals(isexists))
				DbUtil.executeUpdateQuery(INSERT_CCPAYMENT_DATA, new String[]{mgrid,appsData.getBraintreekey().trim(),"braintree_manager","y","y","n","n","n","n","n"});
			
		}else if("stripe".equals(vendor)){
			DbUtil.executeUpdateQuery(INSERT_PAYMENT_DATA, new String[]{mgrid,eid,"stripe",collectbuyer,collectattendee,appsData.getStripekey().trim(),"",""});
			String isexists=DbUtil.getVal("select 'y' from ccpayment_setting_level_fields where vendor_type=? and mgr_id=? and merchant_key=?",new String[]{"stripe",mgrid,appsData.getStripekey().trim()});
			if(isexists==null)isexists="";
			if(!"y".equals(isexists))
				DbUtil.executeUpdateQuery(INSERT_CCPAYMENT_DATA, new String[]{mgrid,appsData.getStripekey().trim(),"stripe","n","y","n","n","n","n","n"});
	}
	}
	
    public static ArrayList<Entity> getAllTickets(String eid){
    	String query = "select 	ticket_name,price_id from price where evt_id=CAST(? as bigint) and is_at_door='N'";
    	DBManager db = new DBManager();
    	StatusObj statobj = null;
    	ArrayList<Entity> allTicketsList = new ArrayList<Entity>();
    	statobj = db.executeSelectQuery(query,new String[]{eid});    	
    	if(statobj.getCount()>0){
    		for(int i=0;i<statobj.getCount();i++)
    		allTicketsList.add(new Entity(db.getValue(i,"price_id",""),db.getValue(i,"ticket_name","")));
    	}
	return allTicketsList;
	}
	
    public static ArrayList<Entity> getSelTickets(String eid){
    	String query = "select 	ticket_name,price_id from price where evt_id=CAST(? as bigint) and is_at_door='Y'";
    	DBManager db = new DBManager();
    	StatusObj statobj = null;
    	ArrayList<Entity> selTicketsList = new ArrayList<Entity>();
    	statobj = db.executeSelectQuery(query,new String[]{eid});    	
    	if(statobj.getCount()>0){
    		for(int i=0;i<statobj.getCount();i++)
    			selTicketsList.add(new Entity(db.getValue(i,"price_id",""),db.getValue(i,"ticket_name","")));
    	}
	return selTicketsList;
	}
    
    public static  void saveTicketsDisplay(String ticketstoshow,String ticketsnottoshow,String eid){
    	System.out.println("ticketstoshow::"+ticketstoshow+"ticketsnottoshow::"+ticketsnottoshow);
    	if((ticketstoshow!=null && !"".equals(ticketstoshow))){
    	String query="update price set is_at_door='Y' where evt_id=CAST(? as bigint) and price_id::bigint in("+ticketstoshow+")";
    	DbUtil.executeUpdateQuery(query, new String[]{eid});
    	}
    	if((ticketsnottoshow!=null && !"".equals(ticketsnottoshow))){
    	String query="update price set is_at_door='N' where evt_id=CAST(? as bigint) and price_id::bigint in("+ticketsnottoshow+")";
    	DbUtil.executeUpdateQuery(query, new String[]{eid});
    	}
    }
	
}
