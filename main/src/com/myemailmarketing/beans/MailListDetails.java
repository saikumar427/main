package com.myemailmarketing.beans;

import java.io.Serializable;

public class MailListDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -327620986403801826L;
	private String listId="";
	private String listName="";
	private String memberCount="";
	private String descritption="";
	private String unsubmsg="";
	private String status="";
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescritption() {
		return descritption;
	}
	public void setDescritption(String descritption) {
		this.descritption = descritption;
	}
	public String getUnsubmsg() {
		return unsubmsg;
	}
	public void setUnsubmsg(String unsubmsg) {
		this.unsubmsg = unsubmsg;
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(String memberCount) {
		this.memberCount = memberCount;
	}
}
