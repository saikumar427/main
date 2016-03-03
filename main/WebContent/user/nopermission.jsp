<%@page import="com.event.helpers.I18n"%>
<%
String url="";
if(session.getAttribute("SESSION_USER")!=null){
	url="/main/user/login";
}
else if(session.getAttribute("SESSION_SUBMGR")!=null){
	url="/main/submanager/login";
} else url="#";
%>
<%=I18n.getString("global.somethig.wrong.please.lbl1")%> <a href="<%=url%>"><%=I18n.getString("global.somethig.wrong.please.lbl2")%></a>