package com.eventregister.dbhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EventbeeLogger;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;

public class RegistrationDBHelper {
	final String EVENT_REG_DETAILS="select * from event_reg_details_temp where tid=? and eventid=?";
	final String BUYER_DETAILS_QUERY="select * from buyer_base_info where transactionid=?";
	final String EVENT_REG_TICKET_DETAIL="select *,,(finalprice+finalfee)*qty as total from event_reg_ticket_details_temp where tid=?";
	final String MEMBER_SEQ_ID="select nextval('seq_maillist') as memberid" ;
	final String INSERT_MEMBER="insert into member_profile(member_id,m_lastname,created_at,m_email,m_firstname,manager_id,m_email_type) values(to_number(?,'9999999999'),?,now(),?,?,to_number(?,'9999999999'),?)"	;
	final String INSERT_LIST_MEMBER="insert into mail_list_members(member_id,list_id,status,created_at,created_by) values(to_number(?,'9999999999'),to_number(?,'9999999999'),'available',now(),'Auto Subscription')";
	final String PROFILE_INFO="select * from profile_base_info where transactionid =? and tickettype='attendeeType'";
	final String ATTENDEE_QUERY="select * from profile_base_info where transactionid=?";
	String serveraddress="http://"+EbeeConstantsF.get("serveraddress","");
	public HashMap getRegistrationData(String tid,String eid){
	DBManager dbmanager=new DBManager();
	HashMap hmap=new HashMap();
	StatusObj statobj=dbmanager.executeSelectQuery(EVENT_REG_DETAILS,new String[]{tid,eid});
	try{
	if(statobj.getStatus()){
	String [] columnnames=dbmanager.getColumnNames();
	for(int j=0;j<columnnames.length;j++){
	hmap.put(columnnames[j],dbmanager.getValue(0,columnnames[j],""));
	}
	}
	}
	catch(Exception e){
	System.out.println("Exception in getRegistrationData"+e.getMessage());
	}
	return hmap;
	}
	public HashMap getBuyerInfo(String tid,String eid){
	HashMap hm=new HashMap();
	try{
	DBManager db=new DBManager();
	StatusObj sb=db.executeSelectQuery(BUYER_DETAILS_QUERY,new String[]{tid});
	if(sb.getStatus()){
	hm.put("firstName",db.getValue(0,"fname",""));
	hm.put("lastName",db.getValue(0,"lname",""));
	hm.put("email",db.getValue(0,"email",""));
	hm.put("phone",db.getValue(0,"phone",""));
	}
	}
	catch(Exception e){
	System.out.println("Exception in getting buyerInfo"+e.getMessage());
	}
	return hm;
	}

	public ArrayList getpurchasedTickets(String tid){
		HashMap <String,ArrayList>attendeeDetails=getAttendeeDetails(tid);
		ArrayList ticketlist=new ArrayList();
		try{
		String attendeeQuery="select *,(finalprice+finalfee)*qty as total from event_reg_ticket_details_temp where tid=?";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(attendeeQuery,new String[]{tid});
		if(sb.getStatus()){
		for(int k=0;k<sb.getCount();k++){
		HashMap hm=new HashMap();
		hm.put("ticketId",db.getValue(k,"ticketid",""));
		hm.put("ticketName",db.getValue(k,"ticketname",""));
		hm.put("ticketPrice",db.getValue(k,"originalprice",""));
		hm.put("discount",db.getValue(k,"discount",""));
		hm.put("ticketQuantity",db.getValue(k,"qty",""));
		hm.put("processingFee",db.getValue(k,"finalfee",""));
		hm.put("totalAmount",db.getValue(k,"total",""));
		hm.put("ticketType",db.getValue(k,"tickettype",""));
		if(attendeeDetails.containsKey(db.getValue(k,"ticketid",""))){
		hm.put("profiles",attendeeDetails.get(db.getValue(k,"ticketid","")));
		}
		ticketlist.add(hm);
		}
		}
		}
		catch(Exception e){
		System.out.println("Exception in getting getpurchasedTickets"+e.getMessage());
		}
		return ticketlist;
	}

	public void InserMailingList(String eventid,HashMap hmap){
	try{
	String memberid=DbUtil.getVal(MEMBER_SEQ_ID,new String[]{});
	String listid=DbUtil.getVal("select value from config where name='event.maillist.id' and config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT))", new String []{eventid});
	StatusObj status1=DbUtil.executeUpdateQuery(INSERT_MEMBER,new String [] {memberid,(String)hmap.get("lname"),(String)hmap.get("email"),(String)hmap.get("fname"),"0","html"});
	StatusObj status2=DbUtil.executeUpdateQuery(INSERT_LIST_MEMBER,new String [] {memberid,listid});
	}
	catch(Exception e){
	System.out.println("Exception occured in InserMailingList"+e.getMessage());
	}
	}


	public ArrayList getProfileInfo(String tid,String eid){
	ArrayList attendeeList=new ArrayList();
	DBManager db=new DBManager();
	try{
	StatusObj sb=db.executeSelectQuery(PROFILE_INFO,new String []{tid});
	if(sb.getStatus()){
	for(int i=0;i<sb.getCount();i++){
	HashMap attendeeMap=new HashMap();
	attendeeMap.put("fname",db.getValue(i,"fname",""));
	attendeeMap.put("lname",db.getValue(i,"lname",""));
	attendeeMap.put("email",db.getValue(i,"email",""));
	attendeeList.add(attendeeMap);
	}
	}
	}
	catch(Exception e){
	System.out.println("Exception occured in getProfileInfo"+e.getMessage());
	}
	return attendeeList;
	}

	HashMap<String,ArrayList>getAttendeeDetails(String tid){
		HashMap <String,ArrayList>hm=new HashMap<String,ArrayList>();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(ATTENDEE_QUERY,new String[]{tid});
		if(sb.getStatus()){
		ArrayList attndeelist=null;
		for(int k=0;k<sb.getCount();k++){
		HashMap hmap=new HashMap();
		hmap.put("profilekey",db.getValue(k,"profilekey",""));
		hmap.put("fname",db.getValue(k,"fname",""));
		hmap.put("lname",db.getValue(k,"lname",""));
		hmap.put("seatcode",db.getValue(k, "seatcode", ""));
		System.out.println("seat code in get attendee profiles: "+db.getValue(k, "seatcode", ""));
		String name=db.getValue(k,"fname","")+" "+db.getValue(k,"lname","");
		hmap.put("name",name);
		if(hm.containsKey(db.getValue(k,"ticketid",""))){
		attndeelist=hm.get(db.getValue(k,"ticketid",""));
		attndeelist.add(hmap);
		}
		else{
		attndeelist=new ArrayList();
		attndeelist.add(hmap);
		}
		hm.put(db.getValue(k,"ticketid",""),attndeelist);
		}
		}
		return hm;
		}
	
	
	String  getExternalPayId(HashMap eventRegData){
		String extpayid="";
		String paytype=(String)eventRegData.get("selectedpaytype");
		String tid=(String)eventRegData.get("tid");
		if("eventbee".equals(paytype))
		extpayid=DbUtil.getVal("select response_id  from cardtransaction where internal_ref=?",new String[]{tid});
		else if("paypal".equals(paytype))
		extpayid=DbUtil.getVal("select paypal_tran_id   from paypal_payment_backup_data where ebee_tran_id=?",new String[]{tid});
		else if("google".equals(paytype))
		extpayid=DbUtil.getVal("select google_order_no   from google_payment_data where ebee_tran_id=?",new String[]{tid});
		else
		extpayid="";
		return extpayid;
		}
	
	public ArrayList getConfirmationTicketDetails(String tid,String eid){
		ArrayList al=new ArrayList();
		String ordernumber="";
		String extpayid="";
		double tickettotamount=0;
		HashMap <String,String>regDetails=getTransactionDetails(tid);
		if(regDetails!=null&&regDetails.size()>0){
		ordernumber=regDetails.get("ordernumber");
		}

		HashMap <String,String>attendeemap=null;
		JSONObject jobj=null;
		String objstring=null;
		
		HashMap customquestions=new HashMap();
		String showcustomquestions=DbUtil.getVal("select value from config where config_id =(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.confirmationemail.questions.show'", new String[]{eid});
		if("Y".equals(showcustomquestions))
			customquestions=getCustomQuestions(eid,tid);
		
		ArrayList purchasedTickets=getpurchasedTickets(tid);

		if(purchasedTickets!=null){
		try{
		for(int i=0;i<purchasedTickets.size();i++){
		HashMap hm=(HashMap)purchasedTickets.get(i);
		String qty=(String)hm.get("ticketQuantity");
		String tkttype=(String)hm.get("ticketType");
		String total=(String)hm.get("totalAmount");
		ArrayList profile=(ArrayList)hm.get("profiles");
		if("attendeeType".equals(tkttype)){
		for(int p=0;p<profile.size();p++){
		jobj=new JSONObject();
		jobj.put("eid",eid);
		jobj.put("xid",tid);
		jobj.put("on","1");
		jobj.put("tn",(String)hm.get("ticketName"));
		jobj.put("tId",(String)hm.get("ticketId"));
		attendeemap=(HashMap)profile.get(p);
		jobj.put("pkey",attendeemap.get("profilekey"));
		jobj.put("fn",attendeemap.get("fname"));
		jobj.put("ln",attendeemap.get("lname"));
		
		objstring=jobj.toString();
		HashMap gm=new HashMap();
		gm.putAll(hm);
		gm.put("ticketQuantity","1");
		if("Y".equals(showcustomquestions)){
			String pkey=(String)attendeemap.get("profilekey");
			String custquestion=getCustQuestionsResponse(pkey, customquestions);
			if(!"".equals("custquestion"))gm.put("customQuestion", custquestion);
		}
		try{
		 tickettotamount=Double.parseDouble(total)/Integer.parseInt(qty);
		}
		catch(Exception e){
			tickettotamount=0;	
		}
		gm.put("totalAmount",CurrencyFormat.getCurrencyFormat("",tickettotamount+"",true));
		gm.put("attendeeName",attendeemap.get("name"));
		String seatcode=attendeemap.get("seatcode");
		
		if("".equals(seatcode) || seatcode==null)
		{}
		else{
			gm.put("seatCodes",attendeemap.get("seatcode"));
		}
		//gm.put("qrcode","<img src='http://chart.apis.google.com/chart?chld=L|0&cht=qr&chs=100x100&chl="+java.net.URLEncoder.encode(objstring)+"'/>");
		HashMap qr_barcodemsg=getQrCodeBarCode(eid);
		String qrcode="",vbarcode="",hbarcode="";
		qrcode=(String)qr_barcodemsg.get("qrcode");
		vbarcode=(String)qr_barcodemsg.get("vbarcode");
		hbarcode=(String)qr_barcodemsg.get("hbarcode");
		
		try{
			String qr[]=qrcode.split("#msg");
			qrcode=qr[0]+java.net.URLEncoder.encode(objstring)+qr[1];
			String vbar[]=vbarcode.split("#msg");
			vbarcode=vbar[0]+jobj.get("pkey")+vbar[1];
			String hbar[]=hbarcode.split("#msg");
			hbarcode=hbar[0]+jobj.get("pkey")+hbar[1];
			String vserver[]=vbarcode.split("#serveraddress");
			vbarcode=vserver[0]+serveraddress+vserver[1];
			String hserver[]=hbarcode.split("#serveraddress");
			hbarcode=hserver[0]+serveraddress+hserver[1];
			//vbarcode.replaceAll("#msg",(String) jobj.get("pkey"));
			//vbarcode.replaceAll("#serveraddress",serveraddress);
			//hbarcode.replaceAll("#msg",(String) jobj.get("pkey"));
			//hbarcode.replaceAll("#serveraddress",serveraddress);
			//qrcode.replaceAll("#msg", java.net.URLEncoder.encode(objstring));
		}catch(Exception e){System.out.println("exception in try:"+e.getMessage());}
		
		
		gm.put("qrcode", qrcode);
		gm.put("barcode", hbarcode);
		gm.put("vBarcode", vbarcode);
		al.add(gm);
		}
		}
		else{
			String ticketid=(String) hm.get("ticketId");
			ArrayList seatcode=new ArrayList();
			for(int p=0;p<profile.size();p++){
				attendeemap=(HashMap)profile.get(p);
				if("Y".equals(showcustomquestions)){
					String pkey=(String)attendeemap.get("profilekey");
					String custquestion=getCustQuestionsResponse(pkey, customquestions);
					if(!"".equals("custquestion"))hm.put("customQuestion", custquestion);
				}
				if(!"".equals((String)attendeemap.get("seatcode"))){
					seatcode.add(attendeemap.get("seatcode"));			
				}
			}
			if(!seatcode.isEmpty()||seatcode.size()>0)
				hm.put("seatCodes",seatcode);
		al.add(hm);
		}
		}
		}
		catch(Exception e){
		System.out.println("Exception occured is"+e.getMessage());
		}
		}
		return al;
		}
		public HashMap getQrCodeBarCode(String eid) {
	HashMap codemap=new HashMap();
	String code_query="select * from barcode_qrcode_settings where eventid=?";
	DBManager db=new DBManager();
	StatusObj code_sb=db.executeSelectQuery(code_query, new String[]{eid});
	if(code_sb.getStatus()){
		codemap.put("qrcode",db.getValue(0, "qrcode", ""));
		codemap.put("hbarcode",db.getValue(0, "hbarcode", ""));
		codemap.put("vbarcode",db.getValue(0, "vbarcode", ""));
		
	}
	else{
		code_sb=db.executeSelectQuery(code_query, new String[]{"0"});
		codemap.put("qrcode",db.getValue(0, "qrcode", ""));
		codemap.put("hbarcode",db.getValue(0, "hbarcode", ""));
		codemap.put("vbarcode",db.getValue(0, "vbarcode", ""));
		
	}
	return codemap;
}
		public HashMap <String,String>getTransactionDetails(String tid){
		HashMap <String,String>transactionMap=new HashMap<String,String>();
		try{
		String  TRANSACTION_DETAILS="select current_discount,current_amount,original_tax,ordernumber,eventdate,paymenttype,ext_pay_id,bookingdomain,paymentstatus from event_reg_transactions where tid=?";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(TRANSACTION_DETAILS,new String []{tid});
		if(sb.getStatus()){
		transactionMap.put("grandtotal",db.getValue(0,"current_amount",""));
		transactionMap.put("tax",db.getValue(0,"original_tax",""));
		transactionMap.put("ordernumber",db.getValue(0,"ordernumber",""));
		transactionMap.put("eventdate",db.getValue(0,"eventdate"," "));
		transactionMap.put("discount",db.getValue(0,"current_discount"," "));
		transactionMap.put("paymenttype",db.getValue(0,"paymenttype"," "));
		transactionMap.put("extpayid",db.getValue(0,"ext_pay_id"," "));
		transactionMap.put("bookingdomain",db.getValue(0,"bookingdomain"," "));
		transactionMap.put("paymentstatus",db.getValue(0,"paymentstatus"," "));
		}
		}
		catch(Exception e){
		System.out.println("Exception in getTransactionDetails"+e.getMessage());
		}
		return transactionMap;
		}
		public void insertSeatBookingStatus(HashMap seatinghm) {
			try{
			String seatbookingquery="insert into seat_booking_status (eventid,venue_id,section_id,seatindex,eventdate,bookingtime,ticketid,profilekey,tid) values"+
			"(CAST(? AS BIGINT),to_number(?,'999999999'),to_number(?,'999999999'),?,?,now(),to_number(?,'999999999'),?,?)";
			StatusObj seatsb=DbUtil.executeUpdateQuery(seatbookingquery,new String[]{(String)seatinghm.get("eventid"),(String)seatinghm.get("venue_id"),(String)seatinghm.get("section_id"),(String)seatinghm.get("seatindex"),(String)seatinghm.get("eventdate"),(String)seatinghm.get("ticketid"),(String)seatinghm.get("profilekey"),(String)seatinghm.get("tid")});
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO, "ProfileActionDB.java", "Status Of the inserting updateBaseProfile into seating booking status for the transactionid---->"+seatinghm.get("tid"), ""+seatsb.getStatus(), null );
			}catch(Exception e){
				System.out.println("exception in insertseatbookingstatus:"+e.getMessage());
			
			}
		}
		public ArrayList getallProfileInfo(String tid, String eid) {
			ArrayList attendeeList=new ArrayList();
			String PROFILE_INFO_query="select * from profile_base_info where transactionid =? and eventid=CAST(? AS BIGINT)";
			DBManager db=new DBManager();
			try{
			StatusObj sb=db.executeSelectQuery(PROFILE_INFO_query,new String []{tid,eid});
			if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
			HashMap attendeeMap=new HashMap();
			attendeeMap.put("fname",db.getValue(i,"fname",""));
			attendeeMap.put("lname",db.getValue(i,"lname",""));
			attendeeMap.put("email",db.getValue(i,"email",""));
			attendeeMap.put("ticketid",db.getValue(i,"ticketid",""));
			attendeeMap.put("profilekey",db.getValue(i,"profilekey",""));
			attendeeMap.put("tickettype",db.getValue(i,"tickettype",""));
			attendeeMap.put("seatCodes", db.getValueFromRecord(i, "seatcode", ""));
			attendeeList.add(attendeeMap);
			}
			}
			}
			catch(Exception e){
			System.out.println("Exception occured in getProfileInfo"+e.getMessage());
			}
			return attendeeList;
		}
		
		public HashMap getCustomQuestions(String eid, String tid) {
			HashMap hm=new HashMap();
			try{
				/*String query="select question_original as attrib_name,bigresponse  as response, c.profilekey "
							+"from  custom_questions_response a, custom_questions_response_master b,profile_base_info c,confirmationscreen_questions d "
							+"where a.ref_id=b.ref_id "
							+"and c.profilekey=b.profilekey "
							+"and c.eventid=to_number(?,'999999999') "
							+"and d.eventid=cast(c.eventid as varchar) "
							+"and c.transactionid=? "
							+"and a.attribid=d.attrib_id "
							+"order by d.position";*/
				
				String query="select e.attribname as attrib_name,a.bigresponse  as response, c.profilekey " +
						"from custom_questions_response a, custom_questions_response_master b,profile_base_info c, " +
						"confirmationscreen_questions d,custom_attribs e,custom_attrib_master f " +
						"where a.ref_id=b.ref_id and c.profilekey=b.profilekey and c.eventid=f.groupid " +
						"and d.eventid=cast(c.eventid as varchar) and a.attribid=d.attrib_id " +
						"and f.groupid=CAST(? AS BIGINT) and c.transactionid=? and f.attrib_setid=e.attrib_setid and e.attrib_id=a.attribid " +
                        "and d.type='confirmation_email' order by d.position";
				DBManager db=new DBManager();
				StatusObj sb=db.executeSelectQuery(query,new String[]{eid,tid});
				HashMap<String,String> basicAttribs = getBasicAtrribNames(eid);
				if(sb.getStatus()){
					for(int i=0;i<sb.getCount();i++){
						String pkey=db.getValue(i, "profilekey", "");
						if(hm.containsKey(pkey)){
							ArrayList<Entity> temp=(ArrayList)hm.get(pkey);
							temp.add(new Entity(db.getValue(i,"attrib_name",""),db.getValue(i, "response", "")));
							hm.put(pkey, temp);
						}
						else{
							ArrayList<Entity> temp=new ArrayList<Entity>();
							temp.add(new Entity(db.getValue(i,"attrib_name",""),db.getValue(i, "response", "")));
							hm.put(pkey, temp);
						}
					}
				}
				HashMap<String,HashMap<String,String>> basicResponses=null;
				if(basicAttribs.containsKey("-1") || basicAttribs.containsKey("-2"))
				{
					basicResponses=getBasicResponses(eid, tid);
					if(basicResponses!=null){
						Iterator it = basicResponses.entrySet().iterator();
					    while (it.hasNext()) {
					        Map.Entry pairs = (Map.Entry)it.next();
					        String key=(String)pairs.getKey();
							ArrayList<Entity> ent=(ArrayList<Entity>)hm.get(key);
							if(ent==null) ent=new ArrayList<Entity>();
							if(!"".equals(basicResponses.get(key).get("phone")) && basicAttribs.get("-2")!=null)
							{
								ent.add(0, new Entity("Phone",basicResponses.get(key).get("phone")));
							}
							if(!"".equals(basicResponses.get(key).get("email")) && basicAttribs.get("-1")!=null)
							{
								ent.add(0, new Entity("Email",basicResponses.get(key).get("email")));
							}
							hm.put(key, ent);
						}
					    }
				}
			}
			catch(Exception e){
				System.out.println("Exception occured in getting custom questions:"+e.getMessage());
			}
			return hm;
		}
		
		public static HashMap<String,HashMap<String,String>> getBasicResponses(String eid,String tid){
			HashMap<String,HashMap<String,String>> basicResponse=new HashMap<String,HashMap<String,String>>();
			//String query="select profilekey,email,phone from profile_base_info where transactionid=? and eventid =?::bigint";
			String query="select profilekey,email,phone,attribid from profile_base_info p,base_profile_questions b "+
			             " where transactionid=? and p.ticketid=b.contextid and eventid =?::bigint and  attribid in('email','phone')";
			StatusObj statobj=null;
			DBManager dbmanager=new DBManager();
			statobj=dbmanager.executeSelectQuery(query,new String []{tid,eid});
			if(statobj.getStatus() && statobj.getCount()>0){
			for(int k=0;k<statobj.getCount();k++){
				HashMap<String,String> hm=new HashMap<String,String>();
				if(basicResponse.get(dbmanager.getValue(k,"profilekey",""))!=null)
					hm=basicResponse.get(dbmanager.getValue(k,"profilekey",""));
				if("email".equals(dbmanager.getValue(k,"attribid","")))
				    hm.put("email",dbmanager.getValue(k,"email",""));
				if("phone".equals(dbmanager.getValue(k,"attribid","")))
				    hm.put("phone",dbmanager.getValue(k,"phone",""));
				basicResponse.put(dbmanager.getValue(k,"profilekey",""),hm);
			}
			}
			return basicResponse;
		}

		public HashMap<String,String> getBasicAtrribNames(String eid){
			HashMap<String,String> attribsMap=new HashMap();
			String query="select attrib_id,attrib_name as attribname from confirmationscreen_questions where eventid=? and attrib_id in (-1,-2) and type='confirmation_email'";
			DBManager db=new DBManager();
			StatusObj sb=db.executeSelectQuery(query, new String[]{eid});
			if(sb.getStatus()){
				for(int i=0;i<sb.getCount();i++){
					attribsMap.put(db.getValue(i, "attrib_id",""), db.getValue(i, "attribname",""));
				}
			}
			return attribsMap;
		}
		
		public String getCustQuestionsResponse(String pkey,HashMap customquestions){
			String allcustquestions="";
		try{
			if(customquestions.containsKey(pkey)){
				allcustquestions="<table>";
				ArrayList<Entity> custquestion=(ArrayList)customquestions.get(pkey);
				for(int a=0;a<custquestion.size();a++){
					Entity en=custquestion.get(a);
					
					if(!"".equals(en.getValue()) && en.getValue()!="" && en.getValue()!=null)
						allcustquestions=allcustquestions+"<tr><td><b>"+en.getKey()+":</b>&nbsp;"+en.getValue()+"</td></tr>";
				}
				allcustquestions=allcustquestions+"</table>";
				
			}
		}catch(Exception e){System.out.println("Exception in customquests generation is"+e.getMessage());}
		return allcustquestions;
		
	}
		
	public String getTransactionSeatcodes(String tid){
		String query="select seatcode from profile_base_info where transactionid=? and seatcode is not null and seatcode!=''";
		String seatcodes="";
		DBManager db=new DBManager();
		StatusObj sob=db.executeSelectQuery(query, new String[]{tid});
		if(sob.getStatus() && sob.getCount()>0){
			for(int i=0;i<sob.getCount();i++){
				if(i==0) seatcodes+=db.getValue(i, "seatcode","");
				else seatcodes+=", "+db.getValue(i, "seatcode",""); 
			}
		}else seatcodes=null;
		return seatcodes;
	}
}
