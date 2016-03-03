package com.event.dbhelpers;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.csvreader.CsvReader;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.StatusObj;

public class PriorityRegistrationDB {
	public static ArrayList<HashMap<String,String>> saveXLData(String eid,String mgrid, String listId,String ext){
		ArrayList<HashMap<String,String>> memberDataList=new ArrayList<HashMap<String,String>>();
		try{
			String filepath=EbeeConstantsF.get("temp.upload.filepath","C:\\uploads");
			String fileName="maillist_"+mgrid+"_"+listId+".csv";
			FileInputStream fileInputStream = new FileInputStream(filepath+"/"+fileName);
			fileName=filepath+"/"+fileName;
		//	ArrayList<String> filecontent=new ArrayList<String>();
			
		//	MemberData memberDataObj=null;
			int i=ext.indexOf(".xls");
			String fieldcount=getNoOfFields(eid,listId);
			/*System.out.println("my field count::::"+fieldcount);*/
			if(i!=-1)
			{
				
			    Row row = null;
			    Cell cell1 = null;
			    Cell cell2 = null;
			    
			  
			    
			    Iterator<Row> rowIterator = null;
			    Iterator<Cell> cellIterator = null;
			    int numSheets = 0;
			    
			    Workbook workbook = WorkbookFactory.create(fileInputStream);
			    numSheets = workbook.getNumberOfSheets();
			    for(int x = 0; x < numSheets; x++) 
			    {
			        Sheet sheet = workbook.getSheetAt(x);
			        rowIterator = sheet.iterator();
			        //Sheet sheet = workbook.getSheet("sheet1");
			        String pwd="";
			        while (rowIterator.hasNext())
			        {	
			            row = rowIterator.next();
			            cellIterator = row.iterator();
			            while (cellIterator.hasNext())
			            {     
			            	
					    
			                 //memberDataObj=new MemberData();
			                 cell1 = cellIterator.next();
			                 cell1.setCellType(Cell.CELL_TYPE_STRING);
			                 String user=cell1.toString().trim();
			                 for(int j=0;j<numSheets;j++){            	
			                 }
			                 if("2".equals(fieldcount)){
			                	 
			                	 cell2 = cellIterator.next();
			                	 cell2.setCellType(Cell.CELL_TYPE_STRING);
			                	 pwd=cell2.toString().trim();
			                	 
			                 }
			                 
			                 if("2".equals(fieldcount))
			                 {	
			                	 HashMap<String,String>tempMap=new HashMap<String,String>();
			                	 tempMap.put("userid",user);
			                	 tempMap.put("password",pwd);
			                     memberDataList.add(tempMap);
			                     
			                 }
			                 else
			                 {   
			                	 System.out.println("6");
			                	 HashMap<String,String>tempMap=new HashMap<String,String>();
				                 tempMap.put("userid",user); 
				                 memberDataList.add(tempMap);
			                 }
				        }
		             }
			    }
			}
			else
			{ 
					CsvReader products = new CsvReader(fileName);
					products.readHeaders();
					String headers[]=products.getHeaders();
					
						
						if("2".equals(fieldcount))
						{	
							HashMap<String,String>tempMap=new HashMap<String,String>();
							tempMap.put("userid",headers[0].toString().trim());
							tempMap.put("password",headers[1].toString().trim());
							memberDataList.add(tempMap);
						}
						else
		                 {   
		                	 HashMap<String,String>tempMap=new HashMap<String,String>();
			                 tempMap.put("userid",headers[0].toString().trim()); 
			                 memberDataList.add(tempMap);
		                 }
						
								
					
					while (products.readRecord())
					{
						String array[]=products.getValues();
						for(int k=0;k<array.length;k++)
						{
							if("2".equals(fieldcount)){
								HashMap<String,String>tempMap=new HashMap<String,String>();
								tempMap.put("userid",array[k].toString().trim());
								tempMap.put("password",array[k+1].toString().trim());
								memberDataList.add(tempMap);
							}
							else
			                 {
			                	 HashMap<String,String>tempMap=new HashMap<String,String>();
				                 tempMap.put("userid",array[k].toString().trim()); 
				                 memberDataList.add(tempMap);
			                 }
						}
					}
					products.close();
				}
		        File f1=new File(filepath+"/"+fileName);
		        f1.delete();
		}//try close
		catch(Exception e){
			System.out.println("Exception while processing the file data in File Upload in Mail Lists"+e.getMessage());
		}
	return memberDataList;
	}
	
	public static boolean isRSVPEvent(String uploadevt){
	if("RSVP".equals(EventDB.getPowerType(uploadevt)))
	return true;
	else
	return false;
	}
	
	/*public static void getRowData(String eid){
		
		
		DbUtil.executeUpdateQuery("insert into priority_list_records(id,password)values(?,?)",new String[]{eid});
	}*/
	public static ArrayList<HashMap<String,String>> getRSVPData(String uploadedevt){
		ArrayList<HashMap<String,String>> memberList=new ArrayList<HashMap<String,String>>();
		String query="select transactionid from profile_base_info where eventid=cast(? as bigint)";
		DBManager dbm=new DBManager();
		StatusObj sbj=null;
		sbj=dbm.executeSelectQuery(query, new String[]{uploadedevt});
		if(sbj.getStatus() && sbj.getCount()>0){
			for(int i=0;i<sbj.getCount();i++){
				HashMap<String,String>tempMap=new HashMap<String,String>();
				tempMap.put("userid",dbm.getValue(i,"transactionid",""));
				tempMap.put("password","");
				memberList.add(tempMap);
			}
		}
	return memberList;
	}
	
	public static void saveRSVPEventData(String eid,String uploadedevt,String type,String fieldcount){
		DbUtil.executeUpdateQuery("delete from priority_list_records where eventid=cast(? as bigint)", new String[]{eid});
	    DbUtil.executeUpdateQuery("insert into priority_list_records(eventid,nooffields,uploadtype,uploadedevent,created_at) values(?,?,?,?,now())",new String[]{eid,fieldcount,type,uploadedevt});
	}	
	
	public static void deleteLoadedData(String list_id, String eid)
	{System.out.println("in my deleteeeeee....");
	String purpose="delete";
		DbUtil.executeUpdateQuery("delete from priority_list where list_id=? and eventid=cast(? as bigint)",new String[]{list_id,eid});
		DbUtil.executeUpdateQuery("delete from priority_list_records where list_id=? and eventid=cast(? as bigint)",new String[]{list_id,eid});
		insertIntoPriorityListTrack(eid,list_id,purpose,"");

	}
	public static JSONObject saveListData(String eid,String listId,String seltickets,String listname,String firstlbl,String secondlbl,String fieldcount){
		JSONObject saveJson=new JSONObject();
		JSONObject trackJson=new JSONObject();
		try{
			String purpose="";
			try{
				StatusObj statusObj=null;
				if(!"".equals(listId)){
		    		String updateQry="update priority_list set nooffields=?,list_name=?,tickets=?,field1=?,field2=?,created_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),updated_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') where eventid=CAST(? AS BIGINT) and list_id=?";
		    		statusObj=DbUtil.executeUpdateQuery(updateQry,new String[]{fieldcount,listname,seltickets,firstlbl,secondlbl,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),eid,listId});
		    		purpose="Edit List";
				}else{
					listId=DbUtil.getVal("select nextval('PRIORITY_LISTID')", null);
		    		String insertQuery="insert into priority_list(eventid,nooffields,list_id,list_name,tickets,field1,field2,created_at,updated_at) values(CAST(? AS BIGINT),?,?,?,?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
		    		statusObj=DbUtil.executeUpdateQuery(insertQuery,new String[]{eid,fieldcount,listId,listname,seltickets,firstlbl,secondlbl,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate()});
		    		purpose="Add List";
				}
				
				if(statusObj.getCount()>0){
		    		saveJson.put("status","success");
		    		saveJson.put("listid",listId);
		    		saveJson.put("listname",listname);
		    		trackJson.put("status","success");
				}else{
					saveJson.put("status","fail");
					trackJson.put("status","fail");
				}
				
		    	}catch(Exception e){
		    		try{
		    		saveJson.put("status","fail");
		    		trackJson.put("status","fail");
		    		}catch(Exception exp){
		    			
		    		}
		    		System.out.println("Exception ocuured while parsing jsonArray in PriorityRegistrationDB.java "+e.getMessage());
		    	}
			trackJson.put("listid",listId);
			trackJson.put("listname",listname);
			trackJson.put("seltickets",seltickets);
			trackJson.put("firstlbl",firstlbl);
			trackJson.put("secondlbl",secondlbl);
			trackJson.put("nooffields",fieldcount);
			insertIntoPriorityListTrack(eid,listId,purpose,trackJson.toString());
		}catch(Exception e){		
		}
	    return saveJson;
	}
	
	/*public static String deleteData(String listId,String eid)
	{
		DbUtil.executeUpdateQuery("delete from priority_list_records where eventid=?::bigint and list_id=?",new String[]{eid,listId});
		return "deleted";
	}*/
	
public static JSONObject saveEditData(String eid,String uploadeddata,String type,String tkts,String listname,String listId){
		
		JSONObject saveJson=new JSONObject();
		System.out.println("in save edit data.........");
		try{
	    	JSONArray jsonArray=new JSONArray(uploadeddata);
	    	if(jsonArray.length()>0){
	    		/*DbUtil.executeUpdateQuery("insert into priority_list(eventid,nooffields,created_at,list_id,list_name,tickets,field1,field2,count) " +
	    				"values(?,?,now(),?,?,?,?,?,?)",
	    				new String[]{eid,fieldcount,listId,listname,tkts,firstlbl,secondlbl,jsonArray.length()+""});*/
	    		DbUtil.executeUpdateQuery("delete from priority_list_records where eventid=?::bigint and list_id=?",new String[]{eid,listId});

	    		/*String updatequery="update priority_list set nooffields=?,list_name=?,tickets=?,field1=?,field2=?,count=? where eventid=?::bigint and list_id=?";
	    		DbUtil.executeUpdateQuery(updatequery,new String[]{fieldcount,listname,tkts,firstlbl,secondlbl,jsonArray.length()+"",eid,listId});*/
	    		String query="insert into priority_list_records(id,password,eventid,status,list_id) values";
	    		System.out.println("after insert operation..........");
	    		for(int i=0;i<jsonArray.length();i++){
	    			JSONObject js=(JSONObject)jsonArray.get(i);
	    			query+="('"+js.getString("userid")+"','"+js.getString("password")+"',"+eid+",'NOT USED','"+listId+"')";
	    			if(i!=jsonArray.length()-1)
	    				query+=",";
	    		}
	    		DbUtil.executeUpdateQuery(query,null);
	    		saveJson.put("status","success");
	    		saveJson.put("listid",listId);
	    		saveJson.put("listname",listname);
	    	}
	    	
	    	
	    		String updatequery="update priority_list_records set list_name=?,tickets_applicable=? where eventid=?::bigint and list_id=?";
	    	 	DbUtil.executeUpdateQuery(updatequery,new String[]{listname,tkts,eid,listId});
	    	}catch(Exception e){
	    		try{
	    		saveJson.put("status","fail");
	    		}catch(Exception exp){
	    			
	    		}
	    		System.out.println("Exception ocuured while parsing jsonArray in PriorityRegistrationDB.java "+e.getMessage());
	    	}
	    return saveJson;
	}

public static JSONObject savePriorityListRecords(String eid,String jsonRecords,String listId){
	
	JSONObject saveJson=new JSONObject();
	JSONObject recordsJson=new JSONObject();
	System.out.println("in save edit data.........jsonRecords: "+jsonRecords);
	try{
		String purpose="";
	try{
    	JSONArray jsonArray=new JSONArray(jsonRecords);
    	if(jsonArray.length()>0){
    		DbUtil.executeUpdateQuery("delete from priority_list_records where eventid=?::bigint and list_id=?",new String[]{eid,listId});
    		String query="insert into priority_list_records(id,password,eventid,status,list_id) values";
    		
    		for(int i=0;i<jsonArray.length();i++)
    		{
    			JSONObject js=(JSONObject)jsonArray.get(i);
    			query+="('"+js.getString("userid")+"','"+js.getString("password")+"',"+eid+",'NOT USED','"+listId+"')";
    			if(i!=jsonArray.length()-1)
    				query+=",";
    		}
    		DbUtil.executeUpdateQuery(query,null);
    		saveJson.put("status","success");
    		saveJson.put("listid",listId);
    		recordsJson.put("status","success");
    		
    	}
    	}catch(Exception e){
    		try{
    		saveJson.put("status","fail");
    		recordsJson.put("status","fail");
    		}catch(Exception exp){
    			
    		}
    		
    		System.out.println("Exception ocuured while parsing jsonArray in PriorityRegistrationDB.java "+e.getMessage());
    	}

	recordsJson.put("listid",listId);
	recordsJson.put("records",jsonRecords);
	insertIntoPriorityListTrack(eid,listId,"Edit records",recordsJson.toString());
}catch(Exception e){		
}
    	
    return saveJson;
}

public static HashMap<String, String> getPriListRecordsCount(String eid){
	String recCountQry="select count(*) as recCount,list_id,status from priority_list_records where eventid=CAST(? AS BIGINT) group by list_id,status";
	DBManager dbm=new DBManager();
	StatusObj stb=null;
	stb=dbm.executeSelectQuery(recCountQry, new String[]{eid});
	HashMap<String, String> recCountMap=new HashMap<String, String>();
	for(int i=0;i<stb.getCount();i++){
		String listId=dbm.getValue(i,"list_id","");
		String status=dbm.getValue(i,"status","");
		String recCount=dbm.getValue(i,"recCount","");
		if("Completed".equalsIgnoreCase(status))
		{
			if(recCountMap.containsKey(listId+"_usedCount"))
			{
				String usedCount=recCountMap.get(listId+"_usedCount");
				int count=Integer.parseInt(usedCount)+1;
				recCountMap.put(listId+"_usedCount",String.valueOf(count));
			}
			else
			{
				recCountMap.put(listId+"_usedCount","1");
			}
		}
		else
		{
			recCountMap.put(listId+"_usedCount","0");
		}
		
		if(recCountMap.containsKey(listId+"_recCount")){
			String tempRecCount=recCountMap.get(listId+"_recCount");
			int count=Integer.parseInt(recCount)+Integer.parseInt(tempRecCount);
			recCountMap.put(listId+"_recCount",String.valueOf(count));
		}else{
			recCountMap.put(listId+"_recCount",recCount);
		}
	}
	return recCountMap;
}
	
	public static String getPriorityList(String eid){
		//HashMap<String, String> recCountMap=getPriListRecordsCount(eid);
		//ArrayList<Entity> priorityList=new ArrayList<Entity>();
		ArrayList arrlist=new ArrayList();
		DBManager dbm=new DBManager();
		StatusObj stb=null;
		String recordcount="select  count(*) as recordcount,list_id  from  priority_list_records where eventid=?::bigint group by list_id";
		stb=dbm.executeSelectQuery(recordcount, new String[]{eid});
		JSONObject recordcountobj=new JSONObject();
		try{
			for(int i=0;i<stb.getCount();i++)
			{
				JSONObject eachcountobj=new JSONObject();
				eachcountobj.put("recordcount", dbm.getValue(i,"recordcount",""));
				recordcountobj.put(dbm.getValue(i,"list_id",""), eachcountobj);
			}
			}catch(Exception e){}
		System.out.println("recordcount:::"+recordcountobj.toString());
		
		
		
		String soldcount="select count(distinct field1) as soldcount,list_id from priority_reg_transactions where eventid=?::bigint and status='Completed' group by  list_id";
		stb=dbm.executeSelectQuery(soldcount, new String[]{eid});
		JSONObject soldcountobj=new JSONObject();
		try{
		for(int i=0;i<stb.getCount();i++)
		{
			JSONObject eachcountobj=new JSONObject();
			
			eachcountobj.put("soldcount", dbm.getValue(i,"soldcount",""));
			soldcountobj.put(dbm.getValue(i,"list_id",""), eachcountobj);
		}
		}catch(Exception e){}
		System.out.println("soldcount:::"+soldcountobj.toString());
		
		
		String listquery="select list_id,list_name,tickets,nooffields,field1,field2 from priority_list where eventid=?::bigint order by list_id";
				
				stb=dbm.executeSelectQuery(listquery, new String[]{eid});
				if(stb.getStatus() && stb.getCount()>0)
					arrlist=EventDB.getEventTicketsForDiscounts(eid);
				
				HashMap hm=new HashMap();
				
				for(int i=0;i<arrlist.size();i++){
					Entity en=(Entity)arrlist.get(i);
					hm.put(en.getKey(),en.getValue());
				}
				System.out.println(hm);
				JSONObject finalObj=new JSONObject();
				JSONArray jsonarr=new JSONArray();
				try{
				for(int i=0;i<stb.getCount();i++)
				{
					JSONObject eachObj=new JSONObject();
					eachObj.put("listNm",dbm.getValue(i,"list_name",""));
					eachObj.put("noOfFlds",dbm.getValue(i,"nooffields",""));
					eachObj.put("field1",dbm.getValue(i,"field1",""));
					eachObj.put("field2",dbm.getValue(i,"field2",""));
					String tktIDs=dbm.getValue(i,"tickets","");
					
					ArrayList Tktlist=getTicketsData(tktIDs);
					HashMap tempmap=new HashMap();
					for(int j=0;j<Tktlist.size();j++){
						tempmap.put(Tktlist.get(j),hm.get(Tktlist.get(j)));
					}
					if(tempmap.size()>0)
						eachObj.put("tkts", tempmap);
					
					String listid=dbm.getValue(i,"list_id","");
					
					if(recordcountobj.has(listid))
						eachObj.put("recCount",recordcountobj.getJSONObject(listid).get("recordcount"));
					else
						eachObj.put("recCount","0");
					
					if(soldcountobj.has(listid))
						eachObj.put("usdCount",soldcountobj.getJSONObject(listid).get("soldcount"));
					else
						eachObj.put("usdCount","0");
					
					/*eachObj.put("recCount", recCountMap.get(dbm.getValue(i,"list_id","")+"_recCount"));
					System.out.println(recCountMap.get(dbm.getValue(i,"list_id","")+"_usedCount")+"::sold count");
					eachObj.put("usdCount", recCountMap.get(dbm.getValue(i,"list_id","")+"_usedCount"));*/
					jsonarr.put(new JSONObject().put(dbm.getValue(i,"list_id",""), eachObj));
				}
				
				finalObj.put("lists", jsonarr);
				//finalObj.put("listrecords", new JSONObject(recCountMap));
				finalObj.put("status","success");
				}catch(Exception e){
					
				}
		
				System.out.println("final object is::"+finalObj);
				
	return finalObj.toString();
	}
	
	public static ArrayList getTicketsData(String tktIDs){
		
		//ArrayList items = (ArrayList)Arrays.asList(tktIDs.split("\\s*,\\s*"));
		
		ArrayList items = new ArrayList<String>(Arrays.asList(tktIDs.split(",")));
		
		System.out.println("items::::"+items);
	return items;
	}
	
	
	//use this query to get records in single query
	//select  distinct  id,  case when id in(select field1 from priority_reg_transactions where status='Completed' and list_id='1243')  then   'Completed'  else  'Not Completed' end, a.list_id from priority_list_records a left join (select distinct field1,list_id,eventid from  priority_reg_transactions where  status='Completed')b on a.list_id=b.list_id where a.list_id='1243'
	
	public static JSONObject getPriorityListData(String eid,String listId){
        DBManager dbm=new DBManager();
        StatusObj stb=null;
        String status="false";        
        String usedmembersqry="select count(*),field1 from priority_reg_transactions  where status='Completed' and list_id=? group by field1,updated_at order by updated_at";
        stb=dbm.executeSelectQuery(usedmembersqry, new String[]{listId});    
        JSONObject finalObj=new JSONObject();
        JSONArray prArray=new JSONArray();
        try
        {
        if(stb.getStatus() && stb.getCount()>0)
        {
            for(int i=0;i<stb.getCount();i++)
            {    status="true";
            	 JSONObject eachObj=new JSONObject();
            	 eachObj.put("id",dbm.getValue(i,"field1",""));
            	 eachObj.put("status","USED");
            	 prArray.put(eachObj);
            }            

            String fieldname=DbUtil.getVal("select field1 from priority_list  where list_id=?", new String[]{listId});
            finalObj.put("fieldname",fieldname);
            
            finalObj.put("listdata",prArray);
            finalObj.put("status",status);
        }
        else
        {
            finalObj.put("status",status);
        }
        
        }catch(Exception e){
            System.out.println("exception occured in getPriorityListData Method:"+e.getMessage());
        }
        System.out.println(finalObj+"::final");
    return finalObj;
    }
	
	public static HashMap<String,String> getPriorityFields(String eid){
		
		String fileds="select nooffields,field1,field2 from priority_list where eventid=?::bigint";
		DBManager dbm=new DBManager();
		StatusObj stb=null;
		HashMap<String,String> fieldMap=new HashMap<String,String>();
		stb=dbm.executeSelectQuery(fileds, new String[]{eid});
		if(stb.getStatus() && stb.getCount()>0){
			fieldMap.put("nooffields",dbm.getValue(0,"nooffields",""));
			fieldMap.put("field1",dbm.getValue(0,"field1",""));
			fieldMap.put("field2",dbm.getValue(0,"field2",""));
			
		}
		return fieldMap;
	}
	
	public static JSONObject getEditPriorityListData(String eid,String listId){
		JSONObject finalObj=new JSONObject();
		try{
		DBManager dbm=new DBManager();
		String listquery="select list_id,list_name,nooffields,field1,field2,count,tickets from priority_list where eventid=?::bigint and list_id=?";
		
		//listquery="select count(*) as cnt,list_name,list_id,tickets from priority_list_records where eventid=?::bigint and list_id=? group by list_name,list_id,tickets";
				StatusObj stb=null;
				stb=dbm.executeSelectQuery(listquery, new String[]{eid,listId});
				if(stb.getStatus() && stb.getCount()>0){
					finalObj.put("listid",dbm.getValue(0,"list_id",""));
					finalObj.put("listname",dbm.getValue(0,"list_name",""));
				//	finalObj.put("count",dbm.getValue(0,"cnt",""));
					finalObj.put("tickets",dbm.getValue(0,"tickets",""));
					finalObj.put("nooffields",dbm.getValue(0,"nooffields",""));
					finalObj.put("field1",dbm.getValue(0,"field1",""));
					finalObj.put("field2",dbm.getValue(0,"field2",""));
				}
		}catch(Exception e){
			System.out.println("exception occured in getEditPriorityListData Method:"+e.getMessage());
		}		
	return finalObj;
	}
	
	public static void savePriorityFields(String eid,String fieldcount,String field1,String field2){
		
		String isexists=DbUtil.getVal("select 'y' from priority_list where eventid=?::bigint",new String[]{eid});
		isexists=isexists==null?"":isexists;
		if("y".equals(isexists)){
			String updatequery="update priority_list set nooffields=?,field1=?,field2=? where eventid=?::bigint";
			DbUtil.executeUpdateQuery(updatequery,new String[]{fieldcount,field1,field2,eid});
		}else{
			String updatequery="insert into priority_list(nooffields,field1,field2,eventid)values(?,?,?,?::bigint);";
			DbUtil.executeUpdateQuery(updatequery,new String[]{fieldcount,field1,field2,eid});
		}
	}
	
	public static String getPriorityexists(String eid){
		String isexists=DbUtil.getVal("select 'yes' from priority_list where eventid=?::bigint",new String[]{eid});
		if(isexists==null)
			isexists="no";
		else
			isexists="yes";
		return isexists;
	}
	
	public static String getNoOfFields(String eid,String listid){
		String fieldcount=DbUtil.getVal("select nooffields from priority_list where eventid=?::bigint and list_id=?",new String[]{eid,listid});
		System.out.println("filed count::"+fieldcount);
		return fieldcount;
	}
	
	
	public static ArrayList<HashMap<String,String>> getMemberRecords(String eid, String listId){
		ArrayList<HashMap<String,String>> memberDataList=new ArrayList<HashMap<String,String>>();
		try{
			HashMap<String,String>tempMap=new HashMap<String,String>();
			DBManager dbm=new DBManager();
			String listquery="select id,password from priority_list_records where eventid=?::bigint and list_id=?";
			StatusObj stb=null;
			stb=dbm.executeSelectQuery(listquery, new String[]{eid,listId});
			for(int i=0;i<stb.getCount();i++){
				tempMap=new HashMap<String,String>();
				tempMap.put("userid",dbm.getValue(i,"id",""));
				tempMap.put("password",dbm.getValue(i,"password",""));
				memberDataList.add(tempMap);
			}
		}catch(Exception e){
			
		}
		return memberDataList;
	}
	
	public static void setPriorityConfiguration(String eventid){
		String insertQry="insert into config(name,value,config_id) select 'event.priority.enabled','Y',config_id from eventinfo where eventid=CAST(? AS BIGINT)";
		String selQry="select value from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.priority.enabled'";
		String value=DbUtil.getVal(selQry, new String[]{eventid});
		if(!"Y".equalsIgnoreCase(value)){
			DbUtil.executeUpdateQuery(insertQry, new String[]{eventid});
			EventHelperDB.removeGLobalEventCache(eventid, "remove", "eventinfo");
		}
	}
	
	/*public static String getPriorityToken(){
		String priority_reg_seq=DbUtil.getVal("select nextval('seq_priority_reg')", new String[] {});
		String priRegToken = "PR"+EncodeNum.encodeNum(priority_reg_seq).toUpperCase();
		return priRegToken;
	}*/
	
	public static void deleteOldRecords(String listId, String eid)
	{
		System.out.println(listId+"............."+eid);
	//	String purpose="delete";
		/*String updatequery="delete from priority_list_records where eventid=?::bigint and list_id=?";
		DbUtil.executeUpdateQuery(updatequery,new String[]{eid,listId});*/
		DbUtil.executeUpdateQuery("delete from priority_list_records where list_id=? and eventid=cast(? as bigint)",new String[]{listId,eid});
		
		System.out.println("deleted successfully");

		
	}
	
	public static void removePriorityConfiguration(String eventid){
		String delQry="delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.priority.enabled'";
		DbUtil.executeUpdateQuery(delQry, new String[]{eventid});
	}
	
	public static void insertIntoPriorityListTrack(String eid, String listId, String purpose, String updatedData){
		String insertTrackQry="insert into priority_list_edit_track(eventid,listid,action,updated_data,updated_at) values(CAST(? AS BIGINT),?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
		DbUtil.executeUpdateQuery(insertTrackQry, new String[]{eid,listId,purpose,updatedData,DateUtil.getCurrDBFormatDate()});
	}

}
