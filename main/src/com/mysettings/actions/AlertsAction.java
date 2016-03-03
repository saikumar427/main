package com.mysettings.actions;

import com.membertasks.actions.MemberTasksWrapper;

public class AlertsAction extends MemberTasksWrapper{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7990862197971404861L;
	public void prepare() throws Exception {
		try {
			super.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public String execute(){
		return INPUT;
	}
	public String save(){
		return SUCCESS;
	}
}
