package com.eventbee.general;

import java.text.*;
import java.util.*;


public class  EventBeeDate{
	private int dayofmonth;
	private int month;
	private int year;
	private int hour;
	private int minute;
	private int second;
	
	//month index from 1
	public EventBeeDate(String dayofmonth,String month,String year,String hour,String minute,String second){
		this.dayofmonth=Integer.parseInt(dayofmonth);
		this.month=Integer.parseInt(month);
		this.year= Integer.parseInt(year);
		this.hour= Integer.parseInt( hour);
		this.minute= Integer.parseInt( minute);
		this.second=Integer.parseInt( second);
	}
	
	public EventBeeDate(String dayofmonth,String month,String year){
		this(dayofmonth,month,year,"0","0","0");
	}
	

	public int getMonth(){
		return month;
	}
	public int getYear(){
		return year;
	}
	public int getDay(){
		return dayofmonth;
	}
	public int getHour(){
		return hour;
	}
	public int getMinute(){
		return minute;
	}
	public int getSecond(){
		return second;
	}
	
	public void setHour(String hour){
		this.hour=Integer.parseInt( hour);
	}
	public void setMinute(String minute){
		this.minute=Integer.parseInt( minute);
	}
	public void setSecond(String second){
		this.second=Integer.parseInt( second);
	}


	public Date getUtilDate(){
		Date dt=null;
		Calendar cal=Calendar.getInstance();
		cal.setLenient(false);
		cal.set( year,month-1, dayofmonth, hour,minute,  second);
		try{
		 dt=cal.getTime();
		
		}catch(Exception ex){}
		return dt;
	}

	
	
	public static EventBeeDate getEventbeeDate(java.sql.Date date){
		if (date==null)return null;
		Calendar cal=Calendar.getInstance();
		cal.setTime( new Date(date.getTime()) );
		return new EventBeeDate(cal.get(Calendar.DAY_OF_MONTH)+"",cal.get(Calendar.MONTH)+1+"",cal.get(Calendar.YEAR)+"",
		cal.get(Calendar.HOUR_OF_DAY)+"",cal.get(Calendar.MINUTE)+"",cal.get(Calendar.SECOND)+"");
	}
	public static EventBeeDate getEventbeeDate(java.sql.Timestamp timestamp){
		if (timestamp==null)return null;
		
		Calendar cal=Calendar.getInstance();
		cal.setTime( new Date(timestamp.getTime()) );
		return new EventBeeDate(cal.get(Calendar.DAY_OF_MONTH)+"",cal.get(Calendar.MONTH)+1+"",cal.get(Calendar.YEAR)+"",
		cal.get(Calendar.HOUR_OF_DAY)+"",cal.get(Calendar.MINUTE)+"",cal.get(Calendar.SECOND)+"");
	}

	public static EventBeeDate getCurrentDate(){
		Calendar cal=Calendar.getInstance();
		return new EventBeeDate(cal.get(Calendar.DAY_OF_MONTH)+"",cal.get(Calendar.MONTH)+1+"",cal.get(Calendar.YEAR)+"");
	}

//(String dayofmonth,String month,String year,String hour,String minute,String second)
public String toString(){
	      return getClass().getName()
	         + "[dayofmonth=" + dayofmonth
		  + ",month=" + month
	         + ",year=" + year
		 + ",hour=" + hour
		  + ",minute=" + minute
		  + ",second=" + second
		 + "]";
}

	

	public static void main(String args[]){
		EventBeeDate ebeedate=getCurrentDate();
		java.sql.Date dt=java.sql.Date.valueOf("2003-08-20" );
		EventBeeDate ebeedatesql=getEventbeeDate(dt);

	}
	
}