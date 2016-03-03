package com.mycommunities.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.mycommunities.beans.ClubMemberShip;
import com.mycommunities.beans.DiscountData;

public class JsonBuilderHelper {
		
	
	public static JSONObject getMembershipListJson(ArrayList<ClubMemberShip> memList){
		JSONObject allListsObject=new JSONObject();
		JSONArray listobj=new JSONArray();
		try{
			for(int i=0;i<memList.size();i++){
				JSONObject cObject=new JSONObject();
				ClubMemberShip cObj= memList.get(i);
				cObject.put("name",cObj.getMemberShipName());
				cObject.put("id",cObj.getMemberShipId());
				cObject.put("action", "Manage");
				cObject.put("description",cObj.getDescription());
				listobj.put(cObject);
			}
			allListsObject.put("citems",listobj);
		}
		catch(Exception e){
			System.out.println("There is an error in processing the JSON Data for mail lists");
		}
	return allListsObject;		
	}
	public static JSONObject getDiscountsJson(List<DiscountData> discountsInfoList){
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarrObj = new JSONArray();
		try {
			for (DiscountData discountData : discountsInfoList) {
				JSONObject jsonDiscountData = new JSONObject();
				jsonDiscountData.put("discountName",discountData.getName());
				jsonDiscountData.put("discountType",discountData.getDiscountType());
				jsonDiscountData.put("discountId",discountData.getId());
				jsonarrObj.put(jsonDiscountData);
			}
			jsonobj.put("DiscountDetails", jsonarrObj);
		} catch (Exception ex) {
			// TODO: handle exception
	
		}
		return jsonobj;
	}
	public static JSONObject getCommunitiesListJson(ArrayList<HashMap<String,String>> cList){
		JSONObject allListsObject=new JSONObject();
		JSONArray listobj=new JSONArray();
		try{
			for(int i=0;i<cList.size();i++){
				JSONObject cObject=new JSONObject();
				HashMap<String,String> cMapObj= cList.get(i);
				StringBuffer place=new StringBuffer();
				if(!"".equals(cMapObj.get("city")))
					place.append(cMapObj.get("city"));
				if(!"".equals(cMapObj.get("state")))
					place.append(","+cMapObj.get("state"));
				if(!"".equals(cMapObj.get("country")))
					place.append(","+cMapObj.get("country"));
				
				cObject.put("id",cMapObj.get("clubid"));
				cObject.put("name",cMapObj.get("clubname"));
				cObject.put("place",place);
				cObject.put("action", "Manage");
				cObject.put("createdon",cMapObj.get("created_at"));
				cObject.put("status",cMapObj.get("status"));
				cObject.put("members",cMapObj.get("members"));
				listobj.put(cObject);
			}
			allListsObject.put("citems",listobj);
		}
		catch(Exception e){
			System.out.println("There is an error in processing the JSON Data for mail lists");
		}
	return allListsObject;		
	}
}
