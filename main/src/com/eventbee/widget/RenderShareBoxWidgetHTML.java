package com.eventbee.widget;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONObject;

import com.eventbee.general.GenUtil;
import com.eventbee.layout.EventGlobalTemplates;

public class RenderShareBoxWidgetHTML implements RenderWidgetHTML {

	@Override
	public String getHTML(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash)
			throws Exception {
		JSONObject config_options = new JSONObject(configHash.get("config_options"));
		String data=config_options.getString("shareBox");
		java.io.StringWriter out=new java.io.StringWriter();
		Date date=new java.util.Date();
		long time=date.getTime();
		StringBuffer sbf=new StringBuffer();
		try{
		
		String serveraddress=refHash.get("serveraddress");
		if(serveraddress.substring(0, serveraddress.length()-1).indexOf("/")>-1)
			  serveraddress=refHash.get("serveraddress").substring(0,refHash.get("serveraddress").length()-1);
		String groupid=refHash.get("eventid");
		String optionslist="",soptions="",email="",name="",subject="";
		JSONObject jsondata=new JSONObject(data);
		optionslist=jsondata.get("shareboxoptions")+"";
		if(optionslist!=null && !"".equals(optionslist))
			soptions=optionslist.substring(1, optionslist.length()-1);
			String[] strarr=GenUtil.strToArrayStr(soptions,",");
			ArrayList<String> shareoptions=new ArrayList<String>();
			for(int i=0;i<strarr.length;i++){    
				shareoptions.add(strarr[i]);
			}
			JSONObject labels=jsondata.getJSONObject("labels");
			
			VelocityContext vcontext = new VelocityContext();
			vcontext.put("serveraddress", serveraddress);
			vcontext.put("eid", groupid);
			vcontext.put("time", time);
			if(shareoptions.contains("emailfrnd")){
				String emailfrndlabel=labels.getString("emailfrnd");
				if(emailfrndlabel==null || "".equals(emailfrndlabel))emailfrndlabel="Email this to a friend";
				String msg=labels.getString("bmessage");
				subject=dataHash.get("eventname");
				vcontext.put("emailfriendLink", "Yes");
				vcontext.put("subject", subject);
				vcontext.put("emailfrndlabel", emailfrndlabel);
				vcontext.put("email", email);
				vcontext.put("name", name);
				vcontext.put("message", msg);
				
			}
			
            if(shareoptions.contains("fsend")){
            	vcontext.put("fbsend", "Yes");
            	
			}
            
           if(shareoptions.contains("eventurl")){
            	vcontext.put("EventURL", "Yes");
            	String eventurllabel=labels.getString("eventurl");
            	if(eventurllabel==null || "".equals(eventurllabel))eventurllabel="Event URL";
            	vcontext.put("eventurllabel", eventurllabel);
           }
            
            if(strarr.length==0){}else{
            	
                VelocityEngine ve= new VelocityEngine(); 
         	    
                String sharewdgttemplate=configHash.get("global_template_shareboxwidget");
                if(sharewdgttemplate==null || "".equals(sharewdgttemplate.trim()))
                   sharewdgttemplate=EventGlobalTemplates.get("global_template_shareboxwidget", "");
                
                //System.out.println("Template>>>>>>>>>>>>>>>>>>"+sharewdgttemplate);
                
                
          	  try{
          	  ve.init();
          	  //System.out.println("sbf is:"+sbf.toString());
          	  boolean abletopares=ve.evaluate(vcontext,out,"ebeetemplate",sharewdgttemplate);
          	  sbf=out.getBuffer();
          	  //System.out.println("sbf1 is:"+sbf1);
          	  }
          	  catch(Exception exp){
          	  System.out.println("Exception occured in evaluate velocity"+exp.getMessage());
          	  exp.printStackTrace();
          	  }  
            	
            }
		
		}catch(Exception e){
			System.out.println("Exception occured in RenderShareBoxWidgetHTML"+e.getMessage());
			e.printStackTrace();
		}
		//System.out.println("buffer"+buffer.toString());
		return sbf.toString() ;
    }

	@Override
	public Boolean IsRenderable(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash)
		{
		/*try{
		JSONObject config_options = new JSONObject(configHash.get("config_options"));
		String data=config_options.getString("shareBox");
		String soptions="",optionslist="";
		
		JSONObject jsondata=new JSONObject(data);
		optionslist=jsondata.get("shareboxoptions")+"";
		if(optionslist!=null && !"".equals(optionslist))
			soptions=optionslist.substring(1, optionslist.length()-1);
			String[] strarr=GenUtil.strToArrayStr(soptions,",");
		if(strarr.length==0)
			return false;
		return true;
		}catch(Exception e){
			System.out.println(" ShareBox IsRenderable  parsing error "+e.getMessage());
			return false;
		}*/
		return true;
	
		}
}
