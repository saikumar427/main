package com.event.beans;

public class PrintedData {
	private String selectedtkt="";
	private String selectedtemplate="0";
	public String getSelectedtemplate() {
		return selectedtemplate;
	}
	public void setSelectedtemplate(String selectedtemplate) {
		this.selectedtemplate = selectedtemplate;
	}
	public String getSelectedtkt() {
		return selectedtkt;
	}
	public void setSelectedtkt(String selectedtkt) {
		this.selectedtkt = selectedtkt;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getFromfix() {
		return fromfix;
	}
	public void setFromfix(String fromfix) {
		this.fromfix = fromfix;
	}
	public String getTofix() {
		return tofix;
	}
	public void setTofix(String tofix) {
		this.tofix = tofix;
	}
	public String getAgentcode() {
		return agentcode;
	}
	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
	}
	private String prefix="";
	private String fromfix="";
	private String tofix="";
	private String agentcode="";
}
