package com.eventmanage.actions;

import java.util.HashMap;
import java.util.Vector;

import com.eventregister.dbhelper.ProfilePageVm;
import com.opensymphony.xwork2.ActionSupport;

public class ProfileVmDataAction extends ActionSupport{
	
	String eid="";
	String tid="";
	HashMap profileMap=null;
	HashMap<String,Integer>TicketsQtyMap=new HashMap();
	HashMap<String,String>baseProfileTickets=new HashMap<String,String>();
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String execute(){
		System.out.println("in execute");
	getProfilePageVm();		
	return "Success";	
	}
	
	public HashMap getProfileMap() {
		return profileMap;
	}
	public void setProfileMap(HashMap profileMap) {
		this.profileMap = profileMap;
	}
	void getProfilePageVm(){
		ProfilePageVm profilevm=new ProfilePageVm();
		profileMap=profilevm.getProfilePageVmObjects(tid,eid);	
		Vector CustomVec=(Vector)profileMap.get("customProfile");
		if(CustomVec!=null){
		for(int i=0;i<CustomVec.size();i++){
		HashMap hm=(HashMap)CustomVec.get(i);
		String ticketid=(String)hm.get("selectedTicket");
		String qty=(String)hm.get("qty");
		Vector<HashMap> questions=(Vector)hm.get("questions");
		TicketsQtyMap.put(ticketid,Integer.parseInt(qty));
		if(questions!=null&&questions.size()>0){
			for(int p=0;p<questions.size();p++){
			HashMap<String,String> qmap=questions.get(p);
			String qid=qmap.get("qId");	
			if("fname".equals(qid)||"lname".equals(qid))
		    baseProfileTickets.put(ticketid,"Y");
			}
		}
		}
		}	
		
	}
	public HashMap<String, String> getBaseProfileTickets() {
		return baseProfileTickets;
	}
	public void setBaseProfileTickets(HashMap<String, String> baseProfileTickets) {
		this.baseProfileTickets = baseProfileTickets;
	}
	public HashMap<String, Integer> getTicketsQtyMap() {
		return TicketsQtyMap;
	}
	public void setTicketsQtyMap(HashMap<String, Integer> ticketsQtyMap) {
		TicketsQtyMap = ticketsQtyMap;
	}
	
	}
	
	
	
	
	
	

