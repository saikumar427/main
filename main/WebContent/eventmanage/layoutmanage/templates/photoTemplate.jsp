<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.layout.EventGlobalTemplates"%> 
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<% 
System.out.println("in photo template.jsp");
StringBuffer photobuffer=new StringBuffer("");

photobuffer.append("#if($photoLink)"); 
photobuffer.append( "<a target='_blank' href=\"$photoLink\"><img style='margin:-15px;width: calc(100% + 30px);' src=\"$photoUrl\" width='100%' /></a>");
photobuffer.append("#else");
photobuffer.append("<img style='margin:-15px;width: calc(100% + 30px);' src=\"$photoUrl\" width='100%' />");
photobuffer.append("#end");
EventGlobalTemplates.setGlobalTemplates("global_template_photos",photobuffer.toString());
%>