package com.myevents.actions;

import java.util.HashMap;

import com.event.helpers.DisplayAttribsHelper;
import com.eventbee.general.DbUtil;
import com.user.dbhelpers.UserDisplayAttribsDB;


public class MyOthersAction extends MyEventsActionWrapper 
{
	private String streamerCodeString="";
	private String userName="";
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String execute()
	{
		if("".equals(mgrId) || mgrId==null){
			System.out.println("--- mgrId EMPTY or NULL case --- in MyEventsSummaryAction execute mgrId: "+mgrId);
			return "error";
		}
		
		HashMap<String,String> streamerData=UserDisplayAttribsDB.getAttribValues(mgrId, "maineventstreamer");
		streamerCodeString=DisplayAttribsHelper.getStreamerCodeString(mgrId,streamerData);
		userName=DbUtil.getVal("select getMemberPref(?||'','pref:myurl','') as username",new String[]{mgrId});
		return "success";
		
		
		
	}

	public String getStreamerCodeString() {
		return streamerCodeString;
	}

	public void setStreamerCodeString(String streamerCodeString) {
		this.streamerCodeString = streamerCodeString;
	}

}
