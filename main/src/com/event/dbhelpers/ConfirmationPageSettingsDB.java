package com.event.dbhelpers;

import java.util.HashMap;

import com.event.i18n.dbmanager.ConfirmationPageDAO;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class ConfirmationPageSettingsDB {
	public static String  getContent(String eid,String purpose){
		//System.out.println("we r in get content");
		String content="";
		content=DbUtil.getVal("select content from reg_flow_templates where " +
				"eventid=CAST(? AS BIGINT) and purpose=?", new String[]{eid,purpose});
		//System.out.println("content in confirmation page setting db"+content);
		if("null".equals(content) || "".equals(content) || content==null){
			content=DbUtil.getVal("select content from reg_flow_templates where " +
					"eventid='0' and purpose=?", new String[]{purpose});
		//System.out.println("content according to eventid 0 "+content);
		}
		return content;
	}
	public static void updateContent(String type,String eid,String content, String templateType){
		//System.out.println("content in update content"+content+""+type);
	    String purpose="";
		if("rsvp".equals(type)){
	    	purpose="rsvp_confirmationpage";
		    DbUtil.executeUpdateQuery("delete from custom_reg_flow_templates where eventid=CAST(? AS BIGINT) and purpose=?",new String [] {eid,purpose});
		    DbUtil.executeUpdateQuery("insert into custom_reg_flow_templates(eventid,purpose,content) values(CAST(? AS BIGINT),?,?)",new String [] {eid,purpose,content});
		}else{			
			if("COMPLETED".equals(templateType)){
				purpose="confirmationpage_completed";
				DbUtil.executeUpdateQuery("delete from custom_reg_flow_templates where eventid=CAST(? AS BIGINT) and purpose=?",new String [] {eid,purpose});
				DbUtil.executeUpdateQuery("insert into custom_reg_flow_templates(eventid,purpose,content) values(CAST(? AS BIGINT),?,?)",new String [] {eid,purpose,content});
			}else{
				purpose="confirmationpage_pending";
				DbUtil.executeUpdateQuery("delete from custom_reg_flow_templates where eventid=CAST(? AS BIGINT) and purpose=?",new String [] {eid,purpose});
				DbUtil.executeUpdateQuery("insert into custom_reg_flow_templates(eventid,purpose,content) values(CAST(? AS BIGINT),?,?)",new String [] {eid,purpose,content});
			}		
		}
	}
	
	public static void resetContent(String type, String eid,String templateType){
		String purpose="";
		if("rsvp".equals(type)){
			purpose="rsvp_confirmationpage";
		    DbUtil.executeUpdateQuery("delete from custom_reg_flow_templates where eventid=CAST(? AS BIGINT) and purpose=?",new String [] {eid,purpose});
		}else{
			if("COMPLETED".equals(templateType)){
				purpose="confirmationpage_completed";
				DbUtil.executeUpdateQuery("delete from custom_reg_flow_templates where eventid=CAST(? AS BIGINT) and purpose=?",new String [] {eid,purpose});
			}else{
				purpose="confirmationpage_pending";
				DbUtil.executeUpdateQuery("delete from custom_reg_flow_templates where eventid=CAST(? AS BIGINT) and purpose=?",new String [] {eid,purpose});
			}
		}
	}
    
	public static HashMap<String,String> checkDefaultDisplayQusetions(String eid,String purpose){
		HashMap<String,String> hmap=new HashMap<String,String>();
		String defquesquery="select attrib_id from confirmationscreen_questions where eventid=? and type=? and attrib_id in(-1,-2) order by attrib_id desc";
		DBManager db=new DBManager();
		StatusObj sb=null;
		sb=db.executeSelectQuery(defquesquery, new String[]{eid,purpose});
		if(sb.getStatus() && sb.getCount()>0){
			for(int i=0;i<sb.getCount();i++){
				hmap.put(db.getValue(i, "attrib_id",""), db.getValue(i, "attrib_id",""));
			}
		}
		return hmap;
    }
	
	public static String  getTemplateContent(HashMap<String, String> hm, String lang, String eid){
		ConfirmationPageDAO pageDao=new ConfirmationPageDAO();
		String content=(String)pageDao.getData(hm, lang, eid).get("content");
		return content;
	}

}
