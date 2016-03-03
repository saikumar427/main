<%@ page import="java.util.*,com.user.actions.*,com.eventbee.general.GenUtil,com.user.dbhelpers.MyTicketsActionDB" %>
<%
int status=0;
String arr[]={};
String temp="";
String tids=request.getParameter("tidlist");
String send=request.getParameter("submit");
if("send".equals(send)){
arr=GenUtil.strToArrayStr(tids,",");
for(int i=0;i<arr.length;i++){
	
	 status=MyTicketsActionDB.resendDirectEmail(arr[i].trim());
	 temp=temp+arr[i].trim();
	 if(i!=arr.length-1)
		 temp=temp+",";
System.out.println("the status of "+arr[i]+" is "+status);
}
}
%>

<form name="resend" id="resend" action="" method="post"> 
<table align="left"><tr><td>
<%if(status==1 || status==2){
	%>
<h6>Sent Succesfully</h6>
<%} %> 
</td></tr>
<tr><td>
<input name="tidlist" type="text" size="100" value="<%=temp%>"></input></td></tr>
<tr><td><input type=submit name="submit" value="send"/></td></tr>
</table>
</form>

