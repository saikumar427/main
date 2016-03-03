package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Locale;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.event.beans.ReportsData;
import com.event.dbhelpers.DiscountsDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.ReportsDB;
import com.event.dbhelpers.TransactionDB;
import com.event.helpers.I18n;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.EventbeeStrings;
import com.eventregister.TicketingInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.log4j.Logger;

public class EditTransactionTicketsAction extends ActionSupport {

	/**
	 * 
	 */
	private static Logger log = Logger
			.getLogger(EditTransactionTicketsAction.class);
	private static final long serialVersionUID = 1L;
	private String tid = "";
	private String eid = "";
	HashMap ticketsMap = null;
	ArrayList ticketsArray = null;
	private Vector ticketdetails = null;
	private Vector allticketdetails = null;
	private ArrayList ticketname;
	private ArrayList ticketid;
	private ArrayList ticketgroupid;
	private ArrayList groupname;
	private ArrayList ticketprices;
	private ArrayList discount;
	private ArrayList qty;
	private ArrayList total;
	private String finali = "";
	private String tax = "";
	private String totaldiscount1 = "";
	private String totalamount1 = "";
	private String totalnetamount="";
	private int remqty;
	private ReportsData reportsData = new ReportsData();
	private String msgType = "editTransaction";
	private String currencySymbol = "";
	private ArrayList dates = new ArrayList();
	private String currentTransactionDate = "";
	private ArrayList responseDetails = new ArrayList();
	private ArrayList ticketfees;
	private ArrayList qtyoriginal;
	
	public ArrayList getQtyoriginal() {
		return qtyoriginal;
	}

	public void setQtyoriginal(ArrayList qtyoriginal) {
		this.qtyoriginal = qtyoriginal;
	}

	public ArrayList getResponseDetails() {
		return responseDetails;
	}

	public void setResponseDetails(ArrayList responseDetails) {
		this.responseDetails = responseDetails;
	}

	public String getCurrentTransactionDate() {
		return currentTransactionDate;
	}

	public void setCurrentTransactionDate(String currentTransactionDate) {
		this.currentTransactionDate = currentTransactionDate;
	}

	public ArrayList getDates() {
		return dates;
	}

	public void setDates(ArrayList dates) {
		this.dates = dates;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getTotaldiscount1() {
		return totaldiscount1;
	}

	public void setTotaldiscount1(String totaldiscount1) {
		this.totaldiscount1 = totaldiscount1;
	}

	public String getTotalamount1() {
		return totalamount1;
	}

	public void setTotalamount1(String totalamount1) {
		this.totalamount1 = totalamount1;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getFinali() {
		return finali;
	}

	public void setFinali(String finali) {
		this.finali = finali;
	}

	public ArrayList getTicketid() {
		return ticketid;
	}

	public void setTicketid(ArrayList ticketid) {
		this.ticketid = ticketid;
	}

	public ArrayList getTicketgroupid() {
		return ticketgroupid;
	}

	public void setTicketgroupid(ArrayList ticketgroupid) {
		this.ticketgroupid = ticketgroupid;
	}

	public ArrayList getGroupname() {
		return groupname;
	}

	public void setGroupname(ArrayList groupname) {
		this.groupname = groupname;
	}

	public ArrayList getTicketprices() {
		return ticketprices;
	}

	public void setTicketprices(ArrayList ticketprices) {
		this.ticketprices = ticketprices;
	}

	public ArrayList getDiscount() {
		return discount;
	}

	public void setDiscount(ArrayList discount) {
		this.discount = discount;
	}

	public ArrayList getQty() {
		return qty;
	}

	public void setQty(ArrayList qty) {
		this.qty = qty;
	}

	public ArrayList getTotal() {
		return total;
	}

	public void setTotal(ArrayList total) {
		this.total = total;
	}

	public ArrayList getTicketname() {
		return ticketname;
	}

	public void setTicketname(ArrayList ticketname) {
		this.ticketname = ticketname;
	}

	public ReportsData getReportsData() {
		return reportsData;
	}

	public void setReportsData(ReportsData reportsData) {
		this.reportsData = reportsData;
	}

	public Vector getAllticketdetails() {
		return allticketdetails;
	}

	public void setAllticketdetails(Vector allticketdetails) {
		this.allticketdetails = allticketdetails;
	}

	public Vector getTicketdetails() {
		return ticketdetails;
	}

	public void setTicketdetails(Vector ticketdetails) {
		this.ticketdetails = ticketdetails;
	}

	public ArrayList getTicketsArray() {
		return ticketsArray;
	}

	public void setTicketsArray(ArrayList ticketsArray) {
		this.ticketsArray = ticketsArray;
	}

	public HashMap getTicketsMap() {
		return ticketsMap;
	}

	public void setTicketsMap(HashMap ticketsMap) {
		this.ticketsMap = ticketsMap;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String execute() {

		// getTransactionTicketDetails();
		getEventTickets();

		return "success";
	}

	public String editTickets() {

		ReportsDB.getCurrentTransctionDate(eid, tid);
		currentTransactionDate = ReportsDB.getCurrentTransctionDate(eid, tid);
		System.out.println("we r in edit tickets" + currentTransactionDate);
		dates = ReportsDB.getEventDates(eid, tid);
        currencySymbol = EventDB.getCurrencySymbol(eid);
		ticketdetails = TransactionDB.getTicketInfo(tid,eid);
		allticketdetails = TransactionDB.getAllTicketInfo(eid);
		TransactionDB.mergeVectors(ticketdetails, allticketdetails);
		TransactionDB.getAmountDetails(tid, eid, reportsData);
		return "success";
	}

	public String rsvpEditResponses() {
		// ReportsDB.getCurrentTransctionDate(eid,tid);
		// currentTransactionDate=ReportsDB.getCurrentTransctionDate(eid, tid);
		// System.out.println("we r in edit tickets"+currentTransactionDate);
		// dates=ReportsDB.getEventDates(eid,tid);
		reportsData = TransactionDB.getResponseInfo(eid, tid);

		return "rsvpsuccess";
	}

	public boolean validateInputData() {
		ResourceBundle resourceBundle =I18n.getResourceBundle();
		ReportsData old_reportsdata = TransactionDB.getResponseInfo(eid, tid);
		String addsure = reportsData.getAddsure().trim();
		String addnotsure = reportsData.getAddnotsure().trim();
		String old_responsetype = old_reportsdata.getResponsetype();
		
		log.info("reportsData.getAddsure: " + reportsData.getAddsure());
		log.info("reportsData.getAddnotsure: " + reportsData.getAddnotsure());

		if ("".equals(reportsData.getAddsure().trim())) {
			addFieldError("reportsData.addsure",resourceBundle.getString("ed.trns.tkt.sre.attnd.lbl"));
		} else {
			try {
				if (Integer.parseInt(addsure) < 0) {
					addFieldError("reportsData.addsure",resourceBundle.getString("ed.plZ.entr.vald.num.lbl"));
				}
			} catch (Exception e) {
				addFieldError("reportsData.addsure",resourceBundle.getString("ed.val.shld.num.lbl"));
			}

		}

		if ("".equals(reportsData.getAddnotsure().trim())) {
			addFieldError("reportsData.addnotsure",resourceBundle.getString("ed.ntsure.attnd.emty.lbl"));
		} else {
			try {
				if (Integer.parseInt(addnotsure) < 0) {
					addFieldError("reportsData.addnotsure",resourceBundle.getString("ed.plZ.entr.vald.num.lbl"));

				}
			} catch (Exception e) {
				addFieldError("reportsData.addnotsure",resourceBundle.getString("ed.val.shld.num.lbl"));
			}

		}

		try{if ("N".equals(old_responsetype)
				&& (Integer.parseInt(addsure) == 0 && Integer
						.parseInt(addnotsure) == 0)) {
			addFieldError("reportsData.addsure",resourceBundle.getString("ed.atlst.oneval.rqrd.attnde.resp.lbl"));
		}
		}catch(Exception e){
			addFieldError("reportsData.addsure",resourceBundle.getString("ed.atlst.oneval.rqrd.attnde.lbl"));
			
		}

		if (!getFieldErrors().isEmpty()) {
			// addFieldError("discountData.errorsExists",
			// "There are some errors");

			return false;
		}
		return true;
	}

	public String rsvpUpdateResponses() {

		System.out.println("we r in rsvpupdateresponses");
		boolean status = validateInputData();
		if (status) {
			System.out.println("status in if rsvpupdateresponse" + status);
			TransactionDB.updateResponses(reportsData, eid, tid);
			return "transactiondetailspage";

		} else {
			System.out.println("status in else rsvpupdateresponse" + status);
			reportsData = TransactionDB.getResponseInfo(eid, tid);
			return "inputerror";
		}

	}

	public String changeToNotAttending() {
		TransactionDB.attendingToNotattending(eid, tid);
		return "transactiondetailspage";
	}

	public String updateTickets() {
		int size = Integer.valueOf(finali).intValue();
		String date = reportsData.getDate();
		currentTransactionDate = ReportsDB.getCurrentTransctionDate(eid, tid);
		System.out.println("eventdate and changed date"
				+ currentTransactionDate + ":" + date);
		TransactionDB.updateTransactionTickets(size, ticketname, groupname,
				ticketgroupid, qty, ticketid, discount, ticketprices, total,
				eid, tid, totaldiscount1, totalamount1, tax,totalnetamount,remqty,ticketfees);
		/*if (!"".equals(date) && date != null) {
			TransactionDB.updateEventDate(eid, tid, date,
					currentTransactionDate);
		}*/
		  
		  String isrecurr=DbUtil.getVal("select value from config where name='event.recurring' and config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[]{eid});
	        if("Y".equals(isrecurr)){
	            if ("".equals(date) || date == null)
	                date=currentTransactionDate;
	            TransactionDB.updateEventDate(eid, tid, date, currentTransactionDate);
	        }

		return "ajaxstatusmsg";
	}

	void getEventTickets() {
		TicketingInfo tktinfo = new TicketingInfo();
		tktinfo.intialize(eid, null, null, null);
		ticketsArray = tktinfo.requiredTicketGroups;

	}

	public String getTotalnetamount() {
		return totalnetamount;
	}

	public void setTotalnetamount(String totalnetamount) {
		this.totalnetamount = totalnetamount;
	}

	public int getRemqty() {
		return remqty;
	}

	public void setRemqty(int remqty) {
		this.remqty = remqty;
	}

	public ArrayList getTicketfees() {
		return ticketfees;
	}

	public void setTicketfees(ArrayList ticketfees) {
		this.ticketfees = ticketfees;
	}

	

}
