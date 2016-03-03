package com.event.beans.ticketing;

import java.util.Date;


import com.eventbee.beans.DateTimeBean;

public class TicketData {
	private String ticketName="";
	private String ticketDescription="";
	private String ticketPrice="";
	private String minQty="1";
	private String maxQty="5";
	private Date startDate;
	private Date endDate;
	private String isDonation="";
	private String isAttendee="";
	private String processingFee="";
	private String partnerLimit="";
	private String startTime="";
	private String endTime="";
	private String startBefore="0";
	private String endBefore="0";
	private String startAMPM="";
	private String endAMPM="";
	private String ticketId="";
	private String totalTicketCount="";
	private String ticketType="";
	private String ticketGroupId="0";
	private String position;
	private String currency="$";
	private DateTimeBean stdateTimeBeanObj;
	private DateTimeBean enddateTimeBeanObj;
	private String processFeePaidBy="processFeeAttendee";
	private String memberTicket="No";
	private String hubId="";
	private String startHoursBefore="0";
	private String endHoursBefore="0";
	private String startMinutesBefore="0";
	private String endMinutesBefore="0";
	private String netWorkCommission;
	private String percentage="%";
	private String credits="";
	private String soldqty="";
	private String isScanCode="Yes";
	private String oldTicket="";
	private String newStartDate="";
	private String newEndDate="";
	private String waitListType="NO";
	private String waitListLimit="";
	private String showyn="";
	private String isPostSelling="N";
	
	public String getShowyn() {
		return showyn;
	}
	public void setShowyn(String showyn) {
		this.showyn = showyn;
	}
	public String getNewStartDate() {
		return newStartDate;
	}
	public void setNewStartDate(String newStartDate) {
		this.newStartDate = newStartDate;
	}
	public String getNewEndDate() {
		return newEndDate;
	}
	public void setNewEndDate(String newEndDate) {
		this.newEndDate = newEndDate;
	}
	public String getOldTicket() {
		return oldTicket;
	}
	public void setOldTicket(String oldTicket) {
		this.oldTicket = oldTicket;
	}
	public String getIsScanCode() {
		return isScanCode;
	}
	public void setIsScanCode(String isScanCode) {
		this.isScanCode = isScanCode;
	}
	public String getSoldqty() {
		return soldqty;
	}
	public void setSoldqty(String soldqty) {
		this.soldqty = soldqty;
	}
	
	public String getStartHoursBefore() {
		return startHoursBefore;
	}
	public void setStartHoursBefore(String startHoursBefore) {
		this.startHoursBefore = startHoursBefore;
	}
	public String getEndHoursBefore() {
		return endHoursBefore;
	}
	public void setEndHoursBefore(String endHoursBefore) {
		this.endHoursBefore = endHoursBefore;
	}
	public String getStartMinutesBefore() {
		return startMinutesBefore;
	}
	public void setStartMinutesBefore(String startMinutesBefore) {
		this.startMinutesBefore = startMinutesBefore;
	}
	public String getEndMinutesBefore() {
		return endMinutesBefore;
	}
	public void setEndMinutesBefore(String endMinutesBefore) {
		this.endMinutesBefore = endMinutesBefore;
	}
	public String getMemberTicket() {
		return memberTicket;
	}
	public void setMemberTicket(String memberTicket) {
		this.memberTicket = memberTicket;
	}
	public String getHubId() {
		return hubId;
	}
	public void setHubId(String hubId) {
		this.hubId = hubId;
	}
	public String getProcessFeePaidBy() {
		return processFeePaidBy;
	}
	public void setProcessFeePaidBy(String processFeePaidBy) {
		this.processFeePaidBy = processFeePaidBy;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getIsAttendee() {
		return isAttendee;
	}
	public void setIsAttendee(String isAttendee) {
		this.isAttendee = isAttendee;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getTicketGroupId() {
		return ticketGroupId;
	}
	public void setTicketGroupId(String ticketGroupId) {
		this.ticketGroupId = ticketGroupId;
	}
	public String getTotalTicketCount() {
		return totalTicketCount;
	}
	public void setTotalTicketCount(String totalTicketCount) {
		this.totalTicketCount = totalTicketCount;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getIsDonation() {
		return isDonation;
	}
	public void setIsDonation(String isDonation) {
		this.isDonation = isDonation;
	}
	public String getProcessingFee() {
		return processingFee;
	}
	public void setProcessingFee(String processingFee) {
		this.processingFee = processingFee;
	}
	public String getPartnerLimit() {
		return partnerLimit;
	}
	public void setPartnerLimit(String partnerLimit) {
		this.partnerLimit = partnerLimit;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartAMPM() {
		return startAMPM;
	}
	public void setStartAMPM(String startAMPM) {
		this.startAMPM = startAMPM;
	}
	public String getEndAMPM() {
		return endAMPM;
	}
	public void setEndAMPM(String endAMPM) {
		this.endAMPM = endAMPM;
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
	
	public String getStartBefore() {
		return startBefore;
	}
	public void setStartBefore(String startBefore) {
		this.startBefore = startBefore;
	}
	public String getEndBefore() {
		return endBefore;
	}
	public void setEndBefore(String endBefore) {
		this.endBefore = endBefore;
	}
	public String getNetWorkCommission() {
		return netWorkCommission;
	}
	public void setNetWorkCommission(String netWorkCommission) {
		this.netWorkCommission = netWorkCommission;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getCredits() {
		return credits;
	}
	public void setCredits(String credits) {
		this.credits = credits;
	}
	public String getWaitListType() {
		return waitListType;
	}
	public void setWaitListType(String waitListType) {
		this.waitListType = waitListType;
	}
	public String getWaitListLimit() {
		return waitListLimit;
	}
	public void setWaitListLimit(String waitListLimit) {
		this.waitListLimit = waitListLimit;
	}
	public String getIsPostSelling() {
		return isPostSelling;
	}
	public void setIsPostSelling(String isPostSelling) {
		this.isPostSelling = isPostSelling;
	}
}
