package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.event.beans.SubManagerData;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.SubManagerDB;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.EventBeeValidations;
import com.eventmanage.helpers.SubManagerJSONHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;



public class SubManagerAction  extends ActionSupport implements Preparable,ValidationAware{

	private static final long serialVersionUID = 9202939899844513527L;
	
	private String eid="";
	private String jsonData="";
	private boolean errorsExists;
	private String subMgrId="";
	private String status="";
	private String powertype="";
	private SubManagerData subManagerData = new SubManagerData(); 
	private ArrayList<SubManagerData> subMgrList = new ArrayList<SubManagerData>();
	private ArrayList<String> subMgrPermissionList=new ArrayList<String>();
	private ArrayList<Entity> actionPermissionList=new ArrayList<Entity>();
	private HashMap<String,String> actionPermissionMap=new HashMap<String, String>();
	
	public HashMap<String, String> getActionPermissionMap() {
		return actionPermissionMap;
	}
	public void setActionPermissionMap(HashMap<String, String> actionPermissionMap) {
		this.actionPermissionMap = actionPermissionMap;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubMgrId() {
		return subMgrId;
	}
	public void setSubMgrId(String subMgrId) {
		this.subMgrId = subMgrId;
	}
	public boolean isErrorsExists() {
		return errorsExists;
	}
	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}
	public String getJsonData() {
		return jsonData;
	}
	public SubManagerData getSubManagerData() {
		return subManagerData;
	}
	public void setSubManagerData(SubManagerData subManagerData) {
		this.subManagerData = subManagerData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String execute(){
		Boolean redirect =false;
		String pwrType=ActionContext.getContext().getParameters().get("pwrType").toString();
		if("Ticketing".equals(pwrType))
			redirect = SpecialFeeDB.checking(eid,"SubManager","Ticketing","400");
		else
			redirect = rsvpChecking();
		if(redirect){
			return "pageRedirect";
		}else{
			subMgrList = SubManagerDB.getSubManagerList(eid);
			jsonData = SubManagerJSONHelper.getSubManagerJson(subMgrList).toString();
			return "success";
		}
	}
	Boolean rsvpChecking(){
		String curLvl=ActionContext.getContext().getParameters().get("curLvl").toString();
		int SubManagerRsvp = 150;
		if(Integer.parseInt(curLvl)==SubManagerRsvp)
			return false;
		else
			return true;
	}
	
	public String reloadData(){
		execute();
		return "json";
	}
	
	public String getSubManager(){
		System.out.println("getSubManager powertype: "+powertype);
		if(!"".equals(subMgrId)){
			subManagerData=SubManagerDB.getSubManager(eid,subMgrId);
			//subMgrPermissionList=SubManagerDB.getSubManagerPermissionList(eid, subMgrId);
			subMgrPermissionList=subManagerData.getSubMgrPermissionList();
		}
		String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
		subManagerData.setMgrId(mgrId);
		actionPermissionList=SubManagerDB.getActionPermissionList(powertype);
		
		return "input";
	}
	
	public String Save(){
		boolean status=validateData();
		if(status){
			System.out.println("Save SubManager powertype: "+powertype);
			StringBuffer permCodes= new StringBuffer();
			StringBuffer permissions= new StringBuffer();
			if(subMgrPermissionList.size()>0){
				for(int i=0;i<subMgrPermissionList.size();i++){
					permCodes.append(subMgrPermissionList.get(i)+",");
					permissions.append(actionPermissionMap.get(subMgrPermissionList.get(i))+"<br/>");
				}
				permCodes.deleteCharAt(permCodes.length()-1);
				subManagerData.setPermCodes(permCodes.toString());
				subManagerData.setPermissions(permissions);
			}
			String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
			subManagerData.setMgrId(mgrId);
			if("".equals(subManagerData.getSubMgrId()))
				subManagerData.setManage("add");
			else subManagerData.setManage("edit");
			SubManagerDB.saveSubManager(eid,subManagerData);
			return "addsubmgr";
		}else {
			return "inputerror";
		}
		
	}
	
	public String manageSubManager(){
		SubManagerDB.manageSubManager(eid,subMgrId,status);
		return "addsubmgr";
	}
	
	public String subManagerRemoveScreen(){
		return "remove";
	}
	
	public String removeSubManager(){
		SubManagerDB.removeSubManager(eid, subMgrId);
		return "addsubmgr";
	}
	
	
	public boolean validateData(){
		HashMap validateSubMgr=new HashMap();
		ResourceBundle resourceBundle =I18n.getResourceBundle();
		if ("".equals(subManagerData.getName().trim()))
			addFieldError("subManagerData.name", resourceBundle.getString("sm.name.is.empty.errmsg"));
		
	    if ("".equals(subManagerData.getEmail().trim()))
			addFieldError("subManagerData.email", resourceBundle.getString("global.email.is.empty.errmsg"));
		else if(!EventBeeValidations.isValidFromEmail(subManagerData.getEmail().trim()))
			addFieldError("subManagerData.email", resourceBundle.getString("global.invalid.format.for.email.errmsg"));
	    
	    validateSubMgr=SubManagerDB.validateSubManager(eid,subManagerData);
	    if(validateSubMgr.get("subMgrExist") != null){
	    	addFieldError("subManagerData.getEmail()", validateSubMgr.get("subMgrExist").toString());
	    }
		/*if(validateSubMgr.get("pwdLength")!=null){
			addFieldError("subManagerData.getPassword()", validateSubMgr.get("pwdLength").toString());
		}
		if(validateSubMgr.get("pwdMatch")!=null){
			addFieldError("subManagerData.getPassword()", validateSubMgr.get("pwdMatch").toString());
		}*/
		if(!getFieldErrors().isEmpty()){
			errorsExists = true;
			return false;
		}
	return true;
	}

	public ArrayList<SubManagerData> getSubMgrList() {
		return subMgrList;
	}
	public void setSubMgrList(ArrayList<SubManagerData> subMgrList) {
		this.subMgrList = subMgrList;
	}
	public ArrayList<String> getSubMgrPermissionList() {
		return subMgrPermissionList;
	}
	public void setSubMgrPermissionList(ArrayList<String> subMgrPermissionList) {
		this.subMgrPermissionList = subMgrPermissionList;
	}
	public ArrayList<Entity> getActionPermissionList() {
		return actionPermissionList;
	}
	public void setActionPermissionList(ArrayList<Entity> actionPermissionList) {
		this.actionPermissionList = actionPermissionList;
	}
	public String getPowertype() {
		return powertype;
	}
	public void setPowertype(String powertype) {
		this.powertype = powertype;
	}

}
