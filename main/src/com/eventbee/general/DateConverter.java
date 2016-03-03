package com.eventbee.general;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import com.eventbee.beans.DateTimeBean;

public class DateConverter {
private String toTimeZone="";
private String fromTimeZone="";
private DateTimeBean dateTimeBeanObj;
private String datePart="";
private String timePart="";
public DateConverter(String ftz,String ttz){
	this.fromTimeZone=ftz;
	this.toTimeZone=ttz;
}
public String getDatePart(){
	return datePart;
}
public String getTimePart(){
	return timePart;
}
public void setDatePart(String datePart) {
	this.datePart = datePart;
}
public void setTimePart(String timePart) {
	this.timePart = timePart;
}
public DateTimeBean getDateTimeBeanObj() {
	return dateTimeBeanObj;
}
public void setDateTimeBeanObj(DateTimeBean dateTimeBeanObj) {
	this.dateTimeBeanObj = dateTimeBeanObj;
}
public boolean convertDateTime(DateTimeBean dtb){
	boolean status=true;
	String month=dtb.getMonthPart();
	String day=dtb.getDdPart();
	String year=dtb.getYyPart();
	//this.datePart=dateConverted;
	String hh=dtb.getHhPart();
	String mm=dtb.getMmPart();
	String ampm=dtb.getAmpm();
	hh=getHH24(hh,ampm);
	TimeZone T1=new SimpleTimeZone(TimeZone.getTimeZone(fromTimeZone).getRawOffset(), fromTimeZone);
	Calendar calendarObj = Calendar.getInstance(T1);
	calendarObj.set(Integer.parseInt(year),Integer.parseInt(month)-1,
	  						Integer.parseInt(day),
	  						Integer.parseInt(hh),
	  						Integer.parseInt(mm));
	String[] dateTime=getTimeZoneConverted(calendarObj,toTimeZone);
	// System.out.println("Dates after converting"+dateTime[0]+"d"+dateTime[1]);
	this.datePart=dateTime[0];
	this.timePart=dateTime[1];
	return status; 
}
public boolean convertDateTime(String datePart,String timePart,String userTimeZone){
	boolean status=true;
	try{
	String month=datePart.substring(5,7);
	String dd=datePart.substring(8,10);
	String yy=datePart.substring(0,4);
	String hh=timePart.substring(0,2);
	String mm=timePart.substring(3,5);
	dateTimeBeanObj=new DateTimeBean();
	fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
	TimeZone T1=new SimpleTimeZone(TimeZone.getTimeZone(fromTimeZone).getRawOffset(), fromTimeZone);
	Calendar calendarObj = Calendar.getInstance(T1);
	calendarObj.set(Integer.parseInt(yy),Integer.parseInt(month)-1,
	  						Integer.parseInt(dd),
	  						Integer.parseInt(hh),
	  						Integer.parseInt(mm));
	String[] dateTime=getTimeZoneConverted(calendarObj,userTimeZone);
	String tempDate=dateTime[0];
	String tempTime=dateTime[1];
	month=tempDate.substring(5,7);
	dd=tempDate.substring(8,10);
	yy=tempDate.substring(0,4);
	hh=tempTime.substring(0,2);
	mm=tempTime.substring(3,5);
	dateTimeBeanObj.setDdPart(dd);
	dateTimeBeanObj.setMonthPart(month);
	dateTimeBeanObj.setYyPart(yy);
	String AMPM=DateConverter.getAMPM(hh);
	hh=DateConverter.getHour12(hh);
	dateTimeBeanObj.setHhPart(hh);
	dateTimeBeanObj.setMmPart(mm);
	dateTimeBeanObj.setAmpm(AMPM);
	}
	catch(Exception e){
		System.out.println("Exception"+e);
	}
	return status;
}

public String[] getTimeZoneConverted(Calendar cal,String timezone)
{
Date date = cal.getTime();
String formatPattern = "yyyy-MM-dd','HH:mm";
SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
TimeZone T2=new SimpleTimeZone(TimeZone.getTimeZone(timezone).getRawOffset(),timezone);
sdf.setTimeZone(T2);
String d=sdf.format(date);
String[] stimes=GenUtil.strToArrayStr(d,",");
return stimes;
}

public String[] getTimeZoneConverted(Calendar cal,String timezone,String formatPattern)
{
Date date = cal.getTime();
SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
TimeZone T2=new SimpleTimeZone(TimeZone.getTimeZone(timezone).getRawOffset(),timezone);
sdf.setTimeZone(T2);
String d=sdf.format(date);
String[] stimes=GenUtil.strToArrayStr(d,",");
return stimes;
}
public static String getHH24(String m_Hour12,String m_AMPM){

	String th12="12".equals(m_Hour12)?"0":m_Hour12;
	int factor="AM".equals(m_AMPM)?0:1;
	int th24=0;
	try{
		th24=Integer.parseInt(th12)+factor*12;
	}catch(Exception e){}
	if(th24<10)
		return "0"+th24;
	else
		return ""+th24;
}
public static String getAMPM(String hour){
	String AMPM="AM";
		if(hour!=null){
			try{
				int hr=Integer.parseInt(hour);
				if(hr>=12)AMPM="PM";
			}catch(Exception exp){
			}
		}
		
		
		return AMPM;
	}
public static String getHour12(String hour){
	String hr1="01";
	if(hour!=null){
		try{
			int hr=Integer.parseInt(hour);
			if(hr==0)hr=12;
			if(hr>12)hr=hr-12;
			hr1=""+hr;
		}
		catch(Exception exp){
		}
	}
	
	if(hr1.length()<2)hr1="0"+hr1;
	return hr1;
}

public static String getTimeAM(String starttime){

    String hour=starttime.substring(0,2);
    String min=starttime.substring(3,starttime.length());
    String shour="",smin="";

    int i=Integer.parseInt(hour);
    int j=Integer.parseInt(min);
    shour=""+i;
    smin=""+j;
    if(j<10) smin="0"+j;
    if(i >=12){
        if(i!=12){
            i=(i-12);
            shour=""+i;
            if(i<10) shour="0"+i;
        }
            starttime=shour+":"+smin+" PM";

    }else {
        if(i<10) shour="0"+i;
        starttime=shour+":"+smin+" AM";
    }
    return starttime;
}


}
