package com.eventbee.general;
import java.util.*;
import java.text.*;
public class EventbeeDates  {

public static final String[] months=new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","sep","oct","Nov","Dec"};
public static final String[] hours=new String[]{"00:00 AM","01:00 AM","02:00 AM","03:00 AM","04:00 AM","05:00 AM","06:00 AM","07:00 AM","08:00 AM","09:00 AM","10:00 AM","11:00 AM",
			"12:00 NOON","01:00 PM","02:00 PM","03:00 PM","04:00 PM","05:00 PM","06:00 PM","07:00 PM","08:00 PM","09:00 PM","10:00 PM","11:00 PM"
			};
public static final String[] category=new String[] { "BUSINESS", "ENTERTAINMENT", "PERSONAL", "SPORTS" };
public static final String[] evtCatvalues  =new String[] { "business", "entertainment", "personal", "sports" };
public static final String[] state=new String[] { "AA","AK","AL","AP","AR","AS","AZ","CA","CO","CT","DC","DE","FL","FM","GA","GU","CO","IA","ID","IL","IN","KS","KY" };
public static final String[] country=new String[] {"USA","UK","ABW","AFG","AGO","FRA","MMR","VAT" };
public static final String[] days={"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
               "13","14","15","16","17","18","19","20", "21", "22", "23", "24", "25", "26", "27",
               "28","29","30","31" };
public static final String[] monthVals={"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
//String[] hours={ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
//                 "13","14","15","16","17","18","19","20", "21", "22", "23", "24" };
public static final String[] minutes= { "00", "15", "30", "45", "60"};


public static String getDayHtml(int daytoselect,String nameofsel){
	StringBuffer sb=new StringBuffer();
	
	sb.append("<select name='"+nameofsel+"' >");
	for(int i=1;i<=31;i++){
	 if(daytoselect==i){
	  sb.append("<option value='"+i+"' selected='selected'>"+i+"</option>");
	 }else{
		  sb.append("<option value='"+i+"'>"+i+"</option>");
	 }
	}
	
	sb.append("</select>");
	
	return sb.toString();

}



public static String getMonthHtml(int monthtoselect,String nameofsel){
	StringBuffer sb=new StringBuffer();
	
	sb.append("<select name='"+nameofsel+"' >");
	
	
	
	for(int i=0;i<months.length;i++){
	
	 if(monthtoselect==i){
	  sb.append("<option value='"+i+"' selected='selected'>"+months[i]+"</option>");
	 }else{
		  sb.append("<option value='"+i+"'>"+months[i]+"</option>");
	 }
	}
	
	sb.append("</select>");
	
	
	return sb.toString();

}



public static String getYearHtml(int startyear,int noofyears,String nameofsel){
	StringBuffer sb=new StringBuffer();
	
	sb.append("<select name='"+nameofsel+"' >");
	
	int endyear=noofyears+startyear;
	if(endyear>startyear){
		for(int i=startyear;i<=endyear;i++){
		
		 if(startyear==i){
		  sb.append("<option value='"+i+"' selected='selected'>"+i+"</option>");
		 }else{
			  sb.append("<option value='"+i+"'>"+i+"</option>");
		 }
		}
	}else{
		for(int i=startyear;i>=endyear;i--){
		
		 if(startyear==i){
		  sb.append("<option value='"+i+"' selected='selected'>"+i+"</option>");
		 }else{
			  sb.append("<option value='"+i+"'>"+i+"</option>");
		 }
		}
	}
	sb.append("</select>");
	
	
	return sb.toString();

}

public static String getYearHtml(int startyear,int noofyears,int yeartosel,String nameofsel){
	StringBuffer sb=new StringBuffer();
	
	sb.append("<select name='"+nameofsel+"' >");
	
	int endyear=noofyears+startyear;
	if(endyear>startyear){
		for(int i=startyear;i<=endyear;i++){
		
		 if(yeartosel==i){
		  sb.append("<option value='"+i+"' selected='selected'>"+i+"</option>");
		 }else{
			  sb.append("<option value='"+i+"'>"+i+"</option>");
		 }
		}
	}else{
		for(int i=startyear;i>=endyear;i--){
		
		 if(startyear==i){
		  sb.append("<option value='"+i+"' selected='selected'>"+i+"</option>");
		 }else{
			  sb.append("<option value='"+i+"'>"+i+"</option>");
		 }
		}
	}
	sb.append("</select>");
	
	
	return sb.toString();

}

public static String getHoursHtml(int hourtoselect,String nameofsel){
	StringBuffer sb=new StringBuffer();
	
	sb.append("<select name='"+nameofsel+"' >");
	
	
	
	for(int i=0;i<hours.length;i++){
	
	 if(hourtoselect==i){
	  sb.append("<option value='"+i+"' selected='selected'>"+hours[i]+"</option>");
	 }else{
		  sb.append("<option value='"+i+"'>"+hours[i]+"</option>");
	 }
	}
	
	sb.append("</select>");
	
	return sb.toString();

}

public static String getYear(int startyear,int noofyears,int yeartosel,String nameofsel){
	StringBuffer sb=new StringBuffer();
	sb.append("<select name='"+nameofsel+"' >");
	int endyear=noofyears+startyear;
	if (endyear > startyear){
		for(int i=startyear;i<=endyear;i++){
		 if(yeartosel==i){
		  sb.append("<option value='"+i+"' selected='selected'>"+i+"</option>");
		 }else{
			  sb.append("<option value='"+i+"'>"+i+"</option>");
		 }
		}
	}else{
		for(int i=startyear;i>=endyear;i--){
		
		 if(startyear==i){
		  sb.append("<option value='"+i+"' selected='selected'>"+i+"</option>");
		 }else{
			  sb.append("<option value='"+i+"'>"+i+"</option>");
		 }
		}
	}
	sb.append("</select>");
	return sb.toString();

}

public static String getMonth(String nameofsel,String monthtoselect){
	StringBuffer sb=new StringBuffer();
	sb.append("<select name='"+nameofsel+"' >");
	for(int i=0;i<months.length;i++) {
	 if(monthVals[i].equals(monthtoselect)) {
	       sb.append("<option value='"+monthVals[i]+"' selected='selected'>"+months[i]+ "</option>");
	 }else {
		  sb.append("<option value='"+monthVals[i]+"'>" + months[i] + "</option>");
	       }
	}
	
	sb.append("</select>");
	return sb.toString();

}

public static String getDay(String nameofsel,String daytoselect){
	StringBuffer sb=new StringBuffer();
	sb.append("<select name='"+nameofsel+"' >");
	for(int i=0;i<days.length;i++) {
	 if(days[i].equals(daytoselect)) {
	       sb.append("<option value='"+days[i]+"' selected='selected'>"+days[i]+ "</option>");
	 }else {
		  sb.append("<option value='"+days[i]+"'>" + days[i] + "</option>");
	       }
	}
	
	sb.append("</select>");
	return sb.toString();

}

public static String getHour(String selectname,String defhour){
	StringBuffer sb=new StringBuffer();
	sb.append("<select name='"+ selectname +"' >");
	
	for(int i=0;i<hours.length;i++){
	
	 if(hours[i].equals(defhour)){
	 sb.append("<option value='"+ hours[i] +"' selected='selected'>"+ hours[i] +
"</option>");
	 }else{ 
	       sb.append("<option value='"+ hours[i]+"'>"+ hours[i] +"</option>");
	      }
	}
	
	sb.append("</select>");
	return sb.toString();

}

public static String getMinute(String selectname,String defmin){
	StringBuffer sb=new StringBuffer();
	sb.append("<select name='"+ selectname +"' >");
	
	for(int i=0;i<minutes.length;i++){
	
	 if(minutes[i].equals(defmin)){
	 sb.append("<option value='"+ minutes[i] +"' selected='selected'>"+ minutes[i] +
"</option>");
	 }else{ 
	       sb.append("<option value='"+ minutes[i]+"'>"+ minutes[i] +"</option>");
	      }
	}
	
	sb.append("</select>");
	return sb.toString();

}

public static String getCategory(String selectname, String defcate){
	StringBuffer sb=new StringBuffer();
	sb.append("<select name='"+ selectname +"' >");
	for(int i=0;i<category.length;i++){
	if (evtCatvalues[i].equals(defcate)){
	  sb.append("<option value='"+ evtCatvalues[i] +"' selected='selected'>"+ category[i] +
"</option>");
	 }else{ 
	  sb.append("<option value='"+evtCatvalues[i]+"'>"+ category[i] + "</option>");
	 }
	}
	
	sb.append("</select>");
	return sb.toString();

}

public static String getState(String selectname,String defstate){
	StringBuffer sb=new StringBuffer();
	sb.append("<select name='"+ selectname +"' >");
	for(int i=0;i<state.length;i++){
	
	 if (state[i].equals(defstate)){
	  sb.append("<option value='"+ state[i] +"' selected='selected'>"+ state[i] +
"</option>");
	 }else{ 
	        sb.append("<option value='"+state[i]+"'>"+ state[i] + "</option>");
	      }
	}
	
	sb.append("</select>");
	return sb.toString();

}

public static String getCountry(String selectname,String defcountry){
	StringBuffer sb=new StringBuffer();
	sb.append("<select name='"+ selectname +"' >");
	
	for(int i=0;i<country.length;i++){
	
	 if(country[i].equals(defcountry)){
	 sb.append("<option value='"+ country[i] +"' selected='selected'>"+ country[i] +
"</option>");
	 }else{ 
	       sb.append("<option value='"+ country[i]+"'>"+ country[i] +"</option>");
	      }
	}
	
	sb.append("</select>");
	return sb.toString();

}

   public static String getNextPayDate(int termmonths){
		String nextpaydate="";
		java.util.Date currdate=new java.util.Date();
		return getNextPayDate(termmonths, currdate);
    }
    public static String getNextPayDate(int termmonths,java.util.Date currdate){
		String nextpaydate="";
		currdate.setMonth(currdate.getMonth()+termmonths);
		int days=currdate.getDate();
		if(days>28){
			currdate.setDate(28);
		}
		try{
			SimpleDateFormat sm=new SimpleDateFormat("MM-dd-yyyy");
			nextpaydate=sm.format(currdate);
		}catch(Exception e){
			System.out.println("Error in Eventbeedates getNextPayDate()"+e.getMessage());
		}
		return nextpaydate;
    }


public static java.util.Date getNextPayUtilDate(int termmonths){
		
		java.util.Date currdate=new java.util.Date();
		currdate.setMonth(currdate.getMonth()+termmonths);
		int days=currdate.getDate();
		if(days>28){
			currdate.setDate(28);
		}
			
		return currdate;
    }



public static void main(String[] arg){
	String s=EventbeeDates.getHoursHtml(3,"hour");
	System.out.println(s);





}


}

