<%@page import="org.json.JSONObject"%>
<%@page import="com.eventbee.geocodes.Geocode"%>
<%@page import="com.eventbee.util.Address"%>
<%@page import="com.eventbee.geocodes.GeocodeGenerator"%>
<%@page import="com.eventbee.general.EbeeConstantsF"%>
<%@page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String address1 = request.getParameter("address");
String address2 = request.getParameter("venue");
String city = request.getParameter("city");
String state = request.getParameter("state");
String country = request.getParameter("country");
System.out.println("address1:"+address1);
System.out.println("address2:"+address2);
System.out.println("city:"+city);
System.out.println("state:"+state);
System.out.println("country:"+country);
JSONObject jsonobj = new JSONObject();
// String address1="Backstage Capitol",address2="93 King St",city="Delhi",state="ON",country="Canada";

GeocodeGenerator geocodegenerator=null;
try{
	Object obj=(Object)Class.forName(EbeeConstantsF.get("geocodes.provider.class","com.eventbee.geocodes.GeocodeAmericaREST")).newInstance();
	geocodegenerator=(GeocodeGenerator)obj;
	String straddress=address1+"%"+address2;//
	com.eventbee.util.Address address=new Address(straddress,city,state,"",country);
	geocodegenerator.setAddress(address);
	Geocode geocode=geocodegenerator.getGeocodes();
	if(geocode.getStatus()){
		System.out.println("lat::"+geocode.getLatitude());
		System.out.println("long::"+geocode.getLongitude());
		jsonobj.put("lat",geocode.getLatitude());
		jsonobj.put("lng",geocode.getLongitude());
		//StatusObj obj1=DbUtil.executeUpdateQuery("update eventinfo set latitude=?,longitude=? where eventid=CAST(? AS BIGINT)  ",new String [] {geocode.getLatitude(),geocode.getLongitude(),eid});
	}
	}catch(Exception e){
System.out.println(e.getMessage());
}
out.println(jsonobj.toString(2));

%>



