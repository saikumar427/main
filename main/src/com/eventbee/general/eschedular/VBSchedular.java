package com.eventbee.general.eschedular;

import java.util.Timer;
import java.util.TimerTask;

public class VBSchedular {
	
	Timer timer=null;
	String serverAddress = "http://"+com.eventbee.general.EbeeConstantsF.get("serveraddress","www.eventbee.com");
	
	private static VBSchedular timertest=null;

	public static  VBSchedular getTimerInstance(){
		if(timertest==null)timertest=new VBSchedular();
		return timertest;
	}
	
	public void runSchedular(long timeInterval) {
		System.out.println("\n Schedular starting...");
    	if(timer!=null) timer.cancel();
    	timer = new Timer();
        timer.schedule(new ProcessTask(), 0, timeInterval);
    }
	
	public void stopSchedular(){
    	System.out.println("\n Schedular stopped.");
    	if(timer!=null) timer.cancel();
        timer=null;
    }
    
    public String getStatus(){
    	if(timer==null)
    		return "stopped";
    	else
    		return "running";
    }
    
    class ProcessTask extends TimerTask {
        
        public void run() {
        	System.out.println("running...");
            com.eventbee.util.CoreConnector cc1=new com.eventbee.util.CoreConnector(serverAddress+"/main/utiltools/volumebee/processtransactions.jsp");
        	cc1.setTimeout(30000);
        	cc1.MGet();
        }
    }

}


