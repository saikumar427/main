package com.eventbee.general;

import java.util.HashMap;

import com.event.beans.WaitListData;
import com.event.dbhelpers.WaitListDB;

public class WaitListActivationThread implements Runnable{
	private String eid="",wid="";
	private WaitListData waitListData=new WaitListData();
	private HashMap eventinfo=new HashMap();
	public WaitListActivationThread(String eid, String wid, WaitListData waitListData,HashMap eventinfo){
		this.eid=eid;
		this.wid=wid;
		this.waitListData=waitListData;
		this.eventinfo=eventinfo;
	}
	public void run() {
		WaitListDB.sendActivationLink(eid, wid, waitListData,eventinfo);
	}
	

}
