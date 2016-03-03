package com.myevents.beans;


public class BoxOfficeData {
	
	private String boxoffice_id="";
	private String title="";
	private String description="";
	private String photourl="";
	private String eventsdisplaytype="ALL";
	private String displayorder="ANY";
	private boolean enabled;
	private String fbtitle="UpComing Events";
	private String fbdescription="";
	private String fbpagination="yes";
	private String fbpaginationsize="3";
	private String fbbuybutton="Buy Tickets";
	private String fbnoevtsmsg="No upcoming events available!";
	
	public String getFbnoevtsmsg() {
		return fbnoevtsmsg;
	}
	public void setFbnoevtsmsg(String fbnoevtsmsg) {
		this.fbnoevtsmsg = fbnoevtsmsg;
	}
	
	public String getFbpaginationsize() {
		return fbpaginationsize;
	}
	public void setFbpaginationsize(String fbpaginationsize) {
		this.fbpaginationsize = fbpaginationsize;
	}
	public String getFbtitle() {
		return fbtitle;
	}
	public void setFbtitle(String fbtitle) {
		this.fbtitle = fbtitle;
	}
	public String getFbdescription() {
		return fbdescription;
	}
	public void setFbdescription(String fbdescription) {
		this.fbdescription = fbdescription;
	}
	public String getFbpagination() {
		return fbpagination;
	}
	public void setFbpagination(String fbpagination) {
		this.fbpagination = fbpagination;
	}
	public String getFbbuybutton() {
		return fbbuybutton;
	}
	public void setFbbuybutton(String fbbuybutton) {
		this.fbbuybutton = fbbuybutton;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getDisplayorder() {
		return displayorder;
	}
	public void setDisplayorder(String displayorder) {
		this.displayorder = displayorder;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhotourl() {
		return photourl;
	}
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}
	public String getEventsdisplaytype() {
		return eventsdisplaytype;
	}
	public void setEventsdisplaytype(String eventsdisplaytype) {
		this.eventsdisplaytype = eventsdisplaytype;
	}
	public String getBoxoffice_id() {
		return boxoffice_id;
	}
	public void setBoxoffice_id(String boxoffice_id) {
		this.boxoffice_id = boxoffice_id;
	}
}
