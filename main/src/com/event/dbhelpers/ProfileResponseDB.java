package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import com.eventbee.general.DbUtil;
import com.eventbee.general.DBManager;
import com.eventbee.general.StatusObj;

public class ProfileResponseDB {
	
public static HashMap getResponses(String pid){
		HashMap hm=new HashMap();
	String query= "select * from profile_base_info where profilekey=?";
	DBManager db=new DBManager();
	StatusObj sb=db.executeSelectQuery(query,new String[]{pid});
	if(sb.getStatus()){

	hm.put("fname",db.getValue(0,"fname",""));
	hm.put("lname",db.getValue(0,"lname",""));
	hm.put("email",db.getValue(0,"email",""));
	hm.put("phone",db.getValue(0,"phone",""));
	hm.put("profileid",db.getValue(0,"profileid",""));
	hm.put("profilekey",db.getValue(0,"profilekey",""));
	hm.put("ticketid",db.getValue(0,"ticketid",""));
	hm.put("tickettype",db.getValue(0,"tickettype",""));
	hm.put("tid",db.getValue(0,"transactionid",""));
	hm.put("eventid",db.getValue(0,"eventid",""));
	hm.put("seatcode",db.getValue(0,"seatcode",""));
	hm.put("seatindex", db.getValue(0,"seatindex",""));
		}
	return hm;
 }
public static HashMap getBuyerResponses(String pid){
	HashMap hm=new HashMap();
String query= "select * from buyer_base_info where profilekey=?";
DBManager db=new DBManager();
StatusObj sb=db.executeSelectQuery(query,new String[]{pid});
if(sb.getStatus()){

hm.put("fname",db.getValue(0,"fname",""));
hm.put("lname",db.getValue(0,"lname",""));
hm.put("email",db.getValue(0,"email",""));
hm.put("phone",db.getValue(0,"phone",""));
hm.put("profileid",db.getValue(0,"profileid",""));
hm.put("profilekey",db.getValue(0,"profilekey",""));
hm.put("tid",db.getValue(0,"transactionid",""));
hm.put("eventid",db.getValue(0,"eventid",""));
	}
return hm;
}

 public static ArrayList getCustomReponses(String pid){
	ArrayList customArrayList =new ArrayList();
	String query="select attribid,shortresponse from custom_questions_response cr,custom_questions_response_master cm where cm.ref_id=cr.ref_id and cm.profilekey=?";
	DBManager db=new DBManager();
	StatusObj sb=db.executeSelectQuery(query, new String[]{pid});
	if(sb.getStatus()){
		for(int i=0;i<sb.getCount();i++){
			HashMap hm=new HashMap();
			hm.put("qid",db.getValueFromRecord(i,"attribid",""));
			hm.put("response",db.getValueFromRecord(i,"shortresponse",""));
			customArrayList.add(hm);
		}
	}
	return  customArrayList;
 }
 
 
 public static boolean  updateBasicProfile(HashMap profileMap){
	 
	 StatusObj sb=DbUtil.executeUpdateQuery("update profile_base_info set fname=?,lname=?,email=?,phone=? where profilekey=?",new String[]{(String)profileMap.get("fname"),(String)profileMap.get("lname"),(String)profileMap.get("email"),(String)profileMap.get("phone"),(String)profileMap.get("pkey")});  
 
 return sb.getStatus();
 }
 public static String  getProfileResponseId(String profilekey){
	HashMap hmap=new HashMap();
	String responseid="";
	String Query="select ref_id  from custom_questions_response_master where profilekey=?";
	DBManager db=new DBManager();
	StatusObj sb=db.executeSelectQuery(Query, new String[]{profilekey});
	if(sb.getStatus()){
		responseid=db.getValue(0,"ref_id","");
	}
	return responseid;
 }
 
}
