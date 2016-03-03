package com.mycommunities.actions;

import java.util.ArrayList;
import java.util.HashMap;

import com.event.dbhelpers.TransactionDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.StatusObj;
import com.mycommunities.beans.AddCommunityMemberData;
import com.mycommunities.dbhelpers.AddCommunityMemberDB;
import com.mycommunities.dbhelpers.PassiveMemberDB;
import com.user.dbhelpers.UserDB;
import com.mycommunities.helpers.EventBeeValidations;

public class PassiveMemberCreateManualAction extends MyCommunitiesWrapper{

	private static final long serialVersionUID = -4351839924633520168L;
	private AddCommunityMemberData memberdata=new AddCommunityMemberData();
	private HashMap validateSignUp=new HashMap();
	private boolean errorsExists;
	private ArrayList<Entity> genderType=new ArrayList<Entity>();
	private ArrayList<String> membershipType=new ArrayList<String>();


	
	public ArrayList<Entity> getGenderType() {
		return genderType;
	}

	public void setGenderType(ArrayList<Entity> genderType) {
		this.genderType = genderType;
	}

	public ArrayList<String> getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(ArrayList<String> membershipType) {
		this.membershipType = membershipType;
	}

	public boolean isErrorsExists() {
		return errorsExists;
	}

	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}

	public AddCommunityMemberData getMemberdata() {
		return memberdata;
	}

	public void setMemberdata(AddCommunityMemberData memberdata) {
		this.memberdata = memberdata;
	}
	
	public String execute(){
System.out.println("in execute"+this.getClass().getName());
		return "success";
	}
        
	 public boolean validateProfile(){
		 validateSignUp = PassiveMemberDB.validateAttendeeSignUp(memberdata);
		 
	 if(validateSignUp.size()>0){
		if(validateSignUp.get("generalError")!=null){
					
				}
		if (validateSignUp.get("firstnameError") != null) {
			addFieldError("userData.getFirstName()", validateSignUp.get(
					"firstnameError").toString());
		}
		if (validateSignUp.get("lastnameError") != null) {
			addFieldError("userData.getLastName()", validateSignUp.get(
					"lastnameError").toString());
		}
		if (validateSignUp.get("genderError") != null) {
			addFieldError("userData.getGender()", validateSignUp.get(
					"genderError").toString());
		}
		if (validateSignUp.get("emailError") != null) {
			addFieldError("userData.getEmail()", validateSignUp.get(
					"emailError").toString());
		}
		if (validateSignUp.get("phoneError") != null) {
			addFieldError("userData.getPhone()", validateSignUp.get(
					"phoneError").toString());
		}
		if (validateSignUp.get("startdateError") != null){
			addFieldError(memberdata.getStartDate(), validateSignUp.get(
			"startdateError").toString());

		}
		if(validateSignUp.get("payduedateError") !=null){
			
			addFieldError(memberdata.getPayDueDate(), validateSignUp.get(
			"payduedateError").toString());

		}
	 }
	 
	     
		if (!getFieldErrors().isEmpty()) {

			errorsExists = true;
			return false;
		}
		return true;
	 }
	 

	
	public String  saveMemberProfile(){
		if(groupId==null) groupId="";
	    boolean status=validateProfile();
	    if(status){
	    	System.out.println("in save member profile"+groupId+memberdata.getFirstName());
			if(!"".equals(groupId))
	    	PassiveMemberDB.saveProfile(groupId,memberdata);
		//	System.out.println("before return");
			return "onSave";
	    }else{
	    	
	     //  System.out.println(" with errors in passive mem"+groupId);
	 	   genderType=	AddCommunityMemberDB.populateGender();
	 	   if(!"".equals(groupId))
	 	   membershipType=AddCommunityMemberDB.populateMembershiptypes(groupId);
	 	   
	 	   return "addPassiveMember";
	 		
	    }
		
	}
	public void prepare() throws Exception {
		try {
			
			super.prepare();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	public HashMap getValidateSignUp() {
		return validateSignUp;
	}

	public void setValidateSignUp(HashMap validateSignUp) {
		this.validateSignUp = validateSignUp;
	}

}
