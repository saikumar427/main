package com.eventregister.customquestions.beans;

import java.util.HashMap;

public class CustomAttribSet {
	private String attribSetid;
	private CustomAttribute[] attribs;
	private HashMap customAttribSetHash=new HashMap();

	public void setAttribSetid(String attribsetid){
		this.attribSetid=attribsetid;
	}
	public String getAttribSetid(){
		return attribSetid;
	}
	public CustomAttribute[] getAttributes(){
		return attribs;
	}
	public void setAttributes(CustomAttribute[] attribs){
		this.attribs=attribs;
	}
	public HashMap getGenericData(){
		return customAttribSetHash;
	}
	public void setGenericData(HashMap customattsethash){
		this.customAttribSetHash=customattsethash;
	}
	public Object getGenericVal(Object key){
		return (this.customAttribSetHash).get(key);
	}
	public void setGenericval(Object key,Object val){
		(this.customAttribSetHash).put(key,val);
	}

}
