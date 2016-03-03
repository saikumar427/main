package com.eventbee.widget;

import java.util.Date;
import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.eventbee.general.GenUtil;
import com.eventbee.layout.DBHelper;
import com.eventbee.layout.EventGlobalTemplates;

public class RenderTicketsWidgetHTML implements RenderWidgetHTML {

	@Override
	public String getHTML(HashMap<String, String> refHash,
		HashMap<String, String> dataHash, HashMap<String, String> configHash) {
		String serveraddress=refHash.get("serveraddress");
		if(serveraddress.substring(0, serveraddress.length()-1).indexOf("/")>-1)
		  serveraddress=refHash.get("serveraddress").substring(0,refHash.get("serveraddress").length()-1);
		String groupid=refHash.get("eventid");
		Date date=new java.util.Date();
		long time=date.getTime();
		java.io.StringWriter out=new java.io.StringWriter();
		boolean isrsvp=false,isrecurring=false,isseating=false;
		String venueid="",fbappid="",eventstatus="",isseatingevent="",recurringselect="",recurdateslabel="",venuecss="",ntsenable="",ntscommission="",loginpopup="";
		String notavailimg="",notavailmsg="",unassignimg="",unassignmsg="",trackcode="",discountcode="",ticketurlcode="",context="",fbsharepopup="",customcss="",ntscode="";
		HashMap<String,String> venueMap=new HashMap<String,String>();
		if(refHash.get("discountcode")!=null)discountcode=refHash.get("discountcode");
		System.out.println("discount code in render tickets:"+discountcode);
		if(refHash.get("trackcode")!=null)trackcode=refHash.get("trackcode");
		if(refHash.get("referralntscode")!=null)ntscode=refHash.get("referralntscode");
		if(refHash.get("ntsenable")!=null)ntsenable=refHash.get("ntsenable");
		if(refHash.get("ntscommission")!=null)ntscommission=refHash.get("ntscommission");
		
		StringBuffer sbf1=new StringBuffer();
		try{
		eventstatus=dataHash.get("status");
		HashMap<String,String> configMap=DBHelper.getConfigValuesFromDb(groupid);
		//customcss=DBHelper.getCustomCSS(groupid);
		isrecurring=("Y".equalsIgnoreCase(GenUtil.getHMvalue(configMap,"event.recurring","N")));
		isrsvp=("Yes".equalsIgnoreCase(GenUtil.getHMvalue(configMap,"event.rsvp.enabled","no")));
		isseating=("YES".equalsIgnoreCase(GenUtil.getHMvalue(configMap,"event.seating.enabled","NO")));
		venueid=GenUtil.getHMvalue(configMap,"event.seating.venueid","0");
		fbappid=DBHelper.getFBAppId("ebee.fbconnect.appid");
		loginpopup=GenUtil.getHMvalue(configMap,"event.reg.loginpopup.show","Y");
		if(isseating)isseatingevent="YES";
		else isseatingevent="NO";
		if(isrecurring){
		   recurringselect=DBHelper.getRecurringEventDates(groupid,"tickets");
		   recurdateslabel=DBHelper.getRecurringDatesLabel(groupid);
		}   
		if(recurringselect==null || "".equals(recurringselect))
			recurringselect="<select onchange=getTicketsJson('"+groupid+"'); id='eventdate' name='eventdate'></select>";
		if(recurdateslabel==null || "".equals(recurdateslabel))recurdateslabel="Select a date and time to attend";
		venueMap=DBHelper.getVenueCssMsgMap(venueid);
		if(venueMap.get("venuecss")!=null && !"".equals(venueMap.get("venuecss")))
			venuecss=venueMap.get("venuecss");
		if(venueMap.get("notavailimg")!=null && !"".equals(venueMap.get("notavailimg")))
			notavailimg=venueMap.get("notavailimg");
		if(venueMap.get("notavailmsg")!=null && !"".equals(venueMap.get("notavailmsg")))
			notavailmsg=venueMap.get("notavailmsg");
		if(venueMap.get("unassignimg")!=null && !"".equals(venueMap.get("unassignimg")))
			unassignimg=venueMap.get("unassignimg");
		if(venueMap.get("unassignmsg")!=null && !"".equals(venueMap.get("unassignmsg")))
			unassignmsg=venueMap.get("unassignmsg");
		
		VelocityContext vcontext = new VelocityContext();
 	    vcontext.put("eid",groupid);
 	    vcontext.put("serveraddress", serveraddress);
 	    vcontext.put("customcss", customcss);
 	    vcontext.put("time", time);
 	    vcontext.put("fbappid",fbappid);
 	    if(isrsvp){
 	    	vcontext.put("isRsvpEvent","Yes");
 	    }
 	   else{
 		  vcontext.put("isTicketingEvent","Yes");
 	   if(isrecurring){
 		  vcontext.put("recurreningSelect",recurringselect);
 		  vcontext.put("recurringdateslabel",recurdateslabel);
 	   }
 	   if(isseating)
 		  vcontext.put("isSeating", "Yes");
 	   }
 	   vcontext.put("venueid", venueid); 
	   if(!"".equals(venuecss) && venuecss!=null) 
		vcontext.put("venuecss", venuecss);
	   vcontext.put("trackcode", trackcode);
	   vcontext.put("discountcode", discountcode);
	   vcontext.put("ticketurlcode", ticketurlcode);
	   vcontext.put("context", context);
	   vcontext.put("fbsharepopup", fbsharepopup);
	   vcontext.put("eventstatus", eventstatus);
	   vcontext.put("isseatingevent", isseatingevent);
	   vcontext.put("notavailimg", notavailimg);
	   vcontext.put("notavailmsg", notavailmsg);
	   vcontext.put("unassignimg", unassignimg);
	   vcontext.put("unassignmsg", unassignmsg);
	   vcontext.put("ntsenable", ntsenable);
	   vcontext.put("ntscommission", ntscommission);
	   vcontext.put("referralntscode", ntscode);
	   vcontext.put("loginpopup", loginpopup);
	   
	   
	   VelocityEngine ve= new VelocityEngine(); 
	   String tktwdgttemplate=configHash.get("global_template_ticketingwidget");
	   if(tktwdgttemplate==null || "".equals(tktwdgttemplate.trim()))
	   tktwdgttemplate=EventGlobalTemplates.get("global_template_ticketingwidget", "");
 	  try{
 	  ve.init();
 	  //System.out.println("sbf is:"+sbf.toString());
 	  boolean abletopares=ve.evaluate(vcontext,out,"ebeetemplate",tktwdgttemplate);
 	  sbf1=out.getBuffer();
 	  //System.out.println("sbf1 is:"+sbf1);
 	  }
 	  catch(Exception exp){
 	  System.out.println("Exception occured in evaluate velocity"+exp.getMessage());
 	  exp.printStackTrace();
 	  }  
		}catch(Exception e){
			System.out.println("Exception occured in RenderTicketsWidgetHTML"+e.getMessage());
			e.printStackTrace();
		}
 	    return sbf1.toString();
	}

	@Override
	public Boolean IsRenderable(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash) {
		// TODO Auto-generated method stub
		return true;
	}

}
