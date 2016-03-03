
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.general.DBManager"%>
<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DbUtil"%>
<%@page import="com.eventbee.general.formatting.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GroupTicketMange</title>
<style type="text/css">
.odd {
	background-color: #E4FAE6;
}

.even {
	background-color: #F1FCF2;
}

.head {
	background-color: silver;
}
</style>

<%!


ArrayList<String> getGroupTicketDetails(){
	
	ArrayList grouptickets=new ArrayList();
    DBManager db=new DBManager();
    
	String query="select eventid,ticketid,name,price,discount_value,approval_status from groupdeal_tickets order by eventid";
	StatusObj statobj=db.executeSelectQuery(query,new String[]{});
	// System.out.println("status in get section details in groupticket manage page"+statobj.getStatus());
	
	 if(statobj.getStatus()){
	
		for(int i=0;i<statobj.getCount();i++){
		
	    HashMap groupTicketMap=new HashMap();
	    String eventid=db.getValue(i,"eventid","");
	    groupTicketMap.put("eventid",db.getValue(i,"eventid",""));
	    groupTicketMap.put("ticketid",db.getValue(i,"ticketid",""));
	    groupTicketMap.put("name",db.getValue(i,"name",""));
	    
	    String org_price=db.getValue(i,"price","");
	    String price=CurrencyFormat.getCurrencyFormat("", org_price, false);
	    groupTicketMap.put("price",price);
	    
	    String org_discountvalue=db.getValue(i,"discount_value","");
	    String discount_value=CurrencyFormat.getCurrencyFormat("", org_discountvalue, false);
	    groupTicketMap.put("discount_value",discount_value);
	    
	    groupTicketMap.put("status",db.getValue(i,"approval_status",""));
	   
	    DBManager dbm=new DBManager();
	    String eventinfo="select mgr_id,eventname from eventinfo where eventid=CAST(? AS BIGINT)";
	    StatusObj stat=dbm.executeSelectQuery(eventinfo,new String[]{eventid});
	    String mgrid=dbm.getValue(0,"mgr_id","");
	    String evename=dbm.getValue(0,"eventname","");
	    
	    groupTicketMap.put("mgrid",mgrid);
	    groupTicketMap.put("evename",evename);
	    
		grouptickets.add(groupTicketMap);
		
	}
	 }
	return grouptickets;
}
%>


</head>
<body>

<h3 align="center">GroupTickets Details</h3>
<%
ArrayList groupticketlist=new ArrayList();
groupticketlist=getGroupTicketDetails();
if(groupticketlist.size()>0){
%>
<table align="center" border="1" cellpadding="5" cellspacing="5"
	style="border-collapse: collapse; border-style: solid"
	bordercolor="black">
	<tr class="head">
		<th>Event Id</th>
		<th>Manager Id</th>
		<th>Ticket Name</th>
		<th>Price($)</th>
		<th>Discount(%)</th>
		<th>Status</th>
		<th>Manage</th>
	</tr>
	<%    
                      String cls="";
               for(int i=0;i<groupticketlist.size();i++){
	             if(i%2==0){
		              cls="even";
	                 }else{
	                    	cls="odd";
	                     }
	                HashMap groupTicketDetails= (HashMap)groupticketlist.get(i);
		    	    String eventid = (String) groupTicketDetails.get("eventid");
		    	    String ticketid = (String) groupTicketDetails.get("ticketid");
		    	    String name = (String) groupTicketDetails.get("name");
		    	    String price = (String) groupTicketDetails.get("price");
		    	    String discountvalue = (String) groupTicketDetails.get("discount_value");
		    	    String status = (String) groupTicketDetails.get("status");
		    	    String managerid = (String) groupTicketDetails.get("mgrid");
		    	    String eventname=(String)groupTicketDetails.get("evename");
			
	%>
	<tr class="<%=cls%>">
		<td><%=eventid%></td>
		<td><%=managerid%></td>
		<td><%=name%></td>
		<td><%=price%></td>
		<td><%=discountvalue%></td>
		<td><%=status%></td>
		<td align="center"><a
			href="groupticketapproval.jsp?eventid=<%=eventid%>&ticketid=<%=ticketid%>&mgrid=<%=managerid%>&ticketname=<%=name%>&approval_status=<%=status%>&eventname=<%=eventname%>&price=<%=price%>&discountvalue=<%=discountvalue%>">Edit</a>
          
		</td>
	</tr>
	<%  } %>
</table>
<% 	    
	}
	
%>

</body>
</html>