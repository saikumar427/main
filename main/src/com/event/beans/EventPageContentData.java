package com.event.beans;

public class EventPageContentData {
	private String photourl="";	
	private String message="";
	private String eventpassword="";
	private String eventphotoURL="";
	private String uploadurl="";
	
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
	public String getEventpassword() {
		return eventpassword;
	}
	public void setEventpassword(String eventpassword) {
		this.eventpassword = eventpassword;
	}
	public String getPhotourl() {
		return photourl;
	}
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	

}
