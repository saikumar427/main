package com.myevents.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.JSONObject;
import com.event.dbhelpers.PaymentSettingsDB;
import com.event.dbhelpers.UpgradeEventDB;
import com.event.themes.ThemeController;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.dbhelpers.PreviewDBHelper;
import com.eventbee.general.formatting.WriteSelectHTML;
import com.eventbee.general.helpers.FileWriterHelper;
import com.eventbee.general.helpers.PreviewHelper;
import com.eventbee.payments.dbhelpers.CardProcessorDB;
import com.myevents.beans.BoxOfficeData;
import com.myevents.beans.UserThemes;
import com.myevents.dbhelpers.BoxOfficeDB;
import com.myevents.dbhelpers.UserCustomThemeDB;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.event.dbhelpers.AddEventDB;;

public class CustomizeEventsPageThemeAction extends MyEventsActionWrapper implements Preparable, ValidationAware {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5286279172444818935L;
	private UserThemes userThemes=new UserThemes();
	private String theme="";
	private String themetype="";
	private String mytheme="";
	private String htmlContent="";
	private String cssContent="";
	private String msgKey = "";
	private String eventphotoURL="";
	private String uploadurl="";
	private HashMap<String,String> attribMap=new HashMap<String,String>();
	private String myeventpagethemename="";
	private String previewFileName="";
	private String CSS="";
	private String shortUrl="";
	private boolean isfbinstalled=false;
	
	
	
	private ArrayList<Entity> allEvents=new ArrayList<Entity>();
	private ArrayList<Entity> selEvents=new ArrayList<Entity>();
	private BoxOfficeData boxoffice=new BoxOfficeData();
	
	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public BoxOfficeData getBoxoffice() {
		return boxoffice;
	}

	public void setBoxoffice(BoxOfficeData boxoffice) {
		this.boxoffice = boxoffice;
	}
	public ArrayList<Entity> getAllEvents() {
		return allEvents;
	}

	public void setAllEvents(ArrayList<Entity> allEvents) {
		this.allEvents = allEvents;
	}
	public ArrayList<Entity> getSelEvents() {
		return selEvents;
	}

	public void setSelEvents(ArrayList<Entity> selEvents) {
		this.selEvents = selEvents;
	}
	
	public String getCSS() {
		return CSS;
	}
	public void setCSS(String css) {
		CSS = css;
	}
	public String getPreviewFileName() {
		return previewFileName;
	}
	public void setPreviewFileName(String previewFileName) {
		this.previewFileName = previewFileName;
	}
	public String getEventphotoURL() {
		return eventphotoURL;
	}
	public void setEventphotoURL(String eventphotoURL) {
		this.eventphotoURL = eventphotoURL;
	}
	public String getUploadurl() {
		return uploadurl;
	}
	public void setUploadurl(String uploadurl) {
		this.uploadurl = uploadurl;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getMyeventpagethemename() {
		return myeventpagethemename;
	}
	public void setMyeventpagethemename(String myeventpagethemename) {
		this.myeventpagethemename = myeventpagethemename;
	}
	public HashMap<String, String> getAttribMap() {
		return attribMap;
	}
	public void setAttribMap(HashMap<String, String> attribMap) {
		this.attribMap = attribMap;
	}
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public String getCssContent() {
		return cssContent;
	}
	public void setCssContent(String cssContent) {
		this.cssContent = cssContent;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getThemetype() {
		return themetype;
	}
	public void setThemetype(String themetype) {
		this.themetype = themetype;
	}
	public String getMytheme() {
		return mytheme;
	}
	public void setMytheme(String mytheme) {
		this.mytheme = mytheme;
	}
	public UserThemes getUserThemes() {
		return userThemes;
	}
	public void setUserThemes(UserThemes userThemes) {
		this.userThemes = userThemes;
	}
	public void prepare() throws Exception {
		try {
			super.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}	
	public String execute(){
		
		allEvents=BoxOfficeDB.getAllEvents(mgrId);
		selEvents=BoxOfficeDB.getSelEvents(mgrId);
		boxoffice=BoxOfficeDB.getBoxOfficeDetails(mgrId);
	   isfbinstalled=BoxOfficeDB.checkFBInstalled(mgrId);
	   System.out.println("is fb installed::"+isfbinstalled);
	   		if(isfbinstalled)
	   			boxoffice=BoxOfficeDB.getFBDetails(mgrId,boxoffice);
		
		Map themesmap=ThemeController.getThemes(new String [] {"eventspage"});
		String themevals[]=(String [])themesmap.get("themevals");
		String themenames[]=(String [])themesmap.get("themenames");
		Map mythemesmap=ThemeController.getMyThemes(new String [] {mgrId,"eventspage"});
		String mythemevals[]=new String[0];
		String mythemenames[]=new String[0];
		if(mythemesmap!=null&&mythemesmap.size()>0){
			mythemevals=(String [])mythemesmap.get("themevals");
			mythemenames=(String [])mythemesmap.get("themenames");
		}
		String [] themedata=UserCustomThemeDB.getMyPublicPageThemeCodeAndType("eventspage",mgrId,"basic");
		String selectedtheme=themedata[1];
		String selectedthemetype=themedata[0];
		HashMap hm=new HashMap();
		String theme=WriteSelectHTML.getSelectHtml(themenames,themevals,"theme",selectedtheme,null,null );
		String userTheme=WriteSelectHTML.getSelectHtml(mythemenames,mythemevals,"mytheme",selectedtheme,null,null,"onChange='submitform()'");
		userThemes.setEventbeeThemes(theme);
		userThemes.setUserThemes(userTheme);		
		userThemes.setThemetype(selectedthemetype);
		userThemes.setSelectedTheme(selectedtheme);
		userThemes.setNamesArrayLength(mythemenames.length);
		String query="";
		String[] params=null;
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String HTML="";
		String CSS="";
		if("PERSONAL".equals(selectedthemetype)){
			query="select content as html,cssurl as css from user_customized_themes " +
					"where module='eventspage' and userid=? and themeid=? ";
			params=new String[]{mgrId,selectedtheme};
			myeventpagethemename=DbUtil.getVal("select themename from user_customized_themes where " +
					"module='eventspage' and userid=? and themeid=?", new String[]{mgrId,selectedtheme});
			userThemes.setMyeventpagethemename(myeventpagethemename);
			
		}else{
			query="select defaultcontent as html,cssurl as css from ebee_roller_def_themes where " +
					"module='eventspage' and themecode='basic'";
			params=new String[]{};
			
		}
		statobj=dbmanager.executeSelectQuery(query,params);
		if(statobj.getStatus() && statobj.getCount()>0){
		HTML=dbmanager.getValue(0, "html", "");
		CSS=dbmanager.getValue(0, "css", "");
		attribMap.put("HTML",HTML);
		attribMap.put("CSS",CSS);
		}		
		String[] values=new String[]{"Yes","No"};
		String[] captions=new String[]{"Show","Hide"};
		String[] upComingEventValues=new String[]{"Yes","Selected"};
		String[] upComingEventCaptions=new String[]{"Show All","Show Selected"};
		String def_val="Yes";
		StatusObj sobj=DbUtil.getKeyValues("select pref_name,pref_value from member_preference" +
				" where user_id=? ",new String[]{mgrId});
		HashMap prefMap=new HashMap();
		if(sobj.getCount()>0){
			prefMap=(HashMap) sobj.getData();
		}
		String upcomingEvents="";
		 upcomingEvents=DbUtil.getVal("select pref_value   from member_preference where user_id=? and pref_name=?", new String[]{mgrId, "events.upcomingEvents"});
		if("".equals(upcomingEvents) || upcomingEvents==null)
			upcomingEvents="Yes";
			
		
		//String upcomingEvents=WriteSelectHTML.getSelectOneRadio("userThemes.upcomingEvents",upComingEventValues,upComingEventCaptions,GenUtil.getHMvalue(prefMap,"events.upcomingEvents",def_val),"onclick='javascript:showdiv(this)'");
		String pastEvents="";
		 pastEvents=DbUtil.getVal("select pref_value   from member_preference where user_id=? and pref_name=?", new String[]{mgrId, "events.pastEvents"});
		 if("".equals(pastEvents) || pastEvents==null)
			 pastEvents="Yes";
		 
		 //String pastEvents=WriteSelectHTML.getSelectOneRadio("userThemes.pastEvents",values,captions,GenUtil.getHMvalue(prefMap,"events.pastEvents",def_val));
		String photoDisplay=WriteSelectHTML.getSelectOneRadio("userThemes.photoDisplay",values,captions,GenUtil.getHMvalue(prefMap,"events.photoDisplay",def_val));
		String profileDisplay=WriteSelectHTML.getSelectOneRadio("userThemes.ProfileDisplay",values,captions,GenUtil.getHMvalue(prefMap,"events.ProfileDisplay",def_val));
		userThemes.setUpcomingEvents(upcomingEvents);
		userThemes.setPastEvents(pastEvents);
		userThemes.setPhotoDisplay(photoDisplay);
		userThemes.setProfileDisplay(profileDisplay);
		actionTitle="My Box Office Page Settings";
		//userThemes.setPhoto(UserCustomThemeDB.getConfigVal(mgrId,"event.eventsPublicPagephotoURL",""));
		userThemes.setPhoto(UserCustomThemeDB.getBoxOfficePhotoURL(mgrId));
		shortUrl = BoxOfficeDB.getShortURL(mgrId);
		return "manage";		
	}
	public boolean isIsfbinstalled() {
		return isfbinstalled;
	}

	public void setIsfbinstalled(boolean isfbinstalled) {
		this.isfbinstalled = isfbinstalled;
	}

	public String edit(){
		System.out.println("\n In edit");
		String [] themedata=UserCustomThemeDB.getMyPublicPageThemeCodeAndType("eventspage",mgrId,"basic");
		String selectedtheme=themedata[1];
		String selectedthemetype=themedata[0];
		userThemes.setSelectedTheme(selectedtheme);
		String query="";
		String[] params=null;
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String HTML="";
		String CSS="";
		if("PERSONAL".equals(selectedthemetype)){
			query="select content as html,cssurl as css from user_customized_themes " +
					"where module='eventspage' and userid=? and themeid=? ";
			params=new String[]{mgrId,selectedtheme};
			myeventpagethemename=DbUtil.getVal("select themename from user_customized_themes where " +
					"module='eventspage' and userid=? and themeid=?", new String[]{mgrId,selectedtheme});
			userThemes.setMyeventpagethemename(myeventpagethemename);
			
		}else{
			query="select defaultcontent as html,cssurl as css from ebee_roller_def_themes where " +
					"module='eventspage' and themecode='basic'";
			params=new String[]{};
			
		}
		statobj=dbmanager.executeSelectQuery(query,params);
		if(statobj.getStatus() && statobj.getCount()>0){
		HTML=dbmanager.getValue(0, "html", "");
		CSS=dbmanager.getValue(0, "css", "");
		attribMap.put("HTML",HTML);
		attribMap.put("CSS",CSS);
		}
		return "themeinput";
	}
	public String save(){	
		System.out.println(" in save "+"themetype::"+themetype+" theme::"+theme);
		UserCustomThemeDB.saveMyPublicPageTheme(themetype,theme,mgrId,userThemes,attribMap);
		msgKey="success";
		return "success";
	}
	public String saveTheme(){	
		System.out.println("in saveTheme "+"themetype::"+themetype+" theme::"+userThemes);
		UserCustomThemeDB.saveMyPublicPageThemeContent(theme,mgrId,userThemes,attribMap);
		msgKey="success";
		return "success";
	}
	public String customizeContent(){
		
		String[] values=new String[]{"Yes","No"};
		String[] captions=new String[]{"Show","Hide"};
		String def_val="Yes";
		StatusObj sobj=DbUtil.getKeyValues("select pref_name,pref_value from member_preference" +
				" where user_id=? ",new String[]{mgrId});
		HashMap prefMap=new HashMap();
		if(sobj.getCount()>0){
			prefMap=(HashMap) sobj.getData();
		}
		String upcomingEvents=WriteSelectHTML.getSelectOneRadio("userThemes.upcomingEvents",values,captions,GenUtil.getHMvalue(prefMap,"events.upcomingEvents",def_val));
		String pastEvents=WriteSelectHTML.getSelectOneRadio("userThemes.pastEvents",values,captions,GenUtil.getHMvalue(prefMap,"events.pastEvents",def_val));
		
		//String upcomingEvents=DbUtil.getVal("select events_display_type  from box_office_master where userid=?", new String[]{mgrId});
		//String pastEvents=DbUtil.getVal("", new String[]{});
		String photoDisplay=WriteSelectHTML.getSelectOneRadio("userThemes.photoDisplay",values,captions,GenUtil.getHMvalue(prefMap,"events.photoDisplay",def_val));
		String profileDisplay=WriteSelectHTML.getSelectOneRadio("userThemes.ProfileDisplay",values,captions,GenUtil.getHMvalue(prefMap,"events.ProfileDisplay",def_val));
		userThemes.setUpcomingEvents(upcomingEvents);
		userThemes.setPastEvents(pastEvents);
		userThemes.setPhotoDisplay(photoDisplay);
		userThemes.setProfileDisplay(profileDisplay);
		return "contentinput";
	}
	public String updatePreferences(){	
		System.out.println("\n selEvents size: "+selEvents.size());
		UserCustomThemeDB.updatePreferences(mgrId,userThemes,selEvents);
		msgKey="success";
		return "success";
	}
	public String manageEventsPage(){
		
		return "manage";
	}
	public String updateeventPublicPagePhotoURL(){	
		System.out.println("\n In updateeventPublicPagePhotoURL");
		UserCustomThemeDB.updatePublicPagePhotoURL(mgrId,eventphotoURL,uploadurl);
		userThemes.setPhoto(UserCustomThemeDB.getBoxOfficePhotoURL(mgrId));
		msgKey = "done";
		return "success";
	}
	
	public void deletePhoto(){
	    System.out.println("enter into deletePhoto:"+mgrId);
		UserCustomThemeDB.deltePublicPagePhotoURL(mgrId);
	}
	
	public String eventbeethemePreview(){
		System.out.println("we r in eventbee theme preview");
	    String css=PreviewDBHelper.getEventsPageCSSContentforTheme(theme, themetype, myeventpagethemename);
		 //String css=attribMap.get("CSS");
		String previewData=PreviewHelper.eventspageProcessPreviewData(css);
		Random rand = new Random();
	    int num = rand.nextInt(99999);
	    previewFileName=mgrId+"_"+num;
	   	FileWriterHelper.doWrite(previewFileName, previewData);
		return "preview";
	}
	
	public String customthemePreview(){
		System.out.println("we r in custom preview");
	    String css=attribMap.get("CSS");
		String previewData=PreviewHelper.eventspageProcessPreviewData(css);
		Random rand = new Random();
	    int num = rand.nextInt(99999);
	    previewFileName=mgrId+"_"+num;
	   	FileWriterHelper.doWrite(previewFileName, previewData);
		return "preview";
	}
	
	public String customizeSettings(){
		System.out.println("\n customizeSettings");
		String mgrcardchkstatus=AddEventDB.getMgrEventStaus(mgrId); 
		System.out.println("mgrcardchkstatus is:"+mgrcardchkstatus); 
		if("askcard".equalsIgnoreCase(mgrcardchkstatus)) 
			msgKey="askcard";
		else{
		BoxOfficeDB.saveBoxOfficeCustomization(boxoffice, mgrId);
		msgKey="don't askcard";
		}
		return "success";
    }
	
	public String fbSave(){
		System.out.println("the mgrId is::"+mgrId);
		boolean flag = validateFbSettings();
		if(flag){
			BoxOfficeDB.saveFBSettings(mgrId,boxoffice);
		}else{
			return "inputerror";
		}
		
	return "success";
	}
	
	public boolean validateFbSettings(){
		if("".equals(boxoffice.getFbtitle().trim()))
			addFieldError("boxoffice.fbtitle", "Title is empty");
		if("yes".equals(boxoffice.getFbpagination().trim())){
			if("".equals(boxoffice.getFbpaginationsize().trim())){
				addFieldError("", "Pagination per page is empty");
			}else{
				try{
					Integer.parseInt(boxoffice.getFbpaginationsize().trim());
				}catch(Exception e){
					addFieldError("", "Pagination per page should be numeric");
				}
			}
		}
		
		if("".equals(boxoffice.getFbbuybutton().trim()))
			addFieldError("boxoffice.fbtitle", "Buy Ticket Button is empty");
		
		if("".equals(boxoffice.getFbnoevtsmsg().trim()))
			addFieldError("boxoffice.fbtitle", "Message is empty");
		
		
		if(!getFieldErrors().isEmpty()){
			return false;
		}
	
	return true;
	}
}
