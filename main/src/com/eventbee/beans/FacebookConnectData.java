package com.eventbee.beans;

public class FacebookConnectData {
	private String fbApiKey="";
	private String fbAppId="";
	private String fbeventid="";
	private String fbuserid="";
	boolean alreadyPublished=false;

	
	public boolean isAlreadyPublished() {
		return alreadyPublished;
	}

	public void setAlreadyPublished(boolean alreadyPublished) {
		this.alreadyPublished = alreadyPublished;
	}

	public String getFbeventid() {
		return fbeventid;
	}

	public void setFbeventid(String fbeventid) {
		this.fbeventid = fbeventid;
	}

	public String getFbApiKey() {
		return fbApiKey;
	}

	public void setFbApiKey(String fbApiKey) {
		this.fbApiKey = fbApiKey;
	}

	public String getFbuserid() {
		return fbuserid;
	}

	public void setFbuserid(String fbuserid) {
		this.fbuserid = fbuserid;
	}

	public String getFbAppId() {
		return fbAppId;
	}

	public void setFbAppId(String fbAppId) {
		this.fbAppId = fbAppId;
	}
}
