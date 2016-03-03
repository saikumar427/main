package com.submanager.actions;

import java.util.HashMap;
import java.util.Map;


import com.event.beans.EventData;
import com.event.beans.SubManagerData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.FacebookConnectDB;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.user.dbhelpers.UserDB;
import com.event.dbhelpers.SubManagerDB;
import com.eventbee.general.EventBeeValidations;

import com.event.helpers.I18n;
import java.util.ResourceBundle;
import java.util.Locale;


public class SubManagerLoginAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2985084346950999097L;
	//------------------------------------------Properties-----------------------
	String sub_mgr_id="";
	String email="";
	String password="";
	String eid="";
	EventData edata=null;
	String message="";
	String newPassword="";
	String confirmNewPassword="";
	private boolean errorsExists;
	private String msgKey="";
	private String FBAppId="";
    private String i18nCode="";
	    
	public String getI18nCode() {
		return i18nCode;
	}
	public void setI18nCode(String i18nCode) {
		this.i18nCode = i18nCode;
	}

	public String getFBAppId() {
		return FBAppId;
	}
	public void setFBAppId(String fBAppId) {
		FBAppId = fBAppId;
	}
	//------------------------------------------Setters and Getters-------------
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public boolean isErrorsExists() {
		return errorsExists;
	}
	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}
	public String getSub_mgr_id() {
		return sub_mgr_id;
	}
	public void setSub_mgr_id(String sub_mgr_id) {
		this.sub_mgr_id = sub_mgr_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public EventData getEdata() {
		return edata;
	}
	public void setEdata(EventData edata) {
		this.edata = edata;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	//------------------------------------------Action Methods-----------------
	public String execute(){
		FBAppId=FacebookConnectDB.getFBAppId("0");
			return "success";
	}
	public String authenticate(){
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		FBAppId=FacebookConnectDB.getFBAppId("0");
		Map session = ActionContext.getContext().getSession();
		if(session.get("SESSION_SUBMGR")!=null || session.get("SESSION_USER")!=null) return "success";
		SubManagerData smd=SubManagerDB.validateSubManager(email,password);
		if(smd!=null){
			System.out.println("smd in db: "+smd.getSubMgrId());
			UserDB.userLangTrack(smd.getEmail(),I18n.getLanguageFromSession().toString(),"Submanagerlogin","0");
			session.remove("SESSION_USER");
		System.out.println("object: "+smd);
			session.put("SESSION_SUBMGR", smd);
			return "loggedin";
		}
		else{
			message=resourceBundle.getString("la.invalid.error.lbl");

			return execute();
		}
	}
	public String logout(){
		Map session = ActionContext.getContext().getSession();
        i18nCode= I18n.getActualLangFromSession();
		String seid="";
		if(session.get("SESSION_SUBMGR")!=null){
			seid=((SubManagerData)session.get("SESSION_SUBMGR")).getEid();
		}
		if(seid!=null && !"".equals(seid))
			eid=seid;
		session.remove("SESSION_SUBMGR");
		return "loggedout";
	}
	public String subMgrChangepwd(){
		System.out.println("subMgrChangepwd ");
		return "changepwd";
	}
	
	public String saveSubMgrChangePwd(){	
		System.out.println("saveSubMgrChangePwd email: "+email);
		boolean status=validateData();
		if(status){
			msgKey="submgrchangepwd";
			SubManagerDB.updatePassword(newPassword.trim(), email.toLowerCase().trim());
			return "pwdupdated";
		}else{
			System.out.println("in ipnut error");
		return "inputerror";
		}
	}
	
	
	public boolean validateData(){
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		HashMap validateSubMgr=new HashMap();
		
	    if ("".equals(getEmail().trim()))
			addFieldError("email", resourceBundle.getString("global.email.is.empty.errmsg"));
		else if(!EventBeeValidations.isValidFromEmail(getEmail().trim()))
			addFieldError("email", resourceBundle.getString("global.invalid.format.for.email.errmsg"));

	    
	    if ("".equals(getPassword().trim()))
			addFieldError("oldpassword", resourceBundle.getString("sub.old.pwd.empty.msg"));
	    if ("".equals(getNewPassword().trim()))
			addFieldError("newpassword", resourceBundle.getString("sub.new.pwd.empty.msg"));
	    if ("".equals(getConfirmNewPassword().trim()))
			addFieldError("confirmnewpassword", resourceBundle.getString("sub.confirm.new.pwd.msg"));
	    
	    if(getFieldErrors().isEmpty())
	    	validateSubMgr=SubManagerDB.validateSubMgrChangePwd(getEmail().toLowerCase().trim(), getPassword().trim(), getNewPassword().trim(), getConfirmNewPassword().trim());
		
	    if(validateSubMgr.get("invalidemail") != null){
	    	addFieldError("email", validateSubMgr.get("invalidemail").toString());
	    }
	    
	    if(validateSubMgr.get("invalidoldpwd") != null){
	    	addFieldError("oldpassword", validateSubMgr.get("invalidoldpwd").toString());
	    }
	    
		if(validateSubMgr.get("pwdMatch")!=null){
			addFieldError("newpassword", validateSubMgr.get("pwdMatch").toString());
		}
		if(validateSubMgr.get("pwdLength")!=null){
			addFieldError("newpassword", validateSubMgr.get("pwdLength").toString());
		}
		if(!getFieldErrors().isEmpty()){
			errorsExists = true;
			return false;
		}
	return true;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
}
