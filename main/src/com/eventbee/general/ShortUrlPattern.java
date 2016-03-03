package com.eventbee.general;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
public class ShortUrlPattern
{
       public static String get(String value){
		 String serveradd="";
		 serveradd =EbeeConstantsF.get("patternserver","http://www.eventbee.com/p/TO_BE_REPLACED");
		 if(serveradd!=null){
			 if(serveradd.indexOf("TO_BE_REPLACED")!=-1&&value!=null)
				serveradd=serveradd.replaceAll("TO_BE_REPLACED",value);
		 }
       return serveradd;
	}
}