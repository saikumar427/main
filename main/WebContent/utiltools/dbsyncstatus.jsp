<%@ page import="com.eventbee.general.DBManager,com.eventbee.general.StatusObj,com.eventbee.general.DbUtil"%>
<%@ page import="org.json.JSONObject" %>
<%
  String dbstatusqry="select count(*) as data,'transcations' as type from event_reg_transactions "+
                     " union all select count(*) as data,'tickets' as type from transaction_tickets "+
                     " union all  select count(*) as data,'authentication' as type from authentication "+
                     " union all select count(*) as data,'events' as type from eventinfo ";
  DBManager dbm=new DBManager();
  StatusObj statobj=null;
  JSONObject json=new JSONObject();
  try{
  statobj=dbm.executeSelectQuery(dbstatusqry, null);
  if(statobj.getStatus() && statobj.getCount()>0){
	  for(int i=0;i<statobj.getCount();i++){
		  json.put(dbm.getValue(i,"type", ""),dbm.getValue(i, "data", ""));
	  }
  }
  }catch(Exception e){
	  System.out.println("Exception occured while getting dbsyncstatus "+e.getMessage());
	  json.put("transcations","0");
	  json.put("tickets","0");
	  json.put("authentication","0");
	  json.put("events","0");
  }
 out.println(json.toString());
%>