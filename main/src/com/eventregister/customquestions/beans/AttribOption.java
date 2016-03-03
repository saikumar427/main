package com.eventregister.customquestions.beans;

import java.util.HashMap;

public class AttribOption {
	private String isDefault;
	private String isSelectable;
	private String option_id;
	private String lastupdated;
	private String optionValue;
	private String optionDisplay;
	private String position;
	private String optgroupName;
	private String versionNumber;
	private HashMap attribOptionHash=new HashMap();

	public HashMap getGenericData(){
		return attribOptionHash;
	}
	public void setGenericData(HashMap attopthash){
		this.attribOptionHash=attopthash;
	}
	public Object getGenericVal(Object key){
		return (this.attribOptionHash).get(key);
	}
	public void setGenericval(Object key,Object val){
		(this.attribOptionHash).put(key,val);
	}
	
	public void setIsDefault(String isdefault){
		this.isDefault=isdefault;
	}
	public String getisDefault(){
		return isDefault;
	}
	public void setIsSelectable(String isselect){
		this.isSelectable=isselect;
	}
	public String getIsSelectable(){
		return isSelectable;
	}
	public void setOptgroupName(String optgrpname){
		this.optgroupName=optgrpname;
	}
	public String getOptgroupName(){
		return optgroupName;
	}
	public String getPosition(){
		return position;
	}
	public void setPosition(String position){
		this.position=position;
	}
	public String getOptionid(){
		return option_id;
	}
	public void setOptionDisplay(String optiondisplay){
		this.optionDisplay=optiondisplay;
	}
	public String getOptionDisplay(){
		return optionDisplay;
	}
	public void setOptionid(String optionid){
		this.option_id=optionid;
	}
	public void setLastUpdated(String updated){
		this.lastupdated=updated;
	}
	public String getLastUpdated(){
		return lastupdated;
	}
	public void setOptionValue(String optionval){
		this.optionValue=optionval;
	}
	public String getOptionValue(){
		return optionValue;
	}
	public void setVersionNumber(String versionnumber){
		this.versionNumber=versionnumber;
	}
	public String getVersionNumber(){
		return versionNumber;
	}
	
}
