<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.layout.EventGlobalTemplates"%> 
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<% 
System.out.println("in descriptionTemplate.jsp");
StringBuffer discriptionbuffere=new StringBuffer("");	
discriptionbuffere.append("#if($height)");
discriptionbuffere.append("<div style=\"overflow: auto;" +
			"height:$height px;" +
			"overflow-y:auto;"+
		"background:"+
			"linear-gradient(rgba(255,255,255,0), rgba(255,255,255,0)),"+
			"linear-gradient(rgba(255,255,255,0), rgba(255,255,255,0)) 0 100%,"+
			"radial-gradient(50% 0, farthest-side, rgba(0,0,0,.2), rgba(0,0,0,0)),"+
			"radial-gradient(50% 100%,farthest-side, rgba(0,0,0,.2), rgba(0,0,0,0)) 0 100%;"+
		"background:"+
			"linear-gradient(rgba(255,255,255,0), rgba(255,255,255,0)),"+
			"linear-gradient(rgba(255,255,255,0), rgba(255,255,255,0)) 0 100%,"+
			"radial-gradient(farthest-side at 50% 0, rgba(0,0,0,.2), rgba(0,0,0,0)),"+
			"radial-gradient(farthest-side at 50% 100%, rgba(0,0,0,.2), rgba(0,0,0,0)) 0 100%;"+
		"background-repeat: no-repeat;"+
		"background-size: 100% 40px, 100% 40px, 100% 14px, 100% 14px;"+
		"background-attachment: local, local, scroll, scroll;\">$description</div>");
discriptionbuffere.append("#else");
discriptionbuffere.append("$description");
discriptionbuffere.append("#end");

EventGlobalTemplates.setGlobalTemplates("global_template_description",discriptionbuffere.toString());
%>