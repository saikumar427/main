<%
  String sername=request.getServerName();
%>

<%if("www.eventbee.com".equals(sername.toLowerCase())){%>
   <META HTTP-EQUIV="Refresh" CONTENT="0;URL=http://${domain}/main/user/login!tokenize?usertoken=${usertoken}&tokenId=${tokenId}">
<%}else{%>
   <META HTTP-EQUIV="Refresh" CONTENT="0;URL=http://<%=sername%>/main/user/login!tokenize?usertoken=${usertoken}&tokenId=${tokenId}">
 <%}%>     