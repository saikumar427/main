package com.eventbee.interfaces;

import javax.mail.Session;

import com.eventbee.general.EmailObj;

public interface MailerInterface {
	public Session getMailSession();
	public int processHtmlMail(EmailObj emailobj);
	public void processTextMail(EmailObj emailobj);
}
