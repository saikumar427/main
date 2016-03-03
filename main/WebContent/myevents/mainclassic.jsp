<%@page import="com.eventbee.general.EbeeConstantsF"%>
<%
String serveraddress=EbeeConstantsF.get("serveraddress","www.eventbee.com");
%>
<script>
var serveradd='<%=serveraddress%>';
window.location.href='http://'+serveradd+'/classic/user/login!authenticate?tokenId=${messageJson}';
</script>