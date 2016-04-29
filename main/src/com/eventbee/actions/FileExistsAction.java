package com.eventbee.actions;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.event.helpers.I18n;
import com.eventbee.general.EbeeConstantsF;
import com.opensymphony.xwork2.ActionSupport;

public class FileExistsAction extends ActionSupport {
	String pattern="";
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String process(){
		boolean exists=false;
		HttpServletRequest request = ServletActionContext.getRequest();
		String jspFileNi18n = request.getRequestURL().substring(request.getRequestURL().indexOf("main/")+5);	
		String[] jspNLang=jspFileNi18n.split("/");
		String jspFile="";
		String i18n="en-us";
		//i18n=I18n.getLanguageFromSession();
		if(jspNLang.length>1){
			i18n=jspNLang[1]; 
			i18n=I18n.getMappingCode(i18n);	
		}
		jspFile=jspNLang[0];
		String filepath = EbeeConstantsF.get("help.file.path","D:\\JBoss-EB\\jboss-4.2.2.GA\\server\\default\\deploy\\main.war\\help\\");
		
		try{
			File file=new File(filepath+i18n+"/"+jspFile+".jsp");
			exists = file.exists();
		}catch(Exception e){
			System.out.println("Exception in FileExistsAction ERROR: "+e.getMessage());
			exists=false;
		}
		//exists =true;		
		setPattern(jspFile);
		if ((exists && ("eventbee-for-business".equals(jspFile) || "pricing".equalsIgnoreCase(jspFile) || "how-it-works".equalsIgnoreCase(jspFile) || "eventbee-ticketing-kindle-promotion".equalsIgnoreCase(jspFile) || "faq".equalsIgnoreCase(jspFile) || "eventbee-customer-case-study-bishop-kelly-high-school".equalsIgnoreCase(jspFile) || "eventbee-manager-app".equalsIgnoreCase(jspFile) || "eventbee-customer-case-study-demolay-international".equalsIgnoreCase(jspFile) || "refer-a-friend".equalsIgnoreCase(jspFile) || "venue-reserved-seating".equalsIgnoreCase(jspFile) || "sell-tickets-on-facebook".equalsIgnoreCase(jspFile) || "social-media-event-marketing".equalsIgnoreCase(jspFile) || "sell-event-tickets-with-paypal-stripe-braintree-authorize-net".equalsIgnoreCase(jspFile) || "free-event-ticketing-software".equalsIgnoreCase(jspFile) || "free-online-event-registration-software".equalsIgnoreCase(jspFile) || "attendee-event-management-at-the-door".equalsIgnoreCase(jspFile) || "custom-online-registration-form".equalsIgnoreCase(jspFile) ))){ 
			return "pricing";
	    }
		else if(exists){
			return "success";
		}
		else{
			System.out.println("*** filepath in FileExistsAction: "+filepath+jspFile+".jsp");
			return "error";
		}
				
	}

}
