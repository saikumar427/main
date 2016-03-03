package com.user.actions;

import java.util.HashMap;
import java.util.Vector;


import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EventBeeValidations;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.user.beans.UserData;
import com.user.dbhelpers.UserDB;
import java.util.ResourceBundle;

import com.event.helpers.I18n;

public class LoginProblemAction extends ActionSupport implements Preparable,ValidationAware{
	private static final long serialVersionUID = -4351839924633520168L;
	private UserData userData=new UserData();
	private boolean errorsExists;
	private Vector vect_data=new Vector();
	private HashMap logindatahm=null;
	private HashMap emailcontent=new HashMap();
	private String message="";
	private String msgKey="";
	private String role="";
	
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HashMap getEmailcontent() {
		return emailcontent;
	}
	public void setEmailcontent(HashMap emailcontent) {
		this.emailcontent = emailcontent;
	}
	public HashMap getLogindatahm() {
		return logindatahm;
	}
	public void setLogindatahm(HashMap logindatahm) {
		this.logindatahm = logindatahm;
	}
	public Vector getVect_data() {
		return vect_data;
	}
	public void setVect_data(Vector vect_data) {
		this.vect_data = vect_data;
	}
	public boolean isErrorsExists() {
		return errorsExists;
	}
	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}
	public UserData getUserData() {
		return userData;
	}
	public void setUserData(UserData userData) {
		this.userData = userData;
	}
	public String execute(){		
		return "success";
	}
	public boolean validateData(){
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		//System.out.println("vecdata::::"+vect_data);
		
		if(userData.getEmail().trim().toLowerCase().length()==0){
			addFieldError("userData.getEmail()", resourceBundle.getString("global.email.is.empty.errmsg"));
		}else{
			boolean value=EventBeeValidations.isValidFromEmail(userData.getEmail().toLowerCase().trim());
			if(!value){
				addFieldError("userData.getEmail()", resourceBundle.getString("lp.invalid,email.format"));			
			}
		}
		if(vect_data.size()==0){			
			addFieldError("userData.getEmail()", resourceBundle.getString("lp.no.account.exists"));			
		}
		if(!getFieldErrors().isEmpty()){
			errorsExists = true;
			return false;
		}
	return true;
	}
	public String loginHelp(){	
		System.out.println("role::"+role);
		
		if("submgr".equals(role))
		vect_data=UserDB.isValidMember(userData.getEmail(),"13579","submgr");
		else
		vect_data=UserDB.isValidMember(userData.getEmail(),"13579","loginname");
		
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<vect_data.size();i++){
		String user_id=EbeeConstantsF.get("login.label",I18n.getString("global.beeid.ph"));
		logindatahm=(HashMap)(vect_data.elementAt(i));
		if(logindatahm!=null){	
			        emailcontent.put("email",userData.getEmail());
			        String password=(String)logindatahm.get("password");
			        if(password.indexOf("<")>-1 || password.indexOf(">")>-1){
			        	password=password.replace("<","&lt;");
			        	password=password.replace(">","&gt;");
			        }
			        sb.append(user_id+": "+(String)logindatahm.get("login_name")+"<br/>");
					sb.append(I18n.getString("global.pwd.ph")+": "+password+"<br/><br/>");
					}else{
						msgKey="login help1";
					}
					msgKey="login help2";
		}    
		emailcontent.put("dbloginnames",sb.toString());
		
		if(vect_data.size()>0){
			if("submgr".equals(role))
				UserDB.SendEmail(emailcontent,-498,"13579");
			else
				UserDB.SendEmail(emailcontent,vect_data.size(),"13579");
			}
		boolean status=validateData();
		if(status){			
			return "sent";
		}else{
			System.out.println("in Login Help error case");
		return "inputerror";
		}
		
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
