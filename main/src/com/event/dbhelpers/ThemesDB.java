package com.event.dbhelpers;

import java.util.HashMap;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;

import com.eventbee.general.StatusObj;

public class ThemesDB {
	public static HashMap<String,String> getThemeContent(String gid,String mgrId){
		HashMap <String,String> themeContent=new HashMap <String,String>();
		
		String getDefaultsQuery="select defaultcontent,cssurl from ebee_roller_def_themes" +
 		" where module=? and themecode=?";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(getDefaultsQuery,new String []{"groupevent","basic"});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
		   String defaultcontent=dbmanager.getValue(0,"defaultcontent","");
		   String cssurl=dbmanager.getValue(0,"cssurl","");
		   themeContent.put("HTML", defaultcontent);
		   themeContent.put("CSS", cssurl);
		}
		String getCustomizedQuery="select content,cssurl from user_custom_roller_themes where refid=? and module=? ";
		statobj=dbmanager.executeSelectQuery(getCustomizedQuery,new String []{gid,"groupevent"});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
			 String content=dbmanager.getValue(0,"content","");
			 String cssurl=dbmanager.getValue(0,"cssurl","");
			 themeContent.put("HTML", content);
			 themeContent.put("CSS", cssurl);
		}
		return themeContent;
	}
	public static void saveGroupTheme(HashMap<String,String> themeContent,String gid,String mgrId){
		String HTML =themeContent.get("HTML");
		String CSS= themeContent.get("CSS");
		String delete_User_Roller_Themes="delete from user_roller_themes where refid =? and module=?";
		String delete_Custom_Roller_Themes="delete from user_custom_roller_themes where refid =? and module=?";
		DbUtil.executeUpdateQuery(delete_User_Roller_Themes,new String[]{gid,"groupevent"});
		DbUtil.executeUpdateQuery(delete_Custom_Roller_Themes,new String[]{gid,"groupevent"});
		String insert_User_Roller_Themes="insert into user_roller_themes(userid,module,themecode,cssurl," +
				"refid,themetype) values(to_number(?,'999999999999'),?,?,?,?,?)";
		String insert_Custom_Roller_Themes="insert into user_custom_roller_themes(userid,refid,cssurl,content," +
				"module,themecode) values(to_number(?,'999999999999'),?,?,?,?,?)";
		DbUtil.executeUpdateQuery(insert_User_Roller_Themes,new String[]{mgrId,"groupevent","basic","",gid,"CUSTOM"});
		DbUtil.executeUpdateQuery(insert_Custom_Roller_Themes,new String[]{mgrId,gid,CSS,HTML,"groupevent","basic"});
	 	
			 
	}
	
}
