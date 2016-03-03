<%@ page import="com.eventbee.namedquery.*,org.json.*"%>
<%
JSONObject obj = new JSONObject();
NQDbManager.loadAllQueries();
obj.put("status","success");
out.println(obj);
%>