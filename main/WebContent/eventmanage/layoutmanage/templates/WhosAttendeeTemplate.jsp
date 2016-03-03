<%@page import="com.eventbee.general.EbeeConstantsF"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.layout.EventGlobalTemplates"%>
<% 
System.out.println("in WhosAttendeeTemplate.jsp");
StringBuffer whosattending=new StringBuffer("");
whosattending.append("<script type='text/javascript' src='/main/js/layoutmanage/whosattending.js'></script>");
whosattending.append("<script type='text/javascript' src='$serveraddress/home/js/advajax.js'></script>");
whosattending.append("#if($isrecur)#if($nodates)<select onchange=showAttendeesList($eid,$configData); id='event_date' name='event_date' style='display: block;'></select>#else $selectdata #end</br>#end");
whosattending.append("<div id='attendeeinfo'></div><script>showAttendeesList($eid,$configData);</script>");
EventGlobalTemplates.setGlobalTemplates("global_template_whosattending",whosattending.toString());
%> 