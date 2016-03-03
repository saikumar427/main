package com.eventbee.general.dbhelpers;

import java.util.HashMap;

import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.HTMLnCSSCustomizationDB;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class PreviewDBHelper {

	public static String getDefaultHTML(String purpose){
		//Get the default html for preview and return that content.
		String temp=purpose.toUpperCase();
		String query="select htmlcontent from previewhtmlcontent where upper(purpose)=?";
		String htmlContent=DbUtil.getVal(query,new String[]{temp});
		return htmlContent;
	}
	public static String getCSSContentforTheme(String themeCode,String themetype,String mytheme){
		if("PERSONAL".equalsIgnoreCase(themetype)){
			themeCode=mytheme;
			String query="select cssurl from user_customized_themes where themeid=? and module='event'";
			String cssContent=DbUtil.getVal(query,new String[]{mytheme});
			return cssContent;
		}
		else{
			String query="select cssurl from ebee_roller_def_themes where themecode=? and module='event'";
			String cssContent=DbUtil.getVal(query,new String[]{themeCode});
			return cssContent;
		}
	}
	public static HashMap<String, String> getPreviewCssAndHtml(String eid,String themeCode,String themetype,String mytheme){
		System.out.println("getPreviewCssAndHtml themeCode: "+themeCode+" themetype: "+themetype);
		String query="select cssurl,defaultcontent as content from ebee_roller_def_themes where themecode=? and module='event'";
		HashMap<String, String> previewMap=new HashMap<String, String>();
		if("PERSONAL".equalsIgnoreCase(themetype)){
			themeCode=mytheme;
			query="select cssurl,content from user_customized_themes where themeid=? and module='event'";
		}else if("".equals(themeCode) && "".equals(themetype)){
			previewMap=HTMLnCSSCustomizationDB.getHTMLnCSS(eid, "event", EventDB.getUserId(eid));
			return previewMap;
		}
		DBManager db=new DBManager();
		StatusObj sob=db.executeSelectQuery(query, new String[]{themeCode});
		if(sob.getStatus()){
			previewMap.put("CSS", db.getValue(0, "cssurl", ""));
			previewMap.put("HTML", db.getValue(0, "content", ""));
		}
		return previewMap;
	}
	public static void updatePreviewTheme(String eid,String css,String html,String themename,String themecode){
		DbUtil.executeUpdateQuery("delete from theme_preview where ref_id=?", new String[]{eid});
		DbUtil.executeUpdateQuery("insert into theme_preview(ref_id,user_id,themecode," +
				"themename,content,cssurl) values(?,?,?,?,?,?)", new String[]{eid,EventDB.getUserId(eid),themecode,themename,html,css});
	}
	
	public static String getEventsPageCSSContentforTheme(String themeCode,String themetype,String mytheme){
		System.out.println("getEventsPageCSSContentforTheme themeCode: "+themeCode+" themetype: "+themetype);
		
		if("PERSONAL".equalsIgnoreCase(themetype)){
			themeCode=mytheme;
			String query="select cssurl from user_customized_themes where themeid=? and module='eventspage'";
			String cssContent=DbUtil.getVal(query,new String[]{themeCode});
			
			return cssContent;
		}
		else{
			String query="select cssurl from ebee_roller_def_themes where themecode=? and module='eventspage'";
			String cssContent=DbUtil.getVal(query,new String[]{themeCode});
			//System.out.println("csscontent"+cssContent);
			return cssContent;
		}
	}
	
}
