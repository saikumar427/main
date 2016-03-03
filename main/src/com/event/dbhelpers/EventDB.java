package com.event.dbhelpers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eventbee.general.*;
import com.eventbee.namedquery.NQDbHelper;
import com.event.beans.EventData;
import com.event.helpers.I18n;
import com.eventbee.beans.DateTime;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.eventbee.general.formatting.CurrencyFormat;
@SuppressWarnings("unchecked")
public class EventDB {
	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";   
	final static String CONFIG_INSERT="insert into config(config_id, name, value) values (CAST(? AS INTEGER),'event.maillist.id',?)";
	public final static String GROUP_MAIL_LIST_INSERT="insert into group_mail_list(groupid,group_type,list_id) values (?,'EVENT',?)";
	static final String INSERT_LIST="insert into mail_list(manager_id,list_id,list_name,list_desc,unit_id,unsubmess,role,created_at) values(CAST(? AS INTEGER),CAST(? AS INTEGER),?,?,CAST(? AS INTEGER),?,?,now())";
	public static final String TICKETS_FOR_CUSTOM_ATTRIBS="select pr.price_id,pr.ticket_name as ticketname,etg.groupname  as groupname from price pr," +
			"event_ticket_groups etg,group_tickets gt where gt.ticket_groupid=etg.ticket_groupid and pr." +
			"price_id=CAST(gt.price_id AS INTEGER) and  etg.eventid=? and " +
			"pr.evt_id=CAST(? AS BIGINT) order by etg.position,gt.position";
	
	public static String getRecurringDate(String eventid){
		return "";
	}
	public static boolean isValidUser(String mgrid){
		if(!"blocked".equalsIgnoreCase(DbUtil.getVal("select accounttype from AUTHENTICATION where User_ID=?", new String[]{mgrid})))
		return true;
		return false;
	}
	public static EventData getEventData(String eventid){

		EventData edata=null;
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		
		String eventtype="";
		eventtype=DbUtil.getVal("select 'nonprofit' from ebee_special_fee where  userid::int=(select mgr_id from eventinfo where eventid=?::bigint) or eventid=?", new String[]{eventid,eventid});
		if(eventtype==null || "".equals(eventtype))eventtype="normalevent";

		String EVENT_INFO_QUERY =  "select a.eventid,a.eventname,a.email,a.address1,a.mgr_id,a.premiumlevel,"
			+" a.address2,a.city,a.state,a.country,a.venue,a.address1,a.address2,a.status,getMemberPref(mgr_id||'','pref:myurl','') as username,"
			+" trim(to_char(a.start_date, 'Day')) ||', '|| to_char(a.start_date, 'Month DD, YYYY') as start_date,"
			+" starttime,endtime,trim(to_char(a.end_date, 'Day')) ||', '|| to_char(a.end_date, 'Month DD, YYYY')"
			+" as end_date,a.phone,trim(to_char(a.end_date, 'Dy')) ||', '|| to_char(a.end_date, 'Mon DD, YYYY') as evt_end_date,"
			+" trim(to_char(a.start_date, 'Dy')) ||', '|| to_char(a.start_date, 'Mon DD, YYYY') as evt_start_date,"
			+" a.nts_enable,a.nts_commission,a.current_level,a.current_fee from eventinfo a where eventid=CAST(? AS BIGINT)";
		statobj=dbmanager.executeSelectQuery(EVENT_INFO_QUERY,new String []{eventid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			edata=new EventData();
			edata.setEventName(dbmanager.getValue(0,"eventname",""));
			edata.setEmail(dbmanager.getValue(0,"email",""));
			edata.setAddress1(dbmanager.getValue(0,"address1",""));
			edata.setAddress2(dbmanager.getValue(0,"address2",""));
			edata.setCity(dbmanager.getValue(0,"city",""));
			edata.setState(dbmanager.getValue(0,"state",""));
			edata.setCountry(dbmanager.getValue(0,"country",""));
			String locval=GenUtil.getCSVData(new String[]{dbmanager.getValue(0,"venue",""),dbmanager.getValue(0,"address1",""),dbmanager.getValue(0,"address2",""),dbmanager.getValue(0,"city",""),dbmanager.getValue(0,"state",""),dbmanager.getValue(0,"country","")});
			edata.setLocation(locval);
			String status=(String)dbmanager.getValue(0,"status","");
			if("ACTIVE".equals(status))	status="Active";
			if("PENDING".equals(status)) status="Suspended";
			if("CLOSED".equals(status)) status="Closed";
			if("cancel".equalsIgnoreCase(status)){
				System.out.println("blocked event logout eid::"+eventid);
				return null;
			}
			edata.setStatus(status);
			edata.setUsername(dbmanager.getValue(0,"username",""));
			edata.setStart_date(dbmanager.getValue(0,"start_date",""));
			edata.setStarttime(DateConverter.getTimeAM(dbmanager.getValue(0,"starttime","")));
			edata.setEndtime(DateConverter.getTimeAM(dbmanager.getValue(0,"endtime","")));
			edata.setEnd_date(dbmanager.getValue(0,"end_date",""));
			edata.setPhone(dbmanager.getValue(0,"phone",""));
			edata.setEvt_end_date(dbmanager.getValue(0,"evt_end_date",""));
			edata.setEvt_start_date(dbmanager.getValue(0,"evt_start_date",""));
			edata.setMgrId(dbmanager.getValue(0,"mgr_id",""));
			String currentlevel=(String)dbmanager.getValue(0,"current_level","");
			if("nonprofit".equals(eventtype)){
				if("100".equals(currentlevel) || "90".equals(currentlevel)) currentlevel="Basic";
				else if("200".equals(currentlevel) || "150".equals(currentlevel)) currentlevel="Pro-NonProfit";
				else if("300".equals(currentlevel)) currentlevel="Seating-NonProfit";
				else currentlevel="Basic-NonProfit";
				edata.setIsNonProfit("Y");
			}else{
			if("100".equals(currentlevel) || "90".equals(currentlevel)) currentlevel="Basic";
			else if("200".equals(currentlevel) || "150".equals(currentlevel)) currentlevel="Pro";
			else if("300".equals(currentlevel)) currentlevel="Seating";
			else currentlevel="Basic";
			}
            /*if("EVENT_PREMIUM_LISTING".equals(premiumlevel)) premiumlevel="Premium";
			else if("EVENT_FEATURED_LISTING".equals(premiumlevel)) premiumlevel="Featured";
			else if("EVENT_CUSTOM_LISTING".equals(premiumlevel)) premiumlevel="Custom";
			else if("".equals(premiumlevel)) premiumlevel="Basic";*/

			edata.setPremiumlevel(currentlevel);
			edata.setPoweredbyEB(PaymentSettingsDB.getPaymentConfigStatus(eventid));
			String powertype=getPowerType(eventid);
			//if("Ticketing".equals(powertype)) powertype="Tickets";
			edata.setPowertype(powertype);
			
			String hostname=getConfigVal(eventid,"event.hostname","");
			if("".equals(hostname)) hostname="";
			else hostname=hostname+",";
			edata.setHostName(hostname);
			String ntsEnable = dbmanager.getValue(0,"nts_enable","");
			if(ntsEnable.equals(""))
				ntsEnable="N";
			edata.setNtsStatus(ntsEnable);
			String ntsCommission = dbmanager.getValue(0,"nts_commission","");
			if(ntsCommission.equals(""))
				ntsCommission="0";
			edata.setNtsCommission(ntsCommission);
			edata.setCurrentLevel(dbmanager.getValue(0,"current_level",""));
			edata.setCurrentFee(dbmanager.getValue(0,"current_fee",""));
			
		}
		return edata;
	}
	public static ArrayList getTicketsData(String query,String eid){
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		ArrayList alltickets=new ArrayList();
	
		try{
			statobj=dbmanager.executeSelectQuery(query,new String []{eid,eid});
			int count1=statobj.getCount();
			if(statobj.getStatus()&&count1>0){
				for(int k=0;k<count1;k++){
					String ticketId=dbmanager.getValue(k,"price_id","");
					String ticketName=dbmanager.getValue(k,"ticketname","");
					String groupName=dbmanager.getValue(k,"groupname","");
					if(!"".equals(groupName))
						ticketName=ticketName+" ("+groupName+")";
					alltickets.add(new Entity(ticketId,ticketName));
				}
			}
			
			System.out.println("in getticketsdata "+alltickets.size());
		}//End of try block
		catch(Exception e){
			System.out.println("There is an Exception while executing the ticket selection query" +
					"for the eventid"+eid+"in Disccounts Management"+e.getMessage());
		}
		return alltickets;
	}
	
	public static boolean isSeatingEvent(String eid){
		String isSeatingEvent=DbUtil.getVal("select value from config where name='event.seating.enabled' and config_id in(select config_id from eventinfo where eventid=CAST(? AS BIGINT) )", new String[]{eid});
	if(isSeatingEvent==null || "NO".equals(isSeatingEvent))
		return false;
	else
		return true;
	}
	public static void removeTicketsFromWideWidgets(String singleWidgets,String wideWidgets,String eid){
		System.out.println("remove ticket from wide widget for pagedesign");
		String [] widgetsArr = wideWidgets.split(",");
		List<String> list = new ArrayList<String>(Arrays.asList(widgetsArr));
		list.remove("tickets");
		String data=list.toString().replace("[","").replace(" ", "").replace("]", "");
		DbUtil.executeUpdateQuery("update event_layout set wide_widgets=? where eventid=?::bigint",new String[]{data,eid});
		if("".equals(singleWidgets.trim()))
			DbUtil.executeUpdateQuery("update event_layout set single_widgets=? where eventid=?::bigint",new String[]{"tickets",eid});
		else
			DbUtil.executeUpdateQuery("update event_layout set single_widgets=?+single_widgets where eventid=?::bigint",new String[]{"tickets,",eid});
	}
	
	public static ArrayList getAllTypesOfTicketsData(String query,String eid){
		
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		ArrayList alltickets=new ArrayList();
		String Query="select name,ticketid from groupdeal_tickets where eventid=?";
		try{
			statobj=dbmanager.executeSelectQuery(query,new String []{eid,eid});
			int count1=statobj.getCount();
			if(statobj.getStatus()&&count1>0){
				for(int k=0;k<count1;k++){
					String ticketId=dbmanager.getValue(k,"price_id","");
					String ticketName=dbmanager.getValue(k,"ticketname","");
					String groupName=dbmanager.getValue(k,"groupname","");
					if(!"".equals(groupName))
						ticketName=ticketName+" ("+groupName+")";
					alltickets.add(new Entity(ticketId,ticketName));
					
				}
			}
			
			statobj=dbmanager.executeSelectQuery(Query,new String[]{eid});
			
			int count2=statobj.getCount();
			
			if(statobj.getStatus()&&count2>0){
				for(int k=0;k<count2;k++){
					String ticketId=dbmanager.getValue(k,"ticketid","");
					String ticketName=dbmanager.getValue(k,"name","");
			        alltickets.add(new Entity(ticketId,ticketName));
				}
				
			}
			System.out.println("in getalltypes of ticketsdata "+alltickets.size());
		}//End of try block
		catch(Exception e){
			System.out.println("There is an Exception while executing the ticket selection query" +
					"for the eventid"+eid+"in question Management"+e.getMessage());
		}
		return alltickets;

		
	}
	public static void updateNonrecurrTicketsData(String eid){
		
		     String eventinfoquery="select start_date from eventinfo " +
		    		               "where eventid=CAST(? AS BIGINT)";
		    String event_start_date=DbUtil.getVal(eventinfoquery,new String []{eid});
		 		if(event_start_date!=null){	 
			 String price_update_query= "update price " +
			 		"set start_date=to_date(?,'YYYY-MM-DD') - cast(start_before ||' day' as interval) ," +
			 " end_date=to_date(?,'YYYY-MM-DD') - cast(end_before ||' day' as interval) " +           
			 " where evt_id=CAST(? AS BIGINT)";
			 DbUtil.executeUpdateQuery(price_update_query,new String[]{event_start_date,event_start_date,eid});
			 
		 		}
	}
   

	public static void getregTicketsData(String eid){
		//String priceQuery="select price_id,start_date from price where evt_id=to_number(?,'99999999999999') order by price_id;" ;
		//return updateregTicketData(priceQuery,eid);

		  String eventstartquery="select start_date from eventinfo where eventid=CAST(? AS BIGINT)" ;
		  String event_start_date=DbUtil.getVal(eventstartquery,new String[]{eid});
		 if (event_start_date!=null){
		 String price_update_query="update price set "+
		                            " start_before=to_date(?,'YYYY-MM-DD')-CAST(start_date as date)," +
		                            "end_before='0',end_hours_before='0',start_hours_before='0'," +
		                            "start_minutes_before='0',end_minutes_before='0' "+
		                           " where evt_id=CAST(? AS BIGINT)";
		                           
		  DbUtil.executeUpdateQuery(price_update_query,new String[]{event_start_date,eid});
		 }
		 
		
		
	}
	
	
	public static ArrayList getEventTickets(String eid){
		String ticketSelQuery="select pr.price_id,pr.ticket_name as ticketname,etg.groupname  as groupname from price pr," +
		"event_ticket_groups etg,group_tickets gt where gt.ticket_groupid=etg.ticket_groupid and pr." +
		"price_id=CAST(gt.price_id AS INTEGER) and  etg.eventid=? and " +
		"pr.evt_id=CAST(? AS BIGINT) order by etg.position,gt.position";
		return getTicketsData(ticketSelQuery,eid);
	}
	/*public static ArrayList getAllTypesOfTickets(String eid){
		String ticketSelQuery="select pr.price_id,pr.ticket_name as ticketname,etg.groupname  as groupname from price pr," +
		"event_ticket_groups etg,group_tickets gt where gt.ticket_groupid=etg.ticket_groupid and pr." +
		"price_id=to_number(gt.price_id,'9999999999') and  etg.eventid=? and " +
		"pr.evt_id=to_number(?,'99999999999') order by etg.position,gt.position";
		return getAllTypesOfTicketsData(ticketSelQuery,eid);
	}*/
	
	public static ArrayList getAllTypesOfTickets(String eid){
		ArrayList alltickets=new ArrayList();
		alltickets=getGeneralTickets(eid);
		alltickets.addAll(getVolumeTickets(eid));
		return alltickets;
	}
	
	public static ArrayList getGeneralTickets(String eid){
		String ticketSelQuery="select pr.price_id,pr.ticket_name as ticketname,etg.groupname  as groupname from price pr," +
		"event_ticket_groups etg,group_tickets gt where gt.ticket_groupid=etg.ticket_groupid and pr." +
		"price_id=CAST(gt.price_id AS INTEGER) and  etg.eventid=? and " +
		"pr.evt_id=CAST(? AS BIGINT) order by etg.position,gt.position";
		
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		ArrayList gentickets=new ArrayList();
		try{
			statobj=dbmanager.executeSelectQuery(ticketSelQuery,new String []{eid,eid});
			int count=statobj.getCount();
			if(statobj.getStatus()&&count>0){
				for(int k=0;k<count;k++){
					String ticketId=dbmanager.getValue(k,"price_id","");
					String ticketName=dbmanager.getValue(k,"ticketname","");
					String groupName=dbmanager.getValue(k,"groupname","");
					if(!"".equals(groupName))
						ticketName=ticketName+" ("+groupName+")";
					gentickets.add(new Entity(ticketId,ticketName));
					
				}
			}
		}
		catch(Exception e){
			System.out.println("Exception in getGeneralTickets eventid"+eid+"in question Management for "+e.getMessage());
		}
		return gentickets;
	}
	
	public static JSONArray getTicketsJson(String eid){
		
		
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		JSONArray jsonticketsarray = new JSONArray();
		try{
			statobj=dbmanager.executeSelectQuery(TICKETS_FOR_CUSTOM_ATTRIBS,new String []{eid,eid});
			int count=statobj.getCount();
			if(statobj.getStatus()&&count>0){
				for(int k=0;k<count;k++){
					JSONObject json=new JSONObject();
					String ticketId=dbmanager.getValue(k,"price_id","");
					String ticketName=dbmanager.getValue(k,"ticketname","");
					String groupName=dbmanager.getValue(k,"groupname","");
					if(!"".equals(groupName))
						ticketName=ticketName+" ("+groupName+")";
					json.put("tktId", ticketId);
					json.put("tktNm", ticketName);
					jsonticketsarray.put(json);
				}
			}
		}
		catch(Exception e){
			System.out.println("Exception in getGeneralTickets eventid"+eid+"in question Management for "+e.getMessage());
		}
		return jsonticketsarray;
	}
	
	public static ArrayList getVolumeTickets(String eid){
		String Query="select ticket_name,ticketid from groupdeal_tickets where eventid=?";		
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		ArrayList voltickets=new ArrayList();
		try{
			statobj=dbmanager.executeSelectQuery(Query,new String[]{eid});
			int count=statobj.getCount();
			if(statobj.getStatus()&&count>0){
				for(int k=0;k<count;k++){
					String ticketId=dbmanager.getValue(k,"ticketid","");
					String ticketName=dbmanager.getValue(k,"ticket_name","");
					voltickets.add(new Entity(ticketId,ticketName));
				}
				
			}
		}
		catch(Exception e){
			System.out.println("Exception in getVolumeTickets eventid"+eid+"in question Management for "+e.getMessage());
		}
		return voltickets;
	}
	
	public static ArrayList getEventTicketsForDiscounts(String eid){
		String ticketSelQuery="select pr.price_id,pr.ticket_name as ticketname,etg.groupname  as groupname from price pr," +
		"event_ticket_groups etg,group_tickets gt where gt.ticket_groupid=etg.ticket_groupid and pr." +
		"price_id=CAST(gt.price_id AS INTEGER) and  etg.eventid=? and " +
		"pr.evt_id=CAST(? AS BIGINT) and upper(isdonation)!='YES' order by etg.position,gt.position";
		return getTicketsData(ticketSelQuery,eid);
	}
	public static ArrayList<Entity> geteventCommunities(String eventid){
		ArrayList<Entity> hubData=new ArrayList<Entity>();
		HashMap inParams = new HashMap();
		inParams.put("eventid", eventid);
		NQDbHelper dbhelper = new NQDbHelper();
		//select c.clubid,c.clubname from event_ticket_communities u,clubinfo c  where
		//u.clubid=c.clubid and u.eventid=?
		List hubsList = dbhelper.getDataList("selectcommunities", inParams);
		if (hubsList != null) {
			for (int i = 0; i < hubsList.size(); i++) {
				Entity entityObj=new Entity();
				HashMap hmap = (HashMap) hubsList.get(i);
				entityObj=new Entity(hmap.get("clubid").toString(),hmap.get("clubname").toString());
				hubData.add(entityObj);
			}
		}
		return hubData;
	}
	public static ArrayList<Entity> getManagerCommunities(String eventid){
		ArrayList<Entity> hubData=new ArrayList<Entity>();
		HashMap inParams = new HashMap();
		inParams.put("eventid", eventid);
		NQDbHelper dbhelper = new NQDbHelper();
		//select c.clubid,c.clubname from event_ticket_communities u,clubinfo c  where
		//u.clubid=c.clubid and u.eventid=?
		List hubsList = dbhelper.getDataList("selectmgrcommunities", inParams);
		if (hubsList != null) {
			for (int i = 0; i < hubsList.size(); i++) {
				Entity entityObj=new Entity();
				HashMap hmap = (HashMap) hubsList.get(i);
				entityObj=new Entity(hmap.get("clubid").toString(),hmap.get("clubname").toString());
				hubData.add(entityObj);
			}
		}
		return hubData;
	}
	public static List<Entity> getCountries(){
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		List<Entity> countrylist=new ArrayList<Entity>();
		String Query="select countryname from country_states where statecode is null  order by disposition";
		try{
			statobj=dbmanager.executeSelectQuery(Query,new String []{});
			int count1=statobj.getCount();
			if(statobj.getStatus()&&count1>0){
				for(int k=0;k<count1;k++){
					String countryname=dbmanager.getValue(k,"countryname","");
					countrylist.add(new Entity(countryname,countryname));
				}
			}
		}//End of try block
		catch(Exception e){
			System.out.println("There is an Exception "+e.getMessage());
		}
		return countrylist;
	}
	public static String getPowerType(String eid){
		String type="";
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		type=DbUtil.getVal("select value from config where config_id=CAST(? as integer) and name='event.rsvp.enabled'",new String[]{configid});
		if("yes".equals(type)) type="RSVP";
		else type="Ticketing";
		return type;
	}
	public static String getcurrency(String eid){
		String currency=DbUtil.getVal("select currency_code from event_currency where eventid=?",new String[]{eid});
		if(currency==null)
			currency="USD";
		return currency;
	}
	public static String getConfigVal(String eid, String keyname, String defaultval){
		if("event.ticketpage.refundpolicy.statement".equals(keyname)){
			String lang = I18n.getActualLangFromSession();
			String managerId=DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
			System.out.println("lang : "+lang+" managerId: "+managerId+" eid: "+eid+" keyname: "+keyname);
			String description = DbUtil.getVal("select value from config a,eventinfo b where a.config_id=b.config_id "+
			"and name=? and a.config_id in(select config_id from config where name in ('event.i18n.actual.lang','event.ticketpage.refundpolicy.statement') and value=?)"+
			" and mgr_id=CAST(? as INTEGER) order by created_at desc limit 1", new String[]{keyname,lang,managerId});
			return description;
		}else{
			String configval=DbUtil.getVal("select value from config a,eventinfo b where a.config_id= b.config_id and b.eventid=CAST(? AS BIGINT) and a.name=?",new String[]{eid, keyname});
			if(configval==null)
				configval=defaultval;
			if(!"".equals(configval) && "event.eventphotoURL".equals(keyname)){
				String widthexists=DbUtil.getVal("select 'Y' from config a,eventinfo b where a.config_id= b.config_id and b.eventid=CAST(? AS BIGINT) and a.name=?",new String[]{eid,"event.eventphotoURL.width"});
				if(widthexists==null)
					widthexists="";
				if("".equals(widthexists)){
				try{
				String imgname=configval.substring(configval.lastIndexOf("/"), configval.length());
				String thumbnailpath=EbeeConstantsF.get("smallthumbnail.photo.image.webpath","http://images.eventbee.com/images/photos/smallthumbnail/");
				System.out.println("imgname is:"+imgname);
				configval=thumbnailpath+imgname;
				}catch(Exception e){
					System.out.println("Exception occured while getting imagename in getConfigVal "+eid);
				}
				}
			}
			return configval;
		}
	}
	public static String getConfig(String eid, String keyname,String facebookeventid){
		String configval=DbUtil.getVal("select value from config a,eventinfo b where a.config_id= b.config_id and b.eventid=CAST(? AS BIGINT) and a.name=?",new String[]{eid, keyname});
		   return configval;
	}
	public static void updateFbeid(String eid,String facebookeventid)
	{
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?",new String[]{eid,"event.FBRSVPList.eventid"});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.FBRSVPList.eventid",facebookeventid.trim(),eid});
	}
	public static String getEventTimeZone(String eid){
		String timeZone=getConfigVal(eid, "event.timezone","SystemV/PST8 Pacific Time");
		String userTimeZone=DateTime.getTimeZoneVal(timeZone);
		if("".equals(userTimeZone)) userTimeZone="SystemV/PST8";
		return userTimeZone;
	}
	public static String getEventURL(String eid,String username){
		String eventURL=DbUtil.getVal("select url from event_custom_urls where eventid=?",new String[]{eid});
		if(eventURL==null){
			eventURL=GenUtil.get(username)+"/event?eid="+eid;
		}
		return 	eventURL;
	}
	public static String getFaceBookTicketingPageURL(String eid){
		String FBTicketingURL="http://apps.facebook.com/"+EbeeConstantsF.get("fbapp.eventregapp.name","eventregister")+"/register?eid="+eid;
		return 	FBTicketingURL;
	}
	public static String getUserURL(String eid,String username){
		String userurl=DbUtil.getVal("select shorturl from event_custom_urls where eventid=?",new String[]{eid});
		if(userurl==null)
			userurl="";
		return 	userurl;
	}
	public static String getUserId(String eid){
		String userid=DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});		
		return 	userid;
	}
	public static String getEventPassword(String eid){
		String password=DbUtil.getVal("select password from view_security where eventid=?",new String[]{eid});
		if(password==null) password="";
		return password;
	}
	public static String serverAdderess(){
		String serveraddress=EbeeConstantsF.get("serveraddress","www.eventbee.com");
		if(serveraddress!=null){
			if(!serveraddress.startsWith("http://")){
				serveraddress="http://"+serveraddress;
			}
		}
		return serveraddress;
	}
	public static String setCustomURL(String eid,String mgrId,String shorturl,String newurl){
		String msg="";
		String eventurl="";
		ProfileValidator pv=new ProfileValidator();
		StatusObj sb=null;
		String checkexists="null";
		String memberprefexists=null;
		if(newurl!=null){
			if(!"".equals(newurl)){
				if(pv.checkNameValidity(newurl,true)){
					newurl=newurl.toLowerCase();
					//alreadyexists=DbUtil.getVal("select 'yes' from hardcoded_urls where shorturl=?",new String[]{newurl});
					//memberprefexists=DbUtil.getVal("select 'yes' from member_preference where pref_name=? and lower(pref_value)=?",new String []{"pref:myurl",newurl});
					checkexists=EventBeeValidations.isValidBeeIDCustomUrl(newurl);
					if("notexists".equals(checkexists)){
						DbUtil.executeUpdateQuery("delete from hardcoded_urls where shorturl=?",new String[]{shorturl});
						sb=DbUtil.executeUpdateQuery("insert into hardcoded_urls(shorturl,type,url,outofcontext) values(?,?,?,?)",new String[]{newurl,"member","/customevents/eventhandler.jsp?userid="+mgrId+"&eventid="+eid+"&UNITID=13579","N"});

						if(sb.getStatus()){
							eventurl=GenUtil.get(newurl);
							DbUtil.executeUpdateQuery("delete from event_custom_urls where eventid=?",new String[]{eid});
							DbUtil.executeUpdateQuery("insert into event_custom_urls(url,eventid,shorturl) values(?,?,?)",new String[]{eventurl,eid,newurl});
						}
						return "inserted";
					}
					else
						return "URL Exists";
				}
				else
					return "spacesInUrl";
			}

			else
			{
				DbUtil.executeUpdateQuery("delete from  hardcoded_urls  where shorturl=?",new String[]{shorturl});
				DbUtil.executeUpdateQuery("delete from  event_custom_urls  where eventid=?",new String[]{eid});
			}
		}
		else
			return "Invalid";

		return msg;
	}
	public static HashMap getRSVPInfo(String eventid){
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery("select * from EVENT_CONFIG where eventid=CAST(? AS BIGINT)",new String []{eventid});
		HashMap map=new HashMap();
		if(statobj.getStatus()){
			String [] columnnames=dbmanager.getColumnNames();
			for(int i=0;i<statobj.getCount();i++){
				for(int j=0;j<columnnames.length;j++){
					map.put(columnnames[j],dbmanager.getValue(i,columnnames[j],""));
				}
			}
		}
		return map;
	}
	public static void updateRSVPSettings(EventData eventData,String eid){
		String rsvp_count=null;
		String type=eventData.getRsvptype();
		String notatttype=eventData.getNotatttype();
		if("".equals(notatttype))
			notatttype="N";
		if("1".equals(type))
			rsvp_count=eventData.getRsvplimit();	
		DbUtil.executeUpdateQuery("delete from rsvp_settings where eventid=?", new String[]{eid});
		DbUtil.executeUpdateQuery("insert into rsvp_settings(eventid,limittype,attendeelimit,notsurelimit,totallimit,notattending)" +
				" values(?,CAST(? AS INTEGER),CAST(? AS INTEGER),CAST(? AS INTEGER),CAST(? AS INTEGER),?)", new String[]{eid,eventData.getRsvptype(),eventData.getRsvpattendeelimit(),eventData.getRsvpnotsurelimit(),rsvp_count,notatttype});
	}
	public static int InsertMemberList(HashMap hm){
		int rcount=0;
		String desc="Auto generated mailing list for event attendees";
		StatusObj sobj=null;
		EventData eventData=new EventData();
		String listname="Attendee List-"+(String)hm.get("evtcode");
		eventData.setUnitId((String)hm.get("unitid"));
		eventData.setManagerId((String)hm.get("mgrid"));
		eventData.setListName(listname);
		eventData.setListDesc(desc);
		eventData.setGroupId((String)hm.get("evtid"));
		sobj=EventDB.addManagerList(eventData);
		hm.put("listid",eventData.getListId());
		if (sobj.getStatus()){
			DbUtil.executeUpdateQuery(CONFIG_INSERT,new String[]{(String)hm.get("configid"),(String)sobj.getData()});
			String exists=DbUtil.getVal("select 'yes' from group_mail_list where groupid=?",new String []{(String)hm.get("evtid")});
			if(!"yes".equals(exists)){
				DbUtil.executeUpdateQuery(GROUP_MAIL_LIST_INSERT,new String[]{eventData.getGroupId(),eventData.getListId()});
			}
		}
		return rcount;
	}
	public static StatusObj addManagerList(EventData eventData){
		StatusObj statobj =new StatusObj(false,null,eventData);
		String listid=DbUtil.getVal("select nextval('seq_list') as seqlist", new String[] {});
		eventData.setListId(listid);
		statobj=DbUtil.executeUpdateQuery(INSERT_LIST,new String[]{eventData.getManagerId(),eventData.getListId(),eventData.getListName(),eventData.getListDesc(),eventData.getUnitId(),eventData.getUnSubMessage(),eventData.getAuthRole()});
		statobj.setData(listid);
		return statobj;
	}
	public static boolean isRecurringEvent(String eid){
		String recurring=getConfigVal(eid, "event.recurring","");
		return "Y".equals(recurring);
	}
	public static String getCurrencySymbol(String eid){
		String currencyformat="";
		String query="select currency_symbol from currency_symbols cs,event_currency ec where " +
				"cs.currency_code=ec.currency_code and eventid=?";
		currencyformat=DbUtil.getVal(query, new String[]{eid});
		if(currencyformat==null)
			currencyformat="$";
		return currencyformat;
	}
	public static ArrayList<EventData> getRSVPCounts(String eid,String eventDate){
		
		/*String Query="select attendingevent,count(*) from rsvpattendee" +
				" where eventid=to_number(?,'999999999999999') group by attendingevent";*/
		String sureCount="";
		String notSureCount="";
		String notAttendingCount="";
		if("".equals(eventDate)){
			sureCount=DbUtil.getVal("select sum(yescount) from rsvp_transactions where eventid=?", new String[]{eid});
			notSureCount=DbUtil.getVal("select sum(notsurecount) from rsvp_transactions where eventid=?", new String[]{eid});
			notAttendingCount=DbUtil.getVal("select count(*) from rsvp_transactions where eventid=? and responsetype='N';", new String[]{eid});
			
		}
		else{
			sureCount=DbUtil.getVal("select sum(yescount) from rsvp_transactions a,event_reg_transactions b " +
					"where b.eventid=? and b.tid=a.tid and b.eventdate=?", new String[]{eid,eventDate});
			notSureCount=DbUtil.getVal("select sum(notsurecount) from rsvp_transactions a," +
					"event_reg_transactions b where b.eventid=? and b.tid=a.tid and b.eventdate=?", new String[]{eid,eventDate});
			notAttendingCount=DbUtil.getVal("select count(*) from rsvp_transactions a,event_reg_transactions b where " +
					" a.responsetype='N' and b.eventid=? and b.tid=a.tid and b.eventdate=?", new String[]{eid,eventDate});
		}
		//DBManager dbmanager=new DBManager();
		ArrayList<EventData> list = new	ArrayList<EventData>();
		if(!("0".equals(sureCount) || sureCount==null)){
			EventData edata=new EventData();
			edata.setAttendingevent("Attending");
			edata.setRsvpcount(sureCount);
			list.add(edata);
		}
		if(!("0".equals(notSureCount) || notSureCount==null)){
			EventData edata=new EventData();
			edata.setAttendingevent("Not Sure");
			edata.setRsvpcount(notSureCount);
			list.add(edata);
		}
		if(!("0".equals(notAttendingCount) || notAttendingCount==null)){
			EventData edata=new EventData();
			edata.setAttendingevent("Not Attending");
			edata.setRsvpcount(notAttendingCount);
			list.add(edata);
		}
		//StatusObj stobj=dbmanager.executeSelectQuery(Query,new String[]{eid});
		/*if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				EventData edata=new EventData();
				String response=dbmanager.getValue(i,"attendingevent","");
				if("yes".equals(response))
					response="Attending";
				if("no".equals(response))
				    response="Not Attending";
				if("notsure".equals(response))
				    response="Not Sure";
				String count=dbmanager.getValue(i,"count","");
				if("".equals(count)) count="0";
				edata.setAttendingevent(response);
				edata.setRsvpcount(count);
				list.add(edata);
			}
		}*/
		return list;
	}
	public static void updateEventStatus(String eid,String purpose){
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String currentDate = sdf.format(dt);
		
		String queryforenddate="select end_date|| ' ' ||endtime from eventinfo where eventid=CAST(? AS BIGINT)";
		String event_end_date=DbUtil.getVal(queryforenddate,new String[]{eid});
		
		if(currentDate.compareTo(event_end_date)>0 && purpose.equalsIgnoreCase("ACTIVE")){
			String Query="Update eventinfo set status=? where eventid=CAST(? AS BIGINT)";
			DbUtil.executeUpdateQuery(Query,new String [] {"CLOSED",eid});
		}else{
			String Query="Update eventinfo set status=? where eventid=CAST(? AS BIGINT)";
			
			if("CANCEL".equalsIgnoreCase(purpose))// updated on sep 30 2014 for cancel_by 
				Query="Update eventinfo set status=?,cancel_by='manager' where eventid=CAST(? AS BIGINT)";

			DbUtil.executeUpdateQuery(Query,new String [] {purpose,eid});
		}
	}
	public static String getEventPageViews(String eid){
		
		String isnewevent=DbUtil.getVal("select 'yes' from eventinfo where eventid=CAST(? AS BIGINT) and created_at>'2012-10-23'",new String[]{eid});
		String eventpageviews=null;
		if("yes".equals(isnewevent)){
		String eventpagesummarycount=DbUtil.getVal("select visit_count from hits_summary where id=? and resource='Event Page'",new String[]{eid});
		if(eventpagesummarycount==null)eventpagesummarycount="0";
		String eventpagevisitcount=DbUtil.getVal("select count(*) from hit_track where id=? and resource='Event Page'" ,new String[]{eid});
		
		
		
		if(eventpagevisitcount==null)eventpagevisitcount="0";
		try{
			eventpageviews=Integer.toString((Integer.parseInt(eventpagesummarycount)+Integer.parseInt(eventpagevisitcount)));
		}catch(Exception e)
		 {
			eventpageviews="0";
			System.out.println("Exception occured parsing eventpagevistscount in EventDB");}
		}else eventpageviews="NA";
		if(eventpageviews==null) eventpageviews="0";
		return eventpageviews;
	}
	public static String getRSVPResponsesCount(String eid){
		String Query="select count(*) from rsvp_transactions" +
		" where eventid=?";
		String count=DbUtil.getVal(Query,new String[]{eid});
		if(count==null) count="0";
		return count;
	}
	public static void updateRSVPtoTicketing(String eid){
		String configid = DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[] { eid });
		String CONFIG_POWERTYPE_INSERT="insert into config(config_id,name,value) values(CAST(? AS INTEGER),?,'yes')";
		String configregtypename="event.poweredbyEB";
		DbUtil.executeUpdateQuery(CONFIG_POWERTYPE_INSERT,new String [] {configid,configregtypename});	
		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name in ('event.rsvp.limit')",new String [] {configid});
		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name in ('event.rsvp.enabled')",new String [] {configid});
		DbUtil.executeUpdateQuery("delete from config where config_id=CAST(? AS INTEGER) and name in ('event.regexternal.url')",new String [] {configid});
		DbUtil.executeUpdateQuery("delete from rsvp_transactions where eventid=?", new String[] { eid });
		DbUtil.executeUpdateQuery("delete from profile_base_info where eventid=CAST(? AS BIGINT)", new String[] { eid });
		DbUtil.executeUpdateQuery("delete from buyer_base_info where eventid=CAST(? AS BIGINT)", new String[] { eid });
		DbUtil.executeUpdateQuery("delete from event_reg_transactions where eventid=?", new String[] { eid });
		DbUtil.executeUpdateQuery("delete from transaction_tickets where eventid=?", new String[] { eid });
		DbUtil.executeUpdateQuery("delete from custom_questions_response where ref_id in " +
				"(select ref_id from custom_questions_response_master" +
				" where groupid=CAST(? AS BIGINT))", new String[] { eid });
		DbUtil.executeUpdateQuery("delete from custom_questions_response_master where groupid=CAST(? AS BIGINT)", new String[] { eid });
		
		DbUtil.executeUpdateQuery("delete from email_templates where groupid=? and purpose='RSVP_CONFIRMATION'", new String[] { eid });
		DbUtil.executeUpdateQuery("delete from reg_flow_templates where eventid=CAST(? AS BIGINT) and purpose='rsvp_confirmationpage'", new String[] { eid });
		
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) " +
				"and name in ('event.twitter.show','event.fblike.show','event.googleplusone.show','event.fbcomment.show')",new String[]{eid});
		
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.twitter.show","Y",eid});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fblike.show","Y",eid});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.googleplusone.show","Y",eid});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbcomment.show","Y",eid});

	}
	public static String getRsvpMaxAttendeelimit(String eid){
		String rsvpattendeemaxlimit=DbUtil.getVal("select value from config where name='event.rsvp.attendlimit' and config_id in (select config_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[] {eid});
		return rsvpattendeemaxlimit;
	}
	public static String getRsvpMaxNotsurelimit(String eid){
		String rsvpnotsuremaxlimit=DbUtil.getVal("select value from config where name='event.rsvp.notsurelimit' and config_id in (select config_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[] {eid});
		return rsvpnotsuremaxlimit;
	}
	public static EventData getRSVPSettings(String eid){
		EventData eventData=new EventData();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery("select limittype,attendeelimit,notsurelimit,totallimit,notattending from rsvp_settings where eventid=?", new String[]{eid});
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
					eventData.setRsvptype(dbmanager.getValue(i, "limittype", ""));
					eventData.setRsvpattendeelimit(dbmanager.getValue(i,"attendeelimit", ""));
					eventData.setRsvpnotsurelimit(dbmanager.getValue(i, "notsurelimit", ""));
					eventData.setRsvplimit(dbmanager.getValue(i, "totallimit", ""));
					eventData.setNotatttype(dbmanager.getValue(i,"notattending", ""));
				}
			}
		return eventData;
	}
	
	public static String getNetWorkCommission(String eid){
		String nts_commission=DbUtil.getVal("select nts_commission from eventinfo where  eventid=CAST(? AS BIGINT)",new String[] {eid});
		nts_commission=(nts_commission==null)?"0":nts_commission;
		return nts_commission;
	}
	
	public static String getNTSStatus(String eid){
		String nts_status=DbUtil.getVal("select nts_enable from eventinfo where  eventid=CAST(? AS BIGINT)",new String[] {eid});
		return nts_status;
	}
	
	public static void updateNTSStatus(String eid, String status){
		DbUtil.executeUpdateQuery("update eventinfo set nts_enable=? where eventid=CAST(? AS BIGINT)", new String[] {status,eid});
	}
	
	public static void insertRSVPSocialNetwork(String eid){
		System.out.println("In EventDB insertRSVPSocialNetwork while creating RSVP Event: "+eid);
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) " +
				"and name in ('event.fbshare.show','event.twitter.show','event.fblike.show','event.googleplusone.show','event.fbcomment.show','event.fbsend.show'," +
				"'event.FBRSVPList.show','event.FBRSVPList.gendercount.show','event.FBRSVPList.eventid','event.fbfanpagecomments.show','event.confirmationpage.fbsharepopup.show'," +
				"'event.fbiboughtbutton.gender','event.fbiboughtbutton.show','event.fbfanpagecomments.pageid','event.reg.loginpopup.show')",new String[]{eid});
		
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.twitter.show","N",eid});
		
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fblike.show","N",eid});
		
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.googleplusone.show","N",eid});
	  
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbcomment.show","N",eid});
		
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbsend.show","Y",eid});
		
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.FBRSVPList.show","N",eid});
		
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.FBRSVPList.gendercount.show","N",eid});
		
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.confirmationpage.fbsharepopup.show","Y",eid});
		
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.reg.loginpopup.show","Y",eid});
		
		/*String configid=DbUtil.getVal("select config_id from eventinfo where eventid=to_number(?,'9999999999999999')", new String[]{eid});
		String query="insert into config(config_id, name, value) values ("+configid+",'event.twitter.show','N'),("+configid+",'event.fblike.show','N'),("+configid+",'event.googleplusone.show','N'),("+configid+",'event.fbcomment.show','N')," +
				"("+configid+",'event.fbsend.show','Y'),("+configid+",'event.FBRSVPList.show','N'),("+configid+",'event.FBRSVPList.gendercount.show','N'),("+configid+",'event.confirmationpage.fbsharepopup.show','Y'),("+configid+",'event.reg.loginpopup.show','Y')";			
		DbUtil.executeUpdateQuery(query,null);*/
		
	}
	public static ArrayList<HashMap<String,String>> getPormotedMembers(String eid){
		DBManager db=new DBManager();
		StatusObj sb=null;
		ArrayList<HashMap<String, String>> promotedMembers=new ArrayList<HashMap<String,String>>();
		HashMap<String,String> ntscodecreditsmap=new HashMap<String,String>();
		String ntscreditsqry="select sum(total_nts_commission) as ntscommission,partnerid  from event_reg_transactions e,transaction_tickets t "+
				              " where e.eventid=? and e.tid=t.tid and e.partnerid!='' and e.partnerid is not null group by partnerid";
		sb=db.executeSelectQuery(ntscreditsqry, new String[]{eid});
		if(sb.getStatus() && sb.getCount()>0){
			for(int i=0;i<sb.getCount();i++){
				ntscodecreditsmap.put(db.getValue(i, "partnerid", ""),db.getValue(i, "ntscommission", ""));
			}	
	    }
		/*String query="select eventname,a.eventid,fbuid,name,(select count(*) from event_partner_share_clicks where eventid=a.eventid and nts_code in(select nts_code from ebee_nts_partner where external_userid=fbuid)) as impressions," +
				"(select count(*) from event_reg_transactions where eventid=a.eventid and partnerid in(select nts_code from ebee_nts_partner where external_userid=fbuid)) as sales," +
				"(select count(*) from event_reg_details_temp where eventid=a.eventid and referral_ntscode in(select nts_code from ebee_nts_partner where external_userid=fbuid)) as clicks,"+
				"trim(to_char(a.created_at,'Day')) as day,to_char(a.created_at, 'YYYY/MM/DD') as createdat,to_char(a.created_at,'Month DD') as month from social_promoted_users a,eventinfo b where CAST(a.eventid as BIGINT)=b.eventid and a.eventid=? order by a.created_at desc";*/
		String query="select date_part('hour', a.promoted_at) as hour,to_char(promoted_at, 'Mon dd, YYYY') as dateformat,eventid,visit_count as visits,fname||' '||lname as name,external_userid as fbuid,to_char(promoted_at, 'YYYY/MM/DD'),ticket_sale_count as sales,promoted_at,"+
				"trim(to_char(promoted_at,'Day')) as day,to_char(promoted_at, 'YYYY-MM-DD') as createdat,to_char(promoted_at,'Month DD') as month,a.nts_code from nts_visit_track a ,ebee_nts_partner b where " +
				"a.nts_code=b.nts_code and a.eventid=? and promoted_at is not null order by promoted_at desc";
		sb=db.executeSelectQuery(query, new String[]{eid});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
				HashMap<String, String> promotedMember=new HashMap<String, String>();
				promotedMember.put("eventid", db.getValue(i, "eventid", ""));
				promotedMember.put("fbuid", db.getValue(i, "fbuid", ""));
				promotedMember.put("name", db.getValue(i, "name", ""));
				
				String date=db.getValue(i, "day", "")+", "+db.getValue(i, "month", "");
				promotedMember.put("day",date);
				promotedMember.put("createdat", db.getValue(i, "createdat", ""));
				promotedMember.put("visits", db.getValue(i, "visits", ""));
				promotedMember.put("sales", db.getValue(i, "sales", ""));
				promotedMember.put("hour", db.getValue(i, "hour", ""));
				promotedMember.put("dateformat", db.getValue(i, "dateformat", ""));
				Double visitstotal=Double.parseDouble(db.getValue(i, "visits", "0"))*0.10;
				if(Integer.parseInt(db.getValue(i, "visits", "0"))>0){
				promotedMember.put("visitstotal","$"+CurrencyFormat.getCurrencyFormat("",Double.toString(visitstotal),true));
				if(ntscodecreditsmap.size()>0 && Integer.parseInt(db.getValue(i, "sales", "0"))>0)
				promotedMember.put("salestotal","$"+CurrencyFormat.getCurrencyFormat("",ntscodecreditsmap.get(db.getValue(i, "nts_code", "")),true));
				}
				promotedMembers.add(promotedMember);
			}
		}
		return promotedMembers;
	}
	
	/*public static String getManagerLatestEvent(String mgrid){
		String eventid="";
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		String query="select eventid from eventinfo where mgr_id=CAST(? as integer) and status!='CANCEL' and eventid in(select refid::int from payment_types) order by created_at desc limit 1";
		statobj=dbm.executeSelectQuery(query, new String[]{mgrid});
		if(statobj.getStatus() && statobj.getCount()>0){
		eventid=dbm.getValue(0,"eventid","");
		}
		return eventid;
	}*/
	
	public static HashMap<String,String> getRSVPChartCounts(String eid,String eventDate){
	String sureCount="";
	String notSureCount="";
	String notAttendingCount="";
	if("".equals(eventDate)){
		sureCount=DbUtil.getVal("select sum(yescount) from rsvp_transactions where eventid=?", new String[]{eid});
		notSureCount=DbUtil.getVal("select sum(notsurecount) from rsvp_transactions where eventid=?", new String[]{eid});
		notAttendingCount=DbUtil.getVal("select count(*) from rsvp_transactions where eventid=? and responsetype='N';", new String[]{eid});
		
	}
	else{
		sureCount=DbUtil.getVal("select sum(yescount) from rsvp_transactions a,event_reg_transactions b " +
				"where b.eventid=? and b.tid=a.tid and b.eventdate=?", new String[]{eid,eventDate});
		notSureCount=DbUtil.getVal("select sum(notsurecount) from rsvp_transactions a," +
				"event_reg_transactions b where b.eventid=? and b.tid=a.tid and b.eventdate=?", new String[]{eid,eventDate});
		notAttendingCount=DbUtil.getVal("select count(*) from rsvp_transactions a,event_reg_transactions b where " +
				" a.responsetype='N' and b.eventid=? and b.tid=a.tid and b.eventdate=?", new String[]{eid,eventDate});
	}
	
	HashMap<String,String> hmap=new HashMap<String,String>();
	if(!("0".equals(sureCount) || sureCount==null))
		hmap.put("Attending",sureCount);
	if(!("0".equals(notSureCount) || notSureCount==null))
		hmap.put("NotSure",notSureCount);
	if(!("0".equals(notAttendingCount) || notAttendingCount==null))
		hmap.put("NotAttending",notAttendingCount);
	
	return hmap;
}
	
	public static String getChargeStatus(String mgrId){


		String chargeStatus=DbUtil.getVal("select value from mgr_config where name='require_charge' and userid=CAST(? AS INTEGER)", new String[]{mgrId});
		if(chargeStatus==null || "".equals(chargeStatus)) chargeStatus="N";

		return chargeStatus;
	}
	
	public static boolean isNtsGraphEnable(String eid){
		String query="select value from config where config_id=(select config_id from eventinfo where eventid=cast(? as integer)) and name='event.ntsgraph.enalbe'";
		String val=DbUtil.getVal(query, new String[]{eid});
		if(val==null || "".equals(val) || "no".equals(val) )
		return false;
	return true;
	}
	
	
	public static JSONObject getRecentTransactions(String eid){
		    
        
		/*String recenttrnasactionquery="select tix,fname,lname,to_char(transaction_date,'Mon dd,YYYY HH:MI AM') as tdate,ticketname,b.tid from (select sum(collected_ticketqty) as tix,fname,lname,tid from event_reg_transactions"+
		" where eventid=? group by fname,lname,tid,transaction_date order by transaction_date desc limit 5)a left join transaction_tickets"+
		" b on a.tid=b.tid and b.eventid=?";
		*/
		
		/*String recenttrnasactionquery="select ticket_qty,fname,lname,to_char(transaction_date,'Mon dd,YYYY HH:MI AM') as tdate,ticketname,b.tid from (select"+
				" sum(actual_ticketqty) as ticket_qty,fname,lname,tid,transaction_date from"+
				" event_reg_transactions where eventid=? and paymentstatus='Completed' group by"+
				" fname,lname,tid,transaction_date order by transaction_date desc limit"+
				" 5)a left join transaction_tickets b on a.tid=b.tid and"+
				" b.eventid=?";*/
		
		
		
		String recenttrnasactionquery="select sum(ticketqty) as ticket_qty,fname,lname,to_char(transaction_date,'Mon dd, YYYY HH:MI AM') as tdate,ticketname,b.tid from (select"+
				 " fname,lname,tid,transaction_date from"+
				" event_reg_transactions where eventid=? and paymentstatus='Completed'   order by transaction_date desc limit"+
				 " 5)a join transaction_tickets b on a.tid=b.tid and b.eventid=? group by fname,lname,b.tid,transaction_date,ticketname order by transaction_date desc";
		
		
		
		DBManager dbm=new DBManager();
		StatusObj statObj=null;
		HashMap transactionMap=new HashMap();
		ArrayList<String>tidList=new ArrayList<String>();
		statObj=dbm.executeSelectQuery(recenttrnasactionquery,new String[]{eid,eid});
		
		if(statObj.getStatus() && statObj.getCount()>0){
		for(int i=0;i<statObj.getCount();i++){
			HashMap eachMap=new HashMap();
			HashMap ticketsMap=new HashMap();
			String tid=dbm.getValue(i,"tid","");
			
			if(transactionMap.containsKey(tid)){
				eachMap=(HashMap)transactionMap.get(tid);
				ticketsMap=(HashMap)eachMap.get("tickets");
				ticketsMap.put(dbm.getValue(i,"ticketname",""),dbm.getValue(i,"ticket_qty",""));
				eachMap.put("tickets",ticketsMap);
				//ticketsMap.put(tid,ticketsMap);
				transactionMap.put(tid, eachMap);
			}else{
				tidList.add(tid);
				eachMap.put("fname",dbm.getValue(i,"fname",""));
				eachMap.put("lname",dbm.getValue(i,"lname",""));
				eachMap.put("tdate",dbm.getValue(i,"tdate",""));
				ticketsMap.put(dbm.getValue(i,"ticketname",""), dbm.getValue(i,"ticket_qty",""));
				eachMap.put("tickets",ticketsMap);
				transactionMap.put(tid, eachMap);
			}	
		}	
		}
		
		JSONArray jsonArr=new JSONArray();
		
		for(int i=0;i<tidList.size();i++)
			jsonArr.put(transactionMap.get(tidList.get(i)));
		
		JSONObject recentTransactionObj=new JSONObject();
		try{
			recentTransactionObj.put("recenttransactions", jsonArr);
			}
		catch(Exception e){
			
		}
		System.out.println("the rence transactions are::"+recentTransactionObj);
	return recentTransactionObj;
	}
	
	
	public static ArrayList<String> getRecurringDates(String eid){
	String query= " select to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00')," +
	            " 'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY HH12:MI AM') as evt_start_date " +
	            " from event_dates where eventid=CAST(? AS BIGINT)";

	ArrayList<String> dateList=new ArrayList<String>();
			DBManager db=new DBManager();
			
			StatusObj statobj=db.executeSelectQuery(query,new String[]{eid});
			//System.out.println("status in geteventdates"+statobj.getStatus());
			int count=statobj.getCount();
			if(statobj.getStatus() && count>0){
	          for(int i=0;i<count;i++){
	        	  dateList.add(db.getValue(i,"evt_start_date",""));
	          }
			}
return dateList;
	}
	
	
	public static ArrayList<String> getRSVPRecurringDates(String eid){
	
		/*ArrayList<String> dateList=new ArrayList<String>();
		String Query="select distinct eventdate from event_reg_transactions  "+
				"where eventid=? and eventdate!='' and eventdate is not null";
		DBManager dbmanager=new DBManager();
		StatusObj sb=dbmanager.executeSelectQuery(Query,new String[]{eid});
		if(sb.getStatus() && sb.getCount()>0){
			for(int k=0;k<sb.getCount();k++)
				dateList.add(dbmanager.getValue(k,"eventdate",""));
		}
		return dateList;*/
		
		
		ArrayList<String> dateList=new ArrayList<String>();
		 String query="select date_display as evt_start_date from event_dates where eventid=CAST(? AS BIGINT) order by (zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS aaa') as text) as time ))";
		 DBManager db=new DBManager();  
		   String str=null;
		   StatusObj stob=db.executeSelectQuery(query,new String[]{eid} );
		   if(stob.getStatus()){
		   for(int i=0;i<stob.getCount();i++){
			   dateList.add(db.getValue(i,"evt_start_date",""));
		   }
		   }
		 return dateList;
}
	public static void getTogglingToken(String mgrId,String tokenId,String eid,String actionPattern){
		System.out.println("getTogglingToken mgrId: "+mgrId);
		String qry="insert into main_to_classic_token (mgr_id,token_id,eventid,action_pattern,status,created_at) " +
				"values(CAST(? AS INTEGER),?,?,?,'ACTIVE',to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
		DbUtil.executeUpdateQuery(qry, new String[]{mgrId,tokenId,eid,actionPattern,DateUtil.getCurrDBFormatDate()});
	}
	
	public static String randomAlphaNumeric(int count) {   
		 StringBuilder builder = new StringBuilder();   
		 while (count-- != 0) {   
		  int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());   
		  builder.append(ALPHA_NUMERIC_STRING.charAt(character));   
		 }   
		 return builder.toString();   
		} 
	
	
}
