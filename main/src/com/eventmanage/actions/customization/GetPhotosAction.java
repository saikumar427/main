package com.eventmanage.actions.customization;

import com.event.beans.customization.GetPhotos;
import com.opensymphony.xwork2.ActionSupport;

public class GetPhotosAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8031445328995738804L;
	private GetPhotos photos;
	public GetPhotos getPhotos() {
		return photos;
	}
	public void setPhotos(GetPhotos photos) {
		this.photos = photos;
	}
	public String execute(){
		return "photoinput";
	}
}
