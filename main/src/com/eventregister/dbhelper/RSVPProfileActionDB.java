package com.eventregister.dbhelper;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONObject;

import com.event.dbhelpers.EventInfoDB;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailTemplate;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventregister.TicketsDB;

public class RSVPProfileActionDB {
	final static String GET_TRANSACTION_ID_QUERY = "select nextval('seq_transactionid') as transactionid";
	static String  serveraddress="http://"+EbeeConstantsF.get("serveraddress","");
	public static String getTransactionId(String pattern){
	 	String transid=DbUtil.getVal(GET_TRANSACTION_ID_QUERY,new String[]{});
		String transactionid=pattern+EncodeNum.encodeNum(transid).toUpperCase();
		return transactionid;
		}
	
	public static void FillTransactionLevel(HashMap attendeeMap){
		String eventdate=(String)attendeeMap.get("eventdate");
		String transid=(String)attendeeMap.get("tid");
		String trackcode="";
		String event_closed_date="";
		double servicefee=0,surecount=0,servicefee_USD=0;
		if(eventdate!=null&&!"".equals(eventdate)){
			String close_datequery="select est_enddate from event_dates where  date_display=?   and eventid=CAST(? AS BIGINT)";
			event_closed_date=DbUtil.getVal(close_datequery, new String[]{eventdate,(String)attendeeMap.get("eventid")});
		}
		else{
			String close_datequery="select end_date from eventinfo where eventid=CAST(? AS BIGINT)";
			event_closed_date=DbUtil.getVal(close_datequery, new String[]{(String)attendeeMap.get("eventid")});
		}
		String sure=(String)attendeeMap.get("sure");
		RegistrationTiketingManager regtktmgr=new RegistrationTiketingManager();
		HashMap<String,String> ebeefeeMap=regtktmgr.getEbeeFee((String)attendeeMap.get("eventid"));
		String ebeeFee=ebeefeeMap.get("finalebeefee");
		String ebeeFee_USD=ebeefeeMap.get("ebeefee_usd");
		try{
			surecount=Double.parseDouble(sure);
		}
		catch(Exception e){
			surecount=0;
		}
		servicefee=(Double.parseDouble(ebeeFee)*surecount);
		servicefee_USD=(surecount)*(Double.parseDouble(ebeeFee_USD));
		String mgrId=DbUtil.getVal("select mgr_id from eventinfo where eventid=?::integer", new String[]{(String)attendeeMap.get("eventid")});
		Double amount_we_have=0.00-servicefee;
		String query="insert into event_reg_transactions(tid,bookingsource,paymentstatus,transaction_date,paymenttype,fname,lname,email,phone,eventid,bookingdomain,eventdate,trackpartner,event_closed_date,servicefee) values(?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),?,?,?,?,?,?,?,?,?,?,CAST(? AS NUMERIC))";
		DbUtil.executeUpdateQuery(query,new String[]{transid,"Manager","Completed",DateUtil.getCurrDBFormatDate(),"RSVP",(String)attendeeMap.get("fname"),(String)attendeeMap.get("lname"),(String)attendeeMap.get("email"),"",(String)attendeeMap.get("eventid"),"EB",eventdate,trackcode,event_closed_date,servicefee+""});

		String transactionTicketquery="insert into transaction_tickets(ticketid,ticketname,ticketqty,tid,eventid) values (to_number(?,'999'),?,to_number(?,'999'),?,?)";
		DbUtil.executeUpdateQuery(transactionTicketquery,new String[]{"101","Attending",sure,transid,(String)attendeeMap.get("eventid")});
		String rsvp_transaction_query="insert into rsvp_transactions (eventid,tid,tdate,fname,lname,email,phone,tkey,responsetype,yescount,notsurecount) values (?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),?,?,?,?,?,?,to_number(?,'999'),to_number(?,'999'))";

		DbUtil.executeUpdateQuery(rsvp_transaction_query,new String[]{(String)attendeeMap.get("eventid"),transid,DateUtil.getCurrDBFormatDate(),(String)attendeeMap.get("fname"),(String)attendeeMap.get("lname"),(String)attendeeMap.get("email"),"",transid,"Y",sure,"0"});
		
		DbUtil.executeUpdateQuery("update event_reg_transactions set servicefee=CAST(? AS NUMERIC),ebeefee_usd=?::NUMERIC,currency_code='USD',collected_ticketqty=?::NUMERIC,actual_ticketqty=?::NUMERIC,currency_conversion_factor=?::BIGINT,amount_we_have=?  where tid=?",new String[]{servicefee+"",servicefee_USD+"",surecount+"",surecount+"",ebeefeeMap.get("conv_factor"),amount_we_have+"",transid});
		
		String iscollected=DbUtil.getVal("select 'yes' from mgr_credits_usage_history where tid=?", new String[]{transid});
		if(!"yes".equals(iscollected) && servicefee_USD>0){
			String avail=DbUtil.getVal("select 'yes' from mgr_available_credits where mgr_id=?::BIGINT and available_credits>=?::NUMERIC", new String[]{mgrId,servicefee+""});
			if("yes".equals(avail) && !"yes".equals(iscollected)){
				DbUtil.executeUpdateQuery("update mgr_available_credits set used_credits=used_credits+?::NUMERIC where mgr_id=?::BIGINT", new String[]{servicefee_USD+"",mgrId});
				DbUtil.executeUpdateQuery("update mgr_available_credits set available_credits=total_credits-used_credits,updated_by='registration',last_updated_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') where mgr_id=?::BIGINT", new String[]{DateUtil.getCurrDBFormatDate(),mgrId});
				DbUtil.executeUpdateQuery("insert into mgr_credits_usage_history(mgr_id,used_for_eventid,used_credits,tid,used_date) values(?::BIGINT,?::BIGINT,?::NUMERIC,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))", new String[]{mgrId,(String)attendeeMap.get("eventid"),servicefee_USD+"",transid,DateUtil.getCurrDBFormatDate()});
				DbUtil.executeUpdateQuery("update event_reg_transactions set collected_servicefee=?::NUMERIC,collected_by='beecredits' where tid=?", new String[]{servicefee+"",transid});
				DbUtil.executeUpdateQuery("update event_reg_transactions set amount_we_have=amount_we_have::NUMERIC+collected_servicefee where tid=?", new String[]{transid});
			}
		}
	}
	
	public static void insertBaseProfile(HashMap baseprofile){
		String profilebasequery="insert into profile_base_info(eventid,fname,lname,transactionid,phone,email,profilekey,ticketid,tickettype,profileid,created_at)"
		                          +" values(CAST(? AS BIGINT),?,?,?,?,?,?,CAST(? AS BIGINT),?,CAST(? AS INTEGER),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
		StatusObj s1=DbUtil.executeUpdateQuery(profilebasequery,new String[]{(String)baseprofile.get("eventid"),
				(String)baseprofile.get("fname"),(String)baseprofile.get("lname"),
				(String)baseprofile.get("tid"),(String)baseprofile.get("phone"),(String)baseprofile.get("email"),
				(String)baseprofile.get("profilekey"),(String)baseprofile.get("ticketid"),
				(String)baseprofile.get("tickettype"),(String)baseprofile.get("profileid"),
				DateUtil.getCurrDBFormatDate()});
		}


public static void insertRsvpResponse(HashMap respnseMap){	
	System.out.println("respnseMap::::::"+respnseMap);
	StatusObj sb2=DbUtil.executeUpdateQuery("delete from custom_questions_response where ref_id=to_number(?,'99999999') and attribid=to_number(?,'999999999') and shortresponse=? and question_original=?",	new String[]{(String)respnseMap.get("responseid"),(String)respnseMap.get("questionid"),(String)respnseMap.get("shortresponse"),(String)respnseMap.get("question")});
StatusObj sb1=DbUtil.executeUpdateQuery("insert into custom_questions_response(ref_id,attribid," +
		"created,shortresponse,bigresponse,question_original) " +
		"values(?,to_number(?,'9999999999'),now(),?,?,?)",
		new String[]{(String)respnseMap.get("responseid"),(String)respnseMap.get("questionid"),
		(String)respnseMap.get("shortresponse"),(String)respnseMap.get("bigresponse"),
		(String)respnseMap.get("question")});
}

public static HashMap getQuestionOriginal(String custom_setid) {
	DBManager Db=new DBManager();
	StatusObj s1=Db.executeSelectQuery("select attrib_id,attribname from custom_attribs where attrib_setid=to_number(?,'999999999')", new String[]{custom_setid});
	HashMap hm=null;
	
	if(s1.getStatus()){
	hm=new HashMap();
		for(int i=0;i<s1.getCount();i++){
		hm.put(Db.getValue(i, "attrib_id", ""), Db.getValue(i, "attribname", ""));
	}
	}
	return hm;
}

static String[] fillData(HashMap hm,String eventid,EmailTemplate emailtemplate,VelocityContext context){
	//EventTicketDB edb=new EventTicketDB();
	//HashMap evtmap=new HashMap();
	//StatusObj sobj=edb.getEventInfo(eventid,evtmap);
	String eventurl=null;
	eventurl=DbUtil.getVal("select url from event_custom_urls where eventid=?",new String[]{eventid});
	if(eventurl==null){
	eventurl=serveraddress+"/event?eid="+eventid;
	}

	String urllink=eventurl;
	HashMap attendeemap=new HashMap();
	attendeemap.put("firstName",GenUtil.XMLEncode((String)context.get("fname")));
	attendeemap.put("lastName",GenUtil.XMLEncode((String)context.get("lname")));
	attendeemap.put("email",GenUtil.XMLEncode((String)context.get("emailid"))) ;

	context.put("eventURL",urllink);

	String mailtype="";
	String mailcontent=emailtemplate.getHtmlFormat();
	if(mailcontent==null||"".equals(mailcontent)){
	mailcontent=emailtemplate.getTextFormat();
	mailtype="Text";
	}
	else{
	mailtype="Html";
	}
	String message=getVelocityOutPut(context,mailcontent);
	String frommail=(String)hm.get("m_email");
	String subject=getVelocityOutPut(context,emailtemplate.getSubjectFormat());
	return new String[]{message,frommail,subject,mailtype};
			}
	static String getVelocityOutPut(VelocityContext vc,String Template){
	StringBuffer str=new StringBuffer();
	java.io.StringWriter out1=new java.io.StringWriter();
	VelocityEngine ve= new VelocityEngine();
	try{
	ve.init();
	boolean abletopares=ve.evaluate(vc,out1,"ebeetemplate",Template );
	str=out1.getBuffer();
	}
	catch(Exception e){
	System.out.println(e.getMessage());
	}
	return str.toString();
	}
	
	public static void getdetails(String tid,String eid,VelocityContext context,String sure,String notsure){
		EventInfoDB edb=new EventInfoDB();
		RegistrationDBHelper regdb=new RegistrationDBHelper();
		TicketsDB tktdb=new TicketsDB();
		HashMap configMap = tktdb.getConfigValuesFromDb(eid);
		HashMap evtmap=new HashMap();
		StatusObj sobj=edb.getEventInfo(eid,evtmap);
		String startday,endday;
		startday=(String)evtmap.get("StartDate_Day");

		endday=(String)evtmap.get("EndDate_Day");
		String starttime=(String)evtmap.get("STARTTIME");

		String endtime=(String)evtmap.get("ENDTIME");
		String venue=(String)evtmap.get("VENUE");
		String location=(String)evtmap.get("LOCATION");
		String fname="";
		String lname="";
        String showcustomquestions="";
		HashMap attendeeDetails=new HashMap();
		HashMap notSureDetails=new HashMap();
        HashMap customquestions=new HashMap();
		showcustomquestions=DbUtil.getVal("select value from config where config_id =(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.confirmationemail.questions.show'", new String[]{eid});
		if("Y".equalsIgnoreCase(showcustomquestions))
			 customquestions=regdb.getCustomQuestions(eid,tid); 
        HashMap buyerdetails=null;
		buyerdetails=regdb.getBuyerInfo(tid,eid);
		String getprofiledetailsquery="select fname,lname,profilekey from profile_base_info where eventid=CAST(? AS BIGINT) and transactionid=? and ticketid=to_number(?,'999999999')";
		DBManager db=new DBManager();
		if(Integer.parseInt(sure)>0){
			StatusObj profilesb=db.executeSelectQuery(getprofiledetailsquery,new String[]{eid,tid,"101"});
			
			if(profilesb.getStatus()){
				for(int i=0;i<profilesb.getCount();i++){
					attendeeDetails.put("fname_"+i,db.getValue(i,"fname",""));
					attendeeDetails.put("lname_"+i,db.getValue(i,"lname",""));
					attendeeDetails.put("pkey_"+i,db.getValue(i,"profilekey",""));
                    String pkey=db.getValue(i,"profilekey","");
					String custquestion=regdb.getCustQuestionsResponse(pkey, customquestions);
					if(!"".equals("custquestion"))attendeeDetails.put("customQuestion_"+i, custquestion);
                
				}

				context.put("attendeeDetails",attendeeDetails);
			}
		}
		if(Integer.parseInt(notsure)>0){
			StatusObj profilesb=db.executeSelectQuery(getprofiledetailsquery,new String[]{eid,tid,"102"});
			if(profilesb.getStatus()){
				for(int i=0;i<profilesb.getCount();i++){
					notSureDetails.put("fname_"+i,db.getValue(i,"fname",""));
					notSureDetails.put("lname_"+i,db.getValue(i,"lname",""));
					notSureDetails.put("pkey_"+i,db.getValue(i,"profilekey",""));
                    String pkey=db.getValue(i,"profilekey","");
					String custquestion=regdb.getCustQuestionsResponse(pkey, customquestions);
					if(!"".equals("custquestion"))notSureDetails.put("customQuestion_"+i, custquestion);
                }
				
				context.put("notSureDetails",notSureDetails);
			}
		}
		HashMap codersvp=new HashMap();
		codersvp=getrsvpbarcode(eid,tid,sure,attendeeDetails,context,"a");
		context.put("attendingCode",codersvp);

		codersvp=getrsvpbarcode(eid,tid,notsure,notSureDetails,context,"ns");
		context.put("notSureCode",codersvp);

		String qrcodeoption=(String)configMap.get("confirmationpage.qrcodes");

		if(qrcodeoption==null||"".equals(qrcodeoption))
		qrcodeoption="Attendee";
		//ArrayList purchasedTickets=regdb.getConfirmationTicketDetails(tid,eid);
		ArrayList profileInfo=regdb.getProfileInfo(tid,eid);
		//HashMap ticketsdetails=regmgr.getTicketDetails(eid);
		if(profileInfo!=null&&profileInfo.size()>0){
			try{
				for(int i=0;i<profileInfo.size();i++){
					
					HashMap profile=(HashMap)profileInfo.get(i);
					//String ticketid=(String)profile.get("ticketid");
					String profilekey=(String)profile.get("profilekey");
					fname=(String)profile.get("fname");
					lname=(String)profile.get("lname");
					if("".equals(fname)){
						fname=(String)buyerdetails.get("fname");
						lname=(String)buyerdetails.get("lname");
					}
					
					String  eventdate=DbUtil.getVal("select eventdate from event_reg_transactions where tid=?",new String[]{tid});
					if(eventdate == null || eventdate.equals(""))
						eventdate=startday+" "+starttime+"<br>Ends - "+endday+" "+endtime;
					context.put("eventdate",eventdate);
					context.put("startDay",startday);
					context.put("endDay",endday);
					context.put("startTime",starttime);
					context.put("endTime",endtime);
					context.put("venue",venue);
					context.put("location",location);
					context.put("eventName",(String)evtmap.get("EVENTNAME"));
					context.put("mgrFirstName",(String)evtmap.get("FIRSTNAME"));
					context.put("mgrLastName",(String)evtmap.get("LASTNAME"));
					context.put("mgrEmail",(String)evtmap.get("EMAIL"));
					context.put("mgrPhone",GenUtil.getHMvalue(evtmap,"PHONE"));
					context.put("registrantDetails",buyerdetails);
				}
			}
			catch(Exception e){
				System.out.println(e);
			}
		  }
		}
	
	static HashMap getrsvpbarcode(String eid,String tid,String count,HashMap details,VelocityContext context,String pattern){
		HashMap barcodersvp=new HashMap();
		try{
			RegistrationDBHelper regdb=new RegistrationDBHelper();
			HashMap qr_barcodemsg=regdb.getQrCodeBarCode(eid);
			String qrcode="",vbarcode="",hbarcode="";
			qrcode=(String)qr_barcodemsg.get("qrcode");
			vbarcode=(String)qr_barcodemsg.get("vbarcode");
			hbarcode=(String)qr_barcodemsg.get("hbarcode");
			
			
		if(Integer.parseInt(count)>0){
			JSONObject obj=new JSONObject();
			
			obj.put("eid",eid);
			obj.put("xid",tid);
			System.out.println(eid+"<----->"+tid);
			for(int i=0;i<Integer.parseInt(count);i++){
				obj.put("pkey",details.get("pkey_"+i));
				obj.put("fn",details.get("fname_"+i));
				obj.put("ln",details.get("lname_"+i));
				obj.put("tId","101");
				String qstring=obj.toString();
				try{
					String qr[]=qrcode.split("#msg");
					qrcode=qr[0]+java.net.URLEncoder.encode(qstring)+qr[1];
					String vbar[]=vbarcode.split("#msg");
					vbarcode=vbar[0]+obj.get("pkey")+vbar[1];
					String hbar[]=hbarcode.split("#msg");
					hbarcode=hbar[0]+obj.get("pkey")+hbar[1];
					String vserver[]=vbarcode.split("#serveraddress");
					vbarcode=vserver[0]+serveraddress+vserver[1];
					String hserver[]=hbarcode.split("#serveraddress");
					hbarcode=hserver[0]+serveraddress+hserver[1];
					
				}catch(Exception e){System.out.println("exception in try:"+e.getMessage());}
				
				barcodersvp.put(pattern+"_qrCode_"+i, qrcode);
				barcodersvp.put(pattern+"_barCode128_"+i, hbarcode);
				barcodersvp.put(pattern+"_vbarCode128_"+i, vbarcode);
					
				//barcodersvp.put(pattern+"_qrCode_"+i,"<img src='http://chart.apis.google.com/chart?cht=qr&chs=100x100&chl="+java.net.URLEncoder.encode(qstring)+"'/>");
				//barcodersvp.put(pattern+"_barCode128_"+i,"<img width='200px' src='"+serveraddress+"/genbc?type=code128&msg="+obj.get("pkey")+"&height=0.75cm&hrsize=6pt&hrp=bottom&fmt=gif'>");
			}
			
			
		}
		
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return barcodersvp;
	}

}
