
<%@page import="com.eventbee.layout.EventGlobalTemplates"%>
<% 
System.out.println("in whenwidgettemplate.jsp");
StringBuffer whenwidget=new StringBuffer();
whenwidget.append("#if($replacekeywords)");
whenwidget.append("<table width=\'100%\' cellpadding=\'0\' cellspacing=\'0\'> <tr><td >$replacekeywords </td></tr>");
whenwidget.append("#if($calenderdata)<tr><td><img src=\'http://www.eventbee.com/home/images/addcal.png\' border=\'0\'/>$calenderdata</td></tr>#end</table>#end");
EventGlobalTemplates.setGlobalTemplates("global_template_whenwidget",whenwidget.toString());
%>