package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.event.beans.CustomAttribute;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.namedquery.NQDbHelper;
import com.eventbee.namedquery.NQDbUtil;
import com.eventbee.namedquery.NQStatusObj;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class CustomAttributesDB {
	
	private static Logger log = Logger.getLogger(CustomAttributesDB.class);
	public final static String CUSTOM_ATTRIB_TICKETS="select attribid,subgroupid from subgroupattribs where groupid =?";
	public final static String INSERT_SUBGROUP_ATTRIBS="insert into subgroupattribs(groupid,subgroupid,attribsetid,attribid) values (?,?,?,?)";
	public final static String DELETE_SUBGROUP_ATTRIB="delete from subgroupattribs where groupid=CAST(? AS BIGINT) and attribid=CAST(? AS INTEGER) and subgroupid=CAST(? AS INTEGER)";
    public  static ArrayList getTicketLevelBaseProfileSettings(String eid, String attribid){
    	ArrayList baseProfileSettings=new ArrayList();
    	String mappedAttribId=attribid;
    	if("-5".equals(attribid)) mappedAttribId="fname";
    	if("-6".equals(attribid)) mappedAttribId="lname";
    	if("-7".equals(attribid)) mappedAttribId="email";
    	if("-8".equals(attribid)) mappedAttribId="phone";
    	DBManager dbmanager = new DBManager();
		StatusObj statobj = dbmanager.executeSelectQuery("select contextid, isrequired from base_profile_questions " +
		    	" where groupid=CAST(? AS BIGINT) and attribid=? and contextid<>0", new String[]{eid, mappedAttribId});
		if (statobj != null && statobj.getStatus()) {
			int recordcounttodata = statobj.getCount();
			for (int i = 0; i < recordcounttodata; i++) {
				HashMap settings=new HashMap();
				settings.put("isrequired", dbmanager.getValue(i, "isrequired", ""));
				settings.put("tid", dbmanager.getValue(i, "contextid", ""));
				baseProfileSettings.add(settings);
			}
		}
    			
    	return baseProfileSettings;
    }
    
    
    public static void deleteBaseQuestionTickets(String eid,String attribid){
    	String mappedAttribId=attribid;
    	if("-5".equals(attribid)) mappedAttribId="fname";
    	if("-6".equals(attribid)) mappedAttribId="lname";
    	if("-7".equals(attribid)) mappedAttribId="email";
    	if("-8".equals(attribid)) mappedAttribId="phone";
    	String deleteQuery="delete from base_profile_questions where groupid=CAST(? AS BIGINT) and attribid=? and contextid<>0 ";
    	DbUtil.executeUpdateQuery(deleteQuery, new String[]{eid,mappedAttribId});
		
    }
    
    
    public  static ArrayList getRSVPResponseLevelBaseProfileSettings(String eid, String attribid){
    	ArrayList baseProfileSettings=new ArrayList();
    	String mappedAttribId=attribid;
    	//if("-5".equals(attribid)) mappedAttribId="fname";
    	//if("-6".equals(attribid)) mappedAttribId="lname";
    	if("-11".equals(attribid)) mappedAttribId="email";
    	if("-12".equals(attribid)) mappedAttribId="phone";
    	System.out.println("in get method"+mappedAttribId+" "+eid);
    	DBManager dbmanager = new DBManager();
		StatusObj statobj = dbmanager.executeSelectQuery("select contextid, isrequired from base_profile_questions " +
		    	" where groupid=CAST(? AS BIGINT) and attribid=? and contextid in (101,102)", new String[]{eid, mappedAttribId});
		if (statobj != null && statobj.getStatus()) {
			int recordcounttodata = statobj.getCount();
			for (int i = 0; i < recordcounttodata; i++) {
				HashMap settings=new HashMap();
				settings.put("isrequired", dbmanager.getValue(i, "isrequired", ""));
					String tid=dbmanager.getValue(i, "contextid", "");
					if(tid.equals("101")) tid="yes";
					if(tid.equals("102")) tid="notsure";
				settings.put("tid", tid);
				baseProfileSettings.add(settings);
			}
		}
    			
    	return baseProfileSettings;
    }
    public static void saveTicketBaseProfileSettings(String eid, String attributeid, List seltickets, ArrayList isReqSettingsMap){
    	String mappedAttribId="";
    	String isReq="Y";
        if("-5".equals(attributeid)) mappedAttribId="fname";
    	if("-6".equals(attributeid)) mappedAttribId="lname";
    	if("-7".equals(attributeid)) mappedAttribId="email";
    	if("-8".equals(attributeid)) mappedAttribId="phone";
    	DbUtil.executeUpdateQuery("delete from base_profile_questions where groupid=CAST(? AS BIGINT) and contextid<>0 and attribid=?",new String[]{eid, mappedAttribId});
        HashMap reqMap=new HashMap();
        ArrayList<String> totaltktsList=new ArrayList<String>();
        ArrayList<String> unselecttktsList=new ArrayList<String>();
    	if(isReqSettingsMap!=null ){
			for(int j=0;j<isReqSettingsMap.size();j++){
			String isReqVal=((String[])isReqSettingsMap.get(j))[0];
			String[] tktarr=GenUtil.strToArrayStr(isReqVal, "_");
			totaltktsList.add(tktarr[1].trim());
			reqMap.put(isReqVal.substring(2), isReqVal.substring(0, 1));
			}
		}
    	System.out.println("the reuired maps ::"+reqMap);
    	if(seltickets==null){
            if("fname".equals(mappedAttribId) || "lname".equals(mappedAttribId))
    		updatePriceIsAteendee(eid,mappedAttribId,null,"notkts");
    	 return;
    	}else{
    		System.out.println("totaltktsList are:"+totaltktsList); 
    		System.out.println("seltickets are:"+seltickets);
    		for(int i=0;i<totaltktsList.size();i++){
        		if(!seltickets.contains(totaltktsList.get(i)))
        			unselecttktsList.add(totaltktsList.get(i));
        	}
    		System.out.println("unselecttktsList are:"+unselecttktsList);
    		ArrayList<String> nonexisttktids=new ArrayList<String>();
    		ArrayList<String> selecttktids=new ArrayList<String>();
    		String chkentry="",attribid="";
    		if("fname".equals(mappedAttribId))
    			attribid="lname";
    		else if("lname".equals(mappedAttribId))
    			attribid="fname";
    	   for(int i=0;i<seltickets.size();i++){
    		if(reqMap.containsKey(seltickets.get(i))) isReq=(String)reqMap.get(seltickets.get(i));
    		saveTicketBaseProfileSettings(eid,mappedAttribId, (String)seltickets.get(i),isReq);
    		selecttktids.add((String)seltickets.get(i));
    		}
    	   if(selecttktids.size()>0 && ("fname".equals(mappedAttribId) || "lname".equals(mappedAttribId)))
    	   updatePriceIsAteendee(eid,mappedAttribId,selecttktids,"selecttkts");
    	   
    	   for(int i=0;i<unselecttktsList.size();i++){
    	   chkentry=chkEntryForAttribId(eid,unselecttktsList.get(i),attribid);
   		   if(!"Y".equals(chkentry))nonexisttktids.add(unselecttktsList.get(i));
   		   }
    	   if(nonexisttktids.size()>0 && ("fname".equals(mappedAttribId) || "lname".equals(mappedAttribId)))
    	   updatePriceIsAteendee(eid,mappedAttribId,nonexisttktids,"unselecttkts");
    	}	
    }
    
    
    
    
    
    public static void saveResponseBaseProfileSettings(String eid, String attributeid, List rsvpStatusOptionsList, ArrayList isReqSettingsMap){
    	String mappedAttribId="";
    	String isReq="Y";
    	//if("-5".equals(attributeid)) mappedAttribId="fname";
    	//if("-6".equals(attributeid)) mappedAttribId="lname";
    	if("-11".equals(attributeid)) mappedAttribId="email";
    	if("-12".equals(attributeid)) mappedAttribId="phone";
    	DbUtil.executeUpdateQuery("delete from base_profile_questions where groupid=CAST(? AS BIGINT) and contextid in (101,102) and attribid=?",new String[]{eid, mappedAttribId});
    	HashMap reqMap=new HashMap();
    	if(isReqSettingsMap!=null ){
			for(int j=0;j<isReqSettingsMap.size();j++){
			String isReqVal=((String[])isReqSettingsMap.get(j))[0];
			reqMap.put(isReqVal.substring(2), isReqVal.substring(0, 1));
			}
		}
    	if(rsvpStatusOptionsList==null) return;
    	for(int i=0;i<rsvpStatusOptionsList.size();i++){
    		String contextid="";
     		if(reqMap.containsKey(rsvpStatusOptionsList.get(i))) isReq=(String)reqMap.get(rsvpStatusOptionsList.get(i));
    		if(rsvpStatusOptionsList.get(i).equals("yes"))
    			contextid="101";
    		if(rsvpStatusOptionsList.get(i).equals("notsure"))
    			contextid="102";
    		saveTicketBaseProfileSettings(eid,mappedAttribId, contextid,isReq);
    	}
    }
    public static void saveTicketBaseProfileSettings(String eid, String attributeid, String ticketId, String isReq){
    	String deleteqry="delete from  base_profile_questions where groupid=CAST(? AS BIGINT) and contextid=CAST(? as INTEGER)"+
    	                 " and attribid=?";
    	DbUtil.executeUpdateQuery(deleteqry,new String[]{eid,ticketId,attributeid});
    	String insertquery="insert into base_profile_questions (groupid,contextid, isrequired, attribid) " +
    			" values(CAST(? AS BIGINT), CAST(? AS INTEGER), ?, ?)";
    	DbUtil.executeUpdateQuery(insertquery,new String[]{eid,ticketId,isReq, attributeid});
    }
    public static void deleteTicketBaseProfileSettings(String eid,String ticketId){
    	String deletequery="delete from base_profile_questions where groupid=CAST(? AS BIGINT) and contextid=CAST(? as INTEGER)"+
    	                   " and attribid in('fname','lname')";
    	DbUtil.executeUpdateQuery(deletequery,new String[]{eid,ticketId});
    }
    
    public static void saveResponseBaseProfileSettings(String eid, String attributeid, String ticketId, String isReq){
    	String insertquery="insert into base_profile_questions (groupid,contextid, isrequired, attribid) " +
    			" values(CAST(? AS BIGINT), CAST(? AS INTEGER), ?, ?)";
    	DbUtil.executeUpdateQuery(insertquery,new String[]{eid,ticketId,isReq, attributeid});
    }
    public static void savePhoneQuestionSettings(String eid, String displayPhoneYN, String isReq){
    	DbUtil.executeUpdateQuery("delete from base_profile_questions where groupid=CAST(? AS BIGINT) and contextid=0 and attribid='phone'",new String[]{eid});
    	if("Y".equals(displayPhoneYN)){
    	String insertquery="insert into base_profile_questions (groupid,contextid, isrequired, attribid) " +
    			" values(CAST(? AS BIGINT), 0, ?, 'phone')";
    	DbUtil.executeUpdateQuery(insertquery,new String[]{eid,isReq});
    	}
    }
    
    public static void saveCustomQuestionSettings(String eid,String type,String qid){
    	String attribsetid=DbUtil.getVal("select attrib_setid from custom_attrib_master " +
				" where purpose='EVENT' and groupid=CAST(? AS BIGINT)", new String[]{eid});
    	String updateQuery="update custom_attribs set isrequired=?  where attrib_id=CAST(? AS INTEGER) " +
				"and attrib_setid =CAST(? AS INTEGER)";
		DbUtil.executeUpdateQuery(updateQuery, new String[]{type,qid,attribsetid});
    }
    
    
    public static HashMap getTransactionLevelBaseProfileSettings(String eid){
    	HashMap basicProfileSettingsMap=new HashMap();
    	String collectinstatus=DbUtil.getVal("select isrequired from base_profile_questions where groupid=CAST(? AS BIGINT) and contextid=0 and attribid='phone'", new String[]{eid});
    	if(collectinstatus==null) collectinstatus="Hide";
    	basicProfileSettingsMap.put("phone", collectinstatus);
    	return basicProfileSettingsMap;
    }
	
	public static List<CustomAttribute> getTransactionLevelCustomAttribs(String eid,String purpose){
		List<CustomAttribute> list = new ArrayList<CustomAttribute>();
		try {
			String query = "select attribname,attribtype,isrequired,textboxsize,rows,cols,b.attrib_id," +
					"b.attrib_setid,b.sub_qns_count from custom_attrib_master a,custom_attribs b where " +
					"a.attrib_setid=b.attrib_setid and a.attrib_setid=(select attrib_setid from " +
					"custom_attrib_master where" 
					+ " groupid=CAST(? AS BIGINT) and purpose=?)"
					+ "and b.attrib_id in (select attribid from buyer_custom_questions "
					+ "where eventid=CAST(? AS BIGINT)) and (subquestionof is null or subquestionof='no') order by position";
			DBManager dbmanager = new DBManager();
			StatusObj statobj = dbmanager.executeSelectQuery(query,new String[] { eid, purpose,eid });
			if (statobj != null && statobj.getStatus()) {
				int recordcounttodata = statobj.getCount();
				for (int i = 0; i < recordcounttodata; i++) {
					CustomAttribute ca = new CustomAttribute();
					ca.setName(dbmanager.getValue(i, "attribname", ""));
					ca.setQtype(dbmanager.getValue(i, "attribtype", ""));
					ca.setIsRequired(dbmanager.getValue(i, "isrequired", ""));
					ca.setAttribid(dbmanager.getValue(i, "attrib_id", ""));
					ca.setSubQnsCount(dbmanager.getValue(i, "sub_qns_count", ""));
					list.add(ca);
				}
			}
			return list;
		} catch (Exception e) {
			
		}
		return list;
	}
	public static List<CustomAttribute> getTicketLevelCustomAttribs(String eid,String purpose){
		List<CustomAttribute> list = new ArrayList<CustomAttribute>();
		try {
			String query = "select attribname,attribtype,isrequired,textboxsize,rows,cols,b.attrib_id,b.attrib_setid,b.sub_qns_count from "
					+ "custom_attrib_master a,custom_attribs b where a.attrib_setid=b.attrib_setid and "
					+ "a.attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose=?)"
					+ "and b.attrib_id not in (select attribid from buyer_custom_questions "
					+ "where eventid=CAST(? AS BIGINT)) and (b.subquestionof is null or b.subquestionof='no') order by position";
			DBManager dbmanager = new DBManager();
			StatusObj statobj = dbmanager.executeSelectQuery(query,new String[] { eid, purpose,eid });
			if (statobj != null && statobj.getStatus()) {
				int recordcounttodata = statobj.getCount();
				for (int i = 0; i < recordcounttodata; i++) {
					CustomAttribute ca = new CustomAttribute();
					ca.setName(dbmanager.getValue(i, "attribname", ""));
					ca.setQtype(dbmanager.getValue(i, "attribtype", ""));
					ca.setAttribid(dbmanager.getValue(i, "attrib_id", ""));
					ca.setIsRequired(dbmanager.getValue(i, "isrequired", ""));
					ca.setSubQnsCount(dbmanager.getValue(i, "sub_qns_count", ""));
					list.add(ca);
				}
			}
			return list;
		} catch (Exception e) {
			
		}
		return list;
	}
	
	public static JSONArray getSubCustomAttribs(String eid,String attribId,String attribOptionId){
		JSONArray jsonarray=new JSONArray();
		try {
			
			String subquestionof=attribId+"_"+attribOptionId;
						
			String query="select attribname,attribtype,isrequired,textboxsize,rows,cols,attrib_id,attrib_setid from custom_attribs"
					+" where attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose='EVENT')"
					+" and subquestionof='"+subquestionof+"' order by position";
			
			
			DBManager dbmanager = new DBManager();
			StatusObj statobj = dbmanager.executeSelectQuery(query,new String[] { eid });
			if (statobj != null && statobj.getStatus()) {
				int recordcounttodata = statobj.getCount();
				for (int i = 0; i < recordcounttodata; i++) {
					JSONObject json = new JSONObject();
					json.put("attribid", dbmanager.getValue(i, "attrib_id", ""));
					json.put("name", dbmanager.getValue(i, "attribname", ""));
					json.put("reqoptional", dbmanager.getValue(i, "isrequired", ""));
					json.put("size", dbmanager.getValue(i, "textboxsize", ""));
					json.put("rows", dbmanager.getValue(i, "rows", ""));
					json.put("columns", dbmanager.getValue(i, "cols", ""));
					String attribtype=dbmanager.getValue(i, "attribtype", "");
					json.put("type", attribtype);
					json.put("multiple", false);
					if(!"text".equalsIgnoreCase(attribtype) && !"textarea".equalsIgnoreCase(attribtype)){
						
						json.put("type", "selection");
						if("checkbox".equalsIgnoreCase(attribtype)){
							json.put("qtype", "single");
							json.put("multiple", true);
						}else if("radio".equalsIgnoreCase(attribtype))
							json.put("qtype", "single");
						else if("select".equalsIgnoreCase(attribtype))
							json.put("qtype", "pulldown");
						
						HashMap pinparams = new HashMap();
						pinparams.put("groupid", eid);
						pinparams.put("purpose", "EVENT");
						pinparams.put("attrib_id", dbmanager.getValue(i, "attrib_id", ""));
						NQDbHelper dbhelper = new NQDbHelper();
						List dblist = dbhelper.getDataList("selectcustomattriboptions", pinparams);
						System.out.println("getting selectcustomattriboptions ");
						
						if (dblist != null) {
							
							System.out.println("selectcustomattriboptions count: "+dblist.size());
							JSONArray optionsArray=new JSONArray();
							for (int j = 0; j < dblist.size(); j++){ 
								JSONObject optionJson=new JSONObject();
								HashMap hmap = (HashMap) dblist.get(j);
								optionJson.put("key",hmap.get("option").toString());
								optionJson.put("value",hmap.get("option_val").toString());
								optionsArray.put(optionJson);
							}
							json.put("answers", optionsArray);
						}
						
					}
					jsonarray.put(json);
				}
			}
			return jsonarray;
		} catch (Exception e) {
			
		}
		return jsonarray;
	}
	
	public static List<CustomAttribute> getResponseLevelCustomAttribs(String eid,String purpose){
		List<CustomAttribute> list = new ArrayList<CustomAttribute>();
		try {
			String query = "select attribname,attribtype,isrequired,textboxsize,rows,cols,b.attrib_id,b.attrib_setid from "
					+ "custom_attrib_master a,custom_attribs b where a.attrib_setid=b.attrib_setid and "
					+ "a.attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose=?)"
					+ "and b.attrib_id not in (select attribid from buyer_custom_questions "
					+ "where eventid=CAST(? AS BIGINT)) order by position";
			DBManager dbmanager = new DBManager();
			StatusObj statobj = dbmanager.executeSelectQuery(query,new String[] { eid, purpose,eid });
			if (statobj != null && statobj.getStatus()) {
				int recordcounttodata = statobj.getCount();
				for (int i = 0; i < recordcounttodata; i++) {
					CustomAttribute ca = new CustomAttribute();
					ca.setName(dbmanager.getValue(i, "attribname", ""));
					ca.setQtype(dbmanager.getValue(i, "attribtype", ""));
					ca.setAttribid(dbmanager.getValue(i, "attrib_id", ""));
					ca.setIsRequired(dbmanager.getValue(i, "isrequired", ""));
					list.add(ca);
				}
			}
			return list;
		} catch (Exception e) {
			
		}
		return list;
	}
	
	public static List<CustomAttribute> getCustomAttributes(String eid,String purpose) {
		List<CustomAttribute> list = new ArrayList<CustomAttribute>();
		try {
			NQDbHelper dbhelper = new NQDbHelper();
			HashMap pInParams = new HashMap();
			pInParams.put("groupid", eid);
			pInParams.put("purpose", purpose);
			List dblist = dbhelper.getDataList("getattributes", pInParams);
			if (dblist != null) {
				for (int i = 0; i < dblist.size(); i++) {
					HashMap hmap = (HashMap) dblist.get(i);
					CustomAttribute ca = new CustomAttribute();
					ca.setName(hmap.get("attribname").toString());
					ca.setQtype(hmap.get("attribtype").toString());
					ca.setAttribid(hmap.get("attrib_id").toString());
					list.add(ca);
				}
			}
			return list;
		} catch (Exception ex) {

		}
		return list;
	}
	
	private static JSONArray getJsonArrayOfQuestions(String eid,String purpose){
		JSONArray questions_jsonarray = new JSONArray();
		try {
			NQDbHelper dbhelper = new NQDbHelper();
			HashMap pInParams = new HashMap();
			pInParams.put("groupid", eid);
			pInParams.put("purpose", purpose);
			List dblist = dbhelper.getDataList("getattributes", pInParams);
			if (dblist != null) {
				for (int i = 0; i < dblist.size(); i++) {
					HashMap hmap = (HashMap) dblist.get(i);
					JSONObject question_jsonObj = new JSONObject();
					question_jsonObj.put("attribname", hmap.get("attribname").toString());
					question_jsonObj.put("attrib_id", hmap.get("attrib_id").toString());
					String QType = hmap.get("attribtype").toString();
					if(QType.equals("text")){
						question_jsonObj.put("attribtype", "Small Text");
					}else if(QType.equals("textarea")){
						question_jsonObj.put("attribtype", "Multiline Text");
					}else if(QType.equals("radio")){
						question_jsonObj.put("attribtype", "Radio");
					}else if(QType.equals("checkbox")){
						question_jsonObj.put("attribtype", "Check Box");
					}else{
						question_jsonObj.put("attribtype", "Drop Down List");
					}					
					questions_jsonarray.put(question_jsonObj);				
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	return questions_jsonarray;
	}
	
	public static JSONObject getQuestionsInJsonObj(String eid,String purpose){
		JSONObject jsonObj = new JSONObject();
		try {			
			jsonObj.put("questions",getJsonArrayOfQuestions(eid,purpose));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jsonObj;
	}
	public String saveQuestion(String eid, CustomAttribute ca, String attribid, String purpose,String type) {
		
		boolean status = false;
		try {
			NQDbHelper dbh = new NQDbHelper();
			HashMap inParams = new HashMap();
			String attrib_set_id = getAttribsetid(eid,inParams,type);
			ca.setAttribsetid(attrib_set_id);
			if(purpose.equals("edit")){
				CustomAttribute dbca = new CustomAttribute();
				dbca = getCustomAttribute(eid,attribid,dbca,type);
				status = updateCustomAttribs(eid, attribid, ca, inParams,type);
				status = updateCustomAttribOptions(dbca, ca, inParams);
				updateConfirmScreenQuestions(eid,attribid,ca);
				updateWhoIsAttendingQuestions(eid,attribid,ca);
				//updateInCustomQuestionResponse(eid,attribid,ca);
			}else{
				//String position = getAttribPosition(inParams);
				String position = getNewMaxId(inParams,"customattributesposition");
				String newattribid = getNewMaxId(inParams,"select_attribid");
				attribid = newattribid;
				//String newattribid = getNewAttribid(attrib_set_id);
				log.info("attrib_set_id:" + attrib_set_id);
				ca.setAttribid(newattribid);
				log.info("newattribid:" + newattribid);		
				status = insertIntoCustomAttribs(ca,inParams,attrib_set_id,newattribid,position);
				status = insertIntoCustomAttribOptions(ca,inParams);
			}
			
			return attribid;
		} catch (Exception ex) {
			log.error("Exception in saveQuestion :" + ex);
		}
		return attribid;
	}
	public void updateOptionSubQuestions(String subquestions,String optionid,String attribid,String attribsetid){
		//System.out.println("!!! subquestions: "+subquestions+" optionid: "+optionid+" attribid: "+attribid+" attribsetid: "+attribsetid);
		DbUtil.executeUpdateQuery("update custom_attrib_options set subquestions=? where option=CAST(? AS NUMERIC) and attrib_id=CAST(? AS INTEGER) and attrib_setid=CAST(? AS INTEGER)", new String[]{subquestions,optionid,attribid,attribsetid});
	}
	
	public void updateSubQnsCount(String attributeid,String attributesetid){
		ArrayList<String> subquestionsList=(ArrayList<String>)DbUtil.getValues("select subquestions from custom_attrib_options where attrib_setid=cast(? as integer) and attrib_id=cast(? as integer)", new String[]{attributesetid,attributeid});
		int count=0;
		for(int i=0;i<subquestionsList.size();i++){
			try{
				if(!"".equals(subquestionsList.get(i)) && !"null".equals(subquestionsList.get(i)) && subquestionsList.get(i)!=null){
					String [] subattribs=subquestionsList.get(i).split(",");
					count+=subattribs.length;
				}
			}catch(Exception e){
				System.out.println("Exception in updateSubQnsCount error: "+e.getMessage());
			}
		}
		System.out.println("attributeid: "+attributeid+"attributesetid: "+attributesetid+" count: "+count+" subquestionsList: "+subquestionsList);
		
		String updateQuery="update custom_attribs set sub_qns_count=CAST(? AS INTEGER) where attrib_id=CAST(? AS INTEGER) and attrib_setid =CAST(? AS INTEGER)";
		if(count>0)
		DbUtil.executeUpdateQuery(updateQuery, new String[]{Integer.toString(count),attributeid, attributesetid});
	}
	public static void updateConfirmScreenQuestions(String eid,String attribid,CustomAttribute ca){
		DbUtil.executeUpdateQuery("update confirmationscreen_questions set attrib_name=? where " +
				"eventid=? and attrib_id=CAST(? AS INTEGER)", new String[]{ca.getName(),eid,attribid});
	}
	
	public static void updateWhoIsAttendingQuestions(String eid,String attribid,CustomAttribute ca){
		DbUtil.executeUpdateQuery("update attendeelist_attributes set attribname=? where " +
				"eventid=CAST(? AS BIGINT) and attrib_id=CAST(? AS INTEGER)", new String[]{ca.getName(),eid,attribid});

	}
	
	/*public static void updateInCustomQuestionResponse(String eid,String attribid,CustomAttribute ca){
		System.out.println("updateInCustomQuestionResponse attribid: "+attribid);
		DbUtil.executeUpdateQuery("update custom_questions_response set question_original=? " +
				"where attribid=CAST(? AS INTEGER) and ref_id in(select distinct ref_id from custom_questions_response_master " +
				"where groupid=CAST(? AS BIGINT))", new String[]{ ca.getName(),attribid,eid});
	}*/
	
	private boolean updateCustomAttribs(String eid,String attribid,CustomAttribute ca,HashMap inParams,String type){
		System.out.println("In updateCustomAttribs ca.getName: "+ca.getName().trim());
		boolean status = false;
		try{
			String attribName = ca.getName().trim();
			if(attribName.contains("\n"))
				attribName = attribName.replaceAll("\r\n", " ");
			ca=getDefualtsizeValues(ca);
			inParams.put("purpose",type);
			inParams.put("groupid", eid);
			inParams.put("attrib_id", attribid);
			inParams.put("attribname", attribName);
			inParams.put("attribtype", ca.getQtype());
			inParams.put("isrequired", ca.getIsRequired());
			inParams.put("textboxsize", ca.getSize());
			inParams.put("rows", ca.getRows());
			inParams.put("cols", ca.getColumns());
			NQStatusObj stobj = NQDbUtil.executeQuery("update_custom_attribs",inParams);
			status = stobj.getStatus();
			log.info("execution status of attribs:" + stobj.getStatus());
			return status;
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return status;
	}
	
	public CustomAttribute getDefualtsizeValues(CustomAttribute ca)
	{
		if("".equals(ca.getSize().trim()))
			ca.setSize("10");
		else{	
			String size=ca.getSize().trim();
			if(size.indexOf(".")>-1){
				int pos=ca.getSize().trim().indexOf(".");	
				String substring=size.substring(0,pos);
				try{
					Integer.parseInt(substring);
					ca.setSize(substring);
				}catch(Exception e){
					ca.setSize("10");
				}
			}else{
				try{
					Integer.parseInt(size);
					ca.setSize(size);
				}catch(Exception e){
					ca.setSize("10");
				}}}
		if(Integer.parseInt(ca.getSize().trim())<=10)
			ca.setSize("10");
		if(Integer.parseInt(ca.getSize().trim())>=70)
			ca.setSize("70");
	
		if("".equals(ca.getRows().trim()))
			ca.setRows("5");
			else{	
				String rowsize=ca.getRows().trim();
				if(rowsize.indexOf(".")>-1){
					int pos=rowsize.indexOf(".");
					String substr=rowsize.substring(0,pos);
					try{
						Integer.parseInt(substr);
						ca.setRows(substr);
					}catch(Exception e){
						ca.setRows("5");
					}
				}else{
					try{
						Integer.parseInt(rowsize);
						ca.setRows(rowsize);
					}catch(Exception e){
						ca.setRows("5");
					}
				}
			}
		if(Integer.parseInt(ca.getRows().trim())<=5)
			ca.setRows("5");
		if(Integer.parseInt(ca.getRows().trim())>=70)
			ca.setRows("70");
		
		if("".equals(ca.getColumns().trim()))
			ca.setColumns("10");
		else{
			String colsize=ca.getColumns().trim();
			if(colsize.indexOf(".")>-1){
				int pos=colsize.indexOf(".");
				String substr=colsize.substring(0,pos);
				try{
					Integer.parseInt(substr);
					ca.setColumns(substr);
				}catch(Exception e){
					ca.setColumns("10");
				}
			}else{
				try{
					Integer.parseInt(colsize);
					ca.setColumns(colsize);
				  }catch(Exception e){
					ca.setColumns("10");
				}}}
		if(Integer.parseInt(ca.getColumns().trim())<=10)
			ca.setColumns("10");
		if(Integer.parseInt(ca.getColumns().trim())>=70)
			ca.setColumns("70");
		return ca;
	}
	
	private boolean updateCustomAttribOptions(CustomAttribute dbca,CustomAttribute ca,HashMap inParams){
		boolean status = false;
		boolean deletestatus = false;
		try{			
			preparerowscolvalues(ca);
			List<Entity> dblist = dbca.getOptionsList();
			List<Entity> newlist = ca.getOptionsList();
			System.out.println("CustomAttribOptions dblist.size: "+dblist.size());
			System.out.println("CustomAttribOptions newlist.size: "+newlist.size());
			for(int i=0;i<dblist.size();i++){
				deletestatus = false;
				if(newlist.size()==0)
					deletestatus = true;
				for(int j=0;j<newlist.size();j++){					
					if(dblist.get(i).getKey().equals(newlist.get(j).getKey())){
						deletestatus = false;
						break;
					}else{
						deletestatus = true;
					}
				}
				if(deletestatus){
					inParams.put("option", dblist.get(i).getKey());
					if(!dbca.isSubQuestion()){
						ArrayList<String> subattribidsList=(ArrayList<String>)DbUtil.getValues("select subquestions from custom_attrib_options where attrib_setid=cast(? as integer) and attrib_id=cast(? as integer) and option=cast(? as numeric)", new String[]{inParams.get("attrib_setid").toString(),inParams.get("attrib_id").toString(),inParams.get("option").toString()});
						String subattribids = subattribidsList.toString().replace("[", "").replace("]", "");
						if(!"".equals(subattribids)){
							StatusObj stobj=DbUtil.executeUpdateQuery("delete from custom_attrib_options where attrib_setid = cast(? as integer) and attrib_id in("+subattribids+")", new String[]{inParams.get("attrib_setid").toString()});
							stobj=DbUtil.executeUpdateQuery("delete from custom_attribs where attrib_setid = cast(? as integer) and attrib_id in("+subattribids+")", new String[]{inParams.get("attrib_setid").toString()});
						}
					}
					NQStatusObj stobj = NQDbUtil.executeQuery("delete_custom_attrib_option",inParams);
					status = stobj.getStatus();
				}
				
			}
			for(int i=0;i<newlist.size();i++){
				int position = i+1;				
				if(newlist.get(i).getKey().length()!=0){
					//update already present options
					inParams.put("option",newlist.get(i).getKey());
					inParams.put("option_val",newlist.get(i).getValue());
					inParams.put("position", position);
					NQStatusObj stobj = NQDbUtil.executeQuery("update_custom_attrib_options", inParams);
					status = stobj.getStatus();
					System.out.println("updateCustomAttribOptions status when length>0 "+status);
				}
				else{
					//new options
					String optid = getNewMaxId(inParams,"select_max_option");
					inParams.put("option", optid);
					newlist.get(i).setKey(optid);
					inParams.put("position", position);
					inParams.put("option_val", newlist.get(i).getValue());
					NQStatusObj stobj = NQDbUtil.executeQuery("insert_custom_attrib_options", inParams);
					status = stobj.getStatus();
					System.out.println("updateCustomAttribOptions status when length=0 "+status);
				}
			}
			if(newlist!=null&&newlist.size()>0) ca.setOptionsList(newlist);
		}catch(Exception ex){
			System.out.println("Exception in update options: "+ex.getMessage());
		}
		return true;
	}
	private void preparerowscolvalues(CustomAttribute ca){
		String rows="0";
		String cols="0";
		String size="0";
		if("text".equalsIgnoreCase(ca.getQtype())){
			rows="0";
			ca.setRows(rows);
			cols="0";
			ca.setColumns(cols);
		}
		else if ("textarea".equalsIgnoreCase(ca.getQtype())){
			size="0";
			ca.setSize(size);
		}
		else{
			ca.setRows(rows);
			ca.setColumns(cols);
			ca.setSize(size);
		}
	}
	private boolean insertIntoCustomAttribs(CustomAttribute ca,HashMap inParams,String attrib_set_id,String newattribid,String position){
		boolean status = false;
		try{
			
			String attribName = ca.getName().trim();
			if(attribName.contains("\n"))
				attribName = attribName.replaceAll("\r\n", " ");
			preparerowscolvalues(ca);
			ca=getDefualtsizeValues(ca);
			/*inParams.put("attrib_setid", attrib_set_id);
			inParams.put("attrib_id", newattribid);
			inParams.put("attribname", attribName);
			inParams.put("attribtype", ca.getQtype());
			inParams.put("isrequired", ca.getIsRequired());
			inParams.put("textboxsize",ca.getSize());
			inParams.put("rows",ca.getRows());
			inParams.put("cols",ca.getColumns());
			inParams.put("position", position);	*/
			String[] params = new String[]{attrib_set_id,newattribid,attribName,ca.getQtype(),ca.getIsRequired(),ca.getSize(),ca.getRows(),ca.getColumns(),position,ca.getSubQuestionOf()};
			System.out.println("attrib ID, position: "+newattribid+" "+position);
			//"insert into custom_attribs(attrib_setid,attrib_id,attribname,attribtype,isrequired,textboxsize,rows,cols) values(?,?,?,?,?,?,?,?)"
			//NQStatusObj stobj = NQDbUtil.executeQuery("insert_custom_attribs",inParams);
			String insert_custom_attribs = "insert into custom_attribs(attrib_setid,attrib_id,attribname,attribtype,isrequired,textboxsize,rows,cols,position,subquestionof) " +
					"values(cast(? as integer),cast(? as integer),?,?,?,cast(? as integer),cast(? as integer),cast(? as integer),cast(? as integer),?)";
			StatusObj stobj= DbUtil.executeUpdateQuery(insert_custom_attribs, params);
			status = stobj.getStatus();
			log.info("execution status of attribs:" + stobj.getStatus());
			return status;
		}catch(Exception ex){
			
		}
		return status;
	}
	
	private boolean insertIntoCustomAttribOptions(CustomAttribute ca,HashMap inParams){
		System.out.println(" insertIntoCustomAttribOptions");
		boolean status = false;
		try{
			if(ca.getOptionsList()!=null){
				List<Entity> optionsList=new ArrayList<Entity>();
				for (int i = 0; i < ca.getOptionsList().size(); i++) {
					int option = i+1; 
					/*inParams.put("option", option);
					inParams.put("position", option);
					inParams.put("option_val", ca.getOptionsList().get(i).getValue());
					NQStatusObj stobj = NQDbUtil.executeQuery("insert_custom_attrib_options", inParams);*/
					String[] params=new String[]{ca.getAttribsetid(),ca.getAttribid(),String.valueOf(option),ca.getOptionsList().get(i).getValue(),String.valueOf(option)};
					String insert_custom_attrib_options="insert into custom_attrib_options(attrib_setid,attrib_id,option,option_val,position) " +
							"values(CAST(? AS INTEGER),CAST(? AS INTEGER),CAST(? AS NUMERIC),?,CAST(? AS INTEGER))";
					StatusObj stobj= DbUtil.executeUpdateQuery(insert_custom_attrib_options, params);
					status = stobj.getStatus();
					log.info("execution status of option:" + i + ",Status: "+ stobj.getStatus());
					optionsList.add(new Entity(option+"",ca.getOptionsList().get(i).getValue()));
					
				}
				if(optionsList!=null && optionsList.size()>0)
					ca.setOptionsList(optionsList);
			}
			return status;
		}catch(Exception ex){
			
		}
		return status;
	}
	
	private boolean updateCustomAttribOptions(CustomAttribute ca,HashMap inParams){
		System.out.println(" insertIntoCustomAttribOptions");
		boolean status = false;
		try{
			if(ca.getOptionsList()!=null){
				for (int i = 0; i < ca.getOptionsList().size(); i++) {
					int option = i+1; 
					inParams.put("option", option);
					inParams.put("position", option);
					inParams.put("option_val", ca.getOptionsList().get(i).getValue());
					NQStatusObj stobj = NQDbUtil.executeQuery("insert_custom_attrib_options", inParams);
					status = stobj.getStatus();
					log.info("execution status of option:" + i + ",Status: "+ stobj.getStatus());
					
				}
			}
			return status;
		}catch(Exception ex){
			
		}
		return status;
	}
	
	private String getAttribsetid(String eid,HashMap inParams,String type){
		String attrib_set_id="";
		try{
			NQDbHelper dbh = new NQDbHelper();
			inParams.put("purpose", type);
			inParams.put("groupid", eid);			
			attrib_set_id = dbh.getDataString("attrib_setid_select",inParams);			
			if (attrib_set_id == null) {
				inParams.put("reqSeq", "attrib_set_id");			
				attrib_set_id = dbh.getDataString("getNewSequence", inParams);
				inParams.put("attrib_setid", attrib_set_id);
				inParams.put("groupid", eid);
				inParams.put("purpose", type);
				NQStatusObj stobj = NQDbUtil.executeQuery("insert_custom_attrib_master", inParams);
				log.info("execution status of master:" + stobj.getStatus());
			}
			inParams.put("attrib_setid", attrib_set_id);
			return attrib_set_id;
		}catch(Exception ex){
			
		}
		return attrib_set_id;
	}
	
	private static String getNewAttribid(String attrib_setid) {
		HashMap pInParams = new HashMap();
		pInParams.put("attrib_setid", attrib_setid);
		NQDbHelper dbh = new NQDbHelper();
		String maxattribid = dbh.getDataString("select_attribid", pInParams);
		int newattribid;
		if (maxattribid == null)
			maxattribid = "0";
		try {
			newattribid = Integer.parseInt(maxattribid) + 1;
		} catch (Exception ex) {
			newattribid = 0;
		}
		return newattribid + "";
	}
	
	private static String getAttribPosition(HashMap inParams){
		String attribposition = "0";
		try{
			NQDbHelper dbh = new NQDbHelper();
			attribposition = dbh.getDataString("customattributesposition", inParams);
			int newattribposition;
			if (attribposition == null)
				attribposition = "0";
			try {
				newattribposition = Integer.parseInt(attribposition) + 1;
			} catch (Exception ex) {
				newattribposition = 0;
			}
			return newattribposition + "";
		}catch(Exception ex){
			
		}
		return attribposition;
	}
	
	private static String getNewMaxId(HashMap inParams,String queryname){
		String id = "0";
		try{
			NQDbHelper dbh = new NQDbHelper();
			id = dbh.getDataString(queryname, inParams);
			int newid;
			if (id == null)
				id = "0";
			try {
				newid = Integer.parseInt(id) + 1;
			} catch (Exception ex) {
				newid = 0;
			}
			return newid + "";
		}catch(Exception ex){
			
		}
		return id;
	}
	
	public static CustomAttribute getCustomAttribute(String eid,String attribid,CustomAttribute ca,String type){		
		try{
			HashMap pinparams = new HashMap();
			pinparams.put("groupid", eid);
			pinparams.put("purpose", type);
			pinparams.put("attrib_id", attribid);
			NQDbHelper dbhelper = new NQDbHelper();			
			HashMap dbhmap = dbhelper.getDataHash("selectcustomattrib", pinparams);
			if (dbhmap != null){
					String qtype=dbhmap.get("attribtype").toString();
					ca.setName(dbhmap.get("attribname").toString());
					ca.setQtype(dbhmap.get("attribtype").toString());
					ca.setIsRequired(dbhmap.get("isrequired").toString());
					if("text".equalsIgnoreCase(qtype)){
						ca.setSize(dbhmap.get("textboxsize").toString());
					}else if("textarea".equalsIgnoreCase(qtype)){					
					ca.setRows(dbhmap.get("rows").toString());
					ca.setColumns(dbhmap.get("cols").toString());
					}
			}
			List dblist = dbhelper.getDataList("selectcustomattriboptions", pinparams);
			System.out.println("getting selectcustomattriboptions ");
			if (dblist != null) {
				System.out.println("selectcustomattriboptions count: "+dblist.size());
				for (int i = 0; i < dblist.size(); i++) {
					HashMap hmap = (HashMap) dblist.get(i);	
					ca.getOptionsList().add(new Entity(hmap.get("option").toString(),hmap.get("option_val").toString()));					
				}
			}
			return ca;
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return ca;
	}
	public static boolean swapQuestions(String eid,String qlevel,String qpos){
		String attribsetid=DbUtil.getVal("select attrib_setid from custom_attrib_master " +
				" where purpose='EVENT' and groupid=CAST(? AS BIGINT)", new String[]{eid});
		System.out.println("the eid:"+eid+":"+qlevel+":"+qpos);
		String query="";
		if("transaction".equals(qlevel))
		query="select position from custom_attribs where attrib_setid =CAST(? AS INTEGER) and attrib_id in (select attribid from buyer_custom_questions where eventid=cast(? as bigint)) order by position";
		else
		query="select position from custom_attribs where attrib_setid =CAST(? AS INTEGER) and attrib_id not in (select attribid from buyer_custom_questions where eventid=cast(? as bigint)) order by position";
			
		ArrayList dbpos=new ArrayList();
		DBManager dbm=new DBManager();
		StatusObj sbj=null;
		sbj=dbm.executeSelectQuery(query, new String[]{attribsetid,eid});
		if(sbj.getStatus()&& sbj.getCount()>0){
			for(int i=0;i<sbj.getCount();i++){
				dbpos.add(dbm.getValue(i,"position", ""));
			}
		}
		String posarr[]=qpos.split(",");
		try{
		 for(int i=0;i<posarr.length;i++){
			 if("".equals(posarr[i])) continue;
			
			 String updateQuery="update custom_attribs set position=CAST(? AS INTEGER) where attrib_id=CAST(? AS INTEGER) " +
			 		"and attrib_setid =CAST(? AS INTEGER)";
			 DbUtil.executeUpdateQuery(updateQuery, new String[]{(String)dbpos.get(i),posarr[i], attribsetid});
		 }
		}catch(Exception e){System.out.println(e);}
		
		
		System.out.println("dbpos::"+dbpos);
		
	/*	String pos1=DbUtil.getVal(query, new String[]{attributeid, attribsetid});
		if(pos1==null) pos1="1";
		String pos2=DbUtil.getVal(query, new String[]{swapattributeid, attribsetid});
		if(pos2==null) pos2="2";
		System.out.println("q swap pos1: "+pos1 + "swapattributeid: "+swapattributeid);
		System.out.println("q swap pos2: "+pos2 + "attributeid: "+attributeid);
		String updateQuery="update custom_attribs set position=to_number(?, '9999') where attrib_id=to_number(?, '99999999999') " +
				"and attrib_setid =to_number(?, '99999999999')";
		DbUtil.executeUpdateQuery(updateQuery, new String[]{pos1,swapattributeid, attribsetid});
		DbUtil.executeUpdateQuery(updateQuery, new String[]{pos2,attributeid, attribsetid});
		*/return true;
	}
	
	public void swapSubQuestions(String subattribs,String attribsetid){
		
		System.out.println("swapSubQuestions:::: "+subattribs);
		String subattribsarr[]=subattribs.split(",");
		if(subattribsarr.length>1){
			String query="select position from custom_attribs where attrib_setid =CAST(? AS INTEGER) and attrib_id in ("+subattribs+") order by position";
			ArrayList dbpos=new ArrayList();
			DBManager dbm=new DBManager();
			StatusObj sbj=null;
			sbj=dbm.executeSelectQuery(query, new String[]{attribsetid});
			if(sbj.getStatus()&& sbj.getCount()>0){
				for(int i=0;i<sbj.getCount();i++){
					dbpos.add(dbm.getValue(i,"position", ""));
				}
			}
			
			for(int i=0;i<subattribsarr.length;i++){
				String updateQuery="update custom_attribs set position=CAST(? AS INTEGER) where attrib_id=CAST(? AS INTEGER) and attrib_setid =CAST(? AS INTEGER)";
				DbUtil.executeUpdateQuery(updateQuery, new String[]{(String)dbpos.get(i),subattribsarr[i], attribsetid});
			}
			System.out.println("swapSubQuestions dbpos:::: "+dbpos);
		}
	}
	
	public static boolean deleteQuestion(String eid,String attribid,String type){
		boolean status = false;
		try{
			HashMap inparams = new HashMap();
			inparams.put("groupid", eid);
			inparams.put("purpose", type);
			inparams.put("attrib_id", attribid);
			String query="select attrib_id as subattribid from custom_attribs where attrib_setid = (select attrib_setid from custom_attrib_master where groupid=cast(? as integer) " +
					"and purpose=?) and subquestionof like '%"+attribid+"_%'";
			
			StringBuffer attribids = new StringBuffer();
			attribids.append(attribid);
			
			DBManager dbm=new DBManager();
			StatusObj sbj=null;
			sbj=dbm.executeSelectQuery(query, new String[]{eid,type});
			if(sbj.getStatus()&& sbj.getCount()>0){
				for(int i=0;i<sbj.getCount();i++){
					attribids.append(","+dbm.getValue(i,"subattribid", ""));
				}
			}
			/*NQStatusObj stobj = NQDbUtil.executeQuery("delete_custom_attribs", inparams);
			stobj = NQDbUtil.executeQuery("delete_customattrib_options",inparams);
			status = stobj.getStatus();*/
			sbj=DbUtil.executeUpdateQuery("delete from custom_attribs where attrib_setid = (select attrib_setid from custom_attrib_master where groupid=cast(? as integer) and purpose=?) and attrib_id in("+attribids+")", new String[]{eid,type});
			status=sbj.getStatus();
			
			//added on Jun 11, 2015 while conditional questions feature
			DbUtil.executeUpdateQuery("delete from custom_attrib_options where attrib_setid = (select attrib_setid from custom_attrib_master where groupid=cast(? as integer) and purpose=?) and attrib_id in("+attribids+")", new String[]{eid,type});
			
			//added on Jun 11, 2015 while conditional questions feature
			DbUtil.executeUpdateQuery("delete from buyer_custom_questions where eventid=cast(? as bigint) and attribid in("+attribids+")", new String[]{eid});

			CustomAttributesDB cdb=new CustomAttributesDB();
			DbUtil.executeUpdateQuery("delete from confirmationscreen_questions where eventid=?" +
					" and attrib_id=CAST(? AS INTEGER)", new String[]{eid,attribid});
			cdb.deleteQuestionDisplayInformation(eid,attribid);
			
			DbUtil.executeUpdateQuery("delete from attendeelist_attributes where eventid=CAST(? AS BIGINT)" +
					" and attrib_id=CAST(? AS INTEGER)", new String[]{eid,attribid});
			//cdb.deleteQuestionDisplayInformation(eid,attribid);
			
			/*DbUtil.executeUpdateQuery("delete from report_custom_questions_filter where eventid=CAST(? AS BIGINT)" +
					" and attrib_id=CAST(? AS INTEGER)", new String[]{eid,attribid});*/
			
			DbUtil.executeUpdateQuery("delete from report_custom_questions_filter where eventid=CAST(? AS BIGINT)" +
					" and attrib_id in("+attribids+")", new String[]{eid});
			
			/*DbUtil.executeUpdateQuery("delete from custom_questions_response where attribid=CAST(? AS INTEGER) " +
					"and ref_id in(select ref_id from custom_questions_response_master where groupid=CAST(? AS BIGINT))", new String[]{attribid,eid});*/
			
			DbUtil.executeUpdateQuery("delete from custom_questions_response where attribid in("+attribids+") " +
					"and ref_id in(select ref_id from custom_questions_response_master where groupid=CAST(? AS BIGINT))", new String[]{eid});
			
			return status;
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return status;
	}

	public static String deleteQuestionConfirm(String eid,String attribid){
		String responseexist=DbUtil.getVal("select 'yes' from custom_questions_response where attribid=CAST(? AS INTEGER) "+
				"and ref_id in(select ref_id from custom_questions_response_master where groupid=CAST(? AS BIGINT)) and bigresponse!=''", new String[]{attribid,eid});
		return responseexist;
	}
	
	public void saveQuestionSettings(String eid,String attributeid,String collectbuyer, List seltickets,String type){
		try {
			deleteQuestionDisplayInformation(eid,attributeid);
			HashMap pInparams = new HashMap();
			String attribsetid = getAttribsetid(eid,pInparams,type);
			pInparams.put("eventid", eid);
			pInparams.put("groupid", eid);
			pInparams.put("attribid", attributeid);			
			if(collectbuyer.equals("true")){
				NQDbUtil.executeQuery("insert_buyer_custom_questions", pInparams);
			}
			pInparams.put("attribsetid", attribsetid);
			
			if(seltickets!=null){
				for (int i=0;i<seltickets.size();i++) {
					pInparams.put("subgroupid", seltickets.get(i));					
					NQDbUtil.executeQuery("insertsubgroupattribs", pInparams);
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception:"+ex);
		}
	}
	
	/*public static void updateCustomAttribTickets(String eid,String attribId, String ticketId, String isCheck){
		if("true".equals(isCheck)){
			String attribsetid=DbUtil.getVal("select attrib_setid from custom_attrib_master " +
					" where purpose='EVENT' and groupid=CAST(? AS BIGINT)", new String[]{eid});
			DbUtil.executeUpdateQuery(INSERT_SUBGROUP_ATTRIBS,new String[]{eid,ticketId,attribsetid,attribId});
		}else{
			DbUtil.executeUpdateQuery(DELETE_SUBGROUP_ATTRIB, new String[]{eid,attribId,ticketId});
		}
	}
	
	public static void updateBaseProfileTickets(String eid,String attribId, String ticketId, String isCheck){
		System.out.println("updateBaseProfileTickets: "+eid+" attribid: "+attribId+" ticketid: "+ticketId+" ischeck: "+isCheck);
		
		String mappedAttribId=attribId;
    	if("-5".equals(attribId)) mappedAttribId="fname";
    	if("-6".equals(attribId)) mappedAttribId="lname";
    	if("-7".equals(attribId)) mappedAttribId="email";
    	if("-8".equals(attribId)) mappedAttribId="phone";
		
		if("true".equals(isCheck)){
			String insertquery="insert into base_profile_questions (groupid,contextid, isrequired, attribid) " +
					" values(CAST(? AS BIGINT), CAST(? AS INTEGER), ?, ?)";
			DbUtil.executeUpdateQuery(insertquery,new String[]{eid,ticketId,"Y", mappedAttribId});
		}else{
			String deleteqry="delete from base_profile_questions where groupid=CAST(? AS BIGINT) and contextid=CAST(? AS INTEGER) and attribid=?";
			DbUtil.executeUpdateQuery(deleteqry,new String[]{eid,ticketId,mappedAttribId});
		}
	}*/
	
	public void saveBuyerSubQuestionSettings(String eid,String subattribid){
		String inssett_query="insert into buyer_custom_questions(eventid,attribid,issubquestion) values(?,?,'Y')";
		DbUtil.executeUpdateQuery(inssett_query,new String[]{eid,subattribid});
	}
	
	public void saveRSVPQuestionSettings(String eid,String attributeid,String type){
		try {
			deleteQuestionDisplayInformation(eid,attributeid);
			HashMap pInparams = new HashMap();
			String attribsetid = getAttribsetid(eid,pInparams,type);
			pInparams.put("eventid", eid);
			pInparams.put("groupid", eid);
			pInparams.put("attribid", attributeid);			
			pInparams.put("attribsetid", attribsetid);
			pInparams.put("subgroupid", "101");					
			NQDbUtil.executeQuery("insertsubgroupattribs", pInparams);
			pInparams.put("subgroupid", "102");					
			NQDbUtil.executeQuery("insertsubgroupattribs", pInparams);
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception:"+ex);
		}
	}
	
	private void deleteQuestionDisplayInformation(String eid,String attributeid){
		try {
			HashMap pInparams = new HashMap();			
			pInparams.put("eventid", eid);
			pInparams.put("groupid", eid);
			pInparams.put("attribid", attributeid);
			NQStatusObj obj= NQDbUtil.executeQuery("delete_buyer_custom_questions", pInparams);
			obj=NQDbUtil.executeQuery("delete_subgroupattribs", pInparams);
			String delsett_query = "delete from rsvp_attribs where eventid=CAST(? AS BIGINT) and attrib_id = CAST(? AS INTEGER)";
			DbUtil.executeUpdateQuery(delsett_query,new String[]{eid,attributeid});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void saveBaseQuestionSettings(String eid,List seltickets){
		try {
			HashMap pInparams = new HashMap();
			pInparams.put("eventid", eid);
			NQStatusObj obj= NQDbUtil.executeQuery("delete_eventbaseprofiletickets", pInparams);			
			if(seltickets!=null){
				for (int i=0;i<seltickets.size();i++) {
					pInparams.put("ticketid", seltickets.get(i));				
					NQDbUtil.executeQuery("insert_eventbaseprofiletickets", pInparams);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public List getBaseProfileTickets(String eid){
		List list=new ArrayList();
		try {
			HashMap pInParams = new HashMap();
			pInParams.put("eventid", eid);
			NQDbHelper nqdbhelper = new NQDbHelper();
			list = nqdbhelper.getDataColumnList("select_eventbaseprofiletickets", pInParams);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public List getCustomQuestionTickets(String eid, String attributeid){
		List list=new ArrayList();			
		try {
			HashMap pInParams = new HashMap();
			pInParams.put("groupid", eid);
			pInParams.put("attribid", attributeid);
			NQDbHelper nqdbhelper = new NQDbHelper();
			list = nqdbhelper.getDataColumnList("selectedtickets_subgroupattribs", pInParams);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public static JSONObject getCustomAttribTickets(String eid, HashMap basicProfileSettingsMap){
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		JSONObject jsonObj=new JSONObject();
		
		try{
			ArrayList<String> basicAttribList=new ArrayList<String>();
			basicAttribList.add("fname");
			basicAttribList.add("lname");
			basicAttribList.add("email");
			basicAttribList.add("phone");
			
			for(int j=0;j<basicAttribList.size()&&basicProfileSettingsMap!=null;j++){
				String attribType=basicAttribList.get(j);
				if((basicProfileSettingsMap.get(attribType)==null)){
					
				}else{
					String attribId="";
					if("fname".equals(attribType)) attribId="-5";
			    	if("lname".equals(attribType)) attribId="-6";
			    	if("email".equals(attribType)) attribId="-7";
			    	if("phone".equals(attribType)) attribId="-8";
			    	
					ArrayList profileQTickets=(ArrayList)basicProfileSettingsMap.get(attribType);
					
					for(int i=0;i<profileQTickets.size();i++){
						HashMap ticketData=(HashMap)profileQTickets.get(i);
						String ticketId= (String)ticketData.get("tid");
						
						if(!jsonObj.isNull(attribId)){
							JSONArray jsonarray=jsonObj.getJSONArray(attribId);
							jsonarray.put(ticketId);
						}else{
							jsonObj.put(attribId, new JSONArray(new Object[] {ticketId} ));
						}
					}
				}
			}
			
			statobj=dbmanager.executeSelectQuery(CUSTOM_ATTRIB_TICKETS,new String []{eid});
			int count=statobj.getCount();
			if(statobj.getStatus()&&count>0){
				for(int k=0;k<count;k++){
					String attribId=dbmanager.getValue(k,"attribid","");
					String ticketId=dbmanager.getValue(k,"subgroupid","");
					if(!jsonObj.isNull(attribId)){
						JSONArray jsonarray=jsonObj.getJSONArray(attribId);
						jsonarray.put(ticketId);
					}else{
						jsonObj.put(attribId, new JSONArray(new Object[] {ticketId} ));
					}
				}
			}
		}
		catch(Exception e){
			System.out.println("Exception in getCustomAttribTickets eventid: "+eid+" in question Management for "+e.getMessage());
		}
		return jsonObj;
	
	}
	
	
	public String getBuyerStatus(String eid,String attributeid){
		String status = "flase";
		try {
			HashMap pInParams = new HashMap();
			pInParams.put("eventid", eid);
			pInParams.put("attribid", attributeid);
			NQDbHelper nqdbhelper = new NQDbHelper();
			String count = nqdbhelper.getDataString("count_buyer_custom_questions", pInParams);
			if(!count.equals("0")){
				status = "true";
			}
			return status;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return status;
	}
	
	/*public Vector<HashMap<String,String>> getAttributes(String eid,String type){		
		String RESPONSE_QUERY_FOR_ATTRIBUTE="select attribname as attrib_name from " +
				"custom_attribs where attrib_setid=(select attrib_setid from custom_attrib_master " +
				"where groupid = CAST(? AS INTEGER) and purpose =?) and attribname not in (select attribname " +
				"from attendeelist_attributes where eventid=CAST(? AS INTEGER))and attrib_id " +
				"not in (select attribid from buyer_custom_questions where eventid=CAST(? AS BIGINT))";
		
		Vector<HashMap<String,String>> attribsVector=new Vector<HashMap<String,String>>();
			DBManager dbmanager=new DBManager();
			HashMap<String,String> hm = null;
			StatusObj statobj=dbmanager.executeSelectQuery(RESPONSE_QUERY_FOR_ATTRIBUTE,new String [] {eid,type,eid,eid});
			if(statobj.getStatus()){
			        for(int k=0;k<statobj.getCount();k++){
					hm=new HashMap<String,String>();
					hm.put("attrib_name",dbmanager.getValue(k,"attrib_name",""));
					attribsVector.add(hm);
			        }
			}
			 return attribsVector;

			}*/
	
	// Display fields in EventPageContent.
	public static ArrayList<Entity> getAttributes(String eid,String type){		
		String RESPONSE_QUERY_FOR_ATTRIBUTE="select a.attrib_id,a.attribname as attrib_name from " +
				"custom_attribs a,custom_attrib_master b where a.attrib_setid=b.attrib_setid and " +
				"b.groupid = CAST(? AS BIGINT) and b.purpose =? and a.attrib_id not in (select attrib_id " +
				"from attendeelist_attributes where eventid=CAST(? AS BIGINT)) and a.attrib_id " +
				"not in (select attribid from buyer_custom_questions where eventid=CAST(? AS BIGINT))";
		
		ArrayList<Entity> attribsList=new ArrayList<Entity>();
		
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		
	    String defaultquestionsquery="select distinct(attribid) from base_profile_questions where contextid!=0 and attribid "+
        " in('email','phone') and groupid=CAST(? AS BIGINT) order by attribid";
        
	    String defattendeelistquery="select attrib_id from attendeelist_attributes where eventid=CAST(? AS BIGINT)  and  attrib_id in(-1,-2) order by attrib_id desc";
		
        
      HashMap<String,String> hmap=new HashMap<String,String>();
      statobj=dbmanager.executeSelectQuery(defattendeelistquery, new String[]{eid});
		if(statobj.getStatus() && statobj.getCount()>0){
			for(int i=0;i<statobj.getCount();i++){
				hmap.put(dbmanager.getValue(i, "attrib_id",""), dbmanager.getValue(i, "attrib_id",""));
			}
		}
		statobj=dbmanager.executeSelectQuery(defaultquestionsquery, new String[]{eid});
		if(statobj.getStatus() && statobj.getCount()==1){
			if("email".equals(dbmanager.getValue(0,"attribid",""))){
			    if(!"-1".equals(hmap.get("-1")))
			    	attribsList.add(new Entity("-1","Email"));
			}else if("phone".equals(dbmanager.getValue(0,"attribid",""))){
				if(!"-2".equals(hmap.get("-2"))) 
					attribsList.add(new Entity("-2","Phone"));	
			}
		}	
		else if(statobj.getStatus() && statobj.getCount()==2){
			if(!"-1".equals(hmap.get("-1")))
				attribsList.add(new Entity("-1","Email"));
			if(!"-2".equals(hmap.get("-2")))
				attribsList.add(new Entity("-2","Phone"));
			
		}
		
		statobj=dbmanager.executeSelectQuery(RESPONSE_QUERY_FOR_ATTRIBUTE,new String [] {eid,type,eid,eid});
		if(statobj.getStatus()){
			for(int k=0;k<statobj.getCount();k++){
				attribsList.add(new Entity(dbmanager.getValue(k,"attrib_id",""),dbmanager.getValue(k,"attrib_name","")));
			}
		}
		return attribsList;
	}

	public static ArrayList<Entity> getAttendeeAttributes(String eid){
		ArrayList<Entity> editattributeslist= new ArrayList<Entity>();
			DBManager dbm =new DBManager();
			
			String Query="select attrib_id,attribname as attrib_name from attendeelist_attributes where eventid=CAST(? AS BIGINT)"; 
			StatusObj statobj=dbm.executeSelectQuery(Query,new String[] {eid});
			if(statobj.getStatus())	{
				for(int k=0;k<statobj.getCount();k++){
				  editattributeslist.add(new Entity(dbm.getValue(k,"attrib_id",""),dbm.getValue(k,"attrib_name","")));
				}
			}else{
				editattributeslist.add(new Entity("0","Attendee Name"));
			}	
			return editattributeslist;
		}
	
	public void saveAttendeeAttribs(String eid,List<Entity> attendeeQuestions,String type){
		try {
	        String DELETEQUERY="delete from attendeelist_attributes where eventid=CAST(? AS BIGINT)";
			String INSERT_ATTENDEE_ATTRIBUTES="insert into attendeelist_attributes(eventid,position,attrib_setid," +
					"attrib_id,attribname,created_at,updated_at)values(CAST(? AS BIGINT),CAST(? AS INTEGER)," +
					"CAST(? AS INTEGER),CAST(? AS INTEGER),?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
			HashMap inParams = new HashMap();
			String custom_setid = getAttribsetid(eid, inParams,type);
			DbUtil.executeUpdateQuery(DELETEQUERY,new String[]{eid});
			for(int i=0;i<attendeeQuestions.size();i++){
				DbUtil.executeUpdateQuery(INSERT_ATTENDEE_ATTRIBUTES,new String[]{eid,""+(i+1),custom_setid,attendeeQuestions.get(i).getKey(),attendeeQuestions.get(i).getValue(),DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate()});
			}
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
	}

	public static void saveRsvpQuestionSettings(String eid,String attributeid,List sellist,String purpose){
		try {
			String delsett_query = "delete from rsvp_attribs where eventid=CAST(? AS BIGINT) and attrib_id = CAST(? AS INTEGER)";
			String inssett_query = "insert into rsvp_attribs (eventid,attrib_id,rsvp_status) values(CAST(? AS BIGINT),CAST(? AS INTEGER),?)";
			//if(purpose.equals("edit")){
				DbUtil.executeUpdateQuery(delsett_query,new String[]{eid,attributeid});
			//}
			if(sellist!=null){
				for(int i=0;i<sellist.size();i++){			
					DbUtil.executeUpdateQuery(inssett_query,new String[]{eid,attributeid,sellist.get(i).toString()});
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static List getRsvpQuestionsettings(String eid,String attributeid){
		List list = new ArrayList();
		try {
			String get_query = "select rsvp_status from rsvp_attribs where eventid=CAST(? AS BIGINT) and attrib_id=CAST(? AS INTEGER)";
			DBManager dbm = new DBManager();
			list = DbUtil.getValues(get_query, new String[]{eid,attributeid});
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public static ArrayList<Entity> getAttribs(String eid,String purpose){
		ArrayList<Entity> attribslist=new ArrayList<Entity>();
		attribslist=getAttribs(eid,purpose,"","");
		return attribslist;
	}
	
	
	public static ArrayList<Entity> getAttribs(String eid,String purpose,String ptype,String contexttype){
        System.out.println("purpose is"+purpose);
		ArrayList<Entity> attribs=new ArrayList<Entity>();
		String query="",defaultquestionsquery="",defconfirmationquesquery="";
		try{
			if("RSVP".equalsIgnoreCase(ptype)){
				String rsvpbqstatus=DbUtil.getVal("select 'Y' from base_profile_questions where attribid='email' and contextid in(101,102) and groupid=CAST(? AS BIGINT)",new String[]{eid});	
				if(rsvpbqstatus==null)rsvpbqstatus="N";
				if(!"Y".equalsIgnoreCase(rsvpbqstatus)){
				 DbUtil.executeUpdateQuery("insert into base_profile_questions(attribid,isrequired,contextid,groupid) values('email','Y',101,CAST(? AS BIGINT))", new String[]{eid});	
				 DbUtil.executeUpdateQuery("insert into base_profile_questions(attribid,isrequired,contextid,groupid) values('email','Y',102,CAST(? AS BIGINT))", new String[]{eid});
				}
				}
			if("attendee".equals(contexttype)){
				query="select a.attrib_id,a.attribname from custom_attribs a,custom_attrib_master b "+  
					" where a.attrib_setid=b.attrib_setid and b.groupid=CAST(? AS BIGINT) and b.purpose='EVENT' " +
					"and a.attrib_id not in (select attrib_id from confirmationscreen_questions where eventid=? and type=?) " +
					"and a.attrib_id not in (select attribid from buyer_custom_questions where eventid=CAST(? AS BIGINT))" +
					"order by a.position";
				defaultquestionsquery="select distinct(attribid) from base_profile_questions where contextid!=0 and attribid "+
		                               " in('email','phone') and groupid=CAST(? AS BIGINT) order by attribid ";
				
				defconfirmationquesquery="select attrib_id from confirmationscreen_questions where eventid=? and type=? and  attrib_id in(-1,-2) order by attrib_id desc";
			}else{
				query="select a.attrib_id,a.attribname from custom_attribs a,custom_attrib_master b"+ 
					" where a.attrib_setid=b.attrib_setid and b.groupid=CAST(? AS BIGINT) and b.purpose='EVENT' " +
					"and a.attrib_id not in (select attrib_id from confirmationscreen_questions where eventid=? and type=?) " +
					"and a.attrib_id in (select attribid from buyer_custom_questions where eventid=CAST(? AS BIGINT))" +
					"order by a.position";	
				
				defaultquestionsquery="select distinct(attribid) from base_profile_questions where contextid=0 and attribid "+
		        " in('email','phone') and groupid=CAST(? AS BIGINT) order by attribid ";
				
				defconfirmationquesquery="select attrib_id from confirmationscreen_questions where eventid=? and type=? and  attrib_id=-3";
				}
        DBManager db=new DBManager();
		StatusObj sb=null;
        HashMap<String,String> hmap=new HashMap<String,String>();
			sb=db.executeSelectQuery(defconfirmationquesquery, new String[]{eid,purpose});
			if(sb.getStatus() && sb.getCount()>0){
				for(int i=0;i<sb.getCount();i++){
					hmap.put(db.getValue(i, "attrib_id",""), db.getValue(i, "attrib_id",""));
				}
			}
			sb=db.executeSelectQuery(defaultquestionsquery, new String[]{eid});
			if(sb.getStatus() && sb.getCount()==1){
				if("attendee".equals(contexttype)){
				if("email".equals(db.getValue(0,"attribid",""))){
				    if(!"-1".equals(hmap.get("-1")))
					attribs.add(new Entity("-1","Email"));
				}else if("phone".equals(db.getValue(0,"attribid",""))){
					if(!"-2".equals(hmap.get("-2"))) 
					attribs.add(new Entity("-2","Phone"));	
				}
				}else{
					 if("phone".equals(db.getValue(0,"attribid",""))){
							if(!"-3".equals(hmap.get("-3")))
								attribs.add(new Entity("-3","Phone"));
					}
				 }	
			}	
			else if(sb.getStatus() && sb.getCount()==2){
				if("attendee".equals(contexttype)){
				if(!"-1".equals(hmap.get("-1")))
				attribs.add(new Entity("-1","Email"));
				if(!"-2".equals(hmap.get("-2")))
				attribs.add(new Entity("-2","Phone"));
				}else{
					if(!"-3".equals(hmap.get("-3")))
						attribs.add(new Entity("-3","Phone"));
				}

			}
        
		
		sb=db.executeSelectQuery(query, new String[]{eid,eid,purpose,eid});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
				attribs.add(new Entity(db.getValue(i, "attrib_id",""),db.getValue(i,"attribname","")));
			}
		}
	    }catch(Exception e){
		System.out.println("Exception occured in getAttribs method");
	    }
		return attribs;
	}
	
	public static ArrayList<Entity> getAddedAttribs(String eid,String purpose){
		ArrayList<Entity> addedattribs=new ArrayList<Entity>();
		addedattribs=getAddedAttribs(eid,purpose,"attendee");
	    return addedattribs;
	}
	
	public static ArrayList<Entity> getAddedAttribs(String eid,String purpose,String contexttype){
		ArrayList<Entity> addedattribs=new ArrayList<Entity>();
		String query="select attrib_id,attrib_name from confirmationscreen_questions where eventid=? and type=? and context=? order by position";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query, new String[]{eid,purpose,contexttype});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
				addedattribs.add(new Entity(db.getValue(i, "attrib_id",""),db.getValue(i,"attrib_name","")));
			}
		}
		return addedattribs;
	}
	
	public static void saveAttribs(String eid,ArrayList<Entity> buyerattribs,ArrayList<Entity>attendeeattribs,String purpose){
		try {
            
			String DELETEQUERY="delete from confirmationscreen_questions where eventid=? and type=?";
			String INSERT_ATTENDEE_ATTRIBUTES="insert into confirmationscreen_questions(eventid,position,attrib_id,attrib_name," +
			"created_at,updated_at,type,context)values(?,CAST(? AS INTEGER),CAST(? AS INTEGER),?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),?,?)";
			DbUtil.executeUpdateQuery(DELETEQUERY,new String[]{eid,purpose});
			for(int i=0;i<attendeeattribs.size();i++){
				DbUtil.executeUpdateQuery(INSERT_ATTENDEE_ATTRIBUTES,new String[]{eid,""+(i+1),attendeeattribs.get(i).getKey().trim(),attendeeattribs.get(i).getValue().trim(),DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),purpose,"attendee"});
			}
			for(int i=0;i<buyerattribs.size();i++){

				DbUtil.executeUpdateQuery(INSERT_ATTENDEE_ATTRIBUTES,new String[]{eid,""+(i+1),buyerattribs.get(i).getKey().trim(),buyerattribs.get(i).getValue().trim(),DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),purpose,"buyer"});
			}
		} catch(Exception ex){
			// TODO: handle exception
			System.out.println("exception occured in saveAttribs method:"+ex.getMessage());
		}
	}
	
/*	public static HashMap<String, ArrayList<Entity>> getVolumeTickets(String eid){
	ArrayList<Entity> voltickets=new ArrayList<Entity>();
	ArrayList<Entity> selvoltickets=new ArrayList<Entity>();
	HashMap<String, ArrayList<Entity>> volumetickets = new HashMap<String, ArrayList<Entity>>();
	String query="select ticketid,ticket_name,showhide from groupdeal_tickets where eventid=?";
	DBManager db=new DBManager();
	StatusObj sb=db.executeSelectQuery(query, new String[]{eid});
	if(sb.getStatus()){
		for(int i=0;i<sb.getCount();i++){
			if(db.getValue(i, "showhide","").equals("Y"))
				selvoltickets.add(new Entity(db.getValue(i, "ticketid",""),db.getValue(i,"ticket_name","")));
			else
				voltickets.add(new Entity(db.getValue(i, "ticketid",""),db.getValue(i,"ticket_name","")));
		}
	}
	volumetickets.put("voltickets", voltickets);
	volumetickets.put("selvoltickets", selvoltickets);
	return volumetickets;
  }

  public static void saveVolumeTickets(String eid,List<Entity> voltickets){
	try {
		if(voltickets!=null){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<voltickets.size() ;i++){
				if(i==voltickets.size()-1)
					sb.append(voltickets.get(i).getKey());
				else
					sb.append(voltickets.get(i).getKey()+",");
			}
		
			String queryY = "update groupdeal_tickets set showhide='Y' where ticketid not in("+sb+") and eventid=?";
			DbUtil.executeUpdateQuery(queryY,new String[]{eid});
			String queryN = "update groupdeal_tickets set showhide='N' where ticketid in("+sb+") and eventid=?";
			DbUtil.executeUpdateQuery(queryN,new String[]{eid});
		}else DbUtil.executeUpdateQuery("update groupdeal_tickets set showhide='Y' where eventid=?",new String[]{eid});
	} catch (Exception ex) {
		// TODO: handle exception
		System.out.println("Exception In Edit Volume Tickets In EventManage Content ERROR: "+ex.getMessage());
	}
}*/
  
  public static ArrayList<String> getDefaultAttribs(String eid){
	    ArrayList<String> defaultattribs=new ArrayList<String>();
	    String defaultquestionsquery="select distinct(attribid) from base_profile_questions where contextid!=0 and attribid "+
      " in('email','phone') and groupid=CAST(? AS BIGINT) order by attribid ";
	    
	    DBManager db=new DBManager();
		StatusObj sb=null;
		
		sb=db.executeSelectQuery(defaultquestionsquery, new String[]{eid});
		if(sb.getStatus() && sb.getCount()==1){
			if("email".equals(db.getValue(0,"attribid","")))
				defaultattribs.add("Email");
			else if("phone".equals(db.getValue(0,"attribid","")))
				defaultattribs.add("Phone");
			
		}	
		else if(sb.getStatus() && sb.getCount()==2){
			defaultattribs.add("Email");
			defaultattribs.add("Phone");
		}
		return defaultattribs;
	}
  
  public static HashMap<String,String> checkDefaultDisplayQusetions(String eid,String purpose){
		HashMap<String,String> hmap=new HashMap<String,String>();
		String defquesquery="";
		if("confirmationscreen".equals(purpose)){
		defquesquery="select attrib_id from confirmationscreen_questions where eventid=? and attrib_id in(-1,-2) order by attrib_id desc";
		}else if("whoisattending".equals(purpose)){
		defquesquery="select attrib_id from attendeelist_attributes where eventid=CAST(? AS BIGINT)"+
      " and attrib_id in(-1,-2) order by attrib_id desc";
		}
		DBManager db=new DBManager();
		StatusObj sb=null;
		
		sb=db.executeSelectQuery(defquesquery, new String[]{eid});
		if(sb.getStatus() && sb.getCount()>0){
			for(int i=0;i<sb.getCount();i++){
				hmap.put(db.getValue(i, "attrib_id",""), db.getValue(i, "attrib_id",""));
			}
		}
		return hmap;
	}
  
  public static void getPDFTickets(String eid,String val){
		String CONFIG_INFO_INSERT="insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)";
		String CONFIG_KEY_DELETE="delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?";
		
		DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE,new String[]{eid,"event.pdfticket.enable"});
		DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{"event.pdfticket.enable",val,eid});
	}
  
  public static void updatePriceIsAteendee(String eid,String attribid,ArrayList<String> tktids,String purpose){
	  List<String> existtktids=null; 
	  String finaltktids="",finalattribid="",isattendee="";
	  if("fname".equals(attribid))
		  finalattribid="lname";
	  else if("lname".equals(attribid))
		  finalattribid="fname";
	  if(tktids==null){
	  existtktids=DbUtil.getValues("select contextid from base_profile_questions where groupid=CAST(? AS BIGINT) and attribid=? and contextid!=0",new String[]{eid,finalattribid});
	  isattendee="No";
	  }else{
		  existtktids=tktids;
		  if("selecttkts".equals(purpose))
			  isattendee="Yes";
		  else if("unselecttkts".equals(purpose))
			  isattendee="No";
	  }
	  if(existtktids.size()>0){
		  for(int i=0;i<existtktids.size();i++){
			  if(i==0)
				  finaltktids=(String)existtktids.get(i);
			  else
				  finaltktids=finaltktids+","+(String)existtktids.get(i);
		  }
		if(!"".equals(finaltktids) && !"".equals(eid) && tktids==null)
			DbUtil.executeUpdateQuery("update price set isattendee=? where evt_id=CAST(? AS BIGINT) and price_id not in("+finaltktids+")", new String[]{isattendee,eid});
		else if(!"".equals(finaltktids) && !"".equals(eid) && tktids!=null)
			DbUtil.executeUpdateQuery("update price set isattendee=? where evt_id=CAST(? AS BIGINT) and price_id in("+finaltktids+")", new String[]{isattendee,eid});
	   }else{
		   if(!"".equals(eid))
		   DbUtil.executeUpdateQuery("update price set isattendee=? where evt_id=CAST(? AS BIGINT)", new String[]{isattendee,eid}); 
	  }
	  
	  
  }
  
  public static String chkEntryForAttribId(String eventid,String ticketid,String attribid){
	  String chkentry="";
	  if(!"".equals(eventid) && !"".equals(ticketid))
	  chkentry=DbUtil.getVal("select 'Y' from base_profile_questions where groupid=CAST(? AS BIGINT) and contextid=CAST(? as INTEGER)"
			  +" and attribid=?",new String[]{eventid,ticketid,attribid});
      if(chkentry==null || "".equals(chkentry) || "null".equals(chkentry))chkentry="N";
	  return chkentry;
  }
  
   public static HashMap<String,ArrayList<Entity>> getBuyerOptionsList(ArrayList<Entity> optionsList){
		String key="",value="";
		HashMap<String,ArrayList<Entity>> optionsmap=new HashMap<String,ArrayList<Entity>>();
		ArrayList<Entity> attendeemap=new ArrayList<Entity>();
		ArrayList<Entity> buyermap=new ArrayList<Entity>();
		HashMap<String,String> hm=new HashMap<String,String>();
		ArrayList<Entity> keyvaluearr=new ArrayList<Entity>();
		try{
		for(int i=0;i<optionsList.size();i++){
		String[] keyarr=GenUtil.strToArrayStr(optionsList.get(i).getKey(), ",");
		String[] valuearr=GenUtil.strToArrayStr(optionsList.get(i).getValue(), ",");
		if(keyarr.length==2 && valuearr.length==2){
		 for(int k=0;k<keyarr.length;k++){
			key=keyarr[k];
			value=valuearr[k];
			keyvaluearr.add(new Entity(key,value));
		 }
	   }else{
			key=keyarr[0];
			value=valuearr[0];
			keyvaluearr.add(new Entity(key,value));
	   }
	  }
	  for(int i=0;i<keyvaluearr.size();i++){
		if(keyvaluearr.get(i).getKey().indexOf("buyer_q_")>-1){
			buyermap.add(new Entity(keyvaluearr.get(i).getKey().replace("buyer_q_", ""),keyvaluearr.get(i).getValue().replace("buyer_q_","")));
		}else{
			attendeemap.add(new Entity(keyvaluearr.get(i).getKey(),keyvaluearr.get(i).getValue()));
		}
	  }
	}catch(Exception e){
		System.out.println("Exception occured in getBuyerOptionsList");
	}
	optionsmap.put("buyermap", buyermap);
	optionsmap.put("attendeemap", attendeemap);
	return optionsmap;
	}

  public static String getQuestionsStatus(String eid,String contexttype){
	  String questionstatus="";
	  if("attendee".equals(contexttype)){
		  String query="select 'Y' from custom_attribs a,custom_attrib_master b"+ 
			" where a.attrib_setid=b.attrib_setid and b.groupid=CAST(? AS BIGINT) and b.purpose='EVENT' " +
			"and a.attrib_id not in (select attribid from buyer_custom_questions where eventid=CAST(? AS BIGINT))" +
			"order by a.position";
		  questionstatus=DbUtil.getVal(query,new String[]{eid,eid});  
		  if(questionstatus==null)
			questionstatus=DbUtil.getVal("select 'Y' from base_profile_questions where groupid=CAST(? AS BIGINT) and contextid!=0 and attribid in('email','phone')",new String[]{eid});  
		  if(questionstatus==null)questionstatus="N";
	  }else{
		  questionstatus=DbUtil.getVal("select 'Y' from buyer_custom_questions where eventid=CAST(? AS BIGINT)",new String[]{eid}); 
		  if(questionstatus==null)
			questionstatus=DbUtil.getVal("select 'Y' from base_profile_questions where groupid=CAST(? AS BIGINT) and contextid=0 and attribid='phone'",new String[]{eid});  
		  if(questionstatus==null)questionstatus="N";
	  }
	  return questionstatus;
   }
  
}
