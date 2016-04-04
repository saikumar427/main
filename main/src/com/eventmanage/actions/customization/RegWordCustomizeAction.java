package com.eventmanage.actions.customization;

import java.util.HashMap;

import com.event.dbhelpers.DisplayAttribsDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.EventHelperDB;
import com.eventbee.general.DbUtil;
import com.eventbee.general.I18NCacheData;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class RegWordCustomizeAction extends ActionSupport implements Preparable,ValidationAware{
	private String eid="";
	private String msgType = "regword";
    private HashMap<String,String> wordAttribs=new HashMap<String,String>();
    private String vbEnabled="";
    private String seatingEnabled="";
    private String count="";
    
    public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public HashMap<String, String> getWordAttribs() {
		return wordAttribs;
	}

	private String msgKey="";
    
    
    
	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public void setWordAttribs(HashMap<String, String> wordAttribs) {
		this.wordAttribs = wordAttribs;
	}

	public String getSeatingEnabled() {
		return seatingEnabled;
	}

	public void setSeatingEnabled(String seatingEnabled) {
		this.seatingEnabled = seatingEnabled;
	}

	public String getVbEnabled() {
		return vbEnabled;
	}

	public void setVbEnabled(String vbEnabled) {
		this.vbEnabled = vbEnabled;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
		
	}
    public String execute() {
    	String curLvl=ActionContext.getContext().getParameters().get("curLvl").toString();
    	System.out.println("Current Level : "+curLvl+" & EventId : "+eid);
    	int RegWordCustomize = 200;
    	if(Integer.parseInt(curLvl)>=RegWordCustomize){
			seatingEnabled=EventDB.getConfigVal(eid, "event.seating.enabled", "No");
			String query="select count(*) from priority_list where eventid=?";
			count=DbUtil.getVal(query, new String[]{eid});
			HashMap<String, String> hm= new HashMap<String,String>();
			hm.put("module", "RegFlowWordings");
			wordAttribs= DisplayAttribsDB.getDisplayAttribs(hm, I18NCacheData.getI18NLanguage(eid), eid);//DisplayAttribsDB.getAttribValues(eid, "RegFlowWordings");
			//vbEnabled = VolumeTicketsDB.isVolumeTicketingEnabled(eid,"mgr.volumeticketing.enabled");	
			return "success";
    	}else
    		return "pageRedirect";
	}
	
	public String save(){
		DisplayAttribsDB.insertDisplayAttribs(eid, "RegFlowWordings", wordAttribs, true);		
		msgKey="success";
		EventHelperDB.removeGLobalEventCache(eid, "remove", "ticketsettings");
		
		return "ajaxmsg";
		/*return "ajaxstatusmsg";*/
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
