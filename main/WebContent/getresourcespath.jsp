<%@ page import="com.eventbee.general.EbeeConstantsF"%>
<%
   String resourceaddress="";
  if("https".equalsIgnoreCase(request.getHeader("x-forwarded-Proto")))
     resourceaddress="//"+EbeeConstantsF.get("resourcessslserveraddress","localhost");
  else
       resourceaddress="//"+EbeeConstantsF.get("resourceserveraddress","localhost");
%>
