package com.mycommunities.beans;

import java.io.Serializable;

public class ClubInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8634089154705248078L;
	private String unitId;
    private String managerId;
    private String clubId;
    private String category;    
    private String clubName;
    private String description;
    private String clubLogo;
    private String configId;
    private String clubURL;
    private String signupclubURL;
    private String listAtEventbee="Yes";
    private String termsCond;
    private String clubcity;
    private String clubstate;
    private String communityURL="";
    private String loginURL="";
    private String signupURL="";
    private String renewURL="";
    private String city="";
    private String country="";
    public final static String URL="club.url";
    public final static String LISTATEB="club.listateventbee";

    public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCommunityURL() {
		return communityURL;
	}

	public void setCommunityURL(String communityURL) {
		this.communityURL = communityURL;
	}

	public String getLoginURL() {
		return loginURL;
	}

	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}

	public String getSignupURL() {
		return signupURL;
	}

	public void setSignupURL(String signupURL) {
		this.signupURL = signupURL;
	}

	public String getRenewURL() {
		return renewURL;
	}

	public void setRenewURL(String renewURL) {
		this.renewURL = renewURL;
	}

	public ClubInfo() {

    }

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClubLogo() {
		return clubLogo;
	}

	public void setClubLogo(String clubLogo) {
		this.clubLogo = clubLogo;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getClubURL() {
		return clubURL;
	}

	public void setClubURL(String clubURL) {
		this.clubURL = clubURL;
	}

	public String getSignupclubURL() {
		return signupclubURL;
	}

	public void setSignupclubURL(String signupclubURL) {
		this.signupclubURL = signupclubURL;
	}

	public String getListAtEventbee() {
		return listAtEventbee;
	}

	public void setListAtEventbee(String listAtEventbee) {
		this.listAtEventbee = listAtEventbee;
	}

	public String getTermsCond() {
		return termsCond;
	}

	public void setTermsCond(String termsCond) {
		this.termsCond = termsCond;
	}

	public String getClubcity() {
		return clubcity;
	}

	public void setClubcity(String clubcity) {
		this.clubcity = clubcity;
	}

	public String getClubstate() {
		return clubstate;
	}

	public void setClubstate(String clubstate) {
		this.clubstate = clubstate;
	}

	public static String getURL() {
		return URL;
	}

	public static String getLISTATEB() {
		return LISTATEB;
	} 
    
}
