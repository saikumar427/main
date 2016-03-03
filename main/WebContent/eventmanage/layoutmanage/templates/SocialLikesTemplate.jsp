<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.layout.EventGlobalTemplates"%> 
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<% 
System.out.println("in SocialLikes.jsp");
StringBuffer socialbuffer=new StringBuffer("");	
socialbuffer.append("#if($isRender)");
socialbuffer.append("<table width=\"100%\" cellpadding=\"5\" cellspacing=\"5\" ><tr> <td align=\"center\"><table><tr>");
socialbuffer.append("#if($twitUrl)");
socialbuffer.append("<td valign=\"bottom\" style=\"padding-bottom:2px;\" width=\"80px\"><iframe src='$twitUrl' id='twitter' name='twitter' frameborder='0' width='70px' height='75px' scrolling='no'></iframe></td>");
socialbuffer.append("#end");
socialbuffer.append("#if($fbUrl)");
socialbuffer.append("<td valign=\"middle\" width=\"60px\"><fb:like href='$fbUrl' send='false' layout='box_count' width='60' show_faces='true'></fb:like>$exthtml</td>" );
socialbuffer.append("#end");
socialbuffer.append("#if($googleUrl)");
socialbuffer.append("<td valign=\"bottom\" style=\"padding-bottom:2px;\" width=\"70px\"><iframe id='googleplusone' name='googleplusone' src='$googleUrl' frameborder='0' width='70px' height='75px' scrolling='no' style='margin-left:-10px'></iframe></td>");
socialbuffer.append("#end");
socialbuffer.append("</tr></table>");
socialbuffer.append("</tr></table>");
socialbuffer.append("#end");
EventGlobalTemplates.setGlobalTemplates("global_template_sociallikes",socialbuffer.toString());
%>