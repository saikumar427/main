package com.eventmanage.actions.customization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.event.beans.customization.ThemePage;
import com.event.dbhelpers.DisplayAttribsDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.HTMLnCSSCustomizationDB;
import com.event.helpers.EvtCustomizationHelper;
import com.event.themes.ThemeController;
import com.eventbee.beans.Entity;
import com.eventbee.general.GenUtil;
import com.eventbee.general.formatting.WriteSelectHTML;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class LooknFeelAction extends ActionSupport implements Preparable{
	
	private static final long serialVersionUID = 6948788993082267015L;
	
	private ArrayList<Entity> fontTypes = new ArrayList<Entity>();
	private ArrayList<Entity> fontSizes = new ArrayList<Entity>();
	private String eid = "";
	private HashMap<String,String> themeAttribs=new HashMap<String,String>();
	//private String previewFileName="";
	private String looknfeel="";
	private ThemePage themePage=new ThemePage();
	private HashMap<String,String> attribMap=new HashMap<String,String>();
	private String mgrId="";
	private String serverAddress="";
	private String purpose="";
	private String type="";
	
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
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public HashMap<String, String> getAttribMap() {
		return attribMap;
	}
	public void setAttribMap(HashMap<String, String> attribMap) {
		this.attribMap = attribMap;
	}
	public String getMgrId() {
		return mgrId;
	}
	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
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
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public HashMap<String, String> getThemeAttribs() {
		return themeAttribs;
	}
	public void setThemeAttribs(HashMap<String, String> themeAttribs) {
		this.themeAttribs = themeAttribs;
	}
	
	/*public String getPreviewFileName() {
		return previewFileName;
	}
	public void setPreviewFileName(String previewFileName) {
		this.previewFileName = previewFileName;
	}*/
	
	public void fillFontTypes() {
		fontTypes = EvtCustomizationHelper.fillFonts();
	}

	public void fillFontSizes() {
		fontSizes = EvtCustomizationHelper.fillFontSizes();
	}
	
	public String execute() {
		serverAddress=EventDB.serverAdderess();
		fillFontTypes();
		fillFontSizes();
		themeAttribs= DisplayAttribsDB.getAttribValues(eid, "ThemePage");
		// System.out.println("attribs Map"+themeAttribs);
		
		eventThemes();
		
		fillHTMLnCSS();
		
		looknfeel=EventDB.getConfigVal(eid,"event.theme.type","");
		if("Custom HTML & CSS".equals(looknfeel))
			looknfeel="HTMLnCSS";
		else if("Custom Look & Feel".equals(looknfeel))
			looknfeel="LooknFeel";
		else looknfeel="Themes";
		return "success";
	}
	
	public void eventThemes(){
		Map themesmap=ThemeController.getThemes(new String [] {"event"});
		String themevals[]=(String [])themesmap.get("themevals");
		String themenames[]=(String [])themesmap.get("themenames");
		Map mythemesmap=ThemeController.getMyThemes(new String [] {EventDB.getUserId(eid),"event"});
		String mythemevals[]=new String[0];
		String mythemenames[]=new String[0];
		if(mythemesmap!=null&&mythemesmap.size()>0){
			mythemevals=(String [])mythemesmap.get("themevals" );
			mythemenames=(String [])mythemesmap.get("themenames" );
		}

		String [] themedata=ThemeController.getThemeCodeAndType("event%",eid,"basic");
		String selectedtheme=themedata[1];
		String selectedthemetype=themedata[0];
		
		HashMap hm=new HashMap();
		String theme=WriteSelectHTML.getSelectHtml(themenames,themevals,"theme",GenUtil.getHMvalue(hm,"theme",selectedtheme),null,null,"onChange='changeloc()'" );
		themePage.setEventbeeThemes(theme);
		String userTheme=WriteSelectHTML.getSelectHtml(mythemenames,mythemevals,"mytheme",GenUtil.getHMvalue(hm,"theme",selectedtheme),null,null,"onChange='changeloc1()'");
		themePage.setUserThemes(userTheme);
		String currentTheme=EventDB.getConfigVal(eid,"event.theme.type","");
		if("".equals(currentTheme)) currentTheme="Eventbee Theme (ebee-3d-grey)";
		if("Custom Look & Feel".equals(currentTheme)) currentTheme="Custom Colors & Text";
		themePage.setCurrentTheme(currentTheme);
		themePage.setThemetype(selectedthemetype);
		themePage.setSelectedTheme(selectedtheme);
		themePage.setNamesArrayLength(mythemenames.length);
	}
	
	public void fillHTMLnCSS(){
		attribMap=HTMLnCSSCustomizationDB.getHTMLnCSS(eid,"event",mgrId);
	}
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String getFontStyles(){
		fillFontTypes();
		fillFontSizes();
	return "fontstyles";
	}
	
	
	public ThemePage getThemePage() {
		return themePage;
	}
	public void setThemePage(ThemePage themePage) {
		this.themePage = themePage;
	}
	public String getLooknfeel() {
		return looknfeel;
	}
	public void setLooknfeel(String looknfeel) {
		this.looknfeel = looknfeel;
	}
	

}
