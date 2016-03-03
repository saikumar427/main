package com.eventbee.general.helpers;
public abstract class ReportGenerator{
		public static ReportGenerator getReportGenerator(String exportType){
			if(exportType.equals("excel"))
				return new ExcelContentGenerator();
			else 
				return new CSVContentGenerator();
				/*if("fo".equals(reportType))
						return new FoContentGenerator();
				else if("excel".equals(reportType))
						return new ExcelContentGenerator();
				else
						return new HTMLContentGenerator();*/
				
		}
	public void startContent(StringBuffer content,String header){}
	public void endContent(StringBuffer content){}
	public void startTable(StringBuffer content,String style,String cols){}
	public void endTable(StringBuffer content){}
	public void startRow(StringBuffer content ,String style){}
	public void endRow(StringBuffer content){}
	public void fillColumn(StringBuffer content,String style,String data){}
}
	 
