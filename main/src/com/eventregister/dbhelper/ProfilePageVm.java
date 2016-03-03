package com.eventregister.dbhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.eventbee.general.DBManager;
import com.eventbee.general.StatusObj;
import com.eventregister.TicketsDB;
import com.eventregister.customquestions.beans.CustomAttribSet;
import com.eventregister.customquestions.beans.CustomAttribute;
import com.eventregister.customquestions.dbhelper.CustomAttribsDB;

public class ProfilePageVm {
	
	final String GET_TICKETS="select ticketid,ticketname,qty,tickettype  from transaction_tickets t,profile_base_info p where t.tid=t.transactionid and eid=? and tid=?";
	
	public HashMap getProfilePageVmObjects(String tid,String eid){
		RegistrationTiketingManager regTktMgr=new RegistrationTiketingManager();
		TicketsDB ticketInfo=new TicketsDB();
		HashMap ticketspecificAttributeIds=null;
		CustomAttribsDB ticketcustomattribs=new CustomAttribsDB();
		ArrayList selectedTickets=new ArrayList();
		selectedTickets=regTktMgr.getSelectedTickets(eid,tid);
		Vector attribs=new Vector();
		HashMap attribMap=new HashMap();
		String arribsetid=null;
		try{
		CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribs.getCustomAttribSet(eid,"EVENT" );
		CustomAttribute[]  attributeSet=customattribs.getAttributes();
		arribsetid=customattribs.getAttribSetid();
		if(selectedTickets!=null&&selectedTickets.size()>0){
		for(int i=0;i<selectedTickets.size();i++){
		HashMap profileMap=(HashMap)selectedTickets.get(i);
		String selecedticket=(String)profileMap.get("selectedTicket");
		Vector <HashMap>questions=new Vector<HashMap>();
		Vector p=getAttendeeObject(selecedticket,eid);
		questions.addAll(p);
		ticketspecificAttributeIds=ticketcustomattribs.getTicketLevelAttributes(eid);
		if(ticketspecificAttributeIds!=null&&ticketspecificAttributeIds.containsKey(selecedticket))
		{
		ArrayList al=null;
		al=(ArrayList)ticketspecificAttributeIds.get(selecedticket);
		if(attributeSet!=null&&attributeSet.length>0){
		for(int pindex=0;pindex<al.size();pindex++){
		String qid=(String)al.get(pindex);
		for(int k=0;k<attributeSet.length;k++){
		boolean noattribs=false;
		HashMap customMap=new HashMap();
		CustomAttribute cb=(CustomAttribute)attributeSet[k];
		if(qid.equals(cb.getAttribId())){
		customMap.put("qType",cb.getAttributeType());
		customMap.put("qId",cb.getAttribId());
		questions.add(customMap);
		}
		}
		}
		}
		}

		if(questions!=null&&questions.size()>0){
		profileMap.put("questions",questions);
		attribs.add(profileMap);
		}
		}
		}
		attribMap.put("customProfile",attribs);


		HashMap byerMap=new HashMap();
		Vector buyerQues=getAttendeeObject("0",eid);
		ArrayList buyerAttribs=null;
		buyerAttribs=regTktMgr.getBuyerSpecificAttribs(eid);
		if(attributeSet!=null&&attributeSet.length>0){
		for(int k=0;k<attributeSet.length;k++){
		ArrayList al=null;
		CustomAttribute cb=(CustomAttribute)attributeSet[k];
		if(buyerAttribs!=null&&buyerAttribs.contains(cb.getAttribId())){
		HashMap customattib=new HashMap();
		customattib.put("qId",cb.getAttribId());
		buyerQues.add(customattib);
		}
		}
		}
		byerMap.put("buyerQues",buyerQues);
		attribMap.put("buyer",byerMap);
		attribMap.put("arribsetid",arribsetid);
		}
		catch(Exception e){
		}
		return attribMap;
		}

		

		HashMap <String,String>getfieldMap(String field){
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("qId",field);
		return map;
		}

	public 	Vector getAttendeeObject(String ticketid,String eid){
		Vector v=new Vector();
		HashMap <String,String>attribMap=getAttribsForTickets(ticketid,eid);
		if(attribMap==null)
			attribMap=new HashMap();
			List<String> list=new ArrayList<String>();
			if("0".equals(ticketid)||attribMap.containsKey("fname"))	
			list.add("fname");
			if("0".equals(ticketid)||attribMap.containsKey("lname"))	
			list.add("lname");
			if("0".equals(ticketid)||attribMap.containsKey("email"))	
			list.add("email");
		   	if(attribMap.containsKey("phone"))	
			 list.add("phone");
			
		
			for(int i=0;i<list.size();i++){
			HashMap <String,String>hm=getfieldMap(list.get(i));
			v.add(hm);
	     
		
		}
		return v;
		}

public HashMap <String,Vector> getBaseQuestionsSettings(String eid){
HashMap<String,Vector> settingsMap=new HashMap<String,Vector>();	
String query="select contextid,attribid,isrequired from base_profile_questions where groupid=CAST(? AS BIGINT)";
DBManager db=new DBManager();
StatusObj sb=db.executeSelectQuery(query, new String []{eid});
if(sb.getStatus()){
	Vector al=null;
for(int i=0;i<sb.getCount();i++){
HashMap hm=new HashMap();
hm.put("qId",db.getValue(i,"attribid",""));
hm.put("isRequired",db.getValue(i,"isrequired",""));
if(settingsMap.get(db.getValue(i,"contextid",""))!=null){
	al=(Vector)settingsMap.get(db.getValue(i,"contextid",""));
	al.add(hm);
}
else{
al=new Vector();
al.add(hm);
settingsMap.put(db.getValue(i,"contextid",""), al);
}
}
}
return settingsMap;
}
		
public HashMap getAttribsForTickets(String ticketid,String groupid){
HashMap hm=new HashMap<String,String>();
try{
String query="select attribid,isrequired from base_profile_questions where contextid=to_number(?,'9999999999') and groupid=CAST(? AS BIGINT)";
DBManager db=new DBManager();
StatusObj sb=db.executeSelectQuery(query, new String[]{ticketid,groupid});
if(sb.getStatus()){
for(int i=0;i<sb.getCount();i++){
hm.put(db.getValueFromRecord(i,"attribid",""),db.getValueFromRecord(i,"isrequired",""));	
}
}
}
catch(Exception e){
System.out.println("Exception Occured in getAttribsForTickets"+e.getMessage());
}
return hm;
}
public HashMap getAttribsForBuyer(String groupid){
	HashMap hm=new HashMap<String,String>();
	try{
	String query="select attribid,isrequired from base_profile_questions where  groupid=CAST(? AS BIGINT)";
	DBManager db=new DBManager();
	StatusObj sb=db.executeSelectQuery(query, new String[]{groupid});
	if(sb.getStatus()){
	for(int i=0;i<sb.getCount();i++){
	hm.put(db.getValueFromRecord(i,"attribid",""),db.getValueFromRecord(i,"isrequired",""));	
	}
	}
	}
	catch(Exception e){
	System.out.println("Exception Occured in getAttribsForTickets"+e.getMessage());
	}
	return hm;
	}

public ArrayList <HashMap> getRegisteredTickets(String eid,String tid){
	ArrayList  <HashMap>ticketsList=new ArrayList();
    try{
	DBManager dbmanager=new DBManager();
	StatusObj sb=dbmanager.executeSelectQuery(GET_TICKETS,new String []{eid,tid});
	if(sb.getStatus())
	{
	for(int index=0;index<sb.getCount();index++){
	HashMap ticketMap=new HashMap();
	ticketMap.put("ticketName",dbmanager.getValue(index,"ticketname",""));
	ticketMap.put("selectedTicket",dbmanager.getValue(index,"ticketid",""));
	ticketMap.put("qty",dbmanager.getValue(index,"qty",""));
	ticketMap.put("type",dbmanager.getValue(index,"tickettype",""));
	ticketsList.add(ticketMap);
	}
	}
    }
    catch(Exception e){
	System.out.println("Exception in getSelectedTickets()"+e.getMessage());
	}
	return ticketsList;
	}





}
