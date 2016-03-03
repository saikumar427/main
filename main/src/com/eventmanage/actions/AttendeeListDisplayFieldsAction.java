package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import com.event.dbhelpers.CustomAttributesDB;
import com.eventbee.beans.Entity;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class AttendeeListDisplayFieldsAction extends ActionSupport implements ValidationAware,Preparable{
	private static Logger log = Logger.getLogger(AttendeeListDisplayFieldsAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 3221676672629048961L;
	private String eid = "";
	private ArrayList<Entity> questions=new ArrayList<Entity>();
	private ArrayList<Entity> attendeeQuestions=new ArrayList<Entity>();
	private List<Entity> optionsList=new ArrayList<Entity>();
	private String refType="EVENT";
	private ArrayList<Entity> attribs=new ArrayList<Entity>();
	private ArrayList<Entity> selattribs=new ArrayList<Entity>();
	//private ArrayList<Entity> voltickets=new ArrayList<Entity>();
	//private ArrayList<Entity> selvoltickets=new ArrayList<Entity>();
	private String purpose="";
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	/*public ArrayList<Entity> getVoltickets() {
		return voltickets;
	}
	public void setVoltickets(ArrayList<Entity> voltickets) {
		this.voltickets = voltickets;
	}
	public ArrayList<Entity> getSelvoltickets() {
		return selvoltickets;
	}
	public void setSelvoltickets(ArrayList<Entity> selvoltickets) {
		this.selvoltickets = selvoltickets;
	}*/
	public List<Entity> getOptionsList() {
		return optionsList;
	}
	public void setOptionsList(List<Entity> optionsList) {
		this.optionsList = optionsList;
	}
	public List<Entity> getAttendeeQuestions() {
		return attendeeQuestions;
	}
	public ArrayList<Entity> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<Entity> questions) {
		this.questions = questions;
	}
	public void setAttendeeQuestions(ArrayList<Entity> attendeeQuestions) {
		this.attendeeQuestions = attendeeQuestions;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public ArrayList<Entity> getAttribs() {
		return attribs;
	}
	public void setAttribs(ArrayList<Entity> attribs) {
		this.attribs = attribs;
	}
	
	
	public ArrayList<Entity> getSelattribs() {
		return selattribs;
	}
	public void setSelattribs(ArrayList<Entity> selattribs) {
		this.selattribs = selattribs;
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String displayInformation(){
		try {			
			questions = CustomAttributesDB.getAttributes(eid,refType);
			attendeeQuestions = CustomAttributesDB.getAttendeeAttributes(eid);
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return "displayinfo";
	}
	public String saveDisplayInformation(){
		try {
			CustomAttributesDB cdb = new CustomAttributesDB();
			cdb.saveAttendeeAttribs(eid,optionsList,refType);
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return "ajaxmsg";
	}
	public String customAttribs(){
		attribs=CustomAttributesDB.getAttribs(eid,purpose);
		selattribs=CustomAttributesDB.getAddedAttribs(eid,purpose);
		return "confirmscreenattribs";
	}
	public String saveAttribs(){
		//CustomAttributesDB.saveAttribs(eid, optionsList,defaultquestions);
		return "ajaxmsg";
	}
	
	/*public String volumeTickets(){
		HashMap<String, ArrayList<Entity>> allvolumetickets = CustomAttributesDB.getVolumeTickets(eid);
		voltickets=allvolumetickets.get("voltickets");
		selvoltickets=allvolumetickets.get("selvoltickets");
		return "volumetickets";
	}
	public String savevolumeTickets(){
		CustomAttributesDB.saveVolumeTickets(eid, optionsList);
		return "ajaxmsg";
	}*/
	
}
