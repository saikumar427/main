package com.eventregister.dbhelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.StatusObj;
import com.eventregister.customquestions.beans.AttribOption;

public class ProfileActionDB {

	public void updateBaseProfile(HashMap baseprofile,String eid){
		
		StatusObj statobj = null;
		DBManager db = new DBManager();
		System.out.println("update base profile" + (String) baseprofile.get("tid") + eid);

		String fname = (String) baseprofile.get("fname");
		String lname = (String) baseprofile.get("lname");
		String email=(String) baseprofile.get("email");
		String phone=(String) baseprofile.get("phone");
		
		String buyerfname = "";
		String buyerlname = "";
		String buyeremail = "";
		String buyerphone = "";
		String buyerbaseinfo = "select fname,lname,email,phone from buyer_base_info where eventid=CAST(? AS BIGINT) and transactionid=?";
		statobj = db.executeSelectQuery(buyerbaseinfo, new String[] { eid,
				(String) baseprofile.get("tid") });
		System.out.println("status" + statobj.getStatus());
		buyerfname = db.getValue(0, "fname", "");
		buyerlname = db.getValue(0, "lname", "");
		buyeremail = db.getValue(0, "email", "");
		buyerphone = db.getValue(0, "phone", "");
		if ((fname == null || "".equals(fname)) && (lname == null || "".equals(lname))){
			fname = buyerfname;
			lname = buyerlname;
		}
		if(email == null || "".equals(email)){
			email=buyeremail;
		}
		if(phone == null || "".equals(phone)){
			phone=buyerphone;
		}	
	String profilebasequery = "insert into profile_base_info(eventid,fname,lname,transactionid,phone,email,profilekey,ticketid,tickettype,profileid,seatcode,seatindex,created_at)"
				+ " values(CAST(? AS BIGINT),?,?,?,?,?,?,CAST(? AS BIGINT),?,CAST(? AS INTEGER),?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
		StatusObj s1 = DbUtil.executeUpdateQuery(profilebasequery,
				new String[] { (String) baseprofile.get("eventid"), fname,
						lname, (String) baseprofile.get("tid"),
						phone,email,(String) baseprofile.get("profilekey"),
						(String) baseprofile.get("ticketid"),(String) baseprofile.get("tickettype"),
						(String) baseprofile.get("profileid"),(String)baseprofile.get("seatcode"),
						(String)baseprofile.get("seatindex"),DateUtil.getCurrDBFormatDate()});
	}
	
	
	public void updateBaseProfile(HashMap baseprofile){
		
	String profilebasequery="insert into profile_base_info(eventid,fname,lname,transactionid,phone,email,profilekey,ticketid,tickettype,profileid,seatcode,seatindex,created_at,profilestatus)"
	                          +" values(CAST(? AS BIGINT),?,?,?,?,?,?,CAST(? AS BIGINT),?,CAST(? AS INTEGER),?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),'Completed')";
	StatusObj s1=DbUtil.executeUpdateQuery(profilebasequery,new String[]{(String)baseprofile.get("eventid"),
			(String)baseprofile.get("fname"),(String)baseprofile.get("lname"),
			(String)baseprofile.get("tid"),(String)baseprofile.get("phone"),(String)baseprofile.get("email"),
			(String)baseprofile.get("profilekey"),(String)baseprofile.get("ticketid"),
			(String)baseprofile.get("tickettype"),(String)baseprofile.get("profileid"),
			(String)baseprofile.get("seatcode"),(String)baseprofile.get("seatindex"),
			DateUtil.getCurrDBFormatDate()});
	}
	
	public void updateBuyerBaseProfile(HashMap baseprofile){
		StatusObj sb=DbUtil.executeUpdateQuery("insert into buyer_base_info(fname,lname,email," +
				"phone,profileid,profilekey,transactionid,eventid,created_at,profilestatus)" +
				" values (?,?,?,?,to_number(?,'999999999999'),?,?,CAST(? AS BIGINT),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),'Completed')",
				new String[]{(String)baseprofile.get("fname"),(String)baseprofile.get("lname"),
				(String)baseprofile.get("email"),(String)baseprofile.get("phone"),
				(String)baseprofile.get("profileid"),(String)baseprofile.get("profilekey"),
				(String)baseprofile.get("tid"),(String)baseprofile.get("eventid"),DateUtil.getCurrDBFormatDate()});
		
		
		}
	public void updateTransactionProfile(HashMap baseprofile){
	
	
		String query=" update event_reg_transactions set fname=?, lname=?, email=?, phone=? " +
				         " where tid=? ";
		StatusObj statobj=DbUtil.executeUpdateQuery(query,new String[]{(String)baseprofile.get("fname"),
				(String)baseprofile.get("lname"),(String)baseprofile.get("email"),
				(String)baseprofile.get("phone"),(String)baseprofile.get("tid")});
		System.out.println("status of update transactionProfile "+statobj.getStatus());
	}
	
	public void InsertResponseMaster(HashMap responseMaterMap){
	String response_master="insert into custom_questions_response_master(transactionid,attribsetid," +
			"ref_id,subgroupid,groupid,profileid,profilekey,created)" +
			" values(?,?,?,CAST(? AS BIGINT),CAST(? AS BIGINT)," +
			"CAST(? AS BIGINT),?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
	StatusObj s=DbUtil.executeUpdateQuery(response_master,new String[]{(String)responseMaterMap.get("tid"),
			(String)responseMaterMap.get("custom_setid"),(String)responseMaterMap.get("responseid"),
			(String)responseMaterMap.get("ticketid"),(String)responseMaterMap.get("eventid"),
			(String)responseMaterMap.get("profileid"),(String)responseMaterMap.get("profilekey"),
			DateUtil.getCurrDBFormatDate()});
	}

	public void insertResponse(HashMap respnseMap){	
		//System.out.println("respnseMap::::::"+respnseMap);
	StatusObj sb1=DbUtil.executeUpdateQuery("insert into custom_questions_response(ref_id,attribid," +
			"created,shortresponse,bigresponse,question_original) " +
			"values(?,to_number(?,'9999999999'),now(),?,?,?)",
			new String[]{(String)respnseMap.get("responseid"),(String)respnseMap.get("questionid"),
			(String)respnseMap.get("shortresponse"),(String)respnseMap.get("bigresponse"),
			(String)respnseMap.get("question")});
	}

	public void InserBuyerInfo(HashMap buyerInfo){
		
	StatusObj sb=DbUtil.executeUpdateQuery("insert into buyer_base_info(fname,lname,email,phone,profileid," +
			"profilekey,transactionid,eventid,created_at) " +
			"values (?,?,?,?,to_number(?,'999999999999'),?,?,CAST(? AS BIGINT),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))",
			new String[]{(String)buyerInfo.get("fname"),(String)buyerInfo.get("lname"),
			(String)buyerInfo.get("email"),(String)buyerInfo.get("phone"),
			(String)buyerInfo.get("profileid"),(String)buyerInfo.get("profilekey"),
			(String)buyerInfo.get("tid"),(String)buyerInfo.get("eventid"),DateUtil.getCurrDBFormatDate()});
	}

	public String [] getOptionVal(ArrayList options,String responses[]){
	ArrayList responseList=new ArrayList();
	try{
	List als=Arrays.asList(responses);
	if(options!=null){
	for(int p=0;p<options.size();p++){
	AttribOption attribop=(AttribOption)options.get(p);
	String optionid=attribop.getOptionid();
	if(als.contains(optionid)){
	responseList.add(attribop.getOptionValue());
	}
	}
	}
	}
	catch(Exception e){

	}
	return (String[])responseList.toArray(new String[responseList.size()]);
	}
	
	public boolean deleteProfileData(String pid){
			StatusObj sb=DbUtil.executeUpdateQuery("delete from profile_base_info where profilekey=?",new String[]{pid});
	
	return sb.getStatus();
	}
	public boolean deleteBuyerProfileData(String pid){
		StatusObj sb=DbUtil.executeUpdateQuery("delete from buyer_base_info where profilekey=?",new String[]{pid});

		return sb.getStatus();
	}
	
	public boolean clearResponse(String responseid,String qid){
		StatusObj sb=DbUtil.executeUpdateQuery("delete from custom_questions_response where ref_id=? and attribid=to_number(?,'9999999999')",new String[]{responseid,qid});
		return sb.getStatus();
	}
	
	public static void responseMasterInsert(HashMap detailsMap,String eid,String responseid){
		String setId=DbUtil.getVal("select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose=?",new String[] {eid,"EVENT"});

		HashMap profileresponseMaster=new HashMap();
		 profileresponseMaster.put("tid", (String)detailsMap.get("tid"));
		 profileresponseMaster.put("profileid",(String)detailsMap.get("profileid"));
		 profileresponseMaster.put("profilekey",(String)detailsMap.get("profilekey"));
		 profileresponseMaster.put("responseid",responseid);
		 profileresponseMaster.put("ticketid",(String)detailsMap.get("ticketid"));
		 profileresponseMaster.put("eventid",eid);
		 profileresponseMaster.put("custom_setid",setId);
		 //System.out.println("profileresponseMaster"+profileresponseMaster);
			ProfileActionDB profiledb=new ProfileActionDB();
			profiledb.InsertResponseMaster(profileresponseMaster);
	}
	public void updateNonAttendeeTicketsprofiles(String tid,String eid,HashMap seatingtickets){
		
		String query="select ticketid,qty,tickettype from event_reg_ticket_details_temp where ticketid not in (select ticketid from profile_base_info where transactionid=?) and tid=?";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query,new String[]{tid,tid});
		try{
		if(sb.getStatus()){
			HashMap basicProfile=new HashMap();
			for(int i=0;i<sb.getCount();i++){
			String ticketid=db.getValue(i,"ticketid","");
			String tickettype=db.getValue(i,"tickettype","");	
			String qty=db.getValue(i,"qty","0");
			String seatcode="";
			String seatindex="";
			int count=Integer.parseInt(qty);
			String attendeeids[]=DbUtil.getSeqVals("seq_attendeeId",count);
			for(int p=0;p<count;p++){
				String attendeeKey="AK"+EncodeNum.encodeNum(attendeeids[p]).toUpperCase();
				try{
					ArrayList seatcodes=(ArrayList) seatingtickets.get(ticketid);
					ArrayList seatindeces=(ArrayList) seatingtickets.get(ticketid+"_index");
					seatcode=(String)seatcodes.get(0);
					seatindex=(String)seatindeces.get(0);
					seatcodes.remove(0);
					seatindeces.remove(0);
				
				}catch(Exception e){
					System.out.println("caught exception");
					seatcode="";
				}
				basicProfile.put("fname","");
				basicProfile.put("lname","");
				basicProfile.put("email","");
				basicProfile.put("phone","");

				basicProfile.put("profileid",attendeeids[p]);
				basicProfile.put("profilekey",attendeeKey);
				basicProfile.put("eventid",eid);
				basicProfile.put("tid",tid);
				basicProfile.put("ticketid",ticketid);
				basicProfile.put("tickettype",tickettype);
				basicProfile.put("seatcode", seatcode);
				basicProfile.put("seatindex", seatindex);
				updateBaseProfile(basicProfile,eid);
			}
			}
		}
		}
					
			
			
			
			
		catch(Exception e){
		}
}
	public void deletePreviousData(String tid){
		DbUtil.executeUpdateQuery("delete from custom_questions_response where ref_id in(select ref_id from custom_questions_response_master where transactionid=?)",new String[]{tid});
		DbUtil.executeUpdateQuery("delete from custom_questions_response_master where transactionid=?",new String[]{tid});
		DbUtil.executeUpdateQuery("delete from buyer_base_info where transactionid=?", new String[]{tid});
		DbUtil.executeUpdateQuery("delete from profile_base_info where transactionid=?", new String[]{tid});
	}
}