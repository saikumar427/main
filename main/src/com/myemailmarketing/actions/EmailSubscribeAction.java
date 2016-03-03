package com.myemailmarketing.actions;

import com.myemailmarketing.beans.MemberData;
import com.myemailmarketing.dbhelpers.MailingListDB;
import com.opensymphony.xwork2.ActionSupport;
public class EmailSubscribeAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String listId;
	private String email;
	private String fname;
	private String lname;
	private String gender;
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String process()
	{
		System.out.println("in subscribe action");
		return "success";		
	}
	public String commit(){
		MemberData mdata=new MemberData();
		if(email=="" || email==null)
			email=" ";
		if(fname=="" || fname==null)
			fname=" ";
		if(lname=="" || lname==null)
			lname=" ";
		mdata.setEmail(email);
		mdata.setEmailPref("html");
		mdata.setFirstName(fname);
		mdata.setLastName(lname);
		mdata.setGender(gender);
		MailingListDB.saveManualMemberData(userId,listId,mdata);
		return "success";
	}
	
}
