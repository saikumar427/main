package com.mycommunities.actions;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.mycommunities.beans.DiscountData;
import com.mycommunities.dbhelpers.CommunityDiscountDB;
import com.mycommunities.helpers.JsonBuilderHelper;


public class ManageDiscountsAction extends MyCommunitiesWrapper{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2783737413379354179L;
	private static Logger log = Logger.getLogger(ManageDiscountsAction.class);
	private String id ="";	
	private String msgKey="";
	private List<DiscountData> discountsList=new ArrayList<DiscountData>();
	private String jsonData = "";
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public List<DiscountData> getDiscountsList() {
		return discountsList;
	}

	public void setDiscountsList(List<DiscountData> discountsList) {
		this.discountsList = discountsList;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	@Override
	public void prepare() throws Exception {
		try {
			// TODO Auto-generated method stub
			super.prepare();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public String populateDiscountsDetails(){		
		discountsList=CommunityDiscountDB.getEventDiscountsList(groupId);
		System.out.println("discountsList"+discountsList.size());
		List a=new ArrayList(discountsList);
		jsonData = JsonBuilderHelper.getDiscountsJson(a).toString();
		return "jsondata";
	}
	public String delete(){
		log.info("deleting... discounts");
		CommunityDiscountDB.deleteDiscountData(groupId, id);
		msgKey="discount.deleted";
		return "deleted";		
	}
}
