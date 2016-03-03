package com.mycommunities.beans;

import java.util.*;
public class SubscriptionDue {
	private String userId;
	private String status="";
	private String amountDue="0";
	private Date dueDate ;
	private Date entryDate ;
	private String purpose="";


	public String getUserId(){
	return userId;
	}


	public void setUserId(String userId){
	this.userId=userId;
	}


	public String getStatus(){
	return status;
	}


	public void setStatus(String status){
	this.status=status;
	}


	public String getAmountDue(){
	return amountDue;
	}


	public void setAmountDue(String amountDue){

	this.amountDue=amountDue;
	}


	public Date getDueDate(){
	return dueDate;
	}


	public void setDueDate(Date dueDate){
	this.dueDate=dueDate;
	}

	public Date getEntryDate(){
	return entryDate;
	}


	public void setEntryDate(Date entryDate){
	this.entryDate=entryDate;
	}


	public String getPurpose(){
	return purpose;
	}

	public void setPurpose(String purpose){
	this.purpose=purpose;
	}



}
