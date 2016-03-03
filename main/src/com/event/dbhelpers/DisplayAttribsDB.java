package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.event.i18n.dbmanager.DisplayAttribsDAO;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.I18NCacheData;
import com.eventbee.general.StatusObj;
import com.eventbee.layout.DBHelper;
import com.eventbee.namedquery.NQDbHelper;

public class DisplayAttribsDB {
	
	public static void insertDisplayAttribs(String eid,String module,HashMap<String, String>attribMap ,boolean forceDelete){	
		Set mapKeys = attribMap.keySet();
	    Iterator It = mapKeys.iterator();
	    String inserAttribQuery="insert into custom_event_display_attribs(eventid ,data,last_modified) " +
	    		"values(CAST(? AS BIGINT),?,'now()')";
	    String deleteQuery="delete from custom_event_display_attribs where eventid=CAST(? AS BIGINT)";
	    
	    HashMap<String, String> wordsMap=DisplayAttribsDAO.getDefaultAttribs(module, I18NCacheData.getI18NLanguage(eid));
	    JSONArray allModulesJSON= DisplayAttribsDAO.getAllModulesData(I18NCacheData.getI18NLanguage(eid), eid) ; 
	    if(forceDelete){
	    	DbUtil.executeUpdateQuery(deleteQuery, new String []{eid});
	    }
	    try{
	    	JSONObject moduleData=new JSONObject();
	    	JSONObject finalJSON=new JSONObject();
	    	
	    	while (It.hasNext()) {
	    		String key = (String)(It.next());
	    		String value=attribMap.get(key);
	    		value=value==null?"":value;
	    		if(!value.trim().equals(wordsMap.get(key)))
	    			moduleData.put(key, value);    		
	    	}
	        boolean moduleExists=false;
	    	for (int i = 0; i < allModulesJSON.length(); i++) {
	    		try {
	    			JSONObject jsonObject = allModulesJSON.getJSONObject(i);
	    			if(jsonObject.getString("m").equals(module)){
	    				moduleExists=true;
	    				jsonObject.put("data", moduleData);
	    				break;
	    			}
	    		} catch (JSONException e) {
	    			e.printStackTrace();
	    		}
	    	}
	    	if(!moduleExists)
	    		allModulesJSON.put(new JSONObject().put("m", module).put("data", moduleData));
	    	finalJSON.put("lang", I18NCacheData.getI18NLanguage(eid));
	    	finalJSON.put("modules", allModulesJSON);
	    	DbUtil.executeUpdateQuery(inserAttribQuery, new String []{eid,new JSONArray().put(finalJSON).toString()});
	    }catch(Exception e){
	       System.out.println("Exception while inserting custom attributes::"+eid+" :: moudle"+module+" lang::"+I18NCacheData.getI18NLanguage(eid));
	    } 
	    
	}// End of insertDisplayAttribs
//getThemePageAttribs
public static HashMap<String,String> getAttribValues(String eid,String module){
	/*HashMap<String,String> defaultValues=getDefaultAttribs(module);
	DBManager dbmanager=new DBManager();
	StatusObj statobj=null;
	String attribValsQuery="select attrib_name,attrib_value from event_display_attribs " +
			"where eventid=CAST(? AS BIGINT) and module=?";
	statobj=dbmanager.executeSelectQuery(attribValsQuery,new String []{eid,module});
	int count=statobj.getCount();
	if(statobj.getStatus() && count>0){
		for(int k=0;k<count;k++){
			 String attribname=dbmanager.getValue(k,"attrib_name","");
			 String attribval=dbmanager.getValue(k,"attrib_value","");
			 if(defaultValues.containsKey(attribname)){
				 defaultValues.remove(attribname);
			 }
			 defaultValues.put(attribname, attribval);
		}
		}
	return defaultValues;*/
	HashMap<String, String> hm= new HashMap<String,String>();
	hm.put("module", module);
	String lang=I18NCacheData.getI18NLanguage(eid);
	if(lang==null || "".equals(lang)) lang="en_US";
	return getDisplayAttribs(hm,lang,eid);
	
}
public static HashMap<String,String> getDefaultAttribs(String module){
	/*HashMap<String,String> defaultAttribMap=new HashMap<String,String>();
	String defaultValsQuery="select attribname,attribdefaultvalue from event_display_defaultattribs where module=?";
	DBManager dbmanager=new DBManager();
	StatusObj statobj=null;
	statobj=dbmanager.executeSelectQuery(defaultValsQuery,new String []{module});
	int count=statobj.getCount();
	if(statobj.getStatus() && count>0){
		for(int k=0;k<count;k++){
		    String attribname=dbmanager.getValue(k,"attribname","");
		    String attribval=dbmanager.getValue(k,"attribdefaultvalue","");
		    defaultAttribMap.put(attribname, attribval);
		}
	}
	return defaultAttribMap;*/
	return getDefaultDisplayAttribs(module,"en_US");
}

public static ArrayList getTicketsData(String query,String eid){
	
	DBManager dbmanager=new DBManager();
	StatusObj statobj=null;
	ArrayList alltickets=new ArrayList();

	try{
		statobj=dbmanager.executeSelectQuery(query,new String []{eid,eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
				String ticketId=dbmanager.getValue(k,"price_id","");
				String ticketName=dbmanager.getValue(k,"ticketname","");
				String groupName=dbmanager.getValue(k,"groupname","");
				if(!"".equals(groupName))
					ticketName=ticketName+" ("+groupName+")";
				alltickets.add(new Entity(ticketId,ticketName));
			}
		}
		
	}//End of try block
	catch(Exception e){
		System.out.println("There is an Exception while executing the ticket selection query" +
				"for the eventid"+eid+" "+e.getMessage());
	}
	return alltickets;
}

public static ArrayList getEventTickets(String eid){
	String ticketSelQuery="select pr.price_id,pr.ticket_name as ticketname,etg.groupname  as groupname from price pr," +
	"event_ticket_groups etg,group_tickets gt where gt.ticket_groupid=etg.ticket_groupid and pr." +
	"price_id=CAST(gt.price_id AS INTEGER) and  etg.eventid=? and " +
	"pr.evt_id=CAST(? AS BIGINT) and upper(isdonation)!='YES' order by etg.position,gt.position";
	return getTicketsData(ticketSelQuery,eid);
}

public static ArrayList<Entity> getDisplayFormat(String eid){
	String formatQuery="select formatid,display_format as format from ticketsavailability_display_formats " +
			"where eventid=?";
	
	DBManager dbmanager=new DBManager();
	StatusObj statobj=null;
	ArrayList<Entity> formatList=new ArrayList<Entity>();

	try{
		statobj=dbmanager.executeSelectQuery(formatQuery,new String []{eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int i=0;i<count1;i++){
				String formatId=dbmanager.getValue(i,"formatid","");
				String format=dbmanager.getValue(i,"format","");
				formatList.add(new Entity(formatId,format));
			}
		}
	}//End of try block
	catch(Exception e){
		System.out.println("There is an Exception while executing the format selection query" +
				"for the eventid"+eid+" "+e.getMessage());
	}
	return formatList;

}

public static void insertDisplayFormat(String eid,String formatId,String displayFormat,List formatTickets){
	
	if(formatId != null && !formatId.equals("")){
		String query = "update ticketsavailability_display_formats set display_format=? where " +
		"formatid=CAST(? AS BIGINT) and eventid=?";
		DbUtil.executeUpdateQuery(query, new String []{displayFormat,formatId,eid});
		
		DbUtil.executeUpdateQuery("delete from ticketsfordisplayformats where " +
				"formatid=CAST(? AS BIGINT) and eventid=?",new String[]{formatId,eid});
		for(int i=0;i<formatTickets.size();i++){
			String ticketId = (String)formatTickets.get(i);
			DbUtil.executeUpdateQuery("delete from ticketsfordisplayformats where" +
					" ticketid=CAST(? AS BIGINT) and eventid=?",new String[]{ticketId,eid});
			String qry = "insert into ticketsfordisplayformats(ticketid,formatid,eventid)"+
							"values(CAST(? AS BIGINT),CAST(? AS BIGINT),?)";
			
			DbUtil.executeUpdateQuery(qry, new String []{ticketId,formatId,eid});
		}
	}else{
		HashMap inParams = new HashMap();
		inParams.put("eventid", eid);
		String fmtId=getNewMaxId(inParams,"ticketdisplayformat");
		
		//String fmtId=DbUtil.getVal("select nextval('tickets_availability_display_sequence')",null);
		String query = "insert into ticketsavailability_display_formats(display_format,formatid,eventid)"+
		"values(?,CAST(? AS BIGINT),?)";
		DbUtil.executeUpdateQuery(query, new String []{displayFormat,fmtId,eid});
		
		for(int i=0;i<formatTickets.size();i++){
			String ticketId = (String)formatTickets.get(i);
			
			DbUtil.executeUpdateQuery("delete from ticketsfordisplayformats where" +
					" ticketid=CAST(? AS BIGINT) and eventid=?",new String[]{ticketId,eid});
			
			String qry = "insert into ticketsfordisplayformats(ticketid,formatid,eventid)"+
							"values(CAST(? AS BIGINT),CAST(? AS BIGINT),?)";
			
			DbUtil.executeUpdateQuery(qry, new String []{ticketId,fmtId,eid});
		}
	}

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




public static void deleteDisplayFormat(String formatId,String eid){
	DbUtil.executeUpdateQuery("delete from ticketsavailability_display_formats where " +
			"formatid=CAST(? AS BIGINT) and eventid=?",new String[]{formatId,eid});
	DbUtil.executeUpdateQuery("delete from ticketsfordisplayformats where " +
			"formatid=CAST(? AS BIGINT) and eventid=?",new String[]{formatId,eid});
}

public static List getFormatTickets(String formatId,String eid){
	List formatTickets=new ArrayList();
	if(formatId !=null && !formatId.equals("")){
		formatTickets = DbUtil.getValues("select ticketid from ticketsfordisplayformats where " +
			"formatid=CAST(? AS BIGINT) and eventid=?", new String[]{formatId,eid});
	}
	return formatTickets;
}

public static String getFormatData(String formatId,String eid){
	String dispFormat = null;
	if(formatId !=null && !formatId.equals("")){
		dispFormat = DbUtil.getVal("select display_format from ticketsavailability_display_formats where " +
			"formatid=CAST(? AS BIGINT) and eventid=?", new String[]{formatId,eid});
	}
	return dispFormat;
}

public static JSONObject getDisplayFormatJson(List<Entity> tctDspFrmList){
	JSONObject jsonobj = new JSONObject();
	JSONArray jsonarrObj = new JSONArray();

	try {
		for (Entity data : tctDspFrmList) {
			JSONObject jsonData = new JSONObject();
			jsonData.put("fm",data.getValue());
			jsonData.put("fmId",data.getKey());
			jsonData.put("action","Manage");
			jsonarrObj.put(jsonData);
		}
		jsonobj.put("formats", jsonarrObj);
	} catch (Exception ex) {
		// TODO: handle exception
		ex.printStackTrace();
	}
	return jsonobj;
}

public static void updateRegistrationTimeOut(String eid,String val){
	try{
		String timeoutvalue="";
		if(Integer.parseInt(val)>=5 && Integer.parseInt(val)<=30){
			timeoutvalue=Integer.toString(Integer.parseInt(val)-1);
		}else
			timeoutvalue=val;
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? as BIGINT)) and name=?", new String[]{eid,"timeout"});
		DbUtil.executeUpdateQuery("insert into config(config_id,name,value) select config_id,?,? from eventinfo where eventid=CAST(? as BIGINT)", new String[]{"timeout",timeoutvalue,eid});
	}catch(Exception e){
		System.out.println("Exception occured while updateEventPageTimeOut for event: "+eid+" value :: "+val);
	}
}

public static HashMap<String,String> getDisplayAttribs(HashMap<String, String> hm, String lang, String eid){
	DisplayAttribsDAO displayDAO = new DisplayAttribsDAO();
	HashMap<String, String> displayAttribs= (HashMap<String,String>) displayDAO.getData(hm, lang, eid).get("displayAttribs");
	return  displayAttribs;
}

public static HashMap<String,String> getDefaultDisplayAttribs(String module,String lang){
	HashMap<String,String> defaultValues=new HashMap<String,String>();
	DisplayAttribsDAO displayDao=new DisplayAttribsDAO();
	HashMap<String, String> hm= new HashMap<String,String>();
	hm.put("module", module);
	defaultValues=(HashMap<String,String>) displayDao.getDefaultAttribs(module, lang);
	return defaultValues;
}

public static HashMap<String,String> getAttribValuesForKeys(String eid, String module, String lang, String [] keys){
	HashMap<String,String> defaultValues=new HashMap<String,String>();
	DisplayAttribsDAO displayDao=new DisplayAttribsDAO();
	HashMap<String, String> hm= new HashMap<String,String>();
	hm.put("module", module);
	defaultValues=(HashMap<String,String>) displayDao.getDataForKeys(hm, lang, eid, keys).get("displayAttribsForKeys");
	return defaultValues;
}

public static HashMap<String,String> getDefaultAttribValuesForKeys(String module,String lang, String [] keys){
	HashMap<String,String> defaultValues=new HashMap<String,String>();
	DisplayAttribsDAO displayDao=new DisplayAttribsDAO();
	HashMap<String, String> hm= new HashMap<String,String>();
	hm.put("module", module);
	defaultValues=(HashMap<String,String>) displayDao.getDefaultAttribsForKeys(module, lang, keys);
	return defaultValues;
}

} //End of class DisplayAttribsDB
