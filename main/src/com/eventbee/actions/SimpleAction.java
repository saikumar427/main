package com.eventbee.actions;

import com.opensymphony.xwork2.ActionSupport;


public class SimpleAction extends ActionSupport {

	private static final long serialVersionUID = -2613425890762568273L;

	public String process()
	{
		return "success";		
	}
	
}
