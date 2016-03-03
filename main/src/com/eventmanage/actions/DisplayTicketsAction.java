package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.event.dbhelpers.DisplayAttribsDB;
import com.event.dbhelpers.EventDB;
import com.event.helpers.I18n;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.eventbee.beans.Entity;
import com.eventbee.general.I18NCacheData;

public class DisplayTicketsAction  extends ActionSupport implements Preparable,ValidationAware{
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";
	private static String module="TicketDisplayOptions";
	private String msgType = "displaytickets";
	private HashMap<String,String> ticketDisplayOptionsMap=new HashMap<String,String>();
	private ArrayList alltickets=new ArrayList();
	private ArrayList formatTickets=new ArrayList();
	private ArrayList<Entity> formatList=new ArrayList<Entity>();
	private String displayFormat;
	private String formatId;
	private String dispFrmt="";
	private String dispId;
	private String jsonData="";	
	private String regtimeout="";
	
	public String getRegtimeout() {
		return regtimeout;
	}
	public void setRegtimeout(String regtimeout) {
		this.regtimeout = regtimeout;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getDispId() {
		return dispId;
	}
	public void setDispId(String dispId) {
		this.dispId = dispId;
	}
	public ArrayList getFormatList() {
		return formatList;
	}
	public void setFormatList(ArrayList formatList) {
		this.formatList = formatList;
	}
	public String getDispFrmt() {
		return dispFrmt;
	}
	public void setDispFrmt(String dispFrmt) {
		this.dispFrmt = dispFrmt;
	}
	
	public String getFormatId() {
		return formatId;
	}
	public void setFormatId(String formatId) {
		this.formatId = formatId;
	}
	public ArrayList getFormatTickets() {
		return formatTickets;
	}
	public void setFormatTickets(ArrayList formatTickets) {
		this.formatTickets = formatTickets;
	}
	
	public String getDisplayFormat() {
		return displayFormat;
	}
	public void setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
	}
	public HashMap<String, String> getTicketDisplayOptionsMap() {
		return ticketDisplayOptionsMap;
	}
	public void setTicketDisplayOptionsMap(
			HashMap<String, String> ticketDisplayOptionsMap) {
		this.ticketDisplayOptionsMap = ticketDisplayOptionsMap;
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
	public void populateEventTickets(){
		alltickets=DisplayAttribsDB.getEventTickets(eid);	
	}
	
	public String populateFormatData(){
		formatTickets=(ArrayList)DisplayAttribsDB.getFormatTickets(formatId,eid);	
		displayFormat = DisplayAttribsDB.getFormatData(formatId,eid);	
		populateEventTickets();
		return "popupcontent";
	}
	/*public void populateDisplayFormat(){
		formatList=DisplayAttribsDB.getDisplayFormat(eid);
	}*/
	public String execute(){
		populateDisplayFormatList();
		populateEventTickets();
		populateFormatData();
		HashMap<String, String> hm= new HashMap<String,String>();
		hm.put("module", module);
		ticketDisplayOptionsMap= DisplayAttribsDB.getDisplayAttribs(hm, I18NCacheData.getI18NLanguage(eid), eid);//DisplayAttribsDB.getAttribValues(eid, module);
		if("Display".equals(dispFrmt)){
			return "displayformat";
		}
		regtimeout=EventDB.getConfigVal(eid,"timeout","");
		if(regtimeout==null)regtimeout="";
		if(!"".equals(regtimeout)){
		try{
		if(Integer.parseInt(regtimeout)==4 || (Integer.parseInt(regtimeout)>=5 && Integer.parseInt(regtimeout)<=30))
			regtimeout=(Integer.parseInt(regtimeout)+1)+"";
		}catch(Exception e){
			System.out.println("Exception occured while setting regtimeout :: "+e.getMessage()+ " :: "+eid);
		}
		}else
			regtimeout="15";
		
		return "success";
	}
	
	public String populateDisplayFormatList(){		
		ArrayList<Entity> tctDspFrmList = DisplayAttribsDB.getDisplayFormat(eid);		
		JSONObject js= DisplayAttribsDB.getDisplayFormatJson(tctDspFrmList);
		jsonData=js.toString();	
		/*System.out.println("Formats::"+jsonData);*/
		return "displayFormatJson";		
	}
	
	public String save(){
		boolean status;
		status=validateDataRegTimeOut();
		if(status){
		DisplayAttribsDB.insertDisplayAttribs(eid, module, ticketDisplayOptionsMap, true);
		DisplayAttribsDB.updateRegistrationTimeOut(eid, regtimeout);
		return "updated";
		}else
			return "displayformaterror";
	}
	
	public void rebuildPopulateDisplayFormatList()
	{		
		ArrayList<Entity> tctDspFrmList = DisplayAttribsDB.getDisplayFormat(eid);		
		JSONObject js= DisplayAttribsDB.getDisplayFormatJson(tctDspFrmList);
		jsonData=js.toString();	
		/*System.out.println("Formats::"+jsonData);*/
		
	}
	
	
	
	public String saveformat(){
		if(validateDisplayFormat())
		{
			DisplayAttribsDB.insertDisplayFormat(eid,formatId,displayFormat,formatTickets);
			rebuildPopulateDisplayFormatList();
			return "displayFormatJson";
		}
		else 
			return "displayformaterror";
	}
	
	public String deleteDisplayFormat(){
		/*System.out.println("in delete"+formatId+"::"+eid);*/
		DisplayAttribsDB.deleteDisplayFormat(formatId,eid);
		populateDisplayFormatList();
		return "displayFormatJson";
	}
	
	public String displayFormatPopupContent(){
		populateEventTickets();
		return "popupcontent";
	}
	
	
	public boolean validateDisplayFormat(){
		ResourceBundle resourceBundle =I18n.getResourceBundle();
		if("".equals(displayFormat.trim())){
			addFieldError("displayFormat",resourceBundle.getString("dp.frmt.rqrd.lbl"));
		}
		if(!getFieldErrors().isEmpty()){
			return false;
		}
		return true;
	}
	
	public boolean validateDataRegTimeOut(){
		ResourceBundle resourceBundle =I18n.getResourceBundle();
		/*System.out.println("In validateDataRegTimeOut method::"+eid);*/
		if("".equals(regtimeout)){
			addFieldError("",resourceBundle.getString("dp.rgstrn.tme.out.shld.nt.emty.lbl"));
		}else{
		try{
		Integer.parseInt(regtimeout);
		if(Integer.parseInt(regtimeout)<5)
			addFieldError("",resourceBundle.getString("dp.rgstrn.grt.equl.fve.lbl"));
		else if(Integer.parseInt(regtimeout)>30)
			addFieldError("",resourceBundle.getString("dp.rgstrn.tme.out.less.eql.lbl"));
		}catch(Exception e){
			addFieldError("",resourceBundle.getString("dp.rgstrn.tme.out.numeric.lbl"));
		}}
		if(!getFieldErrors().isEmpty()){
			return false;
		}else
			return true;
		
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public ArrayList getAlltickets() {
		return alltickets;
	}

	public void setAlltickets(ArrayList alltickets) {
		this.alltickets = alltickets;
	}
	


}
