package com.mymarketing.beans;

import java.util.ArrayList;


public class MailListData {
	
	private String name="";
	private String description="";
	private String email="";
	private String listid="";
	private ArrayList<String> emails=new ArrayList<String>();
	private String existmanagerstatus="no";
	
	public String getExistmanagerstatus() {
		return existmanagerstatus;
	}
	public void setExistmanagerstatus(String existmanagerstatus) {
		this.existmanagerstatus = existmanagerstatus;
	}
	public String getListid() {
		return listid;
	}
	public void setListid(String listid) {
		this.listid = listid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<String> getEmails() {
		return emails;
	}
	public void setEmails(ArrayList<String> emails) {
		this.emails = emails;
	}

}
