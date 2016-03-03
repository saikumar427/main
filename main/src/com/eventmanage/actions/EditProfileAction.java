package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventregister.customquestions.dbhelper.CustomAttribsDB;
import com.eventregister.dbhelper.ProfilePageVm;
import com.eventregister.dbhelper.RegistrationTiketingManager;
import com.opensymphony.xwork2.ActionSupport;

public class EditProfileAction extends ActionSupport{
	String ticketid="";
	String eid="";
	ArrayList questionslist=null;
	String pid="";
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public ArrayList getQuestionslist() {
		return questionslist;
	}
	public void setQuestionslist(ArrayList questionslist) {
		this.questionslist = questionslist;
	}
	public String getTicketid() {
		return ticketid;
	}
	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}

	public String execute(){
		
	getprofileMap();
	return "success";	
	}
void getprofileMap(){
	RegistrationTiketingManager regTktMgr=new RegistrationTiketingManager();
	CustomAttribsDB ticketcustomattribs=new CustomAttribsDB();
	ArrayList ticketlevelbaseProfiles=regTktMgr.getTicketIdsForBaseProfiles(eid);
	HashMap ticketspecificAttributeIds=ticketcustomattribs.getTicketLevelAttributes(eid);
	ArrayList attribids=(ArrayList)ticketspecificAttributeIds.get(ticketid);
	ProfilePageVm pvm=new ProfilePageVm();
	HashMap hm=pvm.getAttribsForTickets(ticketid,eid);
	questionslist=new ArrayList();
	if(hm!=null){
	//if(hm.containsKey("fname"))
	questionslist.add("fname");
	//if(hm.containsKey("lname"))
	questionslist.add("lname");
	if(hm.containsKey("email"))
	questionslist.add("email");
	if(hm.containsKey("phone"))
	questionslist.add("phone");
	}
	if(attribids!=null)
	questionslist.addAll(attribids);	

}
public String buyerProfile(){
	RegistrationTiketingManager regTktMgr=new RegistrationTiketingManager();
	ProfilePageVm pvm=new ProfilePageVm();
	HashMap hm=pvm.getAttribsForBuyer(eid);
	ArrayList buyerAttribs=regTktMgr.getBuyerSpecificAttribs(eid);		
	questionslist=new ArrayList();
	questionslist.add("fname");
	questionslist.add("lname");
	questionslist.add("email");
	//if(hm!=null){	
	//if(hm.containsKey("phone"))
	questionslist.add("phone");
	//}	
	if(buyerAttribs!=null) questionslist.addAll(buyerAttribs);	
	
return "buyerprofile";
}
}
