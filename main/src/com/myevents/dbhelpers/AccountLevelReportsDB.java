package com.myevents.dbhelpers;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myevents.beans.AccountLevelReportsData;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;
import com.eventbee.payments.dbhelpers.CardProcessorDB;
import com.event.beans.ScanData;



public class AccountLevelReportsDB {
	AccountLevelReportsData reportsData=new AccountLevelReportsData();
	public static final HashMap<String,String> attColumnMap=new HashMap<String, String>();
	public static final HashMap<String,String> tranColumnMap=new HashMap<String, String>();
	static String GET_SCAN_DATA="select   tid ,  attendeekey,  checkinstatus,  to_char(checkedintime,'mm/dd/yyyy HH:MI AM') as checkedintime,  checkedby ,  scanid,  " +
	" syncedyn,  syncedby,  syncedat,  syncuser from event_scan_status where eventid=?";
static String GET_SCAN_DATA_RECURRING="select   tid ,  attendeekey,  checkinstatus,  to_char(checkedintime,'mm/dd/yyyy HH:MI AM') as checkedintime,  checkedby ,  scanid,  " +
" syncedyn,  syncedby,  syncedat,  syncuser from event_scan_status where eventid=? " +
" and tid in (select tid from event_reg_transactions where eventdate=? and eventid=?)";

static String CUSTOM_ATTRIBUTES_QUERY="select attrib_id,attribname from custom_attribs where " +
"attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) " +
"and purpose='EVENT') order by position";

static String BUYER_ATTRIBUTES_QUERY="select attrib_id,attribname from custom_attribs where " +
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
						String attribval=TruncateData(dbmanager.getValue(k,"attribname",""),33);
						attribs_list.add(new Entity(dbmanager.getValue(k,"attrib_id",""),attribval));
					}
			}
		return attribs_list;
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
		String query ="SELECT distinct ticketname as tname, ticketid as tktid from transaction_tickets where eventid=?";
		StatusObj sb=dbmanager.executeSelectQuery(query,new String[]{eid});
		if(sb.getStatus()){
			for(int k=0;k<sb.getCount();k++){
			String ticketid=dbmanager.getValue(k,"tktid","");
			String ticketname=dbmanager.getValue(k,"tname","");
			if(groupnames.containsKey(ticketid)){
				ticketname=ticketname+"("+groupnames.get(ticketid)+")";
			}
		ticketsList.add(new Entity(ticketid,ticketname));
			}		
		}
		return ticketsList;
	}
	public static ArrayList getBookingSourceDetails(String mgrId){
		DBManager dbmanager=new DBManager();
		ArrayList sourceList=new ArrayList();
	
		sourceList.add(new Entity("direct","Main URL"));
		//sourceList.add(new Entity("nts","NTS"));
		sourceList.add(new Entity("alltrackcodes","All Tracking URLs"));
		sourceList.add(new Entity("nts","All NTS Partners"));
		try{
		StatusObj sb=null;
		String trackpartnerqry="select distinct trackingcode  from trackurls where eventid in(select eventid::text  from eventinfo where mgr_id=CAST(? as INTEGER) and status!='CANCEL' and eventid is not null and eventid::text!='' and eventid::text!='null')";
		if(!"".equals(mgrId) && !"null".equals(mgrId) && mgrId!=null)
		sb=dbmanager.executeSelectQuery(trackpartnerqry,new String[]{mgrId});
		if(sb.getStatus()){
			for(int k=0;k<sb.getCount();k++){	
				sourceList.add(new Entity(dbmanager.getValue(k,"trackingcode",""),"Track URL: "+dbmanager.getValue(k,"trackingcode","")));
			}		
		}
		sourceList.add(new Entity("fbapp","FB App"));
		}catch(Exception e){
			System.out.println("Exception occured in getBookingSourceDetails");
		}
		return sourceList;
	}
	public static ArrayList getRecurringEvents(String eid){
		DBManager dbmanager=new DBManager();
		ArrayList eventsList=new ArrayList();
		String Query="select distinct eventdate from event_reg_transactions  "+
				"where eventid=? and eventdate!='' and eventdate is not null";
	    StatusObj sb=dbmanager.executeSelectQuery(Query,new String[]{eid});
		if(sb.getStatus()){
			for(int k=0;k<sb.getCount();k++){	
				eventsList.add(new Entity(dbmanager.getValue(k,"eventdate",""),dbmanager.getValue(k,"eventdate","")));
			}		
		}
		return eventsList;
	}
	
	
	public static ArrayList getRecurringEvents(String eid,String trackcode){
		
		DBManager dbmanager=new DBManager();
		ArrayList eventsList=new ArrayList();
		String Query="select distinct eventdate from event_reg_transactions  "+
				"where eventid=? and eventdate!='' and trackpartner=? ";
		StatusObj sb=dbmanager.executeSelectQuery(Query,new String[]{eid,trackcode});
		if(sb.getStatus()){
			for(int k=0;k<sb.getCount();k++){	
				eventsList.add(new Entity(dbmanager.getValue(k,"eventdate",""),dbmanager.getValue(k,"eventdate","")));
			}		
		}
		return eventsList;
		
	}
	
	
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
	//attColumnMap.put("EN","Event Name");
	
}
public static ArrayList<HashMap<String,String>> getAttendeeListInfo(String events,String selindex,AccountLevelReportsData reportsData,
	String typeIndex,String sortField,String sortDirection,HashMap<String,String> currencymap){
	DBManager dbmanager=new DBManager();
	StatusObj stobj=null;
   //groupname is added in the below query on 14th feb 2012
	ArrayList<HashMap<String,String>> attendeeList=new ArrayList<HashMap<String,String>>();
	HashMap<String,String> eventnamesmap=new HashMap<String,String>();
	try{
	if(events!=null && !"".equals(events) && !"null".equals(events))
	stobj=dbmanager.executeSelectQuery("select eventid,eventname from eventinfo where eventid in("+events+")", null);
	
	if(stobj.getStatus() && stobj.getCount()>0){
		for(int i=0;i<stobj.getCount();i++){
			eventnamesmap.put(dbmanager.getValue(i,"eventid", ""), dbmanager.getValue(i,"eventname", ""));
		}
	}
	}catch(Exception e){System.out.println("Exception occured for prepare eventnamesmap");}
	
	try{
	String GET_ATTENDEELIST_INFO="select distinct a.fname as dfname,c.eventid," +
			"a.lname as dlname,lower(a.email) as demail,a.ticketid,ticketname,groupname,ticketprice,a.phone,a.seatcode,to_char(transaction_date,'yyyy/mm/dd') as trdate,transaction_date," +
			"c.paymenttype,d.profilekey as buyerkey, a.profilekey as attendeekey,a.profileid as attendeeid,a.transactionid,ordernumber," +
			"bookingsource,current_amount,lower(c.email) as buyeremail,c.phone as buyerphone," +
			"c.fname || ' ' || c.lname  as buyername,to_char(a.checkintime,'mm/dd/yyyy HH:MI AM') as checkintime,a.scanid as scanid " +
			"from profile_base_info a, transaction_tickets b," +
			" event_reg_transactions c, buyer_base_info d " +
			"where a.ticketid||'_'||a.eventid=b.ticketid||'_'||b.eventid  " +
			"and b.tid=c.tid " +
			"and CAST(c.eventid AS BIGINT) in("+events+")"+
			"and (UPPER(paymentstatus) in ('COMPLETED','CHARGED','PENDING') ) " +
			"and a.transactionid=b.tid " +
			"and c.tid=d.transactionid";
				
	if("2".equals(selindex)){
		GET_ATTENDEELIST_INFO+=" and bookingsource='online' ";
	}else if("3".equals(selindex)){
		GET_ATTENDEELIST_INFO+=" and bookingsource='Manager' ";
	}
	if("2".equals(reportsData.getAttdatetype())){
		String[] startdatearr=GenUtil.strToArrayStr(reportsData.getEventAttStartDate(), "/");
		String startdate=startdatearr[2]+"-"+startdatearr[0]+"-"+startdatearr[1];
		String[] enddatearr=GenUtil.strToArrayStr(reportsData.getEventAttEndDate(), "/");
		String enddate=enddatearr[2]+"-"+enddatearr[0]+"-"+enddatearr[1];
		GET_ATTENDEELIST_INFO+=" and to_char(c.transaction_date,'yyyy-mm-dd') between '"+startdate+"' and '"+enddate+"'"; 
	}
	if("2".equals(typeIndex)){	
		GET_ATTENDEELIST_INFO+=" and a.tickettype='attendeeType' ";
	}
	else if("3".equals(typeIndex)){	
		GET_ATTENDEELIST_INFO+=" and  b.ticketid="+reportsData.getTicket();
	}
	String orderby=attColumnMap.get(sortField);
	if(orderby==null) orderby="attendeekey";
	if(sortDirection==null || "".equals(sortDirection)) sortDirection="desc";
		GET_ATTENDEELIST_INFO+=" order by "+orderby+" "+sortDirection;
	
long starttime=System.currentTimeMillis();

long totaltime=(System.currentTimeMillis())-starttime;

long forloopstarttime=System.currentTimeMillis();
		
         if(events!=null && !"".equals(events) && !"null".equals(events))
		 stobj=dbmanager.executeSelectQuery(GET_ATTENDEELIST_INFO,null);
         
		 if(stobj.getStatus() && stobj.getCount()>0){
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
					String eid=dbmanager.getValue(i,"eventid","");
					attinfo.put("EID",eid);
					String evtname=eventnamesmap.get(eid);
					String transactionid="<a href='/main/eventmanage/TransactionDetails!getTransactionInfo?eid="+eid+"&tid="+trnsid+"'>View</a>";
					attinfo.put("TID",trnsid);
					attinfo.put("AK",dbmanager.getValue(i,"attendeekey","") );
					attinfo.put("SC",dbmanager.getValue(i,"seatcode","") );
					attinfo.put("EN",evtname);
					HashMap buyerattribhm=new HashMap(); 
					HashMap attribhm=new HashMap();
					
					String Ticketname=dbmanager.getValue(i,"ticketname","");
					String ticketPrice=dbmanager.getValue(i,"ticketprice","0.00");
					String ticketid=dbmanager.getValue(i,"ticketid","");
					String currencysymbol="";
					if(currencymap.size()>0)
						currencysymbol=currencymap.get(eid);
					if(currencysymbol==null || "".equals(currencysymbol))currencysymbol="$";
					String groupName=dbmanager.getValue(i,"groupname","");
					if(!groupName.equals("") && groupName != null)
					Ticketname=Ticketname+" ("+groupName+")";
					attinfo.put("TN",Ticketname);
					attinfo.put("TP",currencysymbol+ticketPrice);
					attendeeList.add(attinfo);
				}
			 
				//System.out.println("Attendee_Reports_eid: "+eid+" UUID:  getAttendeeListInfo(ReportsDB.java) ended, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
}
			 long forlooptotaltime=(System.currentTimeMillis())-forloopstarttime;
			 //System.out.println("Attendee_Reports_eid: "+eid+" UUID: Total time taken to execute ** for loop to get attendeeList **: "+forlooptotaltime+" MS");
	}catch(Exception e){
		System.out.println("Exception occured in getAttendeeListInfo");
	}			 
	return attendeeList;
}

	public static final String GET_TICKETS_INFO="select ticketqty,ticketname,ticketid,tid,groupname,ticketprice,(discount/ticketqty) as ticketdiscount,fee as ticketfee from transaction_tickets where eventid=? order by ticketid";
	public static final String GET_NOTES_INFO="select tid,notes from transaction_notes where tid in (select tid from event_reg_transactions where eventid=? order by tid)"; //Rahul
	public static HashMap getTransactionNotesInfo(String events){
		HashMap notesMap=new HashMap();
		DBManager dbmanager=new DBManager();
		String query="select tid,notes from transaction_notes where tid in (select tid from event_reg_transactions where cast(eventid as integer) in ("+events+")order by tid)";
		StatusObj statobj=dbmanager.executeSelectQuery(query,null);
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
				notesMap.put(dbmanager.getValue(i,"tid", null), dbmanager.getValue(i, "notes",""));
			}
		}
		return notesMap;
	}
	public static HashMap getTicketInfo(String events,HashMap<String,String> currencymap){
		HashMap hmap=new HashMap();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		HashMap ticketHistory = null;
		try{
		String query="select ticketqty,ticketname,ticketid,tid,groupname,ticketprice,case when ticketqty>0 then (discount/ticketqty) else 0 end as ticketdiscount,fee as ticketfee,eventid  from transaction_tickets where cast(eventid as integer) in ("+events+") order by ticketid";
		if(events!=null && !"".equals(events) && !"null".equals(events))
		statobj=dbmanager.executeSelectQuery(query,null);
		if(statobj.getStatus() && statobj.getCount()>0){
			for(int i=0;i<statobj.getCount();i++){
				String tid =dbmanager.getValue(i,"tid",null);
				String ticketqty=dbmanager.getValue(i,"ticketqty","0");
				String ticketprice=dbmanager.getValue(i,"ticketprice","0.00");
				String ticketname=dbmanager.getValue(i,"ticketname","");
				String ticketid=dbmanager.getValue(i,"ticketid","");
				String groupName=dbmanager.getValue(i,"groupname","");
				String eventid=dbmanager.getValue(i,"eventid","");
				String currencysymbol="";
				if(currencymap.size()>0)
				currencysymbol=currencymap.get(eventid);
				if(currencysymbol==null || "".equals(currencysymbol))currencysymbol="$";
				String ticketdiscount=CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i,"ticketdiscount","0.00"),true);
				String ticketfee=dbmanager.getValue(i,"ticketfee","0.00");
				ticketfee=CurrencyFormat.getCurrencyFormat("",ticketfee,true);
				double price=0;
				price = Double.parseDouble(ticketprice) - Double.parseDouble(ticketfee);
				ticketprice=String.valueOf(price);
				ticketprice=CurrencyFormat.getCurrencyFormat("",ticketprice,true);
				if(!"".equals(groupName) && groupName!=null)
			    //ticketname=groupName+" - "+ticketname;
				ticketname=ticketname+"("+groupName+")";
				else
				ticketname=ticketname;
				if(hmap.get(tid)!=null){
					ticketHistory = (HashMap)hmap.get(tid);
					ticketname=ticketHistory.get("DESC")+", "+ticketname;
					ticketqty=ticketHistory.get("Count")+", "+ticketqty;
					ticketprice=ticketHistory.get("Price")+", "+currencysymbol+ticketprice;
					ticketid=ticketHistory.get("TicketId")+", "+ticketid;
					ticketdiscount=ticketHistory.get("Discount")+", "+currencysymbol+ticketdiscount;
					ticketfee=ticketHistory.get("Fee")+", "+currencysymbol+ticketfee;
				 }else{
				  	ticketHistory = new HashMap();
				  	ticketprice=currencysymbol+ticketprice;
				  	ticketdiscount=currencysymbol+ticketdiscount;
				  	ticketfee=currencysymbol+ticketfee;
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
		}catch(Exception e){
			System.out.println("Exception occured in getTicketInfo for transaction report");
		}
		return hmap;
	}
	private static StatusObj executeQuery(String baseQuery,String date, 
			String typeIndex, String tckid,DBManager dbmanager, String[] params,String sortField,String sortDirection,String events){
		System.out.println("executeQuery(ReportsDB.java) started ,free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		StatusObj statobj=null;
		String finalQuerypart=baseQuery;
		String[] newParams;
		
		newParams=new String[params.length];
		for(int i=0;i<params.length;i++){
			newParams[i]=params[i];
		}
		
			if(!"".equals(tckid)){
				finalQuerypart+=" and tid in (select tid from transaction_tickets where ticketid="+tckid+")";				
			}
			String orderby=tranColumnMap.get(sortField);
			if(orderby==null) orderby="trdate";
			if(sortDirection==null || "".equals(sortDirection)) sortDirection="desc";
			finalQuerypart+=" order by "+orderby+" "+sortDirection;
			System.out.println(finalQuerypart);
			System.out.println("executeSelectQuery(ReportsDB.java) calling executeSelectQuery");
			if(events!=null && !"".equals(events) && !"null".equals(events))
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
		//tranColumnMap.put("EN", "eventname");
		
	}
	
	private static ArrayList<HashMap<String,String>> getTransactionDetailsList(StatusObj statobj, String events,
			HashMap ticketsinfo,HashMap notesinfo,DBManager dbmanager,ArrayList Fields,HashMap<String,String> currencymap){
		System.out.println("Transaction_Reports_UUID: getTransactionDetailsList(ReportsDB.java) started, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		ArrayList<HashMap<String,String>> transactionDetailsList=new ArrayList<HashMap<String,String>>();
		DBManager dbm=new DBManager();
		StatusObj sobj=null;
		long starttime=System.currentTimeMillis();
		HashMap attribhm=null;
		HashMap<String,String> eventnamesmap=new HashMap<String,String>();
		try{
		if(events!=null && !"".equals(events) && !"null".equals(events))
		sobj=dbm.executeSelectQuery("select eventid,eventname from eventinfo where eventid in("+events+")", null);
		if(sobj.getStatus() && sobj.getCount()>0){
			for(int i=0;i<sobj.getCount();i++){
				eventnamesmap.put(dbm.getValue(i,"eventid", ""), dbm.getValue(i,"eventname", ""));
			}
		}}catch(Exception e){
			System.out.println("Exception occured for getting eventnamesmap in transaction reports of accout level reports");
		}
		 try {
			if(statobj.getStatus()){
				for(int i=0;i<statobj.getCount();i++){
						HashMap<String,String> traninfo=new HashMap<String,String>();
						String trnsid=dbmanager.getValue(i,"tid","");
						String eid=dbmanager.getValue(i,"eventid","");
						String eventname=eventnamesmap.get(eid);
						String transactionid="<a href='/main/eventmanage/TransactionDetails!getTransactionInfo?eid="+eid+"&tid="+trnsid+"'>View</a>";
						String currencysymbol="";
						if(currencymap.size()>0)
						currencysymbol=currencymap.get(eid);
						if(currencysymbol==null || "".equals(currencysymbol))
							currencysymbol="$";
						String paymenttype=dbmanager.getValue(i,"paymenttype","");
						String ccvendor=dbmanager.getValue(i,"cc_vendor","");
						String collectedby=dbmanager.getValue(i,"collected_by","");
						traninfo.put("TID",trnsid);
						traninfo.put("TD",dbmanager.getValue(i,"trdate",""));
						traninfo.put("FN",dbmanager.getValue(i,"dfname",""));
						traninfo.put("LN",dbmanager.getValue(i,"dlname",""));
						traninfo.put("EN",eventname);
						traninfo.put("Ph",dbmanager.getValue(i,"phone",""));
						traninfo.put("TTC",currencysymbol+dbmanager.getValue(i,"current_amount","0"));
						traninfo.put("PID",dbmanager.getValue(i,"partnerid",""));
						traninfo.put("CCPF",currencysymbol+dbmanager.getValue(i,"current_tax","0"));
						double ebeefee=0.0;
						ebeefee=Double.parseDouble(dbmanager.getValue(i,"servicefee","0"));
						traninfo.put("ESF", currencysymbol+CurrencyFormat.getCurrencyFormat("",ebeefee+"",true));
						double cardfee=0.0;
						cardfee=Double.parseDouble(dbmanager.getValue(i,"ccfee","0.00"));
					    //traninfo.put("ECCF", currencysymbol+CurrencyFormat.getCurrencyFormat("",cardfee+"",true));
						double collectedservfee=0.0;
						collectedservfee=Double.parseDouble(dbmanager.getValue(i,"collected_servicefee","0.00"));
						String csf=CurrencyFormat.getCurrencyFormat("",collectedservfee+"",true);
						if(collectedservfee > 0 && !"".equals(collectedby)){
							if("paypalx".equals(collectedby)) collectedby="paypal";
							traninfo.put("CSF", currencysymbol+csf+" ("+collectedby+")");
						}else traninfo.put("CSF", currencysymbol+csf);
						double ntscommission = Double.parseDouble(dbmanager.getValue(i,"current_nts","0"));
						traninfo.put("NTSC",currencysymbol+CurrencyFormat.getCurrencyFormat("",ntscommission+"",true));
						double totalnet=0;
						double grandtotal1=0;
						grandtotal1=Double.parseDouble(dbmanager.getValue(i,"current_amount","0"));
						
						double vtscommission=0.0;//volume ticket selling commission
						String bookingdomain = dbmanager.getValue(i,"bookingdomain","");
						if(bookingdomain.contains("VBEE"))
							vtscommission=(grandtotal1*10)/100;
						double ebeeccfee=0;double otherccfee=0;
						if("eventbee".equalsIgnoreCase(paymenttype)){
							ebeeccfee=cardfee;
						}else if(Fields.contains("OCCF")){
							otherccfee=cardfee;
						}
						traninfo.put("ECCF", CurrencyFormat.getCurrencyFormat("",ebeeccfee+"",true));
						traninfo.put("OCCF",CurrencyFormat.getCurrencyFormat("",otherccfee+"",true));
						//totalnet=grandtotal1-ebeefee-cardfee-ntscommission-vtscommission-otherccfee;
						totalnet=grandtotal1-ebeefee-ebeeccfee-otherccfee-ntscommission;
						
						traninfo.put("VTSC",currencysymbol+CurrencyFormat.getCurrencyFormat("",vtscommission+"",true));
						traninfo.put("TNet",currencysymbol+CurrencyFormat.getCurrencyFormat("",totalnet+"",true));
						traninfo.put("Bal",CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i,"amount_we_have","0")+"",true));
						//traninfo.put("Net",CurrencyFormat.getCurrencyFormat("",net+"",true));
						//traninfo.put("CSF",CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i,"collected_servicefee","0.00"),true));
						traninfo.put("Due",currencysymbol+CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i,"due","0.00"),true));
						traninfo.put("EPID",dbmanager.getValue(i,"ext_pay_id",""));
						String agentid=dbmanager.getValue(i,"partnerid","null");
						/*String discount=dbmanager.getValue(i,"current_discount","");
						if(discount==null) discount="0";
						traninfo.put("Di",discount);*/
						
						if("authorize.net".equalsIgnoreCase(paymenttype)) paymenttype="Authorize.Net";
						else if("braintree".equalsIgnoreCase(paymenttype)) paymenttype="Braintree";
						else if("stripe".equalsIgnoreCase(paymenttype)) paymenttype="Stripe";
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
						if("FBApp".equalsIgnoreCase(bookingdomain))
						traninfo.put("So","FB App");
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
						String TicketPrice="";String TicketDiscount="";String TicketFee="";
						HashMap TIDTicketHistory = (HashMap)ticketsinfo.get(dbmanager.getValue(i,"tid",""));
						if(TIDTicketHistory!=null){			
							TicketCount=GenUtil.getHMvalue(TIDTicketHistory,"Count");
							Ticketname=GenUtil.getHMvalue(TIDTicketHistory,"DESC");
							TicketPrice=GenUtil.getHMvalue(TIDTicketHistory,"Price");
							TicketDiscount=GenUtil.getHMvalue(TIDTicketHistory,"Discount");
							TicketFee=GenUtil.getHMvalue(TIDTicketHistory,"Fee");
							traninfo.put("TC",TicketCount);
							traninfo.put("TN",Ticketname);
							traninfo.put("TP",TicketPrice);
							traninfo.put("Di",TicketDiscount);
							traninfo.put("SF",TicketFee);
							
						}
						traninfo.put("EID",eid);
					 	transactionDetailsList.add(traninfo);
					}
				
			 }
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
		}
		//System.out.println("Transaction_Reports_UUID: eid: "+eid+": getTransactionDetailsList(ReportsDB.java) ended, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long totaltime=(System.currentTimeMillis())-starttime;
		//System.out.println("Transaction_Reports_UUID: eid: "+eid+": Total time taken to get  getTransactionDetailsList: "+totaltime+" MS");
		 return transactionDetailsList;
	}
	
	public static final String  commonselectpart="select distinct partnerid,lower(email) as demail,discountcode,e.eventid," +
	"trackpartner,tid,fname as dfname,lname as dlname,paymenttype ,fname || ' ' || lname  as name," +
	"upper(fname), upper(lname),transaction_date," +
	"to_char(transaction_date,'yyyy/mm/dd') as trdate,ordernumber,phone,current_nts,partnerid,current_amount," +
	"servicefee,collected_servicefee,(servicefee-collected_servicefee) as due,ccfee,current_discount,paymentstatus," +
	"ext_pay_id,bookingdomain,current_tax,collected_by,amount_we_have,cc_vendor from event_reg_transactions e";

public static  String ALL_TRANSACTION_DETAILS=" and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') )  ";

//public  static String TRANSACTION_DETAILS_BEETWEEN_DATES=" and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') )  and to_char(transaction_date,'yyyy-mm-dd') between ? and ? ";

public  static String TRANSACTION_DETAILS_BEETWEEN_DATES=" and to_char(transaction_date,'yyyy-mm-dd') between ? and ? and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') )";

public static  String TRANSACTION_DETAILS_TRANSACTIONID=" and  tid=? ";

public static  String TRANSACTION_DETAILS_ATTENDEENAME=" and lower(fname||' '||lname) like lower(?) and (UPPER(paymentstatus) in ('COMPLETED','CHARGED','PENDING') )";

public static  String TRANSACTION_DETAILS_PAYMENTSTATUS=" and  upper(paymentstatus)=upper(?)";

public static  String TRANSACTION_DETAILS_ORDERNUMBER=" and ordernumber=? ";

public  static String TRANSACTION_DETAILS_TRACKCODE=" and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') ) and  trackpartner=? ";

public static  String TRANSACTION_DETAILS_DIRECT=" and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') )  and  (trackpartner is null or trackpartner='' or trackpartner='null') and (partnerid is null  or partnerid='null' or partnerid='') and ( bookingdomain is null or bookingdomain='null' or bookingdomain='' or upper(bookingdomain)!=upper('FBApp'))";

public static  String TRANSACTION_DETAILS_NTS=" and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') )  and partnerid is not null and partnerid <>'null' and partnerid!='' ";

public static  String TRANSACTION_DETAILS_ALLTRACKCODES=" and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') )  and trackpartner is not null and trackpartner <>'null' and trackpartner<>'' ";

public static  String TRANSACTION_DETAILS_FBAPP=" and (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') ) and upper(bookingdomain)=upper(?)";

public static  String TRANSACTION_DETAILS_RECURRING_EVENT=" and eventdate=? ";
	public static ArrayList<HashMap<String,String>> getTransactionInfo(String events,String selectedvalue,HashMap ticketsinfo,HashMap notesinfo,AccountLevelReportsData reportsData,String cardtype,String typeIndex,String sortField,
			String sortDirection,ArrayList Fields,HashMap<String,String> currencymap){
		//System.out.println("Transaction_Reports_eid: "+eid+" UUID:  getTransactionInfo(ReportsDB.java) started, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long starttime=System.currentTimeMillis();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String date=reportsData.getDate();
		String finalQuery=commonselectpart+" where cast(e.eventid as integer) in("+events+")";
		String[] inparams=new String[]{};
		
		if("Y".equals(reportsData.getIsVolumeSale())){
			if("".equals(reportsData.getVolumeTickets()) && "".equals(reportsData.getOnline()) && "on".equals(reportsData.getManual())){
				finalQuery+=" and bookingsource='Manager'";
			}else if("".equals(reportsData.getVolumeTickets()) && "on".equals(reportsData.getOnline()) && "".equals(reportsData.getManual())){
				finalQuery+=" and bookingdomain not like '%VBEE%' and bookingsource='online'";
			}else if("".equals(reportsData.getVolumeTickets()) && "on".equals(reportsData.getOnline()) && "on".equals(reportsData.getManual())){
				finalQuery+=" and bookingdomain not like '%VBEE%'";
			}else if("on".equals(reportsData.getVolumeTickets()) && "".equals(reportsData.getOnline()) && "".equals(reportsData.getManual())){
				finalQuery+=" and bookingdomain like '%VBEE%'";
			}else if("on".equals(reportsData.getVolumeTickets()) && "on".equals(reportsData.getOnline()) && "".equals(reportsData.getManual())){
				finalQuery+=" and bookingsource='online'";
			}else if("on".equals(reportsData.getVolumeTickets()) && "".equals(reportsData.getOnline()) && "on".equals(reportsData.getManual())){
				finalQuery+=" and (bookingdomain like '%VBEE%' or bookingsource='Manager')";
			}
		}else{
			if(reportsData.getOnline().equals("on") && reportsData.getManual().equals("")){
				finalQuery+=" and bookingsource='online'";
			}else if(reportsData.getOnline().equals("") && reportsData.getManual().equals("on")){
				finalQuery+=" and bookingsource='Manager'";
			}
		}
		
		if(!(cardtype.equals("all"))){
			finalQuery+="and paymenttype=?";
			inparams=addParam(inparams, cardtype);
		}
		
		if("2".equals(selectedvalue)){	
			String[] startdatearr=GenUtil.strToArrayStr(reportsData.getEventTranStartDate(), "/");
			String startdate=startdatearr[2]+"-"+startdatearr[0]+"-"+startdatearr[1];
			String[] enddatearr=GenUtil.strToArrayStr(reportsData.getEventTranEndDate(), "/");
			String enddate=enddatearr[2]+"-"+enddatearr[0]+"-"+enddatearr[1];
			finalQuery+=TRANSACTION_DETAILS_BEETWEEN_DATES;
			inparams=addParam(inparams, startdate);
			inparams=addParam(inparams, enddate);
			statobj=executeQuery(finalQuery,date,typeIndex,reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,events);			
		}else if("3".equals(selectedvalue)){
			String transactionid=reportsData.getTransactionID();
			finalQuery+=TRANSACTION_DETAILS_TRANSACTIONID;
			inparams=addParam(inparams, transactionid);
			statobj=executeQuery(finalQuery,date,typeIndex,reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,events);
		}
		else if("4".equals(selectedvalue)){
			String attendee=reportsData.getAttendeeName().trim();
			finalQuery+=TRANSACTION_DETAILS_ATTENDEENAME;
			inparams=addParam(inparams,"%"+attendee+"%");
			statobj=executeQuery(finalQuery,date,typeIndex,reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,events);			
		}
		else if("5".equals(selectedvalue)){
		        String paymentstaus=reportsData.getPaymentstaus();
		        finalQuery+=TRANSACTION_DETAILS_PAYMENTSTATUS;
		        inparams=addParam(inparams, paymentstaus);
		        statobj=executeQuery(finalQuery,date,typeIndex,reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,events);		       
		}else if("6".equals(selectedvalue)){
		        String ordernumber=reportsData.getOrderNumber();
		        finalQuery+=TRANSACTION_DETAILS_ORDERNUMBER;
		        inparams=addParam(inparams, ordernumber);
		        statobj=executeQuery(finalQuery,date,typeIndex,reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,events);		        
		}else if("7".equals(selectedvalue)){
		        String source=reportsData.getSource();		        
				if("direct".equals(source)){
					finalQuery+=TRANSACTION_DETAILS_DIRECT;
					statobj=executeQuery(finalQuery,date,typeIndex,reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,events);					
				}else if("nts".equals(source)){
					finalQuery+=TRANSACTION_DETAILS_NTS;
					statobj=executeQuery(finalQuery,date,typeIndex,reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,events);					
						 
				}else if("alltrackcodes".equals(source)){
					finalQuery+=TRANSACTION_DETAILS_ALLTRACKCODES;
					statobj=executeQuery(finalQuery,date,typeIndex,reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,events);					
				}else if("fbapp".equalsIgnoreCase(source)){
					finalQuery+=TRANSACTION_DETAILS_FBAPP;
					inparams=addParam(inparams, source);
					statobj=executeQuery(finalQuery,date, 
							typeIndex, reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,events);
				}else{
					finalQuery+=TRANSACTION_DETAILS_TRACKCODE;
					inparams=addParam(inparams, source);
					statobj=executeQuery(finalQuery,date,typeIndex,reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,events);					
				}
		 }else{
			 finalQuery+=ALL_TRANSACTION_DETAILS;
			 statobj=executeQuery(finalQuery,date,typeIndex,reportsData.getTicketid(),dbmanager,inparams,sortField,sortDirection,events);	
			 
			}
		//System.out.println("Transaction_Reports_eid: "+eid+" UUID: getTransactionInfo(ReportsDB.java) ended, free memory: "+((Runtime.getRuntime().freeMemory())/(1024*1024))+" Mb");
		long totaltime=(System.currentTimeMillis())-starttime;
		//System.out.println("Transaction_Reports_eid: "+eid+" UUID: Total time taken to excute TRANSACTION_DETAILS query: "+totaltime+" MS");
   
		return getTransactionDetailsList(statobj,events,ticketsinfo,notesinfo,dbmanager,Fields,currencymap); 
		  
	}
	public static String[] addParam(String[] inparam,String param){
		int lastindex=inparam.length;
		String[] temp=new String[lastindex+1];
		for(int i=0;i<lastindex;i++)
			temp[i]=inparam[i];
		temp[lastindex]=param;
		return temp;
	}

	
public static ArrayList<HashMap<String,String>> getAllReports(String events,String selindex,HashMap ticketsinfo,AccountLevelReportsData reportsData,String typeIndex,String sortField,
		String sortDirection,ArrayList Fields,HashMap<String,String> currencymap){
	//ArrayList<HashMap<String,String>> List=new ArrayList<HashMap<String,String>>();		 
		 String[] vendorTypes=new String[]{"eventbee", "google", "paypal","ebeecredits","nopayment","other","","authorize.net","braintree","stripe"};
	     ArrayList<HashMap<String,String>> transactionDetailsList=new ArrayList<HashMap<String,String>>();		 
 		int paymentTypeIndex=Integer.parseInt(reportsData.getPaymenttypeindex());
 		String paymenttype="";
 		if(paymentTypeIndex==6)
 			paymenttype="all";
 		else
 		paymenttype=vendorTypes[paymentTypeIndex];
 		transactionDetailsList=getTransactionInfo(events,selindex,ticketsinfo,getTransactionNotesInfo(events),reportsData,paymenttype,typeIndex,sortField,sortDirection,Fields,currencymap);
    	return transactionDetailsList;
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
	
	public static String getCurrentTransctionDate(String eid,String tid){
		
		AccountLevelReportsData reportsData=new AccountLevelReportsData();
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
		
			DBManager db=new DBManager();
			ArrayList pendingAppList=new ArrayList();    
			StatusObj statobj=null;
			String query = "select count(*),eventdate from event_reg_transactions where eventid=? and paymentstatus='Need Approval' and eventdate!='' and eventdate is not null group by eventdate";
			
			statobj=db.executeSelectQuery(query,new String[]{eid});
			int count=statobj.getCount();
			if(statobj.getStatus() && count>0)
	          for(int i=0;i<count;i++)
	        	  js.put(db.getValue(i,"eventdate",""),db.getValue(i,"count",""));
			
		
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
	
    public static String checkOtherCCPFee(String eid){
		String otherccpfee=DbUtil.getVal("select value from mgr_config where name in('paypal.actual.ccfee','google.actual.ccfee') and userid=(select mgr_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[]{eid} );
		return otherccpfee;
	}
	
public static HashMap<String,String> getEventsCurrencyMap(String events){
	HashMap<String,String> eventcurrencymap=new HashMap<String,String>();
	try{
	DBManager dbmanager=new DBManager();
	StatusObj stobj=null;
	if(events!=null && !"".equals(events) && !"null".equals(events))
	stobj=dbmanager.executeSelectQuery("select distinct eventid,currency_symbol from currency_symbols cs,event_currency ec where " +
				"cs.currency_code=ec.currency_code and cast(eventid as integer) in("+events+")", null);
	if(stobj.getStatus() && stobj.getCount()>0){
		for(int i=0;i<stobj.getCount();i++){
			eventcurrencymap.put(dbmanager.getValue(i,"eventid",""),dbmanager.getValue(i,"currency_symbol",""));
		}
	}
	}catch(Exception e){
		System.out.println("Exception occured in getEventsCurrencyMap");
	}
	return eventcurrencymap;
  }
  
  
  public static void saveSelectedEvents(String eventlist,String mgrid,AccountLevelReportsData rdata){
		if(!"".equals(mgrid) && mgrid!=null){
		String isEntryExists=isEntryExists(mgrid);
		String updatequery="update all_reports_events set eventid=?,updated_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),event_start=?,event_upto=? where"+
		" mgr_id=cast(? as integer)";
		String insertquery="insert into all_reports_events(mgr_id,eventid,created_at,event_start,event_upto,updated_at) values (cast(? as integer),?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'))";
	    if("yes".equalsIgnoreCase(isEntryExists))
	    DbUtil.executeUpdateQuery(updatequery,new String[]{eventlist,DateUtil.getCurrDBFormatDate(),rdata.getStartdate(),rdata.getEnddate(),mgrid});
		else
		DbUtil.executeUpdateQuery(insertquery,new String[]{mgrid,eventlist,DateUtil.getCurrDBFormatDate(),rdata.getStartdate(),rdata.getEnddate(),DateUtil.getCurrDBFormatDate()});
		}
		
	}
  
  
  public static String isEntryExists(String mgrid){
	  String isexists="yes";
	  String val=DbUtil.getVal("select 'yes' from all_reports_events where mgr_id=cast(? as integer)",new String[]{mgrid});
	  if(val==null || "".equals(val) || "null".equals(val))
		  isexists="no";
	  return isexists;
  }
  
  public static JSONArray getAllEvents(String mgrid,HashMap<String,String> dateslist)
  {
	  /*ArrayList<Entity> arrlist=new ArrayList<Entity>();*/
	  JSONArray allevents=new JSONArray();
	  try{
			String[] params={};
		    String query="";
			String startdate=dateslist.get("startdate");
			String enddate=dateslist.get("enddate");
			if(startdate==null)startdate="";
			System.out.println("startdate is:"+startdate);
			if(!"".equals(startdate)){
				query="select eventid,eventname from eventinfo where mgr_id=cast(? as integer) and status!='CANCEL' and current_level in('100','200','300','400') and start_date::date between "+
					" to_timestamp(?,'mm/dd/yyyy') and to_timestamp(?,'mm/dd/yyyy') and current_level is not null order by start_date";
				params=new String[]{mgrid,startdate,enddate};
			}else {
				query="select eventid,eventname from eventinfo where mgr_id=cast(? as integer) and status!='CANCEL' and current_level in('100','200','300','400') and "+
				" current_level is not null order by start_date";
				params=new String[]{mgrid};
			}
				
		DBManager dbm=new DBManager();
		StatusObj stobj=null;
	    if(mgrid!=null && !"".equals(mgrid) && !"null".equals(mgrid))
	    stobj=dbm.executeSelectQuery(query,params);
	    if(stobj.getStatus()&& stobj.getCount()>0){
	    	for(int i=0;i<stobj.getCount();i++){
	    		JSONObject each=new JSONObject();
	    		each.put(dbm.getValue(i,"eventid",""),dbm.getValue(i,"eventname",""));
	    		allevents.put(each);
	    		/*arrlist.add(new Entity(dbm.getValue(i,"eventid",""),dbm.getValue(i,"eventname","")));*/
	    	}
	    }
		}catch(Exception e){
			System.out.println("Exception Occured in getEventsBwDates");
		}
		
	  return allevents;
  }
  
  public static String getMgrAccountCCStatus(String mgrid){
	  String cardstatus="",cardRequired="";
		if(!"".equals(mgrid) && mgrid!=null && !"null".equals(mgrid))
		cardRequired = DbUtil.getVal("select value from mgr_config where name='mgr.card.popup.required' and userid=CAST(? AS INTEGER)", new String[]{mgrid});
		if(cardRequired==null || "N".equalsIgnoreCase(cardRequired) || "".equals(mgrid))
			cardstatus="don't askcard";
		else{
			cardstatus=CardProcessorDB.getManagerCCStatus(mgrid);
		if(cardstatus==null || "".equals(cardstatus))
			cardstatus="askcard";
		else
			cardstatus="don't askcard";
		}
		return cardstatus;
  }
  
}
