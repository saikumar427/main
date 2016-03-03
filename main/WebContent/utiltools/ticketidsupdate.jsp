<%@ page import="java.util.*,com.eventbee.general.*" %>

<%!
HashMap<String, String> getticketids(){
String query="select ticketid,p.ticket_type,tid from transaction_tickets t,price p where p.price_id=t.ticketid and p.ticket_type='Public'";
DBManager db=new DBManager();
HashMap<String, String> TransactionMap=new HashMap<String, String>();
StatusObj sb=db.executeSelectQuery(query,new String[]{});

if(sb.getStatus()){
for(int i=0;i<sb.getCount();i++){

TransactionMap.put(db.getValue(i,"tid",""),db.getValue(i,"ticketid",""));
}
}
return TransactionMap;
}
%>
<%
HashMap<String, String> ticketids=getticketids();



try{

String s="select transactionid from eventattendee order by attendeeid desc";
DBManager db=new DBManager();
StatusObj sb=db.executeSelectQuery(s,new String[]{});
if(sb.getStatus()){

for(int i=0;i<sb.getCount();i++){

	String ticketid=ticketids.get(db.getValue(i,"transactionid",""));

if(ticketid!=null){
StatusObj b=DbUtil.executeUpdateQuery("update eventattendee set ticketid=to_number(?,'999999999') where transactionid=?",new String[]{ticketid,db.getValue(i,"transactionid","")});

}
else{
StatusObj b=DbUtil.executeUpdateQuery("update eventattendee set ticketid=to_number(?,'999999999') where transactionid=?",new String[]{"0",db.getValue(i,"transactionid","")});
}
}
}
}
catch(Exception e){
System.out.println("Exception Occured is"+e.getMessage());

}






%>










