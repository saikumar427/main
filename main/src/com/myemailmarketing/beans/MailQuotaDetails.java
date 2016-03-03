package com.myemailmarketing.beans;

import java.io.Serializable;

public class MailQuotaDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 839705114645994379L;
	private String quotaName="";
	private String quotaPrice="";
	public String getQuotaName() {
		return quotaName;
	}
	public void setQuotaName(String quotaName) {
		this.quotaName = quotaName;
	}
	public String getQuotaPrice() {
		return quotaPrice;
	}
	public void setQuotaPrice(String quotaPrice) {
		this.quotaPrice = quotaPrice;
	}

}
