package com.mycommunities.beans;

public class MailListMember {
	
	private String managerId="";
	private String memberId="";
	private String userId="";
	private String name="";
	private String firstname="";
	private String lastname="";
	private String email="";
	private String phone="";
	private String company="";
	private String jobType="";
	private String place="";
	private String age="";
	private String gender="";
	private String emailType="";
	private String status="";
	private String listId="";

	private String createdBy=null;
	private java.sql.Date createdDate=null;
	
	public static final String[] OPTION_DISP_PREF=new String[]{"HTML","Text"};
	public static final String[] OPTION_VAL_PREF=new String[]{"html","text"};
	public static final String[] OPTION_DISP_GEN=new String[]{"Male","Female"};
	public static final String[] OPTION_VAL_STAT=new String[]{"available","unsubscribed","invalid","bounced"};
	public static final String[] OPTION_DISP_STAT=new String[]{"Active","Unsubscribed","Invalid","Bounced"};



	
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public java.sql.Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(java.sql.Date createdDate) {
		this.createdDate = createdDate;
	}
	public static String[] getOPTION_DISP_PREF() {
		return OPTION_DISP_PREF;
	}
	public static String[] getOPTION_VAL_PREF() {
		return OPTION_VAL_PREF;
	}
	public static String[] getOPTION_DISP_GEN() {
		return OPTION_DISP_GEN;
	}
	public static String[] getOPTION_VAL_STAT() {
		return OPTION_VAL_STAT;
	}
	public static String[] getOPTION_DISP_STAT() {
		return OPTION_DISP_STAT;
	}
	

}
