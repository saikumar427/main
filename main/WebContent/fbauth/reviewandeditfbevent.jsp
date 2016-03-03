<%@ page import="java.util.*" %>
<%@ page import="com.eventbee.general.*" %>
<script type="text/javascript" language="JavaScript" src="../js/fbconnect.js"></script>

<%
String groupid=request.getParameter("GROUPID");
String fbuid=request.getParameter("fbuid");
String config_id="0";
String bundleid=DbUtil.getVal("select value from config where name='ebee.fbconnect.publishfeed.editfbevent.bundleid' and config_id=to_number(?,'9999999999999999')",new String[]{config_id});
String mgrname=DbUtil.getVal("select login_name from authentication where to_number(user_id,'9999999999999')=(select mgr_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[]{groupid});	
//String eventurl=ShortUrlPattern.get(mgrname)+"/event?eventid="+groupid;
String eventbeeurl="http://www.eventbee.com";		
StringBuffer sb=new StringBuffer();
sb.append("select a.eventname,a.city,a.state,a.country,a.category,a.venue,a.description, ");
sb.append(" trim(to_char(a.start_date,'Day')) ||', '|| to_char(a.start_date,'Month DD, YYYY')  as start_date, starttime,endtime,");
sb.append("trim(to_char(a.end_date,'Day')) ||', '|| to_char(a.end_date,'Month DD, YYYY') as end_date,b.first_name,b.last_name,");
sb.append(" to_char(a.start_date,'yyyy') as start_yy,to_char(a.start_date,'mm') as start_mm, ");
sb.append(" to_char(start_date,'dd') as start_dd,");
sb.append(" starttime,endtime,");
sb.append(" to_char(to_timestamp('2003-10-10'||COALESCE(starttime,'00'),'yyyy-dd-mm HH24:MI'),'HH24') ");
sb.append(" as start_hh,");
sb.append(" to_char(to_timestamp('2003-10-10'||COALESCE(starttime,'00'),'yyyy-dd-mm HH24:MI'),'MI') as start_mi,");
sb.append(" to_char(end_date,'yyyy') as end_yy, to_char(end_date,'mm') as end_mm, ");
sb.append(" to_char(end_date,'dd') as end_dd, to_char(to_timestamp('2003-10-10 '||COALESCE(endtime,'00'),'yyyy-dd-mm HH24:MI'),'HH24')");
sb.append(" as end_hh,");                            
sb.append(" to_char(to_timestamp('2003-10-10 '||COALESCE(endtime,'00'),'yyyy-dd-mm HH24:MI'),'MI') as end_mi ");


sb.append("  from eventinfo a,user_profile b ");

sb.append("where eventid=CAST(? AS BIGINT) and a.mgr_id=to_number(b.user_id,'99999999999999999999')");

String QUERY=sb.toString();
String eventtype="";
String resvpeventstatus=DbUtil.getVal("select 'yes'  from config a,eventinfo b where a.config_id=b.config_id and eventid=CAST(? AS BIGINT) and a.name='event.rsvp.enabled' and a.value='yes'",new String[]{groupid});


String ticketingeventstatus=DbUtil.getVal("select 'yes'  from config a,eventinfo b where a.config_id=b.config_id and eventid=CAST(? AS BIGINT) and a.name='event.rsvp.enabled' and a.value='yes'",new String[]{groupid});

if("yes".equals(resvpeventstatus))
eventtype="rsvp";
else if("yes".equals(ticketingeventstatus))
eventtype="ticketingevent";
else
eventtype="none";

String textdesc=DbUtil.getVal("select description  from eventinfo where descriptiontype='text' and eventid=CAST(? AS BIGINT)",new String[]{groupid});

String timezone=DbUtil.getVal("select value from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.timezone'",new String[]{groupid});
String eventname="";
String category="";
String location="";
String venue="";
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
String starttime="";
String endtime="";
String description="";
DBManager dbmanager=new DBManager();
StatusObj statobj=dbmanager.executeSelectQuery(QUERY,new String [] {groupid});  
if(statobj.getStatus() && statobj.getCount()>0)  { 
	eventname=dbmanager.getValue(0,"eventname","");
	category=dbmanager.getValue(0,"category","");
	location=dbmanager.getValue(0,"venue","");
	city=dbmanager.getValue(0,"city","");
	start_date=dbmanager.getValue(0,"start_date","");
	end_date=dbmanager.getValue(0,"end_date","");
	host=dbmanager.getValue(0,"first_name","") +" "+ dbmanager.getValue(0,"last_name","");
	
	startyy=dbmanager.getValue(0,"start_yy","") ;
	startdd=dbmanager.getValue(0,"start_dd","") ;
	startmm=dbmanager.getValue(0,"start_mm","") ;
	endyy=dbmanager.getValue(0,"end_yy","") ;
	enddd=dbmanager.getValue(0,"end_dd","") ;
	endmm=dbmanager.getValue(0,"end_mm","") ;
	
	String stimes[]=GenUtil.strToArrayStr(dbmanager.getValue(0,"starttime",""),":");
	String etimes[]=GenUtil.strToArrayStr(dbmanager.getValue(0,"endtime",""),":");
	
	starthh=stimes[0];
	startmi=stimes[1];
	endhh=etimes[0];
	endmi=etimes[1];
	
	starttime=DateConverter.getTimeAM(dbmanager.getValue(0,"starttime","")) ;
	endtime=DateConverter.getTimeAM(dbmanager.getValue(0,"endtime","") );
	
	
	description=dbmanager.getValue(0,"description","") ;
	String timezone1=EbeeConstantsF.get("Facebook.publishevent.time.zone","SystemV/PST8");
	 TimeZone T1=new SimpleTimeZone(TimeZone.getTimeZone(timezone1).getRawOffset(), timezone1);

	Calendar calendar =Calendar.getInstance(T1); 
	                calendar.set(Integer.parseInt(startyy),
	                                        Integer.parseInt(startmm)-1,
	                                        Integer.parseInt(startdd),
	                                        Integer.parseInt(starthh),
	                                        Integer.parseInt(startmi));
	


 StartDate=calendar.getTime();



Calendar calendar1 =Calendar.getInstance(T1); 
calendar1.set(Integer.parseInt(endyy),
				Integer.parseInt(endmm)-1,
				Integer.parseInt(enddd),
				Integer.parseInt(endhh),
				Integer.parseInt(endmi));



 endDate=calendar1.getTime();


       
}
String eventURL=null;
String username=DbUtil.getVal("select login_name from authentication where to_number(user_id,'9999999999999') =(select mgr_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[]{groupid});

	if(groupid!=null){
		eventURL=DbUtil.getVal("select url from event_custom_urls where eventid=?",new String[]{groupid});
		if(eventURL==null){
			mgrname=DbUtil.getVal("select login_name from authentication where to_number(user_id,'9999999999999')=(select mgr_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[]{groupid});
			if(mgrname!=null){
				eventURL=ShortUrlPattern.get(mgrname)+"/event?eid="+groupid;
			}
		}
			
	}
String fbeventid=DbUtil.getVal("select fbeventid from ebee_fb_publishevent where eventid=?",new String[]{groupid});

String eventurl="http://www.facebook.com/event.php?eid="+fbeventid;
%>


<form id="eventinformation" method="post" name="eventinformation" action="../fbauth/editfbevent.jsp" onsubmit="validateeventinformation(); return false;">
<input type="hidden" name="StartDate" value="<%=StartDate.getTime()/1000%>" >
<input type="hidden" name="endDate" value="<%=endDate.getTime()/1000%>" >
<input type="hidden" name="fbuid" value="<%=fbuid%>" >
<input type="hidden" name="groupid" value="<%=groupid%>" >
<input type="hidden" name="username" id="username"  value="<%=username%>" >
<input type="hidden" name="bundleid" id="bundleid"  value="<%=bundleid%>" >
<input type="hidden" name="eventurl" id="eventurl"  value="<%=eventurl%>" >
<input type="hidden" name="eventbeeurl" id="eventbeeurl"  value="<%=eventbeeurl%>" >


<table>
<tr><td colspan="2"  id="error"></td></tr>


<tr><td >Event Name:&nbsp;</td>
<td><input type="text"  value="<%=eventname%>" name="eventname" id="eventname" size="66"></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>

<tr>
	<td class="label" valign="top">Category:</td>
	<td>
		<select name="category" id="category"  >
					<option value="">Select Category</option>
					<option value="1">Party</option>
					<option value="2">Causes</option>
					<option value="3">Education</option>
					<option value="4">Meetings</option>
					<option value="5">Music/Arts</option>
					<option value="6">Sports</option>
					<option value="7">Trips</option>
					<option value="8">Other</option>			
				</select>
					
				Subcategory: <select name="subcategory" id="subcategory" >
					<option value="">Select Sub Category</option>
					<option value="1">Birthday Party</option>
					<option value="2">Cocktail Party</option>
					<option value="3">Club Party</option>
					<option value="4">Concert</option>
					<option value="5">Fraternity/Sorority Party</option>
					<option value="6">Business Meeting</option>
					<option value="7">Barbecue</option>
					<option value="8">Card Night</option>
					<option value="9">Dinner Party</option>
					<option value="10">Holiday Party</option>
					<option value="11">Night of Mayhem</option>
					<option value="12">Movie/TV Night</option>
					<option value="13">Drinking Games</option>
					<option value="14">Bar Night</option>
					<option value="15">LAN Party</option>
					<option value="16">Study Group</option>
					<option value="17">Mixer</option>
					<option value="18">Slumber Party</option>
					<option value="19">Erotic Party</option>
					<option value="20">Benefit</option>
					<option value="21">Goodbye Party</option>
					<option value="21">Business Meeting</option>
					<option value="22">House Party</option>
					<option value="23">Reunion</option>
					<option value="24">Fundraiser</option>
					<option value="25">Protest</option>
					<option value="26">Rally</option>
					<option value="27">Class</option>
					<option value="28">Lecture</option>
					<option value="29">Office Hours</option>
					<option value="30">Workshop</option>
					<option value="31">Club/Group Meeting</option>
					<option value="32">Convention</option>
					<option value="33">Dorm/House Meeting</option>
					<option value="34">Informational Meeting</option>
					<option value="35">Audition</option>
					<option value="36">Exhibit</option>
					<option value="37">Jam Session</option>
					<option value="38">Listening Party</option>
					<option value="39">Opening</option>
					<option value="40">Performance</option>
					<option value="41">Preview</option>
					<option value="42">Recital</option>
					<option value="43">Rehearsal</option>
					<option value="44">Pep Rally</option>
					<option value="45">Pick-Up</option>
					<option value="46">Sporting Event</option>
					<option value="47">Sports Practice</option>
					<option value="48">Tournament</option>
					<option value="49">Camping Trip</option>
					<option value="50">Daytrip</option>
					<option value="51">Group Trip</option>
					<option value="52">Roadtrip</option>
					<option value="53">Carnival</option>
					<option value="54">Ceremony</option>
					<option value="55">Festival</option>
					<option value="56">Flea Market</option>
					<option value="57">Retail</option>
		</select>       
    
	</td>
</tr>
<!--

<tr>
<td class="label" valign="top">Category:</td>
<td ><input type="text" readonly value="<%=category%>" name="category" id="category" size="66"></td>
</tr>
-->
<tr><td colspan="2" height="10"></td></tr>
<!--<tr><td >Subcategory:&nbsp;</td>
<td><input type="text" readonly value="<%=subcategory%>" name="subcategory" id="subcategory" size="66"></td>
</tr-->
<!--
<input type="hidden" name="subcategory" value="<%=subcategory%>" >
-->
<tr><td colspan="2" height="10"></td></tr>
<tr><td >Host:&nbsp;</td>
<td><input type="text"  value="<%=host%>" name="host" id="host" size="66"></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >Location:&nbsp;</td>
<td><input type="text"  value="<%=location%>" name="location" id="location" size="66"> </td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >City:&nbsp;</td>
<td><input type="text"  value="<%=city%>" name="city" id="city" size="66"></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >Start Time:&nbsp;</td>
<td><%=start_date%> <%=starttime%></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td>End Time:&nbsp;</td>
<td><%=end_date%> <%=endtime%></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td >Description:&nbsp;</td>
<%
String msg="";
if("rsvp".equals(eventtype))
msg="For Registration";

else
msg="For tickets and registration";


if(textdesc!=null)
textdesc=textdesc;
else
textdesc="";
%>
<td><textarea name="description" id="description" rows=5 cols=50  ><%=textdesc%>

<%=msg%>, please visit
<%=eventURL%></textarea></td>
</tr>
<tr><td colspan="2" height="10"></td></tr>
<tr><td ></td>
<td align="center"><input type="submit"  value="Publish To Facebook" name="submit">
<input value="Cancel" name="Cancel" type="button" onClick="hidewindow();"/></td>
</tr>
</table>
</form>

