package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import org.json.JSONObject;

import com.event.beans.RegEmailSettingsData;
import com.event.dbhelpers.ConfirmationPreviewDB;
import com.event.dbhelpers.CustomAttributesDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.RegEmailSettingsDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EmailTemplate;
import com.eventbee.general.I18NCacheData;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class RegEmailSettingsAction extends ActionSupport implements Preparable,ValidationAware{
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";      
	private EmailTemplate emailtemplate=null;
	private RegEmailSettingsData regEmailSettingsData=new RegEmailSettingsData();
	private String msgType ="regemailsettings";
	private String powerType="";
	private String previewContent="";
    private ArrayList<String> defaultattribs=new ArrayList<String>();
	private ArrayList<Entity> attribs=new ArrayList<Entity>();
	private ArrayList<Entity> selattribs=new ArrayList<Entity>();
	private ArrayList<Entity> buyattribs=new ArrayList<Entity>();
	private ArrayList<Entity> buyselattribs=new ArrayList<Entity>();
	private ArrayList<Entity> optionsList=new ArrayList<Entity>();
	private String purpose="confirmation_email";
	private String val="";
	private String confirmationemailshow="";
	private boolean showconfirmationemaillist = false;
	private String confirmationemailradio="";
	private String pdfcheck="N";
	private String pdfcheckpending="N";
	private String chkbuyerquestions="";
	private String chkattendeequestions="";
	private String msg="";
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	private String templateType="COMPLETED";
	private String jsonData="";

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getPdfcheckpending() {
		return pdfcheckpending;
	}

	public void setPdfcheckpending(String pdfcheckpending) {
		this.pdfcheckpending = pdfcheckpending;
	}
	
	public ArrayList<Entity> getBuyattribs() {
		return buyattribs;
	}
	
    public void setBuyattribs(ArrayList<Entity> buyattribs) {
		this.buyattribs = buyattribs;
	}
	public ArrayList<Entity> getBuyselattribs() {
		return buyselattribs;
	}
	public void setBuyselattribs(ArrayList<Entity> buyselattribs) {
		this.buyselattribs = buyselattribs;
	}
	public ArrayList<Entity> getOptionsList() {
		return optionsList;
	}
	public void setOptionsList(ArrayList<Entity> optionsList) {
		this.optionsList = optionsList;
	}
	public String getChkbuyerquestions() {
		return chkbuyerquestions;
	}
	public void setChkbuyerquestions(String chkbuyerquestions) {
		this.chkbuyerquestions = chkbuyerquestions;
	}
	public String getChkattendeequestions() {
		return chkattendeequestions;
	}
	public void setChkattendeequestions(String chkattendeequestions) {
		this.chkattendeequestions = chkattendeequestions;
	}
	public String getPdfcheck() {
		return pdfcheck;
	}
	public void setPdfcheck(String pdfcheck) {
		this.pdfcheck = pdfcheck;
	}
	public String getVal() {
		return val;
	}
	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public void setVal(String val) {
		this.val = val;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public ArrayList<String> getDefaultattribs() {
		return defaultattribs;
	}
	public void setDefaultattribs(ArrayList<String> defaultattribs) {
		this.defaultattribs = defaultattribs;
	}
	public ArrayList<Entity> getAttribs() {
		return attribs;
	}
	public void setAttribs(ArrayList<Entity> attribs) {
		this.attribs = attribs;
	}
	public ArrayList<Entity> getSelattribs() {
		return selattribs;
	}
	public void setSelattribs(ArrayList<Entity> selattribs) {
		this.selattribs = selattribs;
	}
	public String getPowerType() {
		return powerType;
	}
	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}
	public EmailTemplate getEmailtemplate() {
		return emailtemplate;
	}
	public void setEmailtemplate(EmailTemplate emailtemplate) {
		this.emailtemplate = emailtemplate;
	}
	public RegEmailSettingsData getRegEmailSettingsData() {
		return regEmailSettingsData;
	}
	public void setRegEmailSettingsData(RegEmailSettingsData regEmailSettingsData) {
		this.regEmailSettingsData = regEmailSettingsData;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getPreviewContent() {
		return previewContent;
	}
	public void setPreviewContent(String previewContent) {
		this.previewContent = previewContent;
	}
    public boolean isShowconfirmationemaillist() {
		return showconfirmationemaillist;
	}
	public void setShowconfirmationemaillist(boolean showconfirmationemaillist) {
		this.showconfirmationemaillist = showconfirmationemaillist;
	}
    public String getConfirmationemailshow() {
		return confirmationemailshow;
	}
	public void setConfirmationemailshow(String confirmationemailshow) {
		this.confirmationemailshow = confirmationemailshow;
	}
	public String getConfirmationemailradio() {
		return confirmationemailradio;
	}
	public void setConfirmationemailradio(String confirmationemailradio) {
		this.confirmationemailradio = confirmationemailradio;
	}
	
	public String execute(){
		//showStatus=EventDB.getConfigVal(eid,"event.emailbytype.enable","N");
		String dbi18nLang=I18NCacheData.getI18NLanguage(eid);
		HashMap<String, String> hm= new HashMap<String,String>();
    	powerType=EventDB.getPowerType(eid);
		regEmailSettingsData.setPowerType(powerType);
		HashMap<String, String> emailTemplate=new HashMap<String, String>();
		hm.put("isCustomFeature", "Y");
		if("RSVP".equalsIgnoreCase(powerType)){
			hm.put("purpose", "RSVP_CONFIRMATION");
			emailTemplate=RegEmailSettingsDB.getEmailTemplate(hm, dbi18nLang, eid);
			
		}else{
			if("COMPLETED".equals(templateType)){
				hm.put("purpose", "EVENT_REGISTARTION_CONFIRMATION_COMPLETED");
				emailTemplate=RegEmailSettingsDB.getEmailTemplate(hm, dbi18nLang, eid);			
			}else{
				hm.put("purpose", "EVENT_REGISTARTION_CONFIRMATION_PENDING");
				emailTemplate=RegEmailSettingsDB.getEmailTemplate(hm, dbi18nLang, eid);	
			}
		}
		regEmailSettingsData.setTempalteContent(emailTemplate.get("htmlFormat"));
		regEmailSettingsData.setSubject(emailTemplate.get("subjectFormat"));
		String sendtoattendee=EventDB.getConfigVal(eid, "event.sendmailtoattendee", "yes");
		String sendtomgr=EventDB.getConfigVal(eid, "event.sendmailtomgr", "yes");	
		pdfcheck=EventDB.getConfigVal(eid,"event.pdfticket.enable","N");
		pdfcheckpending=EventDB.getConfigVal(eid,"event.pdfticketpending.enable","N");
		
		regEmailSettingsData.setSendToAttendee(sendtoattendee);
		regEmailSettingsData.setSendToManager(sendtomgr);
		regEmailSettingsData.setCc(EventDB.getConfigVal(eid, "registration.email.cc.list", ""));
        getConfirmationEmailDetails(eid,powerType);
        chkbuyerquestions=CustomAttributesDB.getQuestionsStatus(eid, "buyer");
        chkattendeequestions=CustomAttributesDB.getQuestionsStatus(eid, "attendee");
		
        return SUCCESS;
	}
	
	public String confirmationType(){
		execute();
		JSONObject json=new JSONObject();
		try{
		json.put("content",regEmailSettingsData.getTempalteContent());
		json.put("subject",regEmailSettingsData.getSubject());
		jsonData=json.toString();
		}catch(Exception e){
			System.out.println("the Exception occured at confirmationType:"+e.getMessage());
		}
		return "confirmationdata";
	}
	
	public String save(){
		// adding for special fee start
		HashMap<String,ArrayList<Entity>> buyerattendeeOptions=new HashMap<String,ArrayList<Entity>>(); 
		ArrayList<Entity> buyerList=new ArrayList<Entity>();
		ArrayList<Entity> attendeeList=new ArrayList<Entity>();
		String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
		if(regEmailSettingsData.getPowerType().equals("RSVP"))
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP Confirmation Email");
		else
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"300","Confirmation Email");
		// special fee end.
		RegEmailSettingsDB.updateRegEmailSettings(eid, regEmailSettingsData,templateType);
        updateShowAttendee();
        buyerattendeeOptions=CustomAttributesDB.getBuyerOptionsList(optionsList);
		buyerList=buyerattendeeOptions.get("buyermap");
		attendeeList=buyerattendeeOptions.get("attendeemap");
        CustomAttributesDB.saveAttribs(eid, buyerList,attendeeList,purpose);
        ResourceBundle resourceBundle=I18n.getResourceBundle();
 	    msg=resourceBundle.getString("asm.cfm.mil.up.lbl");
        return "ajaxmsg";
	}
	
	public String resetEmailSettings(){
		RegEmailSettingsDB.resetRegEmailSettings(eid,powerType,templateType);
		return confirmationType();
	}
	
	public String preview(){
		try{
			HashMap<String,ArrayList<Entity>> buyerattendeeOptions=new HashMap<String,ArrayList<Entity>>(); 
			ArrayList<Entity> buyerList=new ArrayList<Entity>();
			ArrayList<Entity> attendeeList=new ArrayList<Entity>();
			buyerattendeeOptions=CustomAttributesDB.getBuyerOptionsList(optionsList);
			buyerList=buyerattendeeOptions.get("buyermap");
			attendeeList=buyerattendeeOptions.get("attendeemap");
	        CustomAttributesDB.saveAttribs(eid, buyerList,attendeeList,"email_preview");
			String type = "";
			if(regEmailSettingsData.getPowerType().equals("RSVP")) type = "rsvp";
			String content = regEmailSettingsData.getTempalteContent();
			previewContent = ConfirmationPreviewDB.fillConfirmation(eid,content,type,"email");
		}catch(Exception e){
			System.out.println("Exception occured in preview section: "+eid+" :: "+e.getMessage());
		}
		return "preview";
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
	}
	public void getConfirmationEmailDetails(String eid,String ptype){
    	attribs=CustomAttributesDB.getAttribs(eid,purpose,ptype,"attendee");
		selattribs=CustomAttributesDB.getAddedAttribs(eid,purpose,"attendee");
        buyattribs=CustomAttributesDB.getAttribs(eid,purpose,ptype,"buyer");
		buyselattribs=CustomAttributesDB.getAddedAttribs(eid,purpose,"buyer");
   }
	
	public void updateShowAttendee(){
		
		if(optionsList.size()>0)
			confirmationemailradio="Y";
		else
			confirmationemailradio="N";
		
		String CONFIG_INFO_INSERT="insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)";
		String CONFIG_KEY_DELETE="delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?";
		DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE,new String[]{eid,"event.confirmationemail.questions.show"});
		DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{"event.confirmationemail.questions.show",confirmationemailradio,eid});
	
		DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE,new String[]{eid,"event.pdfticket.enable"});
		DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{"event.pdfticket.enable",pdfcheck,eid});
		DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE,new String[]{eid,"event.pdfticketpending.enable"});
		DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{"event.pdfticketpending.enable",pdfcheckpending,eid});
    }
	
	public void deletePreviewQuestions(){
		ConfirmationPreviewDB.deletePQuestions(eid,"email_preview");
	}
	
    public String getMsgType() {
		return msgType;
	}
    public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
}
