package com.eventmanage.actions.customization;

import java.util.ArrayList;
import java.util.HashMap;

import com.event.dbhelpers.DisplayAttribsDB;
import com.event.dbhelpers.EvtCustomizationDB;
import com.event.helpers.DisplayAttribsHelper;
import com.event.helpers.EvtCustomizationHelper;
import com.eventbee.beans.Entity;
import com.eventbee.general.dbhelpers.PreviewDBHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class EventCustomizationAction extends ActionSupport implements Preparable{

	private static final long serialVersionUID = 2109959509959076001L;
	//private ArrayList<Entity> fontTypes = new ArrayList<Entity>();
	//private ArrayList<Entity> fontSizes = new ArrayList<Entity>();
	private String eid = "";
	private HashMap<String,String> themeAttribs=new HashMap<String,String>();
	private static String module="ThemePage";
	private String msgType = "customization";
	//private String previewFileName="";
	
	/*public String getPreviewFileName() {
		return previewFileName;
	}

	public void setPreviewFileName(String previewFileName) {
		this.previewFileName = previewFileName;
	}*/

	public HashMap<String, String> getThemeAttribs() {
		return themeAttribs;
	}

	public void setThemeAttribs(HashMap<String, String> themeAttribs) {
		this.themeAttribs = themeAttribs;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	/*public ArrayList<Entity> getFontSizes() {
		return fontSizes;
	}

	public void setFontSizes(ArrayList<Entity> fontSizes) {
		this.fontSizes = fontSizes;
	}

	public ArrayList<Entity> getFontTypes() {
		return fontTypes;
	}

	public void setFontTypes(ArrayList<Entity> fontTypes) {
		this.fontTypes = fontTypes;
	}

	public void fillFontTypes() {
		fontTypes = EvtCustomizationHelper.fillFonts();
	}

	public void fillFontSizes() {
		fontSizes = EvtCustomizationHelper.fillFontSizes();
	}*/

	/*public String execute() {
		
		fillFontTypes();
		fillFontSizes();
		themeAttribs= DisplayAttribsDB.getAttribValues(eid, module);
		// System.out.println("attribs Map"+themeAttribs);
		return "input";
	}*/
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String save(){
		//fillFontTypes();
		//fillFontSizes();
		//DisplayAttribsHelper.processLookandFeelData(themeAttribs);
		DisplayAttribsDB.insertDisplayAttribs(eid, module, themeAttribs, true);
		//Generate HTML and CSS files using the entered values.
		HashMap<String,String> lnfcssMap=DisplayAttribsHelper.processLandFDataForCSS(themeAttribs);
		String CSSContent=EvtCustomizationHelper.generateCSSString(lnfcssMap,"event",getEid());
		EvtCustomizationDB.insertThemeData(eid, "event", themeAttribs,CSSContent);
		HashMap<String,String> HandFooterMap=DisplayAttribsHelper.processHandFooterData(themeAttribs);
		EvtCustomizationDB.insertHandFData(eid,module,HandFooterMap);		
		EvtCustomizationDB.updateConfigVal(eid,"Custom Look & Feel");
		return SUCCESS;
	}

	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String preview(){
		DisplayAttribsHelper.processLookandFeelData(themeAttribs);
		HashMap<String,String> lnfcssMap=DisplayAttribsHelper.processLandFDataForCSS(themeAttribs);
		String CSSContent=EvtCustomizationHelper.generateCSSString(lnfcssMap,"event",getEid());
		HashMap<String, String> previewMap=PreviewDBHelper.getPreviewCssAndHtml(eid, "", "","");
		HashMap<String,String> HandFooterMap=DisplayAttribsHelper.processHandFooterData(themeAttribs);
		String headerHTML=HandFooterMap.get("HEADER_HTML");
		String footerHTML=HandFooterMap.get("FOOTER_HTML");
		/*if("".equals(footerHTML)){
			footerHTML=PreviewDBHelper.getDefaultFooterHTML();
		}*/
		String HTMLContent=previewMap.get("HTML");
		if(!"Default".equals(headerHTML))
			HTMLContent=HTMLContent.replace("$eventbeeHeader", headerHTML);
		if(!"Default".equals(footerHTML))
			HTMLContent=HTMLContent.replace("$eventbeeFooter", footerHTML);
			
		PreviewDBHelper.updatePreviewTheme(eid, CSSContent,HTMLContent , "", "");
		//String previewData=PreviewHelper.processPreviewData(CSSContent);
		//Random rand = new Random();
	   // int num = rand.nextInt(99999);
	  //  previewFileName=eid+"_"+num;
	   //	FileWriterHelper.doWrite(previewFileName, previewData);
		return "preview";
	}

	

}
