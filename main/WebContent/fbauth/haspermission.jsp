<%@ page import="com.google.code.facebookapi.*"%>
<%@ page import="java.util.*,com.eventbee.general.*"%>

<%
String fbuid=request.getParameter("fbuid");

Long uid= new Long(fbuid);
boolean hasPermission=false;
String fbsessionkey=request.getParameter("sessionkey");

session.setAttribute("FB_SESSION_KEY", fbsessionkey);
fbsessionkey= (String)session.getAttribute("FB_SESSION_KEY");
String fbconnapi=(String)session.getAttribute("FBCONNECTAPIKEY");
if(fbconnapi==null){
	fbconnapi=DbUtil.getVal("select value from config where name='ebee.fbconnect.api' and config_id=to_number(?,'9999999999999999999')",new String[]{"0"});
	session.setAttribute("FBCONNECTAPIKEY", fbconnapi);
}
String fbconnsecretkey=(String)session.getAttribute("FBCONNECTSECRETKEY");
if(fbconnsecretkey==null){
	fbconnsecretkey=DbUtil.getVal("select value from config where name='ebee.fbconnect.secretkey' and config_id=to_number(?,'9999999999999999999')",new String[]{"0"});
	session.setAttribute("FBCONNECTSECRETKEY", fbconnsecretkey);
}
String permission_status="NO";
try{
	FacebookJaxbRestClient client= new FacebookJaxbRestClient(fbconnapi,fbconnsecretkey,fbsessionkey);
	hasPermission=client.users_hasAppPermission(Permission.CREATE_EVENT, uid);
	if(hasPermission)
		permission_status="<status>ALREADY_GRANTED</status>";
}catch(Exception fbEx){
	System.out.println(fbEx.getMessage());
}
%>
<%=permission_status%>

