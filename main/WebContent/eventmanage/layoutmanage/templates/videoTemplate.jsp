<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.layout.EventGlobalTemplates"%> 
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<% 
System.out.println("in videos template.jsp");
StringBuffer vediosbuffer=new StringBuffer("");	
vediosbuffer.append("<style>" +
		".video-container {" +
		"position: relative;" +
		"padding-bottom: 56.25%;" +
		"padding-top: 30px;" +
		" height: 0; " +
		"overflow: hidden;" +
		"}" +
		".video-container iframe," +
		".video-container object," +
		".video-container embed {" +
		"position: absolute;" +
		"top: 0;" +
		"left: 0;" +
		"width: 100%;" +
		"height: 100%;" +
		"}" +
		"</style>"); 

vediosbuffer.append("#if($youtubeUrl)");
vediosbuffer.append("<div style='margin:-15px;width: calc(100% + 30px);' class=\"video-container\"><iframe width=\"100%\" src=\"//www.youtube.com/embed/$youtubeUrl\" frameborder=\"0\" allowfullscreen></iframe></div>");
vediosbuffer.append("#end");
vediosbuffer.append("#if($vimeoUrl)");
vediosbuffer.append("<div style='margin:-15px;width: calc(100% + 30px);' class=\"video-container\"><iframe src=\"//player.vimeo.com/video/$vimeoUrl\" width=\"100%\" height=\"auto\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe></div>");
vediosbuffer.append("#end");
EventGlobalTemplates.setGlobalTemplates("global_template_videos",vediosbuffer.toString());
%>