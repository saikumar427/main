package com.eventmanage.actions;



import com.eventbee.beans.FacebookConnectData;
import com.eventbee.general.DbUtil;
import com.event.beans.EventData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.FacebookConnectDB;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
public class PublishtoFacebookAction  extends ActionSupport implements Preparable,ValidationAware{
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";
	boolean alreadyPublished=false;
	private FacebookConnectData facebookConnectData=new FacebookConnectData();
	private String fname="";
	private String lname="";
	private String fbuid="";
	private String msgKey="";
	private String jsonData="";
	private EventData edata;
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getFbuid() {
		return fbuid;
	}
	public void setFbuid(String fbuid) {
		this.fbuid = fbuid;
	}
	public boolean isAlreadyPublished() {
		return alreadyPublished;
	}
	public void setAlreadyPublished(boolean alreadyPublished) {
		this.alreadyPublished = alreadyPublished;
	}
	public FacebookConnectData getFacebookConnectData() {
		return facebookConnectData;
	}
	public void setFacebookConnectData(FacebookConnectData facebookConnectData) {
		this.facebookConnectData = facebookConnectData;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String execute(){
		facebookConnectData.setFbApiKey(FacebookConnectDB.getFBApiKey(eid));
		String fbeventid=FacebookConnectDB.getFBEventId(eid);
		facebookConnectData.setFbeventid(fbeventid);
		facebookConnectData.setFbAppId(FacebookConnectDB.getFBAppId(eid));
		if(fbeventid!=null) alreadyPublished=true;
		facebookConnectData.setAlreadyPublished(alreadyPublished);
		return SUCCESS;
	}
	public String getbeeidofFBuser(){
		String ebeeid=DbUtil.getVal("select ebeeid from ebee_fb_link where fbid=?",new String[]{fbuid});
		if(ebeeid==null){
			String authid=DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});		
			DbUtil.executeUpdateQuery("insert into  ebee_fb_link(fbid,ebeeid,created_at) values(?,?,now())",new String[]{fbuid,authid});
		}
		return SUCCESS;
	}
	public String getEventDetails(){
		edata=EventDB.getEventData(eid);
		String desc=FacebookConnectDB.getEventDescription(eid);
		String sdate=edata.getStart_date()+" "+edata.getStarttime();
		String edate=edata.getEnd_date()+" "+edata.getEndtime();
		edata.setStart_date(sdate);
		edata.setEnd_date(edate);
		edata.setDescription(desc);
		return "eventinfo";
	}
	public String saveFBEid(){
		FacebookConnectDB.saveFBId(eid, facebookConnectData);
		msgKey="success";
		return "ajaxmsg";
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public EventData getEdata() {
		return edata;
	}
	public void setEdata(EventData edata) {
		this.edata = edata;
	}

}
