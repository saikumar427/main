package com.event.dbhelpers;

import java.io.StringReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;
import com.event.beans.EmailAttendeesData;
import com.event.beans.PrintedData;
import com.eventbee.beans.DateTime;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventregister.dbhelper.RegistrationTiketingManager;

import org.apache.fop.apps.*;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.sax.SAXResult;

public class PrePrintedTicketsDB {
	
	
	public static ArrayList<HashMap<String,String>> getPrintedTicketDetails(String eid,String eventdate){
		String query="";
		String[] params;
	if("".equals(eventdate.trim()))	
		query ="select list_id,ticket_name,ticketid,prefix,notes,agentcode,startsfrom,ends from preprinted_tickets_list where eid=?::bigint and edate=''" ;
	else
		query ="select list_id,ticket_name,ticketid,prefix,notes,agentcode,startsfrom,ends from preprinted_tickets_list where eid=?::bigint and edate=?" ;
	 params=new String[]{eid,eventdate};
	
	DBManager dbmanager=new DBManager();
	StatusObj statobj=dbmanager.executeSelectQuery(query,params);
	int count=statobj.getCount();
	ArrayList<HashMap<String,String>> PrintedList=new ArrayList<HashMap<String,String>>();
	if(statobj.getStatus()&&count>0){
		for(int k=0;k<count;k++){     
			HashMap<String,String>notassigned=new HashMap<String,String>();
			notassigned.put("listid",dbmanager.getValue(k,"list_id",""));
			notassigned.put("ticketname",dbmanager.getValue(k,"ticket_name",""));
			notassigned.put("ticketid",dbmanager.getValue(k,"ticketid",""));
			notassigned.put("prefix",dbmanager.getValue(k,"prefix",""));
			notassigned.put("notes",dbmanager.getValue(k,"notes",""));
			notassigned.put("startsfrom",dbmanager.getValue(k,"startsfrom",""));
			notassigned.put("ends",dbmanager.getValue(k,"ends",""));
			notassigned.put("agentcode",dbmanager.getValue(k,"agentcode",""));
			PrintedList.add(notassigned);
		}
	}
	return PrintedList;
	}
	
	public static String savePrintedTickets(String eid,PrintedData printdata,String eventDate){
		StringBuffer fop = new StringBuffer();
		String selectedtkt=printdata.getSelectedtkt();
		String fromfix=printdata.getFromfix();
		String tofix=printdata.getTofix();
		String agentcode=printdata.getAgentcode();
		String prefix=printdata.getPrefix();
		String listId=DbUtil.getVal("select nextval('PRINTEDTICKETS_LISTID')", null);
		try{
			fop.append("<fo:root xmlns:fo='http://www.w3.org/1999/XSL/Format'><fo:layout-master-set>");
			fop.append("<fo:simple-page-master page-height='11.0in' master-name='PageMaster'  margin-top='0.5in' margin-bottom='0.5in' margin-left='0.5in' margin-right='0.25in' >");
			fop.append("<fo:region-body border-width='0in' border-style='solid'/>");
			fop.append("<fo:region-after display-align='after' extent='10mm'/>");
			fop.append("</fo:simple-page-master></fo:layout-master-set>");
			fop.append("<fo:page-sequence format='1' initial-page-number='1' force-page-count='no-force' master-reference='PageMaster'>");
			fop.append("<fo:flow line-height='15pt' font-size='12pt' flow-name='xsl-region-body'>");
			String  serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
			
			String templateid=printdata.getSelectedtemplate();
			String pdflayout=DbUtil.getVal("select html from preprinted_tickets_templates where eid='0' and templateid=?",new String []{templateid});
			
			
			HashMap<String,String> evtmap=new HashMap<String,String>();
			HashMap<String,String>qrbarmap=new HashMap<String,String>();
			evtmap=getEventInfo(eid);
			qrbarmap=getQrCodeBarCode(eid);
			HashMap ticketMap=getTicketMap(eid);
			String logoURL = serveraddress+"/home/images/logo_big.jpg";
			String eventNm = GenUtil.AllXMLEncode((String)evtmap.get("EVENTNAME"));
			String startday = (String)evtmap.get("StartDate_Day");
			String starttime = (String)evtmap.get("STARTTIME");
			/*String endday = (String)evtmap.get("EndDate_Day");
			String endtime = (String)evtmap.get("ENDTIME");*/
			String venue = GenUtil.AllXMLEncode((String)evtmap.get("VENUE"));
			String location = GenUtil.AllXMLEncode((String)evtmap.get("LOCATION"));
			String where ="";
			if(!"".equals(venue) && venue!=null)
				where=venue+" "+location;
			else where =location;
			String eventdate=startday+" "+starttime;
			
		int from=Integer.parseInt(fromfix);
		int to=Integer.parseInt(tofix);
		int count=to-from;
		String attendeeids[]=DbUtil.getSeqVals("seq_attendeeId",count+2);
		
		
		
		String listquery="insert into preprinted_tickets_list(list_id,eid,edate,created_at,ticketid,notes,agentcode,prefix,startsfrom,ends,ticket_name)"+
		"values(?::bigint,?::bigint,?,now(),?::bigint,?,?,?,?,?,?)";
		
		DbUtil.executeUpdateQuery(listquery,new String[]{listId,eid,eventDate,selectedtkt,"",agentcode,prefix,from+"",to+"",ticketMap.get(selectedtkt)+""});
		
		
		
		String query="insert into preprinted_tickets_details(eid,edate,status,created_at,created_by,internal_key,agent_code,ticketid,attendeekey,event_date,list_id)"+
		" values(?,now(),'NOTASSIGNED',now(),'MANAGER',?,?,?,?,?,?::bigint)";
		
		for(int i=0;i<=count;i++){
			String attendeeKey="AK"+EncodeNum.encodeNum(attendeeids[i]).toUpperCase();
			String internalkey=prefix+""+(from++);
			DbUtil.executeUpdateQuery(query,new String[]{eid,internalkey,agentcode,selectedtkt,attendeeKey,eventDate,listId});
			
			String pdfbarcode=(String)qrbarmap.get("pdfbarcode");
			String pdfqrcode=(String)qrbarmap.get("pdfqrcode");
			pdfqrcode=pdfqrcode.replace("117x117","110x110");
			JSONObject jobj=new JSONObject();
			jobj.put("eid",eid);
			jobj.put("xid",internalkey);
			jobj.put("on","1");
			jobj.put("tId",selectedtkt);
			String pdfbar[]=pdfbarcode.split("#msg");
			pdfbarcode=serveraddress+"/"+pdfbar[0]+attendeeKey+pdfbar[1];
			String pdfqr[]=pdfqrcode.split("#msg");
			jobj.put("pkey",attendeeKey);
			String objstring=jobj.toString();
			pdfqrcode=pdfqr[0]+java.net.URLEncoder.encode(objstring);
			VelocityContext mp = new VelocityContext();
			
			mp.put("eventName",eventNm);
			mp.put("when",eventdate);
			mp.put("where",where);
			mp.put("logoUrl",logoURL);
			mp.put("barcode",pdfbarcode);
			mp.put("qrcode",pdfqrcode);
			mp.put("referencekey",internalkey);
			mp.put("ticketName",ticketMap.get(selectedtkt));
			java.io.StringWriter out1=new java.io.StringWriter();
			VelocityEngine ve= new VelocityEngine();
			ve.init();
			boolean abletopares=ve.evaluate(mp,out1,"pdftickettemplate",pdflayout);
			/*if(i%2==0)
			fop.append("<fo:block linefeed-treatment=\"preserve\">&#xA;</fo:block>");*/
			fop.append(out1.getBuffer());
			fop.append("<fo:block linefeed-treatment=\"preserve\">&#xA;</fo:block>");
		}
		fop.append("</fo:flow></fo:page-sequence></fo:root>");
		}catch(Exception e){
			System.out.println("exception ::"+e.getMessage());
		}
		
		try{
		StringReader reader = new StringReader(fop.toString());
		
		System.out.println("the reader is::"+reader);
		
	    FopFactory fopFactory = FopFactory.newInstance();
	    FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
	    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	    Fop fops = fopFactory.newFop("application/pdf",foUserAgent, outStream);
	    TransformerFactory factory = TransformerFactory.newInstance();
	    Transformer transformer = factory.newTransformer();
	    Result res = new SAXResult(fops.getDefaultHandler());
	    transformer.transform(new StreamSource(reader), res);
	    String filepath=EbeeConstantsF.get("temp.upload.filepath","C:\\uploads\\");
	    String fileName=eid+"_"+listId+".pdf";
		FileOutputStream output = new FileOutputStream(filepath+fileName);
	    output.write(outStream.toByteArray());
	    output.close();
		}catch(Exception e){
			System.out.println("the exception occured while generating Pdf:"+e.getMessage());
		}
		
		
		return listId;
		}
	
	public static ArrayList getNotAssignedPrintedTicketsList(String eid,String eventDate,String listid){
		ArrayList scheduledList = new ArrayList();
		HashMap<String,String> hm=new HashMap<String,String>();
		String query ="";
		String params[];
		if("".equals(eventDate)){    
		query = "select ticketid,attendeekey,agent_code,internal_key,to_char(edate, 'Dy, Mon DD, YYYY, HH:MI PM') as sch_time " +
				"from preprinted_tickets_details where status='NOTASSIGNED' and eid=?::bigint and list_id=?::bigint order by created_at desc";
		params=new String[]{eid,listid};
		}else{
			query = "select ticketid,attendeekey,agent_code,internal_key,to_char(edate, 'Dy, Mon DD, YYYY, HH:MI PM') as sch_time " +
					"from preprinted_tickets_details where status='NOTASSIGNED' and eid=?::bigint and event_date=? and list_id=?::bigint order by created_at desc";
			params=new String[]{eid,eventDate,listid};
		}
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,params);
		int count=statobj.getCount();
		
		if(statobj.getStatus()&&count>0){
			for(int k=0;k<count;k++){     
				EmailAttendeesData emailEmailAttendeesData = new EmailAttendeesData();
				HashMap notassigned=new HashMap();
				notassigned.put("attendeekey",dbmanager.getValue(k,"attendeekey",""));
				notassigned.put("internalkey",dbmanager.getValue(k,"internal_key",""));
				notassigned.put("schtime",dbmanager.getValue(k,"sch_time",""));
				notassigned.put("ticketid",dbmanager.getValue(k,"ticketid",""));
				notassigned.put("agentcode",dbmanager.getValue(k,"agent_code",""));
				scheduledList.add(notassigned);
			}
		}

		return scheduledList;
	}
	
	public static ArrayList getAssignedPrintedTicketsList(String eid,String eventDate,String listid){
		ArrayList scheduledList = new ArrayList();
		HashMap<String,String> hm=new HashMap<String,String>();
		String query ="";
		String params[];
		if("".equals(eventDate)){ 
			query = "select ticketid,attendeekey,internal_key,agent_code,notes" +
					" from preprinted_tickets_details where status='ASSIGNED' and eid=?::bigint and list_id=?::bigint order by created_at desc";
		params=new String[]{eid,listid};
		}else{
			query = "select ticketid,attendeekey,internal_key,agent_code,notes" +
					" from preprinted_tickets_details where status='ASSIGNED' and eid=?::bigint and event_date=? and list_id=?::bigint order by created_at desc";
			params=new String[]{eid,eventDate,listid};
		}
		
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,params);
		int count=statobj.getCount();
		
		if(statobj.getStatus()&&count>0){
			for(int k=0;k<count;k++){
				HashMap assigned=new HashMap();
				assigned.put("attendeekey",dbmanager.getValue(k,"attendeekey",""));
				assigned.put("internalkey",dbmanager.getValue(k,"internal_key",""));
				assigned.put("ticketid",dbmanager.getValue(k,"ticketid",""));
				assigned.put("agentcode",dbmanager.getValue(k,"agent_code",""));
				assigned.put("notes",dbmanager.getValue(k,"notes",""));
				scheduledList.add(assigned);
			}
		}
		return scheduledList;
	}
	
	
	public static void saveChangeStatus(String eid,String attendees,String notes,String issametransaction){
		String GET_TRANSACTION_ID_QUERY = "select nextval('seq_transactionid') as transactionid";
		String attendeesarr[]=attendees.split(",");
		String UpdateQuery="update preprinted_tickets_details set status='ASSIGNED',tid=?,notes=? where eid=?::bigint and attendeekey=?";
		String transid=DbUtil.getVal(GET_TRANSACTION_ID_QUERY,new String[]{});
		String transactionid="RK"+EncodeNum.encodeNum(transid).toUpperCase();
		
		for(int i=0;i<attendeesarr.length;i++){
			if(!"on".equals(issametransaction)){
				transid=DbUtil.getVal(GET_TRANSACTION_ID_QUERY,new String[]{});
				transactionid="RK"+EncodeNum.encodeNum(transid).toUpperCase();
			}
			DbUtil.executeUpdateQuery(UpdateQuery,new String[]{transactionid,notes,eid,attendeesarr[i]});
		}
		
	}
	
	public static void deleteAttendees(String eid,String attendeekeys){
		String attendeekey[]=attendeekeys.split(",");
		for(int i=0;i<attendeekey.length;i++){
		String UpdateQuery="update preprinted_tickets_details set status='DELETED' where eid=?::bigint and attendeekey=?";
		DbUtil.executeUpdateQuery(UpdateQuery,new String[]{eid,attendeekey[i]});
		}
	}
	
	private static JSONArray getPrintedJson(HashMap ticketMap,ArrayList prePrintedList){
		JSONArray jsonarrObj = new JSONArray();
		int ntsIndex=0;
		int ntsTotal = prePrintedList.size();
		try {
			for (int i=0;i<ntsTotal;i++) {
				HashMap map=(HashMap)prePrintedList.get(i);
				String isFirst =(ntsIndex==0)?"Y": "N";
				String isLast =(ntsIndex == ntsTotal-1)?"Y": "N";
				JSONObject jsonObj_emailAttendeesDetails = new JSONObject();
				String listid = (String)map.get("listid");
				String ticketname = (String)map.get("ticketname");
				String ticketid = "",prefix="",notes="",agentcode="",startsfrom="",ends="";
				if(map.containsKey("ticketid"))
					ticketid=(String)map.get("ticketid");
				
				if(map.containsKey("prefix"))
					prefix=(String)map.get("prefix");
				if(map.containsKey("agentcode"))
					agentcode=(String)map.get("agentcode");
				if(map.containsKey("notes"))
					notes=(String)map.get("notes");
				if(map.containsKey("startsfrom"))
					startsfrom=(String)map.get("startsfrom");
				if(map.containsKey("ends"))
					ends=(String)map.get("ends");
				
				jsonObj_emailAttendeesDetails.put("listid",listid);
				jsonObj_emailAttendeesDetails.put("ticketname",ticketname);
				jsonObj_emailAttendeesDetails.put("ticketid",ticketid);
				jsonObj_emailAttendeesDetails.put("prefix",prefix);
				jsonObj_emailAttendeesDetails.put("notes",notes);
				jsonObj_emailAttendeesDetails.put("startsfrom",startsfrom);
				jsonObj_emailAttendeesDetails.put("ends",ends);
				jsonObj_emailAttendeesDetails.put("agentcode",agentcode);
				jsonarrObj.put(jsonObj_emailAttendeesDetails);
				ntsIndex++;
			}
		} catch (Exception ex) {
			System.out.println("\n In getPromotedEventsJson: "+ex.getMessage());
		}
		return jsonarrObj;
	}
	
	
	public static JSONArray getAllPrePrintedListJson(HashMap ticketMap,ArrayList prePrintedList){
		JSONArray jsonarrObj = new JSONArray();
		int ntsIndex=0;
		int ntsTotal = prePrintedList.size();
		try {
			for (int i=0;i<ntsTotal;i++) {
				HashMap map=(HashMap)prePrintedList.get(i);
				String isFirst =(ntsIndex==0)?"Y": "N";
				String isLast =(ntsIndex == ntsTotal-1)?"Y": "N";
				JSONObject jsonObj_emailAttendeesDetails = new JSONObject();
				String akey = (String)map.get("attendeekey");
				String internalkey = (String)map.get("internalkey");
				String schtime = "",agentcode="",notes="";
				if(map.containsKey("schtime"))
					schtime=(String)map.get("schtime");
				String ticketid="";
				if(map.containsKey("ticketid"))
					ticketid=(String)map.get("ticketid");
				if(map.containsKey("agentcode"))
					agentcode=(String)map.get("agentcode");
				if(map.containsKey("notes"))
					notes=(String)map.get("notes");
				jsonObj_emailAttendeesDetails.put("attendeekey",akey);
				jsonObj_emailAttendeesDetails.put("internalkey",internalkey);
				jsonObj_emailAttendeesDetails.put("schtime",schtime);
				jsonObj_emailAttendeesDetails.put("action","");
				jsonObj_emailAttendeesDetails.put("slno", ntsIndex+1);
				jsonObj_emailAttendeesDetails.put("ticketname",ticketMap.get(ticketid));
				jsonObj_emailAttendeesDetails.put("agentcode",agentcode);
				jsonObj_emailAttendeesDetails.put("notes",notes);
				jsonarrObj.put(jsonObj_emailAttendeesDetails);
				ntsIndex++;
			}
		} catch (Exception ex) {
			System.out.println("\n In getPromotedEventsJson: "+ex.getMessage());
		}
		return jsonarrObj;
	}
	
	public static JSONObject getAllPrePrintedJson(String eid,ArrayList notassignedList, ArrayList assignedList){
		JSONObject allEmailAttendeesObj=new JSONObject();
		HashMap ticketMap=getTicketMap(eid);
		try{
			allEmailAttendeesObj.put("notassigned",getAllPrePrintedListJson(ticketMap,notassignedList));
			allEmailAttendeesObj.put("assigned",getAllPrePrintedListJson(ticketMap,assignedList));
		}catch(Exception e){}
		return allEmailAttendeesObj;
	}
	
	public static JSONObject getPrePrintedJson(String eid,ArrayList printedList){
		JSONObject allEmailAttendeesObj=new JSONObject();
		HashMap ticketMap=getTicketMap(eid);
		try{
			allEmailAttendeesObj.put("printedList",getPrintedJson(ticketMap,printedList));
		}catch(Exception e){}
		return allEmailAttendeesObj;
	}
	
	
	public static HashMap getTicketMap(String eid){
		HashMap tktMap=new HashMap();
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		statobj=dbm.executeSelectQuery("select price_id,ticket_name from  price where evt_id=CAST(? AS BIGINT) and isdonation='No' and ticket_name is not null", new String[]{eid});
		if(statobj.getStatus() && statobj.getCount()>0){
			for(int i=0;i<statobj.getCount();i++){
				tktMap.put(dbm.getValue(i,"price_id",""),dbm.getValue(i,"ticket_name",""));
			}
		}
		return tktMap;
	}
	
	public static HashMap<String,String> getEventInfo(String eid){
		HashMap<String,String> evtInfo=new HashMap<String,String>();
		  DBManager dbm=new DBManager();
		    StatusObj statobj=null;
	        try{
	        	 String EVENT_INFO_QUERY="select getMemberPref(mgr_id||'','pref:myurl','') as username,venue,starttime,endtime,eventname,status,email,mgr_id,evt_level,phone, to_char(start_date,'mm/dd/yyyy')  as startdate, to_char(end_date,'mm/dd/yyyy') as enddate, to_char(start_date,'Month DD, YYYY') as start_date,trim(to_char(end_date,'Day')) ||', '|| to_char(end_date,'Month DD, YYYY') as end_date,city,state,address1,address2,country "
	        			   +" from eventinfo  where   eventid=CAST(? as integer)";
	                statobj=dbm.executeSelectQuery(EVENT_INFO_QUERY, new String[]{eid});
			        if(statobj.getStatus() && statobj.getCount()>0){
	                for(int i=0;i<statobj.getCount();i++){
	                        evtInfo.put("EVENTNAME", dbm.getValue(i, "eventname",""));
	                        evtInfo.put("STATUS",dbm.getValue(i, "status",""));
	                        evtInfo.put("EMAIL",dbm.getValue(i, "email",""));
				            evtInfo.put("STARTDATE",dbm.getValue(i, "startdate",""));
				            evtInfo.put("ENDDATE",dbm.getValue(i, "enddate",""));
				            evtInfo.put("StartDate_Day",dbm.getValue(i, "start_date",""));
				            evtInfo.put("EndDate_Day",dbm.getValue(i, "end_date",""));
				            evtInfo.put("STARTTIME", DateTime.getTimeAM(dbm.getValue(i, "starttime","")));
				            evtInfo.put("ENDTIME", DateTime.getTimeAM(dbm.getValue(i, "endtime","")));
				            evtInfo.put("LOCATION",GenUtil.getCSVData(new String[]{dbm.getValue(i, "city",""),dbm.getValue(i, "state","")}));
				            evtInfo.put("VENUE",GenUtil.getCSVData(new String[]{dbm.getValue(i, "venue",""),dbm.getValue(i, "address1",""),dbm.getValue(i, "address2","")}));
	                        evtInfo.put("FULLADDRESS",GenUtil.getCSVData(new String[]{dbm.getValue(i, "venue",""),dbm.getValue(i, "address1",""),dbm.getValue(i, "address2",""),dbm.getValue(i, "city",""),dbm.getValue(i, "state",""),dbm.getValue(i, "country","")}));
	                        evtInfo.put("ADDRESS",GenUtil.getCSVData(new String[]{dbm.getValue(i, "address1",""),dbm.getValue(i, "address2","")}));
	                        evtInfo.put("EVENTLEVEL",dbm.getValue(i, "evt_level",""));
			       }
			  }
	}catch(Exception e){
		}
	return evtInfo;
}
	
	public static HashMap<String,String> getQrCodeBarCode(String eid) {
		HashMap<String,String> codemap=new HashMap<String,String>();
		String code_query="select * from barcode_qrcode_settings where eventid=? and purpose='printedtickets'";
		DBManager db=new DBManager();
		StatusObj code_sb=db.executeSelectQuery(code_query, new String[]{eid});
		if(code_sb.getStatus()){
			codemap.put("pdfbarcode",db.getValue(0, "pdfbarcode", ""));
			codemap.put("pdfqrcode",db.getValue(0, "pdfqrcode", ""));
		}
		else{
			code_sb=db.executeSelectQuery(code_query, new String[]{"0"});
			codemap.put("pdfbarcode",db.getValue(0, "pdfbarcode", ""));
			codemap.put("pdfqrcode",db.getValue(0, "pdfqrcode", ""));
		}
		return codemap;
	}
	
	public static ArrayList getDatesList(String eid){
		Vector vec=new Vector();
		ArrayList<Entity> dateslist=new ArrayList<Entity>();
		RegistrationTiketingManager rtm=new RegistrationTiketingManager();
		vec=rtm.getRecurringEventDates(eid);
		dateslist.add(new Entity("0","--Select Date--"));
		for(int i=0;i<vec.size();i++)
			dateslist.add(new Entity((String)vec.get(i),(String)vec.get(i)));
		return dateslist;
	}
}
