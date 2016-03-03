package com.event.beans;

import java.util.ArrayList;
import java.util.List;
import com.eventbee.beans.Entity;

public class CustomAttribute {
	
	private String name = "";
	private String size = "10";
	private String rows = "5";
	private String columns = "10";
	private String qtype = "";
	private String isRequired = "Optional";	
	private List<Entity> optionsList;
	private String option="";
	private String attribid = "";
	private String attribsetid = "";
	private String propValue="";
	private ArrayList chkList = new ArrayList();
	private String subQuestionOf="no";
	private boolean isSubQuestion=false;
	private String subQnsCount="";
	
	public ArrayList getChkList() {
		return chkList;
	}
	public void setChkList(ArrayList chkList) {
		this.chkList = chkList;
	}
	public String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	public String getAttribsetid() {
		return attribsetid;
	}
	public void setAttribsetid(String attribsetid) {
		this.attribsetid = attribsetid;
	}
	public String getAttribid() {
		return attribid;
	}
	public void setAttribid(String attribid) {
		this.attribid = attribid;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public CustomAttribute(){
		optionsList = new ArrayList<Entity>();
	}
	public List<Entity> getOptionsList() {
		return optionsList;
	}
	public void setOptionsList(List<Entity> optionsList) {
		this.optionsList = optionsList;
	}
	public String getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public String getQtype() {
		return qtype;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	public String getSubQuestionOf() {
		return subQuestionOf;
	}
	public void setSubQuestionOf(String subQuestionOf) {
		this.subQuestionOf = subQuestionOf;
	}
	public boolean isSubQuestion() {
		return isSubQuestion;
	}
	public void setSubQuestion(boolean isSubQuestion) {
		this.isSubQuestion = isSubQuestion;
	}
	public String getSubQnsCount() {
		return subQnsCount;
	}
	public void setSubQnsCount(String subQnsCount) {
		this.subQnsCount = subQnsCount;
	}
}
