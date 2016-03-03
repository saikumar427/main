package com.mycommunities.beans;

import java.io.Serializable;

public class ClubMemberShip implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1073851383944315373L;
	private String clubId;
    private String clubName="";
    private String memberShipId;
    private String memberShipName;
    private String description;
    private String currencyType;
    private String price;//this is used as admission fee
    private String termPrice;//this is used as term fee
    private String mshipTerm;
    private String mshipPeriod="";
    private String mcreatetype="";
    private String mstatus="";

    private String cycleType="Join Date";//can be also Fixed Date
    private String fixedDD;
    private String fixedMM;
    //private String discount="";
    //private String discountcode="";
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public String getMemberShipId() {
		return memberShipId;
	}
	public void setMemberShipId(String memberShipId) {
		this.memberShipId = memberShipId;
	}
	public String getMemberShipName() {
		return memberShipName;
	}
	public void setMemberShipName(String memberShipName) {
		this.memberShipName = memberShipName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTermPrice() {
		return termPrice;
	}
	public void setTermPrice(String termPrice) {
		this.termPrice = termPrice;
	}
	public String getMshipTerm() {
		return mshipTerm;
	}
	public void setMshipTerm(String mshipTerm) {
		this.mshipTerm = mshipTerm;
	}
	public String getMshipPeriod() {
		return mshipPeriod;
	}
	public void setMshipPeriod(String mshipPeriod) {
		this.mshipPeriod = mshipPeriod;
	}
	public String getMcreatetype() {
		return mcreatetype;
	}
	public void setMcreatetype(String mcreatetype) {
		this.mcreatetype = mcreatetype;
	}
	public String getMstatus() {
		return mstatus;
	}
	public void setMstatus(String mstatus) {
		this.mstatus = mstatus;
	}
	public String getCycleType() {
		return cycleType;
	}
	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}
	public String getFixedDD() {
		return fixedDD;
	}
	public void setFixedDD(String fixedDD) {
		this.fixedDD = fixedDD;
	}
	public String getFixedMM() {
		return fixedMM;
	}
	public void setFixedMM(String fixedMM) {
		this.fixedMM = fixedMM;
	}
	public String toString(){
	      return getClass().getName()
	         + "[clubId=" + clubId
	         + ",memberShipId=" + memberShipId
		 + ",memberShipName=" + memberShipName
		 + ",description=" + description
		 + ",currencyType=" + currencyType
		 + ",price=" + price

		 + "]";
}
    

}
