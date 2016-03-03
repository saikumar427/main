<%@page import="com.eventbee.general.DbUtil" %>
<%
	String zone=request.getParameter("zone");
	String customtype=request.getParameter("customtype");
	int totalbanners=Integer.parseInt(request.getParameter("totalbanners"));
	DbUtil.executeUpdateQuery("delete from zone_event_topbanners where region=?",new String[]{zone});
	for(int i=1;i<=totalbanners;i++){
		String eidtemp="eventid"+i;
		String urltemp="url"+i;
		String buttontemp="buttonlabel"+i;
		String eventid=request.getParameter(eidtemp);
		String url=request.getParameter(urltemp);
		String buttonlabel=request.getParameter(buttontemp);
		DbUtil.executeUpdateQuery("insert into zone_event_topbanners(region,image_src,eventid,button_label,position) values(?,?,CAST(? AS BIGINT),?,to_number(?,'999999999')) ",new String[]{zone,url,eventid,buttonlabel,i+""});
	}
	String url="partyzonemanage.jsp?"+"zone="+zone+"&customtype="+customtype;
	response.sendRedirect(url);
%>