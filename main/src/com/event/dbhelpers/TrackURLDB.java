package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;
import com.event.beans.ImportTrackUrlData;
import com.event.beans.TrackURLData;
import com.event.helpers.I18n;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.namedquery.NQDbUtil;


public class TrackURLDB {
	private static HashMap<String,String> attColumnMap=new HashMap<String,String>();
	private static HashMap<String,String> rsvpattColumnMap=new HashMap<String,String>();
	static
	{
		attColumnMap.put("Transaction_Date", "transaction_date");
		attColumnMap.put("Transaction_ID", "tid");
		attColumnMap.put("First_Name", "fname");
		attColumnMap.put("Last_Name", "lname");
		attColumnMap.put("Ticket_Name", "lname");
		attColumnMap.put("Ticket_Price", "ticketprice");
		attColumnMap.put("Tickets_Count", "ticketcount");
		attColumnMap.put("Discount", "dicount");
		attColumnMap.put("Tickets_Total", "ticketstotal");
		
		rsvpattColumnMap.put("Transaction_Date", "transaction_date");
		rsvpattColumnMap.put("Transaction_ID", "tid");
		rsvpattColumnMap.put("First_Name", "fname");
		rsvpattColumnMap.put("Last_Name", "lname");
		rsvpattColumnMap.put("Response_Type", "responsetype");
		rsvpattColumnMap.put("Response_Count", "responsecount");
		
		}
	
	public static ArrayList getTrackingURLdetails(String eid) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList trackURLList = new ArrayList();
    	String TrackURLsQUERY = "select eventid,trackingcode,trackingid,count,"
    				+" secretcode from  trackurls  where  eventid=?";
    	statobj=dbmanager.executeSelectQuery(TrackURLsQUERY,new String []{eid});
			if(statobj.getStatus()){
				for(int k=0;k<statobj.getCount();k++){	
					TrackURLData trackURLDataObj=new TrackURLData();	
					trackURLDataObj.setEventid(dbmanager.getValue(k,"eventid",""));
					trackURLDataObj.setTrackingcode(dbmanager.getValue(k,"trackingcode",""));
					trackURLDataObj.setTrackingid(dbmanager.getValue(k,"trackingid",""));
					trackURLDataObj.setCount(dbmanager.getValue(k,"count",""));
					trackURLDataObj.setSecretcode(dbmanager.getValue(k,"secretcode",""));
					ArrayList trackData=getTrackDetails(eid,dbmanager.getValue(k,"trackingcode",""));
					StringBuilder ticketName=new StringBuilder();
					StringBuilder ticketQty=new StringBuilder();
					StringBuilder ticketPrice=new StringBuilder();
					for(int i=0;i<trackData.size();i++){
						TrackURLData tempObj=(TrackURLData)trackData.get(i);
						ticketName.append(tempObj.getTicketname().toString());
						ticketQty.append(tempObj.getTicketQty().toString());
						ticketPrice.append(tempObj.getTicketPrice().toString());						
					}
					trackURLDataObj.setTicketname(ticketName.toString());
					trackURLDataObj.setTicketQty(ticketQty.toString());
					String tickets=ticketName.toString()+" - "+ticketQty.toString();
					if(!"".equals(ticketName.toString()))	trackURLDataObj.setTickets(tickets);
					trackURLList.add(trackURLDataObj);
			}					
		}	    	

		return trackURLList;
	}
	public static ArrayList getIndividualTrackCodeDetails(String eid,String trackcode,String scode){
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList indivualTrackcodeList = new ArrayList();
		String individualTrackURLsQUERY = "select password, message, photo, status, secretcode"
									+" from trackURLs where eventid=? and lower(trackingcode)=?";
		statobj=dbmanager.executeSelectQuery(individualTrackURLsQUERY,new String []{eid,trackcode});
		if(statobj.getStatus()){
		for(int k=0;k<statobj.getCount();k++){	
			TrackURLData individualtrackURLDataObj=new TrackURLData();
			String status=dbmanager.getValue(k,"status","");
			if(status=="") status="Approved";
				
			individualtrackURLDataObj.setPassword(dbmanager.getValue(k,"password",""));
			individualtrackURLDataObj.setMessage(dbmanager.getValue(k,"message",""));
			individualtrackURLDataObj.setPhoto(dbmanager.getValue(k,"photo",""));
			individualtrackURLDataObj.setStatus(status);
			individualtrackURLDataObj.setIndividulasecretcode(dbmanager.getValue(k,"secretcode",""));			
			indivualTrackcodeList.add(individualtrackURLDataObj);
		}					
		}	    
		return indivualTrackcodeList;
	}
	
	public static JSONObject getTrackCodes(String eid,String trackcode,String scode){
		DBManager dbmanager = new DBManager();
		StatusObj statObj = null;
		JSONObject trackList = new JSONObject();
		String QueryTracklist = "select password, message, status, secretcode, trackingcode, photo from trackURLs where eventid=? and lower(trackingcode)=?";
		statObj = dbmanager.executeSelectQuery(QueryTracklist, new String []{eid,trackcode});
		if(statObj.getStatus()){
			try{
				trackList.put("password", dbmanager.getValue(0, "password", ""));
				trackList.put("message", dbmanager.getValue(0, "message", ""));
				trackList.put("status", dbmanager.getValue(0, "status", ""));
				trackList.put("secretcode", dbmanager.getValue(0, "secretcode", ""));
				trackList.put("trackingcode", dbmanager.getValue(0, "trackingcode", ""));
				trackList.put("photo", dbmanager.getValue(0, "photo", ""));
			}catch(Exception e){
				System.out.println("Exception at edit trackUrls");
			}
		}
		return trackList;
	}
	
	public static ArrayList<HashMap<String,String>> getTrackCodeReport(String eid,String trackcode, HashMap ticketsinfo,TrackURLData trackURLData,String powertype){
		boolean isrecurr = EventDB.isRecurringEvent(eid);		
		String QUERY = "select distinct tid,fname,trackpartner,lname,paymenttype as transactiontype," +
				"fname || ' ' || lname as name,to_char(transaction_date,'yyyy/mm/dd')  as trandate,transaction_date,to_char(transaction_date,'Mon DD YYYY')  as newtrandate," +
				" current_amount as grandtotal,servicefee as ebeefee,ccfee as cardfee," +
				"current_discount as discount,paymentstatus as payment_status " +
				"from event_reg_transactions" +
				" where eventid=? and lower(trackpartner)=? " +
				"and bookingsource='online' ";
		
		String recurringdate=trackURLData.getDate();
		String currencySymbol=EventDB.getCurrencySymbol(eid);
        if(currencySymbol==null)
        	currencySymbol="$";
		
		if(isrecurr){
			QUERY+=" and eventdate=?";
		}
		
		String sortDirection = trackURLData.getDir();
		if(sortDirection==null || "".equals(sortDirection)) sortDirection="desc";
		if("yes".equalsIgnoreCase(powertype)){
			String orderby=rsvpattColumnMap.get(trackURLData.getSortedkey().replaceAll(" ", "_"));
			if(orderby==null|| "".equals(orderby)) orderby="trandate";
			//if(trackURLData.getSortedkey()!=null && !"".equals(trackURLData.getSortedkey()) && rsvpattColumnMap.containsKey(trackURLData.getSortedkey().replaceAll(" ", "_")))
			    QUERY+="order by "+orderby+" "+sortDirection;
			
		}else{
			String orderby=attColumnMap.get(trackURLData.getSortedkey().replaceAll(" ", "_"));
			if(orderby==null|| "".equals(orderby)) orderby="trandate";
			//if(trackURLData.getSortedkey()!=null && !"".equals(trackURLData.getSortedkey()) && attColumnMap.containsKey(trackURLData.getSortedkey().replaceAll(" ", "_")))
				QUERY+="order by "+orderby+"  "+sortDirection;
		}
		//QUERY+=" order by trandate desc";
		//System.out.println("QUERY: "+QUERY);
		DBManager dbmanager=new DBManager();
		StatusObj stobj=null;
		if(isrecurr){
			System.out.println("enterd");
			stobj=dbmanager.executeSelectQuery(QUERY,new String[]{eid,trackcode,recurringdate});
			
		}else{
			stobj=dbmanager.executeSelectQuery(QUERY,new String[]{eid,trackcode});
		}
		// StatusObj stobj=dbmanager.executeSelectQuery(QUERY,new String[]{eid,trackcode,recurringdate});
		 ArrayList<HashMap<String,String>> trackReportsList=new ArrayList<HashMap<String,String>>();
		 if(stobj.getStatus()){
				for(int i=0;i<stobj.getCount();i++){
					HashMap<String,String> trackreportsinfo=new HashMap<String,String>();
					if("yes".equals(powertype)){
						trackreportsinfo.put(I18n.getString("trul.report.transaction.id"),	dbmanager.getValue(i,"tid",""));
						trackreportsinfo.put(I18n.getString("trul.report.transaction.date"), dbmanager.getValue(i,"newtrandate",""));	
						
						trackreportsinfo.put(I18n.getString("trul.report.first.name"), dbmanager.getValue(i,"fname",""));
						trackreportsinfo.put(I18n.getString("trul.report.last.name"), dbmanager.getValue(i,"lname",""));
						trackreportsinfo.put(I18n.getString("global.name.lbl"),dbmanager.getValue(i,"name",""));
						trackreportsinfo.put(I18n.getString("trul.report.transactionNewDate"), dbmanager.getValue(i,"trandate",""));	
						//System.out.println("in if loop gettrackcode report");
						HashMap TIDTicketHistory = (HashMap)ticketsinfo.get(dbmanager.getValue(i,"tid",""));
						if(TIDTicketHistory!=null){					
						trackreportsinfo.put(I18n.getString("trul.report.response.count"),GenUtil.getHMvalue(TIDTicketHistory,"Count"));
						trackreportsinfo.put(I18n.getString("trul.report.response.type"),GenUtil.getHMvalue(TIDTicketHistory,"DESC"));
						}
						trackReportsList.add(trackreportsinfo);
					}else{
						trackreportsinfo.put(I18n.getString("trul.report.transaction.id"),	dbmanager.getValue(i,"tid",""));
						trackreportsinfo.put(I18n.getString("trul.report.transaction.date"), dbmanager.getValue(i,"newtrandate",""));	
						trackreportsinfo.put(I18n.getString("trul.report.transactionNewDate"), dbmanager.getValue(i,"trandate",""));	
						trackreportsinfo.put(I18n.getString("trul.report.first.name"), dbmanager.getValue(i,"fname",""));
						trackreportsinfo.put(I18n.getString("trul.report.last.name"), dbmanager.getValue(i,"lname",""));
						trackreportsinfo.put(I18n.getString("trul.report.tickets.total")+" ("+currencySymbol+")", dbmanager.getValue(i,"grandtotal",""));
						trackreportsinfo.put(I18n.getString("trul.report.grandtotal.lbl"), dbmanager.getValue(i,"grandtotal",""));
						trackreportsinfo.put(I18n.getString("trul.report.ebeefee.lbl"), dbmanager.getValue(i,"ebeefee",""));		
						trackreportsinfo.put(I18n.getString("trul.report.cardfee.lbl"), dbmanager.getValue(i,"cardfee",""));			
						trackreportsinfo.put(I18n.getString("trul.report.discount")+" ("+currencySymbol+")", dbmanager.getValue(i,"discount",""));
						trackreportsinfo.put(I18n.getString("global.name.lbl"),dbmanager.getValue(i,"name",""));
						
						//System.out.println("in elseloop gettrackcode report");
						HashMap TIDTicketHistory = (HashMap)ticketsinfo.get(dbmanager.getValue(i,"tid",""));
						if(TIDTicketHistory!=null){					
						trackreportsinfo.put(I18n.getString("trul.report.tickets.count"),GenUtil.getHMvalue(TIDTicketHistory,"Count"));
						trackreportsinfo.put(I18n.getString("trul.report.ticket.name"),GenUtil.getHMvalue(TIDTicketHistory,"DESC"));
						trackreportsinfo.put(I18n.getString("trul.report.ticket.price")+" ("+currencySymbol+")",GenUtil.getHMvalue(TIDTicketHistory,"Price"));
						}
						trackReportsList.add(trackreportsinfo);
					}
					}
					
		 }
		 return trackReportsList;
	}
	
	
	
	
	public static String getTrackCodeJsonData(ArrayList getTrackCodeReport,ArrayList Fields){
		String msg="";
		JSONObject json=new JSONObject();
		try{
			json.put("fields", Fields);
			json.put("TrackCodeReportMap",getTrackCodeReport);
		    msg=json.toString();
		}catch(Exception e){
			System.out.println("Exception occured at prepare json in getRSVPResponseJson"+e.getMessage());
		}
		return msg;
	}
	
	public static final String GET_TICKETS_INFO="select ticketqty,ticketname,ticketid,tid,groupname," +
			"ticketprice from transaction_tickets where eventid=? order by ticketid";

	public static HashMap getTicketInfo(String groupid){
		HashMap hmap=new HashMap();
		DBManager dbmanager=new DBManager();
		HashMap ticketHistory = null;
		String ticketname="";
		String ticketid="";
		StatusObj statobj=dbmanager.executeSelectQuery(GET_TICKETS_INFO,new String [] {groupid});
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
				String tid =dbmanager.getValue(i,"tid",null);
				String ticketqty=dbmanager.getValue(i,"ticketqty","0");
				String ticketprice=dbmanager.getValue(i,"ticketprice","0");
				ticketid=dbmanager.getValue(i,"ticketid","");
				ticketname=dbmanager.getValue(i,"ticketname","");
				String groupName=dbmanager.getValue(i,"groupname","");
				if(!"".equals(groupName) && groupName!=null)
				ticketname=groupName+" - "+ticketname;
				else
				ticketname=ticketname;
				if(hmap.get(tid)!=null){
					ticketHistory = (HashMap)hmap.get(tid);
					ticketname=ticketHistory.get("DESC")+", "+ticketname;
					ticketqty=ticketHistory.get("Count")+", "+ticketqty;
					ticketprice=ticketHistory.get("Price")+", "+ticketprice;
					ticketid=ticketHistory.get("TicketId")+", "+ticketid;
				 }else{
				  	ticketHistory = new HashMap();
				 }
				 ticketHistory.put("Count",ticketqty);
				 ticketHistory.put("Price",ticketprice);
				 ticketHistory.put("DESC",ticketname);
				 ticketHistory.put("TicketId",ticketid);
				 hmap.put(tid,ticketHistory);
			}
		}
	 	return hmap;
	}
	public static void updateTrackUrlData(String status,String password, String photo, String message, String trackCode, String eid){
		DbUtil.executeUpdateQuery("update trackURLs set status=?,password=?,photo=?,message=? where eventid=? and trackingcode =?",new String [] {status,password,photo,message,eid,trackCode});
	}
	
	public static void changeTrackURLStatus(String status,String eid,String trackcode){
		DbUtil.executeUpdateQuery("update trackURLs set status=? where eventid=? and trackingcode =?",new String [] {status,eid,trackcode});
	}
	public static void changeTrackURLPwd(String password,String eid,String trackcode){
		DbUtil.executeUpdateQuery("update trackURLs set password=? where eventid=? and trackingcode =?",new String [] {password,eid,trackcode});
	}
	public static void changeTrackURLPhoto(String photourl,String eid,String trackcode){
		DbUtil.executeUpdateQuery("update trackURLs set photo=? where eventid=? and trackingcode =?",new String [] {photourl,eid,trackcode});
	}
	public static void changeTrackURLMessage(String message,String eid,String trackcode){
		DbUtil.executeUpdateQuery("update trackURLs set message=? where eventid=? and trackingcode =?",new String [] {message,eid,trackcode});
	}
	public static void populateEventURL(String eid,TrackURLData trackURLData){
		String eventURL=DbUtil.getVal("select url from event_custom_urls where eventid=?",new String[]{eid});
		if(eventURL!=null)
			eventURL=eventURL+"/t/";
		else
			eventURL="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/event?eid="+eid+"&track=";
		trackURLData.setEventURL(eventURL);	
	}
	public static void populateManageEventURL(String eid,TrackURLData trackURLData,String trackcode){
		populateEventURL(eid,trackURLData);
		String manageEventURL=DbUtil.getVal("select url from event_custom_urls where eventid=?",new String[]{eid});
		if(manageEventURL!=null){
			manageEventURL=manageEventURL+"/t/";
		manageEventURL=manageEventURL+trackcode+"/manage";
		}else
		  manageEventURL="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/event?eid="+eid+"&track="+trackcode+"&manage=manage";
		trackURLData.setManageEventURL(manageEventURL);
		
	}
	
	public static ArrayList<String> populateFields(String eid){
		String currencySymbol=EventDB.getCurrencySymbol(eid);
        if(currencySymbol==null)
        	currencySymbol="$";
        ArrayList<String> Fields=new ArrayList<String>();
		//Fields.add("Transaction Date");
        Fields.add(I18n.getString("trul.report.transaction.date"));
		//Fields.add("Transaction ID");
		Fields.add(I18n.getString("trul.report.transaction.id"));
		//Fields.add("First Name");
		Fields.add(I18n.getString("trul.report.first.name"));
		//Fields.add("Last Name");
		Fields.add(I18n.getString("trul.report.last.name"));
		//Fields.add("Ticket Name");
		Fields.add(I18n.getString("trul.report.ticket.name"));
		//Fields.add("Ticket Price ("+currencySymbol+")");
		Fields.add(I18n.getString("trul.report.ticket.price")+" ("+currencySymbol+")");
		//Fields.add("Tickets Count");
		Fields.add(I18n.getString("trul.report.tickets.count"));
		//Fields.add("Discount ("+currencySymbol+")");
		Fields.add(I18n.getString("trul.report.discount")+" ("+currencySymbol+")");
		//Fields.add("Tickets Total ("+currencySymbol+")");
		Fields.add(I18n.getString("trul.report.tickets.total")+" ("+currencySymbol+")");
		return Fields;
	}
	public static ArrayList<String> populateRSVPFields(){
		//System.out.println("in populate rsvp fields");
		ArrayList<String> Fields=new ArrayList<String>();
		//Fields.add("Transaction Date");
		Fields.add(I18n.getString("trul.report.transaction.date"));
		//Fields.add("Transaction ID");
		Fields.add(I18n.getString("trul.report.transaction.id"));
		//Fields.add("First Name");
		Fields.add(I18n.getString("trul.report.first.name"));
		//Fields.add("Last Name");
		Fields.add(I18n.getString("trul.report.last.name"));
		//Fields.add("Response Type");
		Fields.add(I18n.getString("trul.report.response.type"));
		//Fields.add("Response Count");
		Fields.add(I18n.getString("trul.report.response.count"));
		return Fields;
	}
	
	/*public static final String alltrackquery="select sum(ticketqty) as qty,trackingcode,ticketname,ticketid,groupname " +
	" from transaction_tickets a,trackURL_transaction b where eventid=? and a.tid=b.transactionid " +
	"group by trackingcode,ticketid,ticketname,groupname";*/
	public static final String alltrackquery="select sum(ticketqty) as qty,trackpartner as trackingcode,ticketname,ticketid,groupname"+
		" from transaction_tickets a,event_reg_transactions b where a.eventid=? and b.paymentstatus='Completed' and a.tid=b.tid"+
		" group by trackpartner,ticketid,ticketname,groupname";
	public static ArrayList getTrackDetails(String eid,String trackingcode){
		ArrayList list=new ArrayList();
		String ticketname="";
		String ticketid="";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String [] {eid,trackingcode});
	
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
				TrackURLData obj=new TrackURLData();
				String qty=dbmanager.getValue(i,"qty","");
			    ticketid=dbmanager.getValue(i,"ticketid","");
				if(Integer.parseInt(ticketid)==102){
					//System.out.println("get trackdetails if");
				       ticketname="Not Sure";
				}else{
					
					 ticketname=dbmanager.getValue(i,"ticketname","");
					// System.out.println("get trackdetails else"+ticketname);
				}
			//	String ticketname=dbmanager.getValue(i,"ticketname","");
				String groupName=dbmanager.getValue(i,"groupname","");
				if(!"".equals(groupName) && groupName!=null)
					ticketname=groupName+" - "+ticketname;
				else
				ticketname=ticketname;		
				
				obj.setTicketname(ticketname);
				obj.setTicketQty(qty);				
				list.add(obj);
			}
		}
		/*
		for(int i=0;i<3;i++){
			TrackURLData obj=new TrackURLData();
			String qty="3";
			String ticketid="24";
			String ticketname="abc";
			String groupName="gh";
			if(!"".equals(groupName) && groupName!=null)
				ticketname=groupName+" - "+ticketname;
			else
			ticketname=ticketname;		
			
			obj.setTicketname(ticketname);
			obj.setTicketQty(qty);				
			list.add(obj);
		}
		*/
	 	return list;
	}
	
	
	public static final String query="select sum(ticketqty) as qty,ticketname,ticketid,groupname " +
			" from transaction_tickets where eventid=? and" +
			" tid in (select tid from event_reg_transactions where trackpartner=? and paymentstatus='Completed') " +
			"group by ticketid,ticketname,groupname";


	public static HashMap getAllTrackDetails(String eid){
		
		String ticketname="";
		String ticketid="";
		DBManager dbmanager=new DBManager();
		
		StatusObj statobj=dbmanager.executeSelectQuery(alltrackquery,new String [] {eid});
		HashMap allTracks=new HashMap();
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
				String trackingcode=dbmanager.getValue(i,"trackingcode","");
				ArrayList list=(ArrayList)allTracks.get(trackingcode);
				if(list==null) list=new ArrayList();
				TrackURLData obj=new TrackURLData();
				String qty=dbmanager.getValue(i,"qty","");
			    ticketid=dbmanager.getValue(i,"ticketid","");
				if(Integer.parseInt(ticketid)==102){
				       ticketname=I18n.getString("rep.filter.not.sure.lbl");
				}else{
					 ticketname=dbmanager.getValue(i,"ticketname","");
					 if("Attending".equals(ticketname)){
						 ticketname=I18n.getString("rep.filter.attending.lbl");
					 }else if("Not Attending".equals(ticketname)){
						 ticketname=I18n.getString("rep.filter.not.attending.lbl");
					 }
				}
			//	String ticketname=dbmanager.getValue(i,"ticketname","");
				String groupName=dbmanager.getValue(i,"groupname","");
				if(!"".equals(groupName) && groupName!=null)
					ticketname=groupName+" - "+ticketname;
				else
				ticketname=ticketname;		
				
				obj.setTicketname(ticketname);
				obj.setTicketQty(qty);				
				list.add(obj);
				allTracks.put(trackingcode, list);
			}
		}
		
	 	return allTracks;
	}
	public static ArrayList<TrackURLData> getTrackURLdetails(String eid) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList<TrackURLData> list = new ArrayList<TrackURLData>();

		String TrackURLsQUERY = "select eventid,trackingcode,trackingid,count,"
			+" secretcode from  trackurls  where  eventid=?";
    	statobj=dbmanager.executeSelectQuery(TrackURLsQUERY,new String []{eid});
			if(statobj.getStatus()){
				for(int k=0;k<statobj.getCount();k++){	
					TrackURLData trackURLDataObj=new TrackURLData();
					trackURLDataObj.setTrackingcode(dbmanager.getValue(k,"trackingcode",""));
					trackURLDataObj.setTrackingid(dbmanager.getValue(k,"trackingid",""));
					trackURLDataObj.setCount(dbmanager.getValue(k,"count",""));
					trackURLDataObj.setSecretcode(dbmanager.getValue(k,"secretcode",""));					
					list.add(trackURLDataObj);
			}					
		}	    	

		return list;
	}
	public static ArrayList<TrackURLData> getTrackURLSummarydetails(String eid) {
		//ResourceBundle resourceBundle=I18n.getResourceBundle();
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		HashMap atracks =getAllTrackDetails(eid);
		ArrayList<TrackURLData> trackURLList = new ArrayList<TrackURLData>();
		String TrackURLsQUERY = "select eventid,trackingcode,trackingid,count,status, secretcode from  trackurls  where  eventid=?";
		statobj=dbmanager.executeSelectQuery(TrackURLsQUERY,new String []{eid});
	if(statobj.getStatus()){
		for(int k=0;k<statobj.getCount();k++){	
			TrackURLData trackURLDataObj=new TrackURLData();	
			trackURLDataObj.setEventid(dbmanager.getValue(k,"eventid",""));
			trackURLDataObj.setTrackingcode(dbmanager.getValue(k,"trackingcode",""));
			trackURLDataObj.setTrackingid(dbmanager.getValue(k,"trackingid",""));
			trackURLDataObj.setCount(dbmanager.getValue(k,"count",""));
			trackURLDataObj.setSecretcode(dbmanager.getValue(k,"secretcode",""));
			String status = dbmanager.getValue(k, "status", "");
			//System.out.println(status+" this is from DB  ");
			String updateStatus="";
			if("Approved".equals(status))
				updateStatus = I18n.getString("trul.approved.lbl");
			else
				updateStatus = I18n.getString("trul.suspended.lbl");
			
			trackURLDataObj.setStatus(updateStatus);
			//ArrayList trackData=getTrackDetails(eid,dbmanager.getValue(k,"trackingcode",""));
			ArrayList trackData=(ArrayList)atracks.get(dbmanager.getValue(k,"trackingcode",""));
			trackURLDataObj.setTicketsData(trackData);
			trackURLList.add(trackURLDataObj);
	}					
}	
		return trackURLList;
	}
	
	public static ArrayList<ImportTrackUrlData> importTrackURL(String eid) {
		//gets a list of Global track partners for a manager who is identified by his eventid
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
			String QUERY="select trackid,trackname from accountlevel_track_partners where trackid not in"+
						"(select trackingid from trackURLs where eventid=?)"+
						"and lower(trackname) not in(select lower(trackingcode) from trackurls where eventid=?)"+
						"and userid=(select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)) and status='Approved'";
    	statobj=dbmanager.executeSelectQuery(QUERY,new String []{eid,eid,eid});
    	ArrayList<ImportTrackUrlData> imptList=new ArrayList<ImportTrackUrlData>();
    	for(int i=0;i<statobj.getCount();i++)
    	{
    		ImportTrackUrlData imtd=new ImportTrackUrlData();
    		imtd.setTrackId(Long.parseLong(dbmanager.getValue(i, "trackid","")));
    		imtd.setTrackName(dbmanager.getValue(i, "trackname", ""));
    		imptList.add(imtd);
    	}
		return imptList;
	}
	public static ImportTrackUrlData getGlobalTrackPartner(String trackId){
		//gets data from accountlevel_track_partners for given trackid and populates in to bean ImportTrackUrlData
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		String Query="select userid,trackid,trackname,password,message,photourl,eventscount,status,scode from accountlevel_track_partners where trackid=to_number(?,'9999999999')";
		statobj=dbmanager.executeSelectQuery(Query,new String []{trackId});
		ImportTrackUrlData imptd=new ImportTrackUrlData();
			ImportTrackUrlData imtd=new ImportTrackUrlData();
    		imptd.setTrackId(Long.parseLong(dbmanager.getValue(0, "trackid","")));
    		imptd.setTrackName(dbmanager.getValue(0, "trackname", ""));
    		imptd.setUserID(dbmanager.getValue(0, "userid", ""));
    		imptd.setPassword(dbmanager.getValue(0, "password",""));
    		imptd.setMessage(dbmanager.getValue(0,"message", ""));
    		imptd.setPhotoUrl(dbmanager.getValue(0,"photourl", ""));
    		imptd.setEventCount(dbmanager.getValue(0, "eventscount", ""));
    		imptd.setStatus(dbmanager.getValue(0,"status", ""));
    		imptd.setScode(dbmanager.getValue(0,"scode", ""));
    	
    	return imptd;
	}
	public static void insertGlobalTrackUnderEvent(ImportTrackUrlData imptd,String eid){
		HashMap inParams = new HashMap();
		inParams.put("eventid",eid);
		inParams.put("trackingcode",imptd.getTrackName());
		inParams.put("password",imptd.getPassword());
		inParams.put("trackingid",imptd.getTrackId());
		inParams.put("message",imptd.getMessage());
		inParams.put("photourl",imptd.getPhotoUrl());
		inParams.put("secretcode",imptd.getScode());
		
		NQDbUtil.executeQuery("TrackingDetails", inParams);
}
	
	public static String getTrackReportExists(String eventid,String trackcode){
		String reportexists="";
		reportexists=DbUtil.getVal("select 'yes' from event_reg_transactions where eventid=? and trackpartner=? and paymentstatus in('Completed','Pending','Authorized','CHARGED')", new String[]{eventid,trackcode});
		if("yes".equalsIgnoreCase(reportexists))
			reportexists="yes";
		else
			reportexists="no";
		return reportexists;
	}
	
	public static void deleteTrackURLFromReport(String eventid,String trackcode){
		DbUtil.executeUpdateQuery("delete from trackurls where eventid=? and trackingcode=?", new String[]{eventid,trackcode});
	}
}