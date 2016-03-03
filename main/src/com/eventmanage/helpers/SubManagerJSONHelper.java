package com.eventmanage.helpers;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.event.beans.SubManagerData;

public class SubManagerJSONHelper {
	
	private static final Logger log = Logger.getLogger(SubManagerJSONHelper.class);
	
	public static JSONObject getSubManagerJson(List<SubManagerData> subMgrList){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonarrObj = new JSONArray();
		int submgrndex=0;
		int submgrTotal = subMgrList.size();

		try {
			for (SubManagerData subMgrData : subMgrList) {

				String isFirst =(submgrndex==0)?"Y": "N";
				String isLast =(submgrndex == submgrTotal-1)?"Y": "N";
				JSONObject jsonObj_subMgrDetails = new JSONObject();
				String submgrid = subMgrData.getSubMgrId();
				
				jsonObj_subMgrDetails.put("isFirst", isFirst);
				jsonObj_subMgrDetails.put("isLast", isLast);
				jsonObj_subMgrDetails.put("submgrid", submgrid);
				jsonObj_subMgrDetails.put("name", subMgrData.getName());
				jsonObj_subMgrDetails.put("email", subMgrData.getEmail());
				jsonObj_subMgrDetails.put("status", subMgrData.getStatus());
				submgrndex++;
				jsonarrObj.put(jsonObj_subMgrDetails);
			}
			jsonObj.put("SubManagerDetails", jsonarrObj);
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return jsonObj;
	}
	
}
