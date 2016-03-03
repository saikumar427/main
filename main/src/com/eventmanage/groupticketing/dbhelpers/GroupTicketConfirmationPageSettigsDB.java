package com.eventmanage.groupticketing.dbhelpers;

import com.event.dbhelpers.ConfirmationPageSettingsDB;
import com.eventbee.general.DbUtil;


public class GroupTicketConfirmationPageSettigsDB {
	
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
	public static void updateContent(String eid,String content){
		     System.out.println("in update content");
	   		DbUtil.executeUpdateQuery("delete from reg_flow_templates where eventid=CAST(? AS BIGINT) and purpose='groupticket_confirmationpage'",new String [] {eid});
			DbUtil.executeUpdateQuery("insert into reg_flow_templates(eventid,purpose,content) values(CAST(? AS BIGINT),'groupticket_confirmationpage',?)",new String [] {eid,content});
			
	}
}