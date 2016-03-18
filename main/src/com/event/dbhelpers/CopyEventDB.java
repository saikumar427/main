package com.event.dbhelpers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

import com.event.beans.AddEventData;
import com.eventbee.beans.DateTime;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.general.*;
import com.eventbee.general.formatting.CurrencyFormat;

@SuppressWarnings("unused")
public class CopyEventDB{
	private static Logger log=Logger.getLogger(CopyEventDB.class);
	public static String copyEvent(String mgrId,String oldeid,String eventname,AddEventData addEventData,String ticketlevel,HashMap oldeventdata,String powertype){
		String newseqid = "";
		try{
		String seqIdQry="select nextval('seq_groupid')";   
		String configIdQry="select nextval('seq_configid')";
		newseqid=DbUtil.getVal(seqIdQry,null);
		String newConfigId=DbUtil.getVal(configIdQry, null);
		newseqid=GenUtil.getUniqueId(newseqid);
		//String powertype=EventDB.getPowerType(oldeid);
		System.out.println("addEventData.getUpgradeLevel: "+addEventData.getUpgradeLevel());
		copyEvent(mgrId,oldeid,eventname,newseqid,newConfigId,addEventData,ticketlevel,oldeventdata,powertype);
		}catch(Exception ex){
			System.out.print("Exception: "+ex+"\n");
		}
		
		return newseqid;      
	}
	public static String getEventName(String mgrId,String eid){
		String eventname = DbUtil.getVal("select eventname from eventinfo where" +
				" eventid = CAST(? AS BIGINT)",new String[]{eid});
		return eventname;
	}
	public static void copyEvent(String mgrId,String oldEventId,String newEventName,String newSeqId,String newConfigId,AddEventData addEventData,String ticketlevel,HashMap oldeventdata,String powertype){
		/*Copy Eventinfo*/
		copyEventInfo(mgrId, oldEventId, newEventName, newSeqId, newConfigId,addEventData,ticketlevel,oldeventdata,powertype);
		
		//String confightmlcss= getConfigLooknFeel(oldEventId);
		
		/*Copy Config*/	
		copyEventConfig(oldEventId, newConfigId, addEventData.getUpgradeLevel());
		
		/*Copy Custom Attributes*/
		if("150".equals(addEventData.getUpgradeLevel()) || "300".equals(addEventData.getUpgradeLevel()) || "400".equals(addEventData.getUpgradeLevel()))
			copyEventCustomAttributes(oldEventId,newSeqId,powertype);
		
		/*Copy RSVP Attribs*/
		copyRsvpAttribs(oldEventId,newSeqId);
		
		/*Copy RSVP Settings*/
		copyRsvpSettings(oldEventId,newSeqId);
		
		/*Copy Event Curency and PaymentSettings*/
		copyEventCurrencyAndPaymentTypes(oldEventId,newSeqId,newConfigId,addEventData.getUpgradeLevel());
		
		/*Copy Event Look and Feel*/
		//copyEventLookAndFeel(oldEventId,newSeqId); //in new UI this is moved to Layout Page
		
		/*Copy Event Wording*/
		if("150".equals(addEventData.getUpgradeLevel()) || "200".equals(addEventData.getUpgradeLevel()) || "300".equals(addEventData.getUpgradeLevel()) || "400".equals(addEventData.getUpgradeLevel()))
			copyDisplayAttribs(oldEventId,newSeqId,addEventData.getUpgradeLevel());
		
		/*Copy Base Profile Questions*/
		copyBaseProfileQuestions(oldEventId,newSeqId);
		
		/*Copy Discount Codes*/
		if("200".equals(addEventData.getUpgradeLevel()) || "300".equals(addEventData.getUpgradeLevel()) || "400".equals(addEventData.getUpgradeLevel()))
			copyDiscountCodesMaster(oldEventId,newSeqId);
		
		 /*Copy Tickets*/
		copyEventTickets(oldEventId,newSeqId,addEventData,oldeventdata);
		
		//copyEventRollerThemes(oldEventId,newSeqId,mgrId,addEventData.getUpgradeLevel(),confightmlcss); //in new UI this is not required
		
		 
		if("150".equals(addEventData.getUpgradeLevel()) || "300".equals(addEventData.getUpgradeLevel()) || "400".equals(addEventData.getUpgradeLevel())){
			/*Copy Event EmailTemplates*/
			copyEventEmailTemplates(oldEventId,newSeqId);
			
			/*Copy Email Attendees*/
			copyEventMailList(newSeqId,mgrId,newEventName,"EVENT",oldEventId,newConfigId,addEventData);
			
			copyConfirmationPageSettings(oldEventId,newSeqId);
		}
		
		if("300".equals(addEventData.getUpgradeLevel()) || "400".equals(addEventData.getUpgradeLevel()))
			copyEventSeating(oldEventId,newSeqId);
		if("400".equals(addEventData.getUpgradeLevel()))
			copyBuyerLayoutPageSettings(oldEventId,newSeqId,addEventData.getUpgradeLevel());
		
		copyLayoutPageSettings(oldEventId,newSeqId,addEventData.getUpgradeLevel());
	}
	public static boolean copyEventInfo(String mgrId,String oldEventId,String newEventName,String newSeqId,String newConfigId,AddEventData addEventData,String ticketlevel,HashMap oldeventdata,String powertype){
		try{
			log.info("copyEventInfo powertype: "+powertype);
			log.info("Old EventId:"+oldEventId+"New EventId:"+newSeqId+"New EventName"+newEventName+"New ConfigId"+newConfigId);
			
			String endyear = addEventData.getEndyear().trim();
			String endmonth = addEventData.getEndmonth().trim();
			String endday = addEventData.getEndday().trim();
			String endhour = addEventData.getEndhour();
			String endminute = addEventData.getEndminute();
			String endampm = addEventData.getEndampm();
			
			String startyear = addEventData.getStartyear().trim();
			String startmonth = addEventData.getStartmonth().trim();
			String startday = addEventData.getStartday().trim();
			String starthour = addEventData.getStarthour();
			String startminute = addEventData.getStartminute();
			String startampm = addEventData.getStampm();
			
		String neweventstartdate=startyear+"-"+startmonth+"-"+startday;
    	String neweventenddate=endyear+"-"+endmonth+"-"+endday;
		String sttime=DateConverter.getHH24(starthour,startampm);
		String neweventstarttime=sttime+":"+startminute;
		String endtime=DateConverter.getHH24(endhour,endampm);
		String neweventendtime=endtime+":"+endminute;
		
		String eventstatus=(String)oldeventdata.get("eventstatus");
		//begin for enddate_est
		DateTimeBean dtb=new DateTimeBean();
		if("Y".equalsIgnoreCase(eventstatus)){
			endyear=startyear;
			endmonth=startmonth;
			endday=startday;
        }
		dtb.setYyPart(endyear);
        dtb.setMonthPart(endmonth);
        dtb.setDdPart(endday);
		dtb.setHhPart(endhour);
        dtb.setMmPart(endminute);
        dtb.setAmpm(endampm);
        String userTimeZone=EventDB.getEventTimeZone(oldEventId);
        String toTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		DateConverter dc=new DateConverter(userTimeZone,toTimeZone);
        boolean statusdc=dc.convertDateTime(dtb);
        String enddate_est=dc.getDatePart()+" "+dc.getTimePart();
        //end for enddate_est
		String accounttype=(String)oldeventdata.get("accounttype");
		String featurelevel="";
		if(powertype.equals("RSVP"))
			featurelevel=getRSVPTicketingType(oldEventId);
		else
			featurelevel=getTicketingType(oldEventId,null);
		
		DbUtil.executeUpdateQuery("update eventinfo set feature_type=? where eventid=CAST(? AS BIGINT)", new String[]{featurelevel,oldEventId});
		String featuretype=featurelevel;
		
		String[] inputparams;
		HashMap<String,String> levelfeemap=new HashMap<String,String>();
		levelfeemap=getLevelServiceFeeMap(powertype,mgrId,oldEventId,newSeqId,accounttype,addEventData);
		String servicefee=levelfeemap.get("currentfee");
		featurelevel=levelfeemap.get("currentlevel");
		if(powertype.equals("RSVP")){
			if(servicefee==null || "".equalsIgnoreCase(servicefee))servicefee="0";
			if(featurelevel==null || "".equalsIgnoreCase(featurelevel))featurelevel="90";
		}else{
		if(servicefee==null || "".equalsIgnoreCase(servicefee))servicefee="1";
		if(featurelevel==null || "".equalsIgnoreCase(featurelevel))featurelevel="100";
		}	
		/*String servicefee=DbUtil.getVal("select l"+featurelevel+" from ebee_special_fee where userid=?",new String[]{mgrId});
	    if(servicefee==null) {
	    	if("basic".equalsIgnoreCase(accounttype)|| accounttype==null){
	    		servicefee=DbUtil.getVal("select l"+featurelevel+" from ebee_special_fee where userid='0' and eventid='0'",null);
		     }else{
		    	 if(powertype.equals("RSVP")){
		    		 servicefee="0";
			    	 featurelevel="150";
		    	 }else{
			    	 servicefee="1.00";
			    	 featurelevel="300";
		    	 }
		     }
	    }
	    
	    servicefee=CurrencyFormat.getCurrencyFormat("",servicefee,false);*/
		String EventInfoQry="insert into eventinfo(eventname, eventid, config_id, unitid,code, mgr_id," +
		" category, description, address1, address2, city, state, country, start_date,end_date, starttime," +
		" endtime, update_desc, internalcomments, mgr_st_desc, ebee_st_desc, email,phone, event_type," +
		" comments, evt_level, price_id, status, created_by, created_at, updated_by, " +
		" listebee, role, listtype, pblview_ok, type,  photourl, tags,descriptiontype, attendeepagephoto," +
		" venue, latitude, longitude, region, showlogin, nts_enable, nts_commission,current_level,current_fee,feature_type,enddate_est,currency_update_date)	" +
		" select ?,CAST(? AS BIGINT),CAST(? AS INTEGER), unitid, code, mgr_id," +
		" category,description, address1, address2, city, state, country, to_date(?,'YYYY-MM-DD'),to_date(?,'YYYY-MM-DD'),?," +
		" ?,update_desc, internalcomments, mgr_st_desc, ebee_st_desc, email, phone, event_type, " +
		" comments,evt_level, price_id, 'ACTIVE', 'Copy Event-"+oldEventId+"', now(), updated_by," +
		" listebee, role,listtype, pblview_ok, type,photourl, tags, descriptiontype, attendeepagephoto," +
		" venue, latitude, longitude, region, showlogin, nts_enable,10,?,CAST(? as NUMERIC),?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),now()" +
		" from eventinfo where eventid = CAST(? AS BIGINT)";
		//checking for recurring event
		if("Y".equalsIgnoreCase(eventstatus))
		 inputparams=new String[]{newEventName,newSeqId,newConfigId,neweventstartdate,neweventstartdate,neweventstarttime,neweventendtime,featurelevel,servicefee,featuretype,enddate_est,oldEventId};
		else
		 inputparams=new String[]{newEventName,newSeqId,newConfigId,neweventstartdate,neweventenddate,neweventstarttime,neweventendtime,featurelevel,servicefee,featuretype,enddate_est,oldEventId};	
		DbUtil.executeUpdateQuery(EventInfoQry, inputparams);
		AddEventDB.insertEventHitSummary(newSeqId);
		if("Y".equalsIgnoreCase(eventstatus)){    
			try{
			String starttime=addEventData.getStarthour()+":"+addEventData.getStartminute()+" "+addEventData.getStampm();
			String ettime=addEventData.getEndhour()+":"+addEventData.getEndminute()+" "+addEventData.getEndampm();
			
			String zoneStartTime24hours=neweventstarttime;
			String zoneEndTime24hours=neweventendtime;
			String []eststarttimes=AddEventDB.getEstDates(starttime,neweventstartdate,userTimeZone);
			String []estendtimes=AddEventDB.getEstDates(ettime,neweventstartdate,userTimeZone);
			String datekey=neweventstartdate+"/"+neweventstarttime+"/"+starttime+"/"+neweventstartdate+"/"+neweventendtime+"/"+ettime;
			DateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf=new SimpleDateFormat("EEE dd MMM yyyy");
			java.util.Date date=new java.util.Date();
			date=formatter.parse(neweventstartdate);
			String[] uriarr=GenUtil.strToArrayStr(sdf.format(date), " ");
			String displaydate=uriarr[1]+" "+uriarr[2]+" "+uriarr[3]+" "+starttime+"-"+ettime+" "+"("+uriarr[0]+")";
			String eventdatesqry="insert into event_dates(eventid,status,zone_startdate,zone_enddate,zone_start_time,zone_end_time,est_startdate,est_enddate,est_start_time,est_end_time,eventdate,date_display,date_key) values(CAST(? AS BIGINT),?,to_date(?,'YYYY MM DD'),to_date(?,'YYYY MM DD'),?,?,to_date(?,'YYYY MM DD'),to_date(?,'YYYY MM DD'),?,?,to_date(?,'YYYY MM DD'),?,?)" ;
			DbUtil.executeUpdateQuery(eventdatesqry, new String[]{newSeqId,"ACTIVE",neweventstartdate,neweventstartdate,zoneStartTime24hours,zoneEndTime24hours,eststarttimes[0],estendtimes[0],eststarttimes[1],estendtimes[1],neweventstartdate,displaydate,datekey});
		    
		}catch(Exception e){
			System.out.println("Exception occured in recurringevent copy:"+e.getMessage());
		}
		}
		//vcal start
		String description="";
        String city="";
		String vcalselectqry="select description, city from eventinfo where eventid =CAST(? AS BIGINT)";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(vcalselectqry, new String[]{newSeqId});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
			description=dbmanager.getValue(0, "description","");
			city=dbmanager.getValue(0, "city","");
		}
        HashMap vcal=new HashMap();
		vcal.put(VCal.ID,newSeqId);
		vcal.put(VCal.PURPOSE,"event");
		vcal.put(VCal.DESCRIPTION,description);
		vcal.put(VCal.NAME,newEventName);
		vcal.put(VCal.LOCATION,city);

		DateTime stime=new DateTime(startyear,startmonth,startday,starthour,startminute,startampm);
		DateTime etime=new DateTime(endyear,endmonth,endday,endhour,endminute,endampm);
		stime.setHour12(starthour);
		stime.setAmpm(startampm);
		etime.setHour12(endhour);
		etime.setAmpm(endampm);
		vcal.put(VCal.STARTTIME,stime.getDateTimeForVCal(userTimeZone));
		
		vcal.put(VCal.ENDTIME,etime.getDateTimeForVCal(userTimeZone));
		(new Thread(new VCalThread(vcal))).start();
		System.out.println(":::: vcal in copy event :::::");
        //vcal end
		}catch(Exception e){
			System.out.println("RException in copyEventInfo"+e+"\n");
		}
		
		return true;
	}
	public static boolean copyEventConfig(String oldEventId,String newConfigId,String upgradeLevel){
		log.info("Entered in to copyEventConfig ");
		try{
			String name_not_in="'event.maillist.id','','event.volumetickets.show','event.FBRSVPList.show','event.FBRSVPList.eventid'";// should not copy as these are event specific
			
			if("90".equals(upgradeLevel)) //should not copy for rsvp if it is basic
				name_not_in+=",'event.fbcomment.show','event.twitter.show','event.googleplusone.show','event.fblike.show'";
			
			if(("90".equals(upgradeLevel) || "100".equals(upgradeLevel) || "200".equals(upgradeLevel))){
				name_not_in+=",'event.seating.venueid','event.seating.enabled','event.pdfticket.enable','event.pdfticketpending.enable','event.sendmailtoattendee','event.sendmailtomgr','registration.email.cc.list'";//these are all Advanced
				
				if(!"200".equals(upgradeLevel)) name_not_in+=",'timeout','event.reg.eventlevelcheck.count'"; //these are Pro
				
				/*if("yes".endsWith(confightmlcss))
					name_not_in+=",'event.theme.type'";*/
			}
			String insertConfigValuesQry="insert into config(config_id, name, value) " +
					"select CAST(? AS INTEGER), name,value from config a,eventinfo b where" +
					" a.config_id =b. config_id and b.eventid = CAST(? AS BIGINT) and " +
					"name not in ("+name_not_in+");";
			String[] inputparams=new String[]{newConfigId,oldEventId};
			DbUtil.executeUpdateQuery(insertConfigValuesQry, inputparams);
		}
		catch(Exception e){
			System.out.println(" Exception in copyEventConfig"+e+"\n");
		}
		return true;
	}
	public static boolean copyEventCustomAttributes(String oldEventId,String newSeqId,String powertype){
		boolean result=true;
		log.info("Entered in to copyEventCustomAttributes ");
		try{
		String attribSetidQry="select nextval('attrib_set_id')";
		String newAttribsetid =DbUtil.getVal(attribSetidQry, null);
		String customAttribMasterQry="insert into custom_attrib_master(purpose, groupid, attrib_setid, created) " +
				"select purpose, CAST(? AS BIGINT), CAST(? AS INTEGER),now()" +
				" from custom_attrib_master where groupid = CAST(? AS BIGINT);";
		String[] inputparams=new String[]{newSeqId,newAttribsetid,oldEventId};
		DbUtil.executeUpdateQuery(customAttribMasterQry, inputparams);
	 	String customAttribsQry="insert into custom_attribs(attrib_setid, attrib_id, rows,cols, textboxsize," +
	 			" attribname, attribtype, isrequired,position) select CAST(? AS INTEGER)," +
	 			" attrib_id, rows, cols, textboxsize, attribname,attribtype,isrequired,position from	" +
	 			" custom_attribs a ,custom_attrib_master b where a.attrib_setid=b.attrib_setid and" +
	 			" b.groupid = CAST(? AS BIGINT)";
	 	String[] inputparams1=new String[]{newAttribsetid,oldEventId};
		DbUtil.executeUpdateQuery(customAttribsQry, inputparams1);		 
	 	String customAttribOptionsQry="insert into custom_attrib_options(attrib_setid,attrib_id,option, " +
	 			"option_val,position) select CAST(? AS INTEGER),attrib_id,option," +
	 			"option_val,position from custom_attrib_options a,custom_attrib_master b " +
	 			"where a.attrib_setid=b.attrib_setid and b.groupid = CAST(? AS BIGINT)";
	 	String[] inputparams2=new String[]{newAttribsetid,oldEventId};
		DbUtil.executeUpdateQuery(customAttribOptionsQry, inputparams2);		
	 	copyAttendeeListAttribs(oldEventId,newSeqId,newAttribsetid);
	 	copyConfirmationScreenAttribs(oldEventId,newSeqId);
	 	if(powertype.equals("RSVP"))
	 		copyRSVPsubgroupattribs(oldEventId,newSeqId,newAttribsetid);
		}
		catch (Exception e){
			System.out.println(" Exception in copyEventCustomAttributes"+e+"\n");
		}
		return result;
	}
	public static boolean copyAttendeeListAttribs(String oldEventId,String newSeqId,String newAttribsetid){
		log.info("Entered in to copyAttendeeListAttribs");
		boolean result=true;
		try{
		
		String attListAttribQry="INSERT INTO attendeelist_attributes(eventid, position, attrib_setid, attribname," +
				" created_at, updated_at,attrib_id) select CAST(? AS BIGINT),position,CAST(? AS INTEGER)," +
				"attribname,created_at, updated_at,attrib_id from attendeelist_attributes where  " +
				" eventid = CAST(? AS BIGINT)";
		String[] inputparams=new String[]{newSeqId,newAttribsetid,oldEventId};
		DbUtil.executeUpdateQuery(attListAttribQry, inputparams);
		}
		catch(Exception e){
			System.out.println(" Exception in copyAttendeeListAttribs"+e+"\n");
		}
		return result;
	}
	
	public static boolean copyConfirmationScreenAttribs(String oldEventId,String newSeqId){
		log.info("Entered in to copyConfirmationScreenAttribs");
		boolean result=true;
		try{
		String confirmScreenAttribQry="insert into confirmationscreen_questions(eventid,position,attrib_name," +
			" created_at,updated_at,attrib_id,context,type) select ?,position,attrib_name,created_at,updated_at,attrib_id,context,type  from "+
			" confirmationscreen_questions where eventid=?";
		String[] inputparams=new String[]{newSeqId,oldEventId};
		DbUtil.executeUpdateQuery(confirmScreenAttribQry, inputparams);
		}catch(Exception e){
			System.out.println(" Exception in copyConfirmationScreenAttribs"+e+"\n");
		}
		return result;
	}
	
	public static boolean copyRsvpAttribs(String oldEventId,String newSeqId){
		boolean result=true;
		log.info("Entered in to copyRsvpAttribs ");
		try{
		String RSVPattribsQry="insert into rsvp_attribs(eventid,attrib_id,rsvp_status) " +
				"select CAST(? AS BIGINT),attrib_id,rsvp_status from rsvp_attribs" +
				" where eventid=CAST(? AS BIGINT)";
		String[] inputparams=new String[]{newSeqId,oldEventId};
		DbUtil.executeUpdateQuery(RSVPattribsQry, inputparams);
		}
		catch(Exception e){
			System.out.println(" Exception in copyRsvpAttribs"+e+"\n");
		}
		return result;
	}
	public static boolean copyBaseProfileQuestions(String oldEventId,String newSeqId){
		boolean result=true;
		log.info("Entered in to copyBaseProfileQuestions ");
		try{
		 /* Copy everything as is and then update with new ticketids in copyEventTickets func.*/
		String baseProfileQnsQry="insert into base_profile_questions(groupid,contextid,attribid,isrequired ) " +
				"select CAST(? AS BIGINT),contextid,attribid,isrequired from base_profile_questions " +
				"where groupid=CAST(? AS BIGINT);";
		String[] inputparams=new String[]{newSeqId,oldEventId};
		DbUtil.executeUpdateQuery(baseProfileQnsQry, inputparams);
		}
		catch(Exception e){
			System.out.println(" Exception in copyBaseProfileQuestions"+e+"\n");
		}
		return result;
	}
	public static boolean copyEventCurrencyAndPaymentTypes(String oldEventId,String newSeqId,String newConfigId,String upgradeLevel){
		boolean result=true;
		log.info("Entered in to copyEventCurrencyAndPaymentTypes ");
		try{
		String evtCurrencyQry="insert into event_currency (eventid, currency_code) select '"+newSeqId+"', currency_code" +
				" from event_currency where eventid = ?";
		String[] inputparams=new String[]{oldEventId};
		DbUtil.executeUpdateQuery(evtCurrencyQry, inputparams);
		String payTypesQry="insert into payment_types (userid, refid, purpose,paytype, status, attrib_1, " +
				"attrib_2, attrib_3, attrib_4, attrib_5, other_ccfee) select userid,'"+newSeqId+"', purpose," +
				"paytype, status, attrib_1, attrib_2, attrib_3, attrib_4, attrib_5, other_ccfee from " +
				"payment_types where refid = ? and paytype<>'google' and status<>'disable'";
		String[] inputparams1=new String[]{oldEventId};
		DbUtil.executeUpdateQuery(payTypesQry, inputparams1);
		
		String paypalPaymentQuery="insert into paypalx_events(eventid,paymentoption,servicefee) select '"+newSeqId+"',paymentoption,servicefee from "+
		               "paypalx_events where eventid = ?::bigint";
		DbUtil.executeUpdateQuery(paypalPaymentQuery,new String[]{oldEventId});
		
		//logic for ccprocessingfee and tax
		String ccprocessingfee="";
		String taxtype=DbUtil.getVal("select value from config where name='event.tax.type' and config_id=CAST(? AS INTEGER)",new String[]{newConfigId});
		if(taxtype==null)taxtype="";
		String tax=EventDB.getConfigVal(oldEventId,"event.tax.amount","");
		HashMap<String,String> taxAndCCProFee=taxAndCCProFee(oldEventId);
		if("".equals(tax)){
		  //taxccprocessmap=PaymentSettingsDB.getTaxAndCCProcessFee(oldEventId);
			if(!taxAndCCProFee.isEmpty()){
				tax=taxAndCCProFee.get("tax");
				ccprocessingfee=taxAndCCProFee.get("ccprofee");
			}else{
				tax="0";
				ccprocessingfee="0";
			}
		  
		  if(!"bymanager".equals(taxtype))
		  DbUtil.executeUpdateQuery("insert into config(name,value,config_id) values('event.tax.amount',?,CAST(? AS INTEGER))", new String[]{tax+"+"+ccprocessingfee,newConfigId});
		  if("".equals(taxtype))
		     DbUtil.executeUpdateQuery("insert into config(name,value,config_id) values('event.tax.type',?,CAST(? AS INTEGER))", new String[]{"byattendee",newConfigId});
		  else
			 DbUtil.executeUpdateQuery("update config set value=? where name='event.tax.type' and config_id=CAST(? AS INTEGER)", new String[]{taxtype,newConfigId}); 
		}else{
			try{
				String[] uriarr=GenUtil.strToArrayStr(tax, "+");
			  	tax=uriarr[0];
			  	ccprocessingfee=uriarr[1];
			  	if("100".equals(upgradeLevel)){
			  		if(!taxAndCCProFee.isEmpty()){
			  			if(Float.parseFloat(tax)>Float.parseFloat(taxAndCCProFee.get("tax")))
			  				tax=taxAndCCProFee.get("tax");
			  			if(Float.parseFloat(ccprocessingfee)>Float.parseFloat(taxAndCCProFee.get("ccprofee")))
			  				ccprocessingfee=taxAndCCProFee.get("ccprofee");
					}else{
						tax="0";
						ccprocessingfee="0";
					}
			  	}
			}catch(Exception e){
					tax="0";
					ccprocessingfee="0";	
			}
			if("".equals(ccprocessingfee)) ccprocessingfee="0";
         if(!"bymanager".equals(taxtype)){		
		 DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name='event.tax.amount'", new String[]{newConfigId});
		 DbUtil.executeUpdateQuery("insert into config(name,value,config_id) values('event.tax.amount',?,CAST(? AS INTEGER))", new String[]{tax+"+"+ccprocessingfee,newConfigId});
         }
		 if("".equals(taxtype))
			 DbUtil.executeUpdateQuery("insert into config(name,value,config_id) values('event.tax.type',?,CAST(? AS INTEGER))", new String[]{"byattendee",newConfigId}); 
		 else
			 DbUtil.executeUpdateQuery("update config set value=? where name='event.tax.type' and config_id=CAST(? AS INTEGER)", new String[]{taxtype,newConfigId});
		 
		 }
		//end logic for tax and ccprocessingfee
		}
		catch(Exception e){
			System.out.println(" Exception in copyEventCurrencyAndPaymentTypes"+e+"\n");
		}
		return result;
	}
	/*public static boolean  copyEventLookAndFeel(String oldEventId,String newSeqId){
		boolean result=true;
		log.info("Entered in to copyEventLookAndFeel ");
		try{
		String looknFeelQry="insert into configure_looknfeel (refid, unitid, idtype,pagetitle, headerhtml, " +
				"postloginheaderhtml,navhtml, footerhtml)	select '"+newSeqId+"', unitid, idtype, pagetitle,headerhtml, " +
				"postloginheaderhtml, navhtml, footerhtml	from configure_looknfeel where refid = ?;";
		String[] inputparams=new String[]{oldEventId};
		DbUtil.executeUpdateQuery(looknFeelQry, inputparams);
		}
		catch(Exception e){
			System.out.println(" Exception in copyEventLookAndFeel"+e+"\n");
		}
		return result;
	}*/
	public static boolean  copyEventEmailTemplates(String oldEventId,String newSeqId){
		boolean result=true;
		log.info("Entered in to copyEventEmailTemplates ");
		try{
		String templatesQry="insert into custom_email_templates (groupid,purpose,fromemail, replytoemail," +
				" textformat, htmlformat, subjectformat, header, footer, headertype,footertype) " +
				"select '"+newSeqId+"', purpose, fromemail, replytoemail, textformat, htmlformat,subjectformat,header, " +
				"footer, headertype, footertype from custom_email_templates where groupid = ?";
		String[] inputparams=new String[]{oldEventId};
		DbUtil.executeUpdateQuery(templatesQry, inputparams);
		String evtTagsQry="insert into event_tags (eventid,tags) select '"+newSeqId+"',tags from event_tags where " +
				"eventid = ?;";		 
		String[] inputparams1=new String[]{oldEventId};
		DbUtil.executeUpdateQuery(evtTagsQry, inputparams1);
		}
		catch(Exception e){
			System.out.println(" Exception in copyEventEmailTemplates"+e+"\n");
		}
		return result;
	}
	public static boolean copyDisplayAttribs(String oldEventId,String newSeqId,String upgradeLevel){
		boolean result=true;
		log.info("Entered in to copyDisplayAttribs");
		try{
			String dispAttribsQry="INSERT INTO custom_event_display_attribs(eventid, data, last_modified) " +
					"select CAST(? AS BIGINT),data,now() from custom_event_display_attribs " +
					"where eventid = CAST(? AS BIGINT)";
			String[] inputparams=new String[]{newSeqId,oldEventId};
		    DbUtil.executeUpdateQuery(dispAttribsQry, inputparams);
		    
		    String ticketsdisplayformats="insert into ticketsavailability_display_formats(eventid,formatid,display_format) select '"+newSeqId+"'," +
		    		"formatid,display_format from ticketsavailability_display_formats where eventid=?";
		    if(!"100".equals(upgradeLevel))
		    	DbUtil.executeUpdateQuery(ticketsdisplayformats, new String[]{oldEventId});
		}
		catch(Exception e){
			System.out.println(" Exception in copyDisplayAttribs"+e+"\n");
		}
		return result;
	}
	/*public static boolean copyEventRollerThemes(String oldEventId,String newSeqId,String mgrId,String upgradeLevel,String confightmlcss){
		boolean result=true;
		log.info("Entered in to copyEventRollerThemes ");
		try{
			
			String userCustomRollerQry="insert into user_custom_roller_themes(userid,themecode,module,content,cssurl," +
					"refid) select CAST(? AS INTEGER),themecode,module,content,cssurl,? from" +
					" user_custom_roller_themes where refid = ?";
			String[] inputparams=new String[]{mgrId,newSeqId,oldEventId};
			
			String userRollerThemes="insert into user_roller_themes(userid,themecode,module,cssurl,themetype,refid) " +
					"select CAST(? AS INTEGER),themecode,module,cssurl,themetype,? from user_roller_themes" +
					" where refid = ?";
			String[] inputparams1=new String[]{mgrId,newSeqId,oldEventId};
			
			if("150".equals(upgradeLevel) || "300".equals(upgradeLevel)){
				DbUtil.executeUpdateQuery(userCustomRollerQry, inputparams);
				DbUtil.executeUpdateQuery(userRollerThemes,inputparams1);
			}else if(!"yes".equals(confightmlcss))
				DbUtil.executeUpdateQuery(userRollerThemes,inputparams1);
		}
		catch(Exception e){
			System.out.println(" Exception in copyEventRollerThemes"+e+"\n");
		}
		return result;
	}*/
	public static boolean copyEventMailList(String newSeqId,String mgrId,String newEventName,String grpType,
			String oldEventId,String newConfigId,AddEventData addeventdata){
		boolean result=true;
		log.info("Entered in to copyEventMailList ");
		try{
		String listId=DbUtil.getVal("select nextval('seq_list')",null);
		String month=addeventdata.getStartmonth();
		String day=addeventdata.getStartday();
		String year=addeventdata.getStartyear();
		String listName="Attendee List-" +newEventName+"_"+month+" "+day+" "+year;
		System.out.println("the list name in mailing list is"+listName);
		String configQry="insert into config(config_id, name, value) values(CAST(? AS INTEGER)," +
				"'event.maillist.id',?);";
		DbUtil.executeUpdateQuery(configQry, new String[]{newConfigId,listId});
		String grpMailListQry="insert into group_mail_list (groupid ,list_id,group_type) select '"+newSeqId+"'," +
				"?,group_type from group_mail_list where groupid=?";
		String[] inputparams=new String[]{listId,oldEventId};
		DbUtil.executeUpdateQuery(grpMailListQry, inputparams);
		/*String mailListQry="insert into mail_list (list_id,list_desc,unit_id,role,list_name,manager_id,created_at," +
				"created_by,updated_at,updated_by,unsubmess) select '"+listId+"',list_desc,unit_id,role,'"+listName+"',manager_id," +
				"now(),created_by,updated_at,updated_by,unsubmess from mail_list a,group_mail_list b  where " +
				"a.list_id =to_number(b.list_id,'9999999999') and b.groupid = ?";
			String[] inputparams1=new String[]{oldEventId};
			DbUtil.executeUpdateQuery(mailListQry, inputparams1);
*/		
		System.out.println("not inserted into mail list when copy event action");
		}
		catch(Exception e){
			System.out.println(" Exception in copyEventMailList"+e+"\n");
		}
		return result;
	}
	public static boolean copyEventTickets(String oldEventId,String newSeqId,AddEventData addeventdata,HashMap oldeventdata){
		boolean result=true;
		log.info("Entered in to copyEventTickets ");
		try{
		String grpIdSelectQry="select ticket_groupid from event_ticket_groups where eventid = ?";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(grpIdSelectQry, new String[]{oldEventId});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
				String oldTktGrpId=dbmanager.getValue(k, "ticket_groupid","");
				String newTktGrpId=DbUtil.getVal("select nextval('Ticket_Groupid_Seq')",null);
                String currentdate=DateUtil.getCurrDBFormatDate();
				String grpInsert="insert into event_ticket_groups (eventid,ticket_groupid, groupname, position, " +
						"date, description, tickettype, grouptype, showname_yn,showdesc_yn ) select '"+newSeqId+"', " +
						"'"+newTktGrpId+"', groupname, position, to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'), description, tickettype, grouptype," +
						"showname_yn, showdesc_yn from event_ticket_groups where eventid =?	" +
						"and ticket_groupid = ?";
				DbUtil.executeUpdateQuery(grpInsert, new String[]{currentdate,oldEventId,oldTktGrpId});
				copyTicketsOfGroup(oldEventId,newSeqId,oldTktGrpId,newTktGrpId,addeventdata,oldeventdata);
			}
		}
		
		if("300".equals(addeventdata.getUpgradeLevel())|| "400".equals(addeventdata.getUpgradeLevel()) || "150".equals(addeventdata.getUpgradeLevel())){
			String buyerCustomQnsQry="insert into buyer_custom_questions (eventid,attribid)" +
					" select CAST(? AS BIGINT),attribid from buyer_custom_questions where " +
					" eventid = CAST(? AS BIGINT)"; 
			String[] inputparams5=new String[]{newSeqId,oldEventId};
			StatusObj stobj=DbUtil.executeUpdateQuery(buyerCustomQnsQry, inputparams5);
			System.out.println("buyerCustomQnsQry msg"+stobj.getErrorMsg());
		}
		}
		catch(Exception e){
			System.out.println(" Exception in copyEventTickets   "+e+"\n");
		}
		return result;
	}
	public static boolean copyTicketsOfGroup(String oldEventId,String newSeqId,String oldTktGrpId,String newTktGrpId,AddEventData addeventdata,HashMap oldeventdata){
		boolean result=true;
		log.info("Entered in to copyTicketsOfGroup ");
		try{
		System.out.println("copyTicketsOfGroup oldEventId:"+oldEventId+"      newSeqId:"+newSeqId);
		System.out.println("oldTktGrpId:"+oldTktGrpId+"      newTktGrpId:"+newTktGrpId);
		String attribSetidQry="select  attrib_setid from custom_attrib_master where" +
				" groupid = CAST(? AS BIGINT)";
		String attribSetId=DbUtil.getVal(attribSetidQry, new String[]{newSeqId});
		String oldTicketIdsQry="select price_id from group_tickets where ticket_groupid = ?";
		
		String endyear = addeventdata.getEndyear().trim();
		String endmonth = addeventdata.getEndmonth().trim();
		String endday = addeventdata.getEndday().trim();
		String endhour = addeventdata.getEndhour();
		String endminute = addeventdata.getEndminute();
		String endampm = addeventdata.getEndampm();
        
		String startyear = addeventdata.getStartyear().trim();
		String startmonth = addeventdata.getStartmonth().trim();
		String startday = addeventdata.getStartday().trim();
		String starthour = addeventdata.getStarthour();
		String startminute = addeventdata.getStartminute();
		String startampm = addeventdata.getStampm();
		String upgradeLevel=addeventdata.getUpgradeLevel();
		
	    //String newtktstartdate=startyear+"-"+startmonth+"-"+startday;
	    String currentdate=DateUtil.getCurrDBFormatDate();
		String newtktstartdate=currentdate;
		//String newtktenddate=endyear+"-"+endmonth+"-"+endday;
		String newtktenddate="";
	    String sttime=DateConverter.getHH24(starthour,startampm);
	    String newtktstarttime=sttime+":"+startminute;
	    String endtime=DateConverter.getHH24(endhour,endampm);
	    String newtktendtime=endtime+":"+endminute;
	    java.util.Date today=new java.util.Date();
        Format fm=new SimpleDateFormat("HH:mm");
        String timenow=fm.format(today);
		
	    String eventstatus=(String)oldeventdata.get("eventstatus");
	    String oldCurrentLevel=(String)oldeventdata.get("currentLevel");
		if("Y".equalsIgnoreCase(eventstatus)){ //for recurring event.
			newtktenddate=newtktstartdate;
		}else{
			DateTimeBean enddtbObj=new DateTimeBean();
			enddtbObj.setYyPart(endyear);
			enddtbObj.setMonthPart(endmonth);
			enddtbObj.setDdPart(endday);
			enddtbObj.setHhPart(endhour);
			enddtbObj.setMmPart(endminute);
			enddtbObj.setAmpm(endampm);
			
			String userTimeZone=EventDB.getEventTimeZone(oldEventId);
	        String toTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
			DateConverter dc=new DateConverter(userTimeZone,toTimeZone);
	        boolean statusdc=dc.convertDateTime(enddtbObj);
	        newtktenddate=dc.getDatePart();
	        newtktendtime=dc.getTimePart();
		}
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(oldTicketIdsQry, new String[]{oldTktGrpId});
		int count1=statobj.getCount();
		StatusObj stobj=null;
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
				String oldTicketId=dbmanager.getValue(k, "price_id","");
				String newTicketId=DbUtil.getVal("select nextval('seq_priceid')", null);
				String ticketInsertQry="insert into price(evt_id, price_id, ticket_name, description,ticket_price," +
						" start_date, end_date,max_ticket, ticket_category, sold_qty, min_qty, max_qty,ticket_type," +
						" starttime, endtime, status,networkcommission, process_fee,partnerlimit,start_before," +
						" end_before,start_hours_before,end_hours_before,start_minutes_before,end_minutes_before,isdonation," +
						" showyn,scan_code_required,created_by,wait_list_type,wait_list_max_qty) select CAST(? AS BIGINT), CAST(? AS INTEGER), " +
						" ticket_name,description, ticket_price, to_date(?,'yyyy-MM-dd'), to_date(?,'yyyy-MM-dd'),max_ticket, ticket_category," +
						" 0, min_qty,max_qty, ticket_type, ?,?, status,10," +
						" CASE WHEN '"+upgradeLevel+"'='"+oldCurrentLevel+"' THEN process_fee ELSE 0 END, partnerlimit,start_before,end_before,start_hours_before," +
						" end_hours_before,start_minutes_before,end_minutes_before,isdonation,CASE WHEN '"+ upgradeLevel+"'='100' THEN 'Y' ELSE showyn END," +
						" scan_code_required,created_by,CASE WHEN '"+ upgradeLevel+"'='300' OR '"+ upgradeLevel+"'='400' THEN  wait_list_type ELSE 'NO' END, CASE WHEN '"+ upgradeLevel+"'='300' OR '"+ upgradeLevel+"'='400'" +
						" THEN  wait_list_max_qty ELSE 0 END from price where evt_id = CAST(? AS BIGINT) and price_id = CAST(? AS INTEGER)";
				String[] inputparams=new String[]{newSeqId,newTicketId,newtktstartdate,newtktenddate,timenow,newtktendtime,oldEventId,oldTicketId};
				stobj=DbUtil.executeUpdateQuery(ticketInsertQry, inputparams);
				System.out.println("ticketInsertQry msg"+stobj.getErrorMsg());
				
				DbUtil.executeUpdateQuery("update price set networkcommission=0 where isdonation='Yes' and evt_id=CAST(? as INTEGER)",new String[]{newSeqId});
				
				String grpInsertQry="insert into group_tickets(ticket_groupid, price_id,position) " +
						"select '"+newTktGrpId+"','"+newTicketId+"',position from group_tickets " +
								"where ticket_groupid = ? and price_id =?";
				String[] inputparams1=new String[]{oldTktGrpId,oldTicketId};
				stobj=DbUtil.executeUpdateQuery(grpInsertQry, inputparams1);
				System.out.println("grpInsertQry msg"+stobj.getErrorMsg());
				String baseProfileTicketsInsertQry="insert into eventbaseprofiletickets (eventid,ticketid) " +
						"select CAST(? AS BIGINT),CAST(? AS BIGINT) " +
						"from eventbaseprofiletickets	where eventid=CAST(? AS BIGINT) and" +
						" ticketid = CAST(? AS BIGINT)";
				String[] inputparams2=new String[]{newSeqId,newTicketId,oldEventId,oldTicketId};
				stobj=DbUtil.executeUpdateQuery(baseProfileTicketsInsertQry, inputparams2);
				System.out.println("baseProfileTicketsInsertQry msg"+stobj.getErrorMsg());
				//Case A
				if(attribSetId!=null){
				String subgroupAttribInsertQry="insert into subgroupattribs(groupid, subgroupid, attribsetid, " +
						"attribid)	select CAST(? AS BIGINT),CAST(? AS INTEGER)," +
						"'"+attribSetId+"',attribid from subgroupattribs where groupid=CAST(? AS BIGINT)" +
						" and subgroupid=CAST(? AS INTEGER)";
				String[] inputparams3=new String[]{newSeqId,newTicketId,oldEventId,oldTicketId};
				stobj=DbUtil.executeUpdateQuery(subgroupAttribInsertQry, inputparams3);
				System.out.println("subgroupAttribInsertQry msg"+stobj.getErrorMsg());
				}
				String baseProfileQnUpdateQry="update base_profile_questions set " +
						"contextid=CAST(? AS INTEGER)	where " +
						"contextid=CAST(? AS INTEGER) and " +
						"groupid=CAST(? AS BIGINT)";
				String[] inputparams4=new String[]{newTicketId,oldTicketId,newSeqId};
				stobj=DbUtil.executeUpdateQuery(baseProfileQnUpdateQry, inputparams4);
				System.out.println("baseProfileQnUpdateQry msg"+stobj.getErrorMsg());
				
				String venueid=getVenue(newSeqId);
				if(venueid != null)
					copySeatingTickets(oldEventId,newSeqId,oldTicketId,newTicketId,venueid);
				
				
				String couponTicketsQry="insert into coupon_ticket(couponid,price_id) select couponid, "+
				"'"+newTicketId+"' from coupon_ticket_temp where price_id=CAST(? AS INTEGER) and eventid=?";
				DbUtil.executeUpdateQuery(couponTicketsQry, new String[]{oldTicketId,newSeqId});
				
				String ticketsfordisplayformats="insert into ticketsfordisplayformats(eventid,formatid,ticketid) " +
					"select '"+newSeqId+"',formatid,'"+newTicketId+"' from ticketsfordisplayformats where ticketid=CAST(? AS BIGINT) and eventid=?";
				if(!"100".equals(upgradeLevel))
					DbUtil.executeUpdateQuery(ticketsfordisplayformats, new String[]{oldTicketId,oldEventId});
				}//End of for loop
		
		}//End of if block.
		
		//Case B
		
		}
		catch(Exception e){
			System.out.println(" Exception in copyTicketsOfGroup   "+e+"\n");
		}
		/*
		 *Need to check both caseA and CaseB scenarios.  attribid 
		 */
	

		
		return result;
	}
	
	public static boolean copySeatingTickets(String oldEventId,String newSeqId,String oldTicketId,String newTicketId,String venueid){
		log.info("Entered in to copySeatingTickets ");
		try{
		String oldSeatGroupidsQry = "select seat_groupid, section_id  from seat_tickets where ticketid=CAST(? AS INTEGER) and eventid=CAST(? AS BIGINT)";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(oldSeatGroupidsQry, new String[]{oldTicketId,oldEventId});
		int count1=statobj.getCount();
		StatusObj stobj=null;
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
				String oldSeatGroupId=dbmanager.getValue(k, "seat_groupid","");
				String sectionid=dbmanager.getValue(k, "section_id","");
				if(sectionid.equals(""))
					sectionid=null;
				String colorQry = "select color from seat_groups where seat_groupid=CAST(? AS INTEGER) and eventid=CAST(? AS BIGINT)";
				DBManager dbmanager2=new DBManager();
				StatusObj statobj2=dbmanager2.executeSelectQuery(colorQry, new String[]{oldSeatGroupId,oldEventId});
				int count2=statobj2.getCount();
				if(statobj2.getStatus()){
					String color = dbmanager2.getValue(0, "color","");
					String selectseatgroupidqry = "select seat_groupid from seat_groups where color=? and eventid=CAST(? AS BIGINT)";
					DBManager dbmanager3=new DBManager();
					StatusObj statobj3=dbmanager3.executeSelectQuery(selectseatgroupidqry, new String[]{color,newSeqId});
					String newseatgroupid;
					if(statobj3.getStatus()){
						newseatgroupid = dbmanager3.getValue(0, "seat_groupid","");
						DbUtil.executeUpdateQuery("insert into seat_tickets(eventid,venue_id,section_id,seat_groupid,ticketid) values(CAST(? AS BIGINT),CAST(? AS INTEGER),CAST(? AS INTEGER),CAST(? AS INTEGER),CAST(? AS INTEGER))", new String[]{newSeqId,venueid,sectionid,newseatgroupid,newTicketId});
						DbUtil.executeUpdateQuery("insert into copy_event_track(old_eventid,new_eventid,old_seat_groupid,new_seat_groupid,old_ticketid,new_ticketid) values(?,?,CAST(? AS BIGINT),CAST(? AS BIGINT),CAST(? AS BIGINT),CAST(? AS BIGINT))", new String[]{oldEventId,newSeqId,oldSeatGroupId,newseatgroupid,oldTicketId,newTicketId});
					    }else{
						newseatgroupid=DbUtil.getVal("select nextval('seat_groupid_seq')",null);
						DbUtil.executeUpdateQuery("insert into seat_groups(eventid,venue_id,section_id,seat_groupid,color) values(CAST(? AS BIGINT),CAST(? AS INTEGER),CAST(? AS INTEGER),CAST(? AS INTEGER),?)", new String[]{newSeqId,venueid,sectionid,newseatgroupid,color});
						DbUtil.executeUpdateQuery("insert into seat_tickets(eventid,venue_id,section_id,seat_groupid,ticketid) values(CAST(? AS BIGINT),CAST(? AS INTEGER),CAST(? AS INTEGER),CAST(? AS INTEGER),CAST(? AS INTEGER))", new String[]{newSeqId,venueid,sectionid,newseatgroupid,newTicketId});
						DbUtil.executeUpdateQuery("insert into copy_event_track(old_eventid,new_eventid,old_seat_groupid,new_seat_groupid,old_ticketid,new_ticketid) values(?,?,CAST(? AS BIGINT),CAST(? AS BIGINT),CAST(? AS BIGINT),CAST(? AS BIGINT))", new String[]{oldEventId,newSeqId,oldSeatGroupId,newseatgroupid,oldTicketId,newTicketId});
					    
					}
				}
			}
		}
		}
		catch(Exception e){
			System.out.println(" Exception in copySeatingTickets   "+e+"\n");
		}
		return true;
	}
	
	public static boolean copyEventSeating(String oldEventId,String newSeqId){
		log.info("Entered in to copyEventSeating ");
		try{
			String venueid=getVenue(newSeqId);
			if(venueid != null){
				copySeatingTickets(oldEventId,newSeqId,"-100","-100",venueid);
				String selectQry = "select distinct old_seat_groupid,new_seat_groupid from copy_event_track where old_eventid=? and new_eventid=?";
				DBManager dbmanager=new DBManager();
				StatusObj statobj=dbmanager.executeSelectQuery(selectQry, new String[]{oldEventId,newSeqId});
				int count1=statobj.getCount();
				StatusObj stobj=null;
				if(statobj.getStatus() && count1>0){
					for(int i=0;i<count1;i++){
						String old_seat_groupid = dbmanager.getValue(i, "old_seat_groupid","");
						String new_seat_groupid = dbmanager.getValue(i, "new_seat_groupid","");
						String inserQry = "insert into event_seating (venue_id,section_id,seat_groupid,eventid,seatindex) select venue_id,section_id,CAST(? AS INTEGER),CAST(? AS BIGINT),seatindex " +
							"from event_seating where seat_groupid=CAST(? AS INTEGER) and eventid=CAST(? AS BIGINT)";
						DbUtil.executeUpdateQuery(inserQry, new String[]{new_seat_groupid,newSeqId,old_seat_groupid,oldEventId});
					    
					}
				}
			}
		}catch(Exception e){
			System.out.println(" Exception in copyEventSeating   "+e+"\n");
		}
		return true;
	}
	
	public static String getVenue(String eid){
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid});
		String venueid=DbUtil.getVal("select value from config where config_id=CAST(? AS INTEGER) and name='event.seating.venueid'", new String[]{configid});
		return venueid;
	}
	public static String getCurrentFee(String eid, String mgrid,String currentLevel){
		String powertype=EventDB.getPowerType(eid),currentfee="",currency="",ttype=currentLevel;
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		try{
		currency=DbUtil.getVal("select currency_code from event_currency  where eventid=?", new String[]{eid});
		String nonprofitstatus=DbUtil.getVal("select 'Y' from ebee_special_fee where eventid=?", new String[]{eid});
		if(nonprofitstatus==null)nonprofitstatus=DbUtil.getVal("select 'Y' from ebee_special_fee where userid::BIGINT=(select mgr_id from eventinfo where eventid=CAST(? as BIGINT))", new String[]{eid});
		if(nonprofitstatus==null)nonprofitstatus="N";
		HashMap<String,String> evtmap=new HashMap<String,String>();
		if("Y".equalsIgnoreCase(nonprofitstatus))currentLevel="ln"+currentLevel;
		else currentLevel="l"+currentLevel;
		if("Ticketing".equalsIgnoreCase(powertype)&& !"100".equalsIgnoreCase(ttype)){
			currentfee=DbUtil.getVal("select "+currentLevel+" from international_pricing where currency_code=?", new String[]{currency});	
			}
		else if("RSVP".equalsIgnoreCase(powertype))
			currentfee=DbUtil.getVal("select "+currentLevel+" from international_pricing where currency_code='USD'", null);
		}catch(Exception e){
			System.out.println("Exception occured in copy event "+eid+" :: "+e.getMessage());
		}
		if("Ticketing".equalsIgnoreCase(powertype)&& "".equalsIgnoreCase(currentfee)&& currentfee==null){
			currentfee="1";
		}
		return currentfee;
	}
	public static HashMap<String,String> getDates(String eventid,String accounttype){
		HashMap<String,String> getdates=new HashMap<String,String>();
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		//CurrencyFormat cf=new CurrencyFormat();
		String eventstatusquery="select value from config where config_id in (select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.recurring'";
		String eventstatus=DbUtil.getVal(eventstatusquery,new String[]{eventid});
		if(eventstatus==null) eventstatus="N";
		String olddatesquery="select start_date,end_date,feature_type,current_fee,current_level,mgr_id from eventinfo where eventid=CAST(? AS BIGINT)";
		statobj=dbm.executeSelectQuery(olddatesquery, new String[]{eventid});
		if(statobj.getStatus() && statobj.getCount()>0){
			String[] startdate=GenUtil.strToArrayStr(dbm.getValue(0,"start_date",""), "-");
			String[] enddate=GenUtil.strToArrayStr(dbm.getValue(0,"end_date",""), "-");
			getdates.put("startyear",startdate[0]);
			getdates.put("startmonth",startdate[1]);
			getdates.put("startday",startdate[2]);
			getdates.put("endyear",enddate[0]);
			getdates.put("endmonth",enddate[1]);
			getdates.put("endday",enddate[2]);
		    getdates.put("eventstatus",eventstatus);
			getdates.put("accounttype",accounttype);
			String currentFee=getCurrentFee(eventid,dbm.getValue(0,"mgr_id",""),dbm.getValue(0,"current_level",""));
			getdates.put("currentFee",CurrencyFormat.getCurrencyFormat("",currentFee,true));
			getdates.put("currentLevel",dbm.getValue(0,"current_level",""));
		}	
		 return getdates;
	}
	public static boolean copyConfirmationPageSettings(String oldEventId,String newSeqId){
		boolean result=true;
		log.info("Entered in to copyConfirmationPageSettings ");
		try{
		StatusObj statobj=null;
		String ConfirmpagesettingsQry="insert into custom_reg_flow_templates(eventid,content,purpose) " +
				"select CAST(? AS BIGINT),content,purpose from custom_reg_flow_templates" +
				" where eventid = CAST(? AS BIGINT)";
		String[] inputparams=new String[]{newSeqId,oldEventId};
		statobj=DbUtil.executeUpdateQuery(ConfirmpagesettingsQry, inputparams);
		
		}
		catch(Exception e){
			System.out.println(" Exception in copyConfirmationPageSettings"+e+"\n");
		}
		return result;
	}
	
	public static String getTicketingType(String eventid,String powertype){
		log.info("Enter getTicketingType eventid: "+eventid);
    	String venuestatus=DbUtil.getVal("select 'yes' from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name in ('event.seating.venueid','event.seating.enabled')", new String[]{eventid});
    	if(venuestatus!=null) return "300";
    	String customquestionsstatus=DbUtil.getVal("select 'yes' from custom_attribs where attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT))", new String[]{eventid});
        if(customquestionsstatus!=null) return "300";
        String htmlncssstatus=DbUtil.getVal("select 'yes' from user_roller_themes where refid=? and themetype!='DEFAULT'", new String[]{eventid});
        String configcss=DbUtil.getVal("select 'yes' from config where name='event.theme.type' and value='Custom HTML & CSS' and config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[]{eventid});
        if(htmlncssstatus!=null && configcss!=null)return "300";
        String confirmpagestatus=DbUtil.getVal("select 'yes' from custom_reg_flow_templates where eventid=CAST(? AS BIGINT)", new String[]{eventid});
        if(confirmpagestatus!=null)return "300";
        String confirmemailstatus=DbUtil.getVal("select 'yes' from custom_email_templates where groupid=?", new String[]{eventid});	
        if(confirmemailstatus!=null)return "300";
        String checkdiscount=DbUtil.getVal("select 'yes' from coupon_master  where groupid=?", new String[]{eventid});
        if(checkdiscount!=null)return "200";
        //if call from Change RSVP to Ticketing case
        if(powertype != null && powertype.equals("RSVP")){
			String trackURLs=DbUtil.getVal("select 'yes' from trackURLs where eventid=?",new String[]{eventid});
			if(trackURLs!=null)return "200";
			String scanids=DbUtil.getVal("select 'yes' from event_scanners where eventid=?",new String[]{eventid});
			if(scanids!=null)return "200";
        }	
        return "100";
	}
	
	public static void copyRsvpSettings(String oldEventId, String newEventId){
		log.info(" copy rsvp settings");
		String query="insert into rsvp_settings(eventid,limittype,attendeelimit,notsurelimit,totallimit,notattending)" +
				"select ?,limittype,attendeelimit,notsurelimit,totallimit,notattending from rsvp_settings where eventid=?";
		try{
			DbUtil.executeUpdateQuery(query, new String[]{newEventId,oldEventId});
		}catch(Exception e){
			System.out.println("Exception in copy event copyRsvpSettings Error: "+e.getMessage());
		}
	}
	
	public static void copyRSVPsubgroupattribs(String oldEventId,String newSeqId,String attribSetId){
		System.out.println("copyRSVPsubgroupattribs");
		String rsvpSubgroupAttribInsertQry="insert into subgroupattribs(groupid, subgroupid, attribsetid, " +
		"attribid) select CAST(? AS BIGINT),subgroupid," +
		"'"+attribSetId+"',attribid from subgroupattribs where groupid=CAST(? AS BIGINT)";
		String[] params=new String[]{newSeqId,oldEventId};
		StatusObj stobj=DbUtil.executeUpdateQuery(rsvpSubgroupAttribInsertQry, params);
		System.out.println("rsvpSubgroupAttribInsertQry msg: "+stobj.getErrorMsg());
	}
	
	public static String getRSVPTicketingType(String eventid){
        log.info(" getRSVPTicketingType eventid: "+eventid);
    	String customquestionsstatus=DbUtil.getVal("select 'yes' from custom_attribs where attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT))", new String[]{eventid});
        if(customquestionsstatus!=null) return "150";
        String confirmemailstatus=DbUtil.getVal("select 'yes' from custom_email_templates where purpose='RSVP_CONFIRMATION' and groupid=?", new String[]{eventid});	
        if(confirmemailstatus!=null)return "150";
        String confirmpagestatus=DbUtil.getVal("select 'yes' from custom_reg_flow_templates where purpose='rsvp_confirmationpage' and eventid=CAST(? AS BIGINT)", new String[]{eventid});
        if(confirmpagestatus!=null)return "150";
        String confightmlcss=DbUtil.getVal("select 'yes' from config where name='event.theme.type' and value='Custom HTML & CSS' and config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[]{eventid});
        if(confightmlcss != null)return "150";
        String fbcommenting=DbUtil.getVal("select 'yes' from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.fbcomment.show' and value ='Y'",new String[]{eventid});
        if(fbcommenting != null)return "150";
        String twitter=DbUtil.getVal("select 'yes' from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.twitter.show' and value ='Y'",new String[]{eventid});
        if(twitter != null)return "150";
        String googleplusone=DbUtil.getVal("select 'yes' from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.googleplusone.show' and value ='Y'",new String[]{eventid});
        if(googleplusone != null)return "150";
        String fblike=DbUtil.getVal("select 'yes' from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.fblike.show' and value ='Y'",new String[]{eventid});
        if(fblike != null)return "150";
        String rsvpwording = DbUtil.getVal("select 'yes' from custom_event_display_attribs where eventid=CAST(? AS BIGINT) and data->0->'modules' @> '[{\"m\": \"RSVPFlowWordings\"}]'", new String[]{eventid});
        if(rsvpwording != null)return "150";
        return "90";
                    	
	}
	
	public static void copyDiscountCodesMaster(String oldEventId, String newEventId){
		System.out.println("In copyDiscountMaster");
		try{
			String couponIdSelectQry="select couponid from coupon_master where groupid = ?";
			DBManager dbmanager=new DBManager();
			StatusObj statobj=dbmanager.executeSelectQuery(couponIdSelectQry, new String[]{oldEventId});
			int count1=statobj.getCount();
			if(statobj.getStatus()&&count1>0){
				for(int k=0;k<count1;k++){
					String oldCoupId=dbmanager.getValue(k, "couponid","");
					String newCoupId=DbUtil.getVal("select nextval('seq_couponid')",null);
	                String currentdate=DateUtil.getCurrDBFormatDate();
					String couponMasterInsert="insert into coupon_master (groupid, couponid, managerid, grouptype, " +
							"name, description, discount, discounttype, created_by, updated_by, coupontype, eachcode_limit, " +
							"activationdate, cutoffdate, created_at, updated_at) select '"+newEventId+"', '"+newCoupId+"', " +
							"managerid, grouptype, name, description, discount, discounttype, created_by, updated_by, " +
							"coupontype, eachcode_limit, to_date(?,'YYYY-MM-DD'),to_date(?,'YYYY-MM-DD'), " +
							"to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'), to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S') " +
							"from coupon_master where groupid =? and couponid = ?";
					DbUtil.executeUpdateQuery(couponMasterInsert, new String[]{currentdate,currentdate,currentdate,currentdate,oldEventId,oldCoupId});
					copyDiscountCodes(newCoupId, oldCoupId, newEventId);
				}
			}
		}catch(Exception e){
				System.out.println(" Exception in copyDiscountMaster   "+e+"\n");
		}
	}
	
	public static void copyDiscountCodes(String newCoupId, String oldCoupId, String newEventId){
		
		String couponCodesQry="insert into coupon_codes (couponid, couponcode, maxcount) select '"+newCoupId+"', " +
				"couponcode, maxcount from coupon_codes where couponid=?";
		DbUtil.executeUpdateQuery(couponCodesQry, new String[]{oldCoupId});
		
		String couponTicketsTempQry="insert into coupon_ticket_temp(couponid,price_id,eventid) select '"+newCoupId+"', "+
		"price_id,'"+newEventId+"' from coupon_ticket where couponid=?";
		
		DbUtil.executeUpdateQuery(couponTicketsTempQry, new String[]{oldCoupId});
		
	}
	
	public static HashMap<String,String> getLevelServiceFeeMap(String powertype,String mgrId,String oldEventId,String newSeqId,String accounttype,AddEventData addEventData){
		System.out.println("In getLevelServiceFeeMap for event ::"+newSeqId);
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		String currentfee="",currentlevel="";
		HashMap<String,String> feemap=new HashMap<String,String>();
		HashMap<String,String> evtmap=new HashMap<String,String>();
		try{
		
		if("Gold".equalsIgnoreCase(accounttype) || "Platinum".equalsIgnoreCase(accounttype)){
			if("RSVP".equalsIgnoreCase(powertype)){
				 currentfee="0";
				 currentlevel="150";
			}else{
				 currentfee="1";
				 currentlevel="400";
			}
		}else{
			currentlevel=addEventData.getUpgradeLevel();
			System.out.println("getLevelServiceFeeMap currentlevel: "+currentlevel);
			String nonprofitstatus=DbUtil.getVal("select 'Y' from ebee_special_fee where userid=?", new String[]{mgrId});
			if(nonprofitstatus==null)nonprofitstatus="N";
			if("Y".equalsIgnoreCase(nonprofitstatus)){
				if("RSVP".equalsIgnoreCase(powertype)){
					currentfee=DbUtil.getVal("select ln"+currentlevel+" from international_pricing where currency_code='USD'", null);
					//currentlevel="90";
				}else{
					evtmap=SpecialFeeDB.getEventMap(oldEventId);
					//currentlevel="100";
					currentfee=DbUtil.getVal("select ln"+currentlevel+" from international_pricing where currency_code=?", new String[]{evtmap.get("currencycode")});
					currentfee=Double.toString(Double.parseDouble(currentfee)/Double.parseDouble(evtmap.get("conversionfactor")));
				}
			}else{
				if("RSVP".equalsIgnoreCase(powertype)){
					currentfee=DbUtil.getVal("select l"+currentlevel+" from international_pricing where currency_code='USD'", null);
					//currentlevel="90";
				}else{
					evtmap=SpecialFeeDB.getEventMap(oldEventId);
					//currentlevel="100";
					currentfee=DbUtil.getVal("select l"+currentlevel+" from international_pricing where currency_code=?", new String[]{evtmap.get("currencycode")});
					currentfee=Double.toString(Double.parseDouble(currentfee)/Double.parseDouble(evtmap.get("conversionfactor")));
				}
			}
		}
		
		if("".equals(currentfee) && "".equals(currentlevel)){
			System.out.println("currentfee and currentlevel empty case:"+newSeqId);
			if("RSVP".equalsIgnoreCase(powertype)){
				currentfee="0";
				currentlevel="90";
		    }else{	
			    currentfee="1";
				currentlevel="100";
		   }
		}
		feemap.put("currentfee", currentfee);
		feemap.put("currentlevel", currentlevel);
		}catch(Exception e){
			System.out.println("Exception occured in getLevelServiceFeeMap for event :: "+newSeqId+" :: "+e.getMessage());
		}
		System.out.println("feemap is:"+feemap);
		return feemap;
	}
	
	public static String getConfigLooknFeel(String eventId){
		String confightmlcss=DbUtil.getVal("select 'yes' from config where name='event.theme.type' and value='Custom HTML & CSS' and config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[]{eventId});
		if(confightmlcss==null) confightmlcss="";
		return confightmlcss;
	}
	
	public static HashMap<String, String> taxAndCCProFee(String eventid) {
		HashMap<String, String> hmap =new HashMap<String, String>();
		DBManager dbmanager = new DBManager();
		String paymentTypesQry="select paytype,attrib_5 from payment_types where status='Enabled' and refid=? and paytype in('eventbee','paypal')";
		StatusObj stobj = dbmanager.executeSelectQuery(paymentTypesQry,new String[]{eventid});
		for(int i=0;i<stobj.getCount();i++) {
			String paytype=dbmanager.getValue(i,"paytype","");
			String subpaytype=dbmanager.getValue(i,"attrib_5","");
			if("eventbee".equals(paytype) && "paypal_pro".equals(subpaytype)){
				hmap.put("tax", "4.95");
				hmap.put("ccprofee", "0.5");
				break;
			}else if(("eventbee".equals(paytype) && "stripe".equals(subpaytype)) || "paypal".equals(paytype)){
				hmap.put("tax", "2.95");
				hmap.put("ccprofee", "0.3");
			}
		}
		
		System.out.println("paymentTypeTaxAndCCProFeeMap in copyevent is:" + hmap);
		return hmap;
	}
	
	public static void copyLayoutPageSettings(String oldEventId,String newSeqId, String ticketlevel){
		System.out.println("copyLayoutPageSettings oldEventId: "+oldEventId+" newEventId: "+newSeqId);
		String copyEventLayoutQry = "insert into event_layout (eventid,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode," +
				"stage,sync,updated_at,created_at) select CAST(? AS BIGINT),wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode,stage,sync," +
				"to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') from event_layout where eventid=CAST(? AS BIGINT)";
		
		StatusObj statobj = DbUtil.executeUpdateQuery(copyEventLayoutQry, new String[]{newSeqId,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),oldEventId});
		if(statobj.getCount()==0){
			copyEventLayoutQry = "insert into event_layout (eventid,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode," +
					"stage,sync,updated_at,created_at) select CAST(? AS BIGINT),wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode,stage,sync," +
					"to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') from event_layout where eventid='1'::bigint";
			
			statobj = DbUtil.executeUpdateQuery(copyEventLayoutQry, new String[]{newSeqId,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate()});
		}
		
		
		String copyCustom_widget_Options="insert into custom_widget_options (widget_ref_title ,widgetid, updated_at, created_at, stage, widget_title, config_data, eventid) select widget_ref_title ,widgetid, to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'), to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'), stage, widget_title, config_data, CAST(? AS BIGINT) from custom_widget_options where eventid=CAST(? AS BIGINT) and widgetid<>'RSVPList'";
		
		if("300".equals(ticketlevel) || "150".equals(ticketlevel)){
			//DbUtil.executeUpdateQuery("delete from custom_widget_options where eventid=CAST(? AS BIGINT) and widgetid='RSVPList'",new String[]{newSeqId});
		}else{
			copyCustom_widget_Options="insert into custom_widget_options (widget_ref_title ,widgetid, updated_at, created_at, stage, widget_title, config_data, eventid) select widget_ref_title ,widgetid, to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'), to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'), stage, widget_title, config_data, CAST(? AS BIGINT) from custom_widget_options where eventid=CAST(? AS BIGINT) and widgetid not in('RSVPList','whosAttending')";
		}
		StatusObj insetCustom = DbUtil.executeUpdateQuery(copyCustom_widget_Options, new String []{DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),newSeqId,oldEventId});
		/*For Seating*/
		Boolean isSeating = EventDB.isSeatingEvent(oldEventId);
		 if("300".equals(ticketlevel) || "400".equals(ticketlevel)){
			 if(isSeating){
				 DBManager dbm = new DBManager();
				 StatusObj ifSeating;
					 String selectEvent_layout = "select * from event_layout where eventid=?::bigint and stage='final'";
					 ifSeating = dbm.executeSelectQuery(selectEvent_layout, new String[]{newSeqId});
					 if(ifSeating.getStatus() && ifSeating.getCount()>0){
							String singleWidgets=dbm.getValue(0,"single_widgets","");
							String wideWidgets=dbm.getValue(0,"wide_widgets","");
							if(singleWidgets!=null && singleWidgets.indexOf("tickets")>-1){
								
							}else if(wideWidgets!=null && wideWidgets.indexOf("tickets")>-1){
								System.out.println("moving ticket widget to top stage is Final");
								EventDB.removeTicketsFromWideWidgets(singleWidgets,wideWidgets,newSeqId);
							}
							System.out.println("moving ticket widget to top stage is Draft");
							String themeData=DbUtil.getVal("select 'yes' from event_layout where eventid=?::bigint and stage='draft'", new String[]{newSeqId});
							if(themeData==null || "".equals(themeData)){
								String query = "insert into event_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode) (select  ?::bigint,'draft','no',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode from event_layout where eventid='1'::bigint )";
								DbUtil.executeUpdateQuery(query, new String[]{newSeqId});
							}
							DbUtil.executeUpdateQuery("update event_layout set single_widgets=(select single_widgets from event_layout where eventid=?::bigint and stage='final') where eventid=?::bigint and stage='draft'", new String[]{newSeqId,newSeqId});
							DbUtil.executeUpdateQuery("update event_layout set wide_widgets=(select wide_widgets from event_layout where eventid=?::bigint and stage='final') where eventid=?::bigint and stage='draft'", new String[]{newSeqId,newSeqId});
				 }
			 }
		 }
		
		String copyWidgetOptionQry = "insert into  widget_options (eventid,widgetid,config_data,widget_title,widget_ref_title,stage,created_at,updated_at) " +
				"select CAST(? AS BIGINT),widgetid,config_data,widget_title,widget_ref_title,stage,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') from widget_options where eventid=?::bigint ";
		//statobj = DbUtil.executeUpdateQuery(copyWidgetOptionQry, new String[]{newSeqId,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),oldEventId});
	}
	public static void copyBuyerLayoutPageSettings(String oldEventId,String newSeqId, String ticketlevel){
		System.out.println("oldEventId : "+oldEventId+" newSeqId : "+newSeqId+" ticketlevel : "+ticketlevel);
		String copyBuyerEventLayoutQry = "insert into buyer_att_page_layout (eventid,wide_widgets,narrow_widgets,single_widgets,stage,global_style,layout_type,single_bottom_widgets," +
				"created_at, updated_at,hide_widgets,sync,header_theme) select CAST(? AS BIGINT),wide_widgets,narrow_widgets,single_widgets,stage,global_style,layout_type,single_bottom_widgets,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS')," +
				"to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),hide_widgets,sync,header_theme from buyer_att_page_layout where eventid=CAST(? AS BIGINT)";
		StatusObj statobj = DbUtil.executeUpdateQuery(copyBuyerEventLayoutQry, new String[]{newSeqId,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),oldEventId});
		if(statobj.getCount()==0){
			copyBuyerEventLayoutQry = "insert into buyer_att_page_layout (eventid,wide_widgets,narrow_widgets,single_widgets,stage,global_style,layout_type,single_bottom_widgets,created_at, updated_at,hide_widgets,sync,header_theme) " +
					"select CAST(? AS BIGINT),wide_widgets,narrow_widgets,single_widgets,stage,global_style,layout_type,single_bottom_widgets,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),hide_widgets,sync,header_theme from buyer_att_page_layout where eventid='1'::bigint";
			
			statobj = DbUtil.executeUpdateQuery(copyBuyerEventLayoutQry, new String[]{newSeqId,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate()});
		}
		String buyer_att_custom_widget_options="insert into buyer_att_custom_widget_options (widget_ref_title ,widgetid, updated_at, created_at, stage, widget_title, config_data, eventid,layout_type) select widget_ref_title ,widgetid, to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'), to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'), stage, widget_title, config_data, CAST(? AS BIGINT),layout_type from buyer_att_custom_widget_options where eventid=CAST(? AS BIGINT) and widgetid<>'RSVPList'";
		if("400".equals(ticketlevel) || "150".equals(ticketlevel)){
		}else{
			buyer_att_custom_widget_options="insert into buyer_att_custom_widget_options (widget_ref_title ,widgetid, updated_at, created_at, stage, widget_title, config_data, eventid,layout_type) select widget_ref_title ,widgetid, to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'), to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'), stage, widget_title, config_data, CAST(? AS BIGINT),layout_type from buyer_att_custom_widget_options where eventid=CAST(? AS BIGINT) and widgetid<>'RSVPList'";
		}
		StatusObj insetBuyerCustom = DbUtil.executeUpdateQuery(buyer_att_custom_widget_options, new String []{DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),newSeqId,oldEventId});
	
	}
	
}	
