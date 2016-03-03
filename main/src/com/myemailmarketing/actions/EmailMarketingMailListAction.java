package com.myemailmarketing.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.*;

import org.json.JSONObject;
import com.event.beans.EventData;
import com.event.dbhelpers.EventDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.EventBeeValidations;
import com.eventbee.general.GenUtil;
import com.membertasks.actions.MemberTasksWrapper;
import com.myemailmarketing.beans.MailListDetails;
import com.myemailmarketing.beans.MemberData;
import com.myemailmarketing.dbhelpers.MailingListDB;
import com.myemailmarketing.helpers.JsonBuilderHelper;
import com.myemailmarketing.helpers.ShowFileDataHelper;
public class EmailMarketingMailListAction extends MemberTasksWrapper {

	private static final long serialVersionUID = 7039441661632727801L;
	private MailListDetails mailList=new MailListDetails();
	private String listId="";
	private ArrayList<MailListDetails> allMailLists=new ArrayList<MailListDetails>();
	private MemberData memberData=new MemberData();
	private ArrayList<Entity> genderTypelist=new ArrayList<Entity>();
	private ArrayList<Entity> emailPreflist=new ArrayList<Entity>();
	private ArrayList liststobeMerged=new ArrayList();
	private String filePath="";
	private ArrayList fileData=new ArrayList();
	private ArrayList<Entity> memberAttribs=new ArrayList<Entity>();
	private ArrayList<String> optionsList = new ArrayList<String>();
	private ArrayList<Entity> statusList=new ArrayList<Entity>();
	private ShowFileDataHelper helperObject=new ShowFileDataHelper();
	private String jsonData="";
	private String memberId="";
	private String bulkmails="";
	private String jSonSumary="";
	private boolean replaceDuplicates;
	private int invalidcount=0;
	private int duplicateemailcount=0;
	private int addedmembers=0;
	private int bulkvalidlines=0;
	private int bulkadded=0;
	private int bulkduplicates=0;
	private String button="";   
	private String action="";
	private int validcount=0;
	
	private EventData eventData=new EventData();
	private HashMap<String,String> memberSummaryCount=new HashMap<String,String>();
	private ArrayList fileheadings=new ArrayList();
	private String ext="";
		
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public ArrayList getFileheadings() {
		return fileheadings;
	}
	public void setFileheadings(ArrayList fileheadings) {
		this.fileheadings = fileheadings;
	}
	public HashMap<String, String> getMemberSummaryCount() {
		return memberSummaryCount;
	}
	public void setMemberSummaryCount(HashMap<String, String> memberSummaryCount) {
		this.memberSummaryCount = memberSummaryCount;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public ArrayList<Entity> getStatusList() {
		return statusList;
	}
	public void setStatusList(ArrayList<Entity> statusList) {
		this.statusList = statusList;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public void setHelperObject(ShowFileDataHelper helperObject) {
		this.helperObject = helperObject;
	}
	public ShowFileDataHelper getHelperObject() {
		return helperObject;
	}
	public ArrayList<String> getOptionsList() {
		return optionsList;
	}
	public void setOptionsList(ArrayList<String> optionsList) {
		this.optionsList = optionsList;
	}
	/**
	 * @param listId the listId to set
	 */
	public void setListId(String listId) {
		this.listId = listId;
	}
	/**
	 * @return the listId
	 */
	public String getListId() {
		return listId;
	}
	
	public ArrayList<Entity> getMemberAttribs() {
		return memberAttribs;
	}
	public void setMemberAttribs(ArrayList<Entity> memberAttribs) {
		this.memberAttribs = memberAttribs;
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public ArrayList getListstobeMerged() {
		return liststobeMerged;
	}
	public void setListstobeMerged(ArrayList liststobeMerged) {
		this.liststobeMerged = liststobeMerged;
	}
	public ArrayList<MailListDetails> getAllMailLists() {
		return allMailLists;
	}
	public void setAllMailLists(ArrayList<MailListDetails> allMailLists) {
		this.allMailLists = allMailLists;
	}
	public ArrayList<Entity> getGenderTypelist() {
		return genderTypelist;
	}
	public void setGenderTypelist(ArrayList<Entity> genderTypelist) {
		this.genderTypelist = genderTypelist;
	}
	public void setEmailPreflist(ArrayList<Entity> emailPreflist) {
		this.emailPreflist = emailPreflist;
	}
	public ArrayList<Entity> getEmailPreflist() {
		return emailPreflist;
	}
	public void setMemberData(MemberData memberData) {
		this.memberData = memberData;
	}
	public MemberData getMemberData() {
		return memberData;
	}
	public MailListDetails getMailList() {
		return mailList;
	}
	public void setMailList(MailListDetails mailList) {
		this.mailList = mailList;
	}
	public String getBulkmails() {
		return bulkmails;
	}
	public void setBulkmails(String bulkmails) {
		this.bulkmails = bulkmails;
	}
	public EventData getEventData() {
		return eventData;
	}
	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}

	public void populateemailPref(){
		emailPreflist.clear();
		emailPreflist.add(new Entity("html","HTML"));
		emailPreflist.add(new Entity("text","Text"));				
	}
	public void populategenderTypes(){
		genderTypelist.clear();
		genderTypelist.add(new Entity("Male","Male"));
		genderTypelist.add(new Entity("Female","Female"));				
	}
	public void populateStatusList(){
		statusList.clear();
		statusList.add(new Entity("available","Active"));
		statusList.add(new Entity("unsubscribed","Unsubscribed"));
		statusList.add(new Entity("invalid","Invalid"));
		statusList.add(new Entity("bounced","Bounced"));
	}
	public void prepare() throws Exception {
		try {
			super.prepare();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public String execute(){
		System.out.println("In Execute method");
		System.out.println(memberAttribs);
		
		return INPUT;
	}
	public boolean validateInputData(){
		if("".equals(mailList.getListName().trim())){
			addFieldError("mailList.listName", "List Name is empty");
			
		}
		if("".equals(mailList.getUnsubmsg().trim())){
			addFieldError("mailList.unsubmsg", "Unsubscribe Message is empty");
			
		}
		if(!getFieldErrors().isEmpty()){
			return false;
		}
		return true;
	}
	
	public String addMailList(){
		
		mailList.setUnsubmsg("If you no longer wish to receive emails from us, please click on Unsubscribe button.");
		System.out.println("EmailMarketingMailListAction addMailList method");
		return INPUT;
	}
	public String saveList(){
		System.out.println("EmailMarketingMailListAction saveList method");
		boolean isValid=validateInputData();
		System.out.println("isValid"+isValid);
		if(isValid)
		{
		MailingListDB.saveMailList(userId,mailList);
		return "listadded";
		}
		else{
		return "inputerror";
		}
		
	}
	
	public String listInfo(){
		eventData.setServerAddress(EventDB.serverAdderess());
		System.out.println("server address:"+eventData.getServerAddress());
		mailList=MailingListDB.getListInfo(userId, listId);
		actionTitle=mailList.getListName()+" > Member List";
		memberSummaryCount=MailingListDB.getSummaryCount(userId,listId);
		return "listinfo";
	}
	public String editMailList(){
		System.out.println("EmailMarketingMailListAction editMailList method");
		mailList=MailingListDB.getListInfo(userId, listId);
		System.out.println("listname"+mailList.getListName());
		System.out.println("listdesc"+mailList.getDescritption());
		return INPUT;
	}
	public String addMemberManual(){
		populateemailPref();
		populategenderTypes();
		populateStatusList();
		return "addmanual";
	}
	public String editMemberManual(){
		populateemailPref();
		populategenderTypes();
		populateStatusList();
		System.out.println("Edit member data member id value is "+memberId);
		memberData=MailingListDB.getMemberData(userId, memberId,listId);
		System.out.println("memberData"+memberData.getFirstName());
		return "addmanual";
	}
	public String saveMemberManual(){    
		
		if("".equals(memberData.getEmail().trim()))
			addFieldError("memberData.email", "Email is empty.");
		else if(!EventBeeValidations.isValidFromEmail(memberData.getEmail().trim()))
			addFieldError("memberData.email","Invalid Format For Email.");
		if (!getFieldErrors().isEmpty()) 
			return "addmanualerror";
		else{
			memberData.setEmail(memberData.getEmail().trim());
			MailingListDB.saveManualMemberData(userId,listId,memberData);			
			return "manualadded";}
}
	
	
	
	
	
	private int validateMemberManualInputData(){    
		return ShowFileDataHelper.isValidEmail(memberData.getEmail());
	}
	public String mergeLists(){
		allMailLists=MailingListDB.getManagerMailLists(userId,listId);
		return "mergelistsinput";
	}
	public String saveMergeListData(){
		System.out.println("lists2be merged"+liststobeMerged);
		MailingListDB.saveMergeListData(userId,listId,liststobeMerged);
		return "mergelistdone";
	}
	/*public String showMemberData(){
		System.out.println("enterting into the method");
		fileData=ShowFileDataHelper.getFileData(filePath,userId,listId);
		System.out.println("file data is"+fileData);
		memberAttribs=ShowFileDataHelper.getMemberAttribs();
		fileheadings=ShowFileDataHelper.getFileHeadings(userId,listId);
		System.out.println("ending of shoe memeber data");
		return "showmemberdata";
	}*/
	public String saveFileData(){
		ArrayList<MemberData> fileMemberData=null;
		fileMemberData=ShowFileDataHelper.getEntireFileData(filePath,userId,listId,"delete",ext);
		MailingListDB.saveFileData(userId,listId,fileMemberData);
		return "savefiledatadone";
	}
	public String showValidationData(){
		fileData=ShowFileDataHelper.getFileData(filePath,userId,listId,ext);
		validcount=ShowFileDataHelper.getValidCount(fileData);
		ArrayList<MemberData> addedcount=ShowFileDataHelper.getEntireFileData(filePath,userId,listId,"notdelete",ext);
		addedmembers=addedcount.size();
		duplicateemailcount=validcount-addedcount.size();
		return "showvaildations";
	}
	public String populateMembers(){
		System.out.println("list Id in populateMembers() method is"+listId);
		ArrayList<MemberData> activeMembersList=MailingListDB.getActiveMembers(userId, listId);
		ArrayList<MemberData> inactiveMembersList=MailingListDB.getInactiveMembers(userId, listId);
		ArrayList<MemberData> bouncedMembersLsList=MailingListDB.getBouncedMembers(userId, listId);
		ArrayList<MemberData> unsubscribedMembersList=MailingListDB.getUnsubMembers(userId, listId);
		JSONObject js=JsonBuilderHelper.getMembersJson(activeMembersList, inactiveMembersList, bouncedMembersLsList,unsubscribedMembersList);
		jsonData=js.toString();
		return "membersJson";
	}
	public String deleteList(){
		MailingListDB.deleteMailList(userId, listId);
		return "ajaxmsg";
	}
	public String deleteMember(){
		MailingListDB.deleteMember(memberId,listId);
		return "ajaxmsg";
	}
	public String bulkUpload(){
		if("cancel".equals(button)){
			bulkmails=bulkmails.replaceAll("<br/>","\n");
			bulkmails=bulkmails.trim();}
		return "bulk";
	}
	public String SaveBulk(){
		if("insert".equals(action))
		bulkmails=bulkmails.replaceAll("<br/>","\n");
		long starttime=System.currentTimeMillis();
		ArrayList<String> bulkemails=new ArrayList<String>();
		ArrayList<MemberData> members=new ArrayList<MemberData>();
		JSONObject summaryJSON=new JSONObject();
		StringTokenizer lines=new StringTokenizer(bulkmails,"\n");
		int i=0,totalValidLines=0,duplicates=0;
		String temp=null;
		String invalidLines="";
		int noOfLines=lines.countTokens();
		while(lines.hasMoreTokens()){
			i++;
			memberData=new MemberData();
			memberData.setEmailPref("html");
			boolean isValidline=false;
			temp=lines.nextToken();
			StringTokenizer columns=new StringTokenizer(temp,",");
			int columncount=columns.countTokens();
			for(int k=1;k<=columncount;k++){
				if(k==1){
					String s=columns.nextToken().trim().toLowerCase();
					boolean value=isValidEmail(s);
					String valid="";
					if(value)
					valid="yes";
					else
					valid="no";
					if("no".equals(valid)){
						isValidline=false;
					}else {
						totalValidLines++;
						String duplicateemails=bulkUploadData(bulkemails,s, replaceDuplicates);
						bulkemails.add(s);
						if("notexisted".equals(duplicateemails)){
							bulkadded++;
							memberData.setEmail(s);
							isValidline=true;}
							else
							isValidline=false;
					}
				}else
				if(k==2){
					if(isValidline)
						memberData.setFirstName(columns.nextToken().trim());
				}else
				if(k==3){
					memberData.setLastName(columns.nextToken().trim());
				}
			}
			if(isValidline){
				members.add(memberData);
			}
			else
				invalidLines=invalidLines+temp+"\n";
		}if("insert".equals(action)){
		duplicates=MailingListDB.bulkUploadinsert(userId, listId, members, replaceDuplicates);
		}
		try{
			summaryJSON.put("validcount", totalValidLines);
			summaryJSON.put("invalidcount",i-totalValidLines);
			summaryJSON.put("duplicates", duplicates);
			summaryJSON.put("invalidlines", invalidLines);
			summaryJSON.put("totallines", noOfLines);
			summaryJSON.put("userId", userId);
			summaryJSON.put("listId", listId);
			summaryJSON.put("data", GenUtil.textToHtml(bulkmails));
			jSonSumary=summaryJSON.toString();
		
		}
		catch (Exception e) {
				System.out.println("Exception caught "+e.getMessage());
		}
		long endtime=System.currentTimeMillis();
		long totaltime=endtime-starttime;
		System.out.println("Total time taken to process: ");
		System.out.println("in milliseconds: "+totaltime);
		int seconds=(int)((totaltime/1000));
		System.out.println("in seconds: "+seconds);
		int minutes=(int)((totaltime/1000)/60);
		System.out.println("in minutes: "+minutes);
		return "bulksummary";
	}
	/**
	 * Validates email string using patterns
	 * @param email 
	 */
	
	public String showBulkValidateData()
	{
		ArrayList<String> bulkemails=new ArrayList<String>();
		ArrayList<MemberData> members=new ArrayList<MemberData>();
		bulkmails=bulkmails.replaceAll("<br/>","\n");
		bulkmails=bulkmails.replaceAll("\\<.*?\\>", "");
		bulkmails=bulkmails.trim();
		StringTokenizer lines=new StringTokenizer(bulkmails,"\n");
		int i=0;
		String temp=null;
		String invalidLines="";
		int noOfLines=lines.countTokens();
		while(lines.hasMoreTokens()){
			i++;
			memberData=new MemberData();
			memberData.setEmailPref("html");
			boolean isValidline=false;
			temp=lines.nextToken();
			StringTokenizer columns=new StringTokenizer(temp,",");
			int columncount=columns.countTokens();
			for(int k=1;k<=columncount;k++){
				if(k==1){
					String s=columns.nextToken().trim().toLowerCase();
					String valid="";
					boolean value=isValidEmail(s);
					if(value)
						valid="yes";
					else
						valid="no";
					if("no".equals(valid)){
						isValidline=false;
					}else {
						bulkvalidlines++;
						String duplicates=bulkUploadData(bulkemails,s, replaceDuplicates);
						bulkemails.add(s);
						if("notexisted".equals(duplicates)){
						bulkadded++;
						memberData.setEmail(s);
						isValidline=true;}
						else
							isValidline=false;
					}
				}else
				if(k==2){
					if(isValidline)
						memberData.setFirstName(columns.nextToken().trim());
				}else
				if(k==3){
					memberData.setLastName(columns.nextToken().trim());
				}
			}
			if(isValidline){
				members.add(memberData);
			}
			else
				invalidLines=invalidLines+temp+"\n";
		}
		
		bulkduplicates=bulkvalidlines-bulkadded;
		bulkmails=bulkmails.replaceAll("\n","<br/>");
		bulkmails=bulkmails.replaceAll("\\n","<br/>");
		bulkmails=bulkmails.trim();
		return "showbulkvaildations";
	}
	
	public String bulkUploadData(ArrayList<String> filecontent,String cell,boolean replaceDuplicates)
	{
		System.out.println("the size is"+filecontent.size());	
		if(filecontent.contains(cell) && filecontent.size()!=0)
			return "exitsted";	
			else
			return "notexisted";
	}
	public boolean isValidEmail(String email){
		Pattern p = Pattern.compile("^[_A-Za-z0-9-_-]+(\\.[_A-Za-z0-9-_-]+)*@[A-Za-z0-9_-]+(\\.[A-Za-z0-9-_-]+)*(\\.[A-Za-z_-]{2,})$");
	      Matcher m = p.matcher(email);
	      boolean matchFound = m.matches();
	      if (matchFound)
	        return true;
	      else
	        return false;
	}
	public String deleteFile()
	{
		ShowFileDataHelper.deleteFile(userId,listId,ext);
	return "savefiledatadone";
	}
	public boolean isReplaceDuplicates() {
		return replaceDuplicates;
	}
	public void setReplaceDuplicates(boolean replaceDuplicates) {
		this.replaceDuplicates = replaceDuplicates;
	}
	public String getJSonSumary() {
		return jSonSumary;
	}
	public void setJSonSumary(String sonSumary) {
		jSonSumary = sonSumary;
	}
	public String addWidget(){
		System.out.println("widget method");
		eventData.setServerAddress(EventDB.serverAdderess());
		return "widget";
	}
	public ArrayList getFileData() {
		return fileData;
	}
	public void setFileData(ArrayList fileData) {
		this.fileData = fileData;
	}
	public int getInvalidcount() {
		return invalidcount;
	}
	public void setInvalidcount(int invalidcount) {
		this.invalidcount = invalidcount;
	}
	public int getDuplicateemailcount() {
		return duplicateemailcount;
	}
	public void setDuplicateemailcount(int duplicateemailcount) {
		this.duplicateemailcount = duplicateemailcount;
	}
	public int getAddedmembers() {
		return addedmembers;
	}
	public void setAddedmembers(int addedmembers) {
		this.addedmembers = addedmembers;
	}
	public int getBulkvalidlines() {
		return bulkvalidlines;
	}
	public void setBulkvalidlines(int bulkvalidlines) {
		this.bulkvalidlines = bulkvalidlines;
	}
	
	
	public String getButton() {
		return button;
	}
	public void setButton(String button) {
		this.button = button;
	}
	public int getBulkadded() {
		return bulkadded;
	}
	public void setBulkadded(int bulkadded) {
		this.bulkadded = bulkadded;
	}
	public int getBulkduplicates() {
		return bulkduplicates;
	}
	public void setBulkduplicates(int bulkduplicates) {
		this.bulkduplicates = bulkduplicates;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getValidcount() {
		return validcount;
	}
	public void setValidcount(int validcount) {
		this.validcount = validcount;
	}
}
