package com.eventbee.namedquery;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class NQDbManager {
	private static Logger log = Logger.getLogger(NQDbManager.class);
	private static HashMap datasources=null;
	public static HashMap queriesHash= new HashMap();
	private static HashMap<String,ArrayList<InParam>> inparamsHash=null;
	private static HashMap<String,ArrayList<OutParam>> outparamsHash=null;
	private static String defaultDSName="ds1";
	
	public static HashMap getDataSources(){
		return datasources;
	}
	
	public static void addDataSource(String dsname, Object dataSource){
		if(datasources==null)
			datasources=new HashMap();
		datasources.put(dsname, dataSource);
		
	}
	
	public static Connection getConnection(String dsname){
		if(dsname==null) dsname=defaultDSName;
		Connection connection = null;
		try{
			if(datasources!=null){
				log.info("Accessed data source key: "+dsname);
				DataSource datasource=(DataSource)datasources.get(dsname);
				if(datasource!=null)
				connection = datasource.getConnection();
			}
			log.info("not Accessed data source key: ");
			return connection;
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return connection;
	}
	/* loading all quries in to hashmap.*/
	public static void loadAllQueries(){
		try{			
			populateInParams();
			populateOutParams();
			populateAllQuries();
		}catch(Exception ex){
			log.error("Exception in loadAllQueries: "+ex);
		}
	}
	
	/* populate inputparams*/
	private static void populateInParams(){
		try{
			inparamsHash = new HashMap<String,ArrayList<InParam>>();
			//log.info("populating in params");
			String queryinparams = "SELECT DISTINCT QID,PARAMINDEX,PARAMKEY,PARAMDEFAULT,PARAMTYPE FROM QUERYINPARAMS ORDER BY QID,PARAMINDEX";
			NQDbHelper dbhelper = new NQDbHelper();
			NQStatusObj statobj = dbhelper.executeSelectQuery(queryinparams, null);
			if(statobj.getStatus()){
				for(int k=0;k<statobj.getCount();k++){
					String qid = dbhelper.getValue(k, "qid", "");
					ArrayList<InParam> inlist;					
					if(!inparamsHash.containsKey(qid)){
						inlist = new ArrayList<InParam>();						
					}else{
						inlist = inparamsHash.get(qid);						
					}
					InParam inp = new InParam();
					inp.setIndex(dbhelper.getValue(k, "PARAMINDEX", ""));
					inp.setKey(dbhelper.getValue(k, "PARAMKEY", ""));
					inp.setDefaultValues(dbhelper.getValue(k, "PARAMDEFAULT", ""));
					inp.setType(dbhelper.getValue(k, "PARAMTYPE", ""));
					inlist.add(inp);
					inparamsHash.put(qid, inlist);
				}
				//log.info("inparamsHash: "+inparamsHash);
			}
		}catch(Exception ex){
			log.error("Exception in populateInParams: "+ex);
		}
	}
	/* populate outputparams*/
	private static void populateOutParams(){
		try{
			outparamsHash = new HashMap<String,ArrayList<OutParam>>();
			//log.info("populating out params");
			String queryoutparams = "SELECT DISTINCT QID,PARAMKEY,PARAMCOLUMN,PARAMDEFAULT FROM QUERYOUTPARAMS ORDER BY QID";
			NQDbHelper dbhelper = new NQDbHelper();
			NQStatusObj statobj = dbhelper.executeSelectQuery(queryoutparams, null);
			if(statobj.getStatus()){
				for(int k=0;k<statobj.getCount();k++){
					String qid = dbhelper.getValue(k, "qid", "");
					ArrayList<OutParam> outlist;
					
					if(!outparamsHash.containsKey(qid)){
						outlist = new ArrayList<OutParam>();						
					}else{
						outlist = outparamsHash.get(qid);						
					}
					OutParam outp = new OutParam();
					outp.setColumnName(dbhelper.getValue(k, "PARAMCOLUMN", ""));
					outp.setDefaultVal(dbhelper.getValue(k, "PARAMDEFAULT", ""));
					outp.setKey(dbhelper.getValue(k, "PARAMKEY", ""));					
					outlist.add(outp);
					outparamsHash.put(qid, outlist);
					//log.info("outparamsHash: "+outparamsHash);
				}
			}
		}catch(Exception ex){
			log.error("Exception in populateOutParams: "+ex);
		}
	}
	/* load all quries into queriesHash*/
	private static void populateAllQuries(){
		try{
			queriesHash = new HashMap();
			//log.info("populating all quries");
			String dynaquery = "SELECT DISTINCT QID,MODULE,QUERYNAME,SQL,DSNAME FROM DYNAQUERIES ORDER BY QID";
			NQDbHelper dbhelper = new NQDbHelper();
			NQStatusObj statobj = dbhelper.executeSelectQuery(dynaquery, null);
			if(statobj.getStatus()){
				for(int k=0;k<statobj.getCount();k++){
					String qid = dbhelper.getValue(k, "qid", "");
					Query query = new Query();
					query.setDsn(dbhelper.getValue(k, "dsname", ""));
					query.setSql(dbhelper.getValue(k, "sql", ""));
					query.setQid(qid);
					ArrayList<InParam> inp = new ArrayList<InParam>();
					if(inparamsHash.get(qid)!=null){
						inp = inparamsHash.get(qid);
					}
					ArrayList<OutParam> outp = new ArrayList<OutParam>();
					if(outparamsHash.get(qid)!=null){
						outp = outparamsHash.get(qid);
					}
					query.setInparams(inp);
					query.setOutparams(outp);					
					queriesHash.put(dbhelper.getValue(k, "queryname", ""), query);
				}
			}
			//log.info("queriesHash: "+queriesHash);
		}catch(Exception ex){
			log.error("Exception in populateAllQuries: "+ex);
		}
	}
	
}
