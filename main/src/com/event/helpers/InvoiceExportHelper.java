package com.event.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import com.event.helpers.I18n;



import com.eventbee.general.helpers.ReportGenerator;
 


public class InvoiceExportHelper {
	
	public static String exportToExcel(ArrayList<HashMap<String, String>> mapList,ArrayList fieldNames, String exporttype){
		System.out.println("\n In InvoiceExeclExport exportToExcel method:: exporttype: "+exporttype+" type::");
		ReportGenerator report=ReportGenerator.getReportGenerator(exporttype);
		StringBuffer content= new StringBuffer();
		ArrayList<String> dbFieldNames = new ArrayList<String>();
	    report.startContent(content,"");
	    Double totalservicefee=0.0;
	    int cols=fieldNames.size();
	    report.startTable(content,null,cols+"");
		if (mapList!=null&&mapList.size()>0){
			report.startRow(content,null);
			for(int colindex=0;colindex<fieldNames.size();colindex++){
				String fieldNm = (String)fieldNames.get(colindex);
				 if(fieldNm!=null && fieldNm.contains("\n"))
					 fieldNm = fieldNm.replaceAll("\n", " ");
				String colname="";
				if(fieldNm.indexOf('_')>-1)
					colname=fieldNm.replaceAll("_", " ");
				
				if("Event Name".equalsIgnoreCase(colname)) colname=I18n.getString("global.name.lbl");
				else if("Service Fee".equalsIgnoreCase(colname))colname=I18n.getString("global.cost.lbl");
				else if("Ticket Count".equalsIgnoreCase(colname))colname=I18n.getString("mt.tkt.qty.ph.lbl");
				else if("Total Service Fee Usd".equalsIgnoreCase(colname))colname=I18n.getString("global.total.amnt.lbl");
				else if("Total Service Fee Local Currency".equalsIgnoreCase(colname))colname=I18n.getString("global.total.amnt.lcl.lbl");
				
				//if("Total Service Fee Usd".equals(colname))colname=colname+" ($)";
				
				if(exporttype.equals("csv") && fieldNm.contains(",")){
					report.fillColumn(content,"","\""+colname+"\"");
				}else{ 
					report.fillColumn(content,"",colname);
		
				}
				
				//System.out.println("fieldNm::"+fieldNm);
				
				dbFieldNames.add(fieldNm);
			}
			report.endRow(content);
			HashMap colContent=new HashMap();
			String eventname="";
			//System.out.println("mapList is:"+mapList);
			for(int i=0;i<mapList.size();i++){
		       HashMap hashmap=mapList.get(i);
		       for(int k=0; k<dbFieldNames.size();k++){
		    	   String fieldNm = dbFieldNames.get(k);
		    	   //System.out.println("fieldNm::"+fieldNm);
		    	// System.out.println("hashmap::::"+hashmap);
		    	   //String fieldValue = GenUtil.AllXMLEncode((String)hashmap.get(fieldNm));
		    	   
		    	   String fieldValue = (String)hashmap.get(fieldNm);
		    	   //System.out.println("fieldValue::"+fieldValue);
	    		   
		    	  
	    		   if(fieldValue!=null && fieldValue.contains("\n"))
	    			   fieldValue = fieldValue.replaceAll("\n", ", ");
	    		   if(fieldValue!=null && exporttype.equals("csv") && fieldValue.contains(","))
	    			   fieldValue="\""+fieldValue+"\"";
	    		 if("Event_Name".equals(fieldNm) && eventname.equals(fieldValue))
	    			 colContent.put(fieldNm,"");
	    		else
	    			 colContent.put(fieldNm,fieldValue);
	    			 
	    		
	    		 if("Event_Name".equals(fieldNm))
	    		      eventname=fieldValue;
	    		 if(fieldNm.equals("Total_Service_Fee_Usd"))
	    			 totalservicefee += Double.parseDouble((String)hashmap.get(fieldNm));
		       }
	        report.startRow(content,null);
			for(int colindex=0;colindex<dbFieldNames.size();colindex++){
				report.fillColumn(content,"",(String)colContent.get(dbFieldNames.get(colindex)));
			}
			report.endRow(content);	
			}
			report.startRow(content,null);
			for(int colindex=0;colindex<dbFieldNames.size();colindex++){
				if(colindex==0)
					report.fillColumn(content,"","Total");
				if(fieldNames.contains("Total_Service_Fee_Local_Currency")){
					if(colindex==1 || colindex==2 || colindex==3)
						report.fillColumn(content,"","");
				}else{
					if(colindex==1 || colindex==2)
						report.fillColumn(content,"","");
				}
				if(colindex==3)
				report.fillColumn(content,"",totalservicefee.toString());
			}
			report.endRow(content);	
			
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
