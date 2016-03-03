package com.eventbee.widget;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eventbee.general.EbeeConstantsF;
import com.eventbee.layout.EventGlobalTemplates;

public class RenderOrganizerWidgetHTML implements RenderWidgetHTML {

	@Override
	public String getHTML(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash) throws Exception
		 {
	//System.out.println("configHash--->"+configHash);
		JSONObject config_options = new JSONObject(configHash.get("config_options"));
		JSONObject organizer = new JSONObject(config_options.getString("organizer"));
		
		JSONArray chkarray = organizer.getJSONArray("chkflds");
		ArrayList<String> chkvals = new ArrayList<String>();
		String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		VelocityContext vc = new VelocityContext();
		for(int i=0;i<chkarray.length();i++)
			chkvals.add(chkarray.getString(i));
		
		if(chkvals.contains("chkhost"))
			vc.put("hostedby", organizer.get("hostbydata"));
		if(chkvals.contains("chkctmgr")){
			String contactMgr="Contact Manager";
			if(!"".equals(organizer.get("ctcmgrdata").toString()))
				contactMgr=organizer.get("ctcmgrdata").toString();
			vc.put("contactmgr", contactMgr);
		}
		if(chkvals.contains("chknotes")){
			vc.put("notes", organizer.get("notesdata"));
		}
		if(chkvals.contains("show")){
			vc.put("action", "SaveOrganizerEmail.jsp");
		}else{
			vc.put("action", "/portal/emailprocess/emailtoevtmgr.jsp");
		}
		String template=configHash.get("global_template_organizer");
		if(template==null || "".equals(template.trim()))
		   template=EventGlobalTemplates.get("global_template_organizer","");
		
		vc.put("eventid", dataHash.get("eventid"));
		vc.put("subject", dataHash.get("eventname"));
		vc.put("serveraddress", serveraddress);
		String result=getVelocityOutPut(vc,template);
		//System.out.println("ORG:::-->"+result);
		return result;
	}

	@Override
	public Boolean IsRenderable(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash)
			 {
		/*try{
		JSONObject config_options = new JSONObject(configHash.get("config_options"));
		if(!config_options.has("organizer"))return false;
		JSONObject organizer = new JSONObject(config_options.getString("organizer"));
		if(!organizer.has("chkflds"))return false;
		JSONArray chkarray = organizer.getJSONArray("chkflds");
		ArrayList<String> chkvals = new ArrayList<String>();
		
		for(int i=0;i<chkarray.length();i++)        
			chkvals.add(chkarray.getString(i));
		if(chkvals.contains("chkhost") || chkvals.contains("chkctmgr") || chkvals.contains("chknotes"))
			return true;
		else
			return false;
		}catch(Exception e){
			return false;
		}*/
		// TODO Auto-generated method stub
	return true;	
	}
	
	private static String getVelocityOutPut(VelocityContext vc,String Template){
		//System.out.println("Organizer Template::>"+Template);
		StringBuffer str=new StringBuffer();
		java.io.StringWriter out1=new java.io.StringWriter();
		VelocityEngine ve= new VelocityEngine();
		try{
			ve.init();
			boolean abletopares=ve.evaluate(vc,out1,"organizer",Template );
			str=out1.getBuffer();
		}catch(Exception e){
			System.out.println("organizer exception: "+e.getMessage());
		}
			return str.toString();
	}

}
