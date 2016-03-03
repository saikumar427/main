<%@page import="com.eventbee.layout.DBHelper"%>
<%@page import="com.eventbee.buyer.att.layout.BuyerAttHelper"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String data = request.getParameter("data");
String type = request.getParameter("type");
String eventid = request.getParameter("eid");
if(eventid==null || "".equals(eventid))
	eventid = request.getParameter("eventid");
String widgetid = request.getParameter("widgetid");
String title = request.getParameter("title");
String ref_title = request.getParameter("ref_title");
String layout_type = request.getParameter("layout_type");
System.out.println("the type is::"+type);

if("layout".equals(type)) {
	if(DBHelper.saveLayout(data))
		out.println(new JSONObject().put("status", "success"));
	else
		out.println(new JSONObject().put("status", "failed"));
} else if("globalStyle".equals(type)) {
	if(DBHelper.saveGlobalStyles(data,eventid))
		out.println(new JSONObject().put("status", "success"));
	else
		out.println(new JSONObject().put("status", "failed"));
} else if("allStyle".equals(type)) {
	if(DBHelper.allStyleSettings(data,eventid))
		out.println(new JSONObject().put("status", "success"));
	else
		out.println(new JSONObject().put("status", "failed"));
}  
else if("widgetStyle".equals(type)){ 
	if(DBHelper.saveWidgetStyles(data, eventid, widgetid))
		out.println(new JSONObject().put("status", "success"));
	else
		out.println(new JSONObject().put("status", "failed"));
} else if("widgetOptions".equals(type)) {
	if(DBHelper.saveWidgetOptions(data, eventid, widgetid,title, ref_title)) 
		out.println(new JSONObject().put("status", "success"));
	else
		out.println(new JSONObject().put("status", "failed"));
} else if("saveHeaderTheme".equals(type)) {
	if(DBHelper.saveHeaderTheme(data, eventid))
		out.println(new JSONObject().put("status", "success"));
	else
		out.println(new JSONObject().put("status", "failed")); 
} else if("applyHeaderTheme".equals(type)) {
	if(DBHelper.ApplyHeaderTheme(data, eventid))
		out.println(new JSONObject().put("status", "success"));
	else
		out.println(new JSONObject().put("status", "failed"));
}
 else if("deleteWOp".equals(type)) {
	if(DBHelper.deteWidgetOption(widgetid, eventid))
		out.println(new JSONObject().put("status", "success"));
	else
		out.println(new JSONObject().put("status", "failed"));
}
 else if("saveRefTiltle".equals(type)) {
		if(DBHelper.saveRefTitle(eventid, title, widgetid))
	out.println(new JSONObject().put("status", "success"));
		else
	out.println(new JSONObject().put("status", "failed"));
	}
 else if("hideWidget".equals(type)) {
		if(DBHelper.saveHide(eventid, data))
	out.println(new JSONObject().put("status", "success"));
		else
	out.println(new JSONObject().put("status", "failed"));
	} 
 else if("resetLastFinal".equals(type)) {
		if(DBHelper.resetLastFinal(eventid))
	out.println(new JSONObject().put("status", "success"));
		else
	out.println(new JSONObject().put("status", "failed"));
	} 
 else if("HeaderSettings".equals(type)){
	 if(DBHelper.saveHeaderSettings(data,eventid))
		 out.println(new JSONObject().put("status", "success")); 
	 else
	out.println(new JSONObject().put("status", "failed"));
 }else if("buyerPage".equals(type)){
	if(BuyerAttHelper.saveLayout(data))
		out.println(new JSONObject().put("status", "success")); 
	else
		out.println(new JSONObject().put("status", "failed"));
}else if("BuyerAttOptions".equals(type)){
	if(BuyerAttHelper.saveWidgetOptions(data, eventid, widgetid,title, ref_title,layout_type)) 
		out.println(new JSONObject().put("status", "success"));
	else
		out.println(new JSONObject().put("status", "failed"));
}else if("deleteBAOptions".equals(type)){
	if(BuyerAttHelper.deteWidgetOption(widgetid, eventid, layout_type))
		out.println(new JSONObject().put("status", "success"));
	else
		out.println(new JSONObject().put("status", "failed"));
}else if("saveRefTiltleBA".equals(type)){
	if(BuyerAttHelper.saveRefTitle(eventid, title, widgetid,layout_type))
		out.println(new JSONObject().put("status", "success"));
			else
		out.println(new JSONObject().put("status", "failed"));
}else if("resetLastFinalBuyer".equals(type)) {
	if(BuyerAttHelper.resetLastFinal(eventid,layout_type))
		out.println(new JSONObject().put("status", "success"));
	else
		out.println(new JSONObject().put("status", "failed"));
} 
%>