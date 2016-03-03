package com.eventbee.beans;

public class CreditCardData {
	private String seqId="";
	private String amount="";
	private String creditCardScreenData="";
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCreditCardScreenData() {
		return creditCardScreenData;
	}

	public void setCreditCardScreenData(String creditCardScreenData) {
		this.creditCardScreenData = creditCardScreenData;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
}
