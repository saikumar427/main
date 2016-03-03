package com.eventmanage.groupticketing.beans;

import com.eventbee.beans.DateTimeBean;
import java.util.Date;




public class GroupDealTicketsData {
	  private String ticketName="";
	  private String description="";
	  private String price="";
	  private String discounttype="";
	  private String discountvalue="";
	  private String triggerqty="1";
	  private String minqty="";
	  private String  maxqty="";
	  private Date   startdate_req;
	  private String starttime_req="";
	  private String startampm="";
	  
	  private String approval_status="";
	  private Date act_startdate;
	  private String act_startime="";
	  private String  act_startampm="";
	  private Date enddate;
	  private String  endtime="";
	  private String endampm="";
	  private String activeduration="";
	  
	  private String show_status="";
	  private String  soldqty="0";
	  private String currentcycle="";
	  private String createdat="";
	  private String lastupdated="";
	  private String post_trigger_type="3";
	  
	  private String upperlimit="";
	  private String cycleslimit="2";
	  private String position="";
	  private String trigger_fail_action="1";
	  private String startbeforedays="";
	  private String startbeforehours="";
	  private String startbeforeminutes="";
	  private String start_type="";
		
	  private String currency="$";
      private DateTimeBean stdateTimeBeanObj;
	  private DateTimeBean enddateTimeBeanObj;
      private String processFeePaidBy="processFeeMgr";
      private String groupticketid="";
      private String triggerfailDicount="0";
      private String percentageSymbol="%";
      
	public String getPercentageSymbol() {
		return percentageSymbol;
	}
	public void setPercentageSymbol(String percentageSymbol) {
		this.percentageSymbol = percentageSymbol;
	}
	public String getTriggerfailDicount() {
		return triggerfailDicount;
	}
	public void setTriggerfailDicount(String triggerfailDicount) {
		this.triggerfailDicount = triggerfailDicount;
	}
	public String getGroupticketid() {
		return groupticketid;
	}
	public void setGroupticketid(String groupticketid) {
		this.groupticketid = groupticketid;
	}
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDiscounttype() {
		return discounttype;
	}
	public void setDiscounttype(String discounttype) {
		this.discounttype = discounttype;
	}
	public String getDiscountvalue() {
		return discountvalue;
	}
	public void setDiscountvalue(String discountvalue) {
		this.discountvalue = discountvalue;
	}
	public String getTriggerqty() {
		return triggerqty;
	}
	public void setTriggerqty(String triggerqty) {
		this.triggerqty = triggerqty;
	}
	public String getMinqty() {
		return minqty;
	}
	public void setMinqty(String minqty) {
		this.minqty = minqty;
	}
	public String getMaxqty() {
		return maxqty;
	}
	public void setMaxqty(String maxqty) {
		this.maxqty = maxqty;
	}
	public Date getStartdate_req() {
		return startdate_req;
	}
	public void setStartdate_req(Date startdate_req) {
		this.startdate_req = startdate_req;
	}
	public String getStarttime_req() {
		return starttime_req;
	}
	public void setStarttime_req(String starttime_req) {
		this.starttime_req = starttime_req;
	}
	public String getStartampm() {
		return startampm;
	}
	public void setStartampm(String startampm) {
		this.startampm = startampm;
	}
	public String getApproval_status() {
		return approval_status;
	}
	public void setApproval_status(String approval_status) {
		this.approval_status = approval_status;
	}
	public Date getAct_startdate() {
		return act_startdate;
	}
	public void setAct_startdate(Date act_startdate) {
		this.act_startdate = act_startdate;
	}
	public String getAct_startime() {
		return act_startime;
	}
	public void setAct_startime(String act_startime) {
		this.act_startime = act_startime;
	}
	public String getAct_startampm() {
		return act_startampm;
	}
	public void setAct_startampm(String act_startampm) {
		this.act_startampm = act_startampm;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getEndampm() {
		return endampm;
	}
	public void setEndampm(String endampm) {
		this.endampm = endampm;
	}
	public String getActiveduration() {
		return activeduration;
	}
	public void setActiveduration(String activeduration) {
		this.activeduration = activeduration;
	}
	public String getShow_status() {
		return show_status;
	}
	public void setShow_status(String show_status) {
		this.show_status = show_status;
	}
	public String getSoldqty() {
		return soldqty;
	}
	public void setSoldqty(String soldqty) {
		this.soldqty = soldqty;
	}
	public String getCurrentcycle() {
		return currentcycle;
	}
	public void setCurrentcycle(String currentcycle) {
		this.currentcycle = currentcycle;
	}
	public String getCreatedat() {
		return createdat;
	}
	public void setCreatedat(String createdat) {
		this.createdat = createdat;
	}
	public String getLastupdated() {
		return lastupdated;
	}
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}
	public String getPost_trigger_type() {
		return post_trigger_type;
	}
	public void setPost_trigger_type(String post_trigger_type) {
		this.post_trigger_type = post_trigger_type;
	}
	public String getUpperlimit() {
		return upperlimit;
	}
	public void setUpperlimit(String upperlimit) {
		this.upperlimit = upperlimit;
	}
	public String getCycleslimit() {
		return cycleslimit;
	}
	public void setCycleslimit(String cycleslimit) {
		this.cycleslimit = cycleslimit;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTrigger_fail_action() {
		return trigger_fail_action;
	}
	public void setTrigger_fail_action(String trigger_fail_action) {
		this.trigger_fail_action = trigger_fail_action;
	}
	public String getStartbeforedays() {
		return startbeforedays;
	}
	public void setStartbeforedays(String startbeforedays) {
		this.startbeforedays = startbeforedays;
	}
	public String getStartbeforehours() {
		return startbeforehours;
	}
	public void setStartbeforehours(String startbeforehours) {
		this.startbeforehours = startbeforehours;
	}
	public String getStartbeforeminutes() {
		return startbeforeminutes;
	}
	public void setStartbeforeminutes(String startbeforeminutes) {
		this.startbeforeminutes = startbeforeminutes;
	}
	public String getStart_type() {
		return start_type;
	}
	public void setStart_type(String start_type) {
		this.start_type = start_type;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public DateTimeBean getStdateTimeBeanObj() {
		return stdateTimeBeanObj;
	}
	public void setStdateTimeBeanObj(DateTimeBean stdateTimeBeanObj) {
		this.stdateTimeBeanObj = stdateTimeBeanObj;
	}
	public DateTimeBean getEnddateTimeBeanObj() {
		return enddateTimeBeanObj;
	}
	public void setEnddateTimeBeanObj(DateTimeBean enddateTimeBeanObj) {
		this.enddateTimeBeanObj = enddateTimeBeanObj;
	}
	public String getProcessFeePaidBy() {
		return processFeePaidBy;
	}
	public void setProcessFeePaidBy(String processFeePaidBy) {
		this.processFeePaidBy = processFeePaidBy;
	}
	  
	 	
}
