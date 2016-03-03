<%@ page import="java.util.*" %>
<%@ page import="com.eventbee.general.*" %>
<%
String groupid=request.getParameter("GROUPID");
String fbuid=request.getParameter("fbuid");
StringBuffer sb=new StringBuffer();
sb.append("select a.eventname,a.city,a.state,a.country,a.category,");
sb.append(" to_char(a.start_date,'MM-DD-YYYY') as start_date, starttime,endtime,");
sb.append("trim(to_char(a.end_date,'Day')) ||', '|| to_char(a.end_date,'Month DD, YYYY') as end_date,b.first_name,b.last_name,");
sb.append(" to_char(a.start_date,'yyyy') as start_yy,to_char(a.start_date,'mm') as start_mm, ");
sb.append(" to_char(start_date,'dd') as start_dd,");

sb.append(" to_char(to_timestamp('2003-10-10'||COALESCE(starttime,'00'),'yyyy-dd-mm HH:MI'),'HH24') ");
sb.append(" as start_hh,");
sb.append(" to_char(to_timestamp('2003-10-10'||COALESCE(starttime,'00'),'yyyy-dd-mm HH:MI'),'MI') as start_mi,");
sb.append(" to_char(end_date,'yyyy') as end_yy, to_char(end_date,'mm') as end_mm, ");
sb.append(" to_char(end_date,'dd') as end_dd, to_char(to_timestamp('2003-10-10 '||COALESCE(endtime,'00'),'yyyy-dd-mm HH:MI'),'HH24')");
sb.append(" as end_hh,");                            
sb.append(" to_char(to_timestamp('2003-10-10 '||COALESCE(endtime,'00'),'yyyy-dd-mm HH:MI'),'MI') as end_mi ");


sb.append("  from eventinfo a,user_profile b ");

sb.append("where eventid=to_number(?,'9999999999999999') and a.mgr_id=to_number(b.user_id,'99999999999999999999')");

String QUERY=sb.toString();

String eventname="";
String category="";
String location="";
String city="";
String start_date="";
String end_date="";
String host="";
String subcategory="NA";
String starthh="";
String startmi="";
String startyy="";
String startdd="";
String startmm="";
String endhh="";
String endmi="";
String endyy="";
String enddd="";
String endmm="";
Date endDate=null;
Date StartDate=null;

DBManager dbmanager=new DBManager();
StatusObj statobj=dbmanager.executeSelectQuery(QUERY,new String [] {groupid});  
if(statobj.getStatus() && statobj.getCount()>0)  { 
	eventname=dbmanager.getValue(0,"eventname","");
	category=dbmanager.getValue(0,"category","");
	location=dbmanager.getValue(0,"state","")+", "+dbmanager.getValue(0,"country","");
	city=dbmanager.getValue(0,"city","");
	start_date=dbmanager.getValue(0,"start_date","");
	end_date=dbmanager.getValue(0,"end_date","");
	host=dbmanager.getValue(0,"first_name","") +" "+ dbmanager.getValue(0,"last_name","");
	
	starthh=dbmanager.getValue(0,"start_hh","") ;
	
	startmi=dbmanager.getValue(0,"start_mi","") ;
	startyy=dbmanager.getValue(0,"start_yy","") ;
	startdd=dbmanager.getValue(0,"start_dd","") ;
	startmm=dbmanager.getValue(0,"start_mm","") ;
	endhh=dbmanager.getValue(0,"end_hh","") ;
	endmi=dbmanager.getValue(0,"end_mi","") ;
	endyy=dbmanager.getValue(0,"end_yy","") ;
	enddd=dbmanager.getValue(0,"end_dd","") ;
	endmm=dbmanager.getValue(0,"end_mm","") ;
	
	Calendar calendar = new GregorianCalendar(Integer.parseInt(startyy),
	                                        Integer.parseInt(startmm)-1,
	                                        Integer.parseInt(startdd),
	                                        Integer.parseInt(starthh),
	                                        Integer.parseInt(startmi));
	


 StartDate=calendar.getTime();




Calendar calendar1 = new GregorianCalendar(Integer.parseInt(endyy),
				Integer.parseInt(endmm)-1,
				Integer.parseInt(enddd),
				Integer.parseInt(endhh),
				Integer.parseInt(endmi));



 endDate=calendar1.getTime();


        
}
String eventURL=null;
String mgrname="";

	if(groupid!=null){
		eventURL=DbUtil.getVal("select url from event_custom_urls where eventid=?",new String[]{groupid});
		if(eventURL==null){
			mgrname=DbUtil.getVal("select login_name from authentication where to_number(user_id,'99999999999999')=(select mgr_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[]{groupid});
			if(mgrname!=null){
				eventURL=ShortUrlPattern.get(mgrname)+"/event?eid="+groupid;
			}
		}
			
	}	
%>


<form id="eventinformation" method="post" name="eventinformation" action="../fbauth/validateeventinfo.jsp" onsubmit="validateeventinformation(); return false;">
<input type="hidden" name="StartDate" value="<%=StartDate.getTime()/1000%>" >
<input type="hidden" name="endDate" value="<%=endDate.getTime()/1000%>" >
<input type="hidden" name="fbuid" value="<%=fbuid%>" >
<input type="hidden" name="groupid" value="<%=groupid%>" >
<table>
<tr><td colspan="2"  id="error"></td></tr>
<tr><td colspan="2" align="center"><b>Event details</b></td></tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >Event name:&nbsp;</td>
<td><input type="text" readonly value="<%=eventname%>" name="eventname" id="eventname" size="66"></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >Category:&nbsp;</td>
<td><input type="text" readonly value="<%=category%>" name="category" id="category" size="66"></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >Subcategory:&nbsp;</td>
<td><input type="text" readonly value="<%=subcategory%>" name="subcategory" id="subcategory" size="66"></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >Host:&nbsp;</td>
<td><input type="text" readonly value="<%=host%>" name="host" id="host" size="66"></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >Location:&nbsp;</td>
<td><input type="text" readonly value="<%=location%>" name="location" id="location" size="66"> </td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >City:&nbsp;</td>
<td><input type="text" readonly value="<%=city%>" name="city" id="city" size="66"></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >Start time:&nbsp;</td>
<td><input type="text" readonly value="<%=start_date%>" name="startdate" id="startdate" size="66"></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td>End time:&nbsp;</td>
<td><input type="text" readonly value="<%=end_date%>" name="enddate" id="enddate" size="66"></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >Description:&nbsp;</td>
<td><textarea name="description" id="description" rows=5 cols=50 readonly >For more details visit, <%=eventURL%></textarea></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td ></td>
<td><input type="submit"  value="Publishhhh to facebook" name="submit"></td>
</tr>
</table>
</form>