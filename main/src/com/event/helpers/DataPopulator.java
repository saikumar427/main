package com.event.helpers;

import java.util.ArrayList;
import java.util.Calendar;

import com.event.beans.AddEventData;
import com.event.beans.ticketing.TicketData;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.beans.Entity;


public class DataPopulator {
	public static ArrayList populateCategoryList(){
		ArrayList categorytype=new ArrayList();
		categorytype.add("Arts");
		categorytype.add("Associations");
		categorytype.add("Books");
		categorytype.add("Business");
		categorytype.add("Career");
		categorytype.add("Causes");
		categorytype.add("Community");
		categorytype.add("Corporate");
		categorytype.add("Education");
		categorytype.add("Entertainment");
		categorytype.add("Entrepreneur");
		categorytype.add("Games");
		categorytype.add("Family");
		categorytype.add("Festivals");
		categorytype.add("Food");
		categorytype.add("Health");
		categorytype.add("Meeting");
		categorytype.add("Movies");
		categorytype.add("Music");
		categorytype.add("Non-Profit");
		categorytype.add("Party");
		categorytype.add("Politics");
		categorytype.add("Religion");
		categorytype.add("School");
		categorytype.add("Social");
		categorytype.add("Sports");
		categorytype.add("Technology");
		categorytype.add("Travel");
		categorytype.add("Trips");
		categorytype.add("Other");
		return categorytype;
	}
	public static ArrayList populateTimeZones(){
		ArrayList timezones=new ArrayList();
		
		timezones.add(new Entity("Europe/London","(GMT 00:00) Greenwich Mean Time"));
		timezones.add(new Entity("SystemV/HST10 Hawaii","(GMT-10:00) Hawaii"));
		timezones.add(new Entity("SystemV/YST9YDT Alaska (DST)","(GMT-09:00) Alaska (DST)"));
		timezones.add(new Entity("SystemV/YST9 Alaska","(GMT-09:00) Alaska"));
		timezones.add(new Entity("SystemV/PST8PDT Pacific Time (DST)","(GMT-08:00) Pacific Time (DST)"));
		timezones.add(new Entity("SystemV/PST8 Pacific Time","(GMT-08:00) Pacific Time"));
		timezones.add(new Entity("SystemV/MST7 Arizona","(GMT-07:00) Arizona"));
		timezones.add(new Entity("SystemV/MST7MDT La Paz (DST)","(GMT-07:00) La Paz (DST)"));
		timezones.add(new Entity("SystemV/MST7 La Paz","(GMT-07:00) La Paz"));
		timezones.add(new Entity("SystemV/MST7MDT Mountain Time (DST)","(GMT-07:00) Mountain Time (DST)"));
		timezones.add(new Entity("SystemV/MST7 Mountain Time","(GMT-07:00) Mountain Time"));
		timezones.add(new Entity("SystemV/CST6 Central America","(GMT-06:00) Central America"));
		timezones.add(new Entity("SystemV/CST6CDT Central Time (DST)","(GMT-06:00) Central Time (DST)"));
		timezones.add(new Entity("SystemV/CST6 Central Time","(GMT-06:00) Central Time"));
		timezones.add(new Entity("SystemV/CST6CDT Mexico City, Monterrey(DST)","(GMT-06:00) Mexico City, Monterrey(DST)"));
		timezones.add(new Entity("SystemV/CST6 Mexico City, Monterrey","(GMT-06:00) Mexico City, Monterrey"));
		timezones.add(new Entity("SystemV/EST5EDT Eastern Time (DST)","(GMT-05:00) Eastern Time (DST)"));
		timezones.add(new Entity("SystemV/EST5 Eastern Time","(GMT-05:00) Eastern Time"));
		timezones.add(new Entity("Etc/GMT+12","(GMT-12:00) Eniwetok, Kwajalein"));
		timezones.add(new Entity("Pacific/Apia","(GMT-11:00) Midway Island; Samoa"));
		timezones.add(new Entity("America/Regina","(GMT-06:00) Saskatchewan"));
		timezones.add(new Entity("America/Bogota","(GMT-05:00) Bogota, Lima, Quito"));
		timezones.add(new Entity("Etc/GMT+5","GMT-05:00) Indiana (East)"));
		timezones.add(new Entity("America/Halifax","(GMT-04:00) Atlantic Time (Canada)"));
		timezones.add(new Entity("America/Caracas","(GMT-04:30) Caracas"));
		timezones.add(new Entity("America/Santiago","(GMT-04:00) Santiago"));
		timezones.add(new Entity("America/St_Johns","(GMT-03:30) Newfoundland"));
		timezones.add(new Entity("America/Sao_Paulo","(GMT-03:00) Brasilia"));
		timezones.add(new Entity("America/Buenos_Aires","(GMT-03:00) Buenos Aires, Georgeto..."));
		timezones.add(new Entity("America/Godthab","(GMT-03:00) Greenland"));
		timezones.add(new Entity("Atlantic/South_Georgia","(GMT-02:00) Mid-Atlantic"));
		timezones.add(new Entity("Atlantic/Azores","(GMT-01:00) Azores"));
		timezones.add(new Entity("Atlantic/Cape_Verde","(GMT-01:00) Cape Verde Is."));
		timezones.add(new Entity("Africa/Casablanca","(GMT 00:00) Casablanca, Monrovia"));
		timezones.add(new Entity("Europe/Berlin","(GMT+01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna"));
		timezones.add(new Entity("Europe/Budapest","(GMT+01:00) Belgrade, Bratislava, Budapest, Ljubljana, Prague"));
		timezones.add(new Entity("Europe/Paris","(GMT+01:00) Brussels, Copenhagen, Madrid, Paris"));
		timezones.add(new Entity("Europe/Warsaw","(GMT+01:00) Sarajevo, Skopje, Warsaw, Zagreb"));
		timezones.add(new Entity("Africa/Lagos","(GMT+01:00) West Central Africa"));
		timezones.add(new Entity("Europe/Istanbul","(GMT+02:00) Athens, Bucharest, Istanbul, Mins..."));
		timezones.add(new Entity("Africa/Cairo","(GMT+02:00) Cairo"));
		timezones.add(new Entity("Africa/Johannesburg","(GMT+02:00) Harare, Prestoria"));
		timezones.add(new Entity("Europe/Kiev","(GMT+02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius"));
		timezones.add(new Entity("Asia/Jerusalem","(GMT+02:00) Jerusalem"));
		timezones.add(new Entity("Asia/Baghdad","(GMT+03:00) Baghdad"));
		timezones.add(new Entity("Asia/Riyadh","(GMT+03:00) Kuwait, Riyadh"));
		timezones.add(new Entity("Europe/Moscow","(GMT+03:00) Moscow, St. Petersburg, Volgograd"));
		timezones.add(new Entity("Africa/Nairobi","(GMT+03:00) Nairobi"));
		timezones.add(new Entity("Asia/Tehran","(GMT+03:30) Tehran"));
		timezones.add(new Entity("Asia/Dubai","(GMT+04:00) Abu Dhabi, Muscat"));
		timezones.add(new Entity("Baku","(GMT+04:00) Baku, Tbilisi, Yerevan"));
		timezones.add(new Entity("Asia/Kabul","(GMT+04:30) Kabul"));
		timezones.add(new Entity("Asia/Yekaterinburg","(GMT+05:00) Ekaterinburg"));
		timezones.add(new Entity("Asia/Karachi","(GMT+05:00) Islamabad, Karachi, Tashkent"));
		timezones.add(new Entity("Asia/Calcutta","(GMT+05:30) India Standard Time"));
		timezones.add(new Entity("Asia/Katmandu","(GMT+05:45) Kathmandu"));
		timezones.add(new Entity("Asia/Novosibirsk","(GMT+06:00) Almaty, Novosibirsk"));
		timezones.add(new Entity("Asia/Dhaka","(GMT+06:00) Astana, Dhaka"));
		timezones.add(new Entity("Asia/Colombo","(GMT+06:00) Sri Jayawardenepura"));
		timezones.add(new Entity("Asia/Rangoon","(GMT+06:30) Rangoon"));
		timezones.add(new Entity("Asia/Bangkok","(GMT+07:00) Bangkok, Hanoi, Jakarta"));
		timezones.add(new Entity("Asia/Krasnoyarsk","(GMT+07:00) Krasnoyarsk"));
		timezones.add(new Entity("Asia/Shanghai","(GMT+08:00) Beijing, Chongqing, Hong Kong, Urumqi"));
		timezones.add(new Entity("Asia/Irkutsk","(GMT+08:00) Irkutsk, Ulaan Bataar"));
		timezones.add(new Entity("Asia/Singapore","(GMT+08:00) Kuala Lumpur, Singapore"));
		timezones.add(new Entity("Australia/Perth","(GMT+08:00) Perth"));
		timezones.add(new Entity("Asia/Taipei","(GMT+08:00) Taipei"));
		timezones.add(new Entity("Asia/Tokyo","(GMT+09:00) Osaka, Sapporo, Tokyo"));
		timezones.add(new Entity("Asia/Seoul","(GMT+09:00) Seoul"));
		timezones.add(new Entity("Asia/Yakutsk","(GMT+09:00) Yakutsk"));
		timezones.add(new Entity("Australia/Adelaide","(GMT+09:30) Adelaide"));
		timezones.add(new Entity("Australia/Darwin","(GMT+09:30) Darwin"));
		timezones.add(new Entity("Australia/Brisbane","(GMT+10:00) Brisbane"));
		timezones.add(new Entity("Australia/Sydney","(GMT+10:00) Canberra, Melbourne, Sydney"));
		timezones.add(new Entity("Pacific/Port_Moresby","(GMT+10:00) Guam, Port Moresby"));
		timezones.add(new Entity("Australia/Hobart","(GMT+10:00) Hobart"));
		timezones.add(new Entity("Asia/Vladivostok","(GMT+10:00) Vladivostok"));
		timezones.add(new Entity("Pacific/Guadalcanal","(GMT+11:00) Magadan, Solomon Is., New Caledonia"));
		timezones.add(new Entity("Pacific/Auckland","(GMT+12:00) Auckland, Wellington"));
		timezones.add(new Entity("Pacific/Fiji","(GMT+12:00) Fiji, Kamchatka, Marshall Is."));
		timezones.add(new Entity("Pacific/Tongatapu","(GMT+13:00) Nuku'alofa"));
		return timezones;
	}
	public static ArrayList populateListingPrivacyTypes(){
		ArrayList listingprivacy=new ArrayList();
		listingprivacy.add(new Entity("PBL","Public"));
		listingprivacy.add(new Entity("PVT","Private"));
		return listingprivacy;
	}
	public static ArrayList populateListingTypes(){
		ArrayList listingtype=new ArrayList();
		listingtype.add(new Entity("event","Event"));
		listingtype.add(new Entity("class","Class"));
		return listingtype;
	}
	public static ArrayList populateDescriptionTypes(){
		ArrayList descriptiontype=new ArrayList();
		descriptiontype.add(new Entity("text","Text"));
		descriptiontype.add(new Entity("html","HTML"));
		descriptiontype.add(new Entity("wysiwyg","WYSIWYG Editor"));
		return descriptiontype;
	}
	
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
	static String[] monthvals=new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
	static String[] hourvals=new String[]{"00", "01","02","03","04","05","06","07","08","09","10","11","12"};

	public static void defaultDateDisplay(AddEventData addEventData){
		
		Calendar currentdate=Calendar.getInstance();
		currentdate.add(Calendar.DATE, 1);
		String currentmonth=monthvals[currentdate.get(Calendar.MONTH)];
		int date = currentdate.get(Calendar.DATE);
		String presentdate=(date<10)?("0"+date):(date+"");
		String dateformate=currentdate.get(Calendar.YEAR)+"-"+currentmonth+"-"+presentdate;
		System.out.println(dateformate);
		//String hrofday=getHours().get(currentdate.get(Calendar.HOUR)).toString();
		
		try{
			addEventData.setStartyear(currentdate.get(Calendar.YEAR)+"");
			addEventData.setEndyear(currentdate.get(Calendar.YEAR)+"");
			addEventData.setStartmonth(currentmonth);
			addEventData.setEndmonth(currentmonth);
			addEventData.setStartday(presentdate);
			addEventData.setEndday(presentdate);
			addEventData.setDate(dateformate);
			addEventData.setStarthour("09");
			addEventData.setEndhour("10");
			addEventData.setStartminute("00");
			addEventData.setEndminute("00");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		}
public static void defaultStartDateDisplayForTickets(TicketData ticketData){
		
		Calendar currentdate=Calendar.getInstance();
		currentdate.add(Calendar.HOUR,-4);
		String currentmonth=monthvals[currentdate.get(Calendar.MONTH)];
		int date = currentdate.get(Calendar.DATE);
		int hh=currentdate.get(Calendar.HOUR);
		int mm=currentdate.get(Calendar.MINUTE);
		String presentdate=(date<10)?("0"+date):(date+"");
		String dateformate=currentdate.get(Calendar.YEAR)+"-"+currentmonth+"-"+presentdate;
		System.out.println(dateformate);
		//String hrofday=getHours().get(currentdate.get(Calendar.HOUR)).toString();
		
		try{
			DateTimeBean dtbObj=new DateTimeBean();
			dtbObj.setYyPart(currentdate.get(Calendar.YEAR)+"");
			dtbObj.setDdPart(presentdate);
			dtbObj.setMonthPart(currentmonth);
			dtbObj.setHhPart(hh+"");
			dtbObj.setMmPart(mm+"");
			ticketData.setStdateTimeBeanObj(dtbObj);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		}

public static ArrayList populateCurrencyTypes(){
	ArrayList currencylist=new ArrayList();
	currencylist.add(new Entity("USD","U.S. Dollars ($)"));
	currencylist.add(new Entity("EUR","Euros (&euro;)"));
	currencylist.add(new Entity("GBP","Pounds Sterling (&pound;)"));
	currencylist.add(new Entity("JPY","Yen (&yen;)"));
	currencylist.add(new Entity("AUD","Australian Dollars (AU$)"));
	currencylist.add(new Entity("CAD","Canadian Dollars (C$)"));
	currencylist.add(new Entity("DKK","Danish Krone (kr)"));
	currencylist.add(new Entity("HKD","Hong Kong Dollar (HK$)"));
	currencylist.add(new Entity("HUF","Hungarian Forint (Ft)"));
	currencylist.add(new Entity("NZD","New Zealand Dollar (NZ$)"));
	currencylist.add(new Entity("SGD","Singapore Dollar (S$)"));
	currencylist.add(new Entity("SEK","Swedish Krona (kr)"));
	currencylist.add(new Entity("CHF","Swiss Franc (Fr)"));
	currencylist.add(new Entity("MXN","Mexican Peso (MX$)"));
	currencylist.add(new Entity("ILS","Israeli Shekels (&#8362;)"));
	currencylist.add(new Entity("NOK","Norwegian Krone (kr)"));

	return currencylist;
}

public static ArrayList populateNTSEnable(){
	ArrayList ntsEnable=new ArrayList();
	ntsEnable.add(new Entity("Y","Yes"));
	ntsEnable.add(new Entity("N","No"));
	return ntsEnable;
}
}
