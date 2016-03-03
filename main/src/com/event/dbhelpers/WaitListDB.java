package com.event.dbhelpers;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;

import org.apache.velocity.VelocityContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.event.beans.WaitListData;
import com.eventbee.beans.DateTime;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateConverter;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.general.I18NCacheData;
import com.eventbee.general.EventbeeMail;
import com.eventbee.general.GenUtil;
import com.eventbee.general.SendMailStatus;
import com.eventbee.general.StatusObj;
import com.eventbee.general.WaitListActivationThread;
import com.eventregister.TicketsDB;
import com.eventregister.dbhelper.VelocityHelper;

public class WaitListDB {
	public static final Format fm=new SimpleDateFormat("MMM dd, yyyy");
	public static final String MGR_NAME_QUERY="select first_name||' '||last_name as mgrname from user_profile a, eventinfo b where a.user_id::bigint=b.mgr_id and b.eventid=?";
	
	public static JSONObject getWaitListSummary(String eid,WaitListData waitlistdata){
		int completed=0, waiting=0, inprocess=0, expired=0;
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonarrObj = new JSONArray();
		DBManager db = new DBManager();
		StatusObj stObj = null;
		String query="select to_char(created_at,'yyyy-mm-dd') as datepart,to_char(created_at,'hh24:mi') as timepart,wait_list_id,name,email,phone,status,activation_link," +
				"exp_date,exp_time,eventid,user_exp_date,user_exp_time,eventdate from wait_list_transactions where eventid=CAST(? AS BIGINT) and status<>'DELETED' order by created_at desc";
		
		stObj = db.executeSelectQuery(query, new String[]{eid});
		if(stObj.getStatus() && stObj.getCount()>0){
			try{
				ArrayList<Entity> ticketsList=new ArrayList<Entity>();
				ArrayList<Entity> eventDatesList=new ArrayList<Entity>();
				ArrayList<String> eventDates=new ArrayList<String>();
				Map<String, JSONArray> ticketTypeSummaryMap=new HashMap<String, JSONArray>();
				Map<String, Integer> ticketTypeStatusMap=new HashMap<String, Integer>();
				HashMap<String,HashMap<String,String>> hm=getWaitListTicketInfo(eid,"");
				String userTimeZone = EventDB.getEventTimeZone(eid);
				String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
				StringBuffer widlist= new StringBuffer();
				Map<String,String> ticketsMap=new HashMap<String,String>();
				//Map<String,String> eventDatesMap=new HashMap<String,String>();
				String ticketId="";
				for (int i=0;i<stObj.getCount();i++) {
					JSONArray ticketJsonarrObj = new JSONArray();
					JSONObject waitlistJsonObj = new JSONObject();
					HashMap<String,String> waitListTicketsMap=hm.get(db.getValue(i, "wait_list_id", ""));
					String tickets="";
					if(waitListTicketsMap!=null){
						tickets=waitListTicketsMap.get("Tickets");
						ticketId=waitListTicketsMap.get("TicketId");
						String ticketNm=waitListTicketsMap.get("TicketNm");
						if(!ticketsMap.containsKey(ticketId))
							ticketsMap.put(ticketId,ticketNm);
					}
					String eventdate=db.getValue(i, "eventdate", "");
					if(!"".equals(eventdate) && !eventDates.contains(eventdate))
						eventDates.add(eventdate);
					String wid=db.getValue(i, "wait_list_id", "");
					waitlistJsonObj.put("wid", db.getValue(i, "wait_list_id", ""));
					waitlistJsonObj.put("name", db.getValue(i, "name", ""));
					waitlistJsonObj.put("email", db.getValue(i, "email", ""));
					waitlistJsonObj.put("eventdate", eventdate);
					waitlistJsonObj.put("phone", db.getValue(i, "phone", ""));
					
					waitlistJsonObj.put("activationlink", db.getValue(i, "activation_link", ""));
					String estExpDate=db.getValue(i,"exp_date","");
					String estExpTime=db.getValue(i,"exp_time","");
					String status=db.getValue(i, "status", "");
					
					if("In Process".equals(status)){
						if(!checkTimeDiffrence(estExpDate,estExpTime)){
							status="Expired";
							if(widlist.length()==0)
								widlist.append("'"+wid+"'");
							else widlist.append(",'"+wid+"'");
						}
					}
					waitlistJsonObj.put("status", status);
					
					String req_datepart=db.getValue(i, "datepart", "");
					String req_timepart=db.getValue(i, "timepart", "");
					String rquestedon="";
					if(!"".equals(req_datepart) && !"".equals(req_timepart)){
						try{
							DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
							boolean statusdc=dc.convertDateTime(req_datepart,req_timepart,userTimeZone);
							DateTimeBean dateobj=dc.getDateTimeBeanObj();
							Date dateConverted = new GregorianCalendar(Integer.parseInt(dateobj.getYyPart()),Integer.parseInt(dateobj.getMonthPart())-1,Integer.parseInt(dateobj.getDdPart())).getTime();
							rquestedon=fm.format(dateConverted)+" "+dateobj.getHhPart()+":"+dateobj.getMmPart()+" "+dateobj.getAmpm();
						}catch(Exception e){System.out.println("Exception in getWaitListSummary1: "+e.getMessage());}
					}
					waitlistJsonObj.put("tdate",rquestedon);
					waitlistJsonObj.put("userTdate", req_datepart+" "+req_timepart);
					
					String userExpdate=db.getValue(i, "user_exp_date", "");
					String userExpTime=db.getValue(i, "user_exp_time", "");
					String expiration="";
					if(!"".equals(userExpdate) && !"".equals(userExpTime)){
						try{
							String[] usrexpdate=userExpdate.split("-");
							Date dateConverted = new GregorianCalendar(Integer.parseInt(usrexpdate[0]),Integer.parseInt(usrexpdate[1])-1,Integer.parseInt(usrexpdate[2])).getTime();
							expiration=fm.format(dateConverted)+" "+DateTime.getTimeAM(userExpTime);
						}catch(Exception e){System.out.println("Exception in getWaitListSummary2: "+e.getMessage());}
					}
					waitlistJsonObj.put("expirationtime",expiration);
					waitlistJsonObj.put("userExpirationTime", userExpdate+" "+userExpTime);
					/*if(!"".equals(estExpDate) && !"".equals(estExpTime)){
						DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
						boolean statusdc=dc.convertDateTime(estExpDate,estExpTime,userTimeZone);
						DateTimeBean expdtb=dc.getDateTimeBeanObj();
						String datePart=expdtb.getMonthPart()+"-"+expdtb.getDdPart()+"-"+expdtb.getYyPart();
						String timePart=expdtb.getHhPart()+":"+expdtb.getMmPart()+" "+expdtb.getAmpm();
						waitlistJsonObj.put("expirationtime",datePart+" "+timePart);
					}else waitlistJsonObj.put("expirationtime","");*/
					waitlistJsonObj.put("eventid", db.getValue(i, "eventid", ""));
					waitlistJsonObj.put("tickets", tickets);
					waitlistJsonObj.put("ticketid", ticketId);
					jsonarrObj.put(waitlistJsonObj);
					
					if(ticketTypeSummaryMap.containsKey(ticketId)){
						ticketJsonarrObj=(JSONArray)ticketTypeSummaryMap.get(ticketId);
						ticketJsonarrObj.put(waitlistJsonObj);
					}else{
						ticketJsonarrObj.put(waitlistJsonObj);
					}
					ticketTypeSummaryMap.put(ticketId,ticketJsonarrObj);
					
					/*ticketTypeSummaryMap.put(ticketId+"_Completed","0");
					ticketTypeSummaryMap.put(ticketId+"_In Process","0");
					ticketTypeSummaryMap.put(ticketId+"_Expired","0");
					ticketTypeSummaryMap.put(ticketId+"_Waiting","0");*/
					
					if(ticketTypeStatusMap.containsKey(ticketId+"_"+status)){
						int value=ticketTypeStatusMap.get(ticketId+"_"+status);
						ticketTypeStatusMap.put(ticketId+"_"+status,value+1);
					}else{
						ticketTypeStatusMap.put(ticketId+"_"+status,1);
					}
					
					if("Completed".equals(status)) completed+=1;
					if("In Process".equals(status)) inprocess+=1;
					if("Expired".equals(status)) expired+=1;
					if("Waiting".equals(status)) waiting+=1;
				}
				if(widlist.length()>0) updateWaitListStatus(eid,widlist.toString());
				jsonObj.put("WaitListSummary", jsonarrObj);
				for (Map.Entry<String, JSONArray> entry : ticketTypeSummaryMap.entrySet()){
					jsonObj.put(entry.getKey(), entry.getValue());
				}
				jsonObj.put("Completed", String.valueOf(completed));
				jsonObj.put("InProcess", String.valueOf(inprocess));
				jsonObj.put("Expired", String.valueOf(expired));
				jsonObj.put("Waiting", String.valueOf(waiting));
				jsonObj.put("All", String.valueOf(completed+inprocess+expired+waiting));
				waitlistdata.setCompleted( String.valueOf(completed));
				waitlistdata.setExpired(String.valueOf(expired));
				waitlistdata.setInProcess(String.valueOf(inprocess));
				waitlistdata.setWaiting(String.valueOf(waiting));
				waitlistdata.setAllStatus(String.valueOf(completed+inprocess+expired+waiting));
				JSONArray ticketTypes=new JSONArray();
				for (Map.Entry<String, String> entry : ticketsMap.entrySet()){
				    ticketsList.add(new Entity(entry.getKey(),entry.getValue()));
				    JSONObject json = new JSONObject();
				    json.put("ticketId",entry.getKey());
				    json.put("ticketNm",entry.getValue());
				    ticketTypes.put(json);
				    /*int tkt_comp=0,tkt_inp=0,tkt_exp=0,tkt_wat=0;
				    
				    if(ticketTypeStatusMap.containsKey(entry.getKey()+"_Completed"))
				    	tkt_comp=ticketTypeStatusMap.get(entry.getKey()+"_Completed");
				    
				    jsonObj.put(entry.getKey()+"_Completed", tkt_comp);
				    
				    if(ticketTypeStatusMap.containsKey(entry.getKey()+"_In Process"))
				    	tkt_inp=ticketTypeStatusMap.get(entry.getKey()+"_In Process");

				    jsonObj.put(entry.getKey()+"_InProcess", tkt_inp);
				    
				    if(ticketTypeStatusMap.containsKey(entry.getKey()+"_Expired"))
				    	tkt_exp=ticketTypeStatusMap.get(entry.getKey()+"_Expired");

				    jsonObj.put(entry.getKey()+"_Expired", tkt_exp);
				    
				    if(ticketTypeStatusMap.containsKey(entry.getKey()+"_Waiting"))
				    	tkt_wat=ticketTypeStatusMap.get(entry.getKey()+"_Waiting");

				    jsonObj.put(entry.getKey()+"_Waiting", tkt_wat);
				    
				    jsonObj.put(entry.getKey()+"_All", tkt_comp+tkt_inp+tkt_exp+tkt_wat);*/
				}
				
				for (String eventDate : eventDates) 
					eventDatesList.add(new Entity(eventDate,eventDate));
				
				waitlistdata.setTicketsList(ticketsList);
				waitlistdata.setEventDates(eventDatesList);
				jsonObj.put("ticketTypes", ticketTypes);
			}catch(Exception e){
				System.out.println("Exception in WaitListDB getWaitListSummary: "+e.getMessage());
			}
		}
		return jsonObj;
	}
	
	public static void updateWaitListStatus(String eid,String widlist){
		//System.out.println("widlist: "+widlist);
		String query="update wait_list_transactions set status='Expired',updated_at=now() where eventid=CAST(? AS BIGINT) and wait_list_id in("+widlist+")";
		DbUtil.executeUpdateQuery(query, new String[]{eid});
	}
	
	public static ArrayList<HashMap<String,String>> getWaitListMultiTicketInfo(String eid, String wid){
		HashMap<String,HashMap<String,String>> hm = new HashMap<String,HashMap<String,String>>();
		ArrayList<HashMap<String,String>> waitListInfo=new ArrayList<HashMap<String,String>>();
		HashMap<String,String> ticketHistory = null;
		DBManager db = new DBManager();
		StatusObj stObj = null;
	
		String query="select ticketid,ticket_qty,wait_list_id,ticket_name from wait_list_tickets where eventid=CAST(? AS BIGINT) and wait_list_id=?";
		stObj = db.executeSelectQuery(query, new String[]{eid,wid});
		
		if(stObj.getStatus()){
			for(int i=0;i<stObj.getCount();i++){
				ticketHistory = new HashMap<String,String>();
				ticketHistory.put("ticketId",db.getValue(i, "ticketid", ""));
				ticketHistory.put("ticketQty",db.getValue(i, "ticket_qty", "0"));
				ticketHistory.put("ticketName",db.getValue(i, "ticket_name", ""));
				waitListInfo.add(ticketHistory);
			}
		}
		//System.out.println("getWaitListMultiTicketInfo hm: "+hm);
		return waitListInfo;
	}
	
	public static HashMap<String,HashMap<String,String>> getWaitListTicketInfo(String eid, String wid){
		HashMap<String,HashMap<String,String>> hm = new HashMap<String,HashMap<String,String>>();
		HashMap<String,String> ticketHistory = null;
		DBManager db = new DBManager();
		StatusObj stObj = null;
		String query="";
		if(!"".equals(wid)){
			query="select ticketid,ticket_qty,wait_list_id,ticket_name from wait_list_tickets where eventid=CAST(? AS BIGINT) and wait_list_id=?";
			stObj = db.executeSelectQuery(query, new String[]{eid,wid});
		}else{
			query="select ticketid,ticket_qty,wait_list_id,ticket_name from wait_list_tickets where eventid=CAST(? AS BIGINT)";
			stObj = db.executeSelectQuery(query, new String[]{eid});
		}
		if(stObj.getStatus()){
			for(int i=0;i<stObj.getCount();i++){
				String ticketid=db.getValue(i, "ticketid", "");
				String ticketqty=db.getValue(i, "ticket_qty", "0");
				String ticketname=db.getValue(i, "ticket_name", "");
				wid=db.getValue(i, "wait_list_id", "");
				String tickets="";
				ticketHistory = new HashMap<String,String>();
				tickets=ticketname+" ("+ticketqty+")";
				ticketHistory.put("Tickets",tickets);
				ticketHistory.put("TicketId",ticketid);
				ticketHistory.put("TicketNm",ticketname);
				ticketHistory.put("TicketQty",ticketqty);
				hm.put(wid,ticketHistory);
			}
		}
		//System.out.println("getWaitListTicketInfo hm: "+hm);
		return hm;
	}
	
	public static WaitListData getWLTransactionData(String wid,String eid){
		System.out.println("wait list id: "+wid+" eid: "+eid);
		WaitListData waitListData = new WaitListData();
		DBManager db = new DBManager();
		StatusObj stObj = null;
		try{
			
			HashMap<String,HashMap<String,String>> hm=getWaitListTicketInfo(eid,wid);
			ArrayList<HashMap<String,String>> ticketList=getWaitListMultiTicketInfo(eid,wid);
			
			String query="select to_char(transaction_date,'yyyy/mm/dd') as tdate,notes,name,email,phone,status,activation_link,exp_date,exp_time," +
					" user_exp_date,user_exp_time,eventdate from wait_list_transactions where eventid=CAST(? AS BIGINT) and wait_list_id=?";
			stObj = db.executeSelectQuery(query, new String[]{eid,wid});
			if(stObj.getStatus() && stObj.getCount()>0){
				HashMap<String,String> ticketsMap=hm.get(wid);
				String tickets="";String ticketId="";String ticketQty="";
				if(ticketsMap!=null){
					tickets=ticketsMap.get("Tickets");
					ticketId=ticketsMap.get("TicketId");
					ticketQty=ticketsMap.get("TicketQty");
				}
				waitListData.setTdate(db.getValue(0, "tdate", ""));
				waitListData.setWid(wid);
				waitListData.setNotes(db.getValue(0, "notes", ""));
				waitListData.setName(db.getValue(0, "name", ""));
				waitListData.setEmail(db.getValue(0, "email", ""));
				waitListData.setPhone(db.getValue(0, "phone", ""));
				waitListData.setStatus(db.getValue(0, "status", ""));
				waitListData.setActivationLink(db.getValue(0, "activation_link", ""));
				waitListData.setTickets(tickets);
				waitListData.setEventid(eid);
				waitListData.setTicketId(ticketId);
				waitListData.setTicketList(ticketList);
				waitListData.setTicketQty(ticketQty);
				waitListData.setEventDate(db.getValue(0, "eventdate", ""));
				
				String userExpdate=db.getValue(0, "user_exp_date", "");
				String userExpTime=db.getValue(0, "user_exp_time", "");
				
				/*if(!"".equals(db.getValue(0,"exp_date","")) && !"".equals(db.getValue(0,"exp_time",""))){
					String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
					String userTimeZone = EventDB.getEventTimeZone(eid);
					DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
					boolean statusdc=dc.convertDateTime(db.getValue(0,"exp_date",""),db.getValue(0,"exp_time",""),userTimeZone);
					DateTimeBean expdtb=dc.getDateTimeBeanObj();
					waitListData.setExpDateTimeBeanObj(expdtb);
				}*/
				
				if(!"".equals(userExpdate) && !"".equals(userExpTime)){
					try{
						String[] usrexpdate=userExpdate.split("-");
						String[] userexptime=DateTime.getTimeAM(userExpTime).split(":");
						String[] userampm=userexptime[1].split(" ");
						DateTimeBean dtbObj=new DateTimeBean();
						dtbObj.setYyPart(usrexpdate[0]);
						dtbObj.setMonthPart(usrexpdate[1]);
						dtbObj.setDdPart(usrexpdate[2]);
						dtbObj.setHhPart(userexptime[0]);
						dtbObj.setMmPart(userampm[0]);
						dtbObj.setAmpm(userampm[1]);
						waitListData.setExpDateTimeBeanObj(dtbObj);
						}catch(Exception e){System.out.println("Exception in getWLTransactionData1: "+e.getMessage());}
				}else{
					getWaitListDefaultExpDate(waitListData,eid);
				}
				
			}
		}catch(Exception e){
			System.out.println("Exception in WaitListDB getWLTransactionData: "+e.getMessage());
		}
		return waitListData;
	}
	
	public static void deleteWLTransaction(String eid,String wltid){
		//String query="delete from wait_list_transactions where eventid=CAST(? AS BIGINT) and wait_list_id=?";
		String query="update wait_list_transactions set status='DELETED',updated_at=now() where eventid=CAST(? AS BIGINT) and wait_list_id=? ";
		DbUtil.executeUpdateQuery(query, new String[]{eid,wltid});
	}
	
	public static String validateTimeCompare(String eid,WaitListData waitListData){
		try{
		String userTimeZone = EventDB.getEventTimeZone(eid);
		String toTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		DateConverter dc=new DateConverter(userTimeZone,toTimeZone);
		DateTimeBean dtb=waitListData.getExpDateTimeBeanObj();
		boolean statusdc=dc.convertDateTime(dtb);
		String expDate=dc.getDatePart();
		String expTime=dc.getTimePart();
		if(checkTimeDiffrence(expDate,expTime)){
			waitListData.setExpDate(expDate);
			waitListData.setExpTime(expTime);
		}else
			return "errors";
		}catch(Exception e){
			System.out.println("Exception in validateTimeCompare ERROR: "+e.getMessage());
			e.printStackTrace();
			return "errors";
		}
		return "success";
	}
	
	public static HashMap getEventData(String eid){
		HashMap eventinfoMap=new HashMap();
		HashMap evtmap=new HashMap();
		EventInfoDB edb=new EventInfoDB();
		StatusObj sobj=edb.getEventInfo(eid,evtmap);
		TicketsDB ticketInfo=new TicketsDB();
		eventinfoMap.put("base_info", evtmap);
		eventinfoMap.put("config", ticketInfo.getConfigValuesFromDb(eid));
		HashMap<String, String> hm= new HashMap<String, String>();
		hm.put("purpose", "WAITLIST_ACTIVATION_LINK");
		hm.put("isCustomFeature", "Y");
		String dbi18nLang=I18NCacheData.getI18NLanguage(eid);
		eventinfoMap.put("emailtemplate", RegEmailSettingsDB.getEmailTemplate(hm, dbi18nLang, eid));
		return eventinfoMap;
	}
	
	public static String sendMultipleActivation(String eid, String widAry,WaitListData waitListData){
		System.out.println("sendMultipleActivation widAry: "+widAry);
		DBManager db=new DBManager();
		StatusObj stObj = null;
		if("".equals(widAry.trim())) return "success";
		widAry=widAry.replace(",", "','");
		String MAIL_TO="select name,email,wait_list_id  from wait_list_transactions where wait_list_id in('"+widAry+"') and eventid=CAST(? AS BIGINT)";
		stObj=db.executeSelectQuery(MAIL_TO, new String[]{eid});
		if(stObj.getStatus() && stObj.getCount()>0){
			HashMap eventInfo=getEventData(eid);
			WaitListData eachData=null;



			for(int i=0;i<stObj.getCount();i++){
				String wid=db.getValue(i, "wait_list_id", "");
				eachData=(WaitListData)waitListData.clone();
				eachData.setName(db.getValue(i, "name", ""));
				eachData.setEmail(db.getValue(i, "email", ""));


				(new Thread(new WaitListActivationThread(eid, wid, eachData,eventInfo))).start();
			}
		}

	return "success";
}
	
	public static String sendActivationLink(String eid,String wid,WaitListData waitListData,HashMap eventInfo){
		System.out.println("sendActivationLink wid: "+wid);
		String  serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		String activationLink=serveraddress+"/event?eid="+eid+"&wid="+wid;
		
		//System.out.println("sendActivationLink wid: "+wid+" ExpDate & ExpTime: "+waitListData.getExpDate()+" "+waitListData.getExpTime());
		
		//int monthpart=Integer.parseInt(waitListData.getExpDateTimeBeanObj().getMonthPart())-1;
		
		String userDatePart=waitListData.getExpDateTimeBeanObj().getYyPart()+"-"+waitListData.getExpDateTimeBeanObj().getMonthPart()+"-"+waitListData.getExpDateTimeBeanObj().getDdPart();
		
		String hhpart=waitListData.getExpDateTimeBeanObj().getHhPart();
		String minpart=waitListData.getExpDateTimeBeanObj().getMmPart();
		String ampmpart=waitListData.getExpDateTimeBeanObj().getAmpm();
		
		String timepart=DateConverter.getHH24(hhpart,ampmpart);
		String userTimePart=timepart+":"+minpart;
		
		//System.out.println("sendActivationLink wid: "+wid+" userDate&Time: "+userDatePart+" "+userTimePart);
		
		String updateqry="update wait_list_transactions set updated_at=now(),status=?,activation_link=?,exp_date=?,exp_time=?,user_exp_date=?,user_exp_time=? where eventid=? and wait_list_id=?";
		StatusObj stobj = DbUtil.executeUpdateQuery(updateqry,new String[]{"In Process",activationLink,waitListData.getExpDate(),waitListData.getExpTime(),userDatePart,userTimePart,eid,wid});
		waitListData.setEventid(eid);
		waitListData.setWid(wid);
		waitListData.setActivationLink(activationLink);
		SendEmail(waitListData,eventInfo);
		return "success";
	}
	
	static void SendEmail(WaitListData waitListData,HashMap eventInfo) {
		try {
			HashMap evtmap=(HashMap)eventInfo.get("base_info");
			HashMap configmap=(HashMap)eventInfo.get("config");
			HashMap<String, String> emailTemplate =(HashMap<String, String>)eventInfo.get("emailtemplate");
			
			int yearpart=Integer.parseInt(waitListData.getExpDateTimeBeanObj().getYyPart());
			int monthpart=Integer.parseInt(waitListData.getExpDateTimeBeanObj().getMonthPart())-1;
			int daypart=Integer.parseInt(waitListData.getExpDateTimeBeanObj().getDdPart());
			
			String hhpart=waitListData.getExpDateTimeBeanObj().getHhPart();
			String minpart=waitListData.getExpDateTimeBeanObj().getMmPart();
			String ampmpart=waitListData.getExpDateTimeBeanObj().getAmpm();
			
			String expiration="";
		    try{
		    	//Format fm=new SimpleDateFormat("MMMM dd, yyyy");
		    	Date dateConverted = new GregorianCalendar(yearpart,monthpart,daypart).getTime();
		    	expiration=fm.format(dateConverted)+" "+hhpart+":"+minpart+" "+ampmpart;
		    }catch (Exception ex){System.out.println("Exception in SendEmail wid: "+waitListData.getWid()+ " Error: "+ex);}
		     
		    //System.out.println("SendEmail wid: "+waitListData.getWid()+" expiration: "+expiration+" eventDate: "+waitListData.getEventDate()); 
			VelocityContext mp = new VelocityContext();
			mp.put("buyerName", waitListData.getName());
			mp.put("eventName",(String)evtmap.get("EVENTNAME"));
			mp.put("activationURL", waitListData.getActivationLink());
			String mgrname=GenUtil.getHMvalue(configmap,"event.hostname",null);
			if(mgrname!=null){
				mp.put("mgrFirstName",mgrname);
				mp.put("mgrLastName","");
			}else{
				mp.put("mgrFirstName",(String)evtmap.get("FIRSTNAME"));
				mp.put("mgrLastName",(String)evtmap.get("LASTNAME"));
			}
			mp.put("expiration", expiration);
			if(!"".equals(waitListData.getEventDate()))
				mp.put("eventdate", waitListData.getEventDate());
			String mailcontent=emailTemplate.get("htmlFormat");
			String message=VelocityHelper.getVelocityOutPut(mp,mailcontent,"ebeetemplate");						
			String subject=VelocityHelper.getVelocityOutPut(mp,emailTemplate.get("subjectFormat"),"ebeetemplate");
			EmailObj obj = EventbeeMail.getEmailObj();
			
			String fromemail=GenUtil.getHMvalue(configmap,"event.sendmailfrom","");
			if("".equals(fromemail)) fromemail=EbeeConstantsF.get("MESSAGES_FROM_EMAIL","messages@eventbee.com");
						
			String bcclist=EbeeConstantsF.get("ADMIN_EMAIL","giri@eventbee.com");
			String bcclistfromDB=GenUtil.getHMvalue(configmap,"waitlist.activationemail.cc.list","");
			if(bcclistfromDB!=null&&!"".equals(bcclistfromDB))
				bcclist+=","+bcclistfromDB;
			if("Y".equals(GenUtil.getHMvalue(configmap,"waitlist.activation.sendmailtomgr","Y")))
				bcclist+=","+(String)evtmap.get("EMAIL");
			
			obj.setTo(waitListData.getEmail());
			obj.setFrom(fromemail);
			obj.setReplyTo((String)evtmap.get("EMAIL"));
			obj.setBcc(bcclist);
			obj.setSubject(MimeUtility.encodeText(subject,"UTF-8","Q"));
			obj.setHtmlMessage(message);
			obj.setSendMailStatus(new SendMailStatus("0","WAITLIST_ACTIVATION_LINK",waitListData.getEventid(),"1"));
			EventbeeMail.sendHtmlMailPlain(obj);
			EventbeeMail.insertStrayEmail(obj,"html","S");
		} catch (Exception e) {
			System.out.println("Exception in WaitListDB sendEmail: "+e.getMessage());
		}
	}
	public static void getWaitListDefaultExpDate(WaitListData waitListData,String eid){
		
		String selectTicketEndDate="select to_char(date,'yyyy') as teyr," +
				"to_char(date,'mm') as temm," +
				"to_char(date,'dd') as tedd," +
				"to_char(date,'hh') as tehh," +
				"to_char(date,'mi') as temi," +
				"to_char(date,'am') as teampm " +
				"from (select (start_date + COALESCE(starttime, '00:00')::text::time without time zone-interval '1 hour' ) as date " +
				"from eventinfo where eventid=CAST(? AS BIGINT)) waitlist_exp_date";
		DateTimeBean dtbObj=new DateTimeBean();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(selectTicketEndDate,new String []{eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			dtbObj.setYyPart(dbmanager.getValue(0,"teyr",""));
			dtbObj.setMonthPart(dbmanager.getValue(0,"temm",""));
			dtbObj.setDdPart(dbmanager.getValue(0,"tedd",""));
			dtbObj.setHhPart(dbmanager.getValue(0,"tehh",""));
			dtbObj.setMmPart(dbmanager.getValue(0,"temi",""));
			dtbObj.setAmpm(dbmanager.getValue(0,"teampm","").toUpperCase());
		}
		waitListData.setExpDateTimeBeanObj(dtbObj);
	}
	
	static boolean checkTimeDiffrence(String expdate,String exptime){
		boolean flag=false;
		String time=expdate+" "+exptime;
		String timequery="select '"+time+"'>now()";
		//System.out.println("timequery: "+timequery);
		String val=DbUtil.getVal(timequery,null);
		if("t".equals(val))
			flag=true;
	return flag;
	}
	
	public static HashMap<String,String> getTicketSoldStatusMap(String eid,String ticketid,String wid){
		System.out.println("getTicketSoldStatusMap ticketid: "+ticketid+" eid: "+eid+" wid: "+wid);
		/*String soldstatquery="select 'yes' from price a,wait_list_tickets b where b.eventid=CAST(? AS BIGINT) and b.ticketid=CAST(? AS BIGINT) and a.price_id=b.ticketid and (b.ticket_qty+a.sold_qty>a.max_ticket)";
		String status=DbUtil.getVal(soldstatquery, new String[]{eid,ticketid});*/
		
		HashMap<String,String> tktsoldstatusmap=null;
		//String soldstatquery="select b.ticket_qty,a.sold_qty,a.max_ticket from price a,wait_list_tickets b where b.eventid=CAST(? AS BIGINT) and b.ticketid=CAST(? AS BIGINT) and a.price_id=b.ticketid";
		String inprocessTktQtyQry="select sum(a.ticket_qty) as ttq from wait_list_tickets a, wait_list_transactions b where " +
				"b.wait_list_id=a.wait_list_id " +
				"and b.status='In Process' " +
				"and b.eventid=CAST(? AS BIGINT) " +
				"and a.ticketid=CAST(? AS BIGINT) " +
				"and b.wait_list_id<> ? ";
		
		String inprocessTktQty=DbUtil.getVal(inprocessTktQtyQry, new String[]{eid,ticketid,wid});
		if(inprocessTktQty==null) inprocessTktQty="0";
		
		String priceqry="select sold_qty,max_ticket,price_id from price where evt_id=CAST(? AS BIGINT) and price_id=CAST(? AS INTEGER)";
		
		
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(priceqry,new String []{eid,ticketid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			tktsoldstatusmap=new HashMap<String,String>();
			tktsoldstatusmap.put("ticketid", ticketid);
			tktsoldstatusmap.put("sold_qty", dbmanager.getValue(0,"sold_qty","0"));
			tktsoldstatusmap.put("max_ticket", dbmanager.getValue(0,"max_ticket","0"));
			tktsoldstatusmap.put("inprocess_tkt_qty", inprocessTktQty);
		}
		
		return tktsoldstatusmap;
	}
	
	
	public static HashMap<String,HashMap<String,String>> getMultipleTicketSoldStatusMap(String eid,String widAry){
		HashMap<String,String> tktsoldstatusmap=null;
		HashMap<String,String> waitListIdMap=null;
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		HashMap<String,HashMap<String,String>> tktReqQtyMap=new HashMap<String,HashMap<String,String>>();
		
		
		
		String totaltikquery="select sum(a.ticket_qty) as totqty,a.ticketid from wait_list_tickets a, wait_list_transactions b where " +
				"b.wait_list_id=a.wait_list_id " +
				"and b.status='In Process' " +
				"and b.eventid=CAST(? AS BIGINT) " +
				"and b.wait_list_id not in('"+widAry+"') " +// if the page is not loaded and sending again activation link case on same waitlist id
				"group by a.ticketid";
		
		String soldstatquery="select b.ticket_qty,a.sold_qty,a.max_ticket,price_id,a.ticket_name,wait_list_id from price a,wait_list_tickets b " +
				"where b.eventid=CAST(? AS BIGINT) and a.price_id=b.ticketid and wait_list_id in('"+widAry+"')";
		
		System.out.println("totaltikquery: "+totaltikquery+" eid: "+eid+" widAry: "+widAry);
		
		statobj=dbmanager.executeSelectQuery(totaltikquery,new String []{eid});
		int count=statobj.getCount();
		HashMap<String, Integer> alreadyWLQtyTicketMap=new HashMap<String, Integer>();
		if(statobj.getStatus()&&count>0){
			for(int i=0;i<count;i++){
				
				String ticketid=dbmanager.getValue(i,"ticketid","");
				String totaltktqty=dbmanager.getValue(i,"totqty","0");
				alreadyWLQtyTicketMap.put(ticketid, Integer.parseInt(totaltktqty));
			}
		}
		
		
		statobj=dbmanager.executeSelectQuery(soldstatquery,new String []{eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int i=0;i<count1;i++){
				
				String waitListId=dbmanager.getValue(i,"wait_list_id","");
				String ticketid=dbmanager.getValue(i,"price_id","0");
				String thisWidreqQty=dbmanager.getValue(i,"ticket_qty","0");
				String soldQty=dbmanager.getValue(i,"sold_qty","0");
				String maxTicket=dbmanager.getValue(i,"max_ticket","0");
				int alreadyWLtotaltktqty=0;
				System.out.println(" alreadyWLQtyTicketMap: "+alreadyWLQtyTicketMap+" eid: "+eid);
				if(alreadyWLQtyTicketMap.containsKey(ticketid))
					alreadyWLtotaltktqty=alreadyWLQtyTicketMap.get(ticketid);
				System.out.println(" alreadyWLtotaltktqty: "+alreadyWLtotaltktqty+" ticketid: "+ticketid+" eid: "+eid);
				
				if((Integer.parseInt(thisWidreqQty)+Integer.parseInt(soldQty)+alreadyWLtotaltktqty)>Integer.parseInt(maxTicket)){	
					
					if(tktReqQtyMap.containsKey(ticketid)){
						tktsoldstatusmap=tktReqQtyMap.get(ticketid);
						thisWidreqQty=String.valueOf(Integer.parseInt(thisWidreqQty)+Integer.parseInt(tktsoldstatusmap.get("ticketQty")));
					}else{
						tktsoldstatusmap=new HashMap<String,String>();
						tktsoldstatusmap.put("ticketName", dbmanager.getValue(i,"ticket_name",""));
						if(Integer.parseInt(thisWidreqQty)+Integer.parseInt(soldQty)-Integer.parseInt(maxTicket)>0)
							thisWidreqQty=String.valueOf(Integer.parseInt(thisWidreqQty)+Integer.parseInt(soldQty)-Integer.parseInt(maxTicket));
					}
					
					
					tktsoldstatusmap.put("ticketQty", thisWidreqQty);
					
					tktReqQtyMap.put(ticketid, tktsoldstatusmap);
					
					if(tktReqQtyMap.get("waitListIds")!=null){
						waitListIdMap=tktReqQtyMap.get("waitListIds");
						String ticketids=waitListIdMap.get(waitListId);
						List<String> items=null;
						if(ticketids!=null){
							items = Arrays.asList(ticketids.split(","));
							if(items!=null && !items.contains(dbmanager.getValue(i,"price_id","0"))){
								ticketids=","+dbmanager.getValue(i,"price_id","0");
								waitListIdMap.put(waitListId, ticketids);
							}
						}else{
							waitListIdMap.put(waitListId, dbmanager.getValue(i,"price_id","0"));
						}
					}else{
						waitListIdMap=new HashMap<String,String>();
						waitListIdMap.put(waitListId, dbmanager.getValue(i,"price_id","0"));
					}					
					tktReqQtyMap.put("waitListIds", waitListIdMap);
				}
				if(alreadyWLQtyTicketMap.containsKey(ticketid)) 
					alreadyWLQtyTicketMap.put(ticketid, alreadyWLQtyTicketMap.get(ticketid)+Integer.parseInt(thisWidreqQty));
				else alreadyWLQtyTicketMap.put(ticketid, Integer.parseInt(thisWidreqQty));
				
			}
		}
		
		return tktReqQtyMap;
	}
	
	public static void updateTicketQty(String eid, String ticketid, String ticketQty){
		System.out.println("WaitlistDB updateTicketQty eid: "+eid+" ticketid:"+ticketid+" ticketQty:" +ticketQty);
		DbUtil.executeUpdateQuery("update price set max_ticket=max_ticket+CAST(? AS INTEGER) where evt_id=CAST(? AS BIGINT) and price_id=CAST(? AS INTEGER)", new String[]{ticketQty,eid,ticketid});
		
		//incrementing eventcapacity 
		String update_eventcapacity_qry="update config set value=value::integer+CAST(? AS INTEGER) where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.reg.eventlevelcheck.count'";
		StatusObj stObj=DbUtil.executeUpdateQuery(update_eventcapacity_qry,new String[]{ticketQty,eid});
		if(stObj.getStatus()) System.out.println("Event capacity is updated for eid: "+eid+" count: "+stObj.getCount());
	}
	
	public static String updateMultipleTicketQty(String eid, String multipleTicketReqMap, WaitListData waitlistdata){
		try {
			String status=validateTimeCompare(eid, waitlistdata);
			if("errors".equals(status)) return "errors";
			JSONObject json=new JSONObject(multipleTicketReqMap);
			//System.out.println("json: "+json);
			//HashMap eventInfo=getEventData(eid);

			for(Iterator iterator = json.keys(); iterator.hasNext();) {
			    String key = (String) iterator.next();

			    if("waitListIds".equals(key)){
			    	JSONObject jsonwaitlistIds=json.getJSONObject(key);
			    	StringBuffer waitListIds= new StringBuffer();
			    	for(Iterator waitlistiterator = jsonwaitlistIds.keys(); waitlistiterator.hasNext();){
			    		if(waitListIds.length()==0) waitListIds.append("'"+waitlistiterator.next()+"'");
			    		else waitListIds.append(",'"+waitlistiterator.next()+"'");
			    	}
			    	
			    	DBManager db=new DBManager();
					StatusObj stObj = null;
					String MAIL_TO="select name,email,wait_list_id  from wait_list_transactions where wait_list_id in("+waitListIds.toString()+") and eventid=CAST(? AS BIGINT)";
					stObj=db.executeSelectQuery(MAIL_TO, new String[]{eid});
					if(stObj.getStatus() && stObj.getCount()>0){
						HashMap eventInfo=getEventData(eid);
						WaitListData eachData=null;
						for(int i=0;i<stObj.getCount();i++){
							String wid=db.getValue(i, "wait_list_id", "");
							eachData=(WaitListData)waitlistdata.clone();
							eachData.setName(db.getValue(i, "name", ""));
							eachData.setEmail(db.getValue(i, "email", ""));
				    		(new Thread(new WaitListActivationThread(eid, wid, eachData, eventInfo))).start();
						}
					}
			    	
			    }else{
					 updateTicketQty(eid, key, json.getJSONObject(key).getString("ticketQty"));
			    }
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public static void resetWaitListEmailSettings(String eid, String purpose){
		if("confirmed".equals(purpose)){
			String DELETE_TEMPLATE="delete from custom_email_templates where  purpose='WAITLIST_CONFIRMATION' and groupid=?";
			DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{eid});
		}else{
			String DELETE_TEMPLATE="delete from custom_email_templates where  purpose='WAITLIST_ACTIVATION_LINK' and groupid=?";
			DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{eid});
		}	
	}
	
	public static void updateWaitListEmailSettings(String eid,String purpose, WaitListData waitListData){		
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		if("confirmed".equals(purpose)){
			DbUtil.executeUpdateQuery("delete from config where config_id=?::int and name='waitlist.registration.sendmailtomgr'",new String [] {configid});
			String confirmedtomanager=waitListData.getTomanager();
			if("".equals(confirmedtomanager.trim())) confirmedtomanager="N"; 
			else confirmedtomanager="Y";
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(?::int,'waitlist.registration.sendmailtomgr',?)",new String [] {configid, confirmedtomanager});
		}else{
			DbUtil.executeUpdateQuery("delete from config where config_id=?::int and name='waitlist.activation.sendmailtomgr'",new String [] {configid});
			String activationtomanagervalue=waitListData.getTomanager();
			if("".equals(activationtomanagervalue))activationtomanagervalue="N"; else activationtomanagervalue="Y";
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(?::int,'waitlist.activation.sendmailtomgr',?)",new String [] {configid, activationtomanagervalue});
		}
		if("confirmed".equals(purpose)){
			DbUtil.executeUpdateQuery("delete from config where config_id=?::int and name='waitlist.registration.cc.list'",new String [] {configid});
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(?::int,'waitlist.registration.cc.list',?)",new String [] {configid, waitListData.getBcc()});
		}else{
			DbUtil.executeUpdateQuery("delete from config where config_id=?::int and name='waitlist.activationemail.cc.list'",new String [] {configid});
			DbUtil.executeUpdateQuery("insert into config(config_id,name,value) values(?::int,'waitlist.activationemail.cc.list',?)",new String [] {configid, waitListData.getBcc()});
		}if("confirmed".equals(purpose)){			
			String DELETE_TEMPLATE="delete from custom_email_templates where purpose='WAITLIST_CONFIRMATION' and groupid=?";
			DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{eid});
			String INSERT_TEMPLATE="insert into custom_email_templates(groupid,subjectformat,htmlformat,textformat, replytoemail,fromemail,purpose) values (?,?,?,?,?,?,?)";
			DbUtil.executeUpdateQuery(INSERT_TEMPLATE,new String[]{eid,waitListData.getSubject(),waitListData.getEmailcontent(),null,null,null,"WAITLIST_CONFIRMATION"});
		}else{
			String DELETE_TEMPLATE="delete from custom_email_templates where  purpose='WAITLIST_ACTIVATION_LINK' and groupid=?";
			DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{eid});
			String INSERT_TEMPLATE="insert into custom_email_templates(groupid,subjectformat,htmlformat,textformat, replytoemail,fromemail,purpose) values (?,?,?,?,?,?,?)";
			DbUtil.executeUpdateQuery(INSERT_TEMPLATE,new String[]{eid,waitListData.getSubject(),waitListData.getEmailcontent(),null,null,null,"WAITLIST_ACTIVATION_LINK"});
		}	
	}
	
	public static JSONObject getTemplate(String eid, String purpose){
		
		JSONObject waitlistData = new JSONObject();
		try{
			TicketsDB ticketInfo=new TicketsDB();
			HashMap configdata=ticketInfo.getConfigValuesFromDb(eid);
			//EmailTemplate emailTemplate=null;"WAITLIST_CONFIRMATION"
			String dbi18nLang=I18NCacheData.getI18NLanguage(eid);
			HashMap<String, String> hm= new HashMap<String,String>();
			HashMap<String, String> emailTemplate=new HashMap<String, String>();
			hm.put("isCustomFeature", "Y");
			if(purpose.equals("confirmed")){
				hm.put("purpose", "WAITLIST_CONFIRMATION");
				emailTemplate=RegEmailSettingsDB.getEmailTemplate(hm, dbi18nLang, eid);
			}
			else{
				hm.put("purpose", "WAITLIST_ACTIVATION_LINK");
				emailTemplate=RegEmailSettingsDB.getEmailTemplate(hm, dbi18nLang, eid);
			}
			
			String bcc="";
			if(purpose.equals("confirmed"))	bcc=GenUtil.getHMvalue(configdata,"waitlist.registration.cc.list","");
			else bcc=GenUtil.getHMvalue(configdata,"waitlist.activationemail.cc.list","");
			
			String tomanager="";
			if(purpose.equals("confirmed")){
				tomanager=GenUtil.getHMvalue(configdata,"waitlist.registration.sendmailtomgr","Y");
				if("Y".equals(tomanager)) tomanager="yes";  else tomanager="no";
			}else{
				tomanager=GenUtil.getHMvalue(configdata,"waitlist.activation.sendmailtomgr","Y");
				if("Y".equals(tomanager)) tomanager="yes"; else tomanager="no";
			}
			String mgrname=GenUtil.getHMvalue(configdata,"event.hostname",null);
			if(mgrname==null) mgrname=getMgrName(eid);
			waitlistData.put("mgrName", mgrname);
			waitlistData.put("bcc", bcc);
			waitlistData.put("subject", emailTemplate.get("subjectFormat"));
			waitlistData.put("content", emailTemplate.get("htmlFormat"));
			waitlistData.put("tomanager", tomanager);
			waitlistData.put("purpose", purpose);
		
		}catch(Exception e){
			System.out.println("Exception in Waitlist getTemplate ERROR:: "+e.getMessage());
		}
		return waitlistData;
	}
	
	/*public static EmailTemplate getEmailTemplate(String eid,String purpose){
		EmailTemplate emailtemplate=null;
		String isformatexists=DbUtil.getVal("select 'yes' from email_templates where purpose=? and groupid=?", new String []{purpose,eid});
		if("yes".equals(isformatexists)){
			emailtemplate=new EmailTemplate("13579",purpose,eid);
		}else{
			emailtemplate=new EmailTemplate("500",purpose);
		}
		return emailtemplate;
	}*/
	
	public static String getMgrName(String eid){
		String mgrname=DbUtil.getVal(MGR_NAME_QUERY, new String[]{eid});
		if(mgrname==null) mgrname="";
		return mgrname;
	}

} 



