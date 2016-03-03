package com.eventbee.general.helpers;

public class CSVContentGenerator extends ReportGenerator{

	StringBuffer content=new StringBuffer();

	public void startContent(StringBuffer content,String header){
		content.append("");
	}
	public void endcontent(StringBuffer content){
		content.append("");
	}
	public void startTable(StringBuffer content, String style,String cols){
		content.append("");
	}
	public void endTable(StringBuffer content){
		content.append("");
	}
	public void startRow(StringBuffer content, String style){
		content.append("");
	}
	public void endRow(StringBuffer content){
		content.append("\n");
	}

	public void fillColumn(StringBuffer content, String style, String data){
		content.append(data+",");
	}
}
