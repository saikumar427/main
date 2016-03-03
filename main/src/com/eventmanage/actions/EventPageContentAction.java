package com.eventmanage.actions;



import java.util.ArrayList;

import com.event.beans.EventPageContentData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.EventPageContentDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.VolumeTicketsDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.payments.dbhelpers.CardProcessorDB;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class EventPageContentAction extends ActionSupport implements Preparable,ValidationAware{
	private static final long serialVersionUID = -3773514102198232135L;
	private String eid="";
	private EventPageContentData eventPageContentData;
	private String configid="";
	private String imageurl="";	
	private String message="";
	private String image="";
	private String password="";
	private String eventpageattendeeshow="";
	boolean showeventpageattendeelist = false;
	boolean showconfirmscrnquestions=false;
	private String googlemapshow="";
	boolean showgooglemap = false;
	private String promotionsshow="";
	boolean showpromotions = false;
	private String msgKey = "";
	private String eventphotoURL="";
	private String uploadurl="";
	private String eventexternalphotoURL="";
	private String photowidth="";
	private ArrayList socialnwchkbox = new ArrayList();
	ArrayList<Entity> configValues = new ArrayList<Entity>();
	public String getFbShareConfirmation() {
		return fbShareConfirmation;
	}
	public void setFbShareConfirmation(String fbShareConfirmation) {
		this.fbShareConfirmation = fbShareConfirmation;
	}
	public String getFbLoginduringRegistration() {
		return fbLoginduringRegistration;
	}
	public void setFbLoginduringRegistration(String fbLoginduringRegistration) {
		this.fbLoginduringRegistration = fbLoginduringRegistration;
	}
	public String getPromotions() {
		return promotions;
	}
	public void setPromotions(String promotions) {
		this.promotions = promotions;
	}
	private String facebookeventid="";
	private String fanpageid="";
	private String ntsEnable="";
	private String regloginpopup="";
	private String mgrId="";
	private String msgType = "eventpagecontent";
	private String powertype="";
	private String currentLevel="";
	private String currentFee="";
	private String socialpromotionsshow="";
	boolean showsocialpromotions=false;
	boolean showvoltickets=false;
	private String volticketsshow="";
	private int volumeTicketCount=0;
	private String serveraddress="";
	private String eventLogoURL="";
	private ArrayList promosettings = new ArrayList();
	ArrayList<Entity> configValuesofpromotions = new ArrayList<Entity>();
	private String fbShareConfirmation="";
	private String fbLoginduringRegistration="";
	private String promotions="";
	
	public String getImgtype() {
		return imgtype;
	}
	public void setImgtype(String imgtype) {
		this.imgtype = imgtype;
	}
	private String imgtype="";
	//boolean showTwitter = false;
	
	public boolean isShowvoltickets() {
		return showvoltickets;
	}
	public void setShowvoltickets(boolean showvoltickets) {
		this.showvoltickets = showvoltickets;
	}
	public String getVolticketsshow() {
		return volticketsshow;
	}
	public void setVolticketsshow(String volticketsshow) {
		this.volticketsshow = volticketsshow;
	}
	public int getVolumeTicketCount() {
		return volumeTicketCount;
	}
	public void setVolumeTicketCount(int volumeTicketCount) {
		this.volumeTicketCount = volumeTicketCount;
	}
	public String getPowertype() {
		return powertype;
	}
	public void setPowertype(String powertype) {
		this.powertype = powertype;
	}
	public String getFanpageid() {
		return fanpageid;
	}
	public void setFanpageid(String fanpageid) {
		this.fanpageid = fanpageid;
	}
	public String getPromotionsshow() {
		return promotionsshow;
	}
	public void setPromotionsshow(String promotionsshow) {
		this.promotionsshow = promotionsshow;
	}
	public boolean isShowpromotions() {
		return showpromotions;
	}
	public void setShowpromotions(boolean showpromotions) {
		this.showpromotions = showpromotions;
	}
	public String getPhotowidth() {
		return photowidth;
	}
	public void setPhotowidth(String photowidth) {
		this.photowidth = photowidth;
	}
	public String getEventexternalphotoURL() {
		return eventexternalphotoURL;
	}
	public void setEventexternalphotoURL(String eventexternalphotoURL) {
		this.eventexternalphotoURL = eventexternalphotoURL;
	}
	public String getUploadurl() {
		return uploadurl;
	}
	public void setUploadurl(String uploadurl) {
		this.uploadurl = uploadurl;
	}
	public String getEventphotoURL() {
		return eventphotoURL;
	}
	public void setEventphotoURL(String eventphotoURL) {
		this.eventphotoURL = eventphotoURL;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGooglemapshow() {
		return googlemapshow;
	}
	public void setGooglemapshow(String googlemapshow) {
		this.googlemapshow = googlemapshow;
	}
	public boolean isShowgooglemap() {
		return showgooglemap;
	}
	public void setShowgooglemap(boolean showgooglemap) {
		this.showgooglemap = showgooglemap;
	}
	private String val="";
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public boolean isShoweventpageattendeelist() {
		return showeventpageattendeelist;
	}
	public void setShoweventpageattendeelist(boolean showeventpageattendeelist) {
		this.showeventpageattendeelist = showeventpageattendeelist;
	}
	public String getEventpageattendeeshow() {
		return eventpageattendeeshow;
	}
	public void setEventpageattendeeshow(String eventpageattendeeshow) {
		this.eventpageattendeeshow = eventpageattendeeshow;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	public String getEid() {
		return eid;
	}

	public String getConfigid() {
		return configid;
	}
	public void setConfigid(String configid) {
		this.configid = configid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getNtsEnable() {
		return ntsEnable;
	}
	public void setNtsEnable(String ntsEnable) {
		this.ntsEnable = ntsEnable;
	}
	public EventPageContentData getEventPageContentData() {
		return eventPageContentData;
	}

	public void setEventPageContentData(EventPageContentData eventPageContentData) {
		this.eventPageContentData = eventPageContentData;
	}
	public String getEventLogoURL() {
		return eventLogoURL;
	}
	public void setEventLogoURL(String eventLogoURL) {
		this.eventLogoURL = eventLogoURL;
	}
	public void prepare(){	
		eventPageContentData=new EventPageContentData();
	}
	public String execute(){	
		mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
		serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/";
		System.out.println("server address::"+serveraddress);
		message=EventDB.getConfigVal(eid,"eventpage.logo.message","");
		if(message==null) message="";
		
		imageurl=EventDB.getConfigVal(eid,"eventpage.logo.url","");
		if(imageurl==null) imageurl="";	
		
		eventpageattendeeshow=EventDB.getConfigVal(eid,"eventpage.attendee.show","");
		if("Yes".equalsIgnoreCase(eventpageattendeeshow)){
			showeventpageattendeelist=true;
		}
		/*eventpageattendeeshow=EventDB.getConfigVal(eid,"eventpage.attendee.show","");
		if("Yes".equalsIgnoreCase(eventpageattendeeshow)){
			showeventpageattendeelist=true;
		}*/
		socialpromotionsshow=EventDB.getConfigVal(eid,"event.socialmediapromotions.show","");
		if("Y".equalsIgnoreCase(socialpromotionsshow)){
			showsocialpromotions=true;
		}
		String showquestions=EventDB.getConfigVal(eid,"event.confirmationscreen.questions.show","");
		if("Y".equalsIgnoreCase(showquestions)){
			showconfirmscrnquestions=true;
		}
		googlemapshow=EventDB.getConfigVal(eid,"eventpage.map.show","");
		if("Yes".equalsIgnoreCase(googlemapshow)){
			showgooglemap=true;
		}
		promotionsshow=EventDB.getConfigVal(eid,"show.ebee.promotions","yes");
		if("Yes".equalsIgnoreCase(promotionsshow)){
			showpromotions=true;
		}
		volumeTicketCount=VolumeTicketsDB.getVolumeTicketCount(eid);
		if(volumeTicketCount>0){
			volticketsshow = EventDB.getConfigVal(eid,"event.volumetickets.show","");
			if("Y".equalsIgnoreCase(volticketsshow)){
				showvoltickets=true;
			}
		}
		configValues = EventPageContentDB.getConfigValues(eid);
		if(configValues != null && !configValues.isEmpty()){
			for(int i=0;i<configValues.size();i++){
				
				
				String name = configValues.get(i).getKey();
				String value = configValues.get(i).getValue();
				
				if(name.equals("event.twitter.show") && value.equals("Y"))
					socialnwchkbox.add("twitter");
				if(name.equals("event.fbshare.show") && value.equals("Y"))
					socialnwchkbox.add("fbshare");
				if(name.equals("event.fblike.show") && value.equals("Y"))
					socialnwchkbox.add("fblike");
				if(name.equals("event.googleplusone.show") && value.equals("Y"))
					socialnwchkbox.add("googleplusone");
				/*if(name.equals("event.twittercomment.show") && value.equals("Y"))
					socialnwchkbox.add("twittercomment");*/
				if(name.equals("event.fbcomment.show") && value.equals("Y"))
					socialnwchkbox.add("fbcomment");
				if(name.equals("event.fbsend.show") && value.equals("Y"))
					socialnwchkbox.add("fbsend");
				if(name.equals("event.fbfanpagecomments.show") && value.equals("Y"))
					socialnwchkbox.add("fbfan");
				if(name.equals("event.FBRSVPList.show") && value.equals("Y"))
					socialnwchkbox.add("fbattending");
				if(name.equals("event.fbiboughtbutton.show") && value.equals("Y"))
					socialnwchkbox.add("fbibought");
				if(name.equals("event.fbiboughtbutton.gender") && value.equals("Y"))
					socialnwchkbox.add("ibgendercount");
				if(name.equals("event.FBRSVPList.gendercount.show") && value.equals("Y"))
					socialnwchkbox.add("gendercount");
				if(name.equals("event.confirmationpage.fbsharepopup.show") && value.equals("Y"))
					socialnwchkbox.add("sharepopup");
				if(name.equals("event.FBRSVPList.eventid"))
					facebookeventid=value;
				if(name.equals("event.fbfanpagecomments.pageid"))
					fanpageid=value;
				if(name.equals("event.reg.loginpopup.show") && value.equals("Y"))
					socialnwchkbox.add("fblogin");
				
			}
		}
		
		configValuesofpromotions = EventPageContentDB.getconfigValuesofpromotions(eid);
		
		
		//System.out.println("arraykk"+configValuesofpromotions.contains("show.ebee.promotions"));
		
		/*if(!configValuesofpromotions.contains("show.ebee.promotions"))
			promosettings.add("promotions");*/
			
		if(configValuesofpromotions != null && !configValuesofpromotions.isEmpty()){
			for(int i=0;i<configValuesofpromotions.size();i++)
			{
				String name = configValuesofpromotions.get(i).getKey();
				String value = configValuesofpromotions.get(i).getValue();
				
				
				if(name.equals("eventpage.attendee.show") && value.equals("Yes"))
					promosettings.add("attendee");
				if(name.equals("eventpage.socialmediapromotions.show") && value.equals("Y"))
					promosettings.add("socialmediapromotions");
				if(name.equals("eventpage.map.show") && value.equals("Yes"))
					promosettings.add("maps");
				if(name.equals("show.ebee.promotions") && value.equals("Yes"))
					promosettings.add("promotions");
				if(name.equals("event.confirmationscreen.questions.show") && value.equals("Yes"))
					promosettings.add("confirmationqno");
			
			}
			
		}
		
		
		
		
		eventPageContentData.setEventpassword(EventDB.getEventPassword(eid));
		eventPageContentData.setEventphotoURL(EventDB.getConfigVal(eid,"event.eventphotoURL",""));
		eventPageContentData.setUploadurl(EventPageContentDB.getPhotourl(eid));
        
		String img=EventDB.getConfigVal(eid,"event.eventphotoURL.width","");
		if("".equals(img))
			imgtype="uploaded";else
			imgtype="urlimage";
		
		ntsEnable = EventDB.getNTSStatus(eid);
		
		regloginpopup=EventPageContentDB.getRegLoginPopupStatus(eid);
		if(regloginpopup==null) regloginpopup="N";
		
		return "success";
	}

	public String getServeraddress() {
		return serveraddress;
	}
	public void setServeraddress(String serveraddress) {
		this.serveraddress = serveraddress;
	}
	public String insertlogoandmessage(){
		EventPageContentDB.insertlogoandmessage(eid,message,image);
		EventPageContentDB.removeGLobalEventCache(eid);
		
		imageurl=EventDB.getConfigVal(eid,"eventpage.logo.url","");
		if(imageurl==null) imageurl="";	
		msgKey=imageurl;
		/*msgKey = "insertlogoandmessage.done";*/
		return "ajaxmsg";
	}
	public void updateshowattendeeinfo(){		
		EventPageContentDB.updateshowattendeeinfo(eid,val);
	}
	public String updateshowgooglemap(){		
		EventPageContentDB.updateshowgooglemap(eid,val);
		msgKey = "done";
		return "ajaxmsg";
	}
	public String updateshowpromotions(){
		EventPageContentDB.updateshowpromotions(eid,val);
		msgKey = "done";
		return "ajaxmsg";
	}
	public String updateshowattendee(){
		updateshowattendeeinfo();
		msgKey = "done";
		return "ajaxmsg";
	}
	public String updateshowsocialpromotions(){
		EventPageContentDB.updateshowsocialpromotions(eid,val);
		msgKey = "done";
		return "ajaxmsg";
	}
	public String updateshowquestions(){
		EventPageContentDB.updateshowquestioninfo(eid,val);
		msgKey = "done";
		return "ajaxmsg";
	}
	public String insertEventPassword(){
		System.out.println("powertype: "+powertype);
		if(powertype.equals("RSVP")){
			mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP EventPage Password");
		}
		EventPageContentDB.insertEventPwd(eid,password);
		eventPageContentData.setEventpassword(EventDB.getEventPassword(eid));
		msgKey = "done";
		return "ajaxmsg";
	}
	
	public String updateEventLogoURL(){
		System.out.println("eid: "+eid+" eventLogoURL: "+eventLogoURL);
		EventPageContentDB.updateEventLogoURL(eid, eventLogoURL);
		EventPageContentDB.removeGLobalEventCache(eid);
		msgKey = "done";
		return "ajaxmsg";
	}
	public String updateeventphotoURL(){
		EventPageContentDB.updateeventphotoURL(eid,eventphotoURL,uploadurl);
		eventPageContentData.setEventphotoURL(EventDB.getConfigVal(eid,"event.eventphotoURL",""));
		eventPageContentData.setUploadurl(EventPageContentDB.getPhotourl(eid));
		EventPageContentDB.removeGLobalEventCache(eid);
		msgKey = "done";
		return "ajaxmsg";
	}
	public String getlogoandmessage(){
		message=EventDB.getConfigVal(eid,"eventpage.logo.message","");
		if(message==null) message="";
		
		imageurl=EventDB.getConfigVal(eid,"eventpage.logo.url","");
		if(imageurl==null) imageurl="";	
		return "logonmsg";
	}
	
	
	public String geteventpassword(){
		eventPageContentData.setEventpassword(EventDB.getEventPassword(eid));
		return "password";
	}
	
	public String updateContentFields(){
		EventPageContentDB.updateFBconfirmationPromoSettings(eid,fbShareConfirmation,fbLoginduringRegistration,promotions);
		/*if(powertype.equals("RSVP")){
			mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP EventPage Password");
		}*/
		EventPageContentDB.insertEventPwd(eid,password);
		eventPageContentData.setEventpassword(EventDB.getEventPassword(eid));
		msgKey="Updated";
		return "ajaxmsg";
	}
	
		
	public String updateSocialNwSettings(){
		boolean status=true;
		/*if(powertype.equals("RSVP")){
			mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
			String accounttype=UpgradeEventDB.getAccountType(mgrId);
			if("basic".equalsIgnoreCase(accounttype))
				status=validateData();
		}*/
			
		if(status){
			System.out.println("EventPageContent powertype: "+powertype);
			if(powertype.equals("RSVP") && socialnwchkbox.contains("twitter"))
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP EventPageContent");
			else if(powertype.equals("RSVP") && socialnwchkbox.contains("fblike"))
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP EventPageContent");
			else if(powertype.equals("RSVP") && socialnwchkbox.contains("googleplusone"))
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP EventPageContent");
			else if(powertype.equals("RSVP") && socialnwchkbox.contains("fbcomment"))
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP EventPageContent");
			else if(powertype.equals("RSVP") && socialnwchkbox.contains("fbattending"))
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP EventPageContent");		
			
					EventPageContentDB.updateSocialNetwork(eid,socialnwchkbox,facebookeventid,fanpageid);
					msgKey="Updated";
					return "ajaxmsg";
				}
				else
				{
					return "fail";
				}
			}
	
	
	
	public String updatePromoSettings()
	{
	boolean	 status=true;
		if(status)
		{	
			EventPageContentDB.updatePromotionSettings(eid,promosettings);
			msgKey="Updated";
			return "ajaxmsg";
		}
		else
		{
			return "fail";
		}
	}
	
	
	
	
	private void updatePromotionsSettings() {
		// TODO Auto-generated method stub
		
	}
	public boolean validateData(){
		//String accounttype=UpgradeEventDB.getAccountType(mgrId);
		if(currentLevel.equals("150") && currentFee.equals("0"))
			return true;
    			
		if(socialnwchkbox.contains("twitter"))
			getCardStatus();
		else if(socialnwchkbox.contains("fblike"))
			getCardStatus();
		else if(socialnwchkbox.contains("googleplusone"))
			getCardStatus();
		else if(socialnwchkbox.contains("fbcomment"))
			getCardStatus();
		else if(socialnwchkbox.contains("fbattending"))
			getCardStatus();
		
		
		if(!getFieldErrors().isEmpty()){
			return false;
		}
		return true;
	}
	
	public void getCardStatus(){
		String cardrequired="";
		String cardstatus=CardProcessorDB.getManagerCCStatus(mgrId);
		if(!"Y".equalsIgnoreCase(cardstatus)){
		    cardrequired = DbUtil.getVal("select value from mgr_config where name='card.popup.required' and userid=CAST(? AS INTEGER)", new String[]{mgrId});
			if(cardrequired==null || cardrequired.equals("Y"))
				addFieldError("","You have no cards");
		}
	}
	
	public String updateshowvolumetickets(){
		EventPageContentDB.updateShowVolumeTickets(eid,val);
		msgKey = "done";
		return "ajaxmsg";
	}
	
	public String getexternalphotourl(){
		return "externalurl";
	}
	public String insertExternalURL(){
		if("".equals(eventexternalphotoURL.trim()))
			addFieldError("eventPageContentData.eventexternalphotoURL", "Photo URL is empty");
		if("".equals(photowidth.trim()))
			addFieldError("photowidth","Width is empty");
		else{
			try{
				Integer.parseInt(photowidth);
			}catch(Exception e){
				addFieldError("photowidth","Width should be nuemeric");
			}
			}
		if(!getFieldErrors().isEmpty())
			return "fail";
		
		
		EventPageContentDB.updateexternaleventphotoURL(eid,eventexternalphotoURL,photowidth);
		EventPageContentDB.removeGLobalEventCache(eid);
		msgKey=eventexternalphotoURL.trim();
		/*msgKey="done";*/
		return "ajaxmsg";
	}
	public String deletePhotoURL(){
		EventPageContentDB.deletePhoto(eid);
		EventPageContentDB.removeGLobalEventCache(eid);
		return "ajaxmsg";
	}
	
	public String deleteLogoURL(){
		EventPageContentDB.deleteLogoURL(eid);
		EventPageContentDB.removeGLobalEventCache(eid);
		return "ajaxmsg";
	}
	public ArrayList getSocialnwchkbox() {
		return socialnwchkbox;
	}
	public ArrayList getPromosettings() {
		return promosettings;
	}
	public void setPromosettings(ArrayList promosettings) {
		this.promosettings = promosettings;
	}
	public void setSocialnwchkbox(ArrayList socialnwchkbox) {
		this.socialnwchkbox = socialnwchkbox;
	}
	public boolean isShowconfirmscrnquestions() {
		return showconfirmscrnquestions;
	}
	public void setShowconfirmscrnquestions(boolean showconfirmscrnquestions) {
		this.showconfirmscrnquestions = showconfirmscrnquestions;
	}
	public ArrayList<Entity> getConfigValues() {
		return configValues;
	}
	public ArrayList<Entity> getConfigValuesofpromotions() {
		return configValuesofpromotions;
	}
	public void setConfigValuesofpromotions(
			ArrayList<Entity> configValuesofpromotions) {
		this.configValuesofpromotions = configValuesofpromotions;
	}
	public void setConfigValues(ArrayList<Entity> configValues) {
		this.configValues = configValues;
	}
	public String getFacebookeventid() {
		return facebookeventid;
	}
	public void setFacebookeventid(String facebookeventid) {
		this.facebookeventid = facebookeventid;
	}
	public String getRegloginpopup() {
		return regloginpopup;
	}
	public void setRegloginpopup(String regloginpopup) {
		this.regloginpopup = regloginpopup;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMgrId() {
		return mgrId;
	}
	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}
	public String getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}
	public String getCurrentFee() {
		return currentFee;
	}
	public void setCurrentFee(String currentFee) {
		this.currentFee = currentFee;
	}
	public String getSocialpromotionsshow() {
		return socialpromotionsshow;
	}
	public void setSocialpromotionsshow(String socialpromotionsshow) {
		this.socialpromotionsshow = socialpromotionsshow;
	}
	public boolean isShowsocialpromotions() {
		return showsocialpromotions;
	}
	public void setShowsocialpromotions(boolean showsocialpromotions) {
		this.showsocialpromotions = showsocialpromotions;
	}
}
