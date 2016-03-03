package com.eventmanage.layout.actions;

import com.eventbee.layout.DBHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class LayoutManageAction extends ActionSupport implements Preparable,ValidationAware{ 
	private String eid="";
	private String action="";
	private String widgetid="";
	private String target="";
	private String jsonData="";
	private String msgKey="";
	private String themecode = "";
	
	
	public String getThemecode() {
		return themecode;
	}

	public void setThemecode(String themecode) {
		this.themecode = themecode;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getWidgetid() {
		return widgetid;
	}

	public void setWidgetid(String widgetid) {
		this.widgetid = widgetid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String execute(){
		/*JSONArray themeDetails = new JSONArray();
		themeDetails = DBHelper.getThemeDetails();*/
		try{
			DBHelper.updateDraftWithFinal(eid);
			jsonData=DBHelper.getLayout(eid,"_ref","draft").toString();
		}catch(Exception e){
			System.out.println("Exception in LayoutManageAction.java execute(): ERROR:: "+e.getMessage());
		}
		//System.out.println(themeDetails);
		return "success";	
	}
public String editLayout() {
     System.out.println("action::"+action);
     if(action!=null && !"".equals(action) && "WidgetOptions".equals(action)&&  widgetid!=null && !"".equals(widgetid) ){
    	 target=widgetid.split("_")[0];
    	 target = Character.toUpperCase(target.charAt(0)) + target.substring(1);
    	// target=target!=null &&!"".equals(target) ?"_"+target:target;
    	 target="/eventmanage/layoutmanage/Options/Get"+target+action;
    	 System.out.println("target:::"+target);
    	 return "forward";
     }
     
	return "success";
	
}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String chanagetheme(){
		String changeTheme = DBHelper.chenageThemJson(getEid(),getThemecode());
		if("success".equals(changeTheme))
			setMsgKey("success");
		else
			setMsgKey("fail");
		return "ajaxmsg";
	}
	
	public String updateDraftWithFinal(){
		DBHelper.updateDraftWithFinal(eid);
		return "ajaxmsg";
	}
}
