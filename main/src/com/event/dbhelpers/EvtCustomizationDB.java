package com.event.dbhelpers;


import java.util.HashMap;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;

import com.eventbee.general.StatusObj;

public class EvtCustomizationDB{
	
	
	
	public static void insertThemeData(String eid, String module,HashMap<String,String> themeAttribs,String CSSContent){
		String insertQuery="insert into user_roller_themes(userid,module,themecode,cssurl,refid,themetype) values" +
				"(to_number(?,'9999999999'),?,?,?,?,?)";
		String selectQuery="select themecode,themetype from user_roller_themes where refid=? and module='event'";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String HTMLContent="";
		String mgrId="";
		mgrId=DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		String themeCode="";
		statobj=dbmanager.executeSelectQuery(selectQuery,new String []{eid});
		if(statobj.getStatus() && statobj.getCount()>0){
			String query="";
			String[] params=null;
			 String themetype=dbmanager.getValue(0,"themetype","");
			 themeCode=dbmanager.getValue(0,"themecode","");
			 if("DEFAULT".equalsIgnoreCase(themetype)){
				 query="select defaultcontent as html from ebee_roller_def_themes where module=? and themecode=?" ;
				 params=new String[]{"event",themeCode};
			 }
			 else if ("PERSONAL".equalsIgnoreCase(themetype)){
				query="select content as html from user_customized_themes where upper(module)='EVENT'" +
					"and themeid=? ";
				 params=new String[]{themeCode};
				 
			 }
			 else if ("CUSTOM".equalsIgnoreCase(themetype)){
				 query="select content as html from user_custom_roller_themes where refid=? and module=? ";					
				 params=new String[]{eid,"event"};
					
			 }
			 else{
					query="select defaultcontent as html  from ebee_roller_def_themes where upper(module)='EVENT'" +
						"and themecode=? ";
					params=new String[]{"basic"};
				}
			 HTMLContent=DbUtil.getVal(query, params);
			 String updateQuery="update user_roller_themes set themetype='CUSTOM' where refid=? and module=?";
			 DbUtil.executeUpdateQuery(updateQuery, new String[]{eid,module});
		
		}
		else{
			themeCode="basic";//eid+".theme";
			DbUtil.executeUpdateQuery(insertQuery, new String[]{mgrId,module,themeCode,"",eid,"CUSTOM"});
			String getDefaultHTMLQuery="select defaultcontent from ebee_roller_def_themes" +
	 		" where module=? and themecode=?";
			//HTMLContent=DbUtil.getVal(getDefaultHTMLQuery, new String[]{module,"fordefaultcss"});
			HTMLContent=DbUtil.getVal(getDefaultHTMLQuery, new String[]{module,"basic"});
		}
		String deleteQuery="delete from user_custom_roller_themes where refid=? and module=?";
		DbUtil.executeUpdateQuery(deleteQuery, new String[]{eid,module});
		String insertQry="insert into user_custom_roller_themes(userid,themecode,module,content,cssurl,refid)" +
				" values (to_number(?,'9999999999'),?,?,?,?,?)";
		DbUtil.executeUpdateQuery(insertQry, new String[]{mgrId,themeCode,module,HTMLContent,CSSContent,eid});
	}
	public static String getDefaultCSSContent(String module,String themeCode){
		String CSSContent="";
		String selectCSSQuery="select cssurl from ebee_roller_def_themes where module=? and themecode=?";
		CSSContent=DbUtil.getVal(selectCSSQuery, new String[]{module,themeCode});
		return CSSContent;
		
	}
	public static void insertHandFData(String eid,String module,HashMap<String,String>HandFooterMap){
		String idType="eventdetails";
		String deleteQuery="delete from configure_looknfeel where refid=? and idtype=?";
		DbUtil.executeUpdateQuery(deleteQuery, new String[]{eid,idType});
		String insertQuery="insert into configure_looknfeel(idtype,pagetitle,headerhtml,postloginheaderhtml," +
				"navhtml,footerhtml,refid) values(?,?,?,?,?,?,?)";
		String headerHTML=HandFooterMap.get("HEADER_HTML");
		String footerHTML=HandFooterMap.get("FOOTER_HTML");
		DbUtil.executeUpdateQuery(insertQuery, new String[]{idType,"",headerHTML,"","",footerHTML,eid});

	}
	public static void updateConfigVal(String eid,String configVal){
		
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid});
		DbUtil.executeUpdateQuery("delete from config where config_id=to_number(?,'999999999999999') and name='event.theme.type'", new String[]{configid});
		DbUtil.executeUpdateQuery("insert into config(name,value,config_id) values('event.theme.type',?,to_number(?,'999999999999999'))", new String[]{configVal,configid});

	}
}
