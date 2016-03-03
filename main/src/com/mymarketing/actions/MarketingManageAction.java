package com.mymarketing.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONObject;

import com.eventbee.general.EventbeeDates;
import com.membertasks.actions.MemberTasksWrapper;
import com.myemailmarketing.beans.MailListDetails;
import com.mymarketing.beans.ScheduleData;
import com.mymarketing.beans.TemplateData;
import com.mymarketing.dbhelpers.MarketingDB;
import com.mymarketing.dbhelpers.MarketingManageDB;
import com.mymarketing.helpers.JsonBuilderHelper;

public class MarketingManageAction extends MemberTasksWrapper{

	private String templateId="";
	private ArrayList<HashMap<String,String>> scheduledList=new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String,String>> completedList=new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String,String>> runningList=new ArrayList<HashMap<String,String>>();
	private String jsonData="";
	private ArrayList<MailListDetails> allMailLists=new ArrayList<MailListDetails>();
	private ArrayList<String> selectedMailLists=new ArrayList<String>();
	private HashMap<String,String> listMembersCount=new HashMap<String,String>();
	private ScheduleData scheduleData=new ScheduleData();
	private String schId="";
	private boolean errorsExists;
	private TemplateData templateData=new TemplateData();
	
	
	public TemplateData getTemplateData() {
		return templateData;
	}

	public void setTemplateData(TemplateData templateData) {
		this.templateData = templateData;
	}

	public boolean isErrorsExists() {
		return errorsExists;
	}

	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}

	public HashMap<String, String> getListMembersCount() {
		return listMembersCount;
	}

	public void setListMembersCount(HashMap<String, String> listMembersCount) {
		this.listMembersCount = listMembersCount;
	}

	public ArrayList<String> getSelectedMailLists() {
		return selectedMailLists;
	}

	public void setSelectedMailLists(ArrayList<String> selectedMailLists) {
		this.selectedMailLists = selectedMailLists;
	}

	public ArrayList<MailListDetails> getAllMailLists() {
		return allMailLists;
	}

	public void setAllMailLists(ArrayList<MailListDetails> allMailLists) {
		this.allMailLists = allMailLists;
	}

	public String execute(){
		return "input";
	}
	
	public String templateInfo(){
		populateListData();
		templateData=MarketingDB.getTemplateData(templateId);		
		actionTitle = "Schedules For Template  > "+templateData.getName();
		
		return "input";
	}
	
	public String populateListData(){
		scheduledList=MarketingManageDB.getScheduledScheduleList(templateId);
		completedList=MarketingManageDB.getCompletedScheduleList(templateId);
		runningList=MarketingManageDB.getRunningScheduleList(templateId);
		
		JSONObject js= JsonBuilderHelper.getSchedulesJson(scheduledList, completedList, runningList);
		jsonData=js.toString();
		return "schedulelistjson";
	}
	
	public String createSchedule(){
		prepareDataforSchedule();
		return "createschedule";
	}
	
	public void prepareDataforSchedule(){
		try {
			allMailLists = MarketingManageDB.getMailLists(userId);
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			scheduleData.setHour(EventbeeDates.getHoursHtml(hour,"scheduleData.time"));
			String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
			String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
			String year = Integer.toString(calendar.get(Calendar.YEAR));
			scheduleData.setDay(day);
			scheduleData.setYear(year);
			scheduleData.setMonth(month);
			if(!schId.equals("")){
				MarketingManageDB.getScheduleData(schId, templateId, scheduleData);
				if(scheduleData.getSchtimeType().equals("date")){
					hour=Integer.parseInt(scheduleData.getHour());
					scheduleData.setHour(EventbeeDates.getHoursHtml(hour,"scheduleData.time"));
					scheduleData.setTime(hour+"");
				}else if(scheduleData.getSchtimeType().equals("later")){
					scheduleData.setHour(EventbeeDates.getHoursHtml(hour,"scheduleData.time"));
					scheduleData.setDay(day);
					scheduleData.setYear(year);
					scheduleData.setMonth(month);
					scheduleData.setTime(hour+"");
				}
				
				String[] mailListArray = null;
			    if (scheduleData.getMailList() != null || !scheduleData.getMailList().equalsIgnoreCase("")){
			    	mailListArray = scheduleData.getMailList().split(",");
			        selectedMailLists.addAll(Arrays.asList(mailListArray));
			    }
			}
			
			String schName="";
			if(scheduleData.getName().equals(""))
				schName="Create Schedule";
			else
				schName=scheduleData.getName();
			
			actionTitle = "<a href='marketingmanage!templateInfo?templateId="+ templateId + "'>" + "Marketing Schedules"
					+ "</a> > "+schName;
			listMembersCount=MarketingManageDB.getMaillistMembersCount();
		} catch (Exception e) {
			System.out.println("There is an error "+e);
		}
	}
	
	public String saveSchedule(){
		boolean status = validateInputData();
		if(status){
			StringBuffer sb= new StringBuffer();
			if(selectedMailLists.size()>0){
				for(int i=0;i<selectedMailLists.size();i++)
					sb.append(selectedMailLists.get(i)+",");
				sb.deleteCharAt(sb.length()-1);
				scheduleData.setMailList(sb.toString());
			}
			scheduleData.setSchId(schId);
			scheduleData.setTemplateId(templateId);
			MarketingManageDB.saveScheduleData(scheduleData);
			templateData=MarketingDB.getTemplateData(templateId);
			actionTitle = "Schedules For Template  > "+templateData.getName();
			
			return SUCCESS;
		}else
			//prepareDataforSchedule();
		return "inputerror";
		
	}
	
	public String deleteSchedule(){
		MarketingManageDB.deleteSchedule(schId);
		return "input";
	}
	
	public boolean validateInputData() {
		
		if ("".equals(scheduleData.getName())) {
			addFieldError("templateData.name", "Template Name is empty");
		}
		if (selectedMailLists.size()==0) {
			addFieldError("error", "Select at least one mailing list");
		}
		
		
		if (!getFieldErrors().isEmpty()) {
			errorsExists = true;
			return false;
		}
		return true;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public ArrayList<HashMap<String, String>> getScheduledList() {
		return scheduledList;
	}

	public void setScheduledList(ArrayList<HashMap<String, String>> scheduledList) {
		this.scheduledList = scheduledList;
	}

	public ArrayList<HashMap<String, String>> getCompletedList() {
		return completedList;
	}

	public void setCompletedList(ArrayList<HashMap<String, String>> completedList) {
		this.completedList = completedList;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getSchId() {
		return schId;
	}

	public void setSchId(String schId) {
		this.schId = schId;
	}

	public ScheduleData getScheduleData() {
		return scheduleData;
	}

	public void setScheduleData(ScheduleData scheduleData) {
		this.scheduleData = scheduleData;
	}

	public ArrayList<HashMap<String, String>> getRunningList() {
		return runningList;
	}

	public void setRunningList(ArrayList<HashMap<String, String>> runningList) {
		this.runningList = runningList;
	}

	
}
