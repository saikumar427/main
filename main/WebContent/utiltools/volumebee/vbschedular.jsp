<%@page import="java.util.Timer,java.util.TimerTask"%>
<%@page import="com.eventbee.general.eschedular.VBSchedular" %>
<%! String timeinterval = "5"; %>
<%
 	VBSchedular vbschedular = VBSchedular.getTimerInstance();
 	if(request.getParameter("stop")!=null){
 		vbschedular.stopSchedular();
 	}
 	if(request.getParameter("start")!=null){
 		if(request.getParameter("interval")!=null)
 			timeinterval=request.getParameter("interval");
 		
 		vbschedular.runSchedular(Integer.parseInt(timeinterval)*60*1000);
	}
 	String status = vbschedular.getStatus();
 	
 %>
 <html>
 <body>
 <form name="vbsch" method="post" action="vbschedular.jsp">
 <table>
 <tr><td colspan="2"><b>Volumebee Schedular</b></td></tr>
 <tr><td width="10%"></td><td>
 <table>
 <tr><td>Current Status: <%=status%></td></tr>
 <tr>
 <td>Time Interval: <input type="text" name="interval" size="5" value="<%=timeinterval%>"/>min</td>
 <td><input type="submit" name="start" value="Start"/></td>
 <td><input type="submit" name="stop" value="Stop"/></td>
 </tr>
 </table>
 </td></tr>
 </table>
 </form>
 </body>
 </html>