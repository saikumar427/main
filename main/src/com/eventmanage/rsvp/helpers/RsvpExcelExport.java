package com.eventmanage.rsvp.helpers;

	import java.util.ArrayList;
import java.util.HashMap;

import com.event.helpers.I18n;
import com.eventbee.general.helpers.ReportGenerator;
public class RsvpExcelExport {

			public static String exportToExcel(ArrayList<HashMap<String, String>> mapList,ArrayList fieldNames, String type,String exporttype,
					HashMap<String, String> customAttribsMap,HashMap<String,String> rsvpReportsColumnMap,HashMap<String,String> rsvpEbeeI18nMapping){
			System.out.println("In RsvpExeclExport exportToExcel method:: exporttype: "+exporttype+" type::"+type);
			ReportGenerator report=ReportGenerator.getReportGenerator(exporttype);
			StringBuffer content= new StringBuffer();
			ArrayList<String> dbFieldNames = new ArrayList<String>();
		    report.startContent(content,"");
		    int cols=fieldNames.size();
		    report.startTable(content,null,cols+"");
			if (mapList!=null&&mapList.size()>0){
				report.startRow(content,null);
				for(int colindex=0;colindex<fieldNames.size();colindex++){
					String fieldNm = (String)fieldNames.get(colindex);
					 if(fieldNm!=null && fieldNm.contains("\n"))
						 fieldNm = fieldNm.replaceAll("\n", " ");
					 
					 if(rsvpReportsColumnMap.containsKey(fieldNm))
						 fieldNm=I18n.getString(rsvpReportsColumnMap.get(fieldNm));
					 else if(customAttribsMap.containsKey(fieldNm))
						 fieldNm=customAttribsMap.get(fieldNm);
					
					if(exporttype.equals("csv") && fieldNm.contains(","))
						report.fillColumn(content,"","\""+fieldNm+"\"");
					else 
						report.fillColumn(content,"",fieldNm);
			
				}
				report.endRow(content);
				HashMap colContent=new HashMap();
				
				for(int i=0;i<mapList.size();i++){
			       HashMap hashmap=mapList.get(i);
			       for(int k=0; k<fieldNames.size();k++){
			    	   String fieldNm = (String)fieldNames.get(k);
			    	
			    	   String fieldValue = (String)hashmap.get(fieldNm);
		    		   if(fieldValue!=null && fieldValue.contains("\n"))
		    			   fieldValue = fieldValue.replaceAll("\n", ", ");
		    		   if(fieldValue!=null && exporttype.equals("csv") && fieldValue.contains(","))
		    			   fieldValue="\""+fieldValue+"\"";
		    		   
		    		   if(rsvpEbeeI18nMapping.containsKey(fieldValue))
		    			   fieldValue=I18n.getString(rsvpEbeeI18nMapping.get(fieldValue));
		    		   colContent.put(fieldNm,fieldValue);
			    	   
			       }
		        report.startRow(content,null);
				for(int colindex=0;colindex<fieldNames.size();colindex++)
					  report.fillColumn(content,"",(String)colContent.get(fieldNames.get(colindex)));
				report.endRow(content);	
				}
				
		}else{
			report.startRow(content,null);
			report.fillColumn(content,null,I18n.getString("rep.excel.no.records.lbl"));
			report.endRow(content);
		}
		report.endTable(content);
		report.endContent(content);
		return content.toString();
		}
	}


