package com.eventbee.interceptors;

import java.util.Map;
import com.event.helpers.I18n;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class I18nSessionInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4589651994975546103L;

	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();	
		I18n.putLocaleInSession(session,invocation);
		return invocation.invoke();		
	}
}
