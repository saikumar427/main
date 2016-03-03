package com.event.beans.customization;

import java.util.HashMap;

public class GetPhotos {
private String smallRadio="";
private String mediumRadio="";
private String largeRadio="";
private String radioBox="";
private HashMap<String,String> photosMap=new HashMap<String,String>();
private String imageURL="";

public String getRadioBox() {
	return radioBox;
}
public void setRadioBox(String radioBox) {
	this.radioBox = radioBox;
}
public String getSmallRadio() {
	return smallRadio;
}
public void setSmallRadio(String smallRadio) {
	this.smallRadio = smallRadio;
}
public String getMediumRadio() {
	return mediumRadio;
}
public void setMediumRadio(String mediumRadio) {
	this.mediumRadio = mediumRadio;
}
public String getLargeRadio() {
	return largeRadio;
}
public void setLargeRadio(String largeRadio) {
	this.largeRadio = largeRadio;
}
public HashMap<String, String> getPhotosMap() {
	return photosMap;
}
public void setPhotosMap(HashMap<String, String> photosMap) {
	this.photosMap = photosMap;
}
public String getImageURL() {
	return imageURL;
}
public void setImageURL(String imageURL) {
	this.imageURL = imageURL;
}

}
