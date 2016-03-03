<%@page import="com.eventbee.layout.EventGlobalTemplates"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
StringBuffer trackdata=new StringBuffer();
trackdata.append("<table><tr><td>#if($tracklogo)<img src='$tracklogo' width='150px'></img>#end</td>#if($narrow)</tr><tr>#end<td class='small'>#if($trackmsg)$trackmsg#end</td></tr></table>");
EventGlobalTemplates.setGlobalTemplates("global_template_trackpartner",trackdata.toString());      
%>