<%@page import="com.eventbee.layout.DBHelper"%>
<%@page import="com.eventbee.beans.Entity"%>
<%@page import="com.event.dbhelpers.CustomAttributesDB"%>
<%@page import="com.event.beans.CustomAttribute"%>
<%@ page import="java.util.*"%>
<%@ page import="com.eventbee.general.*" %>
<%!
private final static String RESPONSE_QUERY="select attribid,bigresponse  as response,c.firstname || ' ' || c.lastname as name,"
				+" c.attendeekey  from  custom_questions_response a,"
				+" custom_questions_response_master b,eventattendee c where" 
				+" a.ref_id=b.ref_id and c.attendeekey=b.profilekey and"
				+" b.attribsetid=?";
				//+" and attribid::varchar in (++)";
	public static HashMap getResponses(String setid,String eid,Vector requiredList){
		
		DBManager dbmanager=new DBManager();
		String list=requiredList.toString().replace("[", "'").replace("]", "'").replace(", ", "','");
		Vector responses=new Vector();
		StatusObj statobj=null;
		HashMap hm=null;
		String query="select attribid,bigresponse  as response,c.firstname || ' ' || c.lastname as name,"
				+" c.attendeekey  from  custom_questions_response a,"
				+" custom_questions_response_master b,eventattendee c where" 
				+" a.ref_id=b.ref_id and c.attendeekey=b.profilekey and"
				+" b.attribsetid=?"
				+" and attribid::varchar in ("+list+")";
		statobj=dbmanager.executeSelectQuery(query,new String []{setid});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
		hm=new HashMap();
		for(int k=0;k<count;k++){
			String attendeekey=dbmanager.getValue(k,"attendeekey","");
			HashMap options=(HashMap)hm.get(attendeekey);
			if (options==null)
				options=new HashMap();
			options.put(dbmanager.getValue(k,"attribid","0"),dbmanager.getValue(k,"response","0"));
			hm.put(attendeekey,options);

		}

		}

	return hm;
}
public static HashMap<String,HashMap<String,String>> getBasicResponses(String eid){
	HashMap<String,HashMap<String,String>> basicResponse=new HashMap<String,HashMap<String,String>>();
	String query="select profilekey,email,phone from profile_base_info where eventid =?::bigint";
	StatusObj statobj=null;
	DBManager dbmanager=new DBManager();
	statobj=dbmanager.executeSelectQuery(query,new String []{eid});
	if(statobj.getStatus() && statobj.getCount()>0){
	for(int k=0;k<statobj.getCount();k++){
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("email",dbmanager.getValue(k,"email",""));
		hm.put("phone",dbmanager.getValue(k,"phone",""));
		basicResponse.put(dbmanager.getValue(k,"profilekey",""),hm);
	}
	}
	return basicResponse;
}
public static Vector getRequiredAtribs(String eid,String templatedata){
	
	ArrayList arrlistList=DBHelper.getAttributes(eid,"EVENT");
	ArrayList questionLlist=new ArrayList();
	HashMap tempMap=new HashMap();
	for(int i=0;i<arrlistList.size();i++){
		Entity e1=(Entity)arrlistList.get(i);
		questionLlist.add(e1.getKey());
	    tempMap.put(e1.getKey(),e1.getValue());
	}
    Vector selList=new Vector(); 	
	for(int i=0;i<questionLlist.size();i++){
		if(templatedata.contains((String)tempMap.get(questionLlist.get(i))))
			selList.add((String)questionLlist.get(i));
	}
	
/* String ATTENDEELIST_ATTRIBUTES="select attrib_id from attendeelist_attributes where eventid=CAST(? AS INTEGER) order by position";
	Vector attribsVector=new Vector();
	DBManager dbmanager=new DBManager();
	HashMap hm = null;
	StatusObj statobj=dbmanager.executeSelectQuery(ATTENDEELIST_ATTRIBUTES,new String [] {eid});
	if(statobj.getStatus()){
	        for(int k=0;k<statobj.getCount();k++){
			attribsVector.add(dbmanager.getValue(k,"attrib_id",""));
	}
	} */
	 return selList;

	}

%>
