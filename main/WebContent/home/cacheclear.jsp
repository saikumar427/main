<%@page import="com.eventbee.general.helpers.EventInfoCache"%>
<%@ page import="com.eventbee.general.*"%>
<%
String purpose=request.getParameter("purpose");
if(purpose!=null && "eventlang".equals(purpose)){
	String eid=request.getParameter("eid");
	if(eid!=null){
		EventInfoCache.clearCache(eid+"_db_i18n_lang");
		out.println("Clearing EventInfoCache of i18n language for event: "+eid);
	}else{
		EventInfoCache.clearAllCache();
		out.println("Clearing EventInfoCache of i18n language for all events");
	}
}else{
EbeeCachingManager.clearAllCache();
out.println("EbeeCachingManager Cache Cleared");
}
%>