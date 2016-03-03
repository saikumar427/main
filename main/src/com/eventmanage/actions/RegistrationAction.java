package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.event.beans.CustomAttribute;
import com.eventbee.beans.Entity;
import com.event.dbhelpers.CustomAttributesDB;
import com.event.dbhelpers.DiscountsDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.helpers.CustomAttribsJSONBuilderHelper;
import com.event.helpers.I18n;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class RegistrationAction extends ActionSupport implements Preparable,ValidationAware{
	private static Logger log = Logger.getLogger(DiscountsDB.class);
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";
	private String qlevel="";
	private String type="text";
	private ArrayList<Entity> typeList;
	private String msgKey="";
	private String option ="text";
	private List<Entity> optionsList;
	private List<CustomAttribute> customattributes;   
	private CustomAttribute customattribute;
	private String attributeid="-1";
	private String attributesetid="";
	private String swapattributeid="-1";
	private List<Entity> requiredlist;
	private List<Entity> selectionList;
	private String purpose ="add";
	private String qname = "";
	private List alltickets;
	private List seltickets;
	private List<Entity> collectbuyerlist;
	private String collectbuyer="false";
	private List<Entity> rsvpStatusOptionsList;
	private List selRsvpStatusOptions; 
	private String powertype="";
	private String questionJsonData="";
	private String transcationQuestionsJsonData="";
	private String ticketQuestionJsonData="";
	private String responseQuestionJsonData="";
	private String refType="EVENT";
	private String displayPhoneYN="Y";
	private ArrayList selticketsOption;
	private ArrayList reqtickets;
	private ArrayList reqresponses;
	private ArrayList selresponsesOption;
	private List gentickets;
	private List voltickets;
	private String multiple="";
	private String customOptions=""; 
	private String phoneRequired="";
	private String questionPostions="";
	private String questionRequired="";
	private HashMap<String,String> multipleOptionsMap=new HashMap<String, String>();
	private ArrayList multipleOptionsList=new ArrayList();
	
	private String subquestionsinfo="{}";
	private ArrayList attribOptions=new ArrayList();
	private CustomAttribute subCustomAttribute;
	private String optionId="";
	private String attribOptionId="";
	private String subQuestionsJsonData="";
	private String getDBData="no";
	private String optionVal ="";
	private String ticketsJson="";
	private String questionTicketJson="";
	private String attribId="";
	private String ticketId="";
	private String isCheck="";
	private String ticketQnJson="";
	
	public HashMap<String, String> getMultipleOptionsMap() {
		return multipleOptionsMap;
	}
	public void setMultipleOptionsMap(HashMap<String, String> multipleOptionsMap) {
		this.multipleOptionsMap = multipleOptionsMap;
	}
	public ArrayList getMultipleOptionsList() {
		return multipleOptionsList;
	}
	public void setMultipleOptionsList(ArrayList multipleOptionsList) {
		this.multipleOptionsList = multipleOptionsList;
	}
	public String getQuestionRequired() {
		return questionRequired;
	}
	public void setQuestionRequired(String questionRequired) {
		this.questionRequired = questionRequired;
	}
	public String getQuestionPostions() {
		return questionPostions;
	}
	public void setQuestionPostions(String questionPostions) {
		this.questionPostions = questionPostions;
	}
	public String getPhoneRequired() {
		return phoneRequired;
	}
	public void setPhoneRequired(String phoneRequired) {
		this.phoneRequired = phoneRequired;
	}
	public String getCustomOptions() {
		return customOptions;
	}
	public void setCustomOptions(String customOptions) {
		this.customOptions = customOptions;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	
	public List getGentickets() {
		return gentickets;
	}
	public void setGentickets(List gentickets) {
		this.gentickets = gentickets;
	}
	public List getVoltickets() {
		return voltickets;
	}
	public void setVoltickets(List voltickets) {
		this.voltickets = voltickets;
	}
	public ArrayList getReqresponses() {
		return reqresponses;
	}
	public void setReqresponses(ArrayList reqresponses) {
		this.reqresponses = reqresponses;
	}
	public ArrayList getReqtickets() {
		return reqtickets;
	}
	public void setReqtickets(ArrayList reqtickets) {
		this.reqtickets = reqtickets;
	}
	public ArrayList getSelticketsOption() {
		return selticketsOption;
	}
	public void setSelticketsOption(ArrayList selticketsOption) {
		this.selticketsOption = selticketsOption;
	}
	public String getDisplayPhoneYN() {
		return displayPhoneYN;
	}
	public void setDisplayPhoneYN(String displayPhoneYN) {
		this.displayPhoneYN = displayPhoneYN;
	}
	public String getQuestionJsonData() {
		return questionJsonData;
	}
	public void setQuestionJsonData(String questionJsonData) {
		this.questionJsonData = questionJsonData;
	}
	public List getSelRsvpStatusOptions() {
		return selRsvpStatusOptions;
	}
	public void setSelRsvpStatusOptions(List selRsvpStatusOptions) {
		this.selRsvpStatusOptions = selRsvpStatusOptions;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public List<Entity> getSelectionList() {
		return selectionList;
	}
	public void setSelectionList(List<Entity> selectionList) {
		this.selectionList = selectionList;
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
	public List<Entity> getOptionsList() {
		return optionsList;
	}
	public void setOptionsList(List<Entity> optionsList) {
		this.optionsList = optionsList;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public ArrayList<Entity> getTypeList() {
		return typeList;
	}
	public void setTypeList(ArrayList<Entity> typeList) {
		this.typeList = typeList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	
	
	public String populate(){
		
		return "populate";
	}
	public String execute(){      
		
		//displayPhoneYN="N";
		getTransactionQnData();
		getTicketQnData();
		//HashMap basicProfileSettingsMap=CustomAttributesDB.getTransactionLevelBaseProfileSettings(eid);
		
		return "success";
	}
	
	public String getTransactionQnData(){
		customattributes = CustomAttributesDB.getTransactionLevelCustomAttribs(eid,"EVENT");
		HashMap basicProfileSettingsMap= CustomAttributesDB.getTransactionLevelBaseProfileSettings(eid);
		transcationQuestionsJsonData =CustomAttribsJSONBuilderHelper.getTransactionLevelJsonObj(customattributes,basicProfileSettingsMap,EventDB.getPowerType(eid)).toString();
		customattribute = new CustomAttribute();
		System.out.println("basicProfileSettingsMap:"+basicProfileSettingsMap);
		String phonedisplaystatus=(String)basicProfileSettingsMap.get("phone");
		if("Hide".equals(phonedisplaystatus)){
			displayPhoneYN="N";
			customattribute.setIsRequired("Optional");
		}else{
			displayPhoneYN="Y";
			phonedisplaystatus=("Y".equals(phonedisplaystatus))?"Required":"Optional";
			customattribute.setIsRequired(phonedisplaystatus);
		}
		return "refreshTrData";
	}
	
	public String getTicketQnData(){
		HashMap basequestionTicketsMap = new HashMap();
		basequestionTicketsMap.put("fname", CustomAttributesDB.getTicketLevelBaseProfileSettings(eid,"fname"));
		basequestionTicketsMap.put("lname", CustomAttributesDB.getTicketLevelBaseProfileSettings(eid,"lname"));
		basequestionTicketsMap.put("email", CustomAttributesDB.getTicketLevelBaseProfileSettings(eid,"email"));
		basequestionTicketsMap.put("phone", CustomAttributesDB.getTicketLevelBaseProfileSettings(eid,"phone"));
		/*alltickets=EventDB.getAllTypesOfTickets(eid);
		//customattributes = CustomAttributesDB.getTicketLevelCustomAttribs(eid,"EVENT");
		List<CustomAttribute> rsvpcustomattributes = CustomAttributesDB.getResponseLevelCustomAttribs(eid,"EVENT");
		System.out.println("qcount: "+rsvpcustomattributes.size());
		ticketQuestionJsonData =CustomAttribsJSONBuilderHelper.getTicketLevelJsonObj(customattributes, basequestionTicketsMap, eid,alltickets).toString();*/
		customattributes = CustomAttributesDB.getTicketLevelCustomAttribs(eid,"EVENT");
		responseQuestionJsonData =CustomAttribsJSONBuilderHelper.getResponseLevelJsonObj(customattributes,  eid).toString();
		ticketsJson=EventDB.getTicketsJson(eid).toString();
		questionTicketJson=CustomAttributesDB.getCustomAttribTickets(eid, basequestionTicketsMap).toString();
		JSONObject json=new JSONObject();
		try{
			json.put("responseQuestionJsonData",new JSONObject(responseQuestionJsonData));
			json.put("questionTicketJson",new JSONObject(questionTicketJson));
			ticketQnJson=json.toString();
			System.out.println("for getTicketQnData ticketQnJson: "+ticketQnJson);
		}catch(Exception e){
			System.out.println("Exception getTicketQnData: ERROR:: "+e.getMessage());
		}
		
		//System.out.println("questionTicketJson: "+questionTicketJson);
		return "refreshTktData";
	}
	
	public String addQuestion(){
		try{
			System.out.println("addQuestion powertype: "+powertype);
			purpose = "add";
			customattribute = new CustomAttribute();			
			populateTypeList();
			populateRequiredList();
			if(powertype.equals("RSVP")){
				//populateRsvpAttribs();
				//selRsvpStatusOptions = new ArrayList();
			}else{
				
			//questionSettings();
			
			}
			
			JSONObject msg=new JSONObject();
			msg.put("multilpledata","");
			customOptions=msg.toString();
			
			if("transaction".equals(qlevel))
			return "addquestion";
			else{
				if(!"RSVP".equals(powertype)){
					gentickets = EventDB.getGeneralTickets(eid);
					CustomAttributesDB cadb = new CustomAttributesDB();
					seltickets = cadb.getCustomQuestionTickets(eid,attributeid);
				}
				return "addtktquestion";
			}
		}catch(Exception ex){
			System.out.println("error"+ex.getMessage());
			log.error("Exception: "+ex);
		}
		return "error";
	}
	public String saveQuestion(){
		if(!validateInputData()) return "inputerror";
		try{
			System.out.println("saveQuestion powertype: "+powertype+" subquestionsinfo: "+subquestionsinfo);
			// adding for special fee start
			String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
			if(powertype.equals("RSVP"))
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP Questions");
			else
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"300","Questions");
			// special fee end.
			
			if(!type.equals("selection")){
				customattribute.setQtype(type);
				optionsList = null;
			}else{
				optionsList=new ArrayList<Entity>();
				if("single".equals(customattribute.getQtype()) && "multiple".equals(multiple)){
					customattribute.setQtype("checkbox");
				}else if("single".equals(customattribute.getQtype())){
					customattribute.setQtype("radio");
				}else if("pulldown".equals(customattribute.getQtype())){
					customattribute.setQtype("select");
				}
				
				for(int i=0;i<multipleOptionsList.size();i++){
					String val=(String)multipleOptionsList.get(i);
					if(!"".equals(multipleOptionsMap.get(val))){
						if("edit".equals(purpose)){
							if(Integer.parseInt(val)>99999)
								optionsList.add(new Entity("",multipleOptionsMap.get(val)));
							else optionsList.add(new Entity(val,multipleOptionsMap.get(val)));
						}else 
							optionsList.add(new Entity(i+"",multipleOptionsMap.get(val)));
					}
					
				}
			}
			//System.out.println("the option list::"+optionsList);
			if(optionsList!=null){
				customattribute.setOptionsList(optionsList);
			}
			CustomAttributesDB cadb = new CustomAttributesDB();
			if("-4".equals(attributeid)){
				//no need to save question in custom attribs table
			}else{
				attributeid = cadb.saveQuestion(eid,customattribute,attributeid,purpose,refType);
				saveSubQuestions(eid,customattribute,attributeid,purpose,refType,cadb);
			}
			
			if("transaction".equals(qlevel)){
				if("-4".equals(attributeid)){
					String isReq="N";
					if("Required".equals(customattribute.getIsRequired())) isReq="Y";
					cadb.savePhoneQuestionSettings(eid, displayPhoneYN, isReq);
				}else{
					cadb.saveQuestionSettings(eid, attributeid, "true", null,refType);
				}
				
			}
			else{
				if("RSVP".equals(powertype)){
					cadb.saveRSVPQuestionSettings(eid,attributeid,refType);
				}else{
					cadb.saveQuestionSettings(eid, attributeid, "false", seltickets,refType);
				}
			}
			msgKey = "question.saved";
			return "saved";
		}catch(Exception ex){
			log.error("Exception in saveQuestion: "+ex);
		}
		return "error";
	}
	
	/*public String updateCustomAttribTickets(){
		CustomAttributesDB.updateCustomAttribTickets(eid, attribId, ticketId, isCheck);
		return "updatetickets";
	}
	
	public String updateBaseProfileTickets(){
		System.out.println("updateBaseProfileTickets: "+eid);
		CustomAttributesDB.updateBaseProfileTickets(eid, attribId, ticketId, isCheck);
		return "updatetickets";
	}*/
	public void saveSubQuestions(String eid, CustomAttribute customattribute, String attributeid, String purpose,String refType,CustomAttributesDB cadb){
		try{
			attributesetid=customattribute.getAttribsetid();
			List<Entity> newoptionlist = customattribute.getOptionsList();
			System.out.println("newoptionlist.size:: "+newoptionlist.size()+" attribOptions.size: "+attribOptions.size());
			if(newoptionlist!=null && newoptionlist.size()>0 && !attribOptions.isEmpty() && attribOptions.size()>0){
				JSONObject jObject  = new JSONObject(subquestionsinfo);
				StringBuffer qn_option_for_subqn= new StringBuffer();
				//int subQnsCount=0;
				for(int i=0;i<attribOptions.size()&&jObject.length()>0;i++){
					try{
					boolean swap=false;
					boolean delete=false;
					String val=(String)attribOptions.get(i);
					String option = newoptionlist.get(i).getKey();
					JSONArray subquns=new JSONArray();
					if(jObject.has(val)) subquns= (JSONArray) jObject.get(val);
					StringBuffer comma_subquns = new StringBuffer();
					for(int j=0;j<subquns.length();j++){
						try{
						CustomAttribute subCustomattribute=new CustomAttribute();
						List<Entity> subOptionsList;
						String subAttributeId="";
						subAttributeId=subquns.getJSONObject(j).getString("attribid");
						
						if(!"".equals(subAttributeId) && subquns.getJSONObject(j).has("deletestatus") && subquns.getJSONObject(j).getBoolean("deletestatus")) {
							delete=subquns.getJSONObject(j).getBoolean("deletestatus");
							CustomAttributesDB.deleteQuestion(eid,subAttributeId,"EVENT");
							continue;
						}
						subCustomattribute.setName(subquns.getJSONObject(j).getString("name"));
						subCustomattribute.setSize(subquns.getJSONObject(j).getString("size"));
						subCustomattribute.setRows(subquns.getJSONObject(j).getString("rows"));
						subCustomattribute.setColumns(subquns.getJSONObject(j).getString("columns"));
						subCustomattribute.setIsRequired(subquns.getJSONObject(j).getString("reqoptional"));
						if(!subquns.getJSONObject(j).getString("type").equals("selection")){
							subCustomattribute.setQtype(subquns.getJSONObject(j).getString("type"));
							subOptionsList = null;
						}else{
							subOptionsList=new ArrayList<Entity>();
							
							if("single".equals(subquns.getJSONObject(j).getString("qtype"))){
								if(subquns.getJSONObject(j).getBoolean("multiple"))
									subCustomattribute.setQtype("checkbox");
								else subCustomattribute.setQtype("radio");
							}else 
								subCustomattribute.setQtype("select");
							
							JSONArray subQunOptions= (JSONArray) subquns.getJSONObject(j).get("answers");
							System.out.println("subQunOptions: "+subQunOptions);
							for(int k=0;k<subQunOptions.length();k++){
								String key=subQunOptions.getJSONObject(k).getString("key");
								String value=subQunOptions.getJSONObject(k).getString("value");
								
								if(!"".equals(value)){
									if(Integer.valueOf(key)>99999)
										subOptionsList.add(new Entity("",value));
									else subOptionsList.add(new Entity(key,value));
								}
								
							}
						}
						if(subOptionsList!=null){
							subCustomattribute.setOptionsList(subOptionsList);
						}
						
						subCustomattribute.setSubQuestionOf(attributeid+"_"+option);
						String subpurpose="add";
						if("edit".equals(purpose) && !"".equals(subAttributeId)){
							subpurpose="edit";
							swap=true;
						}
						
						subCustomattribute.setSubQuestion(true);
						subAttributeId = cadb.saveQuestion(eid,subCustomattribute,subAttributeId,subpurpose,refType);
						
						if("transaction".equals(qlevel))
							cadb.saveBuyerSubQuestionSettings(eid, subAttributeId);
						
						if("".equals(comma_subquns.toString()))
							comma_subquns.append(subAttributeId);
						else comma_subquns.append(","+subAttributeId);
					}catch(Exception e){
						System.out.println("Exception saveSubQuestions Error in option subquestions loop: "+e.getMessage());
					}
					}
					
					if(comma_subquns.length()>0){
						cadb.updateOptionSubQuestions(comma_subquns.toString(),String.valueOf(option),attributeid,attributesetid);
						if(swap)
							cadb.swapSubQuestions(comma_subquns.toString(), attributesetid);
					}else if(delete)
						cadb.updateOptionSubQuestions(comma_subquns.toString(),String.valueOf(option),attributeid,attributesetid);
					
				}catch(Exception e){
					System.out.println("Exception saveSubQuestions Error in attribOptions loop: "+e.getMessage());
				}
				}
				cadb.updateSubQnsCount(attributeid,attributesetid);
			}
		}catch(Exception ex){
			log.error("Exception in saveSubQuestions ERROR: "+ex.getMessage());
		}
	}
	
	
	public String savePhoneSettings(){
		CustomAttributesDB cadb = new CustomAttributesDB();
		System.out.println("save phone::"+displayPhoneYN+":::"+phoneRequired);
		String isReq="N";
		if("Required".equals(phoneRequired)) isReq="Y";
		cadb.savePhoneQuestionSettings(eid, displayPhoneYN, isReq);
		return "saved";
	}
	
	
	
	public String editQuestion(){
		try{
			System.out.println("editQuestion powertype: "+powertype);
			if("-4".equals(attributeid)) return editPhoneQuestion();
			purpose = "edit";
			customattribute = new CustomAttribute();
			customattribute = CustomAttributesDB.getCustomAttribute(eid,attributeid,customattribute,refType);
			if(!customattribute.getQtype().equals("text") && !customattribute.getQtype().equals("textarea")){
				type = "selection";
				if("checkbox".equals(customattribute.getQtype()))
					multiple="multiple";
			}else
				type = customattribute.getQtype();
			
			populateTypeList();
			populateRequiredList();
			JSONObject msg=new JSONObject();
			
			JSONObject json=new JSONObject();
			JSONArray jsonarray=new JSONArray();
			if("selection".equals(type)){
			optionsList=customattribute.getOptionsList();
			for(int i=0;i<optionsList.size();i++){
				json=new JSONObject();
				json.put("key",optionsList.get(i).getKey());
				json.put("value",optionsList.get(i).getValue());
				jsonarray.put(json);
			}
			
			msg.put("multilpledata",jsonarray);
			}else{
				msg.put("multilpledata","");
			}
			customOptions=msg.toString();
			if("ticket".equals(qlevel)){
				
				if("RSVP".equals(powertype)){
					
				}else{
					gentickets = EventDB.getGeneralTickets(eid);
					CustomAttributesDB cadb = new CustomAttributesDB();
					seltickets = cadb.getCustomQuestionTickets(eid,attributeid);
				}
					return "addtktquestion";
				}
			return "edit";
		}catch(Exception ex){
			System.out.println("Exception: "+ex.getMessage());
			log.error("Exception: "+ex);
		}
		return "error";
	}
	public String editPhoneQuestion(){
		try{
			purpose = "edit";
			customattribute = new CustomAttribute();
			HashMap basicProfileSettingsMap=CustomAttributesDB.getTransactionLevelBaseProfileSettings(eid);
			String phonedisplaystatus=(String)basicProfileSettingsMap.get("phone");
			if("Hide".equals(phonedisplaystatus)){
				displayPhoneYN="N";
				customattribute.setIsRequired("Optional");
			}else{
				displayPhoneYN="Y";
				phonedisplaystatus=("Y".equals(phonedisplaystatus))?"Required":"Optional";
				customattribute.setIsRequired(phonedisplaystatus);
			}
			
			populateRequiredList();
			
			return "edit";
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return "error";
	}
	
	public String applyTicketsToQuestion(){
		try{
			purpose = "apply";
			if(powertype.equals("RSVP")){
				populateRsvpAttribs();
				rsvpQuestionSettings();
				selRsvpStatusOptions = CustomAttributesDB.getRsvpQuestionsettings(eid,attributeid);
				return "assignresponses";
			}else{
				questionSettings();
				
				return "assigntickets";
			}
			
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return "error";
	}
	
	public String deleteQuestionConfirm(){
		try{
			String responseexist = CustomAttributesDB.deleteQuestionConfirm(eid,attributeid);
			if("yes".equals(responseexist))
				msgKey = "exist";
			else msgKey = "not";
			return "ajaxmsg";
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return "error";
	}
	
	public String deleteQuestion(){
		try{
			boolean status = CustomAttributesDB.deleteQuestion(eid,attributeid,refType);
			msgKey = "question.deleted";
			return "deleted";
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return "error";
	}
	
	
	public String deleteBaseQuestionTickets(){
		System.out.println("into delete base questions::"+attributeid);
		CustomAttributesDB.deleteBaseQuestionTickets(eid,attributeid);
		return "deleted";
	}
	
	
	public String swapQuestions(){
		try{
			boolean status = CustomAttributesDB.swapQuestions(eid,qlevel,questionPostions);
			msgKey = "questions.swapped";
			return "saved";
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return "error";
	}
	public String defaultQuestionSettings(){
		try{
			alltickets = new ArrayList();
			alltickets=EventDB.getAllTypesOfTickets(eid);
			CustomAttributesDB cadb = new CustomAttributesDB();
			seltickets = cadb.getBaseProfileTickets(eid);						
			return "defaultquestionsettings";
		}catch (Exception e) {
			// TODO: handle exception
		}
		return "error";
	}
	public String questionSettings(){
		try{
			//alltickets = EventDB.getAllTypesOfTickets(eid);
			gentickets = EventDB.getGeneralTickets(eid);
			voltickets = EventDB.getVolumeTickets(eid);
			CustomAttributesDB cadb = new CustomAttributesDB();
			if(attributeid.indexOf("-")>-1){
				
				seltickets=new ArrayList();
				reqtickets=new ArrayList();
				ArrayList selectedTicketsWithSettings=cadb.getTicketLevelBaseProfileSettings(eid,attributeid);
				System.out.println("selectedTicketsWithSettings::"+selectedTicketsWithSettings);
				for(int i=0;i<selectedTicketsWithSettings.size();i++){
					seltickets.add(((HashMap)selectedTicketsWithSettings.get(i)).get("tid"));
					if("Y".equals((String)((HashMap)selectedTicketsWithSettings.get(i)).get("isrequired")))
						reqtickets.add(((HashMap)selectedTicketsWithSettings.get(i)).get("tid"));
				System.out.println("req tkts::"+reqtickets);
				}
			}else{
				seltickets = cadb.getCustomQuestionTickets(eid,attributeid);
			}
			return "questionsettings";
		}catch (Exception e) {
			System.out.println("questionSettings error "+e.getMessage());
		}
		return "error";
	}
	public void rsvpQuestionSettings(){
		try{
			if(attributeid.indexOf("-")>-1){
				reqresponses=new ArrayList();
				ArrayList selectedResposesWithSettings=CustomAttributesDB.getRSVPResponseLevelBaseProfileSettings(eid,attributeid);
				for(int i=0;i<selectedResposesWithSettings.size();i++){
					if("Y".equals((String)((HashMap)selectedResposesWithSettings.get(i)).get("isrequired")))
						reqresponses.add(((HashMap)selectedResposesWithSettings.get(i)).get("tid"));
				}
			}
			
		}catch (Exception e) {
			System.out.println("questionSettings error "+e.getMessage());
		}
	}
	public String saveQSettings(){
		try {
			CustomAttributesDB cadb = new CustomAttributesDB();	
			if(attributeid.indexOf("-")>-1 && !(powertype.equals("RSVP"))){
				log.info("Saving Base Question Settings for eventid: "+eid+", question Id: "+attributeid);
				System.out.println("attrib id:"+attributeid+":"+seltickets+"::"+selticketsOption);
				cadb.saveTicketBaseProfileSettings(eid, attributeid,  seltickets,selticketsOption);
			}else{
				if(powertype.equals("RSVP")){
					if(attributeid.indexOf("-")>-1){
						cadb.saveResponseBaseProfileSettings(eid, attributeid, selRsvpStatusOptions, selresponsesOption);
					}
					log.info("Saving RSVP Question Settings for eventid: "+eid+", question Id: "+attributeid);
					saveRsvpQuestionSettings();
				}else{
					log.info("Saving Ticket Question Settings for eventid: "+eid+", question Id: "+attributeid);
					cadb.saveQuestionSettings(eid, attributeid, "false", seltickets,refType);
				}			
			}
			return "saved";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "error";
	}
	
	
	public String saveCustomQuestionRequiredOptional(){
		CustomAttributesDB cadb = new CustomAttributesDB();
		System.out.println("save qid::"+attributeid+":::questionRequired"+questionRequired+"::eid"+eid);
		cadb.saveCustomQuestionSettings(eid,questionRequired,attributeid);
	return "saved";
	}
	
	public String saveBaseQSettings(){
		try {
			CustomAttributesDB cadb = new CustomAttributesDB();
			cadb.saveBaseQuestionSettings(eid, seltickets);
			return "saveqsettings";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "error";
	}
	private void saveRsvpQuestionSettings(){
			CustomAttributesDB.saveRsvpQuestionSettings(eid,attributeid,selRsvpStatusOptions,purpose);
	}
	private void populateRefData(){
		populateTypeList();
		populateRequiredList();
	}
	private void populateTypeList(){
		typeList = new ArrayList<Entity>();
		typeList.add(new Entity("text","Small Text"));
		typeList.add(new Entity("textarea","Multiline Text"));
		typeList.add(new Entity("selection","Multiple Choices"));
		selectionList = new ArrayList<Entity>();		
		selectionList.add(new Entity("radio","Radio"));
		selectionList.add(new Entity("checkbox","Check Box"));
		selectionList.add(new Entity("select","Drop Down List"));
	}
	private void populateRequiredList(){
		requiredlist = new ArrayList<Entity>();
		requiredlist.add(new Entity("Required","Mandatory"));
		requiredlist.add(new Entity("Optional","Optional"));
	}
	private void populateRsvpAttribs(){
		rsvpStatusOptionsList = new ArrayList<Entity>();
		rsvpStatusOptionsList.add(new Entity("yes","Attending"));
		//rsvpStatusOptionsList.add(new Entity("no","Not Attending"));
		rsvpStatusOptionsList.add(new Entity("notsure","Not Sure"));
		selRsvpStatusOptions = new ArrayList();
	}
	
	public boolean validateInputData() {
		if ("".equals(customattribute.getName().trim())) {
			addFieldError("customattribute.name", I18n.getString("qn.question.empty.errmsg"));
		}
		
		if(type.equals("selection")){
			boolean flag=false;
			for (Map.Entry<String, String> entry : multipleOptionsMap.entrySet()){
			    if(!"".equals(entry.getValue().trim())){
	            	flag=true;
	            	break;
	            }
			}
			if(!flag) addFieldError("multiplechoice", I18n.getString("qn.ans.mult.choice.qn.errmsg"));
		}
		
		if (!getFieldErrors().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public String customSubQuestions(){
		if("yes".equals(getDBData) && attributeid!=null && attribOptionId !=null)
			subQuestionsJsonData=CustomAttributesDB.getSubCustomAttribs(eid,attributeid,attribOptionId).toString();
		return "subquestions";
	}
	
	public String addSubQuestion(){
		try{
			subCustomAttribute = new CustomAttribute();			
			populateTypeList();
			populateRequiredList();
			JSONObject msg=new JSONObject();
			msg.put("multilpledata","");
			customOptions=msg.toString();
			/*CustomAttributesDB cadb = new CustomAttributesDB();
			customattribute.setIsSubQuestion("yes");
			customattribute.setAttribsetid(attributesetid);
			attributeid = cadb.createQuestion(eid,customattribute,attributeid,purpose,refType);
			attributesetid=customattribute.getAttribsetid();
			System.out.println("!!! attributesetid: "+attributesetid);*/
			return "addsubquestion";
			
		}catch(Exception ex){
			System.out.println("error"+ex.getMessage());
		}
		return "error";
	}
	
	public void prepare() throws Exception {
		// TODO Auto-generated method stub	
	}
	public String getQname() {
		return qname;
	}
	public void setQname(String qname) {
		this.qname = qname;
	}
	public List getAlltickets() {
		return alltickets;
	}
	public void setAlltickets(List alltickets) {
		this.alltickets = alltickets;
	}
	public List getSeltickets() {
		return seltickets;
	}
	public void setSeltickets(List seltickets) {
		this.seltickets = seltickets;
	}
	public List<Entity> getCollectbuyerlist() {
		return collectbuyerlist;
	}
	public void setCollectbuyerlist(List<Entity> collectbuyerlist) {
		this.collectbuyerlist = collectbuyerlist;
	}
	public String getCollectbuyer() {
		return collectbuyer;
	}
	public void setCollectbuyer(String collectbuyer) {
		this.collectbuyer = collectbuyer;
	}
	public List<Entity> getRsvpStatusOptionsList() {
		return rsvpStatusOptionsList;
	}
	public void setRsvpStatusOptionsList(List<Entity> rsvpStatusOptionsList) {
		this.rsvpStatusOptionsList = rsvpStatusOptionsList;
	}
	public String getPowertype() {
		return powertype;
	}
	public void setPowertype(String powertype) {
		this.powertype = powertype;
	}
	public String getTranscationQuestionsJsonData() {
		return transcationQuestionsJsonData;
	}
	public void setTranscationQuestionsJsonData(String transcationQuestionsJsonData) {
		this.transcationQuestionsJsonData = transcationQuestionsJsonData;
	}
	public String getTicketQuestionJsonData() {
		return ticketQuestionJsonData;
	}
	public void setTicketQuestionJsonData(String ticketQuestionJsonData) {
		this.ticketQuestionJsonData = ticketQuestionJsonData;
	}
	public String getQlevel() {
		return qlevel;
	}
	public void setQlevel(String qlevel) {
		this.qlevel = qlevel;
	}
	public String getSwapattributeid() {
		return swapattributeid;
	}
	public void setSwapattributeid(String swapattributeid) {
		this.swapattributeid = swapattributeid;
	}
	public String getResponseQuestionJsonData() {
		return responseQuestionJsonData;
	}
	public void setResponseQuestionJsonData(String responseQuestionJsonData) {
		this.responseQuestionJsonData = responseQuestionJsonData;
	}
	public ArrayList getSelresponsesOption() {
		return selresponsesOption;
	}
	public void setSelresponsesOption(ArrayList selresponsesOption) {
		this.selresponsesOption = selresponsesOption;
	}
	public String getSubquestionsinfo() {
		return subquestionsinfo;
	}
	public void setSubquestionsinfo(String subquestionsinfo) {
		this.subquestionsinfo = subquestionsinfo;
	}
	public ArrayList getAttribOptions() {
		return attribOptions;
	}
	public void setAttribOptions(ArrayList attribOptions) {
		this.attribOptions = attribOptions;
	}
	public CustomAttribute getSubCustomAttribute() {
		return subCustomAttribute;
	}
	public void setSubCustomAttribute(CustomAttribute subCustomAttribute) {
		this.subCustomAttribute = subCustomAttribute;
	}
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	public String getAttribOptionId() {
		return attribOptionId;
	}
	public void setAttribOptionId(String attribOptionId) {
		this.attribOptionId = attribOptionId;
	}
	public String getSubQuestionsJsonData() {
		return subQuestionsJsonData;
	}
	public void setSubQuestionsJsonData(String subQuestionsJsonData) {
		this.subQuestionsJsonData = subQuestionsJsonData;
	}
	public String getGetDBData() {
		return getDBData;
	}
	public void setGetDBData(String getDBData) {
		this.getDBData = getDBData;
	}
	public String getOptionVal() {
		return optionVal;
	}
	public void setOptionVal(String optionVal) {
		this.optionVal = optionVal;
	}
	public String getTicketsJson() {
		return ticketsJson;
	}
	public void setTicketsJson(String ticketsJson) {
		this.ticketsJson = ticketsJson;
	}
	public String getQuestionTicketJson() {
		return questionTicketJson;
	}
	public void setQuestionTicketJson(String questionTicketJson) {
		this.questionTicketJson = questionTicketJson;
	}
	public String getAttribId() {
		return attribId;
	}
	public void setAttribId(String attribId) {
		this.attribId = attribId;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public String getAttributesetid() {
		return attributesetid;
	}
	public void setAttributesetid(String attributesetid) {
		this.attributesetid = attributesetid;
	}
	public String getTicketQnJson() {
		return ticketQnJson;
	}
	public void setTicketQnJson(String ticketQnJson) {
		this.ticketQnJson = ticketQnJson;
	}
	
}
