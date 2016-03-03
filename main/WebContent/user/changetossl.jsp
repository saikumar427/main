<%
String sername=request.getServerName();
%>
<%if("www.eventbee.com".equals(sername.toLowerCase())){%>
<META HTTP-EQUIV="Refresh" CONTENT="0;URL=${schemeToUse}://${ssldomain}/main/user/login">
<%}else{%>
   <META HTTP-EQUIV="Refresh" CONTENT="0;URL=${schemeToUse}://<%=sername%>/main/user/login">
 <%}%>