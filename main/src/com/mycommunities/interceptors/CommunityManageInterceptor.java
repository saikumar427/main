package com.mycommunities.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mycommunities.beans.ClubInfo;
import com.mycommunities.dbhelpers.CommunityDB;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.user.beans.UserData;


public class CommunityManageInterceptor extends AbstractInterceptor {


	
	private static final long serialVersionUID = 1292940913535131787L;
	private Map actionTitleMap;
	private Map menuMap;
	
	
	public Map getActionTitleMap() {
		return actionTitleMap;
	}
	public void setActionTitleMap(Map actionTitleMap) {
		this.actionTitleMap = actionTitleMap;
	}
	public Map getMenuMap() {
		return menuMap;
	}
	public void setMenuMap(Map menuMap) {
		this.menuMap = menuMap;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String groupId="";
		Map session = invocation.getInvocationContext().getSession();
		if(session.get("SESSION_USER") == null) {
			
			return Action.ERROR;
		}
		UserData user=(UserData)session.get("SESSION_USER");
		Object obj=invocation.getInvocationContext().getParameters().get("groupId");		
		ClubInfo cData=new ClubInfo();
		if(obj!=null){
			String[] objs=(String[])obj;
			groupId=objs[0];
			System.out.println("groupId in interceptor is: "+groupId);
			System.out.println("uid in interceptor is: "+user.getUid());
			if(!"".equals(groupId) && groupId != null)
				cData=CommunityDB.getClubInfo(groupId);
			else{
				HttpServletRequest request = ServletActionContext.getRequest();
				System.out.println("*** In CommunityManageInterceptor groupId empty case::getRequestURI: "+request.getRequestURI());
				System.out.println("*** In CommunityManageInterceptor groupId empty case::getQueryString: "+request.getQueryString());
				return Action.ERROR;
			}
			//System.out.println("eid in interceptor is: "+groupId);
			if(cData==null)
				return Action.ERROR;
			System.out.println("mgrid in interceptor is: "+cData.getManagerId());
		    if((user.getUid()).equals(cData.getManagerId())){
		    	Map context = new HashMap();
				context.put("communityname",cData.getClubName());
				context.put("actionTitleMap", actionTitleMap);
				context.put("menuMap", menuMap);
				invocation.getInvocationContext().getValueStack().push(context);	
		    }else{
		    	return Action.ERROR;
		    }
		}else{
			System.out.println("groupId not found in interceptor");
			//addActionError(invocation, "You must be authenticated to access this page");
			//return Action.ERROR;
		}
		Map context = new HashMap();
		context.put("marketingUser", user.getMarketingUser());
		invocation.getInvocationContext().getValueStack().push(context);
		return invocation.invoke();
	}

}
