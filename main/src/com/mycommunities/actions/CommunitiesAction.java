package com.mycommunities.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.event.dbhelpers.DiscountsDB;
import com.event.dbhelpers.EventDB;
import com.event.helpers.DataPopulator;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.mycommunities.beans.ClubInfo;
import com.mycommunities.dbhelpers.CommunityDB;
import com.mycommunities.helpers.JsonBuilderHelper;


public class CommunitiesAction extends MyCommunitiesWrapper{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5852831599212588878L;
	private String jsonData="";
	private String servername="";
	private ClubInfo clubInfo=new ClubInfo();
	private ArrayList<Entity> categorytype=new ArrayList<Entity>();
	private List<Entity> countrylist=new ArrayList<Entity>();
	private String configentry="yes";

	public String getConfigentry() {
		return configentry;
	}
	public void setConfigentry(String configentry) {
		this.configentry = configentry;
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}
	public List<Entity> getCountrylist() {
		return countrylist;
	}
	public void setCountrylist(List<Entity> countrylist) {
		this.countrylist = countrylist;
	}
	public ArrayList<Entity> getCategorytype() {
		return categorytype;
	}
	public void setCategorytype(ArrayList<Entity> categorytype) {
		this.categorytype = categorytype;
	}
	public ClubInfo getClubInfo() {
		return clubInfo;
	}
	public void setClubInfo(ClubInfo clubInfo) {
		this.clubInfo = clubInfo;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public void prepare() throws Exception {
		try {
			super.prepare();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public String execute(){
		String entry=DbUtil.getVal("select 'Y' from config where config_id=cast(? as integer) and name=? and value=?", new String[]{userId,"mgr.communities.enable","Y"});
		if(entry==null || !"Y".equals(entry))
		{
		configentry="no";
		}
		 return SUCCESS;
	}
	public String getCommunitiesList(){
		if(userId==null) userId="";
		ArrayList<HashMap<String,String>> communitiesList=new ArrayList<HashMap<String,String>>();
	    if(!"".equals(userId))
		communitiesList=CommunityDB.getCommunitiesList(userId);
		JSONObject js=JsonBuilderHelper.getCommunitiesListJson(communitiesList);
		jsonData=js.toString();
		return "jsondata";
	}
	public String createCommunity(){
		categorytype=DataPopulator.populateCategoryList();
		countrylist=EventDB.getCountries();
		servername=EbeeConstantsF.get("serveraddress","http://www.eventbee.com");
		if(!servername.startsWith("http://")) servername="http://"+servername;
		if(!"".equals(groupId))
		clubInfo = CommunityDB.getClubInfo(groupId);
		return "createcommunity";
	}
	public boolean validateInputData(){
		if("".equals(clubInfo.getClubName().trim())){
			addFieldError("clubInfo.clubName", "Community Name is required");			
		}
		if("".equals(clubInfo.getCommunityURL().trim())){
			addFieldError("clubInfo.communityURL", "URL is required");			
		}
		if ("1".equals(clubInfo.getCategory())) {
			addFieldError("clubInfo.category", "Category is not selected");
		}
		if ("".equals(clubInfo.getCity())) {
			addFieldError("clubInfo.city", "City is empty");
		}
		if ("1".equals(clubInfo.getCountry())) {
			addFieldError("clubInfo.country", "Country is not selected");
		}
		if ("".equals(clubInfo.getDescription())) {
			addFieldError("clubInfo.description", "Description is empty");
		}
		if(!getFieldErrors().isEmpty()){			
			return false;
		}
		return true;
	}
	public String saveCommunityDetails(){
		boolean status=validateInputData();	
		if(status){
			if(clubInfo.getClubId() != null && !clubInfo.getClubId().equals(""))
				CommunityDB.editCommunity(clubInfo);
			else
				CommunityDB.createCommunity(clubInfo,userId);
			return "ajaxmsg";
		}
		else{
			categorytype=DataPopulator.populateCategoryList();
			countrylist=EventDB.getCountries();
			return "inputerror";
		}
		
		
	}
}
