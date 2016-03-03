package com.eventbee.namedquery;

import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
public class NQEbeeConstantsF {

private static Map propmap=new HashMap();
private static Map misspropmap=new HashMap();

public static final String IMAGE_CLUBS_PATH="image.clubs.path";
public static final String CREDIT_CARD_VENDOR="test";


static {
initialize();
}

public static String get(String name,String def){
	if(!propmap.containsKey(name)){
		if(misspropmap.containsKey(name)){
			int count=Integer.parseInt(misspropmap.get(name).toString());
			count++;
			misspropmap.put(name,count);
		}else{
			misspropmap.put(name,1);
		}
		return def;
	}
	else{
		return (String)propmap.get(name);
	}
}

public static void setProperty(String name, String value){

propmap.put(name,value);

}

public static Map getAllProperties(){
	return propmap;
}

public static Map getAllMisssedProperties(){
	return misspropmap;
}

public static void reload(){
	initialize();
}

private static void initialize(){
	misspropmap.clear();
	propmap.clear();
	try {

		String fileconfproppath=System.getProperty("ebee.conf.location","/opt/eventbee/");
	File file= new File(fileconfproppath);


		if(file.isDirectory()){
		String[] files=file.list();
			for(int i=0;i<files.length;i++){
				if(files[i].endsWith(".ebeeprops")){
					try{

					InputStream in= new FileInputStream(new File(file,files[i]));
					Properties pp=new Properties();
					pp.load(in);
					in.close();
					propmap.putAll((Map)pp);
					}catch(IOException e1){
						System.err.println("Exception:in reading property file "+ files[i]+" "+ e1.getMessage());
					}
				}
			}

		}
	}catch(Exception e) {
			System.err.println("IOException: " + e.getMessage());
	}
}

public static void main(String args[]){

}

}
