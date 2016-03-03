package com.event.dbhelpers;

import java.util.HashMap;
import com.event.beans.customization.ThemePage;
import com.event.themes.ThemeController;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class HTMLnCSSCustomizationDB {

	public static HashMap<String,String> getHTMLnCSS(String eid,String module,String mgrId){
		HashMap<String,String> tempMap=new HashMap<String,String>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String HTML="";
		String CSS="";
		String urt_select_Qry="select themetype,themecode from user_roller_themes where upper(module)='EVENT' and refid=?";
		statobj=dbmanager.executeSelectQuery(urt_select_Qry, new String[]{eid});
		String themeType=dbmanager.getValue(0, "themetype", "");
		String themeCode=dbmanager.getValue(0, "themecode", "");
		String query="";
		String[] params=null;
		if("CUSTOM".equalsIgnoreCase(themeType)){
			query="select content as html,cssurl as css from user_custom_roller_themes where refid=? ";
			params=new String[]{eid};
		}
		else if("DEFAULT".equalsIgnoreCase(themeType)){
			query="select defaultcontent as html,cssurl as css  from ebee_roller_def_themes where upper(module)='EVENT'" +
					"and themecode=? ";
			params=new String[]{themeCode};
		}else if("PERSONAL".equalsIgnoreCase(themeType)){
			query="select content as html,cssurl  as css from user_customized_themes where upper(module)='EVENT'" +
			"and themeid=? ";
			params=new String[]{themeCode};
		}
		else{
			query="select defaultcontent as html,cssurl as css  from ebee_roller_def_themes where upper(module)='EVENT'" +
				"and themecode=? ";
			params=new String[]{"basic"};
		}
		statobj=dbmanager.executeSelectQuery(query,params);
		if(statobj.getStatus() && statobj.getCount()>0){
		HTML=dbmanager.getValue(0, "html", "");
		CSS=dbmanager.getValue(0, "css", "");
		}
		tempMap.put("HTML",HTML);
		tempMap.put("CSS",CSS);
		return tempMap;
	}
	public static void saveHTMLnCSS(String eid,String module,HashMap<String,String> attribMap){
		//saving HTML and CSS data.
		String HTML=attribMap.get("HTML");
		String CSS=attribMap.get("CSS");
		String mgrId="";
		mgrId=DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		String themeCode="basic";//eid+".theme";
		String deleteQry="delete from user_roller_themes where refid=? and module='event'";
		DbUtil.executeUpdateQuery(deleteQry, new String[]{eid});
		String insertQuery="insert into user_roller_themes(userid,module,themecode,cssurl,refid,themetype) values" +
		"(to_number(?,'9999999999'),?,?,?,?,?)";
		DbUtil.executeUpdateQuery(insertQuery, new String[]{mgrId,"event",themeCode,"",eid,"CUSTOM"});
		String insertQry="insert into user_custom_roller_themes(userid,themecode,module,content,cssurl,refid)" +
		" values (to_number(?,'9999999999'),?,?,?,?,?)";
		String deleteQuery="delete from user_custom_roller_themes where refid=? and module='event'";
		DbUtil.executeUpdateQuery(deleteQuery, new String[]{eid});
		DbUtil.executeUpdateQuery(insertQry, new String[]{mgrId,themeCode,"event",HTML,CSS,eid});
	}
	public static void saveTheme(String themecode,String themetype,String mytheme,ThemePage themePage,String eid){
		System.out.println("saveTheme themecode: "+themecode+" themetype: "+themetype);
		String configVal="Default";
		if("PERSONAL".equals(themetype)){
			themecode=mytheme;
			configVal="My Themes";
		}else{
			configVal="Eventbee Theme ("+themecode+")";
		}

		String currenttheme=themePage.getSelectedTheme();	
		//if(!themecode.equals(currenttheme)){
			ThemeController.updateThemes(EventDB.getUserId(eid),"event%",themecode,eid,themetype);			
		//}
		
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid});
		DbUtil.executeUpdateQuery("delete from config where config_id=to_number(?,'999999999999999') and name='event.theme.type'", new String[]{configid});
		DbUtil.executeUpdateQuery("insert into config(name,value,config_id) values('event.theme.type',?,to_number(?,'999999999999999'))", new String[]{configVal,configid});

	}
	
}
