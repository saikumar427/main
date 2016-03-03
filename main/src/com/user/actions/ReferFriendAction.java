package com.user.actions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.event.helpers.I18n;
import com.event.i18n.dbmanager.ConfirmationEmailDAO;
import com.eventbee.filters.SMTPHostProps;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EventBeeValidations;
import com.eventbee.interfaces.MailerInterface;
import com.opensymphony.xwork2.ActionContext;
import com.user.beans.UserData;

public class ReferFriendAction {
	private String email = "";
	
		public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

		public String execute(){
			HashMap<String, String> hashMap = getEmailTemplate();
			sendEmail(hashMap);			
			email = email.replace("[", "").replace("]", "");			
			return "displaysuccess";
		}
		
		
		public void sendEmail(HashMap<String, String> hashMap){
			System.out.println("email in action before replace::"+email);
			email = email.replace("[", "").replace("]", "");
			System.out.println("email in action after replace::"+email);
			String[] emails = email.split(",");
			MailerInterface mi=null;		
			try{
				String smtphost=SMTPHostProps.get("DEFAULT","AmzonPhpMail");				
				smtphost = SMTPHostProps.get(hashMap.get("purpose"),"").trim();
				if("".equals(smtphost)) smtphost=SMTPHostProps.get("DEFAULT","AmzonPhpMail");
				System.out.println("smtphost: "+smtphost);
				try{
					mi = (MailerInterface)Class.forName("com.eventbee.mail."+smtphost).newInstance();
				}catch(Exception e){
					System.out.println("Exception in getMailerInterface inner try: "+e.getMessage());
					mi = (MailerInterface)Class.forName("com.eventbee.mail."+smtphost).newInstance();
				}
			}catch(Exception e){
				System.out.println("Exception in getMailerInterface: "+e.getMessage());
			}
			int status=0;
			try{
				 javax.mail.Session mailSession=mi.getMailSession();
			MimeMessage msg = new MimeMessage( mailSession );
			
			if(msg!=null){
				msg.setFrom( new InternetAddress( hashMap.get("fromemail") ) );				
				msg.setSubject( hashMap.get("subjectformat"),"UTF-8" );
				String replyto=hashMap.get("replytoemail");
				//if("".equals(replyto) || replyto==null)replyto=hashMap.get("fromemail");
				msg.setReplyTo(new Address[]{new InternetAddress( replyto ) });
				MimeBodyPart textpart=new MimeBodyPart();
				textpart.setText("");
				textpart.addHeaderLine("Content-Type: text/plain; charset=\"UTF-8\"");
				textpart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");
				MimeBodyPart htmlpart=new MimeBodyPart();
				htmlpart.setText(hashMap.get("htmlformat"),"UTF-8");
				htmlpart.addHeaderLine("Content-Type: text/html; charset=\"UTF-8\"");
				htmlpart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");
				Multipart mp2=new MimeMultipart("alternative");
				mp2.addBodyPart(textpart);
				mp2.addBodyPart(htmlpart);				
				msg.setContent(mp2);		
				if(!"".equals(emails)){
				 List<String> list = Arrays.asList(emails);
				    Set<String> set = new HashSet<String>(list);
				    for(String s : set){
				    	System.out.println(s);
				    	try{	
				    		if(EventBeeValidations.isValidFromEmail(s.trim())){
							InternetAddress[]inarr=InternetAddress.parse(s.trim(), false );
							System.out.println("before send method");
							msg.setRecipients(javax.mail.Message.RecipientType.TO, inarr);
							System.out.println("msg is:"+msg);
							Transport.send(msg);							
							System.out.println("after send method");
							status=1;	
				    		}
							}catch(Exception e){System.out.println("refere friend action sent mail......>"+e.getMessage());}
				    }
				    insertStrayEmail(hashMap,email,set);
				/*for(int i=0;i<emails.length;i++){
					System.out.println("emails:::"+emails[i].trim());
				try{	
				InternetAddress[]inarr=InternetAddress.parse(emails[i].trim(), false );
				System.out.println("before send method");
				msg.setRecipients(javax.mail.Message.RecipientType.TO, inarr);
				System.out.println("msg is:"+msg);
				Transport.send(msg);
				System.out.println("after send method");
				status=1;		
				}catch(Exception e){System.out.println("refere friend action sent mail......>"+e.getMessage());}
				}		*/									
					
			}
			}//end of msg null
			}catch(Exception ex){
				status=0;
				System.out.println("Exception from DirectEmail.java: sendHtmlMailPlain() message ="+ex.getMessage());
			}
			
			
			
		}
		
		
		
		public static void insertStrayEmail(HashMap<String,String> hashMap,String email,Set<String> set){
			try{			
			String emailscount = set.size()+"";
			String query="insert into stray_email( unit_id,purpose,ref_id,mail_count,mail_to,mail_from,mail_subject, "
    			+" mail_replyto,text_message,html_message,sch_time,emailtype,mail_cc,mail_bcc,status) "
    			+" values (to_number(?,'99999999999999'),?,?, to_number(?,'99999999999999'),?,?,?,?, ?, ?, now(),?,?,?,?)";
    		    DbUtil.executeUpdateQuery(query, new String[]{"13579","REFER_FRIEND_MAIL",hashMap.get("userid"),emailscount,
    				set.toString(),hashMap.get("fromemail"),hashMap.get("subjectformat"),hashMap.get("replytoemail"),hashMap.get("textformat"),hashMap.get("htmlformat"),
    				"html","","","S"});
			}catch(Exception e){System.out.println("exception in insertStrayEmail method in ReferFriendAction.java::"+e.getMessage());}
			
			}
		
		
		
		public HashMap<String, String> getEmailTemplate(){
			//DBManager db = new DBManager();
			DBManager dbm = new DBManager();
			HashMap<String, String> hmp = new HashMap<String, String>();
			try{
			String mgrName="";			
			/*String template = "SELECT fromemail,replytoemail,textformat,htmlformat,subjectformat,purpose FROM EMAIL_TEMPLATES WHERE unitid='13579' AND purpose='REFER_FRIEND_MAIL'";		
			db.executeSelectQuery(template, null);*/
			String lang=I18n.getLanguageFromSession();
			HashMap<String, String> purposeHm= new HashMap<String,String>();
			purposeHm.put("purpose", "REFER_FRIEND_MAIL");
			purposeHm.put("isCustomFeature", "N");
			HashMap<String, String> emailTemplate=new HashMap<String, String>();
			ConfirmationEmailDAO emailDao=new ConfirmationEmailDAO();
			emailTemplate=(HashMap<String, String>)emailDao.getData(purposeHm, lang, "").get("emailTemplate");
			Map session = ActionContext.getContext().getSession();
			UserData user=(UserData)session.get("SESSION_USER");
			System.out.println("getuser id::"+user.getUid());
			String query = "select first_name,last_name from user_profile where user_id=?";
			String beeid = DbUtil.getVal("select login_name from authentication where user_id=?",new String[]{user.getUid()});
			dbm.executeSelectQuery(query, new String[]{user.getUid()});
			//String email = dbm.getValue(0,"email","");
			String fname = dbm.getValue(0,"first_name","");
			String lname = dbm.getValue(0,"last_name","");
			mgrName=fname+" "+lname;
			System.out.println("manager name:::"+mgrName);
			/*if(email!=null && !"".equals(email)){
			if(email.indexOf("_inactive_eb")>-1){
				email=email.substring(0,email.indexOf("_inactive_eb"));	
			}
			}*/
			String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/";
			//System.out.println("email from session:::"+email);
			System.out.println("ReferFriendAction fromemail::"+emailTemplate.get("fromMail"));
			hmp.put("fromemail", emailTemplate.get("fromMail"));
			hmp.put("replytoemail", emailTemplate.get("replyToMail"));
			hmp.put("textformat", emailTemplate.get("textFormat"));
			hmp.put("htmlformat", emailTemplate.get("htmlFormat").replace("$mgrName",mgrName).replace("$acceptLink", serveraddress+"signup/"+beeid));
			hmp.put("subjectformat", emailTemplate.get("subjectFormat").replace("$mgrName",mgrName));
			hmp.put("purpose", "REFER_FRIEND_MAIL");
			hmp.put("userid", user.getUid());
			}catch(Exception e){System.out.println("Exception from ReferFriendAction.java--->"+e.getMessage());}
			
			return hmp;
		}
		
				
	
}
