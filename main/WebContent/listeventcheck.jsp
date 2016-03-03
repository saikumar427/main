<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.HashMap,java.util.List,org.apache.log4j.Logger,com.event.beans.AddEventData, com.eventbee.beans.DateTime, com.eventbee.beans.DateTimeBean, com.eventbee.beans.Entity, com.eventbee.general.DBManager, com.eventbee.general.DateConverter, com.eventbee.general.DbUtil, com.eventbee.general.EbeeConstantsF, com.eventbee.general.StatusObj, com.eventbee.general.VCal, com.eventbee.geocodes.Geocode, com.eventbee.geocodes.GeocodeGenerator, com.eventbee.namedquery.NQDbUtil, com.eventbee.namedquery.NQStatusObj, com.eventbee.util.Address"%>


<%!
public void setGeocodes(String address1,String address2,String city,String state,String country,String eid,JspWriter out){
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
			out.println("<br>update geo codes");
			}
	}catch(Exception e){
		try{
			out.println("<br> exception in updating geo codes"+e.getMessage());
		}
		catch(IOException ioe){
			
		}
	}
}

%>
<%

final  String SEQ_GET="99999";
//public void createEvent(AddEventData addEventData,String eid,String uid,String fckdesc,String isRecurring){
	
	String eventTitle="Event Listing Checking";
	String venue="sdfsdf";
	String tags="";
	String city="hyd";
	String email="hemanth.pajworld@gmail.com";
	String listtype="ticketing";
	String listingprivacy="public";
	String address1="";
	String address2="";
	String country="india";
	String state="ap";
	String category="art";
	String subcategory="art";
	String description="";
	String descriptiontype="text";
	String startyear="2011";
	String startmonth="05";
	String startday="01";
	String endyear="2011";
	String endmonth="06";
	String endday="01";
	String currency="USD";
	String registrationtype="RSVP";
	String evtlevel="2";
	String listatebee="yes";
	String timezone="PST";
	String startdate=startyear+"-"+startmonth+"-"+startday;
	String enddate=endyear+"-"+endmonth+"-"+endday;
	String name="jsp test event";
	String starthour="09";
	String startmin="00";
	String endhour="10";
	String endmin="00";

	String sampm="AM";
	String eampm="PM";

	String stttime=DateConverter.getHH24(starthour,sampm);
	String entime=DateConverter.getHH24(endhour,eampm);
	String starttime=stttime+":"+startmin;
	String endtime=entime+":"+endmin;
	String unitid="13579";
	String eventcode="";
	HashMap inParams = new HashMap();
	inParams.put("eventid","999999999");
	inParams.put("mgr_id","42918");
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
	try{
		out.println("<br>before insert into eventinfo");
	NQStatusObj obj= NQDbUtil.executeQuery("ListingQuery", inParams);
	out.println("<br>inserted into eventinfo");
	setGeocodes(address1,address2,city,state,country,"999999999",out);
	out.println("<br>address1: "+address1);
	out.println("<br>address2: "+address2);
	HashMap vcal=new HashMap();
	vcal.put(VCal.ID,"999999999");
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
	try{
	out.println("<br>vcal create file");
	//VCal.createFile(vcal);
	}
	catch(Exception vce){
		out.println("<br>Exception in vcal create file");
	}
	out.println("<br>vacal file created");
	String CONFIG_STREAMER_INSERT="insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'eventpage.streamer.show','No')";
	String CONFIG_MAP_INSERT="insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'eventpage.map.show','Yes')";
	String CONFIG_TIMEZONE_INSERT="insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'event.timezone',?)";
	String CONFIG_URLMAP_INSERT="insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'event.urlmap','No')";
	String CONFIG_SHOWINSEARCH_INSERT="insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'event.publish.showinsearch','yes')";
	String EVNET_CONFIG_INSERT="insert into EVENT_CONFIG(eventid,rsvp_type,rsvp_count) values (CAST(? AS BIGINT),'0','0')";
	String CONFIG_HOSTNAME_INSERT="insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'event.hostname',?)";
	String CONFIG_POWERTYPE_INSERT="insert into config(config_id,name,value) values(to_number(?,'99999999999999'),?,'yes')";
	String CONFIG_RECURRINGEVENT_INSERT="insert into config(config_id,name,value) values(to_number(?,'99999999999999'),'event.recurring','Y')";
	out.println("<br>before insert in to config");
	String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"999999999"});
	DbUtil.executeUpdateQuery(CONFIG_STREAMER_INSERT,new String [] {configid});
	DbUtil.executeUpdateQuery(CONFIG_MAP_INSERT,new String [] {configid});
	DbUtil.executeUpdateQuery(CONFIG_TIMEZONE_INSERT,new String [] {configid,timezone});
	DbUtil.executeUpdateQuery(CONFIG_URLMAP_INSERT,new String [] {configid});
	DbUtil.executeUpdateQuery(CONFIG_SHOWINSEARCH_INSERT,new String [] {configid});
	DbUtil.executeUpdateQuery(CONFIG_HOSTNAME_INSERT,new String [] {configid,name});
	out.println("<br>after insert in to config");

	DbUtil.executeUpdateQuery(EVNET_CONFIG_INSERT,new String [] {"999999999"});
	DbUtil.executeUpdateQuery("update eventinfo set starttime=?,endtime=?, status='ACTIVE'  where eventid=CAST(? AS BIGINT)",new String[]{starttime,endtime, "999999999"});
	String configregtypename="";
	if("ticketing".equals(registrationtype)){
		configregtypename="event.poweredbyEB";
		DbUtil.executeUpdateQuery("delete from event_currency where eventid=? ",new String [] {"999999999"});
		DbUtil.executeUpdateQuery("insert into event_currency(eventid,currency_code) values(?,?)",new String [] {"999999999",currency});
		DbUtil.executeUpdateQuery(CONFIG_POWERTYPE_INSERT,new String [] {configid,configregtypename});
	}
		String value="YES";
	if(value==null || "".equals(value.trim())){
	String evtcode=eventcode;
	if(evtcode==null || "".equals(evtcode.trim())){
	evtcode=eventTitle+"_"+startmonth+" "+startday+" "+startyear;
	}
	HashMap hm=new HashMap();
	hm.put("mgrid","42918");
	hm.put("unitid",unitid);
	hm.put("configid",configid);
	hm.put("evtcode",evtcode.trim());
	hm.put("evtid","999999999");
	//int rcount=(new EventDB()).InsertMemberList(hm);
	hm=null;
	}
	}catch(Exception ex){
		out.println("<br>"+ex.toString());
	}

%>
