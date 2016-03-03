<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DbUtil" %>
<%@page import="com.eventbee.general.DBManager" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete Transaction</title>
</head>
<body>
<%
StatusObj statobj=null;
DBManager dbmanager=new DBManager();
String transaction_id=request.getParameter("transactionid");
String eventid=DbUtil.getVal("select eventid from event_reg_transactions where tid=? ",new String[]{transaction_id});
//System.out.println("eventid"+eventid);
String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eventid});
String url="";
String rsvpenabled="";
String isrecurr="";
try{
	 rsvpenabled=DbUtil.getVal("select value from config where name='event.rsvp.enabled' and config_id=to_number(?,'999999999')",new String[]{configid});
	 isrecurr=DbUtil.getVal("select value from config where name='event.recurring' and config_id=to_number(?,'999999999')",new String[]{configid});	
}catch(Exception e){
	
}


String dbtid=DbUtil.getVal("select tid from event_reg_transactions where tid=?",new String[]{transaction_id});
System.out.println(" dbtid "+dbtid);
if("".equals(dbtid) || dbtid==null){
	//System.out.println("in if");
	url="delete.jsp?tidexist=false";
}
else{
	if("yes".equals(rsvpenabled)){
		
		
		String delete_eventregtransactions="delete from event_reg_transactions where  tid=?";
		 statobj=DbUtil. executeUpdateQuery(delete_eventregtransactions, new String[]{transaction_id});
		 
		 String delete_rsvptransactions="delete from rsvp_transactions where tid=?";
		 statobj=DbUtil. executeUpdateQuery(delete_rsvptransactions, new String[]{transaction_id});
		 
		 String delete_transaction_tickets="delete from transaction_tickets where  tid=?";
		 statobj=DbUtil. executeUpdateQuery(delete_transaction_tickets, new String[]{transaction_id});
		 
		 String delete_profilebaseinfo="delete from profile_base_info where " +
		 		"  transactionid=?";
		 statobj=DbUtil. executeUpdateQuery(delete_profilebaseinfo, new String[]{transaction_id});
		 
		 String delete_buyerbaseinfo="delete from buyer_base_info where" +
		 		" transactionid=?";
		 statobj=DbUtil. executeUpdateQuery(delete_buyerbaseinfo, new String[]{transaction_id});
		
	}else{
		String factor="-1";
		String update_eventregtransactions="update event_reg_transactions set paymentstatus=? where tid=?  ";
		statobj = DbUtil.executeUpdateQuery(update_eventregtransactions,new String[] { "Cancelled", transaction_id });
		
		String TRN_TICKETS_SELECT_QUERY = "select ticketid,sum(ticketqty) as qty from "+
		             " transaction_tickets where tid=? and eventid=? group by ticketid";
		String SOLDQTY_UPDATE = "update price set sold_qty=sold_qty+to_number(?,'999999999') where price_id=to_number(?,'999999999') and evt_id=CAST(? AS BIGINT)";
		
		String rec_soldqty="update reccurringevent_ticketdetails set soldqty=soldqty+to_number(?,'999999999') where ticketid=to_number(?,'9999999999') and eventid=CAST(? AS BIGINT)";
		
		if("Y".equals(isrecurr)){
			statobj = dbmanager.executeSelectQuery(TRN_TICKETS_SELECT_QUERY,
					new String[] { transaction_id, eventid });
			
			int count = statobj.getCount();
			if (statobj.getStatus()) {
				for (int k = 0; k < count; k++) {
					String ticketid = (String) dbmanager.getValue(k, "ticketid", "");
					String qty = (String) dbmanager.getValue(k, "qty", "");
					int qtytemp = Integer.parseInt(qty) * Integer.parseInt(factor);
					String temp = Integer.toString(qtytemp);
					StatusObj statobj1 = DbUtil.executeUpdateQuery(rec_soldqty,
							new String[] { temp, ticketid, eventid });
				} // end for()
			} // end if()
		}else{
			statobj = dbmanager.executeSelectQuery(TRN_TICKETS_SELECT_QUERY,
					new String[] { transaction_id, eventid });
			
			int count = statobj.getCount();
			if (statobj.getStatus()) {
				for (int k = 0; k < count; k++) {
					String ticketid = (String) dbmanager.getValue(k, "ticketid", "");
					String qty = (String) dbmanager.getValue(k, "qty", "");
					int qtytemp = Integer.parseInt(qty) * Integer.parseInt(factor);
					String temp = Integer.toString(qtytemp);
					StatusObj statobj1 = DbUtil.executeUpdateQuery(SOLDQTY_UPDATE,
							new String[] { temp, ticketid, eventid });
				} // end for()
			} // end if()
			
		  }//3nd else
		
	}//2nd else



	 url="delete.jsp?deletetransaction=true";

}//1st else
response.sendRedirect(url);


%>
</body>
</html>