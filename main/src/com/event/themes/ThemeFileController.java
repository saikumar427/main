package com.event.themes;
import java.util.*;
import java.io.*;
import com.eventbee.general.*;
public class ThemeFileController{
	public static String filepath=EbeeConstantsF.get("usertheme.file.path","C:\\");

	public static String readFilesNReturnContent(String filename){
        BufferedReader bufreader =null;
     	String content="";
		try{
			String str=null;
			String sysfilename=filepath+filename;
			System.out.println("sysfilename::::::::::"+sysfilename);
			File fobj=new File(sysfilename);
			System.out.println("fobj::::::::::"+fobj);

			if(fobj.exists()){
				StringBuffer sb=new StringBuffer();
				 bufreader = new BufferedReader(new FileReader(fobj));
				while((str=bufreader.readLine())!=null){
					sb.append(str);
					sb.append("\n");
					}
				content=sb.toString();
			}

		}
		catch(Exception e){
			//EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "/customevents/themefilefunctions.jsp", "exception in readFilesNReturnContent", e.getMessage(), e ) ;
		}
		finally{
			try{

			if(bufreader!=null)
		      bufreader.close();
		     }

		 catch(Exception e){
			System.out.println("Exception occured in themes reader "+e.getMessage());
		 }
	 }
		return content;
	}

	public static void createFiles(String content,String themeid,String ext){
	try{

		String filename=filepath+themeid+ext;
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"ThemeFileController.java======"," file name is"+filename,"",null);
		BufferedWriter bwfile = new BufferedWriter(new FileWriter(filename));
		bwfile.write(content);
		bwfile.close();

	}
	catch(Exception e){

		//EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "/customevents/themefilefunctions.jsp", "exception in createFiles", e.getMessage(), e ) ;
	}

}


}
