package com.eventbee.widget;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eventbee.general.EbeeConstantsF;
import com.eventbee.layout.DBHelper;
import com.eventbee.layout.EventGlobalTemplates;

public class RenderRSVPListWidgetHTML implements RenderWidgetHTML {

	@Override
	public String getHTML(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash)
			throws Exception {
		JSONObject config_options = new JSONObject(configHash.get("config_options"));
		JSONObject rsvplist = new JSONObject(config_options.getString("RSVPList"));
		String fbeventid=rsvplist.getString("fbeventid");
		JSONArray chkarray = rsvplist.getJSONArray("chkflds");
		ArrayList<String> chkvals = new ArrayList<String>();
		String showgender="Y";
		for(int i=0;i<chkarray.length();i++)
			chkvals.add(chkarray.getString(i));
		if(!chkvals.contains("fbmfcount"))
			showgender="N";
		
		System.out.println("RenderRSVPListWidgetHTML");
		String eid=refHash.get("eventid");
		String serveraddress="http://"+EbeeConstantsF.get("serveraddress","localhost");
		
		String sec="",venueid="",domain="",fbuid="",source="";
		if(sec==null)sec="";
		if(venueid==null)venueid="";
		if(domain==null)domain="";
		if(fbuid==null)fbuid="";
		if(source==null || source.equals(""))
			source="ebee";
		String name=source+".fbconnect.appid";
		String fbappid=DBHelper.getFBAppId(name);
		//buffer.append("<iframe width='290px' scrolling='no' height='295px' frameborder='0' style='margin:0px;' src='http://www.eventbee.com/portal/socialnetworking/fbattendeelist.jsp?eid=159704432&showgender="+showgender+"'></iframe>");
		String template=configHash.get("global_template_rsvplist");
		if(template==null || "".equals(template.trim()))
		   template=EventGlobalTemplates.get("global_template_rsvplist","");
		VelocityContext vc = new VelocityContext();
		vc.put("fbappid", fbappid);
		vc.put("fbeventid", fbeventid);
		vc.put("showgender", showgender);
		vc.put("eid", eid);
		vc.put("serveraddress", serveraddress);
		vc.put("source", source);
		vc.put("domain", domain);
		vc.put("venueid", venueid);
		vc.put("fbuid", fbuid);
		vc.put("record_id", "");
		vc.put("sec", "");
		vc.put("boxtype", "");
		
		String result=getVelocityOutPut(vc,template);
		//System.out.println("result: "+result);
		return result;
		//return config_options.getString("RSVPList");
	}

	@Override
	public Boolean IsRenderable(HashMap<String, String> refHash,
			HashMap<String, String> dataHash, HashMap<String, String> configHash)
			 {
		try{
		JSONObject config_options = new JSONObject(configHash.get("config_options"));
		if(!config_options.has("RSVPList"))
			return false;
		
		JSONObject rsvplist = new JSONObject(config_options.getString("RSVPList"));
		if(!rsvplist.has("fbeventid") || !rsvplist.has("chkflds"))
			return false;
		String fbeventid=rsvplist.get("fbeventid").toString();
		JSONArray chkarray = rsvplist.getJSONArray("chkflds");
		ArrayList<String> chkvals = new ArrayList<String>();
		for(int i=0;i<chkarray.length();i++)
			chkvals.add(chkarray.getString(i));
		
		if(fbeventid== null || "".equals(fbeventid) || !chkvals.contains("fbatt")){
			return false;
		}
		// TODO Auto-generated method stub
		return true;
		}catch(Exception e){
			return false;
		}
	}
	
	private static String getVelocityOutPut(VelocityContext vc,String Template){
		//System.out.println("RSVP>>>>>>>>>>>>"+Template);
		String nameMain = "/main/";
		String nameMainnew = "/main/";
		Template = Template.replaceAll(nameMain, nameMainnew);
		//System.out.println("RSVP<<<<<<<<<<<<<<<<"+Template);
		StringBuffer str=new StringBuffer();
		java.io.StringWriter out1=new java.io.StringWriter();
		VelocityEngine ve= new VelocityEngine();
		try{
			ve.init();
			boolean abletopares=ve.evaluate(vc,out1,"fbrsvplist",Template);
			str=out1.getBuffer();
		}catch(Exception e){
			System.out.println("fbrsvplist exception: "+e.getMessage());
		}
			return str.toString();
	}

}
