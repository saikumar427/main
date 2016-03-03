package com.eventbee.general.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.eventbee.general.EbeeConstantsF;

public class FileReaderHelper {
	static public String getContents(String aFile) {
	    StringBuilder contents = new StringBuilder();
	    try {
	      String filepath=EbeeConstantsF.get("temp.previewfiles.path","/uploads");
	      aFile=filepath+"/"+aFile+".html";;
	      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	      try {
	        String line = null;
	        /*
	        * readLine is a bit quirky :
	        * it returns the content of a line MINUS the newline.
	        * it returns null only for the END of the stream.
	        * it returns an empty String if two newlines appear in a row.
	        */
	        while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	    	System.out.println("Exception in reading the file"+ex.getMessage());
	    }
	    
	    return contents.toString();
	  }

}
