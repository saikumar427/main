package com.eventmanage.actions;

import com.opensymphony.xwork2.ActionContext;
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
		String curLvl=ActionContext.getContext().getParameters().get("curLvl").toString();
		String pwrType=ActionContext.getContext().getParameters().get("pwrType").toString();
		int OrderConfirmationTkt = 300;
		int OrderConfirmationRsvp = 150;
		System.out.println("Current Level : "+curLvl+" , Power Type : "+pwrType+" & EventId : "+eid);
		if(Integer.parseInt(curLvl)==OrderConfirmationRsvp || Integer.parseInt(curLvl)>=OrderConfirmationTkt)
			return "success";
		else
			return "pageRedirect";
	}
}
