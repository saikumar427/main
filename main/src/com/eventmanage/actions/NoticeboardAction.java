package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.event.dbhelpers.NoticeboardDB;
import com.eventbee.beans.Entity;
import com.eventmanage.helpers.JsonBuilderHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class NoticeboardAction extends ActionSupport implements Preparable,ValidationAware{

	private static Logger log = Logger.getLogger(NoticeboardAction.class);
	private static final long serialVersionUID = -125798731031021774L;
	private ArrayList<Entity> noticeTypeList=new ArrayList<Entity>();
	private HashMap<String,String> noticeData=new HashMap<String,String>();
	private String eid="";
	private String noticeId="";
	private String jsonData="";
	
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public HashMap<String, String> getNoticeData() {
		return noticeData;
	}

	public void setNoticeData(HashMap<String, String> noticeData) {
		this.noticeData = noticeData;
	}

	public ArrayList<Entity> getNoticeTypeList() {
		return noticeTypeList;
	}

	public void setNoticeTypeList(ArrayList<Entity> noticeTypeList) {
		this.noticeTypeList = noticeTypeList;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String newNotice(){
		return "noticepopup";
}

	
	
	
	public String addNotice(){
		populateNoticeTypes();
		if(log.isDebugEnabled())
			log.debug("addNotice Method called");
		return "addnotice";
	}
	public void populateNoticeTypes(){
		noticeTypeList.clear();
		noticeTypeList.add(new Entity("Alert","Alert"));
		noticeTypeList.add(new Entity("Info","Info"));
		noticeTypeList.add(new Entity("Message","Message"));
	}
	public String saveNoticeData(){
		System.out.println("in save"+noticeData);
		NoticeboardDB.saveNoticeData(noticeData,eid);
		return "noticesaved";
	}
	public String editNoticeData(){
		populateNoticeTypes();
		if(log.isDebugEnabled())
			log.debug("editNoticeData Method called");
		noticeData=NoticeboardDB.getNoticeInfo(noticeId);
		return "noticepopup";
		
	}
	public String deleteNotice(){
		NoticeboardDB.deleteNotices(noticeId);
		return "noticeDeleted";
	}
	public String getNoticeItems(){
		ArrayList<HashMap<String,String>> notices=NoticeboardDB.getAllNotices(eid);
		System.out.println("notices"+notices);
		jsonData=JsonBuilderHelper.getNoticesListJson(notices).toString();
		System.out.println("jsonData"+jsonData);
		return "jsondata";
	}
	public String getNoticeItemsAfterAdd(){
		ArrayList<HashMap<String,String>> notices=NoticeboardDB.getAllNotices(eid);
		System.out.println("notices"+notices);
		jsonData=JsonBuilderHelper.getNoticesListJson(notices).toString();
		System.out.println("jsonData"+jsonData);
		return "jsonafteradd";
	}
}
