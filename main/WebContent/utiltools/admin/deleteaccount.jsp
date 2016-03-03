

<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DbUtil" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete page</title>
</head>
<body>
<%
	StatusObj status = null;
	String login_name = request.getParameter("loginname").trim();
	String user_id = request.getParameter("userid").trim();
	String logintype=request.getParameter("logintype");
	String dbloginname="";
	String dbuserid="";
	String url="";
	String userexist="false";
	String login_type = request.getParameter("logintype");

	
	if("loginname".equals(logintype)){
		if(login_name!=null && !"".equals(login_name)){
              dbloginname=DbUtil.getVal("select login_name from authentication where login_name=?",new String[]{login_name});
			 if(!"".equals(dbloginname)&& dbloginname!=null){
		    	userexist="true";
		    	user_id = DbUtil.getVal("select user_id from authentication where login_name=? ",
						new String[] { login_name });
		    	}//3rd if
		 
		}//2nd if
	}//1st if
	
	else{
		if(user_id!=null && !"".equals(user_id)){
			dbuserid=DbUtil.getVal("select user_id from authentication where user_id=?",new String[]{user_id});
			if(!"".equals(dbuserid) && dbuserid!=null) userexist="true";
			
			
		}
	}
	
	
	if("true".equals(userexist)){
		
		String delete_authentication = "delete from authentication where user_id=?";
		status = DbUtil.executeUpdateQuery(delete_authentication,
				new String[] { user_id });
		//System.out.println("status of authentication" + status.getStatus());

		String delete_userprofile = "delete from user_profile where user_id=?";
		status = DbUtil.executeUpdateQuery(delete_userprofile,
				new String[] { user_id });
		//System.out.println("status of delete_userprofile" + status.getStatus());

		String delete_memberpreference = "delete from member_preference where user_id=?";
		status = DbUtil.executeUpdateQuery(delete_memberpreference,
				new String[] { user_id });
		//System.out.println("status of delete_memberpreference"+ status.getStatus());

		String update_eventinfo="update eventinfo set status='CANCEL' where mgr_id=to_number(?,'999999999')";
		status=DbUtil.executeUpdateQuery(update_eventinfo,new String[]{user_id});
		// System.out.println("status of  update_eventinfo"+ status.getStatus());
		
		String update_clubinfo="update clubinfo set clubid=-(clubid) where mgr_id=to_number(?,'999999999')";
		status=DbUtil.executeUpdateQuery(update_clubinfo,new String[]{user_id});
		// System.out.println("status of  update_clubinfo"+ status.getStatus());
		
	     url = "delete.jsp?deleteaccount=true";
		

		
	}else{
		url="delete.jsp?userexist=false";
			}
	response.sendRedirect(url);
%>
</body>
</html>