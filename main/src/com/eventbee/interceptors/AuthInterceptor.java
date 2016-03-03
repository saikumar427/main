package com.eventbee.interceptors;

import java.util.HashMap;
import java.util.Map;

import com.event.dbhelpers.EventDB;
import com.opensymphony.xwork2.Action;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.user.beans.UserData;

public class AuthInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4706688402321386754L;

	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		//This is not required for submanager checking commented on 04-10-2013
		/*if(session.get("SESSION_SUBMGR")!= null) {
			
		}else*/ 
		if(session.get("SESSION_USER") == null) {
			System.out.println("no user in AuthInterceptor: ");
			return Action.ERROR;
		}
		UserData user=(UserData)session.get("SESSION_USER");
		if(user!=null){
			if(!EventDB.isValidUser(user.getUid()))
			{session.put("SESSION_USER",null);System.out.println("blocked user logout mgrid::"+user.getUid());return Action.ERROR;}
			
		Map context = new HashMap();
		context.put("marketingUser", user.getMarketingUser());
		context.put("currentaction_original", invocation.getProxy().getNamespace().replaceAll("/", "")+"."+invocation.getInvocationContext().getName());

		invocation.getInvocationContext().getValueStack().push(context);
		}
		return invocation.invoke();
	}
	
}