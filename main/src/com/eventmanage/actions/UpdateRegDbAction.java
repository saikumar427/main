package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Locale;
import java.util.HashMap;

import com.event.dbhelpers.TransactionDB;
import com.event.helpers.I18n;
import com.eventbee.general.DbUtil;
import com.eventregister.dbhelper.RegistrationDBHelper;
import com.eventregister.dbhelper.RegistrationTiketingManager;
import com.eventregister.dbhelper.RegistrationConfirmationEmail;
import com.eventregister.dbhelper.RegistrationProcessDB;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateRegDbAction extends ActionSupport{

	String eid="";
	String tid="";
	String eventdate="";
	String seatingenabled="";
	RegistrationProcessDB regdb=new RegistrationProcessDB();
	private String msgType = "addmanualattendee";
	private String pctype="";
	private String paytoken="";
	private String alreadydone="";
	
	public String getAlreadydone() {
		return alreadydone;
	}
	public void setAlreadydone(String alreadydone) {
		this.alreadydone = alreadydone;
	}
	public String getPaytoken() {
		return paytoken;
	}
	public void setPaytoken(String paytoken) {
		this.paytoken = paytoken;
	}
	public String getPctype() {
		return pctype;
	}
	public void setPctype(String pctype) {
		this.pctype = pctype;
	}
	public String getEid() {
		return eid;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
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
	public String getEventdate() {
		return eventdate;
	}
	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}
	public String getSeatingenabled() {
		return seatingenabled;
	}
	public void setSeatingenabled(String seatingenabled) {
		this.seatingenabled = seatingenabled;
	}
	public String execute(){
		ResourceBundle resourceBundle =I18n.getResourceBundle();

		
		/*if("YES".equals(seatingenabled))
			fillseatingstatus(tid, eid, eventdate);
		System.out.println("updateRegDbAction paymentcollection tid: "+tid+" type: "+pctype+" paytoken: "+paytoken);
		if("cc".equals(pctype)){
			String status = DbUtil.getVal("select status from eventbee_payments_submits where internalref=? and paytoken=? ", new String[]{tid,paytoken});
			if(!"success".equals(status)){
				addFieldError("addattendee", "Credit card process is failed. Please try again.");
				return "fail";
			}
		}
		updateRegDb();*/	
		
		if(!"yes".equals(alreadydone))
			alreadydone = DbUtil.getVal("select 'yes' from event_reg_transactions where eventid=? and tid=? ", new String[]{eid,tid});
		
		if(!"yes".equals(alreadydone)) alreadydone="no";
		
		System.out.println("##### updateRegDbAction execute() tid: "+tid+" pctype: "+pctype+" paytoken: "+paytoken+" alreadydone: "+alreadydone);
		
		if("yes".equals(alreadydone)){
			
		}else if("cc".equals(pctype)){

			String ccstatus = DbUtil.getVal("select status from eventbee_payments_submits where internalref=? and paytoken=? ", new String[]{tid,paytoken});
			System.out.println("##### updateRegDbAction execute() tid: "+tid+" ccstatus: "+ccstatus);
			if(!"success".equals(ccstatus)){
				addFieldError("addattendee",resourceBundle.getString("upd.crd.prcs.faild.plz.try.agn.lbl"));
				return "fail";
			}
			
		}else{
			updateRegDb();	
		}
		return "Success";
	}
	
	void updateRegDb(){
		System.out.println("##### updateRegDbAction updateRegDb() tid: "+tid+" pctype: "+pctype+" paytoken: "+paytoken);
		RegistrationConfirmationEmail regmail=new RegistrationConfirmationEmail();
		RegistrationTiketingManager regmgr=new RegistrationTiketingManager();
		regmgr.updatePaytype(tid, "confirmation page");
		
		if("nopayment".equals(pctype))
			regmgr.updateEbeeFeeInTemp(tid);
		
		if("cc".equals(pctype))
			regmgr.updatePaymentType(tid);
		else
			regmgr.updateCCVendor(tid,eid);
		regmgr.updateTax(tid,eid);
		int i=	regdb.InsertRegistrationDb(tid,eid);
		
		if("cc".equals(pctype) && !"".equals(paytoken))
			regdb.updateExtPayId(tid,paytoken);
		
		if(i>=1 && "paid".equals(pctype)){
			String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
			regdb.updateEbeeCredits(tid,eid,mgrId);
		}
		
		if("YES".equals(seatingenabled))
			regmgr.fillseatingstatus(tid, eid, eventdate);
		
		//int emailcount=regmail.sendRegistrationEmail(tid,eid);
		TransactionDB.resendingMail(eid,tid,"Ticketing");
		regmgr.deleteTempDetails(tid);
		regmgr.updateProfileStatus(tid);
	}
/*	public void fillseatingstatus(String tid,String eid,String eventdate){
		RegistrationDBHelper regdb=new RegistrationDBHelper();
		RegistrationTiketingManager regmgr=new RegistrationTiketingManager();
		ArrayList profileInfo=regdb.getallProfileInfo(tid,eid);
		ArrayList ticketids=regmgr.getTicketIds(eid);
		HashMap seatingdetails=regmgr.getSeatingDetails(eid, tid, eventdate, ticketids);
		if(profileInfo!=null&&profileInfo.size()>0){
			for(int i=0;i<profileInfo.size();i++){
				HashMap profile=(HashMap)profileInfo.get(i);
				String seatcode=(String)profile.get("seatCodes");
				String profilekey=(String)profile.get("profilekey");
				String ticketid=(String)profile.get("ticketid");
				try{
					if(!"".equals(seatcode)||seatcode!=null){
						ArrayList seatcodes_tktID=(ArrayList)seatingdetails.get(ticketid);
						ArrayList seatindex_tktID=(ArrayList)seatingdetails.get("seatindex_"+ticketid);
						int indexofseatcode=seatcodes_tktID.indexOf(seatcode);
						String seatindex_atseatcode=(String)seatindex_tktID.get(indexofseatcode);
						seatcodes_tktID.remove(indexofseatcode);
						seatindex_tktID.remove(indexofseatcode);
						HashMap seatinghm=(HashMap)seatingdetails.get(ticketid+"_"+seatindex_atseatcode+"_"+seatcode);
						seatinghm.put("eventid",eid);
						seatinghm.put("eventdate",eventdate);
						seatinghm.put("ticketid",ticketid);
						seatinghm.put("profilekey",profilekey);
						seatinghm.put("tid",tid);
						
						regdb.insertSeatBookingStatus(seatinghm);
					}
			}catch(Exception e){	
			}
			}
	}
	}
*/
}
