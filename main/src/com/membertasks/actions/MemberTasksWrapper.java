package com.membertasks.actions;

import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.user.beans.UserData;

public class MemberTasksWrapper extends ActionSupport implements Preparable,ValidationAware{
  /**
	 * 
	 */
	private static final long serialVersionUID = 266993418092068625L;
	public String userId="";
	protected String actionTitle="";
	
 	public String getActionTitle() {
	return actionTitle;
 	}
 	public void setActionTitle(String actionTitle) {
	this.actionTitle = actionTitle;
 	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("in wrapper prepare: ");
		Map session = ActionContext.getContext().getSession();
		UserData user=(UserData)session.get("SESSION_USER");
		if(user!=null){
			userId=user.getUid();
			System.out.println("in wrapper prepare: "+userId);
		}else{
			System.out.println("in wrapper prepare: no login");
		}
	}
	
	public String getMgrId() {
		return userId;
	}
	public void setMgrId(String userId) {
		this.userId = userId;
	}
	

	
}
