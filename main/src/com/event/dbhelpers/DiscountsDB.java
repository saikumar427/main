package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import com.event.beans.DiscountData;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.eventbee.beans.DateTimeBean;
import com.eventbee.general.*;
import com.eventbee.namedquery.NQDbHelper;


public class DiscountsDB {
	private static Logger log = Logger.getLogger(DiscountsDB.class);
	static ArrayList discountsList = new ArrayList();

	public static JSONObject getEventDiscountsList(String eid,String eventURL) {
		// TODO Auto-generated method stub
		/*
		 * This method is used to populate the discount details that are already
		 * existed for the given event id
		 */
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList <HashMap<String,Object>> discountsList = new ArrayList <HashMap<String,Object>>();
		
		
		String currencySymbol=DbUtil.getVal("select currency_symbol from currency_symbols where currency_code=(select currency_code from event_currency where eventid=?)",new String[]{eid});
		  if(currencySymbol==null)
			  currencySymbol="$";
		
		HashMap<String,ArrayList<String>> ticketsMap=new HashMap<String,ArrayList<String>>();
		HashMap<String,HashMap<String,String>> codesMap=new HashMap<String,HashMap<String,String>>();
		HashMap<String,String> ticketsNameMap=new HashMap<String,String>();
		HashMap<String,String> discountSoldMap=new HashMap<String,String>();
		
		
		ticketsMap=getDiscountTicketMap(eid);
		codesMap=getDiscountCodeMap(eid);
		ticketsNameMap=getTicketNames(eid);
		discountSoldMap=getDiscountSoldInfo(eid);
		
		
		String query="select *,to_char(exp_date,'Dy Mon DD') as date from coupon_master where groupid=? order by created_at desc";
		      statobj=dbmanager.executeSelectQuery(query,new String[]{eid});
		
		      if(statobj.getStatus() && statobj.getCount()>0){
		    	  
		    	  for(int i=0;i<statobj.getCount();i++){
		    		  HashMap<String,Object> eachCouponMap=new HashMap<String,Object>();
		    		  eachCouponMap.put("discountId", dbmanager.getValue(i,"couponid",""));
		    		  eachCouponMap.put("discountURL",eventURL);
		    		  eachCouponMap.put("codes",codesMap.get(dbmanager.getValue(i,"couponid","")));
		    		  eachCouponMap.put("tickets",ticketsMap.get(dbmanager.getValue(i,"couponid",""))==null?new ArrayList<String>():ticketsMap.get(dbmanager.getValue(i,"couponid",""))); 
		    		  eachCouponMap.put("discounttype",dbmanager.getValue(i,"discounttype",""));
		    		  eachCouponMap.put("dicsountname",dbmanager.getValue(i,"name",""));
		    		  eachCouponMap.put("noofcodes",codesMap.get(dbmanager.getValue(i,"couponid","")).size());
		    		  eachCouponMap.put("eachlimitcode",dbmanager.getValue(i,"eachcode_limit",""));
		    		  
		    		  eachCouponMap.put("exptype",dbmanager.getValue(i,"exp_type",""));
		    		  
		    		  if("Y".equals(dbmanager.getValue(i,"exp_type","")))
		    			  eachCouponMap.put("expdate",dbmanager.getValue(i,"date",""));
		    		  
		    		  
		    		  if("Y".equals(dbmanager.getValue(i,"eachcode_limit",""))){
		    			  for (Entry entry : codesMap.get(dbmanager.getValue(i,"couponid","")).entrySet()) {
		    				    eachCouponMap.put("codetype","eachcodelimit");
		    				    eachCouponMap.put("discountcount",entry.getValue());
		    				    
		    				    System.out.println("entry.getValue():"+entry.getValue());
		    				    break;
		    				}
		    		  }else{
		    			  String limit="";
		    			  for (Entry entry : codesMap.get(dbmanager.getValue(i,"couponid","")).entrySet()) {
		    				  limit=entry.getValue().toString();
		    				    break;
		    				}
		    			  
		    			  if("100000".equals(limit)){
		    				  eachCouponMap.put("codetype","nolimit");
		    				  eachCouponMap.put("discountcount",limit);
		    			  }else{
		    				  eachCouponMap.put("codetype","allcodelimit");
		    				  eachCouponMap.put("discountcount",limit);
		    			  }
		    			  
		    			  
		    		  }
		    		
		    		  String discountValue=dbmanager.getValue(i,"discount","");
		    		  String discounttype=dbmanager.getValue(i,"discounttype","");
		    		  
		    		  if("PERCENTAGE".equalsIgnoreCase(discounttype)) discountValue=discountValue+"%";
					  if("ABSOLUTE".equalsIgnoreCase(discounttype)) discountValue=currencySymbol+discountValue;
						eachCouponMap.put("discountValue",discountValue);
		    		  
						discountsList.add(eachCouponMap);
		    	  }
		    	  
		      }
		      
		      
		
		/*List list = dbhelper.getDataList("select_coupon_master", inParams);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				DiscountData tempDiscountDataobj = new DiscountData();
				HashMap hmap = (HashMap) list.get(i);
				if (hmap.containsKey("couponid")) {
					tempDiscountDataobj.setId(hmap.get("couponid").toString());
				}
				if (hmap.containsKey("name")) {
					tempDiscountDataobj.setName(hmap.get("name").toString());
				}
				if (hmap.containsKey("discounttype")) {
					tempDiscountDataobj.setDiscountType(hmap.get("discounttype").toString());
				}
				String codesstr="";
				String code="";
				HashMap codes=getCodes(hmap.get("couponid").toString());
				for(int k=0;k<codes.size();k++){
				code=(String)codes.get("couponcode"+k);
				if(!"".equals(codesstr))
				codesstr=codesstr+","+code; 
				else
				codesstr=code;
				}
				tempDiscountDataobj.setDiscountCode(codesstr);
				tempDiscountDataobj.setDiscountTickets(ticketsMap.get(hmap.get("couponid").toString()));
				discountsList.add(tempDiscountDataobj);
			}
		}*/
		      JSONObject jsonObj=new JSONObject();
		      try{
		      jsonObj.put("DiscountDetails", discountsList);
		      jsonObj.put("TicketNames",ticketsNameMap);
		      jsonObj.put("discountsSold",discountSoldMap);
		      }catch(Exception e){
		    	  
		      }
		System.out.println("id size" + DiscountsDB.discountsList.size());
		return jsonObj;
	}
	
	
	public static HashMap<String,ArrayList<String>> getDiscountTicketMap(String eid){
		
		String ticketQuery="select couponid,price_id from coupon_ticket where couponid in(select couponid from coupon_master where groupid=?)";
		DBManager dbm=new DBManager();
		StatusObj sbj=dbm.executeSelectQuery(ticketQuery,new String[]{eid});
		HashMap<String,ArrayList<String>> ticketMap=new HashMap<String,ArrayList<String>>();
		if(sbj.getStatus() && sbj.getCount()>0){
		for(int i=0;i<sbj.getCount();i++){
			ArrayList<String> eachList=new ArrayList<String>();
			String couponid=dbm.getValue(i,"couponid","");
			if(ticketMap.containsKey(couponid)){
				eachList=ticketMap.get(couponid);
				eachList.add(dbm.getValue(i,"price_id",""));
				ticketMap.put(couponid, eachList);
			}else{
				eachList.add(dbm.getValue(i,"price_id",""));
				ticketMap.put(couponid, eachList);
			}
		}	
		
		}
		
	return ticketMap;
	}
	
	 public static HashMap<String,HashMap<String,String>> getDiscountCodeMap(String eid){
		
		String ticketQuery="select couponid,couponcode,maxcount from coupon_codes where couponid in(select couponid from coupon_master where groupid=?)";
		DBManager dbm=new DBManager();
		StatusObj sbj=dbm.executeSelectQuery(ticketQuery,new String[]{eid});
		HashMap<String,HashMap<String,String>> codeMap=new HashMap<String,HashMap<String,String>>();
		if(sbj.getStatus() && sbj.getCount()>0){
		for(int i=0;i<sbj.getCount();i++){
			HashMap<String,String> eachMap=new HashMap<String,String>();
			String couponid=dbm.getValue(i,"couponid","");
			if(codeMap.containsKey(couponid)){
				eachMap=codeMap.get(couponid);
				eachMap.put(dbm.getValue(i,"couponcode",""),dbm.getValue(i,"maxcount",""));
				codeMap.put(couponid, eachMap);
			}else{
				eachMap.put(dbm.getValue(i,"couponcode",""),dbm.getValue(i,"maxcount",""));
				codeMap.put(couponid, eachMap);
			}
		}	
		
		}
		
	return codeMap;
	}
	 
	 public static HashMap<String,String> getTicketNames(String eid){
		 String query="select price_id,ticket_name from price where evt_id=?::bigint";
		 DBManager dbm=new DBManager();
		 HashMap<String,String> ticketMap=new HashMap<String,String>();
		 StatusObj stb=dbm.executeSelectQuery(query,new String[]{eid});
		 if(stb.getStatus() && stb.getCount()>0){
			 for(int i=0;i<stb.getCount();i++)
  			 ticketMap.put(dbm.getValue(i,"price_id",""),dbm.getValue(i,"ticket_name",""));
		 }
		 return ticketMap;
	 }
	 
	 public static HashMap<String,String> getDiscountSoldInfo(String eid){
		 String discountQuery="select sum(tt.ticketqty) as sum,et.discountcode from transaction_tickets tt,event_reg_transactions et where et.tid=tt.tid and tt.eventid=? and tt.discount>0 group by discountcode";
		 DBManager dbm=new DBManager();
		 HashMap<String,String> discountMap=new HashMap<String,String>();
		 StatusObj stb=dbm.executeSelectQuery(discountQuery,new String[]{eid});
		 if(stb.getStatus() && stb.getCount()>0){
			 for(int i=0;i<stb.getCount();i++)
				 discountMap.put(dbm.getValue(i,"discountcode",""),dbm.getValue(i,"sum",""));
		 }
			
		return discountMap;
	 }
	 
	
	
	
	
	
	public static int getCount(String eid){
		int count=0;
		try{
           count=Integer.parseInt(DbUtil.getVal("select count(*) from price where evt_id=?",new String[]{eid}));
		}catch(Exception e){
			count=0;
		}
            return count;
    }
	

	/**
	 * 
	 * @param discountData
	 * @param eid
	 */
	public static void saveDiscountData(DiscountData discountData, String eid) {
		String description = discountData.getDescription();
		String discountval = discountData.getDiscountVal();
		String discountType = discountData.getDiscountType();
		String limitType = discountData.getLimitType();
		String limitValue = "";
		String mgrid = "123";
		String discountCodeCSV = discountData.getDiscountCodescsv();
		String eachcodelimitvalue="";
		System.out.println("limitType" + limitType);
		if ("1".equals(limitType)){
			limitValue = "100000";
			eachcodelimitvalue="N";
		}	
		else if("2".equals(limitType)){
			limitValue=discountData.getAllLimitValue().trim();
			eachcodelimitvalue="N";
		}else if("3".equals(limitType)){
			limitValue=discountData.getEachLimitValue().trim();
			eachcodelimitvalue="Y";
		}
		System.out.println("limitValue is:" + limitValue);
		System.out.println("eachcodelimitvalue is:" +eachcodelimitvalue);
		String discountName = discountData.getName();
		ArrayList codes = discountData.getCodes();
		String groupType = "Event";
		String couponId = discountData.getId();
		
		String userTimeZone = EventDB.getEventTimeZone(eid);
		String toTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		DateConverter dc=new DateConverter(userTimeZone,toTimeZone);
		DateTimeBean dtb=discountData.getExpDateTimeBeanObj();
		boolean statusdc=dc.convertDateTime(dtb);
		String expDate=dc.getDatePart();
		String expTime=dc.getTimePart();
		
		HashMap inParams = new HashMap();
		//inParams.put("managerId", mgrid);
		//inParams.put("groupType", groupType);
		//inParams.put("name", discountName);
		//inParams.put("description", description);
		//inParams.put("discount", discountval);
		//inParams.put("updated_by", "Manager");
		//inParams.put("discounttype", discountType);
		//inParams.put("coupontype", "General");
		//inParams.put("groupid", eid);
		//inParams.put("couponid", couponId);
		String expType=discountData.getExpType();
				
		if ("".equals(discountData.getId())) {
			NQDbHelper dbh = new NQDbHelper();
			inParams.put("reqSeq", "seq_couponid");
			couponId = dbh.getDataString("getNewSequence", inParams);
			log.info("couponid : " + couponId);
			inParams.put("couponid", couponId);
			log.info("Inserting... discount data");
			/*com.eventbee.namedquery.NQStatusObj statobj = com.eventbee.namedquery.NQDbUtil.executeQuery(
					"insert_coupon_master", inParams);
			log.info("Insert Status: " + statobj.getStatus());*/
			
			String insert_coupon="insert into coupon_master(managerId,groupId,groupType,couponId,name,description,discount,activationDate,cutOffDate,created_by," +
					"created_at,updated_by,updated_at,discounttype,coupontype,eachcode_limit,exp_type,exp_date,exp_time)" +
					"values(?,?,?,?,?,?,?,now(),now(),'coupon',now(),'coupon',now(),?,'General',?,?,?,?)";
			
			StatusObj stobj = DbUtil.executeUpdateQuery(insert_coupon,new String[]{mgrid,eid,groupType,couponId,discountName,description,discountval,discountType,eachcodelimitvalue,expType,expDate,expTime});
			log.info("Insert Status: " + stobj.getStatus());
		} else {
			log.info("Updating... discount data");
			/*com.eventbee.namedquery.NQStatusObj statobj = com.eventbee.namedquery.NQDbUtil.executeQuery(
					"update_coupon_master", inParams);
			log.info("Update Status: " + statobj.getStatus());*/
			String update_coupon="update coupon_master set managerId=?,groupType=?,name=?,description=?,discount=?,updated_by='Manager',updated_at=now(),discounttype=?,coupontype='General',eachcode_limit=?,exp_type=?,exp_date=?,exp_time=? where groupid=? and couponid=?";
			StatusObj stobj = DbUtil.executeUpdateQuery(update_coupon,new String[]{mgrid,groupType,discountName,description,discountval,discountType,eachcodelimitvalue,expType,expDate,expTime,eid,couponId});
		}
		//DbUtil.executeUpdateQuery("update coupon_master set eachcode_limit=? where groupid=? and couponid=?",new String[]{eachcodelimitvalue,eid,couponId});
		ArrayList selectedTickets = discountData.getSeltickets();
		couponCodesInsert(couponId, discountCodeCSV, limitValue);
		ticketsInsert(selectedTickets, couponId);
	}

	public static DiscountData getDiscountData(String eid, String id) {
		// select query to fetch the discount codes from DB for this event id
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		DiscountData tempDiscountDataobj = new DiscountData();

		/*HashMap inParams = new HashMap();
		inParams.put("groupid", eid);
		inParams.put("couponid", id);
		NQDbHelper dbhelper = new NQDbHelper();
		HashMap hmap = dbhelper.getDataHash("select_discountdata", inParams);
		String eachcodelimit=DbUtil.getVal("select eachcode_limit from coupon_master where groupid=? and couponid=?",new String[]{eid,id});
		if(eachcodelimit==null)eachcodelimit="N";*/
		
		String select_discountdata="select coupon_master.couponid,name,description,discount,discounttype,coupontype as limittype," +
				"maxcount,eachcode_limit,exp_type,exp_date,exp_time from coupon_master,coupon_codes where groupid=? and " +
				"coupon_master.couponid=? and coupon_codes.couponid=coupon_master.couponid";
		
		statobj=dbmanager.executeSelectQuery(select_discountdata, new String[]{eid,id});
		
		if (statobj.getStatus() && statobj.getCount()>0) {
			String eachcodelimit=dbmanager.getValue(0,"eachcode_limit","");
			if("".equals(eachcodelimit) || eachcodelimit==null)eachcodelimit="N";
			String couponid = dbmanager.getValue(0,"couponid","");
			tempDiscountDataobj.setId(couponid);
			tempDiscountDataobj.setDiscountType(dbmanager.getValue(0,"discounttype",""));
			tempDiscountDataobj.setName(dbmanager.getValue(0,"name",""));
			//tempDiscountDataobj.setLimitType(hmap.get("limittype").toString());
			tempDiscountDataobj.setDiscountVal(dbmanager.getValue(0,"discount",""));
			tempDiscountDataobj.setDescription(dbmanager.getValue(0,"description",""));
			
			if("Y".equals(eachcodelimit)){
				tempDiscountDataobj.setLimitType("3");
				tempDiscountDataobj.setEachLimitValue((dbmanager.getValue(0,"maxcount","")));
			}else if("N".equals(eachcodelimit)){
				if ("100000".equals(dbmanager.getValue(0,"maxcount","")))
				    tempDiscountDataobj.setLimitType("1");
				else{
					 tempDiscountDataobj.setLimitType("2");
					 tempDiscountDataobj.setAllLimitValue((dbmanager.getValue(0,"maxcount","")));
				   }   
			}
			
			if(!"".equals(dbmanager.getValue(0,"exp_date","")) && !"".equals(dbmanager.getValue(0,"exp_time",""))){
				tempDiscountDataobj.setExpType(dbmanager.getValue(0,"exp_type","N"));
				String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
				String userTimeZone = EventDB.getEventTimeZone(eid);
				DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
				boolean statusdc=dc.convertDateTime(dbmanager.getValue(0,"exp_date",""),dbmanager.getValue(0,"exp_time",""),userTimeZone);
				DateTimeBean expdtb=dc.getDateTimeBeanObj();
				tempDiscountDataobj.setExpDateTimeBeanObj(expdtb);
			}else{
				getDiscountDefaultExpDate(tempDiscountDataobj,eid);
			}
			ArrayList selectedTickets = getSelectedTickets(eid, couponid);
			String codesCSV = getCodesCSV(eid, couponid);
			tempDiscountDataobj.setDiscountCodescsv(codesCSV);
			tempDiscountDataobj.setSeltickets(selectedTickets);
		}
		return tempDiscountDataobj;
	}

	public static ArrayList getSelectedTickets(String eid, String id) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList selt = new ArrayList();
		HashMap inParams = new HashMap();
		inParams.put("couponid", id);

		NQDbHelper dbhelper = new NQDbHelper();
		List list = dbhelper.getDataList("select_coupon_ticket", inParams);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				HashMap hmap = (HashMap) list.get(i);
				selt.add(hmap.get("price_id").toString());
			}
		}
		/*
		 * StringselTicketQuery=
		 * "select couponid,price_id from coupon_ticket where couponid=?";
		 * statobj=dbmanager.executeSelectQuery(selTicketQuery,new String
		 * []{id}); int count1=statobj.getCount();
		 * if(statobj.getStatus()&&count1>0){ for(int k=0;k<count1;k++){ String
		 * priceid=dbmanager.getValue(k,"price_id",""); selt.add(priceid); } }
		 */
		return selt;
	}

	public static String getCodesCSV(String eid, String id) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		StringBuffer codecsv = new StringBuffer();
		HashMap inParams = new HashMap();
		inParams.put("couponid", id);

		NQDbHelper dbhelper = new NQDbHelper();
		List list = dbhelper.getDataColumnList("select_coupon_codes", inParams);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (i == 0)
					codecsv = codecsv.append(list.get(i));
				else
					codecsv = codecsv.append("," + list.get(i));
			}
		}
		/*
		 * StringselTicketQuery=
		 * "select couponcode,maxcount from coupon_codes where couponid=?";
		 * statobj=dbmanager.executeSelectQuery(selTicketQuery,new String
		 * []{id}); int count1=statobj.getCount();
		 * if(statobj.getStatus()&&count1>0){
		 * codecsv=codecsv.append(dbmanager.getValue(0,"couponcode",""));
		 * for(int k=1;k<count1;k++){ String
		 * couponcode=dbmanager.getValue(k,"couponcode","");
		 * codecsv.append(","+couponcode);
		 * 
		 * } }
		 */

		return codecsv.toString();
	}

	public static void couponCodesInsert(String couponId,
			String discountCodeCSV, String limitValue) {
		StringTokenizer st = new StringTokenizer(discountCodeCSV, ",");
		String tempcode = "";
		HashMap inParams = new HashMap();
		inParams.put("couponId", couponId);		
		com.eventbee.namedquery.NQDbUtil.executeQuery("delete_coupon_codes", inParams);
		//String query = "insert into coupon_codes (couponId,couponCode,maxcount) values (?,?,to_number(?,'999999999'))";
		Set<String> set = new HashSet<String>();
		while (st.hasMoreTokens()) {
			tempcode = (String) st.nextToken();
			if(set.add(tempcode)){// to remove duplicate codes in CSV
				inParams.put("couponCode", tempcode.trim());
				inParams.put("maxcount", limitValue);
				com.eventbee.namedquery.NQDbUtil.executeQuery("insert_coupon_codes", inParams);
			}
			/*DbUtil.executeUpdateQuery(query, new String[] { couponId,
					tempcode.trim(), limitValue });*/

		}
	}

	public static void ticketsInsert(ArrayList selectedTickets, String couponId) {		
		HashMap inParams = new HashMap();
		inParams.put("couponId", couponId);		
		com.eventbee.namedquery.NQDbUtil.executeQuery("delete_coupon_ticket", inParams);
		for (int i = 0; i < selectedTickets.size(); i++) {
			System.out.println("tid" + selectedTickets.get(i));
			String ticketId = (String) selectedTickets.get(i);
			inParams.put("price_id", ticketId);
			com.eventbee.namedquery.NQDbUtil.executeQuery("insert_coupon_ticket", inParams);
			/*String couponTicketInsertQuery = "insert into coupon_ticket (couponId,price_id) values (?,to_number(?,'9999999999'))";
			DbUtil.executeUpdateQuery(couponTicketInsertQuery, new String[] {
					couponId, ticketId });*/

		}
	}
	
	public static boolean deleteDiscountData(String eid,String couponId){
		com.eventbee.namedquery.NQStatusObj statobj = new com.eventbee.namedquery.NQStatusObj(false, "", "0", 0);
		try{
			String namedqueries[] = {"delete_coupon_codes","delete_coupon_ticket","delete_coupon_master"};
			HashMap inParams = new HashMap();
			inParams.put("couponId", couponId);
			for(int i=0;i<namedqueries.length;i++){
				com.eventbee.namedquery.NQDbUtil.executeQuery(namedqueries[i], inParams);
			}
		}catch(Exception ex){
			log.error("Exception in delete discount data: "+ex);
		}
		return statobj.getStatus();
	}
	
	public static List validateCodescsv(String eid, String cid, String discountCodeCSV){
		List errlist = new ArrayList();
		try{			
			NQDbHelper dbhelper = new NQDbHelper();
			String namedQuery = "select_coupon_codes_foradd";
			HashMap pInParams = new HashMap();
			pInParams.put("groupid", eid);
			if(!"".equals(cid)){
				namedQuery = "select_coupon_codes_foredit";
				pInParams.put("couponid", cid);
			}
			List list = dbhelper.getDataColumnList(namedQuery, pInParams);
			String codes[] = discountCodeCSV.split(",");
			for(int i=0;i<codes.length;i++){
				for(int j=0;j<list.size();j++){
					if(codes[i].trim().equals(list.get(j).toString().trim())){
						errlist.add(codes[i].trim());
					}
				}
			}
			return errlist;
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return errlist;
	}
	public static String populateEventURL(String eid){
		String eventURL=DbUtil.getVal("select url from event_custom_urls where eventid=?",new String[]{eid});
		String username=DbUtil.getVal("select login_name from authentication where user_id= cast((select mgr_id from eventinfo where " +
				" eventid=CAST(? AS BIGINT)) as varchar)",new String[]{eid});
		if(eventURL==null){
			eventURL=ShortUrlPattern.get(username)+"/event?eventid="+eid;
			eventURL=eventURL+"&code=";
		 }
		 else{
			 eventURL=eventURL+"/discount?code=";
		 }
		return eventURL;
	}
	public static HashMap getCodes(String couponid){
		DBManager db=new DBManager();
		HashMap hm=new HashMap();
		String query="select couponcode from coupon_codes where couponid=?";
		StatusObj sb=db.executeSelectQuery(query,new String[]{couponid});
		if(sb.getStatus())
		{
		for(int i=0;i<sb.getCount();i++){
		hm.put("couponcode"+i,db.getValue(i,"couponcode",""));
		}
		}
		return hm;
		}
	
	public static void getDiscountDefaultExpDate(DiscountData discountData,String eid){
		
		String selectTicketEndDate="select to_char(date,'yyyy') as teyr," +
				"to_char(date,'mm') as temm," +
				"to_char(date,'dd') as tedd," +
				"to_char(date,'hh') as tehh," +
				"to_char(date,'mi') as temi," +
				"to_char(date,'am') as teampm " +
				"from (select (end_date + COALESCE(endtime, '00:00')::text::time without time zone-interval '0 hour' ) as date " +
				"from eventinfo where eventid=CAST(? AS BIGINT)) discount_exp_date";
		DateTimeBean dtbObj=new DateTimeBean();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(selectTicketEndDate,new String []{eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			dtbObj.setYyPart(dbmanager.getValue(0,"teyr",""));
			dtbObj.setMonthPart(dbmanager.getValue(0,"temm",""));
			dtbObj.setDdPart(dbmanager.getValue(0,"tedd",""));
			dtbObj.setHhPart(dbmanager.getValue(0,"tehh",""));
			dtbObj.setMmPart(dbmanager.getValue(0,"temi",""));
			dtbObj.setAmpm(dbmanager.getValue(0,"teampm","").toUpperCase());
		}
		discountData.setExpDateTimeBeanObj(dtbObj);
	}
}
