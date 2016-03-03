package com.eventbee.buyer.att.layout;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.event.dbhelpers.CustomAttributesDB;
import com.event.dbhelpers.EventDB;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.I18NCacheData;
import com.eventbee.general.StatusObj;
import com.eventbee.layout.LayoutManager;

public class BuyerAttHelper {
	public static String getLayout(String eventid,String ref,String stage,String action){
		try{
			Boolean isRecurring=EventDB.isRecurringEvent(eventid);
			DBManager dbm = new DBManager();
			StatusObj statobj;
			HashMap<String, String> widgettitles=getTitles(eventid,stage,action);
			String query = "select * from buyer_att_page_layout where eventid = ?::bigint and stage = 'draft' and layout_type=?";
			if("final".equals(stage))
				query = "select * from buyer_att_page_layout where eventid = ?::bigint and stage = 'final' and layout_type=?";
			statobj = dbm.executeSelectQuery(query, new String[]{eventid,action});
			JSONObject result = null;
			if(statobj.getStatus() && statobj.getCount()>0 ) {
				result=fillLayout(eventid,ref,dbm,widgettitles,isRecurring);
				
			}else{
				String insertQuery = "insert into buyer_att_page_layout(eventid,wide_widgets,narrow_widgets,single_widgets,stage,global_style,layout_type,single_bottom_widgets,created_at,hide_widgets,sync,header_theme)"+
						"select ?,wide_widgets,narrow_widgets,single_widgets,'draft',global_style,?,single_bottom_widgets,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),hide_widgets,'no',header_theme from buyer_att_page_layout where eventid='1' and stage='final' and layout_type=?";
				statobj = DbUtil.executeUpdateQuery(insertQuery, new String[]{eventid,action,DateUtil.getCurrDBFormatDate(),action});
				System.out.println(insertQuery);
				String	defaultQuery = "select * from buyer_att_page_layout where eventid = '1'::bigint and stage = 'final' and layout_type=?";
				statobj = dbm.executeSelectQuery(defaultQuery, new String[]{action});
				result=fillLayout(eventid,ref,dbm,widgettitles,isRecurring);
				
			}
			if("buyer".equals(action))
				result.put("layout_type","buyer");
			if("attendee".equals(action))
				result.put("layout_type", "attendee");
			
			return result.toString();
		}catch(Exception e){
			System.out.println("Exception at getLayout");
		}
		return "";
		
	}
	
	public static HashMap<String, String> getTitles(String eventid,String stage,String action) {
		HashMap<String,String> defWidTitMap=getDefWidgetTitles(I18NCacheData.getI18NLanguage(eventid),action);
		String cusQry="select widget_ref_title,widget_title,widgetid from buyer_att_custom_widget_options where eventid=?::bigint and stage=? and layout_type=?";
		DBManager db=new DBManager();
		StatusObj stb=db.executeSelectQuery(cusQry, new String[]{eventid,stage,action});
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
	public static HashMap<String,String> getDefWidgetTitles(String lang, String action){
		HashMap<String,String> defWidgetTitMap=new HashMap<String,String>();
		String defWidgetTitQry="select widget_ref_title,widget_title,widgetid from buyer_att_def_widget_options where lang=? and stage='init' and layout_type=?";
		DBManager db=new DBManager();
		StatusObj statobj=null;
		statobj=db.executeSelectQuery(defWidgetTitQry,new String []{lang,action});
		int count=statobj.getCount();
		if(statobj.getStatus() && count>0){
			for(int i=0;i<count;i++){
				defWidgetTitMap.put(db.getValue(i, "widgetid", ""), db.getValue(i, "widget_title", ""));
				defWidgetTitMap.put(db.getValue(i, "widgetid", "")+"_ref", db.getValue(i, "widget_ref_title", ""));
			}
		}
		return defWidgetTitMap;
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
		result.put("global_style", new JSONObject(dbm.getValue(0, "global_style", "")));
		result.put("themeCodeName", dbm.getValue(0, "themecode", ""));
		JSONObject addedBuyer = LayoutManager.concatArrays(single,wide,narrow,single_bottom);
		result.put("addedBuyer",addedBuyer);
		}catch(Exception e){
			System.out.println("Exception in DBHelper.java fillLayout() ERROR:: "+e.getMessage());
		}
		return result;
	}
	private static Boolean checkHideStatus(String widgetid,String ref,JSONObject hides){
		if("".equals(ref)){
			if( hides.has(widgetid))
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	public static Boolean saveLayout(String jsonData) throws Exception {
		
		JSONObject data = new JSONObject(jsonData);
		JSONObject titlesjson;
		String layout_type="";
		if(data.has("layout_type"))
			layout_type =data.getString("layout_type");
		
		String wideWidgets="",narrowWidgets="",singleWidgets="",next,singlBottomWidgets="";
		if(data.has("titles"))
		{	titlesjson=data.getJSONObject("titles");
		    // saveTitle(data.getString("eventid"), titlesjson);
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
		   }catch(Exception e){System.out.println("single-bottom coulmn parsing"+e.getMessage());}
		  	if(!"".equals(wideWidgets))wideWidgets = wideWidgets.substring(0, wideWidgets.length()-1);
			if(!"".equals(narrowWidgets))narrowWidgets = narrowWidgets.substring(0, narrowWidgets.length()-1);
			if(!"".equals(singleWidgets))singleWidgets = singleWidgets.substring(0, singleWidgets.length()-1);
			if(!"".equals(singlBottomWidgets))singlBottomWidgets = singlBottomWidgets.substring(0, singlBottomWidgets.length()-1);
		String query = "update buyer_att_page_layout set wide_widgets=?,narrow_widgets=?,single_widgets=?,single_bottom_widgets=?,updated_at='now()',sync='no' where stage=? and eventid=?::bigint and layout_type=?";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{wideWidgets,narrowWidgets,singleWidgets,singlBottomWidgets,"draft",data.getString("eventid"),layout_type});
		if(statobj.getStatus() && statobj.getCount()>0 && "draft".equals(data.getString("stage"))){
			return true;
		}else if(statobj.getStatus() && statobj.getCount()>0 && "final".equals(data.getString("stage"))){
			
			query = "update buyer_att_page_layout set wide_widgets=aa.wide_widgets,narrow_widgets=aa.narrow_widgets,"+
			"single_widgets=aa.single_widgets,single_bottom_widgets=aa.single_bottom_widgets,hide_widgets=aa.hide_widgets,"+
			"updated_at='now()',global_style=aa.global_style,sync='yes',layout_type=aa.layout_type from "+
			"(select wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,hide_widgets,global_style,layout_type "+
			"from buyer_att_page_layout where stage='draft' and eventid=?::bigint and layout_type=?) aa where stage='final' and eventid=?::bigint";
			
			statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid"),layout_type,data.getString("eventid")});
			
			if(statobj.getCount()==0){
				
				query="insert into buyer_att_page_layout (eventid,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,hide_widgets,global_style" +
						",sync,stage,updated_at,created_at,layout_type,header_theme) select CAST(? AS BIGINT),wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets, " +
						"hide_widgets,global_style,'yes','final',to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),layout_type,header_theme " +
						"from buyer_att_page_layout where eventid=CAST(? AS BIGINT) and stage='draft' and layout_type=?";
				
				statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid"),DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),data.getString("eventid"),layout_type});
			}
			 if(statobj.getStatus() && statobj.getCount()>0){ 
				 updateSync(data.getString("eventid"),"yes",layout_type);
				
				 query="delete  from buyer_att_custom_widget_options where eventid=?::bigint and stage='final' and layout_type=?";
				 statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid"),layout_type});
				 
				 query = "insert into  buyer_att_custom_widget_options (eventid,widgetid,config_data,widget_title,widget_ref_title,updated_at,stage,layout_type) select eventid,widgetid,config_data,widget_title,widget_ref_title,now(),'final',layout_type from buyer_att_custom_widget_options  where stage='draft' and eventid=?::bigint and layout_type=?";
				 statobj = DbUtil.executeUpdateQuery(query, new String[]{data.getString("eventid"),layout_type});
			   if(statobj.getStatus() ){
			    return true;
			   }
			   else				 
			   return false;
			 }
			 else
				 return false;			 
		 }else
			 return false;
	}
	public static String getWidgetOptions(String eventid,String widgetid,String layout_type) throws JSONException {
		return getWidgetOptions(eventid,widgetid,"draft",layout_type);
	}
	public static String getWidgetOptions(String eventid,String widgetid,String stage,String layout_type) throws JSONException {
		String query = "select config_data from buyer_att_custom_widget_options where eventid=?::bigint and widgetid=? and stage=? and layout_type=?";
		String result = DbUtil.getVal(query, new String[]{eventid,widgetid,stage,layout_type});
		//String result = "";
		if(result==null) {
			String defaultwidgetid = widgetid;
			if(defaultwidgetid.contains("_"))
				defaultwidgetid = defaultwidgetid.split("_")[0];
			query="select config_data from buyer_att_def_widget_options where lang=? and widgetid=? and layout_type=?";
			result=DbUtil.getVal(query, new String[]{I18NCacheData.getI18NLanguage(eventid),defaultwidgetid,layout_type});
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
	
	public static String getTitle(String eventid,String widgetid,String layout_type) {
		String title = "";
		title = DbUtil.getVal("select widget_title from buyer_att_custom_widget_options where eventid=?::bigint and stage='draft' and widgetid=? and layout_type=?", new String[]{eventid,widgetid,layout_type});
		if(title == null || "".equals(title))
			title = DbUtil.getVal("select widget_title from buyer_att_def_widget_options where stage='init' and widgetid=? and lang=? and layout_type=?", new String[]{widgetid,I18NCacheData.getI18NLanguage(eventid),layout_type});
		title=title==null?"":title;
		return title;
		}
	
	public static Boolean saveWidgetOptions(String data,String eventid,String widgetid,String title, String ref_title,String layout_type)throws JSONException {
		System.out.println("saveWidgetOptionsBuyerAtt eventid:-"+eventid+", widgetid:-"+widgetid+", ref_title:-"+ref_title+"layout_type"+layout_type);

		if("files".equals(widgetid)){
			String configdata=DbUtil.getVal("select config_data from buyer_att_custom_widget_options where eventid=CAST(? AS BIGINT) and widgetid='files' and stage='draft' and layout_type='buyer'" , new String[]{eventid});
			//System.out.println("my data"+configdata);
			if("".equals(configdata) || configdata==null)
			{
				//System.out.println("we are in zero");
				JSONObject	jsonData = new JSONObject();
				JSONArray obj=new JSONArray();
				JSONObject	eachobj = new JSONObject(data);
				obj.put(eachobj);
				jsonData.put("files", obj);
				data=jsonData.toString();
				//System.out.println(data+"::final");
			}
			else
			{//System.out.println("we are appending");
				JSONObject	 jsonobj = new JSONObject();
				JSONArray obj=new JSONArray();
				try{
					JSONObject	jsonData = new JSONObject(configdata);
					obj=(JSONArray)jsonData.get("files");
					JSONObject	eachobj = new JSONObject(data);
					obj.put(eachobj);
					jsonobj.put("files",obj);
					data=jsonobj.toString();
				}
				catch(Exception e)
				{		
					System.out.println("Exception at file widget save"+e);

				}
			}
		}

		if("RSVPList".equals(widgetid)){
			JSONObject fbid = new JSONObject(data);
			String facebookId="";
			if(fbid.has("fbeventid")){
				facebookId = fbid.getString("fbeventid");
				updateConfigData(eventid,facebookId);
				System.out.println("adding rsvp widget, with fbid:: "+facebookId+" insert/update in page desing also");
				String updateBuyerCustom = "update custom_widget_options set config_data=? where eventid=? and stage='draft'";
				StatusObj statobjBuyer = DbUtil.executeUpdateQuery(updateBuyerCustom, new String[]{data,eventid});
				if(statobjBuyer.getCount()==0){
					String insertBuyerCustom = "insert into custom_widget_options(eventid,widgetid,config_data,widget_title,widget_ref_title,created_at,updated_at,stage) "+
									"select CAST(? AS BIGINT),widgetid,?,widget_title,widget_ref_title,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),'draft' "+
									"from default_widget_options where lang=? and widgetid='RSVPList'";
					StatusObj statobjBuyerInsert = DbUtil.executeUpdateQuery(insertBuyerCustom, new String[]{eventid,data,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),I18NCacheData.getI18NLanguage(eventid)});
				}
			}
		}

		String updateQry = "update buyer_att_custom_widget_options set config_data=?,widget_title=?,widget_ref_title=?,updated_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') "+
				"where eventid=?::bigint and widgetid=? and stage='draft' and layout_type=?";
		String insertQry="insert into buyer_att_custom_widget_options (eventid,widgetid,config_data,widget_title,widget_ref_title,created_at,updated_at,stage,layout_type)"+
				" values(CAST(? AS BIGINT),?,?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),'draft',?)";

		StatusObj statobj = DbUtil.executeUpdateQuery(updateQry, new String[]{data,title,ref_title,DateUtil.getCurrDBFormatDate(),eventid,widgetid,layout_type});
		if(statobj.getCount()==0)
			statobj=DbUtil.executeUpdateQuery(insertQry, new String []{eventid,widgetid,data,title,ref_title,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),layout_type});

		updateSync(eventid,"no",layout_type);
		if("whosAttending".equals(widgetid)){
			saveWhosAttendingData(eventid,data,layout_type);
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
	
	private static void updateSync(String eventid,String sync,String layout_type){
		String query = "update buyer_att_page_layout set sync=? where eventid=?::bigint and stage='draft' and layout_type=?";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{sync,eventid,layout_type});
	}
	public static void saveWhosAttendingData(String eid,String jsondata,String layout_type){
		ArrayList<Entity>arrlist=new ArrayList<Entity>();
		try{
		JSONObject jsonObj=new JSONObject(jsondata);
		JSONArray orderarr=jsonObj.getJSONArray("positions");
		for(int i=0;i<orderarr.length();i++){
			String attribid=jsonObj.getString(orderarr.getString(i));
			arrlist.add(new Entity(attribid,orderarr.getString(i)));
		}
		CustomAttributesDB cdb=new CustomAttributesDB();
		cdb.saveAttendeeAttribs(eid,arrlist,"EVENT");
		
		}catch(Exception e){
			
		}
	}
	
	public static Boolean deteWidgetOption(String widgetid,String eventid,String layout_type){
		String query = "delete from  buyer_att_custom_widget_options where eventid=?::bigint and widgetid=? and stage='draft' and layout_type=?";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,widgetid,layout_type});
		if(statobj.getStatus())
			return true;
		else
			return false; 
		
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
		return hmap;
	}
	
	 public  static boolean saveRefTitle(String eventid,String title,String widgetid,String layout_type){
			try{
			  	 
		String query   = "update buyer_att_custom_widget_options set widget_ref_title=?,updated_at='now()' where eventid=?::bigint and widgetid=? and stage='draft' and layout_type=?";
		StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{title,eventid,widgetid,layout_type});
			if(statobj.getStatus() && statobj.getCount()>0)
				return true;
				else{
					 query= "insert into buyer_att_custom_widget_options(eventid,widgetid,widget_ref_title,config_data,stage,layout_type) values(?::bigint,?,?,?,'draft',?)";
					statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,widgetid,title,"",layout_type});
					String yes=DbUtil.getVal("select 'yes' from buyer_att_custom_widget_options where eventid=?::bigint and widgetid=? and stage='final' and layout_type=?", new String[]{eventid,widgetid,layout_type});
					if(!"yes".equals(yes)){
					query= "insert into buyer_att_custom_widget_options(eventid,widgetid,widget_ref_title,config_data,stage,layout_type) values(?::bigint,?,?,?,'final',?)";
					statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,widgetid,title,"",layout_type});
					}
					if(statobj.getStatus())
						return true;
					else
						return false;
				}
							
			}catch(Exception e){System.out.println(" exception while savingsaving  titles");return false;}					
		 }
	 
	 public static Boolean resetLastFinal(String eventid,String type){
		 String query = "update buyer_att_page_layout set global_style=aa.global_style,wide_widgets=aa.wide_widgets,narrow_widgets=aa.narrow_widgets,single_widgets=aa.single_widgets,single_bottom_widgets=aa.single_bottom_widgets,hide_widgets=aa.hide_widgets,updated_at='now()' from (select global_style,wide_widgets,narrow_widgets,single_widgets,single_bottom_widgets,hide_widgets from buyer_att_page_layout  where stage='final' and eventid=?::bigint) aa where stage='draft' and eventid=?::bigint ";
		 StatusObj statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid,eventid});
		 if(statobj.getStatus() && statobj.getCount()>0){
			 updateSync(eventid, "yes",type);
			 query="delete  from buyer_att_custom_widget_options where eventid=?::bigint and stage='draft'";
			 statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid});
			 query = "insert into  buyer_att_custom_widget_options (eventid,widgetid,config_data,widget_title,widget_ref_title,updated_at,stage) select eventid,widgetid,config_data,widget_title,widget_ref_title,now(),'draft' from buyer_att_custom_widget_options  where stage='final' and eventid=?::bigint ";
			 statobj = DbUtil.executeUpdateQuery(query, new String[]{eventid});
			 if(statobj.getStatus()){
				 return true;
			 }
			 else				 
				 return false;
		 }		 
		 else
			 return false;		
	 }
	 
	 public static String deleteFiles(String eid, String fileid) {
		 String data="";
		 String configdata=DbUtil.getVal("select config_data from buyer_att_custom_widget_options where eventid=CAST(? AS BIGINT) and widgetid='files' and stage='draft' and layout_type='buyer'" , new String[]{eid});
			JSONObject	 jsonobj = new JSONObject();
			JSONArray newobj=new JSONArray();
			JSONArray obj=new JSONArray();
			try{
				JSONObject	jsonData = new JSONObject(configdata);
				 obj=(JSONArray)jsonData.get("files");
				 for(int i=0;i<obj.length();i++)
				 {   
					 String foundfile=obj.getJSONObject(i).get("fileid").toString();
					 if(!foundfile.equals(fileid))
					 {
						 newobj.put(obj.getJSONObject(i));
					 }
					 else System.out.println("found obj"+obj.getJSONObject(i).toString());
				 }
				 jsonobj.put("files",newobj);
				 String updateddata=jsonobj.toString();
				 String updateQry="update buyer_att_custom_widget_options set config_data=? where eventid=CAST(? AS BIGINT) and layout_type='buyer' and stage='draft' and widgetid='files'";
				 StatusObj statobj = DbUtil.executeUpdateQuery(updateQry,new String[]{updateddata,eid});
				 if(statobj.getCount()==1){
					 //System.out.println("updated successfully");
					  data= DbUtil.getVal("select config_data from buyer_att_custom_widget_options where eventid=CAST(? AS BIGINT) and widgetid='files' and stage='draft' and layout_type='buyer'" , new String[]{eid});
				 }
			}
			catch(Exception e){		
				System.out.println("failure");
			}
			return data;
			
	 }
	 
	 public static String  getFilesDetails(String eid){
		 String data =  DbUtil.getVal("select config_data from buyer_att_custom_widget_options where eventid=CAST(? AS BIGINT) and widgetid='files' and stage='draft' and layout_type='buyer'" , new String[]{eid});
		 if("".equals(data) || data==null)
			 data = DbUtil.getVal("select config_data from buyer_att_def_widget_options where lang=? and widgetid='files' and layout_type='buyer'", new String[]{I18n.getLanguageFromSession()});
		 return data;
	 }
	 
	 
	 
}
