package com.eventbee.actions;

import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.StatusObj;
import com.eventbee.general.StringEncryptDecrypt;
import com.opensymphony.xwork2.ActionSupport;

public class SubscribeAction extends ActionSupport{
        /**
	 * 
	 */
	private static final long serialVersionUID = 478016628088197699L;
		private String token="";
        private String tid="";
        private String cid="";
        private String pid="";
        private String temptext="";
        private String decodetoken="";
        private String message="";
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getTemptext() {
			return temptext;
		}
		public void setTemptext(String temptext) {
			this.temptext = temptext;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getTid() {
			return tid;
		}
		public void setTid(String tid) {
			this.tid = tid;
		}
		public String getCid() {
			return cid;
		}
		public void setCid(String cid) {
			this.cid = cid;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		
		public String execute(){
			if("em".equalsIgnoreCase(pid)){
				String campaignid="",fromemail="";
				if(cid==null)cid="";
				if(token==null)token="";
				if("".equals(cid)){
					message="Unsubscribe is allowed through real emails";
					return "notunsubscribe";
				}	
				else{
					 if(!"".equals(cid)){
						try{
			        	campaignid=EncodeNum.decodeNum(cid);
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
					decodetoken=StringEncryptDecrypt.getDecryptedString(token);
					}catch(Exception e){
						System.out.println("Exception occured in getDecodeEmail");
					}
					return "decodedemailid";
				} 
			}else{
				System.out.println("emailid in unsubscribeMarketingEmail:"+token);
				System.out.println("templateid in unsubscribeMarketingEmail:"+tid);
				String fromemail="",decodetempid="";
				if(!"".equals(tid)){
					try{
					decodetempid=EncodeNum.decodeNum(tid);
					}catch(Exception e){System.out.println("Exception occured in decode tempid in  getMarketingDecodeEmail");}
					if(!"".equals(decodetempid))
						fromemail=DbUtil.getVal("select fromemail  from marketing_templates where templateid=?", new String[]{decodetempid});	
					if(fromemail==null)fromemail=""; 
				}
				if(!"".equals(token)){
				  try{
					  if(token.trim().indexOf("@")>-1)
					      decodetoken=token.trim();
					  else
					      decodetoken=StringEncryptDecrypt.getDecryptedString(token.trim());
				  }catch(Exception e){System.out.println("Exception occured in decode email in getMarketingDecodeEmail");}
				  }
				if(!"".equals(fromemail))
					temptext="If you no longer wish to receive emails from "+fromemail+", please click on Unsubscribe button.";
				else
					temptext="If you no longer wish to receive emails, please click on Unsubscribe button.";
				
				System.out.println("call the marketingdecodeemail");
				return "marketingdecodeemail";
			}
		
		}
		
		
		public String doneUnsubscribe(){
			StatusObj statobj=null;
			if("em".equals(pid)){
				String campaignid="";
				String query="update mail_list_members set status='unsubscribed' where member_id in(select member_id from member_profile"+
				             " where manager_id=(select manager_id from email_campaign where camp_id=CAST(? as integer)) and m_email=?)";
				if("".equals(cid)){
					message="Unsubscribe failed";
					return "unsubscribe";
				}
				if(!"".equals(cid)){
				campaignid=EncodeNum.decodeNum(cid);
				statobj=DbUtil.executeUpdateQuery(query,new String[]{campaignid,decodetoken});
				if(statobj.getStatus() && statobj.getCount()>0){
				message="Unsubscribed successfully";
				String mgrid=DbUtil.getVal("select manager_id from email_campaign where camp_id=CAST(? as integer)", new String[]{campaignid});
				String insertquery="insert into unsubscribe_manager_campaignemails(email,mgr_id) values(?,CAST(? as integer))";
				DbUtil.executeUpdateQuery(insertquery,new String[]{decodetoken,mgrid});
				}else
					message="Unsubscribe failed";
				}
				return "unsubscribe";	
			}else{
				System.out.println("decodeemail in unsubscribeMarketingEmail:"+decodetoken);
				if(!"".equals(decodetoken)){
				if(token.trim().indexOf("@")>-1)
					decodetoken=token.trim();
				else
				    decodetoken=StringEncryptDecrypt.getDecryptedString(token.trim());	
				String chkemailstatus=DbUtil.getVal("select 'Y' from marketing_unsubscribe_emails where lower(email)=?",new String[]{decodetoken.toLowerCase()});
				String emailinsertqry="insert into marketing_unsubscribe_emails(email,date) values(?,to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.S'))";
				if(chkemailstatus==null)chkemailstatus="N";
				if(!"Y".equalsIgnoreCase(chkemailstatus)){
					String currentdate=DateUtil.getCurrDBFormatDate();
					statobj=DbUtil.executeUpdateQuery(emailinsertqry,new String[]{decodetoken.toLowerCase(),currentdate});
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
			
		}
		public String getDecodetoken() {
			return decodetoken;
		}
		public void setDecodetoken(String decodetoken) {
			this.decodetoken = decodetoken;
		}
}
