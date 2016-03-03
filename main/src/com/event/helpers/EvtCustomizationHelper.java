package com.event.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.event.dbhelpers.EvtCustomizationDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.TemplateConverter;

public class EvtCustomizationHelper {
	public static String generateCSSString(HashMap<String,String> lnfMap,String module,String eid){
		String cssfilecontent="";
		String selectQuery="select themecode,themetype from user_roller_themes where refid=? and module='event'";
		DBManager dbManager=new DBManager();
		StatusObj statobj=dbManager.executeSelectQuery(selectQuery,new String []{eid});
		if(statobj.getStatus() && statobj.getCount()>0){
			 String themeCode=dbManager.getValue(0,"themecode","");
			 if(themeCode.equalsIgnoreCase("basic")){
				 cssfilecontent=DbUtil.getVal("select cssurl from ebee_roller_def_themes where module='ebee-responsive' and themecode='ebee-responsive'", new String[]{});;				 
			 }
		}
		else
			 cssfilecontent=DbUtil.getVal("select cssurl from ebee_roller_def_themes where module='ebee-responsive' and themecode='ebee-responsive'", new String[]{});;				 
		if("".equals(cssfilecontent))
			cssfilecontent=EvtCustomizationDB.getDefaultCSSContent("eventtemplate","fordefaultcss");
		
		String csscontent=TemplateConverter.getMessage(lnfMap,cssfilecontent);		
		return csscontent;
	}
	
	public static String generateCSSString(HashMap<String,String> lnfMap,String module){		
		String cssfilecontent=EvtCustomizationDB.getDefaultCSSContent("eventtemplate","fordefaultcss");
		String csscontent=TemplateConverter.getMessage(lnfMap,cssfilecontent);		
		return csscontent;
	}
	
	public static ArrayList<Entity> fillFonts(){
		ArrayList<Entity> fonts=new ArrayList<Entity>();
		fonts.add(new Entity("Verdana","Verdana"));
		fonts.add(new Entity("Arial","Arial"));
		fonts.add(new Entity("Times New Roman","Times New Roman"));
		fonts.add(new Entity("Comic Sans MF","Comic Sans MF"));
		fonts.add(new Entity("Courier New","Courier New"));
		fonts.add(new Entity("Georgia","Georgia"));
		return fonts;
	}
	public static ArrayList<Entity> fillFontSizes(){
		ArrayList<Entity> fontsize=new ArrayList<Entity>();
		for (int i=6; i<61;i++){
			fontsize.add(new Entity(Integer.toString(i)+"px",Integer.toString(i)+"px"));	
		}
				
		return fontsize;
	}
	public static ArrayList<Entity> fillCategories(){
		ArrayList<Entity> catg=new ArrayList<Entity>();
		String [] category=new String[]{"Arts","Associations","Books","Business","Education","Career","Community","Corporate","Entertainment","Entrepreneur","Games","Family","Festivals","Food","Health","Movies","Music","Non-Profit","Politics","Religion","School","Social","Sports","Technology","Travel","Other"};
		for (int i=0; i<category.length;i++){
			catg.add(new Entity(category[i],category[i]));
		}
		return catg;
	}
	public static ArrayList<Entity> fillLocations(){
	ArrayList<Entity> loc=new ArrayList<Entity>();
	String [] US_REGIONS=new String[]{"Albuquerque","Anchorage","Atlanta","Austin","Boise","Boston","Brevard County FL","Charlotte",
			"Chicago","Cincinnati","Cleveland","Colorado Springs","Columbus OH","Dallas","Denver","Detroit",
			"Fresno", "Hartford", "Honolulu", "Houston", "Indianapolis", "Jacksonville", "Kansas City", "Las Vegas",
			"Los Angeles", "Miami", "Milwaukee", "Minneapolis", "Nashville","New Jersey", "New Orleans", "New York City",
			"Norfolk","Oklahoma City","Orlando", "Philadelphia", "Phoenix", "Pittsburgh", "Portland OR",
			"Providence", "Raleigh", "Richmond VA", "Sacramento", "Salt Lake City", "San Diego", "SF Bay Area",
			"Seattle", "St. Louis", "Tampa Bay Area", "Tucson", "Washington, D.C.","Other"
			};
	String [] US_REGION_CODES=new String[]{"albuquerque","anchorage","atlanta","austin","boise","boston","brevardcountyfl","charlotte",
			"chicago","cincinnati","cleveland","coloradosprings","columbusoh","dallas","denver","detroit",
			"fresno", "hartford", "honolulu", "houston", "indianapolis", "jacksonville", "kansas", "lasvegas",
			"losangeles", "miami", "milwaukee", "minneapolis", "nashville","newjersey", "neworleans", "newyork",
			"norfolk","oklahoma","orlando", "philadelphia", "phoenix", "pittsburgh", "portlandor",
			"providence", "raleigh", "richmondva", "sacramento", "saltlake", "sandiego", "bayarea",
			"seattle", "stlouis", "tampabayarea", "tucson", "washington","other"
			};
		for (int i=0; i<US_REGIONS.length;i++){
			loc.add(new Entity(US_REGION_CODES[i],US_REGIONS[i]));
		}
		return loc;
	}
	public static ArrayList<Entity> fillCount(){
		ArrayList<Entity> count=new ArrayList<Entity>();
	String [] countarr={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
	for (int i=0; i<countarr.length;i++){
		count.add(new Entity(countarr[i],countarr[i]));
	}

	return count;
	}

}
