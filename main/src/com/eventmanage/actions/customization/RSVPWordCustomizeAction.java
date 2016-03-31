package com.eventmanage.actions.customization;

import java.util.HashMap;

import com.event.dbhelpers.DisplayAttribsDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.eventbee.general.I18NCacheData;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

	public class RSVPWordCustomizeAction extends ActionSupport implements Preparable,ValidationAware{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String eid="";
		private String msgType = "RSVPword";
	    private HashMap<String,String> wordAttribs=new HashMap<String,String>();
	    
		public String getEid() {
			return eid;
		}

		public void setEid(String eid) {
			this.eid = eid;
		}

		public String getMsgType() {
			return msgType;
		}

		public void setMsgType(String msgType) {
			this.msgType = msgType;
		}

		public HashMap<String, String> getWordAttribs() {
			return wordAttribs;
		}

		public void setWordAttribs(HashMap<String, String> wordAttribs) {
			this.wordAttribs = wordAttribs;
		}
		public String execute(){
			String curLvl=ActionContext.getContext().getParameters().get("curLvl").toString();
	    	System.out.println("Current Level : "+curLvl+" & EventId : "+eid);
	    	int RSVPRegWordCustomize = 150;
	    	if(Integer.parseInt(curLvl)>=RSVPRegWordCustomize){
				HashMap<String, String> hm= new HashMap<String,String>();
				hm.put("module", "RSVPFlowWordings");
				wordAttribs= DisplayAttribsDB.getDisplayAttribs(hm, I18NCacheData.getI18NLanguage(eid), eid);//DisplayAttribsDB.getAttribValues(eid, "RSVPFlowWordings");
				return "success";
	    	}else
	    		return "pageRedirect";
		}
		public String save(){
			String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVPFlowWordings");
			DisplayAttribsDB.insertDisplayAttribs(eid, "RSVPFlowWordings", wordAttribs, true);
			return "ajaxstatusmsg";
		}
		@Override
		public void prepare() throws Exception {
			// TODO Auto-generated method stub
			
		}

}
