package com.mycommunities.actions;

import java.util.HashMap;

import com.eventbee.general.DbUtil;
import com.eventbee.general.ShortUrlPattern;
import com.mycommunities.beans.ClubInfo;
import com.mycommunities.dbhelpers.CommunityDB;

public class IntegrationLinksAction extends MyCommunitiesWrapper{

	private static final long serialVersionUID = -3773514102198232135L;
	private String msgKey="";
	HashMap<String,String> clubDetails=new HashMap<String,String>();
	private ClubInfo clubInfo=new ClubInfo();
	public ClubInfo getClubInfo() {
		return clubInfo;
	}

	public void setClubInfo(ClubInfo clubInfo) {
		this.clubInfo = clubInfo;
	}

	public HashMap<String, String> getClubDetails() {
		return clubDetails;
	}

	public void setClubDetails(HashMap<String, String> clubDetails) {
		this.clubDetails = clubDetails;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public void prepare(){		
	}
	public String execute(){
		if(groupId==null) groupId="";
		if(!"".equals(groupId))
		clubDetails=CommunityDB.getClubDetails(groupId);
		
		String userid=(String)clubDetails.get("mgr_id");
		String loginname=DbUtil.getVal("select login_name from authentication where auth_id=to_number(?,'99999999999')", new String []{userid});
		String communityurl=ShortUrlPattern.get(loginname)+"/community/"+(String)clubDetails.get("code");
		String loginurl=ShortUrlPattern.get(loginname)+"/community/"+(String)clubDetails.get("code")+"/login";
		String signupurl=ShortUrlPattern.get(loginname)+"/community/"+(String)clubDetails.get("code")+"/signup";
		String renewurl=ShortUrlPattern.get(loginname)+"/community/"+(String)clubDetails.get("code")+"/renew";
		clubInfo.setLoginURL(loginurl);
		clubInfo.setSignupURL(signupurl);
		clubInfo.setRenewURL(renewurl);
		clubInfo.setCommunityURL(communityurl);
		return "success";
	}
}
