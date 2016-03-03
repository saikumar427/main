package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.EventHelperDB;
import com.event.dbhelpers.PriorityRegistrationDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class PriorityRegistrationAction extends ActionSupport implements Preparable,ValidationAware{

	private String eid="";
	private String uploadedevent="";
	private String mgrid="";
	private String ext="";
	private String jsonData="";	
	private String credentials="";
	private String uploadtype="";
	private String fieldcount="";
	private String firstLabel="";
	private String secondLabel="";
	private String msgType="authenticateuser";
	private ArrayList<Entity> priorityList=new ArrayList<Entity>();
	private ArrayList<Entity> alltickets=new ArrayList<Entity>();
	private String seltickets="";
	private String listId="";
	private String listName=""; 
	private String fieldexists="no";
	private String id="";
	private String password="";
	private String noOfFields="1";
	private boolean deleteold=false;
	private List selectedTickets;
	
	
	
	public List getSelectedTickets() {
		return selectedTickets;
	}
	public void setSelectedTickets(List selectedTickets) {
		this.selectedTickets = selectedTickets;
	}
	public boolean isDeleteold() {
		return deleteold;
	}
	public void setDeleteold(boolean deleteold) {
		this.deleteold = deleteold;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	public String getFieldexists() {
		return fieldexists;
	}
	public void setFieldexists(String fieldexists) {
		this.fieldexists = fieldexists;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getFirstLabel() {
		return firstLabel;
	}
	public void setFirstLabel(String firstLabel) {
		this.firstLabel = firstLabel;
	}
	public String getSecondLabel() {
		return secondLabel;
	}
	public void setSecondLabel(String secondLabel) {
		this.secondLabel = secondLabel;
	}
public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getSeltickets() {
		return seltickets;
	}
	public void setSeltickets(String seltickets) {
		this.seltickets = seltickets;
	}
	public ArrayList<Entity> getAlltickets() {
		return alltickets;
	}
	public void setAlltickets(ArrayList<Entity> alltickets) {
		this.alltickets = alltickets;
	}
public ArrayList<Entity> getPriorityList() {
		return priorityList;
	}
	public void setPriorityList(ArrayList<Entity> priorityList) {
		this.priorityList = priorityList;
	}
public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
public String getFieldcount() {
		return fieldcount;
	}
	public void setFieldcount(String fieldcount) {
		this.fieldcount = fieldcount;
	}
public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
public String getMgrid() {
		return mgrid;
	}
	public void setMgrid(String mgrid) {
		this.mgrid = mgrid;
	}
public String getUploadedevent() {
		return uploadedevent;
	}
	public void setUploadedevent(String uploadedevent) {
		this.uploadedevent = uploadedevent;
	}
public String getEid() {
		return eid;
	}
public void setEid(String eid) {
	this.eid = eid;
}

public String getNoOfFields() {
	return noOfFields;
}
public void setNoOfFields(String noOfFields) {
	this.noOfFields = noOfFields;
}
public void prepare(){
	
}
	
	
public String execute(){
	System.out.println("in authenticate user action class.."+eid);
	fieldexists=PriorityRegistrationDB.getPriorityexists(eid);
	//priorityList=PriorityRegistrationDB.getPriorityList(eid);
	
	jsonData=PriorityRegistrationDB.getPriorityList(eid);
	
	mgrid=ActionContext.getContext().getParameters().get("mgrId").toString();
return "success";
}
public String getPriorityListData(){
	System.out.println("getPriorityListData::");
	JSONObject proObj=new JSONObject();
	proObj=PriorityRegistrationDB.getPriorityListData(eid,listId);
	jsonData=proObj.toString();
	return "jsonData";
}
public String getEditPriorityListData(){
	System.out.println("getEditPriorityListData::");
	alltickets=EventDB.getEventTicketsForDiscounts(eid);
	JSONObject proObj=new JSONObject();
	proObj=PriorityRegistrationDB.getEditPriorityListData(eid,listId);
	try {
		listId=proObj.getString("listid");
		listName=proObj.getString("listname");
		firstLabel=proObj.getString("field1");
		secondLabel=proObj.getString("field2");		
		seltickets=proObj.getString("tickets");		
		selectedTickets = Arrays.asList(seltickets.split("\\s*,\\s*"));
		noOfFields=proObj.getString("nooffields");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	jsonData=proObj.toString();
	return "createlist";
}
public String createPriorityList(){
	this.firstLabel="";
	this.listId="";
	this.seltickets="";
	this.listName="";
	this.secondLabel="";
	this.noOfFields="1";
	alltickets=EventDB.getEventTicketsForDiscounts(eid);
	return "createlist";
}



public String savePriorityList(){
	
	String listExists="";
	
	
	
	/*listExists=DbUtil.getVal("select 'y' from priority_list where eventid=?::bigint and 	list_name=?",new String[]{eid,listName});
	System.out.println(listExists+":::list exisrs with name");*/
	
	
	String listidexists=DbUtil.getVal("select list_id from priority_list where eventid=?::bigint and list_name=?",new String[]{eid,listName});
	if(!"".equals(listidexists) && listidexists!=null)
	{
		if(listId.equals(listidexists))
			listExists="N";
		else
			listExists="y";
	}
	else 
		listExists="N";
		
	
	
	
	
	if("y".equals(listExists))
	{
		JSONObject finalObj=new JSONObject();
		try{
		finalObj.put("status","failure");
		}catch(Exception e){}
		jsonData=finalObj.toString();
	}
	else
	{
		PriorityRegistrationDB.saveListData(eid,listId,seltickets,listName,firstLabel,secondLabel,noOfFields);
		if(deleteold)
			PriorityRegistrationDB.deleteOldRecords(listId,eid);
		jsonData=PriorityRegistrationDB.getPriorityList(eid).toString();
		PriorityRegistrationDB.setPriorityConfiguration(eid);
	}
	return "jsonData";
}

public String savePriorityListRecords(){
	boolean idDuplicateExists=duplicateRecords(credentials);
	if(!idDuplicateExists){
	PriorityRegistrationDB.savePriorityListRecords(eid,credentials,listId);
	jsonData=PriorityRegistrationDB.getPriorityList(eid);
	}
	else
	{
		JSONObject finalObj=new JSONObject();
		try{
		finalObj.put("status","failure");
		}catch(Exception e){}
		jsonData=finalObj.toString();
	}
	return "jsonData";
}


public boolean duplicateRecords(String jsonRecords)
{
	boolean flag=false;
	List<String> list = new ArrayList<String>();
	try
	{
		JSONArray jsonArray=new JSONArray(jsonRecords);
		for(int i=0;i<jsonArray.length();i++)
		{
			JSONObject js=(JSONObject)jsonArray.get(i);
			list.add(js.getString("userid"));
		}
		
		Set<String> uniqueSet = new HashSet<String>(list);
		for (String temp : uniqueSet) {
			if(Collections.frequency(list, temp)>1)
			{
				flag=true;
			}
		}
		
		//if(i!=jsonArray.length()-1){}
	}catch(Exception e)
	{
		
	}
	return flag;
}

public String deletePriorityList(){
	System.out.println("deletePriorityList::");
		PriorityRegistrationDB.deleteLoadedData(listId,eid);
		jsonData=PriorityRegistrationDB.getPriorityList(eid);
		try {
			JSONObject json=new JSONObject(jsonData);
			json.getJSONArray("lists").length();
			if(json.getJSONArray("lists").length()==0){
				PriorityRegistrationDB.removePriorityConfiguration(eid);
				EventHelperDB.removeGLobalEventCache(eid, "remove", "eventinfo");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	return "jsonData";
}




public String getUploadtype() {
	System.out.println("getUploadtype::");
	return uploadtype;
}
public void setUploadtype(String uploadtype) {
	System.out.println("setUploadtype::");
	this.uploadtype = uploadtype;
}

public String saveXlformat(){
	System.out.println("saveXlformat::");
ArrayList<HashMap<String,String>> memberData=PriorityRegistrationDB.saveXLData(eid,mgrid,listId,ext);	
JSONObject jsonObj=new JSONObject();
try{
	jsonObj.put("data", memberData);
	jsonObj.put("field1",DbUtil.getVal("select field1 from priority_list where eventid=?::bigint and list_id=?",new String[]{eid,listId}));
	jsonObj.put("field2",DbUtil.getVal("select field2 from priority_list where eventid=?::bigint and list_id=?",new String[]{eid,listId}));
	jsonData=jsonObj.toString();
}catch(Exception e){
}
return "addrecords";
}

public String addOrEditRecords(){
	System.out.println("addOrEditRecords eid: "+eid+"...."+listId);
	JSONObject jsonObj=new JSONObject();
	ArrayList<HashMap<String,String>> memberData=PriorityRegistrationDB.getMemberRecords(eid,listId);
	//noOfFields=PriorityRegistrationDB.getNoOfFields(eid);
	try{
		jsonObj.put("data", memberData);
		jsonData=jsonObj.toString();
	}catch(Exception e){
	}
	return "addrecords";
}





}
