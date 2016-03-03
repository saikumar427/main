package com.event.beans.ticketing;

import com.eventbee.beans.DateTimeBean;

public class VolumeTicketData {
	private String ticketId="";
	private String ticketName="";
	private String ticketDescription="";
	private String ticketPrice="";
	private String minQty="1";
	private String maxQty="5";
	private String processingFee="";
	private String processFeePaidBy="processFeeAttendee";
	private DateTimeBean stdateTimeBeanObj;
	private DateTimeBean enddateTimeBeanObj;
	private String currency="$";
	private String discountType="A";
	private String amountDiscount="1";
	private String percentageDiscount="5";
	private String triggerQty="";
	private String activeDuration="";
	private String postTriggerType="1";
	private String upperLimit="";
	private String cycles="";
	private String triggerFailAction="2";
	private String triggerFailDiscount="";
	private String approvalStatus="";
	private String status="";
	private String simulate="";
	private String volumePrice="";
	private String showhide="";
	public String getMinQty() {
		return minQty;
	}
	public void setMinQty(String minQty) {
		this.minQty = minQty;
	}
	public String getMaxQty() {
		return maxQty;
	}
	public void setMaxQty(String maxQty) {
		this.maxQty = maxQty;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getAmountDiscount() {
		return amountDiscount;
	}
	public void setAmountDiscount(String amountDiscount) {
		this.amountDiscount = amountDiscount;
	}
	public String getPercentageDiscount() {
		return percentageDiscount;
	}
	public void setPercentageDiscount(String percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
	}
	public String getTriggerQty() {
		return triggerQty;
	}
	public void setTriggerQty(String triggerQty) {
		this.triggerQty = triggerQty;
	}
	public String getActiveDuration() {
		return activeDuration;
	}
	public void setActiveDuration(String activeDuration) {
		this.activeDuration = activeDuration;
	}
	public String getPostTriggerType() {
		return postTriggerType;
	}
	public void setPostTriggerType(String postTriggerType) {
		this.postTriggerType = postTriggerType;
	}
	public String getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(String upperLimit) {
		this.upperLimit = upperLimit;
	}
	public String getCycles() {
		return cycles;
	}
	public void setCycles(String cycles) {
		this.cycles = cycles;
	}
	public String getTriggerFailAction() {
		return triggerFailAction;
	}
	public void setTriggerFailAction(String triggerFailAction) {
		this.triggerFailAction = triggerFailAction;
	}
	public String getTriggerFailDiscount() {
		return triggerFailDiscount;
	}
	public void setTriggerFailDiscount(String triggerFailDiscount) {
		this.triggerFailDiscount = triggerFailDiscount;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	public String getTicketDescription() {
		return ticketDescription;
	}
	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}
	public String getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getProcessingFee() {
		return processingFee;
	}
	public void setProcessingFee(String processingFee) {
		this.processingFee = processingFee;
	}
	public String getProcessFeePaidBy() {
		return processFeePaidBy;
	}
	public void setProcessFeePaidBy(String processFeePaidBy) {
		this.processFeePaidBy = processFeePaidBy;
	}
	public DateTimeBean getStdateTimeBeanObj() {
		return stdateTimeBeanObj;
	}
	public void setStdateTimeBeanObj(DateTimeBean stdateTimeBeanObj) {
		this.stdateTimeBeanObj = stdateTimeBeanObj;
	}
	public DateTimeBean getEnddateTimeBeanObj() {
		return enddateTimeBeanObj;
	}
	public void setEnddateTimeBeanObj(DateTimeBean enddateTimeBeanObj) {
		this.enddateTimeBeanObj = enddateTimeBeanObj;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSimulate() {
		return simulate;
	}
	public void setSimulate(String simulate) {
		this.simulate = simulate;
	}
	public String getVolumePrice() {
		return volumePrice;
	}
	public void setVolumePrice(String volumePrice) {
		this.volumePrice = volumePrice;
	}
	public String getShowhide() {
		return showhide;
	}
	public void setShowhide(String showhide) {
		this.showhide = showhide;
	}

}
