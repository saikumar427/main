<%@ page import="java.util.*" %>
<%@page import="com.eventbee.general.DbUtil"%>
<%
String from=request.getParameter("from");
String to=request.getParameter("to");
if(from==null) from="";
if(to==null) to="";
String send=request.getParameter("submit");
ArrayList tidlist=new ArrayList();
if("send".equals(send)){
	String query="select tid from event_reg_transactions where to_char(transaction_date,'YYYY-MM-DD HH24:MI:SS') between ? and ?";
	List tids=DbUtil.getValues(query,new String[]{from,to});
	if(tids!=null)
	for(int i=0;i<tids.size();i++){
		String tid=(String)tids.get(i);
		String strayquery="select 'yes' from stray_email where html_message like ?";
		String st=DbUtil.getVal(strayquery,new String []{"%"+tid+"%"});
		if(!"yes".equals(st))
			tidlist.add(tid);
	}
}
if(tidlist.size()>0)
	out.println(tidlist);
else if("send".equals(send) && tidlist.size()==0) out.println("No result found");
%>
<form name="tidtrace" id="tidtrace" action="" method="post"> 
<table align="left">
<tr><td>From:&nbsp;
<input name="from" type="text" size="20" value="<%=from%>"></input></td>
<td>To:&nbsp;
<input name="to" type="text" size="20" value="<%=to%>"></input></td></tr>
<tr><td><input type=submit name="submit" value="send"/></td></tr>
</table>
</form>

