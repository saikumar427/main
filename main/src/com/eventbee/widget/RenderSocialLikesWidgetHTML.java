package com.eventbee.widget;

import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONObject;

import com.eventbee.layout.EventGlobalTemplates;

public class RenderSocialLikesWidgetHTML implements RenderWidgetHTML {

	@Override
	public String getHTML(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash)
			throws Exception {
		JSONObject config_options = new JSONObject(configHash.get("config_options"));
		VelocityContext vc = new VelocityContext();
		String data=config_options.getString("socialLikes");
		try{
		String serveraddress=refHash.get("serveraddress");		
		String groupid=refHash.get("eventid");
		
		if((data.indexOf("fb")>-1)){
			String exthtml="";
			String eventurl=serveraddress+"event?eid="+groupid;
		   // String encodeurl=URLEncoder.encode(eventurl);
			vc.put("fbUrl", eventurl);	
			vc.put("exthtml", exthtml);	
		}		
		if((data.indexOf("twit")>-1)){
			String src=serveraddress+"portal/socialnetworking/twitter.jsp?eid="+groupid;
			vc.put("twitUrl", src);
		}			
		if((data.indexOf("google")>-1)){
		String src=serveraddress+"portal/socialnetworking/googleplus1.jsp?eid="+groupid;
		vc.put("googleUrl", src);
		}	
		
		if("[]".equals(data))
		return "";		
		else {
		vc.put("isRender", "yes");
		String template=configHash.get("global_template_sociallikes");
		if(template==null || "".equals(template))
          template=EventGlobalTemplates.get("global_template_sociallikes","");	
		return getVelocityOutPut(vc,template);
		}

		
		
		}catch(Exception e){System.out.println("exeception while preparing sociallikes html::"+e.getMessage());}		
		return "" ;
	}

	@Override
	public Boolean IsRenderable(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash)
			 {
		return true;
		/*try{
		JSONObject config_options = new JSONObject(configHash.get("config_options"));
		if(!config_options.has("socialLikes"))return false;
		String data=config_options.getString("socialLikes");
		if("[]".equals(data)){return false;}
		return true;
		}catch(Exception e){
		return false;	
		}*/
		}
	private static String getVelocityOutPut(VelocityContext vc,String Template){		
		StringBuffer str=new StringBuffer();
		java.io.StringWriter out1=new java.io.StringWriter();
		VelocityEngine ve= new VelocityEngine();
		try{
			ve.init();
			ve.evaluate(vc,out1,"sociallikes",Template );			
			str=out1.getBuffer();			
		 }catch(Exception e){System.out.println("sociallikes tempalte exception: "+e.getMessage());}
			return str.toString();
	 }
}
