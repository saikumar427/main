package com.event.dbhelpers;

import java.text.DecimalFormat;
 
import java.util.*;

import org.apache.velocity.VelocityContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.event.beans.ReportsData;
import com.event.beans.SubManagerData;
import com.event.beans.TransactionNotesData;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.GetTicketsThread;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;
import com.eventregister.dbhelper.RSVPProfileActionDB;
import com.eventregister.dbhelper.RSVPRegistrationConfirmationEmail;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("unchecked")
public class TransactionDB {
	TransactionNotesData transactionNotesData=new TransactionNotesData();
    ReportsData reportsdata=new ReportsData();
	final static String TransactionQuery = "select current_amount,to_char(transaction_date,'mm/dd/yyyy HH:MI AM') as "
			+ "transaction_date,tid,bookingsource,paymentstatus,discountcode,current_tax,paymenttype,"
			+ "ordernumber,current_discount,bookingdomain,original_amount,refund_amount,eventdate, " 
			+ "case when to_date(event_closed_date,'YYYY-MM-DD')<now() then 'CLOSED' else 'NOT_CLOSED' end as endstatus " 
			+ "from event_reg_transactions where eventid=? and tid=?";
	final static String ticketsQuery = "select ticketname,ticketid,ticketqty,ticketprice,discount,"
			+ "ticketstotal from transaction_tickets where eventid=? and tid=?";
	static final DecimalFormat TWO_DECIMAL_PLACE = new DecimalFormat("0.00");
	static String GET_TICKET_INFO = "select distinct ticketname,ticketprice,fee,ticketid,ticketqty,discount,"
			+ "ticket_groupid as tktgroupid,groupname,ticketstotal from transaction_tickets  where tid=? "
			+ "and eventid=? ";
	static String GET_TICKET_TYPE="select tickettype,ticketid from profile_base_info " +
			"where transactionid=?";
	static String GET_ALL_TICKET_INFO = "select distinct ticket_name as ticketname,ticket_price+process_fee as"
			+ " ticketprice,process_fee,pr.price_id as ticketid,isdonation ,0 as ticketqty,0 as discount,gt.ticket_groupid as tktgroupid,"
			+ "groupname,0 as ticketstotal from price pr,group_tickets gt,event_ticket_groups etg  where  "
			+ " etg.eventid=? and CAST(etg.eventid AS BIGINT)=pr.evt_id and"
			+ " to_number(gt.price_id,'99999999999999999999')=pr.price_id  and gt.ticket_groupid=etg.ticket_groupid";
	static String MODIFIED_DETAILS = "select current_amount as orgamtfrmevtreg,"
			+ "current_discount as orgdiscountfrmevtreg,current_tax as tax from event_reg_transactions "
			+ "where tid=? and eventid=?";
	final static String serverAddress = "http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
	public static HashMap getRegTransactionInfo(String eid, String tid) {
		HashMap tranInfo = new HashMap();
		try {
			DBManager db = new DBManager();
			StatusObj sb = db.executeSelectQuery(TransactionQuery,
					new String[] { eid, tid });
			if (sb.getStatus()) {      
				tranInfo.put("totalAmount", CurrencyFormat.getCurrencyFormat("",db.getValueFromRecord(0, "current_amount", "") + "",true));
				tranInfo.put("transaction_date", db.getValueFromRecord(0,"transaction_date", ""));
				tranInfo.put("tid", db.getValueFromRecord(0, "tid", ""));
				String eventdate=db.getValueFromRecord(0, "eventdate", "").toString();
				if("null".equals(eventdate) || eventdate==null) eventdate="";
				tranInfo.put("eventdate", eventdate);
				String bookingsource = db.getValue(0, "bookingsource", "");
	            
				
				if ("Manager".equals(bookingsource))
					bookingsource = "Added Manually";
				if ("online".equals(bookingsource))
					bookingsource = "Online";
                
                tranInfo.put("bookingsource", bookingsource);
				String paymentstatus=db.getValue(0,"paymentstatus", "");
				if("CANCELLED".equalsIgnoreCase(paymentstatus)) paymentstatus="Deleted";
				if("Need Approval".equalsIgnoreCase(paymentstatus)) paymentstatus="Pending Approval";
				
				tranInfo.put("paymentstatus", paymentstatus);
				tranInfo.put("discountcode", db.getValueFromRecord(0, "discountcode", ""));
				tranInfo.put("tax", CurrencyFormat.getCurrencyFormat("", db.getValueFromRecord(0, "current_tax", "")+ "", true));
				// tranInfo.put("paymenttype",
				// db.getValueFromRecord(0,"paymenttype",""));
				String paymenttype = db.getValue(0, "paymenttype", "");
				if ("eventbee".equals(paymenttype))
					paymenttype = "Eventbee";
				else if ("google".equals(paymenttype))
					paymenttype = "Google";
				else if ("paypal".equals(paymenttype))
					paymenttype = "PayPal";
				else if ("other".equals(paymenttype))
					paymenttype = "Other";
				else if ("nopayment".equals(paymenttype))
					paymenttype = "No Payment";
				else if ("Bulk".equalsIgnoreCase(paymenttype))
					paymenttype = "Added Manually";
				else if ("authorize.net".equalsIgnoreCase(paymenttype))
					paymenttype = "Authorize.Net";
				else if("braintree".equalsIgnoreCase(paymenttype))
					paymenttype="Braintree";
				else if("stripe".equalsIgnoreCase(paymenttype))
					paymenttype="Stripe";
				else if("payulatam".equalsIgnoreCase(paymenttype))
					paymenttype="payulatam";
				
				tranInfo.put("paymenttype", paymenttype);
				tranInfo.put("orderNumber", db.getValueFromRecord(0, "ordernumber", ""));
				tranInfo.put("discount", CurrencyFormat.getCurrencyFormat("",db.getValueFromRecord(0, "current_discount", "") + "",	true));
				String bookingdomain = db.getValue(0, "bookingdomain", "");
                if(bookingdomain.contains("VBEE"))
                    bookingdomain="VBEE";
                else bookingdomain="EB";
                tranInfo.put("bookingdomain", bookingdomain);
                
                double originalamount=0;
                originalamount=Double.parseDouble(db.getValueFromRecord(0, "original_amount", "0")+"");
                String refundamt=CurrencyFormat.getCurrencyFormat("",db.getValueFromRecord(0, "refund_amount", "0")+"",true);
                tranInfo.put("refund_amt",refundamt);
				String endstatus=db.getValue(0, "endstatus", "");
                String refund="No";
                /*if(originalamount-refundamount>0)
                	refund="Yes";*/
                if("Eventbee".equalsIgnoreCase(paymenttype) && "NOT_CLOSED".equalsIgnoreCase(endstatus))
                	refund="Yes";
                else if("PayPal".equalsIgnoreCase(paymenttype) || 
                		"Authorize.Net".equalsIgnoreCase(paymenttype) ||
                		"Braintree".equalsIgnoreCase(paymenttype) ||
                		"Stripe".equalsIgnoreCase(paymenttype) ||
                		"payulatam".equalsIgnoreCase(paymenttype))
                	refund="Yes";
                tranInfo.put("refund", refund);
			}
		} catch (Exception e) {

		}
		return tranInfo;
	}

	public static ArrayList getRegTicketsInfo(String eid, String tid) {
		ArrayList ticketsList = new ArrayList();
		HashMap groupnames=ReportsDB.getTicketGroupdetails(eid);
		try {
			DBManager db = new DBManager();
			StatusObj sb = db.executeSelectQuery(ticketsQuery, new String[] {
					eid, tid });
			if (sb.getStatus()) {
				for (int i = 0; i < sb.getCount(); i++) {
					HashMap ticketsMap = new HashMap();
					
					String ticketname=db.getValue(i,"ticketname","");
					String ticketid=db.getValue(i,"ticketid","");
				    if(groupnames.containsKey(ticketid)){
				    	ticketname=ticketname+" ("+groupnames.get(ticketid)+ ")";
				    }
					
					ticketsMap.put("ticketName",ticketname);
				    
					ticketsMap.put("tktnumber", db.getValueFromRecord(i,
							"ticketid", ""));
					ticketsMap.put("qty", db.getValueFromRecord(i, "ticketqty",
							""));
					ticketsMap.put("ticketprice", db.getValueFromRecord(i,
							"ticketprice", ""));
					ticketsMap.put("discount", db.getValueFromRecord(i,
							"discount", ""));
					ticketsMap.put("ticketstotal", db.getValueFromRecord(i,
							"ticketstotal", ""));
					ticketsMap.put("ticketdonationtype",ticketname);
					// ticketsMap.put("profiles",getProfileData((String)db.
					// getValueFromRecord(i,"ticketid","")));
					ticketsList.add(ticketsMap);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception occured is" + e.getMessage());
		}

		return ticketsList;

	}

	public static HashMap getProfileData(String eid, String tid) {
		System.out.println("getProfileData tid: "+tid+" eid: "+eid);
		HashMap regprofileMap = new HashMap();
		HashMap groupnames=ReportsDB.getTicketGroupdetails(eid);
		ArrayList pList = new ArrayList();
		String query="";
		query = "select * from profile_base_info where eventid=CAST(? AS BIGINT) and "
				+ "transactionid=? order by seatcode,profileid";
		try {
			DBManager db = new DBManager();
			StatusObj sb =null;
		    sb=db.executeSelectQuery(query,new String[] {eid,tid });
			if (sb.getStatus()) {
				ArrayList profileList = null;
				for (int i = 0; i < sb.getCount(); i++) {
					HashMap profilemap = new HashMap();
					String seatcode="";
					profilemap.put("fname", db.getValue(i,"fname",""));
					profilemap.put("lname", db.getValue(i,"lname",""));
			     	profilemap.put("email", db.getValue(i,"email",""));
					profilemap.put("phone", db.getValue(i,"phone",""));
					seatcode=db.getValue(i,"seatcode","");
					if(seatcode==null || "null".equals(seatcode)) seatcode="";
					profilemap.put("seatcode", seatcode);
					profilemap.put("profileid", db.getValue(i,"profileid",""));
					profilemap.put("profilekey", db.getValue(i,"profilekey",""));
					profilemap.put("tickettype", db.getValue(i,"tickettype",""));
					profileList = (ArrayList) regprofileMap.get(db.getValue(i,"ticketid", ""));
					if (profileList != null)
						profileList.add(profilemap);
					else {
						profileList = new ArrayList();
						profileList.add(profilemap);
					}
					regprofileMap.put(db.getValue(i, "ticketid", ""),
							profileList);

				}
			}
		} catch (Exception e) {

		}
		return regprofileMap;
	}

	public static HashMap getBuyerData(String eid, String tid) {
		System.out.println("getBuyerData tid: "+tid+" eid: "+eid);
		HashMap buyerMap = new HashMap();
		ArrayList pList = new ArrayList();
		String query = "select * from buyer_base_info where eventid=CAST(? AS BIGINT) and "
				+ "transactionid=?";
		try {
			DBManager db = new DBManager();
			StatusObj sb = db.executeSelectQuery(query,
					new String[] { eid, tid });
			
			if (sb.getStatus()) {
				ArrayList profileList = null;
				for (int i = 0; i < sb.getCount(); i++) {
					buyerMap.put("fname", db.getValue(i, "fname", ""));
					buyerMap.put("lname", db.getValue(i, "lname", ""));
					buyerMap.put("email", db.getValue(i, "email", ""));
					buyerMap.put("phone", db.getValue(i, "phone", ""));
					buyerMap.put("profileid", db.getValue(i, "profileid", ""));
					buyerMap.put("profilekey", db.getValue(i, "profilekey", ""));

				}
			}
		} catch (Exception e) {

		}
		return buyerMap;
	}

	public static HashMap getTransactionAttributes(String eid, String tid) {
		System.out.println("getTransactionAttributes tid: "+tid+" eid: "+eid);
		HashMap responsesMap = new HashMap();
		/*String query = "select question_original,bigresponse,profilekey from custom_questions_response cq, "
				+ "custom_questions_response_master cm where  cq.ref_id=cm.ref_id and "
				+ "groupid=to_number(?,'9999999999') and transactionid=?";*/
		String query = "select c.attribname,a.bigresponse,b.profilekey from custom_questions_response a, " +
				"custom_questions_response_master b,custom_attribs c where a.ref_id=b.ref_id " +
				"and a.attribid=c.attrib_id and b.attribsetid::int=c.attrib_setid " +
				"and b.groupid=CAST(? AS BIGINT) and transactionid=? order by c.position";
		DBManager db = new DBManager();
		StatusObj sb = db.executeSelectQuery(query, new String[] { eid, tid });
		if (sb.getStatus()) {
			ArrayList attribList = null;
			for (int i = 0; i < sb.getCount(); i++) {
				HashMap hm = new HashMap();
				//String question = db.getValue(i, "question_original", "");
				String question = db.getValue(i, "attribname", "");
				String response = db.getValue(i, "bigresponse", "");
				String profilekey = db.getValue(i, "profilekey", "");
				hm.put("question", question);
				hm.put("response", response);
				attribList = (ArrayList) responsesMap.get(profilekey);
				if (attribList != null) {
					attribList.add(hm);
				} else {
					attribList = new ArrayList();
					attribList.add(hm);
				}
				responsesMap.put(profilekey, attribList);
			}

		}
		return responsesMap;
	}
	
	public static HashMap getTicketTypes(String transactionid) {
		System.out.println("getTicketTypes transactionid: "+transactionid);
		HashMap ticketTypes=new HashMap();
		DBManager dbmanager = new DBManager();
		StatusObj stobj = dbmanager.executeSelectQuery(GET_TICKET_TYPE,
				new String[] { transactionid });
		if (stobj.getStatus()) {
			for (int i = 0; i < stobj.getCount(); i++) {
				
				String ticketid = dbmanager.getValue(i, "ticketid", "");
				String ticketType=dbmanager.getValue(i, "tickettype", "");
				String isDonation =
					("donationType".equals(ticketType))?"yes":"no";
				
				ticketTypes.put( ticketid,isDonation);
			}
		}
		return ticketTypes;
	}


	public static Vector getTicketInfo(String transactionid, String eventid) {
		System.out.println("getTicketInfo transactionid: "+transactionid+" eventid: "+eventid);
		Vector v = new Vector();
		HashMap ticketTypes=getTicketTypes(transactionid);
		DBManager dbmanager = new DBManager();
		StatusObj stobj = dbmanager.executeSelectQuery(GET_TICKET_INFO,
				new String[] { transactionid, eventid });
		if (stobj.getStatus()) {
			for (int i = 0; i < stobj.getCount(); i++) {
				HashMap hm = new HashMap();
				String ticketid = dbmanager.getValue(i, "ticketid", "");
				hm.put("ticketid", ticketid);
				//String isDonationType=ticketTypes.get(ticketid).toString();
				hm.put("isDonation", ticketTypes.get(ticketid));
				String ticketname = (String) dbmanager.getValue(i,
						"ticketname", "");
				
				hm.put("ticketname", ticketname);
				hm.put("price", dbmanager.getValue(i, "ticketprice", ""));
				hm.put("qty", dbmanager.getValue(i, "ticketqty", ""));
				hm.put("discount", dbmanager.getValue(i, "discount", ""));
				hm.put("tktgroupid", dbmanager.getValue(i, "tktgroupid", ""));
				hm.put("groupname", dbmanager.getValue(i, "groupname", ""));
				hm.put("ticketstotal", dbmanager.getValue(i, "ticketstotal", ""));
				hm.put("fee", dbmanager.getValue(i, "fee", "0"));
				v.addElement(hm);

			}
		}
		return v;
	}

	public static Vector getAllTicketInfo(String eventid) {
		System.out.println("getAllTicketInfo eventid: "+eventid);
		Vector v = new Vector();
		DBManager dbmanager = new DBManager();
		StatusObj stobj = dbmanager.executeSelectQuery(GET_ALL_TICKET_INFO,
				new String[] { eventid });
		if (stobj.getStatus()) {
			for (int i = 0; i < stobj.getCount(); i++) {
				HashMap hm = new HashMap();
				String ticketid = dbmanager.getValue(i, "ticketid", "");
				hm.put("ticketid", ticketid);
				String ticketname = (String) dbmanager.getValue(i,
						"ticketname", "");
				String isDonationType=(String) dbmanager.getValue(i,
						"isdonation", "No");
			
				hm.put("isDonation", isDonationType.toLowerCase());
				hm.put("ticketname", ticketname);
				hm.put("price", CurrencyFormat.getCurrencyFormat("", dbmanager
						.getValue(i, "ticketprice", ""), true));
				hm.put("qty", dbmanager.getValue(i, "ticketqty", ""));
				hm.put("discount", CurrencyFormat.getCurrencyFormat("",
						dbmanager.getValue(i, "discount", ""), true));
				hm.put("tktgroupid", dbmanager.getValue(i, "tktgroupid", ""));
				hm.put("groupname", dbmanager.getValue(i, "groupname", ""));
				hm.put("ticketstotal", CurrencyFormat.getCurrencyFormat("",
						dbmanager.getValue(i, "ticketstotal", ""), true));
				hm.put("fee", CurrencyFormat.getCurrencyFormat("", dbmanager
						.getValue(i, "process_fee", "0"), true));
				v.addElement(hm);

			}
		}
		return v;
	}

	public static String getKey(HashMap tctDetails) {

		String price = (String) tctDetails.get("price");
		String ticketid = (String) tctDetails.get("ticketid");
		String ticketgroupid = (String) tctDetails.get("tktgroupid");
		String groupName = (String) tctDetails.get("groupname");
		String ticketName = (String) tctDetails.get("ticketname");
//		String key = ticketid + ":" + ticketgroupid + ":" + ticketName + ":"
//				+ groupName + ":"+ CurrencyFormat.getCurrencyFormat("", price + "", true);
		String key = ticketName + ": "+CurrencyFormat.getCurrencyFormat("", price + "", true);
		return key;
	}

	public static void mergeVectors(Vector ticketdetails,
			Vector allticketdetails) {

		
		HashMap addedTickets=new HashMap();
		HashMap ticketIndices=new HashMap();
		for (int i = 0; i < ticketdetails.size(); i++) {
			HashMap hm = (HashMap) ticketdetails.elementAt(i);
			String key = getKey(hm);
			addedTickets.put(hm.get("ticketid"), key);
			ticketIndices.put(hm.get("ticketid"), ""+i);
			
		}
		for (int i = 0; i < allticketdetails.size(); i++) {
			HashMap hm = (HashMap) allticketdetails.elementAt(i);
			String key = getKey(hm);
			String oldKey=(String)addedTickets.get(hm.get("ticketid"));
			// System.out.println("key"+key);
			// System.out.println("oldKey"+oldKey);
			if (oldKey!=null) {
				if(!oldKey.equals(key)){
					int index=Integer.parseInt((String)ticketIndices.get(hm.get("ticketid")));
					((HashMap)ticketdetails.get(index)).put("newName", key);
				}
			} else {
				ticketdetails.add(hm);
				addedTickets.put(hm.get("ticketid"), key);
			}
			
		
		}
	}

	public static void getAmountDetails(String transactionid, String eventid,
			ReportsData reportsData) {
		String totalamount="";
		DBManager dbmanager = new DBManager();
		StatusObj stobj = dbmanager.executeSelectQuery(MODIFIED_DETAILS,
				new String[] { transactionid, eventid });
		
		if (stobj.getStatus()) {
			//reportsData.setOriginalAmount(dbmanager.getValue(0,
					//"orgamtfrmevtreg", ""));
			reportsData.setCurrentDiscount(dbmanager.getValue(0,
					"orgdiscountfrmevtreg", ""));
			String tax=dbmanager.getValue(0, "tax", "");
			if(tax==null)tax="";
			if(!"".equals(tax) && Double.parseDouble(tax)>0.00){
				totalamount=Double.toString(Double.parseDouble(dbmanager.getValue(0,"orgamtfrmevtreg", ""))-Double.parseDouble(tax));
			    totalamount=CurrencyFormat.getCurrencyFormat("",totalamount,false);
			}else{
				totalamount=Double.toString(Double.parseDouble(dbmanager.getValue(0,"orgamtfrmevtreg", "")));
				totalamount=CurrencyFormat.getCurrencyFormat("",totalamount,false); 
			}	
			reportsData.setOriginalAmount(totalamount);
			reportsData.setCurrentTax(dbmanager.getValue(0, "tax", ""));
			double net = Double.parseDouble(dbmanager.getValue(0,
					"orgamtfrmevtreg",""));
			reportsData.setNetAmount(TWO_DECIMAL_PLACE.format(net));
		}

	}

	static String SOLDQTY_UPDATE = "update price set sold_qty=sold_qty+to_number(?,'999999999999999') where price_id=to_number(?,'999999999999999') and evt_id=CAST(? AS BIGINT)";

	public static void updateSoldQty(String tid, String eventid, String factor) {
		System.out.println("updateSoldQty tid: "+tid+" eventid: "+eventid+" factor: "+factor);
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		String TRN_TICKETS_SELECT_QUERY = "select ticketid,sum(ticketqty) as qty from transaction_tickets where tid=? and eventid=? group by ticketid";
		statobj = dbmanager.executeSelectQuery(TRN_TICKETS_SELECT_QUERY,
				new String[] { tid, eventid });
		int count = statobj.getCount();
		if (statobj.getStatus()) {
			for (int k = 0; k < count; k++) {
				String ticketid = (String) dbmanager
						.getValue(k, "ticketid", "");
				String qty = (String) dbmanager.getValue(k, "qty", "");
				int qtytemp = Integer.parseInt(qty) * Integer.parseInt(factor);
				String temp = Integer.toString(qtytemp);
				StatusObj statobj1 = DbUtil.executeUpdateQuery(SOLDQTY_UPDATE,
						new String[] { temp, ticketid, eventid });
				System.out.println("soldqty updated for:"+ticketid+" effected rows:"+statobj1.getCount());
			} // end for()
		} // end if()
	} // end method()

	public static String TR_TICKETS_DELETE = "delete from transaction_tickets where eventid=? and tid=? ";
	public static String TR_TICKETS_INSERT = "insert into transaction_tickets(groupname,ticket_groupid,ticketid,"
			+ "ticketname,"
			+ "ticketqty,ticketprice,"
			+ "discount,tid,eventid,ticketstotal,transaction_at,fee) values (?,?,to_number(?,'999999999999999'),?,"
			+ "to_number(?,'999999999999999'),"
			+ "to_number(?,'9999999999999.99'),to_number(?,'9999999999999.99'),?,?,to_number(?,'9999999999999.99'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_number(?,'9999999999999.99'))";
	public static String EVT_REG_TRN_UPDATE = "update event_reg_transactions set current_tickets=?,"
			+ "current_discount=to_number(?,'9999999999999.99'),"
			+ "current_amount=to_number(?,'9999999999999.99'),current_tax=to_number(?,'9999999999999.99'),"
			+ " modifiedon=now() where eventid=? and tid=?";

	public static void updateTransactionTickets(int size, ArrayList ticketname,
			ArrayList groupname, ArrayList ticketgroupid, ArrayList qty,
			ArrayList ticketid, ArrayList discount, ArrayList ticketprices,
			ArrayList total, String eid, String tid, String totaldiscount1,
			String totalamount1, String tax,String totalnetamount,int remqty,ArrayList ticketfees) {
		System.out.println("updateTransactionTickets tid: "+tid+" eid: "+eid);
		TransactionDB.updateSoldQty(tid, eid, "-1");
		int totalqty=0;
		HashMap profiletickettemp = getProfileTickets(eid, tid);
		System.out.println("profiletickettemp is:"+profiletickettemp);
		HashMap allTickets=new HashMap();
		StatusObj statobj = DbUtil.executeUpdateQuery(TR_TICKETS_DELETE,
				new String[] { eid, tid });
		
		JSONArray ticketsArr = new JSONArray();
		for (int i = 1; i <= size; i++) {
			String tName = ((String[]) ticketname.get(i))[0];
			String gName = ((String[]) groupname.get(i))[0];
			String tktGId = ((String[]) ticketgroupid.get(i))[0];
			String Qty = ((String[]) qty.get(i))[0];
			String tickeId = ((String[]) ticketid.get(i))[0];
			String dscnt = ((String[]) discount.get(i))[0];
			String price = ((String[]) ticketprices.get(i))[0];
			String fee = ((String[]) ticketfees.get(i))[0];
			String Total = ((String[]) total.get(i))[0];
			String group_name=DbUtil.getVal("select groupname from event_ticket_groups where " +
					" ticket_groupid in(select ticket_groupid from " +
					" group_tickets where price_id=?)",new String[]{tickeId});
			
			
			String ticket_type=DbUtil.getVal("select ticket_type from price where " +
					" price_id=to_number(?,'99999999')", new String[]{tickeId});
			if("Public".equals(ticket_type)) ticket_type="attendeeType";
			
			DbUtil.executeUpdateQuery("delete from event_reg_ticket_details_temp where tid=? and ticketid=to_number(?,'9999999999')",new String[]{tid,tickeId});
			
			if(Integer.parseInt(Qty)>0){
			   
			       String insert_eventregticketdetailstemp = "insert into event_reg_ticket_details_temp" +
			     		" (originalfee,ticketgroupid,qty,"
						+ " eid,discount,tickettype,finalprice,"
						+ "finalfee,ticketname,ticketgroupname,originalprice,"
						+ " tid,ticketid,transaction_at)"
						+ " values(to_number(?,'9999999999.99'),?,to_number(?,'999999999'),"
						+ " ?,to_number(?,'9999999999.99'),?,to_number(?,'9999999999.99')-to_number(?,'9999999999.99'),"
						+ " to_number(?,'9999999999.99'),?,"
						+ " ?,to_number(?,'9999999999.99')-to_number(?,'9999999999.99'),?,to_number(?,'9999999999'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS')) ";
			      
			     StatusObj status=DbUtil.executeUpdateQuery(insert_eventregticketdetailstemp,new String[]
			           {fee,tktGId,Qty,eid,dscnt,ticket_type,price,fee,fee,tName,
			    		 gName,price,fee,tid,tickeId,DateUtil.getCurrDBFormatDate()});
			     System.out.println("status of insert event reg temp"+status.getStatus());
			      
			}
			
			String existingQty=(String)allTickets.get(tickeId);
			if(existingQty==null){
				allTickets.put(tickeId, Qty);
			}
			else{
				int tqty=Integer.parseInt(Qty)+Integer.parseInt(existingQty);
				allTickets.put(tickeId,""+tqty);
			}
			//System.out.println(tickeId + " " + Qty + " getprofiletickets "+ profiletickettemp.get(tickeId));
			

			JSONObject TicketObject = new JSONObject();
			if ("0".equals(Qty)) {
			} else {
				try {
					TicketObject.put("gn", gName);
					TicketObject.put("gid", tktGId);
					TicketObject.put("tn", tName);
					TicketObject.put("qty", Qty);
					TicketObject.put("tid", tickeId);
					TicketObject.put("d", dscnt);
					TicketObject.put("p", price);
					TicketObject.put("ttotal", Total);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ticketsArr.put(TicketObject);
				StatusObj statobj1 = DbUtil.executeUpdateQuery(
						TR_TICKETS_INSERT, new String[] { gName, tktGId,
								tickeId, tName, Qty, price, dscnt, tid, eid,
								Total,DateUtil.getCurrDBFormatDate(),fee });
				totalqty=totalqty+Integer.parseInt(Qty);
			}
		}// for
		/*try{
		//update servicefee for edittickets 
		String servicefee=DbUtil.getVal("select current_fee from eventinfo where eventid=to_number(?,'999999999')",new String[]{eid});
		if(servicefee==null)servicefee="1.00";
		String conv_factor=DbUtil.getVal("select conversion_factor from currency_symbols a,event_currency b where b.eventid=? and a.currency_code=b.currency_code", new String[]{eid});
		if(conv_factor==null || "".equals(conv_factor))
		conv_factor="1";
		String totalservicefee=Double.toString((Double.parseDouble(servicefee))*(Double.parseDouble(conv_factor))*remqty);
		totalservicefee=CurrencyFormat.getCurrencyFormat("",totalservicefee,false);
		System.out.println("totalservicefee is:"+totalservicefee);
		DbUtil.executeUpdateQuery("update event_reg_transactions set servicefee=servicefee+"+totalservicefee+" where eventid=? and tid=?",new String[]{eid,tid});
		}catch(Exception e){}*/
		Set mapKeys = allTickets.keySet();
	    java.util.Iterator It = mapKeys.iterator();
	    while (It.hasNext()) {
            String eticketid = (String)(It.next());
            String eqty=(String)allTickets.get(eticketid);
            String profileticketqty = (String) profiletickettemp.get(eticketid);
    		int diff = getCountDiff(profileticketqty, eqty);
    		if (diff < 0)// profile has less quantity
    		{
    			insertMissingQty(eid, tid, eticketid, -1 * diff);
    		}
    		if (diff > 0)// profile has more quantity
    		{
    			deleteExcessQty(eid, tid, eticketid, diff);
    		}
           
            }
		
		TransactionDB.updateSoldQty(tid, eid, "1");
		
		StatusObj statobj2 = DbUtil.executeUpdateQuery(EVT_REG_TRN_UPDATE,
				new String[] { ticketsArr.toString(), totaldiscount1,
						totalnetamount, tax, eid, tid });
		//System.out.println("status of evt_reg_trn_update"+statobj2.getStatus());
		/*int s = ticketsArr.length();
		String so = ticketsArr.toString();
		System.out.println("ticketarr elements" + so);*/
		
	}

	public static int getCountDiff(String cQty, String nQty) {
		if (cQty == null)
			cQty = "0";
		if (nQty == null)
			nQty = "0";
		if (cQty == nQty)
			return 0;
		int incQty = 0;
		int innQty = 0;
		try {
			incQty = Integer.parseInt(cQty);
		} catch (Exception e) {
		}
		try {
			innQty = Integer.parseInt(nQty);
		} catch (Exception e) {
		}
		return incQty - innQty;

	}

	public static void deleteExcessQty(String eid, String tid, String tickeId,
			int diff) {
		System.out.println("deleteExcessQty tid: "+tid+" eid: "+eid+" tickeId: "+tickeId);
		DBManager db = new DBManager();
		StatusObj statobj = null;
		ArrayList profilekeyList = new ArrayList();
		
		String query = "select profilekey from profile_base_info where eventid=CAST(? AS BIGINT) "
				+ " and transactionid=? and ticketid=to_number(?,'99999999999')";
		statobj = db.executeSelectQuery(query,
				new String[] { eid, tid, tickeId });
		//System.out.println("status of profilebase info" + statobj.getStatus());
		int count = statobj.getCount();
		if (statobj.getStatus()) {

			for (int i = 0; i < count; i++) {
				String profilekey = db.getValue(i, "profilekey", "");
				profilekeyList.add(profilekey);
			}// end for

		}// end if
		String query1 = "delete from profile_base_info where profilekey=?";		
		for (int i = 0; i <diff; i++) {
			String profilekey = (String) profilekeyList.get(i);			
			StatusObj statobj1 = DbUtil.executeUpdateQuery(query1,new String[] { profilekey });	
			
			

		}// end for first

	}

	public static void insertMissingQty(String eid,String tid,String tickeId,int diff){
		System.out.println("insertMissingQty tid: "+tid+" eid: "+eid+" tickeId: "+tickeId);
	      DBManager db=new DBManager();
	      StatusObj status=null;
	      
         HashMap buyerMap=getBuyerData(eid,tid);
		 String  fname=(String)buyerMap.get("fname");
  	     String  lname=(String)buyerMap.get("lname");
  	     String  phone=(String)buyerMap.get("phone");
  	     String  email=(String)buyerMap.get("email");
  	     String  tickettype=getTicketType(tickeId);
  	     
  	    String profilebasequery="insert into profile_base_info(eventid,fname,lname,transactionid,phone,email," +
  	     		                  "profilekey,ticketid,tickettype,profileid,created_at,profilestatus)"
                                  +" values(CAST(? AS BIGINT),?,?,?,?,?,?,CAST(? AS BIGINT),?" +
                  		          ",CAST(? AS INTEGER),now(),'Completed')";
  	    

  	      for(int i=0;i<diff;i++){
  	    	 
  	    	 String profileid=DbUtil.getVal("select nextval('SEQ_attendeeid')",new String[]{});
  			 String profilekey="AK"+EncodeNum.encodeNum(profileid).toUpperCase();
  	    	 StatusObj profile_base_info_insert=DbUtil.executeUpdateQuery(profilebasequery,
  	    			                            new String[]{eid,fname,lname,tid,phone,email,profilekey,tickeId,tickettype,profileid});
  	    	
  	      }
  	     
  	      		 	 
	}	 
   
	public static String getTicketType(String tickeId){
		System.out.println("getTicketType tickeId: "+tickeId);
  		   DBManager db=new DBManager();
  		 String tickettype="";
  		 String ticket_type="select  isdonation , isattendee  from price where price_id=to_number(?,'99999999')";
  	     StatusObj statobj=db.executeSelectQuery(ticket_type, new String[]{tickeId});
  	 
  	     int count=statobj.getCount();
  	     if(statobj.getStatus()&& count>0){
  	    	 for(int i=0;i<count;i++){
  	    		if("Yes".equalsIgnoreCase(db.getValue(i,"isdonation","")))

  	    			tickettype="donationType";
  	    			    else if("Yes".equalsIgnoreCase(db.getValue(i,"isattendee","")))
  	    			 tickettype="attendeeType";
  	    			    else
  	    			 tickettype="nonAttendee" ;
  	    	 }
  	     }
  	     
  	     else {
  	    	 ticket_type="select tickettype from profile_base_info " +
  	    	 		"where ticketid=to_number(?,'9999999999')" +
  	    	 		" order by pid desc limit 1";
  	    	 StatusObj statobj1=db.executeSelectQuery(ticket_type, new String[]{tickeId});
  	      
  	         count=statobj1.getCount();
  	  	     if(statobj1.getStatus()&& count>0){
  	  	    	 for(int i=0;i<count;i++){
  	  	    		tickettype=db.getValue(i,tickettype,"");
  	  	    	 }
  	  	     }
  	     }
  	    	 
  	    	 if("".equals(tickettype))
  	    		 tickettype="attendeeType";
  	    		 
  	    	   return tickettype;
  	   }
     	


	public static HashMap getProfileTickets(String eid, String tid) {
		System.out.println("getProfileTickets tid: "+tid+" eid: "+eid);
		String PROFILE_BASE_INFO = "select ticketid,count(*) as qty from profile_base_info "
				+ "where eventid=CAST(? AS BIGINT) and transactionid=? "
				+ " group by ticketid";
		DBManager dbmanager = new DBManager();
		StatusObj statobj3 = dbmanager.executeSelectQuery(PROFILE_BASE_INFO,
				new String[] { eid, tid });
	
		int count = statobj3.getCount();
		HashMap profiletickets = new HashMap();
		if (statobj3.getStatus()) {
			for (int i = 0; i < count; i++) {
				String profileticketid = (String) dbmanager.getValue(i,
						"ticketid", "");
				String profileqty = (String) dbmanager.getValue(i, "qty", "");
				profiletickets.put(profileticketid, profileqty);
			}// for
		}// if
		return profiletickets;
	}

	public static void deleteTransaction(String eid, String tid) {
		System.out.println("deleteTransaction tid: "+tid+" eid: "+eid);
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		String isrecurr=DbUtil.getVal("select value from config where name='event.recurring' and config_id=CAST(? AS INTEGER)",new String[]{configid});
		StatusObj statob = DbUtil.executeUpdateQuery("update event_reg_transactions set paymentstatus=? where tid=? and eventid=?",new String[] { "Cancelled", tid, eid });
		DbUtil.executeUpdateQuery("update priority_reg_transactions set status=? where tid=? and eventid=?", new String[] { "Cancelled", tid, eid });
		if("Y".equals(isrecurr)) rec_updateSoldQty(tid,eid,"-1");
		else updateSoldQty(tid, eid, "-1");
		
	}
	
	
	
	public static void rec_updateSoldQty(String tid,String eid,String factor){
		System.out.println("rec_updateSoldQty tid: "+tid+" eid: "+eid+" factor: "+factor);
		StatusObj statobj = null;
		DBManager dbmanager = new DBManager();
		String TRN_TICKETS_SELECT_QUERY = "select ticketid,sum(ticketqty) as qty from "
				+ " transaction_tickets where tid=? and eventid=? group by ticketid";

		String rec_soldqty = "update reccurringevent_ticketdetails set soldqty=soldqty+to_number(?,'999999999')"
				+ " where ticketid=to_number(?,'9999999999') and eventid=CAST(? AS BIGINT) and eventdate in(select eventdate"
				+" from event_reg_transactions where tid=? and eventid=?)";
		statobj = dbmanager.executeSelectQuery(TRN_TICKETS_SELECT_QUERY,
				new String[] { tid, eid });

		int count = statobj.getCount();
		if (statobj.getStatus()) {
			for (int k = 0; k < count; k++) {
				String ticketid = (String) dbmanager
						.getValue(k, "ticketid", "");
				String qty = (String) dbmanager.getValue(k, "qty", "");
				int qtytemp = Integer.parseInt(qty) * Integer.parseInt(factor);
				String temp = Integer.toString(qtytemp);
				StatusObj statobj1 = DbUtil.executeUpdateQuery(rec_soldqty,
						new String[] { temp, ticketid, eid, tid,eid});
			} // end for()
		} // end if()
	
	}
	
	
	public static void deleteRSVPTransaction(String eid, String tid) {
		System.out.println("deleteRSVPTransaction tid: "+tid+" eid: "+eid);
		StatusObj statobj=null;
	 /*    statobj = DbUtil
				.executeUpdateQuery(
						"update event_reg_transactions set paymentstatus=? where tid=? and eventid=?",
						new String[] { "Cancelled", tid, eid });
		updateSoldQty(tid, eid, "-1");*/
		
		
		 String delete_eventregtransactions="delete from event_reg_transactions where eventid=? and tid=?";
		 statobj=DbUtil. executeUpdateQuery(delete_eventregtransactions, new String[]{eid,tid});
		 
		 String delete_rsvptransactions="delete from rsvp_transactions where eventid=? and tid=?";
		 statobj=DbUtil. executeUpdateQuery(delete_rsvptransactions, new String[]{eid,tid});
		 
		 String delete_transaction_tickets="delete from transaction_tickets where eventid=? and tid=?";
		 statobj=DbUtil. executeUpdateQuery(delete_transaction_tickets, new String[]{eid,tid});
		 
		 String delete_profilebaseinfo="delete from profile_base_info where eventid=CAST(? AS BIGINT)" +
		 		" and transactionid=?";
		 statobj=DbUtil. executeUpdateQuery(delete_profilebaseinfo, new String[]{eid,tid});
		 
		 String delete_buyerbaseinfo="delete from buyer_base_info where" +
		 		"  eventid=CAST(? AS BIGINT) and transactionid=?";
		 statobj=DbUtil. executeUpdateQuery(delete_buyerbaseinfo, new String[]{eid,tid});
		
	}


	public static void undeleteTransaction(String eid, String tid, String changestatus) {
		System.out.println("undeleteTransaction tid: "+tid+" eid: "+eid+" changestatus: "+changestatus);
		
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		String isrecurr=DbUtil.getVal("select value from config where name='event.recurring' and config_id=to_number(?,'999999999')",new String[]{configid});
		
		StatusObj statob = DbUtil.executeUpdateQuery("update event_reg_transactions set paymentstatus=? where tid=? and eventid=?",
						new String[] { changestatus, tid, eid });
		
		if("Y".equals(isrecurr)) rec_updateSoldQty(tid,eid,"1");
		else updateSoldQty(tid, eid, "1");
		
	}
	public static void saveNotesData(TransactionNotesData transactionNotesData,String tid){
		System.out.println("saveNotesData tid: "+tid);
		String notes=transactionNotesData.getNotes();
		//String notes_date=transactionNotesData.getNotes_date();
		//String notes_id=transactionNotesData.getNotes_id();
		if("".equals(transactionNotesData.getNotes_id())){
		String query="insert into transaction_notes(tid,notes,notes_date,notes_id) values(?,?,now(),nextval('notes_seq'))";
		StatusObj statobj=DbUtil.executeUpdateQuery(query,new String[]{tid,notes});
		
		
		}
		else
		{
			String query="update transaction_notes set notes=?,notes_date=now() where tid=? and notes_id=to_number(?,'99999999999')";
			StatusObj statobj=DbUtil.executeUpdateQuery(query,new String[]{notes,tid,transactionNotesData.getNotes_id()});
			
		}
	}
	public static TransactionNotesData getNotesData(String tid){
		System.out.println("getNotesData tid: "+tid);
		DBManager db=new DBManager();
		TransactionNotesData tempNotesData=new TransactionNotesData();
		String query="select notes,notes_id from transaction_notes where tid=? ";
		StatusObj statobj=db.executeSelectQuery(query,new String[]{tid});
		boolean status=statobj.getStatus();
		int count=statobj.getCount();
		
		if(status==true){
			for(int i=0;i<count;i++){
				tempNotesData.setNotes(db.getValue(i,"notes",""));
				tempNotesData.setNotes_id(db.getValue(i, "notes_id", ""));
				
			}//end for
			
		}//end if
	return tempNotesData;
	}
	//This is used when converting A Regular Event to Recurring Event
	
	public static void convertTransaction(String eid){
		System.out.println("convertTransaction eid: "+eid);
        DBManager db=new DBManager();
        StatusObj statobj=null;
		String eventstartdate= " select  to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time," +
				"'00'),'HH24:MI:SS') as text) as time ), 'Dy, Mon DD, YYYY HH12:MI AM') as evt_start_date " +
                      " from event_dates where eventid=CAST(? AS BIGINT) order by date_key  limit 1";
		String evt_start_date=DbUtil.getVal(eventstartdate,new String[]{eid});
		
		
		
		//here eventid is of type varchar and eventdate is of type varchar
		String eventregtransactions="update event_reg_transactions set eventdate=?  where eventid=? and eventdate is null";
		statobj=DbUtil.executeUpdateQuery(eventregtransactions,new String[]{evt_start_date,eid});
		
		String updatePriorityRegTxQry="update priority_reg_transactions set eventdate=? where eventid=? and (eventdate='' or eventdate is null)";
		DbUtil.executeUpdateQuery(updatePriorityRegTxQry,new String[]{evt_start_date,eid});
		
		String transaction_tickets_query="select ticketid,sum(ticketqty)as qtysold from transaction_tickets where eventid=? group by ticketid";//eventid is of type varchar
		statobj=db.executeSelectQuery( transaction_tickets_query, new String[]{eid});
		
		int count=statobj.getCount();
		if(statobj.getStatus()&& statobj.getCount()>0){
			for(int i=0;i<count;i++){
				String ticketid=db.getValue(i,"ticketid","");
				String qtysold=db.getValue(i, "qtysold","");
				String insert_recurringevent_ticketdetails="insert into reccurringevent_ticketdetails(eventid,ticketid,eventdate,soldqty)" +
						           "values(CAST(? AS BIGINT),to_number(?,'999999999'),?,to_number(?,'99999999'))";
				statobj=DbUtil.executeUpdateQuery(insert_recurringevent_ticketdetails, new String[]{eid,ticketid,evt_start_date,qtysold});
				
				
			}
		}
 
		
	}
	public static void updateEventDate(String eid,String tid,String date,String olddate){
		System.out.println("updateEventDate eid: "+eid+" tid: "+tid);
		String query="";
		StatusObj statobj=null;
		
		if(!date.equals(olddate)){
			query="update event_reg_transactions set eventdate=? where eventid=? and tid=?";
			statobj=DbUtil.executeUpdateQuery(query,new String[]{date,eid,tid});
		}
		query="delete from reccurringevent_ticketdetails where eventdate in(?,?) and eventid=CAST(? AS BIGINT)";
		statobj=DbUtil.executeUpdateQuery(query,new String[]{date,olddate,eid});
		
		query="insert into reccurringevent_ticketdetails(eventid,ticketid,eventdate,soldqty) "+
                " select CAST(a.eventid AS BIGINT),ticketid,eventdate,sum(ticketqty)"+ 
                 " from transaction_tickets a, event_reg_transactions b"+
                " where a.eventid=?"+
                 " and a.tid =b.tid and eventdate=?"+
                 " group by a.eventid,ticketid,eventdate";
		statobj=DbUtil.executeUpdateQuery(query,new String[]{eid,date});
		if(!date.equals(olddate)){
			statobj=DbUtil.executeUpdateQuery(query,new String[]{eid,olddate});

			String isseating=getSeatingStatus(eid);
			if("Y".equals(isseating) && EventDB.isRecurringEvent(eid)){
			   DbUtil.executeUpdateQuery("delete from seat_booking_status where tid=? and eventdate=? and eventid=?::BIGINT", new String[]{tid,olddate,eid});
			   System.out.println("In Edit Transaction seat released for old date :: "+olddate+" :: for transactionid :: "+tid);
			}
		}		
		}
	
	public static ReportsData getResponseInfo(String eid,String tid){
		System.out.println("getResponseInfo eid: "+eid+" tid: "+tid);
		DBManager db=new DBManager();
		ReportsData reportsData=new ReportsData();
		StatusObj statobj=null;
		String query="select responsetype,yescount,notsurecount from rsvp_transactions where eventid=? and tid=?";
		statobj=db.executeSelectQuery(query,new String[]{eid,tid});
		
		int count=statobj.getCount();
		if(statobj.getStatus() && count>0){
			for(int i=0;i<count;i++){
				reportsData.setResponsetype(db.getValue(i,"responsetype",""));
				reportsData.setSurecount(db.getValue(i,"yescount",""));
				reportsData.setNotsurecount(db.getValue(i,"notsurecount",""));
				
			}
		};
		return reportsData;
	}
	
	public static void updateResponses(ReportsData reportsData,String eid,String tid){
		System.out.println("updateResponses eid: "+eid+" tid: "+tid);
		StatusObj statobj=null;
		//System.out.println("we r in update responses in transactiondb");
		String new_responsetype=reportsData.getResponsetype();
		String new_addsure=reportsData.getAddsure();
		String new_addnotsure=reportsData.getAddnotsure();
		
		ReportsData old_reportsdata=getResponseInfo(eid,tid);
		String old_surecount=old_reportsdata.getSurecount();
		String old_notsurecount=old_reportsdata.getNotsurecount();
		String old_responsetype=old_reportsdata.getResponsetype();
		
		//System.out.println("old one"+old_responsetype+"new one"+new_responsetype);
		/*if("N".equals(old_responsetype) && "notattending".equals(new_responsetype)){
			
		}*/
		
	   if("N".equals(old_responsetype)){
			 
			 notattendingToAttending(eid,tid,new_addsure,new_addnotsure);
			   
		}    
		
		else if("Y".equals(old_responsetype)){
			
			//int surediff= getSureDiff(old_surecount,new_surecount);
			//int notsurediff= getNotSureDiff(old_notsurecount,new_notsurecount);
			attendingToAttending(eid,tid,new_addsure,new_addnotsure,old_surecount,old_notsurecount);
			
		}
		
	}
	
	public static void attendingToNotattending(String eid,String tid){
		System.out.println("attendingToNotattending eid: "+eid+" tid: "+tid);
		try{
		StatusObj statobj=null;
		
		String RSVP_TransactionInfo="update rsvp_transactions set responsetype='N',yescount=0," +
		                              " notsurecount=0 where eventid=? and tid=?";
        statobj=DbUtil.executeUpdateQuery(RSVP_TransactionInfo, new String[]{eid,tid});
     
       String Tarnsaction_Tickets="delete from transaction_tickets where eventid=? and tid=?";
       statobj=DbUtil.executeUpdateQuery(Tarnsaction_Tickets, new String[]{eid,tid});
      

       String Insert_Transaction_Tickets="insert into transaction_tickets(ticketname,ticketid," +
		                              " ticketqty,tid,eventid) values('Not Attending',100,1,?,?)";
       statobj=DbUtil.executeUpdateQuery(Insert_Transaction_Tickets,new String[]{tid,eid});
     
 
       String Profile_Base_Info="delete from profile_base_info where eventid=CAST(? AS BIGINT)" +
		                         " and transactionid=?";
       statobj=DbUtil.executeUpdateQuery(Profile_Base_Info, new String[]{eid,tid});
      
		}catch(Exception e){
			System.out.println("Exception occured in attendingToNotattending for tid:"+tid+":"+eid);
		}
		
	}
	
	public static void notattendingToAttending(String eid,String tid,String new_surecount,String new_notsurecount){
		System.out.println("notattendingToAttending eid: "+eid+" tid: "+tid);
		try{ 
		DBManager db=new DBManager();
		 StatusObj statobj=null;
		 String RSVP_TransactionInfo="update rsvp_transactions set responsetype='Y',yescount=to_number(?,'9999999999')," +
         " notsurecount=to_number(?,'9999999999') where eventid=? and tid=?";
         statobj=DbUtil.executeUpdateQuery(RSVP_TransactionInfo, new String[]{new_surecount,new_notsurecount,eid,tid});
        
         String delete_transaction_tickets="delete from transaction_tickets where eventid=? and tid=? ";
         statobj=DbUtil.executeUpdateQuery(delete_transaction_tickets,new String[]{eid,tid});

         
         HashMap buyerMap=getBuyerData(eid,tid);
		 String  fname=(String)buyerMap.get("fname");
	     String  lname=(String)buyerMap.get("lname");
	     String  phone=(String)buyerMap.get("phone");
	     String  email=(String)buyerMap.get("email");
	   
       if(Integer.parseInt(new_surecount)>0){

           String Transaction_Tickets="insert into transaction_tickets(ticketname,ticketid,ticketqty,eventid,tid)" +
                            " values('Attending',101,to_number(?,'9999999999'),?,?) ";
           statobj=DbUtil.executeUpdateQuery(Transaction_Tickets, new String[]{new_surecount,eid,tid});
        
           
          String profilebasequery="insert into profile_base_info(eventid,fname,lname,transactionid,phone,email," +
    	     		                  "profilekey,ticketid,tickettype,profileid,created_at)"
                                    +" values(CAST(? AS BIGINT),?,?,?,?,?,?,'101','Attending'" +
                    		          ",CAST(? AS INTEGER),now())";

            for(int i=0;i<Integer.parseInt(new_surecount);i++){
            	
            	 String profileid=DbUtil.getVal("select nextval('SEQ_attendeeid')",new String[]{});
      			 String profilekey="AK"+EncodeNum.encodeNum(profileid).toUpperCase();
      	    	 statobj=DbUtil.executeUpdateQuery(profilebasequery,
      	    			                            new String[]{eid,fname,lname,tid,phone,email,profilekey,profileid});
      	    
      	    	
            }

         }
       if(Integer.parseInt(new_notsurecount)>0){

          String Transaction_Tickets="insert into transaction_tickets(ticketname,ticketid,ticketqty,eventid,tid)" +
                            " values('Not Sure',102,to_number(?,'9999999999'),?,?) ";
           statobj=DbUtil.executeUpdateQuery(Transaction_Tickets, new String[]{new_notsurecount,eid,tid});
          
           
           String profilebasequery="insert into profile_base_info(eventid,fname,lname,transactionid,phone,email," +
                                     " profilekey,ticketid,tickettype,profileid)"
                                     +" values(CAST(? AS BIGINT),?,?,?,?,?,?,'102','Attending'" +
	                                 ",CAST(? AS INTEGER))";

           for(int i=0;i<Integer.parseInt(new_notsurecount);i++){

                String profileid=DbUtil.getVal("select nextval('SEQ_attendeeid')",new String[]{});
                String profilekey="AK"+EncodeNum.encodeNum(profileid).toUpperCase();
                statobj=DbUtil.executeUpdateQuery(profilebasequery,
                                          new String[]{eid,fname,lname,tid,phone,email,profilekey,profileid});
              

             }

           
         }
         
		}catch(Exception e){
			System.out.println("Exception occured in notattendingToAttending for tid:"+tid+":"+eid);
		}
	}
	
 /*    public static int getSureDiff(String old_surecount,String new_surecount){
        if (old_surecount == null)
    		 old_surecount = "0";
 		if (new_surecount == null)
 			new_surecount = "0";
 		if (old_surecount == new_surecount)
 			return 0;
 		int old_sure= 0;
 		int new_sure = 0;
 		try {
 			old_sure= Integer.parseInt(old_surecount);
 		} catch (Exception e) {
 		}
 		try {
 			new_sure = Integer.parseInt(new_surecount);
 		} catch (Exception e) {
 		}
 		return old_sure - new_sure;

     }

     public static int getNotSureDiff(String old_notsurecount,String new_notsurecount){
    	 
    	 if (old_notsurecount == null)
    		 old_notsurecount = "0";
 		if (new_notsurecount == null)
 			new_notsurecount = "0";
 		if (old_notsurecount == new_notsurecount)
 			return 0;
 		int old_notsure = 0;
 		int new_notsure= 0;
 		try {
 			old_notsure = Integer.parseInt(old_notsurecount);
 		} catch (Exception e) {
 		}
 		try {
 			new_notsure = Integer.parseInt(new_notsurecount);
 		} catch (Exception e) {
 		}
 		return old_notsure - new_notsure;

    	 
     }*/
     
     public static void attendingToAttending(String eid,String tid,String new_addsure,
    		 String new_addnotsure,String old_surecount,String old_notsurecount)
    		 {
    	 System.out.println("attendingToAttending eid: "+eid+" tid: "+tid);
    	 try{
    	 StatusObj statobj=null;
    	 DBManager db=new DBManager();
 		 String sure_count=null;
 		 String notsure_count=null;
 		 HashMap buyerMap=getBuyerData(eid,tid);
		 String  fname=(String)buyerMap.get("fname");
	     String  lname=(String)buyerMap.get("lname");
	     String  phone=(String)buyerMap.get("phone");
	     String  email=(String)buyerMap.get("email");
	     
	     int yescount=Integer.parseInt(old_surecount)+Integer.parseInt(new_addsure);
	     int notsurecount=Integer.parseInt(old_notsurecount)+Integer.parseInt(new_addnotsure);
	     
	     
	   
 	     
 		String RSVP_TransactionInfo="update rsvp_transactions set yescount=to_number(?,'9999999999')," +
 		                              " notsurecount=to_number(?,'9999999999') where eventid=? and tid=?";
        statobj=DbUtil.executeUpdateQuery(RSVP_TransactionInfo, new String[]{Integer.toString(yescount),Integer.toString(notsurecount),eid,tid});
        
        
        String  Select_Rsvp_TransactionInfo="select yescount,notsurecount from rsvp_transactions where " +
        	                               " eventid=? and tid=? ";
        statobj=db.executeSelectQuery(Select_Rsvp_TransactionInfo,new String[]{eid,tid});
        int count=statobj.getCount();
       
        if(statobj.getStatus() && count>0){
        for(int i=0;i<count;i++){
        	sure_count=db.getValue(i,"yescount","");
        	notsure_count=db.getValue(i,"notsurecount","");

        	 }
        }
        	                                  
       String delete_transactiontickets="delete from transaction_tickets where eventid=? and tid=?";
       statobj=DbUtil.executeUpdateQuery(delete_transactiontickets,new String[]{eid,tid});
        
       if(Integer.parseInt(sure_count)>0){
       String update_transactiontickets="insert into transaction_tickets(ticketid,ticketname,ticketqty,eventid,tid) " +
       		" values(101,'Attending',to_number(?,'99999999'),?,?)";
       statobj=DbUtil.executeUpdateQuery(update_transactiontickets, new String[]{sure_count,eid,tid});
       }
       if(Integer.parseInt(notsure_count)>0){
       String update_transactiontickets1="insert into transaction_tickets(ticketid,ticketname,ticketqty,eventid,tid) " +
  		" values(102,'Not Sure',to_number(?,'99999999'),?,?)";
       statobj=DbUtil.executeUpdateQuery(update_transactiontickets1, new String[]{notsure_count,eid,tid});
       }
        
        if(Integer.parseInt(new_addsure)>0){
      
        String profilebasequery="insert into profile_base_info(eventid,fname,lname,transactionid,phone,email," +
         "profilekey,ticketid,tickettype,profileid,created_at)"
      +" values(CAST(? AS BIGINT),?,?,?,?,?,?,'101','Attending'" +
        ",CAST(? AS INTEGER),now())";

         for(int i=0;i<Integer.parseInt(new_addsure);i++){

                String profileid=DbUtil.getVal("select nextval('SEQ_attendeeid')",new String[]{});
                String profilekey="AK"+EncodeNum.encodeNum(profileid).toUpperCase();
                statobj=DbUtil.executeUpdateQuery(profilebasequery,
                        new String[]{eid,fname,lname,tid,phone,email,profilekey,profileid});
            

         }


        }
        
        if(Integer.parseInt(new_addnotsure)>0){
        
        String profilebasequery="insert into profile_base_info(eventid,fname,lname,transactionid,phone,email," +
        " profilekey,ticketid,tickettype,profileid)"
        +" values(CAST(? AS BIGINT),?,?,?,?,?,?,'102','Attending'" +
        ",CAST(? AS INTEGER))";

           for(int i=0;i<Integer.parseInt(new_addnotsure);i++){

                String profileid=DbUtil.getVal("select nextval('SEQ_attendeeid')",new String[]{});
                String profilekey="AK"+EncodeNum.encodeNum(profileid).toUpperCase();
                statobj=DbUtil.executeUpdateQuery(profilebasequery,
              new String[]{eid,fname,lname,tid,phone,email,profilekey,profileid});
       

            }



        }
     }catch(Exception e){
    	 System.out.println("Exception occured in attendingToAttending for tid:"+tid+":"+eid);
     }
    	 
     }
     
    public static void convetingToNotsure(String eid,String tid,String pid,String ticketid){
    	System.out.println("convetingToNotsure eid: "+eid+" tid: "+tid);
    	try{
    	StatusObj statobj=null;
    	DBManager db=new DBManager();
    	String surecount=null;
    	String notsurecount=null;
    	String sum=null;
    	
    	String rsvp_transaction1="update rsvp_transactions set notsurecount=notsurecount+1,yescount=yescount-1 where eventid=? and tid=?";
    	statobj=DbUtil.executeUpdateQuery(rsvp_transaction1,new String[]{eid,tid});
    	
    	
    	
    	String transaction_tickets1="delete from transaction_tickets where eventid=? and tid=?";
    	statobj=DbUtil.executeUpdateQuery(transaction_tickets1,new String[]{eid,tid});
    	
    	
    	String rsvp_transaction="select yescount,notsurecount,yescount+notsurecount as count from rsvp_transactions where eventid=? and tid=?";
        statobj=db.executeSelectQuery(rsvp_transaction,new String[]{eid,tid});
    	
        int count=statobj.getCount();
        if(statobj.getStatus() && count>0){
	     for(int i=0;i<count;i++){
	       surecount=db.getValue(i,"yescount","");
	       notsurecount=db.getValue(i,"notsurecount","");
	       sum=db.getValue(i,"count","");
	      	
	    }
      }
 if(Integer.parseInt(sum)==0){
	String rsvp_transactions4="update rsvp_transactions set responsetype='N',yescount=0,notsurecount=0 " +
			" where eventid=? and tid=?";
	statobj=DbUtil.executeUpdateQuery(rsvp_transactions4,new String[]{eid,tid});
	
	
	
	String transaction_tickets2="insert into transaction_tickets(ticketid,ticketname,ticketqty,tid,eventid)" +
	" values(100,'Not Attending',1,?,?)";
    statobj =DbUtil.executeUpdateQuery(transaction_tickets2,new String[]{surecount,tid,eid});
   
	
 }
 
  if(Integer.parseInt(surecount)>0){
		String transaction_tickets="insert into transaction_tickets(ticketid,ticketname,ticketqty,tid,eventid)" +
				" values(101,'Attending',to_number(?,'999999999'),?,?)";
		statobj =DbUtil.executeUpdateQuery(transaction_tickets,new String[]{surecount,tid,eid});
	
 }
  if(Integer.parseInt(notsurecount)>0){
	String transaction_tickets="insert into transaction_tickets(ticketid,ticketname,ticketqty,tid,eventid)" +
			" values(102,'Not Sure',to_number(?,'999999999'),?,?)";
	statobj =DbUtil.executeUpdateQuery(transaction_tickets,new String[]{notsurecount,tid,eid});
	
 }


    	
         String profile_base_info="update profile_base_info set ticketid=102" +
	       " where eventid=CAST(? AS BIGINT) and transactionid=? and ticketid=101 and profilekey=?";
       statobj=DbUtil.executeUpdateQuery(profile_base_info,new String[]{eid,tid,pid});
        
    	}catch(Exception e){
    		System.out.println("Exception occured in convetingToNotsure for tid:"+tid+":"+eid);
    	}
    	
    }
    public static void convetingToSure(String eid,String tid,String pid,String ticketid){
    	System.out.println("convetingToSure eid: "+eid+" tid: "+tid);
    	try{
    	StatusObj statobj=null;
    	DBManager db=new DBManager();
    	String surecount=null;
    	String notsurecount=null;
    	String sum=null;
    	String rsvp_transaction1="update rsvp_transactions set notsurecount=notsurecount-1,yescount=yescount+1 where eventid=? and tid=?";
    	statobj=DbUtil.executeUpdateQuery(rsvp_transaction1,new String[]{eid,tid});
    	
    	
    	
    	String transaction_tickets1="delete from transaction_tickets where eventid=? and tid=?";
    	statobj=DbUtil.executeUpdateQuery(transaction_tickets1,new String[]{eid,tid});
    	
    	
    	String rsvp_transaction="select yescount,notsurecount,yescount+notsurecount as count from rsvp_transactions where eventid=? and tid=?";
        statobj=db.executeSelectQuery(rsvp_transaction,new String[]{eid,tid});
    	
    	
    	
    	
    	int count=statobj.getCount();
    	if(statobj.getStatus() && count>0){
    		for(int i=0;i<count;i++){
    		 surecount=db.getValue(i,"yescount","");
    		 notsurecount=db.getValue(i,"notsurecount","");	
    		 sum=db.getValue(i,"count","");
    			
    		}
    	}
    	if(sum!=null && Integer.parseInt(sum)==0){
    		String rsvp_transactions4="update rsvp_transactions set responsetype='N',yescount=0,notsurecount=0 " +
    				" where eventid=? and tid=?";
    		statobj=DbUtil.executeUpdateQuery(rsvp_transactions4,new String[]{eid,tid});
        	
        	
        	
           String transaction_tickets2="insert into transaction_tickets(ticketid,ticketname,ticketqty,tid,eventid)" +
			" values(100,'Not Attending',1,?,?)";
	        statobj =DbUtil.executeUpdateQuery(transaction_tickets2,new String[]{surecount,tid,eid});
	       
        	
    	}
    	 
    	if(Integer.parseInt(surecount)>0){
    		
    			String transaction_tickets="insert into transaction_tickets(ticketid,ticketname,ticketqty,tid,eventid)" +
    					" values(101,'Attending',to_number(?,'999999999'),?,?)";
    			statobj =DbUtil.executeUpdateQuery(transaction_tickets,new String[]{surecount,tid,eid});
    			
    	}
    	if(Integer.parseInt(notsurecount)>0){
			String transaction_tickets="insert into transaction_tickets(ticketid,ticketname,ticketqty,tid,eventid)" +
					" values(102,'Not Sure',to_number(?,'999999999'),?,?)";
			statobj =DbUtil.executeUpdateQuery(transaction_tickets,new String[]{notsurecount,tid,eid});
		
	}
	
    	
    	String profile_base_info="update profile_base_info set ticketid=101" +
    			" where eventid=CAST(? AS BIGINT) and transactionid=? and ticketid=102 and profilekey=?";
    	statobj=DbUtil.executeUpdateQuery(profile_base_info,new String[]{eid,tid,pid});
    	
    	
    	}catch(Exception e){
    		System.out.println("Exception occured in convetingToSure for tid:"+tid+":"+eid);
    	}
    }
    
    
    public static void convetingToNotAttending(String eid,String tid,String pid,String ticketid){
    	System.out.println("convetingToNotAttending eid: "+eid+" tid: "+tid);
    	try{
    	StatusObj statobj=null;
    	String quantity101=null;
    	String quantity102=null;
    	String qty=null;
    	String sum=null;
    	
    	
    	if(Integer.parseInt(ticketid)==101){
    		
        	String rsvp_transaction1="update rsvp_transactions set yescount=yescount-1" +
        			                 " where eventid=? and tid=?";
        	statobj=DbUtil.executeUpdateQuery(rsvp_transaction1,new String[]{eid,tid});
        	
        	
        	String transaction_tickets1="update transaction_tickets set ticketqty=ticketqty-1 where eventid=?" +
        			" and tid=? and ticketid=101";
        	statobj=DbUtil.executeUpdateQuery(transaction_tickets1,new String[]{eid,tid});
        	
        	
        	String profile_base_info="delete from profile_base_info " +
        			" where eventid=CAST(? AS BIGINT) and transactionid=? and" +
        			" ticketid=to_number(?,'999999999') and profilekey=?";
        	statobj=DbUtil.executeUpdateQuery(profile_base_info,new String[]{eid,tid,ticketid,pid});
        
        	
    		
    	}
    	else{
    		String rsvp_transaction2="update rsvp_transactions set notsurecount=notsurecount-1" +
            " where eventid=? and tid=?";
           statobj=DbUtil.executeUpdateQuery(rsvp_transaction2,new String[]{eid,tid});
          // System.out.println(" converting notsure to not attending rsvp_transactions2 status"+statobj.getStatus());
            
           
           String transaction_tickets2="update transaction_tickets set ticketqty=ticketqty-1 where eventid=?" +
			" and tid=? and ticketid=102";
           statobj=DbUtil.executeUpdateQuery(transaction_tickets2,new String[]{eid,tid});
	      // System.out.println(" converting notsure to attending transaction_tickets2 status"+statobj.getStatus());
	       
	            String profile_base_info="delete from profile_base_info  " +
			         " where eventid=CAST(? AS BIGINT) and transactionid=? and" +
			          " ticketid=to_number(?,'999999999') and profilekey=?";
	          statobj=DbUtil.executeUpdateQuery(profile_base_info,new String[]{eid,tid,ticketid,pid});
	        //  System.out.println(" converting notsure to notattending transaction_tickets status"+statobj.getStatus());
	
	
    	}
    	
    	String rsvp_transactions3="select yescount+notsurecount from rsvp_transactions where eventid=? and tid=?";
    	 sum=DbUtil.getVal(rsvp_transactions3, new String[]{eid,tid});
    	if(sum!=null && Integer.parseInt(sum)==0){
    		String rsvp_transactions4="update rsvp_transactions set responsetype='N',yescount=0,notsurecount=0 " +
    				" where eventid=? and tid=?";
    		statobj=DbUtil.executeUpdateQuery(rsvp_transactions4,new String[]{eid,tid});
       
        	
    	}
    	 
    	String transaction_tickets3="select sum(ticketqty) as qty from transaction_tickets where eventid=?" +
    			" and tid=?";
    	 qty=DbUtil.getVal(transaction_tickets3, new String[]{eid,tid});
    	
    	if(qty!=null && Integer.parseInt(qty)==0){
    		String transaction_tickets4="delete from transaction_tickets where eventid=? and tid=?";
    				
    		statobj=DbUtil.executeUpdateQuery(transaction_tickets4,new String[]{eid,tid});
        	
        	
        	 String transaction_tickets5="insert into transaction_tickets(ticketname,ticketid,ticketqty,eventid,tid)" +
             " values('Not Attending',100,1,?,?) ";
             statobj=DbUtil.executeUpdateQuery(transaction_tickets5, new String[]{eid,tid});
          
        	
    	}
    	String  transaction_tickets6="select ticketqty from transaction_tickets where eventid=? and tid=? and ticketid=101";
        quantity101=DbUtil.getVal(transaction_tickets6, new String[]{eid,tid});
    	if(quantity101!=null && Integer.parseInt(quantity101)==0){
    		
    		String delete_transactiontickets="delete from transaction_tickets where eventid=? and tid=? and ticketid=101";
    		statobj=DbUtil.executeUpdateQuery(delete_transactiontickets, new String[]{eid,tid});
    		
    	}
    	String  transaction_tickets7="select ticketqty from transaction_tickets where eventid=? and tid=? and ticketid=102";
        quantity102=DbUtil.getVal(transaction_tickets7, new String[]{eid,tid});
    	if(quantity102!=null && Integer.parseInt(quantity102)==0){
    		
    		String delete_transactiontickets="delete from transaction_tickets where eventid=? and tid=? and ticketid=102";
    		statobj=DbUtil.executeUpdateQuery(delete_transactiontickets, new String[]{eid,tid});
    		
    	}
    	
    	}catch(Exception e){
    		System.out.println("Exception occured in convetingToNotAttending for tid:"+tid+":"+eid);
    	}
    }
    
    public static void updateRSVPEventDate(String eid,String tid,String newdate,String olddate){
    	System.out.println("updateRSVPEventDate eid: "+eid+" tid: "+tid);
    	String query="update event_reg_transactions set eventdate=? where eventid=? and tid=?";
		StatusObj statobj=DbUtil.executeUpdateQuery(query,new String[]{newdate,eid,tid});
		
	 }
    
    public static int coreConnector(HashMap paramMap){
    	int status=0;
    	try{
    	com.eventbee.util.CoreConnector cc1=new com.eventbee.util.CoreConnector(com.eventbee.general.EbeeConstantsF.get("CONNECTING_PDF_URL",serverAddress+"/attendee/utiltools/sendPdfMail.jsp"));
		cc1.setArguments(paramMap);
	    cc1.setTimeout(30000);
	    String st=cc1.MGet();
	    status =Integer.parseInt(st.trim());
    	}catch(Exception e){
    		System.out.println("Error in TransactionDB coreConnector: "+e.getMessage());
    	}
	    return status;
    }
    // resending direct mail with bcc list
    public static void resendingMail(String eid,String tid,String powerType){
    	System.out.println("resendingMail eid: "+eid+" tid: "+tid);
    	HashMap paramMap=new HashMap();
		/*if(powerType.equals("RSVP")){
			sendRSVPMail(eid, tid);
		}else{*/
			//RegistrationConfirmationEmail regmail=new RegistrationConfirmationEmail();
			//regmail.sendRegistrationEmail(tid,eid);
			
			paramMap.put("tid",tid);
            paramMap.put("eid",eid);
            paramMap.put("bcc", "bcc");
            paramMap.put("powertype", powerType);
            (new Thread(new GetTicketsThread(paramMap))).start();
		//}
     }
    
    public static void sendRSVPMail(String eid,String tid){
    	sendRSVPMail(eid,tid,"bcc",null);
    }
    
    // resending direct mail with out bcc list
    public static int resendingDirectMail(String eid,String tid,String powerType){
    	return resendingDirectMail(eid,tid,powerType,null);
    }
    public static int resendingDirectMail(String eid,String tid,String powerType,String mailto){
    	System.out.println("resendingDirectMail eid: "+eid+" tid: "+tid);
    	HashMap paramMap=new HashMap();
    	int status = 0;
    	/*if(powerType.equals("RSVP")){
    		status = sendRSVPMail(eid, tid,"nobcc",mailto);
		}else{*/
			//RegistrationConfirmationEmail regmail=new RegistrationConfirmationEmail();
			//status = regmail.sendRegistrationEmail(tid, eid, "nobcc",mailto);
			
			paramMap.put("tid",tid);
            paramMap.put("eid",eid);
            paramMap.put("bcc", "nobcc");
            paramMap.put("mailto", mailto);
            paramMap.put("powertype", powerType);
            status = coreConnector(paramMap);
		//}
    	if(status == 1){
    		
    		DBManager dbm =new DBManager();
			StatusObj statobj=null;
			String query="select et.email,e.eventname from eventinfo e,event_reg_transactions et where e.eventid=CAST(et.eventid AS BIGINT) and et.tid=?";
			statobj=dbm.executeSelectQuery(query,new String[]{tid});
			if(statobj.getStatus() && statobj.getCount()>0){
				String email=dbm.getValue(0,"email","");
				String eventname=dbm.getValue(0,"eventname","");
				if(mailto != null)
					email=mailto;
				DbUtil.executeUpdateQuery("insert into mytickets_track(tid,emailid,eventname,date)values(?,?,?,now())",new String[]{tid,email,eventname});
				System.out.println("data inserted in mytickets_track");
			}
    		
			
		}
    	return status;
     }
    
    public static int sendRSVPMail(String eid,String tid,String bcc,String mailto){
    	System.out.println("sendRSVPMail eid: "+eid+" tid: "+tid);
		String sure="",notsure="",eventdate="",emailid_trac="";
		eventdate=DbUtil.getVal("select eventdate from event_reg_transactions where tid=? and eventid=?",new String[]{tid,eid});
		emailid_trac=DbUtil.getVal("select email from buyer_base_info where transactionid=? and eventid=CAST(? AS BIGINT)", new String[]{tid,eid});
		DBManager db2=new DBManager();
		String attquery="select notsurecount as notsure,yescount as sure from rsvp_transactions where eventid=? and tid=?";
		StatusObj sb=db2.executeSelectQuery(attquery, new String[]{eid,tid});
		if(sb.getStatus()){
			notsure=db2.getValue(0, "notsure", "");
			sure=db2.getValue(0, "sure", "");
		}
		if(eventdate==null || "".equals(eventdate)) eventdate="false";
		if(sure==null || "".equals(sure)) sure="0";
		if(notsure==null || "".equals(notsure)) notsure="0";
		VelocityContext context=new VelocityContext();
		context.put("attendee",sure);
		context.put("notSure",notsure);
		context.put("transactionKey",tid);
		context.put("eventdate",eventdate);
		context.put("eventreglink","");
		context.put("eventid",eid);
		context.put("attendingLabel","Attending");
		context.put("mayBeLabel","MayBe");
		RSVPProfileActionDB.getdetails(tid,eid,context,sure,notsure);
		HashMap rsvpattendee=new HashMap();
		rsvpattendee.put("m_email",context.get("mgrEmail"));
		rsvpattendee.put("GROUPID",eid);
		rsvpattendee.put("emailid",emailid_trac);
		rsvpattendee.put("email",emailid_trac);
		
		
		int r=RSVPRegistrationConfirmationEmail.sendRsvpEmail(rsvpattendee,context,bcc,mailto);
		return r;
}

    
    public static ArrayList<Entity>  populateStatusTypeList(){
		
		ArrayList<Entity> statusType=new ArrayList<Entity>();
		statusType.add(new Entity("Complete",I18n.getString("rep.change.status.complete.lbl")));
		statusType.add(new Entity("Delete",I18n.getString("rep.change.status.delete.lbl")));
		statusType.add(new Entity("Charge Back",I18n.getString("rep.change.status.chargeback.lbl")));
		statusType.add(new Entity("Refund",I18n.getString("rep.change.status.refund.lbl")));
		return statusType;
		
		
	}
    
    public static ArrayList<Entity>  populateOtherStatusTypeList(){
		
		ArrayList<Entity> statusType=new ArrayList<Entity>();
		statusType.add(new Entity("Complete",I18n.getString("rep.change.status.complete.lbl")));
		statusType.add(new Entity("Delete",I18n.getString("rep.change.status.delete.lbl")));
		statusType.add(new Entity("Need Approval",I18n.getString("rep.trans.pending.approval.lbl")));
		return statusType;
		
		
	}
    
    public static String getCurrentStatus(String eid,String tid){
    	String eventregtransactions="select paymentstatus from event_reg_transactions where  tid=? and eventid=?";
    	String paymentstatus=DbUtil.getVal(eventregtransactions,new String[]{tid,eid});
    	return paymentstatus;
    }
    
public static void insertStatus(String eid,String tid,ReportsData reportsData){
    	try{
	    System.out.println("insertStatus method tid: "+tid+" eid: "+eid+" changestatus: "+reportsData.getChangeStatus());
    	//saveNotesData(transactionNotesData,tid);
    	
    	String changestatus=reportsData.getChangeStatus();
    	
    	String currentstatus=TransactionDB.getCurrentStatus(eid,tid);
    	System.out.println("insertStatus method tid: "+tid+" eid: "+eid+" currentstatus: "+currentstatus);
    	if("Delete".equalsIgnoreCase(changestatus)) reportsData.setChangeStatus("Cancelled");
    	if("Complete".equalsIgnoreCase(changestatus)) reportsData.setChangeStatus("Completed");
    	if("Refund".equalsIgnoreCase(changestatus)) reportsData.setChangeStatus("Refunded");
    	if("Charge Back".equalsIgnoreCase(changestatus)) reportsData.setChangeStatus("Chargeback");
    	// Need Approval = Pending Approval
    	if("Need Approval".equalsIgnoreCase(changestatus)) reportsData.setChangeStatus("Need Approval");
    	
    	changestatus=reportsData.getChangeStatus();
    	
    	if(currentstatus.equalsIgnoreCase(changestatus) || "".equalsIgnoreCase(changestatus) || changestatus==null){
    		
    	}
    	else if(("Completed".equalsIgnoreCase(currentstatus) || "Need Approval".equalsIgnoreCase(currentstatus)) && ("Cancelled".equalsIgnoreCase(changestatus) || "Refunded".equalsIgnoreCase(changestatus) || "Chargeback".equals(changestatus))){
    		
    	    completedToOtherStatus( eid, tid,changestatus);
    
    		
    	}
    	else if(("Cancelled".equalsIgnoreCase(currentstatus) || "Refunded".equalsIgnoreCase(currentstatus)|| "Chargeback".equals(currentstatus) )&&("Completed".equalsIgnoreCase(changestatus) || "Need Approval".equalsIgnoreCase(changestatus))){
    		
    		undeleteTransaction(eid,tid,changestatus);
    	
    		
    	}
    	
    	else{
			if("Need Approval".equalsIgnoreCase(currentstatus) && "Completed".equalsIgnoreCase(changestatus)){
    			System.out.println("Sending Email when staus from need approval to completed:"+eid+"::tid:"+tid);
    			String powertype=EventDB.getPowerType(eid);
    			resendingMail(eid,tid,powertype);
    		}
    	         DbUtil.executeUpdateQuery(
    					"update event_reg_transactions set paymentstatus=? where tid=? and eventid=?",
    					new String[] { changestatus, tid, eid });
    	         System.out.println("updated with status:"+tid+":"+changestatus);
    	
      			}
    	String status="change status from "+currentstatus+" to "+changestatus;
    	System.out.println(status+":"+tid);
    	String query="insert into editmanage_attendeetrack(eventid,deleted_at,tid,actiontype)values(?,now(),"+
      			     "?,?)";
    	DbUtil.executeUpdateQuery(query,new String[]{eid,tid,status});
    	
    	}catch(Exception e){
    		System.out.println("Exception occured in insertStatus method:"+tid+":"+eid);
    	}
    	//return "";
    }
    
    public static void completedToOtherStatus(String eid,String tid,String changestatus){
    	System.out.println("completedToOtherStatus eid: "+eid+" tid: "+tid+" changestatus: "+changestatus);
    	String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		String isrecurr=DbUtil.getVal("select value from config where name='event.recurring' and config_id=CAST(? AS INTEGER)",new String[]{configid});
		
		StatusObj statobj = DbUtil.executeUpdateQuery("update event_reg_transactions set paymentstatus=? where tid=? and eventid=?", new String[] { changestatus, tid, eid });
		System.out.println("total rows updated in completedToOtherStatus method for "+tid+" is:"+statobj.getCount());
		DbUtil.executeUpdateQuery("update priority_reg_transactions set status=? where tid=? and eventid=?", new String[] { changestatus, tid, eid });
		if(changestatus.equalsIgnoreCase("Cancelled") || changestatus.equalsIgnoreCase("Refunded") || changestatus.equalsIgnoreCase("Chargeback")){
			String isSeatingEvent=DbUtil.getVal("select 'yes' from config where name='event.seating.venueid' and config_id in(select config_id from eventinfo where eventid=CAST(? AS BIGINT) )", new String[]{eid});
			if(isSeatingEvent==null) isSeatingEvent=""; 
			if("yes".equalsIgnoreCase(isSeatingEvent))
				releaseSeats(eid,tid);
		}
		if("Y".equals(isrecurr)) rec_updateSoldQty(tid,eid,"-1");
		else updateSoldQty(tid, eid, "-1");
    }
    public static void releaseSeats(String eid,String tid){
		System.out.println("releaseSeats tid: "+tid+" eventid: "+eid);
		DbUtil.executeUpdateQuery("delete from seat_booking_status where eventid=CAST(? AS BIGINT) and tid=?", new String[]{eid,tid});
		DbUtil.executeUpdateQuery("delete from event_reg_block_seats_temp where eventid=? and transactionid=?", new String[]{eid,tid});
		DbUtil.executeUpdateQuery("update profile_base_info set seatcode=NULL,seatindex=NULL where eventid=CAST(? AS BIGINT) and transactionid=?", new String[]{eid,tid});
	}
    
    public static String getProfileCount(String eventid,String tid){
    	String profilecount=DbUtil.getVal("select count(*) from profile_base_info where transactionid=? and eventid=CAST(? AS BIGINT)",new String[]{tid,eventid}); 
        return profilecount;
    }
    
    public static String getEventType(String eventid){
    	String eventtype=DbUtil.getVal("select 'Y' from config where config_id in(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.rsvp.enabled'", new String[]{eventid});
    	if("Y".equals(eventtype))
    		eventtype="RSVP";
    	else
    		eventtype="Ticketing";
    	return eventtype;
    }
    public static void deleteAttendeeInfo(String profkey,String eid,String ticketid,String tid,String notes){
 	   System.out.println("deleteAttendeeInfo tid: "+tid+" eid: "+eid);
    	try{
 		    String actualreducedqty="1",discount="",ticketstotalprice="",totalamount="",ticketsdiscountprice="",remainingqty="",totaldiscount="";
 		    List totalamounts=null;
 		    //String ticketqty=DbUtil.getVal("select sum(ticketqty) as qty from transaction_tickets where tid=? and eventid=? and ticketid=to_number(?,'99999999999')",new String[]{tid,eid,ticketid});
 		    String ticketqty=DbUtil.getVal("select ticketqty as qty from transaction_tickets where tid=? and eventid=? and ticketid=to_number(?,'99999999999')",new String[]{tid,eid,ticketid});
 		    DBManager dbm=new DBManager();
 		    StatusObj statobj=null;
 		    statobj=dbm.executeSelectQuery("select discount,ticketstotal from transaction_tickets where tid=? and eventid=? and ticketid=to_number(?,'999999999')", new String[]{tid,eid,ticketid});
 		    
 		    if(statobj.getStatus()){
 		    	discount=CurrencyFormat.getCurrencyFormat("",dbm.getValue(0,"discount",""),false);
 		    	ticketstotalprice=CurrencyFormat.getCurrencyFormat("",dbm.getValue(0,"ticketstotal",""),false);
 		    }
 		    
 		    if(!"".equals(ticketqty) && !"".equals(actualreducedqty))
 		    remainingqty=Integer.toString(Integer.parseInt(ticketqty)-Integer.parseInt(actualreducedqty));
 		    
 		    if(!"".equals(discount) && !"".equals(ticketqty) && !"".equals(remainingqty))
 		    discount=Double.toString((Double.parseDouble(discount)/Integer.parseInt(ticketqty))*Integer.parseInt(remainingqty));
 		    discount=CurrencyFormat.getCurrencyFormat("",discount,false);
 		    
 		    if(!"".equals(ticketstotalprice) && !"".equals(ticketqty) && !"".equals(remainingqty)){
 		        if(Integer.parseInt(ticketqty)>1)
 		    	ticketsdiscountprice=Double.toString(((Double.parseDouble(ticketstotalprice)/Integer.parseInt(ticketqty))*Integer.parseInt(remainingqty)));
 		        else
 		        ticketsdiscountprice=Double.toString(Double.parseDouble(ticketstotalprice));	
 		    }
 		    ticketsdiscountprice=CurrencyFormat.getCurrencyFormat("",ticketsdiscountprice,false);
 		    
 		    insertAttendeeTrack(tid,profkey,eid,ticketid,notes);
 		    
 		    DbUtil.executeUpdateQuery("delete from profile_base_info where profilekey=? and eventid=CAST(? AS BIGINT)", new String[]{profkey,eid});
 		    DbUtil.executeUpdateQuery("update transaction_tickets set ticketstotal=to_number(?,'999999999.99'),ticketqty=CAST(? AS INTEGER),discount=to_number(?,'999999999.99') where eventid=? and tid=? and ticketid=CAST(? AS BIGINT)", new String[]{ticketsdiscountprice,remainingqty,discount,eid,tid,ticketid});
  		    
 		    if(Integer.parseInt(remainingqty)==0){
  		      System.out.println("remainingqty is 0 case:"+tid+":"+eid);
  		    	DbUtil.executeUpdateQuery("delete from transaction_tickets where eventid=? and tid=? and ticketid=CAST(? AS BIGINT)", new String[]{eid,tid,ticketid});	
  		    	
  		    	String priorityRegQry="select ticketid from transaction_tickets where ticketid in(select unnest(string_to_array(pl.tickets, ',')::bigint[]) from priority_reg_transactions pr, priority_list pl"+ 
  		    						" where pr.eventid=CAST(? AS BIGINT) and pr.tid=? and pr.list_id=pl.list_id and pr.eventid=pl.eventid) and tid=? and eventid=?";
  		    	
  		    	List priorityTickets=DbUtil.getValues(priorityRegQry, new String[]{eid,tid,tid,eid});
  		    	if(priorityTickets.size()==0) 
  		    		DbUtil.executeUpdateQuery("update priority_reg_transactions set status='No Priority' where tid=? and status='Completed' and eventid=CAST(? AS BIGINT)", new String[]{tid,eid});
  		    	
  		    }	
 		    statobj=dbm.executeSelectQuery("select sum(ticketstotal) as ticketstotal,sum(discount) as discount from transaction_tickets where tid=? and eventid=?", new String[]{tid,eid});
 		    if(statobj.getStatus()){
 		    for(int i=0;i<statobj.getCount();i++){
 		    totalamount=CurrencyFormat.getCurrencyFormat("",dbm.getValue(i,"ticketstotal",""),false);
 		    totaldiscount=CurrencyFormat.getCurrencyFormat("",dbm.getValue(i,"discount",""),false);
 		    }
 		    }
 		    String tax=DbUtil.getVal("select current_tax from event_reg_transactions where eventid=? and tid=?", new String[]{eid,tid});
 		    if(tax==null)tax=""; 
 		    if(!"".equals(tax)){
 		    totalamount=Double.toString((Double.parseDouble(totalamount)+Double.parseDouble(tax)));
 		    totalamount=CurrencyFormat.getCurrencyFormat("",totalamount,false);
 		    }
 		    DbUtil.executeUpdateQuery("update event_reg_transactions set current_amount=to_number(?,'999999999.99'),current_discount=to_number(?,'999999999.99'),original_discount=to_number(?,'999999999.99') where eventid=? and tid=?", new String[]{totalamount,totaldiscount,totaldiscount,eid,tid});
 		    String isrecurr=DbUtil.getVal("select value from config where name='event.recurring' and config_id in(select config_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[]{eid});
 		    if("Y".equals(isrecurr)){
 		    	System.out.println("in recurring case:"+tid+":"+eid);
 		    	DbUtil.executeUpdateQuery("update reccurringevent_ticketdetails set soldqty=soldqty-"+actualreducedqty+" where eventid=CAST(? AS BIGINT) and ticketid=to_number(?,'999999999') and eventdate in(select eventdate from event_reg_transactions where eventid=? and tid=?)", new String[]{eid,ticketid,eid,tid});
 		    	DbUtil.executeUpdateQuery("update price set sold_qty=sold_qty-"+actualreducedqty+" where evt_id=CAST(? AS BIGINT) and price_id=to_number(?,'999999999')", new String[]{eid,ticketid});
 		    }else{
 		    	System.out.println("not recurring case:"+tid+":"+eid);
 		    	DbUtil.executeUpdateQuery("update price set sold_qty=sold_qty-"+actualreducedqty+" where evt_id=CAST(? AS BIGINT) and price_id=to_number(?,'999999999')", new String[]{eid,ticketid});
 		    }String isSeatingEvent=DbUtil.getVal("select 'yes' from config where name='event.seating.venueid' and config_id in(select config_id from eventinfo where eventid=CAST(? AS BIGINT) )", new String[]{eid});
 			if(isSeatingEvent==null) isSeatingEvent=""; 
 			if("yes".equalsIgnoreCase(isSeatingEvent))
 				DbUtil.executeUpdateQuery("delete from seat_booking_status where profilekey=?", new String[]{profkey});
 			    
    	   }catch(Exception e){
 		   System.out.println("Exception occured in deleteAttendeeInfo method :"+eid+":"+tid+e.getMessage());
 	   }
    }
    
    public static void insertAttendeeTrack(String tid,String profkey,String eid,String ticketid,String notes){
    	System.out.println("insertAttendeeTrack tid: "+tid+" eid: "+eid);
    	String fname="",lname="",email="",seatcode="",seatindex="",profileid="";
    	String currentdate=DateUtil.getCurrDBFormatDate();
    	String profileinfoquery="select fname,lname,email,seatcode,seatindex,profileid from profile_base_info where transactionid=? and eventid=CAST(? AS BIGINT) and profilekey=?";
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
    	statobj=dbm.executeSelectQuery(profileinfoquery,new String[]{tid,eid,profkey});
		    if(statobj.getStatus()){
		     for(int i=0;i<statobj.getCount();i++){
		      fname=dbm.getValue(i,"fname","");
		      lname=dbm.getValue(i,"lname","");
		      email=dbm.getValue(i,"email","");
		      seatcode=dbm.getValue(i,"seatcode","");
		      seatindex=dbm.getValue(i,"seatindex","");
		      profileid=dbm.getValue(i,"profileid","");
		      String actiontype="delete attendee";
		      DbUtil.executeUpdateQuery("insert into editmanage_attendeetrack(fname,lname,email,originalseatcode,originalseatindex,profileid,tid,profilekey,eventid,deleted_at,ticketid,notes,actiontype)"
		    			+"values(?,?,?,?,?,?,?,?,?,to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.ms'),?,?,?)",
		    			new String[]{fname,lname,email,seatcode,seatindex,profileid,tid,profkey,eid,currentdate,ticketid,notes,actiontype});
		     }
		 }
    	
			
    }
    
    public static String getSeatingStatus(String eventid){
    	String seatingstatus="";
    	seatingstatus=DbUtil.getVal("select 'Y' from config where config_id in(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.seating.enabled'", new String[]{eventid});
    	return seatingstatus;
    }
    
    public static String getProfileKeyStatus(String profilekey,String eventid,String ticketid,String tid){
       String profilekeystatus=DbUtil.getVal("select 'Y' from profile_base_info where transactionid=? and eventid=CAST(? AS BIGINT) and profilekey=? and ticketid=to_number(?,'999999999')", new String[]{tid,eventid,profilekey,ticketid});	
       if(profilekeystatus==null)profilekeystatus="";
       if("".equals(profilekeystatus)) profilekeystatus="N";
       return profilekeystatus;
    }
    
    public static void deleteRSVPAttendeeInfo(String profid,String eid,String ticketid,String tid){
       System.out.println("deleteattendeeinfo for RSVP event:"+tid+":"+eid);
       String actualreducedqty="1",attendeetype="",notsurecount="",yescount=""; 
       DBManager dbm=new DBManager();
       StatusObj statobj=null;
       
       DbUtil.executeUpdateQuery("update transaction_tickets set ticketqty=ticketqty-"+actualreducedqty+" where tid=? and eventid=? and ticketid=to_number(?,'9999999999')", new String[]{tid,eid,ticketid});
       DbUtil.executeUpdateQuery("delete from profile_base_info where profilekey=? and transactionid=?",new String[]{profid,tid});
       
       attendeetype=DbUtil.getVal("select ticketname from transaction_tickets where tid=? and eventid=? and ticketid=to_number(?,'999999999')", new String[]{tid,eid,ticketid});
       if("Maybe".equalsIgnoreCase(attendeetype) || "Not Sure".equalsIgnoreCase(attendeetype))
    	   DbUtil.executeUpdateQuery("update rsvp_transactions set notsurecount=notsurecount-"+actualreducedqty+" where tid=? and eventid=?", new String[]{tid,eid});
       else if("Attending".equalsIgnoreCase(attendeetype))
    	   DbUtil.executeUpdateQuery("update rsvp_transactions set yescount=yescount-"+actualreducedqty+" where tid=? and eventid=?", new String[]{tid,eid});
       
       statobj=dbm.executeSelectQuery("select notsurecount,yescount from rsvp_transactions where tid=? and eventid=?",new String[]{tid,eid});
       if(statobj.getStatus()){
    	   for(int i=0;i<statobj.getCount();i++){
    		   notsurecount=dbm.getValue(i,"notsurecount","");
    		   yescount=dbm.getValue(i, "yescount","");
    	   }
       }
       if(!"".equals(notsurecount) && !"".equals(yescount)){
    	   if(Integer.parseInt(notsurecount)==0 && Integer.parseInt(yescount)==0)
    		   DbUtil.executeUpdateQuery("update rsvp_transactions set responsetype='N' where tid=? and eventid=?", new String[]{tid,eid});
    	   
       }
    	   
    }
    
    public static String getOrderNumTransactionID(String eventid,String ordernumber){
    	String tid=DbUtil.getVal("select tid from event_reg_transactions where eventid=? and ordernumber=?", new String[]{eventid,ordernumber});
    	return tid;
    }
    
   public static ArrayList<HashMap<String,String>> getAttendeeTranInfo(String eventid,String name){
	   System.out.println("insertAttendeeTrack name: "+name+" eventid: "+eventid);
    	ArrayList<HashMap<String,String>> atttraninfolist=new ArrayList<HashMap<String,String>>();
    	DBManager dbm=new DBManager();
    	StatusObj statobj=null;
    	String reportquery="select fname || ' ' || lname as name,transactionid,to_char(created_at,'mm/dd/yyyy')  as trandate from profile_base_info "+

    		               " where eventid=CAST(? as bigint) " +
    		               " and transactionid in(select tid from event_reg_transactions where eventid=? and (UPPER(paymentstatus) in ('COMPLETED','CHARGED','PENDING') ))" +
    		               " and lower(fname || lname) like lower(?)";
    	statobj=dbm.executeSelectQuery(reportquery, new String[]{eventid,eventid,"%"+name+"%"});
    	if(statobj.getStatus() && statobj.getCount()>0){
    		for(int i=0;i<statobj.getCount();i++){
    			HashMap<String,String> hmap=new HashMap<String,String>();
    			hmap.put("Attendee Name",dbm.getValue(i,"name",""));
    			hmap.put("Transaction ID", dbm.getValue(i,"transactionid", ""));
    			hmap.put("Transaction Date",dbm.getValue(i,"trandate", ""));
    			atttraninfolist.add(hmap);
    		}
    	}
    	return atttraninfolist;
    }
   
   
   public static ArrayList<HashMap<String,String>> getAttendeeTranInfoByTid(String eventid,String tid){
	  
   	ArrayList<HashMap<String,String>> atttraninfolist=new ArrayList<HashMap<String,String>>();
   	DBManager dbm=new DBManager();
   	StatusObj statobj=null;
   	String reportquery="select p.fname || ' ' || p.lname as name,transactionid,to_char(created_at,'mm/dd/yyyy')  as trandate from profile_base_info p,event_reg_transactions e "+
   		               " where p.eventid=cast(e.eventid as bigint) and p.transactionid=e.tid and p.eventid=CAST(? as integer) and e.tid=?";
   	statobj=dbm.executeSelectQuery(reportquery, new String[]{eventid,tid});
   	if(statobj.getStatus() && statobj.getCount()>0){
   		for(int i=0;i<statobj.getCount();i++){
   			HashMap<String,String> hmap=new HashMap<String,String>();
   			hmap.put("Attendee Name",dbm.getValue(i,"name",""));
   			hmap.put("Transaction ID", dbm.getValue(i,"transactionid", ""));
   			hmap.put("Transaction Date",dbm.getValue(i,"trandate", ""));
   			atttraninfolist.add(hmap);
   		}
   	}
   	return atttraninfolist;
   }
   
   
    
    public static String getAttendeeTranJsonData(ArrayList<HashMap<String,String>> attendeestransactions,ArrayList Fields){
		String msg="";
		JSONObject json=new JSONObject();
		try{
			json.put("fields", Fields);
			json.put("AttendeeTranReport",attendeestransactions);
		    msg=json.toString();
		}catch(Exception e){
			System.out.println("Exception occured at prepare json in getAttendeeTranJsonData"+e.getMessage());
		}
		return msg;
	}
    
    public static ArrayList<HashMap<String,String>> getTransactionInfoByDates(String startdate,String enddate,String eventid){
    	ArrayList<HashMap<String,String>> traninfobydates=new ArrayList<HashMap<String,String>>();
    	DBManager dbm=new DBManager();
    	StatusObj statobj=null;
    	String query="select to_char(transaction_date,'mm/dd/yyyy') as trandate,tid,p.fname || ' ' || p.lname as name from event_reg_transactions et,profile_base_info p"
    		         +" where cast(et.eventid as bigint)=p.eventid and et.eventid=? and et.tid=p.transactionid and et.transaction_date "
    		         +" between  to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') and (UPPER(et.paymentstatus) in ('COMPLETED','CHARGED','PENDING') ) ";
    	statobj=dbm.executeSelectQuery(query,new String[]{eventid,startdate,enddate});
    	if(statobj.getStatus() && statobj.getCount()>0){
    		for(int i=0;i<statobj.getCount();i++){
    			HashMap<String,String> hmap=new HashMap<String,String>();
    			hmap.put("Transaction Date",dbm.getValue(i,"trandate",""));
    			hmap.put("Transaction ID", dbm.getValue(i,"tid",""));
    			hmap.put("Attendee Name", dbm.getValue(i,"name",""));
    			traninfobydates.add(hmap);
    		}
    	}
    	return traninfobydates;
    }
    
    public static ArrayList<HashMap<String,String>> getTransactionInfoByEmail(String eventid,String emailid){
    	ArrayList<HashMap<String,String>> traninfobyemail=new ArrayList<HashMap<String,String>>();
    	DBManager dbm=new DBManager();
    	StatusObj statobj=null;
    	String query="select fname || ' ' || lname as name,transactionid,to_char(created_at,'mm/dd/yyyy')  as trandate from profile_base_info "+

    		         " where eventid=CAST(? as bigint) " +
    		         " and transactionid in(select tid from event_reg_transactions where eventid=? and (UPPER(paymentstatus) in ('COMPLETED','CHARGED','PENDING') ))" +
    		         " and lower(email)=lower(?) ";
    	statobj=dbm.executeSelectQuery(query,new String[]{eventid,eventid,emailid});
    	if(statobj.getStatus() && statobj.getCount()>0){
    		for(int i=0;i<statobj.getCount();i++){
    			HashMap<String,String> hmap=new HashMap<String,String>();
    			hmap.put("Transaction Date",dbm.getValue(i,"trandate",""));
    			hmap.put("Transaction ID", dbm.getValue(i,"transactionid",""));
    			hmap.put("Attendee Name", dbm.getValue(i,"name",""));
    			traninfobyemail.add(hmap);
    		}
    	}
    	return traninfobyemail;
    }

    public static String getOriginalAmount(String tid, String eid){
    	String originalAmt=DbUtil.getVal("select original_amount from event_reg_transactions where tid=? and eventid=?", new String []{tid, eid});
    	return originalAmt;
    }

    public static String changeCheckInStatus(String eid,String tid,String pkey, String checkInStatus){
 	   
 	   if("No".equalsIgnoreCase(checkInStatus)){
 		   String scanid="";
 		   String checkedby="mgr";
 		   Map session = ActionContext.getContext().getSession();
 		   if(session.get("SESSION_SUBMGR")!= null){
 			   SubManagerData smd=(SubManagerData)session.get("SESSION_SUBMGR");
 			   String sub_mgr_id=smd.getSubMgrId();
 			   scanid=smd.getEmail();
 			   checkedby="scanid";
 			   System.out.println("changeCheckInStatus sub_mgr_id: "+sub_mgr_id+" email: "+scanid);
 		   }
 		   
 		   String INSERT_SCAN_STATUS="insert into event_scan_status (tid , attendeekey, checkinstatus, " +
 					" checkedintime, checkedby, eventid, ticketid, syncedby, scanid) values(?,?,1,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),?,?,CAST(? AS INTEGER),'EB',?)";
 					
 		   String UPDATE_SCAN_INFO="update profile_base_info set checkintime =to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'), " +
 					" scanid=? where eventid=cast(? as bigint) and transactionid=? and profilekey=?";
 		   
 		   String ticketId=DbUtil.getVal("select ticketid from profile_base_info where eventid=cast(? as bigint) and transactionid=? and profilekey=? ", new String[]{eid,tid,pkey});
 		   
 		   DbUtil.executeUpdateQuery(INSERT_SCAN_STATUS,new String[]{tid,pkey,DateUtil.getCurrDBFormatDate(),checkedby,eid,ticketId,scanid});
 		   
 		   DbUtil.executeUpdateQuery(UPDATE_SCAN_INFO,new String[]{DateUtil.getCurrDBFormatDate(),scanid,eid,tid,pkey});
 		   
 	   }else{
 		   String delquery="delete from event_scan_status where eventid=? and tid=? and attendeekey=?";
 		   DbUtil.executeUpdateQuery(delquery,new String[]{eid,tid,pkey});
 		   String makenull="update profile_base_info set checkintime=NULL,scanid=NULL where eventid=cast(? as bigint) and transactionid=? and profilekey=?";
 		   DbUtil.executeUpdateQuery(makenull,new String[]{eid,tid,pkey});
 	   }
    return "true";
    }
   
   public static ArrayList getEventDates(String eid,String tid){
		DBManager db=new DBManager();
		ArrayList eventDatesList=new ArrayList();
		StatusObj statobj=null;
		String eventtype="";
		String rsvpeventtype=DbUtil.getVal("select 'y' from config where config_id=(select config_id from eventinfo"+
				" where eventid=CAST(? AS BIGINT)) and name='event.rsvp.enabled'", new String[]{eid});
		if(rsvpeventtype==null)rsvpeventtype="";
		if("".equals(rsvpeventtype))
			eventtype="Ticketing";
		else
			eventtype="RSVP";
		
		String ticketingquery= " select to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00')," +
	                  " 'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY HH12:MI AM') as evt_start_date " +
                     " from event_dates where eventid=CAST(? AS BIGINT) and " +
                     " to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00')," +
	                  " 'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY HH12:MI AM')" +
	                  " not in( select eventdate  from "+
                     " event_reg_transactions where eventid=? and tid=?) order by date_key ";
		
		
		String rsvpquery= " select date_display  from event_dates where eventid=CAST(? AS BIGINT) order by date_key ";
		if("RSVP".equals(eventtype))
		    statobj=db.executeSelectQuery(rsvpquery,new String[]{eid});
		else if("Ticketing".equals(eventtype))
			statobj=db.executeSelectQuery(ticketingquery,new String[]{eid,eid,tid});	
		//System.out.println("status in geteventdates"+statobj.getStatus());
		int count=statobj.getCount();
		if(statobj.getStatus() && count>0){
         for(int i=0;i<count;i++){
       	  if("RSVP".equals(eventtype))
       		  eventDatesList.add(db.getValue(i,"date_display",""));
       	  else if("Ticketing".equals(eventtype)) 
       		  eventDatesList.add(new Entity(db.getValue(i,"evt_start_date",""),db.getValue(i,"evt_start_date","")));
         }
		}
		
		return eventDatesList;
	}
   
   
    
}


