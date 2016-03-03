package com.event.beans;

import java.util.ArrayList;


public class SubManagerData {
	private String eid="";
	private String email="";
	private String name="";
	private String password="";
	private String retypePassword="";
	private String mgrId="";
	private String subMgrId="";
	private String status="";
	private String permCodes="";
	private StringBuffer permissions=new StringBuffer();
	private String sendEmail="";
	private String manage="";
	private String emailTemplate="EVENT_SUBMANAGER_EXISTING";
	ArrayList<String> subMgrPermissionList = new ArrayList<String>();
	public ArrayList<String> getSubMgrPermissionList() {
		return subMgrPermissionList;
	}
	public void setSubMgrPermissionList(ArrayList<String> subMgrPermissionList) {
		this.subMgrPermissionList = subMgrPermissionList;
	}
	public String getManage() {
		return manage;
	}
	public void setManage(String manage) {
		this.manage = manage;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
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
	public String getMgrId() {
		return mgrId;
	}
	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}
	public String getSubMgrId() {
		return subMgrId;
	}
	public void setSubMgrId(String subMgrId) {
		this.subMgrId = subMgrId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRetypePassword() {
		return retypePassword;
	}
	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}
	public String getPermCodes() {
		return permCodes;
	}
	public void setPermCodes(String permCodes) {
		this.permCodes = permCodes;
	}
	public String getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}
	public StringBuffer getPermissions() {
		return permissions;
	}
	public void setPermissions(StringBuffer permissions) {
		this.permissions = permissions;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailTemplate() {
		return emailTemplate;
	}
	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}
	
	
	
}
