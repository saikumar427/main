package com.eventmanage.actions;

import com.event.dbhelpers.EventDB;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TicketSettingsAction extends ActionSupport{
	private String eid = "";
	private String eventleveltktcount="0";
	private String venueenable="YES";
	
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getEventleveltktcount() {
		return eventleveltktcount;
	}

	public void setEventleveltktcount(String eventleveltktcount) {
		this.eventleveltktcount = eventleveltktcount;
	}
	
	public String getVenueenable() {
		return venueenable;
	}

	public void setVenueenable(String venueenable) {
		this.venueenable = venueenable;
	}

	public String execute() {
		String curLvl=ActionContext.getContext().getParameters().get("curLvl").toString();
		String pwrType=ActionContext.getContext().getParameters().get("pwrType").toString();
		int Ticketsettings =200;
		System.out.println("Current Level : "+curLvl+" , Power Type : "+pwrType+" & EventId : "+eid);
		if(Integer.parseInt(curLvl)>=Ticketsettings){
			eventleveltktcount=EventDB.getConfigVal(eid,"event.reg.eventlevelcheck.count","");
			venueenable=EventDB.getConfigVal(eid,"event.seating.enabled","NO");
			return "success";
		}
	else
		return "pageRedirect";
	
}
}

