package com.myevents.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventbee.general.helpers.ReportGenerator;

public class ExportToExcelHelper {
	
	public static String exportToExcel(ArrayList<HashMap<String, String>> mapList,ArrayList fieldNames, String type,String exporttype,HashMap<String,String> customAttribsMap,HashMap<String,String> reportsColumnMap ){
		System.out.println("In ExportToExcelHelper exportToExcel method:: exporttype: "+exporttype);
		ReportGenerator report=ReportGenerator.getReportGenerator(exporttype);
		StringBuffer content= new StringBuffer();
	    report.startContent(content,"");
	    int cols=fieldNames.size();
	    report.startTable(content,null,cols+"");
		if (mapList!=null&&mapList.size()>0){
			report.startRow(content,null);
			for(int colindex=0;colindex<fieldNames.size();colindex++){
				String fieldNm = (String)fieldNames.get(colindex);
							
				if(reportsColumnMap.containsKey(fieldNm))
					fieldNm=reportsColumnMap.get(fieldNm);
				else if(customAttribsMap.containsKey(fieldNm))
					fieldNm=customAttribsMap.get(fieldNm);
				
				if(exporttype.equals("csv") && fieldNm.contains(","))
					report.fillColumn(content,"","\""+fieldNm+"\"");
				else 
					report.fillColumn(content,"",fieldNm);
				
			}
			report.endRow(content);
			HashMap colContent=new HashMap();
			Double ccprofee=0.0;
	    	Double servicefee=0.0;
	    	Double collservicefee=0.0;
	    	Double ticketstotal=0.0;
	    	Double totalnet=0.0;Double ntsc=0.0;
	    	Double esf=0.0;Double eccf=0.0;Double balance=0.0;
	    	Double otherccfee=0.0;
			for(int i=0;i<mapList.size();i++){
		       HashMap hashmap=(HashMap)mapList.get(i);
		       for(int k=0; k<fieldNames.size();k++){
		    	   String fieldNm = (String)fieldNames.get(k);
		    	   String fieldValue = (String)hashmap.get(fieldNm);
		    	   if("TD".equals(fieldNm)) 
		    		   fieldValue = fieldValue.substring(5,7)+"/"+fieldValue.substring(8)+"/"+fieldValue.substring(0,4);
		    	   //if(fieldValue==null) fieldValue="";
	    		   if(fieldValue.contains("\n"))
	    			   fieldValue = fieldValue.replaceAll("\n", ", ");
	    		   if(exporttype.equals("csv") && fieldValue.contains(","))
	    			   fieldValue="\""+fieldValue+"\"";
	    		   colContent.put(fieldNm,fieldValue);
		    	 /* if(type.equals("transaction")){
		    		   if(fieldNm.equals("CCPF"))
		    			   ccprofee += Double.parseDouble((String)hashmap.get(fieldNm));
		    	   	   if(fieldNm.equals("SF"))
		    	   		   servicefee += Double.parseDouble((String)hashmap.get(fieldNm));
		    	   	   if(fieldNm.equals("CSF")){
		    	   		   String csfee="";
		    	   		   if(((String)hashmap.get(fieldNm)).indexOf("(beecredits)")>-1){
			    	   			csfee=((String)hashmap.get(fieldNm)).replace("(beecredits)", "");
			    	   			csfee=csfee.trim();
		    	   		   }else if(((String)hashmap.get(fieldNm)).indexOf("(paypal)")>-1){
			    	   			csfee=((String)hashmap.get(fieldNm)).replace("(paypal)", "");
			    	   			csfee=csfee.trim();   
		    	   		   }else
		    	   			   csfee=((String)hashmap.get(fieldNm)).trim();  
		    	   		   
		    	   		   collservicefee += Double.parseDouble(csfee);
		    	   	   }
		    	   	   if(fieldNm.equals("TTC"))
		    	   		   ticketstotal += Double.parseDouble((String)hashmap.get(fieldNm));
		    	   	   if(fieldNm.equals("TNet"))
		    	   		   totalnet += Double.parseDouble((String)hashmap.get(fieldNm));
		    	   	   if(fieldNm.equals("ESF"))
		    	   		   esf += Double.parseDouble((String)hashmap.get(fieldNm));
		    	   	   if(fieldNm.equals("ECCF"))
		    	   		   eccf += Double.parseDouble((String)hashmap.get(fieldNm));
		    	   	   if(fieldNm.equals("NTSC"))
		    	   		   ntsc += Double.parseDouble((String)hashmap.get(fieldNm));
		    	   	   if(fieldNm.equals("Bal"))
		    	   		   balance += Double.parseDouble((String)hashmap.get(fieldNm));
		    	   	   if(fieldNm.equals("OCCF"))
		    	   		   otherccfee += Double.parseDouble((String)hashmap.get(fieldNm));
		    	   }*/
		       }
	        report.startRow(content,null);
			for(int colindex=0;colindex<fieldNames.size();colindex++)
				  report.fillColumn(content,"",(String)colContent.get((String)fieldNames.get(colindex)));
			report.endRow(content);	
			}
			/*if(type.equals("transaction")){
				report.startRow(content,null);
				for(int colindex=0;colindex<fieldNames.size();colindex++){
					String fieldNm = (String)fieldNames.get(colindex);
					if(fieldNm.equals("CCPF"))
						report.fillColumn(content,"",ccprofee.toString());
					else if(fieldNm.equals("SF"))
						report.fillColumn(content,"",servicefee.toString());
					else if(fieldNm.equals("CSF"))
						report.fillColumn(content,"",collservicefee.toString());
					else if(fieldNm.equals("TTC"))
						report.fillColumn(content,"",ticketstotal.toString());
					else if(fieldNm.equals("TNet"))
						report.fillColumn(content,"",totalnet.toString());
					else if(fieldNm.equals("ESF"))
						report.fillColumn(content,"",esf.toString());
					else if(fieldNm.equals("ECCF"))
						report.fillColumn(content,"",eccf.toString());
					else if(fieldNm.equals("NTSC"))
						report.fillColumn(content,"",ntsc.toString());
					else if(fieldNm.equals("Bal"))
						report.fillColumn(content,"",balance.toString());
					else if(fieldNm.equals("OCCF"))
						report.fillColumn(content,"",otherccfee.toString());
					else report.fillColumn(content,"","");
				}	
				report.endRow(content);
			}*/
	}else{
		report.startRow(content,null);
		report.fillColumn(content,null,"No Records");
		report.endRow(content);
	}
	report.endTable(content);
	report.endContent(content);
	return content.toString();
	}
}
