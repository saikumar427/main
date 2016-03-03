package com.eventregister.dbhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.json.JSONObject;
import org.json.JSONArray;

import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;


public class RegistrationTiketingManager {
	final String GET_TRANSACTION_ID_QUERY = "select nextval('seq_transactionid') as transactionid";
	final String GET_PAYMENT_TYPES_QUERY="select distinct paytype,attrib_1 from payment_types where refid=? and status=? and purpose=?";
	//final String GET_TICKETS_QUERY="select groupname,ticket_groupid,price_id,ticket_name from tickets_info where eventid=?";
	final String GET_TICKETS_QUERY="select a.groupname,a.ticket_groupid,b.price_id,b.ticket_name from event_ticket_groups a, group_tickets c, price b where b.evt_id=CAST(? AS BIGINT) "+
	"and a.ticket_groupid=c.ticket_groupid AND to_number(c.price_id, '999999999') = b.price_id";
	final String BUYER_BASE_INFO="select fname,lname,email,phone from buyer_base_info";
	final String BUYER_CUSTOM_QUESTIONS="select attribid from buyer_custom_questions where eventid=CAST(? AS BIGINT)";
	final String GET_SELECTED_TICKETS="select ticketid,ticketname,tickettype,qty as ticketqty " +
			"from event_reg_ticket_details_temp where tid=?";
	final String GET_PURCHASED_TICKETS="select distinct ticketid,ticketname,ticketqty  " +
	"from transaction_tickets where tid=?";
	

	private String  transactionid=null;
	private int selectedTicketsQty=0;
	private int collectedTicketsQty=0;
	public	String  createNewTransaction(String eventid,HashMap contextMap){
		try{
			transactionid=getTransactionId();
			DbUtil.executeUpdateQuery("insert into event_reg_details_temp(tid,useragent,eventid,transactiondate,trackurl,clubuserid,ticketurlcode,context,selectedpaytype,current_action,discountcode) values(?,?,?,now(),?,?,?,?,?,?,?)",new String[]{transactionid,(String)contextMap.get("useragent"),eventid,(String)contextMap.get("trackurl"),(String)contextMap.get("clubuserid"),(String)contextMap.get("ticketurlcode"),(String)contextMap.get("context"),"Bulk","profile page",(String)contextMap.get("discountcode")});
			if(!"".equals((String)contextMap.get("trackurl"))&&(String)contextMap.get("trackurl")!=null)
			DbUtil.executeUpdateQuery("insert into trackURL_transaction(transactionid,trackingcode) values(?,?)",new String [] {transactionid,(String)contextMap.get("trackurl")});
			/*if((String)contextMap.get("eventdate")!=null&&!"".equals((String)contextMap.get("eventdate")))
			DbUtil.executeUpdateQuery("update event_reg_details_temp set eventdate=? where tid=?",new String [] {(String)contextMap.get("eventdate"),transactionid});*/
	    }catch(Exception e){
		    System.out.println("Exception in createNewTransaction--"+e.getMessage());	
		 }
		return transactionid;
		}
	
	public void updateTransaction(String tid,String eventid,HashMap contextMap){
		DbUtil.executeUpdateQuery("update event_reg_details_temp set discountcode=?,current_action='profile page' where tid=?",new String [] {(String)contextMap.get("discountcode"),tid});
	}
	
	public void updateTransactionEventdate(String tid,String eventid,String eventdate){
		DbUtil.executeUpdateQuery("update event_reg_details_temp set eventdate=? where tid=?",new String [] {eventdate,tid});
	}


		String getTransactionId(){
	 	String transid=DbUtil.getVal(GET_TRANSACTION_ID_QUERY,new String[]{});
		String transactionid="RK"+EncodeNum.encodeNum(transid).toUpperCase();
		return transactionid;
		}

		public void InsertTicketDetailsToDb(HashMap<String,Object> ticketHash,String tid,String eid){
		System.out.println("in InsertTicketDetailsToDb");
		try{
		String qty=(String)ticketHash.get("qty");
		String price=(String)ticketHash.get("originalPrice");
		String discount=(String)ticketHash.get("discount");
		String fee=(String)ticketHash.get("originalFee");
		String finalPrice=(String)ticketHash.get("finalPrice");
		String ticketname=(String)ticketHash.get("ticketName");
		String finalprocessfee=(String)ticketHash.get("finalFee");
		String ticketGroupid=(String)ticketHash.get("ticketGroupId");
		String ticketType=(String)ticketHash.get("ticketType");
		String ticketid=(String)ticketHash.get("ticketid");
		String eventdate=(String)ticketHash.get("eventdate");
		String groupname=(String)ticketHash.get("groupname");
		JSONArray seat_index=(JSONArray)ticketHash.get("seat_index");
		if(finalPrice==null||"".equals(finalPrice)){
		finalPrice=price;
		}
		if(Double.parseDouble(finalPrice)==Double.parseDouble(price)){
		discount="0";
		}
		if(fee==null)	fee="0";
		if(finalprocessfee==null)	finalprocessfee=fee;
		try{
		selectedTicketsQty+=Integer.parseInt(qty);
		}
		catch(Exception e)
		{
		selectedTicketsQty+=0;
		}
		try{
			if(Double.parseDouble(finalPrice)>0)
				collectedTicketsQty+=Integer.parseInt(qty);
		}catch(Exception e){
			collectedTicketsQty+=0;
		}
		if(discount==null||"".equals(discount))	discount="0";
		StatusObj sb=DbUtil.executeUpdateQuery("insert into event_reg_ticket_details_temp(tid,ticketid,tickettype,qty,originalprice,originalfee,discount,finalprice,ticketname,finalfee,ticketgroupid,eid,ticketgroupname,transaction_at) values(?,to_number(?,'9999999999'),?,to_number(?,'9999999999'),to_number(?,'9999999999.99'),to_number(?,'9999999999.99'),to_number(?,'9999999999.99'),to_number(?,'9999999999.99'),?,to_number(?,'9999999999.99'),?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))",
		new String[]{tid,ticketid,ticketType,qty,price,fee,discount,finalPrice,ticketname,finalprocessfee,ticketGroupid,eid,groupname,DateUtil.getCurrDBFormatDate()});
		String lock_query="insert into event_reg_locked_tickets (eventid,tid,locked_qty,ticketid,eventdate,locked_time) values (?,?,to_number(?,'999999999999'),to_number(?,'999999'),?,now())";
		StatusObj lockedsb=DbUtil.executeUpdateQuery(lock_query, new String []{eid,tid,qty,ticketid,eventdate});
		System.out.println("status of insertinto locked table:"+lockedsb.getStatus());
		String Blockseats_query="insert into event_reg_block_seats_temp (eventid,seatindex,transactionid,blocked_at,ticketid,eventdate) values (?,?,?,now(),to_number(?,'9999999999999'),?)";
			System.out.println("seat indeces length: "+seat_index.length());
	    for(int i=0;i<seat_index.length();i++){
			DbUtil.executeUpdateQuery(Blockseats_query, new String[]{eid,(String)seat_index.get(i),tid,ticketid,eventdate});
			
		}
		   }
	   catch(Exception e){
		   System.out.println("Exception in InsertTicketDetailsToDb--"+e.getMessage());
	   }

		}

	   public void setTransactionAmounts(String eventid, String tid){
		   try{
			   Double taxad=0.0;
	    String taxpercent=DbUtil.getVal("select value from config where name='event.tax.amount' and config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[]{eventid} );
		if(taxpercent==null) taxpercent="0";
		try{
			String taxarr[]=taxpercent.split("\\+");
		    if(taxarr.length==2)
		   {taxpercent=taxarr[0];
		    taxad=Double.parseDouble(taxarr[1]);
		   }
		   Double.parseDouble(taxpercent);
		}catch(Exception e){
			System.out.println("exeception::"+e.getMessage());
			   taxpercent="0";
			   taxad=0.0;
		}
		String total="0.00";
		String discountamt="0.00";
		String nettotal="0.00";
		String query="select sum((originalprice+originalfee)*qty) as total,sum((finalprice+finalfee)*qty) as netamount,sum(discount*qty) as disamount from event_reg_ticket_details_temp where tid=?";
		DBManager dbmanager=new DBManager();
		StatusObj sb=dbmanager.executeSelectQuery(query,new String []{tid});
		if(sb.getStatus()){
		discountamt=dbmanager.getValue(0,"disamount","0");
		total=dbmanager.getValue(0,"total","0");
		nettotal=dbmanager.getValue(0,"netamount","0");
		}
		Map<String,String> Feesmap=getFessDetails(eventid);
		//String ebeefee=Feesmap.get("ebeefee");
		HashMap<String,String> ebeefeeMap=getEbeeFee(eventid);
		String ebeefee=ebeefeeMap.get("finalebeefee");
		String ebeefee_usd=ebeefeeMap.get("ebeefee_usd");
		 String cardfactor=Feesmap.get("card_factor");
	     String cardbase=Feesmap.get("card_base");

		if(total==null) total="0.00";
		if(discountamt==null) discountamt="0.00";
		if(nettotal==null) nettotal="0.00";
		if("0.00".equals(nettotal))
			   taxad=0.00;
		DbUtil.executeUpdateQuery("update event_reg_details_temp set totalamount=CAST(? AS NUMERIC),granddiscount=CAST(? AS NUMERIC),nettotal=CAST(? AS NUMERIC) where tid=?",new String[]{total,discountamt,nettotal,tid});
		String tax=DbUtil.getVal("select (CAST(? AS NUMERIC) * nettotal/100)+cast(? as numeric) as totaltax from event_reg_details_temp where tid=?",new String [] {taxpercent,taxad+"",tid} );
		if(tax==null) tax="0.00";
		
		sb=DbUtil.executeUpdateQuery("update event_reg_details_temp set grandtotal=(nettotal+CAST(? AS NUMERIC)),tax=CAST(? AS NUMERIC),ebeefee=("+ebeefee+"*(CAST(? AS NUMERIC))),ebeefee_usd="+ebeefee_usd+"*(to_number(?,'999999999999')),cardfee=(nettotal*CAST(? AS NUMERIC)+("+cardbase+")) where tid=?",new String[]{tax,tax, collectedTicketsQty+"",collectedTicketsQty+"",cardfactor,tid});
		
		sb=DbUtil.executeUpdateQuery("update event_reg_details_temp set totticketsqty=to_number(?,'999999999999'),collected_ticketqty=to_number(?,'999999999999'),currency_conversion_factor=?::numeric,currency_code=?,current_service_fee=?::numeric where tid=?",new String[]{selectedTicketsQty+"",collectedTicketsQty+"",ebeefeeMap.get("conv_factor"),ebeefeeMap.get("currency_code"),ebeefee,tid});
		
		
		
		   }
	    catch(Exception e){
			System.out.println("Exception in setTransactionAmounts"+e.getMessage());
		}
		}
	   
	   public HashMap<String,String> getEbeeFee(String eid){
		   HashMap<String,String> feeMap=new HashMap<String, String>();
		   String ebeefee=DbUtil.getVal("select current_fee from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid});
		   if(ebeefee==null || "".equals(ebeefee)){
			   ebeefee=DbUtil.getVal("select l100 from ebee_special_fee where userid='0' and eventid='0'", null);;
		   }
		   String conv_factor="1";
		   String currency_code="USD";
		   DBManager db=new DBManager();
		   StatusObj sb=db.executeSelectQuery("select conversion_factor,b.currency_code as currency from currency_symbols a,event_currency b where b.eventid=? and a.currency_code=b.currency_code", new String[]{eid});
		   if(sb.getStatus()){
			   conv_factor=db.getValue(0, "conversion_factor", "1");
			   currency_code=db.getValue(0, "currency", "USD");
		   }
		   feeMap.put("conv_factor", conv_factor);
		   feeMap.put("currency_code", currency_code);
		   feeMap.put("ebeefee_usd", (Double.parseDouble(ebeefee))+"");
		   double finalebeefee=1;
		   try{
			   finalebeefee=(Double.parseDouble(ebeefee))*(Double.parseDouble(conv_factor));
		   }catch(Exception e){
			   System.out.println("Exception occured in getEbeeFee(eid:"+eid+")."+e.getMessage());
		   }
		   feeMap.put("finalebeefee", finalebeefee+"");
		   return feeMap;
	   }

		public HashMap getRegTotalAmounts(String tid){
		String query="select totalamount, granddiscount, nettotal, tax, grandtotal,status from event_reg_details_temp where tid=?";
		DBManager dbmanager=new DBManager();
		String totalamount="0.00";
		String granddiscount="0.00";
		String netamount="0.00";
		String tax="0.00";
		String grandtotal="0.00";
		HashMap <String,String>hm=new HashMap();
	    try{
		StatusObj sb=dbmanager.executeSelectQuery(query,new String []{tid});
		if(sb.getStatus()){
		totalamount=dbmanager.getValue(0,"totalamount","0");
		granddiscount=dbmanager.getValue(0,"granddiscount","0");
		netamount=dbmanager.getValue(0,"nettotal","0");
		tax=dbmanager.getValue(0,"tax","0");
		grandtotal=dbmanager.getValue(0,"grandtotal","0");
		}
		if(totalamount==null) totalamount="0";
		if(granddiscount==null) granddiscount="0";
		if(netamount==null) netamount="0";
		if(tax==null) tax="0";
		if(grandtotal==null) grandtotal="0";
		hm.put("status",dbmanager.getValue(0,"status","0"));
		hm.put("totamount",CurrencyFormat.getCurrencyFormat("",totalamount,true));
		hm.put("disamount",CurrencyFormat.getCurrencyFormat("",granddiscount,true));
		hm.put("netamount",CurrencyFormat.getCurrencyFormat("",netamount,true));
		hm.put("tax",CurrencyFormat.getCurrencyFormat("",tax,true));
		hm.put("grandtotamount",CurrencyFormat.getCurrencyFormat("",grandtotal,true));
	    }
	    catch(Exception e){
			System.out.println("Exception in getRegTotalAmounts"+e.getMessage());
		}
		return hm;
		}

		public void fillAmountDetails(HashMap amountsMap, JSONObject jsonObj,String tid){
		JSONObject obj=new JSONObject();
		try{
		obj.put("tid",tid);
		obj.put("totamount",amountsMap.get("totamount"));
		obj.put("disamount",amountsMap.get("disamount"));
		obj.put("netamount",amountsMap.get("netamount"));
		obj.put("tax",amountsMap.get("tax"));
		obj.put("grandtotamount",amountsMap.get("grandtotamount"));
		jsonObj.put("amounts",obj);
		}
		catch(Exception e){
	System.out.println("Exception in fillAmountDetails(): "+e.getMessage());

		}
		}

		public ArrayList <HashMap> getSelectedTickets(String eid,String tid){
		ArrayList  <HashMap>ticketsList=new ArrayList();
	    try{
	    	StatusObj sb;
		DBManager dbmanager=new DBManager();
		String isCompletedTransaction=DbUtil.getVal("select tid from event_reg_transactions where tid=?", new String[]{tid});
		if(isCompletedTransaction!=null)
		sb=dbmanager.executeSelectQuery(GET_PURCHASED_TICKETS,new String []{tid});
		else
			sb=dbmanager.executeSelectQuery(GET_SELECTED_TICKETS,new String []{tid});
		if(sb.getStatus())
		{
		for(int index=0;index<sb.getCount();index++){
		HashMap ticketMap=new HashMap();
		ticketMap.put("ticketName",dbmanager.getValue(index,"ticketname",""));
		ticketMap.put("selectedTicket",dbmanager.getValue(index,"ticketid",""));
		ticketMap.put("qty",dbmanager.getValue(index,"ticketqty",""));
		ticketMap.put("type",dbmanager.getValue(index,"tickettype",""));
		ticketsList.add(ticketMap);
		}
		}
	    }
	    catch(Exception e){
		System.out.println("Exception in getSelectedTickets(): "+e.getMessage());
		}
		return ticketsList;
		}

		public ArrayList <String>  getTicketIdsForBaseProfiles(String eventid){
		  String query="select ticketid from eventbaseprofiletickets where eventid=CAST(? AS BIGINT)";
		  ArrayList<String> baseprofileTickets=new ArrayList();
		  try{
		   DBManager db=new DBManager();
		   StatusObj sb=db.executeSelectQuery(query,new String[]{eventid} );
		   if(sb.getStatus()){
		     for(int k=0;k<sb.getCount();k++){
		     baseprofileTickets.add(db.getValue(k,"ticketid",""));
		     }
		   }
	      }
	     catch(Exception e){
			 System.out.println("Exception in getTicketIdsForBaseProfiles(): "+e.getMessage());
		 }
		return baseprofileTickets;
		}


		public ArrayList<String> getBuyerSpecificAttribs(String eventid){
		ArrayList <String>attribidList=new ArrayList();
		try{
		 DBManager dbmanager=new DBManager();
		 StatusObj sb=dbmanager.executeSelectQuery(BUYER_CUSTOM_QUESTIONS,new String[]{eventid});
		 if(sb.getStatus()){
		    for(int p=0;p<sb.getCount();p++){
		    attribidList.add(dbmanager.getValue(p,"attribid",""));
		   }
		  }
	     }
	     catch (Exception e){
		   System.out.println("Exception in getBuyerSpecificAttribs: "+e.getMessage());
		 }
		return attribidList;
		}



		public Vector <HashMap>getAllPaymentTypes(String eventid,String purpose){
		Vector <HashMap>payVector=new Vector();
		try{
		DBManager dbmanager=new DBManager();
		StatusObj sb=dbmanager.executeSelectQuery(GET_PAYMENT_TYPES_QUERY,new String[]{eventid,"Enabled","Event"});
		if(sb.getStatus()){
		for(int i=0;i<sb.getCount();i++){
		HashMap <String,String>hm=new HashMap();
		hm.put("paytype",dbmanager.getValue(i,"paytype",""));
		hm.put("desc",dbmanager.getValue(i,"attrib_1",""));
		payVector.add(hm);
		}
		}
	    }
	    catch(Exception e){
	    System.out.println("Exception in getAllPaymentTypes"+e.getMessage());
		}
		return payVector;
		}


		public HashMap<String,String> getBuyerDeails(String transactionid){
		HashMap <String,String>buyerMap=new HashMap();
		try{
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(BUYER_BASE_INFO,new String[]{transactionid});
		if(sb.getStatus()){
		buyerMap.put("fname",db.getValue(0,"fname",""));
		buyerMap.put("lname",db.getValue(0,"lname",""));
		buyerMap.put("email",db.getValue(0,"email",""));
		buyerMap.put("phone",db.getValue(0,"phone",""));
		}
	    }
	    catch(Exception e){
		  System.out.println("Exception in getBuyerDeails"+e.getMessage());
	    }

		return buyerMap;
		}


	    public HashMap<String,HashMap> getTicketDetails(String eid){
	    HashMap <String,HashMap>ticketsMap=new HashMap();
	    DBManager db=new DBManager();
	    StatusObj sb=db.executeSelectQuery(GET_TICKETS_QUERY,new String[]{eid});
	    if(sb.getStatus()){
	    for(int i=0;i<sb.getCount();i++){
	    HashMap <String,String> priceMap=new HashMap();
	    priceMap.put("ticketname",db.getValue(i,"ticket_name",""));
	    priceMap.put("groupname",db.getValue(i,"groupname",""));
	    priceMap.put("ticket_groupid",db.getValue(i,"ticket_groupid",""));
	    priceMap.put("price_id",db.getValue(i,"price_id",""));
	    ticketsMap.put(db.getValue(i,"price_id",""),priceMap);
	    }
	    }
	     return ticketsMap;

	    }
	   public  String getVelocityTemplate(String eid,String purpose){
	    	String template="";
	    	try{
	    	template=DbUtil.getVal("select content from reg_flow_templates where eid=CAST(? AS BIGINT) and purpose=?",new String[]{eid,purpose});

	    	if(template==null)
	    	template=DbUtil.getVal("select content from reg_flow_templates where  purpose=?",new String[]{purpose});
	    	}
	    	catch(Exception e){

	    	System.out.println("Exception in getVelocityTemplate"+e.getMessage());
	    	}

	    	return template;

	    	}
	   
	   
	   
	public Vector getRecurringEventDates(String eventid){
		   Vector vec=new Vector();
		   String query="select  to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY HH12:MI AM') as evt_start_date"
			      +" from event_dates where eventid=CAST(? AS BIGINT) order by (zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS aaa') as text) as time ))";
		   DBManager db=new DBManager();  
		   StatusObj stob=db.executeSelectQuery(query,new String[]{eventid} );
		   if(stob.getStatus()){
		   for(int i=0;i<stob.getCount();i++){
		   vec.add(db.getValue(i,"evt_start_date",""));
		   }
		   }
		   return vec;
		   }

	
	public String getEventCurrencyFormat(String eid){
	String currency="";
	currency=DbUtil.getVal("select html_symbol from currency_symbols where currency_code=(select currency_code from event_currency where eventid=?)",new String[]{eid});
	if(currency==null)
		currency="$";	
	return currency;	
	}

	public Map<String,String> getFessDetails(String eventid){
		Map <String,String>hm=new HashMap<String,String>();

		String query="select ebee_base as ebeefee,card_factor,card_base from ebeefees where ref1=? and purpose='EVENT_REGISTRATION'";
		DBManager dbManager=new DBManager();
		StatusObj sb=dbManager.executeSelectQuery(query,new String[]{eventid});


		if(sb.getStatus()){
		hm.put("ebeefee",dbManager.getValue(0,"ebeefee",""));
		hm.put("card_factor",dbManager.getValue(0,"card_factor",""));
		hm.put("card_base",dbManager.getValue(0,"card_base",""));

		}
		else{
		String defaultquery="select ebee_base as ebeefee,card_factor,card_base from ebeefees where ref1 is null and purpose='EVENT_REGISTRATION'";
		sb=dbManager.executeSelectQuery(defaultquery,new String[]{});
		if(sb.getStatus()){
		hm.put("ebeefee",dbManager.getValue(0,"ebeefee",""));
		hm.put("card_factor",dbManager.getValue(0,"card_factor",""));
		hm.put("card_base",dbManager.getValue(0,"card_base",""));

		}
		}

		return hm;
		}
	
	public boolean getSeatingEnabled(String eid){
		String value=DbUtil.getVal("select value from config where name='event.seating.enabled' " +
				"and config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT))", new String[]{eid});
		if("yes".equalsIgnoreCase(value))
			return true;
		else 
			return false;
	}
	
	public HashMap getSeatingCodeDetails(String eid,String tid,String eventdate,String ticketids){
		System.out.println("ticketids: "+ticketids);
		String []arrayticketids=ticketids.split(",");
		HashMap seatingdetails=new HashMap();
		if(" ".equals(eventdate))eventdate="";
		String query="select seatcode,seatindex from venue_seats where venue_id =to_number((select value from config where name='event.seating.venueid' and config_id in (select config_id from eventinfo where eventid=CAST(? AS BIGINT))),'999999999') and seatindex in(select seatindex from event_reg_block_seats_temp where eventid=? and transactionid=? and ticketid=to_number(?,'999999999999999'))";
		if(!"".equals(eventdate)){
			query="select seatcode,seatindex from venue_seats where venue_id =to_number((select value from config where name='event.seating.venueid' and config_id in (select config_id from eventinfo where eventid=CAST(? AS BIGINT))),'999999999') and seatindex in(select seatindex from event_reg_block_seats_temp where eventid=? and transactionid=? and ticketid=to_number(?,'999999999999999') and eventdate=?)";		
		}
		DBManager db=new DBManager();
		StatusObj Sel_tic_sb;	
		for(int k=0;k<arrayticketids.length;k++){
			ArrayList seatcodes=new ArrayList();
			ArrayList seatindeces=new ArrayList();
			String ticketid=arrayticketids[k];
			if(!"".equals(eventdate))
				Sel_tic_sb=db.executeSelectQuery(query,new String[]{eid,eid,tid,ticketid,eventdate});
			else
				Sel_tic_sb=db.executeSelectQuery(query,new String[]{eid,eid,tid,ticketid});
			if(Sel_tic_sb.getStatus()&&Sel_tic_sb.getCount()>0){
				for(int i=0;i<Sel_tic_sb.getCount();i++){
					String seatcode=db.getValue(i, "seatcode", "");
					String seatindex=db.getValue(i, "seatindex", "");
					System.out.println("seatcode: "+seatcode+" tickid: "+ticketid);
					seatcodes.add(seatcode);
					seatindeces.add(seatindex);
				}
				
			}
			seatingdetails.put(ticketid, seatcodes);
			seatingdetails.put(ticketid+"_index", seatindeces);
			
		}
		
		return seatingdetails;
	}
	public HashMap getSeatingDetails(String eid,String tid,String eventdate,ArrayList ticketids){
		//String []arrayticketids=ticketids.split(",");
		HashMap seatingdetails=new HashMap();
		
		if(" ".equals(eventdate))eventdate="";
		String query="select seatcode,venue_id,section_id,seatindex from venue_seats where venue_id =to_number((select value from config where name='event.seating.venueid' and config_id in (select config_id from eventinfo where eventid=CAST(? AS BIGINT))),'999999999') and seatindex in(select seatindex from event_reg_block_seats_temp where eventid=? and transactionid=? and ticketid=to_number(?,'9999999999999999'))";
		if(!"".equals(eventdate)){
			query="select seatcode,venue_id,section_id,seatindex from venue_seats where venue_id =to_number((select value from config where name='event.seating.venueid' and config_id in (select config_id from eventinfo where eventid=CAST(? AS BIGINT))),'999999999') and seatindex in(select seatindex from event_reg_block_seats_temp where eventid=? and transactionid=? and ticketid=to_number(?,'9999999999999999') and eventdate=?)";		
		}
		DBManager db=new DBManager();
		StatusObj Sel_tic_sb;
		for(int k=0;k<ticketids.size();k++){
			ArrayList seatcodes=new ArrayList();
			ArrayList seat_index=new ArrayList();
			String ticketid=(String)ticketids.get(k);
			if(!"".equals(eventdate))
				Sel_tic_sb=db.executeSelectQuery(query,new String[]{eid,eid,tid,ticketid,eventdate});
			else
				Sel_tic_sb=db.executeSelectQuery(query,new String[]{eid,eid,tid,ticketid});
			if(Sel_tic_sb.getStatus()&&Sel_tic_sb.getCount()>0){
				for(int i=0;i<Sel_tic_sb.getCount();i++){
					String seatcode=db.getValue(i, "seatcode", "");
					String seatindex=db.getValue(i,"seatindex","");
					seatcodes.add(seatcode);
					seat_index.add(seatindex);
					HashMap hm=new HashMap();
					hm.put("venue_id",db.getValue(i,"venue_id",""));
					hm.put("section_id",db.getValue(i,"section_id",""));
					hm.put("seatindex",seatindex);
					seatingdetails.put(ticketid+"_"+seatindex+"_"+seatcode,hm);
				}
				
			}
			seatingdetails.put(ticketid, seatcodes);
			seatingdetails.put("seatindex_"+ticketid, seat_index);
		}
		
		return seatingdetails;
		
	}


	public ArrayList getTicketIds(String eventid) {
		 String query="select price_id from price where evt_id=CAST(? AS BIGINT)";
		  ArrayList profileTickets=new ArrayList();
		  try{
		   DBManager db=new DBManager();
		   StatusObj sb=db.executeSelectQuery(query,new String[]{eventid} );
		   if(sb.getStatus()){
		     for(int k=0;k<sb.getCount();k++){
		     profileTickets.add(db.getValue(k,"price_id",""));
		     }
		   }
	     }
	    catch(Exception e){
			 System.out.println("Exception in getTicketIdsForBaseProfiles()"+e.getMessage());
		 }
		return profileTickets;
	}
	

	public void deleteTempDetails(String tid){
		//StatusObj sb=DbUtil.executeUpdateQuery("delete from event_reg_ticket_details_temp where tid=?",new String[]{tid});
		DbUtil.executeUpdateQuery("delete from event_reg_block_seats_temp where transactionid=?", new String[]{tid});
		DbUtil.executeUpdateQuery("delete from event_reg_locked_tickets where tid=?", new String[]{tid});

	}
	
	public void updateProfileStatus(String tid){
		String profilesetid=DbUtil.getVal("select profileid from buyer_base_info where transactionid=?", new String[]{tid});
		DbUtil.executeUpdateQuery("update profile_base_info set profilestatus ='Completed',profile_setid=CAST(? as INTEGER) where transactionid=?",new String [] {profilesetid,tid});
		DbUtil.executeUpdateQuery("update buyer_base_info set profilestatus='Completed' where transactionid=?",new String [] {tid});
	}
	
	public void updateEbeeFeeInTemp(String tid){
		DbUtil.executeUpdateQuery("update event_reg_details_temp set ebeefee=0,collected_ticketqty=0 where tid=?",new String[]{tid});
	}
	
	public void updatePaymentType(String tid){
		DbUtil.executeUpdateQuery("update event_reg_details_temp set selectedpaytype='eventbee' where tid=?", new String[]{tid});
	}
	
	public void updatePaytype(String tid, String currentAction){
		DbUtil.executeUpdateQuery("update event_reg_details_temp set current_action=?,status='Completed' where tid=?",new String[]{currentAction, tid});	
	}
	
	public void updatePaytype(String tid, String currentAction,String ccvendor){
		DbUtil.executeUpdateQuery("update event_reg_details_temp set current_action=?,cc_vendor=? where tid=?",new String[]{currentAction, ccvendor, tid});	
	}
	
	public void fillseatingstatus(String tid,String eid,String eventdate){
		RegistrationDBHelper regdb=new RegistrationDBHelper();
		//RegistrationTiketingManager regmgr=new RegistrationTiketingManager();
		ArrayList profileInfo=regdb.getallProfileInfo(tid,eid);
		//ArrayList ticketids=regmgr.getTicketIds(eid);
		ArrayList ticketids=getTicketIds(eid);
		//HashMap seatingdetails=regmgr.getSeatingDetails(eid, tid, eventdate, ticketids);
		HashMap seatingdetails=getSeatingDetails(eid, tid, eventdate, ticketids);
		if(profileInfo!=null&&profileInfo.size()>0){
			for(int i=0;i<profileInfo.size();i++){
				HashMap profile=(HashMap)profileInfo.get(i);
				String seatcode=(String)profile.get("seatCodes");
				String profilekey=(String)profile.get("profilekey");
				String ticketid=(String)profile.get("ticketid");
				try{
					if(!"".equals(seatcode)||seatcode!=null){
						ArrayList seatcodes_tktID=(ArrayList)seatingdetails.get(ticketid);
						ArrayList seatindex_tktID=(ArrayList)seatingdetails.get("seatindex_"+ticketid);
						int indexofseatcode=seatcodes_tktID.indexOf(seatcode);
						String seatindex_atseatcode=(String)seatindex_tktID.get(indexofseatcode);
						seatcodes_tktID.remove(indexofseatcode);
						seatindex_tktID.remove(indexofseatcode);
						HashMap seatinghm=(HashMap)seatingdetails.get(ticketid+"_"+seatindex_atseatcode+"_"+seatcode);
						seatinghm.put("eventid",eid);
						seatinghm.put("eventdate",eventdate);
						seatinghm.put("ticketid",ticketid);
						seatinghm.put("profilekey",profilekey);
						seatinghm.put("tid",tid);
						
						regdb.insertSeatBookingStatus(seatinghm);
					}
			}catch(Exception e){	
			}
			}
	}
	}
	
	public void updateTax(String tid,String eid){
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		String ptypeqry="select selectedpaytype,cc_vendor from event_reg_details_temp where tid=? and eventid=?";
		statobj=dbm.executeSelectQuery(ptypeqry, new String[]{tid,eid});
		if(statobj.getStatus() && statobj.getCount()>0){
			if("Bulk".equalsIgnoreCase(dbm.getValue(0,"selectedpaytype","")) && !"braintree_eventbee".equalsIgnoreCase(dbm.getValue(0,"cc_vendor","")))
				DbUtil.executeUpdateQuery("update event_reg_details_temp set tax='0.00',grandtotal=grandtotal-tax where tid=? and eventid=?", new String[]{tid,eid});
		}
	}

    
	public void updateCCVendor(String tid,String eid){
		DbUtil.executeUpdateQuery("update event_reg_details_temp set cc_vendor=NULL where tid=? and eventid=?", new String[]{tid,eid});
	}
	
	
}	

