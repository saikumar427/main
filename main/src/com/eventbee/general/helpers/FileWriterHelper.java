package com.eventbee.general.helpers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import com.eventbee.general.EbeeConstantsF;

public class FileWriterHelper {
	
	public static void doWrite(String fileName,String content){
		try {
		    String filepath=EbeeConstantsF.get("temp.previewfiles.path","/uploads");
		    fileName=filepath+"/"+fileName+".html";;
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(content);
            out.write("\n");
            out.close();
        } 
		catch (IOException e) {
            System.out.println("IOException:");
            e.printStackTrace();
        }

	}
}
