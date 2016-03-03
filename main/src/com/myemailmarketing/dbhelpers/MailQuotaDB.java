package com.myemailmarketing.dbhelpers;

import java.util.ArrayList;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class MailQuotaDB {
	public static ArrayList<Entity> mailQuotaListDetails(){
		DBManager dbmanager=new DBManager();
		ArrayList<Entity> list = new ArrayList<Entity>();
		String Query="select quota,price from mail_quota_price " +
				"where role='MEM' order by quotapriceid";
		StatusObj stobj=dbmanager.executeSelectQuery(Query,new String[]{});
		if(stobj.getStatus()){
			for(int i=0;i<stobj.getCount();i++){
				list.add(new Entity(dbmanager.getValue(i,"price",""),dbmanager.getValue(i,"quota","0")+" emails - $"+dbmanager.getValue(i,"price","0")));
			}
		}
		return list;
	}
	public static String getMailQuota(String userId){    
		int quota_left=0;
		//String reservedQuota=DbUtil.getVal("select getReservedMailQuota(?,'MEM','13579')",new String[]{userId});
		String reservedQuota=DbUtil.getVal("select sum(value) from mail_quota where userid=? and type=?",new String[]{userId,"D"});
	//	System.out.println("the userid is"+userId);
	//	System.out.println("the complted count is"+reservedQuota);
		if(reservedQuota==null) reservedQuota="0";
		String tempQuota=DbUtil.getVal("select quotaval from user_quota where userid=? and quotatype='mail.quota.buy'",new String[]{userId});
		if(tempQuota==null) tempQuota="100";
		quota_left=Integer.parseInt(tempQuota)-Integer.parseInt(reservedQuota);		
		if(quota_left<0)
		quota_left=0;
		String Quota=Integer.toString(quota_left);
		return Quota;
	}
}
