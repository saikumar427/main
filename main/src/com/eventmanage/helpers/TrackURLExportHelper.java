package com.eventmanage.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventbee.general.helpers.ReportGenerator;

public class TrackURLExportHelper {

	
	public static String exportToExcel(ArrayList<HashMap<String, String>> mapList,ArrayList fieldNames, String type,String exporttype){
		System.out.println("\n In TrackURLExeclExport exportToExcel method:: exporttype: "+exporttype+" type::"+type);
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
				
				if(exporttype.equals("csv") && fieldNm.contains(","))
					report.fillColumn(content,"","\""+fieldNm+"\"");
				else 
					report.fillColumn(content,"",fieldNm);
		
			
				
				//System.out.println("fieldNm::"+fieldNm);
				dbFieldNames.add(fieldNm);
			}
			report.endRow(content);
			HashMap colContent=new HashMap();
			
			for(int i=0;i<mapList.size();i++){
		       HashMap hashmap=mapList.get(i);
		       for(int k=0; k<dbFieldNames.size();k++){
		    	   String fieldNm = dbFieldNames.get(k);
		    	// System.out.println("fieldNm12::::"+fieldNm);
		    	// System.out.println("hashmap::::"+hashmap);
		    	   //String fieldValue = GenUtil.AllXMLEncode((String)hashmap.get(fieldNm));
		    	
		    	   String fieldValue = (String)hashmap.get(fieldNm);
		    	   if("Transaction Date".equals(fieldNm))
		    		   fieldValue = fieldValue.substring(5,7)+"/"+fieldValue.substring(8)+"/"+fieldValue.substring(0,4);

	    		   if(fieldValue!=null && fieldValue.contains("\n"))
	    			   fieldValue = fieldValue.replaceAll("\n", ", ");
	    		   if(fieldValue!=null && exporttype.equals("csv") && fieldValue.contains(","))
	    			   fieldValue="\""+fieldValue+"\"";
	    		   colContent.put(fieldNm,fieldValue);
		    	   
		       }
	        report.startRow(content,null);
			for(int colindex=0;colindex<dbFieldNames.size();colindex++)
				  report.fillColumn(content,"",(String)colContent.get(dbFieldNames.get(colindex)));
			report.endRow(content);	
			}
			
	  }else{
		report.startRow(content,null);
		report.fillColumn(content,null,"No Records");
		report.endRow(content);
	  }
	  report.endTable(content);
	  report.endContent(content);
	  //System.out.println("over::::"+content);
	  return content.toString();
	 }
}
