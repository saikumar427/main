package com.mymarketing.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.membertasks.actions.MemberTasksWrapper;
import com.mymarketing.beans.MailListData;
import com.mymarketing.beans.TemplateData;
import com.mymarketing.dbhelpers.MarketingDB;
import com.mymarketing.helpers.JsonBuilderHelper;


public class MarketingSummaryAction extends MemberTasksWrapper{

	private String templateJson="";
	private TemplateData templateData=new TemplateData();
	private String templateid="";
	private String mailingListJson="";
	private String listid="";
	private MailListData maildata=new MailListData();
	private String unsubscribeCount="";
	private String email="";
	private String bulkmails="";
	private String bulkmailsright="";
	private String validcount="";
	private String invalidcount="";
	private String addedmailscount="";
	private String dupliactemailscount="";
	private String jSonSumary="";
	private List<String> filterdomains=new ArrayList<String>();
	
	
	public List<String> getFilterdomains() {
		return filterdomains;
	}

	public void setFilterdomains(List<String> filterdomains) {
		this.filterdomains = filterdomains;
	}

	public String getBulkmails() {
		return bulkmails;
	}

	public void setBulkmails(String bulkmails) {
		this.bulkmails = bulkmails;
	}

	public String getJSonSumary() {
		return jSonSumary;
	}

	public void setJSonSumary(String sonSumary) {
		jSonSumary = sonSumary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getUnsubscribeCount() {
		return unsubscribeCount;
	}

	public void setUnsubscribeCount(String unsubscribeCount) {
		this.unsubscribeCount = unsubscribeCount;
	}

	public String getListid() {
		return listid;
	}

	public void setListid(String listid) {
		this.listid = listid;
	}

	public String getMailingListJson() {
		return mailingListJson;
	}

	public void setMailingListJson(String mailingListJson) {
		this.mailingListJson = mailingListJson;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public TemplateData getTemplateData() {
		return templateData;
	}

	public void setTemplateData(TemplateData templateData) {
		this.templateData = templateData;
	}

	public String getTemplateJson() {
		return templateJson;
	}

	public void setTemplateJson(String templateJson) {
		this.templateJson = templateJson;
	}

	public void prepare() throws Exception {
		try {
			super.prepare();
			
		} catch (Exception e) {
			
		}
	}
	
	public String execute(){
		ArrayList templateList = MarketingDB.getTemplates(userId);
		JSONObject tempjs=JsonBuilderHelper.getTemplateListJson(templateList);
		templateJson=tempjs.toString();
		ArrayList mailingList=MarketingDB.getMailingList(userId);
		JSONObject mailjs=JsonBuilderHelper.getMailingListJson(mailingList);
		mailingListJson=mailjs.toString();
		return SUCCESS;
	}
	
	public String addTemplate(){
		return "addtemplate";
	}
	
	public String editTemplate(){
		templateData=MarketingDB.getTemplateData(templateid);
		return "addtemplate";
	}
	
	public String saveTemplate(){
		boolean status = validateInputData();
		if(status)
			MarketingDB.saveTemplate(templateData, userId);
		else
			return "inputerror";
		return "ajaxmsg";
	}
	
	public String testEmail(){
		//populateDescriptionTypes();
		return "testemail";
	}
	
	public String sendTestEmail(){
		if("".equals(templateData.getMailTo().trim().toLowerCase())){
			addFieldError("templateData.mailTo", "Email IDs field is empty");
			return "inputerror";
		}else if(!isValidEmail(templateData.getMailTo().trim().toLowerCase())){
			addFieldError("templateData.mailTo", "Invalid email format" );
			return "inputerror";
		}
		MarketingDB.getDetailsForTestMail(templateid,templateData);
	    String response = MarketingDB.sendTestMail(templateData, userId);
		return "ajaxmsg";
	}
	
	public String createMailList(){
		filterdomains=MarketingDB.getFilterDomains();
		return "popup";
	}
	
	public String editMailList(){
		MarketingDB.getMailListData(listid,maildata);
		filterdomains=MarketingDB.getFilterDomains();
		bulkmails=maildata.getEmail();
		return "popup";
	}
	
	public String deleteMailList(){
		MarketingDB.deleteMailList(listid);
		return "success";
	}
	
	public String deleteTemplate(){
		MarketingDB.deleteTemplate(templateid);
		return "success";
	}
	
	public String saveMailList(){
		boolean status=validateMailListInputData();
		if(status){
		maildata.setListid(listid);
		listid = MarketingDB.saveMailListData(maildata,userId);
		String existmanagerstatus=maildata.getExistmanagerstatus();
		MarketingDB.bulkUploadinsert(listid, bulkmails,filterdomains,existmanagerstatus);
		JSONObject summaryJSON=new JSONObject();
		try{
			summaryJSON.put("validcount", validcount);
			summaryJSON.put("invalidcount",invalidcount);
			summaryJSON.put("duplicates", dupliactemailscount);
			summaryJSON.put("invalidlines", bulkmailsright);
			summaryJSON.put("totallines", addedmailscount);
			summaryJSON.put("listid", listid);
			jSonSumary=summaryJSON.toString();
		}
		catch (Exception e) {
				System.out.println("Exception caught "+e.getMessage());
		}
		return "bulksummary";
		}else
			return "inputerror";
		/*boolean status = validateMailListInputData();
		if(status){
			maildata.setListid(listid);
			listid = MarketingDB.saveMailListData(maildata);
			
		ArrayList<MailListData> members=new ArrayList<MailListData>();
		JSONObject summaryJSON=new JSONObject();
		System.out.println("bulkmails are:"+bulkmails);
		StringTokenizer lines=new StringTokenizer(bulkmails,"\n");
		int i=0,totalValidEmails=0,totalInValidEmails=0,duplicates=0;
		String temp=null;
		String invalidEmails="";
		String emailadd="";
		System.out.println("lines are:"+lines);
		while(lines.hasMoreTokens()){
			i++;
			//maildata=new MailListData();
			boolean isValidline=false;
			temp=lines.nextToken();
			System.out.println("temp is:"+temp);
			StringTokenizer columns=new StringTokenizer(temp,",");
			System.out.println("columns are:"+bulkmails);
			int columncount=columns.countTokens();
			for(int k=1;k<=columncount;k++){
				emailadd=columns.nextToken().trim().toLowerCase();
				if(!isValidEmail(emailadd)){
					totalInValidEmails++;
					invalidEmails=invalidEmails+emailadd+"\n";
				}else {
					totalValidEmails++;
					if(!maildata.getEmails().contains(emailadd))
						maildata.getEmails().add(emailadd);
				}
			}
		}
		duplicates=MarketingDB.bulkUploadinsert(listid, maildata);
		duplicates+=totalValidEmails-maildata.getEmails().size();
		try{
			summaryJSON.put("validcount", totalValidEmails);
			summaryJSON.put("invalidcount",totalInValidEmails);
			summaryJSON.put("duplicates", duplicates);
			summaryJSON.put("invalidlines", invalidEmails);
			summaryJSON.put("totallines", totalValidEmails+totalInValidEmails);
			summaryJSON.put("listid", listid);
			jSonSumary=summaryJSON.toString();
			bulkmails=invalidEmails;
		}
		catch (Exception e) {
				System.out.println("Exception caught "+e.getMessage());
		}
		
		return "bulksummary";
		
		}else
			return "inputerror";
		*/
	}
	
	
	
	public String unsubscribe(){
		unsubscribeCount = MarketingDB.unsubscribeMailListCount();
		return "unsubscribe";
	}
	
	public String saveUnsubscribeEmail(){
		boolean status = validateUnsubscribeInputData();
		if(status){
			MarketingDB.saveUnsubscribeEmail(email);
			return "ajaxmsg";
		}else 
			return "inputerror";
	}
	
	public boolean isValidEmail(String email){
	    Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	    Matcher m = p.matcher(email);
	    boolean matchFound = m.matches();
	    if (matchFound)
	      return true;
	    else
	      return false;
	}
	
	public boolean isValidFromEmail(String email){
		Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	    Matcher m = p.matcher(email);
	    boolean matchFound = m.matches();
	    if (matchFound)
	      return true;
	    else
	      return false;
	}
	
	public boolean validateInputData() {
		
		if ("".equals(templateData.getName().trim())) {
			addFieldError("templateData.name", "Template Name is empty");
		}
		if ("".equals(templateData.getSubject().trim())) {
			addFieldError("templateData.subject", "Subject is empty");
		}
		if ("".equals(templateData.getFrom().trim())) {
			addFieldError("templateData.from", "From is empty");
		}else{
			if(!isValidFromEmail(templateData.getFrom().trim()))
		    	addFieldError("templateData.from", "From is not a valid email address");
			
		}
		if ("".equals(templateData.getContent().trim())) {
			addFieldError("templateData.content", "Content is empty");
		}
		if (!getFieldErrors().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean validateMailListInputData(){
		if("".equals(maildata.getName().trim())){
			addFieldError("maildata.email", "Name is empty" );
		}
		/*if("".equals(maildata.getDescription().trim())) 
		{
			addFieldError("maildata.description", "Description  is empty");
		}*/
		if("".equals(bulkmails.trim())) {
			addFieldError("maildata.email", "Email IDs is empty");
	        }
		/*else if(!isValidEmail(maildata.getEmail().trim()))
				addFieldError("maildata.email", "Invalid Email format" );*/
		
		if (!getFieldErrors().isEmpty()) {
			return false;
		}
		return true;
		
	}
	
	public boolean validateUnsubscribeInputData(){
		if(email.trim().equals("")){
			addFieldError("email", "Please enter email" );
		}
		if (!getFieldErrors().isEmpty()) {
			return false;
		}
		return true;
		
	}

	public MailListData getMaildata() {
		return maildata;
	}

	public void setMaildata(MailListData maildata) {
		this.maildata = maildata;
	}

	public String getBulkmailsright() {
		return bulkmailsright;
	}

	public void setBulkmailsright(String bulkmailsright) {
		this.bulkmailsright = bulkmailsright;
	}

	public String getValidcount() {
		return validcount;
	}

	public void setValidcount(String validcount) {
		this.validcount = validcount;
	}

	public String getInvalidcount() {
		return invalidcount;
	}

	public void setInvalidcount(String invalidcount) {
		this.invalidcount = invalidcount;
	}

	public String getAddedmailscount() {
		return addedmailscount;
	}

	public void setAddedmailscount(String addedmailscount) {
		this.addedmailscount = addedmailscount;
	}

	public String getDupliactemailscount() {
		return dupliactemailscount;
	}

	public void setDupliactemailscount(String dupliactemailscount) {
		this.dupliactemailscount = dupliactemailscount;
	}

}
