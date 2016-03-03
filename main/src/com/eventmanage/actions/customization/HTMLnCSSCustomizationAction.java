package com.eventmanage.actions.customization;

import java.util.HashMap;

import com.event.dbhelpers.EvtCustomizationDB;
import com.event.dbhelpers.HTMLnCSSCustomizationDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.eventbee.general.dbhelpers.PreviewDBHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HTMLnCSSCustomizationAction extends ActionSupport{

	private static final long serialVersionUID = 6771691530950834858L;
	private String eid="";
	private HashMap<String,String> attribMap=new HashMap<String,String>();
	String module="event";
	String mgrId="";      
	private String msgType = "htmlncss";
	//private String previewFileName="";
	private String powertype="";
	
	
	public String getPowertype() {
		return powertype;
	}
	public void setPowertype(String powertype) {
		this.powertype = powertype;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	/*public String getPreviewFileName() {
		return previewFileName;
	}
	public void setPreviewFileName(String previewFileName) {
		this.previewFileName = previewFileName;
	}*/
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public void fillHTMLnCSS(){
		attribMap=HTMLnCSSCustomizationDB.getHTMLnCSS(eid,module,mgrId);
	}
	public String execute(){
		fillHTMLnCSS();
		return INPUT;
	}
	/**
	 * @param attribMap the attribMap to set
	 */
	public void setAttribMap(HashMap<String,String> attribMap) {
		this.attribMap = attribMap;
	}

	/**
	 * @return the attribMap
	 */
	public HashMap<String,String> getAttribMap() {
		return attribMap;
	}
	public String save(){
		// adding for special fee start
		String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
		if(powertype.equals("RSVP"))
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP HTML & CSS");
		else
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"300","HTML & CSS");
		// special fee end.
		HTMLnCSSCustomizationDB.saveHTMLnCSS(eid,"event", attribMap);
		EvtCustomizationDB.updateConfigVal(eid,"Custom HTML & CSS");
		return SUCCESS;
	}

	
	public String preview(){
		//String CSS=attribMap.get("CSS");
		PreviewDBHelper.updatePreviewTheme(eid, attribMap.get("CSS"), attribMap.get("HTML"), "", "");
		//String previewData=PreviewHelper.processPreviewData(CSS);
		//Random rand = new Random();
	  //  int num = rand.nextInt(99999);
	  //  previewFileName=eid+"_"+num;
	   //	FileWriterHelper.doWrite(previewFileName, previewData);
		return "preview";
	}
}
