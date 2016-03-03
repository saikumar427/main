<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.layout.EventGlobalTemplates"%>
<% 
System.out.println("in WhosPromotingTemplate.jsp");
StringBuffer whospromoting=new StringBuffer("");
whospromoting.append("<div id='whospromotingdata'></div><script type='text/javascript' src='/main/js/layoutmanage/whospromotingwidget.js'></script>");
whospromoting.append("<script>getWhosPromotingData($eid);</script>");
EventGlobalTemplates.setGlobalTemplates("global_template_whospromoting",whospromoting.toString());
%> 