<%@ page language="java" contentType="text/html;"%>
<%@page import="com.eventbee.general.DBManager,com.eventbee.general.DbUtil,java.util.HashMap" %>
<%@page import="com.eventbee.general.*"%>
<%!
public StatusObj getTickets(){
	String qry="select final.eventid,tid,ticketid,sum(tot) as total,sum(cnt) as count,sum(tot)-sum(cnt) " +
	"as diff from ( select to_number(a.eventid,'99999999999') as eventid,sum(ticketqty) as tot," +
	"0 as cnt,a.tid,a.ticketid from transaction_tickets a ,event_reg_transactions b where a.tid=b.tid" +
	" and a.eventid=b.eventid group by to_number(a.eventid,'99999999999'),a.tid,a.ticketid " +
	"union all select b.eventid,0 as tot,count(b.*) as cnt,c.tid,b.ticketid from profile_base_info b," +
	"event_reg_transactions c where c.tid=b.transactionid and b.eventid=c.eventid group by b.eventid," +
	"c.tid,b.ticketid) as final,eventinfo temp where final.eventid=temp.eventid and " +
	"temp.end_date>'2010-07-01' group by final.eventid ,tid,ticketid having (sum(tot)-sum(cnt)>0) " +
	"order by eventid,sum(tot)-sum(cnt) desc ";
	StatusObj st=null;
	DBManager dbmanager=new DBManager();
	StatusObj stObj=dbmanager.executeSelectQuery(qry,null);
	int count=stObj.getCount();
	if(stObj.getStatus()&&count>0){
				for(int k=0;k<count;k++){
				String trnId=dbmanager.getValue(k,"tid","");	
				String tktId=dbmanager.getValue(k,"ticketid","");
				int diff=Integer.parseInt(dbmanager.getValue(k,"diff",""));
				String eventId=dbmanager.getValue(k,"eventid","");
				if(diff>0){
						for(int idx=0;idx<diff;idx++){
							DBManager dbmanager1=new DBManager();
							String type="attendeeType";
							String profileId=DbUtil.getVal("select nextval('seq_attendeeId')",null);
							String profileKey="AK"+EncodeNum.encodeNum(profileId).toUpperCase();
							String insertQry="insert into profile_base_info(eventid,ticketid,profileid,profilekey,"+
									"tickettype,email,phone,transactionid,lname,fname,created_at) select eventid,"+tktId+""+
									","+profileId+",'"+profileKey+"','"+type+"',email,phone,'"+trnId+"',lname,fname,now() from "+
									"buyer_base_info where transactionid=?";
							st=DbUtil.executeUpdateQuery(insertQry,new String[]{trnId});
						
						}
					}
				}
			}
		
	
	return st;
}
%>
<%
StatusObj result=getTickets();
out.println("result"+result.getStatus());
%>
