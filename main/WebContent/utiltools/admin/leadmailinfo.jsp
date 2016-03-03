

<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DbUtil"%>
<%@page import="com.eventbee.general.DBManager"%>
<%@page import="java.util.*"%>
<%!
ArrayList<String> getMailListInfo(String manager_id){
	DBManager db=new DBManager();
	StatusObj status=null;
	ArrayList<String> maillist=new ArrayList<String>();
	String query="select list_name from mail_list where manager_id=to_number(?,'999999999')";
	status=db.executeSelectQuery(query,new String[]{manager_id});
	// System.out.println(" status "+status.getStatus());
	if(status.getStatus()){
		for(int i=0;i<status.getCount();i++){
			maillist.add(db.getValue(i,"list_name",""));
		}
	}
	
	return maillist;
}

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>lead MailInfo</title>
<script language="javascript">
function validateMailInfo(){
	var listname=document.getElementById("listname").value;
	if(listname=="" || listname==null){
		alert("Please select listname.");
		return false;
	}else return true;
}
</script>
</head>
<body>

<%

ArrayList<String> mail_list = new ArrayList<String>();
String email_exists="false";
String manager_id="";
String loginname=request.getParameter("loginname");
String dbloginname=DbUtil.getVal("select login_name from authentication where login_name=?",new String[]{loginname});
if("".equals(dbloginname) || dbloginname==null){
	System.out.println("in if loop"+dbloginname);
	String url="managerlogin.jsp?loginname_exist=false";
	response.sendRedirect(url);
	

}else{
	     manager_id=DbUtil.getVal("select user_id from authentication where login_name=?",new String[]{loginname});
		 System.out.println("manager_id"+manager_id);
		mail_list= getMailListInfo(manager_id);
		email_exists=request.getParameter("emailexists");
}

%>

<form name="leadmailinfoform" id="leadmailinfoform" action="savememberinfo.jsp" onsubmit="return validateMailInfo()" >
<h3 align="center" >Member Info</h3>
<% if("true".equals(email_exists)){ %>
<center>Mail already existed.<br>
Please enter New Member.</center>
<% } %>
<table align="center">

	<tr>
		<td>List Name</td>
		<td colspan="3"><select name="listname" id="listname">
			<option value="">--Select maillist-- <% for(int i=0;i<mail_list.size();i++){ %>
			
			<option value="<%= mail_list.get(i)%>"
				<%if(mail_list.equals(mail_list.get(i))) {%> selected="selected"
				<%} %>><%=mail_list.get(i)%> <%} %>
			</option>
		</select></td>
	</tr>

	<tr>
		<td>First Name</td>
		<td colspan="3"><input type="text" name="firstname"
			id="firstname" value=""></td>
	</tr>
	<tr>
		<td>Last Name</td>
		<td colspan="3"><input type="text" name="lastname" id="lastname"
			value=""></td>
	</tr>
	<tr>
		<td>Email</td>
		<td colspan="3"><input type="text" name="email" id="email"
			value=""></td>
	</tr>
	<tr>
		<td>Phone</td>
		<td colspan="3"><input type="text" name="phone" id="phone"
			value=""></td>
	</tr>
	<tr>
		<td>Address</td>
		<td colspan="3"><input type="text" name="address" id="address"
			value=""></td>
	</tr>
	<tr>
		<td><input type="submit" name="submit" value="Submit">
         <input type="button" name="cancel" value="Cancel"
			onclick="javascript:window.location='managerlogin.jsp'"></td>
	</tr>
</table>
<input type="hidden" name="manager_id" id="manager_id"
	value="<%=manager_id %>"> <input type="hidden" name="loginname"
	id="loginname" value="<%=loginname %>"></form>
</body>
</html>