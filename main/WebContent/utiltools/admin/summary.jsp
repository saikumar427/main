<%@ page import="com.eventbee.general.*"%>
<%@ page import="java.util.*,java.text.SimpleDateFormat;"%>


<html>
<head>
</head>
<body>
<%
Calendar cal = Calendar.getInstance(); 
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
String todate=request.getParameter("todate")==null?sdf.format(cal.getTime()):request.getParameter("todate");
cal.add(Calendar.DATE, -1);   
String fromdate=request.getParameter("fromdate")==null?sdf.format(cal.getTime()):request.getParameter("fromdate");

%>
<form name="summary.jsp" id="summary.jsp" action="summary.jsp" method="post">
<table align="center">
<tr><td><b>Daily Status</b></td></tr>
<tr>
<td>
Date From  <input type="text" name="fromdate" id="fromdate" value="<%=fromdate %>" size="10">&nbsp;
To <input type="text" name="todate" id="todate" value="<%=todate%>" size="10">&nbsp;(YYYY-MM-DD)
&nbsp;&nbsp;&nbsp;
<input type="submit" name="get" value="GET">
</td>
</tr>
<tr>
		<td height="20"></td>
	</tr>
</table>
</form>

<%!
    private static final String Signups = "select count(*) from authentication where created_at between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd')";
    private static final String Eventslisted = "select count(*) from eventinfo where created_at between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd')";
    private static final String Transactions = "select count(*) from event_reg_transactions where  transaction_date  between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ";
    private static final String Paypal_Transactions="select count(*) from event_reg_transactions where paymenttype='paypal' and transaction_date between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ";
    private static final String Google_Transactions="select count(*) from event_reg_transactions where paymenttype='google' and transaction_date between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ";
    private static final String Eventbee_Transactions="select count(*) from event_reg_transactions where paymenttype='eventbee' and transaction_date between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ";
    private static final String Bulk_Transactions="select count(*) from event_reg_transactions where paymenttype='Bulk' and transaction_date between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ";
    private static final String Other_Transactions="select count(*) from event_reg_transactions where paymenttype='other' and transaction_date between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ";
    private static final String Nopayment_Transactions="select count(*) from event_reg_transactions where paymenttype='nopayment' and transaction_date between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ";
    private static final String RSVP_Transactions="select count(*) from event_reg_transactions where paymenttype='RSVP' and transaction_date between to_timestamp(?,'yyyy-mm-dd') and to_timestamp(?,'yyyy-mm-dd') ";
    
    
	String result(String query, String fromdate,String todate) {
    	String count = DbUtil.getVal(query, new String[] {fromdate,todate });
		return count;
	}
	
	%>
<%
List theList = new ArrayList();
String[] list = { "Signups","Eventslisted","Transactions","Paypal_Transactions","Google_Transactions","Eventbee_Transactions","Bulk_Transactions","Other_Transactions","Nopayment_Transactions","RSVP_Transactions" };
String count = "";
String query1 = "";
int i = 0;
%>	

<table align="center" border="1" cellpadding="5" cellspacing="5"
	style="border-collapse: collapse; border-style: solid"
	bordercolor="black">

<tr>
<th>Feature</th>
<th>Count</th>
</tr>
<% if((fromdate != null || !"".equals(fromdate))
		&& (todate != null || !"".equals(todate))) {
	
	theList.add(Signups);
	theList.add(Eventslisted);
	theList.add(Transactions);
	theList.add(Paypal_Transactions);
	theList.add(Google_Transactions);
	theList.add(Eventbee_Transactions);
	theList.add(Bulk_Transactions);
	theList.add(Other_Transactions);
	theList.add(Nopayment_Transactions);
	theList.add(RSVP_Transactions);

	for (Iterator iter = theList.iterator(); iter.hasNext();) {
		query1 = (String) iter.next();
		count = result(query1,fromdate,todate); 
		
		
	%>
       <tr>
				<td><%=list[i]%></td>
				<td><%=count%></td>
			</tr>

            <%
				i = i + 1;
					}
				}
			%>
</table>
</body>
</html>

