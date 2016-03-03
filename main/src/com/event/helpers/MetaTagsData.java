package com.event.helpers;
import java.util.HashMap;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class MetaTagsData {

	private static HashMap<String, String> pages=new HashMap<String, String>();
	static{
		String query="select  *  from pages_meta_tags_data";

		DBManager dbm=new DBManager();
		StatusObj sbj=dbm.executeSelectQuery(query, null);
		for(int i=0;i<sbj.getCount();i++){

			if(dbm.getValue(i,"description","")!=null && !"".equals(dbm.getValue(i,"description","")))
				pages.put(dbm.getValue(i,"lang","")+"_"+dbm.getValue(i,"page_name","")+"_description",dbm.getValue(i,"description",""));

			if(dbm.getValue(i,"title","")!=null && !"".equals(dbm.getValue(i,"title","")))
				pages.put(dbm.getValue(i,"lang","")+"_"+dbm.getValue(i,"page_name","")+"_title",dbm.getValue(i,"title",""));        	 

			if(dbm.getValue(i,"keywords","")!=null && !"".equals(dbm.getValue(i,"keywords","")))
				pages.put(dbm.getValue(i,"lang","")+"_"+dbm.getValue(i,"page_name","")+"_keywords",dbm.getValue(i,"keywords",""));

			if(dbm.getValue(i,"author","")!=null && !"".equals(dbm.getValue(i,"author","")))
				pages.put(dbm.getValue(i,"lang","")+"_"+dbm.getValue(i,"page_name","")+"_author",dbm.getValue(i,"author",""));
		}
		

	}
	public static void putNewPageTags(String pageName,String title,String description,String keywords,String author){
		if(pageName!=null && !"".equals(pageName)){
			try{	
				pages.remove(pageName+"_description");
				pages.put(pageName+"_description",description);
				pages.remove(pageName+"_title");
				pages.put(pageName+"_title",title);			
				pages.remove(pageName+"_keywords");
				pages.put(pageName+"_keywords",keywords);		
				pages.remove(pageName+"_author");
				pages.put(pageName+"_author",author);

				pageName=pageName==null?"":pageName;
				title=title==null?"":title;
				description=description==null?"":description;
				keywords=keywords==null?"":keywords;
				author=author==null?"":author;

				DbUtil.executeUpdateQuery("delete from pages_meta_tags_data where page_name=?", new String[]{pageName});
				DbUtil.executeUpdateQuery("insert into pages_meta_tags_data(page_name,description,title,keywords,author) values(?,?,?,?,?)", new String[]{pageName,description.replace("'", "''"),title.replace("'", "''"),keywords.replace("'", "''"),author.replace("'", "''")});
			}
			catch(Exception e){
				System.out.println("exception in entering values"+e.getMessage());
			}
		}
	}
	public static String getPageTag(String pageName,String key,String lang){
		
		if(pages.get(lang+"_"+pageName+"_"+key)!=null && !"".equals(pages.get(lang+"_"+pageName+"_"+key)))
			return pages.get(lang+"_"+pageName+"_"+key);
		else
			return null;
	}
	public static HashMap<String, String> getPageTags(String pageName){		
		HashMap<String,String> data=new HashMap<String, String>();
		data.put("description",pages.get(pageName+"_description")==null?"":pages.get(pageName+"_description"));
		data.put("title", pages.get(pageName+"_title")==null?"":pages.get(pageName+"_title"));
		data.put("keywords", pages.get(pageName+"_keywords")==null?"":pages.get(pageName+"_keywords"));
		data.put("author", pages.get(pageName+"_author")==null?"":pages.get(pageName+"_author"));		
		return data;
	}
}
