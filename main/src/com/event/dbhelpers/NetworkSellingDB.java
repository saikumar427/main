package com.event.dbhelpers;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.event.beans.ticketing.GroupData;
import com.event.beans.ticketing.TicketData;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateConverter;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;

public class NetworkSellingDB {
	private static Logger log=Logger.getLogger(NetworkSellingDB.class);
	public static ArrayList getEventTicketsList(String eid,String userTimeZone) {
		log.info("In NetworkSellingDB getEventTicketsList");
		
		String conversion_factor_qry = "select conversion_factor from currency_symbols where currency_code=(select currency_code from event_currency where eventid=?)";
		String convFactor = DbUtil.getVal(conversion_factor_qry, new String[]{eid});
		if(convFactor == null)
			convFactor="1";
		
		String ebee_nts_commission_for_event_qry = "select commission from ebee_nts_commission where eventid=? and nts_code='0'";
		String ebeeCommission = DbUtil.getVal(ebee_nts_commission_for_event_qry, new String[]{eid});
		if(ebeeCommission == null){
			String ebee_default_commission_qry="select commission from ebee_nts_commission where eventid='0' and nts_code='0'";
			ebeeCommission = DbUtil.getVal(ebee_default_commission_qry, null);
		}
		if(ebeeCommission == null)
			ebeeCommission="0";
		
		ArrayList groupsList=new ArrayList();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		
		String getGroupsListQuery="select distinct a.ticket_groupid,groupname,grouptype,a.position from " +
				"event_ticket_groups a,group_tickets b  where eventid=? and " +
				"a.ticket_groupid=b.ticket_groupid	union select distinct a.ticket_groupid,groupname,grouptype," +
				"a.position from event_ticket_groups a where eventid=? and length(a.groupname)!=0 " +
				"order by position";
		statobj=dbmanager.executeSelectQuery(getGroupsListQuery,new String []{eid,eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
			    String ticket_groupid=dbmanager.getValue(k,"ticket_groupid","");
				String groupname=dbmanager.getValue(k,"groupname","");
				String grouptype=dbmanager.getValue(k,"grouptype","");
				String position=dbmanager.getValue(k,"position","");
				GroupData tempGroupDataobj=new GroupData();
				tempGroupDataobj.setGroupId(ticket_groupid);
				tempGroupDataobj.setGroupName(groupname);
				tempGroupDataobj.setGroupType(grouptype);
				tempGroupDataobj.setPosition(position);
				ArrayList ticketData=new ArrayList();
				ticketData=getTicketsforGroup(ticket_groupid,eid,userTimeZone,convFactor,ebeeCommission);
				tempGroupDataobj.setGroupTickets(ticketData);
				groupsList.add(tempGroupDataobj);

			}
		}
		return groupsList;
	}
	public static ArrayList getTicketsforGroup(String groupId,String eid,String userTimeZone,String convFactor,String ebeeCommission){
		ArrayList tDataList=new ArrayList();
		//select query to fetch the ticketData objects for this group and event ids
		String selectTicketsQry="select  pr.price_id,pr.ticket_name,pr.process_fee,pr.ticket_price," +
				"gt.position,to_char(pr.start_date,'yyyy') as start_yy," +
				"to_char(pr.start_date,'mm') as start_mm,to_char(pr.start_date,'dd') as start_dd," +
				"to_char(pr.end_date,'yyyy') as end_yy,to_char(pr.end_date,'mm') as end_mm," +
				"to_char(pr.end_date,'dd') as end_dd,pr.starttime,pr.endtime,start_date,end_date,start_before,end_before, " +
				"pr.networkcommission from price pr," +
				"event_ticket_groups etg,group_tickets gt where to_number(etg.eventid,'99999999999')=pr.evt_id" +
				" and pr.price_id=to_number(gt.price_id,'999999999') and gt.ticket_groupid=etg.ticket_groupid " +
				"and pr.isdonation='No' and etg.eventid=? and etg.ticket_groupid=? order by etg.position,gt.position";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(selectTicketsQry,new String []{eid,groupId});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			String currency=EventDB.getCurrencySymbol(eid);
			for(int k=0;k<count1;k++){
				TicketData tData=new TicketData();
				String ticketname=dbmanager.getValue(k,"ticket_name","");
				String ticketPrice=dbmanager.getValue(k,"ticket_price","");
				String ticketid=dbmanager.getValue(k,"price_id","");
				String position=dbmanager.getValue(k,"position","");
				String start_dd=dbmanager.getValue(k,"start_dd","");
				String start_mm=dbmanager.getValue(k,"start_mm","");
				String start_yy=dbmanager.getValue(k,"start_yy","");
				String end_dd=dbmanager.getValue(k,"end_dd","");
				String end_mm=dbmanager.getValue(k,"end_mm","");
				String end_yy=dbmanager.getValue(k,"end_yy","");
				String start_time=dbmanager.getValue(k,"starttime","");
				String end_time=dbmanager.getValue(k,"endtime","");
				String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
				String start_date=dbmanager.getValue(k,"start_date","");
				String end_date=dbmanager.getValue(k,"end_date","");
				String start_before = dbmanager.getValue(k,"start_before","");
				String end_before = dbmanager.getValue(k,"end_before","");
				String networkcomm = dbmanager.getValue(k,"networkcommission","");
				DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
				boolean statusdc=dc.convertDateTime(start_date,start_time,userTimeZone);
				DateTimeBean stdtb=dc.getDateTimeBeanObj();
				tData.setStdateTimeBeanObj(stdtb);
				statusdc=dc.convertDateTime(end_date, end_time,userTimeZone);
				DateTimeBean enddtb=dc.getDateTimeBeanObj();
				tData.setEnddateTimeBeanObj(enddtb);
				tData.setPosition(position);
				tData.setTicketName(ticketname);
				ticketPrice=CurrencyFormat.getCurrencyFormat("",ticketPrice,false);
				tData.setTicketPrice(ticketPrice);
				tData.setTicketId(ticketid);
				tData.setStartBefore(end_before);
				tData.setEndBefore(start_before);
				String processfee=dbmanager.getValue(k,"process_fee","");
				processfee=CurrencyFormat.getCurrencyFormat("",processfee,false);
				tData.setProcessingFee(processfee);
				tData.setCurrency(currency);
				
				String credits = getCredits(networkcomm,ticketPrice,convFactor,ebeeCommission);
				tData.setCredits(credits);
				networkcomm=CurrencyFormat.getCurrencyFormat("",networkcomm,false);
				tData.setNetWorkCommission(networkcomm);
				tDataList.add(tData);
			}
		}
		return tDataList;
	}
	
	public static String getCredits(String commission,String price, String convFactor, String ebeeNtsCommission){
		String credits="";
		try{
			double actualcredits =  (Double.parseDouble(price) * Double.parseDouble(commission))/(100 * Double.parseDouble(convFactor));
			// taking ebeecommission
			double finalcredits = actualcredits - (actualcredits * Double.parseDouble(ebeeNtsCommission)/100);
			credits = CurrencyFormat.getCurrencyFormat("",Double.toString(finalcredits),false); 
			
		}catch(Exception ex){
			System.out.println("In getCredits Error: "+ex.getMessage());
		}
		return credits;
	}
	
	public static TicketData getTicketCommissionData(String eid, String tid){
		TicketData ticketData = new TicketData();
		String selectquery="select ticket_name,networkcommission from price where evt_id=CAST(? AS BIGINT) and price_id=to_number(?,'9999999999')";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(selectquery,new String []{eid, tid});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
				ticketData.setTicketName(dbmanager.getValue(0,"ticket_name",""));
				ticketData.setNetWorkCommission(dbmanager.getValue(0,"networkcommission",""));
			}
		return ticketData;
	}
	
	public static void updateCommission(String commission, String tid, String eid){
		String updateQuery="update price set networkcommission=to_number(?,'9999999999999.99') where price_id=to_number(?,'9999999999') and evt_id=CAST(? AS BIGINT)";
		DbUtil.executeUpdateQuery(updateQuery,new String[]{commission, tid, eid});
	}
	
	public static void updateAllTicketsCommission(String commission, String eid){
		
		String query="update eventinfo set nts_commission=to_number(?,'9999999999999.99') where eventid=CAST(? AS BIGINT)";
		DbUtil.executeUpdateQuery(query,new String[]{commission, eid});
		String query2="update price set networkcommission=to_number(?,'9999999999999.99') where evt_id=CAST(? AS BIGINT) and isdonation='No'";
		DbUtil.executeUpdateQuery(query2,new String[]{commission, eid});
	}
	
	public static void updateNTSEnableChargeStatus(String eid){
		DbUtil.executeUpdateQuery("update eventinfo set nts_enable='Y',nts_commission='10' where eventid=CAST(? as BIGINT)", new String[]{eid});
		DbUtil.executeUpdateQuery("update price set networkcommission='10' where evt_id=CAST(? as BIGINT)", new String[]{eid});
	}
	
	/*public static String getFeeShowStatus(String eid){
		String feeshowstatus="";
		String count=DbUtil.getVal("select sum(visit_count) as count from nts_visit_track where eventid=? and visit_count is not null", new String[]{eid});
		if(count==null) count="0";
		if(Integer.parseInt(count)>0)
			feeshowstatus="Y";
		else
			feeshowstatus="N";
		return feeshowstatus;
	}*/
	
}
