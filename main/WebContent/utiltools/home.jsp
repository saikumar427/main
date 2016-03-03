<%@ page import="java.util.*,com.eventbee.namedquery.*"%>
<%
NQDbManager.loadAllQueries();
/*Set set = DbManager.queriesHash.keySet();
Iterator iter = set.iterator();
while(iter.hasNext()){
	String key = iter.next().toString();
	Query query = (Query) DbManager.queriesHash.get(key);
	out.println(query.getSql());
}*/
/*
HashMap inParams = new HashMap();
inParams.put("eventid","1234678");
//inParams.put("mgr_id","1234533");
inParams.put("eventname","my eve");
StatusObj statobj = DbUtil.executeQuery("ins1",inParams);
out.println("statobj"+statobj.getStatus());
*/
/*
HashMap inParams = new HashMap();
inParams.put("eventid","1234534");
StatusObj statobj = DbUtil.executeQuery("deleteinfo",inParams);
out.println("statobj"+statobj.getStatus());*/

/*HashMap inParams = new HashMap();
inParams.put("eventid","1234534");
inParams.put("eventname","my event");
StatusObj statobj = DbUtil.executeQuery("updateinfo",inParams);
out.println("statobj"+statobj.getStatus()+":"+statobj.getCount());*/

/*HashMap inParams = new HashMap();
HashMap outParams = new HashMap();
inParams.put("mgr_id","12857");
outParams.put("eventid","eventid");
DbHelper dbhelper = new DbHelper("");
List list = dbhelper.getDataList("slevname2",inParams,outParams);
if(list!=null){
	out.println("<table>");
	for(int i=0;i<list.size();i++){
		out.println("<tr>");
		HashMap hmap = (HashMap) list.get(i);
		Set set = hmap.keySet();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			String key = iter.next().toString();
			String value = hmap.get(key).toString();
			out.println("<td>key:"+key+"</td>");
			out.println("<td>value"+value+"</td>");
		}
		out.println("</tr>");
	}
	out.println("</table>");
}*/
/*HashMap inParams = new HashMap();
inParams.put("mgr_id","13852");
DbHelper dbhelper = new DbHelper();
List list = dbhelper.getDataColumnList("slevname",inParams);
if(list!=null){
for(int i=0;i<list.size();i++){
	out.println("<br>"+list.get(i).toString());
}
}*/

NQDbHelper dbhelper = new NQDbHelper();
HashMap inParams = new HashMap();
inParams.put("eventid","14016");
HashMap hmap = dbhelper.getDataHash("slmgr1",inParams);
Set set = hmap.keySet();
Iterator iter = set.iterator();
while(iter.hasNext()){
	String key = iter.next().toString();
	String value = hmap.get(key).toString();
	out.println("Hash: <br>"+value);
}
%>

