package com.mycommunities.actions;

import java.util.*;

import com.event.beans.CustomAttribute;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.mycommunities.beans.AddCommunityMemberData;
import com.mycommunities.dbhelpers.AddCommunityMemberDB;
import com.mycommunities.dbhelpers.CommunityDB;
import com.user.dbhelpers.UserDB;

public class AddCommunityMemberAction extends MyCommunitiesWrapper {

	// groupId,userId,actionTitle are known to this page
	private static final long serialVersionUID = -4351839924633520168L;
	private ArrayList<HashMap<String, String>> mshipidlist = new ArrayList<HashMap<String, String>>();
	private String membertype = "";
	private AddCommunityMemberData memberdata = new AddCommunityMemberData();
	private ArrayList<Entity> genderType = new ArrayList<Entity>();
	private ArrayList<String> membershipType = new ArrayList<String>();
	private ArrayList<Entity> statusTypes=new ArrayList<Entity>();
	private boolean errorsExists;
	private HashMap validateSignUp = new HashMap();
	private boolean addmember = false;
	private List<CustomAttribute> custAttList = new ArrayList<CustomAttribute>();
	private ArrayList chklist=new ArrayList();
	private String usrId;
	private String pwdPopup="";
	private String password="";
	private String rePassword="";
	
	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	public String getPwdPopup() {
		return pwdPopup;
	}
	public void setPwdPopup(String pwdPopup) {
		this.pwdPopup = pwdPopup;
	}
	public ArrayList<Entity> getStatusTypes() {
		return statusTypes;
	}
	public void setStatusTypes(ArrayList<Entity> statusTypes) {
		this.statusTypes = statusTypes;
	}
	
	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public ArrayList getChklist() {
		return chklist;
	}

	public void setChklist(ArrayList chklist) {
		this.chklist = chklist;
	}

	public List<CustomAttribute> getCustAttList() {
		return custAttList;
	}

	public void setCustAttList(List<CustomAttribute> custAttList) {
		this.custAttList = custAttList;
	}

	public boolean isAddmember() {
		return addmember;
	}

	public void setAddmember(boolean addmember) {
		this.addmember = addmember;
	}

	public HashMap getValidateSignUp() {
		return validateSignUp;
	}

	public void setValidateSignUp(HashMap validateSignUp) {
		this.validateSignUp = validateSignUp;
	}

	public boolean isErrorsExists() {
		return errorsExists;
	}

	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}

	public ArrayList<String> getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(ArrayList<String> membershipType) {
		this.membershipType = membershipType;
	}

	public ArrayList<Entity> getGenderType() {
		return genderType;
	}

	public void setGenderType(ArrayList<Entity> genderType) {
		this.genderType = genderType;
	}

	public AddCommunityMemberData getMemberdata() {
		return memberdata;
	}

	public void setMemberdata(AddCommunityMemberData memberdata) {
		this.memberdata = memberdata;
	}

	public String getMembertype() {
		return membertype;
	}

	public void setMembertype(String membertype) {
		this.membertype = membertype;
	}

	public ArrayList<HashMap<String, String>> getMshipidlist() {
		return mshipidlist;
	}

	public void setMshipidlist(ArrayList<HashMap<String, String>> mshipidlist) {
		this.mshipidlist = mshipidlist;
	}

	//------------------------------------------***------------------------------
	
	public String execute() {
		if(groupId==null)groupId="";
		if(!"".equals(groupId)){
		mshipidlist = AddCommunityMemberDB.getclubmembermaster(groupId);
		membertype = AddCommunityMemberDB.getMemberType(groupId);
		}
		return "success";
	}

	public String settings() {
		if("Yes".equals(pwdPopup)){
			return "changepwd";
		}
		if(groupId==null)groupId="";
		if(!"".equals(groupId)){
		AddCommunityMemberDB.insertAddMemberSettings(groupId, memberdata);
		genderType = AddCommunityMemberDB.populateGender();
		membershipType = AddCommunityMemberDB.populateMembershiptypes(groupId);
		statusTypes=CommunityDB.getStatusTypes("");
		custAttList = AddCommunityMemberDB.getCustomAttributeList(groupId,usrId);
		}
		if(usrId != null && !usrId.equals(""))
			memberdata = AddCommunityMemberDB.getMemberData(usrId);
		//populateCustomAttribs();
		return "addmember";
	}
	public String addPassiveMember() {
		if(groupId==null)groupId="";
		genderType = AddCommunityMemberDB.populateGender();
		if(!"".equals(groupId))
		membershipType = AddCommunityMemberDB.populateMembershiptypes(groupId);

		return "addpassivemember";

	}

	public boolean validateProfile() {

			validateSignUp = AddCommunityMemberDB.validateAttendeeSignUp(usrId,groupId,memberdata);

		if (validateSignUp.size() > 0) {
			if (validateSignUp.get("generalError") != null) {

			}
			if (validateSignUp.get("loginnameExist") != null) {
				addFieldError(memberdata.getLoginId(), validateSignUp.get(
						"loginnameExist").toString());
			}
			if (validateSignUp.get("loginLength") != null) {
				addFieldError(memberdata.getLoginId(), validateSignUp.get(
						"loginLength").toString());
			}
			if (validateSignUp.get("invalid_id") != null) {
				addFieldError(memberdata.getLoginId(), validateSignUp.get(
						"invalid_id").toString());
			}
			if (validateSignUp.get("pwdLength") != null) {
				addFieldError(memberdata.getPassword(), validateSignUp.get(
						"pwdLength").toString());
			}
			if (validateSignUp.get("firstnameError") != null) {
				addFieldError(memberdata.getFirstName(), validateSignUp.get(
						"firstnameError").toString());
			}
			if (validateSignUp.get("lastnameError") != null) {
				addFieldError(memberdata.getLastName(), validateSignUp.get(
						"lastnameError").toString());
			}
			if (validateSignUp.get("genderError") != null) {
				addFieldError(memberdata.getGender(), validateSignUp.get(
						"genderError").toString());
			}
			if (validateSignUp.get("membershipTypeError") != null) {
				addFieldError(memberdata.getMembershiptype(), validateSignUp.get(
						"membershipTypeError").toString());
			}
			if (validateSignUp.get("emailError") != null) {
				addFieldError(memberdata.getEmail(), validateSignUp.get(
						"emailError").toString());
			}
			if (validateSignUp.get("phoneError") != null) {
				addFieldError(memberdata.getPhone(), validateSignUp.get(
						"phoneError").toString());
			}
			if (validateSignUp.get("startdateError") != null) {
				addFieldError(memberdata.getStartDate(), validateSignUp.get(
						"startdateError").toString());
			}
			if (validateSignUp.get("payduedateError") != null) {

				addFieldError(memberdata.getPayDueDate(), validateSignUp.get(
						"payduedateError").toString());
			}
			if (validateSignUp.get("discountcodeinvalid") != null) {
				addFieldError(memberdata.getDiscountcode(), validateSignUp.get(
						"discountcodeinvalid").toString());
			}
			
		}
		
		if(custAttList.size()>0){
			for(int i=0;i<custAttList.size();i++){
				CustomAttribute ca=custAttList.get(i);
				if(ca.getIsRequired().equals("Required")){
					String response=ca.getPropValue();
					if(ca.getQtype().equals("checkbox")){
						if(ca.getChkList()==null || ca.getChkList().size()<1)
						{
							addFieldError(ca.getName(), ca.getName()+" is required");
						}
					}
					else if(response==null || "".equals(response.trim())){
						addFieldError(ca.getName(), ca.getName()+" is required");
					}
				}
			}
				
		}

		if (!getFieldErrors().isEmpty()) {
			errorsExists = true;
			return false;
		}
		return true;
	}
	
	
	public boolean validatePassword(){

		validateSignUp = AddCommunityMemberDB.validateAttendeePassword(usrId,password,rePassword);
		if (validateSignUp.size() > 0) {
			if (validateSignUp.get("pwdLength") != null) {
				addFieldError(password, validateSignUp.get("pwdLength").toString());
			}
			
			/*if (validateSignUp.get("repwdLength") != null) {
				addFieldError(rePassword, validateSignUp.get("repwdLength").toString());
			}*/
		}else if(!password.equals(rePassword)){
			addFieldError(password, "Passwords should match ");
		}
		
		
		if (!getFieldErrors().isEmpty()) {
			errorsExists = true;
			return false;
		}
		return true;
	
	}

	public String saveProfile() {
		if(groupId==null) groupId="";
		boolean status = validateProfile();
		if (status) {
            if(!"".equals(groupId))
			AddCommunityMemberDB.insertProfile(usrId, groupId, memberdata,custAttList);
			if(usrId != null && !usrId.equals(""))
				return "managemembers";
			
			return "onsavemember";
			
		} else {
			genderType = AddCommunityMemberDB.populateGender();
			if(!"".equals(groupId))
			membershipType = AddCommunityMemberDB.populateMembershiptypes(groupId);
			statusTypes=CommunityDB.getStatusTypes("");
			return "addmember";

		}

	}
	
	public String savePassword(){
		if(validatePassword()){
			if(usrId != null && !usrId.equals(""))
				AddCommunityMemberDB.updatePassword(usrId,password);
			
			return "ajaxmsg";
		}
		return "pwderror";
	}

	public void prepare() throws Exception {
		try {

			super.prepare();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

}
