package com.eventmanage.groupticketing.helpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import com.eventbee.beans.DateTimeBean;
import com.eventbee.beans.Entity;
import com.eventbee.general.DateConverter;
import com.eventbee.general.EbeeConstantsF;
import com.eventmanage.groupticketing.beans.GroupDealTicketsData;

public class DataPopulator {
	static String[] monthvals=new String[]{"01","02","03","04","05","06","07","08","08","10","11","12"};
	static String[] hourvals=new String[]{"00", "01","02","03","04","05","06","07","08","09","10","11","12"};
	public static ArrayList<Entity> getHours(){
		ArrayList<Entity> hrs=new ArrayList<Entity>();
		for( int i=0;i<13;i++){
			String val=Integer.toString(i);
			if(val.length()<2)
				val="0"+val;
			hrs.add(new Entity(val,val));
		}
		return hrs;
	}
	public static ArrayList<Entity> getMinutes(){
		ArrayList<Entity> mins=new ArrayList<Entity>();
		for( int i=0;i<60;i++){
			String val=Integer.toString(i);
			if(val.length()<2)
				val="0"+val;
			mins.add(new Entity(val,val));
		}
		return mins;
	}

	public static void defaultStartDateDisplayForGroupTickets(GroupDealTicketsData grptcktData,String timeZone){
		
		Calendar currentdate=Calendar.getInstance();
		currentdate.setTimeZone(TimeZone.getTimeZone(timeZone));
		//currentdate.add(Calendar.HOUR,-4);
		String currentmonth=monthvals[currentdate.get(Calendar.MONTH)];
		int date = currentdate.get(Calendar.DATE);
		int hh=currentdate.get(Calendar.HOUR);
		int mm=currentdate.get(Calendar.MINUTE);
		int ampm=currentdate.get(Calendar.AM_PM);
		if(ampm==1 && hh==0)
			hh=hh+12;
		String hours=""+hh;
		String minutes=""+mm;
		if(hours.length()<2)
			hours="0"+hours;
		if(minutes.length()<2)
			minutes="0"+minutes;
		String presentdate=(date<10)?("0"+date):(date+"");
		String dateformate=currentdate.get(Calendar.YEAR)+"-"+currentmonth+"-"+presentdate;
		
		
		try{
			DateTimeBean dtbObj=new DateTimeBean();
			dtbObj.setYyPart(currentdate.get(Calendar.YEAR)+"");
			dtbObj.setDdPart(presentdate);
			dtbObj.setMonthPart(currentmonth);
			dtbObj.setHhPart(hours);
			dtbObj.setMmPart(minutes);
			if(ampm==1)
				dtbObj.setAmpm("PM");
			else
				dtbObj.setAmpm("AM");
			grptcktData.setStdateTimeBeanObj(dtbObj);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		}
	}
