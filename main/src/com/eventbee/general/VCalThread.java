package com.eventbee.general;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.collections.map.HashedMap;

public class VCalThread implements Runnable{
	private HashMap eventDetails;
	public static final String ID="ID";
	public static final String PURPOSE="PURPOSE";
	public static final String SUMMARY="SUMMARY";

	public static final String STARTTIME="STARTTIME";
	public static final String ENDTIME="ENDTIME";
	public static final String DESCRIPTION="DESCRIPTION";

	public static final String NAME="NAME";
	public static final String LOCATION="LOCATION";
	public VCalThread(HashMap hm)  
    {  
		eventDetails = hm;  
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			//System.out.println("in vcal thread..sleeping");
			//Thread.sleep(120000);
			//System.out.println("after sleep");
			String fileName=EbeeConstantsF.get("vcal.path","/opt/jboss-3.2.1/server/default/deploy/home.war/vcal")+"/"+"vcal_"+(String)eventDetails.get(PURPOSE)+"_"+(String)eventDetails.get(ID)+".ics";
			 File file=new File(fileName);
			Calendar currentDate = Calendar.getInstance();

			String groupid=(String)eventDetails.get(ID);
			SimpleDateFormat formatter=  new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");

			String dateNow = formatter.format(currentDate.getTime());

			System.out.println("id in vcal"+(String)eventDetails.get(ID));
			// String mgrlogin=DbUtil.getVal("select login_name from authentication where to_number(user_id,'999999999999999999')=(select mgr_id from eventinfo where eventid=to_number(?,'9999999999999999'))",new String[]{(String)eventDetails.get(ID)});

			String query="select login_name from authentication where user_id= cast((select mgr_id from eventinfo where "+
					" eventid=CAST(? AS BIGINT)) as varchar)";
			String mgrlogin=DbUtil.getVal(query,new String[]{(String)eventDetails.get(ID)});
			String eventurl=ShortUrlPattern.get(mgrlogin)+"/event?eventid="+groupid;


			if(file.exists() )file.delete();
			RandomAccessFile raf=new RandomAccessFile(file ,"rw");


				raf.writeBytes("BEGIN:VCALENDAR\n") ;
				raf.writeBytes("METHOD:PUBLISH\n") ;
				raf.writeBytes("CALSCALE:GREGORIAN\n") ;
				raf.writeBytes("Content-Type:text/calendar\n") ;
				raf.writeBytes("PRODID:-//www.eventbee.com//EN\n");
				raf.writeBytes("VERSION:2.0\n") ;
				raf.writeBytes("BEGIN:VEVENT\n") ;
				raf.writeBytes("DTSTART:"+(String)eventDetails.get(STARTTIME)+"\n" ) ;
				raf.writeBytes("DTEND:"+(String)eventDetails.get(ENDTIME)+"\n" ) ;
				raf.writeBytes("LOCATION:"+(String)eventDetails.get(LOCATION)+"\n" ) ;
				raf.writeBytes("UID:"+dateNow+"@eventbee.com\n");

				//raf.writeBytes("DESCRIPTION:"+(String)hm.get(DESCRIPTION)+"\n" ) ;

				raf.writeBytes("SUMMARY:"+(String)eventDetails.get(NAME)+"\n" ) ;
				raf.writeBytes("URL:"+eventurl+"\n") ;

				raf.writeBytes("END:VEVENT\n") ;
				raf.writeBytes("END:VCALENDAR\n") ;


			}catch(Exception ex){
			System.out.println("Error in VCal in createfile()"+ex.getMessage());
			}
	}

}
