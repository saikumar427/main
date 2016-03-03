<%@page import="com.eventbee.general.*,org.json.*" %>
<%@ page import="java.util.*,java.text.SimpleDateFormat" %>
<%! String limit = "5";%>
<%!
public String getRawResponseData(String url,String[] params,String [] values,String method){
	com.eventbee.util.CoreConnector conector=new com.eventbee.util.CoreConnector(url);
	conector.setArguments(values,params);
	conector.setTimeout(30000);
	if("get".equals(method))
		return conector.MGet();
	else
		return conector.MPost();
}
%>
<%
String fromdate=request.getParameter("fromdate");
String todate=request.getParameter("todate");
Date date = new Date();
String DATE_FORMAT = "yyyy-MM-dd";
SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
if(fromdate==null){
GregorianCalendar gc = new GregorianCalendar();
int dayBefore = gc.get(Calendar.DAY_OF_YEAR);
gc.roll(Calendar.DAY_OF_YEAR, -1);
int dayAfter = gc.get(Calendar.DAY_OF_YEAR);
if(dayAfter > dayBefore) {
    gc.roll(Calendar.YEAR, -1);
}
gc.get(Calendar.DATE);
java.util.Date yesterday = gc.getTime();
fromdate =sdf.format(yesterday);
todate=sdf.format(date);
}
if(request.getParameter("submit")!=null){
	
	System.out.println("\n execution starting for geocodes.jsp...");
	try{
		
		String urlstring="http://maps.google.com/maps/api/geocode/json";
		int updatedcount=0;
		String query="select address1,address2,city,state,eventid from eventinfo where (longitude is null or latitude is null) and created_at between to_timestamp(?,'yyyy-MM-dd') and to_timestamp(?,'yyyy-MM-dd') order by created_at desc ";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(query,new String []{fromdate,todate});
		out.println(statobj.getCount()+" record(s) selected. <br>");
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
				String eventid = dbmanager.getValue(i,"eventid","");
				String address1 = dbmanager.getValue(i,"address1","");
				String address2 = dbmanager.getValue(i,"address2","");
				String city = dbmanager.getValue(i,"city","");
				String state = dbmanager.getValue(i,"state","");
				String addressstring=address1+","+address2+","+city+","+state+"";
				String geoResponse=getRawResponseData(urlstring,new String[]{"address","sensor"},new String[] {addressstring,"false"},"get");
				if(geoResponse!=null){
					JSONObject obj=new JSONObject();
					obj=(JSONObject)new JSONTokener(geoResponse).nextValue();
					JSONArray ja=obj.getJSONArray("results");
					System.out.println("\n eventid: "+eventid+" ja.length: "+ja.length());
					if(ja.length() > 0){
						String lng =ja.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
						String lat =ja.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
						
						if(lng != null && lat != null){
							StatusObj stobj=DbUtil.executeUpdateQuery("update eventinfo set latitude=?,longitude=? where eventid=CAST(? AS BIGINT)",new String []{lat,lng,eventid});
							if(stobj.getStatus())
								updatedcount++;
						}
					}	
				}
			}
		}
		
		out.println(updatedcount+" record(s) updated.");
	}catch(Exception e){
		System.out.println("\n Exception in geocodes.jsp: "+e.getMessage());
	}
}
%>
<html>
 <body>
 <form name="geocodes" method="post" action="geocodes.jsp">
 <table>
 <tr><td width="10%"></td><td>
 <table>
 <tr>
 <td>Date&nbsp;From: <input type="text" name="fromdate" size="20" value="<%=fromdate%>"/>&nbsp;(yyyy-mm-dd)</td>
 <td>To: <input type="text" name="todate" size="20" value="<%=todate%>"/>&nbsp;(yyyy-mm-dd)</td>
 <td><input type="submit" name="submit" value="Submit"/></td>
 </tr>
 
 </table>
 </td></tr>
 </table>
 </form>
 </body>
 </html>