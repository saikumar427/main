
<%@page import="com.eventbee.general.DbUtil"%>
<%@ page import="com.event.beans.EventData,org.json.JSONObject" %>
<%@ page import="com.event.dbhelpers.TransactionDB,com.event.dbhelpers.EventDB"%>
<%	
	String trnsid = request.getParameter("trnsid");
	String eventid = request.getParameter("eventid");
	String mailto = request.getParameter("mailto");
	JSONObject jsonObj = new JSONObject();
	if(eventid != null && trnsid != null){
		EventData edata=EventDB.getEventData(eventid);
		String powertype = edata.getPowertype();
		int status = TransactionDB.resendingDirectMail(eventid,trnsid,powertype,mailto);
		if(status == 1)
			jsonObj.put("status","success");
		else
			jsonObj.put("status","failure");
	}
	out.println(jsonObj);
%>