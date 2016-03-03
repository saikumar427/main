package com.submanager.actions;

import java.util.Map;

import com.event.beans.SubManagerData;
import com.eventbee.general.EbeeConstantsF;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.user.beans.UserData;

public class SubmangerAuthWrapper extends ActionSupport implements Preparable{
  protected String submgrId="";
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
		Map session = ActionContext.getContext().getSession();
		SubManagerData user=(SubManagerData)session.get("SESSION_SUBMGR");
		if(user!=null){
			submgrId=user.getSubMgrId();
			System.out.println("in wrapper prepare: "+submgrId);
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

	public String getSubmgrId() {
		return submgrId;
	}
	public void setSubmgrId(String submgrId) {
		this.submgrId = submgrId;
	}
	public String getServeraddress() {
		return serveraddress;
	}
	public void setServeraddress(String serveraddress) {
		this.serveraddress = serveraddress;
	}

	

}
