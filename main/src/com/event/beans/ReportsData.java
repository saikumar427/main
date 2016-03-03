package com.event.beans;

public class ReportsData {
	private String startYear="";
	private String startMonth="";
	private String startDay="";
	private String endYear="";
	private String endMonth="";
	private String endDay="";
	private String attstartYear="";
	private String attstartMonth="";
	private String attstartDay="";
	private String attendYear="";
	private String attendMonth="";
	private String attendDay="";
	private String ticket="";
	private String ticketid="";
	private String reportsradio="attendee";
	private String transactionID="";
	private String orderNumber="";
	private String attendeeName="";
	private String paymentstaus="";
	private String source="";
	private String sYear="";
	private String sMonth="";
	private String sDay="";
	private String eYear="";
	private String eMonth="";
	private String eDay="";
	private String attsYear="";
	private String attsMonth="";
	private String attsDay="";
	private String atteYear="";
	private String atteMonth="";
	private String atteDay="";
	private String attselindex="1";
	private String attdatetype="1";
	private String typeindex="1";
	private String transactiontypeindex="1";
	private String selindex="1";
	private String[] Fields={};
	private String[] attFields={};
	private String[] checkinFields={};
	private String date="";
	private String originalAmount="";
	private String currentDiscount="";
	private String currentTax="";
	private String netAmount="";
	private String checkinreportsindex="1";
	private String purchasedOnline="on";
	private String addedManually="on";
	private String reportType="";
	private String paymenttypeindex="";
	private String currentTransactionDate="";
	private String surecount="44";
	private String notsurecount="";
	private String attendingradio="";
	private String notattendingradio="";
	private String responsetype="";
	private String addsure="0";
	private String addnotsure="0";
	private String changeStatus="";
	private String currentStatus="";
	private String online="";
	private String manual="";
	private String isVolumeSale="";
	private String volumeTickets="";
	private String eventStartDate="";
	private String eventEndDate="";
	private String eventTranStartDate="";
	private String eventTranEndDate="";
	private String chkInStartDate="";
	private String chkInEndDate="";
	private String searchOn="";
	private String searchContent="";
	private boolean isSearch=false;
	private String salesDateType="1";
	private String chkinDateType="1";
	private String attDispFields="1";
	private String chkinDispFields="1";
	private String salesDispFields="1";
	
	public String getAttDispFields() {
		return attDispFields;
	}
	public void setAttDispFields(String attDispFields) {
		this.attDispFields = attDispFields;
	}
	public String getChkinDispFields() {
		return chkinDispFields;
	}
	public void setChkinDispFields(String chkinDispFields) {
		this.chkinDispFields = chkinDispFields;
	}
	public String getSalesDispFields() {
		return salesDispFields;
	}
	public void setSalesDispFields(String salesDispFields) {
		this.salesDispFields = salesDispFields;
	}
	public String getSalesDateType() {
		return salesDateType;
	}
	public void setSalesDateType(String salesDateType) {
		this.salesDateType = salesDateType;
	}
	public String getChkinDateType() {
		return chkinDateType;
	}
	public void setChkinDateType(String chkinDateType) {
		this.chkinDateType = chkinDateType;
	}
	public boolean getIsSearch() {
		return isSearch;
	}
	public void setIsSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}
	public String getSourceContent() {
		return sourceContent;
	}
	public void setSourceContent(String sourceContent) {
		this.sourceContent = sourceContent;
	}
	private String sourceContent="";
	
	public String getSearchOn() {
		return searchOn;
	}
	public void setSearchOn(String searchOn) {
		this.searchOn = searchOn;
	}
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	public String getChkInStartDate() {
		return chkInStartDate;
	}
	public void setChkInStartDate(String chkInStartDate) {
		this.chkInStartDate = chkInStartDate;
	}
	public String getChkInEndDate() {
		return chkInEndDate;
	}
	public void setChkInEndDate(String chkInEndDate) {
		this.chkInEndDate = chkInEndDate;
	}
	public String getEventTranStartDate() {
		return eventTranStartDate;
	}
	public void setEventTranStartDate(String eventTranStartDate) {
		this.eventTranStartDate = eventTranStartDate;
	}
	public String getEventTranEndDate() {
		return eventTranEndDate;
	}
	public void setEventTranEndDate(String eventTranEndDate) {
		this.eventTranEndDate = eventTranEndDate;
	}
	public String getEventStartDate() {
		return eventStartDate;
	}
	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	public String getEventEndDate() {
		return eventEndDate;
	}
	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	public String getVolumeTickets() {
		return volumeTickets;
	}
	public void setVolumeTickets(String volumeTickets) {
		this.volumeTickets = volumeTickets;
	}
	public String getIsVolumeSale() {
		return isVolumeSale;
	}
	public void setIsVolumeSale(String isVolumeSale) {
		this.isVolumeSale = isVolumeSale;
	}
	public String getChangeStatus() {
		return changeStatus;
	}
	public void setChangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
	}
	
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getAddsure() {
		return addsure;
	}
	public void setAddsure(String addsure) {
		this.addsure = addsure;
	}
	public String getAddnotsure() {
		return addnotsure;
	}
	public void setAddnotsure(String addnotsure) {
		this.addnotsure = addnotsure;
	}
	public String getResponsetype() {
		return responsetype;
	}
	public void setResponsetype(String responsetype) {
		this.responsetype = responsetype;
	}
	public String getSurecount() {
		return surecount;
	}
	public void setSurecount(String surecount) {
		this.surecount = surecount;
	}
	public String getNotsurecount() {
		return notsurecount;
	}
	public void setNotsurecount(String notsurecount) {
		this.notsurecount = notsurecount;
	}
	public String getAttendingradio() {
		return attendingradio;
	}
	public void setAttendingradio(String attendingradio) {
		this.attendingradio = attendingradio;
	}
	public String getNotattendingradio() {
		return notattendingradio;
	}
	public void setNotattendingradio(String notattendingradio) {
		this.notattendingradio = notattendingradio;
	}
	public String getCurrentTransactionDate() {
		return currentTransactionDate;
	}
	public void setCurrentTransactionDate(String currentTransactionDate) {
		this.currentTransactionDate = currentTransactionDate;
	}
	public String getPaymenttypeindex() {
		return paymenttypeindex;
	}
	public void setPaymenttypeindex(String paymenttypeindex) {
		this.paymenttypeindex = paymenttypeindex;
	}
	public String getTransactiontypeindex() {
		return transactiontypeindex;
	}
	public void setTransactiontypeindex(String transactiontypeindex) {
		this.transactiontypeindex = transactiontypeindex;
	}
	public String getTicketid() {
		return ticketid;
	}
	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getTypeindex() {
		return typeindex;
	}
	public void setTypeindex(String typeindex) {
		this.typeindex = typeindex;
	}
	public String getAddedManually() {
		return addedManually;
	}
	public void setAddedManually(String addedManually) {
		this.addedManually = addedManually;
	}
	public String getPurchasedOnline() {
		return purchasedOnline;
	}
	public void setPurchasedOnline(String purchasedOnline) {
		this.purchasedOnline = purchasedOnline;
	}
	public String[] getCheckinFields() {
		return checkinFields;
	}
	public void setCheckinFields(String[] checkinFields) {
		this.checkinFields = checkinFields;
	}
	public String getCheckinreportsindex() {
		return checkinreportsindex;
	}
	public void setCheckinreportsindex(String checkinreportsindex) {
		this.checkinreportsindex = checkinreportsindex;
	}
	public String getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	public String getOriginalAmount() {
		return originalAmount;
	}
	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}
	public String getCurrentDiscount() {
		return currentDiscount;
	}
	public void setCurrentDiscount(String currentDiscount) {
		this.currentDiscount = currentDiscount;
	}
	public String getCurrentTax() {
		return currentTax;
	}
	public void setCurrentTax(String currentTax) {
		this.currentTax = currentTax;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String[] getFields() {
		return Fields;
	}
	public void setFields(String[] fields) {
		Fields = fields;
	}
	public String[] getAttFields() {
		return attFields;
	}
	public void setAttFields(String[] attFields) {
		this.attFields = attFields;
	}
	public String getSelindex() {
		return selindex;
	}
	public void setSelindex(String selindex) {
		this.selindex = selindex;
	}
	public String getAttselindex() {
		return attselindex;
	}
	public void setAttselindex(String attselindex) {
		this.attselindex = attselindex;
	}
	public String getSYear() {
		return sYear;
	}
	public void setSYear(String year) {
		sYear = year;
	}
	public String getSMonth() {
		return sMonth;
	}
	public void setSMonth(String month) {
		sMonth = month;
	}
	public String getSDay() {
		return sDay;
	}
	public void setSDay(String day) {
		sDay = day;
	}
	public String getEYear() {
		return eYear;
	}
	public void setEYear(String year) {
		eYear = year;
	}
	public String getEMonth() {
		return eMonth;
	}
	public void setEMonth(String month) {
		eMonth = month;
	}
	public String getEDay() {
		return eDay;
	}
	public void setEDay(String day) {
		eDay = day;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPaymentstaus() {
		return paymentstaus;
	}
	public void setPaymentstaus(String paymentstaus) {
		this.paymentstaus = paymentstaus;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getAttendeeName() {
		return attendeeName;
	}
	public void setAttendeeName(String attendeeName) {
		this.attendeeName = attendeeName;
	}
	public String getReportsradio() {
		return reportsradio;
	}
	public void setReportsradio(String reportsradio) {
		this.reportsradio = reportsradio;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getAttdatetype() {
		return attdatetype;
	}
	public void setAttdatetype(String attdatetype) {
		this.attdatetype = attdatetype;
	}
	public String getStartYear() {
		return startYear;
	}
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndYear() {
		return endYear;
	}
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public String getAttstartYear() {
		return attstartYear;
	}
	public void setAttstartYear(String attstartYear) {
		this.attstartYear = attstartYear;
	}
	public String getAttstartMonth() {
		return attstartMonth;
	}
	public void setAttstartMonth(String attstartMonth) {
		this.attstartMonth = attstartMonth;
	}
	public String getAttstartDay() {
		return attstartDay;
	}
	public void setAttstartDay(String attstartDay) {
		this.attstartDay = attstartDay;
	}
	public String getAttendYear() {
		return attendYear;
	}
	public void setAttendYear(String attendYear) {
		this.attendYear = attendYear;
	}
	public String getAttendMonth() {
		return attendMonth;
	}
	public void setAttendMonth(String attendMonth) {
		this.attendMonth = attendMonth;
	}
	public String getAttendDay() {
		return attendDay;
	}
	public void setAttendDay(String attendDay) {
		this.attendDay = attendDay;
	}
	public String getAttsYear() {
		return attsYear;
	}
	public void setAttsYear(String attsYear) {
		this.attsYear = attsYear;
	}
	public String getAttsMonth() {
		return attsMonth;
	}
	public void setAttsMonth(String attsMonth) {
		this.attsMonth = attsMonth;
	}
	public String getAttsDay() {
		return attsDay;
	}
	public void setAttsDay(String attsDay) {
		this.attsDay = attsDay;
	}
	public String getAtteYear() {
		return atteYear;
	}
	public void setAtteYear(String atteYear) {
		this.atteYear = atteYear;
	}
	public String getAtteMonth() {
		return atteMonth;
	}
	public void setAtteMonth(String atteMonth) {
		this.atteMonth = atteMonth;
	}
	public String getAtteDay() {
		return atteDay;
	}
	public void setAtteDay(String atteDay) {
		this.atteDay = atteDay;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	public String getManual() {
		return manual;
	}
	public void setManual(String manual) {
		this.manual = manual;
	}
	
}
