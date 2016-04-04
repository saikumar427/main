package com.eventmanage.actions.ticketing;

import com.event.beans.ticketing.GroupData;
import com.event.beans.ticketing.TicketData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.EventHelperDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.TicketsDB;
import com.event.dbhelpers.TicketsDBAdvanced;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.formatting.CurrencyFormat;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;


public class TicketDetailsAction extends ActionSupport implements Preparable,
		ValidationAware {

	private static final long serialVersionUID = -4857839233183838429L;
	private String eid = "";
	private String msgKey = "";
	private String ticketId = "";
	private TicketData ticketData = new TicketData();
	private String ticketType = "";
	private String ticketAtt = "ATTENDEE";
    private String groupId = "";
	@SuppressWarnings("unchecked")
	private ArrayList allgroups = new ArrayList();
	private String t1 = "";
	private String p1 = "";
	private String t2 = "";
	private String p2 = "";
	private String currency = "";
	private ArrayList<Entity> hours = new ArrayList<Entity>();
	private ArrayList<Entity> minutes = new ArrayList<Entity>();
	private ArrayList<Entity> uaQuestions = new ArrayList<Entity>();
	private ArrayList<Entity> aQuestions = new ArrayList<Entity>();
	private ArrayList<Entity> ticketsList=new ArrayList<Entity>();
	@SuppressWarnings("unchecked")
	private ArrayList<Entity> hubMap;
	private String purpose="";
	private String tktcount="";
	private String tkttype="";
	private String tktJson="";
	private String evtlevel="";
	private String specialfee="";
	private String advSpecialfee="";
	private HashMap<String, String> specialFeeMap=new HashMap<String, String>();
	
	public HashMap<String, String> getSpecialFeeMap() {
		return specialFeeMap;
	}

	public void setSpecialFeeMap(HashMap<String, String> specialFeeMap) {
		this.specialFeeMap = specialFeeMap;
	}

	public String getSpecialfee() {
		return specialfee;
	}

	public void setSpecialfee(String specialfee) {
		this.specialfee = specialfee;
	}

	public String getTktJson() {
		return tktJson;
	}

	public void setTktJson(String tktJson) {
		this.tktJson = tktJson;
	}
	
	public String getEvtlevel() {
		return evtlevel;
	}

	public void setEvtlevel(String evtlevel) {
		this.evtlevel = evtlevel;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getTicketAtt() {
		return ticketAtt;
	}

	public void setTicketAtt(String ticketAtt) {
		this.ticketAtt = ticketAtt;
	}

	public ArrayList<Entity> getHubMap() {
		return hubMap;
	}

	public void setHubMap(ArrayList<Entity> hubMap) {
		this.hubMap = hubMap;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public ArrayList<Entity> getUaQuestions() {
		return uaQuestions;
	}

	public void setUaQuestions(ArrayList<Entity> uaQuestions) {
		this.uaQuestions = uaQuestions;
	}

	public ArrayList<Entity> getAQuestions() {
		return aQuestions;
	}

	public void setAQuestions(ArrayList<Entity> questions) {
		aQuestions = questions;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@SuppressWarnings("unchecked")
	public ArrayList getAllgroups() {
		return allgroups;
	}

	public void setAllgroups(ArrayList allgroups) {
		this.allgroups = allgroups;
	}

	public void prepare() {
	}

	public void populateEventGroups() {
		allgroups = TicketsDB.getEventGroups(eid);
	}

	public void populateTime() {
		hours = TicketsDB.getHours();
		minutes = TicketsDB.getMinutes();
	}

	public void populateEventCommunities() {
		hubMap = EventDB.geteventCommunities(eid);
		if (hubMap == null || hubMap.size() == 0)
			hubMap = EventDB.getManagerCommunities(eid);
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public TicketData getTicketData() {
		return ticketData;
	}

	public void setTicketData(TicketData ticketData) {
		this.ticketData = ticketData;
	}

	public String getT1() {
		return t1;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public String getT2() {
		return t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public String execute() {
		String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
		populateEventGroups();
		populateTime();
		populateEventCommunities();
		//DataPopulator.defaultStartDateDisplayForTickets(ticketData);
		//currency = EventDB.getCurrencySymbol(eid);
		HashMap<String,String> currencydetails=TicketsDB.getCurrencyDetails(eid);
		currency=currencydetails.get("currencysymbol");
		if (currency == null)currency = "$";
		ticketData.setCurrency(currency);
		Double processingfee=0.00;
		String conversionfactor =CurrencyFormat.getCurrencyFormat("",currencydetails.get("conversionfactor"),false);
		String userTimeZone = EventDB.getEventTimeZone(eid);
		//TicketsDB.getEndDate(ticketData, eid, userTimeZone);
		TicketsDB.getTicketDefaultStartDate(ticketData, eid,userTimeZone);//updated on Nov 9th,2013
		TicketsDB.getTicketDefaultEndDate(ticketData, eid);//updated on Nov 9th,2013
		ticketsList=TicketsDB.getEventTicketsList(eid);
		HashMap<String,String> levelFeeMap=TicketsDB.getTicketServiceFee(eid); 
		String servicefee=levelFeeMap.get("currentfee");
		String currentLevel=levelFeeMap.get("currentlevel");
		if("Yes".equalsIgnoreCase(SpecialFeeDB.checkUpgradeStatus(eid, "TicketServiceFee", "Ticketing", evtlevel))){
			//specialfee=SpecialFeeDB.getNewSpecialFee(eid,mgrId,evtlevel,currencydetails.get("currencycode"));
			specialFeeMap=SpecialFeeDB.getNewSpecialFee(eid,mgrId,evtlevel,currencydetails.get("currencycode"));
			if(!specialFeeMap.isEmpty() && specialFeeMap.get(evtlevel) !=null)
				specialfee=specialFeeMap.get(evtlevel);
		}
		try{
			if(Integer.parseInt(currentLevel)<300){
				//advSpecialfee=SpecialFeeDB.getNewSpecialFee(eid,mgrId,"300",currencydetails.get("currencycode"));
				if(specialFeeMap.isEmpty())
					specialFeeMap=SpecialFeeDB.getNewSpecialFee(eid,mgrId,"300",currencydetails.get("currencycode"));
				if(!specialFeeMap.isEmpty() && specialFeeMap.get(evtlevel) !=null)
					advSpecialfee=specialFeeMap.get("300");
			}
		processingfee=Double.parseDouble(servicefee)*Double.parseDouble(conversionfactor );
		}catch(Exception e){System.out.println("Exception occured in parsing value to double in TicketDetails Action");}
		servicefee=CurrencyFormat.getCurrencyFormat("",Double.toString(processingfee),false);
		ticketData.setProcessingFee(servicefee);
		ticketData=TicketsDB.getStartEndDate(ticketData);
		if ("Donation".equals(ticketType)){
			ticketData.setIsScanCode("No");
			return "donationinput";
		}	
		else
			return "input";
	}

	public boolean validateInputData(boolean isrecurr) {
		
		if(!"".equals(ticketData.getProcessingFee().trim())){
			try{
				Double processingfee = Double.parseDouble(ticketData.getProcessingFee());
				if(processingfee<0)
					addFieldError("ticketData.processingFee", I18n.getString("mt.processing.fee.negative.msg"));
			}catch(Exception e){
				addFieldError("ticketData.processingFee", I18n.getString("mt.processing.fee.numeric.msg"));
			}
		}

		if ("".equals(ticketData.getTicketName().trim())) {
			addFieldError("ticketData.ticketName", I18n.getString("mt.tkt.name.error.msg"));

		}
		if ("".equals(ticketData.getTicketPrice())) {
			addFieldError("ticketData.ticketPrice", I18n.getString("mt.tkt.price.empty.msg"));
		} else {

			try {
				Double price = Double.parseDouble(ticketData.getTicketPrice());
				if (price < 0) {
					addFieldError("ticketData.ticketPrice", I18n.getString("mt.invalid.tkt.price"));
				}
			} catch (NumberFormatException e) {
				addFieldError("ticketData.ticketPrice", I18n.getString("mt.tkt.price.num.error.msg"));

			} catch (Exception e) {
				addFieldError("ticketData.ticketPrice", I18n.getString("mt.tkt.price.num.error.msg"));

			}
		}
		if ("".equals(ticketData.getMinQty())) {
			addFieldError("ticketData.minQty",I18n.getString("mt.min.qty.empty.msg"));
		} else {
			try {
				int mqty = Integer.parseInt(ticketData.getMinQty());
				if (mqty <= 0) {
					addFieldError("ticketData.ticketPrice", I18n.getString("mt.invalid.min.qty"));
				}
			} catch (Exception e) {
				addFieldError("ticketData.minQty", I18n.getString("mt.min.qty.numeric.msg"));
			}
		}
		if ("".equals(ticketData.getMaxQty())) {
			addFieldError("ticketData.maxQty", I18n.getString("mt.max.qty.empty.msg"));
		} else {
			try {
				int mqty = Integer.parseInt(ticketData.getMaxQty());
				if (mqty <= 0) {
					addFieldError("ticketData.ticketPrice", I18n.getString("mt.invalid.max.qty.msg"));
				}
			} catch (Exception e) {
				addFieldError("ticketData.maxQty", I18n.getString("mt.max.qrt.numeric.msg"));
			}
		}
		if ("".equals(ticketData.getTotalTicketCount())) {
			addFieldError("ticketData.totalTicketCount", I18n.getString("mt.qty.error.msg"));
		} else {
			try {
				int tqty = Integer.parseInt(ticketData.getTotalTicketCount());
				if (tqty <= 0) {
					addFieldError("ticketData.ticketPrice",I18n.getString("mt.invalid.num.qty.msg"));
				}
			} catch (Exception e) {
				addFieldError("ticketData.totalTicketCount", I18n.getString("mt.qty.numeric.msg"));
			}
		}
		try {
			int minqty = Integer.parseInt(ticketData.getMinQty());
			int maxty = Integer.parseInt(ticketData.getMaxQty());
			int total = Integer.parseInt(ticketData.getTotalTicketCount());
			if (minqty > maxty && minqty > 0 && maxty > 0) {
				addFieldError("ticketData.error", I18n.getString("mt.min.grt.max.msg"));
			}
			if (total < maxty && total > 0 && maxty > 0) {
				addFieldError("ticketData.error1", I18n.getString("mt.max.grt.qty.msg"));
			}
/*			if((minqty==1) && (maxty-minqty>100)){
				addFieldError("ticketData.error2",
				"For performance reasons there is a max limit of 100 tickets per transaction");
			}else*/ if((minqty>=1) && (maxty-minqty>100)){
				addFieldError("ticketData.error3", I18n.getString("mt.max.grt.hundred.msg"));
			}
		} catch (Exception e) {
			// addFieldError("ticketData.error",
			// "Minimum Quantity is Greater than Maximum Quantity");
		}

		if ("LIMIT".equalsIgnoreCase(ticketData.getWaitListType())) {
			if ("".equals(ticketData.getWaitListLimit().trim())) {
				addFieldError("ticketData.waitListLimit", I18n.getString("mt.waitlist.limit.empty.msg"));
			} else {
				try {
					int tqty = Integer.parseInt(ticketData.getWaitListLimit().trim());
					if (tqty <= 0) {
						addFieldError("ticketData.waitListLimit", I18n.getString("mt.invalid.waitlist.msg"));
					}
				} catch (Exception e) {
					addFieldError("ticketData.totalTicketCount", I18n.getString("mt.waitlist.limit.numeric.msg"));
				}
			}
		}
		if (isrecurr) {
			    
			// Start Days Before Field Validation
			if ("".equals(ticketData.getStartBefore())) {
				addFieldError("ticketData.startDaysBefore", I18n.getString("mt.tkt.sale.day.gefore.empty.msg"));
			    } else {
				   try {
					int mqty = Integer.parseInt(ticketData.getStartBefore());
					if(Integer.parseInt(ticketData.getStartBefore())<0)
						addFieldError("ticketData.startDaysBefore", I18n.getString("mt.tkt.sale.day.before.num.msg"));
				   } catch (Exception e) {
					addFieldError("ticketData.startDaysBefore", I18n.getString("mt.tkt.sale.day.before.num.msg"));
				   }
			    }
			
			
			         // Start Hours Before Field Validation
						if ("".equals(ticketData.getStartHoursBefore())) {
							addFieldError("ticketData.startHoursBefore", I18n.getString("mt.tkt.sale.day.before.empty.msg"));
						} else {
							try {
								Integer.parseInt(ticketData.getStartHoursBefore());
								if(Integer.parseInt(ticketData.getStartHoursBefore())<0)
									addFieldError("ticketData.startHoursBefore", I18n.getString("mt.tkt.sale.hr.before.num.msg"));
							} catch (Exception e) {
								// TODO: handle exception
								addFieldError("ticketData.startHoursBefore", I18n.getString("mt.tkt.sale.hr.before.num.msg"));
							}
						}
						// Start Minutes Before Field Validation
						if ("".equals(ticketData.getStartMinutesBefore())) {
							addFieldError("ticketData.startMinutesBefore", I18n.getString("mt.tkt.start.min.before.empty.msg"));
						} else {
							try {
								Integer.parseInt(ticketData.getStartMinutesBefore());
								if(Integer.parseInt(ticketData.getStartMinutesBefore())<0)
									addFieldError("ticketData.startMinutesBefore", I18n.getString("mt.start.min.before.evt.num.msg"));
							} catch (Exception e) {
								// TODO: handle exception
								addFieldError("ticketData.startMinutesBefore", I18n.getString("mt.start.min.before.evt.num.msg"));
							}
						}
			
			
			
			// End Days Before Field Validation
			
			if ("".equals(ticketData.getEndBefore())) {
				addFieldError("ticketData.endDaysBefore", I18n.getString("mt.tkt.end.before.empty.msg"));
			} else {
				try {
					int mqty = Integer.parseInt(ticketData.getEndBefore());
					if(Integer.parseInt(ticketData.getEndBefore())<0)
						addFieldError("ticketData.endDaysBefore", I18n.getString("mt.tkt.end.before.endbefore.num.msg"));
				} catch (Exception e) {
					addFieldError("ticketData.endDaysBefore", I18n.getString("mt.tkt.end.before.endbefore.num.msg"));
				}
			}
			/* 23-09-2010 */
			
			// End Hours Before Field Validation
			if ("".equals(ticketData.getEndHoursBefore())) {
				addFieldError("ticketData.endHoursBefore", I18n.getString("mt.tkt.end.sale.before.empty.msg"));
			} else {
				try {
					Integer.parseInt(ticketData.getEndHoursBefore());
					if(Integer.parseInt(ticketData.getEndHoursBefore())<0)
						addFieldError("ticketData.endHoursBefore", I18n.getString("mt.tkt.end.before.evt.num.msg"));
				} catch (Exception e) {
					// TODO: handle exception
					addFieldError("ticketData.endHoursBefore", I18n.getString("mt.tkt.end.before.evt.num.msg"));
				}
			}
			// End Minutes Before Field Validation
			if ("".equals(ticketData.getEndMinutesBefore())) {
				addFieldError("ticketData.endMinutesBefore", I18n.getString("mt.tkt.end.minbefore.evt.empty"));
			} else {
				try {
					Integer.parseInt(ticketData.getEndMinutesBefore());
					if(Integer.parseInt(ticketData.getEndMinutesBefore())<0)
						addFieldError("ticketData.endMinutesBefore", I18n.getString("mt.tkt.end.min.before.num.msg"));
				} catch (Exception e) {
					// TODO: handle exception
					addFieldError("ticketData.endMinutesBefore", I18n.getString("mt.tkt.end.min.before.num.msg"));
				}
			}
			/* 23-09-2010 */
		}
		else{
			/* validation for non recurring */
			
			//validateNormalInputData();
		}
		if (!getFieldErrors().isEmpty()) {
			return false;
		}
		return true;
	}

	public String save() {
		
		boolean isrecurr = EventDB.isRecurringEvent(eid);
		currency = EventDB.getCurrencySymbol(eid);
		if(currency == null)
			currency = "$";
		ticketData.setCurrency(currency);
		boolean status = validateInputData(isrecurr);
        if (status) {
			if ("".equals(ticketData.getTicketId())) {
				msgKey = "ticket.added";
			} else
				msgKey = "ticket.updated";
			ticketData.setTicketType(ticketType);
			ticketData.setTicketGroupId(groupId);
			String userTimeZone = EventDB.getEventTimeZone(eid);
			//TicketsDB.saveTicketData(ticketData, eid, userTimeZone, isrecurr);
			if(isrecurr==false)
			ticketData=TicketsDB.setStartEndDates(ticketData);
			ticketData =TicketsDB.saveTicketData(ticketData, eid, userTimeZone, isrecurr);
			try {
				msgKey=(new JSONObject().put("status","success").put("id",ticketData.getTicketId()).put("gid",ticketData.getTicketGroupId())).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			System.out.println("removeGLobalEventCache: while adding ticket");
			EventHelperDB.removeGLobalEventCache(eid, "remove", "ticketsinfo");
            return "ajaxmsg";
		} else {
			return "inputerror";
		}
	}
	
	public String getEventCapacity(){
		tktcount=EventDB.getConfigVal(eid,"event.reg.eventlevelcheck.count","");
		System.out.println("the ticket count is::"+tktcount);
		if(tktcount==null || "0".equals(tktcount) || "".equals(tktcount))
			tkttype="unlimited";
		else
			tkttype="limited";
		return "eventcapacity";
	}
	
	public String saveEventCapacity(){

		int count=0;
		int exception=0;
		if("limited".equals(tkttype)){
		if("".equals(tktcount.trim()))
			addFieldError("tktcount",I18n.getString("mt.tot.evt.capacity.empty"));
		else{
		try{
			count=Integer.parseInt(tktcount.trim());
		}catch(Exception e){
			addFieldError("ticketcount",I18n.getString("mt.tot.evt.capacity.num.error"));
		exception++;
		}
		if(count<0)
		addFieldError("ticketcount",I18n.getString("mt.invalid.num.evt.capacity"));
		}
		if(!getFieldErrors().isEmpty()){
			return "donationinputerror";
		}
		}
		TicketsDB.saveTicketCount(eid,tktcount.trim(),tkttype);
		String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
        SpecialFeeDB.chekingSpecialFee(eid,mgrId,"200","eventcapacity");
		return "success";
	}
	

	public String donationSave() {
		currency = EventDB.getCurrencySymbol(eid);		
		boolean isrecurr = EventDB.isRecurringEvent(eid);
		if (currency == null)
			currency = "$";
		ticketData.setCurrency(currency);			
	    boolean status = validateDonationInputData(isrecurr);	    
		if (status) {
			if ("".equals(ticketData.getTicketId()))
				msgKey = "donation.added";
			
			ticketData.setTicketType(ticketType);
			ticketData.setTicketGroupId(groupId);
			String userTimeZone = EventDB.getEventTimeZone(eid);
			if(isrecurr==false)
			ticketData=TicketsDB.setStartEndDates(ticketData);
			TicketsDB.saveTicketData(ticketData, eid, userTimeZone, isrecurr);
			JSONObject jsonObj=new JSONObject();
			try{
			msgKey=(new JSONObject().put("status","success").put("id",ticketData.getTicketId()).put("gid",ticketData.getTicketGroupId())).toString();
			}catch(Exception e){
				
			}
			System.out.println("removeGLobalEventCache: while adding donation ticket");
			EventHelperDB.removeGLobalEventCache(eid, "remove", "ticketsinfo");
			return "ajaxmsg";
		} else
			return "donationinputerror";
	}
	
	
	public boolean validateDonationInputData(boolean isrecurr) {

		if ("".equals(ticketData.getTicketName().trim())) {
			addFieldError("ticketData.ticketName", I18n.getString("mt.dont.name.req.msg"));
		}
		
		
		if (isrecurr) {
			if ("".equals(ticketData.getStartBefore())) {
				addFieldError("ticketData.startDaysBefore", I18n.getString("mt.start.day.before.empty"));
			} else {
				try {
					int mqty = Integer.parseInt(ticketData.getStartBefore());
				} catch (Exception e) {
					addFieldError("ticketData.startDaysBefore", I18n.getString("mt.start.day.before.num.msg"));
				}
			}
			if ("".equals(ticketData.getEndBefore())) {
				addFieldError("ticketData.endDaysBefore", I18n.getString("mt.end.day.before.empty.msg"));
			} else {
				try {
					int mqty = Integer.parseInt(ticketData.getEndBefore());
				} catch (Exception e) {
					addFieldError("ticketData.endDaysBefore", I18n.getString("mt.end.day.before.evt.num.error.msg"));
				}
			}
			/* Author:Rahul my code starts here */
			// Start Hours Before Field Validation
			if ("".equals(ticketData.getStartHoursBefore())) {
				addFieldError("ticketData.startHoursBefore", I18n.getString("mt.tkt.sale.start.hr.before.empty"));
			} else {
				try {
					Integer.parseInt(ticketData.getStartHoursBefore());
				} catch (Exception e) {
					// TODO: handle exception
					addFieldError("ticketData.startHoursBefore", I18n.getString("mt.tkt.sale.hr.before.num.msg"));
				}
			}
			// Start Minutes Before Field Validation
			if ("".equals(ticketData.getStartMinutesBefore())) {
				addFieldError("ticketData.startMinutesBefore", I18n.getString("mt.tkt.start.min.before.empty.msg"));
			} else {
				try {
					Integer.parseInt(ticketData.getStartMinutesBefore());
				} catch (Exception e) {
					// TODO: handle exception
					addFieldError("ticketData.startMinutesBefore", I18n.getString("mt.start.min.before.evt.num.msg"));
				}
			}
			// End Hours Before Field Validation
			if ("".equals(ticketData.getEndHoursBefore())) {
				addFieldError("ticketData.endHoursBefore", I18n.getString("mt.tkt.sale.hr.before.empty"));
			} else {
				try {
					Integer.parseInt(ticketData.getEndHoursBefore());
				} catch (Exception e) {
					// TODO: handle exception
					addFieldError("ticketData.endHoursBefore", I18n.getString("mt.tkt.end.before.evt.num.msg"));
				}
			}
			// End Hours Before Field Validation
			if ("".equals(ticketData.getEndMinutesBefore())) {
				addFieldError("ticketData.endMinutesBefore", I18n.getString("mt.tkt.end.minbefore.evt.empty"));
			} else {
				try {
					Integer.parseInt(ticketData.getEndMinutesBefore());
				} catch (Exception e) {
					// TODO: handle exception
					addFieldError("ticketData.endMinutesBefore", I18n.getString("mt.tkt.end.min.before.num.msg"));
				}
			}
			/* Author:Rahul my code ends here */
		}
		else{
			/* validation for non recurring */
			//validateNormalInputData();		
		}
		
		
		if (!getFieldErrors().isEmpty()) {
			return false;
		}
		return true;

	}
	private void validateNormalInputData(){
		/* modified by me */
		

		if ("".equals(ticketData.getStdateTimeBeanObj().getMonthPart())) {
			addFieldError("ticketData.stdateTimeBeanObj.monthPart",I18n.getString("mt.tkt.strt.date.month.empty"));
		} 
		else {			
		String validate=validateNumber(ticketData.getStdateTimeBeanObj().getMonthPart());
		if("notvalidnumber".equals(validate))
			addFieldError("ticketData.stdateTimeBeanObj.monthPart",I18n.getString("mt.date.month.numeric.error.msg"));
		}
		
		
		if ("".equals(ticketData.getStdateTimeBeanObj().getDdPart())) {
			addFieldError("ticketData.stdateTimeBeanObj.ddPart",I18n.getString("mt.start.date.field.empty.msg"));
		}else{			 
				String validate=validateNumber(ticketData.getStdateTimeBeanObj().getDdPart());
				if("notvalidnumber".equals(validate))
				addFieldError("ticketData.stdateTimeBeanObj.ddPart",I18n.getString("mt.sale.srt.date.numeric.msg"));
				}			
		
		// Start Minutes Before Field Validation
		if ("".equals(ticketData.getStdateTimeBeanObj().getYyPart())) {
			addFieldError("ticketData.stdateTimeBeanObj.yyPart",I18n.getString("mt.srt.date.year.empty.error.msg"));
		} 
		else{
			String validate=validateNumber(ticketData.getStdateTimeBeanObj().getYyPart());
			if("notvalidnumber".equals(validate))
				addFieldError("ticketData.stdateTimeBeanObj.yyPart",I18n.getString("mt.sale.srt.date.year.num.msg"));
		}
		
		if ("".equals(ticketData.getEnddateTimeBeanObj().getMonthPart())) {
			addFieldError("ticketData.enddateTimeBeanObj.monthPart",I18n.getString("mt.sale.end.date.month.empty"));
		} else {
			String validate=validateNumber(ticketData.getEnddateTimeBeanObj().getMonthPart());
			if("notvalidnumber".equals(validate))
				addFieldError("ticketData.enddateTimeBeanObj.monthPart",I18n.getString("mt.sale.end.month.numeric.msg"));
		}
		
		// End Hours Before Field Validation
		if ("".equals(ticketData.getEnddateTimeBeanObj().getDdPart())) {
			addFieldError("ticketData.enddateTimeBeanObj.ddPart",I18n.getString("mt.sale.end.date.empty.msg"));
		} else{
			String validate=validateNumber(ticketData.getEnddateTimeBeanObj().getDdPart());
			if("notvalidnumber".equals(validate))
				addFieldError("ticketData.enddateTimeBeanObj.ddPart",I18n.getString("mt.sale.end.date.numeric"));
		}
		
		// End Hours Before Field Validation
		if ("".equals(ticketData.getEnddateTimeBeanObj().getYyPart())) {
			addFieldError("ticketData.enddateTimeBeanObj.yyPart",I18n.getString("mt.sale.end.year.empty.eror.msg"));
		} else{
			String validate=validateNumber(ticketData.getEnddateTimeBeanObj().getYyPart());
			if("notvalidnumber".equals(validate))
			addFieldError("ticketData.enddateTimeBeanObj.yyPart",I18n.getString("mt.sale.end.date.numeric.msg"));
			}
	}
	
	public String validateNumber(String number){
	    if (number==null || number.length()==0)return "notvalidnumber";
	    for (int i = 0; i < number.length(); i++) {
	      if (!Character.isDigit(number.charAt(i)))
	      return "notvalidnumber";
	    }
	    return "validnumber";
	}

	public String optselector() {
		populateEventGroups();
		return "optselector";
	}
	
	
	public String getCopyTicketDetails(){
if("inputeidt".equals(edit()))
		return "input";
	return "input";
	}
	
	
	

	public String edit() {
		System.out.println("TicketDetailsAction edit method");
		String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
		populateEventGroups();
		populateTime();
		populateEventCommunities();
		String userTimeZone = EventDB.getEventTimeZone(eid);
		//currency = EventDB.getCurrencySymbol(eid);
		HashMap<String,String> currencydetails=TicketsDB.getCurrencyDetails(eid);
		currency=currencydetails.get("currencysymbol");
		String conversionfactor=CurrencyFormat.getCurrencyFormat("",currencydetails.get("conversionfactor"),false);
		ticketData = TicketsDB.getTicketData(eid, ticketId, userTimeZone,conversionfactor);
		if("Yes".equalsIgnoreCase(SpecialFeeDB.checkUpgradeStatus(eid, "TicketServiceFee", "Ticketing", evtlevel))){
			//specialfee=SpecialFeeDB.getNewSpecialFee(eid,mgrId,evtlevel,currencydetails.get("currencycode"));
			specialFeeMap=SpecialFeeDB.getNewSpecialFee(eid,mgrId,evtlevel,currencydetails.get("currencycode"));
			if(!specialFeeMap.isEmpty() && specialFeeMap.get(evtlevel) !=null)
				specialfee=specialFeeMap.get(evtlevel);
		}
		if(specialFeeMap.isEmpty())
			specialFeeMap=SpecialFeeDB.getNewSpecialFee(eid,mgrId,"300",currencydetails.get("currencycode"));
		if(!specialFeeMap.isEmpty() && specialFeeMap.get("300") !=null)
			advSpecialfee=specialFeeMap.get("300");
		//advSpecialfee=SpecialFeeDB.getNewSpecialFee(eid,mgrId,"300",currencydetails.get("currencycode"));
		if("selecttkt".equals(purpose)){
		ticketData.setOldTicket(ticketId);
		ticketData.setTicketId("");
	    //TicketsDB.getEndDate(ticketData, eid, userTimeZone);
		TicketsDB.getTicketDefaultEndDate(ticketData, eid);//updated on Nov 9th,2013
		}
		String groupId1 = TicketsDB.getGroupIdforTicket(ticketId);
		GroupData gdata=new GroupData();
		gdata=TicketsDB.getGroupInfo(groupId1, eid);
		String groupname=gdata.getGroupName();
		if("".equals(groupname) && "selecttkt".equals(purpose)){
	    ticketData.setTicketGroupId("");
		setGroupId("");
		}else{
		  ticketData.setTicketGroupId(groupId1);
	      setGroupId(groupId1);
		}
		
		ticketData=TicketsDB.getStartEndDate(ticketData);
		
		if("processFeeMgr".equals(ticketData.getProcessFeePaidBy())){
			Double processingfee=0.00;
			HashMap<String,String> levelFeeMap=TicketsDB.getTicketServiceFee(eid); 
			String servicefee=levelFeeMap.get("currentfee");
			try{
				processingfee=Double.parseDouble(servicefee)*Double.parseDouble(conversionfactor );
			}catch(Exception e){}
			servicefee=CurrencyFormat.getCurrencyFormat("",Double.toString(processingfee),false);
			ticketData.setProcessingFee(servicefee);
		}
		
		String typeOfTicket = ticketData.getIsAttendee();
		if ("Yes".equalsIgnoreCase(typeOfTicket)) {
			ticketAtt = "ATTENDEE";
		} else
			ticketAtt = "NONATTENDEE";
		String isDonation = ticketData.getIsDonation();
		if ("Yes".equalsIgnoreCase(isDonation))
			ticketType = "Donation";
        if (currency == null)
			currency = "$";
		ticketData.setCurrency(currency);

		if (ticketData == null) {
			msgKey = "Ticket.idnotfound";
			return "success";
		}
		if ("Donation".equalsIgnoreCase(ticketType)) {
			return "donationinput";
		}
		return "inputedit";
	}
	
	public String updatePositions(){
		JSONObject result=new JSONObject();
		try{
		
		JSONObject tjobj=new JSONObject(tktJson);
		String type=(String)tjobj.get("type");
		if("ll".equals(type) ||"gl".equals(type) ){result=TicketsDBAdvanced.shiftGroupPosition(tjobj,type,eid);}
		else{result=TicketsDBAdvanced.shiftTicketPosition(tjobj,type,eid);};			
		System.out.println("tobj"+tjobj);
		msgKey=result.toString();
		
		}catch(Exception e){System.out.println(" error while update positions"+e.getMessage());try {
			msgKey=result.put("status", "fail").toString();
		} catch (JSONException e1) {
		}}
		
		return "ajaxmsg";
	}

	public String swapTicket() {
		// select price_id from group_tickets where price_id!= order by position
		// desc limit 1
		boolean status = TicketsDB.swapTicket(eid, groupId, t1, p1, t2, p2);
		return "success";
	}

	public String swapGroup() {
		@SuppressWarnings("unused")
		boolean status = TicketsDB.swapGroup(eid, groupId, t1, p1, t2, p2);
		return "success";
	}

	public String deleteGroup() {
		boolean status = TicketsDB.deleteGroup(eid, groupId);
		return "success";
	}

	public String deleteTicket() {
		boolean status = TicketsDB.deleteTicket(eid, ticketId);
		EventHelperDB.removeGLobalEventCache(eid, "remove", "ticketsinfo");
		return "success";
	}

	public String attribQuestionSelector() {
		uaQuestions = TicketsDB.getuaQuestions(eid, ticketId);
		aQuestions = TicketsDB.getAnsweredQuestions(eid, ticketId);
		return "attribQuestionSelector";

	}
	
	public String selectTktDetails(){
		ticketsList=TicketsDB.getEventTicketsList(eid);
		return edit();
	}

	public String submitRegQuestionsforTicket() {
		for (Entity aqs : aQuestions) {
			// System.out.println("key"+aqs.getKey());
			// System.out.println("value"+aqs.getValue());
		}
		return "success";
	}

	public String agreeServiceFee(){
		SpecialFeeDB.insertSpecialFee(eid,evtlevel,specialfee,"TicketServiceFee");
		return "success";
	}

	public ArrayList<Entity> getTicketsList() {
		return ticketsList;
	}

	public void setTicketsList(ArrayList<Entity> ticketsList) {
		this.ticketsList = ticketsList;
	}

	public String getTkttype() {
		return tkttype;
	}

	public void setTkttype(String tkttype) {
		this.tkttype = tkttype;
	}

	public String getTktcount() {
		return tktcount;
	}

	public void setTktcount(String tktcount) {
		this.tktcount = tktcount;
	}

	public String getAdvSpecialfee() {
		return advSpecialfee;
	}

	public void setAdvSpecialfee(String advSpecialfee) {
		this.advSpecialfee = advSpecialfee;
	}

}
