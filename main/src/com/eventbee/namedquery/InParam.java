package com.eventbee.namedquery;

public class InParam {
	private String key = "";
	private String index;
	private String defaultValues = "";
	private String type = "";
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getDefaultValues() {
		return defaultValues;
	}
	public void setDefaultValues(String defaultValues) {
		this.defaultValues = defaultValues;
	}
}
