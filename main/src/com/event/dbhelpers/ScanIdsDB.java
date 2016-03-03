package com.event.dbhelpers;

import java.util.ArrayList;

import com.event.beans.ScanIdsData;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class ScanIdsDB {
	public static void insertScanId(String eid,String scanid){
		DbUtil.executeUpdateQuery("insert into event_scanners(eventid, scanid) values(?,?)",new String[]{eid,scanid});
	}
	public static ArrayList<ScanIdsData> getScanIds(String eid){
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList<ScanIdsData> ScanIdsList = new ArrayList<ScanIdsData>();
    	String ScanIdsQUERY = "select scanid,eventid from  event_scanners  where  eventid=?";
    	statobj=dbmanager.executeSelectQuery(ScanIdsQUERY,new String []{eid});
			if(statobj.getStatus()){
				for(int k=0;k<statobj.getCount();k++){	
					ScanIdsData scanIdsDataObj=new ScanIdsData();
					scanIdsDataObj.setEventid(dbmanager.getValue(k,"eventid",""));
					scanIdsDataObj.setScanid(dbmanager.getValue(k,"scanid",""));
					ScanIdsList.add(scanIdsDataObj);
				}					
			}	    	

			return ScanIdsList;
	}

	public static boolean deleteScan(String scanid,String eid)
	{
		
		String deletescan="delete from event_scanners where scanid=? and eventid=?";
		DbUtil.executeUpdateQuery(deletescan,new String[]{scanid,eid});
		return true;
	}

}
