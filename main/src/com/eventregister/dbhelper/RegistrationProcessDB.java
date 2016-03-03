package com.eventregister.dbhelper;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;

public class RegistrationProcessDB {
	private ArrayList TicketInfo=null;
	private HashMap buyerDetails=null;
	private ArrayList ProfileInfo=null;
	private HashMap EventRegData=null;
	private String eventdate=null;
	final String Transaction_Tickets="insert into transaction_tickets"
								+"(ticketstotal,fee,ticketqty,ticket_groupid,ticketprice,ticketname,groupname,tid,eventid,ticketid,discount,transaction_at)"
								+"(select (finalprice+finalfee)*qty,finalfee,qty,ticketgroupid,originalprice+originalfee,ticketname,ticketgroupname,tid,eid,ticketid,discount*qty,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') from event_reg_ticket_details_temp where tid=? and eid=?)";
	final String EVENT_ATTENDEE="insert into eventattendee(firstname,lastname,email,phone,transactionid,eventid,attendeeid,attendeekey,ticketid)(select fname,lname,email,phone,transactionid,eventid,profileid,profilekey,ticketid  from profile_base_info where transactionid=? and eventid=CAST(? AS BIGINT) and tickettype='attendeeType')";
	final String ORDER_SEQ="select sequence+1 from transaction_sequence where groupid=? and grouptype=?";
	final String ORDER_SEQ_INSERT="insert into transaction_sequence (groupid,sequence,grouptype) values(?,to_number(?,'9999999999'),'EVENT')";
	final String EVENT_REG_TRANSACTIONS_INSERT="insert into event_reg_transactions(current_discount,"
										+"discountcode,ccfee,paymentstatus,phone,userid,current_amount,transaction_date,ordernumber,"
										+"original_amount,servicefee,eventid,paymenttype,current_tax,"
										+"tid,original_discount,fname,bookingsource,lname,current_status_date,"
										+"original_tax,current_nts,email,original_nts,trackpartner,eventdate,ext_pay_id,event_closed_date," 
										+"actual_ticketqty,collected_ticketqty,currency_code,currency_conversion_factor,bookingdomain,current_service_fee,ebeefee_usd,amount_we_have,cc_vendor) " 
										+"values(to_number(?,'9999999999.99'),?,to_number(?,'9999999999.99'),?,?,?,to_number(?,'9999999999.99'),now(),?," 
										+"to_number(?,'9999999999.99'),to_number(?,'9999999999.99'),?,?,to_number(?,'9999999999.99'),?," 
										+"to_number(?,'9999999999.99'),?,?,?,now(),to_number(?,'9999999999.99'),to_number(?,'9999999999.99'),?," 
										+"to_number(?,'9999999999.99'),?,?,?,?,?::BIGINT,?::INTEGER,?,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?,?)";
	final String ORDER_SEQ_UPDATE="update transaction_sequence set sequence=to_number(?,'9999999999') where groupid=?";
	final String SOLD_QTY_UPDATE="update price set sold_qty=sold_qty+to_number(?,'9999999999') where price_id=to_number(?,'9999999999')";
	final String RECCURRING_DETAILS_UPDATE="update reccurringevent_ticketdetails set soldqty=soldqty+to_number(?,'9999999999') where ticketid=to_number(?,'9999999999') and eventdate=? and eventid=CAST(? AS BIGINT)";
	final String RECCURRING_DETAILS_INSERT="insert into reccurringevent_ticketdetails(eventid,ticketid,eventdate,soldqty,isdonation) values(CAST(? AS BIGINT),to_number(?,'9999999999'),?,to_number(?,'9999999999'),?)";

	public int  InsertRegistrationDb(String tid,String eid){
    String externalpayid="";
	RegistrationDBHelper regdbhelper=new RegistrationDBHelper();
	EventRegData=regdbhelper.getRegistrationData(tid,eid);
	buyerDetails=regdbhelper.getBuyerInfo(tid,eid);
	ProfileInfo=regdbhelper.getProfileInfo(tid,eid);
	TicketInfo=regdbhelper.getpurchasedTickets(tid);
    String source="Manager";
	if(EventRegData!=null&&EventRegData.size()>0){
	if("eventbee".equals(EventRegData.get("selectedpaytype"))){
	}
	else if("Bulk".equals(EventRegData.get("selectedpaytype"))){
		EventRegData.put("cardfee","0");
		//EventRegData.put("ebeefee","0");
		//source="Manager";
	}
	else{
	EventRegData.put("cardfee","0");

	}
	externalpayid=regdbhelper.getExternalPayId(EventRegData);
	}

	DbUtil.executeUpdateQuery("delete from transaction_tickets where tid=? and eventid=?",new String[]{tid,eid});
	StatusObj s=DbUtil.executeUpdateQuery(Transaction_Tickets,new String[]{DateUtil.getCurrDBFormatDate(),tid,eid});
		StatusObj s1=DbUtil.executeUpdateQuery(EVENT_ATTENDEE,new String[]{tid,eid});
	
	String orderseq=DbUtil.getVal(ORDER_SEQ,new String []{eid,"EVENT"});
	String paddedorderseq=null;
	String isexists=null;
	if(orderseq==null){
	orderseq="10000200";
	DbUtil.executeUpdateQuery(ORDER_SEQ_INSERT,new String[]{eid,orderseq});
	}
	else{
	StatusObj statobj=DbUtil.executeUpdateQuery(ORDER_SEQ_UPDATE,new String[]{orderseq,eid});
	
	}
	paddedorderseq=GenUtil.getLeftZeroPadded(orderseq,8,"0");
	eventdate=(String)EventRegData.get("eventdate");
	String isdonation="";
	if(TicketInfo!=null&&TicketInfo.size()>0){
	for(int k=0;k<TicketInfo.size();k++){
	HashMap hmap=(HashMap)TicketInfo.get(k);
	if("donationType".equals((String)hmap.get("ticketType")))isdonation="Yes";
	else isdonation="No";
	DbUtil.executeUpdateQuery(SOLD_QTY_UPDATE,new String[]{(String)hmap.get("ticketQuantity"),(String)hmap.get("ticketId")});
	if(eventdate!=null&&!"".equals(eventdate)){
	isexists=DbUtil.getVal("select 'yes' from reccurringevent_ticketdetails where ticketid=to_number(?,'99999999999') and eventdate=?",new String[]{(String)hmap.get("ticketId"),eventdate});
	if("yes".equals(isexists))
	DbUtil.executeUpdateQuery(RECCURRING_DETAILS_UPDATE,new String[]{(String)hmap.get("ticketQuantity"),(String)hmap.get("ticketId"),eventdate,eid});
	else
	DbUtil.executeUpdateQuery(RECCURRING_DETAILS_INSERT,new String[]{eid,(String)hmap.get("ticketId"),eventdate,(String)hmap.get("ticketQuantity"),isdonation});
	}
	}
	}
	if("".equals(eventdate)){
	eventdate=null;
	}
	String event_closed_date="";
	if(eventdate!=null&&!"".equals(eventdate)){
		String close_datequery="select est_enddate from event_dates where  to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY HH12:MI AM')=?   and eventid=CAST(? AS BIGINT)";
		event_closed_date=DbUtil.getVal(close_datequery, new String[]{eventdate,eid});
	}
	else{
		String close_datequery="select end_date from eventinfo where eventid=CAST(? AS BIGINT)";
		event_closed_date=DbUtil.getVal(close_datequery, new String[]{eid});
	}


	double current_amount=0.00,servicefee=0.00,ccfee=0.00,amount_we_have=0.00;
	try{
		servicefee=Double.parseDouble((String)EventRegData.get("ebeefee"));	
	}catch(Exception e){
		servicefee=0.00;
	}
	if("eventbee".equals(EventRegData.get("selectedpaytype"))){	 
		try{
			current_amount= Double.parseDouble((String)EventRegData.get("grandtotal"));
		}catch(Exception e){
			current_amount=0.00;
		}
		try{
			ccfee=Double.parseDouble((String)EventRegData.get("cardfee"));
		}catch(Exception e){
			ccfee=0.00;
		}
		try{
			amount_we_have=current_amount - servicefee - ccfee;
		}catch(Exception e){
			amount_we_have=current_amount - servicefee - ccfee;
		}
		
	}else{
		amount_we_have=0.00 - servicefee;
	}

	StatusObj sb1=DbUtil.executeUpdateQuery("delete from event_reg_transactions where eventid=? and tid=?",new String[]{eid,tid});
	StatusObj sb=DbUtil.executeUpdateQuery(EVENT_REG_TRANSACTIONS_INSERT,new String[]{(String)EventRegData.get("granddiscount"),(String)EventRegData.get("discountcode"),(String)EventRegData.get("cardfee"),"Completed",(String)buyerDetails.get("phone"),"0",(String)EventRegData.get("grandtotal"),paddedorderseq,(String)EventRegData.get("grandtotal"),(String)EventRegData.get("ebeefee"),eid,(String)EventRegData.get("selectedpaytype"),(String)EventRegData.get("tax"),tid,(String)EventRegData.get("granddiscount"),(String)buyerDetails.get("firstName"),source,(String)buyerDetails.get("lastName"),(String)EventRegData.get("tax"),"0",(String)buyerDetails.get("email"),"0",(String)EventRegData.get("trackurl"),eventdate,externalpayid,event_closed_date,(String)EventRegData.get("totticketsqty"),(String)EventRegData.get("collected_ticketqty"),(String)EventRegData.get("currency_code"),(String)EventRegData.get("currency_conversion_factor"),(String)EventRegData.get("context"),(String)EventRegData.get("current_service_fee"),(String)EventRegData.get("ebeefee_usd"),CurrencyFormat.getCurrencyFormat("", Double.toString(amount_we_have), true),(String)EventRegData.get("cc_vendor")});
	if(sb.getStatus())
	return Integer.parseInt((String)EventRegData.get("collected_ticketqty"));
	else
	return 0;
	}
	
	public void updateEbeeCredits(String tid,String eid,String mgrId){
		String exist=DbUtil.getVal("select 'yes' from mgr_credits_usage_history where mgr_id=?::BIGINT and tid=?", new String[]{mgrId,tid});
		if(!"yes".equals(exist)){
			String avail=DbUtil.getVal("select 'yes' from mgr_available_credits where mgr_id=?::BIGINT and available_credits>=?::NUMERIC and ?::numeric>0", new String[]{mgrId,(String)EventRegData.get("ebeefee_usd"),(String)EventRegData.get("ebeefee_usd")});
		    if("yes".equals(avail)){
		        DbUtil.executeUpdateQuery("update mgr_available_credits set used_credits=used_credits+?::NUMERIC where mgr_id=?::BIGINT", new String[]{(String)EventRegData.get("ebeefee_usd"),mgrId});
		        DbUtil.executeUpdateQuery("update mgr_available_credits set available_credits=total_credits-used_credits,updated_by='addattendee',last_updated_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') where mgr_id=?::BIGINT", new String[]{DateUtil.getCurrDBFormatDate(),mgrId});
		        DbUtil.executeUpdateQuery("insert into mgr_credits_usage_history(mgr_id,used_for_eventid,used_credits,tid,used_date) values(?::BIGINT,?::BIGINT,?::NUMERIC,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))", new String[]{mgrId,eid,(String)EventRegData.get("ebeefee_usd"),tid,DateUtil.getCurrDBFormatDate()});
		        DbUtil.executeUpdateQuery("update event_reg_details_temp set collected_servicefee=?::NUMERIC where tid=?", new String[]{(String)EventRegData.get("ebeefee"),tid});
		        DbUtil.executeUpdateQuery("update event_reg_transactions set collected_servicefee=?::NUMERIC, collected_by='beecredits' where tid=?", new String[]{(String)EventRegData.get("ebeefee"),tid});
		        DbUtil.executeUpdateQuery("update event_reg_transactions set amount_we_have=collected_servicefee-servicefee where tid=?", new String[]{tid});
		    }	
		}
		
	}
	
	public void updateExtPayId(String tid, String paytoken){
		String extpayid=DbUtil.getVal("select transaction_id from ccdata where paytoken=?", new String[]{paytoken});
		if(extpayid != null && !"".equals(extpayid))
			DbUtil.executeUpdateQuery("update event_reg_transactions set  ext_pay_id =? where tid=?", new String[]{extpayid,tid});
	}

}
