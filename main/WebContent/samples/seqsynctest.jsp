<%@ page language="java" contentType="text/html;"%>
<%@ page import=" java.sql.CallableStatement,java.sql.Connection,java.sql.ResultSet,com.eventbee.general.*;" %>
<%	
	String newseqid = "";
	String result="";
	try{
	Connection con=EventbeeConnection.getConnection();
	CallableStatement cs = con.prepareCall("{call  syncseqtest()}");
	ResultSet rs = cs.executeQuery();
	if(rs.next()){
		out.println("Result Set"+rs.toString());
	}
	}catch(Exception ex){
		System.out.print("Exception: "+ex);
	}
%>
