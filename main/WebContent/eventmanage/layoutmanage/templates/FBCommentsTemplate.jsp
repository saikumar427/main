<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.layout.EventGlobalTemplates"%> 
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<% 
System.out.println("in fbcomments.jsp");
StringBuffer fbcommentsbuffer=new StringBuffer("");	
fbcommentsbuffer.append("<div class=\"fb-comments\" data-numposts=\"$numPosts\" data-colorscheme=\"$colorScheme\" data-href=\"$eventUrl\" data-width=\"580\" data-colorscheme=\"light\"></div>");
EventGlobalTemplates.setGlobalTemplates("global_template_fbcomments",fbcommentsbuffer.toString());
%>