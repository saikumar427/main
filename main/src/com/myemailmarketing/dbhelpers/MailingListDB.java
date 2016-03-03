package com.myemailmarketing.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;
import com.myemailmarketing.beans.CampaignDetails;
import com.myemailmarketing.beans.MailListDetails;
import com.myemailmarketing.beans.MemberData;

public class MailingListDB {
	public static boolean saveMailList(String userId,MailListDetails mailList){
	String INSERT_LIST="insert into mail_list(manager_id,list_id,list_name,list_desc,unit_id,unsubmess," +
			"created_at) values(to_number(?,'9999999999'),to_number(?,'9999999999'),?,?,to_number(?,'9999999999')," +
			"?,now())";
	String UPDATE_LIST="update mail_list set list_name=?,list_desc=?,unsubmess=? where manager_id=to_number(?,'9999999999')" +
			"and list_id=to_number(?,'9999999999')";
	String listId=mailList.getListId();
	System.out.println("listId value"+listId);
	String LIST_SEQ_ID=DbUtil.getVal("select  nextval('seq_list') as seqlist",null);
	String listName=mailList.getListName();
	String desc=mailList.getDescritption();
	String unsubmsg=mailList.getUnsubmsg();
	if("".equals(listId)){
	DbUtil.executeUpdateQuery(INSERT_LIST, new String[]{userId,LIST_SEQ_ID,listName,desc,"13579",unsubmsg});
	}
	else{
		DbUtil.executeUpdateQuery(UPDATE_LIST, new String[]{listName,desc,unsubmsg,userId,listId});
	}
	return true;
	}
	public static ArrayList<MailListDetails> getListdetails(String userId){
		String LISTOFUSER="select ml.list_id,ml.list_name,ml.unsubmess,ml.list_desc," +
				"count(mlm.list_id) as total from mail_list ml left join mail_list_members mlm on " +
				"(ml.list_id=mlm.list_id) where  ml.manager_id=to_number(?,'9999999999') group by" +
				" ml.manager_id,ml.list_id,ml.list_name,ml.unsubmess,ml.list_desc order by list_id desc"; 
		DBManager dbmanager=new DBManager();
		ArrayList<MailListDetails> mList=new ArrayList<MailListDetails>();
		StatusObj statobj1=dbmanager.executeSelectQuery(LISTOFUSER,new String[]{userId});
	 	if(statobj1.getStatus()){     
		for(int k=0;k<statobj1.getCount();k++)
		{
			MailListDetails mListObj=new MailListDetails();
			String listId=dbmanager.getValue(k,"list_id","");
			String listName=dbmanager.getValue(k,"list_name","");
			String unsubmsg=dbmanager.getValue(k,"unsubmess","");
			String listDesc=dbmanager.getValue(k,"list_desc","");
			String count=dbmanager.getValue(k,"total","");
			//String status=dbmanager.getValue(k,"status","");
			mListObj.setDescritption(listDesc);
			mListObj.setListId(listId);
			mListObj.setListName(listName);
			mListObj.setUnsubmsg(unsubmsg);
			mListObj.setMemberCount(count);
			//mListObj.setStatus(status);
			mList.add(mListObj);
		}//End of for
		}//End of if
		return mList;
	}
	public static MailListDetails getListInfo(String userId,String listId){
		MailListDetails mListObj=new MailListDetails();
		String qry="select * from mail_list where manager_id=to_number(?,'9999999999') " +
				"and list_id=to_number(?,'9999999999')";
		DBManager dbmanager=new DBManager();
		StatusObj statobj1=dbmanager.executeSelectQuery(qry,new String[]{userId,listId});
	 	if(statobj1.getStatus()){     
	 		String listName=dbmanager.getValue(0,"list_name","");
			String unsubmsg=dbmanager.getValue(0,"unsubmess","");
			String listDesc=dbmanager.getValue(0,"list_desc","");
			mListObj.setDescritption(listDesc);
			mListObj.setListId(listId);
			mListObj.setListName(listName);
			mListObj.setUnsubmsg(unsubmsg);
	 	}
		return mListObj;
		
	}
	public static boolean saveManualMemberData(String userId,String listId,MemberData memberData){
		String MEMBER_SEQ_ID="select nextval('seq_maillist') as memberid" ;
		String mid=DbUtil.getVal(MEMBER_SEQ_ID,new String[]{});
		String fname=memberData.getFirstName();
		String lname=memberData.getLastName();
		String email=memberData.getEmail();
		String mpref=memberData.getEmailPref();
		String place=memberData.getPlace();
		String phone=memberData.getPhone();
		String gender=memberData.getGender();
		String age=memberData.getAge();
		String company=memberData.getCompany();
		String title=memberData.getTitle();
		String memberId=memberData.getMemberId();
		String status=memberData.getStatus();
		String INSERT_MEMBER="insert into member_profile(manager_id,member_id,m_firstname,m_lastname,m_email," +
				"m_phone,m_company  ,m_jobtype  ,m_place"
		+"  ,m_age  ,m_gender  ,m_email_type,created_at) 	values (to_number(?,'999999999'),to_number(?,'999999999')," +
				"?,?,?,?,?,?,?,?,?,?,now())";
		if("".equals(memberId)){
			DbUtil.executeUpdateQuery(INSERT_MEMBER,new String [] {userId,mid,fname,lname,email,phone,company,
					title,place,age,gender,mpref});
		String INSERT_LIST_MEMBER="insert into mail_list_members(member_id,list_id,status,created_at,created_by)" +
				" values(to_number(?,'999999999'),to_number(?,'999999999'),'available',now(),'Manager')";
		DbUtil.executeUpdateQuery(INSERT_LIST_MEMBER,new String [] {mid,listId});
		}
		else{
			//Update method will follow here.
			String UPDATE_QRY="update member_profile set m_firstname=?,m_lastname=?,m_email=?,m_age=?," +
					"m_phone=?,m_company=?,m_jobtype=?,m_place=?,m_gender=?,m_email_type=?," +
					"updated_at='now()' where " +
					"member_id=to_number(?,'999999999') ";
			String qry="update mail_list_members set status=? where  member_id=to_number(?,'999999999') and " +
					"list_id=to_number(?,'999999999')";
			DbUtil.executeUpdateQuery(UPDATE_QRY,new String [] {fname,lname,email,age,phone,company,
					title,place,gender,mpref,memberId});
			DbUtil.executeUpdateQuery(qry,new String[]{status,memberId,listId});
			if(memberData.getMem_userId() != null && !memberData.getMem_userId().equals("")){
				String usrprofQry = "update user_profile set email=?, first_name=?,last_name=?, updated_at='now()' where user_id=?";
				DbUtil.executeUpdateQuery(usrprofQry,new String[]{email,fname,lname,memberData.getMem_userId()});
			}
			
		}
		
		return true;
	}
	public static MemberData  getMemberData(String userId,String memberId,String listId){
		String selectQry="select m_firstname,m_lastname,m_email,m_company,m_jobtype,m_place," +
				"m_age,m_gender,m_email_type,m_phone,userid from member_profile where " +
				"member_id=to_number(?,'999999999')";
		MemberData mData=new MemberData();
		DBManager dbmanager=new DBManager();
		StatusObj statobj1=dbmanager.executeSelectQuery(selectQry,new String[]{memberId});
	 	if(statobj1.getStatus()){    
	 		String fname=dbmanager.getValue(0,"m_firstname","");
			String lname=dbmanager.getValue(0,"m_lastname","");
			String email=dbmanager.getValue(0,"m_email","");
			String mpref=dbmanager.getValue(0,"m_email_type","");
			String place=dbmanager.getValue(0,"m_place","");
			String phone=dbmanager.getValue(0,"m_phone","");
			String gender=dbmanager.getValue(0,"m_gender","");
			String age=dbmanager.getValue(0,"m_age","");
			String company=dbmanager.getValue(0,"m_company","");
			String title=dbmanager.getValue(0,"m_jobtype","");
			mData.setMem_userId(dbmanager.getValue(0,"userid",""));
			mData.setAge(age);
			mData.setCompany(company);
			mData.setEmail(email);
			mData.setEmailPref(mpref);
			mData.setFirstName(fname);
			mData.setGender(gender);
			mData.setLastName(lname);
			mData.setMemberId(memberId);
			mData.setPhone(phone);
			mData.setPlace(place);
			mData.setTitle(title);
			String status=DbUtil.getVal("select status from mail_list_members where " +
					"member_id=to_number(?,'999999999')",new String[]{memberId});
			mData.setStatus(status);
	 	}// End of if block.
		return mData;
	}
	public static ArrayList<MemberData> getMembersbyStatus(String query,String userId,String listId){
		DBManager dbmanager=new DBManager();
		ArrayList<MemberData> mDataList=new ArrayList<MemberData>();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{listId,listId});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
				MemberData mData=new MemberData();
				String fname=dbmanager.getValue(k,"m_firstname","");
				String lname=dbmanager.getValue(k,"m_lastname","");
				String email=dbmanager.getValue(k,"m_email","");
				String mpref=dbmanager.getValue(k,"m_email_type","");
				String place=dbmanager.getValue(k,"m_place","");
				String phone=dbmanager.getValue(k,"m_phone","");
				String gender=dbmanager.getValue(k,"m_gender","");
				String age=dbmanager.getValue(k,"m_age","");
				String company=dbmanager.getValue(k,"m_company","");
				String title=dbmanager.getValue(k,"m_jobtype","");
				String memberId=dbmanager.getValue(k,"member_id","");
				mData.setAge(age);
				mData.setCompany(company);
				mData.setEmail(email);
				mData.setEmailPref(mpref);
				mData.setFirstName(fname);
				mData.setGender(gender);
				mData.setLastName(lname);
				mData.setMemberId(memberId);
				mData.setPhone(phone);
				mData.setPlace(place);
				mData.setTitle(title);
				mDataList.add(mData);
			}
		}
		return mDataList;
	}
	public static ArrayList<MemberData> getAllMembers(String userId,String listId){
		String query="select  a.userid , manager_id,a.member_id,m_name,m_firstname,m_lastname,m_email,m_company," +
				"m_phone,m_age,m_jobtype,m_place,m_gender,m_email_type,b.status,b.created_at,b.created_by " +
				" from member_profile a,mail_list_members b where a.member_id=b.member_id and " +
				"b.list_id=to_number(?,'9999999999') and a.userid is  null union select  a.userid , " +
				" manager_id,a.member_id,c.first_name||' '||c.last_name as m_name,c.first_name,c.last_name," +
				"c.email as m_email,m_company,m_phone,m_age,m_jobtype,m_place,c.gender, m_email_type,b.status," +
				"b.created_at,b.created_by from member_profile a,mail_list_members b, user_profile c where " +
				" a.member_id=b.member_id and b.list_id=to_number(?,'9999999999') and " +
				"a.userid =to_number(c.user_id,'9999999999') and a.userid is not null;";
		
		return getMembersbyStatus(query,userId,listId);
	}
	public static ArrayList<MemberData> getActiveMembers(String userId,String listId){
		String query="select  a.userid , manager_id,a.member_id,m_name,m_firstname,m_lastname,m_email," +
		"m_company,m_phone,m_age,m_jobtype,m_place,m_gender  ,m_email_type,b.status,b.created_at," +
		"b.created_by  from member_profile a,mail_list_members b where a.member_id=b.member_id and " +
		"b.list_id=to_number(?,'9999999999') and upper(b.status)='AVAILABLE' and a.userid is  null union " +
		"select  a.userid ,  manager_id,a.member_id,c.first_name||' '||c.last_name as m_name," +
		" c.first_name,c.last_name,c.email as m_email, m_company,m_phone,m_age,m_jobtype,m_place, " +
		"c.gender, m_email_type,b.status,b.created_at,b.created_by from member_profile a," +
		"mail_list_members b, user_profile c where  a.member_id=b.member_id and " +
		"b.list_id=to_number(?,'9999999999') and upper(b.status)='AVAILABLE' and " +
		"a.userid =to_number(c.user_id,'9999999999') and a.userid is not null order by created_at desc";
		return getMembersbyStatus(query,userId,listId);
	}
	public static ArrayList<MemberData> getUnsubMembers(String userId,String listId){
		String query="select  a.userid , manager_id,a.member_id,m_name,m_firstname,m_lastname,m_email," +
		"m_company,m_phone,m_age,m_jobtype,m_place,m_gender  ,m_email_type,b.status,b.created_at," +
		"b.created_by  from member_profile a,mail_list_members b where a.member_id=b.member_id and " +
		"b.list_id=to_number(?,'9999999999') and upper(b.status)='UNSUBSCRIBED' and a.userid is  null union " +
		"select  a.userid ,  manager_id,a.member_id,c.first_name||' '||c.last_name as m_name," +
		" c.first_name,c.last_name,c.email as m_email, m_company,m_phone,m_age,m_jobtype,m_place, " +
		"c.gender, m_email_type,b.status,b.created_at,b.created_by from member_profile a," +
		"mail_list_members b, user_profile c where  a.member_id=b.member_id and " +
		"b.list_id=to_number(?,'9999999999') and upper(b.status)='UNSUBSCRIBED' and " +
		"a.userid =to_number(c.user_id,'9999999999') and a.userid is not null";
		return getMembersbyStatus(query,userId,listId);
	}
	public static ArrayList<MemberData> getBouncedMembers(String userId,String listId){
		String query="select  a.userid , manager_id,a.member_id,m_name,m_firstname,m_lastname,m_email," +
				"m_company,m_phone,m_age,m_jobtype,m_place,m_gender  ,m_email_type,b.status,b.created_at," +
				"b.created_by  from member_profile a,mail_list_members b where a.member_id=b.member_id and " +
				"b.list_id=to_number(?,'9999999999') and upper(b.status)='BOUNCED' and a.userid is  null union " +
				"select  a.userid ,  manager_id,a.member_id,c.first_name||' '||c.last_name as m_name," +
				" c.first_name,c.last_name,c.email as m_email, m_company,m_phone,m_age,m_jobtype,m_place, " +
				"c.gender, m_email_type,b.status,b.created_at,b.created_by from member_profile a," +
				"mail_list_members b, user_profile c where  a.member_id=b.member_id and " +
				"b.list_id=to_number(?,'9999999999') and upper(b.status)='BOUNCED' and " +
				"a.userid =to_number(c.user_id,'9999999999') and a.userid is not null";
		
		
		return getMembersbyStatus(query,userId,listId);
	}
	public static ArrayList<MemberData> getInactiveMembers(String userId,String listId){
		String query="select  a.userid , manager_id,a.member_id,m_name,m_firstname,m_lastname,m_email," +
		"m_company,m_phone,m_age,m_jobtype,m_place,m_gender  ,m_email_type,b.status,b.created_at," +
		"b.created_by  from member_profile a,mail_list_members b where a.member_id=b.member_id and " +
		"b.list_id=to_number(?,'9999999999') and upper(b.status)='INVALID' and a.userid is  null union " +
		"select  a.userid ,  manager_id,a.member_id,c.first_name||' '||c.last_name as m_name," +
		" c.first_name,c.last_name,c.email as m_email, m_company,m_phone,m_age,m_jobtype,m_place, " +
		"c.gender, m_email_type,b.status,b.created_at,b.created_by from member_profile a," +
		"mail_list_members b, user_profile c where  a.member_id=b.member_id and " +
		"b.list_id=to_number(?,'9999999999') and upper(b.status)='INVALID' and " +
		"a.userid =to_number(c.user_id,'9999999999') and a.userid is not null";
		
		return getMembersbyStatus(query,userId,listId);
	}
	public static ArrayList<MailListDetails> getManagerMailLists(String userId,String listId){
		String query="select list_id,list_name from mail_list where manager_id=to_number(?,'9999999999')" +
				" and list_id!=to_number(?,'9999999999')";
		DBManager dbmanager=new DBManager();
		ArrayList<MailListDetails> mailList=new ArrayList<MailListDetails>();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{userId,listId});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
				MailListDetails mListData=new MailListDetails();
				String lId=dbmanager.getValue(k,"list_id","");
				String lname=dbmanager.getValue(k,"list_name","");
				mListData.setListId(lId);
				mListData.setListName(lname);
				mailList.add(mListData);
			}
		}
		return mailList;
	}
	public static void saveMergeListData(String userId,String listId,ArrayList liststobeMerged){
		String query="insert into mail_list_members(member_id,list_id,status,created_by,created_at)" +
				" (select mlm.member_id,list_id,'available','mergelist',now() from mail_list_members mlm ," +
				"member_profile mp where list_id=to_number(?,'999999999') and mlm.member_id=mp.member_id " +
				"and m_email not in ( select m_email from member_profile mp1,mail_list_members mlm1  where " +
				" list_id=to_number(?,'999999999') and mlm1.member_id=mp1.member_id)) ";
		//First one is from which the list has to be copied
		for (int i=0;i<liststobeMerged.size();i++){
			String fromListId=(String) liststobeMerged.get(i);
			DbUtil.executeUpdateQuery(query, new String []{fromListId,listId});
			
		}
	}
	public static void saveFileData(String userId,String listId,ArrayList<MemberData> fileMemberData){
		for (MemberData memberData : fileMemberData) {
			String MEMBER_SEQ_ID="select nextval('seq_maillist') as memberid" ;
			String mid=DbUtil.getVal(MEMBER_SEQ_ID,new String[]{});
			String fname=memberData.getFirstName();
			String lname=memberData.getLastName();
			String email=memberData.getEmail();
			String mpref=memberData.getEmailPref();
			String place=memberData.getPlace();
			String phone=memberData.getPhone();
			String gender=memberData.getGender();
			String age=memberData.getAge();
			String company=memberData.getCompany();
			String title=memberData.getTitle();
			String INSERT_MEMBER="insert into member_profile(manager_id,member_id,m_firstname,m_lastname,m_email," +
			"m_phone,m_company  ,m_jobtype  ,m_place"
	+"  ,m_age  ,m_gender  ,m_email_type,created_at) values (to_number(?,'999999999'),to_number(?,'999999999')," +
			"?,?,?,?,?,?,?,?,?,?,now())";
			DbUtil.executeUpdateQuery(INSERT_MEMBER,new String [] {userId,mid,fname,lname,email,phone,company,
					title,place,age,gender,mpref});
			String INSERT_LIST_MEMBER="insert into mail_list_members(member_id,list_id,status,created_at,created_by)" +
				" values(to_number(?,'999999999'),to_number(?,'999999999'),'available',now(),'Manager')";
			DbUtil.executeUpdateQuery(INSERT_LIST_MEMBER,new String [] {mid,listId});
		}
	}
	public static HashMap<String,String> getSummaryCount(String userId,String listId){
		HashMap<String,String> summaryCount=new HashMap<String,String>();
		summaryCount.put("ACTIVE_COUNT", "0");
		summaryCount.put("INACTIVE_COUNT", "0");
		summaryCount.put("UNSUB_COUNT", "0");
		summaryCount.put("BOUNCED_COUNT", "0");
		summaryCount.put("ALL_COUNT", "0");
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String qry="select status,count(*) as statuscount from mail_list_members where list_id=to_number(?,'999999999') group by status";
		try{
			statobj=dbmanager.executeSelectQuery(qry,new String []{listId});
			int count1=statobj.getCount();
			if(statobj.getStatus()&&count1>0){   
				for(int k=0;k<count1;k++){
					if("available".equalsIgnoreCase(dbmanager.getValue(k,"status","")))
						summaryCount.put("ACTIVE_COUNT", dbmanager.getValue(k,"statuscount",""));
					if("invalid".equalsIgnoreCase(dbmanager.getValue(k,"status","")))
						summaryCount.put("INACTIVE_COUNT", dbmanager.getValue(k,"statuscount",""));
					if("bounced".equalsIgnoreCase(dbmanager.getValue(k,"status","")))
						summaryCount.put("BOUNCED_COUNT", dbmanager.getValue(k,"statuscount",""));
					if("unsubscribed".equalsIgnoreCase(dbmanager.getValue(k,"status","")))
						summaryCount.put("UNSUB_COUNT", dbmanager.getValue(k,"statuscount",""));
				}
			}
			int active=Integer.parseInt(summaryCount.get("ACTIVE_COUNT").toString());
			int inactive=Integer.parseInt(summaryCount.get("INACTIVE_COUNT").toString());
			int bounced=Integer.parseInt(summaryCount.get("BOUNCED_COUNT").toString());
			int unsub=Integer.parseInt(summaryCount.get("UNSUB_COUNT").toString());
			summaryCount.put("ALL_COUNT", ""+(active+inactive+bounced+unsub));
			}//End of try block
			catch(Exception e){
				System.out.println("There is an Exception while executing membercounts  query" +
						"for the mgr "+userId +" "+e.getMessage());
			}
		return summaryCount;
	}
	public static ArrayList<CampaignDetails> getCamplist(String userId){
		ArrayList<CampaignDetails> camplist =new ArrayList<CampaignDetails>();
		try{
		String CAMPAIGN_OF_MEMBER="select camp_id,camp_name,camp_title from campaign_templates where " +
		 		"manager_id =to_number(?,'9999999999') order by created_at desc";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery( CAMPAIGN_OF_MEMBER,new String[]{userId});
		if(statobj !=null && statobj.getStatus()){
		int recordcounttodata=statobj.getCount();
		for(int i=0;i<recordcounttodata;i++){
				CampaignDetails cinfo =new CampaignDetails();
				cinfo.setCampId(dbmanager.getValue(i,"camp_id",""));
				cinfo.setCampName(dbmanager.getValue(i,"camp_name",""));
				cinfo.setCampTitle(dbmanager.getValue(i,"camp_title",""));
				camplist.add(cinfo);
		}//end for
		}//end if block.
	  }catch (Exception e) 
	  {
		  System.out.println("Exception Occured"+e);
		  }
	return camplist;
	}//End of getCamplist(String userId)
	public static void deleteMailList(String userid,String listId){
		DbUtil.executeUpdateQuery("delete from email_recepients where recepient_id=to_number(?,'99999999') and camp_id in(select camp_id from email_campaign where camp_status='P')", new String[]{listId});
		DbUtil.executeUpdateQuery("delete from member_profile  where member_id in(select member_id from mail_list_members where list_id=to_number(?,'99999999'))", new String[]{listId});
		DbUtil.executeUpdateQuery("delete from mail_list_members where list_id=to_number(?,'99999999')", new String[]{listId});
		DbUtil.executeUpdateQuery("delete from mail_list where manager_id=to_number(?,'99999999') and list_id=to_number(?,'99999999')", new String[]{userid,listId});
	}
	public static void deleteMember(String memberId,String listId){
		DbUtil.executeUpdateQuery("delete from member_profile  where member_id=to_number(?,'99999999') and member_id in(select member_id from mail_list_members where list_id=to_number(?,'99999999'))", new String[]{memberId,listId});
		DbUtil.executeUpdateQuery("delete from mail_list_members where member_id=to_number(?,'99999999') and list_id=to_number(?,'99999999')", new String[]{memberId,listId});	
	}
	/**
	 * Checks duplicate email IDs and inserts non-duplicates in to DB. 
	 * @param userId
	 * @param listId
	 * @param members
	 * @param replaceDuplicates
	 * @return integer which indicates no of duplicates found
	 */
	public static int bulkUploadinsert(String userId,String listId,ArrayList<MemberData> members,boolean replaceDuplicates){
		ArrayList<String> currentMembers=getCurrentMembers(listId);
		ArrayList<MemberData> duplicates=new ArrayList<MemberData>();
		ArrayList<MemberData> insertMembers=new ArrayList<MemberData>();
		int count=0;
		for(int i=0;i<members.size();i++){
			String memail=members.get(i).getEmail();
			if(currentMembers.contains(memail)){ //adding duplicate emails to separate list
				duplicates.add(members.get(i));
				count++;
			}else
				insertMembers.add(members.get(i));
		}
		//Here we are inserting non-duplicates first
		for(int j=0;j<insertMembers.size();j++){
			saveManualMemberData(userId, listId, insertMembers.get(j));
		}
		//if user checks the replace duplicates option, the first name and last name of corresponding email will be updated here. 
		if(replaceDuplicates && duplicates.size()>0)
		{
			for(int k=0;k<duplicates.size();k++){
				MemberData memberData=duplicates.get(k);
			DbUtil.executeUpdateQuery("update member_profile set m_firstname=?,m_lastname=? where member_id in (select member_id from mail_list_members where list_id=to_number(?,'99999999')) and lower(m_email)=?", new String[]{memberData.getFirstName(),memberData.getLastName(),listId,memberData.getEmail().toLowerCase()});
			}
		}
		return count;//finally returning duplicates count
	}
	/**
	 * Populates all email IDs for a list 
	 * @param listId
	 * @return Array List of Strings  
	 */
	public static ArrayList<String> getCurrentMembers(String listId){
		ArrayList<String> currentMembers=new ArrayList<String>();
		String query="select lower(m_email) as email from member_profile where member_id in(select member_id from mail_list_members where list_id=to_number(?,'99999999999'))";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query, new String[]{listId});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
				currentMembers.add(db.getValue(i, "email", ""));
			}
		}
		return currentMembers;
	}
}
