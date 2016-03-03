package com.eventbee.general;
import java.util.*;
import java.text.*;

import com.eventbee.beans.DateTime;
import com.eventbee.beans.DateTimeBean;


public class DateUtil{
	public static final Format fm=new SimpleDateFormat("MMMM dd, yyyy");
	
	public static String getFormatedDate(Date d,String format,String def){
		SimpleDateFormat SDF=null;
		if(d !=null && format !=null){
			try{
				SDF=new SimpleDateFormat(format);
				return SDF.format(d);
			}catch(Exception exp){
				return def;
			}
				
		}else{
			return def;
		}
	}
	
	public static String getCurrDBFormatDate(){
        java.util.Date today=new java.util.Date();
        Format fm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String timenow=fm.format(today);
        return timenow;
    }
	
	public static String getDateFormat(String yyyymmdd){
		String[] datearray=yyyymmdd.split("-");
		Date dateConverted = new GregorianCalendar(Integer.parseInt(datearray[0]),Integer.parseInt(datearray[1])-1,Integer.parseInt(datearray[2])).getTime();
		return fm.format(dateConverted);
	}
	
	public static String getDateTimeFormat(String yyyymmdd,String _24hrs){
			String[] datearray=yyyymmdd.split("-");
			Date dateConverted = new GregorianCalendar(Integer.parseInt(datearray[0]),Integer.parseInt(datearray[1])-1,Integer.parseInt(datearray[2])).getTime();
			return fm.format(dateConverted)+" "+DateTime.getTimeAM(_24hrs);
	}
	
	public static String getUserZoneDateTimeFormat(String yyyymmdd, String _24hrs, String userTimeZone){
		String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
		boolean statusdc=dc.convertDateTime(yyyymmdd,_24hrs,userTimeZone);
		DateTimeBean dateobj=dc.getDateTimeBeanObj();
		Date dateConverted = new GregorianCalendar(Integer.parseInt(dateobj.getYyPart()),Integer.parseInt(dateobj.getMonthPart())-1,Integer.parseInt(dateobj.getDdPart())).getTime();
		return fm.format(dateConverted)+" "+dateobj.getHhPart()+":"+dateobj.getMmPart()+" "+dateobj.getAmpm();
	}
	
	public static void sortCustomDate(ArrayList<String> datesList, final String dateFormat, boolean isReverseOrder){
		//DateFormat f = new SimpleDateFormat("EEE, MMM dd, yyyy hh:mm a");
		Collections.sort(datesList, new Comparator<String>() {
			DateFormat f = new SimpleDateFormat(dateFormat);
	        @Override
	        public int compare(String o1, String o2) {
	            try {
	                return f.parse(o1).compareTo(f.parse(o2));
	            } catch (ParseException e) {
	            	System.out.println("Exception in DateUtil compareAlphaDate ERROR:: "+e.getMessage());
	                throw new IllegalArgumentException(e);
	            }
	        }
	    });
		
		if(isReverseOrder) Collections.reverse(datesList);
	}
	
	public static void main(String args[]){
		//System.out.println(""+getDateTimeFormat("2015-04-25","18:00"));
		//System.out.println(""+getUserZoneDateTimeFormat("2015-04-15","02:20","Asia/Calcutta"));
	}
}
