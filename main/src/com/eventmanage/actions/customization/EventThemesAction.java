package com.eventmanage.actions.customization;

import java.util.HashMap;

import com.event.beans.customization.ThemePage;
import com.event.dbhelpers.HTMLnCSSCustomizationDB;
import com.eventbee.general.dbhelpers.PreviewDBHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class EventThemesAction extends ActionSupport implements Preparable{

	private static final long serialVersionUID = 5340070412882968627L;
	private String eid = "";
	private String msgType = "eventThemes";
	private ThemePage themePage=new ThemePage();
	private String theme="";
	private String themetype="";
	private String mytheme="";
	//private String previewFileName="";
	
	/*public String getPreviewFileName() {
		return previewFileName;
	}

	public void setPreviewFileName(String previewFileName) {
		this.previewFileName = previewFileName;
	}*/
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

	public ThemePage getThemePage() {
		return themePage;
	}

	public void setThemePage(ThemePage themePage) {
		this.themePage = themePage;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	@SuppressWarnings("unchecked")
	/*public String execute(){
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
		if("".equals(currentTheme)) currentTheme="Default";
		themePage.setCurrentTheme(currentTheme);
		themePage.setThemetype(selectedthemetype);
		themePage.setSelectedTheme(selectedtheme);
		themePage.setNamesArrayLength(mythemenames.length);
		return "success";
	}*/
	public String save(){
		HTMLnCSSCustomizationDB.saveTheme(theme,themetype,mytheme,themePage,eid);
		return "ajaxstatusmsg";
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String preview(){
		try{
		HashMap<String, String> previewMap=PreviewDBHelper.getPreviewCssAndHtml(eid,theme, themetype, mytheme);
		//String htmlContent=PreviewDBHelper.getHTMLContentforTheme(theme, themetype, mytheme);
		PreviewDBHelper.updatePreviewTheme(eid, previewMap.get("CSS"), previewMap.get("HTML"), theme, mytheme);
		//System.out.println("css content: "+cssContent);
		/*Random rand = new Random();
	    int num = rand.nextInt(99999);
	    previewFileName=eid+"_"+num;
	    String previewData=PreviewHelper.processPreviewData(cssContent);
	    FileWriterHelper.doWrite(previewFileName, previewData);
		*/
		}catch(Exception e){
			System.out.println("Exception"+e.getLocalizedMessage());
		}
		return "preview";
	}

}
