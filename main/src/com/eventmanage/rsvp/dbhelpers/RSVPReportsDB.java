package com.eventmanage.rsvp.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONObject;

import com.event.beans.ScanData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.ReportsDB;
import com.event.rsvp.beans.RSVPReportsData;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;

public class RSVPReportsDB {
private static HashMap attColumnMap=new HashMap();
private static HashMap resattColumnMap=new HashMap();
static{
	
	attColumnMap.put("Transaction_ID", "transactionid");
	attColumnMap.put("Transaction_Date", "trdate");
	attColumnMap.put("First_Name", "fname");
	attColumnMap.put("Last_Name", "lname");
	attColumnMap.put("Email", "email");
	attColumnMap.put("Check_In_Time", "checkintime");
	attColumnMap.put("Scan_ID", "scanid");
	
	resattColumnMap.put("Transaction_ID", "tid");
	resattColumnMap.put("First_Name", "fname");
	resattColumnMap.put("Last_Name", "lname");
	resattColumnMap.put("Email", "email");
	resattColumnMap.put("profilekey", "profilekey");
	resattColumnMap.put("Tracking URL", "email");
	resattColumnMap.put("Response", "response");
	resattColumnMap.put("Notes", "notes");
	resattColumnMap.put("Transaction_Source", "bookingsource");
	resattColumnMap.put("Tracking_URL", "trackpartner");
	resattColumnMap.put("Transaction_Date", "bookingdate");
		
}
	private static final String RESPONSE_QUESTIONS_QUERY = "select question from report_custom_questions_filter" +
					" where eventid=CAST(? AS BIGINT) and reporttype='rsvpresponse'";
	 /*private static final String ATTENDING_COMMON_QUERY_PART=" select a.transactionid as tid,a.fname as fname,a.profilekey as profilekey,"+
		"a.lname as lname,lower(a.email) as email,a.mgr_notes as notes,"+
		"to_char(c.transaction_date,'yyyy-mm-dd') as bookingdate,c.bookingsource as bookingsource,"+ 
		"b.profilekey as transactionkey ,a.ticketid as response, c.eventdate,"+
		"c.trackpartner as trackpartner,c.bookingsource as bookingsource from profile_base_info a,buyer_base_info b ,event_reg_transactions c "+ 
		"where c.eventid=? and b.transactionid=a.transactionid and c.tid=b.transactionid ";
	 private static final String NOTATTENDING_COMMON_QUERY_PART="select c.tid,to_char(c.transaction_date,'mm/dd/yyyy') as bookingdate,b.fname as fname,b.lname as lname,lower(b.email) as email,b.phone,b.profilekey,c.eventdate," +
	 		" b.mgr_notes ,c.bookingsource,c.trackpartner, a.responsetype as response,a.yescount,a.notsurecount from event_reg_transactions c,buyer_base_info b," +
	 		" rsvp_transactions a where c.tid=b.transactionid and c.tid=a.tid and c.paymenttype='RSVP' and a.responsetype='N'" +
	 		" and c.eventid=?";*/
	 private static  String orderby=" order by transaction_date desc";
	 
	 private static String ATTENDING_COMMON_QUERY_PART="select a.transactionid as tid,a.fname as fname,a.lname as lname,lower(a.email) as email,b.phone,a.profilekey as profilekey," +
	 		" c.eventdate,a.mgr_notes as notes,to_char(c.transaction_date,'yyyy-mm-dd') as bookingdate,c.bookingsource as bookingsource,b.profilekey as transactionkey," +
	 		" a.ticketid::text as response,c.trackpartner as trackpartner,c.transaction_date from profile_base_info a,buyer_base_info b ,event_reg_transactions c " +
	 		" where c.eventid=? and b.transactionid=a.transactionid and c.tid=b.transactionid ";
	//union is there on these queries
	 private static String NOTATTENDING_COMMON_QUERY_PART=" select c.tid as tid,b.fname as fname,b.lname as lname,lower(b.email) as email,b.phone,b.profilekey as profilekey,c.eventdate,b.mgr_notes as notes," +
	 		" to_char(c.transaction_date,'yyyy-mm-dd') as bookingdate,c.bookingsource as bookingsource,b.profilekey as transactionkey,a.responsetype as response," +
	 		" c.trackpartner as trackpartner,c.transaction_date from event_reg_transactions c,buyer_base_info b,rsvp_transactions a where c.tid=b.transactionid " +
	 		" and c.tid=a.tid and c.paymenttype='RSVP' and a.responsetype='N' and c.eventid=? ";
	 
	 public static ArrayList<HashMap<String,String>> getRSVPAttendeeReports(String eid,HashMap attribResponse,ArrayList responseAttributes,RSVPReportsData rsvpReportsData,boolean export){
		boolean isrecurr=EventDB.isRecurringEvent(eid);
		String selindex=rsvpReportsData.getAttselindex();
		String resptdateradio=rsvpReportsData.getResptdateradio();
		ArrayList<String> bSourceList=rsvpReportsData.getBookingSourceType();
		int bSourcelistsize=rsvpReportsData.getBookingSourceType().size();
		String trackCode=rsvpReportsData.getTrackCode();
		//String tid=rsvpReportsData.getTid().trim();
		DBManager dbmanager=new DBManager();
		StatusObj stobj=null;
		String[] attnd_params={eid};
		String[] nonattnd_params={eid};
		String finalQuery="";
		String param="";
		String attndsubquery="";
		String nonattndsubquery="";
		if(rsvpReportsData.getSortedkey()!=null && !"".equals(rsvpReportsData.getSortedkey())&& resattColumnMap.containsKey(rsvpReportsData.getSortedkey().replaceAll(" ", "_"))){
			orderby="order by "+resattColumnMap.get(rsvpReportsData.getSortedkey().replaceAll(" ", "_"))+"  "+rsvpReportsData.getDir();
		}
		if(rsvpReportsData.getIsSearch()){
			if("transactionId".equals(rsvpReportsData.getSearchOn())){
				param=rsvpReportsData.getSearchContent().trim();
				attndsubquery=" and c.tid=?";
				nonattndsubquery=" and c.tid=?";
			}else if("email".equals(rsvpReportsData.getSearchOn())){
				param=rsvpReportsData.getSearchContent().trim();
				attndsubquery=" and lower(a.email)=lower(?)";
				nonattndsubquery=" and lower(b.email)=lower(?)";
			}else/* if("name".equals(rsvpReportsData.getSearchOn()))*/{
				param="%"+rsvpReportsData.getSearchContent().trim()+"%";
				attndsubquery=" and lower(a.fname||' '||a.lname) like lower(?)";
				nonattndsubquery=" and lower(b.fname||' '||b.lname) like lower(?)";
			}
			
			finalQuery=ATTENDING_COMMON_QUERY_PART+attndsubquery;
			finalQuery+=" union "+ NOTATTENDING_COMMON_QUERY_PART+nonattndsubquery;
			if(!"".equals(param))
				stobj=dbmanager.executeSelectQuery(finalQuery+orderby, new String[]{eid,param,eid,param});
			else 
				stobj=dbmanager.executeSelectQuery(finalQuery+orderby, new String[]{eid,eid});
		}else{
			System.out.println("rsvpReportsData.getAttselindex(): "+rsvpReportsData.getAttselindex());
			System.out.println("rsvpReportsData.getAttendeeType(): "+rsvpReportsData.getAttendeeType());
			System.out.println("rsvpReportsData.bSourceList(): "+bSourceList);
			if(export){
				if("Yes".equals(rsvpReportsData.getAttselindex())){
					finalQuery=ATTENDING_COMMON_QUERY_PART;
					if(rsvpReportsData.getAttendeeType().contains("sure")){
						finalQuery=finalQuery+" and a.ticketid=101";
					}
					if(rsvpReportsData.getAttendeeType().contains("notsure")){
						finalQuery=finalQuery+" and a.ticketid=102";
					}
				}else if("No".equals(rsvpReportsData.getAttselindex()))
					finalQuery=NOTATTENDING_COMMON_QUERY_PART;
				
				if(bSourceList.contains("Online") && !bSourceList.contains("Manager")){
					attndsubquery+=" and c.bookingsource='online'";
					nonattndsubquery+=" and c.bookingsource='online'";
				}else if(!bSourceList.contains("Online") && bSourceList.contains("Manager")){
					attndsubquery+=" and c.bookingsource='Manager'";
					nonattndsubquery+=" and c.bookingsource='Manager'";
				}
				
				/*if("transactionSource".equals(rsvpReportsData.getSearchOn())){
					String source=rsvpReportsData.getTrackCode();
					if("alltrackcodes".equals(source)){
						attndsubquery=" and (c.trackpartner!='' or c.trackpartner!='null')";
						nonattndsubquery=" and (c.trackpartner!='' or c.trackpartner!='null')";
					}else{
						param=source;
						attndsubquery=" and c.trackpartner=?";
						nonattndsubquery=" and c.trackpartner=?";
					}
				}*/
			}
			if(!("1".equals(resptdateradio))){
				if(rsvpReportsData.getEventStartDate().indexOf(",")>-1)rsvpReportsData.setEventStartDate(rsvpReportsData.getEventStartDate().replace(",",""));
				if(rsvpReportsData.getEventEndDate().indexOf(",")>-1)rsvpReportsData.setEventEndDate(rsvpReportsData.getEventEndDate().replace(",",""));
				String[] startdatearr=GenUtil.strToArrayStr(rsvpReportsData.getEventStartDate().trim(), "/");
				String startDate=startdatearr[2]+"-"+startdatearr[0]+"-"+startdatearr[1];
				String[] enddatearr=GenUtil.strToArrayStr(rsvpReportsData.getEventEndDate().trim(), "/");
				String endDate=enddatearr[2]+"-"+enddatearr[0]+"-"+enddatearr[1];
				
				attndsubquery+=" and to_char(c.transaction_date,'yyyy-mm-dd') between ? and ? ";
				attnd_params =addParam(attnd_params, startDate);
				attnd_params =addParam(attnd_params, endDate);
				
				nonattndsubquery+=" and to_char(c.transaction_date,'yyyy-mm-dd') between ? and ? ";
				nonattnd_params =addParam(nonattnd_params, startDate);
				nonattnd_params =addParam(nonattnd_params, endDate);
			}
			if(isrecurr){
				if(!"all".equals(rsvpReportsData.getDate())){
					attndsubquery+=" and c.eventdate=? ";
					attnd_params=addParam(attnd_params, rsvpReportsData.getDate());
					
					nonattndsubquery+=" and c.eventdate=? ";
					nonattnd_params =addParam(nonattnd_params, rsvpReportsData.getDate());
				}
			}
			String [] totalParams={};
			if(export && !"1".equals(rsvpReportsData.getAttselindex())){
				if("Yes".equals(rsvpReportsData.getAttselindex())){
					finalQuery=finalQuery+attndsubquery;
					totalParams=concatenateArrays(totalParams,attnd_params);
				}else if("No".equals(rsvpReportsData.getAttselindex())){
					finalQuery=finalQuery+nonattndsubquery;
					totalParams=concatenateArrays(totalParams,nonattnd_params);
				}
			}else{
				finalQuery=ATTENDING_COMMON_QUERY_PART+attndsubquery;
				finalQuery=finalQuery+" union "+ NOTATTENDING_COMMON_QUERY_PART+nonattndsubquery;
				totalParams=concatenateArrays(attnd_params,nonattnd_params);
			}
			System.out.println("getRSVPAttendeeReports attnd_params size: "+attnd_params.length+" nonattnd_params size: "+nonattnd_params.length);
			System.out.println("getRSVPAttendeeReports totalParams size: "+totalParams.length+" finalQuery: "+finalQuery);
			stobj=dbmanager.executeSelectQuery(finalQuery+orderby,totalParams);
		}
		ArrayList<HashMap<String,String>> rsvpresponseList=new ArrayList<HashMap<String,String>>();
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				HashMap<String,String> respinfo=new HashMap<String, String>();
				respinfo.put("Transaction ID", dbmanager.getValue(i, "tid",""));
				respinfo.put("First Name", dbmanager.getValue(i, "fname", ""));
				respinfo.put("Last Name", dbmanager.getValue(i, "lname", ""));
				respinfo.put("Email", dbmanager.getValue(i, "email", ""));
				String response=dbmanager.getValue(i, "response","");
				if(response==null)
					response="";
				if("101".equals(response)){
					response="Attending";
					respinfo.put("RST", "Yes");
					respinfo.put("SUR", "sure");
				}if("n".equals(response.toLowerCase())){
					respinfo.put("RST", "No");
					response="Not Attending";
				}if("102".equals(response)){
					respinfo.put("RST", "Yes");
					respinfo.put("SUR", "notsure");
					response="Not Sure";
				}
				respinfo.put("Response", response);
				respinfo.put("Notes", dbmanager.getValue(i,"notes", ""));
				respinfo.put("Transaction Date", dbmanager.getValue(i,"bookingdate", ""));
				String bookingsource=dbmanager.getValue(i,"bookingsource", "");
				if("online".equals(bookingsource)) bookingsource="Online";
				respinfo.put("Transaction Source", bookingsource);
				respinfo.put("BKS", bookingsource);
				String trackurl=dbmanager.getValue(i, "trackpartner", "");
				if(trackurl.equals("null") || trackurl==null)	trackurl="";
				respinfo.put("Tracking URL", trackurl);
				String eventdate=dbmanager.getValue(i, "eventdate", "");
				if("null".equals(eventdate)) eventdate="";
				respinfo.put("Event Date", eventdate);
				HashMap respattribhm=new HashMap();
				HashMap transattribhm=new HashMap();
				if (attribResponse!=null&&attribResponse.size()>0){		
					respattribhm=(HashMap)attribResponse.get(dbmanager.getValue(i,"profilekey",""));
					transattribhm=(HashMap)attribResponse.get(dbmanager.getValue(i,"transactionkey",""));
					if(respattribhm==null) respattribhm=new HashMap();
					if(transattribhm==null) transattribhm=new HashMap();
				}
				for(int p=0;p<responseAttributes.size();p++){
					//Entity a=(Entity) responseAttributes.get(p);
					String a=(String) responseAttributes.get(p);
					String val=(String)respattribhm.get(a);
					if(val==null) val=(String)transattribhm.get(a);
					if(val==null)	val="";
					if(val.indexOf("##")>0)	val=val.replaceAll("##",", ");	
					respinfo.put(a,GenUtil.AllXMLEncode(val));
				}
			rsvpresponseList.add(respinfo);
			}
		}
		return rsvpresponseList;
	}
public static ArrayList<HashMap<String,String>> getRSVPCheckedInReports(String eid,RSVPReportsData rsvpReportsData){
	
	String GET_RSVPCHECKEDIN_INFO="select a.transactionid,a.fname as fname,a.lname as lname,lower(a.email) as email,a.profilekey,a.scanid,a.checkintime" +
			" from profile_base_info a,event_reg_transactions b where a.transactionid=b.tid " +
			"and a.eventid=CAST(? AS BIGINT)";
	HashMap scanDataMap;
	boolean isrecurr=EventDB.isRecurringEvent(eid);
	String checkinselindex=rsvpReportsData.getCheckinreportsindex();
	String checkdateradio=rsvpReportsData.getCheckDateRadio();
	//if("2".equ)
	String[] checkedinreportsinparams={eid};
	if(isrecurr){
		if(!"all".equals(rsvpReportsData.getDate())){
		scanDataMap=ReportsDB.getScanStatusData(eid,rsvpReportsData.getDate());
		GET_RSVPCHECKEDIN_INFO+=" and b.eventdate=?";
		checkedinreportsinparams=new String[]{eid,rsvpReportsData.getDate()};
		}else
			scanDataMap=ReportsDB.getScanStatusData(eid,null);	
	}
	else{
		scanDataMap=ReportsDB.getScanStatusData(eid,null);
	}
	
	String param="";
	String checkinsubquery="";
	if(rsvpReportsData.getIsSearch()){
		
		if("transactionId".equals(rsvpReportsData.getSearchOn())){
			param=rsvpReportsData.getSearchContent().trim();
			checkinsubquery=" and b.tid=?";
		}
		if("email".equals(rsvpReportsData.getSearchOn())){
			param=rsvpReportsData.getSearchContent().trim();
			checkinsubquery=" and lower(a.email)=lower(?)";
		}
		if("name".equals(rsvpReportsData.getSearchOn())){
			param="%"+rsvpReportsData.getSearchContent().trim()+"%";
			checkinsubquery=" and lower(a.fname||' '||a.lname) like lower(?)";
		}
		checkedinreportsinparams =addParam(checkedinreportsinparams, param);
		
	}else{
		if(!("1".equals(checkdateradio))){
			if(rsvpReportsData.getCheckInStartDate().indexOf(",")>-1)rsvpReportsData.setCheckInStartDate(rsvpReportsData.getCheckInStartDate().replace(",",""));
			if(rsvpReportsData.getCheckInEndDate().indexOf(",")>-1)rsvpReportsData.setCheckInEndDate(rsvpReportsData.getCheckInEndDate().replace(",",""));
			String[] startdatearr=GenUtil.strToArrayStr(rsvpReportsData.getCheckInStartDate().trim(), "/");
			String startDate=startdatearr[2]+"-"+startdatearr[0]+"-"+startdatearr[1];
			String[] enddatearr=GenUtil.strToArrayStr(rsvpReportsData.getCheckInEndDate().trim(), "/");
			String endDate=enddatearr[2]+"-"+enddatearr[0]+"-"+enddatearr[1];
			
			checkinsubquery+=" and to_char(b.transaction_date,'yyyy-mm-dd') between ? and ? ";
			checkedinreportsinparams =addParam(checkedinreportsinparams, startDate);
			checkedinreportsinparams =addParam(checkedinreportsinparams, endDate);
			
		}
	}
	
	GET_RSVPCHECKEDIN_INFO+=checkinsubquery;
	if(rsvpReportsData.getSortedkey()!=null && !"".equals(rsvpReportsData.getSortedkey())&& attColumnMap.containsKey(rsvpReportsData.getSortedkey().replaceAll(" ", "_"))){
		GET_RSVPCHECKEDIN_INFO+="order by "+attColumnMap.get(rsvpReportsData.getSortedkey().replaceAll(" ", "_"))+"  "+rsvpReportsData.getDir();
	}
	System.out.println("GET_RSVPCHECKEDIN_INFO:::"+GET_RSVPCHECKEDIN_INFO);
	DBManager dbmanager=new DBManager();
	StatusObj stobj=dbmanager.executeSelectQuery(GET_RSVPCHECKEDIN_INFO,checkedinreportsinparams);
	ArrayList<HashMap<String,String>> rsvpCheckedinList=new ArrayList<HashMap<String,String>>();
	 if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				boolean checkedin=false;
				
				HashMap<String,String> checkedininfo=new HashMap<String,String>();
				String attKey=dbmanager.getValue(i,"profilekey","");
				if(scanDataMap.get(attKey)!=null){
					ScanData scanData=(ScanData)scanDataMap.get(attKey);
					checkedininfo.put("Checked In","Yes"  );
					checkedininfo.put("Check In Time",scanData.getCheckedintime() );
					checkedininfo.put("Scan ID",scanData.getScanid() );
					checkedin=true;
				}else{
					checkedininfo.put("Checked In","No" );
					checkedininfo.put("Check In Time","" );
					checkedininfo.put("Scan ID","" );
				}
				checkedininfo.put("Attendee Key",attKey);
				checkedininfo.put("AK",attKey);
				checkedininfo.put("First Name",dbmanager.getValue(i,"fname",""));
				checkedininfo.put("Last Name",dbmanager.getValue(i,"lname","") );
			//	checkedininfo.put("Order Number",dbmanager.getValue(i,"ordernumber",""));
			  	String trnsid=dbmanager.getValue(i,"transactionid","");

				String transactionid="<a href='TransactionDetails!getTransactionInfo?eid="+eid+"&tid="+trnsid+"'>View</a>";
				checkedininfo.put("Transaction ID",trnsid);
				//checkedininfo.put("view",transactionid);
				checkedininfo.put("Email",dbmanager.getValue(i,"email","") );
				//if("1".equals(checkinselindex)){
					rsvpCheckedinList.add(checkedininfo);
				/*}else if("2".equals(checkinselindex)){
					if(checkedin) rsvpCheckedinList.add(checkedininfo);
				}else if("3".equals(checkinselindex)){
					if(!checkedin) rsvpCheckedinList.add(checkedininfo);
				}*/				
				
			}
		 }
	return rsvpCheckedinList;
}
	public static String[] addParam(String[] inparam,String param){
		int lastindex=inparam.length;
		String[] temp=new String[lastindex+1];
		for(int i=0;i<lastindex;i++)
			temp[i]=inparam[i];
		temp[lastindex]=param;
		return temp;
	}
	
	public static String[] concatenateArrays(String[] array1, String[] array2){
		return ArrayUtils.addAll(array1,array2);
	}
	public static String getResponseAttribsCount(String eid){
		String count=DbUtil.getVal("select count(*) from report_custom_questions_filter " +
				"where eventid=CAST(? AS BIGINT) and reporttype='rsvpresponse'", new String []{eid});
		return count;
	}
	/*public static ArrayList getSelectedResponseQuestions(String eid) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList selQuestion = new ArrayList();
		statobj=dbmanager.executeSelectQuery(RESPONSE_QUESTIONS_QUERY,new String []{eid});
		if(statobj.getStatus()){
			for (int i = 0; i < statobj.getCount(); i++) {				
				selQuestion.add(dbmanager.getValue(i,"question",""));
			}
		}
		return selQuestion;
	}*/
	
	public static ArrayList getSelectedResponseQuestions(String eid) {
		
		String QUERY = "select attrib_id from report_custom_questions_filter" +
		"  where eventid=CAST(? AS BIGINT) and reporttype='rsvpresponse'";
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList selQuestion = new ArrayList();
		statobj=dbmanager.executeSelectQuery(QUERY,new String []{eid});
		if(statobj.getStatus()){
			for (int i = 0; i < statobj.getCount(); i++) {				
				selQuestion.add(dbmanager.getValue(i,"attrib_id",""));
			}
		}
		return selQuestion;
	}
	public static void responseQuestionsFilter(ArrayList resQuestionFields, String eid, HashMap<String, String> attributeFilterMap){
		DbUtil.executeUpdateQuery("delete from report_custom_questions_filter " +
				"where eventid=CAST(? AS BIGINT) and reporttype='rsvpresponse'",new String [] {eid});
		for (int i = 0; i < resQuestionFields.size(); i++) {
		String attribid = (String) resQuestionFields.get(i);
		String question = attributeFilterMap.get(attribid);
		DbUtil.executeUpdateQuery("insert into report_custom_questions_filter(reporttype,eventid,question,attrib_id)" +
				" values('rsvpresponse',CAST(? AS INTEGER)," +
				"?,CAST(? AS INTEGER)) ",new String [] {eid,question,attribid});

		}		
	}
	public static ArrayList getTrackCodes(String eid){
		DBManager dbmanager=new DBManager();
		ArrayList sourceList=new ArrayList();
		StatusObj sb=dbmanager.executeSelectQuery("select distinct trackpartner from event_reg_transactions where eventid=? and trackpartner!='' and  trackpartner!='null'",new String[]{eid});
		if(sb.getStatus()){
			for(int k=0;k<sb.getCount();k++){
				String trackcode=dbmanager.getValue(k,"trackpartner","");
				sourceList.add(new Entity(trackcode,trackcode));
			}		
		}
		return sourceList;
	}
	
	public static JSONObject getRecurringAttendeeCount(String eid){
		JSONObject js=new JSONObject();
		try{
			
			int totalsure=0,totalnotsure=0,totalchk=0;
			DBManager db=new DBManager();
			StatusObj statobj=null;
			String query = "select sum(a.yescount) as  surecnt,sum(a.notsurecount) as notsurecnt,b.eventdate from rsvp_transactions a, event_reg_transactions b " +
					"where b.eventid=? and b.eventdate!='' and a.responsetype='Y' and a.eventid=b.eventid and a.tid=b.tid group by b.eventdate";
			
			statobj=db.executeSelectQuery(query,new String[]{eid});
			int count=statobj.getCount();
			if(statobj.getStatus() && count>0)
	          for(int i=0;i<count;i++){
	        	  js.put(db.getValue(i,"eventdate","")+"_sure",db.getValue(i,"surecnt","0"));
	        	  js.put(db.getValue(i,"eventdate","")+"_notsure",db.getValue(i,"notsurecnt","0"));
	        	  totalsure+=Integer.parseInt(db.getValue(i,"surecnt","0"));
	        	  totalnotsure+=Integer.parseInt(db.getValue(i,"notsurecnt","0"));
	          }
			
			String chkinQry="select count(*) as chkcnt,b.eventdate from event_scan_status as a, event_reg_transactions b where b.eventid=? and b.eventdate!='' and a.eventid=b.eventid and a.tid=b.tid group by b.eventdate";
			statobj=db.executeSelectQuery(chkinQry,new String[]{eid});
			if(statobj.getStatus() && statobj.getCount()>0)
	          for(int i=0;i<statobj.getCount();i++){
	        	  js.put(db.getValue(i,"eventdate","")+"_chkcnt",db.getValue(i,"chkcnt","0"));
	        	  totalchk+=Integer.parseInt(db.getValue(i,"chkcnt","0"));
	          }
			js.put("totalSure",totalsure);
			js.put("totalNotSure",totalnotsure);
			js.put("totalCheckIn",totalchk);
		
		}catch(Exception e){
			System.out.println("Exception in ReportsDB getRecurringPendingApprovalCount: "+e.getMessage());
		}
		return js;
	}
	
	public static HashMap<String, String> getTotalAttendees(String eventid){
		HashMap<String, String> hm = new HashMap<String, String>();
		String query="select sum(yescount) as  surecnt,sum(notsurecount) as notsurecnt from rsvp_transactions where eventid=? and responsetype='Y'";
		DBManager db=new DBManager();
		StatusObj statobj=null;
		statobj=db.executeSelectQuery(query,new String[]{eventid});
		int count=statobj.getCount();
		if(statobj.getStatus() && count>0){
        	  hm.put("sure",db.getValue(0,"surecnt","0"));
        	  hm.put("notsure",db.getValue(0,"notsurecnt","0"));
		}
		return hm;
	}
	
	public static String getTotalCheckIns(String eventid){
		String qry="select count(*) from event_scan_status a, event_reg_transactions b where b.eventid=? and a.eventid=b.eventid and a.tid=b.tid";
		String total=DbUtil.getVal(qry, new String[]{eventid});
		if(total==null) total="0";
		return total;
	}
}