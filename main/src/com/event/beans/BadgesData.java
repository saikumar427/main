package com.event.beans;

import java.util.ArrayList;

import com.eventbee.beans.Entity;

public class BadgesData {
	private String fontsize1="";
	private String fonttype1="";
	private String fontsize2="";
	private String fonttype2="";
	private String pageheight="11.0";
	private String pagewidth="8.5";
	private String leftmargin="0.25";
	private String rightmargin="0.25";
	private String topmargin="0.5";
	private String bottommargin="0.5";
	private String colwidth="4.0";
	private String colheight="2.0";
	private String line1fontsize="";
	private String line2fontsize="";
	private String line1fontfamily="";
	private String line2fontfamily="";
	//newly added properties for the purpose of badges
	private String sYear="";
	private String sMonth="";
	private String sDay="";
	private String eYear="";
	private String eMonth="";
	private String eDay="";	
	private String startYear="";
	private String startMonth="";
	private String startDay="";
	private String endYear="";
	private String endMonth="";
	private String endDay="";
	private String eventDate="";
	private String transactionId="";
	private String transactionradio="";
	private String sortBy="";
	private String startDate="";
	private String endDate="";
	private ArrayList selectedTkts=new ArrayList();
	private ArrayList<Entity> ticketsList=new ArrayList<Entity>();
	
	public String getSortBy() {
		return sortBy;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getTransactionradio() {
		return transactionradio;
	}
	public void setTransactionradio(String transactionradio) {
		this.transactionradio = transactionradio;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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
	
	public String getFontsize1() {
		return fontsize1;
	}
	public void setFontsize1(String fontsize1) {
		this.fontsize1 = fontsize1;
	}
	public String getFonttype1() {
		return fonttype1;
	}
	public void setFonttype1(String fonttype1) {
		this.fonttype1 = fonttype1;
	}
	public String getFontsize2() {
		return fontsize2;
	}
	public void setFontsize2(String fontsize2) {
		this.fontsize2 = fontsize2;
	}
	public String getFonttype2() {
		return fonttype2;
	}
	public void setFonttype2(String fonttype2) {
		this.fonttype2 = fonttype2;
	}
	public String getPageheight() {
		return pageheight;
	}
	public void setPageheight(String pageheight) {
		this.pageheight = pageheight;
	}
	public String getPagewidth() {
		return pagewidth;
	}
	public void setPagewidth(String pagewidth) {
		this.pagewidth = pagewidth;
	}
	public String getLeftmargin() {
		return leftmargin;
	}
	public void setLeftmargin(String leftmargin) {
		this.leftmargin = leftmargin;
	}
	public String getRightmargin() {
		return rightmargin;
	}
	public void setRightmargin(String rightmargin) {
		this.rightmargin = rightmargin;
	}
	public String getTopmargin() {
		return topmargin;
	}
	public void setTopmargin(String topmargin) {
		this.topmargin = topmargin;
	}
	public String getBottommargin() {
		return bottommargin;
	}
	public void setBottommargin(String bottommargin) {
		this.bottommargin = bottommargin;
	}
	public String getColwidth() {
		return colwidth;
	}
	public void setColwidth(String colwidth) {
		this.colwidth = colwidth;
	}
	public String getColheight() {
		return colheight;
	}
	public void setColheight(String colheight) {
		this.colheight = colheight;
	}
	public String getLine1fontsize() {
		return line1fontsize;
	}
	public void setLine1fontsize(String line1fontsize) {
		this.line1fontsize = line1fontsize;
	}
	public String getLine2fontsize() {
		return line2fontsize;
	}
	public void setLine2fontsize(String line2fontsize) {
		this.line2fontsize = line2fontsize;
	}
	public String getLine1fontfamily() {
		return line1fontfamily;
	}
	public void setLine1fontfamily(String line1fontfamily) {
		this.line1fontfamily = line1fontfamily;
	}
	public String getLine2fontfamily() {
		return line2fontfamily;
	}
	public void setLine2fontfamily(String line2fontfamily) {
		this.line2fontfamily = line2fontfamily;
	}
	public ArrayList getSelectedTkts() {
		return selectedTkts;
	}
	public void setSelectedTkts(ArrayList selectedTkts) {
		this.selectedTkts = selectedTkts;
	}
	public ArrayList<Entity> getTicketsList() {
		return ticketsList;
	}
	public void setTicketsList(ArrayList<Entity> ticketsList) {
		this.ticketsList = ticketsList;
	}
}
