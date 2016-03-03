package com.mycommunities.dbhelpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.ProfileValidator;
import com.eventbee.general.StatusObj;
import com.eventbee.general.StringEncrypter;
import com.mycommunities.beans.*;
import com.user.dbhelpers.UserDB;
import com.mycommunities.dbhelpers.AddCommunityMemberDB;
import com.mycommunities.helpers.EventBeeValidations;

public class PassiveMemberDB {

	public static String mem_id = "";
	public static String man_id = null;
	static String LOGIN_LABEL = "User Name";
	public static	ClubMemberShip clubmship=new ClubMemberShip();

	public static HashMap validateAttendeeSignUp(
			AddCommunityMemberData memberdata) {

		HashMap hm = new HashMap();
		ProfileValidator pv = new ProfileValidator();
		int i = hm.size();

		

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
		
		hm= validateStartDate(memberdata.getStartDate().toString(),"Start date","startdateError",hm);
	    hm= validatePayDueDate(memberdata.getPayDueDate().toString(),"Pay due date","payduedateError",hm);
		int k = hm.size() - i;
		if (k == 1)
			hm.put("generalError", "There is " + k + " error:");
		else if (k > 1)
			hm.put("generalError", "There are " + k + " errors:");

		return hm;
	}
     
	 public static HashMap validateStartDate(String startDate,String label,String path,HashMap hm){
	    	StatusObj stob=new StatusObj(false,"",null);
	        stob=EventBeeValidations.isValidDate1(startDate,label);
	        if(!stob.getStatus()){
	    	    hm.put(path,stob.getErrorMsg());
		}
	        else{
	        	
	            if( (new java.util.Date()).before((java.util.Date)stob.getData() ) ){
	     		    stob.setErrorMsg("Invalid Start Date Start Date is after Current Date");
	     			hm.put(path,stob.getErrorMsg());
	     		}
	         }
		return hm;
	    }
	 
	  public static HashMap validatePayDueDate(String payDueDate,String label,String path,HashMap hm){
	    	StatusObj stob=new StatusObj(false,"",null);
	        stob=EventBeeValidations.isValidDate(payDueDate,label);
	        
	        if(!stob.getStatus()){
	        	 hm.put(path,stob.getErrorMsg());
		}
	    	return hm;
	    }
	public static String getmanagerid(String clubid) {

		String clubinfo = "Select mgr_id from clubinfo where clubid=to_number(?,'999999999')";
		man_id = DbUtil.getVal(clubinfo, new String[] { clubid });
		return man_id;

	}

	public static ArrayList<HashMap<String, String>> getclubmembermaster(
			String clubid) {

		//System.out.println("AddCommunityMemeberDbhelper " + clubid);

		DBManager db = new DBManager();
		StatusObj statobj = null;

		HashMap<String, String> mshipidnamemap = new HashMap<String, String>();
		ArrayList mshipidlist = new ArrayList();

		String clubmembermaster = " select membership_id,membership_name,createtype from club_membership_master"
				+ " where clubid=to_number(?,'999999999') and status='ACTIVE' order by created_at";
		statobj = db.executeSelectQuery(clubmembermaster,
				new String[] { clubid });
	//	System.out.println(" status of club memeber master "+ statobj.getStatus());

		for (int i = 0; i < statobj.getCount(); i++) {

			ClubMemberShip clubinfo = new ClubMemberShip();
			String membershipid = db.getValue(i, "membership_id", "");
			String membershipname = db.getValue(i, ",membership_name", "");
			String createtype = db.getValue(i, "createtype", "");
			clubinfo.setClubId(clubid);
			clubinfo.setMemberShipId(membershipid);
			clubinfo.setMemberShipName(membershipname);
			if ("Auto".equals(createtype))
				mem_id = membershipid;
			String midclubid = clubid + "~" + membershipid;
			mshipidnamemap.put("midclubid", " membershipname");
			mshipidlist.add(mshipidnamemap);

		}
	//	System.out.println("" + mshipidlist.size());
		return mshipidlist;
	}

	public static void getClubURL(String groupId,
			AddCommunityMemberData memberdata) {

		String cluburl = "";
		String servername = EbeeConstantsF.get("serveraddress",
				"http://www.eventbee.com");
		if (!servername.startsWith("http://"))
			servername = "http://" + servername;

	}

	public static void saveProfile(String clubid,
			AddCommunityMemberData memberdata) {

		//System.out.println("in save profile " +clubid+memberdata.getMembershiptype());
		StatusObj statobj = null;
		Vector memshipvec = getClubMembershipInfo(clubid);
		statobj = createPassiveMember(clubid, memberdata, memshipvec);

	}

	public static StatusObj createPassiveMember(String clubid,
			AddCommunityMemberData memberdata, Vector memshipvec) {
		
		
		 DateFormat  formatter=new SimpleDateFormat("yyyy-MM-dd");
		// System.out.println("in create passive member" + memberdata.getUserId());
		StatusObj statobj = null;
		int rcount = 1;
		String acctstatus = "1";
		memberdata.setStatus(acctstatus);
		Date startdate=null;
		Date payduedate=null;
		try{
			 startdate=(Date)formatter.parse(memberdata.getStartDate().trim());
			 payduedate=(Date)formatter.parse(memberdata.getPayDueDate().trim());
		}catch(Exception e){
			
		}
		if(payduedate!=null){
			if(payduedate.before(startdate)){
				memberdata.setStatus("9");
				memberdata.setMembershipStatus("INDIRECT");
			}
		}
	
		
		
		String unitid = "13579";
		memberdata.setUnitId(unitid);
		String userid = DbUtil.getVal("select nextval('seq_userid')", null);
		memberdata.setUserId(userid);
		String mgrid = DbUtil
				.getVal(
						"select mgr_id from clubinfo where clubid=to_number(?,'999999999')",
						new String[] { clubid });
		memberdata.setManagerId(mgrid);
		String loginname = clubid + "_" + memberdata.getUserId();
		memberdata.setLoginId(loginname);
		memberdata.setPassword(loginname);
		// System.out.println("before insertattendeedata");
		statobj = insertAttendeeData(memberdata);
		
		String clubmembermaster= " select membership_id from club_membership_master " +
        " where clubid=to_number(?,'999999999') and membership_name=? and status='ACTIVE'";
		String membership_id=DbUtil.getVal(clubmembermaster,new String[]{clubid,memberdata.getMembershiptype()});
		

        clubmship.setMemberShipId(membership_id);
        clubmship.setClubId(clubid);
		memberdata.setMemberShipId(membership_id);
		// System.out.println("membershipid"+memberdata.getMemberShipId());
		
		Object obj = getObj(clubmship, memshipvec);
		if (obj != null) {
			clubmship = (ClubMemberShip) obj;
			}
		String termPrice = clubmship.getTermPrice();
		String termperiod = clubmship.getMshipTerm();
		String tempduedate = memberdata.getPayDueDate();
		java.util.Date duepaydate = null;
		// System.out.println("before temp"+termperiod);
		
		if (tempduedate == null || "".equals(tempduedate)) {
			
			String tempstdate = memberdata.getStartDate();
			Calendar cal = Calendar.getInstance();
			java.util.Date tempstdt=null;
			try{
				 tempstdt=(Date)formatter.parse(tempstdate);
			}catch(Exception e){
				
			}
			
			
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

			
			duepaydate = cal.getTime();
			String paydate=formatter.format(duepaydate);
			memberdata.setPayDueDate(paydate);
			// System.out.println("due pay date in if"+duepaydate);
		} 
		else {
			
			try{
				
				 duepaydate=(Date)formatter.parse(tempduedate);
			   // System.out.println("due pay date in else"+duepaydate);
			}catch(Exception e){
				
			}
			
			
			
		}
	   // System.out.println("status before insert1"+statobj.getStatus());
        if(statobj.getStatus()){
        	rcount=insert1(clubid, memberdata);
        }
        if(rcount>0){
        	 SubscriptionDue subdue=new SubscriptionDue();
	  		  subdue.setUserId(memberdata.getUserId());
	  		  subdue.setPurpose("MEMBER_SUBSCRIPTION");
	  		  subdue.setStatus("");
	  		  subdue.setEntryDate(new java.util.Date());
	  		  subdue.setDueDate(duepaydate);
	  		 // System.out.println("duepaydate"+duepaydate);
			  subdue.setAmountDue(termPrice);
             rcount=insertSubscriptionDue(subdue,memberdata);
			 
        }
        if(rcount==0){
			statobj=new StatusObj(false, "rollback", null, rcount);
		}else{
			statobj=new StatusObj(true, "success", memberdata, rcount);
		}
		// System.out.println(" statobj  "+statobj.getStatus());
		

		return statobj;
	}
     
	public static int insertSubscriptionDue(SubscriptionDue subdue,AddCommunityMemberData memberdata){
	    DateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
		int rcount=0;
		StatusObj statobj=null;
	    java.util.Date date=null;
	   
	     String INSERT_SUBSCRIPTION_DUE_USER=
				" insert into subscriptiondue(userid,status,unitid,amountdue,due_date,entry_date,purpose)"
				+ " values(?, ?, ?,CAST(? AS NUMERIC),to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?)";
	   if(subdue.getEntryDate() !=null)
			 date=new java.util.Date(subdue.getEntryDate().getTime());
	   //  System.out.println("date"+date+"  "+subdue.getDueDate());
	    String duedt="";
	    String entrydt="";
	     try{
	    	 
	    	 // System.out.println("duedate"+subdue.getDueDate());
	    	  duedt=formatter.format(subdue.getDueDate()); 
		   //  System.out.println("duedt"+duedt);
		     entrydt=formatter.format(date);
		 
	    }catch(Exception e){
	    	System.out.println(" Exception Occured while formatting date "+e.getMessage());
	    }
	    
	     statobj=DbUtil.executeUpdateQuery(INSERT_SUBSCRIPTION_DUE_USER,new String[]{
	    		 subdue.getUserId(),subdue.getStatus(),memberdata.getUnitId(),
	    		 subdue.getAmountDue(),duedt,entrydt,
	    		 subdue.getPurpose()
	    		
	     } );
	     
	   //  System.out.println("status of subscription due"+ statobj.getStatus());
	     
	     if(statobj.getStatus()==true)rcount=1;
	     
	     

		return rcount;
	}

	public static Object getObj(Object obj, Vector vec){
       // System.out.println("in getobj"+obj. toString());
		Object obj1=null;
		if(vec!= null && obj !=null){
			if(vec.contains(obj)){
				obj1=vec.elementAt(vec.indexOf(obj));
			}
		}

		return obj1;

	}

	public static int insert1(String clubid,AddCommunityMemberData memberdata){
	   // System.out.println("in insert1");
		int rcount=0;
		
		/*String MEMBER_SEQ_ID="select nextval('seq_maillist') as memberid" ;
		String memberid=DbUtil.getVal(MEMBER_SEQ_ID,new String[]{});
		String listid=DbUtil.getVal("select list_id from mail_list where" +
				" list_name like 'Passive Members%' and unit_id=to_number(?,'999999999') and manager_id=to_number(?,'999999999')",
				new String[]{clubid,memberdata.getManagerId()} );*/
		/*String INSERT_MEMBER="insert into member_profile(manager_id,member_id,m_email,userid,created_at)" +
				"    values (to_number(?,'999999999'),to_number(?,'999999999'),?,to_number(?,'999999999'),now())";
		
		StatusObj statobj=DbUtil.executeUpdateQuery(INSERT_MEMBER,new String [] 
		        {memberdata.getManagerId(),memberid,memberdata.getEmail(),
				memberdata.getUserId()});*/
		// System.out.println("status of member_profile in insert1"+statobj.getStatus());
		
		
		/*String INSERT_LIST_MEMBER="insert into mail_list_members(member_id,list_id,status,created_at) " +
				"    values(to_number(?,'999999999'),to_number(?,'999999999'),'available',now())";

		statobj=DbUtil.executeUpdateQuery(INSERT_LIST_MEMBER,new String [] {memberid,listid});*/
		// System.out.println("status of INSERT_LIST_MEMBER in insert1"+statobj.getStatus());
		
		if("13579".equals(memberdata.getUnitId())){
		
			rcount=AddCommunityMemberDB.insertClubMemberEntryNew(memberdata.getUserId(),clubid,memberdata);
		}
		else{
			 rcount=AddCommunityMemberDB.insertClubMemberEntry(memberdata.getUserId(),clubid,memberdata);
			
		}
		
		return rcount;
	}
	
		
		
	public static StatusObj insertAttendeeData(AddCommunityMemberData memberdata) {
		StatusObj statobj = null;
		statobj = insertMemberData(memberdata);
		int rcount=0;
		if(statobj.getStatus()){
			rcount=1;	
			/*if(hm.containsKey("updateAttd")){
					rcount=updateAttendeeAuthId(hm);
				}else{
					rcount=1;
				}*/
			
		 }
		 if(rcount>0){
			 statobj=new StatusObj(true, "success",memberdata);
		 }else{
			
			statobj=new StatusObj(false, "member insertion failed",null);
		}
		return statobj;
	}

	public static StatusObj insertMemberData(AddCommunityMemberData memberdata) {
		StatusObj statobj = null;
		int rcount = 0;
		rcount = insertUserProfileEntry(memberdata);
		if (rcount > 0) {
			rcount = 0;
			rcount = insertAuthenticationEntry(memberdata);//k
			if (rcount > 0) {
				updateUserPreference(memberdata.getUserId(), "pref:myurl",
						memberdata.getLoginId());//k
				/*
				 * if(prefs!=null){
				 * //InsertPreferencesEntry(prefs,(String)authMap
				 * .get("userid"),con); }
				 */
			}
		}

		if (rcount > 0) {
			
			statobj = new StatusObj(true, "success", memberdata);
		} else {

			statobj = new StatusObj(false, "member insertion failed", null);
		}
		// System.out.println("in insert memberdata"+statobj.getStatus());
		
		return statobj;
	}

	public static int insertUserProfileEntry(AddCommunityMemberData memberdata) {

		StatusObj statobj = null;
		int rcount = 0;

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

	//	System.out.println("status of USER_PROFILE_INSERT"	+ statobj.getStatus());

		if (statobj.getStatus() == true)
			rcount = 1;

		String INSERT_INTO_UNIVERSITY = "insert into member_university(university,year,"
				+ " userid) values('','',?) ";
		statobj = DbUtil.executeUpdateQuery(INSERT_INTO_UNIVERSITY,
				new String[] { memberdata.getUserId() });
		//System.out.println("INSERT_INTO_UNIVERSITY" + statobj.getStatus());

		String INSERT_INTO_COMPANY = "insert into member_company(company,from_dt,to_dt,"
				+ " userid) values('','','',?) ";
		statobj = DbUtil.executeUpdateQuery(INSERT_INTO_COMPANY,
				new String[] { memberdata.getUserId() });
	//	System.out.println("INSERT_INTO_COMPANY" + statobj.getStatus());

		ArrayList l1 = new ArrayList();
		MailListMember ml = new MailListMember();
		ml.setEmail(memberdata.getEmail());
		ml.setManagerId(memberdata.getManagerId());
		ml.setUserId(memberdata.getUserId());
		l1.add(ml);
		Config config = new Config();
		config = AddCommunityMemberDB.getConfig(memberdata.getUnitId(),
				"UNIT_BASE_CONFIG");
		if (config != null) {
			String unitlistid = config.getKeyValue("unit.maillist.id", "none");// 0
		//	System.out.println("insert user profile  before addmembers"	+ unitlistid);
			if (!(unitlistid.equals("none")))
				addMembers(l1, unitlistid);
		}

		return rcount;
	}

	public static int insertAuthenticationEntry(
			AddCommunityMemberData memberdata) {
		int rcount = 0;
		StatusObj statobj = null;
		// System.out.println("in passivedb auth entry userid"	+ memberdata.getUserId());

		String pwd = memberdata.getPassword();
		String userencpass = "";
		try {
			userencpass = (new StringEncrypter(
					StringEncrypter.DES_ENCRYPTION_SCHEME)).encrypt(pwd);
		} catch (Exception e) {

		}

		String AUTHENTICATION_INSERT = " insert into authentication(auth_id, "
				+ " org_id,unit_id,role_id,user_id, "
				+ " login_name,password,acct_status, "
				+ " created_by,created_at,description,membership_status) "
				+ " values (to_number(?,'999999999'),CAST('3467' as integer),to_number(?,'999999999'),"
				+ " CAST('-100' as integer),?,?,?,to_number(?,'999999999'), "
				+ " 'com.eventbee.useraccount.AccountDB', now(),'',?) ";

		statobj = DbUtil.executeUpdateQuery(AUTHENTICATION_INSERT,
				new String[] {

				memberdata.getUserId(), memberdata.getUnitId(),
						memberdata.getUserId(), memberdata.getLoginId(),
						userencpass, memberdata.getStatus(),
						memberdata.getMembershipStatus()

				});

		if(statobj.getStatus()) rcount=1;
	//	System.out.println("status of authentication" + statobj.getStatus());
		return rcount;
	}

	public static boolean updateUserPreference(String userid, String pref_name,
			String pref_value) {
		int i = 0;
		StatusObj statobj = null;
		String MEM_PREFERENCE_DELETE = "delete from member_preference where pref_name like ? and user_id=?";
		statobj = DbUtil.executeUpdateQuery(MEM_PREFERENCE_DELETE,
				new String[] { pref_name,userid });

		String MEM_PREFERENCE_INSERT = " insert into member_preference( "
				+ " user_id, pref_name, pref_value) values (?,?,?) ";
		statobj = DbUtil.executeUpdateQuery(MEM_PREFERENCE_INSERT,
				new String[] { userid, pref_name, pref_value });
	 // System.out.println(" status of Memberpreference insert "+ statobj.getStatus());

	   if(statobj.getStatus()) i=1;
	   
		if (i > 0) {
			return true;

		} else {
			return false;

		}

	}
	
	 public static void addMembers(ArrayList members,String listId){
		   
		   StatusObj st=null;
		   /*String INSERT_MEMBER="insert into member_profile(manager_id,member_id,m_name,m_firstname,m_lastname,m_email," +
		   		" m_phone,m_company ,m_jobtype ,m_place ,m_age ,m_gender ,m_email_type,userid) 	" +
		   		" values (to_number(?,'999999999'),to_number(?,'999999999'),?,?,?,?,?,'','','','',?,'',to_number(?,'999999999'))";
*/		   
		   if(!members.isEmpty()){
			   String createdBy="Auto Subscription";
			   
			   Iterator iter=members.iterator();
				while(iter.hasNext()){
					MailListMember member=(MailListMember)iter.next();
					member.setListId(listId);
					if("0".equals(member.getManagerId()) ){
						createdBy= (member.getUserId()==null)?createdBy:createdBy+"(Member)" ;
					}else
					if("1".equals(member.getManagerId()) ){
						createdBy= "Self";
					}else{
						createdBy= "Manager";
					}
					member.setCreatedBy(createdBy);
		            /*StatusObj statobj=isMemberExisting(member);
		         	if(statobj.getStatus()){
		                member.setMemberId(statobj.getData().toString());
						//addMemberLink(member);
					}else{
						
						String memberid=getNewMemberId();
						member.setMemberId(memberid);
					//	System.out.println("in else memberid"+memberid);
						addMemberLink(member);
						st=DbUtil.executeUpdateQuery(INSERT_MEMBER,new String[]{
								member.getManagerId(),member.getMemberId(),member.getName(),
								member.getFirstname(),member.getLastname(),member.getEmail(),
								member.getPhone(),member.getGender(),member.getUserId()
						});
						
					//	System.out.println("status of Insert member(member profile)"+st.getStatus());
			          }*///end of else
			     }//end of while
		   }//end of if 
		   
	   }
	 public static String getNewMemberId() {
			String MEMBER_SEQ_ID = "select  nextval('seq_maillist') as memberid";
			String new_memberid = DbUtil.getVal(MEMBER_SEQ_ID, null);

			return new_memberid;
		}

		public static void addMemberLink(MailListMember member) {

			/*StatusObj statobj = null;
		//	System.out.println("list,memberid,created by  "+member.getListId()+member.getCreatedBy()+member.getMemberId());
			
			String INSERT_LIST_MEMBER = " insert into mail_list_members(member_id,list_id,"
					+ " status,created_at,created_by) values(to_number(?,'999999999'),"
					+ " to_number(?,'999999999'),'available',now(),?)";

			statobj = DbUtil.executeUpdateQuery(INSERT_LIST_MEMBER,
					new String[] { member.getMemberId(), member.getListId(),
							member.getCreatedBy() });*/
			
		//	System.out.println("status of Mail_list members" + statobj.getStatus());

		}
		   
	  public static StatusObj isMemberExisting(MailListMember member){
			   
		  StatusObj statobj=new StatusObj(false,null,"0");
	      /*String MEMBER_EXISTS_INLIST="select mp.member_id from member_profile mp," +
	      		            " mail_list_members mlm where  m_email=? and "
			                +" mp.member_id=mlm.member_id and list_id=to_number(?,'999999999') ";

	     String exmemid=DbUtil.getVal(MEMBER_EXISTS_INLIST,new String[]{member.getEmail(),
	    		                         member.getListId()});
	   //  System.out.println("in is member existing"+exmemid);
				if(exmemid !=null){
					statobj.setStatus(true);
					statobj.setData(exmemid);
				}*/

				return statobj;
			}

	  		public static int updateAttendeeAuthId(String attendeekey){
	  			int rcount=0;
	  			StatusObj statobj=null;
	  		    String ATTENDEE_AUTHID_UPDATE="update eventattendee set authid=to_number(?,'999999999')" +
	  		  		             " where attendeekey=?";
	  		    statobj=DbUtil.executeUpdateQuery(ATTENDEE_AUTHID_UPDATE,new String[]{attendeekey});
	  		    if(statobj.getStatus()) rcount=1;
	  		    
	  			return rcount;
	  		}
	  		
	  		public static Vector getClubMembershipInfo(String groupId){
	  			
	  			StatusObj statobj=null;
	  			DBManager db=new DBManager();
	  			Vector memshipvec=new Vector();
	  			String CLUB_UNIT_MEMSHIP_QUERY=" select b.clubname,a.membership_id,a.membership_name,a.description,a.createtype,a.status,"
	  				+"a.currency_type,a.price,a.term_fee,a.mship_term from club_membership_master as a,"
	  				+"clubinfo as b where a.clubid=b.clubid and  a.clubid=to_number(?,'999999999') and b.unitid=CAST('13579' as integer) and a.status='ACTIVE'";
	  	        
	  			statobj=db.executeSelectQuery(CLUB_UNIT_MEMSHIP_QUERY,new String[]{groupId});
	  		//	System.out.println("CLUB_UNIT_MEMSHIP_QUERY"+statobj.getStatus());
	  			for(int i=0;i<statobj.getCount();i++){
	  				
	  				clubmship.setMemberShipId(db.getValue(i, "membership_id", ""));
	  				clubmship.setMemberShipName(db.getValue(i,"membership_name",""));
	  				clubmship.setDescription(db.getValue(i,"description","")); 
	  				clubmship.setCurrencyType(db.getValue(i,"currency_type",""));
	  				clubmship.setPrice(db.getValue(i,"price",""));
	  				clubmship.setTermPrice(db.getValue(i,"term_fee",""));
	  				clubmship.setMshipTerm(db.getValue(i, "mship_term",""));
	  				memshipvec.add(clubmship);
	  			}
	  			return  memshipvec;	
	  			
	  		}

}
