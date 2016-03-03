package com.eventbee.general;
import java.util.*;
import java.text.*;
import java.sql.*;



public class DBManager{

private Map columnMap=null;
private String[] columnnames=null;
private List records=null;
List columnlist=null;
private String dsnname=null;

public DBManager(String name){

dsnname=name;
}


public DBManager(){


}


public StatusObj executeSelectQuery(String query,String[] inputparams){

HashMap recordsmap=new MasterDB(dsnname).getRecords( query, inputparams,null);
StatusObj statobj=new StatusObj(false,"","0",0);
if(recordsmap !=null){

	columnMap=(Map)recordsmap.get(MasterDB.COLUMN_MAPPING);
	columnnames=(String[])recordsmap.get(MasterDB.COLUMN_NAMES);
	columnlist=Arrays.asList(columnnames);
	records=(List)recordsmap.get(MasterDB.RECORDS);
	statobj.setStatus(true);
	statobj.setData(recordsmap);
	statobj.setCount( records.size());
}
	return statobj;

}
public String[] getColumnNames(){
	return columnnames;
}
public Object getValueFromRecord(int index,String columnname1,String defval){
	Object retval=null;
	String columnname=(columnname1 !=null)?columnname1.toLowerCase() :" ";
	try{
		Object[] record=(Object[])records.get(index);
		retval=(columnlist.indexOf(columnname)!=-1)?(record[columnlist.indexOf(columnname)  ]):defval;
		if(retval==null)retval=defval;
	}catch(Exception exp){
	System.out.println("Error in getValueFromRecord()"+exp.getMessage());
	}
	return retval;
}

public String getValue(int index,String columnname1,String defval){
	Object retval=null;
	String columnname=(columnname1 !=null)?columnname1.toLowerCase() :" ";
	try{
		Object[] record=(Object[])records.get(index);
		retval=(columnlist.indexOf(columnname)!=-1)?(record[columnlist.indexOf(columnname)  ]):defval;
		if(retval==null)retval=defval;
	}catch(Exception exp){
		System.out.println("Error in getValueFrom Record()"+exp.getMessage());
	}

	if(retval !=null)return retval.toString();
	return null;
}




public static void main(String [] args){

DBManager dbmanager=new DBManager();

String query="select a.msgto,b.msgat,u.first_name, u.last_name,u.email from messageinfo a,messages b,user_profile u,Authentication k "

 +" where a.msgid=b.msgid  and u.user_id=k.user_id and a.msgto=u.user_id and b.msgat>(select nullif(now()-4, max(processsed_at)) from processed_alerts where process_code='Messaging') and k.unit_id=13579 ";



dbmanager.executeSelectQuery( query,null);



}




}
