package com.event.dbhelpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.event.beans.AddEventData;
import com.event.helpers.I18n;
import com.eventbee.beans.DateTime;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateConverter;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EventListingKeywords;
import com.eventbee.general.StatusObj;
import com.eventbee.general.VCal;
import com.eventbee.general.VCalThread;
import com.eventbee.geocodes.Geocode;
import com.eventbee.geocodes.GeocodeGenerator;
import com.eventbee.namedquery.NQDbUtil;
import com.eventbee.namedquery.NQStatusObj;
import com.eventbee.payments.dbhelpers.CardProcessorDB;
import com.eventbee.util.Address;
import com.myevents.actions.AddEventAction;
import com.user.dbhelpers.UserDB;

public class AddEventDB {
	private static final Logger log = Logger.getLogger(AddEventDB.class);
	final static String SEQ_GET="select nextval('seq_groupid') as eventid,nextval('seq_transactionid') as transactionid,"
		+" nextval('seq_configid') as configid,0 as priceid";
	public static void createEvent(AddEventData addEventData,String eid,String uid,String fckdesc,String isRecurring,String accounttype){
		String eventTitle=addEventData.getEventTitle();
		String venue=addEventData.getVenue();
		String tags=addEventData.getTags();
		String city=addEventData.getCity();
		String email=addEventData.getEmail().trim();
		String listtype=addEventData.getListtype();
		String listingprivacy=addEventData.getListingprivacy();
		String address1=addEventData.getAddress1();
		String address2=addEventData.getAddress2();
		String country=addEventData.getCountry();
		String state=addEventData.getState();
		String category=addEventData.getCategory();
		String subcategory=addEventData.getSubcategory();
		String description="",isexits="",temp="",registrationcount="0";
		String descriptiontype=addEventData.getDescriptiontype();
		int regcount=0;
		
		if("wysiwyg".equals(descriptiontype)) {
			description=addEventData.getDescription();
		}
		else{
			description=addEventData.getTextcontent();
		}
		isexits=DbUtil.getVal("select 'Y' from mgr_config where name='mgr.eventlisting.allow' and userid=cast(? as integer) and value='Y'", new String[]{uid});
		if(isexits==null)isexits = "N";
		if(!"Y".equals(isexits)){
			registrationcount=DbUtil.getVal("select count(*) from event_reg_transactions where  paymenttype<>'RSVP' and eventid::bigint in(select eventid from eventinfo where mgr_id=?::integer)", new String[]{uid});
			if(registrationcount==null)registrationcount="0";
			try{
				regcount=Integer.parseInt(registrationcount);
				System.out.println("the registration count in eventlisting:"+regcount);
			}catch(Exception e){regcount=0;}
			if(regcount<=0){
			HttpServletRequest request = ServletActionContext.getRequest();
			String ip = AddEventAction.getIpFromRequest(request);
			if(EventListingKeywords.keywords.size()==0)
				EventListingKeywords.keywords.addAll(AddEventDB.getKeywords());
		//if(EventListingKeywords.keywords.size()>0){		
			for(int key=0;key<EventListingKeywords.keywords.size();key++){	
				 //Pattern pattern = Pattern.compile(EventListingKeywords.keywords.get(key)); //commented on 29 sep 2014 for handling special chars 
				 //if(pattern.matcher(description.toLowerCase()).find() || pattern.matcher(eventTitle.toLowerCase()).find()){
				if(description.toLowerCase().replaceAll("&lt;", "<").replaceAll("&gt;", ">").indexOf(EventListingKeywords.keywords.get(key))>-1 || eventTitle.toLowerCase().indexOf(EventListingKeywords.keywords.get(key))>-1){
					System.out.println("user blocked pattern: "+EventListingKeywords.keywords.get(key)+" userid: "+uid+" eventid: "+eid);
					DbUtil.executeUpdateQuery("insert into emptied_description_events(eventid,description,created_at,userid,title,pattern) values(cast(? as bigint),?,now(),CAST(? as BIGINT),?,?)",new String[]{eid,description,uid,eventTitle,EventListingKeywords.keywords.get(key)});
					DbUtil.executeUpdateQuery("update authentication set accounttype='blocked',blocked_by='event_desc_pattern',blocked_at=now() where user_id=?", new String[]{uid});
					String query="insert into suspected_user_activities(userid,ip,action,created_at,pattern) values(?,?,'add_event_db',now(),?)";
					DbUtil.executeUpdateQuery(query,new String[]{uid,ip,EventListingKeywords.keywords.get(key)});
					String useremail = DbUtil.getVal("select email from user_profile where user_id=?",new String[]{uid});	
					System.out.println("update user_profile email:: "+useremail);
					if(useremail!=null && !"".equals(useremail) && !useremail.contains("_inactive_eb"))
						DbUtil.executeUpdateQuery("update user_profile set email='"+useremail+"_inactive_eb' where user_id=?",new String[]{uid});
					description = "";
					temp = "cancel";
					break;
					
				}			
			}
			/*}else{
					//System.out.println("from db");
					ArrayList<String> keywords = new ArrayList<String>();
					keywords = AddEventDB.getKeywords();
					EventListingKeywords.keywords.addAll(keywords);					
					for(int i=0;i<keywords.size();i++){
						//Pattern pattern = Pattern.compile(keywords.get(i));
						//if(pattern.matcher(description.toLowerCase()).find() || pattern.matcher(eventTitle.toLowerCase()).find()){
						if(description.toLowerCase().indexOf(keywords.get(i))>-1 || eventTitle.toLowerCase().indexOf(keywords.get(i))>-1){
							System.out.println("from static block::else case: "+keywords.get(i)+" userid:::"+uid);
							DbUtil.executeUpdateQuery("insert into emptied_description_events(eventid,description,created_at,ip,userid,title,pattern) values(cast(? as bigint),?,now(),CAST(? as BIGINT),?,?)",new String[]{eid,description,ip,uid,eventTitle,keywords.get(i)});
							DbUtil.executeUpdateQuery("update authentication set accounttype='blocked' where user_id=?", new String[]{uid});
							String query="insert into suspected_user_activities(userid,ip,action,created_at,pattern) values(?,?,'add_event_db',now(),?)";
							DbUtil.executeUpdateQuery(query,new String[]{uid,ip,keywords.get(i)});
							String useremail = DbUtil.getVal("select email from user_profile where user_id=?",new String[]{uid});
							System.out.println("update user_profile email:: "+useremail);
							if(useremail!=null && !"".equals(useremail) && !useremail.contains("_inactive_eb"))
								DbUtil.executeUpdateQuery("update user_profile set email='"+useremail+"_inactive_eb' where user_id=?",new String[]{uid});
							description = "";
							temp = "cancel";
							break;
							
						}
					}
				}*/ 
			}
		}
		String startyear=addEventData.getStartyear();
		String startmonth=addEventData.getStartmonth();
		String startday=addEventData.getStartday();
		String endyear=addEventData.getEndyear();
		String endmonth=addEventData.getEndmonth();
		String endday=addEventData.getEndday();
		String currency=addEventData.getCurrency();
		String registrationtype=addEventData.getPowerWithType();
		String evtlevel="2";
		String listatebee="yes";
		String timezone=addEventData.getTimezones();
		String startdate=startyear+"-"+startmonth+"-"+startday;
		String enddate=endyear+"-"+endmonth+"-"+endday;
		String name=addEventData.getName();
		String starthour=addEventData.getStarthour();
		String startmin=addEventData.getStartminute();
		String endhour=addEventData.getEndhour();
		String endmin=addEventData.getEndminute();

		String sampm=addEventData.getStampm();
		String eampm=addEventData.getEndampm();

		String stttime=DateConverter.getHH24(starthour,sampm);
		String entime=DateConverter.getHH24(endhour,eampm);
		String starttime=stttime+":"+startmin;
		String endtime=entime+":"+endmin;
		//begin for enddate_est
		DateTimeBean dtb=new DateTimeBean();
        dtb.setYyPart(endyear);
        dtb.setMonthPart(endmonth);
        dtb.setDdPart(endday);
        dtb.setHhPart(endhour);
        dtb.setMmPart(endmin);
        dtb.setAmpm(eampm);
        String userTimeZone=DateTime.getTimeZoneVal(timezone);
        String toTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		DateConverter dc=new DateConverter(userTimeZone,toTimeZone);
        boolean statusdc=dc.convertDateTime(dtb);
        
        String endDate=dc.getDatePart();
        String endTime=dc.getTimePart();
        String enddate_est=endDate+" "+endTime;
        //end for enddate_est
		String unitid="13579";
		String eventcode="";
		String ntsEnable = addEventData.getNtsEnable();
		String ntsCommission = addEventData.getNtsCommission();
		HashMap specialfeehm=getSpecialFee(uid,registrationtype,accounttype);
		String currentlevel = (String)specialfeehm.get("currentlevel");
		String currentfee = (String)specialfeehm.get("currentfee");
		System.out.println("---- In createEvent eid: "+eid+" mgrId: "+uid+" currentlevel: "+currentlevel+" currentfee: "+currentfee);
		if(registrationtype.equals("rsvp"))
			ntsEnable="N";
		if(ntsEnable.equals("N"))
			ntsCommission="10";
		HashMap inParams = new HashMap();
		inParams.put("eventid",eid);
		inParams.put("mgr_id",uid);
		inParams.put("category",category);
		inParams.put("eventname",eventTitle);
		inParams.put("description",description);
		inParams.put("venue",venue);
		inParams.put("address1",address1);
		inParams.put("city",city);
		inParams.put("state",state);
		inParams.put("country",country);
		inParams.put("email",email);
		inParams.put("event_type",subcategory);
		inParams.put("evt_level",evtlevel);
		inParams.put("listebee",listatebee);
		inParams.put("listtype",listingprivacy);
		inParams.put("descriptiontype",descriptiontype);
		inParams.put("address2",address2);
		inParams.put("start_date",startdate);
		inParams.put("end_date",enddate);
		inParams.put("type",listtype);
		inParams.put("tags",tags);
		inParams.put("nts_enable",ntsEnable);
		inParams.put("nts_commission",ntsCommission);
		inParams.put("current_level",currentlevel);
		inParams.put("current_fee",currentfee);
		try{
			System.out.println("listing query");
		NQStatusObj obj= NQDbUtil.executeQuery("ListingQuery", inParams);
		setGeocodes(address1,address2,city,state,country,eid);
		HashMap vcal=new HashMap();
		vcal.put(VCal.ID,eid);
		vcal.put(VCal.PURPOSE,"event");
		vcal.put(VCal.DESCRIPTION,description);
		vcal.put(VCal.NAME,eventTitle);
		vcal.put(VCal.LOCATION,city);
        
		DateTime stime=new DateTime(startyear,startmonth,startday,starthour,startmin,sampm);
		DateTime etime=new DateTime(endyear,endmonth,endday,endhour,endmin,eampm);
		stime.setHour12(starthour);
		stime.setAmpm(sampm);
		etime.setHour12(endhour);
		etime.setAmpm(eampm);
		vcal.put(VCal.STARTTIME,stime.getDateTimeForVCal(DateTime.getTimeZoneVal(timezone)));
		vcal.put(VCal.ENDTIME,etime.getDateTimeForVCal(DateTime.getTimeZoneVal(timezone)));
		System.out.println("vcal");
		//VCal.createFile(vcal);
		(new Thread(new VCalThread(vcal))).start();
		String CONFIG_STREAMER_INSERT="insert into config(config_id,name,value) values(CAST(? AS INTEGER),'eventpage.streamer.show','No')";
		String CONFIG_MAP_INSERT="insert into config(config_id,name,value) values(CAST(? AS INTEGER),'eventpage.map.show','Yes')";
		String CONFIG_TIMEZONE_INSERT="insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.timezone',?)";
		String CONFIG_URLMAP_INSERT="insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.urlmap','No')";
		String CONFIG_SHOWINSEARCH_INSERT="insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.publish.showinsearch','yes')";
		String EVNET_CONFIG_INSERT="insert into EVENT_CONFIG(eventid,rsvp_type,rsvp_count) values (CAST(? AS BIGINT),'0','0')";
		String CONFIG_HOSTNAME_INSERT="insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.hostname',?)";
		String CONFIG_POWERTYPE_INSERT="insert into config(config_id,name,value) values(CAST(? AS INTEGER),?,'yes')";
		String CONFIG_RECURRINGEVENT_INSERT="insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.recurring','Y')";
		String CONFIG_I18NLANG_INSERT="insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.i18n.lang',?)";
		String CONFIG_I18N_ACTUAL_LANG_INSERT="insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.i18n.actual.lang',?)";
		
		
		System.out.println("1)getting config_id");
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		System.out.println("2)inserting streamer: ");
		DbUtil.executeUpdateQuery(CONFIG_STREAMER_INSERT,new String [] {configid});
		System.out.println("2)inserted");
		System.out.println("3)inserting config map");
		DbUtil.executeUpdateQuery(CONFIG_MAP_INSERT,new String [] {configid});
		System.out.println("3)inserted");
		System.out.println("4)inserting time zone");
		DbUtil.executeUpdateQuery(CONFIG_TIMEZONE_INSERT,new String [] {configid,timezone});
		System.out.println("4)inserted time zone");
		System.out.println("5)inserting URL  Map");
		DbUtil.executeUpdateQuery(CONFIG_URLMAP_INSERT,new String [] {configid});
		System.out.println("5)inserted");
		System.out.println("6)inserting CONFIG_SHOWINSEARCH");
		DbUtil.executeUpdateQuery(CONFIG_SHOWINSEARCH_INSERT,new String [] {configid});
		System.out.println("6)inserted");
		System.out.println("7)inserting host name");
		DbUtil.executeUpdateQuery(CONFIG_HOSTNAME_INSERT,new String [] {configid,name});
		System.out.println("7)inserted");
		if(isRecurring!=null){
			if(isRecurring.equals("true")){
				System.out.println("RECC inserting config entry for recurring");
				DbUtil.executeUpdateQuery(CONFIG_RECURRINGEVENT_INSERT,new String [] {configid});
				System.out.println("RECC insered config entry for recurring");
			}
		}
		System.out.println("8)inserting EVENT_CONFIG");
		DbUtil.executeUpdateQuery(EVNET_CONFIG_INSERT,new String [] {eid});
		System.out.println("8)inserted");			
		
		if("cancel".equals(temp)){
			System.out.println("9)updating eventinfo start time,end time, status='Cancel' and enddate_est");
		DbUtil.executeUpdateQuery("update eventinfo set starttime=?,endtime=?, status='CANCEL', enddate_est=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'), cancel_by='event_desc_pattern' where eventid=CAST(? AS BIGINT)",new String[]{starttime,endtime,enddate_est,eid});	
		}else{
			System.out.println("9)updating eventinfo start time,end time, status='Active' and enddate_est");
		DbUtil.executeUpdateQuery("update eventinfo set starttime=?,endtime=?, status='ACTIVE', enddate_est=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS') where eventid=CAST(? AS BIGINT)",new String[]{starttime,endtime,enddate_est,eid});
		}
		System.out.println("9)updated");
		String configregtypename="";
		if("ticketing".equals(registrationtype)){
			configregtypename="event.poweredbyEB";
			System.out.println("10)deleting currency");
			DbUtil.executeUpdateQuery("delete from event_currency where eventid=? ",new String [] {eid});
			System.out.println("deleted");
			System.out.println("11)inserting currency");
			DbUtil.executeUpdateQuery("insert into event_currency(eventid,currency_code) values(?,?)",new String [] {eid,currency});
			System.out.println("11)currency inserted");
			System.out.println("12)inserting powertype");
			DbUtil.executeUpdateQuery(CONFIG_POWERTYPE_INSERT,new String [] {configid,configregtypename});
			System.out.println("power type inserted");
		}
		if("rsvp".equals(registrationtype)){
			String rsvp_count=null;
			if("0".equals(addEventData.getRsvptype()))
				rsvp_count="";
			else
			       rsvp_count=addEventData.getRsvplimit();

			String count=null;
			if("1".equalsIgnoreCase(addEventData.getRsvptype())){
				count=addEventData.getRsvplimit();
				try{
					Integer.parseInt(count);
				}catch(Exception e){
				}
			}
			System.out.println("13)inserting rsvp entries");
			DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name in ('event.rsvp.limit')",new String [] {configid});
			DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name in ('event.rsvp.enabled')",new String [] {configid});
			DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name in ('event.regexternal.url')",new String [] {configid});

			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) select config_id,'event.rsvp.enabled',? from eventinfo where eventid=CAST(? AS BIGINT)",new String [] {"yes",eid});
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) select config_id,'event.rsvp.limit',? from eventinfo where eventid=CAST(? AS BIGINT)",new String [] {rsvp_count,eid});

			DbUtil.executeUpdateQuery("delete from event_config where eventid=CAST(? AS BIGINT)",new String [] {eid});
			DbUtil.executeUpdateQuery("insert into event_config(eventid,rsvp_limit,rsvp_type) values(CAST(? AS BIGINT),CAST(? AS INTEGER),CAST(? AS INTEGER)) ",new String [] {eid,(count==null)?"0":count,addEventData.getRsvptype()});
			System.out.println("13)inserted ");

		}

		String value=EventDB.getConfigVal(eid,"event.maillist.id","");
		if(value==null || "".equals(value.trim())){
		String evtcode=eventcode;
		if(evtcode==null || "".equals(evtcode.trim())){
		evtcode=eventTitle+"_"+startmonth+" "+startday+" "+startyear;
		}

		HashMap hm=new HashMap();
		hm.put("mgrid",uid);
		hm.put("unitid",unitid);
		hm.put("configid",configid);
		hm.put("evtcode",evtcode.trim());
		hm.put("evtid",eid);
		System.out.println("14)Inserting into memberlist");
		//int rcount=(new EventDB()).InsertMemberList(hm);
		System.out.println("14)Inserted memberlist");
		hm=null;
		}
		
		String insertLayoutPageQry = "insert into event_layout (eventid,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode," +
				"stage,sync,updated_at,created_at) select CAST(? AS BIGINT),wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode,stage,sync," +
				"to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') from event_layout where eventid='1'::bigint";
		
		DbUtil.executeUpdateQuery(insertLayoutPageQry, new String[]{eid,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate()});
		System.out.println("15)Inserting into event_layout for layoutpage");
		
		DbUtil.executeUpdateQuery(CONFIG_I18NLANG_INSERT, new String[]{configid,addEventData.getI18nLang()});
		DbUtil.executeUpdateQuery(CONFIG_I18N_ACTUAL_LANG_INSERT, new String[]{configid,addEventData.getI18nActulaLang()});
		UserDB.userLangTrack(uid, addEventData.getI18nLang(), "listevent", eid);
		System.out.println("16)Inserting event.i18n.lang set up");
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
	}
	public static void mapEventSeqIdToEncoded(String seqid, String encodedid){
		DbUtil.executeUpdateQuery("insert into event_seq_random_nums_map(event_seqid,event_randomid) values(?,?)",new String[]{seqid,encodedid});
	}


	public static HashMap getEvtSeqID(){
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(SEQ_GET,new String[]{});
		HashMap hm=new HashMap();
		if(statobj.getStatus()){
				hm.put("eventid",dbmanager.getValue(0,"eventid",""));
				hm.put("configid",dbmanager.getValue(0,"configid",""));
				hm.put("transid",dbmanager.getValue(0,"transactionid",""));
		}
		return hm;
		}
	public static void updateEventInfo(String eid,AddEventData addEventData){
		String premiumlevel=addEventData.getPremiumlisttype();
		String level="";
		if("premium".equals(premiumlevel)){
			level="EVENT_PREMIUM_LISTING";
		}
		else if("featured".equals(premiumlevel)){
			level="EVENT_FEATURED_LISTING";
		}
		else if("custom".equals(premiumlevel)){
			level="EVENT_CUSTOM_LISTING";
		}
		DbUtil.executeUpdateQuery("update eventinfo set status='ACTIVE',premiumlevel=?  where eventid=CAST(? AS BIGINT)",new String[]{level, eid});
	}

	public static void setGeocodes(String address1,String address2,String city,String state,String country,String eid){
		GeocodeGenerator geocodegenerator=null;
		try{
			Object obj=(Object)Class.forName(EbeeConstantsF.get("geocodes.provider.class","com.eventbee.geocodes.GeocodeAmericaREST")).newInstance();
			geocodegenerator=(GeocodeGenerator)obj;
			String straddress=address1+"%"+address2;
			com.eventbee.util.Address address=new Address(straddress,city,state,"",country);
			geocodegenerator.setAddress(address);
			Geocode geocode=geocodegenerator.getGeocodes();
			if(geocode.getStatus()){
				StatusObj obj1=DbUtil.executeUpdateQuery("update eventinfo set latitude=?,longitude=? where eventid=CAST(? AS BIGINT)  ",new String [] {geocode.getLatitude(),geocode.getLongitude(),eid});
			}else
				DbUtil.executeUpdateQuery("insert into googlemap_track(source,date,eventid) values('Create Event',to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),?)",new String [] {DateUtil.getCurrDBFormatDate(),eid});
		}catch(Exception e){
		System.out.println(e.getMessage());
		}
	}

	public static void createRecurringEvent(String eid,List<Entity> seldateslist)
	{
		try {
			System.out.println("cursor inside try");
			/*
			String query = "insert into event_dates(eventid,status,eventdate) values(to_number(?,'999999999999')," +
					"?,to_date(?,'YYYY-MM-DD'))";
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(int i=0;i<seldateslist.size();i++){
				Date seldate = df.parse(seldateslist.get(i).getKey());
				String strdate = sdf.format(seldate);
				DbUtil.executeUpdateQuery(query, new String[]{eid,"ACTIVE",strdate});
				}
			*/
			String query = "insert into event_dates(eventid,status,zone_startdate,zone_enddate,zone_start_time,zone_end_time,est_startdate,est_enddate,est_start_time,est_end_time,eventdate,date_display,date_key) values(CAST(? AS BIGINT),?,to_date(?,'YYYY-MM-DD'),to_date(?,'YYYY-MM-DD'),?,?,to_date(?,'YYYY-MM-DD'),to_date(?,'YYYY-MM-DD'),?,?,to_date(?,'YYYY-MM-DD'),?,?)";
			String userTimeZone=EventDB.getEventTimeZone(eid);
			System.out.println("inserting recurring dates ");
			for(int i=0;i<seldateslist.size();i++)
			{
			String seldate = seldateslist.get(i).getKey();
			String display_date = seldateslist.get(i).getValue();
			String[] optns = seldateslist.get(i).getKey().toString().split("/");
			String st_date,st_time,end_date,end_time;
			st_date = optns[0];
			st_time=optns[2];
			end_date = optns[3];
			end_time = optns[5];
			
			//st_time = optns[2];
			String[] stimes=st_time.split(" ");
			String startAmpm=stimes[1];
			String []starthourpart=stimes[0].split(":");
			String[] endtimes=end_time.split(" ");
			String []endHourpart=endtimes[0].split(":");
			String endAmpm=endtimes[1];
		
			
			String zoneStartTime24hours=DateConverter.getHH24(starthourpart[0],startAmpm)+":"+starthourpart[1];
			String zoneEndTime24hours=DateConverter.getHH24(endHourpart[0],endAmpm)+":"+endHourpart[1];;
			String []eststarttimes=getEstDates(st_time,st_date,userTimeZone);
			String []estendtimes=getEstDates(end_time,end_date,userTimeZone);
		    DbUtil.executeUpdateQuery(query, new String[]{eid,"ACTIVE",st_date,end_date,zoneStartTime24hours,zoneEndTime24hours,eststarttimes[0],estendtimes[0],eststarttimes[1],estendtimes[1],st_date,display_date,seldate});
			}
			System.out.println("recurring dates inserted");
	}
		/*catch(ParseException ex){
		log.error("Date parse Exception: "+ex);
	}*/
	catch (Exception ex) {
		// TODO: handle exception
		log.error("Exception: "+ex);
	}
}
	
	public static void updateEventData(AddEventData addEventData, String eid,String recurring,List<Entity> recurringdates,String powertype){
		String description="",featuretype="",currentfee="",currentlevel="";
		String eventTitle = addEventData.getEventTitle();
		String venue = addEventData.getVenue();
		String tags = addEventData.getTags();
		String city = addEventData.getCity();
		String email = addEventData.getEmail().trim();
		String listtype = addEventData.getListtype();
		String listingprivacy = addEventData.getListingprivacy();
		String address1 = addEventData.getAddress1();
		String address2 = addEventData.getAddress2();
		String country = addEventData.getCountry();
		String state = addEventData.getState();
		String region = addEventData.getRegion();
		String category = addEventData.getCategory();
		String subcategory = addEventData.getSubcategory();
		String descriptiontype = addEventData.getDescriptiontype();
		if ("wysiwyg".equals(descriptiontype))
		 description = addEventData.getDescription();
		else
		 description = addEventData.getTextcontent();
		
	  /*  if(!"".equals(EventListingKeywords.keywords)){
			for(int key=0;key<EventListingKeywords.keywords.size();key++){
				if(description.contains(EventListingKeywords.keywords.get(key)))
				{
					DbUtil.executeUpdateQuery("insert into emptied_description_events(eventid,description) values(cast(? as bigint),?)",new String[]{eid,description});
					description = "";
					break;
					
				}			
			}}  */
		String startyear = addEventData.getStartyear();
		String startmonth = addEventData.getStartmonth();
		String startday = addEventData.getStartday();
		String endyear = addEventData.getEndyear();
		String endmonth = addEventData.getEndmonth();
		String endday = addEventData.getEndday();
		String name = addEventData.getName();
		String timeZone = addEventData.getTimezones();
		String startdate=startyear+"-"+startmonth+"-"+startday;
		String enddate=endyear+"-"+endmonth+"-"+endday;	
		String starthour=addEventData.getStarthour();
		String startmin=addEventData.getStartminute();
		String endhour=addEventData.getEndhour();
		String endmin=addEventData.getEndminute();
		String sampm=addEventData.getStampm();
		String eampm=addEventData.getEndampm();
		
		String stttime=DateConverter.getHH24(starthour,sampm);
		String entime=DateConverter.getHH24(endhour,eampm);
		String starttime=stttime+":"+startmin;
		String endtime=entime+":"+endmin;
		
		//begin for enddate_est
		DateTimeBean dtb=new DateTimeBean();
        dtb.setYyPart(endyear);
        dtb.setMonthPart(endmonth);
        dtb.setDdPart(endday);
        dtb.setHhPart(endhour);
        dtb.setMmPart(endmin);
        dtb.setAmpm(eampm);
        String userTimeZone=DateTime.getTimeZoneVal(timeZone);
        String toTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		DateConverter dc=new DateConverter(userTimeZone,toTimeZone);
        boolean statusdc=dc.convertDateTime(dtb);
        
        String endDate=dc.getDatePart();
        String endTime=dc.getTimePart();
        String enddate_est=endDate+" "+endTime;
        System.out.println("--- editevent enddate_est: "+enddate_est);
        //end for enddate_est
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Date today = Calendar.getInstance().getTime();
	    String reportDate = df.format(today);
	    //System.out.println("Report Date: " + reportDate);
	    if("rsvp".equals(powertype)){
	    	featuretype="90";
	    	currentfee="0";
	    	currentlevel="90";
	    }else{
	    	featuretype="100";
	    	currentfee="1";
	    	currentlevel="100";
	    }
	    try{
	    	 
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	Date date1 = sdf.parse(enddate);
        	Date date2 = sdf.parse(reportDate);
 
        	//System.out.println(sdf.format(date1));
        	//System.out.println(sdf.format(date2));
 
        	if(date1.compareTo(date2)>0){
        		System.out.println("Date1 is after Date2");
        		String Query="Update eventinfo set status=? where eventid=CAST(? AS BIGINT)";
        		DbUtil.executeUpdateQuery(Query,new String [] {"ACTIVE",eid});
        	}
 
    	}catch(ParseException ex){
    		ex.printStackTrace();
    	}
    	
    	
		String updateQuery = "update eventinfo set feature_type=?,listtype=?,eventname=?, description=?, category=?,event_type=?,"
				+ " city=?, state=?, country=?,email=?,"
				+ " address1=?,address2=?,updated_by='AddEventDB!Update',updated_at=now()," +
						"type=?,descriptiontype=?,tags=?, venue=?,region=?,start_date=to_date(?,'yyyy-mm-dd')," +
						"end_date=to_date(?,'yyyy-mm-dd'),starttime=?,endtime=?,enddate_est=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),current_level=?,current_fee=CAST(? as float)" +
						" where eventid=CAST(? AS BIGINT)";

		StatusObj updateStatusObj = DbUtil.executeUpdateQuery(updateQuery,
				new String[] { featuretype,listingprivacy, eventTitle, description,
						category, subcategory, city, state, country, email,
						address1, address2, listtype, descriptiontype, tags,
						venue, region,startdate,enddate,starttime,endtime,enddate_est,currentlevel,currentfee,eid});
		//System.out.println("updateStatusObj: "+updateStatusObj.getStatus());		
		
		
		String[] tagarr = null;
		if (tags != null && !"".equals(tags.trim())) {
			tagarr = EditEventDB.strToArrayStr(tags, ",");
		}
		DbUtil.executeUpdateQuery("delete from event_tags where eventid=?",
				new String[] { eid });
		if (tagarr != null && tagarr.length > 0) {
			for (int k = 0; k < tagarr.length; k++) {
				DbUtil.executeUpdateQuery(
						"insert into event_tags(eventid,tags) values(?,?)",
						new String[] { eid, tagarr[k] });
			}
		}
		String configid = DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[] { eid });

		if("rsvp".equals(powertype)){
			DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? as integer) and name='event.rsvp.enabled'",new String[] { configid });
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? as integer),'event.rsvp.enabled',?)",	new String[] { configid, "yes" });	
		}else	
			DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? as integer) and name='event.rsvp.enabled'",new String[] { configid });
		
		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name='event.hostname' ",new String[] { configid });
		DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.hostname',?)",	new String[] { configid, name });

		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name='event.timezone' ",new String[] { configid });
		DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.timezone',?)",	new String[] { configid, timeZone });

		setGeocodes(address1,address2,city,state,country,eid);
		boolean isRecurringfromdb = EventDB.isRecurringEvent(eid);
		//System.out.println("isRecurring:"+isRecurring+":isRRecurringfromdb:"+isRecurringfromdb);
		if("true".equalsIgnoreCase(recurring)){
			if(isRecurringfromdb == false){
				//EventDB.updatePriceStartDates(eid);
				//This is the case when we are changing a Regular event to Recurring event
				EventDB.getregTicketsData(eid);
			}
			DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name='event.recurring' ",new String[] { configid });
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.recurring',?)",	new String[] { configid, "Y" });
			
			EditEventDB.updateEventDates(eid,recurringdates);
			if(isRecurringfromdb == false){
				TransactionDB.convertTransaction(eid);
			}
			
			
			
		}
		
		else
		{	
			if(isRecurringfromdb == true){
				
				//This is  the case when we are changing Recurring event to Regular event
				EventDB.updateNonrecurrTicketsData(eid);

			}
			DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name='event.recurring' ",new String[] { configid });
			
			DbUtil.executeUpdateQuery("delete from event_dates where eventid = CAST(? AS BIGINT)", new String[]{eid});
			
			HashMap vcal=new HashMap();
			vcal.put(VCal.ID,eid);
			vcal.put(VCal.PURPOSE,"event");
			vcal.put(VCal.DESCRIPTION,description);
			vcal.put(VCal.NAME,eventTitle);
			vcal.put(VCal.LOCATION,city);
			DateTime stime=new DateTime(startyear,startmonth,startday,starthour,startmin,sampm);
			DateTime etime=new DateTime(endyear,endmonth,endday,endhour,endmin,eampm);
			stime.setHour12(starthour);
			stime.setAmpm(sampm);
			etime.setHour12(endhour);
			etime.setAmpm(eampm);
			
			vcal.put(VCal.STARTTIME,stime.getDateTimeForVCal(DateTime.getTimeZoneVal(timeZone)));
			vcal.put(VCal.ENDTIME,etime.getDateTimeForVCal(DateTime.getTimeZoneVal(timeZone)));

			VCal.createFile(vcal);

		}
		
			
	}

	
	public static String []getEstDates(String zonetime,String zonedate,String userTimeZone){
		String []esttimes=new String[2];
		
		try{
			System.out.println("zonetime--"+zonetime);
			System.out.println("zonedate--"+zonedate);
		String[] times=zonetime.split(" ");
		String []dates=zonedate.split("-");
		System.out.println("time part after splitting--"+times[0]);
		String [] time=times[0].split(":");
		String ampm=times[1];
		DateTimeBean dtb=new DateTimeBean();
		dtb.setYyPart(dates[0]);
        dtb.setMonthPart(dates[1]);
        dtb.setDdPart(dates[2]);
        dtb.setHhPart(time[0]);
        System.out.println(time[0]);
        System.out.println(time[1]);
        dtb.setMmPart(time[1]);
        dtb.setAmpm(ampm);
        dtb.setTimeZone(userTimeZone);
         String toTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
	    DateConverter dc=new DateConverter(userTimeZone,toTimeZone);
	    boolean statusdc=dc.convertDateTime(dtb);
	    System.out.println("statusdc"+statusdc);
	    esttimes[0]=dc.getDatePart();
	    esttimes[1]=dc.getTimePart();
		}
		catch(Exception e){
			
			System.out.println("Exception in timezone conversion--"+e.getMessage());
		}
		return esttimes;
	}
	
	
	public static HashMap getSpecialFee(String mgrid,String registrationtype,String accounttype){
		HashMap hm=new HashMap();
		try{
			String currentLevel="";
			String currentFee="";
			DBManager dbmanager=new DBManager();
			StatusObj statobj=null;
			if("Gold".equalsIgnoreCase(accounttype) || "Platinum".equalsIgnoreCase(accounttype)){
				if("RSVP".equalsIgnoreCase(registrationtype)){
					currentFee="0";
					currentLevel="150";
				}else{
					currentFee="1";
					currentLevel="400";
				}
			}else{
			String nonprofitstatus=DbUtil.getVal("select 'Y' from ebee_special_fee where userid=?", new String[]{mgrid});
			if("Y".equalsIgnoreCase(nonprofitstatus))
			  statobj=dbmanager.executeSelectQuery("select ln100,ln200,ln300,ln400,ln90,ln150 from international_pricing where currency_code='USD'", null);
			else
              statobj=dbmanager.executeSelectQuery("select l100,l200,l300,l400,l90,l150 from international_pricing where currency_code='USD'", null);
				
			if(statobj.getStatus()){
				try{
				if(!registrationtype.equals("rsvp")){
					if("Y".equalsIgnoreCase(nonprofitstatus)){
						if(Double.parseDouble(dbmanager.getValue(0, "ln100", "")) == Double.parseDouble(dbmanager.getValue(0, "ln200", ""))){
							if(Double.parseDouble(dbmanager.getValue(0, "ln200", "")) == Double.parseDouble(dbmanager.getValue(0, "ln300", ""))){
								if(Double.parseDouble(dbmanager.getValue(0, "ln300", "")) == Double.parseDouble(dbmanager.getValue(0, "ln400", ""))){
									currentLevel = "400";
									currentFee = dbmanager.getValue(0, "ln400", "");
								}else{
									currentLevel = "300";
									currentFee = dbmanager.getValue(0, "ln300", "");
								}
							}else{
								currentLevel = "200";
								currentFee = dbmanager.getValue(0, "ln200", "");
							}
						}else {
								currentLevel = "100";
								currentFee = dbmanager.getValue(0, "ln100", "");
						}
						
					}else{
						if(Double.parseDouble(dbmanager.getValue(0, "l100", "")) == Double.parseDouble(dbmanager.getValue(0, "l200", ""))){
							if(Double.parseDouble(dbmanager.getValue(0, "l200", "")) == Double.parseDouble(dbmanager.getValue(0, "l300", ""))){
								if(Double.parseDouble(dbmanager.getValue(0, "l300", "")) == Double.parseDouble(dbmanager.getValue(0, "l400", ""))){
									currentLevel = "400";
									currentFee = dbmanager.getValue(0, "l400", "");
								}else{
									currentLevel = "300";
									currentFee = dbmanager.getValue(0, "l300", "");
								}
							}else{
								currentLevel = "200";
								currentFee = dbmanager.getValue(0, "l200", "");
							}
						}else {
								currentLevel = "100";
								currentFee = dbmanager.getValue(0, "l100", "");
						}
					}
					
			}else{
					if("Y".equalsIgnoreCase(nonprofitstatus)){
						if(Double.parseDouble(dbmanager.getValue(0,"ln90","")) == Double.parseDouble(dbmanager.getValue(0,"ln150",""))){
							currentLevel = "150";
							currentFee = dbmanager.getValue(0,"ln150","");
						}else{
							currentLevel = "90";
							currentFee = dbmanager.getValue(0,"ln90","");
						}
					}else{
						if(Double.parseDouble(dbmanager.getValue(0,"l90","")) == Double.parseDouble(dbmanager.getValue(0,"l150",""))){
							currentLevel = "150";
							currentFee = dbmanager.getValue(0,"l150","");
						}else{
							currentLevel = "90";
							currentFee = dbmanager.getValue(0,"l90","");
						}
					}
				}
		      }catch(Exception e){
					System.out.println("Exception in In getSpecialFee Error: "+e.getMessage());
					if(registrationtype.equals("rsvp")){
						currentLevel = "90";
						currentFee = "0";
					}else{
						currentLevel = "100";
						currentFee = "1";
					}
				}
				
			}
			}
			
			hm.put("currentlevel", currentLevel);
			hm.put("currentfee", currentFee);
			
		}catch(Exception e){
			System.out.println("Exception AddEventDB.java In getSpecialFee Error:"+mgrid+" :: "+e.getMessage());
		}
		System.out.println("getSpecialFeeMap in listing event for manager ::"+mgrid+" :: "+hm);
		return hm;
	}
	
	public static String getMgrEventStaus(String mgrid){
        String req="",query="";
		query="select value from mgr_config where userid=0 and name='card.required.eventlisting'";
		req=DbUtil.getVal(query,null);
	    if ("N".equals(req)) return "nocard";
	    query="select value from mgr_config where userid=Cast(? as integer) and name='card.popup.required'";
		req=DbUtil.getVal(query,new String[]{mgrid});
		if ("N".equals(req)) return "nocard";
		query="select value from mgr_config where userid=Cast(? as integer) and name='card.required.eventlisting'";
		req=DbUtil.getVal(query,new String[]{mgrid});
		if ("N".equals(req)) return "nocard";
		String accounttype=UpgradeEventDB.getAccountType(mgrid);
		if(!"basic".equalsIgnoreCase(accounttype)) return "nocard";
		String cardstatus=CardProcessorDB.getManagerCCStatus(mgrid);
		if("Y".equalsIgnoreCase(cardstatus)) return "nocard";
		return "askcard";	
		}
	
	public static String getManagerShowCardStatus(){
	String query="select value from mgr_config where userid=0 and name='card.required.eventlisting'";	
	String showcardstatus=DbUtil.getVal(query,null);	
	if(showcardstatus==null)showcardstatus="Y";
	return showcardstatus;
	}
	
	public static void insertEventListTrack(String mgrid,AddEventData addEventData,String isrecurring){
	String createdat=DateUtil.getCurrDBFormatDate();
	String descriptiontype=addEventData.getDescriptiontype();
	String recurringtype="",description="";
	if("true".equals(isrecurring))
		recurringtype="Y";
	else
		recurringtype="N";
	if("wysiwyg".equals(descriptiontype)) 
		description=addEventData.getDescription();
	else
		description=addEventData.getTextcontent();
	String query="insert into eventlist_track(eventname,recurringtype,eventtype,description,created_at,mgr_id) values(?,?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:ss.S'),?)";
	DbUtil.executeUpdateQuery(query, new String[]{addEventData.getEventTitle(),recurringtype,addEventData.getPowerWithType(),description,createdat,mgrid});
	System.out.println("data inserted successfully in eventlisttrack"); 
	}
	
	public static void insertEventHitSummary(String eventid){
	 String query="insert into  hits_summary(visit_count,id,resource) values('0',?,'Event Page')";
	 String chksummaryentry=DbUtil.getVal("select 'Y' from hits_summary where id=?", new String[]{eventid});
	 if(chksummaryentry==null)chksummaryentry="N";
	 if(!"Y".equalsIgnoreCase(chksummaryentry))
	 DbUtil.executeUpdateQuery(query, new String[]{eventid});
	 System.out.println("successfully inserted into event hits summary :"+eventid);
	}
	
	public static String getEvtsCount(String mgrid){
		System.out.println("mgrid in addevent db getEvtsCount::"+mgrid);
		String count = DbUtil.getVal("select count(*) from eventinfo where to_char(created_at,'YYYY-MM-DD') = to_char(now(),'YYYY-MM-DD') and mgr_id=cast(? as integer)",new String[]{mgrid});
		System.out.println("count in add event db::"+count);
		return count;
	}
	
	public static String getTicketingEvtsCount(String mgrid){
		String count = DbUtil.getVal("select count(*) from price where evt_id in(select eventid from eventinfo where mgr_id=cast(? as integer))",new String[]{mgrid});
		System.out.println("count in add event db::"+count);
		return count;
	}
	

	
	public static ArrayList<String> getKeywords(){
		String Query = "select pattern from  blocked_description_patterns";		
		DBManager db=new DBManager();
		StatusObj sb=null;
		sb = db.executeSelectQuery(Query, null);
		ArrayList<String> pattern = new ArrayList<String>();
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){				
				pattern.add(db.getValue(i,"pattern",""));
			}
		}		
		return pattern;
	}
	
	public static boolean checkeventid(String eid){
		boolean flag = false;
		String exists = DbUtil.getVal("select 'Y' from emptied_description_events where eventid=cast(? as integer)", new String[]{eid});		
		if("Y".equals(exists))
			flag = true;
		
		return flag;
	} 
}
