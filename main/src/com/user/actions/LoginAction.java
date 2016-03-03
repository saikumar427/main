package com.user.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.event.dbhelpers.FacebookConnectDB;
import com.event.helpers.I18n;
import com.eventbee.general.DbUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.user.beans.UserData;
import com.user.dbhelpers.UserDB;

public class LoginAction extends ActionSupport implements Preparable,ValidationAware{
	
	private static Logger log = Logger.getLogger(LoginAction.class);
	private static final long serialVersionUID = -4351839924633520168L;
    private String uname="";
    private String password="";
    private String usertoken="";
    private String schemeToUse="http";
    private String domain="";
    private String ssldomain="";
    private String message="";
    private String FBAppId="";
    private String source="";
    private String tid="";
    private String tokenId="";
    private String purpose="";
    private String refId="";
    private String actionPattern="";

    private String i18nCode="";
    
    public String getI18nCode() {
		return i18nCode;
	}
	public void setI18nCode(String i18nCode) {
		this.i18nCode = i18nCode;
	}   
    
    public String getActionPattern() {
		return actionPattern;
	}
	public void setActionPattern(String actionPattern) {
		this.actionPattern = actionPattern;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String execute(){	
    	HttpServletRequest request = ServletActionContext.getRequest();
		String scheme=request.getScheme();
		log.debug("request scheme"+scheme);
		FBAppId=FacebookConnectDB.getFBAppId("0");
		if(!schemeToUse.equalsIgnoreCase(scheme)){
			return "needhttps";
		}
		return "success";
	}
	@Override
	public void prepare() throws Exception {
		message="";		
	}
	public String tokenize(){
		
		UserData user=new UserData();
		String isexists=UserDB.validateToken(usertoken);
		if(isexists==null)isexists="";
		if(!"Y".equalsIgnoreCase(isexists)){
			if(!"".equals(tokenId)){
				HashMap<String,String> userMap=new HashMap<String,String>();
				userMap=UserDB.getUserToen(tokenId);
				purpose=userMap.get("actionpattern");
				String eid=userMap.get("eid");
				UserDB.makeTokenExpire(tokenId);
				if(!"".equals(purpose) && !"".equals(eid)){
					actionPattern=purpose+"?eid="+eid;
					return "actionpattern";
				}else if("createevent".equals(purpose)){
					return "createeventredirect";
				}
			}
			
		return "input";
		}
		else{
			
		user.setUid(usertoken);
		//System.out.println("uid in tokenizer: "+usertoken);
		user.setMarketingUser(UserDB.getMarketingUser(usertoken));
		String beeId=DbUtil.getVal("select login_name from authentication where user_id=?", new String[]{usertoken});
		if(beeId==null) beeId="";
		user.setBeeId(beeId);
		Map session = ActionContext.getContext().getSession();
		session.put("SESSION_USER", user);
		
		if(!"".equals(tokenId)){
			HashMap<String,String> userMap=new HashMap<String,String>();
			userMap=UserDB.getUserToen(tokenId);
			purpose=userMap.get("actionpattern");
			String eid=userMap.get("eid");
			UserDB.makeTokenExpire(tokenId);
			if(!"".equals(purpose) && !"".equals(eid)){
				actionPattern=purpose+"?eid="+eid;
				return "actionpattern";
			}else if("createevent".equals(purpose)){
				return "createeventredirect";
			}
				
				
		}
		
		return "tokenized";
		}
	}
	public String logout(){
		Map session = ActionContext.getContext().getSession();
        i18nCode= I18n.getActualLangFromSession();
		session.remove("SESSION_USER");
		return "loggedout";
	}
	public String authenticate(){

		ResourceBundle resourceBundle=I18n.getResourceBundle();
		FBAppId=FacebookConnectDB.getFBAppId("0");
		System.out.println("in authentication...");
		Map session = ActionContext.getContext().getSession();
		HashMap<String,String> userMap=new HashMap<String,String>();
		
		if(session.get("SESSION_SUBMGR")!=null || session.get("SESSION_USER")!=null){
			UserData udata=(UserData)session.get("SESSION_USER");
			if(!"".equals(tokenId.trim())){
				userMap=UserDB.getUserToen(tokenId);
				UserDB.userLangTrack(usertoken,I18n.getLanguageFromSession().toString(),"login","0");
				System.out.println(userMap.get("mgrid")+" -- "+udata.getUid());
				if(userMap.get("mgrid")!=null && userMap.get("mgrid").equals(udata.getUid()))
					return "loggedin";
			}
			return "success";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String useragent=request.getHeader("user-Agent");
		String userip=request.getHeader("x-forwarded-for");
		if(userip==null || "".equals(userip))userip=request.getRemoteAddr();
		String scheme=request.getScheme();
		if(!schemeToUse.equalsIgnoreCase(scheme)){
			return "needhttps";
		}
		
		
		if("".equals(tokenId.trim())){
		
		if("".equals(getUname())){

			message=resourceBundle.getString("la.invalid.error.lbl");
         return "input";
        }
		//usertoken=UserDB.getUserId(uname, "");
		usertoken=UserDB.authenticateUser(uname.trim(), password,useragent,userip);
		if(usertoken==null){

			message=resourceBundle.getString("la.invalid.error.lbl");
	   			return "input";
		}
		if("inactive".equals(usertoken)){

			message=resourceBundle.getString("la.check.email.box.activation.link");
	         return "input";
	    }
		}else{   
			userMap=UserDB.getUserToen(tokenId);
			System.out.println("the user map is::"+userMap);
			usertoken=userMap.get("mgrid");
			if(usertoken==null || "".equals(usertoken)){

				message=resourceBundle.getString("la.invalid.error.lbl");
				return "input";
			}
			UserDB.makeEnrtyInLoggedInUsers(usertoken,userip,useragent);
		}
		UserData user=new UserData();
		user.setBeeId(uname);
		user.setUid(usertoken);
		session.remove("SESSION_SUBMGR");
		session.put("SESSION_USER", user);
		UserDB.userLangTrack(usertoken,I18n.getLanguageFromSession().toString(),"login","0");
		return "loggedin";
	}
	
	public String getattendee(){
		FBAppId=FacebookConnectDB.getFBAppId("0");
		return "success";
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertoken() {
		return usertoken;
	}

	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSchemeToUse() {
		return schemeToUse;
	}

	public void setSchemeToUse(String schemeToUse) {
		this.schemeToUse = schemeToUse;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSsldomain() {
		return ssldomain;
	}

	public void setSsldomain(String ssldomain) {
		this.ssldomain = ssldomain;
	}
	public String getFBAppId() {
		return FBAppId;
	}
	public void setFBAppId(String appId) {
		FBAppId = appId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	
}
