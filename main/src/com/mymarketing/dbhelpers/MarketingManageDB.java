package com.mymarketing.dbhelpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;
import com.myemailmarketing.beans.MailListDetails;
import com.mymarketing.beans.ScheduleData;

public class MarketingManageDB {
	
	public static ArrayList<HashMap<String,String>> getCompletedScheduleList(String tempId){
		
		String query="select blastid,name,to_char(sch_time,'Dy, Mon DD ,YYYY, HH:00 PM') as schdate," +
				"sent_count,totalcount,to_char(starttime,'Dy, Mon DD ,YYYY, HH:MI:SS.MS PM') as starttime," +
				"to_char(lastsenttime,'Dy, Mon DD ,YYYY, HH:MI:SS.MS PM') as lastsenttime,maillistid from " +
				"marketing_schedules where status='S' and campid=? order by blastid desc";
		return getSchedules(query,tempId);
	}
	
	public static ArrayList<HashMap<String,String>> getScheduledScheduleList(String tempId){
		String query="select blastid,name,to_char(sch_time,'Dy, Mon DD ,YYYY, HH:00 PM') as schdate," +
		"sent_count,totalcount,to_char(starttime,'Dy, Mon DD ,YYYY, HH:MI:SS.MS PM') as starttime," +
		"to_char(lastsenttime,'Dy, Mon DD ,YYYY, HH:MI:SS.MS PM') as lastsenttime,maillistid from " +
		"marketing_schedules where status='P' and campid=? order by blastid desc";		
		return getSchedules(query,tempId);
	}
	
	public static ArrayList<HashMap<String,String>> getRunningScheduleList(String tempId){
		
		String query="select blastid,name,to_char(sch_time,'Dy, Mon DD ,YYYY, HH:00 PM') as schdate," +
				"sent_count,totalcount,to_char(starttime,'Dy, Mon DD ,YYYY, HH:MI:SS.MS PM') as starttime," +
				"to_char(lastsenttime,'Dy, Mon DD ,YYYY, HH:MI:SS.MS PM') as lastsenttime,maillistid from " +
				"marketing_schedules where status='K' and campid=? order by blastid desc";
		return getSchedules(query,tempId);
	}
	
	public static ArrayList<HashMap<String,String>> getSchedules(String query,String tempId){
		System.out.println("In getSchedules templateId: "+tempId);
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		ArrayList<HashMap<String,String>> listObj=new ArrayList<HashMap<String,String>>();
		statobj=dbmanager.executeSelectQuery(query,new String[]{tempId});
		
		if(statobj !=null && statobj.getStatus() && statobj.getCount()>0 ){
		int recordcounttodata=statobj.getCount();
		for(int i=0;i<recordcounttodata;i++){
				HashMap<String,String> mapObj=new HashMap<String,String>();
				String blastid=dbmanager.getValue(i,"blastid","");
				String name=dbmanager.getValue(i,"name","");
				String schdate=dbmanager.getValue(i,"schdate","");
				String sent_count=dbmanager.getValue(i,"sent_count","");
				String maillistid=dbmanager.getValue(i,"maillistid","");
				String starttime=dbmanager.getValue(i,"starttime","");
				String lastsenttime=dbmanager.getValue(i,"lastsenttime","");
				mapObj.put("schid",blastid);
				mapObj.put("schname",name);
				mapObj.put("schdate",schdate);
				mapObj.put("sent_count",sent_count);
				mapObj.put("maillistid",maillistid);
				mapObj.put("starttime",starttime);
				mapObj.put("lastsenttime",lastsenttime);
				
				listObj.add(mapObj);
			}
		}
		return listObj;

	}
	
	public static ArrayList<MailListDetails> getMailLists(String ownerid){
		String query="select list_id,name from marketing_mail_list where owner_id=CAST(? AS INTEGER) order by list_id desc";
		DBManager dbmanager=new DBManager();
		ArrayList<MailListDetails> mailList=new ArrayList<MailListDetails>();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{ownerid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
				MailListDetails mListData=new MailListDetails();
				String lId=dbmanager.getValue(k,"list_id","");
				String lname=dbmanager.getValue(k,"name","");
				mListData.setListId(lId);
				mListData.setListName(lname);
				mailList.add(mListData);
			}
		}
		return mailList;
	}
	
	
	public static HashMap<String,String> getMaillistMembersCount(){
	 	HashMap<String,String> unitlistmap= new HashMap<String,String>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
			
			String countqduery="select list_id, count(*) as count from marketing_list_emails " +
					"where CAST(list_id AS VARCHAR) in(select list_id from marketing_mail_list)  group by list_id"; 
			
			statobj=dbmanager.executeSelectQuery(countqduery,null);
			if(statobj !=null && statobj.getStatus()){
				int recordcounttodata=statobj.getCount();
				for(int i=0;i<recordcounttodata;i++){
				unitlistmap.put(dbmanager.getValue(i,"list_id",""),dbmanager.getValue(i,"count","0"));
			}
			}//end if staus
				return unitlistmap;
	 }
	
	public static void saveScheduleData(ScheduleData scheduleData){
		System.out.println("In saveScheduleData schId: "+scheduleData.getSchId());
		String name=scheduleData.getName();
		String schId=scheduleData.getSchId();
		String tempId=scheduleData.getTemplateId();
		String mailList=scheduleData.getMailList();
		
		String schTime="";
		java.sql.Timestamp tsp=null;
		System.out.println("scheduled data"+scheduleData.getMonth()+"::"+scheduleData.getDay()+"::"+scheduleData.getYear()+"::"+scheduleData.getHour()+"::"+scheduleData.getTime());
		if("now".equals(scheduleData.getSchtimeType())){
			java.util.Date d=new java.util.Date();
			tsp=new java.sql.Timestamp(Calendar.getInstance().get(Calendar.YEAR)-1900,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH),Calendar.getInstance().get(Calendar.HOUR_OF_DAY),0,0,0);
			schTime=tsp.toString();
		}else if("date".equals(scheduleData.getSchtimeType())){				 
			tsp=new java.sql.Timestamp(Integer.parseInt(scheduleData.getYear())-1900,Integer.parseInt(scheduleData.getMonth())-1,Integer.parseInt(scheduleData.getDay()),Integer.parseInt(scheduleData.getTime()),0,0,0);	
			schTime=tsp.toString();
		}else{
			schTime=null;
		}
		String insertQry="insert into marketing_schedules (blastid,campid,maillistid,status,name,sent_count,totalcount,sch_time,created_at)"+
		" values(?,?,?,?,?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:00:00'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
		
		String updateQry="update marketing_schedules set name=?,maillistid=?,sch_time=to_timestamp(?,'YYYY-MM-DD HH24:00:00') where blastid=? and campid=?";

		
		if(schId.equals("")){
			schId=DbUtil.getVal("select nextval('seq_campaign')",null);
			DbUtil.executeUpdateQuery(insertQry,new String[]{schId,tempId,mailList,"P",name,"0","0",schTime,DateUtil.getCurrDBFormatDate()});
		}else
			DbUtil.executeUpdateQuery(updateQry, new String[]{name,mailList,schTime,schId,tempId});
		
	}
	
	public static void getScheduleData(String schId, String templateId, ScheduleData schData){
		System.out.println("In getScheduleData schId: "+schId);
		String query = "select name,maillistid,EXTRACT(HOUR FROM sch_time) AS hour," +
				"EXTRACT(DAY FROM sch_time) AS day, EXTRACT(YEAR FROM sch_time) AS year," +
				"EXTRACT(MONTH FROM sch_time) AS month,sch_time from marketing_schedules where blastid=? and campid=?";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(query,new String[]{schId,templateId});
		if(statobj.getStatus()){
			schData.setTemplateId(templateId);
			schData.setName(dbmanager.getValue(0,"name",""));
			schData.setMailList(dbmanager.getValue(0,"maillistid",""));
			schData.setDay(dbmanager.getValue(0,"day",""));
			schData.setYear(dbmanager.getValue(0,"year",""));
			schData.setMonth(dbmanager.getValue(0,"month",""));
			schData.setHour(dbmanager.getValue(0,"hour",""));
			String sch_time = dbmanager.getValue(0,"sch_time","");
			if(sch_time.equals(""))
				schData.setSchtimeType("later");
			else
				schData.setSchtimeType("date");
		}
	
	}
	
	public static void deleteSchedule(String blastid){
		String query="delete from marketing_schedules where blastid=?";
		DbUtil.executeUpdateQuery(query,new String[]{blastid});
		System.out.println("schedule deleted successfully");
	}

}
