package com.mymarketing.beans;

public class ScheduleData {
	private String schId="";
	private String templateId="";
	private String name="";
	private String month="";
    private String day="";
    private String year="";
    private String hour="";
    private String time="";
    private String schtimeType="now";
    private String mailList="";
    
	public String getMailList() {
		return mailList;
	}
	public void setMailList(String mailList) {
		this.mailList = mailList;
	}
	public String getSchtimeType() {
		return schtimeType;
	}
	public void setSchtimeType(String schtimeType) {
		this.schtimeType = schtimeType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSchId() {
		return schId;
	}
	public void setSchId(String schId) {
		this.schId = schId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
