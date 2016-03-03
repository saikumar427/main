package com.eventbee.filters;
import java.util.*;
import java.io.*;
public class DsnProperty {

private static Map memorymap=new HashMap();


static {
initialize();
}

public static String get(String name,String def){
	if(!memorymap.containsKey(name)){
		return def;
	}else{
		return (String)memorymap.get(name);
	}
}

public static void setProperty(String name, String value){
	memorymap.put(name,value);
}

public static Map getAllProperties(){
	return memorymap;
}



public static void reload(){
	initialize();
}

private static void initialize(){
	memorymap.clear();
	try{
		String fileconfproppath=System.getProperty("ebee.conf.location","/opt/eventbee/");
		File file= new File(fileconfproppath);
		if(file.isDirectory()){
			String[] files=file.list();
			for(int i=0;i<files.length;i++){
				if(files[i].endsWith("dbdsn.ebeeprops")){
					try{
						InputStream in= new FileInputStream(new File(file,files[i]));
						Properties pp=new Properties();
						pp.load(in);
						in.close();
						memorymap.putAll((Map)pp);
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

public static HashMap getFileMap(){
	HashMap filemap=new HashMap();
	try{
		String fileconfproppath=System.getProperty("ebee.conf.location","/opt/eventbee/");
		File file= new File(fileconfproppath);
		if(file.isDirectory()){
		String[] files=file.list();
			for(int i=0;i<files.length;i++){
				if(files[i].endsWith("dbdsn.ebeeprops")){
					try{
						InputStream in= new FileInputStream(new File(file,files[i]));
						Properties pp=new Properties();
						pp.load(in);
						in.close();
						filemap.putAll((Map)pp);
					}catch(IOException e1){
						System.err.println("Exception:in reading property file "+ files[i]+" "+ e1.getMessage());
					}
				}
			}
		}
	}catch(Exception e) {
			System.err.println("IOException: " + e.getMessage());
	}
	
	return filemap;
}

public static void main(String args[]){

}

public static Map getMemorymap() {
	return memorymap;
}

public static void setMemorymap(Map memorymap) {
	DsnProperty.memorymap = memorymap;
}

}
