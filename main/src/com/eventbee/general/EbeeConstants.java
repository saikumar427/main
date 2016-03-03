package com.eventbee.general;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
public class EbeeConstants {


private static Map propmap=new HashMap();

public static final String IMAGE_CLUBS_PATH="image.clubs.path";
public static final String CREDIT_CARD_VENDOR="test";


public static final String[] PROPS_FILES=new String[]{"com/eventbee/general/urlprop.prop"};



static {
	initialize();

}




public static String get(String name,String def){

	return ( propmap.get(name)==null   )?def:(String)propmap.get(name);

}



public static void reload(){
	initialize();

}

private static void initialize(){
propmap=new HashMap();
ClassLoader cl1 = EbeeConstants.class.getClassLoader();

for(int i=0;i<PROPS_FILES.length;i++){
try {
			InputStream in= cl1.getResourceAsStream(PROPS_FILES[i]);

			Properties pp=new Properties();
			pp.load(in);
			in.close();
			propmap.putAll((Map)pp);


	}
	catch(Exception e) {
		System.err.println("IOException: " + e.getMessage());
	}
}



}


public static void main(String args[]){

	EbeeConstants.initialize();



}


}
