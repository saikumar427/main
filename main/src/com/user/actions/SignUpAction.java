package com.user.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.EventBeeValidations;
import com.eventbee.general.formatting.WriteSelectHTML;
import com.myevents.actions.AddEventAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.user.beans.UserData;
import com.user.dbhelpers.UserDB;

public class SignUpAction extends ActionSupport implements Preparable,ValidationAware,SessionAware{
	private static final long serialVersionUID = -4351839924633520168L;
	private UserData userData=new UserData();
	private ArrayList<Entity> genderType=new ArrayList<Entity>();
	private ArrayList<Entity> referType=new ArrayList<Entity>();	
	private HashMap validateSignUp=new HashMap();
	private boolean errorsExists;
	private String usertoken="";
	private String message="";
	private String domain="";
	private String categories="";
	private String pc="";
	private String ref="";
	private String captcha="";
	private Map session;
	private String token="";
	private String serverAddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/"; 
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public ArrayList<Entity> getReferType() {
		return referType;
	}

	public void setReferType(ArrayList<Entity> referType) {
		this.referType = referType;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
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

	public boolean isErrorsExists() {
		return errorsExists;
	}

	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}

	public HashMap getValidateSignUp() {
		return validateSignUp;
	}

	public void setValidateSignUp(HashMap validateSignUp) {
		this.validateSignUp = validateSignUp;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public ArrayList<Entity> getGenderType() {
		return genderType;
	}

	public void setGenderType(ArrayList<Entity> genderType) {
		this.genderType = genderType;
	}
	public void populateInfo(){
		try{
		List categoryCode=DbUtil.getValues("select code from categories where purpose=? order by displayname",new String[]{"Community"});
		List categoryName=DbUtil.getValues("select displayname from categories where purpose=? order by displayname",new String[]{"Community"});
		if(categoryCode==null || categoryName==null){
	    	categoryCode=new ArrayList();
	        categoryName=new ArrayList();
	    }  
		categoryCode.add("Other");
		categoryName.add("Other");
		String categoryCodestr[]=(String[])categoryCode.toArray(new String[0]);
      	String categoryNamestr[]=(String[])categoryName.toArray(new String[0]);
		genderType=UserDB.populateGender();
		categories=WriteSelectHTML.getSelectHtml(categoryCodestr,categoryNamestr,"userData.category",userData.getCategory(),"-- Select Category --","");
		userData.setCategories(categories);
		referType=UserDB.populatereferTypeList();
		}catch(Exception e){
			System.out.println("Exception ocuured in populateInfo in SignupAction"+e.getMessage());
		}
		
	}
	public String execute(){
		populateInfo();
		userData.setPromotionCode(pc);
		if(!"".equals(ref)) {
			userData.setReferBy(ref);
			userData.setReferType("Friend");
		}
		return "success";
	}
	public boolean validateData(){
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		session = ActionContext.getContext().getSession();
		String s="";
		if(session.get("captcha_signupForm")!=null)
			s=(String)session.get("captcha_signupForm");
		System.out.println("**** captcha from session: "+s.toLowerCase());
		validateSignUp=UserDB.validateAttendeeSignUp(userData);
		if(validateSignUp.size()>0){
			if(validateSignUp.get("generalError")!=null){
				//addFieldError("userData.getBeeId()", validateSignUp.get("generalError").toString());
			}
			if(validateSignUp.get("loginnameExist")!=null){
				
				/*String loginname=userData.getBeeId();
				boolean status=UserDB.CheckLoginDetails(loginname,userData);
				
				if(loginname==null)loginname="";
				loginname=loginname.trim();
				
				if(status==false){*/
					System.out.println("we are in validate data status==false");
					addFieldError("userData.getBeeId()", validateSignUp.get("loginnameExist").toString());
				//}
		     }
			if(validateSignUp.get("loginLength")!=null){
				addFieldError("userData.getBeeId()", validateSignUp.get("loginLength").toString());
			}/*else{
				if(userData.getBeeId().trim().length()>20)
					addFieldError("userData.beeId", " BeeId maximum size exceeds 20");
			}*/
			
			if(validateSignUp.get("invalid_id")!=null){
			addFieldError("userData.getBeeId()", validateSignUp.get("invalid_id").toString());
			}
			if(validateSignUp.get("pwdLength")!=null){
				addFieldError("userData.getPassword()", validateSignUp.get("pwdLength").toString());
			}/*else{
				if(userData.getPassword().trim().length()>20)		
					addFieldError("userData.password", " Password maximum size exceeds 20");
			}*/
			/*if(validateSignUp.get("pwdMatch")!=null){
				addFieldError("userData.getPassword()", validateSignUp.get("pwdMatch").toString());
			}*/
			/*if(validateSignUp.get("firstnameError")!=null){
			addFieldError("userData.getFirstName()", validateSignUp.get("firstnameError").toString());
			}
			if(validateSignUp.get("lastnameError")!=null){
			addFieldError("userData.getLastName()", validateSignUp.get("lastnameError").toString());
			}			
		    if(validateSignUp.get("genderError")!=null){
			addFieldError("userData.getGender()", validateSignUp.get("genderError").toString());
		    }
		    /*if(validateSignUp.get("emailError")!=null){
				addFieldError("userData.getEmail()", validateSignUp.get("emailError").toString());  }
		   if(validateSignUp.get("phoneError")!=null){
				addFieldError("userData.getPhone()", validateSignUp.get("phoneError").toString());
		   }*/
		}
	    if ("".equals(userData.getEmail().trim()))
			addFieldError("userData.email", resourceBundle.getString("global.email.is.empty.errmsg"));
		else if(!EventBeeValidations.isValidFromEmail(userData.getEmail().trim()))
			addFieldError("userData.email", resourceBundle.getString("global.invalid.format.for.email.errmsg"));
		s=s.toLowerCase();
		/*captcha=captcha.toLowerCase().trim();
		if("".equals(captcha) || !captcha.equals(s)){
		   addFieldError("Captcha", "Please enter correct verification code");
		}*/
		if(getFieldErrors().isEmpty())
		validateSignUp();
		if(!getFieldErrors().isEmpty()){
			errorsExists = true;
			//userData.setEmail(userData.getEmail().trim());
			return false;
		}
	return true;
	}
	
	
	
	public void validateSignUp(){		
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		String email = userData.getEmail().trim();
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip=AddEventAction.getIpFromRequest(request);
		String emailcount = DbUtil.getVal("select count(*) from user_profile where email=? and to_char(created_at,'YYYY-MM-DD') = to_char(now(),'YYYY-MM-DD')",new String[]{email});
		String ipcount = DbUtil.getVal("select count(*) from user_profile where ip=? and to_char(created_at,'YYYY-MM-DD') = to_char(now(),'YYYY-MM-DD')",new String[]{ip});
		String query="";
		try{
			System.out.println("email count::"+Integer.parseInt(emailcount)+"ip count:::"+Integer.parseInt(ipcount));
		if(Integer.parseInt(emailcount)>=3){
			addFieldError("Email",resourceBundle.getString("su.too.many.signup.email.msg"));
			query="insert into suspected_user_activities(userid,ip,action,created_at) values(?,?,'signup_action_email',now())";
			DbUtil.executeUpdateQuery(query,new String[]{null,ip});
			}else if(Integer.parseInt(ipcount)>=3){	
			addFieldError("Ip",resourceBundle.getString("su.too.many.signup.ip.msg"));
			query="insert into suspected_user_activities(userid,ip,action,created_at) values(?,?,'signup_action_ip',now())";
			DbUtil.executeUpdateQuery(query,new String[]{null,ip});			
		}}catch(Exception e){System.out.println("exception in validatesignup::"+e.getMessage());}			
		
	}
	
	
	
	public String signUpProcess(){
		boolean status=validateData();
		genderType=UserDB.populateGender();
		if(!"".equals(ref)) userData.setReferBy(ref);
		if(status){
			UserDB.processSignUp(userData);	
			 //usertoken=UserDB.getUserId(userData.getBeeId(), "");
			//UserDB.removeDuplicates(userData.getBeeId());
			/*HttpServletRequest request=ServletActionContext.getRequest();
			String useragent=request.getHeader("user-Agent");
			String userip=request.getHeader("x-forwarded-for");
			if(userip==null || "".equals(userip))userip=request.getRemoteAddr();
			 usertoken=UserDB.authenticateUser(userData.getBeeId(), userData.getPassword(),useragent,userip);
				UserData user=new UserData();
				user.setUid(usertoken);
				Map session = ActionContext.getContext().getSession();
				session.put("SESSION_USER", user);*/
			return "actionvationlink";
		}else{	
			populateInfo();
		return "success";
		}
	}
	
	
	public  String activateAccount(){
		token=token==null?"":token.trim();
		String status=DbUtil.getVal("select 'yes' from authentication where usertoken=? and accounttype='Pending' ", new String[]{token});
		if("yes".equals(status))
			return "activate";
		else
		return "invalid";
				
	} 
	
	public String activateProcess(){
		try{
		HttpServletRequest request=ServletActionContext.getRequest();
		String useragent=request.getHeader("user-Agent");
		String userip=request.getHeader("x-forwarded-for");
		String decodedString=EncodeNum.decodeUserId(token).trim();
		boolean flag=UserDB.validateUser(decodedString,token.trim());
		System.out.println("the flag is::"+flag);
		if(flag)
		UserDB.activateManager(userData,decodedString,useragent,userip);
		else{
			errorsExists=true;
			addFieldError("","Enter valid token");
			return "activate";	
		}
		errorsExists=false;
		usertoken=decodedString;
		UserData user=new UserData();
		user.setUid(decodedString);
		Map session = ActionContext.getContext().getSession();
		session.put("SESSION_USER", user);
	return "loggedin";
		}catch(Exception e){
			System.out.println("Exception Occured in activateProcess method:"+e.getMessage());
			return "error";
		}
	}
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public boolean isValidFromEmail(String email){
		Pattern p = Pattern.compile("^[a-zA-Z0-9!#$%&'*+/=?^_`():{|}<>~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^:_`(){|}<>~-]+)*@(?:[a-zA-Z0-9!#$%&'*+/=?:^_`(){|}<>~-](?:[_a-zA-Z0-9-'!#$%&'*+/=?^:_`(){|}<>~-]*[_a-zA-Z0-9-'!#$%&'*+/=?:^_`(){|}<>~-])?\\.)+[_a-zA-Z0-9-'!#$%&'*+/=?:^_`(){|}<>~-](?:[_a-zA-Z0-9-'!#$%&'*+/=?^:_`(){|}<>~-]*[_a-zA-Z0-9-'!#$%&'*+/=?:^_`(){|}<>~-])?$");
	    Matcher m = p.matcher(email);
	    boolean matchFound = m.matches();
	    if (matchFound)
	      return true;
	    else
	      return false;
	}
	

}
