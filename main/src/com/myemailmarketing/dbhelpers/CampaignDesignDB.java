package com.myemailmarketing.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.StatusObj;
import com.myemailmarketing.beans.CampaignDetails;
import com.myemailmarketing.beans.EmailScheduleData;
import com.myemailmarketing.beans.MailListDetails;

public class CampaignDesignDB {

	public ArrayList<CampaignDetails> getCampaignList(String mgrId){
		 String CAMPAIGN_OF_MEMBER="select camp_id,camp_name,camp_title from " +
		 		"campaign_templates where manager_id =to_number(?,'99999999999') " +
		 		"order by created_at desc";
		 ArrayList<CampaignDetails> camplist =new ArrayList<CampaignDetails>();
		 DBManager dbmanager=new DBManager();
	 	 StatusObj statobj=dbmanager.executeSelectQuery( CAMPAIGN_OF_MEMBER,new String[]{mgrId});
	 	 if(statobj !=null && statobj.getStatus()){
	 			int recordcounttodata=statobj.getCount();
	 			for(int i=0;i<recordcounttodata;i++){
	 				CampaignDetails cinfo=new CampaignDetails();
	 				cinfo.setCampId(dbmanager.getValue(i,"camp_id",""));
	 				cinfo.setCampName(dbmanager.getValue(i,"camp_name",""));
	 				cinfo.setCampTitle(dbmanager.getValue(i,"camp_title",""));
	 				camplist.add(cinfo);
					}//end for
	 	 	}// End of if().
	 	return camplist;
	}
	public static CampaignDetails getCampaignData(String mgrId,String campId){
		CampaignDetails campData=new CampaignDetails();
		String campDataSelectQuery="select camp_name,camp_content,camp_text,camp_html," +
				"desctype,background,border,border_width from campaign_templates where " +
				"manager_id=to_number(?,'9999999999') and camp_id=to_number(?,'9999999999')";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		if(mgrId==null)mgrId="";
		if(campId==null)campId="";
		if(!"".equals(mgrId) && !"".equals(campId))
		statobj=dbmanager.executeSelectQuery( campDataSelectQuery,new String[]{mgrId,campId});
		if(statobj !=null && statobj.getStatus()&& statobj.getCount()>0 ){
			
			String background=dbmanager.getValue(0,"background","");
			
			if(background.startsWith("url")){
				campData.setBackgroundType("IMAGE");
				String temp=background.substring(background.indexOf("(",0)+1,background.lastIndexOf(")"));
				campData.setBackgroungImage(temp);
				campData.setBackground(temp);
			}
			else{
				campData.setBackgroundType("COLOR");
				campData.setBackground(dbmanager.getValue(0,"background",""));
				campData.setBackgroundColor(dbmanager.getValue(0,"background",""));
			}
			campData.setBordercolor(dbmanager.getValue(0,"border",""));
			campData.setBorderwidth(dbmanager.getValue(0,"border_width",""));
			campData.setDescription(dbmanager.getValue(0,"camp_content",""));
			campData.setTextcontent(dbmanager.getValue(0,"camp_html",""));
			campData.setCampDescType(dbmanager.getValue(0,"desctype",""));
			campData.setCampName(dbmanager.getValue(0,"camp_name",""));
			campData.setCampDescType(dbmanager.getValue(0,"desctype",""));
			campData.setCampContent(dbmanager.getValue(0,"camp_html",""));
		}
			
		return campData;
	}
	public static String saveCampaignData(String mgrId,CampaignDetails campData,String campId,String fckdesc){
		String INSERT_QUERY="insert into campaign_templates(camp_id,manager_id,camp_name,camp_title," +
				"camp_content,camp_img1,camp_img2,camp_template,camp_text,camp_html, created_by,created_at," +
				"updated_by, updated_at,unitid,role,desctype,background,border,border_width) "
			+" values (to_number(?,'9999999999'),to_number(?,'9999999999'),?,?,?,?,?,?,?,?,'campaddt3',now(),'campaddt3',now(),CAST('13579' as numeric),?,?,?,?,?)";
		String UPDATE_QUERY="update campaign_templates set camp_name=?,camp_content=?,"
			+" camp_html=? ,background=?,border=?,border_width=?,desctype=?,updated_at=now() where camp_id=to_number(?,'9999999999')";
		String campid=DbUtil.getVal("select nextval('seq_campdesignid')",null);
		String campName=campData.getCampName();
		String content=campData.getDescription();
		String campHTML=campData.getTextcontent();
		String campTemplate="t3";		
		String contentType=campData.getCampDescType();
		if ("wysiwyg".equals(contentType))
			campHTML=content;
		else
			content=campHTML;
		String role="MEM";
		String background=campData.getBackground();
		String borderColor=campData.getBordercolor();
		String borderwidth=campData.getBorderwidth();
		//String campaignId=campData.getCampId();
		if(campId==""|| "".equals(campId)){
			String[] inputparams=new String[]{
				campid,mgrId,campName,null,content,null,null,campTemplate,null,campHTML,role,contentType,
				background,borderColor,borderwidth};
		StatusObj statusobj=DbUtil.executeUpdateQuery(INSERT_QUERY, inputparams);
		}
		else{
			campid=campId;
			//Update campaign
			System.out.println("In Update method");
			DbUtil.executeUpdateQuery(UPDATE_QUERY, new String[]{campName,content,content,background,
					borderColor,borderwidth,contentType,campId});
		}
	return campid;
	}
	public static ArrayList<HashMap<String,String>> getUserEventsList(String mgrId){
		String EVENTS_OF_USER="select code,unitid,eventid,eventname,description,city,state,country," +
				"to_char(start_date,'MM/dd/yyyy') as start_date, end_date " 
		    +" from  eventinfo where role='Member' and  (status='ACTIVE' or status='PENDING') " +
		    		"and mgr_id=to_number(?,'9999999999') order by eventname";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		ArrayList<HashMap<String,String>> listObj=new ArrayList<HashMap<String,String>>();
		statobj=dbmanager.executeSelectQuery( EVENTS_OF_USER,new String[]{mgrId});
		if(statobj !=null && statobj.getStatus()    && statobj.getCount()>0 ){
			int recordcounttodata=statobj.getCount();
			for(int i=0;i<recordcounttodata;i++){
				HashMap<String,String> mapObj=new HashMap<String,String>();
				String eventid=dbmanager.getValue(i,"eventid","");
				String stdt=dbmanager.getValue(i,"start_date","");
				String evntinfolink="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/event?eid="+eventid;
				String evtname=dbmanager.getValue(i,"eventname","");
				mapObj.put("evtname",evtname);
				mapObj.put("stdt",stdt);
				mapObj.put("evntinfolink",evntinfolink);
				listObj.add(mapObj);
			}
		}
	return listObj;
	}
	public static ArrayList<MailListDetails> getManagerMailLists(String userId){
		String query="select list_id,list_name from mail_list where manager_id=to_number(?,'9999999999') order by" +
				" list_name desc";
		DBManager dbmanager=new DBManager();     
		ArrayList<MailListDetails> mailList=new ArrayList<MailListDetails>();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{userId});
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
	public static HashMap<String,String> getMaillistMembersCount(String userId){
	 	HashMap<String,String> unitlistmap= new HashMap<String,String>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
			
			String countqduery="select list_id,count(*) as count from mail_list_members"
			                    +" where list_id in (select list_id  from mail_list " +
			                    		" where manager_id=to_number(?,'9999999999'))" +
			                    		" and upper(status)='AVAILABLE' group by list_id, status"; 
			
			statobj=dbmanager.executeSelectQuery( countqduery,new String[]{userId});
			if(statobj !=null && statobj.getStatus()){
				int recordcounttodata=statobj.getCount();
				for(int i=0;i<recordcounttodata;i++){
				unitlistmap.put(dbmanager.getValue(i,"list_id",""),dbmanager.getValue(i,"count","0"));
			}
			}//end if staus
				return unitlistmap;
	 }
	
	
	public static int getSelectedMailListCount(ArrayList<String> selectedlist)
	{
		int count=0;
		String cnt="";
		for(int i=0;i<selectedlist.size();i++)
		{
		String query="select count(*) as count from mail_list_members where list_id=cast(? as integer)";
		cnt=DbUtil.getVal(query, new String[]{selectedlist.get(i)});	
		System.out.println("the cnt value is"+cnt);
		count=count+Integer.parseInt(cnt);
		}
		return count;
	}
	
	
	public static EmailScheduleData getEmailBlastData(String userId,String campId,String emailCampId){
		DBManager dbmanager=new DBManager();
		EmailScheduleData emailSchData=new EmailScheduleData();
		StatusObj statobj=null;
		/*String query="select * from email_campaign where camp_id=to_number(?,'9999999999') and " +
				"manager_id=to_number(?,'9999999999') and campaigntemplate_id=?";*/
		String query="select camp_name,camp_html,camp_text,camp_subject,camp_to,camp_from,camp_replyto," +
				"EXTRACT(HOUR FROM camp_scheddate) AS hour,EXTRACT(DAY FROM camp_scheddate) AS day," +
				"EXTRACT(YEAR FROM camp_scheddate) AS year,EXTRACT(MONTH FROM camp_scheddate) AS month,display_logo," +
				"to_char(camp_scheddate,'MM/DD/YYYY,HH:MI AM') as schdate from email_campaign where camp_id=to_number(?,'9999999999') and " +
		"manager_id=to_number(?,'9999999999')";
		statobj=dbmanager.executeSelectQuery( query,new String[]{emailCampId,userId});
		if(statobj !=null && statobj.getStatus()){
			emailSchData.setCampFrom(dbmanager.getValue(0,"camp_from",""));
			emailSchData.setCampReplyTo(dbmanager.getValue(0,"camp_replyto",""));
			emailSchData.setCampSubject(dbmanager.getValue(0,"camp_subject",""));
			emailSchData.setDay(dbmanager.getValue(0,"day",""));
			emailSchData.setYear(dbmanager.getValue(0,"year",""));
			emailSchData.setMonth(dbmanager.getValue(0,"month",""));
			emailSchData.setHour(dbmanager.getValue(0,"hour",""));
			String campHtml=dbmanager.getValue(0,"camp_html","");
			String schDate=dbmanager.getValue(0,"schdate","");
			if(schDate.equals(""))
				emailSchData.setSchtimeType("later");
			else
				emailSchData.setSchtimeType("date");
			emailSchData.setCampScheduledDate(schDate);
			emailSchData.setCampHtml(campHtml);
			emailSchData.setDisplayEbeeLogo(dbmanager.getValue(0,"display_logo",""));
		}
		ArrayList<String> listValues=new ArrayList<String>();
		String recepeintsQuery="select recepient_id from email_recepients where camp_id=to_number(?,'9999999999')";
		statobj=dbmanager.executeSelectQuery( recepeintsQuery,new String[]{emailCampId});
		if(statobj !=null && statobj.getStatus()){
			int recordcounttodata=statobj.getCount();
			for(int i=0;i<recordcounttodata;i++){
				listValues.add(dbmanager.getValue(i,"recepient_id",""));
			}
		}
		emailSchData.setCampList(listValues);
		String campName=DbUtil.getVal("select camp_name from campaign_templates where camp_id=to_number(?,'9999999999')", new String[]{campId});
		emailSchData.setCampName(campName);
		return emailSchData;
	}
	public static void insertEmailCampaign(String mgrId,EmailScheduleData emailschData,String templateId,String emailCampaignId) {
		String query="insert into email_campaign (manager_id,camp_id,camp_name,camp_groupid,"
			+" camp_grouptype,camp_html,camp_text,camp_subject,camp_to,camp_from,camp_replyto,camp_scheddate,"
			+" camp_status,camp_timezone,created_by,created_at,updated_by,updated_at,unitid,campaigntemplate_id,display_logo)"
			+" values (to_number(?,'9999999999'),to_number(?,'9999999999'),?,to_number(?,'9999999999'),?,?,?,?,?,?,?," +
					"to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS')," +
					"'mailscheduler',now(),'mailscheduler',now(),CAST('13579' as integer),?,?)";
		try{
		//String campId=DbUtil.getVal("select nextval('seq_campaign') as campid",null);
		String campId=emailschData.getCampId();
		String campName=emailschData.getCampName();
		String campGroupId="13579";
		String campGroupType="Event";
		String campHTML=emailschData.getCampHtml();
		String campText=emailschData.getCampText();
		String campSubjet=emailschData.getCampSubject();
		String campFrom=emailschData.getCampFrom().trim();
		String replyTo=emailschData.getCampReplyTo();
		String schDate=emailschData.getCampScheduledDate();
		System.out.println("schDate is:"+schDate); 
		String campStatus=emailschData.getCampStatus();
		String campTimezone=emailschData.getCampTimeZone();
		System.out.println("campTimezone is:"+campTimezone);
		ArrayList<String> listIds=emailschData.getCampList();
		String display_logo=emailschData.getDisplayEbeeLogo();
		String camp_to="";
		if(emailCampaignId==""|| "".equals(emailCampaignId)){
			//Insertion
		for (String opt : listIds) {
			insertEmailRecepients(campId,opt);
		}
		DBManager dbmanager=new DBManager();
		StatusObj statobj=DbUtil.executeUpdateQuery(query,new String[]{mgrId,campId,campName,campGroupId,campGroupType,
				campHTML,campText,campSubjet,camp_to,campFrom,replyTo,schDate,campStatus,campTimezone,templateId,display_logo});	 
		}
		else{
			//Updation
			String DELETE_RECEIPIENTS_QUERY="delete from email_recepients where camp_id=to_number(?,'9999999999')";
			DbUtil.executeUpdateQuery(DELETE_RECEIPIENTS_QUERY,new String[]{emailCampaignId});
			for (String opt : listIds) {
				insertEmailRecepients(emailCampaignId,opt);
			}
			String UPDATE_EMAILCAMPAIGN_DATA="update email_campaign set camp_html=?,camp_text=?," +
					"camp_subject=?,camp_from=?,camp_replyto=?,camp_scheddate=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'),updated_at=now(),display_logo=? where " +
					"camp_id=to_number(?,'9999999999') and campaigntemplate_id=?";
			DbUtil.executeUpdateQuery(UPDATE_EMAILCAMPAIGN_DATA,new String[]{campHTML,campText,campSubjet,
					campFrom,replyTo,schDate,display_logo,emailCampaignId,templateId});
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ther is an error in inserting Email Campaign Blast");
		}
	}
	public static void insertEmailRecepients(String campid, String listid)
			throws Exception {
		try {
			String query = "insert into email_recepients(camp_id, recepient_id, created_by,created_at,updated_by," +
					"updated_at) values (to_number(?,'9999999999'),to_number(?,'9999999999'),'mailscheduler',now()," +
					" 'mailschedule',now())";
			DBManager dbmanager = new DBManager();
			StatusObj statobj =DbUtil.executeUpdateQuery(query,new String[]{campid,listid});
		} catch (Exception we) {
			System.out.println("There is an error");
		}


}//end of email recepients
	public static ArrayList<HashMap<String,String>> getCompletedEmailBlasts(String mgrId,String campId){
		
		String ECOFUSER="SELECT camp_id,camp_name,to_char(camp_scheddate,'MM/dd/yyyy, hh:mi AM') as camp_scheddate,camp_subject " +
				"FROM email_campaign where" +
				" manager_id=to_number(?,'9999999999') and campaigntemplate_id=? and upper(camp_status)='S' order by camp_id desc";
		return getEmailBlasts(ECOFUSER,mgrId,campId);
	}
	public static ArrayList<HashMap<String,String>> getScheduledEmailBlasts(String mgrId,String campId){
		String ECOFUSER="SELECT camp_id,camp_name,to_char(camp_scheddate,'MM/dd/yyyy, hh:mi AM') as camp_scheddate,camp_subject " +
				"FROM email_campaign where" +
				" manager_id=to_number(?,'9999999999') and campaigntemplate_id=? and upper(camp_status) in ('P','T') order by camp_id desc";
		return getEmailBlasts(ECOFUSER,mgrId,campId);
	}
	public static ArrayList<HashMap<String,String>> getEmailBlasts(String query,String mgrId,String campId){
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		ArrayList<HashMap<String,String>> listObj=new ArrayList<HashMap<String,String>>();
		statobj=dbmanager.executeSelectQuery(query,new String[]{mgrId,campId});
		if(statobj !=null && statobj.getStatus() && statobj.getCount()>0 ){
		int recordcounttodata=statobj.getCount();
		for(int i=0;i<recordcounttodata;i++){
				HashMap<String,String> mapObj=new HashMap<String,String>();
				String camp_id=dbmanager.getValue(i,"camp_id","");
				String camp_name=dbmanager.getValue(i,"camp_name","");
				String camp_scheddate=dbmanager.getValue(i,"camp_scheddate","");
				String camp_subject=dbmanager.getValue(i,"camp_subject","");
				mapObj.put("camp_id",camp_id);
				mapObj.put("camp_name",camp_name);
				mapObj.put("camp_scheddate",camp_scheddate);
				mapObj.put("camp_subject",camp_subject);
				listObj.add(mapObj);
			}
		}
		return listObj;

	}
	public static HashMap<String,String> getemailBlastsSummaryCount(String mgrId){
		String query="select sum(success) as completed,sum(pending) as scheduled from" +
				"  (select (case when camp_status='S' then 1 else 0 end ) as success,(case when " +
				"camp_status in ('P','T') then 1 else 0 end ) as pending from email_campaign where " +
				"CAST(manager_id AS VARCHAR)=?)as a " ;
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery( query,new String[]{mgrId});
		HashMap<String,String> mapObj=new HashMap<String,String>();
		String completedCount=dbmanager.getValue(0,"completed","");
		String scheduledCount=dbmanager.getValue(0,"scheduled","");
		mapObj.put("COMPLETED",completedCount);
		mapObj.put("SCHEDULED",scheduledCount);
		return mapObj;
	}
	public static ArrayList<String> getUserTotalInfo(String mgrId){
		String query="select getMemberName(user_id||'') as name,street,city,state,country,zip,email " +
				" from user_profile where user_id=?";
		ArrayList<String> addresslist=new ArrayList<String>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String [] {mgrId});
		if(statobj.getStatus()){
		addresslist.add(dbmanager.getValue(0,"name",""));
		addresslist.add(dbmanager.getValue(0,"street",""));
		addresslist.add(dbmanager.getValue(0,"city",""));
		addresslist.add(dbmanager.getValue(0,"state",""));
		addresslist.add(dbmanager.getValue(0,"country",""));
		addresslist.add(dbmanager.getValue(0,"zip",""));
		
		}
		return addresslist;
	}
	public static String getNewCampId(){
		String query="select  nextval('seq_campaign') as campid";
		String campIdVal=DbUtil.getVal(query,null);
		return campIdVal;
	}
	/*public static String sendTestMail(String userId,EmailScheduleData emailschData){
		String msg="";
		EmailObj emailobj=null;
		try{				
			emailobj=new EmailObj();				
			emailobj.setTo(emailschData.getMailtotest().trim());
			emailobj.setFrom(emailschData.getCampFrom());
			emailobj.setHtmlMessage(emailschData.getCampHtml());				
			emailobj.setReplyTo(emailschData.getCampReplyTo());
			emailobj.setSubject(emailschData.getCampSubject()+" (Test mail)");
			EventbeeMail.sendHtmlMailPlain(emailobj);
			return "Success";	
		}catch(Exception exp){				
			//EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "ecmailprocess .jsp in sending test mail from emial attendee", "jsp service", exp.getMessage(), exp ) ;
		}
		return msg;
		
	}*/
	public static String getMgrEmail(String mgrId){
		String mgr_email=DbUtil.getVal("select email from user_profile where user_id=?",new String[]{mgrId});
		return mgr_email;
	}
	public static int getActiveMembersCount(ArrayList<String> listIds,String status){
		int total=0;
		for(String listId:listIds){
			total+=getActiveMembersCount(listId,status);
		}
		return total;
	}
	public static int getActiveMembersCount(String listId,String status){
		int count=0;
		String MEMBERS_OF_LIST_ACTIVE_COUNT="select count(*) as count from member_profile  where  member_id " +
				"in(select member_id from mail_list_members where list_id=to_number(?,'9999999999')" +
				" and upper(status)=?)";
		String cnt=DbUtil.getVal(MEMBERS_OF_LIST_ACTIVE_COUNT,new String[]{listId,status});
		count=Integer.parseInt(cnt);
		return count;
	}
	public static int getQuotaLeft(String userId){
		String tempquota="";
		tempquota=DbUtil.getVal("select quotaval from user_quota where userid=? and quotatype='mail.quota.buy'",new String[]{userId});
		if(tempquota==null) tempquota="100";
		/*String quotaquery="select b.camp_id, count(*) as count from mail_list_members a,  email_recepients b, email_campaign c"
						+" where a.list_id=b.recepient_id"
						+" and b.camp_id =c.camp_id "
						+" and camp_status<>'S' "
						+" and manager_id=to_number(?,'9999999999')"
						+" and status='available' group by b.camp_id";*/
		String quotaquery="select sum(value) from mail_quota where userid=? and type=?";
		String usedquota=DbUtil.getVal(quotaquery, new String[]{userId,"D"});
		if(usedquota==null)usedquota="0";
		int resquota=Integer.parseInt(usedquota);
	//	if(resquota<0)
		//	resquota=0;
		int quota_left=Integer.parseInt(tempquota)-resquota;
			//System.out.println("quota_left"+quota_left);
		if(quota_left<0)
			quota_left=0;
		return quota_left;
	}
	public static int getreservedcount(String userId,String query){
	 	
		int quotacount=0;
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery( query,new String[]{userId});
		if(statobj !=null && statobj.getStatus()){
		int recordcounttodata=statobj.getCount();
		for(int i=0;i<recordcounttodata;i++){
			quotacount+=Integer.parseInt(dbmanager.getValue(i,"count",""));
		}//End of for()
		}//end if staus
		return quotacount;
	 }
	public static HashMap<String,String> getEmailBlastReportData(String emailCampId){
		HashMap<String,String> reportData=new HashMap<String,String>();
		reportData.put("SENT","0");
		reportData.put("OPEN","0");
		reportData.put("BOUNCE","0");
		reportData.put("CLICK","0");
		String GET_SENT="select count(*) from campaign_sent where camp_id=to_number(?,'9999999999')";
		String GET_OPEN="select count(distinct(a.eid)) from campaign_open a,campaign_sent b where " +
				"a.eid=b.eid and b.camp_id=to_number(?,'9999999999')";
		String GET_BOUNCE="select count(distinct(member_code)) from campaign_bounce  where camp_id=to_number(?,'9999999999')";
		String GET_CLICK="select count(*) from campaign_click_through a,campaign_sent b where " +
				"a.eid=b.eid and b.camp_id=to_number(?,'9999999999')";
		String SENT=DbUtil.getVal(GET_SENT,new String[]{emailCampId});
		String OPEN=DbUtil.getVal(GET_OPEN,new String[]{emailCampId});
		String BOUNCE=DbUtil.getVal(GET_BOUNCE,new String[]{emailCampId});
		String CLICK=DbUtil.getVal(GET_CLICK,new String[]{emailCampId});
		reportData.put("SENT",SENT);
		reportData.put("OPEN",OPEN);
		reportData.put("BOUNCE",BOUNCE);
		reportData.put("CLICK",CLICK);
		return reportData;
	}
	public static ArrayList<String> getListNamesofCampaign(String emailCampId){
		String query="select list_name from mail_list where list_id in(select recepient_id from email_recepients " +
				"where camp_id =to_number(?,'9999999999'))";
		ArrayList<String> nameList=new ArrayList<String>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery( query,new String[]{emailCampId});
		if(statobj !=null && statobj.getStatus()){
		int recordcounttodata=statobj.getCount();
		for(int i=0;i<recordcounttodata;i++){
			nameList.add(dbmanager.getValue(i,"list_name",""));
		}//End of for()
		}//end if staus
		return nameList;
	}
	public static void deleteCampaign(String campId){
		DbUtil.executeUpdateQuery("delete from email_recepients where camp_id in(select camp_id from email_campaign where campaigntemplate_id=?)", new String[]{campId});
		DbUtil.executeUpdateQuery("delete from email_campaign where campaigntemplate_id=?", new String[]{campId});
		DbUtil.executeUpdateQuery("delete from campaign_templates where camp_id=to_number(?,'99999999')", new String[]{campId});
	}
	public static void deleteEmailBlast(String emailCampId){
		DbUtil.executeUpdateQuery("delete from email_recepients where camp_id=to_number(?,'99999999') ", new String[]{emailCampId});
		DbUtil.executeUpdateQuery("delete from email_campaign where camp_id=to_number(?,'99999999')", new String[]{emailCampId});
	}

public static void insertIntoMailQuota(int count,String userid)
{
String cnt=Integer.toString(count);
	String query="insert into mail_quota(unitid,userid,type,value,tdate) values(?,?,?,cast(? as numeric),now())";
	DbUtil.executeUpdateQuery(query,new String[]{"13579",userid,"D",cnt});
}


}
