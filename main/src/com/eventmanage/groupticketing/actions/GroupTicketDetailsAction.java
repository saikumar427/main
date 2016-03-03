package com.eventmanage.groupticketing.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONObject;

import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.TicketsDB;
import com.eventmanage.groupticketing.helpers.DataPopulator;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.beans.Entity;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.formatting.CurrencyFormat;
import com.eventmanage.groupticketing.beans.GroupDealTicketsData;
import com.eventmanage.groupticketing.dbhelpers.GroupDealTicketsDB;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class GroupTicketDetailsAction extends ActionSupport implements
		Preparable, ValidationAware {

	/**
	 * 
	 */
	//-------------------------------------------Properties------------------------------------------
	private static final long serialVersionUID = -3012260130968396988L;
	private String eid="";
	private String msgKey="";
	private String ticketId="";
	private GroupDealTicketsData grptcktData=new GroupDealTicketsData();
	private ArrayList<Entity> hours=new ArrayList<Entity>();
	private ArrayList<Entity> minutes=new ArrayList<Entity>();
	private JSONObject messageJson=new JSONObject();
	private String price="";
	private String discount="";
	//------------------------------------------Getters and Setters----------------------------------
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
	public GroupDealTicketsData getGrptcktData() {
		return grptcktData;
	}
	public void setGrptcktData(GroupDealTicketsData grptcktData) {
		this.grptcktData = grptcktData;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public JSONObject getMessageJson() {
		return messageJson;
	}
	public void setMessageJson(JSONObject messageJson) {
		this.messageJson = messageJson;
	}

	//---------------------------------------------Action Methods---------------------------------------
	public String execute(){
		populateTime();
		String userTimeZone=EventDB.getEventTimeZone(eid);
		DataPopulator.defaultStartDateDisplayForGroupTickets(grptcktData,userTimeZone);
		System.out.println("in GroupTicketDetailsAction execute method");
		return "input";
	}
	public void populateTime(){
		hours=DataPopulator.getHours();
		minutes=DataPopulator.getMinutes();
	}
	public String save(){
		String userTimeZone=EventDB.getEventTimeZone(eid);
		boolean isvalid=validateData(userTimeZone);
		if(isvalid){
			GroupDealTicketsDB.saveGroupTicket(grptcktData, eid, userTimeZone);
		}
		return "ajaxjson";
	}
	public String edit(){
		populateTime();
		String userTimeZone=EventDB.getEventTimeZone(eid);
		System.out.println("userTimeZone:"+userTimeZone);
		grptcktData=GroupDealTicketsDB.getGroupTicketData(eid, ticketId, userTimeZone);
		System.out.println("tid:"+grptcktData.getGroupticketid());
		return "input";
	}
	public boolean validateData(String userTimeZone){
		//boolean errorExist=false;
		try{
		String name=grptcktData.getTicketName().trim();
		if("".equals(name))
			messageJson.put("name", "empty");
		String description=grptcktData.getDescription();
		if("".equals(description))
			messageJson.put("desc", "empty");
		if("".equals(grptcktData.getPrice()))
				messageJson.put("price", "empty");
		else{
			try{
				double price=Double.parseDouble(grptcktData.getPrice().trim());
				if(price<1)
					messageJson.put("price", "negative");
			}
			catch (Exception e) {
				messageJson.put("price", "numeric");
			}
		}
		if("".equals(grptcktData.getDiscountvalue()))
			messageJson.put("discount", "empty");
		
		else{
			try{
				double discount=Double.parseDouble(grptcktData.getDiscountvalue().trim());
				if(discount<25)
					messageJson.put("discount", "mindisc");
				else if(discount>100)
					messageJson.put("discount","maxdisc");
			}
			catch (Exception e) {
				messageJson.put("discount", "numeric");
			}
		}
		if("".equals(grptcktData.getTriggerqty().trim()))
			messageJson.put("triggerqty", "empty");
		else{
			try{
				int triggerqty=Integer.parseInt(grptcktData.getTriggerqty().trim());
				if(triggerqty<1)
					messageJson.put("triggerqty", "negative");
			}
			catch (Exception e) {
				messageJson.put("triggerqty", "numeric");
			}
		}
		int maxqty=0,minqty=0;
		if("".equals(grptcktData.getMinqty().trim()))
			messageJson.put("minqty", "empty");
		else{
			try{
				 minqty=Integer.parseInt(grptcktData.getMinqty().trim());
				if(minqty<1)
					messageJson.put("minqty", "negative");
			}
			catch (Exception e) {
				messageJson.put("minqty", "numeric");
			}
		}
		if("".equals(grptcktData.getMaxqty().trim()))
			messageJson.put("maxqty", "empty");
		else{
			try{
				maxqty=Integer.parseInt(grptcktData.getMaxqty().trim());
				if(maxqty<1)
					messageJson.put("maxqty", "negative");
			}
			catch (Exception e) {
				messageJson.put("maxqty", "numeric");
			}
		}
		if(minqty>maxqty && minqty>1 && maxqty>1)
			messageJson.put("minmax", "greater");
		messageJson.put("posttrigtype",grptcktData.getPost_trigger_type());
		if("2".equals(grptcktData.getPost_trigger_type())){
			if("".equals(grptcktData.getUpperlimit().trim()))
				messageJson.put("upperlimit", "empty");
			else{
				try{
					int upperlimit=Integer.parseInt(grptcktData.getUpperlimit().trim());
					if(upperlimit<1)
						messageJson.put("upperlimit", "negative");
				}
				catch (Exception e) {
					messageJson.put("upperlimit", "numeric");
				}
			}
		}
		else if("3".equals(grptcktData.getPost_trigger_type())){
			if("".equals(grptcktData.getCycleslimit().trim()))
				messageJson.put("cycleslimit", "empty");
			else{
				try{
					int cycleslimit=Integer.parseInt(grptcktData.getCycleslimit().trim());
					if(cycleslimit<1)
						messageJson.put("cycleslimit", "negative");
				}
				catch (Exception e) {
					messageJson.put("cycleslimit", "numeric");
				}
			}
		}
		messageJson.put("failtype", grptcktData.getTrigger_fail_action());
		if("1".equals(grptcktData.getTrigger_fail_action())){
			if("".equals(grptcktData.getTriggerfailDicount().trim()))
				messageJson.put("faildiscount", "empty");
			
			else{
				try{
					double faildiscount=Double.parseDouble(grptcktData.getTriggerfailDicount().trim());
					if(faildiscount<0)
						messageJson.put("faildiscount", "negative");
				}
				catch (Exception e) {
					messageJson.put("faildiscount", "numeric");
				}
			}
		}
		if(grptcktData.getStdateTimeBeanObj()!=null && "0".equals(grptcktData.getSoldqty()) ){
			boolean isBefore=validategivenDate(grptcktData.getStdateTimeBeanObj());
			if(isBefore)
				messageJson.put("startdate", "isbefore");
		}
		System.out.println("json length:"+messageJson.length());
		if(messageJson.length()>2){
			messageJson.put("errorexist", "1");
			return false;
		}
		else
			messageJson.put("errorexist", "0");
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}
	public boolean validategivenDate(DateTimeBean inputDate){
		String userTimeZone=EventDB.getEventTimeZone(eid);
		int year=Integer.parseInt(inputDate.getYyPart());
		int month=Integer.parseInt(inputDate.getMonthPart())-1;
		int hourOfDay=Integer.parseInt(inputDate.getHhPart());
		String ampm=inputDate.getAmpm();
		int date=Integer.parseInt(inputDate.getDdPart());
		if(ampm.equals("PM") && hourOfDay!=12){
			hourOfDay=hourOfDay+12;
		}
		int minute=Integer.parseInt(inputDate.getMmPart());
		Calendar givenCal=Calendar.getInstance();
		givenCal.set(year, month, date, hourOfDay, minute);
		givenCal.setTimeZone(TimeZone.getTimeZone(userTimeZone));
		Calendar currentCal=Calendar.getInstance();
		currentCal.setTimeZone(TimeZone.getTimeZone(userTimeZone));
		/*SimpleDateFormat df1=new SimpleDateFormat("yyyy.MM.DD hh:mm aa zzz");
		System.out.println(givenCal);
		System.out.println(currentCal);
		System.out.println(df1.format(givenCal.getTime()));
		System.out.println(df1.format(currentCal.getTime())); */
		if(givenCal.before(currentCal))
			return true;
		else //if(givenCal.after(currentCal))
			return false;
	}
	public String calculatePrice(){
		double tprice=Double.parseDouble(price.trim());
		double tdiscount=Double.parseDouble(discount.trim());
		double dprice=(tprice*tdiscount)/100;
		String finalDiscount=CurrencyFormat.getCurrencyFormat("", dprice+"", false);
		msgKey=finalDiscount.trim();
		return "discountprice";
	}
}
