package com.mycommunities.actions;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.event.beans.CustomAttribute;
import com.event.dbhelpers.CustomAttributesDB;
import com.eventbee.beans.Entity;


public class RegistrationAction extends MyCommunitiesWrapper {

	private static final long serialVersionUID = 1960067403151215216L;
	private static Logger log = Logger.getLogger(RegistrationAction.class);
	private CustomAttribute customattribute;
	private List<CustomAttribute> customattributes;
	private String questionJsonData="";
	private String type="text";
	private String msgKey="";
	private ArrayList<Entity> typeList;
	private List<Entity> selectionList;
	private String purpose ="add";
	private String attributeid="-1";
	private List<Entity> requiredlist;
	private List<Entity> optionsList;
	private String refType="CLUB_MEMBER_SIGNUP_PAGE";
		
	public List<Entity> getOptionsList() {
		return optionsList;
	}
	public void setOptionsList(List<Entity> optionsList) {
		this.optionsList = optionsList;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public List<Entity> getRequiredlist() {
		return requiredlist;
	}
	public void setRequiredlist(List<Entity> requiredlist) {
		this.requiredlist = requiredlist;
	}
	public String getAttributeid() {
		return attributeid;
	}
	public void setAttributeid(String attributeid) {
		this.attributeid = attributeid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<Entity> getTypeList() {
		return typeList;
	}
	public void setTypeList(ArrayList<Entity> typeList) {
		this.typeList = typeList;
	}
	public List<Entity> getSelectionList() {
		return selectionList;
	}
	public void setSelectionList(List<Entity> selectionList) {
		this.selectionList = selectionList;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public CustomAttribute getCustomattribute() {
		return customattribute;
	}
	public void setCustomattribute(CustomAttribute customattribute) {
		this.customattribute = customattribute;
	}
	public List<CustomAttribute> getCustomattributes() {
		return customattributes;
	}
	public void setCustomattributes(List<CustomAttribute> customattributes) {
		this.customattributes = customattributes;
	}
	public String getQuestionJsonData() {
		return questionJsonData;
	}
	public void setQuestionJsonData(String questionJsonData) {
		this.questionJsonData = questionJsonData;
	}

	public void prepare() throws Exception {
		try {
			super.prepare();
			log.info("My Communities RegistrationAction prepare method.");
		} catch (Exception e) {

		}
	}
	public String execute(){
		if(groupId==null) groupId="";
		if(!"".equals(groupId)){
		customattributes = CustomAttributesDB.getCustomAttributes(groupId,refType);
		questionJsonData = CustomAttributesDB.getQuestionsInJsonObj(groupId,refType).toString();
		}
		return "success";
	}
	private void populateTypeList(){
		typeList = new ArrayList<Entity>();
		typeList.add(new Entity("text","Small Text"));
		typeList.add(new Entity("textarea","Multiline Text"));
		typeList.add(new Entity("selection","Multiple Choices"));
		selectionList = new ArrayList<Entity>();		
		selectionList.add(new Entity("radio","Radio"));
		selectionList.add(new Entity("checkbox","Check Box"));
		selectionList.add(new Entity("dropdown","Drop Down List"));
	}
	private void populateRequiredList(){
		requiredlist = new ArrayList<Entity>();
		requiredlist.add(new Entity("Required","Mandatory"));
		requiredlist.add(new Entity("Optional","Optional"));
	}
	private void populateRefData(){
		populateTypeList();
		populateRequiredList();
	}
	public String addQuestion(){
		try{
			purpose = "add";
			customattribute = new CustomAttribute();			
			populateTypeList();
			populateRequiredList();
			return "addquestion";
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return "error";
	}
	public String saveQuestion(){
		if(groupId==null) groupId="";
		if(attributeid==null) attributeid="";
		try{	
			System.out.println("Action saveQuestion method: "+type);
			if(!type.equals("selection")){
				customattribute.setQtype(type);
				optionsList = null;
				
			}
			
			if(optionsList!=null){
				System.out.println("Saving options count: "+optionsList.size());
				customattribute.setOptionsList(optionsList);
			}
			CustomAttributesDB cadb = new CustomAttributesDB();
			log.debug("name"+customattribute.getName()+"attribId"+attributeid+"purposeinaction"+purpose);
			if(!"".equals(attributeid) && !"".equals(groupId))
			attributeid = cadb.saveQuestion(groupId,customattribute,attributeid,purpose,refType);
			msgKey = "question.saved";
		return "saved";
		}catch(Exception ex){
			log.error("Exception in saveQuestion: "+ex);
		}
		return "error";
	}
	public String editQuestion(){
		if(groupId==null) groupId="";
		if(attributeid==null) attributeid="";
		try{
			purpose = "edit";
			customattribute = new CustomAttribute();
			if(!"".equals(attributeid) && !"".equals(groupId))
			customattribute = CustomAttributesDB.getCustomAttribute(groupId,attributeid,customattribute,refType);
			if(!customattribute.getQtype().equals("text") && !customattribute.getQtype().equals("textarea"))
				type = "selection";
			else
				type = customattribute.getQtype();
			populateTypeList();
			populateRequiredList();
			return "edit";
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return "error";
	}
	public String deleteQuestion(){
		if(groupId==null) groupId="";
		if(attributeid==null) attributeid="";
		try{
			if(!"".equals(attributeid) && !"".equals(groupId)){
			boolean status = CustomAttributesDB.deleteQuestion(groupId,attributeid,refType);
			}
			msgKey = "question.deleted";
			return "deleted";
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return "error";
	}
}
