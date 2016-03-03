package com.mycommunities.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import com.event.dbhelpers.DiscountsDB;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.namedquery.NQDbHelper;
import com.mycommunities.beans.DiscountData;

public class CommunityDiscountDB {
	private static Logger log = Logger.getLogger(DiscountsDB.class);
	public static ArrayList<DiscountData> getEventDiscountsList(String groupId) {
		/*
		 * This method is used to populate the discount details that are already
		 * existed for the given Community
		 */
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList<DiscountData> discountsList = new ArrayList<DiscountData>();
		String query="select couponid,name,discounttype from coupon_master where groupid=? " +
				"and coupontype='CLUB_SIGNUP'";
		statobj=dbmanager.executeSelectQuery(query,new String[]{groupId});
		if(statobj !=null && statobj.getStatus()){
		 	int recordcounttodata=statobj.getCount();
		 	for(int i=0;i<recordcounttodata;i++){
			DiscountData tempDiscountDataobj = new DiscountData();
			tempDiscountDataobj.setId(dbmanager.getValue(i, "couponid", ""));
			tempDiscountDataobj.setName(dbmanager.getValue(i, "name", ""));
			tempDiscountDataobj.setDiscountType(dbmanager.getValue(i, "discounttype", ""));
			discountsList.add(tempDiscountDataobj);
			}
		}
		return discountsList;
	}

	public static void saveDiscountData(DiscountData discountData, String groupId) {
		String description = discountData.getDescription();
		String discountval = discountData.getDiscountVal();
		String discountType = discountData.getDiscountType();
		String limitType = discountData.getLimitType();
		String limitValue = discountData.getLimitValue();
		String discountCodeCSV = discountData.getDiscountCodescsv();
		System.out.println("limitType" + limitType);
		if ("1".equals(limitType))
			limitValue = "100000";
		String discountName = discountData.getName();
		System.out.println("discountName"+discountName);
		ArrayList codes = discountData.getCodes();
		String groupType = "CLUB";
		String couponType="CLUB_SIGNUP";
		String couponId = discountData.getId();
		String updatedBy="Manager";
		String userId="123";//Manager Id should come here.
		if ("".equals(discountData.getId())) {
			couponId=DbUtil.getVal("select nextval('seq_couponid') as couponid",new String[]{});
			log.info("couponid : " + couponId);
			log.info("Inserting... discount data");
			String insertQry="insert into coupon_master(managerId,groupId,groupType,couponId,name,description," +
					"discount,activationDate,cutOffDate,created_by,created_at,updated_by,updated_at,discounttype," +
					"coupontype)values(to_number(?,'999999999'),?,?,?,?,?,?,now(),now(),'coupon',now(),'coupon',now(),?,?)";
			DbUtil.executeUpdateQuery(insertQry, new String[]{userId,groupId,groupType,couponId,discountName,
					description,discountval,discountType,couponType});
		} else {
			log.info("Updating... discount data");
			String updateQry="update coupon_master set groupType=?,name=?,description=?,discount=?," +
					"updated_by=?,updated_at=now(),discounttype=?,coupontype=? " +
					"where groupid=? and couponid=?";
			DbUtil.executeUpdateQuery(updateQry, new String[]{groupType,discountName,description,discountval,updatedBy,
					discountType,couponType,groupId,couponId});
		}
		ArrayList selectedItems = discountData.getSelItems();
		couponCodesInsert(couponId, discountCodeCSV, limitValue);
		selectedItemsInsert(selectedItems, couponId);
	}

	public static void couponCodesInsert(String couponId,
		String discountCodeCSV, String limitValue) {
		StringTokenizer st = new StringTokenizer(discountCodeCSV, ",");
		String tempcode = "";
		String delQuery="delete from coupon_codes where couponId=?";
		DbUtil.executeUpdateQuery(delQuery, new String[]{couponId});
		String query = "insert into coupon_codes (couponId,couponCode,maxcount) values (?,?,to_number(?,'999999999'))";
		while (st.hasMoreTokens()) {
			tempcode = (String) st.nextToken();
			DbUtil.executeUpdateQuery(query, new String[]{couponId, tempcode.trim(), limitValue});
		}
	}

	public static void selectedItemsInsert(ArrayList selectedItems, String couponId) {	
		String delQuery="delete from coupon_membership where couponId=?";
		DbUtil.executeUpdateQuery(delQuery, new String[]{couponId});
		String insertQuery="insert into coupon_membership (couponId,membership_id) values (?,?)";
		for (int i = 0; i < selectedItems.size(); i++) {
			String id = (String) selectedItems.get(i);
			DbUtil.executeUpdateQuery(insertQuery,new String[] {couponId, id });
		}
	}

	public static boolean deleteDiscountData(String groupId,String couponId){
		com.eventbee.namedquery.NQStatusObj statobj = new com.eventbee.namedquery.NQStatusObj(false, "", "0", 0);
		try{
			String delete_coupon_codes="delete from coupon_codes where couponId=?";
			String delete_coupon_memberships="delete from coupon_membership where couponId=?";
			String delete_coupon_master="delete from coupon_master where couponId=?";
			String queries[] = {delete_coupon_codes,delete_coupon_memberships,delete_coupon_master};
			for(int i=0;i<queries.length;i++){
				DbUtil.executeUpdateQuery(queries[i], new String[]{couponId});
			}
		}
		catch(Exception ex){
			log.error("Exception in delete discount data: "+ex);
		}
		return statobj.getStatus();
	}

	public  static ArrayList<Entity>  getMemberShips(String clubid){
		ArrayList<Entity> obj=new ArrayList<Entity>();
	    String que="select membership_name,membership_id from club_membership_master where" +
	    		" clubid=to_number(?,'999999999') and status='ACTIVE'";
	    DBManager db=new DBManager();
	    StatusObj sb=db.executeSelectQuery(que, new String[] {clubid});
	    if(sb.getStatus())
	    {
	     for(int i=0;i<sb.getCount();i++)
	     {
	     Entity a=new Entity(db.getValue(i,"membership_id",""),db.getValue(i,"membership_name",""));
	     obj.add(a);
	     }
	    }
	      return obj;
	}

	public static DiscountData getDiscountData(String groupId, String id) {
		// select query to fetch the discount codes from DB for this event id
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		DiscountData tempDiscountDataobj = new DiscountData();

		HashMap inParams = new HashMap();
		inParams.put("groupid", groupId);
		inParams.put("couponid", id);
		NQDbHelper dbhelper = new NQDbHelper();
		HashMap hmap = dbhelper.getDataHash("select_discountdata", inParams);
		if (hmap != null) {
			String couponid = hmap.get("couponid").toString();
			tempDiscountDataobj.setId(couponid);
			tempDiscountDataobj.setDiscountType(hmap.get("discounttype")
					.toString());
			tempDiscountDataobj.setName(hmap.get("name").toString());
			//tempDiscountDataobj.setLimitType(hmap.get("limittype").toString());
			tempDiscountDataobj.setDiscountVal(hmap.get("discount").toString());
			tempDiscountDataobj.setDescription(hmap.get("description")
					.toString());
			tempDiscountDataobj.setLimitValue(hmap.get("maxcount").toString());
			if ("100000".equals(hmap.get("maxcount").toString()))
				tempDiscountDataobj.setLimitType("1");
			else
				tempDiscountDataobj.setLimitType("2");
			ArrayList selectedTickets = getSelectedItems(groupId, couponid);
			String codesCSV = getCodesCSV(groupId, couponid);
			tempDiscountDataobj.setDiscountCodescsv(codesCSV);
			tempDiscountDataobj.setSelItems(selectedTickets);
		}
		return tempDiscountDataobj;
	}

	public static ArrayList getSelectedItems(String eid, String id) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList selt = new ArrayList();
		String query="select * from coupon_membership where couponId=?";
		
		statobj=dbmanager.executeSelectQuery(query,new String[]{id}); 
		  int count1=statobj.getCount();
		  if(statobj.getStatus()&&count1>0){
			  for(int k=0;k<count1;k++){
				  String memberId=dbmanager.getValue(k,"membership_id","");
				 selt.add(memberId);			  
			  }   
		  }
		return selt;
	}

	public static String getCodesCSV(String groupId, String id) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		StringBuffer codecsv = new StringBuffer();
		String selTicketQuery="select couponcode,maxcount from coupon_codes where couponid=?";
		statobj=dbmanager.executeSelectQuery(selTicketQuery,new String[]{id}); 
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
		codecsv=codecsv.append(dbmanager.getValue(0,"couponcode",""));
		for(int k=1;k<count1;k++){
			  String  couponcode=dbmanager.getValue(k,"couponcode","");
		codecsv.append(","+couponcode);
		}
		}
		return codecsv.toString();
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
					if(codes[i].equals(list.get(j).toString())){
						errlist.add(codes[i]);
					}
				}
			}
			return errlist;
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return errlist;
	}
}
