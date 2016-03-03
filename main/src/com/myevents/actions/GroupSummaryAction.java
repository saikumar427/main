package com.myevents.actions;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;
import org.json.JSONObject;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.EventsGroupDB;
import com.event.dbhelpers.ThemesDB;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.helpers.FileWriterHelper;
import com.eventbee.general.helpers.PreviewHelper;
import com.eventmanage.helpers.JsonBuilderHelper;
import com.myevents.beans.EventsGroup;
import com.myevents.helpers.EventsJsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class GroupSummaryAction extends MyEventsActionWrapper implements
Preparable, ValidationAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5045441955770338277L;
	private String gid="";
	private String shortURL="";
	private String customURL="";
	private String msg="";
	private EventsGroup eventGroupData=new EventsGroup();
	private HashMap<String,String> themeContent=new HashMap<String,String>();
	private Vector<HashMap<String,String>> groupEvents=null;
	private String jsonData = "";
	private String previewFileName="";
	
	public String getPreviewFileName() {
		return previewFileName;
	}

	public void setPreviewFileName(String previewFileName) {
		this.previewFileName = previewFileName;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String execute(){
		eventGroupData= EventsGroupDB.getEventsGroupData(gid, mgrId);
		if(eventGroupData.getTitle()==null) return "invalid";
		groupEvents=EventsGroupDB.getDBGroupEvents(gid);
		jsonData = EventsJsonBuilder.getGroupEventsJson(groupEvents).toString();
		System.out.println("Events count of group id: "+gid +" is "+groupEvents.size());
		actionTitle=eventGroupData.getTitle();
		eventGroupData.setEventURL(EventDB.getEventURL(gid,eventGroupData.getUserName()));
		eventGroupData.setCustomURL(EventDB.getUserURL(gid,eventGroupData.getUserName()));
		return SUCCESS;
	}
	public HashMap<String, String> getThemeContent() {
		return themeContent;
	}
	public void setThemeContent(HashMap<String, String> themeContent) {
		this.themeContent = themeContent;
	}
	public String editGroupTheme(){
		//Get the values
		themeContent=ThemesDB.getThemeContent(gid,mgrId);
		return "themeinput";
	}
	
	public String saveGroupTheme(){
		System.out.println("In saveGroupTheme");
		ThemesDB.saveGroupTheme(themeContent,gid,mgrId);
		return "themesaved";
	}
	public String GroupThemePreview(){
		String CSS= themeContent.get("CSS");
		String previewData=PreviewHelper.processPreviewData(CSS);
		Random rand = new Random();
	    int num = rand.nextInt(99999);
	    previewFileName=mgrId+"_"+num;
	    FileWriterHelper.doWrite(previewFileName, previewData);
		return "preview";
	}
	public String setCustomURL(){
		System.out.println("custom URL eid: "+gid +" mgrid: "+mgrId);
		msg=EventDB.setCustomURL(gid,mgrId,shortURL,customURL);
		if("inserted".equals(msg) || "".equals(msg)){
			eventGroupData= EventsGroupDB.getEventsGroupData(gid, mgrId);
			JSONObject jsonObj=new JSONObject();
			try{
			jsonObj.put("url",EventDB.getEventURL(gid,eventGroupData.getUserName()));
			jsonObj.put("newurl",customURL);
			msg=jsonObj.toString();
			}catch(Exception e){
				System.out.println("the exception is::"+e.getMessage());
			}
			return "ajaxjson";
		}
		
		
		System.out.println(msg);
		return "ajaxmsg";
	}
	public String customURL(){		
		eventGroupData.setCustomURL(EventDB.getUserURL(gid,eventGroupData.getUserName()));
		eventGroupData.setMgrId(EventDB.getUserId(gid));
		return "customurl";
	}
	
	public String rebuildJson()
	{  System.out.println("in rebuld");
		groupEvents=EventsGroupDB.getDBGroupEvents(gid);
		jsonData = EventsJsonBuilder.getGroupEventsJson(groupEvents).toString();
		return "jsondata";
	}
	
	
	
	public void prepare() throws Exception {
		try {
			super.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public EventsGroup getEventGroupData() {
		return eventGroupData;
	}
	public void setEventGroupData(EventsGroup eventGroupData) {
		this.eventGroupData = eventGroupData;
	}
	public String getShortURL() {
		return shortURL;
	}
	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}
	public String getCustomURL() {
		return customURL;
	}
	public void setCustomURL(String customURL) {
		this.customURL = customURL;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Vector<HashMap<String, String>> getGroupEvents() {
		return groupEvents;
	}
	public void setGroupEvents(Vector<HashMap<String, String>> groupEvents) {
		this.groupEvents = groupEvents;
	}


}
