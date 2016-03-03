package com.eventregister.customquestions.beans;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAttribute {
	private String isActive;
	private String eraseonFocus;
	private String isRequired;
	private String versionNumber;
	private String textboxSize="10";
	private String rows="10";
	private String cols="70";
	private String position;
	private String attribId;
	private String attribSetid;
	private String attributeName;
	private String attributeType;
	private String lastUpdated;
	private String attributeName_shortForm;
	private String defaultValue;
	private ArrayList attriboptions=new ArrayList();
	private HashMap customAttributeHash=new HashMap();
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getEraseonFocus() {
		return eraseonFocus;
	}
	public void setEraseonFocus(String eraseonFocus) {
		this.eraseonFocus = eraseonFocus;
	}
	public String getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getTextboxSize() {
		return textboxSize;
	}
	public void setTextboxSize(String textboxSize) {
		this.textboxSize = textboxSize;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getCols() {
		return cols;
	}
	public void setCols(String cols) {
		this.cols = cols;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getAttribId() {
		return attribId;
	}
	public void setAttribId(String attribId) {
		this.attribId = attribId;
	}
	public String getAttribSetid() {
		return attribSetid;
	}
	public void setAttribSetid(String attribSetid) {
		this.attribSetid = attribSetid;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getAttributeName_shortForm() {
		return attributeName_shortForm;
	}
	public void setAttributeName_shortForm(String attributeName_shortForm) {
		this.attributeName_shortForm = attributeName_shortForm;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public ArrayList getAttriboptions() {
		return attriboptions;
	}
	public void setAttriboptions(ArrayList attriboptions) {
		this.attriboptions = attriboptions;
	}
	public HashMap getCustomAttributeHash() {
		return customAttributeHash;
	}
	public void setCustomAttributeHash(HashMap customAttributeHash) {
		this.customAttributeHash = customAttributeHash;
	}

	

}
