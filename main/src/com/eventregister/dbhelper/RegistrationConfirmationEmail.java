package com.eventregister.dbhelper;
import java.util.ArrayList;
import java.util.HashMap;
import javax.mail.internet.MimeUtility;
import org.apache.velocity .*;
import org.apache.velocity.app.*;
import com.event.dbhelpers.DisplayAttribsDB;
import com.event.dbhelpers.EventInfoDB;
import com.eventbee.creditcard.PaymentTypes;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.general.EmailTemplate;
import com.eventbee.general.EventbeeMail;
import com.eventbee.general.GenUtil;
import com.eventbee.general.SendMailStatus;
import com.eventbee.general.StatusObj;
import com.eventregister.TicketsDB;
public class RegistrationConfirmationEmail {
	
	public int sendRegistrationEmail(String tid,String eid){
		return sendRegistrationEmail(tid,eid,"bcc",null);
	}
	
	public int sendRegistrationEmail(String tid,String eid,String bcc,String mailto){
		System.out.println("\n In RegistrationConfirmationEmail sendRegistrationEmail: "+tid);
		int status=0;
		String unitid="13579";
		String emailyes=null;
		//String emailtype="";
		TicketsDB ticketInfo=new TicketsDB();
		HashMap scopemap=ticketInfo.getConfigValuesFromDb(eid);

		if(scopemap!=null)
		emailyes=GenUtil.getHMvalue(scopemap,"event.reg.email.sent","Yes");

		if("Yes".equalsIgnoreCase(emailyes)){
		try{

			//EmailTemplate emailtemplate = getEmailTemplate(eid);

			EmailObj obj=EventbeeMail.getEmailObj();
			String[] formatmessage=fillData(tid,eid,scopemap);
			String toemailId=formatmessage[4];
			
			if(bcc.equals("bcc")){
			String sendmailtoattendee=GenUtil.getHMvalue(scopemap,"event.sendmailtoattendee","Yes");
			String sendmailtomgr=GenUtil.getHMvalue(scopemap,"event.sendmailtomgr","Yes");
			String bcclist=EbeeConstantsF.get("ADMIN_EMAIL","sridevi@beeport.com");
			String bcclistfromDB=GenUtil.getHMvalue(scopemap,"registration.email.cc.list","");
		
			if("No".equalsIgnoreCase(sendmailtoattendee)){
				if("Yes".equalsIgnoreCase(sendmailtomgr)){ //A=N,M=Y
					toemailId=formatmessage[1];
					//Eventbee admin and bcc configured list will get bcc
					if(bcclistfromDB!=null&&!"".equals(bcclistfromDB))
					bcclist+=","+bcclistfromDB;
				}else{//A=N,M=N
					toemailId=EbeeConstantsF.get("ADMIN_EMAIL","sridevi@beeport.com");
					//Eventbee admin in to & configured list will get bcc
					bcclist=bcclistfromDB;
				}
			}else{
				if("Yes".equalsIgnoreCase(sendmailtomgr)){//A=Y,M=Y
					bcclist+=","+formatmessage[1];
				}else{//A=Y,M=N
				
				}
				if(bcclistfromDB!=null&&!"".equals(bcclistfromDB))
				bcclist+=","+bcclistfromDB;
			}
			obj.setBcc(bcclist);
			}
			if(mailto != null)
				toemailId=mailto;
			obj.setTo(toemailId);
			//emailtype=formatmessage[3];
			//obj.setFrom(formatmessage[1]);
			obj.setReplyTo(formatmessage[1]);
			String fromemail=DbUtil.getVal("select email from from_email_permissions where email=?", new String[]{formatmessage[1]});
			if(fromemail==null || "".equals(fromemail))
				fromemail=EbeeConstantsF.get("TICKETING_FROM_EMAIL","tickets@eventbee.com");
			obj.setFrom(fromemail);
			obj.setSubject(MimeUtility.encodeText(formatmessage[2],"UTF-8","Q"));
			obj.setSendMailStatus(new SendMailStatus(unitid,"EVENT_REGISTARTION_CONFIRMATION",eid,"1"));
			//if("Html".equals(emailtype)){
			obj.setHtmlMessage(formatmessage[0]);
			System.out.println("direct mail before send and insert stray");
			EventbeeMail.sendHtmlMailPlain(obj);
			EventbeeMail.insertStrayEmail(obj,"html","S");
			System.out.println("direct mail after send and insert stray");
			/*}else if("Text".equals(emailtype)){
				obj.setTextMessage(formatmessage[0]);
				System.out.println("before send text mail");
				EventbeeMail.sendTextMail(obj);
				System.out.println("after send html mail");
			}*/
			status=1;
		}catch(Exception e){
			status=0;
			System.out.println("\n Exception In RegistrationConfirmationEmail sendRegistrationEmail: "+e.getMessage());
		}
	}
	return status;
	}
	
	public EmailTemplate getEmailTemplate(String eid,String purpose){
		EmailTemplate emailtemplate=null;
		String isformatexists=DbUtil.getVal("select 'yes' from email_templates where purpose=? and groupid=?", new String []{purpose,eid});
		if("yes".equals(isformatexists)){
			emailtemplate=new EmailTemplate("13579",purpose,eid);
		}else{
			emailtemplate=new EmailTemplate("500",purpose);
		}
		return emailtemplate;
	}


	String[] fillData(String tid,String eid,HashMap scopemap){
	String  serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
	String eventid=eid;
	String unitid="13579";
	String transactionid=tid;
	RegistrationDBHelper regdb=new RegistrationDBHelper();
	
	String isntsenabled=DbUtil.getVal("select enablenetworkticketing from group_agent_settings where groupid=? ",new String [] {eventid});
	String commission=DbUtil.getVal("select max(networkcommission) from price where evt_id=CAST(? AS BIGINT)",new String[]{eventid});
	commission="$"+commission;
	String feeconfiguration=GenUtil.getHMvalue(scopemap,"event.feelabel","Fee") ;
	String participateurl=serveraddress+"/participate.jsp?eid="+eventid;
	String learnmorelink=serveraddress+"/helplinks/partnernetwork.jsp";
	EventInfoDB edb=new EventInfoDB();
	HashMap evtmap=new HashMap();
	StatusObj sobj=edb.getEventInfo(eventid,evtmap);
	String emailcontent="This is email content send while in registration";
	
	String currencyformat=DbUtil.getVal("select html_symbol from currency_symbols where currency_code=(select currency_code from event_currency where eventid=?)",new String[]{eventid});
	if(currencyformat==null)
	currencyformat="$";
	
	

	ArrayList purchasedtickets=regdb.getConfirmationTicketDetails(tid,eid);
	HashMap <String,String>regDetails=regdb.getTransactionDetails(tid);
	double processfee=0;
	String eventdate=regDetails.get("eventdate");
	String paymentstatus=regDetails.get("paymentstatus");
	String bookingdomain=regDetails.get("bookingdomain");
	String purpose="EVENT_REGISTARTION_CONFIRMATION";
	String ticketid="";
	String context="";
	if(bookingdomain.contains("VBEE")){
		HashMap purtickets = (HashMap)purchasedtickets.get(0);
		ticketid = (String)purtickets.get("ticketId");
		System.out.println("******** In Confirmation Email for Volume Ticket::ticketId: "+ticketid+" ::paymentstatus: "+paymentstatus);
		//GT: Group Ticketr
		if(!"".equals(ticketid) && ticketid!=null)
			context=DbUtil.getVal("select context from vb_widgets where ticketid=?",new String [] {ticketid});
		if(context==null) context="";
		if(bookingdomain.equals("VBEE_TRIGGER_SUCCESS") && paymentstatus.equals("Completed")){
			if("gt".equals(context))
				purpose="VB_GROUP_TRIGGERCAPTURE_CONFIRMATION";
			else
				purpose="VB_TRIGGERCAPTURE_CONFIRMATION";
		}
		if(bookingdomain.equals("VBEE_NOTRIGGER_SUCCESS") && paymentstatus.equals("Completed")){
			if("gt".equals(context))
				purpose="VB_GROUP_NOTRIGGERCAPTURE_CONFIRMATION";
			else
				purpose="VB_NOTRIGGERCAPTURE_CONFIRMATION";
		}
		if(paymentstatus.equals("Authorized")){
			if("gt".equals(context))
				purpose="VB_GROUP_REGISTRATION_TRIGNOTYET";
			else
				purpose="VB_REGISTRATION_TRIGNOTYET";
		}
		if(paymentstatus.equals("Cancelled")){
			if("gt".equals(context))
				purpose="VB_GROUP_VOID_CONFIRMATION";
			else
				purpose="VB_VOID_CONFIRMATION";
		}
	}
	
	String eventurl=null;
	if(bookingdomain.contains("VBEE") && !"".equals(ticketid) && ticketid != null){
		eventurl=DbUtil.getVal("select visiting_url from vb_widgets where ticketid=?",new String[]{ticketid});
	}
	if(eventurl==null || "".equals(eventurl)){
		eventurl=DbUtil.getVal("select url from event_custom_urls where eventid=?",new String[]{eventid});
	}
	if(eventurl==null || "".equals(eventurl)){
		eventurl=serveraddress+"/event?eid="+eventid;
	}
	
	String startday=(String)evtmap.get("StartDate_Day");
	
	String starttime=(String)evtmap.get("STARTTIME");


	String endday=(String)evtmap.get("EndDate_Day");
	String endtime=(String)evtmap.get("ENDTIME");
	String venue=(String)evtmap.get("VENUE");
	String location=(String)evtmap.get("LOCATION");
	String urllink=eventurl;
	HashMap buyerDetails=regdb.getBuyerInfo(tid,eid);
	System.out.println("buyerdetails"+buyerDetails.toString());

	String reftermsandcondtions=GenUtil.getHMvalue(scopemap,"event.ticketpage.refundpolicy.statement","") ;
	String refundpolicy=GenUtil.getHMvalue(scopemap,"event.confirmationemail.refundpolicy","Yes");
	String grandtotal=GenUtil.getHMvalue(regDetails,"grandtotal","0.00");
	String taxamount=GenUtil.getHMvalue(regDetails,"tax","0.00");

	HashMap ticketPageLabels=DisplayAttribsDB.getAttribValues(eid,"RegFlowWordings");
	//******************************************
	String ticketNameLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.ticket.name.label","Ticket Name");
	String ticketPriceLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.ticket.price.label","Price");
	String ticketQtyLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.ticket.qty.label","Quantity");
	String processFeeLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.processfee.label","Fee");
	String taxAmountLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.tax.amount.label","Tax");
	String GrandTotalLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.grandtotal.amount.label","Grand Total");
	String totalAmountLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.total.amount.label","Total");
	//*************************************************************
	String footer="";	
	VelocityContext mp = new VelocityContext();
	String firstName="";
	String lasName="";
	String toemail="";
	if(buyerDetails!=null){
	firstName=(String)buyerDetails.get("firstName");
	lasName=(String)buyerDetails.get("lastName");
	toemail=(String)buyerDetails.get("email");
	}
	mp.put("firstName",firstName);
	mp.put("lastName",lasName);
	mp.put("eventName",(String)evtmap.get("EVENTNAME"));
	mp.put("transactionKey",transactionid);
	System.out.println("eventdate---"+eventdate);
	if(eventdate!=null&&!" ".equals(eventdate)){
		mp.put("startDay",eventdate);
	}
	else{
	mp.put("startDay",startday);
	mp.put("endDay",endday);
	mp.put("startTime",starttime);
	mp.put("endTime",endtime);
	}
	if(Double.parseDouble(taxamount)>0){
	mp.put("tax",taxamount);
	}
	mp.put("processFeeLabel",feeconfiguration);
	mp.put("venue",venue);
	mp.put("location",location);
	mp.put("eventURL",urllink);
	mp.put("seatcodes", regdb.getTransactionSeatcodes(transactionid));
	mp.put("commission",commission);
	mp.put("mgrFirstName",(String)evtmap.get("FIRSTNAME"));
	mp.put("mgrLastName",(String)evtmap.get("LASTNAME"));
	mp.put("mgrEmail",(String)evtmap.get("EMAIL"));
	mp.put("mgrPhone",GenUtil.getHMvalue(evtmap,"PHONE"));
	mp.put("purchasedTickets",purchasedtickets);
	mp.put("currencyFormat",currencyformat);
	mp.put("grandTotal",grandtotal);
	String attendeeurl=serveraddress+"/main/user/attendee?tid="+tid;
	mp.put("attendeeUrl",attendeeurl);
	String ticketspdf=serveraddress+"/gettickets?tid="+tid;
	mp.put("ticketspdf",ticketspdf);
	if("Yes".equalsIgnoreCase(isntsenabled)){
	mp.put("enabledNTS",isntsenabled);
	}
	mp.put("participateURL",participateurl);
	mp.put("learnMoreLink",learnmorelink);
	if("Yes".equalsIgnoreCase(refundpolicy)){               
	mp.put("refundPolicy",reftermsandcondtions);
	}

	String orderNumber=GenUtil.getHMvalue(regDetails,"ordernumber","0");
	mp.put("orderNumber",orderNumber);
	mp.put("ticketPriceLabel",ticketPriceLabel);
	mp.put("ticketNameLabel",ticketNameLabel);
	mp.put("taxAmountLabel",taxAmountLabel);
	mp.put("GrandTotalLabel",GrandTotalLabel);
	mp.put("grandTotalLabel",GrandTotalLabel);
	mp.put("totalAmountLabel",totalAmountLabel);
	mp.put("ticketQtyLabel",ticketQtyLabel);
	mp.put("processFeeLabel",processFeeLabel);
	mp.put("buyerDetails",buyerDetails);

	HashMap ptypehm=PaymentTypes.getPaymentTypeInfo(eventid,"Event","other");
	String otherdesc=null;
	if(ptypehm!=null){
	otherdesc=(String)ptypehm.get("attrib_1");
	}	       
	if("other".equalsIgnoreCase(GenUtil.getHMvalue(regDetails,"paymenttype",""))){
	mp.put("otherPaymentDesc",otherdesc);
	}

	System.out.println("******** In Confirmation Email for tid: "+tid+" PURPOSE: "+purpose);
	EmailTemplate emailtemplate = getEmailTemplate(eid,purpose);
	
	String mailtype="";
	String mailcontent=emailtemplate.getHtmlFormat();
	if(mailcontent==null||"".equals(mailcontent)){
	mailcontent=emailtemplate.getTextFormat();
	mailtype="Text";
	}
	else{
	mailtype="Html";
	}
	String message=getVelocityOutPut(mp,mailcontent);
	String frommail=(String)evtmap.get("EMAIL");
	if(bookingdomain.contains("VBEE") && "gt".equals(context)){
		frommail="support@groupticketer.com";
	}
	String subject=getVelocityOutPut(mp,emailtemplate.getSubjectFormat());
	return new String[]{message,frommail,subject,mailtype,toemail};
	}


	String getVelocityOutPut(VelocityContext vc,String Template){
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



}
