<%@page import="com.eventbee.layout.EventGlobalTemplates"%>
<%
   StringBuffer wherebfr=new StringBuffer("");
   String  GOOGLEMAP="<iframe src='$serveraddress/customevents/googlemap_js.jsp?lon=$longitude&lat=$latitude'  frameborder='0' height='260' width='260' marginheight='0' marginwidth='0' name='googlemap' scrolling='no'></iframe>";
   String  gmstring="http://maps.google.com/maps?q=$address$city$state$country";
   String mapstring="<a href="+gmstring+"> Map and driving directions</a>";
   wherebfr.append("#if($isWhere)");
   wherebfr.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tr><td >");
   wherebfr.append("$content<br/>");
   wherebfr.append("#if($isgMap)");
   wherebfr.append("<div><img src=\"$serveraddress/home/images/viewmap.png\" border=\"0\"/>"+mapstring+"</div>");
   wherebfr.append(GOOGLEMAP);
   wherebfr.append("#end");
   wherebfr.append("</td></tr></table>");
   wherebfr.append("#end");
   EventGlobalTemplates.setGlobalTemplates("global_template_wherewidget", wherebfr.toString());
%>