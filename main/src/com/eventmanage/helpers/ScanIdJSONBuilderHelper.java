package com.eventmanage.helpers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.event.beans.ScanIdsData;
import com.event.beans.TrackURLData;

public class ScanIdJSONBuilderHelper {
	public static JSONObject getScanIdsListJson(ArrayList<ScanIdsData> scanIDsList,ScanIdsData scanIdsData){
		JSONObject allScanIdsObject=new JSONObject();
		JSONArray scanIdsobj=new JSONArray();
		try{
			for(int i=0;i<scanIDsList.size();i++){
				JSONObject scanidsObject=new JSONObject();
				ScanIdsData cListObj= scanIDsList.get(i);
				scanidsObject.put("scanid",cListObj.getScanid());
				scanidsObject.put("id",cListObj.getEventid());
				scanidsObject.put("action", "Manage");
				scanIdsobj.put(scanidsObject);
			}
			allScanIdsObject.put("citems",scanIdsobj);
		}
		catch(Exception e){
			System.out.println("There is an error in processing the JSON Data for Scanids");
		}
	return allScanIdsObject;
	}
}
