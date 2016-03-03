String [] camplistids=request.getParameterValues("listids");
String senderid=request.getParameter("userid");
String sel=request.getParameter("nowval");
String templateId=request.getParameter("tid");

String htmlmessage=campTemplateDB.getCampHtml(templateId);
String server_address1="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
String imgpattern=server_address1+"/home/images/poweredby.jpg";
if("No".equals(request.getParameter("EVENTBEE_LOGO_DISPLAY")))
	imgpattern=server_address1+"/home/images/spacer.gif";
String imagetest=server_address1+"/campmailsopen?eid=#*EID*#";	
String textunsuburl="If you wish to remove yourself from this mailing list, please unsubscribe by ";

String email="";
String fulladdress="";
String query="select getMemberName(user_id||'') as name,street,city,state,country,zip,email from user_profile where user_id=? ";
ArrayList addresslist=null;
DBManager dbmanager=new DBManager();
StatusObj statobj=dbmanager.executeSelectQuery(query,new String [] {senderid});
if(statobj.getStatus()){
addresslist=new ArrayList();
addresslist.add(dbmanager.getValue(0,"name",""));
addresslist.add(dbmanager.getValue(0,"street",""));
addresslist.add(dbmanager.getValue(0,"city",""));
addresslist.add(dbmanager.getValue(0,"state",""));
addresslist.add(dbmanager.getValue(0,"country",""));
addresslist.add(dbmanager.getValue(0,"zip",""));
email=dbmanager.getValue(0,"email","");
}
if(addresslist!=null) fulladdress=GenUtil.getCSVData((String [])addresslist.toArray(new String[addresslist.size()]));
String messagefrommgr="This email was sent by "+fulladdress;
htmlmessage=htmlmessage+"<a href='"+server_address1+"'><img src='"+imgpattern+"'   border='0' /></a><br/><br/><div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>"+messagefrommgr+"</font></div>"
+" <br/> <div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>"+textunsuburl
+" <a href='"+server_address1+"/portal/guesttasks/campunsubscribe.jsp?memberid=#*ENCODED_MEM_ID*#&listid=#*ENCODED_List_ID*#&eid=#*EID*#'>clicking here</a>, or send mail to "+email+" </font></div>"
+"<div ><img src='"+imagetest+"'   border='0' /></div>";

EmailSchedule emailsch=new EmailSchedule();
String campid=ScheduleDB.getNewCampId();
emailsch.setCampId(campid);
emailsch.setCampList(camplistids);
emailsch.setCampStatus("P");
if(sel.equals("now")){
	java.sql.Timestamp tsp=new java.sql.Timestamp(Calendar.getInstance().get(Calendar.YEAR)-1900,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH),Calendar.getInstance().get(Calendar.HOUR_OF_DAY),0,0,0);
	emailsch.setCampScheduledDate(tsp.toString());
	emailsch.setCampTimeZone(new Date()).toString());
}else if(sel.equals("date")){
	String day= request.getParameter("day");
	String month= request.getParameter("month");
	String year= request.getParameter("year");
	String time=request.getParameter("time");
	java.sql.Timestamp tsp=new java.sql.Timestamp(Integer.parseInt(year)-1900,Integer.parseInt(month)-1,Integer.parseInt(day),Integer.parseInt(time),0,0,0);
	emailsch.setCampScheduledDate(tsp.toString());
	emailsch.setCampTimeZone(tsp.toString());
}else{
	emailsch.setCampScheduledDate(null);
	emailsch.setCampStatus("T");
	emailsch.setCampTimeZone((new Date()).toString());	
}


emailsch.setCampHtml(htmlmessage);
ScheduleDB.insertEmailCampaign( emailsch);