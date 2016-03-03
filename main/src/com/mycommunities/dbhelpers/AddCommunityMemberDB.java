package com.mycommunities.dbhelpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.event.beans.CustomAttribute;
import com.eventbee.beans.Entity;
import com.eventbee.general.*;
import com.mycommunities.beans.AddCommunityMemberData;
import com.mycommunities.beans.ClubMemberShip;
import com.mycommunities.beans.MailListMember;
import com.mycommunities.beans.Config;
import com.mycommunities.helpers.EventBeeValidations;
import com.mycommunities.beans.SubscriptionDue;
import com.myemailmarketing.beans.MemberData;
import com.user.dbhelpers.UserDB;

public class AddCommunityMemberDB {

	static String LOGIN_LABEL = "Login ID";
	public static ClubMemberShip clubmship = new ClubMemberShip();
	
	private static final DateFormat parseFormat = new SimpleDateFormat("yy-MM-dd");
    private static final DateFormat formattingFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static String CONFIG_KEY_DELETE = "delete from config where config_id=to_number(?,'999999999') and name=?";
	public static String CONFIG_INFO_INSERT = "insert into config(config_id, name, value) values (to_number(?,'999999999'),?,?)";
	public static String CLUB_INFO_QUERY = "select config_id  from clubinfo where clubid=to_number(?,'999999999')";

	String MEM_PROFILE_GET = "select a.first_name,a.last_name,a.email,a.phone,a.street,a.city as city,"
			+ "a.state as state,a.zip,a.country,a.gender,a.statement,a.process_statement,a.autoprocess,"
			+ "a.company,a.title,a.photourl,a.photo_caption,a.education,a.eduarea,a.university,a.industry,b.unit_id,"
			+ "a.interests,a.personalurl,a.blogurl,a.companyurl,a.shareprofile,a.native_city,a.native_state,"
			+ "a.native_country,b.login_name from user_profile as a,"
			+ "authentication as b where a.user_id=b.user_id and a.user_id=?";
	// in user_profile user_id is varchar type,in authentication user_id is
	// varcahr

	public static String MEM_PREFERENCE_INSERT = " insert into member_preference(user_id, pref_name, pref_value)"
			+ " values (?,?,?) ";

	public static ArrayList<HashMap<String, String>> getclubmembermaster(
			String clubid) {

		// System.out.println("AddCommunityMemeberDbhelper " + clubid);

		DBManager db = new DBManager();
		StatusObj statobj = null;
		String mem_id = "";
		HashMap<String, String> mshipidnamemap = new HashMap<String, String>();
		ArrayList mshipidlist = new ArrayList();

		String clubmembermaster = " select membership_id,membership_name,createtype from club_membership_master"
				+ " where clubid=to_number(?,'999999999') and status='ACTIVE' order by created_at";
		statobj = db.executeSelectQuery(clubmembermaster,
				new String[] { clubid });

		// System.out.println(" status of club memeber master"+
		// statobj.getStatus());

		for (int i = 0; i < statobj.getCount(); i++) {

			String membershipid = db.getValue(i, "membership_id", "");
			String membershipname = db.getValue(i, ",membership_name", "");
			String createtype = db.getValue(i, "createtype", "");
			if ("Auto".equals(createtype))
				mem_id = membershipid;
			mshipidnamemap.put("membershipid", " membershipname");
			mshipidlist.add(mshipidnamemap);

		}
		// System.out.println("" + mshipidlist.size());
		return mshipidlist;
	}

	public static String getMemberType(String groupId) {

		String query = "select value from community_config_settings where key='MEMBER_ACCOUNT_TYPE' and clubid=?";
		String membertype = DbUtil.getVal(query, new String[] { groupId });
		return membertype;
	}

	public static void insertAddMemberSettings(String groupId,
			AddCommunityMemberData memberdata) {
		DBManager db = new DBManager();
		StatusObj statobj = null;

		String config_id = DbUtil.getVal(CLUB_INFO_QUERY,
				new String[] { groupId });

		String emailkey = "unit.memberadd.email";
		String pwdkey = "unit.memberadd.needresetpassword";
		String usernamekey = "unit.memberadd.needresetusername";

		String emailvalue = memberdata.getEmailradio();
		String pwdvalue = memberdata.getPwdresetradio();
		String usernamevlaue = memberdata.getUsernamereset();
	//	System.out.println("eval:" + emailvalue + "pva:" + pwdvalue + "uv" + usernamevlaue);

		statobj = DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE, new String[] {
				config_id, emailkey });
		statobj = DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE, new String[] {
				config_id, pwdkey });
		statobj = DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE, new String[] {
				config_id, usernamekey });
	//	System.out.println("status of config key delete" + statobj.getStatus());

		statobj = DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT, new String[] {
				config_id, emailkey, emailvalue });
		statobj = DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT, new String[] {
				config_id, pwdkey, pwdvalue });
		statobj = DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT, new String[] {
				config_id, usernamekey, usernamevlaue });
		// System.out.println("status of CONFIG_INFO_INSERT "+ statobj.getStatus());

	}

	public static ArrayList<Entity> populateGender() {
		ArrayList<Entity> gendertype = new ArrayList<Entity>();
		gendertype.add(new Entity("Male", "Male"));
		gendertype.add(new Entity("Female", "Female"));
		return gendertype;
	}

	public static ArrayList<String> populateMembershiptypes(String groupId) {
		StatusObj statobj = null;
		DBManager db = new DBManager();
		ArrayList<String> membershiplist = new ArrayList<String>();

		String query = "select membership_name from club_membership_master where clubid=to_number(?,'999999999')";
		statobj = db.executeSelectQuery(query, new String[] { groupId });
		for (int i = 0; i < statobj.getCount(); i++) {
			String membershipname = db.getValue(i, "membership_name", "");
			membershiplist.add(membershipname);
		}

		return membershiplist;
	}

	public static HashMap<String, String> getConfigId(String config_id,
			HashMap configmap) {

		StatusObj statobj = null;
		DBManager db = new DBManager();
		if (configmap == null)
			configmap = new HashMap();

		String CONFIG_INFO_GET = "select * from config where config_id=to_number(?,'999999999')";
		statobj = db.executeSelectQuery(CONFIG_INFO_GET,
				new String[] { config_id });
		for (int i = 0; i < statobj.getCount(); i++) {

			String name = db.getValue(i, "name", "");
			String value = db.getValue(i, "value", "");
			configmap.put(name, value);

		}
       
		return configmap;
	}

	public static void insertProfile(String usrId, String groupId, AddCommunityMemberData memberdata,List<CustomAttribute> custAttList) {
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		String status="";
		String config_id = DbUtil.getVal(CLUB_INFO_QUERY, new String[] { groupId });
		HashMap<String, String> configmap = new HashMap<String, String>();
		configmap = getConfigId(config_id, configmap);

		String membertype = getMemberType(groupId);

		String accountstatusval = "1";
		boolean sendmemberaddemail=false;
		Date tempduedate = null;
		Date tempstartdate = null;
		String startdate="";
		String payduedate="";
		try{
			startdate = formattingFormat.format(parseFormat.parse(memberdata.getStartDate().trim()));
			if(memberdata.getPayDueDate().trim() != null && !memberdata.getPayDueDate().trim().equals(""))	
				payduedate = formattingFormat.format(parseFormat.parse(memberdata.getPayDueDate().trim()));
			memberdata.setStartDate(startdate);
			memberdata.setPayDueDate(payduedate);
		}catch(Exception e){
			
		}
		//String startdate = memberdata.getStartDate().trim();
		//String payduedate = memberdata.getPayDueDate().trim();

		Vector memshipvec = getClubMembershipInfo(groupId);

		if ("EXCLUSIVE".equals(membertype))
			accountstatusval = "3";
		String queryforuserpass = "";
	
		if (configmap != null && configmap.size() > 0) {

			if (("Yes".equalsIgnoreCase((String) configmap
					.get("unit.memberadd.needresetusername")))) {
				queryforuserpass += "U";
				accountstatusval = "6";
			}
			if (("Yes".equalsIgnoreCase((String) configmap
					.get("unit.memberadd.needresetpassword")))) {
				queryforuserpass += "P";
				accountstatusval = "6";
			}
			status=accountstatusval;
			//memberdata.setStatus(accountstatusval);
			memberdata.setRequserpassstr(queryforuserpass);
			
			sendmemberaddemail = ("Yes".equalsIgnoreCase((String) configmap.get("unit.memberadd.email"))) ? true : false; 
		}

		if ("".equals(payduedate)) {
			status=accountstatusval;
			//memberdata.setStatus(accountstatusval);
		} else {
			try {
				tempstartdate = (Date) df.parse(startdate);
				tempduedate = (Date) df.parse(payduedate);
			} catch (Exception e) {

			}
			if (tempduedate.before(tempstartdate)) {
				status=accountstatusval;
				//memberdata.setStatus("9");
			} else {
				status=accountstatusval;
				//memberdata.setStatus(accountstatusval);
			}
		}
		
		if(memberdata.getStatus().equals("")){
			memberdata.setStatus(status);
		}
		StatusObj statobj = createMember(usrId, groupId, memberdata, memshipvec, custAttList);
		if(usrId == null || usrId.equalsIgnoreCase("")){
			statobj = DbUtil.executeUpdateQuery(MEM_PREFERENCE_INSERT,
					new String[] { memberdata.getUserId(), "pref:myurl",
							memberdata.getLoginId() });
		}
		
		String cluburl = getClubURL(groupId, memberdata);

		if (sendmemberaddemail && (usrId == null || usrId.equals("")))
			sendmemberemail(memberdata, cluburl, groupId);
		
		if (usrId != null || !usrId.equals(""))
			updateDiscountCode(usrId,groupId,memberdata);
	}

	public static String getClubURL(String groupId,
			AddCommunityMemberData memberdata) {
		String cluburl = "";
		String servername = EbeeConstantsF.get("serveraddress",
				"http://www.eventbee.com");
		if (!servername.startsWith("http://"))
			servername = "http://" + servername;

		String clublogo = DbUtil
				.getVal(
						"select clublogo from clubinfo where clubid=to_number(?,'999999999')",
						new String[] { groupId });
		String username = DbUtil.getVal(
				"select login_name from authentication where " + " user_id=?",
				new String[] { memberdata.getManagerId() });
		cluburl = ShortUrlPattern.get(username) + "/community/" + clublogo
				+ "/login";
		// System.out.println("in get club url" + cluburl);
		return cluburl;
	}

	public static void sendmemberemail(AddCommunityMemberData memberdata,
			String cluburl, String groupId) {

	   //	System.out.println("in send member email");
		DBManager db = new DBManager();
		String clubname = DbUtil
				.getVal(
						"select clubname from clubinfo where clubid=to_number(?,'999999999')",
						new String[] { groupId });
		String mgrid = DbUtil
				.getVal(
						"select mgr_id from clubinfo where clubid=to_number(?,'999999999')",
						new String[] { groupId });
		String userprofile = "select first_name,last_name,email,phone from user_profile "
				+ " where user_id=? ";

		StatusObj statobj = db.executeSelectQuery(userprofile,
				new String[] { mgrid });
		// System.out.println(" status in send member profile "+
		// statobj.getStatus() + " ," + clubname + " ," + cluburl);
		String mgrfrstname = db.getValue(0, "first_name", "");
		String mgrlastname = db.getValue(0, "last_name", "");
		String mgremail = db.getValue(0, "email", "");
		String mgrphone = db.getValue(0, "phone", "");

		try {
			
			EmailTemplate emailtemplate=null;
			String isformatexists=DbUtil.getVal("select 'yes' from email_templates where purpose='COMMUNITY_MEMBER_SIGNUP_CONFIRMATION' and groupid=?", new String []{groupId});
			if("yes".equals(isformatexists))
				emailtemplate=new EmailTemplate("13579","COMMUNITY_MEMBER_SIGNUP_CONFIRMATION",groupId);
			else
				emailtemplate=new EmailTemplate("13579","WELCOME_COMMUNITY_MEMBER (manually added)");
				 
			
			/*EmailTemplate emailtemplate = new EmailTemplate("13579",
					"WELCOME_COMMUNITY_MEMBER (manually added)");*/
			// System.out.println("after emailtemplate");
			EmailObj obj = new EmailObj();
			// System.out.println("after emailobj");
			Map mp = new HashMap();
			mp.put(TemplateConstants.FIRSTNAME, memberdata.getFirstName());
			mp.put(TemplateConstants.LASTNAME, memberdata.getLastName());
			mp.put(TemplateConstants.USER_ID, memberdata.getLoginId());
			mp.put(TemplateConstants.PWORD, memberdata.getPassword());
			mp.put(TemplateConstants.MEMBERSHIPNAME, memberdata.getMembershiptype());
			mp.put(TemplateConstants.CLUBURL, cluburl);
			mp.put(TemplateConstants.CLUBNAME, clubname);
			mp.put(TemplateConstants.MGRFIRSTNAME, mgrfrstname);
			mp.put(TemplateConstants.MGRLASTNAME, mgrlastname);
			mp.put(TemplateConstants.MGREMAIL, mgremail);
			mp.put(TemplateConstants.MGRPHONE, mgrphone);
			String HTMLcontent = TemplateConverter.getMessage(mp, emailtemplate
					.getHtmlFormat());
			// System.out.println("html:" + HTMLcontent);
			obj.setTo(memberdata.getEmail());
			obj.setFrom(mgremail);
			obj.setBcc("bala@eventbee.com,newsignup@eventbee.com");
			obj.setSubject(TemplateConverter.getMessage(mp, emailtemplate
					.getSubjectFormat()));
			obj.setHtmlMessage(HTMLcontent);
			obj.setSendMailStatus(new SendMailStatus("0", "Manager_Add_Manual",
					"0", "1"));
		
			EventbeeMail.sendHtmlMail(obj);

		} catch (Exception e) {
			EventbeeLogger.logException("com.eventbee.exception",
					EventbeeLogger.INFO, "UserDB.java", "SendMail()",
					"ERROR in SendMail method", e);
			System.out.println("Exception occured at" + e.getMessage());
		}
	}

	public static StatusObj createMember(String usrId, String groupId,AddCommunityMemberData memberdata, 
			Vector memshipvec, List<CustomAttribute> custAttList) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			int rcount = 1;
			StatusObj statobj = null;
			String unitid = "13579";
			memberdata.setUnitId(unitid);
			String managerId = DbUtil.getVal("select mgr_id from clubinfo where clubid=to_number(?,'999999999')",
							new String[] { groupId });
			memberdata.setManagerId(managerId);
			String userid = null;
			rcount = InsertUserProfileEntry(usrId, groupId,memberdata, custAttList);
			
			if (rcount > 0 ) {
				rcount = insert(usrId, groupId, memberdata);

			}

			String clubmembermaster = " select membership_id from club_membership_master "
					+ " where clubid=to_number(?,'999999999') and membership_name=? and status='ACTIVE'";
			String membership_id = DbUtil.getVal(clubmembermaster, new String[] {
					groupId, memberdata.getMembershiptype() });

			clubmship.setMemberShipId(membership_id);
			clubmship.setClubId(groupId);
			memberdata.setMemberShipId(membership_id);
			System.out.println("membershipid" + memberdata.getMemberShipId());

			Object obj = getObj(clubmship, memshipvec);
			if (obj != null) {
				clubmship = (ClubMemberShip) obj;
			}

			String termPrice = clubmship.getTermPrice();
			String termperiod = clubmship.getMshipTerm();
			String tempduedate = memberdata.getPayDueDate();
			java.util.Date duepaydate = null;

			if (tempduedate == null || "".equals(tempduedate)) {

				String tempstdate = memberdata.getStartDate();
				Calendar cal = Calendar.getInstance();
				java.util.Date tempstdt = null;
				java.util.Date tempdt = null;
				try {
					tempstdt = formatter.parse(tempstdate);
				} catch (Exception e) {

				}

				// if (statobjstdt.getStatus()) {
				cal.setTime((java.util.Date) tempstdt);

				if ("annual".equalsIgnoreCase(termperiod)) {
					cal.add(Calendar.MONTH, 12);
				} else if ("Monthly".equalsIgnoreCase(termperiod)) {
					cal.add(Calendar.MONTH, 1);
				} else if ("Quarterly".equalsIgnoreCase(termperiod)) {
					cal.add(Calendar.MONTH, 3);
				} else if ("Half yearly".equalsIgnoreCase(termperiod)) {
					cal.add(Calendar.MONTH, 6);
				} else {
					cal.add(Calendar.MONTH, 12);
				}

				// }
				duepaydate = cal.getTime();
				String paydate = formatter.format(duepaydate);
				memberdata.setPayDueDate(paydate);
			} else {
				try {
					duepaydate = formatter.parse(tempduedate);
					memberdata.setPayDueDate(tempduedate);
				} catch (Exception e) {
					System.out.println("exception in duepaydate" + e.getMessage());

				}

			}

			if (rcount > 0) {
				rcount = insert1(usrId, groupId, memberdata);
			}

			if (rcount > 0) {

				SubscriptionDue subdue = new SubscriptionDue();
				subdue.setUserId(memberdata.getUserId());
				subdue.setPurpose("MEMBER_SUBSCRIPTION");
				subdue.setStatus("");
				subdue.setEntryDate(new java.util.Date());
				subdue.setDueDate(duepaydate);
				subdue.setAmountDue(termPrice);
				rcount = insertSubscriptionDue(usrId, subdue, memberdata);
			}
			if (rcount == 0) {
				statobj = new StatusObj(false, "rollback", null, rcount);
			} else {
				statobj = new StatusObj(true, "success", memberdata, rcount);
			}
			return statobj;
	}

	public static int insertSubscriptionDue(String usrId, SubscriptionDue subdue, AddCommunityMemberData memberdata) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		int rcount = 0;
		StatusObj statobj = null;
		java.util.Date date = null;
		
		if (subdue.getEntryDate() != null)
			date = new java.util.Date(subdue.getEntryDate().getTime());
		
		String duedt = formatter.format(subdue.getDueDate());
		String entrydt = formatter.format(date);
		
		if(usrId != null && !usrId.equalsIgnoreCase("")){
			String UPDATE_SUBSCRIPTION_DUE_USER = "update subscriptiondue set status=?, unitid=?, " +
					"amountdue=CAST(? AS NUMERIC), due_date=to_date(?,'yyyy-mm-dd'), " +
					"entry_date=to_date(?,'yyyy-mm-dd'), purpose=? where userid=?";
			statobj = DbUtil.executeUpdateQuery(UPDATE_SUBSCRIPTION_DUE_USER,new String[]{
					subdue.getStatus(),memberdata.getUnitId(),
					subdue.getAmountDue(),duedt,entrydt,
					subdue.getPurpose(),memberdata.getUserId()});
			
			if (statobj.getStatus() == true)
				rcount = 1;
		}else{
			String INSERT_SUBSCRIPTION_DUE_USER = "insert into subscriptiondue(userid,status,unitid,amountdue,due_date,entry_date,purpose)"
				+ " values(?, ?, ?,CAST(? AS NUMERIC),to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?)";

			statobj = DbUtil.executeUpdateQuery(INSERT_SUBSCRIPTION_DUE_USER,
				new String[] { subdue.getUserId(), subdue.getStatus(),
						memberdata.getUnitId(), subdue.getAmountDue(), duedt,
						entrydt, subdue.getPurpose()
				});


		if (statobj.getStatus() == true)
			rcount = 1;
		}

		return rcount;
	}

	public static int insert1(String usrId, String groupId, AddCommunityMemberData memberdata) {
		int rcount = 0;
		if(usrId == null || usrId.equalsIgnoreCase("")){
			/*String mgrid = DbUtil.getVal("select mgr_id from clubinfo where clubid=to_number(?,'999999999')",
					new String[] { groupId });
			String MEMBER_SEQ_ID = "select nextval('seq_maillist') as memberid";
			String memberid = DbUtil.getVal(MEMBER_SEQ_ID, new String[] {});*/

			/*String listid = DbUtil.getVal("select list_id from mail_list where"
							+ " list_name like 'Active Members%'and unit_id=to_number(?,'999999999') and manager_id=to_number(?,'999999999')",
					new String[] { groupId, mgrid });*/

			/*String INSERT_MEMBER = "insert into member_profile(manager_id,member_id,m_firstname,m_lastname," +
					" m_email,userid,created_at) values (to_number(?,'999999999'),to_number(?,'999999999'),?,?,?,to_number(?,'999999999'),now())";
			StatusObj stat = DbUtil.executeUpdateQuery(INSERT_MEMBER,
			new String[] { mgrid, memberid, 
					memberdata.getFirstName(),memberdata.getLastName(),
					memberdata.getEmail(), memberdata.getUserId() });*/

			/*String INSERT_LIST_MEMBER = "insert into mail_list_members(member_id,list_id,status,"
				+ "created_at) values(to_number(?,'999999999'),to_number(?,'999999999'),'available',now())";
			stat = DbUtil.executeUpdateQuery(INSERT_LIST_MEMBER, new String[] {
			memberid, listid });*/
		}else if(usrId != null && !usrId.equalsIgnoreCase("")){
			/*String UPDATE_MEMBER = "update member_profile set m_firstname=?,m_lastname=?,m_email=? where userid=to_number(?,'999999999')";
			StatusObj stat = DbUtil.executeUpdateQuery(UPDATE_MEMBER,new String[]{memberdata.getFirstName(),memberdata.getLastName(),
					memberdata.getEmail(),memberdata.getUserId()});*/
			
			if(memberdata.getStatus() != null && !memberdata.getStatus().equals("")){
				String requestuserpass="";
				String status = memberdata.getStatus();
				if(status.equals("1"))
					status = "3";
				String authqry = "update authentication set acct_status=to_number(?,'999999999'),requestuserpass=? where user_id=?";
				DbUtil.executeUpdateQuery(authqry,new String[]{status,requestuserpass,memberdata.getUserId()});
			}
		}
		
		
		
		if ("13579".equals(memberdata.getUnitId())) {
			rcount = insertClubMemberEntryNew(usrId, groupId, memberdata);
		} else {
			rcount = insertClubMemberEntry(usrId, groupId, memberdata);

		}

		return rcount;

	}

	public static int insertClubMemberEntryNew(String usrId, String groupId, AddCommunityMemberData memberdata) {
		int rcount = 0;
		StatusObj stat = null;
		
		if(usrId != null && !usrId.equalsIgnoreCase("")){
			String MEMBER_ID = "select member_id from club_member where userid=?";
			String memberId = DbUtil.getVal(MEMBER_ID, new String[]{usrId});
			String CLUB_MEM_UPDATE = "update club_member set mgr_id=to_number(?,'999999999'), " +
					"membership_id=to_number(?,'999999999'), internalid=?, start_date=to_date(?,'yyyy-mm-dd'), " +
					"pay_next_due_date=to_date(?,'yyyy-mm-dd'), ismgr='false', status='ACTIVE', " +
					"created_by='com.eventbee.useraccount.AccountDB', created_at=now() " +
					"where userid=? and member_id=? and clubid=to_number(?,'999999999')";
			stat = DbUtil.executeUpdateQuery(CLUB_MEM_UPDATE,new String[]{
					memberdata.getManagerId(),
					memberdata.getMemberShipId(),memberdata.getInternalId(),
					memberdata.getStartDate(),memberdata.getPayDueDate(),
					memberdata.getUserId(),memberId,groupId});
			if (stat.getStatus() == true)
				rcount = 1;
		}else{
			String MEMBER_ID = "select nextval('seq_clubmemberid') as memberid";
			String memberid = DbUtil.getVal(MEMBER_ID, null);
			String CLUB_MEM_INSERT_NEW = "insert into club_member ( "
					+ " clubid,mgr_id,member_id, membership_id, "
					+ " userid,created_by,created_at,internalid,start_date,pay_next_due_date,status,ismgr)"
					+ "values(to_number(?,'999999999'),to_number(?,'999999999'),?,"
					+ "to_number(?,'999999999'),?,'com.eventbee.useraccount.AccountDB',now(),"
					+ " ?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),'ACTIVE','false')";

			stat = DbUtil.executeUpdateQuery(CLUB_MEM_INSERT_NEW, new String[] {
					groupId, memberdata.getManagerId(), memberid,
					memberdata.getMemberShipId(), memberdata.getUserId(),
					memberdata.getInternalId(), memberdata.getStartDate(),
					memberdata.getPayDueDate()

			});

			if (stat.getStatus() == true)
				rcount = 1;
		}

		return rcount;
	}

	public static int insertClubMemberEntry(String usrId, String groupId, AddCommunityMemberData memberdata) {
		int rcount = 0;
		
		if(usrId != null && !usrId.equalsIgnoreCase("")){
			String CLUB_MEM_PROFILE_UPDATE = "update club_member_profile set mgr_id=to_number(?,'999999999'), " +
					"member_id=?, membership_id=to_number(?,'999999999'), internalid=?,start_date=to_date(?,'yyyy-mm-dd'), " +
					"pay_next_due_date=to_date(?,'yyyy-mm-dd'), created_by='com.eventbee.useraccount.AccountDB', " +
					"created_at=now() where userid=? and clubid=to_number(?,'999999999')";
			StatusObj statobj = DbUtil.executeUpdateQuery(CLUB_MEM_PROFILE_UPDATE,new String[]{
					memberdata.getManagerId(),memberdata.getMemberId(),
					memberdata.getMemberShipId(),memberdata.getInternalId(),
					memberdata.getStartDate(),memberdata.getPayDueDate(),
					memberdata.getUserId(),groupId});
			if (statobj.getStatus() == true)
				rcount = 1;
		}else{
			String CLUB_MEM_INSERT = "insert into club_member_profile ( "
				+ " clubid,mgr_id,member_id, membership_id, "
				+ " userid,created_by,created_at,internalid,start_date,pay_next_due_date)"
				+ " values(to_number(?,'999999999'),to_number(?,'999999999'),?,"
				+ " to_number(?,'999999999'),?,'com.eventbee.useraccount.AccountDB',"
				+ " now(),?,to_date(?,'yyyy-mm-dd'),to_number(?,'yyyy-mm-dd'))";

		StatusObj stat = DbUtil.executeUpdateQuery(CLUB_MEM_INSERT, new String[] { groupId,
						memberdata.getManagerId(), memberdata.getMemberId(),
						memberdata.getMemberShipId(), memberdata.getUserId(),
						memberdata.getInternalId(), memberdata.getStartDate(),
						memberdata.getPayDueDate() });
		if (stat.getStatus() == true)
			rcount = 1;
		}

		return rcount;
	}

	public static int insert(String usrId, String groupId, AddCommunityMemberData memberdata) {
		int rcount = 0;
		if (memberdata.getLoginId() == null
				|| "".equals(memberdata.getLoginId())) {

			String temploginname = memberdata.getFirstName().substring(0, 1)
					+ memberdata.getLastName();
			int usercount = getLoginNameCount(temploginname);
			if (usercount == 0) {
			} else if (usercount > 9) {
				temploginname = temploginname + usercount;
			} else {
				temploginname = temploginname + "0" + usercount;
			}

			memberdata.setLoginId(temploginname);
		}
		rcount = insertAuthenticationEntryWithReqUP(usrId, memberdata);

		return rcount;
	}

	public static Object getObj(Object obj, Vector vec) {
		// System.out.println("in getobj" + obj.toString());
		Object obj1 = null;
		if (vec != null && obj != null) {
			if (vec.contains(obj)) {
				obj1 = vec.elementAt(vec.indexOf(obj));
			}
		}

		return obj1;

	}

	public static int insertAuthenticationEntryWithReqUP(String usrId, AddCommunityMemberData memberdata) {
		int rcount = 0;
		StatusObj statobj = null;
		if(usrId == null || usrId.equals("")){
		String pwd = memberdata.getPassword();
		String userencpass = "";
		try {
			userencpass = (new StringEncrypter(
					StringEncrypter.DES_ENCRYPTION_SCHEME)).encrypt(pwd);
		} catch (Exception e) {

		}
		String AUTHENTICATION_INSERT_REQUP = " insert into authentication(auth_id, "
				+ " org_id,unit_id,role_id,user_id, "
				+ " login_name,password,acct_status, "
				+ " created_by,created_at,description,requestuserpass,membership_status) "
				+ " values (to_number(?,'999999999'),CAST('3467' as integer),to_number(?,'999999999'),CAST('-100' as integer),?,?,?,to_number(?,'999999999'), "
				+ " 'com.eventbee.useraccount.AccountDB', now(),'',?,?) ";
		//System.out.println("status in authentication"+memberdata.getStatus());
		statobj = DbUtil.executeUpdateQuery(AUTHENTICATION_INSERT_REQUP,
				new String[] { memberdata.getUserId(), memberdata.getUnitId(),
						memberdata.getUserId(), memberdata.getLoginId(),
						userencpass, memberdata.getStatus(),
						memberdata.getRequserpassstr(),
						memberdata.getMembershipStatus()

				});

		// System.out.println("status of AUTHENTICATION_INSERT_REQUP"+
		// statobj.getStatus());
		if (statobj.getStatus() == true)
			rcount = 1;
		}else if(usrId != null && !usrId.equals("")){
			String AUTH_UPDATE_QUERY = "update authentication set login_name=? where user_id=?";
			statobj = DbUtil.executeUpdateQuery(AUTH_UPDATE_QUERY,	new String[] {memberdata.getLoginId(),usrId});
			if (statobj.getStatus() == true)
				rcount = 1;
			
		}
			
		return rcount;

	}

	public static int getLoginNameCount(String loginname) {

		int count = 0;
		String NAME_EXISTS_QUERY1 = " select count(*) as count from AUTHENTICATION "
				+ " Where Login_Name like ?";

		String cn = DbUtil.getVal(NAME_EXISTS_QUERY1, new String[] { loginname
				+ "%" });
		count = Integer.parseInt(cn);

		return count;
	}

	public static int InsertUserProfileEntry(String usrId, String groupId, AddCommunityMemberData memberdata,
			List<CustomAttribute> custAttList) {
		StatusObj statobj = null;
		int rcount = 0;
		
		if(usrId != null && !usrId.equalsIgnoreCase("")){
			memberdata.setUserId(usrId);
			String USER_PROFILE_UPDATE = "update user_profile set first_name=?, last_name=?, email=?, phone=?, gender=?, street=?, city=?, state=?, zip=?, country=?, created_by='com.eventbee.useraccount.AccountDB', created_at=now() where user_id=?";
			statobj = DbUtil.executeUpdateQuery(USER_PROFILE_UPDATE,new String[]{
					memberdata.getFirstName(),memberdata.getLastName(),
					memberdata.getEmail(),memberdata.getPhone(),
					memberdata.getGender(),memberdata.getStreet(),
					memberdata.getCity(),memberdata.getState(),
					memberdata.getZip(),memberdata.getCountry(),usrId});
			if (statobj.getStatus() == true)
				rcount = 1;
		}else{
			String userid = DbUtil.getVal("select nextval('seq_userid')", null);
			memberdata.setUserId(userid);

			String USER_PROFILE_INSERT = "insert into user_profile(user_id,first_name, "
					+ " last_name,email,phone,gender,street,city,state,zip, "
					+ " country,created_by,created_at) values (?,?,?,?,?,?,?,?,?,?,?,"
					+ " 'com.eventbee.useraccount.AccountDB',now()) ";

			statobj = DbUtil.executeUpdateQuery(USER_PROFILE_INSERT, new String[] {
					memberdata.getUserId(), memberdata.getFirstName(),
					memberdata.getLastName(), memberdata.getEmail(),
					memberdata.getPhone(), memberdata.getGender(),
					memberdata.getStreet(), memberdata.getCity(),
					memberdata.getState(), memberdata.getZip(),
					memberdata.getCountry() });

			if (statobj.getStatus() == true)
				rcount = 1;

			String INSERT_INTO_UNIVERSITY = "insert into member_university(university,year,"
					+ " userid) values('','',?) ";
			statobj = DbUtil.executeUpdateQuery(INSERT_INTO_UNIVERSITY, new String[] { userid });

			String INSERT_INTO_COMPANY = "insert into member_company(company,from_dt,to_dt,"
					+ " userid) values('','','',?) ";
			statobj = DbUtil.executeUpdateQuery(INSERT_INTO_COMPANY, new String[] { userid });
			
		}
		
		/*ArrayList l1 = new ArrayList();
		MailListMember ml = new MailListMember();
		ml.setEmail(memberdata.getEmail());
		ml.setManagerId(memberdata.getManagerId());
		ml.setUserId(memberdata.getUserId());
		l1.add(ml);
		Config config = new Config();
		config = getConfig(memberdata.getUnitId(), "UNIT_BASE_CONFIG");
		if (config != null) {
			String unitlistid = config.getKeyValue("unit.maillist.id", "none");// 0
			if (!(unitlistid.equals("none")))
				addMembers(l1, unitlistid);
		}*/

		
		// inserting response data for custom attributes
		
		if(usrId != null && !usrId.equalsIgnoreCase("") && custAttList.size()>0){
			String responseId=DbUtil.getVal("select responseid from custom_attrib_response_master where userid=?",new String[]{usrId});
			
			if(responseId == null){
				responseId=DbUtil.getVal("select nextval('attributes_survey_responseid')",null);
				if(responseId!=null && !("".equals(responseId))){
					String attrib_setid=DbUtil.getVal("select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose='CLUB_MEMBER_SIGNUP_PAGE'", new String[]{groupId});
					String response_master_query="insert into custom_attrib_response_master(responseid,attrib_setid,userid,response_dt" +
							") values(to_number(?,'999999999999'),to_number(?,'9999999999999'),?,now())";
					StatusObj staobj=DbUtil.executeUpdateQuery(response_master_query, new String[]{responseId,attrib_setid,memberdata.getUserId()});
				}
			}
			if(responseId!=null && !("".equals(responseId))){
				
				DbUtil.executeUpdateQuery("delete from custom_attrib_response where " +
						"responseid=to_number(?,'99999999999999')",new String[]{responseId});
				
				String insert_response_query="insert into custom_attrib_response(responseid,attrib_name,response)" +
				" values(to_number(?,'9999999999999'),?,?)";
					
					for (CustomAttribute ca : custAttList) {
						String response=ca.getPropValue();
						if(ca.getQtype().equals("checkbox")){
							ArrayList checkList=ca.getChkList();
							if(checkList!=null){
							String checkbox_response="";
							for(int i=0;i<checkList.size();i++)
							{
								if(i==0)
								checkbox_response+=(String)checkList.get(i);
								else
									checkbox_response+=","+(String)checkList.get(i);
							}
							response=checkbox_response;
							}
						}
						DbUtil.executeUpdateQuery(insert_response_query, new String[]{responseId,ca.getName(),response});
					}
				}
			}else if(custAttList.size()>0){
			String bResponseId=DbUtil.getVal("select nextval('attributes_survey_responseid')",null);
			if(bResponseId!=null && !("".equals(bResponseId))){
				String attrib_setid=DbUtil.getVal("select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose=''", new String[]{groupId});
				String response_master_query="insert into custom_attrib_response_master(responseid,attrib_setid,userid,response_dt" +
						") values(to_number(?,'999999999999'),to_number(?,'9999999999999'),?,now())";
				StatusObj staobj=DbUtil.executeUpdateQuery(response_master_query, new String[]{bResponseId,attrib_setid,memberdata.getUserId()});
				if(staobj.getStatus()){
					String insert_response_query="insert into custom_attrib_response(responseid,attrib_name,response)" +
							" values(to_number(?,'9999999999999'),?,?)";
					for (CustomAttribute ca : custAttList) {
						String response=ca.getPropValue();
						if(ca.getQtype().equals("checkbox")){
							ArrayList checkList=ca.getChkList();
							if(checkList!=null){
							String checkbox_response="";
							for(int i=0;i<checkList.size();i++)
							{
								if(i==0)
								checkbox_response+=(String)checkList.get(i);
								else
									checkbox_response+=", "+(String)checkList.get(i);
							}
							response=checkbox_response;
							}
						}
						DbUtil.executeUpdateQuery(insert_response_query, new String[]{bResponseId,ca.getName(),response});
					}
				}
			}
		}
		

		return rcount;
	}

	public static void addMembers(ArrayList members, String listId) {

		StatusObj st = null;
		/*String INSERT_MEMBER = "insert into member_profile(manager_id,member_id,m_name,m_firstname,m_lastname,m_email,"
				+ " m_phone,m_company ,m_jobtype ,m_place ,m_age ,m_gender ,m_email_type,userid) 	"
				+ " values (to_number(?,'999999999'),to_number(?,'999999999'),?,?,?,?,?,'','','','',?,'',to_number(?,'999999999'))";
*/
		if (!members.isEmpty()) {
			String createdBy = "Auto Subscription";

			Iterator iter = members.iterator();
			while (iter.hasNext()) {
				MailListMember member = (MailListMember) iter.next();
				member.setListId(listId);
				if ("0".equals(member.getManagerId())) {
					createdBy = (member.getUserId() == null) ? createdBy
							: createdBy + "(Member)";
				} else if ("1".equals(member.getManagerId())) {
					createdBy = "Self";
				} else {
					createdBy = "Manager";
				}
				member.setCreatedBy(createdBy);
				/*StatusObj statobj = isMemberExisting(member);
				if (statobj.getStatus()) {
					member.setMemberId(statobj.getData().toString());
					addMemberLink(member);
				} else {
					String memberid = getNewMemberId();
					member.setMemberId(memberid);
					System.out.println("in else memberid" + memberid);
					addMemberLink(member);
					st = DbUtil.executeUpdateQuery(INSERT_MEMBER, new String[] {
							member.getManagerId(), member.getMemberId(),
							member.getName(), member.getFirstname(),
							member.getLastname(), member.getEmail(),
							member.getPhone(), member.getGender(),
							member.getUserId() });

					// System.out.println(
					// "status of Insert member(member profile)"+
					// st.getStatus());
				}*/// end of else
			}// end of while
		}// end of if

	}

	public static String getNewMemberId() {
		String MEMBER_SEQ_ID = "select  nextval('seq_maillist') as memberid";
		String new_memberid = DbUtil.getVal(MEMBER_SEQ_ID, null);

		return new_memberid;
	}

	public static void addMemberLink(MailListMember member) {
		/*StatusObj statobj = null;
		String INSERT_LIST_MEMBER = " insert into mail_list_members(member_id,list_id,"
				+ " status,created_at,created_by) values(to_number(?,'999999999'),"
				+ " to_number(?,'999999999'),'available',now(),?)";

		statobj = DbUtil.executeUpdateQuery(INSERT_LIST_MEMBER,
				new String[] { member.getMemberId(), member.getListId(),
						member.getCreatedBy() });*/

	}

	public static StatusObj isMemberExisting(MailListMember member) {
		
		StatusObj statobj = new StatusObj(false, null, "0");
		/*String MEMBER_EXISTS_INLIST = "select mp.member_id from member_profile mp,"
				+ " mail_list_members mlm where  m_email=? and "
				+ " mp.member_id=mlm.member_id and list_id=to_number(?,'999999999') ";

		String exmemid = DbUtil.getVal(MEMBER_EXISTS_INLIST, new String[] {
				member.getEmail(), member.getListId() });
		if (exmemid != null) {
			statobj.setStatus(true);
			statobj.setData(exmemid);
		}*/

		return statobj;
	}

	public static Config getConfig(String refid, String purpose) {

		// refid is nothing but unitid here

		StatusObj statusObj = null;
		String configid = null;
		Config cf = new Config();
		cf.setRefID(refid);
		cf.setPurpose(purpose);
		String config_id = getConfigID(refid, purpose);
		if (!("".equals(config_id) && config_id == null)) {
			cf.setConfigID(configid);
			HashMap<String, String> configmap = new HashMap<String, String>();
			configmap = getConfigId(config_id, configmap);
			cf.setConfigHash(configmap);
		}

		return cf;
	}

	public static String getConfigID(String refid, String purpose) {

		StatusObj statusobj = null;
		boolean flag = false;
		String CONFIG_ID_GET = "select config_id from config_master where ref_id=to_number(?,'999999999') and purpose=?";
		String config_id = DbUtil.getVal(CONFIG_ID_GET, new String[] { refid,
				purpose });

		return config_id;
	}

	public static Vector getClubMembershipInfo(String groupId) {

		StatusObj statobj = null;
		DBManager db = new DBManager();
		Vector memshipvec = new Vector();
		String CLUB_UNIT_MEMSHIP_QUERY = " select b.clubname,a.membership_id,a.membership_name,a.description,a.createtype,a.status,"
				+ "a.currency_type,a.price,a.term_fee,a.mship_term from club_membership_master as a,"
				+ "clubinfo as b where a.clubid=b.clubid and  a.clubid=to_number(?,'999999999') and b.unitid=CAST('13579' as integer) and a.status='ACTIVE'";

		statobj = db.executeSelectQuery(CLUB_UNIT_MEMSHIP_QUERY,
				new String[] { groupId });
		// System.out.println("CLUB_UNIT_MEMSHIP_QUERY" + statobj.getStatus());
		for (int i = 0; i < statobj.getCount(); i++) {

			clubmship.setMemberShipId(db.getValue(i, "membership_id", ""));
			clubmship.setMemberShipName(db.getValue(i, "membership_name", ""));
			clubmship.setDescription(db.getValue(i, "description", ""));
			clubmship.setCurrencyType(db.getValue(i, "currency_type", ""));
			clubmship.setPrice(db.getValue(i, "price", ""));
			clubmship.setTermPrice(db.getValue(i, "term_fee", ""));
			clubmship.setMshipTerm(db.getValue(i, "mship_term", ""));
			memshipvec.add(clubmship);
		}
		return memshipvec;

	}

	public static HashMap validateAttendeeSignUp(String usrId, String groupId,AddCommunityMemberData memberdata) {

		HashMap hm = new HashMap();
		ProfileValidator pv = new ProfileValidator();
		int i = hm.size();

		
		if(usrId == null || usrId.equals("")){	
			hm = UserDB.validateUserName("", memberdata.getLoginId().toString(),
					LOGIN_LABEL + " already exists", "loginnameExist", hm,
					"signup", LOGIN_LABEL, "13579");
		}else if(usrId != null && !usrId.equals("")){
			hm = UserDB.validateUserName("", memberdata.getLoginId().toString(),
					LOGIN_LABEL + " already exists", "loginnameExist", hm,
					"signup", LOGIN_LABEL, "13579",usrId);
			
			hm = UserDB.validateDiscountCode("", memberdata,
					"DiscountCode" + " is invalid", "discountcodeinvalid", hm,
					"CLUB_SIGNUP", usrId,groupId);
		}
			if (memberdata.getLoginId().toString() != null
					&& !"".equals((memberdata.getLoginId().toString()).trim())) {
				if (!pv.checkNameValidity(memberdata.getLoginId().toString(), true)) {
					hm.put("invalid_id", "Invalid " + LOGIN_LABEL
							+ ". Use alphanumeric characters only");
				}
			}
			
		if(usrId == null || usrId.equals("")){
			hm = UserDB.validateString(memberdata.getLoginId().toString(),
					LOGIN_LABEL, "loginLength", 200, 4, hm);
			hm = UserDB.validateString(memberdata.getPassword().toString(),
					"Password", "pwdLength", 200, 4, hm);
		}

		hm = UserDB.validateString(memberdata.getFirstName().toString(),
				"First Name", "firstnameError", 50, 1, hm);
		hm = UserDB.validateString(memberdata.getLastName().toString(),
				"Last Name", "lastnameError", 50, 1, hm);
		hm = UserDB.validateEmail(memberdata.getEmail().toString(), "Email",
				"emailError", hm);
		hm = UserDB.validateRadio(memberdata.getGender().toString(), "Gender",
				"genderError", hm);
		hm = UserDB.validateString(memberdata.getPhone().toString(), "Phone",
				"phoneError", 50, 1, hm);

		hm = validateStartDate(memberdata.getStartDate().toString(),
				"Start date", "startdateError", hm);
		hm = validatePayDueDate(memberdata.getPayDueDate().toString(),
				"Pay due date", "payduedateError", hm);
		
		hm = UserDB.validateRadio(memberdata.getMembershiptype().toString(), "Membership Type",
				"membershipTypeError", hm);

		int k = hm.size() - i;
		if (k == 1)
			hm.put("generalError", "There is " + k + " error:");
		else if (k > 1)
			hm.put("generalError", "There are " + k + " errors:");

		return hm;
	}
	
	
	public static HashMap validateAttendeePassword(String usrId, String pwd, String rePwd) {
		
		HashMap hm = new HashMap();
		ProfileValidator pv = new ProfileValidator();
		int i = hm.size();
		
		hm = UserDB.validateString(pwd.toString(),
				"Password", "pwdLength", 200, 4, hm);
		/*hm = UserDB.validateString(rePwd.toString(),
				"RePassword", "repwdLength", 200, 4, hm);*/
		
		int k = hm.size() - i;
		if (k == 1)
			hm.put("generalError", "There is " + k + " error:");
		else if (k > 1)
			hm.put("generalError", "There are " + k + " errors:");

		return hm;
	}

	public static HashMap validateStartDate(String startDate, String label,
			String path, HashMap hm) {
		StatusObj stob = new StatusObj(false, "", null);
		stob = EventBeeValidations.isValidDate1(startDate, label);

		if (!stob.getStatus()) {
			hm.put(path, stob.getErrorMsg());
		} /*else {

			if ((new java.util.Date()).before((java.util.Date) stob.getData())) {
				stob.setErrorMsg("Invalid Start Date, Start Date should not be greater than Current Date");
				hm.put(path, stob.getErrorMsg());
			}
		}*/
		return hm;
	}

	public static HashMap validatePayDueDate(String payDueDate, String label,
			String path, HashMap hm) {
		StatusObj stob = new StatusObj(false, "", null);
		stob = EventBeeValidations.isValidDate(payDueDate, label);

		if (!stob.getStatus()) {
			hm.put(path, stob.getErrorMsg());
		}
		return hm;
	}
	
	public static List<CustomAttribute> getCustomAttributeList(String groupId, String usrId){
		List<CustomAttribute> custAttList = new ArrayList<CustomAttribute>();
		try {
			HashMap<String,String> hm = new HashMap<String,String>();
			if(usrId != null){
				String qry ="select * from custom_attrib_response where responseid=" +
					"(select responseid from custom_attrib_response_master where userid=?)";
			
				DBManager dbmgr = new DBManager();
				StatusObj stobj = dbmgr.executeSelectQuery(qry, new String[] {usrId});
				if (stobj != null && stobj.getStatus()) {
					for (int i = 0; i < stobj.getCount(); i++) {
						String attrName=dbmgr.getValue(i, "attrib_name", "");
						String response=dbmgr.getValue(i, "response", "");
						hm.put(attrName, response);
					}
				}
			}
			
			HashMap<String,ArrayList<Entity>> attributes=new HashMap<String,ArrayList<Entity>>();
			ArrayList<Entity> options;
			String selQry = "select * from custom_attrib_options where " +
					"attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose='CLUB_MEMBER_SIGNUP_PAGE')";
			
			DBManager dbmanager1 = new DBManager();
			StatusObj statobj1 = dbmanager1.executeSelectQuery(selQry,new String[] {groupId});
			if (statobj1 != null && statobj1.getStatus()) {
				for (int i = 0; i < statobj1.getCount(); i++) {
					String attribute=dbmanager1.getValue(i, "attrib_id", "");
					if(attributes.containsKey(attribute)){
						options = attributes.get(attribute);
						options.add(new Entity(dbmanager1.getValue(i,"option",""),dbmanager1.getValue(i,"option_val","")));
					}else{
						options=new ArrayList<Entity>();
						options.add(new Entity(dbmanager1.getValue(i,"option",""),dbmanager1.getValue(i,"option_val","")));
						attributes.put(attribute, options);
					}
				}
			}
			
			String query = "select * from custom_attribs where " +
			"attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose='CLUB_MEMBER_SIGNUP_PAGE')";
			
			DBManager dbmanager = new DBManager();
			StatusObj statobj = dbmanager.executeSelectQuery(query,new String[] {groupId});
			if (statobj != null && statobj.getStatus()) {
				int recordcounttodata = statobj.getCount();
				for (int i = 0; i < recordcounttodata; i++) {
					CustomAttribute ca = new CustomAttribute();
					ca.setName(dbmanager.getValue(i, "attribname", ""));
					String attName = dbmanager.getValue(i, "attribname", "");
					
					ca.setAttribsetid(dbmanager.getValue(i,"attrib_setid", ""));
					ca.setQtype(dbmanager.getValue(i, "attribtype", ""));
					String attType = dbmanager.getValue(i, "attribtype", "");
					
					ca.setIsRequired(dbmanager.getValue(i, "isrequired", ""));
					ca.setAttribid(dbmanager.getValue(i, "attrib_id", ""));
					ca.setSize(dbmanager.getValue(i, "textboxsize", ""));
					ca.setRows(dbmanager.getValue(i, "rows", ""));
					ca.setColumns(dbmanager.getValue(i, "cols", ""));
					String attId= dbmanager.getValue(i, "attrib_id", "");
					if(attributes.containsKey(attId))
						ca.setOptionsList(attributes.get(attId));
					custAttList.add(ca);
					if(attType.equals("checkbox")){
						if(!hm.isEmpty() && hm.containsKey(attName)){
							String csstr = hm.get(attName);
							String[] chlist = csstr.split(",");
							for (String str : chlist)
						          ca.getChkList().add(str.trim());
						}
					}else
						if(!hm.isEmpty() && hm.containsKey(attName))
							ca.setPropValue(hm.get(attName));
				}
			}
			return custAttList;
		} catch (Exception e) {
			
		}
		return custAttList;
	}
	
	public static AddCommunityMemberData getMemberData(String userId){
		
		AddCommunityMemberData memberData = new AddCommunityMemberData();
		try {
			String memberShipId = null;
			String query = "select * from user_profile where user_id=?";
			
			DBManager dbmanager = new DBManager();
			StatusObj statobj = dbmanager.executeSelectQuery(query,new String[] {userId});
			if (statobj != null && statobj.getStatus()) {
				memberData.setFirstName(dbmanager.getValue(0, "first_name", ""));
				memberData.setLastName(dbmanager.getValue(0, "last_name", ""));
				memberData.setEmail(dbmanager.getValue(0, "email", ""));
				memberData.setGender(dbmanager.getValue(0, "gender", ""));
				memberData.setPhone(dbmanager.getValue(0, "phone", ""));
				memberData.setStreet(dbmanager.getValue(0, "street", ""));
				memberData.setCity(dbmanager.getValue(0, "city", ""));
				memberData.setZip(dbmanager.getValue(0, "zip", ""));
				memberData.setMembershiptype(dbmanager.getValue(0, "zip", ""));
			}
			String query1 ="select * from authentication where user_id=?";
			DBManager dbmanager1 = new DBManager();
			StatusObj statobj1 = dbmanager1.executeSelectQuery(query1,new String[] {userId});
			if (statobj1 != null && statobj1.getStatus()) {
				memberData.setLoginId(dbmanager1.getValue(0, "login_name", ""));
				memberData.setPassword(dbmanager1.getValue(0,"password", ""));	
				String status = dbmanager1.getValue(0,"acct_status", "");
				if(status.equals("3"))
					memberData.setStatus("1");
				else
					memberData.setStatus(status);
			}
			String query2 ="select * from club_member where userid=?";
			DBManager dbmanager2 = new DBManager();
			StatusObj statobj2 = dbmanager2.executeSelectQuery(query2,new String[] {userId});
			if (statobj2 != null && statobj2.getStatus()) {
				memberData.setInternalId(dbmanager2.getValue(0, "internalid", ""));
				memberData.setStartDate(dbmanager2.getValue(0, "start_date", "")); 
				memberData.setPayDueDate(dbmanager2.getValue(0,"pay_next_due_date", ""));	
				memberData.setMemberShipId(dbmanager2.getValue(0,"membership_id", ""));
				memberShipId = dbmanager2.getValue(0,"membership_id", "");
			}
			String query3="select membership_name from club_membership_master where membership_id=to_number(?,'99999999999')";
			DBManager dbmanager3 = new DBManager();
			if(memberShipId != null && !memberShipId.equals("")){
				StatusObj statobj3 = dbmanager3.executeSelectQuery(query3, new String[] {memberShipId});
				if(statobj3 != null && statobj3.getStatus())
					memberData.setMembershiptype(dbmanager3.getValue(0, "membership_name", ""));
			}
			
			String query4="select discountcode from transaction t,user_profile u where t.refid=u.user_id and t.refid=?";
			DBManager dbmanager4 = new DBManager();
			StatusObj statobj4 = dbmanager4.executeSelectQuery(query4,new String[] {userId});
			if (statobj4 != null && statobj4.getStatus()) {
				String dis = dbmanager4.getValue(0, "discountcode", "");
				if(dis == null)
					memberData.setDiscountcode("");
				memberData.setDiscountcode(dis);
				memberData.setDiscountCodeExist("Yes");
			}
			
			return memberData;
		} catch (Exception e) {
			
		}
		return memberData;
	}
	
	public static void updatePassword(String usrId, String pwd){
		

		StatusObj statobj = null;
		String userencpass = "";
		try {
			userencpass = (new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME)).encrypt(pwd);
		} catch (Exception e) {

		}
		String AUTH_UPDATE_QUERY = "update authentication set password=? where user_id=?";
		
		//System.out.println("status in authentication"+memberdata.getStatus());
		statobj = DbUtil.executeUpdateQuery(AUTH_UPDATE_QUERY,
				new String[] { userencpass, usrId});
		// System.out.println("status of AUTHENTICATION_INSERT_REQUP"+
		// statobj.getStatus());
		
	}
	
	
	public static void updateDiscountCode(String usrId, String groupId, AddCommunityMemberData memberdata){
		
		String membership_id="";
		String fee="";
		String newfee1="";
		double newfee=0;
		String s="";
		String tfee="";
		String price="";
		double fee1=0.0;
		String discount="";
		String discounttype="";
		HashMap discountsMap = new HashMap();
		
		//HashMap hmp1=getMemshipDiscounts(groupId, memberdata.getDiscountcode());
		
		//if(hmp1.containsKey(memberdata.getMemberShipId())){
			if(!memberdata.getDiscountcode().trim().equals("")){
				discountsMap=getDiscountDetails(groupId,memberdata.getDiscountcode());
				discount=(String)discountsMap.get("discount");
				discounttype=(String)discountsMap.get("discounttype");
			}
			
			System.out.println("\n discount: "+discount);
			System.out.println("\n discounttype: "+discounttype);
			
			if(discounttype==null) discounttype="";
			
			Vector v=getMemberShipDetails(memberdata.getMemberShipId(),groupId);
			for(int i=0;i<v.size();i++){
			
				HashMap hmp2=(HashMap)v.elementAt(i);
				//membership_id=(String)hmp2.get("memship_"+i);
				tfee=(String)hmp2.get("termfee_"+i);
				price=(String)hmp2.get("fee_"+i);
				fee1=Double.parseDouble(tfee)+Double.parseDouble(price);
				fee=Double.toString(fee1);
				Double discountVal=0.00;
				if(discount == null || discount.equals(""))
					discount = "0.00";
				if(discounttype.equals("PERCENTAGE")){
					discountVal=fee1*Double.parseDouble(discount)/100;
					discount=Double.toString(discountVal);
				}
				//if(hmp1.get(membership_id)!=null)
				//{
				newfee=Double.parseDouble(fee)-Double.parseDouble(discount);
				if(newfee<0)
					newfee=0.00;
				newfee1=Double.toString(newfee);
				
				System.out.println("\n newfee: "+newfee1);
				
				String UPDATE_TRAN_DISCOUNT = "update transaction set discountcode=?,grandtotal=?,discount=CAST(? as numeric),totalamount=? where refid=?";
				System.out.println("\n discount: "+discount);
				DbUtil.executeUpdateQuery(UPDATE_TRAN_DISCOUNT, new String[] { memberdata.getDiscountcode(),newfee1,discount,newfee1, usrId});
				//}

			}
		//}
		
	}
	
	public static Vector getMemberShipDetails(String memberShipId, String groupId){
		String query="select term_fee,price from club_membership_master where membership_id=to_number(?,'99999999999') and clubid=to_number(?,'99999999999') and status='ACTIVE'";

		Vector vec=new Vector();
		HashMap hm=new HashMap();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query,new String []{memberShipId,groupId});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++)
			{
				hm=new HashMap();
				System.out.println("\n term_fee: "+db.getValue(i,"term_fee",""));
				System.out.println("\n price: "+db.getValue(i,"price",""));
				//hm.put("memship_"+i,db.getValue(i,"membership_id",""));
				hm.put("termfee_"+i,db.getValue(i,"term_fee",""));
				hm.put("fee_"+i,db.getValue(i,"price",""));
				
				vec.add(hm);
			}

	}

	return vec;
	}
	
	public static HashMap getMemshipDiscounts(String groupid,String code){
		String query="select membership_id from coupon_membership  where couponid in(select couponid from coupon_master where couponid in (select couponid from coupon_codes where couponcode=?) and groupid=?)";
		HashMap hmp=new HashMap();
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query,new String []{code,groupid});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++)
			{
				hmp.put(db.getValue(i,"membership_id",""),"");
			}

		}
		return hmp;
	}
	
	public static HashMap getDiscountDetails(String groupid,String code){
		
		String query="select discount,discounttype from coupon_master where couponid in (select couponid from coupon_codes where couponcode=?) and groupid=? and coupontype=?";
		DBManager db=new DBManager();
		HashMap discountsMap=new HashMap();
		StatusObj sb=db.executeSelectQuery(query,new String []{code,groupid,"CLUB_SIGNUP"});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
				discountsMap.put("discount",db.getValue(i,"discount",""));
				discountsMap.put("discounttype",db.getValue(i,"discounttype",""));
			}
		}
		return discountsMap;
		}

}
