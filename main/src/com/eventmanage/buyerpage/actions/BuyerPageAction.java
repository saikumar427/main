package com.eventmanage.buyerpage.actions;

import org.json.JSONArray;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.eventbee.buyer.att.layout.*;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.layout.DBHelper;

public class BuyerPageAction extends ActionSupport implements Preparable,ValidationAware{
	private String eid="";
	private String action="";
	private String jsonData="";
	private String widgetid="";
	private String target="";
	private String filesdata="";
	private String fileid="";
	
	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getFilesdata() {
		return filesdata;
	}

	public void setFilesdata(String filesdata) {
		this.filesdata = filesdata;
	}

	public String getWidgetid() {
		return widgetid;
	}

	public void setWidgetid(String widgetid) {
		this.widgetid = widgetid;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String execute(){
		try{
			jsonData=BuyerAttHelper.getLayout(eid,"_ref","draft","buyer");
		}catch(Exception e){
			System.out.println("Exception in LayoutManageAction.java execute(): ERROR:: "+e.getMessage());
		}
		return "success";
	}
	public String editBuyerLayout() {
	     System.out.println("action::"+action);
	     if(action!=null && !"".equals(action) && "WidgetOptions".equals(action)&&  widgetid!=null && !"".equals(widgetid) ){
	    	 target=widgetid.split("_")[0];
	    	 target = Character.toUpperCase(target.charAt(0)) + target.substring(1);
	    	 target="/eventmanage/layoutmanage/BuyerAttOptions/Get"+target+action;
	    	 return "forward";
	     }
		return "success";
	}
	
	public String getFilesData(){
		 filesdata=BuyerAttHelper.getFilesDetails(eid);
		 return "jsondata";
	}
	
	
	public String deleteFile(){
		filesdata=BuyerAttHelper.deleteFiles(eid,fileid);
		return "jsondata";
	}
}
