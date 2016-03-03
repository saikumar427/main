package com.eventbee.general;

import java.util.*;
import java.io.*;
import java.util.logging.*;
import com.eventbee.general.EbeeConstantsF;

public class EventbeeLogger {


	/*
		#logger related properties to be set in .ebeeprops file
		eventbee.logging.namesoflogger=com.eventbee.main,com.eventbee.exception

		com.eventbee.main.level=DEBUG
		com.eventbee.main.logfilename=/home/narayan/log/EventBee.log

		com.eventbee.exception.level=ERROR

		com.eventbee.exception.logfilename=/home/narayan/log/EVENTBEE_EXCEPTION.log
		#ERROR,WARNING,INFO,DATA,INPUT,DEBUG


		USAGE OF THIS CLASS IN JAVA,JSP XSP
			EXAMPLES ARE AS FOLLOWS
		EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "sourceClass", "sourceMethod", "msg", Throwable thrown) ;
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG, "Listmainscr.xsp", "METHOD", EventbeeLogger.LOG_END_PAGE, null);
		OR
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG, "Listmainscr.xsp", "METHOD", "CUSTOM MESSAGE", STRING[] OR null);




	*/

	public static final String LOG_START_PAGE="ENTERED PAGE ";
	public static final String LOG_END_PAGE="COMPLETED PAGE ";
	public static final String LOGGER_MAIN="com.eventbee.main";
	public static final String LOGGER_EXCEPTION="com.eventbee.exception";


	private static  Logger logger;

	public static final String ERROR=Level.SEVERE.toString();
	public static final String WARNING=Level.WARNING.toString();
	public static final String INFO=Level.INFO.toString();
	//public static final String CONFIG=Level.CONFIG.toString();-not used
	public static final String DATA=Level.FINE.toString();
	public static final String INPUT=Level.FINER.toString();
	public static final String DEBUG=Level.FINEST.toString();
	static final HashMap loggingmap=new HashMap();


	static{



		loggingmap.put("ERROR",EventbeeLogger.ERROR);
		loggingmap.put("WARNING",EventbeeLogger.WARNING);
		loggingmap.put("INFO",EventbeeLogger.INFO);
		loggingmap.put("DATA",EventbeeLogger.DATA);
		loggingmap.put("INPUT",EventbeeLogger.INPUT);
		loggingmap.put("DEBUG",EventbeeLogger.DEBUG);


		try{




		String nameoflogger=	EbeeConstantsF.get("eventbee.logging.namesoflogger",null);
			if(nameoflogger==null){
				throw new Exception("no logger found for property  eventbee.logging.namesoflogger");
			}else{
				String[] noofloggers=GenUtil.strToArrayStr( nameoflogger,",");

				for(int i=0;i<noofloggers.length;i++){
				String leveloflogger=	EbeeConstantsF.get(noofloggers[i]+"."+"level","DEBUG");
				//System.out.println("leveloflogger:="+leveloflogger);
				String nameoflogfiletocreate=	EbeeConstantsF.get(noofloggers[i]+"."+"logfilename",noofloggers[i]+".log");
				//System.out.println("nameoflogfiletocreate:="+nameoflogfiletocreate);
				boolean append = true;
				FileHandler handler = new FileHandler(nameoflogfiletocreate, append);
				handler.setFormatter(new SimpleFormatter());

				logger = Logger.getLogger(noofloggers[i]);
				String levelname=(String)loggingmap.get(leveloflogger);
				//System.out.println("from file level setted to :="+levelname);
				if(levelname==null)levelname=ERROR;
				logger.setLevel(Level.parse(  levelname    )) ;
				logger.setUseParentHandlers(false);
				//System.out.println("from file level setted to :="+logger.getLevel().toString());
				logger.addHandler(handler);


				}//end for
			}
		}catch(Exception ex){
			logger= Logger.getLogger("Eventbeelogger");
			ConsoleHandler handler = new ConsoleHandler();
			logger.setUseParentHandlers(false) ;
			logger.addHandler(handler);
		}

	}


	public static void reloadLogProperties(){

		try{




		String nameoflogger=	EbeeConstantsF.get("eventbee.logging.namesoflogger",null);
			if(nameoflogger==null){
				throw new Exception("no logger found for property  eventbee.logging.namesoflogger");
			}else{
				String[] noofloggers=GenUtil.strToArrayStr( nameoflogger,",");

				for(int i=0;i<noofloggers.length;i++){
				String leveloflogger=	EbeeConstantsF.get(noofloggers[i]+"."+"level","DEBUG");
				//System.out.println("leveloflogger:="+leveloflogger);
				String nameoflogfiletocreate=	EbeeConstantsF.get(noofloggers[i]+"."+"logfilename",noofloggers[i]+".log");
				//System.out.println("nameoflogfiletocreate:="+nameoflogfiletocreate);
				boolean append = true;
				FileHandler handler = new FileHandler(nameoflogfiletocreate, append);
				handler.setFormatter(new SimpleFormatter());

				logger = Logger.getLogger(noofloggers[i]);
				String levelname=(String)loggingmap.get(leveloflogger);
				if(levelname==null)levelname=ERROR;
				logger.setLevel(Level.parse(  levelname    )) ;
				logger.setUseParentHandlers(false);

				logger.addHandler(handler);


				}//end for
			}
		}catch(Exception ex){
			logger= Logger.getLogger("Eventbeelogger");
			ConsoleHandler handler = new ConsoleHandler();
			logger.setUseParentHandlers(false) ;
			logger.addHandler(handler);
		}
	}



//EventbeeLogger.log(String nameoflogger,String level, String sourceClass, String sourceMethod, String msg, Object[] params)

	public static void logException(String nameoflogger,String level, String sourceClass, String sourceMethod, String msg, Throwable thrown)  {
		if(nameoflogger !=null)
		logger = Logger.getLogger(nameoflogger);
		else
			logger=Logger.getLogger("com.eventbee.main");


		String server=EbeeConstantsF.get("serveraddress","www.eventbee.com");
		//logger.logp( Level.parse(EventbeeLogger.ERROR),  sourceClass,  sourceMethod,  msg,  thrown);
		logger.logp( Level.parse(EventbeeLogger.ERROR),  sourceClass,  sourceMethod,  msg,  "");
		String sendemail=EbeeConstantsF.get("exceptions.email","no");
		if("yes".equals(sendemail)){
			EmailObj emailobj=new EmailObj();
			emailobj.setTo(EbeeConstantsF.get("exceptions.send.email","sridhar@beeport.com"));
			emailobj.setFrom("exceptions@beeport.com");
			//emailobj.setBcc(EbeeConstantsF.get("exceptions.send.bccemail","sridhar@beeport.com"));
			emailobj.setTextMessage( msg);
			emailobj.setHtmlMessage( msg );
			emailobj.setSubject("("+server+"),Exceprtion in class: "+sourceClass+", in method: "+sourceMethod);
			emailobj.setReplyTo("exceptions@beeport.com");
			//EventbeeMail.sendHtmlMailPlain( emailobj);
		}

	 }

	 public static void log(String nameoflogger,String level, String sourceClass, String sourceMethod, String msg, Object[] params) {
		 if(nameoflogger !=null)
		 logger = Logger.getLogger(nameoflogger);
	 		else
		logger=Logger.getLogger("com.eventbee.main");
		 if(params !=null)
		 logger.logp( Level.parse(level),  sourceClass,  sourceMethod,  msg,  params);
	 	else
			 logger.logp( Level.parse(level),  sourceClass,  sourceMethod,  msg);
	 }
	public static void log(String sourceClass,String sourceMethod,String msg,Object[] params)
	{
		log(LOGGER_MAIN,DEBUG,sourceClass,sourceMethod,msg,params);
	}


}

