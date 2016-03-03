package com.eventmanage.actions;
import java.util.HashMap;

import com.event.dbhelpers.DisplayAttribsDB;
import com.eventbee.general.I18NCacheData;
import com.eventregister.dbhelper.RegistrationTiketingManager;
import com.eventregister.dbhelper.TicketsPageJson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class AddAttendeeVmAction extends ActionSupport implements Preparable{

   String eid="";
   HashMap ticketsVm=null;
   HashMap labelsMap=null;;
   String purpose="";
   String eventdate="";
   boolean seatingenabled;
   String currencyFormat="";
   
   public String getCurrencyFormat() {
	return currencyFormat;
}

public void setCurrencyFormat(String currencyFormat) {
	this.currencyFormat = currencyFormat;
}

public String getEventdate() {
	return eventdate;
}

public void setEventdate(String eventdate) {
	this.eventdate = eventdate;
}



	public String getPurpose() {
	return purpose;
}

public void setPurpose(String purpose) {
	this.purpose = purpose;
}

	public HashMap getLabelsMap() {
	return labelsMap;
}

public void setLabelsMap(HashMap labelsMap) {
	this.labelsMap = labelsMap;
}

	

	public HashMap getTicketsVm() {
	return ticketsVm;
}

public void setTicketsVm(HashMap ticketsVm) {
	this.ticketsVm = ticketsVm;
}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
	public boolean isSeatingenabled() {
		return seatingenabled;
	}

	public void setSeatingenabled(boolean seatingenabled) {
		this.seatingenabled = seatingenabled;
	}
	

	public String execute(){
	getTicketsVm(eid);
	return "success";
	}
	void getTicketsVm(String eid){
		RegistrationTiketingManager regTktMgr=new RegistrationTiketingManager();
		TicketsPageJson ticketsdb=new TicketsPageJson();
		ticketsVm=ticketsdb.getGroupTicketsVec(eid,null,eventdate,purpose);
		HashMap<String, String> hm= new HashMap<String,String>();
		hm.put("module", "RegFlowWordings");
		labelsMap=DisplayAttribsDB.getDisplayAttribs(hm, I18NCacheData.getI18NLanguage(eid), eid);//DisplayAttribsDB.getAttribValues(eid,"RegFlowWordings");
		currencyFormat=regTktMgr.getEventCurrencyFormat(eid);
		seatingenabled=regTktMgr.getSeatingEnabled(eid);
		}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	}
	
	
	
