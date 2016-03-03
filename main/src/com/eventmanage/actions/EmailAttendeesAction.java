package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.event.beans.EmailAttendeesData;
import com.event.dbhelpers.EmailAttendeesDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventmanage.helpers.EmailAttendeesJsonHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class EmailAttendeesAction extends ActionSupport implements Preparable,ValidationAware{

	private static final long serialVersionUID = 3638845334032069827L;
	private EmailAttendeesData emailAttendeesData=new EmailAttendeesData();
	private ArrayList<Entity> years=new ArrayList<Entity>();
	private String blastid="";
	private String eid="";
	private String msg="";
	private String mytextarea;	
	private String day="";
	private String month="";
	private String year="";
	private String now="";
	private String time="";
	private String powertype="";
	private String mgrId="";
	private String actiontype="";
	private String content="";
	private String isErrorExist="";
	private String errormsg="yes";	
	private ArrayList<Entity> hours = new ArrayList<Entity>();
	private ArrayList<Entity> minutes = new ArrayList<Entity>();
	private ArrayList<Entity> descriptiontype=new ArrayList<Entity>();
	private ArrayList<Entity> ticketsList=new ArrayList<Entity>();
	private ArrayList selectedTkts=new ArrayList();
	private ArrayList chkedTickets=new ArrayList();
	private String startDate="";
	
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public ArrayList getChkedTickets() {
		return chkedTickets;
	}
	public void setChkedTickets(ArrayList chkedTickets) {
		this.chkedTickets = chkedTickets;
	}
	public ArrayList getSelectedTkts() {
		return selectedTkts;
	}
	public void setSelectedTkts(ArrayList selectedTkts) {
		this.selectedTkts = selectedTkts;
	}
	public ArrayList<Entity> getTicketsList() {
		return ticketsList;
	}
	public void setTicketsList(ArrayList<Entity> ticketsList) {
		this.ticketsList = ticketsList;
	}
	private HashMap<String,String> AttendeeList=new HashMap<String,String>();
	private String blastschtype="";
	private boolean isrecurring=false;
	
	
	private String msgType = "emailattendees";
	private String jsonData="";	
	
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getNow() {
		return now;
	}
	public void setNow(String now) {
		this.now = now;
	}
	public String getMytextarea() {
		return mytextarea;
	}
	public void setMytextarea(String mytextarea) {
		this.mytextarea = mytextarea;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public HashMap<String, String> getAttendeeList() {
		return AttendeeList;
	}
	public void setAttendeeList(HashMap<String, String> attendeeList) {
		AttendeeList = attendeeList;
	}
	public EmailAttendeesData getEmailAttendeesData() {
		return emailAttendeesData;
	}
	public void setEmailAttendeesData(EmailAttendeesData emailAttendeesData) {
		this.emailAttendeesData = emailAttendeesData;
	}
	public ArrayList<Entity> getDescriptiontype() {
		return descriptiontype;
	}
	public void setDescriptiontype(ArrayList<Entity> descriptiontype) {
		this.descriptiontype = descriptiontype;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public void populateDescriptionTypes(){			
			descriptiontype.add(new Entity("text","Text"));
			descriptiontype.add(new Entity("html","HTML"));
			descriptiontype.add(new Entity("wysiwyg"," WYSIWYG Editor"));
	}
	
	public String execute(){
		
		
		//populateDescriptionTypes();
		String powertype=EventDB.getPowerType(eid);
		if("Ticketing".equalsIgnoreCase(powertype))
			ticketsList=EmailAttendeesDB.getTicketsList(eid);
		
		
		emailAttendeesData.setContentlist(EmailAttendeesDB.getCopyFromDetails(eid));
		isrecurring=EventDB.isRecurringEvent(eid);
		if(isrecurring)
			emailAttendeesData.setRecurringlist(EmailAttendeesDB.getRecurringList(eid,powertype));
		int count=EmailAttendeesDB.getEmailCount(eid);
		if(count==0)
		{ 
			try{
				jsonData=new JSONObject().put("sent", new JSONArray()).put("drafts", new JSONArray()).put("scheduled", new JSONArray()).toString();
			}catch(Exception e){				
			}			
		}
		else
		{	
		ArrayList scheduledList=EmailAttendeesDB.getScheduledList(eid);
		ArrayList draftsList=EmailAttendeesDB.getDraftedList(eid);
		ArrayList sentList=EmailAttendeesDB.getSentList(eid);
		JSONObject js= EmailAttendeesJsonHelper.getEmailAttendeesJson(scheduledList,draftsList,sentList);
		jsonData=js.toString();
		
		}

		return SUCCESS;
	}
	public String reloadData(){
		execute();
		return "emailAttendeeJson";
	}
	
	public String deleteMail() {
		boolean status = EmailAttendeesDB.deleteMail(blastid, eid);
		execute();
		
		return "emailAttendeeJson";             
	}
	
	
	/*public String jsonBuilder(){
		execute();
	}*/
	
	/*public void populateYearTypes()
	{
		years.add(new Entity("2012", "2012"));
		years.add(new Entity("2013", "2013"));
		years.add(new Entity("2014", "2014"));
		years.add(new Entity("2015", "2015"));
		years.add(new Entity("2016", "2016"));
		years.add(new Entity("2017", "2017"));
		years.add(new Entity("2018", "2018"));
		years.add(new Entity("2019", "2019"));
		years.add(new Entity("2020", "2020"));
		years.add(new Entity("2021", "2021"));
		years.add(new Entity("2022", "2022"));
		years.add(new Entity("2023", "2023"));
		years.add(new Entity("2024", "2024"));
		years.add(new Entity("2025", "2025"));
		
	}*/
	public void populateTime() {
		hours = EmailAttendeesDB.getHours();
		minutes = EmailAttendeesDB.getMinutes();
	}
	public String getEmailAttendee(){
		System.out.println("in get attendee");
		powertype=EventDB.getPowerType(eid);
		populateDescriptionTypes();
		//populateYearTypes();
		populateTime();
		EmailAttendeesDB.getCreateAttendeeDetails(eid,emailAttendeesData);
		emailAttendeesData.setContentlist(EmailAttendeesDB.getCopyFromDetails(eid));
		emailAttendeesData.setEmail(EmailAttendeesDB.getMgrEmail(eid));
		if("Ticketing".equalsIgnoreCase(powertype))
		ticketsList=EmailAttendeesDB.getTicketsList(eid);
		
	    isrecurring=EventDB.isRecurringEvent(eid);
		if(isrecurring)
			emailAttendeesData.setRecurringlist(EmailAttendeesDB.getRecurringList(eid,powertype));
		return "emailattendee";
		
	}
	public String editEmailAttendee(){
		
		populateDescriptionTypes();
		populateTime();
		isrecurring=EventDB.isRecurringEvent(eid);
		powertype=EventDB.getPowerType(eid);
		emailAttendeesData=EmailAttendeesDB.getEditDetails(blastid, eid);
		
		emailAttendeesData.setStartDate(emailAttendeesData.getDateTimeBeanObj().getMonthPart()+"/"+emailAttendeesData.getDateTimeBeanObj().getDdPart()+"/"+emailAttendeesData.getDateTimeBeanObj().getYyPart()+" "+emailAttendeesData.getDateTimeBeanObj().getHhPart()+":"+emailAttendeesData.getDateTimeBeanObj().getMmPart()+" "+emailAttendeesData.getDateTimeBeanObj().getAmpm());
		if("Ticketing".equalsIgnoreCase(powertype)){
			ticketsList=EmailAttendeesDB.getTicketsList(eid);
			if(!"ALL".equalsIgnoreCase(emailAttendeesData.getSendTo()))
					selectedTkts=EmailAttendeesDB.getSelectedTktsList(emailAttendeesData.getSelectedTickets());
			emailAttendeesData.setStartDate(emailAttendeesData.getDateTimeBeanObj().getMonthPart()+"/"+emailAttendeesData.getDateTimeBeanObj().getDdPart()+"/"+emailAttendeesData.getDateTimeBeanObj().getYyPart()+" "+emailAttendeesData.getDateTimeBeanObj().getHhPart()+":"+emailAttendeesData.getDateTimeBeanObj().getMmPart()+" "+emailAttendeesData.getDateTimeBeanObj().getAmpm());
		}
		
		return "emailattendee";
	}
	
	public String saveMail(){
		boolean status = validateInputData();
		if(status){
			isErrorExist="no";
			mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
			if(powertype.equals("RSVP"))
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP Email Attendees");
			else
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"200","Email Attendees");
			EmailAttendeesDB.saveMail(blastid,eid,emailAttendeesData,chkedTickets,powertype);
			blastschtype=emailAttendeesData.getSendAt();
			JSONObject jsonobj=new JSONObject();
			try{
			jsonobj.put("blastschtype",blastschtype);
			jsonobj.put("success","success");
			msg=jsonobj.toString();
			}catch(Exception e){
				System.out.println("Exception Occured in saveMail Method."+e.getMessage());
			}
		}
		else 
			{isErrorExist="yes";
		return "inputerror";
	}
		return "ajaxjson";
	//return "success";	
	}
	
	public String showEmailContent(){
		
		populateDescriptionTypes();
		emailAttendeesData=EmailAttendeesDB.showContent(blastid, eid);
		emailAttendeesData.getContent();
		return "content";
	}
	
	public String contentShow()	{
		return "success";
	}
	
	public String sendTestmail(){
		populateDescriptionTypes();
		return "sendtestmail";
	}
	public String sendTest(){
		ResourceBundle resourceBundle =I18n.getResourceBundle();
	    if("".equals(emailAttendeesData.getMailtotest().trim())){
			addFieldError("EmailAttendeesData.mailtotest",resourceBundle.getString("em.mail.emty.lbl"));
			return "inputerror";
		}else if(!isValidSendEmail(emailAttendeesData.getMailtotest().trim())){
			addFieldError("EmailAttendeesData.email",resourceBundle.getString("em.inv.mail.frmt.lbl"));
			return "inputerror";
		}
	    EmailAttendeesDB.getDetailsForMailSend(eid,blastid,emailAttendeesData);
		EmailAttendeesDB.sendTestMail(eid,emailAttendeesData,"");
	
		return "json";
	}
	
public boolean validateInputData(){
	ResourceBundle resourceBundle =I18n.getResourceBundle();
	if("Y".equals(emailAttendeesData.getBuyer().trim())&& "".equals(emailAttendeesData.getAttendee().trim()))
		addFieldError("EmailAttendeesData.Buyer",resourceBundle.getString("em.actions.must.be.to.buyer"));
	
	/*if ("".equals(emailAttendeesData.getEmail().trim()))
		addFieldError("EmailAttendeesData.email", " From email is empty");
	else if(!EventBeeValidations.isValidFromEmail(emailAttendeesData.getEmail().trim()))
		addFieldError("EmailAttendeesData.email", " Invalid email " );*/
	if("".equals(emailAttendeesData.getSubject().trim())) 
		addFieldError("EmailAttendeesData.subject",resourceBundle.getString("em.sub.emty.lbl"));
	
	
	if("wysiwyg".equals(emailAttendeesData.getDescriptiontype())){
		String content=emailAttendeesData.getDescription().trim();
		
		String result=content.replaceAll("&nbsp;"," ").trim();
		result=result.replaceAll("<br>"," ").trim();

		
	
		if("".equals(result)){
		addFieldError("EmailAttendeesData.description",resourceBundle.getString("em.cntnt.emty.lbl"));
		}
		
		else if(result.split(("span")).length==3)
		{
					
			int spi=0;
					spi=result.lastIndexOf("span");String pos="";
					try{ pos=(result.charAt(spi+5))+"";}catch(Exception e){System.out.println(e);pos="";}
					String compare=pos;
					
					if("".equals(compare))
					{
					addFieldError("EmailAttendeesData.description",resourceBundle.getString("em.cntnt.emty.lbl"));
		             }
       }
	}else if(!"wysiwyg".equals(emailAttendeesData.getDescriptiontype()) && "".equals(emailAttendeesData.getContent().trim())){
		addFieldError("EmailAttendeesData.content", resourceBundle.getString("em.cntnt.emty.lbl"));
	}
	if("now".equals(emailAttendeesData.getSendAt().trim())){
	boolean isExists=EmailAttendeesDB.getTransactionExists(eid,emailAttendeesData.getSeldate());
	if(!isExists)
		addFieldError("EmailAttendeesData.content",resourceBundle.getString("em.you.hav.attnds.snd.mail.lbl"));
	}
	if("date".equals(emailAttendeesData.getSendAt())){	
		if("".equals(emailAttendeesData.getDateTimeBeanObj().getMonthPart()))
			addFieldError("emailAttendeesData.dateTimeBeanObj.monthPart",resourceBundle.getString("em.snd.Date.Mnth.emty.lbl"));
		else {
			try {
				Integer.parseInt(emailAttendeesData.getDateTimeBeanObj().getMonthPart());
			} catch (Exception e) {
				// TODO: handle exception
				addFieldError("emailAttendeesData.dateTimeBeanObj.monthPart",resourceBundle.getString("em.snd.date.mnth.fld.num.lbl"));
			}
		}	
		if("".equals(emailAttendeesData.getDateTimeBeanObj().getDdPart()))
			addFieldError("emailAttendeesData.dateTimeBeanObj.ddPart",resourceBundle.getString("em.snd.date.date.emty.lbl"));
		else {
			try {
				Integer.parseInt(emailAttendeesData.getDateTimeBeanObj().getDdPart());
			} catch (Exception e) {
				// TODO: handle exception
				addFieldError("emailAttendeesData.dateTimeBeanObj.ddpart",resourceBundle.getString("em.snd.date.fld.num.lbl"));
			}
		}	
		if("".equals(emailAttendeesData.getDateTimeBeanObj().getYyPart()))
			addFieldError("emailAttendeesData.dateTimeBeanObj.yypart",resourceBundle.getString("em.snd.date.year.emty.lbl"));
	      else{
			try {
				Integer.parseInt(emailAttendeesData.getDateTimeBeanObj().getYyPart());
			} catch (Exception e) {
				// TODO: handle exception
				addFieldError("emailAttendeesData.dateTimeBeanObj.yyPart",resourceBundle.getString("em.snd.date.year.num.lbl"));
			}
		}	
		}
	if (!getFieldErrors().isEmpty()) {
		return false;
	}
	return true;
	
}

public boolean isValidSendEmail(String email){
    Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
    Matcher m = p.matcher(email);
    boolean matchFound = m.matches();
    if (matchFound)
      return true;
    else
      return false;
}
public String getBlastDetails(){
	
	HashMap<String,String> blastMap=EmailAttendeesDB.getBlastDetails(blastid,eid);
	JSONObject json=new JSONObject();
	try {
		json.put("blast", blastMap);
	}catch (Exception e) {
		// TODO: handle exception
	}
	msg=json.toString();
	
	return "ajaxjson";
}
	
	         

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getPowertype() {
		return powertype;
	}
	public void setPowertype(String powertype) {
		this.powertype = powertype;
	}
	public String getMgrId() {
		return mgrId;
	}
	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}
	public String getBlastid() {
		return blastid;
	}
	public void setBlastid(String blastid) {
		this.blastid = blastid;
	}
	public String getActiontype() {
		return actiontype;
	}
	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}
	public void getContentDetails()
	{
		emailAttendeesData.setDescription(content);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<Entity> getYears() {
		return years;
	}
	public void setYears(ArrayList<Entity> years) {
		this.years = years;
	}
	public String getIsErrorExist() {
		return isErrorExist;
	}
	public void setIsErrorExist(String isErrorExist) {
		this.isErrorExist = isErrorExist;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
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
	public String getBlastschtype() {
		return blastschtype;
	}
	public void setBlastschtype(String blastschtype) {
		this.blastschtype = blastschtype;
	}
	public boolean isIsrecurring() {
		return isrecurring;
	}
	public void setIsrecurring(boolean isrecurring) {
		this.isrecurring = isrecurring;
	}	
}
