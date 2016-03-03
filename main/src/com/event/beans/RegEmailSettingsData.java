package com.event.beans;
public class RegEmailSettingsData {
	private String sendToAttendee="yes";
	private String sendToManager="yes";
	private String cc="";
	private String tempalteContent="";
	private String powerType="";
	private String subject="";
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPowerType() {
		return powerType;
	}
	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}
	public String getTempalteContent() {
		return tempalteContent;
	}
	public void setTempalteContent(String tempalteContent) {
		this.tempalteContent = tempalteContent;
	}
	public String getSendToAttendee() {
		return sendToAttendee;
	}
	public void setSendToAttendee(String sendToAttendee) {
		this.sendToAttendee = sendToAttendee;
	}
	public String getSendToManager() {
		return sendToManager;
	}
	public void setSendToManager(String sendToManager) {
		this.sendToManager = sendToManager;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}

}
