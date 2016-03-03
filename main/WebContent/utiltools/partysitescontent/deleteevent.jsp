<%@page import="com.eventbee.general.DbUtil" %>
<%
	String zone=request.getParameter("zone");
	String eid=request.getParameter("eid");
	String eventdate=request.getParameter("eventdate");
	String seqid=request.getParameter("seqid");
	DbUtil.executeUpdateQuery("delete from party_events where region=? and seq_id=to_number(?,'999999999')",new String[]{zone,seqid});
	String url="partyzonemanage.jsp?"+"zone="+zone+"&eventdate="+eventdate;
	response.sendRedirect(url);
%>
