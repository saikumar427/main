<%@page import="com.eventbee.general.DbUtil" %>
<%
	String zone=request.getParameter("zone");
	String customtype=request.getParameter("customtype");
	int totalimages=Integer.parseInt(request.getParameter("totalimages"));
	DbUtil.executeUpdateQuery("delete from zone_event_images where region=?",new String[]{zone});
	for(int i=1;i<=totalimages;i++){
		String hreftemp="href"+i;
		String urltemp="url"+i;
		String href=request.getParameter(hreftemp);
		String url=request.getParameter(urltemp);
		DbUtil.executeUpdateQuery("insert into zone_event_images(region,image_src,image_href,position) values(?,?,?,to_number(?,'999999999')) ",new String[]{zone,url,href,i+""});
	}
	String url="partyzonemanage.jsp?"+"zone="+zone+"&customtype="+customtype;
	response.sendRedirect(url);
%>