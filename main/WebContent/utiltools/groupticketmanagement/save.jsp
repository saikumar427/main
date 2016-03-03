
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DbUtil"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>

<%
try{
	

	StatusObj statobj = null;
	String eventid = request.getParameter("eventid");
	String ticketid = request.getParameter("ticketid");
	String approval_status = request.getParameter("status");
	String mgrid = request.getParameter("managerid");
	String act_start_date = request.getParameter("act_start_date");
	String act_starttime=request.getParameter("act_starttime");
	
	String active_duration = request.getParameter("activeduration");
	if("".equals(active_duration)||active_duration==null)
		active_duration="0";
	
	String ourfee = request.getParameter("fee");
	
		
	String sold_qty=request.getParameter("soldqty");
	if("".equals(sold_qty)||sold_qty==null)  sold_qty="0";
	
	String current_cycle=request.getParameter("currentcycle");
	if("".equals(current_cycle)||current_cycle==null)  current_cycle="1";
	//System.out.println("sold qty,current cycle,approvalstatus "+sold_qty+current_cycle+approval_status);
	String end_date = "";
	String end_time = "";
	String endampm = "";

	
		
        String delete_groupticketrfee="delete from  groupticketrfee where eventid=? and mgr_id=to_number(?,'999999999999') and ticketid=to_number(?,'999999999999')" ;
        statobj=DbUtil.executeUpdateQuery(delete_groupticketrfee,new String[]{eventid,mgrid,ticketid});
        
        
		String groupticketrfee = " insert into groupticketrfee(eventid,ticketid,mgr_id,ebeefee,card_factor,purpose,card_base) "
				+ " values(?,to_number(?,'999999999999'),to_number(?,'999999999999'),to_number(?,'99999999D99'),'0.0495','EVENT_REGISTRATION','0.50')";
		statobj = DbUtil.executeUpdateQuery(groupticketrfee,
				new String[] {eventid,ticketid,mgrid,ourfee});
		System.out.println("status of groupticketrfee"
				+ statobj.getStatus());

	
	
  
	String update1_groupdealtickets = "update groupdeal_tickets set act_start_date=to_date(?,'yyyy-mm-dd'),act_starttime=?,end_date=to_date(?,'yyyy-mm-dd'),end_time=?,approval_status=?, "
			+ " endampm='',act_startampm='', active_duration=?,sold_qty=to_number(?,'99999999999'),current_cycle=to_number(?,'999999999') "+
					" where eventid=to_number(?,'999999999') and ticketid=to_number(?,'999999999')";
	statobj=DbUtil.executeUpdateQuery(update1_groupdealtickets,new String[]{act_start_date,act_starttime,act_start_date,act_starttime,approval_status,active_duration,sold_qty,current_cycle,eventid,ticketid});
	
	System.out.println("status of update1 groupdealtickets"+statobj.getStatus());
	
	active_duration="'"+active_duration+" hours'";
	
	String select_groupdealtickets= " select (act_start_date+cast(cast(to_timestamp(COALESCE(act_starttime,'00'),'HH12:MI:SS') as text) as time) +  interval "+active_duration+") "+
	" from groupdeal_tickets where eventid=to_number(?,'999999999') and ticketid=to_number(?,'999999999')";
	String end_ts=DbUtil.getVal(select_groupdealtickets,new String[]{eventid,ticketid});
	
	String[] tm=end_ts.split(" ");
	end_date=tm[0];
	String et=tm[1];
	
	String[] etime=et.split(":");
	String hh=etime[0];
	String mm=etime[1];
	end_time=hh+":"+mm;
	
	System.out.println("end_ts: "+end_ts+",date"+end_date+",end_time:"+end_time);
	
	
	String update2_groupdealtickets="update groupdeal_tickets set end_date=to_date(?,'yyyy-mm-dd'),end_time=? "+
			                       " where eventid=to_number(?,'999999999') and ticketid=to_number(?,'999999999')";
	
	statobj=DbUtil.executeUpdateQuery(update2_groupdealtickets,new String[]{end_date,end_time,eventid,ticketid});
	System.out.println("status of update2 groupdealtickets"+statobj.getStatus());
	
   /*String url = "groupticketapproval.jsp?eventid=" + eventid
			+ "&ticketid=" + ticketid + "&mgrid=" + mgrid
			+ "&approval_status=" + approval_status;*/
			
			String url="groupticketmanage.jsp";
	
	
	response.sendRedirect(url);
	
}catch(Exception e){
	System.out.println("Exception occured at"+e.getMessage());
}
%>
</body>
</html>