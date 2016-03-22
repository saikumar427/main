<%@page import="com.event.helpers.I18n"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DBManager"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%
   JSONArray jarray = new JSONArray();
   JSONObject jsonobj = null;
   DBManager dbm=new DBManager();
   StatusObj statobj=null;
   statobj=dbm.executeSelectQuery("select * from international_pricing order by currency", null);
   if(statobj.getStatus() && statobj.getCount()>0){
	   for(int i=0;i<statobj.getCount();i++){
		   jsonobj = new JSONObject();
		   
		   String currencyName = dbm.getValue(i, "currency_code", "");
		   currencyName = "pricing."+currencyName+".lbl";
		   currencyName = I18n.getString(currencyName);
		   jsonobj.put("currency",currencyName);
		   jsonobj.put("currencycode",dbm.getValue(i, "currency_code", ""));
		   jsonobj.put("currencysymbol",dbm.getValue(i, "currency_symbol", ""));
		   jsonobj.put("basicfee",dbm.getValue(i, "l100", ""));
		   jsonobj.put("profee",dbm.getValue(i, "l200", ""));
		   jsonobj.put("advancedfee",dbm.getValue(i, "l300", ""));
		   jsonobj.put("businessfee",dbm.getValue(i, "l400", ""));
		   jsonobj.put("paymentprocessor",dbm.getValue(i, "payment_processors", ""));
		   jarray.put(jsonobj);
	   }
   }
   out.println(jarray.toString());
%>