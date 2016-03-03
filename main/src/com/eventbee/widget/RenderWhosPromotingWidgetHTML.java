package com.eventbee.widget;

import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONObject;

import com.eventbee.layout.EventGlobalTemplates;

public class RenderWhosPromotingWidgetHTML implements RenderWidgetHTML {

	@Override
	public String getHTML(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash)
			throws Exception {
		
		//JSONObject config_options = new JSONObject(configHash.get("config_options"));
		
		String promos="";
		String eventid=refHash.get("eventid");
		String serveraddress=refHash.get("serveraddress");
		
		String template =configHash.get("global_template_whospromoting");
		if(template==null || "".equals(template.trim()))
			template=EventGlobalTemplates.get("global_template_whospromoting","");
		
		VelocityContext vc = new VelocityContext();
		vc.put("eid", eventid);
		promos=getVelocityOutPut(vc,template);
		//System.out.println("the promos::"+promos);
		return promos;
		
	}

	@Override
	public Boolean IsRenderable(HashMap<String, String> refHash,
		HashMap<String, String> dataHash, HashMap<String, String> configHash) throws Exception{
		
		JSONObject config_options = new JSONObject(configHash.get("config_options"));
		System.out.println("is show OR NOT>>>>>"+config_options);
		
		/*if(config_options.has("whosPromoting")){
			System.out.println("this is whos Data::::;;FIRST");
			JSONObject whosData= new JSONObject(config_options.getString("whosPromoting"));
			System.out.println("this is whos Data::::;;LAST"+whosData);
			String seltype=whosData.getString("seloption");
			if("show".equals(seltype))
				return true;
			else
				return false;
		}*/
		
		try{
			JSONObject whosData= new JSONObject(config_options.getString("whosPromoting"));
			String seltype=whosData.getString("seloption");
			if("show".equals(seltype))
				return true;
			else
				return false;
		}catch(Exception e){
			System.out.println("Exception while render whos promoting:: "+e);
		}
		
		// TODO Auto-generated method stub
	
	
	return false;
	
	
	
		/*try{		
			 String when=config_options.getString("when");
			 JSONObject json=new JSONObject(when);
		     String whenc=json.getString("whenc");
		   if(whenc==null || "".equals(whenc)){
		   return false;
		   }
	}catch(Exception e){System.out.println(" when IsRenderable  parsing error "+e.getMessage());}
		return true;*/
	
	
	
	
	
	
	
	
	}
	
private static String getVelocityOutPut(VelocityContext vc,String Template){
	System.out.println("beforTemplate::;"+Template);
		String nameMain = "/main/";
		String nameMainnew = "/main/";
		Template = Template.replaceAll(nameMain, nameMainnew);
		
		System.out.println("afterTemplate::;"+Template);
		
		
		StringBuffer str=new StringBuffer();
		java.io.StringWriter out1=new java.io.StringWriter();
		VelocityEngine ve= new VelocityEngine();
		try{
			ve.init();
			boolean abletopares=ve.evaluate(vc,out1,"whospromoting",Template );
			str=out1.getBuffer();
		}catch(Exception e){
			System.out.println("fbrsvplist exception: "+e.getMessage());
		}
			return str.toString();
	}
	
}
