package com.eventbee.beans;

public class DateTimeBean {
private String ddPart;
private String monthPart;
private String yyPart;
private String hhPart;
private String mmPart;
private String ampm;
private String timeZone;
public DateTimeBean(){
}
public DateTimeBean(String ampm, String ddPart, String hhPart, String mmPart,
		String monthPart, String timeZone, String yyPart) {
	super();
	this.ampm = ampm;
	this.ddPart = ddPart;
	this.hhPart = hhPart;
	this.mmPart = mmPart;
	this.monthPart = monthPart;
	this.timeZone = timeZone;
	this.yyPart = yyPart;
}
public String getDdPart() {
	return ddPart;
}
public void setDdPart(String ddPart) {
	this.ddPart = ddPart;
}
public String getMonthPart() {
	return monthPart;
}
public void setMonthPart(String monthPart) {
	this.monthPart = monthPart;
}
public String getYyPart() {
	return yyPart;
}
public void setYyPart(String yyPart) {
	this.yyPart = yyPart;
}
public String getHhPart() {
	return hhPart;
}
public void setHhPart(String hhPart) {
	this.hhPart = hhPart;
}
public String getMmPart() {
	return mmPart;
}
public void setMmPart(String mmPart) {
	this.mmPart = mmPart;
}
public String getAmpm() {
	return ampm;
}
public void setAmpm(String ampm) {
	this.ampm = ampm;
}
public String getTimeZone() {
	return timeZone;
}
public void setTimeZone(String timeZone) {
	this.timeZone = timeZone;
}


}
