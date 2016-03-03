
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.eventbee.general.DBManager"%>
<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DbUtil"%>
<%@page import="java.io.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>


<%@page import="java.util.StringTokenizer"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
	/*   function save(eventid, ticketid, mgrid, approval_status) {
	      window.location = "save.jsp?eventid=" + eventid + "&ticketid="
				+ ticketid + "&mgrid=" + mgrid + "&approval_status="
				+ approval_status;

	}*/
</script>

</head>
<body>
<%
	String eventid = request.getParameter("eventid");
	String managerid = request.getParameter("mgrid");
	String ticketid = request.getParameter("ticketid");
	String ticketname = request.getParameter("ticketname");
	String approval_status = request.getParameter("approval_status");
	String eventname = request.getParameter("eventname");
	String price = request.getParameter("price");
	String discountvalue = request.getParameter("discountvalue");

	DBManager db = new DBManager();
	StatusObj statobj = null;
	String groupticketdetails = "select * from groupdeal_tickets where eventid=to_number(?,'999999999') and ticketid=to_number(?,'999999999')";
	statobj = db.executeSelectQuery(groupticketdetails, new String[] {
			eventid, ticketid });
	//System.out.println("status of groupdealtickets in groupticketapproval page" + statobj.getStatus());
	String description = db.getValue(0, "description", "");
	if (description == null)
		description = "";
	String discounttype = db.getValue(0, "discount_type", "");
	if (discounttype == null)
		discounttype = "";
	String trigger_qty = db.getValue(0, "trigger_qty", "");
	if (trigger_qty == null)
		trigger_qty = "";
	String min_qty = db.getValue(0, "min_qty", "");
	if (min_qty == null)
		min_qty = "";
	String max_qty = db.getValue(0, "max_qty", "");
	if (max_qty == null)
		max_qty = "";
	String start_date_req = db.getValue(0, "start_date_req", "");
	if (start_date_req == null)
		start_date_req = "";
	String start_time_req = db.getValue(0, "start_time_req", "");
	if (start_time_req == null)
		start_time_req = "";
	String startampm = db.getValue(0, "startampm", "");
	if (startampm == null)
		startampm = "";
	String end_date = db.getValue(0, "end_date", "");
	if (end_date == null)
		end_date = "";
	String end_time = db.getValue(0, "end_time", "");
	if (end_time == null)
		end_time = "";
	String show_status = db.getValue(0, "show_status", "");
	if (show_status == null)
		show_status = "";
	String created_at = db.getValue(0, "created_at", "");
	if (created_at == null)
		created_at = "";
	String last_updated = db.getValue(0, "last_updated", "");
	if (last_updated == null)
		last_updated = "";
	String post_trigger_type = db.getValue(0, "post_trigger_type", "");
	if (post_trigger_type == null)
		post_trigger_type = "";
	String upperlimit = db.getValue(0, "upper_limit", "");
	if (upperlimit == null)
		upperlimit = "";
	String cycleslimit = db.getValue(0, "cycles_limit", "");
	if (cycleslimit == null)
		cycleslimit = "";
	String position = db.getValue(0, "position", "");
	if (position == null)
		position = "";
	String trigger_fail_action = db.getValue(0, "trigger_fail_action",
			"");
	if (trigger_fail_action == null)
		trigger_fail_action = "";
	String fail_discount = db.getValue(0, "fail_discount", "");
	if (fail_discount == null)
		fail_discount = "";
	String mgrfee = db.getValue(0, "fee", "");
	if (mgrfee == null)
		mgrfee = "";

	String sold_qty = db.getValue(0, "sold_qty", "");
	if (sold_qty == null)
		sold_qty = "";

	String current_cycle = db.getValue(0, "current_cycle", "");
	if (current_cycle == null)
		current_cycle = "";

	//System.out.println("sold qty,current cycle"+sold_qty+current_cycle);

	String act_start_date = db.getValue(0, "act_start_date", "");
	if (act_start_date == null)
		act_start_date = "";

	String act_starttime = db.getValue(0, "act_starttime", "");
	if (act_starttime == null)
		act_starttime = "";

	String activeduration = db.getValue(0, "active_duration", "");
	if (activeduration == null)
		activeduration = "";

	String groupticketrfee = "select ebeefee from groupticketrfee where eventid=? and ticketid=to_number(?,'99999999999') and mgr_id=to_number(?,'99999999999')";
	String fee = DbUtil.getVal(groupticketrfee, new String[] { eventid,
			ticketid, managerid });
	if (fee == null)
		fee = "25";

	String url = "groupticketmanage.jsp";
%>
<!--<a href="<%=url%>">Back</a>  -->
<form name="groupticketapproval" id="groupticketapproval" method="post"
	action="save.jsp">
<h3 align="center">Group Ticket Settings</h3>
<table align="center" border="1" width="840px">
	<tr>
		<td width="50%">
		<table cellpadding="5" cellspacing="5">
			<tr>
				<td>Event Name:&nbsp;<%=eventname%></td>
			</tr>
			<tr>
				<td>Ticket Name:&nbsp;<%=ticketname%></td>
			</tr>
			<tr>
				<td>Description:&nbsp;<%=description%></td>
			</tr>
			<tr>
				<td>Price&nbsp;($):&nbsp;<%=price%></td>
			</tr>
			<tr>
				<td>Discount Type&nbsp;(%):&nbsp;<%=discounttype%></td>
			</tr>
			<tr>
				<td>Discount Value&nbsp;(%):&nbsp;<%=discountvalue%></td>
			</tr>
			<tr>
				<td>Trigger Qty:&nbsp;<%=trigger_qty%></td>
			</tr>
			<tr>
				<td>Min Purchase Qty:&nbsp;<%=min_qty%></td>
			</tr>
			<tr>
				<td>Max Purchase Qty:&nbsp;<%=max_qty%></td>
			</tr>
			<tr>
				<td>Sale StartDate:&nbsp;<%=start_date_req%></td>
			</tr>
			<tr>
				<td>Sale StartTime:&nbsp;<%=start_time_req%>&nbsp;<%=startampm%></td>
			</tr>
			<tr>
				<td>End Date:&nbsp;<%=end_date%></td>
			</tr>
			<tr>
				<td>End Time:&nbsp;<%=end_time%></td>
			</tr>
			<tr>
				<td>Show Status:&nbsp;<%=show_status%></td>
			</tr>
			<tr>
				<td>Created Date:&nbsp;<%=created_at%></td>
			</tr>
			<tr>
				<td>Last Updated Date:&nbsp;<%=last_updated%></td>
			</tr>
			<tr>
				<td>Post Trigger Type:&nbsp;<%=post_trigger_type%></td>
			</tr>
			<tr>
				<td>Upper Limit:&nbsp;<%=upperlimit%></td>
			</tr>
			<tr>
				<td>Cycles Limit:&nbsp;<%=cycleslimit%></td>
			</tr>
			<tr>
				<td>Position:&nbsp;<%=position%></td>
			</tr>

			<tr>
				<td>Trigger Fail Action:&nbsp;<%=trigger_fail_action%></td>
			</tr>
			<tr>
				<td>Fail Discount:&nbsp;<%=fail_discount%></td>
			</tr>

			<tr>
				<td>Fee:&nbsp;<%=mgrfee%></td>
			</tr>

		</table>
		</td>
		<td width="50%" valign="top">

		<table cellpadding="5" cellspacing="5">
			<tr>
				<td>Start Date</td>
				<td colspan="3"><input type="text" name="act_start_date"
					id="act_start_date" value="<%=act_start_date%>">&nbsp;(yyyy-mm-dd)</td>
			<tr>
				<td>Time</td>
				<td colspan="3"><input type="text" name="act_starttime"
					id="act_starttime" value=<%=act_starttime%>>&nbsp;23:30</td>
			<tr>
				<td>Active Duration</td>
				<td colspan="3"><input type="text" name="activeduration"
					id="activeduration" value="<%=activeduration%>">&nbsp;hours</td>
			</tr>
			<tr>
				<td>Our Fee</td>
				<td colspan="3"><input type="text" name="fee" id="fee"
					value="<%=fee%>">&nbsp;%</td>
			</tr>

			<tr>
				<td>Status</td>
				<td colspan="3"><input type="text" name="status" id="status"
					value="<%=approval_status%>"><br />
				A:Approved,R:Rejected,P:Pending</td>
			</tr>

			<tr>
				<td><b><u>Simulated Fields</u></b></td>
			</tr>
			<tr>
				<td>Sold Qty</td>
				<td colspan="3"><input type="text" name="soldqty" id="soldqty"
					value="<%=sold_qty%>">
			</tr>
			<tr>
				<td>Current Cycle</td>
				<td colspan="3"><input type="text" name="currentcycle"
					id="currentcycle" value="<%=current_cycle%>">
			</tr>

			<tr>
				<td colspan="3"><input type="submit" id="submit" value="Submit">
				&nbsp; <input type="button" value="Cancel"
					onclick="javascript:window.location='groupticketmanage.jsp'">
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<input type="hidden" name="eventid" value="<%=eventid%>"> <input
	type="hidden" name="ticketid" value="<%=ticketid%>"> <input
	type="hidden" name="managerid" value="<%=managerid%>"></form>
</body>
</html>