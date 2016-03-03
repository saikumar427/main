package com.eventmanage.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class SimpleAction extends ActionSupport implements Preparable,ValidationAware{
	private String eid="";
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
