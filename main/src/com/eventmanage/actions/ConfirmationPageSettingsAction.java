package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import org.json.JSONObject;

import com.event.beans.ConfirmationPageSettingsData;
import com.event.dbhelpers.ConfirmationPageSettingsDB;
import com.event.dbhelpers.ConfirmationPreviewDB;
import com.event.dbhelpers.CustomAttributesDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.eventbee.general.I18NCacheData;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class ConfirmationPageSettingsAction extends ActionSupport implements Preparable,ValidationAware{
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";
	private String msgType ="confirmationpagesettings";
	private ConfirmationPageSettingsData confirmationPageSettingsData=new ConfirmationPageSettingsData();
	private String type="";
	private String content="";
	private String previewContent="";     
	private String htmlcss="";
    private ArrayList<String> defaultattribs=new ArrayList<String>();
	private ArrayList<Entity> attribs=new ArrayList<Entity>();
	private ArrayList<Entity> selattribs=new ArrayList<Entity>();
	private ArrayList<Entity> buyattribs=new ArrayList<Entity>();
	private ArrayList<Entity> buyselattribs=new ArrayList<Entity>();
	private ArrayList<Entity> optionsList=new ArrayList<Entity>();
	private String purpose="confirmation_page";
	private String val="";
	private String confirmationpagequestionsshow="";
	private boolean configentry=false;
	private String confirmationpageradio="";
	private boolean showconfirmationpagelist = false;
	private String chkbuyerquestions="";
	private String chkattendeequestions="";
	private String jsonData="";
	private String msg="";
	
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	private String templateType="COMPLETED";
	
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
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
	public String getConfirmationpagequestionsshow() {
		return confirmationpagequestionsshow;
	}
	public void setConfirmationpagequestionsshow(
			String confirmationpagequestionsshow) {
		this.confirmationpagequestionsshow = confirmationpagequestionsshow;
	}
	public boolean isShowconfirmationpagelist() {
		return showconfirmationpagelist;
	}
	public void setShowconfirmationpagelist(boolean showconfirmationpagelist) {
		this.showconfirmationpagelist = showconfirmationpagelist;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isConfigentry() {
		return configentry;
	}
	public void setConfigentry(boolean configentry) {
		this.configentry = configentry;
	}
    public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
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
	public ArrayList<String> getDefaultattribs() {
		return defaultattribs;
	}
	public void setDefaultattribs(ArrayList<String> defaultattribs) {
		this.defaultattribs = defaultattribs;
	}
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ConfirmationPageSettingsData getConfirmationPageSettingsData() {
		return confirmationPageSettingsData;
	}
	public void setConfirmationPageSettingsData(ConfirmationPageSettingsData confirmationPageSettingsData) {
		this.confirmationPageSettingsData = confirmationPageSettingsData;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	
	public String getHtmlcss() {
		return htmlcss;
	}
	public void setHtmlcss(String htmlcss) {
		this.htmlcss = htmlcss;
	}
    public String getConfirmationpageradio() {
		return confirmationpageradio;
	}
	public void setConfirmationpageradio(String confirmationpageradio) {
		this.confirmationpageradio = confirmationpageradio;
	} 
    @Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
	}
    
	public String execute(){
		//showStatus=EventDB.getConfigVal(eid,"event.confirmationpagebytype.enable","N");
		//System.out.println("showStatus"+showStatus);
		HashMap<String, String> hm= new HashMap<String,String>();
		type=EventDB.getPowerType(eid);
		String dbi18nLang=I18NCacheData.getI18NLanguage(eid);
		if("rsvp".equalsIgnoreCase(type))
			type="rsvp";
		if("rsvp".equalsIgnoreCase(type)){
			hm.put("purpose", "rsvp_confirmationpage");
			String content=ConfirmationPageSettingsDB.getTemplateContent(hm, dbi18nLang, eid);//ConfirmationPageSettingsDB.getContent(eid,purpose);
			confirmationPageSettingsData.setContent(content);
		}else{
			if("COMPLETED".equals(templateType)){
				hm.put("purpose", "confirmationpage_completed");
				String content=ConfirmationPageSettingsDB.getTemplateContent(hm, dbi18nLang, eid);//ConfirmationPageSettingsDB.getContent(eid,purpose);
				confirmationPageSettingsData.setContent(content);
			}else{
				hm.put("purpose", "confirmationpage_pending");
				String content=ConfirmationPageSettingsDB.getTemplateContent(hm, dbi18nLang, eid);//ConfirmationPageSettingsDB.getContent(eid,purpose);
				confirmationPageSettingsData.setContent(content);
			}
		}
		getConfirmationPageDetails(eid,type);
        chkbuyerquestions=CustomAttributesDB.getQuestionsStatus(eid, "buyer");
        chkattendeequestions=CustomAttributesDB.getQuestionsStatus(eid, "attendee");
		
        return "success";
	}
	
	public String confirmationType(){
		execute();
		JSONObject json=new JSONObject();
		try{
		json.put("content",confirmationPageSettingsData.getContent());
		jsonData=json.toString();
		}catch(Exception e){
			System.out.println("the Exception occured at confirmationType:"+e.getMessage());
		}
		return "confirmationdata";
	}
	
	public String save(){
		if("rsvp".equalsIgnoreCase(type)) type="rsvp";
		HashMap<String,ArrayList<Entity>> buyerattendeeOptions=new HashMap<String,ArrayList<Entity>>(); 
		ArrayList<Entity> buyerList=new ArrayList<Entity>();
		ArrayList<Entity> attendeeList=new ArrayList<Entity>();
		String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
		if("rsvp".equalsIgnoreCase(type))
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP Confirmation Page");
		else
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"300","Confirmation Page");
		// special fee end.
		 content=confirmationPageSettingsData.getContent();
		 ConfirmationPageSettingsDB.updateContent(type,eid,content,templateType);
         updateShowAttendee();
         buyerattendeeOptions=CustomAttributesDB.getBuyerOptionsList(optionsList);
 		 buyerList=buyerattendeeOptions.get("buyermap");
 		 attendeeList=buyerattendeeOptions.get("attendeemap");
	     CustomAttributesDB.saveAttribs(eid, buyerList,attendeeList,purpose);
	     ResourceBundle resourceBundle=I18n.getResourceBundle();
	     msg=resourceBundle.getString("asm.cfm.pge.up.lbl");
	   
	     return "ajaxmsg";
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String reset(){
		
		if("rsvp".equalsIgnoreCase(type)) type="rsvp";
		ConfirmationPageSettingsDB.resetContent(type,eid, templateType);
		return confirmationType();
	}
	
	public String preview(){
		try{
			if("rsvp".equalsIgnoreCase(type)) type="rsvp";
			HashMap<String,ArrayList<Entity>> buyerattendeeOptions=new HashMap<String,ArrayList<Entity>>(); 
			ArrayList<Entity> buyerList=new ArrayList<Entity>();
			ArrayList<Entity> attendeeList=new ArrayList<Entity>();
			buyerattendeeOptions=CustomAttributesDB.getBuyerOptionsList(optionsList);
			buyerList=buyerattendeeOptions.get("buyermap");
			attendeeList=buyerattendeeOptions.get("attendeemap");
	        CustomAttributesDB.saveAttribs(eid, buyerList,attendeeList,"page_preview");
		    content=confirmationPageSettingsData.getContent();
		    htmlcss = ConfirmationPreviewDB.getHTMLCSS(eid);
		    previewContent = ConfirmationPreviewDB.fillConfirmation(eid,content,type,"page");
		  }catch(Exception e){
			System.out.println("Exception occured in preview section: "+eid+" :: "+e.getMessage());
		}
		return "preview";
	}
	public void getConfirmationPageDetails(String eid,String ptype){
    	attribs=CustomAttributesDB.getAttribs(eid,purpose,ptype,"attendee");
		selattribs=CustomAttributesDB.getAddedAttribs(eid,purpose,"attendee");
        buyattribs=CustomAttributesDB.getAttribs(eid,purpose,ptype,"buyer");
		buyselattribs=CustomAttributesDB.getAddedAttribs(eid,purpose,"buyer");
    }
	
	public void updateShowAttendee(){
		System.out.println("the options list is"+optionsList);
		if(optionsList.size()>0)
			confirmationpageradio="Y";   
		else
			confirmationpageradio="N";
		String CONFIG_INFO_INSERT="insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)";
		String CONFIG_KEY_DELETE="delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?";
		DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE,new String[]{eid,"event.confirmationpage.questions.show"});
		DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{"event.confirmationpage.questions.show",confirmationpageradio,eid});
	}
	
	public void deletePreviewQuestions(){
		ConfirmationPreviewDB.deletePQuestions(eid,"page_preview");
	}
	
    public String getPreviewContent() {
		return previewContent;
	}
    public void setPreviewContent(String previewContent) {
		this.previewContent = previewContent;
	}
}
