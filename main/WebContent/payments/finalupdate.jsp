<%@ page import="com.eventregister.dbhelper.*"%>
<%@ page import="com.event.dbhelpers.TransactionDB"%>
<%@ page import="com.eventbee.general.*"%>
<%!
public String updateRegDb(String tid, String paytoken, String eid){
	System.out.println("##### updateRegDb() in finalupdate.jsp tid: "+tid);
	String status="fail";
	try{
		RegistrationConfirmationEmail regmail=new RegistrationConfirmationEmail();
		RegistrationTiketingManager regmgr=new RegistrationTiketingManager();
		RegistrationProcessDB regdb=new RegistrationProcessDB();
		
		regmgr.updatePaytype(tid, "confirmation page");
			
		regmgr.updatePaymentType(tid);
			
		regdb.InsertRegistrationDb(tid,eid);
		
		regdb.updateExtPayId(tid,paytoken);
		
		boolean seatingenabled=regmgr.getSeatingEnabled(eid);
		System.out.println("In finalupdate.jsp seatingenabled: "+seatingenabled);
		if(seatingenabled){
			String eventdate = DbUtil.getVal("select  eventdate from event_reg_transactions where eventid=? and tid=? ", new String[]{eid,tid});
			if(eventdate==null || "null".equals(eventdate)) eventdate="";
			regmgr.fillseatingstatus(tid, eid, eventdate);
		}
		
		TransactionDB.resendingMail(eid,tid,"Ticketing");
		regmgr.deleteTempDetails(tid);
		regmgr.updateProfileStatus(tid);
		status="success";
	}catch(Exception e){
		System.out.println("Exception in finalupdate.jsp Error:: "+e.getMessage());
	}
	return status;
}
%>

<%
System.out.println("#### in finalupdate.jsp");
String paytoken=request.getParameter("paytoken");
System.out.println("#### in finalupdate.jsp paytoken: "+paytoken);
DBManager dbmanager=new DBManager();
StatusObj statobj=null;
String query = "select internalref,refid from eventbee_payments_submits where status='success' and paytoken=? ";
statobj=dbmanager.executeSelectQuery(query,new String []{paytoken});
String tid="";String eid="";
if(statobj.getStatus()){
	tid = dbmanager.getValue(0,"internalref","");
	eid = dbmanager.getValue(0,"refid","");
}
String finalstatus="";
if(tid!=null && eid!=null){
	String status = DbUtil.getVal("select 'yes' from event_reg_transactions where eventid=? and tid=? ", new String[]{eid,tid});
	if(!"yes".equals(status)){
		finalstatus=updateRegDb(tid, paytoken, eid);
	}
}
out.println(finalstatus);
%>