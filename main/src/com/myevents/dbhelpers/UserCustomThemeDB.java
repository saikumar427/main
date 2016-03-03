package com.myevents.dbhelpers;

import com.event.dbhelpers.EvtCustomizationDB;
import com.event.helpers.DisplayAttribsHelper;
import com.event.helpers.EvtCustomizationHelper;
import com.event.themes.ThemeController;
import com.event.themes.ThemeQueries;
import com.eventbee.beans.Entity;
import com.eventbee.general.EventbeeLogger;
import com.eventbee.general.DBManager;
import com.eventbee.general.StatusObj;
import com.eventbee.general.DbUtil;
import com.eventbee.general.DBQueryObj;
import com.myevents.beans.UserThemes;
import javax.servlet.http.*;
import java.util.*;


public class UserCustomThemeDB{


public static final String [] attributes={"userThemes.themeName","userThemes.BG_TYPE","userThemes.BG_COLOR","userThemes.BG_IMAGE","userThemes.LARGETEXTCOLOR","BIGGER_FONT_TYPE","BIGGER_FONT_SIZE","userThemes.MEDIUMTEXTCOLOR","MEDIUM_FONT_TYPE","MEDIUM_FONT_SIZE","userThemes.SMALLTEXTCOLOR","SMALL_FONT_TYPE","SMALL_FONT_SIZE"};

public static final String INSERT_DETAILS="insert into streaming_details(streamid,userid,purpose,refid) values(to_number(?,'9999999999999999'),to_number(?,'9999999999999999'),?,?)";

public static final String INSERT_ATTRIBUTES="insert into streaming_attributes(streamid,stream_attribute,attrib_value) values(to_number(?,'9999999999999999'),?,?)";

public static final String DELETE_ATTRIBUTES="delete from streaming_attributes where streamid=to_number(?,'9999999999999999')";

public static final String GET_ATTRIBUTES="select stream_attribute, attrib_value,streamid from streaming_attributes where streamid=(select streamid from streaming_details where userid=to_number(?,'9999999999999999') and purpose=? and refid=?)";

public static final String DELETE_EVENTS="delete from box_office_events where boxoffice_id=?";

public static final String INSERT_EVENTS="insert into box_office_events(boxoffice_id,eventid,position) values(?,?,to_number(?,'999999999999'))";

public static final String HEADER_FOOTER_VALUE="select attrib_name,attrib_value from usertheme_display_attribs where module='myeventstheme' and attrib_name in('FOOTER_HTML','HEADER_HTML') and themeid=CAST(? AS INTEGER)";
	



public static Map getStremingAttributes(String userid,String purpose,String refid){	
	Map hm=new HashMap();
	try{
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"UserCustomThemeDB.java","getStremingAttributes()","",null);
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(GET_ATTRIBUTES,new String []{userid,purpose,refid});
		int recordcount=statobj.getCount();
		if(recordcount>0){
			for(int i=0;i<recordcount;i++){
				hm.put(dbmanager.getValue(i,"stream_attribute",""),dbmanager.getValue(i,"attrib_value",""));
			}
			hm.put("streamid",dbmanager.getValue(0,"streamid",""));
		}
	}
	catch(Exception e){
		EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "UserCustomThemeDB.java", "getStremingAttributes()", e.getMessage(), e ) ;
	}
	return hm;

}




public static void insertStreamingAttributes(HttpServletRequest req,String streamid){
	try{
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"UserCustomThemeDB.java","insertStreamingAttributes()","",null);

		StatusObj statobj=null;
		List dbquery=new ArrayList();
		for(int i=0;i<attributes.length;i++){
		
			dbquery.add(new DBQueryObj(INSERT_ATTRIBUTES,new String [] {streamid,attributes[i],req.getParameter(attributes[i])}));
		}
		if(dbquery!=null&&dbquery.size()>0)
			statobj=DbUtil.executeUpdateQueries((DBQueryObj [])dbquery.toArray(new DBQueryObj [dbquery.size()]));
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"UserCustomThemeDB.java","insertStreamingAttributes()","Status of insert into streaming_attributes is:"+statobj.getStatus(),null);

	}
	catch(Exception e){
			EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "UserCustomThemeDB.java", "insertStreamingAttributes()", e.getMessage(), e ) ;
	}
}




public static String insertStreamingDetails(String userid,String purpose,String refid){
	String streamid="";
	try{
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"UserCustomThemeDB.java","insertStreamingDetails()","",null);
		streamid=DbUtil.getVal("select nextval('streaming_id')",null);
		
		StatusObj statobj=DbUtil.executeUpdateQuery(INSERT_DETAILS,new String[]{streamid,userid,purpose,refid});
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"UserCustomThemeDB.java","insertStreamingDetails()","Status of insert into streaming_details is:"+statobj.getStatus(),null);
	}
	catch(Exception e){
		EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "UserCustomThemeDB.java", "insertStreamingDetails()", e.getMessage(), e ) ;
	}
	return streamid;

}

public static void updateStreamingAttributes(HttpServletRequest req,String streamid){
	try{
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"UserCustomThemeDB.java","updateStreamingAttributes()","",null);
		StatusObj statobj=null;
		List dbquery=new ArrayList();
		dbquery.add(new DBQueryObj(DELETE_ATTRIBUTES,new String [] {streamid}));
	
		for(int i=0;i<attributes.length;i++){			
			dbquery.add(new DBQueryObj(INSERT_ATTRIBUTES,new String [] {streamid,attributes[i],req.getParameter(attributes[i])}));
		}
		if(dbquery!=null&&dbquery.size()>0)
			statobj=DbUtil.executeUpdateQueries((DBQueryObj [])dbquery.toArray(new DBQueryObj [dbquery.size()]));
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"UserCustomThemeDB.java","updateStreamingAttributes()","Status of update streaming_attributes is:"+statobj.getStatus(),null);

	}

	catch(Exception e){
			EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "UserCustomThemeDB.java", "updateStreamingAttributes()", e.getMessage(), e ) ;
	}

	}
public static String getDefaultHTMLContent(String module,String themeCode){
	String Content="";
	String selectQuery="select defaultcontent from ebee_roller_def_themes where module=? and themecode=?";
	Content=DbUtil.getVal(selectQuery, new String[]{module,themeCode});
	return Content;
	
}
/*public static void insertThemeAttributes(UserThemes userThemes,String mgrId,String themeid){	
	HttpServletRequest request = ServletActionContext.getRequest();
	String streamid=null;
	if("edit".equals(userThemes.getAction())){		
		streamid=DbUtil.getVal("select streamid from streaming_details where refid=?",new String[]{themeid});		
		UserCustomThemeDB.updateStreamingAttributes(request,streamid);
	}else{
		themeid=ThemeController.getThemeid();		
		streamid=UserCustomThemeDB.insertStreamingDetails(mgrId,"event",themeid);
		UserCustomThemeDB.insertStreamingAttributes(request,streamid);
	}
	HashMap<String, String> hm=new HashMap<String, String>();
	String themename=userThemes.getThemeName();
	if(themename==null)themename="";
	String background=userThemes.getBG_COLOR();
	if("BG_COLOR".equals(background))
		hm.put("#**BACKGROUND**#",userThemes.getBG_COLOR());
	else{
		String bgimg=userThemes.getBG_IMAGE();
		hm.put("#**BACKGROUND**#","url("+bgimg+")");
		}	
	hm.put("#**CONTAINERBACKGROUND**#","#FFFFFF");	
	hm.put("#**BOXBACKGROUND**#","#FFFFFF");
	hm.put("#**BOXHEADERBACKGROUND**#","#FFFFFF");
	hm.put("#**BIGGER_FONT_SIZE**#",userThemes.getLARGETEXTFONTSIZE());
	hm.put("#**BIGGER_FONT_TYPE**#",userThemes.getLARGETEXTFONTTYPE());
	hm.put("#**BIGGER_TEXT_COLOR**#",userThemes.getLARGETEXTCOLOR());
	
	hm.put("#**MEDIUM_FONT_SIZE**#",userThemes.getMEDIUMTEXTFONTSIZE());
	hm.put("#**MEDIUM_FONT_TYPE**#",userThemes.getMEDIUMTEXTFONTYPE());
	hm.put("#**MEDIUM_TEXT_COLOR**#",userThemes.getMEDIUMTEXTCOLOR());

	hm.put("#**SMALL_FONT_SIZE**#",userThemes.getSMALLTEXTFONTSIZE());
	hm.put("#**SMALL_FONT_TYPE**#",userThemes.getSMALLTEXTFONTTYPE());
	hm.put("#**SMALL_TEXT_COLOR**#",userThemes.getSMALLTEXTCOLOR());
	String CSSContent=EvtCustomizationHelper.generateCSSString(hm,"event");
	String htmlcontent=getDefaultHTMLContent("eventtemplate","fordefaultcss");
	if("edit".equals(userThemes.getAction())){
		StatusObj sobj=ThemeController.updateMyThemeWithName(CSSContent,themeid,"event",themename.trim());
		ThemeController.updateMyTheme(new String []{CSSContent,htmlcontent},themeid,"event");
	}else{
		StatusObj sobj=ThemeController.createMyTheme(new String []{CSSContent,htmlcontent}, mgrId,"event",themename.trim(),themeid);

		}
}*/

public static HashMap<String,String> getAttribValues(String themeid,String module){
	HashMap<String,String> defaultValues=new HashMap<String,String>();
	defaultValues=getDefaultAttribs(module);
	DBManager dbmanager=new DBManager();
	StatusObj statobj=null;
	String attribValsQuery="select attrib_name,attrib_value from usertheme_display_attribs " +
			"where themeid=to_number(?,'9999999999') and module=?";
	statobj=dbmanager.executeSelectQuery(attribValsQuery,new String []{themeid,module});
	int count=statobj.getCount();
	if(statobj.getStatus() && count>0){
		for(int k=0;k<count;k++){
			 String attribname=dbmanager.getValue(k,"attrib_name","");
			 String attribval=dbmanager.getValue(k,"attrib_value","");
			 defaultValues.put(attribname, attribval);
		}
		}
	
	return defaultValues;
	
}
public static HashMap<String,String> getDefaultAttribs(String module){
	HashMap<String,String> defaultAttribMap=new HashMap<String,String>();
	String defaultValsQuery="select attribname,attribdefaultvalue from user_display_defaultattribs where module=?";
	DBManager dbmanager=new DBManager();
	StatusObj statobj=null;
	statobj=dbmanager.executeSelectQuery(defaultValsQuery,new String []{module});
	int count=statobj.getCount();
	if(statobj.getStatus() && count>0){
		for(int k=0;k<count;k++){
		    String attribname=dbmanager.getValue(k,"attribname","");
		    String attribval=dbmanager.getValue(k,"attribdefaultvalue","");
		    defaultAttribMap.put(attribname, attribval);
		   // System.out.println( defaultAttribMap.put(attribname, attribval));
		}
	}
	
	return defaultAttribMap;
}
public static void insertDisplayAttribs(String themeid,String module,HashMap<String, String>attribMap ,boolean forceDelete){
	Set mapKeys = attribMap.keySet();
    Iterator It = mapKeys.iterator();
    String inserAttribQuery="insert into usertheme_display_attribs(themeid ,module,attrib_name,attrib_value) " +
    		"values(to_number(?,'99999999999999'),?,?,?)";
    String deleteQuery="delete from usertheme_display_attribs where themeid=to_number(?,'999999999999') and module=?";
    if(forceDelete){
    	DbUtil.executeUpdateQuery(deleteQuery, new String []{themeid,module});
    }
    while (It.hasNext()) {
            String key = (String)(It.next());
            String value=attribMap.get(key);
            DbUtil.executeUpdateQuery(inserAttribQuery, new String []{themeid,module,key,value});
            }
}
public static void insertThemeAttributes(String module,UserThemes userThemes,String mgrId,String themeid,HashMap<String,String> themeAttribs){	
	String themename=themeAttribs.get("THEME_NAME");
	if(themename==null)themename="";
	if("edit".equals(userThemes.getAction())){		
	}else{
		themeid=ThemeController.getThemeid();		
	}		

DisplayAttribsHelper.processLookandFeelData(themeAttribs);
UserCustomThemeDB.insertDisplayAttribs(themeid,module, themeAttribs, true);
HashMap<String,String> lnfcssMap=DisplayAttribsHelper.processLandFDataForCSS(themeAttribs);
String CSSContent=EvtCustomizationHelper.generateCSSString(lnfcssMap,"event");
String htmlcontent=getDefaultHTMLContent("eventtemplate","fordefaultcss");
String headercontent=themeAttribs.get("HEADER_HTML");
String footercontent=themeAttribs.get("FOOTER_HTML");
if(!"".equals(headercontent)&& headercontent!=null)		
	htmlcontent=htmlcontent.replace("$eventbeeHeader", headercontent);		
	
if(!"".equals(footercontent) && footercontent!=null)
	htmlcontent=htmlcontent.replace("$eventbeeFooter", footercontent);

if("edit".equals(userThemes.getAction())){
	StatusObj sobj=ThemeController.updateMyThemeWithName(CSSContent,themeid,"event",themename.trim());
	ThemeController.updateMyTheme(new String []{CSSContent,htmlcontent},themeid,"event");
}else{
	StatusObj sobj=ThemeController.createMyTheme(new String []{CSSContent,htmlcontent}, mgrId,"event",themename.trim(),themeid);
}
HashMap<String,String> HandFooterMap=DisplayAttribsHelper.processHandFooterData(themeAttribs);
EvtCustomizationDB.insertHandFData(mgrId,module,HandFooterMap);
}
static String GET_SELECTEDTHEME_INFO="select themetype,themecode,cssurl from user_roller_themes where module=? and userid=to_number(?,'99999999999999999') ";

public static String [] getMyPublicPageThemeCodeAndType(String module,String userid,String deftheme){
	DBManager dbmanager=new DBManager();
	StatusObj statobj=dbmanager.executeSelectQuery(GET_SELECTEDTHEME_INFO,new String []{module,userid});
	if(statobj.getStatus()&&statobj.getCount()>0)
		return new String [] {dbmanager.getValue(0,"themetype","DEFAULT"),dbmanager.getValue(0,"themecode",deftheme),dbmanager.getValue(0,"cssurl",deftheme+".css")};
	else
		return new String [] {"DEFAULT",deftheme,deftheme+".css"};
}

public static void saveMyPublicPageTheme(String themetype,String themecode,String mgrId,UserThemes userThemes,HashMap<String,String> attribMap){
	if("PERSONAL".equals(themetype)){		
		String isexist=DbUtil.getVal("select 'Yes' from user_customized_themes where themeid=? and userid=?",new String[]{userThemes.getSelectedTheme(),mgrId});
		if(isexist==null){
			String themeid=DbUtil.getVal(ThemeQueries.GET_THEME_ID,null);
			themecode=themeid;
			DbUtil.executeUpdateQuery(ThemeQueries.INSERT_MYTHEME_DATA,new String [] {mgrId,attribMap.get("CSS"),attribMap.get("HTML"),"eventspage",themeid,userThemes.getMyeventpagethemename()});
		}else{
			themecode=userThemes.getSelectedTheme();
			DbUtil.executeUpdateQuery(ThemeQueries.UPDATE_MY_THEME,new String [] {attribMap.get("HTML"),userThemes.getSelectedTheme()});
			DbUtil.executeUpdateQuery(ThemeQueries.UPDATE_MY_THEME_CSS,new String [] {attribMap.get("CSS"),userThemes.getSelectedTheme()});
			DbUtil.executeUpdateQuery(ThemeQueries.UPDATE_MY_THEME_WITH_NAME,new String [] {userThemes.getMyeventpagethemename(),userThemes.getSelectedTheme()});
		}
	}	
	String currenttheme=userThemes.getSelectedTheme();	
	if(!themecode.equals(currenttheme)){
		updatePublicPageThemes(mgrId,"eventspage",themecode,themetype);		
	}
}
public static void saveMyPublicPageThemeContent(String themecode,String mgrId,UserThemes userThemes,HashMap<String,String> attribMap){
		String isexist=DbUtil.getVal("select 'Yes' from user_customized_themes where themeid=? and userid=?",new String[]{userThemes.getSelectedTheme(),mgrId});
		if(isexist==null){
			String themeid=DbUtil.getVal(ThemeQueries.GET_THEME_ID,null);
			themecode=themeid;
			DbUtil.executeUpdateQuery(ThemeQueries.INSERT_MYTHEME_DATA,new String [] {mgrId,attribMap.get("CSS"),attribMap.get("HTML"),"eventspage",themeid,userThemes.getMyeventpagethemename()});
		}else{
			themecode=userThemes.getSelectedTheme();
			DbUtil.executeUpdateQuery(ThemeQueries.UPDATE_MY_THEME,new String [] {attribMap.get("HTML"),userThemes.getSelectedTheme()});
			DbUtil.executeUpdateQuery(ThemeQueries.UPDATE_MY_THEME_CSS,new String [] {attribMap.get("CSS"),userThemes.getSelectedTheme()});
			DbUtil.executeUpdateQuery(ThemeQueries.UPDATE_MY_THEME_WITH_NAME,new String [] {userThemes.getMyeventpagethemename(),userThemes.getSelectedTheme()});

			
		}

	String currenttheme=userThemes.getSelectedTheme();		
	if(!themecode.equals(currenttheme)){
		updatePublicPageThemes(mgrId,"eventspage",themecode,"PERSONAL");		
	}
}
public static void updatePublicPageThemes(String userid,String module,String code,String themetype){	
	String DELETE_PUBLIC_PAGE_THEME_ENTRY="delete from user_roller_themes where userid=to_number(?,'99999999999999999') and module=? ";
	String INSERT_PUBLIC_PAGE_THEME_ENTRY="insert into user_roller_themes (userid,module,themecode,themetype,cssurl) values(to_number(?,'99999999999999999'),?,?,?,?)";
	String DELETE_PUBLIC_PAGE_CUSTOM_THEME="delete from user_custom_roller_themes where userid=to_number(?,'99999999999999999') and module=?";

	DBQueryObj [] dbquery=new DBQueryObj [3];
	dbquery[0]=new DBQueryObj(DELETE_PUBLIC_PAGE_THEME_ENTRY,new String[]{userid,module});
	dbquery[1]=new DBQueryObj(INSERT_PUBLIC_PAGE_THEME_ENTRY,new String[]{userid,module,code,themetype,code+".css"});
	dbquery[2]=new DBQueryObj(DELETE_PUBLIC_PAGE_CUSTOM_THEME,new String[]{userid,module});
	DbUtil.executeUpdateQueries(dbquery);
}
public static void updatePreferences(String mgrId,UserThemes userThemes,ArrayList<Entity> selectedEvents){
	String DELETE_QUERY="delete from member_preference where user_id=? and pref_name=? ";
	String INSERT_QUERY="insert into member_preference(user_id,pref_name,pref_value) values(?,?,?)";
	DbUtil.executeUpdateQuery(DELETE_QUERY,new String[]{mgrId,"events.upcomingEvents"});
	DbUtil.executeUpdateQuery(DELETE_QUERY,new String[]{mgrId,"events.pastEvents"});
	DbUtil.executeUpdateQuery(DELETE_QUERY,new String[]{mgrId,"events.photoDisplay"});
	DbUtil.executeUpdateQuery(DELETE_QUERY,new String[]{mgrId,"events.ProfileDisplay"});
	
	DbUtil.executeUpdateQuery(INSERT_QUERY,new String[]{mgrId,"events.upcomingEvents",userThemes.getUpcomingEvents()});
	DbUtil.executeUpdateQuery(INSERT_QUERY,new String[]{mgrId,"events.pastEvents",userThemes.getPastEvents()});
	DbUtil.executeUpdateQuery(INSERT_QUERY,new String[]{mgrId,"events.photoDisplay",userThemes.getPhotoDisplay()});
	DbUtil.executeUpdateQuery(INSERT_QUERY,new String[]{mgrId,"events.ProfileDisplay",userThemes.getProfileDisplay()});

	DbUtil.executeUpdateQuery("update user_profile set shareprofile=?,updated_at=now() where user_id=?",new String[]{userThemes.getProfileDisplay(),mgrId});
	
	//changes done by rambabu.
	String upCommingEvents = userThemes.getUpcomingEvents();
	String eventsDisplayType="";
	if(upCommingEvents.equals("Selected"))
		eventsDisplayType = "SELECTED";
	else eventsDisplayType = "ALL";
	String boxoffice_id=DbUtil.getVal("select boxoffice_id from box_office_master where userid=?", new String[]{mgrId});
	if(boxoffice_id==null || "".equals(boxoffice_id)){
		boxoffice_id=DbUtil.getVal("select nextval('seq_boxoffice_id')",null);
		String INSERT_BOX_OFFICE="insert into box_office_master(boxoffice_id,userid,events_display_type) values(?,?,?)";
		DbUtil.executeUpdateQuery(INSERT_BOX_OFFICE, new String[]{boxoffice_id,mgrId,eventsDisplayType});
		if(upCommingEvents.equals("Selected"))
		updateBoxOfficeEvents(boxoffice_id,selectedEvents);
	}else{
		String UPDATE_BOX_OFFICE = "update box_office_master set events_display_type=? where boxoffice_id=?";
		DbUtil.executeUpdateQuery(UPDATE_BOX_OFFICE, new String[]{eventsDisplayType,boxoffice_id});
		if(upCommingEvents.equals("Selected"))
		updateBoxOfficeEvents(boxoffice_id, selectedEvents);
	}
}


public static void updateBoxOfficeEvents(String boxoffice_id,ArrayList<Entity> selectedEvents){
	if(selectedEvents.size()>0){
		System.out.println("\n EXECUTING IN DB");
		DbUtil.executeUpdateQuery(DELETE_EVENTS, new String[]{boxoffice_id});
		for(int i=0;i<selectedEvents.size();i++){
			DbUtil.executeUpdateQuery(INSERT_EVENTS, new String[]{boxoffice_id,selectedEvents.get(i).getKey(),""+(i+1)});
		}
	}
}

public static void updatePublicPagePhotoURL(String mgrId,String eventphotoURL,String uploadurl){
	//DbUtil.executeUpdateQuery("delete from config where config_id=to_number(?,'9999999999999999') and name=?",new String[]{mgrId,"event.eventsPublicPagephotoURL"});
	//DbUtil.executeUpdateQuery("insert into config(config_id, name, value) values(to_number(?,'9999999999999999'),?,?)",new String[]{mgrId,"event.eventsPublicPagephotoURL",eventphotoURL});
	
	//changes done by rambabu.
	String boxoffice_id=DbUtil.getVal("select boxoffice_id from box_office_master where userid=?", new String[]{mgrId});
	if(boxoffice_id==null || "".equals(boxoffice_id)){
		boxoffice_id=DbUtil.getVal("select nextval('seq_boxoffice_id')",null);
		String INSERT_BOX_OFFICE_PHOTO="insert into box_office_master(boxoffice_id,userid,photo_url) values(?,?,?)";
		DbUtil.executeUpdateQuery(INSERT_BOX_OFFICE_PHOTO, new String[]{boxoffice_id,mgrId,eventphotoURL});
		
	}else{
		String UPDATE_BOX_OFFICE_PHOTO = "update box_office_master set photo_url=? where boxoffice_id=?";
		DbUtil.executeUpdateQuery(UPDATE_BOX_OFFICE_PHOTO, new String[]{eventphotoURL,boxoffice_id});
		
	}
	
}

public static void deltePublicPagePhotoURL(String mgrId){
	DbUtil.executeUpdateQuery("update box_office_master set photo_url='' where userid=?",new String[]{mgrId});
}

public static String getBoxOfficePhotoURL(String mgrId){
	String photourl=DbUtil.getVal("select photo_url from box_office_master where userid=?",new String[]{mgrId});
	return photourl;
}



public static HashMap<String,String> getHeadernFooter(String themeid){
	DBManager dbm=new DBManager();
	HashMap<String,String> hmp=new HashMap<String,String>();
	StatusObj Obj=dbm.executeSelectQuery(HEADER_FOOTER_VALUE, new String[]{themeid});
	if(Obj.getStatus()){
	             for(int i=0;i<Obj.getCount();i++)
	             {   
	                 hmp.put(dbm.getValue(i,"attrib_name",""),dbm.getValue(i,"attrib_value",""));
	           
	                  
	             }
	                           }
	
	return hmp;
}

















/*public static String getConfigVal(String mgrId, String keyname, String defaultval){
	String configval=DbUtil.getVal("select value from config where config_id=to_number(?,'9999999999999999') and name=?",new String[]{mgrId, keyname});
	if(configval==null)
		configval=defaultval;
	return configval;
}
*/
}