package com.myevents.actions;

import java.util.Map;

import com.eventbee.general.EbeeConstantsF;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.user.beans.UserData;

public class MyEventsActionWrapper extends ActionSupport implements Preparable{
  protected String mgrId="";
  protected String actionTitle="";
  protected String serveraddress="";
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
			mgrId=user.getUid();
			System.out.println("in wrapper prepare: "+mgrId);
		}else{
			System.out.println("in wrapper prepare: no login");
		}
		serveraddress=EbeeConstantsF.get("serveraddress","www.eventbee.com");
		if(serveraddress!=null){
			if(!serveraddress.startsWith("http://")){
				serveraddress="http://"+serveraddress;
			}
			}
	}
	public String getMgrId() {
		return mgrId;
	}
	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}
	public String getServeraddress() {
		return serveraddress;
	}
	public void setServeraddress(String serveraddress) {
		this.serveraddress = serveraddress;
	}

	

}
