package com.eventbee.general;


public class  EmailObj{
	//String mailto, String message,String from,String subject,String text,String html,String replyto
	
	
	
	private String to = null;
	
	private String cc = null;
	private String bcc = null;


	private String from = null;
	private String subject = null;
	private String textMessage = "";
	private String htmlMessage = "";
	private String replyTo = null;
	private SendMailStatus smstat=null;
	private String campId="0";
	private String memId="0";
	   
	public void setTo(String to){
		this.to=to;
	}
	public String getTo(){
		return to;
	}



public void setCc(String cc){
		this.cc=cc;
	}
	public String getCc(){
		return cc;
	}

public void setBcc(String bcc){
		this.bcc=bcc;
	}
	public String getBcc(){
		return bcc;
	}



	public void setReplyTo(String replyTo){
		this.replyTo=replyTo;
	}
	public String getReplyTo(){
		return replyTo;
	}

	public void setFrom(String from){
		this.from=from;
	}
	public String getFrom(){
		return from;
	}

	public void setSubject(String subject){
		this.subject=subject;
	}
	public String getSubject(){
		return subject;
	}
	
	
	public void setTextMessage(String textMessage){
		this.textMessage=textMessage;
	}
	public String getTextMessage(){
		return textMessage;
	}


	public void setHtmlMessage(String htmlMessage){
		this.htmlMessage=htmlMessage;
	}
	public String getHtmlMessage(){
		return htmlMessage;
	}
	public SendMailStatus getSendMailStatus(){
		return smstat;
	}

	public void setSendMailStatus(SendMailStatus smstat){
		this.smstat=smstat;
	}
	public String getCampId(){
		return campId;
	}
	public void setCampId(String campId){
		this.campId=campId;
	}
	public String getMemId(){
		return memId;
	}

	public void setMemId(String memId){
		this.memId=memId;
	}



}