
<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DbUtil"%>
<%@page import="com.eventbee.general.DBManager"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Save memberInfo</title>
</head>
<body>
<%
	StatusObj status = null;
	DBManager db = new DBManager();
	int count = 0;
	String listname = request.getParameter("listname");
	String listid = DbUtil.getVal(
			"select list_id from mail_list where list_name=? ",
			new String[] { listname });
	String firstname = request.getParameter("firstname");
	String lastname = request.getParameter("lastname");
	String email = request.getParameter("email");
	String phone = request.getParameter("phone");
	String address = request.getParameter("address");
	String manager_id = request.getParameter("manager_id");
	String loginname = request.getParameter("loginname");
	String memberid = DbUtil.getVal("select nextval('seq_maillist')",
			new String[] {});
     
	String emailquery = "select m_email from member_profile where member_id in "
			+ "(select member_id from mail_list_members where list_id=to_number(?,'999999999'))";
	status = db.executeSelectQuery(emailquery, new String[] { listid });
	if (status.getStatus()) {
		for (int i = 0; i < status.getCount(); i++) {
			String emailfromdb = db.getValue(i, "m_email", "");
		//	System.out.println("mails "+emailfromdb+" "+email);
			if (email.equals(emailfromdb))
				count++;
		}
	}

	// System.out.println("count"+count);
	String url = "";
	String emailexists = "";
	if (count > 0) {
		url = "leadmailinfo.jsp?emailexists=true" + "&loginname="
				+ loginname;

	} else {
		String insert_maillistmembers = "insert into  mail_list_members(member_id,list_id,status,created_by)"
				+ "values(to_number(?,'999999999'),to_number(?,'999999999'),'available','Manager')";
		status = DbUtil.executeUpdateQuery(insert_maillistmembers,
				new String[] { memberid, listid });

		//System.out.println("status of insert_maillistmembers"+ status.getStatus());

		String insert_memberprofile = "insert into member_profile(manager_id,member_id,m_firstname,m_lastname,m_email,m_phone,m_place,m_email_type)"
				+ "values(to_number(?,'999999999'),to_number(?,'999999999'),?,?,?,?,?,'html')";
		status = DbUtil.executeUpdateQuery(insert_memberprofile,
				new String[] { manager_id, memberid, firstname,
						lastname, email, phone, address });
		//	System.out.println("status of insertmemberprofile"+ status.getStastus());
		url = "managerlogin.jsp?memberadded=true";
	}

	response.sendRedirect(url);
%>

</body>
</html>