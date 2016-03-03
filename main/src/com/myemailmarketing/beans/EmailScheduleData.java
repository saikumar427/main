package com.myemailmarketing.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author paj
 *
 */
public class EmailScheduleData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 500934418992111102L;
	private String campManagerId="";
    private String campId="";
    private String campName="";
    private String campGroupId="";
    private String campGroupType="";
    private String campHtml="";
    private String campText="";
    private String campSubject="";
    private String campTo="";
    private String campFrom="";
    private String campReplyTo="";
    private String campScheduledDate="";
    private String campStatus="";
    private String campTimeZone="";
    private ArrayList<String> campList;
    private String displayEbeeLogo="yes";
    private String schtimeType="now";
    private String month="";
    private String day="";
    private String year="";
    private String hour="";
    private String time="";
    private String mailtotest="";
    
	public String getMailtotest() {
		return mailtotest;
	}
	public void setMailtotest(String mailtotest) {
		this.mailtotest = mailtotest;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	
	public String getSchtimeType() {
		return schtimeType;
	}
	public void setSchtimeType(String schtimeType) {
		this.schtimeType = schtimeType;
	}
	public String getDisplayEbeeLogo() {
		return displayEbeeLogo;
	}
	public void setDisplayEbeeLogo(String displayEbeeLogo) {
		this.displayEbeeLogo = displayEbeeLogo;
	}
	public String getCampManagerId() {
		return campManagerId;
	}
	public void setCampManagerId(String campManagerId) {
		this.campManagerId = campManagerId;
	}
	public String getCampId() {
		return campId;
	}
	public void setCampId(String campId) {
		this.campId = campId;
	}
	public String getCampName() {
		return campName;
	}
	public void setCampName(String campName) {
		this.campName = campName;
	}
	public String getCampGroupId() {
		return campGroupId;
	}
	public void setCampGroupId(String campGroupId) {
		this.campGroupId = campGroupId;
	}
	public String getCampGroupType() {
		return campGroupType;
	}
	public void setCampGroupType(String campGroupType) {
		this.campGroupType = campGroupType;
	}
	public String getCampHtml() {
		return campHtml;
	}
	public void setCampHtml(String campHtml) {
		this.campHtml = campHtml;
	}
	public String getCampText() {
		return campText;
	}
	public void setCampText(String campText) {
		this.campText = campText;
	}
	public String getCampSubject() {
		return campSubject;
	}
	public void setCampSubject(String campSubject) {
		this.campSubject = campSubject;
	}
	public String getCampTo() {
		return campTo;
	}
	public void setCampTo(String campTo) {
		this.campTo = campTo;
	}
	public String getCampFrom() {
		return campFrom;
	}
	public void setCampFrom(String campFrom) {
		this.campFrom = campFrom;
	}
	public String getCampReplyTo() {
		return campReplyTo;
	}
	public void setCampReplyTo(String campReplyTo) {
		this.campReplyTo = campReplyTo;
	}
	public String getCampScheduledDate() {
		return campScheduledDate;
	}
	public void setCampScheduledDate(String campScheduledDate) {
		this.campScheduledDate = campScheduledDate;
	}
	public String getCampStatus() {
		return campStatus;
	}
	public void setCampStatus(String campStatus) {
		this.campStatus = campStatus;
	}
	public String getCampTimeZone() {
		return campTimeZone;
	}
	public void setCampTimeZone(String campTimeZone) {
		this.campTimeZone = campTimeZone;
	}
	public ArrayList<String> getCampList() {
		return campList;
	}
	public void setCampList(ArrayList<String> campList) {
		this.campList = campList;
	}

   public String toString(){
	   return "campId"+campId+"campName"+campName+"campList"+campList+"campReplyTo"+campReplyTo+
	   "campFrom"+campFrom+"campSubject"+campSubject+"schtimeType"+schtimeType+"day"+day+"month"+month+
	   "year"+year;
   }
   

}
