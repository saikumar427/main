package com.eventbee.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.event.beans.EventData;
import com.event.beans.SubManagerData;
import com.event.dbhelpers.EventDB;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.user.beans.UserData;
import com.event.dbhelpers.SubManagerDB;
import com.event.helpers.I18n;

public class EventManageInterceptor extends AbstractInterceptor {

	private static final String USER_KEY = "user";
	private Map actionTitleMap;
	private Map menuMap;
	

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		String eid="";
		Map session = invocation.getInvocationContext().getSession();
		HashMap<String, String> permittedActions=null;
		HashMap<String, String> nonPermittedActions=null;
		Object obj=invocation.getInvocationContext().getParameters().get("eid");
		String sub_mgr_id="";
		System.out.println("current action: "+invocation.getInvocationContext().getName());
		System.out.println("session user "+session.get("SESSION_USER"));
		System.out.println("session subuser "+session.get("SESSION_SUBMGR"));
		if(session.get("SESSION_USER") == null) {
		if(session.get("SESSION_SUBMGR")!= null){
			if(obj!=null){
				String[] objs=(String[])obj;
				eid=objs[0];
			}else return "nopermission";
			System.out.println("session sub is not null");
			SubManagerData smd=(SubManagerData)session.get("SESSION_SUBMGR");
			sub_mgr_id=smd.getSubMgrId();
			System.out.println("sub_mgr_id: "+sub_mgr_id);
			if(!"".equals(eid) && eid != null && !"null".equals(eid)){
				try{
					Long.parseLong(eid);
					permittedActions=SubManagerDB.getPermittedActions(sub_mgr_id,eid);
					nonPermittedActions=SubManagerDB.getNonPermittedActions(sub_mgr_id,eid);
					
				}catch(Exception e){
					System.out.println("*** Exception in EventManageInterceptor eid: "+eid);
					return Action.ERROR;
				}
			}
			//if(permittedActions==null || permittedActions.size()==0 || permittedActions.get(invocation.getInvocationContext().getName())==null)
			if(permittedActions==null || permittedActions.size()==0 || nonPermittedActions.get(invocation.getInvocationContext().getName())!=null){
				//if(!(("EditEvent".equals(invocation.getInvocationContext().getName()) && permittedActions.containsKey("EventPageContent")) || ("ManageTickets".equals(invocation.getInvocationContext().getName()) && (permittedActions.containsKey("TicketURLs") || permittedActions.containsKey("DisplayTickets")))))
				if(!(("EditEvent".equals(invocation.getInvocationContext().getName()))))
					return "nopermission";
			}
		}
		   else return Action.ERROR;
		}
		
		UserData user=(UserData)session.get("SESSION_USER");
		EventData edata=null;	
		Map context = new HashMap();
		if(obj!=null){
			String[] objs=(String[])obj;
			eid=objs[0];
			System.out.println("eid in interceptor is: "+eid);
			//System.out.println("uid in interceptor is: "+user.getUid());
			if(!"".equals(eid) && eid != null && !"null".equals(eid)){
				try{
					Long.parseLong(eid);
					edata=EventDB.getEventData(eid);
				}catch(Exception e){
					System.out.println("*** Exception in EventManageInterceptor eid: "+eid);
					return Action.ERROR;
				}
			}else{
				HttpServletRequest request = ServletActionContext.getRequest();
				System.out.println("*** In EventManageInterceptor eid empty case::getRequestURI: "+request.getRequestURI());
				System.out.println("*** In EventManageInterceptor eid empty case::getQueryString: "+request.getQueryString());
				return Action.ERROR;
			}
			//System.out.println("eid in interceptor is: "+eid);
			if(edata==null)
				return Action.ERROR;
			System.out.println("mgrid in interceptor is: "+edata.getMgrId());
			if(session.get("SESSION_SUBMGR")==null){
		    if(user!=null && (user.getUid()).equals(edata.getMgrId())){
		    	
		    	if(!EventDB.isValidUser(user.getUid()))
				{session.put("SESSION_USER",null);System.out.println("blocked user logout mgrid::"+user.getUid());return Action.ERROR;}
			
		    	invocation.getInvocationContext().getParameters().put("mgrId",user.getUid());
		    	
		    }else{
		    	return Action.ERROR;
		    }
			}else invocation.getInvocationContext().getParameters().put("mgrId",edata.getMgrId());
		}else{
			System.out.println("eid not found in interceptor");
			//addActionError(invocation, "You must be authenticated to access this page");
			return Action.ERROR;
		}
		
		String chargeStatus=EventDB.getChargeStatus(invocation.getInvocationContext().getParameters().get("mgrId").toString());
		System.out.println("EventManageInterceptor mgrId: "+invocation.getInvocationContext().getParameters().get("mgrId")+" chargeStatus: "+chargeStatus);
		if("Y".equalsIgnoreCase(chargeStatus)){
			if(session.get("SESSION_USER") != null) 
				session.remove("SESSION_USER");
			if(session.get("SESSION_SUBMGR")!= null)
				session.remove("SESSION_SUBMGR");
			return Action.ERROR;
		}
		
		context.put("eventname", edata.getEventName());
		context.put("eventid", eid);
		context.put("poweredbyEB", edata.getPoweredbyEB());
		context.put("powertype", edata.getPowertype());
		context.put("isrecurring", EventDB.isRecurringEvent(eid));
		context.put("eventstatus", edata.getStatus());
		context.put("currentLevel", edata.getCurrentLevel());
		context.put("currentFee", edata.getCurrentFee());
		context.put("ntsEnable", edata.getNtsStatus());
		context.put("actionTitleMap", actionTitleMap);
		context.put("currentaction_original", invocation.getInvocationContext().getName());
		context.put("menuMap", menuMap);
		if(user!=null)
		context.put("marketingUser", user.getMarketingUser());
		context.put("subMgr", session.get("SESSION_SUBMGR"));
		context.put("submgr_permissions", permittedActions);
		//System.out.println("permittedActions: "+permittedActions);
		invocation.getInvocationContext().getParameters().put("curLvl",edata.getCurrentLevel());
		invocation.getInvocationContext().getParameters().put("pwrType",edata.getPowertype());
		invocation.getInvocationContext().getValueStack().push(context);
		return invocation.invoke();
	}

	private void addActionError(ActionInvocation invocation, String message) {
		Object action = invocation.getAction();
		if(action instanceof ValidationAware) {
			((ValidationAware) action).addActionError(message);
		}
	}

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
}