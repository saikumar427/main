package com.eventmanage.seating.beans;

import java.util.ArrayList;

public class Section {
	private String venueid="";
	private String sectionid="";
	private String sectionname="";
	private String no_of_rows="";
	private String no_of_cols="";
	private String background_image="";
	private String seat_image_width="";
	private String seat_image_height="";
	private String layout_css="";

	private ArrayList<Seat> seats=new ArrayList<Seat>();
	public ArrayList<Seat> getSeats() {
		return seats;
	}
	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}
	public String getVenueid() {
		return venueid;
	}
	public void setVenueid(String venueid) {
		this.venueid = venueid;
	}
	public String getSectionid() {
		return sectionid;
	}
	public void setSectionid(String sectionid) {
		this.sectionid = sectionid;
	}
	public String getSectionname() {
		return sectionname;
	}
	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}
	public String getNo_of_rows() {
		return no_of_rows;
	}
	public void setNo_of_rows(String no_of_rows) {
		this.no_of_rows = no_of_rows;
	}
	public String getNo_of_cols() {
		return no_of_cols;
	}
	public void setNo_of_cols(String no_of_cols) {
		this.no_of_cols = no_of_cols;
	}
	public String getBackground_image() {
		return background_image;
	}
	public void setBackground_image(String background_image) {
		this.background_image = background_image;
	}
	public String getSeat_image_width() {
		return seat_image_width;
	}
	public void setSeat_image_width(String seat_image_width) {
		this.seat_image_width = seat_image_width;
	}
	public String getSeat_image_height() {
		return seat_image_height;
	}
	public void setSeat_image_height(String seat_image_height) {
		this.seat_image_height = seat_image_height;
	}
	public String getLayout_css() {
		return layout_css;
	}
	public void setLayout_css(String layout_css) {
		this.layout_css = layout_css;
	}
}
