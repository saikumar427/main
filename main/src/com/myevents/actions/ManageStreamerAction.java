package com.myevents.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import com.event.helpers.DisplayAttribsHelper;
import com.event.helpers.EvtCustomizationHelper;
import com.eventbee.beans.Entity;
import com.eventbee.general.EbeeConstantsF;

import com.eventbee.general.StringEncrypter.EncryptionException;
import com.eventbee.general.helpers.FileWriterHelper;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.user.dbhelpers.UserDisplayAttribsDB;

public class ManageStreamerAction  extends MyEventsActionWrapper implements
Preparable, ValidationAware {
	

	private static final long serialVersionUID = -3358580942644944499L;
	private ArrayList<Entity> fontTypes = new ArrayList<Entity>();
	private ArrayList<Entity> fontSizes = new ArrayList<Entity>();
	private ArrayList<Entity> categories=new ArrayList<Entity>();
	private ArrayList<Entity> locations=new ArrayList<Entity>();
	private ArrayList<Entity> count=new ArrayList<Entity>();
	private String streamerCodeString="";
	private String previewFileName="";
    private ArrayList events=new ArrayList();
	public ArrayList getEvents() {
		return events;
	}
	public void setEvents(ArrayList events) {
		this.events = events;
	}
	public String getPreviewFileName() {
		return previewFileName;
	}
	public void setPreviewFileName(String previewFileName) {
		this.previewFileName = previewFileName;
	}
	public String getStreamerCodeString() {
		return streamerCodeString;
	}
	public void setStreamerCodeString(String streamerCodeString) {
		this.streamerCodeString = streamerCodeString;
	}
	public ArrayList<Entity> getCount() {
		return count;
	}
	public void setCount(ArrayList<Entity> count) {
		this.count = count;
	}
	private HashMap<String,String> streamerData=new HashMap<String,String>();
	String module="maineventstreamer";
	public ArrayList<Entity> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<Entity> categories) {
		this.categories = categories;
	}
	public ArrayList<Entity> getFontTypes() {
		return fontTypes;
	}
	public void setFontTypes(ArrayList<Entity> fontTypes) {
		this.fontTypes = fontTypes;
	}
	public ArrayList<Entity> getFontSizes() {
		return fontSizes;
	}
	public void setFontSizes(ArrayList<Entity> fontSizes) {
		this.fontSizes = fontSizes;
	}
	public HashMap<String, String> getStreamerData() {
		return streamerData;
	}
	public void setStreamerData(HashMap<String, String> streamerData) {
		this.streamerData = streamerData;
	}
	public void fillFontTypes() {
		fontTypes = EvtCustomizationHelper.fillFonts();
	}
	public void fillCategories(){
		categories =EvtCustomizationHelper.fillCategories();
	}
	public void fillFontSizes() {
		fontSizes = EvtCustomizationHelper.fillFontSizes();
	}
	public void fillLocations() {
		locations=EvtCustomizationHelper.fillLocations();
	}
	/**
	 * @param locations the locations to set
	 */
	public void setLocations(ArrayList<Entity> locations) {
		this.locations = locations;
	}
	/**
	 * @return the locations
	 */
	public ArrayList<Entity> getLocations() {
		return locations;
	}
	public String execute(){
		fillFontTypes();
		fillFontSizes();
		fillCategories();
		fillLocations();
		count=EvtCustomizationHelper.fillCount();
		System.out.println("mgrId is"+mgrId);
		streamerData=UserDisplayAttribsDB.getAttribValues(mgrId, module);
		System.out.println("In ManageStreamerAction execute");
		return SUCCESS;
	}

	public void prepare() throws Exception {
		try {
			System.out.println("In ManageStreamerAction prepare");
			super.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public String editStreamer(){
		//streamerData
		System.out.println("In ManageStreamerAction editStreamer method");
		return SUCCESS;
	}
	public String saveStreamerData(){
		System.out.println("mgrId in save method is "+mgrId);
		UserDisplayAttribsDB.insertDisplayAttribs(mgrId, module, streamerData, true);
		return "saved";
	}
	
	public String saveStreamerPreviewData()
	{
		UserDisplayAttribsDB.insertPreviewAttribs(mgrId, module, streamerData, true);
		return "success";
	}
	
	public String preview() throws EncryptionException{
		//System.out.println("mgrId in preview method is "+mgrId);
		//streamerCodeString=DisplayAttribsHelper.getStreamerCodeString(mgrId,streamerData);
		//System.out.println("streamerCodeString in preview method is "+streamerCodeString);*/
		streamerData=UserDisplayAttribsDB.getPreviewAttribValues(mgrId, module);
		events=UserDisplayAttribsDB.getHashmapValues(streamerData,mgrId);
		return "preview";
	}
}
