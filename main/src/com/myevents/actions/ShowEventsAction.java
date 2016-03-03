package com.myevents.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.event.dbhelpers.EventsGroupDB;
import com.event.dbhelpers.MyEventsDB;
import com.event.helpers.DisplayAttribsHelper;
import com.event.helpers.EvtCustomizationHelper;
import com.event.themes.ThemeController;
import com.eventbee.beans.Entity;
import com.eventbee.general.StatusObj;
import com.eventbee.general.dbhelpers.PreviewDBHelper;
import com.eventbee.general.helpers.FileWriterHelper;
import com.eventbee.general.helpers.PreviewHelper;
import com.myevents.beans.UserThemes;
import com.myevents.dbhelpers.TrackingPartnerDB;
import com.myevents.dbhelpers.UserCustomThemeDB;
import com.myevents.helpers.EventsJsonBuilder;
import com.myevents.helpers.TrackingPartnerJsonBuilder;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import org.json.*;
public class ShowEventsAction extends MyEventsActionWrapper  implements Preparable,ValidationAware{

	private static final long serialVersionUID = 5619525978842135563L;
	private String themeid="";
	Map attribmap=null;
	private UserThemes userThemes=new UserThemes();
	private String jsonData="";	
	private String action="";
	private HashMap<String,String> themeAttribs=new HashMap<String,String>();
	private static String module="myeventstheme";
	private ArrayList<Entity> fontTypes = new ArrayList<Entity>();
	private ArrayList<Entity> fontSizes = new ArrayList<Entity>();
	private String previewFileName="";
	private String eventsPageURL="";
	private String previewcontent="";
	private String CSSContent="";
	private String HTMLContent="";
	private String eid = "";
	public String getCSSContent() {
		return CSSContent;
	}

	public void setCSSContent(String content) {
		CSSContent = content;
	}

	public String getHtmlcontent() {
		return HTMLContent;
	}

	public void setHtmlcontent(String htmlcontent) {
		HTMLContent = htmlcontent;
	}

	public String getPreviewcontent() {
		return previewcontent;
	}

	public void setPreviewcontent(String previewcontent) {
		this.previewcontent = previewcontent;
	}

	public String getEventsPageURL() {
		return eventsPageURL;
	}

	public void setEventsPageURL(String eventsPageURL) {
		this.eventsPageURL = eventsPageURL;
	}

	public String getPreviewFileName() {
		return previewFileName;
	}

	public void setPreviewFileName(String previewFileName) {
		this.previewFileName = previewFileName;
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
	public HashMap<String, String> getThemeAttribs() {
		return themeAttribs;
	}
	public void setThemeAttribs(HashMap<String, String> themeAttribs) {
		this.themeAttribs = themeAttribs;
	}
	public static String getModule() {
		return module;
	}
	public static void setModule(String module) {
		ShowEventsAction.module = module;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getThemeid() {
		return themeid;
	}
	public void setThemeid(String themeid) {
		this.themeid = themeid;
	}
	
	public Map getAttribmap() {
		return attribmap;
	}
	public void setAttribmap(Map attribmap) {
		this.attribmap = attribmap;
	}	
	
	public UserThemes getUserThemes() {
		return userThemes;
	}
	public void setUserThemes(UserThemes userThemes) {
		this.userThemes = userThemes;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String execute()
	{		
		return "success";		
	}
	public String populateTrackingPartnersList(){
		ArrayList trackingPartnersList=TrackingPartnerDB.getTrackingPartnersList(mgrId);
		JSONObject js = TrackingPartnerJsonBuilder.getTrackingPartnerJson(trackingPartnersList,mgrId);
		jsonData=js.toString();
		return "trackingpartnercreated";
		
	}
	public String populateEventGroupsList()
	{
		List<HashMap<String,String>> groupEvents=EventsGroupDB.getEventGroups(mgrId);
		JSONObject js= EventsJsonBuilder.getEventGroupsJson(groupEvents);
		jsonData=js.toString();
		return "eventgroupsJson";		
	}
	
	public String rebuildEventsGroupTable()
	{
		List<HashMap<String,String>> groupEvents=EventsGroupDB.getEventGroups(mgrId);
		JSONObject js= EventsJsonBuilder.getEventGroupsJson(groupEvents);
		jsonData=js.toString();
		return "rebuildEventsgroupJson";		
	}
	
	
	public String populateEventThemesList()
	{		
		List<HashMap<String,String>> eventThemes=EventsGroupDB.getEventThemes(mgrId);		
		JSONObject js= EventsJsonBuilder.getEventThemesJson(eventThemes);
		jsonData=js.toString();	
		return "eventthemesJson";		
	}
	public String populateEventsPage(){
		eventsPageURL=MyEventsDB.getEventsPageURL(mgrId);
		JSONObject js= EventsJsonBuilder.getEventsPageURL(eventsPageURL);
		jsonData=js.toString();	
		return "eventsPageJson";		
	}
	public String populateEventsList()
	{
		ArrayList activeEventsList=MyEventsDB.populateUpComingEvents(mgrId,0);
		ArrayList closedEventsList=MyEventsDB.populateRecentClosedEvents(mgrId,0);
		ArrayList suspendedEventsList=MyEventsDB.populateSuspendedEvents(mgrId,0);
		JSONObject js= EventsJsonBuilder.getEventsJson(activeEventsList, closedEventsList, suspendedEventsList);
		jsonData=js.toString();
		return "eventsJson";		
	}
	public void prepare() throws Exception {
		try {
			super.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}	
	public void fillFontTypes() {
		fontTypes = EvtCustomizationHelper.fillFonts();
	}

	public void fillFontSizes() {
		fontSizes = EvtCustomizationHelper.fillFontSizes();
	}
	public String addUserTheme(){		
		fillFontTypes();
		fillFontSizes();
		themeAttribs= UserCustomThemeDB.getDefaultAttribs(module);
		actionTitle="Create New Theme";
		return "usertheme";
	}
	public String editUserTheme(){		
		fillFontTypes();
		fillFontSizes();
		userThemes.setAction(action);
		themeAttribs= UserCustomThemeDB.getAttribValues(themeid,module);
		String themeName=themeAttribs.get("THEME_NAME");
		if(themeName==null) actionTitle="Edit Theme Look & Feel";
		else actionTitle=themeName+" > Edit Theme Look & Feel";
		return "usertheme";
	}
	
	public String saveTheme(){
		UserCustomThemeDB.insertThemeAttributes(module,userThemes,mgrId,themeid,themeAttribs);
		return "themecreated";
	}
	
	public String themeTemplates(){
		String CSScontent=ThemeController.getCSS(themeid,"PERSONAL","event");
		String HTMLContent=ThemeController.getContent(mgrId, "event", themeid, "event", "", "PERSONAL");					
		CSScontent=CSScontent.replace("#**BODY_FONT_TYPE**#","Verdana, Arial, Helvetica, sans-serif");
		CSScontent=CSScontent.replace("#**BODY_FONT_SIZE**#","12px");
		CSScontent=CSScontent.replace("#**BODY_TEXT_COLOR**#","#000000");
		CSScontent=CSScontent.replace("#**LINK_TEXT_COLOR**#","#0C50A1");
		CSScontent=CSScontent.replace("#**LINK_FONT_SIZE**#","12px");
		CSScontent=CSScontent.replace("#**LINK_FONT_TYPE**#","Lucida Grande,Lucida Sans Unicode,sans-serif");
		HashMap<String,String> hmp=new HashMap<String,String>();
		hmp=UserCustomThemeDB.getHeadernFooter(themeid);		
		String headerHTML=hmp.get("HEADER_HTML");
		String footerHTML=hmp.get("FOOTER_HTML");
				
		if(!"".equals(headerHTML)&& headerHTML!=null){			
			HTMLContent=HTMLContent.replace("$eventbeeHeader", headerHTML);		
	}	
			
	   if(!"".equals(footerHTML) &&footerHTML!=null)
			HTMLContent=HTMLContent.replace("$eventbeeFooter", footerHTML);
	   
		themeAttribs= UserCustomThemeDB.getAttribValues(themeid,module);
		String themeName=themeAttribs.get("THEME_NAME");
		if(themeName==null) actionTitle="Edit Theme HTML & CSS";
		else actionTitle=themeName+" > Edit Theme HTML & CSS";
		userThemes.setCSScontent(CSScontent);
		
		userThemes.setHTMLContent(HTMLContent);
		return "htmlncss";
	}
	public String save(){		
		StatusObj sobj=ThemeController.updateMyTheme(new String []{userThemes.getCSScontent(),userThemes.getHTMLContent()},themeid,"event");
		return "themecreated";
	}
	public String preview(){
		String CSS=userThemes.getCSScontent();
		HTMLContent=userThemes.getHTMLContent();
		
		
		String previewData=PreviewHelper.processPreviewData(CSS);		
		Random rand = new Random();
	    int num = rand.nextInt(99999);
	    previewFileName=mgrId+"_"+num;
	    FileWriterHelper.doWrite(previewFileName, previewData);
	   	
		return "preview";
	}
	public String previewLnF(){
		DisplayAttribsHelper.processLookandFeelData(themeAttribs);
		HashMap<String,String> lnfcssMap=DisplayAttribsHelper.processLandFDataForCSS(themeAttribs);
		String CSSContent=EvtCustomizationHelper.generateCSSString(lnfcssMap,"event");		
		String previewData=PreviewHelper.processPreviewData(CSSContent);
		Random rand = new Random();
	    int num = rand.nextInt(99999);
	    previewFileName=mgrId+"_"+num;
	   	FileWriterHelper.doWrite(previewFileName, previewData);
		return "preview";
	}

	public String getHTMLContent() {
		return HTMLContent;
	}

	public void setHTMLContent(String content) {
		HTMLContent = content;
	}
	
}
