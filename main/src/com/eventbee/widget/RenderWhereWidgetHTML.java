package com.eventbee.widget;

import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eventbee.layout.EventGlobalTemplates;

public class RenderWhereWidgetHTML implements RenderWidgetHTML {

	@Override
	public String getHTML(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash)
			throws Exception {
		
		JSONObject config_options = new JSONObject(configHash.get("config_options"));
		
		String longitude = config_options.getString("longitude");
		String latitude = config_options.getString("latitude");
		
		String where=config_options.getString("where");
		java.io.StringWriter out=new java.io.StringWriter();
		StringBuffer sbf1=new StringBuffer("");
		try{
			String serveraddress=refHash.get("serveraddress");
			if(serveraddress.substring(0, serveraddress.length()-1).indexOf("/")>-1)
			  serveraddress=refHash.get("serveraddress").substring(0,refHash.get("serveraddress").length()-1);
			JSONObject json=new JSONObject(where);
		    String wherec=json.has("wherec")?json.getString("wherec"):"";
		    
		    String gmap=json.has("gmap")?json.getString("gmap"):"";
		    JSONArray keyw=json.getJSONArray("keyw");
		    /*String lat=json.getString("lat");
		    String lng=json.getString("lng");*/
		    gmap=gmap==null?"":gmap;
		    /*lat=lat==null?"":lat;
		    lng=lng==null?"":lng;*/
		    /*if("".equals(lat) || "".equals("lng"))
		    {
		    	lat=dataHash.get("latitude");
		    	lng=dataHash.get("longitude");
		    }*/
		    VelocityContext vcontext = new VelocityContext();
		    vcontext.put("serveraddress", serveraddress);
		    if(wherec!=null && !"".equals(wherec)){
		    	vcontext.put("isWhere", "Yes");
		    	vcontext.put("content", replaceKeyWords(keyw,dataHash,wherec));
		    	//System.out.println("content is:"+replaceKeyWords(keyw,dataHash,wherec));
		    	if(gmap.indexOf("gmap")>-1)
		  	    {
		    		vcontext.put("isgMap", "Yes");
		    		vcontext.put("longitude", longitude);
		    		vcontext.put("latitude", latitude);
		    		vcontext.put("address", dataHash.get("address1"));
		    		vcontext.put("city", dataHash.get("city"));
		    		vcontext.put("state", dataHash.get("state"));
		    		vcontext.put("country", dataHash.get("country"));
		  	    }
		    }
		 
		    VelocityEngine ve= new VelocityEngine(); 
		    String wherewdgttemplate=configHash.get("global_template_wherewidget");
		    if(wherewdgttemplate==null || "".equals(wherewdgttemplate.trim()))
		    	wherewdgttemplate=EventGlobalTemplates.get("global_template_wherewidget", "");
			//System.out.println("wherewdgttemplate in getHTML:    WHERE:"+wherewdgttemplate);
		    String nameMain = "/main/";
			String nameMainnew = "/main/";
			wherewdgttemplate = wherewdgttemplate.replaceAll(nameMain, nameMainnew);
			try{
		 	  ve.init();
		 	  //System.out.println("sbf is:"+sbf.toString());
		 	 boolean abletopares=ve.evaluate(vcontext,out,"ebeetemplate",wherewdgttemplate);
		 	  sbf1=out.getBuffer();
		 	  }
		 	  catch(Exception exp){
		 	  System.out.println("Exception occured in evaluate velocity"+exp.getMessage());
		 	  exp.printStackTrace();
		 	  } 
		}catch(Exception e){
			System.out.println(" where parsing error "+e.getMessage()); 
			//e.printStackTrace();
		}
		
		//System.out.println("the final where widget content is::"+sbf1);
		
	      return sbf1.toString();
}
	

	@Override
	public Boolean IsRenderable(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash)
			throws Exception {
		// TODO Auto-generated method stub
		return true;
	}
	
	private static String replaceKeyWords(JSONArray keywords,HashMap<String, String> datahash, String content){
		try {
		for(int i=0;i<keywords.length();i++){			
				String key = keywords.getString(i);			
				
				String value=datahash.get(key.replace("$", "").toLowerCase());
				
			         while(content.contains(key)){
			        	/*if(content.charAt(content.indexOf(key)+key.length())==',' && "".equals(value))
						content=content.substring(0,content.indexOf(key)+key.length())+content.substring(content.indexOf(key)+key.length()+1,content.length());*/
						content=content.replace(key, value);			    	  
			         }
		     }
		} catch (JSONException e) {
			System.out.println("exception while replacing key words"+e.getMessage());	
			}
		//System.out.println("content"+content);
		return content;
	}

}
