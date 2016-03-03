package com.eventbee.general;

import javax.naming.*;

import java.sql.*;

import javax.sql.*;


public class EventbeeConnection  {

	public static String context="";

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	public static Connection getConnection(){
	Connection con=null;
		try{
		con= getWriteConnection();
		}catch(Exception sqlex){
			System.out.println("Exception from Database connection :"+sqlex.getMessage());
		}
		return con;
	}//end of getConnection

	public static Connection getConnection(String name){
	Connection con=null;
		try{
		con= getPostgresConnection(name);
		}catch(Exception sqlex){
			System.out.println("Exception from Database connection :"+name+" "+sqlex.getMessage());
		}
		return con;
	}//end of getConnection
	
	
	
	
	private static Connection getPostgresConnection() throws Exception{
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup(context);
		java.sql.Connection con = ds.getConnection();
		return con;


	}//end of getconnection

	private static Connection getPostgresConnection(String name) throws Exception{
		System.out.println("EventbeeConnection getPostgresConnection readDsn: "+name);
						Context ctx = new InitialContext();
						javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:/"+name);
						java.sql.Connection con = ds.getConnection();
						return con;
				
				
	}//end of getconnection




	public static Connection getReadConnection(String modulename) throws Exception{
			String dsname=getDSName(modulename+".read.connection");
			if(dsname==null|| "".equals(dsname) )return getReadConnection();
			return getPostgresConnection(dsname);
	}
	public static Connection getWriteConnection(String modulename) throws Exception{
		String dsname=getDSName(modulename+".write.connection");
			if(dsname==null|| "".equals(dsname) )return getWriteConnection(); 
			return getPostgresConnection(dsname);
	}

	public static Connection getReadConnection() throws Exception{
		String dsname=getDSName("defalut.read.connection");
		if(dsname==null|| "".equals(dsname) )return getPostgresConnection() ; 
		return getPostgresConnection(dsname);
	}
	public static Connection getWriteConnection() throws Exception{
		String dsname=getDSName("defalut.write.connection");
		if(dsname==null|| "".equals(dsname) )return getPostgresConnection() ; 
		return getPostgresConnection(dsname);
	}
	

	private static String getDSName(String modulename){
		return EbeeConstantsF.get(modulename,null);
	}


}

