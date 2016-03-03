package com.user.actions;

import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.StatusObj;
import com.eventbee.general.StringEncryptDecrypt;

public class UnsubscribeCampaignAction {

    private String campid="";
    private String emailid="";
	private String decodeemail="";
	private String temptext="";
	private String message="";
	private String msgKey="";
	private String tempid="";
    public String getTempid() {
		return tempid;
	}
	public void setTempid(String tempid) {
		this.tempid = tempid;
	}
	public String getTemptext() {
		return temptext;
	}
	public void setTemptext(String temptext) {
		this.temptext = temptext;
	}
	public String getDecodeemail() {
		return decodeemail;
	}
	public void setDecodeemail(String decodeemail) {
		this.decodeemail = decodeemail;
	}
	public String getCampid() {
		return campid;
	}
	public void setCampid(String campid) {
		this.campid = campid;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String execute(){
		return "";
	}
	
	public String getDecodeEmail(){
		String campaignid="",fromemail="";
		if(campid==null)campid="";
		if(emailid==null)emailid="";
		if("".equals(campid)){
			message="Unsubscribe is allowed through real emails";
			return "notunsubscribe";
		}	
		else{
			 if(!"".equals(campid)){
				try{
	        	campaignid=EncodeNum.decodeNum(campid);
				System.out.println("campaignid after decode is:"+campaignid);
				}catch(Exception e){System.out.println("Exception Occured in decode campainid in getDecodeEmail");}
			 if(!"".equals(campaignid))
				 fromemail=DbUtil.getVal("select camp_from from email_campaign where camp_id=CAST(? as integer)",new String[]{campaignid});
			 System.out.println("fromemail is:"+fromemail);    
			 if(fromemail==null)fromemail="";
			}
			if(!"".equals(fromemail))
				temptext="If you no longer wish to receive emails from "+fromemail+", please click on Unsubscribe button.";
			else
			    temptext="If you no longer wish to receive emails, please click on Unsubscribe button.";
			
			temptext=temptext.replaceAll("\n","<br/>");
			try{
			decodeemail=StringEncryptDecrypt.getDecryptedString(emailid);
			}catch(Exception e){
				System.out.println("Exception occured in getDecodeEmail");
			}
			return "decodedemailid";
		} 
	}
	
	public String doneUnsubscribe(){
		StatusObj statobj=null;
		String campaignid="";
		String query="update mail_list_members set status='unsubscribed' where member_id in(select member_id from member_profile"+
		             " where manager_id=(select manager_id from email_campaign where camp_id=CAST(? as integer)) and m_email=?)";
		if("".equals(campid)){
			message="Unsubscribe failed";
			return "unsubscribe";
		}
		if(!"".equals(campid)){
		campaignid=EncodeNum.decodeNum(campid);
		statobj=DbUtil.executeUpdateQuery(query,new String[]{campaignid,decodeemail});
		if(statobj.getStatus() && statobj.getCount()>0){
		message="Unsubscribed successfully";
		String mgrid=DbUtil.getVal("select manager_id from email_campaign where camp_id=CAST(? as integer)", new String[]{campaignid});
		String insertquery="insert into unsubscribe_manager_campaignemails(email,mgr_id) values(?,CAST(? as integer))";
		DbUtil.executeUpdateQuery(insertquery,new String[]{decodeemail,mgrid});
		}else
			message="Unsubscribe failed";
		}
		return "unsubscribe";
	}
	
	public String getMarketingDecodeEmail(){
		System.out.println("emailid in unsubscribeMarketingEmail:"+emailid);
		System.out.println("templateid in unsubscribeMarketingEmail:"+tempid);
		String fromemail="",decodetempid="";
		if(!"".equals(tempid)){
			try{
			decodetempid=EncodeNum.decodeNum(tempid);
			}catch(Exception e){System.out.println("Exception occured in decode tempid in  getMarketingDecodeEmail");}
			if(!"".equals(decodetempid))
				fromemail=DbUtil.getVal("select fromemail  from marketing_templates where templateid=?", new String[]{decodetempid});	
			if(fromemail==null)fromemail=""; 
		}
		if(!"".equals(emailid)){
		  try{
			decodeemail=StringEncryptDecrypt.getDecryptedString(emailid.trim());
		  }catch(Exception e){System.out.println("Exception occured in decode email in getMarketingDecodeEmail");}
		  }
		if(!"".equals(fromemail))
			temptext="If you no longer wish to receive emails from "+fromemail+", please click on Unsubscribe button.";
		else
			temptext="If you no longer wish to receive emails, please click on Unsubscribe button.";
		return "marketingdecodeemail";
	}
	
     public String unsubscribeMarketingEmail(){
		System.out.println("decodeemail in unsubscribeMarketingEmail:"+decodeemail);
		StatusObj statobj=null;
		if(!"".equals(decodeemail)){
		String chkemailstatus=DbUtil.getVal("select 'Y' from marketing_unsubscribe_emails where lower(email)=?",new String[]{decodeemail.toLowerCase()});
		String emailinsertqry="insert into marketing_unsubscribe_emails(email,date) values(?,to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'))";
		if(chkemailstatus==null)chkemailstatus="N";
		if(!"Y".equalsIgnoreCase(chkemailstatus)){
			String currentdate=DateUtil.getCurrDBFormatDate();
			statobj=DbUtil.executeUpdateQuery(emailinsertqry,new String[]{decodeemail.toLowerCase(),currentdate});
			if(statobj.getStatus() && statobj.getCount()>0)
				message="Your email is now removed from this mailing list. Thank You.";
			else
				message="Unsubscribe failed. Please try again later.";
			}else
				message="Your email is now removed from this mailing list. Thank You.";
		}else
			message="Unsubscribe failed. Please try again later.";
		return "marketingunsubscribe";
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
}
