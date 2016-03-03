package com.eventbee.general;

import com.eventbee.mail.DirectEmail;

import java.sql.*;


public class EventbeeMail  {

	public static EmailObj getEmailObj(){
		return new EmailObj();
	}



	public static void insertStrayEmail(EmailObj emailobj,String emailtype){
		insertStrayEmail(emailobj,emailtype,"P");
	}


	public static void insertStrayEmail(EmailObj emailobj,String emailtype,String status){
		SendMailStatus smstat=emailobj.getSendMailStatus();
    	if(smstat==null){
    		smstat=new SendMailStatus("0","sent mail status not set while sending email","0","0");
    	}
    	try{
    		
    		String query="insert into stray_email( unit_id,purpose,ref_id,mail_count,mail_to,mail_from,mail_subject, "
    			+" mail_replyto,text_message,html_message,sch_time,emailtype,mail_cc,mail_bcc,status) "
    			+" values (to_number(?,'99999999999999'),?,?, to_number(?,'99999999999999'),?,?,?,?, ?, ?, now(),?,?,?,?)";
    		DbUtil.executeUpdateQuery(query, new String[]{smstat.getUnitId(),smstat.getPurpose(),smstat.getRefId(),smstat.getMailCount(),
    				emailobj.getTo(),emailobj.getFrom(),emailobj.getSubject(),emailobj.getReplyTo(),emailobj.getTextMessage(),emailobj.getHtmlMessage(),
    				emailtype,emailobj.getCc(),emailobj.getBcc(),status});
    		
    		/*pstmt=con.prepareStatement(query);
    		pstmt.setString(1,smstat.getUnitId());
    		pstmt.setString(2,smstat.getPurpose());
    		pstmt.setString(3,smstat.getRefId());
    		pstmt.setString(4,smstat.getMailCount());
    		pstmt.setString(5,emailobj.getTo());
    		pstmt.setString(6,emailobj.getFrom());
    		pstmt.setString(7,emailobj.getSubject());
    		pstmt.setString(8,emailobj.getReplyTo());
    		pstmt.setString(9,emailobj.getTextMessage());
    		pstmt.setString(10,emailobj.getHtmlMessage());
    		pstmt.setString(11,emailtype);
    		pstmt.setString(12,emailobj.getCc());
    		pstmt.setString(13,emailobj.getBcc());
    		pstmt.setString(14,status);*/
    	}catch(Exception e){
    		System.out.println("excep from club db insert stray table:="+e.getMessage());
    	}
    	
	}//end of insert




	public static void processEmailStray(){
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		try{
			 String query="select unit_id,purpose,ref_id,mail_count,mail_to,mail_from,mail_subject, "
			 +" mail_replyto,text_message,html_message,sch_time,status,emailtype,mail_cc,mail_bcc from stray_email where status='P' ";
             statobj=dbm.executeSelectQuery(query, null); 
            //pstmt=con.prepareStatement(query);
		   // EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeEmail","in processStrayEmail--pstmt is-------->:"+pstmt,"",null);

           if(statobj.getStatus() && statobj.getCount()>0){
             for(int i=0;i<statobj.getCount();i++){
			        EmailObj emailobj=new EmailObj();
					String unitid=dbm.getValue(i, "unit_id","");
					String purpose=dbm.getValue(i, "purpose","");
					String ref_id=dbm.getValue(i, "ref_id","");
					String mail_count=dbm.getValue(i, "mail_count","");
					SendMailStatus smstat=new SendMailStatus(unitid,purpose,ref_id,mail_count);
					emailobj.setSendMailStatus(smstat);
					String mail_to=dbm.getValue(i, "mail_to","");
					emailobj.setTo(mail_to);
					String mail_from=dbm.getValue(i, "mail_from","");
					emailobj.setFrom(mail_from);
					String mail_subject=dbm.getValue(i, "mail_subject","");
					emailobj.setSubject(mail_subject);
					String mail_replyto=dbm.getValue(i, "mail_replyto","");
					emailobj.setReplyTo(mail_replyto);
					String text_message=dbm.getValue(i, "text_message","");
					emailobj.setTextMessage(text_message);
					String html_message=dbm.getValue(i, "html_message","");
					emailobj.setHtmlMessage(html_message);

					String mail_cc=dbm.getValue(i, "mail_cc","");
					emailobj.setCc(mail_cc);
					String mail_bcc=dbm.getValue(i, "mail_bcc","");
					emailobj.setBcc(mail_bcc);

					String emailtype=dbm.getValue(i, "emailtype","");

			        EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeEmail","in processStrayEmail--emailtype is-------->:"+emailtype,"",null);


                    EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeEmail","in processStrayEmail--before SendHtmlmail-------->:","",null);

					if(emailtype.equalsIgnoreCase("html")){
						//sendHtmlMailPlain(emailobj);
						DirectEmail.sendHtmlMailPlain(emailobj);
					}else{
						//sendTextMailPlain( emailobj);
						DirectEmail.sendTextMailPlain(emailobj);
					}

                    EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeEmail","in processStrayEmail--After SendHtmlmail-------->:","",null);

					updateStray( unitid, mail_to);
					EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"EventbeeEmail","in processStrayEmail--After Update Stray(); SendHtmlmail-------->:","",null);
                }
           }
			}catch(Exception e){
		System.out.println("excep from eventbeemail  insert stray table:="+e.getMessage());

		}
	}//get process stray
	public static void updateStray(String unitid,String email){
		try{
			String query="update stray_email set  status='S' where mail_to=? and unit_id=cast(? as integer)";
			DbUtil.executeUpdateQuery(query, new String[]{email,unitid});
			}catch(Exception e){
			System.out.println("excep from eventbeemail updateStray() insert stray table:="+e.getMessage());
		}
		
	}//update stray


	public static void sendHtmlMail(EmailObj emailobj){
		insertStrayEmail( emailobj,"html");

	}

	public static void sendTextMail(EmailObj emailobj){
		insertStrayEmail( emailobj,"text");
	}


	public static void sendHtmlMailCampaign(EmailObj emailobj){
		DirectEmail.sendHtmlMailCampaign(emailobj);
	}

/*** plain no db interactions***********/

	public static void sendHtmlMailPlain(EmailObj emailobj){
		System.out.println("in send");
		DirectEmail.sendHtmlMailPlain(emailobj);	
	}


	public static void sendTextMailPlain(EmailObj emailobj){
		DirectEmail.sendTextMailPlain(emailobj);
	}


/*************/

	public static void sendTextMailCampaign(EmailObj emailobj){
		DirectEmail.sendTextMailCampaign(emailobj);
	}
	

}
