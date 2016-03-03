package com.eventbee.general;

import java.util.Map;

public class SendEmail{
	
	private Map hMap=null;
	private String UNITID="13579";
	private String purpose="";
	private String PLAINcontent="";
	private String HTMLcontent="";
	private String subject="";
	private String toEmail="";
	private String fromEmail="";
	private String replyEmail="";
	 
	
	private EmailTemplate emailtemplate=null;
	private EmailObj obj=null;
	
	public void setUnitID(String unitid){this.UNITID=unitid;}
	public String getUnitID(){ return this.UNITID;}
	
	public void setPurpose(String pur){this.purpose=pur;}
	public String getPurpose(){ return this.purpose;}
	
	public void setToEmail(String to){this.toEmail=to;}
	public String getToEmail(){ return this.toEmail;}
	
	public void setMessageMap(Map mesMap){this.hMap=mesMap;}
	public Map getMessageMap(){ return this.hMap;}
	
	public void setSubject(String sub){this.subject=sub;}
	public String getSubject(String sub){return this.subject;}
	
	public void setFromEmail(String from){this.fromEmail=from;}
	public String getFromEmail(){ return this.fromEmail;}
	
	public void setReplyEmail(String reply){this.replyEmail=reply;}
	public String getReplyEmail(){ return this.replyEmail;}
	
	public SendEmail(String UNITID,String purpose,String toEmail,Map hMap){
		this.UNITID=UNITID;
		this.purpose=purpose;
		this.hMap=hMap;
		this.toEmail=toEmail;
		initializeTemplate();
		initializeEmailObj();
		fireEmail();
	}
	public EmailTemplate initializeTemplate(){
		emailtemplate=new EmailTemplate(UNITID,purpose);
		PLAINcontent=TemplateConverter.getMessage(hMap,emailtemplate.getTextFormat());
		HTMLcontent=TemplateConverter.getMessage(hMap,emailtemplate.getHtmlFormat());
		subject=TemplateConverter.getMessage(hMap,emailtemplate.getSubjectFormat());
		fromEmail=emailtemplate.getFromMail();
		replyEmail=emailtemplate.getReplyToMail();
		return emailtemplate;
	}
	public EmailObj initializeEmailObj(){
		obj=EventbeeMail.getEmailObj();
		obj.setTo(toEmail);
		obj.setFrom(fromEmail);
		obj.setReplyTo(replyEmail);
		obj.setSubject(subject);
		obj.setTextMessage(PLAINcontent);
		obj.setHtmlMessage(HTMLcontent);
		return obj;
	}
	public boolean fireEmail(){
		EventbeeMail.sendHtmlMail(obj);
		return true;
	}
	
}
