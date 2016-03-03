<%@ page import="com.eventbee.general.*"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="java.util.HashMap"%>
<%! 
public String getLatestEventJson(){
JSONObject jobj=new JSONObject();
try{
String query="select a.eventid,eventname from event_reg_transactions  b, eventinfo a where a.status='ACTIVE'  and a.listtype='PBL' and  b.paymenttype!='RSVP' and CAST(b.eventid AS BIGINT)= a.eventid order by b.transaction_date desc  limit 1";
//System.out.println("getting latest ticket sold data from DB");
DBManager db= new DBManager();
StatusObj sb=db.executeSelectQuery(query,new String[]{});

if(sb.getStatus()){
String eid=db.getValue(0,"eventid","");
String name=db.getValue(0,"eventname","");
if(name.length()>90){
name=name.substring(0,90)+"...";
}
jobj.put("name",name);
jobj.put("id",eid);
jobj.put("link","/event?eid="+eid);
jobj.put("label","<img src='/home/images/ticket.gif'  alt='Ticket Sold' /> Ticket sold just now at ");
}
else{
return null;
}
}
catch(Exception e){
System.out.println("exception in getlatestevent:"+e.getMessage());
return null;
}
return jobj.toString();	
}
%>
<%
String json="";
boolean isInCache=EbeeCachingManager.checkCache("latestevent",60000);
if(!isInCache){
	json=getLatestEventJson();
	if(json!=null)
	EbeeCachingManager.put("latestevent",getLatestEventJson());
	}
json=(String)EbeeCachingManager.get("latestevent");
if(json==null)
	json="{link:'',name:'',label:''}";
out.println(json);
%>