package com.eventmanage.actions.ticketing;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import com.event.beans.ConfirmationPageSettingsData;
import com.event.beans.RegEmailSettingsData;
import com.event.beans.ticketing.VolumeTicketData;
import com.event.dbhelpers.ConfirmationPageSettingsDB;
import com.event.dbhelpers.ConfirmationPreviewDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.TicketsDB;
import com.event.dbhelpers.VolumeTicketsDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailTemplate;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class VolumeTicketDetailsAction extends ActionSupport implements Preparable,
		ValidationAware {
	//private static final long serialVersionUID = -4857839233183838429L;
	private String eid = "";
	private VolumeTicketData volTicketData = new VolumeTicketData();
	private String currency = "";
	private String ticketId = "";
	private ArrayList<Entity> hours = new ArrayList<Entity>();
	private ArrayList<Entity> minutes = new ArrayList<Entity>();
	private ConfirmationPageSettingsData confirmationPageSettingsData = new ConfirmationPageSettingsData();
	private String purpose="";
	private String content="";
	private EmailTemplate emailtemplate=null;
	private RegEmailSettingsData regEmailSettingsData=new RegEmailSettingsData();
	private ArrayList<Entity> widgetCodes = new ArrayList<Entity>();
	private String widgetCode="";
	private String customWidgetCode="";
	private String script="";
	private String showStatus="";
	private String htmlcss="";
	private String previewContent="";
	private String preview="";
	private String msgKey="";
	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getHtmlcss() {
		return htmlcss;
	}

	public void setHtmlcss(String htmlcss) {
		this.htmlcss = htmlcss;
	}

	public String getPreviewContent() {
		return previewContent;
	}

	public void setPreviewContent(String previewContent) {
		this.previewContent = previewContent;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getWidgetCode() {
		return widgetCode;
	}

	public void setWidgetCode(String widgetCode) {
		this.widgetCode = widgetCode;
	}

	public RegEmailSettingsData getRegEmailSettingsData() {
		return regEmailSettingsData;
	}

	public void setRegEmailSettingsData(RegEmailSettingsData regEmailSettingsData) {
		this.regEmailSettingsData = regEmailSettingsData;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public void populateTime() {
		hours = TicketsDB.getHours();
		minutes = TicketsDB.getMinutes();
	}
	
	public String VolumeTicketingEnable(){
		return "vbenable";
	}
	
	public String addVolumeTicket(){
		System.out.println("addVolumeTicket eid: "+eid);
		populateTime();
		VolumeTicketsDB.defaultVolumeTicketStartDate(volTicketData);
		String userTimeZone = EventDB.getEventTimeZone(eid);
		//TicketsDB.getEndDate(volTicketData, eid, userTimeZone);
		String servicefee=VolumeTicketsDB.getVolumeTicketServiceFee(eid);
		volTicketData.setProcessingFee(servicefee);
		currency = EventDB.getCurrencySymbol(eid);
		if (currency == null)
			currency = "$";
		volTicketData.setCurrency(currency);
		return "volumeinput";
	}
	
	public String editVolumeTicket() {
		System.out.println("TicketDetailsAction editVolumeTicket ticketId: "+ticketId);
		populateTime();
		String userTimeZone = EventDB.getEventTimeZone(eid);
		volTicketData = VolumeTicketsDB.getVolumeTicketData(eid, ticketId, userTimeZone);
		currency = EventDB.getCurrencySymbol(eid);
		if (currency == null)
			currency = "$";
		volTicketData.setCurrency(currency);
		return "volumeinput";
	}
	
	
	public boolean validateInputData() {
		
		if ("".equals(volTicketData.getTicketName().trim())) {
			addFieldError("volTicketData.ticketName", "Ticket Name is empty");

		}
		if ("".equals(volTicketData.getTicketPrice().trim())) {
			addFieldError("volTicketData.ticketPrice", "Ticket Price is empty");
		} else {

			try {
				Double price = Double.parseDouble(volTicketData.getTicketPrice());
				if (price < 0) {
					addFieldError("volTicketData.ticketPrice",
							"Invalid Ticket Price");
				}
			} catch (NumberFormatException e) {
				addFieldError("volTicketData.ticketPrice",
						"Ticket Price should be numeric");

			} catch (Exception e) {
				addFieldError("volTicketData.ticketPrice",
						"Ticket Price should be numeric");

			}
		}
		
		if("processFeeAttendee".equals(volTicketData.getProcessFeePaidBy())){
			if ("".equals(volTicketData.getProcessingFee().trim())) {
				addFieldError("volTicketData.processingFee", "Service Fee Collect from Attendee is empty");
			} else {
	
				try {
					Double processingfee = Double.parseDouble(volTicketData.getProcessingFee());
					if (processingfee < 0 ) {
						addFieldError("volTicketData.processingFee",
								"Invalid Service Fee Collect from Attendee");
					}
				} catch (NumberFormatException e) {
					addFieldError("volTicketData.processingFee",
							"Service Fee Collect from Attendee should be numeric");
	
				} catch (Exception e) {
					addFieldError("volTicketData.processingFee",
							"Service Fee Collect from Attendee should be numeric");
	
				}
			}
		}
		
		if(volTicketData.getDiscountType().equals("A")){
			if ("".equals(volTicketData.getAmountDiscount().trim())) {
				addFieldError("volTicketData.amountDiscount", "Volume Discount amount is empty");
			} else {
	
				try {
					
					Double amtdiscount = Double.parseDouble(volTicketData.getAmountDiscount());
					if (amtdiscount < 0) {
						addFieldError("volTicketData.amountDiscount",
								"Invalid Volume Discount amount");
					}else if(!getFieldErrors().containsKey("volTicketData.ticketPrice")){
						Double price = Double.parseDouble(volTicketData.getTicketPrice());
						if (amtdiscount > price) {
							addFieldError("volTicketData.amountDiscount",
									"Volume Discount amount should be less than Ticket Price");
						}
					}
					
				} catch (NumberFormatException e) {
					addFieldError("volTicketData.amountDiscount",
							"Volume Discount amount should be numeric");
	
				} catch (Exception e) {
					addFieldError("volTicketData.amountDiscount",
							"Volume Discount amount should be numeric");
	
				}
			}
		}else{
			
			if ("".equals(volTicketData.getPercentageDiscount().trim())) {
				addFieldError("volTicketData.percentageDiscount", "Volume Discount percentage is empty");
			} else {
	
				try {
					Double perdiscount = Double.parseDouble(volTicketData.getPercentageDiscount());
					if (perdiscount < 0 || perdiscount > 100) {
						addFieldError("volTicketData.percentageDiscount",
								"Invalid Volume Discount percentage");
					}
				} catch (NumberFormatException e) {
					addFieldError("volTicketData.percentageDiscount",
							"Volume Discount percentage should be numeric");
	
				} catch (Exception e) {
					addFieldError("volTicketData.percentageDiscount",
							"Volume Discount percentage should be numeric");
	
				}
			}
			
		}
		
		
		if ("".equals(volTicketData.getMinQty().trim())) {
			addFieldError("volTicketData.minQty", "Minimum Quantity is empty");
		} else {
			try {
				int mqty = Integer.parseInt(volTicketData.getMinQty().trim());
				if (mqty <= 0) {
					addFieldError("volTicketData.ticketPrice",
							"Invalid Minimum Quantity");
				}
			} catch (Exception e) {
				addFieldError("volTicketData.minQty",
						"Minimum Quantity should be numeric");
			}
		}
		if ("".equals(volTicketData.getMaxQty().trim())) {
			addFieldError("volTicketData.maxQty", "Maximum Quantity is empty");
		} else {
			try {
				int mqty = Integer.parseInt(volTicketData.getMaxQty().trim());
				
				if (mqty <= 0) {
					addFieldError("volTicketData.maxQty",
							"Invalid Maximum Quantity");
				}else if(!"".equals(volTicketData.getTriggerQty().trim()) && Integer.parseInt(volTicketData.getTriggerQty().trim()) < mqty){
					addFieldError("volTicketData.maxQty",
							"Maximum Quantity should be less than Trigger Quantity");
				}
			} catch (Exception e) {
				addFieldError("volTicketData.maxQty",
						"Maximum Quantity should be numeric");
			}
		}
		if ("".equals(volTicketData.getTriggerQty().trim())) {
			addFieldError("volTicketData.triggerQty", "Trigger Quantity is empty");
		} else {
			try {
				int tqty = Integer.parseInt(volTicketData.getTriggerQty().trim());
				if (tqty <= 0) {
					addFieldError("volTicketData.triggerQty",
							"Invalid Trigger Quantity");
				}
			} catch (Exception e) {
				addFieldError("volTicketData.triggerQty",
						"Trigger Quantity should be numeric");
			}
		}
		
		if ("".equals(volTicketData.getStdateTimeBeanObj().getMonthPart().trim())) {
			addFieldError("volTicketData.stdateTimeBeanObj.monthPart", "Ticket Sale Starts month is empty");
		} 
		
		if ("".equals(volTicketData.getStdateTimeBeanObj().getDdPart().trim())) {
			addFieldError("volTicketData.stdateTimeBeanObj.ddPart", "Ticket Sale Starts day is empty");
		} 
		
		if ("".equals(volTicketData.getStdateTimeBeanObj().getYyPart().trim())) {
			addFieldError("volTicketData.stdateTimeBeanObj.yyPart", "Ticket Sale Starts year is empty");
		} 
		
		if ("".equals(volTicketData.getActiveDuration().trim())) {
			addFieldError("volTicketData.activeDuration", "Ticket Sale Duration is empty");
		} else {
			try {
				int actdur = Integer.parseInt(volTicketData.getActiveDuration().trim());
				if (actdur <= 0) {
					addFieldError("volTicketData.activeDuration",
							"Invalid Ticket Sale Duration");
				}
			} catch (Exception e) {
				addFieldError("volTicketData.activeDuration",
						"Ticket Sale Duration should be numeric");
			}
		}
		
		//validateGivenDates();
		if(volTicketData.getPostTriggerType().equals("2")){
			if ("".equals(volTicketData.getUpperLimit().trim())) {
				addFieldError("volTicketData.continueUptoTickets", "Trigger continue selling additional tickets is empty");
			} else {
				try {
					int mqty = Integer.parseInt(volTicketData.getUpperLimit().trim());
					if (mqty <= 0) {
						addFieldError("volTicketData.continueUptoTickets",
								"Invalid Trigger continue selling additional tickets");
					}
				} catch (Exception e) {
					addFieldError("volTicketData.continueUptoTickets",
							"Trigger continue selling additional tickets should be numeric");
				}
			}
		}
		if(volTicketData.getPostTriggerType().equals("3")){
			if ("".equals(volTicketData.getCycles().trim())) {
				addFieldError("volTicketData.cycles", "How many times you want to start new cycle");
			} else {
				try {
					int cycles = Integer.parseInt(volTicketData.getCycles().trim());
					if (cycles <= 0) {
						addFieldError("volTicketData.cycles",
								"Invalid Trigger start new cycle");
					}
				} catch (Exception e) {
					addFieldError("volTicketData.cycles",
							"Trigger start new cycle should be numeric");
				}
			}
		}
		/*if(volTicketData.getTriggerFailAction().equals("2")){
		
			if ("".equals(volTicketData.getTriggerFailDiscount().trim())) {
				addFieldError("volTicketData.triggerFailDiscount", "Trigger Fail Discount is empty");
			} else {
				try {
					Double faildiscount = Double.parseDouble(volTicketData.getTriggerFailDiscount());
					if (faildiscount < 0 || faildiscount > 100) {
						addFieldError("volTicketData.triggerFailDiscount",
								"Invalid Trigger Fail Discount ");
					}
				} catch (Exception e) {
					addFieldError("volTicketData.triggerFailDiscount",
							"Trigger Fail Discount should be numeric");
				}
			}
		}*/
		try {
			int minqty = Integer.parseInt(volTicketData.getMinQty().trim());
			int maxty = Integer.parseInt(volTicketData.getMaxQty().trim());
			if (minqty > maxty && minqty > 0 && maxty > 0) {
				addFieldError("volTicketData.error",
						"Minimum Quantity is greater than Maximum Quantity");
			}
			
			if((minqty >= 1) && (maxty-minqty > 100)){
				addFieldError("volTicketData.error3",
				"Max number of tickets allowed for purchase per transaction is 100");
			}
		} catch (Exception e) {
			
		}
		
		if (!getFieldErrors().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public String saveVolumeTicket(){
		
		boolean status = validateInputData();
        if (status) {
        	/*double price = Double.parseDouble(volTicketData.getTicketPrice());
        	double triggerdiscval=0.0;
        	double faildiscval=0.0;
        	double triggervolprice=0.0;
        	double failddiscprice=0.0;
        	if("P".equals(volTicketData.getDiscountType())){
        		triggerdiscval=  (price * Double.parseDouble(volTicketData.getPercentageDiscount()))/100;
				triggervolprice = price - triggerdiscval;
        	}else{
        		triggervolprice = price - Double.parseDouble(volTicketData.getAmountDiscount());
        	}
        	
        	if("2".equals(volTicketData.getTriggerFailAction())){
        		faildiscval=  (price * Double.parseDouble(volTicketData.getTriggerFailDiscount()))/100;
        		failddiscprice = price - faildiscval;
        		
        		if(triggervolprice > failddiscprice){
	        		addFieldError("volTicketData.error",
					"Volume discount price must be less than Sell At discount price");
	        		return "inputerror";
        		}
        	}*/
        	
			String userTimeZone = EventDB.getEventTimeZone(eid);
			VolumeTicketsDB.setVolumeTicketEndDate(volTicketData);
			VolumeTicketsDB.saveVolumeTicketData(volTicketData, eid, userTimeZone);
            return "success";
		} else {
			return "inputerror";
		}
	}
	
	public String widgetCode(){
		System.out.println(" in widgetCode: "+eid);
		//widgetCodes=VolumeTicketsDB.getWidgetCodes(ticketId);
		widgetCode=VolumeTicketsDB.getWidgetCode(ticketId);
		String  serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		script="<script type='text/javascript' language='javascript' src='"+serveraddress+"/volume/volume_widget/volumescript.jsp?wcode="+widgetCode+"'></script>";
		return "widget";
	}
	
	/*public String generateWidgeCode(){
		if(widgetCode.equals("addnew") && !customWidgetCode.equals(""))
			setWidgetCode(customWidgetCode.trim());
		VolumeTicketsDB.generateWidgetCode(widgetCode.trim(), ticketId);
		
		String  serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		script="<script type='text/javascript' language='javascript' src='"+serveraddress+"/volume/widget/volumescript.jsp?wcode="+widgetCode+"'></script>";
		widgetCodes=VolumeTicketsDB.getWidgetCodes(ticketId);
		return "widget";
	}*/
	
	public String deleteVolumeTicket(){
		VolumeTicketsDB.deleteVolumeTicket(eid, ticketId);
		return "success";
	}
	
	public String confirmCancelVolumeTicket(){
		try{
			String transactionsexist = VolumeTicketsDB.confirmCancelVolumeTicket(eid,ticketId);
			if("yes".equals(transactionsexist))
				msgKey = "exist";
			else msgKey = "not";
			return "ajaxmsg";
		}catch(Exception ex){
			System.out.println("Exception: "+ex);
		}
		return "error";
	}
	
	public String cancelVolumeTicket(){
		VolumeTicketsDB.cancelVolumeTicket(eid, ticketId);
		return "success";
	}
	
	/*public String simulator(){
		return "simulate";
	}*/
	
	public String simulating(){
		VolumeTicketsDB.simulate(showStatus, ticketId);
		return "success";
	}
	public String confirmationPage(){
		System.out.println("in confirmationPage: "+eid+" purpose: "+purpose);
		content=ConfirmationPageSettingsDB.getContent(eid,purpose);
		confirmationPageSettingsData.setContent(content);
		return "confirmationpage";
	}
	
	public String saveConfirmationPage(){
		System.out.println("in saveConfirmationPage is: "+eid+" purpose: "+purpose);
		content=confirmationPageSettingsData.getContent();
		VolumeTicketsDB.saveConfirmationPageContent(eid,purpose,content);
		
		return "success";
	}
	
	public String resetConfirmationPage(){
		System.out.println("in resetConfirmationPage: "+eid+" purpose: "+purpose);
		VolumeTicketsDB.resetConfirmationPageContent(eid,purpose);
		return "success";
	}
	
	public String previewConfirmationPage(){
		System.out.println("in previewConfirmationPage: "+eid+" purpose: "+purpose);
		content=confirmationPageSettingsData.getContent();
		htmlcss = ConfirmationPreviewDB.getHTMLCSS(eid);
		previewContent=ConfirmationPreviewDB.fillConfirmation(eid,content,"","page",purpose);
		return "preview";
	}
	
	public String confirmationEmail(){
		System.out.println("in confirmationEmail eid: "+eid+" purpose: "+purpose);
		
		String isformatexists=DbUtil.getVal("select 'yes' from email_templates where purpose=? and groupid=?", new String []{purpose,eid});
		if("yes".equals(isformatexists))
			emailtemplate=new EmailTemplate("13579",purpose,eid);
		else
			emailtemplate=new EmailTemplate("500",purpose);
		
		regEmailSettingsData.setTempalteContent(emailtemplate.getHtmlFormat());
		regEmailSettingsData.setSubject(emailtemplate.getSubjectFormat());
		//String sendtoattendee=EventDB.getConfigVal(eid, "event.sendmailtoattendee", "yes");
		//String sendtomgr=EventDB.getConfigVal(eid, "event.sendmailtomgr", "yes");		
		//regEmailSettingsData.setSendToAttendee(sendtoattendee);
		//regEmailSettingsData.setSendToManager(sendtomgr);
		//regEmailSettingsData.setCc(EventDB.getConfigVal(eid, "registration.email.cc.list", ""));
		System.out.println("in confirmationEmail getSubjectFormat: "+emailtemplate.getSubjectFormat());
		return "confirmationemail";
	}
	
	public String saveConfirmationEmail(){
		System.out.println("in saveConfirmationEmail: "+eid+" purpose: "+purpose);
		VolumeTicketsDB.saveConfirmationEmailContent(eid, regEmailSettingsData, purpose);
		return "success";
	}
	
	public String resetConfirmationEmail(){
		System.out.println("in resetConfirmationEmail: "+eid+" purpose: "+purpose);
		VolumeTicketsDB.resetConfirmationEmailContent(eid,purpose);
		return "success";
	}
	
	public String previewConfirmationEmail(){
		System.out.println("in previewConfirmationEmail: "+eid+" purpose: "+purpose);
		content=regEmailSettingsData.getTempalteContent();
		htmlcss = ConfirmationPreviewDB.getHTMLCSS(eid);
		previewContent=ConfirmationPreviewDB.fillConfirmation(eid,content,"","email",purpose);
		return "preview";
		
	}
	
	
	
	public void validateGivenDates(){
	int styear=Integer.parseInt(volTicketData.getStdateTimeBeanObj().getYyPart());
	int stmonth=Integer.parseInt(volTicketData.getStdateTimeBeanObj().getMonthPart())-1;
	int sthourOfDay=Integer.parseInt(volTicketData.getStdateTimeBeanObj().getHhPart());
	String stampm=volTicketData.getStdateTimeBeanObj().getAmpm();
	int stdate=Integer.parseInt(volTicketData.getStdateTimeBeanObj().getDdPart());
	if(stampm.equals("PM") && sthourOfDay!=12){
		sthourOfDay=sthourOfDay+12;
	}
	int stminute=Integer.parseInt(volTicketData.getStdateTimeBeanObj().getMmPart());
	
	Date startdate = new GregorianCalendar(styear, stmonth, stdate, sthourOfDay, stminute).getTime();
	System.out.println("startdate: "+startdate);
	
	int ndyear=Integer.parseInt(volTicketData.getEnddateTimeBeanObj().getYyPart());
	int ndmonth=Integer.parseInt(volTicketData.getEnddateTimeBeanObj().getMonthPart())-1;
	int ndhourOfDay=Integer.parseInt(volTicketData.getEnddateTimeBeanObj().getHhPart());
	String ndampm=volTicketData.getEnddateTimeBeanObj().getAmpm();
	int nddate=Integer.parseInt(volTicketData.getEnddateTimeBeanObj().getDdPart());
	if(ndampm.equals("PM") && ndhourOfDay!=12){
		ndhourOfDay=ndhourOfDay+12;
	}
	int ndminute=Integer.parseInt(volTicketData.getEnddateTimeBeanObj().getMmPart());
	
	Date enddate = new GregorianCalendar(ndyear, ndmonth, nddate, ndhourOfDay, ndminute).getTime();
	System.out.println("enddate: "+enddate);
	
	if(enddate.before(startdate)){
		addFieldError("volTicketData.date",
		"Enddate should not be less than Startdate");
	}else{
		long diff = (enddate.getTime() - startdate.getTime())/(1000 * 60);
		if(diff>4320)
			addFieldError("volTicketData.date",
			"The difference between Startdate and Enddate should not be greater than 3 days");
	}
}



	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public ArrayList<Entity> getHours() {
		return hours;
	}

	public void setHours(ArrayList<Entity> hours) {
		this.hours = hours;
	}

	public ArrayList<Entity> getMinutes() {
		return minutes;
	}

	public void setMinutes(ArrayList<Entity> minutes) {
		this.minutes = minutes;
	}

	public ConfirmationPageSettingsData getConfirmationPageSettingsData() {
		return confirmationPageSettingsData;
	}

	public void setConfirmationPageSettingsData(
			ConfirmationPageSettingsData confirmationPageSettingsData) {
		this.confirmationPageSettingsData = confirmationPageSettingsData;
	}

	public EmailTemplate getEmailtemplate() {
		return emailtemplate;
	}

	public void setEmailtemplate(EmailTemplate emailtemplate) {
		this.emailtemplate = emailtemplate;
	}

	public ArrayList<Entity> getWidgetCodes() {
		return widgetCodes;
	}

	public void setWidgetCodes(ArrayList<Entity> widgetCodes) {
		this.widgetCodes = widgetCodes;
	}

	public String getCustomWidgetCode() {
		return customWidgetCode;
	}

	public void setCustomWidgetCode(String customWidgetCode) {
		this.customWidgetCode = customWidgetCode;
	}

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}

	public VolumeTicketData getVolTicketData() {
		return volTicketData;
	}

	public void setVolTicketData(VolumeTicketData volTicketData) {
		this.volTicketData = volTicketData;
	}

}
