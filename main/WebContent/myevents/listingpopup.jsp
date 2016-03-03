<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.myevents.actions.AddEventAction"%>
<%@page import="java.util.Map"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.HashMap"%>
<%-- <html>
<head>
</head>
<body>
<s:form  method="post">
<table align="center">
<tr>
<s:if test="%{msgType=='donot_allow'}">
<td>Your event listing limit crossed.  Please contact <a href="#" onClick="initSupportPopup()">support@eventbee.com</a></td>
</s:if>
<s:elseif test="%{msgType=='donot_allow_events'}">
<td>You have listed too many events on the same day. Further listings are blocked for the time being. Please contact <a href="#" onClick="initSupportPopup()">support@eventbee.com</a></td>
</s:elseif>
</tr>
</table>
</s:form>

</body>
</html> --%>

<div class="col-xs-12 col-sm-12">
	<div class="row">
		<div class="col-xs-12">
		<s:form  method="post">		
		<s:if test="%{msgType=='donot_allow'}">
		Your event listing limit crossed.  Please contact <a href="#" onClick="initSupportPopup()">support@eventbee.com</a>
		</s:if>
		<s:elseif test="%{msgType=='donot_allow_events'}">		
		You have listed too many events on the same day. Further listings are blocked for the time being. Please contact <a href="#" onClick="initSupportPopup()">support@eventbee.com</a>
		</s:elseif>		
		</s:form>
		</div>
	</div>
</div>
<%
String yaddress = AddEventAction.getIpFromRequest(request);
com.eventbee.util.CoreConnector cc1=null;
Map resMap=null;
String data="";
JSONObject json=new JSONObject();
HashMap<String,String> inputparams=new HashMap<String,String>();		
inputparams.put("mode","add");
inputparams.put("ip",yaddress);
inputparams.put("allow","allow");
try{
cc1=new com.eventbee.util.CoreConnector("http://"+com.eventbee.general.EbeeConstantsF.get("s1","www.eventbee.com")+"/main/utiltools/blockedManager.jsp");
cc1.setArguments(inputparams);
cc1.setTimeout(50000);
data=cc1.MGet();
cc1=new com.eventbee.util.CoreConnector("http://"+com.eventbee.general.EbeeConstantsF.get("s2","www.eventbee.com")+"/main/utiltools/blockedManager.jsp");
cc1.setArguments(inputparams);
cc1.setTimeout(50000);
data=cc1.MGet();
}catch(Exception e){
System.out.println("Error While Processing Request::"+e.getMessage());		
}		
%>