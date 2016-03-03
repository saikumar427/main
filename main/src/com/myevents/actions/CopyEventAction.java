package com.myevents.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;


import com.event.beans.AddEventData;
import com.event.dbhelpers.AddEventDB;
import com.event.dbhelpers.CopyEventDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.MyEventsDB;
import com.event.dbhelpers.UpgradeEventDB;
import com.event.helpers.DataPopulator;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class CopyEventAction extends MyEventsActionWrapper implements Preparable,
		ValidationAware {
	private List<Entity> eventsList;
	private AddEventData addEventData=new AddEventData();
	private ArrayList<Entity> hours=new ArrayList<Entity>();
	private ArrayList<Entity> minutes=new ArrayList<Entity>();
	private HashMap<String,String> oldeventdates=new HashMap<String,String>();
	private String purpose="";
	private String eventName ="";
	private String eid ="";
	private String selectedEvent="";
	private String newEventName="";
	private String msgKey ="";
	private String msgType="copyevent";
	private String resultdates="";
	private String ticketlevel="";
	private String neweventid ="";
	private String powertype="";
	private String copyeventErrorsExists ="";
	private String jsonData="";
	private String upgradeLevel="basic";
	private String currencySymbol="$";
	
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getNeweventid() {
		return neweventid;
	}
	public void setNeweventid(String neweventid) {
		this.neweventid = neweventid;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		super.prepare();
	}
	public boolean validateData(HashMap oldeventdata){
	 String eventstatus=(String)oldeventdata.get("eventstatus");
	 String numbervalidatestatus="";
	 if(newEventName.trim().length()==0)
	  addFieldError("newEventName", "Enter text for the Event Title");	 
	 if("Y".equalsIgnoreCase(eventstatus)){
	  if(addEventData.getStartmonth().trim().length()==0)
	   addFieldError("addEventData.getStartmonth", "New Event First Instance month field is empty");
	  else{
		 numbervalidatestatus=validateNumber(addEventData.getStartmonth().trim());
	     if("notvalidnumber".equals(numbervalidatestatus))
	    	 addFieldError("addEventData.getStartmonth", "New Event First Instance month field should be numeric"); 
	  }
	  if(addEventData.getStartday().trim().length()==0)
	   addFieldError("addEventData.getStartday", "New Event First Instance date field is empty");
	  else{
		  numbervalidatestatus=validateNumber(addEventData.getStartday().trim());
		   if("notvalidnumber".equals(numbervalidatestatus))
		    addFieldError("addEventData.getStartday", "New Event First Instance date field should be numeric");  
	  }
	  if(addEventData.getStartyear().trim().length()==0)
	   addFieldError("addEventData.getStartyear", "New Event First Instance year field is empty");  
	  else{
		numbervalidatestatus=validateNumber(addEventData.getStartyear().trim());
		 if("notvalidnumber".equals(numbervalidatestatus))
		   addFieldError("addEventData.getStartyear", "New Event First Instance year field should be numeric"); 
	  }
	  }else{
	 if(addEventData.getStartmonth().trim().length()==0)
	 addFieldError("addEventData.getStartmonth", "New Event Start Time month field is empty");
	 else{
	  numbervalidatestatus=validateNumber(addEventData.getStartmonth().trim());
	    if("notvalidnumber".equals(numbervalidatestatus))
	     addFieldError("addEventData.getStartmonth", "New Event Start Time month field should be numeric"); 
	 }
	 if(addEventData.getStartday().trim().length()==0)
		  addFieldError("addEventData.getStartday", "New Event Start Time date field is empty"); 
	 else{
		 numbervalidatestatus=validateNumber(addEventData.getStartday().trim());
		   if("notvalidnumber".equals(numbervalidatestatus))
		   addFieldError("addEventData.getStartday", "New Event Start Time date field should be numeric");
	 }
	 if(addEventData.getStartyear().trim().length()==0)
		  addFieldError("addEventData.getStartyear", "New Event Start Time year field is empty");
	 else{
		 numbervalidatestatus=validateNumber(addEventData.getStartyear().trim());
		   if("notvalidnumber".equals(numbervalidatestatus))
		   addFieldError("addEventData.getStartyear", "New Event Start Time year field should be numeric");
	 }
	 if(addEventData.getEndmonth().trim().length()==0)
		  addFieldError("addEventData.getEndmonth", "New Event End Time month field is empty");
	 else{
		 numbervalidatestatus=validateNumber(addEventData.getEndmonth().trim());
		   if("notvalidnumber".equals(numbervalidatestatus))
		   addFieldError("addEventData.getEndmonth", "New Event End Time month field should be numeric"); 
	 }
	 if(addEventData.getEndday().trim().length()==0)
		  addFieldError("addEventData.getEndday", "New Event End Time date field is empty");
	 else{
	   numbervalidatestatus=validateNumber(addEventData.getEndday().trim());
		  if("notvalidnumber".equals(numbervalidatestatus))
		  addFieldError("addEventData.getEndday", "New Event End Time date field should be numeric"); 
	 }
	 if(addEventData.getEndyear().trim().length()==0)
		 addFieldError("addEventData.getEndyear", "New Event End Time year field is empty");
	 else{
		numbervalidatestatus=validateNumber(addEventData.getEndyear().trim());
		 if("notvalidnumber".equals(numbervalidatestatus))
		 addFieldError("addEventData.getEndyear", "New Event End Date year field should be numeric");  
	 }
	 }
	 if(getFieldErrors().isEmpty())
	 getEventsCount();
	 if(!getFieldErrors().isEmpty()){
		 copyeventErrorsExists = "true";
		 return false;
		}
	 return true;
	}
	
	public String validateNumber(String number){
		if (number==null || number.length()==0)return "notvalidnumber";
        for (int i = 0; i < number.length(); i++) {
          if (!Character.isDigit(number.charAt(i)))
          return "notvalidnumber";
        }
		return "validnumber";
	}
	
	public String execute(){
    	eventsList = new ArrayList<Entity>();
    	System.out.println("Copy Event eid: "+eid+" powertype: "+powertype);
    	eventsList = MyEventsDB.getAllEvents(mgrId);
    	populateData();
    	if(!"".equals(eid) && !"undefined".equals(eid))
    	return copyFromEvent();
    	else
    	return "success";
    }
    public String copyFromEvent(){
    	System.out.println("Copy From Exist Event eid: "+eid);
    	if(!"".equals(eid) && !"undefined".equals(eid)){
	    	selectedEvent = eid;
	    	eventName = CopyEventDB.getEventName(mgrId, eid);
	    	String accounttype=UpgradeEventDB.getAccountType(mgrId);
	    	oldeventdates=CopyEventDB.getDates(eid,accounttype);
	    	currencySymbol=EventDB.getCurrencySymbol(eid);
	    	populateData();
	    		return "success";
    	}else
    		return "error";
       //getDates();
    	
    }
    
    public String getMgrCardStatus(){
    	//String cardstatus=AddEventDB.getMgrEventStaus(mgrId);
    	String cardstatus="nocard";/*we hard code this cardstatus='nocard' for disable card ask logic
    	                           If in case you want that logic just comment this line and uncomment above line*/
    	System.out.println("cardstatus is:"+cardstatus); 
    	JSONObject json=new JSONObject();
    	try{
    		json.put("cardstatus",cardstatus);
    		json.put("mgrid",mgrId);
    	}catch(Exception e){
    		System.out.println("Exception occured at preparing json in CopyEventAction:"+mgrId);
    	}
    	jsonData=json.toString();
    	System.out.println("jsonData is:"+jsonData); 
    	return "jsondata";
    }
    public void populateData(){
    	DataPopulator.defaultDateDisplay(addEventData);
    	addEventData.setStampm("AM");
		addEventData.setEndampm("AM");
    	hours=DataPopulator.getHours();
		minutes=DataPopulator.getMinutes();
    }
    public String copyEvent(){    	
    	boolean status=validateData(oldeventdates);
    	if(status){
    		String powertype=EventDB.getPowerType(selectedEvent);
    		String accounttype=(String)oldeventdates.get("accounttype");
    		System.out.println("copyEvent eid: "+eid+" powertype: "+powertype+" upgradelevel: "+upgradeLevel+" accounttype: "+accounttype);
    		if("RSVP".equals(powertype)){
    			if("Gold".equalsIgnoreCase(accounttype) || "Platinum".equalsIgnoreCase(accounttype)) addEventData.setUpgradeLevel("150");
    			else if("basic".equals(upgradeLevel)) addEventData.setUpgradeLevel("90");
    			else addEventData.setUpgradeLevel("150");
    		}else{
    			if("Gold".equalsIgnoreCase(accounttype) || "Platinum".equalsIgnoreCase(accounttype)) addEventData.setUpgradeLevel("300");
    			else if("basic".equals(upgradeLevel)) addEventData.setUpgradeLevel("100");
    			else if("pro".equals(upgradeLevel)) addEventData.setUpgradeLevel("200");
    			else addEventData.setUpgradeLevel("300");
    		}
	    	neweventid = CopyEventDB.copyEvent(mgrId,selectedEvent,newEventName,addEventData,ticketlevel,oldeventdates,powertype);
	    	msgKey = "copied successfully";
	    	return "copiedsuccess";
    	}else
    		return "fail";
    	}
    
    public void getEventsCount(){
		System.out.println("mgrid in copyEvent::"+mgrId);
		String listingStatus="";
		String isexists = DbUtil.getVal("select value from mgr_config where name='mgr.eventlisting.allow' and userid=cast(? as integer)",new String[]{mgrId});
		if("N".equals(isexists)){
			addFieldError("listingerror","Your event listing limit crossed. Please contact support@eventbee.com");
			//listingStatus = "donot_allow";
			//return "listingpopup";
		}
		else if("Y".equals(isexists)){
		}
		
		else {
			String count = AddEventDB.getEvtsCount(mgrId);	
			System.out.println("count in copyEvent:::"+count);
			try{
			if(Integer.parseInt(count)<10){}
			else{
				String evtcnt = AddEventDB.getTicketingEvtsCount(mgrId);
				System.out.println("events count copyEvent::"+evtcnt);
				if(Integer.parseInt(evtcnt)==0){
					String query = "insert into mgr_config(name,userid,value,created_at) values('mgr.eventlisting.allow',cast(? as integer),'N',now())";
					 DbUtil.executeUpdateQuery(query, new String[]{mgrId});
					 HttpServletRequest request = ServletActionContext.getRequest();
					 String ip=AddEventAction.getIpFromRequest(request);
					 System.out.println("ip address:::"+ip);					
					 AddEventAction.blockIp(ip);					 
					 query="insert into suspected_user_activities(userid,ip,action,created_at) values(?,?,'event_listing_action',now())";
					 DbUtil.executeUpdateQuery(query,new String[]{mgrId,ip});
					 addFieldError("listingerror","Your event listing limit crossed. Please contact support@eventbee.com");					
				}/*else if(Integer.parseInt(count)>=8){
					String query = "insert into mgr_config(name,userid,value,created_at) values('mgr.eventlisting.allow',cast(? as integer),'N',now())";
					 DbUtil.executeUpdateQuery(query, new String[]{mgrId});
					 HttpServletRequest request = ServletActionContext.getRequest();
					String ip=AddEventAction.getIpFromRequest(request);
					 System.out.println("ip address:::"+ip);	 
					 AddEventAction.blockIp(ip);					 
					 query="insert into suspected_user_activities(userid,ip,action,created_at) values(?,?,'event_listing_action',now())";
					 DbUtil.executeUpdateQuery(query,new String[]{mgrId,ip});
					addFieldError("listingerror","You have listed too many events on the same day. Further listings are blocked for the time being. Please contact support@eventbee.com");
				}	*/
				
				
			}
				
			}catch(Exception e){System.out.println("Exception in copyEvent::"+e.getMessage());}		
		}	
	}
    
   public List<Entity> getEventsList() {
		return eventsList;
	}
	public void setEventsList(List<Entity> eventsList) {
		this.eventsList = eventsList;
	}
	public String getSelectedEvent() {
		return selectedEvent;
	}
	public void setSelectedEvent(String selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	public String getNewEventName() {
		return newEventName;
	}
	public void setNewEventName(String newEventName) {
		this.newEventName = newEventName;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public AddEventData getAddEventData() {
		return addEventData;
	}
	public void setAddEventData(AddEventData addEventData) {
		this.addEventData = addEventData;
	}
	public ArrayList<Entity> getHours() {
		return hours;
	}
	public void setHours(ArrayList<Entity> hours) {
		this.hours = hours;
	}
	public ArrayList<Entity> getMinutes() {
		return minutes;
	}
	public void setMinutes(ArrayList<Entity> minutes) {
		this.minutes = minutes;
	}
	public String getResultdates() {
		return resultdates;
	}
	public void setResultdates(String resultdates) {
		this.resultdates = resultdates;
	}
	public String getTicketlevel() {
		return ticketlevel;
	}
	public void setTicketlevel(String ticketlevel) {
		this.ticketlevel = ticketlevel;
	}
	public HashMap<String, String> getOldeventdates() {
		return oldeventdates;
	}
	public void setOldeventdates(HashMap<String, String> oldeventdates) {
		this.oldeventdates = oldeventdates;
	}
	public String getPowertype() {
		return powertype;
	}
	public void setPowertype(String powertype) {
		this.powertype = powertype;
	}
	public String getCopyeventErrorsExists() {
		return copyeventErrorsExists;
	}
	public void setCopyeventErrorsExists(String copyeventErrorsExists) {
		this.copyeventErrorsExists = copyeventErrorsExists;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getUpgradeLevel() {
		return upgradeLevel;
	}
	public void setUpgradeLevel(String upgradeLevel) {
		this.upgradeLevel = upgradeLevel;
	}
	public String getCurrencySymbol() {
		return currencySymbol;
	}
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}
}
