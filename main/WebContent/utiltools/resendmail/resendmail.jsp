<%@ page import="com.eventbee.general.DBManager,com.eventbee.general.StatusObj" %>

<link rel="stylesheet" type="text/css" href="/main/build/container/assets/skins/sam/container.css" />
<script type="text/javascript" src="/main/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="/main/build/container/container-min.js"></script>
<link rel="stylesheet" type="text/css" href="/main/css/yuioverrides.css"/>
<link rel="stylesheet" type="text/css" href='/main/css/common.css' />
<script type="text/javascript" src="/main/js/common/common.js"></script>

<script type="text/javascript" language="JavaScript" src="/main/js/prototype.js"></script>

<script language="javascript">
 function resendsubmit(tid,eventid,k){
 	var str='mailto_'+k;
 	var mailto = document.getElementById(str).value;
	var url='resend.jsp?trnsid='+tid+'&eventid='+eventid+'&mailto='+mailto;
	YAHOO.ebee.popup.wait.setBody("<img src=\"/main/images/loading.gif\"/>");
	YAHOO.ebee.popup.wait.show();
	new Ajax.Request(url, {
		  method: 'get',
		  onSuccess: function(transport) {
			var result=transport.responseText;	
			var jsondata = eval('('+result+')');
			YAHOO.ebee.popup.wait.hide();
			if(jsondata.status=='success'){
	        	alert('Mail sent successfully');
	        } else{
	        	alert('Mail sending Failed');
	        }
		  }
	});	
 }
</script>
<html>

<head>
</head>
<body class="yui-skin-sam">

<%	
	String searchOpt=null; 
	String query=null;
	String parameter=null;
	String[] inputParams=null;
	String emailcheck="";
	String namecheck="";
	String tidcheck="";
	String email="";
	String name="";
	String tid="";

	searchOpt = request.getParameter("radiocheck");
	
	if(searchOpt != null){
	 emailcheck = (searchOpt.equals("chkemail"))?"checked":"";
	 namecheck = (searchOpt.equals("chkname"))?"checked":"";
	 tidcheck = (searchOpt.equals("chktid"))?"checked":"";

	 if(searchOpt.equals("chkemail")){
		email = request.getParameter("srchemail");
	}else if(searchOpt.equals("chkname")){
		name = request.getParameter("srchname");
	}else if(searchOpt.equals("chktid")){
		tid = request.getParameter("srchtid");
	}
	}
	
%>

<form name="rsmail" id="rsmail" method="post" action="resendmail.jsp">
 <table align='center'>
  <tr>
	<td>
	
	 <table align='center'>
       <tr>
		<td><input type="radio" name="radiocheck" value="chkemail" <%=emailcheck%>/>&nbsp;Email</td>
		<td><input type="text" name="srchemail" size="40" value="<%=email%>"></td>
	  </tr>
	  <tr>
		<td><input type="radio" name="radiocheck" value="chkname" <%=namecheck%>/>&nbsp;Name</td>
		<td><input type="text" name="srchname" size="40" value="<%=name%>"></td>
	  </tr>
	  <tr>
	    <td><input type="radio" name="radiocheck" value="chktid" <%=tidcheck%>/>&nbsp;Transaction Id</td>
		<td><input type="text" name="srchtid" size="40" value="<%=tid%>"></td>
	  </tr>
	  <tr><td></td><td><input type="submit" value="Submit"/></td></tr>
	 </table>
	 <%
	  try{
			DBManager dbm =new DBManager();
			StatusObj statobj=null;
		if(emailcheck.equals("checked") && !email.trim().equals("")){
			query = "select ert.fname||' '||ert.lname as name, ert.email, ert.tid, ert.eventid, ei.eventname from event_reg_transactions ert, eventinfo ei where ei.eventid=CAST(ert.eventid AS BIGINT) and (UPPER(ert.email) like ?) and ei.status='ACTIVE' and ert.paymentstatus='Completed'";
			parameter="%"+email.trim()+"%";
			inputParams=new String[]{parameter.toUpperCase()};
		}else if(searchOpt.equals("chkname")){

			query = "select ert.fname||' '||ert.lname as name, ert.email, ert.tid, ert.eventid, ei.eventname from event_reg_transactions ert, eventinfo ei where ei.eventid=CAST(ert.eventid AS BIGINT) and (UPPER(ert.fname) like ? or UPPER(ert.lname) like ? or UPPER(ert.fname||' '||ert.lname)  like ?) and ei.status='ACTIVE' and ert.paymentstatus='Completed'";
			parameter="%"+name.trim()+"%";
			inputParams=new String[]{parameter.toUpperCase(),parameter.toUpperCase(),parameter.toUpperCase()};
		}
		else if(searchOpt.equals("chktid")){
			query = "select ert.fname||' '||ert.lname as name, ert.email, ert.tid, ert.eventid, ei.eventname from event_reg_transactions ert, eventinfo ei where ei.eventid=CAST(ert.eventid AS BIGINT) and ert.tid=? and ei.status='ACTIVE' and ert.paymentstatus='Completed'";
			parameter = tid.trim();
			inputParams=new String[]{parameter};
		}
		if(parameter != null ){
			statobj=dbm.executeSelectQuery(query,inputParams);
			if(statobj.getStatus()){
		%>
		
	<table align='center'>
	 <tr><td><table border="1" width="100%" cellpadding="4" cellspacing="0">
	  <tr>
		<th class="colheader">Buyer Name</th>
		<th class="colheader">Email</th>
		<th class="colheader">Event Name</th>
		<th class="colheader">Transaction Id</th>
		<th class="colheader">Manage</th>
	  </tr>
	<%
			
				for(int k=0;k<statobj.getCount();k++){
					
    %>
	<tr>
		<td><%=dbm.getValue(k,"name","")%></td>
		<td><input type="text" name="mailto_<%=k%>" id="mailto_<%=k%>" size="30" value="<%=dbm.getValue(k,"email","")%>"></td>
		<td><%=dbm.getValue(k,"eventname","")%></td>
		<td><%=dbm.getValue(k,"tid","")%></td>
		<td><input type="button" name="subbtn" value="ResendMail" onclick="javascript:resendsubmit('<%=dbm.getValue(k,"tid","")%>','<%=dbm.getValue(k,"eventid","")%>','<%=k%>')"></td>
		
	  </tr>
<%
				}
			}else{%>
			<tr><td align="center">No Data Found.</td></tr>
           <%}
			}
		
	}catch(Exception e){
		System.out.println(e.getMessage());
	}
%>
</table></td></tr></table>
</td></tr></table>
</form>
</body>
</html>




