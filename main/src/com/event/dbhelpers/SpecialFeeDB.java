package com.event.dbhelpers;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;

public class SpecialFeeDB {
	private static Logger log=Logger.getLogger(SpecialFeeDB.class);
	public static String getSpecialFee(String eid, String mgrId, String changeLevel){
		log.info("***** Entered into getSpecialFee method eventid: "+eid);
		String servicefee="";
		String currentfee="";
		String changefee="";
		try{
			DBManager dbmanager=null;
			StatusObj statobj=null;
			String currentLevel = DbUtil.getVal("select current_level from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid});
			if(currentLevel != null && currentLevel.equals("150") && changeLevel.equals("100")){
				log.info("****** Change RSVP To Ticketing 150 --> 100 case eventid is: "+eid);
				String eventQry = "select l"+changeLevel+" from ebee_special_fee where eventid=?";
				changefee = DbUtil.getVal(eventQry, new String[]{eid});
				if(changefee == null){
					String userQry = "select l"+changeLevel+" from ebee_special_fee where userid=?";
					changefee = DbUtil.getVal(userQry, new String[]{mgrId});
				}
				
				if(changefee == null){
					String defaultQry = "select l"+changeLevel+" from ebee_special_fee where userid='0' and eventid='0'";
					changefee = DbUtil.getVal(defaultQry, null);
				}
				
				if(changefee != null)
					servicefee=changefee;
				else
					servicefee="1";
			}else if(currentLevel != null && Integer.parseInt(currentLevel) < Integer.parseInt(changeLevel)){
				log.info("****** case:1 currentlevel not null and changelevel > currentlevel case eventid is: "+eid);
				String eventQry = "select l"+currentLevel+", l"+changeLevel+" from ebee_special_fee where eventid=?";
				dbmanager=new DBManager();
				statobj=dbmanager.executeSelectQuery(eventQry,new String[]{eid});
				if(statobj.getStatus()==false){
					String userQry = "select l"+currentLevel+", l"+changeLevel+" from ebee_special_fee where userid=?";
					dbmanager=new DBManager();
					statobj=dbmanager.executeSelectQuery(userQry,new String[]{mgrId});
				}
				
				if(statobj.getStatus()==false){
					String defaultQry = "select l"+currentLevel+", l"+changeLevel+" from ebee_special_fee where userid='0' and eventid='0'";
					dbmanager=new DBManager();
					statobj=dbmanager.executeSelectQuery(defaultQry,null);
				}
				
				if(statobj.getStatus()){
						currentfee = dbmanager.getValue(0,"l"+currentLevel,"");
						changefee = dbmanager.getValue(0,"l"+changeLevel,"");
						log.info("****** case:1 eventid is: "+eid+"currentfee is: "+currentfee+" changefee is: "+changefee);
					    if(Double.parseDouble(currentfee) == Double.parseDouble(changefee))
					    	DbUtil.executeUpdateQuery("update eventinfo set current_level=?,current_fee=to_number(?,'9999999999.99') where eventid=CAST(? AS BIGINT)", new String[]{changeLevel,changefee,eid});
					    else
							servicefee=changefee;
							
				}
				
			}
			
			if(currentLevel == null){
				log.info("****** case:2 currentlevel is null case eventid is: "+eid);
				String eventQry = "select l"+changeLevel+" from ebee_special_fee where eventid=?";
				changefee = DbUtil.getVal(eventQry, new String[]{eid});
				if(changefee == null){
					String userQry = "select l"+changeLevel+" from ebee_special_fee where userid=?";
					changefee = DbUtil.getVal(userQry, new String[]{mgrId});
				}
				
				if(changefee == null){
			        String defaultQry = "select l"+changeLevel+" from ebee_special_fee where userid='0' and eventid='0'";
					changefee = DbUtil.getVal(defaultQry, null);
				}
				if(changefee != null)
					servicefee=changefee;
				else if(changeLevel.equals("150"))
					DbUtil.executeUpdateQuery("update eventinfo set current_level=?,current_fee=to_number(?,'9999999999.99') where eventid=CAST(? AS BIGINT)", new String[]{changeLevel,"0",eid});
				else
					DbUtil.executeUpdateQuery("update eventinfo set current_level=?,current_fee=to_number(?,'9999999999.99') where eventid=CAST(? AS BIGINT)", new String[]{changeLevel,"1",eid});
				
			}
			servicefee = CurrencyFormat.getCurrencyFormat("",servicefee,false); 
			log.info("***** servicefee: "+servicefee+" eventid is: "+eid);
		}catch(Exception e){
			System.out.println("***** Exception In getSpecialFee Error: "+e.getMessage());
		}
		
		return servicefee;
	}
    
	
	
	public static void insertSpecialFee(String eid,String ttype,String fee,String source){
		log.info("***** In insertSpecialFee eventid is: "+eid+" servicefee is: "+fee+" currentlevel: "+ttype);
		try{
		String powertype=EventDB.getPowerType(eid);
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		try{
		String nonprofitstatus=DbUtil.getVal("select 'Y' from ebee_special_fee where eventid=?", new String[]{eid});
		if(nonprofitstatus==null)nonprofitstatus=DbUtil.getVal("select 'Y' from ebee_special_fee where userid::BIGINT=(select mgr_id from eventinfo where eventid=CAST(? as BIGINT))", new String[]{eid});
		if(nonprofitstatus==null)nonprofitstatus="N";
		String changelevel="";
		if("Y".equalsIgnoreCase(nonprofitstatus))changelevel="ln"+ttype;
		else changelevel="l"+ttype;
		if("Ticketing".equalsIgnoreCase(powertype) && !"100".equalsIgnoreCase(ttype)){
			String factorval="1",currencycode="USD";
			statobj=dbm.executeSelectQuery("select conversion_factor,ec.currency_code from event_currency ec,currency_symbols cs where eventid=? and ec.currency_code=cs.currency_code", new String[]{eid});
			if(statobj.getStatus() && statobj.getCount()>0){
				factorval=dbm.getValue(0,"conversion_factor", "1");
				currencycode=dbm.getValue(0,"currency_code", "USD");
			}
			
		System.out.println("changelevel is:"+changelevel);
		fee=DbUtil.getVal("select "+changelevel+" from international_pricing where currency_code=?", new String[]{currencycode});	
		fee=Double.toString(Double.parseDouble(fee)/Double.parseDouble(factorval));
		}else if("RSVP".equalsIgnoreCase(powertype))
			fee=DbUtil.getVal("select "+changelevel+" from international_pricing where currency_code='USD'", null);
		}catch(Exception e){
			System.out.println("Exception occured in insertSpecialFee method while getting factor value ::"+eid+" :: "+e.getMessage());
		}
		System.out.println("fee in insertSpecialFee is:"+fee);    
		DbUtil.executeUpdateQuery("update eventinfo set current_level=?,current_fee=CAST(? as NUMERIC) where eventid=CAST(? AS BIGINT)", new String[]{ttype,fee,eid});
		DbUtil.executeUpdateQuery("update event_service_fees set to_date=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') where eventid=? and to_date is null", new String[]{DateUtil.getCurrDBFormatDate(),eid});
		DbUtil.executeUpdateQuery("insert into event_service_fees(eventid,source,ticketing_type,from_date,usd_fee,currency_converted_fee) values(?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),CAST(? as NUMERIC),CAST(? as NUMERIC))", new String[]{eid,source,ttype,DateUtil.getCurrDBFormatDate(),fee,fee});
		}catch(Exception e){
			System.out.println("Exception occured in insertSpecialFee method for event :: "+eid +" :: source :: "+source+" :: "+e.getMessage());
		}
	}
	
	public static void chekingSpecialFee(String eid,String mgrId,String ticketingtype,String link){
		log.info("***** chekingSpecialFee in "+link+" eid: "+eid);
		try{
		String currencycode=DbUtil.getVal("select currency_code from event_currency where eventid=?", new String[]{eid});
		if(currencycode==null)currencycode="USD";
		HashMap<String, String> serviceFeeMap=getNewSpecialFee(eid,mgrId,ticketingtype,currencycode);
		if(!serviceFeeMap.isEmpty() && serviceFeeMap.get(ticketingtype)!=null && !"".equals(serviceFeeMap.get(ticketingtype)))
			insertSpecialFee(eid,ticketingtype,serviceFeeMap.get(ticketingtype),link);
		}catch(Exception e){
			System.out.println("Exception occured in chekingSpecialFee for event :: "+eid+" :: "+e.getMessage());
		}
	}
	
	public static HashMap<String,String> getEventCurrencyMap(String eid){
		HashMap<String,String> eventcurrencymap=new HashMap<String,String>();
		try{
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		statobj=dbm.executeSelectQuery("select currency_symbol,ec.currency_code from event_currency ec,currency_symbols cs where eventid=? and ec.currency_code=cs.currency_code", new String[]{eid});
		if(statobj.getStatus() && statobj.getCount()>0){
			eventcurrencymap.put("currencycode",dbm.getValue(0,"currency_code", ""));
			eventcurrencymap.put("currencysymbol",dbm.getValue(0,"currency_symbol", "$"));
		}
		}catch(Exception e){
			System.out.println("Exception occured in getEventCurrencyMap method for event :: "+eid+" :: "+e.getMessage());
		}
		return eventcurrencymap;
	}
	
	public static HashMap<String,String> getEventMap(String eid){
		HashMap<String,String> evtmap=new HashMap<String,String>();
		try{
		DBManager dbm=new DBManager();
		StatusObj statobj=null;
		statobj=dbm.executeSelectQuery("select mgr_id,ec.currency_code,conversion_factor,current_level  from eventinfo e,event_currency ec,currency_symbols cs where e.eventid=CAST(ec.eventid as BIGINT) and e.eventid=CAST(? as BIGINT) and ec.currency_code=cs.currency_code",new String[]{eid});
		if(statobj.getStatus() && statobj.getCount()>0){
			evtmap.put("mgrid", dbm.getValue(0,"mgr_id", ""));
			evtmap.put("currencycode", dbm.getValue(0,"currency_code", ""));
			evtmap.put("conversionfactor",dbm.getValue(0, "conversion_factor", "0.00"));
			evtmap.put("currentlevel",dbm.getValue(0, "current_level", ""));
		}
		}catch(Exception e){
			System.out.println("Exception occured in getEventMap method for event :: "+eid+" :: "+e.getMessage());
		}
		return evtmap;
	}
	
	public static HashMap<String, String> getServiceFeesMap(String currencycode, String nonprofitstatus,String currentLevel,String changeLevel){
		DBManager dbmanager=dbmanager=new DBManager();
		StatusObj statobj=null;
		String level="l";
		if("Y".equalsIgnoreCase(nonprofitstatus)) level="ln";
		HashMap<String, String> hm=new HashMap<String, String>();
		statobj=dbmanager.executeSelectQuery("select "+level+"90,"+level+"150,"+level+"100,"+level+"200,"+level+"300 from international_pricing where currency_code=?", new String[]{currencycode});
		if(statobj.getStatus()){
			hm.put("90",CurrencyFormat.newCurrencyFormat(Double.parseDouble(dbmanager.getValue(0,level+"90",""))));
			hm.put("150",CurrencyFormat.newCurrencyFormat(Double.parseDouble(dbmanager.getValue(0,level+"150",""))));
			hm.put("100",CurrencyFormat.newCurrencyFormat(Double.parseDouble(dbmanager.getValue(0,level+"100",""))));
			hm.put("200",CurrencyFormat.newCurrencyFormat(Double.parseDouble(dbmanager.getValue(0,level+"200",""))));
			hm.put("300",CurrencyFormat.newCurrencyFormat(Double.parseDouble(dbmanager.getValue(0,level+"300",""))));
			hm.put("cur_level", currentLevel);
			hm.put("ch_level", changeLevel);
		}else{
			
		}
		return hm;
	}
	
	public static HashMap<String, String> getNewSpecialFee(String eid,String mgrid,String changeLevel,String currencycode){
		HashMap<String, String> serviceFeeMap=new HashMap<String, String>();
		log.info("***** Entered into getNewSpecialFee method eventid: "+eid);
		String servicefee="",currentfee="",changefee="",nonprofitstatus="",cLevel="",presentLevel="";
	    try{
			DBManager dbmanager=null;
			StatusObj statobj=null;
			String currentLevel = DbUtil.getVal("select current_level from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid});
			nonprofitstatus=DbUtil.getVal("select 'Y' from ebee_special_fee where eventid=?", new String[]{eid});
			if(nonprofitstatus==null)nonprofitstatus=DbUtil.getVal("select 'Y' from ebee_special_fee where userid=?", new String[]{mgrid});
			if(nonprofitstatus==null)nonprofitstatus="N";
			if("Y".equalsIgnoreCase(nonprofitstatus))cLevel="ln"+changeLevel;
			else cLevel="l"+changeLevel;
			if("90".equalsIgnoreCase(currentLevel) || "150".equalsIgnoreCase(currentLevel) || "150".equalsIgnoreCase(changeLevel))  currencycode="USD";
			if(currencycode==null || "".equals(currencycode.trim()))currencycode="USD";
			if(currentLevel!=null && !"".equals(currentLevel)){
				if("Y".equalsIgnoreCase(nonprofitstatus))presentLevel="ln"+currentLevel;
				else presentLevel="l"+currentLevel;
			}
			if(currentLevel != null && currentLevel.equals("150") && changeLevel.equals("100")){
			log.info("****** Change RSVP To Ticketing 150 in getNewSpecialFee--> 100 case eventid is: "+eid);
			/*changefee=DbUtil.getVal("select "+cLevel+" from international_pricing where currency_code=?", new String[]{currencycode});
			if(changefee != null)
			  servicefee=changefee;
			else
			  servicefee="1";*/
				serviceFeeMap=getServiceFeesMap(currencycode,nonprofitstatus,currentLevel,changeLevel);
			}else if(currentLevel != null && Integer.parseInt(currentLevel) < Integer.parseInt(changeLevel)){
				log.info("****** case:1 currentlevel not null and changelevel > currentlevel case in getNewSpecialFee for eventid is: "+eid);
				//dbmanager=new DBManager();
				System.out.println("presentLevel is:"+presentLevel);
				System.out.println("cLevel is:"+cLevel);
				/*statobj=dbmanager.executeSelectQuery("select "+presentLevel+","+cLevel+" from international_pricing where currency_code=?", new String[]{currencycode});
				if(statobj.getStatus()){
					currentfee = dbmanager.getValue(0,presentLevel,"");
					changefee = dbmanager.getValue(0,cLevel,"");
					log.info("****** case:1 in getNewSpecialFee for eventid is: "+eid+"currentfee is: "+currentfee+" changefee is: "+changefee);
				    if(Double.parseDouble(currentfee) == Double.parseDouble(changefee))
				    	DbUtil.executeUpdateQuery("update eventinfo set current_level=?,current_fee=to_number(?,'9999999999.99') where eventid=CAST(? AS BIGINT)", new String[]{changeLevel,changefee,eid});
				    else
						servicefee=changefee;
				 }*/
				serviceFeeMap=getServiceFeesMap(currencycode,nonprofitstatus,currentLevel,changeLevel);
				if(!serviceFeeMap.isEmpty()){
					currentfee = serviceFeeMap.get(currentLevel);
					changefee = serviceFeeMap.get(changeLevel);
					System.out.println("****** case:1 in getNewSpecialFee for eventid is: "+eid+"currentfee is: "+currentfee+" changefee is: "+changefee);
					if(Double.parseDouble(currentfee) == Double.parseDouble(changefee)){
				    	DbUtil.executeUpdateQuery("update eventinfo set current_level=?,current_fee=to_number(?,'9999999999.99') where eventid=CAST(? AS BIGINT)", new String[]{changeLevel,changefee,eid});
				    	serviceFeeMap.clear();
					}
				}
			}
			
			if(currentLevel == null){
				log.info("****** case:2 currentlevel is null case in getNewSpecialFee for eventid is: "+eid);
				//changefee = DbUtil.getVal("select "+cLevel+" from international_pricing where currency_code=?", new String[]{currencycode}); 
				if("200".equals(changeLevel) || "300".equals(changeLevel)) currentLevel="100";
				else if("150".equals(changeLevel) ) currentLevel="90";
				serviceFeeMap=getServiceFeesMap(currencycode,nonprofitstatus,currentLevel,changeLevel);
				changefee = serviceFeeMap.get(changeLevel); 
				if(!serviceFeeMap.isEmpty() && changefee != null && !"".equals(changefee)){
					//servicefee=changefee;
				}else if(changeLevel.equals("150")){
					DbUtil.executeUpdateQuery("update eventinfo set current_level=?,current_fee=to_number(?,'9999999999.99') where eventid=CAST(? AS BIGINT)", new String[]{changeLevel,"0",eid});
					serviceFeeMap.clear();
				}else{
					DbUtil.executeUpdateQuery("update eventinfo set current_level=?,current_fee=to_number(?,'9999999999.99') where eventid=CAST(? AS BIGINT)", new String[]{changeLevel,"1",eid});
					serviceFeeMap.clear();
				}
				
			}
			//servicefee = CurrencyFormat.getCurrencyFormat("",servicefee,false); 
			log.info("***** serviceFeeMap: "+serviceFeeMap+" eventid is: "+eid);
			
			}catch(Exception e){
			System.out.println("***** Exception In getNewSpecialFee Error For user :: "+mgrid+" :: for event :: "+eid+" :: "+e.getMessage());
		}
		return serviceFeeMap;
	}
	
	public static String checkUpgradeStatus(String eid,String feature,String powertype,String level){
		String query="select case when e.created_at::date>f.start_date then 'Yes' else 'No' end as status from eventinfo e,feature_upgrade_dates f " +
				"where f.feature=? and f.powertype=? and f.level=? and e.eventid=CAST(? AS BIGINT) order by f.created_at desc limit 1";
		String status=DbUtil.getVal(query, new String[]{feature,powertype,level,eid});
		if(status==null) status="Yes";
		return status;
	}
}
