package com.event.dbhelpers;

import java.util.*;

import org.json.JSONObject;

import com.event.beans.ReportsData;
import com.eventbee.beans.Entity;
import com.eventbee.filters.DsnProperty;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.GenUtil;
import com.eventbee.general.JsoupHtml2Text;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;
import com.event.beans.ScanData;
import com.event.helpers.I18n;


public class ReportsDB {
	ReportsData reportsData=new ReportsData();
	public static final HashMap<String,String> attColumnMap=new HashMap<String, String>();
	public static final HashMap<String,String> tranColumnMap=new HashMap<String, String>();
	public static final HashMap<String,String> checkInColumnMap=new HashMap<String, String>();
	static String GET_SCAN_DATA="select   tid ,  attendeekey,  checkinstatus,  to_char(checkedintime,'mm/dd/yyyy HH:MI AM') as checkedintime,  checkedby ,  scanid,  " +
	" syncedyn,  syncedby,  syncedat,  syncuser from event_scan_status where eventid=?";
static String GET_SCAN_DATA_RECURRING="select   tid ,  attendeekey,  checkinstatus,  to_char(checkedintime,'mm/dd/yyyy HH:MI AM') as checkedintime,  checkedby ,  scanid,  " +
" syncedyn,  syncedby,  syncedat,  syncuser from event_scan_status where eventid=? " +
" and tid in (select tid from event_reg_transactions where eventdate=? and eventid=?)";

static String CUSTOM_ATTRIBUTES_QUERY="select attrib_id,attribname,subquestionof,attrib_setid from custom_attribs where " +
"attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) " +
"and purpose='EVENT') order by position";

static String BUYER_ATTRIBUTES_QUERY="select attrib_id,attribname,subquestionof,attrib_setid from custom_attribs where " +
"attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) " +
"and purpose='EVENT') and attrib_id in (select attribid from buyer_custom_questions where eventid=CAST(? AS BIGINT))" +
"order by position";

	
	public static ArrayList getCustomAttributes(String eid){

		DBManager dbmanager=new DBManager();
		ArrayList attribs_list=new ArrayList();
		StatusObj statobj=null;
		HashMap hm=new HashMap();
		statobj=dbmanager.executeSelectQuery(CUSTOM_ATTRIBUTES_QUERY,new String []{eid});
				int count1=statobj.getCount();
					if(statobj.getStatus()&&count1>0){
					for(int k=0;k<count1;k++){
						String attribval=dbmanager.getValue(k,"attribname","");
						//converting html to text as html tags are not closing while truncating...  on 15th April 2015
						attribval=JsoupHtml2Text.getPlainText(attribval);
						attribval=TruncateData(attribval,33);
						attribs_list.add(new Entity(dbmanager.getValue(k,"attrib_id",""),attribval));
					}
			}
		return attribs_list;
	 }
	
	public static HashMap getAllCustomAttributesMap(String eid){

		DBManager dbmanager=new DBManager();
		ArrayList attribs_list=new ArrayList();
		ArrayList<Entity> subquestionlist=null;
		StatusObj statobj=null;
		HashMap hm=new HashMap();
		HashMap optionLabelMap=new HashMap();
		HashMap subquestionofMap=new HashMap();
		HashMap attribOptionMap=new HashMap();
		ArrayList attribOptionList=null;
		StringBuffer sb = new StringBuffer();
		String attribsetid="";
		statobj=dbmanager.executeSelectQuery(CUSTOM_ATTRIBUTES_QUERY,new String []{eid});
				int count1=statobj.getCount();
					if(statobj.getStatus()&&count1>0){
					for(int k=0;k<count1;k++){
						String attribval=dbmanager.getValue(k,"attribname","");
						attribval=JsoupHtml2Text.getPlainText(attribval);
						attribval=TruncateData(attribval,33);
						
						if(dbmanager.getValue(k,"subquestionof","no").equals("no")){
							attribs_list.add(new Entity(dbmanager.getValue(k,"attrib_id",""),attribval));
						}else{
							
							if(subquestionofMap.containsKey(dbmanager.getValue(k,"subquestionof","")))
								subquestionlist=(ArrayList<Entity>) subquestionofMap.get(dbmanager.getValue(k,"subquestionof",""));
							else
								subquestionlist=new ArrayList<Entity>();
							
							subquestionlist.add(new Entity(dbmanager.getValue(k,"attrib_id",""),attribval));
							subquestionofMap.put(dbmanager.getValue(k,"subquestionof",""), subquestionlist);
							
							if(sb.length()>0)
								sb.append(",'"+dbmanager.getValue(k,"subquestionof","no")+"'");
							else sb.append("'"+dbmanager.getValue(k,"subquestionof","no")+"'");
						}
					}
					attribsetid=dbmanager.getValue(0,"attrib_setid","");
					if(sb.length()>0){
						String query="select attrib_id,option,option_val from custom_attrib_options where attrib_setid=CAST(? AS INTEGER) and attrib_id||'_'||option in("+sb.toString()+") order by attrib_id,position";
						statobj=dbmanager.executeSelectQuery(query,new String []{attribsetid});
						if(statobj.getStatus()&&statobj.getCount()>0){
							for(int j=0;j<statobj.getCount();j++){
								optionLabelMap.put(dbmanager.getValue(j,"attrib_id","")+"_"+dbmanager.getValue(j,"option",""),dbmanager.getValue(j,"option_val",""));
								if(attribOptionMap.containsKey(dbmanager.getValue(j,"attrib_id","")))
									attribOptionList=(ArrayList)attribOptionMap.get(dbmanager.getValue(j,"attrib_id",""));
								else attribOptionList=new ArrayList();
								attribOptionList.add(dbmanager.getValue(j,"attrib_id","")+"_"+dbmanager.getValue(j,"option",""));
								attribOptionMap.put(dbmanager.getValue(j,"attrib_id",""),attribOptionList);
							}
						}
					}
			}
					
					HashMap allmap =new HashMap();
					allmap.put("attriblist", attribs_list);
					allmap.put("optionlabelmap", optionLabelMap);
					allmap.put("subquestionofmap", subquestionofMap);
					allmap.put("attriboptionmap", attribOptionMap);
		return allmap;
	 }
	
	public static String TruncateData(String basedata, int tsize){
	    if(basedata==null) return "";
	    if(basedata.length()<=tsize) return basedata;
	    return basedata.substring(0,tsize-1 )+"....";
	}
	
	public static String getAttribSetID(String eid,String purpose){
		String setId=DbUtil.getVal("select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose=?",new String[] {eid,purpose});
		return setId;
    }
	
	public static HashMap getTicketGroupdetails(String eid){
        DBManager dbmanager=new DBManager();
        HashMap groupmap=new HashMap();
       
        StatusObj sb=dbmanager.executeSelectQuery("select a.price_id as ticketid,b.groupname as groupname from group_tickets a,event_ticket_groups b where b.eventid=? and a.ticket_groupid=b.ticket_groupid and groupname!=?",new String[]{eid,""});
        if(sb.getStatus()){
                for(int i=0;i<sb.getCount();i++){
                    groupmap.put(dbmanager.getValue(i,"ticketid",""),dbmanager.getValue(i,"groupname","")); 
                    
                }
        }
       
        return groupmap;
    }
	
	public static ArrayList getTicketDetails(String eid){
		DBManager dbmanager=new DBManager();
		ArrayList ticketsList=new ArrayList();
		HashMap groupnames=getTicketGroupdetails(eid);
		//StatusObj sb=dbmanager.executeSelectQuery("select distinct ticketname,ticketid from transaction_tickets  where eventid=?",new String[]{eid});
		
		/*String query ="SELECT distinct on(a.tktid) a.tname, a.tktid FROM (SELECT distinct ticketname as tname, "+
					  "ticketid as tktid from transaction_tickets where ticketid not in (select price_id from price "+
					  "where evt_id=to_number(?,'999999999999')) and eventid=? UNION SELECT ticket_name as tname, "+
					  "price_id as tktid from price where evt_id=to_number(?,'999999999999'))a order by a.tktid ";*/
		String query ="SELECT distinct ticketname as tname, ticketid as tktid from transaction_tickets where eventid=?";
		//StatusObj sb=dbmanager.executeSelectQuery(query,new String[]{eid,eid,eid});
		StatusObj sb=dbmanager.executeSelectQuery(query,new String[]{eid});
		if(sb.getStatus()){
			for(int k=0;k<sb.getCount();k++){
			String ticketid=dbmanager.getValue(k,"tktid","");
			String ticketname=dbmanager.getValue(k,"tname","");
			if(groupnames.containsKey(ticketid)){
				ticketname=ticketname+"("+groupnames.get(ticketid)+")";
			}
			//System.out.println("ticketname"+ticketname);
			ticketsList.add(new Entity(ticketid,ticketname));
			}		
		}
		return ticketsList;
	}
	
	public static ArrayList getAttendeeTickets(ArrayList ticketsList){
		ArrayList attticketsList=new ArrayList();
		attticketsList.add(new Entity("1",I18n.getString("rep.all.tickets.lbl")));
		attticketsList.add(new Entity("2", I18n.getString("rep.all.attendee.tickets.lbl")));
		attticketsList.addAll(ticketsList);
		return attticketsList;
		
	}
	
	public static ArrayList getBookingSourceDetails(String eid){
		DBManager dbmanager=new DBManager();
		ArrayList sourceList=new ArrayList();
	
		sourceList.add(new Entity("direct",I18n.getString("rep.main.url.lbl")));
		//sourceList.add(new Entity("nts","NTS"));
		sourceList.add(new Entity("alltrackcodes",I18n.getString("rep.all.tracking.urls.lbl")));
		sourceList.add(new Entity("nts",I18n.getString("rep.all.nts.partners.lbl")));
		StatusObj sb=dbmanager.executeSelectQuery("select trackingcode from trackurls where eventid=?",new String[]{eid});
		if(sb.getStatus()){
			for(int k=0;k<sb.getCount();k++){	
				sourceList.add(new Entity(dbmanager.getValue(k,"trackingcode",""),I18n.getString("rep.track.url.lbl")+" "+dbmanager.getValue(k,"trackingcode","")));
			}		
		}
		sourceList.add(new Entity("fbapp",I18n.getString("rep.fb.application.lbl")));
		return sourceList;
	}
	
	public static ArrayList getPaymetTypes(){
		ArrayList paymentTypeList=new ArrayList();
		paymentTypeList.add(new Entity("Eventbee","Eventbee CC"));
		paymentTypeList.add(new Entity("Authorize.Net","Authorize.Net"));
		paymentTypeList.add(new Entity("Braintree","Braintree"));
		paymentTypeList.add(new Entity("Stripe","Stripe"));
		paymentTypeList.add(new Entity("PayPal","PayPal"));
		paymentTypeList.add(new Entity("ebeecredits","Eventbee Credits"));
		paymentTypeList.add(new Entity("No Payment",I18n.getString("rep.no.payment.lbl")));
		paymentTypeList.add(new Entity("Other",I18n.getString("rep.other.payment.lbl")));
		return paymentTypeList;
	}
	
	
	public static ArrayList<Entity> getRecurringDates(String eid,String powertype){
		DBManager dbmanager=new DBManager();
		ArrayList<Entity> eventsList=new ArrayList<Entity>();
		ArrayList<String> datesList=new ArrayList<String>();
		String Query="select distinct eventdate from event_reg_transactions  "+
				"where eventid=? and eventdate!='' and eventdate is not null";
//		String Query="select distinct b.eventdate,to_char(b.eventdate,'Dy, Mon dd, yyyy') as displaydate " +
//		"from event_dates a,event_reg_transactions b " +
//		"where a.eventid=to_number(b.eventid,'999999999999999') " +
//		"and b.eventid=?";
		//StatusObj sb=dbmanager.executeSelectQuery("select eventdate,to_char(eventdate,'dd Mon yyyy') as displaydate from event_dates where eventid=to_number(?,'99999999999999')",new String[]{eid});
		StatusObj sb=dbmanager.executeSelectQuery(Query,new String[]{eid});
		if(sb.getStatus() && sb.getCount()>0){
			for(int k=0;k<sb.getCount();k++)
				datesList.add(dbmanager.getValue(k,"eventdate",""));
		
			if(!"RSVP".equals(powertype))
				DateUtil.sortCustomDate(datesList,"EEE, MMM dd, yyyy hh:mm a",false);
		
			for (String eventdate : datesList)
				eventsList.add(new Entity(eventdate,eventdate));
		}
		return eventsList;
	}
	
	
	public static ArrayList<Entity> getRecurringEvents(String eid,String trackcode,String powertype){
		
		DBManager dbmanager=new DBManager();
		ArrayList<Entity> eventsList=new ArrayList<Entity>();
		ArrayList<String> datesList=new ArrayList<String>();
		String Query="select distinct eventdate from event_reg_transactions  "+
				"where eventid=? and eventdate!='' and trackpartner=? ";
		StatusObj sb=dbmanager.executeSelectQuery(Query,new String[]{eid,trackcode});
		/*if(sb.getStatus()){
			for(int k=0;k<sb.getCount();k++){	
				eventsList.add(new Entity(dbmanager.getValue(k,"eventdate",""),dbmanager.getValue(k,"eventdate","")));
			}		
		}*/
		if(sb.getStatus() && sb.getCount()>0){
			for(int k=0;k<sb.getCount();k++)
				datesList.add(dbmanager.getValue(k,"eventdate",""));
		
			if(!"RSVP".equals(powertype))
				DateUtil.sortCustomDate(datesList,"EEE, MMM dd, yyyy hh:mm a",false);
		
			for (String eventdate : datesList)
				eventsList.add(new Entity(eventdate,eventdate));
		}
		return eventsList;
		
	}
	
	public static  String commonreccuringpart=" and eventdate=?";
	static{
	attColumnMap.put("TID", "a.transactionid");
	attColumnMap.put("TD", "transaction_date");
	attColumnMap.put("FN", "dfname");
	attColumnMap.put("LN", "dlname");
	attColumnMap.put("Em", "demail");
	attColumnMap.put("Ph", "phone");
	attColumnMap.put("TN", "ticketname");
	attColumnMap.put("TP", "ticketprice");
	attColumnMap.put("ON", "ordernumber");
	attColumnMap.put("AK", "attendeekey");
	attColumnMap.put("BN", "buyername");
	attColumnMap.put("BE", "buyeremail");
	attColumnMap.put("BP", "buyerphone");
	attColumnMap.put("SC", "seatcode");
	attColumnMap.put("ED", "c.eventdate");
}
public static ArrayList<HashMap<String,String>> getAttendeeListInfo(String eid, HashMap attribresponse,
		ArrayList attQuestionFields, String selindex,ReportsData reportsData, HashMap ticketsinfo,
		String typeIndex,String sortField,String sortDirection, boolean export){
	System.out.println("Attendee_Reports_eid: "+eid+" UUID: getAttendeeListInfo(ReportsDB.java) started, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
	boolean isrecurr = EventDB.isRecurringEvent(eid);
	String GET_ATTENDEELIST_INFO="select a.fname as dfname," +
			"a.lname as dlname,lower(a.email) as demail,a.ticketid,ticketname,groupname,ticketprice,a.phone,a.seatcode,to_char(transaction_date,'yyyy/mm/dd') as trdate,transaction_date," +
			"c.paymenttype,d.profilekey as buyerkey, a.profilekey as attendeekey,a.profileid as attendeeid,a.transactionid,ordernumber," +
			"bookingsource,current_amount,c.eventdate,lower(c.email) as buyeremail,c.phone as buyerphone," +
			"c.fname || ' ' || c.lname  as buyername,to_char(a.checkintime,'mm/dd/yyyy HH:MI AM') as checkintime,a.scanid as scanid,a.tickettype,b.ticketid, " +
			"c.paymentstatus from profile_base_info a, transaction_tickets b," +
			" event_reg_transactions c, buyer_base_info d " +
			"where a.ticketid||'_'||a.eventid=b.ticketid||'_'||b.eventid  " +
			"and b.tid=c.tid " +
			"and c.eventid=? "+
			"and (UPPER(paymentstatus) in ('COMPLETED','CHARGED','PENDING') ) " +
			"and a.transactionid=b.tid " +
			"and c.tid=d.transactionid";
	
	if(reportsData.getIsSearch()){
		if("transactionId".equals(reportsData.getSearchOn()))
			GET_ATTENDEELIST_INFO+=" and c.tid='"+reportsData.getSearchContent().trim()+"'";
		if("orderNumber".equals(reportsData.getSearchOn()))
			GET_ATTENDEELIST_INFO+=" and c.ordernumber='"+reportsData.getSearchContent().trim()+"'";
		if("email".equals(reportsData.getSearchOn()))
			GET_ATTENDEELIST_INFO+=" and lower(a.email)=lower('"+reportsData.getSearchContent().trim()+"')";
		if("name".equals(reportsData.getSearchOn()))
			GET_ATTENDEELIST_INFO+=" and lower(a.fname||' '||a.lname) like lower('%"+reportsData.getSearchContent().trim()+"%')";
	}else{
		if(export){
			if(!"1".equals(selindex))
				GET_ATTENDEELIST_INFO+=" and bookingsource='"+selindex+"'";
			
			if("1".equals(reportsData.getTicket())){	
				
			}
			else if("2".equals(reportsData.getTicket())){	
				GET_ATTENDEELIST_INFO+=" and a.tickettype='attendeeType' ";
			}
			else{	
				GET_ATTENDEELIST_INFO+=" and  b.ticketid="+reportsData.getTicket();
			}
		}
		if("2".equals(reportsData.getAttdatetype())){
			if(reportsData.getEventStartDate().indexOf(",")>-1)reportsData.setEventStartDate(reportsData.getEventStartDate().replace(",",""));
			if(reportsData.getEventEndDate().indexOf(",")>-1)reportsData.setEventEndDate(reportsData.getEventEndDate().replace(",",""));
			String[] startdatearr=GenUtil.strToArrayStr(reportsData.getEventStartDate().trim(), "/");
			String startdate=startdatearr[2]+"-"+startdatearr[0]+"-"+startdatearr[1];
			String[] enddatearr=GenUtil.strToArrayStr(reportsData.getEventEndDate().trim(), "/");
			String enddate=enddatearr[2]+"-"+enddatearr[0]+"-"+enddatearr[1];
			GET_ATTENDEELIST_INFO+=" and to_char(c.transaction_date,'yyyy-mm-dd') between '"+startdate+"' and '"+enddate+"'"; 
		}
	}
	
	
	if(isrecurr){
		if(!"all".equals(reportsData.getDate()) && reportsData.getDate()!=null)
		  GET_ATTENDEELIST_INFO+=" and c.eventdate=?";
	}
	
	String orderby=attColumnMap.get(sortField);
	if(orderby==null) orderby="attendeekey";
	
	if("dfname".equals(orderby)) orderby="lower(a.fname)";
	if("dlname".equals(orderby)) orderby="lower(a.lname)";
	if("buyername".equals(orderby)) orderby="lower(c.fname)";
	
	if(sortDirection==null || "".equals(sortDirection)) sortDirection="desc";
		GET_ATTENDEELIST_INFO+=" order by "+orderby+" "+sortDirection;
		
	//System.out.println("GET_ATTENDEELIST_INFO: "+GET_ATTENDEELIST_INFO);
	DBManager dbmanager=new DBManager();
	StatusObj stobj=null;
	long starttime=System.currentTimeMillis();
	if(isrecurr){
		System.out.println("ReportsDB getAttendeeListInfo isrecurr: "+reportsData.getDate());
	   if("all".equals(reportsData.getDate()))
		     stobj=dbmanager.executeSelectQuery(GET_ATTENDEELIST_INFO,new String[]{eid});
	   else	
			stobj=dbmanager.executeSelectQuery(GET_ATTENDEELIST_INFO,new String[]{eid,reportsData.getDate()});
	}else
		stobj=dbmanager.executeSelectQuery(GET_ATTENDEELIST_INFO,new String[]{eid});

	long totaltime=(System.currentTimeMillis())-starttime;

	long forloopstarttime=System.currentTimeMillis();
	ArrayList<HashMap<String,String>> attendeeList=new ArrayList<HashMap<String,String>>();		 
			 if(stobj.getStatus()){
				for(int i=0;i<stobj.getCount();i++){
					HashMap<String,String> attinfo=new HashMap<String,String>();
					attinfo.put("FN",dbmanager.getValue(i,"dfname",""));
					attinfo.put("LN",dbmanager.getValue(i,"dlname","") );
					attinfo.put("BN",dbmanager.getValue(i,"buyername",""));
					attinfo.put("BE",dbmanager.getValue(i,"buyeremail",""));
					attinfo.put("BP",dbmanager.getValue(i,"buyerphone",""));
					attinfo.put("Em",dbmanager.getValue(i,"demail",""));
					attinfo.put("Ph",dbmanager.getValue(i,"phone","") );
					attinfo.put("ON",dbmanager.getValue(i,"ordernumber",""));
					attinfo.put("TD",dbmanager.getValue(i,"trdate",""));
					String trnsid=dbmanager.getValue(i,"transactionid","");
					String transactionid="<a href='TransactionDetails!getTransactionInfo?eid="+eid+"&tid="+trnsid+"'>View</a>";
					attinfo.put("TID",trnsid);
					attinfo.put("AK",dbmanager.getValue(i,"attendeekey","") );
					attinfo.put("SC",dbmanager.getValue(i,"seatcode","") );
					String evendate=dbmanager.getValue(i,"eventdate","");
					if("null".equals(evendate)) evendate="";
					attinfo.put("ED", evendate);
					HashMap buyerattribhm=new HashMap(); 
					HashMap attribhm=new HashMap();
					
					if (attribresponse!=null&&attribresponse.size()>0){		
						attribhm=(HashMap)attribresponse.get(dbmanager.getValue(i,"attendeekey",""));
						buyerattribhm=(HashMap)attribresponse.get(dbmanager.getValue(i,"buyerkey",""));
						if(buyerattribhm==null) buyerattribhm=new HashMap();
						if(attribhm==null) attribhm=new HashMap();
					}
					for(int p=0;p<attQuestionFields.size();p++){
						String a= (String)attQuestionFields.get(p);
						String val=(String)attribhm.get(a);
						if(val==null) val=(String)buyerattribhm.get(a);
						if(val==null)	val="";
						if(val.indexOf("##")>0)	val=val.replaceAll("##",", ");
						attinfo.put(a,val);
					}
						
						String Ticketname=dbmanager.getValue(i,"ticketname","");
						String ticketPrice=dbmanager.getValue(i,"ticketprice","");
						String ticketid=dbmanager.getValue(i,"ticketid","");
						
						String groupName=dbmanager.getValue(i,"groupname","");
						if(!groupName.equals("") && groupName != null)
							Ticketname=Ticketname+" ("+groupName+")";
						
						attinfo.put("TN",Ticketname);
						attinfo.put("TP",ticketPrice);
						attinfo.put("BKS",dbmanager.getValue(i,"bookingsource",""));
						attinfo.put("TKID",dbmanager.getValue(i,"ticketid","1"));
						if("attendeeType".equals(dbmanager.getValue(i,"tickettype","")))
							attinfo.put("ATT","2");
						else attinfo.put("ATT","");
						attinfo.put("ST",dbmanager.getValue(i,"paymentstatus",""));
						attendeeList.add(attinfo);
				}
			 }
			System.out.println("Attendee_Reports_eid: "+eid+" UUID:  getAttendeeListInfo(ReportsDB.java) ended, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");

			long forlooptotaltime=(System.currentTimeMillis())-forloopstarttime;
			System.out.println("Attendee_Reports_eid: "+eid+" UUID: Total time taken to execute ** for loop to get attendeeList **: "+forlooptotaltime+" MS");
			 
			return attendeeList;
}


	public static String customQuestionsFilter(ArrayList selcustomlist){
	
		String attriblist="";
		for(int i=0;i<selcustomlist.size();i++){
			String key=(String)selcustomlist.get(i);	
			if(i==selcustomlist.size()-1)
				attriblist=attriblist+key;
			else
				attriblist=attriblist+key+",";
		}
		
		return attriblist;
	
	}

	/*private final static String RESPONSE_QUERY="select a.attribid,a.bigresponse as response,b.profilekey " +
			"from  custom_questions_response a,custom_questions_response_master b where a.ref_id=b.ref_id " +
			"and b.groupid=CAST(? AS BIGINT)  and a.attribid in (select attrib_id from report_custom_questions_filter " +
			"where eventid=CAST(? AS BIGINT) and reporttype=?) and a.bigresponse!='' order by a.ref_id";*/	
	
	public static HashMap getResponses(String eid, ArrayList attQuestionFields, String reportType){
		HashMap hm=null;
		String attriblist=customQuestionsFilter(attQuestionFields);
		if(!"".equals(attriblist)){
			DBManager dbmanager=null;
		    String readDsn=DsnProperty.get("db.dsn.reports","").trim();
		    if(!"".equals(readDsn))
		    	dbmanager=new DBManager(readDsn);
		    else
		    	dbmanager=new DBManager();
		    
		  //attrib_id_list cont be available in lb2 while accessing directly from lb2, so that we didn't write inner query. changed on 2013,Oct 21st.
		    
		    String RESPONSE_QUERY="select a.attribid,a.bigresponse as response,b.profilekey " +
			"from  custom_questions_response a,custom_questions_response_master b where a.ref_id=b.ref_id " +
			"and b.groupid=CAST(? AS BIGINT)  and a.attribid in ("+attriblist+") and a.bigresponse!='' order by a.ref_id";
			
			StatusObj statobj=null;
			
			long attribresponsesttime=System.currentTimeMillis();
			statobj=dbmanager.executeSelectQuery(RESPONSE_QUERY,new String []{eid});
			long attribresponsetotaltime=(System.currentTimeMillis())-attribresponsesttime;
			System.out.println("Attendee_Reports_eid: "+eid+" UUID: Total time taken to execute RESPONSE_QUERY: "+attribresponsetotaltime+" MS");
			int count=statobj.getCount();
			System.out.println("Attendee_Reports_eid: "+eid+" UUID: Total ATTENDEE RESPONSES size: "+count);
			if(statobj.getStatus()&&count>0){
			long attribresponseloopsttime=System.currentTimeMillis();
				hm=new HashMap();
				for(int k=0;k<count;k++){
					String profilekey=dbmanager.getValue(k,"profilekey","");
					HashMap options=(HashMap)hm.get(profilekey);
					if (options==null)	options=new HashMap();
					options.put(dbmanager.getValue(k,"attribid","0"),dbmanager.getValue(k,"response","0"));
					hm.put(profilekey,options);
				}
			}
		}
		return hm;
	}


	public static final String GET_TICKETS_INFO="select ticketqty,ticketname,ticketid,tid,groupname,ticketprice,(discount/ticketqty) as ticketdiscount,fee as ticketfee from transaction_tickets where eventid=? order by ticketid";
	public static final String GET_NOTES_INFO="select tid,notes from transaction_notes where tid in (select tid from event_reg_transactions where eventid=? order by tid)"; //Rahul
	public static HashMap getTransactionNotesInfo(String eid){
		HashMap notesMap=new HashMap();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(GET_NOTES_INFO,new String [] {eid});
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
				notesMap.put(dbmanager.getValue(i,"tid", null), dbmanager.getValue(i, "notes",""));
			}
		}
		return notesMap;
	}
	public static HashMap getTicketInfo(String groupid){
		HashMap hmap=new HashMap();
		DBManager dbmanager=null;
	    String readDsn=DsnProperty.get("db.dsn.reports","").trim();
	    if(!"".equals(readDsn))
	    	dbmanager=new DBManager(readDsn);
	    else
	    	dbmanager=new DBManager();
		//DBManager dbmanager=new DBManager(EventbeeConnection.readDsn);
		HashMap ticketHistory = null;
		StatusObj statobj=dbmanager.executeSelectQuery(GET_TICKETS_INFO,new String [] {groupid});
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
				String tid =dbmanager.getValue(i,"tid",null);
				String ticketqty=dbmanager.getValue(i,"ticketqty","0");
				String ticketprice=dbmanager.getValue(i,"ticketprice","0");
				String ticketname=dbmanager.getValue(i,"ticketname","");
				String ticketid=dbmanager.getValue(i,"ticketid","");
				String groupName=dbmanager.getValue(i,"groupname","");
				String ticketdiscount=CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i,"ticketdiscount","0.00"),true);
				String ticketfee=dbmanager.getValue(i,"ticketfee","0");
				ticketfee=CurrencyFormat.getCurrencyFormat("",ticketfee,true);
				double price=0;
				price = Double.parseDouble(ticketprice) - Double.parseDouble(ticketfee);
				ticketprice=String.valueOf(price);
				ticketprice=CurrencyFormat.getCurrencyFormat("",ticketprice,true);
				if(!"".equals(groupName) && groupName!=null)
			//	ticketname=groupName+" - "+ticketname;
				ticketname=ticketname+"("+groupName+")";
				else
				ticketname=ticketname;
				if(hmap.get(tid)!=null){
					ticketHistory = (HashMap)hmap.get(tid);
					ticketname=ticketHistory.get("DESC")+", "+ticketname;
					ticketqty=ticketHistory.get("Count")+", "+ticketqty;
					ticketprice=ticketHistory.get("Price")+", "+ticketprice;
					ticketid=ticketHistory.get("TicketId")+", "+ticketid;
					ticketdiscount=ticketHistory.get("Discount")+", "+ticketdiscount;
					ticketfee=ticketHistory.get("Fee")+", "+ticketfee;
				 }else{
				  	ticketHistory = new HashMap();
				 }
				 ticketHistory.put("Count",ticketqty);
				 ticketHistory.put("Price",ticketprice);
				 ticketHistory.put("DESC",ticketname);
				 ticketHistory.put("TicketId",ticketid);
				 ticketHistory.put("Discount",ticketdiscount);
				 ticketHistory.put("Fee",ticketfee);
				 hmap.put(tid,ticketHistory);
			}
		}
		
		return hmap;
	}
	private static StatusObj executeQuery(String baseQuery, boolean isrecurr, String date, 
			String typeIndex, String tckid,DBManager dbmanager, String[] params,String sortField,String sortDirection,boolean export){
		StatusObj statobj=null;
		String finalQuerypart=baseQuery;
		String[] newParams;
		if(isrecurr){
			if(!"all".equals(date)){
				newParams=new String[params.length+1];
				finalQuerypart+=commonreccuringpart;
			}else 
				newParams=new String[params.length];
		}
		else
			newParams=new String[params.length];
		for(int i=0;i<params.length;i++){
			newParams[i]=params[i];
		}
		if(isrecurr) {
			if(!"all".equals(date))
			newParams[params.length]=date;
		}
		
		if(export && !"1".equals(tckid)){
			finalQuerypart+=" and tid in (select tid from transaction_tickets where ticketid="+tckid+")";				
		}

			String orderby=tranColumnMap.get(sortField);
			if(orderby==null) orderby="trdate";
			if("dfname".equals(orderby)) orderby="lower(fname)";
			if("dlname".equals(orderby)) orderby="lower(lname)";
			if(sortDirection==null || "".equals(sortDirection)) sortDirection="desc";
			finalQuerypart+=" order by "+orderby+" "+sortDirection;
			//System.out.println("finalQuerypart: "+finalQuerypart);
			statobj=dbmanager.executeSelectQuery(finalQuerypart,newParams);
			System.out.println("executeQuery(ReportsDB.java) ended , free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		return statobj;
	}
	static{
		tranColumnMap.put("TID", "tid");
		tranColumnMap.put("ON", "ordernumber");
		tranColumnMap.put("TD", "transaction_date");
		tranColumnMap.put("St", "paymentstatus");
		tranColumnMap.put("FN", "dfname");
		tranColumnMap.put("LN", "dlname");
		tranColumnMap.put("Em", "demail");
		tranColumnMap.put("Ph", "phone");
		tranColumnMap.put("TU", "trackpartner");
		//tranColumnMap.put("Source", "trackpartner");
		tranColumnMap.put("Di", "current_discount");
		tranColumnMap.put("DC", "discountcode");
		tranColumnMap.put("PT", "paymenttype");
		tranColumnMap.put("ECCF", "ccfee");
		tranColumnMap.put("EPID", "ext_pay_id");
		tranColumnMap.put("ESF", "servicefee");
		tranColumnMap.put("CSF", "collected_servicefee");
		tranColumnMap.put("TTC", "current_amount");
		tranColumnMap.put("CCPF", "current_tax");
		tranColumnMap.put("ED", "eventdate");
	}
	
	private static ArrayList<HashMap<String,String>> getTransactionDetailsList(StatusObj statobj, String eid,
			HashMap attribresponses,ArrayList attributes,HashMap ticketsinfo,HashMap notesinfo,
			DBManager dbmanager,ArrayList Fields){
		System.out.println("Transaction_Reports_UUID: getTransactionDetailsList(ReportsDB.java) started, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		ArrayList<HashMap<String,String>> transactionDetailsList=new ArrayList<HashMap<String,String>>();
		long starttime=System.currentTimeMillis();
		HashMap attribhm=null;
		 try {
			if(statobj.getStatus()){
				
					for(int i=0;i<statobj.getCount();i++){
						HashMap<String,String> traninfo=new HashMap<String,String>();
						String trnsid=dbmanager.getValue(i,"tid","");
						String transactionid="<a href='TransactionDetails!getTransactionInfo?eid="+eid+"&tid="+trnsid+"'>View</a>";
						String paymenttype=dbmanager.getValue(i,"paymenttype","");
						String ccvendor=dbmanager.getValue(i,"cc_vendor","");
						String collectedby=dbmanager.getValue(i,"collected_by","");
						traninfo.put("TID",trnsid);
						traninfo.put("TD",dbmanager.getValue(i,"trdate",""));
						traninfo.put("FN",dbmanager.getValue(i,"dfname",""));
						traninfo.put("LN",dbmanager.getValue(i,"dlname",""));
						traninfo.put("Ph",dbmanager.getValue(i,"phone",""));
						traninfo.put("TTC",dbmanager.getValue(i,"current_amount","0"));
						traninfo.put("PID",dbmanager.getValue(i,"partnerid",""));
						traninfo.put("CCPF",dbmanager.getValue(i,"current_tax","0"));
						traninfo.put("RA",dbmanager.getValue(i,"refund_amount","0.00"));
						String evendate=dbmanager.getValue(i,"eventdate","");
						if("null".equals(evendate)) evendate="";
						traninfo.put("ED", evendate);
						double ebeefee=0.0;
						ebeefee=Double.parseDouble(dbmanager.getValue(i,"servicefee","0"));
						traninfo.put("ESF", CurrencyFormat.getCurrencyFormat("",ebeefee+"",true));
						double cardfee=0.0;
						cardfee=Double.parseDouble(dbmanager.getValue(i,"ccfee","0.00"));
						//traninfo.put("ECCF", CurrencyFormat.getCurrencyFormat("",cardfee+"",true));
						double collectedservfee=0.0;
						collectedservfee=Double.parseDouble(dbmanager.getValue(i,"collected_servicefee","0.00"));
						String csf=CurrencyFormat.getCurrencyFormat("",collectedservfee+"",true);
						if(collectedservfee > 0 && !"".equals(collectedby)){
							if("paypalx".equals(collectedby)) collectedby="paypal";
							traninfo.put("CSF", csf+" ("+collectedby+")");
						}else traninfo.put("CSF", csf);
						double ntscommission = Double.parseDouble(dbmanager.getValue(i,"current_nts","0"));
						traninfo.put("NTSC",CurrencyFormat.getCurrencyFormat("",ntscommission+"",true));
						double totalnet=0;
						double grandtotal1=0;
						grandtotal1=Double.parseDouble(dbmanager.getValue(i,"current_amount","0"));
						
						double vtscommission=0.0;//volume ticket selling commission
						String bookingdomain = dbmanager.getValue(i,"bookingdomain","");
						if(bookingdomain.contains("VBEE"))
							vtscommission=(grandtotal1*10)/100;
						double ebeeccfee=0.0;double otherccfee=0.0;
						if("eventbee".equalsIgnoreCase(paymenttype)){
							ebeeccfee=cardfee;
						}else if(Fields.contains("OCCF")){
							otherccfee=cardfee;
						}
						traninfo.put("ECCF", CurrencyFormat.getCurrencyFormat("",ebeeccfee+"",true));
						traninfo.put("OCCF",CurrencyFormat.getCurrencyFormat("",otherccfee+"",true));
						//totalnet=grandtotal1-ebeefee-cardfee-ntscommission-vtscommission-otherccfee;
						totalnet=grandtotal1-ebeefee-ebeeccfee-otherccfee-ntscommission;
						
						traninfo.put("VTSC",CurrencyFormat.getCurrencyFormat("",vtscommission+"",true));
						traninfo.put("TNet",CurrencyFormat.getCurrencyFormat("",totalnet+"",true));
						traninfo.put("Bal",CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i,"amount_we_have","0")+"",true));
						//traninfo.put("Net",CurrencyFormat.getCurrencyFormat("",net+"",true));
						//traninfo.put("CSF",CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i,"collected_servicefee","0.00"),true));
						traninfo.put("Due",CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i,"due","0.00"),true));
						traninfo.put("EPID",dbmanager.getValue(i,"ext_pay_id",""));
						String agentid=dbmanager.getValue(i,"partnerid","null");
						/*String discount=dbmanager.getValue(i,"current_discount","");
						if(discount==null) discount="0";
						traninfo.put("Di",discount);*/
						traninfo.put("BKS",dbmanager.getValue(i,"bookingsource",""));

						if("authorize.net".equalsIgnoreCase(paymenttype)) paymenttype="Authorize.Net";
						else if("braintree".equalsIgnoreCase(paymenttype))paymenttype="Braintree";
						else if("stripe".equalsIgnoreCase(paymenttype))paymenttype="Stripe";
						else if("eventbee".equals(paymenttype)) paymenttype="Eventbee";
						else if("google".equals(paymenttype)) paymenttype="Google";
						else if("paypal".equals(paymenttype)) paymenttype="PayPal";
						else if("other".equals(paymenttype)) paymenttype="Other";
						else if("nopayment".equals(paymenttype)) paymenttype="No Payment";
						else if("other-needapproval".equals(paymenttype)) paymenttype="Other - Pending Approval";
						else if("other-approved".equals(paymenttype)) paymenttype="Other - Approved";
						else if("Bulk".equals(paymenttype)) paymenttype="Manual";
						traninfo.put("PT",paymenttype);
						String status=dbmanager.getValue(i,"paymentstatus","");
						if("completed".equals(status) || "COMPLETED".equals(status) || "Completed".equals(status)||"CHARGED".equals(status)||"A".equals(status)||"Y".equals(status))
						status="Completed";
						else if("CANCELLED".equals(status) || "Cancelled".equals(status))
							status="Deleted";
						else if(status.equals("Need Approval"))
							status="Pending Approval";
						traninfo.put("St",status);
						traninfo.put("ON",dbmanager.getValue(i,"ordernumber",""));
						String trackurl=dbmanager.getValue(i,"trackpartner","");
						String sourcecode=dbmanager.getValue(i,"trackpartner","");
						
						if("null".equals(sourcecode) || "".equals(sourcecode) || sourcecode==null) sourcecode="Main URL";
						if(!("null".equals(agentid) ) && !("".equals(agentid))) sourcecode="NTS";
						if(!("null".equals(trackurl) ) && !("".equals(trackurl))) sourcecode="Track URL";
						
						if("FBApp".equals(bookingdomain))
							traninfo.put("So","Facebook Application");
						else
							traninfo.put("So",sourcecode);
						traninfo.put("DC",dbmanager.getValue(i,"discountcode",""));
						traninfo.put("Em",dbmanager.getValue(i,"demail",""));
						String trackingurl=dbmanager.getValue(i,"trackpartner","");
						if("null".equals(trackingurl) || trackingurl==null) trackingurl="";
						traninfo.put("TU",trackingurl);
						String notes="";
						if(notesinfo!=null){
							if(notesinfo.get(trnsid)!=null)
							notes=(String)notesinfo.get(trnsid);
							if(notes==null)	notes="";							 
						}
						traninfo.put("No",notes);
						String Ticketname="";String TicketCount="";
						String TicketPrice="";String TicketDiscount="";String TicketFee="",TicketID="";
						HashMap TIDTicketHistory = (HashMap)ticketsinfo.get(dbmanager.getValue(i,"tid",""));
						if(TIDTicketHistory!=null){			
							TicketCount=GenUtil.getHMvalue(TIDTicketHistory,"Count");
							Ticketname=GenUtil.getHMvalue(TIDTicketHistory,"DESC");
							TicketPrice=GenUtil.getHMvalue(TIDTicketHistory,"Price");
							TicketDiscount=GenUtil.getHMvalue(TIDTicketHistory,"Discount");
							TicketFee=GenUtil.getHMvalue(TIDTicketHistory,"Fee");
							TicketID=GenUtil.getHMvalue(TIDTicketHistory,"TicketId");
							traninfo.put("TC",TicketCount);
							traninfo.put("TN",Ticketname);
							traninfo.put("TP",TicketPrice);
							traninfo.put("Di",TicketDiscount);
							traninfo.put("SF",TicketFee);
							traninfo.put("TKTID",TicketID);
						}
						if (attribresponses!=null&&attribresponses.size()>0){
							attribhm=(HashMap)attribresponses.get(dbmanager.getValue(i,"tid",""));
							if(attribhm==null) attribhm=new HashMap();
						}
						
						if(attribhm!=null&&attribhm.size()>0){
								for(int p=0;p<attributes.size();p++){
									String a =  (String)attributes.get(p);
									String val=(String)attribhm.get(a);
									if(val==null)val="";
									if(val.indexOf("##")>0)
										val=val.replaceAll("##",", ");	
									traninfo.put(a,val);
									
								}
							}
							else{
								for(int p=0;p<attributes.size();p++){
									String a = (String)attributes.get(p);
									traninfo.put(a,"");
								}
							}
						
						transactionDetailsList.add(traninfo);
					}
				
			 }
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Transaction_Reports_UUID: eid: "+eid+": getTransactionDetailsList(ReportsDB.java) ended, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long totaltime=(System.currentTimeMillis())-starttime;
		System.out.println("Transaction_Reports_UUID: eid: "+eid+": Total time taken to get  getTransactionDetailsList: "+totaltime+" MS");
		 return transactionDetailsList;
	}
	
	/*public static final String  commonselectpart="select distinct partnerid,lower(email) as demail,discountcode,trackpartner," +
	"tid,initcap(fname) as dfname,initcap(lname) as dlname,paymenttype ,initcap(fname) || ' ' || initcap(lname)  as name,upper(fname), upper(lname)," +
	"to_char(transaction_date,'yyyy-mm-dd') as transaction_date," +
	"to_char(transaction_date,'mm/dd/yyyy') as trdate,ordernumber,phone," +
	"current_nts,current_amount,servicefee,collected_servicefee,(servicefee-collected_servicefee) as due,ccfee,current_nts,current_discount,paymentstatus,ext_pay_id " +
	" from event_reg_transactions where eventid=? and bookingsource='online'";
*/
	public static final String  commonselectpart="select partnerid,lower(email) as demail,discountcode," +
	"trackpartner,tid,fname as dfname,lname as dlname,paymenttype ,fname || ' ' || lname  as name," +
	"upper(fname), upper(lname),transaction_date," +
	"to_char(transaction_date,'yyyy/mm/dd') as trdate,ordernumber,phone,current_nts,partnerid,current_amount," +
	"servicefee,collected_servicefee,(servicefee-collected_servicefee) as due,ccfee,current_discount,paymentstatus," +
	"ext_pay_id,bookingdomain,current_tax,collected_by,amount_we_have,cc_vendor,refund_amount,eventdate,bookingsource from event_reg_transactions where eventid=?";

public static  String ALL_TRANSACTION_DETAILS=" and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') )  ";

//public  static String TRANSACTION_DETAILS_BEETWEEN_DATES=" and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') )  and to_char(transaction_date,'yyyy-mm-dd') between ? and ? ";

public  static String TRANSACTION_DETAILS_BEETWEEN_DATES=" and to_char(transaction_date,'yyyy-mm-dd') between ? and ? ";

public static  String TRANSACTION_DETAILS_TRANSACTIONID=" and  tid=? ";

public static  String TRANSACTION_DETAILS_ATTENDEENAME=" and lower(fname||' '||lname) like lower(?) ";

public static  String TRANSACTION_DETAILS_PAYMENTSTATUS=" and  upper(paymentstatus)=upper(?)";

public static  String TRANSACTION_DETAILS_ORDERNUMBER=" and ordernumber=? ";

public  static String TRANSACTION_DETAILS_TRACKCODE=" and  trackpartner=?";

public static  String TRANSACTION_DETAILS_DIRECT=" and  (trackpartner is null or trackpartner='' or trackpartner='null') and (partnerid is null  or partnerid='null' or partnerid='') and (bookingdomain is null or bookingdomain ='' or bookingdomain ='null' or bookingdomain!='FBApp')";

public static  String TRANSACTION_DETAILS_NTS="  and partnerid is not null and partnerid <>'null' and partnerid!=''";

public static  String TRANSACTION_DETAILS_ALLTRACKCODES=" and trackpartner is not null and trackpartner <>'null' and trackpartner<>''";

public  static String TRANSACTION_DETAILS_FBAPP=" and upper(bookingdomain)=upper(?) ";

public static  String TRANSACTION_DETAILS_RECURRING_EVENT=" and eventdate=? ";

public static  String TRANSACTION_DETAILS_BUYER_EMAIL=" and lower(email)=lower(?) ";
	
public static ArrayList<HashMap<String,String>> getTransactionInfo(String eid,String selectedvalue,HashMap attribresponses,
			ArrayList attributes,HashMap ticketsinfo,HashMap notesinfo,ReportsData reportsData,String cardtype,String typeIndex,String sortField,
			String sortDirection,ArrayList Fields, boolean export){
		System.out.println("Transaction_Reports_eid: "+eid+" UUID:  getTransactionInfo(ReportsDB.java) started, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long starttime=System.currentTimeMillis();
		boolean isrecurr = EventDB.isRecurringEvent(eid);
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String date=reportsData.getDate();
		String finalQuery=commonselectpart;
		String[] inparams={eid};
		
		if(reportsData.getIsSearch()){
			if("transactionId".equals(reportsData.getSearchOn())){
				finalQuery+=TRANSACTION_DETAILS_TRANSACTIONID;
				inparams=addParam(inparams, reportsData.getSearchContent().trim());
			}
			if("orderNumber".equals(reportsData.getSearchOn())){
				finalQuery+=TRANSACTION_DETAILS_ORDERNUMBER;
		        inparams=addParam(inparams, reportsData.getSearchContent().trim());
			}
			if("email".equals(reportsData.getSearchOn())){
				finalQuery+=TRANSACTION_DETAILS_BUYER_EMAIL;
				inparams=addParam(inparams, reportsData.getSearchContent().trim());
			}
			if("name".equals(reportsData.getSearchOn())){
				String attendee=reportsData.getAttendeeName().trim();
				finalQuery+=TRANSACTION_DETAILS_ATTENDEENAME;
				inparams=addParam(inparams,"%"+reportsData.getSearchContent().trim()+"%");
			}
			
			statobj=executeQuery(finalQuery, isrecurr, date, typeIndex, "",dbmanager,inparams,sortField,sortDirection,export);	
			}else{
				if(export){
					if(reportsData.getOnline().equals("on") && reportsData.getManual().equals(""))
						finalQuery+=" and bookingsource='online'";
					else if(reportsData.getOnline().equals("") && reportsData.getManual().equals("on"))
						finalQuery+=" and bookingsource='Manager'";
					if(!"7".equals(reportsData.getPaymenttypeindex())){
						finalQuery+="and paymenttype=?";
						inparams=addParam(inparams, cardtype);
					}
					
					if(!"1".equals(reportsData.getPaymentstaus())){
				        String paymentstaus=reportsData.getPaymentstaus();
				        if("Pending Approval".equals(paymentstaus)) paymentstaus="Need Approval";
				        finalQuery+=TRANSACTION_DETAILS_PAYMENTSTATUS;
				        inparams=addParam(inparams, paymentstaus);
					}
					
					String source=reportsData.getSource();
					if("1".equals(source)){
					}else if("direct".equals(source)){
						finalQuery+=TRANSACTION_DETAILS_DIRECT;
					}else if("nts".equals(source)){
						finalQuery+=TRANSACTION_DETAILS_NTS;
					}else if("alltrackcodes".equals(source)){
						finalQuery+=TRANSACTION_DETAILS_ALLTRACKCODES;
					}else if("fbapp".equals(source)){
						finalQuery+=TRANSACTION_DETAILS_FBAPP;
						inparams=addParam(inparams, source);
					}else{
						finalQuery+=TRANSACTION_DETAILS_TRACKCODE;
						inparams=addParam(inparams, source);
					}
				}
				if("2".equals(reportsData.getSalesDateType())){	
					if(reportsData.getEventTranStartDate().indexOf(",")>-1)reportsData.setEventTranStartDate(reportsData.getEventTranStartDate().replace(",",""));
					if(reportsData.getEventTranEndDate().indexOf(",")>-1)reportsData.setEventTranEndDate(reportsData.getEventTranEndDate().replace(",",""));
					String[] startdatearr=GenUtil.strToArrayStr(reportsData.getEventTranStartDate().trim(), "/");
					String[] enddatearr=GenUtil.strToArrayStr(reportsData.getEventTranEndDate().trim(), "/");
					String startdate=startdatearr[2]+"-"+startdatearr[0]+"-"+startdatearr[1];
					String enddate=enddatearr[2]+"-"+enddatearr[0]+"-"+enddatearr[1];
					
					finalQuery+=TRANSACTION_DETAILS_BEETWEEN_DATES;
					inparams=addParam(inparams, startdate);
					inparams=addParam(inparams, enddate);
						
				}
				

				statobj=executeQuery(finalQuery, isrecurr, date, typeIndex, reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,export);	

		}
		//System.out.println("sales reports finalQuery:: "+finalQuery);
		
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: getTransactionInfo(ReportsDB.java) ended, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long totaltime=(System.currentTimeMillis())-starttime;
		System.out.println("Transaction_Reports_eid: "+eid+" UUID: Total time taken to excute TRANSACTION_DETAILS query: "+totaltime+" MS");

		
		return getTransactionDetailsList(statobj,eid,attribresponses,attributes,ticketsinfo,notesinfo,dbmanager,Fields); 
		  
	}
	public static String[] addParam(String[] inparam,String param){
		int lastindex=inparam.length;
		String[] temp=new String[lastindex+1];
		for(int i=0;i<lastindex;i++)
			temp[i]=inparam[i];
		temp[lastindex]=param;
		return temp;
	}

	
public static ArrayList<HashMap<String,String>> getAllReports(String eid,String selindex,HashMap byerResponses,
		ArrayList buyerAttribs, HashMap ticketsinfo,ReportsData reportsData,String typeIndex,String sortField,
		String sortDirection,ArrayList Fields,boolean export){
		String paymenttype="";
		ArrayList<HashMap<String,String>> transactionDetailsList=new ArrayList<HashMap<String,String>>();	

		/*String[] vendorTypes=new String[]{"eventbee","authorize.net","braintree","stripe","google", "paypal","ebeecredits","","nopayment","other"};

 		int paymentTypeIndex=Integer.parseInt(reportsData.getPaymenttypeindex());

 		if(paymentTypeIndex==7) paymenttype="all";
 		else paymenttype=vendorTypes[paymentTypeIndex];*/
 		
 		transactionDetailsList=getTransactionInfo(eid,selindex,byerResponses,buyerAttribs,ticketsinfo,getTransactionNotesInfo(eid),reportsData,paymenttype,typeIndex,sortField,sortDirection,Fields,export);
    		
    	return transactionDetailsList;
	}


	public static ArrayList getBuyerAttributes(String eventid){
		ArrayList  buyerAttribList=new ArrayList();
		try{
			DBManager db=new DBManager();
			StatusObj sb=db.executeSelectQuery(BUYER_ATTRIBUTES_QUERY, new String[]{eventid,eventid});
			if(sb.getStatus()){
				for(int p=0;p<sb.getCount();p++){
					String buyerattribute=db.getValue(p,"attribname","");

					//converting html to text as html tags are not closing while truncating...  on 15th April 2015
					buyerattribute=JsoupHtml2Text.getPlainText(buyerattribute);
					buyerattribute=TruncateData(buyerattribute,33);
					buyerAttribList.add(new Entity(db.getValue(p,"attrib_id",""),buyerattribute));	
					}

			}
		}catch(Exception e){
			System.out.println("Exception ocuured in"+e.getMessage());
		}
		return buyerAttribList;
	}
	
	public static HashMap getAllBuyerAttributesMap(String eid){

		DBManager dbmanager=new DBManager();
		ArrayList buyerAttribList=new ArrayList();
		ArrayList<Entity> subquestionlist=null;
		StatusObj statobj=null;
		HashMap hm=new HashMap();
		HashMap optionLabelMap=new HashMap();
		HashMap subquestionofMap=new HashMap();
		HashMap attribOptionMap=new HashMap();
		ArrayList attribOptionList=null;
		StringBuffer sb = new StringBuffer();
		String attribsetid="";
		statobj=dbmanager.executeSelectQuery(BUYER_ATTRIBUTES_QUERY,new String []{eid,eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
				String attribval=dbmanager.getValue(k,"attribname","");
				attribval=JsoupHtml2Text.getPlainText(attribval);
				attribval=TruncateData(attribval,33);
						
				if(dbmanager.getValue(k,"subquestionof","no").equals("no")){
					buyerAttribList.add(new Entity(dbmanager.getValue(k,"attrib_id",""),attribval));
				}else{
						if(subquestionofMap.containsKey(dbmanager.getValue(k,"subquestionof","")))
							subquestionlist=(ArrayList<Entity>) subquestionofMap.get(dbmanager.getValue(k,"subquestionof",""));
						else
							subquestionlist=new ArrayList<Entity>();
							
						subquestionlist.add(new Entity(dbmanager.getValue(k,"attrib_id",""),attribval));
						subquestionofMap.put(dbmanager.getValue(k,"subquestionof",""), subquestionlist);
							
						if(sb.length()>0)
							sb.append(",'"+dbmanager.getValue(k,"subquestionof","no")+"'");
						else sb.append("'"+dbmanager.getValue(k,"subquestionof","no")+"'");
						}
					}
					attribsetid=dbmanager.getValue(0,"attrib_setid","");
					if(sb.length()>0){
						String query="select attrib_id,option,option_val from custom_attrib_options where attrib_setid=CAST(? AS INTEGER) and attrib_id||'_'||option in("+sb.toString()+") order by attrib_id,position";
						statobj=dbmanager.executeSelectQuery(query,new String []{attribsetid});
						if(statobj.getStatus()&&statobj.getCount()>0){
							for(int j=0;j<statobj.getCount();j++){
								optionLabelMap.put(dbmanager.getValue(j,"attrib_id","")+"_"+dbmanager.getValue(j,"option",""),dbmanager.getValue(j,"option_val",""));
								if(attribOptionMap.containsKey(dbmanager.getValue(j,"attrib_id","")))
									attribOptionList=(ArrayList)attribOptionMap.get(dbmanager.getValue(j,"attrib_id",""));
								else attribOptionList=new ArrayList();
								attribOptionList.add(dbmanager.getValue(j,"attrib_id","")+"_"+dbmanager.getValue(j,"option",""));
								attribOptionMap.put(dbmanager.getValue(j,"attrib_id",""),attribOptionList);
							}
						}
					}
			}
					
					HashMap allmap =new HashMap();
					allmap.put("buyerattriblist", buyerAttribList);
					allmap.put("buyeroptionlabelmap", optionLabelMap);
					allmap.put("buyersubquestionofmap", subquestionofMap);
					allmap.put("buyerattriboptionmap", attribOptionMap);
		return allmap;
	 }
	/*private final static String BUYER_RESPONSE_QUERY="select a.attribid, a.bigresponse as response,b.transactionid " +
			"from custom_questions_response a,custom_questions_response_master b where a.ref_id=b.ref_id " +
			"and b.groupid=CAST(? AS BIGINT) and a.attribid in (select attrib_id from report_custom_questions_filter " +
			"where eventid=CAST(? AS BIGINT) and reporttype='buyer') and a.bigresponse!='' order by a.ref_id";*/

	public static HashMap getbuyerAttribresponses(String eventid, ArrayList buyerQuestionFields){
		HashMap hm=null;
		String attrib_id_list=customQuestionsFilter(buyerQuestionFields);
		if(!"".equals(attrib_id_list)){
			DBManager dbmanager=null;
		    String readDsn=DsnProperty.get("db.dsn.reports","").trim();
		    if(!"".equals(readDsn))
		    	dbmanager=new DBManager(readDsn);
		    else
		    	dbmanager=new DBManager();
			
		    //attrib_id_list cont be available in lb2 while accessing directly from lb2, so that we didn't write inner query. changed on 2013,Oct 21st.
		    
		    String BUYER_RESPONSE_QUERY="select a.attribid, a.bigresponse as response,b.transactionid " +
			"from custom_questions_response a,custom_questions_response_master b where a.ref_id=b.ref_id " +
			"and b.groupid=CAST(? AS BIGINT) and a.attribid in ("+attrib_id_list+") and a.bigresponse!='' order by a.ref_id";
		    
		    StatusObj statobj=null;
			
			long buyerResponsestarttime=System.currentTimeMillis();
			statobj=dbmanager.executeSelectQuery(BUYER_RESPONSE_QUERY,new String []{eventid});
			long buyerResponsetotaltime=(System.currentTimeMillis())-buyerResponsestarttime;
			System.out.println("Transaction_Reports_eid: "+eventid+" UUID: Total time taken execute BUYER_RESPONSE_QUERY: "+buyerResponsetotaltime+" MS");
	
			int count=statobj.getCount();
			System.out.println("Transaction_Reports_eid: "+eventid+" UUID: Total BUYER RESPONSES size: "+count);
			if(statobj.getStatus()&&count>0){
				long buyerResLoopstarttime=System.currentTimeMillis();
					hm=new HashMap();
					for(int k=0;k<count;k++){
						String userid=dbmanager.getValue(k,"transactionid","");
						HashMap options=(HashMap)hm.get(userid);
						if (options==null)
							options=new HashMap();
						options.put(dbmanager.getValue(k,"attribid","0"),dbmanager.getValue(k,"response","0"));
	
						hm.put(userid,options);
	
					}
			}
		}
	return hm;	
	}
	
	public static HashMap getScanStatusData(String eventid, String edate){
		HashMap scanDataMap=new HashMap();
		DBManager dbmanager = new DBManager();
		StatusObj statobj;
		if(edate==null){
			statobj = dbmanager.executeSelectQuery(GET_SCAN_DATA, new String[]{eventid});
		}else{
			statobj = dbmanager.executeSelectQuery(GET_SCAN_DATA_RECURRING, new String[]{eventid, edate, eventid});
		}
		int count = statobj.getCount();
		if (statobj.getStatus() && count > 0) {
			for (int k = 0; k < count; k++) {
				ScanData scanData=new ScanData();
				String attKey = dbmanager.getValue(k, "attendeekey", "");
				scanData.setAttendeekey(attKey);
				scanData.setCheckedby(dbmanager.getValue(k, "checkedby", ""));
				scanData.setCheckedintime(dbmanager.getValue(k, "checkedintime", ""));
				scanData.setCheckinstatus(dbmanager.getValue(k, "checkinstatus", ""));
				scanData.setScanid(dbmanager.getValue(k, "scanid", ""));
				scanData.setSyncedat(dbmanager.getValue(k, "syncedat", ""));
				scanData.setSyncedby(dbmanager.getValue(k, "syncedby", ""));
				scanData.setSyncedyn(dbmanager.getValue(k, "syncedyn", ""));
				scanData.setSyncuser(dbmanager.getValue(k, "syncuser", ""));
				scanData.setTid(dbmanager.getValue(k, "tid", ""));
				scanDataMap.put(attKey, scanData);
			}
		}
		return scanDataMap;
	}
	
	static{
		checkInColumnMap.put("TID", "transactionid");
		checkInColumnMap.put("AK", "profilekey");
		checkInColumnMap.put("FN", "dfname");
		checkInColumnMap.put("LN", "dlname");
		checkInColumnMap.put("Em", "demail");
		checkInColumnMap.put("ON", "ordernumber");
		checkInColumnMap.put("TT", "tickettype");
		checkInColumnMap.put("SC", "seatcode");
	}

	
	public static ArrayList<HashMap<String,String>> getCheckedInReports(String eid,String checkinreportsindex,ReportsData reportsData,String sortField,String sortDirection,boolean export){
		boolean isrecurr = EventDB.isRecurringEvent(eid);	
		HashMap scanDataMap;
		String checkinfilter=checkinreportsindex;
		String GET_CHECKEDINREPORTS_INFO="select distinct UPPER(a.fname) as fn,a.fname as dfname,UPPER(a.lname) as ln," +
				" a.lname as dlname,lower(a.email) as demail,profilekey,a.transactionid,a.seatcode,tickettype,ordernumber, c.paymentstatus" +
				" from profile_base_info a,  " +
				" event_reg_transactions c where (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') ) " +
				" and a.eventid=CAST(? AS BIGINT) and a.transactionid=c.tid ";
		
		if(reportsData.getIsSearch()){
			if("transactionId".equals(reportsData.getSearchOn())){
				if(reportsData.getSearchContent().trim().startsWith("AK"))
					GET_CHECKEDINREPORTS_INFO+=" and a.profilekey='"+reportsData.getSearchContent().trim()+"'";
				else
					GET_CHECKEDINREPORTS_INFO+=" and c.tid='"+reportsData.getSearchContent().trim()+"'";
			}
			if("orderNumber".equals(reportsData.getSearchOn()))
				GET_CHECKEDINREPORTS_INFO+=" and c.ordernumber='"+reportsData.getSearchContent().trim()+"'";
			if("email".equals(reportsData.getSearchOn()))
				GET_CHECKEDINREPORTS_INFO+=" and lower(a.email)=lower('"+reportsData.getSearchContent().trim()+"')";
			if("name".equals(reportsData.getSearchOn()))
				GET_CHECKEDINREPORTS_INFO+=" and lower(a.fname||' '||a.lname) like lower('%"+reportsData.getSearchContent().trim()+"%')";
			checkinfilter="1";
		}else{
			if("2".equals(reportsData.getChkinDateType())){
				if(reportsData.getChkInStartDate().indexOf(",")>-1)reportsData.setChkInStartDate(reportsData.getChkInStartDate().replace(",",""));
				if(reportsData.getChkInEndDate().indexOf(",")>-1)reportsData.setChkInEndDate(reportsData.getChkInEndDate().replace(",",""));
				String[] startdatearr=GenUtil.strToArrayStr(reportsData.getChkInStartDate().trim(), "/");
				String startdate=startdatearr[2]+"-"+startdatearr[0]+"-"+startdatearr[1];
				String[] enddatearr=GenUtil.strToArrayStr(reportsData.getChkInEndDate().trim(), "/");
				String enddate=enddatearr[2]+"-"+enddatearr[0]+"-"+enddatearr[1];
				GET_CHECKEDINREPORTS_INFO+=" and to_char(c.transaction_date,'yyyy-mm-dd') between '"+startdate+"' and '"+enddate+"'"; 
			}
		}
		

		if(isrecurr){
			if(!"all".equals(reportsData.getDate()))
			GET_CHECKEDINREPORTS_INFO+=" and eventdate=?";
		}

		//GET_CHECKEDINREPORTS_INFO+=" order by fn,ln";
		String orderby=checkInColumnMap.get(sortField);
		if(orderby==null) orderby=" fn,ln";
		if(sortDirection==null || "".equals(sortDirection)) sortDirection="asc";
			GET_CHECKEDINREPORTS_INFO+=" order by "+orderby+" "+sortDirection;
		DBManager dbmanager=new DBManager();
		StatusObj stobj=null;
		if(isrecurr){
			if(!"all".equals(reportsData.getDate())){
               scanDataMap=getScanStatusData(eid,reportsData.getDate());
               stobj=dbmanager.executeSelectQuery(GET_CHECKEDINREPORTS_INFO,new String[]{eid,reportsData.getDate()});
            }else{
				scanDataMap=getScanStatusData(eid,null);
				stobj=dbmanager.executeSelectQuery(GET_CHECKEDINREPORTS_INFO,new String[]{eid});
			}
		}else{
				scanDataMap=getScanStatusData(eid,null);
				stobj=dbmanager.executeSelectQuery(GET_CHECKEDINREPORTS_INFO,new String[]{eid});
			}
		
		 ArrayList<HashMap<String,String>> checkedinrepList=new ArrayList<HashMap<String,String>>();		 
			 if(stobj.getStatus()){
				for(int i=0;i<stobj.getCount();i++){
					boolean checkedin=false;
					
					HashMap<String,String> checkin_info=new HashMap<String,String>();
					String attKey=dbmanager.getValue(i,"profilekey","");
					if(scanDataMap.get(attKey)!=null){
						ScanData scanData=(ScanData)scanDataMap.get(attKey);
						checkin_info.put("CIS","Yes"  );
						checkin_info.put("CIT",scanData.getCheckedintime() );
						checkin_info.put("SID",scanData.getScanid() );
						
						checkedin=true;
					}else{
						checkin_info.put("CIS","No" );
						checkin_info.put("CIT","" );
						checkin_info.put("SID","" );
					}
					checkin_info.put("AK",attKey);
					checkin_info.put("FN",dbmanager.getValue(i,"dfname",""));
					checkin_info.put("LN",dbmanager.getValue(i,"dlname","") );
				    checkin_info.put("ON",dbmanager.getValue(i,"ordernumber",""));
				  	String trnsid=dbmanager.getValue(i,"transactionid","");

					String transactionid="<a href='TransactionDetails!getTransactionInfo?eid="+eid+"&tid="+trnsid+"'>View</a>";
					checkin_info.put("TID",trnsid);
					//checkin_info.put("view",transactionid);
					//checkin_info.put("Transaction ID",dbmanager.getValue(i,"transactionid","") );
					checkin_info.put("Em",dbmanager.getValue(i,"demail","") );
					checkin_info.put("TT",dbmanager.getValue(i,"tickettype","") );
					checkin_info.put("SC",dbmanager.getValue(i,"seatcode","") );
					checkin_info.put("ST", dbmanager.getValue(i,"paymentstatus",""));
					if(!export) checkedinrepList.add(checkin_info);
					else{
						if("1".equals(checkinfilter)){
							checkedinrepList.add(checkin_info);
						}else if("Yes".equals(checkinfilter)){
							if(checkedin) checkedinrepList.add(checkin_info);
						}else if("No".equals(checkinfilter)){
							if(!checkedin) checkedinrepList.add(checkin_info);
						}				
					}	
					
				}
			 }
			 return checkedinrepList;
	}
	public static void attendeeQuestionsFilter(ArrayList attQuestionFields, String eid, HashMap<String, String> attributeFilterMap){
		DbUtil.executeUpdateQuery("delete from report_custom_questions_filter " +
				"where eventid=CAST(? AS BIGINT) and reporttype='attendee'",new String [] {eid});
		for (int i = 0; i < attQuestionFields.size(); i++) {
		String attribid = (String) attQuestionFields.get(i);
		String question = attributeFilterMap.get(attribid);
		DbUtil.executeUpdateQuery("insert into report_custom_questions_filter(reporttype,eventid,question,attrib_id)" +
				" values('attendee',CAST(? AS BIGINT),?," +
				"CAST(? AS INTEGER)) ",new String [] {eid,question,attribid});

		}		
	}
	public static void buyerQuestionsFilter(ArrayList buyerQuestionFields, String eid, HashMap<String, String> attributeFilterMap){
		DbUtil.executeUpdateQuery("delete from report_custom_questions_filter " +
				"where eventid=CAST(? AS BIGINT) and reporttype='buyer'",new String [] {eid});
		for (int i = 0; i < buyerQuestionFields.size(); i++) {
		String attribid = (String) buyerQuestionFields.get(i);
		String question = attributeFilterMap.get(attribid);
		DbUtil.executeUpdateQuery("insert into report_custom_questions_filter(reporttype,eventid,question,attrib_id)" +
				" values('buyer',CAST(? AS BIGINT),?," +
				"CAST(? AS INTEGER)) ",new String [] {eid,question,attribid});

		}		
	}
	
	
	public static HashMap<String,String> getCustomAttributesMap(String eid){
		return  getCustomAttributesMap(eid,false);
	}
	
	
	public static HashMap<String,String> getCustomAttributesMap(String eid,boolean exporttype){
		DBManager dbmanager=null;
	    String readDsn=DsnProperty.get("db.dsn.reports","").trim();
	    if(!"".equals(readDsn))
	    	dbmanager=new DBManager(readDsn);
	    else
	    	dbmanager=new DBManager();
		//DBManager dbmanager=new DBManager(EventbeeConnection.readDsn);
		HashMap<String,String> customAttribsMap=new HashMap<String,String>();
		StatusObj statobj=null;
		HashMap hm=new HashMap();
		statobj=dbmanager.executeSelectQuery(CUSTOM_ATTRIBUTES_QUERY,new String []{eid});
				int count=statobj.getCount();
					if(statobj.getStatus()&&count>0){
					for(int k=0;k<count;k++){
						String attribval=dbmanager.getValue(k,"attribname","");
						
						if(!exporttype){
						//converting html to text as html tags are not closing while truncating...  on 15th April 2015
						attribval=JsoupHtml2Text.getPlainText(attribval);
						attribval=TruncateData(attribval,33);
						}		
						
						customAttribsMap.put(dbmanager.getValue(k,"attrib_id",""),attribval);
					}
			}
		
		return customAttribsMap;
	 }
	
	public static HashMap<String,String> getBuyerAttributesMap(String eid)
	{
		return getBuyerAttributesMap(eid, false);
	}
	
	public static HashMap<String,String> getBuyerAttributesMap(String eid, boolean exporttype){
		DBManager dbmanager=null;
	    String readDsn=DsnProperty.get("db.dsn.reports","").trim();
	    if(!"".equals(readDsn))
	    	dbmanager=new DBManager(readDsn);
	    else
	    	dbmanager=new DBManager();
		//DBManager dbmanager=new DBManager(EventbeeConnection.readDsn);
		HashMap<String,String> customAttribsMap=new HashMap<String,String>();
		StatusObj statobj=null;
		HashMap hm=new HashMap();
		statobj=dbmanager.executeSelectQuery(BUYER_ATTRIBUTES_QUERY,new String []{eid,eid});
				int count=statobj.getCount();
					if(statobj.getStatus()&&count>0){
					for(int k=0;k<count;k++){
						String attribute=dbmanager.getValue(k,"attribname","");
						if(!exporttype){
						//converting html to text as html tags are not closing while truncating...  on 15th April 2015
						attribute=JsoupHtml2Text.getPlainText(attribute);
						attribute=TruncateData(attribute,33);
						}
						customAttribsMap.put(dbmanager.getValue(k,"attrib_id",""),attribute);
					}
			}
		
		return customAttribsMap;
	 }
	
	public static ArrayList getSelectedAttendeeQuestions(String eid) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList selQuestion = new ArrayList();
		String QUERY = "select attrib_id from report_custom_questions_filter" +
				"  where eventid=CAST(? AS BIGINT) and reporttype='attendee'";
		statobj=dbmanager.executeSelectQuery(QUERY,new String []{eid});
		if(statobj.getStatus()){
			for (int i = 0; i < statobj.getCount(); i++) {				
				selQuestion.add(dbmanager.getValue(i,"attrib_id",""));
			}
		}
		return selQuestion;
	}	

	
	public static ArrayList getSelectedBuyerQuestions(String eid) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList selQuestion = new ArrayList();
		String QUERY = "select attrib_id from report_custom_questions_filter" +
				"  where eventid=CAST(? AS BIGINT) and reporttype='buyer'";
		statobj=dbmanager.executeSelectQuery(QUERY,new String []{eid});
		if(statobj.getStatus()){
			for (int i = 0; i < statobj.getCount(); i++) {				
				selQuestion.add(dbmanager.getValue(i,"attrib_id",""));
			}
		}
		
		return selQuestion;
	}	
	public static String attendeeAttribsCount(String eid){
		String count=DbUtil.getVal("select count(*) from report_custom_questions_filter " +
				"where eventid=CAST(? AS BIGINT) and reporttype='attendee'", new String []{eid});
		return count;
	}
	public static String buyerAttribsCount(String eid){
		String count=DbUtil.getVal("select count(*) from report_custom_questions_filter " +
				"where eventid=CAST(? AS BIGINT) and reporttype='buyer'", new String []{eid});
		return count;
	}
	public static String getCurrentTransctionDate(String eid,String tid){
		
		ReportsData reportsData=new ReportsData();
		String query="select eventdate from event_reg_transactions where eventid=? and tid=?";
		reportsData.setCurrentTransactionDate(DbUtil.getVal(query, new String[]{eid,tid}));
		String date=reportsData.getCurrentTransactionDate();
		//System.out.println("date in gettransdate"+date);
		return date;
		
	  
	}
	public static ArrayList getEventDates(String eid,String tid){
		DBManager db=new DBManager();
		ArrayList eventDatesList=new ArrayList();
		StatusObj statobj=null;
		String eventtype="";
		String rsvpeventtype=DbUtil.getVal("select 'y' from config where config_id=(select config_id from eventinfo"+
				" where eventid=CAST(? AS BIGINT)) and name='event.rsvp.enabled'", new String[]{eid});
		if(rsvpeventtype==null)rsvpeventtype="";
		if("".equals(rsvpeventtype))
			eventtype="Ticketing";
		else
			eventtype="RSVP";
		
		String ticketingquery= " select to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00')," +
	                  " 'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY HH12:MI AM') as evt_start_date " +
                      " from event_dates where eventid=CAST(? AS BIGINT) and " +
                      " to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00')," +
	                  " 'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY HH12:MI AM')" +
	                  " not in( select eventdate  from "+
                      " event_reg_transactions where eventid=? and tid=?) order by date_key ";
		
		
		String rsvpquery= " select date_display  from event_dates where eventid=CAST(? AS BIGINT) and "+
                      " date_display not in( select eventdate  from event_reg_transactions " +
                      " where eventid=? and tid=?) order by date_key ";
		if("RSVP".equals(eventtype))
		    statobj=db.executeSelectQuery(rsvpquery,new String[]{eid,eid,tid});
		else if("Ticketing".equals(eventtype))
			statobj=db.executeSelectQuery(ticketingquery,new String[]{eid,eid,tid});	
		//System.out.println("status in geteventdates"+statobj.getStatus());
		int count=statobj.getCount();
		if(statobj.getStatus() && count>0){
          for(int i=0;i<count;i++){
        	  if("RSVP".equals(eventtype))
        		  eventDatesList.add(new Entity(db.getValue(i,"date_display",""),db.getValue(i,"date_display","")));
        	  else if("Ticketing".equals(eventtype)) 
        		  eventDatesList.add(new Entity(db.getValue(i,"evt_start_date",""),db.getValue(i,"evt_start_date","")));
          }
		}
		
		return eventDatesList;
	}
	public static String getConfigValue(String eid) {
		String configValue=DbUtil.getVal("select value from config where name=? and config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[] {"event.seating.enabled",eid});
		return configValue;
	}
	
	public static String getPaymentApprovalType(String refid){
		String paymentType="";
		try{
			String query = "select attrib_4 from payment_types where refid=? and paytype='other'";
			paymentType=DbUtil.getVal(query, new String []{refid});
		}catch(Exception e){
			System.out.println("getPaymentApprovalType Error: "+e.getMessage());
		}
		return paymentType;
	}
	
	public static String getPendingApprovalCount(String eid){
		String query = "select count(*) from event_reg_transactions where eventid=? and paymentstatus='Need Approval'";
		String pendingApproval=DbUtil.getVal(query, new String[]{eid});
		return pendingApproval;
	}
	
	public static JSONObject getRecurringPendingApprovalCount(String eid){
		JSONObject js=new JSONObject();
		try{
			int recPendingAppSize=0,totalatt=0,totalchk=0;
			double totalsal=0;

			DBManager db=new DBManager();
			ArrayList pendingAppList=new ArrayList();    
			StatusObj statobj=null;
			String query = "select count(*),eventdate from event_reg_transactions where eventid=? and paymentstatus='Need Approval' and eventdate!='' and eventdate is not null group by eventdate";
			
			statobj=db.executeSelectQuery(query,new String[]{eid});
			int count=statobj.getCount();
			if(statobj.getStatus() && count>0)
	          for(int i=0;i<count;i++){
	        	  js.put(db.getValue(i,"eventdate","")+"_needapcnt",db.getValue(i,"count","0"));
	        	  recPendingAppSize+=Integer.parseInt(db.getValue(i,"count","0"));
	          }
			String attndQry="select sum(a.ticketqty) as  atndcnt,b.eventdate from transaction_tickets as a, event_reg_transactions b where b.eventid=? and b.eventdate!='' and a.eventid=b.eventid and a.tid=b.tid and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') ) group by b.eventdate";
			statobj=db.executeSelectQuery(attndQry,new String[]{eid});
			if(statobj.getStatus() && statobj.getCount()>0)
	          for(int i=0;i<statobj.getCount();i++){
	        	  js.put(db.getValue(i,"eventdate","")+"_atndtotal",db.getValue(i,"atndcnt","0"));
	        	  totalatt+=Integer.parseInt(db.getValue(i,"atndcnt","0"));
	          }
			String salesQry="select sum(current_amount) as salesamt,eventdate from event_reg_transactions where eventid=? and eventdate!='' and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') ) group by eventdate";
			statobj=db.executeSelectQuery(salesQry,new String[]{eid});
			if(statobj.getStatus() && statobj.getCount()>0)
	          for(int i=0;i<statobj.getCount();i++){
	        	  js.put(db.getValue(i,"eventdate","")+"_salestotal",db.getValue(i,"salesamt","0"));
	        	  totalsal+=Double.parseDouble(db.getValue(i,"salesamt","0"));
	          }
			String chkinQry="select count(*) as  chkcnt,b.eventdate from event_scan_status as a, event_reg_transactions b where b.eventid=? and b.eventdate!='' and a.eventid=b.eventid and a.tid=b.tid and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') ) group by b.eventdate";
			statobj=db.executeSelectQuery(chkinQry,new String[]{eid});
			if(statobj.getStatus() && statobj.getCount()>0)
	          for(int i=0;i<statobj.getCount();i++){
	        	  js.put(db.getValue(i,"eventdate","")+"_chkcnt",db.getValue(i,"chkcnt","0"));
	        	  totalchk+=Integer.parseInt(db.getValue(i,"chkcnt","0"));
	          }

			
			js.put("recPendingAppSize",recPendingAppSize);
			js.put("all_needapcnt",recPendingAppSize);
			js.put("totalAttendees",totalatt);
			js.put("totalSales",totalsal);
			js.put("totalCheckIns",totalchk);
		
		}catch(Exception e){
			System.out.println("Exception in ReportsDB getRecurringPendingApprovalCount: "+e.getMessage());
		}
		return js;
	}
	
	public static String isVolumeSale(String eid){
		String exist=DbUtil.getVal("select 'yes' from event_reg_transactions where eventid=? and bookingdomain like '%VBEE%'", new String[]{eid});
		if("yes".equals(exist)) exist="Y";
		else exist="N";
		return exist;   
	}
	public static void insertIntoCustomQuestions(HashMap hm,ArrayList sellist,String eid,String type)
	{
		DbUtil.executeUpdateQuery("delete from report_custom_questions_filter " +
				"where eventid=CAST(? AS BIGINT) and reporttype=?",new String [] {eid,type});
		for(int i=0;i<sellist.size();i++){
		String key=(String)sellist.get(i);	
		String value=(String)hm.get(key);
		
	      DbUtil.executeUpdateQuery("insert into report_custom_questions_filter(reporttype,eventid,question,attrib_id)" +
					" values(?,CAST(? AS BIGINT),?," +
					"CAST(? AS INTEGER)) ",new String [] {type,eid,value,key});	
		}
	}
	
	
	public static String checkOtherCCPFee(String eid){
		String otherccpfee=DbUtil.getVal("select other_ccfee from payment_types where refid=? and other_ccfee is not null and other_ccfee!=''",new String[]{eid} );
		return otherccpfee;
	}
	
	public static HashMap getOtherCCPFees(String eid){
		HashMap<String, String> hm=null;
		String query = "select name,value from mgr_config where name in('paypal.actual.ccfee','google.actual.ccfee') and userid=(select mgr_id from eventinfo where eventid=CAST(? AS BIGINT))";
		DBManager db=new DBManager();
		StatusObj statobj=null;
		statobj=db.executeSelectQuery(query,new String[]{eid});
		int count=statobj.getCount();
		if(statobj.getStatus() && count>0){
			hm=new HashMap<String, String>();
			for(int i=0;i<count;i++){
				
				String name = db.getValue(i,"name","");
				String otherccfee = db.getValue(i,"value","");
				Double ccfeepercent=0.0; Double ccfeeadd=0.0;
        	  try{
        		  		String otherccfeearr[]=otherccfee.split("\\+");
        		  		if(otherccfeearr.length==2){
        		  			ccfeepercent=Double.parseDouble(otherccfeearr[0]);
        		  			ccfeeadd=Double.parseDouble(otherccfeearr[1]);
    				   }
				    
				}catch(Exception e){
					System.out.println("Ohter cc processing fee exeception ERROR:: "+e.getMessage());
					ccfeepercent=0.0;
					ccfeeadd=0.0;
				}
				if("paypal.actual.ccfee".equals(name)){
					hm.put("paypalpercent",ccfeepercent.toString());
					hm.put("paypalccadd",ccfeeadd.toString());
				}else {
					hm.put("googlepercent",ccfeepercent.toString());
					hm.put("googleccadd",ccfeeadd.toString());
				}
			}
		}
		return hm;
	}
	
	public static String getTotalSales(String eventid){
		String total=DbUtil.getVal("select sum(current_amount) from event_reg_transactions where eventid=? and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') ) ", new String[]{eventid});
		if(total==null) total="0.00";
		return total;
	}
	
	public static String getTotalAttendees(String eventid){
		String qry="select sum(a.ticketqty) from transaction_tickets a, event_reg_transactions b where b.eventid=? and a.eventid=b.eventid and a.tid=b.tid and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') )";
		String total=DbUtil.getVal(qry, new String[]{eventid});
		if(total==null) total="0";
		return total;
	}
	
	public static String getTotalCheckIns(String eventid){
		String qry="select count(*) from event_scan_status a, event_reg_transactions b where b.eventid=? and a.eventid=b.eventid and a.tid=b.tid and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') )";
		String total=DbUtil.getVal(qry, new String[]{eventid});
		if(total==null) total="0";
		return total;
	}

}
