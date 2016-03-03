package com.eventbee.general;

import java.util.HashMap;

public class GetTicketsThread implements Runnable{
	final static String serverAddress = "http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
	HashMap paramMap=new HashMap();
	public GetTicketsThread(HashMap hm) {  
		paramMap = hm;  
    }
	public void run() {
		try{
			com.eventbee.util.CoreConnector cc1=new com.eventbee.util.CoreConnector(com.eventbee.general.EbeeConstantsF.get("CONNECTING_PDF_URL",serverAddress+"/attendee/utiltools/sendPdfMail.jsp"));
			cc1.setArguments(paramMap);
		    cc1.setTimeout(30000);
		    String st=cc1.MGet();
		}catch(Exception e){
			System.out.println("Error in GetTicketsThread: "+e.getMessage());
		}
	}

}
