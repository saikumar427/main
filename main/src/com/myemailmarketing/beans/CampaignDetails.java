package com.myemailmarketing.beans;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;


public class CampaignDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2162732998716294735L;
	private String campName="";
	private String campDescType="html";
	private String campContent="";
	private String backgroundType="COLOR";
	private String backgroundColor="#FFFFFF";
	private String backgroungImage="";
	private String bordercolor="#FFFFFF";
	private String borderwidth="";
	private String campId="";
	private String campTitle="";
	private String background="";
	private String textcontent="";
	private String description="";
	private String previewcontent="";
	public String getPreviewcontent()
	{
		return previewcontent;
	}
	public void setPreviewcontent(String previewcontent)
	{
		this.previewcontent=previewcontent;
	}
	
	public String getTextcontent() {
		return textcontent;
	}
	public void setTextcontent(String textcontent) {
		this.textcontent = textcontent;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getBackgroundType() {
		return backgroundType;
	}
	public void setBackgroundType(String backgroundType) {
		this.backgroundType = backgroundType;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getBackgroungImage() {
		return backgroungImage;
	}
	public void setBackgroungImage(String backgroungImage) {
		this.backgroungImage = backgroungImage;
	}
	
	public String getCampDescType() {
		return campDescType;
	}
	public void setCampDescType(String campDescType) {
		this.campDescType = campDescType;
	}
	public String getCampContent() {
		return campContent;
	}
	public void setCampContent(String campContent) {
		this.campContent = campContent;
	}
	
	public String getBordercolor() {
		return bordercolor;
	}
	public void setBordercolor(String bordercolor) {
		this.bordercolor = bordercolor;
	}
	public String getBorderwidth() {
		return borderwidth;
	}
	public void setBorderwidth(String borderwidth) {
		this.borderwidth = borderwidth;
	}
	public String getCampId() {
		return campId;
	}
	public void setCampId(String campId) {
		this.campId = campId;
	}
	/**
	 * @param campName the campName to set
	 */
	public void setCampName(String campName) {
		this.campName = campName;
	}
	/**
	 * @return the campName
	 */
	public String getCampName() {
		return campName;
	}
	/**
	 * @param campTitle the campTitle to set
	 */
	public void setCampTitle(String campTitle) {
		this.campTitle = campTitle;
	}
	/**
	 * @return the campTitle
	 */
	public String getCampTitle() {
		return campTitle;
	}
	public String toString() {
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception e) {
		}
		return super.toString();
	}
}
