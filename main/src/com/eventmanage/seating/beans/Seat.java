package com.eventmanage.seating.beans;

public class Seat {
	private String rowid="";
	private String colid="";
	private String seatIndex="";
	private String seatCode="";
	private String isSeat="";
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	public String getColid() {
		return colid;
	}
	public void setColid(String colid) {
		this.colid = colid;
	}
	public String getSeatIndex() {
		return seatIndex;
	}
	public void setSeatIndex(String seatIndex) {
		this.seatIndex = seatIndex;
	}
	public String getSeatCode() {
		return seatCode;
	}
	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}
	public String getIsSeat() {
		return isSeat;
	}
	public void setIsSeat(String isSeat) {
		this.isSeat = isSeat;
	}
}
