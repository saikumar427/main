success
<%
String [] sel2vals=request.getParameterValues("aQuestions");
String eid=request.getParameter("eid");
String ticketId=request.getParameter("ticketId");
System.out.println("eid"+eid);
System.out.println("ticketId"+ticketId);
try{
for (int i=0;i<sel2vals.length;i++){
	System.out.println("selected value"+sel2vals[i]);
}
}
catch(Exception ex){
	System.out.println("Exception in submit reg questions for ticket");
}

%>