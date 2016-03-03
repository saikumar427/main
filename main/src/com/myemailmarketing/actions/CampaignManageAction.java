package com.myemailmarketing.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EventBeeValidations;
import com.eventbee.general.EventbeeDates;
import com.eventbee.general.GenUtil;
import com.membertasks.actions.MemberTasksWrapper;
import com.myemailmarketing.beans.CampaignDetails;
import com.myemailmarketing.beans.EmailScheduleData;
import com.myemailmarketing.beans.MailListDetails;
import com.myemailmarketing.dbhelpers.CampaignDesignDB;
import com.myemailmarketing.dbhelpers.MailQuotaDB;
import com.myemailmarketing.helpers.CampaignHelper;
import com.myemailmarketing.helpers.JsonBuilderHelper;
import com.mymarketing.dbhelpers.MarketingManageDB;

public class CampaignManageAction extends MemberTasksWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4110512622505074839L;
	private String campId="";// Campaign Template Id.
	private EmailScheduleData emailScheduleData=new EmailScheduleData();
	private ArrayList<MailListDetails> allMailLists=new ArrayList<MailListDetails>();
	private HashMap<String,String> listMembersCount=new HashMap<String,String>();
	private String appName="";
	private ArrayList<HashMap<String,String>> completedEmailBlasts=new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String,String>> scheduledEmailBlasts=new ArrayList<HashMap<String,String>>();
	private String jsonData="";
	private ArrayList<String> selectedMailLists=new ArrayList<String>();
	private String hour="";
	private String msg="";
	private String mailQuota="";
	private boolean errorsExists;
	private String emailCampId="";
	private String priviewContent="";
	private HashMap<String,String> blastReportData=new HashMap<String,String>();
	private String listNamesofCampaign="";
	private String demo="";
	private int count=0;
	private String action="";
	private String res="";
	private int maillistcount=0;
	private int editselectedcount=0;
	
	
	public int getEditselectedcount() {
		return editselectedcount;
	}
	public void setEditselectedcount(int editselectedcount) {
		this.editselectedcount = editselectedcount;
	}
	public int getMaillistcount() {
		return maillistcount;
	}
	public void setMaillistcount(int maillistcount) {
		this.maillistcount = maillistcount;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getListNamesofCampaign() {
		return listNamesofCampaign;
	}
	public void setListNamesofCampaign(String listNamesofCampaign) {
		this.listNamesofCampaign = listNamesofCampaign;
	}
	public HashMap<String, String> getBlastReportData() {
		return blastReportData;
	}
	public void setBlastReportData(HashMap<String, String> blastReportData) {
		this.blastReportData = blastReportData;
	}
	public String getPriviewContent() {
		return priviewContent;
	}
	public void setPriviewContent(String priviewContent) {
		this.priviewContent = priviewContent;
	}
	public String getEmailCampId() {
		return emailCampId;
	}
	public void setEmailCampId(String emailCampId) {
		this.emailCampId = emailCampId;
	}
	public HashMap<String, String> getListMembersCount() {
		return listMembersCount;
	}
	public void setListMembersCount(HashMap<String, String> listMembersCount) {
		this.listMembersCount = listMembersCount;
	}
	public boolean isErrorsExists() {
		return errorsExists;
	}
	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}
	public String getMailQuota() {
		return mailQuota;
	}
	public void setMailQuota(String mailQuota) {
		this.mailQuota = mailQuota;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public ArrayList<String> getSelectedMailLists() {
		return selectedMailLists;
	}
	public void setSelectedMailLists(ArrayList<String> selectedMailLists) {
		this.selectedMailLists = selectedMailLists;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public ArrayList<HashMap<String, String>> getCompletedEmailBlasts() {
		return completedEmailBlasts;
	}
	public void setCompletedEmailBlasts(
			ArrayList<HashMap<String, String>> completedEmailBlasts) {
		this.completedEmailBlasts = completedEmailBlasts;
	}
	public ArrayList<HashMap<String, String>> getScheduledEmailBlasts() {
		return scheduledEmailBlasts;
	}
	public void setScheduledEmailBlasts(
			ArrayList<HashMap<String, String>> scheduledEmailBlasts) {
		this.scheduledEmailBlasts = scheduledEmailBlasts;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public ArrayList<MailListDetails> getAllMailLists() {
		return allMailLists;
	}
	public void setAllMailLists(ArrayList<MailListDetails> allMailLists) {
		this.allMailLists = allMailLists;
	}
	public EmailScheduleData getEmailScheduleData() {
		return emailScheduleData;
	}
	public void setEmailScheduleData(EmailScheduleData emailScheduleData) {
		this.emailScheduleData = emailScheduleData;
	}
	public String getCampId() {
		return campId;
	}
	public void setCampId(String campId) {
		this.campId = campId;
	}
	public void prepare() throws Exception {
		try {
			super.prepare();
			appName=EbeeConstantsF.get("application.name","Eventbee");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public String execute(){
		return SUCCESS;
	}
	public String campaignInfo(){
		populateListData();
		CampaignDetails campDetails=CampaignDesignDB.getCampaignData(userId, campId);
		actionTitle=campDetails.getCampName()
		            +" > Manage Campaign";
		return SUCCESS;
	}
	public String scheduleblast(){
		prepareDataforScheduleBlast();
		return "scheduleblast";
	}
	public String populateListData(){
		scheduledEmailBlasts=CampaignDesignDB.getScheduledEmailBlasts(userId,campId);
		completedEmailBlasts=CampaignDesignDB.getCompletedEmailBlasts(userId,campId);
		count=completedEmailBlasts.size();
		System.out.println("completed count:"+count);
		if(count>0)
			demo="greater";
		JSONObject js= JsonBuilderHelper.getEmailBlastsJson(scheduledEmailBlasts, completedEmailBlasts);
		jsonData=js.toString();
		return "displaylistjson";
	}
	public String blastSave(){
		boolean status=true;
		System.out.println("selectedMailLists"+selectedMailLists);
		emailScheduleData.setCampList(selectedMailLists);
		if("respone".equals(res))
		{
			System.out.println("the result is"+res);
			return "blastscheduled";
		}
		status=validateInputData(emailScheduleData);
		//Uncomment the above line to do validations.
		
		if(status){
			maillistcount=CampaignDesignDB.getSelectedMailListCount(selectedMailLists);
			int totalmailcount=CampaignDesignDB.getQuotaLeft(userId);
			if(editselectedcount>0)
				totalmailcount=totalmailcount+editselectedcount;
			if(maillistcount>0){
				if(totalmailcount<maillistcount){
				try{
           	 JSONObject json=new JSONObject();
           	 json.put("selectedmailcount",maillistcount);
           	 json.put("quotaleft",totalmailcount);
           	 jsonData=json.toString();
				}catch(Exception e)
				{System.out.println("Exception occured in blastSave Method"+e.getMessage());
				}
				return "blastexceed";
			}
			}
			if(editselectedcount>0)
			maillistcount=maillistcount-editselectedcount;
			CampaignDesignDB.insertIntoMailQuota(maillistcount,userId);
			emailScheduleData=CampaignHelper.processEmailBlastData(userId,campId,emailScheduleData);
			CampaignDesignDB.insertEmailCampaign(userId,emailScheduleData,campId,emailCampId);
		return "blasted";
		}
		else{
			System.out.println("emailCampId"+emailCampId);
			if(!"".equals(emailCampId)){
				editEmailBlast();
			}
			else{
				System.out.println("the enterd email id is"+emailScheduleData.getCampFrom());
				prepareDataforScheduleBlast();
				emailScheduleData.setCampFrom(emailScheduleData.getCampFrom().trim());
			}
		}
		return "inputerror";
	}
	public String emailblastTestMail(){
		System.out.println("Test mail sent");
		msg=CampaignHelper.sendTestMail(userId, campId, emailScheduleData);
		return "ajaxmsg";
	}
	public boolean validateInputData(EmailScheduleData eschData){
		try{
		int membersCount=CampaignDesignDB.getActiveMembersCount(selectedMailLists,userId);
		System.out.println("membersCount"+membersCount);
		if(eschData.getCampSubject().trim().length()==0){
			addFieldError("emailScheduleData.memberlist.subempty", "Subject is empty.");
		}
		if ("".equals(eschData.getCampFrom().trim()))
			addFieldError("emailScheduleData.email.isempty", " From email is empty");
		else if(!EventBeeValidations.isValidFromEmail(eschData.getCampFrom().trim()))
			addFieldError("emailScheduleData.email.invalid"," Invalid email " );

		if(selectedMailLists.size()==0){
			addFieldError("emailScheduleData.memberlist.notselected", "Select a Member List.");
		}
		else{
		maillistcount=CampaignDesignDB.getSelectedMailListCount(selectedMailLists);
		if(maillistcount<=0)
			addFieldError("emailScheduleData.memberlist.count","Should be at least one email in selected mail list");
		}
		/*else if (membersCount>Integer.parseInt(mailQuota)){
			String temp="No Mail Quota available, <a href=\"../myemailmarketing/home\">click here</a> to buy Mail Quota";
			addFieldError("emailScheduleData.insufficientquota", temp);
		}*/
	
		/*maillistcount=CampaignDesignDB.getSelectedMailListCount(selectedMailLists);
		int totalmailcount=CampaignDesignDB.getQuotaLeft(userId);
		if(editselectedcount>0)
		{
			if(maillistcount>editselectedcount)
			{	int diff=maillistcount-editselectedcount;
				totalmailcount=totalmailcount-diff;
			}else{
				int diffrecne=editselectedcount-maillistcount;
				totalmailcount=totalmailcount+diffrecne;
		}
		}
			
		if(maillistcount>0){
		if(totalmailcount<maillistcount)
			addFieldError("emailScheduleData.memberlist.exceded", "exceeded");
		}*/
		if (!getFieldErrors().isEmpty()) {
			errorsExists = true;
			return false;
		}
		}
		catch(Exception e){
			System.out.println("There is an exception in validateInputData method");
		}
		return true;
	}

	public String editEmailBlast(){
		try {
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			String email="";
			emailScheduleData.setHour(EventbeeDates.getHoursHtml(hour,"emailScheduleData.time"));
			String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
			String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
			String year = Integer.toString(calendar.get(Calendar.YEAR));
			emailScheduleData.setDay(day);
			emailScheduleData.setYear(year);
			emailScheduleData.setMonth(month);
			if(!emailCampId.equals("")){
				if("".equals(action))
					email=emailScheduleData.getCampFrom().trim();
				emailScheduleData=CampaignDesignDB.getEmailBlastData(userId,campId,emailCampId);
				if("".equals(action))
				emailScheduleData.setCampFrom(email);
				if(emailScheduleData.getSchtimeType().equals("date")){
					hour=Integer.parseInt(emailScheduleData.getHour());
					emailScheduleData.setHour(EventbeeDates.getHoursHtml(hour,"emailScheduleData.time"));
				}else if(emailScheduleData.getSchtimeType().equals("later")){
					emailScheduleData.setHour(EventbeeDates.getHoursHtml(hour,"emailScheduleData.time"));
					emailScheduleData.setDay(day);
					emailScheduleData.setYear(year);
					emailScheduleData.setMonth(month);
				}
			}	
			mailQuota=Integer.toString(CampaignDesignDB.getQuotaLeft(userId));
			
			actionTitle="<a href='campaignlistmanage!campaignInfo?campId="+campId+"'>"+emailScheduleData.getCampName()+"</a> > Manage Email Blast Schedule";
			//prepareDataforScheduleBlast();
			allMailLists=CampaignDesignDB.getManagerMailLists(userId);
			//int hour=Integer.parseInt(emailScheduleData.getHour());
			//emailScheduleData.setHour(EventbeeDates.getHoursHtml(hour,"emailScheduleData.time"));
			selectedMailLists=emailScheduleData.getCampList();
			listMembersCount=CampaignDesignDB.getMaillistMembersCount(userId);
			editselectedcount=CampaignDesignDB.getSelectedMailListCount(selectedMailLists);
		} catch (NumberFormatException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return "scheduleblast";
	}
	public void prepareDataforScheduleBlast(){
		try {
			allMailLists = CampaignDesignDB.getManagerMailLists(userId);
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			emailScheduleData.setHour(EventbeeDates.getHoursHtml(hour,"emailScheduleData.time"));
			String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
			String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
			String year = Integer.toString(calendar.get(Calendar.YEAR));
			emailScheduleData.setDay(day);
			emailScheduleData.setYear(year);
			emailScheduleData.setMonth(month);
			CampaignDetails campDetails = CampaignDesignDB.getCampaignData(userId, campId);
			emailScheduleData.setCampSubject(campDetails.getCampName());
			emailScheduleData.setCampName(campDetails.getCampName());
			String email = CampaignDesignDB.getMgrEmail(userId);
			//emailScheduleData.setCampFrom(email);
			emailScheduleData.setCampReplyTo(email);
			actionTitle = "<a href='campaignlistmanage!campaignInfo?campId="
					+ campId + "'>" + emailScheduleData.getCampName()
					+ "</a> > Email Blast Schedule";
			mailQuota = Integer.toString(CampaignDesignDB.getQuotaLeft(userId));
			listMembersCount=CampaignDesignDB.getMaillistMembersCount(userId);
		} catch (Exception e) {
			System.out.println("There is an error "+e);
		}
	}
	public String previewEmailBlast(){
		emailScheduleData=CampaignDesignDB.getEmailBlastData(userId,campId,emailCampId);
		priviewContent=emailScheduleData.getCampHtml();
		System.out.println("priviewContent"+priviewContent);
		return "previewCampaign";
	}
	public String getBlastReports(){
		try{
		ArrayList<String> listNames=new ArrayList<String>();
		listNames=CampaignDesignDB.getListNamesofCampaign(emailCampId);
		String[] sb = (String[])(listNames.toArray(new String [listNames.size ()]));
		listNamesofCampaign=GenUtil.stringArrayToStr(sb,",");
		emailScheduleData=CampaignDesignDB.getEmailBlastData(userId,campId,emailCampId);
		blastReportData=CampaignDesignDB.getEmailBlastReportData(emailCampId);
		}
		catch(Exception e){
			System.out.println("There is an error in getting report data from DB."+e);
		}
		return "blastreport";
	}
	public String deleteCampaign(){
		System.out.println("deleted campaign"+campId);
		CampaignDesignDB.deleteCampaign(campId);
		return "ajaxmsg"; 
	}
	public String deleteEmailBlast(){
		System.out.println("deleted email blast");
		CampaignDesignDB.deleteEmailBlast(emailCampId);
		return "ajaxmsg";
	}
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
}
