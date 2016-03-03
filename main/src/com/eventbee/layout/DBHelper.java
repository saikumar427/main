package com.eventbee.layout;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import com.event.helpers.I18n;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject; 
import java.util.ResourceBundle;
import com.event.dbhelpers.CustomAttributesDB;
import com.event.dbhelpers.DisplayAttribsDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.EventHelperDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.I18NCacheData;
import com.eventbee.general.StatusObj;

public class DBHelper{
	public static String getLayout(String eventid,String ref,String stage) throws Exception {
		try{
			Boolean isRecurring=EventDB.isRecurringEvent(eventid);
			DBManager dbm = new DBManager();
			StatusObj statobj;
			System.out.println("getLayout stage:::::"+stage+" eventid: "+eventid);
			HashMap<String, String> widgettitles=getTitles(eventid,stage) ;
			String query = "select * from event_layout where eventid = ?::bigint and stage = 'draft';";
			if("final".equals(stage))
				query = "select * from event_layout where eventid = ?::bigint and stage = 'final';";
			
			statobj = dbm.executeSelectQuery(query, new String[]{eventid});
			
			
			JSONObject result = null;
			
			if(statobj.getStatus() && statobj.getCount()>0 ) {
				result=fillLayout(eventid,ref,dbm,widgettitles,isRecurring);
				
			}else{
				//for final and draft(preview), default event_layout is same 
				String	defaultQuery = "select * from event_layout where eventid = '1'::bigint and stage = 'final';";
				
				statobj = dbm.executeSelectQuery(defaultQuery, null);
				
				result=fillLayout(eventid,ref,dbm,widgettitles,isRecurring);
			}
			
			result.put("allThemes", getThemeDetails());
			Boolean isSeating = EventDB.isSeatingEvent(eventid);
			result.put("isSeating",isSeating);
			
			return result.toString();
			
		}catch(Exception e){System.out.println("exception while getlayout"+e.getMessage());e.printStackTrace();}
		return "";
	}
	
	public static JSONObject fillLayout(String eventid,String ref, DBManager dbm, HashMap<String, String> widgettitles,Boolean isRecurring){
		JSONObject result = new JSONObject();
		try{
		
		String[]widgets;
		JSONObject hidewidgets = new JSONObject();	
		JSONArray hidewidgetsarr = new JSONArray();	
		
		widgets = dbm.getValue(0, "hide_widgets", "").split(",");			
		for(int i=0;i<widgets.length;i++) {	
			if("".equals(widgets[i]))
				continue;
		    hidewidgets.put(widgets[i],i);
		    hidewidgetsarr.put(widgets[i]);
		}
		
		result.put("hide-widgets", hidewidgetsarr);
		result.put("sync",  dbm.getValue(0, "sync", "yes"));
		
		widgets = dbm.getValue(0, "wide_widgets", "").split(",");
		JSONArray wide = new JSONArray();
		for(int i=0;i<widgets.length;i++) {				
			if("".equals(widgets[i]))continue;
			String tle=widgettitles.get(widgets[i]+ref);
			tle=tle==null?"":tle;
			tle=URLDecoder.decode(tle,"UTF-8");
			tle=tle==null?"":tle;
			if(checkHideStatus(widgets[i],ref,hidewidgets))
				continue;
			wide.put(new JSONObject().put(widgets[i],tle));
			
		}
		result.put("column-wide", wide);
		
		widgets = dbm.getValue(0, "narrow_widgets", "").split(",");
		JSONArray narrow = new JSONArray();
		for(int i=0;i<widgets.length;i++) {
			if("".equals(widgets[i]))continue;
			String tle=widgettitles.get(widgets[i]+ref);
			tle=tle==null?"":tle;
			tle=URLDecoder.decode(tle,"UTF-8");
			tle=tle==null?"":tle;
			if(checkHideStatus(widgets[i],ref,hidewidgets))
				continue;
			if(!(isRecurring && "when".equalsIgnoreCase(widgets[i])))
			 narrow.put(new JSONObject().put(widgets[i],tle));
		}
		result.put("column-narrow", narrow);			
		
		widgets = dbm.getValue(0, "single_widgets", "").split(",");
	
		JSONArray single = new JSONArray();
		for(int i=0;i<widgets.length;i++) {
			if("".equals(widgets[i]))continue;
			String tle=widgettitles.get(widgets[i]+ref);
			tle=tle==null?"":tle;
			 tle=URLDecoder.decode(tle,"UTF-8");
			tle=tle==null?"":tle;
			if(checkHideStatus(widgets[i],ref,hidewidgets))
				continue;
			single.put(new JSONObject().put(widgets[i],tle));
		}
		result.put("column-single", single);
		
		widgets = dbm.getValue(0, "single_bottom_widgets", "").split(",");
		JSONArray single_bottom = new JSONArray();
		for(int i=0;i<widgets.length;i++) {
			if("".equals(widgets[i]))continue;
			String tle=widgettitles.get(widgets[i]+ref);
			tle=tle==null?"":tle;
		    tle=URLDecoder.decode(tle,"UTF-8");
			tle=tle==null?"":tle;
			if(checkHideStatus(widgets[i],ref,hidewidgets))
				continue;
			single_bottom.put(new JSONObject().put(widgets[i],tle));
		}
		result.put("column-single-bottom", single_bottom);			
		
		result.put("eventid", dbm.getValue(0, "eventid", ""));
		//System.out.println("before layout::"+dbm.getValue(0, "global_style", ""));
		result.put("global_style", new JSONObject(dbm.getValue(0, "global_style", "")));
		
		result.put("themeCodeName", dbm.getValue(0, "themecode", ""));
		
		if(!"".equals(dbm.getValue(0, "header_theme", "").trim())) {
			result.put("header_theme", dbm.getValue(0, "header_theme", ""));
		} else {
			result.put("header_theme",InitHeaderTheme(eventid));
		}
		
		JSONObject added = LayoutManager.concatArrays(single,wide,narrow,single_bottom);
		result.put("added",added);
		
		}catch(Exception e){
			System.out.println("Exception in DBHelper.java fillLayout() ERROR:: "+e.getMessage());
		}
		return result;
	}
	
	
	public static JSONArray getThemeDetails(){
		ResourceBundle resourceBundle =I18n.getResourceBundle();
		DBManager dbm =new DBManager();
		String ThemQuery = "select * from event_page_themes where eventid= 0";
		StatusObj statusTheme = dbm.executeSelectQuery(ThemQuery,null);
		JSONArray themeData = new JSONArray();
		if(statusTheme.getStatus() && statusTheme.getCount()>0){
			for(int i=0;i<statusTheme.getCount();i++) {
				try{
					JSONObject themeKey = new JSONObject();
					themeKey.put("themeKey",dbm.getValue(i, "themecode", ""));
					String themeCode= dbm.getValue(i, "themecode", "");
					String themeNamei18n = resourceBundle.getString("pg.theme.name."+themeCode);
					themeKey.put("themeValue",themeNamei18n);
					themeData.put(themeKey);
				}catch(Exception e){
					System.out.println("Exception in select thems"+e);
				}
			}
		}
		return themeData;
		
	}
	/*public static String chenageThemJson(String eid, String themecode){
		System.out.println("Themedata seaving..");
		String themeData=DbUtil.getVal("select global_style from event_layout where eventid=?::bigint and stage='draft'", new String[]{eid});
		if(themeData==null || "".equals(themeData)){
			String query = "insert into event_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode) (select  ?::bigint,'draft','no',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode from event_layout where eventid='1'::bigint )";
			DbUtil.executeUpdateQuery(query, new String[]{eid});
		}
		try{
			String updateTheme = "update event_layout set global_style=(select themejson from event_page_themes where eventid=0 and themecode=?), themecode=? where eventid=? ::bigint and stage='draft'";
			StatusObj successObj = DbUtil.executeUpdateQuery(updateTheme, new String[]{themecode,themecode,eid});
		}catch(Exception e){
			System.out.println("Exception while update theme");
			return "fail";
		}
		return "success";
	}*/
	public static String chenageThemJson(String eid, String themecode){
		System.out.println("Update Theme...");
		String themeData=DbUtil.getVal("select global_style from event_layout where eventid=?::bigint and stage='draft'", new String[]{eid});
		if(themeData==null || "".equals(themeData)){
			String query = "insert into event_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode) (select  ?::bigint,'draft','no',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode from event_layout where eventid='1'::bigint )";
			DbUtil.executeUpdateQuery(query, new String[]{eid});
		}
		String coverPhoto="",logomsg="",logourl ="",titleShow="Y";
		try{
			String globalStyleQry = "select global_style from event_layout where eventid=?::bigint and stage='draft'";
			String global_style = DbUtil.getVal(globalStyleQry, new String[]{eid});
			JSONObject gloablStyle=new JSONObject(global_style);
			 coverPhoto = gloablStyle.getString("coverPhoto");
			 logourl = gloablStyle.getString("logourl");
			 logomsg = gloablStyle.getString("logomsg");
			if(gloablStyle.has("titleShow"))
				 titleShow = gloablStyle.getString("titleShow");
		}catch(Exception e){
			System.out.println("Exception at update theme");
		}
		try{
			String themeJSON=DbUtil.getVal("select themejson from event_page_themes where eventid=0 and themecode=?", new String[]{themecode});
			JSONObject json=new JSONObject(themeJSON);
			json.put("coverPhoto", coverPhoto);
			json.put("logourl", logourl);
			json.put("logomsg", logomsg);
			json.put("titleShow", titleShow);
			String updateTheme = "update event_layout set global_style='"+json.toString()+"', themecode=? where eventid=? ::bigint and stage='draft'";
			DbUtil.executeUpdateQuery(updateTheme, new String[]{themecode,eid});
		}catch(Exception e){
			System.out.println("Exception while update theme");
			String updateTheme = "update event_layout set global_style=(select themejson from event_page_themes where eventid=0 and themecode=?), themecode=? where eventid=? ::bigint and stage='draft'";
			DbUtil.executeUpdateQuery(updateTheme, new String[]{themecode,themecode,eid});
		}
		updateBuyerGlobalStyle(eid,"draft");
		return "success";
	}
	
	public static void updateBuyerGlobalStyle(String eid,String stage){
		System.out.println("global_style saving in buyer_att_page_layout stage = "+stage);
		String globalStyle = "select global_style from event_layout where eventid=? and stage='draft'";
		String global_style = DbUtil.getVal(globalStyle, new String[]{eid});
		String themeData="";
		if("draft".equals(stage))
			themeData=DbUtil.getVal("select global_style from buyer_att_page_layout where eventid=?::bigint and stage='draft'", new String[]{eid});
		else
			themeData=DbUtil.getVal("select global_style from buyer_att_page_layout where eventid=?::bigint and stage='final'", new String[]{eid});
		if(themeData==null || "".equals(themeData)){
			String query="";
			if("draft".equals(stage))
				query = "insert into buyer_att_page_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets) (select  ?::bigint,'draft','no',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets from buyer_att_page_layout where eventid='1'::bigint )";
			else
				query = "insert into buyer_att_page_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets) (select  ?::bigint,'final','yes',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets from buyer_att_page_layout where eventid='1'::bigint )";
			DbUtil.executeUpdateQuery(query, new String[]{eid});
		}
		String buyerstyleUpdate="update buyer_att_page_layout set global_style=? where eventid=?::bigint and stage=? and layout_type='buyer'";
		DbUtil.executeUpdateQuery(buyerstyleUpdate, new String[]{global_style,eid,stage});
	}
	
	public static Boolean resetLastFinal(String eventid){
		String query = "update event_layout set global_style=aa.global_style,wide_widgets=aa.wide_widgets,narrow_widgets=aa.narrow_widgets,single_widgets=aa.single_widgets,single_bottom_widgets=aa.single_bottom_widgets,hide_widgets=aa.hide_widgets,updated_at='now()' from (select global_style,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,hide_widgets from event_layout  where stage='final' and eventid=?::bigint) aa where stage='draft' and eventid=?::bigint ";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,eventid});
		 if(statobj.getStatus() && statobj.getCount()>0)			
		 { updateSync(eventid, "yes");
		    /*query = "delete from  widget_options where widgetid not in(select widgetid from widget_options where  stage='final' and eventid=?::bigint) and stage='draft' and eventid=?::bigint ";
		   statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,eventid});
			 
			 
		   query = "update widget_options w set widgetid=aa.widgetid,config_data=aa.config_data,widget_title=aa.widget_title,widget_ref_title=aa.widget_ref_title,updated_at='now()' from (select widgetid,config_data,widget_title,widget_ref_title from widget_options  where stage='final' and eventid=?::bigint) aa where stage='draft' and w.widgetid=aa.widgetid and eventid=?::bigint ";
		   statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,eventid});
		   
		   query="update widget_styles ws set widgetid=aa.widgetid,styles=aa.styles from(select widgetid,styles from widget_styles where stage='final' and eventid=?::bigint) aa where stage='draft' and ws.widgetid=aa.widgetid and eventid=?::bigint ";
		   statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,eventid});*/
			 query="delete  from custom_widget_options where eventid=?::bigint and stage='draft'";
			 statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid});
			 
			 query = "insert into  custom_widget_options (eventid,widgetid,config_data,widget_title,widget_ref_title,updated_at,stage) select eventid,widgetid,config_data,widget_title,widget_ref_title,now(),'draft' from custom_widget_options  where stage='final' and eventid=?::bigint ";
			 statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid});
			 
			 
			/* query = "delete from  widget_styles  where eventid=?::bigint and stage='draft'";
			 statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid});
			 
			 query = "insert into widget_styles (widgetid,styles,stage,eventid)  select widgetid,styles,'draft',eventid from widget_styles  where stage='final' and eventid=?::bigint ";
			 statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid});*/
		 			   
		   if(statobj.getStatus() ){
			   return true;
		   }
		   else				 
		   return false;
		 }		 
		 else
		 return false;		
		
	}
	private static Boolean checkHideStatus(String widgetid,String ref,JSONObject hides){
		if("".equals(ref))
		{  if( hides.has(widgetid))
			return true;
		  else
			return false;
		}
		else
		return false;
	}
	
	public static Boolean saveHide(String eventid,String data){
		try{		  	 
			data=data==null?data="":data;
			data=data.replace("[", "").replace("]", "").replace("\"", "");
			
			String query   = "update event_layout set hide_widgets=? where eventid=?::bigint and stage='draft'";
			StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{data,eventid});
				if(statobj.getStatus() && statobj.getCount()>0)
					{updateSync(eventid, "no");
					return true;					
					}
					else						 
					return false;							
		}catch(Exception e){System.out.println(" exception in saveHide  ");return false;}					

		
	}
	public static Boolean saveLayout(String jsonData) throws Exception {
		System.out.println("the final json data is::"+jsonData);
		JSONObject data = new JSONObject(jsonData);
		JSONArray jsondata = data.getJSONArray("column-wide");
				
		boolean validation=false;
		for(int i=0;i<jsondata.length()&&!validation;i++){
			Iterator<?> iterator = jsondata.getJSONObject(i).keys();
			while(iterator.hasNext()){
				String	mykey = (String)iterator.next();
				if("tickets".equals(mykey)){
					validation=true;
					break;
				}
			}
		}
		
		jsondata = data.getJSONArray("column-single");
		for(int i=0;i<jsondata.length()&&!validation;i++){
				Iterator<?> iterator = jsondata.getJSONObject(i).keys();
				while(iterator.hasNext()){
				String	mykey = (String)iterator.next();
				if("tickets".equals(mykey)){
					validation=true;
					break;
				}
			}
		}
		
		jsondata = data.getJSONArray("column-single-bottom");
		for(int i=0;i<jsondata.length()&&!validation;i++){
				Iterator<?> iterator = jsondata.getJSONObject(i).keys();
				while(iterator.hasNext()){
				String	mykey = (String)iterator.next();
				if("tickets".equals(mykey)){
					validation=true;
					break;
				}
			}
		}
		
		jsondata = data.getJSONArray("column-narrow");
		for(int i=0;i<jsondata.length()&&!validation;i++){
				Iterator<?> iterator = jsondata.getJSONObject(i).keys();
				while(iterator.hasNext()){
				String	mykey = (String)iterator.next();
				if("tickets".equals(mykey)){
					validation=true;
					break;
				}
			}
		}
		
		System.out.println(validation+"::my validation");
		if(!validation)
			return validation;
		
		JSONObject titlesjson;
		String wideWidgets="",narrowWidgets="",singleWidgets="",next,singlBottomWidgets="";
		if(data.has("titles")){	
			 titlesjson=data.getJSONObject("titles");
		     saveTitle(data.getString("eventid"), titlesjson);
		}


	try{
		JSONArray wide = data.getJSONArray("column-wide");
		for(int i=0;i<wide.length();i++) {
			Iterator<?> iterator = wide.getJSONObject(i).keys();
			while(iterator.hasNext()) {
				next = (String)iterator.next();
				wideWidgets += next + ",";
				}
		}
	}catch(Exception e){System.out.println(" wide coulmn parsing"+e.getMessage());}
	
	  try{
		JSONArray narrow = data.getJSONArray("column-narrow");
		for(int i=0;i<narrow.length();i++) {
			Iterator<?> iterator = narrow.getJSONObject(i).keys();
			while(iterator.hasNext()) {
			next = (String)iterator.next();
			narrowWidgets += next + ",";
			}
		}
	  }catch(Exception e){System.out.println(" narrow coulmn parsing"+e.getMessage());}
		
	 try{	
		JSONArray single = data.getJSONArray("column-single");
		
		for(int i=0;i<single.length();i++) {
			Iterator<?> iterator = single.getJSONObject(i).keys();
			while(iterator.hasNext()) {
				next = (String)iterator.next();
				singleWidgets += next + ",";
			   }
		     }
        }catch(Exception e){System.out.println(" single coulmn parsing"+e.getMessage());}
		
	 try{
			JSONArray single_bottom = data.getJSONArray("column-single-bottom");	
	    	for(int i=0;i<single_bottom.length();i++) {
			Iterator<?> iterator = single_bottom.getJSONObject(i).keys();
			while(iterator.hasNext()) {
				next = (String)iterator.next();
				singlBottomWidgets += next + ",";
			}
		}
	   }catch(Exception e){System.out.println(" single-bottom coulmn parsing"+e.getMessage());}
	 
		
		if(!"".equals(wideWidgets))wideWidgets = wideWidgets.substring(0, wideWidgets.length()-1);
		if(!"".equals(narrowWidgets))narrowWidgets = narrowWidgets.substring(0, narrowWidgets.length()-1);
		if(!"".equals(singleWidgets))singleWidgets = singleWidgets.substring(0, singleWidgets.length()-1);
		if(!"".equals(singlBottomWidgets))singlBottomWidgets = singlBottomWidgets.substring(0, singlBottomWidgets.length()-1);
			
		String query = "update event_layout set wide_widgets=?,narrow_widgets=?,single_widgets=?,single_bottom_widgets=?,updated_at='now()',sync='no' where stage=? and eventid=?::bigint ";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{wideWidgets,narrowWidgets,singleWidgets,singlBottomWidgets,"draft",data.getString("eventid")});
		if(statobj.getCount()==0){
			
			
			query = "insert into event_layout (eventid,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode," +
					"stage,sync,updated_at,created_at) select CAST(? AS BIGINT),?,?,?,?,header_theme,global_style,hide_widgets,themecode,'draft','no'," +
					"to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') from event_layout where eventid='1'::bigint";
					//" values(CAST(? AS BIGINT),?,?,?,?,'draft','no',to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
			
			statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid"),wideWidgets,narrowWidgets,singleWidgets,singlBottomWidgets,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate()});
		}
		//updateSync(data.getString("eventid"), "no");
		if(statobj.getStatus() && statobj.getCount()>0 && "draft".equals(data.getString("stage")))
			return true;

		else if(statobj.getStatus() && statobj.getCount()>0 && "final".equals(data.getString("stage"))){
			
			query = "update event_layout set wide_widgets=aa.wide_widgets,narrow_widgets=aa.narrow_widgets,single_widgets=aa.single_widgets, " +
					"single_bottom_widgets=aa.single_bottom_widgets,hide_widgets=aa.hide_widgets,updated_at='now()',global_style=aa.global_style, " +
					"themecode=aa.themecode,header_theme=aa.header_theme,sync='yes' from (select wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,hide_widgets,global_style, " +
					"themecode,header_theme from event_layout  where stage='draft' and eventid=?::bigint) aa where stage='final' and eventid=?::bigint ";
			
			//System.out.println(query); 
			statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid"),data.getString("eventid")});
			
			if(statobj.getCount()==0){
				
				query="insert into event_layout (eventid,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,hide_widgets,global_style, " +
						"themecode,header_theme,sync,stage,updated_at,created_at) select CAST(? AS BIGINT),wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets, " +
						"hide_widgets,global_style,themecode,header_theme,'yes','final',to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') " +
						"from event_layout where eventid=CAST(? AS BIGINT) and stage='draft'";
				
				statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid"),DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),data.getString("eventid")});
			}
			 if(statobj.getStatus() && statobj.getCount()>0){ 
				 updateSync(data.getString("eventid"), "yes");
				 if("final".equals(data.getString("stage"))){
					 updateBuyerGlobalStyle(data.getString("eventid"),"final");
					 String db_styles=DbUtil.getVal("select global_style from event_layout where eventid=?::bigint and stage='final'", new String[]{data.getString("eventid")});
					 try{
						 JSONObject global_style = new JSONObject(db_styles);
						 String logo = global_style.getString("logourl");
						 DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.eventphotoURL'",new String[]{data.getString("eventid")});
						 DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.eventphotoURL",logo,data.getString("eventid")});
					 }catch(Exception e){
						 System.out.println("");
					 }
					
				 }
			   /* query = "delete from  widget_options where widgetid not in(select widgetid from widget_options where  stage='draft' and eventid=?::bigint) and stage='final' and eventid=?::bigint ";
			   statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid"),data.getString("eventid")});
				 
				 
			   query = "update widget_options w set widgetid=aa.widgetid,config_data=aa.config_data,widget_title=aa.widget_title,widget_ref_title=aa.widget_ref_title,updated_at='now()' from (select widgetid,config_data,widget_title,widget_ref_title from widget_options  where stage='draft' and eventid=?::bigint) aa where stage='final' and w.widgetid=aa.widgetid and eventid=?::bigint ";
			   statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid"),data.getString("eventid")});
			   
			   query = "delete from  widget_styles where widgetid not in(select widgetid from widget_styles where  stage='draft' and eventid=?::bigint) and stage='final' and eventid=?::bigint ";
			   statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid"),data.getString("eventid")});
			   
			   query = "update widget_styles w set widgetid=aa.widgetid,styles=aa.styles from (select widgetid,styles from widget_styles  where stage='draft' and eventid=?::bigint) aa where stage='final' and w.widgetid=aa.widgetid and eventid=?::bigint ";
			   statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid"),data.getString("eventid")});*/
				 query="delete  from custom_widget_options where eventid=?::bigint and stage='final'";
				 statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid")});
				 
				 query = "insert into  custom_widget_options (eventid,widgetid,config_data,widget_title,widget_ref_title,updated_at,stage) select eventid,widgetid,config_data,widget_title,widget_ref_title,now(),'final' from custom_widget_options  where stage='draft' and eventid=?::bigint ";
				 statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid")});
				 
				/* query = "delete from  widget_styles  where eventid=?::bigint and stage='final'";
				 statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid")});
				 
				 query = "insert into widget_styles (widgetid,styles,stage,eventid)  select widgetid,styles,'final',eventid from widget_styles  where stage='draft' and eventid=?::bigint ";
				   statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid")});*/
				 
				 
			   if(statobj.getStatus() ){
				   EventHelperDB.removeGLobalEventCache(data.getString("eventid"), "remove", "layoutmanage");
			    return true;
			   }
			   else				 
			   return false;
			   
			   
			 }
			 
			 else
			 return false;			 
		 }
		  else
		 return false;		

		

	}
	
	public static String getGlobalStyles(String eventid) {
		DBManager dbm = new DBManager();
		StatusObj statobj;
		String query = "select global_style from event_layout where eventid = ?::bigint and stage='draft'";
		statobj = dbm.executeSelectQuery(query, new String[]{eventid});
		if(statobj.getStatus())
			return dbm.getValue(0, "global_Style", "");
		else{
			query = "select global_style from event_layout where eventid = '1'::bigint";
			statobj = dbm.executeSelectQuery(query, new String[]{});
			if(statobj.getStatus())
				return dbm.getValue(0, "global_Style", "");
		}
		return "";
		
	}
	public static Boolean allStyleSettings(String current_styles,String eventid) {	
		System.out.println("Page design Style saving in draft");
		String db_styles=DbUtil.getVal("select global_style from event_layout where eventid=?::bigint and stage='draft'", new String[]{eventid});
		if(db_styles==null || "".equals(db_styles)){
			String query = "insert into event_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode) (select  ?::bigint,'draft','no',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode from event_layout where eventid='1'::bigint )";
			DbUtil.executeUpdateQuery(query, new String[]{eventid});
			db_styles=DbUtil.getVal("select global_style from event_layout where eventid=?::bigint",new String[]{"1"});
		}
	String query="update event_layout set global_style=? where eventid=?::bigint and stage='draft'";
	StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{current_styles,eventid});
	updateSync(eventid, "no");
	updateBuyerGlobalStyle(eventid,"draft");
	if(statobj.getStatus())
		return true;
	else
		return false;
	}
	

	
	
	
	public static Boolean saveGlobalStyles(String current_styles,String eventid) {		
		String db_styles=DbUtil.getVal("select global_style from event_layout where eventid=?::bigint and stage='draft'", new String[]{eventid});
		if(db_styles==null || "".equals(db_styles)){
			String query = "insert into event_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode) (select  ?::bigint,'draft','no',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode from event_layout where eventid='1'::bigint )";
			DbUtil.executeUpdateQuery(query, new String[]{eventid});
			db_styles=DbUtil.getVal("select global_style from event_layout where eventid=?::bigint",new String[]{"1"});
		}
			
		try{
		JSONObject dbjson=new JSONObject(db_styles);
		JSONObject currjson=new JSONObject(current_styles);
		currjson.put("details",dbjson.has("details")?dbjson.getString("details"):"");
		currjson.put("coverPhoto",dbjson.has("coverPhoto")?dbjson.getString("coverPhoto"):"");
		currjson.put("logourl",dbjson.has("logourl")?dbjson.getString("logourl"):"");
		currjson.put("logomsg",dbjson.has("logomsg")?dbjson.getString("logomsg"):"");
		currjson.put("title",dbjson.has("title")?dbjson.getString("title"):"");
		String query = "update event_layout set global_style=? where eventid=?::bigint and stage='draft'";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{currjson.toString(),eventid});
		updateSync(eventid, "no");
		if(statobj.getStatus())
			return true;
		else
			return false;
		
		}catch(Exception e){
			System.out.println("inside saving global_style of event::"+eventid+" :: "+e.getMessage());
			return false;
		}		
	}
	
	
	public static Boolean saveHeaderSettings(String headersettings,String eventid){
		System.out.println("in header setting method");
		String global_style=DbUtil.getVal("select global_style from event_layout where eventid=?::bigint and stage='draft'", new String[]{eventid});
		
		if(global_style==null || "".equals(global_style)){
			String query = "insert into event_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode) (select  ?::bigint,'draft','no',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode from event_layout where eventid='1'::bigint )";
			DbUtil.executeUpdateQuery(query, new String[]{eventid});
			global_style=DbUtil.getVal("select global_style from event_layout where eventid=?::bigint",new String[]{"1"});
		}	
		
		try{
		JSONObject dbjson=new JSONObject(global_style);
		JSONObject currjson=new JSONObject(headersettings);
		dbjson.put("details",currjson.getString("details"));
		dbjson.put("coverPhoto",currjson.getString("coverPhoto"));
		dbjson.put("logourl",currjson.getString("logourl"));
		dbjson.put("logomsg",currjson.getString("logomsg"));
		dbjson.put("title",currjson.getString("title"));
		
		String query = "update event_layout set global_style=? where eventid=?::bigint and stage='draft'";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{dbjson.toString(),eventid});
		if(statobj.getStatus())
			return true;
		else
			return false;
		
		}catch(Exception e){
			return false;
		}
		
	}
	
	public static JSONObject getWidgetStyles(String eventid,String widgetid,String stage)throws JSONException {
		String query = "select * from widget_styles where eventid=?::bigint and widgetid=? and stage=?";
		DBManager dbm = new DBManager();
		StatusObj statobj = dbm.executeSelectQuery(query, new String[]{eventid,widgetid,stage});
		String result = "";
		if(statobj.getStatus()) {
			result = dbm.getValue(0, "styles", "");
		}
		return new JSONObject(result);
	}
	public static JSONObject getWidgetStyles(String eventid,String stage)throws JSONException {
		String query="";
		if("draft".equalsIgnoreCase(stage))
		    query = "select * from widget_styles where eventid=?::bigint and stage='draft'";
		else
			query = "select * from widget_styles where eventid=?::bigint and stage='final'";
		DBManager dbm = new DBManager();
		StatusObj statobj = dbm.executeSelectQuery(query, new String[]{eventid});
		JSONObject result = new JSONObject();
		if(statobj.getStatus()) {
			for(int i=0;i<statobj.getCount();i++) {
				result.put(dbm.getValue(i, "widgetid", ""), new JSONObject( dbm.getValue(i, "styles", "")));
			}
		}
		return result;
	}
	public static Boolean saveWidgetStyles(String data,String eventid,String widgetid)throws JSONException {
		String query = "update widget_styles set styles=? where eventid=?::bigint and widgetid=? and stage='draft'";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{data,eventid,widgetid});
		updateSync(eventid, "no");
		if(statobj.getStatus())
			return true;
		else
			return false;
	}
	
	//call this for newly added widget from layout manager
	public static JSONObject InitWidgetStyle(String eventid, String widgetid) throws JSONException {
		String query = "select global_style from event_layout where eventid=?::bigint and stage='draft'";
		DBManager dbm = new DBManager();
		StatusObj statobj = dbm.executeSelectQuery(query, new String[]{eventid});
		if(statobj.getStatus()) {
			JSONObject result = new JSONObject(dbm.getValue(0, "global_style", ""));
			result.remove("bodyBackgroundColor");
			result.remove("contentBackground");
			result.remove("bodyBackgroundImage");
			result.remove("backgroundPosition");
			result.remove("coverPhoto");
			result.remove("details");
			result.remove("title");
			result.put("global", true);
			result.put("hideHeader", false);
			StatusObj s;
			query = "insert into widget_styles(eventid,widgetid,styles,stage) values(?::bigint,?,?,'draft')";
			s = DbUtil.executeUpdateQuery(query, new String[]{eventid,widgetid,result.toString()});
			query="insert into widget_styles(eventid,widgetid,styles,stage) values(?::bigint,?,?,'final')";
			s = DbUtil.executeUpdateQuery(query, new String[]{eventid,widgetid,result.toString()});
			if(s.getStatus())
				return result;
		}
		return new JSONObject();
	}
/*	public static JSONObject getAllWidgetOptions(String eventid,String stage) throws JSONException {
		String query = "select * from widget_options where eventid=?::bigint and stage=?";
		DBManager dbm = new DBManager();
		StatusObj statobj = dbm.executeSelectQuery(query, new String[]{eventid,stage});
		JSONObject result = new JSONObject();
		if(statobj.getStatus()) {
			for(int i=0;i<statobj.getCount();i++) {
				result.put(dbm.getValue(i, "widgetid", ""), dbm.getValueFromRecord(i, "config_data", ""));
				//System.out.println("test ::::::"+dbm.getValueFromRecord(i, "config_data", "").toString());
				//result.put(dbm.getValue(i, "widgetid", ""), new JSONObject( dbm.getValueFromRecord(i, "config_data", "").toString()));
			}
		}
		String query2 = "select latitude, longitude from eventinfo where eventid = ?::bigint";
		statobj = dbm.executeSelectQuery(query2, new String[]{eventid});
		if(statobj.getStatus() && statobj.getCount()>0){
			try{
				result.put("latitude",dbm.getValue(0, "latitude", ""));
				result.put("longitude", dbm.getValue(0, "longitude", ""));
			}catch(Exception e){
				System.out.println("Exception in MapLonLat");
			}
		}
		
		if(result.has("organizer")){
			String orgName = DbUtil.getVal("select value from config where config_id in(select config_id from eventinfo where eventid=?::bigint) and name='event.hostname'", new String[]{eventid});
			if(new JSONObject(result.getString("organizer")).has("hostbydata"))
				if("".equals(new JSONObject(result.getString("organizer")).get("hostbydata")))
					result.put("organizer",new JSONObject(result.getString("organizer")).put("hostbydata",orgName).toString());
		}
		
		
		return result;
	}*/
	
	
	public static String getWidgetOptions(String eventid,String widgetid) throws JSONException {
		return getWidgetOptions(eventid,widgetid,"draft");
	}

	public static String getWidgetOptions(String eventid,String widgetid,String stage) throws JSONException {
		String query = "select config_data from custom_widget_options where eventid=?::bigint and widgetid=? and stage=?";
		String result = DbUtil.getVal(query, new String[]{eventid,widgetid,stage});
		//String result = "";
		if(result==null) {
			String defaultwidgetid = widgetid;
			if(defaultwidgetid.contains("_"))
				defaultwidgetid = defaultwidgetid.split("_")[0];
			query="select config_data from default_widget_options where lang=? and widgetid=?";
			result=DbUtil.getVal(query, new String[]{I18NCacheData.getI18NLanguage(eventid),defaultwidgetid});
		} 
		
		if("organizer".equals(widgetid)){
			String orgName = DbUtil.getVal("select value from config where config_id in(select config_id from eventinfo where eventid=?::bigint) and name='event.hostname'", new String[]{eventid});
			JSONObject resultJson = new JSONObject(result);
			String hostbydata = resultJson.getString("hostbydata");
			if(hostbydata == null || "".equals(hostbydata))
				hostbydata = orgName;
			resultJson.put("hostbydata", hostbydata);
			result = resultJson.toString();
		}
		if(result==null) result = "{}";
		return result;
	}
	
	

	public static Boolean deteWidgetOption(String widgetid,String eventid){
		//System.out.println("widgetid"+widgetid);
		//System.out.println("eventid"+eventid);
		String query = "delete from  custom_widget_options where eventid=?::bigint and widgetid=? and stage='draft'";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,widgetid});
		if(statobj.getStatus())
			return true;
		else
			return false; 
		
	}
	
	public static Boolean saveWidgetOptions(String data,String eventid,String widgetid,String title, String ref_title)throws JSONException {
		System.out.println("saveWidgetOptions eventid:-"+eventid+", widgetid:-"+widgetid+", ref_title:-"+ref_title);
		// for rsvplist
		
		if("RSVPList".equals(widgetid)){
			JSONObject fbid = new JSONObject(data);
			String facebookId="";
			if(fbid.has("fbeventid")){
				facebookId = fbid.getString("fbeventid");
				updateConfigData(eventid,facebookId);
				System.out.println("adding rsvp widget, with fbid:: "+facebookId+" insert/update in buyer page also");
				String updateBuyerCustom = "update buyer_att_custom_widget_options set config_data=? where eventid=? and stage='draft'";
				StatusObj statobjBuyer = DbUtil.executeUpdateQuery(updateBuyerCustom, new String[]{data,eventid});
				if(statobjBuyer.getCount()==0){
					String insertBuyerCustom = "insert into buyer_att_custom_widget_options(eventid,widgetid,config_data,widget_title,widget_ref_title,created_at,updated_at,stage,layout_type) "+
									"select CAST(? AS BIGINT),widgetid,?,widget_title,widget_ref_title,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),'draft',layout_type "+
									"from buyer_att_def_widget_options where lang=? and widgetid='RSVPList'";
					StatusObj statobjBuyerInsert = DbUtil.executeUpdateQuery(insertBuyerCustom, new String[]{eventid,data,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),I18NCacheData.getI18NLanguage(eventid)});
				}
				
				
			}
		}
				
		String updateQry = "update custom_widget_options set config_data=?,widget_title=?,widget_ref_title=?,updated_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') "+
						"where eventid=?::bigint and widgetid=? and stage='draft'";
		//String deleteQry="delete from widget_options where eventid=?::bigint and widgetid=? and stage='draft'";
		String insertQry="insert into custom_widget_options (eventid,widgetid,config_data,widget_title,widget_ref_title,created_at,updated_at,stage)"+
						" values(CAST(? AS BIGINT),?,?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),'draft')";
		//DbUtil.executeUpdateQuery(deleteQry, new String[]{eventid,widgetid});
		
		StatusObj statobj = DbUtil.executeUpdateQuery(updateQry, new String[]{data,title,ref_title,DateUtil.getCurrDBFormatDate(),eventid,widgetid});
		if(statobj.getCount()==0)
			statobj=DbUtil.executeUpdateQuery(insertQry, new String []{eventid,widgetid,data,title,ref_title,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate()});
		
		updateSync(eventid, "no");
		if("whosAttending".equals(widgetid)){
			saveWhosAttendingData(eventid,data);
		}

		if(statobj.getStatus())
			return true;
		else
			return false;
	}

	private static void updateConfigData(String eid, String fbId){
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.FBRSVPList.eventid'",new String[]{eid});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.FBRSVPList.eventid",fbId.trim(),eid});
	}
	
	private static String InitHeaderTheme(String eventid) {
		String query = "select themedata from event_header_themes where themeid=0";
		String themedata = DbUtil.getVal(query, new String[]{});
		query = "update event_layout set header_theme = ? where eventid=?::bigint";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{themedata,eventid});
		if(statobj.getStatus()) {
			return themedata;
		}
		return "";
	}
	public static String getMyHeaderTheme(String eventid) {
		String query = "select header_theme from event_layout where eventid=?::bigint and stage='draft'";
		String themText=DbUtil.getVal(query, new String[]{eventid});
		if(themText==null || "".equals(themText)){
			query = "insert into event_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode) (select  ?::bigint,'draft','no',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode from event_layout where eventid='1'::bigint )";
			DbUtil.executeUpdateQuery(query, new String[]{eventid});
			query = "select header_theme from event_layout where eventid=?::bigint and stage='draft'";
			return DbUtil.getVal(query, new String[]{eventid});
		}
		else
			return DbUtil.getVal(query, new String[]{eventid});
	}
	public static Boolean saveHeaderTheme(String themedata,String eventid) {
		String query = "update event_layout set header_theme=? where eventid=?::bigint and stage='draft'";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{themedata,eventid});
		if(statobj.getStatus())
			return true;
		else
			return false;
	} 
	public static Boolean ApplyHeaderTheme(String themeid,String eventid){
		System.out.println("themeID: "+themeid);
		String isHeader = DbUtil.getVal("select 'Y' from event_layout where eventid=?::bigint", new String[]{eventid});
		if(!"Y".equals(isHeader)){
			String insertData = "insert into event_layout(eventid,stage,sync,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode) (select  ?::bigint,'draft','no',wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,header_theme,global_style,hide_widgets,themecode from event_layout where eventid='1'::bigint )";
			DbUtil.executeUpdateQuery(insertData, new String[]{eventid});
		}
		
		String query = "update event_layout set header_theme=(select themedata from event_header_themes where themeid=?::bigint) where eventid=?::bigint";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{themeid,eventid});
		if(statobj.getStatus())
			return true;
		else
			return false;
	}
	
	public static JSONObject BrowseThemes() throws JSONException {
		//String query = "select thumbnail,description,name,categories,date_created,themeid,ownerid from event_header_themes where themeid!=0 and status='approved'";
		String query = "select thumbnail,description,name,categories,date_created,themeid,ownerid from event_header_themes where status='approved' order by themeid";
		DBManager dbm = new DBManager();
		StatusObj s = dbm.executeSelectQuery(query, new String[]{});
		JSONArray themesarray = new JSONArray();
		JSONObject theme, result = new JSONObject();
		if(s.getStatus()) {
			result.put("total", s.getCount());
			for(int i=0;i<s.getCount();i++) {
				theme = new JSONObject();
				theme.put("name", dbm.getValue(i, "name", ""));
				theme.put("description", dbm.getValue(i, "description", ""));
				theme.put("themeid", dbm.getValue(i, "themeid", ""));
				theme.put("thumbnail", dbm.getValue(i, "thumbnail", ""));
				themesarray.put(theme);
			}
			result.put("themes", themesarray);
		}
		return result;
	}
	
	public static String currentThemeName(String eventid){
		String curTheme = DbUtil.getVal("select a.name from event_header_themes a, event_layout b where a.themedata=b.header_theme and b.stage='draft' and b.eventid=?::bigint", new String[]{eventid});
		if(curTheme==null || "".equals(curTheme))
			return "Default";
		else
		return curTheme;
	}
		public static HashMap<String, String> getEventinfo(String eventid){
		DBManager dbmanager=new DBManager();
		
		HashMap <String,String>hmap=new HashMap<String,String>();
		String query="select trim(to_char(('0001-01-01'||' '|| starttime):: timestamp, 'HH12:MI')) as startTime,trim(to_char(('0001-01-01'||' '|| starttime):: timestamp, 'AM')) as startAMPM,trim(to_char(('0001-01-01'||' '|| endtime):: timestamp, 'AM')) as endAMPM,trim(to_char(a.start_date, 'Dy'))as startDay, trim(to_char(a.start_date, 'Mon')) as startMon,trim(to_char(a.start_date, 'DD')) as startDate, trim(to_char(a.start_date, 'YYYY')) as startYear ," +
				" trim(to_char(('0001-01-01'||' '|| endtime):: timestamp, 'HH12:MI')) as endTime,trim(to_char(a.end_date, 'Dy'))as endDay, trim(to_char(a.end_date, 'Mon')) as endMon,trim(to_char(a.end_date, 'DD')) as endDate, trim(to_char(a.end_date, 'YYYY')) as endYear  " +
				" ,*  from eventinfo a where eventid=?::bigint";
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{eventid});
		try{
			if(statobj.getStatus()){
				String [] columnnames=dbmanager.getColumnNames();
				for(int j=0;j<columnnames.length;j++){
					hmap.put(columnnames[j],dbmanager.getValue(0,columnnames[j],""));
				}
			}
		}
		catch(Exception e){
			System.out.println("Exception in getRegistrationData"+e.getMessage());
		}
		//System.out.println("hmap::"+hmap);
		return hmap;
		
		
	}
		

		public static String getWhosAttendingData(String eventid) throws Exception{
			ArrayList totalList=new ArrayList();
			ArrayList questions=new ArrayList();
			//ArrayList attendeeQuestions=new ArrayList();
			questions=getAttributes(eventid,"EVENT");
			//attendeeQuestions = CustomAttributesDB.getAttendeeAttributes(eventid);
			System.out.println("the questions ::"+questions);
			JSONObject totalJson=new JSONObject();
			JSONObject notselected=new JSONObject();
			JSONObject selected=new JSONObject();
			
			/*ArrayList tempList=new ArrayList();
			for(int i=0;i<attendeeQuestions.size();i++){
				Entity e1=(Entity)attendeeQuestions.get(i);
				tempList.add(e1.getKey());
				totalList.add(e1.getValue());
				selected.put(e1.getKey(),e1.getValue());
			}*/
			
			
			for(int i=0;i<questions.size();i++){
				Entity e1=(Entity)questions.get(i);
				totalList.add(e1.getValue());
				notselected.put(e1.getKey(),e1.getValue());
			}
			
			
			
			totalJson.put("notselected",notselected);
			totalJson.put("selected",selected);
			totalJson.put("totallist",totalList);
			System.out.println("total json object is::"+totalJson);
			return totalJson.toString();
		}
		
		public static void saveWhosAttendingData(String eid,String jsondata){
			ArrayList<Entity>arrlist=new ArrayList<Entity>();
			System.out.println("the json data is::"+jsondata);
			try{
			JSONObject jsonObj=new JSONObject(jsondata);
			JSONArray orderarr=jsonObj.getJSONArray("positions");
			System.out.println("orderarr::"+orderarr.getString(0));
			for(int i=0;i<orderarr.length();i++){
				String attribid=jsonObj.getString(orderarr.getString(i));
				arrlist.add(new Entity(attribid,orderarr.getString(i)));
			}
			CustomAttributesDB cdb=new CustomAttributesDB();
			cdb.saveAttendeeAttribs(eid,arrlist,"EVENT");
			
			}catch(Exception e){
				
			}
			
		}
		

		public static String getFBAppId(String name){
			String fbappid=DbUtil.getVal("select value from config where config_id='0' and name=?",new String[]{name});
			return fbappid;
		}
		

		
		

public static HashMap<String, String> getTitles(String eventid,String stage) {
	
		 //HashMap< String ,String> titles=new HashMap<String, String>();
		 HashMap<String,String> defWidTitMap=getDefWidgetTitles(I18NCacheData.getI18NLanguage(eventid));
		 String cusQry="select widget_ref_title,widget_title,widgetid from custom_widget_options where eventid=?::bigint and stage=?";
	     /*String query = "select widget_ref_title,widget_title,widgetid from widget_options where (eventid=?::bigint or eventid='0'::bigint) and (stage='draft' or  stage='init') order by eventid";
		if("final".equals(stage))
			   query = "select widget_ref_title,widget_title,widgetid from widget_options where (eventid=?::bigint or eventid='0'::bigint) and (stage='final' or  stage='init') order by eventid";
*/		
	     DBManager db=new DBManager();
			StatusObj stb=db.executeSelectQuery(cusQry, new String[]{eventid,stage});
			if(stb.getStatus() && stb.getCount()>0){
				for(int i=0;i<stb.getCount();i++){
					String widgetid=db.getValue(i, "widgetid", "");
					if(defWidTitMap.containsKey(widgetid)){
						defWidTitMap.remove(widgetid);
						defWidTitMap.remove(widgetid+"_ref");
					}
					defWidTitMap.put(db.getValue(i, "widgetid", ""), db.getValue(i, "widget_title", ""));
					defWidTitMap.put(db.getValue(i, "widgetid", "")+"_ref", db.getValue(i, "widget_ref_title", ""));
				}
		  }
			return defWidTitMap;
		}

public static HashMap<String,String> getDefWidgetTitles(String lang){
	HashMap<String,String> defWidgetTitMap=new HashMap<String,String>();
	String defWidgetTitQry="select widget_ref_title,widget_title,widgetid from default_widget_options where lang=? and stage='init'";
	DBManager db=new DBManager();
	StatusObj statobj=null;
	statobj=db.executeSelectQuery(defWidgetTitQry,new String []{lang});
	int count=statobj.getCount();
	if(statobj.getStatus() && count>0){
		for(int i=0;i<count;i++){
		    defWidgetTitMap.put(db.getValue(i, "widgetid", ""), db.getValue(i, "widget_title", ""));
		    defWidgetTitMap.put(db.getValue(i, "widgetid", "")+"_ref", db.getValue(i, "widget_ref_title", ""));
		}
	}
	return defWidgetTitMap;
}



public static String getTitle(String eventid,String widgetid) {
	String title = "";
	title = DbUtil.getVal("select widget_title from custom_widget_options where eventid=?::bigint and stage='draft' and widgetid=?", new String[]{eventid,widgetid});
	if(title == null || "".equals(title))
		title = DbUtil.getVal("select widget_title from default_widget_options where stage='init' and widgetid=? and lang=?", new String[]{widgetid,I18NCacheData.getI18NLanguage(eventid)});
	title=title==null?"":title;
	return title;
	
	 /*String query = "select widget_title from widget_options where widgetid=? and (eventid=?::bigint or eventid='0'::bigint) and (stage='draft' or  stage='init')";
	 String title =DbUtil.getVal(query, new String[]{widgetid,eventid});
	 title=title==null?"":title;
		return title;*/
	}



 public static void saveTitle(String eventid,JSONObject titles) {
    try{					
		 Iterator keys=titles.keys();
	     while(keys.hasNext()){
	  
		 String widget=(String)keys.next();
	     String title=(String)titles.get(widget);
	     
	     saveRefTitle(eventid,title,widget);
		 }				
      }catch(Exception e){System.out.println("default saving  titles");	}			  
}
 
 public  static boolean saveRefTitle(String eventid,String title,String widgetid) {
		try{
		  	 
	String query   = "update custom_widget_options set widget_ref_title=?,updated_at='now()' where eventid=?::bigint and widgetid=? and stage='draft'";
	StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{title,eventid,widgetid});
		if(statobj.getStatus() && statobj.getCount()>0)
			return true;
			else{
				 query= "insert into custom_widget_options(eventid,widgetid,widget_ref_title,config_data,stage) values(?::bigint,?,?,?,'draft')";
				statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,widgetid,title,""});
				String yes=DbUtil.getVal("select 'yes' from custom_widget_options where eventid=?::bigint and widgetid=? and stage='final'", new String[]{eventid,widgetid});
				if(!"yes".equals(yes)){
				query= "insert into custom_widget_options(eventid,widgetid,widget_ref_title,config_data,stage) values(?::bigint,?,?,?,'final')";
				statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,widgetid,title,""});
				}
				if(statobj.getStatus())
					return true;
				else
					return false;
			}
						
		}catch(Exception e){System.out.println(" exception while savingsaving  titles");return false;}					
	 }

 
 public static String getRefTitle(String eventid,String widgetid) {
	 String refTitle="";
	 refTitle = DbUtil.getVal("select widget_ref_title from custom_widget_options where eventid=?::bigint and widgetid=? and stage='draft'", new String[]{eventid,widgetid});
	 if(refTitle==null || "".equals(refTitle))
		 refTitle = DbUtil.getVal("select widget_ref_title from default_widget_options where lang=? and widgetid=?", new String[]{I18NCacheData.getI18NLanguage(eventid),widgetid});
	 refTitle=refTitle==null?"":refTitle;
	 return refTitle;
	}
 
 public  static boolean saveTitle(String eventid,String title,String widgetid) {
		try{
		  	
	String query   = "update custom_widget_options set widget_title=?,updated_at='now()' where eventid=?::bigint and widgetid=? and stage='draft'";
	StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{title,eventid,widgetid});
	updateSync(eventid, "no");
		if(statobj.getStatus() && statobj.getCount()>0)
			return true;
			else{
				 query= "insert into custom_widget_options(eventid,widgetid,widget_title,config_data,stage) values(?::bigint,?,?,?,'draft')";
				statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,widgetid,title,""});
				String yes=DbUtil.getVal("select 'yes' from custom_widget_options where eventid=?::bigint and widgetid=? and stage='final'", new String[]{eventid,widgetid});
				if(!"yes".equals(yes)){				
				 query= "insert into custom_widget_options(eventid,widgetid,widget_title,config_data,stage) values(?::bigint,?,?,?,'final')";
				 statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,widgetid,title,""});
				}
				if(statobj.getStatus())
					return true;
				else
					return false;
			}
						
		}catch(Exception e){System.out.println(" exception while savingsaving  titles");return false;}					
	 }







		public static HashMap<String,String> getConfigValuesFromDb(String eventid){
			String ConfigQuery="select * from config where config_id=(select config_id from eventinfo where eventid=CAST(? as INTEGER))";
			DBManager db=new DBManager();
			HashMap<String,String> confighm=new HashMap<String,String>();
			StatusObj sb=db.executeSelectQuery(ConfigQuery,new String[]{eventid});
			if(sb.getStatus()){
				for(int i=0;i<sb.getCount();i++){
					confighm.put(db.getValue(i,"name",""),db.getValue(i,"value",""));
				}
			}
			return confighm;
		}
		
		public static HashMap<String,String> getVenueCssMsgMap(String venueid){
			HashMap<String,String> venuecssmap=new HashMap<String,String>();
			String venuecss="",notavail="",notavailmsg="",unassign="",unassignmsg="";
			if(!"".equals(venueid) && venueid!=null)
			venuecss=DbUtil.getVal("select layout_css from venue_sections where venue_id=CAST(? AS INTEGER)",new String[]{venueid});
			venuecssmap.put("venuecss", venuecss);
			DBManager dbm=new DBManager();
			StatusObj statobj=null;
			String venuecssqry="select * from venue_seating_images where venue_id=CAST(? AS BIGINT) and context in('notavailable','unassign')";
			if(!"".equals(venueid) && venueid!=null)
			statobj=dbm.executeSelectQuery(venuecssqry, new String[]{venueid});
			if(statobj.getStatus() && statobj.getCount()>0){
				for(int i=0;i<statobj.getCount();i++){
					if("notavailable".equals(dbm.getValue(i,"context",""))){
						venuecssmap.put("notavailimg", dbm.getValue(i,"image",""));
						venuecssmap.put("notavailmsg", dbm.getValue(i,"message",""));
					}
					if("unassign".equals(dbm.getValue(i,"context",""))){
						venuecssmap.put("unassignimg", dbm.getValue(i,"image",""));
						venuecssmap.put("unassignmsg", dbm.getValue(i,"message",""));
					}
				}
			}
			
			return venuecssmap;
		}
		
		public static String getRecurringEventDates(String eventid,String purpose){
			String query="select  to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY HH12:MI AM') as evt_start_date"
		      +",to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY') as evt_start_date1 from event_dates where eventid=cast(? as numeric) and (zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS') as text) as time ))>=current_date order by (zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS') as text) as time ))";
			  String showTimePart=DbUtil.getVal("select value from mgr_config c,eventinfo e where e.eventid=?::BIGINT and c.name='mgr.recurring.showtime' and e.mgr_id=c.userid",new String[]{eventid});		  
				if(showTimePart==null || "".equals(showTimePart)) showTimePart="Y";
				ArrayList al=new ArrayList();
				DBManager db=new DBManager();
				String str=null;
				StatusObj stob=db.executeSelectQuery(query,new String[]{eventid} );
				if(stob.getStatus()){
					for(int i=0;i<stob.getCount();i++){
					HashMap<String,String> hm=new HashMap<String,String>();
						if("N".equals(showTimePart))
						hm.put("display",db.getValue(i,"evt_start_date1",""));
						//al.add(db.getValue(i,"evt_start_date1",""));
						else
						hm.put("display",db.getValue(i,"evt_start_date",""));
						//al.add(db.getValue(i,"evt_start_date",""));
						hm.put("value",db.getValue(i,"evt_start_date",""));
						al.add(hm);
					}
				}
				return getRecurringDatesForEventTickets(al,purpose,eventid);
			}
		
		static String getRecurringDatesForEventTickets(ArrayList al,String purpose,String eventid){
			String str=null;
			String checkrsvp=null;
			if(al!=null&&al.size()>0){
				if("tickets".equals(purpose))
					str="<select name='eventdate' id='eventdate'  onchange=getTicketsJsonBefore('"+eventid+"');>";
					
				else{
					checkrsvp=DbUtil.getVal("Select value from config where name='event.rsvp.enabled' and config_id in (select config_id from eventinfo where eventid=?::bigint)",new String[]{eventid});
					if("yes".equals(checkrsvp)){
						String get_key=null,get_value=null;
						//String Rsvp_RECURRING_EVEBT_DATES = "select distinct eventdate as date_display from event_reg_transactions where eventid=? and eventdate!='' and tid in (select tid from rsvp_transactions where eventid=? and responsetype='Y') order by date_display";
						String Rsvp_RECURRING_EVEBT_DATES  = "select date_display from event_dates where eventid=CAST(? AS BIGINT) and (zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS') as text) as time ))>=current_date order by date_key";

						DBManager dbmanager = new DBManager();
						StatusObj statobj = dbmanager.executeSelectQuery(Rsvp_RECURRING_EVEBT_DATES, new String[]{eventid});
						int rsvpcount = statobj.getCount();
						str="<select name='event_date' id='event_date' onchange=showRSVPAttendeesList('"+eventid+"');>";
						str=str+"<option value='Select Date'>--Select Date--</option>";
						if (statobj.getStatus() && rsvpcount > 0) {
						str="<select name='event_date' id='event_date' onchange=showRSVPAttendeesList('"+eventid+"');>";
						str=str+"<option value='Select Date'>--Select Date--</option>";
							for (int k = 0; k < rsvpcount; k++) {
								get_value = dbmanager.getValue(k, "date_display", "");
								
								str=str+"<option value='"+get_value+"'>"+get_value+"</option>";
							}
						}	
					}
					else{
						str="<select name='event_date' id='event_date' onchange=showAttendeesList('"+eventid+"');>";
					}
				}
				if(!"yes".equals(checkrsvp)){
					//HashMap hm=recurring_display(eventid);
					for(int i=0;i<al.size();i++){
						HashMap<String,String> hm1=(HashMap<String,String>)al.get(i);
						str=str+"<option value='"+hm1.get("value")+"'>"+hm1.get("display")+"</option>";
						//str=str+"<option value='"+(String)al.get(i)+"'>"+(String)al.get(i)+"</option>";
						//str=str+"<option value='"+(String)al.get(i)+"'>"+(String)hm.get(i)+"</option>";
					}
				}
				str=str+"</select>";

			}
			return str;

		}
		
		public static String getRecurringDatesLabel(String eid){
			ResourceBundle resourceBundle =I18n.getResourceBundle();
			String recurdateslabel="";
			HashMap<String,String> disAttribsForKeys=new HashMap<String,String>();
			String lang="en_US";
			if(!"".equals(eid) && eid !=null)
				lang=I18NCacheData.getI18NLanguage(eid);
			if(!"".equals(eid) && eid!=null){
				 disAttribsForKeys=DisplayAttribsDB.getAttribValuesForKeys(eid, "RegFlowWordings", lang, new String []{"event.reg.recurringdates.label"});
				 /*recurdateslabel=DbUtil.getVal("select attrib_value from custom_event_display_attribs where  module='RegFlowWordings' and attrib_name='event.reg.recurringdates.label' and eventid=CAST(? as BIGINT)", new String[]{eid});
				 if(recurdateslabel==null || "".equals(recurdateslabel))
					recurdateslabel=DbUtil.getVal("select attribdefaultvalue from default_event_display_attribs where  module='RegFlowWordings' and attribname='event.reg.recurringdates.label' and lang=?",new String[]{I18NCacheData.getI18NLanguage(eid)});*/
				}
			else{
				disAttribsForKeys=DisplayAttribsDB.getDefaultAttribValuesForKeys("RegFlowWordings", lang, new String []{"event.reg.recurringdates.label"});
			}
			recurdateslabel=disAttribsForKeys.get("event.reg.recurringdates.label");
			if(recurdateslabel==null || "".equals(recurdateslabel)) recurdateslabel=resourceBundle.getString("aa.select.date.time.lbl");
			return recurdateslabel;
		}
		
		public static String getCustomCSS(String eid){
			String customcss="";
			DBManager dbm=new DBManager();
			StatusObj statobj=null;
			String customcssqry="select themetype,themecode from user_roller_themes where upper(module)='EVENT' and refid=?";
			statobj=dbm.executeSelectQuery(customcssqry, new String[]{eid});
			String themeType=dbm.getValue(0, "themetype", "");
			String themeCode=dbm.getValue(0, "themecode", "");
			String query="";
			String[] params=null;
			if("CUSTOM".equalsIgnoreCase(themeType))
				customcss=DbUtil.getVal("select cssurl  from user_custom_roller_themes where refid=? ", new String[]{eid});
			else if("DEFAULT".equalsIgnoreCase(themeType))
				customcss=DbUtil.getVal("select cssurl from ebee_roller_def_themes where upper(module)='EVENT'" +
						"and themecode=?", new String[]{themeCode});
			else if("PERSONAL".equalsIgnoreCase(themeType))
			   customcss=DbUtil.getVal("select cssurl from user_customized_themes where upper(module)='EVENT'" +
				"and themeid=?", new String[]{themeCode});
			else
				customcss=DbUtil.getVal("select cssurl  from ebee_roller_def_themes where upper(module)='EVENT'" +
					"and themecode=?", new String[]{"basic"});
			
		   return customcss;
		}
		
		public static HashMap<String,String> getTrackUrlDetails(String trackcode,String eid){
			HashMap<String,String> detailsMap=new HashMap<String,String>();
			String query="select photo,message from trackurls where trackingcode=? and eventid=?";
			DBManager dbm=new DBManager();
			StatusObj sbj=null;
			sbj=dbm.executeSelectQuery(query,new String[]{trackcode,eid});
			if(sbj.getStatus() && sbj.getCount()>0){
				detailsMap.put("photo",dbm.getValue(0,"photo",""));
				detailsMap.put("message",dbm.getValue(0,"message",""));
			}
		return detailsMap;
		}
		
		public static String getWidgetType(String widgetid,HashMap<String,String> configMap) throws Exception{
			ArrayList<String> arrlist=new ArrayList<String>(Arrays.asList("single","wide","narrow","singlebottom"));
			String widgetData=configMap.get("widgets");
				if(widgetData==null)
					return "none";
		JSONObject widgetjson=new JSONObject(widgetData);      
		System.out.println("the widget json is::"+widgetjson);
		for(int i=0;i<widgetjson.length();i++){
			JSONArray jsonarr=(JSONArray)widgetjson.get(arrlist.get(i));
			if(jsonarr.length()==0)continue;
			for(int j=0;j<jsonarr.length();j++){
				JSONObject jsonObj=jsonarr.getJSONObject(j);
				if(jsonObj.has(widgetid))
				return arrlist.get(i);
			}
		}
			return "none";
		}
		
		
		public static HashMap<String, String> getGlobalTemplates(){
			System.out.println("In DBHelpers.java getGlobalTemplates");
			HashMap<String, String> hm = new HashMap<String, String>();
			
			String query="select template_key,template_value from layout_templates where refid='0'";
			DBManager dbm=new DBManager();
			StatusObj statobj=null;
			statobj=dbm.executeSelectQuery(query, null);
			if (statobj.getStatus() && statobj.getCount() > 0) {
				for(int i=0;i<statobj.getCount();i++){
					String key=dbm.getValue(i, "template_key", "");
					String value=dbm.getValue(i, "template_value", "");
					hm.put(key, value);
				}
			}
			return hm;
		}
		
		private static void updateSync(String eventid,String sync){
			String query = "update event_layout set sync=? where eventid=?::bigint and stage='draft'";
			StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{sync,eventid});
		
			
		}
		
		public static ArrayList<Entity> getAttributes(String eid,String type){		
			String RESPONSE_QUERY_FOR_ATTRIBUTE="select a.attrib_id,a.attribname as attrib_name from " +
					"custom_attribs a,custom_attrib_master b where a.attrib_setid=b.attrib_setid and " +
					"b.groupid = CAST(? AS BIGINT) and b.purpose =?  and a.attrib_id " +
					"not in (select attribid from buyer_custom_questions where eventid=CAST(? AS BIGINT))";
			
			ArrayList<Entity> attribsList=new ArrayList<Entity>();
			
			DBManager dbmanager=new DBManager();
			StatusObj statobj=null;
			
		    String defaultquestionsquery="select distinct(attribid) from base_profile_questions where contextid!=0 and attribid "+
	        " in('email','phone') and groupid=CAST(? AS BIGINT) order by attribid";
	        
		    String defattendeelistquery="select attrib_id from attendeelist_attributes where eventid=CAST(? AS BIGINT)  and  attrib_id in(-1,-2) order by attrib_id desc";
			
	        
	   /*   HashMap<String,String> hmap=new HashMap<String,String>();
	      statobj=dbmanager.executeSelectQuery(defattendeelistquery, new String[]{eid});
			if(statobj.getStatus() && statobj.getCount()>0){
				for(int i=0;i<statobj.getCount();i++){
					hmap.put(dbmanager.getValue(i, "attrib_id",""), dbmanager.getValue(i, "attrib_id",""));
				}
			}*/
		    attribsList.add(new Entity("0","Attendee Name"));
			statobj=dbmanager.executeSelectQuery(defaultquestionsquery, new String[]{eid});
			if(statobj.getStatus() && statobj.getCount()==1){
				if("email".equals(dbmanager.getValue(0,"attribid",""))){
				    	attribsList.add(new Entity("-1","Email"));
				}else if("phone".equals(dbmanager.getValue(0,"attribid",""))){
						attribsList.add(new Entity("-2","Phone"));	
				}
			}	
			else if(statobj.getStatus() && statobj.getCount()==2){
					attribsList.add(new Entity("-1","Email"));
					attribsList.add(new Entity("-2","Phone"));
			}
			
			statobj=dbmanager.executeSelectQuery(RESPONSE_QUERY_FOR_ATTRIBUTE,new String [] {eid,type,eid});
			if(statobj.getStatus()){
				for(int k=0;k<statobj.getCount();k++){
					attribsList.add(new Entity(dbmanager.getValue(k,"attrib_id",""),dbmanager.getValue(k,"attrib_name","")));
				}
			}
			return attribsList;
		}
		
		
		public static void updateDraftWithFinal(String eventid){//will call 1. on pagedesign onload and 2. click on preview while not in pagedesign
			System.out.println("updateDraftOnLoad eventid: "+eventid);
			String query = "update event_layout set wide_widgets=aa.wide_widgets,narrow_widgets=aa.narrow_widgets,single_widgets=aa.single_widgets, " +
					"single_bottom_widgets=aa.single_bottom_widgets,hide_widgets=aa.hide_widgets,updated_at='now()',global_style=aa.global_style, " +
					"themecode=aa.themecode,header_theme=aa.header_theme,sync='yes' from (select wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,hide_widgets,global_style, " +
					"themecode,header_theme from event_layout  where stage='final' and eventid=?::bigint) aa where stage='draft' and eventid=?::bigint";
			
			StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,eventid});
			
			/*if(statobj.getCount()==0){
				
				query="insert into event_layout (eventid,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,hide_widgets,global_style, " +
						"themecode,header_theme,sync,stage,updated_at,created_at) select CAST(? AS BIGINT),wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets, " +
						"hide_widgets,global_style,themecode,header_theme,'yes','draft',to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') " +
						"from event_layout where eventid=CAST(? AS BIGINT) and stage='final'";
				
				statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),eventid});
			}*/
			
			query="delete from custom_widget_options where eventid=?::bigint and stage='draft'";
			statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid});
			 
			query ="insert into custom_widget_options (eventid,widgetid,config_data,widget_title,widget_ref_title,updated_at,stage) " +
					"select eventid,widgetid,config_data,widget_title,widget_ref_title,now(),'draft' " +
					"from custom_widget_options where stage='final' and eventid=?::bigint ";
			statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid});

		}
		
}

