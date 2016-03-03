package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.event.beans.EventData;
import com.event.beans.ScanIdsData;
import com.event.dbhelpers.EmailAttendeesDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.ScanIdsDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.TrackURLDB;
import com.eventbee.general.DbUtil;
import com.eventbee.general.ProfileValidator;
import com.eventbee.namedquery.NQDbUtil;
import com.eventmanage.helpers.ScanIdJSONBuilderHelper;
import com.eventmanage.helpers.TrackURLJSONBuilderHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class ScanIDsAction  extends ActionSupport implements Preparable,ValidationAware{

	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";
	private String scanid="";   
	private String jsonData="";
	private String msgKey="";
	private String name="";
	private ArrayList<ScanIdsData> scanIDsList=new ArrayList<ScanIdsData>();
	private ScanIdsData scanIdsData;
	private String powertype="";
	public ScanIdsData getScanIdsData() {
		return scanIdsData;
	}
	public void setScanIdsData(ScanIdsData scanIdsData) {
		this.scanIdsData = scanIdsData;
	}
	
	public ArrayList<ScanIdsData> getScanIDsList() {
		return scanIDsList;
	}
	public void setScanIDsList(ArrayList<ScanIdsData> scanIDsList) {
		this.scanIDsList = scanIDsList;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String execute(){
		//scanIDsList=ScanIdsDB.getScanIds(eid);
		return "success";
	}
	public String createScanId(){
		return "scanidpage";
	}
	public String insertScanID(){
		ProfileValidator pv=new ProfileValidator();
		if(pv.checkNameValidity(name,true)){
			String namecheck=name.toLowerCase();
			String alreadyexists=DbUtil.getVal("select 'yes' from event_scanners where lower(scanid)=? and eventid=?",new String[]{namecheck,eid});
			if(!"yes".equals(alreadyexists)){
				EventData edata=EventDB.getEventData(eid);
				// adding for special fee start
				String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
				System.out.println("Scan IDs Powertype: "+edata.getPowertype());
				if(edata.getPowertype().equals("RSVP"))
					SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP Scan IDs");
				else
					SpecialFeeDB.chekingSpecialFee(eid,mgrId,"300","Scan IDs");
				// special fee end.
				
				ScanIdsDB.insertScanId(eid,name);				
			}else	msgKey="Name Exists";
		}else{
			msgKey="spacesInUrl";
		}
		return "ajaxmsg";
	}
	public String populateScanIdsList(){
		scanIDsList=ScanIdsDB.getScanIds(eid);
		JSONObject js=ScanIdJSONBuilderHelper.getScanIdsListJson(scanIDsList,scanIdsData);
		jsonData=js.toString();	
		return "jsondata";
	}
	
	public String reloadedData(){
		populateScanIdsList();
		return "reloadedData";
	}
	
	
	
	
	public String getPowertype() {
		return powertype;
	}
	public void setPowertype(String powertype) {
		this.powertype = powertype;
	}
	
	public String deleteScan()
	{
		boolean status = ScanIdsDB.deleteScan(scanid,eid);
		return reloadedData();
	}
	public String getScanid() {
		return scanid;
	}
	public void setScanid(String scanid) {
		this.scanid = scanid;
	}
	
	
	
	
	
}
