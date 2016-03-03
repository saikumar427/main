package com.eventbee.widget;

import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONObject;

import com.event.dbhelpers.EventDB;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.layout.DBHelper;
import com.eventbee.layout.EventGlobalTemplates;

public class RenderWhosAttendingWidgetHTML implements RenderWidgetHTML {

	@Override
	public String getHTML(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash)
			throws Exception {
		JSONObject config_options = new JSONObject(configHash.get("config_options"));
		JSONObject whosAttlist = new JSONObject(config_options.getString("whosAttending"));
		String eid=refHash.get("eventid");
		//String isAttendeeShow=EventDB.getConfigVal(eid, "eventpage.attendee.show", "");
		boolean isrecurringEvt=EventDB.isRecurringEvent(eid);
		String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		String template = configHash.get("global_template_whosattending");
		if(template==null || "".equals(template.trim()))
		template=EventGlobalTemplates.get("global_template_whosattending", "");
		VelocityContext vc = new VelocityContext();
		vc.put("eid",eid);
		vc.put("serveraddress",serveraddress);
			if(isrecurringEvt){
				vc.put("isrecur","");
				String recurringselect=DBHelper.getRecurringEventDates(eid,"attendeelist");
				if(recurringselect==null)
					vc.put("nodates","");
				else
					vc.put("selectdata",recurringselect);
			}
			String result=getVelocityOutPut(vc,template);
		return result;
	}
    
	@Override
	public Boolean IsRenderable(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash) {
		try{
			JSONObject config_options = new JSONObject(configHash.get("config_options"));
			JSONObject whosAttlist = new JSONObject(config_options.getString("whosAttending"));
			String whosdata="";
			if(whosAttlist.has("templatedata")){
				whosdata=whosAttlist.getString("templatedata");
			}
				 
			else
				return false;
			if("".equals(whosdata.trim()) || "<br>".equals(whosdata.trim()))return false;
		}catch(Exception e){
			return false;
		}
		// TODO Auto-generated method stub
		return true;
	}
	
private static String getVelocityOutPut(VelocityContext vc,String Template){
	
		String nameMain = "/main/";
		String nameMainnew = "/main/";
		Template = Template.replaceAll(nameMain, nameMainnew);
		
		StringBuffer str=new StringBuffer();
		java.io.StringWriter out1=new java.io.StringWriter();
		VelocityEngine ve= new VelocityEngine();
		try{
			ve.init();
			boolean abletopares=ve.evaluate(vc,out1,"whosattending",Template );
			str=out1.getBuffer();
		}catch(Exception e){
			System.out.println("whosattending exception: "+e.getMessage());
		}
			return str.toString();
	}

}
