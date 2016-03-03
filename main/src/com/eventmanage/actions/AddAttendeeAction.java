package com.eventmanage.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.event.dbhelpers.DisplayAttribsDB;
import com.event.dbhelpers.EventDB;
import com.eventbee.general.DbUtil;
import com.eventbee.general.I18NCacheData;
import com.eventregister.dbhelper.RegistrationTiketingManager;
import com.eventregister.dbhelper.RegistrationRSVPManager;
import com.eventregister.dbhelper.TicketsPageJson;
public class AddAttendeeAction extends ActionSupport implements Preparable,ValidationAware{
private static final long serialVersionUID = 9223131292850756816L;
private String eid="";
private String ccstatus="";
String ticketsdata="";
String purpose="";
String type="";
String eventdate="";
String recurrdatestxtlabel="";
Vector recurringeventsVec=null;
String attendingLabel="";
TicketsPageJson ticketPage=null;

	
	
public String getRecurrdatestxtlabel() {
	return recurrdatestxtlabel;
}

public void setRecurrdatestxtlabel(String recurrdatestxtlabel) {
	this.recurrdatestxtlabel = recurrdatestxtlabel;
}


public String getEventdate() {
	return eventdate;
}


public String getCcstatus() {
		return ccstatus;
	}


	public void setCcstatus(String ccstatus) {
		this.ccstatus = ccstatus;
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

	public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

	public String getTicketsdata() {
		return ticketsdata;
	}

	public void setTicketsdata(String ticketsdata) {
		this.ticketsdata = ticketsdata;
	}

	public String getEid() {
		return eid;
	}

	public Vector getRecurringeventsVec() {
	return recurringeventsVec;
}

public void setRecurringeventsVec(Vector recurringeventsVec) {
	this.recurringeventsVec = recurringeventsVec;
}
	public void setEid(String eid) {
		this.eid = eid;
	}
	
	public String getJsonData(){
		getTicketJsonData();
	
	return "success";	
	}

	
	public String getAttendingLabel() {
		return attendingLabel;
	}

	public void setAttendingLabel(String attendingLabel) {
		this.attendingLabel = attendingLabel;
	}

	public String execute(){
		boolean recurringevent=EventDB.isRecurringEvent(eid);
		System.out.println("isrecurring is:"+recurringevent);
		String module="";
		if("rsvp".equals(type))
		{
			module="RSVPFlowWordings";
			getRSVPJsonData();
		}
		else{
			module="RegFlowWordings";
			getTicketJsonData();
		}
	    if(recurringevent && !"".equals(eid) && eid!=null){
	     /*recurrdatestxtlabel=DbUtil.getVal("select attrib_value from custom_event_display_attribs where attrib_name='event.reg.recurringdates.label' and module=? and eventid=CAST(? as INTEGER)",new String[]{module,eid});
	     if(recurrdatestxtlabel==null || "".equals(recurrdatestxtlabel))
	    	 recurrdatestxtlabel=DbUtil.getVal("select attribdefaultvalue from default_event_display_attribs where attribname='event.reg.recurringdates.label' and module=? and lang=?",new String[]{module,I18NCacheData.getI18NLanguage(eid)});
*/	     
	    	
	    	HashMap<String,String> disAttribsForKeys=new HashMap<String,String>();
			String lang=I18NCacheData.getI18NLanguage(eid);
			disAttribsForKeys=DisplayAttribsDB.getAttribValuesForKeys(eid, module, lang, new String []{"event.reg.recurringdates.label"});
			recurrdatestxtlabel=disAttribsForKeys.get("event.reg.recurringdates.label");
	    	if(recurrdatestxtlabel==null)recurrdatestxtlabel="";
	    }
	    if("".equals(recurrdatestxtlabel))recurrdatestxtlabel="Select a date and time to attend";
	    
	return "success";	
	}

	public String getTicketsforselecteddate(){
		ticketPage=new TicketsPageJson();
		JSONObject  ticketsData=ticketPage.getJsonTicketsData(eid,eventdate,null,purpose);
		ticketsdata=ticketsData.toString();
		return "recurringeventresult";
	}
	
	void  getTicketJsonData(){
	RegistrationTiketingManager regTktMgr=new RegistrationTiketingManager();
	 ticketPage=new TicketsPageJson();
	 recurringeventsVec=regTktMgr.getRecurringEventDates(eid);
		
	JSONObject  ticketsData=ticketPage.getJsonTicketsData(eid,null,null,purpose);
	ticketsdata=ticketsData.toString();
		
	}
	void getRSVPJsonData(){
		RegistrationRSVPManager regrsvpMgr=new RegistrationRSVPManager();
		recurringeventsVec=regrsvpMgr.getRSVPRecurringDates(eid);
		attendingLabel=regrsvpMgr.attendeeLabel(eid);
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String getManagerCCStatus(){
	JSONObject jsondata=new JSONObject();
	String finalcardstatus="don't askcard",checkfeaturestatus="";
	String mgrid=DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
	if("".equals(finalcardstatus)){/*we hard code finalcardstatus='don't askcard' for disable logic.
        If in case this is required just put finalcardstatus='' and remove this if condition*/

	String ccstatusqry="select value from mgr_config where userid=Cast(? as integer) and name='card.popup.required'";
	String ccpopupstatus=DbUtil.getVal(ccstatusqry,new String[]{mgrid});
	if(ccpopupstatus==null || "N".equalsIgnoreCase(ccpopupstatus)){
	String query="select value from mgr_config where userid=Cast(? as integer) and name='card.required.addattendee'";
	checkfeaturestatus=DbUtil.getVal(query,new String[]{mgrid});
	}
	if("Y".equalsIgnoreCase(checkfeaturestatus) && "N".equalsIgnoreCase(ccpopupstatus))
		finalcardstatus="don't askcard";
	else if(ccpopupstatus==null && "Y".equalsIgnoreCase(checkfeaturestatus)){
	String query="select 'Y' from manager_card_authentication where mgr_id=? and status='Active'";
	String managercard=DbUtil.getVal(query, new String[]{mgrid});
	if("Y".equalsIgnoreCase(managercard))
		finalcardstatus="don't askcard";
	else	
		finalcardstatus="askcard";
	}	
	else if(ccpopupstatus==null && checkfeaturestatus==null)
		finalcardstatus="don't askcard";
	
	else
		finalcardstatus="don't askcard";
	}
   try{
	   Map session = ActionContext.getContext().getSession();
	   	if(session.get("SESSION_USER") == null)
	   		jsondata.put("mgrType", "submgr");
	   	else
	   		jsondata.put("mgrType", "mgr");
		jsondata.put("checkcardstatus", finalcardstatus);
		jsondata.put("mgrid", mgrid);
		ccstatus=jsondata.toString();
	}catch(Exception e){
		System.out.println("Exception occured in preparing json in AddAttendee:"+eid+" "+e.getMessage()); 
	}
	System.out.println("ccstaus is:"+ccstatus);
	return "checkcardstatus";
	}

}
	
	
	


