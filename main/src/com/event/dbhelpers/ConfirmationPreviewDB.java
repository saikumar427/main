package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.event.helpers.I18n;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;
import com.eventmanage.seating.beans.Venue;
import com.eventregister.TicketsDB;

public class ConfirmationPreviewDB {
	public static final String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
	public static String fillConfirmation(String eid,String template,String type, String conpreview){
		return fillConfirmation(eid,template,type,conpreview,"");
	}
	
	public static String fillConfirmation(String eid,String template,String type, String conpreview,String purpose){
		VelocityContext context = new VelocityContext();
		if(conpreview.equals("email")){
			
			String eventurl=null;
			eventurl=DbUtil.getVal("select url from event_custom_urls where eventid=?",new String[]{eid});
			if(eventurl==null){
				eventurl=serveraddress+"/event?eid="+eid;
			}
			context.put("eventURL",eventurl);
			
			String attendeeurl=serveraddress+"/main/user/attendee";
			context.put("attendeeUrl",attendeeurl);
		}
		String seatcode="";
		String venueenalbe=EventDB.getConfigVal(eid,"event.seating.enabled","NO");
		if("yes".equalsIgnoreCase(venueenalbe)){
			String venueid=EventDB.getConfigVal(eid,"event.seating.venueid","");
			seatcode=DbUtil.getVal("select seatcode from venue_seats where venue_id=cast(? as integer)"+
			" and seatcode not in('EMPTY','') and seatcode is not null limit 1", new String []{venueid});
			if(seatcode==null || "null".equals(seatcode))seatcode="";
		}
		
		String recurringevent=DbUtil.getVal("select 'yes' from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.recurring' and value='Y'",new String[]{eid});
		//String eventpageLink="Back To Event Page";
		String eventpageLink = I18n.getString("cps.back.to.event.page.lbl");
		EventInfoDB edb=new EventInfoDB();
		HashMap evtmap=new HashMap();
		StatusObj sobj=edb.getEventInfo(eid,evtmap);
		String startday=(String)evtmap.get("StartDate_Day");
		String starttime=(String)evtmap.get("STARTTIME");
		String endday=(String)evtmap.get("EndDate_Day");
		String endtime=(String)evtmap.get("ENDTIME");
		String venue=(String)evtmap.get("VENUE");
		String location=(String)evtmap.get("LOCATION");
		
		
		
		context.put("transactionKey","RKDBPXQQKA");
		context.put("venue",venue);
		context.put("location",location);
		context.put("eventName",(String)evtmap.get("EVENTNAME"));
		context.put("startDay",startday);
		context.put("startTime",starttime);
		if(recurringevent==null){
			context.put("endDay",endday);
			context.put("endTime",endtime);
		}
		
		String mgrname=DbUtil.getVal("select value from config where name='event.hostname' and config_id=" +
				"(select config_id from eventinfo where eventid=CAST(? AS BIGINT))", new String[]{eid});
		if(mgrname!=null){
			context.put("mgrFirstName",mgrname);
			context.put("mgrLastName","");
		}else{
			context.put("mgrFirstName",(String)evtmap.get("FIRSTNAME"));
			context.put("mgrLastName",(String)evtmap.get("LASTNAME"));
		}
		context.put("mgrEmail",(String)evtmap.get("EMAIL"));
		context.put("mgrPhone",GenUtil.getHMvalue(evtmap,"PHONE"));
		
		HashMap<String,String> buyerDetails=new HashMap<String,String>();
		buyerDetails.put("firstName",I18n.getString("cps.first.name.page.lbl"));
		buyerDetails.put("lastName",I18n.getString("cps.last.name.page.lbl"));
		buyerDetails.put("email","email");
		
		if(type.equals("rsvp")){
			HashMap<String,String> attendeeDetails=new HashMap<String,String>();
			attendeeDetails.put("fname_"+0,I18n.getString("cps.first.name.page.lbl"));
			attendeeDetails.put("lname_"+0,I18n.getString("cps.last.name.page.lbl"));
			String customquestion=getCustomQuestions(eid,"attendee","101",conpreview);
            if(!"".equals(customquestion))attendeeDetails.put("customQuestion_"+0,customquestion);
			
			context.put("attendee","1");
			context.put("notSure","0");
			if(conpreview.equals("email"))
				context.put("eventreglink","");
			else
				context.put("eventreglink",eventpageLink);
			String eventdate="";
	        if(recurringevent!=null)
				eventdate=startday+" "+starttime;
			else
				eventdate=startday+" "+starttime+"<br/>"+I18n.getString("cps.ends.time.lbl")+" - "+endday+" "+endtime;
			context.put("eventdate",eventdate);
			context.put("attendingLabel",I18n.getString("cps.page.attending.lbl"));
			context.put("attendeeDetails",attendeeDetails);
			context.put("registrantDetails",buyerDetails);
			String buyerquestions=getCustomQuestions(eid,"buyer","",conpreview);
			if(!"".equals(buyerquestions)){
				context.put("buyercustomQuestions",buyerquestions);
			}
			
			HashMap codersvp=new HashMap();
			
			codersvp.put("a"+"_qrCode_"+0,"<img src='/main/images/qrcode.jpg'/>");
			codersvp.put("a"+"_barCode128_"+0,"<img width='200px' src='/main/images/barcode.jpg'>");
			codersvp.put("a"+"_vBarCode128_"+0,"<img width='200px' style='-webkit-transform: rotate(-270deg);-moz-transform: rotate(-270deg);filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=1);' src='/main/images/barcode.jpg'>");
			
			context.put("attendingCode",codersvp);
		}else{
			
			
			HashMap ticketPageLabels=DisplayAttribsDB.getAttribValues(eid,"RegFlowWordings");
			String ticketNameLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.ticket.name.label","Ticket Name");
			String ticketPriceLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.ticket.price.label","Price");
			String ticketQtyLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.ticket.qty.label","Quantity");
			String processFeeLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.processfee.label","Fee");
			String taxAmountLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.tax.amount.label","Tax");
			String GrandTotalLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.grandtotal.amount.label","Grand Total");
			String totalAmountLabel=GenUtil.getHMvalue(ticketPageLabels,"event.reg.total.amount.label","Total");
			TicketsDB ticketInfo=new TicketsDB();
			HashMap scopemap=ticketInfo.getConfigValuesFromDb(eid);
			String reftermsandcondtions=GenUtil.getHMvalue(scopemap,"event.ticketpage.refundpolicy.statement","");
			String refundpolicy=GenUtil.getHMvalue(scopemap,"event.confirmationemail.refundpolicy","Yes");
			
			ArrayList purchasedTickets=getConfirmationTicketDetails(eid,purpose,seatcode,conpreview);
			String totalamount="";
			if(purchasedTickets != null){
				HashMap gm = (HashMap)purchasedTickets.get(0);
				totalamount = (String)gm.get("totalAmount");
			}
			
			String currencyformat=DbUtil.getVal("select html_symbol from currency_symbols where currency_code=(select currency_code from event_currency where eventid=?)",new String[]{eid});
			if(currencyformat==null)
				currencyformat="$";
			
			context.put("firstName",I18n.getString("cps.first.name.page.lbl"));
			context.put("lastName",I18n.getString("cps.last.name.page.lbl"));
			context.put("purchasedTickets",purchasedTickets);
			context.put("currencyFormat",currencyformat);
			context.put("grandTotal",totalamount);
			if(!"".equalsIgnoreCase(seatcode.trim()))
			context.put("seatcodes",seatcode);				
			context.put("orderNumber","10000200");
			context.put("ticketPriceLabel",ticketPriceLabel);
			context.put("ticketNameLabel",ticketNameLabel);
			context.put("taxAmountLabel",taxAmountLabel);
			context.put("GrandTotalLabel",GrandTotalLabel);
			context.put("totalAmountLabel",totalAmountLabel);
			context.put("ticketQtyLabel",ticketQtyLabel);
			context.put("processFeeLabel",processFeeLabel);
			context.put("eventpageLink",eventpageLink);
			context.put("buyerDetails",buyerDetails);
			String buyerquestions=getCustomQuestions(eid,"buyer","",conpreview);
			if(!"".equals(buyerquestions)){
				context.put("buyercustomQuestions",buyerquestions);
			}
			
			if("Yes".equalsIgnoreCase(refundpolicy)){               
				context.put("refundPolicy",reftermsandcondtions);
			}
			String otherPaymentDesc=DbUtil.getVal("select attrib_1 from payment_types where refid=? and purpose='Event' and paytype='other' and status='Enabled'",new String[]{eid});
			if(otherPaymentDesc!=null && !"".equals(otherPaymentDesc.trim())){
				context.put("otherPaymentDesc",otherPaymentDesc);
			}
			
		}
		
		String preview=getVelocityOutPut(context,template);
		
		return preview;
	}
	
	public static ArrayList getConfirmationTicketDetails(String eid,String purpose,String seatcode,String previewtype){
	
		ArrayList al=new ArrayList();
		double tickettotamount=0;
		HashMap ticketdetails = new HashMap();
		if(purpose.contains("VB"))
			ticketdetails=getVolumeTicketDetails(eid,purpose);
		else
			ticketdetails=getTicketDetails(eid);
		
		try{
			String total=(String)ticketdetails.get("totalAmount");
            String ticketid=(String)ticketdetails.get("ticketId");
            System.out.println("ticketid is:"+ticketid);
            HashMap gm=new HashMap();
            String customquestion=getCustomQuestions(eid,"attendee",ticketid,previewtype);
            if(!"".equals(customquestion))gm.put("customQuestion",customquestion);
			gm.putAll(ticketdetails);
			gm.put("ticketQuantity","1");
			try{
				tickettotamount=Double.parseDouble(total);
			}catch(Exception e){tickettotamount=0;}
			gm.put("totalAmount",CurrencyFormat.getCurrencyFormat("",tickettotamount+"",true));
			//gm.put("attendeeName","Firstname Lastname");
			gm.put("attendeeName",I18n.getString("cps.first.name.page.lbl")+" "+I18n.getString("cps.last.name.page.lbl"));
			if("Yes".equalsIgnoreCase((String)ticketdetails.get("scanCode"))){
			gm.put("qrcode", "<img src='"+serveraddress+"/main/images/qrcode.jpg'/>");
			gm.put("barcode", "<img width='200px' src='"+serveraddress+"/main/images/barcode.jpg'>");
			gm.put("vBarcode", "<img width='200px' style='-webkit-transform: rotate(-270deg);-moz-transform: rotate(-270deg);filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=1);' src='"+serveraddress+"/main/images/barcode.jpg'>");
			}else{
				gm.put("qrcode", "");
				gm.put("barcode", "");
				gm.put("vBarcode", "");
			}
			if(!"".equalsIgnoreCase(seatcode.trim()))
			gm.put("seatCodes",seatcode);
			al.add(gm);
		
		}catch(Exception e){
			System.out.println("Exception occured is"+e.getMessage());
		}	
		return al;
	}
	
	public static HashMap getTicketDetails(String eid){
		HashMap hm=new HashMap();
		try{
			
		String ticketQuery="select price_id,ticket_name,ticket_price,process_fee,(ticket_price+process_fee) as total,scan_code_required from price where evt_id=CAST(? AS BIGINT)  order by price_id limit 1";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(ticketQuery,new String[]{eid});
		if(sb.getStatus()){
			hm.put("ticketId",db.getValue(0,"price_id",""));
			hm.put("ticketName",db.getValue(0,"ticket_name",""));
			hm.put("ticketPrice",CurrencyFormat.getCurrencyFormat("",db.getValue(0,"ticket_price","")+"",true));
			hm.put("discount","0.00");
			hm.put("processingFee",CurrencyFormat.getCurrencyFormat("",db.getValue(0,"process_fee","")+"",true));
			hm.put("totalAmount",CurrencyFormat.getCurrencyFormat("",db.getValue(0,"total","")+"",true));
			hm.put("scanCode",db.getValue(0,"scan_code_required",""));
		}else{
			hm.put("ticketName","Ticketname");
			hm.put("ticketPrice","0.00");
			hm.put("discount","0.00");
			hm.put("processingFee","0.00");
			hm.put("totalAmount","0.00");
			hm.put("scanCode","Yes");
		}
		
		}
		catch(Exception e){
		System.out.println("Exception in getting getpurchasedTickets"+e.getMessage());
		}
		return hm;
	}
	
	public static HashMap getVolumeTicketDetails(String eid,String purpose){
		HashMap hm=new HashMap();
		try{
		String ticketQuery="select ticketid,ticket_name,price,discount_value,fail_discount,fee,(price+fee) as total from groupdeal_tickets where eventid=? order by ticketid limit 1";	
		//String ticketQuery="select price_id,ticket_name,ticket_price,process_fee,(ticket_price+process_fee) as total from price where evt_id=to_number(?,'9999999999999') and isattendee='Yes' order by price_id";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(ticketQuery,new String[]{eid});
		if(sb.getStatus()){
			String total = db.getValue(0,"total","");
			String discount = "0.00";
			if("VB_NOTRIGGERCAPTURE_CONFIRMATION".equals(purpose))
				discount = db.getValue(0,"fail_discount","");
			else
				discount = db.getValue(0,"discount_value","");
			total = String.valueOf(Double.parseDouble(total)-Double.parseDouble(discount));
			hm.put("ticketId",db.getValue(0,"ticketid",""));
			hm.put("ticketName",db.getValue(0,"ticket_name",""));
			hm.put("ticketPrice",CurrencyFormat.getCurrencyFormat("",db.getValue(0,"price",""),true));
			hm.put("discount",CurrencyFormat.getCurrencyFormat("",discount,true));
			hm.put("processingFee",CurrencyFormat.getCurrencyFormat("",db.getValue(0,"fee",""),true));
			hm.put("totalAmount",CurrencyFormat.getCurrencyFormat("",total,true));
		}else{
			hm.put("ticketName","Ticketname");
			hm.put("ticketPrice","0.00");
			hm.put("discount","0.00");
			hm.put("processingFee","0.00");
			hm.put("totalAmount","0.00");
		}
		
		}
		catch(Exception e){
		System.out.println("Exception in getting getpurchasedTickets"+e.getMessage());
		}
		return hm;
	}
	
	public static String getVelocityOutPut(VelocityContext vc,String Template){
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
	
	public static String getHTMLCSS(String eid){
		
		String csscontent=null;
		String themetype=null;
		String themecode=null;
		HashMap themeDetails=getThemeCodeAndType(eid);
		if(themeDetails!=null){
		themetype=(String)themeDetails.get("themetype");
		themecode=(String)themeDetails.get("themecode");
		}

		if("DEFAULT".equals(themetype))
		csscontent=DbUtil.getVal("select cssurl  from ebee_roller_def_themes where module =? and themecode=?",new String[]{"event",themecode});
		else if("CUSTOM".equals(themetype))
		csscontent=DbUtil.getVal("select cssurl  from user_custom_roller_themes where module =? and themecode=? and refid=?",new String[]{"event",themecode,eid});
		else
		csscontent=DbUtil.getVal("select cssurl  from user_customized_themes where module =? and themeid=? ",new String[]{"event",themecode});

		return csscontent;
	}
	
	public static HashMap getThemeCodeAndType(String refid){
		String themeQuery="select themetype,themecode from user_roller_themes where refid=? and module=?";
		HashMap hm=new HashMap();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(themeQuery,new String[]{refid,"event"});
		if(sb.getStatus())
		{
		hm.put("themetype",db.getValue(0,"themetype",""));
		hm.put("themecode",db.getValue(0,"themecode",""));
		}
		return hm;
		}
	
	
	   public static String getCustomQuestions(String eventid,String context,String ticketid,String purpose){
		   System.out.println("In getCustomQuestions preview for event: "+eventid+" :: "+purpose);
		   String customquestions="";
		   if(eventid==null)eventid="";
		   if(ticketid==null)ticketid="";
		   List questions=new ArrayList();
		   purpose=purpose+"_preview";
		   try{
		   if("buyer".equals(context))
			   questions=DbUtil.getValues("select attrib_name from confirmationscreen_questions where eventid=? and type=? and context=? order by position",new String[]{eventid,purpose,context});
		   else if("attendee".equals(context)){
			  List defques=new ArrayList();
			  List defconfques=new ArrayList();
			  if(!"".equals(eventid) && !"".equals(ticketid))
			  defques=DbUtil.getValues("select attribid from base_profile_questions where groupid=CAST(? as BIGINT) and contextid=CAST(? as INTEGER) order by attribid", new String[]{eventid,ticketid});  
			  defconfques=DbUtil.getValues("select attrib_name from confirmationscreen_questions where eventid=? and attrib_id in(-1,-2) and context=? and type=?",new String[]{eventid,context,purpose});
			  for(int j=0;j<defques.size();j++){
				   if("email".equals((String)defques.get(j)) && defconfques.contains("Email"))
						questions.add("Email");
				   else if("phone".equals((String)defques.get(j)) && defconfques.contains("Phone"))
					   questions.add("Phone");
			   }
			   List cutques=new ArrayList();
			   if(!"".equals(eventid) && !"".equals(ticketid))
			   cutques=DbUtil.getValues("select attrib_name  from confirmationscreen_questions cq where eventid=? and type=? and context=? and attrib_id in(select attribid from subgroupattribs where groupid=CAST(? as BIGINT) and subgroupid=CAST(? as INTEGER)) order by cq.position", new String[]{eventid,purpose,context,eventid,ticketid});
			   for(int k=0;k<cutques.size();k++){
				   questions.add((String)cutques.get(k));
			   }
			   
		   }
		   if(questions.size()>0){
			   customquestions="<table cellpadding='0px' cellspacing='0px' align='left'>";
			   for(int i=0;i<questions.size();i++){
				   customquestions=customquestions+"<tr><td><b>"+(String)questions.get(i)+":</b>&nbsp;"+I18n.getString("cps.answer.lbl")+"</td></tr>";
			   }
			   customquestions=customquestions+"</table>";
		   }
		   }catch(Exception e){
			   System.out.println("Exception occured in getCustomQuestions for event ::"+eventid+" :: purpose: "+purpose);
		   }
		   return customquestions;
	   }
	   
	   
	   public static void deletePQuestions(String eventid,String purpose){
		   System.out.println("In deletePQuestions for event ::"+eventid+" :: "+purpose);
		   DbUtil.executeUpdateQuery("delete from confirmationscreen_questions where eventid=? and type=? and context in('attendee','buyer')", new String[]{eventid,purpose});
		   System.out.println("Preview Questions deleted successfully for event ::"+eventid+" :: "+purpose);
	   }
	

}
