package com.event.dbhelpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.eventbee.util.Address;
import com.event.beans.EditEventData;
import com.eventbee.beans.DateTime;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.beans.Entity;
import com.eventbee.general.*;
import com.eventbee.geocodes.*;
import com.myevents.actions.AddEventAction;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class EditEventDB {
	private static final Logger log = Logger.getLogger(EditEventDB.class);
	private static final String Y = "Y";
	private static final String event = null;  
	public static EditEventData getEventinfo(String eid) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		EditEventData editEventDataObj = new EditEventData();
		String EVENT_INFO_QUERY = "select tags,type,descriptiontype,category,code,eventname, description,"
				+ " longitude,latitude, event_type,evt_level,venue,address1,address2,city, state,"
				+ " country,region,config_id,unitid,email, phone, comments,role,listebee,listtype, "
				+ " to_char(start_date,'yyyy') as start_yy, to_char(start_date,'mm') as start_mm, "
				+ " to_char(created_at,'Month DD, YYYY') as  display_date, "
				+ " to_char(start_date,'dd') as start_dd, "
				+ " to_char(to_timestamp('2003-10-10 '||COALESCE(starttime,'00'),'yyyy-dd-mm HH24:MI'),'HH24') "
				+ " as start_hh,"
				+ " to_char(to_timestamp('2003-10-10 '||COALESCE(starttime,'00'),'yyyy-dd-mm HH24:MI'),'MI') "
				+ " as start_mi,"
				+ " to_char(end_date,'yyyy') as end_yy, to_char(end_date,'mm') as end_mm, "
				+ " to_char(end_date,'dd') as end_dd, to_char(to_timestamp('2003-10-10 '||COALESCE(endtime,'00'),'yyyy-dd-mm HH24:MI'),'HH24') "
				+ " as end_hh,to_char(to_timestamp('2003-10-10 '||COALESCE(endtime,'00'),'yyyy-dd-mm HH24:MI'),'MI') "
				+ " as end_mi,mgr_id "
				+ " from eventinfo where eventid=CAST(? AS BIGINT) ";
		statobj = dbmanager.executeSelectQuery(EVENT_INFO_QUERY,
				new String[] { eid });
		int count1 = statobj.getCount();
		if (statobj.getStatus() && count1 > 0) {
			String venue = dbmanager.getValue(0, "venue", "");
			String tags = dbmanager.getValue(0, "tags", "");
			String eventTitle = dbmanager.getValue(0, "eventname", "");
			String city = dbmanager.getValue(0, "city", "");
			String email = dbmanager.getValue(0, "email", "");
			String listtype = dbmanager.getValue(0, "type", "");
			String listingprivacy = dbmanager.getValue(0, "listtype", "");
			String address1 = dbmanager.getValue(0, "address1", "");
			String address2 = dbmanager.getValue(0, "address2", "");
			String country = dbmanager.getValue(0, "country", "");
			String state = dbmanager.getValue(0, "state", "");
			String region = dbmanager.getValue(0, "region", "");
			String category = dbmanager.getValue(0, "category", "");
			String subcategory = dbmanager.getValue(0, "event_type", "");
			String description = dbmanager.getValue(0, "description", "");
			String descriptiontype = dbmanager.getValue(0, "descriptiontype","");
			String startyear = dbmanager.getValue(0, "start_yy", "");
			String startmonth = dbmanager.getValue(0, "start_mm", "");
			String startday = dbmanager.getValue(0, "start_dd", "");
			String endyear = dbmanager.getValue(0, "end_yy", "");
			String endmonth = dbmanager.getValue(0, "end_mm", "");
			String endday = dbmanager.getValue(0, "end_dd", "");
			String starthour = dbmanager.getValue(0, "start_hh", "");
			String startmin = dbmanager.getValue(0, "start_mi", "");
			String endhour = dbmanager.getValue(0, "end_hh", "");
			String endmin = dbmanager.getValue(0, "end_mi", "");
			String mgrid=dbmanager.getValue(0, "mgr_id", "");
			String latitude=dbmanager.getValue(0, "latitude", "");
			String longitude=dbmanager.getValue(0, "longitude", "");
			//System.out.println("latitude: "+latitude+" longitude: "+longitude);
			editEventDataObj.setVenue(venue);
			editEventDataObj.setTags(tags);
			editEventDataObj.setEventTitle(eventTitle);
			editEventDataObj.setCity(city);
			editEventDataObj.setEmail(email);
			editEventDataObj.setListtype(listtype);
			editEventDataObj.setListingprivacy(listingprivacy);
			editEventDataObj.setAddress1(address1);
			editEventDataObj.setAddress2(address2);
			editEventDataObj.setCountry(country);
			editEventDataObj.setState(state);
			editEventDataObj.setRegion(region);
			editEventDataObj.setCategory(category);
			editEventDataObj.setSubcategory(subcategory);
			
			/*try{
			byte[] bytes = description.getBytes();
			description = new String(bytes, "UTF-8");
			System.out.println("description after converting utf-8"+description);
			}catch(Exception e){}*/
			editEventDataObj.setDescription(description);
			editEventDataObj.setTextcontent(description);
			editEventDataObj.setDescriptiontype(descriptiontype);
			editEventDataObj.setStartyear(startyear);
			editEventDataObj.setStartmonth(startmonth);
			editEventDataObj.setStartday(startday);
			editEventDataObj.setEndyear(endyear);
			editEventDataObj.setEndmonth(endmonth);
			editEventDataObj.setEndday(endday);
			editEventDataObj.setStartHour(starthour);
			editEventDataObj.setStartminute(startmin);
			editEventDataObj.setEndHour(endhour);
			editEventDataObj.setEndminute(endmin);
			editEventDataObj.setMgrId(mgrid);
			editEventDataObj.setLatitude(latitude);
			editEventDataObj.setLongitude(longitude);
			try{
				String configID=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
				String i18nLang=DbUtil.getVal("select value from  config where config_id=CAST(? AS INTEGER)  and name='event.i18n.lang'", new String[]{configID}); 
				if(i18nLang!=null && !"".equals(i18nLang))
				  editEventDataObj.setI18nLang(i18nLang);
			}catch(Exception e){
				System.out.println("getting i18nLang from config::"+eid);
			}
		}
		return editEventDataObj;
	}

	public static void setGeocodes(String address1, String address2,
			String city, String state, String country, String eid) {
		GeocodeGenerator geocodegenerator = null;
		try {
			Object obj = (Object) Class.forName(
					EbeeConstantsF.get("geocodes.provider.class",
							"com.eventbee.geocodes.GeocodeAmericaREST"))
					.newInstance();
			geocodegenerator = (GeocodeGenerator) obj;
			String straddress = address1 + "%" + address2;
			com.eventbee.util.Address address = new Address(straddress, city,
					state, "", country);
			geocodegenerator.setAddress(address);
			Geocode geocode = geocodegenerator.getGeocodes();
			if (geocode.getStatus()) {
				StatusObj obj1=DbUtil.executeUpdateQuery("update eventinfo set latitude=?,longitude=? where eventid=CAST(? AS BIGINT)",new String []{geocode.getLatitude(),geocode.getLongitude(),eid});
			}else{
				DbUtil.executeUpdateQuery("update eventinfo set latitude=?,longitude=? where eventid=CAST(? AS BIGINT)",new String []{null,null,eid});
				DbUtil.executeUpdateQuery("insert into googlemap_track(source,date,eventid) values('Edit Event',to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),?)",new String []{DateUtil.getCurrDBFormatDate(),eid});
			}	
		} catch (Exception e) {
			System.out.println("Exception in EditEventDB setGeocodes: "+e.getMessage());
		}
	}

	public static void updateEventData(EditEventData editEventData, String eid,
			String fckdesc) {
		String description="",isexits="",temp="";
		String eventTitle = editEventData.getEventTitle();
		String venue = editEventData.getVenue();
		String tags = editEventData.getTags();
		String city = editEventData.getCity();
		String email = editEventData.getEmail().trim();
		String listtype = editEventData.getListtype();
		String listingprivacy = editEventData.getListingprivacy();
		String address1 = editEventData.getAddress1();
		String address2 = editEventData.getAddress2();
		String country = editEventData.getCountry();
		String state = editEventData.getState();
		String region = editEventData.getRegion();
		String category = editEventData.getCategory();
		String subcategory = editEventData.getSubcategory();
		String descriptiontype = editEventData.getDescriptiontype();
		String latitude=editEventData.getLatitude();
		String longitude=editEventData.getLongitude();
		String registrationcount="0";
		int regcount=0;
		if ("wysiwyg".equals(descriptiontype))
		 description = editEventData.getDescription();
		else
		 description = editEventData.getTextcontent();
			//if ("wysiwyg".equals(descriptiontype))
			//description = fckdesc;
		
		String uid = editEventData.getMgrId();
		System.out.println("userid:::"+editEventData.getMgrId());
		isexits=DbUtil.getVal("select 'Y' from mgr_config where name='mgr.eventlisting.allow' and userid=cast(? as integer) and value='Y'", new String[]{uid});
		if(isexits==null)isexits = "N";
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip=AddEventAction.getIpFromRequest(request);
		if(!"Y".equals(isexits)){
			registrationcount=DbUtil.getVal("select count(*) from event_reg_transactions where  paymenttype<>'RSVP' and eventid::bigint in(select eventid from eventinfo where mgr_id=?::integer)", new String[]{uid});
			if(registrationcount==null)registrationcount="0";
			try{
				regcount=Integer.parseInt(registrationcount);
				System.out.println("the registrations count in EDIT event:"+regcount);
			}catch(Exception e){regcount=0;}
			if(regcount<=0){
			if(EventListingKeywords.keywords.size()==0)
				EventListingKeywords.keywords.addAll(AddEventDB.getKeywords());
			//if(EventListingKeywords.keywords.size()>0){		 
			for(int key=0;key<EventListingKeywords.keywords.size();key++){	
				//Pattern pattern = Pattern.compile(EventListingKeywords.keywords.get(key)); //commented on 29 sep 2014 for handling special chars 
				//if(pattern.matcher(description.toLowerCase()).find() || pattern.matcher(eventTitle.toLowerCase()).find()){
				if(description.toLowerCase().replaceAll("&lt;", "<").replaceAll("&gt;", ">").indexOf(EventListingKeywords.keywords.get(key))>-1 || eventTitle.toLowerCase().indexOf(EventListingKeywords.keywords.get(key))>-1){
					System.out.println("user blocked pattern: "+EventListingKeywords.keywords.get(key)+" userid: "+uid+" eventid: "+eid);
				    DbUtil.executeUpdateQuery("insert into emptied_description_events(eventid,description,created_at,ip,userid,title,pattern) values(cast(? as bigint),?,now(),?,CAST(? as BIGINT),?,?)",new String[]{eid,description,ip,uid,eventTitle,EventListingKeywords.keywords.get(key)});
					DbUtil.executeUpdateQuery("update authentication set accounttype='blocked',blocked_by='event_desc_pattern',blocked_at=now() where user_id=?", new String[]{uid});
					String query="insert into suspected_user_activities(userid,ip,action,created_at,pattern) values(?,?,'edit_event_db',now(),?)";
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
					System.out.println("from db");
					ArrayList<String> keywords = new ArrayList<String>();
					keywords = AddEventDB.getKeywords();
					EventListingKeywords.keywords.addAll(keywords);					
					for(int i=0;i<keywords.size();i++){
						//Pattern pattern = Pattern.compile(keywords.get(i));
						//if(pattern.matcher(description.toLowerCase()).find() || pattern.matcher(eventTitle.toLowerCase()).find()){
						if(description.toLowerCase().indexOf(keywords.get(i))>-1 || eventTitle.toLowerCase().indexOf(keywords.get(i))>-1){
							System.out.println("from static block::else case: "+keywords.get(i)+" userid:::"+uid);
							DbUtil.executeUpdateQuery("insert into emptied_description_events(eventid,description,created_at,ip,userid,title,pattern) values(cast(? as bigint),?,now(),?,CAST(? as BIGINT),?,?)",new String[]{eid,description,ip,uid,eventTitle,keywords.get(i)});
							DbUtil.executeUpdateQuery("update authentication set accounttype='blocked' where user_id=?", new String[]{uid});
							String query="insert into suspected_user_activities(userid,ip,action,created_at,pattern) values(?,?,'edit_event_db',now(),?)";
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
				} */
		
		}
		}
		String startyear = editEventData.getStartyear();
		String startmonth = editEventData.getStartmonth();
		String startday = editEventData.getStartday();
		String endyear = editEventData.getEndyear();
		String endmonth = editEventData.getEndmonth();
		String endday = editEventData.getEndday();
		String name = editEventData.getName();
		String timeZone = editEventData.getTimezones();
		String startdate=startyear+"-"+startmonth+"-"+startday;
		String enddate=endyear+"-"+endmonth+"-"+endday;	
		String starthour=editEventData.getStartHour();
		String startmin=editEventData.getStartminute();
		String endhour=editEventData.getEndHour();
		String endmin=editEventData.getEndminute();
		String sampm=editEventData.getStartampm();
		String eampm=editEventData.getEndampm();
		
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
    	String updateQuery="";
    	if("cancel".equals(temp)){
    		updateQuery = "update eventinfo set status='CANCEL',listtype=?,eventname=?, description=?, category=?,event_type=?,"
				+ " city=?, state=?, country=?,email=?,"
				+ " address1=?,address2=?,updated_by='EditEventDB!Update',updated_at=now()," +
						"type=?,descriptiontype=?,tags=?, venue=?,region=?,start_date=to_date(?,'yyyy-mm-dd')," +
						"end_date=to_date(?,'yyyy-mm-dd'),starttime=?,endtime=?,enddate_est=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS')," +
						"cancel_by='event_desc_pattern' where eventid=CAST(? AS BIGINT)";
    	}else{
    	
		updateQuery = "update eventinfo set listtype=?,eventname=?, description=?, category=?,event_type=?,"
				+ " city=?, state=?, country=?,email=?,"
				+ " address1=?,address2=?,updated_by='EditEventDB!Update',updated_at=now()," +
						"type=?,descriptiontype=?,tags=?, venue=?,region=?,start_date=to_date(?,'yyyy-mm-dd')," +
						"end_date=to_date(?,'yyyy-mm-dd'),starttime=?,endtime=?,enddate_est=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS')" +
						" where eventid=CAST(? AS BIGINT)";
    	}
		StatusObj updateStatusObj = DbUtil.executeUpdateQuery(updateQuery,
				new String[] { listingprivacy, eventTitle, description,
						category, subcategory, city, state, country, email,
						address1, address2, listtype, descriptiontype, tags,
						venue, region,startdate,enddate,starttime,endtime,enddate_est,eid });
		//System.out.println("updateStatusObj: "+updateStatusObj.getStatus());		
		
		
		String[] tagarr = null;
		if (tags != null && !"".equals(tags.trim())) {
			tagarr = strToArrayStr(tags, ",");
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

		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name='event.hostname' ",new String[] { configid });
		DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.hostname',?)",	new String[] { configid, name });

		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name='event.timezone' ",new String[] { configid });
		DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.timezone',?)",	new String[] { configid, timeZone });
		
		DbUtil.executeUpdateQuery("update config set value=? where config_id=CAST(? AS INTEGER) and name='event.i18n.lang'",	new String[] { editEventData.getI18nLang(),configid});
		
		DbUtil.executeUpdateQuery("update eventinfo set latitude=?,longitude=? where eventid=CAST(? AS BIGINT)",new String []{latitude,longitude,eid});
		//setGeocodes(address1,address2,city,state,country,eid); //commented while doing bootstrap.
		boolean isRecurringfromdb = EventDB.isRecurringEvent(eid);
		//System.out.println("isRecurring:"+isRecurring+":isRRecurringfromdb:"+isRecurringfromdb);
		if("true".equalsIgnoreCase(editEventData.getRecurringcheck())){
			if(isRecurringfromdb == false){
				//EventDB.updatePriceStartDates(eid);
				//This is the case when we are changing a Regular event to Recurring event
				EventDB.getregTicketsData(eid);
			}
			DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name='event.recurring' ",new String[] { configid });
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(CAST(? AS INTEGER),'event.recurring',?)",	new String[] { configid, Y });
			
			updateEventDates(eid,editEventData.getRecurringDates());
			if(isRecurringfromdb == false){
				TransactionDB.convertTransaction(eid);
			}
			
			updateTicketEndDates(eid);
			
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

			//VCal.createFile(vcal);
			(new Thread(new VCalThread(vcal))).start();
		}
		
			
	}
	private static void updatePriceDates(String eid) {
		// TODO Auto-generated method stub
		
	}

	private static void updatepriceDates(String eid) {
		// TODO Auto-generated method stub
		
	}

	
	
	public static void updateEventDates(String eid,List<Entity> list){
		try {
			
			String DELETE_EVENTDATES = "delete from event_dates where eventid = CAST(? AS BIGINT)";
			//System.out.println("inside updateeventdates");
			DbUtil.executeUpdateQuery(DELETE_EVENTDATES, new String[]{eid});
			if(list!=null){
				AddEventDB.createRecurringEvent(eid,list);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static String[] strToArrayStr(String schdatestr, String delim) {
		return strToArrayStr(schdatestr, delim, false);
	}

	public static String[] strToArrayStr(String schdatestr, String delim,
			boolean returnDelims) {
		if (schdatestr == null)
			return new String[] { "" };
		StringTokenizer st = new StringTokenizer(schdatestr, delim,
				returnDelims);
		String[] datestra = new String[st.countTokens()];
		for (int i = 0; i < datestra.length; i++) {
			datestra[i] = ((String) st.nextToken());
		}
		return datestra;
	}

	final static String GET_EVENTMEMBER_CLUBS = " select distinct c.clubid,clubname,c1.updated_by,c.config_id from clubinfo c "
			+ " ,club_member c1,config c2 where c.config_id=c2.config_id "
			+ " and (getHubMemberStatus(c.clubid,userid)='HUBMGR' "
			+ " or (getHubMemberStatus(c.clubid,userid)='HUBMEMBER' "
			+ " and getMemberEventConfigvalue(c.config_id||'')='Yes'))  "
			+ " and c.clubid=c1.clubid and userid=? ";

	public static List<Entity> getClubsForEvents(String userid) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		List<Entity> communities = new ArrayList<Entity>();
		try {
			statobj = dbmanager.executeSelectQuery(GET_EVENTMEMBER_CLUBS,
					new String[] { userid });
			int count1 = statobj.getCount();
			if (statobj.getStatus() && count1 > 0) {
				for (int k = 0; k < count1; k++) {
					String clubid = dbmanager.getValue(k, "clubid", "");
					String clubname = dbmanager.getValue(k, "clubname", "");
					communities.add(new Entity(clubid, clubname));
				}
			}
		}//End of try block
		catch (Exception e) {
			System.out.println("There is an Exception " + e.getMessage());
		}
		return communities;
	}
	
	public static List<Entity> getRecurringEventDates(String eid){
		String date_key,date_val;
		List<Entity> revtdateslist = new ArrayList<Entity>();
		try {/*
			String RECURRING_EVEBT_DATES = "select eventdate from event_dates where eventid=to_number(?,'99999999999')";
			DBManager dbmanager = new DBManager();
			StatusObj statobj = dbmanager.executeSelectQuery(RECURRING_EVEBT_DATES, new String[]{eid});
			int count = statobj.getCount();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfkey = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdfval = new SimpleDateFormat("EEE, MMM dd, yyyy");
			if (statobj.getStatus() && count > 0) {
				for (int k = 0; k < count; k++) {
					String evtdate = dbmanager.getValue(k, "eventdate", "");
					Date seldate = df.parse(evtdate);
					String date_key = sdfkey.format(seldate);
					String date_val = sdfval.format(seldate);
					revtdateslist.add(new Entity(date_key, date_val));
				}
			}*/
			String RECURRING_EVEBT_DATES = "select date_display,date_key from event_dates where eventid=CAST(? AS BIGINT)"+
			                               " order by date_key";
			DBManager dbmanager = new DBManager();
			StatusObj statobj = dbmanager.executeSelectQuery(RECURRING_EVEBT_DATES, new String[]{eid});
			
			int count = statobj.getCount();
			if (statobj.getStatus() && count > 0) {
				
				for (int k = 0; k < count; k++) {
					String date_display1 = dbmanager.getValue(k, "date_display", "");
					String date_key1 = dbmanager.getValue(k, "date_key", "");
					//Date seldate = df.parse(evtdate);
					date_key = date_key1;
					date_val = date_display1;
					revtdateslist.add(new Entity(date_key, date_val));
				}
			}
		}
		/*catch (ParseException ex) {
			// TODO: handle exception
			log.error("date Parse Exception: "+ex);
		} */
		catch (Exception ex) {			
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return revtdateslist;
	}
	
	public static List<Entity> getSummaryRecurringEventDates(String eid){
		List<Entity> revtdateslist = new ArrayList<Entity>();
		try {
//			String RECURRING_EVEBT_DATES = "select distinct b.eventdate " +
//					"from event_dates a,event_reg_transactions b " +
//			"where a.eventid=to_number(b.eventid,'999999999999999') " +
//			"and b.eventid=?";
//			
		    String RECURRING_EVEBT_DATES = "select distinct eventdate " +
			"from event_reg_transactions  where eventid=? and eventdate!=''";
			DBManager dbmanager = new DBManager();
			StatusObj statobj = dbmanager.executeSelectQuery(RECURRING_EVEBT_DATES, new String[]{eid});
			int count = statobj.getCount();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfkey = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdfval = new SimpleDateFormat("EEE, MMM dd, yyyy");
			if (statobj.getStatus() && count > 0) {
				for (int k = 0; k < count; k++) {
					String evtdate = dbmanager.getValue(k, "eventdate", "");
					//Date seldate = df.parse(evtdate);
					//String date_key = sdfkey.format(seldate);
					//String date_val = sdfval.format(seldate);
					//revtdateslist.add(new Entity(date_key, date_val));
					revtdateslist.add(new Entity(evtdate, evtdate));
				}
			
			}
		}
		catch (Exception ex) {			
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return revtdateslist;
	}
	
	public static void updateTicketEndDates(String eid){
		String selectEventDates="select end_date,endtime from eventinfo where eventid=CAST(? AS BIGINT)";
		String endDate="";
		String endTime="";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(selectEventDates,new String []{eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			     endDate=dbmanager.getValue(0,"end_date","");
			     endTime=dbmanager.getValue(0,"endtime","");

			}
		String updateprice="update price set endtime=?,end_date=to_date(?,'YYYY-MM-DD') where evt_id=CAST(? AS BIGINT)";
		DbUtil.executeUpdateQuery(updateprice,new String[]{endTime,endDate,eid});
		
	}
	
}
