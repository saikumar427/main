<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DbUtil" %>
<%
	String zone=request.getParameter("zone");
	String eventdate=request.getParameter("eventdate");
	String eventid=request.getParameter("eid");
	String eventname=request.getParameter("ename");
	String address=request.getParameter("address");
	String button_label=request.getParameter("bname");
	String external_url=request.getParameter("exturl");
	String position=request.getParameter("position");
	String day=request.getParameter("day");
	String month=request.getParameter("month");
	String year=request.getParameter("year");
	String edate=year+"-"+month+"-"+day;
	String add=request.getParameter("add");
	String url="partyzonemanage.jsp?"+"zone="+zone+"&eventdate="+edate;
	if("yes".equals(add)){
		String seqid=DbUtil.getVal("select nextval('zone_event_seq')",null);
		DbUtil.executeUpdateQuery("insert into party_events(seq_id,region,eventid,eventdate,eventname,address,button_label,external_url,position) values(to_number(?,'99999999999'),?,CAST(? AS BIGINT),to_date(?,'YYYY-MM-DD'),?,?,?,?,to_number(?,'99999999'))",new String[]{seqid,zone,eventid,edate,eventname,address,button_label,external_url,position});
	}else{
		String seqid=request.getParameter("seqid");
		StatusObj sb= DbUtil.executeUpdateQuery("delete from party_events where region=?  and seq_id=to_number(?,'99999999999')",new String[]{zone,seqid});
		DbUtil.executeUpdateQuery("insert into party_events(seq_id,region,eventid,eventdate,eventname,address,button_label,external_url,position) values(to_number(?,'999999999'),?,to_number(?,'9999999999'),to_date(?,'YYYY-MM-DD'),?,?,?,?,to_number(?,'99999999'))",new String[]{seqid,zone,eventid,edate,eventname,address,button_label,external_url,position});
	}	
	response.sendRedirect(url);
%>
