<%@ page import="com.eventbee.general.DbUtil"%>
<%@ page import="org.json.*"%>

<%
String seqID=request.getParameter("seqID");
String eid=request.getParameter("eid");
System.out.println("seqID=="+seqID);
System.out.println("eid=="+eid);
String Status=DbUtil.getVal("select status  from CCProcessing_details where refid=CAST(? AS BIGINT) and regid=to_number(?,'99999999999999')",new String[]{eid,seqID});
System.out.println("Status=="+Status);

JSONObject obj=new JSONObject();
obj.put("status",Status);

out.println(obj.toString());
%>