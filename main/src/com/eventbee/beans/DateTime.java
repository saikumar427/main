package com.eventbee.beans;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.*;
import java.text.*;
import com.eventbee.general.GenUtil;

public class DateTime{
		private String m_Year = "";
		private String m_Month = "";// index 1-12
		private String m_Day = "";
		private String m_Hour = "0";
		private String m_Hour12 = "";
		private String m_AMPM = "AM";
		private String m_Minute = "0";
		public static final String[] ZONES=new String[]{"Hawaii","Alaska","Pacific Time","Mountain Time","Central Time","Eastern Time"};
		public static final String[] ZONES_VAL=new String[]{"-600","-540","-480","-420","-360","-300"};
		public static SimpleDateFormat VCAL_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmm'00Z'");
        public static SimpleDateFormat VCAL2_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
		public static final String[] TIME_ZONE_KEYS= new String[]{
							"(GMT+05:30) India Standard Time",
							"(GMT 00:00) Greenwich Mean Time",
							"(GMT-10:00) Hawaii",
							"(GMT-09:00) Alaska (DST)",
							"(GMT-09:00) Alaska",
							"(GMT-08:00) Pacific Time (DST)",
							"(GMT-08:00) Pacific Time",
							"(GMT-07:00) Arizona",
							"(GMT-07:00) La Paz (DST)",
							"(GMT-07:00) La Paz",
							"(GMT-07:00) Mountain Time (DST)",
							"(GMT-07:00) Mountain Time",
							"(GMT-06:00) Central America",
							"(GMT-06:00) Central Time (DST)",
							"(GMT-06:00) Central Time",
							"(GMT-06:00) Mexico City, Monterrey(DST)",
							"(GMT-06:00) Mexico City, Monterrey",
							"(GMT-05:00) Eastern Time (DST)",
							"(GMT-05:00) Eastern Time",
							"(GMT-12:00) Eniwetok, Kwajalein",
							"(GMT-11:00) Midway Island; Samoa",
							"(GMT-06:00) Saskatchewan",
							"(GMT-05:00) Bogota, Lima, Quito",
							"(GMT-05:00) Indiana (East)",
							"(GMT-04:00) Atlantic Time (Canada)",
							"(GMT-04:30) Caracas",
							"(GMT-04:00) Santiago",
							"(GMT-03:30) Newfoundland",
							"(GMT-03:00) Brasilia",
							"(GMT-03:00) Buenos Aires, Georgeto...",
							"(GMT-03:00) Greenland",
							"(GMT-02:00) Mid-Atlantic",
							"(GMT-01:00) Azores",
							"(GMT-01:00) Cape Verde Is.",
							"(GMT 00:00) Casablanca, Monrovia",
							"(GMT+01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna",
							"(GMT+01:00) Belgrade, Bratislava, Budapest, Ljubljana, Prague",
							"(GMT+01:00) Brussels, Copenhagen, Madrid, Paris",
							"(GMT+01:00) Sarajevo, Skopje, Warsaw, Zagreb",
							"(GMT+01:00) West Central Africa",
							"(GMT+02:00) Athens, Bucharest, Istanbul, Mins...",
							"(GMT+02:00) Cairo",
							"(GMT+02:00) Harare, Prestoria",
							"(GMT+02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius",
							"(GMT+02:00) Jerusalem",
							"(GMT+03:00) Baghdad",
							"(GMT+03:00) Kuwait, Riyadh",
							"(GMT+03:00) Moscow, St. Petersburg, Volgograd",
							"(GMT+03:00) Nairobi",
							"(GMT+03:30) Tehran",
							"(GMT+04:00) Abu Dhabi, Muscat",
							"(GMT+04:00) Baku, Tbilisi, Yerevan",
							"(GMT+04:30) Kabul",
							"(GMT+05:00) Ekaterinburg",
							"(GMT+05:00) Islamabad, Karachi, Tashkent",
							"(GMT+05:45) Kathmandu",
							"(GMT+06:00) Almaty, Novosibirsk",
							"(GMT+06:00) Astana, Dhaka",
							"(GMT+06:00) Sri Jayawardenepura",
							"(GMT+06:30) Rangoon",
							"(GMT+07:00) Bangkok, Hanoi, Jakarta",
							"(GMT+07:00) Krasnoyarsk",
							"(GMT+08:00) Beijing, Chongqing, Hong Kong, Urumqi",
							"(GMT+08:00) Irkutsk, Ulaan Bataar",
							"(GMT+08:00) Kuala Lumpur, Singapore",
							"(GMT+08:00) Perth",
							"(GMT+08:00) Taipei",
							"(GMT+09:00) Osaka, Sapporo, Tokyo",
							"(GMT+09:00) Seoul",
							"(GMT+09:00) Yakutsk",
							"(GMT+09:30) Adelaide",
							"(GMT+09:30) Darwin",
							"(GMT+10:00) Brisbane",
							"(GMT+10:00) Canberra, Melbourne, Sydney",
							"(GMT+10:00) Guam, Port Moresby",
							"(GMT+10:00) Hobart",
							"(GMT+10:00) Vladivostok",
							"(GMT+11:00) Magadan, Solomon Is., New Caledonia",
							"(GMT+12:00) Auckland, Wellington",
							"(GMT+12:00) Fiji, Kamchatka, Marshall Is.",
							"(GMT+13:00) Nuku'alofa"
							}	;
	    public static final String[] TIME_ZONE_KEYVALS=new String[]{"Asia/Calcutta",
		    						"Europe/London",
		    						"SystemV/HST10 Hawaii",
		    						"SystemV/YST9YDT Alaska (DST)",
								"SystemV/YST9 Alaska",
								"SystemV/PST8PDT Pacific Time (DST)",
								"SystemV/PST8 Pacific Time",
								"SystemV/MST7 Arizona",
								"SystemV/MST7MDT La Paz (DST)",
								"SystemV/MST7 La Paz",
								"SystemV/MST7MDT Mountain Time (DST)",
								"SystemV/MST7 Mountain Time",
								"SystemV/CST6 Central America",
								"SystemV/CST6CDT Central Time (DST)",
								"SystemV/CST6 Central Time",
								"SystemV/CST6CDT Mexico City, Monterrey(DST)",
								"SystemV/CST6 Mexico City, Monterrey",
								"SystemV/EST5EDT Eastern Time (DST)",
								"SystemV/EST5 Eastern Time",
								"Etc/GMT+12",
								"Pacific/Apia",
								"America/Regina",
								"America/Bogota",
								"Etc/GMT+5",
								"America/Halifax",
								"America/Caracas",
								"America/Santiago",
								"America/St_Johns",
								"America/Sao_Paulo",
								"America/Buenos_Aires",
								"America/Godthab",
								"Atlantic/South_Georgia",
								"Atlantic/Azores",
								"Atlantic/Cape_Verde",
								"Africa/Casablanca",
								"Europe/Berlin",
								"Europe/Budapest",
								"Europe/Paris",
								"Europe/Warsaw",
								"Africa/Lagos",
								"Europe/Istanbul",
								"Africa/Cairo",
								"Africa/Johannesburg",
								"Europe/Kiev",
								"Asia/Jerusalem",
								"Asia/Baghdad",
								"Asia/Riyadh",
								"Europe/Moscow",
								"Africa/Nairobi",
								"Asia/Tehran",
								"Asia/Dubai",
								"Baku",
								"Asia/Kabul",
								"Asia/Yekaterinburg",
								"Asia/Karachi",
								"Asia/Katmandu",
								"Asia/Novosibirsk",
								"Asia/Dhaka",
								"Asia/Colombo",
								"Asia/Rangoon",
								"Asia/Bangkok",
								"Asia/Krasnoyarsk",
								"Asia/Shanghai",
								"Asia/Irkutsk",
								"Asia/Singapore",
								"Australia/Perth",
								"Asia/Taipei",
								"Asia/Tokyo",
								"Asia/Seoul",
								"Asia/Yakutsk",
								"Australia/Adelaide",
								"Australia/Darwin",
								"Australia/Brisbane",
								"Australia/Sydney",
								"Pacific/Port_Moresby",
								"Australia/Hobart",
								"Asia/Vladivostok",
								"Pacific/Guadalcanal",
								"Pacific/Auckland",
								"Pacific/Fiji",
								"Pacific/Tongatapu"
								};


public static final String[] TIME_ZONE_VALS= new String[]{     "Asia/Calcutta",
								"Europe/London",
								"SystemV/HST10",
								"SystemV/YST9YDT",
								"SystemV/YST9",
								"SystemV/PST8PDT",
								"SystemV/PST8",
								"SystemV/MST7",
								"SystemV/MST7MDT",
								"SystemV/MST7",
								"SystemV/MST7MDT",
								"SystemV/MST7",
								"SystemV/CST6",
								"SystemV/CST6CDT",
								"SystemV/CST6",
								"SystemV/CST6CDT",
								"SystemV/CST6",
								"SystemV/EST5EDT",
								"SystemV/EST5",
								"Etc/GMT+12",
								"Pacific/Apia",
								"America/Regina",
								"America/Bogota",
								"Etc/GMT+5",
								"America/Halifax",
								"America/Caracas",
								"America/Santiago",
								"America/St_Johns",
								"America/Sao_Paulo",
								"America/Buenos_Aires",
								"America/Godthab",
								"Atlantic/South_Georgia",
								"Atlantic/Azores",
								"Atlantic/Cape_Verde",
								"Africa/Casablanca",
								"Europe/Berlin",
								"Europe/Budapest",
								"Europe/Paris",
								"Europe/Warsaw",
								"Africa/Lagos",
								"Europe/Istanbul",
								"Africa/Cairo",
								"Africa/Johannesburg",
								"Europe/Kiev",
								"Asia/Jerusalem",
								"Asia/Baghdad",
								"Asia/Riyadh",
								"Europe/Moscow",
								"Africa/Nairobi",
								"Asia/Tehran",
								"Asia/Dubai",
								"Baku",
								"Asia/Kabul",
								"Asia/Yekaterinburg",
								"Asia/Karachi",
								"Asia/Katmandu",
								"Asia/Novosibirsk",
								"Asia/Dhaka",
								"Asia/Colombo",
								"Asia/Rangoon",
								"Asia/Bangkok",
								"Asia/Krasnoyarsk",
								"Asia/Shanghai",
								"Asia/Irkutsk",
								"Asia/Singapore",
								"Australia/Perth",
								"Asia/Taipei",
								"Asia/Tokyo",
								"Asia/Seoul",
								"Asia/Yakutsk",
								"Australia/Adelaide",
								"Australia/Darwin",
								"Australia/Brisbane",
								"Australia/Sydney",
								"Pacific/Port_Moresby",
								"Australia/Hobart",
								"Asia/Vladivostok",
								"Pacific/Guadalcanal",
								"Pacific/Auckland",
								"Pacific/Fiji",
								"Pacific/Tongatapu"

								};
		private String offSet;// in min
               public DateTime(){
                }

		public DateTime(String m_Year,String m_Month,String m_Day,String m_Hour,String m_Minute, String offSet){
			this.m_Year=m_Year;
			this.m_Month=m_Month;
			this.m_Day=m_Day;
			this.m_Hour=m_Hour;
			this.m_Minute=m_Minute;
			this.offSet=offSet;

		}

		public String getOffSet(){
			return offSet;
		}
		public void setOffSet(String offSet){
			this.offSet=offSet;
		}

		public String getYear() { return m_Year; }
		public void setYear(String p_Year) { m_Year = p_Year; }

		public String getMonth() {return m_Month;}
		public void setMonth(String p_Month) { m_Month = p_Month;}

		public String getDay() {return m_Day;}
		public void setDay(String p_Day) { m_Day = p_Day;}

		public String getHour() { return m_Hour; }
		public void setHour(String p_Hour) { m_Hour = p_Hour; }

		public String getAmpm() { return m_AMPM; }
		public void setAmpm(String p_AMPM) {
			m_AMPM = p_AMPM;
			m_Hour=setHH24();
		}

		public String getHour12() { return m_Hour12; }
		public void setHour12(String p_Hour12) {
			m_Hour12 = p_Hour12;
			m_Hour=setHH24();
		}
        public String setHH24(){

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
		public String getMinute() { return m_Minute; }
		public void setMinute(String p_Minute) { m_Minute = p_Minute; }

		public String getDateForDB(String dFormat){
			return m_Year+"-"+m_Month+"-"+m_Day;
		}
		public String getDisplayDate(){
			return m_Month+"-"+m_Day+"-"+m_Year;
		}
		public String getDisplayDateTime(String dFormat,String TFormat)
		{
			return m_Month+"/"+m_Day+"/"+m_Year+" "+getTimeAM(m_Hour+":"+m_Minute);
		}

		public String getDateForListDB(){
			return m_Month+m_Day+m_Year;
		}

		public String getDisplayDateTime(){
			return m_Month+"-"+m_Day+"-"+m_Year+" "+m_Hour+":"+m_Minute+ " Hrs";
		}

		public String getTimeForDB(String dFormat){
			return m_Hour+":"+m_Minute;
		}

		public String getDateTimeForVCal(String dFormat){

			Calendar cal=new GregorianCalendar(Integer.parseInt(m_Year),
					Integer.parseInt(m_Month)-1,
					Integer.parseInt(m_Day),
					Integer.parseInt(m_Hour),
					Integer.parseInt(m_Minute));


			//TimeZone tz=new SimpleTimeZone(TimeZone.getTimeZone(dFormat).getRawOffset(), dFormat);
			//cal.setTimeZone(tz);
			//tz=new SimpleTimeZone(0, dFormat);
		//	VCAL2_DATE_FORMAT.setTimeZone( tz);
			return VCAL2_DATE_FORMAT.format( cal.getTime());
		}
		public Date getDateTime() throws Exception{

		     Calendar calendar = new GregorianCalendar(Integer.parseInt(m_Year),
					Integer.parseInt(m_Month),
					Integer.parseInt(m_Day),
					Integer.parseInt(m_Hour),
					Integer.parseInt(m_Minute));
	             Date d=calendar.getTime();

        	     return d;
	      }

		public Date getExactDateTime() throws Exception{
		     Calendar calendar = new GregorianCalendar(Integer.parseInt(m_Year),
					Integer.parseInt(m_Month)-1,
					Integer.parseInt(m_Day),
					Integer.parseInt(m_Hour),
					Integer.parseInt(m_Minute));
	             Date d=calendar.getTime();

        	     return d;
	      }

	      public static String getTimeZoneKey(String str){
		      for(int i=0;i<DateTime.TIME_ZONE_KEYVALS.length;i++){
			if(str.equals(DateTime.TIME_ZONE_KEYVALS[i])){
				return DateTime.TIME_ZONE_KEYS[i];
			}
		      }
		return "";
	      }
	      public static String getTimeZoneVal(String str){
		      for(int i=0;i<DateTime.TIME_ZONE_KEYVALS.length;i++){
			if(str.equals(DateTime.TIME_ZONE_KEYVALS[i])){
				return DateTime.TIME_ZONE_VALS[i];
			}
		      }
		return "";
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

	public String[] getTimeZoneForTickets(Calendar cal,String timezone2)
	{
	Date date = cal.getTime();

	String formatPattern = "yyyy-MM-dd','HH:mm";
	SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);

	TimeZone T2=new SimpleTimeZone(TimeZone.getTimeZone(timezone2).getRawOffset(),timezone2);



	sdf.setTimeZone(T2);
	String d=sdf.format(date);

	String[] stimes=GenUtil.strToArrayStr(d,",");

	return stimes;
}

   }
