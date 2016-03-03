package com.myemailmarketing.beans;

import java.io.Serializable;

public class MemberData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9062146590027181096L;
	private String email="";
	private String phone="";
	private String firstName="";
	private String lastName="";
	private String gender="";
	private String age="";
	private String title="";
	private String place="";
	private String company="";
	private String emailPref="";
	private String memberId="";
	private String status="";
	private String mem_userId="";
	
	
		
	public String getMem_userId() {
		return mem_userId;
	}
	public void setMem_userId(String mem_userId) {
		this.mem_userId = mem_userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmailPref() {
		return emailPref;
	}
	public void setEmailPref(String emailPref) {
		this.emailPref = emailPref;
	}
	
}
