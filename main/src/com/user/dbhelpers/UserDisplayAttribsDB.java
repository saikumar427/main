package com.user.dbhelpers;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;
import java.util.Set;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.GenUtil;
import com.eventbee.general.EbeeConstantsF;

public class UserDisplayAttribsDB {
	
public static void insertDisplayAttribs(String userid,String module,HashMap<String, String>attribMap ,boolean forceDelete){
	Set<String> mapKeys = attribMap.keySet();
    Iterator<String> It = mapKeys.iterator();
    String contains="select 'Y' from user_display_attribs where userid=to_number(?,'99999999999999')";
    String record=DbUtil.getVal(contains,new String []{userid});
   if(record==null)record="";
    if(!"Y".equals(record))
    {
    String inserAttribQuery="insert into user_display_attribs(userid ,module,attrib_name,attrib_value,preview_attrib_value) " +
    		"values(to_number(?,'99999999999999'),?,?,?,?)";
    while (It.hasNext()) {
        String key = (String)(It.next());
        String value=attribMap.get(key);
        DbUtil.executeUpdateQuery(inserAttribQuery, new String []{userid,module,key,value,value});
        }
    }
    else
    {
    	  while (It.hasNext()) {
              String key = (String)(It.next());
              String value=attribMap.get(key);
              String updatequery="update user_display_attribs set attrib_value=? where attrib_name=? and module=? and userid=to_number(?,'99999999999999')";
              DbUtil.executeUpdateQuery(updatequery, new String []{value,key,module,userid});
              }
    	
    }
 
}// End of insertDisplayAttribs
//getThemePageAttribs


public static void insertPreviewAttribs(String userid,String module,HashMap<String, String>attribMap ,boolean forceDelete){
		Set<String> mapKeys = attribMap.keySet();
	    Iterator<String> It = mapKeys.iterator();
	    String contains="select 'Y' from user_display_attribs where userid=to_number(?,'99999999999999')";
	    String record=DbUtil.getVal(contains,new String []{userid});
	    if(record==null)record="";
	    if(!"Y".equals(record))
	    {
	    String inserAttribQuery="insert into user_display_attribs(userid ,module,attrib_name,attrib_value,preview_attrib_value) " +
	    		"values(to_number(?,'99999999999999'),?,?,?,?)";
	    while (It.hasNext()) {
	        String key = (String)(It.next());
	        String value=attribMap.get(key);
	        DbUtil.executeUpdateQuery(inserAttribQuery, new String []{userid,module,key,value,value});
	        }
	    }
	    else
	    {
	    	  while (It.hasNext()) {
	              String key = (String)(It.next());
	              String value=attribMap.get(key);
	              String updatequery="update user_display_attribs set preview_attrib_value=? where attrib_name=? and module=? and userid=to_number(?,'99999999999999')";
	              DbUtil.executeUpdateQuery(updatequery, new String []{value,key,module,userid});
	              }
	    	
	    }
	 
	}


public static HashMap<String,String> getPreviewAttribValues(String userid,String module){
	HashMap<String,String> defaultValues=new HashMap<String,String>();
	defaultValues=getDefaultAttribs(module);
	DBManager dbmanager=new DBManager();
	StatusObj statobj=null;
	String attribValsQuery="select attrib_name,preview_attrib_value from user_display_attribs " +
	"where userid::text=? and module=?";
	statobj=dbmanager.executeSelectQuery(attribValsQuery,new String []{userid,module});
	int count=statobj.getCount();
	if(statobj.getStatus() && count>0){
		for(int k=0;k<count;k++){
			 String attribname=dbmanager.getValue(k,"attrib_name","");
			 String attribval=dbmanager.getValue(k,"preview_attrib_value","");
			if(defaultValues.containsKey(attribname)){
				 defaultValues.remove(attribname);
			 }
			 defaultValues.put(attribname, attribval);
		}}
	if("IMAGE".equals(defaultValues.get("BG_TYPE")))
		defaultValues.put("BACKGROUND","url("+defaultValues.get("BACKGROUND_IMAGE")+")");
	else
		defaultValues.put("BACKGROUND",defaultValues.get("BACKGROUND_COLOR"));
	return defaultValues;
}


public static HashMap<String,String> getAttribValues(String userid,String module){
	HashMap<String,String> defaultValues=new HashMap<String,String>();
	defaultValues=getDefaultAttribs(module);
	DBManager dbmanager=new DBManager();
	StatusObj statobj=null;
	String attribValsQuery="select attrib_name,attrib_value from user_display_attribs " +
	"where userid::text=? and module=?";
	statobj=dbmanager.executeSelectQuery(attribValsQuery,new String []{userid,module});
	int count=statobj.getCount();
	if(statobj.getStatus() && count>0){
		for(int k=0;k<count;k++){
			 String attribname=dbmanager.getValue(k,"attrib_name","");
			 String attribval=dbmanager.getValue(k,"attrib_value","");
			if(defaultValues.containsKey(attribname)){
				 defaultValues.remove(attribname);
			 }
			 defaultValues.put(attribname, attribval);
		}}
	if("IMAGE".equals(defaultValues.get("BG_TYPE")))
		defaultValues.put("BACKGROUND","url("+defaultValues.get("BACKGROUND_IMAGE")+")");
	else
		defaultValues.put("BACKGROUND",defaultValues.get("BACKGROUND_COLOR"));
	return defaultValues;
}
public static HashMap<String,String> getDefaultAttribs(String module){
	HashMap<String,String> defaultAttribMap=new HashMap<String,String>();
	String defaultValsQuery="select attribname,attribdefaultvalue from user_display_defaultattribs where module=?";
	DBManager dbmanager=new DBManager();
	StatusObj statobj=null;
	statobj=dbmanager.executeSelectQuery(defaultValsQuery,new String []{module});
	int count=statobj.getCount();
	if(statobj.getStatus() && count>0){
		for(int k=0;k<count;k++){
		    String attribname=dbmanager.getValue(k,"attribname","");
		    String attribval=dbmanager.getValue(k,"attribdefaultvalue","");
		    defaultAttribMap.put(attribname, attribval);
		}	}
	return defaultAttribMap;
}

public static ArrayList getHashmapValues(HashMap map,String userid)
{
	//String serveraddress="http://"+EbeeConstantsF.get("serveraddress","")+"/";
	String serveraddress="http://"+EbeeConstantsF.get("serveraddress","192.168.1.8");
	System.out.println("the server address is"+serveraddress);
	HashMap hmap=new HashMap();
	HashMap hm=null;
	ArrayList arlist=new ArrayList();
	hmap.put("location",map.get("LOCATION"));
	hmap.put("category",map.get("CATEGORY"));
	hmap.put("retrievecount",map.get("NO_OF_ITEMS"));
	hmap.put("userid",userid);
	hmap.put("link",map.get("DISPLAYEBEELINK")); 
	Vector events =StreamingDB.getEventList(serveraddress,hmap);
	for(int i=0;i<events.size();i++)
	{
		HashMap eventmap=(HashMap)events.get(i);
		hm=new HashMap();
		hm.put("eventdate",GenUtil.getHMvalue(eventmap,"startdate","",false));
		hm.put("eventname",GenUtil.getHMvalue(eventmap,"eventname","0"));
		arlist.add(hm);
	}
	arlist.add(hmap);
	return arlist;
}



} //End of class UserDisplayAttribsDB
