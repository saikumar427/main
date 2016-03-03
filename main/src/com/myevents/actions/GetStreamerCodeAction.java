package com.myevents.actions;

import java.util.HashMap;

import com.user.dbhelpers.UserDisplayAttribsDB;
import com.event.helpers.DisplayAttribsHelper;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class GetStreamerCodeAction  extends MyEventsActionWrapper implements
Preparable, ValidationAware {

	/**
	 * 
	 */
	private HashMap<String,String> streamerData=new HashMap<String,String>();
	private static final long serialVersionUID = 893856529777464540L;
	String module="maineventstreamer";
	private String streamerCodeString="";
	public String execute(){
		System.out.println("GetStreamerCodeAction execute");
		streamerData=UserDisplayAttribsDB.getAttribValues(mgrId, module);
		streamerCodeString=DisplayAttribsHelper.getStreamerCodeString(mgrId,streamerData);
		return SUCCESS;
	}

	public HashMap<String, String> getStreamerData() {
		return streamerData;
	}

	public void setStreamerData(HashMap<String, String> streamerData) {
		this.streamerData = streamerData;
	}

	public String getStreamerCodeString() {
		return streamerCodeString;
	}

	public void setStreamerCodeString(String streamerCodeString) {
		this.streamerCodeString = streamerCodeString;
	}

	public void prepare() throws Exception {
		try {
			super.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
}
