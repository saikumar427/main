package com.mycommunities.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.json.JSONObject;
import com.event.dbhelpers.ReportsDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.formatting.EventbeeStrings;
import com.mycommunities.beans.ClubInfo;
import com.mycommunities.beans.ClubMemberShip;
import com.mycommunities.beans.CommunityReportData;
import com.mycommunities.dbhelpers.CommunityDB;
import com.mycommunities.helpers.CommunityManageHelper;
import com.mycommunities.helpers.JsonBuilderHelper;
import com.opensymphony.xwork2.ActionContext;

public class CommunityManageAction extends MyCommunitiesWrapper implements ParameterAware {

	private static final long serialVersionUID = -2792683512900912735L;
	private ArrayList<ClubMemberShip> memberships=new ArrayList<ClubMemberShip>();
	private ClubMemberShip membershipData;
	private String jsonData = "";
	private ArrayList<Entity> termTypes=new ArrayList<Entity>();
	private String membershipId="";
	private ClubInfo clubInfo=new ClubInfo();
	private CommunityReportData cReportData=new CommunityReportData();
	private ArrayList<LinkedHashMap<String,String>> signupReportMap=new ArrayList<LinkedHashMap<String,String>>();
	private ArrayList<LinkedHashMap<String,String>> renewalReportMap=new ArrayList<LinkedHashMap<String,String>>();
	private ArrayList<LinkedHashMap<String,String>> memberReportMap=new ArrayList<LinkedHashMap<String,String>>();
	private String attribSetid="";
	private ArrayList<Entity> customattributes=new ArrayList<Entity>();
	private ArrayList<Entity> membershipTypes=new ArrayList<Entity>();
	private ArrayList<Entity> statusTypes=new ArrayList<Entity>();
	private String msgType = "termcondsaved";
	public HashMap<String,String> summaryData=new HashMap<String,String>();	
	private boolean errorsExists;
	private boolean noneFound = false;
	private String exportType="";
	private HttpServletRequest req;
	private Map parameters;
	private ArrayList attFields=new ArrayList();
	
	
	//request.put("myId",myProp);
	
	public ArrayList getAttFields() {
		return attFields;
	}
	public void setAttFields(ArrayList attFields) {
		this.attFields = attFields;
	}
	
	public Map getParameters() {
		return parameters;
	}
	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
	public boolean isNoneFound() {
		return noneFound;
	}
	public void setNoneFound(boolean noneFound) {
		this.noneFound = noneFound;
	}
	public boolean isErrorsExists() {
		return errorsExists;
	}
	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}
	public ArrayList<Entity> getStatusTypes() {
		return statusTypes;
	}
	public void setStatusTypes(ArrayList<Entity> statusTypes) {
		this.statusTypes = statusTypes;
	}
	public void setMembershipTypes(ArrayList<Entity> membershipTypes) {
		this.membershipTypes = membershipTypes;
	}
	public ArrayList<Entity> getMembershipTypes() {
		return membershipTypes;
	}
	public HashMap<String, String> getSummaryData() {
		return summaryData;
	}
	public void setSummaryData(HashMap<String, String> summaryData) {
		this.summaryData = summaryData;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getAttribSetid() {
		return attribSetid;
	}
	public void setAttribSetid(String attribSetid) {
		this.attribSetid = attribSetid;
	}
	public ArrayList<Entity> getCustomattributes() {
		return customattributes;
	}
	public void setCustomattributes(ArrayList<Entity> customattributes) {
		this.customattributes = customattributes;
	}
	public ArrayList<LinkedHashMap<String, String>> getMemberReportMap() {
		return memberReportMap;
	}
	public void setMemberReportMap(
			ArrayList<LinkedHashMap<String, String>> memberReportMap) {
		this.memberReportMap = memberReportMap;
	}
	public ArrayList<LinkedHashMap<String, String>> getRenewalReportMap() {
		return renewalReportMap;
	}
	public void setRenewalReportMap(
			ArrayList<LinkedHashMap<String, String>> renewalReportMap) {
		this.renewalReportMap = renewalReportMap;
	}
	public ArrayList<LinkedHashMap<String, String>> getSignupReportMap() {
		return signupReportMap;
	}
	public void setSignupReportMap(
			ArrayList<LinkedHashMap<String, String>> signupReportMap) {
		this.signupReportMap = signupReportMap;
	}
	public CommunityReportData getCReportData() {
		return cReportData;
	}
	public void setCReportData(CommunityReportData reportData) {
		cReportData = reportData;
	}
	public ClubInfo getClubInfo() {
		return clubInfo;
	}
	public void setClubInfo(ClubInfo clubInfo) {
		this.clubInfo = clubInfo;
	}
	public String getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
	public ClubMemberShip getMembershipData() {
		return membershipData;
	}
	public void setMembershipData(ClubMemberShip membershipData) {
		this.membershipData = membershipData;
	}
	public ArrayList<Entity> getTermTypes() {
		return termTypes;
	}
	public void setTermTypes(ArrayList<Entity> termTypes) {
		this.termTypes = termTypes;
	}
	public ArrayList<ClubMemberShip> getMemberships() {
		return memberships;
	}
	public void setMemberships(ArrayList<ClubMemberShip> memberships) {
		this.memberships = memberships;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public void prepare() throws Exception {
		try {
			super.prepare();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public String execute(){
		if(userId==null) userId="";
		if(groupId==null) groupId="";
		if(!"".equals(userId) && !"".equals(groupId))
		summaryData=CommunityDB.getClubData(userId, groupId);
		return SUCCESS;
	}
	public String showMembershipTypes(){
		System.out.println("CommunityManage Action showMembershipTypes");
		System.out.println("CommunityManage Action groupId"+groupId+"userId"+userId);
		if(groupId==null) groupId="";
		if(!"".equals(groupId))
		memberships=CommunityDB.getMemberShipInfo(groupId,userId);
		JSONObject js=JsonBuilderHelper.getMembershipListJson(memberships);
		jsonData=js.toString();
		System.out.println("jsonData"+jsonData);
		return "displaymembershiplistjson";
	}
	public String createMembershipType(){
		termTypes=CommunityManageHelper.fillTermTypes();
		return "addmembershiptype";
	}
	public String editMembershipType(){
		termTypes=CommunityManageHelper.fillTermTypes();
		System.out.println("Action class"+"groupId"+groupId+"membershipId"+membershipId);
		if(groupId==null)groupId="";
		if(membershipId==null) membershipId="";
		if(!"".equals(groupId) && !"".equals(membershipId))
		membershipData=CommunityDB.getMembershipTypeData(groupId,membershipId);
		return "addmembershiptype";
	}
	public String saveMembershipType(){
		boolean valid=validateMembershipData();
		if(valid){
		if(groupId==null) groupId="";
		if(!"".equals(groupId))
		CommunityDB.saveMembershipType(groupId, membershipData);
		return "membershiptypesaved";
		}
		else{
		termTypes=CommunityManageHelper.fillTermTypes();
		return "addmembershiperrors";
		}
	}
	public boolean validateMembershipData(){
		if("".equals(membershipData.getMemberShipName().trim())){
			addFieldError("membershipData.memberShipName", "Membership Name is empty");

		}
		if("".equals(membershipData.getPrice())){
			addFieldError("membershipData.price", "Initial Fee is empty");
		}
		else{
			try{
				Double price=Double.parseDouble(membershipData.getPrice());

			}
			catch(NumberFormatException e){
				addFieldError("membershipData.price", "Initial Fee should be numeric");

			}
			catch(Exception e){
				addFieldError("membershipData.price", "Initial Fee should be numeric");

			}
		}
		if("".equals(membershipData.getTermPrice())){
			addFieldError("membershipData.termPrice", "Term Fee is empty");
		}
		else{
			try{
				Double price=Double.parseDouble(membershipData.getTermPrice());

			}
			catch(NumberFormatException e){
				addFieldError("membershipData.termPrice", "Term Fee should be numeric");

			}
			catch(Exception e){
				addFieldError("membershipData.termPrice", "Term Fee should be numeric");

			}
		}
		if(!getFieldErrors().isEmpty()){
			return false;
		}
		return true;
	}
	public String showTermCond(){
		clubInfo=CommunityDB.getTermsCond(userId, groupId);
		return "showtermcond";
	}
	public String saveTermsandCond(){
		CommunityDB.updateTermsCond(groupId, userId, clubInfo);
		return "termcondsaved";
	}
	public String showMembershipSignup(){
		populateReportsInfo();
		return "membershipsignup";
	}
	public void populateReportsInfo(){
		int year=0;
		java.util.Date d=new java.util.Date();
	    year=d.getYear()+1900;
	    cReportData.setStartMonth(EventbeeStrings.getMonthHtml("","cReportData.startMonth","",""));
	    cReportData.setStartDay(EventbeeStrings.getDayHtml("","cReportData.startDay","",""));
	    cReportData.setStartYear(EventbeeStrings.getYearHtml(year-5,6,0,"cReportData.startYear","",""));
	    cReportData.setEndMonth(EventbeeStrings.getMonthHtml("","cReportData.endMonth","",""));
	    cReportData.setEndDay(EventbeeStrings.getDayHtml("","cReportData.endDay","",""));
	    cReportData.setEndYear(EventbeeStrings.getYearHtml(year-5, 6,0,"cReportData.endYear","",""));
	}
	public void populateDatesInfo(){
		Calendar calendar = Calendar.getInstance();
		String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
		String year = Integer.toString(calendar.get(Calendar.YEAR));
		cReportData.setStDay(day);
		cReportData.setStYear(year);
		cReportData.setStMonth(month);
		cReportData.setEnDay(day);
		cReportData.setEnYear(year);
		cReportData.setEnMonth(month);
		
	}
	public void prepareDisplayData(){
		if(groupId==null) groupId="";
		if(!"".equals(groupId))
		membershipTypes=CommunityDB.getMemberships(groupId,"");
		statusTypes=CommunityDB.getStatusTypes("");
	}
	public String getSignupReportData(){
		if(groupId==null) groupId="";
		if(!"".equals(groupId))
		signupReportMap=CommunityDB.getMembershipSignupsReport(groupId, userId, cReportData);
		populateReportsInfo();
		return "membershipsignup";
	}
	public String showMembershipRenewal(){
		populateReportsInfo();
		return "membershiprenewal";
	}
	public String getRenewalReportData(){
		if(groupId==null) groupId="";
		if(!"".equals(groupId))
		renewalReportMap=CommunityDB.getRenewMembershipReport(groupId, userId, cReportData);
		populateReportsInfo();
		return "membershiprenewal";
	}
	public String showManageMembers(){
		populateDatesInfo();
		prepareDisplayData();
		System.out.println("showManageMembers");
		return "managemembers";
	}
	public String getMemberManageReport(){
		System.out.println("getMemberManageReport groupId: "+groupId);
		HashMap<String,HashMap<String,String>> temp = new HashMap<String, HashMap<String,String>>();
		if(groupId==null) groupId="";
		if(!"".equals(groupId)){
			attribSetid=ReportsDB.getAttribSetID(groupId,"CLUB_MEMBER_SIGNUP_PAGE");
			if(attribSetid==null) attribSetid="";
			if(!"".equals(attribSetid)){
				customattributes=CommunityDB.getAttributes(attribSetid);
				temp=CommunityDB.getResponses(attribSetid);
			}
			boolean status = validateMemberdata(cReportData);
			System.out.println("status is:"+status);
			if(status){
				memberReportMap=CommunityDB.getMemberReportData(groupId,temp,customattributes, cReportData);
				setNoneFound(true);
				if(memberReportMap != null && memberReportMap.size() > 0){
					LinkedHashMap<String, String> linkHash = memberReportMap.get(0);
					Set keys = linkHash.keySet();
					for (Iterator i = keys.iterator(); i.hasNext();) {
						String key = (String) i.next();
						if(!key.equalsIgnoreCase("usrId"))
							attFields.add(key);
					}
				}
			}
		
		}
		prepareDisplayData();
		return "managemembers";
	}
	public String deleteMembershipType(){
		if(groupId==null) groupId="";
		if(membershipId==null) membershipId="";
		if(!"".equals(groupId) && !"".equals(membershipId))
		CommunityDB.deleteMembershipType(groupId, membershipId);
		return "membershipdeleted";
	}
	
	public boolean validateMemberdata(CommunityReportData cReportData){
		
		int selectedvalue=Integer.parseInt(cReportData.getSelindex());
		if(selectedvalue == 2 && (cReportData.getName() != null && cReportData.getName().trim().equals("")))
			addFieldError(cReportData.getName(),"Please enter Name");
		
		if(selectedvalue == 3 && (cReportData.getEmail() != null && cReportData.getEmail().trim().equals("")))
			addFieldError(cReportData.getEmail(),"Please enter Email");
		
		if (!getFieldErrors().isEmpty()) {
			errorsExists = true;
			return false;
		}
		return true;
	}
	
}
