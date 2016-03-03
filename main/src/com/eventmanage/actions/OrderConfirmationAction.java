package com.eventmanage.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;




public class OrderConfirmationAction extends ActionSupport implements ValidationAware{
	private String eid="";
	
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String execute(){
		return "success";
	}
}
