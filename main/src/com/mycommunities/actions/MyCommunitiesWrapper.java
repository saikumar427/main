package com.mycommunities.actions;

import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.user.beans.UserData;

public class MyCommunitiesWrapper extends ActionSupport implements Preparable,ValidationAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1937983502641759859L;
	public String userId="";
	protected String actionTitle="";
	protected String groupId="";
	public String mgrId="";
		
	
	public String getMgrId() {
		return mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getActionTitle() {
		return actionTitle;
	}

	public void setActionTitle(String actionTitle) {
		this.actionTitle = actionTitle;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("in wrapper prepare: ");
		Map session = ActionContext.getContext().getSession();
		UserData user=(UserData)session.get("SESSION_USER");
		if(user!=null){
			userId=user.getUid();
			mgrId=userId;
			System.out.println("in wrapper prepare: "+userId);
		}else{
			System.out.println("in wrapper prepare: no login");
		}
		
	}
	

}
